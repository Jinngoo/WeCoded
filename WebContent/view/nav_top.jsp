<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
	<nav class="navbar navbar-default navbar-inverse" role="navigation">
		<div class="container" style="width: 1200px;">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">定要子系统</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="javascript:content('main_news');">首页</a></li>
				<li><a href="javascript:content('main_dining');">吃的</a></li>
				<li><a href="javascript:content('main_message');">消息(x)</a></li>
				<li><a href="javascript:content('main_setting');">设置</a></li>
			</ul>
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<p class="navbar-text">${sessionScope.user.name}</p>
				<li><a href="${CONTEXT_PATH}/signout">SignOut</a></li>
			</ul>
		</div>
	</nav>

