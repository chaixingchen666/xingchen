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
<title>销售趋势分析</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<body class="easyui-layout layout panel-noscroll" style="min-height: 700px;overflow: auto;">
	<!-- 工具栏 -->
<div id="bodybox" style="overflow: auto;min-height: 600px;">
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
<div id="hsearch" class="searchbar"  data-enter="searchdata();toggle();" style="clear:both;width:100%;">
		<!-- 高级搜索 -->
		<div class="search-box">
		<div class="s-box"><div class="title">开始日期</div>
		<div class="text"><input id="smindate" type="text" editable="true" style="width:164px;height:25px" class="easyui-datebox" required="required" currentText="today"></input>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
		<div class="text"><input id="smaxdate" type="text" editable="true" style="width:164px;height:25px" class="easyui-datebox" required="required" currentText="today"></input>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
		<div class="text"><input type="text"
					 class="edithelpinput house_help" data-value="#shouseid" name="shousename"
					id="shousename" placeholder="<选择或输入>" ><span onclick="selecthouse('search')"></span>
					<input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">销售方式</div>
		<div class="text"><select id="sxsid" class="sele1">
						<option value="0">零售</option>
						<option value="1">批发</option>
						<option value="2" selected="selected">所有</option>
				</select>
</div>
</div>
<div class="s-box"><div class="title">客户</div>
		<div class="text"><input  type="text" class="edithelpinput cust_help" data-value="#scustid"
				name="scustname" id="scustname" placeholder="<选择或输入>" ><span onclick="selectcust('search')" ></span>
				 <input type="hidden" id="scustid" value="">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
		<div class="text">
		<input type="text" class="edithelpinput" data-value="#swareid" name="swareno"id="swareno"  placeholder="<选择或输入>" >
		<span onclick="selectware('search')"></span>
		<input type="hidden" id="swareid" value="">
</div>
</div>
			
<div class="s-box"><div class="title">商品名称</div>
		<div class="text">
		<input type="text" class="wid160 hig25" name="swarename" id="swarename" maxlength="20" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
		<div class="text">
		<input type="text" class="edithelpinput brand_help" data-value="#sbrandid" name="sbrandname" id="sbrandname" placeholder="<选择或输入>" >
		<span onclick="selectbrand('search')"></span> 
		<input type="hidden" name="sbrandid" id="sbrandid" value="">
</div>
</div>
<div class="s-box"><div class="title">类型</div>
		<div class="text">
		<input type="text" class="edithelpinput" name="sfullname" id="sfullname"  data-value="#stypeid" placeholder="<选择或输入>">
		<span onclick="selectwaretype('search');"></span>
					<input type="hidden" name="stypeid" id="stypeid" value="">
</div>
</div>
					
<div class="s-box"><div class="title">季节</div>
		<div class="text">
		<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
        <span onclick="opencombox(this)"></span>
</div>
</div>

<div class="s-box"><div class="title">生产年份</div>
		<div class="text"><select id="sprodyear" class="sele1">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>
</div>
</div>
<div id="alldiv" style="overflow-y: scroll;overflow-x:hidden;height:230px;">
<div style="width: 98%;overflow:auto">
		<div style="width: 98%; height: 30px;"><table><tr>
			<td><input type="radio" name="hzfs2" value="0" checked="checked"/>本日时点</td>
			<td><input type="radio" name="hzfs2" value="1" />本月时点</td>
			<td><input type="radio" name="hzfs2" value="2"  />按日汇总</td>
			<td><input type="radio" name="hzfs2" value="3" />按周汇总</td>
			<td><input type="radio" name="hzfs2" value="4" />按月汇总</td>
			</tr></table></div>
	 <div id="chartdiv" style="width: 49%; height: 200px;float:left;overflow: auto;"></div>
	  <div id="chartdiv2" style="width: 49%; height: 200px; overflow: auto;"></div>
	</div>
</div>
</div>
	<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<script type="text/javascript">
var searchb = true;
var rownumbers = 1;
var pgid = "pg1704";
var epno = getValuebyName("EPNO");
setqxpublic();
//设置弹出框的属性
$(function() {
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	$('#smindate').datebox('setValue', year + '-' + month + '-01');
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("input:radio[name='hzfs2']").click(function() {
		searchdata();
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
		backFn: function(p) {
			rownumbers = p;
			searchpage(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'ID',
		width: '100%',
		height: ($("body").height()>700?$("body").height():700) - 50,
		fitColumns: true,
		striped: true,
		singleSelect: true,
		showFooter: true,
		toolbar: "#toolbars",
		sortName: "DATESTR",
		sortOrder: "asc",
		columns: [[{
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
			field: 'DATESTR',
			title: "时间",
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'SKC',
			title: 'SKC',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURR',
			title: '销售额',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'RATECUR',
			title: '占比',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					return nval + "%";
				}
			}
		},
		{
			field: 'DISCOUNT',
			title: '平均折扣',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PRICE',
			title: '平均单价',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'MLCURR',
			title: '毛利',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			hidden: allowinsale == 1 ? false: true,
			formatter: currfm
		}]],
		onClickRow: function(rowIndex, rowData) {

		},
		onSortColumn: function(sort,order){
			$("#pp").refreshPage();
		},
		pageSize: 20
	});
	alerthide();
});
var chart2; //饼图
var chart; //折线图
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
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	$('#smindate').datebox('setValue', year + '-' + month + '-1');
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#sxsid').val('2');
	$('#swarename').val('');
	$('#scustname').val('');
	$('#scustid').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
}
var footer = [];
//统计销售趋势方法
function searchdata() {
	var hzfs = $("input[name='hzfs2']:checked").val(); //汇总方式
	var title = "";
	if (hzfs == 0||hzfs == 1) {
		title = "时间";
	} else if (hzfs == 2) {
		title = "日期";
	} else if (hzfs == 3) {
		title = "周";
	} else if (hzfs == 4) {
		title = "月份";
	}
	var $dg = $("#gg");
	var col = $dg.datagrid("getColumnOption","DATESTR");
	col.title = title;
	$dg.datagrid();
	var mindate = $("#smindate").datebox('getValue'); //开始日期
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期
	var houseid = Number($("#shouseid").val()); //店铺
	var xsid = $("#sxsid").val(); //销售方式sortid
	var custid = Number($("#scustid").val()); //客户
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var warename = $("#swarename").val(); //商品
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	brandid = brandid==""?"-1":brandid;
	typeid = typeid==""?"-1":typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totalxsqsfx",
			mindate: mindate,
			maxdate: maxdate,
			hzfs: hzfs,
			houseid: houseid,
			xsid: xsid,
			custid: custid,
			wareno: wareno,
			wareid: wareid,
			warename: warename,
			brandid: brandid,
			typeid: typeid,
			prodyear: prodyear,
			seasonname: seasonname
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					footer = [
						{
							ROWNUMBER: "合计",
							AMOUNT: data.amount,
							CURR: data.curr,
							MLCURR: data.mlcurr
						}
					];
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var currentdata = {};
//查询销售趋势列表方法
function searchpage(page) {
	if(footer.length==0){searchdata();return;};
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	currentdata = {
		erpser: "listxsqsfx",
		order: order,
		sort: sort,
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
					chart = AmCharts.makeChart("chartdiv", {
						type: "serial",
						theme: "patterns",
						categoryField: "DATESTR",
						dataProvider: data.rows,
						startDuration: 0.1,
						colors: amchartcolors,
						categoryAxis: {
							labelRotation: 60,
							gridPosition: "start"
						},
						chartCursor: [{
							cursorAlpha: 0,
							zoomable: false,
							categoryBalloonEnabled: false
						}],
						graphs: [{
							type: "smoothedLine",
							lineColor: "#d1655d",
							valueField: "AMOUNT",
							balloonText: "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>"
						}]
// 						,
// 						legend: {
// 							useGraphSettings: true
// 						}
					});
					chart2 = AmCharts.makeChart("chartdiv2", {
						type: "serial",
						theme: "patterns",
						dataProvider: data.rows,
						categoryField: "DATESTR",
						startDuration: 0.1,
						colors: amchartcolors,
						categoryAxis: {
							labelRotation: 60,
							gridPosition: "start"
						},
						chartCursor: [{
							cursorAlpha: 0,
							zoomable: false,
							categoryBalloonEnabled: false
						}],
						graphs: [{
							type: "column",
							valueField: "CURR",
							lineAlpha: 0,
							fillAlphas: 0.8,
							balloonText: "[[category]]:<b>[[value]]</b>"
						}]
// 						,
// 						legend: {
// 							useGraphSettings: true
// 						}
					});
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter', footer);
					$('#pp_total').html(totals);
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
	<!-- 颜色帮助列表 -->
	<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
</body>
</html>