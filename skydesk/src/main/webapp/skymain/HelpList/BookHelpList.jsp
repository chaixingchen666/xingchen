<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 账本弹框 -->
<div id="bookd" title="账本帮助列表" style="width: 530px;height: 440px" class="easyui-dialog" closed=true>
  <input type="hidden" id="hpid" name="name"> 
  <input type="hidden" id="bookdser" name="bookdser">
	<div id="booksbtn" class="help-qstoolbar">
		<input type="text" placeholder="搜索账本名称<回车搜索>" class="help-qsipt" id="bookfindbox" data-enter="getbooklist('1')">
	<input class="btn2" type="button" value="搜索" onclick="getbooklist('1')">
	</div>
	<table id="bookt" style="overflow: hidden;"></table>
	<div class="dialog-footer">
		<div id="bookpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="bookfuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#bookd').dialog('close')"/>  -->
			</div>
		</div>
</div>
<script type="text/javascript">
var bookdatat = true;
//获取账本列表
flags = "0";
function selectbook(ser) {
	$('#bookdser').val(ser);
	var findbox = '';
	$('#bookfindbox').val(findbox);
	if (!$('#bookt').data().datagrid) {
		$('#bookpp').createPage({
			pageCount: 1,
			current: 1,
			backFn: function(p) {
				getbooklist(p.toString());
			}
		});
		$('#bookt').datagrid({
			idField: 'BOOKNAME',
			height: 350,
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [[{
				field: 'BOOKID',
				title: '账本ID',
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
				field: 'BOOKNAME',
				title: '账本名称',
				width: 200
			}]],
			onClickRow: function(rowIndex, rowData) {
			},
			onDblClickRow: function(rowIndex, rowData) {
				bookfuzhi();
			},
			toolbar: "#booksbtn"
		}).datagrid("keyCtr", "bookfuzhi()");
		$('#bookd').dialog({
			modal: true
		});
		bookdatat = false;
	}
	getbooklist('1');
	$('#bookd').dialog('open');
	// 		$('#bookt').prev().focus();
	$('#bookfindbox').focus();
}
//双击、确定赋值（账本）
function bookfuzhi() {
	var id = $('#uid').val();
	var ser = $('#bookdser').val();
	var noteno = $('#unoteno').val();
	var rowData = $('#bookt').datagrid('getSelected');
	if (rowData) {
		if (ser == 'tobook') {
			$('#utobookid').val(rowData.BOOKID);
			$('#utobookname').val(rowData.BOOKNAME);
			$('#utobookname').focus();
		}else if (ser == 'update') {
			$('#ubookid').val(rowData.BOOKID);
			$('#ubookname').val(rowData.BOOKNAME);
			$('#ubookname').focus();
		}
		$('#bookd').dialog('close');
	} else {
		alert('请选择')
	}
}
//获取账本数据
function getbooklist(page) {
	var findbox = $('#bookfindbox').val();
	var ser = $('#bookdser').val();
	var bookid = 0;
	if(ser=="tobook")
		bookid = Number($("#udetailbookid").val());
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listaccbook",
			fieldlist: "bookid,bookname,balcurr,firstcurr,bookcolor,statetag,notedate",
			bookid: bookid,
			rows: pagecount,
			page: page,
			findbox: findbox
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					$('#bookpp').setPage({
						pageCount: Math.ceil(Number(data.total) / pagecount),
						current: Number(page)
					});
					$('#bookt').datagrid('loadData', data);
					var len = $('#bookt').datagrid('getRows').length;
					if (len > 0) {
						$('#bookt').datagrid("selectRow", 0);
					}
					$('#bookfindbox').focus();
				}
			} catch(err) {
				console.error(err.message);
			}
		}
	});
}

</script>

