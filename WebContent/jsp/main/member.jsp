<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Member</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		$("#mainContent").slideDown("fast");
    	});
    	function goback(){
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href="${param.back}";
    		});
    	}
    	function showTool(obj){
    		$(obj).find("div:first").show();
    	}
    	function hideTool(obj){
    		$(obj).find("div:first").hide();
    	}
    	function deleteUser(userId){
    		
    	}
    </script>
    <style type="text/css">
    </style>
</head>
<body>

	<div id="mainContent" style="display:none;margin-left:20px;">
		
		<div class="well">
			<div style="float:left">
				<img src="${pageContext.request.contextPath}/image/group.jpg" style="width:100px;height:100px" />
			</div>
			<div style="float:left;margin-left:20px;">
				&nbsp;&nbsp;&nbsp;&nbsp;小组名:&nbsp;<c:out value="${group.name }"/><br/>
				&nbsp;&nbsp;&nbsp;&nbsp;创建者:&nbsp;<c:out value="${group.ownerName }"/><br/>
				&nbsp;&nbsp;&nbsp;&nbsp;成员数:&nbsp;<c:out value="${group.memberCount }"/><br/>
				&nbsp;&nbsp;&nbsp;&nbsp;组简介:&nbsp;<c:out value="${group.introduction }"/><br/>
			</div>
			<div style="clear:both"></div>
		</div>
		<div id="collapseTool" class="collapse in collapseDish">
			<button class="btn btn-danger" style="margin-left:10px;" onclick="goback()">&lt;&lt;&nbsp;返回</button>
			<c:if test="${group.ownerId eq sessionScope.user_id }">
				<button class="btn btn-success" style="margin-left:20px;" data-toggle="collapse" data-target=".collapseDish">添加成员</button>
			</c:if>
		</div>
		<div id="collapseCreateDish" class="collapse collapseDish">
		 	Coming soon... <a class="btn btn-small offset1" data-toggle="collapse" data-target=".collapseDish">取消</a>
		</div>
		
		<hr/>
		<aa:zone name="memberList">
		<div id="memberList">
			<div class="alert alert-error" style="margin-left:20px">
				创建者:&nbsp;&nbsp;${group.ownerName }
			</div>
			<c:forEach items="${memberList }" var="user" varStatus="status">
				<div class="alert alert-success" style="margin-left:20px" onmouseover="showTool(this)" onmouseout="hideTool(this)">
					组成员:&nbsp;&nbsp;${user.nickname }
					<div style="display:none;float:right;cursor:pointer;">
						<c:if test="${group.ownerId eq sessionScope.user_id}">
							<i class="icon-ban-circle" style="padding-right:5px;" title="移除成员" onclick="deleteUser('${user.id}')"></i>
						</c:if>
					</div>
					<hr/>
				</div>
			</c:forEach>
		</div>
		</aa:zone>
		
	</div>

	<div class="modal fade" id="confirmModal">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h5 id="confirmTip"></h5>
		</div>
		<div class="modal-body">
			<button class="btn btn-danger" data-dismiss="modal" style="margin-right:20px;" id="confirmBtn">确定</button>
			<button class="btn" data-dismiss="modal">取消</button>
		</div>
	</div>



</body>
</html>