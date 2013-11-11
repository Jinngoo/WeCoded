<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/jinva" prefix="jinva" %>  
<c:set var="JQUERY" value="${pageContext.request.contextPath}/js/util/jquery-1.8.3.min.js" />
<c:set var="JQUERY_COOKIE" value="${pageContext.request.contextPath}/js/util/jquery.cookie.js" />
<c:set var="JQUERY_MD5" value="${pageContext.request.contextPath}/js/util/jquery.md5.js" />
<c:set var="AJAXANYWHERE" value="${pageContext.request.contextPath}/js/util/aa.js" />
<c:set var="BOOTSTRAP_JS" value="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" />
<c:set var="BOOTSTRAP_CSS" value="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dining</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/popoverButton.css" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>  
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/util/jinva.js"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
</head>
<body>


	<jinva:PopoverButton popoverTitle="title" content="content" popoverContent="popoverContent" imgUrl="${pageContext.request.contextPath}/image/touxiang.jpg">
	</jinva:PopoverButton>
	
	
	
</body>
</html>