<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
<jsp:include page="/views/common/meta.jsp"></jsp:include>
<jsp:include page="/views/common/easyui.jsp"></jsp:include>
<title>分摊规则调度运行日期设置</title>
<script type="text/javascript">
	$(function() {
		var typeList = [

			{
				value : '分摊规则组',
				key : 'GROUP'
			},
			{
				value : '动态因子',
				key : 'DYNAMIC'
			},
			{
				value : '静态因子',
				key : 'STATIC'
			},
			{
				value : '常数',
				key : 'CONSTANT'
			}
		];
		var quartzStatus = '{}';
		$.ajax({
			url : '../../hrs/resources/js/quartzStatus.json',
			type : 'POST',
			async : false,
			dataType : 'json',
			success : function(data) {
				quartzStatus = data;
			}
		});

		var scheduleType = '{}';
		$.ajax({
			url : '/hrs/etlProcessing/getEtlScheduleTypeList.do?',
			type : 'POST',
			async : false,
			dataType : 'json',
			success : function(data) {
				scheduleType = data;
			}
		});

		//  var editing ; //判断用户是否处于编辑状态   
		// var flag  ;   //判断新增和修改方法 
		$('#tableview').datagrid({
			onBeforeEdit : onBeforeEditHandeler,
			title : '分摊规则调度运行日期设置',
			nowrap : false,
			rownumbers : true,
			animate : true,
			collapsible : true,
			fitColumns : true,
			singleSelect : true,
			idField : 'INSTANCE_ID',
			autoSizeColumn : true,
			autoSave : false,
			pagination : "true",
			url : '/hrs/dataProcessing/getAllocScheduletInstanceList.do?',
			onDblClickRow : function(rowIndex, row) {
				if (lastIndex != rowIndex) {
					updateFlag = true;
					$('#tableview').datagrid('endEdit', lastIndex);
					$('#tableview').datagrid('beginEdit', rowIndex);
				}
				lastIndex = rowIndex;
			},
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
					field : 'SCHEDULE_ID',
					title : '调度ID',
					width : 50,
					hidden : true
				},
				{
					field : 'INSTANCE_ID',
					title : '任务ID',
					width : 20,
					editor : {
						type : 'combobox',
						options : {
							valueField : 'INSTANCE_ID',
							textField : 'INSTANCE_ID',
							panelHeight : 200,
							method : 'post',
							url : '/hrs/dataProcessing/getAllocScheduleInstanceList.do?',
							required : true
						}
					},
					align : 'center'
				},
				{
					field : 'RULE_TYPE',
					title : '规则类型',
					width : 30,
					formatter : function(value, row, index) {
						for (var i = 0; i < typeList.length; i++) {
							if (typeList[i].key == value)
								return typeList[i].value;
						}
						return value;
					},
					align : 'center'
				},
				{
					field : 'TYPE_PROCESS',
					title : '执行类型',
					align : 'center',
					width : 30,
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
					field : 'DESCRIPTION',
					title : '说明',
					align : 'left',
					width : 80,
					hidden : true
				},
				{
					field : 'VALUE_PROCESS',
					title : '执行区间',
					align : 'center',
					width : 40,
					formatter : productsAttrFormatter,
					editor : {
						type : 'text'
					}
				},
				{
					field : 'CONCURRENT',
					title : '并发执行',
					align : 'center',
					width : 20,
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
					align : 'center',
					width : 30,
					formatter : function(value, row, index) {
						for (var i = 0; i < quartzStatus.length; i++) {
							if (quartzStatus[i].id == value)
								return quartzStatus[i].text;
						}
						return value;
					}
				},
				{
					field : 'RUN_ACTION',
					title : '操作',
					width : 50,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.INSTANCE_ID) {
							var s = '<a href="#" onclick="doProcess(' + row.INSTANCE_ID + ',' + row.SCHEDULE_ID + ')">调度运行</a> <a style="margin-left:40" href="#" onclick="removeSchedule(' + row.SCHEDULE_ID + ')">移除调度</a> <a href="#" style="margin-left:40" onclick="runJob(' + row.INSTANCE_ID + ')">直接运行</a> ';
							return s;
						}
					}
				},
				{
					field : 'GET_HISTORY',
					title : '历史记录',
					width : 20,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.INSTANCE_ID) {
							var s = '<a href="#" onclick="getHistroyJob(' + row.INSTANCE_ID + ',\'' + row.DESCRIPTION + '\')">历史记录</a> ';
							return s;
						}
					}
				}
			] ],
			onError : function(index, row) {
				alert(row.msg);
			},
		});
	});
</script>

<script type="text/javascript">
	var lastIndex;
	var updateFlag = false; //判断按保存按钮时更新标志位

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
				INSTANCE_ID : '请选择',
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
					$.post('/hrs/dataProcessing/deleteAllocSchedule.do?', {
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
			href = "/hrs/dataProcessing/updateAllocSchedule.do?";
		} else {
			href = "/hrs/dataProcessing/saveAllocSchedule.do?";
		}
		var selrow = $('#tableview').datagrid('getSelected');
		var selrowindex = $('#tableview').datagrid('getRowIndex', selrow);
		$('#tableview').datagrid('endEdit', selrowindex);
		var instanceId = selrow.INSTANCE_ID;
		if (!isNaN(instanceId)) {
			if (selrow.VALUE_PROCESS != "") {
				$.post(href, {
					"INSTANCE_ID" : instanceId,
					"CONCURRENT" : selrow.CONCURRENT,
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
			} else {
				$.messager.alert('操作提示', "请选择执行区间！", 'info');
			}
		} else {
			$.messager.alert('操作提示', "请选择任务ID！", 'info')
			$("#tableview").datagrid('rejectChanges');
			addRow();
		}
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
			"/hrs/dataProcessing/AllocRequestInstanceLineCalculation.do?",
			{
				"jobId" : jobId,
				"scheduleId" : scheduleId
			},
			function(result) {
				//currentTabRefresh();
				$("#tableview").datagrid('reload');
			});
	}
	function removeSchedule(scheduleId) { //移除调度键
		$.ajaxSettings.async = false;
		$.post(
			"/hrs/dataProcessing/removeSchedule.do?",
			{
				"scheduleId" : scheduleId
			},
			function(result) {
				//currentTabRefresh();
				$("#tableview").datagrid('reload');
			});
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
		var coll = $(this).datagrid('getColumnOption', 'INSTANCE_ID');
		if (updateFlag) {
			coll.editor.options = {
				valueField : 'INSTANCE_ID',
				textField : 'INSTANCE_ID',
				method : 'post',
				url : '/hrs/dataProcessing/getAllocScheduleInstanceList.do?',
				readonly : true
			}
		} else {
			coll.editor.options = {
				valueField : 'INSTANCE_ID',
				textField : 'INSTANCE_ID',
				method : 'post',
				url : '/hrs/dataProcessing/getAllocScheduleInstanceList.do?',
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
	function doSearch() { //按状态、job名查询
		$('#tableview').edatagrid('load', {
			ruleType : $('#ruleType').combobox('getValue'),
			status : $('#status').combobox('getValue')
		});
	}
	/* 	function doAllSchedule() {//执行所有schedule
			$.ajax({
				url : '/hrs/etlProcessing/getEtlScheduleList.do?',
				type : 'POST',
				async : true,
				dataType : 'json',
				success : function(data) {
					var json = eval(data.rows)
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
					$("#tableview").datagrid('reload');
				}
			});
		} */
	function getHistroyJob(target, headerName) {
		var targetUrl = '/hrs/dataProcessing/showAllocInstanceHistoryView.do?instanceId=' + target;
		if (headerName.length < 1) {
			var tabName = '分摊源账户配置';
		} else {
			var tabName = headerName + '-分摊源账户配置';
		}
		addTabInParent(tabName, targetUrl);
	}

	function runJob(jobId) {
		var postData = "jobId=" + jobId;
		$.ajax({
			url : '/hrs/dataProcessing/AllocRequestInstanceLineCalculation.do?',
			type : 'GET',
			async : true,
			data : postData,
			dataType : 'json',
			success : function(data) {
				completeFlag = 'Y';
				if (timeoutId) {
					clearTimeout(timeoutId);
					$('#progBar').progressbar('setValue', 100);
				}
				var trandata = eval(data);
				if (trandata.success) {
					$.messager.alert('信息', trandata.message, 'info', function() {
						currentTabRefresh();
						//$("#tableview").datagrid('reload');
					});
				} else {
					$.messager.alert('错误', trandata.message, 'error', function() {
						//currentTabRefresh();
						$("#tableview").datagrid('reload');
					});
				}

			},
			error : function(data, msg, exception) {
				completeFlag = 'Y';
				if (timeoutId) {
					clearTimeout(timeoutId);
					$('#progBar').progressbar('setValue', 100);
				}
				$.messager.alert('错误', '任务执行失败！', 'error', function() {
					//currentTabRefresh();
					$("#tableview").datagrid('reload');
				});
			}
		});

	}
</script>

</head>
<body>
	<form:form id="requestExceForm" commandName="executCondition" action=""
		method="post">
		<div id="tb" style="padding:3px">
			&nbsp;&nbsp;&nbsp;<span>规则类型:</span>
			<form:select path="ruleType" id="ruleType" name="ruleType"
				items="${executCondition.typeList}" itemLabel="description"
				itemValue="driverType" class="easyui-combobox" style="width:100"></form:select>
			&nbsp;&nbsp;&nbsp;<span>状态:</span>
			<form:input id="status" path="status" name="status"
				class="easyui-combobox"
				data-options="
		    valueField: 'STATUS_ID',
		    textField: 'STATUS_DESCRIPTION',
		    url: '/hrs/etlProcessing/getEtlScheduleStatusList.do'" />
			<a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
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
		<div id="progBar" style="width:400px;"></div>
		<table id="tableview"></table>
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
	</form:form>
</body>
</html>