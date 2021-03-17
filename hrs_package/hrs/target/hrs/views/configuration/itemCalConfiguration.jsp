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
			    {signId:'ABS',signName:'ABS'},
			    {signId:'+',signName:'+'},
			    {signId:'-',signName:'-'},
			    {signId:'*',signName:'*'},
			    {signId:'/',signName:'/'}
			];
			
		    //get title
		    var tabTile = $('#itemDescription').val() + '-计算配置';
		    var itemHeaderId = $('#itemHeaderId').val();
		    var itemCode = $('#itemCode').val();
		    var itemRuleCode = $('#itemRuleCode').val();
		    var dataUrl = '/hrs/rptSettings/getItemCalConfigList.do?itemHeaderId=' + itemHeaderId + '&itemCode=' + itemCode;
		    var calItemListUrl = '/hrs/rptSettings/getCalItemList.do?itemRuleCode=' + itemRuleCode;
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
				idField:'ITEM_CAL_ID',
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
		        },
				columns:[[ 
					{field:'ITEM_CAL_ID', title:'计算项ID', width:50, hidden:true },
					{field:'ITEM_HEADER_ID', title:'表项头ID', width:50, hidden:true }, 
					{field:'ITEM_CODE', title:'表项头code', width:50, hidden:true }, 
					{field:'LINE_NUM', title:'序号', width:360,halign: 'center', editor: {type:'validatebox', options:{required:true, validType:'number'}}}, 
					{field:'SIGN',title:'符号',halign: 'center',width:360,
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
								required:true,
								onChange: function (newValue, oldValue) {
								    if (newValue) {
								        var row = $('#tableview').datagrid('getSelected');//获取所选行data  
								        var rindex = $('#tableview').datagrid('getRowIndex', row);
								        var calItemCell = $('#tableview').datagrid('getEditor', {index : rindex, field : 'CAL_ITEM_CODE'}); 
								        var constantCell = $('#tableview').datagrid('getEditor', {index : rindex, field : 'CONSTANT'}); 
								        if (calItemCell) {
								            if(newValue == '*' || newValue == '/') {
								                $(calItemCell.target).combobox({disabled: true});
								            } else {
								        	    $(calItemCell.target).combobox({disabled: false});					                            
								            }				            
								        }
								        
								        if (constantCell) {
								        	if(newValue == '+' || newValue == '-') {
								        	    $(constantCell.target).attr("disabled", true);
								                //$(constantCell.target).validatebox('disable');
								            } else {
								                $(constantCell.target).attr("disabled", false);
								        	    //$(constantCell.target).numberbox('enable');
								            }
								            $(constantCell.target).val('');
								           /*  if (row.CONSTANT) {
								                //$(constantCell.target).numberbox('setValue',row.CONSTANT);
								            } */
								            
								        }
								    }

								}
/* 								onLoadSuccess: function () {
                                    var data  = $(this).combobox("getData");
                                    if (data.length > 0) {
                                         //$(this).combobox('select', data[0].signId);
                                         //$(this).combobox('select', data[0].signId);
                                         $(this).combobox('setValue', data[0]['signId']);
                                         $(this).combobox('setText', data[0]['signName']); 
                                    }
                                } */
							}
						}
					},
					{field:'CAL_ITEM_CODE', title:'计算表项',halign: 'center',width:360,
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
								textField:'DESCRIPTION',
								data:calItemList
							}
						}
					},
	                {field:'CONSTANT', title:'常数',halign: 'center', width:360, editor: {type:'validatebox', options:{validType:'number'}}}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				
			    onEdit: function(index, row) {  
				    if (row.SIGN) {
				        var newValue =  row.SIGN;
				        if(newValue == '*' || newValue == '/') {
				            var calItemCell = $('#tableview').datagrid('getEditor', {index : index, field : 'CAL_ITEM_CODE'}); 
				            if (calItemCell) {
				                $(calItemCell.target).combobox({disabled: true});
				            } 
				        } else if (newValue == '+' || newValue == '-') {
				            var constantCell = $('#tableview').datagrid('getEditor', {index : index, field : 'CONSTANT'});
				            if (constantCell) {
				                $(constantCell.target).attr("disabled", true);
						    }
				        }
				    }
				},
				saveUrl: '/hrs/rptSettings/saveItemCalculation.do?',
				updateUrl: '/hrs/rptSettings/updateItemCalculation.do?',
				destroyUrl: '/hrs/rptSettings/deleteItemCalculation.do?'
	    	});
	    });
    </script>

</head>
<body>
    <form:form id="itemHeaderForm" commandName="itemHeaderBean" action="" method="post">
        <form:hidden id="itemHeaderId" path="itemHeaderId" style="width:114px;"/>
        <form:hidden id="itemCode" path="itemCode" style="width:114px;"/>
        <form:hidden id="itemRuleCode" path="ruleCode" style="width:114px;"/>
        <form:hidden id="itemDescription" path="itemDescription" style="width:114px;"/>

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