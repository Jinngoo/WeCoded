<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>出错啦</title>
    	<%@ include file="../head.jsp"%>  
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<!-- Css -->
		<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
		<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
		<link href="${FONT_AWESOME_CSS}" rel="stylesheet" media="screen">
		
		<!-- Js -->
		<script type="text/javascript" src="${JQUERY}"></script>
		<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	
	</head>
	<body>
		<%@ include file="../nav_top.jsp" %>
        
        <div class="container">
            <h1>
                <c:out value="${message }" />
            </h1>
        </div>
        
        <%@ include file="../nav_bottom.jsp" %>
	</body>
</html>
