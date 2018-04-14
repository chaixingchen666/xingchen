<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">修改用户信息</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
 <div class="control-group">
    <label class="control-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
   <div class="controls">
    <input type="text" id="epname" name="epname" maxlength="10">
   </div></div>
    <div class="control-group">
       <label class="control-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</label>
   <div class="controls">
	<select id="sex" name="sex" >
	<option value="">---请选择---</option>
	<option  value="男">男</option>
	<option value="女">女</option>
	</select>
   </div></div>
    <div class="control-group">
       <label class="control-label">用户账户</label>
   <div class="controls">
     <input  type="text" id="epno" disabled>
   </div>
   </div>
    <div class="control-group">
       <label class="control-label" >身份证号</label>
   <div class="controls">
    <input type="number" maxlength="18" id="idno" name="idno" placeholder="请输入身份证号">
   </div></div>
    <div class="control-group">
       <label class="control-label">固定电话</label>
   <div class="controls">
    <input type="number" id="tel" name="tel" placeholder="请输入固定电话">
   </div></div>
   <div class="control-group">
       <label class="control-label">移动电话</label>
   <div class="controls">
    <input type="tel" id="mobile" name="mobile" placeholder="请输入移动电话">
   </div></div>
    <div class="control-group">
<label class="control-label">住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址</label>
   <div class="controls">
    <input type="text" id="address" name="address" maxlength="100" placeholder="请输入住址">
   </div></div>
    <div class="control-group">
   <label class="control-label" >邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编</label>
   <div class="controls">
    <input type="number" id="postcode" name="postcode" maxlength="6" placeholder="请输入邮编">
   </div>
     </div>
</div>  
  </div>
 <div class="modal-footer" >
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
   <button class="btn btn-primary" onclick="updateuserinfo()">保&nbsp;&nbsp;存</button>
 </div>
</div>
<!-- 修改参数设置 -->
<div id="paramModal" class="modal hide fade" tabindex="-1" style="width:750px" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">参数设置</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal" id="form-param" style="text-align: center;">
<table width="680" border="0" style="display: inline-table;">
  <tr>
    <td align="left">启用权限设置</td>
    <td align="left"><div class="switch switch-small" id="qxkz">
    	<input type="checkbox" id="uqxkz"/>
		</div></td>
</tr>
<tr>
    <td align="left">允许负数出库</td>
    <td align="left"><div class="switch switch-small"  id="allowfsck">
    	<input type="checkbox" checked="checked" id="uallowfsck"/>
		</div></td>
  </tr>
  <tr>
    <td align="left">启用最近售价</td>
    <td align="left"> <div class="switch switch-small" id="allownearsale" >
    	<input type="checkbox" id="uallownearsale"  checked="checked"/>
		</div></td>
</tr>
<tr>
    <td align="left">采购入库自动更新进价</td>
    <td align="left"> <div class="switch switch-small" id="allowwareinsale" >
    	<input type="checkbox" id="uallowwareinsalee"  checked="checked"/>
		</div></td>
  </tr>
  <tr>
    <td align="left">每页显示记录</td>
    <td align="left"><select id="pages"  style="width:54px" class="all">
     <option value="10">10</option>
	  <option value="15">15</option>
	  <option value="20">20</option>
	   <option value="25">25</option>
	  <option value="30">30</option>
	  <option value="35">35</option>
	   <option value="40">40</option>
	  <option value="45">45</option>
	  <option value="50">50</option>
    </select>行</td>
</tr>
<tr>
    <td align="left">单价小数位数</td>
    <td align="left"><select id="points" style="width:82px">
	  <option value="0">0</option>
	  <option value="1">1</option>
	  <option value="2">2</option>
	</select></td>
  </tr>
  <tr>
    <td align="left">零售金额小数位数</td>
    <td align="left"><select id="lspoints" style="width:82px">
	  <option value="0">0</option>
	  <option value="1">1</option>
	  <option value="2">2</option>
	</select></td>
  </tr>
  <tr>
    <td align="left">积分抵扣比例</td>
    <td align="left">1元=
   <input id="centbl"  type="text" list="cent_list" style="width:50px" placeholder="<输入>" maxlength="4">分
   	<datalist id="cent_list">
	<option label="10" value="10" />
	<option label="100" value="100" />
	<option label="200" value="200" />
	<option label="500" value="500" />
	<option label="1000" value="1000" />
	</datalist>
	</td>
</tr>
<tr>
    <td align="left" id="allowsuertd">账户禁用</td>
    <td align="left"> <div class="switch switch-small" id="allowuser" >
    	<input type="checkbox" id="uallowuser"  checked="checked"/>
		</div></td>
  </tr>
   <tr>
    <td align="left">商品默认颜色</td>
    <td align="left" colspan="3"><input type="text" id="defaultcolor" placeholder="<请选择默认颜色>" disabled style="width:154px">
    <input type="button" id="selectcolor" value="选择默认颜色" class="btn btn-primary" onclick="getcolorlist('1')">
    <input type="button" id="resetcolor" value="取消默认颜色" class="btn btn-primary" onclick="writedefaultcolor('0')" >
    </td>
    </tr>
    <tr>
    <td align="left">商品默认尺码</td>
    <td align="left" colspan="3">
    <select id="sizegroupno" onchange="writedefaultsize()">
    <option value="">&lt;请选择&gt;</option>
    </select>
    </td>
  </tr>
  <tr>
    <td align="left">批发默认客户</td>
    <td align="left" colspan="3"><input type="text" id="defaultcust" placeholder="<请选择默认客户>" disabled style="width:154px">
    <input type="button" id="selectcust" value="选择默认客户" class="btn btn-primary" onclick="getcustlist('1')">
     <input type="button" id="resetcust" value="取消默认客户" class="btn btn-primary" onclick="setdefaultcust('0')" >
     <input type="hidden" id="defaultcustid" value="0"></td>
  </tr>
    <tr>
    <td align="left">自动产生货号</td>
    <td align="left"><div class="switch switch-small" id="zdcshh">
    	<input type="checkbox" id="uzdcshh"/>
		</div></td>
</tr>
<tr>
    <td align="left">配货单允许直接出库</td>
    <td align="left"><div class="switch switch-small"  id="psdck">
    	<input type="checkbox" checked="checked" id="upsdck"/>
		</div></td>
  </tr>
    <tr>
    <td align="left">配送单审核后才允许捡货</td>
    <td align="left"><div class="switch switch-small" id="psdjh">
    	<input type="checkbox" id="upsdjh"/>
		</div></td>
</tr>
<tr>
    <td align="left">往来账款分店铺核对</td>
    <td align="left"><div class="switch switch-small"  id="xsdzdp">
    	<input type="checkbox" checked="checked" id="uxsdzdp"/>
		</div>
</td>
  </tr>
    <tr>
    	<td align="left">商品自动产生条码</td>
    <td align="left"><div class="switch switch-small"  id="autobarcode">
    	<input type="checkbox" checked="checked" id="uautobarcode"/>
		</div></td>
</tr>
<tr>
    <td align="left">条码产生方式</td>
    <td align="left"> 
    <select id="barcodefs">
    <option value="1">货号+色码+尺码代号</option>
    <option value="2">货号+色码+尺码名称</option>
    <option value="3">厂家编码+色码+尺码代号</option>
    <option value="4">厂家编码+色码+尺码名称</option>
    <option value="5">货号+尺码代号+色码</option>
    <option value="6">货号+尺码名称+色码</option>
    <option value="7">厂家编码+尺码代号+色码</option>
    <option value="8">厂家编码+尺码名称+色码</option>
    <option value="9">8位自动序号</option>
    </select>
    </td>
<!--     <td align="left"><p>条码自动偶数</p><p>补位</p></td> -->
<!--     <td align="left"><div class="switch switch-small" id="barcodedbl"> -->
<!--     	<input type="checkbox" id="ubarcodedbl" checked="checked" /> -->
<!-- 		</div> -->
<!-- 	</td> -->
  </tr>
    <tr>
    <td align="left">收银交班</td>
    <td  align="left"><select id="classtype">
    <option value="0">店铺统一交班</option>
    <option value="1">分收银员交班</option>
    </select>
    </td>
</tr>
<tr>
    <td align="left">零售抹零方式</td>
    <td align="left"> 
    <select id="lsmlfs">
    <option value="0">取整</option>
    <option value="1">四舍五入</option>
    <option value="2">不抹零</option>
    </select>
    </td>
  </tr>
      <tr>
    <td align="left">零售启用收银台</td>
    <td align="left"><div class="switch switch-small" id="usesyt">
    	<input type="checkbox" id="uusesyt" checked="checked" />
		</div>
	</td>
</tr>
<tr>
    <td align="left">批发启用收银台</td>
    <td align="left"><div class="switch switch-small" id="usepfsyt">
    	<input type="checkbox" id="uusepfsyt" checked="checked" />
		</div>
	</td>
	</tr>
	<tr>
    <td align="left">收银启用会员C端验证</td>
    <td align="left"><div class="switch switch-small"  id="useyhq">
    	<input type="checkbox" checked="checked" id="uuseyhq"/>
		</div></td>
</tr>
<tr>
    <td align="left">结账自动勾单</td>
    <td align="left"><div class="switch switch-small"  id="jzzdgd">
    	<input type="checkbox" checked="checked" id="ujzzdgd"/>
		</div></td>
  </tr>
<tr>
    <td align="left">移动设备授权后登录</td>
    <td align="left"><div class="switch switch-small"  id="ydsqsb">
    	<input type="checkbox" checked="checked" id="uydsqsb"/>
		</div></td>
  </tr>
</table>
</div>  
  </div>
 <div class="modal-footer" >
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
 </div>
</div>
<!-- 客户帮助列表 -->
<div class="help-box hide" id="cust-help">
 <div class="help-header">
 <span>选择客户帮助列表</span>
 <span class="help-close">×</span>
</div>
 <div class="help-body">
 <input class="findbox" type="text" id="custfindbox" placeholder="<客户名称搜索>" onkeyup="if(iskey(13)){getcustlist('1');}">
 <input type="button" class="btn" value="搜索" onclick="getcustlist('1')">
 <ul id="cust-ul">
 </ul>
 </div>
 <div class="help-footer">
 <ul class="help-page">
<!--  <li data-page="1">1</li> -->
 </ul>
 <span id="help-total"></span>
 </div>
 </div>
<!-- 颜色帮助列表 -->
<div class="help-box hide" id="color-help">
 <div class="help-header">
 <span>选择颜色帮助列表</span>
 <span class="help-close">×</span>
</div>
 <div class="help-body">
 <input class="findbox" type="text" id="colorfindbox" placeholder="<颜色名称搜索>" onkeyup="if(iskey(13)){getcolorlist('1');}">
  <input class="btn" type="button" value="搜索" onclick="getcolorlist('1')">
 <ul id="color-ul">
 </ul>
 </div>
 <div class="help-footer">
 <ul class="help-page">
<!--  <li data-page="1">1</li> -->
 </ul>
 <span id="help-total"></span>
 </div>
 </div>
<!-- 企业账号 -->
<div id="comModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">账户资料</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
 <div class="control-group">
    <label class="control-label">企业账号</label>
   <div class="controls">
   	 	<input type="text" id="comno" name="comno" disabled>
   </div></div>
   <div class="control-group">
    <label class="control-label">注册时间</label>
   <div class="controls">
   	 	<input type="text" id="logtime" name="logtime" disabled>
   </div></div>
   <div class="control-group">
    <label class="control-label">建账日期</label>
   <div class="controls">
   	 	<input type="text" id="begindate" name="begindate" disabled style="width:80px;">
   	 	<a class="btn btn-primary admin" href="#changebegindateModal" data-toggle="modal" onclick="$('#comModal').hide()">更改建账日期</a>
   </div></div>
   <div class="control-group">
    <label class="control-label">登账日期</label>
   <div class="controls">
   	 	<input type="text" id="calctime" name="calctime" disabled>
   </div></div>
    <div class="control-group">
       <label class="control-label">单位名称</label>
   <div class="controls">
		<input type="text" id="comname" name="comname" disabled>
   </div></div>
    <div class="control-group">
       <label class="control-label">联系人</label>
   <div class="controls">
    <input type="text" id="com_linkman" name="com_linkman" disabled>
   </div>
   </div>
    <div class="control-group">
       <label class="control-label" >固定电话</label>
   <div class="controls">
	<input type="text" id="com_tel" name="com_tel" disabled>
   </div></div>
    <div class="control-group">
       <label class="control-label">移动电话</label>
   <div class="controls">
   <input type="text" id="com_mobile" name="com_mobile" disabled>
   </div></div>
   <div class="control-group">
       <label class="control-label">地址</label>
   <div class="controls">
  		<input type="text" id="com_address" name="com_address" disabled>
   </div></div>
   <div class="control-group">
       <label class="control-label">邮箱</label>
   <div class="controls">
   		<input type="text" id="com_email" name="com_email" disabled>
   </div>
   </div>
   <div class="control-group">
       <label class="control-label">经营模式</label>
   <div class="controls">
   		<select id="com_tag" name="com_tag" disabled>
   		<option value="0">零售</option>
   		<option value="1">批发</option>
   		<option value="2">零售+批发</option>
   		<option value="3" class="hide">迷你</option>
   		</select>
   </div>
   </div>
   <div class="control-group">
       <label class="control-label">购买方式</label>
   <div class="controls">
   		<input type="text" id="com_remark" name="com_remark" disabled>
   </div>
   </div>
</div>  
  </div>
 <div class="modal-footer" >
    <button class="btn btn-primary admin" onclick="updatecominfo()">保存</button>
   <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
 </div>
</div>
<!-- 更改建账日期 -->
<div id="changebegindateModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">更改建账日期</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
   <div class="control-group">
       <label class="control-label">新的建账日期</label>
   <div class="controls">
   		 <input type="text" name="newbegindate" id="newbegindate" style="width: 162px;height:29px" class="easyui-datebox">
   </div>
   </div>
   <label>请您操作前阅读以下注意事项，并勾选已阅读</label>
   <div class="khxz">
   <p style="color:red;text-indent: 27px;">
   执行本程序后，系统自动删除当前日期以前的所有数据，并将建账日期改为当前选择日期，
请谨慎使用，删除后无法恢复以前数据。
   </p>
   </div>
   <div>
	 <label><input type="checkbox" id="isread" style="margin: 5px;">您已阅读并同意以上条例</label>
   </div>
  </div>
  </div>
 <div class="modal-footer" >
   <button class="btn btn-primary" id="ggjzrq" onclick="chengbegindate()">确定</button>
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
 </div>
</div>
<!-- 建议反馈-->
<div id="sugModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">建议反馈</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
<div class="control-group">
    <label style="margin: 10px; font-size: 16px;">反馈内容</label>
   <div style="text-align: center;">
   	 	<textarea id="suggest" style="width: 500px;" maxlength="300" rows="6" placeholder="蓝窗ERP智能管理系统感恩有您一路相伴！您的每一句建议反馈，我们都会认真聆听并努力改进。谢谢！"></textarea>
   </div></div>
</div>  
  </div>
 <div class="modal-footer" >
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    <button class="btn btn-primary" onclick="sendsuggest()">发&nbsp;&nbsp;送</button>
 </div>
</div>
<!-- 数据维护 -->
<div id="sjwhModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">数据维护</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
<div class="control-group">
     <label class="control-label">维护日期</label>
   <div class="controls">
   		 <input type="text" name="startdate" id="startdate"  style="width: 100px;height:29px" class="easyui-datebox">
   		 --<input type="text" name="enddate" id="enddate" style="width: 100px;height:29px" class="easyui-datebox">
   </div>
 </div>
   <div style="color:#ff7900">*如果存在进销存数据不平衡的情况，可以选择不正确数据的时间段进行数据汇总维护！</div>
   <div class="control-group" style="text-align: center;">
    </div>
  </div>
  <ul id="sjwhmsg"></ul>
  </div>
 <div class="modal-footer" >
   <button class="btn btn-primary" id="sjwhbtn" onclick="sjwh()">数据维护</button>
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
 </div>
</div>
<!-- 登账日设置 -->
<div id="ggdzrModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
   <h3 id="myModalLabel">登账日设置</h3>
  </div>
  <div class="modal-body">
<div class="form-horizontal">
<div class="control-group">
   <label class="control-label">设置登账日期</label>
   <div class="controls">
   		 <input type="text" name="calcdate" id="calcdate"  style="width: 100px;height:29px" class="easyui-datebox">
   </div>
 </div>
   <div style="color:#ff7900">*登账日期指的是此日期之后的单据数据允许修改，修改该登账日期，对非今天的单据修改之后，需要手动进行数据日结或者数据维护从该日期第二天开始到今天；如果不进行数据日结，系统维护00:00-00:30的时候自动日结！</div>
   <div class="control-group" style="text-align: center;margin-top: 10px;">
   <button class="btn btn-primary" id="szdzdate" onclick="setcalcdate()">设置登账日期</button>
   <button class="btn btn-primary" id="szdzdate" onclick="dailysjwh()">日结登账</button>
   </div>
   </div>
  </div>
 <div class="modal-footer" >
   <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
 </div>
</div>
<script type="text/javascript">

var changeqx = false;
$(function(){
	$('#qxkz').on('switch-change', function (e,data) {
	if(checked){
	   	updateparam('1',data.value==true?'1':'0');
	}
	});
	$('#allowfsck').on('switch-change', function (e,data) {
		if(checked){
		   updateparam('2',data.value==true?'1':'0');
		}
		});
	$('#allownearsale').on('switch-change', function (e,data) {
		if(checked){
		   updateparam('3',data.value==true?'1':'0');
		}
		});
	$('#allowwareinsale').on('switch-change', function (e,data) {
		if(checked){
		   updateparam('9',data.value==true?'1':'0');
		}
		});
	$('#allowuser').on('switch-change', function (e,data) {
		if(checked){
			setallowuser(data.value==true?'1':'0');
		}
		});
	
	$('#psdjh').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('10',data.value==true?'1':'0');
		}
		});
	$('#psdck').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('11',data.value==true?'1':'0');
		}
		});
	$('#zdcshh').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('12',data.value==true?'1':'0');
		}
		});

	$('#points').change(function(e){
		if(checked){
			updateparam("4",this.value);
		}
	});
	$('#lspoints').change(function(e){
		if(checked){
			updateparam("22",this.value);
		}
	});
	$('#barcodedbl').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('20',data.value==true?'1':'0');
		}
		});
	$('#xsdzdp').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('14',data.value==true?'1':'0');
		}
		});

	$('#classtype').change(function(e){
		if(checked){
			updateparam("15",this.value);
		}
	});
	$('#lsmlfs').change(function(e){
		if(checked){
			updateparam("16",this.value);
		}
	});
	$('#usesyt').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('17',data.value==true?'1':'0');
		}
		});
	$('#usepfsyt').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('19',data.value==true?'1':'0');
		}
		});

	$('#useyhq').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('18',data.value==true?'1':'0');
		}
		});
	$('#jzzdgd').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('21',data.value==true?'1':'0');
		}
		});
	$('#ydsqsb').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('23',data.value==true?'1':'0');
		}
		});
	$('#barcodefs').change(function(e){
		if(checked){
			updateparam("13",this.value);
		}
	});
	$('#autobarcode').on('switch-change', function (e,data) {
		if(checked){
			 updateparam('20',data.value==true?'1':'0');
		}
		});
	$('#pages').change(function(e){
		setCookie("PAGECOUNT", this.value, 300);
	});
	$('#centbl').change(function(e){
		if(checked){
			var vals = "";
			var bl = Number(this.value);
			if(isNaN(bl)||bl<=0){
				alerttext("请输入大于0的数字");
				return;
			}
			if(this.value.length==0)
				alerttext('不能为空！');
			else if(this.value.length==1)
				vals = '000' + this.value;
				else if(this.value.length==2)
					vals = '00' + this.value;
					else if(this.value.length==3)
						vals = '0' + this.value;
						else if(this.value.length==4)
							vals = this.value;
			updateparam("5",vals);
		}
	});
	$('.help-body li').click(function(e){
		var custid = $(this).data('custid');
		var custname = $(this).data('custname');
		setdefaultcust(custid);
		$('#defaultcust').val(custname);
	});
	$('#paramModal').on('hide.bs.modal', function (e) {
			closemodal();
		});
	$('#changebegindateModal').on('hide.bs.modal', function (e) {
			$('#comModal').toggle();
		});
	
	$('.help-close').click(function(){
		$('.help-box').hide();
		$('#paramModal').show();
	});
	$('#cust-ul').delegate('li','click',function(e){
		var custid = $(this).data('custid');
		var custname = $(this).data('custname');
		setdefaultcust(custid);
		$('#defaultcust').val(custname);
	});
	$('#color-ul').delegate('li','click',function(e){
		var colorid = $(this).data('colorid');
		var colorname = $(this).data('colorname');
		writedefaultcolor(colorid);
		$('#defaultcolor').val(colorname);
	});
	$('#color-help .help-page').delegate("li","click",function(){
		$('#color-help .help-page .selected').removeClass('.selected');
		$(this).addClass('selected');
		var p = $(this).data('page');
		getcolorlist(p);
	});
});
var checked=false;
var index = 1;
function getqxpublic(){
	alertmsg(6);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名             
        data:{erpser:"getallqxpublic"},
        dataType: 'json', 
        success: function(data) { 
        	if(valideData(data)){
        		checked=false;
            	var qxpublic = data.QXPUBLIC+"00000000000";
            	var colorname = data.COLORNAME; 
            	var colorid = data.COLORID; 
            	var custname = data.CUSTNAME; 
            	var custid = data.CUSTID; 
            	$('#defaultcust').val(custname);
            	$('#defaultcolor').val(colorname);
        		$('#defaultcustid').val(custid);
            	if(index==1){
            		var html = "";
	            	var sizelist = data.sizegrouplist;
	            	for(var i in sizelist){
	            		html += '<option value="'+sizelist[i].GROUPNO+'">' + sizelist[i].GROUPNO + ':('+sizelist[i].SIZELIST+')</option>';
	            	}
	            	$('#sizegroupno').append(html);
            		index=2;
            	}
            	$('#sizegroupno').val(data.SIZEGROUPNO);
            	var qxkz = qxpublic.charAt(0);//启用权限控制
            	var allowfsck = qxpublic.charAt(1);//允许负数出库
            	var allownearsale = qxpublic.charAt(2);//启用最近售价
            	var points = qxpublic.charAt(3);//单价小数位数（0,1,2）
            	var centbl = qxpublic.substring(4,8);//积分抵扣比例
            	var allowwareinsale = qxpublic.charAt(8);//允许采购入库自动更新进价
            	var phhjh = qxpublic.charAt(9);//配货单审核后才允许拣货
            	var phck = qxpublic.charAt(10);//配货单允许直接出库
            	var zdcshh = qxpublic.charAt(11);//自动产生货号
            	var barcodefs = qxpublic.charAt(12);//产生条码方式
            	var barcodedbl = Number(qxpublic.charAt(19));//条码打印偶数补全
            	var xszkdpdz = qxpublic.charAt(13);//销售账款分店核对
            	var classtype = qxpublic.charAt(14);//收银交班模式
            	var lsmlfs = qxpublic.charAt(15);//零售抹零方式
            	var usesyt = qxpublic.charAt(16);//零售收银台
            	var usepfsyt = qxpublic.charAt(18);//批发收银台
            	var useyhq = qxpublic.charAt(17);//优惠券
            	var autobarcode = qxpublic.charAt(19);//自动产生条码
            	var jzzdgd = qxpublic.charAt(20);//结账自动勾单
            	var lspoints = qxpublic.charAt(21);//零售金额小数位数
            	var ydsqsb = qxpublic.charAt(22);//移动授权
            	var locked = getValuebyName("LOCKED");
            	locked=="1"?$('#allowuser').bootstrapSwitch('setState', true):$('#allowuser').bootstrapSwitch('setState', false);
            	allowwareinsale=="1"?$('#allowwareinsale').bootstrapSwitch('setState', true):$('#allowwareinsale').bootstrapSwitch('setState', false);
            	qxkz=="1"?$('#qxkz').bootstrapSwitch('setState', true):$('#qxkz').bootstrapSwitch('setState', false);
            	allowfsck=="1"?$('#allowfsck').bootstrapSwitch('setState', true):$('#allowfsck').bootstrapSwitch('setState', false);
            	usesyt=="1"?$('#usesyt').bootstrapSwitch('setState', true):$('#usesyt').bootstrapSwitch('setState', false);
            	usepfsyt=="1"?$('#usepfsyt').bootstrapSwitch('setState', true):$('#usepfsyt').bootstrapSwitch('setState', false);
            	useyhq=="1"?$('#useyhq').bootstrapSwitch('setState', true):$('#useyhq').bootstrapSwitch('setState', false);
            	allownearsale=="1"?$('#allownearsale').bootstrapSwitch('setState', true):$('#allownearsale').bootstrapSwitch('setState', false);
            	phhjh=="1"?$('#psdjh').bootstrapSwitch('setState', true):$('#psdjh').bootstrapSwitch('setState', false);
            	phck=="1"?$('#psdck').bootstrapSwitch('setState', true):$('#psdck').bootstrapSwitch('setState', false);
            	zdcshh=="1"?$('#zdcshh').bootstrapSwitch('setState', true):$('#zdcshh').bootstrapSwitch('setState', false);
            	xszkdpdz=="1"?$('#xsdzdp').bootstrapSwitch('setState', true):$('#xsdzdp').bootstrapSwitch('setState', false);
            	barcodedbl==1?$('#barcodedbl').bootstrapSwitch('setState', true):$('#barcodedbl').bootstrapSwitch('setState', false);
            	autobarcode==1?$('#autobarcode').bootstrapSwitch('setState', true):$('#autobarcode').bootstrapSwitch('setState', false);
            	jzzdgd==1?$('#jzzdgd').bootstrapSwitch('setState', true):$('#jzzdgd').bootstrapSwitch('setState', false);
            	ydsqsb==1?$('#ydsqsb').bootstrapSwitch('setState', true):$('#ydsqsb').bootstrapSwitch('setState', false);
            	$('#points').val(points.toString());
            	$('#lspoints').val(lspoints.toString());
            	$('#barcodefs').val(barcodefs);
            	var pgs = getCookie("PAGECOUNT")==""?"20":getCookie("PAGECOUNT");
            	$('#pages').val(pgs);
            	$('#centbl').val(parseFloat(centbl));
            	$('#classtype').val(classtype);
            	$('#lsmlfs').val(lsmlfs);
            	checked=true;
            	var levelid=getValuebyName("GLEVELID");
            	if(levelid!="0"){
            		$('#form-param :input:not(#pages)').attr("disabled",'disabled');
            		$('#pages').removeAttr("disabled");
            		$('#form-param select:not(.all)').attr('disabled','disabled');
            		$('#qxkz,#allowfsck,#allownearsale,#allowwareinsale,#allowuser,#psdjh,#psdck,#zdcshh,#xsdzdp,#usesyt,#usepfsyt,#useyhq,#barcodedbl,#cgthpdgys,#jzzdgd,#autobarcode').bootstrapSwitch('setActive', false);  // true || false
            		$('#selectcust,#selectcolor,#resetcolor,#resetcust,#allowuser,#allowsuertd').hide();
            	}else{
            		$('#form-param :input').not('#defaultcust').removeAttr("disabled");
            		$('#form-paraml select').removeAttr('disabled');
            		$('#qxkz,#allowfsck,#allownearsale,#allowwareinsale,#allowuser,#psdjh,#psdck,#zdcshh,#xsdzdp,#usesyt,#usepfsyt,#useyhq,#barcodedbl,#cgthpdgys,#jzzdgd,#autobarcode').bootstrapSwitch('setActive', true);  // true || false
            		$('#selectcust,#resetcolor,#resetcust,#allowuser,#allowsuertd').show();
            	}
            	changeqx = false;
        	}
        }
	});
}
//修改参数
function updateparam(location,value){
	alertmsg(2);
	var levelid=getValuebyName("GLEVELID");
	if(levelid=="0"){
		$.ajax({
		 	type: "POST",   //访问WebService使用Post方式请求 
	        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名             
	        data:{erpser:"writeqxpublic",locate:location,value:value},
	        dataType: 'json', 
	        success: function(data) { 
	        	if(valideData(data)){
	        		alerthide();
	        		alerttext("修改成功！");
	        		changeqx = true;
	        	}else{
	        		setTimeout('getqxpublic()', 1000);
	        	}
	        }
		});
	}else{
		alerthide();
		alerttext("只有系统管理员有权限修改！")
	}
}
function setallowuser(value){
	alertmsg(2);
	var levelid=getValuebyName("GLEVELID");
	if(levelid=="0"){
		$.ajax({
		 	type: "POST",   //访问WebService使用Post方式请求 
	        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名             
	        data:{erpser:"changeaccregusetag",usetag:value},
	        dataType: 'json', 
	        success: function(data) { 
	        	if(valideData(data)){
	        		alerttext("修改成功！");
	        		changeqx = true;
	        	}else{
	        		alerttext(text);
	        	}
	        }
		});
		}else{
			alerthide();
			alerttext("只有系统管理员有权限修改！")
		}
}
function closemodal(){
	if(changeqx){
		alert("参数发送变化，为了不影响您正常使用，修改之后，请重新登录！");
		location.href="loginservlet?doservlet=logoff";
	}
}
function getuserinfo(){
	alertmsg(6);
	alertmsg(6);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名                
        dataType: 'json', 
        data: {
        	erpser: "getemployebyid",
        	epid: getValuebyName("EPID")
        },
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		var rows = data.rows;
        		var epno = rows[0].EPNO;
        		var epname = rows[0].EPNAME;
        		var sex = rows[0].SEX;
        		var mobile = rows[0].MOBILE;
        		var tel = rows[0].TEL;
        		var postcode = rows[0].POSTCODE;
        		var address = rows[0].ADDRESS;
        		var idno  = rows[0].IDNO;
        		$('#epno').val(epno);
        		$('#epname').val(epname);
        		$('#tel').val(tel);
        		$('#mobile').val(mobile);
        		$('#address').val(address);
        		$('#postcode').val(postcode);
        		$('#idno').val(idno);
        		$('#sex').val(sex);
        	}
        }   
	});
}
function updateuserinfo(){        
	var epname = $('#epname').val();     
	var tel = $('#tel').val();
	var mobile = $('#mobile').val();     
	var address = $('#address').val();   
	var postcode = $('#postcode').val();
	var idno =$('#idno').val();
	var sex = $('#sex').val();
	if(epname==''){
		alerttext('用户名不能为空！');
	}else{
		$.ajax({
		 	type: "POST",   //访问WebService使用Post方式请求 
	        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名  
	        data: {erpser:"updateemployebyid",epid:getValuebyName("EPID"),epname:epname,sex:sex,mobile:mobile,postcode:postcode,tel:tel,address:address,idno:idno},
	        dataType: 'json', 
	        success: function(data) {   
	        	if(valideData(data)){
	        		$('#userinfo').html(epname);
	        		alerttext('修改用户信息成功！');
	        		$('#myModal').modal('hide');
	        	}
	        }
		});
	}
}
function getcalcdate(){
	alertmsg(6);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {
        	erpser : "getaccregbyid",
        	accid: getValuebyName("ACCID"),
        	fieldlist: "accid,accname,regdate,mobile,company,linkman,address,email,tel,begindate,to_char(calcdate,'yyyy-mm-dd') as calcdate"
        	},
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		var rows = data.rows;
        		var calcdate = rows[0].CALCDATE;
        		var ed = new Date(calcdate.replace(/-/g,"/"));
        		var edate = new Date(ed.setDate(ed.getDate()+1)).Format("yyyy-MM-dd");
        		$("#calcdate").datebox("setValue",calcdate);
        		$("#startdate").datebox("setValue",edate);
        	}
        }   
	});
}
//修改登账日期
function setcalcdate(){
	var calcdate  = $('#calcdate').datebox('getValue');
	var td = new Date();
	var tdate = new Date(td.setDate(td.getDate()-1)).Format("yyyy-MM-dd");
	if(tdate<=calcdate){
		alerttext("登账日期需要比今日登账日期"+tdate+"小！");
	}else if(confirm("你确定将今日登账日期改为“"+calcdate+"”吗？确定后将允许更改此日期之后的单据！")){
		alertmsg(2);
		$.ajax({
			type: "POST",   //访问WebService使用Post方式请求 
	        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
		    data: {erpser: "updateaccregbyid",calcdate: calcdate,accid: getValuebyName("ACCID")},
			dataType: 'json', 
			success: function(data) {     //回调函数，result，返回值 
		       	if(valideData(data)){
		       		alert('更改今日登账日期成功！');
// 		       		top.window.location.href="loginservlet?doservlet=logoff";
		       	}
		       }   
		});
	}
}
//数据日结
function dailysjwh(){
	var tdate = new Date().Format("yyyy-MM-dd");
	var sdate = $("#calcdate").datebox("getValue");
	var ed = new Date(sdate);
	var edate = new Date(ed.setDate(ed.getDate()+1)).Format("yyyy-MM-dd");
	if(tdate==edate){
		alerttext("登账日期已经是最新日期！");
	}else if(confirm("你确定对"+edate+"的日期进行日结！日结登账结束,登账日期将改为"+edate)){
		alertmsg(2,5*60*1000);
		$("#ggdzrModal button.btn").prop("disabled",true);
		$.ajax({
			type : "POST", //访问WebService使用Post方式请求 
			url : "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
			data : {
				erpser: "totalaccsum",
				nowdate : edate
			}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
			dataType: 'json',
			success: function(data) { //回调函数，result，返回值 
				try{
					if(valideData(data)){
						alert("日结登账结束,登账日期改为"+edate);
						$("#calcdate").datebox("setValue",edate);
						$("#ggdzrModal button.btn").removeProp("disabled");
					}
				}catch (e) {
					// TODO: handle exception
					console.log(e.message);
				}
			}
		});
	}
}
function getcominfo(){
	alertmsg(6);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {
        	erpser : "getaccregbyid",
        	accid: getValuebyName("ACCID"),
        	fieldlist: "accid,accname,regdate,mobile,company,linkman,address,email,tel,begindate,calcdate,tag"
        	},
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		var rows = data.rows;
        		var comno = rows[0].ACCNAME;
        		var logtime = rows[0].REGDATE0;
        		var begindate = rows[0].BEGINDATE0;
        		var comname = rows[0].COMPANY;
        		var linkman = rows[0].LINKMAN;
        		var mobile = rows[0].MOBILE;
        		var tel = rows[0].TEL;
        		var email = rows[0].EMAIL;
        		var address = rows[0].ADDRESS;
        		var tag = rows[0].TAG;
        		var remark = rows[0].REMARK;
        		var calcdate = rows[0].CALCDATE.substr(0,10);
        		$('#comno').val(comno); 
        		$('#logtime').val(logtime); 
        		$('#begindate').val(begindate); 
        		$('#calctime').val(calcdate); 
        		$("#newbegindate").datebox("setValue",begindate);
        		$('#comname').val(comname);
        		$('#com_linkman').val(linkman);
        		$('#com_tel').val(tel);
        		$('#com_mobile').val(mobile);
        		$('#com_address').val(address);
        		$('#com_email').val(email);
        		$('#com_tag').val(tag);
        		$('#com_remark').val(remark);
        		if(getValuebyName("LEVELID")=='0'){
        			$('#comname').removeAttr('disabled');
        			$('#com_linkman').removeAttr('disabled');
        			$('#com_tel').removeAttr('disabled');
        			$('#com_address').removeAttr('disabled');
        			$('#com_email').removeAttr('disabled');
        			if(tag!=3)
        			$('#com_tag').removeAttr('disabled');
        			else $('#com_tag option.hide').show();
        		}
        	}
        }   
	});
}
function updatecominfo(){
	alertmsg(2);
	var company = $('#comname').val();
	var linkman = $('#com_linkman').val();
	var tel = $('#com_tel').val();
	var mobile = $('#com_mobile').val();
	var address = $('#com_address').val();
	var email = $('#com_email').val();
	var tag = $('#com_tag').val();
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {
        	erpser : "updateaccregbyid",
        	accid: getValuebyName("ACCID"),
        	company : company,
        	linkman : linkman,
        	tel : tel,
        	mobile : mobile,
        	address : address,
        	tag : tag,
        	email : email
        	},
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		alerttext("修改成功");
        	}
        }   
	});
}
function chengbegindate(){
	var date = $('#newbegindate').datebox('getValue');
	var isread = $('#isread').prop('checked');
	if(isread){
		if(confirm("你确定将开工期间改为“"+date+"”吗？确定后将清除“"+date+"”前所有数据，将无法恢复。请您慎重！")){
			alertmsg(2);
			$.ajax({
			 	type: "POST",   //访问WebService使用Post方式请求 
		        url: "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
		        data: {erpser: "changeaccbegindate", begindate: date},
		        dataType: 'json', 
		        success: function(data) {     //回调函数，result，返回值 
		        	if(valideData(data)){
		        		alert('更改建账日期成功！');
		        		top.window.location.href="loginservlet?doservlet=logoff";
		        	}
		        }   
			});
		}
	}else{
		alerttext('请勾选已阅读以上条例！');
	}
}
function sendsuggest(){
	alertmsg(2);
	var suggest = $('#suggest').val();
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "MainServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {mainser : "sendsuggest",suggest : suggest},
        dataType: 'text', 
        success: function(text) {     //回调函数，result，返回值 
        	if(isSucess(text)){
        		alerttext("感谢您为我们提出宝贵的建议！");
        		$('#suggest').val("");
        		$('#sugModal').modal('hide');
        	}
        }   
	});
}
function getsysnotice (page){
	alertmsg(6);
	var suggest = $('#suggest').val();
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "MainServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {mainser : "getsysnotice",page : page},
        dataType: 'text', 
        success: function(text) {     //回调函数，result，返回值 
        	if(isSucess(text)){
        		
        	}
        }   
	});
}
function getdefaultcust(){
	alertmsg(6);
	$('#defaultcust').val("");
	$('#defaultcustid').val('');
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "MainServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {mainser : "getdefaultcust"},
        dataType: 'text', 
        success: function(text) {     //回调函数，result，返回值 
        	if(isSucess(text)){
        		var data = $.parseJSON(text);
        		$('#defaultcust').val(data.CUSTNAME);
        		$('#defaultcustid').val(data.CUSTID);
        	}
        }   
	});
}
function setdefaultcust(custid){
	alertmsg(6);
	//var custid = $('#defaultcustid').val();
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {erpser : "writedefaultcust",custid:custid},
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		alerttext("修改成功！");
        		if(custid==0){
        			$("#defaultcust").val("");
        		}
        		$('.help-box').hide();
        		$('#paramModal').show();
        	}
        }   
	});
}
function writedefaultsize(){
	var groupno = $('#sizegroupno').val();
	alertmsg(2);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {erpser : "writedefaultsize",sizegroupno:groupno},
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		alerttext("修改成功！");
        	}
        }   
	});
}
function writedefaultcolor(colorid){
	alertmsg(2);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {erpser: "writedefaultcolor",colorid:colorid},
        dataType: 'json', 
        success: function(data) {     //回调函数，result，返回值 
        	if(valideData(data)){
        		alerttext("修改成功！");
        		$('.help-box').hide();
        		$('#paramModal').show();
        		if(colorid=='0')
        			$('#defaultcolor').val('');
        	}
        }   
	});
}
function setsysnotice (page){
	var suggest = $('#suggest').val();
	alertmsg(2);
	$.ajax({
	 	type: "POST",   //访问WebService使用Post方式请求 
        url: "MainServlet", //调用WebService的地址和方法名称组合 ---- WsURL/方法名            
        data: {mainser : "setsysnotice",ggid : ggid},
        dataType: 'text', 
        success: function(text) {     //回调函数，result，返回值 
        	if(isSucess(text)){
        		
        	}
        }   
	});
}
function getcustlist(page){
	var findbox = $('#custfindbox').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "customerlisthelp",
			findbox : findbox,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if (valideData(data)){
				var total = data.total;
				var pages = Math.ceil(total/pagecount);
				var rows = data.rows;
				$('#cust-ul').html("");
				for(var i in rows){
					var custid = rows[i].CUSTID;
					var custname = rows[i].CUSTNAME;
					if($('[data-custid='+custid+']').length==0){
						$('#cust-ul').append('<li data-custid="'+custid+'" data-custname="'+custname+'">'+custname+'</li>');
					}
				}
				var pagehtml="";
				if(pages>=2){
					for(var i=1;i<=pages;i++){
						if(i==page){
							pagehtml += '<li class="selected" data-page="'+i+'">'+i+'</li>';
						}else{
							pagehtml += '<li data-page="'+i+'">'+i+'</li>';
						}
					}
				}
				$('#cust-help .help-page').html(pagehtml);
				$('#cust-help .help-page li').click(function(){
					$('#cust-help .help-page .selected').removeClass('.selected');
					$(this).addClass('selected');
					var p = $(this).data('page');
					getcustlist(p);
				});
				$('#cust-help #help-total').html("已显示个"+($('#cust-help .help-body ul li').length)+"客户/总共有"+total+"个客户");
				$('#paramModal').hide();
				$('#cust-help').show();
			} 
		}
	});
}
function getcolorlist(page){
	var findbox = $('#colorfindbox').val();
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "/skydesk/fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		data : {
			erpser : "colorlist",
			findbox : findbox,
			noused: 0,
			rows: pagecount,
			page : page
		}, //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到                          
		dataType : 'json',
		success : function(data) { //回调函数，result，返回值 
			if (valideData(data)){
				var total = data.total;
				var pages = Math.ceil(total/pagecount);
				var rows = data.rows;
				$('#color-ul').html("");
				for(var i in rows){
					var colorid = rows[i].COLORID;
					var colorname = rows[i].COLORNAME;
					if($('[data-colorid='+colorid+']').length==0){
						$('#color-ul').append('<li data-colorid="'+colorid+'" data-colorname="'+colorname+'">'+colorname+'</li>');
					}
				}
				var pagehtml="";
				if(pages>=2){
					for(var i=1;i<=pages;i++){
						if(i==page){
							pagehtml += '<li class="selected" data-page="'+i+'">'+i+'</li>';
						}else{
							pagehtml += '<li data-page="'+i+'">'+i+'</li>';
						}
					}
				}
				$('#color-help .help-page').html(pagehtml);
				
				$('#color-help #help-total').html("共有"+total+"个颜色");
				$('#paramModal').hide();
				$('#color-help').show();
			} 
		}
	});
}
var days = 0;
function sjwh(){
	var sdate = $('#startdate').datebox('getValue').replace(' ','').split('-');
	var edate = $('#enddate').datebox('getValue').replace(' ','').split('-');
	var date1 = new Date();
	var date2 = new Date();
	date1.setFullYear(Number(sdate[0]), Number(sdate[1])-1, Number(sdate[2]));
	date2.setFullYear(Number(edate[0]), Number(edate[1])-1, Number(edate[2]));
	var todaydate = new Date();
	var istoday = Math.floor((todaydate.getTime()-date2.getTime())/(24*3600*1000));
	var date3 = date2.getTime()- date1.getTime();
	var days = Math.floor(date3/(24*3600*1000));
	if(days>90){
		alert('选择的开始结束日期不能相差90天以上！');
		var newdate = new Date(date2.valueOf()-90*24*60*60*1000);
		$('#startdate').datebox('setValue',newdate.toLocaleString());
	}else if(istoday<0){
		alert('结束日期不能大于今天！');
		$('#enddate').datebox('setValue',todaydate.toLocaleString());
	}else{
		$('#sjwhbtn').attr('disabled',true);
		$('#sjwhmsg').html('');
		$('#sjwhmsg').append('<li id="sjwhmsg-li">数据汇总中……</li>');
		getsjwh(date1,date2);
	}
}
function getsjwh(sdate,edate){
	var y = sdate.getFullYear().toString();
	var m = (sdate.getMonth()+1).toString();
	m = m.length==1?'0'+m:m;
	var d = sdate.getDate().toString();
	d = d.length==1?'0'+d:d;
	var date = y +'-' + m + '-' + d;
	$.ajax({
		type : "POST", //访问WebService使用Post方式请求 
		url : "fyjerp", //调用WebService的地址和方法名称组合 ---- WsURL/方法名 
		timeout: 30 * 60 * 1000,
		data : {
			erpser: "totalaccsum",
			timeout: 30*60*1000,
			nowdate : date
		}, //这里是要传递的参数，格式为 data: "{paraName: paraValue}",下面将会看到                          
		dataType: 'json',
		success: function(data) { //回调函数，result，返回值 
			try{
				if(valideData(data)){
					$('#sjwhmsg').append('<li>'+date+':数据汇总完毕！</li>');
				}else{
	        		$('#sjwhmsg').append('<li>'+date+':数据汇总异常：'+data.msg+'</li>');
	        	}
				var newdate =  new Date(sdate.valueOf()+1*24*60*60*1000);
			    var iscontinue = Math.floor((edate.getTime()-newdate.getTime())/(24*3600*1000));
			    if(iscontinue>=0)
			    	getsjwh(newdate,edate);
			    else{
			    	$('#sjwhmsg').append('<li>数据汇总完毕！</li>');
			    	$('#sjwhbtn').removeAttr('disabled');
			    	alert("数据汇总完毕！");
			    	$('#sjwhModal').modal('hide');
			    }
			}catch (e) {
				// TODO: handle exception
				console.log(e.message);
			}
		}
	});
}
</script>