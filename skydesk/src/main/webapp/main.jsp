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
<title>蓝窗商业智能ERP管理系统</title>
<link href="images/icon.ico" rel="icon" type="image/x-icon">
<!-- 加载js11 -->  
<script type="text/javascript" src="js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap-switch.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap-contextmenu.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/menutabs.js?ver=<%=tools.DataBase.VERSION%>"></script>
<link href="http://win.skydispark.com/skyasset/css/bootstrap/bootstrap.min.css?ver=<%=tools.DataBase.VERSION%>" rel="stylesheet" type="text/css">
<link href="http://win.skydispark.com/skyasset/css/bootstrap/bootstrap-switch.css?ver=<%=tools.DataBase.VERSION%>" rel="stylesheet" type="text/css">
<link href="css/icon.css?ver=<%=tools.DataBase.VERSION%>" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="css/skystyle.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
body,html{margin:0px;height:100%}
*{font-family: "Microsoft YaHei","微软雅黑";}
body{font-size:14px;color:#333;background-color:#f7f7f7;position: relative;}
input[disabled],select[disabled],textarea[disabled],input[readonly],select[readonly],textarea[readonly]{background-color:#fff}
.sky{text-align:left;text-decoration:none;margin-bottom:0;text-indent:10%}
/*s-标题栏*/
.panel-heading {
   	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#23baca,endColorStr=#1a8b98);/*IE678*/
   	background: -ms-linear-gradient(top,#23baca,#1a8b98);/* IE 10 */
   	background: -moz-linear-gradient(top,#23baca,#1a8b98);/*火狐*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#23baca), to(#1a8b98));/*谷歌*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#23baca), to(#1a8b98));/* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top,#23baca,#1a8b98);/*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top,#23baca,#1a8b98);/*Opera 11.10+*/
	height: 66px;
	line-height: 66px;
	color: white;
}

.user-box{float:right;margin-right:20px}
.user-box .user-name{font-size:14px;margin-right:25px;float:left}
.icon_flyang{background:url("images/logo.png") no-repeat 25px center;background-size:50px;behavior:url(css/backgroundsize.min.htc)}
.panel-left-title{font-size:18px;float:left}
.set-box,.msg-box{float:right;cursor:pointer;margin-left:10px;position:relative}
.set-box{background:url("images/btn-img.png") no-repeat -25px -6px}
.msg-box{background:url("images/msgicon.png") no-repeat center left}
.set-box:hover .dropdown-menu.pull-right{display:block}
.set-box:hover .dropdown-menu::before{content:"";width:0px;height:0px;border-left:10px solid transparent;border-right:10px solid transparent;border-bottom:10px solid #fff;position:absolute;right:15px;top:-9px}
.set-box,.msg-text{font-size:14px}
.msg-box.show .msg-panel{display:block}
.msg-box.show .msg-panel:before{content:"";width:0px;height:0px;border-left:10px solid transparent;border-right:10px solid transparent;border-bottom:10px solid #fff;position:absolute;right:85px;top:-9px}
.msg-panel{display:none;position:absolute;top:55px;left:-50px;border:1px #eee solid;-webkit-border-radius:3px;-moz-border-radius:3px;border-radius:3px;-webkit-box-shadow:0px 0px 10px #aaa;-moz-box-shadow:0px 0px 10px #aaa;box-shadow:0px 0px 10px #aaa;width:180px;background:#fff;z-index:1}
.sky-msgul{list-style:none;margin-top:10px;overflow:auto;height:240px;cursor:default;margin:0}
.sky-msgul li{margin:3px 10px;cursor:pointer;color:#666;line-height:30px;font-size:14px;text-indent:10px}
.sky-msgul li:not(:LAST-CHILD){border-bottom:1px dashed #aaa}
.sky-msgul li:ACTIVE,.sky-msgul li:HOVER,.bar-btn:HOVER{color:#23b9cb}
.text_num{margin-left:35px;color:#ff6100}
.bottom-bar{height:35px;text-align:center;clear:both;line-height:35px;border-top:1px #eee solid;padding:3px}
.bar-btn{display:inline-block;width:70px;color:#666;background-position-x:0px !important}
/*e-标题栏*/
.menu-group .menu-heading{background-color:#23b9cb;height:35px;color:#fff;line-height:35px;text-indent:20px;font-size:16px;margin-bottom:1px;cursor:pointer}
.menu-group .actives{background:#004248}
.sky-group{border-width:0px 0px 1px 0px;margin-bottom:2px;border-bottom:1px solid white;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;margin:0px;}
.sky-heading{border-bottom:0;font-size:16px}
.sky-body{overflow:hidden;max-width:225px;min-width:130px;position:fixed;left:-1000px;}
.sky-group.open .sky-body{height:auto;border:1px solid #d7d7d7;background:#eaeaea;left:150px;box-shadow:3px 3px 10px rgba(0,0,0,0.3);-moz-box-shadow:3px 3px 10px rgba(0,0,0,0.3)}
.little_split_div{float:left;border-left:1px dashed #aaa;width:1px;height:15px;margin:6px 0}
.big_split_div{margin:5px;height:1px;width:215px;float:left;border-top:#aaa 1px dashed}
.sky-heading .sky-toggle{display:block;color:#323232;padding:8px 40px}
#allmenu .sky-group:hover{background:url('images/mainmenu/left.png') no-repeat 139px center}
#allmenu .sky-group{background:url('images/mainmenu/right.png') no-repeat 85% center}

.panel-header,.skypanel-body{border:none}
.skypanel-body{color:#323232;font-size:12px;background:transparent}
div#allmenu{overflow:hidden;position:relative}

div#favmenu{height:0;overflow:hidden}
div#fav-menu:hover #favmenu{height:auto;overflow:auto;max-height:580px;position:fixed;background:#eaeaea;z-index:1;left:150px;top:67px;max-width:350px;min-width:166px;box-shadow:3px 3px 10px rgba(0,0,0,0.3);-moz-box-shadow:3px 3px 10px rgba(0,0,0,0.3)}
div#mainpanel{float:right;overflow:hidden;position:absolute;left:160px;right:5px;top:2px;bottom:0px}
div#mainpanel.fullscreen{left:5px;top:5px}
div#menupanel{float:left;width:150px;position:absolute;z-index:3;background:#fff;bottom:3px;top:2px}
div#mupl{padding:1px;border:0 solid white;-moz-box-shadow:inset -1px 0px 8px #888;box-shadow:inset -1px 0px 8px #888;height:100%}

.sky-toggle{cursor:pointer}
.sky-inner{font-size:14px;margin:6px 10px;width:90px;float:left;background-color:#eaeaea;color:#323232;cursor:pointer;}
.sky-inner:last-of-type{border:none}
.sky-inner:hover  .add-icon{float:right;background:url('images/add-icon.png') center center no-repeat;width:16px;height:16px}
.sky-inner:hover .add-icon:hover{float:right;background:url('images/add-icon-hover.png') center center no-repeat;width:16px;height:16px}
.nav-tabs{border-bottom:1px solid #eaeaea}
.sky-inner .inner-title:hover,#favmenu .sky-inner:hover,#allmenu .sky-group:hover .sky-heading .sky-toggle,.acc-inner-hover{color:#ff7900}
big:hover{color:red}
.nav-tabs>li{float:left;margin-bottom:-1px}
.nav-tabs>li>a{background-color:#eaeaea}
.nav>li>a{background-color:#eaeaea}
.nav{margin-bottom:0px}
.nav-tabs>li>a,#li-split{margin-right:3px;line-height:16px;border-bottom:3px solid #eaeaea;border-radius:4px 4px 0 0;color:#323232;border-radius:0px 0px 0px 0px;-webkit-border-radius:0px 0px 0px 0px}
#li-split a:hover{margin-right:3px;line-height:16px;border-bottom:3px solid #eaeaea;border-radius:4px 4px 0 0;color:#fff;background:#23b9cb;border-radius:0px 0px 0px 0px;-webkit-border-radius:0px 0px 0px 0px}
a:FOCUS{text-decoration:none;outline-style:none}
a:active{text-decoration:none}
a:HOVER{text-decoration:none}
.nav>li>a:hover{color:#00585f;cursor:pointer;background-color:white;border-bottom-color:#23b9cb}
.nav-tabs > li.active > a,.nav-tabs > li.active > a:focus,.nav-tabs > li.active > a:hover{color:#23b9cb;cursor:pointer;background-color:white;border-bottom:2px solid #23b9cb}
.tab-pane{height:100%}
.badge{display:inline}
iframe{border:none;width:100%;height:100%}
.logo{background-image:url('images/logo.png')}
.panel-heading {
   	FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#23baca,endColorStr=#1a8b98);/*IE678*/
   	background: -ms-linear-gradient(top,#23baca,#1a8b98);/* IE 10 */
   	background: -moz-linear-gradient(top,#23baca,#1a8b98);/*火狐*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%,from(#23baca), to(#1a8b98));/*谷歌*/
	background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#23baca), to(#1a8b98));/* Safari 4-5, Chrome 1-9*/
	background: -webkit-linear-gradient(top,#23baca,#1a8b98);/*Safari5.1 Chrome 10+*/
	background: -o-linear-gradient(top,#23baca,#1a8b98);/*Opera 11.10+*/
	height: 66px;
	line-height: 66px;
	color: white;
}
.skypanel-body{clear:both;padding:0px;position: absolute;top: 66px;bottom: 0;right: 0;left: 0;}
div.loading-modal{z-index:1;background:#f7f7f7;display: none;}
div.tab-content#maincontent,div.loading-modal{overflow: hidden;position: absolute;bottom: 30px;right: 0px;top: 40px;left: 0px;}
.main-footer {width: 100%;height: 30px;text-align: center;float: right;background-color: white;border-top: 1px solid rgb(139, 139, 139);position: absolute;bottom: 0px;}
.version-div{position:absolute;bottom:10px;width:150px;color:#ff9700;font-size:12px;text-align: center;}
.left-split{height:38px;width:20px;float:left;background:url("css/images/tabs_icons.png") no-repeat 4px center;margin-right:5px;cursor:pointer}
.right-split{height:38px;width:20px;float:right;background:url("css/images/tabs_icons.png") no-repeat -14px center;margin-left:5px;border:solid 1px #eaeaea;cursor:pointer}
.menu-left-split{height:34px;width:14px;float:right;background:url("css/images/tabs_icons.png") no-repeat 0px center;margin-left:25px;border:solid 1px #eaeaea;position:absolute;right:-17px;top:-1px;cursor:pointer}
.menu-right-split{height:15px;width:20px;float:right;background:url("css/images/tabs_icons.png") no-repeat -14px center;margin-left:5px;margin-top:3px;position:relative;left:-6px;cursor:pointer}
.menu-split{width:30px;float:left;background-color:rgb(0,124,132);height:600px;display:none;cursor:pointer}
.menu-split .text{color:#fff;margin-left:8px;margin-top:3px;display:block;letter-spacing:10px}
.ul-helplist{list-style:none;position:absolute;overflow:auto;max-height:100px;width:217px;display:block;margin:0;border:#d7d7d7 1px solid}
.ul-helplist li{margin:3px}
.ul-helplist li:hover{background-color:#ff7900;color:#fff}
#chcodemodal .msgbox{color:red;font-size:14px;text-align:center;font-weight:500}
div.help-header{height:46px;line-height:46px;text-indent:;text-indent:10px;background:#23b9cb;color:#fff;font-size:16px}
.help-box{position:fixed;z-index:1041;width:520px;background:#fff;top:10%;left:50%;margin-left:-260px;height:433px}
.help-close{float:right;margin-right:10px;cursor:pointer}
.help-body{border:#d7d7d7 solid 1px;height:340px;overflow:auto}
.help-body ul{list-style:none;margin:10px 0 0 20px}
.help-body li:hover{background:#d7d7d7}
.help-body li{height:25px;line-height:25px;width:200px;cursor:pointer}
.help-footer{position:absolute;bottom:0px;border:solid #d7d7d7 1px;width:518px;height:45px;line-height:;line-height:45px}
#help-total{float:right;margin-right:10px}
.help-page li{float:left;list-style:none;width:18px;height:18px;text-align:center;border:1px solid #d7d7d7;line-height:18px;cursor:pointer;margin:5px}
.help-page .selected{background:#ff7900;color:#fff;border:none}
.help-page li:hover{background:#ff7900;color:#fff;border:none}
.help-page{position:absolute;bottom:0px;margin-left:0 0 0 10px}
.findbox{width:400px;margin:10px}
#form-param table td{padding: 5px;}
.menu_wxbarcode{margin:1px auto;color:#666;overflow:hidden;width:100%;position:absolute;height:150px;bottom:0px}
.icon_wxbarcode{background:url("images/mainmenu/wxbarcode.png") no-repeat center center;height:80px;width:100%;line-height:normal}
.bottom_text{font-size:12px;padding:3px;line-height:normal;text-align:center;color:#ff9700}
.newmsg:before{content:"";position:absolute;left:15px;top:20px;background:#ee2233;width:6px;height:6px;-webkit-border-radius:3px;-moz-border-radius:3px;border-radius:3px}
</style>
</head>
<body style="min-width: 1024px;min-height: 768px">
<div class="panel-heading">
	<div class="panel-left-title icon_flyang">
	<span style="margin-left: 90px;">SKYDispark 蓝窗商业智能ERP管理系统</span>
	</div>
	<div class="user-box">
	<span class="user-name">
		<span id="time">上午好！系统管理员(系统管理员)</span>
	</span>
	<div class="set-box">
	<span style="margin-left: 30px;">设置</span>
	<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dLabel" style="top:50px;text-align:left;">
	<li><a role="button" onclick="getcominfo()" href="#comModal" data-toggle="modal">企业账号&nbsp;&nbsp;&nbsp;<span id="menu-comid"></span></a></li>
	<li><a addTabs id="pgBalcurr" >账户余额&nbsp;&nbsp;&nbsp;<span id="menu-balcurr" ></span></a></li>
	<li><a role="button" onclick="getqxpublic()" href="#paramModal" data-toggle="modal">参数设置</a></li>
	<li class="divider"></li>
	<li><a role="button" onclick="getuserinfo()" href="#myModal" data-toggle="modal">查看用户信息</a></li>
	<li><a role="button" href="#chcodemodal" data-toggle="modal">修改用户密码</a></li>
	<li class="divider admin1"></li>
	<li class="admin1"><a addTabs id="pgmybuyer">我的经销商</a></li>
	<li class="admin1"><a addTabs id="pgmyseller">我的供应商</a></li>
	<li class="admin1"><a addTabs id="pg1905">操作日志</a></li>
	<li class="divider admin0"></li>
  	<li class="admin0"><a role="button" href="#ggdzrModal" data-toggle="modal" onclick="getcalcdate()">登账日设置</a></li>
  	<li class="admin0"><a role="button" href="#sjwhModal" data-toggle="modal" onclick="$('#sjwhmsg').html('');getcalcdate();">数据维护</a></li>
	<li class="divider"></li>
	<li><a role="button" href="#sugModal" data-toggle="modal">建议反馈</a></li>
	<li class="hide"><a>系统公告</a></li>
	<li><a>版本信息&nbsp;&nbsp;&nbsp;<span style="font-size:14px" id="version-span1">Ver<%=tools.DataBase.VERSION%></span></a></li>
	<li><a target="_blank" href="http://www.skydispark.com">关于我们</a></li>
	<li class="divider"></li>
	<li><a href="loginservlet?doservlet=cleardata" onclick="clearAllCookie();">清空登录信息</a></li>
	<li><a href="loginservlet?doservlet=logoff">切换用户</a></li>
	</ul>
	</div>
	<div class="msg-box">
	<span style="margin: 30px">通知</span>
	<!-- 消息 -->
	<div class="msg-panel">
	<ul class="sky-msgul">
	</ul>
	<div class="bottom-bar">
	<span class="bar-btn icon_refresh" style="border-right: 1px solid #eee;">
	刷新
	</span>
	<span class="bar-btn icon_remove">
	关闭
	</span>
	</div>
	</div>
	</div>
</div>
</div>
		<div class="skypanel-body">
<!-- 		<div class="menu-right-split" onclick="showmenu()"></div> -->
<!-- 		<div id="split" class="menu-split" onclick="showmenu()"> -->
			<div id="menupanel">
				<div class="panel" id="mupl">
					<div class="panel-menu-body" style="position: relative;">
								<div class="menu-group">
								<div class="menu-heading" id="all-h">
										全部功能
								</div>
								<div class="menu-body" id="allmenu">
								
								</div>
								</div>
						</div>
						</div>
						  <div class="menu_wxbarcode">
  <div class="icon_wxbarcode">
  </div>
  <div class="bottom_text">
  关注蓝窗店管家微信公众号<br/>了解新功能或优惠信息
  </div>
  </div>
						<div class="version-div"><span id="version-span2"></span></div>
					</div>
			<div id="mainpanel">
				<div class="loading-modal"></div>
				<div>
					<!--  Tabs head -->
					<div class="left-split" style="display: none;"></div>
					<div class="right-split" style="display: none;"></div>
					<div class="tab-title">
					<ul class="nav nav-tabs" role="tablist" id="maintabs" style="background-color: white;display: -moz-grid-group;">
						<li class="active" role="presentation" id="tab_tab_home" ><a role="tablist"
							aria-expanded="false" aria-controls="home" href="#home"  data-tabs="tab"
							data-toggle="tab">首页</a></li>
					</ul>
					</div>
				</div>
				<div class="tab-content" id="maincontent" style="overflow: hidden;">
					<div class="tab-pane active" id="home" role="tabpanel">
					<iframe src="" frameborder="0" id="iframe_home"></iframe></div>
				</div>
				<div class="main-footer">
				<div style="font-size: 12px; margin-top: 5px; margin-left: 32px;">
				<p style="color: rgb(255, 121, 0);">
				<span>客服热线：Tel:4008-270-686&nbsp;</span>
				<span>客服QQ:<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=3329713977&amp;site=qq&amp;menu=yes" target="_blank">3329713977</a>&nbsp;</span>
<!-- 				<span>技术支持:<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=3490679270&amp;site=qq&amp;menu=yes" target="_blank">3490679270</a>&nbsp;<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2728382094&amp;site=qq&amp;menu=yes" target="_blank">2728382094</a>&nbsp;</span> -->
				<span>产品网站： <a target="_blank" href="http://www.skydispark.com">http://www.skydispark.com</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;杭州枫杨科技有限公司 版权所有</span>
				</p>
				<div>
				</div>
				</div>
				</div>
			</div>
		</div>
	<!-- 设置菜单 -->
	<!-- 导航菜单 -->
	<div id="mmenu" style="left: 430px; top: 877px; position: absolute; z-index: 9999;">
          <ul class="dropdown-menu" role="menu">
              <li><a tabindex="-1" title="O">打开</a></li>
              <li><a tabindex="-1" title="A">添加到常用功能</a></li>
              <li class="divider"></li>
              <li><a tabindex="-1" title="C">关闭</a></li>
          </ul>
        </div>
	<!-- 收藏菜单 -->
<div id="fmenu" style="left: 430px; top: 877px; position: absolute; z-index: 9999;">
          <ul class="dropdown-menu" role="menu">
              <li><a tabindex="-1" title="O">打开</a></li>
              <li><a tabindex="-1" title="D">移除</a></li>
              <li class="divider"></li>
              <li><a tabindex="-1" title="C">关闭</a></li>
          </ul>
        </div>
        	<!-- tab菜单 -->
<div id="tabmenu" style="left: 430px; top: 877px; position: absolute; z-index: 9999;">
          <ul class="dropdown-menu" role="menu">
              <li><a tabindex="-1" title="R" id="tab_refresh">刷新</a></li>
              <li><a tabindex="-1" title="C" id="tab_close">关闭</a></li>
              <li class="divider"></li>
              <li><a tabindex="-1" title="CA" id="tab_closeothers">关闭其他页面</a></li>
          </ul>
        </div>
 <!-- 消息
<div class="sky-msgbox hide">
<div class="sky-msgtitle panel-heading">
<span>蓝窗提醒</span>
<span class="sky-msgclose">×</span></div>
<div class="sky-msgbody">
<ul class="sky-msgul">
</ul>
</div>
</div>
  -->

<script type="text/javascript">
var _baseversion = "<%=tools.DataBase.VERSION%>";
var imgurl = "https://jerp.skydispark.com:62220/img/";
var skyversion = "3.1.101";
var skyuser = getCookieObj("SKYSTR");
var balcurr = getValuebyName("BALCURR");
var validday = Number(skyuser.VALIDDAY); //有限天数
validday = isNaN(validday) ? 0 : validday;
var skyepid = Number(skyuser.EPID);
var xmtype = Number(skyuser.TAG); //项目类型
var macEnable = false;
var menuid;
var menucalss;
var port = document.location.host.indexOf("8080") > 0 ? true: false;
var versionname = port == false ? "": "(测试)";
var pagecount = Number(getValuebyName("PAGECOUNT") == "" ? "20": getValuebyName("PAGECOUNT"));
var hopentabid = "no";
var haction = "no";
//复制单据号
var fromnoteno = "";
//复制单据类型
var fromnoteid = 0;
function autokey() {
	jQuery('input:text:first').focus();
	var $inp = jQuery('input:text');
	$inp.bind('keyup',
	function(e) {
		var key = e.which;
		if (key == 13) {
			e.preventDefault();
			var fun = $(this).attr('title');
			if (fun != undefined && fun != '') {
				eval(fun);
			}
			var nxtIdx = $inp.index(this) + 1;
			jQuery(":input:text:eq(" + nxtIdx + ")").focus();
		}
	});
}
function isSucess(text) {
	if (text == 'sys') {
		var date = new Date();
		var hours = date.getHours();
		var m = date.getMinutes();
		if (hours == 0 && (m >= 0 && m <= 30)) alert('00:00-00:30为系统维护时间，请稍后重试！');
		else alert('签名异常，请重新登录！');
		top.window.location.href = "loginservlet?doservlet=logoff";
		return false;
	} else if (text == 'fwq') {
		alerttext('服务器请求超时，请稍后重试！');
		clbol = true;
		return false;
	} else if (text.indexOf('未设置批发默认客户') >= 0) {
		clbol = true;
		return false;
	} else if (text.indexOf('另外的设备登录') >= 0) {
		alert('您已在另外的设备登录，请重新登录！');
		top.window.location.href = "loginservlet?doservlet=logoff";
		return false;
	} else if (text.indexOf('{') == -1 && text != 't') {
		alerttext(text);
		clbol = true;
		return false
	} else if (text.indexOf('<') >= 0 || text.indexOf('{') >= 0 || text == "t") {
		alerthide();
		return true;
	} else {
		clbol = true;
		return false;
	}
}
function valideData(data,showErrMsg) {
	alerthide();
	if (data.syserror != undefined) {
		var date = new Date();
		var hours = date.getHours();
		var m = date.getMinutes();
		var errmsg = "";
		if (hours >=2 && hours<4 || (hours==4 && m <= 30))
			errmsg = "02:00-04:30为系统维护时间，请稍后登录重试！";
		else errmsg = data.syserror;
		var st = setTimeout(function(){
			top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
		},30000);
		$.messager.alert('警告',errmsg,"error",function(){
			top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
			clearTimeout(st);
		});
		return false
	} else if (data.result != undefined) {
		var res = Number(data.result);
		if(data.warning!=undefined&&data.warning.length>0) alerttext(data.warning);
		if (res > 0) {
			return true
		} else {
			if(showErrMsg!=false) 
				alerttext(data.msg);
			setTimeout(function() {
				return false
			},
			2000)
		}
	} else if (data.total != undefined) {
		return true
	}
}
function setnoright() {
	with(document.body) {
		oncontextmenu = function() {
			return false
		}
	}
}
var showmenutimer = null;
var hidemenutimer = null;
$(document).ready(function() {
	//确定下次进新界面还是老界面；
	var mainpageparam = getparam({
		pgid: "mainpage",
		usymbol: "mainpageparam"
	});
	var paramsarr = mainpageparam.split("");
	paramsarr.splice(0, 1, "0");
	mainpageparam = paramsarr.join("");
	mainpageparam += "0000000000";
	setparam({
		pgid: "mainpage",
		usymbol: "mainpageparam",
		uvalue: mainpageparam.substring(0, 10)
	});
	$('#version-span1').html('Ver' + skyversion);
	$('#version-span2').html('Ver&nbsp;' + skyversion + versionname);
	$('#menu-comid').html(getValuebyName("COMID"));
	$('#menu-balcurr').html(balcurr);
	var levelid = Number(getValuebyName("GLEVELID"));
	var qxcs = getValuebyName("QXCS");
	if (levelid != 0) {
		$('.admin').remove();
	}
	if (levelid != 0 && levelid != 4 && levelid != 5) {
		$('.admin1').remove();
	}
	if (levelid != 0 && levelid != 5) {
		$('.admin0').remove();
	}
	
	if (Number(levelid) == 1 && Number(levelid) > 5) {
		$('.tjqx').remove();
	}
	var _1 = qxcs.charAt(0);
	var _2 = qxcs.charAt(1);
	var _3 = qxcs.charAt(2);
	var _4 = qxcs.charAt(3);
	var _5 = qxcs.charAt(4);
	if (_5 == "1" && (levelid == 0 || levelid == 4 || levelid == 5 || levelid == 8)) {
		$("div.omqx,div.omqx div.sky-inner").show();
	} else $("div.omqx").remove();
	//getskymsg();
	setnoright();
	autokey();
	getusepg();
	var now = new Date();
	var hours = now.getHours();
	var timeValue = ((hours >= 12) ? "下午好！": "上午好！") + getValuebyName("EPNAME") + "(" + getValuebyName("LEVELNAME") + ")";
	$('#time').html(timeValue);
	settabmenu();
	$('#split').hide();
	var psw = getValuebyName("PSW") == "6BAD136AEF6902EB0D3C0E7666DD2DB7";
	if (psw) {
		$('#chcodemodal .msgbox').html("提示：密码过于简单，为了不影响正常使用，请重新修改密码！");
		$('#chcodemodal').modal("show");
		$('#chcodemodal').on('hide.bs.modal',
		function(e) {
			alert("密码不修改，无法正常使用！");
			top.window.location.href = "loginservlet?doservlet=logoff";
		});
	}
	if (validday > 0 && validday < 10) {
		alert("您的有效期还有" + validday + "天，为了不影响您的使用，请及时联系开发商！ ");
		allowuse = true;
	} else if (Number(balcurr) > 10) {
		allowuse = true;
	} else if (Number(balcurr) <= 10 && Number(balcurr) > 0) {
		allowuse = true;
		alerttext("您的枫杨果不足10个，为保证您的使用，请及时充值！");
	} else if (Number(balcurr) <= 0) {
		allowuse = false;
		if (levelid == '0') alert("您的枫杨果已不足，为保证您的使用，请充值！");
		else {
			alert("您的枫杨果已不足，为保证您的使用，请联系系统管理员及时充值！");
			window.location.href = "loginservlet?doservlet=logoff";
		}
	}
	$('#mainpanel').click(function(e) {
		$('.open').removeClass('open');
	});

	$('.dropdown,#allmenu .sky-group').hover(function() {
		if (showmenutimer != null) {
			clearTimeout(showmenutimer);
			showmenutimer = null;
		}
		if (hidemenutimer != null) {
			clearTimeout(hidemenutimer);
			hidemenutimer = null;
		}
		var $this = $(this);
		if (!$this.hasClass('open')) {
			if($this.hasClass("sky-group")){//只处理左边菜单
				var thistop = $this.offset().top;
				var $body = $this.find(".sky-body");
				var height = $body.height();
				var top = $body.offset().top;
				var bodyheight = $("body").height();
				if(bodyheight-thistop<=height){
					$body.css("top", bodyheight-height+"px");
				}else if(thistop-0.5*height<=66){
					$body.css("top", 66 +"px");
				}else if(height>32){
					$body.css("top", thistop-0.5*height+18+"px");
				}else{
					$body.css("top", thistop+1+"px");
				}
			}
			if ($('.dropdown.open,#allmenu .sky-group.open').length > 0) {
				showmenutimer = setTimeout(function() {
					$('.dropdown.open,#allmenu .sky-group.open').removeClass("open");
					$this.addClass("open");
				},
				300);
			} else {
				$('.dropdown.open,#allmenu .sky-group.open').removeClass("open");
				$this.addClass("open");
			}
		}
	},
	function() {
		if (hidemenutimer != null) {
			clearTimeout(hidemenutimer);
			hidemenutimer = null;
		}
		if (showmenutimer != null) {
			clearTimeout(showmenutimer);
			showmenutimer = null;
		}
		var $this = $('.dropdown.open,#allmenu .sky-group.open');
		if ($this.length > 0) {
			hidemenutimer = setTimeout(function() {
				$this.removeClass("open");
			},
			500);
		}
	});
	$(document).ajaxStart(function() {
		var LoginEpid = Number(getValuebyName('EPID'));
		if (top.skyepid != LoginEpid) {
			alert("账户数据已过期（一台电脑只能同时登录一个账户），请重新登录！");
			top.window.location.href = "loginservlet?doservlet=logoff";
		}
	});
	if (port) alert("尊敬蓝窗店管家客户，您目前使用的是蓝窗店管家测试版，为了有更好的用户体验，请尽快联系蓝窗客服QQ3329713977，更换成正式版！");
	$("body").keyup(function(e) {
		var k = e.which;
		if (k == 122) setfullscreen();
	});
	
	$('.sky-msgul').delegate("li","click",function(e){
		var classname = $(this).attr('class');
		switch (classname) {
		case "jxssq":
			top.addTabs({
				id : "pgmybuyer",
				title :"我的经销商",
				close : true
			});
			break;
		case "gyssq":
			top.addTabs({
				id : "pgmyseller",
				title :"我的供应商",
				close : true
			});
			break;
		case "jxsdd":
			top.addTabs({
				id : "pg1301",
				title :"客户订单",
				close : true
			});
			break;
		case "gysfh":
			top.addTabs({
				id : "pg1102",
				title :"采购入库",
				close : true
			});
			break;
		case "ztdbdd":
			top.addTabs({
				id : "pg1403",
				title :"调拨入库",
				close : true
			});
			break;

		default:
			break;
		}
		return false;
	});
	$('.icon_refresh').click(function(){
		getskymsg();
	});
	$('.icon_remove').click(function(){
		$("div.msg-box").removeClass("show");
	});
	$("div.msg-box").hover(function(){
		$("div.msg-box").addClass("show");
	},function(){
		$("div.msg-box").removeClass("show");
	});
	getskymsg();
	getMainPage();
});
//获取操作文档
function gethelppdf() {
	addTabs({
		id: "pghelp",
		title: "操作手册",
		close: true
	});
}
function getversionlog() {
	addTabs({
		id: "pgversion",
		title: "版本更新日志",
		close: true
	});
}
var buyernum = 0;
var sellernum = 0;
var jxsordnum = 0;
var gysfhdnum = 0;
var ztdcdnum = 0;
function getskymsg(){
// 	MESSNUM:新公告数
// 	BUYERNUM:买家申请数
// 	SELLERNUM:卖家申请数
// 	BALCURR:枫杨果余额
// 	JXSORDNUM:经销商订货单数
// 	GYSFHDNUM:供应商发货单数
// 	ZTDCDNUM:在途调拨出库单数
	$('.sky-msgul').html("");
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data : {
			erpser:"messstate"
		}, //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType : 'json',
		success : function(data) {
			if(valideData(data)){
				var rows = data.rows;
				var row = rows[0];
				messnum = row.MESSNUM;
				buyernum = row.BUYERNUM;
				sellernum = row.SELLERNUM;
				jxsordnum = row.JXSORDNUM;
				gysfhdnum = row.GYSFHDNUM;
				ztdcdnum = row.ZTDCDNUM;
				html = "";
				if(Number(messnum)>0){
				//	html += '<li>系统公告'+ Number(messnum) + '条</li>';
				}
				if(Number(buyernum)>0){
					html += '<li class="jxssq">经销商申请<span class="text_num">'+ Number(buyernum) + '条</span></li>';
				}
				if(Number(sellernum)>0){
					html += '<li class="gyssq">供应商申请<span class="text_num">'+ Number(sellernum) + '条</span></li>';
				}
				if(Number(jxsordnum)>0){
					html += '<li class="jxsdd">经销商订单<span class="text_num">'+ Number(jxsordnum) + '笔</span></li>';
				}
				if(Number(gysfhdnum)>0){
					html += '<li class="gysfh">供应商发货<span class="text_num">'+ Number(gysfhdnum) + '笔</span></li>';
				}
				if(Number(ztdcdnum)>0){
					html += '<li class="ztdbdd">在途调拨单<span class="text_num">'+ Number(ztdcdnum) + '笔</span></li>';
				}
				if(html!=""){
					$('.sky-msgul').append(html);
					$('.msg-box').addClass('newmsg');
				}else{
					$('.sky-msgul').html("<li>---暂无新通知---</li>");
					$('.msg-box').removeClass('newmsg');
				}
				$("div.msg-box").addClass("show");
				setTimeout(function(){
					$("div.msg-box").removeClass("show");
				}, 3000);
			}
		}
	});
}

function getusepg() {
	alertmsg(6);
	var gysbj = Number(getValuebyName('PROVID')) == -1 ? 0 : 1;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "listsysnamebyuseridpc"
		},
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) {
			if (valideData(data)) {
				var rows = data.rows;
				var rl = rows.length;
				for (var i = 0; i < rl; i++) {
					var row = rows[i];
					var sysid = row.SYSID;
					var sysname = row.SYSNAME;
					var proglist = row.proglist;
					var pl = proglist.length;
					var $group = $('<div class="sky-group" sysid="'+sysid+'"></div>');
					$("#allmenu").append($group);
					var $head = $('<div class="sky-heading" style="background:url('+imgurl+'/sysid'+sysid+'.png) no-repeat 10px center;background-size: 25px;"></div>');
					$group.append($head);
					var $headtext = $('<a class="sky-toggle" href="#pgele'+sysid+'">'+sysname+'</a>');
					$head.append($headtext);
					var $body = $('<div class="sky-body" id="pgele'+sysid+'"></div>');
					$group.append($body);
					for (var j = 0; j < pl; j++) {
						var prog = proglist[j];
						var pgid= prog.PROGID;
						var pgname = prog.PROGNAME;
						var $el = $('<div addtabs class="sky-inner" id="pg'+pgid+'"><span class="inner-title">'+pgname+'</span></div>');
						$body.append($el);
					}
				}
			}
		}
	});
}
function wrtiejsErrorLog(e) {
	var msg = "";
	if (typeof(e) == "string") msg = e;
	else msg = e.stack;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "logservice",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			logser: "writelog",
			epid: getValuebyName("COMID") + "--" + getValuebyName("EPNO"),
			msg: msg
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
		}
	});
}
function setfullscreen() {
	if (top.$("div#mainpanel").hasClass("fullscreen")) {
		top.$("div#menupanel,div#mpl .panel-heading").show();
		top.$("div#mainpanel").removeClass("fullscreen");
	} else {
		$.messager.show({
			msg: "按F11退出全屏模式",
			timeout: 1500,
			showType: 'show',
			height: 60,
			style: {
				right: '',
				top: document.body.scrollTop + document.documentElement.scrollTop,
				bottom: ''
			}
		});
		top.$("div#mainpanel").addClass("fullscreen");
		top.$("div#menupanel,div#mpl .panel-heading").hide();
	}
}
function getservertime() {
	var serverdatetime = "";
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "fyjerp",
		async: false,
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "serverdatetime"
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			serverdatetime = data.SERVERDATETIME;
		}
	});
	if (serverdatetime == undefined || serverdatetime.length == 0) serverdatetime = new Date().getTime();
	return serverdatetime.replace(/-/g, "/");
}
function getMainPage(){
// 	A: 0=系统管理员,3=财务,4=经理,5=老板,6=督导
// 	B: 1=店员,2=店长7=收银员
// 	C: 8=AD客服,9=库管
	var levelid = Number(getValuebyName("GLEVELID"));
	var mainurl = "MainPage.html";
	switch (levelid) {
	case 0:
	case 3:
	case 4:
	case 5:
	case 6:
		mainurl = "MainPage.html";
		break;
	case 1:
	case 2:
	case 7:
		mainurl = "MainPage3.html";
		break;
	default:
		mainurl = "MainPage2.html";
		break;
	}
	$("#iframe_home").attr("src",mainurl);
}
function refreshMainPage(){
	document.getElementById("iframe_home").contentWindow.location.reload(true);
}

</script>
<!-- 用户面板 -->
	<jsp:include page="updateuserinfo.jsp"></jsp:include>
	<jsp:include page="ccodemodal.jsp"></jsp:include>
</body>
</html>