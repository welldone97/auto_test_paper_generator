function titleLoad(){
	getSelectQuestionTypeOption();
}

function getSelectQuestionTypeOption(){
	console.log("contextPath in getSelectQuestionTypeOption():" + contextPath);
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getAllQuestionTypes&date=" + new Date(),
		success:function(result){
			if(result){
				$.each(result, function(i, obj){
					$("#selectQuestionType").append("<option value='" + obj.id + "'>" + obj.name + "</option>");
				});
			}
		},
		dataType:"json"
	});
}

function questionTypeChanged(){
	var questionTypeId = $("#selectQuestionType option:selected").val();
	
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getAddQuestionForm&questionTypeId=" + questionTypeId,
		success:function(result){
			if(result){
				console.log("result not null");
				$("#titleDetail").html(result);
				getSelectSubjectOption();
			}else{
				console.log("result null");
				$("#titleDetail").html("");
			}
		},
		dataType: "text"
	});
}

function getSelectSubjectOption(){
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getAllSubjects&date=" + new Date(), 
		dataType:"json",
		success:function(result){
			if(result){
				$.each(result, function(i, obj) {
		           　　		$("#selectSubject").append("<option value='" + obj.id + "'>"+ obj.name+ "</option>");
				});
			}
		}
	})
}

function subjectChanged(){
	var selectedSubject = $("#selectSubject option:selected").val();
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getKnowledgePoints&subjectId=" + selectedSubject,
		dataType:"json",
		success:function(result){
			if(result){
				$("option.knowledgePoint").remove();
				$.each(result, function(i, obj){
					$("#selectKnowledgePoint").append("<option class='knowledgePoint' value='" + obj.id + "'>" + obj.name + "</option>")
				});
			}
		}
	});
}

function verifyAndSubmit(form){
	console.log("进入");
	var questionTypeId = $("#selectQuestionType option:selected").val();
	var errorCount = 0;
	if(isChoiceQuestion(questionTypeId)){
		var optionA = $("#optionAInput").val().trim();
		var optionB = $("#optionBInput").val().trim();
		var optionC = $("#optionCInput").val().trim();
		var optionD = $("#optionDInput").val().trim();
		console.log("optionA:" + optionA + 
					"optionB:" + optionB +
					"optionC:" + optionC +
					"optionD:" + optionD);
		
		errorCount += verifyOptions(optionA, optionB, optionC, optionD);
	}
	var subjectId = $("#selectSubject option:selected").val();
	var knowledgePointId = $("#selectKnowledgePoint option:selected").val();
	var title = $("#titleInput").val();
	var answer = $("#answerInput").val().trim();
	var difficulty = $("#difficultyInput").val().trim();
	console.log("subjectId:" + subjectId +
				"knowledgePointId:" + knowledgePointId +
				"title:" + title + 
				"answer:" + answer + 
				"difficulty:" + difficulty);
	errorCount += verifySelectors(subjectId, knowledgePointId);
	errorCount += verifyInputs(title, answer);
	errorCount += verifyDifficulty(difficulty);
	console.log("errorCount-->"+errorCount);
	if(errorCount==0){
		form.action = contextPath + "/admin_manage?method=addTitle&questionTypeId=" + questionTypeId + "&subjectId=" + subjectId
		return true;
	}
	return false;
}

function imageFileChanged(obj){
	$('.picInImageDiv').remove();
	var allFile = obj.files;
	var length = allFile.length;
	for(var i=0;i<length;i++){
		var a = URL.createObjectURL(allFile[i]);	
		var urla = allFile[i];
		var template = '<img src="'+a+'" height="100%" class="picInImageDiv">';
		$('#imageDiv').append(template.replace('{src}', URL.createObjectURL(allFile[i])));
	}
}

function imageAnswerChanged(obj){
	$('.picInAnswerDiv').remove();
	var allFile = obj.files;
	var length = allFile.length;
	for(var i=0;i<length;i++){
		var a = URL.createObjectURL(allFile[i]);	
		var urla = allFile[i];
		var template = '<img src="'+a+'" height="100%" class="picInAnswerDiv">';
		$('#answerDiv').append(template.replace('{src}', URL.createObjectURL(allFile[i])));
	}
}

function imageChanged(obj){
	var divId = obj.parentElement.id;
	var divIdWithShell = "#" + divId
	console.log(divId)
	var className = "picIn" + divId;
	var classNameWithPoint = "." + className;
	$(classNameWithPoint).remove();
	
	if(obj.files[0] != null){
		console.log("not null");
		var file = obj.files[0];
		var a = URL.createObjectURL(file);
		
		var template = '<img src="' + a + '" height="100%" class="' + className + '"/>';
		console.log(template);
		$(divIdWithShell).append(template.replace('{src}', URL.createObjectURL(file)));
	}
}









function isChoiceQuestion(questionTypeId){
	return (questionTypeId == "1" || questionTypeId == "2");
}
function isEmpty(option){
	return (option == null || option.trim() == "");
}
function verifyOptions(optionA, optionB, optionC, optionD){
	var errors = 0;
	if(isEmpty(optionA)){
		$("#optionAInput").focus();
		$("#optionAInputLabel").css("color","red");
		errors++;
	}
	if(isEmpty(optionB)){
		$("#optionBInput").focus();
		$("#optionBInputLabel").css("color","red");
		errors++;
	}
	if(isEmpty(optionC)){
		$("#optionCInput").focus();
		$("#optionCInputLabel").css("color","red");
		errors++;
	}
	if(isEmpty(optionD)){
		$("#optionDInput").focus();
		$("#optionDInputLabel").css("color","red");
		errors++;
	}
	return errors;
}
function verifySelectors(subjectId, knowledgePointId){
	var errors = 0;
	if(subjectId == -1){
		$("#selectSubject").focus();
		$("#selectSubjectLabel").css("color","red");
		errors++;
	}
	if(knowledgePointId == -1){
		$("#selectKnowledgePoint").focus();
		$("#selectKnowledgePointLabel").css("color","red");
		errors++;
	}
	return errors;
}
function verifyInputs(title, answer){
	var errors = 0;
	if(title==null || title.trim()==""){
		$("#titleInput").focus();
		$("#titleInputLabel").css("color","red");
		errors++;
	}
	return errors;
}
function verifyDifficulty(difficulty){
	var errors = 0;
	if(difficulty==null || difficulty.trim()==""){
		$("#difficultyInput").focus();
		$("#difficultyInputLabel").css("color","red");
		errors++;
	}
	if(!isDouble(difficulty)){
		$("#difficultyInputLabel").css("color","red");
		errors++;
	}
	return errors;
}
function isDouble(str){
	var patt1= /^(0.\d{1,4})$/; 
	var patt2= /^(1)$/; 
	var patt3= /^(0)$/; 
	var patt4= /^(1.0{1,4})$/; 
	return (patt1.test(str)||patt2.test(str)||patt3.test(str)||patt4.test(str));
}