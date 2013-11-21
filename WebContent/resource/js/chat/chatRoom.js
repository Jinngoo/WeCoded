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
function sendInput() {
    var msg = $("#input").val();
    if ($.trim(msg).length > 0) {
        ws_send(msg);
        $("#input").val("");
    }
}
function receiveData(data){
    data = eval("("+data+")");
    var template = $("#template").clone().removeAttr("id");
    
    var html = "<b>" + data.name + ":</b><br/>" + data.text;
    template.children(".text").html(html.replace(/\s/gi, "<br/>"));
    template.children(".avatar").attr("src", contextPath + "/getImage/1/" + data.id).attr("title", "某某人");
    template.appendTo($("#output")).slideDown("fast");
    $("#output").parent().animate({scrollTop : $("#output").height()});
    
    var chatboxs = $("#output").children(".chatbox");
    if(chatboxs.length > 24){
        chatboxs.first().slideUp("fast", function(){
            $(this).remove();
        });
    }
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