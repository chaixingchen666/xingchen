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
<title>店铺费用收支</title>
</head>
<link rel="stylesheet" href="/skydesk/css/icon.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js"></script>
<body class="easyui-layout layout panel-noscroll">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addnoted()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、费用名称<回车搜索>" data-end="yes" data-enter="getnotedata()" class="ipt1" id="qsnotevalue" >
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
	<span id="serbtn" class="fr hide">
	<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchnote();toggle();"> 
	<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</span>
</div>
<div class="fr">
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
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
<div class="text">
<input type="text" class="hig25 wid160" id="snoteno" name="snoteno" />
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selecthouse('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">费用名称</div>
<div class="text"><input type="text" class="edithelpinput cg_help" data-value="#scgid"
						id="scgname" name="scgname" placeholder="<选择或输入>" /><span onclick="Chargesdata('search')"></span>
						<input type="hidden" id="scgid" name="scgid">
</div>
</div>
<div class="s-box"><div class="title">收支方向</div>
<div class="text"><select name="sfs" id="sfs" class="sele1">
							<option value="0">收入</option>
							<option value="1">支出</option>
							<option value="2" selected="selected">所有</option>
					</select>
</div>
</div>
<div class="s-box"><div class="title">结算方式</div>
<div class="text"><input type="text" class="edithelpinput wid145 hig25 pw_help" data-value="#spayid" maxlength="20" placeholder="<输入或选择>" id="spayname"
					name="spayname" /> <span onclick="Paywaydata('search')"></span>
						<input type="hidden" id="spayid" name="spayid">
</div>
</div>
<div class="s-box"><div class="title">自编号</div>
<div class="text"><input type="text" class="hig25 wid160" id="shandno" name="shandno" />
</div>
</div>
<div class="s-box"><div class="title">备注</div>
<div class="text"><input type="text" class="hig25 wid160" id="sremark" name="sremark" />
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
<!-- 修改 -------------------------------------------------------------->
<div id="ud" title="编辑" style="width: 600px; height: 390px;" class="easyui-dialog" closed=true>
	<div style="overflow: auto; height:305px ">
	<table width="600" border="0" cellspacing="10">
		<tr>
			<td width="70px" align="right">单据号</td>
			<td width="180px" align="left"><input type="text"
				style="width: 160px; height: 25px" placeholder="<保存时自动生成>"
				id="unoteno" name="unoteno" width="175px" readonly />
				
				<input type="hidden" id="djid" name="djid">
				</td>
				<td width="70px" align="right">日期</td>
			<td width="180px" align="left">
							<input id="unotedate" type="text" style="width: 162px;height:29px" class="easyui-datetimebox" data-options="showSeconds:true,readonly:true,hasDownArrow:false">
				</td>
		</tr>
		<tr>
			<td align="right" align="right" width="70px">店铺</td>
            <td width="180px" align="left">
            <input class="edithelpinput house_help wid145 hig25" type="text" id="uhousename" name="uhousename" maxlength="20" placeholder="<选择或输入>" data-value="#uhouseid"
            value=""><span onclick="selecthouse('updatehouse')"></span><input type="hidden" id="uhouseid" name="uhouseid"> </td>
            </tr>
            <tr>
			<td align="right" align="right" width="70px">销售部门</td>
            <td width="180px" align="left">
            <input class="edithelpinput dpt_help hig25" type="text" id="udptname" name="udptname" maxlength="20" placeholder="<选择或输入>" data-value="#udptid"
            value=""><span onclick="selectdepartment('updatedpt')"></span><input type="hidden" id="udptid" name="udptid"> </td>
		<td align="right" align="right" width="70px">费用名称</td>
			<td width="180px" align="left"><input type="text" class="edithelpinput cg_help wid145 hig25" data-value="#ucgid"
				id="ucgname" name="ucgname" placeholder="<选择或输入>" /><span onclick="Chargesdata('update')"></span>
				<input type="hidden" id="ucgid" name="ucgid"></td>
		</tr>
		<tr>
			<td align="right" width="70px">收支方向</td>
			<td align="center">
			<input type="radio" id="ufs0" name="ufs" value="0">收入 <input type="radio" id="ufs1" name="ufs" value="1" checked="checked">支出</td>
			<td align="right" width="70px">结算方式</td>
			<td width="180px" align="left"><input type="text" class="edithelpinput pw_help wid145 hig25" data-value="#upayid" placeholder="<输入或选择>" id="upayname"
				name="upayname" /> <span onclick="Paywaydata('update')"></span>
				<input type="hidden" id="upayid" name="upayid"> 
	             <input type="hidden" id="upayno" name="upayno"> </td>
		</tr>
		<tr>
			<td align="right" width="70px">金额</td>
			<td width="180px" align="left"><input type="text" placeholder="<输入>"
				id="ucurr" name="ucurr" style="width: 160px; height: 25px" /></td>
				<td align="right" width="70px">自编号</td>
			<td width="180px" align="left"><input type="text" placeholder="<输入>"
				id="uhandno" name="uhandno" style="width: 160px; height: 25px" />
			</td>
		</tr>
		<tr>
			<td align="right" align="right" width="70px">备注</td>
			<td width="180px" align="left" colspan="3"><input type="text" placeholder="<输入>"
				id="uremark" name="uremark" style="width: 452px; height: 35px" />
			</td>
		</tr>
	</table></div>
	<div class="dialog-footer" style="text-align: center;" id="div_updatebtn">
	<input type="button" class="btn1" value="保存" id="btn_update0" name="update" onclick="updatenote(0)">
	<input type="button" class="btn1" value="保存并提交" id="btn_update1" name="update1" onclick="updatenote(1)">
	</div>
</div>
<script type="text/javascript">
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
var todaydate = "";
var s = false;
var pgid = 'pg1908';
var levelid = getValuebyName("GLEVELID");
var epname = getValuebyName("EPNAME");
setqxpublic();
$(function() {
	getserdatetime();
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getnotelist(p);
		}
	});
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
				$('#unotedate').val(rowData.NOTEDATE);
				$('#uhandno').val(rowData.HANDNO);
				$('#ucurr').val(rowData.CURR);
				$('#upayname').val(rowData.PAYNAME);
				$('#upayid').val(rowData.PAYID);
				$('#uremark').val(rowData.REMARK);
				$('#djid').val(rowData.ID);
				$('#ucgname').val(rowData.CGNAME);
				$('#ucgid').val(rowData.CGID);
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
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
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'NOTENO',
			title: '单据号',
			align: 'center',
			halign: 'center',
			width: 120,
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
			title: '日期',
			align: 'center',
			halign: 'center',
			width: 100,
			fixed: true
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			align: 'center',
			halign: 'center',
			width: 150,
			fixed: true
		},
		{
			field: 'DPTNAME',
			title: '销售部门',
			align: 'center',
			halign: 'center',
			width: 80,
			fixed: true
		},
		{
			field: 'CGNAME',
			title: '费用名称',
			align: 'center',
			halign: 'center',
			width: 80,
			fixed: true
		},
		{
			field: 'FS',
			title: '收支',
			align: 'center',
			halign: 'center',
			width: 80,
			fixed: true,
			formatter: function(value, row, index) {
				if (row.FS == '1') {
					return '支出';
				} else if (row.FS == '0') {
					return '收入';
				}
			}
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
			field: 'CURR',
			title: '金额',
			align: 'right',
			halign: 'center',
			width: 100,
			fixed: true,
			formatter: function(value, row, index) {
				return Number(value).toFixed(2);
			}
		},
		{
			field: 'REMARK',
			title: '备注',
			align: 'left',
			halign: 'center',
			width: 100,
			fixed: true
		},
		{
			field: 'HANDNO',
			title: '自编号',
			align: 'center',
			halign: 'center',
			width: 100,
			fixed: true
		},
		{
			field: 'OPERANT',
			title: '操作员',
			align: 'center',
			halign: 'center',
			width: 100,
			fixed: true
		},
		{
			field: 'ID',
			title: 'ID',
			width: 200,
			hidden: true
		},
		{
			field: 'PAYID',
			title: 'payid',
			width: 200,
			hidden: true
		},
		{
			field: 'LASTDATE',
			title: '单据创建日期',
			width: 200,
			hidden: true
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
		toolbar: "#toolbars",
		pageSize: 20
	}).datagrid("keyCtr", "updatenoted()");
	getnotedata();
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
});
//添加框
function addnoted() {
	$('#ucgname,#upayname,#uhousename,#ucurr,#uhandno,#uremark').removeProp("readonly");
	$('#ucgname,#upayname,#uhousename').next().show();
	$('#div_updatebtn').show();
	$("#ud table :input").val("");
	$("#ud input#btn_update0").val("保存并继续");
	$('#uhousename').val(getValuebyName('HOUSENAME'));
	$('#uhouseid').val(getValuebyName('HOUSEID'));
	getserdatetime();
	$('#ud').dialog("setTitle", "添加").dialog('open');
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
				$('#ucgname,#upayname,#uhousename,#ucurr,#uhandno,#uremark').removeProp("readonly");
				$('#ucgname,#upayname,#uhousename').next().show();
				$('#div_updatebtn').show();
			}
			$('#unotedate').datetimebox({
				readonly: false,
				hasDownArrow: true
			});
		} else {
			$('#ud').dialog("setTitle", "浏览");
			$('#ud').dialog('open');
			$('#ucgname,#upayname,#uhousename,#ucurr,#uhandno,#uremark').prop("readonly", true);
			$('#ucgname,#upayname,#uhousename').next().hide();
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
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	//		 	var sort = options.sortName;
	//		 	var order = options.sortOrder;
	currentdata["erpser"] = "housecostlist";
	//		 	currentdata["sort"] = sort;
	//		 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.noteno,a.notedate,a.curr,b.housename,a.id,c.cgname,a.statetag,a.operant,a.fs,d.payname,a.remark,a.handno,a.checkman,a.dptid,e.dptname";
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
					dqzjl = data.total;
					dqzcurr = data.totalcurr;
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					data.footer = [{
						ROWNUMBER: "合计",
						CURR: data.totalcurr
					}];
					$dg.datagrid('loadData', data);
					if (data.total > 0) $('#gg').datagrid('scrollTo', 0);
					$('#pp_total').html(data.total);
					$('input[type=button]:first').focus();
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
		findbox: value
	};
	getnotelist(1);
}

//高级搜索
function searchnote() {
	var noteno = $('#snoteno').val();
	var handno = $('#shandno').val();
	var remark = $('#sremark').val();
	var operant = $('#soperant').val();
	var cgid = Number($('#scgid').val());
	var payid = Number($('#spayid').val());
	var payno = $('#spayno').val();
	var fs = $('#sfs').val();
	var houseid = Number($('#shouseid').val());
	currentdata = {
		operant: operant,
		noteno: noteno,
		handno: handno,
		remark: remark,
		cgid: cgid,
		fs: fs,
		payid: payid,
		payno: payno,
		houseid: houseid
	};
	getnotelist(1);
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
//清除高级搜索数据
function resetsearch() {
	$("#smindate,#smaxdate").datebox("setValue",top.getservertime());
	$('#sfs').val("2");
	$('#snoteno').val("");
	$('#spayname').val("");
	$('#scgname').val("");
	$('#scgid').val("");
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
			erpser: "gethousecostbyid",
			noteno: noteno,
			showbalcurr: 1,
			fieldlist: "*"
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
					$('#ucgname').val(row.CGNAME);
					$('#uremark').val(row.REMARK);
					$('#upayname').val(row.PAYNAME);
					$('#uhousename').val(row.HOUSENAME);
					$('#uhouseid').val(row.HOUSEID);
					dqcurr = row.CURR;
					$('#ubalcurr').val(row.BALCURR);
					$("#ufs" + row.FS).prop("checked", true);
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
					erpser: "housecostcancel",
					noteno: noteno
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex", noteno);
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
	var fs = row.FS;
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
					erpser: "updatehousecostbyid",
					noteno: noteno,
					fs: fs,
					statetag: 1
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex", noteno);
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
	if ($('#ucgname').val() == "") {
		alerttext('请选择费用名称！');
		return;
	} else if ($('#upayname').val() == "") {
		alerttext("请选择结算方式！");
		return;
	}
	var noteno = $('#unoteno').val();
	var notedate = $('#unotedate').datetimebox('getValue');
	var cgid = $('#ucgid').val();
	var curr = $('#ucurr').val();
	var handno = $('#uhandno').val();
	var remark = $('#uremark').val();
	var payid = $('#upayid').val();
	var payno = $('#upayno').val();
	var dptid = $('#udptid').val();
	var houseid = $('#uhouseid').val();
	var fs = 0;
	if($("#ufs1").prop("checked")) fs=1;
	var erpser = "updatehousecostbyid";
	if (noteno.length == 0) erpser = "addhousecost";
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
				cgid: cgid,
				curr: curr,
				fs: fs,
				dptid: dptid,
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
					var index = $dg.datagrid("getRowIndex", noteno);
					if (noteno.length > 0) {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								CGID: cgid,
								NOTEDATE: notedate,
								CGNAME: $('#ucgname').val(),
								PAYID: payid,
								PAYNAME: $('#upayname').val(),
								FS: fs,
								DPTID: dptid,
								DPTNAME: $("#udptname").val(),
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
					} else {
						noteno = data.NOTENO;
						notedate = data.NOTEDATE;
						id = data.ID;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ID: id,
								NOTENO: noteno,
								NOTEDATE: notedate,
								OPERANT: getValuebyName("EPNAME"),
								CGID: cgid,
								CGNAME: $('#ucgname').val(),
								FS: fs,
								PAYID: payid,
								PAYNAME: $('#upayname').val(),
								DPTID: dptid,
								DPTNAME: $("#udptname").val(),
								CURR: curr,
								HANDNO: handno,
								ISTODAY: 1,
								REMARK: remark,
								STATETAG: statetag,
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
					erpser: "delhousecostbyid",
					noteno: noteno
				},
				//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						var index = $dg.datagrid("getRowIndex", noteno);
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
					var fs = row.FS;
					alertmsg(2);
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "checkhousecostbyid",
							noteno: noteno,
							checkid: stg,
							fs: fs
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
<jsp:include page="../HelpList/DPTHelpList.jsp"></jsp:include>
<!-- 结算方式帮助列表 -->
<jsp:include page="../HelpList/PayWayHelpList.jsp"></jsp:include>
<!-- 费用项目帮助列表 -->
<jsp:include page="../HelpList/ChargesHelpList.jsp"></jsp:include>
</body>
</html>