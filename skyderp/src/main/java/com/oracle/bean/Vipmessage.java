package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.JGPush;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-20 16:32:49
//*************************************
public class Vipmessage implements java.io.Serializable {
	private Float Messid;
	private Long Accid;
	private Long Vipid;
	private Long Onhouseid;
	private String Remark;
	private Integer Rdbj;
	private Integer Statetag;
	private Integer Lx;
	private Integer Jg;
	private Date Lastdate;
	private String Lastop;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setMessid(Float messid) {
		this.Messid = messid;
	}

	public Float getMessid() {
		return Messid;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRdbj(Integer rdbj) {
		this.Rdbj = rdbj;
	}

	public Integer getRdbj() {
		return Rdbj;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setLx(Integer lx) {
		this.Lx = lx;
	}

	public Integer getLx() {
		return Lx;
	}

	public void setJg(Integer jg) {
		this.Jg = jg;
	}

	public Integer getJg() {
		return Jg;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" update vipmessage set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where messid=" + Messid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append(JSONObject jsonObject, String onhousename) {

		if (Onhouseid == 0) {
			errmess = "缺少线上店铺参数！";
			return 0;
		}
		if (Remark.equals("")) {
			errmess = "消息内容不允许为空！";
			return 0;
		}
		if (!jsonObject.has("vipidlist")) {
			errmess = "接收消息会员参数无效！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("vipidlist");
		int count = jsonArray.size();
		String vipidlist = "^";
		String vipaliaslist = "";

		String qry = "declare ";
		qry += "\n   v_id number; ";
		qry += "\n begin ";
		for (int i = 0; i < count; i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long vipid = Long.parseLong(jsonObject2.getString("vipid"));
			if (!vipidlist.contains("^" + vipid + "^")) {
				// lx:0=通知，1=消费请求
				qry += "\n   insert into vipmessage (messid,vipid,onhouseid,statetag,remark,lx,lastdate,lastop)";
				qry += "\n   values (vipmessage_messid.nextval," + vipid + "," + Onhouseid + ",1,'" + Remark + "',0,sysdate,'" + Lastop + "');";
				vipidlist += vipid + "^";
				vipaliaslist += "vip" + vipid + "^";
			}
		}

		// qry += " select v_id into :id from dual; ";
		qry += "\n end; ";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		if (count > 0)
			JGPush.PushToVip(vipaliaslist, "viptag" + Onhouseid, Remark + "【" + onhousename + "】", Onhouseid, 0, 0);
		errmess = "操作成功！";
		return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update vipmessage set ");
		if (Messid != null) {
			strSql.append("messid='" + Messid + "',");
		}
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Rdbj != null) {
			strSql.append("rdbj=" + Rdbj + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lx != null) {
			strSql.append("lx=" + Lx + ",");
		}
		if (Jg != null) {
			strSql.append("jg=" + Jg + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM vipmessage ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM vipmessage a join vipgroup b on a.vipid=b.vipid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from vipmessage");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 店铺零售前台给会员发送消费通知
	public int doSendmess(String noteno, int newask, long vcpid, int bj) {
		// bj:0=店铺零售，1=商场零售

		if (noteno.equals("")) {
			errmess = "销售单号无效！";
			return 0;
		}
		String qry = "declare";
		qry += "\n    v_vipid  number(10);";
		qry += "\n    v_messid  number;";
		qry += "\n    v_guestid  number;";
		qry += "\n    v_houseid  number;";
		qry += "\n    v_onhouseid  number(10);";
		qry += "\n    v_onhousename  varchar2(100);";
		qry += "\n    v_retcs  varchar2(200);";
		// qry += "\n v_lx number(1);";
		qry += "\n    v_jg  number(1);";
		// qry += "\n v_statetag number(1);";
		qry += "\n begin ";
		qry += "\n    v_messid:=0;v_houseid:=0;v_guestid:=0;v_onhousename:='';";
		if (newask == 1) { // 如果以前已发了通知，将原通知设为无效
			qry += "\n    begin";
			if (bj == 0) {
				qry += "\n       select messid into v_messid from wareouth where noteno='" + noteno + "' and accid=" + Accid + ";";
				qry += "\n       update wareouth set messid=0 where  noteno='" + noteno + "' and accid=" + Accid + ";";
			} else {
				qry += "\n       select messid into v_messid from shopsaleh where noteno='" + noteno + "' and accid=" + Accid + ";";
				qry += "\n       update shopsaleh set messid=0 where  noteno='" + noteno + "' and accid=" + Accid + ";";
			}
			qry += "\n       update vipmessage set jg=3 where messid=v_messid and jg<>3;";
			qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n       v_messid:=0;";
			qry += "\n    end;";
		}
		qry += "\n    begin";

		if (bj == 0)
			qry += "\n       select messid,houseid,guestid into v_messid,v_houseid,v_guestid from wareouth where noteno='" + noteno + "' and accid=" + Accid + ";";
		else
			qry += "\n       select messid,houseid,guestid into v_messid,v_houseid,v_guestid from shopsaleh where noteno='" + noteno + "' and accid=" + Accid + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n       v_retcs:= '0未找到销售单！';";// ,0,0,0,'' into :retcs,:messid,:vipid,:onhouseid,:onhousename from dual;";
		qry += "\n       goto exit;";
		qry += "\n    end;";

		qry += "\n    if v_houseid=0 then";
		qry += "\n       v_retcs:= '0未找到店铺档案1！';";
		qry += "\n       goto exit;";
		qry += "\n    end if;";
		qry += "\n    if v_guestid=0 then";
		qry += "\n       v_retcs:= '0当前销售单没有会员的消费记录！';";
		qry += "\n       goto exit;";
		qry += "\n    end if;";

		qry += "\n    begin";
		qry += "\n       select vipid into v_vipid from guestvip where guestid=v_guestid and accid=" + Accid + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n       v_retcs:= '0未找到会员档案！';";
		qry += "\n       goto exit;";
		qry += "\n    end;";
		qry += "\n    if v_vipid=0 then";
		qry += "\n       v_retcs:= '0当前会员未建立APP账号，请先登录APP建立个人账号！';";
		qry += "\n       goto exit;";
		qry += "\n    end if;";
		qry += "\n    begin";
		qry += "\n       select a.onhouseid,b.onhousename into v_onhouseid,v_onhousename ";
		qry += "\n       from warehouse a join onlinehouse b on a.onhouseid=b.onhouseid  where a.houseid=v_houseid and a.accid=" + Accid + ";";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n       v_retcs:= '0未找到会员店铺资料！';";
		qry += "\n       goto exit;";
		qry += "\n    end;";
		qry += "\n    if v_onhouseid=0 then";
		qry += "\n       v_retcs:= '0当前店铺未建立对应的线上店铺，不允许会员消费！';";
		qry += "\n       goto exit;";
		qry += "\n    end if;";

		qry += "\n    if v_messid>0 then";
		// jg:0=未答，1=同意，2=拒绝，3=失效
		qry += "\n       select jg into v_jg from vipmessage where messid=v_messid; ";
		qry += "\n       if v_jg=0 then";
		qry += "\n          v_retcs:= '0当前消费通知已发送给会员，请耐心等候！';";
		qry += "\n          goto exit;";
		qry += "\n       end if;";
		qry += "\n       if v_jg=1 then";
		qry += "\n          v_retcs:= '2会员同意请求！';";
		qry += "\n          goto exit;";
		qry += "\n       end if;";
		qry += "\n       if v_jg=2 then";
		qry += "\n          v_retcs:= '3会员拒绝请求！';";
		qry += "\n          goto exit;";
		qry += "\n       end if;";
		qry += "\n       if v_jg=3 then";
		qry += "\n          v_retcs:= '4请求已失效！';";
		qry += "\n          goto exit;";
		qry += "\n       end if;";
		qry += "\n   end if;";
		// lx:0=通知，1=消费请求
		qry += "\n   v_messid:=vipmessage_messid.nextval;";
		qry += "\n   insert into vipmessage (messid,vipid,onhouseid,statetag,remark,lx,lastdate,lastop)";
		qry += "\n   values (v_messid,v_vipid,v_onhouseid ,1,'" + Remark + "'," + Lx + ",sysdate,'" + Lastop + "');";
		if (bj == 0)
			qry += "\n   update wareouth set messid=v_messid where accid=" + Accid + " and noteno='" + noteno + "';";
		else
			qry += "\n   update shopsaleh set messid=v_messid where accid=" + Accid + " and noteno='" + noteno + "';";
		qry += "\n   v_retcs:='1发送请求成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs,v_messid,v_vipid,v_onhouseid,v_onhousename into :retcs,:messid,:vipid,:onhouseid,:onhousename from dual;";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("doSendmess PushToVip", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("messid", new ProdParam(Types.FLOAT));
		param.put("vipid", new ProdParam(Types.BIGINT));
		param.put("onhouseid", new ProdParam(Types.BIGINT));
		param.put("onhousename", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		if (retcs.substring(0, 1).equals("1")) {
			Messid = Float.parseFloat(param.get("messid").getParamvalue().toString());
			long onhouseid = Long.parseLong(param.get("onhouseid").getParamvalue().toString());
			String onhousename = param.get("onhousename").getParamvalue().toString();
			String vipidstr = "vip" + param.get("vipid").getParamvalue().toString() + "^";
			String viptagstr = "viptag" + onhouseid;

			Remark += "【" + onhousename + "】";
			JGPush.PushToVip(vipidstr, viptagstr, Remark, onhouseid, Messid, 1);
			// LogUtils.LogDebugWrite("doSendmess PushToVip", vipidstr + "," + viptagstr + "," + onhouseid + " ," + Messid);
		}

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

		// return 1;
	}

	// 给会员发送通知消息
	public int doSendmess1(long houseid, long guestid) {

		if (Remark.equals("")) {
			errmess = "remark不是一个有效！";
			return 0;
		}
		String qry = "declare";
		qry += "\n    v_vipid  number(10);";
		qry += "\n    v_messid  number;";
		qry += "\n    v_guestid  number;";
		qry += "\n    v_houseid  number;";
		qry += "\n    v_onhouseid  number(10);";
		qry += "\n    v_onhousename  varchar2(100);";
		qry += "\n    v_retcs  varchar2(200);";
		// qry += "\n v_lx number(1);";
		qry += "\n    v_jg  number(1);";
		// qry += "\n v_statetag number(1);";
		qry += "\n begin ";
		qry += "\n   v_messid:=0;v_guestid:=0;v_onhousename:='';";
		qry += "\n   v_guestid:=" + guestid + ";";
		qry += "\n   v_houseid:=" + houseid + ";";
		qry += "\n   begin";
		qry += "\n     select vipid into v_vipid from guestvip where guestid=v_guestid and accid=" + Accid + ";";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:= '0未找到会员档案！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_vipid=0 then";
		qry += "\n      v_retcs:= '0当前会员未建立APP账号，请先登录APP建立个人账号！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		qry += "\n   begin";
		qry += "\n     select a.onhouseid,b.onhousename into v_onhouseid,v_onhousename ";
		qry += "\n     from warehouse a join onlinehouse b on a.onhouseid=b.onhouseid where a.houseid=v_houseid and a.accid=" + Accid + ";";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0未找到会员店铺资料！';";
		qry += "\n      goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_onhouseid=0 then";
		qry += "\n      v_retcs:= '0当前店铺未建立对应的线上店铺，不能发送消息！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		// lx:0=通知，1=消费请求
		qry += "\n   v_messid:=vipmessage_messid.nextval;";
		qry += "\n   insert into vipmessage (messid,vipid,onhouseid,statetag,remark,lx,lastdate,lastop)";
		qry += "\n   values (v_messid,v_vipid,v_onhouseid ,1,'" + Remark + "',0,sysdate,'" + Lastop + "');";
		qry += "\n   v_retcs:='1发送请求成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs,v_messid,v_vipid,v_onhouseid,v_onhousename into :retcs,:messid,:vipid,:onhouseid,:onhousename from dual;";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("doSendmess PushToVip", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("messid", new ProdParam(Types.FLOAT));
		param.put("vipid", new ProdParam(Types.BIGINT));
		param.put("onhouseid", new ProdParam(Types.BIGINT));
		param.put("onhousename", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		if (retcs.substring(0, 1).equals("1")) {
			Onhouseid = Long.parseLong(param.get("onhouseid").getParamvalue().toString());
			Vipid = Long.parseLong(param.get("vipid").getParamvalue().toString());
			if (Vipid > 0 && Onhouseid > 0) {
				Messid = Float.parseFloat(param.get("messid").getParamvalue().toString());
				String onhousename = param.get("onhousename").getParamvalue().toString();
				String vipidstr = "vip" + Vipid + "^";
				String viptagstr = "viptag" + Onhouseid;
				Remark += "【" + onhousename + "】";
				JGPush.PushToVip(vipidstr, viptagstr, Remark, Onhouseid, Messid, 0);
			}
		}

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

		// return 1;
	}

}