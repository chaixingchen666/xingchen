package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-07 23:34:02
//*************************************
public class Wareinh implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2967327561185423805L;
	private Float Id;
	private String Noteno;
	private Long Accid;
	private String Notedatestr = "";
	private Long Provid;
	private Long Houseid;
	private Integer Ntid;
	private String Handno;
	private String Remark;
	private Float Totalamt;
	private Double Totalcurr;
	private Float Gdcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	// private Date Lastdate;
	private Long Cent;
	private String Orderno;
	private Integer Ywly;
	private Float Paycurr0;
	private Float Freecurr;
	private Float Checkcurr;
	private String Paylist;
	private Integer Cleartag;
	private Float Totalcost;
	private Integer Bj;
	private Long Sell_accid;
	private String Sell_noteno;
	private Float Totalcheckcurr;
	private String errmess;
	private String Calcdate = "";

	public void setCalcdate(String calcdate) {
		this.Calcdate = calcdate;
	}
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setNoteno(String noteno) {
		this.Noteno = noteno;
	}

	public String getNoteno() {
		return Noteno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}
	//
	// public void setNotedate(String notedate) {
	// this.Notedate = notedate;
	// }
	//
	// public String getNotedate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Notedate);
	// }

	public void setProvid(Long provid) {
		this.Provid = provid;
	}

	public Long getProvid() {
		return Provid;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setNtid(Integer ntid) {
		this.Ntid = ntid;
	}

	public Integer getNtid() {
		return Ntid;
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

	public void setTotalamt(Float totalamt) {
		this.Totalamt = totalamt;
	}

	public Float getTotalamt() {
		return Totalamt;
	}

	public void setTotalcurr(Double totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Double getTotalcurr() {
		return Totalcurr;
	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setCent(Long cent) {
		this.Cent = cent;
	}

	public Long getCent() {
		return Cent;
	}

	public void setOrderno(String orderno) {
		this.Orderno = orderno;
	}

	public String getOrderno() {
		return Orderno;
	}

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public void setFreecurr(Float freecurr) {
		this.Freecurr = freecurr;
	}

	public Float getFreecurr() {
		return Freecurr;
	}

	public void setCheckcurr(Float checkcurr) {
		this.Checkcurr = checkcurr;
	}

	public Float getCheckcurr() {
		return Checkcurr;
	}

	public void setPaylist(String paylist) {
		this.Paylist = paylist;
	}

	public String getPaylist() {
		return Paylist;
	}

	public void setCleartag(Integer cleartag) {
		this.Cleartag = cleartag;
	}

	public Integer getCleartag() {
		return Cleartag;
	}

	public void setTotalcost(Float totalcost) {
		this.Totalcost = totalcost;
	}

	public Float getTotalcost() {
		return Totalcost;
	}

	public void setBj(Integer bj) {
		this.Bj = bj;
	}

	public Integer getBj() {
		return Bj;
	}

	public void setSell_accid(Long sell_accid) {
		this.Sell_accid = sell_accid;
	}

	public Long getSell_accid() {
		return Sell_accid;
	}

	public void setSell_noteno(String sell_noteno) {
		this.Sell_noteno = sell_noteno;
	}

	public String getSell_noteno() {
		return Sell_noteno;
	}

	public void setTotalcheckcurr(Float totalcheckcurr) {
		this.Totalcheckcurr = totalcheckcurr;
	}

	public Float getTotalcheckcurr() {
		return Totalcheckcurr;
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

		strSql.append("declare ");
		strSql.append("\n  v_ywly number(1);");
		strSql.append("\n  v_ntid number(1);");
		strSql.append("\n  v_xsaccid number(10);");
		strSql.append("\n  v_xsnoteno varchar2(20); ");
		strSql.append("\n  v_operant varchar2(20); ");
		strSql.append("\n  v_retcs varchar2(200); ");
		strSql.append("\n  v_statetag number(1); ");
		strSql.append("\n begin ");
		strSql.append("\n  v_ntid:=0;");
		strSql.append("\n  begin");
		strSql.append("\n   select ywly,sell_accid,sell_noteno,ntid,statetag,operant into v_ywly,v_xsaccid,v_xsnoteno,v_ntid,v_statetag,v_operant from wareinh where accid=" + Accid + " and noteno='" + Noteno
				+ "' and statetag<2;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_retcs:='0未找到入库单!'; ");
		strSql.append("\n    goto exit; ");
		strSql.append("\n  end;");

		strSql.append("\n   if v_statetag>0 then ");
		strSql.append("\n      v_retcs:= '0当前单据已提交，不允许删除！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");
		strSql.append("\n   if v_operant<>'" + Operant + "' then ");
		strSql.append("\n      v_retcs:= '0不允许删除其他用户的单据！'; ");
		strSql.append("\n      goto exit; ");
		strSql.append("\n   end if;");

//		strSql.append("\n   delete from wareinm ");
//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "'");
//		strSql.append("\n   and exists (select 1 from wareinh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
//		strSql.append("\n   and a.accid=wareinm.accid and a.noteno=wareinm.noteno and a.statetag=0);");

//		strSql.append("\n   delete from wareincost ");
//		strSql.append("\n   where  accid=" + Accid + " and noteno='" + Noteno + "' ");
//		strSql.append("\n   and exists (select 1 from wareinh a where a.accid=" + Accid + " and a.noteno='" + Noteno + "'");
//		strSql.append("\n   and a.accid=wareincost.accid and a.noteno=wareincost.noteno and a.statetag=0);");

//		strSql.append("\n   delete from wareinh ");
//		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");
		strSql.append("\n   update wareinh set statetag=2,lastdate=sysdate");
		strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0; ");

		strSql.append("\n   if v_ywly=3 and v_xsaccid>0 then "); // 如果入库单 是载入卖家的销售单 ，把销售标志置0
		strSql.append("\n      update wareouth set bj=0 where accid=v_xsaccid and noteno=v_xsnoteno; ");
		strSql.append("\n   end if;");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs,v_ntid into :retcs,:ntid from dual;");
		strSql.append("\n end; ");
		// System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("ntid", new ProdParam(Types.INTEGER));
		// System.out.println("1111");
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		Ntid = Integer.parseInt(param.get("ntid").getParamvalue().toString());
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 增加记录
	public int Append() {
		String qry = "declare ";

		qry += "\n    v_accid  number;";
		qry += "\n    v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		qry += "\n   v_houseid  number;";
		qry += "\n   v_provid  number;";
		qry += "\n   v_id  number;";
		qry += "\n   v_ntid  number(1);";
		qry += "\n   v_retcs nvarchar2(200);";
		qry += "\n   v_progname nvarchar2(20);";
		qry += "\n   v_retbj number(1); ";
		qry += "\n begin ";
		qry += "\n  begin ";
		qry += "\n   v_ntid:=" + Ntid + ";";
		qry += "\n   if v_ntid=0 then ";
		qry += "\n     select 'CG'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareinh";
		qry += "\n       where accid=" + Accid + " and ntid=v_ntid and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CG'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n     v_progname:='采购入库';";
		qry += "\n   else";
		qry += "\n     select 'CT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareinh";
		qry += "\n       where accid=" + Accid + " and ntid=v_ntid and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CT'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n     v_progname:='采购退货';";
		qry += "\n   end if;";
		qry += "\n   v_id:=WAREINH_ID.NEXTVAL;";
		qry += "\n   insert into wareinh (id, accid,noteno,notedate,provid,houseid,ntid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		qry += "\n   values (v_id," + Accid + ",v_noteno,sysdate," + Provid + "," + Houseid + ",v_ntid,0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "\n   commit;";
		qry += "\n   v_retbj:=1; select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from wareinh where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "\n   exception";
		qry += "\n   when others then ";
		qry += "\n   begin";
		qry += "\n     rollback;";
		qry += "\n     v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"';";
		qry += "\n   end;";
		qry += "\n  end; ";
		qry += "\n  select v_retbj,v_retcs into :retbj,:retcs from dual;";
		qry += "\n end;";
		// System.out.print(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retbj", new ProdParam(Types.INTEGER));
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// int retbj =
		// Integer.parseInt(param.get("retbj").getParamvalue().toString());
		errmess = param.get("retcs").getParamvalue().toString();

		// errmess = retcs.substring(1);
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());

		// StringBuilder strSql = new StringBuilder();
		// StringBuilder strSql1 = new StringBuilder();
		// StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("id,");
		// strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		// if (Noteno != null) {
		// strSql1.append("noteno,");
		// strSql2.append("'" + Noteno + "',");
		// }
		// if (Accid != null) {
		// strSql1.append("accid,");
		// strSql2.append(Accid + ",");
		// }
		// if (Notedate != null) {
		// strSql1.append("notedate,");
		// strSql2.append("'" + Notedate + "',");
		// }
		// if (Provid != null) {
		// strSql1.append("provid,");
		// strSql2.append(Provid + ",");
		// }
		// if (Houseid != null) {
		// strSql1.append("houseid,");
		// strSql2.append(Houseid + ",");
		// }
		// if (Ntid != null) {
		// strSql1.append("ntid,");
		// strSql2.append(Ntid + ",");
		// }
		// if (Handno != null) {
		// strSql1.append("handno,");
		// strSql2.append("'" + Handno + "',");
		// }
		// if (Remark != null) {
		// strSql1.append("remark,");
		// strSql2.append("'" + Remark + "',");
		// }
		// if (Totalamt != null) {
		// strSql1.append("totalamt,");
		// strSql2.append("'" + Totalamt + "',");
		// }
		// if (Totalcurr != null) {
		// strSql1.append("totalcurr,");
		// strSql2.append("'" + Totalcurr + "',");
		// }
		// if (Operant != null) {
		// strSql1.append("operant,");
		// strSql2.append("'" + Operant + "',");
		// }
		// if (Checkman != null) {
		// strSql1.append("checkman,");
		// strSql2.append("'" + Checkman + "',");
		// }
		// if (Statetag != null) {
		// strSql1.append("statetag,");
		// strSql2.append(Statetag + ",");
		// }
		// if (Cent != null) {
		// strSql1.append("cent,");
		// strSql2.append(Cent + ",");
		// }
		// if (Orderno != null) {
		// strSql1.append("orderno,");
		// strSql2.append("'" + Orderno + "',");
		// }
		// if (Ywly != null) {
		// strSql1.append("ywly,");
		// strSql2.append(Ywly + ",");
		// }
		// if (Freecurr != null) {
		// strSql1.append("freecurr,");
		// strSql2.append("'" + Freecurr + "',");
		// }
		// if (Checkcurr != null) {
		// strSql1.append("checkcurr,");
		// strSql2.append("'" + Checkcurr + "',");
		// }
		// if (Paylist != null) {
		// strSql1.append("paylist,");
		// strSql2.append("'" + Paylist + "',");
		// }
		// if (Cleartag != null) {
		// strSql1.append("cleartag,");
		// strSql2.append(Cleartag + ",");
		// }
		// if (Totalcost != null) {
		// strSql1.append("totalcost,");
		// strSql2.append("'" + Totalcost + "',");
		// }
		// if (Bj != null) {
		// strSql1.append("bj,");
		// strSql2.append(Bj + ",");
		// }
		// if (Sell_accid != null) {
		// strSql1.append("sell_accid,");
		// strSql2.append(Sell_accid + ",");
		// }
		// if (Sell_noteno != null) {
		// strSql1.append("sell_noteno,");
		// strSql2.append("'" + Sell_noteno + "',");
		// }
		// if (Totalcheckcurr != null) {
		// strSql1.append("totalcheckcurr,");
		// strSql2.append("'" + Totalcheckcurr + "',");
		// }
		// strSql1.append("lastdate");
		// strSql2.append("sysdate");
		// strSql.append("declare ");
		// strSql.append(" v_id number; ");
		// strSql.append(" begin ");
		// strSql.append(" v_id:=wareinh_id.nextval; ");
		// strSql.append(" insert into wareinh (");
		// strSql.append(" " + strSql1.toString());
		// strSql.append(")");
		// strSql.append(" values (");
		// strSql.append(" " + strSql2.toString());
		// strSql.append(");");
		// strSql.append(" select v_id into :id from dual; ");
		// strSql.append(" end;");
		// Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		// param.put("id", new ProdParam(Types.BIGINT));
		// int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// // id = Long.parseLong(param.get("id").getParamvalue().toString());
		// if (ret < 0)
		// return 0;
		// else
		// return 1;
	}

	// 修改记录
	public int Update(JSONObject jsonObject, int changedatebj, int changeenterprice, int housecostbj, int fs, int autogd) {

		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		if (Ntid == null || Ntid < 0 || Ntid > 1) {
			errmess = "ntid不是一个有效值！";
			return 0;
		}

		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入供应商及店铺，要判断供应商及店铺
		{
			if (Provid == null || Provid == 0) {
				errmess = "provid不是一个有效值！";
				return 0;
			}
			if (Houseid == null || Houseid == 0) {
				errmess = "houseid不是一个有效值！";
				return 0;
			}
		}
		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}
		Gdcurr = (float) 0;
		String qry = "declare ";
		qry += "\n   v_count number(10);";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_totalcurr number(16,2); ";
		qry += "\n   v_totalcheckcurr number(16,2); ";
		qry += "\n   v_totalcost number(16,2); ";
		qry += "\n   v_freecurr number(16,2); ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_noteno varchar2(20); ";
		qry += "\n   v_orderno varchar2(20); ";
		qry += "\n   v_notedate date; ";
		qry += "\n begin ";
		if (!Func.isNull(Notedatestr)) {
			qry += "\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');";
			qry += "\n   if to_char(v_notedate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') or v_notedate>sysdate  then ";
			qry += "\n      v_notedate:=sysdate;";
			qry += "\n   end if;";
		} else {
			qry += "\n   v_notedate:=sysdate;";
		}
		qry += "\n   v_count:=0; v_freecurr:=0; ";
		if (Provid != null) {
			qry += "\n   select count(*) into v_count from provide where accid=" + Accid + " and Provid=" + Provid + " and statetag=1;";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:='0供应商无效！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
		}
		if (Houseid != null) {
			qry += "\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;";
			qry += "\n   if v_count=0 then ";
			qry += "\n      v_retcs:='0店铺无效！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
		}

		if (Statetag != null && Statetag == 1 && fs == 0 && Ntid == 1)// 采购退货要判断是否允许负出库 fs=1允许负出库 fs=0不允许负出库
		{
			// 要判断负库存
			qry += "\n begin";
			qry += "\n   select b.wareno||','||c.colorname||','||d.sizename into v_retcs from ";
			qry += "\n   (select wareid,colorid,sizeid,sum(amount) as amount from wareinm where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry += "\n   group by wareid,colorid,sizeid) a";
			qry += "\n   left outer join warecode b on a.wareid=b.wareid";
			qry += "\n   left outer join colorcode c on a.colorid=c.colorid";
			qry += "\n   left outer join sizecode d on a.sizeid=d.sizeid";
			qry += "\n   where a.amount>0 and a.amount>f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",a.wareid,a.colorid,a.sizeid," + Houseid + ",'" + Calcdate + "') and rownum=1;";
			qry += "\n   v_retcs:= '0'||v_retcs||'库存不足，不允许出库！'; ";
			qry += "\n   goto exit; ";

			qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN ";
			qry += "\n   v_retcs:=''; ";
			qry += "\n end;";

		}

		qry += "\n  select nvl(sum(curr),0),nvl(sum(amount),0),nvl(sum(amount*price1),0)  into v_totalcurr,v_totalamt,v_totalcheckcurr from wareinm  where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  select nvl(sum(curr),0) into v_totalcost from wareincost where accid=" + Accid + " and noteno='" + Noteno + "';";
		// if (Ntid == 1)
		// qry += "\n v_totalcurr:=v_totalcurr-v_totalcost;";
		// else
		qry += "\n  v_totalcurr:=v_totalcurr+v_totalcost;";

		if (Statetag != null && Statetag == 1 && Ntid == 0) // 采购入库提交要填入收款方式
		{
			/// * 暂时屏蔽
			if (Totalcurr != null) {
				qry += "\n  if v_totalcurr<>" + Func.getRound(Totalcurr, 2) + " then ";
				qry += "\n     update wareinh set totalcurr=v_totalcurr,totalcheckcurr=v_totalcheckcurr,totalcost=v_totalcost,totalamt=v_totalamt where accid=" //
						+ Accid + " and noteno='" + Noteno + "';";
				qry += "\n     v_retcs:='0合计金额异常" + Func.getRound(Totalcurr, 2) + ",请重新结算！'; ";
				qry += "\n     goto exit; ";
				qry += "\n  end if; ";
			}
			// qry += " delete from waresalepay where accid=" + model.accid + "
			// and noteno='" + model.noteno + "';";
			if (jsonObject.has("paylist")) {
				JSONArray jsonArray = jsonObject.getJSONArray("paylist");

				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					Float paycurr = Float.parseFloat(jsonObject2.getString("paycurr"));
					Long payid = Long.parseLong(jsonObject2.getString("payid"));
					// qry += " select
					// 'FK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000'))
					// into v_noteno from PAYCURR; ";
					qry += "\n   select 'FK'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from paycurr";
					qry += "\n   where accid=" + Accid + "  and LENGTH(noteno)=15 and substr(noteno,1,10)= 'FK'||To_Char(SYSDATE,'yyyymmdd');";

					qry += "\n  insert into paycurr (id, accid,provid,houseid,noteno,notedate,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)";
					qry += "\n  values (PAYCURR_ID.NEXTVAL," + Accid + "," + Provid + "," + Houseid + ",v_noteno,v_notedate," + payid;
					qry += "\n ," + paycurr + ",'','',1,'" + Operant + "','','" + Operant + "','" + Noteno + "',sysdate); ";

					// notetype:0采购(退货) 1物料采购(退货)
					// fs:0采购付款勾采购单1采购付款勾退货单2采购退款勾退货单
					if (autogd == 1) {
						qry += "\n  insert into paylink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)";
						qry += "\n  values (paylink_id.nextval," + Accid + ",0,'" + Noteno + "',v_noteno,0," + paycurr + ",'" + Operant + "');";
					}
					qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
					qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'采购入库付款','【增加记录】入库单号:" + Noteno + ";单据号:'||v_noteno||';金额:" + paycurr + "','" + Operant + "');";
					Gdcurr += paycurr;
				}
			}
			if (Paycurr0 != null && Paycurr0 != 0) {
				Freecurr = Paycurr0;
				if (Totalcurr >= 0) {
					// 折让金额不能大于应付金额

					if (Func.getRound(Freecurr, 2) > Func.getRound(Totalcurr, 2) || Func.getRound(Freecurr, 2) < 0) {
						errmess = "折让金额不允许大于应付金额或小于零！";
						return 0;

					}
				}
				qry += "\n  v_freecurr:=" + Freecurr + "; ";
				qry += "\n  select 'FZ'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from PAYCURR; ";
				qry += "\n  insert into paycurr (id, accid,provid,houseid,noteno,notedate,payid,curr,remark,handno,statetag,operant,checkman,lastop,ynoteno,lastdate)";
				qry += "\n  values (PAYCURR_ID.NEXTVAL," + Accid + "," + Provid + "," + Houseid + ",v_noteno,v_notedate,0";
				qry += " ,v_freecurr,'','',1,'" + Operant + "','','" + Operant + "','" + Noteno + "',sysdate); ";

				// notetype:0采购(退货) 1物料采购(退货)
				// fs:0采购付款勾采购单1采购付款勾退货单2
				if (autogd == 1) {
					qry += "\n  insert into paylink (id,accid,notetype,noteno,pnoteno,fs,curr,lastop)";
					qry += "\n  values (paylink_id.nextval," + Accid + ",0,'" + Noteno + "',v_noteno,0," + Freecurr + ",'" + Operant + "');";
				}
				qry += "\n  insert into LOGRECORD (id,accid,progname,remark,lastop)";
				qry += "\n  values (LOGRECORD_id.nextval," + Accid + ",'采购入库折让','【增加记录】入库单号:" + Noteno + ";单据号:'||v_noteno||';金额:" + Freecurr + "','" + Operant + "');";

				Gdcurr += Freecurr;
			}

		}

		qry += "\n  update wareinh set ";
		if (Provid != null) {
			qry += "provid=" + Provid + ",";
		}
		if (Houseid != null) {
			qry += "houseid=" + Houseid + ",";
		}

		if (Handno != null) {
			qry += "handno='" + Handno + "',";
		}
		if (Orderno != null) {
			qry += "orderno='" + Orderno + "',";
		}
		if (Remark != null) {
			qry += "remark='" + Remark + "',";
		}
		if (Checkman != null) {
			qry += "checkman='" + Checkman + "',";
		}
		if (Statetag != null) {
			qry += "statetag=" + Statetag + ",";
		}
		// if (changedatebj == 1 && !Func.isNull(Notedatestr)) {
		// qry += "notedate=to_date('" + Notedatestr +
		// "','yyyy-mm-ddhh24:mi:ss'),";
		// } else {
		qry += "notedate=v_notedate,";
		// }
		if (Statetag != null && Statetag == 1)// 如果是提交，记录勾单金额
		{
			qry += "gdcurr=" + Gdcurr + ",";
		}

		qry += " totalcheckcurr=v_totalcheckcurr,totalcurr=v_totalcurr,totalcost=v_totalcost,freecurr=v_freecurr,checkcurr=v_totalcurr-v_freecurr,totalamt=v_totalamt,lastdate=sysdate,bj=0";// '"
																																																// +
																																																// DateTime.Now.ToString("yyyy-MM-dd
																																																// HH:mm:ss")
																																																// +
																																																// "'");
		qry += " where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  commit;";
		if (changeenterprice == 1 && Statetag != null && Statetag == 1 && Ntid == 0) // 采购入库单提交时自动更新商品进价
		{
			// qry += " update warecode set entersale =(select a.price from
			// wareinm a where warecode.wareid=a.wareid ";
			// qry += " and a.accid=" + model.accid + " and a.noteno='" +
			// model.noteno + "' and rownum=1)";
			// qry += " where accid=" + model.accid + " and exists (select 1
			// from wareinm b where warecode.wareid=b.wareid and b.noteno='" +
			// model.noteno + "'); ";
			qry += "\n    p_wareinchangeenterprice(" + Accid + ",'" + Noteno + "');";
		}
		if (Statetag != null && Statetag == 1 && Ntid == 0) {
			qry += "\n   p_wareinpaylist(" + Accid + ",'" + Noteno + "'); ";
			qry += "\n   p_calcwareincost(" + Accid + ",'" + Noteno + "'," + housecostbj + ");"; // 计算单据中商品的成本
			// strSql.Append("\n if length(v_orderno)>0 then "); //计算订单完成情况
			qry += "\n   select orderno into v_orderno from wareinh ";
			qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   if length(v_orderno)>0 then ";// 计算采购订单完成
			qry += "\n      p_calcprovorderover(" + Accid + ",v_orderno,1);";
			qry += "\n   end if ; ";
			// strSql.Append("\n end if;");
		}
		qry += "\n  v_retcs:='1操作成功！' ; ";

		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// LogUtils.LogDebugWrite("updatewareinhbyid",qry);
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

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when
		// To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");
		strSql.append(",f_wareincostlist(a.accid,a.noteno) as cgfylist");
		strSql.append("\n FROM wareinh a ");
		strSql.append("\n left outer join provide b on a.provid=b.provid ");
		strSql.append("\n left outer join warehouse c on a.houseid=c.houseid ");
		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		System.out.println(strSql.toString());
		// a.NOTENO='CG2016101500001' and a.ACCID=7129
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM wareinh a");
		strSql.append(" left outer join provide b on a.provid=b.provid ");
		strSql.append(" left outer join warehouse c on a.houseid=c.houseid ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable() {

		String qry = "select cast('*' as nvarchar2(10)) as payno,cast('折让' as nvarchar2(10)) as payname,a.curr from paycurr a";
		qry += " where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid=0 and a.curr<>0 and a.statetag=1";
		qry += " union all";
		qry += " select b.payno,b.payname,a.curr from paycurr a";
		qry += " left outer join payway b  on a.payid=b.payid";
		qry += " where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid>0 and a.statetag=1";
		qry += " order by payno";

		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from wareinh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 载入指定采购订单明细记录到采购入库
	public int doOrdertoIn() {

		String qry = "declare ";
		qry += "  v_amount number; ";
		qry += "  v_totalamt number; ";
		qry += "  v_totalcurr number; ";
		qry += "\n   v_overbj number(1); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n begin ";

		qry += "\n   begin";
		qry += "\n     select overbj into v_overbj from provorderh where accid=" + Accid + " and noteno='" + Orderno + "' and statetag=1;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0订单未找到！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if v_overbj=1 then";
		qry += "\n      v_retcs:='0订单已完成！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";

		qry += "\n    delete from wareinm where  accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n    select a.wareid,a.colorid,a.sizeid,a.amount-a.factamt as amount,a.price0,a.discount,a.price from provorderm a";
		qry += "\n    where a.accid=" + Accid + " and a.noteno='" + Orderno + "' and a.amount>a.factamt;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n          v_amount:=v_row.amount; ";
		qry += "\n          if v_amount>0 then ";
		qry += "\n             insert into wareinm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0,lastdate)";
		qry += "\n             values ( wareinm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_amount,v_row.price0,v_row.discount,v_row.price,v_amount*v_row.price,'',sysdate); ";
		qry += "\n          end if;";
		qry += "\n      end loop;";
		qry += "\n   end;";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareinm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update wareinh set orderno='" + Orderno + "',totalcurr=v_totalcurr,totalamt=v_totalamt";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   v_retcs:='1操作完成！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		// qry += "\n p_calcallotorderover(" + Accid + ",'" + Orderno + "');";
		// // 计算订单完成情况
		qry += "\n end;";
		// LogUtils.LogDebugWrite("doOrdertoOut", qry);
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

	// 撤单
	public int Cancel(int changedatebj) {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		String qry = " declare";
		qry += "\n   v_ntid number(1); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n  v_checkman varchar2(20); ";
		qry += "\n   v_orderno varchar2(20); ";
		qry += "\n   v_progname varchar2(40); ";
		qry += "\n   v_cleartag number(1);";
		// qry += "\n v_cent number(10); ";
		// qry += "\n v_paycurrv number(12,2); ";
		qry += "\n   v_count number(10); ";
		qry += "\n begin ";

		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd') ,ntid,cleartag,orderno ,checkman";
		qry += "\n     into v_notedate,v_ntid,v_cleartag,v_orderno,v_checkman";
		qry += "\n     from wareinh where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0已审核的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		qry += "\n   select count(*) into v_count from paylink where accid=" + Accid + " and notetype=0 and noteno='" + Noteno + "';";
		qry += "\n   if v_count>0 then ";
		qry += "\n      v_retcs:='0采购单有付款勾单记录，不允许撤单！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		qry += "\n   if v_notedate<='" + Calcdate + "' then ";
		qry += "\n      v_retcs:= '0系统登账日" + Calcdate + "及之前的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		qry += "\n   if v_cleartag=1  then ";
		qry += "\n      v_retcs:= '0当前单据已被冲单，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   elsif v_cleartag=2  then ";
		qry += "\n      v_retcs:= '0当前单据是系统生成的冲账单据，不允许撤单！' ; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		qry += "\n  if v_ntid=0 then "; // 采购入库
		qry += "\n     v_progname:='采购入库'; ";
		qry += "\n     update paycurr set statetag=2 where accid=" + Accid + " and ynoteno='" + Noteno + "' and statetag=1 and (checkman is null or length(checkman)<=0); ";// and
		qry += "\n  else ";
		qry += "\n     v_progname:='采购退库'; ";
		qry += "\n  end if; ";
		qry += "\n  update wareinh set statetag=0,paylist='' where accid=" + Accid + " and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   commit;";
		qry += "\n   if length(v_orderno)>0 and v_ntid=0 then";// 如果采购入库单关联了订单，计算订单完成情况
		qry += "\n      p_calcprovorderover(" + Accid + ",v_orderno,0);"; // 计算订单完成情况
		qry += "\n   end if;";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",v_progname,'【撤单】单据号:" + Noteno + "','" + Operant + "');";
		//		if (changedatebj == 1) // 如果允许更改单据日期， 撤单要更改账套登账日期
		// qry += "\n update accreg set
		// calcdate=trunc(to_date(v_notedate,'yyyy-mm-dd')-1) where accid="
		// + Accid + " and calcdate>=v_notedate;";
		//			qry += "\n   update accreg set calcdate=trunc(to_date(v_notedate,'yyyy-mm-dd')-1)  where accid=" + Accid + " and calcdate>=to_date(v_notedate,'yyyy-mm-dd');";

		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 冲单
	public int doClear(int noteid) {
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n    v_noteno varchar2(20);";
		qry += "\n    v_count number;";
		qry += "\n    v_provid number;";
		qry += "\n    v_houseid number;";
		qry += "\n    v_retcs varchar2(200);";
		qry += "\n    v_totalcurr number;";
		qry += "\n    v_totalcheckcurr number;";
		qry += "\n    v_totalamt number;";
		qry += "\n    v_id number;";
		qry += "\n    v_ntid number(1);";
		qry += "\n    v_cleartag number(1);";
		qry += "\n begin";
		qry += "\n    select provid,houseid,cleartag into v_provid,v_houseid,v_cleartag from wareinh where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n    if v_cleartag>0 then ";
		qry += "\n       v_retcs:= '0当前单据已有冲单记录！' ; ";
		qry += "\n       goto exit; ";
		qry += "\n    end if;";
		qry += "\n    begin";

		// 生成单据号
		if (noteid == 0) {
			qry += "\n    select 'CG'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareinh ";
			qry += "\n    where accid=" + Accid + " and ntid=0 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CG'||To_Char(SYSDATE,'yyyymmdd'); ";
			qry += "\n    v_ntid:=0;";
		} else {
			qry += "\n    select 'CT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareinh ";
			qry += "\n    where accid=" + Accid + " and ntid=1 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CT'||To_Char(SYSDATE,'yyyymmdd'); ";
			qry += "\n    v_ntid:=1;";
		}
		qry += "\n    insert into wareinh (id, accid,noteno,notedate,provid,houseid,ntid,statetag,totalamt,totalcurr,remark,handno,orderno,operant,checkman,cleartag,lastdate) ";
		qry += "\n    values (WAREINH_ID.NEXTVAL," + Accid + ",v_noteno,sysdate,v_provid,v_houseid,v_ntid,1,0,0,'冲单：" + Noteno + "','" + Noteno + "','','" + Operant + "','',2,sysdate); ";

		qry += "\n    v_count:=1;";
		qry += "\n    declare cursor cur_data is ";
		qry += "\n      select a.wareid,a.colorid,a.sizeid,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0";
		qry += "\n      from wareinm a join warecode b on a.wareid=b.wareid";
		qry += "\n      where a.accid=" + Accid + " and a.noteno='" + Noteno + "';";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n    begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n         insert into wareinm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)";
		qry += "\n         values (wareinm_id.nextval," + Accid + ",v_noteno,v_row.wareid,v_row.colorid,v_row.sizeid,-v_row.amount,v_row.price0,v_row.discount,v_row.price,-v_row.curr,v_row.remark0);";
		qry += "\n         v_count:=v_count+1;";
		qry += "\n         if v_count>500 then ";
		qry += "\n            v_count:=1;";
		qry += "\n            commit;";
		qry += "\n         end if;";
		qry += "\n      end loop;";
		qry += "\n    end;";
		qry += "\n    select  nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(amount*price1),0) into v_totalamt,v_totalcurr,v_totalcheckcurr from wareinm where accid=" + Accid + " and noteno=v_noteno ;";
		qry += "\n    update wareinh set totalcurr=v_totalcurr,totalamt=v_totalamt,totalcheckcurr=v_totalcheckcurr  where accid=" + Accid + " and noteno=v_noteno;";
		qry += "\n    update wareinh set cleartag=1,remark=remark||'(已被'||v_noteno||'冲单)'  where accid=" + Accid + " and noteno='" + Noteno + "';"; // 将原单据cleartag=1，表示已冲单
		qry += "\n    commit;";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		if (noteid == 0) {
			qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'采购冲单','【冲单】原单据号:" + Noteno + ";新单据号:'||v_noteno,'" + Operant + "');";
		} else {
			qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'采购退货冲单','【冲单】原单据号:" + Noteno + ";新单据号:'||v_noteno,'" + Operant + "');";
		}
		qry += "\n    v_retcs:= '1冲单成功！'; ";
		qry += "\n    exception  ";
		qry += "\n    when others then  ";
		qry += "\n    begin ";
		qry += "\n      rollback; ";
		qry += "\n      v_retcs:= '0冲单失败！' ; ";
		qry += "\n    end; ";
		qry += "\n  end;";
		qry += "\n  <<exit>>";
		qry += "\n  select v_retcs into :retcs from dual;";
		qry += "\n end;";
		// System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret == 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 取采购单结算信息
	public int doGetcheck() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n     v_nowdate varchar2(20);";
		qry += "\n     v_nowdate0 varchar2(10);";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_accid number;";
		qry += "\n     v_provid number;";
		qry += "\n     v_curr  number(16,2); ";
		qry += "\n     v_ntid number(1);";
		qry += "\n     v_statetag number(1);";
		qry += "\n     v_totalcurr  number(16,2); ";
		qry += "\n     v_totalcost  number(16,2); ";
		qry += "\n begin";
		qry += "\n  begin";
		qry += "\n    select nvl(sum(curr),0) into  v_totalcurr from wareinm where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n    select to_char(notedate,'yyyy-mm-dd hh24:mi:ss'),provid,ntid,statetag into v_nowdate,v_provid,v_ntid,v_statetag from wareinh where accid="//
				+ Accid + " and noteno='" + Noteno + "';";
		qry += "\n    if v_statetag=0 then";
		qry += "\n       v_nowdate:=to_char(sysdate,'yyyy-mm-ddhh24:mi:ss');";
		qry += "\n    end if;";
		qry += "\n    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";
		qry += "\n    select nvl(sum(curr),0) into  v_totalcost from wareincost where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n    if v_ntid=1 then ";
		qry += "\n       v_totalcurr:=v_totalcurr-v_totalcost; ";
		qry += "\n    else";
		qry += "\n       v_totalcurr:=v_totalcurr+v_totalcost; ";
		qry += "\n    end if;";
		qry += "\n    v_curr:=f_getpaybaltimex(v_nowdate," + Accid + ",v_provid," + Houseid + ",'" + Calcdate + "');";

		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n    v_totalcurr:=0;v_curr:=0;";
		qry += "\n    end;";

		qry += "\n    select v_totalcurr,v_curr into :totalcurr,:balcurr from dual;";// where
		qry += "\n end;";
		// System.out.println("00000");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalcurr", new ProdParam(Types.DOUBLE));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
		// System.out.println("11111");
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// System.out.println("22222");
		// param.toString()
		// Double ss
		// =Double.parseDouble(param.get("totalcurr").getParamvalue().toString());

		// System.out.println("totalcurr="+ss+" param="+param.toString());
		// Float balcurr =
		// Float.parseFloat(param.get("balcurr").getParamvalue().toString());
		// System.out.println(Totalcurr);
		// 取返回值

		// errmess = "\"TOTALCURR\":\"" +
		// Func.FormatNumber(Float.parseFloat(param.get("totalcurr").getParamvalue().toString()),
		// 2) + "\""//
		// + ",\"BALCURR\": \"" +
		// Func.FormatNumber(Float.parseFloat(param.get("balcurr").getParamvalue().toString()),
		// 2) + "\"";

		errmess = "\"msg\":\"操作成功！\",\"TOTALCURR\":\"" + Func.FormatNumber(Double.parseDouble(param.get("totalcurr").getParamvalue().toString()), 2) + "\""//
				+ ",\"BALCURR\": \"" + Func.FormatNumber(Double.parseDouble(param.get("balcurr").getParamvalue().toString()), 2) + "\"";
		qry = "select payno,payid,payname from payway where accid=" + Accid + " and statetag=1 and noused=0 and payno<>'V' order by payno,payid";
		Table tb = DbHelperSQL.Query(qry).getTable(1);

		errmess += ",\"PAYLIST\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			errmess += "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\"" //
					+ ",\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\""//
					+ ",\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\"}";
		}
		errmess += "]";

		return 1;
	}

	// 取采购入库单付款记录及余额
	public int doGetpaye() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = "declare ";
		qry += "\n     v_nowdate varchar2(20);";
		qry += "\n     v_nowdate0 varchar2(10);";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_accid number;";
		qry += "\n     v_provid number;";
		qry += "\n     v_totalamt number(16,2);";
		qry += "\n     v_totalcurr number(16,2);";
		qry += "\n     v_totalcost number(16,2);";
		qry += "\n     v_balcurr  number(16,2); ";
		qry += "\n     v_freecurr  number(16,2); ";
		qry += "\n     v_zpaycurr  number(16,2); ";
		qry += "\n     v_balcurr0  number(16,2); ";
		qry += "\n     v_qkcurr  number(16,2); ";
		qry += "\n     v_statetag  number(1); ";
		qry += "\n begin";
		qry += "\n   begin";
		// qry += "\n select nvl(sum(curr),0) into v_totalcurr from wareinm
		// where accid=" + accid + " and noteno='" + noteno + "'; ";
		qry += "\n    select to_char(notedate,'yyyy-mm-dd hh24:mi:ss'),provid,nvl(totalcurr,0),nvl(totalcost,0),nvl(totalamt,0),statetag ";
		qry += "\n    into v_nowdate,v_provid,v_totalcurr,v_totalcost,v_totalamt,v_statetag from wareinh where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n    if v_statetag=0 then "; // 如果当前单据未提交，取最近时间的欠款
		qry += "\n       v_nowdate:=to_char(sysdate,'yyyy-mm-dd hh24:mi:ss');";
		qry += "\n    end if;";

		qry += "\n    v_nowdate0:=to_char(to_date(substr(v_nowdate,1,10),'yyyy-mm-dd')-1,'yyyy-mm-dd'); ";

		// 免单金额
		qry += "\n    select nvl(sum(curr),0) into v_freecurr from paycurr where accid=" + Accid + " and ynoteno='" + Noteno + "' and payid=0 and statetag=1;";
		// 收款金额
		qry += "\n    select nvl(sum(curr),0) into v_zpaycurr from paycurr where accid=" + Accid + " and ynoteno='" + Noteno + "' and payid>0 and statetag=1;";

		qry += "\n    v_balcurr:=f_getpaybaltimex(v_nowdate," + Accid + ",v_provid," + Houseid + ",'" + Calcdate + "');";

		// qry += "\n select nvl(sum(curr),0) into v_balcurr from (";
		// // --上日余额
		// qry += "\n select curr from paybal where provid=v_provid and
		// daystr=v_nowdate0";
		// qry += "\n union all";
		// // --本日初始应收款
		// qry += "\n select sum(a.curr) from firstpaycurr a";
		// qry += "\n where a.accid=" + Accid + " and a.provid=v_provid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// // --本日应付货款
		// qry += "\n union all";
		// qry += "\n select sum(case a.ntid when 0 then b.curr else -b.curr
		// end) from wareinh a left outer join wareinm b on a.accid=b.accid and
		// a.noteno=b.noteno ";
		// qry += "\n where a.accid=" + Accid + " and a.provid=v_provid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// // --应收费用
		// qry += "\n union all";
		// qry += "\n select sum(a.curr) from paycost a ";
		// qry += "\n where a.accid=" + Accid + " and a.provid=v_provid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// // --已收及折扣
		// qry += "\n union all";
		// qry += "\n select -sum(a.curr) from paycurr a ";
		// qry += "\n where a.accid=" + Accid + " and a.provid=v_provid ";
		// qry += "\n and a.statetag=1 and
		// a.notedate>=to_date(substr(v_nowdate,1,10),'yyyy-mm-dd') and
		// a.notedate<=to_date(v_nowdate,'yyyy-mm-ddhh24:mi:ss')";
		// qry += "\n ) ;";
		qry += "\n    v_qkcurr:=v_totalcurr-v_freecurr-v_zpaycurr;"; // 本次欠款
		qry += "\n    v_balcurr0:=v_balcurr-v_qkcurr;"; // 累计欠款=上次欠款+本次欠款
		// qry += "\n select
		// v_totalamt,v_totalcurr,v_balcurr,v_balcurr0,v_qkcurr,v_freecurr,v_totalcurr-v_freecurr
		// ";
		// qry += "\n into
		// :totalamt,:totalcurr,:balcurr,:balcurr0,:qkcurr,:freecurr,:checkcurr
		// from dual;";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n    v_totalamt:=0;v_totalcurr:=0;v_totalcost:=0;v_balcurr:=0;v_balcurr0:=0;v_qkcurr:=0;v_freecurr:=0;";// where
																															// custid=v_custid
																															// and
																															// accid="+accid+";";
		qry += "\n    end;";
		qry += "\n    select v_totalamt,v_totalcurr,v_totalcost,v_balcurr,v_balcurr0,v_qkcurr,v_freecurr,v_totalcurr-v_freecurr ";
		qry += "\n       into :totalamt,:totalcurr,:totalcost,:balcurr,:balcurr0,:qkcurr,:freecurr,:checkcurr  from dual;";
		qry += "\n end;";
		// System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.DOUBLE));
		param.put("totalcurr", new ProdParam(Types.DOUBLE));
		param.put("totalcost", new ProdParam(Types.DOUBLE));
		param.put("balcurr", new ProdParam(Types.DOUBLE));
		param.put("balcurr0", new ProdParam(Types.DOUBLE));
		param.put("qkcurr", new ProdParam(Types.DOUBLE));
		param.put("freecurr", new ProdParam(Types.DOUBLE));
		param.put("checkcurr", new ProdParam(Types.DOUBLE));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}

		// 取返回值
		errmess = "\"msg\":\"操作成功！\",\"TOTALAMT\":\"" + param.get("totalamt").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR\": \"" + param.get("totalcurr").getParamvalue().toString() + "\""//
				+ ",\"TOTALCOST\": \"" + param.get("totalcost").getParamvalue().toString() + "\""//
				+ ",\"BALCURR\": \"" + param.get("balcurr").getParamvalue().toString() + "\""//
				+ ",\"BALCURR0\": \"" + param.get("balcurr0").getParamvalue().toString() + "\""//
				+ ",\"QKCURR\": \"" + param.get("qkcurr").getParamvalue().toString() + "\""//
				+ ",\"FREECURR\": \"" + param.get("freecurr").getParamvalue().toString() + "\""//
				+ ",\"CHECKCURR\": \"" + param.get("checkcurr").getParamvalue().toString() + "\""//
		;
		qry = "select a.payid,cast('*' as nvarchar2(10)) as payno,cast('折让' as nvarchar2(10)) as payname,a.curr from paycurr a";
		qry += " where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid=0 and a.curr<>0 and a.statetag=1";
		qry += " union all";
		qry += " select a.payid,b.payno,b.payname,a.curr from paycurr a";
		qry += " left outer join payway b  on a.payid=b.payid";
		qry += " where a.accid=" + Accid + " and a.ynoteno='" + Noteno + "' and a.payid>0 and a.statetag=1";
		qry += " order by payno";

		Table tb = DbHelperSQL.Query(qry).getTable(1);

		errmess += ",\"PAYLIST\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				errmess += ",";
			errmess += "{\"PAYID\":\"" + tb.getRow(i).get("PAYID").toString() + "\""//
					+ ",\"PAYNAME\":\"" + tb.getRow(i).get("PAYNAME").toString() + "\""//
					+ ",\"PAYNO\":\"" + tb.getRow(i).get("PAYNO").toString() + "\""//
					+ ",\"CURR\":\"" + tb.getRow(i).get("CURR").toString() + "\""//
					+ "}";
		}
		errmess += "]";
		return 1;

	}

	// 获取批发分页数据 勾单记录
	public Table GetTableLink(QueryParam qp) {
		String qry = " select a.noteno,a.notedate,a.remark,a.curr,c.curr as curr0,a.payid,b.payname,a.operant ";
		qry += "\n from paycurr a";
		qry += "\n left outer join payway b on a.payid=b.payid";
		qry += "\n join paylink c on a.accid=c.accid and a.noteno=c.pnoteno";
		qry += "\n where a.statetag=1 and a.accid=" + Accid;
		qry += "\n and c.noteno='" + Noteno + "'";
		qry += "\n and c.notetype=0 ";
		// currlink->notetype:0采购(退货) 1物料采购(退货)
		// qry += "\n and exists (select 1 from paylink c where c.noteno='" +
		// Noteno + "'";
		// qry += "\n and c.notetype=0 and a.accid=c.accid and
		// a.noteno=c.pnoteno)";
		qp.setQueryString(qry);
		qp.setSumString("nvl(sum(curr),0) as totalcurr,nvl(sum(curr0),0) as totalcurr0");
		return DbHelperSQL.GetTable(qp);
	}

	// 审核/反审
	public int Check(int bj) {
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_ntid number(1);";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman,ntid into v_checkman,v_ntid from wareinh where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update wareinh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update wareinh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【审核】';";
		}
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",case v_ntid when 0 then '采购入库' else '采购退货' end,v_mess||'单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
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

	//json转单据
	public int json2note(JSONObject jsonObject) {
//		System.out.println("11111");
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();

		if (!Func.isNull(Notedatestr)) {
			if (Func.subString(Notedatestr, 1, 10).compareTo(Calcdate) <= 0) {
				errmess = "单据日期只能在系统登账日 " + Calcdate + " 之后！";
				return 0;
			}
		}

		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Noteno != null) {
		strSql1.append("noteno,");
		strSql2.append("v_noteno ,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");

		if (!Func.isNull(Notedatestr)) {
			strSql1.append("notedate,");
			strSql2.append("to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss'),");

		} else {
			strSql1.append("notedate,");
			strSql2.append("sysdate,");
		}

		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Provid != null) {
			strSql1.append("Provid,");
			strSql2.append(Provid + ",");
		}

		if (Handno != null) {
			strSql1.append("handno,");
			strSql2.append("'" + Handno + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		// }
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Ywly != null) {
			strSql1.append("Ywly,");
			strSql2.append(Ywly + ",");
		}
		if (Ntid != null) {
			strSql1.append("Ntid,");
			strSql2.append(Ntid + ",");
		}
		strSql1.append("operant,");
		strSql2.append("'" + Operant + "',");

		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_rs number; ");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_totalcurr number; ");
//		strSql.append("\n   v_totalcost number; ");
		strSql.append("\n   v_noteno varchar2(20);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_notedate date;");
		strSql.append("\n begin ");

		strSql.append("\n   begin ");

		if (Ntid == 0) {
			strSql.append("\n     select 'CG'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareinh");
			strSql.append("\n     where accid=" + Accid + " and ntid=0 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CG'||To_Char(SYSDATE,'yyyymmdd');");
		} else {
			strSql.append("\n     select 'CT'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from wareinh");
			strSql.append("\n     where accid=" + Accid + " and ntid=1 and LENGTH(noteno)=15 and substr(noteno,1,10)= 'CT'||To_Char(SYSDATE,'yyyymmdd');");

		}
		strSql.append("\n     v_id:=wareinh_id.nextval; ");
		strSql.append("\n     insert into wareinh (" + strSql1.toString() + ")");
		strSql.append("\n     values (" + strSql2.toString() + ");");
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = jsonObject2.has("wareid") ? Long.parseLong(jsonObject2.getString("wareid")) : 0;
			long colorid = jsonObject2.has("colorid") ? Long.parseLong(jsonObject2.getString("colorid")) : 0;
			long sizeid = jsonObject2.has("sizeid") ? Long.parseLong(jsonObject2.getString("sizeid")) : 0;
			Float amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;
			Float price0 = jsonObject2.has("price0") ? Float.parseFloat(jsonObject2.getString("price0")) : 0;
			Float discount = jsonObject2.has("discount") ? Float.parseFloat(jsonObject2.getString("discount")) : 0;
			Float price = jsonObject2.has("price") ? Float.parseFloat(jsonObject2.getString("price")) : 0;
			Float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
			String remark0 = jsonObject2.has("remark0") ? jsonObject2.getString("remark0") : "";
			strSql.append("\n  insert into wareinm (id,accid,noteno,wareid,colorid,sizeid,amount,price0,discount,price,curr,remark0)");
			strSql.append("\n  values (wareinm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," + amount //
					+ "," + price0 + "," + discount + "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from wareinm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update wareinh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1\"msg\":\"操作成功\",'||'\"NOTENO\":\"'||v_noteno||'\"';");
		//		strSql.append("\n   select '\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from firstpaycurr where id=v_id;");

		strSql.append("\n   commit;");
		strSql.append("\n   exception");
		strSql.append("\n   when others then ");
		strSql.append("\n   begin");
		strSql.append("\n     rollback;");
//		strSql.append("\n     v_retcs:='0操作失败!';");
		strSql.append("\n     v_retcs:='0操作失败：'||substr(sqlerrm,1,160);");
		strSql.append("\n   end;");
		strSql.append("\n  end; ");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
		strSql.append("\n end;");
//		System.out.print(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常1！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

}