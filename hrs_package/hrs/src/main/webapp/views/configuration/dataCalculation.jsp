<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>报表行计算</title>   
	<script type="text/javascript">     
	    $(function(){
	    	var status='{}';
 		    $.ajax({    
		        url:'/hrs/etlProcessing/getEtlScheduleStatusList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            status = data;  
		        }    
		    }); 
	        var ledgerList='{}';
 		    $.ajax({    
		        url:'/hrs/dataProcessing/getLedgerList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            ledgerList = data;  
		        }    
		    }); 
	    	var periodList='{}';
 		    $.ajax({    
		        url:'/hrs/dataProcessing/getPeriodList.do?',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            periodList = data;  
		        }    
		    }); 
		    var finEleList = [
			    {value:'财务',key:'F'},
			    {value:'非财务',key:'NF'},
			    {value:'全部',key:'A'}
			];
	    
	    	$('#tableview').edatagrid({
				title:'报表指标计算',
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'INSTANCE_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/dataProcessing/getRequestInstanceList.do?',
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
				    {field:'INSTANCE_ID', title:'任务ID', width:50,align:'center'},
					{field:'LEDGER_ID', title:'账套',width:100,halign: 'center',
						formatter:function(value, row, index){
						  	for(var i=0; i < ledgerList.length; i++){
							    if (ledgerList[i].LEDGERID == value) 
								    return ledgerList[i].LEDGERNAME;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'LEDGERID',
								textField:'LEDGERNAME',
								data:ledgerList
							}
						},align:'center'
					},
					{field:'FIN_ELEMENT_TYPE',title:'财务要素',width:60,
						formatter:function(value, row, index){
						  	for(var i=0; i < finEleList.length; i++){
							    if (finEleList[i].key == value) 
								    return finEleList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'key',
								textField:'value',
								data:finEleList
							}
						},align:'center'
					},
					{field:'PERIOD_FROM',title:'会计区间起',width:80,halign: 'center',
						formatter:function(value, row, index){
						  	for(var i=0; i < periodList.length; i++){
							    if (periodList[i].PERIODID == value) 
								    return periodList[i].PERIODNAME;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'PERIODID',
								textField:'PERIODNAME',
								data:periodList
							}
						},align:'center'
					},
					{field:'PERIOD_TO',title:'会计区间至',width:80,
						formatter:function(value, row, index){
						  	for(var i=0; i < periodList.length; i++){
							    if (periodList[i].PERIODID == value) 
								    return periodList[i].PERIODNAME;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'PERIODID',
								textField:'PERIODNAME',
								data:periodList
							}
						},align:'center'
					},
					{field:'START_TIME',title:'开始时间',width:130,align:'center'},
					{field:'END_TIME',title:'结束时间',width:130,align:'center'},
					{field:'ELAPSED_TIME',title:'花费时间',width:80,align:'center'},
					{field:'STATUS',title:'状态',width:100,formatter : function(value, row, index) {
						for (var i = 0; i < status.length; i++) {
							if (status[i].STATUS_ID == value)
								return status[i].STATUS_DESCRIPTION;
						}
						return value;
					},
					align : 'center'},
					{field:'MESSAGE',title:'信息',width:200,halign: 'center'},
					{field:'RUN_ACTION',title:'操作',width:80,align:'center',
						formatter:function(value,row,index) {
							if (row.INSTANCE_ID){
								var s = '<a href="#" onclick="runJob(' + row.INSTANCE_ID + ')">运行</a> ';
								return s;
							}
						}
					}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/dataProcessing/saveBatchJob.do?',
			    updateUrl: '/hrs/dataProcessing/updateBatchJob.do?',
				destroyUrl: '/hrs/dataProcessing/deleteBatchJob.do?'
	    	});
	    });
    </script>

    <script type="text/javascript">
	    var timeoutId;
		var completeFlag = 'N';
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	        ledgerId: $('input[name="ledgerId"]').val(),
    	    	//ledgerId: $('#ledgerId').val(),
    	    	//finElementType: $('#finElementType').val(),
    	    	//periodName: $('#periodName').val(),
    	    	 finElementType: $('input[name="finElementType"]').val(),
    	    	 //periodName: $('input[name="periodName"]').val(),
    	    	 periodFrom: $('input[name="periodFrom"]').val(),
    	    	 periodTo: $('input[name="periodTo"]').val(),
    	    	 status: $('input[name="status"]').val(),
    	    	 
    	    	//status: $('#status').val()
    	    });
        }
        function runJob(jobId) {
            var postData = "jobId=" + jobId;
		    $.ajax({    
		        url:'/hrs/dataProcessing/performCalculation.do?',     
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
					    $.messager.alert('信息',trandata.message ,'info', function() {$("#tableview").datagrid("reload");});
					} else {
					    $.messager.alert('错误',trandata.message,'error', function() {$("#tableview").datagrid("reload");});
					}		           
					
		        },
		        error: function(data, msg, exception) {
					completeFlag = 'Y';
					if (timeoutId) {
						clearTimeout(timeoutId);
						$('#progBar').progressbar('setValue', 100);
					}								
					$.messager.alert('错误','任务执行失败！','error', function() {$("#tableview").datagrid("reload");});
		        }  
		    });		  
		    	
			setTimeout("$('#tableview').edatagrid('reload',{});", 200);
			//setTimeout("$('#tableview').edatagrid('hideColumn','RUN_ACTION');", 500);
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

</head>
<body>
    <form:form id="requestExceForm" commandName="executCondition" action="" method="post">        
        <div id="tb" style="padding:3px">
	        <span>账套:</span>
	            <form:select path="ledgerId" id="ledgerId" name="ledgerId" items="${executCondition.ledgerSelectionList}" itemLabel="lederName" itemValue="lederCode" class="easyui-combobox" style="width:100"></form:select>
	        &nbsp;&nbsp;&nbsp;<span>财务要素类型:</span>
	            <form:select path="finElementType" id="finElementType" name="finElementType" items="${executCondition.finEleSelectionList}" itemLabel="name"  itemValue="value" class="easyui-combobox" style="width:100"></form:select>
	        &nbsp;&nbsp;&nbsp;<span>会计区间起:</span>
	            <form:select path="periodFrom" id="periodFrom" name="periodFrom" items="${executCondition.periodList}" itemLabel="periodName" itemValue="periodName" class="easyui-combobox" style="width:100"></form:select>        
	        &nbsp;&nbsp;&nbsp;<span>会计区间至:</span>
	            <form:select path="periodTo" id="periodTo" name="periodTo" items="${executCondition.periodList}" itemLabel="periodName" itemValue="periodName" class="easyui-combobox" style="width:100"></form:select>  	       
	        &nbsp;&nbsp;&nbsp;<span>状态:</span>
	            <form:input path="status" id="status" name="status" class="easyui-combobox"  data-options="
		    valueField: 'STATUS_TYPE',
		    textField: 'STATUS_DESCRIPTION',
		    url: '/hrs/etlProcessing/getEtlScheduleStatusList.do'"/>
	        <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
        </div>
        <div style="margin-bottom:10px">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
	    <div id="progBar" style="width:400px;margin-bottom: 10px"></div>
	    <table id="tableview"></table>
	    <div style="margin-bottom:10px">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
    </form:form>
</body>
</html>