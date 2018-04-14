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
<title>供应商档案</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/webuploader/webuploader.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll" style="margin: auto;">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addprovd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updateprovd()">
	<input type="text" data-end="yes" placeholder="输入供应商名称或简称<回车搜索>" class="ipt1" id="qsprovvalue" maxlength="20" data-enter="getprovdata()">
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
</div>
<div class="fl hide" id="searchbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchprov();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr">
	<input type="button" class="btn3" style="width: 45px" value="导入" onclick="openimportd()"> 
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchprov();toggle();">
<div class="search-box">
	<div class="s-box">
	<div class="title">供应商</div>
	<div class="text"><input class="wid160 hig25" id=sprovname maxlength="40" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">联系人</div>
	<div class="text"><input class="wid160 hig25" id="slinkman" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">移动电话</div>
	<div class="text"><input class="wid160 hig25" id="smobile" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">备注</div>
	<div class="text"><input class="wid160 hig25" id="sremark" maxlength="40" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">状态</div>
	<div class="text">
	<label>
	<input type="radio" name="snouse" id="st1" value="0">在用
	</label>
	<label>
	<input type="radio" name="snouse" id="st2" value="1">禁用
	</label>
	<label>
	<input type="radio" name="snouse" id="st3" value="2" checked="checked">所有
	</label>
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
<!-- 修改供应商窗 -->
<div id="ud" title="<span id='udtitle'>编辑供应商</span>" style="width: 600px; height: 400px;" class="easyui-dialog" closed=true>
<div style="margin-top: 10px; text-align: center;">
<input type="hidden" id="uindex" value="0">
<input type="hidden" id="uprovid" value="0">
<table class="table1" cellspacing="10">
<tr>
	<td width="100" align="right" class="header skyrequied">供应商名称</td>
	<td width="160" align="left"><input class="hig25" type="text" maxlength="50"
		placeholder="<输入>"  id="uprovname" name="uprovname" /></td>
	<td width="130" align="right" class="header">联系人</td>
	<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
		placeholder="<输入>"  id="ulinkman" name="ulinkman" /></td>
</tr>
<tr>
	<td align="right" class="header">联系电话</td>
	<td align="left"><input type="text" placeholder="<输入>"  class="hig25"
		id="utel" name="utel" maxlength="32" /></td>
	<td align="right" class="header">移动电话</td>
	<td align="left"><input class="hig25" type="text"
		placeholder="<输入>"  id="umobile" name="umobile" maxlength="11" /></td>
</tr>
<tr align="right">
	<td align="right" class="header">地址</td>
	<td align="left"><input class="hig25" type="text"
		placeholder="<输入>"  maxlength="100" id="uaddress"
		name="uaddress" /></td>
	<td align="right" class="header">邮编</td>
	<td align="left"><input class="hig25" type="text" maxlength="6"
		placeholder="<输入>"  id="upostcode" name="upostcode" /></td>
</tr>
<tr align="right">
	<td align="right" class="header">开户银行</td>
	<td align="left"><input class="hig25" type="text" maxlength="30"
		placeholder="<输入>"  id="ubankname" name="ubankname" /></td>
	<td align="right" class="header">银行账号</td>
	<td align="left"><input type="text" placeholder="<输入>"  maxlength="19" class="hig25"
		id="uaccountno" name="uccountno" /></td>
</tr>
<tr align="right">
	<td align="right" class="header">税号</td>
	<td align="left"><input class="hig25" type="text" maxlength="15"
		placeholder="<输入>"  id="utaxno" name="utaxno" /></td>
	<td align="right" class="header skyrequied">折扣</td>
	<td align="left"><input class="hig25" type="text" maxlength="4" value="1.00" onchange="diskz(this.value,'update')"
		placeholder="<输入>"  id="udiscount" name="udiscount" /></td>
</tr>
<tr align="right">
	<td align="right" class="header skyrequied">价格方式</td>
	<td align="left"><select id="upricetype" name="upricetype"
		style="width: 152px; height: 27px">
			<option value="0">进价</option>
			<option value="1">零价折扣</option>
	</select></td>
	<td align="right" class="header">备注</td>
	<td align="left"><input class="hig25" type="text" maxlength="30"
		placeholder="<输入>"  id="uremark" name="uremark" title="updatesup()"/></td>
</tr>
	</table>
	</div>
	<div class="dialog-footer textcenter">
		<label class="updateshow"><input type="checkbox" name="unoused" id="unoused">禁用</label>
		<label id="showqxkz"><input type="checkbox" id="ualluser" >授权所有职员</label>
		<input type="button" id="savebtn" class="btn1"  name="updateprov" value="保存" onclick="saveprov()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
	</div>
	</div>
	<!-- 职员授权-->
<div id="epsqd" title="授权职员" style="width: 400px;text-align:center; height: 500px" class="easyui-dialog" closed=true>			
	<table id="epsqt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="epsqpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<script type="text/javascript" charset="UTF-8">
//权限设置
setqxpublic();
var pgid= "pg1008";
var levelid = Number(getValuebyName("GLEVELID"));
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
function toggle() {
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		searchb = false;
		$('#searchbtn').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('#searchbtn').show();
	}
	$('.searchbar').slideToggle('fast');
	$('#sprovname').focus();

}
//清空搜索条件
function resetsearch() {
	$('#st3').prop('checked', true);
	var idstr = "#sprovname,#slinkman,#smobile,#sremark";
	$(idstr).val('');
}
//添加框
function addprovd() {
	$("#udtitle").html("添加供应商");
	$("#savebtn").val("保存并继续");
	$('#ud').dialog('open');
	$('#uprovname,#uprovid,#uindex').val("");
	$(".updateshow").hide();
	$("#ulinkman").val('');
	$("#uremark").val('');
	$("#ubankname").val('');
	$("#uaddress").val('');
	$("#umobile").val('');
	$("#utel").val('');
	$("#udiscount").val('1.00');
	$("#upostcode").val('');
	$("#utaxno").val('');
	$("#uaccountno").val('');
	$("#upricetype").val('0');
	$("#ualluser").prop("checked",true);
	$("#uprovname").focus();
}
//编辑框
function updateprovd() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var noused = row.NOUSED;
		$("#udtitle").html("编辑供应商");
		$("#savebtn").val("保存");
		$(".updateshow").show();
		$('#ud').dialog('open');
		$('#uprovname').focus();
		$('#uprovname').val(row.PROVNAME);
		$('#uprovid').val(row.PROVID);
		$('#umobile').val(row.MOBILE);
		$('#uaddress').val(row.ADDRESS);
		$('#ulinkman').val(row.LINKMAN);
		$('#upostcode').val(row.POSTCODE);
		$('#utel').val(row.TEL);
		$('#ubankname').val(row.BANKNAME);
		$('#udiscount').val(Number(row.DISCOUNT).toFixed(2));
		$('#uremark').val(row.REMARK);
		$('#uaccountno').val(row.ACCOUNTNO);
		$('#utaxno').val(row.TAXNO);
		$('#upricetype').val(row.PRICETYPE);
		if (noused != 0) {
			$('#unoused').prop('checked', true);
		} else {
			$('#unoused').removeProp('checked');
		}
		$("#ualluser").removeProp("checked");
	}
}
$(function() {
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getprovlist(p);
		}
	});
	//加载数据
	$('#gg').datagrid({
		idField: 'PROVID',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
// 		sortName: "PROVNAME",
// 		sortOrder: "asc",
		columns: [[{
			field: 'PROVID',
			title: '供应商ID',
			fieldtype: "G",
			width: 200,
			expable: true,
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
			field: 'PROVNAME',
			title: '供应商',
			width: 200,
// 			sortable: true,
			fixed: true,
			halign: "center",
			align: 'left'
		},{
			field : 'LINKMAN',
			title : '联系人',
			width : 100,
			fixed: true,
			align : 'center',
			halign : 'center'
		},{
			field : 'MOBILE',
			title : '移动电话',
			width : 100,
			fixed: true,
			align : 'center',
			halign : 'center'
		},{
			field : 'PROVID',
			title : 'ID',
			width : 100,
			hidden : true
		},{
			field : 'TEL',
			title : '联系电话',
			width : 100,
			fixed: true,
			align : 'center',
			halign : 'center'
		},{
			field : 'DISCOUNT',
			title : '折扣',
			width : 100,
			fieldtype: "G",
			fixed: true,
			align : 'center',
			halign : 'center',
			formatter: currfm
		},{
			field : 'PRICETYPE',
			title : '价格方式',
			align : 'center',
			halign : 'center',
			width : 100,
			fieldtype: "G",
			fixed: true,
			formatter : function(value, row,index) {
				var pt = value=='0'?'进价':'零价折扣';
				return pt;
				}
		},{
			field : 'ADDRESS',
			title : '地址',
			width : 200,
			fixed: true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'POSTCODE',
			title : '邮编',
			width : 100,
			fixed: true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'BANKNAME',
			title : '开户银行',
			width : 100,
			fixed: true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'ACCOUNTNO',
			title : '账号',
			width : 100,
			fixed: true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'TAXNO',
			title : '税号',
			width : 100,
			fixed: true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'REMARK',
			title : '备注',
			width : 100,
			hidden : true
		},{
			field: 'NOUSED',
			title: '禁用',
			width: 80,
			fixed: true,
// 			sortable: true,
			fieldtype: "G",
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var nouse = Number(value);
				var checked = "";
				if (nouse == 1) checked = "checked";
				if (isNaN(nouse)) return "";
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledprov(' + index + ')" ' + checked + '>';
			}
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var html = '<input type="button" class="m-btn" value="删除" onclick="delprov(' + index + ')">';
				if(qxkz==1)
					html += '<input type="button" class="m-btn" value="授权职员" onclick="setepd(' + row.PROVID + ')">';
				return html;
			}
		}]],
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updateprovd();
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updateprovd()");
	getprovdata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendprovide",
		xlsmobanname: "provide",
		channel: "provide"
	});
	if(qxkz==0||(levelid != 0 && levelid != 5) ) $("#showqxkz").hide();
	setept();
});

var currentdata = {};
var getprovlist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "providelist";
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
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
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getprovdata() {
	var value = $('#qsprovvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getprovlist(1);
}
//搜索供应商
function searchprov() {
	var provname = $('#sprovname').val();
	var linkman = $('#slinkman').val();
	var mobile = $('#smobile').val();
	var remark = $('#sremark').val();
	var noused = 0;
	if ($('#st1').prop('checked')) noused = "0";
	else if ($('#st2').prop('checked')) noused = "1";
	else noused = "2";
	alertmsg(6);
	currentdata = {
		provname: provname,
		linkman: linkman,
		mobile: mobile,
		remark: remark,
		noused: noused
	};
	getprovlist(1);
}
//添加供应商
function saveprov() {
	var index = $("#uindex").val();
	var provid = Number($("#uprovid").val());
	var provname = $('#uprovname').val();
	if (provname == "") {
		alerttext("供应商名不能为空");
		return;
	}
	var linkman = $("#ulinkman").val();
	var remark = $("#uremark").val();
	var bankname = $("#ubankname").val();
	var address = $("#uaddress").val();
	var mobile = $("#umobile").val();
	var tel = $("#utel").val();
	var discount = $("#udiscount").val();
	var postcode = $("#upostcode").val();
	var taxno = $("#utaxno").val();
	var accountno = $("#uaccountno").val();
	var pricetype = $("#upricetype").val();
	var erpser = "addprovide";
	var noused = 0;
	if (provid > 0){
		erpser = "updateprovidebyid";
		noused = $("#unoused").prop("checked") ? 1 : 0;
	}
	var alluser = 0;
	if($("#ualluser").prop("checked")&&(levelid == 0 || levelid == 5))
		alluser=1;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			provid : provid,
			provname : provname,
			linkman : linkman,
			remark : remark,
			bankname : bankname,
			address : address,
			mobile : mobile,
			tel : tel,
			discount : discount,
			postcode : postcode,
			taxno : taxno,
			accountno : accountno,
			pricetype : pricetype,
			noused : noused,
			alluser: alluser
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try {
				if (valideData(data)) {
					var $dg = $("#gg");
					if (provid == 0) {
						provid = data.msg;
						$dg.datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								PROVID : provid,
								PROVNAME : provname,
								LINKMAN : linkman,
								REMARK : remark,
								BANKNAME : bankname,
								ADDRESS : address,
								MOBILE : mobile,
								TEL : tel,
								DISCOUNT : discount,
								POSTCODE : postcode,
								TAXNO : taxno,
								ACCOUNTNO : accountno,
								PRICETYPE : pricetype,
								NOUSED : noused
							}
						}).datagrid('refresh');
						var total = Number($("#pp_total").html());
						if (!isNaN(total)) $("#pp_total").html(total + 1);
						$("#uprovname").val('');
						$("#ulinkman").val('');
						$("#uremark").val('');
						$("#ubankname").val('');
						$("#uaddress").val('');
						$("#umobile").val('');
						$("#utel").val('');
						$("#udiscount").val('1.00');
						$("#upostcode").val('');
						$("#utaxno").val('');
						$("#uaccountno").val('');
						$("#upricetype").val('0');
						$("#uprovname").focus();
					} else {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								PROVID : provid,
								PROVNAME : provname,
								LINKMAN : linkman,
								REMARK : remark,
								BANKNAME : bankname,
								ADDRESS : address,
								MOBILE : mobile,
								TEL : tel,
								DISCOUNT : discount,
								POSTCODE : postcode,
								TAXNO : taxno,
								ACCOUNTNO : accountno,
								PRICETYPE : pricetype,
								NOUSED : noused
							}
						}).datagrid('refresh');
						$("#ud").dialog("close");
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function disabledprov(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var provid = row.PROVID;
		var $check = $('#disabledcheckin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateprovidebyid",
				provid: provid,
				noused: noused
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						$dg.datagrid('updateRow', {
							index: index,
							row: {
								NOUSED: noused
							}
						});
					}
					$dg.datagrid('refresh');
				} catch(e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
//删除
function delprov(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var provid = row.PROVID;
		$.messager.confirm(dialog_title, '您确认要删除供应商' + row.PROVNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delprovidebyid",
						provid: provid
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
function getprovep(page) {
	var provid = $('#uprovid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "provemployelist",
			provid: provid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var total = data.total;
				$('#epsqpp').setPage({
					pageCount: Math.ceil(total / pagecount),
					current: Number(page)
				});
				check = false;
				$('#epsqt').datagrid('loadData', data);
				check = true;

			}
		}
	});
}
function setepd(provid) {
	$('#uprovid').val(provid);
	$('#epsqd').dialog('open');
	getprovep('1');
}
function setprovep(epid, value,index) {
	alertmsg(2);
	var row = $("#gg").datagrid("getSelected");
	var provid = $('#uprovid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeemployeprov",
			epid: epid,
			provid: provid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$("#epsqt").datagrid("updateRow",{
					index: index,
					row:{
						SELBJ: value
					}
				});
			}
			$("#epsqt").datagrid("refreshRow",index);
		}
	});
}
function setallep(value) {
	var provid = $('#uprovid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeallprovemploye",
			provid: provid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$("#epsqpp").refreshPage();
			}
		}
	});
}
var check = false;
function setept() {
	$('#epsqpp').createPage({
		current: 1,
		backFn: function(p) {
			getprovep(p);
		}
	});
	$('#epsqt').datagrid({
		idField: 'EPID',
		height: 410,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return index + 1;
			}
		},
		{
			field: 'EPNAME',
			title: '职员',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CHECK',
			checkbox: true,
			fixed: true,
			width: 50
		}]],
		selectOnCheck: false,
		checkOnSelect: false,
		pageSize: 10,
		onCheck: function(index, row) {
			if (check) {
				setprovep(row.EPID, '1',index);
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setprovep(row.EPID, '0',index);
			}
		},
		onCheckAll: function(index, row) {
			if (check) {
				setallep('1');
			}
		},
		onUncheckAll: function(index, row) {
			if (check) {
				setallep('0');
			}
		},
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (item.SELBJ == '1') {
						$('#epsqt').datagrid('checkRow', index);
					} else $('#epsqt').datagrid('uncheckRow', index);
				});
				check = true;
				if (levelid == '0' ||  levelid == '5') {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').removeAttr('disabled');
				} else {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').prop('disabled', true);
				}
			}
		}
	});
}
</script>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>