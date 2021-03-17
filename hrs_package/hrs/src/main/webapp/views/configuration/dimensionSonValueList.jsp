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
		    //get title		 
		    var tabTile = $('#description').val() + '-子值范围';
		    var dimensionId = $('#dimensionId').val();
		    var dimensionValue = $('#dimensionValue').val();
		    var dataUrl = '/hrs/dimension/ findDimensionSonValueList.do?dimensionId=' + dimensionId + '&dimensionValue=' + dimensionValue;
		    
	    	$('#tableview').edatagrid({
				title: tabTile,
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: false,
				singleSelect:true,
				autoSizeColumn:true,
				idField:'HIERARCHY_ID',
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
			   /*  onLoadSuccess: function (data) {
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

				},
				
				onAdd: function(index, row) {
		        	var acctFlag = $('#isFinFlag').val();
		        	if (acctFlag == 'N') {
						var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'ACCOUNT_TYPE'});				
						$(ed.target).combobox('setValue', '-1');		
			    	}
				}, */
		        
				columns:[[ 
					{field:'DIM_VALUE', title:'维值代码', width:50, hidden: true }, 
					
					{field:'CHILD_DIM_VALUE_LOW', title:'子值自',halign: 'center', width:185, editor: {type:'validatebox', options:{required:true}}}, 
					{field:'CHILD_DIM_VALUE_HIGH', title:'子值至',halign: 'center', width:185, editor: {type:'validatebox', options:{required:true}}}, 
					
					/* {field:'ACCOUNT_TYPE',title:'科目属性',width:50,
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
								data:dimLevelList,
								required:true
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
					}, */
					
					{field:'CREATION_DATE', title:'创建时间',halign: 'center', width:185 }, 
					{field:'CREATED_BY', title:'创建用户',halign: 'center', width:185 }, 
					{field:'LAST_UPDATE_DATE', title:'更新时间',halign: 'center', width:185 }, 
					{field:'LAST_UPDATED_BY', title:'更新用户',halign: 'center', width:185 },
					
					
					{field:'HIERARCHY_ID', title:'子值ID', width:50, hidden: true},
					{field:'DIMENSION_ID', title:'维度HeaderID', width:50, hidden: true}
			
					
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/dimension/saveDimensionSonValue.do?',
				updateUrl: '/hrs/dimension/updateDimensionSonValue.do?'
				//,
				//destroyUrl: '/hrs/dimension/deleteDimensionValue'
	    	});
	    	  
	    });
	    
    </script>

    <script type="text/javascript">
/*          function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	dimValueCode: $('#dimSonValueCode').val(),
    	    	dimValueName: $('#dimSonValueName').val()
    	    });
        } 
         */
       
    </script>

</head>
<body>
        <form:form id="dimensionSonValueForm" commandName="dimensionValueBean" action="" method="post">
        <form:hidden id="dimensionId" path="dimensionId" style="width:114px;"/>
        <form:hidden id="dimensionValue" path="dimensionValue" style="width:114px;"/>
        <form:hidden id="description" path="description" style="width:114px;"/>
      
        <div id="tb" style="padding:3px">
	     <!--    <span>子值编码:</span>
	        <input id="dimSonValueCode" name="dimSonValueCode" class="easyui-textbox" type="text" >
	        <span>子值名称:</span>
	        <input id="dimSonValueName" name="dimSonValueName" class="easyui-textbox" type="text" > 
	       <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>  -->
        </div>
        <div style="margin-bottom:10px" id="">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
<!-- 		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> -->
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
	    <table id="tableview"></table>
	    <div style="margin-bottom:10px" id="bottomToolBar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
<!-- 		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> -->
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
    </form:form>
</body>
</html>