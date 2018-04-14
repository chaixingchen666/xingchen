<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
	<div id="barcoded" title="扫描条码" data-options="modal:true,onClose:function(){eval(gtewaremfn);}"
	style="width: 600px;" class="easyui-dialog" closed="true">
		<div class="textcenter">
			<table class="mt10" style="margin-left: 25px;" cellspacing="5" id="barcodeadd_table">
				<tr>
					<td align="right">
						条码
					</td>
					<td align="left">
						<input class="wid160 hig25 focused" type="text" id="barcode" data-end="yes"
						placeholder="<请输入条码>" style="ime-mode:disabled;height: 35px;width:300px;font-size: 20px;">
					</td>
				</tr>
				<tr>
					<td align="right">
						数量
					</td>
					<td align="left">
						<input class="wid160 hig25" type="text" id="barcodeamt" value="1" data-end="yes"
						placeholder="<输入>">
					</td>
				</tr>
				<tr>
					<td align="right">
						总数量
					</td>
					<td align="left">
						<input class="wid160 hig25" type="text" id="barcodetotalamt" value=""
						readonly>
					</td>
				</tr>
				<tr>
					<td align="right">
					</td>
					<td align="left">
						<span style="color:#ff7900">
							提示：使用Tab键切换条码或数量输入,使用数字键盘+或-增减数量
						</span>
					</td>
				</tr>
				<tr id="kzbarcodeaddgys">
					<td align="right">
					</td>
					<td align="left">
						<label>
							<input type="checkbox" id="barcodeaddkzgys" checked>
							仅输入此供应商商品
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
					</td>
					<td align="left">
						<label>
							<input type="checkbox" id="lxamt" checked>
							固定数量连续扫码（F3）。勾选：下次扫码数量保持一致；不勾选：下次扫码数量默认为1
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
					</td>
					<td align="left">
						<label>
							<input type="checkbox" id="isvoice" checked>
							启动声音提示
						</label>
					</td>
				</tr>
			</table>
			<ul id="barcodeul" style="height:280px;overflow: auto;text-align: left;font-size: 16px;">
				<li id="barcodeli" class="hide">
				</li>
			</ul>
		</div>
		<div id="audio-div" style="display:none">
		</div>
	</div>
	<script type="text/javascript">
		var amt = 0;
		var gtewaremfn = "getnotewarem($('#unoteno').val(), '1')";
		var barcodeaction = {
			pg1101: "provorder",
			pg1102: "warein",
			pg1103: "warein",
			pg1201: "wareout",
			pg1301: "custorder",
			pg1302: "wareout",
			pg1303: "wareout",
			pg1308: "refundask",
			pg1309: "warepei",
			pg1401: "allotorder",
			pg1402: "allotout",
			pg1501: "tempcheck",
			pg1502: "warecheck",
			pg1508: "firsthouse"
		}
		var action = barcodeaction[pgid] + "barcode";
		$('#barcode').keyup(function(e) {
			var key = e.which;
			if (this.value != "") this.value = this.value.toUpperCase();
			if (key == 13) {
				var barcode = this.value;
				if (barcode != "") {
					this.value = "";
					setTimeout(function() {
						barcodeadd(barcode);
					},
					30);
				}
			}
		});
		$('#barcodeamt').keyup(function(e) {
			var key = e.which;
			if (key == 13) {
				var $brcode = $('#barcodeadd_table #barcode');
				var $amt = $('#barcodeadd_table #barcodeamt');
				$amt.removeClass('focused');
				$brcode.addClass('focused').val('').focus();
			}
		});
		$('#barcodeadd_table').keydown(function(e) {
			var key = e.which;
			if (key === 9) {
				e.preventDefault();
				var $brcode = $('#barcodeadd_table #barcode');
				var $amt = $('#barcodeadd_table #barcodeamt');
				if ($brcode.hasClass('focused')) {
					$brcode.removeClass('focused');
					$amt.addClass('focused').select().focus();
				} else {
					$amt.removeClass('focused');
					$brcode.addClass('focused').select().focus();
				}
				return false;
			} else if (key == 107) {
				e.preventDefault();
				var $amt = $('#barcodeadd_table #barcodeamt');
				var amt = Number($amt.val());
				$amt.val((amt + 1) == 0 ? 1 : (amt + 1));
				return false;
			} else if (key == 109) {
				e.preventDefault();
				var $amt = $('#barcodeadd_table #barcodeamt');
				var amt = Number($amt.val());
				$amt.val((amt - 1) == 0 ? -1 : (amt - 1));
				return false;
			} else if (key == 114) {
				e.preventDefault();
				var $lxamt = $('#lxamt');
				if ($lxamt.prop('checked')) $lxamt.removeProp('checked');
				else $lxamt.prop('checked', true);
				return false;
			}
		});
		function barcodeadd(barcode) {
			alertmsg(2);
			var noteno = $('#unoteno').val();
			var custid = $('#ucustid').val();
			var amount = $('#barcodeamt').val();
			if (noteno != undefined && noteno != "" && amount != undefined && amount != "" && !isNaN(Number(amount))) {
				if (!$('#lxamt').prop('checked')) {
					$('#barcodeamt').val(1);
				}
				var dataparam = {};
				if (pgid == "pg1401" || pgid == "pg1402" || pgid == "pg1501" || pgid == "pg1502" || pgid == "pg1508") {
					dataparam = {
						erpser: action,
						noteno: noteno,
						barcode: barcode,
						amount: amount
					};
				} else if (pgid == "pg1102" || pgid == "pg1103") {
					var provid = $('#uprovid').val();
					var discount = $('#udiscount').val();
					var pricetype = $('#upricetype').val();
					dataparam = {
						erpser: action,
						provid: provid,
						noteno: noteno,
						barcode: barcode,
						amount: amount,
						discount: discount,
						pricetype: pricetype
					};
				} else if (pgid == "pg1201" || pgid == "pg1301" || pgid == "pg1302" || pgid == "pg1303") {
					var ntid = pgid == "pg1201" ? '0': pgid == "pg1302" ? '1': '2';
					var discount = $('#udiscount').val();
					var houseid = $('#uhouseid').val();
					var pricetype = $('#upricetype').val();
					if (pgid == "pg1201") 
						dataparam = {
						erpser: action,
						noteno: noteno,
						houseid: houseid,
						barcode: barcode,
						amount: amount,
						discount: discount,
						ntid: ntid
						}
					else
						dataparam = {
						erpser: action,
						custid: custid,
						noteno: noteno,
						barcode: barcode,
						amount: amount,
						discount: discount,
						pricetype: pricetype,
						ntid: ntid
						}
					;
				} else {
					var discount = $('#udiscount').val();
					var pricetype = $('#upricetype').val();
					dataparam = {
						erpser: action,
						noteno: noteno,
						barcode: barcode,
						amount: amount,
						discount: discount,
						pricetype: pricetype
					};
				}
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: dataparam,
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					//  async : false,
					success: function(data) {
						try {
							if (valideData(data)) {
								amt += Number(amount);
								$('#barcodetotalamt').val(amt);
								$('#barcodeul #barcodeli').after('<li style="margin:5px;list-style-type: decimal;">' + data.msg.replace('条码', ',条码') + ',数量:' + amount + '</li>');
								var amtstr = amt.toString();
								var countdown = null;
								if ($('#isvoice').prop('checked')) {
									var i = 0;
									countdown = setInterval(function(){
										mediumplay(amtstr.charAt(i));
										if(i==amtstr.length-1){
											clearInterval(countdown);
											countdown = null;
										}
										i++;
									}, 500);
								}	
							}
						} catch(err) {
							console.error(err.message);
						}
					}
				});
			}
		}
		function mediumplay(file) {
			$('#audio-div').html('<audio autoplay id="addaudio">'
					+'<source src="/skydesk/audio/number/a' + file + '.mp3" type="audio/mpeg" />'
					+'<source src="/skydesk/audio/number/a' + file + '.mp3" type="audio/ogg" />'
					+'<embed height="100" width="100" src="/skydesk/audio/number/a' + file + '.mp3" />'
					+'</audio>');
		}
		function openbarcodeadd() {
			if (isAddWarem()) {
				$('#barcoded').dialog('open');
				$('#barcodeul li:not(#barcodeli)').remove();
				amt = 0;
				$('#barcodetotalamt').val(amt);
				$('#barcode').val('').focus();
			}
		}
	</script>