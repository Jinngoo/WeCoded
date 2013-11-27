var statisticsChanged = false;
$(document).ready(function(){
    $("#mainContent").slideDown("fast");
    $("#confirmModal").modal({
        backdrop: true,
        keyboard: true,
        show: false
    });
    dealTotalPrice();
    $("#orderList").slideDown("slow");
});
//$(window).bind("scroll", function(){ });

function dealTotalPrice(){
    var totalPrice = 0;
    $(".totalPrice").each(function(){
        totalPrice = J.plus(totalPrice, $(this).html()).toFixed(2);
    });
    $("#totalPriceBody").append("<tr><td colSpan=\"6\"></td><td>" + totalPrice + "</td></tr>");
}

function changeStatistics(button){
    if(!statisticsChanged){
        showOrderStatistics();
        statisticsChanged = true;
        $(button).html("逐条查看");
    }else{
        recoverTable();
        statisticsChanged = false;
        $(button).html("合并查看");
    }
}

function showOrderStatistics(){
    var statistics = {};
    $("#orderListBody tr").each(function(){
        var tds = $(this).children("td");
        var no = $(tds[0]).html();
        var user = $(tds[1]).html();
        var restaurant = $(tds[2]).html();
        var dish = $(tds[3]).html();
        var count = $(tds[4]).html();
        var price = $(tds[5]).html();
        var totalPrice = $(tds[6]).html();
        var order = {
            "user":user,
            "restaurant":restaurant,
            "dish":dish,
            "count":count,
            "price":price,
            "totalPrice":totalPrice
        };
        if(statistics[restaurant]){
            if(statistics[restaurant][dish]){
                statistics[restaurant][dish].user += ", "+user;
                statistics[restaurant][dish].count = J.plus(statistics[restaurant][dish].count, count);
                statistics[restaurant][dish].totalPrice = J.plus(statistics[restaurant][dish].totalPrice, totalPrice).toFixed(2);
            }else{
                statistics[restaurant][dish] = order;
            }
        }else{
            statistics[restaurant] = {};
            statistics[restaurant][dish] = order;
        }
    });

    $("#orderList").slideUp("fast", function(){
        $("tr.orginTr").hide();
        var index = 1;
        for(var restaurant in statistics){
            for(var dish in statistics[restaurant]){
                var tr = "<tr class=\"success hiddenTr\">";
                tr += "<td>" + index++ + "</td>";
                tr += "<td>" + statistics[restaurant][dish].user + "</td>";
                tr += "<td>" + statistics[restaurant][dish].restaurant + "</td>";
                tr += "<td>" + statistics[restaurant][dish].dish + "</td>";
                tr += "<td>" + statistics[restaurant][dish].count + "</td>";
                tr += "<td>" + statistics[restaurant][dish].price + "</td>";
                tr += "<td>" + statistics[restaurant][dish].totalPrice + "</td>";
                tr += "</tr>";
                $("#orderListBody").append(tr);
            }
        }
        $("tr.hiddenTr").show();
        $(this).slideDown("fast");
    });
}

function recoverTable(){
    $("#orderList").slideUp("fast", function(){
        $("tr.hiddenTr").remove();
        $("tr.orginTr").show();
        $(this).slideDown("fast");
    });
}

function getPostion(id){
    var  $$ = function(id){ return document.getElementById(id); },
         pos_ = { left: $$(id).offsetLeft, top: $$(id).offsetTop } ,            
         win_ = window;
     while(win_.frameElement) {
         var left_ = win_.frameElement.offsetLeft + (win_.frameElement.offsetWidth - win_.frameElement.clientWidth) / 2,
             top_ = win_.frameElement.offsetTop + (win_.frameElement.offsetHeight - win_.frameElement.clientHeight) / 2;
         
         pos_.left += win_.frameElement.frameBorder == "1" ? left_ + 2 : left_;
         pos_.top += win_.frameElement.frameBorder == "1" ? top_ + 2 : top_;
         win_ = win_.parent;
     }
     return pos_;
}

function goback(backUrl){
    if(!backUrl){
        backUrl = $("#goBack").attr("backUrl");
    }
    $("#mainContent").slideUp("fast", function(){
        window.location.href = decodeURIComponent(backUrl);
    });
}
function cancelProvide(orderProviderId, isConfirm){
    $("#confirmBtn").unbind("click");
    if(isConfirm){
        var url = contextPath + "/dining/cancelProvide/" + orderProviderId;
        $.post(url, {}, function(data, textStatus, jqXHR){
            var code = data;
            if(code == "success"){
                goback();
            }else if(code == "error"){
                alert("An error occured");
            }else{
                alert("Unknown error occured");
            }
        });
    }else{
        $("#confirmTip").html("确定取消么？");
        $("#confirmModal").modal("show");
        $("#confirmBtn").click(function(){
            cancelProvide(orderProviderId, true);
        });
    }
}
function finishProvide(orderProviderId, isConfirm){
    $("#confirmBtn").unbind("click");
    if(isConfirm){
        var url = contextPath + "/dining/finishProvide/" + orderProviderId;
        $.post(url, {}, function(data, textStatus, jqXHR){
            var code = data;
            if(code == "success"){
                window.location.href = window.location.href;  
            }else if(code == "error"){
                alert("An error occured");
            }else{
                alert("Unknown error occured");
            }
        });
    }else{
        $("#confirmTip").html("确定结束么？");
        $("#confirmModal").modal("show");
        $("#confirmBtn").click(function(){
            finishProvide(orderProviderId, true);
        });
    }
}