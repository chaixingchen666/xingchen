<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
	<script type="text/javascript">


		var webSocket = null;
		var tryTime = 0;
		$(function() {
			initSocket();

			window.onbeforeunload = function() {
				//离开页面时的其他操作
			};
		});

		/**
		 * 初始化websocket，建立连接
		 */
		function initSocket() {
 			 var url = "ws://localhost:8080/skydesk/websocket/" + 123 + "/" + 1234; 
		        if ('WebSocket' in window) { 
		        	webSocket = new WebSocket(url); 
		        } else if ('MozWebSocket' in window) { 
		        	webSocket = new MozWebSocket(url); 
		        } else { 
		            alert('WebSocket is not supported by this browser.'); 
		            return; 
		        }

			// 收到服务端消息
			webSocket.onmessage = function(msg) {
				console.log(msg);
			};

			// 异常
			webSocket.onerror = function(event) {
				console.log(event);
			};

			// 建立连接
			webSocket.onopen = function(event) {
				console.log(event);
			};

			// 断线重连
			webSocket.onclose = function() {
				// 重试10次，每次之间间隔10秒
				/*
				if (tryTime < 10) {
					setTimeout(function() {
						webSocket = null;
						tryTime++;
						initSocket();
					}, 500);
				} else {
					tryTime = 0;
				}*/
			};

		}
	</script>
</body>
</html>