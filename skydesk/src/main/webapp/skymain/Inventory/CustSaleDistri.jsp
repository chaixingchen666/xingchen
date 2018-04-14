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
<title>客户销售分布</title>
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
							<option value="0" selected="selected">商品</option>
							<option value="1">商品，颜色</option>
							<option value="2">商品，颜色，尺码</option>
					</select>
</div>
</div>
<div class="s-box"><div class="title">销售类型</div>
<div class="text"><select style="width: 163px; height: 28px" id="sxsid" name="sxsid">
							<option value="2" selected="selected">所有</option>
							<option value="0">批发</option>
							<option value="1">退货</option>
					</select>
</div>
</div>
<div class="s-box"><div class="title" color="#ff7900">*&nbsp;店铺</div>
<div class="text"><input class="edithelpinput house_help" data-value="#shouseid" maxlength="20" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span onclick="selectdx_house('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">客户</div>
<div class="text"><input class="edithelpinput cust_help wid145 hig25" data-value="#scustid" id="scustname" name="scustname" maxlength="20" placeholder="<选择或输入>"
					type="text" ><span onclick="selectdx_cust('search')"></span>
					<input type="hidden" id="scustid" name="scustid">
</div>
</div>
<div class="s-box"><div class="title">货号</div>
<div class="text"><input type="text" id="swareno" class="edithelpinput wareno_help" data-value="#swareid" maxlength="20" placeholder="<选择或输入>"
						name="swareno"/><span onclick="selectware('search')"></span>
						<input type="hidden" id="swareid" name="swareid">
</div>
</div>	
<div class="s-box"><div class="title">商品名称</div>
<div class="text">
<input type="text" class="hig25 wid160" id="swarename" name="swarename" placeholder="<输入>" />
</div>
</div>
<div class="s-box"><div class="title">品牌</div>
<div class="text"><input  type="text" class="edithelpinput brand_help" name="sbrandname" id="sbrandname" 
                      data-value="#sbrandid" maxlength="20" data-options="required:true" placeholder="<选择或输入>">
                      <span onclick="selectdx_brand('search')"></span>
				       <input type="hidden" name="sbrandid" id="sbrandid" value="">
</div>
</div>
<div class="s-box"><div class="title">季节</div>
<div class="text">
<input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
<span onclick="opencombox(this)"></span>
</div>
</div>
<div class="s-box"><div class="title">生产年份</div>
<div class="text"><select name="sprodyear" id="sprodyear" class="sele1">
  <option value="">---请选择---</option>
  </select>   
</div>
</div>
<div class="s-box">
<label class="fr"><input id="shavesale" type="checkbox" checked>仅显示有销售的商品尺码库存</label>
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
var pgid ="pg1715";
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
						sortName: "WARENO",
						sortOrder: "asc",
						frozenColumns : [ [ {
							field : 'ROWNUMBER',
							title : '序号',
							width : 50,
							fixed : true,
							rowspan: 2,
							align : 'center',
							halign : 'center',
							formatter : function(value, row, index) {
								if (value != null) {
									var val = Math.ceil(Number(value)/ pagecount)-1;
									return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
								} else {
									return value;
								}
							}
						},{
							field : 'WARENO',
							title : '货号',
							width : 100,
							fixed: true,
							rowspan: 2,
							sortable: true,
							align : 'left',
							halign : 'center'
						},{
							field : 'WARENAME',
							title : '商品',
							width : 100,
							fixed: true,
							rowspan: 2,
							sortable: true,
							align : 'left',
							halign : 'center'
						}, {
							field : 'COLORNAME',
							title : '颜色',
							width : 80,
							fixed: true,
							rowspan: 2,
							align : 'center',
							halign : 'center',
							hidden : true
						},{
							field : 'SIZENAME',
							title : '尺码',
							width : 60,
							fixed: true,
							rowspan: 2,
							align : 'center',
							halign : 'center',
							hidden : true
						},{
							field : 'UNITS',
							title : '单位',
							width : 60,
							fixed: true,
							rowspan: 2,
							align : 'center',
							halign : 'center'
						},{
							field : 'AMTKCHJ',
							title : '库存',
							width : 60,
							fixed: true,
							rowspan: 2,
							sortable: true,
							align : 'center',
							halign : 'center',
							fieldtype: "G",
							formatter : amtfm,
							styler: amtstyle
						},{
							field : 'COLHJ',
							title : '合计',
							width : 160,
							fixed: true,
							sortable: true,
							colspan: 2,
							align : 'center',
							halign : 'center'
						}],[{
							field : 'AMTHJ',
							title : '数量',
							width : 60,
							fixed: true,
							sortable: true,
							fieldtype: "G",
							align : 'center',
							halign : 'center',
							formatter : amtfm,
							styler: amtstyle
						},{
							field : 'CURHJ',
							title : '销售额',
							width : 100,
							fixed: true,
							fieldtype: "N",
							sortable: true,
							align : 'right',
							halign : 'center',
							formatter : currfm,
							styler: currstyle
						}] ],
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
			$('#sorder').val('1');
			$('#scustname').val('');
			$('#scustid').val('');
			$('#shousename').val('');
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
			var custidlist = $("#scustid").val();//客户
			var wareno= $("#swareno").val();//货号no
			var warename = $("#swarename").val();//商品名称
			var wareid= $("#swareid").val();//货号id
			var brandidlist = $("#sbrandid").val();//品牌
			var typeid = $("#stypeid").val();//类型
			var prodyear = $("#sprodyear").val();//生产年份
			var seasonname = $("#sseasonname").val();//季节
			var xsid = $('#sxsid').val();
			var havesale = 0;
			if($('#shavesale').prop("checked"))
				havesale = 1;
			alertmsg(6);
			footer0 = {};
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				dataType : 'json',
				data: {
					erpser: "totalsalewhere",
					mindate: mindate,
					maxdate: maxdate,
					houseidlist : houseidlist,
					custidlist : custidlist,
					wareid : wareid,
					wareno: wareno,
					warename: warename,
					brandidlist: brandidlist,
					typeid : typeid,
					prodyear : prodyear,
					xsid: xsid,
					havesale: havesale,
					seasonname : seasonname
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				success : function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							sumsql = data.sumsql;
							var hzfs = Number($('#shzfs').val());
							var $dg = $('#gg');
							switch (hzfs) {
							case 0:
								$dg.datagrid("hideColumn","COLORNAME");
								$dg.datagrid("hideColumn","SIZENAME");
								break;
							case 1:
								$dg.datagrid("showColumn","COLORNAME");
								$dg.datagrid("hideColumn","SIZENAME");
								break;
							case 2:
								$dg.datagrid("showColumn","COLORNAME");
								$dg.datagrid("showColumn","SIZENAME");
								break;
							default:
								break;
							}
							var _columns = data.columns;
							var columns1 = [];
							var columns2 = [];
							for(var i=0;i<_columns.length;i++){
								var row = _columns[i];
								var title = row.custname;
								if(title.length>10)
									title = title.substring(0,10)+"<br />"+title.substring(10,title.length);
								columns1.push({
									field: "CUSTNAME"+row.custid,
									title: title,
									width: 160,
									colspan: 2,
// 									fixed: true,
									halign: "center"
								});
								columns2.push({
									field: "AMT"+row.custid,
									title: "数量",
									width: 60,
									fixed: true,
									fieldtype: "G",
									formatter: amtfm,
									styler: amtstyle,
									halign: "center",
									align: "center"
								},{
									field: "CUR"+row.custid,
									title: "销售额",
									width: 100,
									fixed: true,
									fieldtype: "N",
									formatter: currfm,
									styler: currstyle,
									halign: "center",
									align: "right"
								});
								footer0["AMT"+row.custid] = row.amount;
								footer0["CUR"+row.custid] = row.curr;
							}
							$('#gg').datagrid({
								columns: [columns1,columns2]
							}).datagrid('resize');
							searchpage(1);
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
		var sumsql = "";
		//分页获取
		var currentdata = {};
		function searchpage(page) {
			if(sumsql==""){
				alerttext("数据为空，请重新搜索！");
				return;
			}
			alertmsg(6);
			//	&hzfs=[汇总方式]&sortid=[排 序方式]
			var hzfs = $('#shzfs').val();
			var options = $('#gg').datagrid('options');
			var sort = options.sortName;
			var order = options.sortOrder;
			currentdata = {
					erpser : "listsalewhere",
					sumsql: sumsql,
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
							footer0["WARENO"] = "合计";
							footer0["AMTKCHJ"] = data.totalkcamt;
							footer0["AMTHJ"] = data.totalamt;
							footer0["CURHJ"] = data.totalcurr;
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
	<!-- 客户帮助列表 -->
	<jsp:include page="../HelpFiles/CustHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<jsp:include page="../HelpFiles/HouseHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpFiles/BrandHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
</body>
</html>