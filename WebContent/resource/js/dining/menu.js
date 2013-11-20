$(document).ready(function(){
    $("#mainContent").slideDown("fast");
    
    $("#createDishBtn").click(function(){
        var nameValid = $("#dish_name").get(0).checkValidity();
        var priveValid = $("#dish_price").get(0).checkValidity();
        if(!nameValid || !priveValid){ //一个不合法就点击提交，触发html5的表单检验提示
            $("#invalidSubmit").click();
            return;
        }
        
        $("#dish_restaurantId").val('${restaurant.id}');
        ajaxAnywhere.formName = "dish_form";
        AjaxAnywhere.prototype.getZonesToReaload = function(){
            return "dishList";
        }
        AjaxAnywhere.prototype.showLoadingMessage = function() {
            $("#createDishBtn").button("loading");
        };
        AjaxAnywhere.prototype.hideLoadingMessage = function() {
            $("#createDishBtn").button("reset");
            $("#collapseTool").collapse('toggle');
            $("#collapseCreateDish").collapse('toggle');
        };
        ajaxAnywhere.submitAJAX();
    });
    
    $("#dish_name").blur(function(){
        $("#dish_name_group").removeClass("error");
    });
    $("#collapseCreateDish").on("hidden.bs.collapse", function () {
        J.clearForm("dish_form");
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
var toReloadImgId = null;
function reloadImage(){
    if(toReloadImgId){
        var img = $("#img_" + toReloadImgId);
        img.attr("src", img.attr("src"));
        toReloadImgId = null;
    }
}
function markDish(dishId){
    toReloadImgId = dishId;
}
// onclick="changePicture('${dish.id}')"
function changePicture(dishId){
    var url = contextPath + "/tool/uploadImage/4/" + dishId;
    window.open(url, "_blank");
//    $("#uploadImgPanel").find("input[name='dishId']").val(dishId);
//    $("#uploadImgPanel").find("input[name='file']").val(null);
//    $("#uploadImgPanel").collapse('show');
//    top.$('html,body').animate({scrollTop: 0}, '1000');
}
function upload(){
    var form = $('#photoForm').ajaxSubmit({  });
    var xhr = form.data('jqxhr');
    xhr.done(function() {
        $("#uploadImgPanel").collapse('hide');
        var url = contextPath + "/aa_dishList?restaurantId=${restaurant.id}";
        ajaxAnywhere.formName = "";
        AjaxAnywhere.prototype.getZonesToReaload = function(){
            return "dishList";
        }
        AjaxAnywhere.prototype.showLoadingMessage = function() {};
        AjaxAnywhere.prototype.hideLoadingMessage = function() {};
        ajaxAnywhere.getAJAX(url);
    });
}
function editDish(dishId){
    var url = contextPath + "/dining/loadDish/" + dishId;
$.get(url, {}, function(data, textStatus, jqXHR){
    var dish = data;
    if(dish == null){
        alert("Dish not exist");
    }else{
        J.fillForm(dish, "dish");
        $("#collapseTool").collapse('toggle');
        $("#collapseCreateDish").collapse('toggle');
        //equals $(".collapseDish").collapse('toggle');
        }
    });
}
function deleteDish(dishId){
    $("#toDeleteId").val(dishId);
$('#deleteConfirmModal').modal({
        backdrop: true,
        keyboard: true,
        show: true
    });
}
function deleteConfirm(){
    var url = contextPath + "/dining/deleteDish/" + $("#toDeleteId").val();
ajaxAnywhere.formName = "";
AjaxAnywhere.prototype.getZonesToReaload = function(){
    return "dishList";
    }
    AjaxAnywhere.prototype.showLoadingMessage = function() {};
    AjaxAnywhere.prototype.hideLoadingMessage = function() {};
    ajaxAnywhere.getAJAX(url);
}
function validatePrice(obj){
    if (!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/)){
    obj.value = obj.t_value;
}else{
    obj.t_value = obj.value;
}
if (obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)){
    obj.o_value = obj.value;
}
if(obj.value.length > 0 && obj.value.indexOf(".") != -1){
    if(obj.value.length - (obj.value.indexOf(".")+1) > 2){
        obj.value = obj.value.substring(0, obj.value.indexOf(".")+3);
    }
}
//obj.value = Math.round(obj.value*100)/100;
}
function copy(restaurantId){
    var url = contextPath + "/dining/copyRestaurant/" + restaurantId;
    $.post(url, {}, function(data, textStatus, jqXHR){
        var code = data.code;
        if(code == "success"){
            goback();
        }else if(code == "error"){
            alert(data.result["message"]);
        }else{
            alert("Copy fail");
        }
    });
}
function showToolBar(popover){
    $(popover).children("div.tool_bar").show();
}
function hideToolBar(popover){
    $(popover).children("div.tool_bar").hide();
}
function reloadAvatar(){
    $("#restaurantAvatar").attr("src", $("#restaurantAvatar").attr("src"));
}