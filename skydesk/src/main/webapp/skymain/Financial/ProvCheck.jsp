<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta name="renderer" content="webkit|ie-comp|ie-stand" /> 
<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1">
<title>采购对账分析</title>
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
<!-- 工具栏 -->
<div id="toolbars" class="toolbar">
<div class="toolbar_box1">
<div class="fl">
<input type="button" id="highsearch" class="btn2" value="查询条件▼" onclick="toggle()"> 
<span id="serbtn" class="fr hide">
<input type="button" class="btn2" value="查找" id="cz" onclick="searchdata();toggle();" /> 
<input type="button" class="btn2" value="清除条件" onclick="resetsearch();" />
</span>
</div>
<div class="fr">
<input type="button" class="btn3" type="button" value="导出" id="odser" onclick="fyexportxls('#pp_total','#gg',currentdata0,'api',0);">

</div>
</div>
	<!-- 查询------------ -->
<div class="searchbar" id="searchbar" style="display: none;" data-enter="searchdata();toggle();">
<div class="search-box">
<div class="s-box"><div class="title">开始日期</div>
<div class="text"><input class="easyui-datebox" name="startdate" id="startdate"
					style="width: 163px; height: 28px; margin-left: 10px"/>
</div>
</div>
<div class="s-box"><div class="title">结束日期</div>
<div class="text"><input class="easyui-datebox" name="enddate" id="enddate"
				style="width: 163px; height: 28px; margin-left: 10px"/>
</div>
</div>
<div class="s-box"><div class="title">供应商</div>
<div class="text"><input class="edithelpinput provd_help" data-value="#sprovid" id="sprovname" name="sprovname" maxlength="20" placeholder="<选择或输入>"
					type="text" ><span onclick="selectdx_prov('search')"></span>
					<input type="hidden" id="sprovid" name="sprovid">
</div>
</div>
<div class="s-box"><div class="title">店铺</div>
<div class="text"><input class="edithelpinput house_help wid145 hig25" data-value="#shouseid" maxlength="30" placeholder="<选择或输入>"
					   type="text" id="shousename" name="shousename"><span  onclick="selecthouse('search')"></span> 
					   <input type="hidden" id="shouseid" name="shouseid">
</div>
</div>
<div class="s-box"><div class="title">欠款情况</div>
<div class="text"><select style="width: 162px; height: 25px" id="qkid">
						<option value="0">有欠款</option>
						<option value="1">有余额</option>
						<option value="2" selected="selected">所有</option>
				</select>
</div>
</div>
</div>
	    </div>
	</div>
	<table id="gg" style="overflow: hidden; height:9000px;"></table>
	<!-- 分页 -->
	<div
		class="page-bar">
		<div class="page-total">
			共有<span id="pp_total">0</span>条记录
		</div>
		<div id="pp" class="tcdPageCode page-code"></div>
	</div>
	<!-- 明细dialog -->
	<div id="md" title="对账明细" style="width: 100%; height: 100%" class="easyui-dialog" closed=true>
	<div class="toolbar" id="md_toolbars">
	<div class="fr">
		<input type="button" class="btn3" type="button" value="导出" id="odser2" onclick="fyexportxls('#totalmx','#ggmx',currentdata1,'api',0,provnamemx+'采购对账明细表');">
	</div>
	</div>
	<table id="ggmx" style="overflow: hidden; height:100%; width:100%"></table>
	<!-- 明细分页 -->
	<div style="height: 30px; border: 1px #d7d7d7 solid; background-color: white; margin-top: 10px;">
		<div
			class="page-total">
			共有<span id="totalmx">0</span>条记录
		</div>
		<div id="ppmx" class="tcdPageCode page-code"></div>
	</div>
	</div>
	<script type="text/javascript" charset="UTF-8">
	var notetype="";
	var provmxid="";
	var provnamemx="";
	var pgid="pg1616";
	var levelid = Number(getValuebyName("GLEVELID")); 
	setqxpublic();
//当浏览器窗口大小改变时，设置显示内容的高度  
		window.onresize = function() {
			changeDivHeight();
		}
		function changeDivHeight() {
			var height = document.documentElement.clientHeight;//获取页面可见高度 
			var width = document.documentElement.clientWidth;//获取页面可见高度 
			$('#gg').datagrid('resize', {
				width : width,
				height : height - 95
			});
		}
		$(function() {
			alerthide();
			$('#pp').createPage({
				pageCount : 1,
				current : 1,
				backFn : function(p) {
					searchpage(p.toString());
				}
			});
			$('#gg').datagrid({   
				width : '100%',
				height : $('body').height() - 50,
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				showFooter : true,
				sort: "PROVNAME",
				order: "asc",
				onDblClickRow: function(rowIndex, rowData){
					provmxid=rowData.PROVID;
					provnamemx=rowData.PROVNAME;
						$('#md').dialog({
							modal : true,
							title : provnamemx+'对账表'
						});
					$('#md').dialog('open');
					searchpagemx(1);
				 },
				columns : [ [ {
					field : 'ROWNUMBER',
					title : '序号',
					width : 50,
					fixed : true,
					align : 'center',
					halign : 'center',
					formatter : function(value, row, index) {
						if (value != null){
							var val = Math.ceil(Number(value)/ pagecount)-1;
							return val*pagecount+Number(index)+1;
						}else{
							return value;
						}
					}
				}, {
					field : 'PROVNAME',
					title : '供应商',
					sortable: true,
					width : 160,
					align : 'center',
					halign : 'center'
				}, {
					field : 'CURR0',
					title : '期初欠款',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				}, {
					field : 'CGAMT',
					title : '采购数量',
					sortable: true,
					width : 160,
					fieldtype: "G",
					align : 'center',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value);
						}
					}
				}, {
					field : 'CGCURR',
					title : '采购金额',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				}, {
					field : 'CTAMT',
					title : '退货数量',
					sortable: true,
					width : 160,
					fieldtype: "G",
					align : 'center',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value);
						}
					}
				}, {
					field : 'CTCURR',
					title : '退货金额',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				}, {
					field : 'FYCURR',
					title : '应付费用',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				},{
					field : 'ZKCURR',
					title : '采购折让',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter: function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				},{
					field : 'CURR1',
					title : '应付金额',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				}, {
					field : 'CURR2',
					title : '已付金额',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				}, {
					field : 'CURR3',
					title : '期末欠款',
					sortable: true,
					width : 160,
					fieldtype: "N",
					align : 'right',
					halign : 'center',
					formatter:function(value,row,index){
						if(Number(value) == 0){
							return "";
						}else{
							return Number(value).toFixed(2);
						}
					}
				},  {
					field : 'ID',
					title : 'ID',
					width : 160,
					hidden : true
				} ] ],
				toolbar : "#toolbars",
				onSortColumn: function(sort, order){
					$('#gg').datagrid('options').sort= sort;
					$('#gg').datagrid('options').order = order;
					$('#pp').refreshPage();
				},
			}).datagrid("keyCtr");
	$('#ppmx').createPage({
		pageCount : 1,
		current : 1,
		backFn : function(p) {
			searchpagemx(p.toString());
		}
	});
	$('#ggmx').datagrid({  
		height : $('body').height()-110,
		fitColumns : true,
		striped : true, //隔行变色
		singleSelect : true,
		showFooter:true,
		onDblClickRow: function(rowIndex, rowData){
			if(!detail_bol) return;
			var noteno = rowData.NOTENO;
				var notetype = rowData.NOTETYPE;
		   		if(noteno!=''&&noteno!=undefined){
			   		opendetaild(notetype,noteno);
		   		}
		},
		onDblClickCell: function(index, field, value) {
			if((levelid==0||levelid==3||levelid==5)&&field=="REMARK0"){
				detail_bol = false;
				var row = $(this).datagrid("getRows")[index];
				opennoteremarkd("#ggmx",index,row);
			}else detail_bol = true;
		},
		columns : [ [{
			field : 'ROWNUMBER',
			title : '序号',
			width : 50,
			fixed : true,
			align : 'center',
			halign : 'center',
			formatter : function(value, row, index) {
				if (value != null){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return val*pagecount+Number(index)+1;
				}else{
					return value;
				}}
		}, {
			field : 'NOTEDATE',
			title : '日期',
			width : 130,
			fixed : true,
			align : 'center',
			halign : 'center'
		}, {
			field : 'REMARK',
			title : '类型',
			width : 60,
			fixed : true,
			align : 'center',
			halign : 'center'
		}, {
			field : 'CGAMT',
			title : '采购数',
			width : 80,
			fieldtype: "G",
			fixed : true,
			align : 'center',
			halign : 'center',
			formatter:function(value,row,index){
				if(Number(value) == 0){
					return "";
				}else{
					return Number(value);
				}
			}
		},{
			field : 'CGCURR',
			title : '采购额',
			width : 80,
			fieldtype: "N",
			fixed : true,
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				if(Number(value) == 0){
					return "";
				}else{
					return Number(value).toFixed(2);
				}
			}
		},  {
			field : 'CTAMT',
			title : '退货数量',
			width : 80,
			fieldtype: "G",
			fixed : true,
			align : 'center',
			halign : 'center',
			formatter:function(value,row,index){
				if(Number(value) == 0){
					return "";
				}else{
					return Number(value);
				}
			}
		}, {
			field : 'CTCURR',
			title : '退货金额',
			width : 80,
			fieldtype: "N",
			fixed : true,
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				if(Number(value) == 0){
					return "";
				}else{
					return Number(value).toFixed(2);
				}
			}
		}, {
			field : 'FYCURR',
			title : '采购费用',
			width : 100,
			fieldtype: "N",
			fixed : true,
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				return Number(value).toFixed(2);
			}
		}, {
			field : 'CURR1',
			title : '应付',
			width : 100,
			fieldtype: "N",
			fixed : true,
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				return Number(value).toFixed(2);
			}
		}, {
			field : 'CURR2',
			title : '已付',
			width : 100,
			fixed : true,
			fieldtype: "N",
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				return Number(value).toFixed(2);
			}
		}, {
			field : 'CURR3',
			title : '余额',
			width : 120,
			fixed : true,
			fieldtype: "N",
			align : 'right',
			halign : 'center',
			formatter:function(value,row,index){
				return Number(value).toFixed(2);
			}
			
		}, {
			field : 'NOTENO',
			title : '单据号',
			width : 140,
			fixed : true,
			align : 'center',
			halign : 'center'
		}, {
			field : 'REMARK0',
			title : '摘要',
			width : 200,
			fixed : true,
			align : 'center',
			halign : 'center'
		}] ],
		toolbar : "#md_toolbars"
	}).datagrid("keyCtr");
 });
			
		//高级搜索
		var searchb = false;
		function toggle() {
			ser='sear';
			if (searchb) {
				$('#highsearch').val('查询条件▼');
				$('#serbtn').hide();
				searchb = false;
			} else {
				$('#highsearch').val('查询条件▲');
				$('#serbtn').show();
				searchb = true;
			}
			$('#searchbar').slideToggle('fast');
		}
		//清除条件
		function resetsearch(){
			$('#sprovid').val("");
			$('#sprovname').val("");
			$('#shouseid').val('');
			$('#shousename').val('');
			$('#qkid').val('2');
			var myDate = new Date(top.getservertime());
			$('#startdate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
			$('#enddate').datebox('setValue', myDate.Format('yyyy-MM-dd')); // 设置日期输入框的值
		}
		var footer = [];
		//获取数据11111111
		function searchdata() {
			var provid = $('#sprovid').val();
			var mindate = $('#startdate').datebox('getValue');
			var maxdate = $('#enddate').datebox('getValue');
			var qkid = $('#qkid').val();
			var houseid= Number($('#shouseid').val());
			var sort = $('#gg').datagrid('options').sort;
			var order = $('#gg').datagrid('options').order;
			alertmsg(6);
			$.ajax({
				type : 'POST',
				url : '/skydesk/fyjerp',
				data : {
					erpser: 'totalprovcheck',
					providlist: provid,
					mindate: mindate,
					maxdate: maxdate,
					houseid: houseid,
					qkid: qkid,
					sort: sort,
					order: order
				},
				dataType : 'json',
				success : function(data) {
					try{
						if (valideData(data)) {
							var totals = data.total;
							var WARNING = data.WARNING;
							if(WARNING!=''&&WARNING!=undefined){
								alerttext(WARNING);
								setTimeout(function(){
									return;
								}, 3000)
							}
							searchpage(1);
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}
		var currentdata0 = {};
		//分页获取
		function searchpage(page) {
			alertmsg(6);
			//	&hzfs=[汇总方式]&sortid=[排 序方式]
			var sort = $('#gg').datagrid('options').sort;
			var order = $('#gg').datagrid('options').order;
			var qkid = $('#qkid').val();
			currentdata0 =  {
					erpser : "listprovcheckhz",
					qkid: qkid,
					sort: sort,
					rows: pagecount,
					order: order,
					page : page
				};
			$.ajax({
				type : "POST", //访问WebService使用Post方式请求 
				url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				dataType : 'json',
				data :currentdata0, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				success : function(data) { //回调函数，result，返回值 
					try{
						if (valideData(data)) {
							data.footer = [{
								CURR0: data.curr0,
								CGAMT: data.cgamt,
								CGCURR: data.cgcurr,
								CTAMT: data.ctamt,
								CTCURR: data.ctcurr,
								FYCURR: data.fycurr,
								ZKCURR: data.zkcurr,
								CURR1: data.curr1,
								CURR2: data.curr2,
								CURR3: data.curr3
							}];
							var totals = data.total;
							$("#pp_total").html(totals);
							$("#pp").setPage({
						        pageCount:Math.ceil(Number(totals)/ pagecount),
						        current: Number(page)
							 });
							$('#gg').datagrid('loadData', data);
							$('#gg').datagrid('scrollTo',0);
							$('#gg').datagrid('resize');
						}
					}catch(err){
						console.error(err.message);
					}
				}
			});
		}	
		
		//获取供应商对账明细
		var currentdata1 = {};
		function searchpagemx(page) {
			alertmsg(6);
			currentdata1 = {
					erpser : 'listprovcheckmx',
					provid : provmxid,
					rows: pagecount,
					page:page
				};
			$.ajax({
				type : 'POST',
				url : '/skydesk/fyjerp',
				data : currentdata1,
				dataType : 'json',
				success : function(data) {
					//"curr3":"-50704.5","curr1":"134.1","curr2":"50838.6","xsamt":"45","xscurr":"34.1","xtamt":"0","xtcurr":"0"
					if (valideData(data)){
						var total = data.total;
						$('#totalmx').html(total);
						$("#ppmx").setPage({
					        pageCount:Math.ceil(Number(total)/ pagecount),
					        current: Number(page)
						 });
						data.footer = [{
							CURR1: data.curr1,
							CURR2: data.curr2,
							CURR3: data.curr3,
							CGAMT: data.cgamt,
							CGCURR: data.cgcurr,
							FYCURR: data.fycurr,
							CTAMT: data.ctamt,
							CTCURR: data.ctcurr
						}];
						$('#ggmx').datagrid('loadData', data);
					}
				}
			});
		}
	
	</script>
	<!-- 店铺帮助列表 -->
	<jsp:include page="../HelpList/HouseHelpList.jsp"></jsp:include>
	<!-- 供应商帮助列表 -->
	<jsp:include page="../HelpFiles/ProvHelpList.jsp"></jsp:include>
	<!-- 单据明细帮助列表 -->
    <jsp:include page="../HelpList/NoteDetail.jsp"></jsp:include>
    <jsp:include page="../HelpList/UpdateNoteFiles.jsp"></jsp:include>
</body>
</html>