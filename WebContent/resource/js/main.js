//function Logout(){
//	if(!!$.cookie("jinva")){
//		$.cookie("jinva", null);
//	}
//	$("#pass").val(null);
//	$("#logged").slideUp(function(){
//		$("#login").slideDown();
//	});
//}

//AjaxAnywhere.prototype.showLoadingMessage = function() {}
//AjaxAnywhere.prototype.hideLoadingMessage = function() {}
window.onbeforeunload = function() {
	$(document).scrollTop(0);
};
$(document).ready(function() {
	$("#logout").click(function() {
		logout();
	});
});
function content(src) {
	$("#content_frame").attr("src", src);
}
function logout() {
	var url = 'ajax_logout';
	$.post(url, {}, function(data, textStatus, jqXHR) {
		window.location.reload();
	});
}

function test(){
    var url = contextPath + '/test/abcabcabc/';
    ajaxAnywhere.getAJAX(url, 'testzone');
    console.log('hehe')
};