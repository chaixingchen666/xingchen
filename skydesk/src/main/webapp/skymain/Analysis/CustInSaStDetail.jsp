<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>客户进销存明细账</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<script type="text/javascript" src="/skydesk/js/datagrid-groupview.js?ver=<%=tools.DataBase.VERSION%>"></script>
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
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
		<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input class="easyui-datebox" name="smindate" id="smindate" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input class="easyui-datebox" name="smaxdate" id="smaxdate" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">汇总方式</div>
<div class="text"><select style="width: 163px; height: 28px" id="shzfs"
						name="shzfs">
							<option value="0" selected="selected">单据</option>
							<option value="1">商品</option>
							<option value="2">商品+颜色</option>
							<option value="3">商品+颜色+尺码</option>
					</select>
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input class="edithelpinput cust_help" data-value="#scustid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="scustname" name="scustname"><span onclick="selectdx_cust('search')"></span> 
					   <input type="hidden" id="scustid" name="scustid">
</div>
</div>
<div class="s-box"><div class="title">生产商</div>
<div class="text">
<input type="hidden" id="sprovid" value="">
<input class="edithelpinput provd_help" id="sprovname" type="text" data-value="#sprovid">
<span class="s-btn" onclick="selectprov('search')"></span>
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
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();  
}  
function changeDivHeight(){               
  var height = document.documentElement.clientHeight;//获取页面可见高度 
  var width = document.documentElement.clientWidth;//获取页面可见高度 
	$('#gg').datagrid('resize',{
	width:width,
  	height:height-50
  });
}
setqxpublic();
var pgid ="pg1718";
var noteColumn = [[
	{
		field : 'RK',
		title : '入库',
		width : 150,
		fixed: true,
		colspan: 3,
		align : 'center',
		halign : 'center'
	},{
		field : 'CK',
		title : '出库',
		width : 150,
		fixed: true,
		colspan: 3,
		align : 'center',
		halign : 'center'
	},{
		field : 'JC',
		title : '结存',
		width : 100,
		fixed: true,
		colspan: 3,
		formatter: currfm,
		styler: currstyle,
		align : 'right',
		halign : 'center'
	},{
		field : 'WLREMARK',
		title : '往来单位及客户',
		width : 150,
		fixed: true,
		rowspan: 2,
		align : 'center',
		halign : 'center'
	}
],[{
	field : 'AMOUNT0',
	title : '数量',
	width : 50,
	fieldtype: "G",
	fixed: true,
	formatter: amtfm,
	styler: currstyle,
	align : 'center',
	halign : 'center'
},{
	field : 'CURRRK',
	title : '金额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURR0',
	title : '成本额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'AMOUNT1',
	title : '数量',
	width : 50,
	fieldtype: "G",
	fixed: true,
	formatter: amtfm,
	styler: currstyle,
	align : 'center',
	halign : 'center'
},{
	field : 'CURRXS',
	title : '金额',
	width : 100,
	fieldtype: "N",
	fixed: true,
	formatter: currfm,
	styler: currstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURR1',
	title : '成本额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'AMOUNT2',
	title : '数量',
	width : 100,
	fieldtype: "G",
	fixed: true,
	formatter: amtfm,
	styler: currstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'PRICE2',
	title : '成本价',
	width : 80,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURR2',
	title : '成本额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
}]];
var wareColumn = [[
	{
		field : 'RK',
		title : '入库',
		width : 150,
		fixed: true,
		colspan: 5,
		align : 'center',
		halign : 'center'
	},{
		field : 'CK',
		title : '出库',
		width : 150,
		fixed: true,
		colspan: 5,
		align : 'center',
		halign : 'center'
	},{
		field : 'JC',
		title : '结存',
		width : 100,
		fixed: true,
		colspan: 3,
		formatter: currfm,
		styler: currstyle,
		align : 'right',
		halign : 'center'
	},{
		field : 'WLREMARK',
		title : '往来单位及客户',
		width : 150,
		fixed: true,
		rowspan: 2,
		align : 'center',
		halign : 'center'
	}
],[{
	field : 'AMOUNT0',
	title : '数量',
	width : 50,
	fieldtype: "G",
	fixed: true,
	formatter: amtfm,
	styler: currstyle,
	align : 'center',
	halign : 'center'
},{
	field : 'PRICERK',
	title : '单价',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURRRK',
	title : '金额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'PRICE0',
	title : '成本价',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURR0',
	title : '成本额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'AMOUNT1',
	title : '数量',
	width : 50,
	fieldtype: "G",
	fixed: true,
	formatter: amtfm,
	styler: currstyle,
	align : 'center',
	halign : 'center'
},{
	field : 'PRICEXS',
	title : '单价',
	width : 100,
	fieldtype: "N",
	fixed: true,
	formatter: currfm,
	styler: currstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURRXS',
	title : '金额',
	width : 100,
	fieldtype: "N",
	fixed: true,
	formatter: currfm,
	styler: currstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'PRICE1',
	title : '成本价',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURR1',
	title : '成本额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'AMOUNT2',
	title : '数量',
	width : 100,
	fieldtype: "G",
	fixed: true,
	formatter: amtfm,
	styler: currstyle,
	align : 'center',
	halign : 'center'
},{
	field : 'PRICE2',
	title : '成本价',
	width : 80,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
},{
	field : 'CURR2',
	title : '成本额',
	width : 100,
	fieldtype: (allowinsale==1?"N":"*"),
	fixed: true,
	formatter: jjcurrfm,
	styler: jjcurrstyle,
	align : 'right',
	halign : 'center'
}]];
$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	var year = myDate.getFullYear();	
    var start = year-5;
	var end = year+1;
	for(var i = start;i <= end ; i++)
	{
	    $("#sprodyear").append("<option value="+i+">"+i+"</option'>");
	}
	$('#pp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
			searchpage(p.toString());
		}
	});
	$('#gg').datagrid(
			{
				idField : 'ROWNUMBER',
				width : '100%',
				height : $('body').height() - 50,
				fitColumns : true,
				striped : true, //隔行变色/*  */
				singleSelect : true,
				showFooter : true,
				nowrap: false,
				toolbar :"#toolbars",
				frozenColumns : [ [ {
					field : 'ROWNUMBER',
					title : '序号',
					width : 50,
					fixed : true,
					align : 'center',
					halign : 'center',
					formatter : function(value, row, index) {
						var val = Number(value);
						if(value==undefined)
							return "";
						else if(isNaN(val))
							return value;
						return val;
					}
				},{
					field : 'NOTEDATE',
					title : '日期',
					width : 100,
					fixed: true,
					align : 'center',
					halign : 'center'
				},{
					field : 'REMARK',
					title : '摘要',
					width : 100,
					fixed: true,
					align : 'center',
					halign : 'center'
				},{
					field : 'NOTENO',
					title : '单据号',
					width : 120,
					fixed: true,
					align : 'center',
					halign : 'center'
				},{
					field : 'WARENO',
					title : '商品',
					width : 100,
					fixed: true,
					hidden: true,
					align : 'center',
					halign : 'center',
					formatter : function(value, row, index) {
						if(value!=undefined)
							return value+"<br/>"+row.WARENAME;
						return "";
					}
				},{
					field : 'COLORNAME',
					title : '颜色',
					width : 100,
					fixed: true,
					hidden: true,
					align : 'center',
					halign : 'center'
				},{
					field : 'SIZENAME',
					title : '尺码',
					width : 100,
					fixed: true,
					hidden: true,
					align : 'center',
					halign : 'center'
				}] ],
				columns: noteColumn,
				onSortColumn: function(sort, order){
					$('#gg').datagrid('options').sortName= sort;
					$('#gg').datagrid('options').sortOrder = order;
					$('#pp').refreshPage();
				},
				onDblClickRow: function(index,row){
					var noteno = row.NOTENO;
					if(noteno){
						var pgno = noteno.substring(0,2);
						opendetaild(pgno,noteno);
					}
				},
			}).datagrid("keyCtr");
	alerthide();
});
function resetsearch(){
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shzfs').val('0');
	$('#scustname').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#swarename').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sprovid,#sprovname').val('');
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
	setTimeout(function(){
		$('#gg').datagrid('resize',{height:$('body').height()-50});
	}, 200);
}
var footer0 = {};
function searchdata(){
	var mindate = $("#smindate").datebox('getValue');//开始日期 
	var maxdate = $("#smaxdate").datebox('getValue');//结束日期 
	var custidlist = $("#scustid").val();//客户
	var wareno= $("#swareno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var brandidlist = $("#sbrandid").val();//品牌
	var provid = $("#sprovid").val();//生产商
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var hzfs = $('#shzfs').val();
	alertmsg(6,5*60*1000);
	footer0 = {};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data: {
			erpser: "totaljxckhmx",
			cxfs: hzfs,
			mindate: mindate,
			maxdate: maxdate,
			custidlist : custidlist,
			wareid : wareid,
			provid: provid,
			wareno: wareno,
			warename: warename,
			brandidlist: brandidlist,
			typeid : typeid,
			prodyear : prodyear,
			seasonname : seasonname
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					footer0["REMARK"] = "期末";
					footer0["AMOUNT0"] = data.TOTALAMT0;//入库数量
					footer0["CURRRK"] = data.TOTALCURRRK;//入库金额
					footer0["CURR0"] = data.TOTALCURR0;//入库成本额
					footer0["AMOUNT1"] = data.TOTALAMT1;//销售数量
					footer0["CURRXS"] = data.TOTALCURRXS;//销售金额
					footer0["CURR1"] = data.TOTALCURR1;//销售成本额
					footer0["AMOUNT2"] = data.BALAMT;//数量结存
					footer0["CURR2"] = data.BALCURR;//成本额结存
					var hzfs = Number($('#shzfs').val());
					var $dg = $('#gg');
					var options = $dg.datagrid('options');
					switch (hzfs) {
					case 0:
						$dg.datagrid({
							columns: noteColumn
						});
						$dg.datagrid("hideColumn","WARENO");
						$dg.datagrid("hideColumn","COLORNAME");
						$dg.datagrid("hideColumn","SIZENAME");
						break;
					case 1:
						$dg.datagrid({
							columns: wareColumn
						});
						$dg.datagrid("showColumn","WARENO");
						$dg.datagrid("hideColumn","COLORNAME");
						$dg.datagrid("hideColumn","SIZENAME");
						break;
					case 2:
						$dg.datagrid({
							columns: wareColumn
						});
						$dg.datagrid("showColumn","WARENO");
						$dg.datagrid("showColumn","COLORNAME");
						$dg.datagrid("hideColumn","SIZENAME");
						break;
					case 3:
						$dg.datagrid({
							columns: wareColumn
						});
						$dg.datagrid("showColumn","WARENO");
						$dg.datagrid("showColumn","COLORNAME");
						$dg.datagrid("showColumn","SIZENAME");
						break;
					default:
						break;
					}
					$('#gg').datagrid('resize');
					searchpage(1);
				}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
//分页获取
var currentdata = {};
function searchpage(page) {
	alertmsg(6);
	//	&hzfs=[汇总方式]&sortid=[排 序方式]
	var hzfs = $('#shzfs').val();
	var options = $('#gg').datagrid('options');
	currentdata = {
			erpser : "listjxckhmx",
			cxfs: hzfs,
			rows: pagecount,
			page : page
		};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					data.footer = [footer0];
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('scrollTo',0);
					$('#gg').datagrid('resize');
					var totals = data.total;
					$("#pp").setPage({
			        	pageCount: Math.ceil(Number(totals)/ pagecount),
			        	current: Number(page)
				 	});
					$('#pp_total').html(data.total);
			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}	

	</script>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpFiles/CustHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>	
	<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
</body>
</html>