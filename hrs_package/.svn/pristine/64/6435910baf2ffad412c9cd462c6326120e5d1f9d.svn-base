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
		    var dimensionList='{}';  
		    $.ajax({    
		        url:'/hrs/rptSettings/getRuleDeminsionList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		        	dimensionList = data;  
		        }    
		    });
		    
		    //get title
		    var tabTile = $('#ruleName').val() + '-维度定义描述';
		    var ruleHeaderId = $('#ruleHeaderId').val();
		    var dataUrl = '/hrs/rptSettings/getRuleLineList.do?ruleHeaderId=' + ruleHeaderId;
		    
	    	$('#tableview').edatagrid({
				title: tabTile,
			    nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				autoSave: false,
				autoSizeColumn:true,
				idField:'RULE_LINE_ID',
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
		        },
				columns:[[ 
					{field:'RULE_LINE_ID', title:'维度行ID', width:50, hidden: true},
					{field:'RULE_HEADER_ID', title:'维度头ID', width:50, hidden: true},
					{field:'RULE_LINE_SEQ', title:'序号', width:200, 
						editor:{
							type:'validatebox', 
							options:{
								required:true,
								validType:'number'
						    }
					    }
					}, 
					{field:'DIM_SEGMENT',title:'维度',width:300,
						editor:{
							type:'combobox',
							options:{
								valueField:'DIM_SEGMENT',
								textField: 'DIM_SEGMENT',
								data: dimensionList,
								required:true,
								onChange:function(selectValue){
									var selectRow = $('#tableview').edatagrid('getSelected');
									var dispValue = '';
									for(var i=0; i<dimensionList.length; i++){
										if (dimensionList[i].DIM_SEGMENT == selectValue) 
											dispValue = dimensionList[i].DIMENSION_NAME;
									} 
									$(".datagrid-row-editing td[field=DESCRIPTION]").text(dispValue);	
								}
							}
						}
					},
					{field:'DESCRIPTION', title:'维度名称', width:500}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/rptSettings/saveRuleLine.do?',
				updateUrl: '/hrs/rptSettings/updateRuleLine.do?',
				destroyUrl: '/hrs/rptSettings/deleteRuleLine.do?'
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
    <form:form id="loopDimForm" commandName="ruleHeaderBean" action="" method="post">
        <form:hidden id="ruleHeaderId" path="ruleHeaderId" style="width:114px;"/>
        <form:hidden id="ruleName" path="description" style="width:114px;"/>
        
        <div id="tb" style="padding:3px">
	        <span>维度字段:</span>
	        <input id="dimSegment" name="dimSegment" class="easyui-textbox" type="text" >
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