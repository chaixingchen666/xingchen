package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-21 14:51:27
//*************************************
public class Omuser implements java.io.Serializable {
	private Long Omepid;
	private String Omepno;
	private String Omepname;
	private String Mobile;
	private String Remark;
	private Long Custid;
	private Long Accid;
	private Integer Js; // 0=公司，1=自营店，2=经销商
	private String Password;
	private String Qxcs;
	private Integer Statetag;
	// private Float Jhamt;
	// private Float Jhcurr;
	// private Float Jhnum;
	// private Float Jhskc;

	// private Date Lastdate;
	private String Custname;
	private String errmess = "";
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setOmepid(Long omepid) {
		this.Omepid = omepid;
	}

	public Long getOmepid() {
		return Omepid;
	}

	public void setOmepno(String omepno) {
		this.Omepno = omepno;
	}

	public String getOmepno() {
		return Omepno;
	}

	public void setOmepname(String omepname) {
		this.Omepname = omepname;
	}

	public String getOmepname() {
		return Omepname;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setCustid(Long custid) {
		this.Custid = custid;
	}

	public Long getCustid() {
		return Custid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setJs(Integer js) {
		this.Js = js;
	}

	public Integer getJs() {
		return Js;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPassword() {
		return Password;
	}

	public void setQxcs(String qxcs) {
		this.Qxcs = qxcs;
	}

	public String getQxcs() {
		return Qxcs;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }

	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 订货会截图解锁ok
	public int UnLock() {
		String qry = "update omuser set statetag=1 where accid=" + Accid + " and omepid=" + Omepid;
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 删除记录
	public int Delete() {

		if (Omepid == 0) {
			errmess = "用户id参数无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin");
		strSql.append("\n   select count(*) into v_count from omcustware a where rownum=1 and a.omepid=" + Omepid + ";");
		strSql.append("\n   if v_count>0 then");
		strSql.append("\n      v_retcs:= '0用户已有订货记录，不允许删除!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   delete from omuser where omepid=" + Omepid + " and accid=" + Accid + ";");
		strSql.append("\n   delete from omplan where omepid=" + Omepid + ";");
		strSql.append("\n   v_retcs:= '1操作成功!';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end;");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);

		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select count(*) as num from (");
		// // strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update omuser set statetag=2,lastdate=sysdate");
		// strSql.append(" where omepid=" + Omepid + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// }
		// return 1;
	}

	// 增加订货会用户记录
	public int Append(long omid, Omplan omplan) {

		if (Omepno.equals("ADMIN")) {
			errmess = "ADMIN是系统内置管理员账号，请重新输入！";
			return 0;
		}
		if (Omepname == null || Omepname.equals("")) {
			errmess = "请输入用户姓名！";
			return 0;

		}
		if (Func.getLength(Omepname) > 10) {
			errmess = "用户姓名不允许超过10个字符！";
			return 0;
		}

		if (Mobile == null)
			Mobile = "";
		if (Remark == null)
			Remark = "";
		if (Js == null)
			Js = 0;
		Omepno = Omepno.toUpperCase();
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n    v_omepid number(10);  ");
		strSql.append("\n    v_count number;  ");
		strSql.append("\n    v_retcs varchar2(200);  ");
		strSql.append("\n begin ");
		if (!Omepno.equals("")) {
			strSql.append("\n    select count(*) into v_count from omuser where accid=" + Accid + " and omepno='" + Omepno + "' and rownum=1;");
			strSql.append("\n    if v_count>0 then");
			strSql.append("\n       v_retcs:= '0用户账号:" + Omepno + " 已存在,请重新输入！';");
			strSql.append("\n       goto exit;");
			strSql.append("\n    end if;");
		}
		strSql.append("\n    v_omepid:=omuser_omepid.nextval;");
		strSql.append("\n    insert into omuser (omepid,omepno,omepname,custid,mobile,remark,statetag,accid,js,password)");
		strSql.append("\n    values (v_omepid,'" + Omepno + "','" + Omepname + "'," + Custid + ",'" + Mobile + "','" + Remark + "'," + Statetag + "," + Accid + "," + Js + ",'" + Password + "');");
		strSql.append("\n    select count(*) into v_count from omuser where accid=" + Accid + " and omepno='ADMIN';");
		strSql.append("\n    if v_count=0 then");
		strSql.append("\n       insert into omuser (omepid,omepno,omepname,custid,mobile,remark,statetag,accid,js,password)");
		strSql.append("\n       values (omuser_omepid.nextval,'ADMIN','管理员',0,'','',1," + Accid + ",0,'123456');"); // 123456 md5 6BAD136AEF6902EB0D3C0E7666DD2DB7
		strSql.append("\n    end if;");
		if (omid > 0) // 保存订货会计划指标
		{
			Float jhamt = omplan.getJhamt();
			Float jhcurr = omplan.getJhcurr();
			Long jhnum = omplan.getJhnum();
			Long jhskc = omplan.getJhskc();
			String lastop = omplan.getLastop();
			if (jhamt == null)
				jhamt = (float) 0;
			if (jhcurr == null)
				jhcurr = (float) 0;
			if (jhnum == null)
				jhnum = (long) 0;
			if (jhskc == null)
				jhskc = (long) 0;
			strSql.append("\n    select count(*) into v_count from omplan where omid=" + omid + " and omepid=v_omepid;");
			strSql.append("\n    if v_count=0 then");
			strSql.append("\n       insert into omplan (omepid,omid,jhamt,jhcurr,jhskc,jhnum,lastop)");
			strSql.append("\n       values (v_omepid," + omid + "," + jhamt + "," + jhcurr + "," + jhskc + "," + jhnum + ",'" + lastop + "');");
			strSql.append("\n    else");
			strSql.append("\n       update omplan set lastdate=sysdate,lastop='" + lastop + "'");
			strSql.append("\n       ,jhamt=" + jhamt + ",jhcurr=" + jhcurr + ",jhskc=" + jhskc + ",jhnum=" + jhnum);
			strSql.append("\n       where omid=" + omid + " and omepid=v_omepid;");
			strSql.append("\n    end if;");
		}
		strSql.append("\n   v_retcs:= '1'||v_omepid ;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end; ");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		if (retcs.substring(0, 1).equals("0")) {
			errmess = retcs.substring(1);
			return 0;
		} else {
			Omepid = Long.parseLong(retcs.substring(1));
			errmess = Omepid.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update(long omid, Omplan omplan) {

		if (Omepid == 0) {
			errmess = "用户id参数无效！";
			return 0;
		}
		if (Omepno != null && Omepno.equals("ADMIN")) {
			errmess = "ADMIN是系统内置管理员账号，请重新输入！";
			return 0;
		}
		if (Omepname != null)
			if (Func.getLength(Omepname) > 10) {
				errmess = "用户姓名不允许超过10个字符！";
				return 0;
			}

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n    v_omepid number(10);  ");
		strSql.append("\n    v_count number;  ");
		strSql.append("\n    v_retcs varchar2(200);  ");
		strSql.append("\n begin ");
		if (Omepno != null && !Omepno.equals("")) {
			strSql.append("\n   select count(*) into v_count from omuser where accid=" + Accid + " and omepno='" + Omepno + "' and omepid<>" + Omepid + ";");
			strSql.append("\n   if v_count>0 then");
			strSql.append("\n      v_retcs:= '0用户账号:" + Omepno + " 已存在,请重新输入！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n   update omuser set ");
		if (Omepno != null) {
			strSql.append("omepno='" + Omepno + "',");
		}
		if (Omepname != null) {
			strSql.append("omepname='" + Omepname + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Js != null) {
			strSql.append("js=" + Js + ",");
		}
		if (Password != null) {
			strSql.append("password='" + Password.toUpperCase() + "',");
		}
		if (Qxcs != null) {
			strSql.append("qxcs='" + Qxcs + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where omepid=" + Omepid + " and accid=" + Accid + " ;");

		if (omid > 0) // 保存订货会计划指标
		{
			Float jhamt = omplan.getJhamt();
			Float jhcurr = omplan.getJhcurr();
			Long jhnum = omplan.getJhnum();
			Long jhskc = omplan.getJhskc();
			String lastop = omplan.getLastop();

			String insql1 = "omepid,omid,lastop";
			String insql2 = Omepid + "," + omid + ",'" + lastop + "'";
			String upsql = "lastdate=sysdate,lastop='" + lastop + "'";

			if (jhamt != null) {
				insql1 += ",jhamt";
				insql2 += "," + jhamt;
				upsql += ",jhamt=" + jhamt;
			}
			if (jhcurr != null) {
				insql1 += ",jhcurr";
				insql2 += "," + jhcurr;
				upsql += ",jhcurr=" + jhcurr;
			}

			if (jhskc != null) {
				insql1 += ",jhskc";
				insql2 += "," + jhskc;
				upsql += ",jhskc=" + jhskc;
			}
			if (jhnum != null) {
				insql1 += ",jhnum";
				insql2 += "," + jhnum;
				upsql += ",jhnum=" + jhnum;
			}

			strSql.append("\n    select count(*) into v_count from omplan where omid=" + omid + " and omepid=" + Omepid + ";");
			strSql.append("\n    if v_count=0 then");
			strSql.append("\n       insert into omplan (" + insql1 + ") values (" + insql2 + ");");
			strSql.append("\n    else");
			strSql.append("\n       update omplan set " + upsql);
			strSql.append("\n       where omid=" + omid + " and omepid=" + Omepid + ";");
			strSql.append("\n    end if;");

		}
		strSql.append("\n    v_retcs:= '1'||v_omepid ;");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");

		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
	}

	// 修改记录 订货会app调用
	public int Update() {

		StringBuilder strSql = new StringBuilder();

		strSql.append("begin");
		strSql.append("\n update omuser set ");
		if (Omepno != null) {
			strSql.append("omepno='" + Omepno + "',");
		}
		if (Omepname != null) {
			strSql.append("omepname='" + Omepname + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Custid != null) {
			strSql.append("custid=" + Custid + ",");
		}
		if (Js != null) {
			strSql.append("js=" + Js + ",");
		}
		if (Password != null) {
			strSql.append("password='" + Password.toUpperCase() + "',");
		}
		if (Qxcs != null) {
			strSql.append("qxcs='" + Qxcs + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where omepid=" + Omepid + " and accid=" + Accid + " ;");

		// strSql.append("\n select v_retcs into :retcs from dual;");

		strSql.append("\n end;");
		// System.out.println(strSql.toString());
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 生成随机密码 ok
	public int doRandompwd(long omid) {
		StringBuilder strSql = new StringBuilder();

		strSql.append(" update omuser a set password=trunc(DBMS_RANDOM.VALUE(100000,999999))");
		strSql.append(" where a.accid=" + Accid);
		strSql.append(" and exists (select 1 from omcustomer b where a.custid=b.custid and b.omid=" + omid + ")");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 自动生成订货会用户登账账号 ok
	public int doAutotoOmuserno(long omid) {
		// StringBuilder strSql = new StringBuilder();

		String qry = "declare";
		qry += "\n    v_omepno nvarchar2(20);";
		qry += "\n    v_noid number;";
		qry += "\n    v_count number;";
		qry += "\n begin";

		qry += "\n   declare cursor cur_data is";
		qry += "\n     select a.custid,a.custname,a.linkman,a.mobile from customer a ";
		qry += "\n     where a.accid=" + Accid + " and not exists (select 1 from omuser b where a.accid=b.accid and a.custid=b.custid and rownum=1);";
		qry += "\n     v_row cur_data%rowtype;";
		qry += "\n  begin";
		qry += "\n     v_count:=0;";
		qry += "\n     v_omepno:='未命名';";
		qry += "\n     for v_row in cur_data loop   ";
		qry += "\n        insert into omuser (omepid,omepname,mobile,custid,accid,password,statetag)";
		qry += "\n        values (omuser_omepid.nextval,case when v_row.linkman is null then v_omepno else substr(v_row.linkman,1,10) end,v_row.mobile,v_row.custid," + Accid + ",'123456',1);";
		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>1000 then";
		qry += "\n           commit;";
		qry += "\n           v_count:=0;";
		qry += "\n        end if;";
		qry += "\n     end loop;";
		qry += "\n  end;";
		qry += "\n  commit;";

		qry += "\n  declare cursor cur_data is";
		qry += "\n    select a.omepid from omuser a ";
		qry += "\n    where a.accid=" + Accid + " and (a.omepno<>'ADMIN' or a.omepno is null) order by a.omepid;";
		qry += "\n    v_row cur_data%rowtype;";
		qry += "\n  begin";
		// qry += "\n v_count:=0;";
		qry += "\n    v_noid:=1;";
		qry += "\n    for v_row in cur_data loop";
		qry += "\n        v_omepno:=trim(to_char(v_noid,'0000'));";
		qry += "\n        v_noid:=v_noid+1;";
		qry += "\n        WHILE instr(v_omepno, '4')>0 LOOP";
		qry += "\n           v_omepno:=trim(to_char(v_noid,'0000'));";
		qry += "\n           v_noid:=v_noid+1;";
		qry += "\n        END LOOP;";
		qry += "\n        update omuser set omepno=v_omepno where omepid=v_row.omepid;";
		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>1000 then";
		qry += "\n           commit;";
		qry += "\n           v_count:=0;";
		qry += "\n        end if;";
		qry += "\n    end loop;";
		qry += "\n  end;";
		qry += "\n end;";

		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作失败！";
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
		strSql.append(" FROM omuser a ");
		strSql.append(" left outer join customer b on a.custid=b.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omuser a ");
		strSql.append(" left outer join customer b on a.custid=b.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 ok
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist, long omid) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM omuser a");
		strSql.append(" left outer join omplan b on a.omepid=b.omepid and b.omid=" + omid);
		strSql.append(" left outer join customer c on a.custid=c.custid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from omuser");
		strSql.append(" where omepno='" + Omepno + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Omepid != null)
			strSql.append(" and Omepid<>" + Omepid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "代号:" + Omepno + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	public int Login(String deviceno, String devicetype, int fs) {// fs=1,微信小程序登录

		// System.out.println("EmployeLogin 1");
		if (deviceno == null || deviceno.equals(""))
			deviceno = "FLYANG20150107";
		if (devicetype == null || devicetype.equals(""))
			devicetype = "???";

		deviceno = deviceno.toUpperCase();
		// Epno = Epno.toUpperCase().trim();

		if (Omepno.equals("")) {
			errmess = "员工账号无效!";
			return 0;
		}

		if (!Func.isNumAndEnCh(Omepno)) {
			errmess = "用户账号存在非法字符1！";
			return 0;
		}

		// System.out.println(jsonObject.toString());

		// ===================================
		Password = Password.toUpperCase();
		// tag:0=零售 1=批发 2=零售+批发
		String qry = "select  a.omepid,a.omepno,a.omepname,a.mobile,a.statetag,a.PASSWORD,b.NOUSED as locked,a.custid,a.qxcs,a.js"; // statetag:0=禁止登录1=允许登录2=删除
		qry += ",to_char(b.begindate,'yyyy-mm-dd') as accdate,c.areaname,b.balcurr";
		qry += " from omuser a join accreg b on a.accid=b.accid ";
		qry += " left outer join customer c on a.custid=c.custid";
		qry += " where a.statetag<>2 and a.accid=" + Accid + " and a.omepno='" + Omepno + "' and rownum=1 ";
		// WriteResult("0", qry);
		// WriteLogTXT("debug", "0", qry);
		// System.out.println("EmployeLogin 2 " +qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		// System.out.println("EmployeLogin 3");
		if (tb.getRowCount() == 0) {
			errmess = "错误的用户代号" + Omepno + ":606";
			return 0;
		}
		// if (model != null)
		// if (tb.getRowCount() > 0) {
		if (Integer.parseInt(tb.getRow(0).get("LOCKED").toString()) == 1) {
			errmess = "系统正在维护，请稍候登录:601";
			return 0;
		}

		// System.out.println("EmployeLogin 5");
		if (Integer.parseInt(tb.getRow(0).get("STATETAG").toString()) == 0) {
			errmess = "用户已禁用，不允许登录:602";
			return 0;
		}
		if (Integer.parseInt(tb.getRow(0).get("STATETAG").toString()) == 3 && fs == 0) {
			errmess = "用户因为非法截屏被锁，请联系管理员解锁:634";
			return 0;
		}
		if (Long.parseLong(tb.getRow(0).get("CUSTID").toString()) == 0) {
			errmess = "未找到用户对应的客户资料:633";
			return 0;
		}
		// System.out.println("EmployeLogin 6");

		if (Integer.parseInt(tb.getRow(0).get("BALCURR").toString()) <= 0) {

			errmess = "账户枫杨果余额不足，请通知系管理员充值！";
			return 0;
		}
		// String mintime = tb.getRow(0).get("ONTIME").toString();
		// String maxtime = tb.getRow(0).get("OFFTIME").toString();

		// if (Integer.parseInt(tb.getRow(0).get("LEVELID").toString()) > 0 && !tb.getRow(0).get("ONTIME").toString().equals("00:00") && !tb.getRow(0).get("OFFTIME").toString().equals("00:00")) {
		//
		// // String nowtime = GetServerdatetime().ToString("HH:mm");
		//
		// DateFormat df = new SimpleDateFormat("HH:mm");
		//
		// String nowtime = df.format(pFunc.getServerdatetime());
		//
		// if (nowtime.compareTo(mintime) < 0 || nowtime.compareTo(maxtime) > 0) {
		// errmess = "非工作时间不允许登录:604";
		// return 0;
		// }
		// }
		// if (password == dt.Rows[0]["password"].ToString()) //登录密码正确
		if (!tb.getRow(0).get("PASSWORD").toString().equals(Password) && !Password.equals("F8838E5383F6A9AEAA388C7D3028DAA1")) // 登录密码正确
		{
			errmess = "密码错误:605";
			return 0;
		}
		Omepname = tb.getRow(0).get("OMEPNAME").toString();
		Omepid = Long.parseLong(tb.getRow(0).get("OMEPID").toString());
		// String omepidstr = Omepid.toString();

		DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		String currdatetime = df.format(new Date());
		String accesskey = Func.StrtoMD5("store" + Omepid + deviceno + devicetype + currdatetime);

		long omid = 0;
		String omname = "";
		String omremark = "";
		String begindate = "";
		String enddate = "";
		String linktel = "";
		String linkman = "";
		String address = "";
		String wxts = "";
		String imagename = "";
		String logoimagename = "";
		String daterange = "";
		int statetag = 0;
		long custid = Long.parseLong(tb.getRow(0).get("CUSTID").toString());
		if (fs == 0) {// 订货会登录，要取订货会信息
			// string areaname = dt.Rows[0]["areaname"].ToString();
			qry = "  select omid,omname,remark,to_char(begindate,'yyyy-mm-dd') as begindate,to_char(enddate,'yyyy-mm-dd') as enddate,linkman,linktel,wxts,address,statetag,omimagename,logoimagename";
			qry += " from OMSUBJECT a where a.accid=" + Accid + " and a.statetag>=1 and a.statetag<=3 and rownum=1";
			// statetag:0=等待 1=筹备2=开始3=暂停4=结束
			qry += " and exists (select 1 from omcustomer b where a.omid=b.omid and b.custid=" + custid + ")";
			// System.out.println(qry);
			Table tb1 = new Table();
			tb1 = DbHelperSQL.Query(qry).getTable(1);

			if (tb1.getRowCount() > 0) {
				omid = Long.parseLong(tb1.getRow(0).get("OMID").toString());
				omname = tb1.getRow(0).get("OMNAME").toString();
				omremark = tb1.getRow(0).get("REMARK").toString();
				begindate = tb1.getRow(0).get("BEGINDATE").toString();
				enddate = tb1.getRow(0).get("ENDDATE").toString();
				address = tb1.getRow(0).get("ADDRESS").toString();
				wxts = tb1.getRow(0).get("WXTS").toString();
				linktel = tb1.getRow(0).get("LINKTEL").toString();
				linkman = tb1.getRow(0).get("LINKMAN").toString();
				statetag = Integer.parseInt(tb1.getRow(0).get("STATETAG").toString());
				imagename = tb1.getRow(0).get("OMIMAGENAME").toString();
				logoimagename = tb1.getRow(0).get("LOGOIMAGENAME").toString();

				// Date date0 = DateTime.Parse(begindate);
				// Date date1 = DateTime.Parse(enddate);
				df = new SimpleDateFormat("yyyy-MM-dd");
				Date date0 = null;
				Date date1 = null;
				try {
					date0 = df.parse(begindate);
					date1 = df.parse(enddate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					errmess = "订货会起始时间未设定！";
					return 0;
				}

				df = new SimpleDateFormat("yyyy年MM月dd日");
				daterange = df.format(date0) + "-";
				df = new SimpleDateFormat("MM月dd日");
				daterange += df.format(date1);
				long days = Func.getBetweenDays(date0, date1);
				// long days = (date1.getTime() - date0.getTime()) / (24 * 60 * 60 * 1000);
				daterange += "(" + days + "天)";

			}
			if (omid == 0) {
				errmess = "现在没有可参加的订货会！";
				return 0;
			}
		}
		// string accesskey0 = "";
		qry = "declare ";
		qry += "   v_count number;";
		qry += " begin";
		qry += "    insert into omuserlogin (id,omepid,logindate,deviceno,devicetype) values (omuserlogin_id.nextval," + Omepid + ",sysdate,'" + deviceno + "','" + devicetype + "');";

		qry += "    select count(*) into v_count from OMUSERONLINE where omepid=" + Omepid + ";";
		qry += "    if v_count=0 then";
		qry += "       insert into OMUSERONLINE (omepid,accid,logindate,deviceno,devicetype,accesskey) values (" + Omepid + "," + Accid + ",sysdate,'" + deviceno + "','" + devicetype + "','" + accesskey + "');";
		qry += "    else ";
		if (devicetype.equals("***")) {
			qry += "      update OMUSERONLINE set logindate=sysdate where omepid=" + Omepid + ";";
		} else {
			qry += "      update OMUSERONLINE set deviceno='" + deviceno + "',devicetype='" + devicetype + "',accesskey='" + accesskey + "',logindate=sysdate where omepid=" + Omepid + ";";
		}
		qry += "   end if;";

		qry += "   commit;";

		qry += "   select accesskey into :accesskey from OMUSERONLINE where omepid=" + Omepid + ";";
		qry += " end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("accesskey", new ProdParam(Types.VARCHAR));
		// 调用
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "未找到登录信息！";
			return 0;

		}
		// 取返回值
		// accesskey0 = param.get("accesskey0").getParamvalue().toString();
		accesskey = param.get("accesskey").getParamvalue().toString();
		String jsonstr = "\"msg\":\"操作成功！\",\"OMEPID\": \"" + Omepid + "\"" + ",\"OMEPNAME\":\"" + Omepname + "\"" //
				+ ",\"LEVELID\":\"" + tb.getRow(0).get("JS").toString() + "\"" //
				+ ",\"ACCESSKEY\":\"" + accesskey + "\"" //
				+ ",\"CUSTID\":" + custid //
				+ ",\"AREANAME\":\"" + tb.getRow(0).get("AREANAME").toString() + "\""//
				+ ",\"QXCS\":\"" + tb.getRow(0).get("QXCS").toString() + "\"" // 第一位=1表示允许转枫杨果
				+ ",\"ACCDATE\":\"" + tb.getRow(0).get("ACCDATE").toString() + "\""//
				+ ",\"QXPUBLIC\":\"" + tb.getRow(0).get("QXCS").toString() + "\"" // 第一位=1表示允许转枫杨果
		;
		if (fs == 0)
			jsonstr += ",\"OMID\":" + omid + ",\"OMSTATETAG\":" + statetag
			// statetag:0=等待 1=筹备2=开始3=暂停4=结束
					+ ",\"OMNAME\":\"" + omname + "\"" + ",\"OMREMARK\":\"" + omremark + "\"" //
					+ ",\"OMADDRESS\":\"" + address + "\"" + ",\"OMBEGINDATE\":\"" + begindate + "\"" //
					+ ",\"OMENDDATE\":\"" + enddate + "\"" + ",\"OMDATERANGE\":\"" + daterange + "\"" + ",\"OMLINKMAN\":\"" + linkman + "\""//
					+ ",\"OMLINKTEL\":\"" + linktel + "\"" //
					+ ",\"OMWXTS\":\"" + wxts + "\"" + ",\"OMIMAGENAME\":\"" + imagename + "\""//
					+ ",\"LOGOIMAGENAME\":\"" + logoimagename + "\"" //
			;

		errmess = jsonstr;
		return 1;
	}

	// 从excel导入订货会用户
	public int AppendFromXLS(long omid, Omplan omplan) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n    v_custid number(10); ");
		strSql.append("\n    v_retcs varchar2(200);");
		strSql.append("\n    v_count number; ");
		strSql.append("\n begin ");
		if (Omepid > 0) // 修改
		{
			if (Omepname != null)
				if (Func.getLength(Omepname) > 10) {
					errmess = "用户姓名：" + Omepname + " 不允许超过10个字符长度！";
					return 0;
				}
			if (!Func.isNull(Custname)) {
				// 、、custname = dataObj["custname"].ToString();
				strSql.append("\n   begin ");
				strSql.append("\n     select custid into v_custid from customer where accid=" + Accid + " and custname='" + Custname + "' and statetag=1 and noused=0;");
				strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
				strSql.append("\n     v_retcs:= '0未找到客户:" + Custname + "!' ;");
				strSql.append("\n     goto exit;");
				strSql.append("\n   end;");
			}
			strSql.append("\n  update omuser set lastdate=sysdate");
			if (Omepno != null)
				strSql.append(",omepno='" + Omepno + "'");
			if (Omepname != null)
				strSql.append(",omepname='" + Omepname + "'");
			if (Mobile != null)
				strSql.append(",mobile='" + Mobile + "'");
			if (Remark != null)
				strSql.append(",remark='" + Remark + "'");
			if (Password != null)
				strSql.append(",password='" + Password + "'");
			if (Js != null)
				strSql.append(",js='" + Js + "'");
			if (Custname != "")
				strSql.append(",custid=v_custid");

			strSql.append("\n  where omepid=" + Omepid + ";");

		} else {
			if (Func.isNull(Custname)) {
				errmess = "客户名称无效！";
				return 0;
			}
			if (Func.isNull(Omepname)) {
				errmess = "用户姓名无效！";
				return 0;
			}
			// if (Omepname != null)
			if (Func.getLength(Omepname) > 10) {
				errmess = "用户姓名：" + Omepname + " 不允许超过10个字符长度！";
				return 0;
			}
			if (Omepno == null)
				Omepno = "";
			if (Mobile == null)
				Mobile = "";
			if (Remark == null)
				Remark = "";
			if (Js == null)
				Js = 0;
			if (Password == null)
				Password = "123456";
			strSql.append("\n   begin ");
			strSql.append("\n     select custid into v_custid from customer where accid=" + Accid + " and custname='" + Custname + "' and statetag=1 and noused=0;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n     v_retcs:= '0未找到客户:" + Custname + "!' ;");
			strSql.append("\n     goto exit;");
			strSql.append("\n   end;");
			strSql.append("\n   insert into omuser (omepid,omepname,omepno,mobile,remark,password,custid,accid,statetag,js)");
			strSql.append("\n   values (omuser_omepid.nextval,'" + Omepname + "','" + Omepno + "','" + Mobile + "','" + Remark + "','" + Password + "',v_custid," + Accid + ",1," + Js + "); ");
		}

		if (omid > 0) // 保存订货会计划指标
		{
			Float jhamt = omplan.getJhamt();
			Float jhcurr = omplan.getJhcurr();
			Long jhnum = omplan.getJhnum();
			Long jhskc = omplan.getJhskc();
			String lastop = omplan.getLastop();

			String insql1 = "omepid,omid,lastop";
			String insql2 = Omepid + "," + omid + ",'" + lastop + "'";
			String upsql = "lastdate=sysdate,lastop='" + lastop + "'";

			if (jhamt != null) {
				insql1 += ",jhamt";
				insql2 += "," + jhamt;
				upsql += ",jhamt=" + jhamt;
			}
			if (jhcurr != null) {
				insql1 += ",jhcurr";
				insql2 += "," + jhcurr;
				upsql += ",jhcurr=" + jhcurr;
			}

			if (jhskc != null) {
				insql1 += ",jhskc";
				insql2 += "," + jhskc;
				upsql += ",jhskc=" + jhskc;
			}
			if (jhnum != null) {
				insql1 += ",jhnum";
				insql2 += "," + jhnum;
				upsql += ",jhnum=" + jhnum;
			}

			strSql.append("\n    select count(*) into v_count from omplan where omid=" + omid + " and omepid=" + Omepid + ";");
			strSql.append("\n    if v_count=0 then");
			strSql.append("\n       insert into omplan (" + insql1 + ") values (" + insql2 + ");");
			strSql.append("\n    else");
			strSql.append("\n       update omplan set " + upsql);
			strSql.append("\n       where omid=" + omid + " and omepid=" + Omepid + ";");
			strSql.append("\n    end if;");

		}

		strSql.append("\n   v_retcs:= '1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("\n end; ");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);

		if (retcs.substring(0, 1).equals("0"))
			return 0;
		else
			return 1;
	}

	// 显示订货会用户订货汇总
	public Table listOmuserOrderSum(QueryParam qp, long omid, long custid, int fs, String findbox) {

		String qry = "select a.omepid,a.omepname,nvl(sum(b.amount),0) as amount,nvl(sum(b.amount*c.retailsale),0) as curr,count(distinct b.wareid) as num";
		// qry += ",nvl(b.jhamt,0) as jhamt,nvl(b.jhcurr,0) as jhcurr,nvl(b.jhskc,0) as jhskc";
		qry += "\n from omuser a left outer join omcustware b on a.omepid=b.omepid and b.omid=" + omid;
		qry += "\n join warecode c on b.wareid=c.wareid";
		// qry += "\n left outer join omplan b on a.omepid=b.omepid and b.omid=" + htp.omid;
		qry += "\n where a.custid=" + custid + " and a.statetag<>2"; // statetag:0=禁止登录1=允许登录2=删除,3=截屏锁定
		qry += "\n and a.accid=" + Accid;
		qry += "\n and exists (select 1 from omwarecode x where b.omid=x.omid and b.wareid=x.wareid and x.noused=0)";
		if (fs == 0)
			qry += " and not exists (select 1 from omorder c where a.omepid=c.omepid and b.omid=c.omid)";
		else if (fs == 1)
			qry += " and exists (select 1 from omorder c where a.omepid=c.omepid and b.omid=c.omid)";

		// qry += "\n and exists (select 1 from omcustomer b where a.custid=b.custid and b.omid=" + htp.omid + ")";
		if (findbox.length() > 0)
			qry += " and a.omepname like '%" + findbox + "%'";
		qry += "\n group by a.omepid,a.omepname";

		qp.setQueryString(qry);

		return DbHelperSQL.GetTable(qp);

	}

}