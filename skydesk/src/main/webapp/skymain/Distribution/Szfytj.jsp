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
<title>收支费用统计</title>
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
<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchdatas();toggle();">
<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空条件" onclick="resetsearch()">
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
</div>
</div>
<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdatas();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input class="easyui-datebox" id="smindate"
						style="width: 164px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input class="easyui-datebox" id="smaxdate"
						style="width: 164px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input type="text" class="edithelpinput house_help" data-value="#shouseid"
					name="shousename" id="shousename" data-options="required:true" style="width:125px;height:24px;"
					placeholder="<选择或输入>" ><span onclick="selectdx_house('search')" ></span><input type="hidden" id="shouseid" value="">
</div>
</div>
<div class="s-box"><div class="title">制单人</div>
<div class="text">
<input type="text" name="soperant" id="soperant" style="width:160px;height:25px" placeholder="<输入>">
</div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden; height:900px;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div class="page-total" id="pp_total">
			共有0条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<script type="text/javascript" charset="UTF-8">

var pgid = "pg1313";
setqxpublic();
var ser;
var s=false;
var levelid=getValuebyName("GLEVELID");

$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#pp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
			searchpages(p.toString());
		}
	});
	$('#gg').datagrid(
			{
				idField : 'ID',
				width : '100%',
				height : $('body').height() - 50,
				fitColumns : true,
				striped : true, //隔行变色
				nowrap: false,
				singleSelect : true,
				showFooter : true,
				toolbar :"#toolbars",
				sortName: "TOTALCURR",
				sortOrder: "asc",
				onSortColumn: function(sort,order){
					$('#pp').refreshPage();
				},
				frozenColumns: [[{
					field: 'ROWNUMBER',
					title: '序号',
					width: 50,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: amtfm //有返回序号 这样就行
				},{
					field: 'XMNAME',
					title: '项目',
					width: 200,
					fixed: true,
					halign: 'center',
					styler: function(value,row,index){
						var xmid = Number(row.XMID);
						if(xmid==-1){
							return "text-align:center;";
						}
					}
				},{
					field: 'TOTALCURR',
					title: '总金额',
					width: 100,
					fixed: true,
					sortable: true,
					halign: 'center',
					align: 'right',
					formatter: currfm,
					styler: currstyle
				}]],
				rowStyler: function(index,row){
					if(row.XMID==-1)
						return "background: yellow !important"
				},
				onSelect: function(index,row){
					if(row&&row.XMID==-1){
						$("#gg").datagrid("unselectRow",index);
					}
				}
			}).datagrid("keyCtr");
	alerthide();
	searchdatas();
});

//清空高级搜索
function resetsearch(){
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	var idstr = "#soperant,#shousename,#shouseid";
	$(idstr).val('');
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
var currentdata = {};
var sortlist = "";
function searchdatas(){
	var mindate = $("#smindate").datebox('getValue');//开始日期 
	var maxdate = $("#smaxdate").datebox('getValue');//结束日期 
	var houseid = $("#shouseid").val();//店铺
	var operant = $("#soperant").val();//制单人
	currentdata = {};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "totalszjyhz",
			mindate: mindate,
			maxdate: maxdate,
			houseidlist: houseid,
			operant: operant
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					currentdata["erpser"] = "listszjyhz";
					currentdata["sumlist"] = data.sumlist;
					footer = [{XMNAME: "总计",XMID:-1}];
					var pwlist = data.pcolumns;
					var cglist = data.fcolumns;
					footer[0]["PAYCURR_ZK"] = data.totalzk;
					footer[0]["TOTALCURR"] = data.totalcurr;
					footer[0]["TOTALGZ"] = data.totalgz;
					var pwlength = pwlist.length;
					var cglength = cglist.length;
					var columns = [[
						{
							field: 'PAYWAY',
							title: '结算方式',
							colspan: pwlength,
							halign: 'center',
							align: 'center'
						},{
							field: 'PAYCURR_ZK',
							title: '折让',
							width: 80,
							fixed: true,
							rowspan: 2,
							sortable: true,
							halign: 'center',
							align: 'right',
							formatter: currfm,
							styler: currstyle
						},{
							field: 'TOTALGZ',
							title: '挂账',
							width: 80,
							fixed: true,
							rowspan: 2,
							sortable: true,
							halign: 'center',
							align: 'right',
							formatter: currfm,
							styler: currstyle
						},{
							field: 'CGWAY',
							title: '费用',
							colspan: cglength,
							halign: 'center',
							align: 'center'
						}
					]];
					var cols1 = [];
					for(var i=0;i<pwlist.length;i++){
						if(!pwlist[i]) continue;
						var field = pwlist[i].fieldname.toUpperCase();
						var title = pwlist[i].title;
						var curr = pwlist[i].curr;
						footer[0][field] = curr;
						cols1.push({
							field: field,
							title: title,
							width: 80,
							fixed: true,
							sortable: true,
							halign: 'center',
							align: 'right',
							formatter: currfm,
							styler: currstyle
						});
					}
					for(var i=0;i<cglist.length;i++){
						if(!cglist[i]) continue;
						var field = cglist[i].fieldname.toUpperCase();
						var title = cglist[i].title;
						var curr = cglist[i].curr;
						footer[0][field] = curr;
						cols1.push({
							field: field,
							title: title,
							width: 80,
							fixed: true,
							sortable: true,
							halign: 'center',
							align: 'right',
							formatter: currfm,
							styler: currstyle
						});
					}
					columns.push(cols1);
					var $dg = $("#gg");
					var options = $dg.datagrid("options");
					options.columns = columns;
					$dg.datagrid();
					searchpages(1);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}

var footer = [{XMNAME: "总计"}];
function searchpages(page){
	if(currentdata["erpser"]==undefined){searchdatas();return;}
	currentdata["page"]=page;
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName;
	var order = options.sortOrder;
	currentdata["sort"] = sort;
	currentdata["order"] = order;
	currentdata["rows"] = pagecount;
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					data.footer = footer;
					$('#pp_total').html('共有'+data.total+'条记录');
					$("#pp").setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current: Number(page)
					 });
					$dg.datagrid('loadData', data);
					if($dg.datagrid('getRows').length>0){
						$dg.datagrid('scrollTo',0);
						$dg.datagrid('selectRow',0);
					}
					$dg.datagrid('resize');
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
	</script>
	<!-- 店铺帮助列表 -->
<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
</body>
</html>