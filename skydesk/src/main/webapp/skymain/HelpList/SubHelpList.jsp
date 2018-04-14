<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
a.roundimgback{padding:25px;display:inline-block;border-radius:10px;margin:0 3px;vertical-align:middle}
li.imgindexli{margin:3px;width:50px;height:60px;text-align:center;float:left;cursor:pointer;position:relative}
span.imgspan{display:inline-block;padding:20px}
li.imgindexli.selected,li.imgindexli:hover{color:#ff7900}
li.imgindexli.selected:before{content: '';width:16px;height:16px;position: absolute;top:0;right: 0;background: url(/skydesk/images/icon_bookcheck.png) no-repeat center center;background-color: #fff;border-radius: 8px;}
span.imgindex0{background:url(/skydesk/images/accbook/recordtype/zb_recordType_00.png) no-repeat center center}
span.imgindex1{background:url(/skydesk/images/accbook/recordtype/zb_recordType_01.png) no-repeat center center}
span.imgindex2{background:url(/skydesk/images/accbook/recordtype/zb_recordType_02.png) no-repeat center center}
span.imgindex3{background:url(/skydesk/images/accbook/recordtype/zb_recordType_03.png) no-repeat center center}
span.imgindex4{background:url(/skydesk/images/accbook/recordtype/zb_recordType_04.png) no-repeat center center}
span.imgindex5{background:url(/skydesk/images/accbook/recordtype/zb_recordType_05.png) no-repeat center center}
span.imgindex6{background:url(/skydesk/images/accbook/recordtype/zb_recordType_06.png) no-repeat center center}
span.imgindex7{background:url(/skydesk/images/accbook/recordtype/zb_recordType_07.png) no-repeat center center}
span.imgindex8{background:url(/skydesk/images/accbook/recordtype/zb_recordType_08.png) no-repeat center center}
span.imgindex9{background:url(/skydesk/images/accbook/recordtype/zb_recordType_09.png) no-repeat center center}
span.imgindex10{background:url(/skydesk/images/accbook/recordtype/zb_recordType_10.png) no-repeat center center}
span.imgindex11{background:url(/skydesk/images/accbook/recordtype/zb_recordType_11.png) no-repeat center center}
span.imgindex12{background:url(/skydesk/images/accbook/recordtype/zb_recordType_12.png) no-repeat center center}
span.imgindex13{background:url(/skydesk/images/accbook/recordtype/zb_recordType_13.png) no-repeat center center}
span.imgindex14{background:url(/skydesk/images/accbook/recordtype/zb_recordType_14.png) no-repeat center center}
span.imgindex15{background:url(/skydesk/images/accbook/recordtype/zb_recordType_15.png) no-repeat center center}
span.imgindex16{background:url(/skydesk/images/accbook/recordtype/zb_recordType_16.png) no-repeat center center}
span.imgindex17{background:url(/skydesk/images/accbook/recordtype/zb_recordType_17.png) no-repeat center center}
span.imgindex18{background:url(/skydesk/images/accbook/recordtype/zb_recordType_18.png) no-repeat center center}
span.imgindex19{background:url(/skydesk/images/accbook/recordtype/zb_recordType_19.png) no-repeat center center}
span.imgindex20{background:url(/skydesk/images/accbook/recordtype/zb_recordType_20.png) no-repeat center center}
span.imgindex21{background:url(/skydesk/images/accbook/recordtype/zb_recordType_21.png) no-repeat center center}
span.imgindex22{background:url(/skydesk/images/accbook/recordtype/zb_recordType_22.png) no-repeat center center}
span.imgindex23{background:url(/skydesk/images/accbook/recordtype/zb_recordType_23.png) no-repeat center center}
span.imgindex24{background:url(/skydesk/images/accbook/recordtype/zb_recordType_24.png) no-repeat center center}
span.imgindex25{background:url(/skydesk/images/accbook/recordtype/zb_recordType_25.png) no-repeat center center}
span.imgindex26{background:url(/skydesk/images/accbook/recordtype/zb_recordType_26.png) no-repeat center center}
span.imgindex27{background:url(/skydesk/images/accbook/recordtype/zb_recordType_27.png) no-repeat center center}
span.imgindex28{background:url(/skydesk/images/accbook/recordtype/zb_recordType_28.png) no-repeat center center}
span.imgindex29{background:url(/skydesk/images/accbook/recordtype/zb_recordType_29.png) no-repeat center center}
</style>
	<!-- 得到记账项目列表  -->
	<div id="subf" title="记账项目列表" style="width: 530px; height: 440px;" class="easyui-dialog" closed=true>
	<!-- 搜索栏 -->
	<div id="subsbtn" class="help-qstoolbar">
		<input class="btn2" type="button" value="添加" onclick="addsubd()">
		<input class="btn2" type="button" value="修改" onclick="updatesubd()">
		<input type="text" class="help-qsipt" id="subfindbox" data-enter="getsubdata('1')" style="width:160px" placeholder="搜索记账项目<回车搜索>"  data-end="yes">
		<label><input type="radio" name="sublx" value="1" checked />收入</label>
		<label><input type="radio" name="sublx" value="2" />支出</label>
		<input class="btn2" type="button" value="搜索" onclick="getsubdata('1')">
		<input  type="hidden" id="subser" >
	</div>
		<table id="subt" style="overflow: hidden;"></table>
		<div class="dialog-footer">
			<div id="subpp" class="tcdPageCode" style="float: left;"></div>
			<div style="float: right;">
				<input type="button" class="btn1" value="确定" onclick="subfuzhi()" />
<!-- 				<input type="button" class="btn2" value="取消" onclick="$('#subf').dialog('close')" />  -->
			</div>
		</div>
	</div>
<!-- 添加记账项目窗 -->
<div id="usubitemd" title="添加记账项目" style="width: 470px; padding: 10px"
		class="easyui-dialog" closed=true>
		<div style="margin-top: 10px;">
		<label class="theader skyrequied">记账项目名称&nbsp;&nbsp;&nbsp;</label> 
		<input  style="height: 25px" type="text" placeholder="<输入>" id="h_subname" name="h_subname" width="175px" maxlength="20" />
		<label><input type="radio" id="addsublx1" name="addsublx" value="1" checked />收入</label>
		<label><input type="radio" id="addsublx2" name="addsublx" value="2" />支出</label>
		</div>
		<div style="margin-top: 10px;">
		<label class="theader skyrequied">请选择记账图标</label>
		<ul id="imgindexul"  style="margin-top: 10px;overflow: hide;zoom:1">
		<li class="imgindexli selected" imgindex=0>
	<span class="imgspan imgindex0">
	</span>
	<span class="imgtitle">
		一般
	</span>
</li>
<li class="imgindexli" imgindex=1>
	<span class="imgspan imgindex1">
	</span>
	<span class="imgtitle">
		收入
	</span>
</li>
<li class="imgindexli" imgindex=2>
	<span class="imgspan imgindex2">
	</span>
	<span class="imgtitle">
		支出
	</span>
</li>
<li class="imgindexli" imgindex=3>
	<span class="imgspan imgindex3">
	</span>
	<span class="imgtitle">
		银行卡
	</span>
</li>
<li class="imgindexli" imgindex=4>
	<span class="imgspan imgindex4">
	</span>
	<span class="imgtitle">
		财务
	</span>
</li>
<li class="imgindexli" imgindex=5>
	<span class="imgspan imgindex5">
	</span>
	<span class="imgtitle">
		酒店
	</span>
</li>
<li class="imgindexli" imgindex=6>
	<span class="imgspan imgindex6">
	</span>
	<span class="imgtitle">
		交通
	</span>
</li>
<li class="imgindexli" imgindex=7>
	<span class="imgspan imgindex7">
	</span>
	<span class="imgtitle">
		餐饮
	</span>
</li>
<li class="imgindexli" imgindex=8>
	<span class="imgspan imgindex8">
	</span>
	<span class="imgtitle">
		固定资产
	</span>
</li>
<li class="imgindexli" imgindex=9>
	<span class="imgspan imgindex9">
	</span>
	<span class="imgtitle">
		罚款
	</span>
</li>
<li class="imgindexli" imgindex=10>
	<span class="imgspan imgindex10">
	</span>
	<span class="imgtitle">
		工资
	</span>
</li>
<li class="imgindexli" imgindex=11>
	<span class="imgspan imgindex11">
	</span>
	<span class="imgtitle">
		货品
	</span>
</li>
<li class="imgindexli" imgindex=12>
	<span class="imgspan imgindex12">
	</span>
	<span class="imgtitle">
		运输
	</span>
</li>
<li class="imgindexli" imgindex=13>
	<span class="imgspan imgindex13">
	</span>
	<span class="imgtitle">
		营销
	</span>
</li>
<li class="imgindexli" imgindex=14>
	<span class="imgspan imgindex14">
	</span>
	<span class="imgtitle">
		会务
	</span>
</li>
<li class="imgindexli" imgindex=15>
	<span class="imgspan imgindex15">
	</span>
	<span class="imgtitle">
		招待
	</span>
</li>
<li class="imgindexli" imgindex=16>
	<span class="imgspan imgindex16">
	</span>
	<span class="imgtitle">
		房租
	</span>
</li>
<li class="imgindexli" imgindex=17>
	<span class="imgspan imgindex17">
	</span>
	<span class="imgtitle">
		水电
	</span>
</li>
<li class="imgindexli" imgindex=18>
	<span class="imgspan imgindex18">
	</span>
	<span class="imgtitle">
		设备
	</span>
</li>
<li class="imgindexli" imgindex=19>
	<span class="imgspan imgindex19">
	</span>
	<span class="imgtitle">
		网络
	</span>
</li>
<li class="imgindexli" imgindex=20>
	<span class="imgspan imgindex20">
	</span>
	<span class="imgtitle">
		通讯
	</span>
</li>
<li class="imgindexli" imgindex=21>
	<span class="imgspan imgindex21">
	</span>
	<span class="imgtitle">
		团建
	</span>
</li>
<li class="imgindexli" imgindex=22>
	<span class="imgspan imgindex22">
	</span>
	<span class="imgtitle">
		办公
	</span>
</li>
<li class="imgindexli" imgindex=23>
	<span class="imgspan imgindex23">
	</span>
	<span class="imgtitle">
		亏损
	</span>
</li>
<li class="imgindexli" imgindex=24>
	<span class="imgspan imgindex24">
	</span>
	<span class="imgtitle">
		注册
	</span>
</li>
<li class="imgindexli" imgindex=25>
	<span class="imgspan imgindex25">
	</span>
	<span class="imgtitle">
		保险
	</span>
</li>
<li class="imgindexli" imgindex=26>
	<span class="imgspan imgindex26">
	</span>
	<span class="imgtitle">
		快递
	</span>
</li>
<li class="imgindexli" imgindex=27>
	<span class="imgspan imgindex27">
	</span>
	<span class="imgtitle">
		捐赠
	</span>
</li>
<li class="imgindexli" imgindex=28>
	<span class="imgspan imgindex28">
	</span>
	<span class="imgtitle">
		日用
	</span>
</li>
<li class="imgindexli" imgindex=29>
	<span class="imgspan imgindex29">
	</span>
	<span class="imgtitle">
		家居
	</span>
</li>
		</ul>
		</div>
		<div class="dialog-normal-footer" style="text-align: center;">
			<input id="add" class="btn1" type="button" name="save" type="button" value="保存" onclick="savesub();">
		</div>
	</div>
<script type="text/javascript">
var subdatagrid = true;
var imgtitle = ["一般","收入","支出","银行卡","财务",
    "酒店","交通","餐饮","固定资产","罚款",
    "工资","货品","运输","营销","会务",
    "招待","房租","水电","设备","网络",
    "通讯","团建","办公","亏损","注册",
    "保险","快递","捐赠","日用","家居"
   ]
//加载记账项目列表窗
function selectsub(subser) {
	$("#subser").val(subser);
	var findbox = '';
	$('#subfindbox').val("");
	if (!$('#subt').data().datagrid) {
		$("input[name=sublx]").change(function() {
			getsubdata(1);
		});
		$("#imgindexul li.imgindexli").click(function(){
			var $this = $(this);
			if(!$this.hasClass("selected")){
				$("li.imgindexli.selected").removeClass("selected");
				$this.addClass("selected");
				var imgindex =$this.attr("imgindex");
				var subname = $("#h_subname").val();
				if(subname==""||$.inArray(subname,imgtitle)>=0) $("#h_subname").val(imgtitle[imgindex]);
				$("#h_subname").focus();
			}
		});
		$('#subpp').createPage({
			current: 1,
			backFn: function(p) {
				getsubdata(p);
			}
		});
		$('#subt').datagrid({
			height: 350,
			idField: 'subname',
			fitColumns: true,
			striped: true,
			//隔行变色
			singleSelect: true,
			pageSize: 20,
			columns: [[{
				field: 'SUBID',
				title: 'ID',
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
					var num = Number(value);
					if (!isNaN(num)) return num;
					return "";
				}
			},
			{
				field: 'SUBNAME',
				title: '记账项目',
				width: 160,
				fixed: true,
				align: 'left',
				halign: 'center',
				formatter: function(value, row, index) {
					var imgindex = "0" + row.IMGINDEX;
					imgindex = imgindex.substr( - 2);
					return '<a class="roundimgback" style="background:url(/skydesk/images/accbook/recordtype/zb_recordType_' + imgindex + '.png)"></a>' + value;
				}
			},{
				field: 'ACTION',
				title: '操作',
				width: 80,
				fixed: true,
				align: 'center',
				halign: 'center',
				formatter: function(value, row, index) {
					var html = '<input type="button" class="m-btn" value="删除" onclick="delsubitem(' + index + ')">';
					return html;
				}
			}]],
			onSelect:function(index,row){
				editsubindex = index;
			},
			onDblClickRow: function(rowIndex, rowData) {
				subfuzhi();
			},
			toolbar: '#subsbtn'
		}).datagrid("keyCtr", "subfuzhi()");
	}
	getsubdata('1'); //加载数据
	$('#subf').dialog('open');
	$('#subfindbox').focus();
}
//双击、确定赋值（记账项目）
function subfuzhi() {
	var rowData = $('#subt').datagrid('getSelected');
	var subser = $('#subser').val();
	if (rowData) {
		if (subser == 'add') {
			$('#adsubname').val(rowData.SUBNAME);
			$('#adsubid').val(rowData.SUBID);
			$('#adsubname').focus();
		} else if (subser == 'update') {
			$('#usubname').val(rowData.SUBNAME);
			$('#usubid').val(rowData.SUBID);
			$('#usublx').val(rowData.LX);
			if(rowData.LX==1) $("#usublxname").val("收入");
			if(rowData.LX==2) $("#usublxname").val("支出");
			$('#usubname').focus();
		} else if (subser == 'analysis') {
			$('#adsubname').val(rowData.SUBNAME);
			$('#adsubid').val(rowData.SUBID);
			$('#adsubname').focus();
		} else if (subser == 'autobarcode') {
			$('#isubname').val(rowData.SUBNAME);
			$('#isubid').val(rowData.SUBID);
			$('#isubname').focus();
		} else if (subser == 'load') {
			$('#lsubname').val(rowData.SUBNAME);
			$('#lsubid').val(rowData.SUBID);
			$('#lsubname').focus();
		} else {
			$('#ssubname').val(rowData.SUBNAME);
			$('#ssubid').val(rowData.SUBID);
			$('#ssubname').focus();
		}
		$('#subf').dialog('close');
	} else {
		alert('请选择');
	}
}
//获取记账项目列表
function getsubdata(page) { //定义回调函数#')
	var findbox = $('#subfindbox').val();
	findbox = findbox == undefined ? "": findbox;
	var lx = $("input[name=sublx]:checked").val();
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fybuyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "listsubitem",
			findbox: findbox,
			rows: pagecount,
			lx: lx,
			noused: 0,
			//lx:1=收入，2=支出
			fieldlist: "subid,subname,noused,imgindex,lx",
			page: page
		},
		dataType: 'json',
		success: function(data) {
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
		}
	});

}
//添加框
function addsubd() {
	$('#usubitemd').dialog("setTitle","新建记账项目").dialog('open');
	editsubid = 0;
	$("#usubitemd input[type=radio]").prop("disabled",false);
	$('#h_subname').val("").focus();
}
var editsubindex = 0;
var editsubid = 0;
function updatesubd() {
	var rows = $("#subt").datagrid("getRows");
	var row = rows[editsubindex];
	if(row){
		editsubid = row.SUBID;
		$("#addsublx"+row.LX+"").prop("checked",true);
		$("#usubitemd input[type=radio]").prop("disabled",true);
		$('#usubitemd').dialog("setTitle","编辑记账项目").dialog('open');
		$("li.imgindexli.selected").removeClass("selected");
		$("#imgindexul li[imgindex="+row.IMGINDEX+"]").addClass("selected");
		$('#h_subname').val(row.SUBNAME).focus();
	}else{
		alerttext("请选择数据");
	}
}
//添加记账项目
function savesub() {
	var erpser = "addsubitem";
	if(editsubid>0){
		erpser="updatesubitem";
	}
	var subname = $('#h_subname').val();
	var lx = $("input[name=addsublx]:checked").val();
	var imgindex = $("li.imgindexli.selected").attr("imgindex");
	if (subname == "" || subname == "null" || subname == "undefined") {
		alerttext("记账项目名不能为空");
	} else {
		alertmsg(2);
		$.ajax({
			type: "POST",
			//访问WebService使用Post方式请求 
			url: "/skydesk/fybuyjerp",
			//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data: {
				erpser: erpser,
				subid: editsubid,
				subname: subname,
				imgindex: imgindex,
				lx: lx
			},
			//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值
				try {
					if (valideData(data)) {
						$('#subpp').refreshPage();
						$("#usubitemd").dialog("close");
					}
				} catch(err) {
					console.error(err.message);
				}
			}
		});
	}
}
function delsubitem(index){
	var $dg = $("#subt");
	var rows = $dg.datagrid("getRows");
	if (rows.length > 0) {
		var row = rows[index];
		var subid = row.SUBID;
		$.messager.confirm(dialog_title, '您确认要删除账本项目' + row.SUBNAME + '？',
		function(r) {
			if (r) {
				alertmsg(2);
				$.ajax({
					type: "POST",
					//访问WebService使用Post方式请求 
					url: "/skydesk/fybuyjerp",
					//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
					data: {
						erpser: "delsubitem",
						subid: subid
					},
					//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
					dataType: 'json',
					success: function(data) { //回调函数，result，返回值 
						try {
							if (valideData(data)) {
								$dg.datagrid("deleteRow", index).datagrid("refresh");
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
}
</script>
