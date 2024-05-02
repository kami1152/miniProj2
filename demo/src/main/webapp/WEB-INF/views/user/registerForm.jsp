<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <link
      href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      rel="stylesheet"
      id="bootstrap-css"
    />
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link href="<c:url value='/css/loginstyle.css'/>" rel="stylesheet" />
  </head>

  <body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <div class="main">
        <div class="col-md-6 col-sm-12">
          <div class="register-form">
            <form id="registerfrom" action="register" method="post">
              <!-- Input Items -->
              <div class="form-group">
                <label>User ID</label>
                <button  onclick="checkUserID()"  type="button"  style="margin: 10px;" class="btn btn-secondary">중복확인</button>
                <input type="text"  id = "member_id" name="member_id" class="form-control" required />
              </div>

              <div class="form-group">
                <label>Password</label>
                <input type="password" id = "member_pwd" name="member_pwd" class="form-control" required />
              </div>

              <div class="form-group">
                <label>User Name</label>
                <input type="text" id="member_name" name="member_name" class="form-control" required />
              </div>

              <div class="form-group">
                <label>Gender</label>
                <div>
                  <input type="radio" id="gender_male" name="member_gender" value="남성" required>
                  <label for="gender_male">남성</label>
          
                  <input type="radio" id="gender_female" name="member_gender" value="여성" required>
                  <label for="gender_female">여성</label>
              </div>
              </div>

              <div class="form-group">
                <label>Phone number</label>
                <input type="tel" id="member_phone_number" name="member_phone_number" class="form-control" required />
              </div>

              <div class="form-group">
                <label>Address</label>
                <input type="text" id = "member_address" name="member_address" class="form-control" required />
              </div>

              <div class="form-group">
                <label>User Hobby</label><br/>
                <!-- <input type="text" name="hobby" class="form-control" required /> -->
                <c:forEach var="item" items="${hblist}">
                  <label>${item}</label>
                  <input type="checkbox" id="hobbyname" name="hobbyname" value="${item}">
                </c:forEach>
              </div>


              <button type="submit" id="reg-btn" class="btn btn-secondary">
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>

      <script type="text/javascript">
        var validUserId = "";

        function checkUserID(){
            const userID = document.getElementById('member_id').value;
            if(!userID){
                alert("ID를 입력해주세요");
                return;
            }
            const param={
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body:   JSON.stringify({ member_id : userID })
            }
            fetch('checkID',param)
            .then( res => res.json())
            .then(json =>{
              if(json.status == -99){
                alert(json.msg);
                var validUserId = "";
              }else{
                alert(json.msg);
                var validUserId = userID;
              }
              
            })
            .catch(error=>{
              alert("서버 오류");
            })
            ;

        }
      
      
      </script>
  </body>
</html>
