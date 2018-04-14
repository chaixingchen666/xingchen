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
//  @author:sunhong  @date:2017-03-02 17:29:05
//*************************************
public class Bookdetail implements java.io.Serializable {
	private Float Id;
	private Long Bookid;
	private Long Tobookid;

	private Long Accid;
	private String Notedate;
	private Long Subid;
	private Double Curr0;
	private Double Curr1;
	private Double Curr2;
	private String Ywnoteno = "";
	private String Remark = "";
	private String Checkman;
	private String Lastop;
	private Integer Dayhj = 0;
	private String errmess;

	private String Mindate;
	private String Maxdate;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Float id) {
		this.Id = id;
	}

	public void setDayhj(Integer dayhj) {
		this.Dayhj = dayhj;
	}

	public void setMindate(String mindate) {
		Mindate = mindate;
	}

	public void setMaxdate(String maxdate) {
		Maxdate = maxdate;
	}

	public Float getId() {
		return Id;
	}

	public void setBookid(Long bookid) {
		this.Bookid = bookid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getBookid() {
		return Bookid;
	}

	public void setNotedate(String notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Notedate);
		return Notedate;
	}

	public void setSubid(Long subid) {
		this.Subid = subid;
	}

	public Long getSubid() {
		return Subid;
	}

	public void setCurr0(Double curr0) {
		this.Curr0 = curr0;
	}

	public Double getCurr0() {
		return Curr0;
	}

	public void setCurr1(Double curr1) {
		this.Curr1 = curr1;
	}

	public Double getCurr1() {
		return Curr1;
	}

	public void setCurr2(Double curr2) {
		this.Curr2 = curr2;
	}

	public Double getCurr2() {
		return Curr2;
	}

	public void setYwnoteno(String ywnoteno) {
		this.Ywnoteno = ywnoteno;
	}

	public String getYwnoteno() {
		return Ywnoteno;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
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
		if (Id == null || Bookid == null || Id == 0 || Bookid == 0) {
			errmess = "id或bookid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_lastop varchar2(20);");
		strSql.append("\n   v_checkman varchar2(20);");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n   v_balcurr number;");
		strSql.append("\n   v_subid number(10);");
		strSql.append("\n   v_id0 number(20);");
		strSql.append("\n   v_bookid number(10);");
		strSql.append("\n   v_balcurr0 number;");
		strSql.append("\n begin");
		strSql.append("\n   begin");
		strSql.append("\n     select lastop,checkman,notedate,subid,id0 into v_lastop,v_checkman,v_notedate,v_subid,v_id0");
		strSql.append("\n     from bookdetail where bookid=" + Bookid + " and id=" + Id + " ;");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:='0未找到相关记录！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n   end;");
		strSql.append("\n   if v_lastop<>'" + Lastop + "' then");
		strSql.append("\n      v_retcs:='0不允许删除其他用户的记录！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   if length(v_checkman)>0 then");
		strSql.append("\n      v_retcs:='0不允许删除已审核的记录！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_subid=0 and v_id0=0 then ");
		strSql.append("\n      v_retcs:='0这是转账记录，请在转出方账本里删除！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   delete from bookdetail where bookid=" + Bookid + " and id=" + Id + ";");

		strSql.append("\n   commit;");
		strSql.append("\n   v_balcurr:=f_calcbookbalcurr(" + Bookid + ",v_notedate) ;");
		// 如果是转账记录，要删除转入账本的记录
		strSql.append("\n   if v_subid=0 and v_id0>0 then");
		strSql.append("\n      begin");
		strSql.append("\n        select notedate,bookid into v_notedate,v_bookid from bookdetail where id=v_id0;");
		strSql.append("\n        delete from bookdetail where id=v_id0;");
		strSql.append("\n        commit;");
		strSql.append("\n        p_calcbookbalcurr(v_bookid,v_notedate) ;");
		strSql.append("\n      EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n        v_balcurr0:=0;");
		strSql.append("\n      end;");
		strSql.append("\n   end if;");

		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs,v_balcurr into :retcs,:balcurr from dual;");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
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
			errmess = "\"msg\":\"" + retcs.substring(1) + "\""//
					+ ",\"balcurr\":" + param.get("balcurr").getParamvalue().toString();
			return 1;
		}
	}

	// 增加记录
	public int Append(Double curr) {
		//System.out.println("1111");
		if (Bookid == null || Bookid == 0) {
			errmess = "账本参数无效！";
			return 0;
		}
		//System.out.println("1111.1");
		if (Subid == null || Subid == 0) {
			errmess = "记账项目参数无效！";
			return 0;
		}
		//System.out.println("1111.2");
		//		if (Func.isNull(Remark)) {
		//			errmess = "请输入摘要！";
		//			return 0;
		//		}
		if (Remark == null)
			Remark = "";
		//System.out.println("1111.3 curr="+curr);
		if (curr == 0) {
			errmess = "请输入金额！";
			return 0;
		}
		//System.out.println("1111.4");
		if (Func.isNull(Notedate)) {
			Notedate = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd HH:mm:ss");
		}
		// if (lx < 1 || lx > 2) {
		// errmess = "lx不是一个有效值！";
		// return 0;
		//
		// }
		//System.out.println("2222");
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_curr0 number; ");
		strSql.append("\n   v_curr1 number; ");
		strSql.append("\n   v_curr2 number; ");
		strSql.append("\n   v_curr20 number; ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_lx number(1); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_notedate varchar2(10); ");
		strSql.append("\n begin");
		strSql.append("\n   v_curr2:=0;");
		strSql.append("\n   begin");
		strSql.append("\n      select to_char(v_notedate,'yyyy-mm-dd') into v_notedate from accbook where accid=" + Accid + " and statetag=1 and bookid=" + Bookid + ";");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_retcs:= '0账本无效!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end;");
		strSql.append("\n   if v_notedate>'" + Func.subString(Notedate, 1, 10) + "' then");

		strSql.append("\n      v_retcs:= '0记账时间不能在账本建账时间'||v_notedate||'之前!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   begin");
		strSql.append("\n     select lx into v_lx from subitem where (accid=" + Accid + " or accid=0) and subid=" + Subid + " and statetag=1 and noused=0;");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_retcs:= '0记账项目无效!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end;");
		strSql.append("\n   if v_lx=1 then ");// 1=收入
		strSql.append("\n      v_curr0:=" + curr + "; v_curr1:=0;");
		strSql.append("\n   else ");// 2=支出
		strSql.append("\n      v_curr0:=0; v_curr1:=" + curr + ";");
		strSql.append("\n   end if; ");

		// strSql.append("\n begin");
		// strSql.append("\n select nvl(curr2,0) into v_curr20 from (select curr2 from bookdetail where bookid=" + Bookid + " and notedate<=to_date('" + Notedate
		// + "','yyyy-mm-dd hh24:mi:ss') order by notedate desc,id desc) where rownum=1;");
		// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN");
		// strSql.append("\n v_curr20:=0;");
		// strSql.append("\n end;");
		// strSql.append("\n v_curr2:=v_curr20+v_curr0-v_curr1;");

		strSql.append("\n   v_id:=bookdetail_id.nextval;");
		strSql.append("\n   insert into bookdetail (id,accid,bookid,subid,remark,curr0,curr1,curr2,ywnoteno,notedate,lastdate,lastop)");
		strSql.append("\n   values (v_id," + Accid + "," + Bookid + "," + Subid + ",'" + Remark + "',v_curr0,v_curr1,0,'" + Ywnoteno + "',to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'),sysdate,'" + Lastop + "');");
		strSql.append("\n   v_retcs:= '1'||v_id ;");
		strSql.append("\n   commit;");
		strSql.append("\n   v_curr2:=f_calcbookbalcurr(" + Bookid + ",to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'));");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs,v_curr2 into :retcs,:balcurr from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
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
			errmess = "\"msg\":\"" + retcs.substring(1) + "\""//
					+ ",\"balcurr\":" + param.get("balcurr").getParamvalue().toString();
			return 1;
		}

	}

	// 增加转账记录
	public int Appendto(Double curr) {
		//System.out.println("1111");
		if (Bookid == null || Bookid == 0) {
			errmess = "源账本参数无效！";
			return 0;
		}
		if (Tobookid == null || Tobookid == 0) {
			errmess = "目的账本参数无效！";
			return 0;
		}
		Subid = (long) 0;// 0=系统约定的转账
		//System.out.println("1111.1");
		// if (Func.isNull(Remark)) {
		// errmess = "请输入摘要！";
		// return 0;
		// }
		if (Remark == null)
			Remark = "";
		//System.out.println("1111.3 curr="+curr);
		if (curr == 0) {
			errmess = "请输入金额！";
			return 0;
		}
		//System.out.println("1111.4");
		if (Func.isNull(Notedate)) {
			Notedate = Func.DateToStr(pFunc.getServerdatetime(), "yyyy-MM-dd HH:mm:ss");
		}
		// if (lx < 1 || lx > 2) {
		// errmess = "lx不是一个有效值！";
		// return 0;
		//
		// }
		//System.out.println("2222");
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_curr0 number; ");
		strSql.append("\n   v_curr1 number; ");
		strSql.append("\n   v_curr2 number; ");
		strSql.append("\n   v_curr20 number; ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_lx number(1); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_bookname0 varchar2(40); ");
		strSql.append("\n   v_bookname1 varchar2(40); ");
		strSql.append("\n   v_notedate date; ");
		strSql.append("\n begin");
		strSql.append("\n   v_curr2:=0;");
		strSql.append("\n   begin");
		strSql.append("\n      select notedate,bookname into v_notedate,v_bookname0 from accbook where accid=" + Accid + " and statetag=1 and bookid=" + Bookid + ";");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_retcs:= '0源账本无效!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end;");

		strSql.append("\n   if v_notedate>to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss') then");

		strSql.append("\n      v_retcs:= '0记账时间不能在账本建账时间'||to_char(v_notedate,'yyyy-mm-dd')||'之前!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   begin");
		strSql.append("\n      select notedate,bookname into v_notedate,v_bookname1 from accbook where accid=" + Accid + " and statetag=1 and bookid=" + Tobookid + ";");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_retcs:= '0目的账本无效!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end;");

		strSql.append("\n   if v_notedate>to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss') then");

		strSql.append("\n      v_retcs:= '0记账时间不能在账本建账时间'||to_char(v_notedate,'yyyy-mm-dd')||'之前!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		// strSql.append("\n begin");
		// strSql.append("\n select nvl(curr2,0) into v_curr20 from (select curr2 from bookdetail where bookid=" + Bookid + " and notedate<=to_date('" + Notedate
		// + "','yyyy-mm-dd hh24:mi:ss') order by notedate desc,id desc) where rownum=1;");
		// strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN");
		// strSql.append("\n v_curr20:=0;");
		// strSql.append("\n end;");
		// strSql.append("\n v_curr2:=v_curr20+v_curr0-v_curr1;");

		// 转入
		strSql.append("\n   v_id:=bookdetail_id.nextval;");
		strSql.append("\n   insert into bookdetail (id,accid,bookid,subid,remark,curr0,curr1,curr2,ywnoteno,notedate,lastdate,lastop,id0)"); // id0=转出方账户记录id
		strSql.append("\n   values (v_id," + Accid + "," + Tobookid + "," + Subid + ",'【'||v_bookname0||'=>'||v_bookname1||'】" + Remark //
				+ "'," + curr + ",0,0,'" + Ywnoteno + "',to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'),sysdate,'" + Lastop + "',0);");

		// 转出
		// strSql.append("\n v_id:=bookdetail_id.nextval;");
		strSql.append("\n   insert into bookdetail (id,accid,bookid,subid,remark,curr0,curr1,curr2,ywnoteno,notedate,lastdate,lastop,id0)");
		strSql.append("\n   values (bookdetail_id.nextval," + Accid + "," + Bookid + "," + Subid + ",'【'||v_bookname0||'=>'||v_bookname1||'】" + Remark//
				+ "',0," + curr + ",0,'" + Ywnoteno + "',to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'),sysdate,'" + Lastop + "',v_id);");
		strSql.append("\n   v_retcs:= '1'||v_id ;");
		strSql.append("\n   commit;");
		strSql.append("\n   v_curr0:=f_calcbookbalcurr(" + Bookid + ",to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'));");
		strSql.append("\n   v_curr1:=f_calcbookbalcurr(" + Tobookid + ",to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'));");

		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs,v_curr0,v_curr1 into :retcs,:balcurr0,:balcurr1 from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("balcurr0", new ProdParam(Types.DOUBLE));
		param.put("balcurr1", new ProdParam(Types.DOUBLE));
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
			errmess = "\"msg\":\"" + retcs.substring(1) + "\""//
					+ ",\"balcurr0\":" + param.get("balcurr0").getParamvalue().toString()//
					+ ",\"balcurr1\":" + param.get("balcurr1").getParamvalue().toString()//
			;
			return 1;
		}

	}

	// 修改记录
	// @SuppressWarnings("null")
	public int Update(Double curr) {
		if (Id == null || Bookid == null || Id == 0 || Bookid == 0) {
			errmess = "id或bookid不是一个有效值！";
			return 0;
		}

		if (Subid != null || curr != null) {
			if (Subid == null || Subid == 0) {
				errmess = "记账项目参数无效！";
				return 0;
			}
			//System.out.println("1111.3 curr="+curr);
			if (curr == null || curr == 0) {
				errmess = "请输入金额！";
				return 0;
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_lastop varchar2(20);");
		strSql.append("\n   v_checkman varchar2(20);");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_curr0 number; ");
		strSql.append("\n   v_curr1 number; ");
		strSql.append("\n   v_curr2 number; ");
		strSql.append("\n   v_curr20 number; ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_lx number(1); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n begin");
		strSql.append("\n   v_curr2:=0;");
		strSql.append("\n   begin");
		strSql.append("\n      select notedate into v_notedate from accbook where accid=" + Accid + " and statetag=1 and bookid=" + Bookid + ";");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_retcs:= '0账本无效!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end;");
		strSql.append("\n   if v_notedate>to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss') then");

		strSql.append("\n      v_retcs:= '0记账时间不能在账本建账时间'||to_char(v_notedate,'yyyy-mm-dd')||'之前!' ;");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   begin");
		strSql.append("\n     select lastop,checkman,notedate into v_lastop,v_checkman,v_notedate from bookdetail where bookid=" + Bookid + " and id=" + Id + " ;");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:='0未找到相关记录！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n   end;");
		strSql.append("\n   if v_lastop<>'" + Lastop + "' then");
		strSql.append("\n      v_retcs:='0不允许修改其他用户的记录！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_checkman<>'' then");
		strSql.append("\n      v_retcs:='0不允许修改已审核的记录！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		if (Subid != null) {
			strSql.append("\n begin");
			strSql.append("\n   select lx into v_lx from subitem where (accid=" + Accid + " or accid=0) and subid=" + Subid + " and statetag=1 and noused=0;");
			strSql.append("\n EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n   v_retcs:= '0记账项目无效!' ;");
			strSql.append("\n   goto exit;");
			strSql.append("\n end;");
			strSql.append("\n if v_lx=1 then ");// 1=收入
			strSql.append("\n    v_curr0:=" + curr + "; v_curr1:=0;");
			strSql.append("\n else ");// 2=支出
			strSql.append("\n    v_curr0:=0; v_curr1:=" + curr + ";");
			strSql.append("\n end if; ");
		}
		if (Notedate != null) {
			strSql.append("\n  if v_notedate>to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss') then");
			strSql.append("\n     v_notedate:=to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss');");
			strSql.append("\n  end if;");
		}

		strSql.append("\n   update bookdetail set ");
		if (Subid != null) {
			strSql.append("\n subid=" + Subid);
			strSql.append("\n ,curr0=v_curr0,curr1=v_curr1,");
		}
		if (Notedate != null) {
			strSql.append("notedate=to_date('" + Notedate + "','yyyy-mm-dd hh24:mi:ss'),");
		}
		if (Ywnoteno != null) {
			strSql.append("ywnoteno='" + Ywnoteno + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and bookid=" + Bookid + " and accid=" + Accid + " ;");
		strSql.append("\n   commit;");
		strSql.append("\n   v_curr2:=f_calcbookbalcurr(" + Bookid + ",v_notedate) ;");
		strSql.append("\n   v_retcs:='1'||v_curr2;");

		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs,v_curr2 into :retcs,:balcurr from dual;");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
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
			errmess = "\"msg\":\"" + retcs.substring(1) + "\""//
					+ ",\"balcurr\":" + param.get("balcurr").getParamvalue().toString();
			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM bookdetail ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	public String GetTable2Json(QueryParam qp, String strWhere, String fieldlist) {

		Table tb = new Table();
		tb = GetTable(qp, strWhere, fieldlist);
		String retcs = "{\"total\":" + tb.getRowCount() + ",\"rows\":[";
		// String fh = "";
		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{";
			// String fh1 = "";
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
				String strValue = tb.getRow(0).get(j).toString();//
				if (j > 0)
					retcs += ",";
				retcs += "\"" + strKey + "\":\"" + strValue + "\"";
				// fh1 = ",";//
			}
			retcs += "}";
		}
		retcs += "]}";
		return retcs;

	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		String totalstr = "";
		StringBuilder strSql = new StringBuilder();
		if (Dayhj == 2) {//要返回期初金额
			strSql.append("declare");
			strSql.append("\n   v_firstcurr number(16,2);");
			strSql.append("\n   v_lastcurr number(16,2);");
			strSql.append("\n begin");
			//取期 初
			strSql.append("\n   begin");
			strSql.append("\n     select nvl(curr2,0) into v_firstcurr from (select curr2 from bookdetail where bookid=" + Bookid);
			strSql.append("\n     and notedate<to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') order by notedate desc,id desc) where rownum=1;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     select nvl(firstcurr,0) into v_firstcurr from accbook where bookid=" + Bookid + ";");
			strSql.append("\n   end;");

			//取期 末
			strSql.append("\n   begin");
			strSql.append("\n     select nvl(curr2,0) into v_lastcurr from (select curr2 from bookdetail where bookid=" + Bookid);
			strSql.append("\n     and notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss') order by notedate desc,id desc) where rownum=1;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_lastcurr:=0;");
			strSql.append("\n   end;");

			strSql.append("\n   select v_firstcurr,v_lastcurr into :firstcurr,:lastcurr from dual;");
			strSql.append("\n end;");
			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("firstcurr", new ProdParam(Types.DOUBLE));
			param.put("lastcurr", new ProdParam(Types.DOUBLE));
			Double firstcurr = (double) 0;
			Double lastcurr = (double) 0;
			// 调用
			if (DbHelperSQL.ExecuteProc(strSql.toString(), param) > 0) {
				// 取返回值
				firstcurr = Double.parseDouble(param.get("firstcurr").getParamvalue().toString());
				lastcurr = Double.parseDouble(param.get("lastcurr").getParamvalue().toString());
			}
			totalstr = "\"firstcurr\":\"" + Func.FormatNumber(firstcurr,2) + "\",\"lastcurr\":\"" +Func.FormatNumber( lastcurr,2) + "\"";

			strSql.setLength(0);
		}

		strSql.append("select " + fieldlist);
		if (Dayhj == 1)
			strSql.append(",X.CURRHJ0,X.CURRHJ1");
		strSql.append("\n FROM bookdetail a ");
		strSql.append("\n left outer join subitem b on a.subid=b.subid");
		if (Dayhj == 1) {
			strSql.append("\n left outer join (");
			strSql.append("\n select to_char(notedate,'yyyy-mm-dd') as daystr,nvl(sum( curr0),0) as currhj0,nvl(sum(curr1),0) as currhj1 from bookdetail a");
			if (strWhere.length() > 0) {
				strSql.append("\n where " + strWhere);
			}
			strSql.append("\n group by to_char(notedate,'yyyy-mm-dd')) x on to_char(a.notedate,'yyyy-mm-dd')=x.daystr");
		}

		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		if (totalstr.length() > 0)
			qp.setTotalString(totalstr);
//		return DbHelperSQL.GetTable(qp);
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);

	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		String totalstr = "";
		StringBuilder strSql = new StringBuilder();
		if (Dayhj == 2) {//要返回期初金额
			strSql.append("declare");
			strSql.append("\n   v_firstcurr number(16,2);");
			strSql.append("\n   v_lastcurr number(16,2);");
			strSql.append("\n begin");
			//取期 初
			strSql.append("\n   begin");
			strSql.append("\n     select nvl(curr2,0) into v_firstcurr from (select curr2 from bookdetail where bookid=" + Bookid);
			strSql.append("\n     and notedate<to_date('" + Mindate + " 00:00:00','yyyy-mm-dd hh24:mi:ss') order by notedate desc,id desc) where rownum=1;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     select nvl(firstcurr,0) into v_firstcurr from accbook where bookid=" + Bookid + ";");
			strSql.append("\n   end;");

			//取期 末
			strSql.append("\n   begin");
			strSql.append("\n     select nvl(curr2,0) into v_lastcurr from (select curr2 from bookdetail where bookid=" + Bookid);
			strSql.append("\n     and notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss') order by notedate desc,id desc) where rownum=1;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
			strSql.append("\n     v_lastcurr:=0;");
			strSql.append("\n   end;");

			strSql.append("\n   select v_firstcurr,v_lastcurr into :firstcurr,:lastcurr from dual;");
			strSql.append("\n end;");
			Map<String, ProdParam> param = new HashMap<String, ProdParam>();
			param.put("firstcurr", new ProdParam(Types.DOUBLE));
			param.put("lastcurr", new ProdParam(Types.DOUBLE));
			Double firstcurr = (double) 0;
			Double lastcurr = (double) 0;
			// 调用
			if (DbHelperSQL.ExecuteProc(strSql.toString(), param) > 0) {
				// 取返回值
				firstcurr = Double.parseDouble(param.get("firstcurr").getParamvalue().toString());
				lastcurr = Double.parseDouble(param.get("lastcurr").getParamvalue().toString());
			}
			totalstr = "\"firstcurr\":\"" + Func.FormatNumber(firstcurr,2) + "\",\"lastcurr\":\"" +Func.FormatNumber( lastcurr,2) + "\"";

			strSql.setLength(0);
		}

		strSql.append("select " + fieldlist);
		if (Dayhj == 1)
			strSql.append(",X.CURRHJ0,X.CURRHJ1");
		strSql.append("\n FROM bookdetail a ");
		strSql.append("\n left outer join subitem b on a.subid=b.subid");
		if (Dayhj == 1) {
			strSql.append("\n left outer join (");
			strSql.append("\n select to_char(notedate,'yyyy-mm-dd') as daystr,nvl(sum( curr0),0) as currhj0,nvl(sum(curr1),0) as currhj1 from bookdetail a");
			if (strWhere.length() > 0) {
				strSql.append("\n where " + strWhere);
			}
			strSql.append("\n group by to_char(notedate,'yyyy-mm-dd')) x on to_char(a.notedate,'yyyy-mm-dd')=x.daystr");
		}

		if (strWhere.length() > 0) {
			strSql.append("\n where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		if (totalstr.length() > 0)
			qp.setTotalString(totalstr);
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页汇总数据
	public Table GetTableSum(QueryParam qp, String strWhere, String fieldlist, int fs) { // 0=记账类别，1=记账人
		StringBuilder strSql = new StringBuilder();
		if (fs == 0)
			strSql.append("select a.subid,b.subname");
		else if (fs == 1)
			strSql.append("select a.lastop");

		strSql.append(",min(id) as keyid," + fieldlist);
		// strSql.append("," + fieldlist);
		strSql.append("\n FROM bookdetail a ");
		// if (fs == 0)
		strSql.append("\n left outer join subitem b on a.subid=b.subid");

		strSql.append("\n where " + strWhere);
		if (fs == 0)
			strSql.append("\n group by a.subid,b.subname");
		else if (fs == 1)
			strSql.append("\n group by a.lastop");

		qp.setQueryString(strSql.toString());
		// qp.setSortString("keyid");
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from bookdetail");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 审核
	public int Check() {
		if (Id == 0 || Bookid == 0) {
			errmess = "bookid或id不是一个有效值！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_lastop varchar2(20);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_remark varchar2(500);";
		qry += "\n   v_curr number(16,2);";
		qry += "\n begin";
		qry += "\n   begin";
		qry += "\n     select lastop,checkman,remark,curr0+curr1 into v_lastop,v_checkman,v_remark,v_curr  from Bookdetail where bookid=" + Bookid + " and id=" + Id + ";";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0未找到记录！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then"; // 如果有审核，表示取消审核
		qry += "\n      if v_checkman<>'" + Lastop + "' then ";
		qry += "\n         v_retcs:='0不是当前用户审核的记录，不允许取消审核！';";
		qry += "\n         goto exit;";
		qry += "\n      end if;";
		qry += "\n      update Bookdetail set checkman='' where  bookid=" + Bookid + " and id=" + Id + ";";
		qry += "\n      v_retcs:='1取消审核成功！';";
		qry += "\n      insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n      values (LOGRECORD_id.nextval," + Accid + ",'账本管理','【取消审核】记录id:" + Id + " 摘要:'||v_remark||',金额:'||v_curr,'" + Lastop + "');";

		qry += "\n      goto exit;";
		qry += "\n   end if;";
		qry += "\n   update Bookdetail set checkman='" + Lastop + "' where  bookid=" + Bookid + " and id=" + Id + ";";
		qry += "\n   v_retcs:='1审核成功！';";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'账本管理','【审核】记录id:" + Id + " 摘要:'||v_remark||',金额:'||v_curr,'" + Lastop + "');";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
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