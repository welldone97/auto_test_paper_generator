<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML >
<html>
  <head>
    <title>manage_knowledge_point</title>
    <link href="${contextPath}/css/admin/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/admin/bootstrap-table.css" rel="stylesheet" type="text/css">
    <link href="${contextPath}/css/admin/base.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/css/admin/info-mgt.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/css/admin/add_title.css" rel="stylesheet" type="text/css"/>
  </head>
  
  	<body onload="getSelectSubjectOption()">
		<div class="title">
			<h2>知识点管理</h2>
		</div>
		<div class="query">
			<div class="tabe_bot">
		    	<div class="l_left">
		       		<label>学科：</label>
		       		<select id="selectSubject" onchange="javascript:subjectChanged()">
		      			<option value="-1">--选择学科--</option>
		      		</select>
				</div>
		       	
		       	<div class="clear">
		       	</div>
		   	</div>
		</div>
		<div class="table-operate ue-clear">
		   	<a href="javascript:;" class="out" onclick="addKnowledgePoint();">添加</a>
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
	<script src="${contextPath}/js/admin/manage_knowledge_point.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = "${contextPath}";
	</script>
</html>
