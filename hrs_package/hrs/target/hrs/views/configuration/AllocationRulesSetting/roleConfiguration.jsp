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
	        var roleList='{}';
		    $.ajax({    
		        url:'/hrs/dimFilterManage/getRoleList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            roleList = data.rows;  
		        }    
		    });
            var dataUrl = '/hrs/dimFilterManage/findRoleUserMap.do?'; 
		    var tabTile =  '角色配置';
	    	$('#tableview').edatagrid({
				title: tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'ID',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url: dataUrl,
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
					{field:'USER_ID',title:'用户',halign: 'center',width:50,editor:{
					  type:'validatebox'
					}
					},					
					{field:'ROLE_ID',title:'角色',halign: 'center',width:50,
					formatter:function(value){
							for(var i=0; i<roleList.length; i++){
								if (roleList[i].ID == value) 
									return roleList[i].DESCRIPT;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'ID',
								textField:'DESCRIPT',
								data:roleList,
								required:true
							}
						}
					},				
                    {field:'ID', title:'id', width:50, hidden:true}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				saveUrl: '/hrs/dimFilterManage/saveRoleUserMap',
				updateUrl: '/hrs/dimFilterManage/updateRoleUserMap' ,
				destroyUrl: '/hrs/dimFilterManage/destroyRoleUserMap' 												  
	    	});
	    });

    </script>
</head>
<body>
<!--    <div id="tb" style="padding:3px">
	    <span>分摊源类型:</span>
	    <input id="roleId" name="roleId" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div> -->  
    <div style="margin-bottom:10px">
	 	 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">新增</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">删除</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
</body>
</html>