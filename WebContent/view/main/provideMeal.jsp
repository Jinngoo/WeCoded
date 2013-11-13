<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ProvideMeal</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/popoverButton.css" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript">
    	var choosenGroups = new Array();
    	var choosenRestaurants = new Array();
    	var choosenUsers = new Array();
    	Array.prototype.toggle = function(value){
    		var deleteIndex = null;
    		for(var i = 0; i < this.length; i ++){
    			if(this[i] == value){
    				deleteIndex = i;
    				break;
    			}
    		}
    		if(deleteIndex == null){//not exist, should insert
    			this.push(value);
    		}else{//exist, should delete
    			this.splice(i, 1);
    		}
    	}
    	$(document).ready(function(){
    		$("#mainContent").slideDown("fast");
    		
    		$("[id^=group_]").click(function(){
    			$(this).find("i[class='icon-ok']").toggle();
    			var groupId = $(this).attr("id").substring("group_".length);
    			choosenGroups.toggle(groupId);
    			$("#groupCount").html(choosenGroups.length);
    		});
    		
    		$("[id^=restaurant_]").click(function(){
    			$(this).find("i[class='icon-ok']").toggle();
    			var groupId = $(this).attr("id").substring("restaurant_".length);
    			choosenRestaurants.toggle(groupId);
    			$("#restaurantCount").html(choosenRestaurants.length);
    		});
    		
    		$("#confirmModal").modal({
    		    backdrop: true,
    		    keyboard: true,
    		    show: false
    		});
    		
    	});
    	function goback(){
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href="${param.back}";
    		});
    	}
    	function confirmOffer(isConfirm){
    		$("#confirmBtn").unbind("click");
    		if(choosenGroups.length == 0 || choosenRestaurants.length == 0){
    			$("#confirmTip").html("请至少选择一个小组和一个餐馆");
    			isConfirm = false;
    			$("#cancelBtn").hide();
    		}else{
    			$("#confirmTip").html("确定发起么?");
    			$("#cancelBtn").show();
    		}
    		if(isConfirm){
    			var url = 'ajax_provideMeal';
    			var params = {"choosenGroups":choosenGroups.join(","),"choosenRestaurants":choosenRestaurants.join(",")};
    			$.post(url, params, function(data, textStatus, jqXHR){
    				var code = data.result["code"];
    				if(code == "success"){
    					goback();
    				}else if(code == "error"){
    					alert("An error occured");
    				}else{
    					alert("Unknown error occured");
    				}
    			});
    		}else{
    			$("#confirmModal").modal("show");
	    		$("#confirmBtn").click(function(){
	    			confirmOffer(true);
	    		});
    		}
    	}
    </script>
    <style type="text/css">
    </style>
</head>
<body>

	<div id="mainContent" style="display:none;margin-left:20px;">
		
		<button class="btn btn-danger" style="margin-left:10px;" onclick="goback()">&lt;&lt;&nbsp;取消</button>
		<button class="btn btn-success" style="margin-left:20px;" onclick="confirmOffer(false)">发起订餐</button>
		
		<!-- choose group -->
		<div>
			<hr/>
			<div>
				已选择&nbsp;<span id="groupCount">0</span>&nbsp;个小组
			</div>
			<div style="margin-left:10px;margin-top:20px;">
				<c:forEach items="${myGroupList }" var="group" varStatus="status">
					<!-- Group button -->
					<jinva:PopoverButton id="group_${group.id}" style="cursor:pointer;"  popoverTitle="[${group.name}]" content="${group.name}" popoverContent="创建者:&nbsp;&nbsp;${group.ownerName}<br>成员数:&nbsp;&nbsp;${group.memberCount}<br>组简介:&nbsp;&nbsp;${group.introduction}" imgUrl="${pageContext.request.contextPath}/image/group.jpg" >
						<i class="icon-ok" title="已经选择" style="display:none"></i>
					</jinva:PopoverButton>
				</c:forEach>
				<c:forEach items="${joinedGroupList }" var="group" varStatus="status">
					<!-- Group button -->
					<jinva:PopoverButton id="group_${group.id}" style="cursor:pointer;"  popoverTitle="[${group.name}]" content="${group.name}" popoverContent="创建者:&nbsp;&nbsp;${group.ownerName}<br>成员数:&nbsp;&nbsp;${group.memberCount}<br>组简介:&nbsp;&nbsp;${group.introduction}" imgUrl="${pageContext.request.contextPath}/image/group.jpg" >
						<i class="icon-ok" title="已经选择" style="display:none"></i>
					</jinva:PopoverButton>
				</c:forEach>
			</div>
		</div>
		<!-- choose restaurant -->
		<div>
			<hr/>
			<div>
				已选择&nbsp;<span id="restaurantCount">0</span>&nbsp;个餐馆
			</div>
			<div style="margin-left:10px;margin-top:20px;">
				<c:forEach items="${myRestaurantList }" var="restaurant" varStatus="status">
					<!-- Group button -->
					<jinva:PopoverButton id="restaurant_${restaurant.id}" style="cursor:pointer;"  popoverTitle="[${restaurant.name}]" content="${restaurant.name}" popoverContent="创建者:&nbsp;&nbsp;${restaurant.ownerName}<br>店简介:&nbsp;&nbsp;${restaurant.introduction}" imgUrl="${pageContext.request.contextPath}/image/touxiang.jpg" >
						<i class="icon-ok" title="已经选择" style="display:none"></i>
					</jinva:PopoverButton>
				</c:forEach>
			</div>
		</div>
		
		<hr/>
		<button class="btn btn-success" style="margin-top:20px;margin-left:104px;" onclick="confirmOffer(false)">发起订餐</button>
		
	</div>
	
	<div class="modal fade" id="confirmModal">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h5 id="confirmTip"></h5>
		</div>
		<div class="modal-body">
			<button class="btn btn-danger" data-dismiss="modal" style="margin-right:20px;" id="confirmBtn">确定</button>
			<button class="btn" data-dismiss="modal" id="cancelBtn">取消</button>
		</div>
	</div>



</body>
</html>