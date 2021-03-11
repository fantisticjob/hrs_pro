<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="java.sql.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>FR System</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
    
  <body>
      <%
          Connection conn = null;
          CallableStatement cs = null;
          String frLink = "";
          try {
              String str="{call test_webpls_pkg.test_webpls(?)}";
              Class.forName("oracle.jdbc.driver.OracleDriver");
              String url = "jdbc:oracle:" + "thin:@10.35.100.67:1522:DEV";
              String user = "apps";
              String password = "apps";
              conn = DriverManager.getConnection(url, user, password);
              if (conn != null) {
                  cs = conn.prepareCall(str);
              }
              if (cs != null) {
                  cs.registerOutParameter(1 ,Types.VARCHAR);
                  cs.execute();
                  frLink = cs.getString(1);
              }
          } catch (Exception e) {
              System.out.println("error happens:" + e.toString());
          } finally {
              if (cs != null) {
                  cs.close();
              }
              if (conn != null) {
                  conn.close();
              }
              System.out.println("frLink=" + frLink);
          }
		  
      %>
	  <%
	      if (frLink != null && 0 < frLink.length()) {
	  %>
          <script language="javascript"type="text/javascript"> 
		      alert("<%=frLink%>");
              window.location.href="<%=frLink%>"; 
          </script> 
      <%
		  } else { 
	  %>
	      No link found for FR system.
	  <%
		  }
	  %>
	
  </body>
</html>
