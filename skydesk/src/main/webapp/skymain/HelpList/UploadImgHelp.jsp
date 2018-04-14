<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/webuploader/webuploader.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript">
var uploaderimg = null;
var uploaderimg_option = {
	auto: true,
	server: "/skydesk/uploadimgservice?flyangser=getfilebase64string",
	uptype: "image",
	pick: "",
	viewimg: "",
	accept: {
		  title: 'Images',
		  extensions: 'jpg,jpeg,png',
		  mimeTypes: 'image/jpg,image/jpeg,image/png'   //这行慎用*，一些浏览器会很慢
		},
	fileQueued: function(file) { // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。  
		uploaderimg.makeThumb(file,
		function(error, ret) {
			if (error) {
				$(uploaderimg_option.viewimg).attr("alt", "暂不支持预览!").show();
			} else {
				$(uploaderimg_option.viewimg).attr('src', ret).show();
			}
			$(uploaderimg_option.pick.id).addClass('changed').show();
		});
	},

	uploadSuccess: function(file, res) {
		try {
			$(uploaderimg_option.pick.id).data('base64str', res.msg);
		} catch(e) {
			console.error(e);
			top.wrtiejsErrorLog(e);
		}
	},
	uploadProgress: function(file, percentage) {

},
	uploadError: function(file) {
		uploaderimg.reset();
	},
	uploadComplete: function(file) {
		alerthide();
		uploaderimg.reset();
	},
	startUpload: function() {
		if (uploaderimg.getFiles().length == 1) {
			uploaderimg.upload();
			alerttext("正在上传请稍等………", 30000);
		} else {
			alerttext("请选择一张图片！");
		}
	}
}
var setuploadimg = function(option) {
	uploaderimg_option = $.extend(uploaderimg_option, option);
	uploaderimg = SkyUploadFiles(uploaderimg_option);
	$('.webuploader-element-invisible').removeClass('webuploader-element-invisible');
}
</script>