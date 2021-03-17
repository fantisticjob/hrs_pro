<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="common/meta.jsp"></jsp:include>
    <jsp:include page="common/easyui.jsp"></jsp:include>
    <title>登录</title>
</head>
<body style="height:100%; width:100%; overflow:hidden; border:none; visibility:visible;">
    <div id="mainwindow" class="easyui-window" style="width:500px; height:300px; background:#fafafa; overflow:hidden"
         title="登录" border="false" resizable="false" draggable="false" minimizable="false" maximizable="false">
        <div class="header" style="height:35px;">
            <div class="toptitle" style="margin-top: 25px; font-size:20px; margin-left:60px;">华正云科报表系统</div>
        </div>
	    <div style="padding:60px 0;">
	        <c:if test="${not empty error }">
	            <div class="error"><font color="red">${error }</font><br/><br/></div>
	        </c:if>
	        <c:if test="${not empty msg }">
	            <div class="msg"><font color="red">${msg }</font><br/><br/></div>
	        </c:if> 
	      <form id="hrsLoginForm" action="userController/login.do" method="POST">
            <div  id="loginForm">
	            <div style="padding-left:150px">
	                <table cellpadding="0" cellspacing="3">
	                    <tr>
		                    <td><label for="username">用户名: </label></td>
		                        <td><input name="username" type="text" class="easyui-validatebox" required="true" value="" />
	                        </td>
                        </tr>
                        <tr>
	                        <td><label for="password">登录密码: </label></td>
	                            <td><input name="password" type="password" class="easyui-validatebox" required="true" value="" />
	                        </td>
                       </tr>
                       <tr>
	                       <td>&nbsp;</td>
	                       <td>&nbsp;</td>
                       </tr>
                       <tr>
	                       <td></td>
	                       <td>
	                       		<input id="btnLogin"  class="easyui-linkbutton" style="font-size: 16px;"  type="submit" value=" 登录 "/>&nbsp;&nbsp;&nbsp;
                				<input  class="easyui-linkbutton"  onclick="clearAll()" style="font-size: 16px;"  type="reset" value=" 重置 "/>
	                      </td>
                      </tr>
	                </table>
		       </div>
		   </div>
		   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	      </form>
	   </div>
</div>

    <script type="text/javascript">	
        function clearAll(){
	        document.getElementById('username').value="";
	        document.getElementById('password').value="";
        }
       document.onkeydown = function(event) {
				e = event ? event : (window.event ? window.event : null);
				if(e.keyCode == 13) {
					//执行的方法
					$("#btnLogin").click();
				}
			}

</script>
</body>
</html>