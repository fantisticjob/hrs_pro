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
				   {field:'typeCode',title:'事务处理类型',width:50,
						formatter:function(value){
							for(var i=0; i<txnProcessTypes.length; i++){
								if (txnProcessTypes[i].typeCode == value) 
									return txnProcessTypes[i].typeName;
							}
							return value;
						}
					},
				
					{field:'ledgerId', title:'账套',width:50,
						formatter:function(value, row, index){
						  	for(var i=0; i < ledgerList.length; i++){
							    if (ledgerList[i].LEDGERID == value) 
								    return ledgerList[i].LEDGERNAME;
							}
							return value;
						}
					},

					
					{field:'companySegValue',title:'公司段',width:50,
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

					{field:'currencyCode',title:'币种',width:50,
						formatter:function(value, row, index){
						  	for(var i=0; i < curryList.length; i++){
							    if (curryList[i].key == value) 
								    return curryList[i].value;
							}
							return value;
						}
					},
					{field:'periodName',title:'会计期间',width:50,
						formatter:function(value, row, index){
						  	for(var i=0; i < periodList.length; i++){
							    if (periodList[i].PERIODID == value) 
								    return periodList[i].PERIODNAME;
							}
							return value;
						}
					},		
					{field:'transactionDate', title:'事务导入时间', width:50},
					{field:'description', title:'事务说明', width:50},
					{field:'auditFlag', title:'审核状态', width:50,
						formatter:function(value){
							if (value == 'Y') {
								return '已审批';
							} else {
								return '未审批';
							}
						}
					}, 
					{field:'transferFlag', title:'过账状态', width:50,
						formatter:function(value){
							if (value == 'Y') {
								return '已过账';
							} else {
								return '未过账';
							}
						}
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
		    datagrid.edatagrid('load', {
			    dimCode : $('#tb input[name=dimCode]').val()
		    });
	    }
    </script>
</head>
<body>
     <div id="tb" style="padding:3px">
	    <span>维度代码:</span>
	    <input id="deminsionCode" name="deminsionCode" class="easyui-textbox" type="text" >
	    <span>维度名称:</span>
	    <input id="deminstionName" name="deminstionName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="auditTransactions()">审批</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>	

</body>
</html>