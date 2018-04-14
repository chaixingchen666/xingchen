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
<title>销售库存分析</title>
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
		<!-- 高级搜索 -->
<div class="search-box">
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>	
		<div class="s-box">
		<div class="title">开始日期</div>
		<div class="text"><input id="smindate" type="text"  editable="true" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
<div class="s-box">
		<div class="title">结束日期</div>
		<div class="text"><input id="smaxdate" type="text"  editable="true" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input type="text" class="edithelpinput house_help" data-value="#shouseid"
					name="shousename" id="shousename" data-options="required:true" style="width:125px;height:24px;"
					placeholder="<选择或输入>" ><span onclick="selectdx_house('search')" ></span><input type="hidden" id="shouseid" value="">
</div>
</div>				
<div class="s-box">
		<div class="title">货号</div>
		<div class="text"><input type="text"
					class="edithelpinput" data-value="#swareid"
					name="swareno" id="swareno"  placeholder="<选择或输入>" ><span onclick="selectware('search')" ></span>
					<input type="hidden" id="swareid" value="">
</div>
</div>
				
<div class="s-box">
		<div class="title">品牌</div>
		<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname" style="width:145px;height:24px;" placeholder="<选择或输入>" >
				<span onclick="selectdx_brand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
</div>
</div>

<div class="s-box">
		<div class="title">类型</div>
		<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" placeholder="<选择或输入>" ><span onclick="selectwaretype('search')"></span>
				 <input type="hidden" id="stypeid" value="">
</div>
</div>
<div class="s-box">
		<div class="title">季节</div>
		<div class="text"><input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
                      <span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box">
		<div class="title">生产年份</div>
		<div class="text"><select id="sprodyear" class="sele1">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>
<div class="s-box">
<label class="fr"><input id="sonlyxs" type="checkbox" checked>只查询有销售商品</label>
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
var pgid = "pg1713";
//var epno =getValuebyName("epno");
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
	$('#mindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#maxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$("input:radio[name='pai1']").eq(0).attr("checked", true); //单选框选中
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
		idField: 'ID',
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色/*  */
		singleSelect: true,
		showFooter: true,
		nowrap: false,
		toolbar: "#toolbars",
		sortName: "WARENO",
		sortOrder: "asc",
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
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear,#shouseid,#shousename').val('');
	$("#sonlyxs").removeProp("checked");
}
var validehzsj = function(ishz, hzname, hzsjname) {
	if (ishz && hzname.indexOf("商品") == -1 && (hzname.indexOf("颜色") > -1 || hzname.indexOf("尺码") > -1)) {
		alerttext("汇总项：商品必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}
	return true;
}
var hjcolumns = [{
	field: 'XSAMTHJ',
	title: '销量',
	width: 60,
	fixed: true,
	sortable: true,
	fieldtype: "G",
	align: 'center',
	halign: 'center',
	formatter: amtfm,
	styler: amtstyle
},
{
	field: 'XSCURRHJ',
	title: '销售额',
	width: 100,
	fixed: true,
	sortable: true,
	fieldtype: "G",
	align: 'center',
	halign: 'center',
	formatter: amtfm,
	styler: amtstyle
},
{
	field: 'KCAMTHJ',
	title: '库存',
	width: 60,
	fixed: true,
	fieldtype: "N",
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm,
	styler: currstyle
}];
function setdatagrid($dg, sortName, columns) {
	sortlist = sortName + " asc";
	columns[0].rowspan = 2;
	columns.push({
		field: 'COLHJ',
		title: '合计',
		width: 160,
		fixed: true,
		sortable: true,
		colspan: 3,
		align: 'center',
		halign: 'center'
	});
	$dg.datagrid({
		sortName: sortName,
		sortOrder: "asc",
		frozenColumns: [columns, hjcolumns]
	}).datagrid('resize');
}
var footer0 = {ROWNUMBER: "合计"};
var sumlist = "";
//查询统计销售库存分析方法
function searchdata() {
	var mindate = $('#smindate').datebox('getValue'); //开始时间
	var maxdate = $('#smaxdate').datebox('getValue'); //结束时间
	var hzfs = $("#shzfs").val(); //汇总方式
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	var houseid = $("#shouseid").val(); //店铺
	var typeid = $("#stypeid").val(); //类型
	typeid = typeid == "" ? "-1": typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var onlyxs = 0; //查询有销售商品
	if($("#sonlyxs").prop("checked")) onlyxs =  1;
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
			erpser: "totalxskczy",
			mindate: mindate,
			maxdate: maxdate,
			hzfs: hzfs,
			wareno: wareno,
			wareid: wareid,
			brandidlist: brandid,
			houseidlist: houseid,
			typeid: typeid,
			prodyear: prodyear,
			seasonname: seasonname,
			onlyxs: onlyxs
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 	
			try {
				if (valideData(data)) {
					var _columns = data.columns;
					var columns1 = [];
					var columns2 = [];
					footer0["XSAMTHJ"] = data.totalxsamt;
					footer0["XSCURRHJ"] = data.totalxscurr;
					footer0["KCAMTHJ"] = data.totalkcamt;
					for (var i = 0; i < _columns.length; i++) {
						var row = _columns[i];
						var title = row.housename;
						if (title.length > 10) title = title.substring(0, 10) + "<br />" + title.substring(10, title.length);
						columns1.push({
							field: "HOUSENAME" + row.houseid,
							title: title,
							width: 160,
							colspan: 3,
							halign: "center"
						});
						columns2.push({
							field: "XSAMT" + row.houseid,
							title: "销量",
							width: 60,
							fixed: true,
							fieldtype: "G",
							formatter: amtfm2,
							styler: amtstyle,
							halign: "center",
							align: "center"
						},
						{
							field: "XSCURR" + row.houseid,
							title: "销售额",
							width: 100,
							fixed: true,
							fieldtype: "G",
							formatter: currfm2,
							styler: currstyle,
							halign: "center",
							align: "right"
						},
						{
							field: "KCAMT" + row.houseid,
							title: "库存",
							width: 60,
							fixed: true,
							fieldtype: "N",
							formatter: amtfm2,
							styler: amtstyle,
							halign: "center",
							align: "center"
						});
						footer0["XSAMT" + row.houseid] = row.xsamt;
						footer0["XSCURR" + row.houseid] = row.xscurr;
						footer0["KCAMT" + row.houseid] = row.kcamt;
					}
					$('#gg').datagrid({
						columns: [columns1, columns2]
					}).datagrid('resize');
					currentdata = {};
					currentdata["erpser"] = "listxskczy";
					currentdata["grouplist"] = hzlist.grouplist;
					currentdata["sumlist"] = data.sumlist;
					currentdata["rows"] = pagecount;
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var sortlist = "";
var currentdata={};
//查询销售库存分析列表方法
function searchpage(page) {
	if (currentdata["erpser"] == undefined){searchdata();return;}
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
		dataType: 'json ',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totals = data.total;
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: page
					});
					$('#pp_total').html(totals);
					data.footer = [footer0];
					$('#gg').datagrid('loadData', data);
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
<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>