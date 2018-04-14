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
<!-- <meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE" />  -->
<title>临时盘点汇总</title>
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
<div class="s-box">
<div class="title">开始日期</div>
<div class="text"><input id="smindate" type="text" editable="true"
				class="easyui-datebox" required="required" currentText="today"
				style="width: 164px; height: 25px"></input>
</div>
</div>
<div class="s-box">
	<div class="title">结束日期</div>
	<div class="text"><input id="smaxdate" type="text" editable="true"
					class="easyui-datebox" required="required" currentText="today"
					style="width: 164px; height: 25px"></input>
	</div>
</div>
<div class="s-box">
	<div class="title" color="#ff7900">*&nbsp;店铺</div>
	<div class="text"><input type="text" class="edithelpinput house_help" name="shousename" data-value="#shouseid"
					id="shousename" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectdx_house('search')"></span>
					<input type="hidden" id="shouseid" value="">
	</div>
	</div>
				
			
<div class="s-box">
	<div class="title">货号</div>
	<div class="text"><input type="text"
					 class="edithelpinput wareno_help" name="swareno" data-value="#swareid"
					id="swareno" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('search')"></span>
					<input type="hidden" id="swareid" value="">
	</div>
	</div>
<div class="s-box">
	<div class="title">商品名称</div>
	<div class="text"><input type="text" name="swarename" id="swarename" maxlength="20"  data-options="required:true"
					placeholder="<输入>" >
	</div>
	</div>
			
<div class="s-box">
	<div class="title">类型</div>
	<div class="text"><input type="text"
					 class="edithelpinput"
					name="sfullname" id="sfullname" data-value="#stypeid"
					data-options="required:true"><span onclick="selectwaretype('search');"></span>
					<input type="hidden" name="stypeid" id="stypeid" value="">
	</div>
	</div>
<div class="s-box">
	<div class="title">品牌</div>
	<div class="text"><input type="text" class="edithelpinput brand_help" data-value="#sbrandid"
					 name="sbrandname" id="sbrandname" data-options="required:true"
					placeholder="<选择或输入>" > <span onclick="selectdx_brand('search')"></span><input type="hidden"
					name="sbrandid" id="sbrandid" value="">
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
</div>
</div>
	<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total" id="pp_total">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<script type="text/javascript">

var pgid = "pg1512";
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
var ser;
var s=false;
var index=1;
var opser;
var index1=1;
var levelid=getValuebyName("GLEVELID");

$(function() {
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setyear(); 
	$('#pp').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
// 			searchpage(p.toString());
			searchpages(p.toString());
		}
	});
	$('#gg').datagrid(
			{
				idField : 'CODENAME',
				width : '100%',
				height : $('body').height() - 50,
				fitColumns : true,
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
				},
				onDblClickRow: function(index,row){
					var noteno = row.NOTENO;
					if(noteno){
						var pgno = noteno.substring(0,2);
						opendetaild(pgno,noteno);
					}
				},
				onSelect :function(index,row){
					var hzlist = $('#shzlist').data('hzlist');
					if(defaulthzfs.showhxcm&&hzlist.grouplist!=""){
						if(row){
							for(var i=1;i<=cols;i++){
								$('#title'+i).html(row['SIZENAME'+i]);
							}
						}
					}
				},
				onLoadSuccess: function(data){
					var hzlist = $('#shzlist').data('hzlist');
					if(defaulthzfs.showhxcm&&hzlist.grouplist!=""){
						var sizelength = 0;
						var rows = data.rows;
						var $dg = $('#gg');
						for(var i=0;i<rows.length;i++){
							var item= rows[i]; 
							while(sizelength<=sizenum){
								var s = sizelength+1;
								if(item["SIZENAME"+s]!=""&&item["SIZENAME"+s]!=undefined)
									sizelength++;
								else
									break;
							}
						}
						for(var i=1;i<=sizenum;i++){
							if(i<=sizelength)
								$dg.datagrid("showColumn","AMOUNT"+i);
							else
								$dg.datagrid("hideColumn","AMOUNT"+i);
						}
						$dg.datagrid('resize');
					}
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
	var idstr = "#shousename,#shouseid"
		+",#swareno,#swareid,#swarename,#sbrandname,#stypeid,#sfullname"
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
	if(ishz&&hzsjname.indexOf("数量")==-1){
		alerttext("汇总项：数量必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("单价")>=0&&hzsjname.indexOf("金额")==-1){
		alerttext("汇总项：金额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("零售价")>=0&&hzsjname.indexOf("零售额")==-1){
		alerttext("汇总项：零售额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(defaulthzfs.showhxcm&&hzname.indexOf("商品")==-1){
		alerttext("尺码横向显示汇总项：商品必选");
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
	var houseid = $("#shouseid").val();//调入店铺
	var wareno= $("#swareno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var brandid = $("#sbrandid").val();//品牌
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var locale = $('#slocale').val(); //货位
	var ntid = $('#sntid').val(); //业务类型
	var grouplist = "";
	var hzlist = $('#shzlist').data('hzlist');
	if(!hzlist){
		alerttext("请选择汇总方式");
		selectzhfs();
		return;
	}
	var currdatetime = new Date(top.getservertime()).Format("yyyy-MM-dd hh:mm:ss");
	if($('#skyhzcheckbj').prop('checked')){
		if(hzlist.grouplist.length==0||(hzlist.fieldlist.length==0&&hzlist.calcfield.length==0)){
			alerttext("请选择汇总项目以及汇总数据");
			return;
		}
	}
	currentdata = {};
	currentdata["erpser"] = "repcxlspd";
	if(defaulthzfs.showhxcm==1&&hzlist.grouplist!="") currentdata["sizenum"] = sizenum;
	currentdata["mindate"] = mindate;
	currentdata["maxdate"] = maxdate;
	currentdata["currdatetime"] = currdatetime;
	currentdata["fieldlist"] = hzlist.fieldlist;
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["calcfield"] = hzlist.calcfield;
	currentdata["sumlist"] = hzlist.sumlist;
	currentdata["sortlist"] = sortlist;
	currentdata["houseidlist"] = houseid;
	currentdata["wareno"] = wareno;
	currentdata["warename"] = warename;
	currentdata["wareid"] = wareid;
	currentdata["brandidlist"] = brandid;
	currentdata["typeid"] = typeid;
	currentdata["prodyear"] = prodyear;
	currentdata["seasonname"] = seasonname;
	currentdata["ntid"] = ntid;
	currentdata["rows"] = pagecount;
	currentdata["page"] = 1;
	searchpages(1);
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
					var totalamt = data.totalamt;
					var totalcurr = data.totalcurr;
					var totalretailcurr = data.totalretailcurr;
					if(totalamt||totalcurr||totalretailcurr){
						data.footer = [{
							ROWNUMBER: "合计",
							AMOUNT: totalamt,
							CURR: totalcurr,
							RETAILCURR: totalretailcurr,
						}];
					}
					$('#pp_total').html('共有'+totals+'条记录');
					$("#pp").setPage({
				        pageCount:Math.ceil(Number(totals)/ pagecount),
				        current: Number(page)
					 });
					$('#gg').datagrid('loadData', data);
// 					$('#gg').datagrid('reloadFooter',footer);
					$('#gg').datagrid('scrollTo',0);
					$('#gg').datagrid('resize');
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
	<!-- 生产商帮助列表 -->
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
	<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>