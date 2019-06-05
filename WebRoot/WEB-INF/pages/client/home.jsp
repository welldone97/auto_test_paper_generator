<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML >
<html>
  <head>
    <title>My JSP 'home.jsp' starting page</title>
    <link rel="stylesheet" href="${contextPath}/css/client/client_home.css" />
    <link rel="stylesheet" href="${contextPath}/css/common/bootstrap.min.css" />
    <style>
	     body{    
	        background-image: url(./images/common/loginBackground.png);    
	        background-size:100%;
	        background-repeat:no-repeat; 
	     }
	</style>
  </head>
  
  <body>
  	<div id="buttonDiv">
  		<button type="button" class="btn btn-primary" onclick="startMakePaper();" id="make-paper-button">开始组卷</button>
  	</div>
    
  </body>
  
  <script type="text/javascript">
  function startMakePaper(){
 	var contextPath = "${contextPath}";
  	location.href = contextPath + "/user?method=startMakePaper&date=" + new Date();
  }
  </script>
</html>
