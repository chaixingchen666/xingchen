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
//import com.common.tool.SizeParam;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-07 23:33:58
//处理采购入库核价
//*************************************
public class vWareincheck {

	private String Mindate;
	private String Maxdate;

	private String Accbegindate = "2000-01-01";
	private String Nowdate;
	private String Calcdate = "";

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	private Long Userid;
	private Long Accid;
	private Long Areaid = (long) -1;
	private Integer Iszero = 0;
	private Integer Priceprec = 0;
	private Float Id = (float) 0;
	private Float Price = (float) 0;
	private Float Discount = (float) 0;
	private Integer Tkbj = 0;
	private Integer Zlbj = 0;

	private Integer Qxbj = 0;

	private Long Wareid = (long) 0;
	private Long Houseid = (long) 0;
	private Long Provid = (long) -1;
	private Long Typeid = (long) -1;
	private Long Brandid = (long) -1;
	private String Noteno = "";
	private String Wareno = "";
	private String Warename = "";
	private String Findbox = "";
	private String Seasonname = "";
	private String Prodyear = "";
	private String errmess = "";

	public String getErrmess() {
		return errmess;
	}

	public void setNowdate(String nowdate) {
		Nowdate = nowdate;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setAccbegindate(String accbegindate) {
		Accbegindate = accbegindate;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public void setPriceprec(Integer priceprec) {
		Priceprec = priceprec;
	}

	public void setQxbj(Integer qxbj) {
		Qxbj = qxbj;
	}

	// 刷新采购明细价格(核价)根据供应商价格方式和商品档案价格刷新采购单上的单价
	public int Refresh() {
		if (Func.isNull(Mindate))
			Mindate = Nowdate;
		if (Func.isNull(Maxdate))
			Maxdate = Nowdate;
		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";

		String qry = "declare ";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_noteno1 varchar2(20);";
		qry += "\n     v_notedatestr varchar2(10);";
		qry += "\n     v_notedate date;";
		qry += "\n     v_minnotedate date;";
		qry += "\n     v_wareid number;";
		qry += "\n     v_price0 number(16,4);";
		qry += "\n     v_discount number(10,2);";
		qry += "\n     v_price number(16,4);";
		qry += "\n     v_id number;";
		qry += "\n     v_count number;";
		qry += "\n     v_totalcurr number;";
		qry += "\n     v_totalcheckcurr number;";
		qry += "\n     v_totalamt number;";
		qry += "\n     v_provid number;";
		qry += "\n     v_houseid number;";
		qry += "\n     v_provid1 number;";
		qry += "\n     v_houseid1 number;";
		qry += "\n begin ";
		qry += "\n     v_notedate:=sysdate; ";
		qry += "\n     declare cursor cur_data is ";
		qry += "\n       select a.noteno,a.notedate,a.provid,a.houseid,b.wareid,case d.pricetype when 0 then c.entersale else c.retailsale end as price0,d.discount,b.id";
		qry += "\n       FROM wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n       left outer join warecode c on b.wareid=c.wareid";
		qry += "\n       left outer join provide d on a.provid=d.provid";
		qry += "\n       where a.accid=" + Accid + " and a.statetag=1 ";
		qry += "\n       and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Iszero == 1)
			qry += "\n   and b.price=0";
		if (Provid > 0)
			qry += "\n    and a.provid=" + Provid;
		if (Houseid > 0)
			qry += "\n    and a.houseid=" + Houseid;
		if (Wareid > 0)
			qry += "\n   and b.wareid=" + Wareid;
		// if (colorid > 0) qry += " and b.colorid=" + colorid;
		// if (sizeid > 0) qry += " and b.sizeid=" + sizeid;
		if (Findbox.length() > 0)
			qry += " and (c.wareno like '%" + Findbox.toUpperCase() + "%' or c.warename like '%" + Findbox + "%')";
		if (Wareno.length() > 0)
			qry += "   and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "   and c.warename like '%" + Warename + "%'";
		// if (typeid>0 ) qry+=" and c.typeid="+typeid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "\n    and c.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "\n   and c.brandid=" + Brandid;
		if (Seasonname.length() > 0)
			qry += "\n   and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "\n   and c.prodyear='" + Prodyear + "'";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		}
		qry += "\n      order by a.provid,a.houseid,a.noteno; ";
		// qry += "\n v_row cur_data%rowtype; ";
		qry += "\n   begin";
		qry += "\n     v_count:=0; ";
		qry += "\n     open cur_data; ";
		qry += "\n     fetch cur_data  into v_noteno,v_notedate,v_provid,v_houseid,v_wareid,v_price0,v_discount,v_id;   ";
		qry += "\n     while cur_data%found loop  ";
		qry += "\n        v_minnotedate:=sysdate;";
		qry += "\n        v_provid1:=v_provid; ";
		qry += "\n        v_houseid1:=v_houseid; ";
		qry += "\n        while cur_data%found and v_provid1=v_provid and v_houseid1=v_houseid loop  ";
		qry += "\n           if v_minnotedate>v_notedate then v_minnotedate:=v_notedate; end if ;";
		qry += "\n           v_noteno1:=v_noteno; ";
		qry += "\n           while cur_data%found and v_noteno1=v_noteno and v_provid1=v_provid and v_houseid1=v_houseid loop  ";
		qry += "\n              v_price:=round(v_price0*v_discount," + Priceprec + "); ";
		qry += "\n              update wareinm set price0=v_price0,discount=v_discount,price=v_price,curr=amount*v_price where id=v_id and accid=" + Accid + " and noteno=v_noteno1; ";
		qry += "\n              v_count:=v_count+1; ";
		qry += "\n              fetch cur_data  into v_noteno,v_notedate,v_provid,v_houseid,v_wareid,v_price0,v_discount,v_id;   ";
		qry += "\n           end loop;  ";
		qry += "\n           select  nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(amount*price1),0) into v_totalamt,v_totalcurr,v_totalcheckcurr from wareinm where accid=" + Accid + " and noteno=v_noteno1; ";
		qry += "\n           update wareinh set totalcurr=v_totalcurr,totalcheckcurr=v_totalcheckcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno=v_noteno1; ";
		qry += "\n           v_count:=v_count+1; ";
		qry += "\n        end loop;  ";
		qry += "\n        if v_count>5000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit;  ";
		qry += "\n        end if;  ";
		// 调存储过程计算应付款余额
		// qry += "\n p_totalprovcurr(to_char(v_minnotedate,'yyyy-mm-dd')," + accid + ",v_provid,v_houseid);";

		qry += "\n     end loop;  ";

		qry += "\n     close cur_data; ";
		qry += "\n     v_notedatestr:=to_char(v_minnotedate,'yyyy-mm-dd');";
		qry += "\n     update accreg set calcdate=trunc(to_date(v_notedatestr,'yyyy-mm-dd')-1)  where accid=" + Accid + " and calcdate>=to_date(v_notedatestr,'yyyy-mm-dd');";
		qry += "\n     commit;  ";
		qry += "\n   end; ";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 更改采购明细商品价格
	public int Setprice() {
		if (Id == 0) {
			errmess = "id不是一个有效值！";
			return 0;
		}
		if (Wareid == 0) {
			errmess = "Wareid不是一个有效值！";
			return 0;
		}
		if (Noteno.length() <= 0) {
			errmess = "noteno不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Mindate))
			Mindate = Nowdate;
		if (Func.isNull(Maxdate))
			Maxdate = Nowdate;
		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;

		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";
		String qry = "declare";
		qry += "\n     v_calcdate varchar2(10);";
		qry += "\n     v_noteno varchar2(20);";
		qry += "\n     v_noteno1 varchar2(20);";
		qry += "\n     v_notedatestr varchar2(10);";
		qry += "\n     v_notedate date;";
		qry += "\n     v_minnotedate date;";
		qry += "\n     v_wareid number;";
		qry += "\n     v_price0 number(16,4);";
		qry += "\n     v_discount number(10,2);";
		qry += "\n     v_price number(16,4);";
		qry += "\n     v_id number;";
		qry += "\n     v_totalcurr number;";
		qry += "\n     v_totalcheckcurr number;";
		qry += "\n     v_totalamt number;";
		qry += "\n     v_provid number;";
		qry += "\n     v_houseid number;";
		qry += "\n     v_provid1 number;";
		qry += "\n     v_houseid1 number;";
		qry += "\n     v_count number;";
		qry += "\n begin ";
		qry += "\n    v_calcdate:='" + Calcdate + "';";
		if (Tkbj == 0) // 只更改当前款价格
		{
			qry += "\n     update wareinm set curr=round(amount*" + Price + "," + Priceprec + "), price=" + Price + ",discount=" + Discount + " where id=" + Id //
					+ "  and wareid=" + Wareid + " and accid=" + Accid + " and noteno='" + Noteno + "';";
			qry += "\n     select  nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(amount*price1),0) into v_totalamt,v_totalcurr,v_totalcheckcurr from wareinm where accid=" + Accid + " and noteno='" + Noteno + "'; ";
			qry += "\n     update wareinh set totalcurr=v_totalcurr,totalcheckcurr=v_totalcheckcurr,totalamt=v_totalamt where accid=" + Accid + " and noteno='" + Noteno + "'; ";
			qry += "\n   select to_char(notedate,'yyyy-mm-dd') into v_notedatestr from wareinh where accid=" + Accid + " and noteno='" + Noteno + "'; ";
			// 调存储过程计算应付款余额
			// qry += "\n select notedate,provid,houseid into v_minnotedate,v_provid,v_houseid from wareinh where accid=" + accid + " and noteno='" + noteno + "'; ";
			// qry += "\n p_totalprovcurr(to_char(v_minnotedate,'yyyy-mm-dd')," + accid + ",v_provid,v_houseid);";

		} else // tkbj=1,要更新同款商品价格
		{
			qry += "\n   v_notedate:=sysdate; ";
			qry += "\n   v_minnotedate:=sysdate;";
			qry += "\n   declare cursor cur_warein is ";
			qry += "\n       select a.noteno,a.notedate,a.provid,a.houseid,b.wareid,b.id";
			qry += "\n       FROM wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno";
			qry += "\n       left outer join warecode c on b.wareid=c.wareid";
			qry += "\n       left outer join provide d on a.provid=d.provid";
			qry += "\n       where a.accid=" + Accid + " and a.statetag=1 ";
			qry += "\n       and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
			qry += "\n       and b.wareid=" + Wareid;
			qry += "\n      order by a.provid,a.houseid,a.noteno; ";
			qry += "\n   begin";
			qry += "\n     v_count:=0;";
			qry += "\n     open cur_warein; ";
			qry += "\n     fetch cur_warein  into v_noteno,v_notedate,v_provid,v_houseid,v_wareid,v_id;   ";
			qry += "\n     while cur_warein%found loop  ";
			// qry += "\n v_minnotedate:=sysdate;";
			qry += "\n        v_provid1:=v_provid; ";
			qry += "\n        v_houseid1:=v_houseid; ";
			qry += "\n        while cur_warein%found and v_provid1=v_provid and v_houseid1=v_houseid loop  ";
			qry += "\n           if v_minnotedate>v_notedate then v_minnotedate:=v_notedate; end if ; ";
			qry += "\n           v_noteno1:=v_noteno; ";
			qry += "\n           while cur_warein%found and v_noteno1=v_noteno and v_provid1=v_provid and v_houseid1=v_houseid loop  ";
			// qry += "\n update wareinm set price0=v_price0,discount=v_discount,price=v_price,curr=amount*v_price where id=v_id; ";
			qry += "\n              update wareinm set curr=round(amount*" + Price + "," + Priceprec + "), price=" + Price + ",discount=" + Discount + " where id=v_id and accid=" + Accid + " and noteno=v_noteno;";
			qry += "\n              v_count:=v_count+1;";
			qry += "\n              if v_count>1000 then ";
			qry += "\n                 v_count:=0;";
			qry += "\n                 commit;";
			qry += "\n              end if;";
			qry += "\n              fetch cur_warein  into v_noteno,v_notedate,v_provid,v_houseid,v_wareid,v_id;   ";
			qry += "\n           end loop;  ";
			qry += "\n           select  nvl(sum(amount),0),nvl(sum(curr),0),nvl(sum(amount*price1),0) into v_totalamt,v_totalcurr,v_totalcheckcurr from wareinm where accid=" + Accid + " and noteno=v_noteno1; ";
			qry += "\n           update wareinh set totalcurr=v_totalcurr,totalamt=v_totalamt,totalcheckcurr=v_totalcheckcurr where accid=" + Accid + " and noteno=v_noteno1; ";
			qry += "\n        end loop;  ";
			qry += "\n        commit;  ";
			// 调存储过程计算应付款余额
			// qry += " p_totalprovcurr(to_char(v_minnotedate,'yyyy-mm-dd')," + accid + ",v_provid,v_houseid);";
			qry += "\n     end loop;  ";
			qry += "\n     close cur_warein; ";
			qry += "\n   end;";
			qry += "\n   v_notedatestr:=to_char(v_minnotedate,'yyyy-mm-dd');";

		}
		// qry += "\n if v_notedatestr<=v_calcdate then";
		qry += "\n   update accreg set calcdate=trunc(to_date(v_notedatestr,'yyyy-mm-dd')-1)  where accid=" + Accid + " and calcdate>=to_date(v_notedatestr,'yyyy-mm-dd');";
		// qry += "\n update accreg set calcdate=trunc(v_notedate-1) where accid=" + Accid + " and calcdate>=trunc(v_notedate);";

		// qry += "\n end if;";
		if (Zlbj == 1)
			qry += "\n   update warecode set entersale=" + Price + " where wareid=" + Wareid + " and accid=" + Accid + "; "; // 更改商品资料价格
		qry += "\n    commit;  ";
		qry += "\n end;";
		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 获取分页明细数据
	public Table GetTable(QueryParam qp) {

		if (Func.isNull(Mindate))
			Mindate = Nowdate;
		if (Func.isNull(Maxdate))
			Maxdate = Nowdate;
		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate;
		if (Mindate.length() <= 10)
			Mindate += " 00:00:00";
		if (Maxdate.length() <= 10)
			Maxdate += " 23:59:59";
		// if (Maxdate.compareTo(Currdatetime) > 0)
		// Maxdate = Currdatetime;

		String qry = " select a.notedate,a.noteno,a.provid,f.provname,b.wareid,c.wareno,c.warename,c.units,b.colorid,d.colorname,b.sizeid,e.sizename,b.amount,b.price0,b.discount,b.price,b.curr,b.id,b.price1,c.entersale,a.remark,b.remark0";
		qry += "  ,case a.ntid when 0 then 'CG' else 'CT' end as notetype,g.housename,b.amount*b.price1 as checkcurr,h.areaname";
		qry += "\n FROM wareinh a join wareinm b on a.accid=b.accid and a.noteno=b.noteno";
		qry += "\n left outer join warecode c on b.wareid=c.wareid";
		qry += "\n left outer join colorcode d on b.colorid=d.colorid";
		qry += "\n left outer join sizecode e on b.sizeid=e.sizeid";
		qry += "\n left outer join provide f on a.provid=f.provid";
		qry += "\n left outer join warehouse g on a.houseid=g.houseid";
		qry += "\n left outer join area h on c.areaid=h.areaid";
		qry += "\n where a.accid=" + Accid + " and a.statetag=1";
		qry += "\n and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Iszero == 1)
			qry += " and b.price=0";
		if (Findbox.length() > 0)
			qry += " and (c.wareno like '%" + Findbox.toUpperCase() + "%' or c.warename like '%" + Findbox + "%')";

		if (Provid > 0)
			qry += "    and a.provid=" + Provid;
		if (Houseid > 0)
			qry += "    and a.houseid=" + Houseid;
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and c.typeid=" + Typeid;
			}
		if (Brandid >= 0)
			qry += "   and c.brandid=" + Brandid;

		// if (!Houseidlist.equals("")) {
		// qry += " and " + Func.getCondition("a.houseid", Houseidlist);
		// }
		// if (!Providlist.equals("")) {
		// qry += " and " + Func.getCondition("a.provid", Providlist);
		// }
		// if (!Brandidlist.equals("")) {
		// qry += " and " + Func.getCondition("c.brandid", Brandidlist);
		// }
		// if (!Typeidlist.equals("")) {
		// qry += " and " + Func.getCondition("c.typeid", Typeidlist);
		// }

		if (Wareid > 0)
			qry += "   and b.wareid=" + Wareid;
		if (Areaid > 0)
			qry += "   and c.areaid=" + Areaid;
		// if (sizeid > 0) qry += " and b.sizeid=" + sizeid;
		if (Wareno.length() > 0)
			qry += "   and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		if (Warename.length() > 0)
			qry += "   and c.warename like '%" + Warename + "%'";
		// if (typeid>0 ) qry+=" and c.typeid="+typeid;
		if (Seasonname.length() > 0)
			qry += "   and c.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "   and c.prodyear='" + Prodyear + "'";
		if (Qxbj == 1) // 1 启用权限控制
		{
			qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " ) ";
			qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") ) ";
		}

		qp.setQueryString(qry);
		return DbHelperSQL.GetTable(qp);
	}

}