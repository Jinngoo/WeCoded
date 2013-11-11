<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
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
    <title>Upload image</title>
    <link rel="stylesheet" type="text/css" href="${BOOTSTRAP_CSS}" />
    <script type="text/javascript" src="${JQUERY}"></script>
    <script type="text/javascript" src="${JQUERY_COOKIE}"></script>
    <script type="text/javascript" src="${JQUERY_MD5}"></script>
    <script type="text/javascript" src="${AJAXANYWHERE}"></script>
    <script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
</head>
<body>

	<img src="../image/01.jpg" width="180px" height="180px" />
	<form class="navbar-form">
		<input type="file" >
	</form>

</body>
</html>