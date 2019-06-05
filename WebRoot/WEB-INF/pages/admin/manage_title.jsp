<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML>
<html>
  <head>
    <title>manage_title.jsp</title>
    <link href="${contextPath}/css/admin/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/admin/bootstrap-table.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/admin/base.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/admin/info-mgt.css" rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="${contextPath}/css/admin/custom-popup/custom.popup-v1.3.5.css"/>
    <link type="text/css" rel="stylesheet" href="${contextPath}/css/admin/manage_title.css"/>
  </head>
  
	<body onload="titleLoad()">
		<div class="title">
			<h2>搜索题目</h2>
		</div>
		<div class="query">
			<div class="tabe_bot">
		    	<div class="l_left">
		       		<label>学科：</label>
		       		<select id="selectSubject" onchange="javascript:subjectChanged()">
		      			<option value="-1">--选择学科--</option>
		      		</select>
				</div>
		       	<div class="l_left">
		       		<label>知识点：</label>
		       		<select id="selectKnowledgePoint">
		       			<option value="-1">--选择知识点--</option>
		       		</select>
		       	</div>
		       	<div class="l_left">
		       		<label>题型：</label>
		       		<select id="selectQuestionType">
		       			<option value="-1">--选择题型--</option>
		       		</select>
		       	</div>
		       	<div class="l_left">
		       		<label>关键字：</label>
		       		<input type="text" id="inputKeyword" placeholder="题目关键字..."/>
		       	</div>
		      	<div class="l_left">
		      		<button class="tabe_btn " onclick="getTitleData();">查询</button>
		       	</div>
		       	<div class="clear">
		       	</div>
		   	</div>
		</div>
		
		<div class="table-box">
		   	<table id="table" class="table_style"></table>
		</div>
		<div class="pagination ue-clear">
		</div>
	</body>
	
  	<script src="${contextPath}/js/admin/jquery.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/bootstrap.min.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/bootstrap-table.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/bootstrap-table-zh-CN.min.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/date/js/laydate.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/manage_title.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/custom-popup/jquery.custom.popup-v1.3.5.min.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/layer_v2.1/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
     	var contextPath = "${contextPath}";
    </script>
</html>
