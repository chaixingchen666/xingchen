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
<title>订货商品统计</title>
<link rel="stylesheet" href="/skydesk/css/icon.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/pagination.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/easydropdown.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/skystyle.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<link rel="stylesheet" href="/skydesk/css/demo.min.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<style type="text/css">
#toolbars{height:40px;}
body {
	font-family: 微软雅黑;
	color: black;
	margin: auto;
}
#hsearch .combox-select{
	width:142px;
}
#hsearch .help-divipt{width:144px;}
</style>
<link rel="stylesheet" href="/skydesk/css/msgbox.css?ver=<%=tools.DataBase.VERSION%>" type="text/css" />
<script type="text/javascript" src="/skydesk/js/msgbox.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/jquery.min.js"></script>
<script type="text/javascript" src="http://win.skydispark.com/skyasset/js/decimal.min.js"></script>
<script type="text/javascript" src="/skydesk/js/jquery.easyui.min.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/easyui-lang-zh_CN.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/jquery.fyutils.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/datagrid-groupview.js?ver=<%=tools.DataBase.VERSION%>"></script>
<script type="text/javascript" src="/skydesk/js/regexp.js?ver=<%=tools.DataBase.VERSION%>"></script>

<script type="text/javascript">
var searchb=false;
var dheight=85;
var rownumbers=1;

	var epno = getValuebyName("EPNO");
	//根据这个判断是否显示成本价及成本额
	setqxpublic();
	if(allowinsale==1){
		hide5=false;
	}else{
		hide5=true;
	}
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
	$(function(){
		$("input:radio[name='pai1']").eq(0).attr("checked",true);//单选框选中
		
		$("#pp").createPage({
			pageCount:1,//总页码
			current:1,//当前页 
	        backFn:function(p){
	        	rownumbers=p;
	        	getinventoryl(p.toString());
	        }
		 });
		$('#gg').datagrid({
			idField : 'WARENAME',
			height : $('body').height() - 80,
			striped : true, //隔行变色
			singleSelect : true,
			showFooter : true,
			pageSize : 20
		})
		
		toggle();
	});
	//清空表单搜索条件 
	function clearAll() {
			$('#aordername').val('');
			$('#aorderid').val('');
			$('#paixu').val('1');
			$('#scustid').val('');
			$('#scustname').val('');
			$('#pai1').prop('checked',true);
		}
	function toggle(){
		$("#hsearch").slideToggle('fast');
		if(searchb){
			searchb=false;
			dheight=85;
			$('#highsearch').val('条件搜索');
			$("#csearch").hide();
			$("#qclear").hide();
			$('#gg').datagrid('resize',{
				height : Number($('body').css('height').replace('px',''))-85
			});
		}else{
			searchb=true;
			dheight=213;
			$('#highsearch').val('条件搜索▲');
			$("#csearch").show();
			$("#qclear").show();
			$('#gg').datagrid('resize',{
				height : Number($('body').css('height').replace('px',''))-143
			});
			
		}
		
	}

	function checkorder(){
		var order=$('#aorderid').val();
		if(order==''){
			alerttext("请选择订货会！");
		}else{
			toggle();pp();
		}
	}
		
</script>
</head>
<body class="easyui-layout layout panel-noscroll">
	<!-- 工具栏 -->
	<div id="toolbars">
				<input type="button" id="highsearch" style="position: absolute; top:10px;" class="btn2"value="条件搜索" onclick="toggle()">
				<input id="csearch" class="btn1" type="button" style="display:none;position: absolute; top:10px;left:150px;" onclick="checkorder()" value="查找" />
				<input id="qclear" class="btn1" type="button" value="清除内容" style="display:none;position: absolute; left:250px;top:10px;" onclick="clearAll()"/>
				<input type="button" class="btn3" style="width:45px;position: absolute; top:10px;right:50px;" value="导出" onclick="exp()">
	</div>
	<!-- 隐藏查询条件 -->
	<div id="hsearch"  style="background-color: white;height:60px;display:none; font-size:12px;"  data-enter="toggle();pp();">
		<table  width="100%" cellspacing="10" class="table1" style="font-size:12px;">
				<tr>
					<td align="right" class="header">订货会</td>
					
					<td align="left"><input  type="text"  class="edithelpinput" data-value="#aorderid"
				name="aordername" id="aordername" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择>" ><span onclick="selectorder('analysis')"></span>
				 <input type="hidden" id="aorderid" value=""></td>
					<td align="right" class="header">经销商</td>
					<td align="left"><input  type="text"  class="edithelpinput cust_help" data-value="#scustid"
				name="scustname" id="scustname" style="width:125px;height:24px;" data-options="required:true" placeholder="<选择或输入>" ><span  onclick="selectbuyer('analysis');"></span>
				 <input type="hidden" id="scustid" value=""></td>
					
					<td align="right" width="70" style="padding-left:20px;" class="header">排序项目</td>
					<td align="left" width="100"><select id="paixu" style="width:144px;height:25px;">
                    	<option value="0">货号</option>
                        <option selected="selected" value="1">数量</option>
                        <option value="2">金额</option>
                    </select></td>
				
					<td align="right" width="70" style="padding-left:20px;" class="header">排序方式</td>
					<td align="left" width="100"><input type="radio" name="pai1" value="1" checked="checked"/>升序
					<input type="radio" name="pai1" value="0" />降序</td>
				</tr>
				
			</table>
	</div>
	<table id="gg" style="overflow: hidden;height:900px"></table>
	<div class="page-bar">
	<div class="page-total" id="notetotal">共有0条记录</div>
	<input type="hidden" id="total" value="1">
	<div id="pp" class="tcdPageCode page-code">
	</div>
</div>


<!-- 经销商帮助列表 -->
<jsp:include page="../HelpList/BuyerHelpList.jsp"></jsp:include>
<!-- 订货会帮助列表 -->
<jsp:include page="../HelpList/OrderHelpList.jsp"></jsp:include>
<script type="text/javascript"> 
var hide=0;
var hide1=true;
var TOTALCURR;//零售额
var WHOLECURR;//批发额
//分页设置
function pp(){
	hide=$('#huizong').val();
	if(hide==0)
		hide1=true;
	else
		hide1=false;
		
		getinventoryl('1');
		$('#gg').datagrid({
			idField : 'wareinm',
			width : '100%',
			height : $('body').height()-85,
			//fitColumns : true,
			striped : true, //隔行变色
			showFooter:true,
			nowrap :false,//允许自动换行
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
				if(rowData){
					for(var i=1;i<=cols;i++){
						$('#title'+i).html(eval('rowData.SIZENAME'+i));
					}
				}
			},
			frozenColumns:[[
			                {field : 'ROWNUMBER',title : '序号',fixed : true,width : 40,
								halign : 'center',align :'center',
								formatter : function(value,row,index){
								return value==""?"":index+1+(rownumbers-1)*pagecount;}
			                }
			                ,{
								field : 'WAREID',
								title : 'WAREID',
								width : 90,
								fixed : true,
								hidden : true
								},
							{
								field : 'WARENO',
								title : '货号',
								width : 100,
								fixed :true,
								halign : 'center',
								styler: function(value,row,index){
								if (value !="合计"){
									return 'text-align:left';
								}else{
									return 'text-align:center';
								}
							}

							},{
							field : 'WARENAME',
							title : '商品名称',
							width : 90,
							fixed : true,
							halign : 'center',
							align :'left'
							}
							,{
								field : 'RETAILSALE',
								title : '零售价',
								width : 90,
								fixed : true,
								halign : 'center',
								align :'right',
								formatter : function(value,row,index){
									return value=='0'||value=='0'||value==undefined?'':Number(value).toFixed(2);
								}
								}
							,{
								field : 'COLORNAME',
								title : '颜色',
								width : 90,
								fixed : true,
								halign : 'center',
								align :'center'
							}
							
							
									]] ,
					columns : [setColums()],
					pageSize : 20
					}).datagrid("keyCtr","");
					
				}
	var sizenum=getValuebyName('SIZENUM');
	var cols = Number(sizenum)<=5?5: Number(sizenum);
//生成列 
function setColums() {
	var colums = [];	
	colums[0] ={
			field : 'UNITS',
			title : '单位',
			width : 40,
			fixed : true,
			halign : 'center',
			align :'center'
		};	
	colums[1] = {
		field : 'WAREID',
		title : 'WAREID',
		hidden : true,
		width : 30
	};
	colums[2] = {
			field : 'COLORID',
			title : '颜色id',
			width : 10,
			hidden : true
		};
	colums[3] ={
			field : 'COLORNAME',
			title : '颜色',
			width : 50,
			fixed : true,
			hidden:hide1
		};
	for (var i = 1; i <= cols; i++) {
		colums[3 + i] = {
			field : 'AMOUNT' + i,
			title : '<span id="title'+i+'"><span>',
			width : 40,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				return value==''?'':Number(value)==0?'':value;
			}
		};
	}
	colums[cols + 4] = {
			field : 'AMOUNT',
			title : '订货数量',
			width : 80,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				return value=='0'||value=='0'||value==undefined?'':Number(value).toFixed(0);
			}
		};
	
	colums[cols + 5] = {
			field : 'RETAILCURR',
			title : '订货金额', 
			width : 80,
			fixed :true,
			halign : 'center',
			align :'right',
			formatter : function(value,row,index){
				return value=='0'||value=='0'||value==undefined?'':Number(value).toFixed(2);
			}
		};

	
	for (var i = 1; i <= cols; i++) {
		colums[cols + 5 + i] = {
			field : 'SIZEID' + i,
			title : 'sizeid',
			hidden : true,
			width : 10
		};
	}
	for (var i = 1; i <= cols; i++) {
		colums[cols + cols + 2 + i] = {
			field : 'SIZENAME' + i,
			title : 'sizename',
			hidden : true,
			width : 10
		};
	}
	return colums;
	}
//导出
function exp(){
	var total = Number($('#total').val()!=''?$('#total').val():0); 
	if(total==0){
		alert('数据为空，不能导出！');
	}else{
		$.messager.confirm(dialog_title,'确认导出:订货会商品统计.xls',function(r){    
		    if (r){   
		    	var omid=$("#aorderid").val();//订货会名称id
		    	var custid=$("#scustid").val();//经销商
		    	var sort=$("#paixu").val();//排序项目
		    	var order=$("input[name='pai1']:checked").val();//排序方式 
		    	
		    	var url = 'InquireOrderServlet?inquireorderser=exportinqorder&page=1'
		    					+ '&sort=' + sort + '&order=' + order
		    					+ '&omid=' + omid+ '&custid=' + custid;
		
		top.window.location.href = encodeURI(url);
		alert('正在努力导出，请不要重复操作！关闭此弹窗，请等待'+Math.ceil(total/10)+'秒左右，等待下载框弹出！');
		    }
		    });
	}
}
//查询订货会现场订货统计
function getinventoryl(page){
	var omid=$("#aorderid").val();//订货会名称id
	var custid=$("#scustid").val();//经销商
	var sort=$("#paixu").val();//排序项目
	var order=$("input[name='pai1']:checked").val();//排序方式 
	
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "InquireOrderServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			inquireorderser : "listomdetailtotalsize",
			omid:omid,
			sort:sort,
			custid:custid,
			order:order,
			page:page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'text',
		success : function(text) { //回调函数，result，返回值 
			if(isSucess(text)) {
				var data = $.parseJSON(text);
				var totals = data.total.split('^');
				$('#total').val(totals[0]);
				$("#pp").setPage({
			        pageCount:Math.ceil(Number(totals[0])/ pagecount),
			        current:Number(page)
				 });
				$('#gg').datagrid('loadData', data);
				$('#total').val(totals[0]);
				$('#notetotal').html('共有'+totals[0]+'条记录');
				$('#gg').datagrid('scrollTo',0);
				if(totals[0]!=0){
					$('#gg').datagrid('selectRow',0);
				}
			}
		}
	});
}
</script>
</body>
</html>