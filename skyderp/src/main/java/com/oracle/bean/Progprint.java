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
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

// *************************************
// @author:sunhong @date:2017-03-23 18:16:19
// *************************************
public class Progprint implements java.io.Serializable {
	private Long Epid;
	private Long Progid;
	private Long Accid;
	private String Xxx;
	private Date Lastdate;
	private Long Prtid;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setXxx(String xxx) {
		this.Xxx = xxx;
	}

	public String getXxx() {
		return Xxx;
	}

	public void setPrtid(Long prtid) {
		this.Prtid = prtid;
	}

	public Long getPrtid() {
		return Prtid;
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
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update progprint set statetag=2,lastop='" + Lastop +
		// "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Progid != null) {
			strSql1.append("progid,");
			strSql2.append(Progid + ",");
		}
		if (Xxx != null) {
			strSql1.append("xxx,");
			strSql2.append("'" + Xxx + "',");
		}
		if (Prtid != null) {
			strSql1.append("prtid,");
			strSql2.append(Prtid + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=progprint_id.nextval; ");
		strSql.append("   insert into progprint (");
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
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update progprint set ");
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Progid != null) {
			strSql.append("progid=" + Progid + ",");
		}
		if (Xxx != null) {
			strSql.append("xxx='" + Xxx + "',");
		}
		if (Prtid != null) {
			strSql.append("prtid=" + Prtid + ",");
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
		strSql.append(" FROM progprint ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM progprint ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from progprint");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}

	// 获取打印参数
	public int Load(int fs, long houseid) {
		if (Progid == 0) {
			errmess = "progid无效！";
			return 0;
		}

		String qry = "";
		if (houseid > 0 || Progid == 1301 || Progid == 1101 || Progid == 1210) // 1301客户订单，1101采购订单，1015条码打印,1210商场零售
		{
			// qry = " select (select xxx from progprint where epid=" + userid +
			// " and progid=" + progid + ") as xxx";
			// qry += ",prtheader,prtfooter from pageprint where houseid=" +
			// houseid + " and progid=" + progid;

			qry = "select a.xxx,a.prtid,b.prtname,b.ipstr,b.port,c.prtheader,c.prtfooter ";
			qry += " from progprint a left outer join printset b on a.prtid=b.prtid  and b.statetag=1";
			qry += " left outer join pageprint c on c.houseid=" + houseid + " and a.progid=c.progid";
			qry += " where a.epid=" + Epid + " and a.progid=" + Progid;

		} else {
			qry = "select a.xxx,a.prtid,b.prtname,b.ipstr,b.port from progprint a left outer join printset b on a.prtid=b.prtid  and b.statetag=1";
			qry += " where a.epid=" + Epid + " and a.progid=" + Progid;
		}
		// WriteResultx("1", qry);
		// WriteDebugTXT("GetPrintcs",qry);
		Long prtid = (long) 0;
		Table tb = new Table();
		String xxx = "010000000000000000000000000000";
		String responsestr = "";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			// ----123456789012345678901234567890
			//			xxx = "010000000000000000000000000000";
			// xxx = Func.subString(xxx, 1, 30);
			//			xxx = Func.subString(xxx + "000000000000000000000000000000", 1, 30);
			responsestr = "\"msg\":\"操作成功！\",\"XXX\": \"" + xxx + "\"" + ",\"PRTID\":\"0\"" //
					+ ",\"PRTNAME\":\"\""//
					+ ",\"IPSTR\":\"\""//
					+ ",\"PORT\":\"\"";

			if (houseid > 0 || Progid == 1301)
				responsestr += ",\"PRTHEADER\":\"\",\"PRTFOOTER\":\"\"";
			// WriteResultx("1", responsestr);

		} else {
			prtid = Long.parseLong(tb.getRow(0).get("PRTID").toString());
			xxx = tb.getRow(0).get("XXX").toString();

			if (xxx.length() <= 0)
				xxx = "01";
			xxx = Func.subString(xxx + "000000000000000000000000000000", 1, 30);
			if (prtid == null) {
				responsestr = "\"msg\":\"操作成功！\",\"XXX\":\"" + xxx + "\"" + ",\"PRTID\": \"0\"" //
						+ ",\"PRTNAME\":\"\""//
						+ ",\"IPSTR\":\"\""//
						+ ",\"PORT\":\"0\"";
			} else {
				responsestr = "\"msg\":\"操作成功！\",\"XXX\": \"" + xxx + "\"" + ",\"PRTID\": \"" + prtid + "\"" //
						+ ",\"PRTNAME\": \"" + tb.getRow(0).get("PRTNAME").toString() + "\""//
						+ ",\"IPSTR\": \"" + tb.getRow(0).get("IPSTR").toString() + "\""//
						+ ",\"PORT\": \"" + tb.getRow(0).get("PORT").toString() + "\"";
			}
			if (houseid > 0 || Progid == 1301)
				responsestr += ",\"PRTHEADER\":\"" + tb.getRow(0).get("PRTHEADER").toString() + "\""//
						+ ",\"PRTFOOTER\":\"" + tb.getRow(0).get("PRTFOOTER").toString() + "\"";
		}
		if (fs == 1) { // 电脑端专用,返回可用的打印端口
			// 打印端口
			qry = "select prtid,prtname,ipstr,port from printset where accid=" + Accid + " and statetag=1 and noused=0";
			if (prtid > 0)
				qry += " and prtid<>" + prtid;
			qry += " order by prtname,prtid";
			tb = DbHelperSQL.Query(qry).getTable(1);
			// int rs=dt.
			int rs = tb.getRowCount();

			responsestr += ",\"printslist\":[";
			String fh = "";
			for (int i = 0; i < rs; i++) {
				responsestr += fh + "{\"PRTID\":\"" + tb.getRow(i).get("PRTID").toString() + "\"," //
						+ "\"PRTNAME\":\"" + tb.getRow(i).get("PRTNAME").toString() + "\""//
						+ ",\"IPSTR\": \"" + tb.getRow(0).get("IPSTR").toString() + "\""//
						+ ",\"PORT\": \"" + tb.getRow(0).get("PORT").toString() + "\"}";
				fh = ",";

			}
			responsestr += "]";
		}
		errmess = responsestr;
		return 1;

	}

	// 写打印参数
	public int Write(long houseid, String prtheader, String prtfooter) {
		if (Xxx == null)
			Xxx = "01000000000";

		Xxx = Func.subString(Xxx + "0000000000000000000000000000000000000000", 1, 30);
		// 1打印方式
		// 2打印分数
		// 3打印输出 0=蓝牙 1=后台
		// 4打印客户/供应商/会员
		// 5打印客户地址，电话,会员电话
		// 6打印店铺/调出店铺
		// 7打印店铺/调入店铺
		// 8打印摘要
		// 9打印自编号
		// 10未用
		// 11打印折扣
		// 12未用
		// 13打印销售费用
		// 14打印销售部门
		// 15打印销售人
		// 16打印制单人
		// 17打印二维码
		// 19打印折让金额
		// 20打印应结金额
		// 21打印上次欠款
		// 22打印本次欠款
		// 23打印累计欠款
		// 18商品明细排序方式 0=货号顺序，1=录入顺序

		if (Func.subString(Xxx, 3, 1).equals("1") && Prtid == 0) // 如果选择后台打印输出方式，必须选后台打印端口
		{
			errmess = "请选择后台打印端口！";
			return 0;
		}
		String qry = "declare";
		qry += "   v_count number;";
		qry += " begin";
		qry += "    select count(*) into v_count from progprint where epid=" + Epid + " and progid=" + Progid + " ;";
		qry += "    if v_count=0 then";
		qry += "       insert into progprint (epid,progid,xxx,prtid) ";
		qry += "       values (" + Epid + ", " + Progid + ",'" + Xxx + "'," + Prtid + ");";
		qry += "    else  ";
		qry += "       update progprint set xxx='" + Xxx + "',prtid=" + Prtid + ",lastdate=sysdate where epid=" + Epid + " and progid=" + Progid + "; ";
		qry += "    end if;";
		if (houseid != 0 || Progid == 1301 || Progid == 1101 || Progid == 1210) // 1301客户订单，1101采购订单，1210商场零售
		{
			// String prtfooter = "";
			// String prtheader = "";
			// if (ht["prtfooter"] != null) prtfooter =
			// ht["prtfooter"].ToString();
			// if (ht["prtheader"] != null) prtheader =
			// ht["prtheader"].ToString();

			qry += "    select count(*) into v_count from pageprint where houseid=" + houseid + " and progid=" + Progid + " ;";
			qry += "    if v_count=0 then";
			qry += "       insert into pageprint (houseid,progid,prtheader,prtfooter) ";
			qry += "          values (" + houseid + ", " + Progid + ",'" + prtheader + "','" + prtfooter + "');";
			qry += "    else  ";
			qry += "       update pageprint set prtheader='" + prtheader + "',prtfooter='" + prtfooter + "',lastdate=sysdate where houseid=" + houseid + " and progid=" + Progid + "; ";
			qry += "    end if;";
		}

		qry += " end ;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

}