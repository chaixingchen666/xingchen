<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div id="tjhoused" title="调价店铺列表" style="width: 530px; height: 480px;" class="easyui-dialog" closed=true>
		<!-- 搜索栏 -->
		<div id="tjhousesbtn" class="help-qstoolbar">
			<input type="hidden" id="tjhouseser">
			<input type="text" placeholder="搜索店铺列表<回车搜索>" class="help-qsipt" id="tjhousefindbox"   data-end="yes"
			data-enter="gettjhouselist(1)">
			<input class="btn2" type="button" value="搜索" onclick="gettjhouselist(1)">
		</div>
	<table id="tjhouset" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="tjhousepp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="getwaretjhousename()" />
			</div>
	</div>
</div>
<script type="text/javascript">
var checkhouse = ''; //存储选中的 店铺和value
var tjhousejson = [];
var fypage; //记录店铺总条数
var selectAll = "0";
function getwaretjhousename() {
	var id = $('uid').val();
	var noteno = $('#unoteno').val()
	$('#tjhoused').dialog('close');
	if (tjhousejson.length > 0) {
		alertmsg(6);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: "writewareadjustc",
				noteno: noteno,
				id: id,
				houselist: JSON.stringify(tjhousejson)
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值  
				try {
					if (valideData(data)) {
						var tjhousename = data.msg;
						$('#checkhouse').val(tjhousename);
						$('#gg').datagrid('updateRow', {
							index: dqindex,
							row: {
								HOUSENAMELIST: tjhousename
							}
						});
						$('#uhouseid').val(tjhousejson[0].houseid);
						wareadjustmlist(noteno, '1');
					} else {
						$('#checkhouse').val("");
					}
				} catch(err) {
					console.error(err.message);
				}
			}
		});
	} else {
		alerttext('店铺选择未发生变化！');
	}

}
function selectwareadjusthouse(allbj) {
	alertmsg(6);
	$('#tjhoused').dialog('close');
	var noteno = $('#unoteno').val()
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "selectwareadjusthouse",
			noteno: noteno,
			allbj: allbj
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值  
			try {
				if (valideData(data)) {
					if (allbj == 0) {
						$('#checkhouse').val("");
					} else {
						$('#checkhouse').val("所有店铺");
					}
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
function selecttjhouse() {
	tjhousejson = [];
	var check = false;
	var tjhouse = "";
	var houseid = $('#uhouseid').val();
	var noteno = $('#unoteno').val();
	if(!$('#tjhouset').data().datagrid){
		$('#tjhousepp').createPage({
	        current:1,
	        backFn:function(p){
	        	gettjhouselist(p);
	        }
		 });
	$('#tjhouset').datagrid({
		height: 385,
		idField: 'HOUSEID',
		fitColumns: true,
		striped: true,
		//隔行变色
		pageSize: 20,
		singleSelect: true,
		checkOnSelect: false,
		selectOnCheck: false,
		columns: [[{
			field: 'SELECTSALE',
			checkbox: true
		},{
			field: 'ROWNUMBER',
			title: '序号',
			width: 50,
			fixed: true,
			align: 'center',
			halign: 'center',
			formatter: function(value, row, index) {
				if (value != '' && value != null) {
					return Number(value);
				}
			}
		},
		{
			field: 'HOUSENAME',
			title: '店铺',
			width: 150,
			fixed: true,
			align: 'center',
			halign: 'center'
		},
		{
			field: 'SELBJ',
			title: '选择',
			width: 200,
			hidden: true
		}]],
		onLoadSuccess: function(data) {
			check = false;
			if (data) {
				$.each(data.rows,
				function(index, item) {
					if (Number(item.SELBJ) == '1') {
						$('#tjhouset').datagrid('checkRow', index);
					} else $('#tjhouset').datagrid('uncheckRow', index);
				});
			}
			check = true;
		},
		onCheck: function(rowIndex, rowData) {
			if (check) {
				writetjhouse(rowData.HOUSEID, '1');
			}
		},
		onUncheck: function(rowIndex, rowData) {
			if (check) {
				writetjhouse(rowData.HOUSEID, '0');
			}
		},
		onCheckAll: function(rows) { //要改
			if (check) {
				selectAll = "1";
				selectwareadjusthouse(1);
			}
		},
		onUncheckAll: function(rows) { //全不选 
			if (check) {
				selectAll = "0";
				selectwareadjusthouse(0);
			}
		},
		toolbar: "#tjhousesbtn"
	});
	}
	gettjhouselist('1');
	$('#tjhoused').dialog('open');
}
function gettjhouselist(page) {
	var noteno = $('#unoteno').val()
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "wareadjustclist",
			rows: pagecount,
			page: page,
			noteno: noteno
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					fypage = data.total;
					$('#tjhousepp').setPage({
				        pageCount:Math.ceil(Number(data.total)/ pagecount),
				        current:Number(page)
					 });
					$('#tjhouset').datagrid('loadData', data);
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}
function writetjhouse(houseid, value) {
	for (var i in tjhousejson) {
		if (Number(tjhousejson[i].houseid) == Number(houseid)) {
			tjhousejson[i].value = value;
			return;
		}
	}
	var newjson = {
		houseid: houseid,
		value: value
	}
	tjhousejson.push(newjson);
}
</script>