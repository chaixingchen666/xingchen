<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收银台</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
.cash-tab .cash-tab-ul li {
    float: left;
    width: 85px;
    font-size: 14px;
    height: 30px;
    line-height: 30px;
    margin: 0;
    padding: 0;
    color: #00959a;
    text-align: center;
    cursor: pointer;
}
.cash-tab .cash-tab-ul {
    padding: 0;
    float: left;
    margin: 0px 10px;
    border: solid #eaeaea 1px;
    border-radius: 3px;
    -webkit-border-radius: 3px;
}
.cash-tab .cash-tab-ul li:HOVER,.cash-tab .cash-tab-ul .li-active {
	background: #ff7900;
	color: #fff;
}
.table-box{
	border:solid 1px red;
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
<body class="easyui-layout layout panel-noscroll" >
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input type="button" class="btn1" id="updatebtn" value="编辑" onclick="updatenoted()">
	<input type="text" placeholder="搜索单据号、店铺、摘要<回车搜索>" data-end="yes" class="ipt1" id="qsnotevalue" maxlength="20" data-enter="getnotedata()">
	<input type="button" id="highsearch" class="btn2"value="高级搜索▼" onclick="toggle()">
	<input type="button" class="btn2 serbtn hide" type="button" value="搜索" onclick="searchnote();toggle();"> 
	<input type="button" class="btn2 serbtn hide" value="清空搜索条件" onclick="resetsearch()">
	<label><input  type="radio" name="jzbj" id="sjzbj2" checked>所有</label>
	<label><input type="radio" name="jzbj" id="sjzbj0">未结账</label>
	<label><input type="radio" name="jzbj" id="sjzbj1">已结账</label>
	</div>
<div class="cash-tab fr">
<ul class="cash-tab-ul">
<li class="li-active lskp hide" id="ntid-0">店铺零售</li><li class="pifa hide" id="ntid-1">批发开票</li><li class="pifa hide" id="ntid-2">批发退库</li>
</ul>
</div>
</div>
<!-- 高级搜索栏 -->
<div class="searchbar" style="display: none" data-enter="searchnote();toggle();">
<input type="hidden" id="swareid">
<input type="hidden" name="scustid" id="scustid" value=""> 
<input type="hidden" name="shouseid" id="shouseid" value=""> 
<div class="search-box">
<div class="s-box"><div class="title">店铺</div>
<div class="text">
<input class="edithelpinput house_help" data-value="#shouseid" id="shousename" placeholder="<输入>" type="text"><span class="s-btn" onclick="selecthouse('search')"></span>
</div>
</div>
<div class="s-box pfdiv"><div class="title" color="#ff7900">*&nbsp;客户</div>
<div class="text">
<input class="edithelpinput cust_help" id="scustname" data-value="#scustid" placeholder="<输入>" type="text"><span class="s-btn" onclick="selectcust('search')"></span>
</div>
</div>
<div class="s-box lsdiv"><div class="title">会员卡号</div>
<div class="text">
<input class="hig25 wid147" id="svipno" type="text" placeholder="<输入>">
</div>
</div>
<div class="s-box lsdiv"><div class="title">会员姓名</div>
<div class="text">
<input class="hig25 wid147" id="sguestname" type="text" placeholder="<输入>">
</div>
</div>
<div class="s-box"><div class="title">销售人</div>
<div class="text">
<input class="edithelpinput saleman_help" id="ssalemanname" data-value="#ssalemanid" type="text" placeholder="<输入>">
<span class="s-btn" onclick="selectemploye('search');"></span>
<input type="hidden" name="ssalemanid" id="ssalemanid" value=""> 
</div>
</div>
<div class="s-box"><div class="title">制单人</div>
<div class="text"><input type="text" class="hig25 wid160" id="soperant" name="soperant" placeholder="<输入>" />
</div>
</div>
</div>
</div>
</div>
<table id="gg" style="overflow: hidden;height:900px"></table>
<!-- 分页 -->
<div class="page-bar">
	<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>
<!-- 批发退库、批发开票-->
<div id="wareoutud" title="编辑批发开票" style="width: 100%;height:100%" class="easyui-dialog" closed="true" data-qickey>
<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" id="paybtn" value="收款" onclick="openwopay()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="pay" value="退单" onclick="wowithdraw()"></span>
	</span>
	<span id="ispayedbar" class="hide">
	<span><input class="btn4" type="button" id="paydetailbtn" value="收款明细" onclick="openwopaydetail()"></span>
	<span class="sepreate" id="sep-withdraw"></span>
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="wotwithdraw()"></span>	
	</span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="woprint" value="后台打印" ></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="woselprint" value="选择打印端口" onclick="cashselprint()"></span>

	<!-- 单据提交框 -->
	<form id="udnoteform" action="" method="get">
	<div id="udnote">
	<input type="hidden" id="untid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag" value="1"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="ucustid">
	<input type="hidden" id="udiscount"> 
	<input type="hidden" id="upricetype">
	<input type="hidden" id="uepid">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
		<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25 wid162" type="text" id="unoteno" readonly>
</div>
</div>
	<div class="s-box"><div class="title">日期</div>
	<div class="text"><input class="hig25 wid162" id="unotedate" type="text" readonly>
</div>
</div>
	<div class="s-box"><div class="title">店铺</div>
	<div class="text"><input class="hig25 wid162" type="text" id="uhousename" readonly>
</div>
</div>
	<div class="s-box"><div class="title" color="#ff7900">客户</div>
	<div class="text"><input class="hig25 wid162" id="ucustname" type="text" readonly>
</div>
</div>
	<div class="s-box"><div class="title">销售</div>
	<div class="text"><input class="hig25 wid162" type="text" id="usalemanlist" readonly>
</div>
</div>
	<div class="s-box"><div class="title">销售部门</div>
	<div class="text"><input  class="hig25 wid162" type="text" id="udptname" placeholder="<选择>" readonly>
</div>
</div>
	<div class="s-box"><div class="title">费用</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="edithelpinput" id="usaletotalcost" value="0" readonly><span onclick="selectsalecost()"></span>
</div>
</div>
	    
	<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" autocomplete="off" class="hig25 wid162" id="uhandno" placeholder="<输入>" readonly>
</div>
</div>
	<div class="clearbox">
	<div class="title">摘要</div>
	<div class="text"><input maxlength="100" type="text" id="uremark" autocomplete="off" class="hig25 wid_remark" readonly>
	</div>
	</div>
	</div>
	</div>
	</form>
	<div class="warem-toolbars">
		<div class="fl">
		<table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="warembar" onclick="updown2()" style="cursor: pointer;">▲&nbsp;&nbsp;商品明细</td>
		</table>
	</div>
	<div class="fr div_sort"><label onclick="getwareoutm($('#unoteno').val(), '1');"><input type="checkbox" id="waremsort" checked>录入排序</label></div>
	</div>
		</div>
	<!-- 商品明细表 -->
	<div>
	<table id="waret" class="table1"></table>
	<div id="warenametable" class="line30 mt10">
	<div class="fr mr20" align="right" id="wmtotalnote"></div>
	</div>
	</div>
</div>
<!-- 添加与修改店铺零售表单 -->
<div id="waresaleud" title="编辑店铺零售单" style="width: 100%; height: 100%;" class="easyui-dialog" closed="true" data-qickey>
<div id="udtool">
	<span id="updatingbar">
	<span><input class="btn4" type="button" id="pay" value="收款" onclick="openwspay()"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="widthdraw" value="退单" onclick="wswithdraw()"></span>
	</span>
	<span id="ispayedbar" class="hide">
	<span><input class="btn4" type="button" id="paydetailbtn" value="收款明细" onclick="openwspaydetail()"></span>
	<span class="sepreate" id="sep-withdraw"></span>
	<span><input class="btn4" type="button" id="withdrawbtn" value="撤单" onclick="wslwithdraw()"></span>	
	</span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="wsprint" value="后台打印"></span>
	<span class="sepreate"></span>
	<span><input class="btn4" type="button" id="wsselprint" value="选择打印端口" onclick="cashselprint()"></span>

	<!-- 单据提交框 -->
	<form id="udnoteform" action="" method="get">
	<div id="udnote">
	<input type="hidden" id="uid"> 
	<input type="hidden" id="untid">
	<input type="hidden" id="uepid">
	<input type="hidden" id="uistoday"> 
	<input type="hidden" id="ustatetag"  value="1"> 
	<input type="hidden" id="uhouseid"> 
	<input type="hidden" id="epid"> 
	<input type="hidden" id="uguestid">
	<input type="hidden" id="vtid">
	<input type="hidden" id="udiscount"> 
	<input type="hidden" id="upricetype">
	<input type="hidden" id="wtotalamt">
	<input type="hidden" id="wtotalcurr">
<div class="search-box">
	<div class="s-box"><div class="title">单据号</div>
	<div class="text"><input class="hig25 wid162" type="text" id="unoteno" readonly>
</div>
</div>
<div class="s-box"><div class="title">日期</div>
	<div class="text"><input class="hig25 wid162" id="unotedate" type="text" readonly>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
	<div class="text"><input class="hig25 wid162" type="text" id="uhousename" readonly>
</div>
</div>
<div class="s-box"><div class="title">销售</div>
	<div class="text"><input  class="hig25 wid162" type="text" id="usalemanlist" placeholder="<选择>" readonly>
</div>
</div>
<div class="s-box"><div class="title">自编号</div>
	<div class="text"><input maxlength="16" type="text" class="hig25 wid162" autocomplete="off" id="uhandno" placeholder="<输入>" readonly>
</div>
</div>
<div class="s-box"><div class="title">销售部门</div>
	<div class="text"><input  class="hig25 wid162" type="text" id="udptname" placeholder="<选择>" readonly>
</div>
</div>
<div class="clearbox">
	<div class="title">摘要</div>
	<div class="text"><input maxlength="100" type="text" id="uremark" autocomplete="off" class="hig25 wid_remark" readonly>
	</div>
	</div>
<div class="clearbox"><div class="title">会员</div>
	<div class="text">
	  <input maxlength="20" type="text" id="uguestname"  class="hig25 wid162" data-value="viplist" placeholder="<选择>" readonly>
</div>
	<input type="hidden" id="uusecent"><input type="hidden" id="ujfcurr">
	<table cellspacing="5" border="0"  id="guestinfo" class="hide">
	<tr><td width="60" align="right">会员卡号</td><td align="left" width="80" id="vipno"></td><td width="60" align="right">会员电话</td><td align="left" width="80" id="mobile"></td><td width="60" align="right">积分余额</td><td align="left" width="80" id="balcent"></td>
	<td width="60" align="right">储值余额</td><td align="left" width="80" id="balcurr"></td><td width="60" align="right">本次积分</td><td align="left" width="80" id="cent"></td><td width="60" align="right">会员类型</td><td align="left" width="80" id="vtname"></td></tr>
	</table>
</div>
</div>
	</div>
	<div class="warem-toolbars">
		<div class="fl">
		<table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="warembar" onclick="updown1()" style="cursor: pointer;">▲&nbsp;&nbsp;商品明细</td></tr>
		</table>
		</div>
	</div>
	</form>
		</div>
	<!-- 商品明细表 -->
	<div>
	<table id="waret" class="table1"></table>
	<div id="warenametable" class="line30 mt10">
	<div class="fr mr20" align="right" id="wmtotalnote"></div>
	</div>
	</div>
</div>

<!-- 店铺零售结账 -->
<div id="wspayd" title="结账" style="width: 600px;" class="easyui-dialog" closed="true" data-qickey="F4:'updatewaresaleh(false)',Z:'quick_zf(0)'">
<div style="text-align: center;">
<form action="" method="get" id="payform">
<input type="hidden" id="newask" value="1">
<table class="table1" border="0" cellspacing="10" cellpadding="0" width="600">
  <tr>
    <td width="67" align="right" class="header">合计金额</td>
    <td align="right" width="170"><input class="hig25 wid160" type="text" id="totalpay" readonly></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">免单金额</td>
    <td align="right"><input class="hig25 wid160" type="text" id="freepay" class="onlyMoney" onchange="getwsactpay()" ></td>
  </tr>
  <tr>
    <td align="right" class="header usecent">积分抵扣</td>
    <td align="right" class="usecent"><input type="text" id="usecent" style="width: 115px;height:25px" class="onlyNum" onchange="getjfcurr();">
    <input class="hig25" type="text" id="jfcurr" style="width: 40px;" value="0" readonly>
    </td>
<!--   </tr> -->
<!--   <tr id="isuseyhq"> -->
    <td align="right" class="header useyhq">优惠券</td>
    <td align="right" class="useyhq">
	<select class="hig25" id="yhjcurr" style="width: 115px;">
	<option value="0" data-xfcurr="0" data-vcpid="0">不使用优惠券</option>
	</select>
	 <input class="hig25" type="text" id="yhjcurrx" style="width: 70px;" value="0" readonly>
	</td>
  </tr>
  <tr>
    <td align="right" class="header">应结金额</td>
    <td align="right"><input class="hig25 wid160" type="text" id="actpay" readonly></td>
  </tr>
  <tr id="payway" class="hide"></tr>
  <tr>
    <td align="right" class="header">收款合计</td>
    <td align="right"><input class="hig25 wid160" type="text" id="sumpay" readonly></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">找零</td>
    <td align="right"><input class="hig25 wid160" type="text" id="changecurr" readonly></td>
  </tr>
</table>
</form>
</div>
<div class="dialog-normal-footer">
		<input type="button" class="btn1 hide" id="sendvipmsg" value="发送会员验证" onclick="sendvipmessage()">
		<input type="button" class="btn1" id="btn_pay"  style="width:70px;margin-right: 10px" name="" value="结账" onclick="updatewaresaleh(false)">
		<input type="button" class="btn1" id="btn_pay_print" style="width:120px;margin-right: 10px" name="" value="结账并打印" onclick="updatewaresaleh(true)">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#wspayd').dialog('close')">
</div>
</div>
<!-- 店铺零售结账明细框 -->
<div id="wspaydetaild" title="结账明细" style="width: 300px; height: 400px" class="easyui-dialog" closed="true">
<div style="text-align: center;">
	<table class="table1" border="0" cellspacing="10" cellpadding="10" width="280">
  <tr>
    <td width="67" class="header" align="right">合计金额</td>
    <td width="170" align="right"><input class="hig25 wid-in-td" type="text" id="totalpaym" readonly></td>
  </tr>
  <tr>
    <td class="header" align="right">免单金额</td>
    <td align="right" align="right"><input class="hig25 wid-in-td" type="text" id="freepaym" readonly></td>
  </tr>
  <tr id="centdivm">
    <td class="header" align="right">积分抵扣</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="jfcurrm" readonly></td>
  </tr>
   <tr id="yhqdivm">
    <td class="header" align="right">优惠券</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="yhjcurrm" readonly></td>
  </tr>
  <tr>
    <td class="header" align="right">应结金额</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="actpaym" readonly></td>
  </tr>
  <tr id="paywaym" class="hide"></tr>
  <tr>
    <td class="header" align="right">收款合计</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="sumpaym" readonly></td>
  </tr>
  <tr>
    <td class="header" align="right">找零</td>
    <td align="right"><input class="hig25 wid-in-td" type="text" id="changecurrm" readonly></td>
  </tr>
</table>
</div>
</div>
<!-- 批发开票结账明细框 -->
<!-- 收款框 -->
<div id="wopayd" title="结账" style="width: 620px;" class="easyui-dialog" closed="true"  data-qickey="F4:'updatewareouth(faalse)'">
<div style="width:100%;text-align: center;">
<table width="600" border="0" cellspacing="10" class="table1">
  <tr>
    <td width="70" align="right" class="header">上欠余额</td>
    <td width="170" align="right"><input class="wid160 hig25" type="text" id="pastpay" readonly></td>
<!--   </tr> -->
<!--     <tr id="iscreditok"> -->
    <td width="70" align="right" class="header creditok">信用余额</td>
    <td width="170" align="right" class="creditok"><input class="wid160 hig25" type="text" id="creditcurr" readonly></td>
  </tr>
  <tr>
    <td align="right" class="header" id="wtpayname">本单应收</td>
    <td align="right"><input class="wid160 hig25" type="text" id="thispay" readonly></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">折让优惠</td>
    <td align="right"><input class="wid160 hig25" type="text" id="discpay" placeholder="<输入>" onkeyup="wosumpay()"></td>
  </tr>
  <tr>
    <td align="right" class="header">实际应收</td>
    <td align="right"><input class="wid160 hig25" type="text" id="actpay" placeholder="<输入>" onkeyup="wosumpay()"></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">下欠余额</td>
    <td align="right"><input class="wid160 hig25" type="text" id="nextpay" placeholder="<输入>" readonly></td>
  </tr>
  <tr id="paylist" style="display: none"></tr>
  <tr>
    <td align="right" class="header" id="wtsumpayname">付款合计</td>
    <td align="right"><input class="wid160 hig25" type="text" id="sumpay" readonly></td>
<!--   </tr> -->
<!--   <tr> -->
    <td align="right" class="header">本单挂账</td>
    <td align="right"><input class="wid160 hig25" type="text" id="nopay" readonly></td>
  </tr>
</table>
</div>
<div class="dialog-normal-footer">
		<input type="button" class="btn1" style="width:70px;margin-right: 10px" name="pay" value="结账" onclick="updatewareouth(false)">
		<input type="button" class="btn1" style="width:120px;margin-right: 10px" name="pay" value="结账并打印" onclick="updatewareouth(true)">
		<input type="button" class="btn2" name="close" value="取消" onclick="$('#wopayd').dialog('close')">
</div>
</div>
<!-- 收款明细框 -->
<div id="wopaydetaild" title="收款明细" style="width: 350px; height: 400px;" class="easyui-dialog" closed="true">
<div style="width:100%;height:320px;overflow: auto;text-align: center;">
<table width="300" border="0" cellspacing="10" class="table1">
  <tr>
    <td align="right" class="header">上欠余额</td>
    <td align="right"><input class="wid160 hig25" type="text" id="pastpaym" readonly></td>
  </tr>
  <tr>
    <td width="70" align="right" class="header">合计金额</td>
    <td width="170" align="right"><input class="wid160 hig25" type="text" id="totalpaym" readonly></td>
  </tr>
  <tr>
    <td align="right" class="header">折让优惠</td>
    <td align="right"><input class="wid160 hig25" type="text" id="discpaym" readonly></td>
  </tr>
  <tr>
    <td align="right" class="header" id="wtpaymname">应结金额</td>
    <td align="right"><input class="wid160 hig25" type="text" id="actpaym" readonly></td>
  </tr>
   <tr id="paymlist" style="display: none"></tr>
  <tr>
    <td align="right" class="header" id="wtsumpaymname">付款合计</td>
    <td align="right"><input class="wid160 hig25" type="text" id="sumpaym" readonly></td>
  </tr>
  <tr>
    <td align="right" class="header">本单挂账</td>
    <td align="right"><input class="wid160 hig25" type="text" id="nopaym" readonly></td>
  </tr>
  <tr>
    <td align="right" class="header">下欠余额</td>
    <td align="right"><input class="wid160 hig25" type="text" id="nextpastpaym" readonly></td>
  </tr>
</table>
</div>
<div class="dialog-footer">
	<input type="button"  style="margin-right: 10px"  class="btn2" name="close" value="关闭" onclick="$('#wopaydetaild').dialog('close')">
</div>
</div>
<script type="text/javascript">

var levelid = getValuebyName("GLEVELID");
var pgid="";
var f4 = "setwareoutpaylist()";
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();  
}  
function changeDivHeight(){               
  var height = document.documentElement.clientHeight;//获取页面可见高度 
	$('#gg').datagrid('resize',{
  	height:height-45
  });
}
var sizenum = getValuebyName("SIZENUM");
var cols = Number(sizenum)<=5?5: Number(sizenum);
cols = cols >= 15 ? 15 : cols;
function setColums() {
	var colums = [];	
	colums[0] = {
			field : 'ROWNUMBER',
			title : '序号',
			fixed : true,
			width : 30,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				return value==""?"":index+1;
			}
		};
	colums[1] = {
		field : 'WAREID',
		title : 'WAREID',
		hidden : true,
		width : 10
	};
	colums[2] = {
		field : 'WARENO',
		title : '货号',
		width : 60,
		fixed : true,
		halign : 'center',
		styler: function(value,row,index){
			if(value=="合计"){
				return 'text-align:center';
			}else{
				return 'text-align:left';
			}
		}
	};
	colums[3] = {
			field : 'WARENAME',
			title : '商品名称',
			width : 120,
			fixed : true,
			halign : 'center',
			align :'left'
		};
	colums[4] = {
		field : 'UNITS',
		title : '单位',
		width : 40,
		fixed : true,
		halign : 'center',
		align :'center'
	};
	colums[5] = {
			field : 'COLORID',
			title : '颜色id',
			width : 10,
			hidden : true
		};
	colums[6] = {
		field : 'COLORNAME',
		title : '颜色',
		width : 60,
		fixed : true,
		halign : 'center',
		align :'center'
	};
	for (var i = 1; i <= cols; i++) {
		colums[6 + i] = {
			field : 'AMOUNT' + i,
			title : '<span id="title'+i+'"><span>',
			width : 35,
			fixed:true,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				return value==''?'':Number(value)==0?'':value;
			}
		};
	}
	colums[cols + 7] = {
		field : 'AMOUNT',
		title : '小计',
		width : 50,
		fixed:true,
		halign : 'center',
		align :'center'
	};
	colums[cols + 8] = {
		field : 'PRICE0',
		title : '原价',
		width : 70,
		fixed : true,
		halign : 'center',
		align :'right',
		formatter : currfm
	};
	colums[cols + 9] = {
			field : 'DISCOUNT',
			title : '折扣',
			width : 40,
			fixed :true,
			halign : 'center',
			align :'right',
			formatter : currfm
		};
	colums[cols + 10] = {
			field : 'PRICE',
			title : '单价',
			width : 70,
			fixed : true,
			halign : 'center',
			align :'right',
			formatter : currfm
		};
	colums[cols + 11] = {
		field : 'CURR',
		title : '金额',
		width : 100,
		fixed : true,
		halign : 'center',
		align : 'right',
		formatter : currfm
	};
	colums[cols + 12] = {
			field : 'SALENAME',
			title : '销售类型',
			width : 55,
			fixed :true,
			halign : 'center',
			align : 'center'
		};
	colums[cols + 13] = {
			field : 'REMARK0',
			title : '备注',
			width : 100,
			fixed : true,
			halign : 'center',
			align : 'left'
		};
	colums[cols + 14] = {
			field : 'SALEID',
			title : '销售类型id',
			width : 70,
			fixed : true,
			hidden : true
		};
	for (var i = 1; i <= cols; i++) {
		colums[cols + 14 + i] = {
			field : 'SIZEID' + i,
			title : 'sizeid',
			hidden : true,
			width : 10
		};
	}
	for (var i = 1; i <= cols; i++) {
		colums[cols + cols + 14 + i] = {
			field : 'SIZENAME' + i,
			title : 'sizename',
			hidden : true,
			width : 10
		};
	}
	return colums;
	}
var opser = "get";
function getntid(){
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	return Number(ntid);
}
$(function(){
	setskypay({
		ywtag: function(){
			var ntid = getntid();
			if(ntid==0)
				return 0;
			if(ntid==1)
				return 3;
		},
		noteno: function(){
			var ntid = getntid();
			if(ntid==0)
				return $('#waresaleud #unoteno').val();
			if(ntid==1)
				return $('#wareoutud #unoteno').val();
		},
		amt: function(){
			var ntid = getntid();
			if(ntid==0)
				return $('#waresaleud #waret').datagrid('getFooterRows')[0].AMOUNT;
			if(ntid==1)
				return $('#wareoutud #waret').datagrid('getFooterRows')[0].AMOUNT;
		},
		totalcurr: function(){
			var ntid = getntid();
			if(ntid==0)
				return $('#wspayd #actpay').val();
			if(ntid==1)
				return $('#wopayd #actpay').val();
		},
		curr: function(payfs){
			var ntid = getntid();
			if(ntid==0)
				return $("#wspayd input.paycurr.payfs"+payfs).val();
			if(ntid==1)
				return $("#wopayd input.paycurr.payfs"+payfs).val();
		},
		onBeforePay: function(payfs){
			var ntid = getntid();
			if(ntid==0){
				var totalcurr = Number($('#wspayd #totalpay').val());
				var jfcurr = Number($('#wspayd #jfcurr').val());
				var changecurr = Number($('#wspayd #changecurr').val());
				var freecurr = Number($('#wspayd #freepay').val());
				var usecent = Number($('#wspayd #usecent').val());
				var checkcurr = Number($('#wspayd #actpay').val());
				var zpaycurr = Number($('#wspayd #sumpay').val());
				var yhjcurr = Number($('#wspayd #yhjcurr').val());
				if(totalcurr-freecurr-jfcurr < yhjcurr&&checkcurr==0)
					yhjcurr = totalcurr-freecurr-jfcurr;
				var vcpid = Number($('#wspayd #yhjcurr :selected').data("vcpid"));
				
//		 		var smspwd; 
//		 		if(isv==true){
//		 			smspwd = $('#payv').val();
//		 		}else{
//		 			smspwd="f";
//		 		}
				if (Number(zpaycurr)<Number(checkcurr)&&checkcurr>=0) {
					alerttext('收款合计不等于应结金额，请输入！');
					$("#wspayd input.paycurr.payfs"+payfs).focus();
					return false;
// 				}else if(checkcurr<0){
// 					alerttext('暂时不支持退款！');
// 					$(jqcurr).focus();
// 					return false;
				}
			}
			if(ntid==1){
				var paycurr = Number($("#wopayd input.paycurr.payfs"+payfs).val());
				if (paycurr==0) {
					alerttext('收款合计不能等于0，请输入！');
					$("#wopayd input.paycurr.payfs"+payfs).focus();
					return false;
				}
			}
			return true;
		},
		onSuccess: function(payfs,curr){
			var ntid = getntid();
			if(ntid==0){
				$("#wspayd input.paycurr.payfs"+payfs).val(curr.toFixed(2))
				$("#wspayd input.paycurr.payfs"+payfs).prop('readonly',true);
				$("#wspayd input.paybtn.payfs"+payfs).prop('disabled',true);
				wssumpay();
				if(Number($('#wspayd .payw').val())>0){
					if(skypayedbj.wxpay==0){
						alerttext('微信支付未完成，请支付！');
						return;
					}
				}
				if(Number($('#wspayd .payz').val())>0){
					if(skypayedbj.zfbpay==0){
						alerttext('支付宝支付未完成，请支付！');
						return;
					}
				}
				var errorpaymsg = "";
				$("#wspayd .payqtzf").each(function(){
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
				//$('#wspayd #pay1').focus();
				updatewaresaleh(true);
			}
			if(ntid==1){
				$("#wopayd input.paycurr.payfs"+payfs).val(curr.toFixed(2))
				$("#wopayd input.paycurr.payfs"+payfs).prop('readonly',true);
				$("#wopayd input.paybtn.payfs"+payfs).prop('disabled',true);
				//$('#pay1').focus();
				if(Number($('#wopayd .payw').val())>0){
					if(skypayedbj.wxpay==0){
						alerttext('微信支付未完成，请支付！');
						return;
					}
				}
				if(Number($('#wopayd .payz').val())>0){
					if(skypayedbj.zfbpay==0){
						alerttext('支付宝支付未完成，请支付！');
						return;
					}
				}
				var errorpaymsg = "";
				$("#wopayd .payqtzf").each(function(){
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
				updatewareouth(true);
			}
		}
	});
	if(usesyt==1)
		$('.lskp').show();
	if(usepfsyt==1)
		$('.pifa').show();
	$('#wspayd #yhjcurr').change(function(e){
		var val = Number(this.value);
		if(!isNaN(val)){
			$('#wspayd #yhjcurrx').val(val.toFixed(2));
			getwsactpay();
			wssumpay();
			checksendmsg();
		}
	});
// 	$('#wspayd').delegate('#usecent','keyup',function(e){
// 		checksendmsg();
// 	});
	$('#pp').createPage({
		pageCount:1,
		current:1,
		autorefresh: true,
        backFn:function(p){
        	getnotelist(p);
        }
	 });
	$('#gg').datagrid({
		idField : 'NOTENO',
		width : '100%',
		height : $('body').height()-45,
		fitColumns : true,
		showFooter :true,
		striped : true, //隔行变色
		singleSelect : true,
		onSelect : function(rowIndex, rowData) {
			dqindex = rowIndex;
		},
		onDblClickRow : function(rowIndex, rowData) {
			updatenoted();
		},
		columns : [ [ {
			field : 'ID',
			title : 'ID',
			width : 200,
			hidden : true
		},	{
			field: 'ROWNUMBER',
			title: '序号',
			width: 40,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (isNaN(Number(value)) && value != undefined && value.length > 0) return value;
				var p = $("#pp").getPage() - 1;
				return p * pagecount + index + 1;
			}
		},
         { field : 'NOTENO',
			title : '单据号',
			width : 125,
			fixed :true,
			align : 'center',
			halign : 'center',
			styler: function(value,row,index){
				if(value=="合计"){
					return 'color:#ff7900;font-weight:700';
				}
				if(row.STATETAG=='1'){
					return 'color:#00959a;font-weight:900';
				}else if(row.STATETAG=='3'){
					return 'color:#ff3b30;font-weight:900';
				}else{
					return 'color:#ff7900;font-weight:700';
				}
			}
		},{
			field : 'NOTEDATE',
			title : '单据时间',
			width : 110,
			fixed :true,
			hidden : true
		},{
			field : 'NOTEDATE0',
			title : '单据日期',
			width : 112,
			fixed :true,
			align : 'center',
			halign : 'center',
			formatter:function(value,row,index){
				return row.NOTEDATE==undefined?'':row.NOTEDATE.substring(0,16);
			}
		},{field : 'HOSEID',
			title : '店铺id',
			width : 200,
			hidden : true
		}, {
			field : 'HOUSENAME',
			title : '店铺名称',
			width : 100,
			fixed : true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'GUESTNAME',
			title : '会员',
			width : 100,
			fixed : true,
			align : 'left',
			halign : 'center'
		}, {
			field : 'CUSTNAME',
			title : '客户名称',
			width : 100,
			fixed : true,
			align : 'left',
			halign : 'center',
			hidden : true
		}, {
			field : 'TOTALCURR',
			title : '总金额',
			width : 80,
			fixed :true,
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				return Number(value).toFixed(2);
			}
		}, {
			field : 'TOTALAMT',
			title : '总数量',
			width : 80,
			fixed :true,
			align : 'right',
			halign : 'left',
			formatter : function(value,row,index){
				return Number(value);
			}
		}, {
			field : 'PAYLIST',
			title : '<span id="paylistname">收款方式</span>',
			width : 100,
			fixed : true,
			align : 'center',
			halign : 'center'
		}, {
			field : 'HANDNO',
			title : '自编号',
			fixed : true,
			width : 100,
			align : 'center',
			halign : 'center'
		}, {
			field : 'REMARK',
			title : '摘要',
			width : 200,
			fixed : true,
			align : 'left',
			halign : 'center'
		},{
			field : 'SALEMANLIST',
			title : '销售人',
			width : 100,
			fixed :true,
			align : 'center',
			halign : 'center'
		},{
			field : 'OPERANT',
			title : '制单人',
			width : 80,
			fixed :true,
			align : 'center',
			halign : 'center'
		}, {
			field : 'STATETAG',
			title : '操作状态',
			width : 200,
			hidden : true
		} ]],
		pageSize : 20,
		toolbar : "#toolbars"
	}).datagrid('keyCtr',"updatenoted()");

	//切换表
	$('.cash-tab-ul li').click(function(e){
		var ntid = $(this).attr('id').replace('ntid-',"");
		$('.li-active').removeClass('li-active');
		$(this).addClass('li-active');
		alertmsg(6);
		switch (ntid) {
		case "0":
			 $('#gg').datagrid('hideColumn',"CUSTNAME");
			 $('#gg').datagrid('showColumn',"GUESTNAME");
			 $('#paylistname').html('收款方式');
			break;
		case "1":
			$('#gg').datagrid('showColumn',"CUSTNAME");
			$('#gg').datagrid('hideColumn',"GUESTNAME");
			$('#paylistname').html('收款方式');
			break;
		case "2":
			$('#gg').datagrid('showColumn',"CUSTNAME");
			$('#gg').datagrid('hideColumn',"GUESTNAME");
			$('#paylistname').html('退款方式');
			break;
		default:
			$('#gg').datagrid('showColumn',"CUSTNAME");
			$('#gg').datagrid('hideColumn',"GUESTNAME");
			break;
		}
		getnotedata();
	});
	$("input[name=jzbj]").change(function(e){
		getnotelist(1);
	});
	//后台打印
	$('#wsprint,#woprint').click(function(e){
		var ntid = $('.li-active').attr('id').replace("ntid-","");
			switch (ntid) {
			case "0":
				var noteid = $('#uid').val();
				var noteno = $('#waresaleud #unoteno').val();
				var houseid = $('#waresaleud #uhouseid').val();
				addbackprint("1201", houseid, noteid, noteno)
				break;
			case "1":
				var noteid = $('#uid').val();
				var noteno = $('#wareoutud #unoteno').val();
				var houseid = $('#wareoutud #uhouseid').val();
				addbackprint("1302", houseid, noteid, noteno)
				break;
			case "2":
				var noteid = $('#uid').val();
				var noteno = $('#wareoutud #unoteno').val();
				var houseid = $('#wareoutud #uhouseid').val();
				addbackprint("1303", houseid, noteid, noteno)	
				break;
			default:
				break;
			}
	});
	setwarem();
	if(usesyt==1){
		$('.li-active').removeClass('li-active');
		$('.lskp').addClass('li-active');
		$('.lskp').click();
	}
	else if(usepfsyt==1){
		$('.li-active').removeClass('li-active');
		$('.pifa:first').addClass('li-active');
		$('.pifa:first').click();
	}else{
		alerthide();
		alert('您未启用收银台的功能！');
		top.closeTab('tab_pg1207');//关闭当前窗口 
	}
});
var searchb = false;
function toggle() {
	var ntid = getntid();
	if(ntid==0){
		$("div.lsdiv").show();
		$("div.pfdiv").hide();
	}else{
		$("div.lsdiv").hide();
		$("div.pfdiv").show();
	}
	if (searchb) {
		$('#highsearch').val('高级搜索▼');
		$('input.serbtn').hide();
		searchb = false;
	} else {
		$('#highsearch').val('高级搜索▲');
		$('input.serbtn').show();
		searchb = true;
	}
	$('.searchbar').slideToggle('fast');
}
function resetsearch(){
	$('#shousename,#shouseid,#scustname,#scustid,#soperant,#ssalemanname,#ssalemanid,#svipno,#sguestname').val('');
}
var currentdata = {};
var getnotelist = function(page) {
	var $dg = $("#gg");
	var options = $dg.datagrid("options");
	// 	var sort = options.sortName;
	// 	var order = options.sortOrder;
	currentdata["erpser"] = "listwareoutcash";
	// 	currentdata["sort"] = sort;
	// 	currentdata["order"] = order;
	var jzbj = 2;
	if($("#sjzbj0").prop("checked"))
		jzbj = 0;
	else if($("#sjzbj1").prop("checked"))
		jzbj = 1;
	else jzbj = 2;
	var ntid = getntid();
	currentdata["ntid"] = ntid;
	currentdata["jzbj"] = jzbj;
	var fieldlist = "a.id,a.noteno,a.accid,a.notedate,a.houseid,c.housename,a.ntid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.lastdate,a.totalamt,a.totalcurr,a.paylist";
	if(ntid == 0)
		fieldlist += ",a.guestid,b.guestname";
	else if(ntid == 1 || ntid ==2)
		fieldlist += ",a.custid,b.custname";
	currentdata["fieldlist"] = fieldlist;
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
	var houseid = Number($('#shouseid').val());
	var custid = Number($('#scustid').val());
	var salemanid = Number($('#ssalemanid').val());
	var operant = $('#soperant').val();
	var vipno = $('#svipno').val();
	var guestname = $('#sguestname').val();
	currentdata = {
		houseid: houseid,
		custid: custid,
		salemanid: salemanid,
		operant: operant,
		vipno: vipno,
		guestname: guestname
	};
	getnotelist(1);
}
function updatenoted(){
	$('#waresaleud #udnote,#wareoutud #udnote').slideDown('fast');
	$('#waresaleud #warembar,#wareoutud #warembar').html('▲&nbsp;&nbsp;商品明细');
	setTimeout(function() {
		$('#waresaleud #waret,#wareoutud #waret').datagrid('resize',{
			height : $('body').height()-90
		});
	}, 200);
	d = false;
	f = false;
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	var row = $('#gg').datagrid("getSelected");
	if(row){
	var noteno = row.NOTENO;
	var id = row.ID;
	var stg = row.STATETAG;
	$('#uid').val(id);
		switch (ntid) {
		case "0":
			if(stg=="1"){
				$('#waresaleud #updatingbar').hide();
				$('#waresaleud #ispayedbar').show();
				$('#waresaleud').data('qickey',"F4:'openwspay()',F10:'wswithdraw()'");
			}else{
				$('#waresaleud #updatingbar').show();
				$('#waresaleud #ispayedbar').hide();
				$('#waresaleud').data('qickey',"F8:'openwspaydetail()',F9:'$(\"#waresaleud #print\").click()',F10:'wslwithdraw()'");
			}
			 getwaresalehbyid(id, noteno);
			 getwaresalem(noteno, "1");
			break;
		case "1":
			if(stg=="1"){
				$('#wareoutud #updatingbar').hide();
				$('#wareoutud #ispayedbar').show();
				$('#wareoutud').data('qickey',"F4:'openwopay()',F10:'wowithdraw()'");
			}else{
				$('#wareoutud #updatingbar').show();
				$('#wareoutud #ispayedbar').hide();
				$('#wareoutud').data('qickey',"F8:'openwopaydetail()',F9:'$(\"#wareoutud #print\").click()',F10:'wolwithdraw()'");
			}
			$('#wareoutud #paybtn').val("收款");
			$('#wareoutud #paydetailbtn').val("收款明细");
			$('#wopayd #wtpayname').html("本单应收");
			$('#wopayd #wtsumpayname').html("付款合计");
			$('#wopaydetaild #wtpaymname').html("应结金额");
			$('#wopaydetaild #wtsumpaymname').html("付款合计");
			 getwareouthbyid(id, noteno);
			break;
		case "2":
			if(stg=="1"){
				$('#wareoutud #updatingbar').hide();
				$('#wareoutud #ispayedbar').show();
				$('#wareoutud').data('qickey',"F4:'openwopay()',F10:'wowithdraw()'");
			}else{
				$('#wareoutud #updatingbar').show();
				$('#wareoutud #ispayedbar').hide();
				$('#wareoutud').data('qickey',"F8:'openwopaydetail()',F9:'$(\"#wareoutud #print\").click()',F10:'wolwithdraw()'");
			}
			$('#wareoutud #paybtn').val("退款");
			$('#wareoutud #paydetailbtn').val("退款明细");
			$('#wopayd #wtpayname').html("本单应退");
			$('#wopayd #wtsumpayname').html("退款合计");
			$('#wopaydetaild #wtpaymname').html("实际应退");
			$('#wopaydetaild #wtsumpaymname').html("退款合计");
			 getwareouthbyid(id, noteno);
			break;
		default:
			break;
		}
	}else{
		alerttext("请选择一条数据！");
	}
}

//获取id资料
function getwaresalehbyid(id, noteno) {
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "getwaresalehbyid",
			fieldlist: "a.id,a.accid,a.noteno,a.notedate,a.guestid,a.houseid,a.ntid,a.handno,a.remark,a.operant,a.statetag,"
				+ "b.guestname,b.vipno,a.checkcurr,a.freecurr,a.changecurr,a.zpaycurr,b.mobile,b.balcent,b.balcurr,"
				+ "c.housename,d.discount,d.vtid,d.vtname,c.address,c.tel,a.cent,a.usecent,a.jfcurr,a.dptid,e.dptname",
			noteno : noteno,
			id : id
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)) {
				var rows = data.rows;
				var guestid = rows[0].GUESTID;
				var noteno = rows[0].NOTENO;
				var notedate = rows[0].NOTEDATE;
				var housename = rows[0].HOUSENAME;
				var houseid = rows[0].HOUSEID;
				var guestname = rows[0].GUESTNAME;
				var salemanlist = rows[0].SALEMANLIST;
				var handno = rows[0].HANDNO;
				var remark = rows[0].REMARK;
				var totalamt = rows[0].TOTALAMT;
				dqamt = totalamt;
// 				var totalcurr = rows[0].TOTALCURR;
				var vipno = rows[0].VIPNO;
				var mobile = rows[0].MOBILE;
				var balcent = rows[0].BALCENT;
				var discount = rows[0].DISCOUNT;
				var vtid = rows[0].VTID;
				var vtname = rows[0].VTNAME;
				var cent = rows[0].CENT;
				var balcurr = rows[0].BALCURR;
				var usecent = rows[0].USECENT;
				var jfcurr = rows[0].JFCURR;
				var freecurr = rows[0].FREECURR;
				var istoday = rows[0].ISTODAY;
				var dptname = rows[0].DPTNAME;
				
				$('#waresaleud #uguestid').val(guestid);
				$('#waresaleud #uhouseid').val(houseid);
				$('#waresaleud #uguestname').val(guestname);
				$('#waresaleud #unoteno').val(noteno);
				$('#waresaleud #unotedate').val(notedate);
				$('#waresaleud #uhousename').val(housename);
				$('#waresaleud #usalemanlist').val(salemanlist);
				$('#waresaleud #uhandno').val(handno);
				$('#waresaleud #uremark').val(remark);
				$('#waresaleud #udptname').val(dptname);
// 				$('#waresaleud #wtotalamt').val(totalamt);
// 				$('#waresaleud #wtotalcurr').val(totalcurr);
				$('#waresaleud #vipno').html(vipno);
				$('#waresaleud #mobile').html(mobile);
				$('#waresaleud #balcent').html(balcent);
				$('#waresaleud #udiscount').val(discount);
				$('#waresaleud #vtid').val(vtid);
				$('#waresaleud #vtname').html(vtname);
				$('#waresaleud #cent').html(cent);
				$('#waresaleud #balcurr').html(balcurr);
				$('#waresaleud #uusecent').val(usecent);
				$('#waresaleud #ujfcurr').val(jfcurr);
				$('#wspaydetaild #freepaym').val(freecurr);
				if(guestid=='0'){
					$('#waresaleud #guestinfo').hide();
				}else{
					$('#waresaleud #guestinfo').show();
				}
				if(istoday=="1"&&allowchedan=='1'){
					$('#waresaleud #sep-withdraw').show();
					$('#waresaleud #withdrawbtn').show();
				}else{
					$('#waresaleud #sep-withdraw').hide();
					$('#waresaleud #withdrawbtn').hide();
				}
				$('#waresaleud').dialog("setTitle","浏览店铺零售单");
				$('#waresaleud').dialog('open');
			}
		}
	});
}
function getwareouthbyid(id, noteno) {
	alertmsg(6);
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "getwareouthbyid",
			fieldlist: "a.id,a.noteno,a.accid,a.notedate,a.custid,a.houseid,a.ntid,a.handno,a.remark,a.operant,a.checkman,a.statetag,a.dptid,e.dptname,"
				+"a.fhtag,a.lastdate,a.totalamt,a.totalcurr,a.totalcost,b.custname,b.discount,b.pricetype,b.address,b.tel,c.housename,a.paylist,a.orderno,"
				+"a.ywnoteno,a.prtnum,a.cleartag",
			noteno : noteno,
			id : id
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)) {
				var rows = data.rows;
				var noteno = rows[0].NOTENO;
				var notedate = rows[0].NOTEDATE;
				var housename = rows[0].HOUSENAME;
				var houseid = rows[0].HOUSEID;
				var custname = rows[0].CUSTNAME;
				var custid = rows[0].CUSTID;
				var salemanlist = rows[0].SALEMANLIST;
				var handno = rows[0].HANDNO;
				var dptname = rows[0].DPTNAME;
				var remark = rows[0].REMARK;
				var totalamt = rows[0].TOTALAMT;
				var totalcurr = rows[0].TOTALCURR;
				var istoday = rows[0].ISTODAY;
				var totalcost = rows[0].TOTALCOST;
				$('#wareoutud #usaletotalcost').val(totalcost);
				$('#wareoutud #unoteno').val(noteno);
				$('#wareoutud #unotedate').val(notedate);
				$('#wareoutud #uhousename').val(housename);
				$('#wareoutud #uhouseid').val(houseid);
				$('#wareoutud #ucustname').val(custname);
				$('#wareoutud #ucustid').val(custid);
				$('#wareoutud #usalemanlist').val(salemanlist);
				$('#wareoutud #uhandno').val(handno);
				$('#wareoutud #udptname').val(dptname);
				$('#wareoutud #uremark').val(remark);
				$('#wareoutud #uistoday').val(istoday);
				$('#wareoutud #wtotalamt').val(totalamt);
				$('#wareoutud #wtotalcurr').val(totalcurr);
				$('#wareoutud').dialog("setTitle",ntid=='1'?"浏览批发开票单":"浏览批发退库单");
				if(istoday=="1"&&allowchedan=='1'){
					$('#wareoutud #sep-withdraw').show();
					$('#wareoutud #withdrawbtn').show();
				}else{
					$('#wareoutud #sep-withdraw').hide();
					$('#wareoutud #withdrawbtn').hide();
				}
				$('#wareoutud').dialog('open');
				 getwareoutm(noteno, "1");
			}
		}
	});
}
//收缩展开
var d = false;
function updown1() {
	$('#waresaleud #udnote').slideToggle('fast');
	if (!d) {
		$('#waresaleud #warembar').html('▼&nbsp;&nbsp;商品明细');
		d = true;
	} else {
		$('#waresaleud #warembar').html('▲&nbsp;&nbsp;商品明细');
		d = false;
	}
	setTimeout(function() {
		$('#waresaleud #waret').datagrid('resize',{
			height : $('body').height()-90
		});
	}, 200);
}
//收缩展开
var f = false;
function updown2() {
	$('#wareoutud #udnote').slideToggle('fast');
	if (!f) {
		$('#wareoutud #warembar').html('▼&nbsp;&nbsp;商品明细');
		f = true;
	} else {
		$('#wareoutud #warembar').html('▲&nbsp;&nbsp;商品明细');
		f= false;
	}
	setTimeout(function() {
		$('#wareoutud #waret').datagrid('resize',{
			height : $('body').height()-90
		});
	}, 200);
}
var nextpg=true;
function setwarem(){
	$('#waresaleud #waret').datagrid({
		idField : 'ID',
		height : $('body').height()-90,
		toolbar: "#waresaleud #udtool",
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		nowrap :false,//允许自动换行
		onDblClickRow : function(rowIndex, rowData) {
			
		},
		showFooter : true,
		columns : [ [
		{
			field : 'ROWNUMBER',title : '序号',width :30,fixed:true,align:'center',halign:'center',
			formatter : function(value,row,index){
				return value==""?"":index+1;
			}
		},{
			field : 'WARENO',title : '货号',width : 1,align:'center',halign:'center'
		},{
			field : 'WARENAME',title : '商品名称',width : 2,align:'center',halign:'center'
		},{
			field : 'COLORNAME',title : '颜色',width : 1,align:'center',halign:'center'
		}, {
			field : 'SIZENAME',title : '尺码',width : 1,align:'center',halign:'center'
		}, {
			field : 'AMOUNT',title : '数量',width : 1,align:'right',halign:'center',
			formatter : function(value,row,index){
				return Number(value);
			}
		},{
			field : 'UNITS',title : '单位',width :50,fixed:true,align:'center',halign:'center'
		}, {
			field : 'PRICE0',title : '单价',width : 2,align:'right',halign:'center',
			formatter : currfm
		}, {
			field : 'DISCOUNT',title : '折扣',width : 2,align:'center',halign:'center',formatter: currfm
		}, {
			field : 'PRICE',title : '折后价',width : 2,align:'right',halign:'center',
			formatter : currfm
		}, {
			field : 'CURR',title : '金额',width : 3,align:'right',halign:'center',
			formatter : currfm
		}] ],
		pageSize : 20
	});
	$('#wareoutud #waret').datagrid({
		idField : 'ID',
		height : $('body').height()-90,
		toolbar: "#wareoutud #udtool",
		fitColumns : true,
		striped : true, //隔行变色
		showFooter:true,
		nowrap :false,//允许自动换行
		singleSelect : true,
		onSelect : function(rowIndex, rowData) {
			if(rowData){
				for(var i=1;i<=cols;i++){
					$('#title'+i).html(eval('rowData.SIZENAME'+i));
				}
			}
		},
		onLoadSuccess: function(data){
			var sizelength = 0;
			var rows = data.rows;
			var $dg = $('#wareourud #waret');
			for(var i=0;i<rows.length;i++){
				var item= rows[i]; 
				while(sizelength<=sizenum){
					var s = sizelength+1;
					if(item["SIZENAME"+s]!=""&&item["SIZENAME"+s]!=undefined)
						sizelength++;
					else
						break;
				}
			}
			for(var i=1;i<=sizenum;i++){
				if(i<=sizelength)
					$dg.datagrid("showColumn","AMOUNT"+i);
				else
					$dg.datagrid("hideColumn","AMOUNT"+i);
			}
			$dg.datagrid('resize');
			},
		columns : [setColums()],
		pageSize : 20
		}).datagrid("keyCtr","");
	
	$('#waresaleud #waret').prev().children('.datagrid-body').scroll(function(){
		var $this =$(this);
	    var viewH =$(this).height();//可见高度
	    var contentH =$(this).get(0).scrollHeight;//内容高度
	    var scrollTop =$(this).scrollTop();//滚动高度
	  	if(contentH > (viewH+1) && contentH - viewH - scrollTop <= 3 && $("#waresaleud #waret").datagrid("getRows").length > 0){ //到达底部100px时,加载新内容
	  		if($('#waresaleud #udnote').css('display')!="none"){
      			$('#waresaleud #udnote').hide();
	      		$('#waresaleud #warembar').html('▼&nbsp;&nbsp;商品明细');
	      		setTimeout(function() {
	      			$('#waresaleud #waret').datagrid('resize',{
	      				height : $('body').height()-90
	      			});
	      		}, 200);
	    		f = true;
      		}if(sumpg>pg&&nextpg){
				nextpg = false;
				getwaresalem($('#waresaleud #unoteno').val(),pg+1);
				nextpg = true;
			}
	   		// 这里加载数据..
	   }
	});
		$('#wareoutud #waret').prev().children('.datagrid-body').scroll(function(){
			var $this =$(this);
		    var viewH =$(this).height();//可见高度
		    var contentH =$(this).get(0).scrollHeight;//内容高度
		    var scrollTop =$(this).scrollTop();//滚动高度
		  	if(contentH > (viewH+1) && contentH - viewH - scrollTop <= 3 && $("#wareoutud #waret").datagrid("getRows").length > 0){ //到达底部100px时,加载新内容
		  		if($('#wareoutud #udnote').css('display')!="none"){
	      			$('#wareoutud #udnote').hide();
		      		$('#wareoutud #warembar').html('▼&nbsp;&nbsp;商品明细');
		      		setTimeout(function() {
		      			$('#wareoutud #waret').datagrid('resize',{
		      				height : $('body').height()-90
		      			});
		      		}, 200);
		    		f = true;
	      		}if(sumpg>pg&&nextpg){
	      			nextpg = false;
					getwareoutm($('#wareoutud #unoteno').val(),pg+1);
					nextpg=true;
				}
		   		// 这里加载数据..
		   }
		});
}
function getwaresalem(noteno,page){
	alertmsg(6);
	if(page==1) $('#waresaleud #waret').datagrid('loadData', nulldata);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async:false,
		data : {
			erpser: "waresalemlist",
			noteno: noteno,
			fieldlist: "*",
			rows: pagecount,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)) {
				var total = data.total;
				var totalamt = data.totalamount;
				var totalcurr = data.totalcurr;
				data.footer = [{
					AMOUNT: totalamt,
					CURR: totalcurr,
					COLORNAME: "SKC",
					SIZENAME: data.skc
				}];
				sumpg = Math.ceil(Number(total) / pagecount);
				$('#waresaleud #wtotalamt').val(totalamt);
				$('#waresaleud #wtotalcurr').val(Number(totalcurr).toFixed(2));
				if(page=='1'){
					pg=1;
					$('#waresaleud #waret').datagrid('loadData', data);
					if($('#waresaleud #waret').datagrid('getRows').length>0){
						$('#waresaleud #waret').datagrid('selectRow',0);
					}
				}else{
					pg++;
					var rows = data.rows;
					for(var i in rows){
						$('#waresaleud #waret').datagrid('appendRow',rows[i]);
					}
				}
				$('#waresaleud #wmtotalnote').html('已显示'+$('#waresaleud #waret').datagrid('getRows').length+'条记录/共有'+total+'条记录');
			}
		}
	});
}
function getwareoutm(noteno,page){
	alertmsg(6);
	if(page==1) $('#wareoutud #waret').datagrid('loadData', nulldata);
	var sortid = $('#waremsort').prop('checked')?1:0;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data : {
			erpser : "wareoutmcolorsumlist",
			noteno : noteno,
			sortid: sortid,
			sizenum: sizenum,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			var totalcost = Number($("#usaletotalcost").val());
			var totals = data.total;
			var totalamt = data.totalamt;
			var totalcurr = data.totalcurr;
			var retailcurr = data.retailcurr;
			data.footer = [{
				WARENO: "合计",
				AMOUNT: totalamt,
				CURR: totalcurr,
				RETAILCURR: retailcurr
			}];
			sumpg = Math.ceil(Number(totals) / pagecount);
				$('#wareoutud #wtotalamt').val(totalamt);
				$('#wareoutud #wtotalcurr').val(Number(totalcurr).toFixed(2));
				if(page=='1'){
					pg=1;
					$('#wareoutud #waret').datagrid('loadData', data);
					if($('#wareoutud #waret').datagrid('getRows').length>0){
						$('#wareoutud #waret').datagrid('selectRow',0);
					}
				}else{
					pg++;
					var rows = data.rows;
					for(var i in rows){
						$('#wareoutud #waret').datagrid('appendRow',rows[i]);
					}
				}
				$('#wareoutud #wmtotalnote').html('已显示'+$('#wareoutud #waret').datagrid('getRows').length+'条记录/共有'+totals+'条记录');
			}
	});
}
//打开结账窗口
function openwspay() {
	var amt = $('#waresaleud #waret').datagrid('getRows').length;
	if(amt==0){
		alerttext("商品不能为空");
	}else{
	$('#wspayd #payform')[0].reset();
	var guestid = Number($('#waresaleud #uguestid').val());
	if($('#waresaleud #uguestname').val()!=""&&$('#waresaleud #uguestid').val()!=""){
		var balcent = $('#waresaleud #balcent').html()=='0'||$('#waresaleud #balcent').html()==''?0:Number($('#waresaleud #balcent').html());
		balcent==0?$('#wspayd td.usecent').hide():$('#wspayd td.usecent').show();
		$('#wspayd #usecent').attr("placeholder","<可用积分："+balcent+">");
		$('#wspayd td.usecent,#wspayd td.useyhq').show();
		$('#wspayd #newask').val(1);
	}else{
		$('#wspayd td.usecent,#wspayd td.useyhq').hide();
	}
	$('#wspayd #freepay').removeAttr('readonly');
	$('#wspayd').dialog({
		modal : true
	});
	var totalpay = Number($('#waresaleud #wtotalcurr').val());
//	if(totalpay<0)
//	var chpay = -(Math.ceil(totalpay)- totalpay);
//else
//	var chpay = 1- (Math.ceil(totalpay)- totalpay);
//chpay = chpay==1||chpay==-1?0:chpay;
	$('#wspayd #totalpay').val(totalpay.toFixed(2));
	var checkpay = 0;
	if(lsmlfs==0||points==0)
	checkpay = Math.floor(totalpay);
	else if(lsmlfs==1)
	checkpay = Math.round(totalpay);
	else if(lsmlfs==2)
	checkpay = totalpay;
	$('#wspayd #actpay').val(checkpay.toFixed(2));
	$('#wspayd #freepay').val((totalpay-checkpay).toFixed(2));
	$('#updatediv').show();
	listvipcoupon();
	getwspaywaylist(false);
	}
}
//结账明细窗口
function openwspaydetail() {
	$('#wspaydetaild #sumpaym').val('');
	$('#wspaydetaild #changecurrm').val('');
	$('#wspaydetaild').dialog({
		modal : true
	});
	var usecent = $('#wspaydetaild #uusecent').val();
	var jfcurr = $('#wspaydetaild #ujfcurr').val();
	usecent==''?$('#wspaydetaild #centdivm').hide():$('#wspaydetaild #centdivm').show();
	$('#wspaydetaild #usecentm').val(usecent);
	$('#wspaydetaild #jfcurrm').val(jfcurr);
	var guestid = $('#wspaydetaild #uguestid').val();
	$('#wspaydetaild #totalpaym').val($('#waresaleud #wtotalcurr').val());
	getwaresalepay();
	$('#wspaydetaild').dialog('open');
}
//生成支付列表行
var isv=false;
function getwspaywaylist(disabled) {
	$('.paytr').detach();
	alertmsg(6);
	var online_pay = {wx:0,zfb:0,qtzf:0};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: "paywaylist",
			rows: 50,
			sort: "payno",
			order: "asc",
			nov: 0,
			sybj: 1 
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) {
			if(valideData(data)){
				var total = data.total;
				payline = Number(total);
				var balcurr = Number($('#waresaleud #balcurr').html());
				var rows = data.rows;
				var i = 1;
				var tr = document.createElement("tr");
				var newtr = true;
				for (var row in rows) {
					if(newtr){
						tr = document.createElement("tr");
						tr.className = "paytr";
					}
					if(rows[row].PAYNO=='V'){
						if(balcurr!=0){
							var td1 = document.createElement("td");
							var td2 = document.createElement("td");
							td1.align="right";
							td1.className="header";
							td2.align="right";
							var label = document.createElement("label");
							var inputt = document.createElement("input");
							var inputh = document.createElement("input");
							var inputh1 = document.createElement("input");
							inputt.type = "text";
							inputh.type = "hidden";
							inputh1.type = "hidden";
							label.innerHTML = rows[row].PAYNAME;
							label.style.cursor = "pointer";
							label.onclick = function(){
								$('#wspayd .paycurr:not([readonly])').val("");
								var pi = this.id.replace("payname","");
								var actpay = Number($('#wspayd #actpay').val());
								var payedcurr = 0;
								for(var i=0;i<$('#wspayd .paycurr[readonly]').length;i++){
									payedcurr += Number($('#wspayd .paycurr[readonly]').eq(i).val());
								}
								if(actpay>Number($('#waresaleud #balcurr').html())){
									$('#wspayd #pay'+pi).val($('#waresaleud #balcurr').html());
								}else
									$('#wspayd #pay'+pi).val((actpay-payedcurr).toFixed(2));
								wssumpay();
								$('#wspayd #pay'+pi).select().focus();
							};
							label.id = "payname" +i;
							inputt.placeholder = "可用余额:"+$('#balcurr').html()+"元";
							inputt.id = "pay" + i;
							inputt.className = "hig25 payvcurr paycurr";
							$(inputt).change(function(){
								var checkcurr = Number($('#actpay').val());
								checkcurr = isNaN(checkcurr)?0:checkcurr
								if(checkcurr<0&&checkcurr>Number(this.value)){
									this.value=Number(checkcurr).toFixed(2);
									alerttext("退款金额不能大于应退金额");
								}
								else
									this.value=Number(this.value).toFixed(2);
								wssumpay();
							});
							inputt.onkeyup = function() {
								if(this.value!="-"){
					        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
					        		wssumpay();
								}
							};
							inputt.onafterpaste = function() {
								if(this.value!="-"){
					        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
									this.value = Number(this.value)>Number($('#balcurr').html())?$('#balcurr').html():Number(this.value);
									wssumpay();
								}
							};
							inputt.disabled = disabled;
							inputh.id = "payid" + i;
							inputh1.id = "payno" + i;
							inputh1.value = rows[row].PAYNO;
							inputh.value = rows[row].PAYID;
							td1.appendChild(label);
							td2.appendChild(inputh);
							td2.appendChild(inputh1);
							td2.appendChild(inputt);
							tr.appendChild(td1);
							tr.appendChild(td2);
						}else continue;
						
					}else if(rows[row].PAYNO=='S'||rows[row].PAYNO=='R'||rows[row].PAYNO=='W'||rows[row].PAYNO=='Z'){
						var td1 = document.createElement("td");
						var td2 = document.createElement("td");
						td1.align="right";
						td1.className="header";
						td2.align="right";
						var label = document.createElement("label");
						var inputt = document.createElement("input");
						var inputh = document.createElement("input");
						var inputh1 = document.createElement("input");
						var inputb =  document.createElement("input");
						inputt.type = "text";
						inputh.type = "hidden";
						inputh1.type = "hidden";
						inputb.type = "button";
						var payfs = 0;
						$(inputb).data('currid',"#pay" + i);
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
						} else if (rows[row].PAYNO == 'W') {
							$(inputb).data('payfs', 1);
							inputb.className = "paybtn payfs"+qtpayfs;
							inputt.className = "paycurr payw payfs1";
							online_pay.wx = 1;
						} else if (rows[row].PAYNO == 'Z') {
							$(inputb).data('payfs', 0);
							inputb.className = "paybtn payfs"+qtpayfs;
							inputt.className = "paycurr payz payfs0";
							online_pay.zfb = 1;
						}
						inputb.onclick = function() {
							var payfs = $(this).data('payfs');
							openskypay(payfs);
						};
						label.innerHTML = rows[row].PAYNAME;
						label.style.cursor = "pointer";
						label.onclick = function(){
							if($('#wspayd #pay'+pi+'[readonly]').length==0){
								$('#wspayd .paycurr:not([readonly])').val("");
								var pi = this.id.replace("payname","");
								var actpay = Number($('#actpay').val());
								var payedcurr = 0;
								for(var i=0;i<$('#wspayd .paycurr[readonly]').length;i++){
									payedcurr += Number($('#wspayd .paycurr[readonly]').eq(i).val());
								}
								$('#wspayd #pay'+pi).val((actpay-payedcurr).toFixed(2));
								wssumpay();
								$('#wspayd #pay'+pi).select().focus();
							}
						};
						label.id = "payname" + i;
						inputt.placeholder = "<输入>";
						inputt.id = "pay" + i; 
						inputt.style.width="105px";
						inputt.style.height="25px";
						inputb.className="btn2";
						inputb.style.width="50px";
						inputb.style.padding="2px";
						var actpay = Number($('#actpay').val());
						if(actpay>=0)
							inputb.value = "支付";
						else if(actpay<0)
							inputb.value = "退款";
						 $(inputt).change(function(){
							this.value=Number(this.value).toFixed(2);
							wssumpay();
						});;
						inputt.onkeyup = function(e) {
							if(this.value!="-"){
				        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
							}
							wssumpay();
						}
						inputt.onafterpaste = function() {
							if(this.value!="-"){
						    		this.value = this.value.replace(/[^-?\d.*$]/g,'');
							}
						};
						inputt.disabled = disabled;
						inputh.id = "payid" + i;
						inputh1.id = "payno" + i;
						inputh1.value = rows[row].PAYNO;
						inputh.value = rows[row].PAYID;
						td1.appendChild(label);
						td2.appendChild(inputb);
						td2.appendChild(inputt);	
						td2.appendChild(inputh);
						td2.appendChild(inputh1);
						tr.appendChild(td1);
						tr.appendChild(td2);
					}else{
						var td1 = document.createElement("td");
						var td2 = document.createElement("td");
						td1.align="right";
						td2.align="right";
						td1.className="header";
						var label = document.createElement("label");
						var inputh1 = document.createElement("input");
						var inputt = document.createElement("input");
						var inputh = document.createElement("input");
						inputt.type = "text";
						inputh.type = "hidden";
						inputh1.type = "hidden";
						label.innerHTML = rows[row].PAYNAME;
						label.style.cursor = "pointer";
						label.onclick = function(){
							$('#wspayd .paycurr:not([readonly],[disabled])').val("");
							var pi = this.id.replace("payname","");
							var actpay = Number($('#actpay').val());
							var payedcurr = 0;
							for(var i=0;i<$('#wspayd .paycurr[readonly]').length;i++){
								payedcurr += Number($('#wspayd .paycurr[readonly]').eq(i).val());
							}
							$('#wspayd #pay'+pi).val((actpay-payedcurr).toFixed(2));
							wssumpay();
							$('#wspayd #pay'+pi).select().focus();
						};
						label.id = "payname" + i;
						inputt.id = "pay" + i;
						inputt.className="hig25 wid160 paycurr";
						inputt.placeholder = "<输入>";
						$(inputt).change(function(){
							this.value=Number(this.value).toFixed(2);
							wssumpay();
						});
						inputt.onkeyup = function() {
							if(this.value!="-"){
				        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
				        		wssumpay();
							}
						};
						inputt.onafterpaste = function() {
							if(this.value!="-"){
				        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
				        		wssumpay();
							}
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
					}
					if(newtr){
						newtr = false;
					}else{
						newtr = true;
						$('#wspayd #payway').before(tr);
					}
					i = i + 1;
				}
				if(newtr==false) $('#wspayd #payway').before(tr);
				skypayedbj = {zfbpay:0,paynoteno_wx:"",wxpay:0,paynoteno_zfb:"",qtzfpay:0,paynoteno_qtzf:""}; //重置支付标记
				if(online_pay.zfb==1){
					checkskypayinfo(0);
				}
				if(online_pay.wx==1){
					checkskypayinfo(1);
				}
				if(online_pay.qtzf==1){
					checkskypayinfo(qtpayfs);
				}
				$('input[type=text]').attr("autocomplete","off");
				$('#wspayd').dialog('open');
				wssumpay();
			}
		}
	});
}
function getwsactpay(){
	var totalpay = $('#wspayd #totalpay').val()==""||$('#wspayd #totalpay').val()=="0"?0.00:Number($('#wspayd #totalpay').val());
	var freepay =  $('#wspayd #freepay').val()==""||$('#wspayd #freepay').val()=="0"?0.00:Number($('#wspayd #freepay').val());
	var jfcurr = $('#wspayd #jfcurr').val()==""||$('#wspayd #jfcurr').val()=="0"?0.00:Number($('#wspayd #jfcurr').val());
	var yhjcurr = $('#wspayd #yhjcurr').val()==""||$('#wspayd #yhjcurr').val()=="0"?0:Number($('#wspayd #yhjcurr').val());
	var actpay = 0;
	if((totalpay -jfcurr - freepay > yhjcurr&&totalpay -jfcurr - freepay>0) || totalpay -jfcurr - freepay<0)
		actpay = totalpay -jfcurr - freepay - yhjcurr ;
	$('#wspayd #actpay').val(actpay.toFixed(2));
	wssumpay();
}
//得到支付记录
function getwaresalepay() {
	var noteno = $('#waresaleud #unoteno').val();
	$('.paytr').detach();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "getwaresalepaye",
			noteno: noteno
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var rows = data.PAYLIST;
				var i = 1;
				var sumpay = 0;
				for (var row in rows) {
					var tr = document.createElement("tr");
					tr.className = "paytr";
					var td1 = document.createElement("td");
					td1.align = "right";
					td1.className = "header";
					td1.style.width = "47px";
					var td2 = document.createElement("td");
					td2.align = "right";
					var inputt = document.createElement("input");
					inputt.className = "hig25 wid-in-td";
					var label = document.createElement("label");
					inputt.type = "text";
					label.innerHTML = rows[row].PAYNAME;
					inputt.readOnly = true;
					inputt.style.textAlign = "right";
					inputt.value = Number(rows[row].CURR).toFixed(2);
					td1.appendChild(label);
					td2.appendChild(inputt);
					tr.appendChild(td1);
					tr.appendChild(td2);
					$('#paywaym').before(tr);
					i = i + 1;
				}
			}
			var yhjcurr = Number(data.YHJCURR).toFixed(2);
			var jfcurr = Number(data.JFCURR).toFixed(2);
			if(yhjcurr>0){
				$("#wspaydetaild #yhqdivm").show();
				$("#wspaydetaild #yhjcurrm").val(yhjcurr);
			}else{
				$("#wspaydetaild #yhqdivm").hide();
				$("#wspaydetaild #yhjcurrm").val(0);
			}
			if(jfcurr>0){
				$("#wspaydetaild #jfcurrm").val(jfcurr);
			}else{
				$("#wspaydetaild #centdivm").hide();
				$("#wspaydetaild #jfcurrm").val(0);
			}
			$('#wspaydetaild #totalpay').val(Number(data.TOTALCURR).toFixed(2));
			$('#wspaydetaild #freepay').val(Number(data.FREECURR).toFixed(2));
			$('#wspaydetaild #sumpaym').val(Number(data.ZPAYCURR).toFixed(2));
			$('#wspaydetaild #actpaym').val(Number(data.CHECKCURR).toFixed(2));
			$('#wspaydetaild #changecurrm').val(Number(data.CHANGECURR).toFixed(2));
		}
	});
}
//求结账合计
var paydata;
function wssumpay() {
	var actpay = Number($('#wspayd #actpay').val());
	var json = [];
	var sum = 0;
	var count = 0;
	for (var i = 1; i <= $("#wspayd input.paycurr").length; i++) {
		var pay = $('#wspayd #pay' + i).val()==""||Number($('#wspayd #pay' + i).val())==0||Number($('#wspayd #pay' + i).val())==NaN?0:Number($('#wspayd #pay' + i).val());
		if (pay!=0) {
			sum = sum + pay;
			var payid = $('#wspayd #payid' + i).val();
			var payno = $('#wspayd #payno' + i).val();
			json[count] = {
				"payid" : payid,
				"payno" : payno,
				"paycurr" : pay
			};
			count = count + 1;	
		}
	}
	paydata = json;
	sum==NaN?$('#wspayd #sumpay').val("0.00"):$('#wspayd #sumpay').val(sum);
	var changecurr = sum - actpay;
	$('#wspayd #changecurr').val(Number(changecurr).toFixed(2));
	checksendmsg();
}
function updatewaresaleh(print) {
	var id = $('#uid').val();
	var guestid = $('#waresaleud #uguestid').val();
	var houseid = $('#waresaleud #uhouseid').val();
	var noteno = $('#waresaleud #unoteno').val();
	var totalcurr = Number($('#wspayd #totalpay').val());
	var jfcurr = $('#wspayd #jfcurr').val()==""||$('#wspayd #jfcurr').val()=="0"?0.00:Number($('#wspayd #jfcurr').val());
	var changecurr = Number($('#changecurr').val());
	if(changecurr>=100){
		alerttext('找零金额不允许大于100！');
		return;
	}
	var notedatestr = $('#waresaleud #unotedate').val();
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
		return;
	}
	var freecurr = $('#wspayd #freepay').val()==''||$('#wspayd #freepay').val()=='0'?0.00:Number($('#wspayd #freepay').val());
	var usecent = $('#wspayd #usecent').val()==''||$('#wspayd #usecent').val()=='0'?0:Number($('#wspayd #usecent').val());
	var checkcurr = Number($('#wspayd #actpay').val());
	var zpaycurr = Number($('#wspayd #sumpay').val());
	var yhjcurr = Number($('#yhjcurr').val());
	if(totalcurr-freecurr-jfcurr<yhjcurr&&checkcurr==0)
		yhjcurr = totalcurr-freecurr-jfcurr;
	var vcpid = Number($('#yhjcurr :selected').data("vcpid"));
	if(Number($('#wspayd .payw').val())>0){
		if(skypayedbj.wxpay==0){
			alerttext('微信支付未完成，请重新支付！');
			return;
		}
	}
	if(Number($('#wspayd .payz').val())>0){
		if(skypayedbj.zfbpay==0){
			alerttext('支付宝支付未完成，请重新支付！');
			return;
		}
	}
	var errorpaymsg = "";
	$("#wspayd .payqtzf").each(function(){
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
	if (Number(zpaycurr)<Number(checkcurr)&&checkcurr>=0) {
		alerttext('收款合计小于应结金额，不允许提交');
	}else if(checkcurr<0&&Number(zpaycurr - changecurr) < Number(checkcurr)){
		alerttext('退款合计大于实退金额，不允许提交');
	} else {
		alertmsg(2);
		$.ajax({
					type : "POST", //访问WebService使用Post方式请求 
					url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data : {
						erpser : "updatewaresalehbyid",
						noteno : noteno,
						id : id,
						statetag: 1,
						guestid : guestid,
						houseid : houseid,
						totalcurr : totalcurr,
						freecurr : freecurr,
						checkcurr : checkcurr,
						zpaycurr : zpaycurr,
						changecurr : changecurr,
						notedatestr : notedatestr,
						usecent : usecent,
						jfcurr : jfcurr,
						yhjcurr: yhjcurr,
						vcpid: vcpid,
						paynoteno_wx: skypayedbj.paynoteno_wx,
						paynoteno_zfb: skypayedbj.paynoteno_zfb,
						paylist : JSON.stringify(paydata)
					}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType : 'json',
					success : function(data) { //回调函数，result，返回值 
						if(valideData(data)) {
							if(print){
								$('#waresaleud #print').click();
							}
							getnotedata();
							$('#waresaleud #updatingbar').hide();
							$('#waresaleud #ispayedbar').show();
							$('#wspayd').dialog('close');
						}
					}
				});
	}
}
function wswithdraw() {
	var noteno = $('#waresaleud #unoteno').val();
	var id = $('#uid').val();
	$.messager.confirm(dialog_title,'您确定退单吗？',function(r){    
		if (r){ 
		alertmsg(2);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "updatewaresalehbyid",
				noteno : noteno,
				statetag: 0,
				ntid:0,
				id : id
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				if(valideData(data)) {
					getnotedata();
					$('#waresaleud').dialog('close');
				}
	
			}
		});
	}
	});
}
var creditok = '0';
//打开收款窗口
function openwopay() {
	$('#wopayd #discpay').val('');
	$('#wopayd #sumpay').val('');
	$('#wopayd #nopay').val('');
	$('#wopayd #discpay').removeAttr('readonly');
	var amt = $('#wareoutud #waret').datagrid('getRows').length;
	if(amt==0){
		alert('商品明细不能为空！');
	}else{
	$('#wopayd').dialog({
		title : '结账',
		modal : true
	});
	var custid = $('#wareoutud #ucustid').val();
	getwopaywaylist(false);
	setTimeout("wosumpay();", 1000);
	$('#updatediv').show();
	$('#wopayd').dialog('open');
	}
}
//收款明细窗口
function openwopaydetail() {
	$('#wopaydetaild #discpaym').val('');
	$('#wopaydetaild #sumpaym').val('');
	$('#wopaydetaild #nopaym').val('');
	$('#wopaydetaild #paydetaild').dialog({
		title : '收款明细',
		modal : true
	});
	var custid = $('#wareoutud #ucustid').val();
	getwareoutpay();
	$('#wopaydetaild').dialog('open');
}
//生成支付列表行
var payline;
function getwopaywaylist(disabled) {
	$('#wopayd .paytr').detach();
	var noteno = $('#wareoutud #unoteno').val();
	var houseid = xsdpdz==1?$('#wareoutud #uhouseid').val():"";
	alertmsg(6);
	var online_pay = {wx:0,zfb:0,qtzf:0};
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "getwareoutcheck",
			houseid : Number(houseid),
			noteno : noteno
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) {
			if(valideData(data)){
				creditok = data.CREDITOK;
				var creditcurr = data.CREDITCURR;
				var balcurr = data.BALCURR;
				var totalcurr = data.TOTALCURR;
				var rows = data.PAYLIST;
				$('#wopayd #pastpay').val(Number(balcurr).toFixed(2));
				$('#wopayd #thispay').val(Number(totalcurr).toFixed(2));
				$('#wopayd #creditcurr').val((Number(creditcurr)-Number(balcurr)).toFixed(2));
				if(creditok=="1"){
					$('td.creditok').show();
				}else{
					$('td.creditok').hide();
				}
				payline = getJsonLength(rows);
				var i = 1;
				var tr = document.createElement("tr");
				var newtr = true;
				for (var row in rows) {
					if(newtr){
						tr = document.createElement("tr");
						tr.className = "paytr";
					}
					if(rows[row].PAYNO=='S'||rows[row].PAYNO=='R'||rows[row].PAYNO=='W'||rows[row].PAYNO=='Z'){
						if(getntid()==1){
							var td1 = document.createElement("td");
							var td2 = document.createElement("td");
							td1.align="right";
							td1.className="header";
							td2.align="right";
							var label = document.createElement("label");
							var inputt = document.createElement("input");
							var inputh = document.createElement("input");
							var inputh1 = document.createElement("input");
							var inputb =  document.createElement("input");
							inputt.type = "text";
							inputh.type = "hidden";
							inputh1.type = "hidden";
							inputb.type = "button";
							var payfs = 0;
							$(inputb).data('currid',"#pay" + i);
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
							} else if (rows[row].PAYNO == 'W') {
								$(inputb).data('payfs', 1);
								inputb.className = "paybtn payfs"+qtpayfs;
								inputt.className = "paycurr payw payfs1";
								online_pay.wx = 1;
							} else if (rows[row].PAYNO == 'Z') {
								$(inputb).data('payfs', 0);
								inputb.className = "paybtn payfs"+qtpayfs;
								inputt.className = "paycurr payz payfs0";
								online_pay.zfb = 1;
							}
							inputb.onclick = function() {
								var payfs = $(this).data('payfs');
								openskypay(payfs);
							};
							label.innerHTML = rows[row].PAYNAME;
							label.style.cursor = "pointer";
							label.onclick = function(){
								if($('#wopayd #pay'+pi+'[readonly]').length==0){
									$('#wopayd .paycurr:not([disabled],[readonly])').val("");
									var pi = this.id.replace("payname","");
									var actpay = Number($('#wopayd #actpay').val());
									var payedcurr = 0;
									for(var i=0;i<$('#wopayd .paycurr[readonly]').length;i++){
										payedcurr += Number($('#wopayd .paycurr[readonly]').eq(i).val());
									}
									$('#wopayd #pay'+pi).val((actpay-payedcurr).toFixed(2));
									wosumpay();
									$('#wopayd #pay'+pi).select().focus();
								}
							};
							label.id = "payname" + i;
							inputt.placeholder = "<输入>";
							inputt.id = "pay" + i; 
							inputt.style.width="105px";
							inputt.style.height="25px";
							inputb.className="btn2";
							inputb.style.width="50px";
							inputb.style.padding="2px";
							var actpay = Number($('#actpay').val());
							if(actpay>=0)
								inputb.value = "支付";
							else if(actpay<0)
								inputb.value = "退款";
							 $(inputt).change(function(){
								this.value=Number(this.value).toFixed(2);
								wosumpay();
							});;
							inputt.onkeyup = function(e) {
								if(this.value!="-"){
					        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
								}
								wosumpay();
							}
							inputt.onafterpaste = function() {
								if(this.value!="-"){
							    		this.value = this.value.replace(/[^-?\d.*$]/g,'');
								}
							};
							inputt.disabled = disabled;
							inputh.id = "payid" + i;
							inputh1.id = "payno" + i;
							inputh1.value = rows[row].PAYNO;
							inputh.value = rows[row].PAYID;
							td1.appendChild(label);
							td2.appendChild(inputb);
							td2.appendChild(inputt);	
							td2.appendChild(inputh);
							td2.appendChild(inputh1);
							tr.appendChild(td1);
							tr.appendChild(td2);
						}else continue;
					}else{
						var td1 = document.createElement("td");
						td1.align="right";
						var td2 = document.createElement("td");
						td2.align="right";
						var label = document.createElement("label");
						var inputt = document.createElement("input");
						var inputh = document.createElement("input");
						inputt.type = "text";
						inputh.type = "hidden";
						label.innerHTML = rows[row].PAYNAME;
						label.style.cursor = "pointer";
						label.onclick = function(){
							$('#wopayd .paycurr').val("");
							var pi = this.id.replace("payname","");
							var thispay = Number($('#wopayd #thispay').val());
							var discpay = Number($('#wopayd #discpay').val());
							var actpay = thispay - discpay;
							$('#wopayd #pay'+pi).val(actpay.toFixed(2));
							wosumpay();
							$('#wopayd #pay'+pi).select().focus();
						};
						label.id = "payname" +i;
						label.className = "header";
						inputt.id = "pay" + i;
						inputt.maxLength = 10; 
						inputt.className="wid160 hig25 paycurr";
						inputt.placeholder = "<输入>";
						inputt.onchange = function() {
							this.value=Number(this.value).toFixed(2);
						};
						inputt.onkeyup = function() {
							if(this.value!="-"){
				        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
								wosumpay();
							}
						};
						inputt.onafterpaste = function() {
							if(this.value!="-"){
				        		this.value = this.value.replace(/[^-?\d.*$]/g,'');
							}
						};
						inputt.disabled = disabled;
						inputh.id = "payid" + i;
						inputh.value = rows[row].PAYID;
						td1.appendChild(label);
						td2.appendChild(inputt);
						td2.appendChild(inputh);
						tr.appendChild(td1);
						tr.appendChild(td2);
					}
					if(newtr){
						newtr = false;
					}else{
						newtr = true;
						$('#wopayd #paylist').before(tr);
					}
					i = i + 1;
				}
				if(!newtr){
					$('#wopayd #paylist').before(tr);
				}
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
						checkskypayinfo(qtpayfs);
					}
				setTimeout(function() {
					wosumpay();
					$('#wopay #pay1').focus();
				}, 200);
			}
		}
	});
}
//得到支付记录
function getwareoutpay() {
	var noteno = $('#wareoutud #unoteno').val();
	$('#wopaydetaild .paymtr').detach();
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	var houseid = xsdpdz==1?$('#wareoutud #uhouseid').val():"0";
	alertmsg(6);
	var erpser = "getwareoutpaye";
	if(ntid==2) erpser = "getrefundoutpaye";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data : {
			erpser: "getwareoutpaye",
			houseid: Number(houseid),
			noteno : noteno
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) {
			if(valideData(data)){
				creditok = data.CREDITOK;
				var creditcurr = data.CREDITCURR;
				var balcurr = Number(data.BALCURR);
				var balcurr0 = Number(data.BALCURR0);
				var totalcurr = Number(data.TOTALCURR);
				var checkcurr = Number(data.CHECKCURR);
				var freecurr = Number(data.FREECURR);
				$('#wopaydetaild #pastpaym').val(balcurr.toFixed(2));
				$('#wopaydetaild #nextpastpaym').val(balcurr0.toFixed(2));
				$('#wopaydetaild #totalpaym').val(totalcurr.toFixed(2));
				$('#wopaydetaild #actpaym').val(checkcurr.toFixed(2));
				$('#wopaydetaild #discpaym').val(freecurr.toFixed(2));
				var rows = data.PAYLIST;
				var i = 1;
				var sumpay=0;
				for ( var row in rows) {
					var curr = Number(rows[row].CURR);
					if(rows[row].PAYNO=='*'){
						$('#wopaydetaild #discpaym').val(curr.toFixed(2));
					}else{
						var tr = document.createElement("tr");
						tr.className='paymtr';
						var td1 = document.createElement("td");
						var td2 = document.createElement("td");
						var inputt = document.createElement("input");
						var label = document.createElement("label");
						inputt.type = "text";
						label.innerHTML = rows[row].PAYNAME;
						inputt.readOnly = true;
						inputt.value = curr.toFixed(2);
						inputt.className="wid160 hig25";
						td1.align="right";
						label.className="header";
						td2.align="right";
						td1.appendChild(label);
						td2.appendChild(inputt);
						tr.appendChild(td1);
						tr.appendChild(td2);
						i = i + 1;
						sumpay=curr+sumpay;
						$('#wopaydetaild #paymlist').before(tr);
					}
			}
			$('#wopaydetaild #sumpaym').val(Number(sumpay).toFixed(2));
			}
			var actpay = Number($('#wopaydetaild #totalpaym').val())-Number($('#wopaydetaild #discpaym').val());
			var nopay = actpay-Number($('#wopaydetaild #sumpaym').val());
			$('#wopaydetaild #actpaym').val(Number(actpay).toFixed(2));
			$('#wopaydetaild #nopaym').val(Number(nopay).toFixed(2));
		}
	});
}
//求付款合计
var paydata;
function wosumpay() {
	var pastpay = Number($('#wopayd #pastpay').val());
	var thispay = Number($('#wopayd #thispay').val());
	var discpay = Number($('#wopayd #discpay').val());
	pastpay = isNaN(pastpay)?0:pastpay;
	thispay = isNaN(thispay)?0:thispay;
	discpay = isNaN(discpay)?0:discpay;
	var actpay = thispay - discpay;
	$('#wopayd #actpay').val(actpay.toFixed(2));
	var json = [];
	var sum = 0;
	var count = 0;
	paylist="";
	for (var i = 1; i <= $("#wopayd input.paycurr").length; i++){
		if ($('#wopayd #pay' + i).length>0) {
			var pay = Number($('#wopayd #pay' + i).val()==''?'0':$('#wopayd #pay' + i).val());
			sum = sum + pay;
			var payid = $('#wopayd #payid' + i).val();
			if(pay!=0){
				paylist += $('#wopayd #payname' + i).html()+":" + pay +",";
				json[count] = {
						"payid" : payid,
						"paycurr" : pay
					};
					count = count + 1;
			}
		}
	}
	paydata = json;
	$('#wopayd #sumpay').val(sum.toFixed(2));
	var balcurr = $('#wopayd #pastpay').val();
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	ntid = Number(ntid);
	var nextpay = Number(balcurr) + thispay - discpay - sum;
	if(ntid==2)
		nextpay = Number(balcurr) - (thispay - discpay - sum);
	$('#wopayd #nextpay').val(nextpay.toFixed(2));
	var nopay = thispay - discpay - sum;
	$('#wopayd #nopay').val(nopay.toFixed(2));
}
function updatewareouth(print) {
	var id = $('#uid').val();
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	var custid = $('#wareoutud #ucustid').val();
	var houseid = $('#wareoutud #uhouseid').val();
	var noteno = $('#wareoutud #unoteno').val();
	var paycurr0 = $('#wopayd #discpay').val()==""?"0":$('#wopayd #discpay').val();
	var totalcurr = $('#wopayd #thispay').val();
	var totalamt = $('#wareoutud #wtotalamt').val();
	var sumpay = $('#wopayd #sumpay').val();
	var nopay = $('#wopayd #nopay').val();
	var pastpay = $('#wopayd #pastpay').val();
	nopay =  isNaN(Number(nopay))?0:Number(nopay);
	pastpay =  isNaN(Number(pastpay))?0:Number(pastpay);
	sumpay = isNaN(Number(sumpay))?0:Number(sumpay);
	paycurr0 = isNaN(Number(paycurr0))?0:Number(paycurr0);
	var thispay = isNaN(Number($('#wopayd #thispay').val()))?"0":Number($('#wopayd #thispay').val());
	var checkcurr = thispay - paycurr0;
	var creditcurr = $('#wopayd #creditcurr').val();
	creditcurr = isNaN(Number(creditcurr))?"0":Number(creditcurr);
	var nextpay = $('#wopayd #nextpay').val();
	nextpay =  isNaN(Number(nextpay))?0:Number(nextpay);
	var notedatestr = $('#wareoutud #unotedate').val();
	if (notedatestr.length != 19) {
		alerttext("请选择单据日期!");
		return;
	}
		$.messager.confirm(dialog_title,'单据提交后禁止修改及删除，是否继续提交？',function(r){    
			if (r){ 
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "updatewareouthbyid",
					noteno : noteno,
					ntid : ntid,
					custid : custid,
					houseid : houseid,
					totalcurr : totalcurr,
					totalamt : totalamt,
					zpaycurr : sumpay,
					checkcurr : checkcurr,
					notedatestr : notedatestr,
					paycurr0 : paycurr0,
					statetag: 1,
					paynoteno_qtzf: skypayedbj.paynoteno_qtzf,
					paynoteno_wx: skypayedbj.paynoteno_wx,
					paynoteno_zfb: skypayedbj.paynoteno_zfb,
					paylist : JSON.stringify(paydata)
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if(valideData(data)) {
						if(print){
							$('#wareoutud #print').click();
						}
						getnotedata();
						$('#wareoutud #updatingbar').hide();
						$('#wareoutud #ispayedbar').show();
						$('#wopayd').dialog('close');
					}
				}
			});
		}
		});

}
function wowithdraw() {
	var noteno = $('#wareoutud #unoteno').val();
	var id = $('#uid').val();
	var ntid = $('.li-active').attr('id').replace("ntid-","");
		$.messager.confirm(dialog_title,'您确定退单吗？',function(r){    
			if (r){ 
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "updatewareouthbyid",
					noteno : noteno,
					id : id,
					statetag: 0,
					ntid : ntid
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if(valideData(data)) {
						getnotedata();
						$('#wareoutud').dialog('close');
					}
				}
			});
		}
		});
}
function wslwithdraw() {
	var istoday = $('#waresaleud #uistoday').val();
	var noteno = $('#waresaleud #unoteno').val();
	var id = $('#uid').val();
	if (istoday != '0') {
		$.messager.confirm(dialog_title,'您确定撤单吗？',function(r){    
			if (r){ 
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "wareouthcancel",
					noteno : noteno,
					id : id
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if(valideData(data)) {
						getnotedata();
						$('#waresaleud').dialog('close');
					}
				}
			});
		}
		});
	} else {
		alerttext('本单据不是当天单据，撤单只对当天单据有效！');
	}
}
function wotwithdraw() {
	var istoday = $('#wareoutud #uistoday').val();
	var noteno = $('#wareoutud #unoteno').val();
	var id = $('#uid').val();
	if (istoday != '0') {
		$.messager.confirm(dialog_title,'您确定撤单吗？',function(r){    
			if (r){ 
			alertmsg(2);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					erpser : "wareouthcancel",
					noteno : noteno,
					id : id
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'json',
				success : function(data) { //回调函数，result，返回值 
					if(valideData(data)) {
						getnotedata();
						$('#wareoutud').dialog('close');
					}
				}
			});
		}
		});
	} else {
		alerttext('本单据不是当天单据，撤单只对当天单据有效！');
	}
}
function getjfcurr(){
	if(Number(centbl)==0)
		alerttext("未设置积分抵扣，请到右上角参数设置里设置！");
	else{
		$('#wspayd #jfcurr').val(Number($('#wspayd #usecent').val())/centbl);
		getwsactpay();
	}
}
function setwareoutpaylist(){
	var rowdata = $('#gg').datagrid("getSelected");
	if(rowdata){
		if(rowdata.STATETAG=="1"){
			alertmsg(2);
			$.ajax({ 
		       	type: "POST",   //访问WebService使用Post方式请求 
		        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		        data: {erpser:"setwareoutpaylist",noteno:rowdata.NOTENO},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		        dataType: 'json', 
		        success: function(data) {     //回调函数，result，返回值 
		        	if(valideData(data)){
		        		if(data.result=="1"){
			        		$('#gg').datagrid('updateRow',{
									index : dqindex,
									row : {
										PAYLIST :data.msg
									}
								});	
			        		$('#gg').datagrid('refresh');
		        			
		        		}
					}
		        }                 
		        });	
		}
	}
}
function cashselprint(){
	var ntid = $('.li-active').attr('id').replace("ntid-","");
	switch (ntid) {
		case "0":
			pgid="pg1201";
			break;
		case "1":
			pgid="pg1302";
			break;
		case "2":
			pgid="pg1303";
			break;
		default:
			break;
		}
	selprint();
}

function listvipcoupon(){
	var houseid = Number($('#waresaleud #uhouseid').val());
	var guestid = Number($('#waresaleud #uguestid').val());
	var xfcurr = Number($('#wspayd #actpay').val());
	if(houseid<=0||isNaN(houseid)){
		alerttext("请选择店铺！");
		return;
	}
	if(guestid<=0||isNaN(guestid)){
		$('#wspayd #yhjcurr').val(0);
		$('#wspayd td.useyhq').hide();
		return;
	}
	$('#wspayd #yhjcurr .yhq').remove();
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listvipcoupon",
			houseid : houseid,
			guestid: guestid,
			xfcurr: xfcurr
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)) {
					var rows = data.rows;
					for(var i in rows){
						var row = rows[i];
						var html = '<option class="yhq" value="'+Number(row.CURR)+'" data-xfcurr="'
							+Number(row.XFCURR)+'" data-vcpid="'+Number(row.VCPID)+'">'+row.REMARK+'</option>';
						$('#wspayd #yhjcurr').append(html);
					}
				}
			}catch(e){
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
function checksendmsg(){
	if(useyhq==1){
		var usecent = Number($('#wspayd  #usecent').val());
		var payvcurr = Number($('.payvcurr').val());
		var yhjcurr = Number($('#yhjcurr').val());
		usecent = isNaN(usecent)?0:usecent;
		payvcurr = isNaN(payvcurr)?0:payvcurr;
		yhjcurr = isNaN(yhjcurr)?0:yhjcurr;
		if(usecent>0||payvcurr>0||yhjcurr>0){
			$('#wspayd #newask').val(1);
			$('#wspayd  #sendvipmsg').val("发送会员验证");
			$('#wspayd  #sendvipmsg').show();
			$('#wspayd #btn_pay,#wspayd #btn_pay_print').hide();
		}else{
			$('#wspayd #sendvipmsg').hide();
			$('#wspayd #btn_pay,#wspayd #btn_pay_print').show();
		}
	}
}
function sendvipmessage(){
	var noteno = $('#waresaleud #unoteno').val();
	var yhjcurr = Number($('#wspayd #yhjcurr').val());
	var xfcurr = Number($('#wspayd #yhjcurr :selected').data("xfcurr"));
	var usecent = Number($('#wspayd #usecent').val());
	var jfcurr = Number($('#wspayd #jfcurr').val());
	var payvcurr = Number($('#wspayd .payvcurr').val());
	var totalcurr = Number($('#wspayd #totalpay').val());
	var newask = Number($('#wspayd #newask').val());
	usecent = isNaN(usecent)?0:usecent;
	payvcurr = isNaN(payvcurr)?0:payvcurr;
	yhjcurr = isNaN(yhjcurr)?0:yhjcurr;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "sendvipmessage",
			noteno : noteno,
			yhjcurr: yhjcurr,
			xfcurr: xfcurr,
			usecent: usecent,
			jfcurr: jfcurr,
			payvcurr: payvcurr,
			totalcurr: totalcurr,
			newask: newask
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)) {
					var result = data.result;
					if(result>0){
						if(newask==0){
							if(result==2){
								alerttext(data.msg);
								$('#wspayd #sendvipmsg').val("发送会员验证");
								$('#wspayd #sendvipmsg').hide();
								$('#wspayd #btn_pay,#wspayd #btn_pay_print').show();
							}else if(result>=3){
								alerttext(data.msg);
								$('#wspayd #sendvipmsg').hide();
								$('#wspayd #usecent,#wspayd #jfcurr,#wspayd .payvcurr').removeProp("readonly");
								$('#wspayd #yhjcurr').removeProp('disabled');
							}
							$('#wspayd #newask').val(1);
						}else if(newask==1){
							alerttext(data.msg);
							$('#wspayd #sendvipmsg').val("获取验证结果");
							$('#wspayd #usecent,#wspayd #jfcurr,#wspayd .payvcurr').prop("readonly",true);
							$('#wspayd #yhjcurr').prop('disabled',true);
							$('#wspayd #newask').val(0);
						}
					}else
						alerttext(data.msg);
				}
			}catch(e){
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
</script>
<!-- 销售费用帮助列表 -->
<jsp:include page="../HelpList/SaleCost.jsp"></jsp:include>
	<!-- 端口帮助列表 -->
	<jsp:include page="../HelpList/PrintcsHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/PayHelp.jsp"></jsp:include>
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
</body>
</html>