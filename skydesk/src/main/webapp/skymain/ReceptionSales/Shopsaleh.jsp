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
<title>前台收银</title>
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
<%-- <script type="text/javascript" src='/skydesk/js/LodopFuncs.js?ver=<%=tools.DataBase.VERSION%>'></script> --%>
</head>
<body class="easyui-layout layout panel-noscroll" style="font-size: 14px">
<!-- <div id="printgg" style="width:100%;overflow:auto;display:none;"></div> -->
<div class="ntoolbar"> 
<div class="left-div">
<input type="hidden" id="id">
<div class="note-cell">
<div class="note-title">单据号：</div>
<div class="noteh-text" id="noteno" style="width:130px;cursor: pointer;" ondblclick="if(hasnote()){openremarkd()}" title="双击填写备注">&nbsp;</div>
</div>
<div class="note-cell">
<div class="noteh-title">日期：</div>
<div class="noteh-text" id="notedate" style="width:85px;text-align: center;line-height: normal;padding-top: 6px;"></div>
</div>
<div class="note-cell">
<div class="noteh-title">收银员：</div>
<div class="noteh-text" id="cashman"  style="width:auto;"></div>
</div>
<div class="note-cell">
<div class="noteh-title" color="#ff7900">*&nbsp;店铺：</div>
<div style="padding-top: 10px;float:left">
<input class="edithelpinput house_help" id="uhousename" type="text"
 data-event="onSelect: changehouse"
 data-value="#uhouseid" placeholder="<输入>">
<span class="s-btn" onclick="selecthouse('update')"></span>
<input type="hidden" id="uhouseid" value="">
</div>
</div>
</div>
<div class="right-div">
<input type="button" class="btn7" value="增加" id="add" onclick="addshopsaleh()">
<input type="button" class="btn7 hide" value="整单打折" id="zddiscbtn" onclick="$('#discd').dialog('open')">
<input type="button" class="btn7 hide" value="挂单" id="guadanbtn" onclick="guadanshopsaleh()">
<input type="button" class="btn7 hide" value="放弃" id="cancelbtn" onclick="delshopsaleh()">
<input type="button" class="btn7 hide" value="收款" id="paybtn" onclick="openpay()">
<input type="button" class="btn7 hide" value="打印" id="print">
<input type="button" class="btn7" value="选择打印端口" id="selprint" onclick="selprint()">
<!-- <input type="button" class="btn7" value="前台打印" disabled="disabled" id="qtprint" onclick="qtprint()"> -->
<input type="button" class="btn7" value="历史查询" id="historybtn" onclick="openhistory()">
<input type="button" class="btn7 hide" value="交定金">
<input type="button" class="btn7 hide" value="提货">
<input type="button" class="btn7 hide" value="活动方案">
</div>
</div>
<div class="nbody" style="overflow: hidden;zoom:1;clear:both;">
<input type="hidden" id="guestid">
<input type="hidden" id="vtid">
<input type="hidden" id="statetag" value="0">
<div class="nbody-left">
<table id="note-waremt"></table>
<div class="npanel hide" id="addmxpanel">
<div class="npanel-header"> 
手工或扫码输入
</div>
<div class="npanel-body" id="addware-body" style="overflow-x: auto;">
<input type="hidden" id="epid">
<input type="hidden" id="wareid">
<input type="hidden" id="colorid">
<input type="hidden" id="sizeid">
<input type="hidden" id="warename">
<input type="hidden" id="tjdisc">
<input type="hidden" id="vipdisc">
<table cellspacing="10" class="npanel-table" id="addware-table">
<tr>
<td class="text-header">选择会员：</td>
<td>
<span class="icon_barcode" onclick="changesetvip()" title="切换选择输入会员方式"></span>
	  <input type="text" class="ipt7 hide combox-input" style="width:95px;color:#fff;" id="vipbarcode" title="扫会员二维码输入" placeholder="<扫会员二维码输入>">
<input type="text" style="width:88px;" class="ipt7 selvip helpipt" placeholder="<卡号或者手机号>" data-value="shopsalevip" id="guestfindbox">
<span class="help-s-btn selvip" onclick="if(hasnote()){selectvip('shopsalevip')}">▼</span>
</td>
<td><input type="button" class="btn7 hide" value="增加会员"><input type="button" class="btn7 fr" value="取消会员" onclick="cancelvip()"></td>
<td><input type="button" class="btn7" value="保存商品" onclick="if(hasware())addshopsalem()"></td>
<td class="text-header">销售人：</td>
<td><input type="text" class="ipt7 uppercase" id="saleman" placeholder="<输入>" onchange="$('#epid').val('')" >
<span class="help-s-btn" onclick="selectemploye('shopsale');">▼</span></td>
<td>
<label>
<input type="checkbox" id="xstype" checked>多人销售模式
</label>
</td>
</tr>
<tr>
<td class="text-header" width="90">条码扫描：</td><td><input type="text" class="ipt7" id="baradd" data-end="yes" placeholder="<输入>" style="ime-mode:disabled;"></td>
<td class="text-header">货号：</td><td><input type="text" class="ipt7 wareno_help helpipt" data-value="shopsaleware" id="wareno" placeholder="<输入>" ><span class="help-s-btn" onclick="selware()">▼</span></td>
<td class="text-header">颜色：</td><td><input type="text" class="ipt7 " data-value="#colorid" id="colorname" placeholder="<输入>" readonly><span class="help-s-btn" onclick="selcolor('shopsale')">▼</span></td>
<td colspan="2">尺码：<input type="text" class="ipt7" data-value="#sizeid" id="sizename" placeholder="<输入>" readonly><span class="help-s-btn" onclick="selsize('shopsale')">▼</span></td>
<!-- <td></td> -->
<!-- <td></td> -->
<!-- <td></td> -->
<!-- <td></td> -->
<!-- <td></td> -->
</tr>
<tr>
<td class="text-header">零售价：</td><td><input type="text" class="ipt7" id="price0"  placeholder="<请选择>" readonly></td>
<td class="text-header">数量：</td><td><input type="text" class="ipt7" id="amount" value="1" placeholder="<输入>" maxlength="3" ></td>
<td class="text-header">折扣：</td><td><input type="text" class="ipt7" id="discount" value="1" placeholder="<输入>"  maxlength="4"></td>
<td colspan="2">单价：<input type="text" class="ipt7" id="price" placeholder="<输入>"  maxlength="7"></td>
</tr>
<tr>
<td class="text-header">金额：</td><td><input type="text" class="ipt7" id="curr" readonly placeholder="<输入>"></td>
</tr>
</table>
</div>
<div class="quickkey_msg">
快捷键说明：
<span>
+号-增加空单或加1；
</span>
<span>
-号-减1；
</span>
<span>
F1-输入会员；
</span>
<span>
F2-输入销售人；
</span>
<span>
F3-输入条码；
</span>
<span>
F4-结账；
</span>
<span>
F6-整单打折；
</span>
<span>
F9-后台打印；
</span>
</div>
</div>
</div>
<div class="nbody-right">
<div class="npanel">
<div class="npanel-header">
收款
<span class="fr" style="margin-right: 10px;cursor: pointer;" id="tgpayd" onclick="tglepayd()">▲</span>
</div>
<div class="npanel-body">
<table cellspacing="5" class="npanel-table" style="width:212px;">
<tr><td class="text-header-left">应收合计：</td><td class="curr-td" id="sshj" style="font-weight: 600;font-size:25px;">&nbsp;</td></tr>
<tr><td class="text-header-left">收款合计：</td><td class="curr-td" id="hkhj" style="font-weight: 600;font-size:25px;">&nbsp;</td></tr>
<tr><td class="text-header-left">找零：</td><td class="curr-td" id="zl" style="font-weight: 600;font-size:25px;">&nbsp;</td></tr>
<tr class="yhmsg"><td colspan="2" style="height:1px;"><span class="dashed-tr">&nbsp;</span></td></tr>
<tr class="yhmsg"><td class="text-header-left" width="90">总金额：</td><td class="curr-td" id="yshj" style="font-size:25px;">&nbsp;</td></tr>
<tr class="yhmsg"><td class="text-header-left">免单金额：</td><td class="curr-td" id="yhje" style="font-size:25px;">&nbsp;</td></tr>
<tr class="yhmsg"><td class="text-header-left">积分抵扣：</td><td class="curr-td" id="jfdk" style="font-size:25px;">&nbsp;</td></tr>
<tr class="yhmsg"><td class="text-header-left">优惠券：</td><td class="curr-td" id="yhq" style="font-size:25px;">&nbsp;</td></tr>
</table>
</div>
</div>
<div class="vip-detail">
<div class="vip-left">
<div class="note-cell">
<div class="note-title">会员卡号：</div>
<div class="note-text" id="vipno"></div>
</div>
<div class="note-cell">
<div class="note-title">会员姓名：</div>
<div class="note-text" id="guestname"></div>
</div>
<div class="note-cell">
<div class="note-title">移动电话：</div>
<div class="note-text" id="mobile"></div>
</div>
<div class="note-cell">
<div class="note-title">积分余额：</div>
<div class="note-text" id="balcent"></div>
</div>
<div class="note-cell">
<div class="note-title">储值余额：</div>
<div class="note-text" id="balcurr"></div>
</div>
<div class="note-cell">
<div class="note-title">本次积分：</div>
<div class="note-text" id="cent"></div>
</div>
<div class="note-cell">
<div class="note-title">会员类型：</div>
<div class="note-text" id="vtname" style="width:95px"></div>
</div>
</div>
</div>
</div>

</div>
<!-- 商品编辑 -->
<div id="uwaremd" title="调整价格" data-options="modal:true" style="width: 400px; height: 400px" class="easyui-dialog" closed="true" data-qickey="F4:'updateshopsalem()'">
<div style="width:100%;height:300px;overflow: auto;text-align: center;">
<input type="hidden" id="uid">
<input type="hidden" id="uepid">
<input type="hidden" id="uwareid">
<input type="hidden" id="ucolorid">
<input type="hidden" id="usizeid">
<table cellspacing="10" class="npanel-table" id="updateware-table" style="width:230px;display: inline-table;margin-top: 15px;overflow: hidden;zoom:1;">
<tr>
<td class="text-header">货号：</td>
<td align="left"><input type="text" class="ipt7" id="uwareno" placeholder="<输入>" readonly></td>
</tr>
<tr>
<td class="text-header">颜色：</td>
<td align="left"><input type="text" class="ipt7 " data-value="#ucolorid" id="ucolorname" placeholder="<输入>" readonly>
<!-- <span class="help-s-btn" onclick="selectwarecolor($('#uwareid').val(),'update');">▼</span> -->
</td>
</tr>
<tr>
<td class="text-header">尺码：</td>
<td align="left"><input type="text" class="ipt7" data-value="#usizeid" id="usizename" placeholder="<输入>" readonly>
<!-- <span class="help-s-btn" onclick="selectsize($('#uwareid').val(),'update');">▼</span> -->
</td>
</tr>
<tr>
<td class="text-header">零售价：</td>
<td align="left"><input type="text" class="ipt7" id="uprice0"  placeholder="<请选择>" readonly></td>
</tr>
<tr>
<td class="text-header">数量：</td>
<td align="left"><input type="text" class="ipt7" id="uamount" value="1" placeholder="<输入>" readonly  maxlength="3"></td>
</tr>
<tr>
<td class="text-header">折扣：</td>
<td align="left"><input type="text" class="ipt7" id="udiscount" value="1" placeholder="<输入>" maxlength="4"></td>
</tr>
<tr>
<td class="text-header">单价：</td>
<td align="left"><input type="text" class="ipt7" id="uprice" placeholder="<输入>"  maxlength="7"></td>
</tr>
<tr>
<td class="text-header">金额：</td>
<td align="left"><input type="text" class="ipt7" id="ucurr" readonly placeholder="<输入>"></td>
</tr>
<tr>
<td class="text-header">销售人：</td>
<td align="left" width="140"><input type="text" class="ipt7 saleman_help helpipt" data-value="#uepid" id="uepname" placeholder="<输入>" ><span class="help-s-btn" onclick="selectemploye('update');">▼</span></td>
</tr>
</table>
</div>
<div class="dialog-footer">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" value="保存" onclick="updateshopsalem()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#uwaremd').dialog('close')">
</div>
</div>
<!-- 收款框 -->
<div id="payd" title="收款结算" style="width: 580px; max-height: 100%;" class="easyui-dialog" closed="true" data-qickey="F4:'updateshopsaleh()',Z:'quick_zf(0)'">
<div style="width:100%;overflow: auto;text-align: center;">
<input type="hidden" id="newask" value="1">
<table width="550" border="0" cellspacing="10" class="table1">
  <tr>
    <td align="right" class="header" width="80">合计金额</td>
    <td align="right"><input class="wid160 hig25 " type="text" id="thispay" readonly></td>
    <td align="right" class="header">免单金额</td>
    <td align="right"><input class="wid160 hig25" type="text" id="discpay" placeholder="<输入>" onkeyup="sumpay()"></td>
  </tr>
  <tr id="isusecent" class="hide">
    <td align="right" class="header">抵扣积分</td>
    <td align="right"><input class="wid160 hig25" type="text" id="usecent" placeholder="<输入可用积分>" onkeyup="sumpay()"></td>
    <td align="right" class="header">抵扣金额</td>
    <td align="right"><input class="wid160 hig25" type="text" id="jfcurr" readonly></td>
  </tr>
    <tr id="isuseyhq" class="hide">
    <td align="right" class="header">优惠券</td>
    <td align="left" colspan="3">
	<select class="hig25" id="yhjcurr" style="width: 115px;" >
	<option value="0" data-xfcurr="0" data-vcpid="0">不使用优惠券</option>
	</select>
	<input class="hig25" style="width: 70px;" type="text" id="yhjcurrx" style="width: 70px;" value="0" readonly>
	</td>
   </tr>
   <tr>
    <td align="right" class="header imp">实结金额</td>
    <td align="left" colspan="3"><input class="wid160 hig25 imp" type="text" id="checkpay" readonly></td>
  </tr>
  <tr id="payway" style="display: none"></tr>
  <tr>
  </tr>
  <tr>
  <td align="right" class="header imp">收款合计</td>
    <td align="right"><input class="wid160 hig25 imp" type="text" id="sumpay" readonly></td>
    <td align="right" class="header imp">找零</td>
    <td align="right"><input class="wid160 hig25 imp" type="text" id="changecurr" readonly></td>
  </tr>
  <tr>
  <td class="header">
  单据摘要
  </td>
  <td colspan="3" align="left">
  <input class="hig25" id="uremark0" type="text" placeholder="<输入>" style="width:420px"/>
  </td>
  </tr>
</table>
</div>
<div class="dialog-normal-footer">
<!-- <label><input class="printtype" type="checkbox" id="isqtprint">前台打印</label> -->
<!-- <span id="zfkjfs" style="color:#ff7900;display:none;float:left;">快捷方式：Z-支付宝</span> -->
<label><input class="printtype" type="checkbox" id="isprint">后台打印</label>
<label><input class="addtype" type="checkbox" id="iscontinue">连续增加</label>
<input type="button" class="btn1 hide" id="sendvipmsg" value="发送会员验证" onclick="sendvipmessage()">
		<input type="button" id="btn_pay" class="btn1" style="width:70px;margin-right: 10px" name="pay" value="提交" onclick="updateshopsaleh()">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#payd').dialog('close')">
</div>
</div>
<!-- 整单打折框 -->
<div id="discd" title="整单打折" style="width: 350px; height: 200px"
		class="easyui-dialog" closed="true">
		<div style="margin-left: 40px;margin-top: 30px">
		<p>请输入折扣</p>
		<p><input class="hig25" id="alldiscount" type="text" placeholder="<请输入折扣>" data-enter="if(hasnote()){alldisc()}"/></p>
		</div>
		<div class="dialog-footer">
				<input type="button" class="btn1" style="width:70px;margin-right: 10px" maxlength="4" name="updateware" value="保存" onclick="if(hasnote()){alldisc()}">
				<input type="button" class="btn2" name="close" value="取消" onclick="$('#discd').dialog('close')">
	</div>
</div>
<!-- 备注 -->
<div id="remarkd" title="输入摘要" style="width: 350px; height: 200px"
		class="easyui-dialog" closed="true">
		<div style="margin-left: 40px;margin-top: 30px">
<!-- 		<p>请输入摘要</p> -->
		<p>
		<input class="hig25" id="uremark" type="text" placeholder="<输入>" style="width:200px" data-enter="if(hasnote()){changeremark()}"/>
		</p>
		</div>
		<div class="dialog-footer">
				<input type="button" class="btn1" style="width:70px;margin-right: 10px" id="remark_btn" value="保存" onclick="if(hasnote()){changeremark()}">
				<input type="button" class="btn2" name="close" value="取消" onclick="$('#remarkd').dialog('close')">
	</div>
</div>
<div class="paybj hide" id="paybj-div">
&nbsp;
</div>
<!-- 历史查询  -->
<div id="historyd" title="历史单据" data-options="modal:true" style="width: 100%; height: 100%" class="easyui-dialog" closed="true">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl ml10">
	<input type="text" name="smindate" id="smindate" placeholder="<输入>" style="width:100px;height:29px" class="easyui-datebox">
	---
	<input type="text" name="smaxdate" id="smaxdate" placeholder="<输入>" style="width:100px;height:29px" class="easyui-datebox">
	<span class="ml10">快速搜索：</span>
	<input  type="text" placeholder="搜索单据号、会员姓名<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" data-enter="getnotedata()" maxlength="20" ><input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	</div>
	<div class="fl hide" id="searchbtn" style="width:300px"><input type="button" class="btn2" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2"  value="清空搜索条件" onclick="resetsearch()">
	</div>
</div>
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="shouseid" id="shouseid" > 
<div class="search-box">
<div class="s-box"><div class="title" color="#ff7900">*&nbsp;店铺</div>
<div class="text">
<input class="edithelpinput house_help" id="shousename" type="text" data-value="#shouseid" placeholder="<输入>">
<span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>
<div class="s-box"><div class="title">会员</div>
<div class="text">
<input class="hig25 wid160" id="svipno" type="text" placeholder="<输入卡号或姓名或电话>">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text">
<input class="edithelpinput " id="swareno" type="text" data-value="#swareid" placeholder="<输入>"><span class="s-btn" onclick="selectware('search')"></span>
</div></div>
<div class="s-box"><div class="title">自编号</div>
<div class="text">
<input class="hig25 wid160" type="text" name="shandno" id="shandno" placeholder="<输入>" >
</div>
</div>
<div class="s-box"><div class="title">摘要</div>
<div class="text">
<input class="hig25 wid160" type="text" name="sreamrk" id="sremark" placeholder="<输入>" >
</div>
</div>
<div class="s-box">
<div class="title">单据状态</div>
	<div class="text">
	<label>
	<input type="radio" name="sstatetag" id="st3" value="2" checked="checked">所有
	</label>
	<label>
	<input type="radio" name="sstatetag" id="st1" value="0">未提交
	</label>
	<label>
	<input type="radio" name="sstatetag" id="st2" value="1">已提交
	</label>
	</div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden;height:900px"></table>
<!-- 分页 -->
<div class="dialog-footer">
<div class="fl ml10">
<div class="page-total" id="notetotal">共0条记录</div>
<div class="page-total" id="notetotal">快捷键说明：Del-删除或者撤销单</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#historyd').dialog('close')">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="updateware" value="载入" onclick="loadshopsale()">
</div>
</div>

<!-- 找零  -->
<div id="changecurrd" title="本次找零" style="width: 300px; height: 160px" class="easyui-dialog" closed="true">
<div id="thischangecurr" style="margin: 28px auto;font-size: 48px;color:#ff7900;text-align: center;">
</div>
</div>

<div id="shopsalemmenu" class="easyui-menu" style="width: 30px; display: none;">  
      <!--放置一个隐藏的菜单Div-->  
      <div id="btn_sort0" onclick="changesort(0)">货号排序</div>        
      <div id="btn_sort1" data-options="iconCls:'icon-ok'" onclick="changesort(1)">录入排序</div>        
 </div> 
<script type="text/javascript">
var levelid = getValuebyName("GLEVELID");
var pgid = "pg1210";
xstype = 1;
dytype = 2;
addtype = 1;
var dqwamt, dqwcurr, dqwindex;
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize = function() {
	changeDivHeight();
}
function changeDivHeight() {
	var height = document.documentElement.clientHeight; //获取页面可见高度 
	var width = document.documentElement.clientWidth; //获取页面可见kuan度  
	$('.nbody-left').width(width - 260);
	// 	$('#addware-body').width(width-271);
	var isshow = $('#addmxpanel').css('display') == 'none' ? true: false;
	if (isshow) {
		$('#note-waremt').datagrid('resize', {
			height: height - 55,
			width: $('.nbody-left').width() - 10
		});
	} else {
		$('#note-waremt').datagrid('resize', {
			height: height - 300,
			width: $('.nbody-left').width() - 10
		});
	}
	var stg = $('#statetag').val();
	if (stg == '1') $('#noteno,#notedate,#cashman').css('color', '#00959a');
	else $('#noteno,#notedate,#cashman').css('color', '#ff7900');
	// 		$('.vip-detail').width($('.nbody-left').width()-10);
}
var dialogbj = 0;
$(function() {
	setvipbarcode('#vipbarcode');
	setskypay({
		ywtag: 1,
		noteno: function() {
			return $('#noteno').html();
		},
		amt: function() {
			return $('#note-waremt').datagrid('getFooterRows')[0].AMOUNT;
		},
		totalcurr: '#checkpay',
		curr: function(payfs) {
			return $("input.paycurr.payfs"+payfs).val();
		},
		onBeforePay: function() {
			var totalcurr = Number($('#thispay').val());
			var jfcurr = Number($('#jfcurr').val());
			var changecurr = Number($('#changecurr').val());
			var usecent = Number($('#usecent').val());
			var freecurr = Number($('#discpay').val());
			var checkcurr = Number($('#checkpay').val());
			var zpaycurr = Number($('#sumpay').val());
			totalcurr = isNaN(totalcurr) ? 0 : totalcurr;
			jfcurr = isNaN(jfcurr) ? 0 : jfcurr;
			changecurr = isNaN(changecurr) ? 0 : changecurr;
			usecent = isNaN(usecent) ? 0 : usecent;
			checkcurr = isNaN(checkcurr) ? 0 : checkcurr;
			//	var checkcurr = totalcurr - jfcurr - freecurr;
			if (zpaycurr != checkcurr && checkcurr >= 0) {
				alerttext('收款合计不等于应结金额，请重新输入！');
				return false;
			} else if (checkcurr == 0) {
				alerttext('金额不能为0！');
				return false;
			}
			return true;
		},
		onSuccess: function(payfs, curr) {
			$("input.paycurr.payfs"+payfs).val(curr.toFixed(2));
			$("input.paycurr.payfs"+payfs).prop('readonly', true);
			$("input.paybtn.payfs"+payfs).prop("disabled",true);
			$('#pay1').focus();
			sumpay();
			if (Number($('.payw').val()) > 0) {
				if (skypayedbj.wxpay == 0) {
					alerttext('微信支付未完成，请支付！');
					return;
				}
			}
			if (Number($('.payz').val()) > 0) {
				if (skypayedbj.zfbpay == 0) {
					alerttext('支付宝支付未完成，请支付！');
					return;
				}
			}
			var errorpaymsg = "";
			$(".payqtzf").each(function(){
				var $this = $(this);
				var payname = $this.data("payname");
				var sucstg = Number($this.data("sucstg"));
				var curr = Number($this.val());
				if(curr>0){
					if(sucstg==0){
						errorpaymsg += payname+" ";
					}
				}
			});
			if (errorpaymsg.length > 0) {
				alerttext(errorpaymsg + '支付未完成，请重新支付！');
				return;
			}
			updateshopsaleh();
		}
	});
	$('#yhjcurr').change(function(e) {
		var val = Number(this.value);
		if (!isNaN(val)) {
			$('#yhjcurrx').val(val.toFixed(2));
			// 			getactpay();
			sumpay();
			checksendmsg();
		}
	});
// 	$('#payd').delegate('#usecent', 'keyup',
// 	function(e) {
// 		checksendmsg();
// 	});
	var xsparams = getparam({
		pgid: pgid.replace("pg", ""),
		usymbol: "xsparams"
	}); //第一位销售模式，第二位打印模式,第三位连续增加
	if (xsparams == "") {
		setparam({
			pgid: pgid.replace("pg", ""),
			usymbol: "xsparams",
			uvalue: "121"
		});
	} else {
		xstype = Number(xsparams.charAt(0));
		dytype = Number(xsparams.charAt(1));
		addtype = Number(xsparams.charAt(2));
	}
	if (xstype == 1) {
		var epid = Number(getValuebyName('EPID'));
		var epno = getValuebyName('EPNO');
		$('#salemanid').val(epid);
		$('#saleman').val(epno);
		$('#xstype').removeAttr('checked');
	} else if (xstype == 2) {
		$('#xstype').prop('checked', true);
	}
	if (dytype == 1) {
		$('#isqtprint').prop('checked', true);
	} else if (dytype == 2) {
		$('#isprint').prop('checked', true);
	}
	if (addtype == 1) {
		$('#iscontinue').prop('checked', true);
	} else if (addtype == 2) {
		$('#iscontinue').prop('checked', true);
	}

	$('#xstype').click(function(e) {
		var ck = $('#xstype').prop('checked');
		if (ck) {
			xstype = 2;
		} else {
			xstype = 1;
		}
		xsparams = xstype.toString() + dytype.toString() + addtype.toString();
		setparam({
			pgid: pgid.replace("pg", ""),
			usymbol: "xsparams",
			uvalue: xsparams
		});
	});
	$('.printtype').click(function(e) {
		if ($(this).prop('checked')) {
			$('.printtype').removeAttr('checked');
			$(this).prop('checked', true);
		}
		if ($('#isqtprint').prop('checked')) {
			dytype = 1;
		} else if ($('#isprint').prop('checked')) dytype = 2;
		else dytype = 0;
		xsparams = xstype.toString() + dytype.toString() + addtype.toString();
		setCookie("XSPARAMS", xsparams, 30);
	});
	$('.addtype').click(function(e) {
		var ck = $(this).prop('checked');
		if (ck) {
			addtype = 2;
		} else {
			addtype = 1;
		}
		xsparams = xstype.toString() + dytype.toString() + addtype.toString();
		setCookie("XSPARAMS", xsparams, 30);

	});
	$('#amount,#baradd').bind('keydown',
	function(e) {
		var key = e.which;
		var amt = Number($('#amount').val());
		if (key == 109) {
			$('#amount').val((amt - 1) == 0 ? -1 : (amt - 1));
			return false;
		} else if (key == 107) {
			$('#amount').val((amt + 1) == 0 ? 1 : (amt + 1));
			return false;
		}
	});
	$('#amount').keyup(function(e) {
		if (!$(this).prop('readonly')) {
			this.value = this.value.replace(/[^-?\d]/g, "");
			if (hasware()) {
				var price = Number($('#price').val());
				var amt = Number(this.value);
				amt = isNaN(amt) ? 0 : amt;
				price = isNaN(price) ? 0 : price;
				var curr = amt * price;
				$('#curr').val(getbylspoints(curr).toFixed(2));
				var key = e.which;
				if(key==13){
					addshopsalem();
				}
			}
		}
	});
	$('#discount').keyup(function(e) {
		if(e.which==13){return};
		if (!$(this).prop('readonly')) {
			this.value = this.value.replace(/[^\d.]/g, "");
			if (hasware()) {
				var price0 = Number($('#price0').val());
				var disc = Number(this.value);
				if (disc > 1) {
					this.value = "1.00";
					disc = 1;
				}
				var amt = Number($('#amount').val());
				disc = isNaN(disc) ? 1 : disc;
				price = isNaN(price) ? 0 : price;
				amt = isNaN(amt) ? 0 : amt;
				var price = getbypoints(disc * price0);
				$('#price').val(price.toFixed(2));
				var curr = amt * price;
				$('#curr').val(getbylspoints(curr).toFixed(2));
			}
		}
	});
	$('#price').keyup(function(e) {
		if (!$(this).prop('readonly')) {
			this.value = this.value.replace(/[^\d.]/g, "");
			if (hasware()) {
				if(e.which==13){
// 					$("#amount").focus();
// 					e.stopImmediatePropagation();
// 					e.stopPropagation();
					addshopsalem();
					return
					};
				var price0 = Number($('#price0').val());
				var amt = Number($('#amount').val());
				var price = Number(this.value);
				price0 = isNaN(price0) ? 0 : price0;
				price = isNaN(price) ? 0 : price;
				amt = isNaN(amt) ? 0 : amt;
				var disc;
				if (price0 == 0) {
					disc = 1;
				} else {
					disc = price / price0;
				}
				$('#discount').val(disc.toFixed(2));
				var curr = amt * getbypoints(price);
				$('#curr').val(getbylspoints(curr).toFixed(2));
			}
		}
	}).change(function(){
		this.value = getbypoints(this.value).toFixed(2);
	});
	$('#uamount').keyup(function(e) {
		if (!$(this).prop('readonly')) {
			this.value = this.value.replace(/[^\d]/g, "");
			var price = Number($('#uprice').val());
			var amt = Number(this.value);
			amt = isNaN(amt) ? 0 : amt;
			price = isNaN(price) ? 0 : price;
			var curr = amt * price;
			$('#ucurr').val(getbylspoints(curr).toFixed(2));
		}
	});
	$('#udiscount').keyup(function(e) {
		if (!$(this).prop('readonly')) {
			this.value = this.value.replace(/[^\d.]/g, "");
			var price0 = Number($('#uprice0').val());
			var disc = Number(this.value);
			if (disc > 1) {
				this.value = "1.00";
				disc = 1;
			}
			var amt = Number($('#uamount').val());
			disc = isNaN(disc) ? 1 : disc;
			price = isNaN(price) ? 0 : price;
			amt = isNaN(amt) ? 0 : amt;
			var price = disc * price0;
			$('#uprice').val(getbypoints(price).toFixed(2));
			var curr = amt * price;
			$('#ucurr').val(curr.toFixed(2));
		}
	});
	$('#uprice').keyup(function(e) {
		if (!$(this).prop('readonly')) {
			this.value = this.value.replace(/[^\d.]/g, "");
			var price0 = Number($('#uprice0').val());
			var amt = Number($('#uamount').val());
			var price = Number(this.value);
			price0 = isNaN(price0) ? 0 : price0;
			price = isNaN(price) ? 0 : price;
			amt = isNaN(amt) ? 0 : amt;
			var disc;
			if (price0 == 0) {
				disc = 1;
			} else {
				disc = price / price0;
			}
			$('#udiscount').val(disc.toFixed(2));
			var curr = amt * getbypoints(price);
			$('#ucurr').val(curr.toFixed(2));
		}
	}).change(function(){
		this.value = getbypoints(this.value).toFixed(2);
	});
	setaddbackprint("1210", {
		id: "$('#id').val()",
		noteno: "$('#noteno').html()"
	});
	$('#uwaremd,#discd,#historyd,#payd').dialog({
		onOpen: function() {
			dialogbj = 1;
			$('.easyui-dialog').window('center');
		},
		onClose: function(){
			dialogbj = 0;
		}
	});
	$('body').keydown(function(e) {
		$('#changecurrd').dialog('close');
		var key = e.which;
		if (dialogbj == 0) {
			if (key == 112) { //F1
				if (dialogbj == 0) {
					$('#guestfindbox').val('');
					$('#guestfindbox').focus();
				}
				e.preventDefault();
			} else if (key == 107) { //+
				if (dialogbj == 0) {
					if (!$('#add').is(':hidden')) addshopsaleh();
				}
				e.preventDefault();
			} else if (key == 113) { //F2
				if (dialogbj == 0) {
					$('#saleman').val('');
					$('#epid').val('');
					$('#saleman').focus();
				}
				e.preventDefault();
			} else if (key == 114) { //F3
				if (dialogbj == 0) {
					$('#baradd').focus();
				}
				e.preventDefault();
			} else if (key == 115) { //F4
				if (dialogbj == 0) $('#paybtn:not(:hidden)').click();
				e.preventDefault();
			} else if (key == 116) { //F5
				if (dialogbj == 0) $('#wareno').focus();
				e.preventDefault();
			} else if (key == 117) { //F6
				if (dialogbj == 0) $('#zddiscbtn:not(:hidden)').click();
				e.preventDefault();
			} else if (key == 120) { //F9
				if (dialogbj == 0) $('#print:not(:hidden)').click();
				e.preventDefault();
			}

		}
	});
	$('#historyd').keyup(function(e) {
		var key = e.which;
		if (key == 46) {
			var row = $('#gg').datagrid('getSelected');
			if(row){
				var stg = Number(row.STATETAG);
				var index = $('#gg').datagrid('getRowIndex', row);
				if (stg == 0) {
					delnote(index);
				} else if (stg == 1) {
					withdraw(index);
				}
			}
		}
	});
	$('#note-waremt').datagrid({
		idField: 'waresalem',
		width: $('.nbody-left').width() - 10,
		height: $('body').height() - 300,
		fitColumns: true,
		nowrap: false,
		striped: true,
		//隔行变色
		singleSelect: true,
		rownumbers: true,
		nowrap: false,
		//允许自动换行
		onDblClickRow: function(index, row) {
			var stg = $('#statetag').val();
			dqwindex = index;
			dqwamt = Number(row.AMOUNT);
			dqwcurr = Number(row.CURR);
			if(stg != '1'){
				$('#uid').val(row.ID);
				$('#uwareid').val(row.WAREID);
				$('#uwareno').val(row.WARENO);
				$('#ucolorid').val(row.COLORID);
				$('#usizeid').val(row.SIZEID);
				$('#uepid').val(row.SALEMANID);
				$('#uepname').val(row.EPNAME);
				$('#uwarename').val(row.WARENAME);
				$('#ucolorname').val(row.COLORNAME);
				$('#usizename').val(row.SIZENAME);
				$('#uprice0').val(row.PRICE0);
				$('#udiscount').val(row.DISCOUNT == '-' ? '0.00': Number(row.DISCOUNT).toFixed(2));
				$('#uprice').val(row.PRICE);
				$('#uamount').val(Number(row.AMOUNT));
				$('#ucurr').val(row.CURR);
				$('#uwaremd').dialog('open');
				if (row.ISZP == "1") {
					$('#udiscount,#uprice').attr('readonly', true);
				} else {
					$('#udiscount,#uprice').removeAttr('readonly');
				}
				$('#uprice').focus();
			}
		},
		showFooter: true,
		columns: [[{
			field: 'WARENO',
			title: '货号',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'WARENAME',
			title: '品名',
			width: 100,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'COLORNAME',
			title: '颜色',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SIZENAME',
			title: '尺码',
			width: 80,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'AMOUNT',
			title: '数量',
			width: 35,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'PRICE0',
			title: '零售价',
			width: 70,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == undefined ? '': Number(value).toFixed(2);
			}
		},
		{
			field: 'DISCOUNT',
			title: '折扣',
			width: 30,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == undefined ? '': Number(value) == 0 ? "-": Number(value).toFixed(2);
			}
		},
		{
			field: 'PRICE',
			title: '单价',
			width: 70,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return value == undefined ? '': Number(value).toFixed(2);
			}
		},
		{
			field: 'CURR',
			title: '金额',
			width: 70,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value).toFixed(2);
			}
		},
		{
			field: 'CHECKZP',
			title: '赠品',
			width: 35,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var stg = $('#statetag').val();
				var htm = "";
				var iszp = row.ISZP;
				if (stg == "0" && iszp == "0") return value == "noaction" ? "": '<input type="checkbox" class="che-iszp" data-index="' + index + '" data-mid="' + row.ID + '">';
				else if (stg == "0" && iszp == "1") return value == "noaction" ? "": '<input type="checkbox" class="che-iszp" data-index="' + index + '" data-mid="' + row.ID + '" checked>';
				else if (stg == "1" && iszp == "0") return value == "noaction" ? "": '<input type="checkbox" disabled>';
				else if (stg == "1" && iszp == "1") return value == "noaction" ? "": '<input type="checkbox" checked disabled>';
			}
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 80,
			fixed: true,
			hidden: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'EPNAME',
			title: '销售人',
			width: 70,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'REMARK',
			title: '备注',
			width: 3,
			align: 'center',
			halign: 'center',
			hidden: true
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var stg = $('#statetag').val();
				var warename = row.WARENAME;
				if (stg == "0" && warename!=undefined) 
					return  '<input type="button" value="删除" class="m-btn" onclick="delwarem(' + index + ')">';
				else return "";
			}
		},
		{
			field: 'ISZP',
			title: 'iszp',
			width: 3,
			hidden: true
		},
		{
			field: 'ID',
			title: 'id',
			width: 3,
			hidden: true
		}]],
		onRowContextMenu: function(e, index, row){
			//三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据  
            e.preventDefault(); //阻止浏览器捕获右键事件  
            if(index==-1) return;
            $(this).datagrid("clearSelections"); //取消所有选中项  
            $(this).datagrid("selectRow", index); //根据索引选中该行  
            $('#shopsalemmenu').menu('show', {  
                //显示右键菜单  
                left: e.pageX,//在鼠标点击处显示菜单  
                top: e.pageY  
            });  
            e.preventDefault();  //阻止浏览器自带的右键菜单弹出  
		},
		pageSize: 20
	});
	$('#guestfindbox').keyup(function(e) {
		var key = e.which;
		if (key == 13) {
			$(this).blur();
			var findbox = this.value;
			this.value = "";
			setTimeout(function() {
				findguestvip(findbox);
			},
			30);
		}
	});
	$('#baradd').keyup(function(e) {
		var key = e.which;
		if (key == 13) {
			var barcode = this.value;
			if (barcode != "") {
				this.value = "";
				var stg = Number($('#statetag').val());
				if (stg == 0) {
					setTimeout(function() {
						barcodeadd(barcode);
					},
					30);
				} else {
					alerttext("该单据状态无效！");
				}
			}
		}
	});
	$('body').delegate('.che-iszp', 'change',
	function() {
		var $this = $(this);
		var type = $this.attr('type');
		if (type == 'checkbox') {
			if (!$this.attr('diabled')) {
				var iszp = $this.prop('checked') == true ? "1": "0";
				var id = $this.data('mid');
				var index = $this.data('index');
				if (id != "") {
					changeshopsalemzp(index, id, iszp);
				}
			}
		}
	});
	$('#addware-table input').prop('readonly', true);
	$('#addware-table .help-s-btn').hide();
	$('#note-waremt').datagrid('loadData', {
		total: 0,
		rows: [],
		footer: [{
			"ROWNUMBER": "no",
			"WARENO": "合计",
			"AMOUNT": 0,
			"CURR": 0,
			"ACTION": "noaction"
		}]
	});
	
	sethistory();
	changeDivHeight();
	tglepayd();
	$("#uhousename").next().hide();
	$("#uhousename").prop("readonly",true);
	alerthide();
	$('body').focus();
});
function hasware() {
	var wareid = $('#wareid').val();
	var noteno = $('#noteno').html();
	if (noteno == '' || noteno == "&nbsp;") {
		alerttext('请先增加单据！');
		return false;
	} else if (wareid != undefined && wareid != "") return true;
	else {
		alerttext('请选择商品！');
		$('#warename').focus();
		return false;
	}
}
function hasnote() {
	var noteno = $('#noteno').html();
	if (noteno == '' || noteno == "&nbsp;") {
		alerttext('请先增加单据！');
		return false;
	} else return true;
}
var d = false;
function tglepayd() {
	if (d == false) {
		$('.yhmsg').hide();
		$('#tgpayd').html('▼');
		d = true;
	} else {
		d = false;
		$('.yhmsg').show();
		$('#tgpayd').html('▲');
	}
}
function addshopsaleh() {
	resetvipipt();
	$('#noteno,#cashman,#notedate,.note-text,.noteh-text,.curr-td').html('');
	$('#vtid,#guestid,#wareid,#colorid,#sizeid,#wareno,#colorname,#sizename,#guestfindbox,#uremark0').val('');
	$('#price0,#price,#curr').val('0.00');
	$('#discount').val('1.00');
	$('#paybtn,#zddiscbtn').hide();
	$('#add').show();
	$('#add, #print, #qtprint,#historybtn').hide();
	$('#cancelbtn,#guadanbtn').show();
	$('#paybj-div').hide();
	$('.yhmsg').hide();
	$('#tgpayd').html('▼');
	d = true;
	alertmsg(2);
	var houseid = getValuebyName("HOUSEID");
	var housename = getValuebyName("HOUSENAME");
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "addshopsaleh",
			houseid: houseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var levelid = Number(getValuebyName("GLEVELID"));
					if(levelid!=1&&levelid!=2&&levelid!=9){
						$("#uhousename").next().show();
						$("#uhousename").removeProp("readonly");
					}
					$("#uhouseid").val(houseid);
					$("#uhousename").val(housename);
					$('#id').val(data.ID);
					$('#noteno').html(data.NOTENO);
					$('#notedate').html(data.NOTEDATE);
					$('#cashman').html(getValuebyName('EPNAME'));
					$('#addware-table input:not(#colorname,#sizename,#price0,#curr)').removeAttr('readonly');
					$('#addware-table .help-s-btn').show();
					$('.vip-right,#addmxpanel').show();
					$('#noteno').removeClass('haspay');
					$('#statetag').val('0');
					$('#note-waremt').datagrid('loadData', {
						total: 0,
						rows: [],
						footer: [{
							"ROWNUMBER": "no",
							"WARENO": "合计",
							"AMOUNT": 0,
							"CURR": 0,
							"ACTION": "noaction"
						}]
					});
					changeDivHeight();
					if (xstype == 1) {
						$('#baradd').focus();
					} else {
						$('#epid,#saleman').val('');
						$('#saleman').focus();
					}
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//删除单据
function delshopsaleh() {
	var id = $('#id').val();
	var noteno = $('#noteno').html();
	$.messager.confirm(dialog_title, '是否放弃当前录入的单据？',
	function(r) {
		if (r) {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "delshopsalehbyid",
					noteno: noteno,
					id: id
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							$('#id').val("");
							$('#noteno,#cashman,#notedate,.note-text,.curr-td').html('');
							$('#vtid,#guestid').val('');
							$('#statetag').val('0');
							$('#cancelbtn,#paybtn,#zddiscbtn,#guadanbtn').hide();
							$('#add,#historybtn').show();
							$('#addware-table input').prop('readonly', true);
							$('#addware-table .help-s-btn').hide();
							$('#addmxpanel').hide();
							$('#note-waremt').datagrid('loadData', {
								total: 0,
								rows: [],
								footer: [{
									"ROWNUMBER": "no",
									"WARENO": "合计",
									"AMOUNT": 0,
									"CURR": 0,
									"ACTION": "noaction"
								}]
							});
							changeDivHeight();
						}
					} catch(err) {
						console.error(err.message);
					}
				}
			});
		}
	});
}
//挂单
function guadanshopsaleh() {
	$.messager.confirm(dialog_title, '您确认挂单么？挂单以后在历史查询中载入！',
	function(r) {
		if (r) {
			$('#id').val("");
			$('#noteno,#cashman,#notedate,.note-text,.curr-td').html('');
			$('#vtid,#guestid').val('');
			$('#statetag').val('0');
			$('#cancelbtn,#paybtn,#zddiscbtn,#guadanbtn').hide();
			$('#add,#historybtn').show();
			$('#addware-table input').prop('readonly', true);
			$('#addware-table .help-s-btn').hide();
			$('#addmxpanel').hide();
			$('#note-waremt').datagrid('loadData', {
				total: 0,
				rows: [],
				footer: [{
					"ROWNUMBER": "no",
					"WARENO": "合计",
					"AMOUNT": 0,
					"CURR": 0,
					"ACTION": "noaction"
				}]
			});
			changeDivHeight();
		}
	});
}
function delnote(index) {
	$('#gg').datagrid('selectRow', index);
	var row = $('#gg').datagrid('getSelected');
	if (row) {
		$.messager.confirm(dialog_title, '单据信息以及商品明细将全部清除，并且不可恢复，是否确认删除吗？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delshopsalehbyid",
						noteno: row.NOTENO,
						id: row.ID
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								var noteno = $('#noteno').html();
								if (noteno == row.NOTENO) {
									$('#id').val("");
									$('#noteno,#cashman,#notedate,.note-text,.curr-td').html('');
									$('#vtid,#guestid').val('');
									$('#statetag').val('0');
									$('#cancelbtn,#paybtn,#zddiscbtn,#guadanbtn').hide();
									$('#add,#historybtn').show();
									$('#addware-table input').prop('readonly', true);
									$('#addware-table .help-s-btn').hide();
									$('#note-waremt').datagrid('loadData', {
										total: 0,
										rows: [],
										footer: [{
											"ROWNUMBER": "no",
											"WARENO": "合计",
											"AMOUNT": 0,
											"CURR": 0,
											"ACTION": "noaction"
										}]
									});
								}
								var footer = $('#gg').datagrid('getFooterRows');
								$('#gg').datagrid('updateFooter', {
									TOTALAMT: Number(footer[0].TOTALAMT) - Number(row.TOTALAMT),
									TOTALCURR: Number(footer[0].TOTALCURR) - Number(row.TOTALCURR),
								});

								var ptotal = Number($('#pagetotal').html());
								var ztotal = Number($('#ztotal').html());
								$('#notetotal #pagetotal').html(ptotal - 1);
								$('#notetotal #ztotal').html(ztotal - 1);

								$('#gg').datagrid('deleteRow', index);
								$('#gg').datagrid('refresh');

							}
						} catch(err) {
							console.error(err.message);
						}
					}
				});
			}
		});
	} else {
		alerttext('请选择！');
	}
}
function cancelvip() {
	var guestid = Number($('#guestid').val());
	if (guestid > 0) {
		$.messager.confirm(dialog_title, '确认取消会员优惠！',
		function(r) {
			if (r) {
				$('.vip-detail .note-text').html('');
				$('#guestid').val('');
				$('#guestfindbox').val('');
				$('#discount').val('1');
				changeguest();
				changediscount();
			}
		});
	} else {
		alerttext('未选择任何会员！');
	}
}
function barcodeadd(barcode) {
	var amount = $('#amount').val();
	$('#amount').val("1");
	var noteno = $('#noteno').html();
	var guestid = $('#guestid').val();
	var discount = $('#discount').val();
	var stg = Number($('#statetag').val());
	if (stg != 0) {
		alerttext("该单据状态无效！");
		return;
	}
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else {
		var salemanid = $('#epid').val();
		var houseid = Number($('#uhouseid').val());
		var salemanno = $('#saleman').val();
		if (salemanid == "" && salemanno == "") {
			alerttext('请选择销售人！');
			$('#saleman').focus();
		} else {
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "shopsalebarcode",
					noteno: noteno,
					amount: amount,
					discount: discount,
					houseid: houseid,
					barcode: barcode.replace(/ /g,""),
					salemanid: Number(salemanid),
					salemanno: salemanno,
					guestid: Number(guestid)
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							var result = data.result;
							if (result == "1") {
								//var msgs = msg.split('^');
								var rows = data.rows;
								//var id = msg[0];
								rows['ISZP'] = "0";
								$('#note-waremt').datagrid('appendRow', rows);
								var dqzcurr1 = Number(data.TOTALCURR);
								var dqzamt1 = Number(data.TOTALAMT);
								$('#note-waremt').datagrid('updateFooter', {
									AMOUNT: data.TOTALAMT,
									CURR: data.TOTALCURR
								});
								$('#yshj').html(dqzcurr1.toFixed(2));
								$('#sshj').html(dqzcurr1.toFixed(2));
								$('#zl').html("0.00");
								$('#yhje').html("0.00");
								$('#hkhj').html("0.00");
								$('#czje').html("0.00");
								$('#jfdk').html("0.00");
								$('#yhq').html("0.00");
								if ($('#note-waremt').datagrid('getRows').length > 0) {
									$('#paybtn,#zddiscbtn').show();
									$('#note-waremt').datagrid('scrollTo', $('#note-waremt').datagrid('getRows').length - 1);
									$('#note-waremt').datagrid('selectRow', $('#note-waremt').datagrid('getRows').length - 1);
								} else $('#paybtn,#zddiscbtn').hide();
							}
							if (xstype == 1) {
								$('#baradd').focus();
							} else {
								$('#epid,#saleman').val('');
								$('#saleman').focus();
							}
						}
					} catch(err) {
						console.error(err.message);
					}
				}
			});
		}
	}
}
function selware() {
	var salemanid = $('#epid').val();
	var salemanno = $('#saleman').val();
	if (salemanid == "" && salemanno == "") {
		alerttext('请选择销售人！');
		$('#saleman').focus();
	} else selectware('shopsale');

}
function selcolor(ser) {
	if (hasware()) {
		selectwarecolor($('#wareid').val(), ser);
	}
}
function selsize(ser) {
	if (hasware()) {
		selectsize($('#wareid').val(), ser);
	}
}
function addshopsalem() {
	alertmsg(2);
	var noteno = $('#noteno').html();
	var wareid = $('#wareid').val();
	var salemanid = $('#epid').val();
	var salemanno = $('#saleman').val();
	var colorid = $('#colorid').val();
	var sizeid = $('#sizeid').val();
	var price0 = $('#price0').val();
	var price = $('#price').val();
	var discount = $('#discount').val();
	var curr = $('#curr').val();
	var amount = Number($('#amount').val());
	if(amount==0){
		alerttext("请输入商品数量！");
		return;
	}
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else if (wareid == "") alerttext('请选择商品！');
	else if (colorid == "") alerttext('请选择商品颜色！');
	else if (sizeid == "") alerttext('请选择商品尺码！');
	else if (salemanid == "" && salemanno == "") {
		alerttext('请选择销售人！');
		$('#saleman').focus();
	} else {
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "addshopsalem",
				noteno: noteno,
				wareid: wareid,
				salemanid: Number(salemanid),
				salemanno: salemanno.toUpperCase(),
				colorid: colorid,
				sizeid: sizeid,
				price0: price0,
				price: price,
				discount: discount,
				curr: curr,
				amount: amount
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						var result = data.result;
						if (result == "1") {
							//var msgs = msg.split('^');
							var rows = data.rows;
							//var id = msg[0];
							rows['ISZP'] = "0";
							$('#note-waremt').datagrid('appendRow', rows);
							var dqzcurr1 = Number(data.TOTALCURR);
							var dqzamt1 = Number(data.TOTALAMT);
							$('#note-waremt').datagrid('updateFooter', {
								AMOUNT: data.TOTALAMT,
								CURR: data.TOTALCURR
							});
							$('#yshj').html(dqzcurr1.toFixed(2));
							$('#sshj').html(dqzcurr1.toFixed(2));
							$('#zl').html("0.00");
							$('#yhje').html("0.00");
							$('#czje').html("0.00");
							$('#jfdk').html("0.00");
							$('#yhq').html("0.00");
							if ($('#note-waremt').datagrid('getRows').length > 0) {
								$('#paybtn,#zddiscbtn').show();
								$('#note-waremt').datagrid('scrollTo', $('#note-waremt').datagrid('getRows').length - 1);
								$('#note-waremt').datagrid('selectRow', $('#note-waremt').datagrid('getRows').length - 1);
							} else $('#paybtn,#zddiscbtn').hide();
						}
						$('#wareno,#wared,#colorid,#sizeid,#colorname,#sizename').val('');
						$('#price0,#price,#curr').val('0.00');
						$('#amount').val('1');
						$('#wareno').focus();
						//getshopwarem('1');
					}
				} catch(err) {
					console.error(err.message);
				}
			}
		});

	}
}
function updateshopsalem() {
	alertmsg(2);
	var index = dqwindex;
	var noteno = $('#noteno').html();
	var id = $('#uid').val();
	var salemanid = $('#uepid').val();
	var salemanno = $('#uepname').val();
	var wareid = $('#uwareid').val();
	var colorid = $('#ucolorid').val();
	var sizeid = $('#usizeid').val();
	var price0 = $('#uprice0').val();
	var price = $('#uprice').val();
	var discount = $('#udiscount').val();
	var curr = $('#ucurr').val();
	var amount = $('#uamount').val();
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else if (wareid == "") alerttext('请选择商品！');
	else if (colorid == "") alerttext('请选择商品颜色！');
	else if (sizeid == "") alerttext('请选择商品尺码！');
	else if (salemanid == "" && salemanno == "") {
		alerttext('请选择销售人！');
		$('#saleman').focus();
	} else {
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateshopsalembyid",
				noteno: noteno,
				id: id,
				salemanid: salemanid,
				wareid: wareid,
				colorid: colorid,
				sizeid: sizeid,
				price0: price0,
				price: price,
				discount: discount,
				curr: curr,
				amount: amount
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						var result = data.result;
						if (result == "1") {
							$('#note-waremt').datagrid('updateRow', {
								index: index,
								row: data.rows
							});
							var dqzcurr1 = Number(data.TOTALCURR);
							var dqzamt1 = Number(data.TOTALAMT);
							$('#note-waremt').datagrid('updateFooter', {
								AMOUNT: data.TOTALAMT,
								CURR: data.TOTALCURR
							});
							$('#yshj').html(dqzcurr1.toFixed(2));
							$('#sshj').html(dqzcurr1.toFixed(2));
							$('#zl').html("0.00");
							$('#yhje').html("0.00");
							$('#czje').html("0.00");
							$('#jfdk').html("0.00");
							$('#yhq').html("0.00");
						}
						$('#uwaremd').dialog('close');
						//getshopwarem('1');
					}
				} catch(err) {
					console.error(err.message);
				}
			}
		});

	}
}
function delwarem(index) {
	var noteno = $('#noteno').html();
	$('#note-waremt').datagrid('selectRow', index);
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else {
		var rowData = $('#note-waremt').datagrid('getSelected');
		$.messager.confirm(dialog_title, '您确认要删除' + rowData.WARENO + '，' + rowData.SIZENAME + '，' + rowData.COLORNAME + '吗？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delshopsalembyid",
						noteno: noteno,
						id: rowData.ID
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$('#note-waremt').datagrid('deleteRow', index);
								var dqzcurr1 = Number(data.TOTALCURR);
								var dqzamt1 = Number(data.TOTALAMT);
								$('#note-waremt').datagrid('updateFooter', {
									AMOUNT: data.TOTALAMT,
									CURR: data.TOTALCURR
								});
								$('#yshj').html(dqzcurr1.toFixed(2));
								$('#sshj').html(dqzcurr1.toFixed(2));
								$('#zl').html("0.00");
								$('#yhje').html("0.00");
								$('#czje').html("0.00");
								$('#jfdk').html("0.00");
								$('#yhq').html("0.00");
								$('#note-waremt').datagrid('refresh');
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
function changesort(sort){
	if(sortid==sort) return;
	sortid = sort;
	$(".menu-icon.icon-ok").removeClass("icon-ok");
	$('#shopsalemmenu').menu('setIcon', {
		target: $('#btn_sort'+sort)[0],
		iconCls: 'icon-ok'
	});
	getshopwarem(1);
}
var sortid = 1;//0=商品，1=录入
function getshopwarem(page) {
	alertmsg(6);
	var noteno = $('#noteno').html();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "shopsalemlist",
			noteno: noteno,
			sortid: sortid,
			fieldlist: "A.ID,A.NOTENO,A.ACCID,A.WAREID,A.COLORID,A.SIZEID,A.HOUSEID,A.AMOUNT,A.PRICE0,A.DISCOUNT,A.PRICE,A.CURR,a.iszp," + "A.REMARK0,B.WARENO,A.SALEMANID,B.WARENAME," + "B.UNITS,C.COLORNAME,D.SIZENAME,E.HOUSENAME,F.EPNAME",
			rows: pagecount,
			page: page
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var total = data.total;
					var stg = $('#statetag').val();
					data.footer =[{
						WARENO: "合计",
						AMOUNT: data.totalamt,
						CURR: data.totalcurr
					}];
					$('#note-waremt').datagrid('loadData', data);
					if ($('#note-waremt').datagrid('getRows').length > 0 && stg != '1') $('#paybtn,#zddiscbtn').show();
					else $('#paybtn,#zddiscbtn').hide();
					var dqzamt1 = Number(data.totalamt);
					var dqzcurr1 = Number(data.totalcurr);
					$('#yshj').html(Number(data.totalcurr).toFixed(2));
					$('#sshj').html(Number(data.totalcurr).toFixed(2));
					// 				$('#zl').html("0.00");
					// 				$('#yhje').html("0.00");
					// 				$('#czje').html("0.00");
					// 				$('#jfdk').html("0.00");
					// 				$('#yhq').html("0.00");
				}
			} catch(err) {
				console.error(err.message);
			}
			if (xstype == 1) {
				$('#baradd').focus();
			} else {
				$('#epid,#saleman').val('');
				$('#saleman').focus();
			}
		}
	});
}

//选择店铺
function changehouse() {
	var houseid = Number($('#uhouseid').val());
	var noteno = $('#noteno').html();
	if(houseid<=0){
		alerttext("请选择有效店铺！");
		return;
	}
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "updateshopsalehbyid",
			noteno: noteno,
			houseid: houseid
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				
			}
		}
	})
}
//选择会员
function changeguest() {
	var guestid = $('#guestid').val();
	var id = $('#id').val();
	var noteno = $('#noteno').html();
	alertmsg(2);
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else {
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async: false,
			data: {
				erpser: "updateshopsalehbyid",
				id: id,
				noteno: noteno,
				guestid: Number(guestid)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
				}
			}
		});
	}
}
function openremarkd(){
	if($("#paybj-div").is(":hidden")){
		$("#uremark").removeProp("readonly");
		$("#remark_btn").show();
		$("#remarkd").dialog("setTitle","输入摘要");
	}else{
		$("#uremark").prop("readonly",true);
		$("#remarkd").dialog("setTitle","浏览摘要");
		$("#remark_btn").hide();
	}
	$("#remarkd").dialog("open");
	$("#uremark").focus();
}
//修改备注
function changeremark(){
	if(!$("#paybj-div").is(":hidden")) return;
	var remark = $('#uremark').val();
	var noteno = $('#noteno').html();
	alertmsg(2);
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else {
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "updateshopsalehbyid",
				noteno: noteno,
				remark: remark
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				if (valideData(data)) {
					$("#remarkd").dialog("close");
				}
			}
		});
	}
}
//折扣
function changediscount() {
	var discount = Number($('#discount').val());
	var guestid = $('#guestid').val();
	$('#vipdisc').val(discount);
	var noteno = $('#noteno').html();
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else {
		if (isNaN(discount) || discount < 0) {
			alerttext('请输入折扣');
		} else {
			alertmsg(2);
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名
				async: false,
				data: {
					erpser: "changeshopsalemdisc",
					noteno: noteno,
					guestid: Number(guestid),
					discount: discount
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						$('#wareno,#wared,#colorid,#sizeid,#colorname,#sizename').val('');
						$('#price0,#price,#curr').val('0.00');
						$('#amount').val('1');
						getshopwarem('1');
					}
				}
			});
			$('#discd').dialog('close');
		}
	}
}
function alldisc() {
	var alldisc = $('#alldiscount').val();
	if (alldisc != "") {
		$('#discount').val(alldisc);
		changediscount();
	} else {
		alerttext('请输入折扣！');
		$('#alldiscount').focus();
	}
}
//打开收款窗口
function openpay() {
	$('#discpay').val('');
	$('#yhqpay').val('');
	$('#usecent').val('');
	$('#usecurr').val('');
	$('#sumpay').val('');
	$('#changecurr').val('');
	$('#discpay').removeAttr('readonly');
	var houseid = Number($("#uhouseid").val());
	if(houseid<=0||isNaN(houseid)){
		alerttext("请选择店铺！");
		return;
	}
	var amt = $('#note-waremt').datagrid('getRows').length;
	if (amt == 0) {
		alert('商品明细不能为空！');
	} else {
		$('#payd').dialog("setTitle", '结账');
		var thispay = Number($('#yshj').html());
		$('#thispay').val(thispay);
		var checkpay = 0;
		if (lsmlfs == 0 || points == 0) checkpay = Math.floor(thispay);
		else if (lsmlfs == 1) checkpay = Math.round(thispay);
		else if (lsmlfs == 2) checkpay = thispay;
		$('#checkpay').val(checkpay.toFixed(2));
		$('#discpay').val((thispay - checkpay).toFixed(2));
		var guestid = Number($('#guestid').val());
		if (guestid>0) {
			var balcent = $('#balcent').html() == '0' || $('#balcent').html() == '' ? 0 : Number($('#balcent').html());
			balcent == 0||centbl==0 ? $('#isusecent').hide() : $('#isusecent').show();
			$('#usecent').attr("placeholder", "<可用积分：" + balcent + ">");
			$('#isusecent,#isuseyhq').show();
			$('#newask').val(1);
		} else {
			$('#isusecent,#isuseyhq').hide();
		}
		$('#yhjcurr .yhq').remove();
		$('#yhjcurr').val(0);
		$('#sendvipmsg').hide();
		$('#btn_pay,#btn_pay_print').show();
		$('#usecent,#jfcurr,.payvcurr').removeProp("readonly");
		$('#yhjcurr').removeProp('disabled');
		$('#freepay').removeAttr('readonly');
		$('#payd').dialog('open');
		listvipcoupon();
		getpaywaylist(false);
	}
}
//生成支付列表行
var payline;
var isv = false;
function getpaywaylist(disabled) {
	$('.paytr').detach();
	var noteno = $('#noteno').html();
	alertmsg(6);
	var online_pay = {
		wx: 0,
		zfb: 0,
		qtzf: 0
	};
    var _guestId = $('#guestid').val();
    var enableCard = false;
    if (_guestId && parseInt(_guestId) > 0) {
		enableCard = true;
	}
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "paywaylist",
			rows: 50,
			sort: "payno",
			order: "asc",
			nov: enableCard ? 0 : 1,
			sybj: 1
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			try {
				if (valideData(data)) {
					var total = data.total;
					payline = Number(total);
					var balcurr = $('#balcurr').html() == '0' || $('#balcurr').html() == '' ? 0 : $('#balcurr').html();
					var rows = data.rows;
					var i = 1;
					var newtr = true;
					var kjfshtml = "快捷方式：";
					for (var row in rows) {
						if (newtr) var tr = document.createElement("tr");
						tr.className = "paytr";
						if (rows[row].PAYNO == 'V') {
							if (balcurr >= 0) {
								tr.className = "paytr";
								var td1 = document.createElement("td");
								var td2 = document.createElement("td");
								td1.align = "right";
								td1.className = "header";
								td2.align = "right";
								var label = document.createElement("label");
								var inputt = document.createElement("input");
								var inputh = document.createElement("input");
								var inputh1 = document.createElement("input");
								inputt.type = "text";
								inputh.type = "hidden";
								inputh1.type = "hidden";
								label.innerHTML = rows[row].PAYNAME;
								label.style.cursor = "pointer";
								label.onclick = function() {
									$('.paycurr:not([readonly])').val("");
									var pi = this.id.replace("payname", "");
									var actpay = Number($('#checkpay').val());
									var payedcurr = 0;
									for (var i = 0; i < $('.paycurr[readonly]').length; i++) {
										payedcurr += Number($('.paycurr[readonly]').eq(i).val());
									}
									if (actpay - payedcurr > Number($('#balcurr').html())) {
										$('#pay' + pi).val($('#balcurr').html());
									} else $('#pay' + pi).val((actpay - payedcurr).toFixed(2));
									sumpay();
									$('#pay' + pi).select().focus();
								};
								label.id = "payname" + i;
								inputt.placeholder = "可用余额:" + $('#balcurr').html() + "元";
								inputt.id = "pay" + i;
								inputt.className = "hig25 wid160 payvcurr paycurr";
								$(inputt).change(function() {
									var checkcurr = Number($('#checkpay').val());
									checkcurr = isNaN(checkcurr) ? 0 : checkcurr
									if (checkcurr<0&&checkcurr>Number(this.value)) {
										this.value = Number(checkcurr).toFixed(2);
										alerttext("退款金额不能大于应退金额");
									} else this.value = Number(this.value).toFixed(2);
									sumpay();
								});
								inputt.onkeyup = function() {
									if (this.value != "-") {
										this.value = this.value.replace(/[^-?\d.*$]/g, '');
										sumpay();
									}
								};
								inputt.onafterpaste = function() {
									if (this.value != "-") {
										this.value = this.value.replace(/[^-?\d.*$]/g, '');
										this.value = Number(this.value) > Number($('#balcurr').html()) ? $('#balcurr').html() : Number(this.value);
										sumpay();
									}
								};
								inputt.disabled = disabled;
								inputh.id = "payid" + i;
								inputh1.id = "payno" + i;
								inputh1.value = rows[row].PAYNO;
								inputh.value = rows[row].PAYID;
								td1.appendChild(label);
								//								td2.appendChild(inputt1);
								//								td2.appendChild(inputb);
								td2.appendChild(inputh);
								td2.appendChild(inputh1);
								td2.appendChild(inputt);
								tr.appendChild(td1);
								tr.appendChild(td2);
								i = i + 1;
								if (newtr) newtr = false;
								else {
									$('#payway').before(tr);
									newtr = true;
								}
							} else {
								isv = false;
								payline = payline - 1;
							}
						} else if (rows[row].PAYNO == 'S' ||rows[row].PAYNO == 'R' || rows[row].PAYNO == 'W' || rows[row].PAYNO == 'Z') {
							var td1 = document.createElement("td");
							var td2 = document.createElement("td");
							td1.align = "right";
							td1.className = "header";
							td2.align = "right";
							var label = document.createElement("label");
							var inputt = document.createElement("input");
							var inputh = document.createElement("input");
							var inputh1 = document.createElement("input");
							var inputb = document.createElement("input");
							inputt.type = "text";
							inputh.type = "hidden";
							inputh1.type = "hidden";
							inputb.type = "button";
							var payfs = 0;
							$(inputb).data('currid', "#pay" + i);
							if (rows[row].PAYNO == 'R'||rows[row].PAYNO == 'S') {
								qtzfpayname = rows[row].PAYNAME;
								qtzfpayno = rows[row].PAYNO;
								if(rows[row].PAYNO == 'R') qtpayfs=2;
								else if(rows[row].PAYNO == 'S') qtpayfs=3;
								$(inputb).data('payfs',qtpayfs);
								inputb.className = "paybtn payfs"+qtpayfs;
								inputt.className = "paycurr payqtzf payfs"+qtpayfs;
								$(inputt).data('payname', qtzfpayname);
								$(inputt).data('sucstg', 0);
								online_pay.qtzf = 1;
// 								kjfshtml += "L-拉卡拉;";
							} else if (rows[row].PAYNO == 'W') {
								$(inputb).data('payfs', 1);
								inputb.className = "paybtn payfs"+qtpayfs;
								inputt.className = "paycurr payw payfs1";
								online_pay.wx = 1;
								kjfshtml += "W-微信;";
							} else if (rows[row].PAYNO == 'Z') {
								$(inputb).data('payfs', 0);
								inputb.className = "paybtn payfs"+qtpayfs;
								inputt.className = "paycurr payz payfs0";
								online_pay.zfb = 1;
								kjfshtml += "Z-支付宝;";
							}
// 							$('#zfkjfs').html(kjfshtml);
// 							$('#zfkjfs').show();
							inputb.onclick = function() {
								var payfs = $(this).data('payfs');
								openskypay(payfs);
							};
							label.innerHTML = rows[row].PAYNAME;
							label.style.cursor = "pointer";
							label.onclick = function() {
								if ($('#pay' + pi + '[readonly]').length == 0) {
									$('.paycurr:not([readonly])').val("");
									var pi = this.id.replace("payname", "");
									var actpay = Number($('#checkpay').val());
									var payedcurr = 0;
									for (var i = 0; i < $('.paycurr[readonly]').length; i++) {
										payedcurr += Number($('.paycurr[readonly]').eq(i).val());
									}
									$('#pay' + pi).val((actpay - payedcurr).toFixed(2));
									sumpay();
									$('#pay' + pi).select().focus();
								}
							};
							label.id = "payname" + i;
							inputt.placeholder = "<输入>";
							inputt.id = "pay" + i;
							inputt.style.width = "100px";
							inputt.style.height = "25px";
							inputb.className = "btn2";
							inputb.style.width = "50px";
							inputb.style.padding = "2px";
							var actpay = Number($('#checkpay').val());
							if (actpay >= 0) inputb.value = "支付";
							else if (actpay < 0) inputb.value = "退款";
							$(inputt).change(function() {
								this.value = Number(this.value).toFixed(2);
								sumpay();
							});
							inputt.onkeyup = function(e) {
								if (this.value != "-") {
									this.value = this.value.replace(/[^-?\d.*$]/g, '');
								}
								sumpay();
							}
							inputt.onafterpaste = function() {
								if (this.value != "-") {
									this.value = this.value.replace(/[^-?\d.*$]/g, '');
								}
							};
							inputt.disabled = disabled;
							inputh.id = "payid" + i;
							inputh1.id = "payno" + i;
							inputh1.value = rows[row].PAYNO;
							inputh.value = rows[row].PAYID;
							td1.appendChild(label);
							td2.appendChild(inputt);
							td2.appendChild(inputb);
							td2.appendChild(inputh);
							td2.appendChild(inputh1);
							tr.appendChild(td1);
							tr.appendChild(td2);
							i = i + 1;
							if (newtr) newtr = false;
							else {
								$('#payway').before(tr);
								newtr = true;
							}
						} else {
							var td1 = document.createElement("td");
							var td2 = document.createElement("td");
							td1.align = "right";
							td2.align = "right";
							td1.className = "header";
							var label = document.createElement("label");
							var inputh1 = document.createElement("input");
							var inputt = document.createElement("input");
							var inputh = document.createElement("input");
							inputt.type = "text";
							inputt.className = "wid160 hig25 paycurr";
							inputh.type = "hidden";
							inputh1.type = "hidden";
							label.innerHTML = rows[row].PAYNAME;
							label.style.cursor = "pointer";
							label.onclick = function() {
								$('.paycurr:not([readonly])').val("");
								var pi = this.id.replace("payname", "");
								var actpay = Number($('#checkpay').val());
								var payedcurr = 0;
								for (var i = 0; i < $('.paycurr[readonly]').length; i++) {
									payedcurr += Number($('.paycurr[readonly]').eq(i).val());
								}
								$('#pay' + pi).val((actpay - payedcurr).toFixed(2));
								sumpay();
								$('#pay' + pi).select().focus();
							};
							label.id = "payname" + i;
							inputt.id = "pay" + i;
							inputt.placeholder = "<输入>";
							$(inputt).change(function() {
								this.value = Number(this.value).toFixed(2);
								sumpay();
							});
							inputt.onkeyup = function(e) {
								if (this.value != "-") {
									this.value = this.value.replace(/[^-?\d.*$]/g, '');
								}
								sumpay();
								if (e.which == 13) {
									$(this).blur();
									var summoney = Number($('#sumpay').val());
									var checkmoney = Number($('#checkpay').val());
									if (summoney >= checkmoney) {
										updateshopsaleh();
									}
								}
							};
							inputt.onafterpaste = function() {
								if (this.value != "-") {
									this.value = this.value.replace(/[^-?\d.*$]/g, '');
								}
								sumpay();
							};
							inputt.disabled = disabled;
							inputh.id = "payid" + i;
							inputh1.id = "payno" + i;
							inputh1.value = rows[row].PAYNO;
							inputh.value = rows[row].PAYID;
							td1.appendChild(label);
							td2.appendChild(inputt);
							td2.appendChild(inputh);
							td2.appendChild(inputh1);
							tr.appendChild(td1);
							tr.appendChild(td2);
							i++;
							if (newtr) newtr = false;
							else {
								$('#payway').before(tr);
								newtr = true;
							}
						}
					}
					if (newtr == false) $('#payway').before(tr);
					skypayedbj = {
						zfbpay: 0,
						paynoteno_wx: "",
						wxpay: 0,
						paynoteno_zfb: "",
						qtzfpay: 0,
						paynoteno_qtzf: ""
					}; //重置支付标记
					if (online_pay.zfb == 1) {
						checkskypayinfo(0);
					}
					if (online_pay.qtzf == 1) {
						checkskypayinfo(2);
					}
					/*if(online_pay.wx==1)
				checkskypayinfo(1);*/
					// 				sumpay();
					$('input[type=text]').attr("autocomplete", "off");
					setTimeout(function() {
						$('#payname1').click();
					},
					300);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});

}
//求付款合计
var paydata;
var paylist = "";
function sumpay() {
	var pastpay = Number($('#pastpay').val());
	var thispay = Number($('#thispay').val());
	var discpay = Number($('#discpay').val());
	var usecent = Number($('#usecent').val());
	var usecurr = Number($('#usecurr').val());
	var yhjcurr = Number($('#yhjcurr').val());
	pastpay = isNaN(pastpay) ? 0 : pastpay;
	thispay = isNaN(thispay) ? 0 : thispay;
	discpay = isNaN(discpay) ? 0 : discpay;
	usecent = isNaN(usecent) ? 0 : usecent;
	usecurr = isNaN(usecurr) ? 0 : usecurr;
	yhjcurr = isNaN(yhjcurr) ? 0 : yhjcurr;
	var usecurr = 0;
	if (centbl > 0)usecurr = usecent / centbl;
	$('#jfcurr').val(usecurr.toFixed(2));
	var json = [];
	var sum = 0;
	var count = 0;
	paylist = "";
	for (var i = 1; i <= payline; i++) {
		if ($('#pay' + i).length > 0) {
			var pay = Number($('#pay' + i).val() == '' ? '0': $('#pay' + i).val());
			sum = sum + pay;
			var payid = $('#payid' + i).val();
			if (pay != 0) {
				paylist += $('#payname' + i).html() + ":" + pay + ",";
				var payno = $('#payno' + i).val();
				json[count] = {
					"payid": payid,
					"payno": payno,
					"paycurr": pay
				};
				count = count + 1;
			}
		}
	}
	paydata = json;
	$('#sumpay').val(sum.toFixed(2));
	var checkcurr = thispay - discpay - usecurr - yhjcurr;
	if ((thispay-usecurr-discpay)<=yhjcurr&&yhjcurr>0) checkcurr = 0;
	if (usecurr > 0) paylist += '积分抵扣:' + usecurr + ",";
	if (yhjcurr > 0) paylist += '优惠券:' + yhjcurr + ",";
	var zl = sum - checkcurr;
	$('#checkpay').val(checkcurr.toFixed(2));
	$('#changecurr').val(zl.toFixed(2));
	if (Number(zl) > 0) {
		paylist += '找零:';
		paylist += zl;
	}
	checksendmsg();
}
function updateshopsaleh() {
	var id = $('#id').val();
	var noteno = $('#noteno').html();
	var isprint = $('#isprint').prop('checked');
	var iscontinue = $('#iscontinue').prop('checked');
	if (noteno == "" || noteno == "&nbsp;") {
		alerttext('请添加新的单据！');
	} else {
		var guestid = $('#guestid').val();
		var remark = $('#uremark0').val();
		var totalcurr = Number($('#thispay').val());
		var jfcurr = Number($('#jfcurr').val());
		var changecurr = Number($('#changecurr').val());
		if(changecurr>=100){
			alerttext('找零金额不允许大于100！');
			return;
		}
		var usecent = Number($('#usecent').val());
		var freecurr = Number($('#discpay').val());
		var checkcurr = Number($('#checkpay').val());
		var zpaycurr = Number($('#sumpay').val());
		totalcurr = isNaN(totalcurr) ? 0 : totalcurr;
		jfcurr = isNaN(jfcurr) ? 0 : jfcurr;
		changecurr = isNaN(changecurr) ? 0 : changecurr;
		usecent = isNaN(usecent) ? 0 : usecent;
		checkcurr = isNaN(checkcurr) ? 0 : checkcurr;
		var yhjcurr = Number($('#yhjcurr').val());
		if (totalcurr - freecurr - jfcurr < yhjcurr && checkcurr == 0) yhjcurr = totalcurr - freecurr - jfcurr;
		var vcpid = Number($('#yhjcurr :selected').data("vcpid"));
		//	var checkcurr = totalcurr - jfcurr - freecurr;
		if (Number($('.payw').val()) > 0) {
			if (skypayedbj.wxpay == 0) {
				alerttext('微信支付未完成，请重新支付！');
				return;
			}
		}
		if (Number($('.payz').val()) > 0) {
			if (skypayedbj.zfbpay == 0) {
				alerttext('支付宝支付未完成，请重新支付！');
				return;
			}
		}
		var errorpaymsg = "";
		$(".payqtzf").each(function(){
			var $this = $(this);
			var payname = $this.data("payname");
			var sucstg = Number($this.data("sucstg"));
			var curr = Number($this.val());
			if(curr>0){
				if(sucstg==0){
					errorpaymsg += payname+" ";
				}
			}
		});
		if (errorpaymsg.length > 0) {
			alerttext(errorpaymsg + '支付未完成，请重新支付！');
			return;
		}
		if (zpaycurr<checkcurr&&checkcurr>= 0) {
			alerttext('收款合计小于应结金额，不允许提交');
			return;
		} else if (checkcurr<0&&zpaycurr>checkcurr) {
			alerttext('退款合计大于实退金额，不允许提交');
			return;
		} else {
			alertmsg(2);
			var notedatestr = $("#notedate").html();
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "updateshopsalehbyid",
					noteno: noteno,
					id: id,
					guestid: Number(guestid),
					totalcurr: totalcurr,
					freecurr: freecurr,
					checkcurr: checkcurr,
					zpaycurr: zpaycurr,
					changecurr: changecurr,
					usecent: usecent,
					jfcurr: jfcurr,
					yhjcurr: yhjcurr,
					vcpid: vcpid,
					remark: remark,
					notedatestr: notedatestr,
					statetag: 1,
					houseid: Number($("#uhouseid").val()),
					paynoteno_wx: skypayedbj.paynoteno_wx,
					paynoteno_qtzf: skypayedbj.paynoteno_qtzf,
					paynoteno_zfb: skypayedbj.paynoteno_zfb,
					paylist: JSON.stringify(paydata)
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					if (valideData(data)) {
						try {
							var cent = Number(data.msg);
							var balcent = Number($('#balcent').html());
							cent = isNaN(cent) ? 0 : cent;
							balcent = isNaN(balcent) ? 0 : balcent;
							if (guestid != "") {
								balcent = balcent - usecent + cent;
								if (cent != 0) $('#cent').html(cent);
								$('#balcent').html(balcent);
							}
							if (isprint) {
								$('#print').click();
							}
// 							if ($('#isqtprint').prop("checked")) { //如果前台打印勾选，
// 								//	qtprint(true);
// 							}
							$('#sshj').html(Number(checkcurr).toFixed(2));
							$('#hkhj').html(Number(zpaycurr).toFixed(2));
							$('#zl').html(Number(changecurr).toFixed(2));
							if (changecurr > 0) {
								$('#thischangecurr').html(Number(changecurr).toFixed(2));
								$('#changecurrd').dialog('open');
							}
							$('#yhje').html(Number(freecurr).toFixed(2));
							$('#czje').html(Number(0).toFixed(2));
							$('#jfdk').html(Number(jfcurr).toFixed(2));
							$('#yhq').html(yhjcurr.toFixed(2));
							$('#payd').dialog('close');
							$('#paybj-div').show();
							$('#statetag').val('1');
							$('#note-waremt').datagrid('refresh');
							$('#noteno').addClass('haspay');
							$('.vip-right').hide();
							d = false;
							$('.yhmsg').show();
							$('#tgpayd').html('▲');
							$('#addmxpanel').hide();
							$('#uhousename').next().hide();
							$("#uremark").val(remark);
							$('#addware-table input,#uhousename').prop('readonly', true);
							$('#cancelbtn,#paybtn,#zddiscbtn,#guadanbtn').hide();
							$('#add,#print, #qtprint,#historybtn').show();
							$('#addware-table .help-s-btn').hide();
						} catch(err) {
							console.error(err.message);
						} finally {
							if (skypayedbj.paynoteno_zfb.length > 0 || skypayedbj.paynoteno_wx.length > 0 || skypayedbj.paynoteno_qtzf.length > 0) {
								$.messager.alert("在线支付提示", "在线支付成功！", "info",
								function() {
									if (iscontinue) {
										addshopsaleh();
									}
								});
							} else {
								if (iscontinue) {
									addshopsaleh();
								}
							}
						}

						changeDivHeight();

					}
				}
			});
		}
	}
}
function withdraw(index) {
	$('#gg').datagrid('selectRow', index);
	if (Number(allowchedan) == 1) {
		var row = $('#gg').datagrid('getSelected');
		if (row) {
			$.messager.confirm(dialog_title, '您确定撤单吗？',
			function(r) {
				if (r) {
					alertmsg(2);
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: "shopsalehcancel",
							noteno: row.NOTENO,
							id: row.ID
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									var noteno = $('#noteno').html();
									var rows = $('#gg').datagrid('getRows');
									var row = rows[index];
									if (row.OPERANT == getValuebyName("EPNAME")) {
										$('#gg').datagrid('updateRow', {
											index: index,
											row: {
												STATETAG: '0',
												PAYLIST: ""
											}
										});
									} else {
										$('#gg').datagrid('deleteRow', index);
									}
									$('#gg').datagrid('refresh');
									if (noteno == row.NOTENO) {
										$('#gg').datagrid('selectRow',index);
										loadshopsale();
									}
								}
							} catch(err) {
								console.error(err.message);
							}

						}
					});
				}
			});
		} else {
			alerttext('请选择！');
		}
	} else {
		alerttext('您没有权限撤单！');
	}

}
function sethistory() {
	var levelid = Number(getValuebyName("GLEVELID"));
	if(levelid==1||levelid==2||levelid==7||levelid==9){
		if(levelid==1||levelid==2||levelid==9){
			$('#shousename').next().hide();
			$('#shousename').prop('readonly',true);
		}
		$('#shouseid').val(Number(getValuebyName('HOUSEID')));
		$('#shousename').val(getValuebyName('HOUSENAME'));
	}
	var myDate = new Date(top.getservertime());
	$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
	$('#smindate,#smaxdate').datebox({
		onSelect: function(date) {
			getnotedata();
		}
	});
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
		height: $('body').height() - 90,
		fitColumns: true,
		nowrap: false,
		showFooter: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		onDblClickRow: function(index, row) {
			loadshopsale();
		},
		columns: [[{
			field: 'ID',
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
				return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
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
				if (row.STATETAG == '1') {
					return 'color:#00959a;font-weight:900';
				} else if (row.STATETAG == '3') {
					return 'color:#ff3b30;font-weight:900';
				} else {
					return 'color:#ff7900;font-weight:700';
				}
			}
		},{
			field: 'HOUSENAME',
			title: '店铺',
			width: 110,
			fixed: true,
			align: 'right',
			halign: 'center'
		},
		{
			field: 'TOTALCURR',
			title: '总金额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'TOTALAMT',
			title: '总数量',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: function(value, row, index) {
				return Number(value);
			}
		},
		{
			field: 'GUESTNAME',
			title: '会员',
			width: 80,
			align: 'center',
			halign: 'center',
			fixed: true
		},
		{
			field: 'FREECURR',
			title: '免单金额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'JFCURR',
			title: '积分抵扣',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'YHJCURR',
			title: '优惠券额',
			width: 80,
			fixed: true,
			align: 'right',
			halign: 'center',
			formatter: currfm
		},
		{
			field: 'PAYLIST',
			title: '收款方式',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center',
			formatter: function(value, row, index) {
				return (value != "" && value != undefined && value != null) ? value.replace(/ /g, "，") : '';
			}
		},
		{
			field: 'REMARK',
			title: '摘要',
			width: 150,
			fixed: true,
			align: 'left',
			halign: 'center'
		},
		{
			field: 'SALEMANLIST',
			title: '销售人',
			width: 120,
			align: 'center',
			halign: 'center',
			fixed: true
		},
		{
			field: 'OPERANT',
			title: '制单人',
			width: 80,
			align: 'center',
			halign: 'center',
			fixed: true
		},
		{
			field: 'ACTION',
			title: '操作',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				var stg = row.STATETAG;
				var istoday = row.ISTODAY;
				if (stg >= 1 && istoday == 1 && allowchedan == 1) return value == "noaction" ? "": '<input type="button" value="撤单" class="m-btn" onclick="withdraw(' + index + ')">';
				else if (stg == '0') return value == "noaction" ? "": '<input type="button" value="删除" class="m-btn" onclick="delnote(' + index + ')">';
				else return "";
			}
		}]],
		pageSize: 20,
		toolbar: "#toolbars"
	}).datagrid('keyCtr', "");
}
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
	setTimeout(function() {
		$('#gg').datagrid('resize', {
			height: $('body').height() - 90
		});
	},
	200);

}
//清空搜索条件
function resetsearch() {
	$('#shousename,#shouseid').val('');
	$('#shandno').val('');
	$('#swareno').val('');
	$('#swareid').val('');
	$('#sremark').val('');
	$('#svipno').val('');
	$('#sguestname').val('');
	$('#st3').prop('checked', true);
	var levelid = Number(getValuebyName("GLEVELID"));
	if(levelid==1||levelid==2||levelid==7||levelid==9){
		if(levelid==1||levelid==2||levelid==9){
			$('#shousename').next().hide();
			$('#shousename').prop('readonly',true);
		}
		$('#shouseid').val(Number(getValuebyName('HOUSEID')));
		$('#shousename').val(getValuebyName('HOUSENAME'));
	}
}
function openhistory() {
	$('#historyd').dialog('open');
	getnotedata();
}
var currentdata = {};
var getnotelist = function(page) {
	var houseid = Number($('#shouseid').val());
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "shopsalehlist";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	currentdata["mindate"] = $("#smindate").datebox("getValue");
	currentdata["maxdate"] = $("#smaxdate").datebox("getValue");
	currentdata["fieldlist"] = "a.id,a.accid,a.noteno,a.notedate,a.houseid,c.housename,a.totalcurr,a.totalamt,a.operant,a.statetag,a.remark,a.freecurr,a.yhjcurr,a.jfcurr,a.paylist,b.guestname";
	currentdata["houseid"] = houseid;
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
					data.footer = [{
						ROWNUMBER: "合计",
						TOTALAMT: dqzamt,
						TOTALCURR: dqzcurr
					}];
					$dg.datagrid('loadData', data);
					if (data.total > 0) $('#gg').datagrid('scrollTo', 0);
					$('#pp_total').html(data.total);
					$('#qsnotevalue').focus();
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
		findbox: value,
		statetag: 2
	};
	getnotelist(1);
}
//高级搜索
function searchnote() {
	var salemanid = $('#ssalemanid').val();
	var guestname = $('#sguestname').val();
	var handno = $('#shandno').val();
	var wareid = $('#swareid').val();
	var guestname = $('#sguestname').val();
	var vipfindbox = $('#svipno').val();
	var remark = $('#sremark').val();
	var statetag;
	if ($('#st1').prop('checked')) {
		statetag = "0";
	} else if ($('#st2').prop('checked')) {
		statetag = "1";
	} else {
		statetag = "2";
	}
	currentdata = {
			handno: handno,
			guestname: guestname,
			vipfindbox: vipfindbox,
			wareid: Number(wareid),
			remark: remark,
			statetag: statetag
	};
	getnotelist(1);
}
function loadshopsale() {
	var row = $('#gg').datagrid('getSelected');
	if (row) {
		$('.vip-detail input[type=hidden]:not(#epid)').val('');
		$('#id').val(row.ID);
		$('#noteno').html(row.NOTENO);
		$('#notedate').html(row.NOTEDATE);
		$('#yshj').html(row.TOTALCURR);
		$('#cashman').html(row.OPERANT);
		$('#statetag').val(row.STATETAG);
		dqamt = Number(row.TOTALAMT);
		dqcurr = Number(row.TOTALCURR);
		var stg = row.STATETAG;
		if (stg == "1") {
			$('#paybj-div').show();
			$('#statetag').val('1');
			$('#note-waremt').datagrid('refresh');
			$('#noteno').addClass('haspay');
			$('.vip-right').hide();
			d = false;
			$('.yhmsg').show();
			$('#tgpayd').html('▲');
			$('#addmxpanel').hide();
			$('#zddiscbtn,#cancelbtn,#paybtn,#zddiscbtn,#guadanbtn').hide();
			$('#add,#print, #qtprint,#historybtn').show();
			$('#addware-table input').prop('readonly', true);
			$('#addware-table .help-s-btn').hide();
			$("#uhousename").next().hide();
			$("#uhousename").prop("readonly",true);
			// 			alert("不允许查看已结账单据！");
		} else {
			resetvipipt();
			$('#paybtn,#zddiscbtn,#add,#print, #qtprint,#historybtn').hide();
			$('#cancelbtn,#guadanbtn').show();
			$('#paybj-div').hide();

			$('.yhmsg').hide();
			$('#tgpayd').html('▼');
			d = true;
			$('#addware-table input:not(#colorname,#sizename,#price0,#curr)').removeAttr('readonly');
			$('#addware-table .help-s-btn').show();
			$('.vip-right,#addmxpanel').show();
			$('#noteno').removeClass('haspay');
			var levelid = Number(getValuebyName("GLEVELID"));
			if(levelid!=1&&levelid!=2&&levelid!=9){
				$("#uhousename").next().show();
				$("#uhousename").removeProp("readonly");
			}
		}
		changeDivHeight();
		alertmsg(6);
		$('#historyd').dialog('close');
		getshopsalebyid();
		getshopwarem('1');
		if (xstype == 1) {
			$('#baradd').focus();
		} else {
			$('#epid,#saleman').val('');
			$('#saleman').focus();
		}
	} else {
		alerttext('请选择！');
	}
}
function getshopsalebyid() {
	var id = $('#id').val();
	var noteno = $('#noteno').html();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "getshopsalehbyid",
			noteno: noteno,
			fieldlist: "a.id,a.accid,a.noteno,a.notedate,a.houseid,c.housename,a.totalcurr,a.totalamt,a.operant,a.statetag,a.remark,a.paylist,a.guestid," 
			+ "b.guestname,a.jfcurr,a.yhjcurr,a.freecurr,b.balcent,b.balcurr,b.mobile,b.vipno,d.vtname,d.discount,a.checkcurr,a.cent,a.changecurr,a.zpaycurr"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					paylist = rows[0].PAYLIST; //结算方式
					var guestid = rows[0].GUESTID;
					var houseid = rows[0].HOUSEID;
					var guestname = rows[0].GUESTNAME;
					var vipno = rows[0].VIPNO;
					var mobile = rows[0].MOBILE;
					var balcent = rows[0].BALCENT;
					var discount = rows[0].DISCOUNT;
					var vtname = rows[0].VTNAME;
					var houseid = rows[0].HOUSEID;
					var housename = rows[0].HOUSENAME;
					var remark = rows[0].REMARK;
					$("#uhouseid").val(houseid);
					$("#uhousename").val(housename);
					$("#uremark,#uremark0").val(remark);
					var cent = rows[0].CENT;
					var balcurr = rows[0].BALCURR;
					var usecent = rows[0].USECENT;
					var jfcurr = Number(rows[0].JFCURR);
					var yhjcurr = Number(rows[0].YHJCURR);
					var freecurr = Number(rows[0].FREECURR);
					var checkcurr = Number(rows[0].CHECKCURR);
					var zpaycurr = Number(rows[0].ZPAYCURR);
					var changecurr = Number(rows[0].CHANGECURR);
					var totalcurr = Number(rows[0].TOTALCURR);
					$('#yhje').html(freecurr.toFixed(2));
					$('#yhq').html(yhjcurr.toFixed(2));
					$('#jfdk').html(jfcurr.toFixed(2));
					if ($('#satatag').val() == '1') $('#sshj').html(checkcurr.toFixed(2));
					else $('#sshj').html(totalcurr.toFixed(2));
					$('#hkhj').html(zpaycurr.toFixed(2));
					$('#zl').html(changecurr.toFixed(2));
					if (guestid != '0') {
						$('#guestid').val(guestid);
						$('#guestname').html(guestname);
						$('#vipno').html(vipno);
						$('#discount').val(Number(discount).toFixed(2));
						$('#mobile').html(mobile);
						$('#balcent').html(balcent);
						$('#vtname').html(vtname + '&nbsp;' + discount);
						$('#cent').html(cent);
						$('#balcurr').html(balcurr);
					} else {
						$('.vip-detail .note-text').html('');
						$('.vip-detail input[type=hidden]:not(#epid)').val('');
					}
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

function getsaleoutprice(wareid) {
	var salemanid = $('#epid').val();
	var houseid = $('#uhouseid').val();
	var guestid = $('#guestid').val();
	var salemanno = $('#saleman').val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "getsaleoutprice",
			wareid: wareid,
			guestid: Number(guestid),
			houseid: Number(houseid),
			salemanid: Number(salemanid),
			salemanno: salemanno.toUpperCase()
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if (Number(data.result) > 0) {
						var price0 = Number(data.PRICE0);
						var discount = Number(data.DISCOUNT);
						var price = Number(data.PRICE);
						$('#price0').val(price0.toFixed(2));
						$('#discount').val(discount.toFixed(2));
						$('#price').val(price.toFixed(2));
						var curr = price * Number($('#amount').val());
						$('#curr').val(getbylspoints(curr).toFixed(2));
						selcolor('shopsale');
					}
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

function findguestvip(findbox) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "findguestvip",
			findbox: findbox
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var result = data.result;
					if (result == "0") {
						selectvip('shopsalevip');
					} else if (result == "1") {
						var rows = data.rows;
						var guestid = rows.guestid;
						var guestname = rows.GUESTNAME;
						var vipno = rows.VIPNO;
						var mobile = rows.MOBILE;
						var vtname = rows.VTNAME;
						var disc = rows.DISCOUNT;
						var olddisc = Number($('#discount').val());
						var balcent = rows.BALCENT;
						var balcurr = rows.BALCURR;
						$('#guestid').val(guestid);
						if (Number(disc) != Number(olddisc)) $('#discount').val(Number(disc).toFixed(2));
						$('#guestname').html(guestname);
						$('#vipno').html(vipno);
						$('#mobile').html(mobile);
						$('#balcent').html(balcent);
						$('#vtname').html(vtname + '&nbsp;' + Number(disc).toFixed(2) + '');
						$('#balcurr').html(balcurr);
						changeguest(guestid);
						if (Number(disc) != Number(olddisc)){
							$('#discount').val(Number(disc).toFixed(2));
							changediscount();
						}
					} else if (result == "2") {
						alerttext(data.msg);
						$('#guestid').val("");
						$('#discount').val("1");
						$('#guestname').html("");
						$('#vipno').html('');
						$('#mobile').html('');
						$('#balcent').html('');
						$('#vtname').html('');
						$('#balcurr').html('');
					}
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
//赠品
function changeshopsalemzp(index, id, iszp) {
	var guestid = $('#guestid').val();
	var noteno = $('#noteno').html();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "changeshopsalemzp",
			noteno: noteno,
			guestid: Number(guestid),
			id: id,
			iszp: iszp
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					rows['ISZP'] = iszp;
					$('#note-waremt').datagrid('updateRow', {
						index: index,
						row: rows
					});
					var dqzcurr1 = Number(data.TOTALCURR);
					$('#note-waremt').datagrid('updateFooter', {
						CURR: data.TOTALCURR
					});
					$('#yshj').html(dqzcurr1.toFixed(2));
					$('#sshj').html(dqzcurr1.toFixed(2));
					$('#zl').html("0.00");
					$('#yhje').html("0.00");
					$('#czje').html("0.00");
					$('#jfdk').html("0.00");
					$('#yhq').html("0.00");
				}
				$('#note-waremt').datagrid('refresh');
			} catch(err) {
				console.error(err.message);
			}
		}

	});
}
function checkchecked() {
	if ($('#isqtprint').prop("checked")) {
		if ($('#isprint').prop("checked")) {
			$('#isprint').removeAttr('checked');
		}
		//$('#isqtprint').prop('checked', 'true');
	}
}
function checkchecked1() {
	if ($('#isprint').prop("checked")) {
		if ($('#isqtprint').prop("checked")) {
			$('#isqtprint').removeAttr('checked');
		}

		//$('#isqtprint').prop('checked', 'true');
	}
}
/*
//获取需要打印的单据信息
function getshopwaremprint() {
	var rows = $("#note-waremt").datagrid("getRows");
	strPrint = "<table>";
	strPrint += '<caption><b><font face="黑体" size="3">实惠服装商场<br/>零售小票<br/>&nbsp;</font></b></caption>';

	strPrint += '<tr><td colspan="2" align="left">NO:' + $('#noteno').html() + '</td><td colspan="3" align="right">日期:' + $('#notedate').html().substring(0, 10) + '</td></tr>';
	strPrint += '<tr class="a2"><td align="left" width="80px">货号</td><td align="center" width="30px">数量</td><td align="left" width="40px">原价</td><td align="center" width="35px" class="zkwd">折扣</td><td width="90px" ><span style="float:left;">售价</span><span style="float:right;">工号</span></td></tr>';
	for (var i = 0; i < rows.length; i++) {
		strPrint += '<tr class="a2"><td align="left" width="80px">' + rows[i].WARENO + '<br>' + rows[i].WARENAME + '</td><td align="center" width="30px">' + '<br>' + Number(rows[i].AMOUNT) + '</td><td align="left" width="50px">' + '<br>' + Number(rows[i].PRICE0) + '</td><td align="center" width="35px" class="zkwd">' + '<br>' + Number(rows[i].DISCOUNT).toFixed(2) + '</td><td align="left" width="90px" class="sjwd"><span style="float:right;">' + rows[i].EPNAME + '</span><br>' + Number(rows[i].CURR) + '</td></tr>';
	}
	strPrint += '<tr><td colspan="5" align="left">消费金额:' + $('#sshj').html() + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实收:' + $('#hkhj').html() + '</td></tr>';
	strPrint += '<tr><td colspan="5" align="left">结算方式:' + paylist + '</td></tr>';
	var yhjep = Number($('#yhje').html());
	if (yhjep != 0) {
		strPrint += '<tr><td colspan="5" align="left">优惠金额:' + yhjep + '</td></tr>';
	}

	strPrint += '<tr><td colspan="2" align="left" class="a1">会员卡号:' + $('#vipno').html() + '</td><td colspan="3" align="left" class="a1">本次积分:' + $('#cent').html() + '</td></tr>';
	strPrint += '<tr><td colspan="5" align="left">收银员:' + $('#cashman').html() + '</td></tr>';
	strPrint += '<tr><td class="a3" colspan="5">欢迎光临实惠服装商场选购商品，为提现本商场的信誉。凡在我商场所购的商品，确属质量问题，在七天内只要无污，无损均可凭此单调换商品。折扣商品除外！<br>地址：临平新大地A区二楼(步行街)<br>电话:0571-89183466</td></tr>';
	strPrint += '<tr><td colspan="5" align="left" class="a4" height="50px">.&nbsp;<br><br>.</td></tr>';
	strPrint += '</tabel>';
	$('#printgg').html(strPrint);
}
//前台打印
function qtprint(aaa) {
	getshopwaremprint();
	//LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	LODOP = getLodop();
	var style1 = "table{width:100%;font-size:11px;border-style: solid;border-color: #000000;  border-collapse:collapse;border-width:0px 0px 0px 0px;}" + "table .a2 td{border: solid #000000;border-width:0px 0px 1px 0px;}" + ".a3{border: solid #000000;border-width:1px 1px 1px 1px;}" + ".a4{height:50px;}" + ".a1{border: solid #000000;border-width:0px 0px 1px 0px;}";
	var strBodyStyle = "<style>" + style1 + "</style>";
	var strFormHtml = strBodyStyle + "<body>" + document.getElementById("printgg").innerHTML + "</body>";
	LODOP.SET_PRINTER_INDEXA("0");
	LODOP.SET_PRINT_PAGESIZE(3, 780, 0, "");
	LODOP.PRINT_INIT("实惠零售小票");
	LODOP.SET_PRINT_STYLE("FontSize", 18);
	LODOP.SET_PRINT_STYLE("Bold", 1);
	LODOP.ADD_PRINT_TEXT(5, 1, 270, 70, "实惠零售小票");
	LODOP.ADD_PRINT_HTM(5, 1, 270, 1000, strFormHtml);
	// if (toPrview)
	//LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW",1);//打印后自动关闭预览窗口
	//LODOP.SET_PREVIEW_WINDOW(0,3,1,600,400,"");
	// LODOP.PREVIEW();
	var prints1 = LODOP.PRINT();

}*/

function resetvipipt() {
	if ($('.icon_barcode').hasClass('active')) {
		$('.icon_barcode').removeClass('active');
		$('#vipbarcode').hide();
		$('.selvip').show();
		$('#guestfindbox').focus();
	}
}
function changesetvip() {
	if ($('.icon_barcode').hasClass('active')) {
		$('.icon_barcode').removeClass('active');
		$('#vipbarcode').hide();
		$('.selvip').show();
		$('#guestfindbox').focus();
	} else {
		$('.icon_barcode').addClass('active');
		$('#vipbarcode').show();
		$('.selvip').hide();
		$('#vipbarcode').val('');
		$('#vipbarcode').focus();
	}
}
var setvipbarcode = function(s) {
	$(s).keyup(function(e) {
		var key = e.which;
		if (key == 13) {
			var vipbarcode = this.value;
			this.value = "";
			if (vipbarcode.indexOf('#') > -1) {
				var vipidstr = vipbarcode.substring(vipbarcode.indexOf('#') + 1, vipbarcode.length);
				getvipinfobystr(vipidstr);
			}
		}
	});
}
function getvipinfobystr(vipidstr) {
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getguestvipbyno",
			vipidstr: vipidstr
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					if (data.total > 0) {
						var row = data.rows[0];
						$('#guestid').val(row.GUESTID);
						$('#discount').val(Number(row.DISCOUNT).toFixed(2));
						$('#guestfindbox').val(row.GUESTNAME);
						$('#guestname').html(row.GUESTNAME);
						$('#vipno').html(row.VIPNO);
						$('#mobile').html(row.MOBILE);
						$('#balcent').html(row.BALCENT);
						$('#vtid').val(row.VTID);
						$('#vtname').html(row.VTNAME);
						$('#balcurr').html(row.BALCURR);
						changeguest(row.GUESTID);
						changediscount();
						changesetvip();
						if (xstype == 1) {
							$('#baradd').focus();
						} else {
							$('#epid,#saleman').val('');
							$('#saleman').focus();
						}
					} else alerttext("未找到会员信息！");
				}
			} catch(e) {
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}

function checksendmsg() {
	if (useyhq == 1) {
		var usecent = Number($('#usecent').val());
		var payvcurr = Number($('.payvcurr').val());
		var yhjcurr = Number($('#yhjcurr').val());
		usecent = isNaN(usecent) ? 0 : usecent;
		payvcurr = isNaN(payvcurr) ? 0 : payvcurr;
		yhjcurr = isNaN(yhjcurr) ? 0 : yhjcurr;
		if (usecent > 0 || payvcurr > 0 || yhjcurr > 0) {
			$('#newask').val(1);
			$('#sendvipmsg').val("发送会员验证");
			$('#sendvipmsg').show();
			$('#btn_pay,#btn_pay_print').hide();
		} else {
			$('#sendvipmsg').hide();
			$('#btn_pay,#btn_pay_print').show();
		}
	}
}
function listvipcoupon() {
	var rows = $('#note-waremt').datagrid('getRows');
	var houseid = Number($("#uhouseid").val());
	var guestid = Number($('#guestid').val());
	var xfcurr = Number($('#checkpay').val());
	if (guestid <= 0 || isNaN(guestid)) {
		$('#yhjcurr').val(0);
		$('#isuseyhq').hide();
		return;
	}
	$('#yhjcurr .yhq').remove();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listvipcoupon",
			houseid: houseid,
			guestid: guestid,
			xfcurr: xfcurr
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var rows = data.rows;
					if (data.total == 0) {
						$('#isuseyhq').hide();
					} else $('#isuseyhq').show();
					for (var i in rows) {
						var row = rows[i];
						var html = '<option class="yhq" value="' + Number(row.CURR) + '" data-xfcurr="' + Number(row.XFCURR) + '" data-vcpid="' + Number(row.VCPID) + '">' + row.REMARK + '</option>';
						$('#yhjcurr').append(html);
					}
				}
			} catch(e) {
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}

function sendvipmessage() {
	var noteno = $('#noteno').html();
	var yhjcurr = Number($('#yhjcurr').val());
	var xfcurr = Number($('#yhjcurr :selected').data("xfcurr"));
	var usecent = Number($('#usecent').val());
	var jfcurr = Number($('#jfcurr').val());
	var payvcurr = Number($('.payvcurr').val());
	var totalcurr = Number($('#thispay').val());
	var newask = Number($('#newask').val());
	usecent = isNaN(usecent) ? 0 : usecent;
	payvcurr = isNaN(payvcurr) ? 0 : payvcurr;
	yhjcurr = isNaN(yhjcurr) ? 0 : yhjcurr;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "sendvipmessage1",
			noteno: noteno,
			yhjcurr: yhjcurr,
			xfcurr: xfcurr,
			usecent: usecent,
			jfcurr: jfcurr,
			payvcurr: payvcurr,
			totalcurr: totalcurr,
			newask: newask
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var result = data.result;
					if (result > 0) {
						if (newask == 0) {
							if (result == 2) {
								alerttext(data.msg);
								$('#sendvipmsg').val("发送会员验证");
								$('#sendvipmsg').hide();
								$('#btn_pay,#btn_pay_print').show();
							} else if (result >= 3) {
								alerttext(data.msg);
								$('#sendvipmsg').hide();
								$('#usecent,#jfcurr,.payvcurr').removeProp("readonly");
								$('#yhjcurr').removeProp('disabled');
							}
							$('#newask').val(1);
						} else if (newask == 1) {
							alerttext(data.msg);
							$('#sendvipmsg').val("获取验证结果");
							$('#usecent,#jfcurr,.payvcurr').prop("readonly", true);
							$('#yhjcurr').hide();
							$('#newask').val(0);
						}
					} else alerttext(data.msg);
				}
			} catch(e) {
				console.error(e);
				top.wrtiejsErrorLog(e);
			}
		}
	});
}
</script>
<!-- 销售员帮助列表 -->
<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 颜色帮助列表 -->
<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
<!-- 尺码帮助列表 -->
<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
<!-- 会员帮助列表 -->
<jsp:include page="../HelpList/VipHelpList.jsp"></jsp:include>
<!-- 会员类型帮助列表 -->
<jsp:include page="../HelpList/VipTypeHelpList.jsp"></jsp:include>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
<!-- 端口帮助列表 -->
<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
<jsp:include page="../HelpList/PayHelp.jsp"></jsp:include>
</body>
</html>