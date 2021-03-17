<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function breakout() {
		if (window.top != window.self) {
			window.top.location = "/hrs";
		}else{
			window.location="/hrs";
		}

	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录失效！</title>
</head>
<body>
	<h3>您的登录已经失效！！！</h3>
	<input type="button" onclick="breakout()" value="请重新登录">
</body>
</html>