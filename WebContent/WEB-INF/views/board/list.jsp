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
						<div id="list">
							<form action="./board?action=search" method="get">
								<div class="form-group text-right">
									<input type="hidden" name="action" value="list">
									<input type="text" name="keyword" value="">
									<button type="submit" id=btn_search>검색</button>
								</div>
							</form>
							<table >
								<thead>
									<tr>
										<th>번호</th>
										<th>제목</th>
										<th>글쓴이</th>
										<th>조회수</th>
										<th>작성일</th>
										<th>관리</th>
									</tr>
								</thead>
								<tbody>
									<%-- 컨트롤러에 받은 bList로 게시글 나열 --%>
									<c:forEach items="${bList}" var="bVo">
										<tr>
											<td>${bVo.no }</td>
											<td class="text-left"><a href="./board?action=read&no=${bVo.no }">${bVo.title }</a></td>
											<td>${bVo.name }</td>
											<td>${bVo.hit }</td>
											<td>${bVo.regDate }</td>
											<td>
												<c:if test="${authUser.no == bVo.userNo }">
													<a href="./board?action=delete&no=${bVo.no }">[삭제]</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				
							<div id="paging">
								<ul>
									<li><a href="">◀</a></li>
									<li><a href="">1</a></li>
									<li><a href="">2</a></li>
									<li><a href="">3</a></li>
									<li><a href="">4</a></li>
									<li class="active"><a href="">5</a></li>
									<li><a href="">6</a></li>
									<li><a href="">7</a></li>
									<li><a href="">8</a></li>
									<li><a href="">9</a></li>
									<li><a href="">10</a></li>
									<li><a href="">▶</a></li>
								</ul>
								
								
								<div class="clear"></div>
							</div>
							<c:choose>
								<c:when test="${authUser == null }"></c:when>
								<c:otherwise><a id="btn_write" href="./board?action=writeForm">글쓰기</a></c:otherwise>
							</c:choose>
						
						</div>
						<!-- //list -->
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
