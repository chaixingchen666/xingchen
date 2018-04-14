<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>蓝窗商业智能ERP管理系统</title>
<link href="images/icon.ico" rel="icon" type="image/x-icon">
<link href="http://win.skydispark.com/skyasset/css/bootstrap/bootstrap.min.css?ver=<%=tools.DataBase.VERSION%>" rel="stylesheet" type="text/css">
<link href="http://win.skydispark.com/skyasset/css/bootstrap/bootstrap-switch.css?ver=<%=tools.DataBase.VERSION%>" rel="stylesheet" type="text/css">
<link href="css/icon.css?ver=<%=tools.DataBase.VERSION%>" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="css/skystyle.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
html{margin:0;padding:0;height:100%;width:100%}
body{width:100%;height:100%;font-family: 微软雅黑;background: #f2f2f2;}
*{padding:0;margin:0;list-style: none;}
.show{display:block}
.hide{display:none}
a{
	text-decoration: none;
}
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
.user-box {
    float: right;
    margin-right: 20px;
}
.user-box .user-name {
    font-size: 14px;
    margin-right: 25px;
    float: left;	
}
.icon_flyang{
	background: url("images/logo.png") no-repeat 25px center;
	background-size: 50px;
	behavior: url(css/backgroundsize.min.htc);
}
.panel-left-title{
	font-size:18px;
	float:left;
}
.set-box,.msg-box {
    float: right;
    cursor: pointer;
    margin-left: 10px;
    position: relative;
}
.set-box{
	background: url("images/btn-img.png") no-repeat -25px -6px;
}
.msg-box{
	background: url("images/msgicon.png") no-repeat center left;
}
.set-box:hover .dropdown-menu.pull-right{
	display: block;
}
.set-box:hover .dropdown-menu::before {
    content: "";
    width: 0px;
    height: 0px;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 10px solid #fff;
    position: absolute;
    right: 15px;
    top: -9px;
}

.set-box,.msg-text{
	font-size: 14px;
}
.msg-box:hover .msg-panel{
	display: block;
}
.msg-box:hover .msg-panel:before{
	content: "";
    width: 0px;
    height: 0px;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 10px solid #fff;
    position: absolute;
    right: 85px;
    top: -9px;
}
.msg-panel {
    display: none;
    position: absolute;
    top: 55px;
    left: -50px;
    border: 1px #eee solid;
    -webkit-border-radius: 3px;
  	-moz-border-radius: 3px;
  	border-radius: 3px;
  	-webkit-box-shadow: 0px 0px 10px #aaa;
  	-moz-box-shadow: 0px 0px 10px #aaa;
 	box-shadow: 0px 0px 10px #aaa;
    width: 180px;
    background: #fff;
    z-index: 1;
}

.sky-msgul {
    list-style: none;
    margin-top: 10px;
    overflow: auto;
    height: 240px;
    cursor: default;
    margin: 0;
}

.sky-msgul li {
    margin: 3px 10px;
    cursor: pointer;
    color: #666;
    line-height: 30px;
    font-size: 14px;
    text-indent: 10px;
}
	
.sky-msgul li:not(:LAST-CHILD){
    border-bottom: 1px dashed #aaa;
}

.sky-msgul li:ACTIVE,.sky-msgul li:HOVER,.bar-btn:HOVER{
	color: #23b9cb;
}
.text_num {
    margin-left: 35px;
    color: #ff6100;
}
.bottom-bar {
    height: 35px;
    text-align: center;
    clear: both;
    line-height: 35px;
    border-top: 1px #eee solid;
    padding: 3px;
}

.bar-btn {
    display: inline-block;
    width: 70px;
    color: #666;
    background-position-x: 0px !important;
}
/*e-标题栏*/
/*s-bootstrap-menu*/
.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: none;
  float: left;
  min-width: 160px;
  padding: 5px 0;
  margin: 2px 0 0;
  list-style: none;
  background-color: #fff;
  border: 1px solid #ccc;
  border: 1px solid rgba(0,0,0,0.2);
  *border-right-width: 2px;
  *border-bottom-width: 2px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  -webkit-box-shadow: 0 5px 10px rgba(0,0,0,0.2);
  -moz-box-shadow: 0 5px 10px rgba(0,0,0,0.2);
  box-shadow: 0 5px 10px rgba(0,0,0,0.2);
  -webkit-background-clip: padding-box;
  -moz-background-clip: padding;
  background-clip: padding-box
}

.dropdown-menu.pull-right {
  right: 0;
  left: auto
}

.dropdown-menu .divider {
  *width: 100%;
  height: 1px;
  margin: 9px 1px;
  *margin: -5px 0 5px;
  overflow: hidden;
  background-color: #e5e5e5;
  border-bottom: 1px solid #fff
}

.dropdown-menu>li>a {
  display: block;
  padding: 3px 20px;
  clear: both;
  font-weight: normal;
  line-height: 20px;
  color: #333;
  white-space: nowrap
}

.dropdown-menu>li>a:hover,.dropdown-menu>li>a:focus,.dropdown-submenu:hover>a,.dropdown-submenu:focus>a {
  color: #fff;
  text-decoration: none;
  background-color: #ff7900;
}

.dropdown-menu>.active>a,.dropdown-menu>.active>a:hover,.dropdown-menu>.active>a:focus {
  color: #fff;
  text-decoration: none;
  background-color: #0081c2;
  background-image: -moz-linear-gradient(top,#08c,#0077b3);
  background-image: -webkit-gradient(linear,0 0,0 100%,from(#08c),to(#0077b3));
  background-image: -webkit-linear-gradient(top,#08c,#0077b3);
  background-image: -o-linear-gradient(top,#08c,#0077b3);
  background-image: linear-gradient(to bottom,#08c,#0077b3);
  background-repeat: repeat-x;
  outline: 0;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff0088cc',endColorstr='#ff0077b3',GradientType=0)
}

.dropdown-menu>.disabled>a,.dropdown-menu>.disabled>a:hover,.dropdown-menu>.disabled>a:focus {
  color: #999
}

.dropdown-menu>.disabled>a:hover,.dropdown-menu>.disabled>a:focus {
  text-decoration: none;
  cursor: default;
  background-color: transparent;
  background-image: none;
  filter: progid:DXImageTransform.Microsoft.gradient(enabled=false)
}

.open {
  *z-index: 1000
}

.open>.dropdown-menu {
  display: block
}

.dropdown-backdrop {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 990
}

.pull-right>.dropdown-menu {
  right: 0;
  left: auto
}

.dropup .caret,.navbar-fixed-bottom .dropdown .caret {
  border-top: 0;
  border-bottom: 4px solid #000;
  content: ""
}

.dropup .dropdown-menu,.navbar-fixed-bottom .dropdown .dropdown-menu {
  top: auto;
  bottom: 100%;
  margin-bottom: 1px
}

.dropdown-submenu {
  position: relative
}

.dropdown-submenu>.dropdown-menu {
  top: 0;
  left: 100%;
  margin-top: -6px;
  margin-left: -1px;
  -webkit-border-radius: 0 6px 6px 6px;
  -moz-border-radius: 0 6px 6px 6px;
  border-radius: 0 6px 6px 6px
}

.dropdown-submenu:hover>.dropdown-menu {
  display: block
}

.dropup .dropdown-submenu>.dropdown-menu {
  top: auto;
  bottom: 0;
  margin-top: 0;
  margin-bottom: -2px;
  -webkit-border-radius: 5px 5px 5px 0;
  -moz-border-radius: 5px 5px 5px 0;
  border-radius: 5px 5px 5px 0
}

.dropdown-submenu>a:after {
  display: block;
  float: right;
  width: 0;
  height: 0;
  margin-top: 5px;
  margin-right: -10px;
  border-color: transparent;
  border-left-color: #ccc;
  border-style: solid;
  border-width: 5px 0 5px 5px;
  content: " "
}

.dropdown-submenu:hover>a:after {
  border-left-color: #fff
}

.dropdown-submenu.pull-left {
  float: none
}

.dropdown-submenu.pull-left>.dropdown-menu {
  left: -100%;
  margin-left: 10px;
  -webkit-border-radius: 6px 0 6px 6px;
  -moz-border-radius: 6px 0 6px 6px;
  border-radius: 6px 0 6px 6px
}

.dropdown .dropdown-menu .nav-header {
  padding-right: 20px;
  padding-left: 20px
}

/*e-bootstrap-menu*/
/*s-bootstrap-override*/
table {
  border-spacing: 5px;
  border-collapse: separate;
}
ul, ol,td p{
    padding: 0;
    margin: 0;
}
input[type="radio"],input[type="checkbox"] {
  margin: 0;
  margin-top: 1px \9;
  *margin-top: 0;
  line-height: normal
}

/*e-bootstrap-override*/

/* s-菜单*/
.menu_box{float:left;background:#fff;width:100px;text-align:center;position: absolute;top:70px;bottom: 0;z-index:3;-webkit-box-shadow: 0px 3px 10px #ddd;
  	-moz-box-shadow: 0px 3px 10px #ddd;
 	box-shadow: 0px 3px 10px #ddd;}
.menu_el{height:65px;margin:1px auto;background:#f7f7f7;cursor:pointer;color:#666;transition:background-color 0.5s ease-out;-webkit-transition-property:background-color;-webkit-transition-duration:0.3s;-webkit-transition-timing-function:ease-out}
.menu_el:HOVER{color:#23baca}
.menu_el_selected{background:#0092a2 !important;border-left:3px #fe7a00 solid;color:#fff !important}
.menu_el_selected.no-right{background:#0092a2 !important}
.menu_el_title{font-family:微软雅黑;font-size:14px}
.menu_el_icon{height:40px;margin:0 auto}
.icon_home{background:url("images/mainmenu/home.png") no-repeat center center}
.menu_el_selected .icon_home{background:url("images/mainmenu/home-hover.png") no-repeat center center}
.icon_warein{background:url("images/mainmenu/warein.png") no-repeat center center}
.menu_el_selected .icon_warein{background:url("images/mainmenu/warein-hover.png") no-repeat center center}
.icon_basic{background:url("images/mainmenu/basic.png") no-repeat center center}
.menu_el_selected .icon_basic{background:url("images/mainmenu/basic-hover.png") no-repeat center center}
.icon_omsubject{background:url("images/mainmenu/omsubject.png") no-repeat center center}
.menu_el_selected .icon_omsubject{background:url("images/mainmenu/omsubject-hover.png") no-repeat center center}
.icon_sale{background:url("images/mainmenu/sale.png") no-repeat center center}
.menu_el_selected .icon_sale{background:url("images/mainmenu/sale-hover.png") no-repeat center center}
.icon_warehouse{background:url("images/mainmenu/warehouse.png") no-repeat center center}
.menu_el_selected .icon_warehouse{background:url("images/mainmenu/warehouse-hover.png") no-repeat center center}
.icon_remove{background:url("images/mainmenu/remove.png") no-repeat center center}
.icon_refresh{background:url("images/mainmenu/refresh.png") no-repeat center center}
.icon_refresh:hover{background:url("images/mainmenu/refresh-hover.png") no-repeat center center}
.icon_remove:hover{background:url("images/mainmenu/remove-hover.png") no-repeat center center}
.menu_el{height:65px;margin:1px auto;background:#f7f7f7;cursor:pointer;color:#666;position:relative;transition:background-color 0.5s ease-out;-webkit-transition-property:background-color;-webkit-transition-duration:0.3s;-webkit-transition-timing-function:ease-out}
.menu_el_selected .menu_el_panel{display:block}
.menu_el_selected .menu_el_panel::before{content:"";width:0px;height:0px;position:absolute;left:-10px;top:42px;border-top:10px solid transparent;border-bottom:10px solid transparent;border-right:10px solid #fff}
.menu_el_panel{display:none;position:absolute;left:97px;top:-20px;color:#666;font-family:微软雅黑;padding:10px;
  	-webkit-box-shadow: 5px 5px 20px #eee;
  	-moz-box-shadow: 5px 5px 20px #eee;
 	box-shadow: 5px 5px 20px #eee;
border: 1px #ddd solid;
background: #fff;
z-index: 1;
}
.panel_header{height:35px;line-height:35px}
.panel_btn{font-size:14px;padding:3px;color:#999}
.panel_btn.yes{color:#23b9cc !important}
#comp_menu{float:left;display:none}
#add_menu,#edit_menu{float:right}
.panel_title{border-top:dashed 1px #aaa;border-bottom:dashed 1px #aaa;height:20px;line-height:20px;text-align:left;background:#f7f7f7;padding-left:5px;color:#999}
.panel_box{max-width:500px;min-width:250px}
.panel_body{clear:both;font-size:12px;cursor:default}
.panel_ul li{list-style:none;float:left;width:124px;height:30px}
div.no-border{border:none}
.panel_li_box{margin:5px 0;height:20px;line-height:20px;text-align:center;overflow:hidden;clear:both}
.panel_ul{overflow:hidden;zoom:1}
.li_title{float:left;width:80px;clear:both;text-align:left;padding-left:5px;cursor:pointer}
.li_btn{width:15px;height:15px;float:left;cursor:pointer;display:none}
.li_title:hover{color:#23b9cc}
.panel_btn:hover{color:#23b9cc}
.menu_wxbarcode {
    margin: 1px auto;
    color: #666;
    overflow: hidden;
	width: 100%;
    position: absolute;
    top: 400px;
    bottom: 0px;
    background: #f7f7f7;
}
.icon_wxbarcode {
    background: url("images/mainmenu/wxbarcode.png") no-repeat center center;
    position: absolute;
    height: 80px;
    width: 100%;
    bottom: 25px;
    line-height: normal;
}
.bottom_text {
    position: absolute;
    bottom: 0px;
    font-size: 8px;
    padding: 3px;
    line-height: normal;
}

/*e-菜单*/
/*s-功能-面板*/
#mainpanel {
    position: absolute;
    left: 110px;
    right: 0;
    top: 70px;
    bottom: 0;
}
.nav-tabs{
	border-bottom:1px solid #eaeaea;
	overflow: hidden;
	max-width: 100%;
	margin: 0;
}
.tab-pane {
    height: 100%;
    display: none;
}
div.active{
	display: block;
}
.nav-tabs > li {
    margin-bottom: 0;
}
.nav-tabs > li > a, #li-split {
    display: block;
    font-size:14px;
    margin-right: 3px;
    padding:5px 15px;
    line-height: 20px;
    height: 20px;
    border: 1px solid #d7d7d7;
    color: #323232;
    -webkit-border-radius: 0;
    -moz-border-radius: 0;
    border-radius: 0;
    cursor: pointer;
}

.nav-tabs > li {
    float: left;
}
.nav > li > a {
    background-color: #eaeaea;
}
.nav-tabs>.active>a,.nav-tabs>.active>a:hover,.nav-tabs>.active>a:focus {
	color: #23b9cb;
    background-color: white;
    border-bottom: 1px solid #23b9cb;
    cursor: pointer;
}

.nav-tabs > li > a:HOVER{
	color: #23b9cb;
	cursor: pointer;
}
.tab-title {
    position: absolute;
    left: 0px;
    right: 10px;
    top: 2px;
    background-color: #fff;
}
div.loading-modal{z-index:1;background:#f7f7f7;display: none;}
.tab-content,div.loading-modal{
	position: absolute;
	top: 40px;
	bottom: 10px;
	left: 0;
	right: 10px;
	overflow: hidden;
}
iframe {
    border: none;
    width: 100%;
    height: 100%;
    border: none;
}
.newmsg:before {
    content: "";
    position: absolute;
    left: 15px;
    top: 20px;
    background: #ee2233;
    width: 6px;
    height: 6px;
    -webkit-border-radius: 3px;
  	-moz-border-radius: 3px;
  	border-radius: 3px;
}

/*e-功能-面板*/
#form-param table td{padding: 5px;}
  </style>
</head>
<body>
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
	<li><a href="main.jsp">返回旧界面</a></li>
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
<div class="menu_box">
  <div class="menu_el no-right" data-pageid="0">
    <div class="menu_el_icon icon_home">
    </div>
    <div class="menu_el_title">
     首页
    </div>
  </div>
  <div class="menu_el" data-pageid="1">
    <div class="menu_el_icon icon_warein">
    </div>
    <div class="menu_el_title">
     采购
    </div>
 	<div class="menu_el_panel">
	<div class="panel_header">
<!--     <span class="panel_btn" id="edit_menu">编辑</span> -->
    <span class="panel_btn" id="add_menu">编辑</span>
    </div>
    <div class="panel_body">
    </div>
  	</div>
  </div>
  <div class="menu_el" data-pageid="2">
    <div class="menu_el_icon icon_sale">
    </div>
    <div class="menu_el_title">
     销售
    </div>
 	<div class="menu_el_panel">
	<div class="panel_header">
<!--     <span class="panel_btn" id="edit_menu">编辑</span> -->
    <span class="panel_btn" id="add_menu">编辑</span>
    </div>
    <div class="panel_body">

    </div>
  	</div>
  </div>
  <div class="menu_el" data-pageid="3">
    <div class="menu_el_icon icon_warehouse">
    </div>
    <div class="menu_el_title">
    仓库
    </div>
 	<div class="menu_el_panel">
	<div class="panel_header">
<!--     <span class="panel_btn" id="edit_menu">编辑</span> -->
    <span class="panel_btn" id="add_menu">编辑</span>
    </div>
    <div class="panel_body">
    </div>
  	</div>
  </div>
  <div class="menu_el" data-pageid="4">
    <div class="menu_el_icon icon_basic">
    </div>
    <div class="menu_el_title">
     基础
    </div>
 	<div class="menu_el_panel">
	<div class="panel_header">
<!--     <span class="panel_btn" id="edit_menu">编辑</span> -->
    <span class="panel_btn" id="add_menu">编辑</span>
    </div>
    <div class="panel_body">
    </div>
  	</div>
  </div>
    <div class="menu_el" data-pageid="5">
    <div class="menu_el_icon icon_omsubject">
    </div>
    <div class="menu_el_title">
     订货
    </div>
 	<div class="menu_el_panel">
	<div class="panel_header">
<!--     <span class="panel_btn" id="edit_menu">编辑</span> -->
    <span class="panel_btn" id="add_menu">编辑</span>
    </div>
    <div class="panel_body">
    </div>
  	</div>
  </div>
  <div class="menu_wxbarcode">
  <div class="icon_wxbarcode">
  </div>
<!--   <div class="bottom_text"> -->
<!--   关注蓝窗店管家微信公众号了解新功能或优惠信息 -->
<!--   </div> -->
  </div>
</div>
<!-- 功能面板 -->
<div id="mainpanel">
<div class="loading-modal"></div>
	<div style="padding: 0px;position: relative;">
		<!--  Tabs head -->
		<div class="left-split" style="display: none;"></div>
		<div class="right-split" style="display: none;"></div>
		<div class="tab-title">
		<ul class="nav nav-tabs" role="tablist" id="maintabs" style="background-color: white;display: -moz-grid-group;">
			<li class="active" role="presentation" id="tab_tab_home"><a role="tablist"
				aria-expanded="false" aria-controls="home" href="#home"  data-tabs="tab"
				data-toggle="tab">首页</a></li>
		</ul>
		</div>
	</div>
	<div class="tab-content" id="maincontent">
		<div class="tab-pane active" id="home" role="tabpanel">
		<iframe src="MainPage.html" frameborder="0" ></iframe></div>
	</div>
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
<script src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap-switch.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/bootstrap/bootstrap-contextmenu.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/menutabs.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript">

var skyversion = "3.1.92";
var skyuser = getCookieObj("SKYSTR");
var balcurr = getValuebyName("BALCURR");
var validday = Number(skyuser.VALIDDAY);//有限天数
validday = isNaN(validday)?0:validday;
var skyepid = Number(skyuser.EPID);
var macEnable = false;
var menuid;
var menucalss;
var port = document.location.host.indexOf("8080")>0?true : false;
var versionname = port == false?"":"(测试)";
var pagecount = Number(getValuebyName("PAGECOUNT")==""?"20":getValuebyName("PAGECOUNT"));
var hopentabid="no";
var haction="no";
//复制单据号
var fromnoteno = "";
//复制单据类型
var fromnoteid = 0;
//flyang对象
var flyang = new Object();
flyang.ajaxOpts = {
	type: "POST",
	url: "skyservice",
	skyser: "",
	getjsonstr: "",
	postjsonstr: "",
	dataType: 'json',
	timeout: 30000,
	async: true,
	doSuccess: function(data){
	}
}
flyang.request = function(ajaxOpts){
	var opts = $.extend({},flyang.ajaxOpts,ajaxOpts)
	$.ajax({
		type: opts.type,
		url: opts.url,
		timeout: opts.timeout,
		async: opts.async,
		data: {
			skyser: opts.skyser,
			getjsonstr: JSON.stringify(opts.getjsonstr),
			postjsonstr: JSON.stringify(opts.postjsonstr)
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: opts.dataType,
		success: opts.doSuccess
	});
}
//验证返回JSON信息
function valideData(data){
	if (data.syserror!=undefined) {
		var date = new Date();
		var hours = date.getHours();
		var m = date.getMinutes();
		if (hours == 0 && (m >= 0 && m <= 30)) alert('00:00-00:30为系统维护时间，请稍后登录重试！');
		else alert(data.syserror);
		top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
		return false;
	} else if (data.result!=undefined) {
		var res = Number(data.result);
		if (res > 0) {
			return true;
		} else {
			alerttext(data.msg);
			setTimeout(function() {
				return false;
			},
			2000);
		}
	} else if (data.total!=undefined) {
		return true;
	}
}
var setmenutimer = null;
var getmsgtimer = null;
$(function(){
	with(document.body) {
	    oncontextmenu=function(){return false}
	}
	//确定下次进新界面还是老界面；
	var mainpageparam = getparam({
		pgid: "mainpage",
		usymbol: "mainpageparam" 
	}); 
	var paramsarr = mainpageparam.split("");
	paramsarr.splice(0,1,"1");
	mainpageparam = paramsarr.join("");
	mainpageparam += "0000000000";
	setparam({
		pgid: "mainpage",
		usymbol: "mainpageparam",
		uvalue: mainpageparam.substring(0, 10)
	});
	$('#version-span1').html('Ver'+skyversion);
	$('#version-span2').html('Ver&nbsp;'+skyversion+versionname);
	$('#menu-comid').html(getValuebyName("COMID"));
	$('#menu-balcurr').html(balcurr);
	var levelid = Number(getValuebyName("GLEVELID"));
	if(levelid != 0){
		$('.admin').remove();
	}
	if(levelid != 0&&levelid!=4&&levelid!=5){
		$('.admin1').remove();
	}
	if(levelid != 0&&levelid!=4){
		$('.admin0').remove();
	}
	if(Number(levelid)==1&&Number(levelid)>5){
		$('.tjqx').remove();
	}
	if(validday>0 && validday<10){
		alert("您的有效期还有"+validday+"天，为了不影响您的使用，请及时联系开发商！ ");
		allowuse = true;
	}else if(Number(balcurr)>10){
		allowuse = true;
	}else if(Number(balcurr)<=10&&Number(balcurr)>0){
		allowuse = true;
		alerttext("您的枫杨果不足10个，为保证您的使用，请及时充值！");
	}else if(Number(balcurr)<=0){
		allowuse = false;
		if(levelid=='0')
			alert("您的枫杨果已不足，为保证您的使用，请充值！");
		else{
			alert("您的枫杨果已不足，为保证您的使用，请联系系统管理员及时充值！");
			window.location.href="loginservlet?doservlet=logoff";
		}
			
	}
	var now = new Date();
	var hours = now.getHours();
	var timeValue = ((hours >= 12) ? "下午好！" : "上午好！")+getValuebyName("EPNAME")+"("+getValuebyName("LEVELNAME")+")";
	$('#time').html(timeValue);
    settabmenu();
	$('#split').hide();
	var psw = getValuebyName("SKYPW")=="6BAD136AEF6902EB0D3C0E7666DD2DB7";
	if(psw){
		$('#chcodemodal .msgbox').html("提示：密码过于简单，为了不影响正常使用，请重新修改密码！");
		$('#chcodemodal').modal("show");
		$('#chcodemodal').on('hide.bs.modal', function (e) {
			alert("密码不修改，无法正常使用！");
			top.window.location.href="loginservlet?doservlet=logoff";
		});
	}
	$('div.menu_box').delegate('div.menu_el','click',function(){
		var $this = $(this);
		var bool = $this.hasClass('menu_el_selected');
		if(!bool){
			if($('span.yes').length==0){
				$('div.menu_box .menu_el_selected').removeClass('menu_el_selected');
				var len = $this.find('li').length;
				if(len==0){
					var pageid = Number($this.data('pageid'));
					if(pageid==0)
						 $('#tab_tab_home a').click();
					else if(!isNaN(pageid))
						getlistprogpage(pageid);
				}
				$this.addClass('menu_el_selected');
				return false;
			}
		}
	}).delegate('#add_menu','click',function(){
		var $this = $(this);
		var bool = $this.hasClass('yes');
		if(bool){
			$this.prev().show();
			$this.parent().siblings('div.panel_body').find('.li_checkbox input').not('input:checked').parent().parent().parent().removeClass('show').addClass('hide');
			$this.parent().siblings('div.panel_body').find('.li_checkbox input').parent().slideUp();
			$this.removeClass('yes');
			$this.html('编辑');
			savefirstpage($this.parent().siblings('div.panel_body'));
		}else{
			$this.prev().hide();
			$this.parent().siblings('div.panel_body').find('.hide').removeClass('hide').addClass('show');
			$this.parent().siblings('div.panel_body').find('.li_checkbox').slideDown();
			$this.addClass('yes');
			$this.html('完成');
		}
		setnoborder($this.parent().siblings('div.panel_body'));
		return false;
	}).delegate('#edit_menu','click',function(){
		var $this = $(this);
		var bool = $this.hasClass('yes');
		if(bool){
			setnoborder($this.parent().siblings('div.panel_body'));
			$this.next().show();
			$this.parent().siblings('div.panel_body').find('li.show .icon_remove').slideUp();
			$this.removeClass('yes');
			$this.html('编辑');
		}else{
			$this.next().hide();
			$this.parent().siblings('div.panel_body').find('li .icon_remove').slideDown();
			$this.addClass('yes');
			$this.html('完成');
		}
		return false;
	}).delegate('div.menu_el.menu_el_selected','mouseenter',function(){
		if(setmenutimer!=null){
			clearTimeout(setmenutimer);
			setmenutimer = null;
		}
	}).delegate('div.menu_el.menu_el_selected','mouseleave',function(){
		var $this = $(this);
		setmenutimer = setTimeout(function(){
			if($('span.yes').length==0){
				$this.removeClass('menu_el_selected');
			}
		},500);
	}).delegate('span.icon_remove','click',function(){
		var $this = $(this);
		var $li = $this.parent().parent();
		var pageid = $li.data('pageid');
		var progid = $li.data('progid');
		$li.removeClass('show').addClass('hide');
		setnoborder($('[data-pageid='+pageid+']').find('div.panel_body'));
		delfirtpage(pageid,progid);
		return false;
	}).delegate('li.panel_li','click',function(){
		var $this = $(this);
		var title = $this.find('.li_title').text();
		var progid = $this.data('progid');
		if($('span.yes').length==0){
			$('.menu_el_selected').removeClass('menu_el_selected');
			addTabs({
				id : "pg"+progid,
				title : title,
				close : true
			});
			return false;
		}
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
	$(document).ajaxStart(function() {
		var LoginEpid = Number(getValuebyName('EPID'));
		if(top.skyepid!=LoginEpid){
			alert("账户数据已过期（一台电脑只能同时登录一个账户），请重新登录！");
			top.window.location.href = "loginservlet?doservlet=logoff";
		}
	});
	getskymsg();
	getmsgtimer = setInterval("getskymsg()", 30*60*1000);
});
function setnoborder($panel_body){
	var len_li = 0;
	$panel_body.find('div.panel_box').each(function(){
		var $t = $(this);
		$t.find('.no-border').removeClass('no-border');
		if($t.find('li.show').length==0)
			$t.prev().removeClass('show').addClass('hide');
		if($t.find('li.show').length>len_li)
			len_li = $t.find('li.show').length;
		var i = 1;
		var sq = 2;
		if(len_li>=4)
			sq = 4;
		$t.find('li.show').each(function(){
			var $li = $(this)
			if((i%sq)==0||len_li==1)
				$li.find('div.panel_li_box').addClass('no-border');
			i++;
		});
	});
	if(len_li>=4)
		$panel_body.width(4*125);
	else
		$panel_body.width(2*125);
}
function getlistprogpage(pageid) {
	var hytag = 2;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data: {
			erpser: "selectprogpage",
			pageid: pageid,
			hytag: hytag
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var grpname = "";
				if (data.total != undefined) {
					var rows = data.rows;
					var html = "";
					for (var i in rows) {
						var row = rows[i];
						if (row.GRPNAME != grpname) {
							grpname = row.GRPNAME;
							if (grpname != "") {
								html += '</ul></div></div>';
							}
							html += ' <div class="panel_title">' + grpname + '</div>' + '<div class="panel_box">' + '<ul class="panel_ul">';
						}
						var progid = row.PROGID;
						var progname = row.PROGNAME;
						var selbj = Number(row.SELBJ);
						var showclass = "hide";
						var check = "";
						if (selbj == 1) {
							showclass = "show";
							check = "checked";
						}
						html += '<li class="panel_li ' + showclass + '" data-progid="' + progid + '" data-pageid="' + pageid + '">' + '<div class="panel_li_box">' + '<span class="li_title">' + progname + '</span>' + '<span class="li_btn li_checkbox">' + '<input type="checkbox" ' + check + '>' + '</span>' + '<span class="li_btn icon_remove" >' + '</span>' + '</div>' + '</li>';
					}
					$('[data-pageid=' + pageid + ']').find('div.panel_body').html(html);
					setnoborder($('[data-pageid=' + pageid + ']').find('div.panel_body'));
				}
			}
			}
		});
	}
	function delfirtpage(pageid, progid) {
		var skyser = "delfirstpage";
		var getjsonstr = {
			skyser: skyser,
			pageid: pageid,
			progid: progid
		}
		var doSuccess = function(data) {
			if (data.result != undefined) {
				if (Number(data.result) == 1) {

}
			}
		}
		var reqOpts = {
			skyser: skyser,
			getjsonstr: getjsonstr,
			async: false,
			doSuccess: doSuccess
		};
		flyang.request(reqOpts);
	}
	function savefirstpage($panel_body) {
		var count = $panel_body.find('li').length;
		if (count > 0) {
			var pageid = $panel_body.parent().parent().data('pageid');
			var proglist = [];
			$panel_body.find('li').each(function() {
				var $this = $(this);
				var progid = $this.data('progid');
				var value = 0;
				if ($this.find('[type="checkbox"]').prop('checked')) value = 1;
				proglist.push({
					progid: progid,
					value: value
				});
			});
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				async: false,
				data: {
					erpser: "savefirstpage",
					pageid: pageid,
					proglist: JSON.stringify(proglist)
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
function isSucess(text){
	if(text=='sys'){
		var date = new Date();
		var hours = date.getHours();
		var m = date.getMinutes();
		if(hours==0&&(m>=0&&m<=30))
			alert('00:00-00:30为系统维护时间，请稍后重试！');
		else
			alert('签名异常，请重新登录！');
		top.window.location.href="loginservlet?doservlet=logoff";
		return false;
	}else if(text=='fwq'){
		alerttext('服务器请求超时，请稍后重试！');
		clbol = true;
		return false;
	}else if(text.indexOf('未设置批发默认客户')>=0){
		clbol = true;
		return false;
	}else if(text.indexOf('设备')>=0){
		alert('您已在另外的设备登录，请重新登录！');
		top.window.location.href="loginservlet?doservlet=logoff";
		return false;
	}else if(text.indexOf('{')==-1&&text!='t'){
		alerttext(text);
		clbol = true;
		return false
	}else if(text.indexOf('<')>=0||text.indexOf('{')>=0||text=="t"){
		alerthide();
		return true;
	}else{
		clbol = true;
		return false;
	}
}
function isGetData(text){
	if(text=='sys'){
		var date = new Date();
		var hours = date.getHours();
		if(hours==0)
			alert('00:00-00:30为系统维护时间，请稍后登录重试！');
		else
			alert('签名异常，请重新登录！');
		top.window.location.href="/skydesk/loginservlet?doservlet=logoff";
		return false;
	}else if(text=='fwq'){
		alerttext('您的网络不佳，请求超时，请稍后重试！');
		return false;
	}else if(text.indexOf('<')>=0||text.indexOf('{')>=0||text=="t"){
		alerthide();
		return true;
	}else{
		return false;
	}
}
var messnum = 0;
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
			}
		}
	});
}
function wrtiejsErrorLog(e){
	var msg = "";
	if(typeof(e)=="string")
		msg = e;
	else msg = e.stack;
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "logservice",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			logser: "writelog",
			epid: getValuebyName("COMID")+"--"+getValuebyName("EPNO"),
			msg: msg
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
		
		}
	});
}
function setfullscreen(){
	if(top.$("div#mainpanel").hasClass("fullscreen")){
		top.$("div#menupanel,div#mpl .panel-heading").show();
		top.$("div#mainpanel").removeClass("fullscreen");
	}
	else{
		$.messager.show({
			msg: "按F11退出全屏模式",
			timeout: 1500,
			showType: 'show',
			height: 60,
			style:{
				right:'',
				top: document.body.scrollTop+document.documentElement.scrollTop,
				bottom:''
			}
		});
		top.$("div#mainpanel").addClass("fullscreen");
		top.$("div#menupanel,div#mpl .panel-heading").hide();
	}
}
function getservertime(){
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
	if(serverdatetime==undefined||serverdatetime.length==0)
		serverdatetime = new Date().getTime();
	return serverdatetime.replace(/-/g,"/");
}
</script>
<!-- 用户面板 -->
<jsp:include page="updateuserinfo.jsp"></jsp:include>
<jsp:include page="ccodemodal.jsp"></jsp:include>
</body>
</html>