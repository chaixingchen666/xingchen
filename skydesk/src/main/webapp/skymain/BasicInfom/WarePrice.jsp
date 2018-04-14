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
<title>商品调价</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
p {
	line-height: 16px;
}
</style>
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
	<!-- 商品调价单据列表 -->
<div id="toolbars" class="toolbar">	
<div class="toolbar_box1">		
<div class="fl">
	<input class="btn1" type="button" id="addnotebtn" value="添加" onclick="addnoted()" />
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()" />
	<input type="TEXT" placeholder="搜索单据号、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20" data-enter="getnotedata()" />
	<input type="button" id="highsearch" class="btn2" value="高级搜索▼" onclick="toggle()" />
	</div>
<div class="fl hide" id="searchbtn" style="width: 20%">
	<input type="button" class="btn2" type="button" value="搜索" onclick="searchnote('1');toggle();"> 
	<input type="button" class="btn2"  value="清空搜索条件" onclick="resetsearch()">
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<div class="search-box">
<div class="s-box">
	<div class="title">
		开始日期<input type="hidden" id="pagetotal" value="0">
	</div>
	<div class="text">
		<input type="text" name="smindate" id="smindate" placeholder="<输入>" style="width: 162px; height: 29px" editable="true" class="easyui-datebox">
	</div>
</div>
<div class="s-box">
	<div class="title">结束日期</div>
	<div class="text">
		<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>" style="width: 162px; height: 29px" editable="true" class="easyui-datebox">
	</div>
</div>
<div class="s-box" id="shouse_div">
	<div class="title">店铺</div>
	<div class="text">
		<input class="edithelpinput house_help" id="shousename" style="width:125px;height:24px;" data-value="#shouseid" type="text" placeholder="<输入>">
		<span class="s-btn" onclick="selecthouse('search')"></span>
		<input type="hidden" name="shouseid" id="shouseid" value="">
	</div>
</div>
<div class="s-box">
	<div class="title">货号</div>
	<div class="text">
		<input class="edithelpinput" id="swareno" type="text" style="width:125px;height:24px;" data-value="#swareid" placeholder="<输入>">
			<span class="s-btn" onclick="selectware('search')"></span>
			<input type="hidden" id="swareid"> 
	</div>
</div>
<div class="s-box">
	<div class="title">摘要</div>
	<div class="text">
		<input class="hig25 wid160" type="text" name="sreamrk" id="sremark" placeholder="<输入>">
</div>
</div>
<div class="s-box">
<div class="title">单据状态</div>
	<div class="text">
	<select class="sele1" id="sstatetag">
	<option value="0">未提交</option>
	<option value="1">未审核</option>
	<option value="2">已审核</option>
	<option value="3">已终止</option>
	<option value="4" selected>所有</option>
	</select>
	</div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden; height: 900px"></table> 
<!-- 分页 --> 
  <div class="page-bar"> 
   <div class="page-total">
    共有<span id="pp_total">0</span>条记录
   </div> 
   <div id="pp" class="tcdPageCode page-code"></div> 
  </div> 
  <!-- 添加与修改商品调价单 --> 
  <div id="ud" title="编辑商品调价" style="width: 1150px;" class="easyui-dialog" closed="true" data-qickey=""> 
   <div id="udtool" style="height: 30px; line-height: 30px"> 
    <span id="updatingbar"> 
    <span><input class="btn4" type="button" data-btn="updatebtn" id="pay" value="提交" onclick="updatewaretj()" /></span>
     <span class="sepreate"></span> <span>
     <input class="btn4" type="button" id="zrware" value="载入商品" onclick="openloadcks()" /></span>
     <span class="sepreate"></span>
	<span><input class="btn4" type="button" id="delete" value="导入" onclick="openimportd()"></span>
      <span class="sepreate"></span>
      <span><input class="btn4 " type="button" value="删除" onclick="deletetj()" /></span> </span> 
    <span id="chedandel"> <span><input class="btn4" type="button" value="撤单" onclick="chedantj()" /></span> </span> 
    <span><input class="btn4 hide checkbtn" type="button" value="审核" onclick="checkwareadjusth(1)" /></span> 
    <span><input class="btn4 hide checkbtn" type="button" value="审核驳回" onclick="$('#refused').dialog('open');$('#refuse_remark').val('');" /></span> 
    <span><input class="btn4 hide endpricebtn" type="button" value="取消审核" onclick="checkwareadjusth(0)" /></span> 
    <span><input class="btn4 hide endpricebtn" type="button" value="终止" onclick="endwareprice()" /></span> 
    <span class="sepreate"></span>
	<input type="button" class="btn4" type="button" value="导出明细" onclick="fyexportxls('#wmtotalnote','#waretj',currentdata,'api',0,'调价明细表');">
   </div> 
   <!-- 单据提交框 --> 
   <div id="udnote"> 
    <form id="udnoteform" action="" method="get"> 
     <input type="hidden" id="uid" /> 
     <input type="hidden" id="uhousenamelist" />
     <input type="hidden" id="untid" /> 
     <input type="hidden" id="uistoday" /> 
     <input type="hidden" id="ustatetag" /> 
     <input type="hidden" id="uhouseid" /> 
     <input type="hidden" id="utohouseid" />
     <input type="hidden" id="ubegindate" /> 
     <input type="hidden" id="uenddate" />
     <input type="hidden" id="wtotalamt" /> 
     <input type="hidden" id="wtotalcurr" /> 
     <table border="0" cellspacing="5" class="table1" width="1100"> 
      <tbody>
       <tr> 
        <td width="50" align="right" class="header">单据号</td> 
        <td width="126" align="left"><input class="hig25 wid160" type="text" id="unoteno" readonly /></td> 
        <td width="60" align="right" class="header">单据日期</td> 
        <td width="140" align="left"><input class="hig25 wid160" id="unotedate" type="text" readonly /></td> 
        <td align="right" width="70" class="header">价格有效期</td> 
        <td align="left" width="100"><input id="tjmindates" name="tjmindates" editable="true" type="text" class="easyui-datebox" required="required" currenttext="today" style="width: 164px; height: 25px" /></td> 
        <td align="right" width="30" class="header">至</td> 
        <td align="left" width="100"><input id="tjmaxdates" name="tjmaxdates" type="text" editable="true" class="easyui-datebox" required="required" currenttext="today" style="width: 164px; height: 25px" /></td> 
       </tr> 
       <tr> 
        <td width="40" align="right" class="header">调价店铺</td> 
        <td width="170" align="left"><input class="wid145 helpinput" placeholder="&lt;选择&gt;" type="text" id="checkhouse" readonly/><span id="selprovspan" onclick="selecttjhouse()"></span></td> 
        <td width="40" align="right" class="header">自编号</td> 
        <td align="left"><input maxlength="16" type="text" class="hig25 wid160" id="uhandno" placeholder="&lt;输入&gt;"  onchange="changehandno(this.value)"/></td> 
        <td align="right" class="header">摘要</td> 
        <td colspan="3"><input maxlength="40" type="text" id="uremark" class="hig25" style="width: 464px;" placeholder="&lt;输入&gt;" onchange="changeremark(this.value)"/></td> 
       </tr> 
      </tbody>
     </table> 
    </form> 
   </div> 
   <div class="warem-toolbars"> 
    <div class="fl"> 
     <table border="0" cellspacing="0" class="fl-ml10"> 
      <tbody>
       <tr> 
       </tr>
       <tr>
        <td id="warembar" onclick="updown()" style="cursor: pointer;">▲&nbsp;&nbsp;商品明细</td> 
        <td width="150" id="tjquickuwaretd"><input type="text" id="tjquickuwareno" class="edithelpinput wareno_help" style="width: 135px;" data-end="yes" data-value="tjquickuwareno" placeholder="&lt;选择或输入添加&gt;" /> <span onclick="if(isAddWarem()){selectware('tjwareadd')}"></span></td> 
       </tr> 
      </tbody>
     </table> 
     <input type="hidden" id="tjquickuwareid" /> 
    </div> 
    <table border="0" cellspacing="0" class="fl-ml10"> 
     <tbody>
      <tr> 
       <td width="250" align="right"><input type="text" id="udfindbox" style="width: 200px;height:25px;" data-enter="getnotewarem($('#unoteno').val(), '1')" data-end="yes" placeholder="输入货号,简称,商品名称" /></td> 
       <td><input type="button" class="btn1" value="查询" onclick="getnotewarem($('#unoteno').val(), '1')" /></td> 
      </tr> 
     </tbody>
    </table> 
    <div class="fr" id="warem-toolbar"> 
     <span><input class="btn5" type="button" id="plupdateprice" value="批量调价" onclick="pltj()" /></span> 
     <span class="seprate1"></span> 
     <span><input class="btn5" type="button" id="updateware" value="〼编辑" onclick="updatewd()" /></span> 
     <span class="seprate1"></span> 
     <span><input class="btn5" type="button" id="delware" value="-删除行" onclick="delwaremx()" /></span> 
    </div> 
   </div> 
   <!-- 调价明细表 --> 
   <div style="margin-left: 15px; margin-top: 10px; width: auto"> 
    <input type="hidden" id="windex" /> 
    <table id="waretj" class="table1"></table> 
    <div id="warenametable" class="line30 mt10"> 
     <div class="fr mr20" align="right" id="wmtotalnote">已显示<span id="wmshowtotal">0</span>条记录/共有<span id="wmtotal">0</span>条记录</div> 
    </div> 
   </div> 
  </div> 
  <!-- 单个商品调价 --> 
  <div title="商品调价" id="sptjup" style="width: 330px; height: 380px; text-align: center;" class="easyui-dialog" closed="true"> 
   <table width="300" cellspacing="10" class="table1 mt14"> 
    <tbody>
     <tr> 
      <td width="80">&nbsp;<input type="hidden" size="10" id="idq" /></td> 
      <td width="80">调价前</td> 
      <td width="80">调价后</td> 
     </tr> 
     <tr class="yxckjj"> 
      <td width="80">进价</td> 
      <td width="80"><input type="text" size="10" id="entersaleq" readonly="" /></td> 
     </tr> 
     <tr> 
     </tr>
     <tr> 
      <td width="80">零售价</td> 
      <td width="80"><input type="text" size="10" id="retailsaleq" readonly="" /></td> 
      <td width="80"><input type="text" size="10" id="retailsaleh" /></td> 
     </tr> 
     <tr> 
      <td width="80">售价一</td> 
      <td width="80"><input type="text" size="10" id="sale1q" readonly="" /></td> 
      <td width="80"><input type="text" size="10" id="sale1h" /></td> 
     </tr> 
     <tr> 
      <td width="80">售价二</td> 
      <td width="80"><input type="text" size="10" id="sale2q" readonly="" /></td> 
      <td width="80"><input type="text" size="10" id="sale2h" /></td> 
     </tr> 
     <tr> 
      <td width="80">售价三</td> 
      <td width="80"><input type="text" size="10" id="sale3q" readonly="" /></td> 
      <td width="80"><input type="text" size="10" id="sale3h" /></td> 
     </tr> 
     <tr> 
      <td width="80">批发价</td> 
      <td width="80"><input type="text" size="10" id="sale4q" readonly="" /></td> 
      <td width="80"><input type="text" size="10" id="sale4h" /></td> 
     </tr> 
     <tr> 
      <td width="80">打包价</td> 
      <td width="80"><input type="text" size="10" id="sale5q" readonly="" /></td> 
      <td width="80"><input type="text" size="10" id="sale5h" /></td> 
     </tr> 
     <tr> 
      <td colspan="3" class="dialog-footer textcenter"><input class="btn1" type="button" onclick="sptjup();" value="确定" /> <input class="btn1" type="button" value="取消" onclick="closesp()" /></td> 
     </tr> 
    </tbody>
   </table> 
  </div>
	<input type="hidden" id="totaltj" />
	<div title="批量调价表" id="pltjup"
		style="width: 430px; height: 410px; text-align: center;"
		class="easyui-dialog" closed="true">
		<table width="380" cellspacing="10" class="table1 mt14">
			<tr>
				<td width="80" align="center">价格类别</td>
				<td width="80" align="center">调价依据</td>
				<td width="80" align="center">调价方式</td>
				<td width="80" align="center">调价数值</td>
			</tr>
			<tr>
				<td align="left"><input type="checkbox" id="retailsalec">零售价=</td>
				<td align="center"><select id="retailsale">
						<option value="0" class="yxckjj">进价</option>
						<option value="1" selected="selected">零售价</option>
						<option value="2">售价一</option>
						<option value="3">售价二</option>
						<option value="4">售价三</option>
						<option value="5">批发价</option>
						<option value="6">打包价</option>
				</select></td>
				<td><select id="rsyun">
						<option value="0">+</option>
						<option value="1">-</option>
						<option value="2">*</option>
				</select></td>
				<td><input type="text" size="10" id="rsjia"></td>
			</tr>
			<tr>
				<td align="left"><input type="checkbox" id="sale1c">售价一=</td>
				<td align="center"><select id="sale1">
						<option value="0" class="yxckjj">进价</option>
						<option value="1">零售价</option>
						<option value="2" selected="selected">售价一</option>
						<option value="3">售价二</option>
						<option value="4">售价三</option>
						<option value="5">批发价</option>
						<option value="6">打包价</option>
				</select></td>
				<td><select id="sale1yun">
						<option value="0">+</option>
						<option value="1">-</option>
						<option value="2">*</option>
				</select></td>
				<td><input type="text" size="10" id="sale1jia"></td>
			</tr>
			<tr>
				<td align="left"><input type="checkbox" id="sale2c">售价二=</td>
				<td align="center"><select id="sale2">
						<option value="0" class="yxckjj">进价</option>
						<option value="1">零售价</option>
						<option value="2">售价一</option>
						<option value="3" selected="selected">售价二</option>
						<option value="4">售价三</option>
						<option value="5">批发价</option>
						<option value="6">打包价</option>
				</select></td>
				<td><select id="sale2yun">
						<option value="0">+</option>
						<option value="1">-</option>
						<option value="2">*</option>
				</select></td>
				<td><input type="text" size="10" id="sale2jia"></td>
			</tr>
			<tr>
				<td align="left"><input type="checkbox" id="sale3c">售价三=</td>
				<td align="center"><select id="sale3">
						<option value="0" class="yxckjj">进价</option>
						<option value="1">零售价</option>
						<option value="2">售价一</option>
						<option value="3">售价二</option>
						<option value="4" selected="selected">售价三</option>
						<option value="5">批发价</option>
						<option value="6">打包价</option>
				</select></td>
				<td><select id="sale3yun">
						<option value="0">+</option>
						<option value="1">-</option>
						<option value="2">*</option>
				</select></td>
				<td><input type="text" size="10" id="sale3jia"></td>
			</tr>
			<tr>
				<td align="left"><input type="checkbox" id="sale4c">批发价=</td>
				<td align="center"><select id="sale4">
						<option value="0" class="yxckjj">进价</option>
						<option value="1">零售价</option>
						<option value="2">售价一</option>
						<option value="3">售价二</option>
						<option value="4">售价三</option>
						<option value="5" selected="selected">批发价</option>
						<option value="6">打包价</option>
				</select></td>
				<td><select id="sale4yun">
						<option value="0">+</option>
						<option value="1">-</option>
						<option value="2">*</option>
				</select></td>
				<td><input type="text" size="10" id="sale4jia"></td>
			</tr>
			<tr>
				<td align="left"><input type="checkbox" id="sale5c">打包价=</td>
				<td align="center"><select id="sale5">
						<option value="0" class="yxckjj">进价</option>
						<option value="1">零售价</option>
						<option value="2">售价一</option>
						<option value="3">售价二</option>
						<option value="4">售价三</option>
						<option value="5">批发价</option>
						<option value="6" selected="selected">打包价</option>
				</select></td>
				<td><select id="sale5yun">
						<option value="0">+</option>
						<option value="1">-</option>
						<option value="2">*</option>
				</select></td>
				<td><input type="text" size="10" id="sale5jia"></td>
			</tr>
			<tr>
			<td colspan="4" align="left">调后单价保留<input type="text" value="0" id="priceprec" maxlength="1" size="5" onkeyup="this.value=this.value.replace(/[^0-2]/g,'')">位小数</td>
			</tr>
			<tr>
				<td colspan="5" class="dialog-footer textcenter"><input
					class="btn1" type="button" onclick="pltjgo();" value="确定" /> <input
					class="btn1" type="button" value="取消" onclick="closeAll()" /></td>
			</tr>
		</table>
	</div>
	<div id="loadkcds" title="载入商品" style="width: 500px; height: 440px;" data-options="modal:true" class="easyui-dialog" closed="true" data-qickey>
<div class="textcenter">
<input type="hidden" id="lwareid">
<input type="hidden" id="lbrandid">
<input type="hidden" id="lareaid">
<input type="hidden" id="lprovid">
<input type="hidden" id="ltypeid">
	<table class="table1" border="0" cellspacing="5" cellpadding="5">
	<!--   <tr> -->
	<!--     <td width="67" class="header" align="right">货位</td> -->
	<%--     <td width="170" align="right"><input class="hig25 wid160" type="text" id="locale" placeholder="<输入>"></td> --%>
	<!--   </tr> -->
	  <tr>
	   <td class="header" align="right">货号</td>
		<td align="left"><input  type="text"  class="edithelpinput wareno_help" data-value="#awareid"
					name="awareno" id="awareno" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('analysis')"></span>
					 <input type="hidden" id="awareid" value="0"></td>
	  </tr>
	    <tr>
	    <td width="67" class="header" align="right">品牌</td>
	    <td width="170" align="left"><input class="edithelpinput brand_help" data-value="#lbrandid" type="text" id="lbrandname" placeholder="<选择或输入>"><span onclick="selectbrand('load')"></span></td>
	  </tr>
	    <tr>
	    <td width="67" class="header" align="right">生产商</td>
	    <td width="170" align="left"><input class="edithelpinput provd_help" data-value="#lprovid" type="text" id="lprovname" placeholder="<选择或输入>"><span onclick="selectprov('load')"></span></td>
	  </tr>
	  <tr>
	    <td class="header" align="right">类型</td>
	    <td align="left"><input class="edithelpinput" type="text" id="lwaretype" placeholder="<输入>" readonly><span onclick="selectwaretype('load')"></span></td>
	  </tr>
	  <tr id="centdivm">
	    <td class="header" align="right">季节</td>
	    <td align="left">
<input  type="text" class="edithelpinput season_help" name="lseasonname" id="lseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span></td>
	  </tr>
	  <tr>
	    <td class="header" align="right">生产年份</td>
	    <td align="left"><input class="hig25 wid160" type="text" id="prodyear" placeholder="<输入>" onkeyup="this.value=this.value.replace(/^-?\D/g,'')"></td>
	  </tr>
	  <tr>
	    <td class="header" align="right">区位</td>
	    <td align="left"><input class="edithelpinput area_help" data-value="#lareaid" type="text" id="lareaname" placeholder="<选择或输入>">
	    <span onclick="selectarea('load')"></span></td>
	  </tr>
	  <tr>
	    <td class="header" align="right">货位</td>
	    <td align="left"><input class="hig25 wid160" type="text" id="locale" placeholder="<输入>"></td>
	  </tr>
	  <tr>
	    <td class="header" align="right">价格区间</td>
	    <td align="left"><input class="hig25 wid60" type="text" id="lminsale" placeholder="<输入>" onkeyup="this.value=this.value.replace(/^-?\D/g,'')">
	    ---<input class="hig25 wid60" type="text" id="lmaxsale" placeholder="<输入>" onkeyup="this.value=this.value.replace(/^-?\D/g,'')"></td>
	  </tr>
	  <tr>
	  <td colspan="2">
	  <label>
			<input type="checkbox" name="loadbj" id="loadbj" value="1">清空原数据
			</label>
	  </td>
	  </tr>
	</table>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="清空条件" onclick="cleanload();" />
<input type="button" class="btn1" value="确定" onclick="loadkcs();" />
<input type="button" class="btn2" value="取消" onclick="$('#loadkcds').dialog('close')" />
</div>
</div>
<div class="easyui-dialog" id="refused" title="请输入取消审核原因" data-options="modal:true" style="width:300px;height:200px;" closed="true">
<div style="text-align: center;">
	<textarea rows="3" cols="3" style="width:260px;height:80px;margin-top: 10px;"  placeholder="<请输入取消审核原因>" id="refuse_remark"></textarea>
</div>
<div class="dialog-footer">
<input type="button" class="btn1" value="确定" onclick="refusewareadjusth();" />
<input type="button" class="btn2" value="取消" onclick="$('#refused').dialog('close')" />
</div>
</div>
<script type="text/javascript">
var levelid = Number(getValuebyName("GLEVELID"));
var pgid = 'pg1502';
var dqindex;
var deleteindex; //删除一行详细数据标记   
var deltotal; //删除后统计总条数 
setqxpublic();
var myDates = new Date(top.getservertime());
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	$('#gg').datagrid('resize', {
		height: height - 45
	});
}
$(function() {
	$('body').delegate('.datagrid', 'keydown',
	function(e) {
		var key = e.which;
		if (key == 113) {
			addnoted();
		}
	});
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	setwarem();
	$('#pp').createPage({
		pageCount: 1,
		current: 1,
		backFn: function(p) {
			getnotelist(p);
		}
	});
	$('#gg').datagrid({
		idField: 'NOTENO',
		width: '100%',
		height: $('body').height() - 45,
		fitColumns: true,
		showFooter: true,
		striped: true,
		//隔行变色
		nowrap: false,
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			if (rowData) {
				dqindex = rowIndex;
				dqamt = rowData.TOTALAMT;
				dqcurr = rowData.TOTALCURR;
			}
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatenoted();
		},
		columns: [[{
			field: 'ID',
			title: 'ID',
			width: 200,
			hidden: true
		},{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == "" ? "": index + 1;
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
				if (Number(row.STATETAG) >= 2) {
					return 'color:#00959a;font-weight:900';
				} else if (Number(row.STATETAG) == 1) {
					return 'color:red;font-weight:900';
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
			formatter: function(value, row, index) {
				return row.NOTEDATE == undefined ? '': row.NOTEDATE.substring(0, 16);
			}
		},
		{
			field: 'HOUSENAMELIST',
			title: '店铺',
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
			field: 'REMARK',
			title: '摘要',
			width: 200,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'CHECKREMARK',
			title: '审核建议',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'STATE',
			title: '状态',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (Number(row.STATETAG) == 0) {
					var checkremark = row.CHECKREMARK;
					if (checkremark == "") return "未提交";
					return "审核被驳回";
				}
				if (Number(row.STATETAG) == 1) {
					return "未审核";
				}
				if (Number(row.STATETAG) == 2) return "已审核";
				if (Number(row.STATETAG) == 3) return "已终止";
			}
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
			title: '审核人员',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'STATETAG',
			title: '操作状态',
			width: 20,
			hidden: true
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	});
	uploader_xls_options.uploadComplete = function(file){
		getnotewarem($("#unoteno").val(),1);
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
		server: "/skydesk/fyimportservice?importser=addwareadjustmxls",
		xlsmobanname: "wareprice",
		channel: "wareprice"
	});
	getnotedata();
	if (levelid == 1 || levelid == 2 || levelid == 7) {
		$('#checkhouse').next().hide();
		$('#shouse_div').hide();
	}
	if (Number(allowinsale) == 0) $('.yxckjj').hide();
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
//收缩展开
var d = false;
function updown() {
	$('#udnote').slideToggle('fast');
	if (!d) {
		$('#warembar').html('▼&nbsp;&nbsp;商品明细');
		$('#waretj').datagrid('resize', {
			height: $('body').height() - 235
		});
		d = true;
	} else {
		$('#warembar').html('▲&nbsp;&nbsp;商品明细');
		$('#waretj').datagrid('resize', {
			height: $('body').height() - 300
		});
		d = false;
	}
}
//清空搜索条件
function resetsearch() {
	var myDate = new Date(top.getservertime());
	$('#smindate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#shousename').val('');
	$('#shouseid').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sremark').val('');
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
// 	var sort = options.sortName;
// 	var order = options.sortOrder;
	currentdata["erpser"] = "wareadjusthlist";
	currentdata["sort"] = "NOTENO";
	currentdata["order"] = "asc";
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.noteno,a.accid,a.notedate,a.handno,a.remark,a.checkremark,a.operant,a.checkman,a.statetag,a.lastdate,a.begindate,a.enddate";
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
					dqzamt = data.totalamount;
					dqzcurr = data.totalcurr;
					$("#pp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$dg.datagrid('loadData', data);
					if(data.total>0)
						$('#gg').datagrid('scrollTo', 0);
					$('#pp_total').html(data.total);
					$('#qsnotevalue').focus();
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//搜索
function getnotedata() {
	var value = $('#qsnotevalue').val();
	currentdata = {
		findbox: value,
		statetag: 3
	};
	getnotelist(1);
}
//高级搜索
function searchnote() {
	var houseid = Number($('#shouseid').val());
	var handno = $('#shandno').val();
	var wareid = Number($('#swareid').val());
	var remark = $('#sremark').val();
	var operant = $('#soperant').val();
	var statetag = $('#sstatetag').val();
	currentdata = {
		houseid: houseid,
		handno: handno,
		wareid: wareid,
		remark: remark,
		operant: operant,
		statetag: statetag
	};
	getnotelist(1);
}
var pg = 1;
var sumpg = 1;
//加载商品明细表
function setwarem() {
	$('#waretj').datagrid({
		idField: 'ID',
		width: 1100,
		height: $('body').height() - 300,
		//fitColumns : true,
		striped: true,
		//隔行变色
		showFooter: true,
		nowrap: false,
		//允许自动换行
		singleSelect: true,
		onSelect: function(rowIndex, rowData) {
			deleteindex = rowIndex;
			$('#windex').val(rowIndex);
			$('#idq').val(rowData.ID); //id 
			$('#entersaleq').val(Number(rowData.ENTERSALE_0).toFixed(2)); //进价 
			$('#retailsaleq').val(Number(rowData.RETAILSALE_0).toFixed(2)); //零售价
			$('#sale1q').val(Number(rowData.SALE1_0).toFixed(2)); //售价一
			$('#sale2q').val(Number(rowData.SALE2_0).toFixed(2)); //售价二
			$('#sale3q').val(Number(rowData.SALE3_0).toFixed(2)); //售价三
			$('#sale4q').val(Number(rowData.SALE4_0).toFixed(2)); //批发价
			$('#sale5q').val(Number(rowData.SALE5_0).toFixed(2)); //打包价
			//调价后
			$('#entersaleh').val(Number(rowData.ENTERSALE).toFixed(2)); //进价 
			$('#retailsaleh').val(Number(rowData.RETAILSALE).toFixed(2)); //零售价
			$('#sale1h').val(Number(rowData.SALE1).toFixed(2)); //售价一
			$('#sale2h').val(Number(rowData.SALE2).toFixed(2)); //售价二
			$('#sale3h').val(Number(rowData.SALE3).toFixed(2)); //售价三
			$('#sale4h').val(Number(rowData.SALE4).toFixed(2)); //批发价
			$('#sale5h').val(Number(rowData.SALE5).toFixed(2)); //打包价 
		},
		onDblClickRow: function(rowIndex, rowData) {
			updatewd(); //编辑单个调价记录  
		},
		//columns : [setColums()],
		columns: [[{
			field: 'ROWNUMBER',
			title: '序号',
			fixed: true,
			width: 30,
			halign: 'center',
			align: 'center',
			rowspan: 2,
			formatter: function(value, row, index) {
				return value == "" ? "": index + 1;
			}
		},{
			field: 'WARENO',
			title: '货号',
			width: 90,
			halign: 'center',
			rowspan: 2,
			styler: function(value, row, index) {
				if (value == "合计") {
					return 'text-align:center';
				} else {
					return 'text-align:left';
				}
			}
		},
		{
			field: 'WARENAME',
			title: '商品名称',
			width: 110,
			halign: 'center',
			align: 'left',
			rowspan: 2
		},
		{
			field: 'AMOUNTKC',
			title: '库存',
			width: 60,
			halign: 'center',
			align: 'right',
			rowspan: 2,
			hidden: true,
			formatter: amtfm
		},
		{
			field: 'ENTERSALE_0',
			title: '进价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			rowspan: 2,
			hidden: (Number(allowinsale) == 1 ? false: true),
			formatter: currfm

		},{
			field: 'AMOUNTS',
			title: '调价前',
			fixed: true,
			halign: 'center',
			align: 'right',
			colspan: 6
		},
		{
			field: 'AMOUNTS',
			title: '调价后',
			fixed: true,
			halign: 'center',
			align: 'right',
			colspan: 6
		}], [
		{
			field: 'RETAILSALE_0',
			title: '零售价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter:currfm
		},
		{
			field: 'SALE4_0',
			title: '批发价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE5_0',
			title: '打包价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE1_0',
			title: '售价一',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE2_0',
			title: '售价二',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'center',
			formatter: currfm
		},
		{
			field: 'SALE3_0',
			title: '售价三',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'center',
			formatter: currfm
		},
		{
			field: 'RETAILSALE',
			title: '零售价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE4',
			title: '批发价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE5',
			title: '打包价',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE1',
			title: '售价一',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE2',
			title: '售价二',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		},
		{
			field: 'SALE3',
			title: '售价三',
			fieldtype: "N",
			width: 60,
			halign: 'center',
			align: 'right',
			formatter: currfm
		}]],
		pageSize: 20
	}).datagrid("keyCtr", "");
	$('#waretj').prev().children('.datagrid-body').scroll(function() {
		var $this = $(this);
		var viewH = $(this).height(); //可见高度
		var contentH = $(this).get(0).scrollHeight; //内容高度
		var scrollTop = $(this).scrollTop(); //滚动高度
		if (contentH > (viewH + 1) && contentH - viewH - scrollTop <= 3 && $("#waretj").datagrid("getRows").length > 0) { //到达底部100px时,加载新内容
			if (sumpg > pg && nextpg) {
				nextpg = false;
				getnotewarem($('#unoteno').val(), pg + 1);
				nextpg = true;
			}
			// 这里加载数据..
		}
	});
}
//增加调价记录
function addnoted() {
	$('#udnoteform')[0].reset();
	$('#udnoteform input[type=hidden]').val('');
	$('#uselecthouseb').removeAttr('disabled');
	$('#isupdatedbar').hide();
	$('#chedandel').hide();
	$('#uhandno').removeAttr('readonly');
	$('#tjhousepp').show();
	$('#tjhousepp2').show();
	$('#updatingbar').show();
	$('#tjquickuwaretd').show();
	$('#tjquickuwareno').val('');
	$('#uremark').removeAttr('readonly');
	if (levelid != 1 && levelid != 2 && levelid != 7) {
		$('#checkhouse').removeAttr('readonly');
		$('#checkhouse').next().show();
	}
	$('#ud #warem-toolbar').show();

	$('#tjmindates').datebox('setValue', ''); // 设置日期输入框的值
	$('#tjmaxdates').datebox('setValue', ''); // 设置日期输入框的值
	$('#tjmindates,#tjmaxdates').datebox({
		readonly: false,
		hasDownArrow: true
	});
	addwarecheckh();
	$('#ud').dialog({
		modal: true,
		inline: true,
		title: '增加商品调价单'
	});

	$('#ustatetag').val('0');
	$('#ud').dialog('open');
	$('#unoteno').focus();
	//$('#addnote').removeAttr('disabled');
}
//编辑单据
function updatenoted() {
	checkhouse = '';
	var rowData = $('#gg').datagrid('getSelected');
	if (rowData == null) {
		alerttext("请选择一行数据，进行编辑!");
	} else {
		$('#uid').val(rowData.ID);
		$('#unoteno').val(rowData.NOTENO);
		$('#unotedate').val(rowData.NOTEDATE);
		$('#uenddate').val(rowData.ENDDATE);
		$('#ubegindate').val(rowData.BEGINDATE);
		$('#uhousename').val(rowData.HOUSENAME);
		$('#checkhouse').val(rowData.HOUSENAMELIST);
		$('#utotalcurr').val(rowData.TOTALCURR);
		$('#utotalamt').val(rowData.TOTALAMT);
		$('#uoperant').val(rowData.OPERANT);
		$('#untid').val(rowData.NTID);
		$('#ustatetag').val(rowData.STATETAG);
		$('#uremark').val(rowData.REMARK);
		$('#uhandno').val(rowData.HANDNO);
		$('#tjmindates').datebox('setValue', ''); // 设置日期输入框的值
		$('#tjmaxdates').datebox('setValue', ''); // 设置日期输入框的值
		if (Number($('#ustatetag').val()) > 0) {
			$('#ud').dialog("setTitle", "浏览商品调价单");
			$('#tjmindates,#tjmaxdates').datebox({
				readonly: true,
				hasDownArrow: false
			});
			$('#tjhousepp').hide();
			$('#tjhousepp2').hide();
			$('#updatingbar').hide();
			if (Number(allowchedan) == 1 && Number(rowData.STATETAG) == 1) {
				$('#chedandel').show();
			} else {
				$('#chedandel').hide();
			}
			$('#isupdatedbar').hide(); //.show()
			$('#tjquickuwaretd').hide();
			$('#uhandno').attr('readonly', 'readonly');
			$('#uremark').attr('readonly', 'readonly');
			$('#checkhouse').attr('readonly', 'readonly');
			$('#checkhouse').next().hide();
			if (Number($('#ustatetag').val()) == 1 && (levelid == 0 || levelid == 3 || levelid == 4 || levelid == 5)) {
				$('#ud .checkbtn').show();
				$('#ud #warem-toolbar').show();
			} else {
				$('#ud #warem-toolbar').hide();
				$('#ud .checkbtn').hide();
			}
		} else {
			$('#ud').dialog({
				title: '修改商品调价单'
			});
			$('#tjmindates,#tjmaxdates').datebox({
				readonly: false,
				hasDownArrow: true
			});
			$('#tjhousepp').show();
			$('#tjhousepp2').show();
			$('#uselecthouseb').removeAttr('disabled');
			$('#isupdatedbar').hide();
			$('#chedandel').hide();
			$('#ud .checkbtn').hide();
			$('#updatingbar').show();
			$('#tjquickuwaretd').show();
			$('#tjquickuwareno').val('');
			$('#uhandno').removeAttr('readonly');
			$('#uremark').removeAttr('readonly');
			if (levelid != 1 && levelid != 2 && levelid != 7) {
				$('#checkhouse').removeAttr('readonly');
				$('#checkhouse').next().show();
			}
			$('#ud #warem-toolbar').show();
		}
		$('#tjmindates').datebox('setValue', rowData.BEGINDATE.substring(0, 10));
		$('#tjmaxdates').datebox('setValue', rowData.ENDDATE.substring(0, 10));
		var ubegindate = $('#ubegindate').val();
		var uenddate = $('#uenddate').val();
		//$('#tjmindates').datebox('setValue', myDates.Format('yyyy-MM-dd')); // 设置日期输入框的值
		//$('#tjmaxdates').datebox('setValue', myDates.Format('yyyy-MM-dd')); // 设置日期输入框的值
		$('#tjquickuwareno').val(''); //设置货号为空 
		if (Number($('#ustatetag').val()) == 2) {
			$('#ud .endpricebtn').show();
		} else {
			$('#ud .endpricebtn').hide();
		}
		$('#ud').dialog('open');
		getnotehbyid();
		getnotewarem($('#unoteno').val(), "1");
		if ($('#ustatetag').val() == '1') {
			$('#unoteno').focus();
		} else {
			$('#tjquickuwareno').focus();
		}
		pg = 1;
	}
}

//修改调价单，提交
function updatewaretj() {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	var remark = $('#uremark').val();
	var checkhouse = $('#checkhouse').val();
	var handno = $('#uhandno').val();
	var house = $('#checkhouse').val();
	var mindate = $('#tjmindates').datebox('getValue');
	var maxdate = $('#tjmaxdates').datebox('getValue');
	var amt = $('#waretj').datagrid('getRows').length;
	if (amt == 0) {
		alert('商品明细不能为空！');
	} else if(house.length==0){
		alerttext("调价店铺不能为空，请选择店铺！");
	} else{
		$.messager.confirm(dialog_title, '单据提交后禁止修改及删除，是否继续？      ',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "updatewareadjusthbyid",
						noteno: noteno,
						statetag: 1,
						begindate: mindate,
						enddate: maxdate,
						remark: remark,
						handno: handno
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$('#gg').datagrid('updateRow', {
									index: dqindex,
									row: {
										STATETAG: '1',
										REMARK: remark,
										CHECKREMARK: "",
										HOUSENAMELIST: checkhouse,
										BEGINDATE: mindate,
										ENDDATE: maxdate,
										HANDNO: $('#uhandno').val()
									}
								});
								$('#gg').datagrid('refresh');
								$('#ud').dialog('close');
							}
						} catch(err) {
							console.error(err.message);
						}
					}
				});
			}
		});
	}
}
function endwareprice() {
	$.messager.confirm(dialog_title, '是否确认终止该调价单！ ',
	function(r) {
		if (r) {
			alertmsg(2);
			var noteno = $("#unoteno").val();
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "updatewareadjusthbyid",
					noteno: noteno,
					statetag: 3
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							$('#gg').datagrid('updateRow', {
								index: dqindex,
								row: {
									STATETAG: '3'
								}
							});
							$('#gg').datagrid('refresh');
							$('#ud').dialog('close');
						}
					} catch(err) {
						console.error(err.message);
					}
				}
			});
		}
	});
}
function quickaddwd() {
	$('#quickuaddwaref')[0].reset();
	$('#quickutable').html('');
	if ($("#uhouseid").val() == '0' || $("#uhouseid").val() == '') {
		alerttext('请选择店铺');
	} else {
		$('#qucikud').dialog({
			modal: true
		});
		$('#quickud').dialog('open');
	}
	/*$('#div_waremx').hide();
			$('#div_waremx').slideToggle('fast');*/
}

function addwareadjustm(wareid, noteno) { //输入货号增加商品调价明细
	//hasTable=true;
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addwareadjustm",
			noteno: noteno,
			wareid: wareid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var data1 = data.rows;
					if (data.msg == 1) {
						//getnotewarem($('#unoteno').val(), "1");
						$('#waretj').datagrid('insertRow', {
							index: 0,
							row: data1
						});
						tpage = Number($('#pagetotal').val());
						$('#tjquickuwareno').val('');
						$('#waretj').datagrid('refresh');
						$('#pagetotal').val(tpage + 1);
					} else {
						$('#tjquickuwareno').val('');
						$('#tjquickuwareno').focus();
					}
					$('#u1-1').focus();
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

//编辑商品调价框 
function updatewd() {
	var arrs = $('#waretj').datagrid('getSelected');
	if (arrs == null) {
		alerttext('请选择一行数据，进行编辑');
	} else {
		var stg = Number($('#ustatetag').val());
		if (stg == 0) {
			$('#sptjup').dialog("setTitle",'修改价格').dialog('open');
		} else if (stg == 1 && (levelid == 0 || levelid == 4 || levelid == 5)) {
			$('#sptjup').dialog("setTitle",'审核调整价格').dialog('open');
		}

	}
}
//批量调价
function pltj() {
	var total = Number($('#waretj').datagrid('getRows').length);
	if (total != 0) {
		$('#pltjup').dialog("setTitle",'批量调价表').dialog('open');
	} else {
		alerttext('数据为空！');
	}
}

function pltjgo() {
	if ($("#pltjup input[type=checkbox]:checked").length == 0) {
		alerttext("请勾选至少一个价格类别");
		return;
	}
	$('#pltjup').dialog('close');
	var tjgs1 = ''; //进价公式
	var tjgs2 = ''; //零售价 
	var tjgs3 = ''; //售价一
	var tjgs4 = ''; //售价二
	var tjgs5 = ''; //售价三
	var tjgs6 = ''; //批发价
	var tjgs7 = ''; //打包价
	if ($("#entersalec").prop("checked") == true) { //进价
		var entersale = $('#entersale').val();
		var esyun = $('#esyun').find("option:selected").text();
		var esjia = Number($('#esjia').val());
		if (entersale == 6) {
			tjgs1 = 'SALE5_0' + esyun + esjia;
		} else if (entersale == 5) {
			tjgs1 = 'SALE4_0' + esyun + esjia;
		} else if (entersale == 4) {
			tjgs1 = 'SALE3_0' + esyun + esjia;
		} else if (entersale == 3) {
			tjgs1 = 'SALE2_0' + esyun + esjia;
		} else if (entersale == 2) {
			tjgs1 = 'SALE1_0' + esyun + esjia;
		} else if (entersale == 1) {
			tjgs1 = 'RETAILSALE_0' + esyun + esjia;
		} else if (entersale == 0) {
			tjgs1 = 'ENTERSALE_0' + esyun + esjia;
		}
	}
	if ($("#retailsalec").prop("checked") == true) { //零售价
		var retailsale = $('#retailsale').val();
		var rsyun = $('#rsyun').find("option:selected").text();
		var rsjia = Number($('#rsjia').val());
		if (retailsale == 6) {
			tjgs2 = 'SALE5_0' + rsyun + rsjia;
		} else if (retailsale == 5) {
			tjgs2 = 'SALE4_0' + rsyun + rsjia;
		} else if (retailsale == 4) {
			tjgs2 = 'SALE3_0' + rsyun + rsjia;
		} else if (retailsale == 3) {
			tjgs2 = 'SALE2_0' + rsyun + rsjia;
		} else if (retailsale == 2) {
			tjgs2 = 'SALE1_0' + rsyun + rsjia;
		} else if (retailsale == 1) {
			tjgs2 = 'RETAILSALE_0' + rsyun + rsjia;
		} else if (retailsale == 0) {
			tjgs2 = 'ENTERSALE_0' + rsyun + rsjia;
		}

	}
	if ($("#sale1c").prop("checked") == true) { //售价一
		var sale1 = $('#sale1').val();
		var sale1yun = $('#sale1yun').find("option:selected").text();
		var sale1jia = Number($('#sale1jia').val());
		if (sale1 == 6) {
			tjgs3 = 'SALE5_0' + sale1yun + sale1jia;
		} else if (sale1 == 5) {
			tjgs3 = 'SALE4_0' + sale1yun + sale1jia;
		} else if (sale1 == 4) {
			tjgs3 = 'SALE3_0' + sale1yun + sale1jia;
		} else if (sale1 == 3) {
			tjgs3 = 'SALE2_0' + sale1yun + sale1jia;
		} else if (sale1 == 2) {
			tjgs3 = 'SALE1_0' + sale1yun + sale1jia;
		} else if (sale1 == 1) {
			tjgs3 = 'RETAILSALE_0' + sale1yun + sale1jia;
		} else if (sale1 == 0) {
			tjgs3 = 'ENTERSALE_0' + sale1yun + sale1jia;
		}

	}
	if ($("#sale2c").prop("checked") == true) { //售价二
		var sale2 = $('#sale2').val();
		var sale2yun = $('#sale2yun').find("option:selected").text();
		var sale2jia = Number($('#sale2jia').val());
		if (sale2 == 6) {
			tjgs4 = 'SALE5_0' + sale2yun + sale2jia;
		} else if (sale2 == 5) {
			tjgs4 = 'SALE4_0' + sale2yun + sale2jia;
		} else if (sale2 == 4) {
			tjgs4 = 'SALE3_0' + sale2yun + sale2jia;
		} else if (sale2 == 3) {
			tjgs4 = 'SALE2_0' + sale2yun + sale2jia;
		} else if (sale2 == 2) {
			tjgs4 = 'SALE1_0' + sale2yun + sale2jia;
		} else if (sale2 == 1) {
			tjgs4 = 'RETAILSALE_0' + sale2yun + sale2jia;
		} else if (sale2 == 0) {
			tjgs4 = 'ENTERSALE_0' + sale2yun + sale2jia;
		}

	}
	if ($("#sale3c").prop("checked") == true) { //售价三
		var sale3 = $('#sale3').val();
		var sale3yun = $('#sale3yun').find("option:selected").text();
		var sale3jia = Number($('#sale3jia').val());
		if (sale3 == 6) {
			tjgs5 = 'SALE5_0' + sale3yun + sale3jia;
		} else if (sale3 == 5) {
			tjgs5 = 'SALE4_0' + sale3yun + sale3jia;
		} else if (sale3 == 4) {
			tjgs5 = 'SALE3_0' + sale3yun + sale3jia;
		} else if (sale3 == 3) {
			tjgs5 = 'SALE2_0' + sale3yun + sale3jia;
		} else if (sale3 == 2) {
			tjgs5 = 'SALE1_0' + sale3yun + sale3jia;
		} else if (sale3 == 1) {
			tjgs5 = 'RETAILSALE_0' + sale3yun + sale3jia;
		} else if (sale3 == 0) {
			tjgs5 = 'ENTERSALE_0' + sale3yun + sale3jia;
		}

	}
	if ($("#sale4c").prop("checked") == true) { //批发价
		var sale4 = $('#sale4').val();
		var sale4yun = $('#sale4yun').find("option:selected").text();
		var sale4jia = Number($('#sale4jia').val());
		if (sale4 == 6) {
			tjgs6 = 'SALE5_0' + sale4yun + sale4jia;
		} else if (sale4 == 5) {
			tjgs6 = 'SALE4_0' + sale4yun + sale4jia;
		} else if (sale4 == 4) {
			tjgs6 = 'SALE3_0' + sale4yun + sale4jia;
		} else if (sale4 == 3) {
			tjgs6 = 'SALE2_0' + sale4yun + sale4jia;
		} else if (sale4 == 2) {
			tjgs6 = 'SALE1_0' + sale4yun + sale4jia;
		} else if (sale4 == 1) {
			tjgs6 = 'RETAILSALE_0' + sale4yun + sale4jia;
		} else if (sale4 == 0) {
			tjgs6 = 'ENTERSALE_0' + sale4yun + sale4jia;
		}

	}
	if ($("#sale5c").prop("checked") == true) { //打包价 
		var sale5 = $('#sale5').val();
		var sale5yun = $('#sale5yun').find("option:selected").text();
		var sale5jia = Number($('#sale5jia').val());
		if (sale5 == 6) {
			tjgs7 = 'SALE5_0' + sale5yun + sale5jia;
		} else if (sale5 == 5) {
			tjgs7 = 'SALE4_0' + sale5yun + sale5jia;
		} else if (sale5 == 4) {
			tjgs7 = 'SALE3_0' + sale5yun + sale5jia;
		} else if (sale5 == 3) {
			tjgs7 = 'SALE2_0' + sale5yun + sale5jia;
		} else if (sale5 == 2) {
			tjgs7 = 'SALE1_0' + sale5yun + sale5jia;
		} else if (sale5 == 1) {
			tjgs7 = 'RETAILSALE_0' + sale5yun + sale5jia;
		} else if (sale5 == 0) {
			tjgs7 = 'ENTERSALE_0' + sale5yun + sale5jia;
		}
	}
	var noteno = $('#unoteno').val(); //单据号
	var priceprec = $('#priceprec').val(); //小数精度
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "changewareadjustmprice",
			noteno: noteno,
			priceprec: priceprec,
			tjgs1: tjgs1,
			tjgs2: tjgs2,
			tjgs3: tjgs3,
			tjgs4: tjgs4,
			tjgs5: tjgs5,
			tjgs6: tjgs6,
			tjgs7: tjgs7
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var res = data.result;
					alerttext("操作成功！");
					getnotewarem($('#unoteno').val(), "1");
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});

}
//关闭批量调价窗口
function closeAll() {
	$('#pltjup').dialog('close');
}

//关闭商品调价窗口
function closesp() {
	$('#sptjup').dialog('close');
}

function sptjup() { //指定商品调价 
	$('#sptjup').dialog('close');
	var id = Number($('#idq').val()); //id
	var entersale = $('#entersaleh').val(); //进价
	var retailsale = $('#retailsaleh').val(); //零售价
	var sale1 = $('#sale1h').val(); //售价一
	var sale2 = $('#sale2h').val(); //售价二
	var sale3 = $('#sale3h').val(); //售价三
	var sale4 = $('#sale4h').val(); //批发价
	var sale5 = $('#sale5h').val(); //打包价 
	var noteno = $('#unoteno').val(); //单据号
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewareadjustmbyid",
			id: id,
			noteno: noteno,
			retailsale: retailsale,
			sale1: sale1,
			sale2: sale2,
			sale3: sale3,
			sale4: sale4,
			sale5: sale5
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
						var data1 = data.rows;
						var RETAILSALE = data1.RETAILSALE;
						var SALE4 = data1.SALE4;
						var SALE5 = data1.SALE5;
						var SALE3 = data1.SALE3;
						var SALE2 = data1.SALE2;
						var SALE1 = data1.SALE1;
						var index = $('#windex').val();
						$('#waretj').datagrid('updateRow', {
							index: index,
							row: {
								RETAILSALE: RETAILSALE,
								SALE4: SALE4,
								SALE5: SALE5,
								SALE3: SALE3,
								SALE2: SALE2,
								SALE1: SALE1
							}
						});
						$('#waretj').datagrid('refresh');
						$('#waretj').datagrid('selectRow', index);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});

}
//删除  单据明细
function delwaremx() {
	var noteno = $('#unoteno').val();
	var rowData = $('#waretj').datagrid("getSelected");
	if (rowData) {
		var id = rowData.ID;
		var index = $('#windex').val();
		$.messager.confirm(dialog_title, '您确认要删除' + rowData.WARENO + '，' + rowData.WARENAME + '吗？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delwareadjustmbyid",
						noteno: noteno,
						id: id
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$('#waretj').datagrid('deleteRow', deleteindex);
								$('#waretj').datagrid('refresh');
								$("#wmshowtotal").html($('#waretj').datagrid('getRows').length);
							}
						} catch(err) {
							console.error(err.message);
						}
					}
				});
			}
		});
	} else {
		alerttext("请选择一条记录！");
	}
}
//撤单
function chedantj() {
	var rowData = $('#gg').datagrid("getSelected");
	var id = Number(rowData.ID);
	var noteno = rowData.NOTENO;
	var notedate = rowData.NOTEDATE; //单据日期
	var date = notedate.substring(0, 10).toString();
	var year = myDates.getFullYear();
	var month = (myDates.getMonth() + 1) < 10 ? '0' + (myDates.getMonth() + 1) : (myDates.getMonth() + 1);
	var day = myDates.getDate() < 10 ? "0" + myDates.getDate() : myDates.getDate();
	var todaydate = (year + '-' + month + '-' + day).toString();
	if (date == todaydate) {
		$.messager.confirm(dialog_title, '您确认要撤单吗?',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "wareadjusthcancel",
						noteno: noteno,
						id: id
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$('#gg').datagrid('updateRow', {
									index: dqindex,
									row: {
										STATETAG: '0'
									}
								});
								$('#gg').datagrid('refresh');
								$('#ud').dialog('close');
							}
						} catch(err) {
							console.error(err.message);
						}
					}
				});
			}
		});
	} else {
		alerttext("只允许撤当天单据！");
	}

}

//作废
function zuofeitj() {
	var rowData = $('#gg').datagrid("getSelected");
	var id = Number(rowData.ID);
	var noteno = rowData.NOTENO;
	$.messager.confirm(dialog_title, '您确认要作废这张单据吗?',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "updatewareadjusthbyid",
					noteno: noteno,
					id: id
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							$('#gg').datagrid('updateRow', {
								index: dqindex,
								row: {
									STATETAG: '2'
								}
							});
							$('#gg').datagrid('refresh');
							$('#ud').dialog('close');
						}
					} catch(err) {
						console.error(err.message);
					}
				}
			});
		}
	});

}
//删除  调价单据
function deletetj() {
	var noteno = $("#unoteno").val();
	var index = $("#gg").datagrid("getRowIndex",noteno);
	var rowData = $('#gg').datagrid("getRows")[index];
	if (rowData) {
		if (Number(rowData.STATETAG) == 0) {
			var id = Number(rowData.ID);
			var noteno = rowData.NOTENO;
			$.messager.confirm(dialog_title, '您确认要删除' + rowData.NOTENO + '吗？',
			function(r) {
				if (r) {
					alertmsg(2);
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "delwareadjusthbyid",
							noteno: noteno
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									$('#gg').datagrid('deleteRow', index);
									$('#ud').dialog('close');
									$('#gg').datagrid('refresh');
									deltotal = deltotal - 1;
									$('#pp_total').html(deltotal);
								}
							} catch(err) {
								console.error(err.message);
							}
						}
					});
				}
			});
		} else {
			alerttext("不允许删除已提交的单据！");
		}
	} else {
		alerttext("请选择一行数据");
	}
}
function getnotehbyid() {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名
		async: false,
		data: {
			erpser: "getwareadjusthbyid",
			noteno: noteno,
			id: id
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				if (data.total >= 1) {
					var rows = data.rows;
					var houseid = Number(rows[0].HOUSEID);
					if (houseid > 0) {
						$('#uhouseid').val(houseid);
					}
					var istoday = rows[0].ISTODAY;
					$('#uistoday').val(istoday);
					if (istoday == "1" && allowchedan == '1') {
						$('#btn').show();
					} else {
						$('#sep-').hide();
						$('#btn').hide();
					}
				}
			}
		}
	});
}
var currentdata = {};
//获取商品调价明细数据
function getnotewarem(noteno, page) {
	alertmsg(6);
	var findbox = $('#udfindbox').val();
	var houseid = $('#uhouseid').val();
	//var sortid = $('#waremsort').prop('checked')?1:0;
	currentdata = {
			erpser: "wareadjustmlist",
			noteno: noteno,
			findbox: findbox,
			houseid: houseid,
			rows: pagecount,
			page: page
		};
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名
		async: false,
		data: currentdata,
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var totals = data.total;
					deleteindex = totals;
					$('#pagetotal').val(totals);
					sumpg = Math.ceil(Number(totals) / pagecount);
					$('#warepp').createPage({
						pageCount: sumpg,
						current: Number(page)
					});
					$('#waretj').datagrid('loading');
					if (page == '1') {
						pg = 1;
						nextpg = true;
						$('#waretj').datagrid('loadData', data);
						if ($('#waretj').datagrid('getRows').length > 0) {
							$('#waretj').datagrid('selectRow', 0);
						}
					} else {
						pg = pg + 1;
						var rows = data.rows;
						for (var i in rows) {
							$('#waretj').datagrid('appendRow', rows[i]);
						}
					}
					$('#waretj').datagrid('loaded');
					$('#wmtotal').html(totals);
					$("#wmshowtotal").html($('#waretj').datagrid('getRows').length);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

//增加商品调价记录post
function addwarecheckh() {
	//alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addwareadjusth",
			houseid: getValuebyName('HOUSEID')
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#uid').val(data.ID);
					$('#ustatetag').val(0);
					$('#unoteno').val(data.NOTENO);
					$('#unotedate').val(data.NOTEDATE);
					$('#uhouseid').val(getValuebyName('HOUSEID'));
					$('#checkhouse').val(getValuebyName('HOUSENAME'));
					var year = myDates.getFullYear();
					var month = myDates.getMonth() + 1;
					var day = myDates.getDate();
					var day2 = day + 7;
					var date2 = year + '-' + month + '-' + day2;
					$('#tjmindates').datebox('setValue', myDates.Format('yyyy-MM-dd')); // 设置日期输入框的值  
					$('#tjmaxdates').datebox('setValue', date2); // 设置日期输入框的值 
					dqindex = 0;
					dqamt = 0;
					dqcurr = 0;
					getnotedata();
					$('#waretj').datagrid('loadData', {
						total: 0,
						rows: []
					});
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//handno
function changehandno(handno) {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewareadjusthbyid",
			id: id,
			noteno: noteno,
			handno: handno
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#gg').datagrid('updateRow', {
						index: dqindex,
						row: {
							HANDNO: handno
						}
					});
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	})
}
//摘要
function changeremark(remark) {
	var id = $('#uid').val();
	var noteno = $('#unoteno').val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updatewareadjusthbyid",
			id: id,
			noteno: noteno,
			remark: remark
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#gg').datagrid('updateRow', {
						index: dqindex,
						row: {
							REMARK: remark
						}
					});
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	})
}

function openloadcks() { //载入商品弹窗
	$('#loadkcds').dialog('open');
}

//载入商品
function loadkcs() {
	$('#loadkcds').dialog('close');
	var noteno = $('#unoteno').val();
	var wareid = Number($('#awareid').val()); //商品id
	var wareno = $('#awareno').val(); //货号
	var seasonname = $('#lseasonname').val(); //季节
	var prodyear = $('#prodyear').val(); //年份
	var provid = $('#lprovid').val(); //生产商
	var typeid = $('#ltypeid').val(); //类型
	var brandid = $('#lbrandid').val(); //品牌 
	provid = provid==""?-1:provid;
	typeid = typeid==""?-1:typeid;
	brandid = brandid==""?-1:brandid;
	var areaid = Number($('#lareaid').val()); //区位 
	var minsale = $('#lminsale').val(); //生产商
	var maxsale = $('#lmaxsale').val(); //生产商
	var locale = $('#locale').val(); //货位
	var loadbj;
	if ($('#loadbj').prop('checked')) loadbj = '0';
	else loadbj = '1';
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "loadtowareadjustm",
			noteno: noteno,
			wareid: wareid,
			wareno: wareno,
			typeid: typeid,
			brandid: brandid,
			provid: provid,
			areaid: areaid,
			loadbj: loadbj,
			locale: locale,
			seasonname: seasonname,
			prodyear: prodyear
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		},
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					getnotewarem(noteno,1);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
function cleanload() {
	$('#awareid').val(''); //商品id
	$('#ltypeid').val(''); //类型
	$('#lbrandid').val(''); //品牌 
	$('#lareaid').val(''); //品牌 
	$('#loadkcds table input').val('');
	$('#loadbj').removeAttr('checked');
}

function checkwareadjusth(opid) {
	var noteno = $("#unoteno").val();
	var index = $('#gg').datagrid('getRowIndex', noteno);
	if(index==-1){
		alerttext("未选择有效单据号，请刷新页面重试！");
		return;
	}
	var msg = "确定审核调价单：" + noteno;
	if(opid==0) msg = "确定取消审核调价单：" + noteno;
	$.messager.confirm(dialog_title, msg,
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名            
				data: {
					erpser: "checkwareadjusth",
					opid: opid,
					noteno: noteno
				},
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							$('#gg').datagrid('updateRow', {
								index: index,
								row: {
									STATETAG: (opid == 0 ? 1 : 2),
									CHECKMAN: (opid == 0 ? "": getValuebyName('EPNAME'))
								}
							});
							$('#ud').dialog('close');
							$('#gg').datagrid('refresh');

						}
					} catch(err) {
						console.error(err.message);
					}
				}
			});
		}
	});

}

function refusewareadjusth() {
	var row = $('#gg').datagrid('getSelected');
	var index = $('#gg').datagrid('getRowIndex', row);
	var msg = "确定审核驳回调价单：" + row.NOTENO;
	var checkremark = $('#refuse_remark').val().replace(/\n/g, "");
	if (checkremark.length == 0) {
		alerttext("请输入审核驳回原因！");
		$('#refuse_remark').focus();
		return;
	}
	$.messager.confirm(dialog_title, msg,
	function(r) {
		if (r) {
			alertmsg(2);
			var opid = 2; //审核驳回
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名            
				data: {
					erpser: "checkwareadjusth",
					opid: opid,
					noteno: row.NOTENO,
					checkremark: checkremark
				},
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							$('#gg').datagrid('updateRow', {
								index: index,
								row: {
									STATETAG: 0,
									CHECKMAN: getValuebyName('EPNAME'),
									CHECKREMARK: checkremark
								}
							});
							$('#ud,#refused').dialog('close');
							$('#gg').datagrid('refresh');
						}
					} catch(err) {
						console.error(err.message);
					}
				}
			});
		}
	});

}
function isAddWarem() {
	var housename = $('#checkhouse').val();
	if (housename.length == 0) {
		alerttext('请选择店铺');
		$('#housename').focus();
		return false;
	} else {
		return true;
	}
}		
</script>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
		<!-- 得到调价店铺列表 -->
	<jsp:include page="../HelpList/TJHouseList.jsp"></jsp:include>
	
<!-- 类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/AreaHelpList.jsp"></jsp:include>
<!-- 生产商帮助列表 -->
<jsp:include page="../HelpList/ProvHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/ImportHelp.jsp"></jsp:include>
</body>
</html>