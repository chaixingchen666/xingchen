<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- （无折扣）快速编辑窗体 -->
<div id="quickud" title="修改商品明细" style="text-align: center;max-width:100%;max-height:100%;"  class="easyui-dialog" closed="true">
<input type="hidden" id="wquickuid" >
<div style="width: auto;overflow: auto;text-align: center;position: relative;left: 0;top: 0;">
<form onsubmit="return false;" id="quickuaddwaref" action="" method="get" class="quickkeymsg">
  输入数量后：Ctrl+A：所有数量一致；Ctrl+S：设置之后同颜色一致；Ctrl+D：设置之后同尺码数量一致；Ctrl+Z:取消所有数量输入。
</form>
<form onsubmit="return false;" action="" method="get" id="quickutable">
</form>
</div>
<div class="dialog-footer" style="text-align: center;display: block;position: relative;">
	<span class="ml10 span_wlhistory hide"><label><input id="showallotouthistory" type="checkbox">显示调拨历史</label></span>
	<span><input id="quickadd_btn" type="button" class="btn1" value="保存"></span>
</div>
<div class="hide" id="allotoutwarehistorydiv" style="clear:both">
<table id="allotoutwarehistoryt"></table>
<div style="margin: 5px">
<div id="allotoutwarehistorypp"  class="tcdPageCode fl"></div>
</div>
</div>
</div>
<script type="text/javascript">
$('#showallotouthistory').change(function(){
	if($('#allotoutwarehistoryt').data().datagrid==undefined){
		setallotoutwarehistt();
	}
	var $this = $(this);
	var showallotouthistory = 0;
	if($this.prop('checked'))
		showallotouthistory = 1;
	var $div = $('#allotoutwarehistorydiv');
	if(showallotouthistory==1){
		$div.show();
		$('#allotoutwarehistoryt').datagrid('resize');
		$('#quickud').window('center');
		listsalehistory(1);
	}else{
		$div.hide();
		$('#quickud').window('center');
	}
	setparam({
		pgid: pgid.replace("pg",""),
		usymbol: "showallotouthistory",
		uvalue: showallotouthistory
	});
	$('#u1-1').focus();
});
$(function(){
	if(pgid=="pg1402"){
		$("span.span_wlhistory").show();
		var showallotouthistory = Number(getparam({pgid: pgid.replace("pg",""),
			usymbol: "showallotouthistory"}));
		if(showallotouthistory==1){
			$('#showallotouthistory').prop('checked',true);
		}
	}else $("span.span_wlhistory").hide();
});
//初始化拿货历史表；
function setallotoutwarehistt(){
	$('#allotoutwarehistorypp').createPage({
		pageCount:1,
        current:1,
        backFn:function(p){
        	listsalehistory(p);
        }
	 });
	$('#allotoutwarehistoryt').datagrid(
		{
			idField : 'ID',
			title: "调拨历史(双击查看明细)",
			height: 240,
			fitColumns: true,
			nowrap: false,
			striped : true, //隔行变色
			singleSelect: true,
			frozenColumns:[[
			{
				field : 'NTID',title : 'NTID',width : 200,hidden:true
			},{
				field : 'ID',title : 'ID',width : 200,hidden:true
			},{
				field : 'ROWNUMBER',title: '序号',width: 50,fixed:true,align:'center',halign:'center',
				formatter:function(value,row,index){
					var val = Math.ceil(Number(value)/ pagecount)-1;
					return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
				}
			},{
				field : 'NOTENO',	title : '单据号',width : 115,fixed:true,align:'center',halign:'center'
			},{
				field : 'NOTEDATE',	title : '单据日期',width : 100,fixed:true,align:'center',halign:'center'
			}]],
			columns: [ [
			{
				field : 'COLORNAME',title : '颜色',width : 80,fixed:true,align:'center',halign:'center'
			}, {
				field : 'AMOUNT',title : '数量',width : 60,fixed:true,align:'center',halign:'center',formatter: amtfm
			}, {
				field : 'REMARK',title : '备注',width : 150,fixed:true,align:'center',halign:'center'
			}]],
			onDblClickRow : function(index, row){
		   		var noteno = row.NOTENO;
		   		var pgno = "DC";
		   		if(noteno)
		   			opendetaild(pgno,noteno);
			}
		});
}

//获取销售历史数据
function listsalehistory(page){
	var tohouseid = $('#utohouseid').val();
	var houseid = $('#uhouseid').val();
	var wareid = $('#wquickuwareid').val();
	var erpser = "listallothistory";
	$('#allotoutwarehistoryt').datagrid('loadData', nulldata);
	alertmsg(2);
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser: erpser,
			houseid: houseid,
			tohouseid: tohouseid,
			wareid: wareid,
			rows: pagecount,
			page: page
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$("#allotoutwarehistorypp").setPage({
						pageCount: Math.ceil(data.total / pagecount),
						current: Number(page)
					});
					$('#allotoutwarehistoryt').datagrid('loadData', data);
				}
			}catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
}
</script>