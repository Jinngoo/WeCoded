<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dining</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/popoverButton.css" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${JQUERY_MD5}"></script>  
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/main/dining.js"></script>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	bindAll();
	    	$("#mainContent").slideDown("fast");
    		var activeTab = "${param.active}";
    		if (J.isEmpty(activeTab)) {
    			activeTab = "orderProvider";
    		}
    		$("#" + activeTab + "Tab").addClass("active");
    		$("#" + activeTab + "List").addClass("active");
		});
    </script>
    <style type="text/css">
    	.collapseTrigger{
    		cursor: pointer;
    	}
    </style>
</head>
<body>

	<!-- main -->
	<div id="mainContent" style="display:none;margin-left:20px;">
		
		<!-- tabs -->
		<ul class="nav nav-tabs">
<%-- 				<h3 class="well"><s:text name="main.index.title"></s:text></h3> --%>
       		<li id="orderProviderTab"><a href="#orderProviderList" data-toggle="tab"><s:text name="main.sboffer"></s:text></a></li>
			<li id="groupTab"><a href="#groupList" data-toggle="tab"><s:text name="main.group"></s:text></a></li>
			<li id="restaurantTab"><a href="#restaurantList" data-toggle="tab"><s:text name="main.restaurant"></s:text></a></li>
		</ul>
		
		<div class="tab-content">
		
			<!-- group tab content -->
			<div class="tab-pane" id="groupList">
				<div id="collapseCreateGroupBtn" class="collapse in createGroup">
					<form class="form-search"">
						<a class="btn btn-success" data-toggle="collapse" data-target=".createGroup">创建小组</a>
						<input type="text" class="search-query" placeholder="Search(Coming soon)">
					</form>
				</div>
				<div id="collapseCreateGroup" class="collapse createGroup">
					<form class="form-horizontal" id="group_form" action="aa_saveGroup" method="post">
						<input type="text" name="group_id" id="group_id" style="display:none">
						<fieldset>
							<div class="control-group" id="group_name_group">
								<label class="control-label" for="group_name_group">组名</label>
								<div class="controls">
									<input type="text" class="input-large" id="group_name" name="group_name">
									<p class="help-inline">字母，数字，汉字皆可(必填)</p>
								</div>
							</div>
							<div class="control-group" id="group_password_group">
								<label class="control-label" for="group_password_group">口令</label>
								<div class="controls">
									<input type="password" class="input-large" id="group_password" name="group_password">
									<p class="help-inline">成员加入时使用，可为空</p>
								</div>
							</div>
							<div class="control-group" id="group_introduction_group">
								<label class="control-label" for="group_introduction_group">简介</label>
								<div class="controls">
									<textarea rows="4" class="input-large" id="group_introduction" name="group_introduction"></textarea>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
								 	<a class="btn btn-primary" id="createGroupBtn">保存</a>
								 	<a class="btn btn-small offset1" data-toggle="collapse" data-target=".createGroup">取消</a>
								 </div>
							</div>
						</fieldset>
					</form>
				</div>
				<aa:zone name="groupList">
				<img id="groupListLoading" style="display:none;" src="${pageContext.request.contextPath}/image/common/loading.gif" />
				<div id="groupListBody">
					<div class="alert alert-error">
						<div class="collapseTrigger" data-toggle="collapse" data-target="#myGroupPanel">
							<i class="icon-chevron-right" style="display:none"></i><i class="icon-chevron-down"></i>
							我创建的小组 (${fn:length(myGroupList)})<hr>
						</div>
						<div id="myGroupPanel" class="collapse in">
							<c:forEach items="${myGroupList }" var="group" varStatus="status">
								<!-- Group button -->
								<jinva:PopoverButton popoverTitle="[${group.name}]" content="${group.name}" popoverContent="创建者:&nbsp;&nbsp;${group.ownerName}<br>成员数:&nbsp;&nbsp;${group.memberCount}<br>组简介:&nbsp;&nbsp;${group.introduction}" imgUrl="img?type=2&id=${group.id}" >
									<i class="icon-user" style="cursor:pointer;" onclick="showGroupMember('${group.id}')" title="浏览成员"></i>
									<i class="icon-wrench" style="cursor:pointer;" onclick="editGroup('${group.id}')" title="编辑"></i>
									<i class="icon-trash" style="cursor:pointer;" onclick="deleteGroup('${group.id}',${group.memberCount}, false)" title="删除"></i>
								</jinva:PopoverButton>
							</c:forEach>
						</div>
					</div>
					<div class="alert alert-success">
						<div class="collapseTrigger" data-toggle="collapse" data-target="#joinedGroupPanel">
							<i class="icon-chevron-right" style="display:none"></i><i class="icon-chevron-down"></i>
							我加入的小组 (${fn:length(joinedGroupList)})<hr>
						</div>
						<div id="joinedGroupPanel" class="collapse in">
							<c:forEach items="${joinedGroupList }" var="group" varStatus="status">
								<jinva:PopoverButton popoverTitle="[${group.name}]" content="${group.name}" popoverContent="创建者:&nbsp;&nbsp;${group.ownerName}<br>成员数:&nbsp;&nbsp;${group.memberCount}<br>组简介:&nbsp;&nbsp;${group.introduction}" imgUrl="img?type=2&id=${group.id}" >
									<i class="icon-user" style="cursor:pointer;" onclick="showGroupMember('${group.id}')" title="浏览成员"></i>
									<i class="icon-ban-circle" style="cursor:pointer;" onclick="quitGroup('${group.id}', false)" title="退出"></i>
								</jinva:PopoverButton>
							</c:forEach>
						</div>
					</div>
					<div class="alert alert-info">
						<div class="collapseTrigger" data-toggle="collapse" data-target="#otherGroupPanel">
							<i class="icon-chevron-right"></i><i class="icon-chevron-down" style="display:none"></i>
							其他的小组 (${fn:length(otherGroupList)})<hr>
						</div>
						<div id="otherGroupPanel" class="collapse">
							<c:forEach items="${otherGroupList }" var="group" varStatus="status">
								<jinva:PopoverButton popoverTitle="[${group.name}]" content="${group.name}" popoverContent="创建者:&nbsp;&nbsp;${group.ownerName}<br>成员数:&nbsp;&nbsp;${group.memberCount}<br>组简介:&nbsp;&nbsp;${group.introduction}" imgUrl="img?type=2&id=${group.id}" >
									<i class="icon-plus" style="cursor:pointer;" onclick="joinGroup('${group.id}', '${group.password}')" title="加入"></i>
								</jinva:PopoverButton>
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
				<div id="collapseCreateRestaurantBtn" class="collapse in createRestaurant">
					<form class="form-search">
						<a class="btn btn-success" data-toggle="collapse" data-target=".createRestaurant">添加餐馆</a>
						<input type="text" class="search-query" placeholder="Search(Coming soon)">
					</form>
				</div>
				<div id="collapseCreateRestaurant" class="collapse createRestaurant">
					<form class="form-horizontal" id="restaurant_form" action="aa_saveRestaurant" method="post">
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
				<img id="restaurantListLoading" style="display:none;" src="${pageContext.request.contextPath}/image/common/loading.gif" />
				<div id="restaurantListBody">
					<div class="alert alert-error">
						<div class="collapseTrigger" data-toggle="collapse" data-target="#myRestaurantPanel">
							<i class="icon-chevron-right" style="display:none"></i><i class="icon-chevron-down"></i>
							我创建的餐馆(${fn:length(myRestaurantList)})<hr>
						</div>
						<div id="myRestaurantPanel" class="collapse in">
							<c:forEach items="${myRestaurantList }" var="restaurant" varStatus="status">
								<jinva:PopoverButton popoverTitle="[${restaurant.name}]" content="${restaurant.name}" popoverContent="创建者:&nbsp;&nbsp;${restaurant.ownerName}<br>菜品数:&nbsp;&nbsp;${restaurant.dishCount}<br>打电话:&nbsp;&nbsp;${restaurant.telphone}<br>店简介:&nbsp;&nbsp;${restaurant.introduction}" imgUrl="img?type=3&id=${restaurant.id}" >
									<i class="icon-list-alt" tool="dish_menu_${restaurant.dishCount}_${restaurant.ownerId}" style="cursor:pointer;" onclick="showRestaurantMenu('${restaurant.id}')" title="菜单"></i>
									<c:if test="${restaurant.ownerId eq sessionScope.user_id }">
										<i class="icon-wrench" style="cursor:pointer;" onclick="editRestaurant('${restaurant.id}')" title="编辑"></i>
									</c:if>
								</jinva:PopoverButton>
							</c:forEach>
						</div>
					</div>
					<div class="alert alert-info">
						<div class="collapseTrigger" data-toggle="collapse" data-target="#otherRestaurantPanel">
							<i class="icon-chevron-right"></i><i class="icon-chevron-down" style="display:none"></i>
							其他餐馆(${fn:length(otherRestaurantList)})<hr>
						</div>
						<div id="otherRestaurantPanel" class="collapse">
							<c:forEach items="${otherRestaurantList }" var="restaurant" varStatus="status">
								<jinva:PopoverButton popoverTitle="[${restaurant.name}]" content="${restaurant.name}" popoverContent="创建者:&nbsp;&nbsp;${restaurant.ownerName}<br>菜品数:&nbsp;&nbsp;${restaurant.dishCount}<br>打电话:&nbsp;&nbsp;${restaurant.telphone}<br>店简介:&nbsp;&nbsp;${restaurant.introduction}" imgUrl="img?type=3&id=${restaurant.id}" >
									<i class="icon-list-alt" tool="dish_menu_${restaurant.dishCount}_${restaurant.ownerId}" style="cursor:pointer;" onclick="showRestaurantMenu('${restaurant.id}')" title="菜单"></i>
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
				<hr/>
				<aa:zone name="orderProviderList">
				<img id="orderProviderListLoading" style="display:none;" src="${pageContext.request.contextPath}/image/common/loading.gif" />
				<table class="table table-bordered table-hover">
					<thead><tr>
						<th><li class="icon-glass"></li></th>
						<th>状态</th>
						<th><s:text name="main.sboffer.createDate"></s:text></th>
						<th><s:text name="main.sboffer.provide"></s:text></th>
						<th><s:text name="main.sboffer.receiveGroups"></s:text></th>
						<th><s:text name="main.sboffer.restaurants"></s:text></th>
						<th></th>
					</tr></thead>
					<tbody id="orderProviderListBody">
						<c:forEach items="${orderProviderList }" var="orderProvider" varStatus="status">
						<tr class="success">
							<td>${status.index+1}</td>
							<td>${orderProviderStatusCode[orderProvider.status] }</td>
							<td style="width:80px;text-align:center"><fmt:formatDate value="${orderProvider.createDate}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
							<td>${orderProvider.provideUserName}</td>
							<td class="script_br">${orderProvider.receiveGroups}</td>
							<td class="script_br">${orderProvider.restaurants}</td>
							<td style="text-align:center">
								<c:if test="${orderProvider.status eq 1 }"><!-- TODO -->
									<button class="btn" onclick="joinProvideMeal('${orderProvider.id}')"><s:text name="main.sboffer.chooseProvide"></s:text></button>
								</c:if>
								<button class="btn" onclick="showOrderList('${orderProvider.id}')">查看订单</button>
								<c:if test="${orderProvider.provideUserId eq sessionScope.user_id && orderProvider.status eq 1}"><!-- TODO -->
									<button class="btn" onclick="cancelProvide('${orderProvider.id}')"><s:text name="main.sboffer.cancelProvide"></s:text></button>
								</c:if>
							</td>
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
			<button class="btn" data-dismiss="modal">取消</button>
		</div>
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