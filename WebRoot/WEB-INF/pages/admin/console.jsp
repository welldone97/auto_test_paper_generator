<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${contextPath}/css/admin/base.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/admin/jquery.dialog.css" />
<link rel="stylesheet" href="${contextPath}/css/admin/console.css" />
    <style>
        .layui-layer-title{background:url(images/righttitlebig.png) repeat-x;font-weight:bold;color:#46647e; border:1px solid #c1d3de;height: 33px;line-height: 33px;}
    </style>
<title>WELLDONE组卷系统管理员页面</title>
<script>!function(e){var c={nonSecure:"8123",secure:"8124"},t={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=t[n]+r[n]+":"+c[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document);</script></head>
<body>
<div id="container" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-27" data-genuitec-path="/auto_test_paper_generation/WebRoot/pages/console.jsp">
	<div id="hd">
    	<div class="hd-wrap ue-clear">
        	<div class="top-light"></div>
            
            <div class="login-info ue-clear">
                <div class="welcome ue-clear"><span>欢迎您,</span><a href="javascript:void(0)" class="user-name">admin</a></div>
                
            </div>
            <div class="toolbar ue-clear">
                <a href="${contextPath}/admin_page_change?method=home" class="home-btn" target="right">首页</a>
                
                <a href="${contextPath}/index.jsp" class="quit-btn exit home-btn">退出</a>
            </div>
        </div>
    </div>

    

    <div id="bd">
    	<div class="wrap ue-clear">
        	<div class="sidebar">
            	<h2 class="sidebar-header"><p>功能导航</p></h2>
                <ul class="nav">
                	<li class="office current">
                        <div class="nav-header">
                            <a href="${contextPath}/admin_page_change?method=home" target="right" class="ue-clear">
                            	<span>首页</span>
                            	<i class="icon"></i>
                            </a>
                        </div>
                    </li>

                    <li class="land">
                    	<div class="nav-header">
	                    	<a href="JavaScript:;" class="ue-clear" >
	                    		<span>题库管理</span>
	                    		<i class="icon hasChild"></i>
	                    	</a>
                    	</div>
                        <ul class="subnav">
                            <li>
                            	<a href="${contextPath}/admin_page_change?method=addTitle" target="right">录入新题目</a>
                            </li>
                            <li>
                            	<a href="${contextPath}/admin_page_change?method=manageTitle" target="right">管理已有题目</a>
                            </li>
                        </ul>
                    </li>

                    <li class="list_m">
                        <div class="nav-header">
                            <a href="JavaScript:;" class="ue-clear" target="right"><span>学科/知识点管理</span><i class="icon"></i>
                            </a>
                        </div>
                        <ul class="subnav">
                            <li><a href="${contextPath}/admin_page_change?method=manageSubject" target="right">学科管理</a></li>
                            <li><a href="${contextPath}/admin_page_change?method=manageKnowledgePoint" target="right">知识点管理</a></li>
                        </ul>
                    </li>

                    
                </ul>
            </div>
            <div class="content">
            	<iframe src="${contextPath}/admin_page_change?method=home" id="iframe" width="100%" height="100%" frameborder="0" name="right" style="min-width: 1100px"></iframe>
            </div>
        </div>
    </div>
    <div id="ft" class="foot_div">
            <span>well-done</span>
            <em>组卷系统</em>
    </div>
</div>


</body>
<script type="text/javascript" src="${contextPath}/js/admin/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/admin/core.js"></script>
<script type="text/javascript" src="${contextPath}/js/admin/jquery.dialog.js"></script>
<script type="text/javascript" src="${contextPath}/js/admin/index.js"></script>
<script src="${contextPath}/js/admin/layer_v2.1/layer/layer.js"></script>
<script type="text/javascript">
    function openlayer(id){
        layer.open({
            type: 2,
            title: '修改密码',
            shadeClose: false,
            shade: 0.5,
            skin: 'layui-layer-rim',
//            maxmin: true,
            closeBtn:2,
            area: ['35%', '40%'],
            content: 'password.html'
            //iframe的url
        });
    }
</script>
</html>
