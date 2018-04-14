<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="panel-fit" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>销售类型</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addsaled()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatesaled()">
	<input type="text" data-end="yes" placeholder="输入销售类型名称或简称<回车搜索>" class="ipt1" id="qssalevalue" maxlength="20" data-enter="getsaledata()">
<!-- 	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchsale();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr hide">
	<input type="button" class="btn3" style="width: 45px" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchsale();toggle();">
	<input type="hidden" id="ssaleid">
	<div class="search-box">
	<div class="s-box">
		<div class="title">销售类型名称</div>
		<div class="text"><input class="wid160 hig25" id="ssalename" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">状态</div>
		<div class="text">
		<label>
		<input type="radio" name="snouse" id="st1" value="0">在用
		</label>
		<label>
		<input type="radio" name="snouse" id="st2" value="1">禁用
		</label>
		<label>
		<input type="radio" name="snouse" id="st3" value="2" checked>所有
		</label>
		</div>
	</div>
	</div>
</div>
</div>
<table id="gg" style="overflow: hidden;"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">
	共有<span id="pp_total">0</span>条记录
</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<!-- 修改销售类型窗 -->
<div id="ud" title="<span id='udtitle'>编辑销售类型</span>" style="width: 350px; height: 200px;" class="easyui-dialog" closed=true>
	<div style="margin-top: 40px; text-align: center;">
	<table class="table1">
	<tr>
	<td class="header skyrequied" align="right">
	销售类型名称
	</td>
	<td>
	<input type="hidden" id="usaleid">
	<input type="hidden" id="uindex">
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="usalename" id="usalename" maxlength="20" /> 
	</td>
	</tr>
	</table>
	</div>
	<div class="dialog-footer textcenter">
		<label class="updateshow"><input type="checkbox" name="unoused" id="unoused">禁用</label>
		<input type="button" id="savebtn" class="btn1"  name="updatesale" value="保存" onclick="savesale()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//权限设置

setqxpublic();
var pgid= "pg1010";
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度  
	$('#gg').datagrid('resize', {
		height: height - 50
	});
}
var searchb = false;
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		searchb = false;
		$('#searchbtn').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('#searchbtn').show();
	}
	$('.searchbar').slideToggle('fast');
	$('#ssalename').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st1').prop('checked', true);
	var idstr = "#ssalename";
	$(idstr).val('');
}
//添加框
function addsaled() {
	$("#udtitle").html("添加销售类型");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$('#usalename,#usaleid,#uindex').val("");
	$(".updateshow").hide();
	$('#usalename').focus();
}
//编辑框
function updatesaled() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var noused = row.NOUSED;
		$("#udtitle").html("编辑销售类型");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#ud').dialog('open');
		$('#usalename').focus();
		$('#usalename').val(row.SALENAME);
		$('#usaleid').val(row.SALEID);
		if (noused != 0) {
			$('#unoused').prop('checked', true);
		} else {
			$('#unoused').removeProp('checked');
		}
	}
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getsalelist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'SALEID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "SALENAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'SALEID',
			title: '销售类型ID',
			fieldtype: "G",
			width: 200,
			expable: true,
			hidden: true
		},{
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
			field: 'SALENAME',
			title: '销售类型',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},
		{
			field: 'NOUSED',
			title: '禁用',
			width: 80,
			fixed: true,
// 			sortable: true,
			fieldtype: "G",
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var nouse = Number(value);
				var checked = "";
				if (nouse == 1) checked = "checked";
				if (isNaN(nouse)) return "";
				if (row.SALEID <= 100)
					return "";
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledsale(' + index + ')" ' + checked + '>';
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
				if (row.SALEID <= 100)
					return "";
				return '<input type="button" class="m-btn" value="删除" onclick="delsale(' + index + ')">';
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			if (rowData.SALEID <= 100)
				alerttext("不允许修改系统约定的销售类型！ ");
			else updatesaled();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatesaled()");
	getsaledata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendsale",
		xlsmobanname: "salecode",
		channel: "salecode"
	});
});

var currentdata = {};
var getsalelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "salecodelist";
	currentdata["noused"] = "2";
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$dg.datagrid('loadData', data);
					$('#pp_total').html(data.total);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getsaledata() {
	var value = $('#qssalevalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getsalelist(1);
}
//搜索销售类型
function searchsale() {
	var salename = $('#ssalename').val();
	var noused = 0;
	if ($('#st1').prop('checked')) noused = "0";
	else if ($('#st2').prop('checked')) noused = "1";
	else noused = "2";
	alertmsg(6);
	currentdata = {
		salename: salename,
		noused: noused
	};
	getsalelist(1);
}
//添加销售类型
function savesale() {
	var index = $("#uindex").val();
	var saleid = Number($("#usaleid").val());
	var salename = $('#usalename').val();
	if (salename == "") {
		alerttext("销售类型名不能为空");
		return;
	}
	var erpser = "addsalecode";
	var noused = 0;
	if (saleid > 0){
		erpser = "updatesalecodebyid";
		noused = $("#unoused").prop("checked") ? 1 : 0;
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			saleid: saleid,
			salename: salename,
			noused: noused
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var $dg = $("#gg");
					if (saleid == 0) {
						saleid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								SALEID: saleid,
								SALENAME: salename,
								NOUSED: noused
							}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						$('#usalename').val("").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								SALEID: saleid,
								SALENAME: salename,
								NOUSED: noused
							}
						}).datagrid('refresh');
						$("#ud").dialog("close");
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function disabledsale(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var saleid = row.SALEID;
		var $check = $('#disabledcheckin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updatesalecodebyid",
				saleid: saleid,
				noused: noused
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								NOUSED: noused
							}
						});
					}
					$dg.datagrid('refresh');
				} catch(e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
//删除
function delsale(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var saleid = row.SALEID;
		$.messager.confirm(dialog_title, '您确认要删除销售类型' + row.SALENAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delsalecodebyid",
						saleid: saleid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
								var total = Number($("#pp_total").html());
								if (!isNaN(total)) $("#pp_total").html(total - 1);
							}
						} catch(e) {
							// TODO: handle exception
							console.error(e);top.wrtiejsErrorLog(e);
						}
					}
				});
			}
		});
	}
}
</script>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>	
</body>
</html>