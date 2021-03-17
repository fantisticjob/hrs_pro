<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>值集定义</title>   
    
	<script type="text/javascript"> 
	    $(function(){
	    	$('#tableview').edatagrid({
				title:'值集定义',
				width: "100%",
				height: "auto",
				scrolling: true,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: false,
				singleSelect:true,
				idField:'LOOKUP_TYPE_ID',
				autoSave: false,
				pagination:"true",
				url:'/hrs/rptSettings/getItemLookUpList.do?',
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
					{field:'LOOKUP_TYPE_ID', title:'值集ID', width:50, hidden:true },
					{field:'LOOKUP_TYPE', title:'值集类型', width:360,halign: 'center', editor: {type:'validatebox', options:{required:true}} }, 
					{field:'DESCRIPTION', title:'描述', width:360,halign: 'center', editor: {type:'validatebox', options:{required:true}},
					   formatter:function(value,row,index) {						
							if (row.DESCRIPTION){
								var s = row.DESCRIPTION.replace(/(^\s*)|(\s*$)/g, "&nbsp;");
								return s;
							}
						}					
					}, 
					{field:'action',title:'操作',width:360,align:'center',
						formatter:function(value,row,index) {						
							if (row.LOOKUP_TYPE_ID){
								var s = '<a href="#" onclick="inputLookUpDimension(' + row.LOOKUP_TYPE_ID + ',\''    + row.DESCRIPTION +      '\')">值集定义</a> ';
								
								return s;
							}
						}
					}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				/* onClickRow : function(rowIndex, rowData){
					  
					  var x = rowData.LOOKUP_TYPE_ID;
					  var targetUrl='/hrs/rptSettings/showLookUpDimensionView?lookUpId='+x;
					  location.href="/hrs/rptSettings/showLookUpDimensionView?lookUpId="+x;
					  var tabName = rowData.DESCRIPTION + '-值集';
			        	addTabInParent(tabName,targetUrl);
					
					},  */
				saveUrl: '/hrs/rptSettings/saveLookUpHeader.do?',
				updateUrl: '/hrs/rptSettings/updateLookUpHeader.do?',
				destroyUrl: '/hrs/rptSettings/deleteLookUpHeader.do?'
	    	});
	    });
	    
        function inputLookUpDimension(target, lookUpName) {
        	var targetUrl = '/hrs/rptSettings/showLookUpDimensionView.do' + '?lookUpId=' + target;
        	var tabName = lookUpName + '-值集定义';
        	addTabInParent(tabName,targetUrl);
        }
    </script>

    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    	    	lookUpCode: $('#lookUpCode').val(),
    	    	lookUpName: $('#lookUpName').val()
    	    });
        }
    </script>

</head>
<body>
    <div id="tb" style="padding:3px">   
	    <span>值集代码:</span>
	    <input id="lookUpCode" name="lookUpCode" class="easyui-textbox" type="text" >
	    <span>值集名称:</span>
	    <input id="lookUpName" name="lookUpName" class="easyui-textbox" type="text" >
	    
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
	<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
	 -->	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
	<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
	 -->	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	
</body>
</html>