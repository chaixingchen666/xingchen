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
public class vTotalJxcmx {

	private Long Accid;// 账户id
	private Long Userid;// 用户id

	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Accbegindate = "";// 账套启用日期
	private Integer Priceprec = 0;

	private Long Wareid = (long) 0;//
	private Long Colorid = (long) 0;//
	private Long Sizeid = (long) 0;//
	private Long Provid = (long) -1;//
	private Long Typeid = (long) -1;//
	private String Brandidlist = "";//
	private String Houseidlist = "";//
	private String Typeidlist = "";//
	private String Providlist = "";//
	private String Areaidlist = "";//
	private String Wareno = "";//
	private String Warename = "";//
	private String Seasonname = "";//
	private String Prodyear = "";//

	private String Mindate;
	private String Maxdate;
	private String Calcdate = "";

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	private String errmess;

	public String getErrmess() {
		return errmess;
	}

	public void setPriceprec(Integer priceprec) {
		Priceprec = priceprec;
	}

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
	}

	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setTypeid(Long typeid) {
		Typeid = typeid;
	}

	// 商品+颜色+尺码记录明细
	public int Total2(int qxbj, int housecostbj, int maxday) {
		// cxfs:0=单据汇总，1=商品汇总，2=商品+颜色，3=商品+颜色+尺码
		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Func.subString(Serverdatetime, 1, 10), Accbegindate, maxday);
		Mindate = mdv.getMindate();
		String mess = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		// System.out.println("mindate="+Mindate+" maxdate="+Maxdate+"
		// mindate0="+mindate0+" Serverdatetime="+Serverdatetime+"
		// Accbegindate="+Accbegindate);
		if (Provid >= 0)
			Providlist = Provid.toString();
		String qry = "declare ";

		qry += "\n    v_bj number(1); ";
		qry += "\n    v_amount2 number; ";
		qry += "\n    v_curr2 number(16,2); ";
		qry += "\n    v_count  number; ";
		qry += "\n begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n        select ROWID from v_cxjxcmx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxjxcmx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n   commit;";
		qry += "\n    insert into v_cxjxcmx_data (id,epid,accid,notedate,noteno,notetype,remark,wlremark,wareid,colorid,sizeid,amount0,price0,curr0,pricerk,currrk,amount1,price1,curr1,pricexs,currxs,amount2,curr2) ";
		qry += "\n    select v_cxjxcmx_data_id.nextval," + Userid + "," + Accid
				+ ",notedate,noteno,notetype,remark,wlremark,wareid,colorid,sizeid,amount0,price0,curr0,pricerk,currrk,amount1,price1,curr1,pricexs,currxs,amount2,curr2 from (";

		qry += "\n    select ' ' as notedate,cast('期初' as nvarchar2(100)) as remark,cast(' ' as nvarchar2(20)) as noteno,'' as notetype,cast(' ' as nvarchar2(20)) as wlremark";
		// cxfs:0=单据汇总，1=商品汇总，2=商品+颜色，3=商品+颜色+尺码

		qry += "\n    ,0 as wareid,0 as colorid, 0 as sizeid";

		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,0 as amount1,0 as price1,0 as curr1,0 as pricexs,0 as currxs,nvl(sum(a.amount),0) as amount2,nvl(sum(a.amount*f.costprice),0) as curr2 ";

		qry += "\n    from waresum a ";
		qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n    left outer join warecost f on a.wareid=f.wareid and a.daystr=f.daystr";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";

		qry += "\n    where a.daystr='" + mindate0 + "' ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (b.brandid=0 or exists (select 1 from employebrand x2 where b.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and a.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and a.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and a.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and b.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and b.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and b.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and b.prodyear='" + Prodyear + "'";
		qry += "\n    and b.accid=" + Accid;
		// qry += "\n group by a.wareid,a.colorid,a.sizeid";

		qry += "\n    union all";
		// --初始入库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('初始' as nvarchar2(100)),a.noteno,d.housename
		// ,'QC',sum(b.amount),sum(b.curr),0,0 ";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,cast('初始' as nvarchar2(100)) as remark,a.noteno ,'QC' as notetype ,d.housename as wlremark";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,b.amount as amount0,b.price as price0,nvl(b.curr,0) as curr0,b.price as pricerk,b.curr as currrk,0 as amount1,0 as price1,0 as curr1,0 as pricexs,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from firsthouseh a  ";
		qry += "\n    join firsthousem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.accid=" + Accid;

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by a.notedate,a.noteno,d.housename";

		qry += "\n    union all";
		// --采购入库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('采购' as
		// nvarchar2(100)),a.noteno,e.provname||'->'||d.housename,'CG',sum(b.amount),sum(b.curr),0,0
		// ";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购' as nvarchar2(100)),a.noteno,'CG' as notetype,e.provname||'->'||d.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,b.amount as amount0,b.price as price0,nvl(b.curr,0) as curr0,b.price as pricerk,b.curr as currrk,0 as amount1,0 as price1,0 as curr1,0 as pricexs,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=0 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by
		// a.notedate,a.noteno,d.housename,e.provname||'->'||d.housename";

		qry += "\n    union all";
		// --采购退库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('采购退货' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.provna+me
		// ,'CT',-sum(b.amount),-sum(b.curr),0,0 ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购退货' as nvarchar2(100)),a.noteno,'CT' as notetype,d.housename||'->'||e.provname";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,-b.amount as amount0,nvl(f.costprice,0) as price0,-nvl(b.amount*f.costprice,0) as curr0,b.price as pricerk,-b.curr as currrk,0 as amount1,0 as price1,0 as curr1,0 as pricexs,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=1 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by
		// a.notedate,a.noteno,d.housename||'->'||e.provname";

		// --调入
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('调入' as nvarchar2(100)),a.noteno,'DR' as notetype,e.housename||'->'||d.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";

		qry += "\n    ,b.amount as amount0,nvl(f.costprice,0) as price0,nvl(b.amount*f.costprice,0) as curr0,b.price as pricerk,b.curr as currrk,0 as amount1,0 as price1,0 as curr1,0 as pricexs,0 as currxs ,0 as amount2,0 as curr2";
		qry += "\n    from allotinh a  ";
		qry += "\n    join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warehouse e on a.fromhouseid=e.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by
		// a.notedate,a.noteno,e.housename||'->'||d.housename ";

		// --盘盈
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('盘盈' as nvarchar2(100)),a.noteno,'PY' as notetype,d.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";

		qry += "\n    ,b.amount as amount0,nvl(f.costprice,0) as price0,nvl(b.amount*f.costprice,0) as curr0,b.price as pricerk,b.curr as currrk,0 as amount1,0 as price1,0 as curr1,0 as pricexs,0 as currxs ,0 as amount2,0 as curr2 ";
		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and b.amount>0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by a.notedate,a.noteno,d.housename ";

		// --零销售
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,currxs,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('零售' as
		// nvarchar2(100)),a.noteno,d.housename,'XX',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('零售' as nvarchar2(100)),a.noteno,'XX' as notetype,d.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,b.amount as amount1,nvl(f.costprice,0) as price1,nvl(b.amount*f.costprice,0) as curr1,b.price as pricexs,b.curr as currxs,0 as amount2,0 as curr2  ";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";

		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=0 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by a.notedate,a.noteno,d.housename ";
		// 批发
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,currxs,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('批发' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.custname,'XS',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发' as nvarchar2(100)),a.noteno,'XS' as notetype,d.housename||'->'||e.custname";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,b.amount as curr1,nvl(f.costprice,0) as price1,nvl(b.amount*f.costprice,0) as curr1,b.price as pricexs,b.curr as currxs,0 as amount2,0 as curr2";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=1 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by a.notedate,a.noteno,d.housename||'->'||e.custname
		// ";
		// 批发退货
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发退货' as nvarchar2(100)),a.noteno,'XT' as notetype,e.custname||'->'||d.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,-b.amount as amount1,nvl(f.costprice,0) as price1,nvl(-b.amount*f.costprice,0) as curr1,b.price as pricexs,-b.curr as currxs,0 as amount2,0 as curr2";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=2 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by a.notedate,a.noteno,e.custname||'->'||d.housename
		// ";

		// --商场销售
		qry += "\n    union all";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('商场销售' as nvarchar2(100)),a.noteno,'XC' as notetype,cast(f_shopsalehouselist(a.accid,a.noteno) as nvarchar2(100)) as wlremark";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,b.amount as amount1,nvl(f.costprice,0) as price1,nvl(b.amount*f.costprice,0) as curr1,b.price as pricexs,b.curr as currxs,0 as amount2,0 as curr2 ";

		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by
		// a.notedate,a.noteno,f_shopsalehouselist(a.accid,a.noteno)";//
		// f_shopsalehouselist(a.accid,a.noteno)

		// --调出
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,curr1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('调出' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.housename,'DC',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('调出' as nvarchar2(100)),a.noteno,'DC' as notetype,d.housename||'->'||e.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";

		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,b.amount as amount1,nvl(f.costprice,0) as price1,nvl(b.amount*f.costprice,0) as curr1,b.price as pricexs,b.curr as currxs,0 as amount2,0 as curr2";

		qry += "\n    from allotouth a  ";
		qry += "\n    join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warehouse e on a.tohouseid=e.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by
		// a.notedate,a.noteno,d.housename||'->'||e.housename ";

		// --盘亏
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,curr1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('盘亏' as
		// nvarchar2(100)),a.noteno,d.housename,'PK',0,-sum(b.amount),-sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('盘亏' as nvarchar2(100)),a.noteno,'PK' as notetype,d.housename";
		qry += "\n    ,b.wareid,b.colorid,b.sizeid";
		qry += "\n    ,0 as amount0,0 as price0,0 as curr0,0 as pricerk,0 as currrk,-b.amount as amount1,nvl(f.costprice,0) as price1,nvl(-b.amount*f.costprice,0) as curr1,b.price as pricexs,-b.curr as currxs,0 as amount2,0 as curr2";

		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and b.amount<0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		// qry += "\n group by a.notedate,a.noteno,d.housename ";
		qry += "\n  ); ";
		// qry += "\n insert into v_cxjxcmx_data
		// (id,epid,notedate,noteno,notetype,remark,wlremark,amount0,curr0,amount1,curr1,currxs,amount2)
		// ";
		// qry += "\n (select v_cxjxcmx_data_id.nextval," + Userid
		// + ",
		// notedate,noteno,notetype,remark,wlremark,nvl(amount0,0),nvl(curr0,0),nvl(amount1,0),nvl(curr1,0),nvl(currxs,0),nvl(amount2,0)
		// from v_cxjxcmx_temp); ";
		// --计算余额
		qry += "\n    declare cursor cur_v_cxjxcmx_data is select id,amount0,curr0,amount1,curr1,amount2,curr2 from v_cxjxcmx_data where epid=" + Userid + " order by notedate,id;  ";
		// qry += " for update nowait; ";
		qry += "\n      v_row cur_v_cxjxcmx_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n      v_amount2:=0; v_curr2:=0;";
		qry += "\n      v_bj:=0; v_count:=0; ";
		qry += "\n      for v_row in cur_v_cxjxcmx_data loop ";
		qry += "\n        if v_bj=0 then ";
		qry += "\n           v_amount2:=v_row.amount2; ";
		qry += "\n           v_curr2:=v_row.curr2; ";
		qry += "\n           v_bj:=1; ";
		qry += "\n        else ";
		qry += "\n            v_amount2:=v_amount2+v_row.amount0-v_row.amount1; ";
		// qry += "\n v_curr2:=v_curr2+v_row.curr2;";//v_row.curr0-v_row.curr1;
		// ";
		qry += "\n            v_curr2:=v_curr2+v_row.curr0-v_row.curr1; ";
		qry += "\n            update v_cxjxcmx_data set amount2=v_amount2, curr2=v_curr2 where id=v_row.id; ";
		qry += "\n        end if; ";
		qry += "\n        if v_count>5000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";

		qry += "\n    end; ";
		// --v_balamt:=v_amount2;
		qry += "\n    commit;";
		qry += "\n    update v_cxjxcmx_data set price2=round(curr2/amount2,2) where epid=" + Userid + " and amount2<>0;";

		qry += "\n    select nvl(sum(a.amount0),0),nvl(sum(a.curr0),0),nvl(sum(a.amount1),0),nvl(sum(a.curr1),0),nvl(sum(a.currxs),0),v_amount2,v_curr2 ";
		qry += "\n    into :totalamt0,:totalcurr0,:totalamt1,:totalcurr1,:totalcurrxs,:balamt,:balcurr from v_cxjxcmx_data a  where a.epid=" + Userid + "; ";
		qry += "\n end;";

		// System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalcurr0", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		param.put("totalcurrxs", new ProdParam(Types.FLOAT));
		param.put("balamt", new ProdParam(Types.FLOAT));
		param.put("balcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;

		}
		errmess = "\"TOTALAMT0\":\"" + param.get("totalamt0").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR0\":\"" + param.get("totalcurr0").getParamvalue().toString() + "\""//
				+ ",\"TOTALAMT1\":\"" + param.get("totalamt1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR1\":\"" + param.get("totalcurr1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURRXS\":\"" + param.get("totalcurrxs").getParamvalue().toString() + "\""//
				+ ",\"BALAMT\":\"" + param.get("balamt").getParamvalue().toString() + "\""//
				+ ",\"BALCURR\":\"" + param.get("balcurr").getParamvalue().toString() + "\""//
				+ ",\"WARNING\":\"" + mess + "\""//
		;
		return 1;

	}

	// 商品颜色汇总
	public int Total1(int qxbj, int cxfs, int housecostbj, int maxday) {
		// cxfs:，1=商品汇总，2=商品+颜色
		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Func.subString(Serverdatetime, 1, 10), Accbegindate, maxday);
		Mindate = mdv.getMindate();
		String mess = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		// System.out.println("mindate="+Mindate+" maxdate="+Maxdate+"
		// mindate0="+mindate0+" Serverdatetime="+Serverdatetime+"
		// Accbegindate="+Accbegindate);
		if (Provid >= 0)
			Providlist = Provid.toString();
		String qry = "declare ";

		qry += "\n    v_bj number(1); ";
		qry += "\n    v_amount2 number; ";
		qry += "\n    v_curr2 number(16,2); ";
		qry += "\n    v_count  number; ";
		qry += "\n    v_price0 number; ";
		qry += "\n    v_price1 number; ";
		qry += "\n    v_pricerk number; ";
		qry += "\n    v_pricexs number; ";
		qry += "\n begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n        select ROWID from v_cxjxcmx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxjxcmx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n   commit;";
		qry += "\n    insert into v_cxjxcmx_data (id,epid,accid,notedate,noteno,notetype,remark,wlremark";
		if (cxfs == 1)
			qry += " ,wareid";
		else
			qry += ",wareid,colorid";
		qry += "\n    ,amount0,curr0,currrk,amount1,curr1,currxs,amount2,curr2) ";
		qry += "\n    select v_cxjxcmx_data_id.nextval," + Userid + "," + Accid + ",notedate,noteno,notetype,remark,wlremark";
		if (cxfs == 1)
			qry += " ,wareid";
		else
			qry += ",wareid,colorid";
		qry += "\n    ,amount0,curr0,currrk,amount1,curr1,currxs,amount2,curr2 from (";

		qry += "\n    select ' ' as notedate,cast('期初' as nvarchar2(100)) as remark,cast(' ' as nvarchar2(20)) as noteno,'' as notetype,cast(' ' as nvarchar2(20)) as wlremark";
		// cxfs:0=单据汇总，1=商品汇总，2=商品+颜色，3=商品+颜色+尺码
		if (cxfs == 1)
			qry += " ,0 as wareid";
		else
			qry += ",0 as wareid,0 as colorid";

		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,0 as amount1,0 as curr1,0 as currxs,nvl(sum(a.amount),0) as amount2,nvl(sum(a.amount*f.costprice),0) as curr2 ";
		// qry += "\n ,0 as price0,0 as price1";
		qry += "\n    from waresum a ";
		qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
		qry += "\n    left outer join warecost f on a.wareid=f.wareid and f.daystr=a.daystr";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.daystr='" + mindate0 + "' ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (b.brandid=0 or exists (select 1 from employebrand x2 where b.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and a.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and a.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and a.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and b.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and b.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("b.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and b.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and b.prodyear='" + Prodyear + "'";
		qry += "\n    and b.accid=" + Accid;
		// if (cxfs == 1)
		// qry += "\n group by a.wareid";
		// else
		// qry += "\n group by a.wareid,a.colorid";
		qry += "\n    union all";
		// --初始入库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('初始' as nvarchar2(100)),a.noteno,d.housename
		// ,'QC',sum(b.amount),sum(b.curr),0,0 ";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,cast('初始' as nvarchar2(100)) as remark,a.noteno ,'QC' as notetype ,d.housename as wlremark";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,sum(b.amount) as amount0,sum(b.curr) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";
		qry += "\n    from firsthouseh a  ";
		qry += "\n    join firsthousem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		qry += "\n    and a.accid=" + Accid;

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";

		qry += "\n    union all";
		// --采购入库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('采购' as
		// nvarchar2(100)),a.noteno,e.provname||'->'||d.housename,'CG',sum(b.amount),sum(b.curr),0,0
		// ";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购' as nvarchar2(100)),a.noteno,'CG' as notetype,e.provname||'->'||d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,sum(b.amount) as amount0,sum(b.curr) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=0 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,e.provname||'->'||d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    union all";
		// --采购退库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('采购退货' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.provna+me
		// ,'CT',-sum(b.amount),-sum(b.curr),0,0 ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购退货' as nvarchar2(100)),a.noteno,'CT' as notetype,d.housename||'->'||e.provname";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,-sum(b.amount) as amount0,-nvl(sum(b.amount*f.costprice),0) as curr0,-sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1  and a.ntid=1 and a.accid=" + Accid;
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename||'->'||e.provname";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		// --调入
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('调入' as nvarchar2(100)),a.noteno,'DR' as notetype,e.housename||'->'||d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,sum(b.amount) as amount0,nvl(sum(b.amount*f.costprice),0) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";
		qry += "\n    from allotinh a  ";
		qry += "\n    join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warehouse e on a.fromhouseid=e.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,e.housename||'->'||d.housename ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";

		// --盘盈
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('盘盈' as nvarchar2(100)),a.noteno,'PY' as notetype,d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,sum(b.amount) as amount0,nvl(sum(b.amount*f.costprice),0) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2 ";
		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and b.amount>0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";

		// --零销售
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,currxs,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('零售' as
		// nvarchar2(100)),a.noteno,d.housename,'XX',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('零售' as nvarchar2(100)),a.noteno,'XX' as notetype,d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as amount1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2  ";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";

		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=0 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		// 批发
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,currxs,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('批发' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.custname,'XS',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发' as nvarchar2(100)),a.noteno,'XS' as notetype,d.housename||'->'||e.custname";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as curr1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=1 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename||'->'||e.custname ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		// 批发退货
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发退货' as nvarchar2(100)),a.noteno,'XT' as notetype,e.custname||'->'||d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,-sum(b.amount) as amount1,nvl(-sum(b.amount*f.costprice),0) as curr1,-sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=2 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,e.custname||'->'||d.housename ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";

		// --商场销售
		qry += "\n    union all";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('商场销售' as nvarchar2(100)),a.noteno,'XC' as notetype,cast(f_shopsalehouselist(a.accid,a.noteno) as nvarchar2(100)) as wlremark";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as amount1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2 ";

		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,f_shopsalehouselist(a.accid,a.noteno)";// f_shopsalehouselist(a.accid,a.noteno)
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";

		// --调出
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,curr1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('调出' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.housename,'DC',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('调出' as nvarchar2(100)),a.noteno,'DC' as notetype,d.housename||'->'||e.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as amount1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from allotouth a  ";
		qry += "\n    join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warehouse e on a.tohouseid=e.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename||'->'||e.housename ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";

		// --盘亏
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,curr1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('盘亏' as
		// nvarchar2(100)),a.noteno,d.housename,'PK',0,-sum(b.amount),-sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('盘亏' as nvarchar2(100)),a.noteno,'PK' as notetype,d.housename";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,-sum(b.amount) as amount1,nvl(-sum(b.amount*f.costprice),0) as curr1,-sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and b.amount<0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename ";
		if (cxfs == 1)
			qry += ", b.wareid";
		else
			qry += ",b.wareid,b.colorid";
		qry += "\n  ); ";
		// qry += "\n insert into v_cxjxcmx_data
		// (id,epid,notedate,noteno,notetype,remark,wlremark,amount0,curr0,amount1,curr1,currxs,amount2)
		// ";
		// qry += "\n (select v_cxjxcmx_data_id.nextval," + Userid
		// + ",
		// notedate,noteno,notetype,remark,wlremark,nvl(amount0,0),nvl(curr0,0),nvl(amount1,0),nvl(curr1,0),nvl(currxs,0),nvl(amount2,0)
		// from v_cxjxcmx_temp); ";
		// --计算余额
		qry += "\n    declare cursor cur_v_cxjxcmx_data is select id,amount0,curr0,currrk,amount1,curr1,currxs,amount2,curr2 from v_cxjxcmx_data where epid=" + Userid + " order by notedate,id;  ";
		// qry += " for update nowait; ";
		qry += "\n      v_row cur_v_cxjxcmx_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n      v_amount2:=0; v_curr2:=0;";
		qry += "\n      v_bj:=0; v_count:=0; ";
		qry += "\n      for v_row in cur_v_cxjxcmx_data loop ";
		qry += "\n          if v_bj=0 then ";
		qry += "\n            v_amount2:=v_row.amount2; ";
		qry += "\n            v_curr2:=v_row.curr2; ";
		qry += "\n            v_bj:=1; ";
		qry += "\n          else ";
		qry += "\n          if v_row.amount0=0 then";
		qry += "\n             v_price0:=0;";
		qry += "\n             v_pricerk:=0;";
		qry += "\n          else ";
		qry += "\n             v_price0:=round(v_row.curr0/v_row.amount0,2);";
		qry += "\n             v_pricerk:=round(v_row.currrk/v_row.amount0," + Priceprec + ");";
		qry += "\n          end if;";
		qry += "\n          if v_row.amount1=0 then";
		qry += "\n             v_price1:=0;";
		qry += "\n             v_pricexs:=0;";
		qry += "\n          else ";
		qry += "\n             v_price1:=round(v_row.curr1/v_row.amount1,2);";
		qry += "\n             v_pricexs:=round(v_row.currxs/v_row.amount1," + Priceprec + ");";
		qry += "\n          end if;";
		qry += "\n          v_amount2:=v_amount2+v_row.amount0-v_row.amount1; ";
		qry += "\n          v_curr2:=v_curr2+v_row.curr0-v_row.curr1; ";
		qry += "\n          update v_cxjxcmx_data set amount2=v_amount2, curr2=v_curr2,price0=v_price0,price1=v_price1 ,pricerk=v_pricerk,pricexs=v_pricexs where id=v_row.id; ";
		qry += "\n        end if; ";
		qry += "\n        if v_count>5000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";

		qry += "\n    end; ";
		// --v_balamt:=v_amount2;
		qry += "\n    commit;";
		qry += "\n    update v_cxjxcmx_data set price2=round(curr2/amount2,2) where epid=" + Userid + " and amount2<>0;";

		qry += "\n    select nvl(sum(a.amount0),0),nvl(sum(a.curr0),0),nvl(sum(a.amount1),0),nvl(sum(a.curr1),0),nvl(sum(a.currxs),0),v_amount2,v_curr2 ";
		qry += "\n    into :totalamt0,:totalcurr0,:totalamt1,:totalcurr1,:totalcurrxs,:balamt,:balcurr from v_cxjxcmx_data a  where a.epid=" + Userid + "; ";
		qry += "\n end;";
		// System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalcurr0", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		param.put("totalcurrxs", new ProdParam(Types.FLOAT));
		param.put("balamt", new ProdParam(Types.FLOAT));
		param.put("balcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;

		}
		errmess = "\"TOTALAMT0\":\"" + param.get("totalamt0").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR0\":\"" + param.get("totalcurr0").getParamvalue().toString() + "\""//
				+ ",\"TOTALAMT1\":\"" + param.get("totalamt1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR1\":\"" + param.get("totalcurr1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURRXS\":\"" + param.get("totalcurrxs").getParamvalue().toString() + "\""//
				+ ",\"BALAMT\":\"" + param.get("balamt").getParamvalue().toString() + "\""//
				+ ",\"BALCURR\":\"" + param.get("balcurr").getParamvalue().toString() + "\""//
				+ ",\"WARNING\":\"" + mess + "\""//
		;
		return 1;

	}

	// 单据汇总
	public int Total(int qxbj, int cxfs, int housecostbj, int maxday) {

		// cxfs:0=单据汇总，1=商品汇总，2=商品+颜色，3=商品+颜色+尺码
		if (cxfs == 3) {
			return Total2(qxbj, housecostbj, maxday);
		} else if (cxfs == 1 || cxfs == 2) {
			return Total1(qxbj, cxfs, housecostbj, maxday);

		}

		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Func.subString(Serverdatetime, 1, 10), Accbegindate, maxday);
		Mindate = mdv.getMindate();
		String mess = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期

		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		int bj = 0;
		if (Calcdate.length() > 0 && Calcdate.compareTo(mindate0) < 0) {
			// Mindate = Calcdate;
			// System.out.println("Calcdate=" + Calcdate);

			// 计算库存
			vTotalCxkczy kc = new vTotalCxkczy();
			kc.setAccid(Accid);
			kc.setUserid(Userid);
			kc.setCxdatetime(mindate0);
			kc.setCalcdate(Calcdate);
			kc.setQxbj(qxbj);
			kc.setWareid(Wareid);
			kc.setColorid(Colorid);
			kc.setSizeid(Sizeid);
			kc.setProdyear(Prodyear);
			kc.setWarename(Warename);
			kc.setWareno(Wareno);
			kc.setTypeid(Typeid);
			kc.setSeasonname(Seasonname);
			kc.setHouseidlist(Houseidlist);
			kc.setBrandidlist(Brandidlist);
			// kc.setTypeidlist(Typeidlist);
			kc.setAreaidlist(Areaidlist);
			kc.setProvidlist(Providlist);

			kc.doCxkczy();
			bj = 1;
		}

		// System.out.println("mindate="+Mindate+" maxdate="+Maxdate+"
		// mindate0="+mindate0+" Serverdatetime="+Serverdatetime+"
		// Accbegindate="+Accbegindate);
		if (Provid >= 0)
			Providlist = Provid.toString();
		String qry = "declare ";

		qry += "\n    v_bj number(1); ";
		qry += "\n    v_amount2 number; ";
		qry += "\n    v_curr2 number(16,2); ";
		qry += "\n    v_count  number; ";
		qry += "\n begin ";

		qry += "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "\n        select ROWID from v_cxjxcmx_data where epid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n          delete from v_cxjxcmx_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";
		qry += "\n    commit;";

		qry += "\n    insert into v_cxjxcmx_data (id,epid,accid,notedate,noteno,notetype,remark,wlremark,amount0,curr0,currrk,amount1,curr1,currxs,amount2,curr2) ";
		qry += "\n    select v_cxjxcmx_data_id.nextval," + Userid + "," + Accid + ",notedate,noteno,notetype,remark,wlremark,amount0,curr0,currrk,amount1,curr1,currxs,amount2,curr2 from (";
		if (bj == 1) {
			qry += "\n    select ' ' as notedate,cast('期初' as nvarchar2(100)) as remark,cast(' ' as nvarchar2(20)) as noteno,'' as notetype,cast(' ' as nvarchar2(20)) as wlremark";
			qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,0 as amount1,0 as curr1,0 as currxs,nvl(sum(a.amount),0) as amount2,nvl( sum(round(a.amount*f.costprice,2)) ,0) as curr2 ";
			qry += "\n    from v_cxkczy_data a ";
			qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
			qry += "\n    left outer join warecost f on a.wareid=f.wareid and f.daystr='" + mindate0 + "'";
			if (housecostbj == 1)// 1= 分店铺计算成本
				qry += " and a.houseid=f.houseid";
			else
				qry += " and f.houseid=0";
			qry += "\n    where a.epid=" + Userid;
		} else {
			qry += "\n    select ' ' as notedate,cast('期初' as nvarchar2(100)) as remark,cast(' ' as nvarchar2(20)) as noteno,'' as notetype,cast(' ' as nvarchar2(20)) as wlremark";
			qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,0 as amount1,0 as curr1,0 as currxs,nvl(sum(a.amount),0) as amount2,nvl( sum(round(a.amount*f.costprice,2)) ,0) as curr2 ";
			qry += "\n    from waresum a ";
			qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
			qry += "\n    left outer join warecost f on a.wareid=f.wareid and a.daystr=f.daystr";
			if (housecostbj == 1)// 1= 分店铺计算成本
				qry += " and a.houseid=f.houseid";
			else
				qry += " and f.houseid=0";
			qry += "\n    where a.daystr='" + mindate0 + "' and b.statetag=1 ";
			qry += "\n    and b.accid=" + Accid;
			if (qxbj == 1) // 1 启用权限控制
			{
				qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
				qry += "\n    and (b.brandid=0 or exists (select 1 from employebrand x2 where b.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
			}
			if (Houseidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
			if (Wareid > 0)
				qry += "\n      and a.wareid=" + Wareid;
			if (Colorid > 0)
				qry += "\n      and a.colorid=" + Colorid;
			if (Sizeid > 0)
				qry += "\n      and a.sizeid=" + Sizeid;
			if (Wareno.length() > 0)
				qry += "\n      and b.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "\n      and b.warename like '%" + Warename + "%'";
			if (Typeidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("b.typeid", Typeidlist);
			if (Brandidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("b.brandid", Brandidlist);
			if (Providlist.length() > 0)
				qry += "\n   and " + Func.getCondition("b.provid", Providlist);
			if (Areaidlist.length() > 0)
				qry += "\n   and " + Func.getCondition("b.areaid", Areaidlist);
			if (Seasonname.length() > 0)
				qry += "\n      and b.seasonname='" + Seasonname + "'";
			if (Prodyear.length() > 0)
				qry += "\n      and b.prodyear='" + Prodyear + "'";
		}
		qry += "\n    union all";
		// --初始入库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('初始' as nvarchar2(100)),a.noteno,d.housename
		// ,'QC',sum(b.amount),sum(b.curr),0,0 ";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,cast('初始' as nvarchar2(100)) as remark,a.noteno ,'QC' as notetype ,d.housename as wlremark";
		qry += "\n    ,sum(b.amount) as amount0,sum(b.curr) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from firsthouseh a  ";
		qry += "\n    join firsthousem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and a.accid=" + Accid;
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename";

		qry += "\n    union all";
		// --采购入库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('采购' as
		// nvarchar2(100)),a.noteno,e.provname||'->'||d.housename,'CG',sum(b.amount),sum(b.curr),0,0
		// ";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购' as nvarchar2(100)),a.noteno,'CG' as notetype,e.provname||'->'||d.housename";
		qry += "\n    ,sum(b.amount) as amount0,sum(b.curr) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=0 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,e.provname||'->'||d.housename";

		qry += "\n    union all";
		// --采购退库
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('采购退货' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.provna+me
		// ,'CT',-sum(b.amount),-sum(b.curr),0,0 ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购退货' as nvarchar2(100)),a.noteno,'CT' as notetype,d.housename||'->'||e.provname";
		qry += "\n    ,-sum(b.amount) as amount0,-nvl(sum(b.amount*f.costprice),0) as curr0,-sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";

		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.ntid=1 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename||'->'||e.provname";

		// --调入
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('调入' as nvarchar2(100)),a.noteno,'DR' as notetype,e.housename||'->'||d.housename";
		qry += "\n    ,sum(b.amount) as amount0,nvl(sum(b.amount*f.costprice),0) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2";
		qry += "\n    from allotinh a  ";
		qry += "\n    join allotinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warehouse e on a.fromhouseid=e.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
											// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,e.housename||'->'||d.housename ";

		// --盘盈
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,curr0,amount1,amount2)
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('盘盈' as nvarchar2(100)),a.noteno,'PY' as notetype,d.housename";
		qry += "\n    ,sum(b.amount) as amount0,nvl(sum(b.amount*f.costprice),0) as curr0,sum(b.curr) as currrk,0 as amount1,0 as curr1,0 as currxs ,0 as amount2,0 as curr2 ";
		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 ";// and
											// to_char(a.notedate,'yyyy-mm-dd')
											// >='"+mindate+"' ";
		qry += "\n    and b.amount>0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		qry += "\n    and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename ";

		// --零销售
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,currxs,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('零售' as
		// nvarchar2(100)),a.noteno,d.housename,'XX',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('零售' as nvarchar2(100)),a.noteno,'XX' as notetype,d.housename";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as amount1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2  ";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd') ";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";

		qry += "\n    where a.statetag=1 and a.ntid=0 and a.accid=" + Accid;// and
																			// to_char(a.notedate,'yyyy-mm-dd')
																			// >='"+mindate+"'
																			// ";
																			// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename ";
		// 批发
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,currxs,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('批发' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.custname,'XS',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发' as nvarchar2(100)),a.noteno,'XS' as notetype,d.housename||'->'||e.custname";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as curr1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1  and a.ntid=1 and a.accid=" + Accid;// and
																				// to_char(a.notedate,'yyyy-mm-dd')
																				// >='"+mindate+"'
																				// ";
																				// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		// qry += "\n and a.ntid=1 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename||'->'||e.custname ";
		// 批发退货
		qry += "\n    union all";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发退货' as nvarchar2(100)),a.noteno,'XT' as notetype,e.custname||'->'||d.housename";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,-sum(b.amount) as amount1,nvl(-sum(b.amount*f.costprice),0) as curr1,-sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 and a.ntid=2 and a.accid=" + Accid;// and
																			// to_char(a.notedate,'yyyy-mm-dd')
																			// >='"+mindate+"'
																			// ";
																			// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		// qry += "\n and a.ntid=2 and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,e.custname||'->'||d.housename ";

		// --商场销售
		qry += "\n    union all";

		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('商场销售' as nvarchar2(100)),a.noteno,'XC' as notetype,d.housename as wlremark";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as amount1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2 ";

		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1  and a.accid=" + Accid;// and
																// to_char(a.notedate,'yyyy-mm-dd')
																// >='"+mindate+"'
																// ";
																// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		// qry += "\n and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename";// f_shopsalehouselist(a.accid,a.noteno)

		// --调出
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,curr1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('调出' as
		// nvarchar2(100)),a.noteno,d.housename||'->'||e.housename,'DC',0,sum(b.amount),sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('调出' as nvarchar2(100)),a.noteno,'DC' as notetype,d.housename||'->'||e.housename";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,sum(b.amount) as amount1,nvl(sum(b.amount*f.costprice),0) as curr1,sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from allotouth a  ";
		qry += "\n    join allotoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warehouse e on a.tohouseid=e.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1  and a.accid=" + Accid;// and
																// to_char(a.notedate,'yyyy-mm-dd')
																// >='"+mindate+"'
																// ";
																// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		// qry += "\n and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename||'->'||e.housename ";

		// --盘亏
		qry += "\n    union all";
		// qry += "\n insert into v_cxjxcmx_temp
		// (notedate,remark,noteno,wlremark,notetype,amount0,amount1,curr1,amount2)
		// ";
		// qry += "\n select to_char(a.notedate,'yyyy-mm-dd
		// hh24:mi:ss'),cast('盘亏' as
		// nvarchar2(100)),a.noteno,d.housename,'PK',0,-sum(b.amount),-sum(b.curr),0
		// ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('盘亏' as nvarchar2(100)),a.noteno,'PK' as notetype,d.housename";
		qry += "\n    ,0 as amount0,0 as curr0,0 as currrk,-sum(b.amount) as amount1,nvl(-sum(b.amount*f.costprice),0) as curr1,-sum(b.curr) as currxs,0 as amount2,0 as curr2";

		qry += "\n    from warecheckh a  ";
		qry += "\n    join warecheckm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join warecost f on b.wareid=f.wareid and f.daystr=to_char(a.notedate,'yyyy-mm-dd')";
		if (housecostbj == 1)// 1= 分店铺计算成本
			qry += " and a.houseid=f.houseid";
		else
			qry += " and f.houseid=0";
		qry += "\n    where a.statetag=1 and a.accid=" + Accid;// and
																// to_char(a.notedate,'yyyy-mm-dd')
																// >='"+mindate+"'
																// ";
		qry += "\n    and b.amount<0 ";// and to_char(a.notedate,'yyyy-mm-dd')
										// <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd') and a.notedate<=to_date('" + Maxdate + " 23:59:59','yyyy-mm-dd hh24:mi:ss')";

		// qry += "\n and a.accid=" + Accid + " ";
		if (qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + " )) ";
		}
		if (Houseidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("a.houseid", Houseidlist);
		if (Wareid > 0)
			qry += "\n      and b.wareid=" + Wareid;
		if (Colorid > 0)
			qry += "\n      and b.colorid=" + Colorid;
		if (Sizeid > 0)
			qry += "\n      and b.sizeid=" + Sizeid;
		if (Wareno.length() > 0)
			qry += "\n      and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "\n      and c.warename like '%" + Warename + "%'";
		if (Typeidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.typeid", Typeidlist);
		if (Brandidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.brandid", Brandidlist);
		if (Providlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.provid", Providlist);
		if (Areaidlist.length() > 0)
			qry += "\n   and " + Func.getCondition("c.areaid", Areaidlist);
		if (Seasonname.length() > 0)
			qry += "\n      and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n      and c.prodyear='" + Prodyear + "'";
		qry += "\n    group by a.notedate,a.noteno,d.housename ";
		qry += "\n  ); ";
		// qry += "\n insert into v_cxjxcmx_data
		// (id,epid,notedate,noteno,notetype,remark,wlremark,amount0,curr0,amount1,curr1,currxs,amount2)
		// ";
		// qry += "\n (select v_cxjxcmx_data_id.nextval," + Userid
		// + ",
		// notedate,noteno,notetype,remark,wlremark,nvl(amount0,0),nvl(curr0,0),nvl(amount1,0),nvl(curr1,0),nvl(currxs,0),nvl(amount2,0)
		// from v_cxjxcmx_temp); ";
		// --计算余额
		qry += "\n    declare cursor cur_v_cxjxcmx_data is ";
		qry += "\n    select id,amount0,curr0,amount1,curr1,amount2,curr2 from v_cxjxcmx_data where epid=" + Userid + " order by notedate,id;  ";
		// qry += " for update nowait; ";
		qry += "\n      v_row cur_v_cxjxcmx_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n      v_amount2:=0; v_curr2:=0;";
		qry += "\n      v_bj:=0; v_count:=0; ";
		qry += "\n      for v_row in cur_v_cxjxcmx_data loop ";
		qry += "\n        if v_bj=0 then ";
		qry += "\n           v_amount2:=v_row.amount2; ";
		qry += "\n           v_curr2:=v_row.curr2; ";
		qry += "\n           v_bj:=1; ";
		qry += "\n        else ";
		qry += "\n          v_amount2:=v_amount2+v_row.amount0-v_row.amount1; ";
		qry += "\n          v_curr2:=v_curr2+v_row.curr0-v_row.curr1; ";
		// qry += "\n v_curr2:=v_curr2+v_row.curr2; ";
		qry += "\n          update v_cxjxcmx_data set amount2=v_amount2, curr2=v_curr2 where id=v_row.id; ";
		qry += "\n        end if; ";
		qry += "\n        if v_count>5000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";

		qry += "\n    end; ";
		qry += "\n    update v_cxjxcmx_data set price2=round(curr2/amount2,2) where epid=" + Userid + " and amount2<>0;";
		// --v_balamt:=v_amount2;
		qry += "\n    commit;";

		qry += "\n    select nvl(sum(a.amount0),0),nvl(sum(a.curr0),0),nvl(sum(a.currrk),0),nvl(sum(a.amount1),0),nvl(sum(a.curr1),0),nvl(sum(a.currxs),0),v_amount2,v_curr2 ";
		qry += "\n    into :totalamt0,:totalcurr0,:totalcurrrk,:totalamt1,:totalcurr1,:totalcurrxs,:balamt,:balcurr from v_cxjxcmx_data a  where a.epid=" + Userid + "; ";
		qry += "\n end;";
		// //System.out.println(qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalcurr0", new ProdParam(Types.FLOAT));
		param.put("totalcurrrk", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		param.put("totalcurrxs", new ProdParam(Types.FLOAT));
		param.put("balamt", new ProdParam(Types.FLOAT));
		param.put("balcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;

		}
		errmess = "\"msg\":\"操作成功！\",\"TOTALAMT0\":\"" + param.get("totalamt0").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR0\":\"" + param.get("totalcurr0").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURRRK\":\"" + param.get("totalcurrrk").getParamvalue().toString() + "\""//
				+ ",\"TOTALAMT1\":\"" + param.get("totalamt1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURR1\":\"" + param.get("totalcurr1").getParamvalue().toString() + "\""//
				+ ",\"TOTALCURRXS\":\"" + param.get("totalcurrxs").getParamvalue().toString() + "\""//
				+ ",\"BALAMT\":\"" + param.get("balamt").getParamvalue().toString() + "\""//
				+ ",\"BALCURR\":\"" + param.get("balcurr").getParamvalue().toString() + "\""//
				+ ",\"WARNING\":\"" + mess + "\""//
		;
		return 1;
	}

	// 分页显示
	public Table GetTable(QueryParam qp, int cxfs) {
		// cxfs 0=单据，1=商品，2=商品+颜色，3=商品+颜色+尺码

		StringBuilder strSql = new StringBuilder();
		strSql.append(" select a.id,a.notedate,a.notetype,a.noteno,a.remark,a.wlremark ");
		if (cxfs > 0)
			strSql.append("\n ,a.price0,a.price1");
		if (cxfs >= 1)
			strSql.append("\n ,a.wareid,b.warename,b.units,b.wareno");
		if (cxfs >= 2)
			strSql.append("\n ,a.colorid,c.colorname");
		if (cxfs == 3)
			strSql.append("\n ,a.sizeid,d.sizename,d.sizeno");

		strSql.append("\n  ,a.amount0,a.curr0,a.currrk,a.amount1,a.curr1,a.currxs,a.amount2,a.price2,a.curr2");
		strSql.append("\n FROM v_cxjxcmx_data a");

		if (cxfs >= 1) {
			strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		}
		if (cxfs >= 2) {
			strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		}
		if (cxfs == 3) {
			strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		}
		strSql.append("\n where a.epid=" + Userid);

		String sort = "notedate,id";

		if (cxfs == 1)
			sort = "notedate,wareno,id";
		else if (cxfs == 2)
			sort = "notedate,wareno,colorid,id";
		else if (cxfs == 3)
			sort = "notedate,wareno,colorid,sizeno,id";

		qp.setSortString(sort);
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}
}