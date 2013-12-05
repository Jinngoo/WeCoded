var dishJson = {};
$(document).ready(function(){
	$("#mainContent").slideDown("fast");
	initOrderList();
	refreshChooseList();
});
function goback(backUrl){
	$("#mainContent").slideUp("fast", function(){
		window.location.href = decodeURIComponent(backUrl);
	});
}

function plusDish(dishId, dishNum, needRefresh){
	if(J.isEmpty(dishNum)){
		dishNum = 1;
	}
	var countBar = $("#dish_" + dishId).children("div.count_bar");
	var countSpan = countBar.children("span");
	var count = countSpan.html();
	if(J.isEmpty(count)){
		count = 0;
	}
	count = parseInt(count, 10) + parseInt(dishNum,10);
	if(parseInt(count, 10) > 0){
    	var minusBtn = $("#dish_" + dishId).children("div.tool_bar").children("i:first");
		countBar.show();
		minusBtn.show();
	}
	eval("dishJson = $.extend({}, dishJson, {'" + dishId + "':count});");
	countSpan.html(count);
	if(needRefresh){
		refreshChooseList();
	}
}

function minusDish(dishId, dishNum, needRefresh){
	if(J.isEmpty(dishNum)){
		dishNum = 1;
	}
	var countBar = $("#dish_" + dishId).children("div.count_bar");
	var countSpan = countBar.children("span");
	var count = countSpan.html();
	if(J.isEmpty(count)){
		return;
	}
	count = parseInt(count, 10) - parseInt(dishNum,10);
	if(count <= 0){
    	var minusBtn = $("#dish_" + dishId).children("div.tool_bar").children("i:first");
		countBar.hide();
		minusBtn.hide();
	}
	eval("dishJson = $.extend({}, dishJson, {'" + dishId + "':count});");
	countSpan.html(count);
	if(needRefresh){
		refreshChooseList();
	}
}

function refreshChooseList(){
	var html = "";
	var height = 0;
	var totalPrice = 0;
	for(var dishId in dishJson){
		var count = dishJson[dishId];
		if(count == 0){
			continue;
		}
		var name = $("#dishName_" + dishId).val();
		var price = $("#dishPrice_" + dishId).val();
		var thisPrice = Math.round(price*count*100)/100;
    	html += "<div style=\"display:block;font-size:12px;\">" + name + "&nbsp;:&nbsp;" + count + "&nbsp;份";
    	html += "&nbsp;&nbsp;-&nbsp;&nbsp;花费&nbsp;:&nbsp;" + price + "&nbspx&nbsp" + count + "&nbsp=&nbsp" + thisPrice + "&nbsp;元";
    	html += "</div>";
    	height += 20;
    	totalPrice += thisPrice;
    	totalPrice = Math.round(totalPrice*100)/100;
	}
	html = "<div style=\"font-size:14px;\">我共选择了&nbsp;:&nbsp;" + totalPrice + "&nbsp;元</div>" + html;
	html += "<hr/>";
	$("#iChoosed").html(html);
	$("#iChoosed").animate({height:60+height+"px"});
}
