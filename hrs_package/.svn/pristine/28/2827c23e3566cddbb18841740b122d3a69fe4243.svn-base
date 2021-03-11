<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="common/meta.jsp"></jsp:include>
    <jsp:include page="common/easyui.jsp"></jsp:include>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>华正云科报表系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>">
	<script type="text/javascript" src="<c:url value="/resources/js/main.js"/>"></script>
</head>
<body style="border:none; visibility:visible; width: 100%; height:100%;">
	<div style="margin:20px 0;"></div>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" style="height:96px">
	        <div class="header" style="background:#fff url('../resources/css/images/banner.jpg');">
	        	<div style="text-align: right; padding-right: 20px; padding-top: 30px; padding-bottom: 14px;">
					<span style="color:#FDFDFD" id="loginuserInfo">欢迎你，管理员</span>
					<span style="color:#FDFDFD" id="loginuserArea">,财务管理部</span>
					<span style="color:#FDFDFD" id="timeInfo"></span>
					<a href="${pageContext.request.contextPath}/userController/performLogout.do" style="color:#FDFDFD;text-decoration:none;">退出</a>
				</div>
				<div class="maintitle" style="top: 12; font-size:25px;color:#FDFDFD; margin-left:5px;">报表系统</div>
			</div>
			<div id="topmenu" class="topmenu" style="height:34px; background: url('../resources/css/images/maintop.png'); color:#fff; font-size:14px; font-weight:bold;">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:addTab('首页','../resources/html/welcome.htm')" >首&nbsp;&nbsp;页</a>
	    	</div> 
		
		</div>
		<div data-options="region:'south'" style="height:18px;">
		    <center>
	    	</center>
		</div>

		<div data-options="region:'west'" title="导航菜单栏" style="width:180px;">
		    <ul id="tt1" class="easyui-tree" data-options="animate:true,dnd:true"></ul>
		</div>
		<div data-options="region:'center'">
	        <div class="easyui-tabs" data-options="fit:'true'" id="tt"> 
	    		<div title="首页" data-options="closable:true">
	    			<iframe width='100%' height='100%'  id='iframe' name='iframe' frameborder='0' scrolling='auto'  src='../views/welcome.jsp'></iframe>
	    		</div>
	    	</div>
		</div>
		<div id="dd"></div>
	</div>
</body>
</html>