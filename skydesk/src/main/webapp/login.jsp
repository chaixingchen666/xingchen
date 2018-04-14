<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Cookie cookie[] = request.getCookies();
String skydserstr = "{}";
try{
	if(cookie!=null){
		for(Cookie ck:cookie){
			String name = ck.getName();
			String value = ck.getValue();
			if(name.equals("SKYSTR")){
				skydserstr = tools.DataBase.getStringfromBase64(value);
				break;
			}
		}
	}
}catch(Exception e){
	tools.LogUtils.LogWrite("loginerror", e.getMessage());
}
Cookie ck1 = new Cookie("SKYSTR", tools.DataBase.getBase64String(skydserstr));
ck1.setMaxAge(30 * 24 * 60 * 60);
ck1.setPath("/");
response.addCookie(ck1);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8;" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<meta name="keywords" content="蓝窗,店管家,企业智能管理系统,服装,进销存,采购,零售,批发,销售,商场,财务,分析,系统" />
<meta name="description" content="蓝窗店管家，SKY Dispark Mobile蓝窗门店商业智能管家销售管理工作平台，帮助服饰鞋帽箱包家纺行业管理销售渠道及门店的经营和销售等行为。 实现店铺的多级互联和多店互联。让用户随时随地轻松掌握门店的运作情况和销售数据，完成对客户和顾客的服务与管理，提高管理效率和销售业绩。 ">
<link rel="stylesheet" type="text/css" href="css/loginStyle.css?ver=<%=tools.DataBase.VERSION%>"/>
<link rel="stylesheet" href="css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link href="images/icon.ico" rel="shortcut icon" />
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<!-- <script src="http://www.jq22.com/jquery/html5.min.js"></script> -->
<script type="text/javascript">
window.console = window.console || (function(){
	var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function(){};
	return c;
	})();
function setnoright(){
	  with(document.body) {
	      oncontextmenu=function(){
	    	  return false;
	}
	}
}
</script>
<title>蓝窗店管家Web登录界面</title>
<style type="text/css">
*{
	font-family: "Microsoft YaHei";
}
body,html{
	margin:0px;
	height:100%;
}
.body-image{
	width:100%;
	height:100%;
	-moz-background-size:100% 100%; /* 老版本的 Firefox */
	background-size:100% 100%;
	background-image: url(images/LoginPage/webimage.png);
/* 	behavior: url(css/backgroundsize.min.htc); */
}
/* .autoHeight{ */
/* 	width:100%; */
/* 	height:100%; */
/* 	background: url(images/LoginPage/LoginPage.png) no-repeat center center; */
/* } */
.autoHeight {
     width: 1300px;
     height: 691px;
    position: relative;
     top: 50%;
    margin: 0 auto 0 auto;
     margin-top: -350px;
/*     background: url(images/LoginPage/LoginPage.png) no-repeat center center */
    background: url(images/LoginPage/Login_bk.png) no-repeat center center;
    behavior: url(css/backgroundsize.min.htc);
    -moz-background-size:100% 100%; /* 老版本的 Firefox */
	background-size:100% 100%;
}
.login-box{
    position: relative;
    top: 50%;
    width: 445px;
    margin: 0 auto;
    height: 50px;
}

</style>
</head>
<body class="body-image">
<div class="autoHeight">
<div class="login-box" >
			<div class="formps" onsubmit="return false;">
				<input type="hidden" name="accid" id="accid">
				<form id="loginform">
				<div id="lg-l1f">
				<span class="login_icon user_icon"></span>
				<input class="lg-inpt" type="text" id="userid" name="userid" placeholder="请输入账户名" maxlength="12">
				</div>
				<div id="lg-l2f">
				<span class="login_icon psw_icon"></span>
				<input class="lg-inpt" type="password" id="userpsw" name="userpsw" placeholder="请输入密码"  maxlength="10">
				</div>
				<div>
					<label id="lg-labcheckbox"><input type="checkbox" id="reepno" style="display: none;" /><span class="checkbox"></span>保存登录信息   |  首次登录请先设置企业信息</label>
				</div>
				<div id="lg-lgbp">
					<input class="lg-insub" type="button" value="登&nbsp;&nbsp;&nbsp;&nbsp;录" id="subbutton" onclick="login();">
				</div>
				<div id="lg-demobp">
					 &nbsp;<input class="lg-inb"  type="button" name="demo" value="演示账号"
						onclick="skyDemo()">
					<input class="lg-inb1"  type="button" name="comconfirm" value="企业验证"
						onclick="$('#loginform').hide();$('#comfirmform').show();$('#comid').select().focus();">
				</div>
				</form>

				<form id="comfirmform" class="hide" onsubmit="return false;">
					<div class="msg" style="text-align: center;" id="msg">
					</div>
					<div class="cs-idp">
						<input class="cs-inpt" type="text" id="comid" name="comid" value="" placeholder="请输入账户名" style="text-align:center" maxlength="12"></div>
					<div>
						<input class="cs-insub" id="cs-confimp" type="button" name="confirm"
							value="验证账户" onclick="idconfirm();"><br>
					</div>
					<div class="cs-d1" style="width:200px;">
						<span onclick="location.href='regOne.html'" style="cursor: pointer;">新企业注册</span>&nbsp;
						<span onclick="$('#comfirmform').hide();$('#loginform').show();" style="cursor: pointer;">返回</span>&nbsp;
					</div>
				</form>


				<div class="banquan">版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;枫杨科技</div>
			</div>
			</div>
</div>
<script type="text/javascript">
$(function() {
	setnoright();
	if(!localStorage) localStorage = {};
	$('#userid').keyup(function(e) {
		if (e.which == 13){
			$('#userpsw').focus();
			 return false;
		}
	});
	$('#userpsw').keyup(function(e) {
		if (e.which == 13){
			login();
			return false;
		}
	});
	$('#comid').keyup(function(e){
		 var key = e.which;
		 if(key==13){
			 idconfirm();
			 return false;
		 }
	 });
	if (localStorage&&localStorage.accid) {//验证是否记住账号并且保存
		var accids = localStorage.accid;
		var comid = localStorage.comid;
		$('#accid').val(accids);
		$('#comid').val(comid);
		idconfirm();
// 	} else {
// 		$('#loginform').hide();
// 		$('#comfirmform').show();
	}
	if (localStorage&&localStorage.userid) {//验证是否记住账号并且保存
		epno = localStorage.userid;
		$('#userid').val(epno.toUpperCase());
		$('#userpsw').focus();
	} else {
		$('#userid').focus();
	}

	$('#lg-labcheckbox').click(
			function(e) {
				var checked = $(this).children('input[type=checkbox]')
						.prop("checked");
				if (checked) {
					$(this).children('span').removeClass("checkboxed")
							.addClass("checkbox");
					$(this).children('input[type=checkbox]').prop(
							"checked", false);
				} else {
					$(this).children('span').removeClass("checkbox")
							.addClass("checkboxed");
					$(this).children('input[type=checkbox]').prop(
							"checked", true);
				}
	});
});
function idconfirm(){
	var comid = $('#comid').val();
	  var oldaccid = Number(localStorage.accid);
	  alerttext("企业验证中……");
	  $.ajax({
           	type: "POST",   //访问WebService使用Post方式请求
            url: "loginservlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
            data: {
            	doservlet:"idconfirm",
            	comid: comid
            	},         //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到
            dataType: 'text',
            success: function(text) {     //回调函数，result，返回值
            	alerthide();
            	if(text=="t"){
//             		setCookie("COMID", comid.replace(/ /g,'').toUpperCase(), 30);
            		skyuser = getCookieObj("SKYSTR");
            		var newaccid = Number(getValuebyName("ACCID"));
            		localStorage.accid = newaccid;
            		localStorage.comid = comid.toUpperCase();
            		$('#accid').val(newaccid);
            		$('#comid').val(comid.replace(/ /g,'').toUpperCase());
    				$('#comfirmform').hide();
            		$('#loginform').show();
            		if(oldaccid==newaccid){
	            		if($('#userid').val().length>0){
	            			$('#userpsw').val('').focus();
		  				}else{
		  					$('#userid').focus();
		  				}
            		}else{
            			$('#userpsw').val('');
						$('#userid').val('').focus();
            		}
            	}else if(text=="none"){
            		clearCookie("ACCID", "", -1);
            		clearCookie("COMID", "", -1);
            		clearCookie("SKYSTR", "", -1);
            		localStorage.clear();
            		alert("输入的账户名不存在，请重新输入！");
            		$('#comid').val('');
            		$('#comid').focus();
            	}else if(text=='fwq'||text=='sys'){
            		//alert(text);
            		alerttext("服务器异常，请稍后再试！");
            	}
            }
            });
  }
function login() {
	var userid = $('#userid').val().replace(' ', '');
	var userpsw = $('#userpsw').val();
	var accid = Number(getValuebyName('ACCID'));
	var comid = $('#comid').val();
	if (userid == "") {
		alert("请输入账号");
		$('#userid').focus();
	} else if (userpsw == "") {
		alert("请输入密码");
		$('#userpsw').focus();
	} else {
		$('#subbutton').val("登录中……");
		$('#subbutton').attr('disabled', 'disabled');
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求
			url : "loginservlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
			data : {
				doservlet : "login",
				userid : userid,
				userpsw : userpsw,
				accid : accid,
				comid : getValuebyName('COMID')
			}, //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到
			dataType : 'text',
			success : function(text) { //回调函数，result，返回值
				try{
					if (text == ''||text == 'error') {
						alert('服务器请求超时，请稍后重试');
					} else if (text == "t") {
						if(accid!=1000&&userid.toUpperCase()!="DEMO"){
							localStorage.userid = userid.toUpperCase();
						}
						var mainpageparam = getparam({
							pgid: "mainpage",
							usymbol: "mainpageparam"
						})+"0000000000";
						if(mainpageparam.charAt(0)=="1") alert("新界面数据功能有待完善，现停止使用，请等待下次开放！");
// 						var tojsp = mainpageparam.charAt(0)=="1"?"/skydesk/main2.jsp":"/skydesk/main.jsp";
						var tojsp = "/skydesk/main.jsp?v=<%=tools.DataBase.VERSION%>";
						top.window.location.href = tojsp;
					} else if (text == "idconfirm") {
						alerttext("请先验证企业账号");
						$('#loginform').hide();
						$('#comfirmform').show();
					} else {
						var data = $.parseJSON(text);
						alert(data.msg);
						if(text.indexOf("密码")<0){
							$('#userpsw').val('');
							$('#userid').val('').focus();
						}else{
							$('#userpsw').val('').focus();
						}
					}
				}catch(e){
					console.log(e.message);
				}
				$('#subbutton').removeAttr('disabled');
				setTimeout(function() {
					$('#subbutton').val("登     录");
				}, 2000);
			}
		});
	}
}
function iskey(key, e) {
	var eventObj = event || e;
	var keyCode = eventObj.keyCode || eventObj.which;
	if (keyCode == key) {
		return true;
	} else {
		return false;
	}
}
function getRequest() {  
	 var url = location.search; //获取url中"?"符后的字串   
	 var theRequest = new Object();  
	 if (url.indexOf("?") != -1) {  
	     var str = url.substr(1);  
	     strs = str.split("&");  
	     for (var i = 0; i < strs.length; i++) {  
	         theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);  
	     }  
	 }  
	 return theRequest;  
} 

function skyDemo(){
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求
		url : "loginservlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data : {
			doservlet : "logindemo"
		}, //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到
		dataType : 'text',
		success : function(text) { //回调函数，result，返回值
			try{
				if (text == ''||text == 'error') {
					alert('服务器请求超时，请稍后重试');
				} else if (text == "t") {
					var tojsp = "/skydesk/main.jsp?v=<%=tools.DataBase.VERSION%>";
					top.window.location.href = tojsp;
				}
			}catch(e){
				console.log(e.message);
			}
			$('#subbutton').removeAttr('disabled');
			setTimeout(function() {
				$('#subbutton').val("登     录");
			}, 2000);
		}
	});
}
function idconfirms(){
	var accname = $('#comid').val();
	var oldaccid = Number(getValuebyName("ACCID"));
	var erpser = "getaccregbyname";
	clearCookie("SKYSTR", "", -1);
	localStorage.clear();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data : {
			erpser: erpser,
			accname: accname,
			fieldlist: "ACCID,ACCNAME,REGDATE"
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值
			try{
				if(valideData(data)){
					if(data.total>0){
						skyuser = getCookieObj("SKYSTR");
	            		var newaccid = Number(getValuebyName("ACCID"));
	            		localStorage.accid = newaccid;
	            		localStorage.comid = comid;
	            		$('#accid').val(newaccid);
	            		$('#comid').val(comid.replace(/ /g,'').toUpperCase());
	    				$('#comfirmform').hide();
	            		$('#loginform').show();
	            		if(oldaccid==newaccid){
		            		if($('#userid').val().length>0){
		            			$('#userpsw').val('').focus();
			  				}else{
			  					$('#userid').focus();
			  				}
	            		}else{
	            			$('#userpsw').val('');
							$('#userid').val('').focus();
	            		}
					}else{
	            		alert("输入的账户名不存在，请重新输入！");
	            		$('#comid').val('');
	            		$('#comid').focus();
					}
				}
			}catch (e) {
				// TODO: handle exception
				console.log(e.message);
			}
		}
	});
}
function valideData(data,showErrMsg) {
	if (data.syserror != undefined) {
		var date = new Date();
		var hours = date.getHours();
		var m = date.getMinutes();
		var errmsg = "";
		if (hours >=2 && housrs<4 || (housrs==4 && m <= 30))
			errmsg = "02:00-04:30为系统维护时间，请稍后登录重试！";
		else errmsg = data.syserror;
		alert(errmsg);
		top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
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
</script>
</body>
</html>

