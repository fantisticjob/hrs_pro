
(function($) {
	$(function() {
		$(function() {
			$('#dataview').datagrid({
				title: '维度设置',
				singleSelect: true,
				idField: 'DIMENSION_ID',
				nowrap: false,
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '/hrs/mainFrame/findDimensionConfig',
				columns: [[ 
					{field:'DIMENSION_ID', title:'维度ID', width:200, hidden:true}, 
					{field:'DIM_SEQ', title:'序号', width:200, align:'right', editor:'numberbox'}, 
					{field:'DIMENSION_CODE', title:'维度名称', width:200, editor:'text'}, 
					{field:'DIMENSION_NAME', title:'维度说明', width:200, editor:'text'}, 
					{field:'DIM_SEGMENT',title:'维度字段',width:100,
						formatter:function(value,row){
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'dimSegmentId',
								textField:'dimSegmentName',
								method: 'get',
								url: '/hrs/mainFrame/getSegmentList',
								required:true
							}
						}
					},
					{field:'FIN_ACCOUNT_FLAG', title:'是否会计科目', width:50,align:'center',
						editor:{
							type:'checkbox',
							options:{
								on: 'Y',
								off: 'N'
							}
						}
					},
					{field:'action', title:'操作', width:70,align:'center',
						formatter:function(value,row,index){
							if (row.editing){
								var s = '<a href="#" onclick="saverow(this)">Save</a> ';
								var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
								return s+c;
							} else {
								var e = '<a href="#" onclick="editrow(this)">Edit</a> ';
								var d = '<a href="#" onclick="deleterow(this)">Delete</a>';
								return e+d;
							}
						}
					}
				]],
				onBeforeEdit:function(index,row){
					row.editing = true;
					updateActions(index);
				},
				onAfterEdit:function(index,row){
					row.editing = false;
					updateActions(index);
				},
				onCancelEdit:function(index,row){
					row.editing = false;
					updateActions(index);
				}
			});
		});			
	});
})(window.jQuery);


function updateActions(index){
	$('#dataview').datagrid('updateRow',{
		index: index,
		row:{}
	});
}



function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
	$('#tt').datagrid('beginEdit', getRowIndex(target));
}
function deleterow(target){
	$.messager.confirm('Confirm','Are you sure?',function(r){
		if (r){
			$('#tt').datagrid('deleteRow', getRowIndex(target));
		}
	});
}
function saverow(target){
	$('#dataview').datagrid('endEdit', getRowIndex(target));
}

function cancelrow(target){
	$('#dataview').datagrid('cancelEdit', getRowIndex(target));
}

