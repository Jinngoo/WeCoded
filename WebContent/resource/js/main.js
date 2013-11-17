

$(document).ready(function(){
    $("#newsCarousel").find("div.active").children(".carousel-caption").append($("#result"));
    $("#newsCarousel").on("slid.bs.carousel", function(){
        $(this).carousel('pause')
    });
});
function flip(pageSize, pageNum, buttonId){
    AjaxAnywhere.prototype.showLoadingMessage = function() {
    };
    AjaxAnywhere.prototype.hideLoadingMessage = function() {
    };
    AjaxAnywhere.prototype.onAfterResponseProcessing = function () {
        $("#newsCarousel").find("div.item").each(function(){
            if(!$(this).hasClass("active")){
                $(this).children(".carousel-caption").empty().append($("#result"));
            }
        });
        $("#"+buttonId).click();
    };
    var url = contextPath + '/test?pageSize=' + pageSize + '&pageNum=' + pageNum;
    console.log(url)
    ajaxAnywhere.getAJAX(url, "testZone");
}
function prev(buttonId){
    var pageSize = parseInt($("#pageInfo").attr("pageSize"), 10);
    var pageNum = parseInt($("#pageInfo").attr("pageNum"), 10);
    var totalCount = parseInt($("#pageInfo").attr("totalCount"), 10);
    if(pageNum == 1){
        pageNum = Math.ceil(totalCount / pageSize);
    }else{
        pageNum -= 1;
    }
    flip(pageSize, pageNum, buttonId);
}
function next(buttonId){
    var pageSize = parseInt($("#pageInfo").attr("pageSize"), 10);
    var pageNum = parseInt($("#pageInfo").attr("pageNum"), 10);
    var totalCount = parseInt($("#pageInfo").attr("totalCount"), 10);
    if(pageNum == Math.ceil(totalCount / pageSize)){
        pageNum = 1;
    }else{
        pageNum += 1;
    }
    flip(pageSize, pageNum, buttonId);
}