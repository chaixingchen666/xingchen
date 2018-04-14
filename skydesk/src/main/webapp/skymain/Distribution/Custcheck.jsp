
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
<title>客户盘点</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
p{
	line-height: 16px;
}
p.nonep{
	color:#aaa;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll" >
<!-- 客户盘点单据列表 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addnoted()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、客户、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20" data-enter="getnotedata()">
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
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="scustid" id="scustid" value=""> 
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
<div class="s-box"><div class="title">客户</div>
<div class="text">
<input class="edithelpinput cust_help" id="scustname" data-value="#scustid" type="text"  placeholder="<输入>" ><span class="s-btn" onclick="selectcust('search')"></span>
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput" id="swareno" type="text" data-value="#swareid"  placeholder="<输入>" ><span class="s-btn" onclick="selectware('search')"></span>
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
<table id="gg" style="overflow: hidden;height:900px"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 添加与修改客户盘点表单 -->
<div id="ud" title="修改客户盘点单" style="width: 100%;height:100%;" class="easyui-dialog" closed="true" data-qickey>
	<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" data-btn="updatebtn" id="pay" value="提交" onclick="updatecustcheckh()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="refresh" value="刷新账面数" onclick="openrefresh()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="loadck" value="载入库存" onclick="openloadck()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="hblpd" value="合并客户临盘单 " onclick="openhblspdd()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="delete" value="删除单据" onclick="delcustcheckh()"></span>
	</span>
	<span id="isupdatedbar">
	<span><input class="btn4" type="button" id="print" value="后台打印"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="selprint" value="选择打印端口" onclick="selprint()"></span>
	<span class="sepreate" id="sep-withdraw"></span>
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="withdraw()"></span>	
	</span>

	<!-- 单据提交框 -->
	<div id="udnote">
	<form id="udnoteform" action="" method="get">
	<input type="hidden" id="uid"> 
	<input type="hidden" id="untid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag"> 
	<input type="hidden" id="ucustid"> 
	<input type="hidden" id="utocustid">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25 wid160" type="text" id="unoteno" readonly>
	</div>
	</div>
<div class="s-box"><div class="title">日期</div>
	<div class="text">
	    <input id="unotedate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:false,hasDownArrow:false,onShowPanel: setTodayDate,onHidePanel: changedate">
	</div>
	</div>
<div class="s-box"><div class="title">客户</div>
	<div class="text"><input class="edithelpinput cust_help" type="text" id="ucustname" data-value="changenotecust" >
	<span onclick="selectcust('update')"></span>
	</div>
	</div>
<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" class="hig25 wid160" id="uhandno" placeholder="<输入>"  onchange="changehandno(this.value)">
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
		<div class="fl"><table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="warembar" onclick="updown()" style="cursor: pointer;">▲&nbsp;&nbsp;商品明细</td><td id="wquickuwaretd"><input type="text" id="wquickuwareno"  class="edithelpinput wid145"  data-end="yes" data-value="quickaddwarem"  placeholder="<选择或输入>" list="wareno_list"><span onclick="if(isAddWarem()){selectware('quickupdatew')}"></span></td></tr>
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
	<input type="hidden" id="windex">
	<table id="waret" class="table1"></table>
	<div id="warenametable" class="line30 mt10">
	<div class="fr mr20" align="right" id="wmtotalnote"></div>
	</div>
	</div>
</div>
<div id="refreshd" title="刷新账面数" style="width: 500px; height: 280px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
<div class="textcenter">
	<ul style="margin: 30px auto;font-size: 16px; width:150px">
	<li class="textleft mt20">
		<label>
		<input type="radio" name="sele" id="sxst1" value="0" checked="checked">实盘数不变
		</label>
	</li>
	<li class="textleft mt20">
		<label>
		<input type="radio" name="sele" id="sxst2" value="1">实盘数=账面数
		</label>
	</li>
	<li class="textleft mt20">
		<label>
		<input type="radio" name="sele" id="sxst3" value="2">实盘数清零
		</label>
	</li>
	</ul>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="refreshcustcheckh();" />
<input type="button" class="btn2" value="取消" onclick="$('#refreshd').dialog('close')" />
</div>
</div>
<div id="loadkcd" title="载入库存" style="width: 500px; height: 420px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
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
  <tr>
    <td class="header" align="right">最低零售价</td>
    <td align="left"><input class="hig25 wid160" type="text" id="lminsale" placeholder="<输入>"></td>
  </tr>
  <tr>
    <td class="header" align="right">最高零售价</td>
    <td align="left"><input class="hig25 wid160" type="text" id="lmaxsale" placeholder="<输入>"></td>
  </tr>
  <tr>
  <td colspan="2">
  <label>
		<input type="checkbox" name="onlyfs" id="onlyfs" value="1">只载入负数库存商品
		</label>
		<label>
		<input type="checkbox" id="loadbj" value="1">清空原数据
		</label>
  </td>
  </tr>
</table>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="if(isAddWarem()){loadkc();}" />
<input type="button" class="btn2" value="取消" onclick="$('#loadkcd').dialog('close')" />
</div>
</div>
<div id="hblspdd" title="合并临时盘点单" style="width: 800px; height: 450px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
<div class="textcenter">
<div  id="hbtoolbars">
<table class="table1" border="0" cellspacing="5" cellpadding="5">
<tr>
<td class="header" align="right">开始日期</td>
<td align="right"><input type="text" name="hbmindate" id="hbmindate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox"></td>
<td class="header" align="right">结束日期</td>
<td align="right"><input type="text" name="hbmaxdate" id="hbmaxdate" placeholder="<输入>"  style="width: 162px;height:29px" class="easyui-datebox"></td>
<td align="right"><label>
	<input type="radio" name="hbsele" id="hbst1" value="1" checked>更新并追加所有商品
	</label>
	<label>
	<input type="radio" name="hbsele" id="hbst1" value="2">仅更新已有的商品
	</label></td>
</tr>
</table>
</div>
<table id="hblspdt" class="easyui-datagrid"></table>
</div>
<div class="dialog-footer">
<div id="hblspdpp" class="tcdPageCode fl"></div> 
<div class="fl" id="hblspdtotal"></div>
<input type="button" class="btn1" value="确定" onclick="hblspdd();" />
<input type="button" class="btn2" value="取消" onclick="$('#hblspdd').dialog('close')" />
</div>
</div>
<script type="text/javascript">
var levelid = getValuebyName("GLEVELID");
var pgid = 'pg1315';
setqxpublic();
// allowchangdate = 0;
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
			var val = Math.ceil(Number(value) / pagecount) - 1;
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
		width: 20,
		hidden: true
	};
	colums[6] = {
		field: 'COLORNAME',
		title: '颜色',
		width: 6,
		halign: 'center',
		align: 'center'
	};
	colums[7] = {
		field: 'ZSNAME',
		title: '  ',
		width: 30,
		fixed: true,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			if (value == "no") return "";
			else return "<p>账</p><p>实</p>";
		}
	}
	for (var i = 1; i <= cols; i++) {
		colums[7 + i] = {
			field: 'CHECKAMT' + i,
			title: '<span id="title' + i + '"><span>',
			width: 35,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				var check = Number(value);
				check = isNaN(check) ? 0 : check;
				var j = this.field.replace("CHECKAMT", "");
				var book = Number(eval('row.BOOKAMT' + j));
				book = isNaN(book) ? 0 : book;
				var nonep1 = book == 0 ? "nonep": "";
				var nonep2 = check == 0 ? "nonep": "";
				if (value == "" && eval('row.BOOKAMT' + j) == "" || row.WARENO == "合计") return "";
				else return '<p class="' + nonep1 + '">' + book + '</p><p class="' + nonep2 + '">' + check + '</p>';
			}
		};
	}
	colums[cols + 8] = {
		field: 'CHECKAMT',
		title: '合计',
		width: 50,
		fixed: true,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			var check = Number(value);
			var book = Number(row.BOOKAMT);
			book = isNaN(book) ? 0 : book;
			var nonep1 = book == 0 ? "nonep": "";
			var nonep2 = check == 0 ? "nonep": "";
			return '<p class="' + nonep1 + '">' + book + '</p><p class="' + nonep2 + '">' + check + '</p>';
		}
	};
	colums[cols + 9] = {
		field: 'AMOUNT',
		title: '盈亏数',
		width: 50,
		fixed: true,
		halign: 'center',
		align: 'center'
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
	return colums;
}
function isAddWarem() {
	var custid = $('#ucustid').val();
	if (custid == '0' || custid == '' || custid == undefined) {
		alerttext('请选择客户');
		$('#wquickuwareid').val("");
		$('#wquickuwareno').val("");
		$('#ucustname').focus();
		return false;
	} else {
		return true;
	}
}
var notedateobj = {
	id: "#uid",
	noteno: "#unoteno",
	notedate: "#unotedate",
	action: "updatecustcheckhbyid"
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
	setaddbackprint("1508");
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
			if (rowData) {
				$('#uid').val(rowData.ID);
				$('#unoteno').val(rowData.NOTENO);
				$('#ucustid').val(rowData.CUSTID);
				$('#ucustname').val(rowData.CUSTNAME);
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
			title: '客户id',
			width: 200,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'CUSTNAME',
			title: '客户',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HANDNO',
			title: '自编号',
			width: 200,
			hidden: true
		},
		{
			field: 'TOTALCURR',
			title: '盈亏金额',
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
			title: '盈亏数',
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
	});
	getnotedata();
	setlspd();
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
	$('#shandno,#scustname,#scustid,#swareno,#swareid,#sremark,#soperant').val('');
	$('#st3').prop('checked', true);
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "custcheckhlist";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.accid,a.noteno,a.notedate,a.custid,b.custname,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag";
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
	var custid = Number($('#scustid').val());
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
		handno: handno,
		custid: custid,
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
var pg = 1;
var sumpg = 1;
var nextpg = true;
//加载商品明细表
function setwarem() {
	$('#waret').datagrid({
		idField: 'custcheckm',
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
			for (var i = 1; i <= cols; i++) {
				$('#title' + i).html(eval('rowData.SIZENAME' + i));
			}
			$('#windex').val(rowIndex);
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
				if (i <= sizelength) $dg.datagrid("showColumn", "CHECKAMT" + i);
				else $dg.datagrid("hideColumn", "CHECKAMT" + i);
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
	$('#addnotenbtn').attr('disabled', 'disabled');
	$('#udnoteform')[0].reset();
	$('#udnoteform input[type=hidden]').val('');
	$('#ucustid').val(getValuebyName('CUSTID'));
	$('#ucustanme').val(getValuebyName('CUSTNAME'));
	$('#waret').datagrid('loadData', {
		total: 0,
		rows: [],
		footer: [{
			"WARENO": "合计",
			"AMOUNT": 0,
			"CURR": 0,
			"ZSNAME": "no"
		}]
	});
	$('#wmtotalnote').html('已显示0条记录/共有0条记录');
	$('#ucustname').removeAttr('readonly');
	$('#uhandno').removeAttr('readonly');
	$('#uselectcustb').removeAttr('disabled');
	$('#isupdatedbar').hide();
	$('#updatingbar').show();
	$('#wquickuwaretd').show();
	$('#wquickuwareno').val('');
	$('#uremark').removeAttr('readonly');
	$('#ucustname').next().show();
	$('#ud #warem-toolbar').show();
	$('#unotedate').datetimebox({
		readonly: false,
		hasDownArrow: true
	});
	addcustcheckh();
	$('#ud').dialog({
		modal: true,
		inline: true,
		title: '增加客户盘点单'
	});
	$('#ustatetag').val('0');
	$('#ud').dialog('open');
	$('#unoteno').focus();
	$('#addnote').removeAttr('disabled');
	$('#ud').data('qickey', "F4:'updatecustcheckh()',Del:'delcustcheckh()'");
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
	$('#waret').datagrid('loadData', {
		total: 0,
		rows: [],
		footer: [{
			"WARENO": "合计",
			"AMOUNT": 0,
			"CURR": 0,
			"ZSNAME": "no"
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
function quickupdatesum() {
	var list = $('#quickutables tr:eq(1) input[type=text]').length; //横向文本框数
	var line = $('#quickutables tr').length - 2; //竖向文本框数
	var quickjsonstr = new Array();
	var totalamount = new Decimal(0);
	var totalcurr = new Decimal(0);
	var count = 0;
	var amount1 = new Array(line);
	var amount2 = new Array(list + 3);
	if (line >= 1 && list >= 1) {
		for (i = 1; i <= line; i++) {
			amount1[i] = new Decimal(0);
			for (j = 1; j <= list + 1; j++) {
				if (j != list + 1) {
					var valstr = $('#u' + i.toString() + "-" + j.toString()).val();
					valstr = valstr== undefined ? "" : valstr;
					var value = Number(valstr);
					if (!isNaN(value)) {
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
					var valstr = $('#u' + j.toString() + "-" + i.toString()).val();
					valstr = valstr== undefined ? "" : valstr;
					var value = Number(valstr);
					if (!isNaN(value)) {
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
		alertmsg(2);
		var custid = Number($('#ucustid').val());
		if (custid == 0) alerttext('请选择客户！');
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getcustcheckmsum",
				noteno: noteno,
				custid: custid,
				wareid: wareid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					var wareno = data.WARENO;
					var warename = data.WARENAME;
					var retailsale = data.RETAILSALE;
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
						alerttext("该商品未选择尺码组，请在商品档案中选择该商品颜色");
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
										input.setAttribute("placeholder", "");
									} else {
										for (r in amountlist) {
											var colorids = amountlist[r].COLORID;
											var sizeids = amountlist[r].SIZEID;
											if (colorids == colorid && sizeid == sizeids) {
												var val = input.value == '' ? 0 : Number(input.value);
												val = val + Number(amountlist[r].CHECKAMT);
												input.value = val == 0 ? "": val;
												amt = Number(amountlist[r].CHECKAMT) + amt;
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
					var amtl = getJsonLength(amountlist);
					var tname = amtl == 0 ? "添加": "修改";
					var title = tname + "商品明细：" + wareno + "，" + warename;
					$('#quickud').dialog({
						title: title,
						width: list * 51 + 360,
						//	 				height: line*35+175,
						modal: true
					});
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
	var custid = $('#ucustid').val();
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
				erpser: "writecustcheckmsum",
				noteno: noteno,
				wareid: wareid,
				custid: custid,
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
		alerttext('请选择一行数据，进行编辑');
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
		var index = $('#windex').val();
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
						erpser: "delcustcheckmsum",
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
		$.messager.show({
			title: '温馨提示',
			msg: '请选择一行数据，进行编辑',
			timeout: 1000,
			style: {
				right: '',
				top: document.body.scrollTop + document.documentElement.scrollTop,
				bottom: ''
			}
		});
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
			erpser: "custcheckmcolorsumlist",
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
			if (valideData(data)) {
				var totals = data.total;
				var totalamt = data.totalamt;
				var totalbookamt = data.totalbookamt;
				var totalcheckamt = data.totalcheckamt;
				var totalcurr = data.totalcurr;
				var retailcurr = data.retailcurr;
				data.footer = [{
					WARENO: "合计",
					AMOUNT: totalamt,
					BOOKAMT: totalbookamt,
					CHECKAMT: totalcheckamt,
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

//添加生成客户盘点单
function addcustcheckh() {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addcustcheckh"
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
				$("#smaxdate").datebox("setValue", top.getservertime());
				$("#qsnotevalue").val("");
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
	delcustcheckh();
}
//删除单据
function delcustcheckh() {
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
					erpser: "delcustcheckhbyid",
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
//修改客户盘点单
function updatecustcheckh() {
	var id = $('#uid').val();
	var custid = $('#ucustid').val();
	var noteno = $('#unoteno').val();
	var handno = $('#uhandno').val();
	var statetag = 1;
	var totalcurr = $('#wtotalcurr').val();
	var totalamt = $('#wtotalamt').val();
	var amt = $('#waret').datagrid('getRows').length;
	var notedatestr = $('#unotedate').datetimebox('getValue');
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
		return;
	}
	if (amt == 0) {
		alert('商品明细不能为空！');
	} else {
		$.messager.confirm(dialog_title, '单据提交后禁止修改及删除，是否继续提交？',
		function(r) {
			if (r) {
				alerttext("正在提交，请等待……", 30 * 60 * 1000);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					timeout: 30 * 60 * 1000,
					data: {
						erpser: "updatecustcheckhbyid",
						noteno: noteno,
						id: id,
						custid: custid,
						handno: handno,
						notedatestr: notedatestr,
						statetag: 1,
						timeout: 30 * 60 * 1000,
						totalcurr: totalcurr,
						totalamt: totalamt
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							$('#gg').datagrid("updateRow", {
								index: dqindex,
								row: {
									STATETAG: 1
								}
							});
							$('#gg').datagrid("refresh");
							$('#ud').dialog('close');
						}
						alerthide();
					}
				});
			}
		});
	}
}
//选择客户
function changecust(id, noteno, custid) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatecustcheckhbyid",
			id: id,
			noteno: noteno,
			custid: custid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#gg').datagrid('updateRow', {
					index: dqindex,
					row: {
						CUSTNAME: $('#ucustname').val(),
						CUSTID: custid
					}
				});
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
			erpser: "updatecustcheckhbyid",
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
			erpser: "updatecustcheckhbyid",
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
						erpser: "custcheckhcancel",
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
function getnotebyid(id, noteno) {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getcustcheckhbyid",
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
						$('#ud').dialog("setTitle", "浏览客户盘点单");
						$('#uhandno').attr('readonly', 'readonly');
						$('#uselectcustb').attr('disabled', 'disabled');
						$('#updatingbar').hide();
						$('#isupdatedbar').show();
						$('#wquickuwaretd').hide();
						$('#uremark').attr('readonly', 'readonly');
						$('#ucustname').attr('readonly', 'readonly');
						$('#ucustname').next().hide();
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
						$('#ud').dialog("setTitle", "修改客户盘点单");
						$('#ucustname').removeAttr('readonly');
						$('#uhandno').removeAttr('readonly');
						$('#uselectcustb').removeAttr('disabled');
						$('#isupdatedbar').hide();
						$('#updatingbar').show();
						$('#wquickuwaretd').show();
						$('#wquickuwareno').val('');
						$('#uremark').removeAttr('readonly');
						$('#ucustname').next().show();
						$('#ud #warem-toolbar').show();
						//$('#waret').datagrid('showColumn','ACTION');
						$('#ud').data('qickey', "F4:'updatecustcheckh()',Del:'delcustcheckh()'");
						$('#unotedate').datetimebox({
							readonly: false,
							hasDownArrow: true
						});
					}
					$('#ud').dialog('open');
					getnotewarem($('#unoteno').val(), "1");
					if ($('#ustatetag').val() == '1') {
						$('#unoteno').focus();
					} else {
						$('#wquickuwareno').focus();
					}
					pg = 1;
					var istoday = row.ISTODAY;
					$('#uistoday').val(istoday);
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					if ((Number(istoday) == 1 || allowchangdate == 1) && allowchedan == '1'&& stg ==1) {
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
function openrefresh() {
	$('#refreshd').dialog('open');
}
function openloadck() {
	$("#loadkcd table input").val("");
	$('#loadkcd').dialog('open');
}
function openhblspdd() {
	var custid = $("#ucustid").val();
	if (custid == "" || custid == "0") {
		alerttext('请选择客户！');
		$('#ucustname').val();
	} else {
		$('#hblspdd').dialog('open');
		$('#hblspdt').datagrid('clearChecked');
		gettempchecklist('1');
	}
}

function refreshcustcheckh() {
	var calc;
	var noteno = $('#unoteno').val();
	if ($('#sxst1').prop('checked')) calc = '0';
	else if ($('#sxst2').prop('checked')) calc = '1';
	else calc = '2';
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "custcheckcalc",
			noteno: noteno,
			calc: calc
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				getnotewarem(noteno, '1');
				$('#refreshd').dialog('close');
			}
		}
	});
}
function loadkc() {
	var noteno = $('#unoteno').val();
	var custid = Number($('#ucustid').val());
	var wareid = Number($('#lwareid').val());
	var typeid = $('#ltypeid').val();
	var brandid = $('#lbrandid').val();
	var areaid = $('#lareaid').val();
	var seasonname = $('#lseasonname').val();
	var prodyear = $('#lprodyear').val();
	var locale = $('#llocale').val();
	var minretailsale = Number($('#lminsale').val());
	var maxretailsale = Number($('#lmaxsale').val());
	typeid = typeid==""?"-1":typeid;
	var onlyfs;
	var loadbj;
	if ($('#onlyfs').prop('checked')) onlyfs = '1';
	else onlyfs = '0';
	if ($('#loadbj').prop('checked')) loadbj = '0';
	else loadbj = '1';
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "custcheckload",
			noteno: noteno,
			custid: custid,
			wareid: wareid,
			typeid: typeid,
			brandidlist: brandid,
			seasonname: seasonname,
			prodyear: prodyear,
			areaidlist: areaid,
			locale: locale,
			minretailsale: minretailsale,
			maxretailsale: maxretailsale,
			change: onlyfs,
			loadbj: loadbj
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
function setlspd() {
	$("#hblspdpp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			gettempchecklist(p.toString());
		}
	});
	$('#hbmindate,#hbmaxdate').datebox({
		onSelect: function(date) {
			gettempchecklist('1');
		}
	});
	var myDate = new Date(top.getservertime());
	$('#hbmindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#hbmaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#hblspdt').datagrid({
		idField: 'NOTENO',
		height: 350,
		fitColumns: true,
		showFooter: true,
		striped: true,
		//隔行变色
		nowrap: false,
		checkOnSelect: false,
		selectOnCheck: false,
		singleSelect: true,
		columns: [[{
			field: 'ID',
			title: 'ID',
			width: 200,
			hidden: true
		},{
			field: 'CHECK',
			checkbox: true
		},{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == "" ? "": index + 1;
			}
		},{
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
		},{
			field: 'NOTEDATE',
			title: '单据日期',
			width: 112,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return row.NOTEDATE == undefined ? '': row.NOTEDATE.substring(0, 16);
			}
		},{
			field: 'CUSTNAME',
			title: '客户',
			width: 100,
			align: 'center',
			halign: 'center'
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
			width: 300,
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
			field: 'STATETAG',
			title: '操作状态',
			width: 200,
			hidden: true
		}]],
		pageSize: 20,
		toolbar: "#hbtoolbars"
	});
}
function gettempchecklist(page) {
	var mindate = $('#hbmindate').datebox('getValue');
	var maxdate = $('#hbmaxdate').datebox('getValue');
	var custid = $('#ucustid').val();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "tempcheckhlist",
			ntid:1,
			statetag: 1,
			fieldlist: "a.id,a.accid,a.noteno,a.notedate,a.custid,b.custname,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",
			mindate: mindate,
			custid: custid,
			rows: pagecount,
			page: page,
			maxdate: maxdate
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		},
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var totals = data.total;
				var totalamt = data.totalamt;
				var totalcurr = data.totalcurr;
				data.footer = [{
					WARENO: "合计",
					TOTALAMT: totalamt,
					TOTALCURR: totalcurr,
				}];
				$("#hblspdpp").setPage({
					pageCount: Math.ceil(Number(totals) / pagecount),
					current: Number(page)
				});
				$('#hblspdt').datagrid('loadData', data);
				$('#hblspdtotal').html('共有' + totals + '条记录');
				$('#hblspdt').datagrid('scrollTo', 0);
			}
		}
	});
}
function hblspdd() {
	var data = $('#hblspdt').datagrid('getChecked');
	var json = new Array();;
	if (data.length > 0) {
		for (var i in data) json[i] = {
			'noteno': data[i].NOTENO
		};
		var noteno = $('#unoteno').val();
		var fs;
		if ($('#hbst1').prop('checked')) fs = '0';
		else fs = '1';
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "tempcheckmerge1",
				noteno: noteno,
				fs: fs,
				tempchecklist: JSON.stringify(json)
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			},
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					getnotewarem(noteno, '1');
					$('#hblspdd').dialog('close');
				}
			}
		});
	} else {
		alerttext('请选择！');
	}
}
</script>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 无折扣商品明细弹窗 -->
<jsp:include page="../HelpList/NoDiscWarem.jsp"></jsp:include>
<!-- 客户帮助列表 -->
<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
<!-- 类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/AreaHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/AreaHelpList.jsp"></jsp:include>
<!-- 条码增加 -->
<jsp:include page="../HelpList/BarcodeAdd.jsp"></jsp:include>
	<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
</body>
</html>