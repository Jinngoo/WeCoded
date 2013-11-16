//AjaxAnywhere.prototype.showLoadingMessage = function() {}
//AjaxAnywhere.prototype.hideLoadingMessage = function() {}
 $(document).ready(function(){
    bindAll();
    $("#mainContent").slideDown("fast");
    var activeTab = $('#tabs').attr('activeTab');
    if (!$.trim(activeTab)) {
        activeTab = "orderProvider";
    }
    $("#" + activeTab + "Tab").children('a[data-toggle="tab"]').tab('show');
    $("#" + activeTab + "List").addClass("active");
});
function bindAll(){
	$('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
		var targetHref = e.target.href;
		
		var targetZone = targetHref.substring(targetHref.indexOf('#') + 1);
		AjaxAnywhere.prototype.showLoadingMessage = function() {
			$("#" + targetZone + "Body").hide();
			$("#" + targetZone + "Loading").show();
			
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
		};
		var url = contextPath + '/dining/' + targetZone;//springmvc restful ajaxanywhere
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
			return "teamList";
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
		if (!$.trim($("#restaurant_name").val())) {// null validate
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

function toggleArrow(trigger){
    $(trigger).children("i").toggle();
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

function editTeam(teamId) {
	var url = contextPath + '/dining/loadTeam';
	var params = {
		"id" : teamId
	};
	$.get(url, params, function(data, textStatus, jqXHR) {
		var team = data;
		if (team == null) {
			alert("Team not exist");
		} else {
			J.fillForm(team, "group");
			$("#collapseCreateGroupBtn").collapse('toggle');
			$("#collapseCreateGroup").collapse('toggle');
		}
	});
}

function joinTeam(teamId, password, trigger) {
	if (J.isNotEmpty(password)) {
		$("#toJoinGroupId").val(teamId);
		$("#toJoinGroupPassword").val(password);
		$("#passwordModal").modal({
			backdrop : true,
			keyboard : true,
			show : true
		});
	} else {
		var url = contextPath + "/dining/joinTeam/" + teamId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "teamList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
		    destroyPopover(trigger);
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

function quitTeam(teamId, isConfirm, trigger) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = contextPath + "/dining/quitTeam/" + teamId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "teamList";
		}
		AjaxAnywhere.prototype.showLoadingMessage = function() {
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
		    destroyPopover(trigger);
		};
		ajaxAnywhere.getAJAX(url);
	} else {
		$("#confirmTip").html("确定退出么?");
		$("#confirmModal").modal("show");
		$("#confirmBtn").click(function() {
			quitTeam(teamId, true, trigger);
		});
	}
}

function deleteTeam(teamId, memberCount, isConfirm) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = contextPath + "/dining/deleteTeam/" + teamId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "teamList";
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
			deleteTeam(teamId, memberCount, true);
		});
	}
}

function cancelProvide(orderProviderId, isConfirm) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = contextPath + "/dining/cancelProvide/" + orderProviderId;
		$.post(url, params, function(data, textStatus, jqXHR) {
			var code = data;
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
	var url = contextPath + '/dining/loadRestaurant/' + restaurantId;
	$.get(url, {}, function(data, textStatus, jqXHR) {
		var restaurant = data;
		if (restaurant == null) {
			alert("Restaurant not exist");
		} else {
			J.fillForm(restaurant, "restaurant");
			$("#collapseCreateRestaurantBtn").collapse('toggle');
			$("#collapseCreateRestaurant").collapse('toggle');
		}
	});
}

//TODO
function showRestaurantMenu(restaurantId) {
	var url = contextPath + "/main_dish";
	url += "?back=" + encodeURIComponent("main_dining?active=restaurant");
	url += "&restaurantId=" + restaurantId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function showTeamMember(teamId) {
	var url = contextPath + "/main/teamMember";
	url += "/" + teamId;
	url += "/" + encodeURIComponent(encodeURIComponent(contextPath + "/dining/team"));
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function provideMeal() {
	var url = contextPath + "/main_provideMeal";
	url += "?back=" + encodeURIComponent("main_dining?active=orderProvider");
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function joinProvideMeal(orderProviderId) {
	var url = contextPath + "/main_order";
	url += "?back=" + encodeURIComponent("main_dining?active=orderProvider");
	url += "&orderProviderId=" + orderProviderId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function showOrderList(orderProviderId) {
	var url = contextPath + "/main_orderList";
	url += "?back=" + encodeURIComponent("main_dining?active=orderProvider");
	url += "&orderProviderId=" + orderProviderId;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}