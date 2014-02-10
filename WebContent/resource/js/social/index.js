/**
 * 
 */
$(document).ready(function() {
	$("#mainContent").slideDown("fast");
	 
	$("#public_btn").click(function(){
		$("#public_topic").html("不支持啊不支持");
	});
	
	loadChannel();
	
});

function loadChannel(){
	var url = contextPath + "/social/loadChannel";
	$.getJSON(url, {}, function(result){
		var rss = result.rss163;
		var channels = rss.channels;
		var output = $("#newsType").empty();
		for(var i = 0; i < channels.length; i ++){
			var channel = channels[i];
			var subs = channel.sub;
			output.append("<hr/><b>" + channel.name + "</b><br/>");
			for(var j = 0; j < subs.length; j ++){
				var sub = subs[j];
				$("<div class='rss'></div>").css({
					"cursor" : "pointer"
				}).html(sub.name).attr("rss-url", rss.uri + channel.uri + sub.uri).appendTo(output);
			}
		}
		output.children("div.rss").click(function(){
			var rssUrl = $(this).attr("rss-url");
			loadRss(rssUrl);
		});
	});
}

function loadRss(rssUrl){
	var url = contextPath + "/social/loadRss";
	var params = {"rssUrl" : rssUrl};
	$.getJSON(url, params, function(channel){
		var output = $("#newsContent").empty();
		output.append("<hr/><hr/>" + channel.description);
		var items = channel.items;
		for(var i = 0; i < items.length; i ++){
			var item = items[i];
			var a = $("<a target='_blank'></a>").html(item.title + "<br/>" + item.description).attr("src", item.link).css("cursor", "pointer");
			output.append("<hr/>").append(a);
		}
	});
}