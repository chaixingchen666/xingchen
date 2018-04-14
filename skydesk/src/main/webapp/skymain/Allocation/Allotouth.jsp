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
<title>调拨出库</title>
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
<input type="hidden" id="flag" value="1">
	<!-- 调拨出库单据列表 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addnoted()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、店铺、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20" data-enter="getnotedata()">
	<input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn" style="width:20%"><input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr hide">
	<input type="button" class="btn3"  value="导入" onclick="importxls()">
	<span class="sepreate"></span>
	<input type="button" class="btn3"  value="导出" onclick="exportxls()">
	</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="stohouseid" id="stohouseid" value=""> 
<input type="hidden" name="shouseid" id="shouseid" value=""> 
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input type="text" name="smindate" id="smindate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
	<div class="s-box"><div class="title" style="color:#ff7900">*调出店铺</div>
<div class="text">
<input class="edithelpinput house_help" id="shousename" data-value="#shouseid" type="text"><span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>
	<div class="s-box"><div class="title" style="color:#ff7900">*调入店铺</div>
<div class="text">
<input class="edithelpinput house_help" id="stohousename" data-value="#stohouseid" type="text"><span class="s-btn" onclick="selecthouse('tosearch')"></span>
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput" id="swareno" type="text" data-value="#swareid"><span class="s-btn" onclick="selectware('search')"></span>
</div></div>
<div class="s-box"><div class="title">自编号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="shandno" id="shandno" placeholder="---请输入---" >
</div>
</div>
<div class="s-box"><div class="title">摘要</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sremark" id="sremark" placeholder="---请输入---" >
</div>
</div>
<div class="s-box"><div class="title">调拨订单</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sorderno" id="sorderno" placeholder="---请输入---" >
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
<table id="gg" style="overflow: hidden;height:900px"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
	<!-- 添加与修改调拨出库表单 -->
	<div id="ud" title="编辑调拨出库" style="width: 100%;height:100%;" class="easyui-dialog" closed="true">
	<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" data-btn="updatebtn" id="pay" value="提交" onclick="updateallotouth()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="delete" value="导入" onclick="if(isAddWarem()){openimportd()}"></span>
<!-- 	<span class="sepreate"></span> -->
<!-- 	<span><input class="btn4" type="button" id="load" value="载入" onclick="loadcg($('#utohouseid').val())"></span> -->
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="load" value="载入调拨订单" onclick="openallotorderd()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="delete" value="删除单据" onclick="delallotouth()"></span>
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
		<span id="clearnotebar">
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="clearbtn" value="冲单" onclick="clearnote(6,$('#unoteno').val())"></span>
	</span>
	</span>
	<span id="copynotebar">
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="copybtn" value="复制单据" onclick="setfromnoteno($('#unoteno').val())"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="pastebtn" value="粘贴单据" onclick="copynote(6,$('#unoteno').val())"></span>
	</span>
	<!-- 单据提交框 -->
	<div id="udnote">
	<form id="udnoteform" action="" method="get">
	<input type="hidden" id="uid"> 
	<input type="hidden" id="untid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="upricetype1"> 
	<input type="hidden" id="ustatetag"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="utohouseid">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
	<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25" type="text" id="unoteno" readonly>
</div>
</div>
<div class="s-box"><div class="title">日期</div>
	<div class="text">
	    <input id="unotedate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false,onShowPanel: setTodayDate,onHidePanel: changedate">
</div>
</div>
		<div class="s-box"><div class="title" style="color:#ff7900">*调出店铺</div>

	<div class="text"><input class="edithelpinput wid145 house_help" data-value="#uhouseid" type="text" id="uhousename"><span onclick="selecthouse('update')"></span>
</div>
</div>
		<div class="s-box"><div class="title" style="color:#ff7900">*调入店铺</div>
	<div class="text"><input class="wid188 edithelpinput house_help" data-value="changenotetohouse" type="text" data-cxdr="1" id="utohousename" placeholder="<请选择>"  >
	<span id="seltohousespan" onclick="selecthouse('tohouse')" ></span>
</div>
</div>
<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" class="hig25 wid143" id="uhandno" placeholder="<输入>" onchange="changehandno(this.value)">
</div>
</div>
<div class="s-box" id="div_orderno"><div class="title">调拨订单</div>
<div class="text" id="uorderno">
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
		<tr><td id="warembar" onclick="updown()" style="color:#ff7900">▲&nbsp;&nbsp;商品明细</td><td id="wquickuwaretd"><input type="text" id="wquickuwareno" data-value="quickaddwarem" class="edithelpinput wid145" placeholder="<选择或输入>" data-value="quickaddwarem" data-end="yes"><span onclick="if(isAddWarem()){selectware('quickupdatew')}"></span></td>
		</tr>
		</table>
		<input type="hidden" id="wquickuwareid" >
		<datalist id="wareno_list">	
		</datalist>
		</div>
			<div class="fr" id="warem-toolbar">
			<span><input class="btn5" type="button" id="notecheckware" value="√扫码验货" onclick="opennotecheckd()"></span>
				<span class="seprate1"></span>
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
<!-- 订单载入窗 -->
<div id="loadd" title="载入未完成调拨订货" style="width: 350px; height: 370px" class="easyui-dialog" closed="true">
<!-- <table id="loadt"></table> -->
<div class="textcenter" id="loadform">
<input type="hidden" id="lwareid">
<input type="hidden" id="lbrandid">
<input type="hidden" id="ltypeid">
<table class="table1" border="0" cellspacing="10" cellpadding="10">
  <tr>
    <td width="67" class="header" align="right">货号</td>
    <td width="170" align="right"><input class="edithelpinput wareno_help" data-value="#lwareid" type="text" id="lwareno" placeholder="<输入>"><span onclick="selectware('load')"></span></td>
  </tr>
  <tr>
    <td class="header" align="right">类型</td>
    <td align="right"><input class="edithelpinput" type="text" id="lwaretype" placeholder="<选择>" readonly><span onclick="selectwaretype('load')"></span></td>
  </tr>
    <tr>
    <td width="67" class="header" align="right">品牌</td>
    <td width="170" align="right"><input class="edithelpinput brand_help" data-value="#lbrandid" type="text" id="lbrandname" placeholder="<输入>"><span onclick="selectbrand('load')"></span></td>
  </tr>
  <tr id="centdivm">
    <td class="header" align="right">季节</td>
    <td align="right"><input  type="text" class="edithelpinput season_help" name="lseasonname" id="lseasonname" maxlength="20" placeholder="<选择或输入>">
                      <span onclick="opencombox(this)"></span></td>
  </tr>
  <tr>
    <td class="header" align="right">生产年份</td>
    <td align="right"><input class="hig25 wid160" type="text" id="lprodyear" placeholder="<输入>" maxlength="4"></td>
  </tr>
</table>
</div>
<div class="dialog-footer">
		<input type="button" class="btn1" style="width:100px;margin-right: 10px" value="清空载入条件" onclick="resetload()">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="载入" onclick="loadnote()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#loadd').dialog('close')">
</div>
</div>
<!-- 订单载入窗 -->
<div id="allotorderd" title="载入调拨订单" style="width: 800px; height: 370px" class="easyui-dialog" closed="true">
<div style="margin: 0 10px;">
<table id="allotordert"></table>
</div>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="allotorder_total">共有0条记录</div>
	<div class="page-total">
	<label>
	<input type="checkbox" id="loadkc" checked>载入有库存的商品
	</label>
	</div>
	<div id="allotorderpp" class="tcdPageCode page-code">
	</div>

</div>
</div>
<!-- 选择xls窗 -->
<div id="downxlsd" title="请选择下载类型" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 80px 10px 10px 10px;text-align: center;">
<input type="button" class="btn1" value="下载（无尺码）" onclick="exportmxxls(0)"> 
<input type="button" class="btn1" value="下载（有尺码）" onclick="exportmxxls(1)"> 
</div>
<div class="dialog-footer">
<input type="button" class="btn2" name="close" value="取消" onclick="$('#downxlsd').dialog('close')">
</div>
</div>
<script type="text/javascript">
var levelid = getValuebyName("GLEVELID");
var pgid = 'pg1402';
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
function isAddWarem() {
	var houseid = $('#uhouseid').val();
	var tohouseid = $('#utohouseid').val();
	if (houseid == '0' || houseid == '' || houseid == undefined) {
		alerttext('请选择调出店铺');
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#utohousename').focus();
		return false;
	} else if (tohouseid == '0' || tohouseid == '' || tohouseid == undefined) {
		alerttext("请选择调入店铺");
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#utohousename').focus();
		return false;
	} else {
		return true;
	}
}
var notedateobj = {
	id: "#uid",
	noteno: "#unoteno",
	notedate: "#unotedate",
	action: "updateallotouthbyid"
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
	setaddbackprint("1402");
	$('div.div_sort .label_sort').click(function() {
		var $this = $(this);
		if ($this.hasClass('icon_jiantou_up')) $this.removeClass("icon_jiantou_up").addClass("icon_jiantou_down");
		else $this.removeClass("icon_jiantou_down").addClass("icon_jiantou_up");
		getnotewarem($('#unoteno').val(), "1");
	});
	$('#loadkc').change(function() {
		var check = $(this).prop('checked');
		var loadkcbj = check ? 1 : 0; //是否载入库存标记
		setparam({
			pgid: pgid.replace("pg", ""),
			usymbol: "loadkcbj",
			uvalue: loadkcbj
		});
	});
	var loadkcbj = getparam({
		pgid: pgid.replace("pg", ""),
		usymbol: "loadkcbj"
	});
	$('#loadkc').prop('checked', loadkcbj == 1 ? true: false);
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
				$('#utohouseid').val(rowData.TOHOUSEID);
				$('#utohousename').val(rowData.TOHOUSENAME);
				$('#uhandno').val(rowData.HANDNO);
				$('#utotalcurr').val(rowData.TOTALCURR);
				$('#utotalamt').val(rowData.TOTALAMT);
				$('#uoperant').val(rowData.OPERANT);
				$('#untid').val(rowData.NTID);
				$('#ustatetag').val(rowData.STATETAG);
				$('#uremark').val(rowData.REMARK);
				$("#uorderno").html(rowData.ORDERNO);
				if (rowData.ORDERNO.length > 0) {
					$("#div_orderno").show();
				} else {
					$("#div_orderno").hide();
				}
				dqindex = rowIndex;
				dqamt = rowData.TOTALAMT;
				dqcurr = rowData.TOTALCURR;
			}
		},
		onDblClickCell: function(index, field, value) {
			var row = $(this).datagrid("getRows")[index];
			var noteno = "";
			if (field == "TOHOUSENAME") {
				noteno = row.NOTENO1;
			} else if (field == "ORDERNO") {
				noteno = row.ORDERNO;
			}
			if (noteno.length > 0) {
				var pgno = noteno.substring(0, 2);
				detail_bol = true;
				opendetaild(pgno, noteno);
			} else detail_bol = false;
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
			title: '单据时间',
			width: 110,
			fixed: true,
			hidden: true
		},
		{
			field: 'NOTENO1',
			title: '调出单据',
			width: 110,
			fixed: true,
			hidden: true
		},
		{
			field: 'NOTEDATE0',
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
			field: 'HOUSENAME',
			title: '调出店铺',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'TOHOUSENAME',
			title: '调入店铺',
			width: 130,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (row.NOTENO1 == undefined || row.NOTENO1 == "") return value;
				else return value + "[" + row.NOTENO1 + "]";
			}
		},
		{
			field: 'ORDERNO',
			title: '调拨订单号',
			width: 120,
			align: 'center',
			halign: 'center',
			fixed: true
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
			fixed: true,
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
			width: 150,
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
				if (stg>0&&row.NOTENO1.length==0) return '<input type="button" value="入库" class="m-btn" onclick="addallotinh(' + index + ')">';
				else return "";
			}
		},
		{
			field: 'STATETAG',
			title: '操作状态',
			width: 200,
			hidden: true
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	});
	getnotedata();
	uploader_xls_options.uploadComplete = function(file){
		getnotewarem($("#unoteno").val(),1);
	}
	uploader_xls_options.startUpload = function(){
		if (uploader_xls.getFiles().length > 0) {
			uploader_xls.option('formData', {
				noteno: $('#unoteno').val(),
				tohouseid: $("#utohouseid").val()
			});
			uploader_xls.upload();
		} else {
			alerttext("文件列表为空！请添加需要上传的文件！");
		}
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=addallotoutmxls",
		xlsmobanname: "allotout",
		channel: "allotout"
	});
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
	setTimeout(function() {
		$('#gg').datagrid('resize', {
			height: $('body').height() - 45
		});
	},
	200);
}
//清空搜索条件
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shousename,#shouseid,#shandno,#stohousename,#stohouseid,#swareno,#swareid,#sremark,#sorderno,#soperant').val('');
	$('#st3').prop('checked', true);
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "allotouthlist";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.accid,a.noteno,a.noteno1,a.notedate,a.houseid,a.tohouseid,b.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag,a.orderno";
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
	var tohouseid = Number($('#stohouseid').val());
	var handno = $('#shandno').val();
	var wareid = Number($('#swareid').val());
	var remark = $('#sremark').val();
	var orderno = $('#sorderno').val();
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
		houseid:houseid,
		tohouseid: tohouseid,
		wareid: wareid,
		remark: remark,
		orderno: orderno,
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
var pg = 1;
var sumpg = 1;
var nextpg = true; //一页页加载
//加载商品明细表
function setwarem() {
	$('#waret').datagrid({
		idField: 'ID',
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
	$('#addnotenbtn').attr('disabled', 'disabled');
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
	$('#uhandno').removeAttr('readonly');
	$('#uselecthouseb').removeAttr('disabled');
	$('#uselecttohouseb').removeAttr('disabled');
	$('#isupdatedbar,#div_orderno').hide();
	$('#updatingbar').show();
	$('#wquickuwaretd').show();
	$('#wquickuwareno').val('');
	$('#uremark').removeAttr('readonly');
	$('#uhousename').next().show();
	$('#uhousename').removeAttr('readonly');
	$('#utohousename').next().show();
	$('#utohousename').removeAttr('readonly');
	$('#ud #warem-toolbar').show();
	$('#unotedate').datetimebox({
		readonly: false,
		hasDownArrow: true
	});
	addallotouth();
	$('#ud').dialog({
		title: '增加调拨出库单'
	});
	$('#ustatetag').val('0');
	$('#ud').dialog('open');
	$('#unoteno').focus();
	$('#addnote').removeAttr('disabled');
	$('#ud').data('qickey', "F4:'updateallotouth()',F7:'loadcg($(\"#utohouseid\").val())',Del:'delallotouth()'");
	$('#wquickuwareno').focus();
}
//编辑单据
function updatenoted() {
	if (detail_bol) return;
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
	} else {
		getnotebyid($('#uid').val(), $('#unoteno').val());
	}
}
function quickaddwd() {
	$('#quickuaddwaref')[0].reset();
	$('#quickutable').html('');
	if ($("#utohouseid").val() == '0' || $("#utohouseid").val() == '') {
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
function quickupdatesum() {
	var list = $('#quickutables tr:eq(1) input[type=text]').length; //横向文本框数
	var line = $('#quickutables tr').length - 2; //竖向文本框数
	var totalamount = new Decimal(0);
	var totalcurr = new Decimal(0);
	var count = 0;
	var amount1 = new Array(line);
	var amount2 = new Array(list + 3);
	var quickjsonstr = new Array();
	if (line >= 1 && list >= 1) {
		for (i = 1; i <= line; i++) {
			amount1[i] = new Decimal(0);
			for (j = 1; j <= list + 1; j++) {
				if (j != list + 1) {
					var valuestr = $('#u' + i.toString() + "-" + j.toString()).val();
					var value = Number($('#u' + i.toString() + "-" + j.toString()).val());
					if (valuestr != "" && !isNaN(value)) {
						quickjsonstr.push({
							colorid: $('#uacolor' + i).val(),
							sizeid: $('#uasize' + j).val(),
							amount: value,
							price: $('#upri' + i).html(),
							curr: Number($('#upri' + i).html()) * Number(value)
						});
						count++;
						amount1[i] = amount1[i].add(value);
						totalamount = totalamount.add(value);
					}
				} else {
					$('#usum' + i).html(amount1[i].valueOf());
					var curr = Number((amount1[i] * Number($('#upri' + i).html())).toFixed(points));
					$('#uacurr' + i).html(curr.toFixed(2));
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
	$('#asum' + (list + 3).toString()).html(totalcurr.toFixed(2));
	jsondata = quickjsonstr;
}
var hasTable = false;
function getwaremsum(wareid, noteno) {
	$('#quickutable').html('');
	if ($('#quickutables').length == 0 && hasTable == false) {
		hasTable = true;
		var houseid = Number($("#uhouseid").val());
		var tohouseid = Number($("#utohouseid").val());
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getallotoutmsum",
				noteno: noteno,
				wareid: wareid,
				tohouseid: tohouseid,
				houseid: houseid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					var wareno = data.WARENO;
					var warename = data.WARENAME;
					var retailsale = data.PRICE;
					var price = retailsale;
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
					table.cellSpacing = 0;
					for (var i = 0; i <= line + 1; i++) {
						//alert(line);
						//创建tr
						var tr = document.createElement("tr");
						var curr = 0;
						var amt = 0;
						var price0 = price;
						for (var j = 0; j <= list + 3; j++) {
							//alert(list);
							//创建td
							var td = document.createElement("td");
							if (i == 0) {
								tr.className = "table-header",
								td.style.border = "none";
								if (j == 0) {
									td.style.width = '80px';
									td.innerHTML = '颜色';
								} else if (j == list + 1) {
									td.style.width = '60px';
									td.align = "center";
									td.innerHTML = "小计";
								} else if (j == list + 2) {
									td.style.width = '80px';
									td.innerHTML = "单价";
								} else if (j == list + 3) {
									td.style.width = '100px';
									td.innerHTML = "金额";
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
								} else if (j == (list + 3)) {
									td.align = "right";
									td.id = "asum" + j;
									td.innerHTML = "";
								} else {
									td.align = "center";
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
									td.id = "upri" + i;
									td.align = "right";
									td.innerHTML = Number(price0).toFixed(2);
								} else if (j == list + 3) {
									//td.style.width="100px";
									td.align = "right";
									td.id = "uacurr" + i;
									td.innerHTML = (curr == undefined ? 0 : curr).toFixed(2);
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
												var val = input.value == '' ? 0 : Number(input.value);
												input.value = val + Number(amountlist[r].AMOUNT);
												price0 = Number(amountlist[r].PRICE);
												amt = Number(amountlist[r].AMOUNT) + amt;
												curr = curr + Number(amountlist[r].CURR);
												//break;
											}
										}
										for (r in kclist) {
											var colorids = kclist[r].COLORID;
											var sizeids = kclist[r].SIZEID;
											if (colorids == colorid && sizeid == sizeids) {
												//		 		            	                   input.placeholder=kclist[r].AMOUNT;
												input.setAttribute("placeholder", kclist[r].AMOUNT);
												//$(input).attr('placeholder',kclist[r].AMOUNT);
												break;
											} else {
												input.setAttribute("placeholder", "");
											}
										}
									}
									// 		        	                     input.onkeyup = function(e){
									// 		        	                    	if(this.value!="-"){
									// 			        	                    		this.value = Number(this.value.replace(/[^-?[1-9]d*$]/g,''))==0?"":Number(this.value.replace(/[^-?[1-9]d*$]/g,''));
									// 		         	                     			input.onafterpaste = Number(this.value.replace(/[^-?[1-9]d*$]/g,''))==0?"":Number(this.value.replace(/[^-?[1-9]d*$]/g,''));
									// 			        	                 			quickupdatesum(line,list);
									// 											}
									// 		        	                     	}
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
					$('input[type=text]').placeholder();
					quickupdatesum();
					var amtl = getJsonLength(amountlist);
					var tname = amtl == 0 ? "添加": "修改";
					var title = tname + "商品明细：" + wareno + "，" + warename;
					$('#quickud').dialog({
						title: title,
						width: list * 51 + 360,
						modal: true
					});
					if ($('#showallotouthistory').prop('checked')) {
						if ($('#allotoutwarehistoryt').data().datagrid == undefined) {
							setallotoutwarehistt();
						}
						$('#allotoutwarehistorydiv').show();
						$('#allotoutwarehistoryt').datagrid('resize');
						listsalehistory(1);
					} else {
						$('#allotoutwarehistorydiv').hide();
					}
					$('#quickud').window('center');
					$('#quickud').dialog('open');
					var out_wid = list * 51 + 320;
					$('#quickutables').width(out_wid);
					$('#u1-1').focus();
				}
				hasTable = false;
			}
		});
	}
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
				erpser: "writeallotoutmsum",
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
					$("#wquickuwareno").parent().children("ul").html("");
					$("#wquickuwareno").parent().children("ul").hide();
					$('#quickud').dialog('close');
					$('#wquickuwareno').focus();
				}

			}
		});
	}
}
//编辑商品框
function updatewd() {
	$('#quickuaddwaref')[0].reset();
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
						erpser: "delallotoutmsum",
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
		alerttext("请选择一行数据，进行编辑");
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
		data: {
			erpser: "allotoutmcolorsumlist",
			noteno: noteno,
			sizenum: sizenum,
			sortid: sortid,
			order: order,
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
				$('#warepp').createPage({
					pageCount: sumpg,
					current: Number(page)
				});
				$('#wtotalamt').val(totalamt);
				$('#wtotalcurr').val(Number(totalcurr).toFixed(2));
				$('#waret').datagrid('loading');
				if (page == '1') {
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
				dqamt = totalamt;
				dqcurr = totalcurr;
				dqzamt = Number(dqzamt) - Number(dqamt) + Number(totalamt);
				dqzcurr = Number(dqzcurr) - Number(dqcurr) + Number(totalcurr);
			}
		}
	});
}

//添加生成调拨出库单
function addallotouth() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addallotouth",
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
				$('#uhandno').val('');
				dqindex = 0;
				dqamt = 0;
				dqcurr = 0;
				$("#qsnotevalue").val("");
				$('#waret').datagrid('loadData', nulldata);
				getnotedata();
			}
		}
	});
}
function delnote(index){
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	$('#uid').val(row.ID);
	$('#unoteno').val(row.NOTENO);
	delallotouth();
}
//删除单据
function delallotouth() {
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
					erpser: "delallotouthbyid",
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

//选择店铺
function changehouse(id, noteno, houseid) {
	if (Number($('#utohouseid').val()) == Number(houseid)) {
		alerttext('调出店铺不能与调入店铺一致');
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateallotouthbyid",
				id: id,
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
		});
	}
}
//修改调拨出库单
function updateallotouth() {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	var houseid = $('#uhouseid').val();
	var tohouseid = $('#utohouseid').val();
	var statetag = $('#ustatetag').val();
	var totalcurr = $('#wtotalcurr').val().replace(/[ ]/g, "");
	var totalamt = $('#wtotalamt').val().replace(/[ ]/g, "");
	var amt = $('#waret').datagrid('getRows').length;
	var notedatestr = $('#unotedate').datetimebox('getValue');
	if (notedatestr.length != 19) {
		alerttext("请选择正确格式单据日期如:2005-01-01 18:18:18");
		return;
	}
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
						erpser: "updateallotouthbyid",
						statetag: "1",
						noteno: noteno,
						houseid: houseid,
						tohouseid: tohouseid,
						totalcurr: totalcurr,
						notedatestr: notedatestr,
						totalamt: totalamt
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							$('#gg').datagrid('updateRow', {
								index: dqindex,
								row: {
									STATETAG: '1'
								}
							});
							$('#gg').datagrid('refresh');
							$('#ud').dialog('close');
						}
					}
				});
			}
		});
	}

}
//选择调出店铺先判断是否一样
function changetohouse(id, noteno, tohouseid) {
	if (Number($('#uhouseid').val()) == Number(tohouseid)) {
		alerttext('调入店铺不能与调出店铺一致');
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateallotouthbyid",
				id: id,
				noteno: noteno,
				tohouseid: tohouseid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#gg').datagrid('updateRow', {
						index: dqindex,
						row: {
							TOHOUSENAME: $('#utohousename').val(),
							TOHOUSEID: tohouseid
						}
					});
					getnotewarem(noteno, '1');
				}
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
			erpser: "updateallotouthbyid",
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
			erpser: "updateallotouthbyid",
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
						erpser: "allotouthcancel",
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
								$('#notetotal').html('共有' + (dqzjl - 1) + '条记录');
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
//载入调拨订单表
function loadcg(tohouseid) {
	if (tohouseid == "0") {
		alerttext("请选择调入店铺！");
	} else {
		$('#loadd').dialog({
			modal: true
		});
		$('#loadd').dialog('open');
	}

}

function resetload() {
	$("#loadform input").val("");
}
//载入到出库单
function loadnote() {
	var noteno = $("#unoteno").val();
	var houseid = $('#uhouseid').val();
	var tohouseid = $('#utohouseid').val();
	var wareid = $('#lwareid').val();
	var typeid = $('#ltypeid').val();
	var brandid = $('#lbrandid').val();
	var seasonname = $('#lseasonname').val();
	var prodyear = $('#lprodyear').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "loadallotorderm",
			noteno: noteno,
			houseid: houseid,
			wareid: wareid,
			typeid: typeid,
			brandid: brandid,
			seasonname: seasonname,
			prodyear: prodyear,
			tohouseid: tohouseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#loadd').dialog('close');
				getnotewarem(noteno, '1');
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
			erpser: "getallotouthbyid",
			noteno: noteno,
			id: id,
			fieldlist: "a.ID,a.NOTENO,a.noteno1,a.ACCID,a.NOTEDATE,a.HOUSEID,a.TOHOUSEID,a.NTID,a.REMARK,"
				+ "a.HANDNO,a.OPERANT,a.cleartag,"
				+ "a.CHECKMAN,a.STATETAG,a.LASTDATE,B.HOUSENAME,c.pricetype1"
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
						$('#ud').dialog("setTitle", "浏览调拨出库单");
						$('#uhandno').attr('readonly', 'readonly');
						$('#uselecthouseb').attr('disabled', 'disabled');
						$('#uselecttohouseb').attr('disabled', 'disabled');
						$('#updatingbar').hide();
						$('#isupdatedbar').show();
						$('#wquickuwaretd').hide();
						$('#uremark').attr('readonly', 'readonly');
						$('#uhousename').attr('readonly', 'readonly');
						$('#uhousename').next().hide();
						$('#utohousename').attr('readonly', 'readonly');
						$('#utohousename').next().hide();
						$('#ud #warem-toolbar').hide();
						//$('#waret').datagrid('hideColumn','ACTION');
						var levelid = getValuebyName("GLEVELID");
						if (levelid == '0' || allowchedan == '1') {
							$('#ud').data('qickey', "F9:'$(\"#print\").click()',F10:'withdraw()'");
						} else {
							$('#ud').data('qickey', "F9:'$(\"#print\").click()'");
						}
						$('#unotedate').datetimebox({
							readonly: true,
							hasDownArrow: false
						});
					} else {
						$('#ud').dialog("setTitle", "修改调拨出库单");
						$('#uhousename').removeAttr('readonly');
						$('#uhandno').removeAttr('readonly');
						$('#uselecthouseb').removeAttr('disabled');
						$('#uselecttohouseb').removeAttr('disabled');
						$('#isupdatedbar').hide();
						$('#updatingbar').show();
						$('#wquickuwaretd').show();
						$('#wquickuwareno').val('');
						$('#uremark').removeAttr('readonly');
						$('#uhousename').removeAttr('readonly');
						$('#uhousename').next().show();
						$('#utohousename').removeAttr('readonly');
						$('#utohousename').next().show();
						$('#ud #warem-toolbar').show();
						//$('#waret').datagrid('showColumn','ACTION');
						$('#ud').data('qickey', "F4:'updateallotouth()',F7:'loadcg($(\"#utohouseid\").val())',Del:'delallotouth()'");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
					}
					$('#ud').dialog('open');
					if ($('#ustatetag').val() == '1') {
						$('#unoteno').focus();
					} else {
						$('#wquickuwareno').focus();
					}
					getnotewarem($('#unoteno').val(), "1");
					pg = 1;
					var istoday = row.ISTODAY;
					var pricetype1 = row.PRICETYPE1;
					$('#uistoday').val(istoday);
					$('#upricetype1').val(pricetype1);
					var cleartag = row.CLEARTAG;
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					if ((Number(istoday) == 1 || allowchangdate == 1) && allowchedan == '1' && cleartag == '0' && stg == "1") {
						$('#sep-withdraw,#withdrawbtn').show();
						$('#clearnotebar').hide();
					} else if (istoday == "0" && Number(allowclear) > 0 && cleartag == '0' && stg == "1") {
						$('#clearnotebar').show();
						$('#sep-withdraw,#withdrawbtn').hide();
					} else {
						$('#clearnotebar').hide();
						$('#sep-withdraw,#withdrawbtn').hide();
					}
				}
			}
		}
	});
}
var allotorderser = true;
function openallotorderd() {
	var houseid = Number($('#uhouseid').val());
	var tohouseid = Number($('#utohouseid').val());
	if (houseid > 0 && tohouseid > 0) {
		if (allotorderser) setallotordert();
		$('#allotorderd').dialog('open');
		loadallotorderhlist('1');
	} else alerttext('请选择店铺');
}
function setallotordert() {
	$('#allotorderpp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			loadallotorderhlist(p.toString());
		}
	});
	$('#allotordert').datagrid({
		idField: 'ID',
		width: '100%',
		height: 280,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 30,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return index+1;
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			width: 120,
			fixed: true,
			halign: 'center',
			align: 'center'
		},
		{
			field: 'NOTEDATE',
			title: '单据日期',
			width: 110,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return row.NOTEDATE == undefined ? '': row.NOTEDATE.substring(0, 16);
			}
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 100,
			fixed: true,
			halign: 'center',
			align: 'center'
		},
		{
			field: 'TOTALCURR',
			title: '总金额',
			width: 100,
			fixed: true,
			halign: 'center',
			align: 'right',
			formatter: function(value, row, index) {
				return Number(value).toFixed(2);
			}
		},
		{
			field: 'TOTALAMT',
			title: '订货数',
			width: 60,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'TOTALFACTAMT',
			title: '已发货',
			width: 80,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'BALAMT',
			title: '未发货',
			width: 80,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				var totalamt = Number(row.TOTALAMT);
				var totalfactamt = Number(row.TOTALFACTAMT);
				if (!isNaN(totalamt)) {
					var balamt = totalamt - totalfactamt;
					return balamt;
				}
				return "";
			}
		},
		{
			field: 'OPERANT',
			title: '制单人',
			width: 200,
			halign: 'center',
			align: 'center'
		}]],
		onDblClickRow: function(index, data) {
			loadallotordernote();
		},
		pageSize: 20
	}).datagrid("keyCtr", "loadallotordernote()");
}
function loadallotorderhlist(page) {
	alertmsg(6);
	var houseid = $('#utohouseid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "allotorderhnotover",
			houseid: houseid,
			rows: pagecount,
			fieldlist: "*",
			sort: "notedate,id",
			order: "asc",
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#allotorder_total').html("共有" + data.total + "条记录");
					$('#allotorderpp').setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					data.footer = [{
						NOTENO: "合计",
						TOTALAMT: data.totalamt,
						TOTALFACTAMT: data.totalfactamt,
						TOTALCURR: data.totalcurr
					}];
					$('#allotordert').datagrid('loadData', data);
				}
			} catch(e) {
				console.error(e);
			}
		}
	});

}
function loadallotordernote() {
	var row = $('#allotordert').datagrid("getSelected");
	if (row) {
		var orderno = row.NOTENO;
		var noteno = $('#unoteno').val();
		if(noteno==""){
			alerttext("当前被载入的调拨出库单单据无效，请重试！");
			return;
		}
		var houseid = "0";
		if ($('#loadkc').prop('checked')) houseid = $('#uhouseid').val();
		var dofn = function() {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "allotordertoout",
					noteno: noteno,
					orderno: orderno,
					houseid: houseid
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							getnotewarem(noteno, '1');
							$('#gg').datagrid('updateRow', {
								index: dqindex,
								row: {
									ORDERNO: orderno
								}
							});
							$("#div_orderno").show();
							$("#uorderno").html(orderno);
						}
					} catch(e) {
						console.error(e);
					}
				}
			});
			$('#allotorderd').dialog('close');
		}
		var msg = "是否确认载入调拨订单号:" + orderno + "！";
		var rows = $('#waret').datagrid('getRows');
		if (rows.length > 0) {
			msg = "发现该调拨出库单子不是空单，载入后将清空原有商品明细，" + msg;
			$.messager.confirm(dialog_title, msg,
			function(r) {
				if (r) {
					dofn();
				}
			});
		} else {
			dofn();
		}
	} else alerttext("请选择单据！");
}
function addallotinh(index){
	var $dg = $("#gg");
	var row = $dg.datagrid("getRows")[index];
	if(!row){
		alerttext("单据无效！");
		return;
	}
	var noteno = row.NOTENO;
	$.messager.confirm(dialog_title, "确定将此调拨单直接生成调拨入库单",
			function(r) {
				if (r) {
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "addallotinh",
							fromnoteno: noteno,
							fs: 1
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									$dg.datagrid("updateRow",{
										index: index,
										row: {
											NOTENO1: noteno
										}
									});
								}
							} catch(e) {
								console.error(e);
							}
						}
					});
				}
	});
}
$('#gg').datagrid({
    onLoadSuccess : function() {
        var $footer_t_b = $('.datagrid-footer .datagrid-footer-inner table tbody');
        var $footer_prev_b = $('.datagrid-footer').prev('.datagrid-body');
        var itemIndex = $footer_t_b.find('tr').last().attr('datagrid-row-index');
        if (itemIndex) {
            var $first_tr = $footer_t_b.find('tr');
            var lineHeight = $first_tr.first().height();
            var bodyHeight = $footer_prev_b.height();
            var tdCount = $first_tr.find('td:visible').size();
            itemIndex = parseInt(itemIndex) + 1;
            $('.datagrid-footer').prev('.datagrid-body').css('height', bodyHeight - lineHeight);
            $footer_t_b.append("<tr class='datagrid-row' datagrid-row-index='"+ itemIndex +"'>" +
                "<td></td>" +
                "<td colspan='"+ (tdCount - 1) +"'>" +
                "<b style='color:#000000;'>单据号：</b>" +
                //"<b style='color:#ff3b30;'>红色提交收银台未付款；</b>" +
                "<b style='color:#00959a;margin-left: 5px;'>绿色已提交；</b>" +
                "<b style='color:#ff7900;margin-left: 5px;'>黄色未提交。</b>" +
                //"<b style='color:#ff7900;margin-left: 15px;'>失效订单</b>" +
                "</td>" +
                "</tr>");
        }
    }
});
</script>
	<!-- 条码增加 -->
<jsp:include page="../HelpList/BarcodeAdd.jsp"></jsp:include>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
		<!-- 类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 无折扣商品明细弹窗 -->
	<jsp:include page="../HelpList/NoDiscWarem.jsp"></jsp:include>
		<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
		<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
		<jsp:include page="../HelpList/ExportHelp.jsp"></jsp:include>
		<jsp:include page="../HelpList/NoteCheckHelp.jsp"></jsp:include>
</body>
</html>