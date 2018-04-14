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
<title>零售开票</title>
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
<div id="toolbars"  class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addnoted()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、店铺、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20" data-enter="getnotedata()"><input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn" style="width:20%">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
	<input class="btn3" type="button" value="导入" onclick="openimportd()">
<!-- 	<span class="sepreate hide"></span> -->
<!-- 	<input type="button" class="btn3"  value="导出" onclick="exportxls()"> -->
	</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" id="ssalemanid">
<input type="hidden" name="shouseid" id="shouseid" > 
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
<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;店铺</div>
<div class="text">
<input class="edithelpinput house_help" id="shousename" type="text" data-value="#shouseid" placeholder="<输入>"><span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>
<div class="s-box"><div class="title">会员卡号</div>
<div class="text">
<input class="hig25 wid147" id="svipno" type="text" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">会员姓名</div>
<div class="text">
<input class="hig25 wid147" id="sguestname" type="text" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">销售人</div>
<div class="text">
<input class="edithelpinput saleman_help" id="ssalemanname" data-value="#ssalemanid" type="text" placeholder="<输入>"><span class="s-btn" onclick="selectemploye('search');"></span>
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput " id="swareno" type="text" data-value="#swareid" placeholder="<输入>"><span class="s-btn" onclick="selectware('search')"></span>
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
	<div class="page-total">共<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 添加与修改零售开票表单 -->
<div id="ud" title="修改店铺零售单" style="width: 100%;height:100%;" class="easyui-dialog" closed="true" data-qickey>
<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4 skbtn" type="button" data-btn="updatebtn" id="pay" value="结账" onclick="openpay()"></span>
<!-- 	<span class="sepreate "></span> -->
	<span><input class="btn4  sytbtn" type="button" id="pay" value="提交至收银台" onclick="submitwareouth()"></span>
	<span class="sepreate "></span>
	<span><input class="btn4" type="button" id="delete" value="删除单据" onclick="delwaresaleh()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="discount" value="整单打折" onclick="opendisc()"></span>
	</span>
	<span id="ispayedbar">
	<span><input class="btn4" type="button" id="paydetailbtn" value="收款明细" onclick="openpaydetail()"></span>
	<span class="sepreate" id="sep-withdraw"></span>
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="withdraw()"></span>	
	</span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="print" value="后台打印"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="selprint" value="选择打印端口" onclick="selprint()"></span>
	<!-- 单据提交框 -->
	<form id="udnoteform" action="" method="get">
	<div id="udnote">
	<input type="hidden" id="uid"> 
	<input type="hidden" id="untid">
	<input type="hidden" id="uepid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="epid"> 
	<input type="hidden" id="uguestid">
	<input type="hidden" id="vtid">
	<input type="hidden" id="udptid">
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
	<div class="text"><input class="edithelpinput house_help" type="text" id="uhousename" data-value="changenotehouse"><span onclick="selecthouse('update')"></span>
	</div>
	</div>
<div class="s-box"><div class="title">销售</div>
	<div class="text"><input class="edithelpinput" type="text" id="usalemanname" data-value="#epid" placeholder="<选择>" readonly><span id="selprovspan" onclick="selectsaleman('update')" ></span>
	</div>
	</div>
<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" class="hig25 wid162" autocomplete="off" id="uhandno" placeholder="<输入>" onchange="changehandno(this.value)">
	</div>
	</div>
<div class="s-box"><div class="title">销售部门</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="edithelpinput dpt_help" data-value="changedpt" id="udptname" placeholder="<输入>">
	<span onclick="selectdepartment('update')"></span>
	</div>
	</div>
	<div class="clearbox">
	<div class="title">摘要</div>
	<div class="text"><input maxlength="100" type="text" id="uremark" autocomplete="off" class="hig25 wid_remark" placeholder="<输入>" onchange="changeremark(this.value)">
	</div>
	</div>
<div class="clearbox"><div class="title">会员</div>
	<div class="text">
	  <div class="combox-select">
	  <span class="icon_barcode" onclick="changesetvip()" title="切换选择输入会员方式"></span>
	  <input type="text" class="hig25 hide combox-input" style="width:130px;color:#fff;" id="vipbarcode" title="扫会员二维码输入" placeholder="<扫会员二维码输入>">
	  <input class="vip_help combox-input selvip" id="uguestname" style="width: 110px;" type="text" maxlength="20" placeholder="<选择>" autocomplete="off" data-value="viplist" readonly>
	  <span class="help-s-btn selvip" onclick="$(this).parent().next().click()">▼</span>
	<ul class="combox-selects" style="display: none;"></ul>
	</div>	  
	<span id="selprovspan" onclick="selectvip('update')"></span>
	</div>
	<input type="hidden" id="uusecent">
	<input type="hidden" id="ujfcurr">
	<table cellspacing="5" border="0"  id="guestinfo" class="hide">
	<tr>
	<td align="right" id="cancelvipbtn"><input type="button" class="btn2" value="取消会员" onclick="cancelguest()"></td>
	<td width="30" align="right">会员卡号</td>
	<td align="left" width="80" id="vipno">
	</td><td width="30" align="right">会员电话</td>
	<td align="left" width="80" id="mobile">
	</td><td width="30" align="right">积分余额</td>
	<td align="left" width="40" id="balcent"></td>
	<td width="30" align="right">储值余额</td>
	<td align="left" width="40" id="balcurr">
	</td><td width="30" align="right">本次积分</td>
	<td align="left" width="40" id="cent"></td>
	<td width="30" align="right">会员类型</td>
	<td align="left" width="60" id="vtname"></td></tr>
	</table>
	</div>
</div>
	</div>
	<div class="warem-toolbars">
		<div class="fl" style="cursor: pointer;"><table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="warembar" onclick="updown()" style="color:#ff7900">▲&nbsp;&nbsp;商品明细</td>
		<td id="wquickuwaretd"><input type="text" id="wquickuwareno"  class="edithelpinput wid145" placeholder="<选择或输入货号>" data-value="quickaddwarem" data-end="yes"><span onclick="if(isAddWarem()){selectware('quickupdatew')}"></span></td>
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
	</form>
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
	<p><input class="hig25" id="alldiscount" type="text" placeholder="<请输入折扣>" data-enter="alldisc()"/></p>
	</div>
	<div class="dialog-footer">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#discd').dialog('close')">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="保存" onclick="alldisc()">
	</div>
	</div>
	<!-- 结账框 -->
<div id="payd" title="结账" style="width: 600px;" class="easyui-dialog" closed="true" data-qickey="F4:'updatewaresaleh(false)',Z:'quick_zf(0)'">
<div style="text-align: center;">
<form action="" method="get" id="payform">
<input type="hidden" id="newask" value="1">
<table class="table1" border="0" cellspacing="10" cellpadding="0" width="600">
  <tr>
    <td width="70" align="right" class="header">合计金额</td>
    <td align="right" width="170"><input class="hig25 wid160" type="text" id="totalpay" readonly></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">免单金额</td>
    <td align="right"><input class="hig25 wid160" type="text" id="freepay" class="onlyMoney" onchange="getactpay()" ></td>
  </tr>
  <tr>
    <td align="right" class="header usecent">积分抵扣</td>
    <td align="right" class="usecent"><input type="text" id="usecent" style="width: 115px;height:25px" class="onlyNum" onchange="getjfcurr();">
    <input class="hig25" type="text" id="jfcurr" style="width: 40px;" value="0" readonly>
    </td>
<!--   </tr> -->
<!--   <tr id="isuseyhq"> -->
    <td align="right" class="header useyhq">优惠券</td>
    <td align="right" class="useyhq">
	<select class="hig25" id="yhjcurr" style="width: 115px;" >
	<option value="0" data-xfcurr="0" data-vcpid="0">不使用优惠券</option>
	</select>
	<input class="hig25" style="width: 70px;" type="text" id="yhjcurrx" style="width: 70px;" value="0" readonly>
	</td>
  </tr>
  <tr>
    <td align="right" class="header">应结金额</td>
    <td align="right"><input class="hig25  wid160" type="text" id="actpay" readonly></td>
  </tr>
  <tr id="payway" class="hide"></tr>
  <tr>
    <td align="right" class="header">收款合计</td>
    <td align="right"><input class="hig25  wid160" type="text" id="sumpay" readonly></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">找零</td>
    <td align="right"><input class="hig25  wid160" type="text" id="changecurr" readonly></td>
  </tr>
</table>
</form>
</div>
<div class="dialog-normal-footer">
		<input type="button" class="btn1 hide" id="sendvipmsg" value="发送会员验证" onclick="sendvipmessage()">
		<input type="button" class="btn1" id="btn_pay" style="width:70px;margin-right: 10px" name="" value="结账" onclick="updatewaresaleh(false)">
		<input type="button" class="btn1" id="btn_pay_print" style="margin-right: 10px" name="" value="结账并打印" onclick="updatewaresaleh(true)">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#payd').dialog('close')">
</div>
</div>
<!-- 结账明细框 -->
<div id="paydetaild" title="结账明细" style="width: 300px; height: 400px" class="easyui-dialog" closed="true">
<div style="text-align: center;">
	<table class="table1" border="0" cellspacing="5" cellpadding="5" width="280">
  <tr>
    <td width="67" class="header" align="right">合计金额</td>
    <td width="170" align="right"><input class="hig25 wid-in-td" type="text" id="totalpaym" readonly></td>
  </tr>
  <tr>
    <td class="header" align="right">免单金额</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="freepaym" readonly></td>
  </tr>
  <tr id="centdivm">
    <td class="header" align="right">积分抵扣</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="jfcurrm" readonly></td>
  </tr>
  <tr id="yhqdivm">
    <td class="header" align="right">优惠券</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="yhjcurrm" readonly></td>
  </tr>
  <tr>
    <td class="header" align="right">应结金额</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="actpaym" readonly></td>
  </tr>
  <tr id="paywaym" class="hide"></tr>
  <tr>
    <td class="header" align="right">收款合计</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="sumpaym" readonly></td>
  </tr>
  <tr>
    <td class="header" align="right">找零</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="changecurrm" readonly></td>
  </tr>
</table>
</div>
</div>
<script type="text/javascript">
var levelid = getValuebyName("GLEVELID");
var pgid = 'pg1201';
var f4 = "setwareoutpaylist()";
setqxpublic();
if (usesyt == 0) $('.sytbtn').remove();
else $('.skbtn').remove();
var defsalemanbj = 1;
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
var sizenum = getValuebyName('SIZENUM');
var cols = Number(sizenum) <= 5 ? 5 : Number(sizenum);
cols = cols >= 15 ? 15 : cols;
function setColums() {
	var colums = [];
	colums[0] = {
		field: 'ROWNUMBER',
		title: '序号',
		fixed: true,
		width: 30,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			return value == "" ? "": index + 1;
		}
	};
	colums[1] = {
		field: 'WAREID',
		title: 'WAREID',
		hidden: true,
		width: 10
	};
	colums[2] = {
		field: 'WARENO',
		title: '货号',
		width: 6,
		halign: 'center',
		styler: function(value, row, index) {
			if (value == "合计") {
				return 'text-align:center';
			} else {
				return 'text-align:left';
			}
		}
	};
	colums[3] = {
		field: 'WARENAME',
		title: '商品名称',
		width: 12,
		halign: 'center',
		align: 'left'
	};
	colums[4] = {
		field: 'UNITS',
		title: '单位',
		width: 40,
		fixed: true,
		halign: 'center',
		align: 'center'
	};
	colums[5] = {
		field: 'COLORID',
		title: '颜色id',
		width: 10,
		hidden: true
	};
	colums[6] = {
		field: 'COLORNAME',
		title: '颜色',
		width: 6,
		halign: 'center',
		align: 'center'
	};
	for (var i = 1; i <= cols; i++) {
		colums[6 + i] = {
			field: 'AMOUNT' + i,
			title: '<span id="title' + i + '"><span>',
			width: 35,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return value == '' ? '': Number(value) == 0 ? '': value;
			}
		};
	}
	colums[cols + 7] = {
		field: 'AMOUNT',
		title: '小计',
		width: 50,
		fixed: true,
		halign: 'center',
		align: 'center'
	};
	colums[cols + 8] = {
		field: 'PRICE0',
		title: '原价',
		width: 7,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	};
	colums[cols + 9] = {
		field: 'DISCOUNT',
		title: '折扣',
		width: 40,
		fixed: true,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	};
	colums[cols + 10] = {
		field: 'PRICE',
		title: '单价',
		width: 7,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	};
	colums[cols + 11] = {
		field: 'CURR',
		title: '金额',
		width: 10,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': (Number(value).toFixed(2));
		}
	};
	colums[cols + 12] = {
		field: 'SALENAME',
		title: '销售类型',
		width: 70,
		fixed: true,
		halign: 'center',
		align: 'center'
	};
	colums[cols + 13] = {
		field: 'SALEID',
		title: '销售类型id',
		width: 70,
		fixed: true,
		hidden: true
	};
	for (var i = 1; i <= cols; i++) {
		colums[cols + 13 + i] = {
			field: 'SIZEID' + i,
			title: 'sizeid',
			hidden: true,
			width: 10
		};
	}
	for (var i = 1; i <= cols; i++) {
		colums[cols + cols + 13 + i] = {
			field: 'SIZENAME' + i,
			title: 'sizename',
			hidden: true,
			width: 10
		};
	}
	return colums;
}
function isAddWarem() {
	var houseid = $('#uhouseid').val();
	var salemanname = $('#usalemanname').val();
	var guestid = $('#uguestid').val();
	if (houseid == '0' || houseid == '' || houseid == undefined) {
		alerttext('请选择店铺');
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#uhousename').focus();
		return false;
	} else if (salemanname == '' || salemanname == undefined) {
		alerttext("请选择销售人员");
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#usalemanname').focus();
		return false;
	} else {
		return true;
	}
}
var opser = 'get';
var creditok = '0';
var notedateobj = {
	id: "#uid",
	noteno: "#unoteno",
	notedate: "#unotedate",
	action: "updatewaresalehbyid"
}
$(function() {
	setvipbarcode('#vipbarcode');
	setskypay({
		ywtag: 0,
		noteno: '#unoteno',
		amt: function() {
			return $('#waret').datagrid('getFooterRows')[0].AMOUNT;
		},
		totalcurr: '#actpay',
		curr: function(payfs) {
			return $("input.paycurr.payfs"+payfs).val();
		},
		onBeforePay: function(payfs) {
			var totalcurr = Number($('#totalpay').val());
			var jfcurr = $('#jfcurr').val() == "" || $('#jfcurr').val() == "0" ? 0.00 : Number($('#jfcurr').val());
			var changecurr = Number($('#changecurr').val());
			var freecurr = $('#freepay').val() == '' || $('#freepay').val() == '0' ? 0.00 : Number($('#freepay').val());
			var usecent = $('#usecent').val() == '' || $('#usecent').val() == '0' ? 0 : Number($('#usecent').val());
			var checkcurr = Number($('#actpay').val());
			var zpaycurr = Number($('#sumpay').val());
			var yhjcurr = Number($('#yhjcurr').val());
			if (totalcurr - freecurr - jfcurr < yhjcurr && checkcurr == 0) yhjcurr = totalcurr - freecurr - jfcurr;
			var vcpid = Number($('#yhjcurr :selected').data("vcpid"));
			var notedatestr = $('#unotedate').datetimebox('getValue');
			if (notedatestr.length != 19) {
				alerttext("请选择单据日期!");
				return false;
			}
			if (Number(zpaycurr) != Number(checkcurr) && checkcurr >= 0) {
				alerttext('收款合计不等于应结金额，请输入！');
				$("input.paycurr.payfs"+payfs).focus();
				return false;
			}
			return true;
		},
		onSuccess: function(payfs, curr) {
			$("input.paycurr.payfs"+payfs).val(curr.toFixed(2));
			$("input.paycurr.payfs"+payfs).prop('readonly', true);
			$("input.paybtn.payfs"+payfs).prop('disabled', true);
			//$('#pay1').focus();
			sumpay();
			if (Number($('.payw').val()) > 0) {
				if (skypayedbj.wxpay == 0) {
					alerttext('微信支付未完成，请支付！');
					return;
				}
			}
			if (Number($('.payz').val()) > 0) {
				if (skypayedbj.zfbpay == 0) {
					alerttext('支付宝支付未完成，请支付！');
					return;
				}
			}
			var errorpaymsg = "";
			$(".payqtzf").each(function(){
				var $this = $(this);
				var payname = $this.data("payname");
				var sucstg = Number($this.data("sucstg"));
				var curr = Number($this.val());
				if(curr>0){
					if(sucstg==0){
						errorpaymsg += payname+" ";
					}
				}
			});
			if (errorpaymsg.length > 0) {
				alerttext(errorpaymsg + '支付未完成，请重新支付！');
				return;
			}
			updatewaresaleh(true);
		}
	});
	$('body').delegate('.datagrid', 'keydown',
	function(e) {
		var key = e.which;
		if (key == 113) {
			addnoted();
		}
	});
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setaddbackprint("1201");
	$('#yhjcurr').change(function(e) {
		var val = Number(this.value);
		if (!isNaN(val)) {
			$('#yhjcurrx').val(val.toFixed(2));
			getactpay();
			sumpay();
			checksendmsg();
		}
	});
	$('div.div_sort .label_sort').click(function() {
		var $this = $(this);
		if ($this.hasClass('icon_jiantou_up')) $this.removeClass("icon_jiantou_up").addClass("icon_jiantou_down");
		else $this.removeClass("icon_jiantou_down").addClass("icon_jiantou_up");
		getnotewarem($('#unoteno').val(), "1");
	});
// 	$('#payd').delegate('#usecent,.payvcurr', 'keyup',
// 	function(e) {
// 		checksendmsg();
// 	});
	wareoutparams = getparam({
		pgid: "wareoutcs",
		usymbol: "wareoutparams" 
	}); 
	if (wareoutparams == "") {
		setparam({
			pgid: "wareoutcs",
			usymbol: "wareoutparams",
			uvalue: defwareoutparams
		});
		wareoutparams = defwareoutparams;
	} else {
		defsalemanbj = Number(wareoutparams.charAt(1));
	}
	setwarem();
	$('#guestinfo').hide();
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
		nowarp: false,
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			$('#udnoteform')[0].reset();
			$('#udnoteform input[type=hidden]').val('');
			$('#uhouseid').val(getValuebyName('HOUSEID'));
			$('#uhousename').val(getValuebyName('HOUSENAME'));
			$('#uepid').val(getValuebyName('EPID'));
			if (rowData) {
				$('#uid').val(rowData.ID);
				$('#unoteno').val(rowData.NOTENO);
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				$('#uhandno').val(rowData.HANDNO);
				$('#wtotalcurr').val(rowData.TOTALCURR);
				$('#wtotalamt').val(rowData.TOTALAMT);
				$('#uoperant').val(rowData.OPERANT);
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
			field: 'ROWNUMBER',
			title: '序号',
			width: 40,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
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
				} else if (row.STATETAG == '3') {
					return 'color:#ff3b30;font-weight:900';
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
			field: 'HOSEID',
			title: '店铺id',
			width: 200,
			hidden: true
		},
		{
			field: 'HOUSENAME',
			title: '店铺名称',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'HANDNO',
			title: '自编号',
			width: 150,
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
			field: 'GUESTNAME',
			title: '会员',
			width: 80,
			align: 'center',
			halign: 'center',
			fixed: true
		},
		{
			field: 'FREECURR',
			title: '免单金额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'JFCURR',
			title: '积分抵扣',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'YHJCURR',
			title: '优惠券额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYLIST',
			title: '收款方式',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'SALEMANLIST',
			title: '销售人',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return (value != "" && value != undefined && value != null) ? value: '';
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
				if (stg==0&&epname==operant)
					return '<input type="button" value="删除" class="m-btn" onclick="delnote(' + index + ')">';
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
	}).datagrid('keyCtr', "updatenoted()");
	getnotedata();
	uploader_xls_options.uploadComplete = function(file){
		getnotedata();
	}
	uploader_xls_options.startUpload = function(){
		if (uploader_xls.getFiles().length > 0) {
			uploader_xls.upload();
		} else {
			alerttext("文件列表为空！请添加需要上传的文件！");
		}
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=excel2wareout",
		xlsmobanname: "waresaleh",
		channel: "waresaleh"
	});
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
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#ssalemanid,#ssalemanname').val('');
	$('#shandno').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sremark').val('');
	$('#svipno').val('');
	$('#sguestname').val('');
	$('#st3').prop('checked', true);
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "waresalehlist";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.accid,a.noteno,a.notedate,a.houseid,a.jfcurr,a.freecurr,a.yhjcurr,c.housename,a.totalcurr,a.totalamt,a.operant,a.statetag,a.remark,a.paylist,a.guestid,b.guestname";
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
	var houseid = $('#shouseid').val();
	var salemanid = $('#ssalemanid').val();
	var guestname = $('#sguestname').val();
	var vipno = $('#svipno').val();
	var handno = $('#shandno').val();
	var wareid = $('#swareid').val();
	var guestname = $('#sguestname').val();
	var vipfindbox = $('#svipno').val();
	var remark = $('#sremark').val();
	var statetag;
	if ($('#st1').prop('checked')) {
		statetag = "0";
	} else if ($('#st2').prop('checked')) {
		statetag = "1";
	} else {
		statetag = "2";
	}
	currentdata = {
		houseid: Number(houseid),
		handno: handno,
		guestname: guestname,
		vipfindbox: vipfindbox,
		wareid: Number(wareid),
		salemanid: Number(salemanid),
		remark: remark,
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
	resetvipipt();
	$('#udnote').show();
	$('#warembar').html('▲&nbsp;&nbsp;商品明细');
	setTimeout(function() {
		$('#waret').datagrid('resize', {
			height: $('body').height() - 90
		});
	},
	200);
	d = false;
	$('#addnotenbtn').hide();
	$('#ud input[type=text]').val('');
	$('#ud input[type=hidden]').val('');
	$('#guestinfo').hide();
	$('#uhouseid').val(getValuebyName('HOUSEID'));
	$('#uhousename').val(getValuebyName('HOUSENAME'));
	$('#uepid').val(getValuebyName('EPID'));
	$('#waret').datagrid('loadData', {
		total: 0,
		rows: [],
		footer: [{
			"WARENO": "合计",
			"AMOUNT": 0,
			"CURR": 0
		}]
	});
	$('#wmtotalnote').html('已显示0条记录/共有0条记录');
	$('#uhousename').removeAttr('readonly');
	$('#uhandno').removeAttr('readonly');
	$('#uselecthouseb').show();
	$('#uselectcustb').show();
	$('#isupdatedbar').hide();
	$('#ispayedbar').hide();
	$('#updatingbar,.icon_barcode').show();
	$('#wquickuwaretd').show();
	$('#wquickuwareno').val('');
	$('#uremark').removeAttr('readonly');
	$('#uhousename').next().show();
	$('#uguestname').next().show();
	// 	$('#uguestname').removeAttr('readonly');
	$('#udptname').next().show();
	$('#udptname').removeAttr('readonly');
	$('#usalemanname').next().show();
	$('#ud #warem-toolbar').show();
		$('#unotedate').datetimebox({
			readonly: false,
			hasDownArrow: true
		});
	addwaresaleh();
	$('#ud').dialog("setTitle","增加店铺零售单");
	$('#ustatetag').val('0');
	$('#udiscount').val('1');
	$('#ud').dialog('open');
	$('#addnote').show();
	$('#ud').data('qickey', "F4:'openpay()',F5:'submitwareouth',F6:'opendisc()',Del:'delwareouth()'");
	$('#wquickuwareno').focus();
}
function updatenoted() {
	resetvipipt();
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
	$('#uhouseid').val(getValuebyName('HOUSEID'));
	$('#uhousename').val(getValuebyName('HOUSENAME'));
	$('#uepid').val(getValuebyName('EPID'));
	$('#waret').datagrid('loadData', {
		total: 0,
		rows: [],
		footer: [{
			"WARENO": "合计",
			"AMOUNT": 0,
			"CURR": 0
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
	if ($("#uguestid").val() == '0' || $("#uguestid").val() == '') {
		alerttext('请选择会员');
	} else {
		$('#qucikud').dialog({
			modal: true
		});
		$('#quickud').dialog('open');
	}
}
var pg = 1;
var sumpg = 1;
var nextpg = true;
//加载商品明细表
function setwarem() {
	$('#waret').datagrid({
		idField: 'ID',
		height: $('body').height() - 90,
		toolbar: '#udtool',
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		nowrap: false,
		//允许自动换行
		onDblClickRow: function(rowIndex, rowData) {
			if(!$("#warem-toolbar").is(":hidden")) updatewd();
		},
		showFooter: true,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 30,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == "" ? "": index + 1;
			}
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 1,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WARENAME',
			title: '商品名称',
			width: 2,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'UNITS',
			title: '单位',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'COLORNAME',
			title: '颜色',
			width: 1,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SIZENAME',
			title: '尺码',
			width: 1,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			width: 1,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'PRICE0',
			title: '单价',
			width: 2,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == undefined ? '': Number(value).toFixed(2);
			}
		},
		{
			field: 'DISCOUNT',
			title: '折扣',
			width: 2,
			align: 'center',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PRICE',
			title: '折后价',
			width: 2,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == undefined ? '': Number(value).toFixed(2);
			}
		},
		{
			field: 'CURR',
			title: '金额',
			width: 3,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value).toFixed(2);
			}
		}]],
		pageSize: 20
	});
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
			erpser: "waresalemlist",
			noteno: noteno,
			sortid: sortid,
			order: order,
			fieldlist: "*",
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var total = data.total;
				var totalamt = data.totalamount;
				var totalcurr = data.totalcurr;
				data.footer = [{
					AMOUNT: totalamt,
					CURR: totalcurr,
					COLORNAME: "SKC",
					SIZENAME: data.skc
				}];
				sumpg = Math.ceil(Number(total) / pagecount);
				$('#wtotalamt').val(totalamt);
				$('#wtotalcurr').val(Number(totalcurr).toFixed(2));
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
				$('#wmtotalnote').html('已显示' + $('#waret').datagrid('getRows').length + '条记录/共有' + total + '条记录');
				dqamt = totalamt;
				dqzamt = Number(dqzamt) - Number(dqamt) + Number(totalamt);
				dqzcurr = Number(dqzcurr) - Number(dqcurr) + Number(totalcurr);
				dqcurr = Number(totalcurr);
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						TOTALAMT: totalamt,
						TOTALCURR: dqcurr
					}
				});
				$('#gg').datagrid('updateFooter', {
					TOTALAMT: dqzamt,
					TOTALCURR: dqzcurr
				});
				$('#gg').datagrid('refresh');
				if(!$('#wquickuwareno').is(":hidden"))
					$('#wquickuwareno').focus();
			}
		}
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
			$('#wquickuwareid').val(arrs.WAREID);
			getwaremsum(arrs.WAREID, $('#unoteno').val());
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
						erpser: "delwareoutmsum",
						noteno: noteno,
						wareid: wareid,
						saleid: '0',
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
					var value = Number($('#u' + i.toString() + "-" + j.toString()).val());
					value = isNaN(value) ? 0 : value;
					if (value != 0) {
						count = count + 1;
						amount1[i] = amount1[i].add(value);
						totalamount = totalamount.add(value);
					}
				} else {
					$('#usum' + i).html(amount1[i].valueOf());
					var curr = amount1[i] * getbypoints($('#upri' + i).val());
					$('#uacurr' + i).html(getbylspoints(curr).toFixed(2));
					totalcurr = totalcurr.add(curr);
				}
			}
		}
		for (i = 1; i <= list; i++) {
			amount2[i] = new Decimal(0);
			for (j = 1; j <= line + 1; j++) {
				if (j != line + 1) {
					var value = Number($('#u' + j.toString() + "-" + i.toString()).val());
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
	$('#asum' + (list + 5).toString()).html(totalcurr.toFixed(2));
}
var hasTable = false;
function getwaremsum(wareid, noteno) {
	$('#quickutable').html('');
	var houseid = $('#uhouseid').val();
	var guestid = $('#uguestid').val();
	if ($('#quickutables').length == 0 && hasTable == false) {
		hasTable = true;
		alertmsg(6);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getwareoutmsum",
				noteno: noteno,
				wareid: wareid,
				guestid: Number(guestid),
				houseid: Number(houseid),
				ntid: "0",
				custid: "0",
				saleid: '0'
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					var wareno = data.WARENO;
					var warename = data.WARENAME;
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
						var price0 = data.PRICE0; //单价
						var disc = data.DISCOUNT; //折扣
						var price = data.PRICE; //折后价
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
									td.innerHTML = "原价";
								} else if (j == list + 3) {
									td.style.width = '60px';
									td.innerHTML = "折扣";
								} else if (j == list + 4) {
									td.style.width = '80px';
									td.innerHTML = "单价";
								} else if (j == list + 5) {
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
								} else {
									if (j == (list + 1)) {
										td.align = "center";
									} else if (j == (list + 2) || j == (list + 5)) {
										td.align = "right";
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
										$('#upri' + t).val(getbypoints(price2).toFixed(2));
										quickupdatesum();
									});
									$(input).change(function() {
										this.value = Number(this.value).toFixed(2);
									});
								} else if (j == list + 4) {
									var input = document.createElement("input");
									input.id = "upri" + i;
									input.style.border = "";
									input.type = "text";
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
										this.value = getbypoints(this.value).toFixed(2);
									});
									td.appendChild(input);
								} else if (j == list + 5) {
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
												// 		            	                    	input.placeholder=kclist[r].AMOUNT;
												input.setAttribute("placeholder", kclist[r].AMOUNT);
												//$(input).attr('placeholder',kclist[r].AMOUNT);
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
					$('input[type=text]').placeholder();
					quickupdatesum();
					var amtl = getJsonLength(amountlist);
					var tname = amtl == 0 ? "添加": "修改";
					var title = tname + "商品明细：" + wareno + "，" + warename;
					$('#quickud').dialog({
						title: title,
						width: list * 52 + 440,
						modal: true
					});
					$('#quickud').window('center');
					$('#quickud').dialog('open');
					var out_wid = list * 52 + 400;
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
	var saleid = $('#usaleid').val();
	if (wareid == undefined || wareid == "") {
		alerttext("请重新选择货号！");
	} else {
		var list = $('#quickutables tr:eq(1) input[type=text]').length - 2; //横向文本框数
		var line = $('#quickutables tr').length - 2; //竖向文本框数
		var quickjsonstr = [];
		var count = 0;
		if (line >= 1 && list >= 1) {
			for (i = 1; i <= line; i++) {
				var remark0 = $('#uremark' + i).val();
				for (j = 1; j <= list + 1; j++) {
					if (j != list + 1) {
						var valuestr = $('#u' + i.toString() + "-" + j.toString()).val();
						var value = Number($('#u' + i.toString() + "-" + j.toString()).val());
						if (valuestr != "" && !isNaN(value)) {
							quickjsonstr.push({
								colorid: $('#uacolor' + i).val(),
								sizeid: $('#uasize' + j).val(),
								amount: value,
								discount: $('#udisc' + i).val(),
								price0: $('#uprice0' + i).html(),
								price: $('#upri' + i).val(),
								remark0: "",
								curr: getbylspoints(value*Number($('#upri' + i).val()))
							});
							count = count + 1;
						}
					}
				}
			}
		}
		jsondata = quickjsonstr;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方  法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "writewareoutmsum",
				noteno: noteno,
				wareid: wareid,
				ntid: '0',
				saleid: "0",
				custid: '0',
				rows: JSON.stringify(jsondata)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#quickuaddwaref')[0].reset();
					$('#quickutable').html('');
					$('#wquickuwareno').val('');
					getnotewarem(noteno, '1');
					$('#wquickuwareid').val('');
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
//添加生成零售开票单
function addwaresaleh() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addwaresaleh",
			noxsman: defsalemanbj,
			houseid: getValuebyName("HOUSEID")
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#uid').val(data.ID);
				$('#unoteno').val(data.NOTENO);
				if(defsalemanbj==1)
					$("#usalemanname").val("");
				else
					$("#usalemanname").val(getValuebyName("EPNAME"));
				changedatebj = false;
				$('#unotedate').datetimebox('setValue', data.NOTEDATE);
				changedatebj = true;
				$('#uhandno').val('');
				$('#uguestname').val('');
				dqindex = 0;
				dqamt = 0;
				dqcurr = 0;
				$('#qsnotevalue').val("");
				$("#smaxdate").datebox("setValue", top.getservertime());
				getnotedata();
				$('#waret').datagrid('loadData', nulldata);
			}
		}
	});
}
function delnote(index) {
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	$('#uid').val(row.ID);
	$('#unoteno').val(row.NOTENO);
	delwaresaleh();
}
//删除单据
function delwaresaleh() {
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
					erpser: "delwaresalehbyid",
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
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewaresalehbyid",
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
	})
}
//选择客户
function changeguest(guestid) {
	alertmsg(2);
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewaresalehbyid",
			id: id,
			noteno: noteno,
			guestid: guestid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						GUESTNAME: $('#uguestname').val(),
						GUESTID: guestid
					}
				});
			}
		}
	});
}
function cancelguest() {
	alertmsg(2);
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewaresalehbyid",
			id: id,
			noteno: noteno,
			guestid: 0
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						GUESTNAME: "",
						GUESTID: 0
					}
				});
				$('#uguestid').val(0);
				$('#uguestname').val("");
				$('#alldiscount,#udiscount').val(1);
				$('#guestinfo').hide();
				alldisc();
			}
		}
	});
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
			erpser: "updatewaresalehbyid",
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
			erpser: "updatewaresalehbyid",
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
//改变部门
function changedpt(dptid) {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewaresalehbyid",
			id: id,
			noteno: noteno,
			dptid: dptid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						DPTID: dptid,
						DPTNAME: $('#udptname').val()
					}
				});
			}
		}
	});
}

//打开打折框
function opendisc() {
	$('#discd').dialog({
		modal: true
	});
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
				erpser: "changewareoutmdisc",
				noteno: noteno,
				discount: discount
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$("#discd").dialog('close');
					getnotewarem(noteno, '1');
				}

			}
		});
	}
}
//打开结账窗口
function openpay() {
	//var amt = Number($('#wtotalamt').val()==""?0:$('#wtotalamt').val());
	var amt = $('#waret').datagrid('getRows').length;
	if (amt == 0) {
		alert('商品明细不能为空！');
	} else {
		$('#payform')[0].reset();
		var guestid = $('#uguestid').val();
		if ($('#uguestname').val() != "" && $('#uguestid').val() != "") {
			var balcent = $('#balcent').html() == '0' || $('#balcent').html() == '' ? 0 : Number($('#balcent').html());
			balcent == 0 ? $('td.usecent').hide() : $('td.usecent').show();
			$('#usecent').attr("placeholder", "<可用积分：" + balcent + ">");
			$('td.usecent,td.useyhq').show();
			$('#newask').val(1);
		} else {
			$('td.usecent,td.useyhq').hide();
		}
		$('#yhjcurr .yhq').remove();
		$('#yhjcurr').val(0);
		$('#sendvipmsg').hide();
		$('#btn_pay,#btn_pay_print').show();
		$('#usecent,#jfcurr,.payvcurr').removeProp("readonly");
		$('#yhjcurr').removeProp('disabled');
		$('#freepay').removeAttr('readonly');
		$('#payd').dialog({
			modal: true
		});
		var totalpay = Number($('#wtotalcurr').val());
		// 		if(totalpay<0)
		//			var chpay = -(Math.ceil(totalpay)- totalpay);
		//		else
		//			var chpay = 1- (Math.ceil(totalpay)- totalpay);
		//		chpay = chpay==1||chpay==-1?0:chpay;
		$('#totalpay').val(totalpay.toFixed(2));
		var checkpay = 0;
		if (lsmlfs == 0 || points == 0) checkpay = Math.floor(totalpay);
		else if (lsmlfs == 1) checkpay = Math.round(totalpay);
		else if (lsmlfs == 2) checkpay = totalpay;
		$('#actpay').val(checkpay.toFixed(2));
		$('#freepay').val((totalpay - checkpay).toFixed(2));
		$('#updatediv').show();
		listvipcoupon();
		getpaywaylist(false);
	}
}
//结账明细窗口
function openpaydetail() {
	$('#sumpaym').val('');
	$('#changecurrm').val('');
	$('#paydetaild').dialog({
		modal: true
	});
	var usecent = $('#uusecent').val();
	var jfcurr = $('#ujfcurr').val();
	usecent == '' ? $('#centdivm').hide() : $('#centdivm').show();
	$('#usecentm').val(usecent);
	$('#jfcurrm').val(jfcurr);
	var guestid = $('#uguestid').val();
	$('#totalpaym').val($('#wtotalcurr').val());
	getwaresalepay();
	$('#paydetaild').dialog('open');
}
//生成支付列表行
var payline;
var isv = false;
function getpaywaylist(disabled) {
	$('.paytr').detach();
	alertmsg(6);
	var online_pay = {
		wx: 0,
		zfb: 0,
		qtzf: 0
	};
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "paywaylist",
			rows: 50,
			sort: "payno",
			order: "asc",
			nov: 0,
			sybj: 1 
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var total = data.total;
				payline = Number(total);
				var balcurr = $('#balcurr').html() == '0' || $('#balcurr').html() == '' ? 0 : $('#balcurr').html();
				var rows = data.rows;
				var i = 1;
				var tr = document.createElement("tr");
				var newtr = true;
				for (var row in rows) {
					if(newtr){
						tr = document.createElement("tr");
						tr.className = "paytr";
					}
					if (rows[row].PAYNO == 'V') {
						if (balcurr != 0) {
							var td1 = document.createElement("td");
							var td2 = document.createElement("td");
							td1.align = "right";
							td1.className = "header";
							td2.align = "right";
							var label = document.createElement("label");
							var inputt = document.createElement("input");
							var inputh = document.createElement("input");
							var inputh1 = document.createElement("input");
							inputt.type = "text";
							inputt.className = "hig25 wid160";
							inputh.type = "hidden";
							inputh1.type = "hidden";
							label.innerHTML = rows[row].PAYNAME;
							label.style.cursor = "pointer";
							label.onclick = function() {
								$('.paycurr:not([readonly],[disabled])').val("");
								var pi = this.id.replace("payname", "");
								var actpay = Number($('#actpay').val());
								var payedcurr = 0;
								for (var i = 0; i < $('.paycurr[readonly]').length; i++) {
									payedcurr += Number($('.paycurr[readonly]').eq(i).val());
								}
								if (actpay > Number($('#balcurr').html())) {
									$('#pay' + pi).val($('#balcurr').html());
								} else $('#pay' + pi).val((actpay - payedcurr).toFixed(2));
								sumpay();
								$('#pay' + pi).select().focus();
							};
							label.id = "payname" + i;
							inputt.placeholder = "可用余额:" + $('#balcurr').html() + "元";
							inputt.id = "pay" + i;
							inputt.className = "hig25 payvcurr paycurr";
							$(inputt).change(function() {
								var checkcurr = Number($('#actpay').val());
								checkcurr = isNaN(checkcurr) ? 0 : checkcurr
								if (checkcurr<0&&checkcurr>Number(this.value)) {
									this.value = Number(checkcurr).toFixed(2);
									alerttext("退款金额不能大于应退金额");
								} else this.value = Number(this.value).toFixed(2);
								sumpay();
							});
							inputt.onkeyup = function() {
								if (this.value != "-") {
									this.value = this.value.replace(/[^-?\d.*$]/g, '');
									sumpay();
								}
							};
							inputt.onafterpaste = function() {
								if (this.value != "-") {
									this.value = this.value.replace(/[^-?\d.*$]/g, '');
									this.value = Number(this.value) > Number($('#balcurr').html()) ? $('#balcurr').html() : Number(this.value);
									sumpay();
								}
							};
							inputt.disabled = disabled;
							inputh.id = "payid" + i;
							inputh1.id = "payno" + i;
							inputh1.value = rows[row].PAYNO;
							inputh.value = rows[row].PAYID;
							td1.appendChild(label);
							td2.appendChild(inputh);
							td2.appendChild(inputh1);
							td2.appendChild(inputt);
							tr.appendChild(td1);
							tr.appendChild(td2);
						} else continue;
					} else if (rows[row].PAYNO == 'S' || rows[row].PAYNO == 'R' || rows[row].PAYNO == 'W' || rows[row].PAYNO == 'Z') {
						var td1 = document.createElement("td");
						var td2 = document.createElement("td");
						td1.align = "right";
						td1.className = "header";
						td2.align = "right";
						var label = document.createElement("label");
						var inputt = document.createElement("input");
						var inputh = document.createElement("input");
						var inputh1 = document.createElement("input");
						var inputb = document.createElement("input");
						inputt.type = "text";
						inputh.type = "hidden";
						inputh1.type = "hidden";
						inputb.type = "button";
						var payfs = 0;
						$(inputb).data('currid', "#pay" + i);
						if (rows[row].PAYNO == 'R'||rows[row].PAYNO == 'S') {
							qtzfpayname = rows[row].PAYNAME;
							qtzfpayno = rows[row].PAYNO;
							if(rows[row].PAYNO == 'R') qtpayfs=2;
							else if(rows[row].PAYNO == 'S') qtpayfs=3;
							$(inputb).data('payfs',qtpayfs);
							inputb.className = "paybtn payfs"+qtpayfs;
							inputt.className = "paycurr payqtzf payfs"+qtpayfs;
							$(inputt).data('payname', qtzfpayname);
							$(inputt).data('sucstg', 0);
							online_pay.qtzf = 1;
						} else if (rows[row].PAYNO == 'W') {
							$(inputb).data('payfs', 1);
							inputb.className = "paybtn payfs"+qtpayfs;
							inputt.className = "paycurr payw payfs1";
							online_pay.wx = 1;
						} else if (rows[row].PAYNO == 'Z') {
							$(inputb).data('payfs', 0);
							inputb.className = "paybtn payfs"+qtpayfs;
							inputt.className = "paycurr payz payfs0";
							online_pay.zfb = 1;
						}
						inputb.onclick = function() {
							var payfs = $(this).data('payfs');
							openskypay(payfs);
						};
						label.innerHTML = rows[row].PAYNAME;
						label.style.cursor = "pointer";
						label.onclick = function() {
							if ($('#pay' + pi + '[readonly]').length == 0) {
								$('.paycurr:not([disabled],[readonly])').val("");
								var pi = this.id.replace("payname", "");
								var actpay = Number($('#actpay').val());
								var payedcurr = 0;
								for (var i = 0; i < $('.paycurr[readonly]').length; i++) {
									payedcurr += Number($('.paycurr[readonly]').eq(i).val());
								}
								$('#pay' + pi).val((actpay - payedcurr).toFixed(2));
								sumpay();
								$('#pay' + pi).select().focus();
							}
						};
						label.id = "payname" + i;
						inputt.placeholder = "<输入>";
						inputt.id = "pay" + i;
						inputt.style.width = "100px";
						inputt.style.height = "25px";
						inputb.className = "btn2";
						inputb.style.width = "50px";
						inputb.style.padding = "2px";
						var actpay = Number($('#actpay').val());
						if (actpay >= 0) inputb.value = "支付";
						else if (actpay < 0) inputb.value = "退款";
						$(inputt).change(function() {
							this.value = Number(this.value).toFixed(2);
							sumpay();
						});;
						inputt.onkeyup = function(e) {
							if (this.value != "-") {
								this.value = this.value.replace(/[^-?\d.*$]/g, '');
							}
							sumpay();
						}
						inputt.onafterpaste = function() {
							if (this.value != "-") {
								this.value = this.value.replace(/[^-?\d.*$]/g, '');
							}
						};
						inputt.disabled = disabled;
						inputh.id = "payid" + i;
						inputh1.id = "payno" + i;
						inputh1.value = rows[row].PAYNO;
						inputh.value = rows[row].PAYID;
						td1.appendChild(label);
						td2.appendChild(inputb);
						td2.appendChild(inputt);
						td2.appendChild(inputh);
						td2.appendChild(inputh1);
						tr.appendChild(td1);
						tr.appendChild(td2);
					} else {
						var td1 = document.createElement("td");
						var td2 = document.createElement("td");
						td1.align = "right";
						td2.align = "right";
						td1.className = "header";
						var label = document.createElement("label");
						var inputh1 = document.createElement("input");
						var inputt = document.createElement("input");
						var inputh = document.createElement("input");
						inputt.type = "text";
						inputh.type = "hidden";
						inputh1.type = "hidden";
						label.innerHTML = rows[row].PAYNAME;
						label.style.cursor = "pointer";
						label.onclick = function() {
							$('.paycurr:not([readonly])').val("");
							var pi = this.id.replace("payname", "");
							var actpay = Number($('#actpay').val());
							var payedcurr = 0;
							for (var i = 0; i < $('.paycurr[readonly]').length; i++) {
								payedcurr += Number($('.paycurr[readonly]').eq(i).val());
							}
							$('#pay' + pi).val((actpay - payedcurr).toFixed(2));
							sumpay();
							$('#pay' + pi).select().focus();
						};
						label.id = "payname" + i;
						inputt.id = "pay" + i;
						inputt.className = "paycurr hig25 wid160";
						inputt.placeholder = "<输入>";
						$(inputt).change(function() {
							this.value = Number(this.value).toFixed(2);
							sumpay();
						});
						inputt.onkeyup = function() {
							if (this.value != "-") {
								this.value = this.value.replace(/[^-?\d.*$]/g, '');
								sumpay();
							}
						};
						inputt.onafterpaste = function() {
							if (this.value != "-") {
								this.value = this.value.replace(/[^-?\d.*$]/g, '');
								sumpay();
							}
						};
						inputt.disabled = disabled;
						inputh.id = "payid" + i;
						inputh1.id = "payno" + i;
						inputh1.value = rows[row].PAYNO;
						inputh.value = rows[row].PAYID;
						td1.appendChild(label);
						td2.appendChild(inputt);
						td2.appendChild(inputh);
						td2.appendChild(inputh1);
						tr.appendChild(td1);
						tr.appendChild(td2);
					}
					if(newtr){
						newtr = false;
					}else{
						newtr = true;
						$('#payway').before(tr);
					}
					i = i + 1;
				}
				if(newtr==false) $('#payway').before(tr);
				skypayedbj = {
					zfbpay: 0,
					paynoteno_wx: "",
					wxpay: 0,
					paynoteno_zfb: "",
					qtzfpay: 0,
					paynoteno_qtzf: ""
				}; //重置支付标记
				if (online_pay.zfb == 1) {
					checkskypayinfo(0);
				}
				if (online_pay.wx == 1) {
					checkskypayinfo(1);
				}
				if (online_pay.qtzf == 1) {
					checkskypayinfo(qtpayfs);
				}
				$('input[type=text]').attr("autocomplete", "off");
				$('#payd').dialog('open');
				setTimeout(function() {
					$('#payname1').click();
				},
				200);
			}
		}
	});
}
function sendsms() {
	var mobile = $('#mobile').html();
	if (mobile == '') {
		alerttext('该会员手机号码为空！');
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fysmsjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "sendsms3",
				mobile: mobile
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					alerttext('发送验证码成功！');
					$('#sendsmsb').attr('disabled', 'true');
					var count = 60;
					var countdown = setInterval(CountDown, 1000);
					function CountDown() {
						$("#sendsmsb").attr("disabled", true);
						$("#sendsmsb").val(count + "s");
						if (count == 0) {
							$("#sendsmsb").val("获取").removeAttr("disabled");
							clearInterval(countdown);
						}
						count--;
					}
				}

			}
		});
	}
}
function getactpay() {
	var totalpay = $('#totalpay').val() == "" || $('#totalpay').val() == "0" ? 0.00 : Number($('#totalpay').val());
	var freepay = $('#freepay').val() == "" || $('#freepay').val() == "0" ? 0.00 : Number($('#freepay').val());
	var jfcurr = $('#jfcurr').val() == "" || $('#jfcurr').val() == "0" ? 0.00 : Number($('#jfcurr').val());
	var yhjcurr = $('#yhjcurr').val() == "" || $('#yhjcurr').val() == "0" ? 0 : Number($('#yhjcurr').val());
	var actpay = 0;
	if ((totalpay - jfcurr - freepay > yhjcurr && totalpay - jfcurr - freepay > 0) || totalpay - jfcurr - freepay < 0) actpay = totalpay - jfcurr - freepay - yhjcurr;
	$('#actpay').val(actpay.toFixed(2));
	sumpay();
}
//得到支付记录
function getwaresalepay() {
	var noteno = $('#unoteno').val();
	$('.paytr').detach();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwaresalepaye",
			noteno: noteno
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var rows = data.PAYLIST;
				var i = 1;
				var sumpay = 0;
				for (var row in rows) {
					var tr = document.createElement("tr");
					tr.className = "paytr";
					var td1 = document.createElement("td");
					td1.align = "right";
					td1.className = "header";
					td1.style.width = "47px";
					var td2 = document.createElement("td");
					td2.align = "right";
					var inputt = document.createElement("input");
					inputt.className = "hig25 wid-in-td";
					var label = document.createElement("label");
					inputt.type = "text";
					label.innerHTML = rows[row].PAYNAME;
					inputt.readOnly = true;
					inputt.style.textAlign = "right";
					inputt.value = Number(rows[row].CURR).toFixed(2);
					td1.appendChild(label);
					td2.appendChild(inputt);
					tr.appendChild(td1);
					tr.appendChild(td2);
					$('#paywaym').before(tr);
					i = i + 1;
				}
			}
			var yhjcurr = Number(data.YHJCURR).toFixed(2);
			var jfcurr = Number(data.JFCURR).toFixed(2);
			if(yhjcurr>0){
				$("#yhqdivm").show();
				$("#yhjcurrm").val(yhjcurr);
			}else{
				$("#yhqdivm").hide();
				$("#yhjcurrm").val(0);
			}
			if(jfcurr>0){
				$("#jfcurrm").val(jfcurr);
			}else{
				$("#centdivm").hide();
				$("#jfcurrm").val(0);
			}
			$('#totalpay').val(Number(data.TOTALCURR).toFixed(2));
			$('#freepay').val(Number(data.FREECURR).toFixed(2));
			$('#sumpaym').val(Number(data.ZPAYCURR).toFixed(2));
			$('#actpaym').val(Number(data.CHECKCURR).toFixed(2));
			$('#changecurrm').val(Number(data.CHANGECURR).toFixed(2));
		}
	});
}
//求结账合计
var paydata;
var paylist;
function sumpay() {
	var actpay = Number($('#actpay').val());
	var freepay = Number($('#freepay').val());
	$('#freepay').val(freepay.toFixed(2));
	var json = [];
	var sum = 0;
	var count = 0;
	paylist = '';
	for (var i = 1; i <= $("input.paycurr").length; i++) {
		var pay = $('#pay' + i).val() == "" || Number($('#pay' + i).val()) == 0 || Number($('#pay' + i).val()) == NaN ? 0 : Number($('#pay' + i).val());
		sum = sum + pay;
		if (pay != 0) {
			paylist += $('#payname' + i).html() + ":" + pay + ",";
			var payid = $('#payid' + i).val();
			var payno = $('#payno' + i).val();
			json[count] = {
				"payid": payid,
				"payno": payno,
				"paycurr": pay
			};
			count = count + 1;
		}
	}
	paydata = json;
	sum == NaN ? $('#sumpay').val("0.00") : $('#sumpay').val(sum.toFixed(2));
	var changecurr = sum - actpay;
	$('#changecurr').val(Number(changecurr).toFixed(2));
	checksendmsg();
}
function updatewaresaleh(print) {
	var id = $('#uid').val();
	var guestid = $('#uguestid').val();
	var houseid = $('#uhouseid').val();
	var noteno = $('#unoteno').val();
	var totalcurr = Number($('#totalpay').val());
	var jfcurr = $('#jfcurr').val() == "" || $('#jfcurr').val() == "0" ? 0.00 : Number($('#jfcurr').val());
	var changecurr = Number($('#changecurr').val());
	if(changecurr>=100){
		alerttext('找零金额不允许大于100！');
		return;
	}
	var freecurr = $('#freepay').val() == '' || $('#freepay').val() == '0' ? 0.00 : Number($('#freepay').val());
	var usecent = $('#usecent').val() == '' || $('#usecent').val() == '0' ? 0 : Number($('#usecent').val());
	var checkcurr = Number($('#actpay').val());
	var zpaycurr = Number($('#sumpay').val());
	var yhjcurr = Number($('#yhjcurr').val());
	if (totalcurr - freecurr - jfcurr < yhjcurr && checkcurr == 0) yhjcurr = totalcurr - freecurr - jfcurr;
	var vcpid = Number($('#yhjcurr :selected').data("vcpid"));
	var notedatestr = $('#unotedate').datetimebox('getValue');
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
		return;
	}
	if (Number($('.payqtzf').val()) > 0) {
		if (skypayedbj.qtzfpay == 0) {
			alerttext(qtzfpayname+'支付未完成，请重新支付！');
			return;
		}
	}
	if (Number($('.payw').val()) > 0) {
		if (skypayedbj.wxpay == 0) {
			alerttext('微信支付未完成，请重新支付！');
			return;
		}
	}
	var errorpaymsg = "";
	$(".payqtzf").each(function(){
		var $this = $(this);
		var payname = $this.data("payname");
		var sucstg = Number($this.data("sucstg"));
		var curr = Number($this.val());
		if(curr>0){
			if(sucstg==0){
				errorpaymsg += payname+" ";
			}
		}
	});
	if (errorpaymsg.length > 0) {
		alerttext(errorpaymsg + '支付未完成，请重新支付！');
		return;
	}
	if (Number(zpaycurr) < Number(checkcurr) && checkcurr >= 0) {
		alerttext('收款合计小于应结金额，不允许提交');
	} else if (checkcurr < 0 && Number(zpaycurr - changecurr) < Number(checkcurr)) {
		alerttext('退款合计大于实退金额，不允许提交');
	}

	else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updatewaresalehbyid",
				noteno: noteno,
				id: id,
				guestid: guestid,
				houseid: houseid,
				totalcurr: totalcurr,
				freecurr: freecurr,
				checkcurr: checkcurr,
				zpaycurr: zpaycurr,
				changecurr: changecurr,
				notedatestr: notedatestr,
				usecent: usecent,
				jfcurr: jfcurr,
				yhjcurr: yhjcurr,
				vcpid: vcpid,
				statetag:1,
				paynoteno_qtzf: skypayedbj.paynoteno_qtzf,
				paynoteno_wx: skypayedbj.paynoteno_wx,
				paynoteno_zfb: skypayedbj.paynoteno_zfb,
				paylist: JSON.stringify(paydata)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					if (print) {
						$('#print').click();
					}
					if (freecurr != 0) paylist = '免单：' + freecurr + "，" + paylist;
					if (changecurr != 0) paylist += "找零：" + changecurr;
					$('#ud').dialog('close');
					$('#payd').dialog('close');
					$('#gg').datagrid('updateRow', {
						index: dqindex,
						row: {
							STATETAG: '1',
							PAYLIST: paylist
						}
					});
					$('#gg').datagrid('refresh');
				}
			}
		});
	}
}
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
						erpser: "wareouthcancel",
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
										STATETAG: '0',
										PAYLIST: ""
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
		alert('本单据不是，撤单只对当天单据有效！');
	}
}

//获取id资料
function getnotebyid(id, noteno) {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwaresalehbyid",
			fieldlist: "a.id,a.accid,a.noteno,a.notedate,a.guestid,a.houseid,a.ntid,a.handno,a.operant,a.statetag,"
				+ "b.guestname,b.vipno,a.checkcurr,a.freecurr,a.changecurr,a.zpaycurr,b.mobile,b.balcent,b.balcurr,"
				+ "c.housename,d.discount,d.vtid,d.vtname,c.address,c.tel,a.cent,a.usecent,a.jfcurr,a.dptid,e.dptname",
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
					var epname = getValuebyName("EPNAME");
					var operant = row.OPERANT;
					var stg = row.STATETAG;
					if (stg==1||epname!=operant) {
						$('#ud').dialog("setTitle", "浏览店铺零售单");
						$('#uhandno').attr('readonly', 'readonly');
						$('#uselecthouseb').hide();
						$('#uselectcustb').hide();
						$('#updatingbar,#cancelvipbtn').hide();
						$('#isupdatedbar,.icon_barcode').hide();
						$('#ispayedbar').show();
						$('#wquickuwaretd').hide();
						$('#uremark').attr('readonly', 'readonly');
						$('#uhousename').attr('readonly', 'readonly');
						$('#uhousename').next().hide();
						$('#usalemanname').next().hide();
						$('#uguestname').next().hide();
						//				 			$('#uguestname').attr("readonly","readonly");
						$('#udptname').next().hide();
						$('#udptname').attr("readonly", "readonly");
						//$('#waret').datagrid('hideColumn','ACTION');
						$('#ud #warem-toolbar').hide();
						var levelid = getValuebyName("GLEVELID");
						if (levelid == '0' || allowchedan == '1') {
							$('#ud').data('qickey', "F8:'openpaydetail()',F9:'$(\"#print\").click()',F10:'withdraw()'");
						} else {
							$('#ud').data('qickey', "F8:'openpaydetail()',F9:'$(\"#print\").click()'");
						}
						if (stg==1) {
							$('#paydetailbtn').show();
						} else {
							$('#paydetailbtn').hide();
						}
						$('#unotedate').datetimebox({
							readonly: true,
							hasDownArrow: false
						});
					} else if (stg == '3') {
						$('#ud').dialog("setTitle", "浏览店铺零售单");
						$('#uhandno').attr('readonly', 'readonly');
						$('#uselecthouseb').hide();
						$('#uselectcustb').hide();
						$('#updatingbar,#cancelvipbtn').hide();
						$('#ispayedbar').hide();
						$('#isupdatedbar,.icon_barcode').show();
						$('#wquickuwaretd').hide();
						$('#uremark').attr('readonly', 'readonly');
						$('#uhousename').attr('readonly', 'readonly');
						$('#uhousename').next().hide();
						$('#usalemanname').next().hide();
						$('#uguestname').next().hide();
						//				 			$('#uguestname').attr("readonly","readonly");
						$('#udptname').next().hide();
						$('#udptname').attr("readonly", "readonly");
						//$('#waret').datagrid('hideColumn','ACTION');
						$('#ispayedbar').hide();
						$('#ud #warem-toolbar').hide();
						$('#ud').data('qickey', "F9:'$(\"#print\").click()'");
						$('#unotedate').datetimebox({
							readonly: true,
							hasDownArrow: false
						});
					} else {
						$('#ud').dialog("setTitle", "修改店铺零售单");
						$('#uhousename').removeAttr('readonly');
						$('#uhandno').removeAttr('readonly');
						$('#uselecthouseb').show();
						$('#uselectcustb').show();
						$('#isupdatedbar').hide();
						$('#ispayedbar').hide();
						$('#updatingbar,#cancelvipbtn').show();
						$('#wquickuwaretd').show();
						$('#wquickuwareno').val('');
						$('#uremark').removeAttr('readonly');
						$('#uhousename').next().show();
						$('#ucustname').next().show();
						//				 			$('#uguestname').removeAttr('readonly');
						$('#uguestname').next().show();
						$('#usalemanname').removeAttr('readonly');
						$('#usalemanname').next().show();
						$('#ud #warem-toolbar').show();
						//$('#waret').datagrid('showColumn','ACTION');
						$('#ud').data('qickey', "F4:'openpay()',F5:'submitwareouth',F6:'opendisc()',Del:'delwaresaleh()'");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
					}
					$('#ud').dialog('open');
					getnotewarem($('#unoteno').val(), "1");
					pg = 1;
					var guestid = row.GUESTID;
					var handno = row.HANDNO;
					var housename = row.HOUSENAME;
					var houseid = row.HOUSEID;
					var guestname = row.GUESTNAME;
					var salemanlist = row.SALEMANLIST == "" ? getValuebyName('EPNAME') : row.SALEMANLIST;
					var vipno = row.VIPNO;
					var mobile = row.MOBILE;
					var balcent = row.BALCENT;
					var discount = row.DISCOUNT.length==0?1:row.DISCOUNT;
					var vtid = row.VTID;
					var vtname = row.VTNAME;
					var cent = row.CENT;
					var balcurr = row.BALCURR;
					var usecent = row.USECENT;
					var jfcurr = row.JFCURR;
					var freecurr = row.FREECURR;
					var istoday = row.ISTODAY;
					var dptid = row.DPTID;
					var dptname = row.DPTNAME;
					$('#uguestid').val(guestid);
					$('#uistoday').val(istoday);
					$('#uhandno').val(handno);
					$('#uguestname').val(guestname);
					$('#uhousename').val(housename == "" ? getValuebyName('HOUSENAME') : housename);
					$('#uhouseid').val(houseid == "" ? getValuebyName('HOUSEID') : houseid);
					$('#usalemanname').val(salemanlist);
					$('#vipno').html(vipno);
					$('#mobile').html(mobile);
					$('#balcent').html(balcent);
					$('#udiscount').val(discount);
					$('#vtid').val(vtid);
					$('#vtname').html(vtname);
					$('#cent').html(cent);
					$('#balcurr').html(balcurr);
					$('#uusecent').val(usecent);
					$('#ujfcurr').val(jfcurr);
					$('#freepaym').val(freecurr);
					$('#udptname').val(dptname);
					$('#udptid').val(dptid);
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					if (guestid == '0') {
						$('#guestinfo').hide();
					} else {
						$('#guestinfo').show();
					}
					if ((istoday == "1" || allowchangdate == 1) && allowchedan == '1' &&stg==1) {
						$('#sep-withdraw').show();
						$('#withdrawbtn').show();
					} else {
						$('#sep-withdraw').hide();
						$('#withdrawbtn').hide();
					}
					if (stg != 0) {
						$('#unoteno').focus();
					} else {
						$('#wquickuwareno').focus();
					}
				}
			}
		}
	});
}
//提交单据到收银台
function submitwareouth() {
	var noteno = $('#unoteno').val();
	var id = $('#uid').val();
	var totalamt = $('#wtotalamt').val();
	var houseid = $('#uhouseid').val();
	var ntid = "1";
	var length = $('#waret').datagrid('getRows').length;
	if (length > 0) {
		$.messager.confirm(dialog_title, '您是否确定提交单据到收银台？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "submitwareouth",
						noteno: noteno,
						id: id,
						ntid: ntid,
						houseid: houseid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							$('#gg').datagrid('updateRow', {
								index: dqindex,
								row: {
									STATETAG: '3'
								}
							});
							$('#gg').datagrid('refresh');
							$('#ud').dialog('close');
						}
					}
				});
			}
		});
	} else {
		alert("商品明细为空！");
	}
}
function getjfcurr() {
	if (Number(centbl) == 0) alerttext("未设置积分抵扣，请到右上角参数设置里设置！");
	else {
		$('#jfcurr').val(Number($('#usecent').val()) / centbl);
		getactpay();
	}
}
function setwareoutpaylist() {
	var rowdata = $('#gg').datagrid("getSelected");
	if (rowdata) {
		if (rowdata.STATETAG == "1") {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "setwareoutpaylist",
					noteno: rowdata.NOTENO
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						$('#gg').datagrid('updateRow', {
							index: dqindex,
							row: {
								PAYLIST: data.msg
							}
						});
						$('#gg').datagrid('refresh');
					}
				}
			});
		}
	}
}
function listvipcoupon() {
	var houseid = Number($('#uhouseid').val());
	var guestid = Number($('#uguestid').val());
	var xfcurr = Number($('#actpay').val());
	if (houseid <= 0 || isNaN(houseid)) {
		alerttext("请选择店铺！");
		return;
	}
	if (guestid <= 0 || isNaN(guestid)) {
		$('#yhjcurr').val(0);
		$('td.useyhq').hide();
		return;
	}
	$('#yhjcurr .yhq').remove();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listvipcoupon",
			houseid: houseid,
			guestid: guestid,
			xfcurr: xfcurr
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					if (data.total == 0) {
						$('td.useyhq').hide();
					} else $('td.useyhq').show();
					for (var i in rows) {
						var row = rows[i];
						var html = '<option class="yhq" value="' + Number(row.CURR) + '" data-xfcurr="' + Number(row.XFCURR) + '" data-vcpid="' + Number(row.VCPID) + '">' + row.REMARK + '</option>';
						$('#yhjcurr').append(html);
					}
				}
			} catch(e) {
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
function checksendmsg() {
	if (useyhq == 1) {
		var usecent = Number($('#usecent').val());
		var payvcurr = Number($('.payvcurr').val());
		var yhjcurr = Number($('#yhjcurr').val());
		usecent = isNaN(usecent) ? 0 : usecent;
		payvcurr = isNaN(payvcurr) ? 0 : payvcurr;
		yhjcurr = isNaN(yhjcurr) ? 0 : yhjcurr;
		if (usecent > 0 || payvcurr > 0 || yhjcurr > 0) {
			$('#newask').val(1);
			$('#sendvipmsg').val("发送会员验证");
			$('#sendvipmsg').show();
			$('#btn_pay,#btn_pay_print').hide();
		} else {
			$('#sendvipmsg').hide();
			$('#btn_pay,#btn_pay_print').show();
		}
	}
}
function sendvipmessage() {
	var noteno = $('#unoteno').val();
	var yhjcurr = Number($('#yhjcurr').val());
	var xfcurr = Number($('#yhjcurr :selected').data("xfcurr"));
	var usecent = Number($('#usecent').val());
	var jfcurr = Number($('#jfcurr').val());
	var payvcurr = Number($('.payvcurr').val());
	var totalcurr = Number($('#totalpay').val());
	var newask = Number($('#newask').val());
	usecent = isNaN(usecent) ? 0 : usecent;
	payvcurr = isNaN(payvcurr) ? 0 : payvcurr;
	yhjcurr = isNaN(yhjcurr) ? 0 : yhjcurr;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "sendvipmessage",
			noteno: noteno,
			yhjcurr: yhjcurr,
			xfcurr: xfcurr,
			usecent: usecent,
			jfcurr: jfcurr,
			payvcurr: payvcurr,
			totalcurr: totalcurr,
			newask: newask
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var result = data.result;
					if (result > 0) {
						if (newask == 0) {
							if (result == 2) {
								alerttext(data.msg);
								$('#sendvipmsg').val("发送会员验证");
								$('#sendvipmsg').hide();
								$('#btn_pay,#btn_pay_print').show();
							} else if (result >= 3) {
								alerttext(data.msg);
								$('#sendvipmsg').hide();
								$('#usecent,#jfcurr,.payvcurr').removeProp("readonly");
								$('#yhjcurr').removeProp('disabled');
							}
							$('#newask').val(1);
						} else if (newask == 1) {
							alerttext(data.msg);
							$('#sendvipmsg').val("获取验证结果");
							$('#usecent,#jfcurr,.payvcurr').prop("readonly", true);
							$('#yhjcurr').prop('disabled', true);
							$('#newask').val(0);
						}
					} else alerttext(data.msg);
				}
			} catch(e) {
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
function resetvipipt() {
	if ($('.icon_barcode').hasClass('active')) {
		$('.icon_barcode').removeClass('active');
		$('#vipbarcode').hide();
		$('.selvip').show();
		$('#uguestname').focus();
	}
}
function changesetvip() {
	if ($('.icon_barcode').hasClass('active')) {
		$('.icon_barcode').removeClass('active');
		$('#vipbarcode').hide();
		$('.selvip').show();
		$('#uguestname').focus();
	} else {
		$('.icon_barcode').addClass('active');
		$('#vipbarcode').show();
		$('.selvip').hide();
		$('#vipbarcode').val('');
		$('#vipbarcode').focus();
	}
}
var setvipbarcode = function(s) {
	$(s).keyup(function(e) {
		var key = e.which;
		if (key == 13) {
			var vipbarcode = this.value;
			this.value = "";
			if (vipbarcode.indexOf('#') > -1) {
				var vipidstr = vipbarcode.substring(vipbarcode.indexOf('#') + 1, vipbarcode.length);
				getvipinfobystr(vipidstr);
			}
		}
	});
}
function getvipinfobystr(vipidstr) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getguestvipbyno",
			vipidstr: vipidstr
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if (data.total > 0) {
						var row = data.rows[0];
						$('#uguestid').val(row.GUESTID);
						$('#udiscount').val(row.DISCOUNT);
						$('#alldiscount').val(row.DISCOUNT);
						$('#uguestname').val(row.GUESTNAME);
						$('#vipno').html(row.VIPNO);
						$('#mobile').html(row.MOBILE);
						$('#balcent').html(row.BALCENT);
						$('#vtid').val(row.VTID);
						$('#vtname').html(row.VTNAME);
						$('#balcurr').html(row.BALCURR);
						$('#guestinfo').show();
						changeguest(row.GUESTID);
						alldisc();
						changesetvip();
						$('#uguestname').focus();
					} else alerttext("未找到会员信息！");
				}
			} catch(e) {
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}

$('#gg').datagrid({
    onLoadSuccess : function() {
        var _footer = $('#gg').siblings('div.datagrid-view2').find('.datagrid-footer');
        var $footer_t_b = _footer.find('.datagrid-footer-inner table tbody');
        var $footer_prev_b = _footer.prev('.datagrid-body');
        var itemIndex = $footer_t_b.find('tr').last().attr('datagrid-row-index');
        if (itemIndex) {
            var $first_tr = $footer_t_b.find('tr');
            var lineHeight = $first_tr.first().height();
            var bodyHeight = $footer_prev_b.height();
            var tdCount = $first_tr.find('td:visible').size();
            itemIndex = parseInt(itemIndex) + 1;
            _footer.prev('.datagrid-body').css('height', bodyHeight - lineHeight);
            $footer_t_b.append("<tr class='datagrid-row' datagrid-row-index='"+ itemIndex +"'>" +
                "<td></td>" +
                "<td colspan='"+ (tdCount - 1) +"'>" +
                "<b style='color:#000000;'>单据号：</b>" +
                "<b style='color:#ff3b30;'>红色提交收银台未付款；</b>" +
                "<b style='color:#00959a;margin-left: 5px;'> 绿色已收款／挂账；</b>" +
                "<b style='color:#ff7900;margin-left: 5px;'>黄色未付款／未选商品。</b>" +
                //"<b style='color:#ff7900;margin-left: 15px;'>失效订单</b>" +
                "</td>" +
                "</tr>");
        }
    }
});
</script>
	<!-- 得到全部销售员列表 -->
	<jsp:include page="../HelpList/SalemanHelpList.jsp"></jsp:include>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 会员帮助列表 -->
	<jsp:include page="../HelpList/VipHelpList.jsp"></jsp:include>
	<!-- 有折扣商品明细弹窗 -->
	<jsp:include page="../HelpList/DiscWarem.jsp"></jsp:include>
	<!-- 销售员帮助列表 -->
<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<!-- 条码增加 -->
<jsp:include page="../HelpList/BarcodeAdd.jsp"></jsp:include>
	<!-- 销售部门帮助列表 -->
	<jsp:include page="../HelpList/DPTHelpList.jsp"></jsp:include>
		<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/PayHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>