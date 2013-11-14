<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dining</title>
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
	
    <script type="text/javascript" src="${RESOURCE}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${RESOURCE}/js/custom/popoverButton.js"></script>
	<script type="text/javascript" src="${RESOURCE}/js/dining/dining.js"></script>
    <style type="text/css">
    	.collapseTrigger{
    		cursor: pointer;
    	}
    </style>
</head>
<body>
	
	<%@ include file="../nav_top.jsp" %>
	
	<!-- main -->
	<div id="mainContent" style="display:none;margin-left:20px;">

			
		<!-- tabs -->
		<ul class="nav nav-tabs">
			<li id="orderProviderTab"><a href="#orderProviderList" data-toggle="tab"><spring:message code="main.sboffer" /></a></li>
			<li id="groupTab"><a href="#teamList" data-toggle="tab"><spring:message code="main.group" /></a></li>
			<li id="restaurantTab"><a href="#restaurantList" data-toggle="tab"><spring:message code="main.restaurant" /></a></li>
		</ul>

		<div class="tab-content">

			<!-- team tab content -->
			<div class="tab-pane" id="teamList" style="padding-top:10px">
				<div id="collapseCreateGroupBtn" class="collapse in createGroup">
					<form class="form-inline" role="search">
						<div class="form-group"> 
							<input type="text" class="form-control" placeholder="Search(Coming soon)">
						</div>
						<a class="btn btn-success" data-toggle="collapse" data-target=".createGroup">创建小组</a>
					</form>
				</div>
				<div id="collapseCreateGroup" class="collapse createGroup" style="width:500px;margin-left: 300px;">
					<form class="form-horizontal" id="group_form" action="dining/saveTeam" method="post" role="form" >
						<input type="text" name="group_id" id="group_id" style="display:none">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="group_name">组名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="group_name" name="group_name">
								<p class="help-block">字母，数字，汉字皆可(必填)</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="group_password">口令</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="group_password" name="group_password">
								<p class="help-block">成员加入时使用，可为空</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="group_introduction">简介</label>
							<div class="col-sm-10">
								<textarea rows="4" class="form-control" id="group_introduction" name="group_introduction"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<a class="btn btn-primary" id="createGroupBtn">保存</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="btn btn-default" data-toggle="collapse" data-target=".createGroup">取消</a>
							</div>
						</div>
					</form>
				</div>
				
				<aa:zone name="teamList">
					<img id="teamListLoading" style="display:none;" src="${RESOURCE}/image/common/loading.gif" />
					<div id="teamListBody" style="margin-top:20px">
						<div class="alert alert-danger">
							<div class="collapseTrigger" data-toggle="collapse" data-target="#myTeamPanel">
								<i class="icon-chevron-right" style="display:none"></i>
								<i class="icon-chevron-down"></i> 我创建的小组 (${fn:length(myTeamList)})
								<hr>
							</div>
							<div id="myTeamPanel" class="collapse in">
								<c:forEach items="${myTeamList }" var="team" varStatus="status">
									<!-- Team button -->
									<jn:PopoverButton popoverTitle="[${team.name}]" content="${team.name}"
										popoverContent="创建者:&nbsp;&nbsp;${team.ownerName}<br>成员数:&nbsp;&nbsp;${team.memberCount}<br>组简介:&nbsp;&nbsp;${team.introduction}"
										imgUrl="getImage/2/${team.id}">
										<i class="icon-user" style="cursor:pointer;" onclick="showTeamMember('${team.id}')" title="浏览成员"></i>
										<i class="icon-wrench" style="cursor:pointer;" onclick="editTeam('${team.id}')" title="编辑"></i>
										<i class="icon-trash" style="cursor:pointer;" onclick="deleteTeam('${team.id}',${team.memberCount}, false)" title="删除"></i>
									</jn:PopoverButton>
								</c:forEach>
							</div>
						</div>
						<div class="alert alert-success">
							<div class="collapseTrigger" data-toggle="collapse"
								data-target="#joinedTeamPanel">
								<i class="icon-chevron-right" style="display:none"></i>
								<i class="icon-chevron-down"></i> 我加入的小组 (${fn:length(joinedTeamList)})
								<hr>
							</div>
							<div id="joinedTeamPanel" class="collapse in">
								<c:forEach items="${joinedTeamList }" var="team" varStatus="status">
									<jn:PopoverButton popoverTitle="[${team.name}]" content="${team.name}"
										popoverContent="创建者:&nbsp;&nbsp;${team.ownerName}<br>成员数:&nbsp;&nbsp;${team.memberCount}<br>组简介:&nbsp;&nbsp;${team.introduction}"
										imgUrl="img?type=2&id=${team.id}">
										<i class="icon-user" style="cursor:pointer;" onclick="showTeamMember('${team.id}')" title="浏览成员"></i>
										<i class="icon-ban-circle" style="cursor:pointer;" onclick="quitTeam('${team.id}', false)" title="退出"></i>
									</jn:PopoverButton>
								</c:forEach>
							</div>
						</div>
						<div class="alert alert-info">
							<div class="collapseTrigger" data-toggle="collapse" data-target="#otherTeamPanel">
								<i class="icon-chevron-right"></i>
								<i class="icon-chevron-down" style="display:none"></i> 其他的小组 (${fn:length(otherTeamList)})
								<hr>
							</div>
							<div id="otherTeamPanel" class="collapse">
								<c:forEach items="${otherTeamList }" var="team" varStatus="status">
									<jn:PopoverButton popoverTitle="[${team.name}]" content="${team.name}"
										popoverContent="创建者:&nbsp;&nbsp;${team.ownerName}<br>成员数:&nbsp;&nbsp;${team.memberCount}<br>组简介:&nbsp;&nbsp;${team.introduction}"
										imgUrl="img?type=2&id=${team.id}">
										<i class="icon-plus" style="cursor:pointer;" onclick="joinTeam('${team.id}', '${team.password}')" title="加入"></i>
									</jn:PopoverButton>
								</c:forEach>
							</div>
						</div>
					</div>
					<script>
						bindCollapseTrigger();
					</script>
				</aa:zone>
			</div>

			<!-- restaurant tab content -->
			<div class="tab-pane" id="restaurantList">
				<div id="collapseCreateRestaurantBtn"
					class="collapse in createRestaurant">
					<form class="form-search">
						<a class="btn btn-success" data-toggle="collapse" data-target=".createRestaurant">添加餐馆</a> 
							<input type="text" class="search-query" placeholder="Search(Coming soon)">
					</form>
				</div>
				<div id="collapseCreateRestaurant"
					class="collapse createRestaurant">
					<form class="form-horizontal" id="restaurant_form"
						action="aa_saveRestaurant" method="post">
						<input type="text" name="restaurant_id" id="restaurant_id" style="display:none">
						<fieldset>
							<div class="control-group" id="restaurant_name_group">
								<label class="control-label" for="restaurant_name_group">店名</label>
								<div class="controls">
									<input type="text" class="input-large" id="restaurant_name" name="restaurant_name">
									<p class="help-inline">字母，数字，汉字皆可(必填)</p>
								</div>
							</div>
							<div class="control-group" id="restaurant_telphone_group">
								<label class="control-label" for="restaurant_telphone_group">电话</label>
								<div class="controls">
									<input type="text" class="input-large" id="restaurant_telphone" name="restaurant_telphone">
									<p class="help-inline">多个用空格分隔</p>
								</div>
							</div>
							<div class="control-group" id="restaurant_introduction_group">
								<label class="control-label" for="restaurant_introduction_group">简介</label>
								<div class="controls">
									<textarea rows="4" class="input-large" id="restaurant_introduction" name="restaurant_introduction"></textarea>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<a class="btn btn-primary" id="createRestaurantBtn">保存</a> 
									<a class="btn btn-small offset1" data-toggle="collapse" data-target=".createRestaurant">取消</a>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
				<aa:zone name="restaurantList">
					<img id="restaurantListLoading" style="display:none;"
						src="${RESOURCE}/image/common/loading.gif" />
					<div id="restaurantListBody">
						<div class="alert alert-error">
							<div class="collapseTrigger" data-toggle="collapse"
								data-target="#myRestaurantPanel">
								<i class="icon-chevron-right" style="display:none"></i><i
									class="icon-chevron-down"></i>
								我创建的餐馆(${fn:length(myRestaurantList)})
								<hr>
							</div>
							<div id="myRestaurantPanel" class="collapse in">
								<c:forEach items="${myRestaurantList }" var="restaurant"
									varStatus="status">
									<jinva:PopoverButton popoverTitle="[${restaurant.name}]"
										content="${restaurant.name}"
										popoverContent="创建者:&nbsp;&nbsp;${restaurant.ownerName}<br>菜品数:&nbsp;&nbsp;${restaurant.dishCount}<br>打电话:&nbsp;&nbsp;${restaurant.telphone}<br>店简介:&nbsp;&nbsp;${restaurant.introduction}"
										imgUrl="img?type=3&id=${restaurant.id}">
										<i class="icon-list-alt"
											tool="dish_menu_${restaurant.dishCount}_${restaurant.ownerId}"
											style="cursor:pointer;"
											onclick="showRestaurantMenu('${restaurant.id}')" title="菜单"></i>
										<c:if test="${restaurant.ownerId eq sessionScope.user_id }">
											<i class="icon-wrench" style="cursor:pointer;"
												onclick="editRestaurant('${restaurant.id}')" title="编辑"></i>
										</c:if>
									</jinva:PopoverButton>
								</c:forEach>
							</div>
						</div>
						<div class="alert alert-info">
							<div class="collapseTrigger" data-toggle="collapse"
								data-target="#otherRestaurantPanel">
								<i class="icon-chevron-right"></i><i class="icon-chevron-down"
									style="display:none"></i>
								其他餐馆(${fn:length(otherRestaurantList)})
								<hr>
							</div>
							<div id="otherRestaurantPanel" class="collapse">
								<c:forEach items="${otherRestaurantList }" var="restaurant"
									varStatus="status">
									<jinva:PopoverButton popoverTitle="[${restaurant.name}]"
										content="${restaurant.name}"
										popoverContent="创建者:&nbsp;&nbsp;${restaurant.ownerName}<br>菜品数:&nbsp;&nbsp;${restaurant.dishCount}<br>打电话:&nbsp;&nbsp;${restaurant.telphone}<br>店简介:&nbsp;&nbsp;${restaurant.introduction}"
										imgUrl="img?type=3&id=${restaurant.id}">
										<i class="icon-list-alt"
											tool="dish_menu_${restaurant.dishCount}_${restaurant.ownerId}"
											style="cursor:pointer;"
											onclick="showRestaurantMenu('${restaurant.id}')" title="菜单"></i>
									</jinva:PopoverButton>
								</c:forEach>
							</div>
						</div>
					</div>
					<script>
					$(function () {
			    		$('#restaurantListBody a[rel="popover"]').popover({html:true});
			    		setTimeout(function(){
			    			$("i[tool='dish_menu_0_${sessionScope.user_id}']").each(function(){
				    			J.flicker("slow", $(this), 3);
			    			});
			    		},1000);
					});
					bindCollapseTrigger();
				</script>
				</aa:zone>
			</div>

			<!-- offer tab content -->
			<div class="tab-pane" id="orderProviderList">
				<a class="btn btn-success" onclick="provideMeal()">发起订餐</a>
				<hr />
				<aa:zone name="orderProviderList">
					<img id="orderProviderListLoading" style="display:none;"
						src="${RESOURCE}/image/common/loading.gif" />
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th><li class="icon-glass"></li></th>
								<th>状态</th>
								<th><spring:message code="main.sboffer.createDate" /></th>
								<th><spring:message code="main.sboffer.provide" /></th>
								<th><spring:message code="main.sboffer.receiveGroups" /></th>
								<th><spring:message code="main.sboffer.restaurants" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody id="orderProviderListBody">
							<c:forEach items="${orderProviderList }" var="orderProvider"
								varStatus="status">
								<tr class="success">
									<td>${status.index+1}</td>
									<td>${orderProviderStatusCode[orderProvider.status] }</td>
									<td style="width:80px;text-align:center"><fmt:formatDate
											value="${orderProvider.createDate}" type="date"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td>${orderProvider.provideUserName}</td>
									<td class="script_br">${orderProvider.receiveGroups}</td>
									<td class="script_br">${orderProvider.restaurants}</td>
									<td style="text-align:center"><c:if
											test="${orderProvider.status eq 1 }">
											<!-- TODO -->
											<button class="btn"
												onclick="joinProvideMeal('${orderProvider.id}')">
												<spring:message code="main.sboffer.chooseProvide" />
											</button>
										</c:if>
										<button class="btn"
											onclick="showOrderList('${orderProvider.id}')">查看订单</button>
										<c:if
											test="${orderProvider.provideUserId eq sessionScope.user_id && orderProvider.status eq 1}">
											<!-- TODO -->
											<button class="btn"
												onclick="cancelProvide('${orderProvider.id}')">
												<spring:message code="main.sboffer.cancelProvide" />
											</button>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<script type="text/javascript">
						String.prototype.replaceAll = function(s1,s2){    
							return this.replace(new RegExp(s1,"gm"),s2);    
						}
						$("td.script_br").each(function(){
							$(this).html($(this).html().replaceAll(",", "<br/>"));
						});
					</script>
					</aa:zone>
				</div>
		</div>
	</div>
		
	<div class="modal fade" id="passwordModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h5>小组设置了口令</h5>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<input type="password" id="inputGroupPassword">
						<input type="text" id="toJoinGroupId" style="display:none">
						<input type="text" id="toJoinGroupPassword" style="display:none">
					</form>
					<button class="btn btn-danger" data-dismiss="modal" style="margin-right:20px;" onclick="confirmPassword()">确定</button>
					<button class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->
	
	
	<div class="modal fade" id="confirmModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<a class="close" data-dismiss="modal">×</a>
					<h5 id="confirmTip"></h5>
				</div>
				<div class="modal-body">
					<button class="btn btn-danger" data-dismiss="modal" style="margin-right:20px;" id="confirmBtn">确定</button>
					<button class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div> <!-- /.modal-content -->
		</div> <!-- /.modal-dialog -->
	</div> <!-- /.modal -->
		
	
	<%@ include file="../nav_bottom.jsp" %>	


</body>
</html>