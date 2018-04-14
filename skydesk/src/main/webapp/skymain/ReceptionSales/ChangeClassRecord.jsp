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
<title>查询交班记录表</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
#benqi-l{
	text-align: center;
	width:98%;
	margin-bottom: 8px;
	border-top:1px solid #0095a0;
}
#benqi-l td{
	line-height:20px;
	border-bottom:1px solid #CCCCCC;
}
#beizhu{
	text-align: center;
	width:98%;
	margin-bottom: 8px;
	border-top:1px solid #0095a0;
}
/* .datagrid-header-row{background-color:#0195a1;} */
/* .datagrid-header td.datagrid-header-over { */
/* 	background:#0195a1 !important;color:#323232 !important; */
/* } */
</style>
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src='/skydesk/js/LodopFuncs.js?ver=<%=tools.DataBase.VERSION%>'></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
<input type="button" class="btn2" id="qc" name="qc" value="查询条件▲" onclick="toggle()">
 <span id="serbtn" class="fr">
<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchpage(1);toggle();">
<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空条件" onclick="resetsearch()">
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
</div>
</div>
<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchpage(1);toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input id="smindate" editable="true" type="text" style="width:164px;height:25px" class="easyui-datebox"></input>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" editable="true" type="text" style="width:164px;height:25px" class="easyui-datebox"></input>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text">
<input type="text" class="edithelpinput house_help" data-value="#shouseid" id="shousename" placeholder="<选择或输入>">
<span onclick="selecthouse('search')"></span> <input type="hidden" id="shouseid" value="">
</div>
</div>
</div>
</div>
</div>
	<table id="gg" style="overflow: hidden;"></table>
	
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code"> </div>
	</div>
<!-- 交班记录弹出窗 -->
<div id="changeclass" title="交班记录" style="width: 100%; height: 100%;text-align: center;" class="easyui-dialog" closed="true">
<table id="start" style="align: center;" cellspacing="10">
	<tr>
		<td class="header">开始日期</td>
		<td><input id="mindates" type="text"  style="width:164px;height:25px" readonly></td>
		<td class="header">当前日期</td>
		<td><input id="maxdates" type="text" style="width:164px;height:25px" readonly></td>
		<td class="header">店铺<td>
		<td width="200">
		<input  type="text" name="shousenames" id="shousenames" style="width:135px;height:24px;" readonly></td>
		<td><span id="lastop" style="font-size:16px;color:#0195a1;"></span>&nbsp;</td>
		<td>
		<input type="button" class="btn3" value="导出" onclick="exp2()">
<!-- 		<input type="button" class="btn3" value="打印" onclick="printdo()"> -->
<!-- 		<input type="button" class="btn3 hide" value="打印预览" onclick="demoPrint(true)"> -->
		<input class="btn1" type="button" onclick="selprint()" value="选择打印端口" />
		<input class="btn1" type="button" id="print" value="后台打印" />
		</td>
	</tr>
</table>
<div id="main">
<div>
 <table id="xs"></table>
</div>
<div>
 <table id="fy"></table>
</div>
<div>
 <table id="sz"></table>
</div>
<div style="height:25px;"><div style="font-size:16px;color:#0195a1;text-align:left;padding-left:10px;">现金</div></div>
<div style="height:80px;margin-top:5px;">
	 <table style="width:98%;" id="benqi-l" cellspacing="10">
 	<tr>
 		<td ><span id="Currqc">0</span><br />上班结存</td>
 		<td ><span id="CURRYE">0</span><br />本班余额</td>
 		<td ><span id="CURRYK">0</span><br />损益金额</td>
 	</tr>
 	<tr>
 		<td><span id="Currsjye">0</span><br />实际金额</td>
 		<td><span id="Curryh">0</span><br />银行存款</td>
 		<td><span id="CURRJC">0</span><br />本班结存</td>
 	</tr>
 </table>
</div>

<div style="height:25px;margin-top:40px;"><div style="font-size:16px;color:#0195a1;text-align:left;padding-left:10px;">备注</div></div>
<div style="height:40px;">
	 <table style="width:98%;border-top:1px solid #0095a0;" cellspacing="10" id="biezhu">
 	<tr>
 		<td ><input type="hidden" id="CURRXJ" /></td>
		<td> <textarea cols="100" id="remark" rows="3" maxlength="50" readonly></textarea></td>
 	</tr>
 </table>
</div>
</div>
</div>
<!-- 页面加载弹出窗撤班 -->
	<div id="printdo" title="撤班"
		style="width: 260px; height: 130px;  text-align: center;"
		class="easyui-dialog" closed="true">
		<table cellspacing="10"  class="table1">
			<tr>
				<td align="center" colspan="2"><input type="checkbox" id="xshz">打印销售汇总详细</td>
			</tr>
			<tr>
				<td><input class="btn1" value="确定"
					type="button" onclick="demoPrint();" />
					<input class="btn1" type="button"
					value="取消" onclick="$('#printdo').dialog('close')" /></td>
			</tr>
		</table>
	</div>
<div id="printgg" style="width:100%;overflow:auto;display:none;"></div>

<script type="text/javascript"> 
var rownumbers = 1;
var pgid = "pg1208";
var epno = getValuebyName("EPNO");
setqxpublic();
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

//设置弹出框的属性
$(function() {
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	setaddbackprint("1208",{
		id: 'classids',
		houseid: 'houseids',
		noteno: "null"
	});
	$("#pp").createPage({
		pageCount: 1,
		//总页码
		current: 1,
		//当前页 
		backFn: function(p) { //http:
			searchpage(p);
		}
	});
	$('#gg').datagrid({
		idField: 'CODENAME',
		width: '100%',
		height: $('body').height() - 50,
		toolbar: "#toolbars",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
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
			field: 'CLASSID',
			title: '班次',
			width: 130,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'BEGINDATE',
			title: '开班时间',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ENDDATE',
			title: '交班时间',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURRQC',
			title: '上班结存',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRXJ',
			title: '现金收支',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRFY',
			title: '费用开支',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRYE',
			title: '本班余额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRXS',
			title: '销售金额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRYH',
			title: '银行存款',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRYK',
			title: '损益金额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURRJC',
			title: '本班结存',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'LASTOP',
			title: '收银员',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		}]],
		onDblClickRow: function(rowIndex, rowData) {
			classids = Number(rowData.CLASSID);
			housenames = rowData.HOUSENAME;
			houseids = Number(rowData.HOUSEID);
			$("#mindates").val(rowData.BEGINDATE);
			$("#maxdates").val(rowData.ENDDATE);
			$("#shousenames").val(housenames);
			$("#lastop").html(rowData.LASTOP);
			gethousework(classids, houseids, housenames);
		},
		pageSize: 20
	});
	datagridtab();
	alerthide();
});
//清空表单搜索条件 
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shouseid').val('');
	$('#shousename').val('');
}

//高级搜索
var searchb = true;
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		$('#serbtn').hide();
		searchb = false;
	} else {
		$('#highsearch').val('高级搜索▲');
		$('#serbtn').show();
		searchb = true;
	}
	$('.searchbar').slideToggle('fast');
}

var htmlStr = "";
var XSLIST = '';
var PAYCURRLIST = '';
var FYLIST = '';
var XSLIST1 = '';
var PAYCURRLIST1 = '';
var FYLIST1 = '';
var data;
var housenames; //店铺
var houseids; //店铺ID
var classids //班次
//查询交班记录 
var currentdata = {};
function searchpage(page) {
	var mindate = $('#smindate').datebox('getValue'); //开始时间
	var maxdate = $('#smaxdate').datebox('getValue'); //结束时间 
	var houseid = Number($("#shouseid").val()); //店铺
	alertmsg(6);
	currentdata = {
			erpser: "houseworklist",
			mindate: mindate,
			maxdate: maxdate,
			houseid: houseid,
			rows: pagecount,
			page: page
		};
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
					data.footer = [{
						ROWNUMBER: "合计",
						BEGINDATE: "存款笔数："+data.numyh+"笔",
						ENDDATE: "损益笔数："+data.numyk+"笔",
						CURRXS: data.currxs,
						CURRYH: data.curryh,
						CURRYK: data.curryk
					}];
					$("#pp").setPage({
						pageCount: Math.ceil(Number(data.total) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#pp_total').html(data.total);
					$('#gg').datagrid('scrollTo', 0);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var printdata = {};
//店铺收银交班记录单条
function gethousework(classids, houseids, housenames) {
	$('#changeclass').dialog('open');
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "houseworktotal",
			classid: classids,
			lastop: $("#lastop").html(),
			houseid: houseids
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					printdata = data;
					var XSLIST = {
						'total': 1,
						'rows': data.XSLIST
					};
					var PAYCURRLIST = {
						'total': 1,
						'rows': data.PAYCURRLIST
					};
					var FYLIST = {
						'total': 1,
						'rows': data.FYLIST
					};
					$('#mindate').val(data.MINDATE);
					$('#maxdate').val(data.MAXDATE);
					$('#Currqc').html(Number(data.CURRQC).toFixed(2));
					$('#CURRYE').html(Number(data.CURRYE).toFixed(2));
					$('#CURRYK').html(Number(data.CURRYK).toFixed(2));
					$('#Currsjye').val(Number(data.CURRSJYE).toFixed(2));
					$('#Curryh').val(Number(data.CURRYH).toFixed(2));
					$('#CURRJC').html(Number(data.CURRJC).toFixed(2));
					$('#CURRXJ').val(Number(data.CURRXJ).toFixed(2));
					$('#xs').datagrid('loadData', XSLIST);
					$('#fy').datagrid('loadData', FYLIST);
					$('#sz').datagrid('loadData', PAYCURRLIST);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//是否打印详细
function printdo() {
	$('#printdo').dialog('open');
}
//拼接table
function printclass() {
	var data = printdata;
	htmlStr = "<table>";
	htmlStr += '<caption><b><font face="黑体" size="5">交班记录<br/>&nbsp;</font></b></caption>';
	htmlStr += '<tr><td>开始日期&nbsp;&nbsp;' + data.MINDATE + '</td><td>当前日期&nbsp;&nbsp;' + data.MAXDATE + '</td><td>店铺&nbsp;&nbsp;' + housenames + '<td>销售人&nbsp;&nbsp;' + data.LASTOP + '</td></tr>';
	if ($("#xshz").prop("checked") == true) {
		htmlStr += '<tr class="t2"><td colspan="4" align="left">销售汇总</tr>';
		htmlStr += '<tr><td>货号</td><td>商品</td><td>数量<td>金额</td></tr>';
		for (var i = 0; i < XSLIST.length; i++) {
			htmlStr += '<tr><td>' + XSLIST[i].WARENO + '</td><td>' + XSLIST[i].WARENAME + '</td><td>' + XSLIST[i].AMOUNT + '<td>' + XSLIST[i].CURR + '</td></tr>';
		}
		htmlStr += '<tr><td>' + XSLIST1[0].WARENO + '</td><td>&nbsp;</td><td>' + XSLIST1[0].AMOUNT + '<td>' + XSLIST1[0].CURR + '</td></tr>';
	} else {
		htmlStr += '<tr class="t2"><td colspan="4" align="left">销售汇总</tr>';
		htmlStr += '<tr><td>总数量</td><td>' + XSLIST1[0].AMOUNT + '<td>总金额</td><td>' + XSLIST1[0].CURR + '</td></tr>';
	}
	htmlStr += '<tr class="t2"><td colspan="4" align="left">费用汇总</tr>';
	htmlStr += '<tr><td>项目名称</td><td>支出</td><td>收入<td>金额</td></tr>';
	for (var i = 0; i < FYLIST.length; i++) {
		htmlStr += '<tr><td>' + FYLIST[i].CGNAME + '</td><td>' + FYLIST[i].CURR0 + '</td><td>' + FYLIST[i].CURR1 + '<td>' + FYLIST[i].CURR2 + '</td></tr>';
	}
	htmlStr += '<tr><td>' + FYLIST1[0].CGNAME + '</td><td>' + FYLIST1[0].CURR0 + '</td><td>' + FYLIST1[0].CURR1 + '<td>' + FYLIST1[0].CURR2 + '</td></tr>';
	htmlStr += '<tr class="t2"><td colspan="4" align="left">收支汇总</tr>';
	htmlStr += '<tr><td>结算方式</td><td>销售收入</td><td>费用支出<td>小计</td></tr>';
	for (var i = 0; i < PAYCURRLIST.length; i++) {
		htmlStr += '<tr><td>' + PAYCURRLIST[i].PAYNAME + '</td><td>' + PAYCURRLIST[i].PAYCURR0 + '</td><td>' + PAYCURRLIST[i].PAYCURR1 + '<td>' + PAYCURRLIST[i].PAYCURR2 + '</td></tr>';
	}
	htmlStr += '<tr><td>' + PAYCURRLIST1[0].PAYNAME + '</td><td>' + PAYCURRLIST1[0].PAYCURR0 + '</td><td>' + PAYCURRLIST1[0].PAYCURR1 + '<td>' + PAYCURRLIST1[0].PAYCURR2 + '</td></tr>';
	htmlStr += '<tr class="t2"><td colspan="4" align="left">现金</tr>';
	htmlStr += '<tr><td>' + Number(data.CURRQC).toFixed(2) + '<br>上班结存</td><td>' + Number(data.CURRYE).toFixed(2) + '<br>本班余额</td><td colspan="2">' + Number(data.CURRYK).toFixed(2) + '<br>损益金额</td></tr>';
	htmlStr += '<tr><td>' + Number(data.CURRSJYE).toFixed(2) + '<br>实际金额</td><td>' + Number(data.CURRYH).toFixed(2) + '<br>银行存款</td><td colspan="2">' + Number(data.CURRJC).toFixed(2) + '<br>本班结存</td></tr>';
	htmlStr += '<tr class="t2"><td colspan="4" align="left">备注</tr>';
	htmlStr += '<tr><td colspan="4" align="left">&nbsp;' + data.REMARK + '</tr>';
	htmlStr += '</table>';
	$('#printgg').html(htmlStr);

}
function demoPrint(toPrview) {
	$('#printdo').dialog('close');
	printclass();
	LODOP = getLodop();
	var style1 = "table{width:100%;border-style: solid;border-color: #000000;  border-collapse:collapse;border-width:0px 0px 0px 0px;}" + "table td{border: solid #000000; border-width:1px 1px 1px 1px;width:auto;}" + "table .t2{border-style: solid;border-color: #000000;font-weight:bold;border-width:1px 0px 1px 0px; height:50px;}";
	// 		+"table .t1{font-weight: bold;font-size:18px;border-width:0px 0px 0px 0px;}"
	// 		+"table .t1 td{border-width:0px 0px 0px 0px; height:50px;}"
	// 		+"table .a3{border: solid #000000;border-width:2px 1px 1px 1px;}"
	// 		+"table .a3 td{border: solid #000000;border-width:3px 1px 1px 1px;}"
	// 		+"table .t3 td{border-width:0px 0px 0px 0px;}"
	// 		+".a1 td{border: solid #000000;border-width:1px 1px 3px 1px;}"
	// 		+".t3{text-align:right;}";
	var strBodyStyle = "<style>" + style1 + "</style>";
	var strFormHtml = strBodyStyle + "<body>" + document.getElementById("printgg").innerHTML + "</body>";
	LODOP.SET_PRINTER_INDEXA("0");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.PRINT_INIT("交班记录");
	LODOP.SET_PRINT_STYLE("FontSize", 18);
	LODOP.SET_PRINT_STYLE("Bold", 1);
	LODOP.ADD_PRINT_TEXT(50, 31, 750, 1000, "交班记录");
	LODOP.ADD_PRINT_HTM(50, 11, 750, 1000, strFormHtml);
	if (toPrview) LODOP.PREVIEW();
	else LODOP.PRINT();
};
//设置表格列名
function datagridtab() {
	$('#xs').datagrid({
		idField: 'WAREID',
		title: "销售汇总",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		scrollbarSize: 0,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value) return value;
				return Number(index+1);
			}
		},{
			field: 'WARENO',
			title: '货号',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WARENAME',
			title: '商品',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'CURR',
			title: '金额',
			width: 150,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		 onLoadSuccess: function(data){  
			 var totalamt = new Decimal(0);
			 var totalcurr = new Decimal(0);
			 if(data.total>0){
				 var rows = data.rows;
				 for(var i=0;i<rows.length;i++){
					 var row = rows[i];
					 totalamt = totalamt.add(row.AMOUNT);
					 totalcurr = totalcurr.add(row.CURR);
				 }
			 }
			 var footer = [{ROWNUMBER: "合计",AMOUNT:totalamt.valueOf(),CURR:totalcurr.valueOf()}];
			 $(this).datagrid("reloadFooter",footer);
		 }
	});
	$('#fy').datagrid({
		idField: 'CGID',
		title: "费用汇总",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		scrollbarSize: 0,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value) return value;
				return Number(index+1);
			}
		},{
			field: 'CGNAME',
			title: '项目名称',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURR0',
			title: '支出',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURR1',
			title: '收入',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'CURR2',
			title: '金额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		 onLoadSuccess: function(data){  
			 var totalcurr0 = new Decimal(0);
			 var totalcurr1 = new Decimal(0);
			 var totalcurr2 = new Decimal(0);
			 if(data.total>0){
				 var rows = data.rows;
				 for(var i=0;i<rows.length;i++){
					 var row = rows[i];
					 totalcurr0 = totalcurr0.add(row.CURR0);
					 totalcurr1 = totalcurr1.add(row.CURR1);
					 totalcurr2 = totalcurr2.add(row.CURR2);
				 }
			 }
			 var footer = [{ROWNUMBER: "合计",CURR0:totalcurr0.valueOf(),CURR1:totalcurr1.valueOf(),CURR2:totalcurr2.valueOf()}];
			 $(this).datagrid("reloadFooter",footer);
		 }
	});
	$('#sz').datagrid({
		idField: 'PAYID',
		title: "收支汇总",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		scrollbarSize: 0,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value) return value;
				return Number(index+1);
			}
		},{
			field: 'PAYNAME',
			title: '结算方式',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'PAYCURR0',
			title: '销售收入',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYCURR1',
			title: '费用支出',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYCURR2',
			title: '小计',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		 onLoadSuccess: function(data){  
			 var totalcurr0 = new Decimal(0);
			 var totalcurr1 = new Decimal(0);
			 var totalcurr2 = new Decimal(0);
			 if(data.total>0){
				 var rows = data.rows;
				 for(var i=0;i<rows.length;i++){
					 var row = rows[i];
					 totalcurr0 = totalcurr0.add(row.PAYCURR0);
					 totalcurr1 = totalcurr1.add(row.PAYCURR1);
					 totalcurr2 = totalcurr2.add(row.PAYCURR2);
				 }
			 }
			 var footer = [{ROWNUMBER: "合计",PAYCURR0:totalcurr0.valueOf(),PAYCURR1:totalcurr1.valueOf(),PAYCURR2:totalcurr2.valueOf()}];
			 $(this).datagrid("reloadFooter",footer);
		 }
	});
}
//导出收银交班记录(后台已改成新街口)
function exp2() {
	$.messager.confirm(dialog_title, '确认导出:详细交班记录.xls',
	function(r) {
		if (r) {
			var url = 'ChangeClassServlet?changeclassser=houseworktotalexp' + '&housename=' + housenames + '&classid=' + classids + '&houseid=' + houseids;
			top.window.location.href = encodeURI(url);
			var num = Number(XSLIST.length + FYLIST.length + PAYCURRLIST.length);
			alert('正在努力导出，请不要重复操作！关闭此弹窗，请等待' + Math.ceil(num / 10) + '秒左右，等待下载框弹出！');
		}
	});
}
</script>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
<!-- 端口帮助列表 -->
<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
</body>
</html>