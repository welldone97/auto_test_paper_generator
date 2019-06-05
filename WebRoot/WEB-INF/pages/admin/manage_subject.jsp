<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML >
<html>
  <head>
    <title>manage_subject.jsp</title>
    <link href="${contextPath}/css/admin/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/admin/bootstrap-table.css" rel="stylesheet" type="text/css">
    <link href="${contextPath}/css/admin/base.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/css/admin/info-mgt.css" rel="stylesheet" type="text/css"/>
  </head>
  
  <body onload="getAllSubject()">
		<div class="title">
			<h2>学科管理</h2>
		</div>
		
		<div class="table-operate ue-clear">
		   	<a href="javascript:;" class="out" onclick="addSubject();">添加</a>
		</div>
		<div class="table-box">
		   	<table id="table" class="table_style"></table>
		</div>
		<div class="pagination ue-clear">
		</div>
	</body>
	
	<script src="${contextPath}/js/admin/jquery.js" type="text/javascript"></script>
	<script src="${contextPath}/js/admin/bootstrap.min.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/bootstrap-table.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/bootstrap-table-zh-CN.min.js" type="text/javascript" ></script>
	<script src="${contextPath}/js/admin/layer_v2.1/layer/layer.js" type="text/javascript"></script>
	<script src="${contextPath}/js/admin/manage_subject.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = "${contextPath}";
	</script>
</html>
