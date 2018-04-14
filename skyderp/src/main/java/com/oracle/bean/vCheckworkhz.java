package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-25 21:40:04
//*************************************
public class vCheckworkhz implements java.io.Serializable {

	private Long Userid = (long) 0;

	private Long Accid = (long) 0;
	private Long Houseid = (long) 0;
	private String Mindate = "";
	private String Maxdate = "";

	private String Findbox = "";

	private String errmess;

	private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}

	public void setAccid(Long accid) {
		Accid = accid;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	public int doTotal() {
		if (Mindate.equals("") || Maxdate.equals("")) {
			errmess = "日期无效！";
			return 0;
		}
		if (Mindate.compareTo(Accbegindate) < 0)
			Mindate = Accbegindate;

		String qry = "\n    declare  ";
		qry += "\n       cursor mycursor is ";
		qry += "         select ROWID from v_checkworkhz_data where userid=" + Userid;
		qry += "\n       order by rowid; ";
		qry += "\n       type rowid_table_type is  table  of rowid index by pls_integer; ";
		qry += "\n       v_rowid   rowid_table_type; ";
		qry += "\n    BEGIN ";
		qry += "\n       open mycursor; ";
		qry += "\n       loop ";
		qry += "\n          fetch  mycursor bulk collect into v_rowid  limit 1000;  ";// --------每次处理5000行，也就是每5000行一提交
		qry += "\n          exit when v_rowid.count=0; ";
		qry += "\n          forall i in v_rowid.first..v_rowid.last ";
		qry += "\n            delete from v_checkworkhz_data where rowid=v_rowid(i); ";
		qry += "\n          commit; ";
		qry += "\n        end loop; ";
		qry += "\n       close mycursor; ";
		qry += "\n    END; ";

		qry += "\n    insert into v_checkworkhz_data (id,userid,epid,epname,checkbj,num)";
		qry += "\n    select v_checkworkhz_data_id.nextval," + Userid + ",epid,epname,checkbj,num from (";
		// --迟到
		qry += "\n    select epid,epname,checkbj,count(num) as num from (";
		qry += "\n    select a.epid,b.epname,1 as checkbj,count(*) as num ";
		qry += "\n    from checkwork a join employe b on a.epid=b.epid";
		qry += "\n    join warehouse c on b.houseid=c.houseid";
		qry += "\n    where a.checkid=0 and a.uptag0=1 and c.accid=" + Accid;
		qry += "\n    and a.classday>='" + Mindate + "' and a.classday<='" + Maxdate + "'";
		if (Houseid > 0)
			qry += "\n    and b.houseid=" + Houseid;
		if (!Findbox.equals(""))
			qry += "\n    and b.epname like '%" + Findbox + "%' ";

		qry += "\n    group by a.epid,b.epname";
		// --早退
		qry += "\n    union all";
		qry += "\n    select a.epid,b.epname,2 as checkbj,count(*) as num ";
		qry += "\n    from checkwork a join employe b on a.epid=b.epid";
		qry += "\n    join warehouse c on b.houseid=c.houseid";
		qry += "\n    where a.checkid=0 and a.uptag1=1 and c.accid=" + Accid;
		qry += "\n    and a.classday>='" + Mindate + "' and a.classday<='" + Maxdate + "'";
		if (Houseid > 0)
			qry += "\n    and b.houseid=" + Houseid;
		if (!Findbox.equals(""))
			qry += "\n    and b.epname like '%" + Findbox + "%' ";
		qry += "\n    group by a.epid,b.epname";
		// --病事假
		qry += "\n    union all";
		qry += "\n    select a.epid,b.epname,checkid+2 as checkbj,count(*) as num ";
		qry += "\n    from checkwork a join employe b on a.epid=b.epid";
		qry += "\n    join warehouse c on b.houseid=c.houseid";
		qry += "\n    where a.checkid>0 and c.accid=" + Accid;
		qry += "\n    and a.classday>='" + Mindate + "' and a.classday<='" + Maxdate + "'";
		if (Houseid > 0)
			qry += "\n    and b.houseid=" + Houseid;
		if (!Findbox.equals(""))
			qry += "\n    and b.epname like '%" + Findbox + "%' ";
		qry += "\n    group by a.epid,b.epname,a.checkid";
		qry += "\n    ) group by epid,epname,checkbj";
		qry += "\n    );";
		qry += "\n end;";

		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	public Table GetTable(QueryParam qp) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select b.houseid,c.housename,a.epid,a.epname");
		strSql.append(" ,sum(case checkbj when 1 then num else 0 end) as zt1");
		strSql.append(" ,sum(case checkbj when 2 then num else 0 end) as zt2");
		strSql.append(" ,sum(case checkbj when 3 then num else 0 end) as zt3");
		strSql.append(" ,sum(case checkbj when 4 then num else 0 end) as zt4");
		strSql.append(" ,sum(case checkbj when 5 then num else 0 end) as zt5");
		strSql.append(" ,sum(case checkbj when 5 then num else 0 end) as zt6");
		strSql.append(" from v_checkworkhz_data a ");
		strSql.append("  join employe b on a.epid=b.epid");
		strSql.append("  join warehouse c on b.houseid=c.houseid");
		strSql.append("  where a.userid=" + Userid);
		strSql.append("  group by b.houseid,c.housename,a.epid,a.epname");

		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

}