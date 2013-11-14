<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="./base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
    <%@ include file="./head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="keywords" content="jinnan,jinn">
	<meta http-equiv="description" content="jinn">
	
	<!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	
    <script type="text/javascript" src="${RESOURCE}/js/main.js"></script>
</head>
<body>

	<%@ include file="nav_top.jsp" %>


	<div class="container">
	
	<h1>Jinn</h1>
	<h3>${requestScope.serverInfo }</h3>
	<h4>${requestScope.test }</h4>
	<button onclick="test()">abc</button>
	<aa:zone name="testzone">
		${requestScope.test }
	</aa:zone>
	
		<a href="${CONTEXT_PATH}/dining" target="_self"><h2>dining &lt;==</h2></a>
	
<%-- 		<iframe id="content_frame" src="${CONTEXT_PATH}/dining" style="width:1000px;height:100%" style="border:none;" frameborder="no"></iframe> --%>

		<%--
		<!-- rihgt -->
		<div class="span2 well pricehover pull-right">
			<img id="photo" class="photo" alt="" src="img?type=1">
			<div id="user_name">
				<c:out value="${sessionScope.user_nickname }"></c:out>
			</div>
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="#" data-toggle="pill">menu 1</a></li>
				<li><a href="#" data-toggle="pill">menu two</a></li>
				<li><a href="#" data-toggle="pill">menu 三</a></li>
			</ul>
		</div>
		 --%>
	</div>


	<%@ include file="nav_bottom.jsp" %>
</body>
</html>