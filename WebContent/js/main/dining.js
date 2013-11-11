//AjaxAnywhere.prototype.showLoadingMessage = function() {}
//AjaxAnywhere.prototype.hideLoadingMessage = function() {}

function bindAll(){
	$('a[data-toggle="tab"]').on('show', function(e) {
		var targetHref = e.target.href;
		var targetZone = targetHref.substring(targetHref.indexOf('#') + 1);
		AjaxAnywhere.prototype.showLoadingMessage = function() {
			$("#" + targetZone + "Body").hide();
			$("#" + targetZone + "Loading").show();
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {

		};
		var url = 'aa_' + targetZone;
		ajaxAnywhere.getAJAX(url, targetZone);
	});
	// ///////////////// group /////////////////
	// create group submit
	$("#createGroupBtn").click(function() {
		if (J.isEmpty($("#group_name").val())) {// null validate
			$("#group_name_group").addClass("error");
			return;
		}
		if (J.isNotEmpty($("#group_password").val())) {
			$("#group_password").val($.md5($("#group_password").val()));
		}
		ajaxAnywhere.formName = "group_form";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "groupList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
			$("#createGroupBtn").button("loading");
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
			$("#createGroupBtn").button("reset");
			$("#collapseCreateGroupBtn").collapse('toggle');
			$("#collapseCreateGroup").collapse('toggle');
		};
		ajaxAnywhere.submitAJAX();
	});
	$("#group_name").blur(function() {
		$("#group_name_group").removeClass("error");
	});
	$("#collapseCreateGroup").on('hidden', function() {
		J.clearForm("group_form");
	});
	// ///////////////// restaurant /////////////////
	// create group submit
	$("#createRestaurantBtn").click(function() {
		if (J.isEmpty($("#restaurant_name").val())) {// null validate
			$("#restaurant_name_group").addClass("error");
			return;
		}
		ajaxAnywhere.formName = "restaurant_form";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "restaurantList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
			$("#createRestaurantBtn").button("loading");
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
			$("#createRestaurantBtn").button("reset");
			$("#collapseCreateRestaurantBtn").collapse('toggle');
			$("#collapseCreateRestaurant").collapse('toggle');
		};
		ajaxAnywhere.submitAJAX();
	});
	$("#restaurant_name").blur(function() {
		$("#restaurant_name_group").removeClass("error");
	});
	$("#collapseCreateRestaurant").on('hidden', function() {
		J.clearForm("restaurant_form");
	});
	// //////////////////////////////////////////
	$("#passwordModal").on("hidden", function() {
		$("#inputGroupPassword").val("");
		$("#toJoinGroupPassword").val("");
		$("#toJoinGroupId").val("");
	});
	$("#confirmModal").modal({
		backdrop : true,
		keyboard : true,
		show : false
	});
	///////////////////////////////////////////////
}

function bindCollapseTrigger(){
	$(".collapseTrigger").mouseover(function(){
    	$(this).css("text-decoration", "underline");
    });
    $(".collapseTrigger").mouseout(function(){
    	$(this).css("text-decoration", "none");
    });
    $(".collapseTrigger").click(function(){
    	$(this).children("i").toggle();
    });
}

function editGroup(groupId) {
	var url = 'ajax_loadGroup';
	var params = {
		"id" : groupId
	};
	$.post(url, params, function(data, textStatus, jqXHR) {
		var group = data.result["group"];
		if (group == null) {
			alert("Group not exist");
		} else {
			J.fillForm(group, "group");
			$("#collapseCreateGroupBtn").collapse('toggle');
			$("#collapseCreateGroup").collapse('toggle');
		}
	});
}

function joinGroup(groupId, password) {
	if (J.isNotEmpty(password)) {
		$("#toJoinGroupId").val(groupId);
		$("#toJoinGroupPassword").val(password);
		$("#passwordModal").modal({
			backdrop : true,
			keyboard : true,
			show : true
		});
	} else {
		var url = "aa_joinGroup?groupId=" + groupId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "groupList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
		};
		ajaxAnywhere.getAJAX(url);
	}
}
function confirmPassword() {
	var password = $("#inputGroupPassword").val();
	if (J.isNotEmpty(password)) {
		password = $.md5(password);
	}
	if (password == $("#toJoinGroupPassword").val()) {
		joinGroup($("#toJoinGroupId").val());
	} else {
		alert("Wrong password")
	}
}

function quitGroup(groupId, isConfirm) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = "aa_quitGroup?groupId=" + groupId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "groupList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
		};
		ajaxAnywhere.getAJAX(url);
	} else {
		$("#confirmTip").html("确定退出么?");
		$("#confirmModal").modal("show");
		$("#confirmBtn").click(function() {
			quitGroup(groupId, true);
		});
	}
}

function deleteGroup(groupId, memberCount, isConfirm) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = "aa_deleteGroup?groupId=" + groupId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "groupList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
		};
		ajaxAnywhere.getAJAX(url);
	} else {
		if (memberCount > 1) {
			$("#confirmTip").html("小组已有人加入，确定删除么?");
		} else {
			$("#confirmTip").html("确定删除么?");
		}
		$("#confirmModal").modal("show");
		$("#confirmBtn").click(function() {
			deleteGroup(groupId, memberCount, true);
		});
	}
}

function cancelProvide(orderProviderId, isConfirm) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = "ajax_cancelProvide";
		var params = {
			"orderProviderId" : orderProviderId
		};
		$.post(url, params, function(data, textStatus, jqXHR) {
			var code = data.result["code"];
			if (code == "success") {
				window.location.reload();// TODO aa?
			} else if (code == "error") {
				alert("An error occured");
			} else {
				alert("Unknown error occured");
			}
		});
	} else {
		$("#confirmTip").html("确定取消么?");
		$("#confirmModal").modal("show");
		$("#confirmBtn").click(function() {
			cancelProvide(orderProviderId, true);
		});
	}
}

function editRestaurant(restaurantId) {
	var url = 'ajax_loadRestaurant';
	var params = {
		"id" : restaurantId
	};
	$.post(url, params, function(data, textStatus, jqXHR) {
		var restaurant = data.result["restaurant"];
		if (restaurant == null) {
			alert("Restaurant not exist");
		} else {
			J.fillForm(restaurant, "restaurant");
			$("#collapseCreateRestaurantBtn").collapse('toggle');
			$("#collapseCreateRestaurant").collapse('toggle');
		}
	});
}

function showRestaurantMenu(restaurantId) {
	var url = "${pageContext.request.contextPath}/main_dish";
	url += "?back=" + encodeURIComponent("main_dining?active=restaurant");
	url += "&restaurantId=" + restaurantId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function showGroupMember(groupId) {
	var url = "${pageContext.request.contextPath}/main_member";
	url += "?back=" + encodeURIComponent("main_dining?active=group");
	url += "&groupId=" + groupId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function provideMeal() {
	var url = "${pageContext.request.contextPath}/main_provideMeal";
	url += "?back=" + encodeURIComponent("main_dining?active=orderProvider");
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function joinProvideMeal(orderProviderId) {
	var url = "${pageContext.request.contextPath}/main_order";
	url += "?back=" + encodeURIComponent("main_dining?active=orderProvider");
	url += "&orderProviderId=" + orderProviderId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function showOrderList(orderProviderId) {
	var url = "${pageContext.request.contextPath}/main_orderList";
	url += "?back=" + encodeURIComponent("main_dining?active=orderProvider");
	url += "&orderProviderId=" + orderProviderId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}