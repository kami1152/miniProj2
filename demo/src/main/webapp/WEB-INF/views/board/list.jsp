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
            <td style="cursor:pointer;"><a data-bs-toggle="modal" data-bs-target="#boardViewModel" data-bs-bno="${board.bno}">${board.bno}</a></td>
            <td>${board.btitle}</td>
            <td>${board.member_id}</td>
            <td>${board.bdate}</td>
        </tr>
        </c:forEach>
    </table>


    <form id="insertForm" action="insertForm" method="post" action="board.do">
		<input type="button" value="등록"
			onclick="jsinsertForm()" class="custom-button">
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