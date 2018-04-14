<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>条码管理</title>
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
</head>
<body class="easyui-layout layout panel-noscroll" style="margin: auto;">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
		<input class="btn1" type="button" value="添加" onclick="addbard()">
		<input class="btn1" type="button" value="自动产生" onclick="addbarid()">
		<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatebard()">
		<input type="button" class="btn1" id="delbatchbtn" value="批量删除(0)" onclick="delselectwarebarcode()">
		<input type="button" class="btn1 hide" value="清空条码" onclick="$('#rbd').dialog('open');$('#remb').focus();">
		<input type="text" placeholder="搜索商品名称、货号、商品类型<回车搜索>" data-end="yes" class="ipt1" id="qswarebarvalue" maxlength="32" data-enter="getwarebarcodedata()">
		<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn">
		<input id="search" type="button" class="btn2" type="button" value="搜索" onclick="searchwarebarcodedata();toggle();"> 
		<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
		<input type="button" class="btn3" value="导入" onclick="openimportd()"> 
		<span class="sepreate"></span> 
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
	</div>
</div>

<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchwarebarcodedata();toggle();">
<form action="" id="searchform">
<div class="search-box">
<div class="s-box"><div class="title">条码</div>
<div class="text"><input id="sbarcode" name="sbarcode" type="text" placeholder="<输入>"
				class="hig25 wid160" maxlength="32">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input type="text" id="swareno" class="edithelpinput wareno_help" data-value="#swareid" maxlength="20" placeholder="<选择或输入>"
				name="swareno" /><span onclick="selectware('search')"></span>
				<input type="hidden" id="swareid" name="swareid">
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input class="edithelpinput  wid145 brand_help" data-value="#sbrandid" placeholder="<选择或输入>"  maxlength="20"  
				 id="sbrandname" name="sbrandname"type="text" ><span onclick="selectbrand('search1')"></span>
				 <input type="hidden" id="sbrandid" name="sbrandid">
</div>
</div>
<div class="s-box"><div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname" data-options="required:true"><span onclick="selectwaretype('search')"></span>
					 <input type="hidden" id="stypeid" value="">
</div>
</div>
<div class="s-box"><div class="title">季节</div>
<div class="text"><input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select name="sprodyear" id="sprodyear"  class="sele1">
                     <option value="">---请选择---</option>
			         </select>
</div>
</div>
<div class="s-box"><div class="title">尺码组</div>
<div class="text"><select name="sgroupno" class="sele1" id="sgroupno" style="width:160px;">
				<option value="">---请选择---</option>
				</select>
</div>
</div>
</div>
	</form>
</div>
</div>
<table id="gg" style="overflow: hidden;height:900px;width:100%"></table>
<div
	class="page-bar">
	<div
		class="page-total">
		共有<span id="pp_total">0</span>条记录
	</div>
	<div id="pp" class="tcdPageCode"
		style="float: right; margin-right: 20px; padding-top: 3px"></div>
</div>
<!-- 增加---------------------------->
<div id="ad" title="增加条码"
	style="width: 425px; height: 400px; text-align: center;"
	class="easyui-dialog" closed=true>
	<div style="width: 100%; height: 300px; overflow: auto;">
	<form action="" id="addfor">
		<table cellspacing="10" class="table1">
			<tr align="right">
				<td  class="header skyrequied">条码</td>
				<td><input class="hig25 wid160" type="text" id="abarcode"
					name="abarcode"  maxlength="32"  placeholder="<输入>"/>
					<input type="hidden" id="acolorid" name="acolorid" /> 
	                <input type="hidden" id="acolorno" name="acolorno" />
					</td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">货号</td>
				<td><input class="edithelpinput wid145 wareno_help" data-value="#awareid" type="text" id="awareno" name="awareno" placeholder="<选择或输入>"
					  /><span onclick="selectware('addbarcode')"></span>
					  <input type="hidden" id="awareid" name="awareid" /></td>
			</tr>
			<tr align="right">
				<td  class="header">商品名称</td>
				<td><input class="hig25 wid160" type="text" id="awarename"  placeholder="<商品名称>"  maxlength="20" readonly
					name="awarename"/></td>
			</tr>
			<tr align="right">
				<td  class="header">品牌</td>
				<td><input class="hig25 wid160" type="text" id="abrandname"  maxlength="20"  placeholder="<品牌>" readonly
					name="abrandname"  /><input type="hidden" id="abrandid" name="abrandid" /> </td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">颜色</td>
				<td><input class="edithelpinput wid145" type="text" id="acolorname" placeholder="<选择或输入>"
					name="acolorname" readonly/><span onclick="selectwarecolor($('#awareid').val(),'add')" ></span></td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">尺码</td>
				<td><input class="edithelpinput wid145" type="text" id="asizename" placeholder="<选择或输入>" readonly
					name="asizename" title="addWarebarCode()"/><span onclick="selectsize($('#awareid').val(),'add')"></span>
					<input type="hidden" id="asizeid" name="asizeid" /><input type="hidden" id="asizeno" name="asizeno" />
					</td>
			</tr>
		</table></form>
	</div>
	<div class="dialog-footer" style="text-align: center;">
	<label class="fl-ml30">
	<input type="checkbox" name="aprtok" id="aprtok" checked>允许打印</label>
		<input class="btn1" type="button" id="add" style="width: 150px;"
			type="button" value="保存并继续添加" onclick="addWarebarCode()">
	</div>
</div>
<!-- 自动产生条码------------------- -->
<div id="ai" title="自动产生条码"
	style="width: 425px; height: 400px; text-align: center;"class="easyui-dialog" closed=true>
	<div style="width: 100%; height: 305px; overflow: auto;">
	<form action="" id="autoform">
		<table cellspacing="10" class="table1">
			<tr align="right">
				<tr align="right">
				<td  class="header">货号</td>
				<td><input class="edithelpinput wid145 wareno_help" data-value="#iwareid" type="text" id="iwareno" name="iwareno" placeholder="<选择或输入>"
					  /><span onclick="selectware('autobarcode')"></span><input type="hidden" id="iwareid"></td>
			</tr>
			<tr align="right">
				<td  class="header">品牌</td>
				<td><input class="edithelpinput wid145 brand_help" data-value="#ibrandid" type="text" id="ibrandname" placeholder="<选择或输入>" name="ibrandname" />
					 <span onclick="selectbrand('autobarcode')"></span> <input type="hidden" id="ibrandid" name="ibrandid"></td>
			</tr>
			<tr align="right">
				<td  class="header">类型</td>
				<td><select name="itypeid" class="sele1" id="itypeid">
					<option value="">---请选择---</option>
					</select></td>
			</tr>
			<tr align="right">
				<td  class="header">季节</td>
				<td>
<input  type="text" class="edithelpinput season_help" name="iseasonname" id="iseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</td>
			</tr>
			<tr align="right">
				<td  class="header">生产年份</td>
				<td><select name="iprodyear" id="iprodyear"  class="sele1">
                         <option value="">---请选择---</option>
				         </select>
                        </td>
			</tr>
			<tr align="right">
				<td  class="header">尺码组</td>
				<td><select name="igroupno" class="sele1" id="igroupno">
					<option value="">---请选择---</option>
					</select></td>
			</tr>
			<tr align="center">
			
				<td colspan="2"><label><input type="checkbox" id="inewbj">仅产生无条码商品</label></td>
			</tr>
		</table>
		</form>
	</div>
	<div class="dialog-footer" style="text-align: center;">
	<input type="button" class="btn1" id="autoadd" value="确定" onclick="autoAddbarCode();">
	<input type="button" class="btn2" name="close" value="取消" onclick="$('#ai').dialog()"> 
	</div>
</div>
<!-- 修改-------------------------- -->
<div id="ud" title="编辑条码"
	style="width: 425px; height: 400px; text-align: center;"
	class="easyui-dialog" closed=true>
	<div style="width: 100%; height: 300px; overflow: auto;">
		<table width="300" border="0" cellspacing="10" class="table1">
			<tr align="right">
				<td width="70">条码</td>
				<td width="180" align="left"><input class="wid160 hig25" type="text"
					id="ubarcode" name="ubarcode" /><input type="hidden" id="uindex" name="uindex">
					<input type="hidden" id="uid" name="uid" /> <input type="hidden" id="ucolorno" name="ucolorno"/>
					<input type="hidden" id="usizeno" name="usizeno" />
					</td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">货号</td>
				<td align="left"><input type="text" id="uwareno" name="uwareno" class="edithelpinput wid145 wareno_help" data-value="#uwareid" >
					<span onclick="selectware('updatebarcode')"></span>
					<input type="hidden" id="uwareid" name="uwareid" /></td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">商品名称</td>
				<td align="left"><input type="text" id="uwarename" name="uwarename" readonly
					class="wid160 hig25" /> </td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">品牌</td>
				<td align="left"><input type="text" id="ubrandname" name="ubrandname"  readonly
					class="wid160 hig25" />  <input type="hidden" id="ubrandid" name="ubrandid"/></td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">颜色</td>
				<td align="left"><input type="text" id="ucolorname" name="ucolorname" class="edithelpinput wid145" readonly/>
					<span onclick="selectwarecolor($('#uwareid').val(),'update')"></span> 
					<input type="hidden" id="ucolorid" name="ucolorid" /></td>
			</tr>
			<tr align="right">
				<td  class="header skyrequied">尺码</td>
				<td align="left"><input type="text" id="usizename" name="usizename" class="edithelpinput wid145" title="updateWarebarCode()" readonly/>
					<span onclick="selectsize($('#uwareid').val(),'update')"></span> <input type="hidden" id="usizeid" name="usizeid" /></td>
			</tr>
		</table>
	</div>
	<div class="dialog-footer" style="text-align: center;">
	<label class="fl-ml30">
	<input type="checkbox" name="uprtok" id="uprtok" checked>允许打印</label>
	 <input type="button" class="btn1" id="update" value="保存" onclick="updateWarebarCode()">
	<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')">
	</div>
</div>
<script type="text/javascript" charset="UTF-8">

var pgid = "pg1006";
setqxpublic();
var searchb = false;
var opser;
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
	$('#sbarcode').focus();

}
function resetsearch() {
	$('#searchform :input').val("");
}
//批量删除
var delb = false;
function delbatch() {
	if (delb) {
		$('#gg').datagrid('hideColumn', 'CHECK').datagrid('resize');
		$('#delbatch').val('批量删除');
		delb = false;
	} else {
		$('#gg').datagrid('showColumn', 'CHECK').datagrid('resize');
		$('#delbatch').val('取消');
		delb = true;
	}
}
//添加框
function addbard() {
	$('#ad').dialog({
		modal: true
	});
	$('#ad').dialog('open');
	$('#barcode').focus();
}
//自动增加框
function addbarid() {
	$('#ai').dialog({
		modal: true
	});
	$('#ai').dialog('open');
	$('#iwareno').focus();
}
//编辑框
function updatebard() {
	var arrs = $('#gg').datagrid('getSelections');
	if (arrs.length != 1) {
		$.messager.show({
			title: '温馨提示',
			msg: '请选择一行数据，进行编辑',
			timeout: 1000,
			style: {
				right: '',
				top: document.body.scrollTop + document.documentElement.scrollTop,
				bottom: ''
			}
		});
	} else {
		var arrs1 = $('#gg').datagrid('getSelected');
		$('#ud').dialog({
			modal: true

		});
		$('#ud').dialog('open');
		$('#ubarcode').focus();
		if (arrs1) {
			$('#ubarcode').val(arrs1.BARCODE);
			$('#uwareno').val(arrs1.WARENO);
			$('#uwarename').val(arrs1.WARENAME);
			$('#ubrandname').val(arrs1.BRANDNAME);
			$('#ucolorname').val(arrs1.COLORNAME);
			$('#usizename').val(arrs1.SIZENAME);
			$('#uwareid').val(arrs1.WAREID);
			$('#uid').val(arrs1.ID);
			$('#usizeid').val(arrs1.SIZEID);
			$('#ucolorid').val(arrs1.COLORID);
			$('#ubrandid').val(arrs1.BRANDID);
			$('#ucolorno').val(arrs1.COLORNO);
			$('#usizeno').val(arrs1.SIZENO);
			if(arrs1.PRTOK=="1") $("#uprtok").prop("checked",true);
			else $("#uprtok").removeProp("checked");
		} else {}
	}
}
var currentdata = {};
function warebarcodelist(page) {
	currentdata["erpser"] = "warebarcodelist";
	currentdata["rows"] = pagecount;
	currentdata["sort"] = "wareno";
	currentdata["order"] = "asc";
	currentdata["page"] = page;
	currentdata["fieldlist"] = "a.statetag,a.id,a.barcode,a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,d.sizeno,d.groupno,b.brandid,e.brandname,a.prtok";
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
				$("#pp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#gg').datagrid('loadData', data);
				$("#pp_total").html(data.total);
			}
		}
	});
}
//搜索
function getwarebarcodedata(page) {
	var findbox = $('#qswarebarvalue').val();
	currentdata = {
		findbox: findbox
	};
	warebarcodelist(1);
}
function searchwarebarcodedata() {
	var barcode = $('#sbarcode').val();
	var wareid = Number($('#swareid').val());
	var brandid = Number($('#sbrandid').val());
	var typeid = Number($('#stypeid').val());
	var seasonname = $('#sseasonname').val();
	var prodyear = $('#sprodyear').val();
	brandid = brandid==""?"-1":brandid;
	typeid = typeid==""?"-1":typeid;
	var groupno = $('#sgroupno').val();
	currentdata = {
		wareid: wareid,
		barcode: barcode,
		brandid: brandid,
		typeid: typeid,
		seasonname: seasonname,
		prodyear: prodyear,
		sizegroupno: groupno
	};
	warebarcodelist(1);
}
function setyear() {
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var start = year - 5;
	var end = year + 1;
	for (var i = start; i <= end; i++) {
		$("#sprodyear").append("<option value=" + i + ">" + i + "</option'>");
		$("#iprodyear").append("<option value=" + i + ">" + i + "</option'>");
	}
}
//获取分组
function getgroupno() {
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "sizegrouplist"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					var options = "";
					for (var i in rows) {
						options = options + '<option value="' + rows[i].GROUPNO + '">' + rows[i].GROUPNO + '(' + rows[i].SIZELIST + ')</option>';
					}
					$('#igroupno').append(options);
					$('#sgroupno').append(options);
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
$(function() {
	getgroupno();
	setyear();
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			warebarcodelist(p);
		}
	});
	$('#gg').datagrid({
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		checkOnSelect: false,
		selectOnCheck: false,
		cache: false,
		columns: [[{
			field: 'ID',
			title: '条码ID',
			width: 100,
			hidden: true
		},
		{
			field: 'CHECK',
			checkbox: true
		},
		{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var val = Math.ceil(Number(value) / pagecount) - 1;
				return val * pagecount + Number(index) + 1;
			}
		},
		{
			field: 'BARCODE',
			title: '条码',
			halign: 'center',
			width: 120,
			fixed: true
		},
		{
			field: 'WARENAME',
			title: '商品',
			halign: 'center',
			width: 100,
			fixed: true
		},
		{
			field: 'WARENO',
			title: '货号',
			halign: 'center',
			width: 100,
			fixed: true
		},
		{
			field: 'COLORNAME',
			title: '颜色',
			halign: 'center',
			align: 'center',
			width: 80,
			fixed: true
		},{
			field: 'GROUPNO',
			title: '尺码组',
			halign: 'center',
			align: 'center',
			width: 80,
			fixed: true
		},{
			field: 'SIZENAME',
			title: '尺码',
			halign: 'center',
			align: 'center',
			width: 80,
			fixed: true
		},{
			field: 'PRTOK',
			title: '允许打印',
			expable: true,
			width: 60,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var prtok = Number(row.PRTOK);
				var checked = "";
				if (prtok == 1) checked = "checked";
				if (isNaN(prtok)) return "";
				return '<input type="checkbox" id="prtokcheckin_' + index + '" class="m-btn" onchange="prtokbarcode(' + index + ')" ' + checked + '>';
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
				return '<input type="button" class="m-btn" value="删除" onclick="delwarebarcode(' + index + ')">';
			}
		}]],
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatebard();
		},
		selkey: {
			name: "BARCODE",
			value: "ID"
		},
		selobject: {
			name: "",
			value: "^"
		},
		helpcheck: false,
		onLoadSuccess: function(data) {
			var rows = data.rows;
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			$this.datagrid("options").helpcheck = false;
			if (rows.length > 0 && selobject.value.length > 0) {
				var selkey = $this.datagrid("options").selkey;
				var namekey = selkey.name;
				var valuekey = selkey.value;
				for (var i in rows) {
					var row = rows[i];
					var _name = row[namekey];
					var _value = Number(row[valuekey]);
					var name = selobject.name;
					var value = selobject.value;
					if (value.indexOf(_value) >= 0) {
						$this.datagrid('checkRow', i);
					} else $this.datagrid('uncheckRow', i);
				}
			}
			$this.datagrid("options").helpcheck = true;
		},
		onCheck: function(index, row) {
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			var selkey = $this.datagrid("options").selkey;
			var helpcheck = $this.datagrid("options").helpcheck;
			if (helpcheck) {
				var $this = $(this);
				var namekey = selkey.name;
				var valuekey = selkey.value;
				var _name = row[namekey];
				var _value = Number(row[valuekey]);
				var name = selobject.name;
				var value = selobject.value;
				if (value.indexOf(_value) == -1) {
					name += _name + ",";
					value += _value + "^";
					selobject.name = name;
					selobject.value = value;
				}
				if (value.split("^").length > 0) $('#delbatchbtn').val("批量删除(" + (value.split("^").length - 2) + ")");
			}
		},
		onCheckAll: function(rows) {
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			var selkey = $this.datagrid("options").selkey;
			var helpcheck = $this.datagrid("options").helpcheck;
			if (helpcheck) {
				for (var i in rows) {
					var row = rows[i];
					var namekey = selkey.name;
					var valuekey = selkey.value;
					var _name = row[namekey];
					var _value = Number(row[valuekey]);
					var name = selobject.name;
					var value = selobject.value;
					if (value.indexOf(_value) == -1) {
						name += _name + ",";
						value += _value + "^";
						selobject.name = name;
						selobject.value = value;
					}
				}
				if (selobject.value.split("^").length > 0) $('#delbatchbtn').val("批量删除(" + (selobject.value.split("^").length - 2) + ")");
			}
		},
		onUncheck: function(index, row) {
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			var selkey = $this.datagrid("options").selkey;
			var helpcheck = $this.datagrid("options").helpcheck;
			if (helpcheck) {
				var namekey = selkey.name;
				var valuekey = selkey.value;
				var _name = row[namekey];
				var _value = Number(row[valuekey]);
				var name = selobject.name;
				var value = selobject.value;
				if (value.indexOf(_value) >= 0) {
					name = name.replace(_name + ",", "");
					value = value.replace(_value + "^", "");
					selobject.name = name;
					selobject.value = value;
				}
				if (value.split("^").length > 0) $('#delbatchbtn').val("批量删除(" + (value.split("^").length - 2) + ")");
			}
		},
		onUncheckAll: function(rows) {
			var $this = $(this);
			var selobject = $this.datagrid("options").selobject;
			var selkey = $this.datagrid("options").selkey;
			var helpcheck = $this.datagrid("options").helpcheck;
			if (helpcheck) {
				for (var i in rows) {
					var row = rows[i];
					var namekey = selkey.name;
					var valuekey = selkey.value;
					var _name = row[namekey];
					var _value = Number(row[valuekey]);
					var name = selobject.name;
					var value = selobject.value;
					if (value.indexOf(_value) >= 0) {
						name = name.replace(_name + ",", "");
						value = value.replace(_value + "^", "");
						selobject.name = name;
						selobject.value = value;
					}
				}
				if (selobject.value.split("^").length > 0) $('#delbatchbtn').val("批量删除(" + (selobject.value.split("^").length - 2) + ")");
			}
		},
		pageSize: 20,
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatebard()");
	getwarebarcodedata();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendwarebarcode",
		xlsmobanname: "warebarcode",
		channel: "warebarcode"
	});
});
//增加
function addWarebarCode() {
	var wareno = $('#awareno').val();
	if (wareno == "") {
		alert("货号不能为空！");
		return;
	}
	var barcode = $('#abarcode').val();
	var brandid = $('#abrandid').val();
	var sizeid = $('#asizeid').val();
	var colorid = $('#acolorid').val();
	var wareid = $('#awareid').val();
	var prtok = 0;
	if($("#uprtok").prop("checked")) prtok =1;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addwarebarcode",
			barcode: barcode,
			brandid: brandid,
			wareno: wareno,
			sizeid: sizeid,
			colorid: colorid,
			prtok: prtok,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var id = data.msg;
					$('#gg').datagrid('insertRow', {
						index: 0,
						row: {
							ROWNUMBER: 1,
							ID: id,
							BARCODE: barcode,
							BRANDID: brandid,
							BRANDNAME: $('#abrandname').val(),
							WARENO: wareno,
							SIZEID: sizeid,
							SIZENAME: $('#asizename').val(),
							COLORID: colorid,
							COLORNAME: $('#acolorname').val(),
							WAREID: wareid,
							PRTOK: prtok,
							WARENAME: $('#awarename').val()
						}
					});
					$('#gg').datagrid("refresh");
					$('#addfor').get(0).reset();
					$('#wareno').focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function autoAddbarCode() {
	var wareid = $('#iwareid').val();
	var brandid = $('#ibrandid').val();
	var sizegroupno = $('#igroupno').val();
	var typeid = $('#itypeid').val();
	var prodyear = $('#iprodyear').val();
	var seasonname = $('#iseasonname').val();
	var fs = 0;
	if ($('#inewbj').prop("checked")) fs = 1;
	else fs = 0;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "autowarebarcode",
			wareid: wareid,
			brandid: brandid,
			sizegroupno: sizegroupno,
			typeid: typeid,
			seasonname: seasonname,
			prodyear: prodyear,
			fs: fs
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#autoform')[0].reset();
					$('#pp').refreshPage();
					$('#iwareno').focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}

		}
	});
}
//修改
function updateWarebarCode() {
	var wareno = $('#uwareno').val();
	if (wareno == "") {
		alert("货号不能为空！");
		return;
	}
	var barcode = $('#ubarcode').val();
	var brandid = $('#ubrandid').val();
	var sizeid = $('#usizeid').val();
	var colorid = $('#ucolorid').val();
	var wareid = $('#uwareid').val();
	var id = $('#uid').val();
	var prtok = 0;
	if($("#uprtok").prop("checked")) prtok =1;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewarebarcodebyid",
			barcode: barcode,
			sizeid: sizeid,
			colorid: colorid,
			wareid: wareid,
			prtok: prtok,
			id: id
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var index = $('#uindex').val();
					$('#gg').datagrid('updateRow', {
						index: index,
						row: {
							ID: id,
							BRANDID: brandid,
							BARCODE: barcode,
							BRANDNAME: $('#ubrandname').val(),
							WARENO: wareno,
							SIZEID: sizeid,
							SIZENAME: $('#usizename').val(),
							colorid: colorid,
							COLORNAME: $('#ucolorname').val(),
							WAREID: wareid,
							PRTOK: prtok,
							WARENAME: $('#uwarename').val()
						}
					});
					$('#gg').datagrid("refresh");
					$('#ud').dialog('close');
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}

		}
	});
}
//清空所有条码
function removeWarebarCode() {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "clearallwarebarcode"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					getwarebarcodedata();
					$('#rbd').dialog('close');
					$('#del').removeAttr("disabled");
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//删除
function delwarebarcode(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var brandid = row.BRANDID;
		$.messager.confirm(dialog_title, '您确认要删除条码' + row.BARCODE + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwarebarcodebyid",
						id: row.ID
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
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

//导入
function importxls() {
	$('#uploadwarebarxlsd').dialog('open');
}
function delselectwarebarcode() {
	var selobject = $('#gg').datagrid("options").selobject;
	if (selobject.name == "") {
		alerttext("删除记录为空，请选择");
	} else {
		var value = selobject.value;
		var valuearr = value.split("^");
		var reclist = [];
		for (var i = 0; i < valuearr.length; i++) {
			var recid = Number(valuearr[i]);
			if (recid > 0) {
				reclist.push({
					recid: recid
				});
			}
		}
		if (reclist.length > 0) {
			$.messager.confirm(dialog_title, '确认删除条码' + reclist.length + '条，删除后将无法恢复！',
			function(r) {
				if (r) {
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "delselectwarebarcode",
							reclist: JSON.stringify(reclist)
						},
						//这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									alerttext("操作成功！");
									$("#pp").refreshPage();
									$("#delbatchbtn").val("批量删除(0)");
									$('#gg').datagrid("options").selobject = {
										name: "",
										value: "^"
									};
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

}
function prtokbarcode(index) {
	var $dg = $('#gg');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	if (row) {
		var id = Number(row.ID);
		var $check = $('#prtokcheckin_' + index);
		var prtok = 0;
		if ($check.prop('checked')) prtok = 1;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updatewarebarcodebyid",
				prtok: prtok,
				id: id
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					$dg.datagrid('updateRow', {
						index: index,
						row: {
							PRTOK: prtok
						}
					});
					$dg.datagrid('refresh');
				}
			}
		});
	} else {
		$dg.datagrid('refresh');
		alerttext('行无效！');
	}
}
</script>
<!-- 商品（货号）帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 商品颜色帮助列表 -->
<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
<!-- 尺码帮助列表 -->
<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>