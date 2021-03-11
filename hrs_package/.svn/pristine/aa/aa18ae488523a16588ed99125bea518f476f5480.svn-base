<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
<jsp:include page="/views/common/meta.jsp"></jsp:include>
<jsp:include page="/views/common/easyui.jsp"></jsp:include>
<title>历史记录</title>
<script type="text/javascript">  
		//get title		 		    
		//var tabTile ="<%=session.getAttribute("requestInstanceName").toString()%>"+'历史记录';
		var tabTile="历史记录";
		var instanceId=<%=session.getAttribute("requestInstanceId")%>;
		var dataUrl = '/hrs/dataProcessing/findAllocInstanceHistory.do?instanceId=' + instanceId;  
			        var ruleList='{}';
		    $.ajax({    
		        url:'/hrs/dimFilterManage/getRuleList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            ruleList = data;  
		        }    
		    }); 
		    var groupList='{}';
		    $.ajax({    
		        url:'/hrs/dataProcessing/getRuleIdForSelect.do?type=GROUP',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            groupList = data;  
		        }    
		    }); 
	    $(function(){
	    	$('#tableview').edatagrid({
				title:tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'HISTORY_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:dataUrl,

				destroyMsg: {
					norecord:{	// when no record is selected
						title:'Warning',
						msg:'没有选中任何记录.'
					},
					confirm:{	// when select a row
						title:'Confirm',
						msg:'你是否要删除当前记录?'
					}
				},
				columns:[[
					{field:'RULE_ID', title:'规则', 
					 formatter:function(value, row, index){
						  	for(var i=0; i < ruleList.length; i++){
							    if (ruleList[i].RULE_ID == value) 
								    return ruleList[i].RULE_NAME;
							}
							return value;
						},
					width:50	}, 
					{field:'GROUP_HEADER_ID', title:'所属规则组', 
						formatter:function(value, row, index){
							for(var i=0; i < groupList.length; i++){
							    if (groupList[i].PERIODID == value) 
								    return groupList[i].PERIODNAME;
							}
							return value;
						},
					width:30}, 	
					{field:'PERIOD', title:'会计期间', width:30}, 	

					{field:'STATUS', title:'状态', width:50}, 	
					{field:'INFORMATION',title: "信息", width:80, 
					formatter:function(value,row,index) {						
							if (row.STATUS=='W101'){
								var s = '警告:分摊源为空 ';
								return s;
							}else if(row.STATUS=='W102'){
								var s = '警告:分摊因子为空，无法分摊';
								return s;
							}else if(row.STATUS=='W103'){
								var s = '警告:分摊结果合计不为0';
								return s;
							}else if(row.STATUS=='W104'){
								var s = '警告:存在分摊因子为空的分摊源，无法分摊';
								return s;
							}else if(row.STATUS=='W105'){
								var s = '警告:期间未打开';
								return s;
							}else if(row.STATUS=='E101'){
								var s = '分摊处理异常';
								return s;
							}else if(row.STATUS=='S101'){
								var s = '分摊处理成功';
								return s;
							}else if(row.STATUS=='R101'){
								var s = '分摊已回滚';
								return s;
							}else if(row.STATUS=='R102'){
								var s = '分摊回滚失败';
								return s;
							}else {
							    var s = '接口调用失败';
								return s;
							}
						}
					
					},
					{field:'MESSAGE', title:'信息', width:50,hidden:true}, 	
					{field:'START_TIME', title:'开始时间', width:80}, 	
					{field:'END_TIME', title:'结束时间', width:80}, 	
					{field:'ROLLBACK_TIME', title:'回滚时间', width:80},
					{field:'ELAPSED_TIME', title:'花费时间', width:50},
					{field:'CANBEROLLBACK', title:'HISTORY_ID', width:50, hidden:true}, 	
					{field:'ROLLBACK',title:'回滚',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.CANBEROLLBACK=='Y'){
								var s = '<a href="#" onclick="runJob(' + row.HISTORY_ID + ')">回滚</a> ';
								return s;
							}
						}
					}, 			
					{field:'HISTORY_ID', title:'HISTORY_ID', width:50, hidden:true},
					{field:'INSTANCE_ID',title:'INSTANCE_ID',width:50,hidden:true}
					
				]],
                onError: function(index, row){
					alert(row.msg);
                },				
	    	});
	    });
	    
    </script>
    
    
    <script type="text/javascript">
	    var timeoutId;
		var completeFlag = 'N';
        function runJob(jobId) {
            var postData = "jobId=" + jobId;
		    $.ajax({    
		        url:'/hrs/dataProcessing/rollbackAllocRequestInstanceLineCalculation.do?',     
		        type : 'GET', 
		        async: true,
		        data: postData,
		        dataType : 'json',  
		        success: function (data){
					completeFlag = 'Y';
					if (timeoutId) {
						clearTimeout(timeoutId);
						$('#progBar').progressbar('setValue', 100);
					}
					var trandata = eval(data); 
					if (trandata.success) {
					    $.messager.alert('信息',trandata.message ,'info', function() {currentTabRefresh();});
					} else {
					    $.messager.alert('错误',trandata.message,'error', function() {currentTabRefresh();});
					}		           
					
		        },
		        error: function(data, msg, exception) {
					completeFlag = 'Y';
					if (timeoutId) {
						clearTimeout(timeoutId);
						$('#progBar').progressbar('setValue', 100);
					}								
					$.messager.alert('错误','任务执行失败！','error', function() {currentTabRefresh();});
		        }  
		    });		  
		    	
			setTimeout("$('#tableview').edatagrid('reload',{});", 200);
			setTimeout("$('#tableview').edatagrid('hideColumn','RUN_ACTION');", 500);
			setTimeout("startProgress()", 1000);			
        } 
        function progressChanging(){
			var value = $('#progBar').progressbar('getValue');
			var rate = 5;
			var timeoutSec = 2000;
			if (value <= 97){		
                if (value <= 50 ) {
				    rate = 5;
					timeoutSec = 3000;
				} else if (50 < value <= 70) {
					rate = 4;
	                timeoutSec = 8000;
	            } else if (70 < value <= 90) {
					rate = 3;
	                timeoutSec = 10000;
	            } else if (90 < value <= 96) {
	                rate = 2;
	                timeoutSec = 15000; 
	            } 
	            value += Math.floor(Math.random() * rate);
	            $('#progBar').progressbar('setValue', value);
	            timeoutId = setTimeout(arguments.callee, timeoutSec);
			}
		}
		
		function startProgress() {
			if (completeFlag == 'N') {
			    $('#progBar').progressbar({value : 0});
			    progressChanging();
			}
		}
    </script>
<!--  <script type="text/javascript">
	function doSearch() {
		$('#tableview').edatagrid('load', {
			ruleName : $('#ruleName').val()
		});
	}
</script>  -->
</head>
<body>
	<!--    <div id="tb" style="padding:3px">
	    <span>规则名称:</span>
	    <input id="ruleName" name="ruleName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>-->
	<form:form id="requestInstance" commandName="requestInstance" action=""
		method="post">
		<form:hidden id="instanceId" path="instanceId" style="width:114px;" />
		<div style="margin-bottom:10px">
			 <a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true"
				onclick="currentTabRefresh()">刷新</a>
		</div>
		<div id="progBar" style="width:400px;"></div>
		<table id="tableview"></table>
		<div style="margin-bottom:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true"
				onclick="currentTabRefresh()">刷新</a>
		</div>
	</form:form>
</body>
</html>