<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>维度设置</title>   
	<script type="text/javascript"> 
	    $(function(){  
	    	$('#tableview').edatagrid({
				title:'ELT任务管理',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'JOB_ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url:'/hrs/etlProcessing/getEtlJobList.do?',
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
					{field:'JOB_NAME', title:'ETL任务名称', width:50, 
						editor:{
							type:'validatebox', 
							options:{
								required:true
						    }
					    }
					}, 
					{field:'FILE_PATH', title:'任务文件路径', width:50, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'PARAM_LIST', title:'参数列表', width:50, editor:'text' },   
					{field:'DESCRIPTION', title:'任务描述', width:50, editor:'text' },
					{field:'START_TIME',title:'开始时间',width:50},
					{field:'END_TIME',title:'结束时间',width:50},
					{field:'ELAPSED_TIME',title:'花费时间',width:50},
					{field:'STATUS',title:'状态',width:50},
					{field:'MESSAGE',title:'信息',width:50},
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {						
							if (row.JOB_ID){
								var s = '<a href="#" onclick="runJob(' + row.JOB_ID + ')">运行</a> ';
								return s;
							}
						}
					},
					{field:'JOB_ID', title:'任务ID', width:50, hidden:true}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/etlProcessing/saveEtlJob.do?',
				updateUrl: '/hrs/etlProcessing/updateEtlJbo.do?',
				destroyUrl: '/hrs/etlProcessing/deleteEtlJob.do?'
	    	});
	    });
	    
        function runJob(jobId) {
            var postData = "jobId=" + jobId;
		    $.ajax({    
		        url:'/hrs/etlProcessing/processEtlJob.do?',     
		        type : 'POST', 
		        async: true,
		        data: postData,
		        dataType : 'json',  
		        success: function (data){
		            currentTabRefresh();
		        }    
		    });
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    etlJobName: $('#etlJobName').val(),
    		    etlJobStatus: $('#etlJobStatus').val()
    	    });
        }
    </script>

</head>
<body>
    <div id="tb" style="padding:3px">
	    <span>ETL任务名称:</span>
	    <input id="etlJobName" name="etlJobName" class="easyui-textbox" type="text" >
	    <span>ETL任务状态:</span>
	    <input id="etlJobStatus" name="etlJobStatus" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	
</body>
</html>