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
<title>客户库存资源明细账</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<style type="text/css">
#warekcdetailt th,#warekcdetailt td{
	padding: 3px 5px;
}
#warekcdetailt .td_zero{
	color: #aaa;
}
</style>
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
		<!--input class="btn1" type="button" value="查询" onclick="inquire()"-->
		<div class="fl">
		<input type="button" id="highsearch"class="btn2"value="条件搜索▲" onclick="toggle()">
		<input id="csearch" class="btn1" type="button" onclick="toggle();searchdatas();" value="查找" />
		<input id="qclear" class="btn1" type="button" value="清除内容" onclick="clearAll()"/>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',defaulthzfs.showhxcm);">
</div>
</div>
	<!-- 隐藏查询条件 -->
<div id="hsearch" data-enter="toggle();searchdatas();" style="clear:both;width:100%;">
<div class="search-box">
<div class="s-box"><div class="title">查询日期</div>
<div class="text"><input id="nowdate" type="text" class="easyui-datebox" editable="true" required="required" currentText="today"  style="width:162px;height:25px"></input>
</div>
</div>
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input type="text" class="edithelpinput cust_help" data-value="#scustid"
					name="scustname" id="scustname" data-options="required:true" style="width:125px;height:24px;"
					placeholder="<选择或输入>" ><span onclick="selectdx_cust('search')" ></span><input type="hidden" id="scustid" value="">
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

<div class="s-box"><div class="title">库存情况</div>
<div class="text"><select id="kucun"  class="sele1" onchange="changevalue()">
                    	<option value="0">有库存</option>
                        <option value="1">无库存</option>
                        <option value="2">指定范围</option>
                        <option value="3" selected="selected">所有</option>
                    </select>
</div>
</div>
<div class="s-box"><div class="title">库存数量</div>
<div class="text"><input type="text" id="minamt" disabled="disabled" size="6"
					onkeyup="value=value.match(/^-?[0-9]\d*$/)||value.match(/-?/)"
					onafterpaste="this.value=this.value.replace(/-?\D/g,'')"  />-<input
					type="text" id="maxamt" disabled="disabled" size="6"
					onkeyup="value=value.match(/^-?[0-9]\d*$/)||value.match(/-?/)"
					onafterpaste="this.value=this.value.replace(/-?\D/g,'')" />
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
<div class="s-box"><div class="title">退货截止</div>
<div class="text"><input type="text" name="sretday" id="sretday" maxlength="20" class="wid160 hig25" placeholder="<输入天数>" >
</div>
</div>
	</div>
</div>
</div>
<div style="margin: 5px">
	<table id="gg" style="overflow: hidden;height:900px"></table>
</div>
<div class="page-bar">
	<div class="page-total" id="pp_total">共有0条记录</div>
	<input type="hidden" id="total" value="1">
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>

<!-- 选择xls窗 -->
<div id="downxlsd" title="请选择下载类型" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 80px 10px 10px 10px;text-align: center;">
<input type="button" class="btn1" value="下载（无尺码）" onclick="exportxls(0)"> 
<input type="button" class="btn1" value="下载（有尺码）" onclick="exportxls(1)"> 
</div>
<div class="dialog-footer">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#downxlsd').dialog('close')">
</div>
</div>

<div id="photodiv" title="" style="width: 400px; height: 400px;" class="easyui-dialog" closed="true" data-qickey>
		<img src="" id="imgdiv" style="width: 99%; height: 99%;">
</div>
<!-- 设置 -->
<div id="settabled" title="设置" data-options="modal:true" style="width:300px;height:300px" class="easyui-dialog" closed="true">
<label style="margin:10px;">显示列：</label>
<ul style="width:200px;margin: 5px auto;text-align: left;overflow: auto;" id="cols_ul">
	
</ul>
</div>
<div id="warekcdetaild" title="<span id='kctitle'></span>库存明细" data-options="modal:true" style="max-width: 100%;min-width:400px;max-height: 100%" class="easyui-dialog" closed="true">
<div style="text-align: center;">
<table id="warekcdetailt" class="table1">
</table>
</div>
</div>
<script type="text/javascript"> 
var searchb=true;
var dheight=85;
var rownumbers=1;

var showentersale = 1;
setqxpublic();
var epno = getValuebyName("EPNO");
var pgid = "pg1717";

//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();
}  
function changeDivHeight(){               
  var height = document.documentElement.clientHeight;//获取页面可见高度 
  $('#gg').datagrid('resize',{
  	height: height-50
  });
}

function setshowcols(){
	$('#cols_ul').html('');
	for(var i in selectshowcols){
		var name = selectshowcols[i].name;
		var cols = selectshowcols[i].cols;
		var hidden =  selectshowcols[i].hidden;
		var showabled = selectshowcols[i].showabled;
		var checked = hidden==true?"":"checked";
		var style = showabled==true?"":"display:none;";
		var html = '<li style="'+style+'"><label><input style="margin: 5px;" type="checkbox" class="showcolsbj" '+checked+'>'+name+'</label></li>';
		$('#cols_ul').append(html);
	}
	$('#gg').datagrid('resize');
}

	//设置弹出框的属性
	$(function(){
		//设置日期框默认值
		var myDate = new Date(top.getservertime());
		$('#nowdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		//生成年份 
		var year=myDate.getFullYear();
		var on=year-5;
		var end=year+1;
		for(var i = on;i <= end ; i++)
		{
		    $("#sprodyear").append("<option value="+i+">"+i+"</option>");
		}
		$('#gg').datagrid({
			idField : 'wareinm',
			height : $('body').height()-50,
			//fitColumns : true,
			striped : true, //隔行变色
			showFooter:true,
			nowrap :false,//允许自动换行
			singleSelect : true,
			pageSize : 20,
			onSortColumn: function(sort,order){
				sortarr = sort.split(",");
				orderarr = order.split(",");
				sortarrs = [];
				for(var i in sortarr){
					var sort = sortarr[i];
					var order = orderarr[i];
					if(sort=="SIZENAME") sort="SIZENO";
					sortarrs.push(sort+" "+order);
				}
				sortlist = sortarrs.join(",");
				if(sortlist==" ") sortlist = ""; 
				$('#pp').refreshPage();
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
				},
			toolbar: '#toolbars'
		}).datagrid("keyCtr","");
		$("#pp").createPage({
			pageCount:1,//总页码
			current:1,//当前页 
	        backFn:function(p){
	        	rownumbers=p;
	        	searchpages(p);
	        }
		 });
		alerthide();
	});
	//清空表单搜索条件 
	function clearAll() {
			var myDate = new Date(top.getservertime());
			$('#nowdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
			$('#shzfs').val('0');
			$('#ssortid').val('1');
			$('#scustid').val('');
			$('#scustname').val('');
			$('#sbrandname').val('');
			$('#sbrandid').val('');
			$('#sseasonname,#slocale,#sareaid,#sareaname').val('');
			$('#years').val('');
			$('#kucun').val('3');
			$('#sfullname,#stypeid,#swareno,#swareid,#swarename,#sprodyear,#ssizename,#ssizeid,#scolorid,#scolorname,#sareaname,#sprovid,#sprovname').val('');
			$('#sssdays').val('30');
			$('#minamt').attr("disabled","disabled");
			$('#maxamt').attr("disabled","disabled"); 
		}
	function toggle(){
		$("#hsearch").slideToggle('fast');
		if(searchb){
			searchb=false;
			$('#highsearch').val('条件搜索 ');
			$("#csearch").hide();
			$("#qclear").hide();
		}else{
			searchb=true;
			$('#highsearch').val('条件搜索▲');
			$("#csearch").show();
			$("#qclear").show();
		}
		setTimeout(function(){
			 $('#gg').datagrid('resize',{
				  	height: $('body').height()-50
				  });
		}, 200);
	}

	function changevalue(){
		var val=$('#kucun').val();
		if(val==2){
			$('#minamt').attr("disabled",false);
			$('#maxamt').attr("disabled",false); 
			$('#minamt').val("-9999");
			$('#maxamt').val("9999");
		}else{
			$('#minamt').attr("disabled","disabled");
			$('#maxamt').attr("disabled","disabled"); 
			$('#minamt').val("");
			$('#maxamt').val("");
		}
	}
		

//分页设置
function pp(){
	hide=$('#shzfs').val();
	if(hide==0)
		$('#gg').datagrid('hideColumn','COLORNAME').datagrid('resize');
	else
		$('#gg').datagrid('showColumn','COLORNAME').datagrid('resize');
		getinventorydata();
		getinventoryl('1');
}
//var sizenum = '<//%//=session.getAttribute("sizenum")%>';
var sizenum=getValuebyName('SIZENUM');
var cols = Number(sizenum)<=5?5: Number(sizenum);

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
var validehzsj = function(ishz,hzname,hzsjname){
	if(ishz&&hzsjname.indexOf("数量")==-1){
		alerttext("汇总项：数量必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("成本价")>=0&&hzsjname.indexOf("成本额")==-1){
		alerttext("汇总项：成本额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("零售价")>=0&&hzsjname.indexOf("零售额")==-1){
		alerttext("汇总项：零售额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("打包价")>=0&&hzsjname.indexOf("打包额")==-1){
		alerttext("汇总项：打包额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("售价一")>=0&&hzsjname.indexOf("售价一额")==-1){
		alerttext("汇总项：售价一额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("售价二")>=0&&hzsjname.indexOf("售价二额")==-1){
		alerttext("汇总项：售价二额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("售价三")>=0&&hzsjname.indexOf("售价三额")==-1){
		alerttext("汇总项：售价三额必选");
		$('#span_hzlist').val("点击选择");
		return false;
	}else if(ishz&&hzsjname.indexOf("批发价")>=0&&hzsjname.indexOf("批发额")==-1){
		alerttext("汇总项：批发额额必选");
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
	var nowdate = $("#nowdate").datebox('getValue');//开始日期 
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
	var custid = $("#scustid").val();//客户
	var provid = $("#sprovid").val();//客户
	var locale = $('#slocale').val(); //货位
	var xsid = $("#kucun").val();//库存数量
	var minamt = $("#minamt").val();//最小库存
	var maxamt = $("#maxamt").val();//最大库存
	var retday = $("#sretday").val();//最大库存
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
	currentdata["erpser"] = "repkhkczy";
	if(defaulthzfs.showhxcm==1&&hzlist.grouplist!="") currentdata["sizenum"] = sizenum;
	currentdata["fieldlist"] = hzlist.fieldlist;
	currentdata["grouplist"] = hzlist.grouplist;
	currentdata["calcfield"] = hzlist.calcfield;
	currentdata["sumlist"] = hzlist.sumlist;
	currentdata["sortlist"] = sortlist;
	currentdata["xsid"] = xsid;
	currentdata["minamt"] = minamt;
	currentdata["maxamt"] = maxamt;
	currentdata["retday"] = retday;
	currentdata["rows"] = pagecount;
	currentdata["page"] = 1;
	var dataparam = {
		erpser: "totalkhkczy",
		custidlist: custid,
		nowdate: nowdate,
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
	}
	searchtotal(dataparam);
}
//查询钱汇总数据
function searchtotal(dataparam){
	alertmsg(6);
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
					var totalamt = data.totalamt;
					var totalcurr = data.totalcurr;
					var totalwholecurr = data.totalwholecurr;
					var totalretailcurr = data.totalretailcurr;
					if(totalamt||totalcurr||totalwholecurr||totalretailcurr){
						data.footer = [{
							ROWNUMBER: "合计",
							AMOUNT: totalamt,
							CURR: totalcurr,
							WHOLECURR: totalwholecurr,
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
<!-- 客户帮助列表 -->
<jsp:include page="../HelpFiles/CustHelpList.jsp"></jsp:include>
<!-- 生产商帮助列表 -->
<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 颜色帮助列表 -->
<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
<!-- 尺码帮助列表 -->
<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpFiles/AreaHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
</body>
</html>