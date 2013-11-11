<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>News</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#mainContent").slideDown("fast");
	    	$('#newsCarousel').carousel({
				interval: 5000,
 			 	pause : ''
 			});
	    	$("#public_btn").click(function(){
	    		$("#public_topic").html("不支持啊不支持");
	    	});
	    });
    </script>
    <style type="text/css">
	</style>
</head>
<body>

	<div id="mainContent" style="display:none;margin-left:20px;">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#news" data-toggle="tab">新闻</a></li>
			<li><a href="#topic" data-toggle="tab">话题</a></li>
			<li><a href="#picture" data-toggle="tab">图片</a></li>
		</ul>
		
		<div class="tab-content">
		
			<!-- news -->
			<div class="tab-pane active span8" id="news">
				<div id="newsCarousel" class="carousel slide">
					<!-- Carousel items -->
					<div class="carousel-inner">
						<div class="active item">
							<img src="image/01.jpg"/>
							<div class="carousel-caption"><h4>制作中啊制作中</h4><p>愤怒地将花费血脖肉凤凰的荣誉撒弄死了的年深法定总分覆盖从难对付合同的呼声年深绿色阿隆索你疯啦</p></div>
						</div>
						<div class="item">
							<img src="image/02.jpg"/>
							<div class="carousel-caption"><h4>等一等呀等一等</h4><p>所开发的反馈撒了三大发的发发发生愤三大大声地三大大大大的是否打算发广告怒随碟附送</p></div>
						</div>
						<div class="item">
							<img src="image/03.jpg"/>
							<div class="carousel-caption"><h4>我勒个去就快了</h4><p>弄丢了看是第三大大的呢栏打开的卡的垃圾决赛中风格的枫叶的投交换机股份巨额听的歌手续入肌肤</p></div>
						</div>
					</div>
					<!-- Carousel nav -->
					<a class="carousel-control left" href="#newsCarousel" data-slide="prev">&lsaquo;</a>
					<a class="carousel-control right" href="#newsCarousel" data-slide="next">&rsaquo;</a>
				</div>
			</div>
			
			
			
			
			<!-- topic -->
			<div class="tab-pane" id="topic">
				<div class="control-group">
					<textarea class="input-xlarge" rows="4" id="public_topic"></textarea>
				</div>
				<div class="control-group">
					<button class="btn btn-primary" id="public_btn">发表</button>
				</div>
				<hr/>
			</div>
			
			<!-- picture -->
			<div class="tab-pane" id="picture">
				图片啊图片片
			</div>
		</div>
		
		
		
		
		
		
		
	</div>




</body>
</html>