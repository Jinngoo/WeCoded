<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="./base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
    <%@ include file="./head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="keywords" content="jinnan,jinn">
	<meta http-equiv="description" content="jinn">
	
	<!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	<link href="${JQUERY_GRIDLY_CSS}" rel="stylesheet" media="screen">
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${AJAXANYWHERE}"></script>
	<script type="text/javascript" src="${JQUERY_GRIDLY_JS}"></script>
	
    <script type="text/javascript" src="${RESOURCE}/js/custom/carouselPage.js"></script>
</head>
<body>

	<%@ include file="nav_top.jsp" %>
	<div class="container">
	
		<style type="text/css">
		  .gridly {
		    position: relative;
		    width: 960px;
		  	border: 1px solid gray;
		  }
		  .brick{
		  	border: 1px solid gray;
		  }
		  .brick.small {
		    width: 100px;
		    height: 100px;
		  }
		  .brick.large {
		    width: 100px;
		    height: 100px;
		  }
		</style>
		<div class="gridly">
		  <div class="brick small">1</div>
		  <div class="brick small">2</div>
		  <div class="brick large">3</div>
		  <div class="brick small">4</div>
		  <div class="brick small">5</div>
		  <div class="brick large">6</div>
		  <div class="brick small">7</div>
		  <div class="brick large">8</div>
		  <div class="brick small">9</div>
		  <div class="brick large">10</div>
		</div>
		<script>
		  $('.gridly').gridly({
		    gutter: 10 // px
		  });
		</script>
	
		<button class="btn btn-default" onclick="test()">test</button>

		<aa:zone name="testZone">
			<div id="pageInfo" pageSize="${pageSize}" pageNum="${pageNum}" totalCount="${totalCount}"></div>
			<div id="result">
				<h3 style="color:green">
				<c:forEach items="${result }" var="item">
					${item}
				</c:forEach>
				</h3>
				<h2>sdsdsdsdsdsdsddsdsd</h2>
			</div>
		</aa:zone>
		<div class='well'>
		<jn:CarouselPage id="newsCarousel" style="width:870px;height:300px;" url="${CONTEXT_PATH}/test" aaZone="testZone" pageDataProvider="result" pageInfoProvider="pageInfo" pageSize="3"/>
		</div>
<%--
		<div id="newsCarousel" class="carousel slide" style="width:870px">
			<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="${RESOURCE}/image/02.jpg"/>
					<div class="carousel-caption"></div>
				</div>
				<div class="item">
					<img src="${RESOURCE}/image/02.jpg"/>
					<div class="carousel-caption"></div>
				</div>
			</div>
			<!-- Carousel nav -->
			<a id="prePage" class="carousel-control left" href="#" onclick="prev('prePageReal')"><span class="glyphicon glyphicon-chevron-left"></span></a>
			<a id="nextPage" class="carousel-control right" href="#" onclick="next('nextPageReal')"><span class="glyphicon glyphicon-chevron-right"></span></a>
			<a id="prePageReal" class="carousel-control left" href="#newsCarousel" data-slide="prev" style="display:none"></a>
			<a id="nextPageReal" class="carousel-control right" href="#newsCarousel" data-slide="next" style="display:none"></a>
		</div>
 --%>
 
	</div>
	<%@ include file="nav_bottom.jsp" %>
</body>
</html>