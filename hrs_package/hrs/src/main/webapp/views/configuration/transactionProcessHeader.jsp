<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
	<script type="text/javascript"> 
	    $(function(){
		    var txnProcessTypes='{}';
	/*	    $.ajax({    
		        url:'/hrs/txnProcess.do?getTxnProcType',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            txnProcessTypes = data;  
		        }    
		    });*/
		   $.ajax({    
		        url:'/hrs/rptSettings/getLookUpLineList.do?lookUpTypeId=1063',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            txnProcessTypes = data;  
		        }    
		    });
		  	var ledgerList='{}';
		   $.ajax({    
		        url:'/hrs/rptSettings/getLookUpLineList.do?lookUpTypeId=1050',     
		        type : 'POST', 
		        async: false,  
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
		    
		    var curryList = [{value:'CNY',key:'CNY'}];
		    		    
	    	$('#tableview').edatagrid({
				title:'事务处理头设置',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'txnHeaderId',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/txnProcess.do?txnProcHeaderDatagrid',
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
				   {field:'typeCode',title:'事务处理类型',width:50,
						formatter:function(value){
							for(var i=0; i<txnProcessTypes.rows.length; i++){
								if (txnProcessTypes.rows[i].LOOKUP_VALUE == value) 
									return txnProcessTypes.rows[i].DESCRIPTION;
							}
							return value;
						},
						/*editor:{
							type:'combobox',
							options:{
								valueField:'typeCode',
								textField:'typeName',
								data:txnProcessTypes,
								required:true
							}
						}*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                data:txnProcessTypes,
				                idField:'LOOKUP_VALUE',
				                textField:'DESCRIPTION',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'LOOKUP_VALUE',title:'编码',width:60},
					                {field:'DESCRIPTION',title:'说明',width:80}
			                   	]],
			                   	options:{
							        required:true
							    },
			                   //	toolbar: '#comboToolBar'
							}
						}
					},
				
					{field:'ledgerId', title:'账套',width:40,
						formatter:function(value, row, index){
						  	for(var i=0; i < ledgerList.rows.length; i++){
							    if (ledgerList.rows[i].LOOKUP_VALUE == value) 
								    return ledgerList.rows[i].DESCRIPTION;
							}
							return value;
						},
						/*editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'LEDGERID',
								textField:'LEDGERNAME',
								data:ledgerList
							}
						}*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                data:ledgerList,
				                idField:'LOOKUP_VALUE',
				                textField:'DESCRIPTION',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'LOOKUP_VALUE',title:'编码',width:60},
					                {field:'DESCRIPTION',title:'说明',width:80}
			                   	]],
			                   	options:{
							        required:true
							    },
			                   //	toolbar: '#comboToolBar'
							}
						}
					},

					
					{field:'companySegValue',title:'公司段',width:80,
					    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT1",
						                "selDimValueCode":	value
			    	                },						
		                            dataType : 'json',  
		                            success: function (data){
									    var transData = eval(data);
									    if (transData.DISP_VALUE) {
										    returnValue = transData.DISP_VALUE;
									    } 
		                            }    
		                        });
					        }
							return returnValue;
						},
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT1&security=true',
				                idField:'DIM_VALUE',
				                textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',title:'维度值',width:60},
					                {field:'DESCRIPTION',title:'说明',align:'right',width:80}
			                   	]],
			                   	options:{
							        required:true
							    },
			                   	toolbar: '#comboToolBar'
							}
						}
					},

					{field:'currencyCode',title:'币种',width:30,
						formatter:function(value, row, index){
						  	for(var i=0; i < curryList.length; i++){
							    if (curryList[i].key == value) 
								    return curryList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'key',
								textField:'value',
								data:curryList
							}
						}
					},
					{field:'periodName',title:'会计期间',width:40,
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
					{field:'transactionDate', title:'事务导入时间', width:40},
					{field:'description', title:'事务说明', width:90, editor:'text'},
					{field:'auditFlag', title:'审核状态', width:30,
						formatter:function(value){
							if (value == 'Y') {
								return '已审批';
							} else {
								return '未审批';
							}
						}
					}, 
					{field:'transferFlag', title:'过账状态', width:30,
						formatter:function(value){
							if (value == 'Y') {
								return '已过账';
							} else {
								return '未过账';
							}
						}
					},
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.txnHeaderId){
								var s = '<a href="#" onclick="inputDimValue(' + row.txnHeaderId + ',\''    + row.description +      '\')">处理行录入</a> ';
								return s;
							}
						}
					},
					{field:'creationDate', title:'创建时间', width:50, hidden:true},
					{field:'createdBy', title:'创建人', width:50, hidden:true},
					{field:'lastUpdateDate', title:'最后更新时间', width:50, hidden:true},
					{field:'lastUpdatedBy', title:'最后更新人', width:50, hidden:true},
					{field:'txnHeaderId', title:'事务处理头ID', width:50, hidden:true}
				]],
				
				onError: function(index, row){
				    alert(row.msg);
				},
			    onBeforeEdit:function(index,row){
			        if (row.auditFlag == "Y") {
			           disableEditing();
			        }
                },
				
				saveUrl: '/hrs/txnProcess.do?saveTxnProcHeader',
				updateUrl: '/hrs/txnProcess.do?updateTxnProcHeader',
				destroyUrl: '/hrs/txnProcess.do?delTxnProcHeader'
	    	});
	    });
	    
        function inputDimValue(target, dimName) {
        	var targetUrl = '/hrs/txnProcess.do?showTransactionProcLineView' + '&txnProcHeaderId=' + target;
        	if (dimName) {
        	    if (dimName == '') {
        	        dimName = target + '-处理行录入';
        	    }
        	} else {
        	    dimName = target;
        	}
        	var tabName = dimName + '-处理行录入';
        	addTabInParent(tabName,targetUrl);
        }
    </script>
    
    <script type="text/javascript">
        
        function comboGridSearch() {
			var combox = $('.combogrid-f').combogrid('grid');
			if (combox) {
			    $('.combogrid-f').combogrid('grid').datagrid(
			                              'reload',
			                              {comboxDimCode: $('#comboxDimCode').val(),
			                               performOperation: 'comboSearch'
			                              }
			                          );
			}
        }
    </script>
	<script type="text/javascript">
        function doSearch(){
              //   alert($('input[name="tnxTypeCode"]').val());
               //  alert($('input[name="ledgerCode"]').val());
              //   alert($('input[name="periodCode"]').val());
    	    $('#tableview').edatagrid('load',{
    	        typeCode: $('input[name="tnxTypeCode"]').val(),
    	    	ledgerId: $('input[name="ledgerCode"]').val(),
    	    	period: $('input[name="periodCode"]').val()
    	    });
        }
	</script>
</head>
<body>
     <form:form id="requestExceForm" commandName="executCondition" action="" method="post">        
        <div id="tb" style="padding:3px">
                <span>事务处理类型:</span>
	            <form:select path="tnxTypeCode" id="tnxTypeCode" name="tnxTypeCode" items="${executCondition.tnxTypelist}" itemLabel="name"  itemValue="code" class="easyui-combobox" style="width:100"></form:select>
	              &nbsp;&nbsp;&nbsp;	
	            <span>账套:</span>
	            <form:select path="ledgerCode" id="ledgerCode" name="ledgerCode"  items="${executCondition.ledgerList}" itemLabel="lederName" itemValue="lederCode" class="easyui-combobox" style="width:130"></form:select>
	             &nbsp;&nbsp;&nbsp;
	             <span>会计区间:</span>   
	              <form:select path="periodCode" id="periodCode" name="periodCode" items="${executCondition.periodList}" itemLabel="periodName" itemValue="periodName" class="easyui-combobox" style="width:100"></form:select>        
	             &nbsp;&nbsp;&nbsp;
           	       <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
          </div>
     
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" 
	                     onclick="addTabInParent('事务行Excel导入','/hrs/txnProcess.do?showTransactionExcelImport');">Excel导入</a>
	
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	 </form:form>
	<div id="comboToolBar" class="datagrid-toolbar" hidden="true">  
	    <span>维度代码:</span>
	    <input id="comboxDimCode" name="comboxDimCode" class="easyui-textbox" type="text" style="width: 100px;">
		<a href="#" class="easyui-linkbutton" onclick="comboGridSearch()">查询</a>
        <div style="clear: both"></div>  
    </div>

</body>
</html>