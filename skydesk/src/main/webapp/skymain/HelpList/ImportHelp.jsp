<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 文件上传串口 -->
<script type="text/javascript" src="http://win.skydispark.com/skyasset/webuploader/webuploader.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<div id="uploadxlshelpd" title="请选择本机要上传的文件" style="width: 500px; height: 300px" closed="true" class="easyui-dialog webuploader-element-invisible">
  <input type="hidden" id="xlsdata">
  <div class="filebtn-box">
  <input type="text" class="wid160 hig25 fl" id="iptfile" placeholder="<请选择xls文件>"style="margin: 10px;">
     <div id="xlsPicker" class="fl">选择文件</div>
     <button id="uploadxlsbtn" class="filebutton wid160">上传</button>
     <button id="resetxlsbtn" class="filebutton">清空文件</button>
    </div>
    <div id="xlsprocess" style="margin-left: 10px;clear: both;float:left;"></div>
    <div style="float:left;margin-left: 10px;margin-right:30px;">
    只显示
    <label><input type="checkbox" id="showfailedli" checked onchange="changeulclass(0)">失败</label>
     <label><input type="checkbox" id="showokli" checked onchange="changeulclass(1)">成功</label>
     </div>
    <ul id="xlsmsgul" class="liststyle1 showokli showfailedli">
    </ul>
<div style="line-height: 30px; text-align: left;" class="dialog-footer">
<p id="importxlswxts" style="line-height: normal;margin: 5px"><img src="/skydesk/images/wxts.png" style="vertical-align: middle;"><b>温馨提示：</b>上传的格式只支持.xls,.xlsx，请按照文件xls,xlsx格式上传。<span onclick="downloadmoban(xlsmobanname)" style="color:#ff7900;cursor: pointer;">点击下载模板</span>
</p>
</div>
</div>
<script type="text/javascript">
var SkyUploadFiles = function(options) {
	var server = options.server;
	var pick = options.pick;
	var accept = options.accept;
	var uptype = options.uptype;
	var compress = false;
	var $upbtn = $(options.upbtn);
	var thumb = options.thumb?options.thumb:{
		width: 110,
		height: 110,
		quality: 70,
		allowMagnify: true,
		crop: true,
		type: 'image/jpeg'
	};
	if (uptype == "image") {
		if (options.compress == undefined) {
			compress = {
				width: 750,
				height: 750,
				quality: 90,
				allowMagnify: true,
				crop: false,
				preserveHeaders: true,
				noCompressIfLarger: true,
				compressSize: 0
			}
		} else compress = options.compress;
	}
	var uploader = WebUploader.create({
		auto: options.auto,
		swf: 'http://win.skydispark.com/skyasset/webuploader/Uploader.swf',
		server: server,
		pick: pick,
		thumb: thumb,
		accept: accept,
		fileNumLimit: options.fileNumLimit,
		method: 'POST',
		prepareNextFile: true,
		compress: compress
	});
	uploader.on('beforeFileQueued', options.beforeFileQueued);
	uploader.on('fileQueued', options.fileQueued);
	if (options.auto) uploader.on('startUpload', options.startUpload);
	else $upbtn.on('click', options.startUpload);
	uploader.on('uploadProgress', options.uploadProgress);
	uploader.on('uploadSuccess', options.uploadSuccess);
	uploader.on('uploadError', options.uploadError);
	uploader.on('uploadComplete', options.uploadComplete);
	return uploader
}
var uploader_xls = null;
var uploader_xls_options = {
		auto: false,
		server: "",
		pick: "#xlsPicker",
		upbtn: '#uploadxlsbtn',
		uptype: "files",
		accept: {
			title: 'XLS',
			//extensions: 'gif,jpg,jpeg,bmp,png',
			extensions: 'xls,xlsx',
			mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
		},
		fileQueued:  function(file) { // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。  
			var filetype = file.type;
			$("#iptfile").val(file.name);
		},
		uploadProgress: function(file, percentage) {
			$('#xlsprocess').html('文件上传' + (percentage * 100).toFixed(0) + '%');
			if (percentage == 1) {
				$('#xlsprocess').html('文件上传成功，请勿执行其他操作！正在服务器同步数据……');
			}

		},
		uploadSuccess:  function(file, res) {
			$('#xlsprocess').html('文件上传成功，服务器同步成功！以下是同步结果：');
			if (webSocket != null) {
				webSocket.close();
				webSocket = null;
			}
		},
		uploadError: function(file, res) {
			alert(res);
		},
		uploadComplete: function(file) {
		
		},
		startUpload: function() {
			if (uploader_xls.getFiles().length > 0) {
				uploader_xls.upload();
			} else {
				alerttext("文件列表为空！请添加需要上传的文件！");
			}
		}
}


var xlsmobanname = "";
var importchannel = "";
var setuploadserver = function(options){
	uploader_xls.option("server",options.server);
	xlsmobanname = options.xlsmobanname;
	importchannel = options.channel;
	$('div.webuploader-element-invisible').removeClass('webuploader-element-invisible');
}
function openimportd(){
	$('#uploadxlshelpd').dialog('open');
}
function initSocket0(relationid, usercode, msgul) {
	var path = top.window.document.location.host + top.window.document.location.pathname;
	if (path.indexOf('\/') > 0) path = path.substring(0, path.lastIndexOf('\/'));
// 	var path = "skydesk";//项目名称 regexp.js也要有
// 	console.log(path);
	var url = "ws://" + path + "/websocket/" + relationid + "/" + usercode;
	if ('WebSocket' in window) {
		webSocket = new WebSocket(url);

	} else if ('MozWebSocket' in window) {
		webSocket = new MozWebSocket(url);
	} else {
		alert('您的系统不支持导入监听！');
		return;
	}

	// 收到服务端消息
	webSocket.onmessage = function(msg) {
		try {
			var data = $.parseJSON(msg.data);
			var html = "";
			var rescl = "";
			if(msg.data.indexOf("成功")>-1)
				rescl = "okli";
			else if(msg.data.indexOf("失败")>-1)
				rescl = "failedli";
			html += '<li class="'+rescl+'">';
			for ( var i in data)
				html += data[i] + '，';
			html += '</li>';
			$(msgul).prepend(html);
		} catch (exp) {
			$(msgul).prepend("<li>该条数据字符异常，无法显示！</li>");
		}
		//$(msgul).scrollTop(0);
	};

	// 异常
	webSocket.onerror = function(event) {
		console.error(event);
	};

	// 建立连接
	webSocket.onopen = function(event) {
		console.error(event);
	};

	// 断线重连
	webSocket.onclose = function() {
		// 重试10次，每次之间间隔10秒
		/*
		if (tryTime < 10) {
			setTimeout(function() {
				webSocket = null;
				tryTime++;
				initSocket();
			}, 500);
		} else {
			tryTime = 0;
		}*/
	};
}
$(function(){
	$('#uploadxlsbtn').bind("click", function(e) {
		initSocket0(importchannel, skyepid, "#xlsmsgul");
	});
	//清空xls队列
	$('#resetxlsbtn').bind('click', function() {
		if (webSocket != null) {
			webSocket.close();
			webSocket = null;
		}
		uploader_xls.stop();
		uploader_xls.reset();
		$("#xlsmsgul").html('');
		$('#xlsprocess').html('');
		$('#iptfile').val('');
	});
});

</script>
