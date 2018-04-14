<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到颜色列表 -->
<div id="sized" title="尺码帮助列表" style="width: 580px;height: 440px;" class="easyui-dialog" closed=true>
<input type="hidden" id="sizeser" name="sizeser">
<input type="hidden" id="size_wareid">
<!-- 搜索栏 -->
		<div id="sizesbtn" class="help-qstoolbar">
				<input type="text" placeholder="搜索尺码名称<回车搜索>" class="help-qsipt" id="sizefindbox" data-enter="getwaresizelist('1')" data-end="yes">
				<input class="btn2" type="button" value="搜索" onclick="getwaresizelist('1')">
			</div>

	<table id="sizet" style="overflow: hidden;"></table>
	<div class="dialog-footer">
			<div id="sizepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				 <input type="button" class="btn1" value="确定" onclick="sizefuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#sized').dialog('close')"/> -->
			</div>
		</div>
</div>
<script type="text/javascript">
var sizet = true;
//加载颜色框和数据
function selectsize(wareid,sizeser){
	$('#sizeser').val(sizeser);
	$('#size_wareid').val(wareid);
	var findbox='';
// 	if(sizeser == 'add'){
// 		findbox = $('#asizename').val();
// 	}if(sizeser == 'shopsale'){
// 		findbox = $('#sizename').val();
// 	}if(sizeser == 'update'){
// 		findbox = $('#usizename').val();
// 	}else{
// 		findbox = $('#wusizename').val();
// 	}
	if(sizeser=="shopsale"){
		var houseid = Number($("#uhouseid").val());
		var colorid = Number($("#colorid").val());
		if(isNaN(houseid)||houseid<=0){
			alerttext("请选择店铺！");
			return;
		}else if(isNaN(colorid)||colorid<=0){
			alerttext("请选择商品颜色！");
			return;
		}
	}
	$('#sizefindbox').val(findbox);
	if(sizet){
		$('#sizepp').createPage({
	        current:1,
	        backFn:function(p){
	        	getwaresizelist(p);
	        }
		 });
		$('#sizet').datagrid(
				{
					height:350,
					idField : 'SIZEID',
					fitColumns : true,
					striped : true, //隔行变色
					singleSelect : true,
					pageSize:20,
					checkOnSelect:true,
					columns : [ [ {
						field : 'SIZEID',
						title : 'ID',
						width : 200,
						hidden : true
					},{
						field : 'ROWNUMBER',
						title : '序号',
						width : 50,
						fixed:true,
						align:'center',
						halign:'center',
						formatter:function(value,row,index){
							var num = Number(value);
							if(!isNaN(num)) return num;
							return "";
						}
					},{
						field : 'SIZENAME',
						title : '尺码',
						width : 100,
						fixed:true,
						align:'center',
						halign:'center'
					},{
						field : 'AMOUNT',
						title : '库存',
						hidden: (sizeser=="shopsale"?false:true),
						width : 80,
						fixed:true,
						align:'center',
						halign:'center'
					}
					] ],
					onDblClickRow:function(rowIndex, rowData){
						sizefuzhi();
						},
					toolbar:'#sizesbtn'
				}).datagrid("keyCtr","sizefuzhi()");
		sizet = false;
	}
	getwaresizelist('1');
	$('#sized').dialog('open');
	$('#sizefindbox').focus();
	
}
//双击、确定赋值（尺码）
   function sizefuzhi(){
	var sizeser=$('#sizeser').val();
	var rowData = $('#sizet').datagrid('getSelected');
	if(rowData){
	   if(sizeser == 'add'){
			$('#asizename').val(rowData.SIZENAME);
			$('#asizeid').val(rowData.SIZEID);	
			$('#asizename').focus();
		}else if(sizeser == 'update'){
			$('#usizename').val(rowData.SIZENAME);
			$('#usizeid').val(rowData.SIZEID);
			$('#usizename').focus();
		}else if(sizeser == 'analysis'||sizeser == 'search'){
			$('#asizename,#ssizename').val(rowData.SIZENAME);
			$('#asizeid,#ssizeid').val(rowData.SIZEID);
			$('#asizename').focus();
		}else if(sizeser == 'shopsale'){
			$('#sizename').val(rowData.SIZENAME);
			$('#sizeid').val(rowData.SIZEID);
			$('#sizename').focus();
			if($('#colorid').val()=="")
				selcolor('shopsale');
			$('#amount').focus();
		}else{
			$('#wusizename').val(rowData.SIZENAME);
			$('#wusizeid').val(rowData.SIZEID);
			$('#wusizename').focus();
		}
		//getwaresum(sizeser);
		$('#sized').dialog('close');
	}else{
		alert('请选择')
	}
}
//获取商品id的颜色名
function getwaresizelist(page) {
	$('#sizet').datagrid('loadData', {total:0,rows:[]});
	var findbox = $('#sizefindbox').val();
	var wareid = Number($('#size_wareid').val());
	var erpser = "sizecodelist";
	if(wareid > 0) erpser = "waresizecodelist";
	var sizeser=$('#sizeser').val();
	var ajaxdata = {
        	erpser: erpser,
        	wareid: wareid,
        	findbox: findbox,
			sort: "SIZENO",
			order: "asc",
			noused: 0,
			fieldlist: "*",
			rows: pagecount,
			page: page
        }
	if(sizeser=="shopsale"){
		ajaxdata = {
	        	erpser: "waresizehouselist",
	        	wareid: wareid,
	        	houseid: $("#uhouseid").val(),
	        	colorid: $("#colorid").val(),
	        	wareid: wareid,
	        	findbox: findbox,
				sort: "SIZENO",
				order: "asc",
				noused: 0,
				fieldlist: "*",
				rows: pagecount,
				page: page
	        }
	}
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: ajaxdata,         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	try{
        		if(valideData(data)){
	    			$('#sizepp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
				  $('#sizet').datagrid('loadData', data);
	    			var len = $('#sizet').datagrid('getRows').length;
					if(len>0){
						$('#sizet').datagrid("selectRow",0);
					}
						$('#sizefindbox').focus();
			  }
        	}catch(err){
				console.error(err.message);
			}
        }                 
        });						
}
</script>