<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
	<title>用户管理</title>
	<script type="text/javascript">
		var dataurl = '/hrs/userManage/findUserInfo.do';
		var saveurl = '/hrs/userManage/addUserInfo.do';
		var delurl = '/hrs/userManage/delUserInfo.do';
		var updateurl = '/hrs/userManage/updateUserInfo.do';
		var concurrent = [{
			id: '001',
			text: '管理员'
		},{
			id: '002',
			text: '会计'
		},{
			id: '003',
			text: '程序员'
		}];
		
	
		$(function(){
			$('#tableview').edatagrid({
				toolbar:"#toolbar",
			    columns:[[
				//	{field:'createdBy',title:'创建人',width:150,editor:{type:'validatebox',options:{required:true}}},
					{field:'createdatetime',title:'test',hidden:true},
				//  {field:'empty',title:'test',hidden:true},
					{field:'id',title:'用户名',width:150,editor:{type:'validatebox',options:{required:true} }},
					{field:'modifydatetime',title:'test',hidden:true},
				//	{field:'lastUpdatedBy',title:'test',hidden:true},
					{field:'password',title:'密码',width:250,editor:{type:'passwordbox',options:{required:true}},
						formatter : function(value, row, index) {
							return "********";
						}},
				//	{field:'rowCount',title:'test',hidden:true},
				//	{field:'rowStartIndex',title:'test',hidden:true},
					{field:'name',title:'姓名',width:150,editor:{type:'validatebox',options:{required:true}}},
					{field:'roleId',title:'角色',width:200,
						editor:{type:'combobox',
							options:{data:concurrent,multiple:true,valueField:'id',textField:'text'
							}
						}
					  	,formatter : function(value, row, index) {
					  		if(value!=null){
								var val = value.split(",");
								var show = "";
								for (var j = 0; j < val.length;j++){
									if (j!=0)
										show = show + ",";
									for (var i = 0; i < concurrent.length; i++) {
										if (concurrent[i].id == val[j])
											show = show + concurrent[i].text.toString();
									}
								}
					  		}
							return show;
						}   
					}
			    ]],
			    destroyMsg: {
					norecord:{	// when no record is selected
						title:'Warning',
						msg:'没有选中任何用户.'
					},
					confirm:{	// when select a row
						title:'Confirm',
						msg:'你是否要删除当前用户?'
					}
				},
				//autoSave:"true",
				pagination : "true",
				url:dataurl,
				saveUrl:saveurl,
				destroyUrl:delurl,
				updateUrl:updateurl
			});
		});
	</script>
</head>
<body>
    <div style="margin-bottom:10px" id="tb">
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