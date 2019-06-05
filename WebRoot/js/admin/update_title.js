var openUpdateFlag = 0;

function loadUpdateTitle(){
	loadSubject();
}

function loadSubject(){
	console.log("contextPath in getSelectSubjectOption():" + contextPath);
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getAllSubjects&date=" + new Date(), 
		success:function(result){
			if(result){
				$.each(result, function(i, obj) {
		           　　		$("#selectSubject").append("<option value='" + obj.id + "'>"+ obj.name+ "</option>");
				});
				$("#selectSubject").find("option[value='" + subjectId + "']").attr("selected",true);
				loadKnowledgePoint();
			}
			
		},
		dataType:"json"
	});
}

function loadKnowledgePoint(){
	var selectedSubject = $("#selectSubject option:selected").val();
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getKnowledgePoints&date=" + new Date(),
		data:{subjectId:selectedSubject},
		success:function(result){
			if(result){
				$("option.knowledgePoint").remove();
				$.each(result, function(i, obj){
					$("#selectKnowledgePoint").append("<option class='knowledgePoint' value='" + obj.id + "'>" + obj.name + "</option>")
				});
			$("#selectKnowledgePoint").find("option[value='" + knowledgePointId + "']").attr("selected",true);
			}
		},
		dataType:"json"
	});
}

function updateTitle(){
	if(openUpdateFlag == 0){
		setNotReadOnly();
		setNotDisabled();
		openUpdateFlag = 1;
	}
}

function setNotReadOnly(){
	$(":input").each(function(){
		$(this).attr("readonly", false)
	});
}
function setNotDisabled(){
	$("select").each(function(){
		$(this).attr("disabled", false)
	});
}



function subjectChanged(){
	loadKnowledgePoint();
}

function titlePicturesChanged(obj){
	var allFile = obj.files;
		$('.picsInImageDiv').remove();
//		$("#imageDiv .hiddenImagePathInput").remove();
		var length = allFile.length;
		for(var i=0;i<length;i++){
			var a = URL.createObjectURL(allFile[i]);	
			var urla = allFile[i];
			var template = '<img src="'+a+'" height="100%" class="picsInImageDiv"/>';
			$('#imageDiv').append(template.replace('{src}', URL.createObjectURL(allFile[i])));
		}
		
}

function imageFileAnswerChanged(obj){
	var allFile = obj.files;
		$('.picsInAnswerDiv').remove();
//		$('#answerDiv .hiddenImageAnswerInput').remove()
		var length = allFile.length;
		for(var i=0;i<length;i++){
			var a = URL.createObjectURL(allFile[i]);	
			var urla = allFile[i];
			var template = '<img src="'+a+'" height="100%" class="picsInAnswerDiv"/>';
			$('#answerDiv').append(template.replace('{src}', URL.createObjectURL(allFile[i])));
		}
//		$(".hiddenImageAnswerInput").remove();
}

function imageFileChanged(obj){
	var elementId = obj.id;
	var abcd = elementId.charAt(elementId.length-1);
	var picClass = "picInOption" + abcd + "Div";
	var picToRemoveClass = "." + "picInOption" + abcd + "Div";
	var picToAppendToId = "#" + "option" + abcd + "Div";
//	var hiddenToRemove = picToAppendToId + " " + ".hiddenImage" + abcd + "Input";

	console.log("图片Id:" + picClass)
	console.log("要删除的图片的Id:" + picToRemoveClass);
	console.log("要将图片添加到的DivId:" + picToAppendToId);
//	console.log("要删除的hidden:" + hiddenToRemove);
	
	var file = obj.files[0];
		$(picToRemoveClass).remove();
//		$(hiddenToRemove).remove();
		if(file!=null){
			var a = URL.createObjectURL(file);	
			var template = '<img src="' + a + '" height="100%" class="' + picClass + '"/>';
			$(picToAppendToId).append(template.replace('{src}', URL.createObjectURL(file)));
		}
}

function verifyAndSubmit(form){
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
//	var image = $("#imageFile");
	var answer = $("#answerInput").val().trim();
	var difficulty = $("#difficultyInput").val().trim();
	
	errorCount += verifySelectors(subjectId, knowledgePointId);
	errorCount += verifyInputs(title, answer);
	errorCount += verifyDifficulty(difficulty);
	if(errorCount==0){
		console.log("ERROR COUNT == 0");
		form.action = contextPath + "/admin_manage?method=updateTitle";
		return true;
	}
	return false;
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