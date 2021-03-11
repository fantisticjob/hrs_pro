<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var cols = [{
		field : 'fileName',
		title : '文件名称',
		width : 150,
		sortable : true
	},  {
		field : 'dataNum',
		title : '导入数量',
		width : 150
	}, {
		field : 'dataStatus',
		title : '导入状态',
		width : 150,
		sortable : true
	},{
		field : 'createdatetime',
		title : '导入时间',
		width : 150,
		sortable : true
	},{
		field : 'createBy',
		title : '导入人',
		width : 150,
		sortable : true/*,
		formatter:function(value){
	     var name='';
	     $.ajax({    
			        url:'/hrs/dimFilterManage/getUserName.do?'+value,     
			        type : 'POST', 
			        async:false,  
			        dataType : 'String',  
			        success: function (data){
			        	 name=data;
			        }    
			    });
		return name;
		}*/
	}];
	
	$(function() {	
		datagrid = $('#datagrid').datagrid({
			url : '/hrs/mrdmExcelController.do?datagrid',
			toolbar : '#toolbar',
			title : '',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			hideColumn:true,
			nowrap : false,
			border : false,
			idField : 'id',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				checkbox : true
			}] ],
			columns : [cols]
			
		});
	
		 
	});

	function uploadExcel(){  		
	    if($("#file").val()==""){
	        $("#uploadInfo").html("<span style='color:Red'>" + '请选择要导入语的文件! '+ "</span>");
            return false;  
        }
        
        $("#p").ajaxSubmit({    
            type:"post",  //提交方式    
            url:'/hrs/mrdmExcelController.do?impExcel', //请求url
            dataType:"json",
            success:function(data){ //提交成功的回调函数    
                var message = "导入成功";            
                if(data.success) {
                	message = data.msg;
                }
            	$("#uploadInfo").html("<span style='color:Red'>" + message + "</span>");
            	datagrid.datagrid('load');
            },
            error: function (returnInfo) {
                //上传失败时显示上传失败信息
            	 $("#uploadInfo").html("<span style='color:Red'>" + '系统加载错误' + "</span>");
            }
        });  
	}
	

	function searchFun() {		
		datagrid.datagrid('load', {
			fileName : $('#toolbar input[name=fileName]').val(),
			createdatetime : $('#toolbar input[comboname=createdatetime]').datetimebox('getValue')
		});
	}
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar"
			style="height: auto; display: none;">
			<fieldset>
				<legend>筛选</legend>
				<table class="tableForm">
					<tr>
						<th>文件名称</th>
						<td><input name="fileName" style="width: 200px;" /></td>
						<th>创建时间</th>
						<td><input name="createdatetime" class="easyui-datetimebox"
							editable="false" style="width: 150px;" /></td>
						<td><a class="easyui-linkbutton" iconCls="icon-search"
							plain="true" onclick="searchFun();" href="javascript:void(0);">查找</a>
							<a class="easyui-linkbutton" iconCls="icon-search" plain="true"
							onclick="clearFun();" href="javascript:void(0);">清空</a></td>
					</tr>
				</table>

				<div id="excelImp" class="easyui-panel" title="excel导入"
					style="width: 700px; height: 100px; padding: 10px; background: #fafafa;"
					data-options="iconCls:'icon-import',collapsed:true, collapsible:true">
					
					<form id="p" method="POST" enctype="multipart/form-data">
						<div>
							<div>
								导入文件: <input id="file" type="file" name="fileToUpload" /><br />
								<label id="uploadInfo" />
								</bar>
							</div>
							<input type="button" value="确定" onclick="uploadExcel()"
								style="margin-top: 10px">
						</div>

					</form>
				</div>

			</fieldset>

			
		</div>
		<table id="datagrid"></table>
	</div>

</body>
</html>