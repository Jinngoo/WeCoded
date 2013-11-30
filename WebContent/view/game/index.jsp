<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Games</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${THREE}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/game/index.js"></script>
	
    <script type="text/javascript">
	   
    </script>
    <style type="text/css">
    	html, body{
    		width: 100%;
    		height: 100%;
    	}
    	div#canvas-frame{
		  border: 1px solid gray;
		  cursor: pointer;
		  background-color: #EEEEEE;
		  width: 100%;
		}
	</style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>

	<div id="canvas-frame"></div>


</body>
</html>