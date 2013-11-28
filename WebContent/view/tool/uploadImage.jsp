<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>上传图片</title>
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
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/tool/uploadImage.js"></script>
	
	<style type="text/css">
		.error{
			font-weight: bold;
		}
    </style>
</head>
<body>

	<%@ include file="../nav_top.jsp" %>


	<div class="container">
		<h1 class="text-center alert alert-success">上传图片</h1>
		<table style="margin-left: 50px">
			<tr>
				<td align="center" style="width: 300px; height: 300px; border: 1px solid gray">
					<div class="error text-center">&nbsp;</div>
					<img id="jcropTarget" />
				</td>
				<td align="center">
					预览
					<div id="preview" style="width:200px;height:200px; overflow:hidden;border: 1px solid gray;margin-left:50px;margin-bottom:20px">
						<img style="display: inline; ">
					</div>
					<button class="btn btn-primary" onclick="chooseImg()" style="margin-left:50px">选择图片</button>
					<button class="btn btn-success" onclick="saveImg(this)">保存</button>
				</td>
			</tr>
		</table>
		
	</div>
	//TODO 增加切换选择区域大小的功能
	//TODO 选择框宽高比不要固定，保存时存成需要的大小
	
	<input type="file" id="fileChooser" style="display:none" accept="image/jpeg,image/x-png">
	<form id="uploadForm" action="${CONTEXT_PATH}/tool/uploadImage" method="post" enctype="multipart/form-data">
		<input id="uploadData" name="uploadData" type="hidden">
		<input id="uploadName" name="uploadName" type="hidden" value="${name}">
		<input id="uploadType" name="uploadType" type="hidden" value="${type}">
	</form>

</body>
</html>