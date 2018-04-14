<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 备注 -->
<div id="updatenote_remarkd" title="输入摘要" style="width: 350px; height: 200px"
		class="easyui-dialog" closed="true">
		<div style="margin-left: 40px;margin-top: 30px">
		<p>
		<input class="hig25" id="updatenote_remark" type="text" placeholder="<输入>" style="width:200px" data-enter="updatenote_remark()"/>
		</p>
		</div>
		<div class="dialog-footer">
				<input type="button" class="btn1" style="width:70px;margin-right: 10px" id="updatenote_remark_btn" value="保存" onclick="updatenote_remark()">
	</div>
</div>
<script type="text/javascript">
var NoteTypeArray = [
	"CG",
	"CT",
	"QS",
	"QF",
	"FZ",
	"FK",
	"FF",
	"XS",
	"XT",
	"SK",
	"SF",
	"FZ",
	"SZ"
	]
var updatenote_notetype = "";
var updatenote_noteno = "";
var updatenote_jlid = "";
var updatenote_dgid = "";
var updatenote_index = -1;
function opennoteremarkd(dgid,index,row){
	if($.inArray(row.NOTETYPE,NoteTypeArray)==-1){
		alerttext("单据无效");
		return;
	}else{
		updatenote_dgid = dgid;
		updatenote_jlid = row.ID;
		updatenote_index = index;
		updatenote_notetype = row.NOTETYPE;
		updatenote_noteno = row.NOTENO;
		$("#updatenote_remark").val(row.REMARK0);
		$("#updatenote_remarkd").dialog("open");
	}
}
function updatenote_remark(){
	var remark = $("#updatenote_remark").val();
	alertmsg(2);
	$.ajax({
		type: "POST",
		//访问WebService使用Post方式请求 
		url: "/skydesk/fyjerp",
		//调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data: {
			erpser: "setnoteremark",
			noteno: updatenote_noteno,
			notetype: updatenote_notetype,
			id: updatenote_jlid,
// 			ntid: (updatenote_notetype=="CT"?1:0),
			remark: remark
		},
		//这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			if (valideData(data)) {
				$(updatenote_dgid).datagrid('updateRow', {
					index: updatenote_index,
					row: {
						REMARK0: remark
					}
				});
				$("#updatenote_remarkd").dialog("close");
			}
		}
	})
}
</script>