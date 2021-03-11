<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>表项定义</title>
	<script type="text/javascript"> 
	    $(function() {
		    var groupList='{}';
		    $.ajax({    
		        url:'/hrs/rptSettings/getLoopGroupList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data) {
		        	groupList = data;  
		        }    
		    });
	    	
	    	$('#tableview').edatagrid({
				title:'表项定义',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'ITEM_HEADER_ID',
				autoSave: false,
				autoSizeColumn:true,
				pagination:"true",
				url:'/hrs/rptSettings/getItemRuleList.do?',
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
					{field:'ITEM_HEADER_ID', title:'表项头ID', width:50, hidden:true },
					{field:'RULE_CODE',title:'循环组',width:50,
						formatter:function(value){
							for(var i=0; i<groupList.length; i++){
								if (groupList[i].RULE_CODE == value) 
									return groupList[i].DESCRIPTION;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'RULE_CODE',
								textField:'DESCRIPTION',
								data:groupList,
								required:true
							}
						}
					},
					{field:'ITEM_CODE', title:'表项代码', width:50, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', title:'表项名称', width:50, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'IS_CONTENT', title:'账户', width:50, hidden:true }, 
					{field:'IS_CALCULATION', title:'计算', width:50, hidden:true}, 
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {
							if (row.IS_CONTENT == 'N' && row.IS_CALCULATION == 'N'){
								var s = '<a href="#" onclick="doContent(' + row.ITEM_HEADER_ID + ',\''    + row.DESCRIPTION +      '\')">账户</a> ';
								var c = '<a href="#" onclick="doCalculation(' + row.ITEM_HEADER_ID + ',\''    + row.DESCRIPTION +      '\')">计算</a> ';
								return s+c;
							} else {
								var t = '';
								if (row.IS_CONTENT == 'Y') {
									t =  '<a href="#" onclick="doContent(' + row.ITEM_HEADER_ID + ',\''    + row.DESCRIPTION +      '\')">账户</a> ';
								} else if (row.IS_CALCULATION == 'Y') {
									t = '<a href="#" onclick="doCalculation(' + row.ITEM_HEADER_ID + ',\''    + row.DESCRIPTION +      '\')">计算</a> ';
								}
								return t;
							}
						}
					}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveItemHeader.do?',
				updateUrl: '/hrs/rptSettings/updateItemHeader.do?',
				destroyUrl: '/hrs/rptSettings/deleteItmeHeader.do?'
	    	});
	    });
	    
        function doContent(target, ruleName) {
        	var targetUrl = '/hrs/rptSettings/showItemContentView.do' + '?itemHeaderId=' + target;
        	var tabName = ruleName + '-账户配置';
        	addTabInParent(tabName,targetUrl);
        }
        
        function doCalculation(target, ruleName) {
        	var targetUrl = '/hrs/rptSettings/showItemCalculationView.do' + '?itemHeaderId=' + target;
        	var tabName = ruleName + '-计算配置';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	ruleCode: $('#ruleCode').val(),
    	    	itemCode: $('#itemCode').val(),
    	    	itemName: $('#itemName').val()
    	    });
        }
    </script>

</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>循环组:</span>
	    <input id="ruleCode" name="ruleCode" class="easyui-textbox" type="text" >
	    <span>表项代码:</span>
	    <input id="itemCode" name="itemCode" class="easyui-textbox" type="text" >
	    <span>表项名称:</span>
	    <input id="itemName" name="itemName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
</body>
</html>