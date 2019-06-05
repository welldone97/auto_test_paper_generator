function loadSubject(){
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
	}); 
	 		
}

function subjectChanged(){
	$("h3").remove();
	var selectedSubject = $("#selectSubject option:selected").val();
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getKnowledgePoints&subjectId=" + selectedSubject,
		dataType:"json",
		success:function(result){
			
			if(result){
				$(".kpSpan").remove();
				$("br").remove();
				$("#kpLabel").remove();
				$("#knowledgePointsDiv").append("<label id='kpLabel' for='name'>选择知识点：<label>");
				$("#knowledgePointsDiv").append("<div>");
				$.each(result, function(i, obj){
					$("#knowledgePointsDiv").append("<span class='kpSpan'><label class='checkbox-inline'><input type='checkbox' class='knowledgePoint' name='knowledgePoint' value='" + obj.id + "'>" + obj.name + "</input>&nbsp;</label></span>");
//						$("#knowledgePointsDiv").append("<br>");
				});
				$("#knowledgePointsDiv").append("</div>");
			}
		}
	});
	
}

function removeKnowledgePoints(){
	$(".kpSpan").remove();
	$("br").remove();
	$("#kpLabel").remove();
}

	
function verifyAndStartMakePaper(form){
	var subject = $("#selectSubject").val();
	var knowledgePoints = "";
	$.each($("input[name='knowledgePoint']:checked"),function(){
		knowledgePoints += $(this).attr('value') + ',';
	});
	
	var difficulty = $("#userWantDifficulty").val();
	var count1=0,count2=0,count3=0,count4=0,
		score1=0,score2=0,score3=0,score4=0;
	if($("#typeCheckbox1").attr("checked",true)){
		count1 = $("input[name='count1']").val();
		score1 = $("input[name='score1'").val();
	}
	if($("#typeCheckbox2").attr("checked",true)){
		count2 = $("input[name='count2']").val();
		score2 = $("input[name='score2'").val();
	}
	if($("#typeCheckbox3").attr("checked",true)){
		count3 = $("input[name='count3']").val();
		score3 = $("input[name='score3'").val();
	}
	if($("#typeCheckbox4").attr("checked",true)){
		count4 = $("input[name='count4']").val();
		score4 = $("input[name='score4'").val();
	}

//	alert("\n课程：" + subject 
//		+ "\n知识点：" + knowledgePoints 
//		+ "\n难度：" + difficulty
//		+ "\n单选题题数：" + count1 + " 分数：" + score1
//		+ "\n多选题题数：" + count2 + " 分数：" + score2
//		+ "\n填空题题数：" + count3 + " 分数：" + score3
//		+ "\n判断题题数：" + count4 + " 分数：" + score4);
	layer.load(1);
	$.ajax({
		url:contextPath + "/user?method=makePaper",
		type:"post",
		data:{
			subject:subject, 
			knowledgePoints:knowledgePoints, 
			difficulty:difficulty,
			count1:count1,
			score1:score1,
			count2:count2,
			score2:score2,
			count3:count3,
			score3:score3,
			count4:count4,
			score4:score4
		},
		success:function(result){
			location.href = contextPath + "/user?method=paperDetail"; 
		},
		dataType:"text"
	});
	
}

function typeChanged(checkbox){
	switch(checkbox.value){
		case "1":
			var count1 = $("input[name='count1']");
			var score1 = $("input[name='score1']");
			if(checkbox.checked){
				count1.attr("readonly", false);
				score1.attr("readonly",false);
			}else{
				count1.val("");
				count1.attr("readonly", true);
				score1.val("");
				score1.attr("readonly",true);
			}
			break;
		case "2":
  			var count2 = $("input[name='count2']");
  			var score2 = $("input[name='score2']");
  			if(checkbox.checked){
				count2.attr("readonly", false);
				score2.attr("readonly",false);
			}else{
				count2.val("");
				count2.attr("readonly", true);
				score2.val("");
				score2.attr("readonly",true);
			}
			break;
		case "3":
			var count3 = $("input[name='count3']");
  			var score3 = $("input[name='score3']");
  			if(checkbox.checked){
				count3.attr("readonly", false);
				score3.attr("readonly",false);
			}else{
				count3.val("");
				count3.attr("readonly", true);
				score3.val("");
				score3.attr("readonly",true);
			}
			break;
		case "4":
			var count4 = $("input[name='count4']");
  			var score4 = $("input[name='score4']");
  			if(checkbox.checked){
				count4.attr("readonly", false);
				score4.attr("readonly",false);
			}else{
				count4.val("");
				count4.attr("readonly", true);
				score4.val("");
				score4.attr("readonly",true);
			}
			break;
	}
}

function verifyKps(){
	var flag = false;
	$(".knowledgePoint").each(function(i){
		if($(this).attr('checked')==true){
			flag = true;
		}
	});
	return flag;
}


function history(){
	location.href = contextPath + "/user?method=getHistory";
}