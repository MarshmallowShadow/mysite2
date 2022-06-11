<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- UserVo, jstl 필수 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.javaex.vo.UserVo"%>	

<!-- html태그 없어도 됨 -->
<div id="header" class="clearfix">
	<h1>
		<a href="/mysite2/main?">MySite</a>
	</h1>
	
	<%-- 로그인 상태에 따라 어떤결 표시할지 정하기 --%>
	<c:choose>
		<%-- 로그인 상태 아니면 (authUser이 null(empty) 일 경우 --%>
		<c:when test="${empty sessionScope.authUser}">
			<ul>
				<li><a href="/mysite2/user?action=loginForm" class="btn_s">로그인</a></li>
				<li><a href="/mysite2/user?action=joinForm" class="btn_s">회원가입</a></li>
			</ul>
		</c:when>
		<%-- 로그인 상태일 경우 --%>
		<c:otherwise>
			<ul>
				<li>${sessionScope.authUser.name}님 안녕하세요 ^^</li>
				<li><a href="./user?action=logout" class="btn_s">로그아웃</a></li>
				<li><a href="./user?action=modifyForm" class="btn_s">회원정보수정</a></li>
			</ul>
		</c:otherwise>
	</c:choose>
	
</div>