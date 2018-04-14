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
<title>发布订货会</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
div.edit_div{
	text-align: center;
}
div.edit_div span {
    display: inline-block;
    margin: 0 5px;
    width: 15px;
    height: 15px;
    line-height: 15px;
    color: #fff;
    background: #ff7900;
    cursor: pointer;
}
div.edit_div span.disabled{
	background: #ddd;
	cursor: not-allowed;
}
div.edit_div input {
    width: 50px;
    height: 25px;
    text-align: center;
}
#omcustd .panel-header,#ompoled .panel-header{
	background: #fff;
}
#omcustd .panel-title,#ompoled .panel-title{
	color: #ff7900;
}
#ompoled .panel-tool{
	height: 30px;
	margin-top: -13px;
}
input[type=file]{
	display: none;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<!-- 表格工具栏 -->
	<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
		<input class="btn1" type="button" value="添加" onclick="addomd()">
		<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updateomd()">
		<input type="text" data-end="yes" placeholder="输入订货会名称或简称<回车搜索>" class="ipt1" id="qsfindbox" maxlength="20">
<!-- 		<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()"> -->
	</div>
	<div class="fl hide" id="searchbtn">
		<input type="button" class="btn2" type="button" value="搜索"
			onclick="serachomdata('1');toggle();"> <input type="button"
			class="btn2" style="width: 100px;" value="清空搜索条件"
			onclick="resetsearch()">
	</div>
	<div class="fr hide">
		<input type="button" class="btn3" style="width: 45px" value="导入" onclick="importxls()"> 
		<span class="sepreate"></span> 
		<input type="button" class="btn3" style="width: 45px" value="导出" onclick="exportxls()">
	</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="serachomdata('1');toggle();">
<div class="search-box">
<!-- <div class="s-box"><div class="title">开始日期</div> -->
<!-- <div class="text"> -->
<%-- <input type="text" name="smindate" id="sbegindate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox"> --%>
<!-- </div> -->
<!-- </div> -->
<!-- <div class="s-box"><div class="title">结束日期</div> -->
<!-- <div class="text"> -->
<%-- <input type="text" name="smaxdate" id="senddate" placeholder="<输入>" style="width: 162px;height:29px" class="easyui-datebox"> --%>
<!-- </div> -->
<!-- </div> -->
<div class="s-box">
<div class="title">主题</div>
<div class="text"><input class="wid160 hig25" id="somname" maxlength="20" type="text" placeholder="<输入>"></div>
</div>
<div class="s-box">
<div class="title">地址</div>
<div class="text"><input class="wid160 hig25" id="saddress" maxlength="20" type="text" placeholder="<输入>"></div>
</div>
<div class="s-box">
<div class="title">内容</div>
<div class="text"><input class="wid160 hig25" id="sremark" maxlength="20" type="text" placeholder="<输入>"></div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden;">
</table>
	<div class="page-bar">
		<div class="page-total">
			共有<span id="total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
<div id="ud" class="easyui-dialog" closed="true" style="width:900px;height:450px;">
<input type="hidden" id="uomid">
<input type="hidden" id="ustatetag">
<input type="hidden" id="uindex">
<div style="text-align:center;">
<table class="table1" cellspacing="10">
<tr>
<td class="header" align="right" width="100">
主题
</td>
<td align="left">
<textarea placeholder="<输入>" name="uomname" id="uomname" maxlength="80"  style="width:380px;height:40px;">
</textarea> 
</td>
<td rowspan="2" class="header" align="right">
品牌LOGO
</td>
<td rowspan="2" align="left">
<div style="width:70px;height:70px;border:1px #ddd solid;border-radius:35px;-webkit-border-radius:35px;" class="icon_uploadimg" id="ulogoimgbtn" onclick="selectimg(2)">
<img id="ulogoimagename" src="" class="hide" alt="加载中……" width="70" height="70">
</div>
<div style="color:#ff7900">
  *尺寸：400*400像素 大小不超过300K 图片格式为.jpg或.png格式
</div>
</td>
</tr>
<tr>
<td class="header" align="right">
内容
</td>
<td align="left">
<textarea  placeholder="<输入>" name="uremark" id="uremark" maxlength="600"  style="width:380px;height:90px;">
</textarea> 
</td>
</tr>
<tr>
<td class="header" align="right">
日期范围
</td>
<td align="left">
<input type="text" id="ubegindate" placeholder="<输入>" style="width: 182px;height:29px" class="easyui-datebox" >
--
<input type="text" id="uenddate" placeholder="<输入>" style="width: 182px;height:29px" class="easyui-datebox">
</td>
<td rowspan="3" class="header" align="right">
订货海报
</td>
<td rowspan="3" align="left">
<div style="width:300px;height:152px;border:1px #ddd solid;" class="icon_uploadimg" id="uomimgbtn" onclick="selectimg(3)">
<img id="uomimagename" src="" class="hide" alt="加载中……" width="300" height="152">
</div>
<div style="color:#ff7900">
  *尺寸：750*380像素 大小不超过300K 图片格式为.jpg或.png格式
</div>
</td>
</tr>
<tr>
<td class="header" align="right">
地点
</td>
<td align="left">
<textarea placeholder="<输入>" name="uaddress" id="uaddress" maxlength="100"  style="width:380px;height:40px;">
</textarea>
</td>
</tr>
<tr>
<td class="header" align="right">
温馨提示
</td>
<td align="left">
<textarea  placeholder="<输入>" name="uwxts" id="uwxts" maxlength="200" style="width:380px;height:70px;">
</textarea> 
</td>
</tr>
</table>
</div>
<div>
<table>
</table>
</div>
<div class="dialog-footer">
	<input type="button" id="update" class="btn1"  name="updateom" value="保存" onclick="saveom()">
	<input type="button" class="btn2" name="close" value="取消" onclick="$('#ud').dialog('close')"> 
</div>
</div>
<div id="omwared" title="管理订货会商品" class="easyui-dialog" closed="true" style="width:100%;height:100%;">
<div class="toolbar" id="omware_toolbar">
<div class="toolbar_box1">
<div class="fl">
		<input class="btn1" type="button" id="addomwarebtn" value="添加商品" onclick="selectomware(0)">
		<input type="text" data-end="yes" placeholder="快速查找<回车搜索>" class="ipt1" id="omwareqsfindbox" data-enter="listomwarecode(1)" maxlength="20">
		<input class="btn2" type="button" value="搜索" onclick="listomwarecode(1)">
		<input class="btn2" type="button" value="高级搜索" onclick="omwaretoggle()">
		<input class="btn2" type="button" value="清空条件" onclick="resetomwaresearch()">
	<input type="button" class="btn3 hide" value="导入" onclick="openimportxlsd('ware')">
	<span class="sepreate hide"></span>
	<input type="button" class="btn3 hide"  value="导出" onclick="exportxls()">
</div>
</div>
<div class="searchbar" id="omsearchwarebox" style="display: none" data-enter="listomwarecode('1');omwaretoggle();">
<input type="hidden" id="stypeid">
<div class="search-box">
	<div class="s-box">
	<div class="title">类型</div>
	<div class="text">
	<input class="edithelpinput" id="sfullname" maxlength="20" type="text" data-value="#stypeid" placeholder="<输入或选择>"><span onclick="selectwaretype('search')"></span>
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
	<div class="text"><input class="wid160 hig25" id="sprodyear"  type="text" list="year_list" placeholder="<输入>">
					         <datalist id="year_list"></datalist>
					         </div>
	</div>
	<div class="s-box">
	<div class="title">商品卖点</div>
	<div class="text"><input class="wid160 hig25" id="swareremark" maxlength="400" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="text">
	<label>
	<input type="radio" name="shp" id="hp1" value="0">无图片</label>
	<label>
	<input type="radio" name="shp" id="hp2" value="1">有图片</label>
	<label>
	<input type="radio" name="shp" id="hp3" value="2" checked="checked">所有</label>
	</div></div>
	</div>
</div>
</div>
<div>
<table id="omwaret"></table>
	<div class="page-bar">
		<div class="page-total">共有<span id="omwaretotal">0</span>条记录
		</div>
		<div id="omwarepp" class="tcdPageCode page-code"></div>
	</div>
</div>
</div>
<div id="omcustd" title="管理订货会客户" class="easyui-dialog" closed="true" style="width:100%;height:100%;">
<input type="hidden" id="uomcustid">
<div class="fl" style="width:600px">
<div class="toolbar" id="omcust_toolbar">
<div class="toolbar_box1">
<div class="fl">
		<input class="btn1" type="button" value="生成随机用户账号" onclick="autotoomuserno()">
		<input class="btn1" type="button" value="生成随机用户密码" onclick="setomuserpwd()">
		<input type="text" data-end="yes" placeholder="快速查找<回车搜索>" data-enter="getomcustlist(1)" class="ipt1 wid160" id="omcustqsfindbox" maxlength="20">
		<input class="btn2" type="button" value="搜索" onclick="getomcustlist(1)">
</div>
</div>
</div>
<table id="omcustt"></table>
	<div class="page-bar">
		<div class="page-total">共有<span id="omcusttotal">0</span>条记录
		</div>
		<div id="omcustpp" class="tcdPageCode page-code"></div>
	</div>
</div>
<div class="fl" style="position: absolute;left:610px;right:0" id="custuserdiv">
<div class="toolbar" id="omuser_toolbar">
<div class="toolbar_box1">
<div class="fl">
<input class="btn1" type="button" value="添加" onclick="addomuserd()">
<input class="btn1" type="button" value="编辑" onclick="updateomuserd()">
		<input type="text" data-end="yes" placeholder="快速查找<回车搜索>" data-enter="getomuserlist(1)" class="ipt1 wid160" id="omuserqsfindbox" maxlength="20">
		<input class="btn2" type="button" value="搜索" onclick="getomuserlist(1)">
	<input type="button" class="btn3" value="导入" onclick="openimportxlsd('user')">
	<span class="sepreate hide"></span>
	<input type="button" class="btn3"  value="导出" onclick="exportuserxls()">
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
</div>
<div id="omwarehelpd" title="<span id='omwarehelptitle'>添加订货会商品</span>" class="easyui-dialog" closed="true" style="width:100%;height:100%;">
	<!-- 快速搜索栏 -->
<div id="omwaresbtn"  class="toolbar">
<div class="toolbar_box1">
<div class="fl">
	<input id="omwarefindbox" type="text" placeholder="搜索货号、商品名称<回车搜索>" class="help-qsipt" data-enter="getomwaredata1(1)" data-end="yes"></input>
	<input class="btn2" type="button" value="搜索" onclick="getomwaredata1(1)">
	<input class="btn2" type="button" value="高级搜索" onclick="omwaretoggle1()">
		<input class="btn2" type="button" value="清空条件" onclick="resetomwaresearch1()">
	<input type="button" class="btn1" value="确定增加" onclick="saveomware();">
</div>
</div>
<div class="searchbar" id="omsearchwarebox1" style="display: none" data-enter="getomwaredata1(1);omwaretoggle1();">
<div class="search-box">
	<div class="s-box">
	<div class="title">类型</div>
	<div class="text">
	<input class="edithelpinput" id="sfullname1" maxlength="20" type="text" data-value="#stypeid1" placeholder="<输入或选择>">
	<span onclick="selectwaretype('search1')"></span>
<input type="hidden" id="stypeid1">
	</div>
	</div>
	<div class="s-box">
	<div class="title">品牌</div>
	<div class="text"><input class="edithelpinput brand_help" data-value="#sbrandid1" id="sbrandname1" maxlength="20" type="text" placeholder="<输入或选择>">
	<span onclick="selectbrand('search1')"></span>
	<input type="hidden" id="sbrandid1">
	</div>
	</div>
	<div class="s-box">
	<div class="title">季节</div>
	<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname1" id="sseasonname1" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
	</div>
	</div>
	<div class="s-box">
	<div class="title">生产年份</div>
	<div class="text"><input class="wid160 hig25" id="sprodyear1"  type="text" list="year_list" placeholder="<输入>">
					         <datalist id="year_list"></datalist>
					         </div>
	</div>
	<div class="s-box">
	<div class="title">商品卖点</div>
	<div class="text"><input class="wid160 hig25" id="swareremark1" maxlength="400" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="text">
	<label>
	<input type="radio" name="shp1" id="hp11" value="0">无图片</label>
	<label>
	<input type="radio" name="shp1" id="hp12" value="1">有图片</label>
	<label>
	<input type="radio" name="shp1" id="hp13" value="2" checked="checked">所有</label>
	</div></div>
	</div>
</div>
</div>
	<table id="omwareht" style="overflow: hidden;"></table>
<div class="dialog-footer">
	<div id="omwarehpp" class="tcdPageCode fl"></div> 
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
订货方式
</td>
<td align="left">
<select class="sele1" id="ujs">
<option value="0">本部</option>
<option value="1">自营店</option>
<option value="2">经销商</option>
</select>
</td>
<td  class="header" align="right">
订货数量
</td>
<td align="left">
<input type="text" id="ujhamt" class="wid160 hig25" value="" placeholder="<输入>" maxlength="7">
</td>
</tr>
<tr>
<td  class="header" align="right">
订货金额
</td>
<td align="left">
<input type="text" id="ujhcurr" class="wid160 hig25" value="" placeholder="<输入>" maxlength="50">
</td>
<td  class="header" align="right">
订货款数
</td>
<td align="left">
<input type="text" id="ujhnum" class="wid160 hig25" value="" placeholder="<输入>" maxlength="7">
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
<!-- 编辑 -->
<div id="uomwared" title="编辑商品" style="width: 660px; text-align: center;height: 480px" class="easyui-dialog" closed=true>
 <input type="hidden" id="uomwareindex">
<div id="uptabs" class="easyui-tabs" style="width:100%;height:390px; ">   
<div title="基本信息">  
<form id="updateform0" action="">
<input type="hidden" id="uclorusedbj">
<table width="600" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="60" align="right" class="header skyrequied">货号</td>
    <td width="230" align="left"><input onchange="getwarenoyz('update');"type="text" name="uwareno" style="width:160px;height:25px"
				id="uwareno" data-options="required:true" placeholder="<输入>">
				 <input type ="hidden" name="uwareid" id="uwareid">
				 <input type="hidden" id="bj" name="bj">
				</td>
    <td width="60" align="right" class="header skyrequied">类型</td>
    <td width="230" align="left"><input  type="text" class="helpinput" name="ufullname" id="ufullname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择>" readonly>
				 <span onclick="selectwaretype('update');"></span><input type="hidden" name="utypeid" id="utypeid" value=""></td>
  </tr>
  <tr>
    <td align="right" class="header">商品名称</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="uwarename" id="uwarename" placeholder="<输入>" maxlength="72"></td>
    <td align="right" class="header">计量单位</td>
    <td align="left"><input name="uunits" id="uunits" class="wid160 hig25" placeholder="<输入或点击选择>" list="unit_list">
  </tr>
  <tr>
    <td align="right" class="header">品牌</td>
    <td align="left"><input  type="text" class="edithelpinput brand_help" name="ubrandname" id="ubrandname" data-value="#ubrandid" maxlength="20" placeholder="<选择或输入>"><span onclick="selectbrand('update')"></span>
				<input type="hidden" name="ubrandid" id="ubrandid" value=""></td>
    <td align="right" class="header">季节</td>
    <td align="left">
<input  type="text" class="edithelpinput season_help" name="useasonname" id="useasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span></td>
  </tr>
  <tr>
    <td align="right" class="header skyrequied">颜色</td>
    <td align="left">
    <input  type="text" class="edithelpinput wid143" name="ucolorname" id="ucolorname" placeholder="<选择或输入>" readonly>
    <span onclick="selectcolor('update')"></span>	 
	<input type="hidden" name="ucolorid" id="ucolorid" value="">
	<input type="button" class="btn1" style="padding:3px;" id="selcolor" value="更多" onclick="getomwarecolor($('#uwareid').val());">
	</td>
    <td align="right" class="header skyrequied">尺码组</td>
    <td align="left"><select name="usizetypegroup" id="usizetypegroup" class="sele1">
				</select>
					<input type="button" class="btn1" style="padding:3px;" id="selsize" value="更多" onclick="getwaresize($('#uwareid').val());">
	</td>			
  </tr>
  	<tr>
  	<td align="right" class="header">生产商</td>
  	<td align="left">
  	<input type="text"  name="uprovname" id="uprovname" placeholder="<输入或选择>" data-showall="1" class="edithelpinput wid145 hig25">
  	<span onclick="selectprov('uwareprov')"></span> 
  	<input type="hidden" id="uprovid" name="uprovid">
  	</td>
    <td align="right" class="header">厂家编码</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="uprodno" id="uprodno" placeholder="<输入>"></td>
   </tr>
   <tr>
   <td align="right" class="header">进价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="uentersale" id="uentersale" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
    <td align="right" class="header">生产年份</td>
    <td align="left">
    <input class="wid160 hig25" type="text" id="uprodyear" name="uprodyear" list="year_list" placeholder="输入或点击选择">
	</td>
	</tr>
  <tr>
    <td align="right" class="header">零售价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="uretailsale" id="uretailsale" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
	<td align="right" class="header">批发价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="usale4" id="usale4" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
  <tr>
  <td align="right" class="header">打包价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="usale5" id="usale5" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
	<td align="right" class="header">售价一</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="usale1" id="usale1" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
  <tr>
    <td align="right" class="header">售价二</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="usale12" id="usale2" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
				 <td align="right" class="header">售价三</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="usale3" id="usale3" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
</table>
</form>
</div>
<div title="详细资料">  
<form id="updateform1" action="">
<table width="570" border="0" cellspacing="10" class="table1">
<tr>
	<td align="right" class="header">波次</td>
  	<td align="left">
  	<input type="text"  name="uwavename" id="uwavename" placeholder="<选择>" data-showall="1" data-value="#uwaveid" class="edithelpinput wave_help">
  	<span onclick="selectwave('update')"></span> 
  	<input type="hidden" id="uwaveid" name="uwaveid">
  	</td>
  	<td align="right" class="header">等级</td>
  	<td align="left"><input type="text" style="width:160px;height:25px"
				name="udj" id="udj" placeholder="<输入>" maxlength="10"></td>
   </tr>
   <tr>
   <td align="right" class="header">执行标准</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="uzxbz" id="uzxbz" placeholder="<输入>" maxlength="32"></td>
  	<td align="right" class="header">国标码</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="ugbbar" id="ugbbar" placeholder="<输入>"></td>
     </tr>
     <tr>
	<td align="right" class="header">区位</td>
  	<td align="left">
  	<input type="text"  name="uareaname" id="uareaname" placeholder="<选择>" data-showall="1" data-value="#uareaid" class="edithelpinput area_help">
  	<span onclick="selectarea('update')"></span> 
  	<input type="hidden" id="uareaid" name="uareaid">
  	</td>
  	<td align="right" class="header">货位</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="ulocale" id="ulocale" placeholder="<输入>"></td>
  	</tr>
  	<tr><td align="right" class="header">设计师</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="usjman" id="usjman" placeholder="<输入>" maxlength="10"></td>
  	<td align="right" class="header">检验人</td>
  	<td align="left"><input type="text" style="width:160px;height:25px"
				name="ujlman" id="ujlman" placeholder="<输入>" maxlength="10"></td>
     </tr>
     <tr>
    <tr>
  	<td align="right" class="header">价格范围</td>
  	<td align="left"><input type="text" style="width:160px;height:25px"
				name="usalerange" id="usalerange" placeholder="<输入>" maxlength="10"></td>
   <td align="right" class="header">上市日期</td>
  	<td align="left">
  	<input type="text" style="width:160px;height:25px" name="ussdate" id="ussdate" placeholder="<输入如2016-01-01>" maxlength="32">
  	</td>
     </tr>
<tr><td align="right" class="header">面料</td>
	<td> <textarea id="uuseritem1" class="wid160" name="uuseritem1" rows="3" wrap="hard" maxlength="60"></textarea></td>
	<td align="right" class="header">里料</td>
	<td> <textarea id="uuseritem2" class="wid160" name="uuseritem2" rows="3" wrap="hard" maxlength="60"></textarea></td></tr>
	<tr><td align="right" class="header">配料</td>
	<td> <textarea id="uuseritem3" class="wid160" name="uuseritem3" rows="3" wrap="hard" maxlength="60"></textarea></td>
	<td align="right" class="header">毛料</td>
	<td> <textarea id="uuseritem4" class="wid160" name="uuseritem4" rows="3" wrap="hard" maxlength="60"></textarea></td></tr>
	<tr><td align="right" class="header">填充物</td>
	<td> <textarea id="uuseritem5" class="wid160" name="uuseritem5" rows="3" wrap="hard" maxlength="60"></textarea></td>
	<td align="right" class="header">洗涤说明</td>
	<td><textarea id="uxdsm" class="wid160" name="uxdsm" maxlength="60" style="height:46px"></textarea></td>
  </tr>
  <tr><td align="right" class="header">商品卖点</td>
	<td colspan="3"><input id="uomremark" class="wid467 hig25" name="uomremark" maxlength="400"></input></td>
  </tr>
</table>
</form>
</div>

<!-- <div title="条码资料">   -->
<!-- <table class="easyui-datagrid" style="height:330px;" id="warebarcodet"    -->
<!--         data-options="fitColumns:true,singleSelect:true,rownumbers: true">    -->
<!--     <thead>    -->
<!--         <tr>    -->
<!--             <th data-options="field:'BARCODE',width:100,align:'center',halign:'center'">条码</th>    -->
<!--             <th data-options="field:'COLORNAME',width:80,align:'center',halign:'center'">颜色</th>    -->
<!--             <th data-options="field:'SIZENAME',width:80,align:'center',halign:'center'">尺码</th>    -->
<!--         </tr>    -->
<!--     </thead>    -->
<!-- </table>   -->
<!-- 	<div id="warebarcodepp" class="tcdPageCode page-code"> -->
<!-- 	</div> -->
<!-- </div> -->
<div title="商品相册">  
<div class="preview">
	<div id="vertical" class="bigImg">
		<img src="" alt="" id="midimg" />
		<div style="display:none;" id="winSelector"></div>
	</div>
<!-- 	bigImg end	 -->
	<div class="smallImg">
<!-- 		<div class="scrollbutton smallImgUp disabled"></div> -->
		<div id="imageMenu">
			<ul id="wareimgul" style="overflow: hidden;zoom:1;">
			</ul>
		</div>
<!-- 		<div class="scrollbutton smallImgDown"></div> -->
<div style="clear:both">
<input type="button" id="sortimgbtn" class="btn2 hidden" style="margin: 3px" value="保存排序" onclick="sortwarepicture()">
<input type="button" id="upplimgbtn" class="btn2 hidden" style="margin: 3px" value="批量上传" onclick="openupimgd(2)">
</div>
	</div>
<!-- 	smallImg end -->
</div>
<div style="margin: 5px">
<input type="file" id="imgfile" style="display:none" />
<input type="hidden" id="uploadtype" value="" />
<input type="hidden" id="upimgid" value="" />
<input type="hidden" id="wareimgsort" value="">
</div>
</div>
</div>
<div class="dialog-footer">
	<label class="fl-ml30 hide">
	<input type="checkbox" name="unouse" id="unouse">禁用</label>
<!-- 	<label class="fl-ml30"> -->
<!-- 	<input type="checkbox" name="utjtag" id="utjtag">特价商品</label> -->
	<input type="hidden" name="unoused" id="unoused"> 
<!-- 	<input type="button" class="btn1" id="aotobar" value="自动产生条码" onclick="autowarebarcode()"> -->
	<input type="button" class="btn1" id="update" value="保存" onclick="updateWareCode()">
<!-- 	<input type="button" class="btn2" name="close" value="取消" onclick="closedialog()"> -->
</div>
</div>
<!-- 上传图片 -->
<div id="uploadfiled" title="批量上传图片" data-options="modal:true,onClose: refreshwareimg" style="width: 600px;text-align:left; height: 480px" class="easyui-dialog webuploader-element-invisible" closed="true">
  <input id="dhhbj" type="hidden" value="0">
  <div class="filebtn-box">
     <div id="filePicker">选择图片</div>
     <button id="uploadbtn" class="filebutton">开始上传</button>
     <button id="delbtn" class="filebutton">删除文件</button>
     <button id="resetbtn" class="filebutton">清空文件</button>
    </div>
 <div class="imgbox">
 <ul id="wareimglist" class="imgul">
 </ul>
 </div>
<div style="margin-top: 15px;height:60px;line-height: 15px" id="plwareimgwxts">
<img src="/skydesk/images/wxts.png"  style="margin-left: 20px;float:left;">
<p><b>温馨提示：</b>上传的图片格式只支持jpg，请按照文件名：“货号.jpg”，图片像素请控制：750px*750px。</p>
<p>或者“货号 (1-10).jpg”(其中1位主图，并且<span style="color:red">编号前有空格</span>如：001 (1).jpg)格式上传。</p>
<p>小技巧：一个货号的产品可以多选同时重命名为货号。(1个货号最多上传10张，单次最多可选300张)</p>
</div>
<div style="margin-top: 15px;height:60px;line-height: 15px" id="wareimgwxts">
<img src="/skydesk/images/wxts.png"  style="margin-left: 20px;float:left;">
<p><b>温馨提示：</b>本次上传图片将替换原有商品图片</p>
<p>上传的图片格式只支持jpg，图片像素请控制：750x750</p>
<p>“货号 (1-10).jpg”(其中1位主图),1个货号最多上传10张</p>
</div>
</div>
	
	<!-- 得到品牌列表  -->
	<div id="brandf" title="品牌帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
		<input type="hidden" id="hbrandname">
		<input type="hidden" id="hbrandid">
		<input type="hidden" id="hbrandser">
		<div id="brandsbtn" class="help-qstoolbar">
<!-- 			<input class="btn2" type="button" value="添加" onclick="$('#brand_add_dialog').dialog('open')"> -->
		<input type="text" class="help-qsipt" id="brandfindbox" data-enter="getbranddata('1')" data-end="yes"  placeholder="搜索品牌<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getbranddata('1')">
		</div>
		<table id="brandt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="brandpp" class="tcdPageCode fl"></div>
		<input type="button" class="btn1" value="确定" onclick="selectbd();" />
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#brandf').dialog('close')" /> -->
		</div>
	</div>
	<!-- 得到颜色列表 -->
<div id="colorf" title="颜色帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
		<input type="hidden" id="hcolorname">
		<input type="hidden" id="hcolorid">
		<input type="hidden" id="hcolorser">	
		<div id="colorsbtn" class="help-qstoolbar">
<!-- 				<input class="btn2" id="color_add_btn" type="button" value="添加" onclick="openaddcolord();"> -->
		<input type="text" class="help-qsipt" id="colorfindbox" data-enter="getwarecolor('1')" data-end="yes" placeholder="搜索颜色<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getwarecolor('1')">
		</div>
		<table id="colort" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="colorpp" class="tcdPageCode fl"></div> 
<!-- 		<input type="button" class="btn1" value="确定" onclick="selectcl();" /> -->
<!-- 		<input type="button" class="btn2" value="取消" onclick="$('#colorf').dialog('close')" /> -->
		</div>
</div>
<div id="selectsized" title="可用商品尺码" data-options="modal:true"  style="width: 400px; height: 400px;" class="easyui-dialog" closed=true>
<div style="margin: 20px auto;width:360px;overflow: auto;">
<!-- <div style="text-align: center;overflow: hidden;zoom:1;"> -->
<!-- <span class="sizesapn">可选择</span> -->
<!-- <span class="sizesapn spanselbj">可用</span> -->
<!-- <span class="sizesapn spanused">在用</span> -->
<!-- </div> -->
<div>
<p style="color:#ff7900;font-size: 14px;margin: 5px;">请选择:</p>
	<ul class="size_ul" id="size_ul">
		<li>L</li>
		<li class="selbj">XL</li>
		<li class="isused">XXL</li>
	</ul>
</div>
<!-- <p style="color:#ff7900;font-size: 14px;margin: 5px;">提示:<span style="color: #aaa;">灰色代表</span>在用商品尺码，不允许编辑！</p> -->
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="saveColorOrSize();" />
<!-- <input type="button" class="btn2" value="取消" onclick="$('#selectsized').dialog('close')" /> -->
</div>
</div>
<div class="easyui-dialog" title="商品陈列" id="ompoled" style="width:100%;height:100%;" closed="true">
<div style="position: relative;overflow: hidden;zoom:1;">
<div class="fl" style="width:400px;">
<div class="toolbar" id="ompole_toolbar">
<div class="toolbar_box1">
<div class="fl">
		<input class="btn1" type="button" value="添加" onclick="addompoled()">
		<input type="text" data-end="yes" placeholder="快速查找<回车搜索>" data-enter="getompolelist(1)" class="ipt1 wid160" id="ompoleqsfindbox" maxlength="20">
		<input class="btn2" type="button" value="搜索" onclick="getompolelist(1)">
</div>
</div>
</div>
<table id="ompolet"></table>
	<div class="page-bar">
		<div class="page-total">共有<span id="ompoletotal">0</span>条记录
		</div>
		<div id="ompolepp" class="tcdPageCode page-code"></div>
	</div>
</div>
<div style="left: 410px; top: 0px; right: 0px; bottom: 0px; position: absolute;">
<div class="easyui-panel" title="上传陈列图片与陈列商品" style="padding-left: 5px;height:100%">
<input type="hidden" id="upoleid" value="">
<div>
<div class="leftbox" onclick="selectimg(1)">
<div class="poletitle">上传陈列图片</div>
<div class="mainimgbox">
<img id="mainimg" class="hide" src="" width="355px" height="300px"/>
<div class="no-image">
<div class="icon_uploadimg" style="height:40px">
</div>
<p>尺寸：710×600PX</p>
<p>大小不超过300K</p>
<p>图片格式为.jsp或者.png</p>
</div>
</div>
</div>
<div style="clear: both;padding: 5px 0;">
<div class="poletitle">陈列说明</div>
<textarea class="maindetail" placeholder="<输入10-120字符说明>" id="upolename" maxlength="120">
</textarea>
</div>
<div style="clear: both;padding: 5px 0;">
<input type="button" class="btn1" value="保存" onclick="saveompole();" />
</div>
</div>
<div style="clear: both;" class="hide" id="polewarebox">
<div title="" class="poletitle">
陈列商品
</div>
<input type="hidden" id="polewaresort" value="">
<ul class="poleimagelist" id="poleimagelist">
<li class="addwareli">
<div class="wareimgbox icon_uploadimg">
</div>
<div class="warenobox">点击增加陈列商品</div>
</li>
</ul>
</div>
</div>
</div>
</div>
</div>
<div class="easyui-dialog" title="<span id='omwaredptitle'></span>定义商品相关搭配" id="omwaredpd" style="width:100%;height:100%;" closed="true" data-options="onClose:function(){omwaretp=0;}">
<div title="" class="poletitle">
商品图片
</div>
<div style="max-width:700px;max-height: 300px;min-width: 100px;min-height: 100px;overflow: auto;">
<img id="dpwareimage" alt="没有图片" src="">
</div>
<div title="" class="poletitle">
搭配图片
</div>
<ul class="poleimagelist" id="dpimagelist">
<li class="addwareli">
<div class="wareimgbox icon_uploadimg">
</div>
<div class="warenobox">点击增加</div>
</li>
</ul>
</div>
<script type="text/javascript">
//权限设置

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
		searchb = false;
		$('#searchbtn').hide();
	} else {
		$('#highsearch').val('高级搜索▲');
		searchb = true;
		$('#searchbtn').show();
	}
	$('.searchbar').slideToggle('fast');
	$('#somname').focus();
}
var uploader_img = null;
var uploader_img_options = {
		auto: false,
		server: "/skydesk/fileup",
		pick: "#filePicker",
		uptype: "image",
		upbtn: "#uploadbtn",
		accept: {  
	    	   title: 'Images',  
	    	   extensions: 'jpg',  
	    	   mimeTypes: 'image/jpeg'  
	    },
		fileQueued: function(file) { // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。  
			var dhhbj = $('#dhhbj').val();
			if(dhhbj==2){
				var files = uploader_img.getFiles();
				var dindex = file.name.indexOf(".");
				var kzm = file.name.substring(dindex,file.name.length);//扩展名
				var rows = $('#omwaret').datagrid("getRows");
				var index = $('#uomwareindex').val();
				var row = rows[index];
				var wareno = $('#uwareno').val();
				if(row) wareno = row.WARENO;
				var fl = files.length;
				file.name = wareno + " ("+fl+")"+kzm;
			}
			var $list = $('#wareimglist');
			var $li = $(
					'<li id="' + file.id + '" class="file-item thumbnail">' +
					'<img>' +
					'<div class="info">' + file.name + '</div>' +
					'</li>'
				),
				$img = $li.find('img');
				// $list为容器jQuery实例  
				$list.append($li);
				// 创建缩略图  
				// 如果为非图片文件，可以不用调用此方法。  
				// thumbnailWidth x thumbnailHeight 为 100 x 100  
				uploader_img.makeThumb(file, function(error, src) { //webuploader方法  
					if (error) {
						$img.replaceWith('<span>不能预览</span>');
						return;
					}
					$img.attr('src', src);
				}, 100, 100);
				
		},
		beforeFileQueued: function(file){
			var flimit = uploader_img.option("fileNumLimit");
			var files = uploader_img.getFiles();
			if(files.length>=flimit) return false;
			else return true;
		},
		uploadSuccess: function(file, res) {
			$('#' + file.id).addClass('upload-state-done');
			var suce = res.stg == 0 ? false : true;
			if (suce) {
				var $li = $('#' + file.id),
					$success = $li.find('div.success');
				// 避免重复创建  
				if (!$success.length) {
					$success = $('<div class="success"></div>').appendTo($li);
				}
				$success.text('上传成功');
			} else {
				var $li = $('#' + file.id),
					$error = $li.find('div.error');
				// 避免重复创建  
				if (!$error.length) {
					$error = $('<div class="error"></div>').appendTo($li);
				}
				if (res.msg == 'sys') {
					uploader_img.stop();
					alert('签名异常，请重新登录！');
					top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
				} else if (res.msg == 'fwq') {
					uploader_img.stop();
					alerttext('您的网络不佳，请求超时，请稍后重试！');
				} else {
					$error.text(res.msg);
				}
			}
		},
		uploadProgress: function(file, percentage) {
			var $li = $('#' + file.id),
				$percent = $li.find('.progress span');
			// 避免重复创建  
			if (!$percent.length) {
				$percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
			}
			$percent.css('width', (percentage * 100).toFixed(0) + '%');
		},
		uploadError: function(file) {
			var $li = $('#' + file.id),$error = $li.find('div.error');
			// 避免重复创建  
			if (!$error.length) {
				$error = $('<div class="error"></div>').appendTo($li);
			}
			$error.text('上传失败！');
		},
		uploadComplete: function(file) {
			$('#' + file.id).find('.progress').remove();
			var dhhbj = $('#dhhbj').val();
		},
		startUpload: function() {
			var files = uploader_img.getFiles();
			if (files.length > 0) {
				var dhhbj = $('#dhhbj').val();
				if(dhhbj==2){
					dhhbj=1;
				}
				uploader_img.option('formData',{
					dhhbj: dhhbj
				});
				uploader_img.upload();
			} else {
				alerttext("文件列表为空！请添加需要上传的文件！");
			}
		}
}
var uploader_wareimg = null;
var uploader_wareimg_options = {
		auto: true,
		server: "../BasicInfom/wareservlet?wareser=uploadwareimage",
		uptype: "image",
		upbtn: "#uploadbtn",
// 		pick: {
// 			multiple: false
// 		},
// 		fileNumLimit: 1, 
		accept: {  
	    	   title: 'Images',  
	    	   extensions: 'gif,jpg,jpeg,bmp,png', 
	    	   mimeTypes: 'image/*'  
	    },
		fileQueued: function(file) { // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。  
			if(uptp==1){
				uploader_wareimg.makeThumb( file, function(error, ret) {
			        if ( error ) {
			           	$('#mainimg').attr("alt","暂不支持预览!")
			        } else {
			        	$('#mainimg').attr('src' ,ret);
			        }
			        $('#mainimg').addClass('changed').show();
			    });
				$('.no-image').hide();
			}else if(uptp==2){
				if(file.name=="omimagename")
					return;
				uploader_wareimg.makeThumb(file, function(error, ret ) {
			        if ( error ) {
			           $('#ulogoimagename').attr("alt","暂不支持预览!")
			        } else {
			        	$('#ulogoimagename').attr('src' ,ret);
			        }
			        $('#ulogoimagename').addClass('changed').show();
			    });
				file.name="logoimagename";
			}else if(uptp==3){
				if(file.name=="logoimagename")
					return;
				uploader_wareimg.makeThumb(file, function( error, ret) {
			        if ( error ) {
			           $('#uomimagename').attr("alt","暂不支持预览!")
			        }else {
			        	$('#uomimagename').attr('src' ,ret);
			        }
			        $('#uomimagename').addClass('changed').show();
			    });
				file.name="omimagename";
			}
		},
		
		uploadSuccess: function(file, res) {
			try{
				if(uptp==1){
					$("#ompolepp").refreshPage();
					var poleid = Number($('#upoleid').val());
					if(poleid==0)
						$('#upoleid').val(res.msg);
					$('#mainimg,#polewarebox').removeClass('changed').show();
					listompoleware();
				}else if(uptp==2||uptp==3){
					/*valideData(res);
					if(Number(res.result)>0){
						$("#pp").refreshPage();
						$('#ud').dialog('close');
					}else
						alert(res.msg);*/
					if(uptp==2)
						$('#ulogoimagename').data('base64str',res.msg);
					if(uptp==3)
						$('#uomimagename').data('base64str',res.msg);
				}else{
					if(res.result==1)
						getwarepicture();
					else 
						alert(res.msg);
				}
				
			}catch(e){
				console.error(e);top.wrtiejsErrorLog(e);
			}
		},
		uploadProgress: function(file, percentage) {
			
		},
		uploadError: function(file) {
			uploader_wareimg.reset();
		},
		uploadComplete:  function(file) {
			alerthide();
			uploader_wareimg.reset();
			$('#imgfile').val('');
		},
		startUpload: function() {
			if(uptp>=1){
				return;
			}
			if (uploader_wareimg.getFiles().length == 1) {
				uploader_wareimg.option('formData', {
					wareid: $('#uwareid').val(),
					dhhbj: 1,
					imgid: $('#upimgid').val()
				});
				uploader_wareimg.upload();
				alerttext("正在上传请稍等………",30000);
			} else {
				alerttext("请选择一张图片！");
			}
		}
}

function openupimgd(dhhbj) {
	if(dhhbj==2){
		uploader_img.option('fileNumLimit', 10);
		$("#plwareimgwxts").hide();
		$("#wareimgwxts").show();
	}else{
		$("#wareimgwxts").hide();
		$("#plwareimgwxts").show();
		uploader_img.option('fileNumLimit', undefined);
	}
	$('#dhhbj').val(dhhbj);
	$('#uploadfiled').dialog('open');
}
var refreshwareimg = function(){
	var dhhbj = $('#dhhbj').val();
	if(dhhbj==2) getwarepicture();
}
$(function(){
	uploader_img = SkyUploadFiles(uploader_img_options);
	//清空队列
	$("#resetbtn").on('click', function() {
		uploader_img.stop();
		uploader_img.reset();
		$('#wareimglist').html('');
	});
	setdatagrid();
	getomdata(1);
	$('ul.size_ul').delegate("li:not(.isused)", 'click', function() {
		var sizeid = $(this).data('sizeid');
		var selbj = $(this).hasClass('selbj');
		var changed = $(this).hasClass('changed');
		if (!changed)
			$(this).addClass('changed');
		if (selbj)
			$(this).removeClass('selbj');
		else
			$(this).addClass('selbj');
	});
	uploader_wareimg = SkyUploadFiles(uploader_wareimg_options);
	$('.webuploader-element-invisible').removeClass('webuploader-element-invisible');
	// 图片上下滚动
	var count = $("#imageMenu li").length - 4; // 显示 5 个 li标签内容 
	var interval = $("#imageMenu li:first").width();
	var curIndex = 0;
	$('.scrollbutton').click(function() {
		if ($(this).hasClass('disabled')) return false;
		if ($(this).hasClass('smallImgUp'))--curIndex;
		else ++curIndex;
		$('.scrollbutton').removeClass('disabled');
		if (curIndex == 0) $('.smallImgUp').addClass('disabled');
		if (curIndex == count - 1) $('.smallImgDown').addClass('disabled');
		$("#imageMenu ul").stop(false, true).animate({
			"marginLeft": -curIndex * interval + "px"
		}, 600);
	});
	// 解决 ie6 select框 问题
	$.fn.decorateIframe = function(options) {
		if (browser.indexOf('IE') >= 0 && Number(version) < 7) {
			var opts = $.extend({}, $.fn.decorateIframe.defaults, options);
			$(this).each(function() {
				var $myThis = $(this);
				//创建一个IFRAME
				var divIframe = $("<iframe />");
				divIframe.attr("id", opts.iframeId);
				divIframe.css("position", "absolute");
				divIframe.css("display", "none");
				divIframe.css("display", "block");
				divIframe.css("z-index", opts.iframeZIndex);
				divIframe.css("border");
				divIframe.css("top", "0");
				divIframe.css("left", "0");
				if (opts.width == 0) {
					divIframe.css("width", $myThis.width() + parseInt($myThis.css("padding")) * 2 + "px");
				}

				if (opts.height == 0) {
					divIframe.css("height", $myThis.height() + parseInt($myThis.css("padding")) * 2 + "px");
				}
				divIframe.css("filter", "mask(color=#fff)");
				$myThis.append(divIframe);
			});
		}
	}
	$.fn.decorateIframe.defaults = {
		iframeId: "decorateIframe1",
		iframeZIndex: -1,
		width: 0,
		height: 0
	}
	//放大镜视窗
	$("#bigView").decorateIframe();
	//点击到中图
	var midChangeHandler = null;
	$("#imageMenu ul").delegate("li img", "click", function() {
		if ($(this).attr("id") != "onlickImg") {
			midChange($(this).attr("src").replace("_60x60-m3.png", ""));
			$("#imageMenu li").removeAttr("id");
			$(this).parent().attr("id", "onlickImg");
		}
	}).delegate("li", "dblclick", function() {
		var imgid = Number($(this).data('imgid'));
		if(imgid==0){
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=addwarepicture&server=buy");
			$('#uploadtype').val('add');
			$('#upimgid').val('0');
			$('#imgfile').click();
		}else if(imgid>0){
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=updatewarepicture&server=buy");
			$('#upimgid').val(imgid);
			$('#uploadtype').val('update');
			$('#imgfile').click();
		}
		selectimg(0);
	}).delegate("li", "click", function() {
		var imgid = Number($(this).data('imgid'));
		if(imgid==0){
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=addwarepicture&server=buy");
			$('#uploadtype').val('add');
			selectimg(0);
		}else{
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=updatewarepicture&server=buy");
			$('#uploadtype').val('update');
			selectimg(0);
		}
		$('#upimgid').val(imgid);
	}).delegate("span.icon_removeimg", "click", function() {
		var $li = $(this).parent();
		var imgid = Number($li.data('imgid'));
		if(imgid>0)
			delwarepicture(imgid);
	});
	$('ul#dpimagelist').delegate('span.icon_removeimg','click',function(){
		var $li = $(this).parent().parent();
		var wareid1 = Number($li.data('wareid'));
		if(wareid1>0)
			delomwaredp(wareid1);
	});
	
	/*.bind("mouseover", function(){
			if ($(this).attr("id") != "onlickImg") {
				window.clearTimeout(midChangeHandler);
				midChange($(this).attr("src").replace("_60x60-m3.png", "_800x600-m2.png"));
				$(this).css({ "border": "3px solid #959595" });
			}
		}).bind("mouseout", function(){
			if($(this).attr("id") != "onlickImg"){
				$(this).removeAttr("style");
				midChangeHandler = window.setTimeout(function(){
					midChange($("#onlickImg img").attr("src").replace("_60x60-m3.png", "_800x600-m2.png"));
				}, 1000);
			}
		});*/
	$('#imgfile').change(function(){
		if(this.files.length>0){
			var fs = uploader_wareimg.getFiles();
			var fss = [];
			for(var f in fs){
				if(f.name=="logoimagename"&&uptp==2){
					uploader_wareimg.removeFile(f);
				}else if(f.name=="omimagename"&&uptp==3){
					uploader_wareimg.removeFile(f);
				}else{
					fss.push(fs[f]);
				}
			}
			fss.push(this.files[0]);
			uploader_wareimg.reset();
			uploader_wareimg.addFiles(fss);
			$('#imgfile').val('');
		}
	});
		function midChange(src) {
			$("#midimg").attr("src", src).load(function() {
				changeViewImg();
			});
		}

		//大视窗看图

		function mouseover(e) {
			if ($("#winSelector").css("display") == "none") {
				$("#winSelector,#bigView").show();
			}
			$("#winSelector").css(fixedPosition(e));
			e.stopPropagation();
		}

		function mouseOut(e) {
			if ($("#winSelector").css("display") != "none") {
				$("#winSelector,#bigView").hide();
			}
			e.stopPropagation();
		}

		//  $("#midimg").mouseover(mouseover); //中图事件

		//   $("#midimg,#winSelector").mousemove(mouseover).mouseout(mouseOut); //选择器事件

		var $divWidth = $("#winSelector").width(); //选择器宽度
		var $divHeight = $("#winSelector").height(); //选择器高度
		var $imgWidth = $("#midimg").width(); //中图宽度
		var $imgHeight = $("#midimg").height(); //中图高度
		var $viewImgWidth = $viewImgHeight = $height = null; //IE加载后才能得到 大图宽度 大图高度 大图视窗高度

		function changeViewImg() {
			$("#bigView img").attr("src", $("#midimg").attr("src").replace("_800x600-m2.png", ""));
		}
		changeViewImg();
		$("#bigView").scrollLeft(0).scrollTop(0);

		function fixedPosition(e) {
			if (e == null) {
				return;
			}
			var $imgLeft = $("#midimg").offset().left; //中图左边距
			var $imgTop = $("#midimg").offset().top; //中图上边距
			X = e.pageX - $imgLeft - $divWidth * 0.5; //selector顶点坐标 X
			Y = e.pageY - $imgTop - $divHeight * 0.5; //selector顶点坐标 Y
			X = X < 0 ? 0 : X;
			Y = Y < 0 ? 0 : Y;
			X = X + $divWidth > $imgWidth ? $imgWidth - $divWidth : X;
			Y = Y + $divHeight > $imgHeight ? $imgHeight - $divHeight : Y;
			if ($viewImgWidth == null) {
				$viewImgWidth = $("#bigView img").outerWidth();
				$viewImgHeight = $("#bigView img").height();
				if ($viewImgWidth < 200 || $viewImgHeight < 200) {
					$viewImgWidth = $viewImgHeight = 800;
				}
				$height = $divHeight * $viewImgHeight / $imgHeight;
				$("#bigView").width($divWidth * $viewImgWidth / $imgWidth);
				$("#bigView").height($height);
			}
			var scrollX = X * $viewImgWidth / $imgWidth;
			var scrollY = Y * $viewImgHeight / $imgHeight;
			$("#bigView img").css({
				"left": scrollX * -1,
				"top": scrollY * -1
			});
			$("#bigView").css({
				"top": $(".bigImg").offset().top,
				"left": $(".bigImg").offset().left + $(".bigImg").width() + 15
			});
			return {
				left: X,
				top: Y
			};
		}
		$("#wareimgul").dragsort({ dragSelector: "li[data-imgid!='']", dragBetween: false, dragEnd: saveOrder,scrollContainer:'#wareimgul', placeHolderTemplate: "" });
		function saveOrder() {
			var data = $("#wareimgul li").map(function() {
				if(Number($(this).data('imgid'))>0)	
					return Number($(this).data('imgid'));
				return;
			}).get();
			if(omwaretp==1){
				$('#polewaresort').val(data.join(","));
				return;
			}
			$('#wareimgsort').val(data.join(","));
			$('#sortimgbtn').show();
		};
	getdefaultcs();
	$("ul.poleimagelist").dragsort({ dragSelector: "li:not(.addwareli)", dragBetween: false, dragEnd: saveOrder,scrollContainer:'ul.poleimagelist', placeHolderTemplate: "" });
	$('ul#poleimagelist').delegate('li','click',function(){
		var enable = $(this).hasClass('addwareli');
		var poleid = Number($('#upoleid').val());
		if(enable&&poleid>0){
			selectomware(1);
		}else if(poleid==0){
			alerttext('请先保存陈列杆的说明！');
		}
	});
	$('ul#dpimagelist').delegate('li','click',function(){
		var enable = $(this).hasClass('addwareli');
		if(enable){
			selectomware(2);
		}
	});
	$('ul#poleimagelist').delegate('span.icon_removeimg','click',function(){
		var $li = $(this).parent().parent();
		var wareid = Number($li.data('wareid'));
		if(wareid>0)
			delompoleware(wareid);
	});
});
function setdatagrid(){
	$('#qsfindbox').bind("keyup", function(e) {
		if (iskey(13, e)) {
			getomdata('1');
		} else {
			if (this.value == "") {
				getomdata("1");
			}
		}
	});
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			if (opser == "get")
				getomdata(p.toString())
			else
				searchom(p.toString());
		}
	});
	//加载数据
	$('#gg').datagrid({
			idField: 'omname',
			height: $('body').height() - 50,
			fitColumns: true,
			striped: true, //隔行变色
			singleSelect: true,
			nowrap: false,
			sort: "OMID",
			order: "asc",
			columns: [
				[{
					field: 'OMID',
					title: 'ID',
					width: 200,
					hidden: true
				}, {
					field: 'CHECK',
					checkbox: true,
					hidden: true
				}, {
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
				}, {
					field: 'OMIMAGENAME',
					title: '图片地址',
					width: 200,
					hidden: true
				}, {
					field: 'IMG',
					title: '订货会海报',
					width: 170,
					halign: 'center',
					align: 'center',
					fixed: true,
					formatter: function(value, row, index) {
						var val = row.OMIMAGENAME;
						if (val != '' && val != null && val != undefined) {
							return '<img bigimg="true"  style=\"height:75px; width:150px;\" src="' + imageurl + '' + row.OMIMAGENAME + '" />';
						}
					}
				}, {
					field: 'OMNAME',
					title: '主题',
					width: 100,
					sortable: true,
					fixed: true,
					align: 'center',
					halign: 'center'
				}, {
					field: 'REMARK',
					title: '内容',
					width: 200,
					sortable: true,
					fixed: true,
					align: 'left',
					halign: 'center'
				}, {
					field: 'BEGINDATE',
					title: '开始日期',
					width: 80,
					sortable: true,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						if(value)
							return value.substring(0,10);
					}
					
				}, {
					field: 'ENDDATE',
					title: '结束日期',
					width: 80,
					sortable: true,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						if(value)
							return value.substring(0,10);
					}
				}, {
					field: 'ADDRESS',
					title: '地址',
					width: 200,
					sortable: true,
					fixed: true,
					align: 'left',
					halign: 'center'

				}, {
					field: 'STATENAME',
					title: '状态',
					width: 80,
					sortable: true,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						var stg = Number(row.STATETAG);
						if(stg==0)
							return "等待中";
						else if(stg==1)
							return "筹备中";
						else if(stg==2)
							return "已开始";
						else if(stg==3)
							return "已暂停";
						else if(stg==4)
							return "已结束";
					}
				}, {
					field: 'ACTION',
					title: '操作',
					width: 80,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						var stg = Number(row.STATETAG);
						var html = "";
						if(stg==0){
							html +='<input type="button" class="m-btn" value="删除" onclick="delom(' + index + ')">';
							html +='<input type="button" class="m-btn" value="筹备" onclick="changeom(' + index + ',1)">';
						}else if(stg==1){
// 							html +='<input type="button" class="m-btn" value="查看" onclick="showom(' + index + ')">';
							html +='<input type="button" class="m-btn" value="等待" onclick="changeom(' + index + ',0)">';
							html +='<input type="button" class="m-btn" value="开始" onclick="changeom(' + index + ',2)">';
						}else if(stg==2){
// 							html +='<input type="button" class="m-btn" value="查看" onclick="showom(' + index + ')">';
							html +='<input type="button" class="m-btn" value="筹备" onclick="changeom(' + index + ',3)">';
							html +='<input type="button" class="m-btn" value="暂停" onclick="changeom(' + index + ',4)">';
							html +='<input type="button" class="m-btn" value="结束" onclick="changeom(' + index + ',6)">';
						}else if(stg==3){
// 							html +='<input type="button" class="m-btn" value="查看" onclick="showom(' + index + ')">';
							html +='<input type="button" class="m-btn" value="继续" onclick="changeom(' + index + ',5)">';
						}else if(stg==4){
// 							html +='<input type="button" class="m-btn" value="查看" onclick="showom(' + index + ')">';
							html +='<input type="button" class="m-btn" value="返回" onclick="changeom(' + index + ',7)">';
						}
						return html;
					}
				}, {
					field: 'ACTION1',
					title: '管理',
					width:240,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						var stg = Number(row.STATETAG);
						var html = "";
						html +='<input type="button" class="m-btn" value="商品管理" onclick="openomwared(' + index + ')">';
						html +='<input type="button" class="m-btn" value="商品陈列" onclick="openompoled(' + index + ')">';
						html +='<input type="button" class="m-btn" value="客户管理" onclick="openomcustd(' + index + ')">';
						html +='<input type="button" class="m-btn" value="订货实时监控" onclick="opendhssjktab(' + index + ')">';
						html +='<input type="button" class="m-btn" value="商品陈列汇总" onclick="opendspclhztab(' + index + ')">';
						return html;
					}
				}, {
					field: 'ACTION2',
					title: '分析',
					width: 100,
					fixed: true,
					align: 'center',
					halign: 'center',
					hidden: true,
					formatter: function(value, row, index) {
						var stg = Number(row.STATETAG);
						var html = "";
						html +='<input type="button" class="m-btn" value="报表分析" onclick="openomwared(' + index + ')">';
						return html;
					}
				}, {
					field: 'STATETAG',
					title: '状态',
					width: 200,
					hidden: true
				}, {
					field: 'WXTS',
					title: '温馨提示',
					width: 200,
					hidden: true
				}]
			],
			onClickRow: function(index, row) {
				$('#uindex').val(index);
			},
			onSortColumn: function(sort, order) {
				$('#gg').datagrid('options').sort = sort;
				$('#gg').datagrid('options').order = order;
				$('#pp').refreshPage();
			},
			onDblClickRow: function(rowIndex, rowData) {
				updateomd();
			},
			pageSize: 20,
			toolbar: '#toolbars'
		}).datagrid("keyCtr", "updateomd()");
	
}

 function getomdata(page){
	opser = "get";
	$('#gg').datagrid('loadData', nulldata);
	var findbox = $('#qsfindbox').val();
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	alertmsg(6);
	var erpser = "listomsubject";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: erpser,
			page: page,
			sort: sort,
			order: order,
			rows: pagecount,
			findbox: findbox
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#total').html(data.total);
					$('body').focus();
				}
			}catch(e){
				console.error(e);
			}
		}
	});
}

function serachomdata(page){
	opser = "search";
	$('#gg').datagrid('loadData', nulldata);
	var sort = $('#gg').datagrid('options').sort;
	var order = $('#gg').datagrid('options').order;
	var omname = $('#somname').val();
	var remark = $('#sremark').val();
	var address = $('#saddress').val();
	alertmsg(6);
	var erpser = "listomsubject";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: erpser,
			page: page,
			sort: sort,
			order: order,
			rows: pagecount,
			omname: omname,
			address: address,
			remark: remark
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#gg').datagrid('loadData', data);
					$('#total').html(data.total);
					$('body').focus();
				}
			}catch(e){
				console.error(e);
			}
		}
	});
}

function addomd(){
	$('#ud').dialog({
		title: "增加订货会"
	});
	$('#ud').dialog('open');
	$('#ubegindate,#uenddate').datebox('setValue','');
	$('#uaddress,#uremark,#uomid,#ustatetag,#uomname,#uwxts').val('');
	$('#uomimagename,#ulogoimagename').hide();
	$('#uomimagename,#ulogoimagename').removeClass('changed');
	if(uploader_wareimg!=null)
		uploader_wareimg.reset();
	$('#uomname').val('').focus();
}
//编辑框
function updateomd() {
	var row = $('#gg').datagrid('getSelected');
	if (!row) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		$('#ud').dialog({
			title: "编辑订货会"
		});
		$('#ud').dialog('open');
		$('#uomname').focus();
		$('#uomname').val(row.OMNAME);
		$('#uomid').val(row.OMID);
		$('#ulogoimagename,#uomimagename').data("base64str","")
		getombyid();
	}
}
//添加编辑
function saveom() {
	var omname = $('#uomname').val();
	var omid = Number($('#uomid').val());
	if (omname == "" || omname == "null" || omname == "undefined") {
		alerttext("请输入主题");
		$('#uomname').focus();
	} else {
		alertmsg(2);
		var begindate = $('#ubegindate').datebox('getValue');
		var enddate = $('#uenddate').datebox('getValue');
		var address = $('#uaddress').val();
		var remark = $('#uremark').val();
		var wxts = $('#uwxts').val();
		var statetag = Number($('#ustatetag').val());
		if(statetag>1){
			alerttext("该订货会不在等待、筹备状态，不允许编辑！");
			return;
		}
		if (begindate.length == 0) {
			alerttext('请选择开始日期');
			return;
		}
		if (enddate.length == 0) {
			alerttext('请选择结束日期');
			return;
		}
		var erpser = "addomsubject";
		if (isNaN(omid) || omid == 0){
			erpser = "addomsubject";
			if(!$('#ulogoimagename').hasClass('changed')){
				alerttext('请选择品牌logo！');
				return;
			}
			if(!$('#uomimagename').hasClass('changed')){
				alerttext('请选择海报！');
				return;
			}
		}
		else{
			erpser = "updateomsubject";
		}
		if($('#uomimagename').hasClass('changed')||$('#ulogoimagename').hasClass('changed')){
			alertmsg(2);
			var omimagename = $('#uomimagename').data("base64str");
			var logoimagename = $('#ulogoimagename').data("base64str");
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/uploadimgservice", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					flyangser: erpser,
					omid: $('#uomid').val(),
					lastop: skyuser.EPNAME,
					omname: omname,
					address: address,
					begindate: begindate,
					enddate: enddate,
					wxts: wxts,
					remark: remark,
					omimagename: omimagename,
					logoimagename: logoimagename
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$('#pp').refreshPage();
							$('#ud').dialog('close');
						}
					}catch (e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
				}
			});
		}else{
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: erpser,
					omid: $('#uomid').val(),
					lastop: skyuser.EPNAME,
					omname: omname,
					address: address,
					begindate: begindate,
					enddate: enddate,
					wxts: wxts,
					remark: remark
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$('#pp').refreshPage();
							$('#ud').dialog('close');
						}
					}catch (e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
				}
			});
		}
	}
}
//获取byiddata
function getombyid() {
	var omid = Number($('#uomid').val());
	if (omid > 0) {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyordjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "getomsubject",
				omid: omid,
				fieldlist: 'omid,omname,begindate,enddate,remark,address,wxts,statetag,logoimagename,omimagename'
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						if (data.total > 0) {
							var row = data.rows[0];
							var omname = row.OMNAME;
							var begindate = row.BEGINDATE;
							var enddate = row.ENDDATE;
							var remark = row.REMARK;
							var address = row.ADDRESS;
							var wxts = row.WXTS;
							var statetag = row.STATETAG;
							var logoimagename = row.LOGOIMAGENAME;
							var omimagename = row.OMIMAGENAME;
							$('#uomname').val(omname);
							$('#uremark').val(remark);
							$('#uaddress').val(address);
							$('#uwxts').val(wxts);
							$('#ustatetag').val(statetag);
							$('#ubegindate').datebox('setValue', begindate);
							$('#uenddate').datebox('setValue', enddate);
							if(logoimagename.length>0){
								$('#ulogoimagename').attr('src',imageurl+logoimagename).show();
							}else{
								$('#ulogoimagename').hide();
							}
							if(omimagename.length>0){
								$('#uomimagename').attr('src',imageurl+omimagename).show();
							}else{
								$('#uomimagename').hide();
							}
							$('#uomimagename,#ulogoimagename').removeClass('changed');
							if(uploader_wareimg!=null)
								uploader_wareimg.reset();
						}
					}
				}catch(e){
					console.error(e);
				}
			}
			});
	}
}
//删除订货会
function delom(index) {
	var rows = $('#gg').datagrid('getRows');
	if (rows) {
		var omname = rows[index].OMNAME;
		var omid = rows[index].OMID;
		$.messager.confirm(dialog_title, '您确认要删除订货会' + omname + '吗？', function(r) {
			if (r) {
				if (omid > 0) {
					alertmsg(2);
					var skyser= "delomsubject";
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyordjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "delomsubject",
							omid: omid
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									$('#gg').datagrid("deleteRow", index);
									$('#total').html(Number($('#total').val())-1);
									$('#gg').datagrid("refresh");
								}
							}catch(e){
								console.error(e);
							}
						}
					});
				}
			}
		});
	}
}
//更改订货会状态
function changeom(index,opid) {
	var rows = $('#gg').datagrid('getRows');
	if (rows) {
		var omname = rows[index].OMNAME;
		var omid = rows[index].OMID;
		var msg  = "";
		var stg = 0;
		switch (opid) {
		case 0:
			msg= "返回等待";
			stg = 0;
			break;
		case 1:
			msg= "开始筹备";
			stg = 1;
			break;
		case 2:
			msg= "开始";
			stg = 2;
			break;
		case 3:
			msg= "返回筹备";
			stg = 1;
			break;
		case 4:
			msg= "暂停";
			stg = 3;
			break;
		case 5:
			msg= "重新开始";
			stg = 2;
			break;
		case 6:
			msg= "结束";
			stg = 4;
			break;
		case 7:
			msg= "重新开始";
			stg = 2;
			break;
		default:
			break;
		}
		$.messager.confirm(dialog_title, '您确认要'+msg+'订货会' + omname + '吗？', function(r) {
			if (r) {
				if (omid > 0) {
					alertmsg(2);
					$.ajax({
						type : "POST", //访问WebService使用Post方式请求 
						url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data : {
							erpser: "changeomsubject",
							omid: omid,
							opid: opid
						}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try{
								if(valideData(data)){
									var index = $('#uindex').val();
									$('#gg').datagrid("updateRow", {
										index: index,
										row: {
											STATETAG: stg
										}
									});
									$('#gg').datagrid('refresh');
								}
							}catch (e) {
								// TODO: handle exception
								console.error(e);top.wrtiejsErrorLog(e);
							}
						}
					});
				}
			}
		});
	}
}
var openomwarebol = true;
function setomwaret(){
	$('#omwarepp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	listomwarecode(p.toString())
        }
	 });
	$('#omwaret').datagrid(
		{
			idField : 'WAREID',
			height : $('body').height()-110,
			fitColumns : true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			columns : [ [
			{
				field : 'WAREID',title : 'ID',width : 200,hidden:true,formatter: amtfm
			},{
				field : 'IMAGENAME',title : 'ID',width : 200,hidden:true
			},{
				field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				title:'图片',
				field:'IMG',
				width:70,
				align:'center',
				fixed :true,
		        formatter:function(value,row,index){
		        	var val =row.IMAGENAME;
		        	if(val!=''&&val!=undefined){
		        		return '<img bigimg="true" style=\"height: 60px;width: 60px;\" src="'+imageurl+val+'_60x60-m3.png" />';
		        	}
		        }
			},{
				field : 'WARENO',title : '货号',width : 100,fixed:true,align:'center',halign:'center'
			},{
				field : 'WARENAME',	title : '商品名称',width : 120,fixed:true,align:'center',halign:'center'
			}, {
				field : 'UNITS',title : '计量单位',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'FULLNAME',title : '商品类型',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'PRODYEAR',title : '年份',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'SEASONNAME',title : '季节',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'WAVENAME',title : '波次',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'SSDATE',title : '上市日期',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'SALERANGE',title : '价格范围',width : 100,fixed:true,align:'center',halign:'center'
			}, {
				field : 'RETAILSALE',title : '零售价',width : 100,fixed:true,align:'right',halign:'center',
				formatter:function(value,row,index){
					return Number(value).toFixed(2);
				}
			}, {
				field : 'PERMITAMT',title : '备货数',width : 80,fixed:true,align:'center',halign:'center',
				hidden: true,
				formatter: amtfm
			}, {
				field : 'PERMITAMT0',title : '备货数',width: 150,fixed:true,align:'center',halign:'center',
				formatter: function(value,row,index){
					var amt = Number(row.PERMITAMT);
					var dis = amt==0?'disabled':'';
					var html = '<div class="edit_div">';
					html += '<span class="down '+dis+'">-</span>';
					html += '<input tyepe="text" value="'+amt+'" data-index="'+index+'">';
					html += '<span class="up">+</span>';
					html += '</div>';
					return html;
				}
			},{
				field: 'MUSTBJ',
				title: '必订项',
				width: 80,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var nouse = Number(value);
					var checked = "";
					if (nouse == 1) checked = "checked";
					if (isNaN(nouse)) return "";
					return '<input type="checkbox" id="mustbjomware_check_' + index + '" class="m-btn" onchange="mustbjomware(' + index + ')" ' + checked + '>';
				}
			},{
				field: 'NOUSED',
				title: '禁用订货',
				width: 80,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var nouse = Number(value);
					var checked = "";
					if (nouse == 1) checked = "checked";
					if (isNaN(nouse)) return "";
					return '<input type="checkbox" id="nousedomware_check_' + index + '" class="m-btn" onchange="nousedomware(' + index + ')" ' + checked + '>';
				}
			}, {
				field : 'ACTION',title : '操作',width: 160,fixed:true,align:'center',halign:'center',
				formatter: function(value,row,index){
					var html ='<input type="button" class="m-btn" value="相关搭配" onclick="openwaredpd(' + index + ')">';
					html +='<input type="button" class="m-btn" value="删除" onclick="delomware(' + index + ')">';
					return html;
				}
			}]],
			toolbar : '#omware_toolbar',
			onDblClickRow: function(index,row){
				$('#uomwareindex').val(index);
				if(openomwarebol){
					updateomwared();
				}
			},
			onDblClickCell: function(index,field,value){
				if(field=="PERMITAMT0"||field=="ACTION")
					openomwarebol = false;
				else
					openomwarebol =true;
			}
		}).datagrid("keyCtr");
	$('#omwaret').parent().delegate('div.edit_div span','click',function(){
		var $this = $(this);
		var $par = $this.parent();
		var $input = $par.find('input');
		if(!$this.hasClass('disabled')){
			var amt = Number($input.val());
			var index = $input.data('index');
			if($this.hasClass('down')){
				$input.val(--amt);
				if(amt==0)
					$this.addClass('disabled');
			}else{
				$input.val(++amt);
				if(amt>0)
					$par.find('span.down').removeClass('disabled');
			}
			updateomwarecode(index,amt);
		}
	}).delegate('div.edit_div input','keyup',function(){
		this.value = this.value.replace(/[^\d]/g, '');
	}).delegate('div.edit_div input','change',function(){
		var $this = $(this);
		var amt = Number($this.val());
		var index = $this.data('index');
		updateomwarecode(index,amt);
	});
}
function openomwared(index){
	var $dg = $('#gg');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	if(row){
		$('#uomid').val(row.OMID);
		if(!$('#omwaret').data().datagrid)
			setomwaret();
		$("#omsearchwarebox").hide();
		resetomwaresearch();
		$('#omwared').dialog('open');
		var stg = Number(row.STATETAG);
		var $omdg = $('#omwaret');
		if(stg<4){
			$omdg.datagrid('showColumn','ACTION')
			.datagrid('showColumn','PERMITAMT0')
			.datagrid('hideColumn','PERMITAMT')
			.datagrid('resize');
			$('#addomwarebtn').show();
		}else{
			$omdg.datagrid('hideColumn','ACTION')
			.datagrid('hideColumn','PERMITAMT0')
			.datagrid('showColumn','PERMITAMT')
			.datagrid('resize');
			$('#addomwarebtn').hide();
		}
		omwaretp=0;
		listomwarecode(1);
	}
}
function omwaretoggle(){
	$("#omsearchwarebox").toggle("fast");
}
function resetomwaresearch(){
	$("#omsearchwarebox").find("#stypeid,#sfullname,#sbrandid,#sbrandname,#sprodyear,#spodyear,#swareremark,#sseasonname,#omwareqsfindbox").val("");
	$('#hp3').prop('checked',true);
// 	listomwarecode(1);
}
function listomwarecode(page){
	alertmsg(6);
	if(omwaretp==0)
		$('#omwaret').datagrid('loadData', nulldata);
	else
		$('#omwareht').datagrid('loadData', nulldata);
	var omid = $('#uomid').val();
	var findbox  = $('#omwareqsfindbox').val();
	if(omwaretp!=0) findbox = $('#omwarefindbox').val();
	var typeid = valideId($("#omsearchwarebox #stypeid").val());
	var brandid = valideId($("#omsearchwarebox #sbrandid").val());
	var seasonname = $("#omsearchwarebox #sseasonname").val();
	var prodyear = $("#omsearchwarebox #sprodyear").val();
	var remark = $("#omsearchwarebox #swareremark").val();
	var havepicture;
	if ($('#omsearchwarebox #hp1').prop('checked')) {
		havepicture = "0";
	} else if ($('#omsearchwarebox #hp2').prop('checked')) {
		havepicture = "1";
	} else {
		havepicture = "2";
	}
	var datajsonstr = {
			omid: omid,
			page: page,
			brandid: brandid,
			typeid: typeid,
			seasonname: seasonname,
			prodyear: prodyear,
			remark: remark,
			havepicture: havepicture,
			sort: 'wareno',
			order: 'asc',
			rows: pagecount,
			lastop: skyuser.EPNAME
		};
	if(findbox.length>0){
		datajsonstr = {
				omid: omid,
				page: page,
				findbox: findbox,
				sort: 'wareno',
				order: 'asc',
				rows: pagecount,
				lastop: skyuser.EPNAME
			};
	}
	datajsonstr.erpser = "listomwarecode";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : datajsonstr, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				if(omwaretp==0){
					$("#omwarepp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omwaretotal').html(data.total);
					$('#omwaret').datagrid('loadData', data);
				}else{
					$("#omwarehpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omwareht').datagrid('loadData', data);
				}
			}
			}catch(e){
				console.error(e);
			}
	}
	});
}
function getomwaredplist(page){
	$('#omwareht').datagrid('loadData', nulldata);
	var erpser = "selectomwarecode1";
	var omid = $('#uomid').val();
	var wareid = $('#uwareid').val();
	var findbox = $('#omwarefindbox').val();
	var typeid = valideId($("#omsearchwarebox1 #stypeid1").val());
	var brandid = valideId($("#omsearchwarebox1 #sbrandid1").val());
	var seasonname = $("#omsearchwarebox1 #sseasonname1").val();
	var prodyear = $("#omsearchwarebox1 #sprodyear1").val();
	var remark = $("#omsearchwarebox1 #swareremark1").val();
	var havepicture;
	if ($('#omsearchwarebox1 #hp11').prop('checked')) {
		havepicture = "0";
	} else if ($('#omsearchwarebox1 #hp12').prop('checked')) {
		havepicture = "1";
	} else {
		havepicture = "2";
	}
	var datajsonstr = {
			erpser: erpser,
			omid: omid,
			wareid: wareid,
			findbox: findbox,
			page: page,
			rows: pagecount
		};
	if(findbox.length>0){
		datajsonstr.findbox = findbox;
	}else{
		$.extend(datajsonstr,{
			brandid: brandid,
			typeid: typeid,
			seasonname: seasonname,
			prodyear: prodyear,
			remark: remark,
			havepicture: havepicture
		});
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : datajsonstr, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到       
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#omwarehpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omwareht').datagrid('loadData', data);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function selectompoleware(page){
	$('#omwareht').datagrid('loadData', nulldata);
	var erpser = "selectompoleware";
	var omid = $('#uomid').val();
	var poleid = $('#upoleid').val();
	var findbox = $('#omwarefindbox').val();
	var typeid = valideId($("#omsearchwarebox1 #stypeid1").val());
	var brandid = valideId($("#omsearchwarebox1 #sbrandid1").val());
	var seasonname = $("#omsearchwarebox1 #sseasonname1").val();
	var prodyear = $("#omsearchwarebox1 #sprodyear1").val();
	var remark = $("#omsearchwarebox1 #swareremark1").val();
	var havepicture;
	if ($('#omsearchwarebox1 #hp11').prop('checked')) {
		havepicture = "0";
	} else if ($('#omsearchwarebox1 #hp12').prop('checked')) {
		havepicture = "1";
	} else {
		havepicture = "2";
	}
	var datajsonstr = {
			erpser: erpser,
			omid: omid,
			poleid: poleid,
			findbox: findbox,
			page: page,
			rows: pagecount
		};
	if(findbox.length>0){
		datajsonstr.findbox = findbox;
	}else{
		$.extend(datajsonstr,{
			brandid: brandid,
			typeid: typeid,
			seasonname: seasonname,
			prodyear: prodyear,
			remark: remark,
			havepicture: havepicture
		});
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : datajsonstr, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#omwarehpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omwareht').datagrid('loadData', data);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function delomwaredp(wareid1){
	var erpser = "delomwarecode1";
	var omid = $('#uomid').val();
	var wareid = $('#uwareid').val();
	$.messager.confirm(dialog_title, '您确认从商品搭配中删除吗？', function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: erpser,
					omid: omid,
					wareid: wareid,
					wareid1: wareid1
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$('#dpimagelist li[data-wareid="'+wareid1+'"]').remove();
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
function updateomwarecode(index,amount){
	var omid = $('#uomid').val();
	var $omdg = $('#omwaret');
	var rows = $omdg.datagrid('getRows');
	var row = rows[index];
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "updateomwarecode",
			omid: omid,
			wareid: row.WAREID,
			amount: amount
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$omdg.datagrid('updateRow',{
						index: index,
						row: {
							PERMITAMT: amount
						}
					}).datagrid('refreshRow',index);
				}
			}catch(e){
				console.error(e);
			}
		}
	});
}
function delomware(index){
	var omid = $('#uomid').val();
	var $omdg = $('#omwaret');
	var rows = $omdg.datagrid('getRows');
	var row = rows[index];
	$.messager.confirm(dialog_title, '您确认从该订货会删除' + row.WARENO + '吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: "delomwarecode",
					omid: omid,
					wareid: row.WAREID
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success:  function(data){
					try{
						if(valideData(data)){
							$omdg.datagrid('deleteRow',index).datagrid('refresh');
						}
					}catch(e){
						console.error(e);
					}
				}
			});
		}
	});
}
function nousedomware(index){
	var omid = $('#uomid').val();
	var $omdg = $('#omwaret');
	var rows = $omdg.datagrid('getRows');
	var row = rows[index];
	var wareid = row.WAREID;
	var $check = $('#nousedomware_check_' + index);
	var noused = 0;
	if ($check.prop('checked')) noused = 1;
	if(noused==1)
		msg="您确认该订货会禁用商品" + row.WARENO + "，不再允许订货吗？";
	else 
		msg="您确认该订货会恢复商品" + row.WARENO + "允许订货？";
	$.messager.confirm(dialog_title, msg, function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: "updateomwarecode",
					omid: omid,
					wareid: wareid,
					noused: noused
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							$omdg.datagrid('updateRow', {
								index: index,
								row: {
									NOUSED: noused
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
		}else{
			$omdg.datagrid('refresh');
		}
	});
}
function mustbjomware(index){
	var omid = $('#uomid').val();
	var $omdg = $('#omwaret');
	var rows = $omdg.datagrid('getRows');
	var row = rows[index];
	var wareid = row.WAREID;
	var $check = $('#mustbjomware_check_' + index);
	var mustbj = 0;
	if ($check.prop('checked')) mustbj = 1;
	if(mustbj==0)
		msg="您确认取消该订货会商品" + row.WARENO + "不作为必订商品";
	else 
		msg="您确认该订货会商品" + row.WARENO + "作为必订订货商品？";
	$.messager.confirm(dialog_title, msg, function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: "updateomwarecode",
					omid: omid,
					wareid: wareid,
					mustbj: mustbj
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							$omdg.datagrid('updateRow', {
								index: index,
								row: {
									MUSTBJ: mustbj
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
		}else{
			$omdg.datagrid('refresh');
		}
	});
}
function selectomware(tp){
	omwaretp = tp;
	omwarehjson = [];
	if($('#omwareht').data().datagrid==undefined)
		setomwareht();
	if(omwaretp==0){
		$('#omwarehelptitle').html('添加订货会商品');
		getomwaredata(1);
		$('#omwareht').datagrid('hideColumn',"PERMITAMT0").datagrid('resize');
	}else if(omwaretp==1){
		$('#omwarehelptitle').html('请选择添加陈列商品');
		selectompoleware(1);
		$('#omwareht').datagrid('showColumn',"PERMITAMT0").datagrid('resize');
	}else if(omwaretp==2){
		$('#omwarehelptitle').html('请选择添加搭配商品');
		getomwaredplist(1);
		$('#omwareht').datagrid('showColumn',"PERMITAMT0").datagrid('resize');
	}
	$("#omsearchwarebox1").hide();
	resetomwaresearch1();
	$('#omwarehelpd').dialog('open');
}

function updateomwared() {
	var rowData = $('#omwaret').datagrid('getSelected');
	if (!rowData) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		getwarecodebyid(rowData.WAREID);
		$('#uwareid').val(rowData.WAREID);
		$('#uptabs').tabs("select", 0);
		getwarepicture();
		salerangelist();
		if(uploader_wareimg!=null)
			uploader_wareimg.reset();
	}
}
//取默认颜色尺码.v
var dfcolorid = "";
var dfcolorname = "";
var dfsizegroupno = "";
var dfcolorcount = 0;
var dfcljson = [];

function getdefaultcs() {
	alertmsg(6);
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwareinfo",
			fs: 1
		},
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var colorname = data.COLORNAME;
				var colorid = data.COLORID;
				var sizegroupno = data.sizegrouplist;
				var size = data.SIZEGROUPNO;
				var seasonlist = data.seasonlist;
				var sizelist = '';
				var sealist = '';
				dfcolorid = colorid;
				dfcolorname = colorname;
				dfsizegroupno = size;
				for (var i in sizegroupno) {
					sizelist += '<option value="' + sizegroupno[i].GROUPNO + '">' + sizegroupno[i].GROUPNO + ':' + sizegroupno[i].SIZELIST + '</option>';
				}
				sizeoptions = sizelist;
				$('#usizetypegroup').append(sizelist);
				cljson = [];
				colorcount = 0;
				if (Number(colorid) > 0) {
					colorcount = 1;
					dfcolorcount = 1;
					var newjson = {
						colorid: colorid,
						colorname: colorname
					};
					cljson.push(newjson);
					dfcljson = copyobjarry(cljson);
				} else {
					dfcljson = [];
					dfcolorcount = 0;
				}
			}
		}
	});
}
//加载品牌列表窗
var hasbranddg = true;

function selectbrand(brandser) {
	$('#hbrandser').val(brandser);
	var findbox = "";
// 	if (brandser == 'add') {
// 		findbox = $('#abrandname').val();
// 	} else if (brandser == 'update') {
// 		findbox = $('#ubrandname').val();
// 	} else {
// 		findbox = $('#sbrandname').val();
// 	}
	$('#brandfindbox').val(findbox);
	if (hasbranddg) {
		$("#brandpp").createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getbranddata(p.toString());
			}
		});
		$('#brandt').datagrid({
			idField: 'brandname',
			height: 350,
			fitColumns: true,
			striped: true, //隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [
				[{
					field: 'BRANDID',
					title: 'ID',
					width: 200,
					hidden: true
				}, {
					field: 'ROWNUMBER',
					title: '序号',
					width: 40,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						var val = Math.ceil(Number(value) / pagecount) - 1;
						return val * pagecount + Number(index) + 1;
					}
				}, {
					field: 'BRANDNAME',
					title: '品牌',
					width: 200
				}]
			],
			onDblClickRow: function(rowIndex, rowData) {
				selectbd();
			},
			toolbar: '#brandsbtn'
		}).datagrid("keyCtr", "selectbd()");
		hasbranddg = false;
	}
	$('#brandf').dialog('open');
	getbranddata('1'); //加载数据
}

function selectbd() {
	var brandser = $('#hbrandser').val();
	var rowData = $('#brandt').datagrid("getSelected");
	if (rowData) {
		if (brandser == 'add') {
			$('#abrandname').val(rowData.BRANDNAME);
			$('#abrandid').val(rowData.BRANDID);
		} else if (brandser == 'update') {
			$('#ubrandname').val(rowData.BRANDNAME);
			$('#ubrandid').val(rowData.BRANDID);
		} else if (brandser == 'search1') {
			$('#sbrandname1').val(rowData.BRANDNAME);
			$('#sbrandid1').val(rowData.BRANDID);
		} else {
			$('#sbrandname').val(rowData.BRANDNAME);
			$('#sbrandid').val(rowData.BRANDID);
		}
		$('#brandf').dialog('close');
	} else {
		alerttext("请选择！");
	}
}
function selectcolor(colorser) {
	$('#colorfindbox').val('');
	$('#hcolorser').val(colorser);
// 	if (colorser == 'add') {
// 		$('#colorf .dialog-footer').show();
// 	} else {
// 		$('#colorf .dialog-footer').hide();

// 	}
	if (!$('#colort').data().datagrid) {
		$("#colorpp").createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				var colorsers = $('#hcolorser').val();
				colorsers == "add" ? getcolordata(p.toString()) : getexistcolorlist(p.toString());
			}
		});
		$('#colorf').dialog({
			modal: true,
			title: '选择颜色'
		});
		$('#colort').datagrid({
			idField: 'colorname',
			height: 350,
			fitColumns: true,
			striped: true, //隔行变色
			singleSelect: true,
			pageSize: 20,
			checkOnSelect: false,
			selectOnCheck: false,
			columns: [
				[{
					field: 'COLORID',
					title: 'ID',
					width: 200,
					hidden: true
				}, {
					field: 'SELECTCOLOR',
					checkbox: true
				}, {
					field: 'ROWNUMBER',
					title: '序号',
					width: 40,
					fixed: true,
					align: 'center',
					halign: 'center',
					formatter: function(value, row, index) {
						var val = Math.ceil(Number(value) / pagecount) - 1;
						return val * pagecount + Number(index) + 1;
					}
				}, {
					field: 'COLORNAME',
					title: '颜色',
					width: 200
				}, {
					field: 'COLORNO',
					title: '色码',
					width: 200
				}, {
					field: 'SELBJ',
					title: 'SELBJ',
					width: 200,
					hidden: true
				}]
			],
			rowStyler: function(index, row) {
				if (row.USEDBJ == 1) {
					return 'background-color:#fff;color:#d7d7d7;font-weight:bold;';
				}
			},
			onLoadSuccess: function(data) {
				var colorsers = $('#hcolorser').val();
				if (data.rows.length > 0) {
					//循环判断操作为新增的不能选择
					var bj = Number($("#bj").val());
					if(colorsers=="update"&&bj==1){
						$('#colorf .datagrid-cell-check input[type=checkbox],#colorf .datagrid-header-check input[type=checkbox]').prop('disabled',true);
					}else{
						for (var i = 0; i < data.rows.length; i++) {
							//根据isFinanceExamine让某些行不可选
							if (data.rows[i].USEDBJ == 1) {
								$("#colorf [datagrid-row-index="+i+"] .datagrid-cell-check :checkbox").prop("disabled",true);
							}
						}
					}
				}
				if (data) {
					clcheck = false;
					var findbox = $('#colorfindbox').val();
					if (colorsers == "update" && findbox.length == 0)
						cljson = [];
					$.each(data.rows, function(index, item) {
						if (colorsers == "add") {
							if (colorcount > 0) {
								for (var i in cljson) {
									if (cljson[i].colorid == item.COLORID)
										$('#colort').datagrid('checkRow', index);
								}
							} else{
								cljson = [];
								$('#colort').datagrid('clearChecked');
							}
						} else {
							if (item.SELBJ == '1') {
								if (!hasKey(item.COLORID)) {
									var newjson = {
										colorid: item.COLORID,
										colorname: item.COLORNAME
									};
									cljson.push(newjson);
								}
								$('#colort').datagrid('checkRow', index);
							}else $('#colort').datagrid('uncheckRow', index);
						}
					});
					clcheck = true;
				}
			},
			onCheck: function(index, row) {
				var colorsers = $('#hcolorser').val();
				if (colorsers == "add") {
					if (clcheck) {
						var newjson = {
							colorid: row.COLORID,
							colorname: row.COLORNAME
						};
						cljson.push(newjson);
						selectcl();
					}
				} else if (colorsers == "update") {
					if (clcheck) {
						setwarecolor(row.COLORID, row.COLORNAME, '1', index);
					}
				}
			},
			onUncheck: function(index, row) {
				var colorsers = $('#hcolorser').val();
				if (Number(row.USEDBJ) == 1) {
					alerttext("颜色已经使用,不允许取消选择!");
					$('#colort').datagrid('checkRow', index);
					clcheck = true;
				}
				if (colorsers == "update") {
					setwarecolor(row.COLORID, row.COLORNAME, '0', index);
				} else {
					for (var i in cljson) {
						if (cljson[i].colorid == row.COLORID) {
							cljson.splice(i, 1);
							break;
						}
					}
					selectcl();
				}
			},
			toolbar: '#colorsbtn'
		}).datagrid("keyCtr", "");
		hascolordg = false;
	}
	$('#colorf').dialog('open');
	getwarecolor('1');
}
/*
	function writetjcolor(colorid,bj){
		if(bj=='0'){
			colorcount = colorcount - 1;
			checkcolor = checkcolor.replace(colorid+"-",''); 
		}else if(bj=='1'){
			colorcount = colorcount+1;
			checkcolor += colorid+'-';
		}
		if(checkcolor.indexOf(colorid) > -1){
				colorcount = colorcount - 1;
				checkcolor = checkcolor.replace(colorid+"-",''); 
		}else{
			colorcount = colorcount+1;
			checkcolor += colorid+'-';
		}
	}
		*/
function getwarecolor(page) {
	var colorser = $('#hcolorser').val();
	if (colorser == 'add') {
		getcolordata(page);
	} else if (colorser == 'update') {
		getexistcolorlist(page);
	}
}
var cljson = [];

function hasKey(key) {
	for (var i in cljson) {
		if (cljson[i].colorid == key)
			return true;
	}
	return false;
}

function selectcl() {
	var colorser = $('#hcolorser').val();
	var l = getJsonLength(cljson);
	colorcount = l;
	var colornames = new Array(l);
	var colorids = new Array(l);
	for (var i in cljson) {
		colornames[i] = cljson[i].colorname;
		colorids[i] = cljson[i].colorid;
	}
	var colorname = colornames.toString();
	var colorid = colorids.toString();
	if (colorser == 'add') {
		$('#acolorname').val(colorname);
		$('#acolorid').val(colorid);
	} else {
		$('#ucolorname').val(colorname);
		$('#ucolorid').val(colorid);
	}

	//	$('#colorf').dialog('close');
}
//获取商品已选颜色
function getexistcolorlist(page) {
	var wareid = $('#uwareid').val();
	var findbox = $('#colorfindbox').val();
	var rdbj = Number($('#uclorusedbj').val());
	alertmsg(6);
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warecolorlist",
			page: page,
			findbox: findbox,
			rows: pagecount,
			rdbj: rdbj,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#colort').datagrid('loadData', data);
				$("#colorpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				clcheck = true;
			}
		}
	});
}

//选择颜色
function setwarecolor(colorid, colorname, value, index) {
	var wareid = $('#uwareid').val();
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "writewarecolor",
			wareid: wareid,
			colorid: colorid,
			value: value
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				if (value == '1') {
					var newjson = {
						colorid: colorid,
						colorname: colorname
					};
					cljson.push(newjson);
				} else {
					for (var i in cljson) {
						if (cljson[i].colorid == colorid) {
							cljson.splice(i, 1);
							break;
						}
					}
				}
				var l = getJsonLength(cljson);
				var colornames = new Array(l);
				var colorids = new Array(l);
				for (var i in cljson) {
					colornames[i] = cljson[i].colorname;
					colorids[i] = cljson[i].colorid;
				}
				var _colorname = colornames.toString();
				var _colorid = colorids.toString();
				if (_colorid != '') {
					$('#ucolorname').val(_colorname);
					$('#ucolorid').val(_colorid);
				}
				var index = $('#uomwareindex').val();
				$('#gg').datagrid('updateRow', {
					index: index,
					row: {
						COLORLIST: _colorname
					}
				});
				$('#gg').datagrid("refresh");
			} else {
				if (value == '1') {
					clcheck = false;
					$('#colort').datagrid('uncheckRow', index);
					clcheck = true;
				} else {
					clcheck = false;
					$('#colort').datagrid('checkRow', index);
					clcheck = true;

				}
				alerttext(text);
			}
		}
	});
}
	
//获取品牌列表
function getbranddata(page) { //定义回调函数
	//url="wareservlet?wareser=getbranddata";
	var findbox = $('#brandfindbox').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "brandhelplist",
			findbox: findbox,
			fieldlist: "*",
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$('#brandt').datagrid('loadData', data);
				$("#brandpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
			}
		}
	});

}
var clcheck = false;
//获取颜色列表
function getcolordata(page) { //定义回调函数
	alertmsg(6);
	var findbox = $('#colorfindbox').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "colorlist",
			findbox: findbox,
			fieldlist: "*",
			noused: 0,
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				$('#colort').datagrid('loadData', data);
				$("#colorpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
			}
		}
	});
}

function validate(f) {
	if ($('#awareno').val() == "" && autowareno != '1') {
		alerttext("货号不能为空！");
		return false;
	} else if ($('#asizetypegroup').val() == "") {
		alerttext("尺码组不能为空");
		return false;
	} else if ($('#acolorid').val().length <= 0) {
		alerttext("颜色组不能为空");
		return false;
	} else {
		return true;
	}
}
//获取商品id数据
function getwarecodebyid(wareid) {
	if (autowareno == '1') {
		$('#uwareno').attr('disabled', true);
	} else {
		$('#uwareno').removeAttr('disabled');
	}
	alertmsg(6);
	$('#updateform0,#updateform1')[0].reset();
	$('#updateform0 input[type=hidden],#updateform1 input[type=hidden]').val("");
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwarecodebyid",
			wareid: wareid,
			fieldlist: "a.wareid,a.wareid1,a.wareno,a.warename,b.fullname,b.typeid,c.brandname,a.waveid,h.wavename," 
			+ "a.units,a.noused,c.brandid,a.seasonname,a.prodyear,a.prodno,a.salerange," 
			+ "a.sizegroupno,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.ssdate," 
			+ "a.sale5,a.accid1,a.gbbar,a.imagename0,a.provid,f.provname,a.locale,a.tjtag,a.dj,a.jlman," 
			+ "a.useritem5,a.useritem4,a.useritem3,a.useritem2,a.useritem1,a.xdsm,a.areaid,g.areaname,a.remark"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				if (data.total > 0) {
					var row = data.rows;
					var arrs1 = row[0];
					var status = arrs1.NOUSED;
					var wareid1 = Number(arrs1.WAREID1);
					if (wareid1 > 0) {
						$("#usizetypegroup").html('<option>' + arrs1.SIZEGROUPNO + '</option>');
						$('#usizetypegroup').attr("disabled", true);
						$('#uclorusedbj').val('1');
					} else {
						$("#usizetypegroup").html(sizeoptions);
						$('#usizetypegroup').attr("disabled", true);
						$('#uclorusedbj').val('0');
					}
					var bj = Number(arrs1.BJ);
					$('#bj').val(bj);
					if (bj == 1) {
						$("#usizetypegroup").attr("disabled", true);
					} else if (bj == 0 && wareid1 <= 0) {
						$("#usizetypegroup").removeAttr("disabled");
					}
					if (wareid1 <= 0)
						$('#usizetypegroup').val(arrs1.SIZEGROUPNO);
					$('#uprovname').val(arrs1.PROVNAME);
					$('#uprovid').val(arrs1.PROVID);
					$('#uwareno').val(arrs1.WARENO);
					$('#ulocale').val(arrs1.LOCALE);
					$('#uareaid').val(arrs1.AREAID);
					$('#uwareid').val(arrs1.WAREID);
					$('#uwarename').val(arrs1.WARENAME);
					$('#ufullname').val(arrs1.FULLNAME);
					$('#utypeid').val(arrs1.TYPEID);
					$('#ubrandname').val(arrs1.BRANDNAME);
					$('#ubrandid').val(arrs1.BRANDID);
					$('#ucolorname').val(arrs1.COLORNAMELIST);
					$('#uprodyear').val(arrs1.PRODYEAR);
					$('#useasonname').val(arrs1.SEASONNAME);
					$('#uunits').val(arrs1.UNITS);
					$('#uprodno').val(arrs1.PRODNO);
					$('#ugbbar').val(arrs1.GBBAR);
					$('#udj').val(arrs1.DJ);
					$('#ussdate').val(arrs1.SSDATE);
					$('#ujlman').val(arrs1.JLMAN);
					$('#usjman').val(arrs1.SJMAN);
					$('#uwaveid').val(arrs1.WAVEID);
					$('#uwavename').val(arrs1.WAVENAME);
					$('#uomremark').val(arrs1.REMARK);
					$('#usalerange').val(arrs1.SALERANGE);
					if (Number(allowinsale) == 1) {
						$('#uentersale').val(Number(arrs1.ENTERSALE != '' ? arrs1.ENTERSALE : '0').toFixed(points));
						$('#uentersale').removeAttr("disabled");
						// 									$('#uchecksale').removeAttr("disabled");
						// 									$('#uchecksale').val(Number(arrs1.CHECKSALE).toFixed(points));
					} else {
						$('#uentersale').val('****');
						$('#uentersale').attr("disabled", true);
						// 									$('#uchecksale').val('****');
						// 									$('#uchecksale').attr("disabled", true);
					}
					$('#uretailsale').val(Number(arrs1.RETAILSALE).toFixed(2));
					$('#usale1').val(Number(arrs1.SALE1).toFixed(2));
					$('#usale2').val(Number(arrs1.SALE2).toFixed(2));
					$('#usale3').val(Number(arrs1.SALE3).toFixed(2));
					$('#usale4').val(Number(arrs1.SALE4).toFixed(2));
					$('#usale5').val(Number(arrs1.SALE5).toFixed(2));
					/*$('#uuseritem1').val(arrs1.USERITEM1);
								$('#uuseritem2').val(arrs1.USERITEM2);
								$('#uuseritem3').val(arrs1.USERITEM3);
								$('#uuseritem4').val(arrs1.USERITEM4);
								$('#uuseritem5').val(arrs1.USERITEM5);*/
					var u1 = arrs1.USERITEM1.split("<br/> ");
					if (u1.length >= 1) {
						var str = "";
						for (var i in u1){
							if(i>=3)
								break;
							if(i>0)
								str += "\n";
							str += u1[i];
						}
						$('#uuseritem1').val(str);
					}
					var u2 = arrs1.USERITEM2.split("<br/> ");
					if (u2.length >= 1) {
						var str = "";
						for (var i in u2){
							if(i>=3)
								break;
							if(i>0)
								str += "\n";
							str += u2[i];
						}
						$('#uuseritem2').val(str);
					}
					var u3 = arrs1.USERITEM3.split("<br/> ");
					if (u3.length >= 1) {
						var str = "";
						for (var i in u3){
							if(i>=3)
								break;
							if(i>0)
								str += "\n";
							str += u3[i];
						}
						$('#uuseritem3').val(str);
					}
					var u4 = arrs1.USERITEM4.split("<br/> ");
					if (u4.length >= 1) {
						var str = "";
						for (var i in u4){
							if(i>=3)
								break;
							if(i>0)
								str += "\n";
							str += u4[i];
						}
						$('#uuseritem4').val(str);
					}
					var u5 = arrs1.USERITEM5.split("<br/> ");
					if (u5.length >= 1) {
						var str = "";
						for (var i in u5){
							if(i>=3)
								break;
							if(i>0)
								str += "\n";
							str += u5[i];
						}
						$('#uuseritem5').val(str);
					}
					var u6 = arrs1.XDSM.split("<br/> ");
					if (u6.length >= 1) {
						var str = "";
						for (var i in u6){
							if(i>=3)
								break;
							if(i>0)
								str += "\n";
							str += u6[i];
						}
						$('#uxdsm').val(str);
					}
					$('#uzxbz').val(arrs1.ZXBZ);
					if (status != '0') {
						$('#unouse').prop('checked', 'true');
					} else {
						$('#unouse').removeAttr('checked');
					}
					var tjtag = Number(arrs1.TJTAG);
					if(tjtag==1)
						$('#utjtag').prop('checked');
					else
						$('#utjtag').removeProp('checked');
				}
			}
		}
	});
	$('#uomwared').dialog('open');
}
//添加颜色
function addcolor() {
	var colorname = $('#color_add_colorname').val();
	var colorno = $('#color_add_colorno').val();
	if (colorname == "" || colorname == "null" || colorname == "undefined") {
		alerttext("颜色名不能为空");
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addcolor",
				colorname: colorname,
				colorno: colorno
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					$('#color_add_colorno').val("");
					$('#color_add_colorname').val("").focus();
				}
			}
		});
	}
}
//添加品牌
function addbrand() {
	var brandname = $('#brand_add_brandname').val();
	if (brandname == "" || brandname == "null" || brandname == "undefined") {
		alerttext("品牌名不能为空");
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST", //访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addbrand",
				brandname: brandname
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					getbranddata('1');
					$('#brand_add_brandname').val("");
					$('#brand_add_brandname').focus();
				}
			}
		});
	}
}
function getwarepicture() {
	alertmsg(6);
	var wareid = $('#uwareid').val();
	$('#imageMenu ul li').remove();
	$('#midimg').attr("src", "");
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "listwarepicture",
			dhhbj: 1,
			wareid: wareid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				var li = '';
				var imgno = 0;
				if (data.total > 0) {
					var rows = data.rows;
					for (var i in rows) {
						var row = rows[i];
						if (row.IMAGENAME != "") {
							imgno++;
							if (imgno == 1) {
								li += '<li id="onlickImg" data-imgid="'+row.ID+'" title="双击替换">'
								+'<span class="icon_removeimg"></span>'
								+'<img src="' + imageurl + row.IMAGENAME + '_60x60-m3.png" width="68" height="68" alt="加载中……"/></li>';
								$('#midimg').attr("src", imageurl + row.IMAGENAME);
								$('#upimgid').val(row.ID);
								var index = $('#uomwareindex').val();
								var ggrows = $('#omwaret').datagrid('getRows');
								var ggrow = ggrows[index];
								if(ggrow.IMAGENAME!=row.IMAGENAME){
									$('#omwaret').datagrid('updateRow',{
										index: index,
										row: {
											IMAGENAME: row.IMAGENAME
										}
									});
									$('#omwaret').datagrid('refreshRow',index);
								}
							} else {
								li += '<li data-imgid="'+row.ID+'" title="双击替换">'
								+'<span class="icon_removeimg"></span>'
								+'<img src="' + imageurl + row.IMAGENAME + '_60x60-m3.png" width="68" height="68" alt="加载中……"/></li>';
							}
						}
					}
				}
				if(imgno<10)
					li += '<li data-imgid="" class="icon_uploadimg" title="点击上传"></li>';
				$('#imageMenu ul').append(li);
				$('#sortimgbtn').hide();
			}
		}
	});
}

function delwarepicture(imgid){
	$.messager.confirm(dialog_title, '确认删除该商品图片', function(r) {
		if(r){
			alertmsg(2);
			var wareid = $('#uwareid').val();
			$.ajax({
				type: "POST", //访问WebService使用Post方式请求 
				url: "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delwarepicture",
					imgid: imgid,
					dhhbj: 1,
					wareid: wareid
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						getwarepicture();
					}
				}
			});
		}
	});
}

function sortwarepicture() {
	var imgidarr = $('#wareimgsort').val().split(",");
	var wareid = $('#uwareid').val();
	var imgidlist = [];
	if(imgidarr.length>0){
		for(var i in imgidarr){
			var imgid = Number(imgidarr[i]);
			if(imgid>0){
				imgidlist.push({
					imgid: imgid
				});
			}
		}
	}else return;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "sortwarepicture",
			sortlist: JSON.stringify(imgidlist),
			dhhbj: 1,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				getwarepicture();
			}
		}
	});
}
//验证货号
function getwarenoyz(wnyz) {
	if (wnyz == "add") {
		var wareno = $('#awareno').val();
		var wareid = "";
		$('#awareno').val(wareno.toUpperCase());
	} else if (wnyz == "update") {
		var wareno = $('#uwareno').val();
		var wareid = $('#uwareid').val();
		$('#uwareno').val(wareno.toUpperCase());
	}

	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warenoexists",
			wareid: Number(wareid),
			wareno: wareno.toUpperCase()
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
			}
		}
	});
}
//修改商品
function updateWareCode() {
	var wareid = $('#uwareid').val();
	var wareno = $("#uwareno").val();
	var typeid = $("#utypeid").val();
	if (typeid == "") {
		typeid = "0";
	}
	var warename = $("#uwarename").val();
	var fullname = $("#ufullname").val();
	var units = $("#uunits").val();
	var brandid = $("#ubrandid").val();
	if (brandid == "") {
		brandid = "0";
	}
	var seasonname = $("#useasonname").val();
	var locale = $("#ulocale").val();
	var colorids = $("#ucolorid").val();
	var bj = Number($('#bj').val());
	var sizetypegroup = $("#usizetypegroup").val();
	var prodyear = $("#uprodyear").val();
	var prodno = $("#uprodno").val();
	var gbbar = $("#ugbbar").val();
	var entersale = $("#uentersale").val();
	var retailsale = $("#uretailsale").val();
	var sale1 = $("#usale1").val();
	var sale2 = $("#usale2").val();
	var sale3 = $("#usale3").val();
	var sale4 = $("#usale4").val();
	var sale5 = $("#usale5").val();
	var salerange = $("#usalerange").val();
	sale1 = sale1 == '' ? '0' : sale1;
	sale2 = sale2 == '' ? '0' : sale2;
	sale3 = sale3 == '' ? '0' : sale3;
	sale4 = sale4 == '' ? '0' : sale4;
	sale5 = sale5 == '' ? '0' : sale5;
	var tta1 = $("#uuseritem1").val();
	var tta2 = $("#uuseritem2").val();
	var tta3 = $("#uuseritem3").val();
	var tta4 = $("#uuseritem4").val();
	var tta5 = $("#uuseritem5").val();
	var zxbz = $("#uzxbz").val();
	var xdsm = $("#uxdsm").val();
	var ssdate = $("#ussdate").val();
	var remark = $('#uomremark').val();
	$('#unouse').prop('checked') ? $('#unoused').val('1') : $('#unoused').val('0');
	var noused = $('#unoused').val();
	var provname = $('#uprovname').val();
	var provid = $('#uprovid').val();
	var waveid = Number($("#uwaveid").val());
	var sjman = $("#usjman").val();
	var jlman = $("#ujlman").val();
	var areaid = $("#uareaid").val();
	var dj = $("#udj").val();
	var tjtag = 0;
	if ($('#utjtag').prop('checked')) tjtag = 1;
	var ajaxdata = {
			erpser: "updatewarecodebyid",
			wareid: wareid,
			wareno: wareno,
			typeid: typeid,
			warename: warename,
			units: units,
			brandid: brandid,
			ssdate: ssdate,
			seasonname: seasonname,
			colorid: colorids,
			prodyear: prodyear,
			prodno: prodno,
			gbbar: gbbar,
			ssdate: ssdate,
			retailsale: retailsale,
			sale1: sale1,
			sale2: sale2,
			sale3: sale3,
			sale4: sale4,
			sale5: sale5,
			noused: noused,
			useritem1: tta1,
			useritem2: tta2,
			useritem3: tta3,
			useritem4: tta4,
			useritem5: tta5,
			zxbz: zxbz,
			xdsm: xdsm,
			remark: remark,
			provid: provid,
			waveid: waveid,
			areaid: areaid,
			sjman: sjman,
			jlman: jlman,
			salerange: salerange,
			dj: dj,
			locale: locale
		};
	if(bj==0) ajaxdata["sizegroupno"] = sizetypegroup;
	if(allowinsale==1) ajaxdata["entersale"] = entersale;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: ajaxdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$('#uomwared').dialog('close');
				var index = $('#uomwareindex').val();
					$('#omwaret').datagrid('updateRow', {
						index: index,
						row: {
							WAREID: wareid,
							WARENO: wareno,
							SALERANGE: salerange,
							WARENAME: $('#uwarename').val(),
							UNITS: units,
							FULLNAME: fullname,
							SEASONNAME: seasonname,
							SSDATE: ssdate,
							PRODYEAR: prodyear,
							RETAILSALE: retailsale,
							WAVENAME: $('#uwavename').val()
						}
					});
				$('#omwaret').datagrid("refreshRow",index);
			}
		}
	});
}
function getomwarecolor(wareid){
	colororsizebj = 0;
	$("#selectsized").dialog("setTitle","选择可用颜色");
	alertmsg(6);
	$('#size_ul').html('');
	var omid = $("#uomid").val();
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "omwarecolorlist",
			omid: omid,
			wareid: wareid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				if (data.total > 0) {
					var rows = data.rows;
					var html = "";
					for (var i in rows) {
						var row = rows[i];
						var sizeid = row.COLORID;
						var sizename = row.COLORNAME;
						var selbj = Number(row.SELBJ);
						var cl_selbj = selbj == 1 ? "selbj" : "";
						html += '<li data-sizeid="' + sizeid + '" class="' + cl_selbj + '">' + sizename + '</li>'
					}
					$('#size_ul').html(html);
					$('#selectsized').dialog('open');
				} else
					alerttext("该商品没有任何颜色！");
			}
		}
	});
}

function getwaresize(wareid) {
	colororsizebj = 1;
	$("#selectsized").dialog("setTitle","选择可用尺码");
	alertmsg(6);
	$('#size_ul').html('');
	$.ajax({
		type: "POST", //访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "waresizelist",
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				if (data.total > 0) {
					var rows = data.rows;
					var html = "";
					for (var i in rows) {
						var row = rows[i];
						var sizeid = row.SIZEID;
						var sizename = row.SIZENAME;
						var selbj = Number(row.SELBJ);
						var isused = Number(row.ISUSED);
						var cl_selbj = selbj == 1 ? "selbj" : "";
						var cl_isused = isused == 1 ? "isused" : "";
						html += '<li data-sizeid="' + sizeid + '" class="' + cl_selbj + ' ' + cl_isused + '">' + sizename + '</li>'
					}
					$('#size_ul').html(html);
					$('#selectsized').dialog('open');
				} else
					alerttext("该商品未选择尺码组！");
			}
		}
	});
}
var colororsizebj = 0;//0--颜色；1--尺码
function saveColorOrSize(){
	if(colororsizebj==0)
		writewarecolor();
	else 
		writewaresize();
}
function writewarecolor() {
	var wareid = Number($('#uwareid').val());
	var omid = Number($('#uomid').val());
	if (wareid > 0) {
		var count = $('#size_ul li.changed').length;
		if (count == 0) {
			$('#selectsized').dialog('close');
			return;
		}
		var sizeobjs = [];
		$('#size_ul li.changed').each(function() {
			var li = this;
			var sizeid = Number($(li).data('sizeid'));
			var selbj = $(this).hasClass('selbj');
			var value = 0;
			if (selbj)
				value = 1;
			var obj = {
				colorid: sizeid,
				value: value
			};
			sizeobjs.push(obj);
		});
		$.messager.confirm(dialog_title, '您确认修改该商品这些颜色订货！', function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST", //访问WebService使用Post方式请求 
					url: "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					async: false,
					data: {
						erpser: "writeomwarenocolor",
						wareid: wareid,
						omid: omid,
						colorlist: JSON.stringify(sizeobjs)
					}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							$('#selectsized').dialog('close');
						}
					}
				});
			}
		});
	} else
		alerttext("未选择商品ID");
}

function writewaresize() {
	var wareid = Number($('#uwareid').val());
	if (wareid > 0) {
		var count = $('#size_ul li.changed').length;
		if (count == 0) {
			$('#selectsized').dialog('close');
			return;
		}
		var sizeobj = {};
		var sizeobjs = [];
		$('#size_ul li.changed').each(function() {
			var li = this;
			var sizeid = Number($(li).data('sizeid'));
			var selbj = $(this).hasClass('selbj');
			var value = 0;
			if (selbj)
				value = 1;
			var obj = {
				sizeid: sizeid,
				value: value
			};
			sizeobjs.push(obj);
		});
		$.messager.confirm(dialog_title, '您确认修改该商品这些尺码订货！', function(r) {
			if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST", //访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				async: false,
				data: {
					erpser: "writewaresize",
					wareid: Number(wareid),
					sizelist: JSON.stringify(sizeobjs)
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						$('#selectsized').dialog('close');
					}
				}
			});
			}
		});
	} else
		alerttext("未选择商品ID");
}

var omwarehjson = [];
var omwarecheck = false;
var omwaretp = 0;
function setomwareht(){
	$('#omwarehpp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	if(omwaretp==0)
        		getomwaredata(p.toString());
        	else if(omwaretp==2)
    			getomwaredplist(p);
        	else
        		selectompoleware(p.toString());
        }
	 });
	$('#omwareht').datagrid(
		{
			idField : 'WAREID',
			height : $('#omwarehelpd').height()-50,
			fitColumns : true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			checkOnSelect: false,
			selectOnCheck: false,
			columns : [ [
			{
				field : 'WAREID',title : 'ID',width : 200,hidden:true
			},{
				field : 'CHECK',title : 'check',checkbox: true
			},{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				title:'图片',
				field:'IMAGENAME',
				width:70,
				align:'center',
				fixed :true,
		        formatter:function(value,row,index){
		        	if(value!=''&&value!=undefined){
		        		return '<img bigimg="true" style=\"height: 60px;width: 60px;\" src="'+imageurl+row.IMAGENAME+'_60x60-m3.png" />';
		        	}
		        }
			},{
				field : 'WARENO',title : '货号',width : 100,fixed:true,align:'center',halign:'center'
			},{
				field : 'WARENAME',	title : '商品名称',width : 120,fixed:true,align:'center',halign:'center'
			}, {
				field : 'UNITS',title : '计量单位',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'FULLNAME',title : '商品类型',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'PRODYEAR',title : '年份',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'SEASONNAME',title : '季节',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'WAVENAME',title : '波次',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'SALERANGE',title : '价格范围',width : 100,fixed:true,align:'center',halign:'center'
			}, {
				field : 'RETAILSALE',title : '零售价',width : 100,fixed:true,align:'right',halign:'center',
				formatter:function(value,row,index){
					return Number(value).toFixed(2);
				}
			}, {
				field : 'PERMITAMT0',title : '备货数',width : 100,fixed:true,align:'right',halign:'center',
				formatter: amtfm
			}]],
			toolbar : '#omwaresbtn',
			onLoadSuccess: function(data) {
				omwarecheck = false;
				var rows = data.rows;
				if(rows.length>0&&omwarehjson.length>0){
					for(var i in rows){
						var row = rows[i];
						var wareid = Number(row.WAREID);
						if(jsonhaskey(omwarehjson,'',wareid)){
							$('#omwareht').datagrid('checkRow', i);
						}else $('#omwareht').datagrid('uncheckRow', i);
					}
				}else{
					$('#omwareht').datagrid('clearChecked');
				}
				omwarecheck = true;
			},
			selectjson: [],
			onCheck: function(index,row){
				if(omwarecheck){
					var wareid = Number(row.WAREID);
					omwarehjson.push(wareid);
				}
			},
			onCheckAll: function(rows){
				if(omwarecheck){
					for(var i in rows){
						var row = rows[i];
						var wareid = Number(row.WAREID);
						if(!jsonhaskey(omwarehjson,'',wareid)){
							omwarehjson.push(wareid);
						}
					}
				}
			},
			onUncheck: function(index,row){
				if(omwarecheck){
					var wareid = Number(row.WAREID);
					deljsonbykey(omwarehjson,"",wareid);
				}
			},
			onUncheckAll: function(rows){
				if(omwarecheck){
					for(var i in rows){
						var row = rows[i];
						var wareid = Number(row.WAREID);
						deljsonbykey(omwarehjson,"",wareid);
					}
				}
			},
		}).datagrid("keyCtr");
}

function jsonhaskey(json,key,value) {
	for (var i in json) {
		if(key==""){
			if (json[i] == value)
				return true;
		}else if (json[i][key] == value)
			return true;
	}
	return false;
}
function deljsonbykey(json,key,value){
	for (var i in json) {
		if(key==""){
			if (json[i] == value)
				json.splice(i,1);
		}else if (json[i][key] == value)
			json.splice(i,1);
	}
}
function omwaretoggle1(){
	$("#omsearchwarebox1").toggle("fast");
}
function resetomwaresearch1(){
	$("#omsearchwarebox1").find("#stypeid1,#sfullname1,#sbrandid1,#sbrandname1,#sprodyear1,#spodyear1,#swareremark1,#sseasonname1,#omwareqsfindbox").val("");
	$("#omsearchwarebox1").find('#hp13').prop('checked',true);
// 	getomwaredata1(1);
}
function getomwaredata1(page){
	if(omwaretp==0)
		getomwaredata(page);
	else if(omwaretp==2)
		getomwaredplist(page);
	else
		selectompoleware(page);
}

function getomwaredata(page){
	alertmsg(6);
	$('#omwareht').datagrid('loadData', nulldata);
	var omid = $('#uomid').val();
	var findbox = $('#omwarefindbox').val();
	var typeid = valideId($("#omsearchwarebox1 #stypeid1").val());
	var brandid = valideId($("#omsearchwarebox1 #sbrandid1").val());
	var seasonname = $("#omsearchwarebox1 #sseasonname1").val();
	var prodyear = $("#omsearchwarebox1 #sprodyear1").val();
	var remark = $("#omsearchwarebox1 #swareremark1").val();
	var havepicture;
	if ($('#omsearchwarebox1 #hp11').prop('checked')) {
		havepicture = "0";
	} else if ($('#omsearchwarebox1 #hp12').prop('checked')) {
		havepicture = "1";
	} else {
		havepicture = "2";
	}
	var datajsonstr = {
			erpser: "warecodelisthelp",
			omid: omid,
			page: page,
			sort: 'wareno',
			order: 'asc',
			noused: 0,
			rows: pagecount,
			fieldlist: 'a.wareid,a.wareno,a.warename,a.units,a.retailsale,a.salerange,a.prodyear,a.seasonname,b.typename,b.fullname,c.brandname,'
						+'a.waveid,h.wavename,a.imagename'
		};
	if(findbox.length>0){
		datajsonstr.findbox = findbox;
	}else{
		$.extend(datajsonstr,{
			brandid: brandid,
			typeid: typeid,
			seasonname: seasonname,
			prodyear: prodyear,
			remark: remark,
			havepicture: havepicture
		});
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : datajsonstr, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data){
			try{
				if(valideData(data)){
					$("#omwarehpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omwarehtotal').html(data.total);
					$('#omwareht').datagrid('loadData', data);
				}
			}catch(e){
				console.error(e);
			}
		}
	});
}

function saveomware(){
	if(omwarehjson.length==0){
		alerttext('选择商品不能为空！');
		return;
	}
	alertmsg(2);
	if(omwaretp==0){
		var omid = $('#uomid').val();
		var warereclist = [];
		for(var i in omwarehjson){
			warereclist.push({
				wareid: omwarehjson[i],
				mustbj:0
			});
		}
		var skyser= "addomwarecode";
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "addomwarecode",
				omid: omid,
				warereclist: JSON.stringify(warereclist)
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data){
				try{
					if(valideData(data)){
						$("#omwarefindbox").val("");
						$('#omwarehelpd').dialog('close');
						listomwarecode(1);
					}
				}catch(e){
					console.error(e);
				}
			}
		});
	}else if(omwaretp==1){
		var poleid = $('#upoleid').val();
		var warelist = [{}];
		var count = omwarehjson.length;
		for(var i in omwarehjson){
			warelist[i] = {
					wareid: omwarehjson[i]
			}
		}
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "saveompoleware",
				warelist: JSON.stringify(warelist),
				poleid: poleid
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)){
						listompoleware();
						$("#omwarefindbox").val("");
						$('#omwarehelpd').dialog('close');
					}
				}catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}else if(omwaretp==2){
		var omid = $('#uomid').val();
		var wareid = $('#uwareid').val();
		var warelist = [{}];
		var count = omwarehjson.length;
		for(var i in omwarehjson){
			warelist[i] = {
					wareid: omwarehjson[i]
			}
		}
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "saveomwarecode1",
				warelist: JSON.stringify(warelist),
				omid: omid,
				wareid: wareid
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)){
						$('#omwarehelpd').dialog('close');
						$("#omwarefindbox").val("");
						listompoleware();
					}
				}catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
	
}

function openomcustd(index){
	var $dg = $('#gg');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	if(row){
		$('#uomid').val(row.OMID);
		$('#omcustd').dialog('open');
		if($('#omcustt').data().datagrid==undefined){
			setomcustt();
			setomusert();
		}
		var stg = Number(row.STATETAG);
		getomcustlist(1);
	}
}
var custcheck = false;
function setomcustt(){
	$('#omcustpp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	getomcustlist(p.toString())
        }
	 });
	$('#omcustt').datagrid(
		{
			title: "客户列表",
			idField : 'CUSTID',
			height: $('#omcustd').height()-60,
			fitColumns: true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			checkOnSelect: false,
			selectOnCheck: false,
			columns: [ [
				{
					field : 'CUSTID',title : 'ID',width : 200,hidden:true
				},{
					field : 'SELBJ',title : 'SELBJ',width : 200,hidden:true
				},{
				field : 'CHECK',title : 'check',checkbox: true
			},{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				field : 'CUSTNAME',title : '客户名称',width : 100,fixed:true,align:'center',halign:'center'
			},{
				field : 'LINKMAN',	title : '联系人',width : 100,fixed:true,align:'center',halign:'center'
			}, {
				field : 'MOBILE',title : '联系方式',width : 120,fixed:true,align:'center',halign:'center'
			}, {
				field : 'ADDRESS',title : '地址',width : 160,fixed:true,align:'center',halign:'center'
			}]],
			toolbar : '#omcust_toolbar',
			onLoadSuccess: function(data) {
				custcheck = false;
				var rows = data.rows;
				if(rows.length>0){
					for(var i in rows){
						var row = rows[i];
						var selbj = Number(row.SELBJ);
						if(selbj==1){
							$('#omcustt').datagrid('checkRow', i);
						}else 	$('#omcustt').datagrid('uncheckRow', i);
					}
				}
				custcheck = true;
			},
			onCheck: function(index,row){
				if(custcheck){
					var custid = Number(row.CUSTID);
					writeomcustomer(index,custid,1);
				}
			},
			onCheckAll: function(rows){
				if(custcheck){
					if(rows.length>0){
						allomcustomer(1);
					}
				}
			},
			onUncheck: function(index,row){
				if(custcheck){
					var custid = Number(row.CUSTID);
					writeomcustomer(index,custid,0);
				}
			},
			onUncheckAll: function(rows){
				if(custcheck){
					if(rows.length>0){
						allomcustomer(0);
					}
				}
			},
			onSelect: function(index,row){
				if(row){
					$('#uomcustid').val(row.CUSTID);
					$('#omuserttitle').html(row.CUSTNAME);
					getomuserlist(1);
					$('#custuserdiv input:not(.btn3)').removeProp('disabled');
				}
			}
		}).datagrid("keyCtr");
}

function getomcustlist(page){
	alertmsg(6);	
	$('#omcustt').datagrid('loadData', nulldata);
	var omid = $('#uomid').val();
	var findbox = $('#omcustqsfindbox').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "listomcustomer",
			page: page,
			rows: pagecount,
			findbox: findbox,
			sort: 'custname',
			order: 'asc',
			omid: omid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#omcustpp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#omcusttotal').html(data.total);
					$('#omcustt').datagrid('loadData', data);
					if($('#omcustt').datagrid('getRows').length>0)
						$('#omcustt').datagrid('selectRow', 0);
					$('#omusert').datagrid('loadData', nulldata);
					$('#custuserdiv input:not(.btn3)').prop('disabled',true);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//增减订货会客户
function writeomcustomer(index,custid,value){
	var omid = $('#uomid').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "writeomcustomer",
			custid: custid,
			value: value,
			omid: omid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					
				}else{
					if(value==0)
						$('#omcustt').datagrid('checkRow', index);
					else
						$('#omcustt').datagrid('uncheckRow', index);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//全选增减客户
function allomcustomer(value){
	alertmsg(2);
	var omid = $('#uomid').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "allomcustomer",
			value: value,
			omid: omid
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					
				}else{
					$('#omcustpp').refreshPage();
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
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
			title: '<span id="omuserttitle" >客户成员管理</span>',
			height: $('#omcustd').height()-60,
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
				field : 'JS',title : '订货方式',width : 100,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val =Number(value);
					if(!isNaN(val)){
						if(val==0)
							return "本部";
						if(val==1)
							return "自营店";
						if(val==2)
							return "经销商";
					}
					return "";
					
				}
			}, {
				field : 'JHAMT',title : '订货量',width : 80,fieldtype:"G",fixed:true,align:'center',halign:'center',formatter: amtfm
			}, {
				field : 'JHCURR',title : '订货额',width : 80,fieldtype:"N",fixed:true,align:'center',halign:'center',formatter: currfm
			}, {
				field : 'JHNUM',title : '订货款',width : 80,fieldtype:"G",fixed:true,align:'center',halign:'center',formatter: amtfm
			}, {
				field : 'STATETAG',title : '登录权限',width : 80,fieldtype:"G",fixed:true,align:'center',halign:'center',
				formatter: function(value, row, index) {
					var nouse = Number(value);
					var checked = "";
					if (nouse == 1) checked = "checked";
					if (isNaN(nouse)) return "";
					return '<input type="checkbox" id="nousedomuser_check_' + index + '" class="m-btn" onchange="changeomuserldl(' + index + ')" ' + checked + '>';
				}
			}, {
				field : 'ACTION',title : '操作',width : 80,fixed:true,align:'center',halign:'center',
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
				updateomuserd();
			},
			toolbar : '#omuser_toolbar'
		}).datagrid("keyCtr","updateomuserd()");
}
//获取订货会用户记录
function getomuserlist(page){
	$('#omusert').datagrid('loadData', nulldata);
	var omid = $('#uomid').val();
	var custid = Number($('#uomcustid').val());
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
			omid: omid,
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
	var omid = $('#uomid').val();
	var custid = $('#uomcustid').val();
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
	if(omepno.length==0){
		alerttext('请填写用户账号');
		return;
	}
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
			omid: omid,
			omepid: omepid,
			omepno: omepno,
			omepname: omepname,
			mobile: mobile,
			password: password,
			js: js,
			jhamt: jhamt,
			jhcurr: jhcurr,
			jhnum: jhnum,
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
	var omid = $('#uomid').val();
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
					omid: omid,
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
function setomuserpwd(){
	$.messager.confirm(dialog_title, '您确认自动设置此订货会<span style="color:red">所有已有用户</span>的随机登陆密码？', function(r) {
		if (r) {
			var omid = $('#uomid').val();
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: "setomuserpwd",
					omid: omid
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$("#omcustpp").refreshPage();
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
function autotoomuserno(){
	$.messager.confirm(dialog_title, '您确认自动产生本次订货会<span style="color:red">所有已有用户</span>的账号名吗？', function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: "autotoomuserno"
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$("#omcustpp").refreshPage();
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
function salerangelist(){
	$('#salerangelist').html('');
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "salerangelist"
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					if(data.total>0){
						var rows = data.rows;
						var html = "";
						for(var i in rows){
							var row = rows[i];
							html += '<option value="'+row.SALERANGE+'" >';
						}
						$('#salerangelist').html(html);
					}
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function openimportxlsd(xlstype){
	if(uploader_xls==null){
		uploader_xls = SkyUploadFiles(uploader_xls_options);
	}
	if(xlstype=="user"){
		uploader_xls.on("startUpload",function(){
			var omid = $("#uomid").val();
			uploader_xls.option('formData', {
				omid: omid
			});
		});
		uploader_xls.on("uploadComplete",function(file){
			$('#omuserpp').refreshPage();
		});
		setuploadserver({
			server: "/skydesk/fyimportservice?importser=appendomuserxls&server=ords",
			xlsmobanname: "omuser",
			channel: "omuser"
		});
	}else if(xlstype=="ware"){
		uploader_xls.on("uploadComplete",function(file){
			$('#omwarepp').refreshPage();
		});
		setuploadserver({
			server: "/skydesk/importservice?flyangser=appendomwarexls",
			xlsmobanname: "omware",
			channel: "omware"
		});
	}
	openimportd();
}
function exportuserxls(){
	fyexportxls(1,'#omusert',{
		erpser: "listomuser",
		rows: pagecount,
		sort: 'omepno',
		order: 'asc',
		omid: $("#uomid").val(),
		custid: 0
	},'ords',0,"订货会用户成员列表");
}
//打开商品陈列表
function openompoled(index){
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	if(row){
		$('#uomid').val(row.OMID);
		if(!$('#ompolet').data().datagrid){
			setompolet();
		}
		getompolelist(1);
		addompoled();
		$('#ompoled').dialog('open');
	}
}

function setompolet(){
	$('#ompolepp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	getompolelist(p);
        }
	 });
	$('#ompolet').datagrid(
		{
			idField : 'POLEID',
			height: $('#omcustd').height()-50,
			fitColumns: true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			columns: [ [
			{
				field : 'POLEID',title : 'ID',width : 200,hidden:true
			},{
				field : 'IMAGENAME',title : 'ID',width : 200,hidden:true
			},{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				field : 'POLENAME',	title : '陈列说明',width : 270,fixed:true,align:'center',halign:'center'
			}, {
				field : 'ACTION',title : '操作',width: 60,fixed:true,align:'center',halign:'center',
				formatter: function(value,row,index){
					var html ='<input type="button" class="m-btn" value="删除" onclick="delompole(' + index + ')">';
					return html;
				}
			}]],
			onDblClickRow: function(index,row){
				if(row){
					omwaretp=1;
					$('#upoleid').val(row.POLEID);
					$('#upolename').val(row.POLENAME);
					$('#mainimg').attr('src',imageurl+row.IMAGENAME);
					$('#mainimg,#polewarebox').removeClass('changed').show();
					$('.no-image').hide();
					listompoleware();
				}
			},
			toolbar : '#ompole_toolbar'
		}).datagrid("keyCtr");
}
function getompolelist(page){
	var omid = $('#uomid').val();
	var findbox = $('#ompoleqsfindbox').val();
	$('#ompolet').datagrid('loadData', nulldata);
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "listompole",
			page: page,
			rows: pagecount,
			sort: 'poleid',
			order: 'asc',
			findbox: findbox,
			omid: omid
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#ompolepp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#ompoletotal').html(data.total);
					$('#ompolet').datagrid('loadData', data);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
var uptp = 0;
function selectimg(t){
	uptp = t;
	if(t==0){
		uploader_wareimg.option('auto',true);
	}else if(t==1){
		uploader_wareimg.option('auto',false);
// 		uploader_wareimg.option('fileNumLimit',1);
		uploader_wareimg.option('thumb', {
    	    width: 355,
    	    height: 300,
    	    // 图片质量，只有type为`image/jpeg`的时候才有效。
    	    quality: 70,
    	    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
    	    allowMagnify: false,
    	    // 是否允许裁剪。
    	    crop: true,
    	    // 为空的话则保留原有图片格式。
    	    // 否则强制转换成指定的类型。
    	    type: 'image/jpeg'
    	});
	}else if(t==2){
		uploader_wareimg.option('auto',true);
		uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=getfilebase64string");
// 		uploader_wareimg.option('fileNumLimit',2);
		uploader_wareimg.option('thumb', {
    	    width: 70,
    	    height: 70,
    	    // 图片质量，只有type为`image/jpeg`的时候才有效。
    	    quality: 70,
    	    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
    	    allowMagnify: false,
    	    // 是否允许裁剪。
    	    crop: false,
    	    // 为空的话则保留原有图片格式。
    	    // 否则强制转换成指定的类型。
    	    type: 'image/jpeg'
    	});
	}else if(t==3){
		uploader_wareimg.option('auto',true);
		uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=getfilebase64string");
// 		uploader_wareimg.option('fileNumLimit',2);
		uploader_wareimg.option('thumb', {
    	    width: 300,
    	    height: 152,
    	    // 图片质量，只有type为`image/jpeg`的时候才有效。
    	    quality: 70,
    	    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
    	    allowMagnify: false,
    	    // 是否允许裁剪。
    	    crop: false,
    	    // 为空的话则保留原有图片格式。
    	    // 否则强制转换成指定的类型。
    	    type: 'image/jpeg'
    	});
	}
	$('#imgfile').click();
}
function addompoled(){
	$('#upoleid').val('');
	$('#upolename').val('');
	$('#mainimg').attr('src',"");
	$('#mainimg,#polewarebox').removeClass('changed').hide();
	$('.no-image').show();
}

function saveompole(){
	var erpser = "addompole";
	var poleid = Number($('#upoleid').val());
	var polename = $('#upolename').val();
	if(poleid>0)
		erpser = "updateompole";
	if(!$('#mainimg').hasClass('changed')&&poleid==0){
		alerttext("请添加陈列图片");
		return;
	}		
	if(polename.length<=10){
		alerttext("输入10-100字的陈列说明");
		return;
	}
	if($('#mainimg').hasClass('changed')){
		if (uploader_wareimg.getFiles().length == 1) {
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser="+erpser);
			uploader_wareimg.option('formData', {
				omid: $('#uomid').val(),
				poleid: poleid,
				polename: polename
			});
			uploader_wareimg.upload();
			alerttext("正在上传请稍等………",30000);
			
		} else {
			alerttext("请选择一张图片！");
		}
	}else{
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: erpser,
				omid: $('#uomid').val(),
				poleid: poleid,
				polename: polename
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)){
						$("#ompolepp").refreshPage();
					}
				}catch (e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
	}
}
function listompoleware(){
	var poleid = Number($('#upoleid').val());
	$('ul.poleimagelist li:not(.addwareli)').remove();
	var erpser = "listompoleware";
	var formdata = {
			erpser: erpser,
			poleid: poleid
		};
	if(omwaretp==2){
		erpser = "listomwarecode1";
		var wareid = $('#uwareid').val();
		var omid = $('#uomid').val();
		formdata = {
			erpser: erpser,
			wareid: wareid,
			omid: omid
		}
	}
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : formdata, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					var rows = data.rows;
					var html = "";
					for(var i in rows){
						var row = rows[i];
						var wareid = row.WAREID;
						var wareno = row.WARENO;
						var warename = row.WARENAME;
						var imagename = row.IMAGENAME;
						html += '<li data-wareid="'+wareid+'">'
							+'<div class="wareimgbox">'
							+'<span class="icon_removeimg"></span>'
							+'<img src="'+imageurl+imagename+'_60x60-m3.png" width="100px" height="100px"/>'
							+'</div>'
							+'<div class="warenobox">'+wareno+','+warename+'</div>'
							+'</li>';
					}
					if(omwaretp==1){
						$('ul#poleimagelist').append(html);
					}else{
						$('ul#dpimagelist').append(html);
					}
					
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function openwaredpd(index){
	var $dg = $('#omwaret');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	if(row){
		omwaretp=2;
		$('#uwareid').val(row.WAREID);
		$('#dpwareimage').attr('src',imageurl+row.IMAGENAME);
		$('#omwaredptitle').html(row.WARENO+","+row.WARENAME+"---");
		$('#omwaredpd').dialog('open');
		listompoleware();
	}
}
function delompole(index){
	var erpser = "delompole";
	var $dg = $('#ompolet');
	var rows = $('#ompolet').datagrid('getRows');
	var row = rows[index];
	var poleid = Number(row.POLEID);
	$.messager.confirm(dialog_title, '您确认删除该陈列杆吗？', function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: erpser,
					poleid: poleid
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$dg.datagrid('deleteRow',index);
							addompoled();
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

function delompoleware(wareid){
	var erpser = "delompoleware";
	var poleid = Number($('#upoleid').val());
	$.messager.confirm(dialog_title, '您确认从该陈列杆删除该商品吗？', function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser: erpser,
					wareid: wareid,
					poleid: poleid
				}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try{
						if(valideData(data)){
							$('#poleimagelist li[data-wareid="'+wareid+'"]').remove();
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
//订货实时监控
function opendhssjktab(index){
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	var omid = Number(row.OMID);
	if(omid>0){
		top.omid = omid;
		top.addTabs({
			id : "pg1813",
			title :"订货实时监控",
			close : true
		});
	}else
		alerttext('请选择订货会');
}
//商品陈列汇总
function opendspclhztab(index){
	var rows = $('#gg').datagrid('getRows');
	var row = rows[index];
	var omid = Number(row.OMID);
	if(omid>0){
		top.omid = omid;
		top.addTabs({
			id : "pg1814",
			title :"商品陈列汇总",
			close : true
		});
	}else
		alerttext('请选择订货会');
}
</script>
<jsp:include page="../HelpList/WaveHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/AreaHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>