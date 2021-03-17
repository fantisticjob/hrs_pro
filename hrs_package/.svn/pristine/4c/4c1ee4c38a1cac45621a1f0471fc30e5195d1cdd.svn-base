<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>维度设置</title>   
	<script type="text/javascript"> 
	    $(function(){
		    var segmentList='{}';
		    $.ajax({    
		        url:'/hrs/dimension/getSegmentList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            segmentList = data;  
		        }    
		    });
		    		    
	    	$('#tableview').edatagrid({
				title:'维度设置',
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'DIMENSION_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/dimension/findDimensionConfig.do?',
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
					{field:'DIM_SEQ', title:'序号', width:105, 
						editor:{
							type:'validatebox', 
							options:{
								required:true,
								validType:'number'
						    }
					    },align:'center'
					}, 
					
					{field:'DIMENSION_CODE', title:'维度代码', width:185,halign: 'center', editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DIMENSION_NAME', title:'维度名称', width:185, editor: {type:'validatebox', options:{required:true}},align:'center'}, 
					{field:'DIM_SEGMENT',title:'维度字段',width:185,
						formatter:function(value){
							for(var i=0; i<segmentList.length; i++){
								if (segmentList[i].dimSegmentId == value) 
									return segmentList[i].dimSegmentName;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'dimSegmentId',
								textField:'dimSegmentName',
								data:segmentList,
								required:true
							}
						},align:'center'
					},				
					{field:'FIN_ACCOUNT_FLAG', title:'是否会计科目', width:185,align:'center',
						formatter:function(value){
							if (value == 'Y') {
								return '是';
							} else {
								return '否';
							}
						},
						editor:{
							type:'checkbox',
							align:'center',
							options:{
								on:  'Y',
								off: 'N'
							}
						}
					},
					{field:'action',title:'操作',width:185,align:'center',
						formatter:function(value,row,index) {						
							if (row.DIMENSION_ID){
								var s = '<a href="#" onclick="inputDimValue(' + row.DIMENSION_ID + ',\''    + row.DIMENSION_NAME +      '\')">维值录入</a> ';
								return s;
							}
						},align:'center'
					},
					{field:'DIMENSION_ID', title:'维度ID', width:50, hidden:true}
				]],
				onError: function(index, row){
				    alert(row.msg);
				    var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_SEGMENT'});
					$(ed.target).combobox({disabled: false});
					$(ed.target).combobox('setValue', row.DIM_SEGMENT);
				},
				onEdit: function(index, row) {
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_SEGMENT'});				
					$(ed.target).combobox({disabled: true});
					$(ed.target).combobox('setValue', row.DIM_SEGMENT);
				},
				saveUrl: '/hrs/dimension/saveDimensionConfig.do?',
				updateUrl: '/hrs/dimension/updateDimensionConfig.do?'
	    	});
	    });
	    
        function inputDimValue(target, dimName) {
        	var targetUrl = '/hrs/dimension/showDimensionValueView.do' + '?demensionId=' + target;
        	var tabName = dimName + '-维值录入';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    dimensionCode: $('#dimensionCode').val(),
    		    dimensionName: $('#dimensionName').val()
    	    });
        }
    </script>
</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>维度代码:</span>
	    <input id="dimensionCode" name="dimensionCode" class="easyui-textbox" type="text" >
	    <span>维度名称:</span>
	    <input id="dimensionName" name="dimensionName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
</body>
</html>