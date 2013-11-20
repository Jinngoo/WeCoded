<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>菜单</title>
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
	
    <script type="text/javascript" src="${JQUERY_FORM}"></script>  
    <script type="text/javascript" src="${JQUERY_UPLOAD}"></script>  
    <script type="text/javascript" src="${JN_UTIL}"></script>
    <script type="text/javascript" src="${RESOURCE}/js/custom/popoverButton.js"></script>
    <script type="text/javascript" src="${RESOURCE}/js/dining/menu.js"></script>
    
    <style type="text/css">
	    .tool_bar{
	    	position:absolute;
	    	right:0px;
	    	top:0px;
	    	background-color:#00CC66;
	    	opacity:0.6;
	    	-moz-opacity:0.6;
	    	filter:alpha(Opacity=60);
	    	padding-left:5px;
	    	padding-right:5px;
	    	margin-top:5px;
	    	margin-right:5px;
	    	cursor:pointer;
	    	display:none;
	    }
    	.tool_img{
    		margin-left: 1px;
    		margin-right: 1px;
    	}
    </style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<c:set var="iamOwner" value="${restaurant.ownerId eq sessionScope.user.id }" />
	<c:set var="canEdit" value="${iamOwner or restaurant.belong eq 2 }"/>
	
	<div class="container">
		<div id="mainContent" style="display:none;margin-left:20px;">
			<div class="well">
				<div style="float:left">
					<c:if test="${canEdit}"><a href="${CONTEXT_PATH}/tool/uploadImage/3/${restaurant.id}?callback=reloadAvatar&close=1" target="_blank" title="换头像"></c:if>
						<img id="restaurantAvatar" class="shadow" src="${CONTEXT_PATH}/getImage/3/${restaurant.id}" style="width:100px;height:100px" />
					<c:if test="${canEdit}"></a></c:if>
				</div>
				<div style="float:left;margin-left:20px;">
					&nbsp;&nbsp;&nbsp;&nbsp;店名：&nbsp;<c:out value="${restaurant.name }"/><br/>
					创建者：&nbsp;<c:out value="${restaurant.ownerName }"/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;电话：&nbsp;<c:out value="${restaurant.telphone }"/><br/>
					&nbsp;&nbsp;&nbsp;&nbsp;简介：&nbsp;<c:out value="${restaurant.introduction }"/><br/>
				</div>
				<div style="clear:both"></div>
			</div>
			
			<!-- Toolbar -->
			<div id="collapseTool" class="collapse in collapseDish">
				<button id="goBack" class="btn btn-danger" style="margin-left:10px;" backUrl="${backUrl}" onclick="goback('${backUrl}')">&lt;&lt;&nbsp;返回</button>
				<c:if test="${canEdit }">
					<button class="btn btn-success" style="margin-left:20px;" data-toggle="collapse" data-target=".collapseDish">添加菜单</button>
				</c:if>
				<c:if test="${iamOwner }">
					<button class="btn btn-primary" style="margin-left:20px;" onclick="copy('${restaurant.id}')" title="复制一份当前餐馆到&lt;我的餐馆&gt;">给我拷一份</button>
				</c:if>
			</div>
			<!-- Edit form -->
			<div id="collapseCreateDish" class="collapse collapseDish">
				<form class="form-horizontal" id="dish_form" action="${CONTEXT_PATH}/dining/saveDish" method="post" role="form" style="margin-left: 100px; padding-top: 10px;max-width:300px">
					<input type="text" name="dish_id" id="dish_id" style="display:none">
					<input type="text" name="dish_restaurantId" id="dish_restaurantId" style="display:none">
					<input id="invalidSubmit" type="submit" style="display:none"/>
					<div class="form-group" id="dish_name_group">
						<div class="input-group">
							<span class="input-group-addon">名称</span>
							<input id="dish_name" name="dish_name" type="text" class="form-control" required="required"> 
						</div>
						<p class="help-block">字母，数字，汉字皆可</p>
					</div>
					<div class="form-group" id="dish_price_group">
						<div class="input-group">
							<span class="input-group-addon">单价</span>
							<input id="dish_price" name="dish_price" type="number" class="form-control" step="0.01" min="0" required="required" onchange="validatePrice(this)"> 
							<span class="input-group-addon">￥</span>
						</div>
					</div>
					<div class="form-group" style="text-align: right">
					 	<a class="btn btn-primary" id="createDishBtn">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	<a class="btn btn-default" data-toggle="collapse" data-target=".collapseDish">取消</a>
					</div>
				</form>
			</div>
			
			<!-- upload dish photo panel -->
			<div id="uploadImgPanel" class="collapse" style="padding-top:10px">
				<form class="alert" action="ajaxUpload" id="photoForm" enctype="multipart/form-data" method="post">
					<input type="file" name="file" onchange="upload()" class="" style="width:63px;font-size:12px;">
					<input name="type" value="4" style="display:none">
					<input name="dishId" value="" style="display:none">
					<a class="btn " data-toggle="collapse" data-target="#uploadImgPanel">取消</a>
				</form>
			</div>
			<hr/>
			
			<aa:zone name="dishList">
			<div id="dishList" class="well">
				<c:forEach items="${dishList }" var="dish" varStatus="status">
					<div class="btn btn-warning" style="padding:5px;margin-left:7px;margin-top:5px;cursor:default;position:relative" 
							data-content="(${restaurant.name})<br>" data-trigger="hover" data-title="${dish.name }" data-placement="bottom" rel="popover"
							onmouseover="showToolBar(this);initPopover(this)" onmouseout="hideToolBar(this)" >
						<div class="tool_bar">
							<c:if test="${canEdit}">
								<a href="${CONTEXT_PATH}/tool/uploadImage/4/${dish.id}?callback=reloadImage&close=1" target="_blank" style="color:white;text-decoration:none;">
									<i class="icon-picture icon-white tool_img" title="更改图片" onmousedown="markDish('${dish.id}')"></i>
								</a>
								<i class="icon-wrench icon-white tool_img" title="编辑" onclick="editDish('${dish.id}')"></i>
								<i class="icon-remove icon-white tool_img" title="删除" onclick="deleteDish('${dish.id}')"></i>
							</c:if>
						</div>
						<div style="width:130px;height:130px">
							<img id="img_${dish.id}" src="${CONTEXT_PATH}/getImage/4/${dish.id}" style="width:130px;height:130px" />
						</div>
						<div style="width:130px;height:20px;font-size:12px;background-color:#cc2222">
							<c:out value="${dish.price }"/>元
						</div>
						<div style="width:130px;height:20px;font-size:12px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" title="${dish.name }">
							<c:out value="${dish.name }"/>
						</div>
					</div>
				</c:forEach>
			</div>
			</aa:zone>
		</div>
	</div>

	<!-- Dialog -->
	<div class="modal fade" id="deleteConfirmModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h5>确定删除么?</h5>
				</div>
				<div class="modal-body">
					<input type="text" style="display:none" id="toDeleteId">
					<button class="btn btn-danger" data-dismiss="modal" style="margin-right:20px;" onclick="deleteConfirm()">确定</button>
					<button class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->



</body>
</html>