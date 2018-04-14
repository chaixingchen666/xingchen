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
<title>进销存分析</title>
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
<input type="button" class="btn3" type="button" value="设置" onclick="opensetcolumnd('#gg')">
	        <span class="sepreate"></span>
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',-1);">
</div>
</div>
	<!-- 隐藏查询条件 -->
<div id="hsearch"  style="font-size:12px;clear:both"  data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">汇总方式</div>
<div class="text"><select style="width: 163px; height: 28px" id="shzfs"
						name="shzfs">
							<option value="0" selected>商品</option>
							<option value="1">商品+颜色</option>
							<option value="2">商品+颜色+尺码</option>
					</select>
</div>
</div>
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input id="smindate" editable="true" type="text" 
required="required" currentText="today" class="easyui-datebox"  style="width:164px;height:25px"></input>
</div>
</div>
				
		<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input id="smaxdate" editable="true" type="text" class="easyui-datebox" 
required="required" currentText="today"  style="width:164px;height:25px"></input>
</div>
</div>
				
<div class="s-box"><div class="title">生产商</div>
<div class="text"><input type="text"
					class="edithelpinput provd_help"  data-value="#sprovid"
					name="sprovname" id="sprovname"
					 data-options="required:true"
					placeholder="<选择或输入>"><span onclick="selectdx_prov('search')" ></span> <input type="hidden" id="sprovid" value="">
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input type="text"
					class="edithelpinput house_help"  data-value="#shouseid"
					name="shousename" id="shousename"
					 data-options="required:true"
					placeholder="<选择或输入>"><span onclick="selectdx_house('search')" ></span> <input type="hidden" id="shouseid" value="">
</div>
</div>
		<div class="s-box"><div class="title">货号</div>
<div class="text"><input  type="text"  class="edithelpinput wareno_help" data-value="#swareid"
				name="awareno" id="swareno" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>"><span onclick="selectware('search')"></span>
				 <input type="hidden" id="swareid" value="">
</div>
</div>
				
		<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>">
				<span onclick="selectdx_brand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
</div>
</div>

		<div class="s-box"><div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>"><span onclick="selectwaretype('search')"></span>
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
<div class="text"><select id="sprodyear"
					style="width: 164px; height: 25px">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>
<div class="s-box"><div class="title">可销天数</div>
<div class="text">
<input type="text" id="sminxsday" class="wid60 hig25">
-
<input type="text" id="smaxxsday" class="wid60 hig25">
</div>
</div>
<div class="s-box"><div class="title">进销比</div>
<div class="text">
<input type="text" id="sminjxrate" class="wid60 hig25" placeholder="<输入>">
-
<input type="text" id="smaxjxrate" class="wid60 hig25" placeholder="<输入>">
</div>
</div>
<div class="s-box" style="width:300px;">
<div class="title">上货日期</div>
<div class="text" style="width:220px">
<input type="text" id="sminssdate" class="easyui-datebox"  style="width:100px;height:25px">
-
<input type="text" id="smaxssdate" class="easyui-datebox"  style="width:100px;height:25px">
</div>
</div>
<div class="s-box"><div class="title">退货截止</div>
<div class="text"><input type="text" name="sretday" id="sretday" maxlength="20" class="wid160 hig25" placeholder="<输入天数>" >
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
var searchb=true;
var dheight=80;
var rownumbers=1;
var pgid = "pg1511";
//是否为搜索时函数内部触发调用排序
var isSearchSort = false;
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
	window.onresize=function(){  
	     changeDivHeight();  
	}  
	function changeDivHeight(){               
	  var height = document.documentElement.clientHeight;//获取页面可见高度 
		$('#gg').datagrid('resize',{
	  	height:height-50
	  });
	}

	//设置弹出框的属性
	$(function(){
		//设置日期框默认值
		var myDate = new Date(top.getservertime());
		$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		$('#sminssdate,#smaxssdate').datebox('setValue', "");
		//生成年份 
		var year=myDate.getFullYear();
		var on=year-5;
		var end=year+1;
		for(var i = on;i <= end ; i++)
		{
		    $("#sprodyear").append("<option value="+i+">"+i+"</option'>");
		}
		$("#pp").createPage({
			pageCount:1,//总页码
			current:1,//当前页 
	        backFn:function(p){
	        	rownumbers=p;
	        	searchpage(p.toString());
	        }
		 });
		$('#gg').datagrid(
				{
					idField : 'NOTEDATE',
					width : '100%',
					height : Number($('body').height())-50,
					nowrap: false,
					fitColumns : true,
					striped : true, //隔行变色
					singleSelect : true,
					multiSort: true,//允许多列排序
					showFooter:true,
					frozenColumns: [[{
						field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',//halign表示对齐头部
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
						hidden: true,
						align:'center',
						halign:'center'
					}, {
						field : 'SIZENAME',
						title : '尺码',
						width : 60,
						sortable:true,
						fixed: true,
						hidden: true,
						align:'center',
						halign:'center'
					}]],
					columns : [ getpgcolumnarray([{
						field : 'PROVNAME',
						title : '生产商',
						width : 100,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center'
					},{
						field : 'PRODNO',
						title : '厂家编码',
						width : 100,
						fixed: true,
						sortable: true,
						align:'center',
						halign:'center'
					},{
						field : 'HOUSENAME',
						title : '店铺',
						width : 100,
						fixed: true,
						sortable: true,
						align:'center',
						halign:'center'
					},{
						field : 'SSDATE',
						title : '上市日期',
						width : 100,
						fixed: true,
						hidden: true,
						sortable:true,
						align:'center',
						halign:'center'
					},{
						field : 'SHDATE',
						title : '上货日期',
						width : 100,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center'
					},{
						field : 'XSDAY',
						title : '销售天数',
						width : 100,
						fieldtpe: "G",
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm
					},{
						field : 'AMOUNTKC0',
						title : '期初库存数',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm,
						styler: zeroCellStyle
					},{
						field : 'CURRKC0',
						title : '期初库存额',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : currfm,
						styler: zeroCellStyle
					},{
						field : 'AMOUNTJH',
						title : '进货数量',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm
					},{
						field : 'NUMJH',
						title : '进货次数',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm
					},{
						field : 'AMOUNTJT',
						title : '退货数量',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm
					},{
						field : 'NUMJT',
						title : '退货次数',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm
					},{
						field : 'AMOUNTXS',
						title : '销售数量',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter :amtfm
					},{
						field : 'AMOUNTKC',
						title : '期末库存数',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : amtfm,
						styler: zeroCellStyle
					},{
						field : 'CURRKC',
						title : '期末库存额',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : currfm,
						styler: zeroCellStyle
					},{
						field : 'AVGXS',
						title : '平均日销',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : currfm
					},{
						field : 'MAXXSDAY',
						title : '可销天数',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align:'center',
						halign:'center',
						formatter : currfm
					}, {
						field : 'JXRATE',
						title : '进销比',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align : 'right',
						halign : 'center',
						formatter : ratefm
					}, {
						field : 'SXRATE',
						title : '售罄率',
						fieldtpe: "G",
						width : 70,
						fixed: true,
						sortable:true,
						align : 'right',
						halign : 'center',
						formatter : ratefm
					}, {
						field : 'NUMZZ',
						title : '周转次数',
						fieldtpe: "N",
						width : 70,
						fixed: true,
						align:'center',
						halign:'center',
						formatter: amtfm
					}, {
						field : 'RETAILSALE',
						title : '零售价',
						fieldtpe: "N",
						width : 70,
						fixed: true,
						align:'right',
						halign:'center',
						formatter: currfm
						}])],
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
						if (isSearchSort === false) {
                            $('#pp').refreshPage();
						}
					},
					pageSize : 20,
					toolbar: "#toolbars"
				});
		alerthide();
		var levelid = Number(getValuebyName("GLEVELID"));
		if(levelid==1||levelid==2||levelid==7){
			$('#shousename').next().hide();
			$('#shousename').attr('readonly',true)
			$('#shouseid').val(Number(getValuebyName('HOUSEID')));
			$('#shousename').val(getValuebyName('HOUSENAME'));
		}
	});
	//清空表单搜索条件 
	function clearAll() {
		var myDate = new Date(top.getservertime());
		$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		$('#sminssdate,#smaxssdate').datebox('setValue', "");
		$("#shzfs").val("0");
		$('#shousename,#shouseid,#swareno,#swareid,#sbrandname,#sretday,#sbrandid,#sprovid,#sprovname,#sfullname,#stypeid,#sseasonname,#sprodyear,#sminxsday,#smaxxsday,#sminjxrate,#smaxjxrate').val('');
		var levelid = Number(getValuebyName("GLEVELID"));
		if(levelid==1||levelid==2||levelid==7){
			$('#shousename').next().hide();
			$('#shousename').attr('readonly',true)
			$('#shouseid').val(Number(getValuebyName('HOUSEID')));
			$('#shousename').val(getValuebyName('HOUSENAME'));
		}
	}
	function toggle(){
		$("#hsearch").slideToggle('fast');
		if(searchb){
			searchb=false;
			dheight=80;
			$('#highsearch').val('条件搜索');
			$("#csearch").hide();
			$("#qclear").hide();
		
			
		}else{
			searchb=true;
			dheight=218;
			$('#highsearch').val('条件搜索▲');
			$("#csearch").show();
			$("#qclear").show();
			
		}
		setTimeout(function(){
			$('#gg').datagrid('resize',{
				height : $('body').height()-50
			});
		}, 200);
	}

//分页设置
function searchdata() {
	$('#gg').datagrid('loadData',nulldata);
	var mindate = $("#smindate").datebox('getValue');//开始日期
	var maxdate = $("#smaxdate").datebox('getValue');//结束日期 
	var hzfs = Number($("#shzfs").val());//汇总方式
	var houseid = $("#shouseid").val();//店铺
	var wareno= $("#swareno").val();//货号no
	var warename = $("#swarename").val();//商品名称
	var wareid= $("#swareid").val();//货号id
	var brandid = $("#sbrandid").val();//品牌
	var typeid = $("#stypeid").val();//类型
	var prodyear = $("#sprodyear").val();//生产年份
	var seasonname = $("#sseasonname").val();//季节
	var provid = $("#sprovid").val();//生产商
	var retday = $("#sretday").val();//退货截止天数
	typeid = typeid == "" ? "-1": typeid;
	var minssdate = $("#sminssdate").datebox('getValue');//上货日期范围
	var maxssdate = $("#smaxssdate").datebox('getValue');
	var $dg = $("#gg");
	switch (hzfs) {
	case 0:
		$dg.datagrid("hideColumn","COLORNAME");
		$dg.datagrid("hideColumn","SIZENAME");
		sortlist = "wareno asc";
        isSearchSort = true;
		$dg.datagrid("sort",{
			sortName: "WARENO",
			sortOrder: "asc"
		});
		break;
	case 1:
		$dg.datagrid("showColumn","COLORNAME");
		$dg.datagrid("hideColumn","SIZENAME");
		sortlist = "WARENO asc,COLORNAME asc";
        isSearchSort = true;
		$dg.datagrid("sort",{
			sortName: "WARENO,COLORNAME",
			sortOrder: "asc,asc"
		});
		break;
	case 2:
		$dg.datagrid("showColumn","COLORNAME");
		$dg.datagrid("showColumn","SIZENAME");
		sortlist = "WARENO asc,COLORNAME asc,SIZENO asc";
        isSearchSort = true;
		$dg.datagrid("sort",{
			sortName: "WARENO,COLORNAME,SIZENAME",
			sortOrder: "asc,asc,asc"
		});
		break;
	default:
		break;
	}
    isSearchSort = false;
	alertmsg(6);
	var showHouse = 0;
    var option = $dg.datagrid("options");
    var column = option.columns[0];
    console.log(column);
    for(var i in column){
        var col = column[i];
        var field = col.field;
        if (field == 'HOUSENAME') {
            if (col.hidden == false) {
                showHouse = 1;
			}
			break;
		}
    }
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		dataType : 'json',
		data : {
			erpser : "totaljxctj",
			mindate : mindate,
			maxdate : maxdate,
			hzfs : hzfs,
			houseidlist : houseid,
			wareid : wareid,
			wareno: wareno,
			warename: warename,
			brandidlist: brandid,
			typeid : typeid,
			prodyear : prodyear,
			providlist: provid,
			seasonname : seasonname,
            havehouse : showHouse,
			retday: retday,
			minssdate: minssdate,
			maxssdate: maxssdate
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		success : function(data) { //回调函数，result，返回值 
			try{
				if (valideData(data)) {
					footer = [{
						ROWNUMBER: "合计",
						AMOUNTJH: data.AMOUNTJH,
						AMOUNTJT: data.AMOUNTJT,
						AMOUNTXS: data.AMOUNTXS,
						CURRKC0: data.CURRKC0,
						AMOUNTKC0: data.AMOUNTKC0,
						CURRKC: data.CURRKC,
						AMOUNTKC: data.AMOUNTKC
					}];
					searchpage(1);
				}
			}catch(err){
				console.error(err.message);
			}
		}
	});
		}
// var sort = "wareid";
// var order = "asc";
var sortlist = "wareid asc";
var currentdata = {};
var footer = [];
//分页获取
function searchpage(page) {
    //alert('sck--' + k);
	$('#gg').datagrid('loadData',nulldata);	
	var minxsday = $('#sminxsday').val();//销售天数范围
	var maxxsday = $('#smaxxsday').val();
	var minjxrate = $('#sminjxrate').val();//进销比范围
	var maxjxrate = $('#smaxjxrate').val();
	var hzfs = Number($("#shzfs").val());//汇总方式
	currentdata = {
			erpser : "listjxctj",
			minxsday: minxsday,
			maxxsday: maxxsday,
			minjxrate: minjxrate,
			maxjxrate: maxjxrate,
			hzfs: hzfs,
			rows: pagecount,
			fieldlist: "a.houseid,d.housename,f.provname,i.locano,j.areaname,g.brandname,a.ssdate,a.xsday,a.maxxsday,a.jxrate,a.avgxs,e.shdate,a.sxrate,a.amountjh,a.numjh,a.amountjt,a.numjt,a.amountxs,a.amountkc,b.prodno,b.retailsale,a.currxs,a.amountkc0,a.numzz,a.currkc,a.currkc0",
			sortlist: sortlist,
			page : page
		};
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : currentdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{if(valideData(data)) {
				var totals = data.total;
				$("#pp_total").html(totals);
				$("#pp").setPage({
			        pageCount:Math.ceil(Number(totals)/ pagecount),
			        current: Number(page)
				 });
				data.footer = footer;
				$('#gg').datagrid('loadData', data);
				$('#gg').datagrid('scrollTo',0);
				$('#gg').datagrid('resize');
			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
    //$('#gg').datagrid();
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
<jsp:include page="../HelpList/ColumnDialog.jsp"></jsp:include>
</body>
</html>