<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" 	src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
<style type="text/css">
.rowstyle {
	background-color: white !important;
	color: #323232 !important;
}
</style>
</head>
<body class="easyui-layout layout panel-noscroll" style="margin: auto;">
	<div style="width: 150px; float: left;margin: 20px 10px;">
			<ul id="tree">
			</ul>
	</div>
<div id="p2" class="easyui-panel" title="<span id='pltitle'>女装</span>" style="overflow: hidden; padding: 0px; background: #fafafa;">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updated()"><input 
	type="text" maxlength="20" placeholder="搜索类型名称<回车搜索>" data-end="yes" class="ipt1" id="qswatpvalue" data-enter="getdata(1)">
	</div>
</div>
</div>
<table id="gg"></table>
<div class="page-bar">
<div class="page-total">共有<span  id="pp_total">0</span>条记录</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
</div>
	<input type="hidden" id="hd" name="hd" value="1" />
	<input type="hidden" id="ptid" name="ptid" value="1" />
	<!-- 添加类型窗 -->
	<div id="editd" title="添加商品类型" data-options="modal: true" style="width: 350px; height: 200px;" class="easyui-dialog" closed=true>
		<input type="hidden" id="edit_typeid" value="">
		<div style="margin-top: 40px; text-align: center;">
			<label class="theader skyrequied">类型名称&nbsp;&nbsp;&nbsp;</label> 
			<input style="height: 25px" type="text" placeholder="<输入>"
				id="edit_typename" name="edit_typename" width="175px" maxlength="20" />
		</div>
		<div class="dialog-footer" style="text-align: center;">
			<input id="edit_add" class="btn1" type="button" name="save" style="width: 150px;" type="button" value="保存并继续添加" onclick="adddata();">
			<input id="edit_save" class="btn1 hide" type="button" name="save" style="width: 150px;" type="button" value="保存" onclick="updatedata();">
		</div>
	</div>
<script type="text/javascript">
var check = false;
var tpid;
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		height: height - 80
	});
}
$(function() {
	setwaretypetree();
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getdata(p);
		}
	});
	$('#gg').datagrid({
		width: '100%',
		height: $('body').height() - 110,
		fitColumns: true,
		selectOnCheck: false,
		checkOnSelect: false,
		striped: true,
		//隔行变色,
		nowrap: false,
		singleSelect: true,
		toolbar: '#toolbars',
		columns: [[{
			field: 'CHECK',
			checkbox: true
		},
		{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'TYPENAME',
			title: '商品类型',
			halign: 'center',
			align: 'center',
			width: 200,
			fixed: true,
			styler: function(value, row, index) {
				var accid = Number(row.ACCID);
				if (isNaN(accid) || accid == 0) {
					return "color:#afafaf;";
				}
			}
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var accid = Number(row.ACCID);
				if (accid == 0 || isNaN(accid)) return "";
				return '<input type="button" class="m-btn" value="删除" onclick="deldata(' + index + ')">';
			}
		},
		{
			field: 'TYPEID',
			title: 'ID',
			hidden: true,
			width: 100
		},
		{
			field: 'LASTNODE',
			title: 'LASTNODE',
			hidden: true,
			width: 100
		},
		{
			field: 'FULLNAME',
			title: 'FULLNAME',
			hidden: true,
			width: 100
		},
		{
			field: 'P_TYPEID',
			title: 'P_TYPEID',
			hidden: true,
			width: 100
		},
		{
			field: 'SELBJ',
			title: 'SELBJ',
			hidden: true,
			width: 100
		},
		{
			field: 'ACCID',
			title: 'ACCID',
			hidden: true,
			width: 100
		}]],
		onCheck: function(index, row) {
			if (check) {
				setWareType(row.TYPEID, 1, index);
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setWareType(row.TYPEID, 0, index);
			}
		},
		onCheckAll: function(rows) {
			if (check) {
				var ptpid = $('#hd').val();
				setAllWareType(ptpid, '1');
			}
		},
		onUncheckAll: function(rows) {
			if (check) {
				var ptpid = $('#hd').val();
				setAllWareType(ptpid, '0');
			}
		},
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (Number(item.SELBJ) == 1) {
						$('#gg').datagrid('checkRow', index);
					}else $('#gg').datagrid('uncheckRow', index);
				});
			}
			check = true;
		},
		onSelect: function(index, row) {
			if (row) {
				var accid = Number(row.ACCID);
				if (isNaN(accid) || accid == 0) {
					$('#updatebtn').hide();
					return;
				}
				$('#updatebtn').show();
			}
		},
		onDblClickRow: function(index, row) {
			var accid = Number(row.ACCID);
			if (isNaN(accid) || accid == 0) {
				alerttext('系统定义角色不允许编辑！');
				return;
			}
			updated();
		}
	});
	getdata('1');
});
function setwaretypetree(){
	var rows = [{
		id: 0,    
	    text: "商品大类",    
	    children: []
	}];
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "waretypelist",
			p_typeid: 0,
			fieldlist: "a.typeid,a.typename,a.lastnode,a.fullname,a.p_typeid",
			page: 1,
			rows: pagecount
		},
		async: false,
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) {
			try {
				if(valideData(data)){
					var rowsstr = JSON.stringify(data.rows).replace(/TYPEID/g,"id").replace(/TYPENAME/g,"text");
					rows[0].children = $.parseJSON(rowsstr);
				}
			} catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
	$('#tree').tree({
		data: rows,
		onClick: function(node) {
			var tid = $('#hd').val();
			if (node.id != '0' && node.id != tid) {
				$('#hd').val(node.id);
				$('#pltitle').html(node.text);
				getdata('1');
			}
		}
	});
}
function addd() {
	$('#editd').dialog({
		title: "添加商品类型"
	});
	$('#editd').dialog('open');
	$('#edit_typename').val("");
	$('#edit_typeid').val("");
	$('#edit_typename').focus();
	$('#edit_add').show();
	$('#edit_save').hide();
}
function updated() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		$('#edit_add').hide();
		$('#edit_save').show();
		$('#editd').dialog({
			title: "编辑商品类型"
		});
		$('#editd').dialog('open');
		$('#edit_typename').val(row.TYPENAME);
		$('#edit_typeid').val(row.TYPEID);
		$('#edit_typename').focus();
	}
}
function setWareType(typeid, value, index) {
	if (ptid != 0) {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "writeaccwaretype",
				typeid: typeid,
				value: value
			},
			//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
			dataType: 'json',
			success: function(data) {
				try {
					if(valideData(data)){
						$("#gg").datagrid("updateRow",{
							index:index,
							rows:{
								SELBJ: value
							}
						})
					}else{
						check=false;
						$("#gg").datagrid("refresh");
						check=true;
					}
				} catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
function setAllWareType(typeid, value) {
	if (ptid != 0) {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "writeallaccwaretype",
				typeid: typeid,
				value: value
			},
			//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
			dataType: 'json',
			success: function(data) {
				try {
					if (valideData(data)) {
						$('#pp').refreshPage();
					}else{
						check=false;
						$("#gg").datagrid("refresh");
						check=true;
					}	
				} catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
				
			}
		});
	}
}
//获取2级数据
function getdata(page) {
	var p_typeid = $('#hd').val();
	var value = $('#qswatpvalue').val();
	check = false;
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "waretypelist",
			findbox: value,
			p_typeid: p_typeid,
			rows: pagecount,
			fieldlist: "a.typeid,a.typename,a.lastnode,a.fullname,a.p_typeid,a.accid",
			page: page
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					$('#pp').setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#pp_total').html(data.total);
					$('#gg').datagrid('loadData', data);
					check = true;
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}

function adddata() {
	var typename = $('#edit_typename').val();
	var p_typeid = $('#hd').val();
	if (typename.length == 0) {
		alerttext("请输入商品类型名称！");
		$('#edit_typename').focus();
		return;
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addwaretype",
			typename: typename,
			p_typeid: p_typeid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var typeid = data.msg;
					var p = $('#pp').getPage();
					var rownumber = 1;
					if (p >= 1) rownumber = (p - 1) * pagecount + rownumber;
					check = false;
					$('#gg').datagrid('insertRow', {
						index: 0,
						row: {
							ROWNUMBER: rownumber,
							TYPEID: typeid,
							P_TYPEID: p_typeid,
							ACCID: getValuebyName('ACCID'),
							TYPENAME: typename,
							SELBJ: 1
						}
					});
					$('#gg').datagrid('refresh');
					$('#edit_typename').val('').focus();
					check = true;
				} else {
					$('#edit_typename').focus();
				}
			} catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
			
		}
	});
}
function updatedata() {
	var typename = $('#edit_typename').val();
	var p_typeid = $('#hd').val();
	var typeid = $('#edit_typeid').val();
	var row = $('#gg').datagrid('getSelected');
	var index = $('#gg').datagrid('getRowIndex', row);
	if (typename.length == 0) {
		alerttext("请输入商品类型名称！");
		$('#edit_typename').focus();
		return;
	}
	var accid = Number(row.ACCID);
	if (accid == 0 || isNaN(accid)) {
		alerttext("系统自带类型，不允许编辑！");
		return;
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewaretypebyid",
			typename: typename,
			accid: accid,
			typeid: typeid,
			p_typeid: p_typeid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					check = false;
					$('#gg').datagrid('updateRow', {
						index: index,
						row: {
							TYPENAME: typename
						}
					});
					$('#gg').datagrid('refresh');
					check = true;
					$('#editd').dialog('close');
				} else {
					$('#edit_typename').focus();
				}
			} catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function deldata(index) {
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	var typeid = Number(row.TYPEID);
	var p_typeid = $('#hd').val();
	if (typeid == 0) {
		alerttext("未选择商品！");
		return;
	}
	var accid = Number(row.ACCID);
	if (accid == 0 || isNaN(accid)) {
		alerttext("系统自带类型，不允许删除！");
		return;
	}
	$.messager.confirm(dialog_title, '删除后不再恢复，确定删除吗？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delwaretypebyid",
					accid: accid,
					typeid: typeid,
					p_typeid: p_typeid
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							check = false;
							$('#gg').datagrid('deleteRow', index);
							$('#gg').datagrid('refresh');
							check = true;
						}
					} catch (e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
				}
			});
		}
	});
}		
</script>
</body>
</html>