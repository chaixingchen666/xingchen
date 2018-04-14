<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到物流公司列表  -->
	<div id="carryf" title="物流公司帮助列表" style="width: 530px; height: 440px"
	class="easyui-dialog" closed=true><input type="hidden" id="carryser" name="carryser">
		<!-- 搜索栏 -->
	<div id="carrysbtn" class="help-qstoolbar">
				<input type="text" placeholder="搜索物流公司<回车搜索>" class="help-qsipt" id="carryhp" data-enter="getcarrydata('1')" data-end="yes"> 
				<input class="btn2" type="button" value="搜索" onclick="getcarrydata('1')">
			</div>
		<table id="carryt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="carrypp" class="tcdPageCode fl"></div>
			<div style="float: right;">
				 <input type="button" class="btn1" value="确定"  onclick="carryfuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#carryf').dialog('close')"/> -->
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//获取物流公司列表
	var carrydatat = true;
	function selectcarry(carryser) {
		$('#carryser').val(carryser);
		var findbox ='';
// 		if (ser == 'add') {
// 			findbox = $('#acarryname').val();
// 		} else if (ser == 'update') {
// 			findbox = $('#ucarryname').val();
// 		} else if (ser == 'sear') {
// 			findbox = $('#hcarryname').val();
// 		}else if (ser == 'vip') {
// 			findbox = $('#icarryname').val();
// 		}else if (ser == 'vipcz') {
// 			findbox = $('#icarrynamecz').val();
// 		}
		$('#carryhp').val(findbox);
		if(carrydatat){
			$('#carryt').datagrid(
					{
						fitColumns : true,
						striped : true, //隔行变色
						height : 350,
						singleSelect : true,
						pageSize : 20,
						columns : [ [
								{
									field : 'ROWNUMBER',
									title : '序号',
									width : 50,
									fixed : true,
									align : 'center',
									halign : 'center',
									formatter : function(value, row, index) {
										var val = Math.ceil(Number(value)/ pagecount)-1;
										return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
									}
								}, {
									field : 'CARRYNAME',
									title : '物流公司',
									width : 200
								}] ],
						onClickRow : function(rowIndex, rowData) {
							
						},
						onDblClickRow : function(rowIndex, rowData) {
							carryfuzhi();
						},
						toolbar:'#carrysbtn'
					}).datagrid("keyCtr","carryfuzhi()");
			$('#carrypp').createPage({
				pageCount :1,
				current : 1,
				backFn:function(p){
					getcarrydata(p.toString());
				}
			});
			carrydatat = false;
		}
		getcarrydata('1');
		$('#carryf').dialog('open');
		$('#carryhp').focus();
	}
	 //双击、确定赋值（物流公司）
    function carryfuzhi(){
    	var ser=$('#carryser').val();
  	  var arrs = $('#carryt').datagrid('getSelected');
  	  if(arrs){
			if (ser == 'add') {
				$('#acarryname').val(arrs.CARRYNAME);
				$('#acarryid').val(arrs.CARRYID);
				$('#acarryno').val(arrs.CARRYNO);
				$('#acarryname').focus();
			} else if (ser == 'update') {
				$('#ucarryname').val(arrs.CARRYNAME);
				$('#ucarryid').val(arrs.CARRYID);
				$('#ucarryno').val(arrs.CARRYNO);
				$('#ucarryname').focus();
			} else if (ser == 'search') {
				$('#scarryname').val(arrs.CARRYNAME);
				$('#scarryid').val(arrs.CARRYID);
				$('#scarryno').val(arrs.CARRYNO);
				$('#scarryname').focus();
			} else if (ser == 'sear') {
				$('#hcarryname').val(arrs.CARRYNAME);
				$('#hcarryid').val(arrs.CARRYID);
				$('#hcarryno').val(arrs.CARRYNO);
				$('#hcarryname').focus();
			}else if (ser == 'vipjf') {
				$('#jfcarryname').val(arrs.CARRYNAME);
				$('#jfcarryid').val(arrs.CARRYID);
				$('#jfcarryno').val(arrs.CARRYNO);
				$('#jfcarryname').focus();
			}else if (ser == 'vipcz') {
				$('#czcarryname').val(arrs.CARRYNAME);
				$('#czcarryid').val(arrs.CARRYID);
				$('#czcarryno').val(arrs.CARRYNO);
				$('#czcarryname').focus();
			}else if (ser == 'fh') {
				$('#carryname').val(arrs.CARRYNAME);
				$('#carryid').val(arrs.CARRYID);
				$('#carryname').focus();
			}
			$('#carryf').dialog('close');
  	  }else{
  		  alerttext('请选择一条数据');
  	  }
    }
  //获取物流公司数据
	function getcarrydata(page) {
	  var findbox = $('#carryhp').val();
	  var sybj=0; 
	  var nov=0;
	  var ser=$('#carryser').val();
	  if(ser=="vipcz"||ser=="vipjf") nov=1;
		alertmsg(6);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fybuyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "carrylinelist",
				noused: 0,
				fiellist: "*",
				rows: pagecount,
				page : page,
				findbox:findbox
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)){
					$('#carrypp').setPage({
						pageCount : Math.ceil(data.total / pagecount),
						current : Number(page)
					});
					$('#carryt').datagrid('loadData', data);
					var len = $('#carryt').datagrid('getRows').length;
					if(len>0){
						$('#carryt').datagrid("selectRow",0);
					}
						$('#carryhp').focus();
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
	</script>