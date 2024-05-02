<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <link
      href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      rel="stylesheet"
      id="bootstrap-css"
    />
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    <link href="<c:url value='/css/loginstyle.css'/>" rel="stylesheet" />

    <title>로그인</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <div class="main">
      <div class="col-md-6 col-sm-12">
        <div class="login-form">
          <form id="loginForm" action="" method="post">
            <div class="form-group">
              <label>User Name</label>
              <input type="text" name="email" class="form-control" />
            </div>
            <div class="form-group">
              <label>Password</label>
              <input type="password" name="password" class="form-control" />
            </div>
            <button type="button" id="login-btn" class="btn btn-black">
              Login
            </button>
            <button type="button" id="register-btn" class="btn btn-secondary">
              Register
            </button>
          </form>
        </div>
      </div>
    </div>

    <script type="text/javascript">
      document
        .getElementById("login-btn")
        .addEventListener("click", function () {
          loginForm.action = "/login";
          loginForm.submit();
        });
    </script>



    <script type="text/javascript">
      document
        .getElementById("register-btn")
        .addEventListener("click", function () {
            alert("회원 가입 페이지로 이동합니다.");
            loginForm.action = "registerForm"
            loginForm.submit();
        });
    </script>
  </body>
</html>
