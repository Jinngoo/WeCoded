<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>test</title>
    <%@ include file="head.jsp"%>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Css -->
	<link href="${BOOTSTRAP_CSS}" rel="stylesheet" media="screen">
	<link href="${BOOTSTRAP_THEME_CSS}" rel="stylesheet" media="screen">
	
	
	<!-- Js -->
	<script type="text/javascript" src="${JQUERY}"></script>
	<script type="text/javascript" src="${BOOTSTRAP_JS}"></script>
	<script type="text/javascript" src="${JQUERY_MD5}"></script>
	<script type="text/javascript" src="${JQUERY_COOKIE}"></script>
	
	<script type="text/javascript">
		var ws = null;
		function connect(){
			var url = "ws://localhost:8888/WeCoded/wsChatRoom?id=${sessionScope.user.id}";
			ws = new WebSocket(url);
			ws.onopen = function() {
				console.log("open");
			}
			ws.onmessage = function(e) {
				console.log(e.data.toString())
			}
			ws.onclose = function(e) {
	            console.log("closed");
	        }
	        ws.onerror = function(e) {
	        	console.log("error");
	        }
		}
		function send(){
			ws.send('hehe');
		}
		function disconnect(){
			if (ws != null) {
	            ws.close();
	            ws = null;
	        }
		}
		window.onbeforeunload = function(){
			if(ws){
				return "确认离开么？";
			}
		};
	</script>
</head>
<body onunload="disconnect();">

	<div class="container">
		<button class="btn btn-default" onclick="connect()">connect</button>
		<button class="btn btn-default" onclick="send()">send</button>
		<button class="btn btn-default" onclick="disconnect()">disconnect</button>
	</div>

</body>
</html>