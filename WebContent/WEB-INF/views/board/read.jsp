<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판</title>
		<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
		<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">
	</head>


	<body>
		<div id="wrap">
			
			<!-- //header -->
			<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
			
			<!-- //nav -->
			<c:import url="/WEB-INF/views/includes/nav.jsp"></c:import>
	
			<div id="container" class="clearfix">
				<div id="aside">
					<h2>게시판</h2>
					<ul>
						<li><a href="">일반게시판</a></li>
						<li><a href="">댓글게시판</a></li>
					</ul>
				</div>
				<!-- //aside -->
	
				<div id="content">
	
					<div id="content-head">
						<h3>게시판</h3>
						<div id="location">
							<ul>
								<li>홈</li>
								<li>게시판</li>
								<li class="last">일반게시판</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					<!-- //content-head -->
		
					<div id="board">
						<div id="read">
							<form action="./board?" method="get">
								<%-- 컨트롤러가 보내준 Vo를 통해서 정보들 넣어주기 --%>
								<!-- 작성자 -->
								<div class="form-group">
									<span class="form-text">작성자</span>
									<span class="form-value">${bVo.name }</span>
								</div>
								
								<!-- 조회수 -->
								<div class="form-group">
									<span class="form-text">조회수</span>
									<span class="form-value">${bVo.hit }</span>
								</div>
								
								<!-- 작성일 -->
								<div class="form-group">
									<span class="form-text">작성일</span>
									<span class="form-value">${bVo.regDate }</span>
								</div>
								
								<!-- 제목 -->
								<div class="form-group">
									<span class="form-text">제 목</span>
									<span class="form-value">${bVo.title }</span>
								</div>
							
								<!-- 내용 -->
								<div id="txt-content">
									<span class="form-value" >
										${bVo.content }
									</span>
								</div>
								
								<c:if test="${authUser.no == bVo.userNo }">
									<a id="btn_modify" href="./board?action=modifyForm&no=${param.no }">수정</a>
								</c:if>
								<a id="btn_modify" href="./board?action=list">목록</a>
								
							</form>
							<!-- //form -->
						</div>
						<!-- //read -->
					</div>
					<!-- //board -->
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
