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
			    {signId:'+',signName:'+'},
			    {signId:'-',signName:'-'}
			];
			
		    //get title
		    var tabTile = $('#itemDescription').val() + '-账户配置';
		    var itemHeaderId = $('#itemHeaderId').val();
		    var itemCode = $('#itemCode').val();
		    var dataUrl = '/hrs/rptSettings/getItemContentConfigList.do?itemHeaderId=' + itemHeaderId + '&itemCode=' + itemCode;
		    
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
				idField:'ITEM_CONTENT_ID',
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
					{field:'ITEM_CONTENT_ID', title:'账户ID', width:50, hidden:true },
					{field:'ITEM_HEADER_ID', title:'表项头ID', width:50, hidden:true }, 
					{field:'ITEM_CODE', title:'表项头code', width:50, hidden:true }, 
					{field:'LINE_NUM', title:'序号', width:220,halign: 'center', editor: {type:'validatebox', options:{required:true,validType:'number'}}}, 
					{field:'SIGN',title:'符号',halign: 'center',width:220,
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
					{field:'CONTENT_LOW', title:'账户下限',halign: 'center', width:220 }, 
					{field:'CONTENT_HIGH', title:'账户上限',halign: 'center', width:220 }, 
					
					{field:'actionLow',title:'账户上下限',width:220,align:'center',
						formatter:function(value,row,index) {
							if (row.ITEM_CONTENT_ID){
								var s = '<a href="#" onclick="configLowHighValues(' + row.ITEM_CONTENT_ID + ',\''  + row.ITEM_CODE + '\')">上下限配置</a> ';
								return s;
							}
						}
					}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveContentHeader.do?',
				updateUrl: '/hrs/rptSettings/updateContentHeader.do?',
				destroyUrl: '/hrs/rptSettings/deleteContentHeader.do?'
	    	});
	    });
	    
        function configLowHighValues(target, itemCode) {
        	var targetUrl = '/hrs/rptSettings/showContentLowHighView.do?itemContentId=' + target;
        	var tabName = itemCode + '-上下限配置';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

</head>
<body>
    <form:form id="itemHeaderForm" commandName="itemHeaderBean" action="" method="post">
        <form:hidden id="itemHeaderId" path="itemHeaderId" style="width:114px;"/>
        <form:hidden id="itemCode" path="itemCode" style="width:114px;"/>
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