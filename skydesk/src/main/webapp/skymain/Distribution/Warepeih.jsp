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
<title>批发配货</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addnoted()"><input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()"><input 
	type="text" placeholder="搜索单据号、客户、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20"  data-enter="getnotedata()"><input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn" style="width:20%"><input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
	        <input type="button" class="btn3 hide" style="width:45px" value="导入" onclick="importxls()">
	        <span class="sepreate"></span>
	        <input type="button" class="btn3"  value="导出" onclick="exportxls()">
	   </div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="scustid" id="scustid" value=""> 
<input type="hidden" name="shouseid" id="shouseid" value=""> 
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
<input class="edithelpinput house_help" data-value="#shouseid" id="shousename" placeholder="<输入>" type="text"><span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>

<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;客户</div>
<div class="text">
<input class="edithelpinput cust_help" id="scustname" data-value="#scustid" placeholder="<输入>" type="text"><span class="s-btn" onclick="selectcust('search')"></span>
</div>
</div>

<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput" id="swareno" type="text" placeholder="<输入>" data-value="#swareid"><span class="s-btn" onclick="selectware('search')"></span>
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
<div class="s-box" style="width:330px;">
<div class="title">单据状态</div>
	<div class="text" style="width:260px">
	<label>
	<input type="radio" name="scxfs" id="st4" value="3" checked="checked">所有
	</label>
	<label>
	<input type="radio" name="scxfs" id="st1" value="0">未提交
	</label>
	<label>
	<input type="radio" name="scxfs" id="st2" value="1">已提交未出库
	</label>
	<label>
	<input type="radio" name="scxfs" id="st3" value="2">已出库
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
<!-- 添加与修改批发配货表单 -->
<div id="ud" title="编辑批发配货单" style="width: 100%;height:100%;" class="easyui-dialog" closed="true" data-qickey>
<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" data-btn="updatebtn" id="pay" value="提交" onclick="updatewarepeih()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="load" value="载入客户订单" onclick="opencustorderd()"></span>
	<span class="sepreate easyno"></span>
	<span><input class="btn4 easyno" type="button" value="载入未发货" onclick="openloadnosendkc()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="delete" value="删除单据" onclick="delwarepeih()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="discount" value="整单打折" onclick="opendisc()"></span>
	</span>
	<span id="ispayedbar">
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="withdraw()"></span>
<!-- 	<span class="sepreate"></span> -->
<!-- 	<span><input class="btn4" type="button" value="前台打印" onclick="getnotewaremprint($('#unoteno').val(),'1')"></span>	 -->
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="print" value="后台打印"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="selprint" value="选择打印端口" onclick="selprint()"></span>
	<input type="button" class="btn4 hide" style="width:45px" value="导入明细" onclick="importxlsmx()">
	</span>
<!-- 	<span id="copynotebar"> -->
<!-- 	<span class="sepreate"></span> -->
<!-- 	<span><input class="btn4" type="button" id="copybtn" value="复制单据" onclick="setfromnoteno($('#unoteno').val())"></span> -->
<!-- 	<span class="sepreate"></span> -->
<!-- 	<span><input class="btn4" type="button" id="pastebtn" value="粘贴单据" onclick="copynote(3,$('#unoteno').val())"></span> -->
<!-- 	</span> -->
	<span class="sepreate"></span>
	<input type="button" class="btn4" value="导出明细" onclick="openexportdialog()">
	<span id="isupdatedbar">
<!-- 	<span><input class="btn4" type="button" id="print1" value="后台打印"></span> -->
	<span><input class="btn4" type="button" id="printq" value="前台打印" onclick="getnotewaremprint($('#unoteno').val(),'1')"></span>
	</span>

	<!-- 单据提交框 -->
	<form id="udnoteform" action="" method="get">
	<div id="udnote">
	<input type="hidden" id="uid"> 
	<input type="hidden" id="untid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="ucustid">
	<input type="hidden" id="udiscount"> 
	<input type="hidden" id="upricetype">
	<input type="hidden" id="uepid">
	<input type="hidden" id="udptid">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
<div class="search-box">
<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25 wid160" type="text" id="unoteno" readonly>
</div>
</div>
<div class="s-box"><div class="title">日期</div>
<input id="unotedate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false,onShowPanel: setTodayDate,onHidePanel: changedate">
<input  id="uaddress" type="hidden" readonly><input  id="utel" type="hidden" readonly>
</div>
<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;店铺</div>
<div class="text"><input class="edithelpinput wid145 house_help" type="text" id="uhousename" data-value="changenotehouse"><span onclick="selecthouse('update')"></span>
</div>
</div>
<div class="s-box"><div class="title" style="color:#ff7900">*&nbsp;客户</div>
<div class="text"><input class="edithelpinput cust_help" type="text" data-value="changenotecust" id="ucustname" placeholder="<选择>" ><span id="selcustspan" onclick="selectcust('update')" ></span>
</div>
</div>
<div class="s-box" id="div_custbal" style="display: none">
<div class="title">累计欠款</div>
<div class="text">
<span id="ucustbalcurr"></span>
</div>
</div>
<div class="s-box"><div class="title">销售</div>
	<div class="text"><input class="edithelpinput emp_help" type="text" id="usalemanname"  data-event="onSelect: changeemp" placeholder="<选择>">
	<span onclick="selectemploye('updateep')" ></span>
	</div>
	</div>
<div class="s-box"><div class="title">销售部门</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="edithelpinput dpt_help" data-value="changedpt" id="udptname" placeholder="<输入>"><span onclick="selectdepartment('update')"></span>
	</div>
	</div>
<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="hig25 wid162" id="uhandno" placeholder="<输入>"  onchange="changehandno(this.value)">
	</div>
</div>
<div class="s-box" id="div_orderno"><div class="title">客户订单</div>
<div class="text" id="uorderno">
</div>
</div>
	<div class="clearbox">
	<div class="title">摘要</div>
	<div class="text"><input maxlength="100" type="text" id="uremark" autocomplete="off" class="hig25 wid_remark" placeholder="<输入>" onchange="changeremark(this.value)">
	</div>
	</div>
	</div>
	</div>
	</form>
	<div class="warem-toolbars">
		<div class="fl"><table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="warembar" onclick="updown()" style="color:#ff7900">▲&nbsp;&nbsp;商品明细</td>
<!-- 		<td id="wquickuwaretd"><input type="text" id="wquickuwareno"  class="edithelpinput wid145" placeholder="选择或输入货号"  data-end="yes" data-value="quickaddwarems"><span onclick="if(isAddWarem()){selectware('quickupdatew1')}"></span></td> -->
<!-- 		<td id="wquickusaletd"><input type="text" id="wquickusalename"  class="edithelpinput wid145 sale_help" placeholder="选择销售类型"  data-end="yes" data-value="#usaleid" value="首单" ><span onclick="selectsaletype('quickupdatew')"></span></td> -->
		<td id="wquickuwaretd"><input type="text" id="wquickuwareno"  class="edithelpinput wid145" placeholder="选择或输入货号" data-end="yes" data-value="getwaresaleid"><span onclick="if(isAddWarem()){selectware('getwaresaleid')}"></span></td>
<!-- 		<td id="wquickusaletd"><input type="text" id="wquickusalename"  class="edithelpinput wid145 sale_help" placeholder="选择销售类型"  data-end="yes" data-value="qaddwarem" value="首单" ><span onclick="selectsaletype('qaddwarem')"></span></td> -->
		</tr>
		</table>
		<input type="hidden" id="wquickuwareid" >
		<input type="hidden" id="usaleid" value="2">
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
	<div id="printgg" style="width:100%;overflow:auto;display:none;"></div>
	<div>
	<table id="waret" class="table1"></table>
	<div id="warenametable" class="line30 mt10">
	<div class="fr mr20" align="right" id="wmtotalnote"></div>
	</div>
	</div>
</div>
<!-- 整单打折框 -->
<div id="discd" title="整单打折" style="width: 350px; height: 200px"
		class="easyui-dialog" closed="true">
		<div style="margin-left: 40px;margin-top: 30px">
		<p>请输入折扣</p>
		<p><input class="hig25" id="alldiscount" type="text" placeholder="<请输入折扣>" data-enter="alldisc()"/></p>
		</div>
		<div class="dialog-footer">
				<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="保存" onclick="alldisc()">
				<input type="button" class="btn2" name="close" value="取消" onclick="$('#discd').dialog('close')">
	</div>
</div>

<!-- 订单载入窗 -->
<div id="custorderd" title="载入客户订单" style="width: 800px; height: 370px" class="easyui-dialog" closed="true">
<div style="margin: 0 10px;">
<table id="custordert"></table>
</div>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total" id="custorder_total">共有0条记录</div>
	<div class="page-total">
	<label>
	<input type="checkbox" id="loadkc" checked>载入有库存的商品
	</label>
	</div>
	<div id="custorderpp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<div id="loadkcd" title="载入未发货" style="width: 300px; height: 410px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
<div class="textcenter">
<table class="table1" border="0" cellspacing="5" cellpadding="5">
  <tr>
    <td width="67" class="header" align="right">货号</td>
    <td width="170" align="right">
    <input class="edithelpinput wareno_help" data-value="#lwareid" type="text" id="lwareno" placeholder="<输入>">
    <span onclick="selectware('load')"></span>
	<input type="hidden" id="lwareid">
    </td>
  </tr>
  <tr>
    <td class="header" align="right">商品名称</td>
    <td align="left"><input class="hig25 wid160" type="text" id="lwarename" placeholder="<输入>"></td>
  </tr>
    <tr>
    <td width="67" class="header" align="right">品牌</td>
    <td width="170" align="right"><input class="edithelpinput brand_help" data-value="#lbrandid" type="text" id="lbrandname" placeholder="<输入>">
    <span onclick="selectdx_brand('load')"></span>
    <input type="hidden" id="lbrandid">
    </td>
  </tr>
  <tr>
    <td class="header" align="right">类型</td>
    <td align="right">
    <input class="edithelpinput" type="text" id="lwaretype" readonly  placeholder="<输入>">
    <span onclick="selectwaretype('load')"></span>
    <input type="hidden" id="ltypeid">
    </td>
  </tr>
  <tr id="centdivm">
    <td class="header" align="right">季节</td>
    <td align="right">
<input  type="text" class="edithelpinput season_help" name="lseasonname" id="lseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span></td>
  </tr>
    <tr>
    <td width="67" class="header" align="right" color="#ff7900">*&nbsp;供应商</td>
    <td width="170" align="right"><input class="edithelpinput provd_help" data-value="#lprovid" type="text" id="lprovname" placeholder="<输入>">
    <span onclick="selectdx_prov('load')"></span>
    <input type="hidden" id="lprovid">
    </td>
  </tr>
  <tr>
    <td class="header" align="right">生产年份</td>
    <td align="left"><input class="hig25 wid160" type="text" id="lprodyear" placeholder="<输入>"></td>
  </tr>
  <tr>
    <td class="header" align="right">区位</td>
    <td align="right">
    <input class="edithelpinput area_help" data-value="#lareaid" type="text" id="lareaname" placeholder="<输入>">
    <span onclick="selectdx_area('load')"></span>
    <input type="hidden" id="lareaid">
    </td>
  </tr>
  <tr>
    <td class="header" align="right">货位</td>
    <td align="left"><input class="hig25 wid160" type="text" id="llocale" placeholder="<输入>"></td>
  </tr>
</table>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="warepeiloadnosendkc();" />
<input type="button" class="btn2" value="取消" onclick="$('#loadkcd').dialog('close')" />
</div>
</div>
<script type="text/javascript">
var levelid = getValuebyName("GLEVELID");
var pgid = 'pg1309';
var datenow; //打印当前日期 
var OPERANT; //制单人
var SALEMANNAME; //销售人
var defsalemanbj = 1;
var f4 = "setwarepeipaylist()";
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		height: height - 59
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
	},{
		field: 'BARCODE',
		title: '条码',
		width: 120,
		expable: false,
		fixed: true,
		hidden: true,
		halign: 'center',
		align: 'left'
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
		field: 'RETAILSALE',
		title: '零售价',
		width: 70,
		fieldtype: "N",
		fixed: true,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		}
	},{
		field: 'PRICE0',
		title: '原价',
		fieldtype: "n",
		width: 70,
		fixed: true,
		halign: 'center',
		align: 'right',
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
		formatter: function(value, row, index) {
			return value == undefined ? '': Number(value).toFixed(2);
		},
		styler: function(value,row,index){
			var entersale = Number(row.ENTERSALE);
			value = Number(value);
			if(entersale>0){
				if(entersale>value) return "color:red;font-weight:600";
			}
		}
	},{
		field: 'CURR',
		title: '金额',
		fieldtype: "N",
		width: 100,
		fixed: true,
		halign: 'center',
		align: 'right',
		formatter: function(value, row, index) {
			return value == undefined ? '': (Number(value).toFixed(2));
		}
	},{
		field: 'SALENAME',
		title: '销售类型',
		width: 55,
		fixed: true,
		halign: 'center',
		align: 'center'
	},{
		field: 'REMARK0',
		title: '备注',
		width: 100,
		fixed: true,
		halign: 'center',
		align: 'left'
	});
	return colums;
}
function isAddWarem() {
	var houseid = $('#uhouseid').val();
	var custid = $('#ucustid').val();
	var saleid = $('#usaleid').val();
	var salemanname = $('#usalemanname').val();
	if (houseid == '0' || houseid == '' || houseid == undefined) {
		alerttext('请选择店铺');
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#uhousename').focus();
		return false;
	} else if (saleid == '' || saleid == undefined) {
		alerttext('请选择销售类型');
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#wquickusalename').focus();
		return false;
	} else if (salemanname == '' || salemanname == undefined) {
		alerttext("请选择销售人员");
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#usalemanname').focus();
		return false;
	} else if (custid == '0' || custid == '' || custid == undefined) {
		alerttext("请选择客户");
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#ucustname').focus();
		return false;
	} else {
		return true;
	}
}
var creditok = '0';
var notedateobj = {
		id: "#uid",
		noteno: "#unoteno",
		notedate: "#unotedate",
		action: "updatewarepeihbyid"
	}
$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('body').delegate('.datagrid', 'keydown',
	function(e) {
		var key = e.which;
		if (key == 113) {
			addnoted();
		}
	});
	setaddbackprint("1309");
	$('div.div_sort .label_sort').click(function() {
		var $this = $(this);
		if ($this.hasClass('icon_jiantou_up')) $this.removeClass("icon_jiantou_up").addClass("icon_jiantou_down");
		else $this.removeClass("icon_jiantou_down").addClass("icon_jiantou_up");
		getnotewarem($('#unoteno').val(), "1");
	});
	setwarem();
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
	wareoutparams = getparam({
		pgid: "wareoutcs",
		usymbol: "wareoutparams" 
	}); 
	//调拨参数见自定义参数说明.txt
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
	if(defsalemanbj==0){
		$("#defsalemanbj").prop("checked",true);
	}else{
		$("#defsalemanbj").removeProp("checked");
	}
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		autorefresh: true,
		backFn: function(p) {
			getnotelist(p);
		}
	});
	$('#gg').datagrid({
		idField: 'warepeih',
		width: '100%',
		height: $('body').height() - 59,
		fitColumns: true,
		striped: true,
		//隔行变色
		nowrap: false,
		showFooter: true,
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			$('#udnoteform')[0].reset();
			$('#udnoteform input[type=hidden]').val('');
			$('#uhouseid').val(getValuebyName("HOUSEID"));
			if (rowData) {
				$('#uid').val(rowData.ID);
				$('#unoteno').val(rowData.NOTENO);
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
				$('#ucustid').val(rowData.CUSTID);
				$('#ucustname').val(rowData.CUSTNAME);
				$('#uhandno').val(rowData.HANDNO);
				$('#wtotalcurr').val(rowData.TOTALCURR);
				$('#wtotalamt').val(rowData.TOTALAMT);
				$('#uoperant').val(rowData.OPERANT);
				$('#untid').val(rowData.NTID);
				$('#udiscount').val(rowData.DISCOUNT);
				$('#upricetype').val(rowData.PRICETYPE);
				$('#ustatetag').val(rowData.STATETAG);
				$('#uremark').val(rowData.REMARK);
				$('#uaddress').val(rowData.ADDRESS);
				$('#utel').val(rowData.TEL);
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
			if (field == "ORDERNO") {
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
				if (Number(row.STATETAG) >= 1) {
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
			field: 'CUSTNAME',
			title: '客户',
			width: 100,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'ADDRESS',
			title: '地址',
			width: 140,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'TEL',
			title: '电话',
			width: 100,
			fixed: true,
			align: 'left',
			halign: 'center'
		},{
			field: 'ORDERNO',
			title: '客户订单号',
			width: 120,
			align: 'center',
			halign: 'center',
			fixed: true
		},
		{
			field: 'HOUSENAME',
			title: '店铺名称',
			width: 100,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'HANDNO',
			title: '自编号',
			width: 100,
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
			halign: 'center',
			align: 'right',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'TOTALFACTAMT',
			title: '已配货',
			width: 80,
			fixed: true,
			hidden: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'TOTALCYAMT',
			title: '未配货',
			width: 80,
			fixed: true,
			hidden: true,
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
			field: 'PRTNUM',
			title: '已打印次数',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'XSSTG',
			title: '配货状态',
			width: 120,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var xsnoteno = row.XSNOTENO;
				var stg = Number(row.STATETAG);
				if (xsnoteno != undefined) {
					if (xsnoteno.length > 0 && stg==4) return '待出库：' + xsnoteno;
					else if(xsnoteno.length > 0 && stg==5) return '已配货出库：' + xsnoteno;
					return "未配货出库";
				}
			}
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
			field: 'CUSTID',
			title: '客户ID',
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
		},
		{
			field: 'XSNOTENO',
			title: '销售单号',
			width: 200,
			hidden: true
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid('keyCtr', "updatenoted()");
	getnotedata();
	getdefaultcust();
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
	$('#shousename,#shouseid,#shandno,#scustname,#scustid,#swareno,#swareid,#sremark,#soperant').val('');
	$('#st4').prop('checked', true);
}
//收缩展开
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
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "warepeihlistpc";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.noteno,a.accid,a.notedate,a.custid,a.houseid,a.handno,a.xsnoteno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalfactamt,a.totalcurr,b.custname,b.discount,b.pricetype,b.address,b.tel,c.housename,a.orderno,a.prtnum";
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
					var factamt = data.totalfactamt;
					var cyamt = data.totalcyamt;
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					data.footer = [{
						ROWNUMBER: "合计",
						TOTALAMT: dqzamt,
						TOTALFACTAMT: factamt,
						TOTALCYAMT: cyamt,
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
		cxfs: 3
	};
	getnotelist(1);
}
//高级搜索
function searchnote() {
	var houseid = Number($('#shouseid').val());
	var custid = Number($('#scustid').val());
	var handno = $('#shandno').val();
	var remark = $('#sremark').val();
	var wareid = Number($('#swareid').val());
	var operant = $('#soperant').val();
	var cxfs=3;
	if ($('#st1').prop('checked')) {
		cxfs = "0";
	} else if ($('#st2').prop('checked')) {
		cxfs = "1";
	} else if ($('#st3').prop('checked')) {
		cxfs = "2";
	} else {
		cxfs = "3";
	}
	var fhtag = $('input[name=sfhtag]:checked').val();
	currentdata = {
		houseid: houseid,
		handno: handno,
		custid: custid,
		wareid: wareid,
		remark: remark,
		operant: operant,
		fhtag: fhtag,
		cxfs: cxfs
	};
	getnotelist(1);
}
var defaultcustdata = {CUSTID:0};
function getdefaultcust() {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名            
		data: {
			erpser: "getdefaultcust"
		},
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data,false)) {
				defaultcustdata = data;
			}
		}
	});
}
//增加单据
function addnoted() {
	$('#udnote').show();
	$('#div_custbal').hide();
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
	$('#uhouseid').val(getValuebyName("HOUSEID"));
	$('#uhousename').val(getValuebyName("HOUSENAME"));
	$('#usaleid').val('2');
	$('#wquickusalename').val('首单');
	$('#addnotenbtn').attr('disabled', 'disabled');
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
	$('#uremark').removeAttr('readonly');
	$('#uselecthouseb').removeAttr('disabled');
	$('#uselectcustb').removeAttr('disabled');
	$('#isupdatedbar,#div_orderno').hide();
	$('#ispayedbar').hide();
	$('#updatingbar').show();
	$('#wquickuwaretd').show();
	$('#wquickusaletd').show();
	$('#wquickuwareno').val('');
	$('#uhousename').next().show();
	$('#ucustname').removeAttr('readonly');
	$('#ucustname').next().show();
	$('#usalemanname').next().show();
	$('#ud #warem-toolbar').show();
	$('#udptname,#usalemanname').removeAttr('readonly');
	$('#udptname').next().show();
	$('#unotedate').datetimebox({
		readonly: false,
		hasDownArrow: true
	});
	addwarepeih();
	$('#ud').dialog({
		title: '增加批发配货单'
	});
	$('#ustatetag').val('0');
	$('#ud').dialog('open');
	$('#addnote').removeAttr('disabled');
	$('#ud').data('qickey', "F4:'openpay()',F6:'opendisc()',F7:'loadcg($(\"#ucustid\").val())',Del:'delwarepeih()'");
	$('#wquickuwareno').focus();

}
//编辑单据
function updatenoted() {
	if (detail_bol) return;
	$('#udnote').show();
	$('#div_custbal').hide();
	$('#warembar').html('▲&nbsp;&nbsp;商品明细');
	setTimeout(function() {
		$('#waret').datagrid('resize', {
			height: $('body').height() - 90
		});
	},
	200);
	d = false;
	//$('#div_waremx').hide();
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
	if ($("#ucustid").val() == '0' || $("#ucustid").val() == '') {
		alerttext('请选择客户');
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
		idField: 'ID',
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
				$('#usaleid').val(rowData.SALEID);
				$('#wquickusalename').val(rowData.SALENAME);
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
	$('#alldisc,#allpri').val('');
	$('#quickutable').html('');
	var arrs = $('#waret').datagrid('getSelected');
	if (arrs == null) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var state = $('#ustatetag').val();
		if (state == '0') {
			$('#wquickuwareid').val(arrs.WAREID);
			getwaremsum(arrs.SALEID, arrs.WAREID, $('#unoteno').val(), false);
		}
	}
}
function delwaremx() {
	var noteno = $('#unoteno').val();
	var rowData = $('#waret').datagrid("getSelected");
	if (rowData) {
		var wareid = rowData.WAREID;
		var colorid = rowData.COLORID;
		var saleid = rowData.SALEID;
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
						erpser: "delwarepeimsum",
						noteno: noteno,
						wareid: wareid,
						colorid: colorid,
						saleid: saleid
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
			erpser: "warepeimsumlist",
			sizenum: sizenum,
			noteno: noteno,
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
					ROWNUMBER: "合计",
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
//获取需要打印的配货单据
function getnotewaremprint(noteno, page) {
	alertmsg(6);
	var sortid = $('#waremsort').prop('checked') ? 1 : 0;
	var houseid = xsdpdz == 1 ? $('#uhouseid').val() : "";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "WarepeiServlet",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			warepeiser: "getwarepeimdatap",
			noteno: noteno,
			sortid: sortid,
			houseid: houseid,
			page: 1
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'text',
		success: function(text) { //回调函数，result，返回值 
			if (isSucess(text)) {
				var data = $.parseJSON(text);
				var htmlStrp = '<table>';
				$('#printgg').html("");
				var totals = data.total.split('-');
				var xiaoji = totals[1]; //小计合计
				var jine = totals[2]; //金额
				var BALCURR = data.BALCURR; //累计欠款
				var TOTALCURR = data.TOTALCURR; //应付账款
				var dataArr = data.rows; //htmlStr
				// OPERANT=dataArr
				var jiesuan = data.jiesuan;
				var html1;
				var html2;
				// var html5='<tr class="a3" width="100%"><td>合计</td><td></td><td></td><td></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td width="30"></td><td align="right" width="50">'+xiaoji+'</td><td width="50"></td><td width="50"></td><td width="50" align="right">'+jine+'</td></tr>';
				var html5 = '<tr class="a3"><td width="50px">合计</td><td width="50px">&nbsp;</td><td width="50px">&nbsp;</td>';
				//alert(cols);
				for (var j = 1; j <= cols; j++) {
					html5 += '<td width="20px">&nbsp;</td>';

				}
				html5 += '<td align="right" width="50px">' + xiaoji + '</td><td width="50px">&nbsp;</td><td width="50px">&nbsp;</td><td width="50px" align="right">' + jine + '</td></tr>';
				var html6;
				htmlStrp += '<caption><b><font face="黑体" size="5">批发配货单<br/>&nbsp;</font></b></caption>';
				htmlStrp += '<tr class="t3"><th align="left"  colspan="10">单据号：' + $("#unoteno").val() + '</th><th  colspan="' + eval(cols - "3") + '" align="right">单据日期：' + $("#unotedate").val() + '</th></tr>';
				htmlStrp += '<tr class="t2"><th align="left"  colspan="10">客户：' + $("#ucustname").val() + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系方式：' + $("#utel").val() + '&nbsp;&nbsp;' + $("#uaddress").val() + '</th><th  colspan="' + eval(cols - "3") + '"  align="right">店铺名称：' + $("#uhousename").val() + '</th></tr>';
				htmlStrp += '<tr class="t2"><th align="left"  colspan="' + eval(cols + "7") + '" >摘要：' + $("#uremark").val() + '　</th></tr>';

				for (var i = 0; i < dataArr.length; i++) {
					if (i == 0) {
						html1 = '<tr class="a3"><td align="center" width="auto">货号</td><td align="center" width="auto">商品名称</td><td align="center" width="60px">颜色</td>';
						html2 = '<tr><td align="left">' + dataArr[i].WARENO + '</td><td align="center">' + dataArr[i].WARENAME + '</td><td align="center">' + dataArr[i].COLORNAME + '</td>'
						for (var j = 1; j <= cols; j++) {
							html1 += '<td width="30px" align="center">' + eval("dataArr[" + i + "].SIZENAME" + j) + '</td>';
							html2 += '<td width="30px" align="center">' + (Number(eval("dataArr[" + i + "].AMOUNT" + j)) == 0 ? "&nbsp;": Number(eval("dataArr[" + i + "].AMOUNT" + j))) + '</td>';

						}
						html1 += '<td align="center" width="50px">小计</td><td align="center" width="50px">单价</td><td align="center" width="50">折扣</td><td align="center" width="50">金额</td></tr>';
						html2 += '<td align="right">' + dataArr[i].AMOUNT + '</td><td align="right">' + dataArr[i].PRICE0 + '</td><td align="right">' + dataArr[i].DISCOUNT + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
						htmlStrp += html1;
						htmlStrp += html2;
					} else {
						html1 = '<tr class="a3"><td align="center" width="50px">货号</td><td align="center" width="100px">商品名称</td><td align="center" width="60px">颜色</td>';
						html2 = '<tr><td align="left">' + dataArr[i].WARENO + '</td><td align="center">' + dataArr[i].WARENAME + '</td><td align="center">' + dataArr[i].COLORNAME + '</td>'
						for (var j = 1; j <= cols; j++) {
							html1 += '<td align="center">' + eval("dataArr[" + i + "].SIZENAME" + j) + '</td>';
							html2 += '<td align="center" width="20px">' + (Number(eval("dataArr[" + i + "].AMOUNT" + j)) == 0 ? "": eval("dataArr[" + i + "].AMOUNT" + j)) + '</td>';

						}
						html1 += '<td align="center" width="50px">小计</td><td align="center" width="50px">单价</td><td align="center" width="50px">折扣</td><td align="center" width="50px">金额</td></tr>';
						html2 += '<td align="right">' + dataArr[i].AMOUNT + '</td><td align="right">' + dataArr[i].PRICE0 + '</td><td align="right">' + dataArr[i].DISCOUNT + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';

						if (dataArr[i].SIZEGROUPNO == dataArr[i - 1].SIZEGROUPNO) {
							htmlStrp += html2;
						} else {
							htmlStrp += html1;
							htmlStrp += html2;
						}
					}

				}
				htmlStrp += html5;
				html6 = '<tr class="t3"><th align="left" colspan="' + eval("7" + cols) + '">';
				var fkhj = 0; //付款合计
				for (var i = 0; i < jiesuan.length; i++) {
					TOTALCURR = Number(TOTALCURR) - Number(jiesuan[i].CURR);
					html6 += jiesuan[i].PAYNAME + ':' + jiesuan[i].CURR + '&nbsp;&nbsp;&nbsp;&nbsp;';
				}
				var gua = TOTALCURR; //挂账
				var shang = Number(BALCURR) - Number(gua);
				html6 += '付款合计：' + fkhj + '</tr>';
				htmlStrp += html6;
				htmlStrp += '<tr class="t3"><th align="left" colspan="10">挂账金额：' + gua + '　</th><th colspan="' + eval(cols - "3") + '" align="right">上次欠款：' + shang + '</th></tr>';
				htmlStrp += '<tr class="t3"><th align="left"  colspan="10">累计欠款：' + BALCURR + '</th></tr>';
				htmlStrp += '<tr class="t3"><th align="left" colspan="10">销售员：' + SALEMANNAME + '</th><th colspan="' + eval(cols - "3") + '" align="right">制单：' + OPERANT + '</th></tr>';

				htmlStrp += '</table>';
				//alert(htmlStrp);
				$('#printgg').html(htmlStrp);
				if (page == '2') {
					demoPrint(true);
				} else {
					demoPrint();
				}

			}
		}
	});
}
//配货单据打印
function demoPrint(toPrview) {
	LODOP = getLodop();
	var style1 = "table{width:100%;border-style: solid;border-color: #000000;  border-collapse:collapse;border-width:0px 0px 0px 0px;}" + "table td{border: solid #000000; border-width:0px 1px 1px 1px;width:auto;}" + "table .t2 th{border-style: solid;border-color: #000000;border-width:0px 0px 0px 0px;}" + "table .t2{border-style: solid;border-color: #000000;font-weight:bold;border-width:0px 0px 0px 0px;}" + "table .t1{font-weight: bold;font-size:18px;border-width:0px 0px 0px 0px;}" + "table .t1 td{border-width:0px 0px 0px 0px; }" + "table .a3{border: solid #000000;border-width:2px 1px 1px 1px;}" + "table .a3 td{border: solid #000000;border-width:2px 1px 1px 1px;}" + "table .t3 td{border-width:0px 0px 0px 0px;}";
	//+".a1 td{border: solid #000000;border-width:1px 1px 3px 1px;}"
	//+".t3{text-align:right;}";
	var strBodyStyle = "<style>" + style1 + "</style>";
	var strFormHtml = strBodyStyle + "<body>" + document.getElementById("printgg").innerHTML + "</body>";
	LODOP.SET_PRINTER_INDEXA("0");
	LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
	LODOP.PRINT_INIT("批发配货单");
	LODOP.SET_PRINT_STYLE("FontSize", 18);
	LODOP.SET_PRINT_STYLE("Bold", 1);
	LODOP.ADD_PRINT_TEXT(20, 5, 760, 900, "批发配货单");
	LODOP.ADD_PRINT_HTM(20, 5, 760, 900, strFormHtml);
	if (toPrview) LODOP.PREVIEW();
	else LODOP.PRINT();
};
function quickupdatesum() {
	var list = $('#quickutables tr:eq(1) input[type=text]').length - 3; //横向文本框数
	var line = $('#quickutables tr').length - 3; //竖向文本框数
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
					var curr = Number((amount1[i] * Number($('#upri' + i).val())).toFixed(points));
					$('#uacurr' + i).html(curr.toFixed(2));
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
//获取最近销售类型
function getnearsaleid(wareid) {
	var custid = $('#ucustid').val();
	if (custid != "" && custid != '0') {
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getwareoutmsaleid",
				wareid: wareid,
				custid: custid,
				ntid: 1
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				var saleid = 2;
				var salename = "首单";
				if (valideData(data, false)) {
					saleid = data.SALEID;
					salename = data.SALENAME;
				}
				$('#usaleid').val(saleid);
				$('#wquickusalename').val(salename);
				$('#wquickusalename').focus();
				var noteno = $('#unoteno').val();
				getwaremsum(saleid, wareid, noteno, true);
				$('#span_sale').show();
			}
		});
	} else {
		alerttext('请选择客户');
		$('#ucustname').focus();
	}
}
var hasTable = false;
function getwaremsum(saleid, wareid, noteno, allowchangesale) {
	$('#quickutable').html('');
	var houseid = $('#uhouseid').val();
	var custid = $('#ucustid').val();
	if ($('#quickutables').length == 0 && hasTable == false) {
		hasTable = true;
		alertmsg(6);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getwarepeimsum",
				noteno: noteno,
				wareid: wareid,
				houseid: houseid,
				custid: custid,
				saleid: saleid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					var wareno = data.WARENO;
					var warename = data.WARENAME;
					var entersale = Number(data.ENTERSALE);
					var colorlist = data.colorlist;
					var sizelist = data.sizelist;
					var amountlist = data.amountlist;
					var amtl = getJsonLength(amountlist);
					var kclist = data.kclist;
					var kchj = [];
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
					for (var i = 0; i <= line + 2; i++) {
						//alert(line);
						//创建tr
						var tr = document.createElement("tr");
						var curr = 0;
						var amt = 0;
						var disc = 1;
						var price0 = data.PRICE0;
						var defaultdisc = data.DISCOUNT;
						var price = data.PRICE;
						var remark = "";
						for (var j = 0; j <= list + 6; j++) {
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
								} else if (j == list + 6) {
									td.style.width = '100px';
									td.innerHTML = "备注";
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
									if (j == (list + 1) || j == (list + 6)) {
										td.align = "center";
									} else if (j == (list + 2) || j == (list + 5)) {
										td.align = "right";
									}
									td.id = "asum" + j;
									td.innerHTML = "";
								}
							} else if (i == line + 2) {
								if (j == 0) {
									td.innerHTML = "库存合计";
								} else if (j == list + 1) {
									var kcsum = new Decimal(0);
									for (var k in kchj) {
										if (kchj[k] != undefined) kcsum = kcsum.add(kchj[k]);
									}
									td.innerHTML = kcsum.valueOf();
								} else if (j > 0 && j < list + 1) {
									td.innerHTML = kchj[j] == undefined ? "": kchj[j].valueOf();
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
									input.value = Number(defaultdisc).toFixed(2);
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
										if (Number(price2) < entersale) $('#upri' + t).css('color', "#ff0000");
										else $('#upri' + t).css('color', "#323232");
										quickupdatesum();
									});
									$(input).change(function() {
										this.value = Number(this.value).toFixed(2);
									});
								} else if (j == list + 4) {
									var input = document.createElement("input");
									input.id = "upri" + i;
									input.type = "text";
									input.style.width = "95%";
									input.style.border = "none";
									input.style.textAlign = "right";
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
										if (Number(this.value) < entersale) this.style.color = "#ff0000";
										else this.style.color = "#323232";
										quickupdatesum();
									});
									$(input).on("change",
									function() {
										this.value = getbypoints(this.value).toFixed(2);
									});
									td.appendChild(input);
								} else if (j == list + 5) {
									td.align = "right";
									td.id = "uacurr" + i;
									td.innerHTML = (curr == undefined ? 0 : curr).toFixed(2);
								} else if (j == list + 6) {
									td.align = "left";
									var input = document.createElement("input");
									input.type = "text";
									input.style.width = "95%";
									input.style.border = "none";
									input.style.textAlign = "left";
									input.maxLength = 60;
									input.setAttribute("placeholder", "<输入>");
									input.autocomplete = 'off';
									input.id = "uremark" + i;
									input.value = remark;
									td.appendChild(input);
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
										input.setAttribute("placeholder", "");
									} else {
										for (r in amountlist) {
											var colorids = amountlist[r].COLORID;
											var sizeids = amountlist[r].SIZEID;
											if (colorids == colorid && sizeid == sizeids) {
												price0 = amountlist[r].PRICE0;
												price = amountlist[r].PRICE;
												defaultdisc = amountlist[r].DISCOUNT;
												var val = input.value == '' ? 0 : Number(input.value);
												input.value = val + Number(amountlist[r].AMOUNT);
												amt = Number(amountlist[r].AMOUNT) + amt;
												curr = curr + Number(amountlist[r].CURR);
												remark = amountlist[r].REMARK0;
												//break;
											}
										}
										for (r in kclist) {
											var colorids = kclist[r].COLORID;
											var sizeids = kclist[r].SIZEID;
											if (colorids == colorid && sizeid == sizeids) {
												input.setAttribute("placeholder", kclist[r].AMOUNT);
												if (kchj[j] == undefined) kchj[j] = new Decimal(0);
												kchj[j] = kchj[j].add(kclist[r].AMOUNT);
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

					var tname = amtl == 0 ? "添加": "修改";
					if (allowchangesale) {
						$('#wquickusalename').next().show();
						$('#wquickusalename').removeAttr("readonly");
					} else {
						$('#wquickusalename').next().hide();
						$('#wquickusalename').attr("readonly", true);

					}
					var title = tname + "商品明细：" + wareno + "，" + warename + "，" + data.SALENAME;
					$('#wquickusalename').val(data.SALENAME);
					$('#usaleid').val(data.SALEID);
					$('#quickud').dialog({
						title: title,
						top: 10,
						// 			        		left: 10,
						width: (list) * 52 + 550,
						// 			 				height:(line)*35+180,
						modal: true,
						onOpen: function() {}
					});
					if ($('#showcusthistory').prop('checked')) {
						if ($('#custwarehistoryt').data().datagrid == undefined) {
							setcustwarehistt();
						}
						$('#custwarehistorydiv').show();
						$('#custwarehistoryt').datagrid('resize');
						$('#quickud').window('center');
						listsalehistory(1);
					} else {
						$('#custwarehistorydiv').hide();
						$('#quickud').window('center');
					}
					$('#quickud').dialog('open');
					var out_wid = (list) * 52 + 550;
					$('#quickutables').width(out_wid - 40);
					$('#u1-1').focus();
				}
				hasTable = false;
			}
		});
	}
}

function addwarepeih() {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addwarepeih",
			custid: defaultcustdata.CUSTID,
			noxsman: defsalemanbj,
			houseid: getValuebyName("HOUSEID")
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var id = data.ID;
				$('#uid').val(id);
				$('#unoteno').val(data.NOTENO);
				if(defsalemanbj==1)
					$("#usalemanname").val("");
				else
					$("#usalemanname").val(getValuebyName("EPNAME"));
				changedatebj = false;
				$('#unotedate').datetimebox("setValue",data.NOTEDATE);
				changedatebj = true;
				$('#uhandno').val('');
				$('#ucustname').val('');
				dqindex = 0;
				dqamt = 0;
				dqcurr = 0;
				$('#qsnotevalue').val("");
				$('#qsnotevalue').val("");
				$("#smaxdate").datebox("setValue", top.getservertime());
				if(defaultcustdata.custid>0){
					var custname = defaultcustdata.CUSTNAME;
					var custid = defaultcustdata.CUSTID;
					var pricetype = defaultcustdata.PRICETYPE;
					var discount = defaultcustdata.DISCOUNT;
					$('#ucustname').val(custname);
					$('#ucustid').val(custid);
					$('#udiscount').val(discount);
					$('#upricetype').val(pricetype);
				}
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
	delwarepeih();
}
//删除单据
function delwarepeih() {
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
					erpser: "delwarepeihbyid",
					noteno: noteno
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

//选择店铺
function changehouse(id, noteno, houseid) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewarepeihbyid",
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
				if(xsdpdz==1) getcustbalcurr();
			}
		}
	})
};

//选择客户
function changecust(id, noteno, custid, discount, pricetype) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewarepeihbyid",
			noteno: noteno,
			custid: custid,
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
						CUSTNAME: $('#ucustname').val(),
						CUSTID: custid,
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
							erpser: "changewarepeimdisc",
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
				getcustbalcurr();
			}
		}
	});
}
//销售员
function changeemp(row) {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewarepeihbyid",
			noteno: noteno,
			salemanid: row.EPID
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						SALEMANID: row.EPID
					}
				});
			}
		}
	})
}
function getcustbalcurr() {
	var custid = Number($('#ucustid').val());
	var houseid = xsdpdz == 1 ? $('#uhouseid').val() : "0";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		async: false,
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getincomebal",
			houseid: houseid,
			custid: custid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var balcurr = Number(data.CURR);
				$('#div_custbal').show();
				$('#ucustbalcurr').html(balcurr.toFixed(2));
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
			erpser: "updatewarepeihbyid",
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
			erpser: "updatewarepeihbyid",
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
			erpser: "updatewarepeihbyid",
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
function quickaddwarem() {
	var noteno = $('#unoteno').val();
	var wareid = $('#wquickuwareid').val();
	var saleid = $('#usaleid').val();
	var custid = $('#ucustid').val();
	var ntid = '1';
	if (wareid == undefined || wareid == "") {
		alerttext("请重新选择货号！");
	} else {
		var list = $('#quickutables tr:eq(1) input[type=text]').length - 3; //横向文本框数
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
						if (valuestr != ""&&!isNaN(value)) {
							quickjsonstr.push({
								colorid: $('#uacolor' + i).val(),
								sizeid: $('#uasize' + j).val(),
								amount: value,
								discount: $('#udisc' + i).val(),
								price0: $('#uprice0' + i).html(),
								price: $('#upri' + i).val(),
								remark0: remark0,
								curr: Number($('#upri' + i).val()) * Number(value)
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
				erpser: "writewarepeimsum",
				noteno: noteno,
				wareid: wareid,
				saleid: saleid,
				saleid0: saleid,
				custid: custid,
				rows: JSON.stringify(jsondata)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#quickuaddwaref')[0].reset();
					$('#quickutable').html('');
					getnotewarem(noteno, '1');
					$('#alldisc').val('');
					$('#allpri').val('');
					$('#wquickuwareid').val('');
					$("#wquickuwareno").parent().children("ul").html("");
					$("#wquickuwareno").parent().children("ul").hide();
					$('#quickud').dialog('close');
					$('#wquickuwareno').val('');
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
				erpser: "changewarepeimdisc",
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

function updatewarepeih() {
	if($("#waret").datagrid("getRows").length==0){
		alerttext("商品明细为空，不允许提交！");
		return;
	}
	var id = $('#uid').val();
	var custid = $('#ucustid').val();
	var houseid = $('#uhouseid').val();
	var noteno = $('#unoteno').val();
	var totalcurr = $('#wtotalcurr').val();
	var totalamt = $('#wtotalamt').val();
	var paycurr0 = $('#discpay').val();
	var sumpay = $('#sumpay').val();
	var nopay = $('#nopay').val();
	var pastpay = $('#pastpay').val();
	var nextpay = $('#nextpay').val();
	nopay = isNaN(Number(nopay)) ? 0 : Number(nopay);
	pastpay = isNaN(Number(pastpay)) ? 0 : Number(pastpay);
	nextpay = isNaN(Number(nextpay)) ? 0 : Number(nextpay);
	paycurr0 = isNaN(Number(paycurr0)) ? 0 : Number(paycurr0);
	sumpay = isNaN(Number(sumpay)) ? "0": Number(sumpay);
	var thispay = isNaN(Number($('#thispay').val())) ? "0": Number($('#thispay').val());
	var checkcurr = thispay - paycurr0;
	var creditcurr = $('#creditcurr').val();
	creditcurr = isNaN(Number(creditcurr)) ? "0": Number(creditcurr);
	var notedatestr = $('#unotedate').datetimebox('getValue');
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
		return;
	}
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
					erpser: "updatewarepeihbyid",
					noteno: noteno,
					notedatestr: notedatestr,
					statetag: "1",
					custid: custid,
					houseid: houseid,
					totalcurr: totalcurr,
					totalamt: totalamt
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						$('#ud').dialog('close');
						$('#payd').dialog('close');
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
function withdraw() {
	var noteno = $('#unoteno').val();
	var id = $('#uid').val();
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
						erpser: "warepeihcancel",
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
										STATETAG: 0
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
}
//载入批发订单表
function loadcg(custid) {
	if (Number(custid) == 0 || isNaN(Number(custid))) {
		alerttext("请选择客户！")
	} else {
		// 				$('#loadpp').setPage({
		// 					pageCount :1,
		// 					current:1,
		// 					backFn:function(p){
		// 						custorderhload(p.toString(), custid);
		// 					}
		// 				});
		// 				$('#loadt').datagrid({
		// 					idField : 'warepeih',
		// 					width : '100%',
		// 					height : '300px',
		// 					fitColumns : true,
		// 					striped : true, //隔行变色
		// 					singleSelect : true,
		// 					columns : [ [ {
		// 						field : "CHECKED",
		// 						checkbox: true
		// 					},{
		// 						field : 'ROWNUMBER',
		// 						title : '序号',
		// 						width : 30,
		// 						fixed : true,
		// 						align : 'center',
		// 						formatter : function (value,row,index){
		// 							return index+1;
		// 						}
		// 					},{
		// 						field : 'NOTENO',
		// 						title : '单据号',
		// 						width : 200
		// 					}, {
		// 						field : 'NOTEDATE',
		// 						title : '单据日期',
		// 						width : 200,
		// 						formatter : function(value,row,index){
		// 							return value.substring(0,16);
		// 						}
		// 					}, {
		// 						field : 'CUSTNAME',
		// 						title : '客户',
		// 						width : 200
		// 					}, {
		// 						field : 'TOTALCURR',
		// 						title : '总金额',
		// 						width : 200
		// 					}, {
		// 						field : 'TOTALAMT',
		// 						title : '总数量',
		// 						width : 200,
		// 						formatter : function(value,row,index){
		// 							return Number(value);
		// 						}
		// 					}, {
		// 						field : 'OPERANT',
		// 						title : '制单人',
		// 						width : 200
		// 					} ] ],
		// 					pageSize : 20
		// 				});
		// 				custorderhload("1", custid);
		$('#loadd').dialog({
			modal: true
		});
		$('#loadd').dialog('open');
	}

}
//载入批发订单数据
function custorderhload(page, custid) {
	if (custid != '') {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "./skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "custorderhload",
				rows: pagecount,
				page: page,
				custid: custid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (isSucess(data)) {
					var totals = data.total.split('^');
					$('#loadpp').setPage({
						pageCount: Math.ceil(Number(totals[0]) / pagecount),
						current: Number(page)
					});
					$('#loadt').datagrid('loadData', data);
				}
			}
		});
	}
}
function resetload() {
	$("#loadform input").val("");
}
//获取是否为当天单据
function getnotebyid(id, noteno) {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwarepeihbyid",
			noteno: noteno,
			fieldlist: "a.id,a.noteno,a.accid,a.notedate,a.custid,a.houseid,a.handno,a.dptid,d.dptname," + "a.xsnoteno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.salemanid,e.epname as salemanname," + "a.totalfactamt,a.totalcurr,b.custname,b.discount,b.pricetype,b.address,b.tel,c.housename,a.orderno,a.prtnum"
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
					if (stg>=1||epname!=operant) {
						$('#ud').dialog("setTitle", "浏览批发配货单");
						$('#uhandno').attr('readonly', 'readonly');
						$('#uselecthouseb').attr('disabled', 'disabled');
						$('#uselectcustb').attr('disabled', 'disabled');
						$('#updatingbar').hide();
						$('#isupdatedbar').hide();
						$('#ispayedbar').show();
						$('#wquickuwaretd').hide();
						$('#wquickusaletd').hide();
						$('#uremark').attr('readonly', 'readonly');
						$('#uhousename').attr('readonly', 'readonly');
						$('#uhousename').next().hide();
						$('#ucustname').attr("readonly", 'readonly');
						$('#ucustname').next().hide();
						$('#udptname,#usalemanname').attr("readonly", 'readonly');
						$('#udptname').next().hide();
						$('#usalemanname').next().hide();
						//$('#waret').datagrid('hideColumn','ACTION');
						$('#ud #warem-toolbar').hide();
						var levelid = getValuebyName("GLEVELID");
						if (levelid == '0' || allowchedan == '1') {
							$('#ud').data('qickey', "F8:'openpaydetail()',F9:'$(\"#print\").click()',F10:'withdraw()'");
						} else {
							$('#ud').data('qickey', "F8:'openpaydetail()',F9:'$(\"#print\").click()'");
						}
						$('#unotedate').datetimebox({
							readonly: true,
							hasDownArrow: false
						});
					} else {
						$('#ud').dialog("setTitle", "修改批发配货单");
						$('#usaleid').val('2');
						$('#wquickusalename').val('首单');
						$('#uhousename').removeAttr('readonly');
						$('#uhandno').removeAttr('readonly');
						$('#uselecthouseb').removeAttr('disabled');
						$('#uselectcustb').removeAttr('disabled');
						$('#isupdatedbar').hide();
						$('#ispayedbar').hide();
						$('#updatingbar').show();
						$('#wquickuwaretd').show();
						$('#wquickusaletd').show();
						$('#wquickuwareno').val('');
						$('#uremark').removeAttr('readonly');
						$('#uhousename').next().show();
						$('#ucustname').next().show();
						$('#ucustname').removeAttr("readonly");
						$('#udptname').next().show();
						$('#udptname').removeAttr("readonly");
						$('#usalemanname').next().show();
						$('#ud #warem-toolbar').show();
						//$('#waret').datagrid('showColumn','ACTION');
						$('#ud').data('qickey', "F4:'openpay()',F5:'submitwarepeih()',F6:'opendisc()',F7:'loadcg($(\"#ucustid\").val())',Del:'delwarepeih()'");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
					}
					$('#ud').dialog('open');
					getcustbalcurr();
					getnotewarem($('#unoteno').val(), "1");
					if ($('#ustatetag').val() != '0') {
						$('#unoteno').focus();
					} else {
						$('#wquickuwareno').focus();
					}
					pg = 1;
					var istoday = row.ISTODAY;
					var salemanlist = row.SALEMANNAME;
					var epid = row.SALEMANID;
					SALEMANNAME = salemanlist; //销售人
					OPERANT = row.OPERANT; //制单 
					var dptname = row.DPTNAME;
					var dptid = row.DPTID;
					$('#usalemanname').val(salemanlist);
					$('#udptname').val(dptname);
					$('#udptid').val(dptid);
					$('#uepid').val(epid);
					$('#uistoday').val(istoday);
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					if (allowchedan == '1' && Number(stg) >= 1) {
						$('#sep-withdraw').show();
						$('#withdrawbtn').show();
					} else {
						$('#sep-withdraw').hide();
						$('#withdrawbtn').hide();
					}

				}
			}
		}
	});
}

function setwarepeipaylist() {
	var rowdata = $('#gg').datagrid("getSelected");
	if (rowdata) {
		if (Number(rowdata.STATETAG) >= 1) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "setwarepeipaylist",
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
var custorderser = true;
function opencustorderd() {
	var houseid = Number($('#uhouseid').val());
	var custid = Number($('#ucustid').val());
	if (houseid > 0 && custid > 0) {
		if (custorderser) setcustordert();
		$('#custorderd').dialog('open');
		loadcustorderhlist('1');
	} else alerttext('请选择店铺或者客户');
}
function setcustordert() {
	$('#custorderpp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			loadcustorderhlist(p.toString());
		}
	});
	$('#custordert').datagrid({
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
				var rn = Number(value);
				if (!isNaN(rn)) return rn;
				else return "";
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			width: 130,
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
			field: 'CUSTNAME',
			title: '客户',
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
			field: 'TOTALPEIAMT',
			title: '已配货',
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
			title: '未配货',
			width: 80,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				var totalamt = Number(row.TOTALAMT);
				var totalpeiamt = Number(row.TOTALPEIAMT);
				if (!isNaN(totalamt)) {
					var balamt = totalamt - totalpeiamt;
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
			loadcustordernote();
		},
		pageSize: 20
	}).datagrid("keyCtr", "loadcustordernote()");
}
function loadcustorderhlist(page) {
	alertmsg(6);
	var custid = $('#ucustid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "custorderhnotpei",
			custid: custid,
			rows: pagecount,
			fieldlist: "a.id,a.noteno,a.notedate,a.custid,b.custname,a.totalamt,a.totalcurr,a.totalpeiamt,a.operant",
			sort: "notedate,id",
			order: "asc",
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#custorder_total').html("共有" + data.total + "条记录");
					$('#custorderpp').setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					data.footer = [{
						NOTENO: "合计",
						TOTALAMT: data.totalamt,
						TOTALPEIAMT: data.totalpeiamt,
						TOTALCURR: data.totalcurr
					}];
					$('#custordert').datagrid('loadData', data);
				}
			} catch(e) {
				console.error(e);
			}
		}
	});

}
function loadcustordernote() {
	var row = $('#custordert').datagrid("getSelected");
	if (row) {
		var orderno = row.NOTENO;
		var noteno = $('#unoteno').val();
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
					erpser: "custordertopei",
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
			$('#custorderd').dialog('close');
		}
		var msg = "是否确认载入客户订单号:" + orderno + "！";
		var rows = $('#waret').datagrid('getRows');
		if (rows.length > 0) {
			msg = "发现该客户出库单子不是空单，载入后将清空原有商品明细，" + msg;
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
function openloadnosendkc() {
	$("#loadkcd table input").val("");
	$('#loadkcd').dialog('open');
}
function warepeiloadnosendkc() {
	var noteno = $('#unoteno').val();
	var houseid = Number($('#uhouseid').val());
	var wareid = Number($('#lwareid').val());
	var typeid = $('#ltypeid').val();
	var brandid = $('#lbrandid').val();
	var provid = $('#lprovid').val();
	var areaid = $('#lareaid').val();
	var seasonname = $('#lseasonname').val();
	var prodyear = $('#lprodyear').val();
	var locale = $('#llocale').val();
	typeid = typeid==""?"-1":typeid;
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warepeiloadnosendkc",
			noteno: noteno,
			houseid: houseid,
			wareid: wareid,
			typeid: typeid,
			brandidlist: brandid,
			providlist: provid,
			seasonname: seasonname,
			prodyear: prodyear,
			areaidlist: areaid,
			locale: locale
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		},
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				getnotewarem(noteno, '1');
				$('#loadkcd').dialog('close');
			}
		}
	});
}
</script>
	<!-- 得到销售员列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 有折扣商品明细弹窗 -->
	<jsp:include page="../HelpList/DiscSaleWarem.jsp"></jsp:include>
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
	<!-- 销售方式帮助列表 -->
	<jsp:include page="../HelpList/SaleHelpList.jsp"></jsp:include>
	<!-- 销售部门帮助列表 -->
	<jsp:include page="../HelpList/DPTHelpList.jsp"></jsp:include>
	<!-- 类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
	<!-- 条码增加 -->
<jsp:include page="../HelpList/BarcodeAdd.jsp"></jsp:include>
	<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
	<jsp:include page="../HelpList/ExportHelp.jsp"></jsp:include>
</body>
</html>