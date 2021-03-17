<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
	<script type="text/javascript"> 
	    $(function(){
		    var txnProcessTypes='{}';
		    $.ajax({    
		        url:'/hrs/txnProcess.do?getTxnProcType',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            txnProcessTypes = data;  
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
		    
		    var curryList = [{value:'CNY',key:'CNY'}];
		    		    
	    	$('#tableview').edatagrid({
				title:'事务处理头设置',
				width: "100%",
				height: "auto",
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:false,			
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/txnProcess.do?txnProcHeaderDatagrid',
				idField:'txnHeaderId',
		        frozenColumns : [ [ {
				    title : 'txnHeaderId',
				    field : 'txnHeaderId',
				    width : 50,
				    checkbox : true
			    }] ],
				columns:[[
				   {field:'typeCode',title:'事务处理类型',width:100,
						formatter:function(value){
							for(var i=0; i<txnProcessTypes.length; i++){
								if (txnProcessTypes[i].typeCode == value) 
									return txnProcessTypes[i].typeName;
							}
							return value;
						},align:'center'
					},
				
					{field:'ledgerId', title:'账套',width:100,
						formatter:function(value, row, index){
						  	for(var i=0; i < ledgerList.length; i++){
							    if (ledgerList[i].LEDGERID == value) 
								    return ledgerList[i].LEDGERNAME;
							}
							return value;
						},align:'center'
					},

					
					{field:'companySegValue',title:'公司段',halign: 'center',width:180,
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
						}
					},

					{field:'currencyCode',title:'币种',width:60,
						formatter:function(value, row, index){
						  	for(var i=0; i < curryList.length; i++){
							    if (curryList[i].key == value) 
								    return curryList[i].value;
							}
							return value;
						},align:'center'
					},
					{field:'periodName',title:'会计期间',width:100,
						formatter:function(value, row, index){
						  	for(var i=0; i < periodList.length; i++){
							    if (periodList[i].PERIODID == value) 
								    return periodList[i].PERIODNAME;
							}
							return value;
						},align:'center'
					},		
					{field:'transactionDate', title:'事务导入时间', width:100,align:'center'},
					{field:'description', title:'事务说明',halign: 'center', width:380},
					{field:'auditFlag', title:'审核状态',halign: 'center', width:100,
						formatter:function(value){
							if (value == 'Y') {
								return '已审批';
							} else {
								return '未审批';
							}
						},align:'center'
					}, 
					{field:'transferFlag', title:'过账状态', width:100,
						formatter:function(value){
							if (value == 'Y') {
								return '已过账';
							} else {
								return '未过账';
							}
						},align:'center'
					}
				]]
	    	});
	    });
	    
    </script>
    
    <script type="text/javascript">
        
	    function auditTransactions() {
	        var rows = $('#tableview').edatagrid('getSelections');
	        var i = 0;  
            var string = "";  
            for(i; i < rows.length; i++){  
                string += rows[i].txnHeaderId;  
                if(i < rows.length - 1){  
                    string += ',';  
                } else{  
                    break;  
                }  
            }  
            if (rows.length== 0) {  
                alert("请选择要删除的行");  
            }  
            if (rows.length>0) {  
                $.messager.confirm('Confirm', '确定审批同意选定的事务?', function(r) {  
                    if (r) {  
                        $.post('/hrs/txnProcess.do?auditTransaction', 
                               {id : string}, 
                               function(result) {  
                                   currentTabRefresh();  
                               });  
                    }  
                });  
            } 
	    }

	    function doSearch() {
		    $('#tableview').edatagrid('load', {
		    	typeCode : $('#tnxTypeCode').combobox('getValue'),
				ledgerId : $('#ledgerCode').combobox('getValue'),
				period : $('#periodCode').combobox('getValue')
		    });
	    }
    </script>
</head>
<body>
     <div id="tb" style="padding:3px">
	    <!-- <span>维度代码:</span>
	    <input id="deminsionCode" name="deminsionCode" class="easyui-textbox" type="text" >
	    <span>维度名称:</span>
	    <input id="deminstionName" name="deminstionName" class="easyui-textbox" type="text" > -->
	    <span>事务处理类型:</span>
	            <form:select path="tnxTypeCode" id="tnxTypeCode" name="tnxTypeCode" data-options="
		    valueField: 'typeCode',
		    textField: 'typeName',
		    url: '/hrs/txnProcess.do?getTxnProcType'" class="easyui-combobox" style="width:100"></form:select>
	              &nbsp;&nbsp;&nbsp;	
	            <span>账套:</span>
	            <form:select path="ledgerCode" id="ledgerCode" name="ledgerCode"  data-options="
		    valueField: 'LEDGERID',
		    textField: 'LEDGERNAME',
		    url: '/hrs/dataProcessing/getLedgerList.do?'" class="easyui-combobox" style="width:130"></form:select>
	             &nbsp;&nbsp;&nbsp;
	             <span>会计区间:</span>   
	              <form:select path="periodCode" id="periodCode" name="periodCode" data-options="
		    valueField: 'PERIODID',
		    textField: 'PERIODNAME',
		    url: '/hrs/dataProcessing/getPeriodList.do?'" class="easyui-combobox" style="width:100"></form:select>        
	             &nbsp;&nbsp;&nbsp;
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="auditTransactions()">审批</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>	

</body>
</html>