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

	    
	     	var staticList='{}';
		    $.ajax({    
		        url:'/hrs/dimFilterManage/getStaticList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            staticList = data;  
		        }    
		    });
	    	var driverTypeList = [
	    	    {value:'动态因子',key:'DYNAMIC'},
			    {value:'静态因子',key:'STATIC'},
			    {value:'常数',key:'CONSTANT'}
			];
	    	var actualList = [
			    {value:'实际',key:'A'},
			    {value:'预算',key:'B'}
			];	    
	    	var currencyTypeList = [
			    {value:'本位币',key:'T'},
			    {value:'原币',key:'E'},
			    {value:'统计',key:'S'}
			];
	    	var currencyCodeList = [
			    {value:'CNY',key:'CNY'},
			    {value:'USD',key:'USD'},
			    {value:'HKD',key:'HKD'},
			    {value:'STAT',key:'STAT'},
			];
			
	    	var amtTypeList = [
			    {value:'PTD',key:'PTD'},
			    {value:'YTD',key:'YTD'}
			];			
	    	var directionList = [
			    {value:'DR',key:'DR'},
			    {value:'CR',key:'CR'},
			    {value:'NET',key:'NET'}
			];				
			
			//get title
		    var allocRuleId = $('#allocRuleId').val();
		    var dataUrl = '/hrs/allocRuleConfig.do?allocDriverDatagrid&allocRuleId=' + allocRuleId; 
		    var tabTile = $('#ruleName').val() + '-分摊因子配置';
	    	$('#tableview').edatagrid({
				title: tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
				idField:'driverId',
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
					{field:'driverType',title:'类型',halign: 'center',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < driverTypeList.length; i++){
							    if (driverTypeList[i].key == value) 
								    return driverTypeList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: driverTypeList,
								required:true
							}
						}
					},						
					{field:'actualFlag',title:'余额类型',halign: 'center',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < actualList.length; i++){
							    if (actualList[i].key == value) 
								    return actualList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: actualList,
								required:true
							}
						}
					},				
					{field:'currencyType',title:'币种类型',halign: 'center',width:50,
					    formatter:function(value, row, index){
						  	for(var i=0; i < currencyTypeList.length; i++){
							    if (currencyTypeList[i].key == value) 
								    return currencyTypeList[i].value;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: currencyTypeList,
								required:true
							}
						}
					},				
					{field:'currencyCode',title:'币种',halign: 'center',width:50,
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: currencyCodeList,
								required:true
							}
						}
					},				
					{field:'amountType',title:'金额类型',halign: 'center',width:50,
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: amtTypeList,
								required:true
							}
						}
					},				
					{field:'directionCode',title:'方向',halign: 'center',width:50,
						editor:{
							type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								data: directionList,
								required:true
							}
						}
					},			
				    {field:'description', title:'说明',halign: 'center', width:50, editor: {type:'validatebox', options:{required:false}}},
             

					{field:'constant',title:'常数',halign: 'center',width:100,
                      editor:{  
                          type:'validatebox'
                      }
                    },

                    {field:'staticHeaderId',title:'静态分摊因子',halign: 'center',width:50,
						formatter:function(value){
							for(var i=0; i<staticList.length; i++){
								if (staticList[i].STATIC_HEADER_ID == value) 
									return staticList[i].DRIVER_CODE;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'STATIC_HEADER_ID',
								textField:'DRIVER_CODE',
								data:staticList,
								required:true
							}
						}
                    },
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index) {	
							if (row.driverType){
							    if ('DYNAMIC' == row.driverType) {
							    	var s = '<a href="#" onclick="configDriverAcct(' + row.driverId + ',\''    + row.description +      '\')">动态分摊因子账户配置</a> ';
								    return s;
							    }
							}
						}
					}, 
                    {field:'driverId', title:'DRIVER_ID', width:50, hidden:true}
				]],
				onError: function(index,row){
					alert(row.msg);
				},
				updateUrl: '/hrs/allocRuleConfig.do?updateAllocDriver'															  
	    	});
	    });
	    
        function configDriverAcct(target, headerName) {
        	//var targetUrl = '/hrs/allocRuleConfig.do?showAllocSourceAccountView' + '?allocSourceId=' + target;
        	var targetUrl='/hrs/dimFilterManage/showAllocDriverAccountValueView.do?allocDriverId=' + target;;  
        	var tabName = $('#ruleName').val() + '-动态分摊因子账户配置';
        	addTabInParent(tabName,targetUrl);
        }
    </script>
<!-- 
    <script type="text/javascript">
        function doSearch(){
    	    $('#tableview').edatagrid('load',{
    		    driverType: $('#driverType').val()
    	    });
        }
    </script>
    
  -->
</head>
<body>
    <form:form id="allocRuleForm" commandName="allocRuleRecord" action="" method="post">
        <form:hidden id="allocRuleId" path="allocRuleId" style="width:114px;"/>
        <form:hidden id="ruleName" path="ruleName" style="width:114px;"/>
<!--    <div id="tb" style="padding:3px">
	    <span>分摊因子类型:</span>
	    <input id="driverType" name="driverType" class="easyui-textbox" type="text" >
	    <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
    </div>   -->
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">刷新</a>
	</div>
	<table id="tableview"></table>
	</form:form>

</body>
</html>