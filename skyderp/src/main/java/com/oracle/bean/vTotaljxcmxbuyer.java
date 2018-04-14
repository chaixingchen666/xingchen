/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.comdot.data.Table;
import com.common.tool.Func;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

/**
 * @author Administrator 销售趋势分析
 *
 */
public class vTotaljxcmxbuyer {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
	private Long Accid = (long) 0;
	private Long Buyeraccid = (long) 0;
	private String Currdatetime = "";

	private String Notedate = "";
	private Long Userid = (long) 0;
	private Long Wareid = (long) 0;
	private Long Colorid = (long) 0;
	private Long Sizeid = (long) 0;
	// private Long Dptid = (long) -1;
	// private Long Areaid = (long) -1;
	// private Long Brandid = (long) -1;
	// private Long Saleid = (long) -1;
	private Long Typeid = (long) -1;
	// private Long Provid = (long) -1;
	private String Seasonname = "";
	private String Warename = "";
	private String Wareno = "";
	private String Prodyear = "";

	private String Mindate = "";
	private String Maxdate = "";

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

	public void setServerdatetime(String serverdatetime) {
		Serverdatetime = serverdatetime;
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
		// Date Nowday = Func.StrToDate(Nowdate);
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}

		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		// String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		String mindate0 = Func.DateToStr(Func.getPrevDay(Mindate));
		Maxdate += " 23:59:59";
		Mindate += " 00:00:00";
		if (Currdatetime.length() <= 0)
			Currdatetime = Serverdatetime;
		if (Maxdate.compareTo(Currdatetime) > 0)
			Maxdate = Currdatetime;

		
		
		
		String qry = "declare ";
		// qry += "\n v_epid number; ";
		// qry += "\n v_accid number; ";
		qry += "\n    v_mindate varchar2(10); ";
		qry += "\n    v_maxdate varchar2(10); ";
		qry += "\n    v_mindate0 varchar2(10); ";
		qry += "\n    v_bj  number(1); ";
		qry += "\n    v_amount2 number; ";
		qry += "\n    v_count  number; ";
		qry += "\n begin ";
		// qry += " delete from v_cxjxcmx_data where epid=" + userid + "; ";

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

		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,noteno,notetype,wlremark,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select ' ' as notedate,cast('期初' as nvarchar2(100)) as remark,cast(' ' as nvarchar2(20)) as noteno,'','',0 as amount0,0 as curr0,0 as amount1,0 as curr1,sum(a.amount) as amount2 ";
		qry += "\n    from waresum a ";
		qry += "\n    left outer join warecode b on a.wareid=b.wareid ";
		// qry += "\n left outer join warecode f on b.accid1=f.accid and b.wareid1=f.wareid";
		qry += "\n    where a.daystr='" + mindate0 + "' ";
		if (Buyeraccid > 0)
			qry += "\n    and b.accid=" + Buyeraccid;
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=b.wareid1 and a1.accid=b.accid1  ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";
		qry += "  );";

		// --采购入库
		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,accid,noteno,wlremark,notetype,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购' as nvarchar2(100)),a.accid,a.noteno,e.provname||'->'||d.housename,'CG',sum(b.amount),sum(b.curr),0,0,0 ";
		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		// qry += "\n left outer join warecode f on c.accid1=e.accid and c.wareid1=e.wareid";
		qry += "\n    where a.statetag=1";// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";

		qry += "\n    and a.ntid=0 ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Buyeraccid > 0)
			qry += "\n    and a.accid=" + Buyeraccid;// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=c.wareid1 and a1.accid=c.accid1    ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";
		qry += "  )";
		qry += "\n    group by a.notedate,a.accid,a.noteno,d.housename,e.provname||'->'||d.housename; ";

		// --采购退库
		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,accid,noteno,wlremark,notetype,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('采购退货' as nvarchar2(100)),a.accid,a.noteno,d.housename||'->'||e.provname ,'CT',-sum(b.amount),-sum(b.curr),0,0,0 ";
		qry += "\n    from wareinh a  ";
		qry += "\n    join wareinm b on a.accid=b.accid and a.noteno=b.noteno ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join provide e on a.provid=e.provid ";
		// qry += "\n left outer join warecode f on c.accid1=f.accid and c.wareid1=f.wareid";
		qry += "\n    where a.statetag=1 ";
		qry += "\n    and a.ntid=1";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Buyeraccid > 0)
			qry += "\n    and a.accid=" + Buyeraccid;// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=c.wareid1 and a1.accid=c.accid1    ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";

		qry += "  )";
		qry += "\n    group by a.notedate,a.accid,a.noteno,d.housename||'->'||e.provname; ";

		// --零销售
		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,accid,noteno,wlremark,notetype,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('零售' as nvarchar2(100)),a.accid,a.noteno,d.housename,'XX',0,0,sum(b.amount),sum(b.curr),0 ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		// qry += "\n left outer join warecode f on c.accid1=f.accid and c.wareid1=f.wareid";
		// qry += "\n left outer join customer e on a.custid=e.custid ";
		qry += "\n    where a.statetag=1 ";// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";

		qry += "\n    and a.ntid=0 ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Buyeraccid > 0)
			qry += "\n    and a.accid=" + Buyeraccid;// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=c.wareid1 and a1.accid=c.accid1    ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";
		qry += "  )";
		qry += "\n    group by a.notedate,a.accid,a.noteno,d.housename; ";
		// 批发
		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,accid,noteno,wlremark,notetype,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发' as nvarchar2(100)),a.accid,a.noteno,d.housename||'->'||e.custname,'XS',0,0,sum(b.amount),sum(b.curr),0 ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		// qry += "\n left outer join warecode f on c.accid1=f.accid and c.wareid1=f.wareid";
		qry += "\n    where a.statetag=1 ";// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.ntid=1 ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Buyeraccid > 0)
			qry += "\n    and a.accid=" + Buyeraccid;// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=c.wareid1 and a1.accid=c.accid1    ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";
		qry += "  )";
		qry += "\n    group by a.notedate,a.accid,a.noteno,d.housename||'->'||e.custname; ";
		// 批发退货
		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,accid,noteno,wlremark,notetype,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('批发退货' as nvarchar2(100)),a.accid,a.noteno,e.custname||'->'||d.housename,'XT',0,0,-sum(b.amount),-sum(b.curr),0 ";
		qry += "\n    from wareouth a  ";
		qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		qry += "\n    left outer join warehouse d on a.houseid=d.houseid ";
		qry += "\n    left outer join customer e on a.custid=e.custid ";
		// qry += "\n left outer join warecode f on c.accid1=f.accid and c.wareid1=f.wareid";
		qry += "\n    where a.statetag=1 ";// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";

		qry += "\n    and a.ntid=2  ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Buyeraccid > 0)
			qry += "\n    and a.accid=" + Buyeraccid;// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=c.wareid1 and a1.accid=c.accid1    ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";
		qry += "  )";
		qry += "\n    group by a.notedate,a.accid,a.noteno,e.custname||'->'||d.housename; ";

		// --商场销售
		qry += "\n    insert into v_cxjxcmx_temp (notedate,remark,accid,noteno,wlremark,notetype,amount0,curr0,amount1,curr1,amount2)  ";
		qry += "\n    select to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss'),cast('商场销售' as nvarchar2(100)),a.accid,a.noteno,f_shopsalehouselist(a.accid,a.noteno) as wlremark,'XC',0,0,sum(b.amount),sum(b.curr),0 ";
		qry += "\n    from shopsaleh a  ";
		qry += "\n    join shopsalem b on a.accid=b.accid and a.noteno=b.noteno  ";
		qry += "\n    left outer join warecode c on b.wareid=c.wareid ";
		// qry += "\n left outer join warecode f on c.accid1=f.accid and c.wareid1=f.wareid";
		qry += "\n    where a.statetag=1 ";// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		// qry+=" and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
		qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";
		if (Buyeraccid > 0)
			qry += "\n    and a.accid=" + Buyeraccid;// and to_char(a.notedate,'yyyy-mm-dd') >='"+mindate+"' ";
		qry += "\n    and exists (select 1 from warecode a1 where a1.accid=" + Accid + " and a1.wareid=c.wareid1 and a1.accid=c.accid1    ";
		if (Wareid > 0)
			qry += "      and a1.wareid=" + Wareid;
		else {
			if (Wareno.length() > 0)
				qry += "      and a1.wareno like '%" + Wareno.toUpperCase() + "%'";
			if (Warename.length() > 0)
				qry += "      and a1.warename like '%" + Warename + "%'";
		}
		if (Typeid >= 0)
			if (Typeid > 0 && Typeid < 1000) {
				qry += "    and exists (select 1 from waretype t where t.typeid=a1.typeid and t.p_typeid= " + Typeid + ")";
			} else {
				qry += "    and a1.typeid=" + Typeid;
			}
		if (Seasonname.length() > 0)
			qry += "      and a1.seasonname='" + Seasonname + "'";
		if (Prodyear.length() > 0)
			qry += "      and a1.prodyear='" + Prodyear + "'";
		qry += "  )";
		qry += "\n    group by a.notedate,a.accid,a.noteno,f_shopsalehouselist(a.accid,a.noteno); ";// f_shopsalehouselist(a.accid,a.noteno)

		qry += "\n    insert into v_cxjxcmx_data (id,epid,notedate,accid,noteno,notetype,remark,wlremark,amount0,curr0,amount1,curr1,amount2) ";
		qry += "\n    (select v_cxjxcmx_data_id.nextval," + Userid + ", notedate,accid,noteno,notetype,remark,wlremark,nvl(amount0,0),nvl(curr0,0),nvl(amount1,0),nvl(curr1,0),nvl(amount2,0) from v_cxjxcmx_temp); ";
		// --计算余额
		qry += "\n    declare cursor cur_v_cxjxcmx_data is select id,amount0,amount1,amount2 from v_cxjxcmx_data where epid=" + Userid + " order by notedate,id;  ";
		// qry += " for update nowait; ";
		qry += "\n      v_row cur_v_cxjxcmx_data%rowtype; ";
		qry += "\n    begin ";
		qry += "\n      v_amount2:=0; ";
		qry += "\n      v_bj:=0; v_count:=0; ";
		qry += "\n      for v_row in cur_v_cxjxcmx_data loop ";
		qry += "\n        if v_bj=0 then ";
		qry += "\n          v_amount2:=v_row.amount2; ";
		qry += "\n          v_bj:=1; ";
		qry += "\n        else ";
		qry += "\n          v_amount2:=v_amount2+v_row.amount0-v_row.amount1; ";
		qry += "\n          update v_cxjxcmx_data set amount2=v_amount2 where id=v_row.id; ";
		qry += "\n        end if; ";
		qry += "\n        if v_count>5000 then ";
		qry += "\n           v_count:=0; ";
		qry += "\n           commit; ";
		qry += "\n        end if; ";
		qry += "\n      end loop; ";

		qry += "\n    end; ";
		// --v_balamt:=v_amount2;
		qry += "\n    commit;";

		qry += "\n    select nvl(sum(a.amount0),0),nvl(sum(a.curr0),0),nvl(sum(a.amount1),0),nvl(sum(a.curr1),0),v_amount2";
		qry += "\n    into :totalamt0,:totalcurr0,:totalamt1,:totalcurr1,:balamt from v_cxjxcmx_data a  where a.epid=" + Userid + "; ";
		qry += "\n    end;";

		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt0", new ProdParam(Types.FLOAT));
		param.put("totalcurr0", new ProdParam(Types.FLOAT));
		param.put("totalamt1", new ProdParam(Types.FLOAT));
		param.put("totalcurr1", new ProdParam(Types.FLOAT));
		param.put("balamt", new ProdParam(Types.FLOAT));
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
				+ ",\"TOTALCURR0\":\"" + param.get("totalcurr0").getParamvalue() + "\"" //
				+ ",\"TOTALAMT1\":\"" + param.get("totalamt1").getParamvalue() + "\"" //
				+ ",\"TOTALCURR1\":\"" + param.get("totalcurr1").getParamvalue() + "\"" //
				+ ",\"BALAMT\":\"" + param.get("balamt").getParamvalue() + "\"" //
				+ ",\"CURRDATETIME\":\"" + Maxdate + "\""

				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM v_cxjxcmx_data a ");
		strSql.append("\n join ACCCONNECT b on a.accid=b.buyaccid");
		strSql.append("\n left outer join customer c on b.sellcustid=c.custid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

}
