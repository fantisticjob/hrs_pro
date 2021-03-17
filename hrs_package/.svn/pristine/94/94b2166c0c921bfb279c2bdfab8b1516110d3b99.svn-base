<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>静态分摊因子头表设置</title>   
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
		    var segmentList='{}';
		    $.ajax({    
		        url:'/hrs/dimFilterManage/getSegmentList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            segmentList = data;  
		        }    
		    });
		    
			
			
			
		    		    
	    	$('#tableview').edatagrid({
				title:'静态分摊因子头表设置',
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: false,
				singleSelect:true,
				idField:'STATIC_HEADER_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/dimFilterManage/findDriverStaticHeader.do?',
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

					
					{field:'DRIVER_CODE', title:'分摊因子', align: 'center',width:185, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', title:'说明',halign: 'center', width:185, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'DIMENSION_SEGMENT',title:'筛选维度',align: 'center',width:185,
						formatter:function(value){
							for(var i=0; i<segmentList.length; i++){
								if (segmentList[i].DIM_SEGMENT == value) 
									return segmentList[i].DIMENSION_NAME;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'DIM_SEGMENT',
								textField:'DIMENSION_NAME',
								data:segmentList,
								required:true
							}
						}
					},
					{field:'START_DATE_ACTIVE',title:'有效日期自',align: 'center',width:185,editor:{type:'datebox'}},
					{field:'END_DATE_ACTIVE',align: 'center',title:'有效日期至',width:185,editor:{type:'datebox'}},
				    {field:'area',title:'范围',width:185,align:'center',
						formatter:function(value,row,index) {						
							
						var s = '<a href="#" onclick="inputlineValue(' + row.STATIC_HEADER_ID + ',\''    + row.DRIVER_CODE +      '\')">子值范围</a> ';
								return s;
							
						}
					}, 
					{field:'STATIC_HEADER_ID', title:'STATIC_HEADER_ID', width:50, hidden:true}
				]],
				/* onError: function(index, row){
				    alert(row.msg);
				    var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_SEGMENT'});
					$(ed.target).combobox({disabled: false});
					$(ed.target).combobox('setValue', row.DIM_SEGMENT);
				},
				onEdit: function(index, row) {
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_SEGMENT'});				
					$(ed.target).combobox({disabled: true});
					$(ed.target).combobox('setValue', row.DIM_SEGMENT);
				}, */
				onError: function(index,row){
					alert(row.msg);
				},
				//saveUrl: '/hrs/dimFilterManage/findDimFilterHeader',
				
				saveUrl: '/hrs/dimFilterManage/saveDriverStaticHeader.do?',
				updateUrl: '/hrs/dimFilterManage/updateDriverStaticHeader.do?'	,
				destroyUrl: '/hrs/dimFilterManage/destroyDriverStaticHeader.do?'																  
	    	});
	    });
	    
        function inputlineValue(target, headerName) {
        	var targetUrl = '/hrs/dimFilterManage/showDriverStaticLineValueView.do' + '?headerId=' + target;
        	var tabName = headerName + '-子值范围';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    driverCode: $('#driverCode').val(),
    		    description: $('#description').val()
    	    });
        }
    </script>
</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>分摊动因:</span>
	    <input id="driverCode" name="driverCode" class="easyui-textbox" type="text" >
	    <span>说明:</span>
	    <input id="description" name="description" class="easyui-textbox" type="text" >
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