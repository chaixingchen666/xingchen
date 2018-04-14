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
<title>采购订单完成统计</title>
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
		<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>	
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input class="easyui-datebox" name="smindate0" id="smindate0" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input class="easyui-datebox" name="smaxdate0" id="smaxdate0" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box hide"><div class="title">入库开始</div>
<div class="text">
<input class="easyui-datebox" name="smindate1" id="smindate1" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box hide"><div class="title">入库结束</div>
<div class="text">
<input class="easyui-datebox" name="smaxdate1" id="smaxdate1" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title" color="#ff7900">*&nbsp;供应商</div>
<div class="text"><input class="edithelpinput provd_help" data-value="#sprovid" id="sprovname" name="sprovname" maxlength="20" placeholder="<选择或输入>"
					type="text" ><span onclick="selectdx_prov('search')"></span>
					<input type="hidden" id="sprovid" name="sprovid">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input type="text" id="swareno" class="edithelpinput wareno_help" data-value="#swareid" maxlength="20" placeholder="<选择或输入>"
						name="swareno"/><span onclick="selectware('search')"></span>
						<input type="hidden" id="swareid" name="swareid">
</div>
</div>	
<div class="s-box"><div class="title">商品名称</div>
<div class="text">
<input type="text" class="hig25 wid160" id="swarename" name="swarename" placeholder="<输入>" />
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text" class="edithelpinput brand_help" name="sbrandname" id="sbrandname" 
                      data-value="#sbrandid" maxlength="20" data-options="required:true" placeholder="<选择或输入>">
                      <span onclick="selectdx_brand('search')"></span>
				       <input type="hidden" name="sbrandid" id="sbrandid" value="">
</div>
</div>
<div class="s-box"><div class="title">季节</div>
<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select name="sprodyear" id="sprodyear" class="sele1">
  <option value="">---请选择---</option>
  </select>   
</div>
</div>
<!-- 订单载入选项 -->
<label class="label_datebj"><input type="checkbox" name="sdatebj" id="sdatebj">按订单统计</label>
</div>
		</div>
	</div>
	<table id="gg" style="overflow: hidden; height:90000000px;"></table>
	<!-- 分页 -->
	<div
		class="page-bar">
		<div
			class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<script type="text/javascript" charset="UTF-8">
var searchb = true;
var pgid = "pg1106";
//var epno =getValuebyName("epno");
setqxpublic();
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

$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate0,#smaxdate0,#smindate1,#smaxdate1').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setyear();
	//统计参数设置
	$("#hzdialogfooter input.selectAll").before($("label.label_datebj"));
	cgparams = getparam({
		pgid: "cgcs",
		usymbol: "cgparams" 
	}); 
	//采购参数见自定义参数说明.txt
	var datebj = 1;
	if (cgparams == "") {
		setparam({
			pgid: "cgcs",
			usymbol: "cgparams",
			uvalue: defcgparams
		});
		cgparams = defcgparams;
	} else {
		datebj = Number(cgparams.charAt(0));
	}
	if(datebj==0){
		$("#sdatebj").prop("checked",true);
	}else{
		$("#sdatebj").removeProp("checked");
	}
	$("#sdatebj").change(function(){
		var datebj = $(this).prop("checked")?0:1;
		var paramsarr = cgparams.split("");
		paramsarr.splice(0,1,datebj);
		cgparams = paramsarr.join("");
		cgparams += "0000000000";
		setparam({
			pgid: "cgcs",
			usymbol: "cgparams",
			uvalue: cgparams.substring(0, 10)
		});
	});
	
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
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
		singleSelect: true,
		showFooter: true,
		nowrap: false,
		toolbar: "#toolbars",
		columns: [],
		onSortColumn: function(sort, order) {
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
		}
	}).datagrid("keyCtr");
	alerthide();
});

function setyear() {
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var start = year - 5;
	var end = year + 1;
	for (var i = start; i <= end; i++) {
		$("#sprodyear").append("<option value=" + i + ">" + i + "</option'>");
	}
}
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate0,#smaxdate0,#smindate1,#smaxdate1').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shzfs').val('0');
	$('#sorder').val('1');
	$('#sprovname').val('');
	$('#sprovid').val('');
	$('#shousename').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#swarename').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
}
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
var validehzsj = function(ishz, hzname, hzsjname) {
	if (ishz && hzname.indexOf("商品") == -1 && (hzname.indexOf("颜色") > -1 || hzname.indexOf("尺码") > -1)) {
		alerttext("汇总项：商品必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}
	return true;
}
var hzcolumns = [{
	field: 'AMOUNT0',
	title: '订货数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'AMOUNT1',
	title: '已入库数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'AMOUNT2',
	title: '未入库数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
}];
function setdatagrid($dg, sortName, columns) {
	sortlist = sortName + " asc";
	columns = columns.concat(hzcolumns);  
	$dg.datagrid({
		sortName: sortName,
		sortOrder: "asc",
		columns: [columns]
	}).datagrid('resize');
}
//获取数据11111111111111
function searchdata() {
	var mindate0 = $("#smindate0").datebox('getValue'); //开始日期 
	var maxdate0 = $("#smaxdate0").datebox('getValue'); //结束日期 
	var mindate1 = $("#smindate1").datebox('getValue'); //开始日期 
	var maxdate1 = $("#smaxdate1").datebox('getValue'); //结束日期 
	var wareno = $("#swareno").val(); //货号no
	var warename = $("#swarename").val(); //商品名称
	var wareid = $("#swareid").val(); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	var provid = $("#sprovid").val(); //供应商
	typeid = typeid == "" ? "-1": typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var locale = $('#slocale').val();
	var ntid = $('#sntid').val();
	var datebj = 1;
	if($("#sdatebj").prop("checked"))
		datebj = 0;
	var grouplist = "";
	var hzlist = $('#shzlist').data('hzlist');
	if (!hzlist) {
		alerttext("请选择汇总方式");
		selectzhfs();
		return;
	}
	if ($('#skyhzcheckbj').prop('checked')) {
		if (hzlist.grouplist.length == 0) {
			alerttext("请选择汇总项目");
			return;
		}
	}
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totalprovorderwc",
			mindate0: mindate0,
			maxdate0: maxdate0,
// 			mindate1: mindate1,
// 			maxdate1: maxdate1,
			providlist: provid,
			wareid: wareid,
			wareno: wareno,
			warename: warename,
			brandidlist: brandid,
			typeid: typeid,
			locale: locale,
			prodyear: prodyear,
			datebj: datebj,
			ntid: ntid,
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
						AMOUNT2: data.TOTALAMT2
					}];
					currentdata = {};
					currentdata["erpser"] = "listprovorderwc";
					currentdata["grouplist"] = hzlist.grouplist;
					currentdata["rows"] = pagecount;
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//分页获取
function searchpage(page) {
	alertmsg(6);
	if (footer.length == 0) searchdata();
	currentdata["sortlist"] = sortlist;
	currentdata["page"] = page;
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
					$("#pp_total").html(data.total);
					$("#pp").setPage({
						pageCount: Math.ceil(Number(data.total) / pagecount),
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
</script>
	<!-- 供应商帮助列表 -->
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
	<!-- 员工（销售人）帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>