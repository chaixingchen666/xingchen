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
<title>职员档案</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
#houseqxtoolbar li.checked {
    background: #ff7900;
    color: #fff;
    border
}
#houseqxtoolbar li {
    float: left;
    padding: 0 5px;
    cursor: pointer;
}
#houseqxtoolbar li:not(.checked):hover {
    color: #00959a;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/datagrid-groupview.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<input type="hidden" id="nouse" value="0">
<!-- 表格工具栏 -->
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addempd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updateempd()">
	<input  type="text" placeholder="搜索职员名称或简称" class="ipt1" id="qsempvalue" data-end="yes" data-enter="getempdata()">
	<input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn">
	<input id="search" type="button" class="btn2" type="button" value="搜索" onclick="searchemp();toggle();"> 
	<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()"></div>
	<div class="fr">
	<input type="button" class="btn3"  value="导入" onclick="openimportd()">
	<span class="sepreate"></span>
	<input type="button" class="btn3"  value="导出" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
	</div>
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchemp();toggle();">
<input id="shouseid" type="hidden">
<div class="search-box">
	<div class="s-box">
	<div class="title">姓名</div>
	<div class="text"><input class="wid160 hig25" id="sepname" type="text" maxlength="20" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">用户账号</div>
	<div class="text"><input class="wid160 hig25" id="sepno" type="text" maxlength="12" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">移动电话</div>
	<div class="text"><input class="wid160 hig25" id="smobile" type="text" maxlength="11" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title" color="#ff7900">*&nbsp;店铺</div>
	<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" id="shousename" type="text" maxlength="20"><span onclick="selecthouse('search')" placeholder="<输入>"></span></div>
	</div>
	<div class="s-box" style="width:260px">
	<div class="title">允许登录</div>
	<div class="text" style="width:190px">
	<label>
	<input type="radio" name="spasskey" id="spass1" value="1">允许登录
	</label>
	<label>
	<input type="radio" name="spasskey" id="spass2" value="0">禁止登录
	</label>
	<label>
	<input type="radio" name="spasskey" id="spass3" value="2" checked="checked">所有
	</label>
	</div>
	</div>
	<div class="s-box">
	<div class="title">状态</div>
	<div class="text">
	<label>
	<input type="radio" name="snouse" id="st1" value="0" checked="checked">在用
	</label>
	<label>
	<input type="radio" name="snouse" id="st2" value="1">禁用
	</label>
	<label>
	<input type="radio" name="snouse" id="st3" value="2">所有
	</label>	
	</div>
	</div>
	</div>
</div>
</div>
	<table id="gg" style="overflow: hidden;height:900px"></table>
<div class="page-bar">
	<div class="page-total">
	共有<span id="pp_total">0</span>条记录
</div>
<div id="pp" class="tcdPageCode page-code"></div>
</div>
<!-- 添加职员 -->
<div id="ad" title="添加职员" style="width: 550px;text-align:center; height:460px"
		class="easyui-dialog" closed=true>
		<form id="addepform">
<table width="550" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="70" align="right" class="header skyrequied">姓名</td>
    <td width="160" align="left"><input class="easyui-validatebox wid160 hig25" type="text" name="aepname" maxlength="10"
				id="aepname" required="required" placeholder="<必须输入>" isValidEmpty="true"></td>
    <td width="120" align="right" class="header skyrequied">用户账号</td>
    <td width="160" align="left"><input class="wid160 hig25" type="text" name="aepno" maxlength="12"
				id="aepno" required="required" placeholder="<输入>" isValidEmpty="true"></td>
  </tr>
  <tr>
    <td align="right" class="header">性别</td>
    <td align="left"><select name="asex" id="asex" class="sele1">
				<option value="">-----请选择-----</option>
				<option value="男">男</option>
				<option value="女">女</option>
				</select></td>
    <td align="right" class="header">身份证号</td>
    <td align="left"><input class="wid160 hig25" type="text" name="aidno"id="aidno" placeholder="<输入>" maxlength="20"></td>
  </tr>
   <tr><td colspan="4" class="tdsplit"></td></tr>
  <tr>
    <td align="right" class="header">固定电话</td>
    <td align="left"><input class="wid160 hig25" type="text" name="atel" id="atel" placeholder="<输入>" maxlength="20"></td>
    <td align="right" class="header">移动电话</td>
    <td align="left"><input class="wid160 hig25" type="text" name="amobile" id="amobile" placeholder="<输入>" maxlength="11"></td>
  </tr>
  <tr>
    <td align="right" class="header">住址</td>
    <td align="left"><input class="wid160 hig25" type="text" name="aaddress" id="aaddress" placeholder="<输入>" maxlength="60"></td>
    <td align="right" class="header">邮编</td>
    <td align="left"><input class="wid160 hig25" type="text" name="apostcode" id="apostcode" placeholder="<输入>" maxlength="6"></td>
  </tr>
   <tr><td colspan="4" class="tdsplit"></td></tr>
  <tr>
    <td align="right" class="header">职务</td>
    <td align="left"><input class="wid160 hig25" type="text" name="aduty" id="aduty" placeholder="<输入>" maxlength="20"></td>
    <td align="right" class="header">入职日期</td>
    <td align="left"><input id="aworkdate" type="text" class="easyui-datebox" style="width:163px;height:27px"></td>
  </tr>
  <tr class="admin">
  <td align="right" class="header">优惠率</td>
    <td align="left"><input class="wid160 hig25 onlyMoney" type="text" name="ayhrate" id="ayhrate" placeholder="输入如：0.50" maxlength="4"></td>
    <td align="right" class="header">登录时间</td>
    <td align="left" colspan="3"><input id="aontime" type="text" class="easyui-timespinner" data-options="showSeconds:false" value="00:00" style="width:73px;height:25px">
    ---
    <input id="aofftime" type="text" class="easyui-timespinner" data-options="showSeconds:false" value="00:00" style="width:73px;height:25px"></td>
  </tr>
  <tr>
    <td align="right" class="header skyrequied">角色</td>
    <td align="left"><input type="hidden" id="alevelid">
    <input type="text" name="alevelname" id="alevelname" style="width:145px;height:25px" class="edithelpinput role_help" data-value="#alevelid"
	 data-options="required:true" placeholder="<请选择>" ><span onclick="selectuserrole('add');"></span></td>
    <td align="right" class="header skyrequied">所属店铺</td>
    <td align="left"><input type="hidden" id="ahouseid">
    <input  type="text" name="ahousename" id="ahousename" class="edithelpinput house_help" data-value="#ahosueid"
    data-options="required:true" placeholder="<请选择>" ><span onclick="selecthouse('addep');"></span></td>
  </tr>
  <tr>
    <td align="right" class="header">备注</td>
    <td colspan="3" align="left"><textarea style="width:100%;" name="aremark" id="aremark" cols="45" rows="2" title="addEmployee();" maxlength="200"></textarea></td>
 	<td></td>
 	<td></td>
  </tr>
</table>
		</form>
<div class="dialog-footer">
	<label class="fl-ml30"><input type="checkbox" id="apasskeys">允许登入</label>
	<input type="hidden" id="apasskey">
	<input class="btn1" type="button" id="add" style="width:150px;margin-right: 30px" type="button" value="保存并继续添加" onclick="addEmployee();">
</div>
</div>
<!-- 修改职员 -->
<div id="ud" title="编辑职员" style="width: 550px;text-align:center; height: 460px" class="easyui-dialog" closed=true>
<input type="hidden" id="uepid" >
<table width="550" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="70" align="right" class="header skyrequied">姓名</td>
    <td width="160" align="left"><input class="wid160 hig25" type="text" name="uepname"  maxlength="10"
				id="uepname" required="required" placeholder="<必须输入>" isValidEmpty="true"></td>
    <td width="120" align="right" class="header skyrequied">用户账号</td>
    <td width="160" align="left"><input class="wid160 hig25" type="text" name="uepno" id="uepno" placeholder="<输入>" maxlength="12" isValidEmpty="true"></td>
  </tr>
  <tr>
    <td align="right" class="header">性别</td>
    <td align="left"><select name="usex" id="usex" class="sele1">
				<option value="">-----请选择-----</option>
				<option value="男">男</option>
				<option value="女">女</option>
				</select></td>
    <td align="right" class="header">身份证号</td>
    <td align="left"><input class="wid160 hig25" type="text" name="uidno" maxlength="20" id="uidno" placeholder="<输入>" ></td>
  </tr>
   <tr><td colspan="4" class="tdsplit"></td></tr>
  <tr>
    <td align="right" class="header">固定电话</td>
    <td align="left"><input class="wid160 hig25" type="text" name="utel" id="utel" placeholder="<输入>" maxlength="20"></td>
    <td align="right" class="header">移动电话</td>
    <td align="left"><input class="wid160 hig25" type="text" name="umobile" id="umobile" placeholder="<输入>" maxlength="11"></td>
  </tr>
  <tr>
    <td align="right" class="header">住址</td>
    <td align="left"><input class="wid160 hig25" type="text" name="uaddress" id="uaddress" placeholder="<输入>" maxlength="60"></td>
    <td align="right" class="header">邮编</td>
    <td align="left"><input class="wid160 hig25" type="text" name="upostcode" id="upostcode" placeholder="<输入>" maxlength="6"></td>
  </tr>
   <tr><td colspan="4" class="tdsplit"></td></tr>
  <tr>
    <td align="right" class="header">职务</td>
    <td align="left"><input class="wid160 hig25" type="text" name="uduty" id="uduty" placeholder="<输入>" maxlength="20"></td>
    <td align="right" class="header">入职日期</td>
    <td align="left"><input id="uworkdate" type="text" class="easyui-datebox" style="width:163px;height:27px"></td>
  </tr>
    <tr class="admin">
     <td align="right" class="header">优惠率</td>
    <td align="left"><input class="wid160 hig25 onlyMoney" type="text" name="uyhrate" id="uyhrate" placeholder="输入如：0.50" maxlength="4"></td>
    <td align="right" class="header" >登录时间</td><td align="left" colspan="3">
    <input id="uontime" type="text" class="easyui-timespinner" data-options="showSeconds:false" value="00:00" style="width:73px;height:25px">
    ---
    <input id="uofftime" type="text" class="easyui-timespinner" data-options="showSeconds:false" value="00:00" style="width:73px;height:25px"></td>
  </tr>
  <tr>
    <td align="right" class="header skyrequied">角色</td>
    <td align="left"><input type="hidden" id="ulevelid">
    <input type="text" name="ulevelname" id="ulevelname" class="edithelpinput role_help" data-value="#ulevelid"
	 data-options="required:true" placeholder="<请选择>" ><span onclick="selectuserrole('update');"></span></td>
    <td align="right" class="header skyrequied">所属店铺</td>
    <td align="left"><input type="hidden" id="uhouseid">
    <input  type="text" name="uhousename" id="uhousename" class="edithelpinput house_help" data-value="#uhsoueid"
    data-options="required:true" placeholder="<请选择>" ><span onclick="selecthouse('updateep');"></span></td>
  </tr>
  <tr>
    <td align="right" class="header">备注</td>
    <td colspan="3" align="left"><textarea style="width:100%;" name="uremark" id="uremark" cols="45" rows="2" title="updateEmployee()" maxlength="200"></textarea></td>
 	<td></td>
 	<td></td>
  </tr>
</table>
<div class="dialog-footer">
<span class="fl-ml30">
	<label><input type="checkbox"name="unouseds" id="unouseds">禁用</label>
	<label><input type="checkbox" id="upasskeys">允许登入</label>
</span>
	<input  type="hidden" name="unoused" id="unoused">
	<input type="hidden" id="upasskey">
	<input type="button" class="btn1" id="updatepsw" value="恢复初始密码" onclick="updateeppsw()">
	<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')">
	<input type="button" class="btn1" style="width:70px;margin-right: 30px" id='update' value="保存" onclick="updateEmployee()">
</div>
</div>
<!-- 品牌授权-->
<div id="brandsqd" title="品牌授权" style="width: 530px;text-align:center; height: 700px;max-height: 100%" class="easyui-dialog" closed=true>			
	<div id="brandsqtoolbar" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="brandsqfindbox" data-enter="getepbrand(1)"  placeholder="搜索品牌<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getepbrand(1)">
	</div>
	<table id="brandt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="brandpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<!-- 店铺授权-->
<div id="housesqd" title="店铺授权" style="width: 530px;text-align:center; height: 700px;max-height: 100%" class="easyui-dialog" closed=true>	
<div class="toolbar" id="houseqxtoolbar" style="height: 30px;line-height: 30px">
<ul>
<li class="checked" id="houseqxbj0">仅查看</li>
<li id="houseqxbj1">可入库</li>
<li id="houseqxbj2">可出库</li>
</ul>
<input type="text" class="help-qsipt" id="housesqfindbox" data-enter="getephouse(1)" style="width: 160px;" placeholder="搜索店铺<回车搜索>"  data-end="yes">
<input class="btn2" type="button" value="搜索" onclick="getephouse(1)">
</div>		
	<table id="housesqt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="housesqpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<!-- 客户授权-->
<div id="custsqd" title="客户授权" style="width: 530px;text-align:center; height: 700px;max-height: 100%" class="easyui-dialog" closed=true>			
		<div id="custsqtoolbar" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="custsqfindbox" data-enter="getepcust(1)"  placeholder="搜索客户<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getepcust(1)">
	</div>
	<table id="custsqt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="custsqpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<!-- 供应商授权-->
<div id="provsqd" title="供应商授权" style="width: 530px;text-align:center; height: 700px;max-height: 100%" class="easyui-dialog" closed=true>			
		<div id="provsqtoolbar" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="provsqfindbox" data-enter="getepprov(1)"  placeholder="搜索供应商<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getepprov(1)">
	</div>
	<table id="provsqt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="provsqpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<script type="text/javascript">
var searchb = false;
var opser;
var _levelid = getValuebyName("GLEVELID");
var pgid="pg1901";
setqxpublic();
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
	$('#sepname').focus();
}
//批量删除
var delb = false;
function delbatch() {
	if (delb) {
		$('#gg').datagrid('hideColumn', 'CHECK');
		$('#gg').datagrid({
			singleSelect: true
		});
		$('#updatebtn').removeAttr('disabled');
		$('#delbatch').val('批量删除');
		delb = false;
	} else {
		$('#gg').datagrid('showColumn', 'CHECK');
		$('#gg').datagrid({
			singleSelect: false
		});
		$('#updatebtn').prop('disabled', 'disabled');
		$('#delbatch').val('取消');
		delb = true;
	}

}
//添加框
function addempd() {
	//$('#addepform')[0].reset();
	$('#aworkdate').datebox('setValue', new Date(top.getservertime()).Format('yyyy-MM-dd'));
	$('#ad').dialog('open');
}
//编辑框
function updateempd() {
	var arrs1 = $('#gg').datagrid('getSelected');
	if (!arrs1) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		$('#uepno').val(arrs1.EPNO);
		$('#uepid').val(arrs1.EPID);
		$('#uepname').val(arrs1.EPNAME);
		$('#usex').val(arrs1.SEX);
		$('#uidno').val(arrs1.IDNO);
		$('#utel').val(arrs1.TEL);
		$('#umobile').val(arrs1.MOBILE);
		$('#uaddress').val(arrs1.ADDRESS);
		$('#upostcode').val(arrs1.POSTCODE);
		$('#uduty').val(arrs1.DUTY);
		$('#uworkdate').datebox('setValue', arrs1.WORKDATE);
		$('#ulevelid').val(arrs1.LEVELID);
		$('#ulevelname').val(arrs1.LEVELNAME);
		$('#uhouseid').val(arrs1.HOUSEID);
		$('#uhousename').val(arrs1.HOUSENAME);
		$('#uremark').val(arrs1.REMARK);
		$('#uyhrate').val(Number(arrs1.YHRATE).toFixed(2));
		$('#uontime').timespinner('setValue', arrs1.ONTIME);
		$('#uofftime').timespinner('setValue', arrs1.OFFTIME);
		var status1 = arrs1.NOUSED;
		var passkey = arrs1.PASSKEY;
		if (status1 != '0') {
			$('#unouseds').prop('checked', true);
		} else {
			$('#unouseds').removeAttr('checked');
		}
		if (passkey != '0') {
			$('#upasskeys').prop('checked', true);
		} else {
			$('#upasskeys').removeAttr('checked');
		}
		if (_levelid == '0' || _levelid == '5') {
			if(_levelid==0)
				$('#upasskeys,#unouseds').removeAttr('disabled');
			if (arrs1.LEVELID == '0') {
				$('#uepno,#upasskeys,#unouseds').prop('disabled', true);
				$('#ulevelname').attr('readonly', 'readonly');
				$('.admin').hide();
				$('#ulevelname').next().hide();
				$('#updatepsw').hide();
			} else {
				$('#uepno').removeAttr('disabled');
				$('.admin').show();
				$('#ulevelname').removeAttr('readonly');
				$('#ulevelname').next().show();
				$('#updatepsw').show();
			}
		} else {
			$('#upasskeys,#unouseds').prop('disabled', true);
			if (passkey != '0') {
				$('#ulevelname').next().hide();
				$('#ulevelname').prop('readonly', true);
			} else {
				if (arrs1.LEVELID == '0') {
					$('#uepno').prop('disabled', true);
					$('#ulevelname').attr('readonly', 'readonly');
					$('#ulevelname').next().hide();
				} else {
					$('#uepno').removeProp('disabled');
					$('#ulevelname').removeAttr('readonly');
					$('#ulevelname').next().show();
				}
			}
			$('#ud :input').prop('readonly', true);
			$('#ud select').prop('disabled', true);
			$('#uworkdate').datebox({
				hasDownArrow: false,
			});
			$('#uhousename').next().hide();
			$('#ud input[type=button]').remove();
		}
		$('#ud').dialog('open');
	}
}
$(function() {
	$("#pp").createPage({
		current: 1,
		pageCount: 1,
		backFn: function(p) {
			getemplist(p);
		}
	});
	$('#gg').datagrid({
		idField: 'EPID',
		width: '100%',
		height: $("body").height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		onDblClickRow: function(rowIndex, rowData) {
			updateempd();
			ser = 'update';
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
				var val = Math.ceil(Number(value) / pagecount) - 1;
				return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
			}
		},{
			field: 'EPID',
			title: 'ID',
			width: 200,
			expable: true,
			hidden: true
		},{
			field: 'EPNAME',
			title: '姓名',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 80
		},
		{
			field: 'EPNO',
			title: '用户账号',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 80

		},
		{
			field: 'SEX',
			title: '性别',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 50
		},{
			field: 'LEVELNAME',
			title: '角色',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 80
		},{
			field: 'MOBILE',
			title: '手机',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 90
		},{
			field: 'TEL',
			title: '电话',
			align: 'left',
			halign: 'center',
			width: 200,
			hidden: true
		},{
			field: 'ADDRESS',
			title: '住址',
			align: 'left',
			halign: 'center',
			fixed: true,
			width: 200
		},{
			field: 'POSTCODE',
			title: '邮编',
			align: 'left',
			halign: 'center',
			width: 150,
			hidden: true
		},{
			field: 'HOUSENAME',
			title: '所属店铺',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 100
		},{
			field: 'PASSKEY',
			title: '登录权限',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 100,
			formatter: function(value, row, index) {
				return value == "1" ? "允许": value == "0" ? "不允许": "";
			}
		},{
			field: 'ACTION',
			title: '操作',
			fixed: true,
			width: 220,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (row.LEVELID == "0" || row.EPNO == "ADMIN") {
					return '<input type="button" class="m-btn wid60" value="品牌授权" onclick="setbrandd(' + row.EPID + ')">' 
					+ '<input type="button" class="m-btn wid60" value="店铺授权" onclick="sethoused(' + row.EPID + ')">'
					+ '<input type="button" class="m-btn" value="客户授权" onclick="setcustd(' + row.EPID + ')">'
					+ '<input type="button" class="m-btn" value="供应商授权" onclick="setprovd(' + row.EPID + ')">';
				} else {
					return '<input type="button" class="m-btn" value="删除" onclick="delemp(' + index + ')">' 
					+ '<input type="button" class="m-btn" value="品牌授权" onclick="setbrandd(' + row.EPID + ')">'
					+ '<input type="button" class="m-btn" value="店铺授权" onclick="sethoused(' + row.EPID + ')">'
					+ '<input type="button" class="m-btn" value="客户授权" onclick="setcustd(' + row.EPID + ')">'
					+ '<input type="button" class="m-btn" value="供应商授权" onclick="setprovd(' + row.EPID + ')">';
				}
			}
		}
		]],
		pageSize: 20,
		toolbar: '#toolbars'
	});
	getempdata();
	setbrandt();
	sethouset();
	setcustt();
	setprovt();
	if (_levelid == '0') {
		$('.admin').show();
		$('#apasskeys').removeAttr('disabled');
		$('#upasskeys,#unouseds').removeAttr('disabled');
	} else {
		$('#apasskeys,#upasskeys,#unouseds').prop('disabled', true);
		$('#updatepsw').remove();
		$('.admin').hide();
	}
	$("#houseqxtoolbar ul li").click(function(){
		$this = $(this);
		if(!$this.hasClass("checked")){
			$("#houseqxtoolbar ul li").removeClass("checked");
			$this.addClass("checked");
			$("#housesqpp").refreshPage();
		}
	});
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendemploye",
		xlsmobanname: "employe",
		channel: "employe"
	});
});
//清空搜索条件
function resetsearch() {
	$('#sepname').val("");
	$('#sepno').val("");
	$('#smobile').val("");
	$('#shouseid').val("");
	$('#shousename').val("");
	$('#st1').prop('checked', 'checked');
	$('#spass1').prop('checked', 'checked');
}

var currentdata = {};
var getemplist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "employelist";
	currentdata["fieldlist"] = "a.EPID,a.EPNO,a.EPNAME,a.LEVELID,c.LEVELNAME,a.SEX,a.MOBILE,a.workdate,a.yhrate,"
		+ "a.ADDRESS,a.POSTCODE,a.TEL,a.IDNO,a.DUTY,a.REMARK,a.NOUSED,a.PASSKEY,A.HOUSEID,B.HOUSENAME,a.ontime,a.offtime";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	var noused;
	if ($('#st1').prop('checked')) {
		noused = "0";
		$('#nouse').val('0');
	} else if ($('#st2').prop('checked')) {
		noused = "1";
		$('#nouse').val('1');
	} else {
		noused = "2";
		$('#nouse').val('2');
	}
	var loginid; // loginid:0=禁止登录，1=允许登录，2=所有
	if ($('#spass1').prop('checked')) {
		loginid = "1";
	} else if ($('#spass2').prop('checked')) {
		loginid = "0";
	} else {
		loginid = "2";
	}
	currentdata["loginid"] = loginid;
	currentdata["noused"] = noused;
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
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getempdata() {
	var value = $('#qsempvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getemplist(1);
}
//搜索品牌
function searchemp() {
	var epname = $('#sepname').val();
	var epno = $('#sepno').val();
	var mobile = $('#smobile').val();
	var houseid = Number($('#shouseid').val());
	alertmsg(6);
	currentdata = {
		epname: epname,
		epno: epno,
		mobile: mobile,
		houseid: houseid
	};
	getemplist(1);
}

//添加职员
function addEmployee() {
	var epname = $("#aepname").val();
	var epno = $("#aepno").val();
	var sex = $("#asex").val();
	var idno = $("#aidno").val();
	var tel = $("#atel").val();
	var mobile = $("#amobile").val();
	var address = $("#aaddress").val();
	var postcode = $("#apostcode").val();
	var duty = $("#aduty").val();
	var workdate = $('#aworkdate').datebox('getValue');
	var levelid = Number($('#alevelid').val());
	var houseid = $('#ahouseid').val();
	var remark = $('#aremark').val();
	var yhrate = $('#ayhrate').val();
	var ontime = $('#aontime').timespinner('getValue');
	var offtime = $('#aofftime').timespinner('getValue');
	var passkey = '0';
	if (_levelid == 0) {
		if ($('#apasskeys').prop('checked')) {
			passkey = '1';
		} else {
			passkey = '0';
		}
	}
	if (epname == "") {
		alerttext("姓名不能为空！");
		$('#aepname').foucs;
	} else if (epno == "" && passkey == '1') {
		alerttext("用户账号不能为空！");
		$('#aepno').foucs;
	} else if (epno.length < 1 || epno.length > 12) {
		alerttext("请控制用户账号1-12位");
		$('#aepno').foucs;
	} else if (levelid ==0 || isNaN(levelid)) {
		alerttext("请选择职员角色！");
		$('#alevelname').foucs;
	} else if (Number(houseid) == 0) {
		alerttext("请选择职员所属店铺");
		$('#ahousename').foucs;
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addemploye",
				epname: epname,
				epno: epno,
				sex: sex,
				idno: idno,
				tel: tel,
				mobile: mobile,
				passkey: passkey,
				workdate: workdate,
				ontime: ontime,
				offtime: offtime,
				levelid: levelid,
				houseid: houseid,
				remark: remark,
				address: address,
				yhrate: yhrate,
				postcode: postcode,
				duty: duty
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$("#aepname").val("");
					$("#aepno").val("");
					$("#asex").val("");
					$("#aidno").val("");
					$("#atel").val("");
					$("#amobile").val("");
					$("#aaddress").val("");
					$("#apostcode").val("");
					$("#aduty").val("");
					// 							$('#alevelname').val("");
					// 							$('#ahousename').val("");
					// 							$('#alevelid').val("");
					// 							$('#ahouseid').val("");
					$('#aremark').val("");
					$("#pp").refreshPage();
				}
			}
		});
	}
}
//修改职员
function updateEmployee() {
	var epid = $('#uepid').val();
	var epname = $("#uepname").val();
	var epno = $("#uepno").val();
	var sex = $("#usex").val();
	var idno = $("#uidno").val();
	var tel = $("#utel").val();
	var mobile = $("#umobile").val();
	var address = $("#uaddress").val();
	var postcode = $("#upostcode").val();
	var duty = $("#uduty").val();
	var workdate = $('#uworkdate').datebox('getValue');
	var ontime = $('#uontime').timespinner('getValue');
	var offtime = $('#uofftime').timespinner('getValue');
	var levelid = $('#ulevelid').val();
	var yhrate = $('#uyhrate').val();
	var houseid = $('#uhouseid').val();
	var remark = $('#uremark').val();
	var passkey;
	var noused;
	if ($('#unouseds').prop('checked')) {
		noused = "1";
	} else {
		noused = "0";
	}
	if (_levelid == 0) {
		if ($('#upasskeys').prop('checked')) {
			passkey = "1";
		} else {
			passkey = "0";
		}
	}
	if (epname == "") {
		alerttext("姓名不能为空！");
		$('#uepname').foucs;
	} else if (epno == "" && passkey == '1') {
		alerttext("用户账号不能为空！");
		$('#uepno').foucs;
	} else if (epno.length < 1 || epno.length > 12) {
		alerttext("请控制用户账号1-12位");
		$('#uepno').foucs;
	} else if (levelid == "") {
		alerttext("请选择职员角色！");
		$('#ulevelname').foucs;
	} else if (houseid == "") {
		alerttext("请选择职员所属店铺！");
		$('#uhousename').foucs;
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateemployebyid",
				epid: epid,
				epname: epname,
				epno: epno,
				sex: sex,
				idno: idno,
				tel: tel,
				mobile: mobile,
				passkey: passkey,
				workdate: workdate,
				ontime: ontime,
				offtime: offtime,
				houseid: houseid,
				remark: remark,
				address: address,
				postcode: postcode,
				duty: duty,
				yhrate: yhrate,
				levelid: levelid,
				noused: noused
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#ud').dialog('close');
					$("#pp").refreshPage();
				}
			}
		});
	}
}
function updateeppsw() {
	if (_levelid == '0') {
		alertmsg(2);
		var epid = $('#uepid').val();
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateemployebyid",
				epid: epid,
				password: "6BAD136AEF6902EB0D3C0E7666DD2DB7"
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#ud').dialog('close');
					alerttext("密码重置成功！");
				}
			}
		});
	} else {
		alerttext("只有系统管理员可以修改！");
	}
}
//删除职员
//删除
function delemp(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var epid = row.EPID;
		$.messager.confirm(dialog_title, '您确认要删除职员' + row.EPNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delemployebyid",
						epid: epid
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
function openhoused(houseser) {
	$('#housesqt').datagrid({
		onClickRow: function(rowIndex, rowData) {
			if (hosueser == "add") {
				$('#ahouseid').val(rowData.HOUSEID);
				$('#ahousename').val(rowData.HOUSENAME);
			} else if (hosueser == "update") {
				$('#uhouseid').val(rowData.HOUSEID);
				$('#uhousename').val(rowData.HOUSENAME);
			}
		}
	});
}
function getepbrand(page) {
	var epid = $('#uepid').val();
	var findbox = $("#brandsqfindbox").val().toUpperCase();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "employebrandlist",
			epid: epid,
			findbox:findbox,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var total = data.total;
				$('#brandpp').setPage({
					pageCount: Math.ceil(total / pagecount),
					current: Number(page)
				});
				check = false;
				$('#brandt').datagrid('loadData', data);
				check = true;
			}
		}
	});
}
function setbrandd(epid) {
	$('#uepid').val(epid);
	$("#brandsqfindbox").val("");
	$('#brandsqd').dialog('open');
	getepbrand('1');
}
function setepbrand(brandid, value) {
	alertmsg(2);
	var epid = $('#uepid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeemployebrand",
			epid: epid,
			brandid: brandid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {

			}
		}
	});
}
function setallbrand(value) {
	var epid = $('#uepid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeallemployebrand",
			epid: epid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
			}
		}
	});
}
var check = false;
function setbrandt() {
	$('#brandpp').createPage({
		current: 1,
		backFn: function(p) {
			getepbrand(p);
		}
	});
	$('#brandt').datagrid({
		idField: 'epbrand',
		height: $('#brandsqd').height()-50,
		toolbar: "#brandsqtoolbar",
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
			field: 'BRANDID',
			title: '品牌ID',
			hidden: true,
			width: 80
		},
		{
			field: 'BRANDNAME',
			title: '品牌',
			width: 100
		},
		{
			field: 'SELBJ',
			title: 'SELBJ',
			hidden: true,
			width: 80
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
				setepbrand(row.BRANDID, '1');
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setepbrand(row.BRANDID, '0');
			}
		},
		onCheckAll: function(index, row) {
			if (check) {
				setallbrand('1');
			}
		},
		onUncheckAll: function(index, row) {
			if (check) {
				setallbrand('0');
			}
		},
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (item.SELBJ == '1') {
						$('#brandt').datagrid('checkRow', index);
					} else $('#brandt').datagrid('uncheckRow', index);
				});
				check = true;
				if (_levelid == '0' || _levelid == '5') {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').removeAttr('disabled');
				} else {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').prop('disabled', true);
				}
			}
		}
	});
}
function getephouse(page) {
	var epid = $('#uepid').val();
	var findbox = $("#housesqfindbox").val().toUpperCase();
	var erpser = "";
	if($("#houseqxbj0").hasClass("checked"))
		erpser = "employehouselist";
	else if($("#houseqxbj1").hasClass("checked"))
		erpser = "employehouseinlist";
	else if($("#houseqxbj2").hasClass("checked"))
		erpser = "employehouseoutlist";
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			findbox: findbox,
			epid: epid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var total = data.total;
				$('#housesqpp').setPage({
					pageCount: Math.ceil(total / pagecount),
					current: Number(page)
				});
				check = false;
				$('#housesqt').datagrid('loadData', data);
				check = true;

			}
		}
	});
}
function sethoused(epid) {
	$('#uepid').val(epid);
	$("#housesqfindbox").val("");
	$('#housesqd').dialog('open');
	getephouse('1');
}
function setephouse(houseid, value) {
	alertmsg(2);
	var row = $("#gg").datagrid("getSelected");
	var _houseid = Number(row.HOUSEID);
	if(Number(houseid)==_houseid&&value==0){
		alerttext("所属店铺不能取消授权");
		$("#housesqpp").refreshPage();
		return;
	}
	var epid = $('#uepid').val();
	var erpser = "";
	if($("#houseqxbj0").hasClass("checked"))
		erpser = "writeemployehouse";
	else if($("#houseqxbj1").hasClass("checked"))
		erpser = "writeemployehousein";
	else if($("#houseqxbj2").hasClass("checked"))
		erpser = "writeemployehouseout";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			epid: epid,
			houseid: houseid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				
			}
		}
	});
}
function setallhouse(value) {
	var epid = $('#uepid').val();
	var erpser = "";
	if($("#houseqxbj0").hasClass("checked"))
		erpser = "writeallemployehouse";
	else if($("#houseqxbj1").hasClass("checked"))
		erpser = "writeallemployehousein";
	else if($("#houseqxbj2").hasClass("checked"))
		erpser = "writeallemployehouseout";
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			epid: epid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
			}
		}
	});
}
var check = false;
function sethouset() {
	$('#housesqpp').createPage({
		current: 1,
		backFn: function(p) {
			getephouse(p);
		}
	});
	$('#housesqt').datagrid({
		idField: 'HOUSEID',
		height: $('#housesqd').height()-50,
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
			field: 'HOUSEID',
			title: '店铺ID',
			hidden: true,
			width: 80
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 100
		},
		{
			field: 'SELBJ',
			title: 'SELBJ',
			hidden: true,
			width: 80
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
		toolbar: "#houseqxtoolbar",
		onCheck: function(index, row) {
			if (check) {
				setephouse(row.HOUSEID, '1');
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setephouse(row.HOUSEID, '0');
			}
		},
		onCheckAll: function(index, row) {
			if (check) {
				setallhouse('1');
			}
		},
		onUncheckAll: function(index, row) {
			if (check) {
				setallhouse('0');
			}
		},
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (item.SELBJ == '1') {
						$('#housesqt').datagrid('checkRow', index);
					} else $('#housesqt').datagrid('uncheckRow', index);
				});
				check = true;
				if (_levelid == '0' || _levelid == '5') {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').removeAttr('disabled');
				} else {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').prop('disabled', true);
				}
			}
		}
	});
}

function getepcust(page) {
	var epid = $('#uepid').val();
	var findbox = $("#custsqfindbox").val().toUpperCase();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "employecustlist",
			findbox: findbox,
			epid: epid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var total = data.total;
				$('#custsqpp').setPage({
					pageCount: Math.ceil(total / pagecount),
					current: Number(page)
				});
				check = false;
				$('#custsqt').datagrid('loadData', data);
				check = true;

			}
		}
	});
}
function setcustd(epid) {
	$('#uepid').val(epid);
	$("#custsqfindbox").val("");
	$('#custsqd').dialog('open');
	getepcust('1');
}
function setepcust(custid, value) {
	alertmsg(2);
	var row = $("#gg").datagrid("getSelected");
	var _custid = Number(row.CUSTID);
	if(Number(custid)==_custid&&value==0){
		alerttext("所属客户不能取消授权");
		$("#custsqpp").refreshPage();
		return;
	}
	var epid = $('#uepid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeemployecust",
			epid: epid,
			custid: custid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				
			}
		}
	});
}
function setallcust(value) {
	var epid = $('#uepid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeallemployecust",
			epid: epid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {

}

		}
	});
}
var check = false;
function setcustt() {
	$('#custsqpp').createPage({
		current: 1,
		backFn: function(p) {
			getepcust(p);
		}
	});
	$('#custsqt').datagrid({
		idField: 'CUSTID',
		height: $('#custsqd').height()-50,
		toolbar: "#custsqtoolbar",
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
			field: 'CUSTID',
			title: '客户ID',
			hidden: true,
			width: 80
		},
		{
			field: 'CUSTNAME',
			title: '客户',
			width: 100
		},
		{
			field: 'SELBJ',
			title: 'SELBJ',
			hidden: true,
			width: 80
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
				setepcust(row.CUSTID, '1');
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setepcust(row.CUSTID, '0');
			}
		},
		onCheckAll: function(index, row) {
			if (check) {
				setallcust('1');
			}
		},
		onUncheckAll: function(index, row) {
			if (check) {
				setallcust('0');
			}
		},
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (item.SELBJ == '1') {
						$('#custsqt').datagrid('checkRow', index);
					} else $('#custsqt').datagrid('uncheckRow', index);
				});
				check = true;
				if (_levelid == '0' || _levelid == '5') {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').removeAttr('disabled');
				} else {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').prop('disabled', true);
				}
			}
		}
	});
}
function getepprov(page) {
	var epid = $('#uepid').val();
	var findbox = $("#provsqfindbox").val().toUpperCase();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "employeprovlist",
			findbox: findbox,
			epid: epid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var total = data.total;
				$('#provsqpp').setPage({
					pageCount: Math.ceil(total / pagecount),
					current: Number(page)
				});
				check = false;
				$('#provsqt').datagrid('loadData', data);
				check = true;

			}
		}
	});
}
function setprovd(epid) {
	$('#uepid').val(epid);
	$("#provsqfindbox").val("");
	$('#provsqd').dialog('open');
	getepprov('1');
}
function setepprov(provid, value) {
	alertmsg(2);
	var row = $("#gg").datagrid("getSelected");
	var _provid = Number(row.PROVID);
	if(Number(provid)==_provid&&value==0){
		alerttext("所属供应商不能取消授权");
		$("#provsqpp").refreshPage();
		return;
	}
	var epid = $('#uepid').val();
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
				
			}
		}
	});
}
function setallprov(value) {
	var epid = $('#uepid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeallemployeprov",
			epid: epid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {

}

		}
	});
}
var check = false;
function setprovt() {
	$('#provsqpp').createPage({
		current: 1,
		backFn: function(p) {
			getepprov(p);
		}
	});
	$('#provsqt').datagrid({
		idField: 'PROVID',
		height: $('#provsqd').height()-50,
		toolbar: "#provsqtoolbar",
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
			field: 'PROVID',
			title: '供应商ID',
			hidden: true,
			width: 80
		},
		{
			field: 'PROVNAME',
			title: '供应商',
			width: 100
		},
		{
			field: 'SELBJ',
			title: 'SELBJ',
			hidden: true,
			width: 80
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
				setepprov(row.PROVID, '1');
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setepprov(row.PROVID, '0');
			}
		},
		onCheckAll: function(index, row) {
			if (check) {
				setallprov('1');
			}
		},
		onUncheckAll: function(index, row) {
			if (check) {
				setallprov('0');
			}
		},
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (item.SELBJ == '1') {
						$('#provsqt').datagrid('checkRow', index);
					} else $('#provsqt').datagrid('uncheckRow', index);
				});
				check = true;
				if (_levelid == '0' || _levelid == '5') {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').removeAttr('disabled');
				} else {
					$('.datagrid-cell-check input[type=checkbox],.datagrid-header-check input[type=checkbox]').prop('disabled', true);
				}
			}
		}
	});
}
//处理输入框空格问题
function validEmpty($ele) {
    var pattern = new RegExp(/\s+/g);
    $ele.find("input[isValidEmpty='true']").on('blur', function () {
		var val = $(this).val();
		if (pattern.test(val)) {
			//var txt = $(this).parent('td').siblings('td[align="right"]').text();
			//alerttext("您输入的" + txt + "字段中含有空格，已为您自动去除");
			$(this).val(val.replace(/\s+/g, ''));
		}
    });
}

$(document).ready(function() {
    validEmpty($('#ad'));
    validEmpty($('#ud'));
});
</script>
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/RoleHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>