<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到客户列表  -->
	<div id="dx_custf" title="选择客户" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_custsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_custareaname" style="width:160px" data-enter="getdx_custdata('1')"  placeholder="搜索客户区域<回车搜索>"  data-end="yes">
		<input type="text" class="help-qsipt" id="dx_custfindbox" style="width:160px" data-enter="getdx_custdata('1')"  placeholder="搜索客户<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_custdata('1')">
		<input  type="hidden" id="dx_custser" >
	</div>
		<table id="dx_custt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_custpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_custselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载客户列表窗
function selectdx_cust(dx_custser){
	$("#dx_custser").val(dx_custser);
	var findbox='';
	$('#dx_custfindbox').val(findbox);
	var $dx_dg = $('#dx_custt');
	if(!$dx_dg .data().datagrid){
		$('#dx_custpp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_custdata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'CUSTID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'CUSTID',
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
					field : 'CUSTNAME',
					title : '客户',
					width : 200
				} ] ],
				toolbar: '#dx_custsbtn'
			};
		$dx_dg.datagrid(options);
		getdx_custdata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_custdata(1);
	$('#dx_custf').dialog('open');
	$('#dx_custfindbox').focus();
}
  //双击、确定赋值（客户）
function dx_custselects(){
	var checkrows = $('#dx_custt').datagrid('getChecked');
	var dx_custser = $('#dx_custser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.CUSTID); 
			namearr.push(row.CUSTNAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_custser=='search'){
		$('#scustname').val(name);
		$('#scustid').val(value);
		$('#scustname').focus();
	}else if(dx_custser=='load'){
		$('#lcustname').val(name);
		$('#lcustid').val(value);
		$('#lcustname').focus();
	}
	$('#dx_custf').dialog('close');
}
//获取客户列表
function getdx_custdata(page) { //定义回调函数#')
	var findbox = $('#dx_custfindbox').val();
	var areaname = $('#dx_custareaname').val();
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser : "customerlisthelp",
			areaname: areaname,
			noused : 0,
			rows: pagecount,
			sort: "custname",
			order: "asc",
			findbox: findbox,
			fieldlist: "custid,custname,discount,pricetype",
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_custpp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_custt');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_custfindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
	
} 
</script>
