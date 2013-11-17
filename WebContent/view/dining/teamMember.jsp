<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>小组成员</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	<link href="${RESOURCE}/css/common.css" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	
	<script type="text/javascript" src="${RESOURCE}/js/util/jinva.js"></script>
	
    <script type="text/javascript">
    	$(document).ready(function(){
    		$("#mainContent").slideDown("fast");
    	});
    	function goback(backUrl){
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href = decodeURIComponent(backUrl);
    		});
    	}
    	function showTool(obj){
    		$(obj).find("div:first").show();
    	}
    	function hideTool(obj){
    		$(obj).find("div:first").hide();
    	}
    	function deleteUser(userId){
    		alert('暂不支持啊不支持')
    	}
    </script>
    <style type="text/css">
    	.avatar{
    		width: 50px;
    		height: 50px;
    	}
    </style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<c:set var="iamOwner" value="${team.ownerId eq sessionScope.user.id }" />
	
	<div class="container">
		<div id="mainContent" style="display:none;">
			<div class="well">
				<div style="float:left">
					<img src="${CONTEXT_PATH}/getImage/2/${team.id}" style="width:100px;height:100px" />
				</div>
				<div style="float:left;margin-left:20px;">
					&nbsp;&nbsp;&nbsp;&nbsp;小组名:&nbsp;<c:out value="${team.name }"/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;创建者:&nbsp;<c:out value="${team.ownerName }"/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;成员数:&nbsp;<c:out value="${team.memberCount }"/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;组简介:&nbsp;<c:out value="${team.introduction }"/><br/>
				</div>
				<div style="clear:both"></div>
			</div>
			<div id="collapseTool" class="collapse in collapseDish">
				<button class="btn btn-danger" style="margin-left:10px;" onclick="goback('${requestScope.backUrl}')">&lt;&lt;&nbsp;返回</button>
				<c:if test="${iamOwner}">
					<button class="btn btn-success" style="margin-left:20px;" data-toggle="collapse" data-target=".collapseDish">添加成员</button>
				</c:if>
			</div>
			<div id="collapseCreateDish" class="collapse collapseDish">
			 	Coming soon... <a class="btn btn-small offset1" data-toggle="collapse" data-target=".collapseDish">取消</a>
			</div>
			
			<hr/>
			<aa:zone name="memberList">
			<div id="memberList">
				<div class="alert alert-danger">
					创建者:&nbsp;&nbsp;
					<img class="avatar shadow" src="${CONTEXT_PATH}/getImage/1/${team.ownerId}"/>&nbsp;&nbsp;
					${team.ownerName }
				</div>
				<c:forEach items="${memberList }" var="user" varStatus="status">
					<div class="alert alert-success" onmouseover="showTool(this)" onmouseout="hideTool(this)">
						组成员:&nbsp;&nbsp;
						<img class="avatar shadow" src="${CONTEXT_PATH}/getImage/1/${user.id}"/>&nbsp;&nbsp;
						${user.nickname }
						<div style="display:none;float:right;cursor:pointer;">
							<c:if test="${iamOwner}">
								<i class="icon-ban-circle" style="padding-right:5px;" title="移除成员" onclick="deleteUser('${user.id}')"></i>
							</c:if>
						</div>
						<hr/>
					</div>
				</c:forEach>
			</div>
			</aa:zone>
		</div>
	</div>
	
	<div class="modal fade" id="confirmModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h5 id="confirmTip"></h5>
				</div>
				<div class="modal-body">
					<button class="btn btn-danger" data-dismiss="modal" style="margin-right:20px;" id="confirmBtn">确定</button>
					<button class="btn" data-dismiss="modal">取消</button>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->



</body>
</html>