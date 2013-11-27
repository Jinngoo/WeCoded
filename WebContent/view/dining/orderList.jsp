<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>查看订单</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_BUTTONS_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_BUTTONS_JS}"></script>
	
	<script type="text/javascript" src="${JN_UTIL}"></script>
    <script type="text/javascript" src="${RESOURCE}/js/dining/orderList.js"></script>
    
    <style type="text/css">
    	.hiddenTr{
    		display:none;
    	}
    	.orginTr{
    		
    	}
    	#mainContent{
    		display: none;
    		margin-left: 20px;
    		margin-right: 20px;
    		max-width: 1300px;
    	}
    	.button{
    		padding-left: 15px;
    		padding-right: 15px;
    	}
    	.button i.fa{
    		margin-right: 5px;
    	}
    </style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<div class="container">
		<div id="mainContent">
			<button class="button" id="goBack" backUrl="${backUrl}" onclick="goback('${backUrl}')"><i class="fa fa-reply"></i>返回</button>
			<c:if test="${orderProvider.provideUserId eq sessionScope.user.id &&  orderProvider.status eq 1}"><!-- TODO -->
				<button class="button button-caution" style="margin-left:20px;" onclick="cancelProvide('${orderProvider.id}')"><i class="fa fa-trash-o"></i>取消订餐</button>
				<button class="button button-action" style="margin-left:20px;" onclick="finishProvide('${orderProvider.id}')"><i class="fa fa-cutlery"></i>结束订餐</button>
			</c:if>
			<button class="button button-primary" style="margin-left:60px;" onclick="changeStatistics(this)">合并查看</button>
			<hr/>
			<div class="alert alert-success">
				<div><b>餐馆信息</b></div>
				<c:forEach items="${restaurantList}" var="restaurant">
					<div>${restaurant.name} ( ${restaurant.telphone} )</div>
				</c:forEach>
			</div>
			<div id="orderList" style="display: none">
				<table class="table table-bordered table-hover" style="font-family: 微软雅黑">
					<thead>
					<tr id="headTr">
						<th width="8%"><li class="fa fa-glass"></li></th>
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
	</div>
	
	<div class="modal fade" id="confirmModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h4 id="confirmTip"></h4>
				</div>
				<div class="modal-body">
					<button class="button button-caution" data-dismiss="modal" style="margin-right:20px;" id="confirmBtn">确定</button>
					<button class="button" data-dismiss="modal" id="cancelBtn">取消</button>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->
	
</body>
</html>