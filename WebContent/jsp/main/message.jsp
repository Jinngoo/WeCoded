<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Message</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
	    $(document).ready(function(){
			$("#mainContent").slideDown("fast");
		});
    </script>
</head>
<body>

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




</body>
</html>