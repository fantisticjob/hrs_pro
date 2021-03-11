<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>表项循环组定义</title>   
    <script type="text/javascript"> 
	    $(function(){		    		    
	    	$('#tableview').edatagrid({
				title:'表项循环组定义',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'RULE_HEADER_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination: true,
				url:'/hrs/rptSettings/getItemLoopRuleList.do?',
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
					{field:'RULE_CODE', title:'循环组编码', width:50, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', title:'描述', width:50, editor: {type:'validatebox', options:{required:true}}}, 
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.RULE_HEADER_ID){
								var s = '<a href="#" onclick="inputLoopDimension(' + row.RULE_HEADER_ID + ',\''    + row.DESCRIPTION +      '\')">维度定义描述</a> ';
								return s;
							}
						}
					},
					{field:'RULE_HEADER_ID', title:'循环组ID', width:50, hidden:true}
				]],
				onError: function(index, row){
				    alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveLoopRuleHeader.do?',
				updateUrl: '/hrs/rptSettings/updateLoopRuleHeader.do?',
				destroyUrl: '/hrs/rptSettings/deleteLoopRuleHeader.do?'
	    	});
	    });
	    
        function inputLoopDimension(target, ruleName) {
        	var targetUrl = '/hrs/rptSettings/showLoopDimensionView.do' + '?loopRuleId=' + target;
        	var tabName = ruleName + '-维度定义描述';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	ruleCode: $('#ruleCode').val(),
    	    	ruleName: $('#ruleName').val()
    	    });
        }
    </script>
</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>循环组代码:</span>
	    <input id="deminsionCode" name="deminsionCode" class="easyui-textbox" type="text" >
	    <span>循环组名称:</span>
	    <input id="deminstionName" name="deminstionName" class="easyui-textbox" type="text" >
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