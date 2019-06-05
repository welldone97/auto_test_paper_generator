<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC>
<html>
  <head>
    <title>My JSP 'history.jsp' starting page</title>
    <link rel="stylesheet" href="${contextPath}/css/common/bootstrap.min.css" />
    
  </head>
  
  <body>
  	<div class="container" style="margin-top:50px">
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				<span><a href="${contextPath}/user?method=returnHome">首页</a>
  				</span><span>>></span>
  				<span><a>我的组卷历史</a></span>
  			</div>
  			<div class="panel-body">
  				<table class="table table-hover">
					<thead>
						<tr>
							<th>试卷名称</th>
							<th>考试方法</th>
							<th>考试时长(分钟)</th>
							<th>试卷总分</th>
							<th>试卷信息</th>
						</tr>
					</thead>
					<c:forEach var="exam" items="${sessionScope.histories}">
					<tbody>
						<tr>
							<td>${exam.ename}</td>
							<td>
								<c:if test="${exam.examMethod=='close'}">闭卷</c:if>
								<c:if test="${exam.examMethod=='open'}">开卷</c:if>
							</td>
							<td>${exam.duration}</td>
							<td>${exam.score}</td>
							<td><a href="${contextPath}/user?method=examDetails&examid=${exam.examid}">查看</a></td>
						</tr>
						
					</tbody>
					</c:forEach>
					</table>
					<input type="button" class="btn btn-success btn-lg btn-block" value="返回" onclick="back();"/>
  			</div>
  		</div>
  	</div>
  	  	
  </body>
  <script type="text/javascript">
    	var contextPath = "${contextPath}";
    	function back(){
    		location.href = contextPath + "/user?method=startMakePaper";	
    	}
    </script>
</html>
