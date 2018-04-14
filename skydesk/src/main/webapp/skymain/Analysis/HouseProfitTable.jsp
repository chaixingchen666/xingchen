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
<title>店铺利润查询</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
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
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata,'api',0);">
</div>
</div>
		<!-- 高级搜索 -->
<div id="hsearch" class="searchbar" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text">
<input class="easyui-datebox" name="smindate" id="smindate" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text">
<input class="easyui-datebox" name="smaxdate" id="smaxdate" style="width: 163px; height: 28px; margin-left: 10px" />
</div>
</div>
<div class="s-box"><div class="title">汇总方式</div>
<div class="text"><select style="width: 163px; height: 28px" id="shzfs"
						name="shzfs">
							<option value="0" selected="selected">店铺</option>
							<option value="1">日期</option>
<!-- 							<option value="2">日期+店铺</option> -->
					</select>
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span onclick="selectdx_house('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
</div>
	</div>
	</div>
	<table id="gg" style="overflow: hidden; height:90000000px;"></table>
	<!-- 分页 -->
	<div
		class="page-bar">
		<div
			class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
	<script type="text/javascript" charset="UTF-8">
//当浏览器窗口大小改变时，设置显示内容的高度  
window.onresize=function(){  
     changeDivHeight();  
}  
function changeDivHeight(){               
  var height = document.documentElement.clientHeight;//获取页面可见高度 
  var width = document.documentElement.clientWidth;//获取页面可见高度 
	$('#gg').datagrid('resize',{
	width:width,
  	height:height-50
  });
}
var ser;
var s=false;
var index=1;
var opser;
var index1=1;
var pgid ="pg1716";
		$(function() {
			var myDate = new Date(top.getservertime());
			$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
			var year = myDate.getFullYear();	
	        var start = year-5;
			var end = year+1;
			for(var i = start;i <= end ; i++)
			{
			    $("#sprodyear").append("<option value="+i+">"+i+"</option'>");
			}
			$('#pp').createPage({
				pageCount : 1,
				current : 1,
				backFn : function(p) {
					searchpage(p.toString());
				}
			});
			$('#gg').datagrid(
					{
						idField : 'ROWNUMBER',
						width : '100%',
						height : $('body').height() - 50,
						fitColumns : true,
						striped : true, //隔行变色/*  */
						singleSelect : true,
						showFooter : true,
						nowrap: false,
						toolbar :"#toolbars",
						sortName: "HOUSENAME",
						sortOrder: "asc",
						frozenColumns : [ [ {
							field : 'ROWNUMBER',
							title : '序号',
							width : 50,
							fixed : true,
							align : 'center',
							halign : 'center',
							formatter : function(value, row, index) {
								var val = Number(value);
								if(value==undefined)
									return "";
								else if(isNaN(val))
									return value;
								return val;
							}
						}] ],
						columns:[[
							{
								field : 'NOTEDATE',
								title : '日期',
								width : 100,
								fixed: true,
								sortable: true,
								hidden: true,
								rowspan: 2,
								align : 'center',
								halign : 'center'
							},{
								field : 'HOUSENAME',
								title : '店铺',
								width : 100,
								fixed: true,
								sortable: true,
								rowspan: 2,
								align : 'left',
								halign : 'center'
							},{
								field : 'XS',
								title : '销售',
								width : 150,
								fixed: true,
								colspan: 2,
								align : 'center',
								halign : 'center'
							},{
								field : 'XT',
								title : '退货',
								width : 150,
								fixed: true,
								colspan: 2,
								align : 'center',
								halign : 'center'
							},{
								field : 'CURRYS',
								title : '应收金额',
								width : 100,
								fieldtype: "N",
								fixed: true,
								sortable: true,
								rowspan: 2,
								formatter: currfm,
								styler: currstyle,
								align : 'right',
								halign : 'center'
							},{
								field : 'CURRSK',
								title : '已收金额',
								width : 100,
								fieldtype: "N",
								fixed: true,
								sortable: true,
								rowspan: 2,
								formatter: currfm,
								styler: currstyle,
								align : 'right',
								halign : 'center'
							},{
								field : 'CURRQK',
								title : '欠账金额',
								width : 100,
								fieldtype: "N",
								fixed: true,
								sortable: true,
								rowspan: 2,
								formatter: currfm,
								styler: currstyle,
								align : 'right',
								halign : 'center'
							},{
								field : 'CURRCB',
								title : '销售成本',
								width : 100,
								fieldtype: "N",
								fixed: true,
								sortable: true,
								rowspan: 2,
								formatter: currfm,
								styler: currstyle,
								align : 'right',
								halign : 'center'
							},{
								field : 'CURRFY',
								title : '店铺费用',
								width : 100,
								fieldtype: "N",
								fixed: true,
								sortable: true,
								rowspan: 2,
								formatter: currfm,
								styler: currstyle,
								align : 'right',
								halign : 'center'
							},{
								field : 'CURRML',
								title : '利润',
								width : 100,
								fieldtype: "N",
								fixed: true,
								sortable: true,
								rowspan: 2,
								formatter: currfm,
								styler: currstyle,
								align : 'right',
								halign : 'center'
							}
						],[{
							field : 'AMOUNTXS',
							title : '数量',
							width : 50,
							fieldtype: "G",
							fixed: true,
							sortable: true,
							formatter: amtfm,
							styler: currstyle,
							align : 'right',
							halign : 'center'
						},{
							field : 'CURRXS',
							title : '金额',
							width : 100,
							fieldtype: "N",
							fixed: true,
							sortable: true,
							formatter: currfm,
							styler: currstyle,
							align : 'right',
							halign : 'center'
						},{
							field : 'AMOUNTXT',
							title : '数量',
							width : 50,
							fieldtype: "G",
							fixed: true,
							sortable: true,
							formatter: amtfm,
							styler: currstyle,
							align : 'right',
							halign : 'center'
						},{
							field : 'CURRXT',
							title : '金额',
							width : 100,
							fieldtype: "N",
							fixed: true,
							sortable: true,
							formatter: currfm,
							styler: currstyle,
							align : 'right',
							halign : 'center'
						}]],
						onSortColumn: function(sort, order){
							$('#gg').datagrid('options').sortName= sort;
							$('#gg').datagrid('options').sortOrder = order;
							$('#pp').refreshPage();
						}
					}).datagrid("keyCtr");
			alerthide();
		});
		function resetsearch(){
			var myDate = new Date(top.getservertime());
			$('#smindate,#smaxdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
			$('#shzfs').val('0');
			$('#shouseid,#shousename').val('');
			$('#swareno').val('');
			$('#swareid').val('');
			$('#swarename').val('');
			$('#sbrandname').val('');
			$('#sbrandid').val('');
			$('#sseasonname').val('');
			$('#sprodyear').val('');
		}
		//高级搜索
		var searchb = true;
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
			setTimeout(function(){
				$('#gg').datagrid('resize',{height:$('body').height()-50});
			}, 200);
		}
		var footer0 = {};
		function searchdata(){
			var mindate = $("#smindate").datebox('getValue');//开始日期 
			var maxdate = $("#smaxdate").datebox('getValue');//结束日期 
			var houseidlist = $("#shouseid").val();//客户
			var wareno= $("#swareno").val();//货号no
			var warename = $("#swarename").val();//商品名称
			var wareid= $("#swareid").val();//货号id
			var brandidlist = $("#sbrandid").val();//品牌
			var typeid = $("#stypeid").val();//类型
			var prodyear = $("#sprodyear").val();//生产年份
			var seasonname = $("#sseasonname").val();//季节
			alertmsg(6);
			footer0 = {};
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				dataType : 'json',
				data: {
					erpser: "totalshopcost",
					mindate: mindate,
					maxdate: maxdate,
					houseidlist : houseidlist
// 					wareid : wareid,
// 					wareno: wareno,
// 					warename: warename,
// 					brandidlist: brandidlist,
// 					typeid : typeid,
// 					prodyear : prodyear,
// 					seasonname : seasonname
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				success : function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							var hzfs = Number($('#shzfs').val());
							var $dg = $('#gg');
							var options = $dg.datagrid('options');
							switch (hzfs) {
							case 0:
								options.sortName = "HOUSENAME";
								$dg.datagrid("showColumn","HOUSENAME");
								$dg.datagrid("hideColumn","NOTEDATE");
								break;
							case 1:
								options.sortName = "NOTEDATE";
								$dg.datagrid("showColumn","NOTEDATE");
								$dg.datagrid("hideColumn","HOUSENAME");
								break;
							default:
								break;
							}
							$('#gg').datagrid('resize');
							searchpage(1);
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
		//分页获取
		var currentdata = {};
		function searchpage(page) {
			alertmsg(6);
			//	&hzfs=[汇总方式]&sortid=[排 序方式]
			var hzfs = $('#shzfs').val();
			var options = $('#gg').datagrid('options');
			var sort = options.sortName;
			var order = options.sortOrder;
			currentdata = {
					erpser : "listshopcost",
					hzfs: hzfs,
					sort: sort,
					order: order,
					rows: pagecount,
					page : page
				};
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				dataType : 'json',
				data : currentdata, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				success : function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							footer0["ROWNUMBER"] = "合计";
							footer0["AMOUNTXS"] = data.amountxs;
							footer0["CURRXS"] = data.currxs;
							footer0["AMOUNTXT"] = data.amountxt;
							footer0["CURRXT"] = data.currxt;
							footer0["CURRCB"] = data.currcb;
							footer0["CURRYS"] = data.currys;
							footer0["CURRQK"] = data.currqk;
							footer0["CURRSK"] = data.currsk;
							footer0["CURRFY"] = data.currfy;
							footer0["CURRML"] = data.currml;
							data.footer = [footer0];
							$('#gg').datagrid('loadData', data);
							$('#gg').datagrid('scrollTo',0);
							$('#gg').datagrid('resize');
							var totals = data.total;
							$("#pp").setPage({
					        	pageCount: Math.ceil(Number(totals)/ pagecount),
					        	current: Number(page)
						 	});
							$('#pp_total').html(data.total);
					}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}	
	</script>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
</body>
</html>