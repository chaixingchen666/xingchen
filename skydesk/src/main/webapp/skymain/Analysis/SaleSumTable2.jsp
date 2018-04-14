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
<title>销售汇总分析</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
#yesno{margin-top:5px;}
.datediv{display: none}
.xsfx{
	border-top:2px solid #999999;
	border-bottom:2px solid #999999;
	border-collapse:collapse;
		
}
.xsfx td{border-right:1px solid #999999;border-bottom:1px solid #999999;width:50px;}
.orangesx{color:#0000FF;}
</style>
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/amchart/amcharts.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/amchart/pie.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/amchart/serial.js"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
		<input type="button" id="highsearch" class="btn2" value="条件搜索" onclick="toggle()">
		<input id="csearch" class="btn1" type="button" onclick="searchdata();toggle();" value="查找" />
		<input id="qclear" class="btn1" type="button" value="清除内容" onclick="resetsearch()"/>
		</div>
	<div class="fr">
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
	</div>
	</div>
	<!-- 隐藏查询条件 -->
	<div id="hsearch"  class="hsearch-box"  data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box datediv"><div class="title">开始日期</div>
<div class="text"><input id="smindate" type="text" editable="true" style="width:162px;height:25px" class="easyui-datebox" required="required" currentText="today">			
</div>
</div>
<div class="s-box datediv"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" type="text" editable="true" style="width:162px;height:25px" class="easyui-datebox" required="required" currentText="today">
</div>
</div>
<div class="s-box"><div class="title">销售方式</div>
<div class="text"><select id="sxsid"
					style="width: 164px; height: 25px">
						<option value="0">零售</option>
						<option value="1">批发</option>
						<option value="2" selected="selected">所有</option>
				</select>
</div>
</div>
<div class="s-box hide"><div class="title">业务类型</div>
<div class="text"><select id="sntid" class="sele1">
						<option value="0">出库</option>
						<option value="1">退货</option>
						<option value="2" selected="selected">所有</option>
				</select>
</div>
</div>
			
<div class="s-box"><div class="title">生产商</div>
<div class="text"><input class="edithelpinput provd_help" maxlength="20" style="width:125px;height:24px;" data-value="#sprovid" placeholder="<选择或输入>"
					 type="text" id="sprovname" name="sprovname"><span onclick="selectprov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid">
</div>
</div>
			
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input type="text" class="edithelpinput house_help" data-value="#shouseid" name="shousename"
					id="shousename"placeholder="<选择或输入>" ><span onclick="selecthouse('search')"></span>
					<input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input  type="text"   class="edithelpinput cust_help" data-value="#scustid"
				name="scustname" id="scustname" placeholder="<选择或输入>" ><span onclick="selectcust('search')"></span>
				 <input type="hidden" id="scustid" value="">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input type="text" 
					class="edithelpinput wareno_help" data-value="#swareid" name="swareno"
					id="swareno" placeholder="<选择或输入>" ><span onclick="selectware('search')"></span>
					<input type="hidden" id="swareid" value="">
</div>
</div>
<div class="s-box"><div class="title">商品名称</div>
<div class="text"><input type="text" name="swarename" id="swarename" maxlength="20"
					style="width: 160px; height: 25px;"
					placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input type="text" class="edithelpinput brand_help" data-value="#sbrandid"
					name="sbrandname" id="sbrandname"
					placeholder="<请选择>" >
					<span onclick="selectbrand('search')" ></span> 
					<input type="hidden" name="sbrandid" id="sbrandid" value="">
</div>
</div>
<div class="s-box"><div class="title">类型</div>
<div class="text"><input type="text" class="edithelpinput" name="sfullname" id="sfullname" data-value="#stypeid" placeholder="<选择或输入>" >
					<span onclick="selectwaretype('search');"></span>
					<input type="hidden" name="stypeid" id="stypeid" value="">
</div>
</div>
		
<div class="s-box"><div class="title">销售部门</div>
<div class="text"><input type="text" class="edithelpinput dpt_help" data-value="#sdptid" id="sdptname"
					placeholder="<选择或输入>">
					<span onclick="selectdepartment('search')"></span>
					<input type="hidden" id="sdptid" name="sdptid">
</div>
</div>
<div class="s-box"><div class="title">季节</div>
<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select id="sprodyear"
					style="width: 164px; height: 25px">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>
<div class="s-box"><div class="title">区位</div>
<div class="text"><input type="text" class="edithelpinput area_help" data-value="#sareaid" id="sareaname"
					placeholder="<选择或输入>">
					<span onclick="selectarea('search')"></span>
					<input type="hidden" id="sareaid" name="sareaid">
</div>
</div>
<div class="s-box"><div class="title">货位</div>
<div class="text"><input type="text" class="wid160 hig25" name="slocale" id="slocale" maxlength="20" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">客户区域</div>
<div class="text">
<input type="text" class="wid160 hig25" name="scustareaname" id="scustareaname" placeholder="<输入>">
</div>
</div>
</div>
</div>
<div id="alldiv">
<div style="width: 98%;height:260px; overflow:auto">
<div style="width: 98%; height: 30px;">
<label><input type="radio" name="hzfs2" value="0"/>今天</label>
<label><input type="radio" name="hzfs2" value="1"/>昨天</label>
<label><input type="radio" name="hzfs2" value="2"/>本周</label>
<label><input type="radio" name="hzfs2" value="3" checked="checked"/>本月</label>
<label><input type="radio" name="hzfs2" value="4"/>指定日期</label>
</div>
<div style="width: 98%; height: 30px;">
			<label><input type="radio" name="hzfs" value="0"  checked="checked"/>商品</label>
			<label> <input type="radio" name="hzfs" value="3" />类型</label>
			<label> <input type="radio" name="hzfs" value="4"/>品牌</label>
			<label> <input type="radio" name="hzfs" value="8" />客户</label>
			<label> <input type="radio" name="hzfs" value="1" />商品,颜色</label>
			<label> <input type="radio" name="hzfs" value="2" />商品,颜色,尺码</label>
			<label> <input type="radio" name="hzfs" value="5" />季节</label>
			<label> <input type="radio" name="hzfs" value="6" />日期</label>
			<label> <input type="radio" name="hzfs" value="19" />生产商</label>
			<label> <input type="radio" name="hzfs" value="7" />店铺</label>
			<label> <input type="radio" name="hzfs" value="10" />颜色</label>
			<label> <input type="radio" name="hzfs" value="11" />销售类型</label>
			<label> <input type="radio" name="hzfs" value="13" />销售部门</label>
			<label> <input type="radio" name="hzfs" value="14" />尺码</label>
			<label> <input type="radio" name="hzfs" value="15" />折扣</label>
			<label> <input type="radio" name="hzfs" value="18" />区位</label>	
			<label> <input type="radio" name="hzfs" value="16" />货位</label>	
</div>
<div>
 <div id="chartdiv0" style="width: 49%; height: 200px;float:left;overflow: auto;"></div>
 <div id="chartdiv1" style="width: 49%; height: 200px; overflow: auto;"></div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>	
<script type="text/javascript">
var searchb = true;
var pgid = "pg1703";
var epno = getValuebyName("EPNO");
var levelid = Number(getValuebyName("GLEVELID"));
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	$('#gg').datagrid("resize", {
		height: $('body').height() - 50
	});
}
var hzcolumn = [{
	field: 'AMOUNT',
	title: '数量',
	fieldtype: "G",
	width: 80,
	fixed: true,
	align: 'right',
	halign: 'center',
	sortable: true,
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(0);
			if (nval != 0) {
				return nval.toString();
			} else {
				return "";
			}
		}
	}
},
{
	field: 'CURR',
	title: '金额',
	fieldtype: "N",
	width: 100,
	fixed: true,
	align: 'right',
	halign: 'center',
	sortable: true,
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(2);
			return nval.toString();
		}
	}
},
{
	field: 'SKC',
	title: 'SKC',
	fieldtype: "G",
	width: 60,
	fixed: true,
	align: 'center',
	halign: 'center',
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(0);
			if (nval != 0) {
				return nval.toString();
			} else {
				return "";
			}
		}
	}
},
{
	field: 'FIXEDCURR',
	title: '成本',
	fieldtype: "N",
	width: 100,
	fixed: true,
	align: 'right',
	halign: 'center',
	hidden: ((Number(allowinsale) == 1) ? false: true),
	formatter: function(value, row, index) {
		if (Number(allowinsale) == 1) {
			var val = Number(value);
			if (!isNaN(val) && val != 0) return Number(value).toFixed(2);
			return "";
		}
		return "***";
	}
},
{
	field: 'MLCURR',
	title: '利润',
	fieldtype: "N",
	width: 100,
	fixed: true,
	align: 'right',
	halign: 'center',
	sortable: true,
	hidden: ((Number(allowinsale) == 1) ? false: true),
	formatter: function(value, row, index) {
		if (Number(allowinsale) == 1) {
			var val = Number(value);
			if (!isNaN(val) && val != 0) return Number(value).toFixed(2);
			return "";
		}
		return "***";
	}
},
{
	field: 'ZBRATE',
	title: '占比',
	fieldtype: "G",
	width: 60,
	fixed: true,
	align: 'right',
	halign: 'center',
	formatter: function(value, row, index) {
		var val = Number(value);
		if (isNaN(val) || val == 0) {
			return "";
		} else {
			return (val * 100).toFixed(2) + "%";
		}
	}
},
{
	field: 'MLRATE',
	title: '毛利率',
	fieldtype: "G",
	width: 60,
	fixed: true,
	align: 'right',
	halign: 'center',
	hidden: (allowinsale==1?false:true),
	formatter: function(value, row, index) {
		var val = Number(value);
		if (isNaN(val) || val == 0) {
			return "";
		} else {
			return (val * 100).toFixed(2) + "%";
		}
	}
},
{
	field: "DISCOUNT",
	title: '平均折扣',
	fieldtype: "N",
	width: 60,
	fixed: true,
	align: 'right',
	halign: 'center',
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(2);
			return nval.toString();
		}
	}
},
{
	field: 'PRICE',
	title: '平均单价',
	fieldtype: "N",
	width: 80,
	fixed: true,
	align: 'right',
	halign: 'center',
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(2);
			return nval.toString();
		}
	}
},
{
	field: 'XSNUM',
	title: '销售单数',
	fieldtype: "G",
	width: 80,
	fixed: true,
	align: 'right',
	halign: 'center',
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(0);
			if (nval != 0) {
				return nval.toString();
			} else {
				return "";
			}
		}
	}
},
{
	field: 'XSORD',
	title: '销售排名',
	fieldtype: "G",
	width: 60,
	fixed: true,
	align: 'right',
	halign: 'center',
	sortable: true,
	formatter: function(value, row, index) {
		if (value != '' && value != null) {
			var val = Number(value);
			var nval = val.toFixed(0);
			if (nval != 0) {
				return nval.toString();
			} else {
				return "";
			}
		}
	}
}];
var colrownum = {
	field: 'ROWNUMBER',
	title: '序号',
	width: 50,
	fixed: true,
	align: 'center',
	halign: 'center',
	//halign表示对齐头部
	formatter: function(value, row, index) {
		if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
		var p = $("#pp").getPage() - 1;
		return p * pagecount + index + 1;
	}
};
var titleFileds = "WARENO";
//设置弹出框的属性
$(function() {
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	month1 = month - 1;
	var day2 = day + 1;
	var day3 = day - 6;
	var day4 = day - 1;
	var todaydate = year + '-' + month1 + '-' + day2; //本月
	var todaydate1 = year + '-' + month + '-' + day3; //本周
	var todaydate2 = year + '-' + month + '-' + day4; //昨天 
	$('#smindate').datebox('setValue', todaydate);
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("input:radio[name='hzfs2']").click(function() {
		var $this = $(this);
		var dateid = Number($this.val());
		if(dateid==4){
			$("div.datediv").show();
		}else{
			$("div.datediv").hide();
		}
		searchdata();
	});
	$("input:radio[name='hzfs']").click(function() {
		var $dg = $("#gg");
		var options = $dg.datagrid("options");
		var hzfs = Number($(this).val()); //汇总方式
		var fzcolobj = fzcolarr[hzfs];
		var frzcolum = [colrownum];
		if (typeof(fzcolobj) != "object") {
			alerttext("汇总方式无效！");
			return;
		}
		var sortname = "";
		for (var key in fzcolobj) {
			if (sortname.length == 0) sortname = key;
			frzcolum.push({
				field: key,
				title: fzcolobj[key],
				width: 100,
				fixed: true,
				align: 'center',
				halign: 'center'
			});
		}
		options.frozenColumns = [frzcolum];
		options.sortName = sortname;
		titleFileds = sortname;
		$dg.datagrid();
		searchpage(1);
	});
	//生成年份 
	var year = myDate.getFullYear();
	var on = year - 5;
	var end = year + 1;
	for (var i = on; i <= end; i++) {
		$("#sprodyear").append("<option value=" + i + ">" + i + "</option'>");
	}
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
		idField: 'KEYID',
		height: $('body').height() - 50,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		toolbar: "#toolbars",
		pageSize: 20,
		sortName: "WARENO",
		sortOrder: "asc",
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			//halign表示对齐头部
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 120,
			fixed: true,
			align: 'center',
			halign: 'center',
			sortable: true
		},
		{
			field: 'WARENAME',
			title: '商品',
			sortable: true,
			order: 'asc',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		}]],
		columns: [hzcolumn],
		onSortColumn: function(sort,order){
			$("#pp").refreshPage();
		}
	});
	alerthide();
});
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		$('#highsearch').val('条件搜索');
		$("#csearch").hide();
		$("#qclear").hide();
	} else {
		searchb = true;
		$('#highsearch').val('条件搜索▲');
		$("#csearch").show();
		$("#qclear").show();
	}
}
//清空表单搜索条件 
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#sortid').val('0');
	$("input[type=radio][name=hzfs][value=0]").attr("checked", 'checked');
	$('#shouseid').val('');
	$('#shousename').val('');
	$('#sxsid').val('2');
	$('#swareno,#swarename').val('');
	$('#swareid').val('');
	$('#scolorname').val('');
	$('#scolorid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#slocale').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
	$('#sprovid,#sprovname,#scustid,#scustname,#scustareaname').val('');
	$('#sdptid,#sdptname').val('');
	$('#sareaid,#sareaname').val('');
}
var chart0; //销售明细1
var chart1; //销售明细2
var footer =[];
//统计销售汇总方法
function searchdata() {
	var dateid = $("input[name='hzfs2']:checked").val(); //日期方式
	var xsid = $("#sxsid").val(); //销售方式
	var ntid = $("#sntid").val(); //业务类型
	var provid = $("#sprovid").val(); //生产商
	var houseid = $("#shouseid").val(); //店铺
	var custid = $("#scustid").val(); //客户
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var locale = $("#slocale").val(); //货位
	var warename = $("#swarename").val(); //商品名称 
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	var dptid = $("#sdptid").val(); //销售部门
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var areaid = $("#sareaid").val(); //区位
	var custareaname = $("#scustareaname").val(); //区位
	var dataparam = {
		erpser: "totalxshzfx2",
		xsid: xsid,
		ntid: ntid,
		dateid: dateid,
		provid: provid,
		houseid: houseid,
		custid: custid,
		wareno: wareno,
		wareid: wareid,
		locale: locale,
		warename: warename,
		brandid: brandid,
		typeid: typeid,
		dptid: dptid,
		areaname: custareaname,
		areaid: areaid,
		prodyear: prodyear,
		seasonname: seasonname
	};
	if (dateid == 4) {
		var mindate = $('#smindate').datebox('getValue'); //开始时间
		var maxdate = $('#smaxdate').datebox('getValue'); //结束时间
		dataparam["mindate"] = mindate;
		dataparam["maxdate"] = maxdate;
	}
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: dataparam,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
// 					Totalamt0=零售数量, totalcurr0=零售金额,mlcurr0=零售毛利，mlrate0=零售毛利率，
// 					totalamt1=批发数量,totalcurr1=批发金额,mlcurr1=批发毛利，mlrate1=批发毛利率，
// 					totalamt=合计数量,totalcurr=合计发金额,totalretailcurr=合计零售总额，fixedcurr=成本,mlcurr=总毛利,mlrate=总毛利率
// 					totalcustnum=总客户数,xscustnum=消费客户数,newcustnum=新客户数,xsnum=批发销费单数,custactive=客户活跃度
// 					totalguestnum=总会员数,xsguestnum=会员消费人数,newguestnum=新会员数,xxnum=零售销费单数,guestactive=会员活跃度
// 					guestrate=会员消费占比，discount0=零售平均折扣,price0=零售平均单价
					footer = [{
						ROWNUMBER: "合计",
						AMOUNT: data.TOTALAMT,
						CURR: data.TOTALCURR,
						FIXEDCURR: data.FIXEDCURR,
						MLCURR: data.MLCURR
					}];
					searchpage(1);
					return;
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//汇总的表头
var fzcolarr = [{ //0
	WARENO: "货号",
	WARENAME: "商品名称"
},
{ //1
	WARENO: "货号",
	WARENAME: "商品名称",
	COLORNAME: "颜色"
},
{ //2
	WARENO: "货号",
	WARENAME: "商品名称",
	COLORNAME: "颜色",
	SIZENAME: "尺码"
},
{ //3
	TYPENAME: "商品类型"
},
{ //4
	BRANDNAME: "品牌"
},
{ //5
	SEASONNAME: "季节"
},
{ //6
	NOTEDATE: "日期"
},
{ //7
	HOUSENAME: "店铺"
},
{ //8
	CUSTNAME: "客户"
},
{ //9暂时没有
	HZFS9: "9"
},
{ //10
	COLORNAME: "颜色"
},
{ //11
	SALENAME: "销售类型"
},
{ //12暂时没有
	HZFS12: "12"
},
{ //13
	DPTNAME: "销售部门"
},
{ //14
	SIZENAME: "尺码"
},
{ //15
	DISCOUNT: "折扣"
},
{ //16
	LOCALE: "货位"
},
{ //17
	HZFS17: "17"
},
{ //18
	AREANAME: "区位"
},
{ //19
	PROVNAME: "生产商"
}]
var currentdata = {};
//查询销售汇总统计列表排序方法 
function searchpage(page) {
	if (footer.length == 0){searchdata();return;};
	var hzfs = Number($("input[name='hzfs']:checked").val()); //汇总方式
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	currentdata = {
		erpser: "listxshzfx2",
		hzfs: hzfs,
		totalcurr: footer[0].CURR,
		sort: sort,
		order: order,
		rows: pagecount,
		page: page
	};
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
					var totals = data.total;
					//销售明细 
					chart0 = AmCharts.makeChart("chartdiv0", {
						type: "pie",
						theme: "patterns",
						colors: amchartcolors,
						groupPercent: 0.1,
						dataProvider: data.rows,
						titleField: titleFileds,
						valueField: "CURR",
						labelsEnabled: false,
						// depth3D:15,
						// angle:30,
						startDuration: 0.01,
						outlineThickness: 2,
						balloonText: "[[title]]<br><b>[[value]]</b> ([[percents]]%)"
					});
					chart1 = new AmCharts.AmSerialChart();
					chart1.dataProvider = data.rows;
					chart1.categoryField = titleFileds;
					chart1.color = "#000000";
					chart1.startDuration = 0.1;
					chart1.plotAreaFillAlphas = 0.2;
					var chartCursor = new AmCharts.ChartCursor();
					chartCursor.cursorAlpha = 0;
					chartCursor.zoomable = false;
					chartCursor.categoryBalloonEnabled = false;
					chart1.addChartCursor(chartCursor);
					// GRAPHS          
					// first graph
					var graph1 = new AmCharts.AmGraph();
					graph1.title = "零售";
					graph1.valueField = "AMOUNT0";
					graph1.type = "column";
					graph1.lineAlpha = 0;
					graph1.lineColor = "#FFC85F";
					graph1.fillAlphas = 1;
					graph1.balloonText = "[[title]] in [[category]]:<b>[[value]]</b>";
					chart1.addGraph(graph1);
					// second graph
					var graph2 = new AmCharts.AmGraph();
					graph2.title = "批发";
					graph2.valueField = "AMOUNT1";
					graph2.type = "column";
					graph2.lineAlpha = 0;
					graph2.lineColor = "#F76F6F";
					graph2.fillAlphas = 1;
					graph2.balloonText = "[[title]] in [[category]]:<b>[[value]]</b>";
					chart1.addGraph(graph2);
					chart1.write("chartdiv1");
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter', footer);
					$('#pp_total').html('共有' + totals + '条记录');
					$('#gg').datagrid('scrollTo', 0);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/AreaHelpList.jsp"></jsp:include>
	<!-- 生产商帮助列表 -->
	<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
	<!-- 销售部门帮助列表 -->
	<jsp:include page="../HelpList/DPTHelpList.jsp"></jsp:include>
</body>
</html>