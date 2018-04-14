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
<title>经销商库存表</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<div class="toolbar" id="toolbars">
<div class="toolbar_box1">
<div class="fl">
<input type="button" class="btn2" id="qc" name="qc" value="查询条件▲" onclick="toggle()">
 <span id="serbtn" class="fr">
<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchdata();toggle();">
<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空条件" onclick="resetsearch()">
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
</div>
</div>
	<!-- 隐藏查询条件 -->
<div id="hsearch" style="background-color: white;clear:both;width:100%;font-size: 12px;"  data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input id="smindate" type="text" editable="true" style="width:162px;height:25px" class="easyui-datebox" required="required" currentText="today">			
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" type="text" editable="true" style="width:162px;height:25px" class="easyui-datebox" required="required" currentText="today">
			
</div>
</div>
<div class="s-box"><div class="title">汇总方式</div>
<div class="text"><select id="shzfs" style="width:164px;height:25px">
	<option value="0" selected="selected">商品</option>
	<option value="1">商品，颜色</option>
	<option value="2">商品，颜色，尺码</option>
</select>
</div>
</div>

<div class="s-box"><div class="title">经销商</div>
<div class="text"><input type="text"
					class="edithelpinput buyer_help" data-value="#saccid"
					name="scompany" id="scompany" 
					data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectbuyer('search')" ></span>
					<input type="hidden" id="saccid" value="">
</div>
</div>

<div class="s-box"><div class="title">货号</div>
<div class="text"><input type="text"
					class="edithelpinput hig25" name="swareno" data-value="#swareid" id="swareno" 
					data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('search')" ></span>
					<input type="hidden" id="swareid" value="">
</div>
</div>
<div class="s-box"><div class="title">类型</div>
<div class="text"><input type="text" class="edithelpinput" name="sfullname" id="sfullname" data-value="#stypeid"  data-options="required:true"
					readonly><span onclick="selectwaretype('search')"></span><input type="hidden"
					id="stypeid" value="">
</div>
</div>

<div class="s-box"><div class="title">颜色</div>
<div class="text"><input type="text" class="edithelpinput hig25" name="scolorname" id="scolorname"
				   data-options="required:true"  readonly><span onclick="selectwarecolor($('#swareid').val(),'analysis')"></span>
				   <input type="hidden" id="scolorid" value="">
</div>
</div>

<div class="s-box"><div class="title">尺码</div>
<div class="text"><input type="text" class="edithelpinput hig25" name="ssizename" id="ssizename"
					 data-options="required:true" readonly><span onclick="selectsize($('#swareid').val(),'analysis')"></span> <input type="hidden"
					id="ssizeid" value="">
</div>
</div>

<div class="s-box"><div class="title">季节</div>
<div class="text"><input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
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
</div>
</div>
	</div>
	<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>

<div id="jxsjxbd" title="经销商进销比汇总" data-options="modal:true" style="width: 100%; height: 100%" class="easyui-dialog" closed="true">
<input type="hidden" id="buyaccid"  value="">
<div class="toolbar" id="jxsjxb_toolbar">
<div class="toolbar_box1">
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#jxsjxbpp_total','#jxsjxbt',currentdata1,'api',-1,'经销商库存明细表');">
</div>
</div>
</div>
<table id="jxsjxbt"></table>
<div class="page-bar">
	<div class="page-total">共有<span id="jxsjxbpp_total">0</span>条记录</div>
	<div id="jxsjxb_pp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<script type="text/javascript"> 
var searchb = true;
var dheight = 80;
var rownumbers = 1;
var pgid = "pg1709";
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
	//生成年份 
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var on = year - 5;
	var end = year + 1;
	for (var i = on; i <= end; i++) {
		$("#sprodyear").append("<option value=" + i + ">" + i + "</option'>");
	}
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
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
		toolbar: '#toolbars',
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		multiSort: true,//允许多列排序
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			rowspan: 2,
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 120,
			sortable: true,
			fixed: true,
			rowspan: 2,
			align: 'center',
			halign: 'center',
		},
		{
			field: 'WARENAME',
			title: '商品',
			width: 120,
			sortable: true,
			fixed: true,
			rowspan: 2,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'COLORNAME',
			title: '颜色',
			width: 60,
			sortable: true,
			fixed: true,
			rowspan: 2,
			hidden: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SIZENAME',
			title: '尺码',
			width: 60,
			sortable: true,
			fixed: true,
			rowspan: 2,
			hidden: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'QC',
			title: '期初',
			width: 160,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'JH',
			title: '进货',
			width: 160,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'XS',
			title: '销售',
			width: 160,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'AMOUNTJC',
			title: '结存',
			fieldtype: "G",
			width: 80,
			sortable: true,
			fixed: true,
			rowspan: 2,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val;
			}
		},
		{
			field: 'RATEWC',
			title: '售罄率',
			fieldtype: "G",
			width: 80,
			sortable: true,
			fixed: true,
			rowspan: 2,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val.toString() + "%";
			}
		}], [{
			field: 'AMOUNTQC',
			title: '数量',
			fieldtype: "G",
			width: 60,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val;
			}
		},
		{
			field: 'CURRQC',
			title: '金额',
			fieldtype: "N",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val.toFixed(2);
			}
		},{
			field: 'AMOUNTRK',
			title: '数量',
			fieldtype: "G",
			width: 60,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val;
			}
		},
		{
			field: 'CURRRK',
			title: '金额',
			fieldtype: "N",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val.toFixed(2);
			}
		},
		{
			field: 'AMOUNTXS',
			title: '数量',
			fieldtype: "G",
			width: 60,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val;
			}
		},
		{
			field: 'CURRXS',
			title: '金额',
			fieldtype: "N",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				else return val.toFixed(2);
			}
		}]],
		onDblClickRow: function(rowIndex, rowData) {
			if (rowData.WAREID != undefined) {
				$('#jxsjxbd').dialog({
					title: rowData.WARENO + "," + rowData.WARENAME + "经销商库存明细"
				});
				$('#jxsjxbd').dialog('open');
				$('#buywareid').val(rowData.WAREID);
				searchdata1(rowData.WAREID);
			} else {
				alerttext("商品ID无效！");
			}
		},
		onSortColumn: function(sort,order){
			sortarr = sort.split(",");
			orderarr = order.split(",");
			sortarrs = [];
			for(var i in sortarr){
				var sort = sortarr[i];
				var order = orderarr[i];
				if(sort == "SIZENAME") sort = "SIZENO"; 
				sortarrs.push(sort+" "+order);
			}
			sortlist = sortarrs.join(",");
			if(sortlist==" ") sortlist = ""; 
			$('#pp').refreshPage();
		},
		pageSize: 20
	});
	$("#jxsjxb_pp").createPage({
		pageCount: 1,
		//总页码
		current: 1,
		//当前页 
		backFn: function(p) {
			rownumbers = p;
			searchpage1(p.toString());
		}
	});
	$('#jxsjxbt').datagrid({
		idField: 'ID',
		width: '100%',
		height: $('body').height() - 110,
		fitColumns: true,
		striped: true,
		//隔行变色
		nowrap: false,
		singleSelect: true,
		showFooter: true,
		sortName: "NOTEDATE",
		sortOrder: "asc",
		toolbar: "#jxsjxb_toolbar",
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			rowspan: 2,
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#jxsjxb_pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'REMARK',
			title: '摘要',
			rowspan: 2,
			width: 50,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'NOTEDATE',
			title: '日期',
			rowspan: 2,
			width: 140,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'NOTENO',
			title: '单据号',
			width: 130,
			rowspan: 2,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'RK',
			title: '入库',
			width: 160,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'CK',
			title: '出库',
			width: 160,
			halign: 'center',
			align: 'center',
			colspan: 2
		},
		{
			field: 'AMOUNT2',
			title: '结存',
			fieldtype: "G",
			width: 80,
			rowspan: 2,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				return val;
			}
		},
		{
			field: 'WLREMARK',
			title: '往来单位',
			width: 200,
			sortable: true,
			fixed: true,
			rowspan: 2,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'BUYACCNAME',
			title: '经销商',
			width: 120,
			sortable: true,
			fixed: true,
			rowspan: 2,
			align: 'left',
			halign: 'center'
		}], [{
			field: 'AMOUNT0',
			title: '数量',
			fieldtype: "G",
			width: 60,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				return val;
			}
		},
		{
			field: 'CURR0',
			title: '进货额',
			fieldtype: "N",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				return val.toFixed(2);
			}
		},
		{
			field: 'AMOUNT1',
			title: '数量',
			fieldtype: "G",
			width: 60,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				return val;
			}
		},
		{
			field: 'CURR1',
			title: '销售额',
			fieldtype: "N",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Number(value);
				if (val == 0 || isNaN(val)) return "";
				return val.toFixed(2);
			}
		}]],
		onDblClickRow: function(rowIndex, rowData) {
			var pgno = rowData.NOTETYPE;
			if (pgno != '' && pgno != undefined) {
				var noteno = rowData.NOTENO;
				var accid = Number(rowData.ACCID);
				opendetaild(pgno, noteno, accid);
			}
		},
		onSortColumn: function(sort,order){
			$("#jxsjxb_pp").refreshPage();
		},
		pageSize: 20
	});
	alerthide();
});
//清空表单搜索条件 
function resetsearch() {
	$('#shzfs').val('0');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#scustname').val('');
	$('#saccid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname,#scompany').val('');
	$('#sprodyear,#scolorid,#scolorname,#ssizeid,#ssizename').val('');
	$('#smindate,#smaxdate').datebox('setValue', top.getservertime()); // 设置日期输入框的值
}
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		$('#highsearch').val('条件搜索');
		$("#serbtn").hide();

	} else {
		searchb = true;
		$('#highsearch').val('条件搜索▲');
		$("#serbtn").show();
	}
	setTimeout(function() {
		$('#gg').datagrid('resize', {
			height: $('body').height() - 50
		});
	},
	200)

}
var footer = [];
var footer1 = [];
var currdatetime = "";
//查询统计经销商进销存汇总 方法
function searchdata() {
	var mindate = $("#smindate").datebox('getValue'); //开始日期
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期
	var buyeraccid = $("#saccid").val(); //经销商
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var typeid = $("#stypeid").val(); //类型
	var prodyear = $("#sprodyear").val(); //生产年份
	var sizeid = $("#ssizeid").val(); //尺码
	var colorid = $("#scolorid").val(); //颜色
	var seasonname = $("#sseasonname").val(); //季节
	var sortid = $("#ssortid").val(); //季节
	var order = $("#sorder").val(); //季节
	var hzfs = Number($("#shzfs").val()); //汇总方式
	var $dg = $("#gg");
	var option = $dg.datagrid("options");
	switch (hzfs) {
	case 0:
		option.sortName= "WARENO";
		option.sortOrder= "asc";
		$dg.datagrid("hideColumn","COLORNAME");
		$dg.datagrid("hideColumn","SIZENAME");
		sortlist = "wareno asc";
		break;
	case 1:
		option.sortName= "WARENO,COLORNAME";
		option.sortOrder= "asc,asc";
		$dg.datagrid("showColumn","COLORNAME");
		$dg.datagrid("hideColumn","SIZENAME");
		sortlist = "WARENO asc,COLORNAME asc";
		break;
	case 2:
		option.sortName= "WARENO,COLORNAME,SIZENAME";
		option.sortOrder= "asc,asc,asc";
		$dg.datagrid("showColumn","COLORNAME");
		$dg.datagrid("showColumn","SIZENAME");
		sortlist = "WARENO asc,COLORNAME asc,SIZENO asc";
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
			erpser: "totalbuyerwarefx",
			timeout: 5*60*1000,
			mindate: mindate,
			maxdate: maxdate,
			buyeraccid: buyeraccid,
			wareno: wareno,
			wareid: wareid,
			sizeid: sizeid,
			colorid: colorid,
			typeid: typeid,
			prodyear: prodyear,
			seasonname: seasonname
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					currdatetime = data.CURRDATETIME;
					footer = [{
						ROWNUMBER: "合计",
						AMOUNTQC: data.TOTALAMTQC,
						CURRQC: data.TOTALCURRQC,
						AMOUNTRK: data.TOTALAMTRK,
						CURRRK: data.TOTALCURRRK,
						AMOUNTXS: data.TOTALAMTXS,
						CURRXS: data.TOTALCURRXS,
						AMOUNTJC: data.TOTALAMTJC,
						RATEWC: data.RATEWC
					}];
					var options = $dg.datagrid("options");
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
var sortlist = "";
//查询经销商进销存汇总列表方法
function searchpage(page) {
	if(footer.length==0){searchdata();return;}
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName; //汇总项目
// 	var order = options.sortOrder; //汇总项目
	var hzfs = Number($("#shzfs").val()); //汇总方式
	currentdata = {
			erpser: "listbuyerwarefx",
			hzfs: hzfs,
			sortlist: sortlist,
			rows: pagecount,
			page: page
		};
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data:currentdata,
		dataType: 'json',
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totals = data.total;
					$("#pp_total").html(totals);
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter', footer);
					$('#gg').datagrid('scrollTo', 0);
					$('#gg').datagrid('resize');
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//查询统计经销商进销存汇总 方法
function searchdata1(wareid) {
	var mindate = $("#smindate").datebox('getValue'); //开始日期
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期
	var wareno = $("#swareno").val(); //货号no
	var typeid = $("#stypeid").val(); //类型
	var prodyear = $("#sprodyear").val(); //生产年份
	var sizeid = $("#ssizeid").val(); //尺码
	var colorid = $("#scolorid").val(); //颜色
	var seasonname = $("#sseasonname").val(); //季节
	var accid = $("#saccid").val(); //经销商
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totaljxcmxbuyer",
			currdatetime: currdatetime,
			mindate: mindate,
			maxdate: maxdate,
			wareid: wareid,
			wareno: wareno,
			wareid: wareid,
			buyeraccid: accid,
			sizeid: sizeid,
			colorid: colorid,
			typeid: typeid,
			prodyear: prodyear,
			seasonname: seasonname
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					footer1 = [{
						ROWNUMBER: "合计",
						AMOUNT0: data.TOTALAMT0,
						CURR0: data.TOTALCURR0,
						AMOUNT1: data.TOTALAMT1,
						CURR1: data.TOTALCURR1,
						AMOUNT2: data.BALAMT
					}];
					searchpage1(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var currentdata1 = {};
//查询经销商进销存汇总列表方法
function searchpage1(page) {
	alertmsg(6);
	var $dg = $("#jxsjxbt");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	currentdata1 = {
		erpser: "listjxcmxbuyer",
		order: order,
		sort: sort,
		rows: pagecount,
		page: page
	};
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType: 'json',
		data: currentdata1,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totals = data.total;
					$("#jxsjxbpp_total").html(totals);
					$("#jxsjxb_pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#jxsjxbt').datagrid('loadData', data);
					$('#jxsjxbt').datagrid('reloadFooter', footer1);
					$('#jxsjxbt').datagrid('scrollTo', 0);
					$('#jxsjxbt').datagrid('resize');
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
</script>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 颜色帮助列表 -->
	<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
	<!-- 尺码帮助列表 -->
	<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 经销商帮助列表 -->
	<jsp:include page="../HelpList/BuyerHelpList.jsp"></jsp:include>
	<!-- 单据明细帮助列表 -->
<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
</body>
</html>