<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.min.js" ></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 작성</title>
    <!-- CKEditor 스크립트 -->
    <script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
</head>
<body>

    <%@ include file="/WEB-INF/views/include/header.jsp" %>

    <div class="container mt-5">
        <h2 class="mb-4">게시글 작성</h2>
        <form id="postForm" method="post" action="insert" enctype="multipart/form-data">
            <input type ="hidden" id ="bwriter" name="bwriter" value="${principal.member_id}"">
            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요">
            </div>
            <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea class="form-control" id="content" name="content" ></textarea>
            </div>
            <div class="mb-3">
                <label for="attachments" class="form-label">파일 첨부</label>
                <input type="file" class="form-control" id="attachments" name="attachments" multiple>
            </div>
            <button type="submit" id="insert-btn" name="insert-btn" class="btn btn-primary">게시글 등록</button>
        </form>
    </div>







    
    <script>
        // CKEditor 초기화
        CKEDITOR.replace('content');

        // 폼 데이터를 JSON으로 변환하여 서버에 제출
        document.getElementById('insert-btn').addEventListener('click', function(e) {
            e.preventDefault(); 

            CKEDITOR.instances.content.updateElement();

            var btitle = document.getElementById('title').value;
            var bcontent = document.getElementById('content').value;

            if(btitle == "" || btitle == ""){
                msg = "제목과 내용은 필수 입력 사항입니다.";
                alert(msg);
                return;
            }
            
            const formData = new FormData(document.getElementById('postForm'));

            fetch('insert', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(
                json => {
                    alert(json.status);
                    if(json.status == 0){
                        alert(json.msg);
                        window.location.href = '/board/list';
                    }else{
                        alert(json.msg);
                    }
                }
            )
            .catch(error => console.error('Error:', error));
        });
    </script>
</body>
</html>
