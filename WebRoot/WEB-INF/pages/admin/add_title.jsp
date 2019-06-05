<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>add_title.jsp</title>
		<link href="${contextPath}/css/admin/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="${contextPath}/css/admin/bootstrap-table.css" rel="stylesheet" type="text/css">
		<link href="${contextPath}/css/admin/base.css" rel="stylesheet" type="text/css"/>
		<link href="${contextPath}/css/admin/info-mgt.css" rel="stylesheet" type="text/css"/>
	  	<link href="${contextPath}/css/admin/add_title.css" rel="stylesheet" type="text/css"/>
	  	
	  	<script src="${contextPath}/js/admin/jquery.js" type="text/javascript" ></script>
  		<script src="${contextPath}/js/admin/add_title.js" type="text/javascript" ></script>
	</head>
  
	<body onload="titleLoad()">
	    <div class="title"><h2>录入题目</h2></div>
		<div class="query">
	    	<div class="tabe_bot">
		        <div class="l_left">
		        	<label>题型：</label>
		        	<select id="selectQuestionType" onchange="javascript:questionTypeChanged()">
		        		<option value="-1">--选择题型--</option>
		        	</select>
		        </div>
	        	<div class="clear"></div>
	    	</div>
		</div>
		<div id="titleDetail">
		</div>
	</body>
	<script type="text/javascript">
		var contextPath = "${contextPath}";
	</script>
</html>
