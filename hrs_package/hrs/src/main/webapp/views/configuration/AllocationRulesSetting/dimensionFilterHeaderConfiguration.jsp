<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <title>维度筛选条件设置</title>   
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
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
		    
		    var txnTypeList = [
			    {value:'特定值',key:'S'}
			];
		    		    
	    	$('#tableview').edatagrid({
				title:'维度筛选条件设置',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'FILTER_HEADER_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/dimFilterManage/findDimFilterHeader.do?',
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

					
					{field:'FILTER_HEADER_NAME', title:'筛选条件名称', width:50, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', title:'说明', width:50, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'DIMENSION_SEGMENT',title:'筛选维度',width:50,
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
								url:"/hrs/dimFilterManage/getSegmentList.do?",
								required:true
							}
						}
					},
					{field:'TYPE',title:'类型',width:50,align:'center',
					formatter:function(value){
							for(var i=0; i<txnTypeList.length; i++){
								if (txnTypeList[i].key == value) 
									return txnTypeList[i].value;
							}
							return value;
						},
					editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data:txnTypeList,
								required:true
							}
						}			
					},
						
				   {field:'area',title:'范围',width:50,align:'center',
						formatter:function(value,row,index) {						
							
								var s = '<a href="#" onclick="inputlineValue(' + row.FILTER_HEADER_ID + ',\''    + row.FILTER_HEADER_NAME +      '\')">子值范围</a> ';
								return s;
							
						}
					}, 
					{field:'FILTER_HEADER_ID', title:'维度ID', width:50, hidden:true}
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
				
				saveUrl: '/hrs/dimFilterManage/saveDimFilterHeader.do?',
				updateUrl: '/hrs/dimFilterManage/updateDimFilterHeader.do?'
	    	});
	    });
	    
        function inputlineValue(target, headerName) {
        	var targetUrl = '/hrs/dimFilterManage/showLineValueView.do' + '?headerId=' + target;
        	var tabName = headerName + '-子值范围';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    deminsionFilterName: $('#deminsionFilterName').val(),
    		    deminstionName: $('#deminstionName').val()
    	    });
        }
    </script>
</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>筛选条件名称:</span>
	    <input id="deminsionFilterName" name="deminsionFilterName" class="easyui-textbox" type="text" >
	    <span>维度:</span>
	    <input id="deminstionName" name="deminstionName" class="easyui-textbox" type="text" >
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