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
<meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE" />
<title>查询积分储值</title>
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

<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
<input type="button" id="highsearch" class="btn2" value="查询条件▼" onclick="toggle()"> 
<span id="serbtn" class="fr">
<input type="button" class="btn2" value="查找" id="cz" onclick="searchdata();toggle();" /> 
<input type="button" class="btn2" value="清除条件" onclick="resetsearch();" />
</span>
</div>
<div class="fr">
<input type="button" class="btn3" value="设置" onclick="opensetcolumnd('#gg')">
	<span class="sepreate"></span>
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'buy',0);">
</div>
</div>
	<!-- 查询------------ -->
<div class="searchbar" id="searchbar" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input class="easyui-datebox" name="smindate" id="smindate"
					style="width: 163px; height: 28px; margin-left: 10px"/>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input class="easyui-datebox" name="smaxdate" id="smaxdate"
				style="width: 163px; height: 28px; margin-left: 10px"/>
</div>
</div>
<div class="s-box"><div class="title">会员卡号</div>
<div class="text">
<input type="text" name="svipno" id="svipno" style="width:160px;height:25px" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">会员姓名</div>
<div class="text">
<input type="text" name="sguestname" id="sguestname" style="width:160px;height:25px" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">会员类型</div>
<div class="text"><input class="edithelpinput house_help" data-value="#svtid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="svtname" name="svtname"><span  onclick="selectdx_vt('search')"></span> 
					   <input type="hidden" id="svtid" name="svtid">
</div>
</div>
<div class="s-box"><div class="title">发卡店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#sfkhouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="sfkhousename" name="sfkhousename"><span  onclick="selectdx_house('searchfk')"></span> 
					   <input type="hidden" id="sfkhouseid" name="sfkhouseid">
</div>
</div>
<div class="s-box"><div class="title">使用店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selectdx_house('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">建档人</div>
<div class="text">
<input type="text" name="slastop" id="slastop" style="width:160px;height:25px" placeholder="<输入>">
</div>
</div>
</div>
</div>
	</div>
	<table id="gg" style="overflow: hidden; height:9000px;"></table>
	<!-- 分页 -->
	<div
		class="page-bar">
		<div class="page-total">
			共有<span id="pp_total"></span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<script type="text/javascript" charset="UTF-8">
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	var width = document.documentElement.clientWidth; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		width: width,
		height: height - 50
	});
}
var levelid = getValuebyName("GLEVELID");
var pgid = "pg1806";
setqxpublic();
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			searchpage(p);
		}
	});
	$('#gg').datagrid({
		width: '100%',
		idField: 'GUESTID',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		sortName: "",
		sortOrder: "",
		onSortColumn: function(sort,order){
			sortarr = sort.split(",");
			orderarr = order.split(",");
			sortarrs = [];
			for(var i in sortarr){
				var sort = sortarr[i];
				var order = orderarr[i];
				sortarrs.push(sort+" "+order);
			}
			sortlist = sortarrs.join(",");
			if(sortlist==" ") sortlist = ""; 
			$('#pp').refreshPage();
		},
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value == "合计") return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		}]],
		columns: [getpgcolumnarray([{
			field: 'VIPNO',
			title: '会员卡号',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'GUESTNAME',
			title: '会员姓名',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SEX',
			title: '性别',
			width: 60,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'VTNAME',
			title: '会员类型',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'MOBILE',
			title: '移动电话',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HOUSENAME',
			title: '发卡店铺',
			width: 120,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURR0',
			title: '本期储值金额',
			width: 80,
			fixed: true,
			sortable: true,
			formatter: currfm,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'CURR1',
			title: '本期使用金额',
			width: 80,
			fixed: true,
			sortable: true,
			formatter: currfm,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'CENT0',
			title: '本期积分',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'CENT1',
			title: '本期使用积分',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'BALCENT',
			title: '积分余额',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'BALCURR',
			title: '储值余额',
			width: 80,
			fixed: true,
			sortable: true,
			formatter: currfm,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'VALIDDATE',
			title: '有效日期',
			align: 'center',
			halign: 'center',
			width: 100,
			fixed: true,
			sortable: true,
			formatter: function(value, row, index) {
				if (value) return value.substring(0, 10);
			}
		},
		{
			field: 'BIRTHDAY',
			title: '出生日期',
			align: 'center',
			halign: 'center',
			width: 100,
			sortable: true,
			fixed: true
		},
		{
			field: 'CREATEDATE',
			title: '建档日期',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LASTDATE',
			title: '最近更新',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LASTOP',
			title: '最近建档人',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		}])],
		toolbar: "#toolbars",
		pageSize: 20
	}).datagrid("keyCtr");
});
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
//清除高级搜索数据
function resetsearch() {
	$('#sguestname,#svtname,#svtid,#svipno,#shouseid,#shousename,#sfkhouseid,#sfkhousename,#slastop').val("");
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
}
var footer = [];
//获取数据11111111
function searchdata() {
	var mindate = $('#smindate').datebox('getValue');
	var maxdate = $('#smaxdate').datebox('getValue');
	var fkhouseidlist = $('#sfkhouseid').val();
	var houseidlist = $('#shouseid').val();
	var vtidlist = $('#svtid').val();
	var guestname = $('#sguestname').val();
	var vipno = $('#svipno').val();
	var lastop = $('#slastop').val();
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	alertmsg(6);
	$.ajax({
		type: 'POST',
		url: '/skydesk/fybuyjerp',
		data: {
			erpser: 'totalguestbal',
			mindate: mindate,
			maxdate: maxdate,
			vipno: vipno,
			guestname: guestname,
			lastop: lastop,
			vtidlist: vtidlist,
			fkhouseidlist: fkhouseidlist,
			houseidlist: houseidlist
		},
		dataType: 'json',
		success: function(data) {
			try {
				if (valideData(data)) {
					var totals = data.total;
					var WARNING = data.WARNING;
					if (WARNING != '' && WARNING != undefined) {
						alerttext(WARNING);
						setTimeout(function() {
							return;
						},
						3000)
					}
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var currentdata = {};
var sortlist = "";
//分页获取
function searchpage(page) {
	alertmsg(6);
	//	&hzfs=[汇总方式]&sortid=[排 序方式]
// 	var sort = $('#gg').datagrid('options').sort;
// 	var order = $('#gg').datagrid('options').order;
	currentdata = {
		erpser: "listguestbal",
		sortlist: (sortlist==""?"GUESTID asc":sortlist),
		sumlist: "nvl(sum(cent0),0) as cent0,nvl(sum(cent1),0) as cent1,nvl(sum(curr0),0) as curr0,nvl(sum(curr1),0) as curr1,nvl(sum(balcurr),0) as balcurr,nvl(sum(balcent),0) as balcent",
		fieldlist: "a.id,a.cent0,a.cent1,a.curr0,a.curr1,b.validdate,a.guestid,b.guestname,b.sex,b.vipno,b.mobile,b.birthday,b.balcent,b.balcurr,b.houseid,b.createdate,b.lastdate,b.lastop,c.vtname,d.housename",
		rows: pagecount,
		page: page
	};
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType: 'json',
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					data.footer = [{
						ROWNUMBER: "合计",
						BALCURR: data.balcurr,
						BALCENT: data.balcent,
						CENT0: data.cent0,
						CENT1: data.cent1,
						CURR0: data.curr0,
						CURR1: data.curr1
					}];
					var totals = data.total;
					$("#pp_total").html(totals);
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals)/pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('scrollTo', 0);
					$('#gg').datagrid('resize');
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
</script>
<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/VTHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ColumnDialog.jsp"></jsp:include>
</body>
</html>