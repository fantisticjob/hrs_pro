<!-- error code 500 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	您的登录信息已失效！<a href="#" onclick="redirect()">请重新登录！</a>
	<script type="text/javascript">
		function redirect(){
			window.top.location.href = '/hrs';
		}
	
	</script>
</body>
</html>