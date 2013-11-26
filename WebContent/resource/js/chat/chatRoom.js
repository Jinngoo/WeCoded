Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
//////////////////////////
/**
 * 桌面通知事件
 */
var notificationEvent = null;
/**
 * 添加桌面通知支持
 */
function addNotificationSupport(){
	notificationEvent = document.createEvent('HTMLEvents');
	notificationEvent.initEvent('WeCoded_ChatRoom_Notufication', false, false);
}
/**
 * 向桌面发送通知
 * @param message
 */
function sendNotification(messageText){
	$("#notificationSupport").attr("message", messageText);
	$("#notificationSupport").get(0).dispatchEvent(notificationEvent); 
}
/////////////////////////
var ws = null;
var ctrl = false;
var fileReader = null;
var maxRecord = 50;
var isFocus = true;
var sendShortcutKeys = 2;

$(document).ready(function() {
	addNotificationSupport();
    ws_connect();
    bindEvent();
    resizeOutput();
    onFocus();
    initSendShortcutKeys();
    console.log('TODO:最大记录保留数、历史记录保存')
    
    $(".videoWindow").easydrag();
    $(".videoWindow").setHandler('moveVideo');
});
$(window).resize(function(){
    resizeOutput();
});
$(window).focus(function(){
    onFocus();
});
$(window).blur(function(){
	onBlur();
});
function onFocus(){
    $("#input").focus();
    isFocus = true;
}
function onBlur(){
	isFocus = false;
}
function resizeOutput(){
    var height = $(window).height() - 255;
    $(".output").height(height);
    var top = $(".output").offset().top + 15;
    var left = $(".output").offset().left + 15;
    $("#userbox").css("top", top).css("left", left).height(height - 40);
}
function bindEvent() {
    $("#send").click(function() {
        sendInput();
    });
    $("#input").keydown(function(e){
        var onlyEnter = sendShortcutKeys == 1;
        var ctrlEnter = sendShortcutKeys == 2;
        if(e.keyCode == 17){
            ctrl = true;
        } else if (e.keyCode == 13){
            if (onlyEnter && !ctrl || ctrlEnter && ctrl) {
                if(!$("#send").attr("disabled")){
                    sendInput();
                }
                return false;
            } else if (onlyEnter && ctrl) {
                $("#input").val($("#input").val() + "\n");
            }
        }
    });
    $("#input").keyup(function(e){
        if(e.keyCode == 17){
            ctrl = false;
        }
    });
    $("#clearMsg").click(function(){
        $("#output").empty();
    });
    $("#sendPic").click(function(){
        $("#fileChooser").click();
    });
    /////////////
    fileReader = new FileReader(); //init FileReader, for send image
    fileReader.onload = function(progressEvent){
        var dataURL = progressEvent.target.result;
        sendImage(dataURL);
    };
    fileReader.onerror = function(){
        alert("Load file error.");
    };
    $("#fileChooser").change(function(){
        var files = $(this).get(0).files;
        var file = files[0];
        sendImageByFile(file);
        $(this).val("");
    });
    /////////////
    $(document).on({
        dragleave:function(e){  //拖离
            e.preventDefault();
        },
        drop:function(e){       //拖后放
            e.preventDefault();
            var dataTransfer = e.originalEvent.dataTransfer;

            var items = dataTransfer.items;
            var item = items[0];
            if(item.kind == "file"){
                var file = item.getAsFile();
                sendImageByFile(file);
            }else if(item.kind == "string"){
                item.getAsString(function(str){
                    if(str.substring(0, 4) != "data"){
                        $("#input").val($("#input").val() + str);
                    }
                });
            }
            
        },
        dragenter:function(e){  //拖进
            e.preventDefault();
        },
        dragover:function(e){   //拖来拖去
            e.preventDefault();
        }
    });
}
/**
 * 发送图片文件，gif最大2MB，其他格式512kb以上压缩后发送
 * @param file
 */
function sendImageByFile(file){
    if(file && /^(image\/jpeg|image\/png|image\/bmp|image\/gif)$/i.test(file.type)){
        if("image/gif" == file.type){
            file.size < 2048 * 1024 ? fileReader.readAsDataURL(file) : alert("动态图片超过2MB，太大鸟~");
            return;
        }
        sendCompressImage(file);//统统压缩~~
//        if (file.size < 256 * 1024) { //256KB以下 直接发
//            fileReader.readAsDataURL(file);//send in onload
//        } else {
//            sendCompressImage(file);
//        }
    }
}
function sendCompressImage(file){
    var url = URL.createObjectURL(file);
    J.loadImage(url, function(image){
        var width = image.naturalWidth;
        var height = image.naturalHeight;
      
        if(image.naturalWidth >= 2560){
            width = 1920;
            height = height * width / image.naturalWidth;
        }else if(image.naturalWidth >= 1920){
            width = 1440;
            height = height * width / image.naturalWidth;
        }
        
        var quality = 1.0;
        if(file.size >= 4096 * 1024){
            quality = 0.4;
        }else if(file.size >= 1024 * 1024){
            quality = 0.6;
        }else if(file.size >= 128 * 1024){
            quality = 0.8;
        }
        var canvas = $("<canvas></canvas>").attr("width", width).attr("height", height).css("display", "none");
        var context = canvas.get(0).getContext("2d");
        context.drawImage(image, 0, 0, width, height);
        var dataURL = canvas.get(0).toDataURL("image/jpeg", quality);
        canvas.remove();
        sendImage(dataURL);
    });
}
function sendImage(dataURL){
    var params = { image : dataURL };
    var message = buildMessage(params);
    ws_send(message);
    appendMessage(message);
}
function appendMessage(message){
    var userid = message.userid;
    var username = message.username;
    var date = new Date(message.date);
    date = date.format("yyyy-MM-dd HH:mm:ss");
    
    var id = Math.uuid();
    var content= null;
    if(message.text != undefined){
        content = J.encodeHTML(message.text).replace(/\n/gi, "<br/>");
    }else if(message.image != undefined){
        content = "<img src='" + message.image + "' class='image' />";
    }
    
	var template = $("#template").clone().attr("id", id);
	template.children("img").attr("src", contextPath + "/getImage/1/" + userid).attr("title", username);
	template.find("div.name").html(username);
	template.find("div.date").html(date);
	template.find("div.content").html(content);
	
    template.appendTo($("#output")).slideDown("fast");
    checkVideo(content, template);
    scrollMessage();
    
    var chatboxs = $("#output").children(".chatbox");
    if(chatboxs.length > maxRecord){
        chatboxs.first().slideUp("fast", function(){
            $(this).remove();
        });
    }
}
function scrollMessage(){
    $("#output").parent().animate({scrollTop : $("#output").height()});
}
function checkVideo(message, chatbox){
    var url = null;
    if(message.indexOf("http") != -1){
        url = J.substring(message, "http", " ");
    } else if(message.indexOf("www") != -1){
        url = J.substring(message, "www", " ");
    }
    if (url && url.indexOf("youku.com") != -1) {
        getVideoInfo("youku", url, chatbox);
    } else if (url && url.indexOf("tudou.com") != -1) {
        getVideoInfo("tudou", url, chatbox);
    }  else if (url && url.indexOf("youtube.com") != -1) {
        getVideoInfo("youtube", url, chatbox);
    } else {
        chatbox.html(chatbox.html().replace(url, "<a href='" + url + "' target='_blank'>" + url + "</a>"));
    }
}
function getVideoInfo(videoType, url, chatbox){
    var params = { "type" : videoType, "url" : url };
    $.post(contextPath + "/chatRoom/videoInfo", params, function(result, textStatus, jqXHR) {
        if (!result.error) {
            var videoBox = initVideoBox("youku", result.thumbnail, result.player);
            var target = chatbox.find("div.content");
            var description = result.description || "";
            if(description.length > 100)description = description.substring(0, 100)+" ...";
            target.append("<br>").append(videoBox.show());
            target.append("<br>视频标题：").append(result.title);
            target.append("<br>视频描述：").append(description);
            scrollMessage();
        }else{
            console.log(result.error);
        }
    });
}
function initVideoBox(embedId, imageSrc, videoLink){
    var videoBox = $("#videoBox").clone().removeAttr("id");
    videoBox.children(".videoThumbnail").attr("src", imageSrc);
    videoBox.children(".videoPlay").click(function(){
        var alreadyHasVideo = !!$(".videoContainer").html();
        
        var video = $("#" + embedId).clone();
        video.children("param[name=src]").attr("value", videoLink);//ie
        video.children("embed").attr("src", videoLink);//chrome
        $(".videoContainer").empty().append(video.show());
        if(!alreadyHasVideo){
            $(".videoWindow").animate({right: "0px"});
        }
    });
    return videoBox;
}
function toggleVideo(){
    var hideRight = 0 - $(".videoContainer").width() + "px";
    var currLeft = $(".videoWindow").css("left");
    var currRight = $(".videoWindow").css("right");
    if(!currLeft || currLeft != "auto"){//拖动过,直接隐藏
        $(".videoWindow").css("left", "").animate({"right": hideRight, "top": "50px"});
        return;
    }
    if(currRight == hideRight){ //现在是隐藏
        currRight = "0px";
    }else{//show or other position
        currRight = hideRight;
    }
    $(".videoWindow").animate({"right": currRight, "top": "50px"});
}
function closeVideo(){
    $(".videoWindow").css("left", "").animate({"right": "-" + $(".videoWindow").width() + "px", "top": "50px"}, function(){
        $(".videoContainer").empty();
    });
}
function buildMessage(params){
    var message = {
        type: "text",
        userid : $("#userid").val(),
        username : $("#username").val(),
        date : new Date()
    };
    if(params){
        $.extend(message, params);
    }
    return message;
}
function sendInput() {
    var text = $("#input").val();
    if ($.trim(text).length > 0) {
    	var userid = $("#userid").val();
    	var username = $("#username").val();
    	var message = buildMessage({ text : text }); 
    	$("#input").val("").focus();
        ws_send(message);
        appendMessage(message);
    }
}
function receiveMessage(message) {
    message = JSON.parse(message);
    if (message.type == "text") {
        appendMessage(message);
        $("#alertAudio").get(0).play();
        if (!isFocus) {
            if (message.text) {
                sendNotification(message.username + " : " + getComment(message.text));// 桌面通知
            } else if (message.image) {
                sendNotification(message.username + " : " + "发来图片。");// 桌面通知
            } else {
                sendNotification(message.username + " : " + "发来消息。");// 桌面通知
            }
        }
    }else if(message.type == "userlist"){
        refreshUserList(message);
    }
}
function getComment(text){
    if(text && text.toString().length > 18){
        return text.toString().substring(0, 18) + "...";
    }else{
        return text;
    }
}
function refreshUserList(message){
    var users = message.userlist;
    $("#usercount").html(users.length);
    $("#userlist").empty();
    for(var i = 0; i < users.length; i ++){
        var user = users[i];
        $("<div></div>").html(user.nickname).appendTo($("#userlist"));
    }
    top_reloadChatRoomUserCount(users.length);
}
function initSendShortcutKeys(){
    if(!$.cookie("wecoded_shortcut_keys")){
        $.cookie("wecoded_shortcut_keys", sendShortcutKeys, {expires: 7, path : "/"});
    }
    sendShortcutKeys = $.cookie("wecoded_shortcut_keys");

    $($("#sendShortcutsMenu").children("li").get(sendShortcutKeys-1)).addClass("active");
}
function changeSendShortcutKeys(type){
    sendShortcutKeys = parseInt(type, 10);
    $("#sendShortcutsMenu").children("li").removeClass("active");
    $($("#sendShortcutsMenu").children("li").get(type-1)).addClass("active");
}
///////////  websocket   ///////////////////////////
function ws_connect() {
    var url = J.getIndexUrl(contextPath, "ws") + "/wsChatRoom";
    url += "?userid=" + $("#userid").val();
    url += "&username=" + encodeURIComponent($("#username").val());
    $("#send").attr("disabled", "disabled");
    ws = new WebSocket(url);
    ws.onopen = function() {
    	$("#send").removeAttr("disabled");
        top_reloadChatRoomUserCount();
    }
    ws.onmessage = function(e) {
        receiveMessage(e.data);
    }
    ws.onclose = function(e) {
        console.log("closed");
    }
    ws.onerror = function(e) {
        console.log("error");
    }
}
function ws_send(msg) {
    if(typeof msg == "object"){
        msg = JSON.stringify(msg);
    }
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
