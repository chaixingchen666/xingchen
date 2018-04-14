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
<title>会员档案</title>
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
<body class="easyui-layout layout panel-noscroll">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input class="btn1" type="button" value="添加" onclick="addvipd()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatevipd()">
	<input type="text" placeholder="输入姓名、卡号、会员类型<回车搜索>" data-end="yes" class="ipt1" id="qsvipvalue" maxlength="20" data-enter="getvipdata()">
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()">
</div>
<div class="fl hide" id="serbtn">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchvip();toggle();"> 
	<input type="button" class="btn2"  value="清空搜索条件" onclick="resetsearch()">
</div>
<div class="fr">
<input type="button" class="btn3" value="设置" onclick="opensetcolumnd('#gg')">
	<span class="sepreate hide only_hrsh"></span>
<input type="button" class="btn3 hide only_hrsh" value="同步会员数据" onclick="yky_syncvip()">
	<span class="sepreate"></span>
	<input type="button" class="btn3"  value="导入" onclick="$('#uploadxlsd').dialog('open')">
	<span class="sepreate"></span>
	<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'buy',0);">
	</div>
</div>
		<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchvip();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">会员类型</div>
<div class="text"><input type="text" name="svtname" id="svtname" class="edithelpinput viptp_help" data-value="#svtid" placeholder="<选择或输入>"><span onclick="selectviptype('search');"></span>
				   <input type="hidden" id="svtid" name="svtid">
</div>
</div>
<div class="s-box"><div class="title">姓名</div>
<div class="text"><input type="text" name="sguestname" id="sguestname" style="width: 160px; height: 25px" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">移动电话</div>
<div class="text"><input type="text" name="smobile" id="smobile" style="width: 160px; height: 25px"  maxlength="11" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">卡号</div>
<div class="text"><input type="text" name="svipno" id="svipno" style="width: 160px; height: 25px" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput wid145 hig25 house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selecthouse('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">状态</div>
<div class="text">
 	<select class="sele1" id="susetag">
	<option value="3" selected>所有</option>
	<option value="0">正常</option>
	<option value="1">禁用</option>
	<option value="2">挂失</option>
	</select>
</div>
</div>
</div>
</div>
			</div>
	<table id="gg" style="overflow: hidden; height:900px;"></table>
	<!-- 分页 -->
	<div class="page-bar">
		<div
			 class="page-total">
			共有<span id="pp_total">0</span>条记录
			<span class="ml10" style="color:#ff7900">积分合计：<span id="totalcent">0</span></span>
			<span class="ml10" style="color:#ff7900">储值合计：<span id="totalcurr">0.00</span></span>
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
	<!-- 修改 ------------------------------------------------->
	<div id="ud" title="Dialog" style="width: 600px; height: 420px;" class="easyui-dialog" closed=true>
	<div style="overflow:auto; height: 330px;" >
	  <table  width="600px" border="0px" cellspacing="10">
	   <tr><td width="80px" class="header skyrequied" align="right">姓名</td>
			<td align="left">
				<input type="hidden" id="uguestid" name="uguestid">
				<input type="text" class="wid160 hig25" id="uguestname" name="uguestname" placeholder="<输入>"/></td>
			<td width="80px"  class="header" align="right">性别</td>
				<td align="center"><input type="radio" name="sex" id="uman" checked>男
				<input type="radio" name="sex" id="uwoman">女
				</td>
			</tr>
	    <tr>
	    <td width="80px" class="header skyrequied" align="right">卡号</td>
			<td align="left">
				<input type="text" name="uvipno" id="uvipno" class="wid160 hig25" placeholder="<输入>"></td>
				<td width="80px" class="header" align="right">移动电话</td>
			<td align="left">
				<input type="text" name="umobile" id="umobile" class="wid160 hig25" maxlength="11" placeholder="<输入>"></td>
			</tr>
	      <tr><td width="80px" class="header skyrequied" align="right">会员类型</td>
			<td align="left"><input type="text" name="uvtname" id="uvtname" class="edithelpinput viptp_help" data-value="#uvtid" placeholder="<输入或选择>">
			    <span onclick="selectviptype('update')"></span> <input type="hidden" id="uvtid" name="uvtid"></td>
			<td width="80px" class="header" align="right">有效期</td>
				<td align="left"><input class="easyui-datebox" name="uvaliddate" id="uvaliddate" style="width: 163px; height: 28px; margin-left: 10px"/>
				</td>
			</tr>
		<tr><td width="80px" class="header" align="right">出生日期</td>
				<td align="left"><input class="easyui-datebox" name="ubirthday" id="ubirthday" style="width: 163px; height: 28px; margin-left: 10px"/>
				</td>
			<td  width="80px" class="header" align="right">身高</td>
	            <td align="left"><select style="width: 163px; height: 28px" id="uheight" name="uheight">
	                        <option value="" selected="selected"></option>
							<option value="150以下">150以下</option>
							<option value="150-155">150-155</option>
							<option value="155-160">155-160</option>
							<option value="160-165">160-165</option>
							<option value="165-170">165-170</option>
							<option value="170-175">170-175</option>
							<option value="175-180">175-180</option>
							<option value="180-185">180-185</option>
							<option value="185以上">185以上</option>
					</select></td></tr>
			<tr><td class="header" width="80px" align="right">店铺</td>
					<td align="left">
					<input class="edithelpinput wid145 hig25 house_help" data-value="#uhouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="uhousename" name="uhousename"><span  onclick="selecthouse('updateep')"></span> 
					   <input type="hidden" id="uhouseid" name="uhouseid"></td>
				<td class="header" width="80px" align="right">尺码</td>
				<td align="left"><input type="text" id="ufitsize" class="wid160 hig25" name="ufitsize" placeholder="<输入>"></td>
			</tr>
			<tr><td class="header" width="80px" align="right">状态</td>
			    <td>
			    <select class="sele1" id="uusetag">
			    <option value="0">正常</option>
			    <option value="1">禁用</option>
			    <option value="2">挂失</option>
			    </select>
			    </td>
			</tr>
			<tr id="vipjfcurrtr"><td class="header" width="80px" align="right">积分余额</td>
			    <td><input type="text" id="ubalcent" name="ubalcent" class="wid160 hig25" placeholder="<输入>" readonly="readonly"></td>
			    <td class="header" width="80px" align="right">储值余额</td>
			    <td><input type="text" id="ubalcurr" name="ubalcurr" class="wid160 hig25" placeholder="<输入>" readonly="readonly"></td>
			</tr>
		<tr><td width="80px" class="header" align="right">爱好</td>
				<td colspan="3" align="left">
				<input type="text" name="ulikesome" id="ulikesome"  style="width:455px; height: 25px" placeholder="<输入>"></td></tr>
			<tr><td class="header" align="right" width="80px">备注</td>
	        <td colspan="3" align="left">
	        <input type="text" id="uremark" name="uremark" maxlength="200" style="width:455px; height :25px" placeholder="允许换行输入，最多200个字符"></td>
		</tr>
	   </table></div>
	    <div class="dialog-footer" style="text-align: center;">
			<input type="button" id="savebtn" class="btn1"  name="updatevip" value="保存" onclick="savevip()">
			<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
		</div>
	</div>
	<!-- 会员积分------------------- -->
	<div id="jfd" title="会员积分" style="width: 100%; height: 100%" class="easyui-dialog" closed=true>
	    <table  id="ggjf_toolbar" border="0" cellspacing="10">
	      <tr><td class="header">姓名</td><td><input type="text" class="wid160 hig25" id="jfguestname" name="jfguestname" readonly></td>
	      <td class="header">会员卡号</td><td><input class="wid160 hig25" type="text" id="jfvipno" name="jfvipno" readonly></td>
	      <td class="header">积分余额</td><td><input class="wid160 hig25" type="text" id="jfbalcent" name="jfbalcent" readonly="readonly">
	      <input type="hidden" id="jfvipid"></td>
	      <td><input type="button" class="btn1" value="积分增加"  onclick="addvipjfd(0)"></td>
	      <td><input type="button" class="btn1" value="积分减少" onclick="addvipjfd(1)"></td>
	    </table>
	    <table id="ggjf" style="overflow: hidden;"></table>
	    <!-- 分页 -->
	<div class="page-bar">
		<div  class="page-total">
			共有<span id="totaljf">0</span>条记录
		</div>
		<div id="ppjf" class="tcdPageCode page-code"></div>
	</div>
	</div>
	<div id="jfud" title="会员积分" style="width: 340px; height: 340px" class="easyui-dialog" closed=true>
	    <table width="300" border="0" cellspacing="10">
	      <tr><td class="header"  width="70px" align="right">日期</td>
	      <td width="180px" align="left"><input type="text" class="wid160 hig25" id="jfnotedate" name="jfnotedate"></td></tr>
	      <tr><td class="header"  width="70px" align="right">店铺</td>
					<td width="180px" align="left"><input class="edithelpinput wid145 hig25 house_help" data-value="#jfhouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="jfhousename" name="jfhousename"><span  onclick="selecthouse('vipjf')"></span> 
					   <input type="hidden" id="jfhouseid" name="jfhouseid"></td></tr>
		   <tr id="trcent"><td class="header"  width="70px" align="right">积分</td>
		   <td width="180px" align="left"><input type="text" class="wid160 hig25" id="jfcent" name="jfcent"></td></tr>
	      <tr><td class="header" width="70px" align="right">备注</td>
	      <td width="180px" align="left"><input type="text" class="wid160 hig25" id="jfremark"></td></tr>
	    </table>
	    <div class="dialog-footer" style="text-align: center;">
			<input type="button" class="btn1" value="确认增加" id="addjf" onclick="addguestcent(0)">
			<input type="button" class="btn1" value="确认减少" id="reducejf" onclick="addguestcent(1)">
		</div>
	</div>
	<!--会员储值------------------- -->
	<div id="czd" title="会员储值" style="width: 100%; height: 100%" class="easyui-dialog" closed=true>
	    <table id="ggcz_toolbar" cellspacing="10">
	      <tr><td class="header">姓名</td><td><input type="text" class="wid160 hig25" id="czguestname" name="czguestname" readonly></td>
	      <td class="header">会员卡号</td><td><input class="wid160 hig25" type="text" id="czvipno" name="czvipno" readonly></td>
	      <td class="header">储值余额</td><td><input class="wid160 hig25" type="text" id="czbalcurr" name="czbalcurr" readonly="readonly">
	      </td>
	       <td><input type="button" id="addvipcz" class="btn1" value="储值增加"  onclick="addvipczd(0)"></td>
	       <td><input type="button" class="btn1" id="cutvipcz" value="储值消费"  onclick="addvipczd(1)"></td>
	       <td><input type="button" class="btn1" value="选择打印端口"  onclick="selprint(9001)"></td>
	       <td><input type="button" class="btn1" id="printvipcz" value="打印"  onclick="czprint()"></td>
	    </table>
	    <table id="ggcz" style="overflow: hidden;"></table>
	    <!-- 分页 -->
	<div class="page-bar">
		<div  class="page-total">
			共有<span id="totalcz">0</span>条记录
		</div>
		<div id="ppcz" class="tcdPageCode page-code"></div>
	</div>
	</div>
	<div id="czud" title="会员储值" style="width: 340px; height: 420px" class="easyui-dialog" closed=true>
	    <table width="300px" border="0px" cellspacing="20">
	      <tr><td class="header"  width="70px" align="right">日期</td>
	      <td width="180px" align="left"><input type="text" class="wid160 hig25" id="cznotedate" name="cznotedate"></td></tr>
	      <tr><td class="header"  width="70px" align="right">店铺</td>
					<td width="180px" align="left"><input class="edithelpinput house_help" data-value="#czhouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="czhousename" name="czhousename"><span  onclick="selecthouse('vipcz')"></span> 
					   <input type="hidden" id="czhouseid" name="czhouseid"></td></tr>
	      <tr class="xfpay"><td class="header"  width="70px" align="right">结算方式</td>
		<td width="180px" align="left"><input type="text" class="edithelpinput pw_help" data-value="#czpayid" maxlength="20" placeholder="<输入或选择>" id="czpayname"
					name="czpayname"/> <span onclick="Paywaydata('vipcz')"></span>
					<input type="hidden" id="czpayid" name="czhpayid"> 
		             <input type="hidden" id="czpayno" name="czpayno"></td></tr>
	      <tr class="xfpay"><td class="header"  width="70px" align="right" id="td_jkcurr">缴款金额</td>
	      <td width="180px" align="left"><input class="wid160 hig25" type="text" id="czpaycurr" name="czpaycurr"></td></tr>
	      <tr><td class="header"  width="70px" align="right" id="td_czcurr">储值金额</td>
	      <td width="180px" align="left"><input type="text" id="czcurr" class="wid160 hig25"></td></tr>
	      <tr><td class="header" width="70px" align="right">备注</td>
	      <td width="180px" align="left"><input type="text" class="wid160 hig25" id="czremark"></td></tr>
	      <tr><td class="header" width="70px" align="right"></td>
	      <td width="180px" align="left"><label><input type="checkbox" id="czisprint">后台打印</label></td></tr>
	    </table>
	    <div class="dialog-footer" style="text-align: center">
			<input type="button" class="btn1" value="确认增加" id="addcz" onclick="addguestcurr(0)">
			<input type="button" class="btn1" value="确认减少" id="reducecz" onclick="addguestcurr(1)">
		</div>
	</div>
	<!-- xls上传窗 -->
<div id="uploadxlsd" title="请选择上传选项" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 80px 10px 10px 10px;text-align: center;">
<input type="button" class="btn1 wid160" value="上传会员档案" onclick="openuploadvip(0)"> 
<input type="button" class="btn1 wid160" value="上传会员余额" onclick="openuploadvip(1)"> 
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
var levelid = getValuebyName("GLEVELID");
var pgid = "pg1804";
setqxpublic();
$(function() {
	
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getviplist(p);
		}
	});
	$('#gg').datagrid({
		width: '100%',
		idField: 'GUESTID',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		sortName: "GUESTNAME",
		sortOrder: "asc",
		onSelect: function(rowIndex, rowData) {
			if (rowData.SEX == '男') {
				$('#uman').prop('checked', true);
			} else {
				$('#uwoman').prop('checked', true);
			}
			dqindex = rowIndex;
			$('#uguestname,#jfguestname,#czguestname').val(rowData.GUESTNAME);
			$('#uvipno,#jfvipno,#czvipno').val(rowData.VIPNO);
			$('#jfbalcent').val(rowData.BALCENT);
			$('#czbalcurr').val(rowData.BALCURR);
			$('#umobile').val(rowData.MOBILE);
			$('#uvtid').val(rowData.VTID);
			$('#uvtname').val(rowData.VTNAME);
			$('#uvaliddate').datebox('setValue', rowData.VALIDDATE);
			$('#ubirthday').datebox('setValue', rowData.BIRTHDAY);
			$('#uheight').val(rowData.STATURE);
			$('#ulikesome').val(rowData.LIKESOME);
			$('#ubalcent').val(rowData.BALCENT);
			$('#ubalcurr').val(rowData.BALCURR);
			$('#uremark').val(rowData.REMARK);
			$('#uguestid').val(rowData.GUESTID);
			$('#uusetag').val(rowData.USETAG);
			$('#uhouseid').val(rowData.HOUSEID);
			$('#uhousename').val(rowData.HOUSENAME);
			$('#ufitsize').val(rowData.FITSIZE);
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatevipd();
		},
		onSortColumn: function(sort, order) {
			$('#pp').refreshPage();
		},
		frozenColumns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if(value=="合计") return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		}]],
		columns: [getpgcolumnarray([
		{
			field: 'GUESTID',
			title: '会员ID',
			fildtype: "G",
			width: 80,
			expable: true,
			hidden: true
		},
		{
			field: 'GUESTNAME',
			title: '会员姓名',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SEX',
			title: '性别',
			width: 60,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'VTNAME',
			title: '会员类型',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'MOBILE',
			title: '移动电话',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'VIPNO',
			title: '卡号',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 120,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'BALCENT',
			title: '积分余额',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: amtfm
		},
		{
			field: 'BALCURR',
			title: '储值余额',
			width: 80,
			fixed: true,
			sortable: true,
			formatter: currfm,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'VALIDDATE',
			title: '有效日期',
			align: 'center',
			halign: 'center',
			width: 100,
			fixed: true,
			sortable: true,
			formatter: function(value, row, index) {
				if(value)
				return value.substring(0, 10);
			}
		},
		{
			field: 'BIRTHDAY',
			title: '出生日期',
			align: 'center',
			halign: 'center',
			width: 100,
			sortable: true,
			fixed: true
		},
		{
			field: 'LIKESOME',
			title: '爱好',
			width: 150,
			fixed: true,
			sortable: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'STATURE',
			title: '身高',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'FITSIZE',
			title: '尺码',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'REMARK',
			title: '备注',
			width: 150,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CREATEDATE',
			title: '建档日期',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LASTDATE',
			title: '最近更新',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LASTOP',
			title: '最近建档人',
			width: 100,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'USETAG',
			title: '状态',
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value == 0) return "正常";
				else if (value == 1) return "禁用";
				else if (value == 2) return "挂失";
				else return "";
			}
		},
		{
			field: 'ACTION',
			title: '操作',
			align: 'center',
			halign: 'center',
			width: 200,
			fixed: true,
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" value="删除" onclick="delguestvip(' + index + ')">' + '<input type="button" class="m-btn" value="会员积分" onclick="openvipjf(' + index + ')">' + '<input type="button" class="m-btn" value="会员储值" onclick="openvipcz(' + index + ')">';
			}
		}])],
		toolbar: "#toolbars",
		pageSize: 20
	}).datagrid("keyCtr", "updatevipd()");
	getvipdata();
	//ggjf gg积分
	$('#ppjf').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			guestcentlist(p);
		}
	});
	$('#ggjf').datagrid({
		width: '100%',
		idField: 'ID',
		height: $('body').height() - 110,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		onDblClickRow: function(rowIndex, rowData) {
			var noteno = rowData.XSNOTENO;
			if (noteno != '' && noteno != undefined) {
				var notetype = noteno.substring(0, 2);
				opendetaild(notetype, noteno);
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
				var p = $("#ppjf").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'NOTEDATE',
			title: '日期',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ZY',
			title: '类别',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CENT0',
			title: '积分',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'XSNOTENO',
			title: '业务单号',
			align: 'center',
			halign: 'center',
			width: 120,
			fixed: true
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'REMARK',
			title: '备注',
			align: 'left',
			halign: 'center',
			fixed: true,
			width: 150
		},{
			field: 'OPERANT',
			title: '收银员',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 70
		}]],
		toolbar: '#ggjf_toolbar'
	});
	//积分储值
	$('#ppcz').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			guestcurrlist(p);
		}
	});
	$('#ggcz').datagrid({
		width: '100%',
		idField: "ID",
		height: $('body').height() - 110,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		showFooter: true,
		onDblClickRow: function(rowIndex, rowData) {
			var noteno = rowData.XSNOTENO;
			if (noteno != '' && noteno != undefined) {
				var notetype = noteno.substring(0, 2);
				opendetaild(notetype, noteno);
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
				var p = $("#ppcz").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
		{
			field: 'NOTEDATE',
			title: '日期',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ZY',
			title: '类别',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'CURR0',
			title: '金额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYNAME',
			title: '付款方式',
			fixed: true,
			width: 80,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'PAYCURR',
			title: '付款金额',
			width: 100,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'XSNOTENO',
			title: '业务单号',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 120
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},{
			field: 'REMARK',
			title: '备注',
			align: 'left',
			halign: 'center',
			fixed: true,
			width: 150
		},{
			field: 'OPERANT',
			title: '收银员',
			align: 'center',
			halign: 'center',
			fixed: true,
			width: 70
		}]],
		toolbar: '#ggcz_toolbar'
	});
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendguestvip&server=buy",
		xlsmobanname: "vipinfo",
		channel: "vipinfo"
	});
	if(levelid==7||levelid==1||levelid==2){
		$('#czhousename,#jfhousename').next().hide();
		$('#czhousename,#jfhousename').attr('readonly',true);
	}
	$("#czpaycurr").keyup(function(e){
		if(e.which==13){
			var paycurr = Number($(this).val());
			if(paycurr>0){
				$('#czcurr').val(paycurr);
			}
		}
	});
	var accid = Number(getValuebyName("ACCID"));
	if(accid==17873){
		$(".only_hrsh").show();
	}
});
//上传会员档案或者会员积分储值余额
function openuploadvip(tp) {
	if (tp == 0) {
		setuploadserver({
			server: "/skydesk/fyimportservice?importser=appendguestvip&server=buy",
			xlsmobanname: "vipinfo",
			channel: "vipinfo"
		});
	} else if (tp == 1) {
		setuploadserver({
			server: "/skydesk/fyimportservice?importser=appendguestcentorcurr&server=buy",
			xlsmobanname: "vipcurr",
			channel: "vipcurr"
		});
	}
	openimportd();
}
//添加框
function addvipd() {
	$("#ud table input").val("");
	var myDate = new Date();
	myDate.setDate(myDate.getDate(top.getservertime())+730);
	$('#uvaliddate').datebox("setValue",myDate.Format("yyyy-MM-dd"));
	$("#man").prop("checked");
	$("#vipjfcurrtr").hide();
	$('#ud').dialog("setTitle", "添加会员").dialog('open');
}
//修改框
function updatevipd() {
	var row = $('#gg').datagrid('getSelected');
	var remark;
	if (row) {
		$("#vipjfcurrtr").show();
		$('#ud').dialog("setTitle", "编辑会员").dialog('open');
	}
}
//高级搜索
var searchb = false;
function toggle() {
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
	setTimeout(function() {
		changeDivHeight();
	},
	200);
}
var currentdata = {};
var getviplist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var usetag = $("#susetag").val();
	var sort = options.sortName;
	var order = options.sortOrder;
	currentdata["erpser"] = "guestviplist";
	currentdata["usetag"] = usetag;
	currentdata["fieldlist"] = "a.validdate,a.remark,a.guestid,a.guestname,a.sex,a.shortname,a.vipno,a.accid,a.vtid,a.mobile,a.birthday,a.statetag,a.usetag,a.stature,a.fitsize,a.likesome,a.balcent,a.balcurr,a.houseid,a.createdate,a.lastdate,a.lastop,b.vtname,c.housename";
	currentdata["sort"] = sort;
	currentdata["order"] = order;
	currentdata["rows"] = pagecount;
	currentdata["page"] = page;
	$dg.datagrid('loadData', nulldata);
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
					$("#totalcent").html(Number(data.totalcent));
					$("#totalcurr").html(Number(data.totalcurr).toFixed(2));
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
function getvipdata() {
	var value = $('#qsvipvalue').val();
	alertmsg(6);
	currentdata = {
		findbox: value
	};
	getviplist(1);
}
//搜索会员类型
function searchvip() {
	var guestname = $('#sguestname').val();
	var vipno = $('#svipno').val();
	var mobile = $('#smobile').val();
	var vtid = Number($('#svtid').val());
	var houseid = $('#shouseid').val();
	houseid = houseid == "" ? "-1": houseid;
	alertmsg(6);
	currentdata = {
		guestname: guestname,
		vipno: vipno,
		mobile: mobile
	};
	if(houseid>0) currentdata["houseid"] = houseid;
	if(vtid>0) currentdata["vtid"] = vtid;
	getviplist(1);
}
//清除高级搜索数据
function resetsearch() {
	$('#sguestname').val("");
	$('#svtname').val("");
	$('#svtid').val("");
	$('#smobile').val("");
	$('#svipno').val("");
	$('#susetag').val("3");
	$('#shouseid').val("");
	$('#shousename').val("");
	$('#sfitsize').val("");
}
//打开会员积分
function openvipjf(index) {
	$("#gg").datagrid("selectRow", index);
	$("#jfd").dialog("open");
	guestcentlist(1);
}
//获取会员积分数据
function guestcentlist(page) {
	var guestid = Number($("#uguestid").val());
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "guestcentlist",
			guestid: guestid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#ppjf').setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#totaljf').html(data.total);
				$('#ggjf').datagrid('loadData', data);
			}
		}
	});
}
//打开会员储值
function openvipcz(index) {
	$("#gg").datagrid("selectRow", index);
	$("#czd").dialog("open");
	guestcurrlist(1);
}
function guestcurrlist(page) {
	var guestid = Number($("#uguestid").val());
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "guestcurrlist",
			guestid: guestid,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#ppcz').setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#totalcz').html(data.total);
				$('#ggcz').datagrid('loadData', data);
			}
		}
	});
}
function addvipjfd(fs) {
	$('#jfud table input').val("");
	if (fs == 0) {
		$("#addjf").show();
		$("#reducejf").hide();
	} else if (fs == 1) {
		$("#addjf").hide();
		$("#reducejf").show();
	}
	var myDate = new Date(top.getservertime());
	$("#jfnotedate").val(myDate.Format('yyyy-MM-dd hh:mm:ss'));
	$("#jfhousename").val(getValuebyName("HOUSENAME"));
	$("#jfhouseid").val(getValuebyName("HOUSEID"));
	$('#jfud').dialog("setTitle",fs==0?"积分增加":"积分消费").dialog('open');
}
//增加、减少会员积分
function addguestcent(fs) {
	var guestid = $('#uguestid').val();
	var cent = $('#jfcent').val();
	var houseid = $('#jfhouseid').val();
	var remark = $('#jfremark').val();
	var notedate = $('#jfnotedate').val();
	if(houseid==0){
		alerttext("请选择店铺！");
		return;
	}
	if(cent==0){
		alerttext("请输入大于0的积分");
		return;
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addguestcent",
			cent: cent,
			fs: fs,
			houseid: houseid,
			remark: remark,
			guestid: guestid,
			notedate: notedate
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				var id = data.id;
				$('#jfbalcent').val(data.balcent);
				$('#ggjf').datagrid('insertRow', {
					index: 0,
					row: {
						ROWNUMBER: 1,
						REMARK: remark,
						ID: id,
						HOUSEID: houseid,
						HOUSENAME: $('#jfhousename').val(),
						CENT0: cent,
						FS: fs,
						NOTEDATE: notedate,
						OPERANT: getValuebyName("EPNAME")
					}
				});
				var index = $("#gg").datagrid("getRowIndex",guestid);
				$("#gg").datagrid("updateRow",{
					index: index,
					row: {
						BALCENT: data.balcent
					}
				});
				var total = Number($("#totaljf").html());
				if (!isNaN(total)) $("#totaljf").html(total + 1);
				$('#ggjf').datagrid('refresh');
				$('#jfud').dialog('close');
			}
		}
	});
}
function addvipczd(fs) {
	$('#czud table input').val("");
	if (fs == 0) {
		$("#addcz,tr.xfpay").show();
		$("#reducecz").hide();
	} else if (fs == 1) {
		$("#addcz,tr.xfpay").hide();
		$("#reducecz").show();
	}
	var myDate = new Date(top.getservertime());
	$("#cznotedate").val(myDate.Format('yyyy-MM-dd hh:mm:ss'));
	$("#czhousename").val(getValuebyName("HOUSENAME"));
	$("#czhouseid").val(getValuebyName("HOUSEID"));
	if(fs==1){
		$("#td_jkcurr").html("消费金额");
		$("#td_czcurr").html("储值消费");
	}else{
		$("#td_jkcurr").html("缴款金额");
		$("#td_czcurr").html("储值金额");
	}
	$('#czud').dialog("setTitle",fs==0?"储值增加":"储值消费").dialog('open');
}
//会员储值、消费
function addguestcurr(fs) {
	var curr = Number($('#czcurr').val());
	var paycurr = Number($('#czpaycurr').val());
	var payid = $('#czpayid').val();
	var houseid = Number($('#czhouseid').val());
	var remark = $('#czremark').val();
	var notedate = $('#cznotedate').val();
	var guestid = $('#uguestid').val();
	if(houseid==0){
		alerttext("请选择店铺！");
		return;
	}
// 	if(paycurr==0&&fs==0){
// 		alerttext("请输入大于0的缴款金额");
// 		return;
// 	}
	var czisprint = $("#czisprint").prop("checked");
	var ajaxdata = {
			erpser: "addguestcurr",
			curr: curr,
			fs: fs,
			houseid: houseid,
			payid: payid,
			remark: remark,
			guestid: guestid
		};
	if(fs==0) ajaxdata.paycurr = paycurr;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: ajaxdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				$('#czbalcurr').val(data.balcurr);
				$('#ggcz').datagrid('insertRow', {
					index: 0,
					row: {
						ROWNUMBER: 1,
						REMARK: remark,
						ID: data.id,
						PAYID: payid,
						PAYNAME: $('#czpayname').val(),
						HOUSEID: houseid,
						HOUSENAME: $('#czhousename').val(),
						CURR0: curr,
						NOTEDATE: notedate,
						PAYCURR: paycurr,
						FS: fs,
						OPERANT: getValuebyName("EPNAME")
					}
				});
				var index = $("#gg").datagrid("getRowIndex",guestid);
				$("#gg").datagrid("updateRow",{
					index: index,
					row: {
						BALCURR: data.balcurr,
						LASTOP: getValuebyName("EPNAME"),
						LASTDATE: new Date().Format("yyyy-MM-dd hh:mm:ss")
					}
				});
				$('#ggcz').datagrid('refresh');
				var total = Number($("#totalcz").html());
				if (!isNaN(total)) $("#totalcz").html(total + 1);
				$('#czud').dialog('close');
				if(czisprint){
					$('#ggcz').datagrid('selectRow',0);
					czprint();
				}
			}
		}
	});
}
//修改
function savevip() {
	var guestname = $('#uguestname').val();
	var vipno = $('#uvipno').val();
	var mobile = $('#umobile').val();
	var vtid = $('#uvtid').val();
	var vtname = $('#uvtname').val();
	var validdate = $('#uvaliddate').datebox("getValue");
	var birthday = $('#ubirthday').datebox("getValue");
	var stature = $('#uheight').val();
	var likesome = $('#ulikesome').val();
	var fitsize = $('#ufitsize').val();
	var houseid = $('#uhouseid').val();
	var usetag = $('#uusetag').val();
	var sex;
	if ($('#uman').prop('checked')) {
		sex = '男';
	} else {
		sex = '女';
	}
	var remark = $('#uremark').val();
	var balcent = $('#ubalcent').val();
	var balcurr = $('#ubalcurr').val();
	var guestid = Number($('#uguestid').val());
	var erpser = "updateguestvipbyid";
	if (guestid == 0) erpser = "addguestvip";
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: erpser,
			guestname: guestname,
			sex: sex,
			vipno: vipno,
			mobile: mobile,
			vtid: vtid,
			validdate: validdate,
			birthday: birthday,
			usetag: usetag,
			likesome: likesome,
			guestid: guestid,
			houseid: houseid,
			fitsize: fitsize,
			stature: stature,
			remark: remark
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				$('#ud').dialog('close');
				if (guestid == 0) {
					$('#gg').datagrid('insertRow', {
						index: 0,
						row: {
							ROWNUMBER: 1,
							GUESTID: data.msg,
							GUESTNAME: guestname,
							VALIDDATE: validdate,
							REMARK: remark,
							SEX: sex,
							VIPNO: vipno,
							VTID: vtid,
							MOBILE: mobile,
							BIRTHDAY: birthday,
							LIKESOME: likesome,
							USETAG: usetag,
							HOUSEID: houseid,
							HOUSENAME: $('#uhousename').val(),
							FITSIZE: fitsize,
							STATURE: stature,
							VTNAME: vtname
						}
					});
				} else {
					$('#gg').datagrid('updateRow', {
						index: dqindex,
						row: {
							VALIDDATE: validdate,
							REMARK: remark,
							GUESTNAME: guestname,
							SEX: sex,
							VIPNO: vipno,
							VTID: vtid,
							MOBILE: mobile,
							BIRTHDAY: birthday,
							LIKESOME: likesome,
							USETAG: usetag,
							HOUSEID: houseid,
							HOUSENAME: $('#uhousename').val(),
							FITSIZE: fitsize,
							STATURE: stature,
							VTNAME: vtname
						}
					});
				}
				$('#gg').datagrid('refresh');
				$('#ud').dialog('close');
			}
		}
	});
}
//删除
function delguestvip(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var guestid = row.GUESTID;
		$.messager.confirm(dialog_title, '您确认要删除会员' + row.GUESTNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delguestvipbyid",
						guestid: guestid
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
							console.error(e);
							top.wrtiejsErrorLog(e);
						}
					}
				});
			}
		});
	}
}	
function czprint(){
	var row = $("#ggcz").datagrid("getSelected");
	if(row){
		var id = row.ID;
		addbackprint(9001, row.HOUSEID, id, id);
	}else{
		alerttext("请选择需要打印的储值记录！");
	}
}
//胡燃商行专用
function yky_syncvip(){
	$.messager.confirm(dialog_title, '同步一卡易会员数据，这个过程可能有些漫长，是否确认更新？',
			function(r) {
				if (r) {
					alertmsg(2,30*60*60);
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fybuyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "yky_syncvip",
							timeout: 30*60*60,
							guestid: guestid
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									alert("同步结束！");
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
</script>
	    <jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
	<!-- 结算方式帮助列表 -->
	<jsp:include page="../HelpList/PayWayHelpList.jsp"></jsp:include>
	<!-- 会员类型帮助列表 -->
	<jsp:include page="../HelpList/VipTypeHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	    	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
	    <jsp:include page="../HelpList/ColumnDialog.jsp"></jsp:include>
</body>
</html>