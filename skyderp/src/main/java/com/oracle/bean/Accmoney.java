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
import com.common.tool.PinYin;
import com.flyang.main.pFunc;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-24 00:40:51
//*************************************
public class Accmoney implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private Date Notedate;
	private Float Fs;
	private Float Curr;
	private Float Paycurr;
	private String Handno;
	private String Validdate;

	private Integer Usernum = 0;
	private String Remark;
	private String Lastop;
	private Date Lastdate;
	private Long Ly;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	// public void setId(Float id) {
	// this.Id = id;
	// }
	//
	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setFs(Float fs) {
		this.Fs = fs;
	}

	public Float getFs() {
		return Fs;
	}

	public void setCurr(Float curr) {
		this.Curr = curr;
	}

	public Float getCurr() {
		return Curr;
	}

	public void setPaycurr(Float paycurr) {
		this.Paycurr = paycurr;
	}

	public Float getPaycurr() {
		return Paycurr;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	// public String getValiddate() {
	// return Validdate;
	// }
	//
	// public void setValiddate(String validdate) {
	// Validdate = validdate;
	// }

	public Integer getUsernum() {
		return Usernum;
	}

	public void setUsernum(Integer usernum) {
		Usernum = usernum;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public void setLy(Long ly) {
		this.Ly = ly;
	}

	public Long getLy() {
		return Ly;
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
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update accmoney set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		if (Fs != null) {
			strSql1.append("fs,");
			strSql2.append("'" + Fs + "',");
		}
		if (Curr != null) {
			strSql1.append("curr,");
			strSql2.append("'" + Curr + "',");
		}
		if (Paycurr != null) {
			strSql1.append("paycurr,");
			strSql2.append("'" + Paycurr + "',");
		}
		if (Handno != null) {
			strSql1.append("handno,");
			strSql2.append("'" + Handno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Ly != null) {
			strSql1.append("ly,");
			strSql2.append(Ly + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=accmoney_id.nextval; ");
		strSql.append("   insert into accmoney (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			long id = Long.parseLong(param.get("id").getParamvalue().toString());

			return 1;
		} else
			return 0;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update accmoney set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Fs != null) {
			strSql.append("fs='" + Fs + "',");
		}
		if (Curr != null) {
			strSql.append("curr='" + Curr + "',");
		}
		if (Paycurr != null) {
			strSql.append("paycurr='" + Paycurr + "',");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Ly != null) {
			strSql.append("ly=" + Ly + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;

	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM accmoney ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据 OK
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM accmoney a join accreg b on a.accid=b.accid ");
		strSql.append("\n  join mobilearea c on substr(b.mobile,1,7)=c.mobile ");
		strSql.append("\n  left outer join employe d on b.handmanid=d.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 OK
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM accmoney a join accreg b on a.accid=b.accid ");
		strSql.append("\n  join mobilearea c on substr(b.mobile,1,7)=c.mobile ");
		strSql.append("\n  left outer join employe d on b.handmanid=d.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
		// return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 OK
	public Table GetTable1(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM accmoney a  ");
		// strSql.append("\n left outer join employe d on b.handmanid=d.epid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setSumString("nvl(sum(curr),0) as totalcurr");
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from accmoney");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	public int Adduser(String accname, int num) {
		// System.out.println("Recharge 1");
		if (accname.equals("")) {
			errmess = "充值账户无效！";
			return 0;
		}
		if (num == 0) {
			errmess = "用户数无效！";
			return 0;
		}
		if (Paycurr == null)
			Paycurr = (float) 0;
		if (Remark == null || Remark.equals("")) {
			errmess = "备注无效！";
			return 0;
		}
		accname = accname.toUpperCase();
		// System.out.println("Recharge 2");

		String qry = "declare ";
		qry += "\n    v_accid number(10); ";
		qry += "\n    v_balcurr number; ";
		qry += "\n    v_curr number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_succtag number(1); ";
		qry += "\n    v_lytag number(1); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n   begin ";
		qry += "\n     select accid,lytag,succtag into v_accid,v_lytag,v_succtag from accreg where accname='" + accname + "'; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n     v_retcs:= '0未找到账户:" + accname + "'; ";
		qry += "\n     goto exit;";
		qry += "\n   end; ";
		qry += "\n   v_curr:=0;";
		qry += "\n   if v_lytag=0 then ";
		qry += "\n      v_curr:= " + Paycurr + ";";
		qry += "\n   end if;";
		qry += "\n   insert into accmoney (id,accid,fs,notedate,paycurr,curr,remark,handno,lastdate,lastop) ";
		qry += "\n   values (accmoney_id.nextval,v_accid,0,sysdate," + Paycurr + ",v_curr,'" + Remark + "','',sysdate,'" + Lastop + "'); ";
		qry += "\n   select nvl(sum(curr),0) into v_balcurr from accmoney where accid=v_accid; ";
		qry += "\n   select nvl(sum(curr),0) into v_balcurr from accmoney where accid=v_accid; ";
		qry += "\n   update accreg set usernum=usernum+" + num + ",balcurr=v_balcurr  where accid=v_accid; ";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual; ";
		qry += "\n end; ";
		// System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作失败!";
			return 0;
		}
		// 取返回值
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1"))
			return 1;
		else
			return 0;
	}

	public int Recharge(String accname, int lytag, long levelid) {
		// System.out.println("Recharge 1");
		if (accname.equals("")) {
			errmess = "充值账户无效！";
			return 0;
		}

		// lytag:0=蓝窗充值,1=蓝窗买断,2=上下衣
		// int lytag = 0;
		// if (Usernum > 0) //用户数大于0，表示买断
		// lytag = 1;

		// System.out.println("Recharge 1.2");
		if (Paycurr == null)
			Paycurr = (float) 0;
		//lytag:0=充值，1=买断，2=加站点

		if (lytag == 2) {//2=加站点
			if (Usernum == null || Usernum == 0) {
				errmess = "增加的用户数无效！";
				return 0;
			}
			if (Paycurr == 0) {
				errmess = "金额无效！";
				return 0;
			}
			Curr = Paycurr;

		} else if (lytag == 1) { //1= 买断
			if (Usernum == null || Usernum == 0) {
				errmess = "用户数无效！";
				return 0;
			}

			if (Paycurr == 0) {
				errmess = "买断金额无效！";
				return 0;
			}
			Curr = Paycurr;
			if (Validdate == null || Validdate.equals("")) {// 买断用户要输入有效期
				// errmess = "有效期无效！";
				// return 0;
				Validdate = Func.DateToStr(Func.getDataAdd(pFunc.getServerdatetime(), 365), "yyyy-MM-dd");
			}
		} else {// 0=充值
			// Usernum = 0;
			if (Curr == null || Curr == 0) {
				errmess = "枫杨果无效！";
				return 0;
			}
			// levelid: 0=系统管理员,1=店员,2=店长,3=财务,4=经理,5=老板,6=督导,7=收银员,8=AD客服,9=库管
			// levelid: 0=系统管理员,1=店员（代理业务员）,2=店长（代理业务主管）,3=财务,4=经理,5=老板,6=督导（公司主管）,7=收银员（公司业务）,8=AD客服,9=库管(程序员)
			if (levelid == 8 && Paycurr == 0 && Curr > 100) {
				errmess = "客服最多只能赠送100个枫杨果！";
				return 0;
			}
		}
		if (Remark == null || Remark.equals("")) {
			errmess = "备注无效！";
			return 0;
		}
		accname = accname.toUpperCase().trim();
		// System.out.println("Recharge 2");

		String qry = "declare ";
		qry += "\n    v_accid number(10); ";
		qry += "\n    v_balcurr number; ";
		qry += "\n    v_count number; ";
		qry += "\n    v_succtag number(1); ";
		qry += "\n    v_lytag number(1); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n begin ";
		qry += "\n  begin ";
		qry += "\n    select accid,lytag,succtag into v_accid,v_lytag,v_succtag from accreg where accname='" + accname + "'; ";
		if (lytag < 2) {
			qry += "\n    if v_lytag>0 then ";// 如果用户已是买断，不允许充值
			qry += "\n       v_retcs:= '0账户:" + accname + "已买断使用，不允许继续充值或买断'; ";
			qry += "\n       goto exit;";
			qry += "\n    end if;";
		}
		qry += "\n    insert into accmoney (id,accid,fs,notedate,paycurr,curr,remark,handno,lastdate,lastop) ";
		qry += "\n    values (accmoney_id.nextval,v_accid,0,sysdate," + Paycurr + "," + Curr + ",'" + Remark + "','',sysdate,'" + Lastop + "'); ";
		qry += "\n    select nvl(sum(curr),0) into v_balcurr from accmoney where accid=v_accid; ";
		qry += "\n    update accreg set balcurr=v_balcurr ";
		if (Paycurr > 0 && lytag < 2)
			qry += ",succtag=2";
		qry += "\n    where accid=v_accid; ";
		if (lytag == 2) {//2=加站点
			qry += "\n  update accreg set usernum=usernum+" + Usernum + " where accid=v_accid;";
			qry += "\n  select usernum into v_count from accreg where accid=v_accid;";

			qry += "\n  v_retcs:= '1加站点成功,用户数:'||v_count;";

		} else if (lytag == 1) {//1= 买断用户

			qry += "\n  update accreg set usernum=" + Usernum + ",succtag=2,lytag=1,validdate=to_date('" + Validdate + "','yyyy-mm-dd') where accid=v_accid;";
			qry += "\n  select count(*) into v_count from employe where  accid=v_accid and statetag=1 and passkey=1;";
			qry += "\n  if v_count>" + Usernum + " then ";// 如果已登记的用户数大于注册用户数，则减少用户数
			// qry += "\n update employe set passkey=0 where accid=v_accid and statetag=1 and levelid>0;";
			// 把多余的用户登录标志设为0 passkey=0

			qry += "\n     update employe a set passkey=0 where a.statetag=1 and a.accid=v_accid and a.passkey=1 ";
			qry += "\n       and epid not in (";
			qry += "\n 	     select epid from (select epid,epname from employe  where accid=v_accid and passkey=1  and statetag=1 order by levelid,epid) ";
			qry += "\n 	     where rownum<=" + Usernum + " );";

			qry += "\n  end if;";
			qry += "\n  v_retcs:= '1买断成功,用户数:" + Usernum + "';";
		} else {// 0=充值
			if (Paycurr > 0) {
				qry += "\n  if v_succtag=0 then"; // 第一次充值加3个用户
				qry += "\n     update accreg set usernum=3,succtag=2 where accid=v_accid;";
				qry += "\n  elsif v_succtag=2 then"; // 如果已充值，再次充值加1个用户
				qry += "\n     update accreg set usernum=usernum+floor(" + Paycurr + "/1000) where accid=v_accid;";
				qry += "\n  end if;";
			}
			qry += "\n  v_retcs:= '1充值成功,当前余额:'||v_balcurr ;";

		}
		qry += "\n    commit;";
		// qry += "\n goto exit;";
		//		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN  ";
		qry += "\n  EXCEPTION when others then";
		qry += "\n    v_retcs:= '0充值失败:'||substr(sqlerrm,1,180); ";
		// qry += "\n goto exit;";
		qry += "\n  end; ";
		qry += "\n   <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual; ";
		qry += "\n end; ";
		// System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作失败!";
			return 0;
		}
		// 取返回值
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("1")) {
			return 1;
		} else {
//			LogUtils.LogDebugWrite("AddAccmoneybynameRec充值失败", qry);
			return 0;
		}
	}

	// 转账
	public int Move(String toaccname) {

		if (toaccname == null || toaccname.equals("")) {
			errmess = "企业账户不允许为空！";
			return 0;
		}
		if (Curr == 0) {
			errmess = "待转账的枫杨果数量无效！";
			return 0;
		}
		// toaccname = toaccname.ToUpper();
		String qry = "declare ";
		qry += "\n    v_toaccid number;";
		qry += "\n    v_balcurr number;";
		qry += "\n    v_accname varchar2(100);";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n    v_qxcs varchar2(10);";
		qry += "\n begin ";
		qry += "\n    begin";
		qry += "\n      select accid into v_toaccid from accreg where accname='" + toaccname + "';";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0未找到企业账户:" + toaccname + "！';";
		qry += "\n      goto exit;";
		qry += "\n    end;";
		qry += "\n    select accname,f_getaccbal(accid),qxcs into v_accname,v_balcurr,v_qxcs from accreg where accid=" + Accid + ";";
		qry += "\n    if v_balcurr<" + Curr + " then";
		qry += "\n      v_retcs:= '0当前企业账户余额不足！' ;";
		qry += "\n      goto exit;";
		qry += "\n    end if;";
		qry += "\n    if SUBSTR(v_qxcs,1,1)<>'1' then";
		qry += "\n      v_retcs:= '0当前企业账户没有转账功能！' ;";
		qry += "\n      goto exit;";
		qry += "\n    end if;";
		// 转出枫杨果 减少
		qry += "\n  begin";
		qry += "\n    insert into accmoney (id,accid,fs,notedate,paycurr,curr,remark,handno,lastdate,lastop) ";
		qry += "\n    values (accmoney_id.nextval," + Accid + ",1,sysdate,0,-" + Curr + ",'转到账户:" + toaccname + "','',sysdate,'" + Lastop + "'); ";
		qry += "\n    select nvl(sum(curr),0) into v_balcurr from accmoney where accid=" + Accid + "; ";
		qry += "\n    update accreg set balcurr=v_balcurr where accid=" + Accid + ";";

		// 转入枫杨果 增加
		qry += "\n    insert into accmoney (id,accid,fs,notedate,paycurr,curr,remark,handno,lastdate,lastop) ";
		qry += "\n    values (accmoney_id.nextval,v_toaccid,0,sysdate,0," + Curr + ",'从账户:'||v_accname||'转入','',sysdate,'" + Lastop + "'); ";
		qry += "\n    select nvl(sum(curr),0) into v_balcurr from accmoney where accid=v_toaccid; ";
		qry += "\n    update accreg set balcurr=v_balcurr where accid=v_toaccid;";
		qry += "\n    v_retcs:= '1操作成功！' ; ";
		qry += "\n    exception  ";
		qry += "\n    when others then  ";
		qry += "\n    begin ";
		qry += "\n      rollback; ";
		qry += "\n      v_retcs:= '0枫杨果转账失败！' ; ";
		qry += "\n    end; ";
		qry += "\n  end; ";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		//		System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

}