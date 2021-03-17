<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
<jsp:include page="/views/common/meta.jsp"></jsp:include>
<jsp:include page="/views/common/easyui.jsp"></jsp:include>
<title>报表行集定义</title>
<script type="text/javascript">
	$(function() {
		var ruleHeaderList = '{}';
		$.ajax({
			url : '/hrs/rptSettings/getItemLoopRuleListForSet.do?target=rowSetConf',
			type : 'POST',
			async : false,
			dataType : 'json',
			success : function(data) {
				ruleHeaderList = data.rows;
			}
		});
		var codeExtHeaderList = '{}'
		$.ajax({
			url : '/hrs/rptSettings/getItemCodeExtList.do?target=rowSetConf',
			type : 'POST',
			async : false,
			dataType : 'json',
			success : function(data) {
				codeExtHeaderList = data.rows;

			}
		});

		$('#tableview').edatagrid({
			title : '报表行集定义',
			width : "100%",
			height : "auto",
			scrolling : true,
			nowrap : false,
			rownumbers : true,
			animate : true,
			collapsible : true,
			fitColumns : false,
			singleSelect : true,
			idField : 'ROW_SET_ID',
			autoSave : false,
			pagination : "true",
			url : '/hrs/rptSettings/getItemRowSetList.do?',
			destroyMsg : {
				norecord : { // when no record is selected
					title : 'Warning',
					msg : '没有选中任何记录.'
				},
				confirm : { // when select a row
					title : 'Confirm',
					msg : '你是否要删除当前记录?'
				}
			},
			columns : [ [
				{
					field : 'ROW_SET_ID',
					title : '标示',
					width : 50,
					checkbox : true
				},
				{
					field : 'ROW_SET_NAME',
					title : '行集名称',
					width : 220,
					halign : 'center',
					editor : {
						type : 'validatebox',
						options : {
							required : true
						}
					}
				},
				{
					field : 'RULE_CODE',
					title : '循环组',
					width : 220,
					halign : 'center',
					editor : {
						type : 'combobox',
						options : {
							valueField : 'RULE_CODE',
							textField : 'DISP_NAME',
							data : ruleHeaderList,
							required : true /*,
								onChange:function(selectValue){
									var selectRow = $('#tableview').edatagrid('getSelected');
									var dispValue = '';
									for(var i=0; i<ruleHeaderList.length; i++){
										if (ruleHeaderList[i].RULE_CODE == selectValue) 
											dispValue = ruleHeaderList[i].DESCRIPTION;
									} 
								 	$(".datagrid-row-editing td[field=DESCRIPTION]").text(dispValue);	 
								} */
						}
					}
				},

				{
					field : 'EXT_ITEM_TYPE',
					title : '外部代码类',
					width : 220,
					halign : 'center',
					editor : {
						type : 'combobox',
						options : {
							valueField : 'ITEM_TYPE',
							textField : 'DESCRIPTION',
							data : codeExtHeaderList,
							required : false /* ,
							    onChange:function(selectValue){
								    var selectRow = $('#tableview').edatagrid('getSelected');
								    var dispValue = '';
								    for(var i=0; i<codeExtHeaderList.length; i++){
									    if (codeExtHeaderList[i].EXT_ITEM_TYPE == selectValue) 
										    dispValue = codeExtHeaderList[i].DESCRIPTION;
								    } 
								    $(".datagrid-row-editing td[field=DESCRIPTION]").text(dispValue);	  
							    } */
						}
					}
				},
				{
					field : 'DESCRIPTION',
					title : '描述',
					width : 220,
					halign : 'center',
					editor : {
						type : 'validatebox',
						options : {
							required : false
						}
					}
				},
				{
					field : 'action',
					title : '操作',
					width : 220,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.ROW_SET_ID) {
							var s = '<a href="#" onclick="inputRowSetDimension(' + row.ROW_SET_ID + ',\'' + row.ROW_SET_NAME + '\')">报表行定义</a> ';
							return s;
						}
					}
				}
			] ],
			onError : function(index, row) {
				alert(row.msg);
			},
			/* onClickRow : function(rowIndex, rowData){
				  
				  var x = rowData.LOOKUP_TYPE_ID;
				  var targetUrl='/hrs/rptSettings/showLookUpDimensionView?lookUpId='+x;
				  location.href="/hrs/rptSettings/showLookUpDimensionView?lookUpId="+x;
				  var tabName = rowData.DESCRIPTION + '-值集';
				        	addTabInParent(tabName,targetUrl);
				
				},  */
			saveUrl : '/hrs/rptSettings/saveRowSetHeader.do?',
			updateUrl : '/hrs/rptSettings/updateRowSetHeader.do?',
			destroyUrl : '/hrs/rptSettings/deleteRowSetHeader.do?'
		});
	});

	function inputRowSetDimension(target, RowSetName) {
		var targetUrl = '/hrs/rptSettings/showRowSetDimensionView.do' + '?RowSetId=' + target;
		var tabName = RowSetName + '-报表行定义';
		addTabInParent(tabName, targetUrl);
	}
</script>

<script type="text/javascript">
	function doSearch() {
		$('#tableview').edatagrid('load', {
			RowSetCodeOne : $('#RowSetCodeOne').val(),
			RowSetNameOne : $('#RowSetNameOne').val()
		});
		$('#tableview').datagrid('reload');
	}
	function copyTable() {
		var row = $('#tableview').datagrid('getSelected');
		if (row) {
			var postData = "rowSetId=" + row.ROW_SET_ID;
			$.ajax({
				url : '/hrs/rptSettings/copyRowSetById.do?',
				type : 'POST',
				async : true,
				data : postData,
				dataType : 'json',
				success : function(data) {
					//currentTabRefresh();
					if (data[0].flag <1000) {
						$.messager.alert("操作提示", "操作失败，请重新复制！", "error");
					}
					$("#tableview").datagrid("reload");
				}
			});
		} else {
			$.messager.alert("操作提示", "请选中要复制的行集！", "info");
		}
	}
</script>

</head>
<body>
	<div id="tb" style="padding:3px">
		<span>报表行集名称:</span> <input id="RowSetCodeOne" name="RowSetCodeOne"
			class="easyui-textbox" type="text"> <span>说明:</span> <input
			id="RowSetNameOne" name="RowSetNameOne" class="easyui-textbox"
			type="text"> <a href="#" class="easyui-linkbutton"
			onclick="doSearch()">查询</a> <a href="#" style="margin-left: 20px"
			class="easyui-linkbutton" onclick="copyTable()">复制行集</a>
	</div>
	<div style="margin-bottom:10px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true"
			onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true"
			onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true"
			onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true"
			onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true"
			onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true"
			onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true"
			onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true"
			onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true"
			onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true"
			onclick="currentTabRefresh()">刷新</a>
	</div>

</body>
</html>