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
<title>商品库存报警设置</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<style type="text/css">
p{
	line-height: 16px;
}
p.nonep{
	color:#aaa;
}
input.warnamt {
 	margin: 3px;
    width: 50px;
    height: 25px;
    border: none;
    text-align: center;
}
ul#ul_house{
	text-align: left;
}
ul#ul_house li{
	padding: 3px;
}
.div_house{
	width:150px;
	vertical-align: top;text-align: left;
}
</style>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll" >
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<table border="0" cellspacing="5" class="fl-ml10">
		<tr>
		<td class="header" style="font-size:14px;">快速增加</td>
		<td id="uwaretd">
		<input type="hidden" id="uwareid">
		<input type="text" id="uwareno"  class="edithelpinput wareno_help" data-end="yes" data-value="#uwareid" data-event="onSelect: openwarnd"  placeholder="<选择或输入货号>">
		<span onclick="selectware('warewarn')"></span>
		</td>
		<td class="header">
		快速搜索
		</td>
		<td>
		<input type="text" data-end="yes" placeholder="输入货号或者商品名称<回车搜索>" class="ipt1" id="qswarewarnvalue" maxlength="20" data-enter="getwarewarndata()">
		<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
		<input type="button" class="searchbtn btn2 hide" type="button" value="搜索" onclick="searchwarewarn();toggle();"> 
		<input type="button" class="searchbtn btn2 hide" value="清空搜索条件" onclick="resetsearch()">	
		</td>
		</tr>
		</table>
	</div>
	<div class="fr">
	<input type="button" class="btn3" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
	</div>
</div>
<div class="searchbar" style="display: none" data-enter="searchwarewarn();toggle();">
<input type="hidden" id="stypeid">
<div class="search-box">
	<div class="s-box"><div class="title">店铺</div>
	<div class="text">
	<input class="edithelpinput house_help" data-value="#shouseid" id="shousename" placeholder="<输入>" type="text">
	<span class="s-btn" onclick="selecthouse('search')"></span>
	<input type="hidden" id="shouseid" value="">
	</div>
	</div>
	<div class="s-box">
	<div class="title">货号</div>
	<div class="text"><input class="edithelpinput" data-value="#swareid" id="swareno" maxlength="20" type="text" placeholder="<输入或选择>">
	<span onclick="selectware('search')"></span>
	<input type="hidden" id="swareid">
	</div>
	</div>
	<div class="s-box">
	<div class="title">商品名称</div>
	<div class="text"><input class="wid160 hig25" id="swarename" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">类型</div>
	<div class="text">
	<input class="edithelpinput" id="sfullname" data-value="#stypeid" maxlength="20" type="text" placeholder="<选择>">
	<span onclick="selectwaretype('search')"></span>
	</div>
	</div>
	<div class="s-box">
	<div class="title">品牌</div>
	<div class="text"><input class="edithelpinput brand_help" data-value="#sbrandid" id="sbrandname" maxlength="20" type="text" placeholder="<输入或选择>">
	<span onclick="selectbrand('search')"></span>
	<input type="hidden" id="sbrandid">
	</div>
	</div>
	<div class="s-box">
	<div class="title">季节</div>
	<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
	</div>
	</div>
	<div class="s-box">
	<div class="title">生产年份</div>
	<div class="text"><input class="wid160 hig25" id="sprodyear"  type="text" placeholder="<输入>">
	</div>
	</div>
	</div>
</div>
</div>

<table id="gg" style="overflow: hidden;"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">
	共有<span id="pp_total">0</span>条记录
</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<!-- 保存 -->
<div id="ud" title="设置商品库存预警" style="text-align: center;max-width:100%;max-height:100%;min-width:600px;" class="easyui-dialog" closed=true>
<!-- 选择店铺 -->
<table>
<tr>
<td class="div_house" >
<span style="color:#ff7900">预警店铺列表：</span>
<ul id="ul_house">
</ul>
</td>
<td>
<div class="div_ware">
<table id="utable_warn" class="table1" border="0" cellspacing="0">

</table>
</div>
</td>
</tr>
</table>
<!-- 填写商品上下限 -->
<div class="dialog-normal-footer">
<span>同款最低：<input type="text" class="hig25 tkminamt" style="width:50px" placeholder="<输入>"></span>
<span>同款最高：<input type="text" class="hig25 tkmaxamt" style="width:50px" placeholder="<输入>"></span>
<input type="button" id="selwarnhouse" class="btn1" onclick="selectdx_house('warewarn')" value="选择店铺">
	<input type="button" id="savebtn" class="btn1"  name="update" value="保存" onclick="savewarewarn()">
	<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
</div>
</div>
<script type="text/javascript">
//权限设置
setqxpublic();
var pgid= "pg1021";
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
var searchb = false;
var sizenum = getValuebyName("SIZENUM");
var cols = Number(sizenum)<=5?5: Number(sizenum);
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		searchb = false;
		$('.searchbtn').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('.searchbtn').show();
	}
	$('.searchbar').slideToggle('fast');
	$('#swareno').focus();
}
var sortlist = "HOUSENAME asc";
$(function(){
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getwarewarnlist(p);
		}
	});
	var column = [{
		field: 'KEYID',
		title: 'KEYID',
		width: 200,
		hidden: true
	},{
		field: 'ROWNUMBER',
		title: '序号',
		width: 50,
		fixed: true,
		align: 'center',
		halign: 'center',
		formatter: function(value, row, index) {
			var p = $("#pp").getPage() - 1;
			return p * pagecount + index + 1;
		}
	},{
		field: 'HOUSENAME',
		title: '店铺',
		width: 150,
		sortable: true,
		fixed: true,
		halign: "center",
		align: 'center'
	},{
		field: 'WARENO',
		title: '货号',
		width: 100,
		sortable: true,
		fixed: true,
		halign: "center",
		align: 'center'
	},{
		field: 'WARENAME',
		title: '商品名称',
		width: 100,
		sortable: true,
		fixed: true,
		halign: "center",
		align: 'center'
	},{
		field: 'COLORNAME',
		title: '颜色',
		width: 80,
		sortable: true,
		fixed: true,
		halign: "center",
		align: 'center'
	},{
		field: 'SIZENAME',
		title: '尺码',
		width: 80,
		sortable: true,
		expable: true,
		hidden: true,
		halign: "center",
		align: 'center'
	},{
		field: 'MINAMT',
		title: '最小库存',
		fieldtype: "G",
		width: 80,
		sortable: true,
		expable: true,
		hidden: true,
		halign: "center",
		align: 'center'
	},{
		field: 'MAXAMT',
		title: '最大库存',
		fieldtype: "G",
		width: 80,
		sortable: true,
		expable: true,
		hidden: true,
		halign: "center",
		align: 'center'
	}];

	for(var i =1; i<= cols;i++){
		column.push({
			field: 'AMT' + i,
			title: '<span id="title' + i + '"><span>',
			width: 80,
			expable: false,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				var maxamt = Number(row["MAX"+this.field]);
				if(row["MAX"+this.field]=="")
					maxamt = "无";
				else
					maxamt = isNaN(maxamt) ? 0 : maxamt;
				var minamt = Number(row["MIN"+this.field]);
				if(row["MIN"+this.field]=="")
					minamt = "无";
				else
					minamt = isNaN(minamt) ? 0 : minamt;
				var nonep1 = minamt == 0 ? "nonep": "";
				var nonep2 = minamt == 0 ? "nonep": "";
				if ((minamt==0 && maxamt==0) || row.WARENO == "合计" || (minamt=="无" && maxamt=="无")) return "";
				else return minamt+"-"+maxamt;
			}
		});
	}
	column.push({
		field: 'ACTION',
		title: '操作',
		width: 100,
		fixed: true,
		align: 'center',
		halign: 'center',
		formatter: function(value, row, index) {
			return '<input type="button" class="m-btn" value="删除" onclick="delwarewarn(' + index + ')">';
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'KEYID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		sortName: "HOUSENAME",
		sortOrder: "asc",
		columns: [column],
		multiSort: true,//允许多列排序
		onSortColumn: function(sort, order) {
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
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(index, row) {
			loadwarewarn(index);
		},
		onSelect: function(index, row) {
			if (row) {
				for (var i = 1; i <= sizenum; i++) {
					$('#title' + i).html(row["SIZENAME" + i]);
				}
			}
		},
		onLoadSuccess: function(data) {
			var sizelength = 0;
			var rows = data.rows;
			var $dg = $('#gg');
			for (var i = 0; i < rows.length; i++) {
				var item = rows[i];
				while (sizelength <= sizenum) {
					var s = sizelength + 1;
					if (item["SIZENAME" + s] != "" && item["SIZENAME" + s] != undefined) sizelength++;
					else break;
				}
			}
			for (var i = 1; i <= sizenum; i++) {
				if (i <= sizelength) $dg.datagrid("showColumn", "AMT" + i);
				else $dg.datagrid("hideColumn","AMT" + i);
			}
			$dg.datagrid('resize');
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatewarewarnd()");
	$("input.tkminamt,input.tkmaxamt").keyup(function(e){
		var key = e.which;
		if(key==13) return;
		var value= this.value;
		value =value.replace(/[^\d]/g,"");
		this.value = value;
		var $this = $(this);
		if(value.length>0&&value!=0){
			if($this.hasClass("tkminamt")){
				$("table#utable_warn input.minamt").val(value);
			}else{
				$("table#utable_warn input.maxamt").val(value);
			}
		}
	});
	
	getwarewarndata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	};
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=addwarewarnxls",
		xlsmobanname: "warewarn",
		channel: "warewarn"
	});
});

var currentdata = {};
var getwarewarnlist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName;
	var order = options.sortOrder;
	currentdata["erpser"] = "listwarewarn";
	currentdata["sizenum"] = sizenum;
	currentdata["sortlist"] = sortlist==""?"HOUSENAME asc":sortlist;
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
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
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$dg.datagrid('loadData', data);
					$('#pp_total').html(data.total);
					if(data.total>0) $dg.datagrid("selectRow",0);
					$("#uwareno").focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getwarewarndata() {
	var value = $('#qswarewarnvalue').val();
	currentdata = {
		findbox: value
	};
	getwarewarnlist(1);
}
//搜索
function searchwarewarn() {
	var wareno = $('#sewareno').val();
	var warename = $('#swarename').val();
	var brandid = $('#sbrandid').val();
	var typeid = $('#stypeid').val();
	var wareid = Number($('#swareid').val());
	var seasonname = $('#sseasonname').val();
	var prodyear = $('#sprodyear').val();
	var houseid = Number($('#shouseid').val());
	brandid = brandid==""?"-1":brandid;
	typeid = typeid==""?"-1":typeid;
	currentdata = {
		wareno: wareno,
		warename: warename,
		typeid: typeid,
		houseid: houseid,
		brandid: brandid,
		wareid: wareid,
		seasonname: seasonname,
		prodyear: prodyear
	};
	getwarewarnlist(1);
}
//清空搜索条件
function resetsearch() {
	var idstr = "#shouseid,#shousename,#swareno,#swarename,#stypeid,#sfullname"
			+",#sbrandid,#sbrandname,#sseasonname,#sprodyear";
	$(idstr).val("");
}
//弹出设置窗
function openwarnd() {
	var wareid = Number($("#uwareid").val());
	if (wareid > 0) {
		$("#utable_warn").html("");
		$("ul#ul_house").html("");
		$("#selwarnhouse").data("selobject",{name: "",value: "^"});
		$("input.tkminamt,input.maxamt").val("");
		alertmsg(6);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "waresizeandcolor2",
				wareid: wareid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						var warecolorjson = data.colorlist;
						var waresizejson = data.sizelist;
						if (!warecolorjson || warecolorjson.length == 0) {
							alerttext("该商品不存在有效颜色！");
							return;
						}
						if (!waresizejson || waresizejson.length == 0) {
							alerttext("该商品不存在有效尺码！");
							return;
						}
						var cl = warecolorjson.length;
						var sl = waresizejson.length;
						for (var i = 0; i <= cl; i++) {
							//创建tr
							var tr = document.createElement("tr");
							for (var j = 0; j <= sl; j++) {
								//alert(list);
								//创建td
								var td = document.createElement("td");
								if (i == 0) {
									tr.className = "table-header",
									td.style.border = "none";
									if (j == 0) {
									 	td.style.width='80px';
										td.innerHTML = '颜色';
									} else {
										td.style.width = '140px';
										td.innerHTML = waresizejson[j - 1].SIZENAME;
									}
								} else {
									if (j == 0) {
										td.innerHTML = warecolorjson[i - 1].COLORNAME;
									} else {
										td.className = "warnamounttd";
										var sizeid = waresizejson[j - 1].SIZEID;
										var colorid = warecolorjson[i - 1].COLORID;
										$(td).data("ware", {
											colorid: colorid,
											sizeid: sizeid
										});
										var $minipt = $('<input type="text" class="warnamt minamt" placeholder="<输入>">');
										var $maxipt = $('<input type="text" class="warnamt maxamt" placeholder="<输入>">');
										$minipt.keyup(function(){
											this.value = this.value.replace(/[^\d]/g,"");
										}).change(function(){
											var $min = $(this).parent().find("input.minamt");
											var $max = $(this).parent().find("input.maxamt");
											var minamt = $min.val();
											var maxamt = $max.val();
											if(minamt.length>0&&maxamt.length>0&&Number(minamt)>=Number(maxamt)){
												alerttext("最高库存数应大于最低库存数",1500);
											}
										});
										$maxipt.keyup(function(){
											this.value = this.value.replace(/[^\d]/g,"");
										}).change(function(){
											var $min = $(this).parent().find("input.minamt");
											var $max = $(this).parent().find("input.maxamt");
											var minamt = $min.val();
											var maxamt = $max.val();
											if(minamt.length>0&&maxamt.length>0&&Number(minamt)>=Number(maxamt)){
												alerttext("最高库存数应大于最低库存数",1500);
											}
										});
										$(td).append($minipt).append("-").append($maxipt);
									}
								}
								tr.appendChild(td);
							}
							$("#utable_warn").append(tr);
						}
						$('#ud').dialog({
							width: sl * 141 + 200
						});
						$("#ud").dialog("setTitle",data.WARENO+"，"+data.WARENAME+"设置商品库存预警").dialog("open");
						$("#utable_warn input").first().focus();
					}
				} catch(e) {
					// TODO: handle exception
					console.error(e);
					top.wrtiejsErrorLog(e);
				}
			}
		});
	} else alerttext("请选择商品");
}
//保存设置
function savewarewarn(){
	var wareid = Number($("#uwareid").val());
	var houselist = [];
	var selobject = $('#selwarnhouse').data("selobject");
	if(selobject){
		var rows = selobject.value.split("^");
		for(var i in rows){
			var value = rows[i];
			if(value&&value.length>0)
				houselist.push({houseid: value});
		}
	}
	if(houselist.length==0){
		alerttext("请选择预警店铺！");
		return;
	}
	var amountlist=[];
	var isright = true;
	$("table#utable_warn td.warnamounttd").each(function(){
		var $this = $(this);
		var ware = $this.data("ware");
		if(ware){
			var colorid = ware.colorid;
			var sizeid = ware.sizeid;
			var minamtstr = $this.find("input.minamt").val();
			var maxamtstr = $this.find("input.maxamt").val();
			var minamt = Number(minamtstr);
			var maxamt = Number(maxamtstr);
			if(maxamt>=minamt&&minamt>=0&&!isNaN(minamt)&&!isNaN(maxamt)){
				amountlist.push({
					colorid: colorid,
					sizeid: sizeid,
					minamt: minamtstr,
					maxamt: maxamtstr
				});
			}
			if(maxamt<minamt&&maxamt>0&&(isNaN(minamt)||isNaN(maxamt))) isright =false;
		}
	});
	if(isright==false){
		alerttext("最高库存数应大于最低库存数");
		return;
	}
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "savewarewarn",
			wareid: wareid,
			houselist: JSON.stringify(houselist),
			amountlist: JSON.stringify(amountlist)
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#utable_warn").html("");
					$("#uwareid").val("");
					$("#ud").dialog("close");
					$("#pp").refreshPage();
					$("#uwareno").val("").focus();
				}
			}catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//删除
function delwarewarn(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var wareid = row.WAREID;
		var houseid = row.HOUSEID;
		var colorid = row.COLORID;
		$.messager.confirm(dialog_title, '您确认要删除' + row.HOUSENAME+","+ row.WARENO+","+row.COLORNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwarewarn",
						houseid: houseid,
						wareid: wareid,
						colorid: colorid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
								var total = Number($("#pp_total").html());
								if (!isNaN(total)) $("#pp_total").html(total - 1);
							}
						} catch(e) {
							// TODO: handle exception
							console.error(e);top.wrtiejsErrorLog(e);
						}
					}
				});
			}
		});
	}
}
//双击
function loadwarewarn(index){
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var wareid = row.WAREID;
		var houseid = row.HOUSEID;
		$("#uwareid").val(wareid);
		$("ul#ul_house").html("").append("<li>"+row.HOUSENAME+"</li>");
		$("#selwarnhouse").data("selobject",{name: row.HOUSENAME+",",value: "^"+row.HOUSEID+"^"});
		$("#utable_warn").html("");
		$("input.tkminamt,input.maxamt").val("");
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "loadwarewarn",
				houseid: houseid,
				wareid: wareid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						var warecolorjson = data.colorlist;
						var waresizejson = data.sizelist;
						var amountlist = data.amountlist;
						if (!warecolorjson || warecolorjson.length == 0) {
							alerttext("该商品不存在有效颜色！");
							return;
						}
						if (!waresizejson || waresizejson.length == 0) {
							alerttext("该商品不存在有效尺码！");
							return;
						}
						var cl = warecolorjson.length;
						var sl = waresizejson.length;
						for (var i = 0; i <= cl; i++) {
							//创建tr
							var tr = document.createElement("tr");
							for (var j = 0; j <= sl; j++) {
								//alert(list);
								//创建td
								var td = document.createElement("td");
								if (i == 0) {
									tr.className = "table-header",
									td.style.border = "none";
									if (j == 0) {
									 	td.style.width='80px';
										td.innerHTML = '颜色';
									} else {
										td.style.width = '140px';
										td.innerHTML = waresizejson[j - 1].SIZENAME;
									}
								} else {
									if (j == 0) {
										td.innerHTML = warecolorjson[i - 1].COLORNAME;
									} else {
										td.className = "warnamounttd";
										var sizeid = Number(waresizejson[j - 1].SIZEID);
										var colorid = Number(warecolorjson[i - 1].COLORID);
										$(td).data("ware", {
											colorid: colorid,
											sizeid: sizeid
										});
										var $minipt = $('<input type="text" class="warnamt minamt" placeholder="<输入>">');
										var $maxipt = $('<input type="text" class="warnamt maxamt" placeholder="<输入>">');
										$minipt.keyup(function(){
											this.value = this.value.replace(/[^\d]/g,"");
										}).change(function(){
											var $min = $(this).parent().find("input.minamt");
											var $max = $(this).parent().find("input.maxamt");
											var minamt = $min.val();
											var maxamt = $max.val();
											if(minamt.length>0&&maxamt.length>0&&Number(minamt)>=Number(maxamt)){
												alerttext("最高库存数应大于最低库存数",1500);
											}
										});
										$maxipt.keyup(function(){
											this.value = this.value.replace(/[^\d]/g,"");
										}).change(function(){
											var $min = $(this).parent().find("input.minamt");
											var $max = $(this).parent().find("input.maxamt");
											var minamt = $min.val();
											var maxamt = $max.val();
											if(minamt.length>0&&maxamt.length>0&&Number(minamt)>=Number(maxamt)){
												alerttext("最高库存数应大于最低库存数",1500);
											}
										});
										//加载数量
										for(var k in amountlist){
											var ware = amountlist[k];
											var _colorid = Number(ware.COLORID);
											var _sizeid = Number(ware.SIZEID);
											if(!isNaN(_colorid)&&!isNaN(_sizeid)&&colorid==_colorid&&sizeid==_sizeid){
												var minamt = ware.MINAMT;
												var maxamt = ware.MAXAMT;
												$minipt.val(minamt);
												$maxipt.val(maxamt);
												break;
											}
										}
										$(td).append($minipt).append("-").append($maxipt);
									}
								}
								tr.appendChild(td);
							}
							$("#utable_warn").append(tr);
						}
						
						$('#ud').dialog({
							width: sl * 141 + 200
						});
						$("#ud").dialog("setTitle",row.WARENO+"，"+row.WARENAME+"设置商品库存预警").dialog("open");
						$("#utable_warn input").first().focus();
					}
				} catch(e) {
					// TODO: handle exception
					console.error(e);
					top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
</script>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
<!-- 类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
</body>
</html>