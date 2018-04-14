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
public class vTotalxstbfx {

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd
	// HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	private Long Dptid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Typeid = (long) -1;
	private String Seasonname = "";
	private String Warename = "";
	private String Wareno = "";
	private String Prodyear = "";
	private Long Houseid = (long) 0;
	private Long Custid = (long) 0;
	private Integer Xsid = 2;// 1=启用权限控
	private Integer Hzfs = 0;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型

	private String Mindate0 = "";
	private String Maxdate0 = "";
	private String Mindate1 = "";
	private String Maxdate1 = "";

	private String Accbegindate = "2000-01-01";
	// private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Maxday = 0;// 最大查询天数

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

	public void setMaxday(Integer maxday) {
		Maxday = maxday;
	}

	public int doTotal() {
		// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型
		if (Hzfs == null || Hzfs < 0 || Hzfs > 5) {
			errmess = "hzfs不是一个有效值！";
			return 0;
		}
		if (Mindate0.length() <= 0 || Maxdate0.length() <= 0) {
			errmess = "mindate0或maxdate0不是一个有效值！";
			return 0;
		}
		if (Mindate1.length() <= 0 || Maxdate1.length() <= 0) {
			errmess = "mindate1或maxdate1不是一个有效值！";
			return 0;
		}
		// System.out.println("33333");
		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate0, Nowdate, Accbegindate, 0);
		Mindate0 = mdv.getMindate();
		String warning = mdv.getErrmess();
		if (Mindate0.compareTo(Maxdate0) > 0)
			Maxdate0 = Mindate0; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		Maxdate0 += " 23:59:59";
		Mindate0 += " 00:00:00";

		mdv = new MinDateValid(Mindate1, Nowdate, Accbegindate, 0);
		Mindate1 = mdv.getMindate();
		if (warning.length() <= 0)
			warning = mdv.getErrmess();
		if (Mindate1.compareTo(Maxdate1) > 0)
			Maxdate1 = Mindate1; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		Maxdate1 += " 23:59:59";
		Mindate1 += " 00:00:00";

		String qry = "declare ";
		qry += "\n     v_houseid  number;  ";
		qry += "\n     v_custid  number;  ";
		qry += "\n     v_ntid  number;  "; // --业务类型 0=出货 1=退货 2=所有
		// qry += " v_xsid number; "; // --销售方式:0=零售 1=经销 2=所有
		qry += "\n     v_saleid  number;  "; // --销售类型:0=零售, 其它=销售类型
		qry += "\n begin  ";

		qry += "\n   delete from v_xstbfx_data where epid=" + Userid + "; ";

		qry += "\n   insert into v_xstbfx_data (id,epid,custid,houseid,wareid,saleid,amount0,curr0,amount1,curr1) ";
		qry += "\n   select v_xstbfx_data_id.nextval," + Userid + ",custid,houseid,wareid,saleid,amount0,curr0 ,amount1,curr1 from ( ";

		qry += "\n    select custid,houseid,wareid,saleid,sum(amount0) as amount0,sum(curr0) as curr0 ,sum(amount1) as amount1,sum(curr1) as curr1 from (";
		// 零售，批发
		qry += "\n    select a.custid,a.houseid,b.wareid,b.saleid,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount0,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr0,0 as amount1,0 as curr1  ";
		qry += "\n    from wareouth a   ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
		qry += "\n    where a.statetag=1  and a.accid=" + Accid + " ";
		if (Xsid == 0) {
			qry += "  and a.ntid=0";
		} else // 零售
		if (Xsid == 1) {
			qry += "  and (a.ntid=1 or a.ntid=2)";
		} // 批发
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " ))  ";
			if (Xsid == 1)
				qry += "\n    and  exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " ) ";
		}
		// qry += "\n and to_char(a.notedate,'yyyy-mm-dd') >=v_mindate0 ";
		// qry += "\n and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate0 ";
		qry += "    and a.notedate>=to_date('" + Mindate0 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate0 + "','yyyy-mm-dd hh24:mi:ss')";

		if (Custid > 0) {
			qry += "\n  and a.custid=" + Custid;
		}

		if (Houseid > 0) {
			qry += "  and a.houseid=" + Houseid;
		}

		if (Wareid > 0) {
			qry += "  and b.wareid=" + Wareid;
		}

		if (Brandid >= 0) {
			qry += "  and c.brandid=" + Brandid;
		}

		// if (typeid > 0) { qry += " and c.typeid=" + typeid; };
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}
		if (Warename.length() > 0)
			qry += "   and c.warename like '%" + Warename + "%'";

		if (Wareno.length() > 0) {
			qry += "  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		}

		if (Seasonname.length() > 0) {
			qry += "  and c.seasonname = '" + Seasonname + "'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and c.prodyear = '" + Prodyear + "'";
		}

		qry += "\n  group by  a.custid,a.houseid,b.wareid,b.saleid";

		// 零售，批发
		qry += "\n    union all ";
		qry += "\n    select a.custid,a.houseid,b.wareid,b.saleid,0 as amount0,0 as curr0,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount1,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr1  ";
		qry += "\n    from wareouth a   ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno   ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid + " ";
		if (Xsid == 0) {
			qry += "  and a.ntid=0";
		} else // 零售
		if (Xsid == 1) {
			qry += "  and (a.ntid=1 or a.ntid=2)";
		} // 批发
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " ))  ";
			if (Xsid == 1)
				qry += "\n    and  exists (select 1 from employecust x3 where a.custid=x3.custid and x3.epid=" + Userid + " ) ";
		}
		qry += "    and a.notedate>=to_date('" + Mindate1 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate1 + "','yyyy-mm-dd hh24:mi:ss')";

		if (Custid > 0) {
			qry += "\n  and a.custid=" + Custid;
		}

		if (Houseid > 0) {
			qry += "  and a.houseid=" + Houseid;
		}

		if (Wareid > 0) {
			qry += "  and b.wareid=" + Wareid;
		}

		if (Brandid >= 0) {
			qry += "  and c.brandid=" + Brandid;
		}

		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}

		if (Wareno.length() > 0) {
			qry += "  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		}
		if (Warename.length() > 0)
			qry += "   and c.warename like '%" + Warename + "%'";

		if (Seasonname.length() > 0) {
			qry += "  and c.seasonname = '" + Seasonname + "'";
		}

		if (Prodyear.length() > 0) {
			qry += "  and c.prodyear = '" + Prodyear + "'";
		}

		qry += "\n  group by  a.custid,a.houseid,b.wareid,b.saleid";
		if ((Xsid == 0 || Xsid == 2) && Custid == 0) {
			qry += "\n   union all";
			// 商场销售
			qry += "\n    select 0 as custid,a.houseid,b.wareid,b.saleid,sum(b.amount) as amount0,sum(b.curr) as curr0,0 as amount1,0 as curr1  ";
			qry += "\n    from shopsaleh a   ";
			qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno   ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
			qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
			// if (xsid == 0) { qry += " and a.ntid=0"; }
			// else //零售
			// if (xsid == 1) { qry += " and (a.ntid=1 or a.ntid=2)"; } //批发
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " ))  ";
			}
			// qry += "\n and to_char(a.notedate,'yyyy-mm-dd') >=v_mindate0 ";
			// qry += "\n and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate0 ";
			qry += "    and a.notedate>=to_date('" + Mindate0 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate0 + "','yyyy-mm-dd hh24:mi:ss')";
			// if (custid > 0) { qry += "\n and a.custid=" + custid; };
			if (Houseid > 0) {
				qry += "  and a.houseid=" + Houseid;
			}
			;
			if (Wareid > 0) {
				qry += "  and b.wareid=" + Wareid;
			}
			;
			if (Brandid >= 0) {
				qry += "  and c.brandid=" + Brandid;
			}
			;
			// if (typeid > 0) { qry += " and c.typeid=" + typeid; };
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}
			if (Warename.length() > 0)
				qry += "   and c.warename like '%" + Warename + "%'";

			if (Wareno.length() > 0) {
				qry += "  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			}

			if (Seasonname.length() > 0) {
				qry += "  and c.seasonname = '" + Seasonname + "'";
			}

			if (Prodyear.length() > 0) {
				qry += "  and c.prodyear = '" + Prodyear + "'";
			}

			qry += "\n  group by  a.houseid,b.wareid,b.saleid";

			// 零售，批发
			qry += "\n    union all ";
			qry += "\n    select 0 as custid,a.houseid,b.wareid,b.saleid,0 as amount0,0 as curr0,sum(b.amount) as amount1,sum( b.curr) as curr1  ";
			qry += "\n    from shopsaleh a   ";
			qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno   ";
			qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
			qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
			// if (xsid == 0) { qry += " and a.ntid=0"; }
			// else //零售
			// if (xsid == 1) { qry += " and (a.ntid=1 or a.ntid=2)"; } //批发
			if (Qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
				qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " ))  ";
			}
			qry += "    and a.notedate>=to_date('" + Mindate1 + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate1 + "','yyyy-mm-dd hh24:mi:ss')";

			// if (custid > 0) { qry += "\n and a.custid=" + custid; };
			if (Houseid > 0) {
				qry += "  and a.houseid=" + Houseid;
			}
			;
			if (Wareid > 0) {
				qry += "  and b.wareid=" + Wareid;
			}
			;
			if (Brandid >= 0) {
				qry += "  and c.brandid=" + Brandid;
			}
			;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Warename.length() > 0)
				qry += "   and c.warename like '%" + Warename + "%'";

			if (Wareno.length() > 0) {
				qry += "  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
			}

			if (Seasonname.length() > 0) {
				qry += "  and c.seasonname = '" + Seasonname + "'";
			}

			if (Prodyear.length() > 0) {
				qry += "  and c.prodyear = '" + Prodyear + "'";
			}
			qry += "\n  group by  a.houseid,b.wareid,b.saleid";
		}
		qry += "\n   ) group by custid,houseid,wareid,saleid );";

		qry += "\n   commit;   ";

		// qry += "\n update v_xstbfx_data set
		// ratesl=(amount1-amount0)/amount0,rateje=(curr1-curr0)/curr0 where
		// epid=" + Userid + ";";

		qry += "\n   select nvl(sum(amount0),0),nvl(sum(amount1),0),nvl(sum(curr0),0),nvl(sum(curr1),0) into :totalamt0,:totalamt1,:totalcurr0,:totalcurr1 from v_xstbfx_data where epid=" + Userid + ";  ";
		qry += "\n end; ";

		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalcurr0", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount =
		// Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr =
		// Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"totalamt0\":\"" + param.get("totalamt0").getParamvalue() + "\"" //
				+ ",\"totalamt1\":\"" + param.get("totalamt1").getParamvalue() + "\"" //
				+ ",\"totalcurr0\":\"" + param.get("totalcurr0").getParamvalue() + "\"" //
				+ ",\"totalcurr1\":\"" + param.get("totalcurr1").getParamvalue() + "\"" //
				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;
	}

	public Table GetTable(QueryParam qp, int hzfs) {
		String qry = "";
		// String sort = "";
		// System.out.println("1111");
		if (hzfs == 1) // 类型
		{
			qry = " select b.typeid,c.fullname as typename,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";
			qry += "\n ,min(a.id) as id";

			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n left outer join warecode b on a.wareid=b.wareid";
			qry += "\n left outer join waretype c on b.typeid=c.typeid";
			qry += "\n where a.epid=" + Userid;
			qry += "\n group by b.typeid,c.fullname";
			// 0=汇总项目， 1=本期数量amount0 2=本期金额curr0 3=比较期数量amount1 4=比较期金额curr1
			// if (sortid == 1) {
			// sort = " amount0 " + order;
			// } else if (sortid == 2) {
			// sort = " curr0 " + order;
			// } else if (sortid == 3) {
			// sort = " amount1 " + order;
			// } else if (sortid == 4) {
			// sort = " curr1 " + order;
			// } else {
			// sort = " typename " + order;
			// }
		} else if (hzfs == 0) // 品牌
		{
			qry = " select b.brandid,c.brandname,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
																																					// skc";
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n left outer join warecode b on a.wareid=b.wareid";
			qry += "\n left outer join brand c on b.brandid=c.brandid";
			qry += "\n where a.epid=" + Userid;
			qry += "\n group by b.brandid,c.brandname";
			// if (sortid == 1) {
			// sort = " amount0 " + order;
			// } else if (sortid == 2) {
			// sort = " curr0 " + order;
			// } else if (sortid == 3) {
			// sort = " amount1 " + order;
			// } else if (sortid == 4) {
			// sort = " curr1 " + order;
			// } else {
			// sort = " brandname " + order;
			// }
		} else if (hzfs == 2) // 季节
		{
			qry = " select b.seasonname,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n left outer join warecode b on a.wareid=b.wareid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += "\n where a.epid=" + Userid;
			qry += "\n group by b.seasonname";
			// if (sortid == 1) {
			// sort = " amount0 " + order;
			// } else if (sortid == 2) {
			// sort = " curr0 " + order;
			// } else if (sortid == 3) {
			// sort = " amount1 " + order;
			// } else if (sortid == 4) {
			// sort = " curr1 " + order;
			// } else {
			// sort = " seasonname " + order;
			// }
		} else if (hzfs == 3) // 店铺
		{
			qry = " select a.houseid,b.housename,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n left outer join warehouse b on a.houseid=b.houseid";
			// qry += " left outer join brand c on b.brandid=c.brandid";
			qry += "\n where a.epid=" + Userid;
			qry += "\n group by a.houseid,b.housename";
			// if (sortid == 1) {
			// sort = " amount0 " + order;
			// } else if (sortid == 2) {
			// sort = " curr0 " + order;
			// } else if (sortid == 3) {
			// sort = " amount1 " + order;
			// } else if (sortid == 4) {
			// sort = " curr1 " + order;
			// } else {
			// sort = " housename " + order;
			// }
		} else if (hzfs == 4) // 客户
		{
			// qry = "select custid,custname,amount,curr from (";
			qry = "select a.custid,cast('零售' as nvarchar2(10)) as custname,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n where a.custid=0 and a.epid=" + Userid;
			qry += "\n group by a.custid";
			qry += "\n union all";

			qry += "\n select a.custid,b.custname,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n left outer join customer b on a.custid=b.custid";
			qry += "\n where a.custid>0 and a.epid=" + Userid;
			qry += "\n group by a.custid,b.custname";
			// qry += ")";
			// if (sortid == 1) {
			// sort = " amount0 " + order;
			// } else if (sortid == 2) {
			// sort = " curr0 " + order;
			// } else if (sortid == 3) {
			// sort = " amount1 " + order;
			// } else if (sortid == 4) {
			// sort = " curr1 " + order;
			// } else {
			// sort = " custname " + order;
			// }
		} else if (hzfs == 5) // 销售类型
		{
			qry = " select a.saleid,cast('零售' as nvarchar2(10)) as salename,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n where a.saleid=0 and a.epid=" + Userid;
			qry += "\n group by a.saleid";
			qry += "\n union all ";
			qry += "\n select a.saleid,b.salename,sum(a.amount0) as amount0,sum(a.curr0) as curr0,sum(a.amount1) as amount1,sum(a.curr1) as curr1";// ,count(distinct
			qry += "\n ,min(a.id) as id";
			qry += "\n ,decode(sum(a.amount0),0,0,round(sum(a.amount1-a.amount0)/sum(a.amount0),4)*100) as ratesl";
			qry += "\n ,decode(sum(a.curr0),0,0,round(sum(a.curr1-a.curr0)/sum(a.curr0),4)*100) as rateje";
			qry += "\n FROM v_xstbfx_data a";
			qry += "\n left outer join salecode b on a.saleid=b.saleid";
			qry += "\n where a.saleid>0 and a.epid=" + Userid;
			qry += "\n group by a.saleid,b.salename";
			// if (sortid == 1) {
			// sort = " amount0 " + order;
			// } else if (sortid == 2) {
			// sort = " curr0 " + order;
			// } else if (sortid == 3) {
			// sort = " amount1 " + order;
			// } else if (sortid == 4) {
			// sort = " curr1 " + order;
			// } else {
			// sort = " salename " + order;
			// }
		}
		// System.out.println(qry);
		// qp.setSortString(sort);
		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

}
