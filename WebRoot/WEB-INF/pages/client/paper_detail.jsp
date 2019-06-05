<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'paper_detail.jsp' starting page</title>
    <link rel="stylesheet" href="${contextPath}/css/client/client_home.css" />
    <link rel="stylesheet" href="${contextPath}/css/common/bootstrap.min.css" />
    <link rel="stylesheet" href="${contextPath}/js/admin/layer_v2.1/layer/skin/layer.css" />
    <script type="text/javascript" src="${contextPath}/js/admin/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/js/client/paper_details.js"></script>
    <script type="text/javascript" src="${contextPath}/js/admin/layer_v2.1/layer/layer.js"></script>
    <script type="text/javascript">
    	var contextPath = "${contextPath}";
    </script>
  </head>
  
  <body>
  	<div class="container body">
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				试卷信息
  			</div>
  			<div class="panel-body" >
				<div>
				<table class="table table-hover">
					<caption>组卷结果</caption>
					<thead>
						<tr>
							<th>适应度</th>
							<th>难度</th>
							<th>总题数</th>
							<th>总分</th>
							<th>知识点覆盖率</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${sessionScope.paper.fitness}</td>
							<td>${sessionScope.paper.difficulty}</td>
							<td>${sessionScope.paper.questionCount}</td>
							<td>${sessionScope.paper.totalScore}</td>
							<td>${sessionScope.paper.kpCoverage}</td>
						</tr>
					</tbody>
				</table>
				</div>
				<input type="button" class="btn btn-primary btn-lg btn-block" value="保存试卷" onclick="savePaper();"/>
	 			<input type="button" class="btn btn-danger btn-lg btn-block" value="返回重新组卷" onclick="returnHome();"/> 
  			</div>
  		</div>
  		
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				题目信息
  			</div>
  			<div class="panel-body">
  				<table class="table table-hover">
  					<tbody>
						<c:forEach items="${sessionScope.questionDetails}" var="question" varStatus="status">
						 	<tr>
						 		<td>
							 	<c:if test="${question.type=='single'}">
							 		(单选题)${status.count}.${question.title}(${question.score}分)<br>
							 		A.${question.optionA}&nbsp;&nbsp;
							 		B.${question.optionB}&nbsp;&nbsp;
							 		C.${question.optionC}&nbsp;&nbsp;
							 		D.${question.optionD}&nbsp;&nbsp;<br>
							 	</c:if>
							 	<c:if test="${question.type=='multi'}">
							 		(多选题)${status.count}.${question.title}(${question.score}分)<br>
							 		A.${question.optionA}&nbsp;&nbsp;
							 		B.${question.optionB}&nbsp;&nbsp;
							 		C.${question.optionC}&nbsp;&nbsp;
							 		D.${question.optionD}&nbsp;&nbsp;<br>
							 	</c:if>
							 	<c:if test="${question.type=='blank'}">
							 		(填空题)${status.count}.${question.title}(${question.score}分)<br>
							 	</c:if>
							 	<c:if test="${question.type=='judgement'}">
							 		(判断题)${status.count}.${question.title}(${question.score}分)<br>
							 	</c:if>
				 				<br>
				 				</td>
				 			</tr> 
					 	</c:forEach>
			 		</tbody>
  				</table> 
  			</div>
  		</div>
  		
  	</div>
  	
  </body>
  
</html>
