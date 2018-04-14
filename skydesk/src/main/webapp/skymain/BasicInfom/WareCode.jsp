<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8;text/x-component">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>商品编码</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<input type="hidden" id="nouse" value="0">
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input class="btn1 scanware" type="button" value="添加" onclick="addwared()">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatewared()">
	<input type="button" value="批量上传图片" class="btn1 scanware" onclick="$('#uploadimgd').dialog('open')">
	<input type="button" value="下载所有图片" class="btn1 scanware" onclick="$('#loadimgd').dialog('open')">
	<input type="text" placeholder="搜索商品名称、货号<回车搜索>" data-end="yes" class="ipt1" id="qswarevalue" data-enter="getwaredata()">
	<input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn"><input id="search" type="button" class="btn2" type="button" value="搜索" onclick="searchwaredata();toggle();"> 
	<input type="button" class="btn2"  value="清空搜索条件" onclick="resetsearch()">
	</div>
	<div class="fr">
	<input class="btn1 scanware" type="button" value="国标码生成条码" onclick="gbbartobarcode()">
	<span class="sepreate scanware"></span>
	<input type="button" class="btn3" value="设置" onclick="opensetcolumnd('#gg')">
	<span class="sepreate scanware"></span>
	<input type="button" class="btn3 scanware" value="导入" onclick="importxls()">
	<span class="sepreate"></span>
<input type="button" class="btn3" type="button" value="导出" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
	</div>
	</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchwaredata();toggle();">
<input type="hidden" id="stypeid">
<div class="search-box">
	<div class="s-box">
	<div class="title">货号</div>
	<div class="text"><input class="wid160 hig25" id="sewareno" maxlength="20" type="text" placeholder="<输入>"></div>
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
	<div class="title">供应商</div>
  	<div class="text"><input type="text"  name="sprovname" id="sprovname" data-value="#sprovid" placeholder="<输入或选择>" data-showall="1" class="edithelpinput provd_help">
  	<span onclick="selectprov('swareprov')"></span> <input type="hidden" id="sprovid" name="sprovid">
  	</div></div>
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
	<div class="s-box">
	<div class="title">建档人</div>
	<div class="text"><input class="wid160 hig25" id="slastop" type="text" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">建档开始</div>
	<div class="text"><input class="easyui-datebox" name="smindate" id="smindate" style="width: 163px; height: 28px"/></div>
	</div>
	<div class="s-box">
	<div class="title">建档结束</div>
	<div class="text"><input class="easyui-datebox" name="smaxdate" id="smaxdate" style="width: 163px; height: 28px"/></div>
	</div>
	<div class="s-box">
	<div class="title">货位</div>
	<div class="text"><input type="text" class="wid160 hig25" name="slocale" id="slocale" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">条码</div>
	<div class="text"><input type="text" class="wid160 hig25" name="sbarcode" id="sbarcode" placeholder="<输入>"></div>
	</div>
	<div class="s-box">
	<div class="title">是否特价</div>
	<div class="text">
	<select class="sele1" id="stjtag">
	<option value="2">所有</option>
	<option value="0">否</option>
	<option value="1">是</option>
	</select>
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
<table id="gg" style="overflow: hidden; height:900px;"></table>
	<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
	</div>
<!-- 添加 -->
<div id="ad" title="添加商品" style="width: 630px;text-align:center; height: 480px" class="easyui-dialog" closed=true>
<!-- <div><ul><li>通用信息</li><li>详细描述</li></ul></div> -->
<div id="adtabs" class="easyui-tabs" style="width:100%;height:390px; ">   
    <div title="基本信息">   
    <form id="addform0" action="">
<table width="570" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="60" align="right" class="header skyrequied">货号</td>
    <td width="150" align="left"><input maxlength="30" type="text" name="awareno" style="width:160px;height:25px;" id="awareno" data-options="required:true" placeholder="<输入>" onchange="getwarenoyz('add');"></td>
    <td width="130" align="right" class="header skyrequied">类型</td>
    <td width="160" align="left"><input  type="text" class="helpinput" name="afullname" id="afullname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择>" readonly>
    <span onclick="selectwaretype('add');" ></span>
				 <input type="hidden" name="atypeid" id="atypeid" value=""></td>
  </tr>
  <tr>
    <td align="right" class="header">商品名称</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="awarename" id="awarename" maxlength="30" placeholder="<输入>"></td>
    <td align="right" class="header easyno">计量单位</td>
    <td align="left" class="easyno">
<input  type="text" class="edithelpinput units_help" name="aunits" id="aunits" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span></td>
  </tr>
  <tr class="easyno">
    <td align="right" class="header">规格</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="amodel" id="amodel" maxlength="20" placeholder="<输入>"></td>
  </tr>
  <tr>
    <td align="right" class="header">品牌</td>
    <td align="left">
    <input  type="text" class="edithelpinput brand_help" name="abrandname" id="abrandname" data-value="#abrandid" maxlength="20" placeholder="<选择或输入>"><span onclick="selectbrand('add')"></span>
				<input type="hidden" name="abrandid" id="abrandid" value="">
	</td>
    <td align="right" class="header">季节</td>
    <td align="left">
<input  type="text" class="edithelpinput season_help" name="aseasonname" id="aseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span></td>
  </tr>
  <tr>
    <td align="right" class="header skyrequied">颜色</td>
    <td align="left"><input  type="text" class="helpinput wid143" name="acolorname" id="acolorname" placeholder="<选择或输入>" readonly><span onclick="selectcolor('add')"></span>
				 <input type="hidden" name="acolorid" id="acolorid" value=""></td>
    <td align="right" class="header skyrequied">尺码组</td>
    <td align="left">
    <select name="asizetypegroup" id="asizetypegroup" style="width: 164px;height:27px">
	<option value="">---请选择---</option>
	</select>
  </tr>
  <tr>
	<td align="right" class="header">供应商</td>
  	<td align="left"><input type="text"  name="aprovname" id="aprovname" data-value="#aprovid" placeholder="<输入或选择>" data-showall="1" class="edithelpinput provd_help">
  	<span onclick="selectprov('awareprov')"></span> <input type="hidden" id="aprovid" name="aprovid"></td>
    <td align="right" class="header">厂家编码</td>
    <td align="left"><input type="text" class="onlyNumAlpha" style="width:160px;height:25px"
				name="aprodno" id="aprodno" placeholder="<输入>"  maxlength="20"></td>
  </tr>
     <tr><td align="right" class="header skyrequied">进价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="aentersale" id="aentersale" placeholder="<输入>" class="onlyMoney" maxlength="10"></td>
          <td align="right" class="header">生产年份</td>
    <td align="left">
    <input class="wid160 hig25" type="text" id="aprodyear" name="aprodyear" list="year_list" placeholder="输入或点击选择">
	</td>
      </tr>
  <tr>
    <td align="right" class="header">零售价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="aretailsale" id="aretailsale" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
	<td align="right" class="header skyrequied">批发价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="asale4" id="asale4" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
  <tr>
	 <td align="right" class="header">打包价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="asale5" id="asale5" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
	<td align="right" class="header easyno">售价一</td>
    <td align="left" class="easyno"><input type="text" style="width:160px;height:25px"
				name="asale1" id="asale1" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
  <tr class="easyno">
    <td align="right" class="header">售价二</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="asale12" id="asale2" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
	<td align="right" class="header">售价三</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="asale3" id="asale3" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
</table>
</form>
    </div>   
    <div title="详细资料" data-options="closable: false">   
    <form id="adform1" action="">
<table width="570" border="0" cellspacing="10" class="table1">
	<tr>
	<td align="right" class="header">波次</td>
  	<td align="left">
  	<input type="text"  name="awavename" id="awavename" placeholder="<选择>" data-showall="1" data-value="#awaveid" class="edithelpinput wave_help">
  	<span onclick="selectwave('add')"></span> 
  	<input type="hidden" id="awaveid" name="awaveid">
  	</td>
  	<td align="right" class="header">等级</td>
  	<td align="left"><input type="text" style="width:160px;height:25px"
				name="adj" id="adj" placeholder="<输入>" maxlength="10"></td>
   </tr>
    <tr>
    <td align="right" class="header">执行标准</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="azxbz" id="azxbz" placeholder="<输入>" maxlength="32"></td>
  	<td align="right" class="header">国标码</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="agbbar" id="agbbar" placeholder="<输入>" maxlength="13"></td>
     </tr>
  <tr>
	<td align="right" class="header">区位</td>
  	<td align="left">
  	<input type="text"  name="aareaname" id="aareaname" placeholder="<选择>" data-showall="1" data-value="#aareaid" class="edithelpinput area_help">
  	<span onclick="selectarea('add')"></span> 
  	<input type="hidden" id="aareaid" name="aareaid">
  	</td>
  	<td align="right" class="header">货位</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="alocale" id="alocale" placeholder="<输入>" maxlength="10"></td>
  </tr>
   <tr><td align="right" class="header">设计师</td>
  	<td align="left"><input type="text" style="width:160px;height:25px" name="asjman" id="asjman" placeholder="<输入>" maxlength="10"></td>
  	<td align="right" class="header">检验人</td>
  	<td align="left"><input type="text" style="width:160px;height:25px"
				name="ajlman" id="ajlman" placeholder="<输入>" maxlength="10"></td>
     </tr>
     <tr>
   <td align="right" class="header">上市日期</td>
  	<td align="left">
  	<input type="text" style="width:160px;height:25px" name="assdate" id="assdate" placeholder="<输入如2016-01-01>" maxlength="32">
  	</td>
  	  <td align="right" class="header">退货截止</td>
  	<td align="left">
  	<input type="text" style="width:160px;height:25px" name="aretdatestr" id="aretdatestr" placeholder="<输入如2016-01-01>" maxlength="32">
  	</td>
     </tr>
  <tr>
  <td align="right" class="header">面料</td>
	<td> <textarea id="auseritem1" class="wid160" name="auseritem1" maxlength="60" rows="3" cols="20" wrap="hard" ></textarea></td>
	<td align="right" class="header">里料</td>
	<td> <textarea id="auseritem2" class="wid160" name="auseritem2" maxlength="60" rows="3" cols="20" wrap="hard"></textarea></td></tr>
	<tr><td align="right" class="header">配料</td>
	<td> <textarea id="auseritem3" class="wid160" name="auseritem3" maxlength="60" rows="3" cols="20" wrap="hard"></textarea></td>
	<td align="right" class="header">毛料</td>
	<td> <textarea id="auseritem4" class="wid160" name="auseritem4" maxlength="60" rows="3" cols="20" wrap="hard"></textarea></td></tr>
	<tr><td align="right" class="header">填充物</td>
	<td> <textarea id="auseritem5" class="wid160" name="auseritem5" maxlength="60" rows="3" cols="20" wrap="hard"></textarea></td>
	<td align="right" class="header">洗涤说明</td>
	<td><textarea id="axdsm" class="wid160" name="axdsm" maxlength="60" rows="3"></textarea></td>
  </tr>
	<tr><td align="right" class="header">备注</td>
	<td colspan="3"><input id="aremark" class="wid467 hig25" name="aremark" maxlength="400"/></td>
   </tr>
</table>  
</form>
    </div>
</div>  
<div class="dialog-footer">
<label class="fl-ml30">
	<input type="checkbox" name="atjtag" id="atjtag">特价商品</label>
<label class="fl-ml30">
	<input type="checkbox" id="resetaddf">添加后清空输入信息</label>
	<input class="btn1" type="button" id="add" style="width:150px;margin-right: 30px" type="button" value="保存并继续添加" onclick="if(validate(this)){addWareCode();}">
</div>
</div>
<!-- 编辑 -->
<div id="ud" title="编辑商品" style="width: 630px; text-align: center;height: 480px" class="easyui-dialog" closed=true>
 <input type="hidden" id="uindex">
<div id="uptabs" class="easyui-tabs" style="width:100%;height:390px; ">   
<div title="基本信息">  
<form id="updateform0" action="">
<input type="hidden" id="uclorusedbj">
<table width="570" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="60" align="right" class="header skyrequied">货号</td>
    <td width="160" align="left"><input onchange="getwarenoyz('update');"type="text" name="uwareno" style="width:160px;height:25px"
				id="uwareno" data-options="required:true" placeholder="<输入>">
				 <input type ="hidden" name="uwareid" id="uwareid">
				 <input type="hidden" id="bj" name="bj">
				</td>
    <td width="80" align="right" class="header skyrequied">类型</td>
    <td width="220" align="left"><input  type="text" class="helpinput" name="ufullname" id="ufullname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择>" readonly>
				 <span onclick="selectwaretype('update');"></span><input type="hidden" name="utypeid" id="utypeid" value=""></td>
  </tr>
  <tr>
    <td align="right" class="header">商品名称</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="uwarename" id="uwarename" placeholder="<输入>"></td>
    <td align="right" class="header easyno">计量单位</td>
    <td align="left" class="easyno">
<input  type="text" class="edithelpinput units_help" name="uunits" id="uunits" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
  </tr>
    <tr class="easyno">
    <td align="right" class="header">规格</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="umodel" id="umodel" maxlength="20" placeholder="<输入>"></td>
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
    <td align="left"><input  type="text" class="helpinput wid143" name="ucolorname" id="ucolorname" placeholder="<选择或输入>" readonly><span onclick="selectcolor('update')"></span>
				 <input type="hidden" name="ucolorid" id="ucolorid" value=""></td>
    <td align="right" class="header skyrequied">尺码组</td>
    <td align="left"><select name="usizetypegroup" id="usizetypegroup" class="sele1">
				</select>
					<input type="button" class="btn1" style="padding:3px;" id="selsize" value="更多" onclick="getwaresize($('#uwareid').val());">
	</td>			
  </tr>
  	<tr>
  	<td align="right" class="header">供应商</td>
  	<td align="left">
  	<input type="text"  name="uprovname" id="uprovname" placeholder="<输入或选择>" data-value="#uprovid" data-showall="1" class="edithelpinput provd_help">
  	<span onclick="selectprov('uwareprov')"></span> 
  	<input type="hidden" id="uprovid" name="uprovid">
  	</td>
    <td align="right" class="header">厂家编码</td>
    <td align="left"><input type="text" style="width:160px;height:25px" name="uprodno" id="uprodno" placeholder="<输入>" maxlength="20"></td>
   </tr>
   <tr>
   <td align="right" class="header">进价</td>
    <td align="left"><input type="text" style="width:160px;height:25px"
				name="uentersale" id="uentersale" placeholder="<输入>" class="onlyMoney" maxlength="10"></td>
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
	<td align="right" class="header easyno">售价一</td>
    <td align="left" class="easyno"><input type="text" style="width:160px;height:25px"
				name="usale1" id="usale1" placeholder="<输入>" class="onlyMoney" maxlength="6"></td>
  </tr>
  <tr class="easyno">
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
  	<td align="left"><input type="text" style="width:160px;height:25px" name="ugbbar" id="ugbbar" placeholder="<输入>" maxlength="13"></td>
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
   <td align="right" class="header">上市日期</td>
  	<td align="left">
  	<input type="text" style="width:160px;height:25px" name="ussdate" id="ussdate" placeholder="<输入如2016-01-01>" maxlength="32">
  	</td>
   <td align="right" class="header">退货截止</td>
  	<td align="left">
  	<input type="text" style="width:160px;height:25px" name="uretdatestr" id="uretdatestr" placeholder="<输入如2016-01-01>" maxlength="32">
  	</td>
     </tr>
     <tr>
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
  <tr><td align="right" class="header">备注</td>
	<td colspan="3"><input id="uremark" class="wid467 hig25" name="uremark" maxlength="400"></input></td>
  </tr>
</table>
</form>
</div>
<div title="条码资料">  
<table class="easyui-datagrid" style="height:320px;" id="warebarcodet"   
        data-options="fitColumns:true,singleSelect:true,rownumbers: true">   
    <thead>   
        <tr>   
            <th data-options="field:'ID',hidden:true,">ID</th>   
            <th data-options="field:'COLORID',hidden:true,">COLORID</th>   
            <th data-options="field:'SIZEID',hidden:true,">SIZEID</th>   
            <th data-options="field:'BARCODE',width:180,fixed:true,align:'left',halign:'center',editor:(allowscanware==1?undefined:'text')">条码</th>   
            <th data-options="field:'COLORNAME',width:80,fixed:true,align:'center',halign:'center',editor:(allowscanware==1?undefined:{type:'combobox',options:warecoloroptions})">颜色</th>   
            <th data-options="field:'SIZENAME',width:80,fixed:true,align:'center',halign:'center',editor:(allowscanware==1?undefined:{type:'combobox',options:waresizeoptions})">尺码</th>   
            <th data-options="field:'ACTION',width:100,fixed:true,align:'center',halign:'center',formatter:setbarcodeaction,hidden:(allowscanware==1?true:false)">操作</th>   
        </tr>   
    </thead>   
</table>
<div style="margin: 5px 0">
	<input type="button" class="btn1" id="addbar" value="添加" onclick="insertbarcode()">
	<input type="button" class="btn1" id="autobar" value="自动产生条码" onclick="autowarebarcode()">
	<div id="warebarcodepp" class="tcdPageCode page-code">
	</div>
</div>  
</div>
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
	<label class="fl-ml30">
	<input type="checkbox" name="unouse" id="unouse">禁用</label>
	<label class="fl-ml30">
	<input type="checkbox" name="utjtag" id="utjtag">特价商品</label>
	<input type="hidden" name="unoused" id="unoused"> 
	<input type="button" class="btn1" id="update" value="保存" onclick="updateWareCode()">
	<input type="button" class="btn2" name="close" value="取消" onclick="closedialog()">
</div>
</div>
	<!-- 得到品牌列表  -->
	<div id="brandf" title="品牌帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
		<input type="hidden" id="hbrandname">
		<input type="hidden" id="hbrandid">
		<input type="hidden" id="hbrandser">
		<div id="brandsbtn" class="help-qstoolbar">
			<input class="btn2" type="button" value="添加" onclick="$('#brand_add_dialog').dialog('open')">
		<input type="text" class="help-qsipt" id="brandfindbox" data-enter="getbranddata('1')" data-end="yes"  placeholder="搜索品牌<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getbranddata('1')">
		</div>
		<table id="brandt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="brandpp" class="tcdPageCode fl"></div>
		<input type="button" class="btn1" value="确定" onclick="selectbd();" />
		<input type="button" class="btn2" value="取消" onclick="$('#brandf').dialog('close')" />
		</div>
	</div>
	<!-- 得到颜色列表 -->
<div id="colorf" title="颜色帮助列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
		<input type="hidden" id="hcolorname">
		<input type="hidden" id="hcolorid">
		<input type="hidden" id="hcolorser">	
		<div id="colorsbtn" class="help-qstoolbar">
				<input class="btn2" id="color_add_btn" type="button" value="增加" onclick="openaddcolord();">
		<input type="text" class="help-qsipt" id="colorfindbox" data-enter="getwarecolor(1)" data-end="yes" placeholder="搜索颜色<回车搜索>" >
		<input class="btn2" type="button" value="搜索" onclick="getwarecolor('1')">
		</div>
		<table id="colort" style="overflow: hidden;"></table>
		<div class="dialog-footer">
		<div id="colorpp" class="tcdPageCode fl"></div>
		<label><input type="checkbox" value="0" id="brandkzcolor" style="margin-right: 5px;" onchange="getwarecolor(1)">显示品牌关联颜色</label>
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
<p style="color:#ff7900;font-size: 14px;margin: 5px;">提示:<span style="color: #aaa;">灰色代表</span>在用商品尺码，不允许编辑！</p>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="writewaresize();" />
<input type="button" class="btn2" value="取消" onclick="$('#selectsized').dialog('close')" />
</div>
</div>
<!-- 图片上传窗 -->
<div id="uploadimgd" title="请选择上传图片地址" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 80px 10px 10px 10px;text-align: center;">
<input type="button" class="btn1 wid160" value="上传订货会图片" id="uploadimg" onclick="openupimgd(1)"> 
<input type="button" class="btn1 wid160" value="上传商品档案图片" id="uploadimg0" onclick="openupimgd(0)"> 
</div>
<div class="dialog-footer">
<div id="loadpp"  class="tcdPageCode fl"></div>
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#uploadimgd').dialog('close')">
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
<!-- xls上传窗 -->
<div id="uploadxlsd" title="请选择上传商品选项" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 80px 10px 10px 10px;text-align: center;">
<input type="button" class="btn1 wid160" value="上传商品档案" id="uploadimg" onclick="uploadwarexls(0)"> 
<input type="button" class="btn1 wid160" value="上传商品颜色" id="uploadimg0" onclick="uploadwarexls(1)"> 
</div>
<div class="dialog-footer">
<div id="loadpp"  class="tcdPageCode fl"></div>
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#uploadxlsd').dialog('close')">
</div>
</div>
<!-- 图片放大窗 -->
<div id="imgf" title="图片预览" style="width: 440px; height: 440px;" class="easyui-dialog" closed=true>
<img src="" id="imgdiv" style="width: 99%; height:99%;" ondblclick="$('#imgfile').click()" >
</div>
<!-- 图片下载窗 -->
<div id="loadimgd" title="请选择下载图片源" data-options="modal:true"  style="width: 400px; height: 300px" class="easyui-dialog" closed="true">
<div style="margin: 80px 10px 10px 10px;text-align: center;">
<input type="button" class="btn1 wid160" value="下载商品档案图片" id="downimg"> 
<input type="button" class="btn1 wid160" value="下载订货会图片" id="downimg0"> 
</div>
<div class="dialog-footer">
<div id="loadpp"  class="tcdPageCode fl"></div>
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#loadimgd').dialog('close')">
</div>
</div>
<!-- 添加颜色窗 -->
<div id="color_add_dialog" title="添加颜色" style="width: 400px; height: 300px;text-align: center;"  data-options="onClose:refreshcolorcode"
		class="easyui-dialog" closed=true>
<table width="400" border="0" cellspacing="10" class="table1" data-qickey="F4:'addcolor();'">
  <tr align="right">
    <td width="100" class="header skyrequied">颜色名称</td>
    <td width="160" align="left"><input class="hig25 wid160 "type="text" maxlength="20" placeholder="<输入>" id="color_add_colorname" data-enter="addcolor()" /> </td>
  </tr>
  <tr align="right">
    <td class="header">色码</td>
    <td align="left"><input class="onlyNumAlpha hig25 wid160 "  maxlength="6" type="text" placeholder="<输入>" id="color_add_colorno"/> </td>
  </tr>
</table>
<div class="dialog-footer" style="text-align: center;">
				<input id="add" class="btn1" type="button" name="save" style="width:150px;" type="button" value="保存并继续添加" onclick="addcolor();">
</div>
	</div>
<!-- <div id="filef" title="导入excel" style="width: 400px; height: 237px;text-align: left;" class="easyui-dialog" closed=true>
<form action="wareservlet?wareser=uploadexcel" method="post" enctype="multipart/form-data">  
<div style="margin-top: 15px"><p style="margin-left: 20px;">请选择本机要上传的.xls文件:</p></div>
<div style="margin-top: 15px"><input style="margin-left: 20px;width:340px" class="ipt3" name="fileurl" type="FILE" id="fileurl" size="50" style="width:200px"></div>
<div style="margin-top: 15px"><img src="/skydesk/images/wxts.png"  style="margin-left: 20px;float:left;"><p><b>温馨提示：</b>上传的文件格式只支持xls，以及请按照商品编码上传数据格式上传。<a style="color:#ff7900">点击查看示例</a></p></div>
<div style="margin-top: 15px;text-align:right;height:50px;line-height: 50px;border-top:#d7d7d7 solid 1px"><input id="upload" class="btn1" style="margin-left: 20px" type="submit" value="上  传"   onclick="uploadexcel()"></div>
</form>
</div> --> 
	<!-- 添加品牌窗 -->
	<div id="brand_add_dialog" title="添加品牌" style="width: 350px; height: 200px;"
		class="easyui-dialog" closed=true>
		<div style="margin-top: 40px; text-align: center;">
			<label class="theader skyrequied">品牌名称&nbsp;&nbsp;&nbsp;</label> <input data-enter="addbrand();"
				style="height: 25px" type="text" placeholder="<输入>"
				id="brand_add_brandname" name="brand_add_brandname" width="175px" maxlength="20" />
		</div>
		<div class="dialog-footer" style="text-align: center;">
			<input id="brand_add_add" class="btn1" type="button" name="save" style="width: 150px;" type="button" value="保存并继续添加" onclick="addbrand();">
		</div>
	</div>
<script type="text/javascript">

var index = 1;
var checkcolor = '';
var colorcount = 0;
var sizeoptions = "";
var s = false;
var pgid = "pg1005";
setqxpublic();
var index1 = 1;
var warefs = true;
var warefs1 = true;
var imgb = false;
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

function closedialog() {
	$('#ad').dialog('close');
	$('#ud').dialog('close');
	$('#rd').dialog('close');
}

function setyear() {
	var myDate = new Date(top.getservertime());
	var year = myDate.getFullYear();
	var start = year - 5;
	var end = year + 1;
	var arealist = '';
	for (var i = start; i <= end; i++) {
		arealist += '<option label="' + i + '" value="' + i + '"/>'
	}
	try {
		$("#year_list").html(arealist);
	} catch(e) {
		console.error(e);
	}
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
		if (dhhbj == 2) {
			var files = uploader_img.getFiles();
			var dindex = file.name.indexOf(".");
			var kzm = file.name.substring(dindex, file.name.length); //扩展名
			var rows = $('#gg').datagrid("getRows");
			var index = $('#uindex').val();
			var row = rows[index];
			var wareno = $('#uwareno').val();
			if (row) wareno = row.WARENO;
			var fl = files.length;
			file.name = wareno + " (" + fl + ")" + kzm;
		}
		var $list = $('#wareimglist');
		var $li = $('<li id="' + file.id + '" class="file-item thumbnail">' + '<img>' + '<div class="info">' + file.name + '</div>' + '</li>'),
		$img = $li.find('img');
		// $list为容器jQuery实例  
		$list.append($li);
		// 创建缩略图  
		// 如果为非图片文件，可以不用调用此方法。  
		// thumbnailWidth x thumbnailHeight 为 100 x 100  
		uploader_img.makeThumb(file,
		function(error, src) { //webuploader方法  
			if (error) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
			$img.attr('src', src);
		},
		100, 100);

	},
	beforeFileQueued: function(file) {
		var flimit = uploader_img.option("fileNumLimit");
		var files = uploader_img.getFiles();
		if (files.length >= flimit) return false;
		else return true;
	},
	uploadSuccess: function(file, res) {
		$('#' + file.id).addClass('upload-state-done');
		var suce = res.stg == 0 ? false: true;
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
		var $li = $('#' + file.id),
		$error = $li.find('div.error');
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
			if (dhhbj == 2) {
				dhhbj = 0;
			}
			uploader_img.option('formData', {
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
	server: "/skydesk/uploadimgservice?flyangser=addwarepicture",
	uptype: "image",
	upbtn: "#uploadbtn",
	pick: {
		multiple: false
	},
	fileNumLimit:  1,
	accept: {
		title: 'Images',
		extensions: 'jpg',
		mimeTypes: 'image/jpeg'
	},
	fileQueued: function(file) { // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。  
	},
	uploadSuccess: function(file, res) {
		try {
			if (res.result == 1) getwarepicture();
			else alert(res.msg);
		} catch(e) {
			console.error(e);
			top.wrtiejsErrorLog(e);
		}
	},
	uploadProgress: function(file, percentage) {

	},
	uploadError: function(file) {
		uploader_wareimg.reset();
	},
	uploadComplete: function(file) {
		alerthide();
		uploader_wareimg.reset();
		$('#imgfile').val('');
	},
	startUpload: function() {
		if (uploader_wareimg.getFiles().length == 1) {
			var imgid = Number($('#upimgid').val());
			uploader_wareimg.option('formData', {
				wareid: $('#uwareid').val(),
				dhhbj: 0,
				imgid: imgid
			});
			uploader_wareimg.upload();
			alerttext("正在上传请稍等………", 30000);
		} else {
			alerttext("请选择一张图片！");
		}
	}
}
function openupimgd(dhhbj) {
	if (dhhbj == 2) {
		uploader_img.option('fileNumLimit', 10);
		$("#plwareimgwxts").hide();
		$("#wareimgwxts").show();
	} else {
		$("#wareimgwxts").hide();
		$("#plwareimgwxts").show();
		uploader_img.option('fileNumLimit', undefined);
	}
	$('#dhhbj').val(dhhbj);
	$('#uploadfiled').dialog('open');
}
var refreshwareimg = function() {
	var dhhbj = $('#dhhbj').val();
	if (dhhbj == 2) getwarepicture();
}
$(function() {
	uploader_img = SkyUploadFiles(uploader_img_options);
	uploader_wareimg = SkyUploadFiles(uploader_wareimg_options);
	//清空队列
	$("#resetbtn").on('click',
	function() {
		uploader_img.stop();
		uploader_img.reset();
		$('#wareimglist').html('');
	});
	//选择文件
	$('#wareimglist').delegate('li', 'click',
	function() {
		if ($(this).hasClass('remove-this')) {
			$(this).removeClass('remove-this');
		} else {
			$(this).addClass('remove-this');
		}
	});
	//删除某些文件
	$("#delbtn").on('click',
	function() {
		var li = $('#wareimglist .remove-this');
		if (li.length > 0) {
			for (var i = 0; i < li.length; i++) {
				uploader_img.removeFile(li[i].id);
				li.remove();
			}
		} else {
			alerttext('请选择要删除要上传文件！');
		}
	});
	$('ul.size_ul').delegate("li:not(.isused)", 'click',
	function() {
		var sizeid = $(this).data('sizeid');
		var selbj = $(this).hasClass('selbj');
		var changed = $(this).hasClass('changed');
		if (!changed) $(this).addClass('changed');
		if (selbj) $(this).removeClass('selbj');
		else $(this).addClass('selbj');
	});
	downloadallimg();
	setyear();
	$("#pp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getwarecodelist(p);
		}
	});
	$("#warebarcodepp").createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getwarebarcodelist(p);
		}
	});
	$('#gg').datagrid({
		idField: 'WAREID',
		width: '100%',
		height: $('body').height() - 50,
		fitColumns: true,
		striped: true,
		//隔行变色
		nowrap: false,
		sortName: "WAREID",
		sortOrder: "asc",
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			$('#uindex').val(rowIndex);
		},
		onDblClickRow: function(rowIndex, rowData) {
			if (!imgb) updatewared();
		},
		onDblClickCell: function(index, field, value) {
			if (field == 'IMG') {
				imgup();
				imgb = true;
			} else {
				imgb = false;
			}
		},
// 		onHeaderContextMenu: createGridHeaderContextMenu,
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
				var val = Math.ceil(Number(value) / pagecount) - 1;
				return val * pagecount + Number(index) + 1;
			}
		}]],
		columns: [getpgcolumnarray([
		{
			title: '图片',
			field: 'IMAGENAME0',
			width: 70,
			align: 'center',
			fixed: true,
			formatter: function(value, row, index) {
				var val = row.IMAGENAME0;
				if (val != '' && val != null && val != undefined) {
					return '<img bigimg="true"  style=\"height:60px; width:60px;\" src="' + imageurl + '' + row.IMAGENAME0 + '_60x60-m3.png" />';
				}
			}
		},
		{
			field: 'WAREID',
			title: '商品ID',
			fieldtype: "G",
			width: 100,
			fixed: true,
			expable: true,
			hidden: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'WARENO',
			title: '货号',
			width: 100,
			fixed: true,
			expable: true,
			sortable: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'WARENAME',
			title: '商品名称',
			width: 150,
			fixed: true,
			expable: true,
			sortable: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'GBBAR',
			title: '国标码',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'ZXBZ',
			title: '执行标准',
			expable: true,
			width: 80,
			fixed: true,
			hidden: true
		},
		{
			field: 'UNITS',
			title: '单位',
			expable: true,
			width: 60,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'TYPENAME0',
			title: '商品大类',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'TYPENAME',
			title: '商品小类',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'FULLNAME',
			title: '商品类型',
			width: 80,
			fixed: true,
			expable: false,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'ENTERSALE',
			title: '进价',
			width: 60,
			fieldtype: "N",
			fixed: true,
			expable: true,
			sortable: true,
			align: 'right',
			halign: 'center',
			hidden: (allowinsale == 1 ? false: true),
			formatter: currfm
		},
		{
			field: 'RETAILSALE',
			title: '零售价',
			width: 60,
			fixed: true,
			expable: true,
			sortable: true,
			fieldtype: "N",
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'SALE4',
			title: '批发价',
			width: 60,
			fixed: true,
			expable: true,
			sortable: true,
			fieldtype: "N",
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'SALE5',
			title: '打包价',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true,
			formatter: currfm
		},
		{
			field: 'SALE1',
			title: '售价1',
			expable: true,
			width: 80,
			fixed: true,
			hidden: true,
			formatter: currfm
		},
		{
			field: 'SALE2',
			title: '售价2',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true,
			formatter: currfm
		},
		{
			field: 'SALE3',
			title: '售价3',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true,
			formatter: currfm
		},
		{
			field: 'BRANDNAME',
			title: '品牌',
			expable: true,
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SEASONNAME',
			title: '季节',
			expable: true,
			width: 50,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'COLORNAMELIST',
			title: '颜色',
			width: 160,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SIZEGROUPNO',
			title: '尺码组',
			expable: true,
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'PROVNAME',
			title: '供应商',
			expable: true,
			width: 80,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'PRODNO',
			title: '厂家编码',
			expable: true,
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'PRODYEAR',
			title: '生产年份',
			expable: true,
			width: 60,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SSDATE',
			title: '上市日期',
			width: 80,
			fixed: true,
			hidden: true,
			expable: false,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LASTDATE',
			title: '建档日期',
			width: 80,
			fixed: true,
			expable: false,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LASTOP',
			title: '建档人',
			width: 70,
			fixed: true,
			expable: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AREANAME',
			title: '区位',
			width: 50,
			fixed: true,
			expable: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'LOCALE',
			title: '货位',
			expable: true,
			width: 50,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'REMARK',
			title: '备注',
			expable: true,
			width: 120,
			fixed: true,
			sortable: true,
			align: 'center',
			halign: 'center'
		},
		{
			field : 'USERITEM1',
			title : '面料',
			expable: true,
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		}, {
			field : 'USERITEM2',
			title : '里料',
			expable: true,
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		}, {
			field : 'USERITEM3',
			title : '配料',
			expable: true,
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		}, {
			field : 'USERITEM4',
			title : '毛料',
			expable: true,
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		}, {
			field : 'USERITEM5',
			title : '填充物',
			expable: true,
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'TJTAG',
			title: '特价',
			expable: true,
			width: 60,
			fixed: true,
			align: 'center',
			halign: 'center',
			hidden: true,
			formatter: function(value, row, index) {
				if(value==1) return "特价";
				return "";
			}
		},
		{
			field: 'NOUSED',
			title: '禁用',
			expable: true,
			width: 60,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var nouse = Number(row.NOUSED);
				if(allowscanware!=1){
					var checked = "";
					if (nouse == 1) checked = "checked";
					if (isNaN(nouse)) return "";
					return '<input type="checkbox" id="disabledcheckin_' + index + '" class="m-btn" onchange="disabledWare(' + index + ')" ' + checked + '>';
				}else{
					if (nouse == 1) return "是";
					else return "否";
				}
			}
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 100,
			fixed: true,
			hidden: (allowscanware==1?true:false),
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return '<input type="button" class="m-btn" value="删除" onclick="delwarecode(' + index + ')">';
			}
		}])],
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid("keyCtr", "updatewared()");
	getwaredata();
// 	gettypehlist();
	getdefaultcs();
	if (top.hopentabid != undefined && top.hopentabid != "no" && top.haction == "addware") {
		addwared();
		top.hopentabid = "no";
		top.haction = "no";
	}
	// 图片上下滚动
	var count = $("#imageMenu li").length - 4; // 显示 5 个 li标签内容 
	var interval = $("#imageMenu li:first").width();
	var curIndex = 0;
	$('.scrollbutton').click(function() {
		if ($(this).hasClass('disabled')) return false;
		if ($(this).hasClass('smallImgUp'))--curIndex;
		else++curIndex;
		$('.scrollbutton').removeClass('disabled');
		if (curIndex == 0) $('.smallImgUp').addClass('disabled');
		if (curIndex == count - 1) $('.smallImgDown').addClass('disabled');
		$("#imageMenu ul").stop(false, true).animate({
			"marginLeft": -curIndex * interval + "px"
		},
		600);
	});
	// 解决 ie6 select框 问题
	$.fn.decorateIframe = function(options) {
		if (browser.indexOf('IE') >= 0 && Number(version) < 7) {
			var opts = $.extend({},
			$.fn.decorateIframe.defaults, options);
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
	$("#imageMenu ul").delegate("li img", "click",
	function() {
		if ($(this).attr("id") != "onlickImg") {
			midChange($(this).attr("src").replace("_60x60-m3.png", ""));
			$("#imageMenu li").removeAttr("id");
			$(this).parent().attr("id", "onlickImg");
		}
	}).delegate("li", "dblclick",
	function() {
		if(allowscanware==1) return;
		var imgid = Number($(this).data('imgid'));
		if (imgid == 0) {
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=addwarepicture&server=buy");
			$('#uploadtype').val('add');
			$('#upimgid').val('0');
			$('#imgfile').click();
		} else if (imgid > 0) {
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=updatewarepicture&server=buy");
			$('#upimgid').val(imgid);
			$('#uploadtype').val('update');
			$('#imgfile').click();
		}
	}).delegate("li", "click",
	function() {
		var imgid = Number($(this).data('imgid'));
		$('#upimgid').val(imgid);
		if (imgid == 0) {
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=addwarepicture&server=buy");
			$('#uploadtype').val('add');
			$('#imgfile').click();
		}else{
			uploader_wareimg.option('server',"/skydesk/uploadimgservice?flyangser=updatewarepicture&server=buy");
		}
	}).delegate("span.icon_removeimg", "click",
	function() {
		var $li = $(this).parent();
		var imgid = Number($li.data('imgid'));
		if (imgid > 0) delwarepicture(imgid);
	})
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
	$('#imgfile').change(function() {
		if (this.files.length > 0) {
			uploader_wareimg.reset();
			uploader_wareimg.addFiles(this.files);
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
		X = X + $divWidth > $imgWidth ? $imgWidth - $divWidth: X;
		Y = Y + $divHeight > $imgHeight ? $imgHeight - $divHeight: Y;
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
	if(allowscanware!=1){
		$("#wareimgul").dragsort({
			dragSelector: "li[data-imgid!='']",
			dragBetween: false,
			dragEnd: saveOrder,
			scrollContainer: '#wareimgul',
			placeHolderTemplate: ""
		});
	}
	function saveOrder() {
		var data = $("#wareimgul li").map(function() {
			if (Number($(this).data('imgid')) > 0) return Number($(this).data('imgid'));
			return;
		}).get();
		$('#wareimgsort').val(data.join(","));
		$('#sortimgbtn').show();
	};
	$('#smindate,#smaxdate').datebox('setValue', '');
	$('#warebarcodet').datagrid({
		onBeforeCellEdit: function(index, field) {
			selcolorobj = {};
			selsizeobj = {};
		},
		onEndEdit: function(index, row) {
			var $dg = $('#warebarcodet');
			if (selcolorobj != {}) {
				$dg.datagrid('updateRow', {
					index: index,
					row: selcolorobj
				});
			}
			if (selsizeobj != {}) {
				$dg.datagrid('updateRow', {
					index: index,
					row: selsizeobj
				});
			}
			var changed = $('#warebarcodet').datagrid('getChanges').length > 0 ? true: false;
			if (changed) {
				updatewarebarcode(index);
			}
		}
	}).datagrid('enableCellEditing');
	uploader_xls_options.uploadComplete = function(file){
		$('#pp').refreshPage();
	}
	uploader_xls = SkyUploadFiles(uploader_xls_options);
	setuploadserver({
		server: "/skydesk/fyimportservice?importser=appendwarecode",
		xlsmobanname: "warecode",
		channel: "warecode"
	});
	if(allowscanware==1){
		$(".scanware").hide();
		$("#updatebtn").val("浏览");
	}
});
//上传商品颜色或者商品编码
function uploadwarexls(tp){
	if(tp==0){
		setuploadserver({
			server: "/skydesk/fyimportservice?importser=appendwarecode",
			xlsmobanname: "warecode",
			channel: "warecode"
		});
	}else if(tp==1){
		setuploadserver({
			server: "/skydesk/fyimportservice?importser=addwarecolor",
			xlsmobanname: "warecolor",
			channel: "warecolor"
		});
	}
	openimportd();
}
function insertbarcode() {
	var changed = $('#warebarcodet').datagrid('getChanges').length > 0 ? true: false;
	if (!changed) {
		var $dg = $('#warebarcodet');
		$dg.datagrid('insertRow', {
			index: 0,
			row: {}
		}).datagrid('refresh');
		$dg.datagrid('editCell', {
			index: 0,
			field: "BARCODE"
		});
	}
}
//修改条码
function updatewarebarcode(index) {
	try {
		var $dg = $('#warebarcodet');
		var rows = $dg.datagrid('getRows');
		var row = rows[index];
		if (row) {
			var wareid = Number($('#uwareid').val());
			var barcode = row.BARCODE.toUpperCase();
			var colorid = Number(row.COLORID);
			var sizeid = Number(row.SIZEID);
			var id = Number(row.ID);
			id = isNaN(id) ? 0 : id;
			colorid = isNaN(colorid) ? 0 : colorid;
			sizeid = isNaN(sizeid) ? 0 : sizeid;
			var skyser = "addwarebarcode";
			if (id > 0) {
				skyser = "updatewarebarcodebyid";
			}
			if (wareid <= 0) {
				alerttext('商品ID无效！');
				return;
			} else if (barcode.length == 0) {
				return;
			} else if (colorid <= 0) {
				return;
			} else if (sizeid <= 0) {
				return;
			}
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: skyser,
					id: id,
					wareid: wareid,
					barcode: barcode,
					colorid: colorid,
					sizeid: sizeid
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						if (id == 0) {
							$dg.datagrid('updateRow', {
								index: index,
								row: {
									ID: data.msg
								}
							});
						}
						$dg.datagrid('acceptChanges').datagrid('refresh');
					}
				}
			});
		}
	} catch(ex) {
		console.error(ex.message);
	}
}
var warecoloroptions = {
	valueField: 'COLORNAME',
	textField: 'COLORNAME',
	editable: false,
	onShowPanel: function() {
		$(this).combobox('loadData', warecolorjson);
	},
	onSelect: function(record) {
		var $dg = $('#warebarcodet');
		var cell = $dg.datagrid('cell');
		selcolorobj = record;
	}
};
var waresizeoptions = {
	valueField: 'SIZENAME',
	textField: 'SIZENAME',
	editable: false,
	onShowPanel: function() {
		$(this).combobox('loadData', waresizejson);
	},
	onSelect: function(record) {
		var $dg = $('#warebarcodet');
		var cell = $dg.datagrid('cell');
		selsizeobj = record;
		$(this).focus();
	}
};
var warecolorjson = [];
var waresizejson = [];
var selcolorobj = {};
var selsizeobj = {};
function waresizeandcolor() {
	try {
		var wareid = Number($('#uwareid').val());
		if (wareid <= 0) {
			alerttext('商品ID无效！');
			return;
		}
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
				if (valideData(data)) {
					warecolorjson = data.colorlist;
					waresizejson = data.sizelist;
				}
			}
		});
	} catch(ex) {
		console.error(ex.message);
	}
}
function delwarebarcode(index) {
	try {
		var $dg = $('#warebarcodet');
		var rows = $dg.datagrid('getRows');
		var row = rows[index];
		if (row) {
			var id = Number(row.ID);
			id = isNaN(id) ? 0 : id;
			if (id <= 0) {
				$dg.datagrid('deleteRow', index);
				return;
			} else {
				$.messager.confirm(dialog_title, '确认删除该条码！',
				function(r) {
					if (r) {
						$.ajax({
							type: "POST",
							//访问WebService使用Post方式请求 
							url: "/skydesk/fyjerp",
							//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
							data: {
								erpser: "delwarebarcodebyid",
								id: id
							},
							//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
							dataType: 'json',
							success: function(data) { //回调函数，result，返回值
								if (valideData(data)) {
									$dg.datagrid('deleteRow', index);
									$dg.datagrid('refresh');
									$dg.datagrid('acceptChanges');
								}
							}
						});
					}
				});
			}
		}
	} catch(ex) {
		console.error(ex.message);
	}
}
var delb = false;
//批量删除
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
//图片放大
function imgup() {
	$('#ud').dialog('close');
	$("#imgdiv").attr("src", "");
	var rowData = $('#gg').datagrid("getSelected");
	if (rowData) {
		if (rowData.IMAGENAME0 != "") {
			$("#imgdiv").attr("src", (imageurl + rowData.IMAGENAME0));
			$("#imgdiv").attr("alt", rowData.WARENO + "图片加载中……");
			$('#imgf').dialog({
				modal: true,
				inline: true
			});
			$('#imgf').dialog('open');

		} else {
			alerttext("未上传该商品图片!");
		}
	}
}
var currentdata = {};
var getwarecodelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	var sort = options.sortName;
	var order = options.sortOrder;
	currentdata["erpser"] = "warecodelist";
	currentdata["sort"] = sort==""?showcolset.showcolarray[0]:sort;
	currentdata["order"] = order;
// 	currentdata["fieldlist"] = showcolset.fieldlist;
	currentdata["fieldlist"] = "a.wareid,a.wareid1,a.wareno,a.warename,a.units,a.shortname,a.brandid,a.seasonname,a.typeid,a.prodyear,a.prodno,a.gbbar,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.sale5,a.remark,a.sizegroupno,a.noused,a.downenabled,c.brandname,b.typename,d.typename as typename0,b.fullname,f.provname,a.lastop,a.ssdate,a.lastdate,a.imagename0,a.locale,g.areaname,a.zxbz,a.xdsm,a.useritem5,a.useritem4,a.useritem3,a.useritem2,a.useritem1,a.tjtag";
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
//快速搜索
function getwaredata() {
	var value = $('#qswarevalue').val();
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
	alertmsg(6);
	currentdata = {
		findbox: value,
		noused: noused
	};
	getwarecodelist(1);
}
function searchwaredata() {
	var wareno = $('#sewareno').val();
	var warename = $('#swarename').val();
	var brandid = $('#sbrandid').val();
	var typeid = $('#stypeid').val();
	var seasonname = $('#sseasonname').val();
	var remark = $('#sremark').val();
	var prodyear = $('#sprodyear').val();
	var provid = $('#sprovid').val();
	var lastop = $('#slastop').val();
	var locale = $('#slocale').val();
	var barcode = $('#sbarcode').val();
	var tjtag = $('#stjtag').val();
	var mindate = $('#smindate').datebox("getValue");
	var maxdate = $('#smaxdate').datebox("getValue");
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
	var havepicture;
	if ($('#hp1').prop('checked')) {
		havepicture = "0";
	} else if ($('#hp2').prop('checked')) {
		havepicture = "1";
	} else {
		havepicture = "2";
	}
	currentdata = {
		wareno: wareno,
		warename: warename,
		typeid: typeid,
		brandid: brandid,
		barcode: barcode,
		seasonname: seasonname,
		remark: remark,
		prodyear: prodyear,
		provid: provid,
		lastop: lastop,
		mindate: mindate,
		maxdate: maxdate,
		locale: locale,
		tjtag: tjtag,
		noused: noused,
		havepicture: havepicture
	};
	getwarecodelist(1);
}
//高级搜索
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
//添加框
function addwared() {
	if (autowareno == '1') {
		$($('#awareno')).attr('placeholder', '<系统自动产生>');
		$('#awareno').prop('disabled', 'disabled');
	} else {
		$('#awareno').removeAttr('disabled');
	}

	$('#addform0,#addform1')[0].reset();
	$('#addform0 input[type=hidden],#addform1 input[type=hidden]').val("");
	var date = new Date(top.getservertime());
	$('#aprodyear').val(date.Format("yyyy"));
	$('#acolorname').val(dfcolorname);
	$('#acolorid').val(dfcolorid);
	$('#asizetypegroup').val(dfsizegroupno);
	cljson = copyobjarry(dfcljson);
	colorcount = dfcolorcount;
	$('#ad').dialog('open');
	$('#adtabs').tabs("select", 0);
	if(autowareno==1)
	$("#awarename").focus();
	else 
	$("#awareno").focus();
}
//编辑框
function updatewared() {
	var rowData = $('#gg').datagrid('getSelected');
	if (!rowData) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		getwarecodebyid(rowData.WAREID);
		$('#uwareid').val(rowData.WAREID);
		waresizeandcolor();
		getwarebarcodelist('1');
		$('#uptabs').tabs("select", 0);
		getwarepicture();

	}
}
var setbarcodeaction = function(value, row, index) {
	var id = Number(row.ID);
	id = isNaN(id) ? 0 : id;
	if (id == 0) return '<input type="button" class="m-btn" value="保存" onclick="updatewarebarcode(' + index + ')">' + '<input type="button" class="m-btn" value="取消" onclick="delwarebarcode(' + index + ')">';
	return '<input type="button" class="m-btn" value="删除" onclick="delwarebarcode(' + index + ')">';

}
//删除框
function delwared(wareid, index) {
	$('#dwareid').val(wareid);
	$('#rd').dialog({
		modal: true
	});
	$('#rd').dialog('open');
	$('#dindex').val(index);
	$('#dwareid').val(wareid);
	$('#delwarebtn').focus();
}
//导入
function importxls() {
	$('#uploadxlsd').dialog('open');
}
//清空搜索条件
function resetsearch() {
	$('#sewareno').val('');
	$('#swarename').val('');
	$('#sprovid').val('');
	$('#sprovname').val('');
	$('#sbrandid').val('');
	$('#sbrandname').val('');
	$('#stypeid').val('');
	$('#sfullname').val('');
	$('#sseasonname').val('');
	$('#sprodyear').val('');
	$('#slastop,#sbarcode').val('');
	$('#slocale,#barcode').val('');
	$('#stjtag').val('2');
	$('#smindate').datebox('setValue', '');
	$('#smaxdate').datebox('setValue', '');
	$('#st1').prop('checked', 'checked');
	$('#hp3').prop('checked', 'checked');
}

function selectwt() {
	var typeser = $('#htypeser').val();
	var rowData = $('#waretypet').datagrid("getSelected");
	if (rowData) {
		if (typeser == 'add') {
			$('#afullname').val(rowData.FULLNAME);
			$('#atypeid').val(rowData.TYPEID);
			if ($('#awarename').val() == "") $('#awarename').val(rowData.TYPENAME);
		} else if (typeser == 'update') {
			$('#ufullname').val(rowData.FULLNAME);
			$('#utypeid').val(rowData.TYPEID);
			if ($('#uwarename').val() == "") $('#uwarename').val(rowData.TYPENAME);
		} else {
			$('#swaretype').val(rowData.FULLNAME);
			$('#stypeid').val(rowData.TYPEID);
		}
		$('#waretypef').dialog('close');
	} else {
		alerttext("请选择！");
	}
}

function setbigwaretype() {
	var typeser = $('#htypeser').val();
	var typename = $('#hwaretype :selected').text();
	var typeid = $('#hwaretype').val();
	if (typeid != '') {
		if (typeser == 'add') {
			$('#afullname').val(typename);
			$('#atypeid').val(typeid);
			$('#awarename').val(typename);
		} else if (typeser == 'update') {
			$('#ufullname').val(typename);
			$('#utypeid').val(typeid);
			$('#uwarename').val(typename);
		} else {
			$('#swaretype').val(typename);
			$('#stypeid').val(typeid);
		}
		$('#waretypef').dialog('close');
	} else {
		alerttext("请选择！");
	}
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
		$('#brandf').dialog({
			modal: true,
			title: '选择品牌'
		});
		$('#brandt').datagrid({
			idField: 'brandname',
			height: 350,
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
				width: 40,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return val * pagecount + Number(index) + 1;
				}
			},
			{
				field: 'BRANDNAME',
				title: '品牌',
				width: 200
			}]],
			onDblClickRow: function(rowIndex, rowData){
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
		} else {
			$('#sbrandname').val(rowData.BRANDNAME);
			$('#sbrandid').val(rowData.BRANDID);
		}
		$('#brandf').dialog('close');
	} else {
		alerttext("请选择！");
	}
}
//加载颜色框和数据
var hascolordg = true;

function selectcolor(colorser) {
	$('#colorfindbox').val('');
	$('#hcolorser').val(colorser);
	if (colorser == 'add') {
		$('#color_add_btn').show();
	} else {
		var bj = Number($("#uclorusedbj").val());
		if(bj==1) $("#color_add_btn").hide();
		else $("#color_add_btn").show();
	}
	if (hascolordg) {
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
			idField: 'COLORID',
			height: 350,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			checkOnSelect: false,
			selectOnCheck: false,
			columns: [[{
				field: 'COLORID',
				title: 'ID',
				width: 200,
				hidden: true
			},
			{
				field: 'SELECTCOLOR',
				checkbox: true
			},
			{
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
			},
			{
				field: 'COLORNAME',
				title: '颜色',
				width: 100,
				fixed: true,
				align: 'center',
				halign: 'center'
			},{
				field: 'COLORNO',
				title: '色码',
				width: 100,
				fixed: true,
				align: 'center',
				halign: 'center'
			},{
				field: 'BRANDNAME',
				title: '品牌',
				width: 100,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value,row,index){
					if(value==""||value=="无")
						return  "通用颜色";
					else return value;
				}
			},{
				field: 'NOUSED',
				title: '禁用',
				width: 80,
				fixed: true,
//	 			sortable: true,
				fieldtype: "G",
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var colorsers = $('#hcolorser').val();
					var nouse = Number(value);
					var selbj = Number(row.SELBJ);
					var checked = "";
					if (nouse == 1) checked = "checked";
					if (isNaN(nouse)||selbj==0 || (colorsers=="update"&&bj==1)) return "";
					return '<input type="checkbox" id="disabledwarecolorin_' + index + '" class="m-btn" onchange="disabledwarecolor(' + index + ')" ' + checked + '>';
				}
			}]],
			rowStyler: function(index, row) {
				if (row.USEDBJ == 1) {
					return 'background-color:#fff;color:#d7d7d7;font-weight:bold;';
				}
			},
			onLoadSuccess: function(data) {
			    //隐藏全选框
                $(".datagrid-header-check").html("");
				var colorsers = $('#hcolorser').val();
				$('#colort').datagrid('clearChecked');
				if (data.rows.length > 0) {
					//循环判断操作为新增的不能选择
					var bj = Number($("#uclorusedbj").val());
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
					if (colorsers == "update" && findbox.length == 0) cljson = [];
					$.each(data.rows,
					function(index, item) {
						if (colorsers == "add") {
							if (colorcount > 0) {
								for (var i in cljson) {
									if (Number(cljson[i].colorid) == Number(item.COLORID)){
										$('#colort').datagrid('checkRow', index);
									}
								}
							} else {
								cljson = [];
							}
						} else {
							if (Number(item.SELBJ) == 1) {
								if (!hasKey(item.COLORID)) {
									var newjson = {
										colorid: item.COLORID,
										colorname: item.COLORNAME
									};
									cljson.push(newjson);
								}
								$('#colort').datagrid('checkRow', index);
							}
						}
					});
					clcheck = true;
				}
			},
			onCheck: function(index, row) {
				if (!clcheck) return;
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
				if (!clcheck) return;
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
function getwarecolor(page) {
	$('#colort').datagrid('loadData', nulldata);
	var colorser = $('#hcolorser').val();
	if (colorser == 'add') {
		$("#colort").datagrid("hideColumn","NOUSED");
		getcolordata(page);
	} else if (colorser == 'update') {
		$("#colort").datagrid("showColumn","NOUSED");
		getexistcolorlist(page);
	}
}

var cljson = [];

function hasKey(key) {
	for (var i in cljson) {
		if (cljson[i].colorid == key) return true;
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
	$('#colort').datagrid('loadData', nulldata);
	var brandid = $("#ubrandid").val();
	brandid = (brandid == ""||isNaN(brandid))?-1:Number(brandid);
	if(!$("#brandkzcolor").prop("checked")) brandid = "-1";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warecolorlist",
			page: page,
			brandid: brandid,
			findbox: findbox,
			rows: pagecount,
			rdbj: rdbj,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$("#colorpp").setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#colort').datagrid('loadData', data);
			}
		}
	});
}
function disabledwarecolor(index) {
	var $dg = $("#colort");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var wareid = $("#uwareid").val();
		var colorid = row.COLORID;
		var $check = $('#disabledwarecolorin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updatewarecolor",
				wareid: wareid,
				colorid: colorid,
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
					$dg.datagrid('refreshRow',index);
				} catch(e) {
					// TODO: handle exception
					console.error(e);top.wrtiejsErrorLog(e);
				}
			}
		});
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
		type: "POST",
		//访问WebService使用Post方式请求 
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
				$('#asizetypegroup').append(sizelist);
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

//选择颜色
function setwarecolor(colorid, colorname, value, index) {
	var wareid = $('#uwareid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
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
				$("#colort").datagrid("updateRow",{
					index: index,
					row: {
						SELBJ: value
					}
				});
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
				var _index = $('#uindex').val();
				$('#gg').datagrid('updateRow', {
					index: _index,
					row: {
						COLORNAMELIST: _colorname
					}
				});
			}
			$("#colort").datagrid("refreshRow",index);
			$('#gg').datagrid("refresh");
		}
	});
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
			+ "a.units,a.model,a.noused,c.brandid,a.seasonname,a.prodyear,a.prodno,a.dj,a.sjman,a.jlman," 
			+ "a.sizegroupno,a.entersale,a.retailsale,a.sale1,a.sale2,a.sale3,a.sale4,a.ssdate," 
			+ "a.sale5,a.accid1,a.gbbar,a.imagename0,a.provid,f.provname,a.locale,a.tjtag,a.zxbz," 
			+ "a.useritem5,a.useritem4,a.useritem3,a.useritem2,a.useritem1,a.xdsm,a.areaid,g.areaname,a.remark,a.retdatestr"
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
					if (wareid1 <= 0) $('#usizetypegroup').val(arrs1.SIZEGROUPNO);
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
					$('#uretdatestr').val(arrs1.RETDATESTR);
					$('#umodel').val(arrs1.MODEL);
					$('#uprodno').val(arrs1.PRODNO);
					$('#ugbbar').val(arrs1.GBBAR);
					$('#udj').val(arrs1.DJ);
					$('#ussdate').val(arrs1.SSDATE);
					$('#ujlman').val(arrs1.JLMAN);
					$('#usjman').val(arrs1.SJMAN);
					$('#uwaveid').val(arrs1.WAVEID);
					$('#uwavename').val(arrs1.WAVENAME);
					$('#uaraid').val(arrs1.AREAID);
					$('#uareaname').val(arrs1.AREANAME);
					$('#uremark').val(arrs1.REMARK);
					if (Number(allowinsale) == 1) {
						$('#uentersale').val(Number(arrs1.ENTERSALE != '' ? arrs1.ENTERSALE: '0').toFixed(2));
						$('#uentersale').removeAttr("disabled");
					} else {
						$('#uentersale').val('****');
						$('#uentersale').attr("disabled", true);
					}
					$('#uretailsale').val(Number(arrs1.RETAILSALE).toFixed(2));
					$('#usale1').val(Number(arrs1.SALE1).toFixed(2));
					$('#usale2').val(Number(arrs1.SALE2).toFixed(2));
					$('#usale3').val(Number(arrs1.SALE3).toFixed(2));
					$('#usale4').val(Number(arrs1.SALE4).toFixed(2));
					$('#usale5').val(Number(arrs1.SALE5).toFixed(2));
					var u1 = arrs1.USERITEM1.split("<br/> ");
					if (u1.length >= 1) {
						var str = "";
						for (var i in u1) {
							if (i >= 3) break;
							if (i > 0) str += "\n";
							str += u1[i];
						}
						$('#uuseritem1').val(str);
					}
					var u2 = arrs1.USERITEM2.split("<br/> ");
					if (u2.length >= 1) {
						var str = "";
						for (var i in u2) {
							if (i >= 3) break;
							if (i > 0) str += "\n";
							str += u2[i];
						}
						$('#uuseritem2').val(str);
					}
					var u3 = arrs1.USERITEM3.split("<br/> ");
					if (u3.length >= 1) {
						var str = "";
						for (var i in u3) {
							if (i >= 3) break;
							if (i > 0) str += "\n";
							str += u3[i];
						}
						$('#uuseritem3').val(str);
					}
					var u4 = arrs1.USERITEM4.split("<br/> ");
					if (u4.length >= 1) {
						var str = "";
						for (var i in u4) {
							if (i >= 3) break;
							if (i > 0) str += "\n";
							str += u4[i];
						}
						$('#uuseritem4').val(str);
					}
					var u5 = arrs1.USERITEM5.split("<br/> ");
					if (u5.length >= 1) {
						var str = "";
						for (var i in u5) {
							if (i >= 3) break;
							if (i > 0) str += "\n";
							str += u5[i];
						}
						$('#uuseritem5').val(str);
					}
					var u6 = arrs1.XDSM.split("<br/> ");
					if (u6.length >= 1) {
						var str = "";
						for (var i in u6) {
							if (i >= 3) break;
							if (i > 0) str += "\n";
							str += u6[i];
						}
						$('#uxdsm').val(str);
					}
					$('#uzxbz').val(arrs1.ZXBZ);
					if (status != '0') {
						$('#unouse').prop('checked', true);
					} else {
						$('#unouse').removeAttr('checked');
					}
					var tjtag = Number(arrs1.TJTAG);
					if (tjtag == 1) $('#utjtag').prop('checked', true);
					else $('#utjtag').removeProp('checked');
				}
			}
		}
	});
	if(allowscanware==1){
		$("#ud :input").prop("readonly",true);
		$("#ud select,#ud [type=checkbox]").prop("disabled",true);
		$("#ud div.combox-select,#ud textarea").css("border","none");
		$("#ud div.combox-select span.help-s-btn,#ud .help-input span.help-s-btn").hide();
		$("#ud [type=button]").hide();
		$('#ud').dialog('setTitle',"浏览商品");
	}
	$('#ud').dialog('open');
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
	var brandid = $("#abrandid").val();
	brandid = (brandid == ""||isNaN(brandid))?-1:Number(brandid);
	if(!$("#brandkzcolor").prop("checked")) brandid = "-1";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "colorlist",
			findbox: findbox,
			brandid: brandid,
			fieldlist: "a.colorid,a.colorname,a.colorno,a.noused,a.brandid,b.brandname",
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
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warenoexists",
			wareid: Number(wareid),
			wareno: wareno.toUpperCase()
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {}
		}
	});
}
//增加商品编码
function addWareCode() {
	var wareno = $("#awareno").val();
	var typeid = $("#atypeid").val();
	if (typeid == "") {
		typeid = "0";
	}
	var warename = $("#awarename").val();
	var units = $("#aunits").val();
	var model = $("#amodel").val();
	var brandid = $("#abrandid").val();
	if (brandid == "") {
		brandid = "0";
	}
	var seasonname = $("#aseasonname").val();
	var locale = $("#alocale").val();
	var sizetypegroup = $("#asizetypegroup").val();
	var prodyear = $("#aprodyear").val();
	var prodno = $("#aprodno").val();
	var gbbar = $("#agbbar").val();
	var entersale = $("#aentersale").val();
	var retailsale = $("#aretailsale").val();
	var waveid = Number($("#awaveid").val());
	var sjman = $("#asjman").val();
	var jlman = $("#ajlman").val();
	var dj = $("#adj").val();
	var ssdate = $("#assdate").val();
	var retdatestr = $("#aretdatestr").val();
	var sale1 = $("#asale1").val();
	var sale1 = $("#asale1").val();
	var sale2 = $("#asale2").val();
	var sale3 = $("#asale3").val();
	var sale4 = $("#asale4").val();
	var sale5 = $("#asale5").val();
	sale1 = sale1 == '' ? '0': sale1;
	sale2 = sale2 == '' ? '0': sale2;
	sale3 = sale3 == '' ? '0': sale3;
	sale4 = sale4 == '' ? '0': sale4;
	sale5 = sale5 == '' ? '0': sale5;
	var tta1 = $("#auseritem1").val();
	var tta2 = $("#auseritem2").val();
	var tta3 = $("#auseritem3").val();
	var tta4 = $("#auseritem4").val();
	var tta5 = $("#auseritem5").val();
	var zxbz = $("#azxbz").val();
	var xdsm = $("#axdsm").val();
	var remark = $('#aremark').val();
	var provid = $('#aprovid').val();
	var areaid = $('#aareaid').val();
	var provname = $('#aprovname').val();
	if (Number(colorcount) == 0) {
		alerttext("请选择颜色！");
		return;
	}
	if (sizetypegroup == '') {
		alerttext("请选择吃尺码组！");
		return;
	}
	var tjtag = 0;
	if ($('#atjtag').prop('checked')) tjtag = 1;
	if (Number(colorcount) > 0 && sizetypegroup != '') {
		var colorids = $("#acolorid").val().split(",");
		var colorlist = [];
		for (var i in colorids) {
			var colorid = Number(colorids[i]);
			if (!isNaN(colorid) && colorid > 0) colorlist.push({
				colorid: colorid
			});
		}
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addwarecode",
				wareno: wareno,
				typeid: typeid,
				warename: warename,
				units: units,
				model: model,
				brandid: brandid,
				seasonname: seasonname,
				gbbar: gbbar,
				sizegroupno: sizetypegroup,
				prodyear: prodyear,
				prodno: prodno,
				entersale: entersale,
				retailsale: retailsale,
				sale1: sale1,
				waveid: waveid,
				areaid: areaid,
				sjman: sjman,
				ssdate: ssdate,
				retdatestr: retdatestr,
				jlman: jlman,
				dj: dj,
				sale2: sale2,
				sale3: sale3,
				sale4: sale4,
				sale5: sale5,
				useritem1: tta1,
				useritem2: tta2,
				useritem3: tta3,
				useritem4: tta4,
				useritem5: tta5,
				zxbz: zxbz,
				xdsm: xdsm,
				remark: remark,
				provid: provid,
				locale: locale,
				colorcount: colorcount,
				tjtag: tjtag,
				colorlist: JSON.stringify(colorlist)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					var wareid = data.WAREID;
					wareno = data.WARENO;
					var nouse = $('#nouse').val();
					if (nouse != '1') {
						if ($('#awarename').val() == "") $('#awarename').val(wareno);
						$('#gg').datagrid('insertRow', {
							index: 0,
							row: {
								ROWNUMBER: 1,
								WAREID: wareid,
								WARENO: wareno,
								TYPEID: typeid,
								UNITS: units,
								MODEL: model,
								BRANDID: brandid,
								SEASONNAME: seasonname,
								GBBAR: gbbar,
								SIZEGROUPNO: sizetypegroup,
								PRODYEAR: prodyear,
								PRODNO: prodno,
								ENTERSALE: entersale,
								RETAILSALE: retailsale,
								WAVEID: waveid,
								AREAID: areaid,
								SJMAN: sjman,
								SSDATE: ssdate,
								JLMAN: jlman,
								DJ: dj,
								SALE1: sale1,
								SALE2: sale2,
								SALE3: sale3,
								SALE4: sale4,
								SALE5: sale5,
								USERITEM1: tta1,
								USERITEM2: tta2,
								USERITEM3: tta3,
								USERITEM4: tta4,
								USERITEM5: tta5,
								ZXBZ: zxbz,
								XDSM: xdsm,
								REMARK: remark,
								PROVID: provid,
								LOCALE: locale,
								COLORCOUNT: colorcount,
								TJTAG: tjtag,
								RETDATESTR: retdatestr,
								WARENAME: $('#awarename').val(),
								BRANDNAME: $('#abrandname').val(),
								AREANAME: $('#aareaname').val(),
								PROVNAME: provname,
								COLORNAMELIST: $('#acolorname').val()
							}
						});
						$('#gg').datagrid("refresh");
					}
					if($("#resetaddf").prop("checked")){
						addwared();
					}else{
						var s = $('#awareno').val();
						if (autowareno != "1") {
							$('#awareno').val(autogetno(s));
						}
						$('#acolorname').val(dfcolorname);
						$('#acolorid').val(dfcolorid);
						colorcount = dfcolorcount;
						cljson = copyobjarry(dfcljson);
						$('#adtabs').tabs("select", 0);
						$('#awareno').focus();
					}
					
				}

			}
		});
	}
}
//修改商品
function updateWareCode() {
	var wareid = $('#uwareid').val();
	var wareno = $("#uwareno").val();
	var typeid = $("#utypeid").val();
	var fullname = $("#ufullname").val();
	if (typeid == "") {
		typeid = "0";
	}
	var warename = $("#uwarename").val();
	var units = $("#uunits").val();
	var model = $("#umodel").val();
	var brandid = $("#ubrandid").val();
	if (brandid == "") {
		brandid = "0";
	}
	var seasonname = $("#useasonname").val();
	var locale = $("#ulocale").val();
	var colorids = $("#ucolorid").val();
	var bj = Number($('#bj').val());
	var ssdate = $('#ussdate').val();
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
	sale1 = sale1 == '' ? '0': sale1;
	sale2 = sale2 == '' ? '0': sale2;
	sale3 = sale3 == '' ? '0': sale3;
	sale4 = sale4 == '' ? '0': sale4;
	sale5 = sale5 == '' ? '0': sale5;
	var tta1 = $("#uuseritem1").val();
	var tta2 = $("#uuseritem2").val();
	var tta3 = $("#uuseritem3").val();
	var tta4 = $("#uuseritem4").val();
	var tta5 = $("#uuseritem5").val();
	var zxbz = $("#uzxbz").val();
	var xdsm = $("#uxdsm").val();
	var remark = $('#uremark').val();
	var noused = 0;
	if ($('#unouse').prop('checked')) noused = 1;
	var provname = $('#uprovname').val();
	var provid = $('#uprovid').val();
	var areaid = $('#uareaid').val();
	var waveid = Number($("#uwaveid").val());
	var sjman = $("#usjman").val();
	var jlman = $("#ujlman").val();
	var ssdate = $("#ussdate").val();
	var retdatestr = $("#uretdatestr").val();
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
			model: model,
			brandid: brandid,
			ssdate: ssdate,
			seasonname: seasonname,
			colorid: colorids,
			prodyear: prodyear,
			prodno: prodno,
			gbbar: gbbar,
			ssdate: ssdate,
			retdatestr: retdatestr,
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
			dj: dj,
			tjtag: tjtag,
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
				$('#ud').dialog('close');
				var index = $('#uindex').val();
				var nouse = $('#nouse').val();
				if (nouse == '0' && noused == '1') {
					$('#gg').datagrid("deleteRow", index);
				} else {
					$('#gg').datagrid('updateRow', {
						index: index,
						row: {
							WAREID: wareid,
							WARENO: wareno,
							TYPEID: typeid,
							WARENAME: warename,
							UNITS: units,
							MODEL: model,
							BRANDID: brandid,
							SSDATE: ssdate,
							SEASONNAME: seasonname,
							COLORID: colorids,
							PRODYEAR: prodyear,
							PRODNO: prodno,
							GBBAR: gbbar,
							SSDATE: ssdate,
							RETAILSALE: retailsale,
							SALE1: sale1,
							SALE2: sale2,
							SALE3: sale3,
							SALE4: sale4,
							SALE5: sale5,
							NOUSED: noused,
							USERITEM1: tta1,
							USERITEM2: tta2,
							USERITEM3: tta3,
							USERITEM4: tta4,
							USERITEM5: tta5,
							ZXBZ: zxbz,
							XDSM: xdsm,
							REMARK: remark,
							PROVID: provid,
							WAVEID: waveid,
							AREAID: areaid,
							SJMAN: sjman,
							JLMAN: jlman,
							RETDATESTR: retdatestr,
							DJ: dj,
							TJTAG: tjtag,
							LOCALE: locale,
							WARENAME: $('#uwarename').val(),
							BRANDNAME: $('#ubrandname').val(),
							PROVNAME: $('#uprovname').val(),
							AREANAME: $('#uareaname').val(),
							COLORNAMELIST: $('#ucolorname').val(),
							PROVNAME: provname
						}
					});
				}
				$('#gg').datagrid("refresh");
			}
		}
	});
}
//删除商品
function delwarecode(index) {
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var wareid = row.WAREID;
		$.messager.confirm(dialog_title, '您确认要删除商品' + row.WARENO + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwarecodebyid",
						wareid: wareid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						if (valideData(data)) {
							$('#gg').datagrid('deleteRow', index);
							$('#gg').datagrid("refresh");
						}
					}
				});
			}
		});
	}
}

function downloadallimg() {
	$('#downimg,#downimg0').click(function() {
		var total = Number($('#pp_total').html() != '' ? $('#pp_total').html() : 0);
		var imgt = $(this).attr('id') == 'downimg' ? 1 : 0;
		if (total == 0) {
			alert('数据为空，不能导出！');
		} else {
			alertmsg(6);
			var prodyear = $('#sprodyear').val();
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "warecodelist",
					prodyear: prodyear,
					sort: "wareid",
					order: "asc",
					havepicture: 1,
					fieldlist: "a.wareid,a.wareno,a.warename,a.imagename,imagename0",
					imgt: imgt
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						var total = data.total;
						if (total > 0) {
							$.messager.confirm(dialog_title, '确认导出商品图片:' + getValuebyName("COMID") + '.zip',
							function(r) {
								if (r) {
									DownLoadFile({
										url: "/skydesk/download",
										//请求的url
										method: 'POST',
										data: {
											downser: "getallwareimg",
											imgt: imgt,
											prodyear: prodyear
										}
									});
									alert('正在努力导出，请不要重复操作！关闭此弹窗，请等待' + Math.ceil(total / 20) + '秒左右，等待下载框弹出！');
								}
							});
						} else alerttext("您商品档案里并没有上传任何图片！");
					}
				}
			});
		}
	});
}

function getwaresize(wareid) {
	alertmsg(6);
	$('#size_ul').html('');
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
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
						var cl_selbj = selbj == 1 ? "selbj": "";
						var cl_isused = isused == 1 ? "isused": "";
						html += '<li data-sizeid="' + sizeid + '" class="' + cl_selbj + ' ' + cl_isused + '">' + sizename + '</li>'
					}
					$('#size_ul').html(html);
					$('#selectsized').dialog('open');
				} else alerttext("该商品未选择尺码组！");
			}
		}
	});
}

function writewaresize() {
	var wareid = Number($('#uwareid').val());
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
			if (selbj) value = 1;
			var obj = {
				sizeid: sizeid,
				value: value
			};
			sizeobjs.push(obj);
		});
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async: false,
			data: {
				erpser: "writewaresize",
				wareid: wareid,
				sizelist: JSON.stringify(sizeobjs)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$('#selectsized').dialog('close');
				}
			}
		});
	} else alerttext("未选择商品ID");
}

function openaddcolord() {
	$('#color_add_dialog').dialog('open');
	$('#color_add_colorname').focus();
}
var refreshcolorcode = function() {
	var colorser = $('#hcolorser').val();
	if (colorser == "update") selectcolor('update');
	else selectcolor('add');
}
//添加颜色
function addcolor() {
	var colorname = $('#color_add_colorname').val();
	var colorno = $('#color_add_colorno').val();
	var colorser = $('#hcolorser').val();
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
					var colorid = data.msg;
					if(colorser=="add"){
						cljson.push({
							colorid: colorid,
							colorname: colorname
						});
						selectcl();
					}else if(colorser=="update"){
						setwarecolor(colorid,colorname,1,0);
					}
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
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addbrand",
				brandname: brandname
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
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

function getwarebarcodelist(page) {
	alertmsg(6);
	var wareid = $('#uwareid').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "warebarcodelist",
			fieldlist: "a.statetag,a.id,a.barcode,a.wareid,b.wareno,b.warename,b.units,a.colorid,c.colorname,a.sizeid,d.sizename,sizeno,b.brandid,e.brandname",
			rows: pagecount,
			page: page,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			if (valideData(data)) {
				$('#warebarcodepp').setPage({
					pageCount: Math.ceil(data.total / pagecount),
					current: Number(page)
				});
				$('#warebarcodet').datagrid('loadData', data);
			}
		}
	});
}
function getwarepicture() {
	alertmsg(6);
	var wareid = $('#uwareid').val();
	$('#imageMenu ul li').remove();
	$('#midimg').attr("src", "");
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "listwarepicture",
			wareid: wareid,
			dhhbj: 0
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
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
								li += '<li id="onlickImg" data-imgid="' + row.ID + '" '+(allowscanware==1?"":'title="双击替换"')+'>';
								if(allowscanware!=1) li += '<span class="icon_removeimg"></span>';
								li += '<img src="' + imageurl + row.IMAGENAME + '_60x60-m3.png" width="68" height="68" alt="加载中……"/></li>';
								$('#midimg').attr("src", imageurl + row.IMAGENAME);
								$('#upimgid').val(row.ID);
								var index = $('#uindex').val();
								var ggrows = $('#gg').datagrid('getRows');
								var ggrow = ggrows[index];
								if (ggrow.IMAGENAME0 != row.IMAGENAME) {
									$('#gg').datagrid('updateRow', {
										index: index,
										row: {
											IMAGENAME0: row.IMAGENAME
										}
									});
									$('#gg').datagrid('refresh');
								}
							} else {
								li += '<li data-imgid="' + row.ID + '" '+(allowscanware==1?"":'title="双击替换"')+'>'; 
								if(allowscanware!=1) li += '<span class="icon_removeimg"></span>';
								li += '<img src="' + imageurl + row.IMAGENAME +'_60x60-m3.png" width="68" height="68" alt="加载中……"/></li>';
							}
						}
					}
				}
				if (imgno < 10 && allowscanware!=1) li += '<li data-imgid="" class="icon_uploadimg" title="点击上传"></li>';
				$('#imageMenu ul').append(li);
				$('#sortimgbtn').hide();
			}
		}
	});
}

function delwarepicture(imgid) {
	$.messager.confirm(dialog_title, '确认删除该商品图片',
	function(r) {
		if (r) {
			alertmsg(2);
			var wareid = $('#uwareid').val();
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fybuyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delwarepicture",
					imgid: imgid,
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
			dhhbj: 0,
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
//自动产生条码
function autowarebarcode() {
	var wareid = Number($('#uwareid').val());
	if (wareid > 0) {
		$.messager.confirm(dialog_title, '确认自动产生条码？',
		function(r) {
			if (r) {
				alertmsg(6);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "autowarebarcode",
						wareid: wareid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值
						if (valideData(data)) {
							getwarebarcodelist(1);
						}
					}
				});
			}
		});
	}
}

function disabledWare(index) {
	var $dg = $('#gg');
	var rows = $dg.datagrid('getRows');
	var row = rows[index];
	if (row) {
		var wareid = Number(row.WAREID);
		var $check = $('#disabledcheckin_' + index);
		var noused = 0;
		if ($check.prop('checked')) noused = 1;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updatewarecodebyid",
				noused: noused,
				wareid: wareid
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				if (valideData(data)) {
					$dg.datagrid('updateRow', {
						index: index,
						row: {
							NOUSED: noused
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
function gbbartobarcode(){
	$.messager.confirm(dialog_title, '系统自动将有国标条码的单款单色的商品生成对应的条码，便于国标条码扫码输入。是否继续？',
		function(r) {
		if (r) {
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "gbbartobarcode"
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值
					if (valideData(data)) {
						alerttext(data.msg);
					}
				}
			});
		}
	});
}
</script>
    <jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
    <jsp:include page="../HelpList/ColumnDialog.jsp"></jsp:include>
    <!-- 生产商帮助列表 -->
	<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/AreaHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/WaveHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
</body>
</html>