<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>静态分摊因子行</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/easyui/themes/default/easyui.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/easyui/themes/icon.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/fw.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/demo.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/fancybox/jquery.fancybox-1.3.4.css"/>">

<script type="text/javascript"
	src="<c:url value="/resources/easyui/jquery.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/easyui/jquery.easyui.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/JQuery-formui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/easyui/locale/easyui-lang-zh_CN.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/validator.js"/>"></script>
<script type="text/javascript"> 
		 //get title		 		    
		    var tabTile ="<%=session.getAttribute("driverStaticHeaderName").toString()%>"+'子值范围';
		    var headerId=<%=session.getAttribute("driverStaticHeaderId")%>;
		    var dataUrl = '/hrs/dimFilterManage/findDriverStaticLineValue.do?headerId=' + headerId;
		    var dimCode="<%=session.getAttribute("driverStaticDeminsionSegment")%>";
		    var getsegmenturl='/hrs/dimension/getDimValList.do?dimCode='+dimCode;	    
	    $(function(){   		    
	    	$('#tableview').edatagrid({
				title:tabTile,
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: false,
				singleSelect:true,
				idField:'STATIC_LINE_ID',
				autoSizeColumn:true,
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
				columns:[[
					{field:'DIM_VALUE',title:'维值',halign: 'center',width:185,options:{required:true},
					    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "valuedata=" + value+"&headid="+headerId;							
						        $.ajax({    
		                            url:getsegmenturl,   
		                            type : 'POST', 
		                            async: false, 
                                    data: postData,								
		                            dataType : 'json',  
		                            success: function (data){
									    var transData = eval(data.rows);
									    //alert(JSON.stringify(data));
									    for(i=0;i<data.total;i++){
									    	if (transData[i].DIM_VALUE==value) {
										    returnValue = transData[i].DESCRIPTION;
									    }
									    } 
		                            }    
		                        });
					        }

							return returnValue;
						},
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url:getsegmenturl, 
				                idField:'DIM_VALUE',
				                textField:'DESCRIPTION',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'维度CODE',width:60},
					                {field:'DESCRIPTION',halign: 'center',title:'说明',align:'right',width:80}
			                   	]],
			                   	toolbar: '#comboToolBar'
							}
						}
					},
					{field:'DRIVER_NAME', title:'名称', halign: 'center',width:185, editor: {type:'validatebox', options:{required:true}}}, 	
					{field:'DESCRIPTION', title:'维值说明',halign: 'center', width:185, editor: {type:'validatebox', options:{required:false}}}, 	
					{field:'PROPORTION', title:'比例',halign: 'center', width:185, editor: {type:'numberbox', options:{required:true}}}, 	
					{field:'START_DATE_ACTIVE',align: 'center',title:'有效日期自',width:185,editor:{type:'datebox'}},
					{field:'END_DATE_ACTIVE',align: 'center',title:'有效日期至',width:185,editor:{type:'datebox'}},	
					{field:'STATIC_LINE_ID',align: 'center', title:'lineID', width:50, hidden:true}
				]],
				saveUrl: '/hrs/dimFilterManage/createDriverStaticLineValue.do?headerId=' + headerId,
				updateUrl: '/hrs/dimFilterManage/updateDriverStaticLineValue.do?headerId=' + headerId,
				destroyUrl: '/hrs/dimFilterManage/destroyDriverStaticLineValue.do?headerId=' + headerId
	    	});
	    });
	    
    </script>

<script type="text/javascript">
	function comboGridSearch() {
		var combox = $('.combogrid-f').combogrid('grid');
		if (combox) {
			$('.combogrid-f').combogrid('grid').datagrid(
				'reload',
				{
					comboxDimCode : $('#comboxDimCode').val(),
					performOperation : 'comboSearch'
				}
			);
		}
	}
</script>
<script type="text/javascript">
	function doSearch() {
		$('#tableview').edatagrid('load', {
			dimValue : $('#dimValue').val(),
			driverCode : $('#driverCode').val()
		});
	}
</script>

</head>
<body>
	<div id="tb" style="padding:3px">
		<span>维值:</span> <input id="dimValue" name="dimValue"
			class="easyui-textbox" type="text"> <span>名称:</span> <input
			id="driverCode" name="driverCode" class="easyui-textbox" type="text">
		<a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
	</div>
	<form:form id="driverStaticHeaderForm" commandName="driverStaticHeader"
		action="" method="post">
		<form:hidden id="staticHeaderId" path="staticHeaderId"
			style="width:114px;" />
		<form:hidden id="driverCode" path="driverCode" style="width:114px;" />
		<div style="margin-bottom:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true"
				onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true"
				onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save',plain:true"
				onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a> <a
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
				data-options="iconCls:'icon-remove',plain:true"
				onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save',plain:true"
				onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true"
				onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true"
				onclick="currentTabRefresh()">刷新</a>
		</div>

		<div id="comboToolBar" class="datagrid-toolbar" hidden="true">
			<span>维度代码:</span> <input id="comboxDimCode" name="comboxDimCode"
				class="easyui-textbox" type="text"> <a href="#"
				class="easyui-linkbutton" onclick="comboGridSearch()">查询</a>
			<div style="clear: both"></div>
		</div>

	</form:form>
</body>
</html>