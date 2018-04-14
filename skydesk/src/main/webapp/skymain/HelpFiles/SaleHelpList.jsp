<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到销售类型列表  -->
	<div id="dx_salef" title="选择销售类型" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_salesbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_salefindbox" data-enter="getdx_saledata('1')"  placeholder="搜索销售类型<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_saledata('1')">
		<input  type="hidden" id="dx_saleser" >
	</div>
		<table id="dx_salet" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_salepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_saleselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载销售类型列表窗
function selectdx_sale(dx_saleser){
	$("#dx_saleser").val(dx_saleser);
	var findbox='';
	$('#dx_salefindbox').val(findbox);
	var $dx_dg = $('#dx_salet');
	if(!$dx_dg .data().datagrid){
		$('#dx_salepp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_saledata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'SALEID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'SALEID',
					title : 'ID',
					width : 200,
					hidden : true
				},{
					field: "CHECK",
					title: "选择",
					checkbox: true
				},{
					field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
					formatter: rownumberfm
				}, {
					field : 'SALENAME',
					title : '销售类型',
					width : 200
				} ] ],
				toolbar: '#dx_salesbtn'
			};
		$dx_dg.datagrid(options);
		getdx_saledata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_saledata(1);
	$('#dx_salef').dialog('open');
	$('#dx_salefindbox').focus();
}
  //双击、确定赋值（销售类型）
function dx_saleselects(){
	var checkrows = $('#dx_salet').datagrid('getChecked');
	var dx_saleser = $('#dx_saleser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.SALEID); 
			namearr.push(row.SALENAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_saleser=='search'){
		$('#ssalename').val(name);
		$('#ssaleid').val(value);
		$('#ssalename').focus();
	}else if(dx_saleser=='load'){
		$('#lsalename').val(name);
		$('#lsaleid').val(value);
		$('#lsalename').focus();
	}
	$('#dx_salef').dialog('close');
}
//获取销售类型列表
function getdx_saledata(page) { //定义回调函数#')
	var findbox = $('#dx_salefindbox').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "salecodelist",
			findbox: findbox,
			rows: pagecount,
			fieldlist: "*",
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_salepp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_salet');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_salefindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
