<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="notedetaild" title="" data-options="modal:true" style="width: 1100px;" class="easyui-dialog" closed="true" data-qickey>
	<!-- 单据提交框 -->
	<div id="detail_udnote" style="overflow:hidden;zoom:1">
	<form id="detail_udnoteform" action="" method="get">
	<input type="hidden" id="detail_wtotalamt">
	<input type="hidden" id="detail_wtotalcurr">
	<input type="hidden" id="detail_noteno" >
	<div id="detail_notedetailh" class="note-box"></div>
	</form>
	</div>
	<div class="warem-toolbars">
		<div class="fl" style="cursor: pointer;">
		<table border="0" cellspacing="0" class="fl-ml10">
		<tr><td id="detail_warembar" onclick="detail_updown()" style="cursor: pointer;">▲&nbsp;&nbsp;商品明细</td></tr>
		</table>
		</div>
	</div>
	<!-- 商品明细表 -->
	<div style="margin-left: 15px;width:auto" id="detail_waret1">
	<table id="detail_waret" class="table1"></table>
	<div id="detail_warenametable" class="line30 mt10">
	<div class="fl mr20" align="right" id="detail_wmtotalnote"></div>
	<div class="fr mr20" align="right"><span style="font-weight: 600;">制单人：</span><span id="detail_noteoperant"></span></div>
	</div>
	</div>
	<div style="margin-left: 15px;width:auto" id="detail_waret2">
	<table id="detail_warecheckt" class="table1"></table>
	<div class="line30 mt10">
	<div class="fl mr20" align="right" id="detail_wmtotalnote1"></div>
	<div class="fr mr20" align="right"><span style="font-weight: 600;">制单人：</span><span id="detail_noteoperant1"></span></div>
	</div>
	</div>
</div>
<script type="text/javascript">
var detail_sumpg=0;
var detail_pg = 1;
var detail_bol = true;
var notedetailhaction={
		CO:"provorder",//采购订单
		CG:"warein",//采购入库
		CT:"warein",//采购退库
		XX:"waresale",//零售开票
		XC:"shopsale",//零售开票
		XS:"wareout",//批发开票
		PH:"warepei",//批发配货
		XT:"wareout",//批发退库
		KO:"custorder",//客户订单
		TS:"refundask",//退货申请单
		DO:"allotorder",//调拨订单
		DR:"allotin",//调拨入库
		DC:"allotout",//调拨出库
		PY:"warecheck",//临时盘点
		PK:"warecheck",//盘点管理
		QR:"firsthouse"//期初入库
}
var notedetailmaction={
		CO:"provordermcolorsumlist",//采购订单
		CG:"wareinmcolorsumlist",//采购入库
		CT:"wareinmcolorsumlist",//采购退库
		XX:"wareoutmcolorsumlist",//零售开票
		XC:"shopsalemlist",//商场零售
		XS:"wareoutmcolorsumlist",//批发开票
		PH:"warepeimsumlist",//批发配货
		XT:"wareoutmcolorsumlist",//批发退库
		KO:"custordermcolorsumlist",//客户订单
		TS:"refundaskmcolorsumlist",//退货申请单
		DO:"allotordermcolorsumlist",//调拨订单
		DR:"allotinmcolorsumlist",//调拨入库
		DC:"allotoutmcolorsumlist",//调拨出库
		PY:"warecheckmcolorsumlist",//临时盘点
		PK:"warecheckmcolorsumlist",//盘点管理
		QR:"firsthousemcolorsumlist"//期初入库
}
var notedetailtitle={
		CO:"采购订单",//采购订单
		CG:"采购入库单",//采购入库
		CT:"采购退库单",//采购退库
		XX:"零售开票单",//零售开票
		XC:"商场零售单",//商场零售
		XS:"批发开票单",//批发开票
		PH:"批发配货单",//批发配货
		XT:"批发退库单",//批发退库
		KO:"客户订单",//客户订单
		TS:"退货申请单",//退货申请单
		DO:"调拨订单",//调拨订单
		DR:"调拨入库单",//调拨入库
		DC:"调拨出库单",//调拨出库
		PY:"商品盘点管理",//临时盘点
		PK:"商品盘点管理",//盘点管理
		QR:"期初入库单"//期初入库
}
var noteZDM = {
		NOTENO:"单据号",
		NOTEDATE:"单据时间",
		HOUSENAME:"店铺",
		FROMHOUSENAME:"调出店铺",
		TOHOUSENAME:"调入店铺",
		CUSTNAME:"客户",
		PROVNAME:"供应商",
		SALEMANLIST:"销售",
		HANDNO:"自编号",
		REMARK:"摘要",
		GUESTNAME:"会员",
		VIPNO:"会员卡号",
		MOBILE:"会员电话",
		BALCENT:"积分余额",
		BALCURR:"储值余额",
		VTNAME:"会员类型",
		CENT:"本次积分",
		USECENT:"积分抵扣",
		JFCURR:"积分抵值",
		TOTALAMT:"总数量",
		XSFYLIST:"销售费用",
		CGFYLIST:"采购费用",
		TOTALCURR:"总金额",
		PAYLIST: "结算明细",
		OPERANT:"制单人"
}
var noteSort = {
		CO:"NOTENO,NOTEDATE,PROVNAME,HANDNO,OPERANT,REMARK",//采购订单
		CG:"NOTENO,NOTEDATE,HOUSENAME,PROVNAME,HANDNO,CGFYLIST,PAYLIST,OPERANT,REMARK",//采购入库
		CT:"NOTENO,NOTEDATE,HOUSENAME,PROVNAME,HANDNO,CGFYLIST,OPERANT,REMARK",//采购退库
		XX:"NOTENO,NOTEDATE,HOUSENAME,SALEMANLIST,DPTNAME,GUESTNAME,VIPNO,MOBILE,HANDNO,PAYLIST,OPERANT,REMARK",//零售开票
		XC:"NOTENO,NOTEDATE,SALEMANLIST,DPTNAME,GUESTNAME,VIPNO,MOBILE,PAYLIST,OPERANT,REMARK",//商场开票
		XS:"NOTENO,NOTEDATE,HOUSENAME,CUSTNAME,SALEMANLIST,DPTNAME,XSFYLIST,HANDNO,PAYLIST,OPERANT,REMARK",//批发开票
		PH:"NOTENO,NOTEDATE,HOUSENAME,CUSTNAME,SALEMANLIST,DPTNAME,XSFYLIST,HANDNO,OPERANT,REMARK",//批发配货
		XT:"NOTENO,NOTEDATE,HOUSENAME,CUSTNAME,SALEMANLIST,DPTNAME,XSFYLIST,HANDNO,PAYLIST,OPERANT,REMARK",//批发退库
		KO:"NOTENO,NOTEDATE,CUSTNAME,HANDNO,OPERANT,REMARK",//客户订单
		TS:"NOTENO,NOTEDATE,HOUSENAME,CUSTNAME,HANDNO,OPERANT,REMARK",//退货申请单
		DO:"NOTENO,NOTEDATE,HOUSENAME,HANDNO,OPERANT,REMARK",//调拨订单
		DR:"NOTENO,NOTEDATE,FROMHOUSENAME,HOUSENAME,HANDNO,OPERANT,REMARK",//调拨入库
		DC:"NOTENO,NOTEDATE,HOUSENAME,TOHOUSENAME,HANDNO,OPERANT,REMARK",//调拨出库
		PY:"NOTENO,NOTEDATE,HOUSENAME,HANDNO,OPERANT,REMARK",//临时盘点
		PK:"NOTENO,NOTEDATE,HOUSENAME,HANDNO,OPERANT,REMARK",//盘点管理
		QR:"NOTENO,NOTEDATE,HOUSENAME,HANDNO,OPERANT,REMARK"//期初入库
}
var notefieldlist={
		CO:"a.noteno,a.notedate,a.id,a.accid,a.provid,b.provname,b.discount,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//采购订单
		CG:"a.noteno,a.notedate,a.id,a.accid,a.provid,a.houseid,b.provname,b.discount,c.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag,a.paylist",//采购入库
		CT:"a.noteno,a.notedate,a.id,a.accid,a.provid,a.houseid,b.provname,b.discount,c.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//采购退库
		XX:"a.id,a.accid,a.noteno,a.notedate,a.guestid,a.houseid,a.ntid,a.handno,a.operant,a.statetag,"
			+ "b.guestname,b.vipno,a.checkcurr,a.freecurr,a.changecurr,a.zpaycurr,a.remark,b.mobile,b.balcent,b.balcurr,"
			+ "c.housename,d.discount,d.vtid,d.vtname,c.address,c.tel,a.cent,a.usecent,a.jfcurr,a.dptid,e.dptname,a.paylist",//零售开票
		XC:"a.id,a.accid,a.noteno,a.notedate,a.houseid,c.housename,a.totalcurr,a.totalamt,a.operant,a.statetag,a.remark,a.paylist,a.guestid," 
			+ "b.guestname,a.jfcurr,a.yhjcurr,a.freecurr,b.balcent,b.balcurr,b.mobile,b.vipno,d.vtname,d.discount,a.checkcurr,a.cent,a.changecurr,a.zpaycurr",//商场开票
		XS:"a.noteno,a.notedate,a.id,a.accid,a.custid,a.houseid,b.custname,b.discount,c.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag,a.paylist",//批发开票
		PH:"a.noteno,a.notedate,a.id,a.accid,a.custid,a.houseid,b.custname,b.discount,c.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//批发配货
		XT:"a.noteno,a.notedate,a.id,a.accid,a.custid,a.houseid,b.custname,b.discount,c.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag,a.paylist",//批发退库
		KO:"a.noteno,a.notedate,a.id,a.accid,a.custid,b.custname,b.discount,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//客户订单
		TS:"a.noteno,a.notedate,a.id,a.accid,a.custid,a.houseid,b.custname,b.discount,c.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//退货申请单
		DO:"a.noteno,a.notedate,a.id,a.accid,b.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//调拨订单
		DR:"a.noteno,a.notedate,a.id,a.accid,b.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//调拨入库
		DC:"a.id,a.noteno,a.accid,a.notedate,a.houseid,a.tohouseid,a.ntid,a.remark,a.handno,a.operant,a.checkman,a.statetag,a.lastdate,b.housename",//调拨出库
		PY:"a.noteno,a.notedate,a.id,a.accid,b.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//临时盘点
		PK:"a.noteno,a.notedate,a.id,a.accid,b.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag",//盘点管理
		QR:"a.noteno,a.notedate,a.id,a.accid,b.housename,a.handno,a.remark,a.totalcurr,a.totalamt,a.operant,a.statetag"//期初入库
}
var haction,maction,pdaction,fieldlist,notezd;
var lyaccid = "";
function opendetaild(pgno,noteno,accid){
	if(notedetailhaction[pgno]!=undefined){
		if(pgno == 'PY' || pgno =='PK'){
			setDetailWarem1(noteno);
			if(pgno=='PK')
				skyykbj = 1;
			else
				skyykbj = 0;
		}else
			setnotewarem(pgno,noteno);
		lyaccid = accid==undefined?"":accid;
		$('#detail_noteno').val(noteno);
		haction = 'get' + notedetailhaction[pgno] +"hbyid";
		maction = notedetailmaction[pgno];
		pdaction = 'get'+ notedetailhaction[pgno] + "pay";
		notezd = noteSort[pgno].split(',');
		fieldlist = notefieldlist[pgno];
		getnotehdetail(pgno,noteno,notezd,fieldlist);
		$('#notedetaild').dialog({
			title: notedetailtitle[pgno]
		});
	// 	if(pgno=='CG'||pgno=='XX'||pgno=='XS'||pgno=='XT')
	// 		$('#udtool').show();	
		
	}
}
function getnotehdetail(pgno,noteno,notezd,fieldlist){
	alertmsg(6);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : haction,
			noteno : noteno,
			accid: Number(lyaccid),
			fieldlist : fieldlist
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if(valideData(data)){
				if(data.total==0){
					alerttext('没有明细数据,该数据无效！');
					return;
				}else{
					$('#notedetaild').dialog('open');
					var rows = data.rows;
					var row = rows[0];
					var html='';
					var provid = Number(getValuebyName('PROVID'));
					if(pgno=="DC") noteZDM["HOUSENAME"] = "调出店铺";
					for(var zd in notezd){
						var r = notezd[zd];
						if(noteZDM[r]!=undefined){
							if(provid==-1&&noteZDM[r]=="结算明细"&&Number(allowinsale)==0){
							}else if(r=="REMARK"){
								html += '<div style="width:100%;clear:both;">'
									+'<div class="title">'+ noteZDM[r]
									+'</div>'
									+'<div class="fl">'+(row[r]==undefined?"":row[r])+'</div>'
									+'</div>';
							}else if(r=="OPERANT"){
								$('#detail_noteoperant,#detail_noteoperant1').html(row[r]);
							}else{
								html += '<div class="s-box">'
									+'<div class="title">'+ noteZDM[r]
									+'</div>'
									+'<div class="text">'+(row[r]==undefined?"":row[r])+'</div>'
									+'</div>';
							}	
						}
					}
					$('#detail_notedetailh').html(html);
					if(pgno == 'PY' || pgno =='PK'){
						getnotewarem1(noteno, '1');
						$('#detail_waret1').hide();
						$('#detail_waret2').show();
						$('#detail_warecheckt').datagrid('resize');
					}else{
						$('#detail_waret2').hide();
						$('#detail_waret1').show();
						getnotewaremdetail('1',noteno);
						$('#detail_waret').datagrid('resize');
					}
					
				}
			}
		}
	});
}
var skyykbj = 0;
function getnotewaremdetail(page,noteno){
	alertmsg(6);
	var fieldlist = noteno.indexOf("XC")>-1?"A.ID,A.NOTENO,A.ACCID,A.WAREID,A.COLORID,A.SIZEID,A.HOUSEID,A.AMOUNT,A.PRICE0,A.DISCOUNT,A.PRICE,A.CURR,a.iszp,"
	+ "A.REMARK0,B.WARENO,A.SALEMANID,B.WARENAME,"
	+ "B.UNITS,C.COLORNAME,D.SIZENAME,E.HOUSENAME,F.EPNAME":"*";
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data : {
			erpser : maction,
			ykbj: skyykbj,
			sizenum : cols,
			noteno : noteno,
			accid: Number(lyaccid),
			fieldlist : fieldlist,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				var totals = data.total;
				var totalamt =data.totalamt;
				var totalcurr = data.totalcurr;
				var footer = [{ROWNUMBER:"",WARENO:"合计",AMOUNT:totalamt,CURR:totalcurr}]; 
				detail_sumpg=Math.ceil(Number(totals) / pagecount);
				$('#detail_wtotalamt').val(totalamt);
				$('#detail_wtotalcurr').val(Number(totalcurr).toFixed(2));
				if(page=='1'){
					detail_pg=1;
					$('#detail_waret').datagrid('loadData', data);
					if($('#detail_waret').datagrid('getRows').length>0){
						$('#detail_waret').datagrid('selectRow',0);
					}
				}else{
					detail_pg++;
					var rows = data.rows;
					for(var i in rows){
						$('#detail_waret').datagrid('appendRow',rows[i]);
					}
				}
				$('#detail_waret').datagrid('loaded');
				$('#detail_waret').datagrid('reloadFooter',footer);
				$('#detail_wmtotalnote').html('已显示'+$('#detail_waret').datagrid('getRows').length+'条记录/共有'+totals+'条记录');
			}
		}
	});
}

var sizenum = getValuebyName("SIZENUM");
var cols = Number(sizenum)<=5?5: Number(sizenum);
cols = cols >= 15 ? 15 : cols;
function setDetailColums(pgno) {
	var colums = [];
	if(pgno=="XC"){
		colums =  [
			{
				field : 'WARENO',title : '货号',width : 2,align:'center',halign:'center'
			},{
				field : 'WARENAME',title : '品名',width : 2,align:'center',halign:'center'
			},{
				field : 'COLORNAME',title : '颜色',width : 1,align:'center',halign:'center'
			}, {
				field : 'SIZENAME',title : '尺码',width : 1,align:'center',halign:'center'
			}, {
				field : 'AMOUNT',title : '数量',width : 35,fixed:true,align:'center',halign:'center',
				formatter : function(value,row,index){
					return Number(value);
				}
			}, {
				field : 'PRICE0',title : '零售价',width :70,fixed:true,align:'right',halign:'center',
				formatter : function(value,row,index){
					return value==undefined?'':Number(value).toFixed(2);
				}
			}, {
				field : 'DISCOUNT',title : '折扣',width :30,fixed:true,align:'center',halign:'center',
				formatter : function(value,row,index){
					return value==undefined?'':Number(value)==0?"-":Number(value).toFixed(2);
				}
			}, {
				field : 'PRICE',title : '单价',width :70,fixed:true,align:'right',halign:'center',
				formatter : function(value,row,index){
					return value==undefined?'':Number(value).toFixed(2);
				}
			}, {
				field : 'CURR',title : '金额',width :70,fixed:true,align:'right',halign:'center',
				formatter : function(value,row,index){
					return Number(value).toFixed(2);
				}
			}, {
				field : 'CHECKZP',title : '赠品',width :35,fixed:true,align:'center',halign:'center',
				formatter : function(value,row,index){
					var stg = "1";
					var htm = "";
					var iszp = row.ISZP;
					if(stg=="0"&&iszp=="0")
						return value=="noaction"?"":'<input type="checkbox" class="che-iszp" data-index="'+index+'" data-mid="'+row.ID+'">';
					else if(stg=="0"&&iszp=="1")
						return value=="noaction"?"":'<input type="checkbox" class="che-iszp" data-index="'+index+'" data-mid="'+row.ID+'" checked>';
					else if(stg=="1"&&iszp=="0")
						return value=="noaction"?"":'<input type="checkbox" disabled>';
					else if(stg=="1"&&iszp=="1")
						return value=="noaction"?"":'<input type="checkbox" checked disabled>';
				}
			}, {
				field : 'HOUSENAME',title : '店铺',width : 4,align:'center',halign:'center'
			}, {
				field : 'EPNAME',title : '销售人',width :70,fixed:true,align:'center',halign:'center'
			}, {
				field : 'REMARK',title : '备注',width : 3,align:'center',halign:'center',hidden:true
			}, {field : 'HOUSEID',title : 'houseid',width : 3,hidden:true}, 
			{field : 'SALEMANID',title : 'salemanid',width : 3,hidden:true},
			{field : 'ISZP',title : 'iszp',width : 3,hidden:true},
			{field : 'ID',title : 'id',width : 3,hidden:true}
			] ;
	}else{
		colums[0] = {
				field : 'ROWNUMBER',
				title : '序号',
				fixed : true,
				width : 30,
				halign : 'center',
				align :'center',
				formatter : function(value,row,index){
					return value==""?"":index+1;
				}
			};
		colums[1] = {
			field : 'WAREID',
			title : 'WAREID',
			hidden : true,
			width : 10
		};
		colums[2] = {
			field : 'WARENO',
			title : '货号',
			width : 6,
			halign : 'center',
			styler: function(value,row,index){
				if(value=="合计"){
					return 'text-align:center';
				}else{
					return 'text-align:left';
				}
			}
		};
		colums[3] = {
				field : 'WARENAME',
				title : '商品名称',
				width : 12,
				halign : 'center',
				align :'left'
			};
		colums[4] = {
			field : 'UNITS',
			title : '单位',
			width : 40,
			fixed : true,
			halign : 'center',
			align :'center'
		};
		colums[5] = {
				field : 'COLORID',
				title : '颜色id',
				width : 10,
				hidden : true
			};
		colums[6] = {
			field : 'COLORNAME',
			title : '颜色',
			width : 6,
			halign : 'center',
			align :'center'
		};
		for (var i = 1; i <= cols; i++) {
			colums[6 + i] = {
				field : 'AMOUNT' + i,
				title : '<span id="detail_title'+i+'"><span>',
				width : 35,
				fixed:true,
				halign : 'center',
				align :'center',
				formatter : function(value,row,index){
					return value==''?'':Number(value)==0?'':value;
				}
			};
		}
		colums[cols + 7] = {
			field : 'AMOUNT',
			title : '小计',
			width : 50,
			fixed:true,
			halign : 'center',
			align :'center'
		};
		if(pgno=="CO"||pgno=="CG"||pgno=="CT"){
			colums[cols + 8] = {
				field : 'PRICE0',
				title : '原价',
				width : 7,
				halign : 'center',
				align :'right',
				formatter : function(value,row,index){
					if(allowinsale=='1'){
						return value==undefined?'':Number(value).toFixed(2);
					}else{
						return "***";
					}
				}
			};
			colums[cols + 9] = {
					field : 'DISCOUNT',
					title : '折扣',
					width : 40,
					fixed :true,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						if(allowinsale=='1'){
							return value==undefined?'':Number(value).toFixed(2);
						}else{
							return "***";
						}
					}
				};
			colums[cols + 10] = {
					field : 'PRICE',
					title : '单价',
					width : 7,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						if(allowinsale=='1'){
							return value==undefined?'':Number(value).toFixed(2);
						}else{
							return "***";
						}
					}
				};
			colums[cols + 11] = {
				field : 'CURR',
				title : '金额',
				width : 10,
				halign : 'center',
				align : 'right',
				formatter : function(value,row,index){
					if(allowinsale=='1'){
						return value==undefined?'':(Number(value).toFixed(2));
					}else{
						return "***";
					}
				}
			};
			for (var i = 1; i <= cols; i++) {
				colums[cols + 11 + i] = {
					field : 'SIZEID' + i,
					title : 'sizeid',
					hidden : true,
					width : 10
				};
			}
			for (var i = 1; i <= cols; i++) {
				colums[cols + cols + 11 + i] = {
					field : 'SIZENAME' + i,
					title : 'sizename',
					hidden : true,
					width : 10
				};
			}
		}else if(pgno=="XX"||pgno=="XT"||pgno=="XS"){
			colums[cols + 8] = {
					field : 'PRICE0',
					title : '原价',
					width : 7,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						var provid = Number(getValuebyName('PROVID'));
						if(provid==-1){
							if(Number(allowinsale)==0)
								return "***";
						}
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 9] = {
					field : 'DISCOUNT',
					title : '折扣',
					width : 40,
					fixed :true,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						var provid = Number(getValuebyName('PROVID'));
						if(provid==-1){
							if(Number(allowinsale)==0)
								return "***";
						}
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 10] = {
					field : 'PRICE',
					title : '单价',
					width : 7,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						var provid = Number(getValuebyName('PROVID'));
						if(provid==-1){
							if(Number(allowinsale)==0)
								return "***";
						}
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 11] = {
				field : 'CURR',
				title : '金额',
				width : 10,
				halign : 'center',
				align : 'right',
				formatter : function(value,row,index){
					var provid = Number(getValuebyName('PROVID'));
					if(provid==-1){
						if(Number(allowinsale)==0)
							return "***";
					}
					return value==undefined?'':(Number(value).toFixed(2));
				}
			};
			colums[cols + 12] = {
					field : 'SALENAME',
					title : '销售类型',
					width : 70,
					fixed :true,
					halign : 'center',
					align : 'center'
				};
			colums[cols + 13] = {
					field : 'SALEID',
					title : '销售类型id',
					width : 70,
					fixed : true,
					hidden : true
				};
			for (var i = 1; i <= cols; i++) {
				colums[cols + 13 + i] = {
					field : 'SIZEID' + i,
					title : 'sizeid',
					hidden : true,
					width : 10
				};
			}
			for (var i = 1; i <= cols; i++) {
				colums[cols + cols + 13 + i] = {
					field : 'SIZENAME' + i,
					title : 'sizename',
					hidden : true,
					width : 10
				};
			}
		}else if(pgno=="KO"||pgno=="TS"||pgno=="PH"){
			colums[cols + 8] = {
					field : 'PRICE0',
					title : '原价',
					width : 7,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 9] = {
					field : 'DISCOUNT',
					title : '折扣',
					width : 40,
					fixed :true,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 10] = {
					field : 'PRICE',
					title : '单价',
					width : 7,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 11] = {
				field : 'CURR',
				title : '金额',
				width : 10,
				halign : 'center',
				align : 'right',
				formatter : function(value,row,index){
					return value==undefined?'':(Number(value).toFixed(2));
				}
			};
			for (var i = 1; i <= cols; i++) {
				colums[cols + 11 + i] = {
					field : 'SIZEID' + i,
					title : 'sizeid',
					hidden : true,
					width : 10
				};
			}
			for (var i = 1; i <= cols; i++) {
				colums[cols + cols + 11 + i] = {
					field : 'SIZENAME' + i,
					title : 'sizename',
					hidden : true,
					width : 10
				};
			}
		}else if(pgno=="QR"||pgno=="DO"||pgno=="DC"||pgno=="DR"){
			colums[cols + 8] = {
					field : 'PRICE0',
					title : '单价',
					width : 7,
					halign : 'center',
					align :'right',
					formatter : function(value,row,index){
						return value==undefined?'':Number(value).toFixed(2);
					}
				};
			colums[cols + 9] = {
					field : 'CURR',
					title : '金额',
					width : 10,
					halign : 'center',
					align : 'center',
					formatter : function(value,row,index){
						return value==undefined?'':(Number(value).toFixed(2));
					}
				};
				for (var i = 1; i <= cols; i++) {
					colums[cols + 9 + i] = {
						field : 'SIZEID' + i,
						title : 'sizeid',
						hidden : true,
						width : 10
					};
				}
				for (var i = 1; i <= cols; i++) {
					colums[cols + cols + 9 + i] = {
						field : 'SIZENAME' + i,
						title : 'sizename',
						hidden : true,
						width : 10
					};
				}
		}
	}
	
	return colums;
	}
//加载商品明细表
var iswaremt = true;
var detail_nextpg = true;
function setnotewarem(pgno,noteno) {
	$('#detail_waret').datagrid({
			width:1070,
			height : $('body').height()-300,
			fitColumns : true,
			striped : true, //隔行变色
			showFooter:true,
			nowrap :false,//允许自动换行
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
				if(rowData){
					for(var i=1;i<=cols;i++){
						$('#detail_title'+i).html(eval('rowData.SIZENAME'+i));
				}}
			},
			onLoadSuccess: function(data){
				if(pgno!="XC"){
					var sizelength = 0;
					var rows = data.rows;
					var $dg = $('#detail_waret');
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
				}
				},
			columns : [setDetailColums(pgno)],
			pageSize : 20
			}).datagrid("keyCtr","");
	if($('#detail_waret').data().datagrid!=undefined){
		$('#detail_waret').prev().children('.datagrid-body').scroll(function(){
	    	var $this =$(this);
	        var viewH =$(this).height();//可见高度
	        var contentH =$(this).get(0).scrollHeight;//内容高度
	        var scrollTop =$(this).scrollTop();//滚动高度
	      	if(contentH > (viewH+1) && contentH - viewH - scrollTop <= 3 && $("#detail_waret").datagrid("getRows").length > 0){
	      		if($('#detail_udnote').css('display')!="none"){
	      			$('#detail_udnote').hide();
		      		$('#detail_warembar').html('▼&nbsp;&nbsp;商品明细');
		    		$('#detail_waret').datagrid('resize',{
		    			height : $('body').height()-235
		    		});
		    		d = true;
	      		}
	      	if(detail_sumpg>detail_pg&&detail_nextpg){
	      		detail_nextpg = false;
				var _noteno =$('#detail_noteno').val();
				getnotewaremdetail(detail_pg+1,_noteno);
				detail_nextpg = true;
			}
	       		// 这里加载数据..
	       }
	    });
	}
}
detail_nextpg = true;
function setDetailWarem1(noteno) {
	$('#detail_warecheckt').datagrid({
			idField : 'warecheckm',
			width:1070,
			height : $('body').height()-300,
			fitColumns : true,
			striped : true, //隔行变色
			showFooter: true,
			nowrap :false,//允许自动换行
			singleSelect : true,
			onSelect : function(rowIndex, rowData) {
					for(var i=1;i<=cols;i++){
						$('#detail_title'+i).html(eval('rowData.SIZENAME'+i));
				}
					$('#detail_windex').val(rowIndex);
			},
			onLoadSuccess: function(data){
				var sizelength = 0;
				var rows = data.rows;
				var $dg = $('#detail_warecheckt');
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
						$dg.datagrid("showColumn","CHECKAMT"+i);
					else
						$dg.datagrid("hideColumn","CHECKAMT"+i);
				}
				$dg.datagrid('resize');
				},
			onDblClickRow : function(rowIndex, rowData) {
				updatewd();
				},
			columns : [setDetailColums1()],
			pageSize : 20
			}).datagrid("keyCtr","");
	if($('#detail_warecheckt').data().datagrid != undefined){
		$('#detail_warecheckt').prev().children('.datagrid-body').scroll(function(){
	    	var $this =$(this);
	        var viewH =$(this).height();//可见高度
	        var contentH =$(this).get(0).scrollHeight;//内容高度
	        var scrollTop =$(this).scrollTop();//滚动高度
	      	if(contentH > (viewH+1) && contentH - viewH - scrollTop <= 3 && $("#detail_warecheckt").datagrid("getRows").length > 0){ //到达底部100px时,加载新内容
	      		if($('#detail_udnote').css('display')!="none"){
	      			$('#detail_udnote').hide();
		      		$('#detail_warembar').html('▼&nbsp;&nbsp;商品明细');
		    		$('#detail_warecheckt').datagrid('resize',{
		    			height : $('body').height()-235
		    		});
		    		d = true;
	      		}if(detail_sumpg>detail_pg&&detail_nextpg){
	      			detail_nextpg = false;
					var _noteno =$('#detail_noteno').val();
					getnotewarem1(_noteno,detail_pg+1);
					detail_nextpg = true;
				}
	       		// 这里加载数据..
	       }
	    });
	}
	}
//获取上面明细单据及分页
function  getnotewarem1(noteno,page){
	alertmsg(6);
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
       	type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		async: false,
		data : {
			erpser : maction,
			noteno : noteno,
			sizenum : cols,
			rows: paecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)) {
				var totals = data.total;
				var totalamt = data.totalamt;
				var totalcurr = data.totalcurr;
				detail_sumpg=Math.ceil(Number(totals)/ pagecount);
				$('#detail_warecheckt').datagrid('loading');
				if(page=='1'){
					detail_pg=1;
					$('#detail_warecheckt').datagrid('loadData', data);
					if($('#detail_warecheckt').datagrid('getRows').length>0){
						$('#detail_warecheckt').datagrid('selectRow',0);
					}
				}else{
					detail_pg++;
					var rows = data.rows;
					for(var i in rows){
						$('#detail_warecheckt').datagrid('appendRow',rows[i]);
					}
				}
				detail_nextpg = true;
				$('#detail_warecheckt').datagrid('loaded');
				$('#detail_wmtotalnote1').html('已显示'+$('#detail_warecheckt').datagrid('getRows').length+'条记录/共有'+totals+'条记录');
			}
        }                 
        });	
}
function setDetailColums1() {
	var colums = [];	
	colums[0] = {
			field : 'ROWNUMBER',
			title : '序号',
			fixed : true,
			width : 30,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				return value==""?"":index+1;
			}
		};
	colums[1] = {
		field : 'WAREID',
		title : 'WAREID',
		hidden : true,
		width : 10
	};
	colums[2] = {
		field : 'WARENO',
		title : '货号',
		width : 6,
		halign : 'center',
		styler: function(value,row,index){
			if(value=="合计"){
				return 'text-align:center';
			}else{
				return 'text-align:left';
			}
		}
	};
	colums[3] = {
			field : 'WARENAME',
			title : '商品名称',
			width : 12,
			halign : 'center',
			align :'left'
		};
	colums[4] = {
		field : 'UNITS',
		title : '单位',
		width : 40,
		fixed : true,
		halign : 'center',
		align :'center'
	};
	colums[5] = {
			field : 'COLORID',
			title : '颜色id',
			width : 20,
			hidden : true
		};
	colums[6] = {
		field : 'COLORNAME',
		title : '颜色',
		width : 6,
		halign : 'center',
		align :'center'
	};
	colums[7] = {
			field : 'ZSNAME',
			title : '  ',
			width : 40,
			fixed:true,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				if(value=="no")
					return "";
				else
				 	return "<p>账：</p><p>实：</p>";
			}
		}
	for (var i = 1; i <= cols; i++) {
		colums[7 + i] = {
			field : 'CHECKAMT' + i,
			title : '<span id="detail_title'+i+'"><span>',
			width : 35,
			fixed:true,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				var check = Number(value);
				check = isNaN(check)?0:check;
				var j = this.field.replace("CHECKAMT", "")
				var book = Number(eval('row.BOOKAMT'+j));
				book = isNaN(book)?0:book;
				if(value==""&&eval('row.BOOKAMT'+j)=="")
					return "";
				else
				 	return "<p>"+book+"</p><p>"+check+"</p>";
			}
		};
	}
	colums[cols + 8] = {
		field : 'AMOUNT',
		title : '盈亏数',
		width : 50,
		fixed:true,
		halign : 'center',
		align :'center'
	};
	colums[cols + 9] = {
		field : 'PRICE',
		title : '单价',
		width : 7,
		halign : 'center',
		align :'right',
		formatter : function(value,row,index){
			return value==undefined?'':Number(value).toFixed(2);
		}
	};
	colums[cols + 10] = {
		field : 'CURR',
		title : '金额',
		width : 10,
		halign : 'center',
		align : 'right',
		formatter : function(value,row,index){
			return value==undefined?'':(Number(value).toFixed(2));
		}
	};
	for (var i = 1; i <= cols; i++) {
		colums[cols +10+ i] = {
			field : 'SIZEID' + i,
			title : 'sizeid',
			hidden : true,
			width : 10
		};
	}
	for (var i = 1; i <= cols; i++) {
		colums[cols + cols + 10 + i] = {
			field : 'SIZENAME' + i,
			title : 'sizename',
			hidden : true,
			width : 10
		};
	}
	for (var i = 1; i <= cols; i++) {
		colums[cols + cols +cols +10 + i] = {
			field : 'BOOKAMT' + i,
			hidden :true,
			halign : 'center',
			align :'center',
			formatter : function(value,row,index){
				return value==''?'':Number(value);
			}
		};
	}
	return colums;
	}
//收缩展开
var d = false;
function detail_updown() {
	$('#detail_udnote').slideToggle('fast');
	if (!d) {
		$('#detail_warembar').html('▼&nbsp;&nbsp;商品明细');
		try{
			$('#detail_waret').datagrid('resize',{
				height : 335
			});
		}catch(er){
			
		} 
		try{
			$('#detail_warecheckt').datagrid('resize',{
				height : 335
			});
		
		}catch(er){
			
		} 
		d = true;
	} else {
		$('#detail_warembar').html('▲&nbsp;&nbsp;商品明细');
		try{
			$('#detail_waret').datagrid('resize',{
				height : 300
			});
		}catch(er){
			
		} 
		try{
			$('#detail_warecheckt').datagrid('resize',{
				height : 300
			});
		
		}catch(er){
			
		} 
		d = false;
	}
}
</script>
	<!-- 销售费用帮助列表 -->
	<jsp:include page="../HelpList/SaleCost.jsp"></jsp:include>