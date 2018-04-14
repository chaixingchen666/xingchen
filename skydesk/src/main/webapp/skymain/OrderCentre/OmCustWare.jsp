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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>客户订货实时监控</title>
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
<style type="text/css">
.box_custlist {
    float: left;
    display: block;
    width: 300px;
    line-height: 20px;
    height: auto;
}
.div_omcustname{
	margin: 5px;
}
.box_custlist input {
    margin-left: 5px;
}

.div_omepnamelist ul,.ul_omcustlist {
    list-style: none;
    display: block;
    line-height: 20px;
}
.span_omepname{
	color: #00959a;
	font-size: 14px;
}
.div_omepnamelist ul li,ul.ul_omcustlist li{
    float: left;
    cursor: pointer;
    border: 1px solid #00959a;
    color: #00959a;
    padding: 5px;
    margin: 5px;
}
.div_omepnamelist ul li.checked,.ul_omcustlist li.checked{
 	border: 1px solid #ff7900;
 	color: #ff7900;
 	font-weight: 600;
}
li.more_cust{
	background: #00959a;
	color: #fff !important;
}

</style>
</head>
<body class="easyui-layout layout panel-noscroll">
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
<input type="button" class="btn2" id="highsearch" name="highsearch" value="查询条件▲" onclick="toggle()">
 <span id="serbtn" class="fr">
<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchdatas();toggle();">
<input type="button" id="resetser" class="btn2" value="清空条件" onclick="resetsearch()">
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'ords',defaulthzfs.showhxcm);">
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
<div class="s-box"><div class="title">客户</div>
<div class="text"><input class="edithelpinput cust_help" type="text" id="scustname" name="scustname" placeholder="<选择或输入>"
						data-value="#scustid" maxlength="20"><span onclick="selectdx_cust('search')"></span>
						<input type="hidden" id="scustid" name="scustid">
</div>
</div>
<div class="s-box"><div class="title">订货人</div>
<div class="text"><input class="edithelpinput" type="text" id="somepname" name="somepname" placeholder="<选择或输入>"
						data-value="#somepid" maxlength="20" readonly><span onclick="selectdx_omep('search')"></span>
						<input type="hidden" id="somepid" name="somepid">
</div>
</div>
<div class="s-box"><div class="title">陈列杆</div>
<div class="text"><input class="edithelpinput pole_help" type="text" id="spolename" name="spolename" placeholder="<选择或输入>"
						data-value="#spoleid" maxlength="20" readonly><span onclick="selectdx_pole('search')"></span>
						<input type="hidden" id="spoleid" name="spoleid">
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
<!-- 
<div  class="easyui-dialog" closed="true" id="omcustlistd" title="请选择客户" style="width:530px;max-height: 500px;">
<div class="help-qstoolbar">
<input type="text" placeholder="搜索业务员名称<回车搜索>" class="help-qsipt" id="omcustfindbox" data-enter="getallomcust(1)"  data-end="yes">
<input type="button" class="btn2" value="搜索" onclick="getallomcust(1)" > 
</div>
<div id="div_omcustlist" style="overflow: hidden;zoom:1;">
<ul class="ul_omcustlist">
<li class="more_cust">加载更多</li>
</ul>
</div>
<div class="dialog-footer" style="text-align: center;position: relative;display: block;">
	<input id="add" class="btn1" type="button" type="button" value="确定" onclick="selectomcust();">
	<input id="add" class="btn3" type="button" type="button" value="清空选择" onclick="clearomcust();">
</div>
</div>
 -->
<script type="text/javascript">
//权限设置
setqxpublic();
var searchb = false;
var opser;
var pgid="pg1813";
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight(){
	var height = document.documentElement.clientHeight; //获取页面可见高度  
	$('#gg').datagrid('resize', {
		height: height - 50
	});
}
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
	$('#sntid').val('2');
	var idstr = "#scustname,#scustid,#spolename,#spoleid,#somepname,#somepid"
		+",#swareno,#swareid,#swarename,#sbrandname"
		+",#sbrandid,#sseasonname,#sprodyear";
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
function jsonhaskey(json,key,value) {
	for (var i in json) {
		if(key==""){
			if (json[i] == value)
				return true;
		}else if (json[i][key] == value)
			return true;
	}
	return false;
}
function deljsonbykey(json,key,value){
	for (var i in json) {
		if(key==""){
			if (json[i] == value)
				json.splice(i,1);
			else
				continue;
		}else if (json[i][key] == value)
			json.splice(i,1);
		else
			continue;
	}
} 
var custjson = [];
$(function(){
	/*$('ul.ul_omcustlist').delegate('li','click',function(e){
		$li = $(this);
		var custid = $li.data('custid');
		var custname = $li.text();
		var Checked = $li.hasClass('checked');
		if(Checked){
			$li.removeClass('checked');
			deljsonbykey(custjson,"custid",custid);
		}else{
			$li.addClass('checked');
			var json = {
					custid: custid,
					custname: custname
			};
			custjson.push(json);
		}
	});*/
	setomcustwaret();
	setyear();
});
function selectomcust(){
	var custnamelist = "";
	var custidlist = "^";
	for(var i=0;i<custjson.length;i++){
		custnamelist += custjson[i].custname+"，";
		custidlist += custjson[i].custid+"^";
	}
	if(custjson.length==0)
		custidlist = "";
	$('#scustname').val(custnamelist);
	$('#scustid').val(custidlist);
	$('#omcustlistd').dialog('close');
}
function clearomcust(){
	$('ul.ul_omcustlist li').removeClass("checked");
	custjson=[];
}
function getallomuser(page){
	var omid = Number(top.omid);
	if(omid>0){
		alertmsg(2);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "listallomuser",
				omid: omid,
				page: page,
				rows: pagecount
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)){
						if(data.total>0){
							var rows = data.rows;
							var custname = "";
							var omepname = "";
							var html = "";
							for(var i=0;i<rows.length;i++){
								var row = rows[i];
								if(custname != row.CUSTNAME){
									if(custname!="")
										html+='</ul></div></div>';
									custname = row.CUSTNAME;
									omepname = row.OMEPNAME;
									html += '<div class="box_custlist">'
									+'<div class="div_omcustname">'
									+'<label class="label_omepname"><span class="span_omepname">'
									+ custname
									+'</span><input type="checkbox" data-custid="'+row.CUSTID+'">全选'
									+'</label>'
									+'</div>'
									+'<div class="div_omepnamelist">'
									+'<ul>';
									html +='<li data-omepid="'+row.OMEPID+'">'+omepname+'</li>';
								}else{
									omepname = row.OMEPNAME;
									html +='<li data-omepid="'+row.OMEPID+'">'+omepname+'</li>';
								}
							}
							html +='</ul></div></div>';
						}
						$("#div_omcustlist").html(html);
					}
				}catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}else
		alerttext("请选择订货会！");
}
function openomcust(){
	$('#omcustlistd').dialog('open');
	getallomcust(1);
}
function getallomcust(page){
	var omid = Number(top.omid);
	if(omid>0){
		var findbox = $('#omcustfindbox').val();
		if($('ul.ul_omcustlist').find('li:not(.more_cust)').length>0){
			return;
		}
		if(page==1)
			$("ul.ul_omcustlist li:not(.more_cust)").remove();
		alertmsg(2);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "listomcustomerin",
				omid: omid,
				findbox: findbox,
				page: page,
				rows: pagecount
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)){
						if(data.total>0){
							var rows = data.rows;
							var $mli = $("ul.ul_omcustlist li.more_cust");
							for(var i=0;i<rows.length;i++){
								var row = rows[i];
								var html = "";
								var custname = "";
								custname = row.CUSTNAME;
								var checked = "";
								if(jsonhaskey(custjson,"custid",Number(row.CUSTID)))
									checked = "checked";
								html = '<li data-custid="'+Number(row.CUSTID)+'" class="'+checked+'"">'+custname+'</li>';
								$mli.before(html);
							}
							var l = $('ul.ul_omcustlist').find('li:not(.more_cust)').length;
							if(l<data.total)
								$mli.show();
							else
								$mli.hide();
						}
					}
				}catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}else
		alerttext("请选择订货会！");
}
function setomcustwaret(){
	$("#pp").createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
			searchpages(p);
		}
	});
	//加载数据
	$('#gg').datagrid(
			{
				idField : 'KEYID',
				width : '100%',
				height : $('body').height() - 50,
				fitColumns : true,
				striped : true, //隔行变色
				nowrap: false,
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
				onSelect: function(index,row){
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
}
var validehzsj = function(ishz,hzname,hzsjname){
	if(ishz&&hzsjname.indexOf("数量")==-1){
		alerttext("汇总项：数量必选");
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
	var omid = Number(top.omid);
	var wareno= $("#swareno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var brandid = $("#sbrandid").val();//品牌
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var custid = $("#scustid").val();//生产商
	var omepid = $('#somepid').val(); //订货人
	var poleid = $('#spoleid').val(); //陈列杆
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
	currentdata["erpser"] = "repcxomorder";
	currentdata["omid"] = omid;
	if(defaulthzfs.showhxcm==1&&hzlist.grouplist!="") currentdata["sizenum"] = sizenum;
	currentdata["fieldlist"] = hzlist.fieldlist;
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["calcfield"] = hzlist.calcfield;
	currentdata["sumlist"] = hzlist.sumlist;
	currentdata["sortlist"] = sortlist;
	currentdata["wareno"] = wareno;
	currentdata["warename"] = warename;
	currentdata["wareid"] = wareid;
	currentdata["brandidlist"] = brandid;
	currentdata["typeid"] = typeid;
	currentdata["prodyear"] = prodyear;
	currentdata["seasonname"] = seasonname;
	currentdata["custidlist"] = custid;
	currentdata["omepidlist"] = omepid;
	currentdata["poleidlist"] = poleid;
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
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
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
							RETAILCURR: totalretailcurr
						}];
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
</script>
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/CustHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/OmUserHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/OmPoleHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>