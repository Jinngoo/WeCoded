var jcrop_api = null;
var scaling = null;

$(document).ready(function(){
    
    $("#fileChooser").change(function(){
        fileChoosen();
    });
    
});

function chooseImg(){
    $("#fileChooser").click();
}

function saveImg(btn){
    if(jcrop_api == null){
        onError("请选择一张图片啊");
        return;
    }
    $(btn).button("loading");
    //截取的大小、位置，有缩放因素
    var select = jcrop_api.tellSelect();
    var x = select.x/scaling;
    var y = select.y/scaling;
    var width = select.w/scaling; 
    var height = select.h/scaling;
    
    //输出大小
    var outWidth = $("#preview").width(); 
    var outHeight = $("#preview").height();
    
    var canvas = $("<canvas></canvas>");
    canvas.attr("width", outWidth);
    canvas.attr("height", outHeight);
    var context = canvas.get(0).getContext("2d");
    context.drawImage($("#jcropTarget").get(0), x, y, width, height, 0, 0, outWidth, outHeight);
    var dataURL = canvas.get(0).toDataURL("image/jpeg");
    var base64Data = dataURL.replace(/^data:image\/\w+;base64,/, "");
    canvas.remove();
    
    $("#uploadData").val(base64Data);
    var form = $('#uploadForm').ajaxSubmit({});
    var xhr = form.data('jqxhr');
    xhr.done(function() {
        var result = xhr.responseText;
        if(result == "success"){
            $('.error').html('保存成功').show();
            
            var callback = J.getUrlParam('callback');
            if(callback && opener){
                opener[callback].call();
            }
            
            var uploadType = $("#uploadType").val();
            if(uploadType == "1"){
                reloadUserAvatar();
                if (opener && opener.reloadUserAvatar) {
                    opener.reloadUserAvatar();
                }
            }
            
            setTimeout(function(){
                var close = J.getUrlParam('close');
                if (close) {
                    window.opener = null;
                    window.close();
                } else {
                    $(btn).button('reset')
                }
            }, 500);
        }else{
            alert('error');
        }
    });
    
}

function showPreview(coords, realWidth, realHeight) {
    var previewImg = $("#preview").children("img");
    var rx = $("#preview").width() / coords.w;
    var ry = $("#preview").height() / coords.h;

    previewImg.css({
        width : Math.round(rx * realWidth) + 'px',
        height : Math.round(ry * realHeight) + 'px',
        marginLeft : '-' + Math.round(rx * coords.x) + 'px',
        marginTop : '-' + Math.round(ry * coords.y) + 'px'
    });
}

function onError(msg){
    $('.error').html(msg).show();
    J.flicker(1000, $('.error'), 3);
    $("#jcropTarget").attr("src", "").width(1).height(1);
    $("#preview").children("img").attr("src", "");
    if (jcrop_api != null) {
        jcrop_api.destroy();
        jcrop_api = null;
    }
}

function fileChoosen() {
    // get selected file
    var oFile = $('#fileChooser')[0].files[0];
    // hide all errors
    $('.error').hide();
    // check for image type (jpg and png are allowed)
    var rFilter = /^(image\/jpeg|image\/png)$/i;
    if (! rFilter.test(oFile.type)) {
        onError("请选择jpg、png类型图像文件");
        return;
    }

    // check for file size
    if (oFile.size > 1024 * 1024) {
        onError("文件超过1MB，请选择一张稍小点的图片");
        return;
    }

    var jcropTarget = $("#jcropTarget");
    var imageDom = jcropTarget.get(0);
    
    var pWidth = jcropTarget.parent().width();
    var pHeight = jcropTarget.parent().height();

    // prepare HTML5 FileReader
    var oReader = new FileReader();
        oReader.onload = function(e) {

        // e.target.result contains the DataURL which we can use as a source of the image
        imageDom.src = e.target.result;
        imageDom.onload = function () { // onload event handler
            $(imageDom).fadeIn(500);
            var naturalWidth = imageDom.naturalWidth;
            var naturalHeight = imageDom.naturalHeight;
            
            var scalingW = pWidth / naturalWidth;
            var scalingH = pHeight / naturalHeight;

            scaling = Math.min(scalingW, scalingH);
            var realWidth = naturalWidth * scaling;
            var realHeight = naturalHeight * scaling;
            
            var previewImg = $("#preview").children("img");
            previewImg.attr("src", $(imageDom).attr("src"));
            $(imageDom).width(realWidth).height(realHeight);

            
            // display some basic image info
//            var sResultFileSize = bytesToSize(oFile.size);
//            $('#filesize').val(sResultFileSize);
//            $('#filetype').val(oFile.type);
//            $('#filedim').val(imageDom.naturalWidth + ' x ' + imageDom.naturalHeight);

            // Create variables (in this scope) to hold the Jcrop API and image size
            var  boundx, boundy;

            // destroy Jcrop if it is existed
            if (jcrop_api != null) 
                jcrop_api.destroy();

            // initialize Jcrop
            jcropTarget.Jcrop({
                minSize: [64, 64], // min crop size
                aspectRatio : 1, // keep aspect ratio 1:1
                bgFade: true, // use fade effect
                bgOpacity: 0.6, // fade opacity
                onChange: function(coords){
                    showPreview(coords, realWidth, realHeight);
                },
                onSelect: function(coords){
                    showPreview(coords, realWidth, realHeight);
                },
                onRelease: function(){}
            }, function(){
                // use the Jcrop API to get the real image size
                var bounds = this.getBounds();
                boundx = bounds[0];
                boundy = bounds[1];
                // Store the Jcrop API in the jcrop_api variable
                jcrop_api = this;
                // select 1/2 center area
                var minLength = Math.min(realWidth, realHeight);
                var center = {x: realWidth/2, y: realHeight/2};
                this.setSelect([center.x-minLength/4, center.y-minLength/4, center.x+minLength/4, center.y+minLength/4]);
            });
        };
    };
    // Download by http://www.codefans.net  
    // read selected file as DataURL
    oReader.readAsDataURL(oFile);
}