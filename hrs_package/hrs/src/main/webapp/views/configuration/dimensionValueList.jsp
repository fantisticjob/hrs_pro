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
		    var acctTypeList='{}';
		    var dimLevelList='{}';    
		    $.ajax({    
		        url:'/hrs/dimension/getAccountTypeList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		        	acctTypeList = data;  
		        }    
		    });
		    
		    $.ajax({    
		        url:'/hrs/dimension/getDimLevelList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		        	dimLevelList = data;  
		        }    
		    });
		    //get title
		    var tabTile = $('#dimenName').val() + '-维值录入';
		    var dimensionId = $('#dimenId').val();
		    var isFinAcct = $('#isFinFlag').val();
		    var dataUrl = '/hrs/dimension/findDimensionValueList.do?dimensionId=' + dimensionId + '&isFinAcctFlag=' + isFinAcct;
		    
	    	$('#tableview').edatagrid({
				title: tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				autoSizeColumn:true,
				idField:'DIM_VALUE_ID',
				autoSave: false,
				pagination:"true",
				url:dataUrl,
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
			    onLoadSuccess: function (data) {
			    	var acctFlag = $('#isFinFlag').val();
			    	if (acctFlag == 'N') {
			    		$('#tableview').edatagrid('hideColumn','ACCOUNT_TYPE');		  		
			    	}
		        },
		        
		        onEdit: function(index, row) {
		        	var acctFlag = $('#isFinFlag').val();
		        	if (acctFlag == 'N') {
						var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'ACCOUNT_TYPE'});				
						$(ed.target).combobox('setValue', '-1');		
			    	}
					var dimValCell = $('#tableview').edatagrid('getEditor', {index : index, field : 'DIM_VALUE'}); 
					if (dimValCell) {
						$(dimValCell.target).attr("disabled", true);
					}
				},
				
				onAdd: function(index, row) {
		        	var acctFlag = $('#isFinFlag').val();
		        	if (acctFlag == 'N') {
						var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'ACCOUNT_TYPE'});				
						$(ed.target).combobox('setValue', '-1');		
			    	}
				},
		        
				columns:[[ 
					{field:'DIM_VALUE', title:'维值代码', width:50, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', title:'维值名称', width:50, editor: {type:'validatebox', options:{required:true}}}, 
					{field:'ACCOUNT_TYPE',title:'科目属性',width:50,
						formatter:function(value){
							for(var i=0; i<acctTypeList.length; i++){
								if (acctTypeList[i].acctTypeId == value) 
									return acctTypeList[i].acctTypeName;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'acctTypeId',
								textField:'acctTypeName',
								data:acctTypeList,
								required:true
							}
						}
					},
					{field:'DIM_LEVEL',title:'维值层次',width:50,
						formatter:function(value){
							for(var i=0; i<dimLevelList.length; i++){
								if (dimLevelList[i].dimLevId == value) 
									return dimLevelList[i].dimLevName;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'dimLevId',
								textField:'dimLevName',
								data:dimLevelList
							}
						}
					},
					{field:'SUMMARY_FLAG', title:'是否汇总', width:50,align:'center',
						formatter:function(value){
							if (value == 'Y') {
								return '是';
							} else {
								return '否';
							}
						},
						editor:{
							type:'checkbox',
							options:{
								on: 'Y',
								off: 'N'
							}
						}
					},
					{field:'ATTRIBUTE1', title:'辅助段1', width:50, editor:"text"}, 
					{field:'ATTRIBUTE2', title:'辅助段2', width:50, editor:"text"}, 
					{field:'ATTRIBUTE3', title:'辅助段3', width:50, editor:"text"}, 
					{field:'ATTRIBUTE4', title:'辅助段4', width:50, editor:"text"}, 
					{field:'ATTRIBUTE5', title:'辅助段5', width:50, editor:"text"}, 
					{field:'ATTRIBUTE6', title:'辅助段6', width:50, editor:"text"}, 
					{field:'ATTRIBUTE7', title:'辅助段7', width:50, editor:"text"}, 
					{field:'ATTRIBUTE8', title:'辅助段8', width:50, editor:"text"}, 
					{field:'ATTRIBUTE9', title:'辅助段9', width:50, editor:"text"}, 
					{field:'ATTRIBUTE10', title:'辅助段10', width:50, editor:"text"}, 
					
					{field:'DIM_VALUE_ID', title:'维值ID', width:50, hidden: true},
					{field:'DIMENSION_ID', title:'维度HeaderID', width:50, hidden: true},
					
					{field:'action',title:'设置子值范围',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.DIMENSION_ID){
								if (row.SUMMARY_FLAG=="Y"){
									
								  var s = '<a href="#" onclick="inputSonDimValue(\'' + row.DIM_VALUE_ID + '\',\''    + row.DESCRIPTION + '\',\''+ row.DIMENSION_ID   +     '\')">子值范围</a> ';
						
								  return s;
								} 
							}
						}
					}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/dimension/saveDimensionValue.do?',
				updateUrl: '/hrs/dimension/updateDimensionValue.do?',
				destroyUrl: '/hrs/dimension/deleteDimensionValue.do?'
	    	});
	    });
	            
        function inputSonDimValue(headDim, headDimName,superHeadDim) {
        	var targetUrl = '/hrs/dimension/showDimensionSonValueSubList.do' + '?headDim=' + headDim+'&superHeadDim=' + superHeadDim;
        	var tabName = headDimName + '-子值范围';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	dimValueCode: $('#dimValueCode').val(),
    	    	dimValueName: $('#dimValueName').val()
    	    });
        }
    </script>

</head>
<body>
    <form:form id="dimensionValueForm" commandName="dimensionBean" action="" method="post">
        <form:hidden id="dimenId" path="dimensionId" style="width:114px;"/>
        <form:hidden id="dimenName" path="dimensionName" style="width:114px;"/>
        <form:hidden id="isFinFlag" path="finAccountFlag" style="width:114px;"/>
        
        <div id="tb" style="padding:3px">
	        <span>维值代码:</span>
	        <input id="dimValueCode" name="dimValueCode" class="easyui-textbox" type="text" >
	        <span>维值名称:</span>
	        <input id="dimValueName" name="dimValueName" class="easyui-textbox" type="text" >
	        <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
        </div>
        <div style="margin-bottom:10px" id="">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
	    <table id="tableview"></table>
	    <div style="margin-bottom:10px" id="bottomToolBar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
    </form:form>
</body>
</html>