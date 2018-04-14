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
<title>库存尺码分析</title>
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
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
			<div class="fl">
				<input type="button" id="highsearch" class="btn2" value="条件搜索▲" onclick="toggle()">
				<input id="csearch" class="btn1" type="button"  onclick="pp();toggle();" value="查找" />
				<input id="qclear" class="btn1" type="button" value="清除内容"  onclick="clearAll()"/>
	</div>
	</div>
	<!-- 隐藏查询条件 -->
	<div id="hsearch"  class="hsearch-box"  data-enter="pp();toggle();">
	<div class="search-box">
	<div class="s-box">
	<div class="title">查询日期</div>
<div class="text"><input id="nowdate" type="text" editable="true" style="width:164px;height:25px" class="easyui-datebox" required="required" currentText="today"></input>
</div>
</div>
			
<div class="s-box">
	<div class="title">尺码类型</div>
<div class="text"><select id="sizegroupno"  style="width:164px;height:25px">
					                 <option value="">---请选择---</option>
                    </select>
</div>
</div>

<div class="s-box">
	<div class="title">排序项目</div>
<div class="text"><select id="sort" style="width:164px;height:25px">
                    	<option value="0" selected="selected">尺号代码</option>
                        <option value="1">数量</option>
                        <option value="2">销售额</option>
                    </select>
</div>
</div>
			
<div class="s-box">
	<div class="title">排序方式</div>
<div class="text"><input type="radio" name="pai1" value="1" id="pai1" checked="checked"/>升序
					<input type="radio" name="pai1" value="0" />降序
</div>
</div>
<div class="s-box">
	<div class="title">店铺</div>
<div class="text"><input type="text"
					 class="edithelpinput house_help"
					name="shousename" id="shousename"
					 data-options="required:true"
					placeholder="<选择或输入>" ><span onclick="selecthouse('analysis')"></span> <input type="hidden" id="shouseid" value="">
</div>
</div>
				
<div class="s-box">
	<div class="title">货号</div>
<div class="text"><input  type="text"  class="edithelpinput wareno_help" data-value="#awareid"
				name="awareno" id=awareno style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectware('analysis')"></span>
				 <input type="hidden" id="awareid" value="">
</div>
</div>
			
<div class="s-box">
	<div class="title">品牌</div>
<div class="text"><input  type="text"  class="edithelpinput brand_help" data-value="#adbrandid"
				name="adbrandname" id=adbrandname style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span onclick="selectbrand('analysis')"></span>
				 <input type="hidden" id="adbrandid" value="">
</div>
</div>
				
<div class="s-box">
	<div class="title">类型</div>
<div class="text"><input  type="text"  class="helpinput" data-value="#adtypeid"
				name="afullname" id="afullname" style="width:145px;height:24px;" data-options="required:true" placeholder="<选择或输入>"><span onclick="selectwaretype('analysis')"></span>
				 <input type="hidden" id="adtypeid" value="">
</div>
</div>
<div class="s-box">
	<div class="title">季节</div>
<div class="text"><input  type="text" class="edithelpinput season_help" name="sseasonname" id="sseasonname" maxlength="20" placeholder="<选择或输入>">
                      <span onclick="opencombox(this)"></span>
</div>
</div>
			
<div class="s-box">
	<div class="title">生产年份</div>
<div class="text"><select id="sprodyear"
					style="width: 164px; height: 25px">
						<option selected="selected" value="">--请选择--</option>						
				</select>
</div>
</div>
</div>
</div>
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
<!-- 颜色帮助列表 -->
<jsp:include page="../HelpList/ColorHelpList.jsp"></jsp:include>
<!-- 商品帮助列表 -->
<jsp:include page="../HelpList/WareHelpList.jsp"></jsp:include>
<!-- 尺码帮助列表 -->
<jsp:include page="../HelpList/SizeHelpList.jsp"></jsp:include>
<!-- 商品类型帮助列表 -->
<jsp:include page="../HelpList/WareTypeHelpList.jsp"></jsp:include>
<!-- 品牌帮助列表 -->
<jsp:include page="../HelpList/BrandHelpList.jsp"></jsp:include>
<script type="text/javascript"> 
var AMOUNT;
var CURR;
//分页设置
function pp(){
    	//加载数据
		$('#gg').datagrid(
							{
								idField : 'SIZENAME',
								height : $("body").height()-50,
								fitColumns : true,
								striped : true, //隔行变色
								singleSelect : true,
								showFooter:true,
								columns : [ [{
									field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',//halign表示对齐头部
									formatter:function(value,row,index){
										if(value!=''){
											return index+1+(rownumbers-1)*pagecount;
										}
									}
								},{
									field : 'SIZENAME',
									title : '尺码',
									width : 20,
									align:'center',
									halign:'center'
								}, {
									field : 'AMOUNT',
									title : '数量',
									width : 20,
									align:'right',
									halign:'center',
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
									align:'right',
									halign:'center',
									formatter: function(value,row,index){
										if(value!=''&&value!=null){
											var val = Number(value);
											var nval = val.toFixed(2);
											return nval.toString()+'%';
										}
									}
								}, {
									field : 'CURR',
									title : '零售额',
									width : 20,
									align:'right',
									halign:'center',
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
									align:'right',
									halign:'center',
									formatter: function(value,row,index){
										if(value!=''&&value!=null){
											var val = Number(value);
											var nval = val.toFixed(2);
											return nval.toString()+'%';
										}
									}
								}] ],
								toolbar: "#toolbars"
							});
			getinvstructdata();
			getinventorystruct('1');
		}
//查询统计库存结构分布方法
function getinvstructdata(){
	var nowdate =$('#nowdate').datebox('getValue');//当前时间
	var sizegroupno=$("#sizegroupno").val();//尺码类型
	var houseid=$("#shouseid").val();//店铺
	var wareno=$("#awareno").val();//货号no
	var wareid=$("#awareid").val();//货号id
	var adbrandid =$("#adbrandid").val();//品牌
	var adtypeid=$("#adtypeid").val();//类型
	var prodyear=$("#sprodyear").val();//生产年份
	var seasonname=$("#sseasonname").val();//季节
	alertmsg(6);
	$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "InvSizeAnalyServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			async:false,
			data : {
				invsizeser : "getinvsizeanalydata",
				nowdate:nowdate,
				sizegroupno:sizegroupno,
				houseid:houseid,
				wareid:wareid,
				wareno:wareno,
				adbrandid:adbrandid,
				adtypeid:adtypeid,
				prodyear:prodyear,
				seasonname:seasonname
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
//查询库存统计结构列表方法
function getinventorystruct(page){
	var sizegroupno=$("#sizegroupno").val();//尺码类型
	var wareno=$("#awareno").val();//货号no
	var wareid=$("#awareid").val();//货号id
	var houseid=$("#shouseid").val();//店铺
	var adbrandid =$("#adbrandid").val();//品牌
	var adtypeid=$("#adtypeid").val();//类型
	var prodyear=$("#sprodyear").val();//生产年份
	var order=$("input[name='pai1']:checked").val();//排序方式
	var sortid=$("#sort").val();//排序项目-汇总方式
	var seasonname=$("#sseasonname").val();//季节
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "InvSizeAnalyServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			invsizeser : "getinvsizeanaly",
			sizegroupno:sizegroupno,
			wareid:wareid,
			wareno:wareno,
			adbrandid:adbrandid,
			adtypeid:adtypeid,
			houseid:houseid,
			prodyear:prodyear,
			seasonname:seasonname,
			order:order,
			sortid:sortid,
			page:page
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
				var footer = [{ROWNUMBER:"",SIZENAME:"合计",AMOUNT:AMOUNT,CURR:CURR}];
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
//获取尺码组
function getsizelist() {
	alertmsg(6);
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "InvSizeAnalyServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {invsizeser:"getsizelist"},         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'text', 
        success: function(text) {     //回调函数，result，返回值 
        	try{if (isSucess(text)) {
        		 $('#sizegroupno').append(text);
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