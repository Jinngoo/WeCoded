
$(document).ready(function(){
	//each为了如果是多个
	$("#notificationSupport").each(function(){
		var type = $(this).attr("type");
		$(this).get(0).addEventListener(type, function(e) {
			var message = $(e.target).attr("message");
			if(message){
				sendNotification(message)
			}
		});
	});
});

function sendNotification(message){
	var request = {"type" : "notification", "message" : message};
	chrome.extension.sendRequest(request, function(data) {
		//console.log("response:" + data)
	});
}

