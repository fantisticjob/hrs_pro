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
		    //get title  
		    var tabTile = $('#RowSetHeaderName').val() + '-报表行定义';
		    var RowSetHeaderId = $('#RowSetHeaderId').val();		    
		    var dataUrl = '/hrs/rptSettings/getRowSetLineList.do?RowSetHeaderId=' + RowSetHeaderId;	    
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
				idField:'ROW_ID',
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
			    /* 	if (data.total==0) {
			    		//alert ($("#bottomToolBar").is(":hidden"));
			    		//$("#bottomToolBar").hide();
			    		//$(this).edatagrid('appendRow', {DIM_VALUE: '<div style="text-align:center;color:red">没有相关记录！</div>' }).edatagrid('mergeCells', {index: 0, field: 'DIM_VALUE', colspan: 9 });  
			    	}  else {
			    		$("#bottomToolBar").show();
			    	} */
		        },
				columns:[[ 
					{field:'ROW_ID', title:'行ID', width:50, hidden: true},
					{field:'ROW_SET_ID', title:'行集ID', width:50, hidden: true},
					{field:'LINE_NUM', title:'序号', width:50,halign: 'center', editor: {type:'validatebox', options:{required:false}} }, 
					{field:'ROW_NAME', title:'行项目代码', width:160,halign: 'center', editor: {type:'validatebox', options:{required:true}}}, 
					{field:'ROW_NUM', title:'行次', width:50,halign: 'center', editor: {type:'validatebox', options:{required:false}} },  
					{field:'CHANGE_SIGN',halign: 'center', title:'更改符号', width:60, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DISPLAY_FLAG', halign: 'center',title:'是否显示', width:60, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'EXTERNAL_CODE',halign: 'center', title:'外部代码', width:60, editor: {type:'validatebox', options:{required:false}} }, 
					
					{field:'ATTRIBUTE1',halign: 'center', title:'辅助段1', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE2',halign: 'center', title:'辅助段2', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE3', halign: 'center',title:'辅助段3', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE4',halign: 'center', title:'辅助段4', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE5',halign: 'center', title:'辅助段5', width:60, editor: 'text'},
					{field:'ATTRIBUTE6',halign: 'center', title:'辅助段6', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE7',halign: 'center', title:'辅助段7', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE8',halign: 'center', title:'辅助段8', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE9',halign: 'center', title:'辅助段9', width:60, editor: 'text'}, 
					{field:'ATTRIBUTE10',halign: 'center', title:'辅助段10', width:60, editor: 'text'},
					
					{field:'action',title:'操作',width:60,align:'center',
						formatter:function(value,row,index) {						
							if (row.ROW_ID){
								var s = '<a href="#" onclick="inputRowDimension(' + row.ROW_ID + ',\''    + row.ROW_NAME +      '\')">规则</a> ';
								return s;
							}
						}
					}
					
					/* {field:'RowSet_VALUE',title:'维度',width:50,
						formatter:function(value){
							for(var i=0; i<dimensionList.length; i++){
								if (dimensionList[i].acctTypeId == value) 
									return dimensionList[i].dimSegment + '-' + dimensionList[i].dimensionName;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'dimSegment',
								textField:'dimensionName',
								data: dimensionList,
								required:true
							}
						}
					} */
					
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveRowSetLine.do?',
				updateUrl: '/hrs/rptSettings/updateRowSetLine.do?',
				destroyUrl: '/hrs/rptSettings/deleteRowSetLine.do?'
	    	});
	    });
	    
	    function inputRowDimension(target, RowName) {
        	var targetUrl = '/hrs/rptSettings/showRowSetLineDimensionView.do' + '?RowId=' + target;
        	var tabName = RowName + '-行项目代码';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	RowName: $('#RowName').val()
    	    });
        }
    </script>

</head>
<body>
    <form:form id="loopDimForm" commandName="RowSetHeaderBean" action="" method="post">
        <form:hidden id="RowSetHeaderId" path="rowSetId" style="width:114px;"/>
        <form:hidden id="RowSetHeaderName" path="rowSetName" style="width:114px;"/>
        
        <div id="tb" style="padding:3px">
	        <span>行项目代码:</span>
	        <input id="RowName" name="RowName" class="easyui-textbox" type="text" >
	        <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
        </div>
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