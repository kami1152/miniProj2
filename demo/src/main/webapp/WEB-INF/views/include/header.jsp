<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
  <sec:authentication property="principal" var="principal" />
</sec:authorize>

<ul class="nav nav-pills nav-justified">
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/'/>" id="home_link">회사소개</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value='/board/list'/>" id="board_link"
      >게시물</a
    >
  </li>

  <c:choose>
    <c:when test="${empty principal}">
      <li class="nav-item">
        <a
          class="nav-link"
          href="<c:url value='/user/findById'/>"
          id="find_id_link"
          >아이디찾기</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          href="<c:url value='/user/findByPassword'/>"
          id="find_password_link"
          >비밀번호 찾기</a
        >
      </li>
    </c:when>
    <c:otherwise>
      <!-- 관리자 -->
      <c:if test="${principal.member_roles == 'admin'}">
        <li class="nav-item">
          <a
            class="nav-link"
            href="<c:url value='/admin/list'/>"
            id="member_link"
            >회원관리</a
          >
        </li>
      </c:if>
     
    </c:otherwise>
  </c:choose>

  <c:choose>
    <c:when test="${empty principal}">
      <li class="nav-item">
        <a
          class="nav-link"
          href="<c:url value='/user/loginForm'/>"
          id="login_link"
          >로그인</a
        >
      </li>
    </c:when>
    <c:otherwise>
      <li class="nav-item">
        <a class="nav-link" id="login_link"
          >${principal.member_name}</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          href="<c:url value='/user/mypage'/>"
          id="mypage_link"
          >마이페이지</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          href="<c:url value='/user/logout'/>"
          id="mypage_link"
          >로그아웃</a
        >
      </li>
    </c:otherwise>
  </c:choose>
</ul>
