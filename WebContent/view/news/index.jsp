<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>News</title>
    <%@ include file="../head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	
	<script type="text/javascript" src="${RESOURCE}/js/util/jinva.js"></script>
	
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#mainContent").slideDown("fast");
	 
	    	$("#public_btn").click(function(){
	    		$("#public_topic").html("不支持啊不支持");
	    	});
	    });
    </script>
    <style type="text/css">
	</style>
</head>
<body>
	<%@ include file="../nav_top.jsp" %>
	<div class="container">
		<div id="mainContent" style="display:none;margin-left:20px;">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#news" data-toggle="tab">新闻</a></li>
				<li><a href="#topic" data-toggle="tab">话题</a></li>
				<li><a href="#picture" data-toggle="tab">图片</a></li>
			</ul>
			
			<div class="tab-content">
			
				<!-- news -->
				<div class="tab-pane active span8" id="news">
					<div id="newsCarousel" class="carousel slide" data-ride="carousel" style="width:870px">
						<!-- Indicators -->
						<ol class="carousel-indicators">
							<li data-target="#newsCarousel" data-slide-to="0" class="active"></li>
							<li data-target="#newsCarousel" data-slide-to="1"></li>
							<li data-target="#newsCarousel" data-slide-to="2"></li>
						</ol>
						<!-- Wrapper for slides -->
						<div class="carousel-inner">
							<div class="item active">
								<img src="${RESOURCE}/image/01.jpg"/>
								<div class="carousel-caption"><h4>制作中啊制作中</h4><p>愤怒地将花费血脖肉凤凰的荣誉撒弄死了的年深法定总分覆盖从难对付合同的呼声年深绿色阿隆索你疯啦</p></div>
							</div>
							<div class="item">
								<img src="${RESOURCE}/image/02.jpg"/>
								<div class="carousel-caption"><h4>等一等呀等一等</h4><p>所开发的反馈撒了三大发的发发发生愤三大大声地三大大大大的是否打算发广告怒随碟附送</p></div>
							</div>
							<div class="item">
								<img src="${RESOURCE}/image/03.jpg"/>
								<div class="carousel-caption"><h4>我勒个去就快了</h4><p>弄丢了看是第三大大的呢栏打开的卡的垃圾决赛中风格的枫叶的投交换机股份巨额听的歌手续入肌肤</p></div>
							</div>
						</div>
						<!-- Carousel nav -->
						<a class="carousel-control left" href="#newsCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
						<a class="carousel-control right" href="#newsCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
					</div>
				</div>
				
				
				<!-- topic -->
				<div class="tab-pane" id="topic">
					<form class="form-horizontal" role="form" action="#" style="width:600px;margin-left: 20px; margin-top: 20px">
						<div class="form-group">
							<textarea class="form-control" rows="4" id="public_topic"></textarea>
						</div>
						<div class="form-group" style="text-align:right">
							<button class="btn btn-primary" id="public_btn">发表</button>
						</div>
					</form>
					<hr/>
				</div>
				
				
				<!-- picture -->
				<div class="tab-pane" id="picture">
					图片啊图片片
				</div>
				
			</div>
		</div>
	</div>



</body>
</html>