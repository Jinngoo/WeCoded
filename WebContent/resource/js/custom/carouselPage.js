$(document).ready(function(){
    $("div.carouselPage").each(function(){
        var carouselPage = $(this);
        var pageDataProvider = carouselPage.attr("pageDataProvider");
        carouselPage.find("div.active").append($("#" + pageDataProvider));
        carouselPage.on("slid.bs.carousel", function(){
            carouselPage.carousel("pause");
        });
//        console.log(carouselPage.find("a.carousel-control").length)
        carouselPage.find("a.carousel-control").css("background-image", null);
        //background-image
    });
});
function cp_flip(cpId, pageSize, pageNum, buttonId){
    var carouselPage = $("#" + cpId);
    var aaZone = carouselPage.attr("aaZone");
    var url = carouselPage.attr("url");
    var pageDataProvider = carouselPage.attr("pageDataProvider");
    
    AjaxAnywhere.prototype.showLoadingMessage = function() {
    };
    AjaxAnywhere.prototype.hideLoadingMessage = function() {
    };
    AjaxAnywhere.prototype.onAfterResponseProcessing = function () {
        carouselPage.find("div.item").each(function(){
            if(!$(this).hasClass("active")){
                $(this).empty().append($("#"+pageDataProvider));
            }
        });
        $("#"+buttonId).click();
    };
    
    url += url.indexOf("?") == -1 ? "?" : "&";
    url += 'pageSize=' + pageSize + '&pageNum=' + pageNum;
    ajaxAnywhere.getAJAX(url, aaZone);
}
function cp_prev(cpId, buttonId){
    var pageInfoProviderId = $("#" + cpId).attr("pageInfoProvider");
    var pageInfoProvider = $("#" + pageInfoProviderId);
    var pageSize = parseInt(pageInfoProvider.attr("pageSize"), 10);
    var pageNum = parseInt(pageInfoProvider.attr("pageNum"), 10);
    var totalCount = parseInt(pageInfoProvider.attr("totalCount"), 10);
    if(pageNum == 1){
        pageNum = Math.ceil(totalCount / pageSize);
    }else{
        pageNum -= 1;
    }
    cp_flip(cpId, pageSize, pageNum, buttonId);
}
function cp_next(cpId, buttonId){
    var pageInfoProviderId = $("#" + cpId).attr("pageInfoProvider");
    var pageInfoProvider = $("#" + pageInfoProviderId);
    var pageSize = parseInt(pageInfoProvider.attr("pageSize"), 10);
    var pageNum = parseInt(pageInfoProvider.attr("pageNum"), 10);
    var totalCount = parseInt(pageInfoProvider.attr("totalCount"), 10);
    if(pageNum == Math.ceil(totalCount / pageSize)){
        pageNum = 1;
    }else{
        pageNum += 1;
    }
    cp_flip(cpId, pageSize, pageNum, buttonId);
}