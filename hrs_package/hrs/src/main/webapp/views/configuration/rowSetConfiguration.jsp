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
		    var tabTile = $('#codeName').val() + '-值集定义';
		    var lookUpTypeId = $('#lookUpTypeId').val();
		    var dataUrl = '/hrs/rptSettings/getLookUpLineList.do?lookUpTypeId=' + lookUpTypeId;
		    
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
				idField:'LOOKUP_VALUE_ID',
				autoSave: false,
				url:dataUrl,
				pagination:"true",
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
					{field:'LOOKUP_VALUE_ID', title:'值集行ID', width:50, hidden: true},
					{field:'LOOKUP_TYPE_ID', title:'值集头ID', width:50, hidden: true},
					{field:'LOOKUP_VALUE', halign: 'center',title:'值代码', width:125, editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', halign: 'center',title:'描述', width:125, editor: {type:'validatebox', options:{required:true}}}, 
					{field:'START_DATE', halign: 'center',title:'有效日期自', width:125}, 
					{field:'END_DATE', halign: 'center',title:'有效日期至', width:125}, 
					{field:'ATTRIBUTE1',halign: 'center', title:'辅助段1', width:120, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'ATTRIBUTE2',halign: 'center', title:'辅助段2', width:120, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'ATTRIBUTE3',halign: 'center', title:'辅助段3', width:120, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'ATTRIBUTE4',halign: 'center', title:'辅助段4', width:120, editor: {type:'validatebox', options:{required:false}}}, 
					{field:'ATTRIBUTE5',halign: 'center', title:'辅助段5', width:120, editor: {type:'validatebox', options:{required:false}}}					
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveLookUpLine.do?',
				updateUrl: '/hrs/rptSettings/updateLookUpLine.do?',
				//destroyUrl: '/hrs/rptSettings/deleteLookUpLine'
	    	});
	    });
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	dimSegment: $('#dimSegment').val()
    	    });
        }
    </script>

</head>
<body>
    <form:form id="loopDimForm" commandName="lookUpHeaderBean" action="" method="post">
        <form:hidden id="lookUpTypeId" path="lookUpTypeId" style="width:114px;"/>
        <form:hidden id="codeName" path="description" style="width:114px;"/>
        
        <div id="tb" style="padding:3px">
	        <span>值代码:</span>
	        <input id="dimSegment" name="dimSegment" class="easyui-textbox" type="text" >
	        <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
        </div>
        <div style="margin-bottom:10px" id="">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<!--    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> -->
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
	    <table id="tableview"></table>
	    <div style="margin-bottom:10px" id="bottomToolBar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		   <!--  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a> -->
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	    </div>
    </form:form>
</body>
</html>