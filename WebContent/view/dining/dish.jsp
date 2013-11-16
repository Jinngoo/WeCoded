<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dish</title>
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
	
    <script type="text/javascript" src="${JQUERY_FORM}"></script>  
    <script type="text/javascript" src="${JQUERY_UPLOAD}"></script>  
    <script type="text/javascript" src="${RESOURCE}/js/util/jinva.js"></script>
    
    <script type="text/javascript">
    	$(document).ready(function(){
    		$("#mainContent").slideDown("fast");
    		
    		$("#createDishBtn").click(function(){
        		var nameValid = $("#dish_name").get(0).checkValidity();
        		var priveValid = $("#dish_price").get(0).checkValidity();
        		if(!nameValid || !priveValid){ //一个不合法就点击提交，触发html5的表单检验提示
	        		$("#invalidSubmit").click();
	        		return;
        		}
        		
        		$("#dish_restaurantId").val('${restaurant.id}');
        		ajaxAnywhere.formName = "dish_form";
        		AjaxAnywhere.prototype.getZonesToReaload = function(){
        			return "dishList";
        		}
        		AjaxAnywhere.prototype.showLoadingMessage = function() {
        			$("#createDishBtn").button("loading");
        		};
        		AjaxAnywhere.prototype.hideLoadingMessage = function() {
        			$("#createDishBtn").button("reset");
        			$("#collapseTool").collapse('toggle');
            		$("#collapseCreateDish").collapse('toggle');
        		};
        		ajaxAnywhere.submitAJAX();
        	});
    		
    		$("#dish_name").blur(function(){
    			$("#dish_name_group").removeClass("error");
	    	});
	    	$("#collapseCreateDish").on("hidden.bs.collapse", function () {
	    		J.clearForm("dish_form");
	    	});
    	});
    	function goback(backUrl){
    	    if(!backUrl){
    	        backUrl = $("#goBack").attr("backUrl");
    	    }
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href = decodeURIComponent(backUrl);
    		});
    	}
    	
    	function changePicture(dishId){
//     		$.upload({
//     			// 上传地址
//     			url: 'ajaxUpload', 
//     			// 文件域名字
//     			fileName: 'file', 
//     			// 其他表单数据
//     			params: {dishId: dishId,type:4},
//     			// 上传完成后, 返回json, text
//     			dataType: 'text',
//     			// 上传之前回调,return true表示可继续上传
//     			onSend: function() {
//     					return true;
//     			},
//     			// 上传之后回调
//     			onComplate: function(data) {
//     				alert(99)
//         			var url = "aa_dishList?restaurantId=${restaurant.id}";
//             		ajaxAnywhere.formName = "";
//             		AjaxAnywhere.prototype.getZonesToReaload = function(){
//             			return "dishList";
//             		}
//             		AjaxAnywhere.prototype.showLoadingMessage = function() {};
//             		AjaxAnywhere.prototype.hideLoadingMessage = function() {};
//             		ajaxAnywhere.getAJAX(url);
//     			}
//     		});
//     		return;
    		$("#uploadImgPanel").find("input[name='dishId']").val(dishId);
    		$("#uploadImgPanel").find("input[name='file']").val(null);
    		$("#uploadImgPanel").collapse('show');
    		top.$('html,body').animate({scrollTop: 0}, '1000');
    	}
    	function upload(){
    		var form = $('#photoForm').ajaxSubmit({  });
    		var xhr = form.data('jqxhr');
    		xhr.done(function() {
    			$("#uploadImgPanel").collapse('hide');
    			var url = contextPath + "/aa_dishList?restaurantId=${restaurant.id}";
        		ajaxAnywhere.formName = "";
        		AjaxAnywhere.prototype.getZonesToReaload = function(){
        			return "dishList";
        		}
        		AjaxAnywhere.prototype.showLoadingMessage = function() {};
        		AjaxAnywhere.prototype.hideLoadingMessage = function() {};
        		ajaxAnywhere.getAJAX(url);
    		});
    	}
    	function editDish(dishId){
    		var url = contextPath + "/dining/loadDish/" + dishId;
    		$.get(url, {}, function(data, textStatus, jqXHR){
    			var dish = data;
    			if(dish == null){
    				alert("Dish not exist");
    			}else{
    				J.fillForm(dish, "dish");
    				$("#collapseTool").collapse('toggle');
            		$("#collapseCreateDish").collapse('toggle');
            		//equals $(".collapseDish").collapse('toggle');
    			}
    		});
    	}
    	function deleteDish(dishId){
    		$("#toDeleteId").val(dishId);
    		$('#deleteConfirmModal').modal({
    		    backdrop: true,
    		    keyboard: true,
    		    show: true
    		});
    	}
    	function deleteConfirm(){
    		var url = contextPath + "/dining/deleteDish/" + $("#toDeleteId").val();
    		ajaxAnywhere.formName = "";
    		AjaxAnywhere.prototype.getZonesToReaload = function(){
    			return "dishList";
    		}
    		AjaxAnywhere.prototype.showLoadingMessage = function() {};
    		AjaxAnywhere.prototype.hideLoadingMessage = function() {};
    		ajaxAnywhere.getAJAX(url);
    	}
    	function validatePrice(obj){
			if (!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/)){
				obj.value = obj.t_value;
			}else{
				obj.t_value = obj.value;
			}
			if (obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)){
				obj.o_value = obj.value;
			}
			if(obj.value.length > 0 && obj.value.indexOf(".") != -1){
				if(obj.value.length - (obj.value.indexOf(".")+1) > 2){
					obj.value = obj.value.substring(0, obj.value.indexOf(".")+3);
				}
			}
			//obj.value = Math.round(obj.value*100)/100;
		}
    	function copy(restaurantId){
    		var url = contextPath + "/dining/copyRestaurant/" + restaurantId;
    		$.get(url, {}, function(data, textStatus, jqXHR){
    			var code = data;
    			if(code == "success"){
    				goback();
    			}else if(code == "error"){
    				alert(data.result["message"]);
    			}else{
    				alert("Copy fail");
    			}
    		});
    	}
    	function showToolBar(popover){
		    $(popover).children("div.tool_bar").show();
		}
    	function hideToolBar(popover){
		    $(popover).children("div.tool_bar").hide();
		}
	</script>
    <style type="text/css">
	    .tool_bar{
	    	position:absolute;right:0px;top:0px;background-color:#777777;opacity:0.6;-moz-opacity:0.6;filter:alpha(Opacity=60);padding-left:5px;padding-right:5px;margin-top:5px;margin-right:5px;cursor:pointer;display:none;
	    }
    	.tool_img{
    		margin-left: 1px;
    		margin-right: 1px;
    	}
    </style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>

	<div id="mainContent" style="display:none;margin-left:20px;">
		
		<div class="well">
			<div style="float:left">
				<img src="${CONTEXT_PATH}/getImage/3/${restaurant.id}" style="width:100px;height:100px" />
			</div>
			<div style="float:left;margin-left:20px;">
				&nbsp;&nbsp;&nbsp;&nbsp;店名:&nbsp;<c:out value="${restaurant.name }"/><br/>
				创建者:&nbsp;<c:out value="${restaurant.ownerName }"/><br/>
				&nbsp;&nbsp;&nbsp;&nbsp;简介:&nbsp;<c:out value="${restaurant.introduction }"/><br/>
			</div>
			<div style="clear:both"></div>
		</div>
		
		<!-- Toolbar -->
		<div id="collapseTool" class="collapse in collapseDish">
			<button id="goBack" class="btn btn-danger" style="margin-left:10px;" backUrl="${requestScope.backUrl}" onclick="goback('${requestScope.backUrl}')">&lt;&lt;&nbsp;返回</button>
			<c:if test="${restaurant.ownerId eq sessionScope.user.id }">
				<button class="btn btn-success" style="margin-left:20px;" data-toggle="collapse" data-target=".collapseDish">添加菜单</button>
			</c:if>
			<c:if test="${restaurant.ownerId ne sessionScope.user.id }">
				<button class="btn btn-primary" style="margin-left:20px;" onclick="copy('${restaurant.id}')">给我拷一份</button>
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
				<a class="btn btn-warning" style="padding:5px;margin-left:7px;margin-top:5px;cursor:default;position:relative" 
						data-content="haha<br>aaa" data-trigger="hover" data-title="oye" data-placement="bottom" rel="popover"
						onmouseover="showToolBar(this)" onmouseout="hideToolBar(this)" >
					<div class="tool_bar">
						<c:if test="${restaurant.ownerId eq sessionScope.user.id }">
							<i class="icon-picture icon-white tool_img" title="更改图片" onclick="changePicture('${dish.id}')"></i>
							<i class="icon-wrench icon-white tool_img" title="编辑" onclick="editDish('${dish.id}')"></i>
							<i class="icon-remove icon-white tool_img" title="删除" onclick="deleteDish('${dish.id}')"></i>
						</c:if>
					</div>
					<div style="width:130px;height:130px">
						<img src="${CONTEXT_PATH}/getImage/4/${dish.id}" style="width:130px;height:130px" />
					</div>
					<div style="width:130px;height:20px;font-size:12px;background-color:#cc2222">
						<c:out value="${dish.price }"/>元
					</div>
					<div style="width:130px;height:20px;font-size:12px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" title="${dish.name }">
						<c:out value="${dish.name }"/>
					</div>
				</a>
			</c:forEach>
		</div>
		</aa:zone>
		
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