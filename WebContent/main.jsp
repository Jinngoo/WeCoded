<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:text name="main.title"></s:text></title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>
    <script type="text/javascript" src="${JQUERY_MD5}"></script>
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${abc}"></script>
    <script type="text/javascript">
    	//AjaxAnywhere.prototype.showLoadingMessage = function() {}
    	//AjaxAnywhere.prototype.hideLoadingMessage = function() {}
    	$(function () {
	    	
    	});
    	window.onbeforeunload = function(){
    		$(document).scrollTop(0);
    	};
    	$(document).ready(function(){
    		$("#logout").click(function(){
    			logout();
    		});
    	});
    	function content(src){
    		$("#content_frame").attr("src", src);
    	}
    	function logout(){
    		var url = 'ajax_logout';
    		 $.post(url, {}, function(data, textStatus, jqXHR){
    			 window.location.reload();
    		 });
    	}
    </script>
</head>
<body>
 
	<div class="navbar navbar-fixed-top navbar-inverse">
		<div class="navbar-inner">
			<div class="container" style="width: 1200px;">
				<a class="brand" href="#" style="font-weight:bold"><s:text name="main.title"></s:text></a>
				<ul class="nav">
					<li class="divider-vertical"></li>
					<li><a href="javascript:content('main_news');" >首页</a></li>
					<li><a href="javascript:content('main_dining');">吃的</a></li>
					<li><a href="javascript:content('main_message');">消息(x)</a></li>
					<li><a href="javascript:content('main_setting');">设置</a></li>
					<li class="divider-vertical"></li>
				</ul>
				<form class="navbar-form pull-left">
					<input type="text" class="search-query" placeholder="Search">
				</form>
				<button id="logout" class="btn btn-danger pull-right">Logout</button>
			</div>
		</div>
	</div>
 
	<div style="clear:both"></div>
	
	<div class="content">
		
		<!-- left -->
		<iframe id="content_frame" src="main_dining" style="width:1000px;height:100%" style="border:none;" frameborder="no"></iframe>
		
		<!-- rihgt -->
		<div class="span2 well pricehover pull-right" >
				<img id="photo" class="photo" alt="" src="img?type=1">
				<div id="user_name"><c:out value="${sessionScope.user_nickname }"></c:out></div>
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="#" data-toggle="pill">menu 1</a></li>
					<li><a href="#" data-toggle="pill">menu two</a></li>
					<li><a href="#" data-toggle="pill">menu 三</a></li>
				</ul>
		</div>
	</div>
	
	
	<div class="navbar navbar-bottom" style="margin-bottom:0;padding-bottom:0">
		<div class="navbar-inner">
			<div class="container">
				<div class="info">Copyright 2012-2013 ???????.com All rights reserved.<br/><br/>参考 http://www.bootcss.com</div>
			</div>
		</div>
	</div>
	

	

</body>
</html>