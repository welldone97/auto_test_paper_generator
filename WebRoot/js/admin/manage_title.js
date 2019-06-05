
var subjectId, knowledgePointId, questionTypeId, keyword, flag = true;
function titleLoad() {
	getSelectSubjectOption();
	getSelectQuestionTypeOption();
    $(function () {
        $('#table').bootstrapTable({
            method: "get",		//向服务器请求远程数据的方式
            striped: true,		//默认false，当设为true，则每行表格的背景会显示灰白相间
            singleSelect: false,//默认false，设为true则允许复选框仅选择一行
            pageList: [10, 25], //可供选择的每页的行数（*）
            dataType: "json",	//期望从服务器获取的数据的类型，默认为json格式字符串
            pagination: true, 	//默认为false，表格的底部工具栏不会显示分页条（pagination toolbar），可以设为true来显示
            uniqueId: "yes",
            pageSize: 10,		//前提：pagination设为true，启用了分页功能。设置每页初始显示的条数10条，默认10条。
            pageNumber: 1,		//前提：pagination设为true，启用了分页功能。- 默认第1页，用于设置初始的页数
            search: false,	 	//默认false不显示表格右上方搜索框 ，可设为true，在搜索框内只要输入内容即开始搜索
            contentType: "application/x-www-form-urlencoded",//请求数据的contentType（内容类型），用于定义文件的类型，决定接收方以什么形式读取这个文件。
            queryParams: null,	//当请求数据时，你可以通过修改queryParams向服务器发送其余的参数。 
            columns: [
                {
                    title: "学科",
                    field: 'subject',
                    align: 'center',
                    valign: 'middle'
                },
                {
                	title:'知识点',
                	field:'knowledgePoint',
                	align:'center',
                	valign:'middle'
                },
                {
                    title: '题型',
                    field: 'questionType',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '题目',
                    field: 'title',
                    align: 'center'
                },
                {
                    title: '最后修改时间',
                    field: 'insertTime',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作',
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row) {
                        var e = '<button button="#" mce_href="#" class="btn btn-primary" onclick="updateTitle(' + row.questionTypeId + ', ' + row.id + ')">查看</button> '
                        return e;
                    }
                },
                {
                	title: '操作',
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row) {
                        var e = '<button button="#" mce_href="#" id="deleteBtn" class="btn btn-danger" onclick="deleteTitle(' + row.questionTypeId + ', ' + row.id + ')">删除</button> '
                        return e;
                    }
                }
            ]
        });
    });
}

function getSelectSubjectOption(){
	console.log("contextPath in getSelectSubjectOption():" + contextPath);
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

function getSelectQuestionTypeOption(){
	console.log("contextPath in getSelectQuestionTypeOption():" + contextPath);
	$.ajax({
		type:"GET",
		url: contextPath + "/admin_manage?method=getAllQuestionTypes&date=" + new Date(),
		dataType:"json",
		success:function(result){
			if(result){
				$.each(result, function(i, obj){
					$("#selectQuestionType").append("<option value='" + obj.id + "'>" + obj.name + "</option>");
				});
			}
		}
	});
	
}

function subjectChanged(){
	var selectedSubject = $("#selectSubject option:selected").val();
	console.log("selectedSubject=" + selectedSubject);
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

function getTitleData() {
    subjectId = $("#selectSubject").val();
    knowledgePointId = $("#selectKnowledgePoint").val();
    questionTypeId = $("#selectQuestionType").val();
    keyword = $("#inputKeyword").val();
    console.log("subjectId=" + subjectId + "\nknowledgePointId=" + knowledgePointId + "\nquestionTypeId=" + questionTypeId + "\nkeyword=" + keyword);
    
    $.ajax({
        type: "GET",
        url: contextPath + "/admin_manage?method=getMatchTitles&date=" + new Date(),
        data:{
        	subjectId:subjectId,
        	knowledgePointId:knowledgePointId,
        	questionTypeId:questionTypeId,
        	keyword:keyword
        },
        success: function (result) {
            if (result) {
                var NoticeTableData = result;
                $('#table').bootstrapTable("load", NoticeTableData);
            }
        },
        dataType: "json"
    })
}

function updateTitle(questionTypeId, id){
	console.log("进入operation:");
	console.log("questionTypeId:" + questionTypeId);
	console.log("id:" + id);
	location.href = contextPath + "/admin_manage?method=getTitleByQuestionTypeAndId" 
								+ "&questionTypeId=" + questionTypeId
								+ "&id=" + id
								+ "&date=" + new Date();
}

function deleteTitle(questionTypeId, id){
	layer.confirm('确定要删除该题？',{icon:3, title:'提示'}, function(index){
		$.ajax({
        	type:"get",
        	url: contextPath + "/admin_manage?method=deleteTitleById",
        	data:{questionTypeId: questionTypeId, id: id},
        	success:function(result){
        		if(result == "true"){
        			getTitleData();
        		}
        	},
        	dataType:"text"
        }); 
		layer.close(index);
	});
}


//function firm(questionTypeId, id) {  
//    //利用对话框返回的值 （true 或者 false）  
//    if (confirm("你确定提交吗？")) {  
//    	
//    }else {  
//        alert("点击了取消");  
//    }  
//}  

//function addWork() {
//    openlayer()
//    currentID = "";
//}
//function editWork(id) {
//    openlayer()
//    currentID = id;
//}
//function outWork(id) {
//    alert(id)
//    var NoticeId = id;
//    $.ajax({
//        url: '../WorkRecord/DeleteWork?workId=' + NoticeId,
//        type: 'GET',
//        dataType: 'json',
//        success: function (data) {
//            if (data.data) {
//                alert("删除成功！")
//                // getNoticeTableData();
//            } else {
//                alert("删除失败")
//            }
//        },
//        error: function (err) {
//        }
//    });
//}
//function getCurrentID() {
//    return currentID;
//}
//function openlayer() {
//    layer.open({
//        type: 2,
//        title: '通知信息',
//        shadeClose: true,
//        shade: 0.5,
//        skin: 'layui-layer-rim',
//        closeBtn: 2,
//        area: ['98%', '98%'],
//        shadeClose: true,
//        closeBtn: 2,
//        content:" work_tail"
//
//    });
//    
//}





