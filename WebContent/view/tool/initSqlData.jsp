<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Test2</title>
    <%@ include file="../head.jsp"%>  
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
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		    
		});
		function init(btn){
		    var url = contextPath + "/tool/initSqlData/do";
		    $(btn).button("loading");
		    $("#progress-bar").addClass("active");
		    $.get(url, {}, function(data, textStatus, jqXHR) {
		        $(btn).button("reset");
			    $("#progress-bar").removeClass("active");
			    if(data == "success"){
			        alert("Success! Default password : 12345")
			        window.location.href = contextPath + "/";
			    }else{
			        alert(data);
			    }
		    });
		}
	</script>
</head>
<body>
	<br/><br/><br/><br/><br/><br/><br/><br/>
	<div class="container" style="text-align: center">
		<div class="progress progress-striped" id="progress-bar">
		  <div class="progress-bar"  role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
		  </div>
		</div>
		<br/><br/>
		<button class="btn btn-success" onclick="init(this)">初始化</button>
		<a class="btn btn-default" href="${CONTEXT_PATH}/">主页</a>
	</div>
</body>
</html>