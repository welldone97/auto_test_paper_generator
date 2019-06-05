//点击“看不清，换一张”执行该动作
function changeImg(){
	$("#verifyImg").attr("src", contextPath + "/common?method=getVerifyCode&date=" + new Date());
}


//登陆成功："1", 验证码错误："0", 用户名与密码不匹配："-1"
function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	var verify = $("#verify").val();
	var role = getRoleVal();
	console.log(username + "," + password + "," + verify + "," + role)
	
	$.ajax({
		url : contextPath + "/common?method=login&date=" + new Date(),
		data: {username:username, password:password, verify:verify, role:role},
		success : function(data){
			if(data){
				if(data=="1"){
					location.href = contextPath + "/common?method=loginSuccess&date=" + new Date();
				}else if(data=="0"){
					alert("验证码错误！");
				}else if(data=="-1"){
					alert("用户名与密码不匹配！");
				}else{
					alert("登陆失败！");
				}
			}
		},
		dataType:"text"
	});
}

function getRoleVal(){
	var role = $("input[class='roleRadio']:checked").val();
	return role;
}


