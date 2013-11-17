<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
	<script>
		function reloadUserAvatar(){
		    $("#userAvatar").attr("src", $("#userAvatar").attr("src"));
		}
	</script>
	<nav class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container" style="max-width: 1300px;">
			<ul class="nav navbar-nav navbar-left">
			<img src="${RESOURCE}/image/common/favicon.ico" style="width: 32px; height: 32px; margin-top: 9px; margin-left: 3px; margin-right: 7px" />
			</ul>
			<div class="navbar-header">
				<a class="navbar-brand" href="#"><spring:message code="main.title" /></a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${CONTEXT_PATH}/news">首页</a></li>
				<li><a href="${CONTEXT_PATH}/dining">吃的</a></li>
				<li><a href="${CONTEXT_PATH}/message">消息(x)</a></li>
				<li><a href="${CONTEXT_PATH}/setting">设置</a></li>
			</ul>
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<p class="navbar-text">
					<span id="topNickName">${sessionScope.user.nickname}</span>&nbsp;(${sessionScope.user.name})
				</p>
				<li><a href="${CONTEXT_PATH}/signout">SignOut</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<a href="${CONTEXT_PATH}/tool/uploadImage/1/${sessionScope.user.id}?close=1" target="_blank">
					<img id="userAvatar" src="${CONTEXT_PATH}/getImage/1/${sessionScope.user.id}" style="width:50px; height:50px; cursor: pointer" title="换头像" />
				</a>
			</ul>
		</div>
	</nav>
	<div style="height:80px"></div>

