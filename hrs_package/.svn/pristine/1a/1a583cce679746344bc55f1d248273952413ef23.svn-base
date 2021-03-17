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
	    
	    	var driverTypeList = [
			    {value:'静态因子',key:'S'},
			    {value:'动态因子',key:'D'},
			    {value:'常数',key:'C'}
			];
	    
	    	$('#tableview').edatagrid({
				title:'分摊规则设置',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'allocRuleId',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/allocRuleConfig.do?allocRuleDatagrid',
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
                    {field:'lineNumber', title:'序号', width:10, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'ruleName', title:'分摊规则名称', width:70, editor: {type:'validatebox', options:{required:true}}}, 
/*					{field:'driverType',title:'分摊因子配置',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < driverTypeList.length; i++){
							    if (driverTypeList[i].key == value) 
								    return driverTypeList[i].value;
							}
						
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: driverTypeList,
								required:true
							}
						}
					},*/
				
					{field:'startDateActive',title:'有效日期自',width:40,editor:{type:'datebox'}},
					{field:'endDateActive',title:'有效日期至',width:40,editor:{type:'datebox'}},					
					{field:'description', title:'说明', width:50, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'driversType',title:'分摊因子',width:50,align:'center',
						formatter:function(value,row,index) {	
							if (row.allocRuleId){
								var s = '<a href="#" onclick="inputDriverValue(' + row.allocRuleId + ',\''    + row.ruleName +      '\')">分摊因子配置</a> ';
								return s;
							}
						}
					}, 
					{field:'source',title:'分摊源',width:50,align:'center',
						formatter:function(value,row,index) {	
							if (row.allocRuleId){
								var s = '<a href="#" onclick="inputSourceValue(' + row.allocRuleId + ',\''    + row.ruleName +      '\')">分摊源配置</a> ';
								return s;
							}
						}
					}, 
					{field:'target',title:'分摊目标',width:50,align:'center',
						formatter:function(value,row,index) {	
							if (row.allocRuleId && row.isSourceOver=='Y' && row.isDriverOver=='Y'){
								var s = '<a href="#" onclick="inputTargetValue(' + row.allocRuleId + ',\''    + row.ruleName +      '\')">分摊(目标/抵消)配置</a> ';
								return s;
							}
						}
					}, 
                    {field:'allocRuleId', title:'RULE_ID', width:50, hidden:true},
                    {field:'isSourceOver', title:'ISSOURCEOVER', width:50, hidden:true},
                    {field:'isDriverOver', title:'ISDRIVEROVER', width:50, hidden:true},
				]],
				onError: function(index,row){
					alert(row.msg);
				},
             	saveUrl: '/hrs/allocRuleConfig.do?saveAllocRule',
				updateUrl: '/hrs/allocRuleConfig.do?updateAllocRule',
				destroyUrl: '/hrs/allocRuleConfig.do?delAllocRule'																  
	    	});
	    });
	    
	   function inputDriverValue(target, headerName) {
        	var targetUrl = '/hrs/allocRuleConfig.do?showAllocDriverView' + '&allocRuleId=' + target;
        	var tabName = headerName + '-分摊因子配置';
        	addTabInParent(tabName,targetUrl);
        }
	    
        function inputSourceValue(target, headerName) {
        	var targetUrl = '/hrs/allocRuleConfig.do?showAllocSourceView' + '&allocRuleId=' + target;
        	var tabName = headerName + '-分摊源配置';
        	addTabInParent(tabName,targetUrl);
        }
        
        function inputTargetValue(target, headerName) {
        	var targetUrl = '/hrs/allocRuleConfig.do?showAllocTargetView' + '&allocRuleId=' + target;
        	var tabName = headerName + '-分摊目标配置';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    ruleName: $('#ruleName').val()
    	    });
        }
    </script>
</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>规则名称:</span>
	    <input id="ruleName" name="ruleName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
</body>
</html>