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
<title>供需平衡分析</title>
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
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>	
<div class="s-box"><div class="title">货号</div>
		<div class="text"><input  type="text"  class="edithelpinput" data-value="#swareid"
				name="swareno" id="swareno" style="width:145px;height:24px;" placeholder="<选择或输入>" ><span onclick="selectware('search')"></span>
				 <input type="hidden" id="swareid" value="">
</div>
</div>		
<div class="s-box"><div class="title">类型</div>
		<div class="text"><input  type="text"  class="helpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" placeholder="<选择或输入>" ><span onclick="selectwaretype('search')"></span>
					 <input type="hidden" id="stypeid" value="">
</div>
</div>		
<div class="s-box"><div class="title">品牌</div>
		<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname" placeholder="<选择或输入>" >
				<span onclick="selectbrand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
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
		<select id="sprodyear"style="width: 164px; height: 25px">
		<option selected="selected" value="">--请选择--</option>						
		</select>
</div>
</div>		
</div>
</div>
	</div>
	<table id="gg" style="overflow: hidden;height:900px"></table>
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<input type="hidden" id="total" value="1">
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<script type="text/javascript"> 
var searchb = true;
var pgid = "pg1509";
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
			searchpage(p);
		}
	});
	$('#gg').datagrid({
		idField: 'KEYID',
		width: '100%',
		height: $("body").height() - 50,
		toolbar: "#toolbars",
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		sortName: "WARENO",
		sortOrder: "asc",
		columns: [],
		pageSize: 20,
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
		}
		
	});
	alerthide();
});
var hzcolumn = [[{
	field: 'CDAMT',
	title: '采购订货',
	width: 120,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'KCAMT',
	title: '库存',
	width: 120,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'KDAMT',
	title: '客户订货',
	width: 120,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'XQAMT',
	title: '可用数',
	width: 120,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
}]];
//清空表单搜索条件 
function resetsearch() {
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
}
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
var validehzsj = function(ishz,hzname,hzsjname){
	if(ishz&&hzname.indexOf("商品")==-1&&(hzname.indexOf("颜色")>-1||hzname.indexOf("尺码")>-1)){
		alerttext("汇总项：商品必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}
	return true;
}
function setdatagrid($dg,sortName,columns){
	sortlist = sortName +" asc";
	$dg.datagrid({
		sortName: sortName,
		sortOrder: "asc",
		frozenColumns: [columns],
		columns: hzcolumn
	}).datagrid('resize');
}
var footer = [];
//查询统计供需平衡 方法 
function searchdata() {
	var $dg = $("#gg");
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	brandid = brandid==""?"-1":brandid;
	typeid = typeid==""?"-1":typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var grouplist = "";
	var hzlist = $('#shzlist').data('hzlist');
	if(!hzlist){
		alerttext("请选择汇总方式");
		selectzhfs();
		return;
	}
	if($('#skyhzcheckbj').prop('checked')){
		if(hzlist.grouplist.length==0){
			alerttext("请选择汇总项目");
			return;
		}
	}
	currentdata = {};
	currentdata["erpser"] = "listgxbalance";
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["rows"] = pagecount;
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totalgxbalance",
			wareid: wareid,
			wareno: wareno,
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
					footer = [{
						ROWNUMBER: "合计",
						CDAMT: data.cdamt,
						KCAMT: data.kcamt,
						KDAMT: data.kdamt,
						XQAMT: data.xqamt,
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
//查询供需平衡方法 
function searchpage(page) {
	if(footer.length==0){searchdata();return;}
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
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>