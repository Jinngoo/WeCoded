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
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
	
    <script type="text/javascript">
    	var dishJson = {};
	    $(document).ready(function(){
			$("#mainContent").slideDown("fast");
			initOrderList();
			refreshChooseList();
	    });
	    function goback(backUrl){
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href = decodeURIComponent(backUrl);
    		});
    	}
	    function initOrderList(){
	    	<c:forEach items="${orderList}" var="order">
				plusDish("${order.dishId}", ${order.dishNum}, false);
			</c:forEach>
	    }
	    
	    function plusDish(dishId, dishNum, needRefresh){
	    	if(J.isEmpty(dishNum)){
	    		dishNum = 1;
	    	}
	    	var countBar = $("#dish_" + dishId).children("div.count_bar");
	    	var countSpan = countBar.children("span");
	    	var count = countSpan.html();
	    	if(J.isEmpty(count)){
	    		count = 0;
	    	}
	    	count = parseInt(count, 10) + parseInt(dishNum,10);
	    	if(parseInt(count, 10) > 0){
		    	var minusBtn = $("#dish_" + dishId).children("div.tool_bar").children("i:first");
	    		countBar.show();
	    		minusBtn.show();
	    	}
	    	eval("dishJson = $.extend({}, dishJson, {'" + dishId + "':count});");
	    	countSpan.html(count);
	    	if(needRefresh){
	    		refreshChooseList();
	    	}
	    }
	    
	    function minusDish(dishId, dishNum, needRefresh){
	    	if(J.isEmpty(dishNum)){
	    		dishNum = 1;
	    	}
	    	var countBar = $("#dish_" + dishId).children("div.count_bar");
	    	var countSpan = countBar.children("span");
	    	var count = countSpan.html();
	    	if(J.isEmpty(count)){
	    		return;
	    	}
	    	count = parseInt(count, 10) - parseInt(dishNum,10);
	    	if(count <= 0){
		    	var minusBtn = $("#dish_" + dishId).children("div.tool_bar").children("i:first");
	    		countBar.hide();
	    		minusBtn.hide();
	    	}
    		eval("dishJson = $.extend({}, dishJson, {'" + dishId + "':count});");
	    	countSpan.html(count);
	    	if(needRefresh){
	    		refreshChooseList();
	    	}
	    }
	    
	    function refreshChooseList(){
	    	var html = "";
	    	var height = 0;
	    	var totalPrice = 0;
	    	for(var dishId in dishJson){
	    		var count = dishJson[dishId];
	    		if(count == 0){
	    			continue;
	    		}
	    		var name = $("#dishName_" + dishId).val();
	    		var price = $("#dishPrice_" + dishId).val();
	    		var thisPrice = Math.round(price*count*100)/100;
		    	html += "<div style=\"display:block;font-size:12px;\">" + name + "&nbsp;:&nbsp;" + count + "&nbsp;份";
		    	html += "&nbsp;&nbsp;-&nbsp;&nbsp;花费&nbsp;:&nbsp;" + price + "&nbspx&nbsp" + count + "&nbsp=&nbsp" + thisPrice + "&nbsp;元";
		    	html += "</div>";
		    	height += 20;
		    	totalPrice += thisPrice;
		    	totalPrice = Math.round(totalPrice*100)/100;
	    	}
	    	html = "<div style=\"font-size:14px;\">我共选择了&nbsp;:&nbsp;" + totalPrice + "&nbsp;元</div>" + html;
	    	html += "<hr/>";
	    	$("#iChoosed").html(html);
	    	$("#iChoosed").animate({height:60+height+"px"});
	    }
	    function submitOrder(){
	    	var url = contextPath + "/dining/submitOrder";
    		var params = {"dishJson":$.param(dishJson), "orderProviderId":"${orderProviderId}"};
    		$.post(url, params, function(data, textStatus, jqXHR){
    			var code = data;
    			if(code == "success"){
    			    $("#tip").html("下单成功！");
    			    $("#tipModal").modal("show");
    			    setTimeout(function(){
    			        $("#tipModal").modal("hide");
    			    	window.location.href = window.location.href;
    			    }, 1000);
				}else if(code == "error"){
					alert("An error occured");
				}else{
					alert("Unknown error occured");
				}
    		});
	    }
    </script>
    <style type="text/css">
    	.opacityBar{
    		background-color: #00CC66;
			opacity: 0.7;
			-moz-opacity: 0.7;
			filter: alpha(Opacity = 70);
			padding-left: 5px;
			padding-right: 5px;
			margin-top: 5px;
			margin-left: 5px;
			margin-right: 5px;
			cursor: pointer;
			display: none;
    	}
    	.count_bar {
			position: absolute;
			left: 0px;
			top: 0px;
			font-weight: bold;
		}
		.tool_bar {
			position: absolute;
			right: 0px;
			top: 0px;
		}
    	.tool_img{
    		margin-left: 1px;
    		margin-right: 1px;
    	}
    </style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<div class="container">
		<div id="mainContent" style="display:none;margin-left:20px;">
		
			<button class="btn btn-danger" style="margin-left:10px;" onclick="goback('${backUrl}')">&lt;&lt;&nbsp;返回</button>
			<button class="btn btn-success" style="margin-left:20px;" onclick="submitOrder()">
				<c:choose>
					<c:when test="${empty orderList }">oye下单!</c:when>
					<c:otherwise>修改订单</c:otherwise>
				</c:choose>
			</button>
			<hr/>
			<div id="iChoosed" class="alert alert-success" style="height:0px">
				
			</div>
		
			<c:forEach items="${restaurantDishMap }" var="entry" varStatus="status">
				<div class="well">
					<div>&nbsp;&nbsp;&nbsp;店名：${restaurantMap[entry.key].name }</div>
					<c:forEach items="${entry.value }" var="dish" varStatus="statusDish">
						<a id="dish_${dish.id}" class="btn btn-warning" style="padding:5px;margin-left:7px;margin-top:5px;cursor:default;position:relative">
							<input type="text" id="dishName_${dish.id}" value="${dish.name}" style="display:none">
							<input type="text" id="dishPrice_${dish.id}" value="${dish.price}" style="display:none">
							<div class="count_bar opacityBar">
								x&nbsp;<span></span>
							</div>
							<div class="tool_bar opacityBar">
								<i class="icon-minus icon-white tool_img" title="减一份" onclick="minusDish('${dish.id}', 1, true)" style="display:none"></i>
								<i class="icon-plus icon-white tool_img" title="订一份" onclick="plusDish('${dish.id}', 1, true)"></i>
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
			</c:forEach>
			<script>
				$("a.btn-warning").mouseover(function(){
		    		$(this).find("div.tool_bar").show();
				});
				$("a.btn-warning").mouseout(function(){
		    		$(this).find("div.tool_bar").hide();
				});
			</script>
		
		</div>
	</div>
	
	<div class="modal fade" id="tipModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<h4 id="tip"></h4>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->
	
</body>
</html>