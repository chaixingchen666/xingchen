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
<title>进销存汇总账</title>
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
<input type="button" class="btn2" id="qc" name="qc" value="查询条件▲" onclick="toggle()">
 <span id="serbtn" class="fr">
<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchdatas();toggle();">
<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空条件" onclick="resetsearch()">
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',defaulthzfs.showhxcm);">
</div>
</div>
<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdatas();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>
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

<div class="s-box"><div class="title">货位</div>
<div class="text">
<input type="text" name="slocale" id="slocale" style="width:160px;height:25px" placeholder="<输入>">
</div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden; height:900px;"></table>
<!-- 分页 -->
<div class="page-bar">
<div class="page-total" id="pp_total">共有0条记录</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<script type="text/javascript" charset="UTF-8">
var pgid = "pg1506";
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
  		height:height-50
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
			searchpages(p.toString());
		}
	});
	$('#gg').datagrid(
			{
				idField : 'ID',
				width : '100%',
				height : $('body').height() - 50,
				fitColumns : false,
				striped : true, //隔行变色
				nowrap: false,
				singleSelect : true,
				showFooter : true,
				toolbar :"#toolbars",
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
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
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
		$('#gg').datagrid('resize',{height:$('body').height()-50});
	}, 200);
}
var validehzsj = function(ishz,hzname,hzsjname){
	if(ishz&&hzname.indexOf("商品")==-1&&(hzname.indexOf("颜色")>-1||hzname.indexOf("尺码")>-1)){
		alerttext("汇总项：商品必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}
	return true;
}
var currentdata = {};
var sortlist = "";
function searchdatas(){
	var mindate = $("#smindate").datebox('getValue');//开始日期 
	var maxdate = $("#smaxdate").datebox('getValue');//结束日期 
	var wareno= $("#swareno").val();//货号no
	var noteno= $("#snoteno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var colorid = $("#scolorid").val();//品牌
	var sizeid = $("#ssizeid").val();//品牌
	var brandid = $("#sbrandid").val();//品牌
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var houseid = $("#shouseid").val();//店铺
	var provid = $("#sprovid").val();//店铺
	var locale = $('#slocale').val(); //货位
	var xsid = $("#kucun").val();//库存数量
	var minamt = $("#minamt").val();//最小库存
	var maxamt = $("#maxamt").val();//最大库存
	var grouplist = "";
	var hzlist = $('#shzlist').data('hzlist');
	if(!hzlist){
		alerttext("请选择汇总方式");
		selectzhfs();
		return;
	}
	var currdatetime = new Date(top.getservertime()).Format("yyyy-MM-dd hh:mm:ss");
	if($('#skyhzcheckbj').prop('checked')){
		if(hzlist.grouplist.length==0){
			alerttext("请选择汇总项目");
			return;
		}
	}
	var jxchzlist = getjxchzlist(defaulthzfs.showprice);
	currentdata = {};
	currentdata["erpser"] = "listjxchz";
	currentdata["fieldlist"] = jxchzlist.fieldlist;
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["calcfield"] = jxchzlist.calcfield;
	currentdata["sumlist"] = jxchzlist.sumlist;
	currentdata["sortlist"] = sortlist;
	currentdata["rows"] = pagecount;
	currentdata["page"] = 1;
	var dataparam = {
		erpser: "totaljxchz",
		houseidlist: houseid,
		mindate: mindate,
		maxdate: maxdate,
		wareno: wareno,
		noteno: noteno,
		warename: warename,
		wareid: wareid,
		colorid: colorid,
		sizeid: sizeid,
		brandidlist: brandid,
		providlist: provid,
		typeid: typeid,
		prodyear: prodyear,
		seasonname: seasonname,
		locale: locale
	}
	searchtotal(dataparam);
}
//查询钱汇总数据
function searchtotal(dataparam){
	alertmsg(6,5*60*1000);
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: dataparam, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到         
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					searchpages(1);
					return;
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function searchpages(page){
	if(currentdata["erpser"]==undefined){searchdatas();return;}
	currentdata["page"]=page;
	currentdata["sortlist"] = sortlist;
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					var totals = data.total;
					var strArr = ["qc","cg","ct","cs","dr","py","ls","xs","xt","dc","pk","qm"];
					data.footer = [{
						ROWNUMBER: "合计"
					}];
					for(var i in strArr){
						var str = strArr[i];
						data.footer[0]["AMOUNT"+str.toUpperCase()]=data["totalamt"+str];
						data.footer[0]["CURR"+str.toUpperCase()]=data["totalcurr"+str];
					}
					$('#pp_total').html('共有'+totals+'条记录');
					$("#pp").setPage({
				        pageCount:Math.ceil(Number(totals)/ pagecount),
				        current: Number(page)
					 });
					var $dg = $('#gg');
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
var amtformatter = function(value,row,index){
	var val = Number(value);
	if(val==0||isNaN(val))
		return "";
	return val;
}
var currformatter = function(value,row,index){
	var val = Number(value);
	if(val==0||isNaN(val))
		return "";
	return val.toFixed(2);
}
var jjcurrformatter = function(value,row,index){
	var val = Number(value);
	if((allowinsale==1&&val==0)||isNaN(val))
		return "";
	if(allowinsale==1){
		return val.toFixed(2);
	}else return "***";
}
function getjxchzcolumn(showprice){
	var colspan = showprice==1?3:2;
	showprice = showprice==1?true:showprice;
	var columns = [[{
		field : 'QC',
		title : '期初',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	},{
		field : 'CG',
		title : '采购入库',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'CT',
		title : '采购退货',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'CS',
		title : '初始',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'DR',
		title : '调入',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'PY',
		title : '盘盈',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'LS',
		title : '零售',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'XS',
		title : '批发销售', 
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'XT',
		title : '批发退库', 
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'DC',
		title : '调出',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'PK',
		title : '盘亏',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}, {
		field : 'QM',
		title : '期末',
		width : 70,
		colspan : colspan,
		align:'right',
		halign:'center'
	}],[]];
	var strArr = ["QC","CG","CT","CS","DR","PY","LS","XS","XT","DC","PK","QM"];
	for(var i in strArr){
		var str = strArr[i];
		if(showprice){
			var title0 = "数量";
			var title1 = "单价";
			var title2 = "金额";
			var cbqx = i==0||i==(strArr.length-1)||str=="CG"||str=="CS";
			if(cbqx){
				title1 = "成本价";
				title2 = "成本额";
			}
			columns[1].push({
				field : 'AMOUNT'+str,
				title : '数量',
				width : 70,
				fieldtype: "G",
				fixed: true,
				align:'right',
				halign:'center',
				formatter: amtformatter
			},{
				field : 'PRICE'+str,
				title : title1,
				width : 70,
				fieldtype:  (cbqx&&allowinsale==0?"*":"N"),
				fixed: true,
				align:'right',
				halign:'center',
				formatter: (cbqx?jjcurrformatter:currformatter)
			}, {
				field : 'CURR'+str,
				title : title2,
				width : 90,
				fieldtype: (cbqx&&allowinsale==0?"*":"N"),
				fixed: true,
				align:'right',
				halign:'center',
				formatter: (cbqx?jjcurrformatter:currformatter)
			});
		}else{
			var title0 = "数量";
			var title1 = "金额";
			var cbqx = i==0||i==(strArr.length-1)||str=="CG"||str=="CS";//成本权限
			if(cbqx){
				title1 = "成本额";
			}
			columns[1].push({
				field : 'AMOUNT'+str,
				title : title0,
				width : 70,
				fieldtype: "G",
				fixed: true,
				align:'right',
				halign:'center',
				formatter: amtformatter
			}, {
				field : 'CURR'+str,
				title : title1,
				width : 90,
				fieldtype:  (cbqx&&allowinsale==0?"*":"N"),
				fixed: true,
				align:'right',
				halign:'center',
				formatter: (cbqx?jjcurrformatter:currformatter)
			});
		}
	}
	return columns;
}
//定义生成表头
function setdatagrid($dg,sortName,columns){
	sortlist = sortName +" asc";
	$dg.datagrid({
		sortName: sortName,
		sortOrder: "asc",
		frozenColumns: [columns],
		columns: getjxchzcolumn(defaulthzfs.showprice)
	}).datagrid('resize');
}
function getjxchzlist(showprice){
	var strArr = ["qc","cg","ct","cs","dr","py","ls","xs","xt","dc","pk","qm"];
	var fieldlist = "";
	var sumlist = "";
	var calcfield = "";
	for(var i in strArr){
		var str = strArr[i];
		fieldlist += "sum(a.amount"+str+") as amount"+str+",sum(a.curr"+str+") as curr"+str+",";
		sumlist += "nvl(sum(amount"+str+"),0) as totalamt"+str+",nvl(sum(curr"+str+"),0) as totalcurr"+str+",";
		if(showprice==1)
			calcfield += "case amount"+str+" when 0 then 0 else curr"+str+"/amount"+str+" end as price"+str+",";
	}
	if(fieldlist.length>0)
		fieldlist=fieldlist.substring(0,fieldlist.length-1);
	if(sumlist.length>0)
		sumlist=sumlist.substring(0,sumlist.length-1);
	if(calcfield.length>0)
		calcfield=calcfield.substring(0,calcfield.length-1);
	var hzlist = {
			fieldlist: fieldlist,
			sumlist: sumlist,
			calcfield: calcfield
	};
	return hzlist;
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
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
	<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>