<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
	<script type="text/javascript"> 
	    $(function(){
	     /*   var ledgerList='{}';
 		    $.ajax({    
		        url:'/hrs/dataProcessing/getLedgerList.do?',     
		        type : 'POST', 
		        async:false,  
		        dataType : 'json',  
		        success: function (data){
		            ledgerList = data;  
		        }    
		    }); 
	    	var finElementList='{}';
 		    $.ajax({    
		        url:'/hrs/dataProcessing/getFinElementListFilter.do?',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            finElementList = data;  
		        }    
		    }); */
		    
		    var finElementList='{}';
		   $.ajax({    
		        url:'/hrs/rptSettings/getLookUpLineList.do?lookUpTypeId=1',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		           for(var i=0; i<data.rows.length; i++){
		          if(data.rows[i].LOOKUP_VALUE.startsWith('1')){
		            delete data.rows[i].LOOKUP_VALUE;
		            delete data.rows[i].DESCRIPTION;
		            delete data.rows[i];
		            }
		            
		            }
		            finElementList = data;  
		        }    
		    });
		  	var ledgerList='{}';
		   $.ajax({    
		        url:'/hrs/rptSettings/getLookUpLineList.do?lookUpTypeId=1050',     
		        type : 'POST', 
		        async: false,  
		        dataType : 'json',  
		        success: function (data){
		            ledgerList = data;  
		        }    
		    });	    
		    //get title
		    var transProcHeaderId = $('#txnProcHeaderId').val();
		    var dataUrl = '/hrs/txnProcess.do?getTxnProcLineList&txnProcHeaderId=' + transProcHeaderId;
	    	$('#tableview').edatagrid({
				title:'?????????????????????',
				nowrap: false,
				//rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: false,
				singleSelect:true,
				idField:'txnLineId',
				autoSizeColumn:true,
				autoSave: false,
				pagination:"true",
				url: dataUrl,
				destroyMsg: {
					norecord:{	// when no record is selected
						title:'Warning',
						msg:'????????????????????????.'
					},
					confirm:{	// when select a row
						title:'Confirm',
						msg:'???????????????????????????????'
					}
				},
				columns:[[
				    {field:'seqNumber',title:'??????',width:30,halign: 'center',  /*width:50,*/
                      editor:{                       
                          type:'numberbox',
                          options:{
						      required:true
						  },
                      }
                    }, 
                    {field:'ledgerId', title:'??????',halign: 'center',width:40,
					/*	editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                data:ledgerList,
				                idField:'LOOKUP_VALUE',
				                textField:'DESCRIPTION',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'LOOKUP_VALUE',title:'??????',halign: 'center',width:40},
					                {field:'DESCRIPTION',title:'??????',align:'left',width:80}
			                   	]],
			                   	options:{
							        required:true
							    },
							}
						}*/
					},
				   /* {field:'ledgerId', title:'??????',width:60,  //width:80,
						formatter:function(value, row, index){
						  	for(var i=0; i < ledgerList.length; i++){
							    if (ledgerList[i].LEDGERID == value) 
								    //return ledgerList[i].LEDGERNAME;
								     return ledgerList[i].LEDGERID;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'LEDGERID',
								textField:'LEDGERNAME',
								data:ledgerList
							}
						}
					},*/
					{field:'finElement',title:'????????????',halign: 'center',width:50,
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                data:finElementList,
				                idField:'LOOKUP_VALUE',
				                textField:'DESCRIPTION',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'LOOKUP_VALUE',title:'??????',halign: 'center',width:40},
					                {field:'DESCRIPTION',title:'??????',halign: 'center',align:'left',width:80}
			                   	]],
			                   	options:{
							        required:true
							    },
			                   //	toolbar: '#comboToolBar'
							}
						}
					},
					/*{field:'finElement', title:'????????????',width:60,  //width:80,
						formatter:function(value, row, index){
						  	for(var i=0; i < finElementList.length; i++){
							    if (finElementList[i].FINELECODE == value) 
							        return finElementList[i].FINELECODE;
								   // return finElementList[i].FINELENAME;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
							    required:true,
								valueField:'FINELECODE',
								textField:'FINELENAME',
								data:finElementList
							}
						}
					},	*/	
					{field:'segment1',title:'??????',halign: 'center',width:80,  /*width:120,*/
					  /*  formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT1",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT1',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',title:'?????????',halign: 'center',width:40},
					                {field:'DESCRIPTION',title:'??????',halign: 'center',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar1'
							}
						}
					},					
					
					{field:'segment2',title:'??????',halign: 'center',width:80,  /*width:120,*/
					 /*  formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT2",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT2',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',title:'?????????',halign: 'center',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar2'
							}
						}
					},					
					{field:'segment3',title:'????????????',width:100,halign: 'center',  /*width:120,*/
				/*	    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT3",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT3',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar3'
							}
						}
					},					
					
					{field:'segment4',title:'????????????',halign: 'center',width:80,  /*width:120,*/
					  /*  formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT4",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT4',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar4'
							}
						}
					},					
					
					{field:'segment5',title:'??????',halign: 'center',width:50,  /*width:120,*/
					/*    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT5",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT5',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar5'
							}
						}
					},					
					
					{field:'segment6',title:'??????',halign: 'center',width:80,  /*width:120,*/
					   /* formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT6",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT6',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar6'
							}
						}
					},					
					
					{field:'segment7',title:'??????',halign: 'center',width:80,  /*width:120,*/
					/*    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT7",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT7',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar7'
							}
						}
					},					
					
					{field:'segment8',title:'??????',halign: 'center',width:100,  /*width:120,*/
					 /*   formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT8",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT8',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar8'
							}
						}
					},					
					
					{field:'segment9',title:'??????1',halign: 'center',width:45,  /*width:120,*/
					/*    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT9",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT9',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar9'
							}
						}
					},					
					
					{field:'segment10',title:'??????2',halign: 'center',width:45,  /*width:120,*/
				/*	    formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT10",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT10',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar10'
							}
						}
					},						
					
					{field:'segment11',title:'??????3',halign: 'center',width:45,  /*width:120,*/
					  /*  formatter:function(value){
					        var returnValue = value;
					        if (value) {
					            var postData = "selDimValueCode=" + value;							
						        $.ajax({    
		                            url:'/hrs/dimension/getDimValueDispName.do?',     
		                            type : 'POST', 
		                            async: false, 
		                            data: {
			    		                "dimCode":	"SEGMENT11",
						                "selDimValueCode":	value
			    	                },						
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
						},*/
						editor:{
							type:'combogrid',
							options:{
				                panelWidth:500,
				                pagination:"true",
				                url: '/hrs/dimension/getDimValList.do?dimCode=SEGMENT11',
				                idField:'DIM_VALUE',
				                textField:'DIM_VALUE',      //textField:'DISP_VALUE',
				                mode:'remote',
				                fitColumns:true,
				                autoSizeColumn:true,
				                columns:[[
					                {field:'DIM_VALUE',halign: 'center',title:'?????????',width:40},
					                {field:'DESCRIPTION',halign: 'center',title:'??????',/*align:'right',*/width:80}
			                   	]],
			                   	//toolbar: '#comboToolBar11'
							}
						}
					},						
                    
                    {field:'amountDR',title:'????????????',halign: 'center',width:100,align:'right',
                    	formatter:function(value){
                          return (value || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
						},
                /*      formatter: function(value){
                          var str=value;
                           var re=/(?=(?!(\b))(\d{3})+$)/g;
                            str=str.replace(re,",");
                            alert(str);
                            return str;
                      },*/
                      editor:{  
                          type:'numberbox'
                      }
                    }, 					
				    {field:'amountCR',title:'????????????',halign: 'center',width:100,align:'right',
				       formatter:function(value){
                          return (value || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
					  },
                      editor:{  
                          type:'numberbox'
                      }
                    }, 					
					{field:'description', title:'????????????',halign: 'center', width:450,  /*width:120,*/ editor:'text'},
					{field:'info',title:'??????',halign: 'center',width:450 }, 		
					{field:'creationDate',halign: 'center', title:'????????????', width:30,  /*width:50,*/  hidden:true},
					{field:'createdBy',halign: 'center', title:'?????????', width:30,  /*width:50,*/ hidden:true},
					{field:'lastUpdateDate',halign: 'center', title:'??????????????????', width:30,  /*width:50,*/ hidden:true},
					{field:'lastUpdatedBy',halign: 'center', title:'???????????????', width:30,  /*width:50,*/ hidden:true},
					{field:'txnHeaderId', halign: 'center',title:'???????????????ID', width:30,  /*width:50,*/ hidden:true},
					{field:'txnLineId',halign: 'center', title:'???????????????ID', width:30,  /*width:50,*/ hidden:true}
				]],
				onAdd: function(index, row) {
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment2'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
					
				    var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment4'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
					
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment5'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '00');
					}
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment6'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment7'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment9'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment10'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
					var ed = $('#tableview').edatagrid('getEditor', {index:index, field:'segment11'});
                    if (ed) {
                        $(ed.target).combogrid('setValue', '0');
					}
				},	
				onError: function(index, row){
				    alert(row.msg);
				},
				onBeforeEdit:function(index,row){
			        if ($('#auditFlag').val() == "Y") {
			           disableEditing();
			        }
                },
				saveUrl: '/hrs/txnProcess.do?saveTxnProcLine',
				updateUrl: '/hrs/txnProcess.do?updateTxnProcLine',
				destroyUrl: '/hrs/txnProcess.do?delTxnProcLine'
	    	});


	    });	 
    </script>
    
    <script type="text/javascript">
        
        function comboGridSearch(inputEleId, segmentCode, gridSeq) {

			var combox = $('.combogrid-f').eq(gridSeq).combogrid('grid');
			if (combox) {
			    $('.combogrid-f').eq(gridSeq).combogrid('grid').datagrid(
			                              'reload',
			                              {comboxDimCode: $('#' + inputEleId).val(),
			                               performOperation: 'comboSearch',
			                               dimCode: segmentCode
			                              }
			                          );
			}
        }        
    </script>
</head>
<body>
    <form:form id="transactionHeaderForm" commandName="txnProcHeaderBean" action="" method="post">
        <form:hidden id="txnProcHeaderId" path="txnHeaderId" style="width:114px;"/>
        <form:hidden id="auditFlag" path="auditFlag" style="width:114px;"/>
    <div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">??????</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">??????</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">??????</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">??????</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">??????</a>	
	</div>
	<table id="tableview"></table>
	<div style="margin-bottom:10px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:$('#tableview').edatagrid('addRow')">??????</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:$('#tableview').edatagrid('saveRow')">??????</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="javascript:$('#tableview').edatagrid('destroyRow')">??????</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="javascript:$('#tableview').edatagrid('cancelRow')">??????</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="currentTabRefresh()">??????</a>
	</div>	
	<!--  <div>
		<div id="comboToolBar1" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode1" name="comboxDimCode1" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode1', 'SEGMENT1', 0)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar2" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode2" name="comboxDimCode2" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode2','SEGMENT2', 1)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar3" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode3" name="comboxDimCode3" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode3','SEGMENT3', 2)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar4" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode4" name="comboxDimCode4" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode4','SEGMENT4',3 )">??????</a>
            <div style="clear: both"></div>  
        </div>
 		<div id="comboToolBar5" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode5" name="comboxDimCode5" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode5','SEGMENT5', 4)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar6" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode6" name="comboxDimCode6" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode6','SEGMENT6', 5)">??????</a>
            <div style="clear: both"></div>  
        </div>
 		<div id="comboToolBar7" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode7" name="comboxDimCode7" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode7','SEGMENT7', 6)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar8" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode8" name="comboxDimCode8" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode8','SEGMENT8', 7)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar9" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode9" name="comboxDimCode9" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode9','SEGMENT9', 8)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar10" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode10" name="comboxDimCode10" class="easyui-textbox" type="text" style="width: 100px;" >
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode10','SEGMENT10', 9)">??????</a>
            <div style="clear: both"></div>  
        </div>
		<div id="comboToolBar11" class="datagrid-toolbar" hidden="true">  
	        <span>????????????:</span>
	        <input id="comboxDimCode11" name="comboxDimCode11" class="easyui-textbox" type="text" style="width: 100px;">
		    <a href="#" class="easyui-linkbutton" onclick="comboGridSearch('comboxDimCode11','SEGMENT11', 10)">??????</a>
            <div style="clear: both"></div>  
        </div>
	</div> -->
    </form:form>
</body>
</html>