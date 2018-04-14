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
<title>店铺年度计划</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
*{
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
	box-sizing: border-box;
}
.cal-box {
    position: absolute;
    left: 0;
    top: 80px;
    width: 535px;
    background: #fff;
}
.cal-table{
    border-spacing: 0;
    border-collapse: collapse;
}
.salemanplan{
	position: absolute;
	top: 80px;
	left: 545px;
	right: 10px;
	bottom: 10px;
	background-color: #fff;
	padding: 5px;
	min-width: 200px;
}
.salemanplan-head{
	height: 35px;
	line-height: 35px;
	border-bottom: 1px solid #ddd;
	overflow: hidden;
	zoom:1;
	font-weight: 600;
}
tr.month-head {
    height: 35px;
}
tr.week-head {
    height: 30px;
}
tr.week-head td {
    border-bottom: #ddd 1px solid;
    border-top: 1px solid #ddd;
    text-align: center;
}
#monthday-body tr td{
	width:75px;
	height:75px;
	padding: 5px;
	text-align: right;
	border-bottom: 1px solid #ddd;
	border-right: 1px solid #ddd;
	font-size: 14px;
	cursor: pointer;
}
#monthday-body tr td[data-datestr]:hover{
	background-color: #0cf;
}
#monthday-body tr td[data-datestr].selected{
	background-color: #09f;
}
#monthday-body tr td:first-child{
	border-left: 1px solid #ddd;
}
.curr,#monthday-body tr td p.daycurr,.epcurr{
	color: #ff7900;
}
#monthday-body tr td p.daycurr{
	font-size: 12px;
}
.salemanplan-table {
    overflow: auto;
    position: absolute;
    bottom: 0;
    top: 40px;
    right: 0;
    left: 0;
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
<body class="easyui-layout layout panel-noscroll">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<table class="table1" cellspacing="10">
<tr>
<td>
店铺：
</td>
<td id="shousename">
棒棒糖
</td>
<td id="syearnum">
2017
</td>
<td>
<select class="sele1" id="smonthnum" style="width:70px" onchange="getsalemanplan()">
<option value="1">1月</option>
<option value="2">2月</option>
<option value="3">3月</option>
<option value="4">4月</option>
<option value="5">5月</option>
<option value="6">6月</option>
<option value="7">7月</option>
<option value="8">8月</option>
<option value="9">9月</option>
<option value="10">10月</option>
<option value="11">11月</option>
<option value="12">12月</option>
</select>
</td>
<td>
计划：<label class="curr" id="totalcurr">10000</label> 元 分配： <label class="curr" id="totalcurr1">10</label> 元
</td>
<td>
<input class="btn1" type="button" id="addnotebtn" value="自动生成" onclick="autosalemanplan()">
</td>
</tr>
</table>
</div>
</div>
<div class="cal-box">
<table class="cal-table" style="margin: 5px;">
<thead>
<tr class="month-head">
<th colspan="7" align="center">
<label class="jinstyle">今</label><label id="yearstr">2017</label>年<label id="monthstr">1</label>月 第<label id="weekstr">1</label>周
</th>
</tr>
<tr class="week-head">
<td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td><td>日</td>
</tr>
</thead>
<tbody id="monthday-body">
<tr class="week1" data-week="1">
<td class="day1">
</td>
<td class="day2">
</td>
<td class="day3">
</td>
<td class="day4">
</td>
<td class="day5">
</td>
<td class="day6">
</td>
<td class="day7">
</td>
</tr>
<tr class="week2" data-week="2">
<td class="day1">
</td>
<td class="day2">
</td>
<td class="day3">
</td>
<td class="day4">
</td>
<td class="day5">
</td>
<td class="day6">
</td>
<td class="day7">
</td>
</tr>
<tr class="week3" data-week="3">
<td class="day1">
</td>
<td class="day2">
</td>
<td class="day3">
</td>
<td class="day4">
</td>
<td class="day5">
</td>
<td class="day6">
</td>
<td class="day7">
</td>
</tr>
<tr class="week4" data-week="4">
<td class="day1">
</td>
<td class="day2">
</td>
<td class="day3">
</td>
<td class="day4">
</td>
<td class="day5">
</td>
<td class="day6">
</td>
<td class="day7">
</td>
</tr>
<tr class="week5"  data-week="5">
<td class="day1">
</td>
<td class="day2">
</td>
<td class="day3">
</td>
<td class="day4">
</td>
<td class="day5">
</td>
<td class="day6">
</td>
<td class="day7">
</td>
</tr>
</tbody>
</table>
</div>
<div class="salemanplan">
<div class="salemanplan-head">
<div class="fl">
日期：
<label id="selectday">2017-01-01</label>
</div>
<div class="fr">
总金额：
<label id="selecttotalcurr" class="curr">600.00</label>
元
</div>
</div>
<div class="salemanplan-table">
<table class="table1" cellspacing="10">
<thead>
<tr>
<th align="center">
店员
</th>
<th align="center">
计划金额
</th>
</tr>
</thead>
<tbody id="salemanlist">
</tbody>
<tfoot>
<tr>
<td colspan="2" align="center">
<input type="button" class="btn1" value="保存" onclick="writesalemanplan()">
</td>
</tr>
</tfoot>
</table>
</div>
</div>
<script type="text/javascript">
var pgid="pg1311";
var levelid = getValuebyName("GLEVELID"); 
setqxpublic();
var selectday = "";
$(function(){
	$("#shousename").html(getValuebyName("HOUSENAME"));
	var houseid = Number(getValuebyName("HOUSEID"));
	if(houseid==0||isNaN(houseid)){
		alerttext("该职员没有所属店铺，请设置！");
		return;
	}
	var date = new Date();
	selectday = date.Format("yyyy-MM-dd");
	var yearnum = date.Format("yyyy");
	var monthnum = date.Format("MM");
	var weeknum = date.Format("q");
	$("#syearnum").html(yearnum);
	$("#smonthnum").val(Number(monthnum));
	$("#yearstr").html(yearnum);
	$("#monthstr").html(monthnum);
	$("#weekstr").html(weeknum);
	getsalemanplan();
	$("#monthday-body td").click(function(e){
		$this = $(this);
		var datestr = $this.data("datestr");
		var weekstr = $this.parent().data("week");
		if(datestr){
			var selected = $this.hasClass("selected");
			if(!selected){
				$("#weekstr").html(weekstr);
				$("#monthday-body td.selected").removeClass("selected");
				$this.addClass("selected");
				selectday = datestr;
				listsalemanplan(1);
			}
		}
	});
	$("#salemanlist").delegate("input.epcurr","keyup",function(e){
		this.value = this.value.replace(/\D/g,"");
		sumepcurr();
	}).delegate("input.epcurr","change",function(e){
		this.value = Number(this.value).toFixed(2);
	});
});
function setdayhtml(yearnum,monthnum){
	var firstdate = new Date(yearnum+"/"+monthnum+"/"+1);
	var weekday = firstdate.getDay();//本月1号是周几
	var d = new Date(yearnum,monthnum,0); //下一月的上一天
	var monthdays = d.getDate(); //本月的天数
	var $monthdays = $("#monthday-body td");
	$monthdays.html("");
	var $first = $("#monthday-body tr.week1 td.day"+weekday);
	var sindex = $monthdays.index($first);
	for(var i=1;i<=monthdays;i++){
		var html = '<p class="dayno">'+i+'</p><p class="daycurr" data-daystr="'+i+'">0.00</p>';
		$monthdays.eq(sindex++).html(html);
	}
}
function getsalemanplan(){
	var houseid = Number(getValuebyName("HOUSEID"));
	if(houseid==0||isNaN(houseid)){
		alerttext("该职员没有所属店铺，请设置！");
		return;
	}
	var yearnum = $("#syearnum").html();
	var monthnum = $("#smonthnum").val();
	$("#monthstr").html(monthnum);
	setdayhtml(yearnum,monthnum);
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "getsalemanplan",
			houseid: houseid,
			yearnum: yearnum,
			monthnum: monthnum
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					$("#totalcurr").html(Number(data.TOTALCURR*10000).toFixed(2));
					$("#totalcurr1").html(Number(data.TOTALCURR1).toFixed(2));
					var daycurr = data.daycurr;
					for(var i in daycurr){
						var row = daycurr[i];
						var datestr = row.DATESTR;
						var daystr = row.DAYSTR;
						var curr = Number(row.CURR).toFixed(2);
						$("#monthday-body p[data-daystr="+Number(daystr)+"]").parent().attr("data-datestr",datestr);
						$("#monthday-body p[data-daystr="+Number(daystr)+"]").html(curr);
					}
				}
				$("#monthday-body td.selected").removeClass("selected");
				$("#monthday-body td[data-datestr="+selectday+"]").addClass("selected");
				listsalemanplan(1);
			}catch(e){
				console.log(e);
			}
		}
	});
}
function autosalemanplan(){
	var houseid = Number(getValuebyName("HOUSEID"));
	if(houseid==0||isNaN(houseid)){
		alerttext("该职员没有所属店铺，请设置！");
		return;
	}
	var yearnum = $("#syearnum").html();
	var monthnum = $("#smonthnum").val();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "autosalemanplan",
			houseid: houseid,
			yearnum: yearnum,
			monthnum: monthnum
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					alerttext("自动生成成功");
					setTimeout(function(){
						getsalemanplan();
					}, 1000);
				}
			}catch(e){
				console.log(e);
			}
		}
	});
}
function listsalemanplan(page){
	var houseid = Number(getValuebyName("HOUSEID"));
	if(houseid==0||isNaN(houseid)){
		alerttext("该职员没有所属店铺，请设置！");
		return;
	}
	$("#selectday").html(selectday);
	$("#selecttotalcurr").html($("#monthday-body td.selected p.daycurr").html());
	if(page==1){
		$("#salemanlist").html("");
	}
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "listsalemanplan",
			houseid: houseid,
			rows: pagecount,
			currdate: selectday
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					if(data.total>0){
						var html = "";
						var rows = data.rows;
						for(var i in rows){
							var row = rows[i];
							html += '<tr>';
							html += '<td align="right">'+row.EPNAME+'</td>';
							html += '<td><input type="text" class="wid160 hig25 epcurr" data-epid="'+row.EPID+'" value="'+Number(row.CURR).toFixed(2)+'" /></td>';
							html += '</tr>';
						}
						$("#salemanlist").append(html);
						$ipt = $("#salemanlist input").eq(0);
						$ipt.select().focus();
					}
				}
			}catch(e){
				console.log(e);
			}
		}
	});
}
function sumepcurr(){
	var sumcurr = 0;
	$("#salemanlist input.epcurr").each(function(e){
		var $this = $(this);
		var curr = Number($this.val());
		if(!isNaN(curr)){
			sumcurr += curr;
		}
	});
	$("#selecttotalcurr").html(sumcurr.toFixed(2));
}
function writesalemanplan(){
	var houseid = Number(getValuebyName("HOUSEID"));
	if(houseid==0||isNaN(houseid)){
		alerttext("该职员没有所属店铺，请设置！");
		return;
	}
	var epcurrlist = [];
	$("#salemanlist input.epcurr").each(function(e){
		var $this = $(this);
		var epid = $this.data("epid");
		var curr = $this.val();
		if(!isNaN(curr)){
			epcurrlist.push({
				epid: epid,
				curr: curr
			});
		}
	});
	alertmsg(2);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "writesalemanplan",
			houseid: houseid,
			currdate: selectday,
			epcurrlist: JSON.stringify(epcurrlist)
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					alerttext(data.msg);
					var sumcurr = Number($("#selecttotalcurr").html());
					var oldcurr = Number($("#monthday-body td.selected p.daycurr").html());
					var totalcurr1 = Number($("#totalcurr1").html());
					totalcurr1 = totalcurr1 - oldcurr + sumcurr;
					$("#totalcurr1").html(totalcurr1.toFixed(2));
					$("#monthday-body td.selected p.daycurr").html(sumcurr.toFixed(2));
				}
			}catch(e){
				console.log(e);
			}
		}
	});
}
</script>
</body>
</html>