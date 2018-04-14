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
<title>客户订单完成统计</title>
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
<div class="s-box hide"><div class="title">销售开始</div>
<div class="text">
<input class="easyui-datebox" name="smindate1" id="smindate1" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box hide"><div class="title">销售结束</div>
<div class="text">
<input class="easyui-datebox" name="smaxdate1" id="smaxdate1" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input class="edithelpinput cust_help wid145 hig25" data-value="#scustid" id="scustname" name="scustname" maxlength="20" placeholder="<选择或输入>"
					type="text" ><span onclick="selectdx_cust('search')"></span>
					<input type="hidden" id="scustid" name="scustid">
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
<div class="s-box"><div class="title">颜色</div>
<div class="text"><input type="text" class="edithelpinput hig25" data-value="#scolorid" name="scolorname" id="scolorname" placeholder="<选择>" >
<span onclick="selectwarecolor($('#swareid').val(),'search')"></span>
<input type="hidden" id="scolorid" value="">
</div>
</div>

<div class="s-box"><div class="title">尺码</div>
<div class="text"><input type="text" class="edithelpinput hig25" data-value="#ssizeid" name="ssizename" id="ssizename" placeholder="<选择>">
<span onclick="selectsize($('#swareid').val(),'search')"></span> 
<input type="hidden" id="ssizeid" value="">
</div>
</div>
<div class="s-box"><div class="title">生产商</div>
<div class="text"><input class="edithelpinput provd_help" type="text" id="sprovname" name="sprovname" placeholder="<选择或输入>"
						data-value="#sprovid" maxlength="20"><span onclick="selectdx_prov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid">
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
<div class="s-box hide"><div class="title">区域</div>
<div class="text">
<input type="text" name="sareaname" id="sareaname" style="width:160px;height:25px" placeholder="<输入>">
</div>
</div>
<!-- 订单载入选项 -->
<label class="label_datebj"><input type="checkbox" name="sdatebj" id="sdatebj">按订单统计</label>

</div>
		</div>
	</div>
	<table id="gg" style="overflow: hidden; height:90000000px;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<script type="text/javascript" charset="UTF-8">
var searchb = true;
var pgid = "pg1306";
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

$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate0,#smaxdate0,#smindate1,#smaxdate1').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setyear();
	//统计参数设置
	$("#hzdialogfooter input.selectAll").before($("label.label_datebj"));
	wareoutparams = getparam({
		pgid: "wareoutcs",
		usymbol: "wareoutparams" 
	}); 
	//调拨参数见自定义参数说明.txt
	var datebj = 1;
	if (wareoutparams == "") {
		setparam({
			pgid: "wareoutcs",
			usymbol: "wareoutparams",
			uvalue: defwareoutparams
		});
		wareoutparams = defwareoutparams;
	} else {
		datebj = Number(wareoutparams.charAt(0));
	}
	if(datebj==0){
		$("#sdatebj").prop("checked",true);
	}else{
		$("#sdatebj").removeProp("checked");
	}
	$("#sdatebj").change(function(){
		var datebj = $(this).prop("checked")?0:1;
		var paramsarr = wareoutparams.split("");
		paramsarr.splice(0,1,datebj);
		wareoutparams = paramsarr.join("");
		wareoutparams += "0000000000";
		setparam({
			pgid: "wareoutcs",
			usymbol: "wareoutparams",
			uvalue: wareoutparams.substring(0, 10)
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
var hzcolumn = [[{
	field: 'AMOUNT0',
	title: '订货数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},{
	field: 'AMOUNT1',
	title: '已发数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'AMOUNT2',
	title: '未发数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
}]];
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
	$('#scustname').val('');
	$('#scustid').val('');
	$('#shousename').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#swarename').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sseasonname').val('');
	$('#ssizeid,#ssizename').val('');
	$('#scolorid,#scolorname').val('');
	$('#sprodyear,#sareaname').val('');
	$('#sprovid,#sprovname').val('');
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
function setdatagrid($dg, sortName, columns) {
	sortlist = sortName + " asc";
	$dg.datagrid({
		sortName: sortName,
		sortOrder: "asc",
		frozenColumns: [columns],
		columns: hzcolumn
	}).datagrid('resize');
}
var footer = [];
//获取数据11111111111111
function searchdata() {
	var $dg = $("#gg");
	var mindate0 = $("#smindate0").datebox('getValue'); //开始日期 
	var maxdate0 = $("#smaxdate0").datebox('getValue'); //结束日期 
	var mindate1 = $("#smindate1").datebox('getValue'); //开始日期 
	var maxdate1 = $("#smaxdate1").datebox('getValue'); //结束日期 
	var custid = $("#scustid").val(); //客户
	var wareno = $("#swareno").val(); //货号no
	var warename = $("#swarename").val(); //商品名称
	var wareid = $("#swareid").val(); //货号id
	var colorid = $("#scolorid").val(); //颜色id
	var sizeid = $("#ssizeid").val(); //尺码id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	typeid = typeid == "" ? "-1": typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var provid = $("#sprovid").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var locale = $('#slocale').val();
	var areaname = $('#sareaname').val();
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
	currentdata = {};
	currentdata["erpser"] = "listcustorderwc";
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["rows"] = pagecount;
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totalcustorderwc",
			mindate0: mindate0,
			maxdate0: maxdate0,
// 			mindate1: mindate1,
// 			maxdate1: maxdate1,
			custidlist: custid,
			providlist: provid,
			wareid: wareid,
			colorid: colorid,
			sizeid: sizeid,
			wareno: wareno,
			warename: warename,
			brandidlist: brandid,
			typeid: typeid,
			locale: locale,
			areaname: areaname,
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
	//	&hzfs=[汇总方式]&sortid=[排 序方式]
	if (footer.length == 0) {
		searchdata();
		return;
	}
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
					var totals = data.total;
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#total').val(totals);
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
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpFiles/CustHelpList.jsp"></jsp:include>
	<!-- 员工（销售人）帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>