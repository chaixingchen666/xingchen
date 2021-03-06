<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>客户缴款</title>
</head>
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
<script type="text/javascript" src='/skydesk/js/LodopFuncs.js?ver=<%=tools.DataBase.VERSION%>'></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<input type="hidden" id="byid"  name="byid">
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addnoted()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索结算方式、单据号、客户<回车搜索>" data-end="yes" data-enter="getnotedata()" class="ipt1" id="qsnotevalue" >
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
	<span id="serbtn" class="fr hide">
	<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchnote();toggle();"> 
	<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
<!-- 			<input type="button" class="btn3" value="打印预览" onclick="demoPrint(true)"> -->
<!-- 			<input type="button" class="btn3" value="前台打印" onclick="demoPrint()"> -->
<!-- 			<input type="button" class="btn3" value="打印端口设置" onclick="selprint()"> -->
<!-- 			<input type="button" class="btn3" id="print" value="后台打印"> -->
</div>
</div>
<!-- 高级搜索 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input class="easyui-datebox" name="smindate" id="smindate"
						style="width: 163px; height: 28px; margin-left: 10px"/>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input class="easyui-datebox" name="smaxdate" id="smaxdate"
					style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">单据号</div>
<div class="text"><input type="text" class="hig25" id="snoteno" name="snoteno" placeholder="<输入>" />
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input class="edithelpinput wid145 hig25 cust_help" maxlength="20" data-value="#scustid" placeholder="<选择或输入>"
					 type="text" id="scustname" name="scustname">
					 <span onclick="selectcust('search')"></span>
						<input type="hidden" id="scustid" name="scustid">
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selecthouse('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">结算方式</div>
<div class="text"><input type="text" class="edithelpinput pw_help" data-value="#spayid" maxlength="20" placeholder="<输入或选择>" id="spayname"
					name="spayname"/> <span onclick="Paywaydata('search')"></span>
					<input type="hidden" id="spayid" name="spayid"> 
		             <input type="hidden" id="spayno" name="spayno">
</div>
</div>
<div class="s-box"><div class="title">制单人</div>
<div class="text"><input type="text" class="hig25 wid160" id="soperant" name="soperant" placeholder="<输入>" />
</div>
</div>
<div class="s-box"><div class="title">自编号</div>
<div class="text"><input type="text" class="hig25" id="shandno" name="shandno" placeholder="<输入>"/>
</div>
</div>
<div class="s-box"><div class="title">备注</div>
<div class="text"><input type="text" class="hig25 wid160" id="sremark" name="sremark" placeholder="<输入>"/>
</div>
</div>
</div>
</div>
</div>
	<table id="gg" style="overflow: hidden; height:9000000px;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div class="page-total">
			共有<span id="pp_total"></span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
	<!-- 修改 --------------------------------------------------->
	<div id="ud" title="Dialog" style="width: 600px; height: 300px;"  class="easyui-dialog" closed=true>
		<table width="600px" border="0" cellspacing="10">
			<tr>
				<td width="70px" align="right">单据号</td>
				<td width="180px" align="left"><input type="text"
					style="width: 160px; height: 25px" placeholder="<保存时自动生成>"
					id="unoteno" name="unoteno" width="175px" readonly />
					<input type="hidden" id="uid" name="uid">
					<input type="hidden" id="uoperant" name="uoperant">
					<input type="hidden" id="curr1" name="curr1">
					</td>
				<td width="70px" align="right">日期</td>
				<td width="180px" align="left">
				<input id="unotedate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false">
					</td>
			</tr>
			<tr>
				<td align="right">客户</td>
				<td align="left"><input class="edithelpinput cust_help wid145 hig25" data-value="#ucustid" id="ucustname" name="ucustname" maxlength="20" placeholder="<选择或输入>"
					type="text"  data-event="onSelect: getincomebal"><span onclick="selectcust('updatesk')"></span> 
					<input type="hidden" id="ucustid" name="ucustid"></td>
			<td align="right">店铺</td>
	            <td align="right">
	            <input class="edithelpinput wid145 hig25 house_help" type="text" id="uhousename" name="uhousename" maxlength="20" placeholder="<选择或输入>" data-value="#uhouseid"
	            value="" data-event="onSelect: getincomebal"><span onclick="selecthouse('updatesk')"></span><input type="hidden" id="uhouseid" name="uhouseid"> </td>
			</tr>
			<tr>
				<td align="right">累计欠款</td>
				<td align="left"><input type="text" placeholder="<输入>"
					id="ubalcurr" name="ubalcurr" style="width: 160px; height: 25px"  class="onlyMoney" maxlength="8" readonly/></td>
			</tr>
			<tr>
				<td align="right" width="50px">结算方式</td>
				<td align="left">
				<input type="text" class="edithelpinput wid145 hig25 pw_help" data-value="#upayid" placeholder="<选择或输入>" id="upayname"
					name="upayname" /> <span onclick="Paywaydata('update')"></span>
					<input type="hidden" id="upayid" name="upayid"> 
		             <input type="hidden" id="upayno" name="upayno"> </td>
				<td align="right">金额</td>
				<td align="left"><input type="text" placeholder="<输入>"
					id="ucurr" name="ucurr" style="width: 160px; height: 25px"  class="onlyMoney" maxlength="8"/></td>
			</tr>
			<tr>
				<td align="right">自编号</td>
				<td align="left"><input type="text" placeholder="<输入>"
					id="uhandno" name="uhandno" style="width: 160px; height: 25px" />
				</td>
				<td align="right">备注</td>
				<td align="left"><input type="text" placeholder="<输入>"
					id="uremark" name="uremark" style="width: 160px; height: 25px" />
				</td>
			</tr>
		</table>
		<div class="dialog-footer" style="text-align: center;" id="div_updatebtn">
		<input type="button" class="btn1" value="保存" id="btn_update0" name="update" onclick="updatenote(0)">
		<input type="button" class="btn1" value="保存并提交" id="btn_update1" name="update1" onclick="updatenote(1)">
		</div>
	</div>
<script type="text/javascript" charset="UTF-8">
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	var width = document.documentElement.clientWidth; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		width: width,
		height: height - 50
	});
}
var htmlStr = '<table>';
var htmlStrp;
var LODOP;
var tjindex = "";
var ser;
var s = false;
var todaydate = "";
var datetime = "";
var index = 1;
var s = false;
var opser;
var index1 = 1;
var pgid = 'pg1610';

var levelid = getValuebyName("GLEVELID");
var epname = getValuebyName("EPNAME");
setqxpublic();
$(function() {
// 	setaddbackprint("1607");
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			if (currentdata == {}) getnotedata();
			else getnotelist(p);
		}
	});
	getserdatetime();
	//加载数据
	$('#gg').datagrid({
		idField: "NOTENO",
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		onDblClickRow: function(rowIndex, rowData) {
			updatenoted();
		},
		onSelect: function(rowIndex, rowData) {
			if (rowData) {
				$('#unoteno').val(rowData.NOTENO);
				$('#uhandno').val(rowData.HANDNO);
				$('#ucurr').val(rowData.CURR);
				$('#ucustname').val(rowData.CUSTNAME);
				$('#ucustid').val(rowData.CUSTID);
				$('#uhousename').val(rowData.HOUSENAME);
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uremark').val(rowData.REMARK);
				$('#uid').val(rowData.ID);
				$('#upayname').val(rowData.PAYNAME);
				$('#upayid').val(rowData.PAYID);
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uoperant').val(rowData.OPERANT);
				dqindex = rowIndex;
				dqcurr = rowData.CURR;
			}
		},
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != null) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
				} else {
					return value;
				}
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			align: 'center',
			halign: 'center',
			width: 130,
			fixed: true,
			styler: function(value, row, index) {
				if (row.STATETAG == '1') {
					return 'color:#00959a;font-weight:900';
				} else {
					return 'color:#ff7900;font-weight:700';
				}
			}
		},
		{
			field: 'NOTEDATE',
			title: '时间',
			align: 'center',
			halign: 'center',
			width: 130,
			fixed: true
		},
		{
			field: 'CUSTNAME',
			title: '客户名称',
			align: 'center',
			halign: 'center',
			width: 130,
			fixed: true
		},
		{
			field: 'PAYNAME',
			title: '结算方式',
			align: 'center',
			halign: 'center',
			width: 80,
			fixed: true
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			align: 'center',
			halign: 'center',
			width: 130,
			fixed: true
		},
		{
			field: 'CURR',
			title: '金额',
			align: 'right',
			halign: 'center',
			fieldtype: "N",
			width: 100,
			fixed: true,
			formatter: function(value, row, index) {
				return Number(value).toFixed(2);
			}
		},
		{
			field: 'OPERANT',
			title: '操作员',
			align: 'center',
			halign: 'center',
			width: 130,
			fixed: true
		},
		{
			field: 'REMARK',
			title: '备注',
			width: 200,
			fixed: true,
			align: 'center',
			halign: 'center',
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
			align: 'center',
			halign: 'center',
			width: 160,
			fixed: true,
			formatter: function(value, row, index) {
				var allowcheck = levelid == 0 || levelid==3 || levelid ==4 || levelid == 5;
				var html = "";
				var checkman = row.CHECKMAN==undefined?"":row.CHECKMAN;
				var stg = row.STATETAG;
				if (row.ACTION == '1') {
					return "";
				} else if (stg==1 && checkman == epname && allowcheck) {
					html += '<input type="button" class="m-btn" value="取消审核" onclick="checknote(' + index + ',0)">';
				} else if (stg == 1&& checkman.length==0) {
					var notedate = row.NOTEDATE;
					var date = notedate.substring(0, 10);
					 if (allowchedan == 1 && Number(row.ISTODAY)==1)
						html += '<input type="button" class="m-btn" value="撤单" onclick="notecancel(' + index + ')">';
					if(allowcheck)
						html += '<input type="button" class="m-btn" value="审核" onclick="checknote(' + index + ',1)">';
				} else if (stg == 0 && row.OPERANT == epname) {
					html += '<input type="button" class="m-btn" value="删除" onclick="delnote(' + index + ')">' 
					+ '<input type="button" class="m-btn" id="' + index + '" value="提交" onclick="updatestatetag(' + index + ')">';
				}
				return html;
			}
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid("keyCtr", "updatenoted()");
	getnotedata();
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
});
//添加框
function addnoted() {
	$('#ucustname,#upayname,#uhousename,#ucurr,#uhandno,#uremark').removeProp("readonly");
	$('#ucustname,#upayname,#uhousename').next().show();
	$('#div_updatebtn').show();
	$("#ud table :input").val("");
	$("#ud input#btn_update0").val("保存并继续");
	$('#uhousename').val(getValuebyName('HOUSENAME'));
	$('#uhouseid').val(getValuebyName('HOUSEID'));
	getserdatetime();
	$('#ud').dialog("setTitle","添加").dialog('open');
}
//修改框
function updatenoted() {
	var arrs = $('#gg').datagrid('getSelected');
	if (!arrs) {
		alerttext('请选择一行数据，进行编辑');
	} else {
		if (arrs.STATETAG == 0 && arrs.OPERANT == epname) {
			$("#ud input#btn_update0").val("保存");
			$('#ud').dialog("setTitle", "编辑");
			$('#ud').dialog('open');
			if (arrs) {
				$('#ucustname,#upayname,#uhousename,#ucurr,#uhandno,#uremark').removeProp("readonly");
				$('#ucustname,#upayname,#uhousename').next().show();
				$('#div_updatebtn').show();
			}
			$('#unotedate').datetimebox({
				readonly: false,
				hasDownArrow: true
			});
		}else{
			$('#ud').dialog("setTitle", "浏览");
			$('#ud').dialog('open');
			$('#ucustname,#upayname,#uhousename,#ucurr,#uhandno,#uremark').prop("readonly",true);
			$('#ucustname,#upayname,#uhousename').next().hide();
			$('#div_updatebtn').hide();
			$('#unotedate').datetimebox({
				readonly: true,
				hasDownArrow: false
			});
		}
		getnotebyid(arrs.NOTENO);
	};
}

//-----------------------------------------------ajax(数据)
//获取服务器当前日期时间
function getserdatetime() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "serverdatetime"
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			var datetime = data.SERVERDATETIME;
			$('#unotedate').datetimebox({
				readonly: false,
				hasDownArrow: true
			});
			changedatebj = false;
			$('#unotedate').datetimebox('setValue', datetime);
			changedatebj = true;
			todaydate = datetime.substring(0, 10);
		}
	});
}
//高级搜索
var searchb = false;
function toggle() {
	ser = 'sear';
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
function getnotelist(page) {
	var mindate = $('#smindate').datebox("getValue");
	var maxdate = $('#smaxdate').datebox("getValue");
	currentdata["mindate"] = mindate;
	currentdata["maxdate"] = maxdate;
	currentdata["erpser"] = "custcurrlist";
	currentdata["fieldlist"] = "a.houseid,d.housename,a.statetag,a.custid,b.custname,c.payname,a.noteno,a.notedate," + "a.curr,a.remark,a.operant,a.id,a.handno,a.checkman";
	currentdata["rows"] = pagecount;
	currentdata["sort"] = "noteno";
	currentdata["page"] = page;
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
			if (valideData(data)) {
				var totals = data.total.split('^');
				$('#pp_total').html(data.total);
				$('#pp').setPage({
					pageCount: Math.ceil(totals[0] / pagecount),
					current: Number(page)
				});
				data.footer = [{
					NOTENO: "合计",
					CURR: data.totalcurr
				}];
				$('#gg').datagrid('loadData', data);
				dqzjl = totals[0];
				dqzcurr = totals[1];
				$('#qsnotevalue').focus();
			}
		}
	});
}
//获取数据
function getnotedata() {
	currentdata = {};
	var findbox = $('#qsnotevalue').val();
	currentdata["findbox"] = findbox;
	getnotelist(1);
}
//高级搜索数据
function searchnote() {
	currentdata = {};
	var noteno = $('#snoteno').val();
	var handno = $('#shandno').val();
	var remark = $('#sremark').val();
	var custid = Number($('#scustid').val());
	var operant = $('#soperant').val();
	var payid = Number($('#spayid').val());
	var houseid = Number($('#shouseid').val());
	currentdata["noteno"] = noteno;
	currentdata["handno"] = handno;
	currentdata["remark"] = remark;
	currentdata["custid"] = custid;
	currentdata["operant"] = operant;
	currentdata["payid"] = payid;
	currentdata["houseid"] = houseid;
	getnotelist(1);
}
//清除高级搜索数据
function resetsearch() {
	$("#smindate,#smaxdate").datebox("setValue",top.getservertime());
	$('#soperant').val("");
	$('#soperant').val("");
	$('#snoteno').val("");
	$('#spayname').val("");
	$('#scustname').val("");
	$('#scustid').val("");
	$('#spayid').val("");
	$('#shandno').val("");
	$('#sremark').val("");
	$('#shouseid').val("");
	$('#shousename').val("");
}
//根据单据号获取明细
function getnotebyid(noteno) {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getcustcurrbyid",
			noteno: noteno,
			showbalcurr: 1,
			fieldlist: "a.id,a.noteno,a.notedate,a.handno,b.custname,b.custid,c.payname," 
			+ "c.payid,a.curr,a.remark,a.statetag,a.lastop,d.houseid,d.housename"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				total = data.total;
				rows = data.rows;
				if (total > 0) {
					var row = rows[0];
					$('#unoteno').val(row.NOTENO);
					changedatebj = false;
					$('#unotedate').datetimebox('setValue', row.NOTEDATE);
					changedatebj = true;
					$('#uhandno').val(row.HANDNO);
					$('#ucurr').val(row.CURR);
					$('#ucustname').val(row.CUSTNAME);
					$('#uremark').val(row.REMARK);
					$('#upayname').val(row.PAYNAME);
					$('#uhousename').val(row.HOUSENAME);
					$('#uhouseid').val(row.HOUSEID);
					dqcurr = row.CURR;
					$('#ubalcurr').val(row.BALCURR);
				}
			}
		}
	});
}
//撤单
function notecancel(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	if (!row) {
		alerttext("单据无效！");
		return;
	}
	var noteno = row.NOTENO;
	$.messager.confirm(dialog_title, '您确认要撤销' + noteno + '？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "custcurrcancel",
					noteno: noteno
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex",noteno);
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								STATETAG: "0"
							}
						}).datagrid('refresh');
					}
				}
			});
		}
	});
}
//直接提交
function updatestatetag(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	if (!row) {
		alerttext("单据无效！");
		return;
	}
	var noteno = row.NOTENO;
	$.messager.confirm(dialog_title, '您确认要提交' + noteno + '？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "updatecustcurrbyid",
					noteno: noteno,
					statetag: 1
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex",noteno);
						$('#gg').datagrid('updateRow', {
							index: index,
							row: {
								STATETAG: 1
							}
						});
					}
				}
			});
		}
	});
}
//保存或提交
function updatenote(statetag) {
	if ($('#ucustname').val() == "") {
		alerttext('请选择客户！');
		return;
	} else if ($('#upayname').val() == "") {
		alerttext("请选择结算方式！");
		return;
	}
	var noteno = $('#unoteno').val();
	var notedate = $('#unotedate').datetimebox("getValue");
	var custid = $('#ucustid').val();
	var curr = $('#ucurr').val();
	var handno = $('#uhandno').val();
	var remark = $('#uremark').val();
	var payid = $('#upayid').val();
	var payno = $('#upayno').val();
	var houseid = $('#uhouseid').val();
	var erpser = "updatecustcurrbyid";
	if(noteno.length==0) erpser="addcustcurr";
	var dofn = function() {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: erpser,
				notedatestr: notedate,
				custid: custid,
				curr: curr,
				handno: handno,
				remark: remark,
				payid: payid,
				payno: payno,
				statetag: statetag,
				noteno: noteno,
				houseid: houseid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					var $dg = $('#gg');
					var index = $dg.datagrid("getRowIndex",noteno);
					if(noteno.length>0){
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								CUSTID: custid,
								NOTEDATE: notedate,
								CUSTNAME: $('#ucustname').val(),
								PAYID: payid,
								PAYNAME: $('#upayname').val(),
								CURR: curr,
								HANDNO: handno,
								REMARK: remark,
								STATETAG: statetag,
								HOUSEID: houseid,
								HOUSENAME: $('#uhousename').val()
							}
						}).datagrid('updateFooter', {
							CURR: Number(dqzcurr) - Number(dqcurr) + Number(curr)
						}).datagrid("refreshRow",index);
						dqzcurr = Number(dqzcurr) - Number(dqcurr) + Number(curr);
						$("#ud").dialog("close");
					}else{
						noteno =  data.NOTENO;
						notedate =  data.NOTEDATE;
						id =  data.ID;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ID: id,
								NOTENO: noteno,
								NOTEDATE: notedate,
								OPERANT: getValuebyName("EPNAME"),
								CUSTID: custid,
								CUSTNAME: $('#ucustname').val(),
								PAYID: payid,
								PAYNAME: $('#upayname').val(),
								CURR: curr,
								HANDNO: handno,
								REMARK: remark,
								STATETAG: statetag,
								ISTODAY: 1,
								HOUSEID: houseid,
								HOUSENAME: $('#uhousename').val()
							}
						}).datagrid("refresh");
						$("#qsnotevalue,#ud table :input").val("");
						$("#smaxdate").datebox("setValue", top.getservertime());
						getserdatetime();
						$('#uhousename').val(getValuebyName('HOUSENAME'));
						$('#uhouseid').val(getValuebyName('HOUSEID'));
						if (statetag == 1) $('#ud').dialog('close');
					}
				}
			}
		});
	};
	if (statetag == 1) {
		$.messager.confirm(dialog_title, '您确认要提交该单据' + noteno + '？',
		function(r) {
			if (r) {
				dofn();
			}
		});
	} else {
		dofn();
	}
}
//删除
function delnote(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	if (!row) {
		alerttext("单据无效！");
		return;
	}
	var noteno = row.NOTENO;
	$.messager.confirm(dialog_title, '您确认要删除' + noteno + '？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delcustcurrbyid",
					noteno: noteno
				},
				//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex",noteno);
						var row = $dg.datagrid("getSelected");
						$dg.datagrid('deleteRow', index).datagrid('refresh').datagrid('updateFooter', {
							CURR: Number(dqzcurr) - Number(row.CURR)
						});
						dqzcurr = Number(dqzcurr) - Number(row.CURR);
						dqzjl = dqzjl - 1;
						$('#pp_toal').html(dqzjl);
					}
				}
			});
		}
	});
}
//审核
function checknote(index,stg){
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	var msg = "您确定审核？";
	if(stg==0)
		msg = "您确定取消审核？";
	$.messager.confirm(dialog_title, msg,
			function(r) {
				if (r) {
					var noteno = row.NOTENO;
					alertmsg(2);
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "checkcustcurrbyid",
							noteno: noteno,
							checkid: stg
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									$dg.datagrid("updateRow",{
										index: index,
										row: {
											CHECKMAN: (stg==0?"":epname)
										}
									});
								}
								$dg.datagrid("refresh");
							} catch(e) {
								// TODO: handle exception
								console.error(e);top.wrtiejsErrorLog(e);
							}
						}
					});
				}
			});
}
//打印表格
function PrintTable() {
	var curr = $('#ucurr').val();
	var noteno = $('#unoteno').val();
	var handno = $('#uhandno').val();
	var custname = $('#ucustname').val();
	var remark = $('#uremark').val();
	var payname = $('#upayname').val();
	var housename = $('#uhousename').val();
	var operant = $('#uoperant').val();
	var curr1 = Arabia_to_Chinese(curr);
	$('#printgg').html("");
	htmlStrp = '<table>';
	htmlStrp += '<tr><td align="right" class="a1"colspan="4"><font face="宋体" size="2">NO:' + noteno + '</font></td></tr>';
	htmlStrp += '<caption><b><font face="黑体" size="5">销售收款单<br/>&nbsp;</font></b></caption>';
	htmlStrp += '<tr><td class="t5">打印时间：</td><td align="left" class="t9">' + datetime + '</td><td align="right" class="t9">自编号：</td><td align="left" class="t9">' + handno + '</td></tr>';
	htmlStrp += '<tr><td align="left" class="t2">今收到：</td><td align="left" class="t6" colspan="3">' + custname + '</td></tr>';
	htmlStrp += '<tr><td align="left" class="t7">人民币：</td><td align="left" class="d6">' + curr1 + '</td><td align="right" class="d3">￥：' + curr + '</td><td align="right" class="t3">结算方式：' + payname + '</td></tr>';
	htmlStrp += '<tr><td align="left" class="t8">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td><td align="left" class="t6" colspan="3">' + remark + '</td></tr>';
	htmlStrp += '<tr><td align="left" class="t4">店&nbsp;&nbsp;&nbsp;&nbsp;铺：</td><td align="left" class="t4">' + housename + '</td><td align="right" colspan="2" class="t4">收款人：' + operant + '</td></tr>';
	htmlStrp += '</table>';
	$('#printgg').html(htmlStrp);
}

function demoPrint(toPrview) {
	var arrs1 = $('#gg').datagrid('getSelections');
	if (arrs1.length != 1) {
		alerttext("请选择需要打印的单据！");
	} else {
		demoPrint1(toPrview);
	}
}
//打印样式
function demoPrint1(toPrview) {
	PrintTable();
	LODOP = getLodop();
	var style1 = "table{width:100%;border-style: solid;border-color: #000000;  border-collapse:collapse;border-width:0px 0px 0px 0px;}" + "table td{border: solid #000000; border-width:0px 1px 1px 1px;width:auto;}" + "table .a1{border-style: none;border-color: #000000;font-weight:bold;border-width:0px 0px 0px 0px; padding-bottom:10px;}" + "table .t2{border-style: solid;border-color: #000000;font-weight:bold;border-width:1px 0px 0px 1px; padding-top:30px; width:80px;}" + "table .t3{border-style: solid;border-color: #000000;font-weight:bold;border-width:1px 1px 1px 0px; padding-top:30px;}" + "table .d3{border-style: solid;border-color: #000000;font-weight:bold;border-width:1px 0px 1px 0px; padding-top:30px;}" + "table .t4{border-style: none;border-color: #000000;font-weight:bold;border-width:0px 0px 0px 1px; padding-top:10px;}" + "table .t5{border-style: solid;border-color: #000000;border-width:0px 0px 1px 0px; padding-bottom:10px; width:80px;}" + "table .t6{border-style: solid;border-color: #000000;font-weight:bold;border-width:1px 1px 1px 0px; padding-top:30px;}" + "table .d6{border-style: solid;border-color: #000000;font-weight:bold;border-width:1px 0px 1px 0px; padding-top:30px;}" + "table .t7{border-style: solid;border-color: #000000;font-weight:bold;border-width:0px 0px 0px 1px; padding-top:30px; width:80px;}" + "table .t8{border-style: solid;border-color: #000000;font-weight:bold;border-width:0px 0px 1px 1px; padding-top:30px; width:80px;}" + "table .t9{border-style: solid dashed;border-color: #000000;border-width:0px 0px 1px 0px; padding-bottom:10px;}";
	var strBodyStyle = "<style>" + style1 + "</style>";
	var strFormHtml = strBodyStyle + "<body>" + document.getElementById("printgg").innerHTML + "</body>";
	LODOP.SET_PRINTER_INDEXA("0");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	LODOP.PRINT_INIT("销售收款单");
	LODOP.SET_PRINT_STYLE("FontSize", 18);
	LODOP.SET_PRINT_STYLE("Bold", 1);
	LODOP.ADD_PRINT_TEXT(50, 31, 750, 1000, "销售收款单");
	LODOP.ADD_PRINT_HTM(50, 26, 720, 1000, strFormHtml);
	if (toPrview) LODOP.PREVIEW();
	else LODOP.PRINT();
};
//金额小写 转大写
function Arabia_to_Chinese(Num) {
	for (i = Num.length - 1; i >= 0; i--) {
		Num = Num.replace(",", "");
		Num = Num.replace(" ", "")
	}
	Num = Num.replace("￥", "");
	if (isNaN(Num)) {
		alert("请检查小写金额是否正确");
		return;
	}
	part = String(Num).split(".");
	newchar = "";
	for (i = part[0].length - 1; i >= 0; i--) {
		if (part[0].length > 10) {
			alert("位数过大，无法计算");
			return "";
		}
		tmpnewchar = "";
		perchar = part[0].charAt(i);
		switch (perchar) {
		case "0":
			tmpnewchar = "零" + tmpnewchar;
			break;
		case "1":
			tmpnewchar = "壹" + tmpnewchar;
			break;
		case "2":
			tmpnewchar = "贰" + tmpnewchar;
			break;
		case "3":
			tmpnewchar = "叁" + tmpnewchar;
			break;
		case "4":
			tmpnewchar = "肆" + tmpnewchar;
			break;
		case "5":
			tmpnewchar = "伍" + tmpnewchar;
			break;
		case "6":
			tmpnewchar = "陆" + tmpnewchar;
			break;
		case "7":
			tmpnewchar = "柒" + tmpnewchar;
			break;
		case "8":
			tmpnewchar = "捌" + tmpnewchar;
			break;
		case "9":
			tmpnewchar = "玖" + tmpnewchar;
			break;
		default:
			break;
		}
		switch (part[0].length - i - 1) {
		case 0:
			tmpnewchar = tmpnewchar + "元";
			break;
		case 1:
			if (perchar != 0) tmpnewchar = tmpnewchar + "拾";
			break;
		case 2:
			if (perchar != 0) tmpnewchar = tmpnewchar + "佰";
			break;
		case 3:
			if (perchar != 0) tmpnewchar = tmpnewchar + "仟";
			break;
		case 4:
			tmpnewchar = tmpnewchar + "万";
			break;
		case 5:
			if (perchar != 0) tmpnewchar = tmpnewchar + "拾";
			break;
		case 6:
			if (perchar != 0) tmpnewchar = tmpnewchar + "佰";
			break;
		case 7:
			if (perchar != 0) tmpnewchar = tmpnewchar + "仟";
			break;
		case 8:
			tmpnewchar = tmpnewchar + "亿";
			break;
		case 9:
			tmpnewchar = tmpnewchar + "拾";
			break;
		default:
			break;
		}
		newchar = tmpnewchar + newchar;
	}
	if (Num.indexOf(".") != -1) {
		if (part[1].length > 2) {
			part[1] = part[1].substr(0, 2)
		}
		for (i = 0; i < part[1].length; i++) {
			tmpnewchar = "";
			perchar = part[1].charAt(i);
			switch (perchar) {
			case "0":
				tmpnewchar = "零" + tmpnewchar;
				break;
			case "1":
				tmpnewchar = "壹" + tmpnewchar;
				break;
			case "2":
				tmpnewchar = "贰" + tmpnewchar;
				break;
			case "3":
				tmpnewchar = "叁" + tmpnewchar;
				break;
			case "4":
				tmpnewchar = "肆" + tmpnewchar;
				break;
			case "5":
				tmpnewchar = "伍" + tmpnewchar;
				break;
			case "6":
				tmpnewchar = "陆" + tmpnewchar;
				break;
			case "7":
				tmpnewchar = "柒" + tmpnewchar;
				break;
			case "8":
				tmpnewchar = "捌" + tmpnewchar;
				break;
			case "9":
				tmpnewchar = "玖" + tmpnewchar;
				break;
			default:
				break;
			}
			if (i == 0) tmpnewchar = tmpnewchar + "角";
			if (i == 1) tmpnewchar = tmpnewchar + "分";
			newchar = newchar + tmpnewchar;
		}
	}
	while(newchar.search("零零") != -1)
	newchar = newchar.replace("零零", "零");
	newchar = newchar.replace("零亿", "亿");
	newchar = newchar.replace("亿万", "亿");
	newchar = newchar.replace("零万", "万");
	newchar = newchar.replace("零元", "元");
	newchar = newchar.replace("零角", "");
	newchar = newchar.replace("零分", "");
	if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角") 
		newchar = newchar + "整"
	return newchar;
}

var getincomebal = function() {
	var custid = $('#ucustid').val();
	var houseid = $('#uhouseid').val();
	houseid = xsdpdz == 1 ? houseid : 0;
	if(xsdpdz == 1&&custid==0){
		alerttext("请选择客户！");
		return;
	}
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getincomebal",
			houseid: Number(houseid),
			custid: Number(custid)
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				var balcurr = Number(data.CURR);
				$('#abalcurr,#ubalcurr').val(balcurr.toFixed(2));
			}
		}
	});
}
$('#gg').datagrid({
    onLoadSuccess : function() {
        var $footer_t_b = $('.datagrid-footer .datagrid-footer-inner table tbody');
        var $footer_prev_b = $('.datagrid-footer').prev('.datagrid-body');
        var itemIndex = $footer_t_b.find('tr').last().attr('datagrid-row-index');
        if (itemIndex) {
            var $first_tr = $footer_t_b.find('tr');
            var lineHeight = $first_tr.first().height();
            var bodyHeight = $footer_prev_b.height();
            var tdCount = $first_tr.find('td:visible').size();
            itemIndex = parseInt(itemIndex) + 1;
            $('.datagrid-footer').prev('.datagrid-body').css('height', bodyHeight - lineHeight);
            $footer_t_b.append("<tr class='datagrid-row' datagrid-row-index='"+ itemIndex +"'>" +
                "<td></td>" +
                "<td colspan='"+ (tdCount - 1) +"'>" +
                "<b style='color:#000000;'>单据号：</b>" +
                //"<b style='color:#ff3b30;'>红色提交收银台未付款；</b>" +
                "<b style='color:#00959a;margin-left: 5px;'>绿色已提交；</b>" +
                "<b style='color:#ff7900;margin-left: 5px;'>黄色未提交。</b>" +
                //"<b style='color:#ff7900;margin-left: 15px;'>失效订单</b>" +
                "</td>" +
                "</tr>");
        }
    }
});
</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpList/CustHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<!-- 结算方式帮助列表 -->
	<jsp:include page="../HelpList/PayWayHelpList.jsp"></jsp:include>
		<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
</body>
</html>