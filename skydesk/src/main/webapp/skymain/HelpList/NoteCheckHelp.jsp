<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="notecheckd" title="扫码验货" style="width: 800px;height:500px;" class="easyui-dialog" closed="true">
<div>
<div id="notecheck_audio-div" style="display:none"></div> 
<table id="notecheckbarcode_table" cellspacing="10">
<tr>
<td>条码：</td>
<td>
<input type="text" id="notecheck_barcode" class="wid160 hig25" placeholder="<输入商品条码>" value="" data-end="yes"/>
</td>
<td>数量：</td>
<td>
<input type="text" id="notecheck_amount" class="hig25" placeholder="<输入商品数量>" value="1" style="width:80px" maxlength="5"/>
</td>
<td>
<input type="button" class="btn1" value="验货完成" onclick="getnotecheckdiff(1)">
</td>
<td>
<input type="button" class="btn2" value="验货清空" onclick="notecheckclear()">
</td>
<td>
<label><input type="checkbox" id="notecheck_sortid" checked>录入排序</label>
</td>
</tr>
</table>
<table id="notecheckt"></table>
<div class="page-bar">
	<div class="page-total">共有<span id="notecheck_total">0</span>条记录</div>
	<div id="notecheck_pp" class="tcdPageCode page-code">
	</div>
</div>
</div>
</div>
<div id="notecheckcyd" title="验货结果" style="width: 600px;height:400px;" class="easyui-dialog" closed="true">
<table id="notecheckcy_table" cellspacing="10">
<tr>
<td>
<input type="button" class="btn1" value="继续完成" onclick="notecheckfinish(1)">
</td>
</tr>
</table>
<table id="notecheckcyt"></table>
<div class="page-bar">
	<div class="page-total">共有<span id="notecheckcy_total">0</span>条记录</div>
	<div id="notecheckcy_pp" class="tcdPageCode page-code">
	</div>
</div>
</div>
<script type="text/javascript">
//tablename:wareinm= 采购/退货，wareoutm=零售/批发/退货，allotoutm=调出
var notechecktablename={
	pg1102:"wareinm",
	pg1302:"wareoutm",
	pg1403:"allotinm",
	pg1402:"allotoutm"
}
function setnotecheckcols() {
	var colums = [];
	colums = [{
		field: 'ROWNUMBER',
		title: '序号',
		fixed: true,
		width: 30,
		halign: 'center',
		align: 'center',
		formatter: function(value, row, index) {
			var p = $("#notecheck_pp").getPage() - 1;
			return p * pagecount + index + 1;
		}
	},{
		field: 'WARENO',
		title: '货号',
		width: 120,
		fixed: true,
		halign: 'center',
		styler: function(value, row, index) {
			if (value == "合计") {
				return 'text-align:center';
			} else {
				return 'text-align:left';
			}
		}
	},{
		field: 'WARENAME',
		title: '商品名称',
		width: 100,
		fixed: true,
		halign: 'center',
		align: 'left'
	},{
		field: 'COLORNAME',
		title: '颜色',
		width: 60,
		fixed: true,
		halign: 'center',
		align: 'center'
	}];
	for (var i = 1; i <= cols; i++) {
		colums.push({
			field: 'AMOUNT' + i,
			title: '<span id="title' + i + '"><span>',
			width: 35,
			fixed: true,
			halign: 'center',
			align: 'center',
			formatter: function(value, row, index) {
				return value == '' ? '' : Number(value) == 0 ? '' : value;
			}
		});
	}
	colums.push({
		field: 'AMOUNT',
		title: '小计',
		width: 50,
		fixed: true,
		halign: 'center',
		align: 'center',
		formatter: amtfm
	});
	return colums;
}

function setnotecheck(){
	$("#notecheck_pp").createPage({
		pageCount :1,
		current :1,
		backFn : function(p){
			getnotechecklist(p);
		}
	});
	$("#notecheckt").datagrid({
		idField: 'ID',
		height: 400,
		fitColumns: true,
		striped: true,
		//隔行变色
		singleSelect: true,
		toolbar: "#notecheckbarcode_table",
		columns: [setnotecheckcols()],
		onLoadSuccess: function(data){
			var sizelength = 0;
			var rows = data.rows;
			var $dg = $(this);
			for(var i=0;i<rows.length;i++){
				var item= rows[i]; 
				while(sizelength<=sizenum){
					var s = sizelength+1;
					if(item["SIZENAME"+s]!=""&&item["SIZENAME"+s]!=undefined)
						sizelength++;
					else
						break;
				}
			}
			for(var i=1;i<=sizenum;i++){
				if(i<=sizelength)
					$dg.datagrid("showColumn","AMOUNT"+i);
				else
					$dg.datagrid("hideColumn","AMOUNT"+i);
			}
			$dg.datagrid('resize');
			},
		onSelect: function(index,row){
			if(row){
				for(var i=1;i<=cols;i++){
					$('#notecheckd #title'+i).html(row["SIZENAME"+i]);
				}
			}
		}
	});
}
$('#notecheck_barcode').keyup(function(e){
	var key = e.which;
	if(this.value!="")
		this.value= this.value.toUpperCase();
	if(key==13){
		var barcode = this.value;
		if(barcode!=""){
			this.value="";
			setTimeout(function(){
				notecheckbarcode(barcode);
			}, 30);
		}
	}
});
$('#notecheck_amount ').keyup(function(e){
	var key = e.which;
	if(key==13){
		var $brcode = $('#notecheckbarcode_table #notecheck_barcode');
		var $amt = $('#notecheckbarcode_table #notecheck_amount');
		$amt.removeClass('focused');
		$brcode.addClass('focused').val('').focus();
	}
});
$('#notecheckbarcode_table').keydown(function(e){
	var key = e.which;
	if(key===9){
		e.preventDefault();
		var $brcode = $('#notecheckbarcode_table #notecheck_barcode');
		var $amt = $('#notecheckbarcode_table #notecheck_amount');
		if($brcode.hasClass('focused')){
			$brcode.removeClass('focused');
			$amt.addClass('focused').select().focus();
		}else{
			$amt.removeClass('focused');
			$brcode.addClass('focused').select().focus();
		}
		return false;
	}else if(key==107){
		e.preventDefault();
		var $amt = $('#notecheckbarcode_table #notecheck_amount');
		var amt = Number($amt.val());
		$amt.val((amt+1)==0?1:(amt+1));
		return false;
	}else if(key==109){
		e.preventDefault();
		var $amt = $('#notecheckbarcode_table #notecheck_amount');
		var amt = Number($amt.val());
		$amt.val((amt-1)==0?-1:(amt-1));
		return false;
	}else if(key==114){
		e.preventDefault();
		var $lxamt = $('#lxamt');
		if($lxamt.prop('checked'))
			$lxamt.removeProp('checked');	
		else
			$lxamt.prop('checked',true);	
		return false;
	}
});
function opennotecheckd(){
	if(!$("#notecheckt").data().datagrid){
		setnotecheck();
	}
	$("#notecheckd").dialog("open");
	getnotechecklist(1);
	$("#notecheck_amount").val("1");
	$("#notecheck_barcode").val("").focus();
}
//验货添加
function notecheckbarcode(barcode){
	barcode = barcode.replace(/ /g,"");
	var amount = Number($("#notecheck_amount").val());
	var noteno = $("#unoteno").val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "notecheckbarcode",
			noteno: noteno,
			tablename: notechecktablename[pgid],
			amount: amount,
			barcode: barcode.toUpperCase()
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					getnotechecklist(1);
					$('#notecheck_audio-div').html('<audio autoplay="autoplay" id="addaudio">'
							+'<source src="/skydesk/audio/wav/ok.mp3"' + 'type="audio/mpeg"/>'
							+'<source src="/skydesk/audio/wav/ok.wav"' + 'type="audio/wav"/>'
							+'</audio>'); 
				}else{
					$('#notecheck_audio-div').html('<audio autoplay="autoplay" id="addaudio">'
							+'<source src="/skydesk/audio/wav/error.mp3"' + 'type="audio/mpeg"/>'
							+'<source src="/skydesk/audio/wav/error.wav"' + 'type="audio/wav"/>'
							+'</audio>'); 
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}

//验货清空
function notecheckclear(){
	$.messager.confirm(dialog_title,"是否清空本次验货，重新开始验货！",function(r){    
		if (r){
			var noteno = $("#unoteno").val();
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "notecheckclear",
					noteno: noteno,
					tablename: notechecktablename[pgid]
				},
				//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)) {
							var $dg = $("#notecheckt");
							$dg.datagrid("loadData",nulldata);
						}
					} catch(e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
				}
			});
		}
	});
}
function getnotechecklist(page){
	var noteno = $("#unoteno").val();
	var sortid = $("#notecheck_sortid").prop("checked")?1:0;
	var $dg = $("#notecheckt");
	$dg.datagrid("loadData",nulldata);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "notechecklist",
			noteno: noteno,
			tablename: notechecktablename[pgid],
			page: page,
			rows: pagecount,
			sortid: sortid,
			order: "asc",
			sizenum: sizenum
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var total = data.total;
					data.footer = [{
						AMOUNT: data.totalamt
					}];
					$("#notecheck_total").html(total);
					$("#notecheck_pp").setPage({
						 pageCount:Math.ceil(total/pagecount),
					     current:Number(page)
					});
					$dg.datagrid("loadData",data);
					if($dg.datagrid("getRows").length>0){
						$dg.datagrid("scrollTo",0);
						$dg.datagrid("selectRow",0);
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
//扫码验货校验差异
function getnotecheckdiff(page){
	var $dg = $("#notecheckcyt");
	if(!$dg.data().datagrid) setnotecheckyt();
	var noteno = $("#unoteno").val();
	alertmsg(6);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "notecheckdiff",
			page: page,
			rows: pagecount,
			noteno: noteno,
			tablename: notechecktablename[pgid],
			xs: 1
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try {
				if (valideData(data)) {
					var total = data.total;
					if(total==0){
						notecheckfinish(0);
						return;
					}else{
						if(pgid=="pg1403"){
							$("#notecheckcy_table").hide();
						}
						alerttext("验货存在差异，请与调出店铺"+$("#ufromhousename").val()+"进行联系");
						$("#notecheckcyd").dialog("open");
					}
					data.footer = [{
						AMOUNT: data.totalamt,
						FACTAMT: data.totalfactamt,
						CYAMT: data.totalcyamt						
					}];
					$("#notecheckcy_total").html(total);
					$("#notecheckcy_pp").setPage({
						 pageCount:Math.ceil(total/pagecount),
					     current:Number(page)
					});
					$dg.datagrid("loadData",data);
					if($dg.datagrid("getRows").length>0){
						$dg.datagrid("scrollTo",0);
						$dg.datagrid("selectRow",0);
					}
				}
			} catch(e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}

function setnotecheckyt(){
	$("#notecheckcy_pp").createPage({
		pageCount :1,
		current :1,
		backFn : function(p){
			getnotecheckdiff(p);
		}
	});
	$("#notecheckcyt").datagrid({
		idField: 'ID',
		height: 300,
		fitColumns: true,
		striped: true,
		toolbar: "#notecheckcy_table",
		//隔行变色
		singleSelect: true,
		columns: [[
			{
				field: 'ROWNUMBER',
				title: '序号',
				fixed: true,
				width: 30,
				halign: 'center',
				align: 'center',
				formatter: function(value, row, index) {
					var p = $("#notecheckcy_pp").getPage() - 1;
					return p * pagecount + index + 1;
				}
			},{
				field: 'WARENO',
				title: '货号',
				width: 120,
				fixed: true,
				halign: 'center',
				styler: function(value, row, index) {
					if (value == "合计") {
						return 'text-align:center';
					} else {
						return 'text-align:left';
					}
				}
			},{
				field: 'WARENAME',
				title: '商品名称',
				width: 100,
				fixed: true,
				halign: 'center',
				align: 'left'
			},{
				field: 'COLORNAME',
				title: '颜色',
				width: 60,
				fixed: true,
				halign: 'center',
				align: 'center'
			},{
				field: 'SIZENAME',
				title: '尺码',
				width: 60,
				fixed: true,
				halign: 'center',
				align: 'center'
			},{
				field: 'AMOUNT',
				title: '数量',
				width: 60,
				fixed: true,
				halign: 'center',
				align: 'center'
			},{
				field: 'FACTAMT',
				title: '已验数量',
				width: 60,
				fixed: true,
				halign: 'center',
				align: 'center'
			},{
				field: 'CYAMT',
				title: '差异数量',
				width: 60,
				fixed: true,
				halign: 'center',
				align: 'center'
			}
		]]
	});
}
//验货完成
function notecheckfinish(cy){
	var msg = "本次验货不存在差异，是否确定完成验货！";
	if(cy==1){
		if($("#warem-toolbar").is(":hidden")){
			msg = "本次验货结果与原有明细不符，请联系制单人修改！";
			return;
		}
		else
			msg = "本次验货结果将替换原有明细，是否确认继续完成验货!";
	}
	var noteno = $("#unoteno").val();
	$.messager.confirm(dialog_title,msg,function(r){    
		if (r){
			$.ajax({
				type: "POST",
				//访问WebService使用Post方式请求 
				url: "/skydesk/fyjerp",
				//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
				data: {
					erpser: "notecheckover",
					noteno: noteno,
					tablename: notechecktablename[pgid],
					bj: 1
				},//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
				dataType: 'json',
				success: function(data) { //回调函数，result，返回值 
					try {
						if (valideData(data)){
							$("#notecheckd,#notecheckcyd").dialog("close");
							if(cy==1)
								getnotewarem(noteno,1);
						}
					} catch(e) {
						// TODO: handle exception
						console.error(e);top.wrtiejsErrorLog(e);
					}
				}
			});
		}
	});
}
</script>
</body>
</html>