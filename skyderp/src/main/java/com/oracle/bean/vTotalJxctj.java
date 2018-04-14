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

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class vTotalJxctj {
	private Long Userid;
	private Long Accid;
	private String errmess;

	private String Mindate = "";
	private String Maxdate = "";
	private String Accbegindate = "2000-01-01";
	private String Nowdate = "";
	private Integer Qxbj = 0;// 1=启用权限控
	private Integer Maxday = 0;// 1=启用权限控
	private String Wareno = "";
	private String Warename = "";
	private Long Brandid = (long) -1;
	private Long Wareid = (long) 0;
	private Long Areaid = (long) -1;
	private Integer Retday = 0; // 最近退货天数
	private String Houseidlist = "";
	private String Brandidlist = "";
	private String Providlist = "";
	private Long Typeid = (long) -1;
	private String Prodyear = "";
	private String Seasonname = "";
	private String Ssdate = "";

	private Integer Housecostbj = 0;

	private Integer Minxsday = -1;
	private Integer Maxxsday = -1;
	private Integer Minjxrate = -1;
	private Integer Maxjxrate = -1;
	private String Minssdate = "";
	private String Maxssdate = "";
	private String Calcdate = ""; // 账套开始使用日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
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

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setHousecostbj(Integer housecostbj) {
		Housecostbj = housecostbj;
	}

	public void setMinxsday(Integer minxsday) {
		Minxsday = minxsday;
	}

	public void setMaxxsday(Integer maxxsday) {
		Maxxsday = maxxsday;
	}

	public void setMinjxrate(Integer minjxrate) {
		Minjxrate = minjxrate;
	}

	public void setMaxjxrate(Integer maxjxrate) {
		Maxjxrate = maxjxrate;
	}

	public void setMinssdate(String minssdate) {
		Minssdate = minssdate;
	}

	public void setMaxssdate(String maxssdate) {
		Maxssdate = maxssdate;
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, int hzfs, JSONObject jsonObject) {
		// 平均日销，可销天数（可销天数=库存/平均日销）
		// hzfs:0=商品，1=商品+颜色，2=商品+颜色+尺码

		String qry = " select a.id,a.wareid,b.wareno,b.warename,b.units,b.prodno,b.imagename0";
		if (hzfs >= 1)
			qry += ",a.colorid,c.colorname";
		if (hzfs == 2)
			qry += ",a.sizeid,s.sizename";
		qry += "\n   ,a.houseid,d.housename";
		qry += "\n   ,e.retailsale,e.checksale,f.provname,i.locano,j.areaname,g.brandname";
		qry += "\n   ,a.ssdate,a.xsday,a.maxxsday,a.jxrate,a.avgxs,e.shdate,a.sxrate";
		qry += "\n    ,a.amountjh,a.numjh,a.amountjt,a.numjt,a.amountxs,a.amountkc";

		qry += "\n FROM v_cxjxctj_data a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		// qry += "\n left outer join housewaredate h on a.wareid=h.wareid and
		// a.houseid=h.houseid";

		qry += "\n left outer join provide f on b.provid=f.provid";
		if (hzfs >= 1)
			qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		if (hzfs == 2)
			qry += "\n left outer join sizecode s on a.sizeid=s.sizeid";
		qry += "\n left outer join brand g on b.brandid=g.brandid";

		qry += "\n left outer join warehouse d on a.houseid=d.houseid";
		qry += "\n left outer join wareloca i on a.locaid=i.locaid";
		qry += "\n left outer join area j on b.areaid=j.areaid";
		qry += "\n left outer join housesaleprice e on a.wareid=e.wareid and a.houseid=e.houseid ";
		qry += "\n where a.epid=" + Userid;
		if (Minxsday >= 0)
			qry += "\n  and a.maxxsday>=" + Minxsday;
		if (Maxxsday >= 0)
			qry += "\n  and a.maxxsday<=" + Maxxsday;
		if (Minjxrate >= 0)
			qry += "\n  and a.jxrate>= " + Minjxrate;
		if (Maxjxrate >= 0)
			qry += "\n  and a.jxrate<= " + Maxjxrate;
		qp.setQueryString(qry);
		// qp.setSumString("nvl(sum(amountjh),0) as
		// amountjh,nvl(sum(amountjt),0) as amountjt,nvl(sum(amountxs),0) as
		// amountxs,nvl(sum(amountkc),0) as amountkc");
		// return DbHelperSQL.GetTable(qp);
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, int hzfs, String fieldlist) {
		// 平均日销，可销天数（可销天数=库存/平均日销）
		// hzfs:0=商品，1=商品+颜色，2=商品+颜色+尺码

		String qry = " select a.id,a.wareid,b.wareno,b.warename,b.units,b.imagename0";
		if (hzfs >= 1)
			qry += ",a.colorid,c.colorname";
		if (hzfs == 2)
			qry += ",a.sizeid,s.sizename,s.sizeno";
		qry += "," + fieldlist;
		//		qry += "\n   ,a.houseid,d.housename";
		//		qry += "\n   ,e.retailsale,e.checksale,f.provname,i.locano,j.areaname,g.brandname";
		//		qry += "\n   ,a.ssdate,a.xsday,a.maxxsday,a.jxrate,a.avgxs,e.shdate,a.sxrate";
		//		qry += "\n    ,a.amountjh,a.numjh,a.amountjt,a.numjt,a.amountxs,a.amountkc";

		qry += "\n FROM v_cxjxctj_data a ";
		qry += "\n left outer join warecode b on a.wareid=b.wareid";
		// qry += "\n left outer join housewaredate h on a.wareid=h.wareid and
		// a.houseid=h.houseid";

		qry += "\n left outer join provide f on b.provid=f.provid";
		if (hzfs >= 1)
			qry += "\n left outer join colorcode c on a.colorid=c.colorid";
		if (hzfs == 2)
			qry += "\n left outer join sizecode s on a.sizeid=s.sizeid";
		qry += "\n left outer join brand g on b.brandid=g.brandid";

		qry += "\n left outer join warehouse d on a.houseid=d.houseid";
		qry += "\n left outer join wareloca i on a.locaid=i.locaid";
		qry += "\n left outer join area j on b.areaid=j.areaid";
		qry += "\n left outer join housesaleprice e on a.wareid=e.wareid and a.houseid=e.houseid ";
		qry += "\n where a.epid=" + Userid;
		if (Minxsday >= 0)
			qry += "\n  and a.maxxsday>=" + Minxsday;
		if (Maxxsday >= 0)
			qry += "\n  and a.maxxsday<=" + Maxxsday;
		if (Minjxrate >= 0)
			qry += "\n  and a.jxrate>= " + Minjxrate;
		if (Maxjxrate >= 0)
			qry += "\n  and a.jxrate<= " + Maxjxrate;
		// if (Minssdate.length() > 0)
		// qry += " and a.ssdate>='" + Minssdate + "'";
		// if (Maxssdate.length() > 0)
		// qry += " and a.ssdate<='" + Maxssdate + "'";

		qp.setQueryString(qry);
		// errmess = "\"AMOUNTJH\":\"" + param.get("amountjh").getParamvalue() +
		// "\"" //
		// + ",\"AMOUNTJT\":\"" + param.get("amountjt").getParamvalue() + "\""
		// //
		// + ",\"AMOUNTXS\":\"" + param.get("amountxs").getParamvalue() + "\""//
		// + ",\"AMOUNTKC\":\"" + param.get("amountkc").getParamvalue() + "\""//
		// qry += "\n
		// ,a.amountjh,a.numjh,a.amountjt,a.numjt,a.amountxs,a.amountkc";
		//		qp.setSumString("nvl(sum(amountjh),0) as amountjh,nvl(sum(amountjt),0) as amountjt,nvl(sum(amountxs),0) as amountxs,nvl(sum(amountkc),0) as amountkc");
		return DbHelperSQL.GetTable(qp);
	}

	public int Total(int hzfs, int havehouse, JSONObject jsonObject) {
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}

		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		// String mess = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		Maxdate += " 23:59:59";

		vTotalCxkczy kc = new vTotalCxkczy();
		DbHelperSQL.JsonConvertObject(kc, jsonObject);
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate)) + " 23:59:59";
		kc.setCxdatetime(mindate0);
		kc.setAccid(Accid);
		kc.setUserid(Userid);
		kc.setQxbj(Qxbj);
		kc.setCalcdate(Calcdate);
		kc.setRetday(Retday);
		kc.doCxkczy();

		String qry = "declare ";
		qry += "\n    v_epid  number; ";
		qry += "\n    v_accid  number; ";
		qry += "\n    v_mindate varchar2(10); ";
		qry += "\n    v_maxdate varchar2(10); ";
		qry += "\n    v_mindate0 varchar2(10); ";
		qry += "\n    v_bj  number(1); ";
		qry += "\n    v_amountye number; ";
		qry += "\n    v_currls2 number; ";
		qry += "\n    v_currps2 number; ";
		qry += "\n    v_count  number; ";
		qry += "\n begin ";
		qry += "\n    declare cursor mycursor is ";
		qry += "\n       select ROWID from v_cxjxctj_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxjxctj_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n       end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n    commit;";

		qry += "\n    insert into v_cxjxctj_data (id,epid,wareid,colorid,sizeid,houseid,ssdate,jxrate,sxrate,xsday,amountjh,numjh,amountjt,numjt,amountxs,currxs,numxs,amountkc0,currkc0,retailcurr,amountdr,amountdc)  ";

		qry += "\n    select v_cxjxctj_data_id.nextval," + Userid + ", a.wareid,a.colorid,a.sizeid,a.houseid";
		qry += "\n    ,case when h.shdate is null then b.ssdate else h.shdate end as ssdate";
		qry += "\n    ,case a.amountxs when 0 then 0 else round((a.amountjh-a.amountjt)/a.amountxs,2) end as jxrate";
		qry += "\n    ,case a.amountjh-a.amountjt when 0 then 0 else round(a.amountxs/(a.amountjh-a.amountjt),2) end as sxrate";
		qry += "\n    ,nvl(round(sysdate-to_date(case when h.shdate is null then b.ssdate else h.shdate end,'yyyy-mm-dd')),0) as xsday";
		qry += "\n    ,amountjh,numjh,amountjt,numjt,amountxs,currxs,numxs,amountkc0,currkc0,retailcurr,amountdr,amountdc from ( ";
		//采购入库
		if (hzfs == 0)
			qry += "\n    select wareid,0 as colorid,0 as sizeid";
		else if (hzfs == 1)
			qry += "\n    select wareid,colorid,0 as sizeid";
		else if (hzfs == 2)
			qry += "\n    select wareid,colorid,sizeid";
		if (havehouse == 1)  //havehouse=1 要汇总店铺,当显示项目中有店铺的时候，传入1，否则传入0
			qry += ",houseid";
		else
			qry += ",0 as houseid";
		qry += "\n    ,nvl(sum(amountjh),0) as amountjh,nvl(sum(numjh),0) as numjh,nvl(sum(amountjt),0) as amountjt,nvl(sum(numjt),0) as numjt";
		qry += "\n    ,nvl(sum(amountxs),0) as amountxs,nvl(sum(currxs),0) as currxs,nvl(sum(numxs),0) as numxs,nvl(sum(amountkc0),0) as amountkc0";
		qry += "\n    ,nvl(sum(currkc0),0) as currkc0,nvl(sum(retailcurr),0) as retailcurr,nvl(sum(amountdr),0) as amountdr,nvl(sum(amountdc),0) as amountdc from ( ";
		// --采购入库
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,sum(b.amount) as amountjh ,count(distinct '^'||a.accid||'^'||a.noteno||'^') as numjh,0 as amountjt,0 as numjt,0 as amountxs,0 as currxs ,0 as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and a.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// a.houseid=e.houseid ";
		qry += "\n    where a.statetag=1 and a.ntid=0 and a.accid=" + Accid;// and
		// to_char(a.notedate,'yyyy-mm-dd')
		// >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";

		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";

		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";
		// --采购退库
		qry += "\n    union all ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh,0 as numjh,sum(b.amount) as amountjt ,count(distinct '^'||a.accid||'^'||a.noteno||'^') as numjt,0 as amountxs,0 as currxs ,0 as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and a.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// a.houseid=e.houseid ";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and a.ntid=1 and a.accid=" + Accid + " ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";

		// --零售
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,sum(b.amount) as amountxs,sum(b.curr) as currxs ,count(distinct '^'||a.accid||'^'||a.noteno||'^') as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from wareouth a ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and a.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// a.houseid=e.houseid ";
		qry += "\n    where a.statetag=1 and a.ntid=0 and a.accid=" + Accid;// and
		// to_char(a.notedate,'yyyy-mm-dd')
		// >='"+mindate+"' ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";

		// 批发
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,sum(b.amount) as amountxs,sum(b.curr) as currxs ,count(distinct '^'||a.accid||'^'||a.noteno||'^') as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and a.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// a.houseid=e.houseid ";
		qry += "\n    where a.statetag=1  and a.ntid=1 and a.accid=" + Accid;// and
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";
		// 批发退货
		qry += "\n    union all ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,-sum(b.amount) as amountxs,-sum(b.curr) as currxs ,0 as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where a.statetag=1  and a.ntid=2 and a.accid=" + Accid;// and
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";

		// --商场销售
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,sum(b.amount) as amountxs,sum(b.curr) as currxs ,count(distinct '^'||a.accid||'^'||a.noteno||'^') as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid;// and
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";
		//调入	
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,0 as amountxs,0 as currxs ,0 as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,sum(b.amount) as amountdr,0 as amountdc,0 as amountyk";
		qry += "\n    from allotinh a  ";
		qry += "\n    join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and b.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// b.houseid=e.houseid ";
		qry += "\n    where a.statetag=1  and a.accid=" + Accid;// and
		// to_char(a.notedate,'yyyy-mm-dd')
		// >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";

		//调出	
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,0 as amountxs ,0 as currxs,0 as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,sum(b.amount) as amountdc,0 as amountyk";
		qry += "\n    from allotouth a  ";
		qry += "\n    join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and b.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// b.houseid=e.houseid ";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid;// and
		// to_char(a.notedate,'yyyy-mm-dd')
		// >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";

		//盘点
		qry += "\n    union all  ";
		qry += "\n    select b.wareid,b.colorid,b.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,0 as amountxs,0 as currxs ,0 as numxs,0 as amountkc0 ";
		qry += "\n    ,0 as currkc0,0 as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,sum(b.amount) as amountyk";
		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecost d on b.wareid=d.wareid and
		// to_char(a.notedate,'yyyy-mm-dd')=d.daystr ";
		// if (housecostbj == 1) qry += " and b.houseid=d.houseid"; else qry +=
		// " and d.houseid=0";
		// qry += "\n left outer join housesaleprice e on b.wareid=e.wareid and
		// b.houseid=e.houseid ";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid + " and (b.amount>0 or b.amount<0)";// and
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (!Houseidlist.equals(""))
			qry += " and " + Func.getCondition("a.Houseid", Houseidlist);
		if (Wareid > 0)
			qry += "      and b.wareid=" + Wareid;
		else if (Wareno.length() > 0)
			qry += "      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (!Warename.equals(""))
			qry += "      and c.warename like '%" + Warename + "%'";
		if (!Seasonname.equals(""))
			qry += "      and c.Seasonname='" + Seasonname + "'";
		if (!Prodyear.equals(""))
			qry += "      and c.prodyear='" + Prodyear + "'";
		if (!Func.isNull(Minssdate))
			qry += "      and c.ssdate>='" + Minssdate + "'";
		if (!Func.isNull(Maxssdate))
			qry += "      and c.ssdate<='" + Maxssdate + "'";
		if (!Providlist.equals(""))
			qry += " and " + Func.getCondition("c.provid", Providlist);
		if (Areaid >= 0)
			qry += "\n      and c.areaid=" + Areaid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "  and exists (select 1 from waretype t where t.Typeid=c.Typeid and t.p_Typeid= " + Typeid + ") ";
			} else {
				qry += "    and c.Typeid=" + Typeid;
			}
		if (Retday > 0) // 退货截止天数
			qry += " and f_isdate(c.retdatestr)=1 and SUBSTR(c.retdatestr,6,5) >= to_char(sysdate, 'mm-dd') and SUBSTR(c.retdatestr, 6,5) <= to_char(sysdate + " + Retday + ", 'mm-dd')";
		qry += "\n    group by b.wareid,b.colorid,b.sizeid,a.houseid ";

		qry += "\n    union all";
		// 库存
		qry += "\n    select a.wareid,a.colorid,a.sizeid,a.houseid,0 as amountjh ,0 as numjh,0 as amountjt ,0 as numjt,0 as amountxs,0 as currxs ,0 as numxs,sum(a.amount) as amountkc0 ";
		qry += "\n    ,sum(a.amount*d.costprice) as currkc0,sum(a.amount*e.retailsale) as retailcurr";
		qry += "\n    ,0 as amountdr,0 as amountdc,0 as amountyk";

		qry += "\n    from v_cxkczy_data a ";
		qry += "\n    left outer join warecost d on a.wareid=d.wareid and d.daystr='" + Func.subString(mindate0, 1, 10) + "'";
		if (Housecostbj == 1)
			qry += "  and a.houseid=d.houseid";
		else
			qry += "  and d.houseid=0";
		qry += "\n    left outer join housesaleprice e on a.wareid=e.wareid and a.houseid=e.houseid ";
		qry += "\n    where a.epid=" + Userid;
		qry += "\n    group by a.wareid,a.colorid,a.sizeid,a.houseid ";

		// qry += "\n ) group by wareid,colorid,houseid) a";
		qry += "\n  )";
		if (hzfs == 0)
			qry += "\n    group by wareid";
		else if (hzfs == 1)
			qry += "\n    group by wareid,colorid";
		else if (hzfs == 2)
			qry += "\n    group by wareid,colorid,sizeid";
		if (havehouse == 1)
			qry += ",houseid";
		qry += "\n ) a";

		qry += "\n  left outer join warecode b on a.wareid=b.wareid";
		qry += "\n  left outer join housesaleprice h on a.wareid=h.wareid and a.houseid=h.houseid";
		qry += "\n  ;";

		qry += "\n   update v_cxjxctj_data a set locaid=(select locaid from wareloca b where a.wareid=b.wareid and a.colorid=b.colorid and b.entertag=1 and rownum=1)";
		// qry += "\n ,sxrate=case amountjh-amountjt when 0 then 1 else
		// round(amountxs / (amountjh-amountjt),4) end";
		qry += "\n   where epid=" + Userid + ";";
		qry += "\n   update v_cxjxctj_data set avgxs=round(amountxs/xsday,2) where epid=" + Userid + " and xsday>0;"; // 平均日销量

		qry += "\n   update v_cxjxctj_data set amountkc=amountkc0+amountjh-amountjt+amountdr-amountxs-amountdc+amountyk where epid=" + Userid + ";";

		qry += "\n   update v_cxjxctj_data set maxxsday=ceil(amountkc/avgxs) where epid=" + Userid + " and avgxs>0  and amountkc>0;"; // 可售天数

		qry += "\n   update v_cxjxctj_data a ";
		qry += "\n   set currkc=(select round(a.amountkc*b.costprice,2) from warecost b where a.wareid=b.wareid and b.daystr='" + Func.subString(Maxdate, 1, 10) + "'";
		if (Housecostbj == 1)
			qry += "  and a.houseid=b.houseid";
		else
			qry += "  and b.houseid=0";
		qry += " ) where epid=" + Userid + ";";
		//numzz:商品销售周转次数=本阶段商品销售额/((期初商品库存成本额+期末商品库存成本额)/2)  周转率
		qry += "\n    update v_cxjxctj_data set numzz=round(decode(currkc0+currkc,0,0,currxs/(currkc0+currkc)/2),2) where epid=" + Userid + ";";

		qry += "\n    select nvl(sum(amountjh),0),nvl(sum(amountjt),0),nvl(sum(amountxs),0),nvl(sum(amountkc),0),nvl(sum(currkc),0),nvl(sum(amountkc0),0),nvl(sum(currkc0),0) ";
		qry += "\n    into :amountjh,:amountjt,:amountxs,:amountkc,:currkc,:amountkc0,:currkc0 ";
		qry += "\n    from v_cxjxctj_data a  where a.epid=" + Userid + "; ";
		qry += "\n end;";
//		System.out.println(qry);
		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("amountjh", new ProdParam(Types.FLOAT));
		param.put("amountjt", new ProdParam(Types.FLOAT));
		param.put("amountxs", new ProdParam(Types.FLOAT));
		param.put("amountkc", new ProdParam(Types.FLOAT));
		param.put("currkc", new ProdParam(Types.DOUBLE));
		param.put("amountkc0", new ProdParam(Types.FLOAT));
		param.put("currkc0", new ProdParam(Types.DOUBLE));
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
		errmess = "\"msg\":\"操作成功！\",\"AMOUNTJH\":\"" + param.get("amountjh").getParamvalue() + "\"" //
				+ ",\"AMOUNTJT\":\"" + param.get("amountjt").getParamvalue() + "\"" //
				+ ",\"AMOUNTXS\":\"" + param.get("amountxs").getParamvalue() + "\""//
				+ ",\"AMOUNTKC\":\"" + param.get("amountkc").getParamvalue() + "\""//
				+ ",\"AMOUNTKC0\":\"" + param.get("amountkc0").getParamvalue() + "\""//
				+ ",\"CURRKC\":\"" + param.get("currkc").getParamvalue() + "\""//
				+ ",\"CURRKC0\":\"" + param.get("currkc0").getParamvalue() + "\""//

				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";

		return 1;
	}
}
