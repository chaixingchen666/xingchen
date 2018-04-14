<%@ page language='java' contentType='text/html; charset=UTF-8'
	pageEncoding='UTF-8'%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>客户档案</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
.changecustbrand{
	width:100px;
	height:25px;
	text-align: center;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/webuploader/webuploader.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class='easyui-layout layout panel-noscroll' style='margin: auto;'>
	<input type='hidden' name='nouse' id='nouse' value="0">
	<!-- 表格工具栏 -->
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
<input class="btn1" type="button" value="添加" onclick="addcustd()">
<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatecustd()">
<input type="text" placeholder="搜索客户名称<回车搜索>" data-end="yes" class="ipt1" id="qscustvalue" maxlength="30" data-enter="getcustdata()">
<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
</div>
<div class="fl hide" id="searchbtn">
<input id="search" type="button" class="btn2" type="button" value="搜索" onclick="searchcustdata();toggle();">
<input type="button" class="btn2" style="width: 100px;" value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr">
<input type="button" class="btn3" value="导入" onclick="openimportd()"> 
<span class="sepreate"></span>
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
		<!-- 高级搜索栏 -->
	<div class="searchbar" style="display: none" data-enter="searchcustdata();toggle();">
	<div class="search-box">
	<div class="s-box">
	<div class="title">客户名称</div>
	<div class="text"><input class="wid160 hig25" id="scustname" maxlength="32" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">联系人</div>
	<div class="text"><input class="wid160 hig25" id="slinkman" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">移动电话</div>
	<div class="text"><input class="wid160 hig25" id="smobile" maxlength="20" type="text"  placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">备注</div>
	<div class="text"><input class="wid160 hig25" id="sremark" maxlength="20" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">区域</div>
	<div class="text"><input type="text" class="hig25 wid160" placeholder="<输入或点击选择>" id="sareaname" name="sareaname" list="area_list">
	</div></div>
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
		<div
			class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
	<!-- 增加客户档案-->
	<div id="ad" title="添加客户" style="width: 600px; height: 410px; text-align: center;"
		class="easyui-dialog" closed=true>
		<table width="550" border="0" cellspacing="5" class="table1">
			<tr>
				<td width="70" align="right" class="header skyrequied">客户名称</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="32"
					placeholder="<输入>" id="acustname" name="acustname" /></td>
				<td width="130" align="right" class="header">联系人</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
					placeholder="<输入>" id="alinkman" name="alinkman" /></td>
			</tr>
			<tr>
				<td align="right" class="header">联系电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>" id="atel" name="atel" maxlength="32" /></td>
				<td align="right" class="header">移动电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>" id="amobile" name="amobile" maxlength="11" /></td>
			</tr>
			<tr>
				<td align="right" class="header">业务员</td>
				<td align="left">
				<input type="hidden" id="ahandmanid">
				<input class="edithelpinput emp_help" type="text" data-value="changenotecust" id="ahandmanname" placeholder="<选择>" >
				<span id="selcustspan" onclick="selectemploye('addcust')" ></span>
				</td>

				<td align="right" class="header skyrequied">欠账提醒日期</td>
				<td align="left"><select id='tblownwarn' name='tlownwarn' style="width: 152px; height: 27px">
					<option value='7'>欠账日7天后</option>
					<option value='15'>欠账日15天后</option>
					<option value='30'>欠账日一个月后</option>
					<option value='60'>欠账日两个月后</option>
					<option value='90'>欠账日三个月后</option>
					<option value='180'>欠账日半年后</option>
					<%--<option value='0'>自定义</option>--%>

				</select></td>

			</tr>
			<tr><td align="right" class="header skyrequied">价格方式</td>
				<td align="left"><select id='apricetype' name='apricetype' style="width: 152px; height: 27px">
						<option value='0'>零售价</option>
						<option value='4'>批发价</option>
						<option value='5'>打包价</option>
						<option value='1'>售价一</option>
						<option value='2'>售价二</option>
						<option value='3'>售价三</option>
				</select></td>
			<td align="right" class="header skyrequied">折扣</td>
				<td align="left"><input class="hig25" type="text" maxlength="4" value="1.00" onchange="diskz(this.value,'add')"
					placeholder="<输入>" id="adiscount" name="adiscount" /></td>
					</tr>
			<tr>
			    <td align="right" class="header">邮编</td>
				<td align="left"><input class="hig25" type="text" maxlength="6"
					placeholder="<输入>" id="apostcode" name="apostcode" /></td>
				<td align="right" class="header">区域</td>
				<td align="left"><input type="text" class="hig25" placeholder="<输入或点击选择>" id="aareaname" name="aareaname" list="area_list">
				  <datalist id="area_list">
				  </datalist>
				</td>	
			</tr>
			<tr>
				<td colspan="4" class="tdsplit"></td>
			</tr>
			<tr>
				<td align="right" class="header">开户银行</td>
				<td align="left"><input class="hig25" type="text" maxlength="30"
					placeholder="<输入>" id="abankname" name="abankname" /></td>
				<td align="right" class="header">银行账号</td>
				<td align="left"><input class="hig25" type="text" maxlength="19"
					placeholder="<输入>" id="aaccountno" name="accountno" /></td>
			</tr>
			<tr>
				<td align="right" class="header">税号</td>
				<td align="left"><input class="hig25" type="text" maxlength="15"
					placeholder="<输入>" id="ataxno" name="ataxno" /></td>
				<td align="right" class="header">保证金</td>
				<td align="left"><input class="hig25" type="text" maxlength="7"
					placeholder="<输入>" id="acurrbzj" name="acurrbzj" /></td>
			</tr>
			<tr>
				<td align="right" class="header">信用额度</td>
				<td align="left"><input class="hig25" type="text" maxlength="8"
					placeholder="<输入>" id="acreditcurr" name="acreditcurr" /></td>
				<td align="right" class="header">备注</td>
				<td align="left"><input class="hig25" type="text" maxlength="30"
					placeholder="<输入>" id="aremark" name="aremark" title="addcustomer();" /></td>
				
			</tr>
			<tr>
				<td align="right" class="header">地址</td>
				<td align="left" colspan="3"><input class="hig25" type="text" style="width:440px;"
					placeholder="<输入>" maxlength="100" id="aaddress"
					name="aaddress" /></td></tr>
		</table>
		<div class="dialog-footer">
			<label class="fl-ml30"><input type="checkbox"
				name="acreditoks" id="acreditoks">启用信用控制</label>
				<label id="ashowqxkz"><input type="checkbox" id="aalluser" >授权所有职员</label>
				 <input class="btn1"
				type="button" id="add" style="width: 150px; margin-right: 20px"
				type="button" value="保存并继续添加" onclick="addcustomer();">
		</div>
	</div>
	<!-- 修改-------------------------- -->
	<div id="ud" title="修改客户"
		style="width: 600px; height: 410px; text-align: center;"
		class="easyui-dialog" closed=true>
		<input type="hidden" name="ucustid" id="ucustid">
		<input type="hidden" id="uindex">
		<table width="550" border="0" cellspacing="5" class="table1">
			<tr>
				<td width="70" align="right" class="header skyrequied">客户名称</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="32"
					placeholder="<输入>" id="ucustname" name="ucustname" /></td>
				<td width="130" align="right" class="header">联系人</td>
				<td width="160" align="left"><input class="hig25" type="text" maxlength="20"
					placeholder="<输入>" id="ulinkman" name="ulinkman" /></td>
			</tr>
			<tr>
				<td align="right" class="header">联系电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>" id="utel" name="utel" maxlength="32" /></td>
				<td align="right" class="header">移动电话</td>
				<td align="left"><input class="hig25" type="text"
					placeholder="<输入>" id="umobile" name="amobile" maxlength="11" /></td>
			</tr>
			<tr>
				<td align="right" class="header">业务员</td>
				<td align="left">
				<input type="hidden" id="uhandmanid">
				<input class="edithelpinput emp_help" type="text" data-value="changenotecust" id="uhandmanname" placeholder="<选择>" >
				<span id="selcustspan" onclick="selectemploye('updatecust')" ></span>
				</td>

				<td align="right" class="header skyrequied">欠账提醒日期</td>
				<td align="left"><select id='xblownwarn' name='xlownwarn' style="width: 152px; height: 27px">
					<option value='7'>欠账日7天后</option>
					<option value='15'>欠账日15天后</option>
					<option value='30'>欠账日一个月后</option>
					<option value='60'>欠账日两个月后</option>
					<option value='90'>欠账日三个月后</option>
					<option value='180'>欠账日半年后</option>
					<%--<option value='0'>自定义</option>--%>
				</select></td>

			</tr>

			<tr>
			<td align="right" class="header skyrequied">价格方式</td>
				<td align="left"><select id='upricetype' name='upricetype'
					style="width: 96px; height: 27px">
						<option value='0'>零售价</option>
						<option value='4'>批发价</option>
						<option value='5'>打包价</option>
						<option value='1'>售价一</option>
						<option value='2'>售价二</option>
						<option value='3'>售价三</option>
				</select>
				<input type="button" class="btn1" style="font-size: 12px;padding: 4px 7px;" value="更多" onclick="openbranddialog()">
				</td>
				<td align="right" class="header skyrequied">折扣</td>
				<td align="left"><input class="hig25" type="text" maxlength="4" value="1.00" onchange="diskz(this.value,'update')"
					placeholder="<输入>" id="udiscount" name="udiscount" /></td>
				</tr>
			<tr>	
				<td align="right" class="header">邮编</td>
				<td align="left"><input class="hig25" type="text" maxlength="6"
					placeholder="<输入>" id="upostcode" name="upostcode" /></td>
				<td align="right" class="header">区域</td>
				<td align="left"><input type="text" class="hig25" placeholder="<输入或点击选择>" id="uareaname" name="uareaname" list="area_list">
					</td>
			</tr>
			<tr>
				<td colspan="4" class="tdsplit"></td>
			</tr>
			<tr>
				<td align="right" class="header">开户银行</td>
				<td align="left"><input class="hig25" type="text" maxlength="30"
					placeholder="<输入>" id="ubankname" name="ubankname" /></td>
				<td align="right" class="header">银行账号</td>
				<td align="left"><input class="hig25" type="text" maxlength="19"
					placeholder="<输入>" id="uaccountno" name="uccountno" /></td>
			</tr>
			<tr>
				<td align="right" class="header">税号</td>
				<td align="left"><input class="hig25" type="text" maxlength="15"
					placeholder="<输入>" id="utaxno" name="utaxno" /></td>
				<td align="right" class="header">保证金</td>
				<td align="left"><input class="hig25" type="text" maxlength="7"
					placeholder="<输入>" id="ucurrbzj" name="ucurrbzj" /></td>
			</tr>
			<tr>
				<td align="right" class="header">信用额度</td>
				<td align="left"><input class="hig25" type="text" maxlength="8"
					placeholder="<输入>" id="ucreditcurr" name="ucreditcurr" /></td>
				<td align="right" class="header">备注</td>
				<td align="left"><input class="hig25" type="text" maxlength="30"
					placeholder="<输入>" id="uremark" name="uremark" title="updatecustomer()"/></td>
			
			</tr>
			<tr>
			<td align="right" class="header">地址</td>
				<td align="left" colspan="3"><input class="hig25" type="text" style="width:440px;"
					placeholder="<输入>" maxlength="100" id="uaddress"
					name="uaddress" /></td>
			
			</tr>
		</table>
		<div class="dialog-footer">
			<span class="fl-ml30"> <label><input type="checkbox"
					name="unouse" id="unouse">禁用</label> <label><input
					type="checkbox" name="ucreditoks" id="ucreditoks">启用信用控制</label></span>
					<label id="ushowqxkz"><input type="checkbox" id="ualluser" >授权所有职员</label>
					 <input
				type="hidden" name="unoused" id="unoused"> 
				<input id="update" type="button" class="btn1" name="updatecust" value="保存" onclick="updatecustomer()">
				<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
		</div>
	</div>
	<div id="barndpriced" title="设置品牌折扣" data-option="modal:true" style="width: 530px; height:440px;" class="easyui-dialog" closed=true>
			<div id="brandpricesbtn" class="help-qstoolbar">
<!-- 		<input class="btn2" type="button" value="添加" onclick="addbrandd()"> -->
		<input type="text" class="help-qsipt" id="custbrandfindbox" data-enter="getcustbrand('1')"  placeholder="搜索品牌<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getcustbrand('1')">
	</div>
	<div id="#barndpricediv">
		<table id="brandpricet" style="overflow: hidden;"></table>
	</div>
		<div class="dialog-footer">
			<div id="brandpricepp" class="tcdPageCode" style="float: left;"></div>
		</div>
	</div>
<div id="omuserd" title="客户职员管理" class="easyui-dialog" closed="true" style="width:100%;height:100%;">
<div class="toolbar" id="omuser_toolbar">
<div class="toolbar_box1">
<div class="fl">
<input class="btn1 glyqx" type="button" value="添加" onclick="addomuserd()">
<input class="btn1 glyqx" type="button" value="编辑" onclick="updateomuserd()">
		<input type="text" data-end="yes" placeholder="快速查找<回车搜索>" data-enter="getomuserlist(1)" class="ipt1 wid160" id="omuserqsfindbox" maxlength="20">
		<input class="btn2" type="button" value="搜索" onclick="getomuserlist(1)">
<!-- 	<input type="button" class="btn3" value="导入" onclick="openimportxlsd('user')"> -->
<!-- 	<span class="sepreate hide"></span> -->
<!-- 	<input type="button" class="btn3"  value="导出" onclick="exportuserxls()"> -->
</div>
</div>
</div>
<table id="omusert" ></table>
	<div class="page-bar">
		<div class="page-total">共有<span id="omusertotal">0</span>条记录
		</div>
		<div id="omuserpp" class="tcdPageCode page-code"></div>
	</div>
</div>
<div id="uomuserd" title="编辑成员" class="easyui-dialog" style="width:500px;height:320px;" closed="true">
<div style="text-align: center;">
<input type="hidden" id="uomepid">
<table class="table1" cellspacing="10">
<tr>
<td  class="header skyrequied" align="right">
用户姓名
</td>
<td align="left">
<input type="text" id="uomepname" class="wid160 hig25" value="" placeholder="<输入>" maxlength="20">
</td>
<td class="header" align="right">
移动电话
</td>
<td align="left">
<input type="text" id="umobile" class="wid160 hig25" value="" placeholder="<输入>" maxlength="20">
</td>
</tr>
<tr>
<td  class="header" align="right">
用户账号
</td>
<td align="left">
<input type="text" id="uomepno" class="wid160 hig25 uppercase" value="" placeholder="<输入字母或数字>" maxlength="10">
</td>
<td  class="header skyrequied" align="right">
密码
</td>
<td align="left">
<input type="text" id="upassword" class="wid160 hig25" value="123456" placeholder="<输入>" maxlength="10">
</td>
</tr>
<tr>
<td  class="header" align="right">
备注
</td>
<td align="left" colspan="3">
<input type="text" id="uuserremark" class="hig25" style="width:390px" value="" placeholder="<输入>" maxlength="50">
</td>
</tr>
</table>
</div>
<div class="dialog-footer">
<div class="fl-ml30">
<label>
<input type="checkbox" id="uuserstatetag" checked>
允许登录
</label>
</div>
	<div class="fr">
	<input type="button" class="btn1" value="确定" onclick="saveomuser();">
	</div>
</div>
</div>
<!-- 职员授权-->
<div id="epsqd" title="授权职员" style="width: 400px;text-align:center; height: 500px" class="easyui-dialog" closed=true>			
	<table id="epsqt" style="overflow:hidden"></table>
	<div class="dialog-footer textcenter">
	<div id="epsqpp" class="tcdPageCode fl"></div> 
	</div>
</div>
<script type='text/javascript'>
setqxpublic();
var searchb = false;
var pgid="pg1009";
var levelid = Number(getValuebyName("GLEVELID"));
var glyqx = levelid==0||levelid==4||levelid==5;
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
	$('#scustname').focus();
}
//折扣控制
function diskz(inVal, state) {
	if (! (/^0|0.([0-9]*)|1$/.test(inVal))) {
		alerttext("请输入0-1之间的数!");
		if (state == 'add') {
			$('#adiscount').val('');
			$('#adiscount').focus();
		} else if (state == 'update') {
			$('#udiscount').val('');
			$('#udiscount').focus();
		}
	}
}
//批量删除
var delb = false;
function delbatch() {
	if (delb) {
		$('#gg').datagrid('hideColumn', 'CHECK');
		$('#gg').datagrid({
			singleSelect: true
		});
		$('#delbatch').val('批量删除');
		delb = false;
	} else {
		$('#gg').datagrid('showColumn', 'CHECK');
		$('#gg').datagrid({
			singleSelect: false
		});
		$('#delbatch').val('取消');
		delb = true;
	}
}
//添加框
function addcustd() {
	$('#ad').dialog('open');
	$("#aalluser").prop("checked",true);
	$('#acustname').focus();
}
//编辑框
function updatecustd() {
	var arrs = $('#gg').datagrid('getSelections');
	if (arrs.length != 1) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		var arrs1 = $('#gg').datagrid('getSelected');
		$('#ud').dialog('open');
		$('#ucustname').focus();
		if (arrs1) {
			$('#ucustname').val(arrs1.CUSTNAME);
			$('#umobile').val(arrs1.MOBILE);
			$('#uaddress').val(arrs1.ADDRESS);
			$('#ucustid').val(arrs1.CUSTID);
			$('#ulinkman').val(arrs1.LINKMAN);
			$('#ucode').val(arrs1.POSTCODE);
			$('#unoused').val(arrs1.NOUSED);
			$('#utel').val(arrs1.TEL);
			$('#uareaname').val(arrs1.AREANAME);
			$('#ubankname').val(arrs1.BANKNAME);
			$('#ucredit').val(arrs1.CREDIT);
			$('#udiscount').val(Number(arrs1.DISCOUNT).toFixed(2));
			$('#uremark').val(arrs1.REMARK);
			$('#uaccountno').val(arrs1.ACCOUNTNO);
			$('#utaxno').val(arrs1.TAXNO);
			$('#ucurrbzj').val(arrs1.CURRBZJ);
			$('#upricetype').val(arrs1.PRICETYPE);
			$('#ucreditcurr').val(arrs1.CREDITCURR);
			$('#uhandmanid').val(arrs1.HANDMANID);
			$('#uhandmanname').val(arrs1.HANDMANNAME);
		} else {}
		var status1 = arrs1.NOUSED;
		var creditok = arrs1.CREDITOK;
		if (status1 != '0') {
			$('#unouse').prop('checked', 'true');
		} else {
			$('#unouse').removeAttr('checked');
		}
		if (creditok != '0') {
			$('#ucreditoks').prop('checked', 'true');
		} else {
			$('#ucreditoks').removeAttr('checked');
		}
		$("#ualluser").removeProp("checked");
	}
}
$(function() {
	setcustbrandt();
	setept();
	if(!glyqx) $(".glyqx").remove();
	if(qxkz==0||(levelid != 0 && levelid != 5) ) $("#ashowqxkz,#ushowqxkz").hide();
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getcustlist(p);
		}
	});

	$('#gg').datagrid({
		width: '100%',
		height: $("body").height()-50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		pageSize: 20,
		sortName: "CUSTNAME",
		sortOrder: "asc",
		columns: [[{
			field: 'CHECK',
			checkbox: true,
			hidden: true
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
			field: 'CUSTID',
			title: '客户ID',
			halign: 'center',
			expable: true,
			hidden: true,
			fixed: true,
			width: 150
		},
		{
			field: 'CUSTNAME',
			title: '客户名称',
			halign: 'center',
			fixed: true,
			width: 150
		},
		{
			field: 'LINKMAN',
			title: '联系人',
			halign: 'center',
			fixed: true,
			width: 80
		},
		{
			field: 'TEL',
			title: '联系电话',
			halign: 'center',
			fixed: true,
			width: 100
		},
		{
			field: 'MOBILE',
			title: '移动电话',
			halign: 'center',
			fixed: true,
			width: 100
		},
		{
			field: 'DISCOUNT',
			title: '折扣',
			align: 'center',
			halign: 'center',
			fieldtype: "N",
			fixed: true,
			formatter: currfm,
			width: 50
		},
		{
			field: 'PRICETYPE',
			title: '价格方式',
			align: 'center',
			halign: 'center',
			width: 100,
			fixed: true,
			formatter: function(value, row, index) {
				var pt = value == '0' ? '零售价': value == '1' ? '售价1': value == '2' ? '售价二': value == '3' ? '售价三': value == '4' ? '批发价': '打包价';
				return pt;
			}
		},


            {
                field: 'TBLOWNWARN',
                title: '欠账提醒日期',
                align: 'center',
                halign: 'center',
                width: 100,
                fixed: true,
                formatter: function(value, row, index) {
                    var tb = value == '7' ? '欠款日7天后': value == '15' ? '欠款日15天后': value == '30' ? '欠款日一个月后': value == '60' ? '欠款日两个月后': value == '90' ? '欠款日三个月后': value == '180' ? '欠款日半年后' : '';
                    return tb;
                }
            },



		{
			field: 'ADDRESS',
			title: '地址',
			halign: 'center',
			fixed: true,
			width: 200
		},
		{
			field: 'AREANAME',
			title: '区域',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 80
		},
		{
			field: 'HANDMANNAME',
			title: '业务员',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 80
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
				return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledcust(' + index + ')" ' + checked + '>';
			}
		},{
			field: 'ACTION',
			title: '操作',
			width: 160,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var html = '<input type="button" class="m-btn" value="删除" onclick="delcust(' + index + ')">'
					+ '<input type="button" class="m-btn" value="职员管理" onclick="openomuserd(' + index + ')">';
				if(qxkz==1)
					html +='<input type="button" class="m-btn" value="授权职员" onclick="setepd(' + row.CUSTID + ')">';
				return html;
			}
		}]],
		onClickRow: function(index, row) {
			$('#uindex').val(index);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatecustd();
		},
		toolbar: '#toolbars'
	}).datagrid("keyCtr", "updatecustd()");
	getcustdata();
	areasearch();
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendcustomer",
		xlsmobanname: "custcode",
		channel: "custcode"
	});
});
//清空搜索条件
function resetsearch() {
	$('#scustname').val('');
	$('#slinkman').val('');
	$('#sareaname').val('');
	$('#smobile').val('');
	$('#sremark').val('');
	$('#st3').prop('checked', 'checked');
}
var currentdata = {};
var getcustlist = function(page) {
	var $dg = $("#gg");
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
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "customerlist";
	currentdata["noused"] = noused;
// 	currentdata["sort"] = sort;
// 	currentdata["order"] = order;
	currentdata["fieldlist"] = "a.custid,a.custname,a.linkman,a.mobile,a.remark,a.noused,a.tel" + ",a.address,a.bankname,a.accountno,a.taxno,a.discount,a.pricetype" + ",a.cust_accid,a.creditcurr,a.currbzj,a.postcode,a.creditok,a.areaname,a.handmanid";
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
function getcustdata() {
	var value = $('#qscustvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getcustlist(1);
}
//搜索客户
function searchcustdata() {
	var custname = $('#scustname').val();
	var linkman = $('#slinkman').val();
	var mobile = $('#smobile').val();
	var remark = $('#sremark').val();
	var areaname = $('#sareaname').val();
	alertmsg(6);
	currentdata = {
			custname: custname,
			linkman: linkman,
			mobile: mobile,
			remark: remark,
			areaname: areaname
	};
	getcustlist(1);
}
function areasearch() {
	$.ajax({
		type: 'POST',
		//访问WebService使用Post方式请求 
		url: '/skydesk/fyjerp',
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: 'custarealist',
			rows: 50,
			page:1
		},
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var rows = data.rows;
				var arealist = '';
				for (var i in rows) {
					arealist += '<option label="' + rows[i].AREANAME + '" value="' + rows[i].AREANAME + '"/>';
				}
				$('#area_list').html(arealist);
			}
		}
	});
}
//添加客户数据
function addcustomer() {
	var custname = $('#acustname').val();
	var linkman = $('#alinkman').val();
	var remark = $('#aremark').val();
	var bankname = $('#abankname').val();
	var address = $('#aaddress').val();
	var mobile = $('#amobile').val();
	var tel = $('#atel').val();
	var discount = $('#adiscount').val();
	var postcode = $('#apostcode').val();
	var taxno = $('#ataxno').val();
	var creditcurr = $('#acreditcurr').val();
	var currbzj = $('#acurrbzj').val();
	var accountno = $('#aaccountno').val();
	var pricetype = $('#apricetype').val();
	var areaname = $('#aareaname').val();
	var handmanid = $('#ahandmanid').val();
	var creditok;
	if ($('#acreditoks').prop('checked')) {
		creditok = '1';
	} else {
		creditok = '0';
	}
	var alluser = 0;
	if($("#aalluser").prop("checked")&&(levelid == 0 || levelid == 5))
		alluser=1;
	if (pricetype == '') {
		alerttext('请选择价格方式！');
	} else {
		alertmsg(2);
		$.ajax({
			type: 'POST',
			//访问WebService使用Post方式请求 
			url: '/skydesk/fyjerp',
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: 'addcustomer',
				custname: custname,
				linkman: linkman,
				remark: remark,
				bankname: bankname,
				address: address,
				mobile: mobile,
				tel: tel,
				handmanid: handmanid,
				areaname: areaname,
				discount: discount,
				postcode: postcode,
				taxno: taxno,
				creditcurr: creditcurr,
				accountno: accountno,
				pricetype: pricetype,
				creditok: creditok,
				currbzj: currbzj,
				alluser: alluser
			},
			//这里是要传递的参数，格式为 data: '{paraName:paraValue}',下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，text，返回值 
				try {
					if (valideData(data)) {
						var custid = data.msg;
						var nouse = $('#nouse').val();
						if (nouse != '1') {
							$('#gg').datagrid("insertRow", {
								index: 0,
								row: {
									ROWNUMBER: 1,
									CUSTID: custid,
									CUSTNAME: custname,
									LINKMAN: linkman,
									REMARK: remark,
									BANKNAME: bankname,
									ADDRESS: address,
									MOBILE: mobile,
									TEL: tel,
									DISCOUNT: discount,
									POSTCODE: postcode,
									TAXNO: taxno,
									HANDMANNAME: $('#ahandmanname').val(),
									CREDITCURR: creditcurr,
									ACCOUNTNO: accountno,
									PRICETYPE: pricetype,
									CREDITOK: creditok,
									CURRBZJ: currbzj,
									NOUSED: '0',
									AREANAME: areaname
								}
							});
							$('#gg').datagrid("refresh");
						}
						$('#adiscount').val('1.00');
						$('#pricetype').val('0');
						$('#acustname,#alinkman,#aremark,#abankname,#aaddress,#amobile,#atel,#apostcode,#ataxno,#acreditcurr,#acurrbzj,#aaccountno,#aareaname,#ahandmanid,#ahandmanname').val('');
						$('#acustname').focus();
						areasearch();
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
//修改用户资料
function updatecustomer() {
	var custid = $('#ucustid').val();
	var custname = $('#ucustname').val();
	var linkman = $('#ulinkman').val();
	var remark = $('#uremark').val();
	var bankname = $('#ubankname').val();
	var address = $('#uaddress').val();
	var mobile = $('#umobile').val();
	var tel = $('#utel').val();
	var discount = $('#udiscount').val();
	var postcode = $('#upostcode').val();
	var taxno = $('#utaxno').val();
	var creditcurr = $('#ucreditcurr').val();
	var currbzj = $('#ucurrbzj').val();
	var accountno = $('#uaccountno').val();
	var pricetype = $('#upricetype').val();
	var areaname = $('#uareaname').val();
	var handmanid = $('#uhandmanid').val();
	var creditok;
	if ($('#ucreditoks').prop('checked')) {
		creditok = '1';
	} else {
		creditok = '0';
	}
	var noused;
	if ($('#unouse').prop('checked')) {
		noused = "1";
	} else {
		noused = "0";
	}
	var alluser = 0;
	if($("#ualluser").prop("checked")&&(levelid == 0 || levelid == 5))
		alluser=1;
	alertmsg(2);
	$.ajax({
		type: 'POST',
		//访问WebService使用Post方式请求 
		url: '/skydesk/fyjerp',
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: 'updatecustomerbyid',
			custid: custid,
			custname: custname,
			linkman: linkman,
			remark: remark,
			bankname: bankname,
			address: address,
			mobile: mobile,
			tel: tel,
			handmanid: handmanid,
			discount: discount,
			postcode: postcode,
			taxno: taxno,
			creditcurr: creditcurr,
			accountno: accountno,
			pricetype: pricetype,
			creditok: creditok,
			noused: noused,
			currbzj: currbzj,
			areaname: areaname,
			alluser: alluser
		},
		//这里是要传递的参数，格式为 data: '{paraName:paraValue}',下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，text，返回值 
			if (valideData(data)) {
				var index = $('#uindex').val();
				var nouse = $('#nouse').val();
				if (nouse == '0' && noused == '1') {
					$('#gg').datagrid("deleteRow", index);
				} else {
					$('#gg').datagrid("updateRow", {
						index: index,
						row: {
							CUSTID: custid,
							CUSTNAME: custname,
							LINKMAN: linkman,
							REMARK: remark,
							BANKNAME: bankname,
							ADDRESS: address,
							MOBILE: mobile,
							TEL: tel,
							DISCOUNT: discount,
							POSTCODE: postcode,
							TAXNO: taxno,
							HANDMANNAME: $('#uhandmanname').val(),
							CREDITCURR: creditcurr,
							ACCOUNTNO: accountno,
							PRICETYPE: pricetype,
							CREDITOK: creditok,
							CURRBZJ: currbzj,
							NOUSED: noused,
							AREANAME: areaname
						}
					});
				}
				$('#gg').datagrid("refresh");
				$('#ud').dialog('close');
				areasearch();
			}
		}
	});
}
function disabledcust(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var custid = row.CUSTID;
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
				erpser: "updatecustomerbyid",
				custid: custid,
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
function delcust(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var custid = row.CUSTID;
		$.messager.confirm(dialog_title, '您确认要删除客户' + row.CUSTNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delcustomerbyid",
						custid: custid
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
function setcustbrandt() {
	$('#brandpricepp').createPage({
		current: 1,
		backFn: function(p) {
			getcustbrand(p.toString());
		}
	});
	$('#brandpricet').datagrid({
		height: 350,
		idField: 'BRANDID',
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		pageSize: 20,
		columns: [[{
			field: 'BRANDID',
			title: 'ID',
			width: 200,
			hidden: true
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
				return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
			}
		},
		{
			field: 'BRANDNAME',
			title: '品牌',
			fixed: true,
			align: 'center',
			halign: 'center',
			width: 100
		},
		{
			field: 'PRICETYPE',
			title: '价格方式',
			fixed: true,
			width: 100,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'DISCOUNT',
			title: '折扣',
			fixed: true,
			width: 100,
			hidden: true
		},
		{
			field: 'PRICETYPE0',
			title: '价格方式',
			fixed: true,
			width: 130,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var pricetype = Number(row.PRICETYPE);
				if (isNaN(pricetype)) return "";
				var selected = ["", "", "", "", "", ""];
				selected[pricetype] = "selected";
				var html = '<select class="changecustbrand select" data-brandidpri="' + Number(row.BRANDID) + '" onchange="changecustbrand(this)" >' + '<option value="0" ' + selected[0] + '>零售价</option>' + '<option value="1" ' + selected[1] + '>售价一</option>' + '<option value="2" ' + selected[2] + '>售价二</option>' + '<option value="3" ' + selected[3] + '>售价三</option>' + '<option value="4" ' + selected[4] + '>批发价</option>' + '<option value="5" ' + selected[5] + '>打包价</option>' + '</select>';
				return html;

			}
		},
		{
			field: 'DISCOUNT0',
			title: '折扣',
			fixed: true,
			width: 130,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var disc = Number(row.DISCOUNT);
				if (isNaN(disc)) return "";
				var html = '<input class="changecustbrand input" type="text" data-brandiddis="' + Number(row.BRANDID) + '" value="' + disc.toFixed(2) + '" onchange="changecustbrand(this)" />';
				return html;
			}
		}]],
		toolbar: '#brandpricesbtn'
	});
}
function openbranddialog() {
	$('#barndpriced').dialog('open');
	getcustbrand('1');
}
function getcustbrand(page) {
	var custid = $('#ucustid').val();
	var findbox = $('#custbrandfindbox').val();
	var pricetype0 = $('#upricetype').val();
	var discount0 = $('#udiscount').val();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listcustbrand",
			custid: custid,
			findbox: findbox,
			pricetype0: pricetype0,
			discount0: discount0,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				var total = data.total;
				$("#brandpricepp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#brandpricet').datagrid('loadData', data);
			}
		}
	});
}
function changecustbrand(obj) {
	var brandid = 0;
	var pricetype = 0;
	var discount = 0;
	if ($(obj).hasClass('select')) {
		brandid = $(obj).data('brandidpri');
		pricetype = obj.value;
		discount = $('input[data-brandiddis=' + brandid + ']').val();
	} else {
		brandid = $(obj).data('brandiddis');
		pricetype = $('select[data-brandidpri=' + brandid + ']').val();
		discount = obj.value;
	}
	if (brandid > 0) {
		writecustbrand(brandid, pricetype, discount);
	} else {
		alerttext('品牌ID无效');
	}
}
function writecustbrand(brandid, pricetype, discount) {
	// 	var pricetype0 = $('#upricetype').val();
	// 	var discount0 = $('#udiscount').val();
	var custid = $('#ucustid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writecustbrand",
			custid: custid,
			brandid: brandid,
			pricetype: pricetype,
			discount: discount
			// 			pricetype0: pricetype0,
			// 			discount0: discount0
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				$('#brandpricepp').refreshPage();
			}
		}
	});
}
function openomuserd(index){
	var $dg = $('#gg');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	if(row){
		$('#ucustid').val(row.CUSTID);
		$('#omuserd').dialog('open');
		if($('#omusert').data().datagrid==undefined){
			setomusert();
		}
		getomuserlist(1);
	}
}
function setomusert(){
	$('#omuserpp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	getomuserlist(p);
        }
	 });
	$('#omusert').datagrid(
		{
			idField : 'OMEPID',
			height: $('#omuserd').height()-50,
			fitColumns: true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			frozenColumns:[[{
				field : 'OMEPID',title : '用户ID',width : 200,fieldtype:"G",expable: true,hidden:true
			},{
				field : 'CUSTID',title : 'CUSTID',width : 200,hidden:true
			},{
				field : 'CUSTNAME',title : '客户名称',width : 200,expable: true,hidden:true
			},{
				field : 'SATETAG',title : '登录标记',width : 200,hidden:true
			},{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				field : 'OMEPNAME',	title : '用户姓名',width : 100,fixed:true,align:'center',halign:'center'
			}]],
			columns: [ [
			{
				field : 'MOBILE',title : '移动电话',width : 160,fixed:true,align:'center',halign:'center'
			},{
				field : 'REMARK',title : '备注',width : 120,fixed:true,align:'center',halign:'center'
			},{
				field : 'OMEPNO',title : '用户账号',width : 100,fixed:true,align:'center',halign:'center'
			}, {
				field : 'PASSWORD',title : '密码',width : 160,fixed:true,align:'center',halign:'center'
			}, {
				field : 'STATETAG',title : '登录权限',width : 80,fieldtype:"G",fixed:true,align:'center',halign:'center',
				formatter: function(value, row, index) {
					var nouse = Number(value);
					if(!glyqx){
						if(nouse==1) return "不允许";
						else return "允许";
					}else{
						var checked = "";
						if (nouse == 1) checked = "checked";
						if (isNaN(nouse)) return "";
						return '<input type="checkbox" id="nousedomuser_check_' + index + '" class="m-btn" onchange="changeomuserldl(' + index + ')" ' + checked + '>';
					}
				}
			}, {
				field : 'ACTION',title : '操作',width : 80,fixed:true,align:'center',halign:'center',hidden: !glyqx,
				formatter: function(value, row, index) {
					var stg = Number(row.STATETAG);
					var html = "";
					html +='<input type="button" class="m-btn" value="删除" onclick="delomuser(' + index + ')">';
					if(stg==3)
					html +='<input type="button" class="m-btn" value="解锁" onclick="orderunlock(' + index + ')">';
					return html;
				}
			}]],
			onDblClickRow: function(index,row){
				if(glyqx) updateomuserd();
			},
			toolbar : '#omuser_toolbar'
		}).datagrid("keyCtr","updateomuserd()");
}

//获取订货会用户记录
function getomuserlist(page){
	$('#omusert').datagrid('loadData', nulldata);
// 	var omid = $('#uomid').val();
	var custid = Number($('#ucustid').val());
	var findbox = $('#omuserqsfindbox').val();
	if(custid==0||isNaN(custid)){
		alerttext('请选择客户');
		return;
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "listomuser",
			page: page,
			rows: pagecount,
			findbox: findbox,
			sort: 'omepno',
			order: 'asc',
// 			omid: omid,
			custid: custid
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#omuserpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omusertotal').html(data.total);
					$('#omusert').datagrid('loadData', data);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function addomuserd(){
	$('#uomuserd input[type="hidden"],#uomuserd input[type="text"]').val('');
	$('#uuserstatetag').prop('checked',true);
	$('#upassword').val('123456');
	$('#uomuserd').dialog("setTitle","添加成员");
	$('#uomuserd').dialog('open');
	$('#uomepno').focus();
}
function updateomuserd(){
	var $omuserdg = $('#omusert');
	var row = $omuserdg.datagrid('getSelected');
	if(row){
		$('#uomepid').val(row.OMEPID);
		$('#uomepno').val(row.OMEPNO);
		$('#uomepname').val(row.OMEPNAME);
		$('#umobile').val(row.MOBILE);
		$('#upassword').val(row.PASSWORD);
		$('#uuserremark').val(row.REMARK);
		$('#ujs').val(row.JS);
		$('#ujhamt').val(Number(row.JHAMT));
		$('#ujhcurr').val(row.JHCURR);
		$('#ujhnum').val(Number(row.JHNUM));
		if(Number(row.STATETAG)==1)
			$('#uuserstatetag').prop('checked',true);
		else
			$('#uuserstatetag').removeProp('checked');
		$('#uomuserd').dialog("setTitle","编辑成员");
		$('#uomuserd').dialog('open');
	}else
		alerttext('请选择！');
}
function saveomuser(){
	alertmsg(2);
// 	var omid = $('#uomid').val();
	var custid = $('#ucustid').val();
	var omepid = Number($('#uomepid').val());
	var omepno = $('#uomepno').val().replace(/ /g,"");
	var omepname = $('#uomepname').val().replace(/ /g,"");
	var mobile = $('#umobile').val().replace(/ /g,"");
	var password = $('#upassword').val().replace(/ /g,"");
	var js = $('#ujs').val();
	var jhamt = Number($('#ujhamt').val());
	var jhcurr = Number($('#ujhcurr').val());
	var jhnum = Number($('#ujhnum').val());
	var password = $('#upassword').val();
	var remark = $('#uuserremark').val();
	var statetag = 0;
	if($('#uuserstatetag').prop('checked'))
		statetag = 1;
	if(omepname.length==0){
		alerttext('请填写用户姓名');
		return;
	}
	if(password.length<6){
		alerttext('请填写长度大于6的用户密码');
		return;
	}
	var erpser= "addomuser";
	if(omepid>0)
		erpser= "updateomuser";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: erpser,
// 			omid: omid,
			omepid: omepid,
			omepno: omepno,
			omepname: omepname,
			mobile: mobile,
			password: password,
			js: 0,
// 			jhamt: jhamt,
// 			jhcurr: jhcurr,
// 			jhnum: jhnum,
			remark: remark,
			statetag: statetag,
			custid: custid
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					if(omepid>0){
						$('#uomuserd').dialog('close');
					}else{
						$('#uomuserd input[type="hidden"],#uomuserd input[type="text"]').val('');
						$('#upassword').val('123456');
						$('#uepname').focus();
					}
					$("#omuserpp").refreshPage();
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//修改登录状态
function changeomuserldl(index){
// 	var omid = $('#uomid').val();
	var $omdg = $('#omusert');
	var rows = $omdg.datagrid('getRows');
	var row = rows[index];
	var omepid = row.OMEPID;
	var $check = $('#nousedomuser_check_' + index);
	var statetag = 0;
	if ($check.prop('checked')) statetag = 1;
	if(statetag==0)
		msg="您确认该订货会不允许此用户" + row.OMEPNAME + "登录吗？";
	else 
		msg="您确认该订货允许此用户" + row.OMEPNAME + "登录订货？";
	$.messager.confirm(dialog_title, msg, function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: "updateomuser",
// 					omid: omid,
					omepid: omepid,
					statetag: statetag
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							$omdg.datagrid('updateRow', {
								index: index,
								row: {
									STATETAG: statetag
								}
							});
						}
						$omdg.datagrid('refresh');
					}catch (e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
				}
			});
		}else $omdg.datagrid('refresh');
	});
}
function delomuser(index){
	var $dg = $('#omusert');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	var omepid = row.OMEPID;
	var omepno = row.OMEPNO;
	var omepname = row.OMEPNAME;
	if(row){
		$.messager.confirm(dialog_title, '您确认删除'+omepno+','+omepname+'用户？', function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type : "POST", //访问WebService使用Post方式请求 
					url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data : {
						erpser: "delomuser",
						omepid: omepid
					}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try{
							if(valideData(data)){
								$("#omuserpp").refreshPage();
							}
						}catch (e) {
							// TODO: handle exception
							console.error(e);top.wrtiejsErrorLog(e);
						}
					}
				});
			}
		});
	}
}
function orderunlock(index){
	var $dg = $('#omusert');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	var omepid = row.OMEPID;
	var omepno = row.OMEPNO;
	var omepname = row.OMEPNAME;
	if(row){
		$.messager.confirm(dialog_title, '您确认解锁'+omepno+','+omepname+'用户？', function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type : "POST", //访问WebService使用Post方式请求 
					url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data : {
						erpser: "orderunlock",
						omepid: omepid
					}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try{
							if(valideData(data)){
								$("#omuserpp").refreshPage();
							}
						}catch (e) {
							// TODO: handle exception
							console.error(e);top.wrtiejsErrorLog(e);
						}
					}
				});
			}
		});
	}
}
function getcustep(page) {
	var custid = $('#ucustid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "custemployelist",
			custid: custid,
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
function setepd(custid) {
	$('#ucustid').val(custid);
	$('#epsqd').dialog('open');
	getcustep('1');
}
function setcustep(epid, value,index) {
	alertmsg(2);
	var row = $("#gg").datagrid("getSelected");
	var custid = $('#ucustid').val();
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
	var custid = $('#ucustid').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writeallcustemploye",
			custid: custid,
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
			getcustep(p);
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
				setcustep(row.EPID, '1',index);
			}
		},
		onUncheck: function(index, row) {
			if (check) {
				setcustep(row.EPID, '0',index);
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
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>