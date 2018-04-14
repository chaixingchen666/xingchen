<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>销售业绩统计</title>
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
		<div class="s-box"><div class="title">开始日期</div>
		<div class="text">
		<input type="text" name="smindate" id="smindate" editable="true" required="required" currentText="today" style="width: 162px;height:29px" class="easyui-datebox">
		</div>
		</div>
		<div class="s-box"><div class="title">结束日期</div>
		<div class="text">
		<input type="text" name="smaxdate" id="smaxdate"  editable="true" required="required" currentText="today" style="width: 162px;height:29px" class="easyui-datebox">
		</div>
		</div>
		<div class="s-box"><div class="title">销售人</div>
		<div class="text">
		<input type="text" class="edithelpinput saleman_help" data-value="#epid" id="epname"  placeholder="<选择或输入>" >
		<span onclick="selectemploye('analysis')"></span>
		<input type="hidden" id="epid" value="">
		</div>
		</div>
		<div class="s-box"><div class="title">店铺</div>
		<div class="text">
		<input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>" type="text" id="shousename" name="shousename">
			<span  onclick="selectdx_house('search')"></span> 
		  	<input type="hidden" id="shouseid" name="shouseid">
		</div>
		</div>
		<div class="s-box"><div class="title">货号</div>
		<div class="text">
		<input type="text" id="swareno" class="edithelpinput wareno_help" data-value="#swareid" maxlength="20" placeholder="<选择或输入>" name="swareno" />
			<span onclick="selectware('search')"></span>
			<input type="hidden" id="swareid" name="swareid">
		</div>
		</div>
		<div class="s-box"><div class="title">品牌</div>
		<div class="text">
		<input  type="text" class="edithelpinput brand_help" name="sbrandname" id="sbrandname" data-value="#sbrandid" maxlength="20" placeholder="<选择或输入>">
			<span onclick="selectdx_brand('search')"></span>
			<input type="hidden" name="sbrandid" id="sbrandid" value="">
		</div>
		</div>
		<div class="s-box"><div class="title">类型</div>
		<div class="text">
		<input type="text" class="edithelpinput" id="sfullname" name="sfullname" placeholder="<选择或输入>">
			<span onclick="selectwaretype('search');"></span>
			<input type="hidden" id="stypeid" name="stypeid">
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
		<select name="sprodyear" id="sprodyear"  class="sele1">
		       <option value="">---请选择---</option>
			 </select>
		</div>
		</div>
		<div class="s-box"><div class="title">生产商</div>
		<div class="text">
		<input class="edithelpinput provd_help" id="sprovname" type="text" data-value="#sprovid">
				<span class="s-btn" onclick="selectdx_prov('search')"></span>
						<input type="hidden" id="sprovid" value="">
		</div>
		</div>
		<div class="s-box"><div class="title">区位</div>
<div class="text"><input  type="text"  class="edithelpinput area_help" data-value="#sareaid"
				name="sareaname" id="sareaname" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" >
				<span onclick="selectdx_area('search')"></span>
				 <input type="hidden" id="sareaid" value="">
</div>
</div>
		<div class="s-box"><div class="title">货位</div>
		<div class="text">
		<input class="hig25 wid160" id="slocale" type="text">
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
var dheight = 90;
var rownumbers = 1;
var pgid = "pg1710";
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
	$('#gg').datagrid({
		idField: 'ID',
		width: '100%',
		height: $("#gg").height - 50,
		toolbar: "#toolbars",
		fitColumns: true,
		striped: true,
		//隔行变色
		//rownumbers : true, //显示行数
		singleSelect: true,
		showFooter: true,
		sortName: "EPNAME",
		sortOrder: "asc",
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'EPNAME',
			title: '职员',
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			fieldtype: "G",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'SKC',
			title: 'SKC',
			fieldtype: "G",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'RATEAMT',
			title: '占比',
			fieldtype: "G",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					return nval + "%";
				}
			}
		},
		{
			field: 'CURR',
			title: '销售额',
			fieldtype: "N",
			width: 150,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'RATECUR',
			title: '占比',
			fieldtype: "G",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					return nval + "%";
				}
			}
		},
		{
			field: 'NUM',
			title: '销售次数',
			fieldtype: "G",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'RATEXS',
			title: '连带率',
			fieldtype: "G",
			width: 100,
			sortable: true,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					return nval + "%";
				}
			}
		}]],
		onSortColumn: function(sort,order){
			$("#pp").refreshPage();
		},
		pageSize: 20
	});
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
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
			searchpage(p);
		}
	});
	var levelid = Number(getValuebyName("GLEVELID"));
	if (levelid == 1 || levelid == 2 || levelid == 7) {
		$('#shousename').next().hide();
		$('#shousename').attr('readonly', true);
		$('#shousename').val(getValuebyName('HOUSENAME'));
		$('#shouseid').val(getValuebyName('HOUSEID'));
		if (levelid == 1) {
			$('#epname').next().hide();
			$('#epname').attr('readonly', true);
			$('#epname').val(getValuebyName('EPNAME'));
			$('#epid').val(getValuebyName('EPID'));
		}
	}
	alerthide();
});
//清空表单搜索条件 
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#sortid').val('0');
	$('#sorder').val('asc');
	$('#epname,#epid,#shousename,#shouseid,#swareno,#swareid,#sbrandname').val('');
	$('#sbrandid,#sfullname,#stypeid,#sseasonname,#sprodyear').val('');
	$('#sprovname,#sprovid,#slocale,#sareaid,#sareaname').val('');
	var levelid = Number(getValuebyName("GLEVELID"));
	if (levelid == 1 || levelid == 2 || levelid == 7) {
		$('#shousename').next().hide();
		$('#shousename').attr('readonly', true);
		$('#shousename').val(getValuebyName('HOUSENAME'));
		$('#shouseid').val(getValuebyName('HOUSEID'));
		if (levelid == 1) {
			$('#epname').next().hide();
			$('#epname').attr('readonly', true);
			$('#epname').val(getValuebyName('EPNAME'));
			$('#epid').val(getValuebyName('EPID'));
		}
	}
}
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		dheight = 90;
		$('#highsearch').val('条件搜索');
		$("#csearch").hide();
		$("#qclear").hide();
	} else {
		searchb = true;
		dheight = 236;
		$('#highsearch').val('条件搜索▲');
		$("#csearch").show();
		$("#qclear").show();
	}
	setTimeout(function() {
		changeDivHeight();
	},
	200);
}
var footer =[];
//统计销售业绩方法
function searchdata() {
	var mindate = $("#smindate").datebox('getValue'); //开始日期 
	var maxdate = $("#smaxdate").datebox('getValue'); //结束日期 
	var houseid = $("#shouseid").val(); //店铺
	var epid = Number($("#epid").val()); //销售人
	//var custid = $("#ucustid").val();//客户
	var wareno = $("#swareno").val(); //货号no
	var wareid = $("#swareid").val(); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	var provid = $("#sprovid").val(); //生产商
	typeid = typeid==""?"-1":typeid;//0代表无 -1代表所有
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var areaid = $('#sareaid').val(); //区位
	var locale = $('#slocale').val(); //货位
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名  
		data: {
			erpser: "totalxsyjtj",
			mindate: mindate,
			maxdate: maxdate,
			houseidlist: houseid,
			brandidlist: brandid,
			areaidlist: areaid,
			epid: epid,
			wareid: wareid,
			wareno: wareno,
			typeid: typeid,
			prodyear: prodyear,
			providlist: provid,
			locale: locale,
			seasonname: seasonname
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totalamt = data.totalamt;
					var totalcurr = data.totalcurr;
					footer = [{
						ROWNUMBER: "合计",
						AMOUNT: totalamt,
						CURR: totalcurr
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
//查询销售业绩方法 
function searchpage(page) {
	if(footer.length==0){searchdata();return;};
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	alertmsg(6);
	currentdata = {
			erpser: "listxsyjtj",
			sort: sort,
			order: order,
			rows: pagecount,
			page: page
		}
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
	<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpFiles/AreaHelpList.jsp"></jsp:include>
	<!-- 销售员帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<!-- 生产商帮助列表 -->
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
</body>
</html>