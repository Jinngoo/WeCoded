var ws = null;
var ctrl = false;
$(document).ready(function() {
    ws_connect();
    bindEvent();
});
function bindEvent() {
    $("#send").click(function() {
        sendInput();
    });
    $("#input").keydown(function(e){
        if(e.keyCode == 17){
            ctrl = true;
        }else if(ctrl && e.keyCode == 13){
            sendInput();
        }
    });
    $("#input").keyup(function(e){
        if(e.keyCode == 17){
            ctrl = false;
        }
    });
}
function appendText(userid, username, text){
	var template = $("#template").clone().removeAttr("id");
	
	var html = "<b>" + username + ":</b><br/>" + text;
    template.children(".text").html(html.replace(/\s/gi, "<br/>"));
    template.children(".avatar").attr("src", contextPath + "/getImage/1/" + userid).attr("title", username);
    template.appendTo($("#output")).slideDown("fast");
    $("#output").parent().animate({scrollTop : $("#output").height()});
    
    var chatboxs = $("#output").children(".chatbox");
    if(chatboxs.length > 24){
        chatboxs.first().slideUp("fast", function(){
            $(this).remove();
        });
    }
}
function sendInput() {
    var text = $("#input").val();
    if ($.trim(text).length > 0) {
    	var userid = $("#userid").val();
    	var username = $("#username").val();
    	var message = "{userid:'" + userid + "',username:'" + username + "',text:'" + text + "'}"; 
        ws_send(message);
        $("#input").val("");
        appendText(userid, username, text);
    }
}
function receiveData(data){
    data = eval("("+data+")");
    appendText(data.userid, data.username, data.text);
}

function ws_connect() {
    var url = J.getIndexUrl(contextPath, "ws") + "/wsChatRoom?id=" + $("#userid").val();
    ws = new WebSocket(url);
    ws.onopen = function() {
        console.log("open");
    }
    ws.onmessage = function(e) {
        receiveData(e.data);
    }
    ws.onclose = function(e) {
        console.log("closed");
    }
    ws.onerror = function(e) {
        console.log("error");
    }
}
function ws_send(msg) {
    ws.send(msg);
}
function ws_close() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
}
window.onbeforeunload = function() {
    if (ws) {
        ws_close();
    }
};