<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>目标账户设置</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/easyui/themes/default/easyui.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/easyui/themes/icon.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/fw.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/demo.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/fancybox/jquery.fancybox-1.3.4.css"/>">

<script type="text/javascript"
	src="<c:url value="/resources/easyui/jquery.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/easyui/jquery.easyui.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/JQuery-formui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.edatagrid.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/easyui/locale/easyui-lang-zh_CN.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/validator.js"/>"></script>

<script type="text/javascript"> 
	/*       var dimValueList='{}';
		    $.ajax({    
		        url:'/hrs/dimension/getDimValList.do?dimCode=SEGMENT1',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            dimValueList = data;  
		        }    
		    });   	*/


		 //get title		 		    

		    var headerId=<%=session.getAttribute("targetId")%>;
		    var targetType="<%=session.getAttribute("targetType")%>";
		    var sourceType="<%=session.getAttribute("sourceType")%>";
		    var driverType="<%=session.getAttribute("driverType")%>";
		    var staticDriverDim="<%=session.getAttribute("staticDriverDim")%>";
		    var targetTypeName=null;
		    if(targetType=="TARGET"){
		    targetTypeName='目标';
		    }else if (targetType=="OFFSET"){
		    targetTypeName='抵消';
		    }
		    var tabTile ="<%=session.getAttribute("ruleName").toString()%>"+targetTypeName+'账户设置';
		    var dataUrl = '/hrs/dimFilterManage/findAllocTargetAccount.do?targetId=' + headerId;  
		    var targetTypeList =null;
		    if (targetType=="TARGET"){
		    targetTypeList=[	
		        {value:'As Source',key:'SOURCE'},
			    {value:'As Driver',key:'DRIVER'},
			    {value:'Match Source & Driver',key:'MATCH'},
			    {value:'VALUE',key:'VALUE'}
			    ];
		   }else if(targetType=="OFFSET"){
		     targetTypeList=[	
		        {value:'As Source',key:'SOURCE'},
			    {value:'As Driver',key:'DRIVER'},
			    {value:'VALUE',key:'VALUE'}
			    ];
		   } else{
		       targetTypeList=[];
		   } ;

	    $(function(){
	        var segmentList='{}';
		    $.ajax({    
		        url:'/hrs/dimFilterManage/getSegmentList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            segmentList = data;  
		        }    
		    });
	    	$('#tableview').edatagrid({
				title:tabTile,
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				singleSelect:true,
							

				idField:'TARGET_ACC_ID',
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
                    {field:'DIMENSION_SEGMENT',halign: 'center',title:'维度',width:50},	
					{field:'DIMENSION_NAME',halign: 'center', title:'维度说明', width:50},
				    {field:'DIM_AllOC_TYPE',halign: 'center',title:'类型',width:50,
				          formatter:function(value, row, index){
						  	for(var i=0; i < targetTypeList.length; i++){
							    if (targetTypeList[i].key == value) 
								    return targetTypeList[i].value;
							}
							return value;
						}, 
				          editor: {
					         type:'combobox',
							options:{
							    textField:'value',
								valueField:'key',
								//data: targetTypeList,
							    url:'/hrs/dimFilterManage/getTargetTypeList.do?targetType='+targetType+'&sourceType='+sourceType+'&driverType='+driverType+'&dimCode=' + 'SEGMENT1'+'&staticDim='+staticDriverDim ,   
								required:true}}},
					{field:'DIMENSION_VALUE',halign: 'center',title:'维值',width:50,
					    formatter:function(value, row, index){
					        var returnValue = value;
					        if (value) {
					            var postData = "dimCode=" + row.DIMENSION_SEGMENT + "&selDimValueCode=" + value;							
						        $.ajax({    
                                    url:'/hrs/dimension/getDimValueDispName.do?',    
		                            //url:'/hrs/dimFilterManage/getTargetTypeList.do?targetType='+targetType+'&sourceType='+sourceType+'&driverType='+driverType+'&dimCode=' + 'SEGMENT1'+'&staticDim='+staticDriverDim ,     
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

			                   	toolbar: '#comboToolBar1',
			                   	//required:true
							}
						}
					},
					{field:'DESCRIPTION',halign: 'center', title:'备注', width:50,editor: {type:'validatebox', options:{required:false}}}, 	
					{field:'TARGET_ID',halign: 'center', title:'TARGET_ID', width:50,hidden:true},
					{field:'TARGET_ACC_ID',halign: 'center', title:'ACC_ID', width:50,hidden:true}
				]],
				onEdit: function(index, row) {
                    var reloadUrl =  '';
		            var reloadTypeUrl = '' ;   
					if (row) {
						reloadUrl = '/hrs/dimension/getDimValList.do?dimCode=' + row.DIMENSION_SEGMENT;
						reloadTypeUrl =  '/hrs/dimFilterManage/getTargetTypeList.do?targetType='+targetType+'&sourceType='+sourceType+'&driverType='+driverType+'&dimCode=' + row.DIMENSION_SEGMENT+'&staticDim='+staticDriverDim;                      
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
					var typeEd = $('#tableview').edatagrid('getEditor', {index:index, field:'DIM_ALLOC_TYPE'});
                    if (typeEd) {
                        var type = $(typeEd.target);
						if (type) {	
	                        type.combobox('reload',reloadTypeUrl);												
						}
					}
				},	
				onError: function(index, row){
					alert(row.msg);
                },			
				updateUrl: '/hrs/dimFilterManage/updateAllocTargetAccount.do?targetAccId=' + headerId
				
	    	});
	    });
	    
    </script>


<script type="text/javascript">
	function doSearch() {
		$('#tableview').edatagrid('load', {
			ruleName : $('#ruleName').val()
		});
	}
</script>
<script type="text/javascript">
	function comboGridSearch() {
		var combox = $('.combogrid-f').combogrid('grid');
		if (combox) {
			$('.combogrid-f').combogrid('grid').datagrid(
				'reload',
				{
					comboxDimCode : $('#comboxDimCode').val(),
					performOperation : 'comboSearch'
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


	<form:form id="allocTarget" commandName="allocTarget" action=""
		method="post">

		<form:hidden id="targetId" path="targetId" style="width:114px;" />
		<div style="margin-bottom:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save',plain:true"
				onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true"
				onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true"
				onclick="currentTabRefresh()">刷新</a>
		</div>
		<table id="tableview"></table>
		<div style="margin-bottom:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save',plain:true"
				onclick="javascript:$('#tableview').edatagrid('saveRow')">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true"
				onclick="javascript:$('#tableview').edatagrid('cancelRow')">取消</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-reload',plain:true"
				onclick="currentTabRefresh()">刷新</a>
		</div>
		<div id="comboToolBar1" class="datagrid-toolbar" hidden="true">
			<span>维值代码:</span> <input id="comboxDimCode" name="comboxDimCode"
				class="easyui-textbox" type="text"> <a href="#"
				class="easyui-linkbutton" onclick="comboGridSearch()">查询</a>
			<div style="clear: both"></div>
		</div>

	</form:form>
</body>
</html>