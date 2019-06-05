<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>      
<!DOCTYPE HTML>
<html>
  <head>
    <title>WELLDONE组卷系统</title>
    <link rel="stylesheet" type="text/css" href="css/common/login.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/common/login.js"></script>
    <style>
	     body{    
	        background-image: url(./images/common/loginBackground.png);    
	        background-size:100%;
	        background-repeat:no-repeat; 
	     }
	</style>
  </head>   
	
  <body>
  	<div id="fontDiv" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-7" data-genuitec-path="/auto_test_paper_generation/WebRoot/index.jsp"><span style="font-size:30px">欢迎使用组卷系统</span></div>
  	<div id="loginDiv">
  		
  		<div id="loginBox">
  			<div class="bxs-row" style="text-align:left;">
            	<span class="tips" style="color:white;">用户登陆</span>
        	</div>
        	<hr>
  			<div class="box-row" style="margin-top:20px;">
  				<input type="text" name="username" id="username" class="inputArea" placeholder="请输入用户名"/>
  				 <p class=" err err-username"></p>
  			</div>
	  		<div class="box-row">
	  			<input type="password" name="password" id="password" class="inputArea" placeholder="请输入密码"/>
	  			 <p class=" err err-password"></p>
	  		</div>
	  		<div class="box-row" >
	  			<input type="text" name="verify" id="verify" class="inputArea" placeholder="请输入验证码"/>
	  			<img alt="验证码" src="${pageContext.request.contextPath}/common?method=getVerifyCode" id="verifyImg">
	  			<a href="#" onclick="javascript:changeImg()" style="font-size:10px"><span style="color:white">看不清，换一张</span></a>
	  			 <p class=" err err-verify"></p>
	  			 <input type="radio" name="role" value="user" class="roleRadio" checked><span style="color:white">用户</span> 
	  			 <input type="radio" name="role" value="admin" class="roleRadio"><span style="color:white">管理员</span> 
	  		</div>
	  		<div class="box-row">
	  			<input type="button" name="loginBtn" id="loginBtn" class="Btn" value="登录" onclick="javascript:login()"/>
	  		</div>
  		</div>
   	</div>
   	
   
  </body>
  	<script type="text/javascript">
     	var contextPath = "${contextPath}";
     	console.log("contextPath=" + contextPath);
    </script>
</html>
