<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 得到商品类型列表 -->
<div id="waretypef" title="选择商品类型" style="width: 530px; height: 450px;" class="easyui-dialog" closed=true>
<input type="hidden" id="waretypeser">
<input type="hidden" id="waretype_p_typeid" value="1">
<div style="width: 120px; float: left;margin: 5px;">
	<ul id="waretypetree">
	</ul>
</div>
<div class="fl" style="width:380px;">
<div id="waretypetoolbars" class="toolbar">
<div class="toolbar_box1">
	<div class="fl">
	<input type="text" maxlength="20" placeholder="搜索类型名称<回车搜索>" data-enter="getwaretypelist(1)" class="hig25" style="width:270px;" id="waretypefindbox" data-enter="getdata(1)">
	<input class="btn2" type="button" value="搜索" onclick="getwaretypelist(1)">
	</div>
</div>
</div>
<table id="waretypet"></table>
</div>
<div class="dialog-footer">
	<div id="waretypepp" class="tcdPageCode" style="float: left;"></div>
		<div style="float: right;">
			<input type="button" class="btn1" value="确定" onclick="waretypefuzhi()"/>
		</div>
	</div>
</div>
<script type="text/javascript">
//商品分页	
var index1=1;
var waretypedatagrid = true;
//获取商品类型数据
function getwaretypelist(page){
	var	p_typeid=$('#waretype_p_typeid').val();
	var findbox=$('#waretypefindbox').val();
	$('#waretypet').datagrid('loadData', nulldata);
	$.ajax({ 
       	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
        data: {erpser:"accwaretypelist",p_typeid:p_typeid,page:page,rows:pagecount,findbox:findbox,fieldlist:"*"},        //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	try{
        		if (valideData(data)){
	    			$('#waretypepp').setPage({
				        pageCount:Math.ceil(data.total/ pagecount),
				        current:Number(page)
					});
        		 	$('#waretypet').datagrid('loadData', data);
        		}
        	}catch(err){
				console.error(err.message);
			}
         }                 
    });
}
//选择商品类型
function selectwaretype(waretypeser){
	$('#waretypeser').val(waretypeser);
	$('#waretypefindbox').val("");
	if(!$('#waretypet').data().datagrid){
		$('#waretypepp').createPage({
			pageCount : 1,
	        current:1,
	        backFn:function(p){
	        	getwaretypelist(p.toString());
	        }
		 });
		$('#waretypet').datagrid(
				{
					height: 330,
					idField : 'TYPEID',
// 					title: "女装",
					fitColumns : true,
					striped : true, //隔行变色
					singleSelect : true,
					pageSize:20,
					toolbar: "#waretypetoolbars",
					columns : [ [ {
						field : 'TYPEID',
						title : 'ID',
						width : 200,
						hidden : true
					},{
						field : 'ROWNUMBER',title : '序号',width : 50,fixed:true,align:'center',halign:'center',
						formatter:function(value,row,index){
							var val = Math.ceil(Number(value)/ pagecount)-1;
							return isNaN(Number(value))||value==""?"":val*pagecount+Number(index)+1;
						}
					}, {
						field : 'TYPENAME',
						title : '类型',
						width : 200
					}, {
						field : 'FULLNAME',
						title : '类型全名',
						width : 200,
						hidden : true
					} ] ],
					onDblClickRow:function(rowIndex, rowData){
						waretypefuzhi();
					}
				}).datagrid("keyCtr","waretypefuzhi()");
		waretypedatagrid = false;
	}
	if($('#waretypetree').children().length==0)
		setwaretypetree();
	$('#waretypef').dialog('open');
}
function waretypefuzhi(){
	var rowData = $('#waretypet').datagrid('getSelected');
	var waretypeser = $('#waretypeser').val();
	if (rowData) {
		if (waretypeser == 'add') {
			$('#afullname').val(rowData.FULLNAME);
			$('#atypeid').val(rowData.TYPEID);
			if($('#awarename').val()=="")
				$('#awarename').val(rowData.TYPENAME);
		} else if (waretypeser == 'update') {
			$('#ufullname').val(rowData.FULLNAME);
			$('#utypeid').val(rowData.TYPEID);
			if($('#uwarename').val()=="")
				$('#uwarename').val(rowData.TYPENAME);
		} else if (waretypeser == 'aw') {
			$('#awfullname').val(rowData.FULLNAME);
			$('#awtypeid').val(rowData.TYPEID);
			if($('#awwarename').val()=="")
				$('#awwarename').val(rowData.TYPENAME);
		}else if(waretypeser =='analysis'){
			$('#afullname').val(rowData.FULLNAME);
			$('#adtypeid').val(rowData.TYPEID);
			$('#afullname').focus();
		}else if(waretypeser =='load'){
			$('#lwaretype').val(rowData.FULLNAME);
			$('#ltypeid').val(rowData.TYPEID);
			$('#lwaretype').focus();
		}else if(waretypeser =='search'){
			$('#sfullname').val(rowData.FULLNAME);
			$('#stypeid').val(rowData.TYPEID);
			$('#sfullname').focus();
		}else if(waretypeser =='search1'){
			$('#sfullname1').val(rowData.FULLNAME);
			$('#stypeid1').val(rowData.TYPEID);
			$('#sfullname1').focus();
		}
		$('#waretypef').dialog('close');
	} else {
		$tree = $("#waretypetree");
		var sel = $tree.tree("getSelected");
		if(sel){
			setbigwaretype(sel);
		}else{
			alerttext("请选择有效类型！");
		}
	}
}
function setbigwaretype(node){
	var waretypeser = $('#waretypeser').val();
	var typename = node.text;
	var typeid = node.id;
	if(waretypeser=='add'||waretypeser=='update'||waretypeser=='aw'){
		alerttext("请选择子类商品类型，不允许选择大类");
		return;
	}else if(waretypeser =='analysis'){
		$('#afullname').val(typename);
		$('#adtypeid').val(typeid);
	}else if(waretypeser =='load'){
		$('#lwaretype').val(typename);
		$('#ltypeid').val(typeid);
		$('#lwaretype').focus();
	}else{
		$('#sfullname').val(typename);
		$('#stypeid').val(typeid);
	}
	$('#waretypef').dialog('close');
}
function setwaretypetree(){
	var rows = [{
		id: -1,    
	    text: "商品大类",    
	    children: []
	}];
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "accwaretypelist",
			p_typeid: 0,
			fieldlist: "a.typeid,a.typename,a.fullname,a.p_typeid,a.lastnode",
			page: 1,
			rows: pagecount
		},
		async: false,
		//这里是要传递的参数，格式为 data: {paraName:paraValue},下面将会看到                          
		dataType: 'json',
		success: function(data) {
			try {
				if(valideData(data)){
					var rowsstr = JSON.stringify(data.rows).replace(/TYPEID/g,"id").replace(/TYPENAME/g,"text");
					rows[0].children = $.parseJSON(rowsstr);
				}
			} catch (e) {
				// TODO: handle exception
				console.error(e);top.wrtiejsErrorLog(e);
			}
		}
	});
	var $tree = $('#waretypetree');
	$tree.tree({
		data: rows,
		onClick: function(node) {
			var tid = $('#waretype_p_typeid').val();
			var waretypeser = $('#waretypeser').val();
			if (node.id != tid && node.id!=-1) {
				if((waretypeser=="add"||waretypeser=="update")&&node.id==0) return;
				$('#waretype_p_typeid').val(node.id);
				getwaretypelist(1);
			}
		},
		onDblClick: function(node){
			if(node){
				setbigwaretype(node);
			}
		}
	});
	var node = $tree.tree("find",1);
	if(node!=null) $tree.tree("select",node.target);
	getwaretypelist(1);
}
</script>
