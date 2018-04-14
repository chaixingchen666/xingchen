/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

/**
 * @author Administrator
 *
 */
public class vTotalcustorderwc {

	private String Fieldlist = ""; // 要显示的项目列表
	private String Grouplist = ""; // 分组的项目列表
	private String Sortlist = ""; // 排序的项目列表
//	private String Sumlist = ""; // 要求和的项目列表
//	private String Calcfield = ""; // 要求和的项目列表

//	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
//	private Long Provid = (long) -1;
//	private Long Brandid = (long) -1;
	private Long Typeid = (long) -1;
	private String Seasonname = "";
	private String Warename = "";
	private String Wareno = "";
	private String Orderno = "";
	private String Prodyear = "";
//	private Long Houseid = (long) 0;
	private String Custidlist = "";
	private String Brandidlist = "";
	private String Providlist = "";
	private Integer Datebj = 0;// 1=按时间统计完成情况
	// private Integer Hzfs = 0;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型

	private String Mindate = "";
	private String Maxdate = "";

	private String Mindate0 = "";
	private String Maxdate0 = "";
	private String Mindate1 = "";
	private String Maxdate1 = "";

	private String Accbegindate = "2000-01-01";
	// private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
//	private Integer Maxday = 0;// 最大查询天数

	private String errmess;

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public String getErrmess() {
		return errmess;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

//	public void setMaxday(Integer maxday) {
//		Maxday = maxday;
//	}

	public int doTotal() {
		if (Mindate0.length() < 0 || Maxdate0.length() < 0) {
			errmess = "mindate0或maxdate0不是一个有效值！";
			return 0;
		}
//		if (Mindate1.length() < 0 || Maxdate1.length() < 0) {
//			errmess = "mindate1或maxdate1不是一个有效值！";
//			return 0;
//		}

		// 校验开始查询日期
		// MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		MinDateValid mdv = new MinDateValid(Mindate0, Nowdate, Accbegindate, 0);
		Mindate0 = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate0.compareTo(Maxdate0) > 0)
			Maxdate0 = Mindate0; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		Maxdate0 += " 23:59:59";
		Mindate0 += " 00:00:00";
		
		mdv = new MinDateValid(Mindate1, Nowdate, Accbegindate, 0);
		Mindate0 = mdv.getMindate();
		// String warning = mdv.getErrmess();
//		if (Mindate1.compareTo(Maxdate1) > 0)
//			Maxdate1 = Mindate1; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
//		Maxdate1 += " 23:59:59";
//		Mindate1 += " 00:00:00";
		Maxdate1=Maxdate0;
		Mindate1=Mindate0;

		String qry = "declare   ";
		qry += "    v_epid number; ";
		qry += "    v_accid number; ";
		qry += "    v_mindate0 varchar2(10); ";
		qry += "    v_maxdate0 varchar2(10); ";
		qry += "    v_mindate1 varchar2(10); ";
		qry += "    v_maxdate1 varchar2(10); ";
		qry += " begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_cxcustorderwc_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxcustorderwc_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_cxcustorderwc_data (id,epid,custid,wareid,colorid,sizeid,amount0,amount1) ";
		qry += "\n    select v_cxcustorderwc_data_id.nextval," + Userid + ",custid,wareid,colorid,sizeid,amount0,amount1 from (  ";
		qry += "\n    select custid,wareid,colorid,sizeid,sum(amount0) as amount0,sum(amount1) as amount1 from (";
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,sum(b.amount) as amount0,0 as amount1 ";
		qry += "\n    from custorderh a  ";
		qry += "\n    join custorderm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid + " ";
		qry += "\n    and a.notedate>=to_date('" + Mindate0 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate0 + "','yyyy-mm-dd hh24:mi:ss')";
		if (!Func.isNull(Orderno))
			qry += "   and a.noteno= '" + Orderno + "'";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Wareid > 0)
			qry += "   and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "   and b.sizeid=" + Sizeid;
		if (!Func.isNull(Wareno))
			qry += "   and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Func.isNull(Warename))
			qry += "   and c.warename like '%" + Warename + "%'";
		if (Custidlist.length() >0)
			qry += "   and "+Func.getCondition("a.Custid", Custidlist);
		if (Brandidlist.length() >0)
			qry += "   and "+Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() >0)
			qry += "   and "+Func.getCondition("c.provid", Providlist);
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}

		if (Seasonname.length() > 0)
			qry += "   and c.seasonname='" + Seasonname + "'";

		if (Prodyear.length() > 0)
			qry += "   and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid ";
		qry += "\n    union all ";
		// --批发出库
		qry += "\n    select a.custid,b.wareid,b.colorid,b.sizeid,0 as amount0,sum(b.amount) as amount1 ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid + " and a.ntid=1 ";
		if (Datebj == 0)// 1=按时间统计，0=按订单统计
			qry += " and length(a.orderno)>0";
		qry += "\n    and a.notedate>=to_date('" + Mindate1 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate1 + "','yyyy-mm-dd hh24:mi:ss')";
		if (Orderno.length() > 0)
			qry += "   and a.orderno= '" + Orderno + "'";

		// -- and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid="+userid+" )
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " ) )";
		}
		if (Custidlist.length() >0)
			qry += "   and "+Func.getCondition("a.Custid", Custidlist);
		if (Brandidlist.length() >0)
			qry += "   and "+Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() >0)
			qry += "   and "+Func.getCondition("c.provid", Providlist);
		if (Wareid > 0)
			qry += "   and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "   and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "   and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "   and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "   and c.warename like '%" + Warename + "%'";
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "   and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "   and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.custid,b.wareid,b.colorid,b.sizeid )";
		qry += "\n    group by custid,wareid,colorid,sizeid); ";
		qry += "\n    delete from v_cxcustorderwc_data where epid=" + Userid + " and amount0=0;";
		qry += "\n    select nvl(sum(amount0),0),nvl(sum(amount1),0),nvl(sum(amount0-amount1),0) into :totalamt0,:totalamt1,:totalamt2 from v_cxcustorderwc_data where epid=" + Userid + "; ";
		qry += "\n end;";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalamt2", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"TOTALAMT0\":\"" + param.get("totalamt0").getParamvalue() + "\"" //
				+ ",\"TOTALAMT1\":\"" + param.get("totalamt1").getParamvalue() + "\"" //
				+ ",\"TOTALAMT2\":\"" + param.get("totalamt2").getParamvalue() + "\"" //
		// + ",\"warning\":\"" + mdv.getErrmess() + "\""
		;
		return 1;

	}

	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		// Fieldlist="";
		Fieldlist = " sum(a.amount0) as amount0,sum(a.amount1) as amount1,sum(a.amount0-a.amount1) as amount2";
		Fieldlist += " ,to_char(case sum(a.amount0) when 0 then 100 else round(sum(a.amount1)/sum(a.amount0),2)*100 end) as ratewc, min(id) as keyid";
		// Sumlist="";
		strSql.append("select ");
		// if (!Grouplist.equals(""))
		strSql.append(Grouplist + ",");
		// if (!Fieldlist.equals(""))
		// Fieldlist += ",";

		// if (!Grouplist.equals("")) // 汇总查询
		// {
		// Fieldlist += "min(id) as keyid";
		// } else
		// Fieldlist += "id as keyid";
		strSql.append(Fieldlist);
		strSql.append("\n FROM v_cxcustorderwc_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand e on b.brandid=e.brandid");
		strSql.append("\n left outer join waretype f on b.typeid=f.typeid");
		strSql.append("\n left outer join customer g on a.custid=g.custid");

		strSql.append("\n where a.epid=" + Userid);
		// if (!Grouplist.equals(""))
		strSql.append("\n group by " + Func.delGroupOfAs(Grouplist));

		//System.out.println("aaa2="+strSql.toString());
		qp.setQueryString(strSql.toString());
		if (Sortlist.equals(""))
			Sortlist = "keyid";
		else
			Sortlist += ",keyid";
		qp.setSortString(Sortlist);
		// qp.setSumString(Sumlist);
		// qp.setCalcfield(Calcfield);
		// qp.setTotalString("\"warning\":\""+warning+"\"");
		return DbHelperSQL.GetTable(qp);
	}
}
