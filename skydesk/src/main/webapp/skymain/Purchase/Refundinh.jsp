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
<title>采购退货</title>
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
<body class="easyui-layout layout panel-noscroll" >
	<!-- 采购退库单据列表 -->
	<!-- 表格工具栏 -->
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addnoted()"><input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()"><input 
	type="text" placeholder="搜索单据号、供应商、摘要" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20" data-enter="getnotedata()"><input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn" style="width:20%"><input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
	</div>
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="sprovid" id="sprovid" value=""> 
<input type="hidden" name="shouseid" id="shouseid" value=""> 
<div class="fl">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input type="text" name="smindate" id="smindate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;店铺</div>
<div class="text">
<input class="edithelpinput house_help" id="shousename" type="text" data-value="#shouseid"><span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>
<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;供应商</div>
<div class="text">
<input class="edithelpinput provd_help" id="sprovname" type="text" data-value="#sprovid"><span class="s-btn" onclick="selectprov('search')"></span>
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
</div>
<table id="gg" style="overflow: hidden;height:500px"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 添加与修改采购退库表单 -->
<div id="ud" title="编辑采购退库单" style="width:100%;height:100%;" class="easyui-dialog" closed="true" data-qickey>
	<div id="udtool" >
	<span id="updatingbar">
	<span><input class="btn4" type="button" data-btn="updatebtn" id="pay" value="提交" onclick="updaterefundinh()"></span>
	<span class="sepreate"></span>
<!-- 	<span><input class="btn4" type="button" id="load" value="载入(F7)" onclick="loadcg($('#uprovid').val())"></span> -->
<!-- 	<span class="sepreate"></span> -->
	<span><input class="btn4" type="button" id="delete" value="删除单据" onclick="delrefundinh()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="discount" value="整单打折" onclick="opendisc()"></span>
	</span>
	<span id="isupdatedbar">
	<span class="sepreate" id="sep-withdraw"></span>
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="withdraw()"></span>	
	<span id="clearnotebar">
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="clearbtn" value="冲单" onclick="clearnote(1,$('#unoteno').val())"></span>
	</span>
	<span><input class="btn4" type="button" id="print" value="后台打印"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="selprint" value="选择打印端口" onclick="selprint()"></span>
	</span>
<span class="sepreate"></span>
	<input type="button" class="btn4" value="导出明细" onclick="openexportdialog()">
	<span id="copynotebar">
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="copybtn" value="复制单据" onclick="setfromnoteno($('#unoteno').val())"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="pastebtn" value="粘贴单据" onclick="copynote(1,$('#unoteno').val())"></span>
	</span>
	<!-- 单据提交框 -->
	<div id="udnote">
	<form id="udnoteform" action="" method="get">
	<input type="hidden" id="uid">
	<input type="hidden" id="untid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="uprovid">
	<input type="hidden" id="udiscount"> 
	<input type="hidden" id="upricetype">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
	<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25 wid160" type="text" id="unoteno" readonly>
	</div>
	</div>
	<div class="s-box"><div class="title">日期</div>
	<div class="text">
		  <input id="unotedate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false,onShowPanel: setTodayDate,onHidePanel: changedate">
	</div>
	</div>
	<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;店铺</div>
	<div class="text"><input class="edithelpinput wid145 house_help" type="text" id="uhousename" data-value="changenotehouse" ><span onclick="selecthouse('update')"></span>
	</div>
	</div>
	<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;供应商</div>
	<div class="text"><input class="wid188 edithelpinput provd_help" type="text" data-value="changenoteprov" id="uprovname" placeholder="---请选择---" >
	<span id="selprovspan" onclick="selectprov('update')" ></span>
	</div>
	</div>
	<div class="s-box"><div class="title">费用</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="edithelpinput" id="usaletotalcost" value="0" readonly><span onclick="selectsalecost()"></span>
</div>
</div>
	<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" class="hig25 wid162" id="uhandno" placeholder="<输入>"  onchange="changehandno(this.value)">
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
		<tr><td id="warembar" onclick="updown()" style="color:#ff7900">▲&nbsp;&nbsp;商品明细</td><td id="wquickuwaretd"><input type="text" id="wquickuwareno" data-value="quickaddwarem"  data-end="yes" class="edithelpinput wid145" placeholder="---选择或输入---" list="wareno_list"><span onclick="if(isAddWarem()){selectware('quickupdatew')}"></span></td>
		</tr>
		</table>
		<input type="hidden" id="wquickuwareid" >
		<datalist id="wareno_list">	
		</datalist>
		</div>
			<div class="fr" id="warem-toolbar">
			<span><input class="btn5" type="button" id="barcodeware" value="＋扫码增加" onclick="openbarcodeadd()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="updateware" value="〼编辑" onclick="updatewd()"></span>
				<span class="seprate1"></span>
				<span><input class="btn5" type="button" id="delware" value="-删除行" onclick="delwaremx()"></span>
			</div>
			
			<div class="fr div_sort">
			<table>
			<tr>
			<td>
			<label onclick="getnotewarem($('#unoteno').val(), '1');">
			<input type="checkbox" id="waremsort" checked>录入排序
			</label>
			</td>
			<td>
			<label class="label_sort icon_jiantou_up">
			</label>
			</td>
			</tr>
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
<!-- 整单打折框 -->
<div id="discd" title="整单打折" style="width: 350px; height: 200px" class="easyui-dialog" closed="true">
	<div style="margin-left: 40px;margin-top: 30px">
	<p>请输入折扣</p>
	<p><input class="hig25" id="alldiscount" type="text" placeholder="---请输入折扣---" data-enter="alldisc()"/></p>
	</div>
	<div class="dialog-footer">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="保存" onclick="alldisc()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#discd').dialog('close')">
	</div>
	</div>
<!-- 订单载入窗 -->
<div id="loadd" title="载入采购订单" style="width: 740px; height: 370px" class="easyui-dialog" closed="true">
<table id="loadt"></table>
<div class="dialog-footer">
<div id="loadpp"  class="tcdPageCode fl"></div>
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="载入" onclick="loadnote()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#loadd').dialog('close')">
</div>
</div>
<script type="text/javascript">
var levelid = getValuebyName("GLEVELID");
var pgid = 'pg1103';
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
		width: 40,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			if(isNaN(Number(value))&&value!=undefined&&value.length>0)
				return value;
			return index + 1;
		}
	}, {
		field: 'WARENO',
		title: '货号',
		width: 60,
		fixed: true,
		halign: 'center'
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
	});;
	for (var i = 1; i <= cols; i++) {
		colums.push({
			field: 'AMOUNT' + i,
			title: '<span id="title' + i + '"><span>',
// 			expable: false,
			fieldtype: "G",
			width: 35,
			fixed: true,
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
		fieldtype: "G",
		fixed: true,
		halign: 'center',
		align: 'center'
	},{
		field: 'PRICE0',
		title: '原价',
		fieldtype: "n",
		width: 70,
		fixed: true,
		halign: 'center',
		align: 'right',
		hidden: allowinsale == 1 ? false : true,
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	},{
		field: 'DISCOUNT',
		title: '折扣',
		fieldtype: "N",
		width: 40,
		fixed: true,
		halign: 'center',
		align: 'right',
		hidden: allowinsale == 1 ? false : true,
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	},{
		field: 'PRICE',
		title: '单价',
		fieldtype: "N",
		width: 70,
		fixed: true,
		halign: 'center',
		align: 'right',
		hidden: allowinsale == 1 ? false : true,
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	},{
		field: 'CURR',
		title: '金额',
		fieldtype: "N",
		width: 100,
		fixed: true,
		halign: 'center',
		align: 'right',
		hidden: allowinsale == 1 ? false : true,
		formatter: currfm
	},{
		field: 'RETAILSALE',
		title: '零售价',
		width: 50,
		fixed: true,
		halign: 'center',
		align: 'right',
		formatter: currfm
	},{
		field: 'RETAILCURR',
		title: '零售额',
		width: 80,
		fixed: true,
		halign: 'center',
		align: 'right',
		formatter: currfm
	});
	return colums;
}
function isAddWarem() {
	var houseid = $('#uhouseid').val();
	var provid = $('#uprovid').val();
	if (houseid == '0' || houseid == '' || houseid == undefined) {
		alerttext('请选择店铺');
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#uhousename').focus();
		return false;
	} else if (provid == '0' || provid == '' || provid == undefined) {
		alerttext("请选择供应商");
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#uprovname').focus();
		return false;
	} else {
		return true;
	}
}
var notedateobj = {
	id: "#uid",
	noteno: "#unoteno",
	notedate: "#unotedate",
	action: "updatewareinhbyid"
}
$(function() {
	$('body').delegate('.datagrid', 'keydown',
	function(e) {
		var key = e.which;
		if (key == 113) {
			addnoted();
		}
	});
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setaddbackprint("1103");
	$('div.div_sort .label_sort').click(function() {
		var $this = $(this);
		if ($this.hasClass('icon_jiantou_up')) $this.removeClass("icon_jiantou_up").addClass("icon_jiantou_down");
		else $this.removeClass("icon_jiantou_down").addClass("icon_jiantou_up");
		getnotewarem($('#unoteno').val(), "1");
	});
	setwarem();
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
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
				$('#uprovid').val(rowData.PROVID);
				$('#uprovname').val(rowData.PROVNAME);
				$('#uhandno').val(rowData.HANDNO);
				$('#wtotalcurr').val(rowData.TOTALCURR);
				$('#wtotalamt').val(rowData.TOTALAMT);
				$('#udiscount').val(rowData.DISCOUNT);
				$('#upricetype').val(rowData.PRICETYPE);
				$('#uoperant').val(rowData.OPERANT);
				$('#untid').val(rowData.NTID);
				$('#uremark').val(rowData.REMARK);
				$('#ustatetag').val(rowData.STATETAG);
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
			field: 'PROVNAME',
			title: '供应商',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},{
			field: 'HOUSENAME',
			title: '店铺名称',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},{
			field: 'HANDNO',
			title: '自编号',
			fixed: true,
			width: 200,
			hidden: true
		},{
			field: 'TOTALCURR',
			title: '总金额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			hidden: allowinsale == 1 ? false : true,
			formatter: function(value, row, index) {
				return allowinsale == '1' ? Number(value).toFixed(2) : "***";
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
			field: 'PROVID',
			title: '供应商ID',
			width: 200,
			hidden: true
		},
		{
			field: 'DISCOUNT',
			title: '折扣',
			width: 200,
			hidden: true
		},
		{
			field: 'PRICETYPE',
			title: '价格方式',
			width: 200,
			hidden: true
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
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatenoted()");
	getnotedata();
});
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
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shousename,#shouseid,#shandno,#sprovname,#sprovid,#swareno,#swareid,#sremark,#soperant').val('');
	$('#st3').prop('checked', true);
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "refundinhlist";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.noteno,a.accid,a.notedate,a.provid,a.houseid,a.ntid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,b.provname,b.discount,b.pricetype,c.housename";
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
	var provid = Number($('#sprovid').val());
	var handno = $('#shandno').val();
	var wareid = Number($('#swareid').val());
	var remark = $('#sremark').val();
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
		houseid: houseid,
		handno: handno,
		provid: provid,
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
//增加单据
function addnoted() {
	$('#udnote').show();
	$('#warembar').html('▲&nbsp;&nbsp;商品明细');
	setTimeout(function() {
		$('#waret').datagrid('resize', {
			height: $('body').height() - 90
		});
	},
	200);
	d = false;
	$('#udnoteform')[0].reset();
	$('#udnoteform input[type=hidden]').val('');
	$('#uhouseid').val(getValuebyName('HOUSEID'));
	$('#uhousename').val(getValuebyName('HOUSENAME'));
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
	$('#wmtotalnote').html('已显示0条记录/共有0条记录');
	$('#uhousename').removeAttr('readonly');
	$('#uhandno').removeAttr('readonly');
	$('#uselecthouseb').removeAttr('disabled');
	$('#uselectprovb').removeAttr('disabled');
	$('#isupdatedbar,#clearnotebar').hide();
	$('#updatingbar').show();
	$('#wquickuwaretd').show();
	$('#wquickuwareno').val('');
	$('#uremark').removeAttr('readonly');
	$('#uprovname').removeAttr('readonly');
	$('#uhousename').next().show();
	$('#uprovname').next().show();
	$('#ud #warem-toolbar').show();
	$('#unotedate').datetimebox({
		readonly: false,
		hasDownArrow: true
	});
	addrefundinh();
	$('#ud').dialog("setTitle", '增加采购退库单').dialog('open');
	$('#ustatetag').val('0');
	$('#ud').data('qickey', "F4:'updaterefundinh()',F6:'opendisc()',Del:'delrefundinh()'");
	// 	$('#ud').data('qickey',"F4:'updaterefundinh()',F6:'opendisc()',F7:'loadcg($(\"#uprovid\").val())',Del:'delrefundinh()'");
	$('#wquickuwareno').focus();
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
	//$('#div_waremx').hide();
	$('#waret').datagrid('loadData', nulldata);
	var rowData = $('#gg').datagrid('getSelected');
	if (rowData == null) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		getnotebyid($('#unoteno').val());
	}
}
function quickaddwd() {
	$('#quickuaddwaref')[0].reset();
	$('#quickutable').html('');
	if ($("#uprovid").val() == '0' || $("#uprovid").val() == '') {
		alerttext('请选择供应商');
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
		toolbar: "#udtool",
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
			if(!$("#warem-toolbar").is(":hidden")) updatewd();
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
//编辑商品框
function updatewd() {
	$('#quickuaddwaref')[0].reset();
	$("#alldisc,#allpri").val("");
	$('#quickutable').html('');
	var arrs = $('#waret').datagrid('getSelected');
	if (arrs == null) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var state = $('#ustatetag').val();
		if (state != '1') {
			getwaremsum(arrs.WAREID, $('#unoteno').val());
			$('#wquickuwareid').val(arrs.WAREID);
		}
	}
}
//获取上面明细单据及分页
function getnotewarem(noteno, page) {
	alertmsg(6);
	if (page == 1) $('#waret').datagrid('loadData', nulldata);
	var sortid = $('#waremsort').prop('checked') ? 1 : 0;
	var order = $('div.div_sort .label_sort').hasClass('icon_jiantou_up') ? "asc": "desc";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "wareinmcolorsumlist",
			sizenum: sizenum,
			rows: pagecount,
			noteno: noteno,
			sortid: sortid,
			order: order,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totals = data.total;
					var totalamt = Number(data.totalamt);
					var totalcurr = Number(data.totalcurr);
					var retailcurr = Number(data.retailcurr);
					var totalcost = Number($("#usaletotalcost").val());
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
						}
					} else {
						pg++;
						var rows = data.rows;
						for (var i in rows) {
							$('#waret').datagrid('appendRow', rows[i]);
						}
					}
					$('#waret').datagrid('loaded');
					$('#wmtotalnote').html('已显示' + $('#waret').datagrid('getRows').length + '条记录/共有' + totals + '条记录');
					var $dg = $("#gg");
					var index = $dg.datagrid("getRowIndex", noteno);
					$dg.datagrid('updateRow', {
						index: index,
						row: {
							TOTALAMT: totalamt,
							TOTALCURR: totalcurr+totalcost
						}
					});
					$dg.datagrid('updateFooter', {
						TOTALAMT: Number(dqzamt) - Number(dqamt) + Number(totalamt),
						TOTALCURR: Number(dqzcurr) - Number(dqcurr) + Number(totalcurr)+totalcost
					});
					dqzamt = Number(dqzamt) - Number(dqamt) + Number(totalamt);
					dqzcurr = Number(dqzcurr) - Number(dqcurr) + Number(totalcurr)+totalcost;
					dqamt = totalamt;
					dqcurr = totalcurr+totalcost;
				}
			} catch(ex) {
				console.error(ex.message);
			}
		}
	});
}
//删除明细
function delwaremx() {
	var noteno = $('#unoteno').val();
	var rowData = $('#waret').datagrid("getSelected");
	if (rowData) {
		var wareid = rowData.WAREID;
		var colorid = rowData.COLORID;
		$.messager.confirm(dialog_title, '您确认要删除' + rowData.WARENO + '，' + rowData.WARENAME + '，' + rowData.COLORNAME + '吗？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwareinmsum",
						noteno: noteno,
						wareid: wareid,
						colorid: colorid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							getnotewarem(noteno, "1");
						}
					}
				});
			}
		});
	} else {
		alerttext("请选择一行数据，进行编辑!");
	}
}
function quickupdatesum() {
	var list = $('#quickutables tr:eq(1) input[type=text]').length - 2; //横向文本框数
	var line = $('#quickutables tr').length - 2; //竖向文本框数
	var y_price = $("#quickutables").data("price");
	$("#quickutables input.price").each(function(){
		$this = $(this);
		var price = Number($this.val());
		if(price<y_price){
			$this.addClass("warnprice");
		}else{
			$this.removeClass("warnprice");
		}
	});
	var totalamount = new Decimal(0);
	var totalcurr = new Decimal(0);
	var count = 0;
	var amount1 = new Array(line);
	var amount2 = new Array(list + 5);
	var quickjsonstr = new Array();
	if (line >= 1 && list >= 1) {
		for (i = 1; i <= line; i++) {
			amount1[i] = new Decimal(0);
			for (j = 1; j <= list + 1; j++) {
				if (j != list + 1) {
					var valuestr = $('#u' + i.toString() + "-" + j.toString()).val();
					var value = Number($('#u' + i.toString() + "-" + j.toString()).val());
					if (valuestr != ""&&!isNaN(value)) {
						quickjsonstr.push({
							colorid: $('#uacolor' + i).val(),
							sizeid: $('#uasize' + j).val(),
							amount: value,
							discount: $('#udisc' + i).val(),
							price0: $('#uprice0' + i).html(),
							price: $('#upri' + i).val(),
							curr: Number($('#upri' + i).val()) * Number(value)
						});
						count = count + 1;
						amount1[i] = amount1[i].add(value);
						totalamount = totalamount.add(value);
					}
				} else {
					$('#usum' + i).html(amount1[i].valueOf());
					var curr = Number((amount1[i] * Number($('#upri' + i).val())).toFixed(points));
					$('#uacurr' + i).html(allowinsale == '1' ? curr.toFixed(2) : "***");
					totalcurr = totalcurr.add(curr);
				}
			}
		}
		for (i = 1; i <= list; i++) {
			amount2[i] = new Decimal(0);
			for (j = 1; j <= line + 1; j++) {
				if (j != line + 1) {
					var value = Number($('#u' + j.toString() + "-" + i.toString()).val() == undefined ? 0 : $('#u' + j.toString() + "-" + i.toString()).val() == '' ? 0 : $('#u' + j.toString() + "-" + i.toString()).val());
					if (value != 0) {
						amount2[i] = amount2[i].add(value);
					}
				} else if (j == line + 1) {
					var a = amount2[i].valueOf();
					$('#asum' + i).html(a == 0 ? "": a);
				}
			}
		}
	}
	$('#asum' + (list + 1).toString()).html(totalamount.valueOf());
	$('#asum' + (list + 5).toString()).html(allowinsale == 1 ? totalcurr.toFixed(2) : "***");
	jsondata = quickjsonstr;
}
var hasTable = false;
function getwaremsum(wareid, noteno) {
	$('#quickutable').html('');
	var houseid = $('#uhouseid').val();
	if ($('#quickutables').length == 0 && hasTable == false) {
		hasTable = true;
		alertmsg(6);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getwareinmsum",
				noteno: noteno,
				houseid: houseid,
				ntid: "1",
				wareid: wareid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					var wareno = data.WARENO;
					var warename = data.WARENAME;
					var entersale = data.ENTERSALE;
					var retailsale = data.RETAILSALE;
					var pricetype = $('#upricetype').val();
					var price1 = pricetype == '0' ? entersale: retailsale;
					var defaultdisc = $('#udiscount').val();
					var colorlist = data.colorlist;
					var sizelist = data.sizelist;
					var amountlist = data.amountlist;
					var kclist = data.kclist;
					var colorid;
					var sizeid;
					//获取hang数值
					var line = getJsonLength(colorlist);
					//获取lie数值
					var list = getJsonLength(sizelist);
					if (line == 0) {
						alerttext("该商品未选择任何可用商品颜色，请在商品档案中选择该商品颜色");
						hasTable = false;
						return;
					}
					if (list == 0) {
						alerttext("该商品未选择尺码组，请在商品档案中选择该商品尺码组");
						hasTable = false;
						return;
					}
					var table = document.createElement("table");
					table.id = "quickutables";
					table.setAttribute("data-price",data.PRICE);
					table.cellSpacing = 0;
					for (var i = 0; i <= line + 1; i++) {
						//alert(line);
						//创建tr
						var tr = document.createElement("tr");
						var curr = 0;
						var amt = 0;
						var price0 = price1; //单价
						var disc = defaultdisc; //折扣
						var price = Number(defaultdisc) * Number(price1); //折后价
						for (var j = 0; j <= list + 5; j++) {
							//alert(list);
							//创建td
							var td = document.createElement("td");
							if (i == 0) {
								tr.className = "table-header",
								td.align = "center";
								td.style.border = "none";
								if (j == 0) {
									td.style.width = '80px';
									td.innerHTML = '颜色';
								} else if (j == list + 1) {
									td.style.width = '60px';
									td.innerHTML = "小计";
								} else if (j == list + 2) {
									td.style.width = '80px';
									allowinsale == '1' ? td.innerHTML = "原价": td.className = "hide";
								} else if (j == list + 3) {
									td.style.width = '60px';
									td.innerHTML = "折扣";
									allowinsale == '1' ? "": td.className = "hide";
								} else if (j == list + 4) {
									td.style.width = '80px';
									td.innerHTML = "单价";
									allowinsale == '1' ? "": td.className = "hide";
								} else if (j == list + 5) {
									td.style.width = '100px';
									td.innerHTML = "金额";
									allowinsale == '1' ? "": td.className = "hide";
								} else {
									td.style.width = '50px';
									var input = document.createElement("input");
									input.type = "hidden";
									input.id = "uasize" + j;
									input.value = sizelist[j - 1].SIZEID;
									td.innerHTML = sizelist[j - 1].SIZENAME;
									td.appendChild(input);
								}
							} else if (i == line + 1) {
								tr.style.fontWeight = 600;
								if (j == 0) {
									td.innerHTML = "合计";
								} else {
									if (j == (list + 1)) {
										td.align = "center";
									} else if (j == (list + 2) || j == (list + 5)) {
										td.align = "right";
									}
									if (j == list + 2 || j == list + 3 || j == list + 4 || j == list + 5) {
										allowinsale == '1' ? "": td.className = "hide";
									}
									td.id = "asum" + j;
									td.innerHTML = "";
								}
							} else {
								if (j == 0) {
									var input = document.createElement("input");
									input.type = "hidden";
									input.id = "uacolor" + i;
									input.value = colorlist[i - 1].COLORID;
									td.innerHTML = colorlist[i - 1].COLORNAME;
									td.appendChild(input);
								} else if (j == list + 1) {
									td.id = "usum" + i;
									td.align = "center";
									td.innerHTML = amt;
								} else if (j == list + 2) {
									td.id = "uprice0" + i;
									td.align = "right";
									td.innerHTML = Number(price0).toFixed(2);
									allowinsale == '1' ? "": td.className = "hide";
								} else if (j == list + 3) {
									var input = document.createElement("input");
									input.id = "udisc" + i;
									input.style.border = "";
									input.type = "text";
									input.style.width = "95%";
									input.style.textAlign = "center";
									input.maxLength = 4;
									input.autocomplete = 'off';
									input.style.border = "none";
									input.value = Number(disc).toFixed(2);
									td.appendChild(input);
									$(input).keyup(function(e) {
										var key = e.which;
										if(key==13||(key<=40&&key>=38)) return;
										this.value = this.value.replace(/[^\d.]/g, "");
										this.value = this.value.replace(/^\./g, ""); //验证第一个字符是数字而不是. 
										this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
										if (Number(this.value) > 10) {
											alerttext("最大折扣为10", 1500);
											this.value = "10";
										}
										var t = Number(this.id.replace("udisc", ""));
										var price2 = 0;
										var tprice0 = Number($('#uprice0' + t).html());
										if (Number(tprice0) != 0) {
											price2 = Number(this.value) * tprice0;
										}
										$('#upri' + t).val(Number(price2).toFixed(2));
										quickupdatesum();
									});
									$(input).change(function() {
										this.value = Number(this.value).toFixed(2);
									});
									allowinsale == '1' ? "": td.className = "hide";
								} else if (j == list + 4) {
									var input = document.createElement("input");
									input.id = "upri" + i;
									input.style.border = "";
									input.type = "text";
									input.className = "price";
									input.style.width = "95%";
									input.style.border = "none";
									input.style.textAlign = "center";
									input.maxLength = 7;
									input.autocomplete = 'off';
									input.value = Number(price).toFixed(2);
									$(input).keyup(function(e) {
										var key = e.which;
										if(key==13||(key<=40&&key>=38)) return;
										this.value = this.value.replace(/[^\d.]/g, "");
										var price2 = Number(this.value);
										var disc2;
										var t = Number(this.id.replace("upri", ""));
										var tprice0 = Number($('#uprice0' + t).html());
										if (Number(tprice0) == 0) {
											disc2 = 1;
										} else {
											disc2 = price2 / tprice0;
										}
										$('#udisc' + t).val(Number(disc2).toFixed(2));
										quickupdatesum();
									});
									$(input).change(function() {
										this.value = Number(this.value).toFixed(2);
									});
									td.appendChild(input);
									allowinsale == '1' ? "": td.className = "hide";
								} else if (j == list + 5) {
									td.align = "right";
									td.id = "uacurr" + i;
									td.innerHTML = allowinsale == '1' ? (curr == undefined ? 0 : curr).toFixed(2) : "***";
									allowinsale == '1' ? "": td.className = "hide";
								} else {
									var input = document.createElement("input");
									input.type = "text";
									input.className = "reg-amt";
									input.style.width = "95%";
									input.style.textAlign = "center";
									input.maxLength = 5;
									input.style.border = "none";
									input.style.imeMode = 'disabled';
									input.autocomplete = 'off';
									sizeid = sizelist[j - 1].SIZEID;
									colorid = colorlist[i - 1].COLORID;
									if (getJsonLength(amountlist) == 0 && getJsonLength(kclist) == 0) {
										input.placeholder = '';
									} else {
										for (r in amountlist) {
											var colorids = amountlist[r].COLORID;
											var sizeids = amountlist[r].SIZEID;
											if (colorids == colorid && sizeid == sizeids) {
												price0 = amountlist[r].PRICE0;
												price = amountlist[r].PRICE;
												disc = amountlist[r].DISCOUNT;
												var val = input.value == '' ? 0 : Number(input.value);
												input.value = val + Number(amountlist[r].AMOUNT);
												amt = Number(amountlist[r].AMOUNT) + amt;
												curr = curr + Number(amountlist[r].CURR);
												//break;
											}
										}
										for (r in kclist) {
											var colorids = kclist[r].COLORID;
											var sizeids = kclist[r].SIZEID;
											if (colorids == colorid && sizeid == sizeids) {
												input.setAttribute("placeholder", kclist[r].AMOUNT);
												break;
											} else {
												input.setAttribute("placeholder", "");
											}
										}
									}
									input.id = 'u' + i.toString() + "-" + j.toString();
									input.setAttribute("data-colnum", j);
									input.setAttribute("data-rownum", i);
									td.appendChild(input);
								}
							}
							tr.appendChild(td);
						}
						table.appendChild(tr);
					}
					if ($('#quickutables').length == 0) {
						document.getElementById("quickutable").appendChild(table);
					}
					//autocreate(colorlist,sizelist);
					quickupdatesum();
					$('input[type=text]').placeholder();
					var amtl = getJsonLength(amountlist);
					var tname = amtl == 0 ? "添加": "修改";
					var title = tname + "商品明细：" + wareno + "，" + warename;
					var out_wid = list * 52 + 400 - (allowinsale == 1 ? 0 : 310);
					out_wid = out_wid > 400 ? out_wid: 400;
					$('#quickud').dialog({
						title: title,
						width: out_wid
					});
					if (allowinsale == "1") {
						$('#quickud .dialog-footer .ml10').show();
					} else {
						$('#quickud .dialog-footer .ml10').hide();
					}
					if ($('#showprovhistory').prop('checked')) {
						if ($('#provwarehistoryt').data().datagrid == undefined) {
							setprovwarehistt();
						}
						$('#provwarehistorydiv').show();
						$('#provwarehistoryt').datagrid('resize');
						listsalehistory(1);
					} else {
						$('#provwarehistorydiv').hide();
					}
					$('#quickud').window('center');
					$('#quickud').dialog('open');
					$('#quickutables').width(out_wid - 30);
					$('#u1-1').focus();
				}
				hasTable = false;
			}
		});
	}
}
//添加生成采购退库单
function addrefundinh() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addrefundinh",
			houseid: getValuebyName("HOUSEID")
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#uid').val(data.ID);
				$('#unoteno').val(data.NOTENO);
				changedatebj = false;
				$('#unotedate').datetimebox('setValue', data.NOTEDATE);
				changedatebj = true;
				dqindex = 0;
				dqamt = 0;
				dqcurr = 0;
				$("#qsnotevalue").val("");
				$('#smaxdate').datebox('setValue', top.getservertime());
				$('#waret').datagrid('loadData', nulldata);
				getnotedata();
			}
		}
	});
}
function delnote(index) {
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	$('#uid').val(row.ID);
	$('#unoteno').val(row.NOTENO);
	delrefundinh();
}
//删除单据
function delrefundinh() {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	$.messager.confirm(dialog_title, '单据信息以及商品明细将全部清除，并且不可恢复，是否确认删除吗？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delwareinhbyid",
					noteno: noteno,
					ntid: '1'
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
						dqzamt = Number(dqzamt) - Number(dqamt);
						dqzcurr = Number(dqzcurr) - Number(dqcurr);
						$('#pp_total').html(dqzjl - 1);
						dqzjl = dqzjl - 1;
					}

				}
			});
		}
	});
}
//修改采购退库单
function updaterefundinh() {
	var id = $('#uid').val();
	var provid = $('#uprovid').val();
	var houseid = $('#uhouseid').val();
	var noteno = $('#unoteno').val();
	var handno = $('#uhandno').val();
	var statetag = $('#ustatetag').val();
	var totalcurr = Number($('#wtotalcurr').val()) + Number($("#usaletotalcost").val());
	var totalamt = $('#wtotalamt').val();
	var remark = $('#uremark').val();
	var notedatestr = $('#unotedate').datetimebox('getValue');
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
		return;
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
						erpser: "updatewareinhbyid",
						ntid: "1",
						noteno: noteno,
						id: id,
						provid: provid,
						houseid: houseid,
						handno: handno,
						notedatestr: notedatestr,
						statetag: 1,
						totalcurr: totalcurr,
						totalamt: totalamt,
						remark: remark
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
//选择店铺
function changehouse(id, noteno, houseid) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewareinhbyid",
			ntid: 1,
			noteno: noteno,
			houseid: houseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						HOUSENAME: $('#uhousename').val(),
						HOUSEID: houseid
					}
				});
			}
		}
	})
}
//选择供应商
function changeprov(id, noteno, provid, discount, pricetype) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewareinhbyid",
			ntid: 1,
			noteno: noteno,
			provid: provid,
			discount: discount,
			pricetype: pricetype
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						PROVNAME: $('#uprovname').val(),
						PROVID: provid,
						DISCOUNT: discount,
						PRICETYPE: pricetype
					}
				});
				if ($('#waret').datagrid('getRows').length > 0) {
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "changewareinmdisc",
							provid: provid,
							noteno: noteno,
							discount: discount
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							if (valideData(data)) {
								getnotewarem(noteno, '1');
							}
						}
					});
				}
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
			erpser: "updatewareinhbyid",
			ntid: 1,
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
			erpser: "updatewareinhbyid",
			ntid: 1,
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
function quickaddwarem() {
	var noteno = $('#unoteno').val();
	var wareid = $('#wquickuwareid').val();
	if (wareid == undefined || wareid == "") {
		alerttext("请重新选择货号！");
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方  法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "writewareinmsum",
				noteno: noteno,
				wareid: wareid,
				rows: JSON.stringify(jsondata)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#quickuaddwaref')[0].reset();
					$('#quickutable').html('');
					$('#wquickuwareno').val('');
					$('#wquickuwareid').val('');
					getnotewarem(noteno, '1');
					$('#quickud').dialog('close');
					$('#alldisc').val('');
					$('#allpri').val('');
					$("#wquickuwareno").parent().children("ul").html("");
					$("#wquickuwareno").parent().children("ul").hide();
					$('#wquickuwareno').focus();
				}
			}
		});
	}
}
//打开打折框
function opendisc() {
	$('#discd').dialog('open');
	$('#alldiscount').focus();
}
//整单打折	
function alldisc() {
	var discount = $('#alldiscount').val();
	var noteno = $('#unoteno').val();
	if (discount == '') {
		alerttext('请输入折扣');
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "changewareinmdisc",
				noteno: noteno,
				discount: discount
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$("#discd").dialog('close');
					$("#udiscount").val(discount);
					getnotewarem(noteno, '1');
				}

			}
		});
	}
}
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
						erpser: "wareinhcancel",
						noteno: noteno
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
							$('#gg').datagrid('refresh');
							$('#ud').dialog('close');
						}

					}
				});
			}
		});
	} else {
		alerttext('本单据不是当天单据，撤单只对当天单据有效！');
	}
}

//获取是否为当天单据
function getnotebyid(noteno) {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwareinhbyid",
			noteno: noteno,
			fieldlist: "a.id,a.noteno,a.accid,a.notedate,a.provid,a.orderno,a.houseid,a.ntid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,a.totalcost,b.provname,b.discount,b.pricetype,c.housename,a.cleartag"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var rows = data.rows;
				if (data.total > 0) {
					var row = rows[0];
					$('#udnoteform input[type=hidden]').val('');
					$('#uid').val(row.ID);
					$('#unoteno').val(row.NOTENO);
					$('#uhouseid').val(row.HOUSEID);
					$('#uhousename').val(row.HOUSENAME);
					$('#uprovid').val(row.PROVID);
					$('#uprovname').val(row.PROVNAME);
					$('#uhandno').val(row.HANDNO);
					$('#wtotalcurr').val(row.TOTALCURR);
					$('#wtotalamt').val(row.TOTALAMT);
					$('#uoperant').val(row.OPERANT);
					$('#uremark').val(row.REMARK);
					$('#untid').val(row.NTID);
					$('#udiscount').val(row.DISCOUNT);
					$('#upricetype').val(row.PRICETYPE);
					$('#ustatetag').val(row.STATETAG);
					var $dg = $("#gg");
					dqamt = row.TOTALAMT;
					dqcurr = row.TOTALCURR;

					var istoday = row.ISTODAY;
					$('#uistoday').val(istoday);
					var cleartag = row.CLEARTAG;
					var stg = row.STATETAG;
					if ((Number(istoday) == 1 || allowchangdate == 1) && allowchedan == '1' && cleartag == '0' && stg == "1") {
						$('#sep-withdraw').show();
						$('#withdrawbtn').show();
						$('#clearnotebar').hide();
					} else if (Number(istoday) == 0 && Number(allowclear) > 0 && cleartag == '0' && stg == "1") {
						$('#clearnotebar').show();
						$('#sep-withdraw').hide();
						$('#withdrawbtn').hide();
					} else {
						$('#clearnotebar').hide();
						$('#sep-withdraw').hide();
						$('#withdrawbtn').hide();
					}
					var epname = getValuebyName("EPNAME");
					var operant = row.OPERANT;
					if (stg==1||epname!=operant) {
						$('#ud').dialog("setTitle", "浏览采购退库单");
						$('#uhandno,#uremark,#uhousename,#uprovname').attr('readonly', 'readonly');
						$('#updatingbar,#wquickuwaretd,#ud #warem-toolbar').hide();
						$('#uhousename').next().hide();
						$('#uprovname').next().hide();
						$('#isupdatedbar').show();
						//$('#waret').datagrid('hideColumn','ACTION');
						if (allowchedan == '1') {
							$('#ud').data('qickey', "F9:'$(\"#print\").click()',F10:'withdraw()'");
						} else {
							$('#ud').data('qickey', "F9:'$(\"#print\").click()'");
						}
						if (allowinsale == 1) {
							$('#paydetail').show();
						} else {
							$('#paydetail').hide();
						}
						$('#unotedate').datetimebox({
							readonly: true,
							hasDownArrow: false
						});
					} else {
						$('#ud').dialog("setTitle", "修改采购退库单");
						$('#uhousename,#uhandno,#uremark,#uprovname').removeAttr('readonly');
						$('#isupdatedbar').hide();
						$('#updatingbar,#wquickuwaretd,#ud #warem-toolbar').show();
						$('#uhousename').next().show();
						$('#uprovname').next().show();
						$('#wquickuwareno').val('');
						//$('#waret').datagrid('showColumn','ACTION');
						$('#ud').data('qickey', "F4:'updaterefundinh()',F6:'opendisc()',Del:'delwareinh()'");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
					}
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					$('#ud').dialog('open');
					var totalcost = Number(row.TOTALCOST);
					$('#usaletotalcost').val(totalcost.toFixed(2));
					getnotewarem(noteno, 1);
					if (stg == 1) {
						$('#unoteno').focus();
					} else {
						$('#wquickuwareno').focus();
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
		<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 有折扣商品明细弹窗 -->
	<jsp:include page="../HelpList/DiscWarem.jsp"></jsp:include>
	<!-- 供应商帮助列表 -->
	<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
		<!-- 条码增加 -->
<jsp:include page="../HelpList/BarcodeAdd.jsp"></jsp:include>
	<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
		<jsp:include page="../HelpList/SaleCost.jsp"></jsp:include>
		<jsp:include page="../HelpList/ExportHelp.jsp"></jsp:include>
</body>
</html>