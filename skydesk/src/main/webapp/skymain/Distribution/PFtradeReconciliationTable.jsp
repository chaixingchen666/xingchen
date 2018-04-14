<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>批发商品对账表</title>
<link rel="stylesheet" href="/skydesk/css/icon.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css" type="text/css" />
<style type="text/css">
a {
	text-decoration: none;
	color: black;
}

body {
	font-family: 微软雅黑;
	color: black;
	margin: auto;
}

select {
	border: 1px solid #d7d7d7;
}

.datagrid-toolbar table {
	float: left;
}

input {
	border: #95b8e7 solid 1px;
}

button {
	border: #95b8e7 solid 1px;
}

.pagination-info {
	padding-right: 50px;
}

div#quicktable table {
	margin: 10px;
}

div#quicktable input {
	width: 40px;
}

#waretypepp {
	margin-top: -10px;
}

#yesno {
	margin-top: 5px;
}

#brandpp {
	margin-top: -15px;
}
#gg table{
	background-color:#FFFFFF;
	border:1px solid #999999;
	border-collapse:collapse;
	border:solid #999999; 
	border-width:0px 0px 0px 0px;
	width:70%;
	margin:auto;
}
#gg table td{border:solid #999999; border-width:0px 1px 1px 1px;width:150px;}
#gg table .t2 td{border-width:0px 0px 1px 0px;}
#gg table .t2{font-weight:bold;border-width:0px 0px 0px 0px;}
#gg table .t1{font-weight: bold;font-size:18px;border-width:0px 0px 0px 0px;}
#gg table .t1 td{border-width:0px 0px 0px 0px; height:50px;align:center;width:100%;}
.a1{background-color:#EEEEEE;}
.a3{background-color:#44b5af;}
.a2{color:#44b5af;}

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
	<!-- 工具栏 -->
<div class="toolbar" id="toolbars">
<div class="toolbar_box1">
<div class="fl">
		<input type="button" id="highsearch" class="btn2" value="条件搜索" onclick="toggle()">
		<input id="csearch" class="btn1" type="button"  onclick="searchdata();toggle();" value="查找" /> 
		<input id="qclear" class="btn1" type="button" value="清除内容" onclick="clearAll()" />
</div>
<div class="fr">
			<input type="button" class="btn3" value="打印预览" onclick="demoPrint(true)">
			<input type="button" class="btn3" value="打印" onclick="demoPrint()">
			</div>
			</div>
	<!-- 隐藏查询条件 -->
<div id="hsearch" class="searchbar" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input id="smindate" type="text" editable="true" class="easyui-datebox" style="width: 144px; height: 25px"></input>
</div>
</div>

<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" type="text" editable="true" class="easyui-datebox" style="width: 144px; height: 25px"></input>
</div>
</div>
<div class="s-box"><div class="title">汇总方式</div>
<div class="text"><select id="shzfs" class="sele1">
						<option value="0" selected="selected">商品</option>
						<option value="1">商品，颜色</option>
						<option value="2">商品，颜色，尺码</option>
				</select>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input type="text" class="edithelpinput house_help" name="shousename" id="shousename" data-value="#shouseid" placeholder="<选择或输入>" >
<span onclick="selectdx_house('search')" ></span>
<input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input type="text" class="edithelpinput cust_help" name="scustname" id="scustname" data-value="#scustid" placeholder="<选择或输入>" >
<span onclick="selectdx_cust('search')" ></span>
<input type="hidden" id="scustid" value="">
</div>
</div>
<div class="s-box"><div class="title">销售人</div>
<div class="text"><input type="text" class="edithelpinput saleman_help" id="sepname"  data-value="#sepid" placeholder="<选择或输入>" >
					<span onclick="selectemploye('search')"></span> 
					<input type="hidden" id="sepid" value="">
</div>
</div>
</div>
	</div>
	</div>
	<div id="printgg" style="width:100%;overflow:auto;display:none;"></div>
	<div id="gg" style="width:100%;overflow:auto;align:center;"></div>
	<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<script type="text/javascript">
var searchb = true;
var dheight = 85;
var rownumbers = 1;
var datenow; //打印当前日期 
var epno = getValuebyName("EPNO");
setqxpublic();
//设置初始属性 
$(function() {
	var hei = $("body").height() - 150;
	$('#gg').css("height", hei);
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var hour = myDate.getHours();
	var min = myDate.getMinutes();
	var sec = myDate.getSeconds();
	if (min == 0) {
		min = '00';
	}
	datenow = year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + sec;
	getaccregbyid();
	alerthide();
});
//清空表单搜索条件 
function clearAll() {
	var myDate = new Date(top.getservertime());
	$('#shzfs').val('0');
	$('#scustname').val('');
	$('#scustid').val('');
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#sepname').val('');
	$('#sepid').val('');
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
};

function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		dheight = 85;
		$('#highsearch').val('条件搜索');
		$("#csearch").hide();
		$("#qclear").hide();
		var hei = $("body").height() - 85;
		$('#gg').css("height", hei);
	} else {
		searchb = true;
		dheight = 130;
		$('#highsearch').val('条件搜索▲');
		$("#csearch").show();
		$("#qclear").show();
		var hei = $("body").height() - 150;
		$('#gg').css("height", hei);

	}

}
var htmlStr = '<table>';
var htmlStrp;
var LODOP;
var hide;
var kehu;
var totalsum;
var accobj = {};
//统计批发商品对账方法
function searchdata() {
	hide = $('#shzfs').val();
	var mindate = $("#smindate").datebox('getValue'); //开始日期
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期
	var epid = $("#sepid").val(); //销售人
	var houseid = $("#shouseid").val(); //店铺
	var custid = $("#scustid").val(); //客户
	var hzfs = $("#shzfs").val(); //汇总方式
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名  
		async: false,
		data: {
			erpser: "totalwareoutcheck",
			mindate: mindate,
			maxdate: maxdate,
			custidlist: custid,
			houseidlist: houseid,
			epid: Number(epid),
			hzfs: hzfs
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					datas = {total:0,rows: []};
					searchpage(1);
					setmxtable();
				}
			} catch(e) {
				// TODO: handle exception
				top.wrtiejsErrorLog(e.message);
			}
		}
	});
}
var setmxtable = function(){
	var data = datas;
	$('#gg').html("");
	if(data.total==0){
		alerttext("不存在批发商品明细记录！");
		return;
	}
	var mindate = $("#smindate").datebox('getValue'); //开始日期
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期
	htmlStr = '<table  class="list">';
	var totals = data.total;
	totalsum = totals;
	var dataArr = data.rows; //htmlStr 
	var html1;
	var html2;
	var html5;
	var html6;
	kehu = $("#scustname").val();
	if (kehu == null || kehu == '') {
		kehu = '所有';
	}
	if (hide == 2) {
		htmlStr += '<tr class="t1"><td colspan="8" align="center">' + acctitle + '批发商品对账表</td><td></td></tr>';
		htmlStr += '<tr class="t2"><td colspan="3" align="left">单位联系人：' + linkman + ' 电话：' + tel + '</td><td colspan="5" align="right">单位地址：' + address + '</td></tr>';
		htmlStr += '<tr class="t2"><td colspan="3" align="left">客户：' + kehu + '</td><td colspan="5" align="right">日期范围：' + mindate + '到' + maxdate + '</td></tr>';
	} else if (hide == 1) {
		htmlStr += '<tr class="t1"><td colspan="7" align="center">' + acctitle + '批发商品对账表</td></tr>';
		htmlStr += '<tr class="t2"><td colspan="3" align="left">单位联系人：' + linkman + ' 电话：' + tel + '</td><td colspan="4" align="right">单位地址：' + address + '</td></tr>';
		htmlStr += '<tr class="t2"><td colspan="3" align="left">客户：' + kehu + '</td><td colspan="4" align="right">日期范围：' + mindate + '到' + maxdate + '</td></tr>';
	} else {
		htmlStr += '<tr class="t1"><td colspan="6" align="center">' + acctitle + '批发商品对账表</td></tr>';
		htmlStr += '<tr class="t2"><td colspan="3" align="left">单位联系人：' + linkman + ' 电话：' + tel + '</td><td colspan="3" align="right">单位地址：' + address + '</td></tr>';
		htmlStr += '<tr class="t2"><td align="left" colspan="3" >客户：' + kehu + '</td><td colspan="3" align="right">日期范围：' + mindate + '到' + maxdate + '</td></tr>';
	}
	for (var i = 0; i < dataArr.length; i++) {
		if (hide == 2) {
			if (i == 0) {
				html1 = '<tr class="a3"><td colspan="6">' + dataArr[i].DATESTR + '</td><td>上日欠款</td><td>' + dataArr[i].FIRCURR + '</td></tr>';
				htmlStr += html1;
				html2 = '<tr class="a2"><td align="center">单据号</td><td align="center">货号</td><td align="center">商品</td><td align="center">颜色</td><td align="center">尺码</td><td align="center">数量</td><td align="center">单价</td><td align="center">金额</td></tr>';
				htmlStr += html2;
				html5 = '<tr><td>' + dataArr[i].NOTENO + '</td><td align="center">' + dataArr[i].WARENO + '</td><td>' + dataArr[i].WARENAME + '</td><td>' + dataArr[i].COLORNAME + '</td><td>' + dataArr[i].SIZENAME + '</td><td align="right">' + Number(dataArr[i].AMOUNT) + '</td><td align="right">' + dataArr[i].PRICE + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
				htmlStr += html5;
			} else {
				html1 = '<tr class="a3"><td colspan="6">' + dataArr[i].DATESTR + '</td><td>上日欠款</td><td>' + dataArr[i].FIRCURR + '</td></tr>';
				html2 = '<tr class="a2"><td align="center">单据号</td><td align="center">货号</td><td align="center">商品</td><td align="center">颜色</td><td align="center">尺码</td><td align="center">数量</td><td align="center">单价</td><td align="center">金额</td></tr>';
				html5 = '<tr><td>' + dataArr[i].NOTENO + '</td><td align="center">' + dataArr[i].WARENO + '</td><td>' + dataArr[i].WARENAME + '</td><td>' + dataArr[i].COLORNAME + '</td><td>' + dataArr[i].SIZENAME + '</td><td align="right">' + Number(dataArr[i].AMOUNT) + '</td><td align="right">' + dataArr[i].PRICE + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
				if (dataArr[i].DATESTR == dataArr[i - 1].DATESTR) {
					htmlStr += html5;
					if (i == (dataArr.length - 1)) {
						html6 = '<tr class="a1"><td colspan="3">应收金额</td><td colspan="2" align="right">' + dataArr[i].SUMCURR + '</td><td colspan="2">折让金额</td><td align="right">' + dataArr[i].CUTCURR + '</td></tr><tr class="a1"><td colspan="3">已收金额</td><td colspan="2" align="right">' + dataArr[i].PAYCURR + '</td><td colspan="2">本日欠款</td><td align="right">' + dataArr[i - 1].BALCURR + '</td></tr>';
						htmlStr += html6;
					}
				} else {
					if (i == (dataArr.length - 1)) {
						htmlStr += html1;
						htmlStr += html2;
						html6 = '<tr class="a1"><td colspan="3">应收金额</td><td colspan="2" align="right">' + dataArr[i].SUMCURR + '</td><td colspan="2">折让金额</td><td align="right">' + dataArr[i].CUTCURR + '</td></tr><tr class="a1"><td colspan="3">已收金额</td><td colspan="2" align="right">' + dataArr[i].PAYCURR + '</td><td colspan="2">本日欠款</td><td align="right">' + dataArr[i - 1].BALCURR + '</td></tr>';
						htmlStr += html5;
						htmlStr += html6;
					} else {
						html6 = '<tr class="a1"><td colspan="3">应收金额</td><td colspan="2" align="right">' + dataArr[i - 1].SUMCURR + '</td><td colspan="2">折让金额</td><td align="right">' + dataArr[i - 1].CUTCURR + '</td></tr><tr class="a1"><td colspan="3">已收金额</td><td colspan="2" align="right">' + dataArr[i - 1].PAYCURR + '</td><td colspan="2">本日欠款</td><td align="right">' + dataArr[i - 1].BALCURR + '</td></tr>';
						htmlStr += html6;
						htmlStr += html1;
						htmlStr += html2;
						htmlStr += html5;
					}

				}
			}
		} else if (hide == 1) {
			if (i == 0) {
				html1 = '<tr class="a3"><td colspan="5">' + dataArr[i].DATESTR + '</td><td>上日欠款</td><td>' + dataArr[i].FIRCURR + '</td></tr>';
				htmlStr += html1;
				html2 = '<tr class="a2"><td align="center">单据号</td><td align="center">货号</td><td align="center">商品</td><td align="center">颜色</td><td align="center">数量</td><td align="center">单价</td><td align="center">金额</td></tr>';
				htmlStr += html2;
				html5 = '<tr><td>' + dataArr[i].NOTENO + '</td><td align="center">' + dataArr[i].WARENO + '</td><td>' + dataArr[i].WARENAME + '</td><td>' + dataArr[i].COLORNAME + '</td><td align="right">' + Number(dataArr[i].AMOUNT) + '</td><td align="right">' + dataArr[i].PRICE + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
				htmlStr += html5;
			} else {
				html1 = '<tr class="a3"><td colspan="5">' + dataArr[i].DATESTR + '</td><td>上日欠款</td><td>' + dataArr[i].FIRCURR + '</td></tr>';
				html2 = '<tr class="a2"><td align="center">单据号</td><td align="center">货号</td><td align="center">商品</td><td align="center">颜色</td><td align="center">数量</td><td align="center">单价</td><td align="center">金额</td></tr>';
				html5 = '<tr><td>' + dataArr[i].NOTENO + '</td><td align="center">' + dataArr[i].WARENO + '</td><td>' + dataArr[i].WARENAME + '</td><td>' + dataArr[i].COLORNAME + '</td><td align="right">' + Number(dataArr[i].AMOUNT) + '</td><td align="right">' + dataArr[i].PRICE + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
				if (dataArr[i].DATESTR == dataArr[i - 1].DATESTR) {
					htmlStr += html5;
					if (i == (dataArr.length - 1)) {
						html6 = '<tr class="a1"><td colspan="2">应收金额</td><td align="right" colspan="2">' + dataArr[i].SUMCURR + '</td><td colspan="2">折让金额</td><td align="right">' + dataArr[i].CUTCURR + '</td></tr><tr class="a1"><td colspan="2">已收金额</td><td align="right" colspan="2>' + dataArr[i].PAYCURR + '</td><td colspan="2">本日欠款</td><td>' + dataArr[i].BALCURR + '</td></tr>';
						htmlStr += html6;
					}
				} else {
					if (i == (dataArr.length - 1)) {
						htmlStr += html1;
						htmlStr += html2;
						html6 = '<tr class="a1"><td colspan="2">应收金额</td><td align="right" colspan="2">' + dataArr[i].SUMCURR + '</td><td colspan="2">折让金额</td><td align="right">' + dataArr[i].CUTCURR + '</td></tr><tr class="a1"><td colspan="2">已收金额</td><td  align="right" colspan="2>' + dataArr[i].PAYCURR + '</td><td colspan="2">本日欠款</td><td>' + dataArr[i].BALCURR + '</td></tr>';
						htmlStr += html5;
						htmlStr += html6;
					} else {
						html6 = '<tr class="a1"><td colspan="2">应收金额</td><td align="right" colspan="2">' + dataArr[i - 1].SUMCURR + '</td><td colspan="2">折让金额</td><td align="right">' + dataArr[i - 1].CUTCURR + '</td></tr><tr class="a1"><td colspan="2">已收金额</td><td align="right" colspan="2>' + dataArr[i - 1].PAYCURR + '</td><td colspan="2">本日欠款</td><td>' + dataArr[i - 1].BALCURR + '</td></tr>';
						htmlStr += html6;
						htmlStr += html1;
						htmlStr += html2;
						htmlStr += html5;
					}

				}
			}
		} else {
			if (i == 0) {
				html1 = '<tr class="a3"><td colspan="4">' + dataArr[i].DATESTR + '</td><td>上日欠款</td><td>' + dataArr[i].FIRCURR + '</td></tr>';
				htmlStr += html1;
				html2 = '<tr class="a2"><td align="center">单据号</td><td align="center">货号</td><td align="center">商品</td><td align="center">数量</td><td align="center">单价</td><td align="center">金额</td></tr>';
				htmlStr += html2;
				html5 = '<tr><td>' + dataArr[i].NOTENO + '</td><td align="center">' + dataArr[i].WARENO + '</td><td>' + dataArr[i].WARENAME + '</td><td align="right">' + Number(dataArr[i].AMOUNT) + '</td><td align="right">' + dataArr[i].PRICE + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
				htmlStr += html5;
			} else {
				html1 = '<tr class="a3"><td colspan="4">' + dataArr[i].DATESTR + '</td><td>上日欠款</td><td>' + dataArr[i].FIRCURR + '</td></tr>';
				html2 = '<tr class="a2"><td align="center">单据号</td><td align="center">货号</td><td align="center">商品</td><td align="center">数量</td><td align="center">单价</td><td align="center">金额</td></tr>';
				html5 = '<tr><td>' + dataArr[i].NOTENO + '</td><td align="center">' + dataArr[i].WARENO + '</td><td>' + dataArr[i].WARENAME + '</td><td align="right">' + Number(dataArr[i].AMOUNT) + '</td><td align="right">' + dataArr[i].PRICE + '</td><td align="right">' + dataArr[i].CURR + '</td></tr>';
				if (dataArr[i].DATESTR == dataArr[i - 1].DATESTR) {
					htmlStr += html5;
					if (i == (dataArr.length - 1)) {
						html6 = '<tr class="a1"><td colspan="2" align="left">应收金额</td><td align="right" colspan="2">' + dataArr[i].SUMCURR + '</td><td align="left">折让金额</td><td align="right">' + dataArr[i].CUTCURR + '</td></tr><tr class="a1"><td colspan="2" align="left">已收金额</td><td align="right" colspan="2">' + dataArr[i].PAYCURR + '</td><td>本日欠款</td><td align="right">' + dataArr[i].BALCURR + '</td></tr>';
						htmlStr += html6;
					}
				} else {
					if (i == (dataArr.length - 1)) {
						htmlStr += html1;
						htmlStr += html2;
						html6 = '<tr class="a1"><td colspan="2" align="left">应收金额</td><td align="right" colspan="2">' + dataArr[i].SUMCURR + '</td><td align="left">折让金额</td><td align="right">' + dataArr[i].CUTCURR + '</td></tr><tr class="a1"><td colspan="2" align="left">已收金额</td><td align="right" colspan="2">' + dataArr[i].PAYCURR + '</td><td>本日欠款</td><td align="right">' + dataArr[i].BALCURR + '</td></tr>';
						htmlStr += html5;
						htmlStr += html6;
					} else {
						html6 = '<tr class="a1"><td colspan="2" align="left">应收金额</td><td align="right" colspan="2">' + dataArr[i - 1].SUMCURR + '</td><td align="left">折让金额</td><td align="right">' + dataArr[i - 1].CUTCURR + '</td></tr><tr class="a1"><td colspan="2" align="left">已收金额</td><td align="right" colspan="2">' + dataArr[i - 1].PAYCURR + '</td><td>本日欠款</td><td align="right">' + dataArr[i - 1].BALCURR + '</td></tr>';
						htmlStr += html6;
						htmlStr += html1;
						htmlStr += html2;
						htmlStr += html5;
					}

				}
			}
		}
	}
	htmlStr += '</table>';
// 	var rnum = rownumbers * 20 > totals ? totals: rownumbers * 20;
	$('#gg').html(htmlStr);
	$('#notetotal').html('共有' + totals + '条记录');
}
//查询批发商品对账列表方法  
var datas = {total:0,rows: []};
function searchpage(page) {
	alertmsg(6);
	var hzfs = $("#shzfs").val(); //汇总方式
	var pages = 1;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		async: false,
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listwareoutcheck",
			rows: pagecount,
			page: page,
			hzfs: hzfs
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var total = data.total;
					console.log(datas);
					if(page==1){
						datas.total = total;
						datas.rows = datas.rows.concat(data.rows);
						pages = Math.ceil(total/pagecount);
					}else{
						datas.rows = datas.rows.concat(data.rows);
					}
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
	if(pages>1){
		for(var i=2;i<=pages;i++){
			searchpage(i);
		}
	}
}
function demoPrint(toPrview) {
	LODOP = getLodop();
	var style1 = "table{width:100%;border-style: solid;border-color: #000000;  border-collapse:collapse;border-width:0px 0px 0px 0px;}" + "table td{border: solid #000000; border-width:0px 1px 1px 1px;width:auto;}" + "table .t2 th{border-style: solid;border-color: #000000;border-width:0px 0px 1px 0px;}" + "table .t2{border-style: solid;border-color: #000000;font-weight:bold;border-width:0px 0px 1px 0px;}" + "table .t1{font-weight: bold;font-size:18px;border-width:0px 0px 0px 0px;}" + "table .t1 td{border-width:0px 0px 0px 0px; height:50px;}" + "table .a3{border: solid #000000;border-width:2px 1px 1px 1px;}" + "table .a3 td{border: solid #000000;border-width:3px 1px 1px 1px;}" + "table .t3 td{border-width:0px 0px 0px 0px;}" + ".a1 td{border: solid #000000;border-width:1px 1px 3px 1px;}"
	//+".a2{font-weight:500;}"
	+ ".t3{text-align:right;}";
	var strBodyStyle = "<style>" + style1 + "</style>";
	var strFormHtml = strBodyStyle + "<body>" + document.getElementById("gg").innerHTML + "</body>";
	LODOP.SET_PRINTER_INDEXA("0");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.PRINT_INIT("批发商品对账表");
	LODOP.SET_PRINT_STYLE("FontSize", 18);
	LODOP.SET_PRINT_STYLE("Bold", 1);
	LODOP.ADD_PRINT_TEXT(50, 31, 750, 1000, "批发商品对账表");
	LODOP.ADD_PRINT_HTM(50, 11, 750, 1000, strFormHtml);
	if (toPrview) LODOP.PREVIEW();
	else LODOP.PRINT();
};
var acctitle = "";
var tel = "";
var address = "";
var linkman = "";
function getaccregbyid() {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getaccregbyid",
			accid: getValuebyName("ACCID"),
			fieldlist: "accid,accname,regdate,mobile,company,linkman,address,email,tel,begindate"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if (data.total > 0) {
						accobj = data.rows[0];
						acctitle = accobj.COMPANY;
						tel = accobj.TEL;
						address = accobj.ADDRESS;
						linkman = accobj.LINKMAN;
					}
				}
			} catch(e) {
				console.log(e);
			}
		}
	});
}
</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpFiles/CustHelpList.jsp"></jsp:include>
	<!-- 销售员帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	
</body>
</html>