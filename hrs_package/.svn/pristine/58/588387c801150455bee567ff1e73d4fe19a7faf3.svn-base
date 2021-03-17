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
	    
	    	var signList = [
			    {signId:'PLUS',signName:'+'},
			    {signId:'MINUS',signName:'-'},
			    {signId:'ABS',signName:'ABS'}
			];
	     
		    //get title	  
		    var tabTile = $('#RowSetLineName').val() + '-规则';
		    var RowSetLineId = $('#RowSetLineId').val();
		    var dataUrl = '/hrs/rptSettings/getRowCalculationList.do?RowSetLineId=' + RowSetLineId;
		    
		    var RowSetRuleCode = $('#RowSetRuleCode').val();
		    var calItemListUrl = '/hrs/rptSettings/getCalItemList.do?itemRuleCode=' + RowSetRuleCode;
		    var calItemList='{}';
		    $.ajax({    
		        url: calItemListUrl,     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            calItemList = data;  
		        }    
		    });	

	    	$('#tableview').edatagrid({
				title: tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'ROW_CAL_ID',
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
			    /* 	if (data.total==0) {
			    		//alert ($("#bottomToolBar").is(":hidden"));
			    		//$("#bottomToolBar").hide();
			    		//$(this).edatagrid('appendRow', {DIM_VALUE: '<div style="text-align:center;color:red">没有相关记录！</div>' }).edatagrid('mergeCells', {index: 0, field: 'DIM_VALUE', colspan: 9 });  
			    	}  else {
			    		$("#bottomToolBar").show();
			    	} */
		        },
				columns:[[ 
					{field:'ROW_CAL_ID', title:'规则ID', width:50, hidden: true},
					{field:'ROW_ID', title:'行ID', width:50, hidden: true},
					{field:'ROW_CAL_NUM', title:'序号', width:50 ,editor: {type:'validatebox', options:{required:true}}}, 
					{field:'SIGN',title:'运算符',width:50,
						formatter:function(value){
							for(var i=0; i < signList.length; i++){
								if (signList[i].signId == value) 
									return signList[i].signName;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'signId',
								textField:'signName',
								data:signList,
								required:true
							}
						}
					},
					{field:'CAL_ITEM_CODE', title:'计算行项目',width:50,
						formatter:function(value, row, index){
						  	for(var i=0; i < calItemList.length; i++){
							    if (calItemList[i].ITEM_CODE == value) 
								    return calItemList[i].DESCRIPTION;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'ITEM_CODE',
								textField:'DISP_NAME',
								data:calItemList,
								required:true
							}
						}
					}				
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveRowCalculation.do?',
				updateUrl: '/hrs/rptSettings/updateRowCalculation.do?',
				destroyUrl: '/hrs/rptSettings/deleteRowCalculation.do?'
	    	});
	    });
	    
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	CalName: $('#CalName').val()
    	    });
        }
    </script>

</head>
<body>
    <form:form id="loopDimForm" commandName="RowSetLineBean" action="" method="post">
        <form:hidden id="RowSetLineId" path="rowId" style="width:114px;"/>
        <form:hidden id="RowSetLineName" path="rowName" style="width:114px;"/>
        <form:hidden id="RowSetRuleCode" path="ruleCode" style="width:114px;"/>
        
        <div id="tb" style="padding:3px">
	        <span>计算行项目:</span>
	        <input id="CalName" name="CalName" class="easyui-textbox" type="text" >
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