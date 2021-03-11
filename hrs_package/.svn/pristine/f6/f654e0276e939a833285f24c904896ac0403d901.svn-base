/*!
* jQuery Form Plugin
* version: 3.18 (28-SEP-2012)
* @requires jQuery v1.5 or later
*/
; (function ($) {
    "use strict";
    
    $(function() {
    /**
     * 2012-03-06
     * 医生，建档人，建档单位的统一封装 需要自定义两个属性 inputtype="combogrid"  mode='doctor' 而且必须是html select元素
     */
	//医生
	$("select[mode='doctor']").combogrid({
		panelWidth : 400,
		idField : 'icode', //ID字段  
		textField : 'name', //显示的字段  
		url : "getDoctorData.action",
		mode : 'remote',
		loadMsg : "数据正在加载中...",
		fitColumns : true,
		striped : true,
		editable : true,
		pagination : false,//是否分页  
		rownumbers : true,//序号  
		collapsible : false,//是否可折叠的  
		fit : true,//自动大小  
		pageSize : 10,//每页显示的记录条数，默认为10  
		pageList : [ 10 ],//可以设置每页记录条数的列表  
		method : 'post',
		columns : [ [ {
			field : 'code',
			title : '编码',
			width : 120
		}, {
			field : 'name',
			title : '名称',
			width : 120
		}, {
			field : '1122',
			title : '检索码',
			width : 120
		}, {
			field : 'sortnum',
			title : '排序码',
			width : 80
		} ] ]
	});
	
	//建档单位
	$("select[mode='office']").combogrid({
		panelWidth : 400,
		idField : 'officeid', //ID字段  
		textField : 'office', //显示的字段  
		url : "getT_OfficeData.action",
		mode : 'remote',
		loadMsg : "数据正在加载中...",
		fitColumns : true,
		striped : true,
		editable : true,
		pagination : false,//是否分页  
		rownumbers : true,//序号  
		collapsible : false,//是否可折叠的  
		fit : true,//自动大小  
		pageSize : 10,//每页显示的记录条数，默认为10  
		pageList : [ 10 ],//可以设置每页记录条数的列表  
		method : 'post',
		columns : [ [ {
			field : 'codeno',
			title : '编码',
			width : 100
		}, {
			field : 'office',
			title : '名称',
			width : 200
		},{
			field : 'officeid',
			title : '科室ID',
			hidden:true,
			width : 120
		}, {
			field : 'spellno',
			title : '检索码',
			width : 120
		} ] ]
	});
	//操作员
	$("select[mode='operator']").combogrid({
		panelWidth : 400,
		idField : 'codeno', //ID字段  
		textField : 'operatorname', //显示的字段  
		url : "getOperatorData.action",
		mode : 'remote',
		loadMsg : "数据正在加载中...",
		fitColumns : true,
		striped : true,
		editable : true,
		pagination : false,//是否分页  
		rownumbers : true,//序号  
		collapsible : false,//是否可折叠的  
		fit : true,//自动大小  
		pageSize : 10,//每页显示的记录条数，默认为10  
		pageList : [ 10 ],//可以设置每页记录条数的列表  
		method : 'post',
		columns : [ [ {
			field : 'codeno',
			title : '编码',
			width : 120
		}, {
			field : 'operatorname',
			title : '名称',
			width : 120
		}, {
			field : '1122',
			title : '检索码',
			width : 120
		}, {
			field : 'sortnum',
			title : '排序码',
			width : 80
		} ] ]
	});
	
    
    
    /**
     * 2013-01-04 jiang
     * 将原来的方法注释掉，修改为新的方法，并且增加填充数据的方法。
     * 为什么修改？原来的是采用排除法，并且处理checkboxlist等不合适。
     * 新方法将采用优选法，使代码逻辑更简单，即，只处理项目中经常用到的集中控件，其他的不管（如果后面真的遇到特殊情况，则特殊处理）。
     * */
    
    /**从表单取数据生成json*/
    $.fn.toJsonString = function(){  
    	var form = this[0];
    	var arr={};
    	
    	//处理text类型    	
    	$(form).find("input[type=text]").each(function(i){
    		if($(this).attr("id")){
    			if($(this).attr("class") && $(this).attr("class").indexOf("easyui-combotree")>-1){
    				//combotree类型的文本输入框
    				arr[$(this).attr("id")] = $('#'+$(this).attr("id")).combotree('getValue') ;
    			}
    			else{
    				//普通的文本输入框
    				arr[$(this).attr("id")] = $(this).val() ;
    			}
    		}	
    	});
    	
    	//处理checkboxlist类型
    	$(form).find("input[type=checkbox]").each(function(i){
    		if($(this).attr("checked")){
    			if(!arr[$(this).attr("name")])
    				arr[$(this).attr("name")]="";
    			arr[$(this).attr("name")]+= $(this).val()+"="+$(this).attr('title')+"|" ;
    		}	    			
    	});
    	
    	//处理radiobuttonlist类型
    	$(form).find("input[type=radio]").each(function(i){
    		if($(this).attr("checked")){
    			arr[$(this).attr("name")]= $(this).val()+"="+$(this).attr('title') ;
    		}	    			
    	});
    	
    	//处理textarea类型
    	$(form).find("textarea").each(function(i){
    		arr[$(this).attr("id")] = $(this).val() ;
    	});
    	
    	//处理select类型
    	$(form).find("select").each(function(i){
    		//arr[$(this).attr("id")] = $(this).val() ;

    		if($(this).attr("inputtype") || $(this).attr("inputtype")=="combogrid"){
    			arr[$(this).attr("id")] = $(this).combogrid('getValue')+"="+$(this).combogrid('getText') ;
    		}else{
    			//alert($(this).val());
    			//alert($(this).find("option:selected").text());    			
    			arr[$(this).attr("id")] = $(this).val()+"="+$(this).find("option:selected").text();
    		}	
    	});
    	
    	//将数组转化为json字符串
    	var jsonstr = "{";
    	$.each(arr, function(idx2,val2) {                    
    		jsonstr += "\"" + idx2 + "\" : " + "\"" + val2 + "\",";
    	});
    	if(jsonstr.length>1)
    		jsonstr = jsonstr.substring(0,jsonstr.length - 1);
    	jsonstr += "}";
    	    	
        return jsonstr;
    };
    
    /**编辑数据时将json填充到表单*/
    $.fn.fromJsonString = function(jsonstr){
    	var form = this[0];
    	
    	//将json字符串转化为数组
    	jsonstr = "["+jsonstr+"]";    	
    	var arr = eval('(' + jsonstr + ')');
    	arr = arr[0];
    	
    	//处理text类型    	
    	$(form).find("input[type=text]").each(function(i){
    		if($(this).attr("id")){
    			if($(this).attr("class") && $(this).attr("class").indexOf("easyui-combotree")>-1){
    				//combotree类型的文本输入框
    				$('#'+$(this).attr("id")).combotree('setValue',arr[$(this).attr("id")]) ;
    			}
    			else{
    				//普通的文本输入框
    				$(this).val(arr[$(this).attr("id")]);
    			}
    		}	
    		
    		
    	});
    	
    	//处理checkboxlist类型
    	$(form).find("input[type=checkbox]").each(function(i){
    		var fieldvalue = arr[$(this).attr("name")];
    		if(fieldvalue){
    			var itemvalues=fieldvalue.split('|');
        		for (i in itemvalues)
        		{
        			if(!itemvalues[i] || itemvalues[i].length==0)
        				continue;
        			var itempair =itemvalues[i].split('=');
        			if(itempair.length!=2)
        				continue;
        			if(itempair[0]==$(this).val()){
        				$(this).attr("checked","true");
        				break;
        			}
        		}	
    		}   			    			
    	});
    	
    	//处理radiobuttonlist类型
    	$(form).find("input[type=radio]").each(function(i){
    		var fieldvalue = arr[$(this).attr("name")];  
    		if(fieldvalue){
    			var itempair =fieldvalue.split('=');
    			if(itempair.length==2
    				&& itempair[0]==$(this).val()){				
        				$(this).attr("checked","true");
    			}	
    		}   			
    	});
    	
    	//处理textarea类型
    	$(form).find("textarea").each(function(i){
    		$(this).val(arr[$(this).attr("id")]);
    	});
    	    	
    	//处理select类型
    	$(form).find("select").each(function(i){
    		if($(this).attr("inputtype") || $(this).attr("inputtype")=="combogrid"){
    			var pairvalue = arr[$(this).attr("id")];
    			var pairarr =pairvalue.split('=');
    			//alert(pairvalue);
    			if(pairarr.length>1){
    				var value = pairarr[0];
    				//alert(value);
        			$(this).combogrid('setValue',value);
        			$(this).combogrid('setText',pairarr[1]);	
    			}    			
    			else{
    				$(this).combogrid('setValue',pairvalue);
    			}
    		}
    		else{
    			var pairvalue = arr[$(this).attr("id")];
    			var pairarr =pairvalue.split('=');
    			if(pairarr.length>1){    				
    				var value = pairarr[0]; 
    				$(this).val(value);        				
    			}    			
    			else{
    				$(this).val(pairvalue);
    			}    			
    		}
    	});
    };
	});
    
    /**
    * Returns the value of the field element.
    */
    $.fieldValue = function (el, successful) {
        var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
        if (successful === undefined) {
            successful = true;
        }

        if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
        (t == 'checkbox' || t == 'radio') && !el.checked ||
        (t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
        tag == 'select' && el.selectedIndex == -1)) {
            return null;
        }

        if (tag == 'select') {
            var index = el.selectedIndex;
            if (index < 0) {
                return null;
            }
            var a = [], ops = el.options;
            var one = (t == 'select-one');
            var max = (one ? index + 1 : ops.length);
            for (var i = (one ? index : 0); i < max; i++) {
                var op = ops[i];
                if (op.selected) {
                    var v = op.value;
                    if (!v) { // extra pain for IE...
                        v = (op.attributes && op.attributes['value'] && !(op.attributes['value'].specified)) ? op.text : op.value;
                    }
                    if (one) {
                        return v;
                    }
                    a.push(v);
                }
            }
            return a;
        }
        return $(el).val();
    };

    
    /**
    * Checks/unchecks any matching checkboxes or radio buttons and
    * selects/deselects and matching option elements.
    */
    $.fn.selected = function (select) {
        if (select === undefined) {
            select = true;
        }
        return this.each(function () {
            var t = this.type;
            if (t == 'checkbox' || t == 'radio') {
                this.checked = select;
            }
            else if (this.tagName.toLowerCase() == 'option') {
                var $sel = $(this).parent('select');
                if (select && $sel[0] && $sel[0].type == 'select-one') {
                    // deselect all other options
                    $sel.find('option').selected(false);
                }
                this.selected = select;
            }
        });
    }

    /**
        设置div的元素
    */
    $.fn.fillRadio = function (jsonString,keyField,valueField) {
        if (this.length === 0) {
            return;
        }
        var els = $.parseJSON(jsonString);
        var radio = this[0];
        var e="";
        for(o in els)
        {
            for(obj in els[o])
            {
                e +="<input id=\""+o+obj.toLocaleString()+"\" name=\""+o+"\" type=\"radio\" value=\""+els[o][obj][keyField]+"\" /><label for=\""+o+obj.toLocaleString()+"\">"+els[o][obj][valueField]+"</label>";
            }    
        }
        $(radio).empty();
        $(radio).append(e);
    }

    $.fn.fillCheckbox = function (jsonString,objType,keyField,valueField) {
        if (this.length === 0) {
            return;
        }
        var els = $.parseJSON(jsonString);
        var checkbox = this[0];
        var e="";
        for(obj in els[objType])
        {
            e +="<input id=\""+objType+obj.toLocaleString()+"\" name=\""+objType+"\" type=\"checkbox\" value=\""+els[objType][obj][keyField]+"\" /><label for=\""+objType+obj.toLocaleString()+"\">"+els[objType][obj][valueField]+"</label>";
        }
        $(checkbox).empty();
        $(checkbox).append(e);
    }
    $.fn.fillSelect = function (jsonString,objType,keyField,valueField) {
        if (this.length === 0) {
            return;
        }
        var els = $.parseJSON(jsonString);
        var select = this[0];
        var e="";
	    for(obj in els[objType])
	    {
	       e += "<option value='" + els[objType][obj][keyField] + "'>" +els[objType][obj][valueField] + "</option>";
	    }
        $(select).empty();
        $(select).append(e);
    }

})(jQuery);
