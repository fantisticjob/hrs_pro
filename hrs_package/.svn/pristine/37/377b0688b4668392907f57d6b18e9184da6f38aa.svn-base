<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>分摊规则运行</title>   
	<script type="text/javascript">     
	    $(function(){
	     /*   var ledgerList='{}';
 		    $.ajax({    
		        url:'/hrs/dataProcessing/getLedgerList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            ledgerList = data;  
		        }    
		    }); */
	    	var periodList='{}';
 		    $.ajax({    
		        url:'/hrs/dataProcessing/getAvailablePeriodList.do?',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            periodList = data;  
		        }    
		    }); 
		    var typeList = [

			    {value:'分摊规则组',key:'GROUP'},
			    {value:'动态因子',key:'DYNAMIC'},
			    {value:'静态因子',key:'STATIC'},
			    {value:'常数',key:'CONSTANT'}
			];
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
		    
	      //  var editing ; //判断用户是否处于编辑状态   
           // var flag  ;   //判断新增和修改方法 
	    	$('#tableview').edatagrid({
				title:'报表运行记录',
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
				url:'/hrs/dataProcessing/getAllocRequestInstance.do?',
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
				    {field:'INSTANCE_ID', title:'任务ID', width:50, hidden:true},
				    {field:'RULE_TYPE',title:'规则类型',width:50,
						formatter:function(value, row, index){
						  	for(var i=0; i < typeList.length; i++){
							    if (typeList[i].key == value) 
								    return typeList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'key',
								textField:'value',
								data:typeList,
							    onSelect:function(data){  
                                        var row = $('#tableview').datagrid('getSelected');  
                                        var rowIndex = $('#tableview').datagrid('getRowIndex',row);//获取行号  
                                        var thisTarget = $('#tableview').datagrid('getEditor', {'index':rowIndex,'field':'RULE_TYPE'}).target;  
                                        var value = thisTarget.combobox('getValue');  
                                        var target = $('#tableview').datagrid('getEditor', {'index':rowIndex,'field':'RULE_ID'}).target;  
                                        target.combobox('clear'); //清除原来的数据  
										var url = '/hrs/dataProcessing/getRuleIdForSelect.do?type=' +data.key;  
                                        target.combobox('reload', url);//联动下拉列表重载  
                                    }  
							}
						}
					},
					{field:'RULE_ID', title:'规则',width:50,
						formatter:function(value, row, index){
						if(row.RULE_TYPE!='GROUP'){
						  	for(var i=0; i < ruleList.length; i++){
							    if (ruleList[i].RULE_ID == value) 
								    return ruleList[i].RULE_NAME;
							}
							return value;
							}else{
							for(var i=0; i < groupList.length; i++){
							    if (groupList[i].PERIODID == value) 
								    return groupList[i].PERIODNAME;
							}
							return value;
							}
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'RULE_ID',
								textField:'RULE_NAME',
								url:  '/hrs/dataProcessing/getRuleIdForSelect.do?type=GROUP' 
							}
						}
					},
					{field:'PERIOD',title:'会计区间',width:50,
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
						}
					},
					//{field: 'DESCRIPTION',title'描述',width:80,editor: {type: 'validatebox',options:{required:false}}},
					{field:'DESCRIPTION',title:'说明',align:'left',width:80,editor: {type: "validatebox"}},
					{field:'RUN_ACTION',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.INSTANCE_ID){
								var s = '<a href="#" onclick="runJob(' + row.INSTANCE_ID + ')">运行</a> ';
								return s;
							}
						}
					},
					{field:'GET_HISTORY',title:'历史记录',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.INSTANCE_ID){
							    var s = '<a href="#" onclick="getHistroyJob(' +  row.INSTANCE_ID+ ',\''    + row.DESCRIPTION +      '\')">历史记录</a> ';
								return s;
							}
						}
					}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
			/*	onEdit: function(index, row) {
                    var reloadUrl =  '';
					if (row) {
						reloadUrl = '/hrs/dataProcessing/getRuleIdForSelect.do?type=' + row.RULE_TYPE;
					} else {
						return;
					}

	            var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'RULE_ID'});
					if (ed) {
					    var filterTarget = $(ed.target);
					    if (filterTarget) {
					        filterTarget.combobox('reload', reloadFilterUrl);
					    }
					} 
				},	*/
				saveUrl: '/hrs/dataProcessing/saveAllocRequestInstance.do?',
			    updateUrl: '/hrs/dataProcessing/updateAllocRequestInstance.do?',
				destroyUrl: '/hrs/dataProcessing/deleteAllocRequestInstance.do?'
	    	});
	    });
    </script>

    <script type="text/javascript">
	    var timeoutId;
		var completeFlag = 'N';
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	        ruleType: $('input[name="ruleType"]').val(),
                period: $('input[name="period"]').val(),
    	    	status: $('#status').val()
    	    });
        }
        function getHistroyJob(target, headerName) {
        	//var targetUrl = '/hrs/allocRuleConfig.do?showAllocSourceAccountView' + '?allocSourceId=' + target;
        	var targetUrl='/hrs/dataProcessing/showAllocInstanceHistoryView.do?instanceId=' + target;  
        	var tabName = headerName+ '-分摊源账户配置';
        	addTabInParent(tabName,targetUrl);
        }
        
        function runJob(jobId) {
            var postData = "jobId=" + jobId;
		    $.ajax({    
		        url:'/hrs/dataProcessing/AllocRequestInstanceLineCalculation.do?',     
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

</head>
<body>
    <form:form id="requestExceForm" commandName="executCondition" action="" method="post">        
        <div id="tb" style="padding:3px">
	        &nbsp;&nbsp;&nbsp;<span>规则类型:</span>
	            <form:select path="ruleType" id="ruleType" name="ruleType" items="${executCondition.typeList}" itemLabel="description"  itemValue="driverType" class="easyui-combobox" style="width:100"></form:select>
	        &nbsp;&nbsp;&nbsp;<span>会计区间:</span>
	            <form:select path="period" id="period" name="period" items="${executCondition.periodList}" itemLabel="periodName" itemValue="periodName" class="easyui-combobox" style="width:100"></form:select>  	       
	        &nbsp;&nbsp;&nbsp;<span>状态:</span>
	            <form:input path="status" id="status" name="status" class="easyui-textbox" />
	        <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
        </div>
        <div style="margin-bottom:10px">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
	    <div id="progBar" style="width:400px;"></div>
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