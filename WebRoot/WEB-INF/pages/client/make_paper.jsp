<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML >
<html>
  <head>
    <title>My JSP 'make_paper.jsp' starting page</title>
    <link rel="stylesheet" href="${contextPath}/css/client/client_home.css" />
     <link rel="stylesheet" href="${contextPath}/css/common/bootstrap.min.css" />
  </head>
  
  <body onload="loadSubject()">
  	<div class="container body">
  		<div class="panel panel-default">
  			<div class="panel-heading">
  				开始组卷
  			</div>
  			<div class="panel-body">
  				<form role="form">
  					<div class="form-group">
  						<label for="name">选择课程：</label>
  						<select id="selectSubject" name="subject" onchange="subjectChanged();" class="form-control">
					    	<option value="-1">--</option>
					    </select>
  					</div>
  					<div id="knowledgePointsDiv" class="form-group">
	    			</div>
	    			<br>
			    	<div class="form-group">
			    		<label for="name">期望试卷难度：</label>
			    		<input type="text" name="userWantDifficulty" id="userWantDifficulty" class="form-control" />
			    	</div>
				    <div class="form-group">
					    <div class="form-group">
					    	<label for="name">选择题型：</label>
					    </div>
					    
					    <div class="row">
					    	<div class="col-md-2">
					    		<input type="checkbox" name="type" value="1" onchange="typeChanged(this);" id="typeCheckbox1"/>单选题&nbsp;
					    	</div>
					    	<div class="col-md-5">
					    		题数：<input type="text" name="count1" readonly/>&nbsp;
					    	</div>
					    	<div class="col-md-5">
					    		分数：<input type="text" name="score1" readonly/>
					    	</div>
					    </div>
					    
					    <div class="row">
					    	<div class="col-md-2">
					    		<input type="checkbox" name="type" value="2" onchange="typeChanged(this);" id="typeCheckbox2"/>多选题&nbsp;
					    	</div>
					    	<div class="col-md-5">
					    		题数：<input type="text" name="count2" readonly/>&nbsp; 
					    	</div>
					    	<div class="col-md-5">
					    		分数：<input type="text" name="score2" readonly/>
					    	</div>
					    </div>
					     
					    <div class="row">
					    	<div class="col-md-2">
					    		<input type="checkbox" name="type" value="3" onchange="typeChanged(this);" id="typeCheckbox3"/>填空题&nbsp;
					    	</div>
					    	<div class="col-md-5">
					    		题数：<input type="text" name="count3" readonly/>&nbsp; 
					    	</div>
					    	<div class="col-md-5">
					    		分数：<input type="text" name="score3" readonly/>
					    	</div>
					    </div>
					    
					    <div class="row">
					    	<div class="col-md-2">
					    		<input type="checkbox" name="type" value="4" onchange="typeChanged(this);" id="typeCheckbox4"/>判断题&nbsp;
					    	</div>
					    	<div class="col-md-5">
					    		题数：<input type="text" name="count4" readonly/>&nbsp; 
					    	</div>
					    	<div class="col-md-5">
					    		分数：<input type="text" name="score4" readonly/>
					    	</div>
					    </div>
				    </div>
				    
				    <div class="form-group">
					    <div style="text-align:center" >
					    	<input type="button" value="开始组卷" onclick="verifyAndStartMakePaper();" class="btn btn-primary btn-lg btn-block"/>
					    	<input type="button" value="组卷历史" onclick="history();" class="btn btn-success btn-lg btn-block"/>
    					</div>
    					
  					</div>
				    
	    			
  				</form>		
		
  			</div>
  		</div>
  	</div>
  
	    
	    
	    
	    
	    
	    
  </body>
  <script src="${contextPath}/js/jquery-3.3.1.min.js" type="text/javascript" ></script>
  <script src="${contextPath}/js/client/make_paper.js"></script>
  <script src="${contextPath}/js/admin/layer_v2.1/layer/layer.js" type="text/javascript"></script>
  <script type="text/javascript">
  	var contextPath = "${contextPath}";
  </script>
  
</html>
