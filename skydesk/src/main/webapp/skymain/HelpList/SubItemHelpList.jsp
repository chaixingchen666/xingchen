<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 记账项目弹框 -->
<div id="subd" title="记账项目帮助列表" style="width: 530px;height: 440px" class="easyui-dialog" closed=true>
  <input type="hidden" id="hpid" name="name"> 
  <input type="hidden" id="subdser" name="subdser">
	<div id="subsbtn" class="help-qstoolbar">
		<input type="text" placeholder="搜索记账项目名称<回车搜索>" class="help-qsipt" id="subfindbox" data-enter="getsublist('1')">
	<input class="btn2" type="button" value="搜索" onclick="getsublist('1')">
	</div>
	<table id="subt" style="overflow: hidden;"></table>
	<div class="dialog-footer">
		<div id="subpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="subfuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#subd').dialog('close')"/>  -->
			</div>
		</div>
</div>
<script type="text/javascript">
var subdatat = true;
//获取记账项目列表
var hsublx = 1;
function selectsub(ser,lx) {
	hsublx = lx;
	$('#subdser').val(ser);
	var findbox = '';
	$('#subfindbox').val(findbox);
	if (!$('#subt').data().datagrid) {
		$('#subpp').createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getsublist(p.toString());
			}
		});
		$('#subt').datagrid({
			idField: 'SUBNAME',
			height: 350,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [[{
				field: 'SUBID',
				title: '记账项目ID',
				width: 200,
				hidden: true
			},
			{
				field: 'ROWNUMBER',
				title: '序号',
				width: 50,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var val = Math.ceil(Number(value) / pagecount) - 1;
					return isNaN(Number(value)) || value == "" ? "": val * pagecount + Number(index) + 1;
				}
			},
			{
				field: 'SUBNAME',
				title: '记账项目名称',
				width: 200
			}]],
			onClickRow: function(rowIndex, rowData) {
			},
			onDblClickRow: function(rowIndex, rowData) {
				subfuzhi();
			},
			toolbar: "#subsbtn"
		}).datagrid("keyCtr", "subfuzhi()");
		subdatat = false;
	}
	getsublist(1);
	$('#subd').dialog("setTitle",lx==1?"选择收入记账项目":"选择支出记账项目").dialog('open');
	$('#subfindbox').focus();
}
//双击、确定赋值（记账项目）
function subfuzhi() {
	var id = $('#uid').val();
	var ser = $('#subdser').val();
	var noteno = $('#unoteno').val();
	var rowData = $('#subt').datagrid('getSelected');
	if (rowData) {
		if (ser == 'tosub') {
			$('#utosubid').val(rowData.SUBID);
			$('#utosubname').val(rowData.SUBNAME);
			$('#utosubname').focus();
		}else if (ser == 'update') {
			$('#usubid').val(rowData.SUBID);
			$('#usubname').val(rowData.SUBNAME);
			$('#usubname').focus();
		}
		$('#subd').dialog('close');
	} else {
		alert('请选择')
	}
}
//获取记账项目数据
function getsublist(page) {
	var findbox = $('#subfindbox').val();
	var ser = $('#subdser').val();
	var subid = 0;
	if(ser=="tosub")
		subid = Number($("#udetailsubid").val());
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listsubitem",
			fieldlist:"subid,subname,noused,imgindex,lx",
			lx: hsublx,
			noused: 0,
			rows: pagecount,
			page: page,
			findbox: findbox
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#subpp').setPage({
						pageCount: Math.ceil(Number(data.total) / pagecount),
						current: Number(page)
					});
					$('#subt').datagrid('loadData', data);
					var len = $('#subt').datagrid('getRows').length;
					if (len > 0) {
						$('#subt').datagrid("selectRow", 0);
					}
					$('#subfindbox').focus();
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

</script>

