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
<title>商品库存报警</title>
<link rel="stylesheet" href="/skydesk/css/icon.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css" type="text/css" />
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
	<input id="csearch" class="btn1" type="button"  onclick="searchdata();toggle();" value="查找" />
	<input id="qclear" class="btn1" type="button" value="清除内容"   onclick="clearAll()"/>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
</div>
</div>
	<!-- 隐藏查询条件 -->
<div id="hsearch"  style="font-size:12px;clear:both"  data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">报警方式</div>
<div class="text"><select id="scxfs" class="sele1">
                    	<option value="0">下限</option>
                        <option value="1">上限</option>
                        <option value="2">上下限</option>
                        <option value="3" selected="selected">所有</option>
                    </select>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input type="text" class="edithelpinput house_help" data-value="#shouseid"
					name="shousename" id="shousename" data-options="required:true" style="width:125px;height:24px;"
					placeholder="<选择或输入>" ><span onclick="selectdx_house('search')" ></span><input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input  type="text"  class="edithelpinput wareno_help" data-value="#swareid"
				name="swareno" id="swareno" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('search')"></span>
				 <input type="hidden" id="swareid" value="0">
</div>
</div>
<div class="s-box"><div class="title">商品名称</div>
<div class="text"><input type="text" name="swarename" id="swarename" maxlength="20" class="wid160 hig25" placeholder="<输入>" >
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

<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectdx_brand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
</div>
</div>
			
<div class="s-box"><div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" data-options="required:true"><span onclick="selectwaretype('search')"></span>
					 <input type="hidden" id="stypeid" value="">
</div>
</div>
<div class="s-box"><div class="title">季节</div>
<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</div>
</div>

<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select id="sprodyear" class="sele1">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>

<div class="s-box"><div class="title">生产商</div>
<div class="text"><input class="edithelpinput provd_help" maxlength="20" style="width:125px;height:24px;" data-value="#sprovid" placeholder="<选择或输入>"
					 type="text" id="sprovname" name="sprovname"><span onclick="selectdx_prov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid"> 
</div>
</div>
<div class="s-box"><div class="title">区位</div>
<div class="text"><input  type="text"  class="edithelpinput area_help" data-value="#sareaid"
				name="sareaname" id="sareaname" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectdx_area('search')"></span>
				 <input type="hidden" id="sareaid" value="">
</div>
</div>
<div class="s-box"><div class="title">货位</div>
<div class="text"><input type="text" name="slocale" id="slocale" maxlength="20" class="wid160 hig25" placeholder="<输入>" >
</div>
</div>
	</div>
</div>
</div>
	<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<input type="hidden" id="total" value="1">
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>

<script type="text/javascript"> 
var searchb = true;
var dheight = 80;
var rownumbers = 1;

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
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#sminssdate,#smaxssdate').datebox('setValue', "");
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
			searchpage(p.toString());
		}
	});
	$('#gg').datagrid({
		idField: 'ID',
		width: '100%',
		height: $('body').height() - 50,
		nowrap: false,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			//halign表示对齐头部
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			title: '图片',
			field: 'IMAGENAME0',
			width: 70,
			align: 'center',
			fixed: true,
			formatter: function(value, row, index) {
				if (value != undefined && value.indexOf('/') >= 0) {
					return '<img bigimg="true" style=\"height: 60px;width: 60px;\" src="' + imageurl + row.IMAGENAME0 + '" />';
				}
				return "";
			}
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WARENAME',
			title: '商品',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},{
			field: 'COLORNAME',
			title: '颜色',
			width: 60,
			fixed: true,
			align: 'center',
			halign: 'center'
		},{
			field: 'SIZENAME',
			title: '尺码',
			width: 60,
			fixed: true,
			align: 'center',
			halign: 'center'
		}]],
		columns: [[{
			field: 'AMOUNT',
			title: '当前库存',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'MINAMT',
			title: '库存下限',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'MAXAMT',
			title: '库存上限',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		}]],
		onSortColumn: function(sortid, ord) {
			sort = sortid;
			order = ord;
			$('#pp').refreshPage();
		},
		pageSize: 20,
		toolbar: "#toolbars"
	});
	alerthide();
	var levelid = Number(getValuebyName("GLEVELID"));
	if (levelid == 1 || levelid == 2 || levelid == 7) {
		$('#shousename').next().hide();
		$('#shousename').attr('readonly', true);
		$('#shouseid').val(Number(getValuebyName('HOUSEID')));
		$('#shousename').val(getValuebyName('HOUSENAME'));
	}
});
//清空表单搜索条件 
function clearAll() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	$('#sminssdate,#smaxssdate').datebox('setValue', "");
	$('#shousename,#shouseid,#swareno,#swareid,#sbrandname,#sbrandid,#sprovid,#sprovname,#sfullname,#stypeid,#sseasonname,#sprodyear,#sminxsday,#smaxxsday,#sminjxrate,#smaxjxrate').val('');
	var levelid = Number(getValuebyName("GLEVELID"));
	if (levelid == 1 || levelid == 2 || levelid == 7) {
		$('#shousename').next().hide();
		$('#shousename').attr('readonly', true);
		$('#shouseid').val(Number(getValuebyName('HOUSEID')));
		$('#shousename').val(getValuebyName('HOUSENAME'));
	}
}
function toggle() {
	$("#hsearch").slideToggle('fast');
	if (searchb) {
		searchb = false;
		dheight = 80;
		$('#highsearch').val('条件搜索');
		$("#csearch").hide();
		$("#qclear").hide();

	} else {
		searchb = true;
		dheight = 218;
		$('#highsearch').val('条件搜索▲');
		$("#csearch").show();
		$("#qclear").show();

	}
	setTimeout(function() {
		$('#gg').datagrid('resize', {
			height: $('body').height() - 50
		});
	},
	200);
}

//分页设置
function searchdata() {
	$('#gg').datagrid('loadData', nulldata);
	var wareno= $("#swareno").val();//货号no
	var noteno= $("#snoteno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var colorid = $("#scolorid").val();//颜色
	var sizeid = $("#ssizeid").val();//尺码
	var brandid = $("#sbrandid").val();//品牌
	var areaid = $("#sareaid").val();//品牌
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var houseid = $("#shouseid").val();//店铺
	var provid = $("#sprovid").val();//店铺
	var locale = $('#slocale').val(); //货位
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType: 'json',
		data: {
			erpser: "totalcxkczy",
			bj: 5,
			houseidlist: houseid,
			wareno: wareno,
			noteno: noteno,
			warename: warename,
			wareid: wareid,
			colorid: colorid,
			sizeid: sizeid,
			brandidlist: brandid,
			areaidlist: areaid,
			providlist: provid,
			typeid: typeid,
			prodyear: prodyear,
			seasonname: seasonname,
			locale: locale
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var sort = "wareid";
var order = "asc";
//分页获取
var currentdata = {};
function searchpage(page) {
	$('#gg').datagrid('loadData', nulldata);
	var cxfs = $('#scxfs').val();
	currentdata = {
			erpser: "listwarn",
			cxfs: cxfs,
			rows: pagecount,
			sort: sort,
			order: order,
			page: page
		};
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
					$("#pp_total").html(totals);
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
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
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
</body>
</html>