<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
<jsp:include page="/views/common/meta.jsp"></jsp:include>
<jsp:include page="/views/common/easyui.jsp"></jsp:include>
<title>维度设置</title>
<script type="text/javascript">
	$(function() {
		var status = '{}';
		$.ajax({
			url : '/hrs/etlProcessing/getEtlScheduleStatusList.do?',
			type : 'POST',
			async : false,
			dataType : 'json',
			success : function(data) {
				status = data;
			}
		});
		$('#tableview').edatagrid({
			title : 'ELT任务管理',
			width : "100%",
			height : "auto",
			scrolling : true,
			nowrap : false,
			rownumbers : true,
			animate : true,
			collapsible : true,
			fitColumns : false,
			singleSelect : true,
			idField : 'JOB_ID',
			autoSizeColumn : true,
			autoSave : false,
			pagination : "true",
			url : '/hrs/etlProcessing/getEtlJobList.do?',
			destroyMsg : {
				norecord : { // when no record is selected
					title : 'Warning',
					msg : '没有选中任何记录.'
				},
				confirm : { // when select a row
					title : 'Confirm',
					msg : '你是否要删除当前记录(对应调度日期同样会被删掉)?'
				}
			},
			frozenColumns : [ [ {
				field : 'JOB_ID',
				title : '',
				width : 10,
				hidden : true
			}, {
				field : 'ORDER_ID',
				title : '运行次序',
				width : 55,
				editor : {
					type : 'combogrid',
					options : {
						required : true
					}
				},
				align : 'center'
			},
				{
					field : 'JOB_NAME',
					title : 'ETL任务名称',
					width : 130,
					editor : {
						type : 'validatebox',
						options : {
							required : true,
						}
					},
					align : 'center'
				},
			] ],
			columns : [ [
				{
					field : 'FILE_PATH',
					title : '任务文件路径',
					width : 240,
					halign : 'center',
					editor : {
						type : 'validatebox',
						options : {
							required : true
						}
					}
				},
				{
					field : 'PARAM_LIST',
					title : '参数列表',
					width : 140,
					editor : 'text',
					halign : 'center'
				},
				{
					field : 'DESCRIPTION',
					title : '任务描述',
					width : 80,
					halign : 'center',
					editor : 'text'
				},
				{
					field : 'START_TIME',
					title : '开始时间',
					halign : 'center',
					width : 130
				},
				{
					field : 'END_TIME',
					title : '结束时间',
					halign : 'center',
					width : 130
				},
				{
					field : 'ELAPSED_TIME',
					title : '花费时间',
					width : 80,
					align : 'center',
					formatter : function(value, row, index) {
						if (value == 0) {
							value = '';
						}
						return value;
					}
				},
				{
					field : 'STATUS',
					title : '状态',
					width : 80,
					formatter : function(value, row, index) {
						for (var i = 0; i < status.length; i++) {
							if (status[i].STATUS_ID == value)
								return status[i].STATUS_DESCRIPTION;
						}
						return value;
					},
					align : 'center'
				},
				{
					field : 'MESSAGE',
					title : '信息',
					halign : 'center',
					width : 250
				},
				{
					field : 'action',
					title : '操作',
					width : 80,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.JOB_ID) {
							var s = '<a href="#" onclick="runJob(' + row.JOB_ID + ')">运行</a> ';
							return s;
						}
					}
				}
			] ],
			onAdd : function(index, row) {
				var getJobLIst = '{}';
				$.ajax({
					url : '/hrs/etlProcessing/getAllEtlJobList.do?',
					type : 'POST',
					async : false,
					dataType : 'json',
					success : function(data) {
						getJobLIst = data;
					}
				});
				var length=getJobLIst.length;
				var codess = [];
				for (var i = 1; i <= length+1; i++) {
					codess.push({code : i});
				}
				var ed = $('#tableview').edatagrid('getEditor', {
					index : index,
					field : 'ORDER_ID'
				});
				if (ed) {
					//$(ed.target).combogrid('setValues', ['001','007']);
					$(ed.target).combogrid({
						panelWidth : 50,
						value : length+1,
						idField : 'code',
						columns : [ [
							{
								field : 'code',
								title : 'Code',
								width : 60
							}
						] ],
						data : codess,
						fitColumns : true
					});
				}
			},
			onError : function(index, row) {
				alert(row.msg);
			},
			saveUrl : '/hrs/etlProcessing/saveEtlJob.do?',
			updateUrl : '/hrs/etlProcessing/updateEtlJob.do?',
			destroyUrl : '/hrs/etlProcessing/deleteEtlJob.do?'
		});
	});
	function runJob(jobId) {
		var postData = "jobId=" + jobId;
		$.ajax({
			url : '/hrs/etlProcessing/processEtlJob.do?',
			type : 'POST',
			async : true,
			data : postData,
			dataType : 'json',
			success : function(data) {
				//currentTabRefresh();
				if (data[0].status == "4" || data[0].status == "2") {
					$.messager.alert("错误", data[0].message, "error");
				}
				;
				$("#tableview").datagrid("reload");
			}
		});
	}
	function doAllJob() { //执行所有job
		$.ajax({
			url : '/hrs/etlProcessing/getAllEtlJobList.do?',
			type : 'POST',
			async : true,
			dataType : 'json',
			success : function(json) {
				//json变量现在就是一个数组对象，直接读取每个数组对象。结合属性进行输出 
				for (var i = 0; i < json.length; i++) {
					$.ajaxSettings.async = false;
					$.post(
						"/hrs/etlProcessing/processEtlJob.do?",
						{
							"jobId" : json[i].JOB_ID
						},
						function(data) {
							if (data[0].status == "4" || data[0].status == "2") {
								$.messager.alert("错误", data[0].message, "error");
								$("#tableview").datagrid("reload");
							}
						});
				}
				//currentTabRefresh();
				$("#tableview").datagrid("reload");
			}
		});
	}
</script>
<script type="text/javascript">
	function doSearch() {
		$('#tableview').edatagrid('load', {
			etlJobName : $('#etlJobName').val(),
			etlJobStatus : $('#etlJobStatus').combobox('getValue')
		});
	}
</script>

</head>
<body>
	<div id="tb" style="padding:3px">
		<span>ETL任务名称:</span> <input id="etlJobName" name="etlJobName"
			class="easyui-textbox" type="text"> <span>ETL任务状态:</span> <input
			id="etlJobStatus" name="etlJobStatus" class="easyui-combobox"
			data-options="
		    valueField: 'STATUS_ID',
		    textField: 'STATUS_DESCRIPTION',
		    url: '/hrs/etlProcessing/getEtlScheduleStatusList.do?'">
		<a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" onclick="doAllJob()">ETL一键运行</a>
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