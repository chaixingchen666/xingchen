var flyang = new Object();
flyang.ajaxOpts = {
	type: "POST",
	url: "/skydesk/skyservice",
	skyser: "",
	getjsonstr: "",
	postjsonstr: "",
	dataType: 'json',
	timeout: 30000,
	async: true,
	doSuccess: function(data) {}
}
flyang.request = function(ajaxOpts) {
	var opts = $.extend({},
	flyang.ajaxOpts, ajaxOpts);
	$.ajax({
		type: opts.type,
		url: opts.url,
		timeout: opts.timeout,
		async: opts.async,
		data: {
			skyser: opts.skyser,
			getjsonstr: JSON.stringify(opts.getjsonstr),
			postjsonstr: JSON.stringify(opts.postjsonstr)
		},
		dataType: opts.dataType,
		success: opts.doSuccess
	})
}
var qxkz;
var allowfsck;
var allownearsale;
var points;
var lspoints;
var centbl;
var dpjbfs;
var lsmlfs;
var allowinsale;
var allowshare;
var allowprymyj;
var allowchedan;
var allowclear;
var autowareno;
var usesyt;
var usepfsyt;
var useyhq;
var jzzdgd;
var cgthpdgys;
var allowchangdate;
var dialog_title = "系统提示";
var imageurl = 'http://dfs.skydispark.com/image/';
var dqpage = '1';
var dqzjl;
var dqindex;
var dqcurr;
var dqamt;
var dqzcurr;
var dqzamt;
var xsdpdz;
var qtzfpayname = "";
var qtzfpayno = "";
var qtpayfs = 2;
var allowscanware = 0;
var pgid;
var nulldata = {
	total: 0,
	rows: [],
	footer: []
};

var xstype;
var dytype;
var addtype;
var allotparams = "1000000000";//调拨参数见自定义参数说明.txt
var cgparams = "1000000000";//采购参数见自定义参数说明.txt
var wareoutparams = "1000000000";//批发参数见自定义参数说明.txt
var defallotparams = "1000000000";//调拨参数见自定义参数说明.txt
var defcgparams = "1000000000";//采购参数见自定义参数说明.txt
var defwareoutparams = "1000000000";//批发参数见自定义参数说明.txt
var hideyj = 0;

var skyuser = getCookieObj("SKYSTR");
var skyaccid = skyuser.ACCID;
var skyepid = skyuser.EPID;
var xmtype = top.xmtype; //项目类型
var hasmsg = false;
var nextpg = false;
var uploader = null;
var webSocket = null;
var tryTime = 0;
var pagecount = Number(getCookie("PAGECOUNT") == "" ? "20": getCookie("PAGECOUNT"));
function setqxpublic() {
	// QXPUBLIC:共40位，1-20:用户系统参数，21-30:用户角色参数(在角色中定义) 31-40为账户控制参数
	var qxpublic = getValuebyName("QXPUBLIC")+"00000000000";
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXX
	// ||||| |||||||||||||_______22 零售单款金额四舍五入
	// ||||| ||||||||||||________21 结账自动勾单
	// ||||| |||||||||||_________20 采购退货判断供应商
	// ||||| ||||||||||__________19 批发启用收银台
	// ||||| |||||||||___________18 收银启用C端验证
	// ||||| ||||||||____________17 零售启用收银台
	// ||||| |||||||_____________16 零售抹零方式：0=取整，1=四舍五入,2=保留原数
	// ||||| |||||||_____________15 0=按店铺交班 1=按收银员交班
	// ||||| ||||||______________14 往来帐款分店铺核对
	// ||||| |||||_______________13 条码产生方式(0-8)
	// ||||| ||||________________12 自动生成货号
	// ||||| |||_________________11 配货单允许直接出库
	// ||||| ||__________________10 配货单审核后才允许拣货
	// ||||| |___________________9 采购入库自动更新进价
	// |||||_____________________5-8 积分抵扣比例（4位）
	// ||||______________________4 单价小数位数(0,1,2)
	// |||_______________________3 启用最近售价
	// ||________________________2 允许负数出库
	// |_________________________1 启用权限控制
	// ==========================
	var rolepublic = getValuebyName("ROLEPUBLIC")+"00000000000";
	 // 用户角色参数(在角色中定义)   
	// 原qxpublic 21-30位参数对应 
	// 1234567890123456789012345678901234567890
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||| ||_8 允许冲单
	// |||| |__7 允许撤单
	// ||||____4-6 允许查询天数（0表示不限止）
	// |||_____3 允许设置打印页眉页脚
	// ||______2 允许分享
	// |_______1 允许查看进价
	var accpublic = getValuebyName("ACCPUBLIC")+"00000000000";
	// 账户控制参数  
	// 原qxpublic 31-40为账户控制参数
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// |||||||_7 1=开通会员商城功能
	// ||||||__6 1=开通采购商城后台功能()
	// |||||_5 1=开通订货会功能
	// ||||__4 1=订货会截屏锁住
	// |||___3 1=允许更改单据日期
	// ||____2 1=表示分店铺算成本
	// |_____1 1=表示允许转枫杨果

	if (qxpublic == undefined || qxpublic == "" || qxpublic == "null" || qxpublic == null) {
		alerttext('您的网络不佳，我们丢失了一些数据，请刷新页面重试！')
	} else {
		//权限参数
		qxkz = Number(qxpublic.charAt(0));
		allowfsck = Number(qxpublic.charAt(1));
		allownearsale = Number(qxpublic.charAt(2));
		points = Number(qxpublic.charAt(3));
		centbl = Number(qxpublic.substring(4, 8));
		autowareno = Number(qxpublic.charAt(11));
		xsdpdz = Number(qxpublic.charAt(13));
		dpjbfs = Number(qxpublic.charAt(14));
		lsmlfs = Number(qxpublic.charAt(15));
		usesyt = Number(qxpublic.charAt(16));
		useyhq = Number(qxpublic.charAt(17));
		usepfsyt = Number(qxpublic.charAt(18));
		cgthpdgys = Number(qxpublic.charAt(19));
		jzzdgd = Number(qxpublic.charAt(20));//结账自动勾单
		lspoints = Number(qxpublic.charAt(21));//零售金额小数位数
		//角色参数
		allowinsale = Number(rolepublic.charAt(0));
		allowshare = Number(rolepublic.charAt(1));
		allowprymyj = Number(rolepublic.charAt(2));
		allowchedan = Number(rolepublic.charAt(6));
		allowclear = Number(rolepublic.charAt(7));
		allowscanware = Number(rolepublic.charAt(8));
		//账号参数
		allowchangdate = Number(accpublic.charAt(2));
	}
}
function getbypoints(price){
	return Number(Number(price).toFixed(points));
}
function getbylspoints(price){
	return Number(Number(price).toFixed(lspoints));
}
var userAgent = navigator.userAgent,
rMsie = /(msie\s|trident.*rv:)([\w.]+)/,
rFirefox = /(mozilla|firefox)\/([\w.]+)/,
rOpera = /(opera).+version\/([\w.]+)/,
rChrome = /(chrome)\/([\w.]+)/,
rSafari = /version\/([\w.]+).*(safari)/;
var browser;
var version;
var ua = userAgent.toLowerCase();
function uaMatch(ua) {
	try {
		var match = rMsie.exec(ua);
		if (match != null) {
			return {
				browser: "IE",
				version: match[2] || "0"
			}
		}
		var match = rFirefox.exec(ua);
		if (match != null) {
			return {
				browser: match[1] || "",
				version: match[2] || "0"
			}
		}
		var match = rOpera.exec(ua);
		if (match != null) {
			return {
				browser: match[1] || "",
				version: match[2] || "0"
			}
		}
		var match = rChrome.exec(ua);
		if (match != null) {
			return {
				browser: match[1] || "",
				version: match[2] || "0"
			}
		}
		var match = rSafari.exec(ua);
		if (match != null) {
			return {
				browser: match[2] || "",
				version: match[1] || "0"
			}
		}
		if (match != null) {
			return {
				browser: "",
				version: "0"
			}
		}
	} catch(e) {
		alert(e.message)
	}
}
var browserMatch = uaMatch(ua);
if (browserMatch.browser) {
	browser = browserMatch.browser;
	version = browserMatch.version
}
window.onerror = function(message,url,line){
	if(!top.$("div.loading-modal").is(":hidden")){
		top.$("div.loading-modal").hide();
	}
	var errmsg = url+"-->"+line+"-->"+message;
	//执行ajax中遇到错误，30秒后终止请求
	if(JSON.stringify(pendingRequests)!="{}"&&url.indexOf("jquery.min.js")==-1&&url.indexOf("jquery.easyui.min.js")==-1){
		errmsg = "执行ajax的时候:"+ JSON.stringify(pendingRequests)+errmsg;
		pendingRequests = {};
		alerthide();
		$.messager.confirm(dialog_title, "十分抱歉，刚才的操作网络存在波动，请刷新查看是否操作成功！如果没有成功请重试，确认是否刷新？",
				function(r) {
					if (r) {
						window.location.reload();
					}
				});
	}
	console.error(errmsg);
	top.wrtiejsErrorLog(errmsg);
	return false;
}
$.fn.onlyNum = function() {
	$(this).keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if ((keyCode >= 48 && keyCode <= 57 || keyCode == 9 || keyCode == 8)) return true;
		else return false
	}).focus(function() {
		this.style.imeMode = 'disabled'
	});
};
$.fn.onlyAlpha = function() {
	$(this).keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if ((keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || keyCode == 9 || keyCode == 8) return true;
		else return false
	}).focus(function() {
		this.style.imeMode = 'disabled'
	});
};
$.fn.onlyWareno = function() {
	$(this).keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || keyCode == 46 || keyCode == 9 || keyCode == 8) return true;
		else return false
	}).focus(function() {
		this.style.imeMode = 'disabled'
	});
};
$.fn.onlyNumAlpha = function() {
	$(this).keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122) || keyCode == 9 || keyCode == 8) return true;
		else return false
	}).focus(function() {
		this.style.imeMode = 'disabled'
	});
};
$.fn.onlyMoney = function() {
	$(this).keyup(function(event) {
		this.value = this.value.replace(/[^-?0-9.]/g,"");;
	}).focus(function() {
		this.style.imeMode = 'disabled'
	});
};
$.fn.onlyDisc = function() {
	$(this).keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if (this.value.indexOf(".") != -1 && keyCode == 46) return false;
		if (this.value.length == 0) {
			return keyCode == 48 || keyCode == 9
		} else {
			return keyCode >= 48 && keyCode <= 57 || keyCode == 46 || keyCode == 9
		}
	}).focus(function() {
		this.style.imeMode = 'disabled'
	});
};
$.fn.selHelpInput = function() {
	var bol = $(this).hasClass("combox-input");
	if (!bol) {
		var div = '<div class="combox-select"></div>';
		var ul = '<ul class="combox-selects" style="display: none;"></ul>';
		var span = '<span class="help-s-btn" onclick="$(this).parent().next().click()">▼</span>';
		$(this).wrap(div);
		$(this).addClass('combox-input');
		$(this).parent().append(span);
		$(this).parent().append(ul)
	}
}
$.fn.HelpInput = function() {
	var bol = $(this).hasClass("combox-input");
	if (!bol) {
		var div = '<div class="combox-select"></div>';
		var ul = '<ul class="combox-selects" style="display: none;"></ul>';
		var span = '<span class="help-s-btn" onclick="$(this).next().show()">▼</span>';
		$(this).wrap(div);
		$(this).addClass('combox-input');
		$(this).parent().append(span);
		$(this).parent().append(ul)
	}
}
$.fn.gethelpval = function() {
	return $(this).parent().children("input[type=hidden]").val()
}
$.fn.sethelpval = function(value) {
	$(this).parent().children("input[type=hidden]").val(value)
}
var comboxseltimer = null;
var comboxdofn = function(objs, name, value, row, $ipt) {
	if (comboxseltimer != null) {
		clearTimeout(comboxseltimer);
		comboxseltimer = null;
	}
	switch (objs) {
	case "changenotehouse":
		var id = $('#uid').val();
		var noteno = $('#unoteno').val();
		var oldval = $('#uhouseid').val();
		if (oldval != value) {
			changehouse(id, noteno, value)
		}
		$('#uhouseid').val(value);
		$('#uhousename').val(name);
		break;
	case "changenotetohouse":
		var id = $('#uid').val();
		var noteno = $('#unoteno').val();
		var oldval = $('#utohouseid').val();
		if (oldval != value) {
			changetohouse(id, noteno, value)
		}
		$('#utohouseid').val(value);
		$('#utohousename').val(name);
		break;
	case "changenotecust":
		var id = $('#uid').val();
		var noteno = $('#unoteno').val();
		var oldval = $('#ucustid').val();
		if (oldval != value) {
			changecust(id, noteno, value, row.DISCOUNT, row.PRICETYPE)
		}
		$('#ucustname').val(name);
		$('#ucustid').val(value);
		$('#udiscount').val(row.DISCOUNT);
		$('#upricetype').val(row.PRICETYPE);
		break;
	case "changesaleman":
		writesaleman(value,1,1);
		$('#usalemanname').val(name);
		break;
	case "changenoteprov":
		var id = $('#uid').val();
		var noteno = $('#unoteno').val();
		var oldval = $('#uprovid').val();
		if (oldval != value) {
			changeprov(id, noteno, value, row.DISCOUNT, row.PRICETYPE)
		}
		$('#uprovname').val(name);
		$('#uprovid').val(value);
		$('#udiscount').val(row.DISCOUNT);
		$('#upricetype').val(row.PRICETYPE);
		break;
	case "changedpt":
		var oldval = $('#udptid').val();
		if (oldval != value) {
			changedpt(value)
		}
		$('#udptname').val(name);
		$('#udptid').val(value);
		break;
	case "viplist":
		var oldval = $('#uguestid').val();
		$('#uguestid').val(value);
		$('#udiscount').val(row.DISCOUNT);
		$('#alldiscount').val(row.DISCOUNT);
		$('#uguestname').val(row.GUESTNAME);
		$('#vipno').html(row.VIPNO);
		$('#mobile').html(row.MOBILE);
		$('#balcent').html(row.BALCENT);
		$('#vtid').val(row.VTID);
		$('#vtname').html(row.VTNAME);
		$('#balcurr').html(row.BALCURR);
		if (oldval != value) {
			changeguest(value);
			alldisc();
		}
		$('#guestinfo').show();
		break;
	case "quickaddwarem":
		if (isAddWarem()) {
			var noteno = $('#unoteno').val();
			$('#wquickuwareid').val(value);
			getwaremsum(value, noteno)
		}
		break;
	case "quickaddwarems":
		if (isAddWarem()) {
			$('#wquickuwareid').val(value);
			var noteno = $('#unoteno').val();
			var saleid = $('#usaleid').val();
			getwaremsum(saleid, value, noteno)
		}
		break;
	case "qaddawarem":
		if (isAddWarem()) {
			var wareid = $('#wquickuwareid').val();
			if (isAddWarem() && wareid != '') {
				var noteno = $('#unoteno').val();
				$('#usaleid').val(value);
				getwaremsum(value, wareid, noteno)
			} else {
				alerttext('请输入货号！');
				$('#wquickuwareno').focus()
			}
		}
		break;
	case "getwaresaleid":
		if (isAddWarem()) {
			$ipt.val(name);
			if (value == undefined) return;
			$('#wquickuwareid').val(value);
			getnearsaleid(value)
		}
		break;
	case "tjquickuwareno":
		if (isAddWarem()) {
			$ipt.val(name);
			if (value == undefined) return;
			$('#tjquickuwareid').val(value);
			addwareadjustm(value, $('#unoteno').val())
		}
		break;
	case "printaddwarem":
		if (value == undefined) return;
		$('#wquickuwareid').val(value);
		getbarcodeprintsum(value);
		break;
	case "shopsalevip":
		var oldval = $('#guestid').val();
		$('#guestid').val(row.GUESTID);
		$('#discount').val(row.DISCOUNT);
		$('#guestfindbox').val(row.GUESTNAME);
		$('#guestname').html(row.GUESTNAME);
		$('#vipno').html(row.VIPNO);
		$('#mobile').html(row.MOBILE);
		$('#balcent').html(row.BALCENT);
		$('#vtid').val(row.VTID);
		$('#vtname').html(row.VTNAME);
		$('#balcurr').html(row.BALCURR);
		if (oldval != value) {
			changeguest(value);
			changediscount()
		}
		break;
	case "shopsaleware":
		var salemanid = $('#epid').val();
		var salemanno = $('#saleman').val();
		if (salemanid == "" && salemanno == "") {
			alerttext('请选择销售人！');
			$('#wareno').val("");
			$('#saleman').focus()
		} else {
			$('#wareno').val(name);
			$('#warename').val(row.WARENAME);
			$('#wareid').val(value);
			getsaleoutprice(value);
			$('#colorname').val('');
			$('#sizename').val('');
			$('#colorid').val('');
			$('#sizeid').val('')
		}
		break;
	case "tjquickuwareno":
		if (isAddWarem()) {
			$ipt.val(name);
			if (value == undefined) return;
			$('#tjquickuwareid').val(value);
			addwareadjustm(value, $('#unoteno').val())
		}
		break;
	default:
		$(objs).val(value);
		$ipt.val(name);
		var eventstr = $ipt.data("event");
		if (eventstr) {
			var eventobj = new Function("return {" + eventstr + "}")();
			eventobj.onSelect(row);
		}
		break
	}
	$('ul.combox-selects').html("");
	$('ul.combox-selects').hide()
}
function autofn() {
	var target = ".edithelpinput,.edithelpinputs,.helpipt";
	$('body').delegate(target, 'keyup',
	function(e) {
		var key = e.which;
		var $ipt = $(this);
		var sel = $ipt.next().next().children('.combox-selected');
		$('.combox-selected').removeClass("combox-selected");
		var uls = $ipt.next().next();
		var ulslis = uls.children("li");
		var ulscro = uls.height();
		var ulscorl = uls.scrollTop();
		var thisindex = ulslis.index(sel) + 1;
		var isdisplay = uls.css('display') == "none" ? false: true;
		var objs = $ipt.data("value");
		if (key == 13 || key == 38 || key == 40 || key == 8) {
			var data = sel.data('datas');
			var isdata = data == undefined ? false: true;
			switch (key) {
			case 8:
				if (this.value.length < 2) {
					if (this.value.length == 0) {
						switch (objs) {
						case "changenotehouse":
							$('#uhouseid').val("");
							alerttext('请选择！', 2000);
							break;
						case "changenotetohouse":
							$('#utohouseid').val('');
							alerttext('请选择！', 2000);
							break;
						case "changedpt":
							$('#udptid').val('');
							alerttext('请选择！', 2000);
							break;
						case "changenotecust":
							$('#ucustid').val('');
							alerttext('请选择！', 2000);
							break;
						case "changenoteprov":
							$('#uprovid').val('');
							alerttext('请选择！', 2000);
							break;
						case "viplist":
							$('#uguestid').val('');
							alerttext('请选择！', 2000);
							break;
						case "quickaddwarem":
							$('#wquickuwareid').val('');
							alerttext('请选择！', 2000);
							break;
						case "quickaddwarems":
							$('#wquickuwareid').val('');
							alerttext('请选择！', 2000);
							break;
						case "qaddawarem":
							$('#usaleid').val('');
							alerttext('请选择！', 2000);
							break;
						case "getwaresaleid":
							$('#wquickuwareid').val('');
							alerttext('请选择！', 2000);
							break;
						default:
							$(objs).val('');
							break
						}
					}
					$('ul.combox-selects').html("");
					$('ul.combox-selects').hide()
				}
				break;
			case 13:
				if (sel.length != 0) {
//					e.stopPropagation();
					var name = "";
					var vlaue = "";
					var row = {};
					if (isdata) {
						name = data.name;
						value = data.value;
						row = data.row;
						comboxdofn(objs, name, value, row, $ipt);
					}
				} else if ($ipt.data("quickkeys") && $ipt.val().length >= 2) {
//					e.stopPropagation();
					gethelp($ipt, $ipt.val(), $ipt.data("quickkeys"));
				} else if ((!$('#ud').is(":hidden") || pgid == "pg1015") && $('#wquickuwareno').length > 0 && ($('#wquickuwareid').val() != '' || ($(objs).val() != '' && $(objs).length != 0 && objs != undefined))) {
//					e.stopPropagation();
					switch (objs) {
					case "quickaddwarem":
						if (isAddWarem()) {
							var wareid = $('#wquickuwareid').val();
							var noteno = $('#unoteno').val();
							getwaremsum(wareid, noteno)
						}
						break;
					case "quickaddwarems":
						if (isAddWarem()) {
							var wareid = $('#wquickuwareid').val();
							var noteno = $('#unoteno').val();
							var saleid = $('#usaleid').val();
							getwaremsum(saleid, wareid, noteno)
						}
						break;
					case "qaddwarem":
						if (isAddWarem()) {
							var wareid = $('#wquickuwareid').val();
							var noteno = $('#unoteno').val();
							var saleid = $('#usaleid').val();
							getwaremsum(saleid, wareid, noteno)
						}
						break;
					case "getwaresaleid":
						var wareid = $('#wquickuwareid').val();
						getnearsaleid(wareid);
						break;
					case "printaddwarem":
						var wareid = $('#wquickuwareid').val();
						getbarcodeprintsum(wareid);
						break;
					default:
						break;
					}
				} else if ((!$('#ud').is(":hidden")) && $('#wquickuwareno').length > 0 && (objs == "getwaresaleid" || objs == "quickaddwarems" || objs == "quickaddwarem") && $('#wquickuwareid').val() == '' && $('#wquickuwareno').val() == '' && $('#waret').datagrid('getRows').length != 0) {
					$('[data-btn=updatebtn]').click()
				} else if ((!$('#ud').is(":hidden")) && $('#wquickuwareno').length > 0 && $('#wquickuwareid').val() == '' && $('#wquickuwareno').val() == '' && $('#waret').length > 0) {
					if ($('#waret').data("datagrid")) {
						if ($('#waret').datagrid('getRows').length == 0) {
							alerttext('请选择货号！');
							$('#wquickuwareno').focus()
						}
					}
				}
				//					$('ul.combox-selects').html("");
				//					$('ul.combox-selects').hide()
				break;
			case 38:
				if (thisindex != 1 && isdisplay) {
					uls.scrollTop((thisindex - 2) * 30);
					sel.prev().addClass("combox-selected")
				} else {
					ulslis.last().addClass("combox-selected");
					var li_count = ulslis.length;
					uls.scrollTop(li_count * 30 + 25)
				}
				break;
			case 40:
				if (thisindex != ulslis.length && isdisplay) {
					uls.scrollTop(thisindex * 30);
					sel.next().addClass("combox-selected")
				} else {
					ulslis.first().addClass("combox-selected");
					uls.scrollTop(0)
				}
				break;
			default:
				break
			}
		} else {
			switch (objs) {
			case "changenotehouse":
				if (isdata) {
					$('#uhouseid').val('')
				}
				break;
			case "changenotetohouse":
				if (isdata) {
					$('#utohouseid').val('')
				}
				break;
			case "changenotecust":
				if (isdata) {
					$('#ucustid').val('')
				}
				break;
			case "changenoteprov":
				if (isdata) {
					$('#uprovid').val('')
				}
				break;
			case "changedpt":
				if (isdata) {
					$('#udptid').val('')
				}
				break;
			case "viplist":
				if (isdata) {
					$('#uguestid').val('');
					$('#guestinfo').hide()
				}
				break;
			case "quickaddwarem":
				if (isAddWarem()) {
					e.preventDefault();
					$('#wquickuwareid').val('')
				}
				break;
			case "quickaddwarems":
				if (isAddWarem()) {
					e.preventDefault();
					$('#wquickuwareid').val('')
				}
				break;
			case "qaddawarem":
				if (isAddWarem()) {
					var wareid = $('#wquickuwareid').val();
					if (isAddWarem() && wareid != '') {
						$('#usaleid').val('')
					}
				}
				break;
			case "getwaresaleid":
				if (isAddWarem()) {
					$('#wquickuwareid').val('');
				}
				break;
			case "printaddwarem":
				$('#wquickuwareid').val('');
				break;
			case "shopsalevip":
				if (isdata) {
					var oldval = $('#guestid').val();
					$('#guestid').val('');
					$('#discount').val('1.00');
					$('#vipno').html('');
					$('#mobile').html('');
					$('#balcent').html('');
					$('#vtid').val('');
					$('#vtname').html('');
					$('#balcurr').html('');
					if (oldval != '') {
						changeguest();
						changediscount()
					}
				}
				break;
			case "shopsaleware":
				if (isdata) {
					$('#warename').val('');
					$('#wareid').val('');
					$('#price0').val('0.00');
					$('#price').val('0.00');
					$('#curr').val('0.00');
					$('#colorname').val('');
					$('#sizename').val('');
					$('#colorid').val('');
					$('#sizeid').val('')
				}
				break;
			default:
				$(objs).val('');
				break
			}
		}
	});
	$('ul.combox-selects').delegate('li', 'click',
	function(e) {
		var sel = $(this);
		var $ipt = sel.parent().siblings('input[type=text]');
		var objs = $ipt.data("value");
		var data = sel.data('datas');
		var isdata = data == undefined ? false: true;
		var name = "";
		var vlaue = "";
		var row = {};
		if (isdata) {
			name = data.name;
			value = data.value;
			row = data.row;
			comboxdofn(objs, name, value, row, $ipt);
		}
		//		$('ul.combox-selects').html("");
		//		$('ul.combox-selects').hide()
	});
	$(target).click(function(e) {
		var $ipt = $(this);
		var readonly = $ipt.attr("readonly") == "readonly" ? false: true;
		var lilenghth = $ipt.parent().children('ul').children('li').length;
		if (readonly && lilenghth > 0) {
			$(target).parent().children('ul').hide();
			$ipt.parent().children('ul').slideDown('fast')
		}
	}).focus(function(e) {
		var $ipt = $(this);
		var isp = $ipt.parent().children('ul').css('display') == "none" ? false: true;
		var readonly = $ipt.attr("readonly") == "readonly" ? false: true;
		var lilenghth = $ipt.parent().children('ul').children('li').length;
		if (!isp && readonly && lilenghth > 0) {
			$(target).parent().children('ul').hide();
			$ipt.parent().children('ul').slideDown('fast')
		}
	});
	$(target).bind('blur',
	function(e) {
		var $ipt = $(this);
		var $it = $ipt.parent();
		var uls = $it.children("ul");
		var sel = $it.children("ul").children('li.combox-selected');
		var lilen = sel.length;
		var objs = $ipt.data("value");
		comboxseltimer = setTimeout(function() {
			/*
			if (lilen != 0) {
				var data = sel.data('datas');
				var isdata = data==undefined?false:true;
				var name = "";
				var vlaue = "";
				var row = {};
				if(isdata){
					name = data.name;
					value= data.value;
					row = data.row;
					comboxdofn(objs,name,value,row,$ipt);
				}
			}
			*/
			$('ul.combox-selects').html("");
			$('ul.combox-selects').hide()
		},
		300);
	})
}
function strlen(str) {
	var strlength;
	var i;
	strlength = 0;
	for (i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 255) strlength += 2;
		else strlength++
	}
	return strlength
}
function getwarenolist(findbox, obj) {
	if (strlen(findbox) < 2) return;
	var provid = "-1";
	if (obj.attr("id") == "wquickuwareno" && showprovware==1 &&(pgid == "pg1101" || pgid == "pg1102" || pgid == "pg1103")){
		if($("#showprovware").prop("checked"))
			provid = $('#uprovid').val();
	}
	$ul = obj.parent().children("ul");
	if (strlen(findbox) >= 2) {
		$.ajax({
			type: "POST",
			url: "/skydesk/fyjerp",
//			async: false,
			data: {
				erpser: "warecodelisthelp",
				findbox: findbox,
				provid: provid,
				rows: pagecount,
				fieldlist: "A.WAREID,A.WARENO,A.WARENAME,A.UNITS,A.SHORTNAME,A.BRANDID,A.SEASONNAME,A.TYPEID,A.PRODYEAR,A.PRODNO,A.ENTERSALE,A.RETAILSALE,A.SALE1,A.SALE2,A.SALE3,A.SALE4,A.SALE5,A.REMARK,A.SIZEGROUPNO,A.NOUSED,A.DOWNENABLED,C.BRANDNAME,B.TYPENAME,B.FULLNAME,a.IMAGENAME0",
				page: 1
			},
			dataType: 'json',
			success: function(data) {
				try {
					if (valideData(data)) {
						var total = data.total;
						if (total > 0) {
							var rows = data.rows;
							$ul.html('');
							for (var i = 0; i < rows.length; i++) {
								var pgids;
								var row = rows[i];
								var name = row.WARENO;
								var value = row.WAREID;
								var $li = $("<li></li>");
								var litext = row.WARENO + ':' + row.WARENAME + ' , 品牌：' + row.BRANDNAME + ' , 零售价：' + row.RETAILSALE;
								var datas = {
									name: name,
									value: value,
									row: row
								};
								$li.data("datas", datas);
								if (pgid == undefined) pgids = 'no';
								else pgids = pgid;
								if (i == 0) $li.addClass("combox-selected");
								if ("pg1301,pg1302,pg1303,pg1309,pg1314,pg1315,pg1316".indexOf(pgids)>=0) 
									litext += ' , 批发价：' + row.SALE4;
								$li.html(litext);
								$ul.append($li);
							}
						} else {
							$ul.html('');
							$ul.append('<li>无该商品</li>')
						}
						$ul.show()
					}
				} catch(ex) {
					console.error(ex.message);
				}
			}
		})
	} else $ul.html('')
}
$.fn.swareno = function() {
	var sel = $(this);
	sel.data("quickkeys", "wareno");
	$(this).keyup(function(e) {
		var key = e.which;
		if ((key > 40 || key < 38) && key != 13) {
			clearTimeout(gethelptimer);
			var findbox = this.value;
			gethelptimer = setTimeout(function() {
				getwarenolist(findbox, sel)
			},
			300)
		}
	})
}
$.fn.quickwareno = function(jq) {
	var sel = $(this);
	sel.data("quickkeys", "wareno");
	$(this).keyup(function(e) {
		var key = e.which;
		if ((key > 40 || key < 38) && key != 13) {
			clearTimeout(gethelptimer);
			var findbox = this.value;
			gethelptimer = setTimeout(function() {
				getwarenolist(findbox, sel)
			},
			300)
		}
	})
}
var gethelptimer = null;
$.fn.gethelp = function(keys) {
	var sel = this;
	if ($(sel).length > 0) {
		$(sel).data("quickkeys", keys);
		var hasul = $(sel).parent().children("ul").length > 0;
		if (!hasul) $(sel).parent().append('<ul class="combox-selects" style="display: none;" id="ss"></ul>');
		$(this).on("keyup",
		function(e) {
			var key = e.which;
			if ((key > 40 || key < 38) && key != 13) {
				if (gethelptimer != null) clearTimeout(gethelptimer);
				var findbox = this.value;
				var readonly = $(this).attr("readonly") == "readonly" ? false: true;
				if (readonly) {
					var $sel = $(this);
					if (strlen(findbox) < 2) {
						$sel.html('');
						return;
					}
					gethelptimer = setTimeout(function() {
						gethelp($sel, findbox, keys)
					},
					300);
				}
//				e.stopPropagation();
			}
		})
	}
}
var opencombox = function(obj) {
	var ob = $(obj).prev().children('input[type=text]');
	var key = "season";
	if (ob.hasClass("units_help")) key = "units";
	gethelp(ob, "", key);
}
function gethelp(obj, findbox, key) {
	var $ul = obj.parent().children("ul");
	findbox = findbox.toUpperCase();
	var namekey = "";
	var valuekey = "";
	var hp = "";
	var url = "/skydesk/fyjerp";
	var erpser = "";
	switch (key) {
	case "brand":
		erpser = "brandhelplist";
		hp = "BRAND";
		break;
	case "color":
		erpser = "getcolorddata";
		hp = "COLOR";
		break;
	case "cust":
		erpser = "customerlisthelp";
		hp = "CUST";
		break;
	case "buyer":
		erpser = "mybuyerlist";
		hp = "CUST";
		break;
	case "vip":
		url = "/skydesk/fybuyjerp";
		erpser = "guestviplist";
		hp = "GUEST";
		break;
	case "viptp":
		url = "/skydesk/fybuyjerp";
		erpser = "guesttypelist";
		hp = "VT";
		break;
	case "role":
		erpser = "userrolelist";
		hp = "LEVEL";
		break;
	case "provd":
		erpser = "providelisthelp";
		hp = "PROV";
		break;
	case "emply":
	case "saleman":
		erpser = "employelist";
		hp = "EP";
		break;
	case "salename":
		erpser = "salecodelist";
		hp = "SALE";
		break;
	case "house":
		erpser = "warehousehelplist";
		hp = "HOUSE";
		break;
	case "wareno":
		erpser = "warecodelisthelp";
		hp = "WARE";
		break;
	case "dpt":
		erpser = "departmentlist";
		hp = "DPT";
		break;
	case "pw":
		erpser = "paywaylist";
		hp = "PAY";
		break;
	case "cg":
		erpser = "chargeslist";
		hp = "CG";
		break;
	case "size":
		erpser = "getwaresize";
		hp = "SIZE";
		break;
	case "wave":
		erpser = "warewavelist";
		hp = "WAVE";
		break;
	case "area":
		erpser = "arealist";
		hp = "AREA";
		break;
	case "carry":
		erpser = "carrylinelist";
		hp = "CARRY";
		url="/skydesk/fybuyjerp";
		break;
	case "season":
		erpser = "getseason";
		hp = "season";
		url = "/skydesk/jsonfiles/season.json";
		break;
	case "units":
		helpser = "getunits";
		hp = "UNITS";
		url = "/skydesk/jsonfiles/units.json";
		break;
	default:
		break
	}
	namekey = hp + "NAME";
	valuekey = hp + "ID";
	if (key == "units" || key == "season") {
		namekey = "name";
		valuekey = "value";
	}else if(key == "buyer"){
		namekey = "CUSTNAME";
		valuekey = "ACCID";
	}
	var getlitext = function($li, row) {
		$li.html(row[namekey]);
	}
	var ajaxdata = {
			erpser: erpser,
			findbox: findbox,
			noused: 0,
			fieldlist: "*",
			rows: pagecount,
			page: 1
	};
	if (key == "wareno") {
		getwarenolist(findbox, obj);
		return;
	} else if (key == "cust") {
		ajaxdata.fieldlist = "a.custid,a.custname,a.linkman,a.mobile,a.tel,a.address,a.discount,a.pricetype,a.areaname";
		getlitext = function($li, row) {
			$li.html(row["CUSTNAME"] + "," + row["LINKMAN"]+ "," + row["MOBILE"]+ "," + row["TEL"]+ "," + row["AREANAME"]+ "," + row["ADDRESS"]);
		}
	} else if (key == "house") {
		var flag = obj.data('cxdr') == undefined ? "0": "1";
		var qxfs = 1;
		if(pgid&&(pgid=="pg1102"||pgid=="pg1103"||pgid=="pg1508"||pgid=="pg1613"||(pgid=="pg1403"&&flags!=1))) qxfs=2;
		else if(pgid&&(pgid=="pg1201"||pgid=="pg1210"||pgid=="pg1302"||pgid=="pg1303"||pgid=="pg1309"||(pgid=="pg1402"&&flags!=1)||pgid=="pg1501"||pgid=="pg1502"||pgid=="pg1602"||pgid=="pg1601"||pgid=="pg1607"||pgid=="pg1908"||pgid=="pg1608")) qxfs = 3;
		else if(pgid&&(pgid=="pg1402"||pgid=="pg1403")&&flags==1) qxfs = 0;
		else qxfs = 1;
		ajaxdata.qxfs = qxfs;
		ajaxdata.fieldlist = "houseid,housename,pricetype1";
	} else if (key == "provd") {
		var flag = obj.data('showall') == undefined ? "0": "1";
		ajaxdata.showall = flag;
	} else if (key == "vip") {
		ajaxdata.usetag = 0;
		getlitext = function($li, row) {
			$li.html(row["VIPNO"] + ":" + row["GUESTNAME"]);
		}
	} else {
		var flag = $('#flag').val() == undefined ? "0": "1";
		ajaxdata.flag = flag;
	}
	$.ajax({
		type: "POST",
		url: url,
//		async: false,
		data: ajaxdata,
		dataType: 'json',
		success: function(data) {
			try {
				if (valideData(data)) {
					var total = data.total;
					if (total > 0) {
						var rows = data.rows;
						$ul.html('');
						for (var i = 0; i < rows.length; i++) {
							var pgids;
							var row = rows[i];
							var name = row[namekey];
							var value = row[valuekey];
							var $li = $("<li></li>");
							var litext = getlitext($li, row);
							var datas = {
								name: name,
								value: value,
								row: row
							};
							$li.data("datas", datas);
							if (pgid == undefined) pgids = 'no';
							else pgids = pgid;
							if (i == 0) $li.addClass("combox-selected");
							$li.html(litext);
							if (key == "units" || key == "season" || key == "year") {
								if (name.indexOf(findbox) == -1 && row.sybj.indexOf(findbox) == -1) continue;
							}
							$ul.append($li);
						}
					} else {
						$ul.html('');
						$ul.append('<li>无</li>')
					}
					$ul.show()
				}
			} catch(ex) {
				console.error(ex.message);
			}
		}
	})
}
$.fn.isShow = function() {
	for (var i in this) {
		if (! ($(this).css('display') == "none")) return true
	}
	return false
}
function autokey() {
	var $inp = jQuery('body');
	var jq = 'input:text:not(#quickutables input:text,.searchbar input:text)';
	jq += ',input[type=button]:not(.m-btn,#quickadd_btn)';
	jq += ',select:not([disabled])';
	$inp.delegate(jq, 'keyup',
	function(e) {
		var key = e.which;
		switch (key) {
		case 13:
			var $them = $(jq);
			var relobj = $(e.target).data("value");
			var relvalue = "1";
			if (relobj && relobj.length > 0) {
				if (relobj.indexOf("#") >= 0) relvalue = $(relobj).val();
				else relvalue = "0";
			}
			var isend = $(this).data("end") == 'yes';
			if (relvalue == "" || relvalue == "0") isend = true;
			if (isend) return;
			var nxtIdx = $them.index(this) + 1;
			var nxtinp = $them.eq(nxtIdx);
			if (nxtinp.length != 0 && !isend) {
				nxtinp.focus();
				nxtinp.select()
			}
			break;
		default:
			break
		}
	})
}
function addAutokey() {
	$('#quickutable').delegate('#quickutables input[type=text]', 'keyup',
	function(e) {
		var $them = $('#quickutables input[type=text]');
		var txtcols = $('#quickutables tr:eq(1) input[type=text]').length;
		var txtrows = $('#quickutables tr').length - 2;
		var allidx = txtcols * txtrows;
		var lstrowIdx = $them.index(this) - txtcols;
		var nxtrowIdx = $them.index(this) + txtcols;
		var lstIdx = $them.index(this) - 1;
		var nxtIdx = $them.index(this) + 1;
		var lstinp = $them.eq(lstIdx);
		var nxtinp = $them.eq(nxtIdx);
		var lstrowinp = $them.eq(lstrowIdx);
		var nxtrowinp = $them.eq(nxtrowIdx);
		var isend = $(this).data("end") == 'yes';
		if ($(this).hasClass('reg-amt')) {
			if (this.value != "-") {
				if (this.value != "") {
					var val = this.value.replace(/[^-?\d.*$]/g, '');
					//						this.value = Number(this.value.replace(/[^-?\d.*$]/g, '')) == 0 ? "0": Number(this.value.replace(/[^-?\d.*$]/g, ''));
					this.value = val;
					this.onafterpaste = Number(this.value.replace(/[^-?\d.*$]/g, '')) == 0 ? "0": Number(this.value.replace(/[^-?\d.*$]/g, ''))
				}
				quickupdatesum();
			}
		} else if ($(this).hasClass('reg-disc') || $(this).hasClass('reg-pri')) {}
		var key = e.which;
		switch (key) {
		case 13:
			if (nxtinp.length != 0 && !isend) {
				nxtinp.focus();
				nxtinp.select()
			} else {
//				var eq = $('.easyui-dialog input[type=text],body input[type=button]').index(this) + 1;
//				var eqipt = $('.easyui-dialog input[type=text],body input[type=button]').eq(eq);
//				eqipt.focus()
				quickaddwarem();
			}
			break;
		case 37:
			e.preventDefault();
			if (lstinp.length != 0 && !isend) {
				lstinp.focus();
				lstinp.select()
			}
			break;
		case 38:
			e.preventDefault();
			if (lstrowinp.length != 0 && !isend) {
				lstrowinp.focus();
				lstrowinp.select()
			}
			break;
		case 39:
			e.preventDefault();
			if (nxtinp.length != 0 && !isend) {
				nxtinp.focus();
				nxtinp.select()
			}
			break;
		case 40:
			e.preventDefault();
			if (nxtrowinp.length != 0 && !isend) {
				nxtrowinp.focus();
				nxtrowinp.select()
			}
			break;
		default:
			break
		}
	});
	$('#quickud').on('keydown',
	function(e) {
		var key = e.which;
		switch (key) {
		case 107:
		case 115:
			e.preventDefault();
			quickaddwarem();
			break;
		default:
			break
		}
		var $target = $(e.target);
		if (e.ctrlKey && key == 65 && $target.hasClass('reg-amt')) { //ctrl+a 全部设置
			e.preventDefault();
			var $allamtinpt = $('#quickutables input.reg-amt');
			var amt = Number($target.val());
			if (amt > 0) {
				$allamtinpt.val(amt);
			}
		} else if (e.ctrlKey && key == 68 && $target.hasClass('reg-amt')) { //ctrl+D 列设置
			e.preventDefault();
			var colnum = $target.data('colnum');
			var $other_cols = $('#quickutables input[data-colnum=' + colnum + ']');
			var amt = Number($target.val());
			if (amt > 0) {
				var start = $other_cols.index($target);
				$other_cols.slice(start).val(amt);
			}
		} else if (e.ctrlKey && key == 75 && $target.hasClass('reg-amt')) { //ctrl+K 查看其他店库存
			e.preventDefault();
			if($target.parent().parent().find("input:hidden").length==1&&(pgid=="pg1302"||pgid=="pg1303"||pgid=="pg1309")){
				var colorid = Number($target.parent().parent().find("input:hidden").val());
				if(!isNaN(colorid)){
					$("#othercolorid").val(colorid);
					openotherhousekct($target);
				}else alerttext("颜色无效");
			}
		} else if (e.ctrlKey && key == 83 && $target.hasClass('reg-amt')) { //ctrl+S 行设置
			e.preventDefault();
			var rownum = $target.data('rownum');
			var $other_rows = $('#quickutables input[data-rownum=' + rownum + ']');
			var amt = Number($target.val());
			if (amt > 0) {
				var start = $other_rows.index($target);
				$other_rows.slice(start).val(amt);
			}
		} else if (e.ctrlKey && key == 90 && $target.hasClass('reg-amt')) { //ctrl+S 行设置
			e.preventDefault();
			var $allamtinpt = $('#quickutables input.reg-amt');
			$allamtinpt.val('');
		}
	});
	$('#alldisc').keyup(function(e) {
		if (this.value.replace(" ", "") != "") {
			var line = $('#quickutables tr').length - 2; // 表格行数
			this.value = this.value.replace(/[^\d.]/g, "");
			this.value = this.value.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
			this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
			if (Number(this.value) > 10) {
				alerttext("最大折扣为10", 1500);
				this.value = "10";
			}
			var disco = Number(this.value);
			changealldisc(disco, line);
		}
	}).change(function(){
		this.value=Number(this.value).toFixed(2);
	});
	$('#allpri').keyup(function(e) {
		if (this.value.replace(" ", "") != "") {
			var line = $('#quickutables tr').length - 2; // 表格行数
			this.value = this.value.replace(/[^\d.]/g, "");
			var pric = Number(this.value);
			changeallpri(pric, line);
		}
	}).change(function(){
		this.value=getbypoints(this.value).toFixed(2);
	});
	$("#quickadd_btn").click(function(e){
		quickaddwarem();
		e.isImmediatePropagationStopped();
	});
}
function changealldisc(value, line) {
	for (var i = 1; i <= line; i++) {
		var price;
		var price0 = Number($('#uprice0' + i).html()).toFixed(2);
		price = getbypoints(value * price0);
		$('#udisc' + i).val(value);
		$('#upri' + i).val(price.toFixed(2))
	}
	quickupdatesum()
}
function changeallpri(value, line) {
	for (var i = 1; i <= line; i++) {
		var disc;
		var price0 = Number($('#uprice0' + i).html()).toFixed(2);
		if (Number(price0) == 0) {
			disc = 1
		} else {
			disc = (Number(value) / Number(price0)).toFixed(2)
		}
		$('#udisc' + i).val(disc);
		$('#upri' + i).val(getbypoints(value).toFixed(2))
	}
	quickupdatesum()
}
var qickey = function(e, keyjson) {
	var k = e.which;
	switch (k) {
	case 112:
		if (keyjson['F1'] != undefined) {
			eval(keyjson['F1']);
			e.preventDefault()
		}
		break;
	case 113:
		if (keyjson['F2'] != undefined) {
			eval(keyjson['F2']);
			e.preventDefault()
		}
		break;
	case 114:
		if (keyjson['F3'] != undefined) {
			eval(keyjson['F3']);
			e.preventDefault()
		}
		break;
	case 115:
		if (keyjson['F4'] != undefined) {
			eval(keyjson['F4']);
			e.preventDefault()
		}
		break;
	case 116:
		if (keyjson['F5'] != undefined) {
			eval(keyjson['F5'])
		}
		break;
	case 117:
		if (keyjson['F6'] != undefined) {
			eval(keyjson['F6']);
			e.preventDefault()
		}
		break;
	case 118:
		if (keyjson['F7'] != undefined) {
			eval(keyjson['F7']);
			e.preventDefault()
		}
		break;
	case 119:
		if (keyjson['F8'] != undefined) {
			eval(keyjson['F8']);
			e.preventDefault()
		}
		break;
	case 120:
		if (keyjson['F9'] != undefined) {
			eval(keyjson['F9']);
			e.preventDefault()
		}
		break;
	case 121:
		if (keyjson['F10'] != undefined) {
			eval(keyjson['F10']);
			e.preventDefault()
		}
		break;
	case 122:
		if (keyjson['F11'] != undefined) {
			eval(keyjson['F11']);
			e.preventDefault()
		}
		break;
	case 123:
		if (keyjson['F12'] != undefined) {
			eval(keyjson['F12']);
			e.preventDefault()
		}
		break;
	case 46:
		if (keyjson['Del'] != undefined) {
			eval(keyjson['Del']);
			e.preventDefault()
		}
		break;
	case 90:
		if (keyjson['Z'] != undefined) {
			eval(keyjson['Z']);
			e.preventDefault()
		}
		break;
	case 87:
		if (keyjson['W'] != undefined) {
			eval(keyjson['W']);
			e.preventDefault()
		}
		break;
	default:
		break
	}
}
$.fn.helpinput = function() {
	var div = '<div class="help-divipt"></div>';
	$(this).wrap(div);
	$(this).addClass('help-input');
	$(this).parent().append('<span class="help-s-btn" onclick="$(this).parent().next().click()">▼</span>');
	$(this).removeClass('helpinput')
}
$.fn.edithelpinput = function() {
	var div = '<div class="help-divipt"></div>';
	$(this).wrap(div);
	$(this).addClass('help-input');
	$(this).parent().append('<span class="help-s-btn" onclick="$(this).parent().next().click()">▼</span>');
	$(this).removeClass('edithelpinput')
}
function iskey(key, evt) {
	var isie = (document.all) ? true: false;
	var keyCode;
	var srcobj;
	if (isie) {
		keyCode = event.keyCode;
		srcobj = event.srcElement
	} else {
		keyCode = evt.which;
		srcobj = evt.target
	}
	if (keyCode == key) {
		return true
	} else {
		return false
	}
}
function getJsonLength(jsonData) {
	var jsonLength = 0;
	for (var item in jsonData) {
		jsonLength++
	}
	return jsonLength
}
function setnoright() {
	with(document.body) {}
}
function addbackprint(progid, houseid, noteid, noteno) {
	//PRTSERIP
	alertmsg(2);
	if (houseid == "0") {
		alerttext("你此账户未授权店铺，请到职员档案授权店铺！")
	} else {
		var prtserip = getValuebyName("PRTSERIP");
		$.ajax({
			type: "POST",
			url: "/skydesk/fyjerp",
			data: {
				erpser: "getprintcs1",
				progid: progid
			},
			dataType: 'json',
			success: function(data) {
				try {
					if (valideData(data)) {
						var prtid = Number(data.PRTID);
						if(prtid==0||isNaN(prtid)){
							alerttext("请先选择打印端口！");
							return;
						}else{
							if(prtserip.length==0||data.IPSTR.length==0){
								$.ajax({
									type: "POST",
									url: "/skydesk/fyjerp",
									data: {
										erpser: "addbackprint",
										noteno: noteno,
										noteid: Number(noteid),
										houseid: Number(houseid),
										epid: top.skyepid,
										progid: progid
									},
									dataType: 'json',
									success: function(data) {
										if (valideData(data)) {
											$.messager.show({
												msg: data.msg,
												timeout: 1500,
												showType: 'show',
												height: 60
											});
											if (xstype == undefined) return;
											if (xstype != undefined) {
												if (xstype == 1) {
													$('#baradd').focus()
												} else {
													$('#epid,#saleman').val('');
													$('#saleman').focus()
												}
											}
										}
									}
								});
							}else{
								progid = Number(progid);
								switch (progid) {
								case 1015:
									noteno = top.skyepid;
									break;
								case 1208:
								case 9001:
									noteno = noteid;
									break;
								default:
									break;
								}
								var fyprt={
										prtid: prtid,
										accid: getValuebyName("ACCID"),
										houseid: houseid,
										epid: top.skyepid,
										progid: progid,
										noteid: noteno,
										author: "skydesk_wmg"
								};
								var fyprtstr = "*fyprt="+JSON.stringify(fyprt);
								$.ajax({
									type: "POST",
									url: "/skydesk/sendudp",
									data: {
										prtserip: prtserip,
										fyprtstr: fyprtstr
									},
									dataType: 'json',
									success: function(data) {
										try {
											if(valideData(data)){
												$.messager.show({
													msg: data.msg,
													timeout: 1500,
													showType: 'show',
													height: 60
												});
											}
										} catch (e) {
											// TODO: handle exception
											console.log(e.message);
										}
									}
								});
							}
						}
					}
				}catch (e) {
					// TODO: handle exception
					
				}
			}
		});
	}
}
//后台打印
function setaddbackprint(progid, obj) {
	if (obj == undefined) {
		$('#print,#print1').bind("click",
		function(e) {
			var houseid = $('#uhouseid').val();
			if (houseid == undefined) houseid = getValuebyName("HOUSEID");
			var noteid = $('#uid').val();
			var noteno = $('#unoteno').val();
			if (progid != '' && noteid != '' && houseid != '' && noteno != '') {
				addbackprint(progid, houseid, noteid, noteno)
			} else {
				alerttext("请重新选择需打印单据！", 3000)
			}
		})
	} else {
		$('#print,#print1').bind("click",
		function(e) {
			var houseid = 0;
			if(obj.houseid==undefined){
				houseid = $('#uhouseid').val();
				if (houseid == undefined) 
					houseid = getValuebyName("HOUSEID");
			}else{
				houseid = eval(obj.houseid);
			}
			var noteid = eval(obj.id);
			var noteno = eval(obj.noteno);
			if (progid != '' && noteid != '' && houseid != '' && noteno != '') {
				addbackprint(progid, houseid, noteid, noteno)
			} else {
				alerttext("请重新选择需打印单据！", 3000)
			}
		})
	}
}
//审核对应请求
var jsoncheckser = {
	pg1401: "checkallotorderhbyid"
}
//单据审核
function checknote(index,stg){
	var $dg = $("#gg");
	var rows = $dg.datagrid("getRows");
	var row = rows[index];
	var msg = "您确定审核？";
	if(stg==0)
		msg = "您确定取消审核？";
	$.messager.confirm(dialog_title, msg,
			function(r) {
				if (r) {
					var noteno = row.NOTENO;
					alertmsg(2);
					$.ajax({
						type: "POST",
						//访问WebService使用Post方式请求 
						url: "/skydesk/fyjerp",
						//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
						data: {
							erpser: jsoncheckser[pgid],
							noteno: noteno,
							checkid: stg
						},
						//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
						dataType: 'json',
						success: function(data) { //回调函数，result，返回值 
							try {
								if (valideData(data)) {
									var epname = getValuebyName("EPNAME");
									$dg.datagrid("updateRow",{
										index: index,
										row: {
											CHECKMAN: (stg==0?"":epname)
										}
									});
								}
								$dg.datagrid("refresh");
							} catch(e) {
								// TODO: handle exception
								console.error(e);top.wrtiejsErrorLog(e);
							}
						}
					});
				}
			});
}
function textJson(text) {
	try {
		if (text == 'fwq') {
			alerttext('您的网络不佳，请求超时，请稍后重试！');
			setTimeout(function() {
				return false
			},
			2000)
		} else {
			var data = $.parseJSON(text);
			return true
		}
	} catch(e) {
		alerttext("返回数据异常！");
		setTimeout(function() {
			return false
		},
		2000)
	}
}
/*function valideData(data,showErrMsg) {
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
}*/
function isSucess(text) {
	try {
		if (text == "sys" || text.indexOf('syserror') >= 0) {
			$('.tcdPageCode').each(function() {
				var $this = $(this);
				if ($this.data('pageArgs')) {
					var isauto = $this.data('pageArgs').autorefresh;
					if (isauto) {
						var timer = $this.data('pageArgs').timer;
						clearInterval(timer)
					}
				}
			});
			var date = new Date();
			var hours = date.getHours();
			var m = date.getMinutes();
			if (hours == 0 && (m >= 0 && m <= 30)) alert('00:00-00:30为系统维护时间，请稍后登录重试！');
			else if (text.indexOf('syserror') >= 0) {
				var data = $.parseJSON(text);
				if (data.syserror) alert(data.syserror);
				else alert('签名异常，请重新登录1！');
			} else alert('签名异常，请重新登录2！');
			top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
			return false
		} else if (text == 'fwq') {
			alerttext('您的网络不佳，请求超时，请稍后重试！');
			return false
		} else if (text.indexOf('未设置批发默认客户') >= 0) {
			return false
		} else if (text.indexOf('当前店铺无开班记录') >= 0 || text == "t" || text.indexOf('<option') >= 0) {
			return true
		} else if (text.indexOf('{') == -1 && text != 't' && text.indexOf('<option') == -1) {
			alerttext(text);
			return false
		} else if (textJson(text)) {
			var data = $.parseJSON(text);
			if (Number(data.result) == 0) {
				alerttext(data.msg);
				return false
			} else return true;
		} else {
			return false
		}
	} catch(e) {
		// TODO: handle exception
		console.error(e);top.wrtiejsErrorLog(e);
		return false;
	}

}
function isGetData(text) {
	try {
		if (text == "sys" || text.indexOf('syserror') >= 0) {
			$('.tcdPageCode').each(function() {
				var $this = $(this);
				var isauto = $this.data('pageArgs').autorefresh;
				if (isauto) {
					var timer = $this.data('pageArgs').timer;
					clearInterval(timer)
				}
			});
			var date = new Date();
			var hours = date.getHours();
			var m = date.getMinutes();
			if (hours == 0 && (m >= 0 && m <= 30)) alert('00:00-00:30为系统维护时间，请稍后登录重试！');
			else if (text.indexOf('syserror') >= 0) {
				var data = $.parseJSON(text);
				if (data.syserror) alert(data.syserror);
				else alert('签名异常，请重新登录1！');
			} else alert('签名异常，请重新登录2！');
			top.window.location.href = "/skydesk/loginservlet?doservlet=logoff";
			return false
		} else if (text == 'fwq') {
			alerttext('您的网络不佳，请求超时，请稍后重试！');
			return false
		} else if (text.indexOf('未设置批发默认客户') >= 0) {
			return false
		} else if (text.indexOf('当前店铺无开班记录') >= 0 || text == "t" || text.indexOf('<option') >= 0) {
			return true
		} else if (text.indexOf('{') == -1 && text != 't' && text.indexOf('<option') == -1) {
			alerttext(text);
			return false
		} else if (textJson(text)) {
			var data = $.parseJSON(text);
			if (Number(data.result) == 0) {
				alerttext(data.msg);
				return false
			} else return true;
		} else {
			return false
		}
	} catch(e) {
		// TODO: handle exception
		console.error(e);top.wrtiejsErrorLog(e);
		return false;
	}

}
function downloadmoban(moban) {
	top.window.location.href = "/skydesk/MainServlet?mainser=getmoban&moban=" + moban
}
function autogetno(text) {
	if (text == "") {
		return ""
	}
	var start = new RegExp(/^\d+/);
	var end = new RegExp(/\d+$/);
	if (end.exec(text) != null) {
		var numstr = (end.exec(text))[0];
		var number = Number(numstr);
		var news = (number + 1).toString();
		var l1 = numstr.length;
		var l2 = news.length;
		if (l1 <= l2) return newtext = text.replace(numstr, news);
		else if (l1 > l2) {
			for (var i = 1; i <= l1 - l2; i++) news = '0' + news;
			return newtext = text.replace(numstr, news)
		}
	} else {
		return text + '0'
	}
}
function initSocket(relationid, usercode, msgul) {
	var path = top.window.document.location.host + top.window.document.location.pathname;
	if (path.indexOf('\/') > 0) path = path.substring(0, path.lastIndexOf('\/'));
//	var path = "skydesk";//项目名称 importhelp.jsp也要改
	var url = "ws://" + path + "/websocket/" + relationid + "/" + usercode;
	try {
		var i = 0;
		if ('WebSocket' in window) {
			webSocket = new WebSocket(url);
			i = 1;
		} else if ('MozWebSocket' in window) {
			webSocket = new MozWebSocket(url);
			i = 2;
		} else {
			alert('您的系统版本不支持查看上传进度！');
			return
		}
	} catch(exp) {
		alert(exp.message + i + url);
	}
	webSocket.onmessage = function(msg) {
		try {
			var data = $.parseJSON(msg.data);
			var html = "";
			var rescl = "";
			if (msg.data.indexOf("成功") > -1) rescl = "okli";
			else if (msg.data.indexOf("失败") > -1) rescl = "failedli";
			if (typeof(data) == "object" && data.constructor == Array) {
				for (var i in data) {
					html += '<li class="' + rescl + '">';
					for (var j in data[i]) html += data[i][j] + '，';
					html += '</li>';
				}
			} else {
				html += '<li class="' + rescl + '">';
				for (var i in data) html += data[i] + '，';
				html += '</li>';
			}
			$(msgul).prepend(html);
		} catch(exp) {
			$(msgul).prepend("<li>该条数据字符异常，无法显示！</li>");
		}
	};
	webSocket.onerror = function(event) {
		console.error(event)
	};
	webSocket.onopen = function(event) {
		console.error(event)
	};
	webSocket.onclose = function() {}
}
//只显示导入的结果 成功或者失败
function changeulclass(tp) {
	if (tp == 0) {
		if ($('.liststyle1').hasClass('showfailedli')) {
			$('.liststyle1').removeClass('showfailedli');
		} else {
			$('.liststyle1').addClass('showfailedli');
		}
	} else if (tp == 1) {
		if ($('.liststyle1').hasClass('showokli')) {
			$('.liststyle1').removeClass('showokli');
		} else {
			$('.liststyle1').addClass('showokli');
		}
	}
}

function setfromnoteno(noteno) {
	if (noteno == undefined || noteno == "") alerttext("单据号不能为空！");
	else if ($('#waret').data("datagrid")) {
		if ($('#waret').datagrid('getRows').length == 0) alerttext("单据明细为空！");
		else {
			top.fromnoteno = noteno;
			
			if(pgid=="pg1102") top.fromnoteid=0;
			else if(pgid=="pg1103") top.fromnoteid=1;
			else if(pgid=="pg1201") top.fromnoteid=2;
			else if(pgid=="pg1302") top.fromnoteid=3;
			else if(pgid=="pg1303") top.fromnoteid=4;
			else if(pgid=="pg1210") top.fromnoteid=5;
			else if(pgid=="pg1402") top.fromnoteid=6;
			else if(pgid=="pg1403") top.fromnoteid=7;
			alerttext("单据号已复制！")
		}
	}
}
function copynote(noteid, tonoteno) {
	var stg = Number($('#ustatetag').val());
	var fromnoteno = top.fromnoteno;
	var fromnoteid = top.fromnoteid;
	if (stg > 0 || isNaN(stg)) alerttext("该单据已提交，不能修改！");
	else if (isAddWarem()) {
		if (fromnoteno == undefined || fromnoteno == "") alerttext("复制单据号不能为空！");
		else if (tonoteno == fromnoteno) alerttext("复制单据号与被复制单据号不能相等！");
		else {
			$.messager.confirm(dialog_title, '确认将' + fromnoteno + "单据信息复制到" + tonoteno + "?",
			function(r) {
				if (r) {
					alertmsg(2);
					$.ajax({
						type: "POST",
						url: "/skydesk/fyjerp",
						data: {
							erpser: "copynote",
							noteid: noteid,
							fromnoteid: fromnoteid,
							fromnoteno: fromnoteno,
							tonoteno: tonoteno
						},
						dataType: 'json',
						success: function(data) {
							if (valideData(data)) {
								getnotewarem(tonoteno, "1");
							}
						}
					})
				}
			})
		}
	}
}
function clearnote(noteid, noteno) {
	var stg = Number($('#ustatetag').val());
	if (stg == 0 || isNaN(stg)) alerttext("该单据未提交，不能冲单！");
	else {
		if (noteno == "") alerttext("单据号不能为空！");
		else {
			$.messager.confirm(dialog_title, '确认将' + noteno + "单据冲单吗?",
			function(r) {
				if (r) {
					alertmsg(2);
					$.ajax({
						type: "POST",
						url: "/skydesk/fyjerp",
						data: {
							erpser: "clearnote",
							noteid: noteid,
							noteno: noteno
						},
						dataType: 'json',
						success: function(data) {
							if (valideData(data)) {
								$('#ud').dialog('close');
								$("#pp").refreshPage();
//								if (typeof(getdata) != "undefined") getdata(1);
//								else getnotedata();
							}
						}
					})
				}
			})
		}
	}
}
function copyobj(obj) {
	var obj2 = new Object();
	for (var p in obj) {
		var name = p;
		var value = obj[p];
		obj2[name] = obj[p]
	}
	return obj2
}
function copyobjarry(objarry) {
	var objarry2 = new Array();
	for (var i in objarry) {
		if (typeof(objarry[i]) == "object" && objarry[i].constructor == Object) objarry2[i] = copyobj(objarry[i]);
		else if (typeof(objarry[i]) == "object" && objarry[i].constructor == Array) {
			objarry2[i] = copyobjarry(objarry[i])
		}
	}
	return objarry2
}
var ctrlKeyDown = false;
var altKeyDown = false;
function banBackSpace(e) {
	var ev = e || window.event;
	var obj = ev.target || ev.srcElement;
	var t = obj.type || obj.getAttribute('type');
	var vReadOnly = obj.getAttribute('readonly');
	var vEnabled = obj.getAttribute('enabled');
	vReadOnly = (vReadOnly == null) ? false: true;
	vEnabled = (vEnabled == null) ? true: false;
	var flag1 = (ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vEnabled != true)) ? true: false;
	var flag2 = (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") ? true: false;
	if (flag2) {
		return false
	}
	if (flag1) {
		return false
	}
}
document.onkeydown = banBackSpace;
$(document).bind("keydown",function(e){
	ctrlKeyDown = e.ctrlKey;
	altKeyDown = e.altKey;
});
//$(document).bind("keyup",function(e){
//	ctrlKeyDown = e.ctrlKey;
//	altKeyDown = e.altKey;
//});

var ma = [['1', '3', '5', '7', '8', '10'], ['4', '6', '9', '11']];
function pasDate(da) {
	var yp = da.indexOf('年'),
	mp = da.indexOf('月'),
	dp = da.indexOf('日');
	var y = Number(da.substr(0, yp)),
	m = Number(da.substr(yp + 1, mp - yp - 1)),
	d = Number(da.substr(mp + 1, dp - mp - 1));
	return [y, m, d]
}
function gettodaydate() {
	var dd = new Date();
	var x = dd.Format('yyyy-MM-dd');
	if (x.indexOf("-") == -1) {
		var o = pasDate(x);
		return o.join('-')
	} else return x
}
function getdatestr(d, showSeconds) {
	showSeconds = showSeconds == undefined ? true: showSeconds;
	if (showSeconds) return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
	else return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
}
function datetrans(date, n, f) {
	var d = new Date(date);
	if (f == 0) d.setDate(d.getDate() - n);
	else if (f == 1) d.setDate(d.getDate() + n);
	return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
}
var setTodayDate = function(){
	var nowDate = new Date(top.getservertime());
	var c = $(notedateobj.notedate).datetimebox('calendar');
	var sp = $(notedateobj.notedate).datetimebox('spinner');
	c.calendar("moveTo",nowDate);
	sp.timespinner("setValue",nowDate.Format("hh:mm:ss"));
}
var changedatebj = false;
var changedate = function() {
	var c = $(notedateobj.notedate).datetimebox('calendar');
	var sp = $(notedateobj.notedate).datetimebox('spinner');
	var date = c.calendar("options").current;
	notedate = date.Format("yyyy-MM-dd") + " " + sp.timespinner("getValue");
	$(notedateobj.notedate).datetimebox('setValue', notedate);
	if (changedatebj && (notedate.length == 19)) {
		var id = $(notedateobj.id).val();
		var noteno = $(notedateobj.noteno).val();
		changenotedate(id, noteno, notedate, notedateobj.action,notedateobj)
	}
}
function changenotedate(id, noteno, notedate, action,notedateobj) {
	alertmsg(2);
	var ntid = pgid=="pg1102"||pgid=="pg1201"||pgid=="pg1501"?0:pgid=="pg1103"||pgid=="pg1302"||pgid=="pg1316"?1:2;
	$.ajax({
		type: "POST",
		url: "/skydesk/fyjerp",
		data: {
			erpser: action,
			ntid: ntid,
			noteno: noteno,
			notedatestr: notedate
		},
		dataType: 'json',
		success: function(data) {
			var $dg = $("#gg");
			var index = -1;
			var row = {};
			if ($dg.length > 0) {
				index = $dg.datagrid("getRowIndex", noteno);
				if (index == -1) index = dqindex;
				row = $dg.datagrid("getRows")[index];
			}else return;
			if (valideData(data)) {
				$dg.datagrid('updateRow', {
					index: index,
					row: {
						NOTEDATE: notedate
					}
				});
				$dg.datagrid('refresh')
			}else{
				$(notedateobj.notedate).datetimebox('setValue',row.NOTEDATE);
			}
		}
	})
}

//导出
var fyexportxls = function(totalstr, dgstr, currentdata, urlid, showhxcmid, filename) {
	var total = Number(totalstr);
	if(isNaN(total))
		total = Number($(totalstr).html().replace(/[^\d.]/g, ""));
	if (total > 0) {
		var msg = "是否确认导出！";
		$.messager.confirm(dialog_title, msg,
		function(r) {
			if (r) {
				var $dg = $(dgstr);
				var dgfrcolumns = $dg.datagrid('options').frozenColumns;
				var dgcolumns = $dg.datagrid('options').columns;
				var rows = $dg.datagrid("getRows");
				var footerrows = $dg.datagrid("getFooterRows");
				var footerrow = {};
				if (footerrows != undefined && footerrows.length > 0) footerrow = footerrows[0];
				var row = rows[0];
				if (dgcolumns.length > 0) {
					var columns = [];
					var showhxcm = false;
					if (showhxcmid == 1) showhxcm = true;
					var dgfclen = dgfrcolumns.length;
					var dgclen = dgcolumns.length;
					var dgi = Math.max(dgfclen, dgclen);
					var frlnum = 0;
					var lnum = 0;
					var dgfrcolumn = dgfrcolumns[0];
					var dgcolumn = dgcolumns[0];
					if (dgfrcolumn) {
						for (var j = 0; j < dgfrcolumn.length; j++) {
							var col = dgfrcolumn[j];
							var fieldno = col.field;
							var fieldname = col.title;
							var fieldtype = col.fieldtype;
							var checkbox = col.checkbox;
							var hidden = col.hidden;
							var expable = col.expable;
							var rspan = Number(col.rowspan);
							var cspan = Number(col.colspan);
							rspan = isNaN(rspan) ? 1 : rspan;
							if (rspan == 1 && dgfclen > 1 && dgclen == 1) rspan = dgfclen;
							cspan = isNaN(cspan) ? 1 : cspan;
							fieldtype = fieldtype == undefined ? "C": fieldtype;
							if (((hidden!=true&&expable==false)||hidden == true || fieldno == "ROWNUMBER" || fieldno == "IMG" || fieldno == "IMAGENAME0" || fieldno == "ACTION" || checkbox == true) && expable != true) continue;
							var colobj = {};
							for (var pi = 0; pi < cspan; pi++) {
								if (cspan > 1) {
									var pj = frlnum++;
									fieldno = dgfrcolumns[1][pj].field;
									fieldtype = dgfrcolumns[1][pj].fieldtype;
									colobj = {
										fieldno: fieldno,
										fieldname: dgfrcolumns[1][pj].title,
										fieldtype: fieldtype,
										level: 1
									}
									if (pi == 0 && dgi > 1) {
										colobj["m_title0"] = fieldname;
										colobj["m_col0"] = cspan - 1;
										colobj["m_row0"] = rspan - 1;
									}
								} else {
									colobj = {
										fieldno: fieldno,
										fieldname: fieldname,
										fieldtype: fieldtype
									}
									if (dgi > 1) {
										colobj["m_title0"] = fieldname;
										colobj["m_col0"] = cspan - 1;
										colobj["m_row0"] = dgi - 1;
									}
								}
								var fieldfooter = footerrow[fieldno];
								if (fieldtype == "N" || fieldtype == "G") {
									if (isNaN(Number(fieldfooter))) fieldfooter = "";
								} else if (fieldfooter == undefined) fieldfooter = "";
								if (fieldfooter != "") colobj["fieldfooter"] = fieldfooter;
								columns.push(colobj);
							}
						}
					}
					for (var j = 0; j < dgcolumn.length; j++) {
						var col = dgcolumn[j];
						var fieldno = col.field;
						var fieldname = col.title;
						var fieldtype = col.fieldtype;
						var checkbox = col.checkbox;
						var hidden = col.hidden;
						var expable = col.expable;
						var rspan = Number(col.rowspan);
						var cspan = Number(col.colspan);
						rspan = isNaN(rspan) ? 1 : rspan;
						if (rspan == 1 && dgfclen > 1 && dgclen == 1) rspan = dgfclen;
						cspan = isNaN(cspan) ? 1 : cspan;
						fieldtype = fieldtype == undefined ? "C": fieldtype;
						if (((hidden!=true&&expable==false) || hidden == true || fieldno == "ROWNUMBER" || fieldno == "IMG" || fieldno == "IMAGENAME0" || fieldno == "ACTION" || checkbox == true) && expable != true) continue;
						var colobj = {};
						for (var pi = 0; pi < cspan; pi++) {
							if (cspan > 1) {
								var pj = lnum++;
								fieldno = dgcolumns[1][pj].field;
								fieldtype = dgcolumns[1][pj].fieldtype;
								colobj = {
									fieldno: fieldno,
									fieldname: dgcolumns[1][pj].title,
									fieldtype: fieldtype,
									level: 1
								}
								if (pi == 0 && dgi > 1) {
									colobj["m_title0"] = fieldname;
									colobj["m_col0"] = cspan - 1;
									colobj["m_row0"] = rspan - 1;
								}
							} else {
								colobj = {
									fieldno: fieldno,
									fieldname: fieldname,
									fieldtype: fieldtype,
									level: 0
								}
								if (dgi > 1) {
									colobj["m_title0"] = fieldname;
									colobj["m_col0"] = cspan - 1;
									colobj["m_row0"] = rspan - 1;
								}
							}
							var fieldfooter = footerrow[fieldno];
							if (fieldtype == "N" || fieldtype == "G") {
								if (isNaN(Number(fieldfooter))) fieldfooter = "";
							} else if (fieldfooter == undefined) fieldfooter = "";
							if (fieldfooter != "") colobj["fieldfooter"] = fieldfooter;
							columns.push(colobj);
						}
					}
					var expdata = $.extend({},currentdata);
					expdata["reptitle"] = getjsonfile(pgid, "pgjson.json");
					if (filename) expdata["reptitle"] = filename;
					expdata["headrows"] = dgcolumns.length;
					expdata["columns"] = JSON.stringify(columns);
					if (filename) expdata["filename"] = filename + ".xlsx";
					else expdata["filename"] = getjsonfile(pgid, "pgjson.json") + ".xlsx";
					var url = "/skydesk/fyexprotxls";
					if (urlid == undefined) expdata["serverid"] = "api";
					else expdata["serverid"] = urlid;
					DownLoadFile({
						url: url,
						//请求的url
						method: 'POST',
						data: expdata
					});
				} else alerttext("列不能为空！");
			}
		});
	} else alerttext("当前记录不能为空，请重新查询");
}
var DownLoadFile = function(options) {
	var config = $.extend(true, {
		method: 'post'
	},
	options);
	var $iframe = $('<iframe id="down-file-iframe" style="diplay:none"/>');
	var $form = $('<form target="_top" method="' + config.method + '"/>');
	$form.attr('action', config.url);
	for (var key in config.data) {
		var $inpt = $('<input type="hidden" name="' + key + '"/>');
		$inpt.val(config.data[key]);
		$form.append($inpt)
	}
	var $inptbj = $('<input type="hidden" name="timebj">');
	$inptbj.val((new Date()).getMilliseconds());
	$form.append($inptbj);
	$form.append($inpt);
	$iframe.append($form);
	$(document.body).append($iframe);
	$form[0].submit();
	$iframe.remove()
}

var valideId = function(id) {
	id = Number(id);
	if (isNaN(id) || id == 0) return "-1";
	else if (id > 0) return id;
}
var showimgtimer = null;
var pendingRequests = {}; //请求集合
$(function() {
	$('input[type=text]').attr("autocomplete", "off");
	$('.helpinput').helpinput();
	$('.edithelpinput').selHelpInput();
	$('.edithelpinputs').HelpInput();
	$('.selhelpinput').selHelpInput();
	$('#swareno').swareno();
	$('#wquickuwareno').quickwareno('#wquickuwareno');
	$('#tjquickuwareno').quickwareno('#tjquickuwareno');
	$('.brand_help').gethelp("brand");
	$('.cust_help').gethelp("cust");
	$('.buyer_help').gethelp("buyer");
	$('.provd_help').gethelp("provd");
	$('.house_help').gethelp("house");
	$('.role_help').gethelp("role");
	$('.sale_help').gethelp("salename");
	$('.dpt_help').gethelp("dpt");
	$('.saleman_help,.emp_help').gethelp("saleman");
	$('.wareno_help').gethelp("wareno");
	$('.color_help').gethelp("color");
	$('.size_help').gethelp("size");
	$('.vip_help,.guest_help').gethelp("vip");
	$('.viptp_help,vt_help').gethelp("viptp");
	$('.pw_help').gethelp("pw");
	$('.cg_help').gethelp("cg");
	$('.wave_help').gethelp('wave');
	$('.units_help').gethelp('units');
	$('.season_help').gethelp('season');
	$('.area_help').gethelp('area');
	$('.carry_help').gethelp('carry');
	autofn();
	autokey();
	addAutokey();
	setnoright();
	$(".onlyNum").onlyNum();
	$(".onlyAlpha").onlyAlpha();
	$(".onlyNumAlpha").onlyNumAlpha();
	$(".onlyMoney").onlyMoney();
	$(".onlyDisc").onlyDisc();
	$(".onlyWareno").onlyWareno();
	$("body").delegate('[data-qickey]:parent', 'keydown',
	function(e) {
		eval('var keyjson = {' + $(this).data('qickey') + '}');
		qickey(e, keyjson)
	});
	$("body").delegate('[data-enter]', 'keyup',
	function(e) {
		var k = e.which;
		var s = this;
		if ($(s).attr("type") == "text") $(e.target).focus();
		if (k >= 37 && k <= 40) {
			e.preventDefault();
			return false;
		} else if (k == 13) {
			$(this).blur();
			setTimeout(function() {
				var relvalue = $($(e.target).data("value")).val();
				if ((relvalue == "" || relvalue == "0") && $(e.target).val()) return;
				eval($(s).data('enter'));
				$(e.target).focus();
			},
			200);
			e.preventDefault();
		} else if ($(s).attr("type") == "text") {
			if (k == 8 || k == 46 || k == 17 || k == 18) {
				if (s.value == "") {
					eval($(s).data('enter'));
					$(s).click();
					$(e.target).focus();
				}
			}
		}
	});
	$("body").delegate('input[maxlength]', 'keyup paste',
	function(e) {
		var k = e.which;
		var ml = $(this).attr('maxlength');
		if (ml !== undefined && k != 13) {
			var str = this.value;
			var inp = this;
			if (strlen(str) > ml) {
				inp.value = str.substring(0, Math.ceil(ml / 2))
			} else {
				return true
			}
		}
	}).keydown(function(e){
		var k = e.which;
		if(k==122){
			e.preventDefault();
			if(typeof(top.setfullscreen)=="function")
				top.setfullscreen();
		}
	});
	$("input.uppercase").keyup(function(e){
		var k = e.which;
		var $this = $(this);
		var value = $this.val();
		if (k < 37 || k > 40){
			value = value.replace(/ /g,"").toUpperCase();
			this.value = value;
		}
	});
	$('.window').delegate('.easyui-dialog[closed=true]:parent', 'keyup',
	function(e) {
		var k = e.which;
		if (k == 27) {
			e.preventDefault();
			$(this).dialog('close');
		}
	});
	$(document).ajaxStart(function() {
		var LoginEpid = Number(getValuebyName('EPID'));
		if (top.skyepid == undefined) {
			alert('版本发生更新，请重新登录')
		} else if (top.skyepid != LoginEpid) {
			alert("账户数据已过期（一台电脑只能同时登录一个账户），请重新登录！");
			top.window.location.href = "/skydesk/loginservlet?doservlet=logoff"
		}
	}).ajaxError(function(event,request,settings) {
		try {
			top.wrtiejsErrorLog("请求页面"+event.URL+"-->"+request.status+"-->"+settings.data);
			alert("请求页面超时");
			alerthide();
		} catch (e) {
			// TODO: handle exception
			top.wrtiejsErrorLog(e.message);
		}
	}).ajaxStop(function() {
		if (!hasmsg) {
			alerthide();
		}
	});
	//防止重复提交；
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		var ReqData = {};
		var reg = new RegExp("(^|&)erpser=([^&]*)(&|$)");
		var r = options.data.match(reg);
		var key = options.url + "_" + (r!=null?unescape(r[2]):options.data.split("&")[0]);
		if (!pendingRequests[key] && key.indexOf('.html') == -1) {
			pendingRequests[key] = jqXHR;
		} else if (key.indexOf('.html') == -1) {
			jqXHR.abort(); // 放弃后触发的重复提交
			top.wrtiejsErrorLog(key+"触发重复");
			console.log(key+"触发重复");
			//pendingRequests[key].abort();	// 放弃先触发的提交
		}
		var complete = options.complete;
		options.complete = function(jqXHR, textStatus) {
			delete pendingRequests[key];
			if ($.isFunction(complete)) {
				complete.apply(this, arguments);
			}
		};
	});
	$('body').delegate("img[bigimg=true]", "mouseenter",
	function(e) {
		clearTimeout(showimgtimer);
		var obj = this;
		$('.big_img_div').remove();
		showimgtimer = setTimeout(function() {
			var width = $(obj).width();
			var height = $(obj).height();
			var bwidth = $('body').width();
			var bheight = $('body').height();
			var left = $(obj).offset().left;
			var top = $(obj).offset().top;
			var _left = e.pageX - left;
			var _top = e.pageY - top;
			var imgdiv = $('.big_img_div');
			var imgsrc = $(obj).attr('src').replace('_60x60-m3.png', "");
			if (imgdiv.length == 0) {
				imgdiv = $('<div class="big_img_div"><img width="100%" height="100%" alt="加载中……"></img></div>');
				$("body").append(imgdiv)
			}
			x = ($('body').width() - Number(e.pageX)) < 300 ? (e.pageX - 300) : e.pageX;
			y = ($('body').height() - Number(e.pageY)) < 300 ? (e.pageY - 300) : e.pageY;
			imgdiv.css({
				'top': y,
				'left': x,
				'zoom': 1,
				'overflow': 'hidden'
			});
			var img = imgdiv.find('img');
			img.attr('src', imgsrc);
			imgdiv.show()
		},
		500)
	}).delegate("img[bigimg=true]", "mouseout",
	function(e) {
		clearTimeout(showimgtimer);
		$('.big_img_div').remove()
	});
	$('div.toolbar').resize(function() {
		if($(this).hasClass("datagrid-toolbar")){
			var dg = $(this).siblings("div.datagrid-view").find("div.datagrid-f");
			if($(dg).data()&&$(dg).data().datagrid!=undefined) $(dg).datagrid("resize");
		}
	});
	if(xmtype==3){
		$(".easyno").hide();
	}
	top.$("div.loading-modal").hide();
});