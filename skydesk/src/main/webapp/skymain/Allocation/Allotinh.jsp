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
<title>调拨入库</title>
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
<!-- 调拨入库单据列表 -->
<div id="toolbars" class="toolbar" >
<div class="toolbar_box1">
	<div class="fl">
	<a class="btn1" id="addnotebtn" href="javascript:void(0)" onclick="loadout()">添加</a>
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、店铺、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20"  data-enter="getnotedata()">
	<input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn" style="width:20%">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr hide">
	<input type="button" class="btn3"  value="导入" onclick="importxls()">
	<span class="sepreate"></span>
	<input type="button" class="btn3"  value="导出" onclick="exportxls()">
	</div>
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none;width:100%;clear:both;" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="sfromhouseid" id="sfromhouseid"> 
<input type="hidden" name="shouseid" id="shouseid"> 
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input type="text" name="smindate" id="smindate" placeholder="<输入>"  style="width: 162px;height: 29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>

<div class="s-box"><div class="title ">调入店铺</div>
<div class="text">
<input class="edithelpinput house_help" id="shousename" data-value="#shouseid" type="text"><span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>

<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput" id="swareno" type="text" data-value="#swareid"><span class="s-btn" onclick="selectware('search')"></span>
</div></div>
<div class="s-box"><div class="title">自编号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="shandno" id="shandno" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">摘要</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sreamrk" id="sremark" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">出库单号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="snoteno1" id="snoteno1" placeholder="---请输入---" >
</div>
</div>
<div class="s-box"><div class="title">制单人</div>
<div class="text"><input type="text" class="hig25 wid160" id="soperant" name="soperant" placeholder="<输入>" />
</div>
</div>
<div class="s-box">
<div class="title">单据状态</div>
	<div class="text">
	<label>
	<input type="radio" name="sstatetag" id="st3" value="2" checked="checked">所有
	</label>
	<label>
	 <input type="radio" name="sstatetag" id="st1" value="0">未提交
	</label>
	<label>
	<input type="radio" name="sstatetag" id="st2" value="1">已提交
	</label>
	</div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden;height:9999px"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 添加与修改调拨入库表单 -->
<div id="ud" title="修改调拨入库单" style="width: 100%;height:100%;" class="easyui-dialog" closed="true" data-qickey="">
	<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" id="pay" value="提交" onclick="updateallotinh()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="delete" value="删除单据" onclick="delallotinh()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="notecheckware" value="扫码验货" onclick="opennotecheckd()"></span>
	</span>
	<span class="sepreate"></span>
	<input type="button" class="btn4" value="导出明细" onclick="openexportdialog()">
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="print" value="后台打印"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="selprint" value="选择打印端口" onclick="selprint()"></span>
	<span id="isupdatedbar">
	<span class="sepreate" id="sep-withdraw"></span>
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="withdraw()"></span>	
	</span>

	<!-- 单据提交框 -->
	<div id="udnote">
	<form id="udnoteform" action="" method="get">
	<input type="hidden" id="uid"> <input type="hidden" id="untid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="ufromhouseid">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25" type="text" id="unoteno" readonly>
</div>
</div>
	<div class="s-box"><div class="title">日期</div>
	<div class="text">
	    <input id="unotedate" type="text" style="width: 142px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false,onShowPanel: setTodayDate,onHidePanel: changedate">
</div>
</div>
	<div class="s-box"><div class="title">调出店铺</div>
	<div class="text">
	<input class="hig25 wid160" type="text" id="ufromhousename" data-value="#ufromhouseid" value="" readonly>
</div>
</div>
	<div class="s-box"><div class="title">调入店铺</div>
	<div class="text">
	<input class="hig25 wid160" type="text" id="uhousename" data-value="#uhouseid" value="" readonly>
</div>
</div>
	<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" class="hig25 wid143" id="uhandno" placeholder="<输入>"  onchange="changehandno(this.value)">
</div>
</div>

	<div class="clearbox">
	<div class="title">摘要</div>
	<div class="text"><input maxlength="100" type="text" id="uremark" autocomplete="off" class="hig25 wid_remark" placeholder="<输入>" onchange="changeremark(this.value)">
	</div>
	</div>
	</div>
	</form>
	</div>
	<div class="warem-toolbars">
		<div class="fl" style="cursor: pointer;"><table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="warembar" onclick="updown()" style="cursor: pointer;">▲&nbsp;&nbsp;商品明细</td></tr>
		</table>
		</div>
	</div>
		</div>
	<!-- 商品明细表 -->
	<div>
	<table id="waret" class="table1"></table>
	<div id="warenametable" class="line30 mt10">
	<div class="fr mr20" align="right" id="wmtotalnote"></div>
	</div>
	</div>
</div>
<!-- 订单载入窗 -->
<div id="loadd" title="载入调拨出库单" data-options="modal:true" style="width: 1000px; height: 470px" class="easyui-dialog" closed="true">
<table id="loadt"></table>
<div class="dialog-footer">
<div id="loadpp"  class="tcdPageCode fl"></div>
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#loadd').dialog('close')">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="载入" onclick="loadnote();">
</div>
</div>
<script type="text/javascript">
var levelid = Number(getValuebyName("GLEVELID"));
var pgid = 'pg1403';
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		height: height - 45
	});
}
var sizenum = getValuebyName("SIZENUM");
var cols = Number(sizenum) <= 5 ? 5 : Number(sizenum);
cols = cols >= 15 ? 15 : cols;
function setColums() {
	var colums = [];
	colums.push({
		field: 'ROWNUMBER',
		title: '序号',
		fixed: true,
		width: 30,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			return value == "" ? "": index + 1;
		}
	},{
		field: 'WARENO',
		title: '货号',
		width: 60,
		fixed: true,
		halign: 'center',
		styler: function(value, row, index) {
			if (value == "合计") {
				return 'text-align:center';
			} else {
				return 'text-align:left';
			}
		}
	},{
		field: 'WARENAME',
		title: '商品名称',
		width: 120,
		fixed: true,
		halign: 'center',
		align: 'left'
	},{
		field: 'UNITS',
		title: '单位',
		width: 40,
		fixed: true,
		halign: 'center',
		align: 'center'
	},{
		field: 'COLORNAME',
		title: '颜色',
		width: 60,
		fixed: true,
		halign: 'center',
		align: 'center'
	},{
		field: 'SIZENAME',
		title: '尺码',
		expable: false,
		width: 60,
		fixed: true,
		hidden: true,
		halign: 'center',
		align: 'center'
	});
	for (var i = 1; i <= cols; i++) {
		colums.push({
			field: 'AMOUNT' + i,
			title: '<span id="title' + i + '"><span>',
			width: 35,
			fixed: true,
			fieldtype: "G",
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return value == '' ? '': Number(value) == 0 ? '': value;
			}
		});
	}
	colums.push({
		field: 'AMOUNT',
		title: '小计',
		width: 50,
		fixed: true,
		fieldtype: "G",
		halign: 'center',
		align: 'center'
	},{
		field: 'PRICE',
		title: '单价',
		fieldtype: "N",
		width: 70,
		fixed: true,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	},{
		field: 'CURR',
		fieldtype: "N",
		title: '金额',
		width: 80,
		fixed: true,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			return value == undefined ? '': (Number(value).toFixed(2));
		}
	});
	return colums;
}
var opser = 'get';
var notedateobj = {
	id: "#uid",
	noteno: "#unoteno",
	notedate: "#unotedate",
	action: "updateallotinhbyid"
}
$(function() {
	if (top.ztdcdnum > 0) $('#addnotebtn').addClass('newmsg');
	$('body').delegate('.datagrid', 'keydown',
	function(e) {
		var key = e.which;
		if (key == 113) {
			loadout();
		}
	});
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setaddbackprint("1403");
	setwarem();
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			//dqpage = p.toString();
			getnotelist(p);
		}
	});
	$('#gg').datagrid({
		idField: 'NOTENO',
		width: '100%',
		height: $('body').height() - 45,
		fitColumns: true,
		showFooter: true,
		striped: true,
		//隔行变色
		nowrap: false,
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			$('#udnoteform')[0].reset();
			$('#udnoteform input[type=hidden]').val('');
			$('#uhouseid').val(getValuebyName('HOUSEID'));
			$('#uhousename').val(getValuebyName('HOUSENAME'));
			if (rowData) {
				$('#uid').val(rowData.ID);
				$('#unoteno').val(rowData.NOTENO);
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				$('#ufromhouseid').val(rowData.FROMHOUSEID);
				//	$('#ufromhousename').val(rowData.FROMHOUSENAME);
				$('#uhandno').val(rowData.HANDNO);
				$('#utotalcurr').val(rowData.TOTALCURR);
				$('#utotalamt').val(rowData.TOTALAMT);
				$('#uoperant').val(rowData.OPERANT);
				$('#untid').val(rowData.NTID);
				$('#ustatetag').val(rowData.STATETAG);
				$('#uremark').val(rowData.REMARK);
				dqindex = rowIndex;
				dqamt = rowData.TOTALAMT;
				dqcurr = rowData.TOTALCURR;
			}
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatenoted();
		},
		columns: [[{
			field: 'ID',
			title: 'ID',
			width: 200,
			hidden: true
		},
		{
			field: 'CHECK',
			checkbox: true,
			hidden: true
		},
		{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			width: 125,
			fixed: true,
			align: 'center',
			halign: 'center',
			styler: function(value, row, index) {
				if (value == "合计") {
					return 'color:#ff7900;font-weight:700';
				}
				if (row.STATETAG == '1') {
					return 'color:#00959a;font-weight:900';
				} else {
					return 'color:#ff7900;font-weight:700';
				}
			}
		},
		{
			field: 'NOTENO1',
			title: '调出单据号',
			width: 110,
			fixed: true,
			hidden: true
		},
		{
			field: 'NOTEDATE',
			title: '单据日期',
			width: 112,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return row.NOTEDATE == undefined ? '': row.NOTEDATE.substring(0, 16);
			}
		},
		{
			field: 'HOSEID',
			title: '店铺id',
			width: 200,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'FROMHOUSENAME',
			title: '调出店铺',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return row.NOTENO1 == undefined ? value: value + "[" + row.NOTENO1 + "]";
			}
		},
		{
			field: 'HOUSENAME',
			title: '调入店铺',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'TOHOSEID',
			title: '店铺id',
			width: 100,
			hidden: true
		},
		{
			field: 'HANDNO',
			title: '自编号',
			width: 200,
			hidden: true
		},
		{
			field: 'TOTALCURR',
			title: '总金额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value).toFixed(2);
			}
		},
		{
			field: 'TOTALAMT',
			title: '总数量',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'REMARK',
			title: '摘要',
			width: 200,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'OPERANT',
			title: '制单人',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var stg = Number(row.STATETAG);
				var epname = getValuebyName("EPNAME");
				var operant = row.OPERANT;
				if (stg==0&&epname==operant) return '<input type="button" value="删除" class="m-btn" onclick="delnote(' + index + ')">';
				else return "";
			}
		},
		{
			field: 'NTID',
			title: 'NTID',
			width: 200,
			hidden: true
		},
		{
			field: 'STATETAG',
			title: '操作状态',
			width: 200,
			hidden: true
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid("keyCtr", "updatenoted()");
	getnotedata();
	if (levelid != 0 && levelid != 4 && levelid != 5) {
		$('#shouseid').val(getValuebyName('HOUSEID'));
		$('#shousename').val(getValuebyName("HOUSENAME"));
		$('#shousename').next().hide();
	}
});
//加载高级搜索栏
var searchb = false;
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		$('#searchbtn').hide();
		searchb = false;
	} else {
		$('#highsearch').val('高级搜索▲');
		$('#searchbtn').show();
		searchb = true;
	}
	$('.searchbar').slideToggle('fast');

}
//清空搜索条件
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	if (levelid != 0 && levelid != 4 && levelid != 5) {
		$('#shouseid').val(getValuebyName('HOUSEID'));
		$('#shousename').val(getValuebyName("HOUSENAME"));
		$('#shousename').next().hide();
	} else {
		$('#shouseid').val('');
		$('#shousename').val('');
	}
	$('#shandno,#sfromhouseid,#swareno,#swareid,#sremark,#snoteno1,#soperant').val('');
	$('#st3').prop('checked', true);
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "allotinhlist";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.noteno,a.noteno1,a.notedate,a.houseid,a.fromhouseid,a.ntid,a.remark,a.handno,a.totalcurr,a.totalamt,a.operant,a.checkman,b.housename,a.statetag";
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		async: false,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					dqzjl = data.total;
					dqzamt = data.totalamount;
					dqzcurr = data.totalcurr;
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					data.footer = [{
						ROWNUMBER: "合计",
						TOTALAMT: dqzamt,
						TOTALCURR: dqzcurr
					}];
					$dg.datagrid('loadData', data);
					if (data.total > 0) $('#gg').datagrid('scrollTo', 0);
					$('#pp_total').html(data.total);
					$('#qsnotevalue').focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getnotedata() {
	var value = $('#qsnotevalue').val();
	currentdata = {
		findbox: value,
		statetag: 2
	};
	getnotelist(1);
}
//高级搜索
function searchnote() {
	var houseid = Number($('#shouseid').val());
	var fromhouseid = Number($('#sfromhouseid').val());
	var handno = $('#shandno').val();
	var wareid = Number($('#swareid').val());
	var remark = $('#sremark').val();
	var operant = $('#soperant').val();
	var noteno1 = $('#snoteno1').val();
	var operant = $('#soperant').val();
	var statetag;
	if ($('#st1').prop('checked')) {
		statetag = "0";
	} else if ($('#st2').prop('checked')) {
		statetag = "1";
	} else {
		statetag = "2";
	}
	currentdata = {
		handno: handno,
		noteno1: noteno1,
		fromhouseid: fromhouseid,
		houseid: houseid,
		wareid: wareid,
		remark: remark,
		operant: operant,
		statetag: statetag
	};
	getnotelist(1);
}

//收缩展开
var d = false;
function updown() {
	$('#udnote').slideToggle('fast');
	if (!d) {
		$('#warembar').html('▼&nbsp;&nbsp;商品明细');
		d = true;
	} else {
		$('#warembar').html('▲&nbsp;&nbsp;商品明细');
		d = false;
	}
	setTimeout(function() {
		$('#waret').datagrid('resize', {
			height: $('body').height() - 90
		});
	},
	200);

}

//编辑单据
function updatenoted() {
	$('#udnote').show();
	$('#warembar').html('▲&nbsp;&nbsp;商品明细');
	setTimeout(function() {
		$('#waret').datagrid('resize', {
			height: $('body').height() - 90
		});
	},
	200);
	d = false;
	$('#waret').datagrid('loadData', {
		total: 0,
		rows: [],
		footer: [{
			"WARENO": "合计",
			"AMOUNT": 0,
			"CURR": 0,
			"ACTION": "noaction"
		}]
	});
	var rowData = $('#gg').datagrid('getSelected');
	if (rowData == null) {
		alerttext("请选择一行数据，进行编辑!");
		$('#updatebtn').remove('disabled');
	} else {
		getnotebyid($('#uid').val(), $('#unoteno').val());
	}
}
function quickaddwd() {
	$('#quickuaddwaref')[0].reset();
	$('#quickutable').html('');
	var fromhouseid = $('#ufromhouseid').val();
	if (fromhouseid == '0' || fromhouseid == '') {
		alerttext('请选择调出店铺');
	} else {
		$('#qucikud').dialog({
			modal: true
		});
		$('#quickud').dialog('open');
	}
	/*$('#div_waremx').hide();
	$('#div_waremx').slideToggle('fast');*/
}
var pg = 1;
var sumpg = 1;
var nextpg = true;
//加载商品明细表
function setwarem() {
	$('#waret').datagrid({
		idField: 'wareinm',
		height: $('body').height() - 90,
		toolbar: '#udtool',
		fitColumns: true,
		striped: true,
		//隔行变色
		showFooter: true,
		nowrap: false,
		//允许自动换行
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			if (rowData) {
				for (var i = 1; i <= cols; i++) {
					$('#title' + i).html(eval('rowData.SIZENAME' + i));
				}
			}
		},
		onLoadSuccess: function(data) {
			var sizelength = 0;
			var rows = data.rows;
			var $dg = $('#waret');
			for (var i = 0; i < rows.length; i++) {
				var item = rows[i];
				while (sizelength <= sizenum) {
					var s = sizelength + 1;
					if (item["SIZENAME" + s] != "" && item["SIZENAME" + s] != undefined) sizelength++;
					else break;
				}
			}
			for (var i = 1; i <= sizenum; i++) {
				if (i <= sizelength) $dg.datagrid("showColumn", "AMOUNT" + i);
				else $dg.datagrid("hideColumn", "AMOUNT" + i);
			}
			$dg.datagrid('resize');
		},
		onDblClickRow: function(rowIndex, rowData) {

},
		columns: [setColums()],
		pageSize: 20
	}).datagrid("keyCtr", "");
	var scrollfn = function(obj) {
		var $this = $(obj);
		var viewH = $this.height(); //可见高度
		var contentH = $this.get(0).scrollHeight; //内容高度
		var scrollTop = $this.scrollTop(); //滚动高度
		var noscroll = sumpg > pg && nextpg&&contentH==viewH&&scrollTop==0; //没有滚动跳的时候加载
		var hasscroll = contentH > (viewH + 1) && contentH - viewH - scrollTop <= 3 && $("#waret").datagrid("getRows").length > 0;//有滚动跳的时候加载
		if (noscroll||hasscroll) { //到达底部时,加载新内容
			if ($('#udnote').css('display') != "none") {
				$('#udnote').hide();
				$('#warembar').html('▼&nbsp;&nbsp;商品明细');
				setTimeout(function() {
					$('#waret').datagrid('resize', {
						height: $('body').height() - 90
					});
				},
				200);
				d = true;
			}
			if (sumpg > pg && nextpg) {
				nextpg = false;
				getnotewarem($('#unoteno').val(), pg + 1);
				nextpg = true;
			}
		}
	};
	$('#waret').prev().children('.datagrid-body').bind('mousewheel DOMMouseScroll', function(event, delta, deltaX, deltaY) {
	    scrollfn(this);
	});
}
//获取商品明细单据及分页
function getnotewarem(noteno, page) {
	alertmsg(6);
	if (page == 1) $('#waret').datagrid('loadData', nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "allotinmcolorsumlist",
			sizenum: sizenum,
			noteno: noteno,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var totals = data.total;
				var totalamt = data.totalamt;
				var totalcurr = data.totalcurr;
				var retailcurr = data.retailcurr;
				data.footer = [{
					WARENO: "合计",
					AMOUNT: totalamt,
					CURR: totalcurr,
					RETAILCURR: retailcurr
				}];
				sumpg = Math.ceil(Number(totals) / pagecount);
				$('#warepp').setPage({
					pageCount: sumpg,
					current: Number(page)
				});
				$('#wtotalamt').val(totalamt);
				$('#wtotalcurr').val(Number(totalcurr).toFixed(2));
				if (page == 1) {
					pg = 1;
					$('#waret').datagrid('loadData', data);
					if ($('#waret').datagrid('getRows').length > 0) {
						$('#waret').datagrid('selectRow', 0);
						$('#waret').datagrid('scrollTo', 0);
					}
				} else {
					pg++;
					var rows = data.rows;
					for (var i in rows) {
						$('#waret').datagrid('appendRow', rows[i]);
					}
				}
				$('#wmtotalnote').html('已显示' + $('#waret').datagrid('getRows').length + '条记录/共有' + totals + '条记录');
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						TOTALAMT: totalamt,
						TOTALCURR: totalcurr
					}
				});
				$('#gg').datagrid('updateFooter', {
					TOTALAMT: Number(dqzamt) - Number(dqamt) + Number(totalamt),
					TOTALCURR: Number(dqzcurr) - Number(dqcurr) + Number(totalcurr)
				});
				dqzamt = Number(dqzamt) - Number(dqamt) + Number(totalamt);
				dqzcurr = Number(dqzcurr) - Number(dqcurr) + Number(totalcurr);
			}
		}
	});
}

//添加生成调拨入库单
function loadnote() {
	var rowData = $('#loadt').datagrid('getSelected');
	if (rowData) {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addallotinh",
				fromnoteno: rowData.NOTENO
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						$('#uid').val(data.ID);
						$('#unoteno').val(data.NOTENO);
						$('#ufromhousename').val(rowData.HOUSENAME + "[" + data.NOTENO + "]");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
						changedatebj = false;
						$('#unotedate').datetimebox('setValue', data.NOTEDATE);
						changedatebj = true;
						$('#uhandno').val('');
						$("#qsnotevalue").val("");
						$("#smaxdate").val("setValue",top.getservertime());
						dqindex = 0;
						dqamt = rowData.TOTALAMT;
						dqcurr = rowData.TOTALCURR;
						getnotedata();
						getnotewarem(data.NOTENO, 1);
						$('#uhandno,#uremark').removeAttr('readonly');
						$('#updateallotinh,#del,#updatingbar,#udnote').show();
						$('#ud #isupdatedbar').hide();
						$('#warembar').html('▲&nbsp;&nbsp;商品明细');
						setTimeout(function() {
							$('#waret').datagrid('resize', {
								height: $('body').height() - 90
							});
						},
						200);
						d = false;
						$("#ud").dialog("setTitle", "添加调拨入库单");
						$('#uhouseid').val(rowData.TOHOUSEID);
						$('#ufromhouseid').val(rowData.HOUSEID);
						$('#uhousename').val(rowData.TOHOUSENAME);
						$('#uhandno').val(rowData.HANDNO);
						$('#uremark').val(rowData.REMARK);
						$('#untid').val(rowData.NTID);
						$('#loadd').dialog('close');
						$('#ud').dialog('open');
						if ($('#loadt').datagrid('getRows').length == 1) {
							$('#addnotebtn').removeClass('newmsg');
							$(top.$("#iframe_home"))[0].contentWindow.getskymsg();
						}
					}
				} catch(e) {
					console.error(e);
				}
			}
		});
	} else {
		alerttext('请选择在途单据');
	}
}
function delnote(index) {
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	$('#uid').val(row.ID);
	$('#unoteno').val(row.NOTENO);
	delallotinh();
}
//删除单据
function delallotinh() {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	$.messager.confirm(dialog_title, '删除单据后不再恢复，确定删除吗？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delallotinhbyid",
					noteno: noteno,
					id: id
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						$("#ud").dialog('close');
						$('#gg').datagrid('deleteRow', dqindex);
						$('#gg').datagrid('refresh');
						$('#gg').datagrid('updateFooter', {
							TOTALAMT: Number(dqzamt) - Number(dqamt),
							TOTALCURR: Number(dqzcurr) - Number(dqcurr)
						});
						$('#pp_total').html(dqzjl - 1);
						dqzamt = Number(dqzamt) - Number(dqamt);
						dqzcurr = Number(dqzcurr) - Number(dqcurr);
						dqzjl = dqzjl - 1;
					}
				}
			});
		}
	});
}

//修改调拨入库单
function updateallotinh() {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	var houseid = $('#uhouseid').val();
	var fromhouseid = $('#ufromhouseid').val();
	var statetag = $('#ustatetag').val();
	var totalcurr = $('#wtotalcurr').val();
	var totalamt = $('#wtotalamt').val();
	var notedatestr = $('#unotedate').datetimebox('getValue');
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
	}
	var amt = $('#waret').datagrid('getRows').length;
	if (amt == 0) {
		alert('商品明细不能为空！');
	} else {
		$.messager.confirm(dialog_title, '单据提交后禁止修改及删除，是否继续提交？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "updateallotinhbyid",
						noteno: noteno,
						houseid: houseid,
						fromhouseid: fromhouseid,
						statetag: "1",
						totalcurr: totalcurr,
						notedatestr: notedatestr,
						totalamt: totalamt
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							$('#ud').dialog('close');
							$('#gg').datagrid('updateRow', {
								index: dqindex,
								row: {
									STATETAG: '1'
								}
							});
							$('#gg').datagrid('refresh');
						}
					}
				});
			}
		});
	}
}

//改变自编号
function changehandno(handno) {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updateallotinhbyid",
			id: id,
			noteno: noteno,
			handno: handno
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						HANDNO: handno
					}
				});
			}
		}
	});
}
//摘要
function changeremark(remark) {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updateallotinhbyid",
			id: id,
			noteno: noteno,
			remark: remark
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						REMARK: remark
					}
				});
			}
		}
	})
}
//选择店铺
//撤单
function withdraw() {
	var istoday = $('#uistoday').val();
	var noteno = $('#unoteno').val();
	var id = $('#uid').val();
	if (Number(istoday) == 1 || allowchangdate == 1) {
		$.messager.confirm(dialog_title, '您确定撤单吗？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "allotinhcancel",
						noteno: noteno,
						id: id
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							var rows = $('#gg').datagrid('getRows');
							var row = rows[dqindex];
							if (row.OPERANT == getValuebyName("EPNAME")) {
								$('#gg').datagrid('updateRow', {
									index: dqindex,
									row: {
										STATETAG: '0'
									}
								});
							} else {
								$('#gg').datagrid('deleteRow', dqindex);
								dqzamt = Number(dqzamt) - Number(row.TOTALAMT);
								dqzcurr = Number(dqzcurr) - Number(row.TOTALCURR);
								dqzjl = dqzjl - 1;
								$('#gg').datagrid('updateFooter', {
									TOTALAMT: dqzamt,
									TOTALCURR: dqzcurr
								});
								$('#pp_total').html(dqzjl - 1);
							}
							$('#ud').dialog("close");
							$('#gg').datagrid('refresh');
						}
					}
				});
			}
		});
	} else {
		alerttext('本单据不是当天单据，撤单只对当天单据有效！');
	}
}
var loaddatagrid = false;
//载入调拨出库表
function loadout() {
	if (loaddatagrid == false) {
		$('#loadpp').createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				allotorderhload(p.toString());
			}
		});
		$('#loadt').datagrid({
			idField: 'NOTENO',
			width: '100%',
			height: '380px',
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			onDblClickRow: function(rowIndex, rowData) {
				loadnote();
			},
			columns: [[{
				field: 'ROWNUMBER',
				title: '序号',
				width: 30,
				fixed: true,
				align: 'center',
				formatter: function(value, row, index) {
					return index + 1 + ($('#loadpp').getPage() - 1) * pagecount;
				}
			},
			{
				field: 'NOTENO',
				title: '单据号',
				width: 125,
				fixed: true,
				haign: 'center',
				align: 'left'
			},
			{
				field: 'NOTEDATE',
				title: '单据日期',
				width: 112,
				fixed: true,
				haign: 'center',
				align: 'left',
				formatter: function(value, row, index) {
					return row.NOTEDATE == undefined ? '': row.NOTEDATE.substring(0, 16);
				}
			},
			{
				field: 'HOUSENAME',
				title: '调出店铺',
				width: 200,
				haign: 'center',
				align: 'center'
			},
			{
				field: 'TOHOUSENAME',
				title: '调入店铺',
				width: 200,
				haign: 'center',
				align: 'center'
			},
			{
				field: 'TOTALCURR',
				title: '总金额',
				width: 100,
				fixed: true,
				haign: 'center',
				align: 'right',
				formatter: function(value, row, index) {
					return Number(value).toFixed(2);
				}
			},
			{
				field: 'TOTALAMT',
				title: '总数量',
				width: 100,
				fixed: true,
				haign: 'center',
				align: 'right',
				formatter: function(value, row, index) {
					return Number(value).toFixed(0);
				}
			},
			{
				field: 'HANDNO',
				title: '自编号',
				width: 300,
				align: 'left',
				halign: 'center',
				hidden: true
			},
			{
				field: 'REMARK',
				title: '摘要',
				width: 300,
				align: 'left',
				halign: 'center'
			},
			{
				field: 'OPERANT',
				title: '制单人',
				width: 100,
				fixed: true
			}]],
			pageSize: 10
		}).datagrid('keyCtr', "loadnote()");
		loaddatagrid = true;
	}
	allotorderhload("1");
	$('#loadd').dialog('open');
}
//载入调拨出库数据
function allotorderhload(page) {
	alertmsg(6);
	var tohouseid = Number(getValuebyName("HOUSEID"));
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "allotouthonlist",
			fieldlist: "a.id,a.accid,a.noteno,a.notedate,a.tohouseid,a.houseid,b.housename,a.handno,a.totalcurr,a.totalamt,a.operant,a.checkman,a.statetag",
			tohouseid: tohouseid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var totals = data.total;
				var totalamt = data.totalamount;
				var totalcurr = data.totalcurr;
				$('#loadpp').setPage({
					pageCount: Math.ceil(Number(totals) / pagecount),
					current: Number(page)
				});
				$('#loadt').datagrid('loadData', data);
			}
		}
	});
}
//获取是否为当天单据
function getnotebyid(id, noteno) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getallotinhbyid",
			fieldlist: "a.id,a.noteno,a.notedate,a.houseid,a.fromhouseid,a.ntid,a.noteno1,a.remark,a.handno,a.operant,a.checkman,a.statetag,b.housename",
			noteno: noteno,
			id: id
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var rows = data.rows;
				if (data.total > 0) {
					var row = rows[0];
					var stg = Number(row.STATETAG);
					var epname = getValuebyName("EPNAME");
					var operant = row.OPERANT;
					if (stg==1||epname!=operant) {
						$('#ud').dialog("setTitle", "浏览调拨入库单");
						$('#uhandno').attr('readonly', 'readonly');
						$('#uselecthouseb').attr('disabled', 'disabled');
						$('#updatingbar,#wquickuwaretd,#updatingbar').hide();
						$('#uremark').attr('readonly', 'readonly');
						$('#uhousename').attr('readonly', 'readonly');
						$('#uhousename').next().hide();
						if (levelid == 0 || allowchedan == 1) {
							$('#ud').data('qickey', "F9:'$(\"#print\").click()',F10:'withdraw()'");
						} else {
							$('#ud').data('qickey', "F9:'$(\"#print\").click()'");
						}
						//$('#waret').datagrid('hideColumn','ACTION');
						$('#unoteno').focus();
						$('#unotedate').datetimebox({
							readonly: true,
							hasDownArrow: false
						});
					} else {
						$('#ud').dialog("setTitle", "修改调拨入库单");
						$('#uselecthouseb').removeAttr('disabled');
						$('#updatingbar,#wquickuwaretd').show();
						$('#isupdatedbar').hide();
						$('#wquickuwareno').val('');
						$('#uhandno,#uremark').removeAttr('readonly');
						$('#ud').data('qickey', "F4:'updateallotinh()',Del:'delallotinh()'");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
					}
					$('#ud').dialog('open');
					$('#unoteno').focus();
					getnotewarem($('#unoteno').val(), "1");
					var istoday = row.ISTODAY;
					var fromhouse = row.FROMHOUSENAME + "[" + row.NOTENO1 + "]";
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					$('#ufromhousename').val(fromhouse);
					$('#uistoday').val(istoday);
					if ((Number(istoday) == 1 || allowchangdate == 1) && allowchedan == '1' && stg > 0) {
						$('#isupdatedbar').show();
					} else {
						$('#isupdatedbar').hide();
					}
				}
			}
		}
	});
}
</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
	<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/NoteCheckHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/ExportHelp.jsp"></jsp:include>
</body>
</html>