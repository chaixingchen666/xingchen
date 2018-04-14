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
<title>销售退货分析</title>
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
</head>
<body class="easyui-layout layout panel-noscroll">
	<!-- 工具栏 -->
	<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
<input type="button" id="highsearch" class="btn2" value="条件搜索" onclick="toggle()">
		<input id="csearch" class="btn1 search_btn" type="button" onclick="searchdata();toggle();" value="查找" />
		<input id="qclear" class="btn1 search_btn" type="button" value="清除内容" onclick="clearAll()"/>
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
		<div class="text">
		<input type="text" name="smindate" id="smindate" editable="true" required="required" currentText="today" style="width: 162px;height:29px" class="easyui-datebox">
		</div>
		</div>
		<div class="s-box"><div class="title">结束日期</div>
		<div class="text">
		<input type="text" name="smaxdate" id="smaxdate"  editable="true" required="required" currentText="today" style="width: 162px;height:29px" class="easyui-datebox">
		</div>
		</div>
		<div class="s-box"><div class="title">汇总方式</div>
		<div class="text">
		<select id="shzfs"  style="width: 164px; height: 25px">
			<option value="0" selected="selected">客户</option>
			<option value="1">店铺</option>
			<option value="2">商品</option>
		</select>	
		</div>
		</div>
		<div class="s-box"><div class="title">客户</div>
		<div class="text">
		<input type="text" class="edithelpinput cust_help" data-value="#scustid" id="scustname" placeholder="<选择或输入>" >
		<span onclick="selectcust('search')"></span>
		<input type="hidden" id="scustid" value="">
		</div>
		</div>
		<div class="s-box"><div class="title">店铺</div>
		<div class="text">
		<input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>" type="text" id="shousename" name="shousename">
			<span  onclick="selecthouse('search')"></span> 
		  	<input type="hidden" id="shouseid" name="shouseid">
		</div>
		</div>
		<div class="s-box"><div class="title">货号</div>
		<div class="text">
		<input type="text" id="swareno" class="edithelpinput wareno_help" data-value="#swareid" maxlength="20" placeholder="<选择或输入>" name="swareno" />
			<span onclick="selectware('search')"></span>
			<input type="hidden" id="swareid" name="swareid">
		</div>
		</div>
		<div class="s-box"><div class="title">品牌</div>
		<div class="text">
		<input  type="text" class="edithelpinput brand_help" name="sbrandname" id="sbrandname" data-value="#sbrandid" maxlength="20" placeholder="<选择或输入>">
			<span onclick="selectbrand('search')"></span>
			<input type="hidden" name="sbrandid" id="sbrandid" value="">
		</div>
		</div>
		<div class="s-box"><div class="title">类型</div>
		<div class="text">
		<input type="text" class="edithelpinput" id="sfullname" name="sfullname" placeholder="<选择或输入>">
			<span onclick="selectwaretype('search');"></span>
			<input type="hidden" id="stypeid" name="stypeid">
		</div>
		</div>
		<div class="s-box"><div class="title">季节</div>
		<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
		</div>
		</div>
		<div class="s-box"><div class="title">生产年份</div>
		<div class="text">
		<select name="sprodyear" id="sprodyear"  class="sele1">
		       <option value="">---请选择---</option>
			 </select>
		</div>
		</div>
		<div class="s-box"><div class="title">生产商</div>
		<div class="text">
		<input class="edithelpinput provd_help" id="sprovname" type="text" data-value="#sprovid">
				<span class="s-btn" onclick="selectprov('search')"></span><input type="hidden" id="sprovid" value="">
		</div>
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
var pgid = "pg1714";
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
	$('#gg').datagrid({
		idField: 'ID',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		showFooter: true,
		nowrap: false,
		//允许自动换行
		singleSelect: true,
		sortName: "CUSTNAME",
		sortOrder: "asc",
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			fixed: true,
			width: 50,
			halign: 'center',
			align: 'center',
			rowspan: 2,
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'ID',
			title: 'ID',
			hidden: true,
			width: 10,
			rowspan: 2
		},
		{
			field: 'CUSTNAME',
			title: '客户',
			width: 110,
			fixed: true,
			sortable: true,
			halign: 'center',
			align: 'left',
			rowspan: 2
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 110,
			fixed: true,
			sortable: true,
			hidden: true,
			halign: 'center',
			align: 'center',
			rowspan: 2
		},
		{
			field: 'WAREID',
			title: 'WAREID',
			hidden: true,
			width: 10,
			rowspan: 2
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 120,
			fixed: true,
			sortable: true,
			hidden: true,
			halign: 'center',
			align: 'center',
			rowspan: 2
		},
		{
			field: 'WARENAME',
			title: '商品名称',
			width: 110,
			fixed: true,
			sortable: true,
			hidden: true,
			halign: 'center',
			align: 'left',
			rowspan: 2
		},
		{
			field: 'wareout',
			title: '销售',
			width: 200,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'refundout',
			title: '退货',
			width: 200,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'THRATE',
			title: '退货率',
			width: 80,
			fixed: true,
			sortable: true,
			halign: 'center',
			align: 'right',
			rowspan: 2,
			formatter: function(value, row, index) {
				if (value == undefined || Number(value) == 0) return "";
				return Number(value).toFixed(2) + "%";
			}
		}], [{
			field: 'AMOUNT0',
			title: '数量',
			width: 100,
			fixed: true,
			sortable: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				if (value == undefined || Number(value) == 0) return "";
				return Number(value);
			}
		},
		{
			field: 'CURR0',
			title: '金额',
			width: 100,
			fixed: true,
			sortable: true,
			halign: 'center',
			align: 'right',
			formatter: function(value, row, index) {
				if (value == undefined || Number(value) == 0) return "";
				return Number(value).toFixed(2);
			}
		},
		{
			field: 'AMOUNT1',
			title: '数量',
			width: 100,
			fixed: true,
			sortable: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				if (value == undefined || Number(value) == 0) return "";
				return Number(value);
			}
		},
		{
			field: 'CURR1',
			title: '金额',
			width: 100,
			fixed: true,
			sortable: true,
			halign: 'center',
			align: 'right',
			formatter: function(value, row, index) {
				if (value == undefined || Number(value) == 0) return "";
				return Number(value).toFixed(2);
			}
		}]],
		onSortColumn: function(sort,order){
			$("#pp").refreshPage();
		},
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid("keyCtr", "");
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("#pp").createPage({
		pageCount: 1,
		//总页码
		current: 1,
		//当前页 
		backFn: function(p) {
			searchpage(p.toString());
		}
	});
	//生成年份 
	var year = myDate.getFullYear();
	var on = year - 5;
	var end = year + 1;
	for (var i = on; i <= end; i++) {
		$("#sprodyear").append("<option value=" + i + ">" + i + "</option'>");
	}
	alerthide();

	//	toggle();
});
//清空表单搜索条件 
function clearAll() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#shzfs').val('0');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
	$('#sprovname').val('');
	$('#sprovid').val('');
	$('#scustid,#scustname').val('');
}
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		dheight = $('#toolbars').height();
		$('#highsearch').val('条件搜索');
		$(".search_btn").hide();
		searchb = false;
	} else {
		searchb = true;
		$('#highsearch').val('条件搜索▲');
		$(".search_btn").show();
	}

}
var footer = [];
//统计销售业绩方法
function searchdata() {
	var mindate = $("#smindate").datebox('getValue'); //开始日期 
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期 
	var houseid = Number($("#shouseid").val()); //店铺
	var custid = $("#scustid").val(); //客户
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	var provid = $("#sprovid").val(); //生产商
	brandid = brandid == "" ? "-1": brandid; //0代表无 -1代表所有
	typeid = typeid == "" ? "-1": typeid;
	provid = provid == "" ? "-1": provid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var hzfs = Number($("#shzfs").val()); //huizongfangshi
	switch (hzfs) {
	case 0:
		$dg.datagrid('showColumn', 'CUSTNAME');
		$dg.datagrid('hideColumn', 'HOUSENAME');
		$dg.datagrid('hideColumn', 'WARENO');
		$dg.datagrid('hideColumn', 'WARENAME');
		options.sortName = "CUSTNAME";
		break;
	case 1:
		$dg.datagrid('hideColumn', 'CUSTNAME');
		$dg.datagrid('showColumn', 'HOUSENAME');
		$dg.datagrid('hideColumn', 'WARENO');
		$dg.datagrid('hideColumn', 'WARENAME');
		options.sortName = "HOUSENAME";
		break;
	case 2:
		$dg.datagrid('hideColumn', 'CUSTNAME');
		$dg.datagrid('hideColumn', 'HOUSENAME');
		$dg.datagrid('showColumn', 'WARENO');
		$dg.datagrid('showColumn', 'WARENAME');
		options.sortName = "WARENO";
		break;
	default:
		break;
	}
	alertmsg(6);
	$.ajax({
		type:
		"POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totalxsthhz",
			mindate: mindate,
			maxdate: maxdate,
			houseid: houseid,
			custid: custid,
			wareid: wareid,
			wareno: wareno,
			brandid: brandid,
			typeid: typeid,
			prodyear: prodyear,
			provid: provid,
			seasonname: seasonname
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					footer = [{
						ROWNUMBER: "合计",
						AMOUNT0: data.TOTALAMT0,
						AMOUNT1: data.TOTALAMT1,
						CURR0: data.TOTALCURR0,
						CURR1: data.TOTALCURR1,
						THRATE: data.THRATE
					}];
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var currentdata = {};
//分页获取
function searchpage(page) {
	alertmsg(6);
	//	&hzfs=[汇总方式]&sortid=[排 序方式]
	var hzfs = $('#shzfs').val();
	if (footer.length == 0){searchdata();return;};
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	currentdata = {
		erpser: "listxsthhz",
		hzfs: hzfs,
		sort: sort,
		order: order,
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
					var totals = data.total;
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#pp_total').html(totals);
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter', footer);
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
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
	<!-- 生产商帮助列表 -->
	<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
</body>
</html>