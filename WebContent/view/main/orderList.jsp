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
    	var statisticsChanged = false;
	    $(document).ready(function(){
			$("#mainContent").slideDown("fast");
			$("#confirmModal").modal({
    		    backdrop: true,
    		    keyboard: true,
    		    show: false
    		});
			dealTotalPrice();
	    });
	    //$(window).bind("scroll", function(){ });
	    
	    function dealTotalPrice(){
	    	var totalPrice = 0;
	    	$(".totalPrice").each(function(){
	    		totalPrice = J.plus(totalPrice, $(this).html()).toFixed(2);
	    	});
	    	$("#totalPriceBody").append("<tr><td colSpan=\"6\"></td><td>" + totalPrice + "</td></tr>");
	    }
	    
	    function changeStatistics(){
	    	if(!statisticsChanged){
	    		showOrderStatistics();
	    		statisticsChanged = true;
	    	}else{
	    		recoverTable();
	    		statisticsChanged = false;
	    	}
	    }
	    
	    function showOrderStatistics(){
	    	var statistics = {};
	    	$("#orderListBody tr").each(function(){
	    		var tds = $(this).children("td");
    			var no = $(tds[0]).html();
    			var user = $(tds[1]).html();
    			var restaurant = $(tds[2]).html();
    			var dish = $(tds[3]).html();
    			var count = $(tds[4]).html();
    			var price = $(tds[5]).html();
    			var totalPrice = $(tds[6]).html();
    			var order = {
    				"user":user,
    				"restaurant":restaurant,
    				"dish":dish,
    				"count":count,
    				"price":price,
    				"totalPrice":totalPrice
    			};
    			if(statistics[restaurant]){
    				if(statistics[restaurant][dish]){
	    				statistics[restaurant][dish].user += ", "+user;
	    				statistics[restaurant][dish].count = J.plus(statistics[restaurant][dish].count, count);
	    				statistics[restaurant][dish].totalPrice = J.plus(statistics[restaurant][dish].totalPrice, totalPrice).toFixed(2);
    				}else{
    					statistics[restaurant][dish] = order;
    				}
    			}else{
    				statistics[restaurant] = {};
    				statistics[restaurant][dish] = order;
    				//eval("statistics = $.extend({}, statistics, {'" + statistics + "':order})");
    			}
	    	});
//	    	alert(decodeURIComponent($.param(statistics)));
			hideTr($("#orderListBody tr"), $("#orderListBody tr").length-1, false, function(){
				var index = 1;
				for(var restaurant in statistics){
					for(var dish in statistics[restaurant]){
						var tr = "<tr class=\"success hiddenTr\">";
						tr += "<td>" + index++ + "</td>";
						tr += "<td>" + statistics[restaurant][dish].user + "</td>";
						tr += "<td>" + statistics[restaurant][dish].restaurant + "</td>";
						tr += "<td>" + statistics[restaurant][dish].dish + "</td>";
						tr += "<td>" + statistics[restaurant][dish].count + "</td>";
						tr += "<td>" + statistics[restaurant][dish].price + "</td>";
						tr += "<td>" + statistics[restaurant][dish].totalPrice + "</td>";
						tr += "</tr>";
						$("#orderListBody").append(tr);
					}
				}
				showTr($("tr.hiddenTr"), $("tr.hiddenTr").length-1);
			});

	    }
	    
	    function recoverTable(){
	    	var hiddenTrs = $("tr.hiddenTr");
	    	var orginTrs = $("tr.orginTr");
	    	hideTr(hiddenTrs, hiddenTrs.length-1, true, function(){
		    	showTr(orginTrs, orginTrs.length-1);
	    	});
	    }
	    
	    function hideTr(trs, index, needRemove, callback){
	    	$(trs[index]).hide("fast",function(){
				if(needRemove){
					$(this).remove();
				}
	    		if(index>0){
	    			hideTr(trs, index-1, needRemove, callback);
	    		}else{
	    			if(callback){
	    				callback.call();
	    			}
	    		}
	    	});
	    }
	    
	    function showTr(trs, index){
	    	$(trs[index]).show("fast",function(){
	    		if(index>0){
	    			showTr(trs, index-1);
	    		}
	    	});
	    }
	    
	    function getPostion(id){
            var  $$ = function(id){ return document.getElementById(id); },
                 pos_ = { left: $$(id).offsetLeft, top: $$(id).offsetTop } ,            
                 win_ = window;
             while(win_.frameElement) {
                 var left_ = win_.frameElement.offsetLeft + (win_.frameElement.offsetWidth - win_.frameElement.clientWidth) / 2,
                     top_ = win_.frameElement.offsetTop + (win_.frameElement.offsetHeight - win_.frameElement.clientHeight) / 2;
                 
                 pos_.left += win_.frameElement.frameBorder == "1" ? left_ + 2 : left_;
                 pos_.top += win_.frameElement.frameBorder == "1" ? top_ + 2 : top_;
                 win_ = win_.parent;
             }
             return pos_;
		}

	    function goback(){
    		$("#mainContent").slideUp("fast", function(){
	    		window.location.href="${param.back}";
    		});
    	}
	    function cancelProvide(orderProviderId, isConfirm){
	    	$("#confirmBtn").unbind("click");
   			if(isConfirm){
		    	var url = "ajax_cancelProvide";
		    	var params = {"orderProviderId":orderProviderId};
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
   				$("#confirmTip").html("确定取消么？");
   				$("#confirmModal").modal("show");
	    		$("#confirmBtn").click(function(){
	    			cancelProvide(orderProviderId, true);
	    		});
   			}
	    }
	    function finishProvide(orderProviderId, isConfirm){
	    	$("#confirmBtn").unbind("click");
   			if(isConfirm){
		    	var url = "ajax_finishProvide";
		    	var params = {"orderProviderId":orderProviderId};
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
   				$("#confirmTip").html("确定结束么？");
   				$("#confirmModal").modal("show");
	    		$("#confirmBtn").click(function(){
	    			finishProvide(orderProviderId, true);
	    		});
   			}
	    }
    </script>
    <style type="text/css">
    	.hiddenTr{
    		display:none;
    	}
    	.orginTr{
    		
    	}
    </style>
</head>
<body>
	<div id="mainContent" style="display:none;margin-left:20px;">
		<button class="btn btn-danger" style="margin-left:10px;" onclick="goback()">&lt;&lt;&nbsp;返回</button>
		<c:if test="${orderProvider.provideUserId eq sessionScope.user_id &&  orderProvider.status eq 1}"><!-- TODO -->
			<button class="btn btn-warning" style="margin-left:20px;" onclick="cancelProvide('${orderProvider.id}')">取消订餐</button>
			<button class="btn btn-success" style="margin-left:20px;" onclick="finishProvide('${orderProvider.id}')">结束订餐</button>
		</c:if>
		<button class="btn btn-info" style="margin-left:20px;" onclick="changeStatistics()">改变统计形式</button>
		<hr/>
		<div >
		<table class="table table-bordered table-hover" id="orderList">
			<thead>
			<tr id="headTr">
				<th width="8%"><li class="icon-glass"></li></th>
				<th width="20%">订餐人</th>
				<th width="20%">餐馆</th>
				<th width="20%">餐品</th>
				<th width="8%">数量</th>
				<th width="12%">单价(元)</th>
				<th width="12%">总价(元)</th>
			</tr>
			</thead>
			<tbody id="orderListBody">
				<c:forEach items="${orderList }" var="order" varStatus="status">
				<tr class="success orginTr">
					<td>${status.index+1}</td>
					<td>${order.userName}</td>
					<td>${order.restaurantName}<span style="display:none">${order.restaurantId}</span></td>
					<td>${order.dishName}<span style="display:none">${order.dishId}</span></td>
					<td>${order.dishNum}</td>
					<td>${order.dishPrice}</td>
					<td class="totalPrice"><fmt:formatNumber value="${order.dishPrice * order.dishNum }" pattern="#.##" minFractionDigits="2" /></td>
				</tr>
				</c:forEach>
			</tbody>
			<tbody id="totalPriceBody">
			</tbody>
		</table>
		</div>
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