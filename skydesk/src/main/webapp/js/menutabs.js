var co = false;
var allowuse = true;
//允许使用
var progjson = getfilejson("pgjspjson.json");
var noconpg = {
	pgversion: "VersionLog.html",
	pghelp: "help.html"
	
};
function src(id) {
    return progjson[id];
}
//设置菜单
function settabmenu() {
    $('#maintabs a').not('[aria-controls="home"]').contextmenu({
        target: '#tabmenu',
        onItem: function(context, e) {
            var href = $(context).attr('href');
            var index = href.indexOf('#tab_') + 5;
            var id = href.substring(index);
            var tabclose = 'tab_' + id;
            var key = e.target.title;
            switch (key) {
            case "R":
            	$("div.loading-modal").show();
            	document.getElementById('frame-' + id).contentWindow.location.reload(true);
                document.getElementById('frame-' + id).contentWindow.$('input[type=text]:first').focus()
                break;
            case "C":
                closeTab(tabclose);
                break;
            case "CA":
                var tab = $('#maintabs a big');
                for (var i = 0; i < tab.length; i++) {
                    var _tabclose = $(tab[i]).attr('tabclose');
                    if (tabclose != _tabclose && _tabclose != undefined) {
                        closeTab(_tabclose);
                    }
                }
                break;
            default:
                break;
            }
            $('#tabmenu').hide();
        }
    });
    $('#tabmenu').on('show.bs.context', function(e) {// console.log('before show event');
    });
    $('#tabmenu').on('shown.bs.context', function(e) {// console.log('after show event');
    });
    $('#tabmenu').on('hide.bs.context', function(e) {// console.log('before hide event');
    });
    $('#tabmenu').on('hidden.bs.context', function(e) {// console.log('after hide event');
    });
    $(".nav-tabs li a").on('click', function(e) {
        if ($(this).attr('title') == "tab") {
            $(".acc-inner-hover").removeClass('acc-inner-hover');
            var id = $(e.target).attr('aria-controls');
            if (!(id == 'home' || id == undefined)) {
                $('#allmenu #' + id.replace('tab_', '')).addClass('acc-inner-hover');
            }
        }
    });
}
// 添加可关闭的tabs
var clbol = true;
var addTabs = function(obj) {
    // addTabs="src: title: closed: "
    if (!allowuse) {
        alerttext("您的枫杨果已不足，为保证您的使用，请充值！");
        return;
    }
    if (clbol) {
        clbol = false;
        // 校验职员登录设备有效
        var id = "tab_" + obj.id;
        var tabtitle = obj.title;
        // 如果TAB不存在，创建一个新的TAB
        if (!$("#" + id)[0]) {
        	$("div.loading-modal").show();
        	if (obj.id == "pgmybuyer" || obj.id == "pgmyseller" || obj.id == "pgBalcurr") {
            	$(".acc-inner-hover").removeClass('acc-inner-hover');
                $(".active").removeClass("active");
                // 固定TAB中IFRAME高度
                // mainHeight = $(document.body).height() - 95;
                // 创建新TAB的title
                title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tablist" data-toggle="tab" title="tab">' + (tabtitle.indexOf("余额") > 0 ? "账户明细" : tabtitle);
                // 是否允许关闭
                if (obj.close) {
                    title += '<big tabclose="' + id + '">&nbsp;×</big>';
                }
                title += '</a></li>';
                // 是否指定TAB内容
                if (obj.content) {
                    content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
                } else {
                    // 没有内容，使用IFRAME打开链接
                    content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="frame-' + obj.id + '" src="skymain/BasicInfom/' + obj.id.replace("pg", "") + '.jsp" scrolling="no" frameborder="0"></iframe></div>';
                }
                // 加入TABS
                $("#maintabs.nav-tabs").append(title);
                // $('#' + obj.id).addClass('acc-inner-hover');
                $("#maincontent.tab-content").append(content);
                // 激活TAB
                $("#tab_" + id).addClass('active');
                $("#" + id).addClass("active");
                settabmenu();
                clbol = true;
            } else if (obj.id == "pghelp" || obj.id=="pgversion" || obj.id=="createMerchant") {
                $(".acc-inner-hover").removeClass('acc-inner-hover');
                $(".active").removeClass("active");
                // 固定TAB中IFRAME高度
                // mainHeight = $(document.body).height() - 95;
                // 创建新TAB的title
                title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tablist" data-toggle="tab" title="tab">' + (tabtitle.indexOf("余额") > 0 ? "账户明细" : tabtitle);
                // 是否允许关闭
                if (obj.close) {
                    title += '<big tabclose="' + id + '">&nbsp;×</big>';
                }
                title += '</a></li>';
                // 是否指定TAB内容
                if (obj.content) {
                    content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
                } else {
                    // 没有内容，使用IFRAME打开链接
                    content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="frame-' + obj.id + '" src="'+noconpg[obj.id]+'" frameborder="0"></iframe></div>';
                }
                // 加入TABS
                $("#maintabs.nav-tabs").append(title);
                // $('#' + obj.id).addClass('acc-inner-hover');
                $("#maincontent.tab-content").append(content);
                // 激活TAB
                $("#tab_" + id).addClass('active');
                $("#" + id).addClass("active");
                settabmenu();
                clbol = true;
            } else{
                $.ajax({
                    type: "POST",
                    url: "fyjerp",
                    async: false,
                    data: {
                        erpser: "devicevalid",
                        progid: obj.id.replace("pg", "")
                    },
                    dataType: 'json',
                    success: function(data) {
                        if (valideData(data)){
                            $(".acc-inner-hover").removeClass('acc-inner-hover');
                            $(".active").removeClass("active");
                            title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tablist" data-toggle="tab" title="tab">' + tabtitle;
                            // 是否允许关闭
                            if (obj.close) {
                                title += '<big tabclose="' + id + '">&nbsp;×</big>';
                            }
                            title += '</a></li>';
                            // 是否指定TAB内容
                            if (obj.content) {
                                content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
                            } else {
                                // 没有内容，使用IFRAME打开链接
                                content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="frame-' + obj.id + '" src="' + src(obj.id) + '" scrolling="no" frameborder="0"></iframe></div>';
                            }
                            // 加入TABS
                            $("#maintabs.nav-tabs").append(title);
                            $('#allmenu #' + obj.id).addClass('acc-inner-hover');
                            $("#maincontent.tab-content").append(content);
                            // 激活TAB
                            $("#tab_" + id).addClass('active');
                            $("#" + id).addClass("active");
                            settabmenu();
                        }
                    }
                });
                clbol = true;
            }
        } else {
            // 激活TAB
            $(".acc-inner-hover").removeClass('acc-inner-hover');
            $(".active").removeClass("active");
            $('#allmenu #' + obj.id).addClass('acc-inner-hover');
            $("#tab_" + id).addClass('active');
            $("#" + id).addClass("active");
            clbol = true;
        }
    }
};
var closeTab = function(id) {
    // 如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("#maintabs li.active").attr('id') == 'tab_' + id) {
        $("#tab_" + id).prev().addClass('active');
        var mid = $("#tab_" + id).prev().attr('id').replace('tab_tab_', '');
        $("#" + id).prev().addClass('active');
        if (mid != "home") {
            $("#allmenu #" + mid).addClass('acc-inner-hover');
        }
    }
    // 关闭TAB
    if (id != '') {
        $('#' + id.replace('tab_', '')).removeClass('acc-inner-hover');
    }
    try {
        $("#tab_" + id).remove();
        $("#" + id).remove();
    } catch (error) {
        console.log(e.message);
    }
    clbol = true;
};
document.onkeydown = function(evt) {
    var isie = (document.all) ? true : false;
    var key;
    var srcobj;
    if (isie) {
        key = event.keyCode;
        srcobj = event.srcElement;
    } else {
        key = evt.which;
        srcobj = evt.target;
    }
    if ((key >= 112 && key <= 122) || key == 27 || key == 46) {
        // 屏蔽 F5 刷新键
        if (isie) {
            event.keyCode = 0;
            window.event.cancelBubble = true;
            event.returnValue = false;
            event.cancelBubble = true;
            return false;
        } else
            return false;
    }
    if (key == 8) {
        //屏蔽退格键  
        if (isie) {
            if (event.srcElement.type != 'text' && event.srcElement.type != 'textarea' && event.srcElement.type != 'password') {
                //屏蔽BackSpace键
                event.keyCode = 0;
                event.cancelBubble = true;
                return false;
            } else if ((event.srcElement.type == 'text' || event.srcElement.type == 'textarea' || event.srcElement.type == 'password') && event.srcElement.readOnly) {
                //屏蔽BackSpace键
                //alert(event.srcElement.readOnly);
                event.keyCode = 0;
                event.cancelBubble = true;
                return false;
            }
            //altert('for ie');
        } else {
            var type = evt.target.localName.toLowerCase();
            if ((type == 'input' || type == 'textarea' || type == 'submit' || type == 'password') && !evt.target.readOnly) {
                return true;
            } else {
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
            } catch (e) {}
            return false;
        }
    }
}
$(function() {
    // mainHeight = $(document.body).height() - 45;
    // $('.main-left,.main-right').height(mainHeight);
    $("body").delegate('[addtabs]', 'click', function() {
        $('#allmenu .open').removeClass('open');
        $this = $(this);
        addTabs({
            id: $this.attr("id"),
            title: $this.children('.inner-title').text() == "" ? $this.text() : $this.children('.inner-title').text(),
            close: true
        });
        return false;
    });
    $(".nav-tabs").on("click", "[tabclose]", function(e) {
        var id = $(this).attr("tabclose");
        closeTab(id);
        return false;
    });
});
