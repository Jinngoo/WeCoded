<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="./base.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Jinn Here</title>
	<%@ include file="./head.jsp"%>  
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="keywords" content="jinngoo,jinnan,jinn">
	<meta http-equiv="description" content="jinngoo">
	<!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_BUTTONS_CSS}" rel="stylesheet" media="screen">
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_BUTTONS_JS}"></script>
	
	<style type="text/css">
		.logo{
			background-image: url("${RESOURCE}/image/logo.png");
			background-repeat: no-repeat;
			background-position: right;
			height: 101px;
			line-height: 101px;
		}
	</style>
  </head>

<body>
	<div class="container text-center">
		<br/>
		<br/>
		<br/>
		<div class="logo">
			<h1>Jinn</h1>
			<h3>${requestScope.serverInfo }</h3>
		</div>
		
		<br/><br/><br/>
		<span>${requestScope.test } - </span>
		
		<a href="${pageContext.request.contextPath}/user/signOut" class="button glow button-rounded button-flat button-tiny">Sign Out</a>


		<br/><br/><br/>
		<div class="well" style="font-size: 18px;">
			&gt;&gt;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/test/canvas">Test Canvas</a><br/><br/>
			&gt;&gt;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/test/websocket">Test WebSocket</a><br/><br/>
			&gt;&gt;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/accounting">Accounting</a><br/><br/>
			&gt;&gt;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/game">Games</a><br/><br/>
		</div>

		<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
			Powered by Jinn
<!-- 			<a href="http://getbootstrap.com/">http://getbootstrap.com/</a><br/> -->
<!-- 			<a href="http://www.bootcss.com/">http://www.bootcss.com/</a><br/> -->
<!-- 			<a href="http://www.bootcss.com/p/buttons/">http://www.bootcss.com/p/buttons/</a><br/> -->
<!-- 			<a href="http://www.bootcss.com/p/bootstrap-switch/">http://www.bootcss.com/p/bootstrap-switch/</a><br/> -->
<!-- 			<a href="http://www.bootcss.com/p/bootstrap-datetimepicker/">http://www.bootcss.com/p/bootstrap-datetimepicker/</a><br/> -->
<!-- 			<a href="http://www.bootcss.com/p/font-awesome/">http://www.bootcss.com/p/font-awesome/</a><br/> -->
<!-- 			<a href="http://www.bootcss.com/p/websafecolors/">http://www.bootcss.com/p/websafecolors/</a><br/> -->
		</nav>
	</div>
</body>
</html>
