<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>分摊规则设置</title>   
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/easyui/themes/default/easyui.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/easyui/themes/icon.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fw.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/demo.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fancybox/jquery.fancybox-1.3.4.css"/>">
	
	<script type="text/javascript" src="<c:url value="/resources/easyui/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/easyui/jquery.easyui.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/JQuery-formui.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/easyui/locale/easyui-lang-zh_CN.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/validator.js"/>"></script>
	<script type="text/javascript"> 
		 //get title		 		    
		    var tabTile ="<%=session.getAttribute("groupName").toString()%>"+'组分摊规则设置';
		    var headerId=<%=session.getAttribute("groupHeaderId")%>;
		    var dataUrl = '/hrs/dimFilterManage/findAllocRulesGroupLineValue.do?headerId=' + headerId;  
	    $(function(){
	        var ruleList='{}';
		    $.ajax({    
		        url:'/hrs/dimFilterManage/getRuleList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            ruleList = data;  
		        }    
		    });   		    
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
				idField:'GROUP_LINE_ID',
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
					{field:'LINE_NUM', title:'序号', width:50,align: 'center', editor: {type:'validatebox', options:{required:true}}}, 
                    {field:'RULE_ID', title:'分摊规则',align: 'center', width:150, 
                    	formatter:function(value){
							for(var i=0; i<ruleList.length; i++){
								if (ruleList[i].RULE_ID == value) 
									return ruleList[i].RULE_NAME;
							}
							return value;
						},
                     editor:{
							type:'combobox',
							options:{
							    textField:'RULE_NAME',
								valueField:'RULE_ID',
								data:ruleList,
								required:true,
									onChange:function(selectValue){
									var selectRow = $('#tableview').edatagrid('getSelected');
									var driverType = '';
									var description ='';
									var startDateActive = '';
									var endDateActive = '';
									for(var i=0; i<ruleList.length; i++){
										if (ruleList[i].RULE_ID == selectValue) {
											driverType = ruleList[i].DRIVER_TYPE;
											startDateActive = ruleList[i].START_DATE_ACTIVE;
											endDateActive=ruleList[i].END_DATE_ACTIVE;
											description=ruleList[i].DESCRIPTION;
											}
									} 
									$(".datagrid-row-editing td[field=DRIVER_TYPE]").text(driverType);	
									$(".datagrid-row-editing td[field=DESCRIPTION]").text(description);	
									$(".datagrid-row-editing td[field=START_DATE_ACTIVE]").text(startDateActive);	
									$(".datagrid-row-editing td[field=END_DATE_ACTIVE]").text(endDateActive);	
							}
							}
						}
                    },	
					{field:'DESCRIPTION', title:'说明',halign: 'center', width:150}, 	
					{field:'DRIVER_TYPE',title:'分摊类型',halign: 'center',width:100},					
					{field:'START_DATE_ACTIVE',title:'有效日期自',halign: 'center',width:150},
					{field:'END_DATE_ACTIVE',title:'有效日期至',halign: 'center',width:150},	
					{field:'GROUP_LINE_ID', title:'lineID', width:50, hidden:true}
				]],
				saveUrl: '/hrs/dimFilterManage/createAllocRulesGroupLine.do?headerId=' + headerId,
				updateUrl: '/hrs/dimFilterManage/updateAllocRulesGroupLineValue.do?headerId=' + headerId,
				destroyUrl: '/hrs/dimFilterManage/destroyDriverAllocRulesGroupLineValue.do?headerId=' + headerId
	    	});
	    });
	    
    </script>
       <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    ruleName: $('#ruleName').val()
    	    });
        }
    </script>
    
</head>
<body>
  <!--    <div id="tb" style="padding:3px">
	    <span>规则名称:</span>
	    <input id="ruleName" name="ruleName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>-->
     <form:form id="allocRulesGroupHeaderBean" commandName="allocRulesGroupHeaderBean" action="" method="post">
        <form:hidden id="groupHeaderId" path="groupHeaderId" style="width:114px;"/>
        <form:hidden id="groupName" path="groupName" style="width:114px;"/>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> 
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> 
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>

    </form:form>
</body>
</html>