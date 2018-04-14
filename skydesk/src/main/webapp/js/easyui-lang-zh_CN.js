if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共有{total}条记录';
}
var getshowColumnArray = function(dgid,columns){
	var arr = getparamx({
		pgid: pgid,
		usymbol: dgid
	});
	var grid = $(dgid);
	var showColumnArray = grid.data("defaultShowColumnArray");
	if(arr.length==0){
		setparamx({
			pgid: pgid,
			usymbol: dgid,
			uvalue: showColumnArray.toString()
		});
	}else{
		showColumnArray = arr.split(",");
	}
//	for(var i in columns[0]){
//		var row = columns[0][i];
//		 var j = $.inArray(row.field,showColumnArray);
//		if(j>=0)
//			row.hidden = false;
//		else
//			row.hidden = true;
//	}
	return columns;
}
var createGridHeaderContextMenu = function(e, field) {  
    e.preventDefault();  
    var grid = $(this);/* grid本身 */ 
    var showColumnArray = grid.datagrid("options").showColumnArray;
    var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */  
    var okCls = 'icon-check';// 选中  
    var emptyCls = 'icon-nocheck';// 取消  
    if (!headerContextMenu) {  
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');  
        var fields = grid.datagrid('getColumnFields');  
        for (var i = 0; i < fields.length; i++) {  
            var fildOption = grid.datagrid('getColumnOption', fields[i]);  
            if (!fildOption.hidden) {  
                $('<div iconCls="' + okCls + '" field="' + fields[i] + '"/>')  
                        .html(fildOption.title).appendTo(tmenu);  
            } else {  
                $('<div iconCls="' + emptyCls + '" field="' + fields[i] + '"/>')  
                        .html(fildOption.title).appendTo(tmenu);  
            }  
        }  
        headerContextMenu = this.headerContextMenu = tmenu.menu({  
            onClick : function(item) {  
                var field = $(item.target).attr('field');  
                var i = $.inArray(field,showColumnArray);
                if (item.iconCls == okCls) {  
                    grid.datagrid('hideColumn', field);  
                    $(this).menu('setIcon', {  
                        target: item.target,  
                        iconCls: emptyCls  
                    });
                    if(i>=0) showColumnArray.splice(i,1);
                } else {  
                    grid.datagrid('showColumn', field);  
                    $(this).menu('setIcon', {  
                        target: item.target,  
                        iconCls: okCls
                    });
                    if(i==-1) showColumnArray.push(field);
                }
                setparamx({
        			pgid: pgid,
        			usymbol: dgid,
        			uvalue: showColumnArray.toString()
        		});
                grid.datagrid("resize");
            }  
        }); 
    }  
    headerContextMenu.menu('show', {  
        left : e.pageX,  
        top : e.pageY  
    });  
};  
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在加载中，请稍待。。。';
	$.fn.datagrid.defaults.nowrap = false;
	$.fn.datagrid.defaults.checkOnSelect = false;
	$.fn.datagrid.defaults.singleSelect = true;
	$.fn.datagrid.defaults.selectOnCheck = false;
	$.fn.datagrid.defaults.scrollLoad = false;
	$.fn.datagrid.defaults.rows = [];
	$.fn.datagrid.defaults.pageSize = 20;
	$.fn.datagrid.defaults.page = 1;
	
	//滚动加载数据
	// 上下键选择
	$.extend($.fn.datagrid.methods, {
		keyCtr: function(jq, field) {
			return jq.each(function() {
				var grid = $(this);
				$('#toolbars :text,.toolbar,.help-qsipt').bind("click keyup",
				function(e) {
					if(e.which!=13&&e.which!=38&&e.which!=40){
						grid.datagrid('unselectAll');
					}
				});
				grid.datagrid('getPanel').panel('panel').attr('tabindex', 1).bind('keyup',
				function(e) {
						switch (e.keyCode) {
						case 38:
							// up
							if (!$('.combox-selects').isShow()) {
								var selected = grid.datagrid('getSelected');
								if (selected) {
									var index = grid.datagrid('getRowIndex', selected);
									if (index == 0) {
										grid.datagrid('scrollTo', index);
									} else {
										grid.datagrid('scrollTo', index - 1);
										grid.datagrid('selectRow', index - 1);
									}
								} else {
								
								}

							}
							break;
						case 40:
							// down
							if (!$('.combox-selects').isShow()) {
								var selected = grid.datagrid('getSelected');
								if (selected) {
									var index = grid.datagrid('getRowIndex', selected);
									var rows = grid.datagrid('getRows');
									if (index != rows.length - 1) {
										grid.datagrid('scrollTo', index + 1);
										grid.datagrid('selectRow', index + 1);
									}
								} else {
									//																		grid.datagrid('scrollTo',0);
									//																		grid.datagrid('selectRow',0);
								}
							}
							break;
						case 13:
							// Enter
							var selected = grid.datagrid('getSelected');
							if (selected) {
								eval(field == undefined ? "": field);
							} else {
								grid.datagrid('unselectAll');
							}
							break;
						case 115:
							// F4
							if (typeof(f4) != "undefined") {
								if (f4 != "") {
									var selected = grid.datagrid('getSelected');
									if (selected) {
										eval(f4);
									} else {
										grid.datagrid('unselectAll');
									}

								}
							}
							break;
						}
				});
			});
		},
		refresh: function(jq) {
			return jq.each(function() {
				var grid = $(this);
				grid.datagrid("loadData", grid.datagrid('getData'));
			});
		},
		updateFooter: function(jq, row) {
			return jq.each(function() {
				var grid = $(this);
				var footer = grid.datagrid('getFooterRows');
				for (var f in row) footer[0][f] = row[f];
				grid.datagrid('reloadFooter');
			});
		}
	});
	//重写render
	
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
	$.messager.defaults.width = 350;
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必输项';
	$.fn.combobox.defaults.onHidePanel = function(){
		$(this).next().children('input[type=text]').focus();
	}
	/*$.fn.combobox.defaults.keyHandler = {
		up: function(e){
//			$(this).combobox('showPanel');
		},
		down: function(e){
//			$(this).combobox('showPanel');
		},
		left: function(e){},
		right: function(e){},
		enter: function(e){},
		query: function(q,e){}
		}*/
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combogrid){
	$.fn.combogrid.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
	var td = new Date();
	var y1 = td.getFullYear();
	var m1 = td.getMonth()+1;
	var d1 = td.getDate();
	$.fn.datebox.defaults.value = y1+'-'+(m1<10?('0'+m1):m1)+'-'+(d1<10?('0'+d1):d1);
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
	$.fn.datebox.defaults.onSelect = function(date) {
		$(this).siblings('span').children('input[type=text]').focus();
	}
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}
//让window居中
var easyuiPanelOnOpen = function (left, top) {
    var iframeWidth = $("body").width();
    var iframeHeight = $("body").height();
    var windowWidth = $(this).parent().width();
    var windowHeight = $(this).parent().height();
    var setWidth = (iframeWidth - windowWidth) / 2;
    var setHeight = (iframeHeight - windowHeight) / 2;
    $(this).parent().css({/* 修正面板位置 */
        left: setWidth,
        top: setHeight
    });
    if (iframeHeight < windowHeight)
    {
        $(this).parent().css({/* 修正面板位置 */
            left: setWidth,
            top: 0
        });
    }
    $(".window-shadow").hide();
};
if($.fn.dialog){
	$.fn.dialog.defaults.onOpen = easyuiPanelOnOpen;
	$.fn.dialog.defaults.modal = true;
}
var helpcheck = false;
var checkseloption = {
	onLoadSuccess: function(data) {
		helpcheck = false;
		var rows = data.rows;
		var $this = $(this);
		var selobject = $this.datagrid("options").selobject;
		var selkey = $this.datagrid("options").selkey;
		var namekey = selkey.name;
		var valuekey = selkey.value;
		if(rows.length>0&&selobject.value.length>0){
			for(var i in rows){
				var row = rows[i];
				var _name = row[namekey];
				var _value = Number(row[valuekey]);
				var name = selobject.name;
				var value = selobject.value;
				var valuearr = value.split("^");
				if($.inArray(_value,valuearr)>=0) $this.datagrid('checkRow', i);
				else $this.datagrid('uncheckRow', i);
			}
		}
		helpcheck = true;
	},
	onCheck: function(index,row){
		if(helpcheck){
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			var selkey = $this.datagrid("options").selkey;
			var namekey = selkey.name;
			var valuekey = selkey.value;
			var _name = row[namekey];
			var _value = Number(row[valuekey]);
			var name = selobject.name;
			var value = selobject.value;
			var valuearr = value.split("^");
			if($.inArray(_value,valuearr)==-1){
				name += _name +",";
				value += _value +"^";
				selobject.name = name;
				selobject.value = value;
			}
		}
	},
	onCheckAll: function(rows){
		if(helpcheck){
			for(var i in rows){
				var row = rows[i];
				var $this = $(this);
				var selobject = $this.datagrid("options").selobject;
				var selkey = $this.datagrid("options").selkey;
				var namekey = selkey.name;
				var valuekey = selkey.value;
				var _name = row[namekey];
				var _value = Number(row[valuekey]);
				var name = selobject.name;
				var value = selobject.value;
				var valuearr = value.split("^");
				if($.inArray(_value,valuearr)==-1){
					name += _name +",";
					value += _value +"^";
					selobject.name = name;
					selobject.value = value;
				}
			}
		}
	},
	onUncheck: function(index,row){
		if(helpcheck){
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			var selkey = $this.datagrid("options").selkey;
			var namekey = selkey.name;
			var valuekey = selkey.value;
			var _name = row[namekey];
			var _value = Number(row[valuekey]);
			var name = selobject.name;
			var value = selobject.value;
			var valuearr = value.split("^");
			if($.inArray(_value,valuearr)>=0){
				name = name.replace(_name +",","");
				value = value.replace(_value +"^","");
				selobject.name = name;
				selobject.value = value;
			}
		}
	},
	onUncheckAll: function(rows){
		if(helpcheck){
			for(var i in rows){
				var row = rows[i];
				var $this = $(this);
				var selobject = $this.datagrid("options").selobject;
				var selkey = $this.datagrid("options").selkey;
				var namekey = selkey.name;
				var valuekey = selkey.value;
				var _name = row[namekey];
				var _value = Number(row[valuekey]);
				var name = selobject.name;
				var value = selobject.value;
				var valuearr = value.split("^");
				if($.inArray(_value,valuearr)>=0){
					name = name.replace(_name +",","");
					value = value.replace(_value +"^","");
					selobject.name = name;
					selobject.value = value;
				}
			}
		}
	}
};

var strfm = function(value, row, index) {
	return value;
}
//序号格式化
var rownumberfm = function(value, row, index) {
	var rm = Number(value);
	var page = Math.ceil(rm / pagecount);
	if (rm == 0 || value == undefined) return "";
	else if (!isNaN(rm)) return (page - 1) * pagecount + index + 1;
	else return value;
}
var datemx = false;
var datefm = function(value, row, index) {
	if (value != undefined) {
		if (datemx) return value;
		return value.substring(0, 10);
	}
	return "";
}
var datetimefm = function(value, row, index) {
	if (value != undefined) {
		if (datemx) return value;
		return value.substring(0, 16);
	}
	return "";
}
var entersalefm = function() {
	if (Number(allowinsale == 1)) return true;
	return false
}
var pfsalefm = function() {
	if (Number(getValuebyName("GLEVELID"))!=7) return true;
	return false
}
var ratefm = function(value, row, index) {
	var val = Number(value);
	if (isNaN(val)) return "";
	return (val * 100).toFixed(2) + "%"
}
var currfm = function(value, row, index) {
	var val = Number(value);
	if (isNaN(val)) return "";
	return val.toFixed(2)
}
var jjcurrfm = function(value, row, index) {
	var val = Number(value);
	if (isNaN(val)) return "";
	if(Number(allowinsale)==1)
		return val.toFixed(2);
	return "***";
}
var currfm2 = function(value, row, index) {
	var val = Number(value);
	if (isNaN(val) || value.length == 0 || val==0) return "";
	return val.toFixed(2)
}
var wareimgfm = function(value, row, index) {
	var val = value;
	if (val != '' && val != null && val != undefined) return '<img bigimg="true"  style=\"height:60px; width:60px;\" src="' + imageurl + '' + value + '_60x60-m3.png" />';
	return "";
}
var amtfm = function(value, row, index) {
	var val = Number(value);
	if (isNaN(val)) return "";
	return val
}
var amtfm2 = function(value, row, index) {
	var val = Number(value);
	if (isNaN(val) || value.length == 0) return "";
	return val
}
//大于20字符串 隐藏后面字符串
var strfm = function(value, row, index) {
	if(value&&value.length>=20) return value.substring(0,20)+"……";
	return value;
}
var strstyle = function(value, row, index) {

}
var amtstyle = function(value, row, index) {
	var val = Number(value);
	if (val == 0) return "color: #aaa;"
}
var currstyle = function(value, row, index) {
	var val = Number(value);
	if (val == 0) return "color: #aaa;"
}
var jjcurrstyle = function(value, row, index) {
	var val = Number(value);
	if (val == 0 && allowinsale==1) return "color: #aaa;"
}

var zeroCellStyle = function(value, row, index) {
	var val = Number(value);
	if (val == 0) return "color: #aaa;";
}