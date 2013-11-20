<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>ProvideMeal</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	
	<link href="${RESOURCE}/css/custom/popoverButton.css" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
	<script type="text/javascript" src="${RESOURCE}/js/custom/popoverButton.js"></script>
	
    <script type="text/javascript">
    	var choosenTeams = new Array();
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
    		
    		$("[id^=team_]").click(function(){
    			$(this).find("i[class='icon-ok']").toggle();
    			var teamId = $(this).attr("id").substring("team_".length);
    			choosenTeams.toggle(teamId);
    			$("#groupCount").html(choosenTeams.length);
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
    	function goback(backUrl){
    	    if(!backUrl){
    	        backUrl = $("#goBack").attr("backUrl");
    	    }
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href = decodeURIComponent(backUrl);
    		});
    	}
    	function confirmOffer(isConfirm){
    		$("#confirmBtn").unbind("click");
    		if(choosenTeams.length == 0 || choosenRestaurants.length == 0){
    			$("#confirmTip").html("请至少选择一个小组和一个餐馆");
    			isConfirm = false;
    			$("#cancelBtn").hide();
    		}else{
    			$("#confirmTip").html("确定发起么?");
    			$("#cancelBtn").show();
    		}
    		if(isConfirm){
    			var url = contextPath + "/dining/provideMeal";
    			var params = {"choosenTeams":choosenTeams.join(","),"choosenRestaurants":choosenRestaurants.join(",")};
    			$.post(url, params, function(result, textStatus, jqXHR){
    				var code = result["code"];
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
	<%@ include file="../nav_top.jsp" %>
	<div class="container">
		<div id="mainContent" style="display:none;margin-left:20px;">
			
			<button class="btn btn-danger" style="margin-left:10px;" id="goBack" backUrl="${backUrl}" onclick="goback('${backUrl}')">&lt;&lt;&nbsp;取消</button>
			<button class="btn btn-success" style="margin-left:20px;" onclick="confirmOffer(false)">确认发起</button>
			
			<!-- choose group -->
			<div>
				<hr/>
				<div>已选择&nbsp;<span id="groupCount">0</span>&nbsp;个小组</div>
				<div style="margin-left:10px;margin-top:20px;">
					<c:forEach items="${teamList }" var="team" varStatus="status">
						<!-- Group button -->
						<jn:PopoverButton id="team_${team.id}" style="cursor:pointer;"  popoverTitle="[${team.name}]" content="${team.name}" popoverContent="创建者:&nbsp;&nbsp;${team.ownerName}<br>成员数:&nbsp;&nbsp;${team.memberCount}<br>组简介:&nbsp;&nbsp;${team.introduction}" imgUrl="${CONTEXT_PATH}/getImage/2/${team.id}" >
							<i class="icon-ok" title="已经选择" style="display:none"></i>
						</jn:PopoverButton>
					</c:forEach>
				</div>
			</div>
			<!-- choose restaurant -->
			<div>
				<hr/>
				<div>已选择&nbsp;<span id="restaurantCount">0</span>&nbsp;个餐馆</div>
				<div style="margin-left:10px;margin-top:20px;">
					<c:forEach items="${restaurantList }" var="restaurant" varStatus="status">
						<!-- Group button -->
						<jn:PopoverButton id="restaurant_${restaurant.id}" style="cursor:pointer;"  popoverTitle="[${restaurant.name}]" content="${restaurant.name}" popoverContent="创建者:&nbsp;&nbsp;${restaurant.ownerName}<br>店简介:&nbsp;&nbsp;${restaurant.introduction}" imgUrl="${CONTEXT_PATH}/getImage/3/${restaurant.id}" >
							<i class="icon-ok" title="已经选择" style="display:none"></i>
						</jn:PopoverButton>
					</c:forEach>
				</div>
				<div>//TODO 加入其它的餐馆</div>
			</div>
			
			<hr/>
			<button class="btn btn-success" style="margin-top:20px;margin-left:104px;" onclick="confirmOffer(false)">确认发起</button>
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
					<button class="btn btn-default" data-dismiss="modal" id="cancelBtn">取消</button>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->



</body>
</html>