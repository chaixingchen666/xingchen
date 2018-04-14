/*******************************************************************************
* �첽�ϴ��ļ�����IE8������͹ȸ����
* ʵ�ֵ�������ϴ���ˢ��
* �޸��ԣ�http://www.ponxu.com
* @author ��ΰΰ <702295399@qq.com>
* @version 1.3 (2015-6-14)
*******************************************************************************/
(function ($) {

    var frameCount = 0;
    var formName = "";

    var iframeObj = null;
    var state = {};


    //var fileHtml = "";
    var colfile = null;

    //���ֵ
    function clean(target) {

        var file = $(target);
        var col = file.clone(true).val("");
        file.after(col);
        file.remove();
        //�ؼ�˵��
        //�ȵõ���ǰ�Ķ���Ͳ��������Ž��п�¡��ͬʱ��¡�¼���
        //����¡�õĸ�������ԭ�ȵ�֮�󣬰���˳�����ɾ��������ʼ����¡�ĸ���
    };
    function ajaxSubmit(target) {

        var options = state.options;

        if (options.url == '' || options.url == null) {
            alert("���ϴ���ַ");
            return;
        }
        if ($(target).val() == '' || $(target).val() == null) {
            alert("��ѡ���ļ�");
            return;
        }

        if (iframeObj == null) {

            var frameName = 'upload_frame_' + (frameCount++);
            var iframe = $('<iframe style="position:absolute;top:-9999px" ><script type="text/javascript"></script></iframe>').attr('name', frameName);
            formName = 'form_' + frameName;
            var form = $('<form method="post" style="display:none;" enctype="multipart/form-data" />').attr('name', formName);
            form.attr("target", frameName).attr('action', options.url);
            //

            var fileHtml = $(target).prop("outerHTML");


             colfile = $(target).clone(true);


            $(target).replaceWith(colfile)

            var formHtml = "";
            // form������������
            for (key in options.params) {
                formHtml += '<input type="hidden" name="' + key + '" value="' + options.params[key] + '">';
            }

            form.append(formHtml);

            form.append(target);

            iframe.appendTo("body");

            form.appendTo("body");


            iframeObj = iframe;
        }
        //����
        $(colfile).attr("disabled", "disabled")
       

        var canSend = options.onSend($(target), $(target).val());
        if (!canSend) {
            return;
        }

        var form = $("form[name=" + formName + "]");

        //�����¼�
        iframeObj.bind("load", function () {
            var contents = $(this).contents().get(0);
            var data = $(contents).find('body').text();
            if ('json' == options.dataType) {
                data = window.eval('(' + data + ')');
            }

            options.onComplate(data);

            iframeObj.remove();
            form.remove();
            iframeObj = null;

            //����
            $(colfile).removeAttr("disabled")
        });

        try {
            form.submit();
        } catch (Eobject) {
            console.log(Eobject)
            //alert(Eobject)
        }
    };
    //����
    $.fn.upload = function (options) {
        if (typeof options == "string") {
            return $.fn.upload.methods[options](this);
        }
        options = options || {};
        state = $.data(this, "upload");
        if (state)
            $.extend(state.options, options);
        else {
            state = $.data(this, "upload", {
                options: $.extend({}, $.fn.upload.defaults, options)
            });
        }

        //var opts = state.options;

        //if (opts.url == '') {
        //    return;
        //}
    };
    //����
    $.fn.upload.methods = {
        clean: function (jq) {
            return jq.each(function () {
                clean(jq);
            });
        },
        ajaxSubmit: function (jq) {
            return jq.each(function () {
                ajaxSubmit(jq);
            });
        },
        getFileVal: function (jq) {
            return jq.val()
        }
    };
    //Ĭ����
    $.fn.upload.defaults = $.extend({}, {
        url: '',
        dataType: 'json',
        params: {},
        onSend: function (obj, str) { return true; },
        onComplate: function () { return true; }
    });
})(jQuery);