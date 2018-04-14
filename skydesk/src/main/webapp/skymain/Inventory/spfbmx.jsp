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
<title>商品分布明细</title>
<link rel="stylesheet" href="/skydesk/css/icon.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css" type="text/css" />
<style type="text/css">
.custcell{
	font-weight: 600;
}
</style>
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
<div class="fr hide">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',defaulthzfs.showhxcm);">
</div>
</div>
<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdata();toggle();">
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
<div class="s-box"><div class="title">进货开始</div>
<div class="text"><input class="easyui-datebox" id="sjhdate"
						style="width: 164px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selectdx_house('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
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
<div class="text"><input type="text" class="edithelpinput" data-value="#scolorid" name="scolorname" id="scolorname" placeholder="<选择>" >
<span onclick="selectwarecolor($('#swareid').val(),'search')"></span>
<input type="hidden" id="scolorid" value="">
</div>
</div>
<div class="s-box"><div class="title">尺码</div>
<div class="text"><input type="text" class="edithelpinput" data-value="#ssizeid" name="ssizename" id="ssizename" placeholder="<选择>">
<span onclick="selectsize($('#swareid').val(),'search')"></span> 
<input type="hidden" id="ssizeid" value="">
</div>
</div>
<div class="s-box"><div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" data-options="required:true"><span onclick="selectwaretype('search')"></span>
					 <input type="hidden" id="stypeid" value="">
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text" class="edithelpinput brand_help" name="sbrandname" id="sbrandname" 
                      data-value="#sbrandid" maxlength="20" placeholder="<选择或输入>">
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
<div class="s-box"><div class="title">生产商</div>
<div class="text"><input class="edithelpinput provd_help" type="text" id="sprovname" name="sprovname" placeholder="<选择或输入>"
						data-value="#sprovid" maxlength="30"><span onclick="selectdx_prov('search')"></span>
						<input type="hidden" id="sprovid" name="sprovid">
</div>
</div>
<div class="s-box">
<label class="fr"><input id="scxkh" type="checkbox" checked>显示客户库存</label>
</div>
</div>
</div>
</div>
<div style="margin: 5px">
<table id="gg" style="overflow: hidden; height:900px;"></table>
<!-- 分页 -->
<div class="page-bar">
<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>

</div>
<div style="margin: 5px">
<table id="mxgg" style="overflow: hidden; height:900px;"></table>
<!-- 分页 -->
<div class="page-bar">
<div class="page-total">共有<span id="mxpp_total">0</span>条记录</div>
<div id="mxpp" class="tcdPageCode page-code"></div>
</div>
</div>
<script type="text/javascript" charset="UTF-8">
var pgid = "pg1514";
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();  
}  
function changeDivHeight(){               
	var height = document.documentElement.clientHeight;//获取页面可见高度 
	var width = document.documentElement.clientWidth;//获取页面可见高度 
	$('#gg').datagrid('resize',{
		width:width,
  		height:height*0.5-50
	});
}
var levelid=getValuebyName("GLEVELID");
$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setyear(); 
	$('#pp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
			searchpage(p);
		}
	});
	$('#mxpp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
			searchpage1(p);
		}
	});
	$('#gg').datagrid({
		idField : 'ID',
		height : $('body').height()*0.5 - 50,
		fitColumns : false,
		striped : true, //隔行变色
		nowrap: false,
		singleSelect : true,
		showFooter : true,
		toolbar: "#toolbars",
		frozenColumns: [[{
			field : 'ROWNUMBER',
			title : '序号',
			width : 50,
			fixed:true,
			align:'center',
			halign:'center',
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
        },{
			title:'图片',
			field:'IMAGENAME0',
			width:70,
			align:'center',
			fixed :true,
	        formatter:function(value,row,index){
	        	if(value!=undefined&&value.indexOf('/')>=0){
	        		return '<img bigimg="true" style=\"height: 60px;width: 60px;\" src="'+imageurl+row.IMAGENAME0+'" />';
	        	}
	        	return "";
	        }
		},{
			field : 'WARENO',
			title : '货号',
			width : 80,
			fixed: true,
			sortable:true,
			align:'center',
			halign:'center'
		},{
			field : 'WARENAME',
			title : '商品',
			width : 80,
			fixed: true,
			sortable:true,
			align:'center',
			halign:'center'
		}, {
			field : 'COLORNAME',
			title : '颜色',
			width : 60,
			sortable:true,
			fixed: true,
			align:'center',
			halign:'center'
		}]],
		columns: [[
			{
				field : 'SEASONNAME',
				title : '季节',
				width : 60,
				sortable:true,
				fixed: true,
				align:'center',
				halign:'center'
			}, {
				field : 'RETAILSALE',
				title : '零售价',
				width : 80,
				sortable:true,
				fixed: true,
				align:'right',
				halign:'center',
				formatter: currfm
			}, {
				field : 'SSDATE',
				title : '上市日期',
				width : 90,
				sortable:true,
				fixed: true,
				align:'center',
				halign:'center'
			}, {
				field : 'AMOUNT',
				title : '当前库存',
				width : 60,
				sortable:true,
				fixed: true,
				align:'center',
				halign:'center',
				formatter: amtfm,
				styler: amtstyle
			}
		]],
		onClickRow: getwarefbmx
	}).datagrid("keyCtr");
	$('#mxgg').datagrid({
		idField : 'ID',
		height : $('body').height()*0.5 - 50,
		fitColumns : false,
		striped : true, //隔行变色
		nowrap: false,
		singleSelect : true,
		showFooter : true,
		frozenColumns: [[{
			field : 'ROWNUMBER',
			title : '序号',
			width : 50,
			fixed:true,
			align:'center',
			halign:'center',
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
        },{
			field : 'HOUSENAME',
			title : '店铺/客户',
			width : 160,
			fixed: true,
			sortable:true,
			align:'left',
			halign:'center',
			styler: function(value,row,index){
				if(row.FS>0)
				return "color: #ff7900;"
			}
		}]],
		columns: [[]]
	}).datagrid("keyCtr");
	alerthide();
});
function setyear(){
	var myDate = new Date(top.getservertime());
	var year=myDate.getFullYear();	
    var start=year-5;
	var end=year+1;
	for(var i = start;i <= end ; i++)
	{
	    $("#sprodyear").append("<option value="+i+">"+i+"</option'>");
	}
}
//清空高级搜索
function resetsearch(){
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate,#jhdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#sntid').val('2');
	var idstr = "#sprovname,#sprovid,#shousename,#shouseid,#scolorname,#scolorid,#ssizeid,#ssizename"
		+",#swareno,#swareid,#swarename,#sbrandname,#sfullname,#stypeid"
		+",#sbrandid,#sseasonname,#sprodyear,#slocale";
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
	setTimeout(function(){
		$('#gg').datagrid('resize',{height:$('body').height()*0.5-50});
	}, 200);
}
function searchdata(){
	var mindate = $("#smindate").datebox('getValue');//开始日期 
	var maxdate = $("#smaxdate").datebox('getValue');//结束日期 
	var jhdate = $("#sjhdate").datebox('getValue');//进货日期 
	var houseid = $("#shouseid").val();//店铺
	var wareno= $("#swareno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var brandid = $("#sbrandid").val();//品牌
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var provid = $("#sprovid").val();//生产商
	typeid = typeid == "" ? "-1": typeid;
	var cxkh = $("#scxkh").prop("checked")?1:0;
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data : {
			erpser: "totalspfbmx",
			mindate: mindate,
			maxdate: maxdate,
			jhdate: jhdate,
			cxkh: cxkh,
			houseidlist: houseid,
			wareid: wareid,
			wareno: wareno,
			warename: warename,
			brandidlist: brandid,
			typeid: typeid,
			prodyear: prodyear,
			providlist: provid,
			seasonname: seasonname
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					searchpage(1);
				}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
var sortlist = "wareid asc,colorname asc";
var currentdata = {};
//分页获取
function searchpage(page) {
	$('#gg').datagrid('loadData',nulldata);	
	var grouplist = "a.wareid,b.warename,b.imagename0,b.units,b.seasonname,b.wareno,b.retailsale,b.ssdate,b.sizegroupno,a.colorid,c.colorname";//汇总方式
	currentdata = {
			erpser : "listspfbmx",
			rows: pagecount,
			timeout: 5*60*1000,
			grouplist: grouplist,
			sortlist: sortlist,
			page : page
		};
	alertmsg(6,5*60*1000);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		timeout: 5*60*1000,
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)) {
				var totals = data.total;
				$("#pp_total").html(totals);
				$("#pp").setPage({
			        pageCount:Math.ceil(Number(totals)/ pagecount),
			        current: Number(page)
				 });
				data.footer = [{
					ROWNUMBER: "合计",
					AMOUNT: data.totalamount
				}];
				$('#gg').datagrid('loadData', data);
				$('#gg').datagrid('scrollTo',0);
				$('#gg').datagrid('resize');
			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
var sizegroupno = "";
var sumlist = "";
var selectrow = {};
var getwarefbmx = function(index,row){
	var _sizegroupno = row.SIZEGROUPNO;
	selectrow.wareid = row.WAREID;
	selectrow.colorid = row.COLORID;
	if(_sizegroupno != sizegroupno){
		sizegroupno = _sizegroupno;
		var wareid = row.WAREID;
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "warespfbmx",
				wareid: wareid
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)) {
						var cols = [{
							field : 'JHAMT',
							title : '累计进货',
							width : 60,
							sortable:true,
							fixed: true,
							align:'center',
							halign:'center',
							formatter: amtfm,
							styler: amtstyle
						},{
							field : 'XSAMT',
							title : '本期销售',
							width : 60,
							sortable:true,
							fixed: true,
							align:'center',
							halign:'center',
							formatter: amtfm,
							styler: amtstyle
						},{
							field : 'XSCURR',
							title : '本期销售额',
							width : 80,
							sortable: true,
							fixed: true,
							align:'right',
							halign:'center',
							formatter: currfm,
							styler: amtstyle
						}];
						var rows = data.sizelist;
						sumlist = data.sumlist;
						for(var i in rows){
							var row = rows[i];
							cols.push({
								field : 'XSAMT'+row.SIZEID,
								title : row.SIZENAME,
								width : 60,
								sortable:true,
								fixed: true,
								align:'center',
								halign:'center',
								formatter: amtfm,
								styler: amtstyle
							});
						}
						cols.push({
							field : 'KCAMT',
							title : "当前库存",
							width : 60,
							sortable:true,
							fixed: true,
							align:'center',
							halign:'center',
							formatter: amtfm,
							styler: amtstyle
						});
						for(var i in rows){
							var row = rows[i];
							cols.push({
								field : 'KCAMT'+row.SIZEID,
								title : row.SIZENAME,
								width : 60,
								sortable:true,
								fixed: true,
								align:'center',
								halign:'center',
								formatter: amtfm,
								styler: amtstyle
							});
						}
						var $dg = $('#mxgg');
						var options = $dg.datagrid("options");
						options.columns = [cols];
						$dg.datagrid();
						searchpage1(1);
					}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}else searchpage1(1);
}
function searchpage1(page){
	var wareid = selectrow.wareid;
	var colorid = selectrow.colorid;
	if(sumlist==""){
		alerttext("请选择数据！");
		return;
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "listspfbmx1",
			wareid: wareid,
			colorid: colorid,
			sumlist: sumlist,
			rows: pagecount,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)) {
					console.log(data);
					var totals = data.total;
					$("#mxpp_total").html(totals);
					$("#mxpp").setPage({
				        pageCount:Math.ceil(Number(totals)/ pagecount),
				        current: Number(page)
					 });
					data.footer = [{
						ROWNUMBER: "合计",
						JHAMT: data.totaljhamt,
						XSAMT: data.totalxsamt,
						XSCURR: data.totalxscurr,
						KCAMT: data.totalkcamt
					}];
					$('#mxgg').datagrid('loadData', data);
					$('#mxgg').datagrid('scrollTo',0);
					$('#mxgg').datagrid('resize');
				}
			}catch(err){
				console.error(err.message);
			}
		}
	});
}
</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
	<!-- 生产商帮助列表 -->
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 颜色帮助列表 -->
<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
<!-- 尺码帮助列表 -->
<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
</body>
</html>