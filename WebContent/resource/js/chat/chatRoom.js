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


/////////////////////////
var ws = null;
var ctrl = false;
var fileReader = null;

$(document).ready(function() {
    ws_connect();
    bindEvent();
    resizeOutput();
    onFocus();
    console.log('加个在线人数、视频独立窗口')
    
    $(".videoWindow").easydrag();
    $(".videoWindow").setHandler('moveVideo');
});
$(window).resize(function(){
    resizeOutput();
});
$(window).focus(function(){
    onFocus();
});
function onFocus(){
    $("#input").focus();
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
    if(chatboxs.length > 24){
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
    if(url && url.indexOf("youku.com") != -1){
        getYoukuInfo(url, chatbox);
    }else if(url && url.indexOf("tudou.com") != -1){
        getTudouInfo(url, chatbox);
    }else{
        chatbox.html(chatbox.html().replace(url, "<a href='"+url+"' target='_blank'>"+url+"</a>"));
    }
}
//http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XNjM4NjczMzI4_ev_3.html
function getYoukuInfo(url, chatbox){
    var api = "https://openapi.youku.com/v2/videos/show_basic.json?client_id=683e07f6f96dd3a0&video_url=";
    api += encodeURIComponent(url);
    $.getJSON(api, function(data, textStatus, jqXHR) {
        if (!data.error) {
            var videoBox = initVideoBox("youku", data.thumbnail_v2, data.player);
            var target = chatbox.find("div.content");
            target.append("<br>").append(videoBox.show());
            target.append("<br>视频标题：").append(data.title);
            target.append("<br>视频描述：").append(data.description);
        }else{
            console.log(data.error);
        }
    });
}
//http://www.tudou.com/v/Nxm2zaSwqRc/&bid=05&rpid=324313167&resourceId=324313167_05_05_99/v.swf
function getTudouInfo(url, chatbox){
    var api = "http://api.tudou.com/v6/video/info?app_key=6206acbc39bf0491&itemCodes=";
    if(url.substring(url.length-1, url.length) == "/"){
        url = url.substring(0, url.lentgh - 1);
    }
    var videoId = url.substring(url.lastIndexOf("/")+1, url.length);
    if(videoId.indexOf(".") != -1){
        videoId = videoId.substring(0, videoId.indexOf("."));
    }
    api += videoId;
    $.getJSON(api, function(data, textStatus, jqXHR) {
        if (data.result) {
            var videoBox = initVideoBox("tudou", data.result.bigPicUrl, data.result.outerPlayerUrl);
            var target = chatbox.find("div.content");
            target.append("<br>").append(videoBox.show());
            target.append("<br>视频标题：").append(data.result.title);
            target.append("<br>视频标签：").append(data.result.tags);
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
function receiveMessage(message){
    message = JSON.parse(message);
    if(message.type == "text"){
        appendMessage(message);
        $("#alertAudio").get(0).play();
    }else if(message.type == "userlist"){
        refreshUserList(message);
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
function ws_connect() {
    var url = J.getIndexUrl(contextPath, "ws") + "/wsChatRoom";
    url += "?userid=" + $("#userid").val();
    url += "&username=" + encodeURIComponent($("#username").val());
    ws = new WebSocket(url);
    ws.onopen = function() {
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
