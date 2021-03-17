<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>分摊规则组头表设置</title>   
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/easyui/themes/default/easyui.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/easyui/themes/icon.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fw.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/demo.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fancybox/jquery.fancybox-1.3.4.css"/>">
	
	<script type="text/javascript" src="<c:url value="/resources/easyui/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/easyui/jquery.easyui.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/JQuery-formui.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/easyui/locale/easyui-lang-zh_CN.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/validator.js"/>"></script>
	<script type="text/javascript"> 
	    $(function(){
	    	$('#tableview').edatagrid({
				title:'分摊规则组设置',
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: false,
				singleSelect:true,
				idField:'GROUP_HEADER_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/dimFilterManage/findAllocRulesGroupHaeader.do?',
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
                    {field:'GROUP_NUM', title:'序号',align: 'center', width:80, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'GROUP_NAME', title:'分摊规则组',align: 'center', width:120, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'DESCRIPTION', title:'说明',halign: 'center', width:200, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'START_DATE_ACTIVE',title:'有效日期自',align: 'center',width:150,editor:{type:'datebox'}},
					{field:'END_DATE_ACTIVE',title:'有效日期至',align: 'center',width:150,editor:{type:'datebox'}},
				    {field:'area',title:'分摊规则',width:150,align:'center',
						formatter:function(value,row,index) {						
							
						var s = '<a href="#" onclick="inputlineValue(' + row.GROUP_HEADER_ID + ',\''    + row.GROUP_NAME +      '\')">规则</a> ';
								return s;
							
						}
					}, 
					{field:'GROUP_HEADER_ID', title:'GROUP_HEADER_ID', width:50, hidden:true}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
             	saveUrl: '/hrs/dimFilterManage/saveAllocRulesGroupHaeader.do?',
				updateUrl: '/hrs/dimFilterManage/updateAllocRulesGroupHaeader.do?'	,
				destroyUrl: '/hrs/dimFilterManage/destroyAllocRulesGroupHaeader.do?'																  
	    	});
	    });
	    
        function inputlineValue(target, headerName) {
        	var targetUrl = '/hrs/dimFilterManage/showAllocRulesGroupLineView.do' + '?headerId=' + target;
        	var tabName = headerName + '-子值范围';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    allocRulesGroupHaeader: $('#allocRulesGroupHaeader').val()
    	    });
        }
    </script>
</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>规则组:</span>
	    <input id="allocRulesGroupHaeader" name="allocRulesGroupHaeader" class="easyui-textbox" type="text" >
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