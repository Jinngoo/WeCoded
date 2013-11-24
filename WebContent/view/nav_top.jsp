<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
	<c:set var="isSignin" value="${not empty sessionScope.user }"/>
	<style>
		.navbar {
			font-family: "微软雅黑";
		}
	</style>
	<nav class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container" style="max-width: 1300px;">
			<ul class="nav navbar-nav navbar-left">
				<img src="${RESOURCE}/image/common/favicon.ico" title="Jinn" style="cursor: pointer; width: 32px; height: 32px; margin-top: 9px; margin-left: 3px; margin-right: 7px" />
			</ul>
			<div class="navbar-header">
				<a class="navbar-brand" href="#"><spring:message code="main.title" /></a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${CONTEXT_PATH}/news">首页</a></li>
				<li><a href="${CONTEXT_PATH}/dining" id="diningLink">吃的</a></li>
				<li><a href="${CONTEXT_PATH}/chatRoom" id="chatRoomLink">聊天室</a></li>
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
		</div>
	</nav>
	<div style="height:80px"></div>
	<script>
		function reloadUserAvatar(){
		    $("#userAvatar").attr("src", $("#userAvatar").attr("src"));
		}
		function top_reloadChatRoomUserCount(count){
        	if (count != null) {
                $("#chatRoomLink").html("聊天室(<span style='color:white;font-weight:bold;'>" + count + "</span>)");
            } else {
                var url = contextPath + "/chatRoom/userCount";
                $.get(url, function(result) {
                    $("#chatRoomLink").html("聊天室(<span style='color:white;font-weight:bold;'>" + result + "</span>)");
                });
            }
        }
		function top_reloadorderProviderCount(count){
        	if (count != null) {
                $("#diningLink").html("吃的(<span style='color:white;font-weight:bold;'>" + count + "</span>)");
            } else {
                var url = contextPath + "/dining/orderProviderCount";
                $.get(url, function(result) {
                    $("#diningLink").html("吃的(<span style='color:white;font-weight:bold;'>" + result + "</span>)");
                });
            }
        }
        $(document).ready(function() {
            $("#diningLink").mouseover(function() {
                top_reloadorderProviderCount();
            });
            $("#chatRoomLink").mouseover(function() {
                top_reloadChatRoomUserCount();
            });
            top_reloadorderProviderCount();
            top_reloadChatRoomUserCount();
            setInterval(function() {
                top_reloadorderProviderCount();
                top_reloadChatRoomUserCount();
            }, 1000 * 60);
        });
        $(window).focus(function() {
            top_reloadorderProviderCount();
            top_reloadChatRoomUserCount();
        });
    </script>

