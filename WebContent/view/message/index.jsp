<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Message</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
    <script type="text/javascript">
	    $(document).ready(function(){
			$("#mainContent").slideDown("fast");
		});
    </script>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<div class="container">
		<div id="mainContent" style="display:none;margin-left:20px;">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#aa" data-toggle="tab">啊是</a></li>
				<li><a href="#bb" data-toggle="tab">哦飞</a></li>
				<li><a href="#cc" data-toggle="tab">饿一</a></li>
			</ul>
			
			<div class="tab-content">
				<div class="tab-pane active" id="aa">阿大的撒</div>
				<div class="tab-pane" id="bb">个归属感</div>
				<div class="tab-pane" id="cc">效果桑德菲杰风格</div>
			</div>
		</div>
	</div>



</body>
</html>