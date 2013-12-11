<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
	<c:set var="isSignin" value="${not empty sessionScope.user }"/>
	<style>
		.navbar {
			font-family: "微软雅黑";
		}
	</style>
	<header id="top_navbar" class="navbar navbar-inverse navbar-fixed-top" role="banner">
		<div class="container" >
				<ul class="nav navbar-nav navbar-left" style="margin-left: 0px;">
					<img src="${RESOURCE}/image/common/favicon.ico" title="Jinn" style="cursor: pointer; width: 32px; height: 32px; margin-top: 9px; margin-left: 3px; margin-right: 7px" />
				</ul>
			<div class="navbar-header">
				<a class="navbar-brand" href="#"><spring:message code="main.title" /></a>
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			      </button>
			</div>
			<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
				<ul class="nav navbar-nav">
					<li><a href="${CONTEXT_PATH}/news">首页</a></li>
					<li><a href="${CONTEXT_PATH}/dining" id="diningLink">吃的</a></li>
					<li><a href="${CONTEXT_PATH}/chatRoom" id="chatRoomLink">聊天室</a></li>
					<li><a href="${CONTEXT_PATH}/game">游戏</a></li>
					<li><a href="${CONTEXT_PATH}/message">消息(x)</a></li>
					<li><a href="${CONTEXT_PATH}/setting">设置</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${isSignin}">
						<p class="navbar-text">
							<span id="topNickName">${sessionScope.user.nickname}</span>
						</p>
						<li><a href="${CONTEXT_PATH}/signout">SignOut</a></li>
					</c:if>
					<c:if test="${not isSignin}">
						<li><a href="${CONTEXT_PATH}/login">SignIn</a></li>
					</c:if>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${isSignin}">
						<a href="${CONTEXT_PATH}/tool/uploadImage/1/${sessionScope.user.id}?close=1" target="_blank">
							<img id="userAvatar" src="${CONTEXT_PATH}/getImage/1/${sessionScope.user.id}" style="width:50px; height:50px; cursor: pointer" title="换头像" />
						</a>
					</c:if>
				</ul>
			</nav>
		</div>
	</header>
	<div id="top_occupying" style="height:80px"></div>
	<script>
		var top_reloadInterval = null;
		function reloadUserAvatar(){
		    $("#userAvatar").attr("src", $("#userAvatar").attr("src"));
		}
		function top_reloadChatRoomUserCount(count){
        	if (count != null) {
                $("#chatRoomLink").html("聊天室(<span style='color:white;font-weight:bold;'>" + count + "</span>)");
            } else {
                var url = contextPath + "/chatRoom/userCount";
                $.getJSON(url).success(function(result){
                   	$("#chatRoomLink").html("聊天室(<span style='color:white;font-weight:bold;'>" + result.count + "</span>)");
                }).error(function(){
                    clearReloadInterval();
                });
            }
        }
		function top_reloadOrderProviderCount(count){
        	if (count != null) {
                $("#diningLink").html("吃的(<span style='color:white;font-weight:bold;'>" + count + "</span>)");
            } else {
                var url = contextPath + "/dining/orderProviderCount";
                $.getJSON(url).success(function(result){
               		$("#diningLink").html("吃的(<span style='color:white;font-weight:bold;'>" + result.count + "</span>)");
                }).error(function(){
                    clearReloadInterval();
                });
            }
        }
        $(document).ready(function() {
            $("#diningLink").mouseover(function() {
                top_reloadOrderProviderCount();
            });
            $("#chatRoomLink").mouseover(function() {
                top_reloadChatRoomUserCount();
            });
            top_reloadOrderProviderCount();
            top_reloadChatRoomUserCount();
            startReloadInterval();
        });
        function startReloadInterval(){
            if(top_reloadInterval != null){
                return;
            }
            top_reloadInterval = setInterval(function() {
                top_reloadOrderProviderCount();
                top_reloadChatRoomUserCount();
            }, 1000 * 60);
        }
        function clearReloadInterval(){
            if(top_reloadInterval != null){
                clearInterval(top_reloadInterval);
                top_reloadInterval = null;
            }
        }
        $(window).focus(function() {
            top_reloadOrderProviderCount();
            top_reloadChatRoomUserCount();
            startReloadInterval();
        });
        $(window).blur(function(){
        	clearReloadInterval();
        });
        $(window).resize(function(){
            $("#top_occupying").height(30 + $("#top_navbar").height());
        });
    </script>

