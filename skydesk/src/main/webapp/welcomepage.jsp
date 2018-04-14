<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<title>Home</title>
<style type="text/css">
* {
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
	background-color: #fff;
/* 	background-image: url(images/LoginPage/webimage.png); */
}
.main-img{
	width:100%;
	height:100%;
	background: url(images/welcome.png) no-repeat center center;
	background-size: 1024px 473px;
	behavior: url(css/backgroundsize.min.htc);
}
/* S-SKYMSGBOX */
.panel-heading{
	background: #00919a;
}
.sky-msgbox {
    position: absolute;
    left: 10px;
    bottom: 10px;
    background: #fff;
    width: 200px;
    border-radius: 3px 3px 0 0;
     -webkit-border-radius:3px 3px 0 0;
    box-shadow: 0px 0px 16px #000;
    height: 200px;
    
}

.sky-msgtitle {
    color: #fff;
    font-size: 15px;
    text-indent: 10px;
    height: 46px;
    border-radius: 3px 3px 0 0;
    -webkit-border-radius:3px 3px 0 0;
    line-height: 46px;
}

.sky-msgul {
    list-style: disc;
    margin-top: 10px;
    overflow: auto;
}
.sky-msgul li{
    margin-top: 3px;
    cursor: pointer;
    color: #ff7900;
}
.sky-msgul li:ACTIVE,.sky-msgul li:HOVER{
	color: #23b9cb;
}
.sky-msgclose {
    float: right;
    position: relative;
    right: 10px;
    font-size: 25px;
    cursor: pointer;
}

.sky-msgclose:hover {
    text-shadow: 0px 0px 5px red;
}

/* E-SKYMSGBOX */
</style>
<script type="text/javascript" src="js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript">
function setnoright(){//禁用右键
	  with(document.body) {
	      oncontextmenu=function(){return false}
	      ondragstart=function(){return false}
	      onselectstart=function(){return false}
	      onbeforecopy=function(){return false}
	      onselect=function(){document.selection.empty()}
	      oncopy=function(){document.selection.empty()}
	    }
}
document.onkeydown = function (evt) {
    var isie = (document.all) ? true : false;
    var key;
    var srcobj;
    if (isie) {
        key = event.keyCode;
        srcobj = event.srcElement;
    }
    else {
        key = evt.which;
        srcobj = evt.target;
    }
    if (key == 116) {       //屏蔽 F5 刷新键    
        if (isie) {
            event.keyCode = 0;
            event.cancelBubble = true;
            return false;
        }
        else
            return false;
    }
    if (key == 8) {                //屏蔽退格键  
        if (isie) {
            if (event.srcElement.type != 'text' && event.srcElement.type != 'textarea' && event.srcElement.type != 'password') { //屏蔽BackSpace键
                event.keyCode = 0;
                event.cancelBubble = true;
                return false;
            }
            else
                if ((event.srcElement.type == 'text' || event.srcElement.type == 'textarea' || event.srcElement.type == 'password') && event.srcElement.readOnly) { //屏蔽BackSpace键
                    //alert(event.srcElement.readOnly);
                    event.keyCode = 0;
                    event.cancelBubble = true;
                    return false;
                }
            //altert('for ie');
        }
        else {
            var type = evt.target.localName.toLowerCase();
            if ((type == 'input' || type == 'textarea' || type == 'submit' || type == 'password') && !evt.target.readOnly) {
                return true;
            }
            else {
                return false;
            }


        }

    }
    //alert(srcobj.type);
    if (key == 13 && srcobj.type != 'button' && srcobj.type != 'submit' && srcobj.type != 'reset' && srcobj.type != 'textarea' && srcobj.type != '' && srcobj.type != 'file') {
        //&& srcobj.type != 'image' 
        /*if (isie) {
        //event.keyCode = 9;
        //event.keyCode=event.charCode=9; 
        var keyEvent = document.createEvent("Event");         
        // This is a lovely method signature         
        keyEvent.initKeyboardEvent("onkeydown", true, true, window, 9, event.location, "", event.repeat, event.locale);         
        event.currentTarget.dispatchEvent(keyEvent);         
        // you may want to prevent default here 
        return false;
        }*/
        //else 
        {
            if (isie)
                event.keyCode = event.charCode = 9;

            try {
                var el = getNextElement(srcobj);
                //alert(el.disabled);
                if ((el.type != 'hidden') && (!el.disabled))
                    el.focus();
                else {

                    while ((el.type == 'hidden') || (el.disabled))
                        el = getNextElement(el);
                    el.focus();
                }

            }
            catch (e) {


           }
            return false;
        }

    }

}
var msgtimer = null;
$(function(){
	$('.sky-msgclose').click(function(e){
		$('.sky-msgbox').hide();		
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
		case "jxsth":
			top.addTabs({
				id : "pg1303",
				title :"批发退库",
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
	});
	getskymsg();
	msgtimer = setInterval("getskymsg()", 30*60*1000);
	$("body").keydown(function(e){
		var k = e.which;
		if(k==122){
			e.preventDefault();
			if(typeof(top.setfullscreen)=="function")
				top.setfullscreen();
		}
	});
});
function isSucess(text){
	if(text=='sys'){
		var date = new Date();
		var hours = date.getHours();
		var m = date.getMinutes();
		clearInterval(msgtimer);
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
		clearInterval(msgtimer);
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
var buyernum = 0;
var sellernum = 0;
var jxsordnum = 0;
var jxsthnum = 0;
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
				var messnum = row.MESSNUM;
				buyernum = Number(row.BUYERNUM);
				sellernum = Number(row.SELLERNUM);
				jxsordnum = Number(row.JXSORDNUM);
				jxsthnum = Number(row.JXSTHNUM);
				gysfhdnum = Number(row.GYSFHDNUM);
				ztdcdnum = Number(row.ZTDCDNUM);
				top.buyernum =buyernum;
				top.sellernum =sellernum;
				top.jxsordnum= jxsordnum;
				top.jxsthnum =jxsthnum;
				top.gysfhdnum= gysfhdnum;
				top.ztdcdnum =ztdcdnum;
				var html = "";
				if(Number(messnum)>0){
				//	html += '<li>系统公告'+ Number(messnum) + '条</li>';
				}
				if(Number(buyernum)>0){
					//$('#pgmybuyer').append('<span class="badge">'+Number(buyernum)+'</span');
					html += '<li class="jxssq">经销商申请'+ Number(buyernum) + '条</li>';
				}
				if(Number(sellernum)>0){
					//$('#pgmyseller').append('<span class="badge">'+Number(sellernum)+'</span');
					html += '<li class="gyssq">供应商申请'+ Number(sellernum) + '条</li>';
				}
				if(Number(jxsordnum)>0){
					//$('#pg1302').append('<span class="badge">'+Number(jxsordnum)+'</span');
					html += '<li class="jxsdd">经销商订单'+ Number(jxsordnum) + '笔</li>';
				}
				if(Number(jxsthnum)>0){
					//$('#pg1302').append('<span class="badge">'+Number(jxsordnum)+'</span');
					html += '<li class="jxsth">经销商退货'+ Number(jxsthnum) + '笔</li>';
				}
				if(Number(gysfhdnum)>0){
					//$('#pg1102').append('<span class="badge">'+Number(gysfhdnum)+'</span');
					html += '<li class="gysfh">供应商发货'+ Number(gysfhdnum) + '笔</li>';
				}
				if(Number(ztdcdnum)>0){
					//$('#pg1403').append('<span class="badge">'+Number(ztdcdnum)+'</span');
					html += '<li class="ztdbdd">在途调拨单'+ Number(ztdcdnum) + '笔</li>';
				}
				if(html!=""){
					$('.sky-msgbox').show();
					$('.sky-msgul').html("");
					$('.sky-msgul').append(html);
				}else
					$('.sky-msgbox').hide();
				
			}
		}
	});
}
</script>
</head>
<body onload="setnoright()">
<div class="body-image">
<div class="main-img body" style="position:absolute;"> 
<!-- <img style="position:fixed;" src="images/welcomefy.jpg" height="100%" width="100%" />  -->
</div>
</div>
 <!-- 消息 -->
<div class="sky-msgbox hide">
<div class="sky-msgtitle panel-heading">
<span>温馨提醒</span>
<span class="sky-msgclose">×</span>
</div>
<div class="sky-msgbody">
<ul class="sky-msgul">
</ul>
</div>
</div>
</body>
</html>