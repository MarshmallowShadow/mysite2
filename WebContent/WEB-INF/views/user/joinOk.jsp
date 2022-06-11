<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>성공</title>
		<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
		<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">
	</head>
	
	<body>
		<div id="wrap">
	
			<!-- //header -->
			<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
	
			<!-- //nav -->
			<c:import url="/WEB-INF/views/includes/nav.jsp"></c:import>
	
			<div id="container" class="clearfix">
				<div id="aside">
					<h2>회원</h2>
					<ul>
						<li>회원정보</li>
						<li>로그인</li>
						<li>회원가입</li>
					</ul>
				</div>
				<!-- //aside -->
	
				<div id="content">
				
					<div id="content-head">
						<h3>회원가입</h3>
						<div id="location">
							<ul>
								<li>홈</li>
								<li>회원</li>
								<li class="last">회원가입</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					<!-- //content-head -->
		
					<div id="user">
						<div id="joinOK">
						
							<p class="text-large bold">
								회원가입을 축하합니다.<br>
								<br>
								<%-- 로그인 폼 이동 --%>
								<a href="/mysite2/user?action=loginForm" >[로그인하기]</a>
							</p>
								
						</div>
						<!-- //joinOK -->
					</div>
					<!-- //user -->
				</div>
				<!-- //content  -->
			</div>
			<!-- //container  -->
	
	
			<!-- //footer -->
			<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	
		</div>
		<!-- //wrap -->
	
	</body>

</html>