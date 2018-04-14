<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 得到店铺列表  -->
	<div id="dx_housef" title="选择店铺" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="dx_housesbtn" class="help-qstoolbar">
		<input type="text" class="help-qsipt" id="dx_housefindbox" data-enter="getdx_housedata('1')"  placeholder="搜索店铺<回车搜索>"  data-end="yes">
		<input class="btn2" type="button" value="搜索" onclick="getdx_housedata('1')">
		<input  type="hidden" id="dx_houseser" >
	</div>
		<table id="dx_houset" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="dx_housepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="dx_houseselects()" />
			</div>
		</div>
	</div>
<script type="text/javascript">
 //加载店铺列表窗
function selectdx_house(dx_houseser){
	$("#dx_houseser").val(dx_houseser);
	var findbox='';
	$('#dx_housefindbox').val(findbox);
	var $dx_dg = $('#dx_houset');
	if(!$dx_dg .data().datagrid){
		$('#dx_housepp').createPage({
	        current:1,
	        backFn:function(p){
	        	getdx_housedata(p.toString());
	        }
		 });
		var empty = {};
		var options = {
				height:350,
				idField : 'HOUSEID',
				fitColumns : true,
				striped : true, //隔行变色
				singleSelect : true,
				selectOnCheck: false,
				checkOnSelect: false,
				pageSize:20,
				columns : [ [ {
					field : 'HOUSEID',
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
					field : 'HOUSENAME',
					title : '店铺',
					width : 200
				} ] ],
				toolbar: '#dx_housesbtn'
			};
		$dx_dg .datagrid(options);
		getdx_housedata('1');//加载数据
	}else if(dx_houseser!="warewarn"){
		var selobj;
		$("#dx_housepp").refreshPage();
	}
	$('#dx_houset').datagrid('clearChecked');
	$('#dx_housef').dialog('open');
	$('#dx_housefindbox').focus();
}
  //双击、确定赋值（店铺）
function dx_houseselects(){
	var checkrows = $('#dx_houset').datagrid('getChecked');
	var dx_houseser = $('#dx_houseser').val();
	var namearr = [];
	var valuearr = [];
	if(checkrows){
		for(var i in checkrows){
			var row = checkrows[i];
			valuearr.push(row.HOUSEID); 
			namearr.push(row.HOUSENAME); 
		}
	}
	var name = namearr.join(",");
	var value = "^"+valuearr.join("^")+"^";
	if(value=="^^") value="";
	var selobject = {
			name: name,
			value: value
	};
	if(dx_houseser=='search'){
		$('#shousename').data("selobject",selobject);
		$('#shousename').val(name);
		$('#shouseid').val(value);
		$('#shousename').focus();
	}else if(dx_houseser=='searchfrom'){
		$('#sfromhousename').data("selobject",selobject);
		$('#sfromhousename').val(name);
		$('#sfromhouseid').val(value);
		$('#sfromhousename').focus();
	}else if(dx_houseser=='searchto'){
		$('#stohousename').data("selobject",selobject);
		$('#stohousename').val(name);
		$('#stohouseid').val(value);
		$('#stohousename').focus();
	}else if(dx_houseser=='searchfk'){
		$('#sfkhousename').data("selobject",selobject);
		$('#sfkhousename').val(name);
		$('#sfkhouseid').val(value);
		$('#sfkhousename').focus();
	}else if(dx_houseser=='warewarn'){
		$('#selwarnhouse').data("selobject",selobject);
		$("ul#ul_house").html("");
		var rows = selobject.name.split(",");
		for(var i in rows){
			var name = rows[i];
			if(name&&name.length>0)
				$("ul#ul_house").append("<li>"+name+"</li>");
		}
	}
	$('#dx_housef').dialog('close');
}
//获取店铺列表
function getdx_housedata(page) { //定义回调函数#')
	var findbox = $('#dx_housefindbox').val();
	findbox = findbox==undefined?"":findbox;
	var flags = "0";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "warehousehelplist",
			sort: "housename",
			order: "asc",
			fieldlist: "houseid,housename,pricetype1",
			noused: 0,
			rows: pagecount,
			page: page,
			findbox: findbox
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			try{if (valideData(data)){
				$('#dx_housepp').setPage({
		       		pageCount:Math.ceil(Number(data.total)/ pagecount),
		        	current:Number(page)
			 	});
				var $dx_dg  = $('#dx_houset');
				$dx_dg .datagrid('loadData', data);
				var len = $dx_dg .datagrid('getRows').length;
				if(len>0){
					$dx_dg .datagrid("selectRow",0);
				}
				$('#dx_housefindbox').focus();
	  		}
		}catch(err){
			console.error(err.message);
		}
		}
	});
	
} 
</script>
