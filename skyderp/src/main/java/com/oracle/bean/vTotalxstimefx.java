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
public class vTotalxstimefx {
	private String Serverdatetime = "";// 服务器当前时间,-------------必传
	private String Nowdate = "";
	// private String Cxdatetime = ""; // 查询库存日期时间 格式 yyyy-MM-dd HH:mm:ss,------------必传
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
	// private Integer Hzfs = 0;// hzfs:0=品牌， 1=类型， 2=季节，3=店铺，4=客户，5=销售类型

	private String Mindate = "";
	private String Maxdate = "";
	private String Mintime = "";
	private String Maxtime = "";
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
		if (Mindate.length() < 0 || Maxdate.length() < 0) {
			errmess = "mindate或maxdate不是一个有效值！";
			return 0;
		}

		// 校验开始查询日期
		MinDateValid mdv = new MinDateValid(Mindate, Nowdate, Accbegindate, Maxday);
		Mindate = mdv.getMindate();
		String warning = mdv.getErrmess();
		if (Mindate.compareTo(Maxdate) > 0)
			Maxdate = Mindate; // 如果结束查询日期小于开始查询日期，则结束查询日期=开始查询日期
		Maxdate += " 23:59:59";
		Mindate += " 00:00:00";
        String qry = "declare ";
        qry += "     v_mindate  varchar2(10);  ";
        qry += "     v_maxdate  varchar2(10);  ";
        qry += "     v_mintime  varchar2(10);  ";
        qry += "     v_maxtime  varchar2(10);  ";

//        qry += "     v_wareid  number;  ";
//        qry += "     v_wareno  varchar2(20);  ";
//        qry += "     v_brandid  number;  ";
//        qry += "     v_typeid  number;  ";
//        qry += "     v_seasonname  varchar2(20);  ";
//        qry += "     v_prodyear  varchar2(4);  ";
//        qry += "     v_houseid  number;  ";
//        qry += "     v_custid  number;  ";
//        qry += "     v_ntid  number;  "; //      --业务类型  0=出货  1=退货  2=所有
        qry += "     v_i  number;  ";  //     
        qry += "     v_maxnum  number;  ";
        qry += "     v_count  number;  ";
//        qry += "     v_saleid  number;  ";    // --销售类型:0=零售, 其它=销售类型
        qry += "     v_totalamt number;  ";   //--14
        qry += "     v_totalcurr  number;  ";   //--15
        qry += "     v_timestr  varchar2(10);  ";
        qry += "begin  ";
        //qry += "    v_accid:=" + accid + "; ";
        //qry += "    v_epid:=" + userid + "; ";

        //qry += "    v_mindate:='" + mindate + "'; ";
        //qry += "    v_maxdate:='" + maxdate + "'; ";
        qry += "    v_mintime:='" + Mintime + "'; ";
        qry += "    v_maxtime:='" + Maxtime + "'; ";

        //qry += "    insert into v_xstimefx_temp (timestr,amount,curr) ";
        //qry += "    delete from v_xstimefx_data where epid=" + userid + "; ";

        qry += "\n    declare  ";
        qry += "\n       cursor mycursor is ";
        qry += "         select ROWID from v_xstimefx_data where epid=" + Userid;
        qry += "\n       order by rowid; ";
        qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
        qry += "\n       v_rowid   rowid_table_type; ";
        qry += "\n    BEGIN ";
        qry += "\n       open mycursor; ";
        qry += "\n       loop ";
        qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";//  --------每次处理5000行，也就是每5000行一提交
        qry += "\n          exit when v_rowid.count=0; ";
        qry += "\n          forall i in v_rowid.first..v_rowid.last ";
        qry += "\n          delete from v_xstimefx_data where rowid=v_rowid(i); ";
        qry += "\n          commit; ";
        qry += "\n        end loop; ";
        qry += "\n       close mycursor; ";
        qry += "\n    END; ";
        
        
        qry += "\n    insert into v_xstimefx_data (id,epid,timestr,amount,curr) ";
        qry += "\n    select v_xstimefx_data_id.nextval," + Userid + ",timestr,amount,curr from ( ";


        qry += "\n    select to_char(a.notedate,'hh24')||'h' as timestr,sum(case a.ntid when 2 then -b.amount else b.amount end) as amount,sum(case a.ntid when 2 then -b.curr else b.curr end) as curr  ";

        qry += "\n    from wareouth a   ";
        qry += "\n    join wareoutm b on a.accid=b.accid and a.noteno=b.noteno   ";
        qry += "\n    left outer join warecode c on b.wareid=c.wareid  ";
        qry += "\n    where  a.statetag=1  and a.accid=" + Accid + " ";
        if (Xsid == 0) { qry += "  and a.ntid=0"; }
        else //零售 
            if (Xsid == 1) { qry += "  and (a.ntid=1 or a.ntid=2)"; }  //批发
        if (Qxbj==1)   //1  启用权限控制
        {
            qry += "\n    and exists (select 1 from employehouse x1 where a.houseid=x1.houseid and x1.epid=" + Userid + " )  ";
            qry += "\n    and (c.brandid=0 or exists (select 1 from employebrand x2 where c.brandid=x2.brandid and x2.epid=" + Userid + ") )  ";
        }

        //                qry += "    and to_char(a.notedate,'yyyy-mm-dd') >=v_mindate ";
        //               qry += "    and to_char(a.notedate,'yyyy-mm-dd') <=v_maxdate ";
        qry += "\n    and a.notedate>=to_date('" + Mindate + "','yyyy-mm-dd hh24:mi:ss') and a.notedate<=to_date('" + Maxdate + "','yyyy-mm-dd hh24:mi:ss')";

        qry += "\n    and to_char(a.notedate,'hh24') >=v_mintime ";
        qry += "\n    and to_char(a.notedate,'hh24') <=v_maxtime ";
        if (Dptid >= 0) qry += "\n   and a.dptid=" + Dptid;
        if (Custid > 0) { qry += "  and a.custid=" + Custid; };
        if (Houseid > 0) { qry += "  and a.houseid=" + Houseid; };
        if (Wareid > 0) { qry += "  and b.wareid=" + Wareid; };
        if (Brandid >= 0) { qry += "  and c.brandid=" + Brandid; };
        //if (typeid > 0) { qry += "  and c.typeid=" + typeid; };
        if (Typeid >= 0)
            if (Typeid > 0 && Typeid < 1000)
            { qry += "\n    and exists (select 1 from waretype t where t.typeid=c.typeid and t.p_typeid= " + Typeid + ")"; }
            else
            { qry += "\n    and c.typeid=" + Typeid; }

		if (!Func.isNull(Wareno)) {
			qry += "\n  and c.wareno like '%" + Wareno.toUpperCase() + "%'";
		}
		if (!Func.isNull(Warename))
			qry += "\n   and c.warename like '%" + Warename + "%'";
		if (!Func.isNull(Seasonname)) {
			qry += "\n  and c.seasonname = '" + Seasonname + "'";
		}
		if (!Func.isNull(Prodyear)) {
			qry += "\n  and c.prodyear = '" + Prodyear + "'";
		}
	


        qry += "\n  group by  to_char(a.notedate,'hh24') );";

        //qry += " delete from v_xstimefx_data where epid="+userid+"; ";
        //qry += " insert into v_xstimefx_data (id,epid,timestr,amount,curr) ";
        //qry += " (select v_xstimefx_data_id.nextval,"+userid+",timestr,amount,curr from v_xstimefx_temp); ";

        qry += "\n   v_maxnum:=to_number(substr(v_maxtime,1,2)); ";
        qry += "\n   v_i:=to_number(substr(v_mintime,1,2));";
        qry += "\n   while v_i<=v_maxnum loop";
        qry += "\n     v_timestr:=trim(to_char(v_i,'00'))||'h'; ";
        qry += "\n     select count(*) into v_count from v_xstimefx_data where epid=" + Userid + " and timestr=v_timestr;";
        qry += "\n     if v_count=0 then";
        qry += "\n       insert into v_xstimefx_data (id,epid,timestr,amount,curr)";
        qry += "\n       values (v_xstimefx_data_id.nextval," + Userid + ",v_timestr,0,0);";
        qry += "\n     end if;";
        qry += "\n     v_i:=v_i+1;";
        qry += "\n   end loop;";


        qry += "\n  commit;   ";


        //qry += " select nvl(sum(amount),0),nvl(sum(curr),0) into :v_totalamt,:v_totalcurr from v_xszbfx_data where epid="+userid+";  ";

        qry += "\n   select sum(amount),sum(curr) into v_totalamt,v_totalcurr from v_xstimefx_data where epid=" +Userid + ";";
        qry += "\n   update v_xstimefx_data set rateamt=case v_totalamt when 0 then 0 else round(amount/v_totalamt*100,1) end";
        qry += "\n   ,ratecur=case v_totalcurr when 0 then 0 else round(curr/v_totalcurr*100,1) end  where epid=" + Userid + ";";
        qry += "\n   select nvl(v_totalamt,0),nvl(v_totalcurr,0) into :totalamt,:totalcurr from dual; ";

        qry += "\n end; ";

		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("totalamt", new ProdParam(Types.FLOAT));
		param.put("totalcurr", new ProdParam(Types.FLOAT));
		// 调用
		if (DbHelperSQL.ExecuteProc(qry, param) < 0) {
			errmess = "操作异常！";
			return 0;
		}
		// 取返回值
		// float amount = Float.parseFloat(param.get("amount").getParamvalue().toString());
		// float curr = Float.parseFloat(param.get("curr").getParamvalue().toString());
		// String Noteno = param.get("Noteno").getParamvalue().toString();
		errmess = "\"msg\":\"操作成功！\",\"totalamt\":\"" + param.get("totalamt").getParamvalue() + "\"" //
				+ ",\"totalcurr\":\"" + param.get("totalcurr").getParamvalue() + "\"" //
				+ ",\"warning\":\"" + mdv.getErrmess() + "\"";
		return 1;

	}
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM v_xstimefx_data a");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(strSql.toString());
	}
}
