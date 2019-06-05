<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'exam_details.jsp' starting page</title>
    <link rel="stylesheet" href="${contextPath}/css/common/bootstrap.min.css" />
    <script type="text/javascript" src="${contextPath}/js/admin/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/js/client/exam_details.js"></script>
    <script type="text/javascript" src="${contextPath}/js/admin/layer_v2.1/layer/layer.js"></script>
    <script type="text/javascript">
    	var contextPath = "${contextPath}";
    </script>
  </head>
  
  <body>
    
    <div class="container" style="margin-top:50px;">
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				试卷信息
  			</div>
  			<div class="panel-body" >
				<table class="table table-hover">
					<caption>试卷概况</caption>
					<thead>
						<tr>
							<th>试卷名称</th>
							<th>考试科目</th>
							<th>考试方法</th>
							<th>考试时长(分钟)</th>
							<th>试卷难度</th>
							<th>试卷总分</th>
							<th>组卷时间</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${sessionScope.exam.ename}</td>
							<td>${sessionScope.exam.courseName}</td>
							<td>
								<c:if test="${sessionScope.exam.examMethod=='close'}">闭卷</c:if>
								<c:if test="${sessionScope.exam.examMethod=='open'}">开卷</c:if>
							</td>
							<td>${sessionScope.exam.duration}</td>
							<td>${sessionScope.exam.difficulty}</td>
							<td>${sessionScope.exam.score}</td>
							<td>${sessionScope.exam.addtime}</td>
						</tr>
					</tbody>
				</table>
				
				<table class="table table-hover">
					<caption>题型比例</caption>
					<thead>
						<tr>
							<th>单选题</th>
							<th>多选题</th>
							<th>填空题</th>
							<th>判断题</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${sessionScope.exam.singlenum}</td>
							<td>${sessionScope.exam.multinum}</td>
							<td>${sessionScope.exam.blanknum}</td>
							<td>${sessionScope.exam.judgementnum}</td>						
						</tr>
					</tbody>
				</table>
					
				<table class="table table-hover">
					<caption>覆盖知识点</caption>
					<tbody>
					<c:forEach var="knowledgePoint" items="${sessionScope.exam.knowledgePoints}" varStatus="status">
						<c:if test="${status.count%2!=0}">
							<tr>
						</c:if>
						<td>${knowledgePoint.name}</td>
						<c:if test="${status.count%2==0}">
							</tr>
						</c:if>
						
					</c:forEach>
					</tbody>
					
				</table>
				<input type="button" class="btn btn-primary btn-lg btn-block" value="导出试卷" onclick="outputPaper();"/>
				<input type="button" class="btn btn-success btn-lg btn-block" value="返回" onclick="back();"/>
  			</div>
  		</div>
  		
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				题目信息
  			</div>
  			<div class="panel-body" style="">
  				<table class="table table-hover">
  					<tbody>
						<c:forEach items="${sessionScope.exam.questions.questions}" var="question" varStatus="status">
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
