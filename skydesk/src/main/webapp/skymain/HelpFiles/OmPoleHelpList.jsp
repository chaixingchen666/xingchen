<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到陈列杆列表  -->
	<div id="dx_polef" title="选择陈列杆" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_POLEsbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_polefindbox" data-enter="getdx_poledata('1')"  placeholder="搜索陈列杆<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_poledata('1')">
		<input  type="hidden" id="dx_poleser" >
	</div>
		<table id="dx_polet" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_polepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_poleselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载陈列杆列表窗
function selectdx_pole(dx_poleser){
	$("#dx_poleser").val(dx_poleser);
	var findbox='';
	$('#dx_polefindbox').val(findbox);
	var $dx_dg = $('#dx_polet');
	if(!$dx_dg .data().datagrid){
		$('#dx_polepp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_poledata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : '陈列杆ID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'POLEID',
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
					field : 'POLENAME',
					title : '陈列杆',
					width : 200
				} ] ],
				toolbar: '#dx_polesbtn'
			};
		$dx_dg.datagrid(options);
		getdx_poledata('1');//加载数据
	}
	$dx_dg.datagrid('clearChecked');
	getdx_poledata(1);
	$('#dx_polef').dialog('open');
	$('#dx_polefindbox').focus();
}
  //双击、确定赋值（陈列杆）
function dx_poleselects(){
	var checkrows = $('#dx_polet').datagrid('getChecked');
	var dx_poleser = $('#dx_poleser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.POLEID); 
			namearr.push(row.POLENAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	if(dx_poleser=='search'){
		$('#spolename').val(name);
		$('#spoleid').val(value);
		$('#spolename').focus();
	}else if(dx_poleser=='load'){
		$('#lpolename').val(name);
		$('#lpoleid').val(value);
		$('#lpolename').focus();
	}
	$('#dx_polef').dialog('close');
}
//获取陈列杆列表
function getdx_poledata(page) { //定义回调函数#')
	var findbox = $('#dx_polefindbox').val();
	var omid = Number(top.omid);
	findbox = findbox==undefined?"":findbox;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyordjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "listompole",
			findbox : findbox,
			omid: omid,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到       
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
				$('#dx_polepp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_polet');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_polefindbox').focus();
	  			}
			}catch(err){
				console.error(err.message);
			}
		}
	});
} 
</script>
