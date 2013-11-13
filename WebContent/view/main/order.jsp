<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dish</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript">
    	var dishJson = {};
	    $(document).ready(function(){
			$("#mainContent").slideDown("fast");
			initOrderList();
			refreshChooseList();
	    });
	    function goback(){
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href="${param.back}";
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
	    function placeOrder(){
	    	var url = 'ajax_placeOrder';
    		var params = {"dishJson":$.param(dishJson), "orderProviderId":"${orderProviderId}"};
    		$.post(url, params, function(data, textStatus, jqXHR){
    			var code = data.result["code"];
    			if(code == "success"){
					window.location.href = window.location.href;
				}else if(code == "error"){
					alert("An error occured");
				}else{
					alert("Unknown error occured");
				}
    		});
	    }
    </script>
    <style type="text/css">
    	.count_bar{
	    	position:absolute;left:0px;top:0px;background-color:#777777;opacity:0.6;-moz-opacity:0.6;filter:alpha(Opacity=60);padding-left:5px;padding-right:5px;margin-top:5px;margin-left:5px;cursor:pointer;display:none;
	    }
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
	<div id="mainContent" style="display:none;margin-left:20px;">
	
		<button class="btn btn-danger" style="margin-left:10px;" onclick="goback()">&lt;&lt;&nbsp;返回</button>
		<button class="btn btn-success" style="margin-left:20px;" onclick="placeOrder()">
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
						<div class="count_bar">
							x&nbsp;<span></span>
						</div>
						<div class="tool_bar">
							<i class="icon-minus icon-white tool_img" title="减一份" onclick="minusDish('${dish.id}', 1, true)" style="display:none"></i>
							<i class="icon-plus icon-white tool_img" title="订一份" onclick="plusDish('${dish.id}', 1, true)"></i>
						</div>
						<div style="width:130px;height:130px">
							<img src="${pageContext.request.contextPath}/image/M10151.jpg" style="width:130px;height:130px" />
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
</body>
</html>