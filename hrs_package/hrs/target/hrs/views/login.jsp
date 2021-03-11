<%@ page language="java" pageEncoding="UTF-8"%>
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
	      <form id="hrsLoginForm" action="performLogin.do?login" method="post">
            <div  id="loginForm">
	            <div style="padding-left:150px">
	                <table cellpadding="0" cellspacing="3">
	                    <tr>
		                    <td><label for="loginid">用户名: </label></td>
		                        <td><input name="loginid" class="easyui-validatebox" required="true" value="" />
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
			                   <a id="btnLogin"  class="easyui-linkbutton"  >登 录</a>
	                           <a class="easyui-linkbutton"  onclick="clearAll()">重 置</a>
	                      </td>
                      </tr>
	                </table>
		       </div>
		   </div>
	      </form>
	   </div>
</div>

    <script type="text/javascript">	
        function clearAll(){
	        document.getElementById('loginid').value="";
	        document.getElementById('password').value="";
        }
        //check keyboard evnet
        $("#password").keydown(function(event){
	        if(event.keyCode==13)
		        $("#btnLogin").click();
        });
        
        $(function() {
		    $("#btnLogin").on('click',function() {
			$.ajax({
			    	url:"userController.do?login",
			    	type:'post',
			    	data:{
			    		"name":	$("input[name=loginid]").val(),
						"password":	$("input[name=password]").val() 
			    	},
			    	dataType:'json',
			    	success :function(data){			    	
						if (data.success) {
							var url = "/hrs/mainFrame/portal.do?";
							window.location.href=url;
						} 
						$.messager.show({
							msg : data.msg,
							title : '提示'
						});
			    	}
			    });
	         });
        });
</script>
</body>
</html>