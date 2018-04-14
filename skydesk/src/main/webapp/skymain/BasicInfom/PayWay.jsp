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
<title>结算方式</title>
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
	<input class="btn1" type="button" value="添加" onclick="addpayd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatepayd()">
	<input type="text" data-end="yes" placeholder="输入结算方式或简称<回车搜索>" class="ipt1" id="qspayvalue" maxlength="20" data-enter="getpaydata()">
<!-- 	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchpay();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr">
	<input type="button" class="btn3 hide" style="width: 45px" value="导入" onclick="openimportd()"> 
	<input type="button" class="btn1" value="开通微信和支付宝" onclick="createMerchant()"> 
	<span class="sepreate hide"></span>
	<input type="button" class="btn3 hide" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchpay();toggle();">
	<input type="hidden" id="spayid">
	<div class="search-box">
	<div class="s-box">
		<div class="title">结算方式</div>
		<div class="text"><input class="wid160 hig25" id="spayname" maxlength="20" type="text" placeholder="<输入>"></div>
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
<!-- 修改结算方式窗 -->
<div id="ud" title="<span id='udtitle'>编辑结算方式</span>" style="width: 350px; height: 200px;" class="easyui-dialog" closed=true>
	<div style="margin-top: 10px; text-align: center;">
	<table class="table1" cellspacing="10">
	<tr>
	<td class="header skyrequied" align="right">
	结算方式
	</td>
	<td>
	<input type="hidden" id="upayid">
	<input type="hidden" id="uindex">
	<input class="hig25 wid160" type="text" placeholder="<输入>" name="upayname" id="upayname" maxlength="20" /> 
	</td>
	</tr>
	<tr align="right">
	<td class="header skyrequied">结算代号</td>
	<td>
	<select name="upayno" id="upayno" class="sele1">
	</select>
	</td>
	</tr>
	</table>
	</div>
	<div class="dialog-footer textcenter">
		<label class="updateshow"><input type="checkbox" name="unoused" id="unoused">禁用</label>
		<input type="button" id="savebtn" class="btn1"  name="updatepay" value="保存" onclick="savepay()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//权限设置

setqxpublic();
var pgid= "pg1011";
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
	$('#spayname').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st1').prop('checked', true);
	var idstr = "#spayname";
	$(idstr).val('');
}
//添加框
function addpayd() {
	getpayno();
	$("#udtitle").html("添加结算方式");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$('#upayname,#upayid,#uindex').val("");
	$('#upayno').removeProp("disabled");
	$(".updateshow").hide();
	$('#upayname').focus();
}
//编辑框
function updatepayd() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var noused = row.NOUSED;
		var payno = row.PAYNO;
		if (payno > "O" || payno == '0') {
			alert("系统约定结算方式，不允许编辑！");
			return;
		}
		$("#udtitle").html("编辑结算方式");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#upayid').val(row.PAYID);
		$('#upayname').val(row.PAYNAME);
		$('#upayno').prop("disabled",true);
		$('#upayno').html("<option>"+row.PAYNO+"</option>");
		$('#ud').dialog('open');
		$('#upayname').focus();
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
			getpaywaylist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'PAYID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "PAYNAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'PAYID',
			title: '结算方式ID',
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
			field: 'PAYNAME',
			title: '结算方式',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'center'
		},
		{
			field: 'PAYNO',
			title: '结算代号',
			width: 100,
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
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledpay(' + index + ')" ' + checked + '>';
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
				var payno = row.PAYNO;
				if (payno > "O" || payno == '0') {
					return "";
				} else {
					return '<input type="button" class="m-btn" value="删除" onclick="delpay('+index+')">';
				}
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatepayd();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatepayd()");
	getpaydata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendpay",
		xlsmobanname: "paycode",
		channel: "paycode"
	});
});
//获取 支付代号
function getpayno() {
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "paynolist"
		}, //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					$('#upayno').html('');
					if(data.total==1){
						var rows = data.rows;
						var row = rows[0];
						var charslist = row.CHARSLIST.split(",");
						for(var i=0;i<charslist.length;i++){
							$('#upayno').append("<option>"+charslist[i]+"</option>");
						}
					}
				}
			} catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
var currentdata = {};
var getpaywaylist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	currentdata["erpser"] = "paywaylist";
	currentdata["noused"] = 2;
	currentdata["rows"] = pagecount;
	currentdata["fieldlist"] = "payid,payname,payno,noused";
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
function getpaydata() {
	var value = $('#qspayvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getpaywaylist(1);
}
//搜索结算方式
function searchpay() {
	var payname = $('#spayname').val();
	var noused = 0;
	if ($('#st1').prop('checked')) noused = "0";
	else if ($('#st2').prop('checked')) noused = "1";
	else noused = "2";
	alertmsg(6);
	currentdata = {
		payname: payname,
		noused: noused
	};
	getpaywaylist(1);
}
//添加结算方式
function savepay() {
	var index = $("#uindex").val();
	var payid = Number($("#upayid").val());
	var payname = $('#upayname').val();
	var payno = $('#upayno').val();
	if (payname == "") {
		alerttext("结算方式名不能为空");
		return;
	}
	var erpser = "addpayway";
	var noused = 0;
	if (payid > 0){
		erpser = "updatepaywaybyid";
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
			payid: payid,
			payno: payno,
			payname: payname,
			noused: noused
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					getpayno();
					var $dg = $("#gg");
					if (payid == 0) {
						payid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								PAYID: payid,
								PAYNO: payno,
								PAYNAME: payname,
								NOUSED: noused
							}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						$('#upayname').val("").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								PAYID: payid,
								PAYNAME: payname,
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
function disabledpay(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var payid = row.PAYID;
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
				erpser: "updatepaywaybyid",
				payid: payid,
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
function delpay(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var payid = row.PAYID;
		$.messager.confirm(dialog_title, '您确认要删除结算方式' + row.PAYNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delpaywaybyid",
						payid: payid
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
function createMerchant(){
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getaccregpay",
			accid: getValuebyName("ACCID"),
			fieldlist: "a.merchantno,a.statetag"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if(data.total>0){
						var row = data.rows[0];
						var stg = Number(row.STATETAG);
						if(stg==0){
							alerttext("您的开通申请正在审核！");
						}else{
							alerttext("您的开通申请已经审核通过，无需重复申请！");
						}
					}else{
						top.noconpg.createMerchant =  "/fypay?accid="+getValuebyName("ACCID")+"&accnotime="+new Date().getTime();
						top.addTabs({
							id : "createMerchant",
							title :"开通快速收款",
							close : true
						});
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
</script>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>