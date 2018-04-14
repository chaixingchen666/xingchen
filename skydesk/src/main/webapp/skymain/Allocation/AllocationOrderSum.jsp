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
<title>调拨订单完成统计</title>
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
				<input type="button" id="highsearch" class="btn2" value="条件搜索▲" onclick="toggle()">
				<input id="csearch" class="btn1" type="button"  onclick="searchdata();toggle();" value="查找" />
				<input id="qclear" class="btn1" type="button" value="清除内容"  onclick="resetsearch()"/>
	</div>
	</div>
	<!-- 隐藏查询条件 -->
	<div id="hsearch"  class="hsearch-box"  data-enter="searchdata();toggle();">
	<div class="search-box">
	<div class="s-box">
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>	
</div>
<div class="s-box">
	<div class="title">开始日期</div>
	<div class="text"><input id="smindate0" type="text" editable="true"
					class="easyui-datebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input>
</div>
</div>
<div class="s-box">
	<div class="title">结束日期</div>
	<div class="text"><input id="smaxdate0" type="text" editable="true"
					class="easyui-datebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input>
</div>
</div>
<div class="s-box hide">
	<div class="title">发货开始</div>
	<div class="text"><input id="smindate1" type="text" editable="true"
					class="easyui-datebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input>
</div>
</div>
			
<div class="s-box hide">
	<div class="title">发货结束</div>
	<div class="text"><input id="smaxdate1" type="text" editable="true"
					class="easyui-datebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input>
</div>
</div>
<div class="s-box">
	<div class="title">店铺</div>
	<div class="text"><input type="text"
					 class="edithelpinput house_help" name="shousename" data-value="#shouseid"
					id="shousename"  placeholder="<选择或输入>" >
					<span onclick="selectdx_house('search')"></span>
					<input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box">
	<div class="title">货号</div>
	<div class="text"><input type="text"
					 class="edithelpinput" data-value="#swareid" name="swareno"
					id="swareno" placeholder="<选择或输入>" >
					<span onclick="selectware('search')"></span>
					<input type="hidden" id="swareid" value="">
</div>
</div>
<div class="s-box">
	<div class="title">商品名称</div>
	<div class="text"><input type="text" class="wid160 hig25" name="warename" id="warename" maxlength="20"  data-options="required:true"
					placeholder="<输入>" >
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
<div class="s-box">
	<div class="title">品牌</div>
	<div class="text"><input type="text" class="edithelpinput" data-value="#sbrandid"
					 name="sbrandname" id="sbrandname"
					placeholder="<选择或输入>" > <span onclick="selectdx_brand('search')"></span>
					<input type="hidden" id="sbrandid" value="">
</div>
</div>
<div class="s-box">
	<div class="title">类型</div>
	<div class="text">
<input type="text" class="edithelpinput" name="sfullname" id="sfullname"  data-value="#stypeid" data-options="required:true" placeholder="<选择或输入>">
	<span onclick="selectwaretype('search');"></span>
	<input type="hidden" name="stypeid" id="stypeid" value="">
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
</div>
<!-- 订单载入选项 -->
<label class="label_datebj"><input type="checkbox" name="sdatebj" id="sdatebj">按订单统计</label>

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
var pgid = "pg1408";
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
	//统计参数设置
	$("#hzdialogfooter input.selectAll").before($("label.label_datebj"));
	allotparams = getparam({
		pgid: "allotcs",
		usymbol: "allotparams" 
	}); 
	//调拨参数见自定义参数说明.txt
	var datebj = 1;
	if (allotparams == "") {
		setparam({
			pgid: "allotcs",
			usymbol: "allotparams",
			uvalue: defallotparams
		});
		allotparams = defallotparams;
	} else {
		datebj = Number(allotparams.charAt(0));
	}
	if(datebj==0){
		$("#sdatebj").prop("checked",true);
	}else{
		$("#sdatebj").removeProp("checked");
	}
	$("#sdatebj").change(function(){
		var datebj = $(this).prop("checked")?0:1;
		var paramsarr = allotparams.split("");
		paramsarr.splice(0,1,datebj);
		allotparams = paramsarr.join("");
		allotparams += "0000000000";
		setparam({
			pgid: "allotcs",
			usymbol: "allotparams",
			uvalue: allotparams.substring(0, 10)
		});
	});
	
	$("#pp").createPage({
		pageCount: 1,
		//总页码
		current: 1,
		//当前页 
		backFn: function(p) { //http:
			searchpage(p);
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
		toolbar: "#toolbars",
		pageSize: 20,
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
	});
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
},
{
	field: 'AMOUNT1',
	title: '到货数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
},
{
	field: 'AMOUNT2',
	title: '未到数',
	width: 100,
	fixed: true,
	sortable: true,
	align: 'center',
	halign: 'center',
	formatter: amtfm
}]];
//清空表单搜索条件 
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate0,#smaxdate0,#smindate1,#smaxdate1').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#shousename').val('');
	$('#warename').val('');
	$('#shouseid').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('0');
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
	setTimeout(function() {
		changeDivHeight();
	},
	200);
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
//统计库存结构分布方法
function searchdata() {
	var $dg = $("#gg");
	var mindate0 = $("#smindate0").datebox('getValue'); //订单开始日期
	var maxdate0 = $("#smaxdate0").datebox('getValue'); //订单结束日期
	var mindate1 = $("#smindate1").datebox('getValue'); //发货开始日期
	var maxdate1 = $("#smaxdate1").datebox('getValue'); //发货结束日期
	var houseid = $("#shouseid").val(); //店铺
	var warename = $("#swarename").val(); //商品名称
	var colorid = $("#scolorid").val(); //颜色
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var colorid = $("#scolorid").val(); //yanseid
	var sizeid = $("#ssizeid").val(); //尺码id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	typeid = typeid == "" ? "-1": typeid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
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
	currentdata["erpser"] = "listallotorderwc";
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["rows"] = pagecount;
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "totalallotorderwc",
			mindate0: mindate0,
			maxdate0: maxdate0,
// 			mindate1: mindate1,
// 			maxdate1: maxdate1,
			houseidlist: houseid,
			warename: warename,
			wareno: wareno,
			wareid: wareid,
			colorid: colorid,
			sizeid: sizeid,
			brandidlist: brandid,
			typeid: typeid,
			prodyear: prodyear,
			datebj: datebj,
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
//查询库存统计结构列表方法
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
	
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
	<!-- 颜色帮助列表 -->
	<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>