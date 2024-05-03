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
<title>Test</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
	<h1>test board page</h1>   
    


    <form id="searchForm" action="list" method="post" >
        <select id="size" name="size" >
        	<c:forEach var="size" items="${sizes}">
        		<option value="${size.name}" ${pageRequestVO.size == size.name ? 'selected' : ''} >${size.name}</option>
        	</c:forEach>
        </select>
    	<label>제목</label>
    	<input type="text" id="searchKey" name="searchKey" value="${param.searchKey}">
        <button type="submit" class="btn btn-dark">검색</button>
    </form>

    <table class ="table caption-top table-bordered table-hover" >
        <caption>게시글 목록</caption> 
        <tr class="table-dark" >
            <th>게시물번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
        <c:forEach var="board" items="${pageResponseVO.list}">
        <tr >
            <td style="cursor:pointer;"><a data-bs-toggle="modal" data-bs-target="#ViewModel" data-bs-bno="${board.bno}">${board.bno}</a></td>
            <td>${board.btitle}</td>
            <td>${board.member_id}</td>
            <td>${board.bdate}</td>
        </tr>
        </c:forEach>
    </table>


    <form id="insertForm" action="insertForm" method="post" action="board.do">
		<input type="button" class="btn btn-secondary" value="등록"
			onclick="jsinsertForm()" class="custom-button">
	</form>


    


    <!-- 상세보기 Modal -->
    <div class="modal fade" id="ViewModel" role="dialog">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">상세정보</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <label>number: </label><span id="bno"></span><br/>
                <label>title : </label><span id="btitle"></span><br/>
                <label>작성자 :</label><span id="member_id"></span><br/>
                <label>작성날짜 : </label><span id="bdate"></span><br/>
                <label>조회수 : </label><span id="view_count"></span><br/>
                <label>내용 : </label><span id="bcontent"></span><br/>
                <label>첨부파일 : </label><span id="filename"></span><br/>
                 <button onclick="download()" class="btn btn-secondary">다운로드</button><br/>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
        </div>
    </div>








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






<!--script sector-->

<script>
    function download() {

    const file_bno = document.getElementById("bno").textContent; // #bno로 id 접근, textContent 사용
    const file_filename = document.getElementById("filename").textContent; // #filename으로 id 접근, textContent 사용
    const formData = new FormData();
    formData.append("bno", file_bno);
    formData.append("filename", file_filename);

    fetch("download",
        {
        method : 'POST',
        body: formData
        })
        .then(
            response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob();}
        )
        .then(blob => {

            const url = window.URL.createObjectURL(blob); // Blob을 URL로 변환
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = file_filename; // 파일 이름 설정
            document.body.appendChild(a);
            a.click(); // 클릭하여 다운로드 실행
            window.URL.revokeObjectURL(url); // URL 해제

        }).catch(
          (error) =>{
                alert("error");
          });
    }
</script>


<script type="text/javascript">
    const span_ViewModel = document.querySelector("#ViewModel");
    const span_bno = document.querySelector(".modal-body #bno");
    const span_btitle = document.querySelector(".modal-body #btitle");
    const span_member_id = document.querySelector(".modal-body #member_id");
    const span_bdate = document.querySelector(".modal-body #bdate");
    const span_view_count = document.querySelector(".modal-body #view_count");
    const span_bcontent = document.querySelector(".modal-body #bcontent");
    const span_filename = document.querySelector(".modal-body #filename");
    ViewModel.addEventListener('shown.bs.modal', function (event) {
        const a = event.relatedTarget;
        const bno = a.getAttribute('data-bs-bno'); //a.dataset["bs-bno"] //, a.dataset.bs-bno 사용안됨
        console.log("모달 대화 상자 출력... member_id ", member_id);
        
        span_bno.innerText = "";
        span_btitle.innerText = "";
        span_member_id.innerText = "";
        span_bdate.innerText = "";
        span_view_count.innerText = "";
        span_bcontent.innerText = "";
        span_filename.innerText = "";

        const custom_param = {
            action : ""
        }

        fetch("BoardInfo",{
            method : 'POST',
            headers:{
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ bno: bno })
        }).then(res => res.json())
        .then(json=>{

            if(json.status == 0){
            
                const jsonBd = json.jsonBoard;
                const file = json.jsonFile;
                if(file != null){
                    span_filename.innerText = file.filename;
                }
                span_bno.innerText = jsonBd.bno;
                span_btitle.innerText = jsonBd.btitle;
                span_member_id.innerText = jsonBd.member_id;
                span_bdate.innerText = jsonBd.bdate;
                span_view_count.innerText = jsonBd.view_count;
                span_bcontent.innerText = jsonBd.bcontent;
                
            }else{
                alert("model set failed");
            }
        }).catch(
            (e) =>{
                alert("error");
            })
    });
</script>





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

document.querySelector("#size").addEventListener("change", e => {
    searchForm.submit();
});

function jsinsertForm(){

    alert(insertForm.action);
    insertForm.submit();
}

</script>

    
</body>
</html>