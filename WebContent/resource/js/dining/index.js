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
		};
		AjaxAnywhere.prototype.showLoadingMessage = function() {
			
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
			$("#collapseCreateGroupBtn").collapse('toggle');
			$("#collapseCreateGroup").collapse('toggle');
		};
		ajaxAnywhere.submitAJAX();
	});
	$("#group_name").blur(function() {
		$("#group_name_group").removeClass("error");
	});
	$("#collapseCreateGroup").on('hidden.bs.collapse', function() {
		J.clearForm("group_form");
		$("#teamAvatar").parent().hide();
	});
	// ///////////////// restaurant /////////////////
	// create restaurant submit
	$("#createRestaurantBtn").click(function() {
		if (!$.trim($("#restaurant_name").val())) {// null validate
			$("#restaurant_name_group").addClass("error");
			return;
		}
		var belong = $("#restaurant_belong_checkbox").get(0).checked ? 2 : 1;
		$("#restaurant_belong").val(belong);
		
		ajaxAnywhere.formName = "restaurant_form";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "restaurantList";
		};
		AjaxAnywhere.prototype.showLoadingMessage = function() {
			
		};
		AjaxAnywhere.prototype.hideLoadingMessage = function() {
			$("#collapseCreateRestaurantBtn").collapse('toggle');
			$("#collapseCreateRestaurant").collapse('toggle');
		};
		ajaxAnywhere.submitAJAX();
	});
	$("#restaurant_name").blur(function() {
		$("#restaurant_name_group").removeClass("error");
	});
	$("#collapseCreateRestaurant").on('hidden.bs.collapse', function() {
		J.clearForm("restaurant_form");
		$("#restaurantAvatar").parent().hide();
		//默认是private
		updateBelongSwitch(false);
		$("#restaurant_belong").val(1);
	});
	// //////////////////////////////////////////
	$("#passwordModal").on("hidden.bs.collapse", function() {
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
var toReloadAvatarId = null;
function reloadAvatar(){
    if(toReloadAvatarId){
        var img = $('img[avatar=' + toReloadAvatarId + ']');
        img.attr("src", img.attr("src"));
        
//        img = $("#img_" + toReloadAvatarId);
        img = $("img[id^=img_" + toReloadAvatarId + "]");
        img.attr("src", img.attr("src"));
        toReloadAvatarId = null;
    }
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
		    toReloadAvatarId = team.id;
		    $("#teamAvatar").attr("src", contextPath + "/getImage/2/" + team.id).attr("avatar", team.id);
		    $("#teamAvatar").parent().show().attr("href", contextPath + "/tool/uploadImage/2/" + team.id + "?callback=reloadAvatar&close=1");
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
		};
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
		alert("Wrong password");
	}
}

function quitTeam(teamId, isConfirm, trigger) {
	$("#confirmBtn").unbind("click");
	if (isConfirm) {
		var url = contextPath + "/dining/quitTeam/" + teamId;
		ajaxAnywhere.formName = "";
		AjaxAnywhere.prototype.getZonesToReaload = function() {
			return "teamList";
		};
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
		};
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

function deleteRestaurant(restaurantId, isConfirm){
    $("#confirmBtn").unbind("click");
    if (isConfirm) {
        var url = contextPath + "/dining/deleteRestaurant/" + restaurantId;
        ajaxAnywhere.formName = "";
        AjaxAnywhere.prototype.getZonesToReaload = function() {
            return "restaurantList";
        };
        AjaxAnywhere.prototype.showLoadingMessage = function() {
        };
        AjaxAnywhere.prototype.hideLoadingMessage = function() {
        };
        ajaxAnywhere.getAJAX(url);
    } else {
        $("#confirmTip").html("确定删除么?");
        $("#confirmModal").modal("show");
        $("#confirmBtn").click(function() {
            deleteRestaurant(restaurantId, true);
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
		    toReloadAvatarId = restaurantId;
            $("#restaurantAvatar").attr("src", contextPath + "/getImage/3/" + restaurantId).attr("avatar", restaurantId);
            $("#restaurantAvatar").parent().show().attr("href", contextPath + "/tool/uploadImage/3/" + restaurantId + "?callback=reloadAvatar&close=1");
			J.fillForm(restaurant, "restaurant");
			
			var isPublic = restaurant.belong == 2;
			updateBelongSwitch(isPublic);
			
			
			$("#collapseCreateRestaurantBtn").collapse('toggle');
			$("#collapseCreateRestaurant").collapse('toggle');
		}
	});
}
function updateBelongSwitch(isPublic){
    var belongCheck = $("#restaurant_belong_checkbox");
    belongCheck.get(0).checked = isPublic;
    if(isPublic){
        belongCheck.parent().removeClass("switch-off").addClass("switch-on");
    }else{
        belongCheck.parent().removeClass("switch-on").addClass("switch-off");
    }
}
function showRestaurantMenu(restaurantId) {
	var url = contextPath + "/dining/restaurantMenu";
	url += "/" + restaurantId;
    url += "/" + encodeURIComponent(encodeURIComponent(contextPath + "/dining/restaurant"));
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function showTeamMember(teamId) {
    var backUrl = encodeURIComponent(encodeURIComponent(contextPath + "/dining/team"));
	var url = contextPath + "/dining/teamMember/" + teamId + "/" + backUrl;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function provideMealPage() {
    var backUrl = encodeURIComponent(encodeURIComponent(contextPath + "/dining/orderProvider"));
	var url = contextPath + "/dining/provideMealPage/" + backUrl;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function joinProvideMeal(orderProviderId) {
    var backUrl = encodeURIComponent(encodeURIComponent(contextPath + "/dining/orderProvider"));
	var url = contextPath + "/dining/joinOrder/" + orderProviderId + "/" + backUrl;
	$("#mainContent").slideUp("fast", function() {
		window.location.href = url;
	});
}

function showOrderList(orderProviderId) {
    var backUrl = encodeURIComponent(encodeURIComponent(contextPath + "/dining/orderProvider"));
    var url = contextPath + "/dining/orderList/" + orderProviderId + "/" + backUrl;
    $("#mainContent").slideUp("fast", function() {
        window.location.href = url;
    });
}

function cancelProvide(orderProviderId, isConfirm) {
    $("#confirmBtn").unbind("click");
    if (isConfirm) {
        var url = contextPath + "/dining/cancelProvide/" + orderProviderId;
        $.post(url, {}, function(data, textStatus, jqXHR) {
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