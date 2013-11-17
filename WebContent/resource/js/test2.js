
var dragging = false;
var rectArr = new Array();
var start = null;
var end = null;
var mouse = {x:0,y:0};
var inputImg = null;
var deviation = 2;

var selectRect = null;

var canvas = null;
var context = null;

$(document).ready(function() {
    canvas = $("#canvas");
    context = $("#canvas").get(0).getContext("2d");
    var input = $("#input");
    
    inputImg = loadImg(input.attr("src"), function(){
        $("#canvas").attr("width", input.width());
        $("#canvas").attr("height", input.height());
        startDraw();
        input.hide();
    });
    
    $("#canvas").mouseover(function(){
        
    });
    $("#canvas").mouseout(function(){
        if(dragging){
            dragging = false;
            dragDone();
        }
    });
    $("#canvas").mousemove(function(event){
        mouse.x = event.pageX - canvas.offset().left;
        mouse.y = event.pageY - canvas.offset().top;
        drawMoveCursor();
        if(dragging){
            end = {x:mouse.x, y:mouse.y};
        }
    });
    $("#canvas").mousedown(function(){
        dragging = true;
        start = {x:mouse.x, y:mouse.y};
    });
    $("#canvas").mouseup(function(){
        if(dragging){
            dragging = false;
            dragDone();
        }
    });
    
});


$(window).unload(function(){
    window.cancelAnimationFrame(animationFrame);
    clearInterval(interval);
});

function startDraw(){
    (function(){
        context.drawImage(inputImg, 0, 0);
        context.fillText("w3school.com.cn", 10, 90);
        
        for(var i = 0; i < rectArr.length; i ++){
            var rect = rectArr[i];
            var start = rect.start;
            var end = rect.end;
            drawRect(start, end);
        }
        
        animationFrame = window.requestAnimationFrame(arguments.callee);
    })();
    setInterval(function(){
        interval = context.strokeStyle = "rgb(" + J.getRandom(0, 255) + "," + J.getRandom(0, 255) + "," + J.getRandom(0, 255) + ")";
    }, 1000)
}

function drawMoveCursor(){
    for(var i = 0; i < rectArr.length; i ++){
        var rect = rectArr[i];
        var start = rect.start;
        var end = rect.end;
        if(mouse.x > start.x+deviation && mouse.y > start.y+deviation && mouse.x < end.x-deviation && mouse.y < end.y-deviation){
            canvas.css("cursor", "move");
            selectRect = i;
            return;
        }
    }
    canvas.css("cursor", "crosshair");
}

function drawRect(start, end){
    context.strokeRect(start.x, start.y, end.x - start.x, end.y - start.y);
}

function dragDone(){
    if(!end || Math.abs(start.x - end.x)<10 || Math.abs(start.y - end.y)<10){
        return;
    }
    rectArr.push({start:{x:start.x,y:start.y}, end:{x:end.x,y:end.y}});
    
    var imgData = context.getImageData(start.x, start.y, end.x - start.x, end.y - start.y);
    var tempCanvas = $("<canvas></canvas>");
    tempCanvas.attr("width", imgData.width);
    tempCanvas.attr("height", imgData.height);
    var tempContext = tempCanvas.get(0).getContext("2d");
    tempContext.putImageData(imgData, 0, 0);
    
    var newImg = $("<img />");
    newImg.attr("src", tempCanvas.get(0).toDataURL());
    $("#outputDiv").append(newImg);
    tempCanvas.remove();
    
    upload(newImg.attr("src"));
    
    start = null;
    end = null;
}

function upload(dataURL){
    var url = contextPath + "/setting/upload";
    
    var base64Data = dataURL.replace(/^data:image\/\w+;base64,/, "");
    
    $("#uploadData").val(base64Data);
    $("#uploadName").val("hehe");
    $("#uploadType").val("3");
    var form = $('#uploadForm').ajaxSubmit({});
    var xhr = form.data('jqxhr');
    xhr.done(function() {
        console.log(xhr.responseText)
    });
    
    
//    $.post(url, data, function(data, textStatus, jqXHR) {
//        
//    });

//    $.ajax({
//        url : url,
//        data : data,
//        type : "post",
//        success : function(result) {
//            console.log(result);
//        }
//    });
}

function go() {


}

function copyImg() {

    $("#output").attr("src", canvas.toDataURL());
}

function loadImg(src, callback) {
    var image = new Image();
    image.src = src;
    if (image.complete) { 
        callback.call(this);
        return image; 
    }
    image.onload = function() {
        callback.call(this);
    };
    return image;
}