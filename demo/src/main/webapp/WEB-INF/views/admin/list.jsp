<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/include/css.jsp" %>
<%@ include file="/WEB-INF/views/include/js.jsp" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.min.js" ></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Test</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>


    <form id="searchForm" action="list" method="post" >
        <select id="searchType" name="searchType" >
        	<c:forEach var="item" items="${boxlist}">
        		<option value="${item}">${item}</option>
        	</c:forEach>
        </select>
    	<label>제목</label>
    	<input type="text" id="searchKey" name="searchKey" value="${param.searchKey}">
        <button type="submit" class="btn btn-dark">검색</button>
    </form>

<form id="tabledataform" action="" method="post">
    <table class ="table caption-top table-bordered table-hover" >
        <caption>유저 목록</caption> 
        <tr class="table-dark">
            <th>선택</th>
            <th>유저 이름</th>
            <th>아이디</th>
            <th>전화 번호</th>
            <th>계정 잠금</th>
        </tr>
            <c:forEach var="user" items="${userResponseVO.list}">
            <tr >
                <td><input type="checkbox" class="select-user" id="member_id" name="member_id" value="${user.member_id}"></td>
                <td style="cursor:pointer;"><a data-bs-toggle="modal" data-bs-target="#ViewModel" data-bs-memberid="${user.member_id}">${user.member_id}</td>
                <td>${user.member_id}</td>
                <td>${user.member_phone_number}</td>
                <td>${user.member_account_locked}</td>
            </tr>
            </c:forEach>  
    </table>
    <button type="button" id="submit-btn" class="btn btn-dark">잠금해제</button>
    <button type="button" id="delete-btn" class="btn btn-dark">삭제</button>
</form>



	<div class="float-end">
		<ul class="pagination flex-wrap">
			<c:if test="${pageResponseVO.prev}">
				<li class="page-item"><a class="page-link"
					data-num="${pageResponseVO.start -1}">이전</a></li>
			</c:if>

			<c:forEach begin="${pageResponseVO.start}" end="${pageResponseVO.end}" var="num">
				<li class="page-item ${pageResponseVO.pageNo == num ? 'active':''} "><a
					class="page-link" data-num="${num}">${num}</a></li>
			</c:forEach>

			<c:if test="${pageResponseVO.next}">
				<li class="page-item"><a class="page-link"
					data-num="${pageResponseVO.end + 1}">다음</a></li>
			</c:if>
		</ul>
	</div>


    

    <!-- 상세보기 Modal -->
    <div class="modal fade" id="ViewModel" role="dialog">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">상세정보</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <label>이름: </label><span id="member_name"></span><br/>
                <label>아이디 : </label><span id="member_id"></span><br/>
                <label>성별 : </label><span id="member_gender"></span><br/>
                <label>전화번호 :</label><span id="member_phone_number"></span><br/>
                <label>권한 : </label><span id="member_privilege"></span><br/>
                <label>계정잠금 : </label><span id="member_account_locked"></span><br/>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
        </div>
    </div>


<!--script sector-->
<script>
        document.querySelector(".pagination").addEventListener("click", function (e) {
        e.preventDefault()

        const target = e.target

        if(target.tagName !== 'A') {
            return
        }
        //dataset 프로퍼티로 접근 또는 속성 접근 메서드 getAttribute() 사용 하여 접근 가능 
        //const num = target.getAttribute("data-num")
        const num = target.dataset["num"];
        
        //페이지번호 설정 
        searchForm.innerHTML += `<input type='hidden' name='pageNo' value='\${num}'>`;
        searchForm.submit();
     
        });

</script>

<script type="text/javascript">
    const ViewModel = document.querySelector("#ViewModel");
    const span_member_name = document.querySelector(".modal-body #member_name");
    const span_member_id = document.querySelector(".modal-body #member_id");
    const span_member_gender = document.querySelector(".modal-body #member_gender");
    const span_member_phone_number = document.querySelector(".modal-body #member_phone_number");
    const span_member_privilege = document.querySelector(".modal-body #member_privilege");
    const span_member_account_locked = document.querySelector(".modal-body #member_account_locked");
    ViewModel.addEventListener('shown.bs.modal', function (event) {
        const a = event.relatedTarget;
        const member_id = a.getAttribute('data-bs-memberid'); //a.dataset["bs-bno"] //, a.dataset.bs-bno 사용안됨
        console.log("모달 대화 상자 출력... member_id ", member_id);
        
        span_member_name.innerText = "";
        span_member_id.innerText = "";
        span_member_gender.innerText = "";
        span_member_phone_number.innerText = "";
        span_member_privilege.innerText = "";
        span_member_account_locked.innerText = "";
        const custom_param = {
            action : ""
        }
        myFetch("jsonBoardInfo", { member_id : member_id }, json => {
            if(json.status == 0) {
                //성공
                const jsonBoard = json.jsonBoard; 
                span_member_name.innerText = jsonBoard.member_name;
                span_member_id.innerText = jsonBoard.member_id;
                span_member_gender.innerText = jsonBoard.member_gender;
                span_member_phone_number.innerText = jsonBoard.member_phone_number;
                span_member_privilege.innerText = jsonBoard.member_privilege;
                span_member_account_locked.innerText = jsonBoard.member_account_locked;
            } else {
                alert(json.statusMessage);
            }
        });
    });
</script>

<script>
    document.getElementById('delete-btn').addEventListener('click',function(){

        tabledataform.action = "deleteAdmin";
        alert(tabledataform.action);
        tabledataform.submit();
        
    });
</script>

<script type="text/javascript">
    document.getElementById('submit-btn').addEventListener('click', function(){
        const checkedBoxes = document.querySelectorAll('.select-user:checked');
        const selectedIds = Array.from(checkedBoxes).map(box => box.value);
        fetch('unlockService',{
            method : 'POST',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ memberIds: selectedIds })
        }).then(response => response.json())
        .then(json =>{
            alert(json.msg);
            location.reload();
        }).catch((error) =>{
            alert("ERROR");
        })

    });
</script>
    
</body>
</html>