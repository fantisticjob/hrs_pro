<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*"%>
<html>
  <head>
    <jsp:include page="/views/common/meta.jsp"></jsp:include>
    <jsp:include page="/views/common/easyui.jsp"></jsp:include>
	<script type="text/javascript">
	    function comboDimGridSearch(gridEleId, inputEleId) {
			var combox = $('#' + gridEleId).combogrid('grid');
			if (combox) {
			    $('#' + gridEleId).combogrid('grid').datagrid(
			                                             'reload',
			                                             { comboxDimValCode: $('#' + inputEleId).val(),
                                                           performOperation: 'comboSearch'
			                                             }
			                                        );
			}
        }
	</script>	
  </head>
<body>
  
	        <form:form id="lowHighForm" commandName="contentLowHighBean" action="" method="post">
                <div  id="lowHighFormDiv">
	                    <form:hidden path="itemContentId"/>
	                    <table cellpadding="0" cellspacing="3">
	                        <tr>
                                <th align="center">维度</th>
                                <th align="center">下限</th>
                                <th align="center">上限</th>
                            </tr>
	                        <c:if test="${contentLowHighBean.segment1Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment1Desc" path="segment1Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension1id" name="dimension1id" path="segment1Pair.dimensionId"/>
		                                <form:hidden id="dimension1SelLowVal" name="dimension1SelValue" path="segment1LowValue"/>
										<form:hidden id="dimension1SelHighVal" name="dimension1SelValue" path="segment1HighValue"/>
	                                </td>
	                        	    <td align="center">
									    <form:input id="segment1LowValue" path="segment1LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment1HighValue" path="segment1HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim1LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim1LowValCode" name="comboxDim1LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment1LowValue','comboxDim1LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim1HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim1HighValCode" name="comboxDim1HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment1HighValue','comboxDim1HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimensionId = $('#dimension1id').val();
									var dim1SelLowVal = $('#dimension1SelLowVal').val();
									var dim1SelHighVal = $('#dimension1SelHighVal').val();
		                            var dataOneUrl = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimensionId;
									$('#segment1LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim1SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim1LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码',halign: 'center', width:60},
                                                                  {field:'DESCRIPTION', title:'说明',halign: 'center', width:100}
                                                               ]]
                                                        });
								    $('#segment1HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim1SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim1HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',halign: 'center',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',halign: 'center',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>
	                        
	                        <c:if test="${contentLowHighBean.segment2Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input  id="segment2Desc" path="segment2Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension2id" name="dimension2id" path="segment2Pair.dimensionId"/>
		                                <form:hidden id="dimension2SelLowVal" name="dimension2SelLowVal" path="segment2LowValue"/>
										<form:hidden id="dimension2SelHighVal" name="dimension2SelHighVal" path="segment2HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment2LowValue" path="segment2LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment2HighValue" path="segment2HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim2LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim2LowValCode" name="comboxDim2LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment2LowValue','comboxDim2LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim2HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim2HighValCode" name="comboxDim2HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment2HighValue','comboxDim2HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId2 = $('#dimension2id').val();
									var dim2SelLowVal = $('#dimension2SelLowVal').val();
									var dim2SelHighVal = $('#dimension2SelHighVal').val();
		                            var dataOneUrl2 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId2;
									$('#segment2LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim2SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl2,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim2LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment2HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim2SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl2,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim2HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>	

	                        <c:if test="${contentLowHighBean.segment3Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment3Desc" path="segment3Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension3id" name="dimension3id" path="segment3Pair.dimensionId"/>
		                                <form:hidden id="dimension3SelLowVal" name="dimension3SelLowVal" path="segment3LowValue"/>
										<form:hidden id="dimension3SelHighVal" name="dimension3SelHighVal" path="segment3HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment3LowValue" path="segment3LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment3HighValue" path="segment3HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim3LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim3LowValCode" name="comboxDim3LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment3LowValue','comboxDim3LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim3HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim3HighValCode" name="comboxDim3HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment3HighValue','comboxDim3HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId3 = $('#dimension3id').val();
									var dim3SelLowVal = $('#dimension3SelLowVal').val();
									var dim3SelHighVal = $('#dimension3SelHighVal').val();
		                            var dataOneUrl3 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId3;
									$('#segment3LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim3SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl3,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim3LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment3HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim3SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl3,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim3HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>
	                        
	                        <c:if test="${contentLowHighBean.segment4Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment4Desc" path="segment4Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension4id" name="dimension4id" path="segment4Pair.dimensionId"/>
		                                <form:hidden id="dimension4SelLowVal" name="dimension4SelLowVal" path="segment4LowValue"/>
										<form:hidden id="dimension4SelHighVal" name="dimension4SelHighVal" path="segment4HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment4LowValue" path="segment4LowValue"  />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment4HighValue" path="segment4HighValue"  />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim4LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim4LowValCode" name="comboxDim4LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment4LowValue','comboxDim4LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim4HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim4HighValCode" name="comboxDim4HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment4HighValue','comboxDim4HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId4 = $('#dimension4id').val();
									var dim4SelLowVal = $('#dimension4SelLowVal').val();
									var dim4SelHighVal = $('#dimension4SelHighVal').val();
		                            var dataOneUrl4 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId4;
									$('#segment4LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim4SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl4,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim4LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment4HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim4SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl4,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim4HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>		        
	                        
	                        <c:if test="${contentLowHighBean.segment5Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment5Desc" path="segment5Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension5id" name="dimension5id" path="segment5Pair.dimensionId"/>
		                                <form:hidden id="dimension5SelLowVal" name="dimension5SelLowVal" path="segment5LowValue"/>
										<form:hidden id="dimension5SelHighVal" name="dimension5SelHighVal" path="segment5HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment5LowValue" path="segment5LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment5HighValue" path="segment5HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim5LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim5LowValCode" name="comboxDim5LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment5LowValue','comboxDim5LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim5HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim5HighValCode" name="comboxDim5HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment5HighValue','comboxDim5HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId5 = $('#dimension5id').val();
									var dim5SelLowVal = $('#dimension5SelLowVal').val();
									var dim5SelHighVal = $('#dimension5SelHighVal').val();
		                            var dataOneUrl5 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId5;
									$('#segment5LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim5SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl5,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim5LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment5HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim5SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl5,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim5HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>		                        

	                        <c:if test="${contentLowHighBean.segment6Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment6Desc" path="segment6Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension6id" name="dimension6id" path="segment6Pair.dimensionId"/>
		                                <form:hidden id="dimension6SelLowVal" name="dimension6SelLowVal" path="segment6LowValue"/>
										<form:hidden id="dimension6SelHighVal" name="dimension6SelHighVal" path="segment6HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment6LowValue" path="segment6LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment6HighValue" path="segment6HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim6LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim6LowValCode" name="comboxDim6LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment6LowValue','comboxDim6LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim6HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim6HighValCode" name="comboxDim6HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment6HighValue','comboxDim6HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId6 = $('#dimension6id').val();
									var dim6SelLowVal = $('#dimension6SelLowVal').val();
									var dim6SelHighVal = $('#dimension6SelHighVal').val();
		                            var dataOneUrl6 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId6;
									$('#segment6LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim6SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl6,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim6LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment6HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim6SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl6,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim6HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>		                                                                 
	                        
	                        <c:if test="${contentLowHighBean.segment7Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment7Desc" path="segment7Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension7id" name="dimension7id" path="segment7Pair.dimensionId"/>
		                                <form:hidden id="dimension7SelLowVal" name="dimension7SelLowVal" path="segment7LowValue"/>
										<form:hidden id="dimension7SelHighVal" name="dimension7SelHighVal" path="segment7HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment7LowValue" path="segment7LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment7HighValue" path="segment7HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim7LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim7LowValCode" name="comboxDim7LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment7LowValue','comboxDim7LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim7HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim7HighValCode" name="comboxDim7HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment7HighValue','comboxDim7HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId7 = $('#dimension7id').val();
									var dim7SelLowVal = $('#dimension7SelLowVal').val();
									var dim7SelHighVal = $('#dimension7SelHighVal').val();
		                            var dataOneUrl7 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId7;
									$('#segment7LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim7SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl7,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim7LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment7HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim7SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl7,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim7HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>	
	                       
	                        <c:if test="${contentLowHighBean.segment8Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment8Desc" path="segment8Pair.dimValueName"  readonly="true" />
		                                <form:hidden id="dimension8id" name="dimension8id" path="segment8Pair.dimensionId"/>
		                                <form:hidden id="dimension8SelLowVal" name="dimension8SelLowVal" path="segment8LowValue"/>
										<form:hidden id="dimension8SelHighVal" name="dimension8SelHighVal" path="segment8HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment8LowValue" path="segment8LowValue"  />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment8HighValue" path="segment8HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim8LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim8LowValCode" name="comboxDim8LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment8LowValue','comboxDim8LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim8HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim8HighValCode" name="comboxDim8HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment8HighValue','comboxDim8HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId8 = $('#dimension8id').val();
									var dim8SelLowVal = $('#dimension8SelLowVal').val();
									var dim8SelHighVal = $('#dimension8SelHighVal').val();
		                            var dataOneUrl8 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId8;
									$('#segment8LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim8SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl8,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim8LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment8HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim8SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl8,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim8HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>		                 	                        
	                       
	                        <c:if test="${contentLowHighBean.segment9Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment9Desc" path="segment9Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension9id" name="dimension9id" path="segment9Pair.dimensionId"/>
		                                <form:hidden id="dimension9SelLowVal" name="dimension9SelLowVal" path="segment9LowValue"/>
										<form:hidden id="dimension9SelHighVal" name="dimension9SelHighVal" path="segment9HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment9LowValue" path="segment9LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment9HighValue" path="segment9HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim9LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim9LowValCode" name="comboxDim9LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment9LowValue','comboxDim9LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim9HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim9HighValCode" name="comboxDim9HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment9HighValue','comboxDim9HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId9 = $('#dimension9id').val();
									var dim9SelLowVal = $('#dimension9SelLowVal').val();
									var dim9SelHighVal = $('#dimension9SelHighVal').val();
		                            var dataOneUrl9 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId9;
									$('#segment9LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim9SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl9,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim9LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment9HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim9SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl9,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim9HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>	
	                        
	                        <c:if test="${contentLowHighBean.segment10Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment10Desc" path="segment10Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension10id" name="dimension10id" path="segment10Pair.dimensionId"/>
		                                <form:hidden id="dimension10SelLowVal" name="dimension10SelLowVal" path="segment10LowValue"/>
										<form:hidden id="dimension10SelHighVal" name="dimension10SelHighVal" path="segment10HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment10LowValue" path="segment10LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment10HighValue" path="segment10HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim10LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim10LowValCode" name="comboxDim10LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment10LowValue','comboxDim10LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim10HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim10HighValCode" name="comboxDim10HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment10HighValue','comboxDim10HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId10 = $('#dimension10id').val();
									var dim10SelLowVal = $('#dimension10SelLowVal').val();
									var dim10SelHighVal = $('#dimension10SelHighVal').val();
		                            var dataOneUrl10 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId10;
									$('#segment10LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim10SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl10,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim10LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment10HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim10SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl10,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim10HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>	
	                        
	                        <c:if test="${contentLowHighBean.segment11Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment11Desc" path="segment11Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension11id" name="dimension11id" path="segment11Pair.dimensionId"/>
		                                <form:hidden id="dimension11SelLowVal" name="dimension11SelLowVal" path="segment11LowValue"/>
										<form:hidden id="dimension11SelHighVal" name="dimension11SelHighVal" path="segment11HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment11LowValue" path="segment11LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment11HighValue" path="segment11HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim11LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim11LowValCode" name="comboxDim11LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment11LowValue','comboxDim11LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim11HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim11HighValCode" name="comboxDim11HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment11HighValue','comboxDim11HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId11 = $('#dimension11id').val();
									var dim11SelLowVal = $('#dimension11SelLowVal').val();
									var dim11SelHighVal = $('#dimension11SelHighVal').val();
		                            var dataOneUrl11 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId11;
									$('#segment11LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim11SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl11,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim11LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment11HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim11SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl11,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim11HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>		                        	                        	                                          
                            <c:if test="${contentLowHighBean.segment12Pair != null}">
	                            <tr>
		                            <td align="center">
		                                <form:input id="segment12Desc" path="segment12Pair.dimValueName" readonly="true" />
		                                <form:hidden id="dimension12id" name="dimension12id" path="segment12Pair.dimensionId"/>
		                                <form:hidden id="dimension12SelLowVal" name="dimension12SelLowVal" path="segment12LowValue"/>
										<form:hidden id="dimension12SelHighVal" name="dimension12SelHighVal" path="segment12HighValue"/>
	                                </td>

	                        	    <td align="center">
									    <form:input id="segment12LowValue" path="segment12LowValue" />                  	  	                 
	                        	    </td>
		            	            <td align="center">
									    <form:input id="segment12HighValue" path="segment12HighValue" />                     	      	                 
	                        	    </td>
                                </tr>
								<div id="comboDim12LowBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim12LowValCode" name="comboxDim12LowValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment12LowValue','comboxDim12LowValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 
								<div id="comboDim12HighBar" class="datagrid-toolbar" hidden="true">  
	                                <span>维值代码:</span>
	                                    <input id="comboxDim12HighValCode" name="comboxDim12HighValCode" class="easyui-textbox" type="text" >
		                                <a href="#" class="easyui-linkbutton" onclick="comboDimGridSearch('segment12HighValue','comboxDim12HighValCode')">查询</a>
                                        <div style="clear: both"></div>  
                                </div> 									
								<script type="text/javascript">
	                                var dimId12 = $('#dimension12id').val();
									var dim12SelLowVal = $('#dimension12SelLowVal').val();
									var dim12SelHighVal = $('#dimension12SelHighVal').val();
		                            var dataOneUrl12 = '/hrs/rptSettings/getConfigurableDimValueList.do?dimensionId=' + dimId12;
									$('#segment12LowValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim12SelLowVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl12,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim12LowBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE', title:'维值代码', width:60},
                                                                  {field:'DESCRIPTION', title:'说明', width:100}
                                                               ]]
                                                        });
								    $('#segment12HighValue').combogrid({
                                                           panelWidth:500,
														   pagination:true,
														   value: dim12SelHighVal,
                                                           idField:'DIM_VALUE',
                                                           textField:'DISP_VALUE',
                                                           url:dataOneUrl12,
														   mode:'remote',
				                                           fitColumns:true,
				                                           autoSizeColumn:true,
														   toolbar: '#comboDim12HighBar',
                                                           columns:[[
                                                                  {field:'DIM_VALUE',title:'维值代码',width:60},
                                                                  {field:'DESCRIPTION',title:'说明',width:100}
                                                               ]]
                                                        });
	                            </script>								
	                        </c:if>			                        
	                        <tr>
	                           <td></td>
	                           <td>
			                       <a id="btnSubmit" onclick="doSubmit()" class="easyui-linkbutton" >提交</a>
	                          </td>
	                          <td></td>
                            </tr>
	                </table>
		       
		        </div>
	     </form:form>
	   


<script type="text/javascript">	
    function doSubmit() {
    	$("#lowHighForm").form("submit", {url:"submitContentLowHighValue.do?",       
		                                  success:function(data){
		    	                              var trandata = eval('(' + data + ')'); 
		    	                              if (trandata.success){
		    		                              $.messager.alert("提示消息", "保存成功!", "info");
				                              } else {
					                              $.messager.alert("提示消息", "保存上下限!", "info");
		    		                              return false;
				                              } 
		                                  }    
		                                 }
    	                      ); 	
    	
    }
</script>



	
</body>
</html>