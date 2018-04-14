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
<title>销售时点分析</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
#toolbars{height:40px;}
</style>
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript">
var searchb=false;
var dheight=90;
var rownumbers=1;

	var epno = getValuebyName("EPNO");
	setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
	window.onresize=function(){  
	     changeDivHeight();  
	}  
	function changeDivHeight(){               
	  var height = document.documentElement.clientHeight;//获取页面可见高度 
		$('#gg').datagrid('resize',{
	  	height:height-dheight
	  });
	}

	//设置弹出框的属性
	$(function() {
		//设置日期框默认值
		var myDate = new Date(top.getservertime());
		$('#mindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		$('#maxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
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
	        	rownumbers=p;
	        	getsalepointa(p.toString());
	        }
		 });
		$('#gg').datagrid({
			idField : 'WARENAME',
			height : $('body').height() - 90,
			striped : true, //隔行变色
			singleSelect : true,
			showFooter : true,
			pageSize : 20
		})
		alerthide();
	});
	//清空表单搜索条件 
	function clearAll() {
		var myDate = new Date(top.getservertime());
		$('#mindate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		$('#maxdate').datebox('setValue', myDate.Format('yyyy-MM-dd'));
		$('#sortid').val('2');
		$('#mintime').val('8');
		$('#maxtime').val('18');
		$('#shousename').val('');
		$('#shouseid').val('');
		$('#awareno').val('');
		$('#awareid').val('');
		$('#adbrandname').val('');
		$('#adbrandid').val('');
		$('#afullname').val('');
		$('#adtypeid').val('');
		$('#sseasonname').val('');
		$('#sprodyear').val('0');
	}
	function toggle(){
		var myDate = new Date(top.getservertime());
		$("#hsearch").slideToggle('fast');
		if(searchb){
			searchb=false;
			dheight=90;
			$('#highsearch').val('条件搜索');
			$("#csearch").hide();
			$("#qclear").hide();
			$('#gg').datagrid('resize',{
				height : Number($('body').css('height').replace('px',''))-90
			});
		}else{
			searchb=true;
			dheight=236;
			$('#highsearch').val('条件搜索▲');
			$("#csearch").show();
			$("#qclear").show();
			$('#gg').datagrid('resize',{
				height : Number($('body').css('height').replace('px',''))-236
			});
		}
		
	}
	
	function gaibian(){
		var min=$('#mintime').val();
		var max=$('#maxtime').val();
		if(parseInt(max)<parseInt(min)){
			alerttext("结束时段不能小于开始时段！")
			if(parseInt(min)!=23){
				$('#maxtime').val(parseInt(min)+1+'');
			}else{
				$('#maxtime').val(parseInt(min)+'');
			}
		}
	}
</script>
</head>
<body class="easyui-layout layout panel-noscroll">
	<!-- 工具栏 -->
	<div id="toolbars">
				<input type="button" id="highsearch" class="btn2" style="position: absolute; top:10px;" value="条件搜索" onclick="toggle()">
				<input id="csearch" class="btn1" type="button" style="display:none;position: absolute; top:10px;left:150px;" onclick="pp();toggle();" value="查找" />
				<input id="qclear" class="btn1" type="button" value="清除内容" style="display:none;position: absolute; left:250px;top:10px;" onclick="clearAll()"/>
	</div>
	<!-- 隐藏查询条件 -->
	<div id="hsearch"  style="background-color: white;margin-top: 3px;height:145px;display:none; font-size:12px;"  data-enter="pp();toggle();">
		<table width="100%" cellspacing="10" class="table1">
			<tr>
					<td align="right" width="70" class="header">开始日期</td>
					<td align="left" width="100"><input id="mindate" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input></td>
				
					<td align="right" width="70" class="header">结束日期</td>
					<td align="left" width="100"><input id="maxdate" editable="true" type="text" class="easyui-datebox" required="required" currentText="today"  style="width:164px;height:25px"></input></td>
					<td align="right" width="70" class="header">时段开始</td>
					<td align="left" width="100"><select id="mintime" 
						style="width: 164px; height: 25px">
						<option value="1">01</option>
						<option value="2">02</option>
						<option value="3">03</option>
						<option value="4">04</option>
						<option value="5">05</option>
						<option value="6">06</option>
						<option value="7">07</option>
						<option value="8" selected="selected">08</option>
						<option value="9">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">00</option>
					</select></td>
					<td align="right" width="70" class="header">时段结束</td>
					<td align="left" width="100"><select id="maxtime" onchange="gaibian()"
						style="width: 164px; height: 25px">
						<option value="1">01</option>
						<option value="2">02</option>
						<option value="3">03</option>
						<option value="4">04</option>
						<option value="5">05</option>
						<option value="6">06</option>
						<option value="7">07</option>
						<option value="8">08</option>
						<option value="9">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18" selected="selected">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">00</option>
				</select></td>
					</tr>
			<tr>
				<td align="right" class="header">店铺</td>
				<td align="left"><input type="text"
					 class="edithelpinput  house_help" name="shousename" data-value="#shouseid"
					id="shousename" 
					data-options="required:true" placeholder="<选择或输入>" ><span onclick="selecthouse('analysis')"></span>
					<input type="hidden" id="shouseid" value=""></td>
					<td align="right" width="100">销售方式</td>
				<td align="left" width="200"><select id="sortid" 
					style="width: 164px; height: 25px">
						<option value="0">零售</option>
						<option value="1">批发</option>
						<option value="2" selected="selected">所有</option>
				</select></td>

				<td align="right" class="header">货号</td>
				<td align="left"><input type="text"
					 class="edithelpinput wareno_help" data-value="#awareid" name="awareno"
					id=awareno 
					data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('analysis')"></span>
					<input type="hidden" id="awareid" value=""></td>
				<td align="right" class="header">品牌</td>
				<td align="left"><input type="text" class="edithelpinput brand_help" data-value="#adbrandid"
					name="adbrandname" id="adbrandname"
					 data-options="required:true"
					placeholder="<选择或输入>" ><span onclick="selectbrand('analysis')"></span> <input type="hidden"
					name="adbrandid" id="adbrandid" value=""></td>
				
			</tr>
			<tr>
				
				<td align="right" class="header">类型</td>
				<td align="left"><input type="text"
					 class="helpinput"
					name="afullname" id="afullname"  data-value="#adtypeid"
					data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectwaretype('analysis');"></span>
					<input type="hidden" name="adtypeid" id="adtypeid" value=""></td>

				<td align="right" class="header">季节</td>
				<td align="left"><input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
                      <span onclick="opencombox(this)"></span></td>
				<td align="right" class="header">生产年份</td>
				<td align="left"><select id="sprodyear"
					style="width: 164px; height: 25px">
						<option selected="selected" value="">--请选择--</option>						
				</select></td>
				</tr>
		</table>
	</div>
	<table id="gg" style="overflow: hidden;"></table>
	<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>

	<!-- 页面加载弹出窗 -->
	
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 商品帮助列表 -->
	<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
	<!-- 商品类型帮助列表 -->
	<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
	<!-- 品牌帮助列表 -->
	<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
	<!-- 销售员帮助列表 -->
	<jsp:include page="../HelpList/EmpHelpList.jsp"></jsp:include>
	<script type="text/javascript">
	var AMOUNT;
	var CURR;
		//分页设置
		function pp() {
			
			
			//加载数据
			$('#gg').datagrid(
					{
						idField : 'WARENAME',
						width : '100%',
						height : Number($('body').css('height').replace('px','')) - 236,
						fitColumns : true,
						striped : true, //隔行变色
						//rownumbers : true, //显示行数
						singleSelect : true,
						showFooter : true,
						columns : [ [
								{
									field : 'ROWNUMBER',
									title : '序号',
									width : 50,
									fixed : true,
									align : 'center',
									halign : 'center',//halign表示对齐头部
									formatter:function(value,row,index){
										if(value!=''&&value!=null){
											return index+1+(rownumbers-1)*pagecount;
										}
									}
								}, {
									field : 'TIMESTR',
									title : '时点',
									width : 20,
									align : 'center',
									halign : 'center'
								}, {
									field : 'AMOUNT',
									title : '数量',
									width : 20,
									align : 'right',
									halign : 'center',
									formatter:function(value,row,index){
										if(value!=''&&value!=null){
											var val=Number(value);
											var nval=val.toFixed(0);
											if(nval!=0){
												return nval.toString();
											}else{
												return "";
											}
										}
									}
								}, {
									field : 'RATEAMT',
									title : '占比',
									width : 20,
									align : 'right',
									halign : 'center',
									formatter:function(value,row,index){
										if(value!=''&&value!=null){
											var val =Number(value);
											var nval=val.toFixed(2);
											return nval+"%";
										}
									}
								}, {
									field : 'CURR',
									title : '销售额',
									width : 20,
									align : 'right',
									halign : 'center',
									formatter:function(value,row,index){
										if(value!=''&&value!=null){
											var val=Number(value);
											var nval=val.toFixed(2);
											if(nval!=0){
												return nval.toString();
											}else{
												return "";
											}
										}
									}
								}, {
									field : 'RATECUR',
									title : '占比',
									width : 20,
									align : 'right',
									halign : 'center',
									formatter:function(value,row,index){
										if(value!=''&&value!=null){
											var val =Number(value);
											var nval=val.toFixed(2);
											return nval+"%";
										}
									}
								} ] ],
						onClickRow : function(rowIndex, rowData) {

						},
						pageSize : 20
					});
			getsalepointadata();
			getsalepointa('1');

		}
		//统计销售时点分析
		function getsalepointadata() {
			var mindate = $("#mindate").datebox('getValue');//开始日期
			var maxdate = $("#maxdate").datebox('getValue');//结束日期
			var houseid = $("#shouseid").val();//店铺
			var xsid = $("#xsid").val();//销售方式
			var mintime = $("#mintime").val();//时段开始
			var maxtime = $("#maxtime").val();//时段结束
			var wareno=$("#awareno").val();//货号no
			var wareid=$("#awareid").val();//货号id
			var dbrandid = $("#adbrandid").val();//品牌
			var typeid = $("#adtypeid").val();//类型
			var prodyear = $("#sprodyear").val();//生产年份
			var seasonname = $("#sseasonname").val();//季节
			alertmsg(6);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "SalePointAnalysisServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名  
				async:false,
				data : {
					salepointaser : "salepointadata",
					mindate : mindate,
					maxdate : maxdate,
					mintime : mintime,
					maxtime:maxtime,
					houseid : houseid,
					xsid : xsid,
					wareno:wareno,
					wareid : wareid,
					dbrandid :dbrandid,
					typeid : typeid,
					prodyear : prodyear,
					seasonname : seasonname
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'text',
				success : function(text) { //回调函数，result，返回值 
					try{if (isSucess(text)) {
						var data = $.parseJSON(text);
						var MESS=data.MESS;
						if(MESS!=''&&MESS!=undefined){
							alerttext(MESS);
						}
						AMOUNT=data.TOTALAMT;
						CURR=data.TOTALCURR;
					}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
		//查询销售时点分析列表方法 
		function getsalepointa(page) {
			var mindate = $("#mindate").datebox('getValue');//开始日期
			var maxdate = $("#maxdate").datebox('getValue');//结束日期
			var houseid = $("#shouseid").val();//店铺
			var xsid = $("#xsid").val();//销售方式
			var mintime = $("#mintime").val();//时段开始
			var maxtime = $("#maxtime").val();//时段结束
			var wareno=$("#awareno").val();//货号no
			var wareid=$("#awareid").val();//货号id
			var dbrandid = $("#adbrandid").val();//品牌
			var typeid = $("#adtypeid").val();//类型
			var prodyear = $("#sprodyear").val();//生产年份
			var seasonname = $("#sseasonname").val();//季节
			alertmsg(6);
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "SalePointAnalysisServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data : {
					salepointaser : "salepointa",
					mindate : mindate,
					maxdate : maxdate,
					mintime : mintime,
					maxtime:maxtime,
					xsid : xsid,
					houseid : houseid,
					wareno:wareno,
					wareid : wareid,
					dbrandid :dbrandid,
					typeid : typeid,
					prodyear : prodyear,
					seasonname : seasonname,
					page : page
				}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType : 'text',
				success : function(text) { //回调函数，result，返回值 
					try{if(isSucess(text)) {
						var data = $.parseJSON(text);
						var totals = data.total;
						$("#pp").setPage({
					        pageCount:Math.ceil(Number(totals)/ pagecount),
					        current:Number(page)
						 });
						var footer = [{ROWNUMBER:"",TIMESTR:"合计",AMOUNT:AMOUNT,CURR:CURR}];
						$('#gg').datagrid('loadData', data);
						$('#gg').datagrid('reloadFooter',footer);
						$('#notetotal').html('共有'+totals+'条记录');
						$('#gg').datagrid('scrollTo',0);
					}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
		
	</script>
</body>
</html>