<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>库存分布状态</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
</head>
<body class="easyui-layout layout panel-noscroll">
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
<input type="button" class="btn2" id="qc" name="qc" value="查询条件▲" onclick="toggle()">
 <span id="serbtn" class="fr">
<input type="button" class="btn2" type="button" value="搜索" id="odser" onclick="searchdata();toggle();">
<input type="button" id="resetser" class="btn2" style="width: 100px;" value="清空条件" onclick="resetsearch()">
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',defaulthzfs.showhxcm);">
</div>
</div>
<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">查询方式</div>
<div class="text" onclick="selectzhfs()" style="cursor: pointer;">
<input type="hidden" id="shzlist" value="">
<input  class="hig25 wid160" type="text" id="span_hzlist" value="点击选择" style="cursor: pointer;color:#ff7900" readonly>
</div>
</div>
<div class="s-box"><div class="title">查询日期</div>
<div class="text"><input id="snowdate" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:162px;height:25px"></input>
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input type="text"
					class="edithelpinput wareno_help" data-value="#swareid"
					name="swareno" id="swareno"
					data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('search')" ></span>
					<input type="hidden" id="swareid" value="">
</div>
</div>
<div class="s-box"><div class="title">商品名称</div>
<div class="text"><input type="text" name="swarename" id="swarename" maxlength="20" class="wid160 hig25" placeholder="<输入>" >
</div>
</div>	
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span onclick="selectdx_house('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#sbrandid"
				name="sbrandname" id="sbrandname"  data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectdx_brand('search')"></span>
				 <input type="hidden" id="sbrandid" value="">
</div>
</div>

<div class="s-box"><div class="title">类型</div>
<div class="text"><input  type="text"  class="edithelpinput" data-value="#stypeid"
				name="sfullname" id="sfullname"  data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectwaretype('search')"></span>
				 <input type="hidden" id="stypeid" value="">
</div>
</div>			
			
<div class="s-box"><div class="title">季节</div>
<div class="text"><input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
                      <span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select id="sprodyear" class="sele1">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>
<div class="s-box"><div class="title">区位</div>
<div class="text"><input  type="text"  class="edithelpinput area_help" data-value="#sareaid"
				name="sareaname" id="sareaname" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectdx_area('search')"></span>
				 <input type="hidden" id="sareaid" value="">
</div>
</div>
<div class="s-box"><div class="title">货位</div>
<div class="text"><input type="text" name="slocale" id="slocale" maxlength="20" class="wid160 hig25" placeholder="<输入>" >
</div>
</div>
</div>
</div>
</div>

<table id="gg" style="overflow: hidden;"></table>
<div class="page-bar">
<div class="page-total">共有<span id="pp_total">0</span>条记录</div>
<div id="pp" class="tcdPageCode page-code">
</div>
</div>
<script type="text/javascript"> 
var searchb=true;
var pgid = "pg1507";
setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();  
}  
function changeDivHeight(){               
  var height = document.documentElement.clientHeight;//获取页面可见高度 
	$('#gg').datagrid('resize',{
  	height:height - 50
  });
}
	//设置弹出框的属性
	$(function(){
		//设置日期框默认值
		var myDate = new Date(top.getservertime());
		$('#nowdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		//生成年份 
		var year=myDate.getFullYear();
		var on=year-5;
		var end=year+1;
		for(var i = on;i <= end ; i++)
		{
		    $("#sprodyear").append("<option value="+i+">"+i+"</option'>");
		}
		$("#pp").createPage({
			pageCount:1,//总页码
			current:1,//当前页 
	        backFn:function(p){
	        	searchpage(p);
	        }
		 });
		$('#gg').datagrid({
			idField: 'KEYID',
			width: '100%',
			height: $("body").height() - 50,
			toolbar: "#toolbars",
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			showFooter: true,
			sortName: "WARENO",
			sortOrder: "asc",
			columns: [],
			pageSize: 20,
			multiSort: true,//允许多列排序
			onSortColumn: function(sort,order){
				sortarr = sort.split(",");
				orderarr = order.split(",");
				sortarrs = [];
				for(var i in sortarr){
					var sort = sortarr[i];
					var order = orderarr[i];
					if(sort == "SIZENAME") sort = "SIZENO"; 
					sortarrs.push(sort+" "+order);
				}
				sortlist = sortarrs.join(",");
				if(sortlist==" ") sortlist = ""; 
				$('#pp').refreshPage();
			}
			
		});
		alerthide();
	});
	function toggle() {
		if (searchb) {
			$('#highsearch').val('高级搜索▼');
			$('#serbtn').hide();
			searchb = false;
		} else {
			$('#highsearch').val('高级搜索▲');
			$('#serbtn').show();
			searchb = true;
		}
		$('.searchbar').slideToggle('fast');
	}
	//清空表单搜索条件 
	function resetsearch(){
		var myDate = new Date(top.getservertime());
		$('#nowdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
		$('#swareno,#swareid,#swarename,#sbrandname,#sbrandid,#sfullname,#stypeid,#sseasonname,#slocale,#sprodyear,#shouseid,#shousename,#sareaid,#sareaname,#slocale').val('');
	}
	var validehzsj = function(ishz,hzname,hzsjname){
		if(ishz&&hzname.indexOf("商品")==-1&&(hzname.indexOf("颜色")>-1||hzname.indexOf("尺码")>-1)){
			alerttext("汇总项：商品必选");
			$('#span_hzlist').val("点击选择");
			return false;
		}
		return true;
	}
	function setdatagrid($dg,sortName,columns){
		sortlist = sortName +" asc";
		columns.push({
			field: "KCHJ",
			title: "小计",
			width: 60,
			fixed: true,
			fieldtype: "G",
			formatter: amtfm2,
			styler: amtstyle,
			halign: "center",
			align: "center"
		});
		$dg.datagrid({
			sortName: sortName,
			sortOrder: "asc",
			frozenColumns: [columns]
		}).datagrid('resize');
	}
	var footer0 = {};
	function searchdata() {
		var nowdate = $("#snowdate").datebox('getValue'); //结束日期 
		var houseidlist = $("#shouseid").val(); //店铺
		var wareno = $("#swareno").val(); //货号no
		var warename = $("#swarename").val(); //商品名称
		var wareid = $("#swareid").val(); //货号id
		var brandidlist = $("#sbrandid").val(); //品牌
		var typeid = $("#stypeid").val(); //类型
		var prodyear = $("#sprodyear").val(); //生产年份
		var seasonname = $("#sseasonname").val(); //季节
		var locale = $("#slocale").val(); //货位
		var areaid = $("#sareaid").val(); //区位
		alertmsg(6);
		footer0 = {};
		var grouplist = "";
		var hzlist = $('#shzlist').data('hzlist');
		if(!hzlist){
			alerttext("请选择汇总方式");
			selectzhfs();
			return;
		}
		if($('#skyhzcheckbj').prop('checked')){
			if(hzlist.grouplist.length==0){
				alerttext("请选择汇总项目");
				return;
			}
		}
		currentdata = {};
		currentdata["erpser"] = "listcxwhere";
		currentdata["grouplist"] = hzlist.grouplist;
		currentdata["rows"] = pagecount;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			dataType: 'json',
			data: {
				erpser: "totalcxwhere",
				nowdate: nowdate,
				houseidlist: houseidlist,
				wareid: wareid,
				wareno: wareno,
				warename: warename,
				brandidlist: brandidlist,
				typeid: typeid,
				prodyear: prodyear,
				locale: locale,
				areaid: areaid,
				seasonname: seasonname
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						sumlist = data.sumlist;
						var hzfs = Number($('#shzfs').val());
						footer0["KCHJ"] = data.totalamt;
						var _columns = data.columns;
						var columns = [];
						for (var i = 0; i < _columns.length; i++) {
							var row = _columns[i];
							var title = row.housename;
							if (title.length > 10) title = title.substring(0, 10) + "<br />" + title.substring(10, title.length);
							columns.push({
								field: "KC" + row.houseid,
								title: title,
								width: 60,
								fixed: true,
								fieldtype: "G",
								formatter: amtfm2,
								styler: amtstyle,
								halign: "center",
								align: "center"
							});
							footer0["KC" + row.houseid] = row.amount;
						}
						$('#gg').datagrid({
							columns: [columns]
						}).datagrid('resize');
						currentdata["sumlist"] = sumlist;
						searchpage(1);
					}
				} catch(err) {
					console.error(err.message);
				}
			}
		});
	}
	var sumlist = "";
	//分页获取
	var currentdata = {};
	function searchpage(page) {
		if (sumlist == "") {
			alerttext("数据为空，请重新搜索！");
			return;
		}
		alertmsg(6);
		//	&hzfs=[汇总方式]&sortid=[排 序方式]
		var options = $('#gg').datagrid('options');
		currentdata["sortlist"] = sortlist;
		currentdata["page"] = page;
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			dataType: 'json',
			data: currentdata,
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			success: function(data) { //回调函数，result，返回值 
				try {
					if (valideData(data)) {
						footer0["ROWNUMBER"] = "合计";
						data.footer = [footer0];
						$('#gg').datagrid('loadData', data);
						$('#gg').datagrid('scrollTo', 0);
						$('#gg').datagrid('resize');
						var totals = data.total;
						$("#pp").setPage({
							pageCount: Math.ceil(Number(totals) / pagecount),
							current: Number(page)
						});
						$('#pp_total').html(data.total);
					}
				} catch(err) {
					console.error(err.message);
				}
			}
		});
	}
</script>
<!-- 店铺帮助列表 -->
<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
<jsp:include page="../HelpList/HzBoxDialog.jsp"></jsp:include>
<jsp:include page="../HelpFiles/AreaHelpList.jsp"></jsp:include>
</body>
</html>