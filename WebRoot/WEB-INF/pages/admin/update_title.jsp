<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
 	<head>
    	<title>update_title.jsp</title>
    	<link href="${contextPath}/css/admin/bootstrap.min.css" rel="stylesheet" type="text/css">
    	<link href="${contextPath}/css/admin/bootstrap-table.css" rel="stylesheet" type="text/css">
    	<style type="text/css">
    		.rowDiv{margin:20px;}
    	</style>
	</head>
	<body onload="loadUpdateTitle()">
  		<form id="dataForm" onsubmit="return verifyAndSubmit(this)" method="post" >
  			<div class="" style="border:1px solid blue;margin-top:20px">
				<input type="hidden" id="id" name="id" value="${titleDetail.id}" />
				<div id="questionTypeDiv" class="rowDiv form-group">
		    		<label id="showQuestionType">题型：${titleDetail.questionType}</label>
		    		<input type="hidden" id="hiddenQuestionTypeId" name="questionTypeId" value="${titleDetail.questionTypeId}"/>
		    	</div>
		    	
		    	<div id="subjectDiv" class="rowDiv form-group">
		    		<label for="selectSubject" id="selectSubjectLabel">学科：</label>
		    		<select id="selectSubject" name="subjectId" onchange="javascript:subjectChanged()" class="form-control" disabled="disabled">
		    			<option value="-1">--选择学科--</option>
		    		</select>
	    		</div>
	    		
	    		<div id="knowledgePointDiv" class="rowDiv form-group">
		    		<label for="selectKnowledgePoint" id="selectKnowledgePointLabel">知识点：</label>
		    		<select id="selectKnowledgePoint" name="knowledgePointId" class="form-control" disabled="disabled">
		    			<option value="-1">--选择知识点--</option>
		    		</select>
	    		</div>
	    		
	    		<div id="titleDiv" class="rowDiv form-group">
		    		<label for="titleInput" id="titleInputLabel">题干：</label>
		    		<textarea id="titleInput" name="title" class="form-control" rows="4" style="resize: none;" readonly>
		    			${titleDetail.title}
		    		</textarea>
	    		</div>
	    		
	    		<c:choose>
					<c:when test="${titleDetail.questionTypeId=='1'}">
						<div id="optionADiv" class="rowDiv form-group">
				    		<label for="optionAInput" id="optionAInputLabel">选项A:</label>
				    		<input type="text" id="optionAInput" name="optionA" value="${titleDetail.optionA}" class="form-control" readonly/>
				    	</div>
				    	
				    	<div id="optionBDiv" class="rowDiv form-group">
				    		<label for="optionBInput" id="optionBInputLabel">选项B:</label>
				    		<input type="text" id="optionBInput" name="optionB" value="${titleDetail.optionB}" class="form-control" readonly/>
				    	</div>
				    	
				    	<div id="optionCDiv" class="rowDiv form-group">
				    		<label for="optionCInput" id="optionCInputLabel">选项C:</label>
				    		<input type="text" id="optionCInput" name="optionC" value="${titleDetail.optionC}" class="form-control" readonly/>
				    	</div>
				    	
				    	<div id="optionDDiv" class="rowDiv form-group">
				    		<label for="optionDInput" id="optionDInputLabel">选项D:</label>
				    		<input type="text" id="optionDInput" name="optionD" value="${titleDetail.optionD}" class="form-control" readonly/>
				    	</div>
					</c:when>
					<c:when test="${titleDetail.questionTypeId=='2'}">
						<div id="optionADiv" class="rowDiv form-group">
				    		<label for="optionAInput" id="optionAInputLabel">选项A:</label>
				    		<input type="text" id="optionAInput" name="optionA" value="${titleDetail.optionA}" class="form-control" readonly/>
				    	</div>
				    	<div id="optionBDiv" class="rowDiv form-group">
				    		<label for="optionBInput" id="optionBInputLabel">选项B:</label>
				    		<input type="text" id="optionBInput" name="optionB" value="${titleDetail.optionB}" class="form-control" readonly/>
				    	</div>
				    	<div id="optionCDiv" class="rowDiv form-group">
				    		<label for="optionCInput" id="optionCInputLabel">选项C:</label>
				    		<input type="text" id="optionCInput" name="optionC" value="${titleDetail.optionC}" class="form-control" readonly/>
				    	</div>
				    	<div id="optionDDiv" class="rowDiv form-group">
				    		<label for="optionDInput" id="optionDInputLabel">选项D:</label>
				    		<input type="text" id="optionDInput" name="optionD" value="${titleDetail.optionD}" class="form-control" readonly/>
				    	</div>
					</c:when>
    			</c:choose>
    			
    			<div id="answerDiv" class="rowDiv form-group">
		    		<label for="answerInput" id="answerInputLabel">答案：</label>
		    		<input type="text" id="answerInput" name="answer" value="${titleDetail.answer}" class="form-control" readonly/>
	    		</div>
	    		
	    		<div id="scoreDiv" class="rowDiv form-group">
		    		<label for="scoreInput" id="scoreInputLabel">分数：</label>
		    		<input type="text" id="scoreInput" name="score" value="${titleDetail.score}" class="form-control" readonly/>
	    		</div>
	    		
	    		<div id="difficultyDiv" class="rowDiv form-group">
		    		<label for="difficultyInput" id="difficultyInputLabel">难度(请输入0-1不超过4位有效数字的小数):</label>
		    		<input type="text" id="difficultyInput" name="difficulty" value="${titleDetail.difficulty}" class="form-control" readonly/>
	    		</div>
	    		
	    		<div id="buttonDiv" class="rowDiv" style="text-align:center;">
					<input type="button" id="updateBtn" class="btn btn-info" style="width:80px;height:30px;margin-right:10px;" value="修改题目" onclick="updateTitle()"/>
		    		<input type="submit" id="submitBtn" class="btn btn-info"  value="提交"   style="width:80px;height:30px;margin-right:10px;margin-left:10px;" />
		    		<input type="button" value="返回" id="backBtn" class="btn btn-info" onclick="back()" style="width:80px;height:30px;margin-left:10px;"/>
	    		</div>
	    	</div>
  		</form>
	</body>
	<script type="text/javascript">
	 	var contextPath = "${contextPath}";
	 	var subjectId = "${titleDetail.subjectId}";
    	var knowledgePointId = "${titleDetail.knowledgePointId}";
    	var questionTypeId = "${titleDetail.questionTypeId}";
	</script>
	<script type="text/javascript" src="${contextPath}/js/admin/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/js/admin/update_title.js"></script>
</html>
