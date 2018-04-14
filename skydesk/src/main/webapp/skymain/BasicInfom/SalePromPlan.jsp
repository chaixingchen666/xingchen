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
<title>促销方案</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
ul#addul {
    box-sizing:border-box;
	-moz-box-sizing:border-box; /* Firefox */
	-webkit-box-sizing:border-box; /* Safari */
    position: absolute;
	z-index: 1;
    padding: 5px 0;
    left: 14px;
    background: #fff;
    border: 1px solid #ddd;
    display: none;
}
div#addbtn:hover ul.menuul{
	display: block;
}
.menuul li {
    padding: 2px 15px;
    font-size: 14px;
    color: #323232;
    text-align: left;
}
.menuul li:hover {
    background: #ff9700;
    color: #fff
}
.bottom-border{
border: none;border-bottom:1px solid #d7d7d7;text-align: center;width: 50px;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui/extends/datagrid-cellediting.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<div id="addbtn" class="btn1 fl hide">
	添加▼
	<ul id="addul" class="menuul">  
	    <li data-ntid="0">买满X元 打Y折 送Z赠品</li>   
	    <li data-ntid="1">买满X元 让利Y元 送Z赠品</li>   
	    <li data-ntid="2">买满X件 打Y折 送Z赠品</li>   
	    <li data-ntid="3">买满X件 让利Y元 送Z赠品</li>   
	</ul>  
	</div>   
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、活动名称、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" data-enter="getnotedata();">
	<input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="scustid" id="scustid" value=""> 
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input type="text" name="smindate" id="smindate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox">
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text">
<input class="edithelpinput cust_help" id="scustname" placeholder="<输入或选择>"  data-value="#scustid" type="text"><span class="s-btn" onclick="selectcust('search')"></span>
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput" id="swareno" type="text" data-value="#swareid" placeholder="<输入>" ><span class="s-btn" onclick="selectware('search')"></span>
</div></div>
<div class="s-box"><div class="title">自编号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="shandno" id="shandno" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">摘要</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sreamrk" id="sremark" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">制单人</div>
<div class="text"><input type="text" class="hig25 wid160" id="soperant" name="soperant" placeholder="<输入>" />
</div>
</div>
<div class="s-box">
<div class="title">单据状态</div>
	<div class="text">
	<label>
	<input type="radio" name="sstatetag" id="st3" value="2" checked="checked">所有
	</label>
	<label>
	<input type="radio" name="sstatetag" id="st1" value="0">未提交
	</label>
	<label>
	<input type="radio" name="sstatetag" id="st2" value="1">已提交
	</label>
	</div>
</div>
<div class="s-box">
<div class="title">审核状态</div>
	<div class="text">
	<label>
	<input type="radio" name="schecktag" id="sc3" value="2" checked="checked">所有
	</label>
	<label>
	<input type="radio" name="schecktag" id="sc1" value="0">未提交
	</label>
	<label>
	<input type="radio" name="schecktag" id="sc2" value="1">已提交
	</label>
	</div>
</div>
</div>
</div>
</div>
</div>
<table id="gg"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 促销方案 -->
<div id="ud" class="easyui-dialog" title="买满X元 打Y折 送Z赠品" style="width:100%;height:100%" closed="true">
<input type="hidden" id="untid" value="0">
<input type="hidden" id="ustatetag" value="0">
<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" value="保存" onclick="updatenote(0)"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" value="提交" onclick="updatenote(1)"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" value="删除" onclick="delnote()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" value="导入商品" onclick="openimportd()"></span>
	</span>
	<span id="updatedbar">
	<span><input class="btn4" type="button" value="撤单" onclick="cancelnote()"></span>
	</span>
</div>
<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25" type="text" id="unoteno" readonly>
</div>
</div>
<div class="s-box"><div class="title">日期</div>
	<div class="text">
	<input id="unotedate" type="text" style="width: 160px;height:25px" readonly>
</div>
</div>
<div class="s-box"><div class="title">活动名称</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="hig25 wid160" id="uactionname" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="hig25 wid160" id="uhandno" placeholder="<输入>" >
</div>
</div>
<div class="clearbox"><div class="title">摘要</div>
	<div class="text"><input maxlength="100" type="text" autocomplete="off" id="uremark" class="hig25 wid_remark" style="width:621px;" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">开始日期</div>
	<div class="text">
	    <input id="ubegindate" type="text" style="width: 162px;height:29px" class="easyui-datebox" data-options="readonly:true,hasDownArrow:false">
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
	<div class="text">
	    <input id="uenddate" type="text" style="width: 162px;height:29px" class="easyui-datebox" data-options="readonly:true,hasDownArrow:false">
	</div>
</div>
<div class="s-box" style="width:460px;"><div class="title">促销日</div>
	<div class="text" style="width:347px" id="usaledaylist">
	<label><input type="checkbox" id="uweek1" checked>周一</label>
	<label><input type="checkbox" id="uweek2" checked>周二</label>
	<label><input type="checkbox" id="uweek3" checked>周三</label>
	<label><input type="checkbox" id="uweek4" checked>周四</label>
	<label><input type="checkbox" id="uweek5" checked>周五</label>
	<label><input type="checkbox" id="uweek6" checked>周六</label>
	<label><input type="checkbox" id="uweek7" checked>周日</label>
	</div>
</div>
<div class="s-box"><div class="title">促销时段</div>
	<div class="text">
	<input id="ubegintime" class="easyui-timespinner" style="width:73px;height:25px"
    data-options="showSeconds:false" value="00:00" />
    -
	<input id="uendtime" class="easyui-timespinner" style="width:73px;height:25px"
    data-options="showSeconds:false" value="00:00" />
	</div>
</div>
<div class="clearbox">
	<label style="margin-left: 5px;"><input type="checkbox" id="ukzxx1" class="kzxx">允许会员折上折</label>
	<label><input type="checkbox" id="ukzxx5" class="kzxx">适用特价商品</label>
	<label><input type="checkbox" id="ukzxx2">计算积分</label>
	<span style="font-weight: 600;margin-left: 10px;">单笔金额储值卡支付控制：</span>
	<span>
	<label><input type="radio" name="ukzxx4" id="ukzxx40" class="kzxx" checked>支付比例</label>
	<input class="bottom-border" type="text" id="uvrate" placeholder="<输入>" style="width:50px;" value="">%
	</span>
	<span>
	<label><input name="ukzxx4" type="radio" id="ukzxx4" class="kzxx">支付金额</label>
	<input type="text" id="uvcurr" class="bottom-border" placeholder="<输入>" value="">元
	</span>
</div>
<div class="clearbox">
	<div class="title">促销方案</div>
	<div class="text" style="width: auto;">
	<span id="ntname0">买满
	<input type="text" id="utotalcurr" class="bottom-border" placeholder="<输入>">
	元，
	</span>
	<span id="ntname1">买满
	<input type="text" id="utotalamt" class="bottom-border" placeholder="<输入>">
	件，
	</span>
	<span id="ntname2">打
	<input type="text" id="uzdiscount" class="bottom-border" placeholder="<0-1>">
	折，
	</span>
	<span id="ntname3">让利
	<input type="text" id="ucutcurr" class="bottom-border" placeholder="<输入>">
	元，
	</span>
	<span>送Z赠品</span>
	</div>
</div>
</div>
<div>
<div class="fl" style="width:180px;">
<div id="salepromhousetoolbar" style="height: 40px;line-height: 40px;">
<input type="button" class="btn1" value="刷新" onclick="listsalepromhouse(1)">
</div>
<table id="salepromhouset"></table>
</div>
<div class="fl" style="width:500px;padding:0 5px;">
<div id="salepromwaretoolbar0" style="height: 40px;line-height: 40px;">
<label>
<input type="checkbox" id="ukzxx3"  class="kzxx">仅对促销商品有效
</label>
<span class="hide" id="salepromwarebar">
<input type="button" class="btn1" value="增加" onclick="selectware('addsaleprom0')">
<!-- <input type="button" class="btn1" value="载入" onclick="opensaleproweareimport(0)"> -->
<input type="button" class="btn1" value="删除" onclick="delsalepromware(0)">
<input type="button" class="btn1" value="刷新" onclick="listsalepromware(1,0)">
</span>
</div>
<table id="salepromwaret0"></table>
</div>
<div class="fl" style="width:330px;">
<div id="salepromwaretoolbar1" style="height: 40px;line-height: 40px;">
<input type="button" class="btn1" value="增加" onclick="selectware('addsaleprom1')">
<input type="button" class="btn1" value="删除" onclick="delsalepromware(1)">
<input type="button" class="btn1" value="刷新" onclick="listsalepromware(1,1)">
</div>
<table id="salepromwaret1"></table>
</div>
</div>
</div>
<script type="text/javascript">
var pgid = "pg1023";
var levelid = getValuebyName("GLEVELID");
setqxpublic();
$(function(){
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getnotelist(p);
		}
	});
	$("#gg").datagrid({
		idField: 'NOTENO',
		height: $('body').height() - 45,
		fitColumns: true,
		striped: true,
		//隔行变色
		nowrap: false,
		singleSelect: true,
		showFooter: true,
		sortName: "NOTEDATE",
		sortOrder: "desc",
		onDblClickRow: function(index, row) {
			$("#unoteno").val(row.NOTENO);
			updatenoted();
		},
		onSelect: function(index, row) {
			if(index>=0) $("#unoteno").val(row.NOTENO);
		},
		columns: [[
		{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(isNaN(Number(value))&&value!=undefined&&value.length>0)
					return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			width: 125,
			fixed: true,
			align: 'center',
			halign: 'center',
			styler: function(value, row, index) {
				if (value == "合计") {
					return 'color:#ff7900;font-weight:700';
				}
				if (row.STATETAG == '1') {
					return 'color:#00959a;font-weight:900';
				} else {
					return 'color:#ff7900;font-weight:700';
				}
			}
		},
		{
			field: 'NOTEDATE',
			title: '单据日期',
			width: 112,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: datetimefm
		},
		{
			field: 'NTIDNAME',
			title: '方案类别',
			width: 200,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'HANDNO',
			title: '自编号',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ACTIONNAME',
			title: '活动名称',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'REMARK',
			title: '摘要',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'BEGINDATE',
			title: '开始日期',
			width: 112,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: datefm
		},
		{
			field: 'ENDDATE',
			title: '结束日期',
			width: 112,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: datefm
		},
		{
			field: 'OPERANT',
			title: '制单人',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CHECKMAN',
			title: '审核人',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var stg = Number(row.STATETAG);
				var checkman = row.CHECKMAN;
				var epname = getValuebyName("EPNAME");
				var operant = row.OPERANT;
				if (stg==0&&epname==operant) return '<input type="button" value="删除" class="m-btn" onclick="delnote()">';
				else if (stg>0&&checkman==""&&(levelid==0||levelid==4||levelid==5||levelid==8)) return '<input type="button" value="审核" class="m-btn" onclick="checksalepromplan(1)">';
				else if (stg>0&&checkman==epname&&(levelid==0||levelid==4||levelid==5||levelid==8)) return '<input type="button" value="取消审核" class="m-btn" onclick="checksalepromplan(0)">';
				return "";
			}
		}
		]],
		pageSize: 20,
		toolbar: '#toolbars'
	});
	getnotedata();
	$("#addul li").click(function(){
		var ntid = $(this).data("ntid");
		$("#untid").val(ntid);
		addnoted(ntid);
	});
	$("#ukzxx3").change(function(){
		if($(this).prop("checked")){
			var zdiscount = Number($("#uzdiscount").val());
			var ntid = Number($("#untid").val());
			if(zdiscount==0&&(ntid==0||ntid==2)){
				alerttext("请填写有效折扣！");
				$(this).prop("checked",false);
				$("#uzdiscount").focus().select();
			}else {
				$("#salepromwarebar").show();
			}
		}
		else
			$("#salepromwarebar").hide();
		
	});
	setsalepromt();
	if(levelid==0||levelid==4||levelid==5||levelid==8){
		$("#addbtn").show();
	}
	uploader_xls_options.uploadComplete = function(file){
		listsalepromware(1,1);
		listsalepromware(1,0);
	}
	uploader_xls_options.startUpload = function(){
		if (uploader_xls.getFiles().length > 0) {
			uploader_xls.option('formData', {
				noteno: $('#unoteno').val()
			});
			uploader_xls.upload();
		} else {
			alerttext("文件列表为空！请添加需要上传的文件！");
		}
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=loadsalepromwarexls&server=buy",
		xlsmobanname: "salepromware",
		channel: "salepromware"
	});
});
var searchb = false;
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		$('#searchbtn').hide();
		searchb = false;
	} else {
		$('#highsearch').val('高级搜索▲');
		$('#searchbtn').show();
		searchb = true;
	}
	$('.searchbar').slideToggle('fast');
}
//清空搜索条件
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shandno,#scustname,#scustid,#swareno,#swareid,#sremark,#soperant').val('');
	$('#st3,#sc3').prop('checked', true);
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName;
	var order = options.sortOrder;
	currentdata["erpser"] = "listsalepromplan";
	currentdata["sort"] = sort;
	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.accid,a.noteno,a.notedate,a.ntid,a.handno,a.remark,a.actionname,a.operant,a.checkman,a.statetag";
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
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
					if (data.total > 0) $('#gg').datagrid('scrollTo', 0);
					$('#pp_total').html(data.total);
					$('#qsnotevalue').focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getnotedata() {
	var value = $('#qsnotevalue').val();
	currentdata = {
		findbox: value,
		statetag: 2
	};
	getnotelist(1);
}
//高级搜索
function searchnote() {
	var custid = Number($('#scustid').val());
	var handno = $('#shandno').val();
	var remark = $('#sremark').val();
	var operant = $('#soperant').val();
	var wareid = Number($('#swareid').val());
	var statetag;
	if ($('#st1').prop('checked')) {
		statetag = "0";
	} else if ($('#st2').prop('checked')) {
		statetag = "1";
	} else {
		statetag = "2";
	}
	var checktag;
	if ($('#sc1').prop('checked')) {
		checktag = "0";
	} else if ($('#sc2').prop('checked')) {
		checktag = "1";
	} else {
		checktag = "2";
	}
	var fhtag = $('input[name=sfhtag]:checked').val();
	currentdata = {
		handno: handno,
		custid: custid,
		wareid: wareid,
		remark: remark,
		operant: operant,
		fhtag: fhtag,
		checktag: checktag,
		statetag: statetag
	};
	getnotelist(1);
}
//增加促销方案计划
// ntid=0 买满X元 打Y折 送Z赠品
// ntid=1 买满X元 让利Y元 送Z赠品
// ntid=2 买满X件 打Y折 送Z赠品
// ntid=3 买满X件 让利Y元 送Z赠品
function addnoted(ntid){
	var title = "";
	switch (ntid) {
	case 0:
		title = "买满X元 打Y折 送Z赠品";
		$("#ntname0,#ntname2").show();
		$("#ntname1,#ntname3").hide();
		$("#salepromwaret0").datagrid("showColumn","DISCOUNT").datagrid("showColumn","PRICE");
		break;
	case 1:
		title = "买满X元 让利Y元 送Z赠品";
		$("#ntname0,#ntname3").show();
		$("#ntname1,#ntname2").hide();
		$("#salepromwaret0").datagrid("hideColumn","DISCOUNT").datagrid("hideColumn","PRICE");
		break;
	case 2:
		title = "买满X件 打Y折 送Z赠品";
		$("#ntname1,#ntname2").show();
		$("#ntname0,#ntname3").hide();
		$("#salepromwaret0").datagrid("showColumn","DISCOUNT").datagrid("showColumn","PRICE");
		break;
	case 3:
		title = "买满X件 让利Y元 送Z赠品";
		$("#ntname1,#ntname3").show();
		$("#ntname0,#ntname2").hide();
		$("#salepromwaret0").datagrid("hideColumn","DISCOUNT").datagrid("hideColumn","PRICE");
		break;
	default:
		break;
	}
	$("#ubegindate,#uenddate").datebox({
		readonly: false,
		hasDownArrow: true
	});
	$("#ubegintime,#uendtime").timespinner({
		readonly: false
	});
	$("#uhandno,#uactionname,#uremark,#uvrate,#uvcurr,#ucutcurr,#utotalcurr,#utotalamt,#uzdiscount").val("").prop("readonly",false);
	$("#ucutcurr,#utotalcurr,#utotalamt,#uzdiscount").val(0);
	$("#ud :checkbox,#ud :radio").prop("disabled",false);
	$("#ud :checkbox,#ud :radio").prop("checked",false);
	$("#ud #usaledaylist :checkbox").prop("checked",true);
	$("#updatingbar,#salepromwaretoolbar0 input.btn1,#ud .datagrid-toolbar:not(#salepromwaretoolbar0)").show();
	$("#updatedbar").hide();
	$("#ustatetag").val(0);
	$("#salepromhouset,#salepromwaret0,#salepromwaret1").datagrid("loadData",{total:0,rows:[]});
	listsalepromhouse(1);//加载店铺
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addsalepromplan",
			ntid: ntid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#unoteno').val(data.NOTENO);
					$('#unotedate').val(data.NOTEDATE);
					$('#ubegindate').datebox("setValue",data.BEGINDATE);
					$('#uenddate').datebox("setValue",data.ENDDATE);
					$('#ubegintime').timespinner("setValue",data.BEGINTIME);
					$('#uendtime').timespinner("setValue",data.ENDTIME);
					$('#qsnotevalue').val("");
					$("#smaxdate").datebox("setValue", top.getservertime());
					$("#pp").refreshPage();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
	$("#ud").dialog("setTitle",title).dialog("open");
}
function updatenoted(){
	var editrow = $("#gg").datagrid("getSelected");
	if(editrow){
		alertmsg(2);
		var noteno = editrow.NOTENO;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fybuyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getsalepromplan",
				noteno: noteno,
				fieldlist: "a.id,a.accid,a.noteno,a.notedate,a.ntid,a.handno,a.remark,a.actionname,a.operant,a.kzxx,a.saledaylist,a.totalamt,a.totalcurr,a.cutcurr,a.vcurr,a.vrate,a.zdiscount,a.statetag"
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						if(data.total>0){
							var row = data.rows[0];
							$('#unoteno').val(row.NOTENO);
							$('#unotedate').val(row.NOTEDATE);
							var stg = Number(row.STATETAG);
							$("#ustatetag").val(stg);
							if(stg==0&&(levelid==0||levelid==4||levelid==5||levelid==8)){
								$("#ubegindate,#uenddate").datebox({
									readonly: false,
									hasDownArrow: true
								});
								$("#ubegintime,#uendtime").timespinner({
									readonly: false
								});
								$("#uhandno,#uactionname,#uremark,#uvrate,#uvcurr,#ucutcurr,#utotalcurr,#utotalamt,#uzdiscount").prop("readonly",false);
								$("#ud :checkbox,#ud :radio").prop("disabled",false);
								$("#updatingbar,#salepromwaretoolbar0 input.btn1,#ud .datagrid-toolbar:not(#salepromwaretoolbar0)").show();
								$("#updatedbar").hide();
							}else{
								$("#ubegindate,#uenddate").datebox({
									readonly: true,
									hasDownArrow: false
								});
								$("#ubegintime,#uendtime").timespinner({
									readonly: true
								});
								$("#uhandno,#uactionname,#uremark,#uvrate,#uvcurr,#ucutcurr,#utotalcurr,#utotalamt,#uzdiscount").prop("readonly",true);
								$("#ud :checkbox,#ud :radio").prop("disabled",true);
								$("#updatingbar,#salepromwaretoolbar0 input.btn1,#ud .datagrid-toolbar:not(#salepromwaretoolbar0)").hide();
								$("#updatedbar").show();
							}
							$('#ubegindate').datebox("setValue",row.BEGINDATE);
							$('#uenddate').datebox("setValue",row.ENDDATE);
							$('#ubegintime').timespinner("setValue",row.BEGINTIME);
							$('#uendtime').timespinner("setValue",row.ENDTIME);
							var title = "";
							var ntid = Number(row.NTID);
							$("#untid").val(ntid);
							switch (ntid) {
							case 0:
								title = "买满X元 打Y折 送Z赠品";
								$("#ntname0,#ntname2").show();
								$("#ntname1,#ntname3").hide();
								$("#salepromwaret0").datagrid("showColumn","DISCOUNT").datagrid("showColumn","PRICE");
								break;
							case 1:
								title = "买满X元 让利Y元 送Z赠品";
								$("#ntname0,#ntname3").show();
								$("#ntname1,#ntname2").hide();
								$("#salepromwaret0").datagrid("hideColumn","DISCOUNT").datagrid("hideColumn","PRICE");
								break;
							case 2:
								title = "买满X件 打Y折 送Z赠品";
								$("#ntname1,#ntname2").show();
								$("#ntname0,#ntname3").hide();
								$("#salepromwaret0").datagrid("showColumn","DISCOUNT").datagrid("showColumn","PRICE");
								break;
							case 3:
								title = "买满X件 让利Y元 送Z赠品";
								$("#ntname1,#ntname3").show();
								$("#ntname0,#ntname2").hide();
								$("#salepromwaret0").datagrid("hideColumn","DISCOUNT").datagrid("hideColumn","PRICE");
								break;
							default:
								break;
							}
							var actionname = row.ACTIONNAME;
							var handno = row.HANDNO;
							var remark = row.REMARK;
							var totalcurr = row.TOTALCURR;
							var totalamt = row.TOTALAMT;
							var vrate = Number(row.VRATE);
							var vcurr = Number(row.VCURR);
							var cutcurr = Number(row.CUTCURR);
							var zdiscount  = Number(row.ZDISCOUNT);
							$("#uactionname").val(actionname);
							$("#uhandno").val(handno);
							$("#uremark").val(remark);
							$("#utotalcurr").val(totalcurr);
							$("#utotalamt").val(totalamt);
							$("#uvrate").val(vrate);
							$("#uvcurr").val(vcurr);
							$("#ucutcurr").val(cutcurr);
							$("#uzdiscount").val(zdiscount);
							var saledaylist = row.SALEDAYLIST;
							var kzxx = row.KZXX;
							dayarr = saledaylist.split("");
							$("#usaledaylist :checkbox").prop("checked",false);
							for(var i=0;i<dayarr.length;i++){
								$("#uweek"+(i+1)).prop("checked",dayarr[i]==1);
							}
							kzxxarr = kzxx.split("");
							$("input.kzxx").prop("checked",false);
							for(var i=0;i<kzxxarr.length;i++){
								if(i==3&&kzxxarr[i]==0){
									 $("#ukzxx40").prop("checked",true);
								}else $("#ukzxx"+(i+1)).prop("checked",kzxxarr[i]==1);
							}
							$("#ud").dialog("setTitle",title).dialog("open");
							$("#salepromhouset,#salepromwaret0,#salepromwaret1").datagrid("loadData",{total:0,rows:[]});
							listsalepromhouse(1);//加载店铺
							if(kzxxarr[2]==1){
								listsalepromware(1,0);//加载促销商品
								$("#salepromwarebar").show();
							}else $("#salepromwarebar").hide();
							listsalepromware(1,1);//加载赠品
						}
					}
				} catch(e) {
					// TODO: handle exception
					console.error(e);
					top.wrtiejsErrorLog(e);
				}
			}
		});
	}else alertttext("请选择有效行！");
}
function setsalepromt(){
	$("#salepromhouset").datagrid({
		title: "促销店铺",
		headerCls: "textcenter",
		height: 500,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		toolbar: "#salepromhousetoolbar",
		columns: [[{
			field: 'SELBJ',
			checkbox: true
		},{
			field: 'ROWNUMBER',
			title: '序号',
			width: 30,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value,row,index){
				return index+1;
			}
		},{
			field: 'HOUSENAME',
			title: '店铺',
			width: 100,
			fixed: true,
			halign: "center",
			align: 'center'
		}]],
		onCheck: function(index,row){
			if(housecheck) writesalepromhouse(index,row,1);
		},
		onUncheck:function(index,row){
			if(housecheck) writesalepromhouse(index,row,0);
		},
		onCheckAll: function(rows){
			if(housecheck) writesalepromhouseall(1);
		},
		onUncheckAll:function(rows){
			if(housecheck) writesalepromhouseall(0);
		},
		onLoadSuccess: function(data) {
			if($("#ustatetag").val()==1)
				$("#ud .datagrid-cell-check :checkbox").prop("disabled",true);
		}
	});
	$("#salepromwaret0").datagrid({
		title: "促销商品",
		headerCls: "textcenter",
		height: 500,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		toolbar: "#salepromwaretoolbar0",
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 30,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value,row,index){
				return index+1;
			}
		},{
			field: 'WARENO',
			title: '货号',
			width: 100,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'WARENAME',
			title: '商品名称',
			width: 100,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'RETAILSALE',
			title: '零售价',
			width: 80,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm
		},{
			field: 'DISCOUNT',
			title: '折扣',
			width: 80,
			fixed: true,
			halign: "center",
			align: 'center',
			formatter: function(value,row,index){
				if(value>0) return Number(value).toFixed(2);
				return "-";
			},
			editor: {
				type:'text'
			}
		},{
			field: 'PRICE',
			title: '折后价',
			width: 80,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm,
			editor: {
				type:'text'
			}
		}]],	    
		onClickCell: function(index,field,value){
			editsalepromwareindex = index;
		},
		onBeforeEdit: function(index,row){
			var stg = Number($("#ustatetag").val());
			if(stg>0) return false;
		},
		onEndEdit: function(index,row,changes) {
			var retailsale = Number(row.RETAILSALE);
			var discount = Number(row.DISCOUNT);
			var price = Number(row.PRICE);
			var changed = $('#salepromwaret0').datagrid('getChanges').length > 0 ? true: false;
			if (changed&&!isNaN(discount)&&!isNaN(price)&&discount>0) {
				if(changes.DISCOUNT){
					price = retailsale*discount;
					row.PRICE = price.toFixed(2);
				}else{
					discount = price==0?1:price/retailsale;
					row.DISCOUNT = discount.toFixed(2);
				}
				updatesalepromware(row);
			}
		}
	}).datagrid('enableCellEditing');
	$("#salepromwaret1").datagrid({
		title: "赠品商品",
		headerCls: "textcenter",
		height: 500,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		toolbar: "#salepromwaretoolbar1",
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 30,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value,row,index){
				return index+1;
			}
		},{
			field: 'WARENO',
			title: '货号',
			width: 100,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'WARENAME',
			title: '商品名称',
			width: 100,
			fixed: true,
			halign: "center",
			align: 'center'
		},{
			field: 'RETAILSALE',
			title: '零售价',
			width: 80,
			fixed: true,
			halign: "center",
			align: 'right',
			formatter: currfm
		}]],	    
		onClickCell: function(index,field,value){
			editsalepromwareindex = index;
		}
	});
}
function updatenote(statetag){
	if(statetag==0) updatesalepromplan(0);
	else{
		$.messager.confirm(dialog_title,"确认提交促销计划！",
		function(r) {
			if (r) {
				updatesalepromplan(1);
			}
		});
	}
}
//修改单据
function updatesalepromplan(statetag){
	var noteno = $("#unoteno").val();
	var actionname = $("#uactionname").val();
	if(actionname.length==0){
		alerttext("请输入活动名称");
		$("#uactionname").focus();
		return;
	}
	var handno = $("#uhandno").val();
	var remark = $("#uremark").val();
	var vrate = Number($("#uvrate").val());
	var vcurr = Number($("#uvcurr").val());
	var cutcurr = Number($("#ucutcurr").val());
	var totalamt = Number($("#utotalamt").val());
	var totalcurr = Number($("#utotalcurr").val());
	var zdiscount = Number($("#uzdiscount").val());
	var begindate = $("#ubegindate").datebox("getValue");
	var enddate = $("#uenddate").datebox("getValue");
	var begintime = $("#ubegintime").timespinner("getValue");
	var endtime = $("#uendtime").timespinner("getValue");
	var saledaylist = ""; //1-7周一到周日
	$("#usaledaylist :checkbox").each(function(){
		saledaylist += $(this).prop("checked")?"1":"0";
	});
	var kzxx = ""; //1-4 1：启用会员折上折，2：计算积分，3：全场或促销(0=全场商品1=促销商品)，4：储值比例或金额(0=比例，1=金额)
	kzxx += $("#ukzxx1").prop("checked")?"1":"0";
	kzxx += $("#ukzxx2").prop("checked")?"1":"0";
	kzxx += $("#ukzxx3").prop("checked")?"1":"0";
	kzxx += $("#ukzxx4").prop("checked")?"1":"0";
	kzxx += $("#ukzxx5").prop("checked")?"1":"0";
	kzxx += "00000";
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatesalepromplan",
			noteno: noteno,
			actionname: actionname,
			handno: handno,
			remark: remark,
			begindate: begindate,
			enddate: enddate,
			saledaylist: saledaylist,
			begintime: begintime,
			endtime: endtime,
			kzxx: kzxx,
			vrate: vrate,
			vcurr: vcurr,
			cutcurr: cutcurr,
			zdiscount: zdiscount,
			totalamt: totalamt,
			totalcurr: totalcurr,
			statetag: statetag
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#pp").refreshPage();
					$("#ud").dialog("close");
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//删除单据
function delnote(){
	$.messager.confirm(dialog_title,"删除促销计划后，数据不可恢复，确认删除？",
		function(r) {
		if (r) {
			var noteno = $("#unoteno").val();
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fybuyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delsalepromplan",
					noteno: noteno
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							$("#ud").dialog("close");
							$("#pp").refreshPage();
						}
					} catch(e) {
						// TODO: handle exception
						console.error(e);
						top.wrtiejsErrorLog(e);
					}
				}
			});	
		}
	});
}
//审核返审
function checksalepromplan(checkid){
	var msg = checkid==1?"确认审核？":"取消审核？";
	$.messager.confirm(dialog_title,msg,
			function(r) {
			if (r) {
				var noteno = $("#unoteno").val();
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "checksalepromplan",
						noteno: noteno,
						checkid: checkid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								var $dg = $("#gg");
								var index  = $dg.datagrid("getRowIndex",noteno);
								$dg.datagrid("updateRow",{
									index: index,
									row: {
										CHECKMAN: (checkid==0?"":getValuebyName("EPNAME"))
									}
								}).datagrid("refreshRow",index);
							}
						} catch(e) {
							// TODO: handle exception
							console.error(e);
							top.wrtiejsErrorLog(e);
						}
					}
				});	
			}
		});
}
//显示促销计划单店铺
function listsalepromhouse(page){
	alertmsg(6);
	noteno = $("#unoteno").val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listsalepromhouse",
			noteno: noteno,
			rows: 50,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var $dg = $("#salepromhouset");
					var rows = data.rows;
					for(var i in rows){
						var row = rows[i];
						row.checked = row.SELBJ==1;
					}
					if (page == 1) {
						$dg.datagrid('loadData', data);
						if ($dg.datagrid('getRows').length > 0) {
							$dg.datagrid('selectRow', 0);
						}
					} else {
						for (var i in rows) {
							$dg.datagrid('appendRow', rows[i]);
						}
					}
					if (data.total > 50*page){
						listsalepromhouse(page+1);
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
var housecheck = true;
function writesalepromhouse(index,row,checkid){
	alertmsg(6);
	noteno = $("#unoteno").val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writesalepromhouse",
			noteno: noteno,
			houseid: row.HOUSEID,
			value: checkid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				var $dg = $("#salepromhouset");
				if (valideData(data)) {
					$dg.datagrid("updateRow",{
						index: index,
						row: {
							SELBJ: checkid,
							checked: (checkid==1?true:false)
						}
					});
				}
				housecheck = false;
				$dg.datagrid("refreshRow",index);
				housecheck = true;
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//加载促销商品或赠品
function listsalepromware(page,iszp){
	alertmsg(2);
	noteno = $("#unoteno").val();
	var $dg = $("#salepromwaret"+iszp);
	if(page==1){
		$dg.datagrid('loadData', {total:0,rows:[]});
	}
	editsalepromwareindex = -1
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp", 
		async: false,
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listsalepromware",
			noteno: noteno,
			iszp: iszp,
			rows: 50,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					if (page == 1) {
						$dg.datagrid('loadData', data);
						if(data.total>0) $("#ukzxx3").prop("checked",true);
					} else {
						for (var i in rows) {
							$dg.datagrid('appendRow', rows[i]);
						}
					}
					if (data.total > 50*page){
						listsalepromware(page+1,iszp);
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//全选。取消全选
function writesalepromhouseall(checkid){
	alertmsg(2);
	noteno = $("#unoteno").val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp", 
		async: false,
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeallsalepromhouse",
			noteno: noteno,
			value: checkid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					listsalepromhouse(1);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
function insertRow(iszp){
	$("#salepromwaret"+iszp).datagrid("insertRow",{index:0,row:{ROWNUMBER:1}});
}
//增加明细
function addsalepromware(row,iszp){
	alertmsg(2);
	noteno = $("#unoteno").val();
	var ntid = $("#untid").val();
	var discount = ntid==0||ntid==2?($("#uzdiscount").val()):1;
	var wareid =row.WAREID;
	if(isNaN(wareid)||Number(wareid)==0){
		alerttext("选择有效货号");
		return;
	}
	
	var price = Number(row.RETAILSALE*discount).toFixed(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp", 
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addsalepromware",
			noteno: noteno,
			iszp: iszp,
			wareid: wareid,
			discount: discount,
			price: price
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					listsalepromware(1,iszp);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//修改商品
function updatesalepromware(row){
	alertmsg(2);
	var discount = Number(row.DISCOUNT);
	var price = Number(row.PRICE);
	var wareid = row.WAREID;
	var noteno = $("#unoteno").val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp", 
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatesalepromware",
			iszp: 0,
			noteno: noteno,
			wareid: wareid,
			discount: discount,
			price: price
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$("#salepromwaret0").datagrid('acceptChanges').datagrid('refresh');
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
var editsalepromwareindex = -1;
function delsalepromware(iszp){
	var row = $("#salepromwaret"+iszp).datagrid("getRows")[editsalepromwareindex];
	if(!row){
		alerttext("请选择行！");
		return;
	}
	var noteno = $("#unoteno").val();
	var wareid = row.WAREID;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp", 
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "delsalepromware",
			noteno: noteno,
			iszp: iszp,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					listsalepromware(1,iszp);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//撤单
function cancelnote(){
	$.messager.confirm(dialog_title, '您确定要撤单吗？',
	function(r) {
		if (r) {
			var noteno = $('#unoteno').val();
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fybuyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "cancelsalepromplan",
					noteno: noteno
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						var $dg = $("#gg");
						var index = $dg.datagrid("getRowIndex",noteno);
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								STATETAG: 0
							}
						}).datagrid('refresh');
						$('#ud').dialog('close');
					}
				}
			});
		}
	});
}
</script>
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>