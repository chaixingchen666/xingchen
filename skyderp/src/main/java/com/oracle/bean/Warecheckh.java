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
import com.common.tool.LogUtils;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-05 21:40:38
//*************************************
public class Warecheckh implements java.io.Serializable {
	private Float Id;
	private String Noteno;
	private Long Accid;
	private Long Userid;
	private String Notedatestr = "";
	// private Long Areaid;
	private Long Houseid;
	private Integer Ntid;
	private String Remark;
	private String Handno;
	private Float Totalamt;
	private Float Totalcurr;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private Date Lastdate;
	private Integer Ywly;
	private String errmess;
	private String Calcdate = ""; // 库存登账日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
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

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setNotedate(String notedatestr) {
		this.Notedatestr = notedatestr;
	}

	// public String getNotedate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Notedate);
	// }

	public void setHouseid(Long Houseid) {
		this.Houseid = Houseid;
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

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setHandno(String handno) {
		this.Handno = handno;
	}

	public String getHandno() {
		return Handno;
	}

	public void setTotalamt(Float totalamt) {
		this.Totalamt = totalamt;
	}

	public Float getTotalamt() {
		return Totalamt;
	}

	public void setTotalcurr(Float totalcurr) {
		this.Totalcurr = totalcurr;
	}

	public Float getTotalcurr() {
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

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		if (Func.isNull(Noteno)) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_statetag number(1); ");
		strSql.append("\n   v_operant varchar2(20); ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n  begin");
		strSql.append("\n    select statetag,operant into v_statetag,v_operant from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag<2;");
		strSql.append("\n  EXCEPTION WHEN NO_DATA_FOUND THEN ");
		strSql.append("\n    v_retcs:='0未找到盘点单!'; ");
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
		// strSql.Append(" delete from warecheckm ");
		// strSql.Append(" where accid=" + accid + " and noteno='" + noteno + "'");
		// strSql.Append(" and exists (select 1 from warecheckh a where a.id=" + id);
		// strSql.Append(" and a.accid=warecheckm.accid and a.noteno=warecheckm.noteno and a.statetag=0); ");
		// strSql.Append(" delete from warecheckh ");
		// strSql.Append(" where id=" + id + " and accid=" + accid + " and noteno='" + noteno + "' and statetag=0; ");
		strSql.append("\n   update warecheckh set statetag=2,lastdate=sysdate");
		strSql.append("\n   where noteno='" + Noteno + "' and accid=" + Accid + " and statetag=0; ");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
		strSql.append("end; ");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		// param.put("ntid", new ProdParam(Types.INTEGER));
		//System.out.println("1111");
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		// Ntid = Integer.parseInt(param.get("ntid").getParamvalue().toString());
		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 增加记录
	public int Append() {
		String qry = "declare ";
		qry += "   v_accid  number;";
		qry += "   v_operant nvarchar2(10);";
		qry += "   v_noteno nvarchar2(20);";
		qry += "   v_houseid  number;";
		qry += "   v_id  number;";
		qry += "   v_retcs nvarchar2(100);";
		qry += "   v_retbj number(1); ";
		qry += " begin ";
		qry += "  begin ";
		qry += "    select 'PD'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from warecheckh";
		qry += "       where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'PD'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "    v_id:=warecheckH_ID.NEXTVAL;";
		qry += "    insert into warecheckh (id, accid,noteno,notedate,houseid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		// qry += " values (v_id," + accid + ",v_noteno,to_date(to_char(sysdate,'yyyy-mm-dd')||'23:59:59','yyyy-mm-ddhh24:mi:ss')," + houseid + ",0,0,0,'','','" + lastop + "','',sysdate);";
		qry += "    values (v_id," + Accid + ",v_noteno,sysdate," + Houseid + ",0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "    commit;";
		qry += "    v_retbj:=1;  select '\"msg\":\"操作成功\",\"ID\":\"'||id||'\",\"NOTENO\":\"'||noteno||'\",\"NOTEDATE\":\"'||to_char(notedate,'yyyy-mm-dd hh24:mi:ss')||'\"' into v_retcs from warecheckh where id=v_id;  ";
		qry += "\n    insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n    values (LOGRECORD_id.nextval," + Accid + ",'库存盘点','【新增单据】单据号:'||v_noteno,'" + Operant + "');";
		qry += "    exception";
		qry += "    when others then ";
		qry += "    begin";
		qry += "      rollback;";
		qry += "      v_retbj:=0;  v_retcs:='\"msg\":\"操作失败，请重试！\"' ;";
		qry += "    end;";
		qry += "   end;";
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
		errmess = param.get("retcs").getParamvalue().toString();
		return Integer.parseInt(param.get("retbj").getParamvalue().toString());
	}

	// 修改记录
	public int Update(int changedatebj) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "单据号无效！";
			return 0;
		}
		Noteno = Noteno.toUpperCase();
		if (Statetag != null && Statetag == 1) // 如果是提交，必须转入店铺，要判断客户及店铺
		{
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

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare ");
		strSql.append("\n   v_totalcurr number;");
		strSql.append("\n   v_totalamt number; ");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n   v_count number;");
		strSql.append("\n     v_notedate date; ");
		strSql.append("\n begin ");
		if (Houseid != null) {
			strSql.append("\n   select count(*) into v_count from warehouse where accid=" + Accid + " and houseid=" + Houseid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0店铺无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		//		if (Notedatestr.length() > 0) {
		//			strSql.append("\n   v_notedate:=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss');");
		//		}

		strSql.append("\n   update warecheckh set ");
		// if (Notedate != null) {
		if (!Func.isNull(Notedatestr)) {
			// strSql.append("notedate=v_notedate,");
			strSql.append("\n   notedate=to_date('" + Notedatestr + "','yyyy-mm-dd hh24:mi:ss'),");
		}
		// else {
		// strSql.append("notedate=sysdate,");
		// }

		if (Houseid != null) {
			strSql.append("Houseid=" + Houseid + ",");
		}
		// if (Ntid != null) {
		// strSql.append("ntid=" + Ntid + ",");
		// }
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Handno != null) {
			strSql.append("handno='" + Handno + "',");
		}
		// if (Totalamt != null) {
		// strSql.append("totalamt='" + Totalamt + "',");
		// }
		// if (Totalcurr != null) {
		// strSql.append("totalcurr='" + Totalcurr + "',");
		// }
		if (Operant != null) {
			strSql.append("operant='" + Operant + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where accid=" + Accid + " and noteno='" + Noteno + "';");
		if (Statetag != null && Statetag == 1)// 提交时刷新当前单据的总数量与总金额
		{
			strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warecheckm ");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");

			strSql.append("\n   update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt");
			strSql.append("\n   where accid=" + Accid + " and noteno='" + Noteno + "';");
			// if (changedatebj == 1) {// 如果提交且允许更改单据日期，要判断是否更改账套结账日期
			// strSql.append("\n update accreg set calcdate=trunc(v_notedate-1) where accid=" + Accid + " and calcdate>=trunc(v_notedate);");
			// }
		}
		strSql.append("\n   v_retcs:='1操作成功！';");
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
		return Integer.parseInt(retcs.substring(0, 1));
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		// strSql.append(",case To_Char(a.notedate,'yyyy-mm-dd') when To_Char(SYSDATE,'yyyy-mm-dd') then '1' else '0' end as istoday");

		strSql.append(" FROM warecheckh a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warecheckh a");
		strSql.append(" left outer join warehouse b on a.houseid=b.houseid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warecheckh");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 实时盘点
	public int doActualWarecheck(long wareid, JSONArray jsonArray) {
		if (Houseid == 0 || wareid == 0) {
			errmess = "商品id或店铺id无效！";
			return 0;
		}
		if (jsonArray == null) {
			errmess = "盘点数据无效！";
			return 0;

		}
		String qry = "declare";
		qry += "\n   v_accid  number;";
		qry += "\n   v_operant nvarchar2(10);";
		qry += "\n   v_noteno nvarchar2(20);";
		qry += "\n   v_Houseid  number;";
		qry += "\n   v_id  number;";

		qry += "\n   v_bookamt  number;";
		qry += "\n   v_checkamt  number;";
		qry += "\n   v_retailsale  number;";
		qry += "\n   v_totalamt  number;";
		qry += "\n   v_totalcurr  number;";
		// qry += "\n v_bookamt number;";
		// qry += "\n v_checkamt number;";

		// qry += "\n v_notenolist nvarchar2(200);";
		// qry += "\n v_retbj nvarchar2(1); ";
		qry += "\n begin ";
		qry += "\n   select 'PD'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from warecheckh";
		qry += "\n   where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'PD'||To_Char(SYSDATE,'yyyymmdd');";
		qry += "\n   v_id:=warecheckH_ID.NEXTVAL;";
		qry += "\n   insert into warecheckh (id, accid,noteno,notedate,Houseid,statetag,totalamt,totalcurr,remark,handno,operant,checkman,lastdate)";
		qry += "\n   values (v_id," + Accid + ",v_noteno,sysdate," + Houseid + ",0,0,0,'','','" + Operant + "','',sysdate);";
		qry += "\n   select retailsale into v_retailsale from warecode where wareid=" + wareid + "; ";
		// {"wareid":"商品id","Houseid":"店铺id","rows":[{"colorid":"颜色id","sizeid":"尺码id","amount":10},{"colorid":"颜色id","sizeid":"尺码id","amount":10}]}
		long sizeid;
		long colorid;
		float amount;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			sizeid = jsonObject2.has("sizeid") ? Long.parseLong(jsonObject2.getString("sizeid")) : 0;
			colorid = jsonObject2.has("colorid") ? Long.parseLong(jsonObject2.getString("colorid")) : 0;
			amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;

			qry += "\n      v_bookamt:=f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + "," + wareid + "," + colorid + "," + sizeid + "," + Houseid + ",'" + Calcdate + "');  ";
			qry += "\n      v_checkamt:=" + amount + "; ";
			qry += "\n      v_id:=warecheckm_id.nextval;";
			qry += "\n      insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
			qry += "\n      values (v_id," + Accid + ",v_noteno," + wareid + " ," + colorid + "," + sizeid + ",v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_retailsale,(v_checkamt-v_bookamt)*v_retailsale,'',sysdate);";
		}

		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		qry += "\n     v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and a.wareid=" + wareid;
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno=v_noteno)";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno=v_noteno);";

		qry += "\n       v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		qry += "\n            v_bookamt:=f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid ," + Houseid + ",'" + Calcdate + "');  ";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid
				+ ",v_noteno,v_row.wareid,v_row.colorid,v_row.sizeid,v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_row.retailsale,(v_checkamt-v_bookamt)*v_row.retailsale,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";

		qry += "\n    select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warecheckm where accid=" + Accid + " and noteno=v_noteno;";
		qry += "\n    update warecheckh set totalamt=v_totalamt,totalcurr=v_totalcurr,statetag=1 where accid=" + Accid + " and noteno=v_noteno;";
		qry += "\n    select v_noteno into :noteno from dual; ";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("noteno", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		Noteno = param.get("noteno").getParamvalue().toString();
		errmess = Noteno;
		return 1;

	}

	// 计算盘点单账面数
	public int Calc(int calc) {
		String qry = "declare ";
		qry += "\n    v_accid number(10);";
		qry += "\n    v_noteno varchar2(20) ; ";
		qry += "\n    v_houseid number(10); ";
		qry += "\n    v_notedate date; ";
		qry += "\n    v_bookamt number; ";
		qry += "\n    v_totalcurr number; ";
		qry += "\n    v_totalamt number; ";
		qry += "\n    v_count number; ";
		// qry += "\n v_notedate date; ";
		qry += "\n begin ";
		// qry += "\n select notedate into v_notedate from warecheckh where accid=" + accid + " and noteno='" + noteno + "';";
		// qry += " v_accid:=" + accid + "; ";
		// qry += " v_noteno:='" + noteno + "';";

		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		// qry += "\n v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "')";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "');";

		qry += "\n        v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,0,0,0,v_row.retailsale,0,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";
		qry += "\n  v_count:=0;";

		// 计算
		qry += "\n  select houseid,notedate into v_houseid,v_notedate from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0;";

		qry += "\n  declare cursor cur_data is select id,wareid,colorid,sizeid from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";// for update nowait;";
		qry += "\n       v_row cur_data%rowtype;  ";
		qry += "\n  begin";
		qry += "\n    for v_row in cur_data loop";
		qry += "\n      select f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_houseid,'" + Calcdate + "') into v_bookamt from dual;";
		if (calc == 0) {
			qry += "\n update warecheckm set bookamt=v_bookamt,amount=checkamt-v_bookamt,curr=(checkamt-v_bookamt)*price where id=v_row.id;";
		} else if (calc == 1) {
			qry += "\n update warecheckm set bookamt=v_bookamt,checkamt=v_bookamt,amount=0,curr=0 where id=v_row.id;";
		} else {
			qry += "\n update warecheckm set bookamt=v_bookamt,checkamt=0,amount=-v_bookamt,curr=-v_bookamt*price where id=v_row.id;";
		}
		qry += "\n       v_count:=v_count+1;";
		qry += "\n       if v_count>1000 then";
		qry += "\n          commit; ";
		qry += "\n          v_count:=0;";
		qry += "\n       end if;";
		qry += "\n    end loop;";
		qry += "\n  end;";
		qry += "\n  commit; ";

		qry += "\n  select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n  update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt ";
		qry += "\n  where accid=" + Accid + " and noteno='" + Noteno + "';";
		// qry += "\n commit;";

		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n  return;   ";
		qry += "\n end;";
		LogUtils.LogDebugWrite("CalcWarecheck", qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 盘点单载入库存
	public int Load(JSONObject jsonObject, int loadbj, int change, int qxbj) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String qry = " select to_char(notedate,'yyyy-mm-dd hh24:mi:ss') as notedate from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0";
		String nowdate = DbHelperSQL.RunSql(qry).toString();
		if (nowdate.length() <= 0) {
			errmess = "未找到盘点单：" + Noteno;
			return 0;
		}
		vTotalCxkczy dal = new vTotalCxkczy();
		DbHelperSQL.JsonConvertObject(dal, jsonObject);// 读取json数据到表类
		dal.setQxbj(qxbj);
		dal.setCxdatetime(nowdate);
		dal.setAccid(Accid);
		dal.setUserid(Userid);
		dal.setCalcdate(Calcdate);
		// dal.setHouseid(Houseid);
		// dal.setAreaid(Areaid);
		dal.doCxkczy();
		Houseid = dal.getHouseid();
		if (Houseid == null || Houseid == 0) {
			errmess = "houseid不是一个有效值！";
			return 0;

		}
		qry = "declare ";

		qry += "\n   v_notedate date; ";
		qry += "\n   v_totalcurr number; ";
		qry += "\n   v_totalamt number; ";
		qry += "\n   v_count number; ";
		qry += "\n begin ";
		// qry += "\n select notedate into v_notedate from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "'";
		if (loadbj == 0) // loadbj:0清空原记录 1追加新记录
		{
			// qry += "\n delete from warecheckm where accid=" + accid + " and noteno='" + noteno + "';";
			qry += "\n    declare  ";
			qry += "\n       cursor mycursor is ";
			qry += "         select ROWID from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "'";
			qry += "\n       order by rowid; ";
			qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
			qry += "\n       v_rowid   rowid_table_type; ";
			qry += "\n    BEGIN ";
			qry += "\n       open mycursor; ";
			qry += "\n       loop ";
			qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
			qry += "\n          exit when v_rowid.count=0; ";
			qry += "\n          forall i in v_rowid.first..v_rowid.last ";
			qry += "\n          delete from warecheckm where rowid=v_rowid(i); ";
			qry += "\n          commit; ";
			qry += "\n        end loop; ";
			qry += "\n       close mycursor; ";
			qry += "\n    END; ";
		}
		qry += "\n  declare cursor cur_data is";
		qry += "\n    select a.wareid,a.colorid,a.sizeid,a.amount,b.retailsale,a.amount*b.retailsale as retailcurr from v_cxkczy_data a";
		qry += "\n    left outer join warecode b on a.wareid=b.wareid";
		qry += "\n    where a.epid=" + Userid + " and a.houseid=" + Houseid;
		// if (minprice > 0) { qry += " and b.retailsale>=" + minprice; }
		// if (maxprice > 0) { qry += " and b.retailsale<=" + maxprice; }
		if (change == 1) // 只载入负库存
		{
			qry += "  and a.amount<0";
		}
		if (loadbj == 1) // loadbj:0清空原记录 1追加新记录
		{
			qry += "\n  and not exists (select 1 from warecheckm c where c.accid=" + Accid + " and c.noteno='" + Noteno + "'";
			qry += "\n  and a.wareid=c.wareid and a.colorid=c.colorid and a.sizeid=c.sizeid)  ";
		}
		qry += "  ; ";
		qry += "\n    v_row cur_data%rowtype;";
		qry += "\n begin ";
		qry += "\n    v_count:=1;";
		qry += "\n    for v_row in cur_data loop";
		qry += "\n        insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0)";
		qry += "\n        values (warecheckm_id.nextval," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,v_row.amount,0,-v_row.amount,v_row.retailsale,-v_row.retailcurr,0);";
		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>500 then ";
		qry += "\n           commit;";
		qry += "\n           v_count:=1;";
		qry += "\n        end if ;";
		qry += "\n    end loop;";
		qry += "\n end;";

		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt ";
		qry += "\n   where accid=" + Accid + " and noteno='" + Noteno + "';";

		qry += "\n end;";
		//System.out.println(qry);
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 合并临盘单
	public int Merge(JSONObject jsonObject, int fs) {
		if (Noteno == null || Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		String wherestr = "";
		if (!jsonObject.has("tempchecklist")) {
			errmess = "未传入要合并的临时盘点单1！";
			return 0;
		}
		JSONArray jsonArray = jsonObject.getJSONArray("tempchecklist");
		if (jsonArray.size() == 0) {
			errmess = "未传入要合并的临时盘点单2！";
			return 0;
		}

		String fh = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			wherestr += fh + " a.noteno ='" + jsonObject2.getString("noteno") + "'";
			fh = " or ";
		}

		String qry = "declare ";
		qry += "\n    v_id number;  ";
		qry += "\n    v_totalcurr number;  ";
		qry += "\n    v_totalamt number;  ";
		qry += "\n    v_count number;  ";
		qry += "\n    v_fs number(1);  ";
		qry += "\n    v_bookamt number;";
		qry += "\n    v_checkamt number;";
		qry += "\n    v_houseid number;";
		qry += "\n    v_notedate date;  ";
		qry += "\n begin ";
		// qry += " v_noteno:='" + noteno + "'; ";
		// qry += " v_accid:=" + accid + "; ";
		qry += "\n   v_count:=0; ";
		qry += "\n   v_fs:=" + fs + "; ";
		qry += "\n   declare cursor cur_data is ";
		qry += "\n      select a.wareid,a.colorid,a.sizeid,b.retailsale,sum(a.amount) as amount";
		qry += "\n      from tempcheckm a ";
		qry += "\n      left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n      where a.accid=" + Accid + " and (" + wherestr + ")";
		qry += "\n      group by a.wareid,a.colorid,a.sizeid,b.retailsale;";
		qry += "\n      v_row cur_data%rowtype;";
		qry += "\n   begin";
		qry += "\n      for v_row in cur_data loop";
		qry += "\n      begin";
		qry += "\n         select id into v_id from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "' ";
		qry += "\n           and wareid=v_row.wareid and colorid=v_row.colorid and sizeid=v_row.sizeid;";
		qry += "\n         update warecheckm set checkamt=checkamt+v_row.amount,amount=amount+v_row.amount,curr=(amount+v_row.amount)*price where id=v_id;";
		qry += "\n      EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n         if v_fs=0 then"; // fs:0=更新并追加所有商品 1=仅更新已有商品
		qry += "\n            v_id:=warecheckm_id.nextval;";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0)";
		qry += "\n            values (v_id," + Accid + ",'" + Noteno + "',v_row.wareid,v_row.colorid,v_row.sizeid,0,v_row.amount,v_row.amount,v_row.retailsale,v_row.amount*v_row.retailsale,'');";
		qry += "\n         end if;";
		qry += "\n      end;  ";
		qry += "\n      v_count:=v_count+1;";
		qry += "\n      if v_count>1000 then";
		qry += "\n         commit;";
		qry += "\n         v_count:=0;";
		qry += "\n      end if;";
		qry += "\n      end loop;";

		// qry += " select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warecheckm where accid=" + accid + " and noteno='" + noteno + "';";
		// qry += " update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + accid + " and noteno='" + noteno + "'; ";
		qry += "\n   end;";
		qry += "\n   commit; ";

		// 将商品，颜色未输入的尺码自动填为0，表示数量为0
		qry += "\n     v_checkamt:=0; ";
		qry += "\n     declare cursor cur_data is";
		qry += "\n       select a.wareid,b.colorid,c.sizeid,a.retailsale from warecode a";
		qry += "\n       join warecolor b on a.wareid=b.wareid";
		qry += "\n       join colorcode d on b.colorid=d.colorid";
		qry += "\n       join sizecode c on a.accid=c.accid and a.sizegroupno=c.groupno ";
		qry += "\n       where c.statetag=1 and c.noused=0 and d.statetag=1 and d.noused=0 and a.accid=" + Accid;
		qry += "\n       and not exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid and c.sizeid=e.sizeid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "' )";

		qry += "\n       and exists (select 1 from warecheckm e where a.wareid=e.wareid and b.colorid=e.colorid";
		qry += "\n       and e.accid=" + Accid + " and e.noteno='" + Noteno + "' );";

		qry += "\n       v_row cur_data%rowtype;";
		qry += "\n     begin";
		qry += "\n        for v_row in cur_data loop";
		qry += "\n            v_bookamt:=0;";// f_getwareamountx(to_char(sysdate,'yyyy-mm-dd')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid ," + Houseid + ",'" + Calcdate + "'); ";
		qry += "\n            insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0,lastdate)";
		qry += "\n            values (warecheckm_id.nextval," + Accid + ",'" + Noteno
				+ "' ,v_row.wareid,v_row.colorid,v_row.sizeid,v_bookamt,v_checkamt,v_checkamt-v_bookamt,v_row.retailsale,(v_checkamt-v_bookamt)*v_row.retailsale,'',sysdate);";
		qry += "\n        end loop;";
		qry += "\n     end;";

		// 刷新账面数
		qry += "\n   v_count:=0;";
		qry += "\n   select houseid,notedate into v_houseid,v_notedate from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "' and statetag=0;";
		qry += "\n   declare cursor cur_data is select id,wareid,colorid,sizeid from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";// for update nowait;";
		qry += "\n      v_row cur_data%rowtype;  ";
		qry += "\n   begin";
		qry += "\n     for v_row in cur_data loop";
		// qry += "\n select f_getwareamountx(to_char(v_notedate,'yyyy-mm-dd')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_houseid,'" + Calcdate + "') into v_bookamt from dual;";
		qry += "\n       select f_getwaresumskux(to_char(v_notedate,'yyyy-mm-dd hh24:mi:ss')," + Accid + ",v_row.wareid,v_row.colorid,v_row.sizeid,v_houseid,'" + Calcdate + "') into v_bookamt from dual;";
		// if (calc == 0) {
		qry += "\n       update warecheckm set bookamt=v_bookamt,amount=checkamt-v_bookamt,curr=(checkamt-v_bookamt)*price where id=v_row.id;";
		// else
		// if (calc == 1) { qry += " update warecheckm set bookamt=v_bookamt,checkamt=v_bookamt,amount=0,curr=0 where id=v_row.id;"; }
		// else
		// { qry += " update warecheckm set bookamt=v_bookamt,checkamt=0,amount=-v_bookamt,curr=-v_bookamt*price where id=v_row.id;"; }
		qry += "\n        v_count:=v_count+1;";
		qry += "\n        if v_count>1000 then";
		qry += "\n           commit; ";
		qry += "\n           v_count:=0;";
		qry += "\n        end if;";
		qry += "\n     end loop;";
		qry += "\n   end;";
		qry += "\n   commit; ";
		qry += "\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr  from warecheckm where accid=" + Accid + " and noteno='" + Noteno + "';";
		qry += "\n   update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "'; ";
		qry += "\n   commit;";

		qry += "\n end; ";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 撤单
	public int Cancel(int changedatebj) {
		if (Noteno.equals("")) {
			errmess = "单据号无效！";
			return 0;
		}
		String qry = " declare";
		// qry += "\n v_ntid number(1); ";
		qry += "\n   v_notedate varchar2(10); ";
		qry += "\n   v_retcs varchar2(200); ";
		qry += "\n   v_count number(10); ";
		qry += "\n   v_checkman varchar2(20); ";
		qry += "\n begin ";
		qry += "\n   begin";
		qry += "\n     select to_char(notedate,'yyyy-mm-dd'),checkman ";
		qry += "\n     into v_notedate,v_checkman";
		qry += "\n     from warecheckh where accid=" + Accid + "  and noteno='" + Noteno + "' and statetag=1; ";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN ";
		qry += "\n     v_retcs:='0未找到单据!';";
		qry += "\n     goto exit;";
		qry += "\n   end;";
		qry += "\n   if length(v_checkman)>0 then ";// 如果收款单关联了销售单，重新刷新销售的的结算方式
		qry += "\n      v_retcs:= '0已审核的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";
		qry += "\n   if v_notedate<='" + Calcdate + "' then ";
		qry += "\n      v_retcs:= '0系统登账日" + Calcdate + "及之前的单据不允许撤单！'; ";
		qry += "\n      goto exit; ";
		qry += "\n   end if; ";

		// qry += "\n select count(*) into v_count from allotouth where accid=" + Accid + " and orderno='" + Noteno + "' and statetag<=1 and rownum=1;";
		// qry += "\n if v_count>0 then ";
		// qry += "\n v_retcs:= '0当前订单已有调出记录，不允许撤单！' ; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if;";

		// if (changedatebj == 0) // 不允许更改单据日期
		// {
		// qry += "\n if v_notedate<to_char(sysdate,'yyyy-mm-dd') then ";
		// qry += "\n v_retcs:= '0今日之前的单据不允许撤单！'; ";
		// qry += "\n goto exit; ";
		// qry += "\n end if; ";
		// }
		qry += "\n   update warecheckh set statetag=0 where accid=" + Accid + "  and noteno='" + Noteno + "' ; ";

		qry += "\n   v_retcs:= '1操作成功！';  ";

		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'商品盘点','【撤单】单据号:" + Noteno + "','" + Operant + "');";
		qry += "\n   commit;";
		// if (changedatebj == 1) // 如果允许更改单据日期， 撤单要更改账套登账日期
		// qry += "\n update accreg set calcdate=trunc(to_date(v_notedate,'yyyy-mm-dd')-1) where accid=" + Accid + " and calcdate>=to_date(v_notedate,'yyyy-mm-dd');";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		//System.out.println(qry);

		// LogUtils.LogDebugWrite("调拨订单撤单", qry);
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

	// 审核/反审
	public int Check(int bj) {
		String qry = "declare";
		qry += "\n   v_count number;";
		qry += "\n   v_checkman varchar2(20);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_mess varchar2(200);";
		qry += "\n begin";
		qry += "\n   select checkman into v_checkman from warecheckh where accid=" + Accid + " and noteno='" + Noteno + "';";
		if (bj == 1) {// 审核
			qry += "\n   if length(v_checkman)>0 then ";
			qry += "\n      v_retcs:='0单据已审核！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warecheckh set checkman='" + Operant + "' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【取消审核】';";

		} else {// 取消审核
			qry += "\n   if v_checkman<>'" + Operant + "' then ";
			qry += "\n      v_retcs:='0系统不允许取消其他用户审核的单据！';";
			qry += "\n      goto exit;";
			qry += "\n   end if;";
			qry += "\n   update warecheckh set checkman='' where accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n   v_mess:='【审核】';";
		}
		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   insert into LOGRECORD (id,accid,progname,remark,lastop)";
		qry += "\n   values (LOGRECORD_id.nextval," + Accid + ",'库存盘点',v_mess||'单据号:" + Noteno + "','" + Operant + "');";
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
		//		if (Ntid != null) {
		//			strSql1.append("Ntid,");
		//			strSql2.append(Ntid + ",");
		//		}

		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
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
		strSql.append("\n     select 'PD'||To_Char(SYSDATE,'yyyymmdd')||trim(to_char(nvl(to_number(max(substr(noteno,11,5)),'99999'),0)+1,'00000')) into v_noteno from warecheckh");
		strSql.append("\n     where accid=" + Accid + " and LENGTH(noteno)=15 and substr(noteno,1,10)= 'PD'||To_Char(SYSDATE,'yyyymmdd');");
		strSql.append("\n     v_id:=warecheckh_id.nextval; ");
		strSql.append("\n     insert into warecheckh (" + strSql1.toString() + ")");
		strSql.append("\n     values (" + strSql2.toString() + ");");
		JSONArray jsonArray = jsonObject.getJSONArray("warereclist");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long wareid = jsonObject2.has("wareid") ? Long.parseLong(jsonObject2.getString("wareid")) : 0;
			long colorid = jsonObject2.has("colorid") ? Long.parseLong(jsonObject2.getString("colorid")) : 0;
			long sizeid = jsonObject2.has("sizeid") ? Long.parseLong(jsonObject2.getString("sizeid")) : 0;
			Float amount = jsonObject2.has("amount") ? Float.parseFloat(jsonObject2.getString("amount")) : 0;
			Float bookamt = jsonObject2.has("bookamt") ? Float.parseFloat(jsonObject2.getString("bookamt")) : 0;
			Float checkamt = jsonObject2.has("checkamt") ? Float.parseFloat(jsonObject2.getString("checkamt")) : 0;
			Float price = jsonObject2.has("price") ? Float.parseFloat(jsonObject2.getString("price")) : 0;
			Float curr = jsonObject2.has("curr") ? Float.parseFloat(jsonObject2.getString("curr")) : 0;
			String remark0 = jsonObject2.has("remark0") ? jsonObject2.getString("remark0") : "";
			strSql.append("\n  insert into warecheckm (id,accid,noteno,wareid,colorid,sizeid,bookamt,checkamt,amount,price,curr,remark0)");
			strSql.append("\n  values (warecheckm_id.nextval," + Accid + ",v_noteno," + wareid + "," + colorid + "," + sizeid + "," //
					+ bookamt + "," + checkamt + "," + amount + "," + price + "," + curr + ",'" + remark0 + "');");
		}

		strSql.append("\n   select nvl(sum(amount),0),nvl(sum(curr),0) into v_totalamt,v_totalcurr from warecheckm where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   update warecheckh set totalcurr=v_totalcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno;");
		strSql.append("\n   v_retcs:='1'||'\"msg\":\"操作成功\",\"NOTENO\":\"'||v_noteno||'\"';");
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