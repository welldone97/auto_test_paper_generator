function outputPaper(){
	var index = layer.open({
		  type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 0, //不显示关闭按钮
		  anim: 2,
		  shadeClose: false, //开启遮罩关闭
		  content:'<div style="margin-top:10px;margin-bottom:10px;margin-left:10px;margin-right:10px;">考试学期：</div>'
			  +'<div style="margin-top:10px;margin-bottom:10px;margin-left:10px;margin-right:10px;"><input id="startYear" type="text" style="width:50px;"/>--<input type="text" id="endYear" style="width:50px;"/>学年第<input type="text" id="term" style="width:20px;"/>学期</div>'
			  + '<div style="margin-top:10px;margin-bottom:10px"><button style="margin-left:60px;" onclick="output();">导出</button><button style="margin-left:10px;" onclick="cancel()">取消</button><div>',
		
		});
}

function output(){
	var startYear = $("#startYear").val();
	var endYear = $("#endYear").val();
	var term = $("#term").val();
	location.href = contextPath + "/user?method=outputPaper&startYear=" + startYear + "&endYear=" + endYear + "&term=" + term;
//	$.ajax({
//		type:"get",
//		url:contextPath + "/user?method=outputPaper",
//		data:{startYear:startYear, endYear:endYear, term:term},
//		dataType:"text",
//		success:function(result){
//			if(result){
//				alert("success");
//				layer.closeAll();
//			}
//		}
//	});
	layer.closeAll();
}

function cancel(){
	layer.closeAll();
}

function back(){
	location.href = contextPath + "/user?method=getHistory";
}

function verifyExamTerm(str){
	
}