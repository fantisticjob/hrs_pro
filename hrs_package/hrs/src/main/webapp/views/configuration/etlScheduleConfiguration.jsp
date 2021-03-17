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
	var lastIndex;
	var updateFlag = false; //判断按保存按钮时更新标志位

	var htmlObj = $.ajax({ //获取quartz调度类型，数据回显时使用
		url : "../../resources/js/quartzStatus.json",
		type : "post",
		async : false,
		data : {
			id : text
		}
	});
	var text = htmlObj.responseText;
	var quartzStatus = jQuery.parseJSON(text);


	var htmlObj = $.ajax({ //远程数据库获取执行类型  每天、每时等
		url : "/hrs/etlProcessing/getEtlScheduleTypeList.do?",
		type : "post",
		async : false,
		data : {
			id : text
		}
	});
	var scheduleText = htmlObj.responseText;
	var type = jQuery.parseJSON(scheduleText);
	var scheduleType = eval(type)


	var concurrent = [ //是否并行data
		{
			id : '1',
			text : 'Y'
		},
		{
			id : '2',
			text : 'N'
		}
	];
	var productsAttrHour = [ { //执行区间时
		id : '0',
		text : '00分'
	},
		{
			id : '1',
			text : '01分'
		}, {
			id : '2',
			text : '02分'
		}, {
			id : '3',
			text : '03分'
		}, {
			id : '4',
			text : '04分'
		},
		{
			id : '5',
			text : '05分'
		}, {
			id : '6',
			text : '06分'
		}, {
			id : '7',
			text : '07分'
		}, {
			id : '8',
			text : '08分'
		},
		{
			id : '9',
			text : '09分'
		}, {
			id : '10',
			text : '10分'
		}, {
			id : '11',
			text : '11分'
		}, {
			id : '12',
			text : '12分'
		},
		{
			id : '13',
			text : '13分'
		}, {
			id : '14',
			text : '14分'
		}, {
			id : '15',
			text : '15分'
		}, {
			id : '16',
			text : '16分'
		},
		{
			id : '17',
			text : '17分'
		}, {
			id : '18',
			text : '18分'
		}, {
			id : '19',
			text : '19分'
		}, {
			id : '20',
			text : '20分'
		},
		{
			id : '21',
			text : '21分'
		}, {
			id : '22',
			text : '22分'
		}, {
			id : '23',
			text : '23分'
		}, {
			id : '24',
			text : '24分'
		},
		{
			id : '25',
			text : '25分'
		}, {
			id : '26',
			text : '26分'
		}, {
			id : '27',
			text : '27分'
		}, {
			id : '28',
			text : '28分'
		},
		{
			id : '29',
			text : '29分'
		}, {
			id : '30',
			text : '30分'
		}, {
			id : '31',
			text : '31分'
		}, {
			id : '32',
			text : '32分'
		},
		{
			id : '33',
			text : '33分'
		}, {
			id : '34',
			text : '34分'
		}, {
			id : '35',
			text : '35分'
		}, {
			id : '36',
			text : '36分'
		},
		{
			id : '37',
			text : '37分'
		}, {
			id : '38',
			text : '38分'
		}, {
			id : '39',
			text : '39分'
		}, {
			id : '40',
			text : '40分'
		},
		{
			id : '41',
			text : '41分'
		}, {
			id : '42',
			text : '42分'
		}, {
			id : '43',
			text : '43分'
		}, {
			id : '44',
			text : '44分'
		},
		{
			id : '45',
			text : '45分'
		}, {
			id : '46',
			text : '46分'
		}, {
			id : '47',
			text : '47分'
		}, {
			id : '48',
			text : '48分'
		},
		{
			id : '49',
			text : '49分'
		}, {
			id : '50',
			text : '50分'
		}, {
			id : '51',
			text : '51分'
		}, {
			id : '52',
			text : '52分'
		}, {
			id : '53',
			text : '53分'
		}, {
			id : '54',
			text : '54分'
		},
		{
			id : '55',
			text : '55分'
		}, {
			id : '56',
			text : '56分'
		}, {
			id : '57',
			text : '57分'
		}, {
			id : '58',
			text : '58分'
		},
		{
			id : '59',
			text : '59分'
		}
	];
	var productsAttrDay = [ //执行区间天
		{
			id : '1',
			text : '01：00'
		},
		{
			id : '2',
			text : '02：00'
		},
		{
			id : '3',
			text : '03：00'
		},
		{
			id : '4',
			text : '04：00'
		},
		{
			id : '5',
			text : '05：00'
		},
		{
			id : '6',
			text : '06：00'
		},
		{
			id : '7',
			text : '07：00'
		},
		{
			id : '8',
			text : '08：00'
		},
		{
			id : '9',
			text : '09：00'
		},
		{
			id : '10',
			text : '10：00'
		},
		{
			id : '11',
			text : '11：00'
		},
		{
			id : '12',
			text : '12：00'
		},
		{
			id : '13',
			text : '13：00'
		},
		{
			id : '14',
			text : '14：00'
		},
		{
			id : '15',
			text : '15：00'
		},
		{
			id : '16',
			text : '16：00'
		},
		{
			id : '17',
			text : '17：00'
		},
		{
			id : '18',
			text : '18：00'
		},
		{
			id : '19',
			text : '19：00'
		},
		{
			id : '20',
			text : '20：00'
		},
		{
			id : '21',
			text : '21：00'
		},
		{
			id : '22',
			text : '22：00'
		},
		{
			id : '23',
			text : '23：00'
		},
		{
			id : '24',
			text : '24：00'
		}
	];
	var productsAttrWeek = [ //执行区间星期
		{
			id : '1',
			text : '星期一'
		},
		{
			id : '2',
			text : '星期二'
		},
		{
			id : '3',
			text : '星期三'
		},
		{
			id : '4',
			text : '星期四'
		},
		{
			id : '5',
			text : '星期五'
		},
		{
			id : '6',
			text : '星期六'
		},
		{
			id : '7',
			text : '星期日'
		}
	];
	var productsAttrMonth = [ //执行区间月
		{
			id : '1',
			text : '01号'
		}, {
			id : '2',
			text : '02号'
		}, {
			id : '3',
			text : '03号'
		}, {
			id : '4',
			text : '04号'
		},
		{
			id : '5',
			text : '05号'
		}, {
			id : '6',
			text : '06号'
		}, {
			id : '7',
			text : '07号'
		}, {
			id : '8',
			text : '08号'
		},
		{
			id : '9',
			text : '09号'
		}, {
			id : '10',
			text : '10号'
		}, {
			id : '11',
			text : '11号'
		}, {
			id : '12',
			text : '12号'
		},
		{
			id : '13',
			text : '13号'
		}, {
			id : '14',
			text : '14号'
		}, {
			id : '15',
			text : '15号'
		}, {
			id : '16',
			text : '16号'
		},
		{
			id : '17',
			text : '17号'
		}, {
			id : '18',
			text : '18号'
		}, {
			id : '19',
			text : '19号'
		}, {
			id : '20',
			text : '20号'
		},
		{
			id : '21',
			text : '21号'
		}, {
			id : '22',
			text : '22号'
		}, {
			id : '23',
			text : '23号'
		}, {
			id : '24',
			text : '24号'
		},
		{
			id : '25',
			text : '25号'
		}, {
			id : '26',
			text : '26号'
		}, {
			id : '27',
			text : '27号'
		}, {
			id : '28',
			text : '28号'
		},
		{
			id : '29',
			text : '29号(当月没有默认最后一天)'
		}, {
			id : '30',
			text : '30号(当月没有默认最后一天)'
		}, {
			id : '31',
			text : '31号(当月没有默认最后一天)'
		}
	];
	var productsAttrYear = [ //执行区间年   暂时不用
		{
			id : '1',
			text : '01月'
		},
		{
			id : '2',
			text : '02月'
		},
		{
			id : '3',
			text : '03月'
		},
		{
			id : '4',
			text : '04月'
		},
		{
			id : '5',
			text : '05月'
		},
		{
			id : '6',
			text : '06月'
		},
		{
			id : '7',
			text : '07月'
		},
		{
			id : '8',
			text : '08月'
		},
		{
			id : '9',
			text : '09月'
		},
		{
			id : '10',
			text : '10月'
		},
		{
			id : '11',
			text : '11月'
		},
		{
			id : '12',
			text : '12月'
		}
	];
	/* * “动态下拉框”改变的时候触发
	 * @param newValue   改变后动态下拉框的数据ID
	 * @param oldValue   改变前动态下拉框的数据ID
	  */
	function onChangeHandeler(newValue, oldValue) {
		//初次点击时候，oldValue为空，直接返回
		if (oldValue == '') {
			return;
		}
		//获取“动态下拉框”改变前的行数据
		var selrow = $('#tableview').datagrid('getSelected');
		//行的索引
		var selrowindex = $('#tableview').datagrid('getRowIndex', selrow);

		$('#tableview').datagrid('endEdit', selrowindex);
		//非初次点击，直接清空动态编辑器框中的值
		selrow.VALUE_PROCESS = '';
		$('#tableview').datagrid('beginEdit', selrowindex);
	}
	function addRow() { //新增行
		if (lastIndex == undefined) {
			updateFlag = false;
			$('#tableview').datagrid('endEdit', lastIndex);
			$('#tableview').datagrid('appendRow', {
				//新增一行的数据
				JOB_ID : '请选择',
				TYPE_PROCESS : '1',
				VALUE_PROCESS : '1',
				CONCURRENT : '1'
			});
			lastIndex = $('#tableview').datagrid('getRows').length - 1;
			$('#tableview').datagrid('selectRow', lastIndex);
			$('#tableview').datagrid('beginEdit', lastIndex);
		}
	}
	function DelData() { //删除行
		var selrow = $('#tableview').datagrid('getSelected');
		if (selrow == null) {
			$.messager.show({
				title : "Warning",
				msg : "没有选中任何记录.",
				showType : 'slide',
				timeout : 2000
			});
		} else {
			$.messager.confirm('Confirm', '你是否要删除当前记录?', function(r) {
				if (r) {
					$.post('/hrs/etlProcessing/deleteEtlSchedule.do?', {
						"id" : selrow.SCHEDULE_ID
					},
						function(data) {
							if (data != null) {
								$("#tableview").datagrid("reload");
								lastIndex = undefined;
							} else {
								$.messager.alert('Error', "删除错误，请重新操作！", 'error')
							}
							;
						});
				}
			});
		}
	}
	function SaveData() { //保存、更新行
		if (updateFlag) {
			href = "/hrs/etlProcessing/updateEtlSchedule.do?";
		} else {
			href = "/hrs/etlProcessing/saveEtlSchedule.do?";
		}
		var selrow = $('#tableview').datagrid('getSelected');
		selrow.JOB_NAME = " ";
		var selrowindex = $('#tableview').datagrid('getRowIndex', selrow);
		$('#tableview').datagrid('endEdit', selrowindex);
		var jobId = selrow.JOB_ID;
		if (!isNaN(jobId)) {
			if (selrow.VALUE_PROCESS != "") {
				$.post(href, {
					"JOB_ID" : selrow.JOB_ID,
					"CONCURRENT" : selrow.CONCURRENT,
					"JOB_NAME" : selrow.JOB_NAME,
					"TYPE_PROCESS" : selrow.TYPE_PROCESS,
					"VALUE_PROCESS" : selrow.VALUE_PROCESS,
					"SCHEDULE_ID" : selrow.SCHEDULE_ID == undefined ? 1010 : selrow.SCHEDULE_ID
				},
					function(data) {
						if (data != null) {
							$("#tableview").datagrid("reload");
							lastIndex = undefined;
						} else {
							$.messager.alert('Error', "增加错误，请重新操作！", 'error')
						}
						;
					});
			}else{
				$.messager.alert('操作提示', "请选择执行区间！", 'info');
			}
		} else {
			$.messager.alert('操作提示', "请选择ETL任务名称！", 'info');
			$("#tableview").datagrid('rejectChanges');
			addRow();
		}

	}
	function concurrentFormatter(value, row, index) {
		lastIndex = undefined;
		if (value == '1') {
			var s = 'Y';
		} else {
			var s = 'N';
		}
		return s;
	}
	function CancelData() { //取消键
		$("#tableview").datagrid('rejectChanges');
		lastIndex = undefined;
	/* var selrow = $('#tableview').datagrid('getSelected');
	var selrowindex = $('#tableview').datagrid('getRowIndex', selrow);
	$('#tableview').datagrid('endEdit', selrowindex);	 */
	}
	function doProcess(jobId, scheduleId) { //调度运行键
		$.ajaxSettings.async = false;
		$.post(
			"/hrs/etlProcessing/processEtlJob.do?",
			{
				"jobId" : jobId,
				"scheduleId" : scheduleId
			},
			function(result) {
				$("#tableview").datagrid("reload");
			});
	}
	function removeSchedule(scheduleId) { //移除调度键
		$.ajaxSettings.async = false;
		$.post(
			"/hrs/etlProcessing/removeSchedule.do?",
			{
				"scheduleId" : scheduleId
			},
			function(result) {
				$("#tableview").datagrid("reload");
			});
	}
	function runJob(jobId) { //直接运行键
		var postData = "jobId=" + jobId;
		$.ajax({
			url : '/hrs/etlProcessing/processEtlJob.do?',
			type : 'POST',
			async : true,
			data : postData,
			dataType : 'json',
			success : function(data) {
				$("#tableview").datagrid("reload");
			}
		});
	}

	function productsAttrFormatter(value, row, index) {
		//判断“动态下拉框”为引用属性的时候，把value替换成 productsAttr中对应的值；
		if (row.TYPE_PROCESS == 1) {
			for (var i = 0; i < productsAttrHour.length; i++) {
				if (productsAttrHour[i].id == value)
					return productsAttrHour[i].text;
			}
		}
		if (row.TYPE_PROCESS == 2) {
			for (var i = 0; i < productsAttrDay.length; i++) {
				if (productsAttrDay[i].id == value)
					return productsAttrDay[i].text;
			}
		}
		if (row.TYPE_PROCESS == 3) {
			for (var i = 0; i < productsAttrWeek.length; i++) {
				if (productsAttrWeek[i].id == value)
					return productsAttrWeek[i].text;
			}
		}
		if (row.TYPE_PROCESS == 4) {
			for (var i = 0; i < productsAttrMonth.length; i++) {
				if (productsAttrMonth[i].id == value)
					return productsAttrMonth[i].text;
			}
		}
		if (row.TYPE_PROCESS == 5) {
			for (var i = 0; i < productsAttrYear.length; i++) {
				if (productsAttrYear[i].id == value)
					return productsAttrYear[i].text;
			}
		}
		return value;
	}
	function onBeforeEditHandeler(rowIndex, rowData) {
		var coll = $(this).datagrid('getColumnOption', 'JOB_ID');
		if (updateFlag) {
			coll.editor.options = {
				valueField : 'JOB_ID',
				textField : 'JOB_NAME',
				method : 'post',
				url : '/hrs/etlProcessing/getEtlJobNameList.do?',
				readonly : true
			}
		} else {
			coll.editor.options = {
				valueField : 'JOB_ID',
				textField : 'JOB_NAME',
				method : 'post',
				url : '/hrs/etlProcessing/getEtlJobNameList.do?',
				required : true,
				panelHeight : 0,
				readonly : false
			}
		}
		var col = $(this).datagrid('getColumnOption', 'VALUE_PROCESS');
		// “动态下拉框”的类型
		var type = $('#tableview').datagrid('getSelected').TYPE_PROCESS;

		if (type == '1') {
			col.editor.type = 'combobox';
			col.editor.options = {
				valueField : 'id',
				textField : 'text',
				data : productsAttrHour,
				panelHeight : 200
			}
		} else if (type == '2') {
			col.editor.type = 'combobox';
			col.editor.options = {
				valueField : 'id',
				textField : 'text',
				data : productsAttrDay,
				panelHeight : 200
			}
		} else if (type == '3') {
			col.editor.type = 'combobox';
			col.editor.options = {
				valueField : 'id',
				textField : 'text',
				data : productsAttrWeek,
				panelHeight : 200
			}
		} else if (type == '4') {
			col.editor.type = 'combobox';
			col.editor.options = {
				valueField : 'id',
				textField : 'text',
				data : productsAttrMonth,
				panelHeight : 200
			}
		} else if (type == '5') {
			col.editor.type = 'datetimebox';
		}

	}
	$(function() {
		$('#tableview').datagrid({
			onBeforeEdit : onBeforeEditHandeler,
			title : 'ELT任务调度日期设置',
			//nowrap : false,
			rownumbers : true,
			animate : true,
			collapsible : true,
			loadMsg : '数据正在加载,请耐心的等待...',
			fitColumns : true,
			singleSelect : true,
			idField : 'JOB_ID',
			autoSizeColumn : true,
			autoSave : false,
			pagination : "true",
			url : '/hrs/etlProcessing/getEtlScheduleList.do?',
			onDblClickRow : function(rowIndex, row) {
				if (lastIndex != rowIndex) {
					updateFlag = true;
					$('#tableview').datagrid('endEdit', lastIndex);
					$('#tableview').datagrid('beginEdit', rowIndex);
				}
				lastIndex = rowIndex;
			},
			columns : [ [
				{
					field : 'SCHEDULE_ID',
					title : '',
					width : 10,
					hidden : true
				},
				{
					field : 'JOB_ID',
					title : 'ETL任务名称',
					width : 150,
					editor : {
						type : 'combobox',
						options : {
							valueField : 'JOB_ID',
							textField : 'JOB_NAME',
							panelHeight : 100,
							method : 'post',
							url : '/hrs/etlProcessing/getEtlJobNameList.do?',
							required : true
						}
					},
					formatter : function(value, row, index) {
						if (value != "" || value != null) {
							var s = row.JOB_NAME;
						}
						readonly:true;
						return s;
					},
					align : 'center'
				},
				{
					field : 'JOB_NAME',
					title : '',
					width : 50,
					hidden : true
				},
				{
					field : 'TYPE_PROCESS',
					title : '执行类型',
					width : 180,
					editor : {
						type : 'combobox',
						options : {
							valueField : 'SCHEDULE_TYPE_ID',
							textField : 'SCHEDULE_TYPE',
							panelHeight : 200,
							method : 'post',
							url : '/hrs/etlProcessing/getEtlScheduleTypeList.do?',
							options : {
								required : true
							},
							onChange : onChangeHandeler
						}
					},
					align : 'center',
					formatter : function(value, rowData, index) {
						//json变量现在就是一个数组对象，直接读取每个数组对象。结合属性进行输出 
						for (var i = 0; i < scheduleType.length; i++) {
							if (scheduleType[i].SCHEDULE_TYPE_ID == value)
								return scheduleType[i].SCHEDULE_TYPE;
						}
						return value;
					}
				},
				{
					field : 'VALUE_PROCESS',
					title : '执行区间',
					width : 150,
					formatter : productsAttrFormatter,
					editor : {
						type : 'text'
					},
					align : 'center'
				},
				{
					field : 'CONCURRENT',
					title : '并发执行',
					width : 80,
					align : 'center',
					formatter : concurrentFormatter,
					editor : {
						type : 'combobox',
						options : {
							valueField : 'id',
							textField : 'text',
							data : concurrent,
							options : {
								required : true
							}
						}
					}
				},
				{
					field : 'STATUS',
					title : '调度状态',
					width : 80,
					align : 'center',
					formatter : function(value, row, index) {
						for (var i = 0; i < quartzStatus.length; i++) {
							if (quartzStatus[i].id == value)
								return quartzStatus[i].text;
						}
						return value;
					}
				},
				{
					field : 'action',
					title : '操作',
					width : 300,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.JOB_ID) {
							var s = '<a href="#" onclick="doProcess(' + row.JOB_ID + ',' + row.SCHEDULE_ID + ')">调度运行</a><a style="margin-left:40" href="#" onclick="removeSchedule(' + row.SCHEDULE_ID + ')">移除调度</a> <a style="margin-left:40" href="#" onclick="runJob(' + row.JOB_ID + ')">直接运行</a> ';
							return s;
						}
					}
				}
			] ],
			onError : function(index, row) {
				alert(row.msg);
			}
		});
		$.parser.parse();
	});
</script>
<script type="text/javascript">
	function doSearch() { //按状态、job名查询
		$('#tableview').edatagrid('load', {
			etlJobName : $('#etlJobName').val(),
			etlJobStatus : $('#etlJobStatus').combobox('getValue')
		});
	}
	function doAllSchedule() { //执行所有schedule
		$.ajax({
			url : '/hrs/etlProcessing/getEtlScheduleList.do?',
			type : 'POST',
			async : true,
			dataType : 'json',
			success : function(data) {
				var json = eval(data.rows);
				//json变量现在就是一个数组对象，直接读取每个数组对象。结合属性进行输出 
				for (var i = 0; i < json.length; i++) {
					$.ajaxSettings.async = false;
					$.post(
						"/hrs/etlProcessing/processEtlJob.do?",
						{
							"jobId" : json[i].JOB_ID,
							"scheduleId" : json[i].SCHEDULE_ID
						},
						function(result) {});
				}
				//currentTabRefresh();
				$("#tableview").datagrid("reload");
			}
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
		    url: '/hrs/etlProcessing/getEtlScheduleStatusList.do'">
		<a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" onclick="doAllSchedule()">开启全部调度</a>
	</div>
	<div style="margin-bottom:10px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="addRow()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="SaveData()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true" onclick="DelData()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true" onclick="CancelData()">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true"
			onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview" style="width:'100%';height:'100%'"></table>
	<div style="margin-bottom:10px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="addRow()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="SaveData()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true" onclick="DelData()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true" onclick="CancelData()">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload',plain:true"
			onclick="currentTabRefresh()">刷新</a>
	</div>

</body>
</html>