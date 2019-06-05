function savePaper(){
	var index = layer.open({
		  type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 0, //不显示关闭按钮
		  anim: 2,
		  shadeClose: false, //开启遮罩关闭
		  content:   '<div style="margin-left:10px;margin-right:10px;margin-top:10px">试卷名称：<input type="text" id="ename"><div>'
			  		+'<div style="margin-top:10px;">考试方法：<select id="examMethod">'
			  					+ '<option value="open">开卷</option>'
			  					+ '<option value="close">闭卷</option>'
			  				+'</select></div>'
			  		+'<div style="margin-top:10px;">考试时长：<input type="text" name="duration" id="duration"/>分钟<div> '
			  		+'<div style="margin-top:10px;margin-bottom:10px"><button style="margin-left:70px;" onclick="save();">保存</button><button style="margin-left:10px;" onclick="cancel()">取消</button><div>'
		});
	
}

function save(){
	var ename = $("#ename").val();
	var examMethod = $("#examMethod option:selected").val();
	var duration = $("#duration").val();
	console.log(ename + examMethod + duration);
	$.ajax({
		type:"GET",
		url:contextPath + "/user?method=savePaper",
		data:{ename:ename, examMethod:examMethod, duration:duration},
		success:function(result){
			alert(result);
			location.href = contextPath + "/user?method=getHistory";
		},
		dataType:"text"
	});
	layer.closeAll();
}

function cancel(){
	layer.closeAll();
}

function returnHome(){
	location.href = contextPath + "/user?method=returnHome";
}