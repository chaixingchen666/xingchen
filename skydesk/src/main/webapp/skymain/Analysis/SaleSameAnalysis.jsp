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
<title>销售同比分析</title>
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
		<input id="csearch" class="btn1" type="button" onclick="searchdata();toggle();" value="查找" />
		<input id="qclear" class="btn1" type="button" value="清除内容" onclick="resetsearch()"/>
	</div>
	<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
</div>
	</div>
	<!-- 隐藏查询条件 -->
	<div id="hsearch" class="searchbar"  data-enter="searchdata();toggle();">
		<!-- 高级搜索 -->
		<div class="search-box">
		<div class="s-box">
		<div class="title">销售开始</div>
		<div class="text"><input id="smindate0" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
		<div class="s-box">
		<div class="title">销售结束</div>
		<div class="text"><input id="smaxdate0" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
			
<div class="s-box">
		<div class="title">同比开始</div>
		<div class="text"><input id="smindate1" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
				
<div class="s-box">
		<div class="title">同比结束</div>
		<div class="text"><input id="smaxdate1" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
<div class="s-box">
		<div class="title">分析项目</div>
		<div class="text"><select id="shzfs" class="sele1">
						<option value="0" selected="selected">品牌</option>
						<option value="1">类型</option>
						<option value="2">季节</option>
						<option value="3">店铺</option>
						<option value="4">客户</option>
						<option value="5">销售类型</option>
				</select>
</div>
</div>
<div class="s-box">
		<div class="title">业务类型</div>
		<div class="text"><select id="sxsid" class="sele1">
						<option value="0">零售</option>
						<option value="1">批发</option>
						<option value="2" selected="selected">所有</option>
				</select>
</div>
</div>
<div class="s-box">
		<div class="title">客户</div>
		<div class="text">
		<input  type="text" class="edithelpinput cust_help" data-value="#scustid" name="scustname" id="scustname" placeholder="<选择或输入>" >
		<span onclick="selectcust('search')"></span>
		<input type="hidden" id="scustid" value="">
</div>
</div>
<div class="s-box">
		<div class="title">货号</div>
		<div class="text">
		<input type="text" class="edithelpinput wareno_help" data-value="#swareid" name="swareno" id="swareno" placeholder="<选择或输入>" ><span onclick="selectware('search')"></span>
					<input type="hidden" id="swareid" value="">
</div>
</div>
				
<div class="s-box">
		<div class="title">品牌</div>
		<div class="text">
		<input type="text" class="edithelpinput brand_help" data-value="#sbrandid" name="sbrandname" id="sbrandname" placeholder="<选择或输入>" >
					<span onclick="selectbrand('search')"></span>
					<input type="hidden" name="sbrandid" id="sbrandid" value="">
</div>
</div>
<div class="s-box">
		<div class="title">类型</div>
		<div class="text">
		<input type="text" class="edithelpinput" name="sfullname" id="sfullname"  data-value="#stypeid" placeholder="<选择或输入>" >
		<span onclick="selectwaretype('search');"></span>
					<input type="hidden" name="stypeid" id="stypeid" value="">
</div>
</div>
<div class="s-box">
		<div class="title">季节</div>
		<div class="text">
		<input type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
                      <span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box">
		<div class="title">生产年份</div>
		<div class="text">
		<select id="sprodyear" class="sele1">
			<option selected="selected" value="">--请选择--</option>						
		</select>
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
var dheight = 80;
var rownumbers = 1;
var pgid="pg1707";
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
	$('#smindate0,#smaxdate0,#smindate1,#smaxdate1').datebox('setValue', myDate.Format('yyyy-MM-dd'));
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
			searchpage(p.toString());
		}
	});
	$('#gg').datagrid({
		idField: 'ID',
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		//rownumbers : true, //显示行数
		singleSelect: true,
		showFooter: true,
		sortName: "BRANDNAME",
		sortOrder: "asc",
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			//halign表示对齐头部
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'BRANDNAME',
			title: '品牌',
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'TYPENAME',
			title: '类型',
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'SEASONNAME',
			title: '季节',
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 150,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'CUSTNAME',
			title: '客户',
			width: 150,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'SALENAME',
			title: '销售类型',
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'AMOUNT0',
			title: '本期数量',
			fieldtype: "G",
			width: 80,
			sortable: true,
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
			field: 'AMOUNT1',
			title: '比较期数量',
			fieldtype: "G",
			width: 80,
			sortable: true,
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
			field: 'CURR0',
			title: '本期金额',
			fieldtype: "N",
			width: 80,
			sortable: true,
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
			field: 'CURR1',
			title: '比较期金额',
			fieldtype: "N",
			width: 80,
			sortable: true,
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
		}]],
		toolbar: "#toolbars",
		pageSize: 20
	});
	alerthide();
});
//清空表单搜索条件 
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate0,#smaxdate0,#smindate1,#smaxdate1').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#shzfs').val('1');
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#sxsid').val('2');
	$('#scustname,#scustid,#swareno,#swareid,#sbrandname,#sbrandid,#sfullname,#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
}
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		dheight = 80;
		$('#highsearch').val('条件搜索');
		$("#csearch").hide();
		$("#qclear").hide();
	} else {
		searchb = true;
		dheight = 250;
		$('#highsearch').val('条件搜索▲');
		$("#csearch").show();
		$("#qclear").show();
	}
	setTimeout(function() {
		changeDivHeight();
	},
	200);
}
//统计销售同比方法
function searchdata() {
	var mindate0 = $("#smindate0").datebox('getValue'); //销售开始日期
	var maxdate0 = $("#smaxdate0").datebox('getValue'); //销售结束日期
	var mindate1 = $("#smindate1").datebox('getValue'); //同比开始日期
	var maxdate1 = $("#smaxdate1").datebox('getValue'); //同比结束日期 
	var xsid = $("#sxsid").val(); //销售方式
	var custid = Number($("#scustid").val()); //客户
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	brandid= brandid==""?"-1":brandid;
	var typeid = $("#stypeid").val(); //类型
	typeid= typeid==""?"-1":typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名  
		data: {
			erpser: "totalxstbfx",
			mindate0: mindate0,
			maxdate0: maxdate0,
			mindate1: mindate1,
			maxdate1: maxdate1,
			custid: custid,
			xsid: xsid,
			wareno: wareno,
			wareid: wareid,
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
					var amount0 = data.totalamt0;
					var amount1 = data.totalamt1;
					var curr0 = data.totalcurr0;
					var curr1 = data.totalcurr1;
					footer = [{
						AMOUNT0: amount0,
						AMOUNT1: amount1,
						CURR0: curr0,
						CURR1: curr1
					}];
					var hzfs = Number($("#shzfs").val()); //分析项目
					var $dg = $("#gg");
					var options = $dg.datagrid("options");
					var sort = "BRANDNAME";
					var hides = [true,true,true,true,true,true];
					if(hzfs==0){
						sort = "BRANDNAME";
						hides[0] = false;
					}
					else if(hzfs==1){
						sort = "TYPENAME";
						hides[1] = false;
					}
					else if(hzfs==2){
						sort = "SEASONNAME";
						hides[2] = false;
					}
					else if(hzfs==3){
						sort = "HOUSENAME";
						hides[3] = false;
					}
					else if(hzfs==4){
						sort = "CUSTNAME";
						hides[4] = false;
					}
					else if(hzfs==5){
						sort = "SALENAME";
						hides[5] = false;
					}
					options.sortName = sort;
					var columns = options.columns[0];
					for(var i = 1;i<=7;i++){
						columns[i].hidden = hides[i-1];
					}
					$dg.datagrid();
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var currentdata = {};
var footer = [];
//查询销售同比列表方法 
function searchpage(page) {
	if(footer.length==0){searchdata();return;};
	var hzfs = $("#shzfs").val(); //分析项目
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	currentdata = {
			erpser: "listxstbfx",
			hzfs: hzfs,
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