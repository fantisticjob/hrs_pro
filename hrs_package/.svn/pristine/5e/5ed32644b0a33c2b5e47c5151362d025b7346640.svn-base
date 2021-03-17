<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    
	<script type="text/javascript"> 
	    $(function(){
	    
	    	var targetTypeList = [
			    {value:'目标',key:'TARGET'},
			    {value:'抵消',key:'OFFSET'}
			];
	    	var actualList = [
			    {value:'实际',key:'A'},
			    {value:'预算',key:'B'}
			];	    
	    	var currencyTypeList = [
			    {value:'本位币',key:'T'},
			    {value:'原币',key:'E'},
			    {value:'统计',key:'S'}
			];
	    	var currencyCodeList = [
			    {value:'CNY',key:'CNY'},
			    {value:'USD',key:'USD'},
			    {value:'HKD',key:'HKD'},
			    {value:'STAT',key:'STAT'},
			];
			
	    	var amtTypeList = [
			    {value:'PTD',key:'PTD'}
			];			
	    	var directionList = [
			    {value:'DR',key:'DR'},
			    {value:'CR',key:'CR'}
			];			
	
			
			//get title
		    var allocRuleId = $('#allocRuleId').val();
		    var dataUrl = '/hrs/allocRuleConfig.do?allocTargetDatagrid&allocRuleId=' + allocRuleId; 
		    var tabTile = $('#ruleName').val() + '-分摊(目标/抵消)配置';
	    	$('#tableview').edatagrid({
				title: tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'targetId',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url: dataUrl,
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
					{field:'targetType',title:'类型',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < targetTypeList.length; i++){
							    if (targetTypeList[i].key == value) 
								    return targetTypeList[i].value;
							}
							return value;
						},
						/*editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: targetTypeList,
								required:true
							}
						}*/
					},						
					{field:'actualFlag',title:'余额类型',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < actualList.length; i++){
							    if (actualList[i].key == value) 
								    return actualList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: actualList,
								required:true
							}
						}
					},				
					{field:'currencyType',title:'币种类型',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < currencyTypeList.length; i++){
							    if (currencyTypeList[i].key == value) 
								    return currencyTypeList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: currencyTypeList,
								required:true
							}
						}
					},				
					{field:'currencyCode',title:'币种',width:50,
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: currencyCodeList,
								required:true
							}
						}
					},				
					{field:'amountType',title:'金额类型',width:50,
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: amtTypeList,
								required:true
							}
						}
					},				
					{field:'directionCode',title:'方向',width:50,
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: directionList,
								required:true
							}
						}
					},	
                    {field:'description', title:'说明', width:50, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {	
							if (row.targetId){
								var s = '<a href="#" onclick="configTargetAcct(' + row.targetId + ',\''    + row.ruleName +      '\')">分摊(目标/抵消)账户配置</a> ';
								return s;
							}
						}
					}, 
                    {field:'targetId', title:'TARGET_ID', width:50, hidden:true}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				updateUrl: '/hrs/allocRuleConfig.do?updateAllocTarget'															  
	    	});
	    });
	    
        function configTargetAcct(target, headerName) {
        	//var targetUrl = '/hrs/allocRuleConfig.do?showAllocSourceAccountView' + '?allocSourceId=' + target;
        	var targetUrl='/hrs/dimFilterManage/showAllocTargetAccountValueView.do?allocTargetId=' + target;;  
        	var tabName = $('#ruleName').val() + '-分摊(目标/抵消)账户配置';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    targetType: $('#targetType').val()
    	    });
        }
    </script>
</head>
<body>
    <form:form id="allocTargetForm" commandName="allocRuleRecord" action="" method="post">
        <form:hidden id="allocRuleId" path="allocRuleId" style="width:114px;"/>
        <form:hidden id="ruleName" path="ruleName" style="width:114px;"/>
    <div id="tb" style="padding:3px">
	    <span>分摊目标类型:</span>
	    <input id="targetType" name="targetType" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	</form:form>
</body>
</html>