/**
 * 时间格式化
 */
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt
}
/**
 * 调用之前请注意引入CryptoJS AES加密
 */
var AESEncrypt = function(word, key) {
	if (CryptoJS) {
		var key = CryptoJS.enc.Utf8.parse(key);
		var srcs = CryptoJS.enc.Utf8.parse(word);
		var encrypted = CryptoJS.AES.encrypt(srcs, key, {
			mode : CryptoJS.mode.ECB,
			padding : CryptoJS.pad.Pkcs7
		});
		return encrypted.toString();
	} else
		alert("页面未加载CryptoJS！");
}
/**
 * AES解密
 */
var AESDecrypt = function(word, key) {
	if (CryptoJS) {
		var key = CryptoJS.enc.Utf8.parse(key);
		var decrypt = CryptoJS.AES.decrypt(word, key, {
			mode : CryptoJS.mode.ECB,
			padding : CryptoJS.pad.Pkcs7
		});
		return CryptoJS.enc.Utf8.stringify(decrypt).toString();
	} else
		alert("页面未加载CryptoJS！");
}
/**
 * Base64加密字符串！
 */
var Base64Encode = function(str) {
	var s = CryptoJS.enc.Utf8.parse(str);
	var base64 = CryptoJS.enc.Base64.stringify(s);
	return base64;
}
/**
 * Base64解密字符串
 */
var Base64Decode = function(encodestr) {
	return CryptoJS.enc.Base64.parse(encodestr).toString(CryptoJS.enc.Utf8);
}
/**
 * Md5加密
 */
var Md5Encode = function(str) {
	var md5 = CryptoJS.MD5(str).toString();
	return md5.toUpperCase();
}
/**
 * data是一个json对象 保存登录信息到本地！
 */
var setUserData = function(data) {
	if (data) {
		var datastr = JSON.stringify(data);
		var base64str = Base64Encode(datastr);
		localStorage.setItem("UERSTR", base64str);
	} else {
		alert("data不是一个有效的字符串！")
	}
}
var getUserObj = function() {
	var userstr = localStorage.getItem("UERSTR");
	if(!userstr)
		return {};
	var datastr = Base64Decode(userstr);
	var obj = $.parseJSON(datastr);
	return obj;
}
/**
 * 加密请求数据
 */
var getReqData = function(words,keySign){
	var key = "Fly#Ang" + keySign.replace(" ", "").toUpperCase() + "Sky@D20$16";
	var resKey = null;
	try{
		resKey = Md5Encode(key);
		console.log("key:"+resKey);
	}catch(e){
		console.log("获取加密请求数据出错：" + e.message);
	}
	return AESEncrypt(words,resKey);
}
/**
 * 获取签名 userobj 指的就是getUserObj()
 */
var getSign = function(userobj, hasdata) {
	var signstr = "";
	var currentTime = new Date().Format("yyMMddhhmmss");
	try {
		var signstr0 = "FlyAng@2015" + userobj.EPID + currentTime
				+ userobj.ACCESSKEY + userobj.ACCID;
		signstr = Md5Encode(signstr0);
	} catch (e) {
		// TODO: handle exception
		console.log("MD5加密出错：" + e.message);
	}
	if (!hasdata) {
		var keyjson = {
			signature : signstr,
			timestamp : currentTime
		}	
		return JSON.stringify(keyjson);
	}
	var keyjson = {
		maccid : userobj.ACCID,
		mlevelid : userobj.LEVELID,
		userid : userobj.EPID,
		username : userobj.EPNAME,
		accdate : userobj.ACCDATE,
		timestamp : currentTime,
		signature : signstr,
		qxpublic : userobj.QXPUBLIC
	};
	return JSON.stringify(keyjson);
}

var defaultAjaxObj = {
	url : "https://jerp.skydispark.com:62220/skyderp/api",
	async : true,
	hassign : true,
	type : "POST",
	dataType : "jsonp",
	jsonp : "action",
	actionValue: null, 
	exdata : {},// 考虑到可能会有额外参数
	doSuccess : function(data) {
	}
};
// 跨域请求
var FlyangAjax = function(options) {
	var ajaxObj = $.extend(true, defaultAjaxObj, options);
	var url = ajaxObj.url;
	var async = ajaxObj.async;
	var paramsDatas = ajaxObj.data;
	var dataType = ajaxObj.dataType;
	var jsonp = ajaxObj.jsonp;
	var actionValue = ajaxObj.actionValue;
	if(!actionValue){
		alert("请求参数不合法！");
		return;
	}
	var doSuccess = ajaxObj.doSuccess;
	paramsDatas.flyang = "20150107";
	var words = JSON.stringify(paramsDatas);
	var keySign = getSign(getUserObj(),ajaxObj.hassign);// 根据是否需要传的东西
	var data = getReqData(words, keySign);
	var sign = Base64Encode(keySign);
	console.log(paramsDatas);
	console.log(keySign);
	var postdata = {
		data : data,
		sign : sign
	};
	console.log(postdata);
	if (ajaxObj.exdata.length > 0) {
		for ( var i in exdata) {
			if (exdata[i])
				postdata[i] = exdata[i];
		}
	}
	$.ajax({
		type : "POST", // 访问WebService使用Post方式请求
		async : async,
		url : url, // 调用WebService的地址和方法名称组合 ---- WsURL/方法名
		data : postdata, // 这里是要传递的参数，格式为 data: "{paraName:
		// paraValue}",下面将会看到
		dataType : dataType,
		jsonp : jsonp,
		jsonpCallback: actionValue,
		beforeSend : function() {
			// 请求前的处理
			try {

			} catch (e) {
				console.log("执行前出错：" + e.message);
			}
		},
		success : function(data) { // 回调函数，result，返回值
			try {
				DoSuccess(data);
			} catch (e) {
				console.log("执行成功时报错：" + e.message);
			}
		},
		complete : function() {
			// 请求完成的处理
			try {

			} catch (e) {
				console.log("执行完成时报错：" + e.message);
			}
		},
		error : function() {
			// 请求出错处理
			try {

			} catch (e) {
				console.log("执行过程出错：" + e.message);
			}
		}
	});
};
