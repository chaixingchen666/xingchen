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
public class vTotalgxbalance {

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd
	// HH:mm:ss,------------必传
	private Long Accid = (long) 0;

	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	private Long Provid = (long) -1;
	private Long Brandid = (long) -1;
	private Long Typeid = (long) -1;
	private String Seasonname = "";
	private String Warename = "";
	private String Wareno = "";
	private String Prodyear = "";

	// private String Mindate = "";
	// private String Maxdate = "";
	private String Accbegindate = "2000-01-01";
	// private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
	// private Integer Maxday = 0;// 最大查询天数

	private String errmess;
	private String Calcdate = ""; // 账套库存登账日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

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

	public int doTotal() {
		Wareno = Wareno.toUpperCase();
		vTotalCxkczy kc = new vTotalCxkczy();
		kc.setAccid(Accid);
		kc.setUserid(Userid);
		kc.setCxdatetime(Nowdate + " 23:59:59");
		kc.setWareid(Wareid);
		kc.setTypeid(Typeid);
		kc.setColorid(Colorid);
		kc.setSizeid(Sizeid);
		kc.setBrandid(Brandid);
		kc.setProvid(Provid);
		kc.setWareno(Wareno);
		kc.setSeasonname(Seasonname);
		kc.setProdyear(Prodyear);
		kc.setQxbj(Qxbj);
		kc.setCalcdate(Calcdate);
		kc.doCxkczy();
		String Mindate = Nowdate + " 00:00:00";
		String Maxdate = Nowdate + " 23:59:59";
		String nowdate0 = Func.DateToStr(Func.getPrevDay(Nowdate));
		String qry;
		qry = "begin ";
		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n       select ROWID from v_gxbalance_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_gxbalance_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		if (Accid == 3464)  // 让爱回家
		{
			qry += "\n   insert into v_gxbalance_data  (id,epid,wareid,colorid,sizeid,cdamt,kcamt,kdamt,xqamt) ";
			qry += "\n   select v_gxbalance_data_id.nextval," + Userid + ",wareid,colorid,sizeid,cdamt,kcamt,kdamt,cdamt+kcamt-kdamt from ( ";
			qry += "\n   select wareid,colorid,sizeid,sum(cdamt) as cdamt,sum(kcamt) as kcamt,sum(kdamt) as kdamt from ( ";
			// --库存
			qry += "\n   select wareid,colorid,sizeid,sum(amount) as kcamt,0 as cdamt,0 as kdamt  ";
			qry += "\n   from v_cxkczy_data where epid=" + Userid;
			qry += "\n   group by wareid,colorid,sizeid ";
			qry += "\n   union all ";
			// --在途数,(未收货的调出单)
			qry += "\n   select b.wareid,b.colorid,b.sizeid,sum(b.amount) as kcamt,0 as cdamt,0 as kdamt  ";
			qry += "\n   from allotouth a  ";
			qry += "\n   join allotoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n   join warecode c on b.wareid=c.wareid";
			qry += "\n   where a.accid=" + Accid + " and a.statetag=1 ";
			qry += "\n   and not exists (select 1 from allotinh a1 where a.accid=a1.accid and a.noteno=a1.noteno1 and a1.statetag=1) ";
			if (Qxbj == 1) {
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";

			qry += "\n   group by b.wareid,b.colorid,b.sizeid ";
			// --未完采购订单期初
			qry += "\n   union all ";
			qry += "\n   select a.wareid,a.colorid,a.sizeid,0 as kcamt,sum(a.amount) as cdamt,0 as kdamt  ";
			qry += "\n   from provorderbal a  ";
			qry += "\n   join warecode c on a.wareid=c.wareid";
			qry += "\n   where a.daystr='" + nowdate0 + "'";
			qry += "\n   and exists (select 1 from provide a1 where a1.accid=" + Accid + " and a.provid=a1.provid)";
			if (Qxbj == 1) {
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and a.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and a.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and a.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";

			qry += "\n   group by a.wareid,a.colorid,a.sizeid ";
			// 本日新增采购订单
			qry += "\n   union all";
			qry += "\n   select b.wareid,b.colorid,b.sizeid,0 as kcamt,sum(b.amount) as cdamt,0 as kdamt  ";
			qry += "\n   from provorderh a  ";
			qry += "\n   join provorderm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n   join warecode c on b.wareid=c.wareid";

			qry += "\n   where a.accid=" + Accid + " and a.statetag=1 ";
			qry += "\n   and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			// qry += "\n and not exists (select 1 from wareinh a1 where
			// a.accid=a1.accid and a.noteno=a1.orderno and a1.statetag=1) ";
			if (Qxbj == 1) {
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";

			qry += "\n    group by b.wareid,b.colorid,b.sizeid ";
			// 本日入库数
			qry += "\n    union all";
			qry += "\n    select b.wareid,b.colorid,b.sizeid,0 as kcamt,-sum(b.amount) as cdamt,0 as kdamt  ";
			qry += "\n    from wareinh a  ";
			qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    join warecode c on b.wareid=c.wareid";
			qry += "\n    where a.accid=" + Accid + " and a.statetag=1 and a.ntid=0 ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n    and exists (select 1 from provorderh a1 join provorderm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno";
			qry += "\n    where a1.statetag=1 and a1.accid=" + Accid + " and a.notedate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and b.wareid=b1.wareid and b.colorid=b1.colorid and b.sizeid=b1.sizeid )";
			if (Qxbj == 1) {
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";

			qry += "\n group by b.wareid,b.colorid,b.sizeid ";

			// --未完客户订单期初
			qry += "\n union all ";
			qry += "\n select a.wareid,a.colorid,a.sizeid,0 as kcamt,0 as cdamt,sum(a.amount) as kdamt  ";
			qry += "\n from custorderbal a  ";
			// qry += "\n join provorderm b on a.accid=b.accid and
			// a.noteno=b.noteno ";
			qry += "\n join warecode c on a.wareid=c.wareid";
			qry += "\n where a.daystr='" + nowdate0 + "'";
			qry += "\n  and exists (select 1 from customer a1 where a1.accid=" + Accid + " and a.custid=a1.custid)";
			if (Qxbj == 1) {
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and a.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and a.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and a.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";

			qry += "\n group by a.wareid,a.colorid,a.sizeid ";
			qry += "\n union all ";

			// --今日客户订单
			qry += "\n select b.wareid,b.colorid,b.sizeid,0 as kcamt,0 as cdamt,sum(b.amount) as kdamt  ";
			qry += "\n from custorderh a  ";
			qry += "\n join custorderm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n join warecode c on b.wareid=c.wareid";

			qry += "\n where a.accid=" + Accid + " and a.statetag=1 ";
			qry += "\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			// qry += "\n and not exists (select 1 from wareouth a1 where
			// a.accid=a1.accid and a.noteno=a1.orderno and a1.statetag=1) ";
			if (Qxbj == 1) {
				// qry += " and exists (select 1 from employehouse x1 where
				// a.houseid=x1.houseid and x1.epid=" + userid + " ) ";
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";
			qry += "\n    group by b.wareid,b.colorid,b.sizeid ";
			// 今日销售数
			qry += "\n    union all";
			qry += "\n    select b.wareid,b.colorid,b.sizeid,0 as kcamt,0 as cdamt,-sum(b.amount) as kdamt  ";
			qry += "\n    from wareouth a  ";
			qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n    join warecode c on b.wareid=c.wareid";
			qry += "\n    where a.accid=" + Accid + " and a.statetag=1 and a.ntid=1 ";
			qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n    and exists (select 1 from custorderh a1 join custorderm b1 on a1.accid=b1.accid and a1.noteno=b1.noteno";
			qry += "\n    where a1.statetag=1 and a1.accid=" + Accid + " and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss') ";
			qry += "\n    and b.wareid=b1.wareid and b.colorid=b1.colorid and b.sizeid=b1.sizeid )";
			if (Qxbj == 1) {
				// qry += " and exists (select 1 from employehouse x1 where
				// a.houseid=x1.houseid and x1.epid=" + userid + " ) ";
				qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += "      and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "      and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "      and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "      and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += "      and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "    and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += "      and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += "      and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += "      and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "      and c.prodyear='" + Prodyear + "'";

			qry += "\n group by b.wareid,b.colorid,b.sizeid ";

			qry += "\n ) ";
			qry += "\n group by wareid,colorid,sizeid ";
			qry += "\n ); ";

		} else {

			qry += "\n insert into v_gxbalance_data(id,epid,wareid,colorid,sizeid,cdamt,kcamt,kdamt,xqamt) ";

			qry += "\n select v_gxbalance_data_id.nextval," + Userid + ",wareid,colorid,sizeid,cdamt,kcamt,kdamt,cdamt+kcamt-kdamt from ( ";
			qry += "\n select wareid,colorid,sizeid,sum(cdamt) as cdamt,sum(kcamt) as kcamt,sum(kdamt) as kdamt from ( ";
			// --库存
			qry += "\n select wareid,colorid,sizeid,sum(amount) as kcamt,0 as cdamt,0 as kdamt ";
			qry += "\n from v_cxkczy_data where epid=" + Userid;
			qry += "\n group by wareid,colorid,sizeid ";
			qry += "\n union all ";
			// --在途数（未入库的调出单）
			qry += "\n select b.wareid,b.colorid,b.sizeid,sum(b.amount) as kcamt,0 as cdamt,0 as kdamt ";
			qry += "\n from allotouth a ";
			qry += "\n join allotoutm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n join warecode c on b.wareid=c.wareid";
			qry += "\n where a.accid=" + Accid + " and a.statetag=1 ";
			qry += "\n and not exists (select 1 from allotinh a1 where a.accid=a1.accid and a.noteno=a1.noteno1 and a1.statetag=1) ";
			if (Qxbj == 1) {
//				qry += " and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += " and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			// if (houseid > 0) qry += " and a.houseid=" + houseid;
			if (Wareid > 0)
				qry += " and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += " and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += " and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += " and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += " and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += " and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += " and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += " and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += " and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += " and c.prodyear='" + Prodyear + "'";

			qry += "\n group by b.wareid,b.colorid,b.sizeid ";
			// 未完采购订单
			qry += "\n union all";
			qry += "\n select b.wareid,b.colorid,b.sizeid,0 as kcamt,sum(b.amount-b.factamt) as cdamt,0 as kdamt ";
			qry += "\n from provorderh a ";
			qry += "\n join provorderm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n join warecode c on b.wareid=c.wareid";

			qry += "\n where a.accid=" + Accid + " and a.statetag=1 and a.overbj=0 ";
			qry += "\n and not exists (select 1 from wareinh a1 where a.accid=a1.accid and a.noteno=a1.orderno and a1.statetag=1) ";
			if (Qxbj == 1) {
//				qry += " and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += " and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += " and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += " and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += " and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += " and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += " and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += " and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += " and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += " and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += " and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += " and c.prodyear='" + Prodyear + "'";

			qry += "\n group by b.wareid,b.colorid,b.sizeid ";
			qry += "\n union all";
			// --未完客户订单
			qry += "\n select b.wareid,b.colorid,b.sizeid,0 as kcamt,0 as cdamt,sum(b.amount-b.factamt) as kdamt ";
			qry += "\n from custorderh a ";
			qry += "\n join custorderm b on a.accid=b.accid and a.noteno=b.noteno ";
			qry += "\n join warecode c on b.wareid=c.wareid";

			qry += "\n where a.accid=" + Accid + " and a.statetag=1 and a.overbj=0 ";
			if (Qxbj == 1) {
//				qry += " and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += " and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Wareid > 0)
				qry += " and b.wareid=" + Wareid;
			if (Colorid > 0)
				qry += " and b.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += " and b.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += " and c.wareno like '%" + Wareno + "%'";
			if (Warename.length() > 0)
				qry += " and c.warename like '%" + Warename + "%'";
			// if (typeid>0 ) qry+=" and b.typeid="+typeid;
			if (Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += " and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += " and c.typeid=" + Typeid;
				}

			if (Brandid >= 0)
				qry += " and c.brandid=" + Brandid;
			if (Provid >= 0)
				qry += " and c.provid=" + Provid;
			if (Seasonname.length() > 0)
				qry += " and c.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += " and c.prodyear='" + Prodyear + "'";

			qry += "\n group by b.wareid,b.colorid,b.sizeid ";
			qry += "\n ) ";
			qry += "\n group by wareid,colorid,sizeid ";
			qry += "\n ); ";
		}
//		qry += "\n   delete from v_gxbalance_data a where a.epid=" + Userid + " and cdamt=0 and kdamt=0;";
		qry += "\n   select nvl(sum(a.cdamt),0),nvl(sum(a.kcamt),0),nvl(sum(a.kdamt),0),nvl(sum(a.xqamt),0) into :cdamt,:kcamt,:kdamt,:xqamt ";
		qry += "\n   from v_gxbalance_data a where a.epid=" + Userid + "; ";
		qry += "\n end; ";
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("cdamt", new ProdParam(Types.FLOAT));
		param.put("kcamt", new ProdParam(Types.FLOAT));
		param.put("kdamt", new ProdParam(Types.FLOAT));
		param.put("xqamt", new ProdParam(Types.FLOAT));
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
		errmess = "\"msg\":\"操作成功！\",\"cdamt\":\"" + param.get("cdamt").getParamvalue() + "\"" //
				+ ",\"kcamt\":\"" + param.get("kcamt").getParamvalue() + "\"" //
				+ ",\"kdamt\":\"" + param.get("kdamt").getParamvalue() + "\"" //
				+ ",\"xqamt\":\"" + param.get("xqamt").getParamvalue() + "\"" //
		;
		return 1;
	}

	public Table GetTable(QueryParam qp, String strWhere, String grouplist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + grouplist);
		strSql.append(",sum(a.cdamt) as cdamt,sum(a.kcamt) as kcamt,sum(a.kdamt) as kdamt,sum(a.xqamt) as xqamt,min(id) as keyid");
		strSql.append("\n FROM v_gxbalance_data a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		strSql.append("\n group by " + Func.delGroupOfAs(grouplist));
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

}
