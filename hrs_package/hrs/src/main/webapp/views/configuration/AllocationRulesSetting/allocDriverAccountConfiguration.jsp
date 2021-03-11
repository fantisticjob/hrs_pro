<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
    <title>动态分摊因子账户设置</title>   
	<script type="text/javascript">  
		//get title		 		    
		var tabTile ="<%=session.getAttribute("ruleName").toString()%>"+'动态分摊因子账户设置';
		var driverId=<%=session.getAttribute("driverId")%>;
		var dataUrl = '/hrs/dimFilterManage/findAllocDriverAccount.do?driverId=' + driverId;  
	    $(function(){
	    	$('#tableview').edatagrid({
				title:tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'DRVIER_ACC_ID',
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
                    {field:'DIMENSION_SEGMENT',title:'维度',width:50},	
					{field:'DIMENSION_NAME', title:'维度说明', width:50}, 	
					{field:'DIMENSION_VALUE',title:'维值',width:50,
					    formatter:function(value, row, index){
					        var returnValue = value;
					        if (value) {
					            var postData = "dimCode=" + row.DIMENSION_SEGMENT + "&selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
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
						},
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
								url:'/hrs/dimension/getDimValList.do?dimCode=SEGMENT1',
				                idField:'DIM_VALUE',
				                textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',title:'维度CODE',width:60},
					                {field:'DESCRIPTION',title:'说明',align:'right',width:80}
			                   	]],							
			                   	toolbar: '#comboToolBar1'
							}
						}
					},
					{field:'FILTER_HEADER_ID', title:'筛选条件', width:50,
					    formatter:function(value, row, index){
					        var returnValue = value;
					        if (value) {
					            var postData = "filterId=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimFilterManage/getDimFilterDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
                                    data: postData,								
		                            dataType : 'json',  
		                            success: function (data){
									    var transData = eval(data);
									    if (transData.FILTER_DISP) {
										    returnValue = transData.FILTER_DISP;
									    } 
		                            }    
		                        });
					        }
							return returnValue;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'FILTER_HEADER_ID',
								textField:'FILTER_DISP',
								url: '/hrs/dimFilterManage/getDimFilterList.do?dimCode=HRS_CORE_LEDGER'
							}
						}
					},
					{field:'DESCRIPTION', title:'备注', width:50,editor: {type:'validatebox', options:{required:false}}}, 	
					{field:'DRVIER_ID', title:'DRVIER_ID', width:50, hidden:true},
					{field:'DRVIER_ACC_ID', title:'ACC_ID', width:50, hidden:true}
				]],
				onEdit: function(index, row) {
                    var reloadUrl =  '';
                    var reloadFilterUrl = '';
					if (row) {
						reloadUrl = '/hrs/dimension/getDimValList.do?dimCode=' + row.DIMENSION_SEGMENT;
						reloadFilterUrl = '/hrs/dimFilterManage/getDimFilterList.do?dimCode=' + row.DIMENSION_SEGMENT;
					} else {
						return;
					}

					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'DIMENSION_VALUE'});
                    if (ed) {
                        var g = $(ed.target).combogrid('grid');
						if (g) {	
	                        g.datagrid({  
                                    url: reloadUrl
                                });														
						}
					}
					
					var filterEd = $('#tableview').edatagrid('getEditor', {index:index, field:'FILTER_HEADER_ID'});
					if (filterEd) {
					    var filterTarget = $(filterEd.target);
					    if (filterTarget) {
					        filterTarget.combobox('reload', reloadFilterUrl);
					    }
					} 
				},	
                onError: function(index, row){
					alert(row.msg);
                },				
				updateUrl: '/hrs/dimFilterManage/updateAllocDriverAccount.do?driverId=' + driverId
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
  <!--    <div id="tb" style="padding:3px">
	    <span>规则名称:</span>
	    <input id="ruleName" name="ruleName" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>-->
     <form:form id="allocDriver" commandName="allocDriver" action="" method="post">
        <form:hidden id="driverId" path="driverId" style="width:114px;"/>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
    <div id="comboToolBar1" class="datagrid-toolbar" hidden="true">  
	    <span>维值代码:</span>
	    <input id="comboxDimCode" name="comboxDimCode" class="easyui-textbox" type="text" >
		<a href="#" class="easyui-linkbutton" onclick="comboGridSearch()">查询</a>
        <div style="clear: both"></div>  
    </div> 
    
    </form:form>
</body>
</html>