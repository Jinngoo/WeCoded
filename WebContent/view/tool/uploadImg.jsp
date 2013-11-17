<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>UploadImg</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="keywords" content="jinnan,jinn">
	<meta http-equiv="description" content="jinn">
	
	<!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${JQUERY_JCROP_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	<script type="text/javascript" src="${JQUERY_FORM}"></script>
	<script type="text/javascript" src="${JQUERY_JCROP_JS}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/util/jinva.js"></script>
	
	<script type="text/javascript" src="${RESOURCE}/js/tool/uploadImg.js"></script>
	
	<style type="text/css">
    </style>
</head>
<body>

	<%@ include file="../nav_top.jsp" %>


	<div class="container">
		<h1>Hehe</h1>
		<input type="file" id="fileChooser" style="display:none">
		<button onclick="chooseImg()">选择图片</button>
		<button onclick="copyImg()">截取图片</button>
		<div class="error"></div>
		
		<table>
			<tr>
				<td align="center" style="width: 300px; height: 300px; border: 1px solid gray">
					<img id="jcropTarget" />
				</td>
				<td align="center" >
					<div id="preview" style="width:200px;height:200px; overflow:hidden;margin-left:5px;">
						<img style="display: inline; ">
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<form id="uploadForm" action="${CONTEXT_PATH}/tool/uploadImg" method="post" enctype="multipart/form-data">
		<input id="uploadData" name="uploadData" type="hidden">
		<input id="uploadName" name="uploadName" type="hidden" value="${name}">
		<input id="uploadType" name="uploadType" type="hidden" value="${type}">
	</form>

</body>
</html>