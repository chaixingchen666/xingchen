<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到结算方式列表  -->
	<div id="payf" title="结算方式帮助列表" style="width: 530px; height: 440px"
	class="easyui-dialog" closed=true><input type="hidden" id="payser" name="payser">
		<!-- 搜索栏 -->
	<div id="paywaysbtn" class="help-qstoolbar">
				<input type="text" placeholder="搜索结算方式<回车搜索>" class="help-qsipt" id="payhp" data-enter="getPayWayData('1')" data-end="yes"> 
				<input class="btn2" type="button" value="搜索" onclick="getPayWayData('1')">
			</div>
		<table id="payt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="paypp" class="tcdPageCode fl"></div>
			<div style="float: right;">
				 <input type="button" class="btn1" value="确定"  onclick="paywayfuzhi()"/>
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#payf').dialog('close')"/> -->
			</div>
		</div>
	</div>
	<script type="text/javascript">
	//获取结算方式列表
	var paywaydatat = true;
	function Paywaydata(payser) {
		$('#payser').val(payser);
		var findbox ='';
// 		if (ser == 'add') {
// 			findbox = $('#apayname').val();
// 		} else if (ser == 'update') {
// 			findbox = $('#upayname').val();
// 		} else if (ser == 'sear') {
// 			findbox = $('#hpayname').val();
// 		}else if (ser == 'vip') {
// 			findbox = $('#ipayname').val();
// 		}else if (ser == 'vipcz') {
// 			findbox = $('#ipaynamecz').val();
// 		}
		$('#payhp').val(findbox);
		if(paywaydatat){
			$('#payt').datagrid(
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
									field : 'PAYNAME',
									title : '结算方式',
									width : 200
								}, {
									field : 'PAYNO',
									title : '编号',
									width : 200
								}, {
									field : 'PAYID',
									title : 'PAYID',
									width : 200,
									hidden : true
								} ] ],
						onClickRow : function(rowIndex, rowData) {
							
						},
						onDblClickRow : function(rowIndex, rowData) {
							paywayfuzhi();
						},
						toolbar:'#paywaysbtn'
					}).datagrid("keyCtr","paywayfuzhi()");
			$('#paypp').createPage({
				pageCount :1,
				current : 1,
				backFn:function(p){
					getPayWayData(p.toString());
				}
			});
			paywaydatat = false;
		}
		getPayWayData('1');
		$('#payf').dialog('open');
		$('#payhp').focus();
	}
	 //双击、确定赋值（结算方式）
    function paywayfuzhi(){
    	var ser=$('#payser').val();
  	  var arrs = $('#payt').datagrid('getSelected');
  	  if(arrs){
			if (ser == 'add') {
				$('#apayname').val(arrs.PAYNAME);
				$('#apayid').val(arrs.PAYID);
				$('#apayno').val(arrs.PAYNO);
				$('#apayname').focus();
			} else if (ser == 'update') {
				$('#upayname').val(arrs.PAYNAME);
				$('#upayid').val(arrs.PAYID);
				$('#upayno').val(arrs.PAYNO);
				$('#upayname').focus();
			} else if (ser == 'search') {
				$('#spayname').val(arrs.PAYNAME);
				$('#spayid').val(arrs.PAYID);
				$('#spayno').val(arrs.PAYNO);
				$('#spayname').focus();
			} else if (ser == 'sear') {
				$('#hpayname').val(arrs.PAYNAME);
				$('#hpayid').val(arrs.PAYID);
				$('#hpayno').val(arrs.PAYNO);
				$('#hpayname').focus();
			}else if (ser == 'vipjf') {
				$('#jfpayname').val(arrs.PAYNAME);
				$('#jfpayid').val(arrs.PAYID);
				$('#jfpayno').val(arrs.PAYNO);
				$('#jfpayname').focus();
			}else if (ser == 'vipcz') {
				$('#czpayname').val(arrs.PAYNAME);
				$('#czpayid').val(arrs.PAYID);
				$('#czpayno').val(arrs.PAYNO);
				$('#czpayname').focus();
			}else if (ser == 'paynote') {
				$('#paynotepayname').val(arrs.PAYNAME);
				$('#paynotepayid').val(arrs.PAYID);
				$('#paynotepayname').focus();
			}
			$('#payf').dialog('close');
  	  }else{
  		  alerttext('请选择一条数据');
  	  }
    }
  //获取结算方式数据
	function getPayWayData(page) {
	  var findbox = $('#payhp').val();
	  var sybj=0; 
	  var nov=0;
	  var ser=$('#payser').val();
	  if(ser=="vipcz"||ser=="vipjf"||pgid=="pg1601"||pgid=="pg1607"||pgid=="pg1610" ) nov=1;
		alertmsg(6);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser : "paywaylist",
				sort: "payno",
				order: "asc",
				sybj: sybj,
				nov: nov,
				noused: 0,
				fiellist: "*",
				rows: pagecount,
				page : page,
				findbox:findbox
			}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType : 'json',
			success : function(data) { //回调函数，result，返回值 
				try{if (valideData(data)){
					$('#paypp').setPage({
						pageCount : Math.ceil(data.total / pagecount),
						current : Number(page)
					});
					$('#payt').datagrid('loadData', data);
					var len = $('#payt').datagrid('getRows').length;
					if(len>0){
						$('#payt').datagrid("selectRow",0);
					}
						$('#payhp').focus();
				}
				}catch(err){
					console.error(err.message);
				}
			}
		});
	}
	</script>