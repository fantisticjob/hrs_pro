<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>维度设置</title>   
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
		  //  var tabTile = $('#dimensionFilterHeaderName').val() + '-子值范围';
		    //alert($('#dimensionFilterHeaderName').val());
		    
		    
		    var tabTile ="<%=session.getAttribute("diminsionFilterHeaderName").toString()%>"+'子值范围';
	
		   // var headerId = $('#dimensionFilterHeaderId').val();
		    //alert(headerId);
		  //  var dataUrl = '/hrs/dimFilterManage/findFilterLineValues?headerId=' + headerId;
		    var headerId=<%=session.getAttribute("diminsionFilterHeaderId")%>;
		    var dataUrl = '/hrs/dimFilterManage/findFilterLineValues.do?headerId=' + headerId;
		    var dimCode="<%=session.getAttribute("filterDeminsionSegment")%>";
		    var getsegmenturl='/hrs/dimension/getDimValList.do?dimCode='+dimCode;
		    var indicator = [
			    {value:'包含',key:'INC'},
			    {value:'排除',key:'EXC'}
			];
		    
	    $(function(){   		    
	    	$('#tableview').edatagrid({
				title:tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'FILTER_LINE_ID',
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
					{field:'VALUE_LOW',title:'自',align: 'center',width:50,
					  /*  formatter:function(value){
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
									    var transData = eval(data);
									    if (transData.DESCRIPTION) {
										    returnValue = transData.DESCRIPTION;
									    } 
		                            }    
		                        });
					        }

							return returnValue;
						},*/
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
			                   	toolbar: '#comboToolBar1'
							}
						}
					},
					{field:'VALUE_HIGH',title:'至',align: 'center',width:50,
					/*    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:getsegmenturl,     
		                            type : 'POST', 
		                            async: false, 
                                    data: postData,								
		                            dataType : 'json',  
		                            success: function (data){
									    var transData = eval(data);
									    if (transData.DISP_VALUE) {
										    returnValue = transData.DISP_VALUE;
									    } 
		                            }    
		                        });
					        }

							return returnValue;
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: getsegmenturl,
				                idField:'DIM_VALUE',
				                textField:'DESCRIPTION',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'维度CODE',width:60},
					                {field:'DESCRIPTION',halign: 'center',title:'说明',align:'right',width:80}
			                   	]],
			                   	toolbar: '#comboToolBar2'
							}
						}
					},	
					{field:'INC_EXC_INDICATOR',halign: 'center',title:'包含或排除',width:50,
						formatter:function(value){
							for(var i=0; i<indicator.length; i++){
								if (indicator[i].key == value) 
									return indicator[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data:indicator,
								required:true
							}
						}
					},
					{field:'DESCRIPTION', title:'说明',halign: 'center', width:50, editor: {type:'validatebox', options:{required:false}}}, 	
					{field:'FILTER_LINE_ID', title:'lineID', width:50, hidden:true}
				]],
				onError: function(index, row){
				    alert(row.msg);
				    var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_SEGMENT'});
					$(ed.target).combobox({disabled: false});
					$(ed.target).combobox('setValue', row.DIM_SEGMENT);
				},
				onEdit: function(index, row) {
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_SEGMENT'});				
					$(ed.target).combobox({disabled: true});
					$(ed.target).combobox('setValue', row.DIM_SEGMENT);
				},
				saveUrl: '/hrs/dimFilterManage/createFilterLineValues.do?headerId=' + headerId,
				updateUrl: '/hrs/dimFilterManage/updateFilterLineValues.do?headerId=' + headerId,
				destroyUrl: '/hrs/dimFilterManage/destroyFilterLineValues.do?headerId=' + headerId
	    	});
	    });
	    
    </script>

    <script type="text/javascript">
        function comboGridSearch() {
			var combox = $('.combogrid-f').combogrid('grid');
			if (combox) {
			    $('.combogrid-f').combogrid('grid').datagrid(
			                              'reload',
			                              {comboxDimCode: $('#comboxDimCode').val(),
			                               performOperation: 'comboSearch'
			                              }
			                          );
			}
        }
    </script>
</head>
<body>
     <form:form id="dimensionFilterLineForm" commandName="dimensionFilterHeaderBean" action="" method="post">
        <form:hidden id="dimensionFilterHeaderId" path="filterHeaderId" style="width:114px;"/>
        <form:hidden id="dimensionFilterHeaderName" path="filterHeaderName" style="width:114px;"/>
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
	
    <div id="comboToolBar1" class="datagrid-toolbar" hidden="true">  
	    <span>维度代码:</span>
	    <input id="comboxDimCode" name="comboxDimCode" class="easyui-textbox" type="text" >
		<a href="#" class="easyui-linkbutton" onclick="comboGridSearch()">查询</a>
        <div style="clear: both"></div>  
    </div> 
    <div id="comboToolBar2" class="datagrid-toolbar" hidden="true">  
	    <span>维度代码:</span>
	    <input id="comboxDimCode" name="comboxDimCode" class="easyui-textbox" type="text" >
		<a href="#" class="easyui-linkbutton" onclick="comboGridSearch()">查询</a>
        <div style="clear: both"></div>  
    </div> 
    </form:form>
</body>
</html>