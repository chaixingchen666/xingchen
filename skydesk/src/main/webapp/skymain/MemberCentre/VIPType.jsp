<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>会员类型</title>
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
	<input class="btn1" type="button" value="添加" onclick="addvtd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatevtd()">
	<input type="text" data-end="yes" placeholder="输入会员类型名称或简称<回车搜索>" class="ipt1" id="qsvtvalue" maxlength="20" data-enter="getvtdata()">
<!-- 	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchvt();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr hide">
	<input type="button" class="btn3" style="width: 45px" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchvt();toggle();">
	<input type="hidden" id="svtid">
	<div class="search-box">
	<div class="s-box">
		<div class="title">会员类型名称</div>
		<div class="text"><input class="wid160 hig25" id="svtname" maxlength="20" type="text" placeholder="<输入>"></div>
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
<!-- 修改会员类型窗 -->
<div id="ud" title="<span id='udtitle'>编辑会员类型</span>" style="width: 350px; height: 260px;" class="easyui-dialog" closed=true>
	<div style="text-align: center;">
	<table class="table1" cellspacing="10">
	<tr>
	<td class="header skyrequied" align="right">
	会员类型
	</td>
	<td>
	<input type="hidden" id="uvtid">
	<input type="hidden" id="uindex">
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="uvtname" id="uvtname" maxlength="20" /> 
	</td>
	</tr>
	<tr>
	<td class="header skyrequied" align="right">
	折扣
	</td>
	<td>
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="udiscount" id="udiscount" maxlength="20" /> 
	</td>
	</tr>
	<tr>
	<td class="header skyrequied" align="right">
	积分比例
	</td>
	<td>
	<input class="onlyNum" type="text" style="width: 112px; height: 25px" id="ucentrate" name="ucentrate" />元积一分
	</td>
	</tr>
	</table>
	</div>
	<div class="dialog-footer textcenter">
		<label class="updateshow"><input type="checkbox" name="unoused" id="unoused">禁用</label>
		<label class="fl-ml30 updateshow"><input type="checkbox" name="udefbj" id="udefbj">默认为线上会员类型</label>
		<input type="button" id="savebtn" class="btn1"  name="updatevt" value="保存" onclick="savevt()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//权限设置
setqxpublic();
var pgid= "pg1805";
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
	$('#svtname').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st3').prop('checked', true);
	var idstr = "#svtname";
	$(idstr).val('');
}
//添加框
function addvtd() {
	$("#udtitle").html("添加会员类型");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$('#uvtname,#uvtid,#uindex,#udiscount,#ucentrate').val("");
	$(".updateshow").hide();
	$('#uvtname').focus();
}
//编辑框
function updatevtd() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var noused = row.NOUSED;
		var defbj = row.DEFBJ;
		$("#udtitle").html("编辑会员类型");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#ud').dialog('open');
		$('#uvtname').focus();
		$('#uvtname').val(row.VTNAME);
		$('#udiscount').val(Number(row.DISCOUNT).toFixed(2));
		$('#ucentrate').val(row.CENTRATE);
		$('#uvtid').val(row.VTID);
		if (noused != 0) {
			$('#unoused').prop('checked', true);
		} else {
			$('#unoused').removeProp('checked');
		}
		if (defbj != 0) {
			$('#udefbj').prop('checked', true);
		} else {
			$('#udefbj').removeProp('checked');
		}
	}
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getvtlist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'VTID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "VTNAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'VTID',
			title: '会员类型ID',
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
			field: 'VTNAME',
			title: '会员类型',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'DISCOUNT',
			title: '会员折扣',
			width: 100,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center',
			formatter: currfm
		},{
			field: 'CENTRATE',
			title: '积分比例',
			width: 150,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center',
			formatter: function(value,index,row){
				var val = Number(value);
				if(val==0)
					return "消费不积分";
				return "消费满"+val+"元积一分";
			}
		},{
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
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledvt(' + index + ')" ' + checked + '>';
			}
		},{
			field: 'ACTION',
			title: '操作',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" value="删除" onclick="delvt(' + index + ')">';
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatevtd();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatevtd()");
	getvtdata();
});

var currentdata = {};
var getvtlist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "guesttypelist";
	currentdata["fieldlist"] = "vtid,vtname,discount,noused,defbj,centrate";
	currentdata["sort"] = "vtname";
	currentdata["order"] = "asc";
	currentdata["noused"] = 2;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
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
function getvtdata() {
	var value = $('#qsvtvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getvtlist(1);
}
//搜索会员类型
function searchvt() {
	var vtname = $('#svtname').val();
	var noused = 0;
	if ($('#st1').prop('checked')) noused = "0";
	else if ($('#st2').prop('checked')) noused = "1";
	else noused = "2";
	alertmsg(6);
	currentdata = {
		vtname: vtname,
		noused: noused
	};
	getvtlist(1);
}
//添加会员类型
function savevt() {
	var index = $("#uindex").val();
	var vtid = Number($("#uvtid").val());
	var vtname = $('#uvtname').val();
	var discount = $('#udiscount').val();
	var centrate = $('#ucentrate').val();
	if (vtname == "") {
		alerttext("会员类型名不能为空");
		return;
	}
	var erpser = "addguesttype";
	var noused = 0;
	if (vtid > 0){
		erpser = "updateguesttypebyid";
		noused = $("#unoused").prop("checked") ? 1 : 0;
	}
	var defbj = 0;
	defbj = $("#udefbj").prop("checked") ? 1 : 0;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			vtid: vtid,
			vtname : vtname,
			centrate: centrate,
			defbj: defbj,
			discount: discount,
			noused: noused
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var $dg = $("#gg");
					if (vtid == 0) {
						vtid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								VTID: vtid,
								VTNAME : vtname,
								CENTRATE: centrate,
								DEFBJ: defbj,
								DISCOUNT: discount,
								NOUSED: noused
							}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						$('#uvtname').val("").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								VTID: vtid,
								VTNAME : vtname,
								CENTRATE: centrate,
								DEFBJ: defbj,
								DISCOUNT: discount,
								NOUSED: noused
							}
						}).datagrid('refresh');
						$("#ud").dialog("close");
					}
					if(defbj==1) $("#pp").refreshPage();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function disabledvt(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var vtid = row.VTID;
		var $check = $('#disabledcheckin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fybuyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateguesttypebyid",
				vtid: vtid,
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
function delvt(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var vtid = row.VTID;
		$.messager.confirm(dialog_title, '您确认要删除会员类型' + row.VTNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delguesttypebyid",
						vtid: vtid
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
</body>
</html>