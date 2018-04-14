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
<title>库存结构分析</title>
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
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/amchart/amcharts.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/amchart/pie.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/amchart/serial.js"></script>
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
	<div class="s-box">
	<div class="title">查询日期</div>
<div class="text"><input id="nowdate" type="text" editable="true" style="width:164px;height:25px" class="easyui-datebox" required="required" currentText="today"></input>
	</div>
	</div>
<div class="s-box">
	<div class="title">尺码类型</div>
<div class="text"><select id="ssizegroupno"   class="sele1">
				<option value="">---请选择---</option>
                    </select>
	</div>
	</div>
				
<div class="s-box">
	<div class="title">店铺</div>
<div class="text">
<input type="text" class="edithelpinput house_help" data-value="#shouseid"
					name="shousename" id="shousename"
					placeholder="<选择或输入>" >
					<span onclick="selecthouse('search')">
					</span> <input type="hidden" id="shouseid" value="">
	</div>
	</div>
<div class="s-box">
	<div class="title">货号</div>
<div class="text">
<input  type="text"  class="edithelpinput" data-value="#swareid" name="swareno" id="swareno" placeholder="<选择或输入>" >
<span onclick="selectware('search')"></span>
				 <input type="hidden" id="swareid" value="">
	</div>
	</div>
				
<div class="s-box">
	<div class="title">颜色</div>
<div class="text"><input  type="text"  class="edithelpinput"
				name="scolorname" id="scolorname" placeholder="<请选择>" readonly><span onclick="selectwarecolor($('#swareid').val(),'search')" ></span>
				 <input type="hidden" id="scolorid" value="">
	</div>
	</div>
<div class="s-box">
	<div class="title">品牌</div>
<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname" placeholder="<选择或输入>" ><span onclick="selectbrand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
	</div>
	</div>
				
	<div class="s-box">
	<div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid" name="sfullname" id="sfullname" placeholder="<选择或输入>">
				<span onclick="selectwaretype('search')"></span>
				 <input type="hidden" id="stypeid" value="">
	</div>
	</div>
<div class="s-box">
	<div class="title">生产商</div>
<div class="text"><input class="edithelpinput provd_help" maxlength="20" data-value="#sprovid" placeholder="<选择或输入>"
					 type="text" id="sprovname" name="sprovname"><span onclick="selectprov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid">
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
<div class="text">
<select id="sprodyear" class="sele1">
	<option selected="selected" value="">--请选择--</option>						
</select>
</div>
</div>
<div class="s-box"><div class="title">区位</div>
<div class="text"><input  type="text"  class="edithelpinput area_help" data-value="#sareaid"
				name="sareaname" id="sareaname" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" >
				<span onclick="selectdx_area('search')"></span>
				 <input type="hidden" id="sareaid" value="">
</div>
</div>
	</div>
	</div>
	<div id="alldiv" style="overflow-y: scroll;overflow-x:hidden;">
	<div style="width: 98%;height:230px;overflow:auto">
		<div style="width: 98%; height: 30px;"><table><tr>
			<td><input type="radio" name="hzfs2" value="0" checked="checked"/>品牌</td>
			<td><input type="radio" name="hzfs2" value="1" />类型</td>
			<td><input type="radio" name="hzfs2" value="2"/>季节</td>
			<td><input type="radio" name="hzfs2" value="3" />店铺</td>
			<td><input type="radio" name="hzfs2" value="4" />颜色</td>
			<td><input type="radio" name="hzfs2" value="5" />生产商</td>
			<td><input type="radio" name="hzfs2" value="6" />尺码</td>
			<td><input type="radio" name="hzfs2" value="7" />区位</td>
			</tr></table></div>
	 <div id="chartdiv" style="width: 49%; height: 200px;float:left;overflow: auto;"></div>
	  <div id="chartdiv2" style="width: 49%; height: 200px; overflow: auto;"></div>
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
var dheight = 100;
var rownumbers = 1;
var sortid = "0";
var order = "asc";
var pgid = "pg1701";
var epno = getValuebyName("EPNO");
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	$('#gg').datagrid("resize", {
		height: $('body').height() - 50
	});
}

//设置弹出框的属性
$(function() {
	//设置日期框默认值
	var myDate = new Date(top.getservertime());
	$('#nowdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
	//$("input:radio[name='pai1']").eq(0).attr("checked",true);//单选框选中
	$("input:radio[name='hzfs2']").click(function() {
		searchdata();
	});
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
		backFn: function(p) { //http:
			searchpage(p);
		}
	});
	var options = {
		idField: 'CODENAME',
		height: $('body').height() - 50,
		fitColumns: true,
		toolbar: "#toolbars",
		sortName: 'CODENAME',
		sortOrder: 'asc',
		remotesort: false,
		striped: true,
		//隔行变色 
		singleSelect: true,
		showFooter: true,
		columns: [[{
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
			field: 'CODENAME',
			title: "品牌",
			width: 100,
			fixed: true,
			align: 'center',
			sortable: true,
			order: 'asc',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			fieldtype: "G",
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			sortable: true,
			formatter: amtfm
		},
		{
			field: 'RATEAMT',
			title: '库存占比',
			fieldtype: "G",
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					return nval.toString() + '%';
				}
			}
		},
		{
			field: 'CURR',
			title: '零售额',
			fieldtype: "N",
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			sortable: true,
			formatter: currfm
		},
		{
			field: 'RATECUR',
			title: '零售额占比',
			fieldtype: "G",
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					var val = Number(value);
					var nval = val.toFixed(2);
					return nval.toString() + '%';
				}
			}
		},
		{
			field: 'SKC',
			title: 'SKC',
			fieldtype: "G",
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			sortable: true
		},
		{
			field: 'PRICE',
			title: '平均单价',
			fieldtype: "N",
			width: 100,
			fixed: true,
			hidden: (allowinsale==1?false:true),
			align: 'right',
			halign: 'center',
			sortable: true,
			formatter: currfm
		},
		{
			field: 'FIXEDCURR',
			title: '成本额',
			fieldtype: "N",
			width: 100,
			fixed: true,
			hidden: (allowinsale==1?false:true),
			align: 'right',
			halign: 'center',
			sortable: true,
			formatter: currfm
		}]],
		onClickRow: function(rowIndex, rowData) {

},
		onSortColumn: function(sort, orders) {
			$("#pp").refreshPage();
		},
		pageSize: 20
	};
	//加载数据
	$('#gg').datagrid(options);
	getsizelist();
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
	$('#nowdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#hzfs').val('2');
	$('#sizegroupno').val('');
	//$('#pai1').prop('checked',true);
	$("input[type=radio][name=hzfs2][value=2]").attr("checked", 'checked');
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#swareid').val('');
	$('#swareno').val('');
	$('#scolorname').val('');
	$('#scolorid').val('');
	$('#sbrandname').val('');
	$('#sbrandid').val('');
	$('#sfullname').val('');
	$('#stypeid').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('0');
	$('#sprovid,#sareaid,#sareaname').val('');
	$('#sprovname,#ssizegroupno').val('');

}
var footer = [];
//查询统计库存结构分布方法,图表
function searchdata() {
	var hzfs = $("input[name='hzfs2']:checked").val(); //汇总方式
	var title = "";
	if (hzfs == 0) {
		title = "品牌";
	} else if (hzfs == 1) {
		title = "类型";
	} else if (hzfs == 2) {
		title = "季节";
	} else if (hzfs == 3) {
		title = "店铺";
	} else if (hzfs == 4) {
		title = "颜色";
	} else if (hzfs == 5) {
		title = "生产商";
	} else if (hzfs == 6) {
		title = "尺码";
	} else if (hzfs == 7) {
		title = "区位";
	}
	var $dg = $("#gg");
	var col = $dg.datagrid("getColumnOption","CODENAME");
	col.title = title;
	$dg.datagrid();
	var nowdate = $('#nowdate').datebox('getValue'); //当前时间
	var sizegroupno = $("#ssizegroupno").val(); //尺码类型
	var colorid = Number($("#scolorid").val()); //颜色
	var houseid = Number($("#shouseid").val()); //店铺
	var wareno = $("#swareno").val(); //货号no
	var wareid = Number($("#swareid").val()); //货号id
	var brandid = $("#sbrandid").val(); //品牌
	var typeid = $("#stypeid").val(); //类型
	var provid = $("#sprovid").val(); //生产商
	brandid = brandid==""?"-1":brandid;
	typeid = typeid==""?"-1":typeid;
	provid = provid==""?"-1":provid;
	var prodyear = $("#sprodyear").val(); //生产年份
	var seasonname = $("#sseasonname").val(); //季节
	var areaid = $("#sareaid").val(); //区位
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data: {
			erpser: "totalkcjgfx",
			nowdate: nowdate,
			hzfs: hzfs,
			areaidlist: areaid,
			sizegroupno: sizegroupno,
			colorid: colorid,
			houseid: houseid,
			wareno: wareno,
			wareid: wareid,
			brandid: brandid,
			typeid: typeid,
			prodyear: prodyear,
			provid: provid,
			seasonname: seasonname
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					footer = [{
						ROWNUMBER: "合计",
						AMOUNT: data.amount,
						CURR: data.curr,
						MLCURR: data.mlcurr,
						FIXEDCURR: data.fixedcurr
					}];
					searchpage(1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
var chart;
var chart2;
var currentdata = {};
//查询库存统计结构列表方法,图表
function searchpage(page) {
	if (footer.length == 0){searchdata();return;};
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName; //汇总项目
	var order = options.sortOrder; //汇总项目
	currentdata = {
		erpser: "listkcjgfx",
		order: order,
		sort: sort,
		rows: pagecount,
		page: page
	};
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: currentdata,
		dataType: 'json',
		success: function(data) {
			try {
				if (valideData(data)) {
					var totals = data.total;
					chart2 = AmCharts.makeChart("chartdiv", {
						type: "pie",
						theme: "patterns",
						dataProvider: data.rows,
						titleField: "CODENAME",
						valueField: "AMOUNT",
						startDuration: 0.01,
						colors: amchartcolors,
						labelsEnabled: false,
						balloonText: "[[title]]:<b>[[value]]</b> ([[percents]]%)" //,
						//                  legend: {
						//                      align: "center",
						//                      markerType: "circle"
						//                  }
					});
					chart = AmCharts.makeChart("chartdiv2", {
						type: "serial",
						theme: "patterns",
						dataProvider: data.rows,
						categoryField: "CODENAME",
						startDuration: 0.1,
						colors: amchartcolors,
						labelsEnabled: false,
						categoryAxis: {
							labelRotation: 0,
							//调整字体倾斜 
							gridPosition: "start"
						},
						chartCursor: [{
							cursorAlpha: 0,
							zoomable: false,
							categoryBalloonEnabled: false
						}],
						graphs: [{
							type: "column",
							valueField: "CURR",
							lineAlpha: 0,
							fillAlphas: 0.8,
							balloonText: "[[category]]:<b>[[value]]</b>"
						}]
						//,
						//                  legend: {
						//                      useGraphSettings: true
						//                  }
					});
					$("#pp").setPage({
						pageCount: Math.ceil(Number(totals) / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('refresh');
					$('#gg').datagrid('loadData', data);
					$('#gg').datagrid('reloadFooter', footer);
					$('#total').val(totals);
					$('#pp_total').html(totals);
					$('#gg').datagrid('scrollTo', 0);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});

}
//获取尺码组
function getsizelist() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "sizegrouplist"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if (data.total > 0) {
						var rows = data.rows;
						for (var i in rows) {
							var row = rows[i];
							$('#ssizegroupno').append('<option value="' + row.GROUPNO + '">' + row.GROUPNO + ':' + row.SIZELIST + '</option>');
						}
					}
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
<!-- 生产商帮助列表 -->
<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 颜色帮助列表 -->
<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
<!-- 尺码帮助列表 -->
<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/AreaHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
</body>
</html>