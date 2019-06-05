$(window).resize(function(e) {
    $("#bd").height($(window).height() - $("#hd").height() - $("#ft").height()-6);
	$(".wrap").height($("#bd").height()-6);
	$(".nav").css("minHeight", $(".sidebar").height() - $(".sidebar-header").height()-1);
	$("#iframe").height($(window).height() - $("#hd").height() - $("#ft").height()-12);
}).resize();

$('.exitDialog').Dialog({
	title:'提示信息',
	autoOpen: false,
	width:400,
	height:200,
});

$('.exit').click(function(){
	window.location.href = "login.html"	;
});

// $('.exitDialog input[type=button]').click(function(e) {
//     $('.exitDialog').Dialog('close');

// 	if($(this).hasClass('ok')){
// 		window.location.href = "login.html"	;		
// 	}
// });

$(function() {
	$('.nav>li').click(function () {
		$('.nav>li').removeClass("current");
		$(".subnav li a").removeClass("color");
		$(this).addClass("current");
		var $ul = $(".subnav",this);
		$(".subnav").slideUp();
		if ($ul.is(':visible')) {
			$ul.slideUp();
			//$(".subnav li a").removeClass("color");
		}else {
			$ul.slideDown();
		}
	});
	$(".subnav li").click(function(e){
		$(".subnav li a").removeClass("color");
		$("a",$(this)).addClass("color");
		e.stopPropagation();

	});
	//$(".nav").click(function(){
    //
	//})
});