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
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-04-07 11:39:49
//*************************************
public class Sysprog implements java.io.Serializable {
	private Long Progid;
	private String Progno;
	private String Progname;
	private Long Groupid;
	private Integer Noused;
	private Long Sysid;
	private Long Ordid;
	private Integer Tag;
	private Integer Nofree;
	private Long Grpid;
	private Long Indexid;
	private Long Ptag;
	private Long Htag;
	private String Grpname;
	private String errmess;
	private String Lastop;
	private Date Lastdate;

	private String Csxxx;
	// XXXXXXXXXX
	// |||||_5 |||_10迷你
	// ||||_4pc ||_9批发
	// |||__3ipd |_8零售
	// ||___2苹果
	// |____1安卓

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setProgid(Long progid) {
		this.Progid = progid;
	}

	public Long getProgid() {
		return Progid;
	}

	public void setProgno(String progno) {
		this.Progno = progno;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getProgno() {
		return Progno;
	}

	public void setProgname(String progname) {
		this.Progname = progname;
	}

	public String getProgname() {
		return Progname;
	}

	public void setGroupid(Long groupid) {
		this.Groupid = groupid;
	}

	public Long getGroupid() {
		return Groupid;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setSysid(Long sysid) {
		this.Sysid = sysid;
	}

	public Long getSysid() {
		return Sysid;
	}

	public void setOrdid(Long ordid) {
		this.Ordid = ordid;
	}

	public Long getOrdid() {
		return Ordid;
	}

	public void setTag(Integer tag) {
		this.Tag = tag;
	}

	public Integer getTag() {
		return Tag;
	}

	public void setNofree(Integer nofree) {
		this.Nofree = nofree;
	}

	public Integer getNofree() {
		return Nofree;
	}

	public void setGrpid(Long grpid) {
		this.Grpid = grpid;
	}

	public Long getGrpid() {
		return Grpid;
	}

	public void setIndexid(Long indexid) {
		this.Indexid = indexid;
	}

	public Long getIndexid() {
		return Indexid;
	}

	public void setPtag(Long ptag) {
		this.Ptag = ptag;
	}

	public Long getPtag() {
		return Ptag;
	}

	public void setHtag(Long htag) {
		this.Htag = htag;
	}

	public Long getHtag() {
		return Htag;
	}

	public void setGrpname(String grpname) {
		this.Grpname = grpname;
	}

	public String getGrpname() {
		return Grpname;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		if (Progid == 0) {
			errmess = "progid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin");
		strSql.append("   delete from Sysprog  where progid=" + Progid + ";");
		strSql.append("   delete from roleprog  where progid=" + Progid + ";");
		strSql.append(" end;");
		//	System.out.println(strSql.toString());
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 增加记录
	public int Append() {
		if (Progid == null || Progid == 0) {
			errmess = "progid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		// strSql1.append("progid,");
		// strSql2.append("v_id,");
		// if (Progid != null) {
		strSql1.append("progid,");
		strSql2.append(Progid + ",");
		//		strSql2.append("v_progid,");
		// }
		if (Progno != null) {
			strSql1.append("progno,");
			strSql2.append("'" + Progno + "',");
		}
		if (Progname != null) {
			strSql1.append("progname,");
			strSql2.append("'" + Progname + "',");
		}
		if (Csxxx != null) {
			strSql1.append("Csxxx,");
			strSql2.append("'" + Csxxx + "',");
		}
		if (Groupid != null) {
			strSql1.append("groupid,");
			strSql2.append(Groupid + ",");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Sysid != null) {
			strSql1.append("sysid,");
			strSql2.append(Sysid + ",");
		}
		if (Ordid != null) {
			strSql1.append("ordid,");
			strSql2.append(Ordid + ",");
		}
		if (Tag != null) {
			strSql1.append("tag,");
			strSql2.append(Tag + ",");
		}
		if (Nofree != null) {
			strSql1.append("nofree,");
			strSql2.append(Nofree + ",");
		}
		if (Grpid != null) {
			strSql1.append("grpid,");
			strSql2.append(Grpid + ",");
		}
		if (Indexid != null) {
			strSql1.append("indexid,");
			strSql2.append(Indexid + ",");
		}
		if (Ptag != null) {
			strSql1.append("ptag,");
			strSql2.append(Ptag + ",");
		}
		if (Htag != null) {
			strSql1.append("htag,");
			strSql2.append(Htag + ",");
		}
		if (Grpname != null) {
			strSql1.append("grpname,");
			strSql2.append("'" + Grpname + "',");
		}
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from Sysprog where progid=" + Progid + "; ");
		strSql.append("\n   if v_count>0 then ");
		strSql.append("\n      v_retcs:='0程序id已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		//		strSql.append("\n   v_progid:=Sysprog_progid.nextval;");
		strSql.append("\n   insert into Sysprog (" + strSql1.toString() + ")");
		strSql.append("\n   values (" + strSql2.toString() + ");");
		strSql.append("\n   v_retcs:='1操作成功！';");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));
		// int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		// if (ret < 0) {
		// errmess = "操作异常！";
		// return 0;
		// } else {
		// errmess = "操作成功！";
		// return 1;
		// }
	}

	// 修改记录
	public int Update() {
		if (Progid == null || Progid == 0) {
			errmess = "progid不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update Sysprog set ");
		// if (Progid != null) {
		// strSql.append("progid=" + Progid + ",");
		// }
		if (Progno != null) {
			strSql.append("progno='" + Progno + "',");
		}
		if (Progname != null) {
			strSql.append("progname='" + Progname + "',");
		}
		if (Groupid != null) {
			strSql.append("groupid=" + Groupid + ",");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Sysid != null) {
			strSql.append("sysid=" + Sysid + ",");
		}
		if (Ordid != null) {
			strSql.append("ordid=" + Ordid + ",");
		}
		if (Tag != null) {
			strSql.append("tag=" + Tag + ",");
		}
		if (Nofree != null) {
			strSql.append("nofree=" + Nofree + ",");
		}
		if (Grpid != null) {
			strSql.append("grpid=" + Grpid + ",");
		}
		if (Indexid != null) {
			strSql.append("indexid=" + Indexid + ",");
		}
		if (Ptag != null) {
			strSql.append("ptag=" + Ptag + ",");
		}
		if (Htag != null) {
			strSql.append("htag=" + Htag + ",");
		}
		if (Grpname != null) {
			strSql.append("grpname='" + Grpname + "',");
		}
		if (Csxxx != null) {
			strSql.append("Csxxx='" + Csxxx + "',");
		}
		strSql.append("lastop='" + Lastop + "',");
		strSql.append("lastdate=sysdate");
		strSql.append("\n  where progid=" + Progid + ";");
		strSql.append("\n  commit;");
		strSql.append("\n end;");
		
//		System.out.println(strSql.toString());
	
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
		strSql.append(" FROM Sysprog ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Sysprog ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTableIPD(long userid, int vertag, String devicetype) {


		String qry = "select a.progid,a.progname";
		qry += " from sysprog a ";
		qry += "\n join roleprog c on a.progid=c.progid ";
		qry += "\n join employe d on c.levelid=d.levelid ";
		qry += "\n where d.epid=" + userid + " and a.noused=0 and c.fs=0 ";
//		qry += " and a.tag=0";
		if (Sysid >= 0)
			qry += " and a.sysid=" + Sysid;
		// sysprog->ptag:运行设备
		// 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
		// --sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣,11=上下衣供应商

		//sysprog->csxxx
		//XXXXXXXXXXX
		//||||    |||__10迷你
		//||||    ||___9批发
		//||||    |____8零售
		//||||_________4PC
		//|||__________3IPAD
		//||___________2苹果
		//|____________1安卓

//		if (fs == 0) // ipad专用
//		{
			//			qry += "\n and (a.ptag=0 or a.ptag=3 or a.ptag=5 or a.ptag=6)";
			qry += " and substr(a.csxxx,3,1)='1' ";
//		} else if (fs == 1) // pc专用
//		{
//			//			qry += "\n and (a.ptag=0 or a.ptag=1 or a.ptag=4 or a.ptag=6)";
//			qry += " and substr(a.csxxx,4,1)='1' ";
//		}

		if (vertag == 0)
			qry += " and substr(a.csxxx,8,1)='1' ";
		else if (vertag == 1)
			qry += " and substr(a.csxxx,9,1)='1' ";
		else if (vertag == 2)
			qry += " and (substr(a.csxxx,8,1)='1' or substr(a.csxxx,9,1)='1' )";

		else if (vertag == 3) //迷你
			qry += " and substr(a.csxxx,10,1)='1' ";

		// qry += " and a.progid<>1708 and a.progid<>1709 and a.progid<>1509 ";
		qry += "\n order by a.ordid,a.progid";
//	 System.out.println(qry);
		return DbHelperSQL.GetTable(qry);
	}

	
	// 获取分页数据
	public Table GetTable(long userid, int fs,  String devicetype) {


		String qry = "select a.progid,a.progname";
		qry += " from sysprog a ";
		qry += "\n join roleprog c on a.progid=c.progid ";
		qry += "\n join employe d on c.levelid=d.levelid ";
		qry += "\n where d.epid=" + userid + " and a.noused=0 and c.fs=0 ";
		if (Sysid >= 0)
			qry += " and a.sysid=" + Sysid;
		// sysprog->ptag:运行设备
		// 0=手机+pc端+ipad，1=pc端专用,2=手机，3=ipad,4=手机+pc,5=手机+ipad，6=pc+ipad
		// --sysprog->htag:程序属性0=通用(蓝窗),1=云壹，10=上下衣,11=上下衣供应商
		if (fs == 0) // ipad专用
		{
			qry += "\n and (a.ptag=0 or a.ptag=3 or a.ptag=5 or a.ptag=6)";
		} else if (fs == 1) // pc专用
		{
			qry += "\n and (a.ptag=0 or a.ptag=1 or a.ptag=4 or a.ptag=6)";
		}
		qry += " and a.htag<10";
		// qry += " and a.progid<>1708 and a.progid<>1709 and a.progid<>1509 ";
		qry += "\n order by a.ordid,a.progid";
		// System.out.println(qry);
		return DbHelperSQL.GetTable(qry);
	}

	// 获取所有数据
	public Table GetTable(String qry) {
		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Sysprog");
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
}