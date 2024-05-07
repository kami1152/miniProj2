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

<script type="text/javascript">
  window.onload = function() {
      // URL에서 쿼리 문자열을 파싱하는 함수
      function getParameterByName(name, url = window.location.href) {
          name = name.replace(/[\[\]]/g, '\\$&');
          var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
              results = regex.exec(url);
          if (!results) return null;
          if (!results[2]) return '';
          return decodeURIComponent(results[2].replace(/\+/g, ' '));
      }
  
      // 'error'와 'exception' 파라미터를 확인
      var error = getParameterByName('error');
      var exception = getParameterByName('exception');
  
      // error가 true이고, exception 메시지가 "계정이 잠겼습니다"인 경우 alert 표시
      if (error === 'true' && exception === '계정이 잠겼습니다') {
          alert('계정이 잠겼습니다');
      }

  };

  </script>
  </body>
</html>
