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
<title>查询储值明细</title>
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
<input type="button" class="btn2" value="查找" id="cz" onclick="searchpage(1);toggle();" /> 
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
<div class="searchbar" id="searchbar" data-enter="searchpage(1);toggle();">
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
<div class="s-box"><div class="title">付款方式</div>
<div class="text"><input type="text" class="edithelpinput pw_help" data-value="#spayid" maxlength="20" placeholder="<输入或选择>" id="spayname"
					name="spayname"/> <span onclick="Paywaydata('search')"></span>
					<input type="hidden" id="spayid" name="spayid"> 
</div>
</div>
<div class="s-box">
	<div class="title">使用类型</div>
	<div class="text"><select id="sfs" class="sele1">
						<option value="0">储值</option>						
						<option value="1">消费</option>						
						<option value="2" selected>所有</option>						
				</select>
	</div>
</div>
<div class="s-box"><div class="title">收银员</div>
<div class="text">
<input type="text" name="soperant" id="soperant" style="width:160px;height:25px" placeholder="<输入>">
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
var pgid = "pg1808";
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
// 			sortarr = sort.split(",");
// 			orderarr = order.split(",");
// 			sortarrs = [];
// 			for(var i in sortarr){
// 				var sort = sortarr[i];
// 				var order = orderarr[i];
// 				sortarrs.push(sort+" "+order);
// 			}
// 			sortlist = sortarrs.join(",");
// 			if(sortlist==" ") sortlist = ""; 
			$('#pp').refreshPage();
		},
		onDblClickRow: function(rowIndex, rowData) {
			var noteno = rowData.XSNOTENO;
			if (noteno != '' && noteno != undefined) {
				var notetype = noteno.substring(0, 2);
				opendetaild(notetype, noteno);
			}
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
			field: 'MOBILE',
			title: '移动电话',
			width: 100,
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
			field: 'FKHOUSENAME',
			title: '发卡店铺',
			width: 120,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'NOTEDATE',
			title: '使用日期',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HOUSENAME',
			title: '使用店铺',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'ZY',
			title: '使用类别',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURR0',
			title: '金额',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYNAME',
			title: '付款方式',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'PAYCURR',
			title: '付款金额',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'XSNOTENO',
			title: '业务单号',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'REMARK',
			title: '备注',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'OPERANT',
			title: '收银员',
			width: 70,
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
	$('#sguestname,#svtname,#svtid,#svipno,#shouseid,#shousename,#spayid,#spayname,#sfkhouseid,#sfkhousename,#slastop,#soperant').val("");
	$("#sfs").val(2);
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
}
var currentdata = {};
var sortlist = "";
//分页获取
function searchpage(page) {
	alertmsg(6);
	var mindate = $('#smindate').datebox('getValue');
	var maxdate = $('#smaxdate').datebox('getValue');
	var fkhouseidlist = $('#sfkhouseid').val();
	var houseidlist = $('#shouseid').val();
	var vtidlist = $('#svtid').val();
	var guestname = $('#sguestname').val();
	var vipno = $('#svipno').val();
	var lastop = $('#slastop').val();
	var operant = $('#soperant').val();
	var payid = Number($("#spayid").val());
	var fs = $("#sfs").val();
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName;
	var order = options.sortOrder;
	currentdata = {
		erpser: "guestcurrlist",
		mindate: mindate,
		maxdate: maxdate,
		vipno: vipno,
		guestname: guestname,
		fs: fs,
		payid: payid,
		lastop: lastop,
		operant: operant,
		vtidlist: vtidlist,
		fkhouseidlist: fkhouseidlist,
		houseidlist: houseidlist,
		cxfs: 1,
// 		sortlist: (sortlist==""?"GUESTID asc":sortlist),
		sort: (sort==""?"id":sort),
		order: (order==""?"desc":order),
		sumlist: "nvl(sum(curr0),0) as curr0",
		fieldlist: "a.id,a.operant,a.remark,a.xsnoteno,b.housename,a.notedate,e.housename as fkhousename,f.vtname,d.mobile,d.sex,d.vipno,d.guestname,a.paycurr,c.payname",
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
						PAYCURR: data.totalpaycurr,
						CURR0: data.totalcurr0
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
<jsp:include page="../HelpList/PayWayHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ColumnDialog.jsp"></jsp:include>
<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
</body>
</html>