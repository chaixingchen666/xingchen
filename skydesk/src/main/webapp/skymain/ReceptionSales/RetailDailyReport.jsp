<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>零售结账日报</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>"type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
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
	<input id="qclear" class="btn1" type="button" value="清除内容" onclick="clearAll()" />
</div>
<div class="fr">
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 隐藏查询条件 -->
<div id="hsearch" style="background-color: white;width:100%; font-size: 12px;clear:both;"  data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input id="smindate" type="text" editable="true" 
					class="easyui-datetimebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" type="text" editable="true" class="easyui-datetimebox" currentText="today"
					style="width: 164px; height: 25px"></input>
</div>
</div>
<div class="s-box"><div class="title">销售人</div>
<div class="text">
<input type="text" class="edithelpinput saleman_help" id="sepname" data-value="#sepid"	placeholder="<选择或输入>">
	<span onclick="selectemploye('search')"></span> 
	<input type="hidden" id="sepid" value="">
</div>
</div>

<div class="s-box"><div class="title" color="#ff7900">*&nbsp;店铺</div>
<div class="text">
<input type="text" class="edithelpinput house_help" data-value="#shouseid" name="shousename" id="shousename" placeholder="<选择或输入>">
<span onclick="selecthouse('search')"></span> 
<input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text">
<input type="text" class="edithelpinput" name="swareno" id="swareno" data-value="#swareid" placeholder="<选择或输入>" >
<span onclick="selectware('search')"></span>
<input type="hidden" id="swareid" value="">
</div>
</div>

<div class="s-box"><div class="title">收银员</div>
<div class="text">
<input type="text" class="edithelpinput saleman_help" id="slastop" placeholder="<选择或输入>">
<span onclick="selectemploye('lastop')"></span> 
</div>
</div>
<div class="s-box"><div class="title" color="#ff7900">*&nbsp;供应商</div>
	<div class="text"><input class="edithelpinput provd_help" type="text" id="sprovname" name="sprovname" placeholder="<选择或输入>"
							 data-value="#sprovid" maxlength="20"><span onclick="selectdx_prov('search')"></span>
		<input type="hidden" id="sprovid" name="sprovid">
	</div>
</div>
<div class="s-box">
	<div class="title">类型</div>
	<div class="text">
		<input class="edithelpinput" id="sfullname" data-value="#stypeid" maxlength="20" type="text" placeholder="<选择或输入>">
		<span onclick="selectwaretype('search')"></span><input type="hidden" id="stypeid" name="stypeid"/>
	</div>
</div>
</div>
</div>
</div>
<div style="margin:5px">
	<table id="gg" style="overflow: hidden;"></table>
</div>
	<div class="page-bar">
		<div class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code" ></div>
	</div>
<script type="text/javascript">
var searchb = true;
var epno = getValuebyName("EPNO");
var levelid = Number(getValuebyName("GLEVELID"));
var epname = getValuebyName('EPNAME');
var pgid = "pg1202";
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
	var year = myDate.getFullYear();
	var hour = myDate.getHours();
	var mi = myDate.getMinutes();
	$('#smindate').datetimebox({
		value: myDate.Format('yyyy-MM-dd') + ' 00:00',
		required: true,
		showSeconds: false
	});
	$('#smaxdate').datetimebox({
		value: myDate.Format('yyyy-MM-dd') + ' 23:59',
		required: true,
		showSeconds: false
	});
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
	$('#gg').datagrid({
		idField: 'ID',
		height: $('body').height() - 50,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		sortName: "NOTENO",
		sortOrder: "asc",
		onDblClickRow: function(rowIndex, rowData) {
			var noteno = rowData.NOTENO;
			if (noteno != '' && noteno != undefined) {
				var notetype = noteno.substring(0, 2);
				opendetaild(notetype, noteno);
			}
		},
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			align: 'center',
			width: 140,
			sortable: true,
			fixed: true
		},
		{
			field: 'NOTEDATE',
			title: '日期',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 120,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'TOTALAMT',
			title: '总数量',
			width: 60,
			fieldtype: "G",
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'TOTALCURR',
			title: '总金额',
			fieldtype: "N",
			width: 80,
			fixed: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid("keyCtr", "");
	alerthide();
	if(levelid==7||levelid==1||levelid==2){
		if(levelid==7){
			$('#slastop').val(epname);
			$('#slastop').next().hide();
			$('#slastop').attr('readonly',true);
		}
		$('#shousename').next().hide();
		$('#shousename').attr('readonly',true);
		$('#shousename').val(getValuebyName('HOUSENAME'));
		$('#shouseid').val(getValuebyName('HOUSEID'));
	}
	searchdata();
});
//清空表单搜索条件 
function clearAll() {
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var hour = myDate.getHours();
	var mi = myDate.getSeconds();
	$('#epname').val('');
	$('#epid').val('');
	$('#smindate').datetimebox({
		value: myDate.Format('yyyy-MM-dd') + ' 00:00',
		required: true,
		showSeconds: false
	});
	$('#smaxdate').datetimebox({
		value: myDate.Format('yyyy-MM-dd') + " 23:59",
		required: true,
		showSeconds: false
	});
	$('#shousename,#swareid,#swareno,#shouseid,#slastop,#sepname,#sepid,#sprovname,#sprovid,#sfullname,#stypeid').val('');
	if(levelid==7||levelid==1||levelid==2){
		if(levelid==7){
			$('#lastop').val(epname);
		}
		$('#shousename').val(getValuebyName('HOUSENAME'));
		$('#shouseid').val(getValuebyName('HOUSEID'));
	}
};

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

var sumsql;
var amtfm = function(value, row, index) {
	if (!isNaN(Number(value)) && Number(value) != 0) return Number(value);
	else return '';
};
var currfm = function(value, row, index) {
	if (!isNaN(Number(value)) && Number(value) != 0) return Number(value).toFixed(2);
	else return '';
};
var footer = [];
function searchdata() {
	$('#gg').datagrid('loadData', nulldata);
	var mindate = $("#smindate").datebox('getValue'); //开始日期
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期
	var houseid = Number($("#shouseid").val()); //店铺
	var wareid = $("#swareid").val(); //货号
	var salemanid = Number($("#sepid").val()); //销售人
	var lastop = $("#slastop").val(); //收银员
	if (levelid == 7) lastop = epname;
	var providerId = $('#sprovid').val();
	var typeId = $('#stypeid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名  
		async: false,
		data: {
			erpser: "totalwaresalesy",
			mindate: mindate,
			maxdate: maxdate,
			houseid: houseid,
			wareid: wareid,
			salemanid: salemanid,
			lastop: lastop,
            providlist: providerId,
			typeid: typeId
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					footer = [{
						ROWNUMBER: "序号",
						TOTALAMT: data.totalamt,
						TOTALCURR: data.totalcurr,
						FREECURR: data.freecurr,
						JFCURR: data.jfcurr,
						CHECKCURR: data.checkcurr,
						ZPAYCURR: data.zpaycurr,
						CHANGECURR: data.changecurr,
						XJPAYCURR: data.xjpaycurr
					}];
					sumsql = data.sumsql;
					var payrows = data.columns;
					var columns = [{
						field: 'FREECURR',
						title: '免单金额',
						fieldtype: "N",
						width: 80,
						fixed: true,
						sortable: true,
						align: 'right',
						halign: 'center',
						formatter: currfm
					},
					{
						field: 'JFCURR',
						title: '积分抵扣',
						fieldtype: "N",
						width: 60,
						fixed: true,
						sortable: true,
						align: 'center',
						halign: 'center',
						formatter: currfm
					},
					{
						field: 'CHECKCURR',
						title: '结算金额',
						fieldtype: "N",
						width: 80,
						fixed: true,
						sortable: true,
						align: 'right',
						halign: 'center',
						formatter: currfm
					}];
					for (var i in payrows) {
						var payrow = payrows[i];
						var payid = payrow.PAYID;
						var payname = payrow.PAYNAME;
						if (payname == "现金") {
							columns.push({
								field: 'PAYCURR' + payid,
								title: payname,
								fieldtype: "N",
								width: 80,
								// 								hidden: true,
								fixed: true,
								sortable: true,
								align: 'right',
								halign: 'center',
								formatter: currfm
							});
						} else {
							columns.push({
								field: 'PAYCURR' + payid,
								title: payname,
								fieldtype: "N",
								width: 80,
								fixed: true,
								sortable: true,
								align: 'right',
								halign: 'center',
								formatter: currfm
							});
						}
						footer[0]["PAYCURR" + payid] = payrow.CURR;
					}
					columns.push({
						field: 'CHANGECURR',
						title: '找零',
						fieldtype: "N",
						width: 80,
						fixed: true,
						sortable: true,
						align: 'right',
						// 						hidden:true,
						halign: 'center',
						formatter: currfm
					},
					{
						field: 'XJPAYCURR',
						title: '实收现金',
						fieldtype: "N",
						width: 80,
						fixed: true,
						sortable: true,
						align: 'right',
						halign: 'center',
						formatter: currfm
					},
					{
						field: 'OPERANT',
						title: '收银员',
						width: 80,
						sortable: true,
						fixed: true,
						align: 'center',
						halign: 'center'
					},
					{
						field: 'SALEMANLIST',
						title: '销售员',
						width: 80,
						sortable: true,
						fixed: true,
						align: 'center',
						halign: 'center'
					},
					{
						field: 'VIPLIST',
						title: '会员',
						width: 130,
						fixed: true,
						sortable: true,
						align: 'center',
						halign: 'center'
					},
					{
						field: 'REMARK',
						title: '摘要',
						width: 130,
						fixed: true,
						sortable: true,
						align: 'center',
						halign: 'center'
					});
					//加载数据
					$('#gg').datagrid({
						columns: [columns]
					});
					currentdata = {
						erpser: "listwaresalesy",
						sumsql: sumsql,
						rows: pagecount
					};
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var currentdata = {};
function searchpage(page) {
	if (!currentdata.sumsql||currentdata.sumsql.length==0) {
		alertmsg("请点击搜索！");
		return;
	};
	var $dg = $("#gg");
	var sort = $dg.datagrid("options").sortName;
	var order = $dg.datagrid("options").sortOrder;
	currentdata["sort"] = sort;
	currentdata["order"] = order;
	currentdata["page"] = page;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#pp_total').html(data.total);
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter', footer);
					$('#gg').datagrid('resize');
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
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 销售员帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<!-- 单据明细帮助列表 -->
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
	<!-- 供应商帮助列表 -->
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
</body>
</html>