<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
session.setAttribute("versionname", tools.DataBase.SNAME);
response.addCookie(new Cookie("SKYVERSIONNAME",tools.DataBase.encode(tools.DataBase.SNAME)));
session.setAttribute("version", tools.DataBase.VERSION);
response.addCookie(new Cookie("SKYVERSION",tools.DataBase.encode(tools.DataBase.VERSION)));*/
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="css/loginStyle.css?ver=<%=tools.DataBase.VERSION%>" />
<link href="images/Ico_16.ico" rel="shortcut icon" />
<link rel="stylesheet" href="css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/Cookies.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="js/jquery.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
<title>蓝窗店管家-企业验证</title>
<style type="text/css">
.idc-body {
	background-color: black;
	background-image: url(images/ComSettingPage/Comsetting.png);
	background-repeat: no-repeat;
	background-position: top;
}
*{
	font-family: "Microsoft YaHei";
}
a{
	text-decoration: none;
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
}
.autoHeight{
	width:100%;
	height:100%;
	background: url(images/ComSettingPage/Comsetting.png) no-repeat center center;
}
</style>
</head>
<body class="body-image">
<div class="autoHeight">
<div style="position: absolute;top: 50%;width:100%;height:50px" >
		<div class="formps">
			<div class="msg" style="text-align: center;" id="msg">
			</div>
			<div class="cs-idp">
				<input class="cs-inpt" type="text" id="comid" name="comid" value="" placeholder="请输入账户名" style="text-align:center" maxlength="12"></div>
			<div>
				<input class="cs-insub" id="cs-confimp" type="button" name="confirm" 
					value="验证账户" onclick="idconfirm();"><br>
			</div>
			<div class="cs-d1" style="display: none;" >
				<a href="http://www.skydispark.com/regOne.aspx" ><font color="white">新企业注册</font></a>&nbsp;
<!-- 				<a href=""><font color="white">找回账号及密码</font></a> -->
			</div>
		</div>
		</div>
</div>
<script type="text/javascript">
	  $(function(){
		  setnoright();
			  if(checkCookie('COMID')&&getCookie('COMID')!="NULL"){
					var comids=getCookie('COMID');
					$('#comid').val(comids);
				}else{
					$('#comid').focus();
				}
			  var msg = '<%=request.getParameter("msg")%>';
			  if(msg==0){
				  setTimeout(function(){
				 	 alerttext("请先输入企业账号！");
				  },1000);
			  }
			 $('#comid').keyup(function(e){
				 var key = e.which;
				 if(key==13){
					 idconfirm();
				 }
			 });
	  });
	  window.console = window.console || (function(){ 
			var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile = c.clear = c.exception = c.trace = c.assert = function(){}; 
			return c; 
			})();
		function setnoright(){
			  with(document.body) {
			      oncontextmenu=function(){return false;}
//		 	      ondragstart=function(){return false}
//		 	      onselectstart=function(){return false}
//		 	      onbeforecopy=function(){return false}
//		 	      onselect=function(){document.selection.empty()}
//		 	      oncopy=function(){document.selection.empty()}
			}
		}
	  var scrollFunc=function(e){ 
		   e=e || window.event; 
		   if(e.wheelDelta && event.ctrlKey){//IE/Opera/Chrome 
		    	event.returnValue=false;
		   }else if(e.detail){//Firefox 
		    	event.returnValue=false; 
		   } 
		  }  
		  if(document.addEventListener){ 
		 	document.addEventListener('DOMMouseScroll',scrollFunc,false); 
		  }//W3C 
		  window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome/Safari 
		  function idconfirm(){
			  var comid = $('#comid').val();
			  alerttext("企业验证中……");
			  $.ajax({ 
		           	type: "POST",   //访问WebService使用Post方式请求 
		            url: "loginservlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		            data: {
		            	doservlet:"idconfirm",
		            	comid:comid},         //这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		            dataType: 'text', 
		            success: function(text) {     //回调函数，result，返回值
		            	if(text=="t"){
		            		setCookie("COMID", comid.replace(/ /g,'').toUpperCase(), 30);
		            		window.location.href='login.jsp';
		            	}else if(text=="none"){
		            		//alert("输入的账户名不存在，请重新输入！");
		            		alerttext("输入的账户名不存在，请重新输入！");
		            		$('#comid').focus();
		            	}else if(text=='fwq'||text=='sys'){	
		            		//alert(text);
		            		alerttext("服务器异常，请稍后再试！");
		            	}
		            }              
		            });
		  }	  
</script>
</body>
</html>