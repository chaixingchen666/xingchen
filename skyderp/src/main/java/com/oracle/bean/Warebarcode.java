package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Types;
import java.util.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
//import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-22 11:08:33
//*************************************
public class Warebarcode implements java.io.Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -1306461226979016089L;
	// public class Warebarcode {
	private Double Id;
	private Long Userid;
	private Long Accid;
	private String Barcode;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private String Lastop;
	private Integer Statetag;
	private Integer Prtok;
	// private Date Lastdate;
	private Integer Ywly;
	private String Barremark;
	private String errmess;
	private String Prodyear = "";
	private String Wareno = "";
	private String Colorno = "";
	private String Colorname = "";
	private String Sizename = "";
	private String Seasonname = "";
	private String Sizegroupno = "";
	private Long Typeid = (long) -1;
	private Long Brandid = (long) -1;
	private Integer Newbj = 0;// newbj:1=仅产生无条码商品
	private String Calcdate = ""; // 账套开始使用日期

	public void setCalcdate(String calcdate) {
		Calcdate = calcdate;
	}

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Double id) {
		this.Id = id;
	}

	public Double getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setBarcode(String barcode) {
		this.Barcode = barcode;
	}

	public void setWareno(String wareno) {
		this.Wareno = wareno;
	}

	public String getBarcode() {
		return Barcode;
	}

	public void setWareid(Long wareid) {
		this.Wareid = wareid;
	}

	public Long getWareid() {
		return Wareid;
	}

	public void setColorid(Long colorid) {
		this.Colorid = colorid;
	}

	public Long getColorid() {
		return Colorid;
	}

	public void setSizeid(Long sizeid) {
		this.Sizeid = sizeid;
	}

	public Long getSizeid() {
		return Sizeid;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public void setYwly(Integer ywly) {
		this.Ywly = ywly;
	}

	public Integer getYwly() {
		return Ywly;
	}

	public void setBarremark(String barremark) {
		this.Barremark = barremark;
	}

	public String getBarremark() {
		return Barremark;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		if (Id <= 0 && Barcode.length() <= 0) {
			errmess = "id或barcode不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append(" delete from  Warebarcode ");
		strSql.append(" where accid=" + Accid);
		if (Id > 0)
			strSql.append(" and id=" + Id);
		if (Barcode.length() > 0)
			strSql.append(" and Barcode='" + Barcode + "'");
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
		if (Barcode == null || Barcode.equals("")) {
			errmess = "条码无效！";
			return 0;
		}
		if (Wareid == null || Colorid == null || Sizeid == null || Wareid == 0 || Colorid == 0 || Sizeid == 0) {
			errmess = "商品或颜色或尺码无效";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");

		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		// if (Barcode != null) {
		strSql1.append("barcode,");
		strSql2.append("'" + Barcode + "',");
		// }
		// if (Wareid != null) {
		strSql1.append("wareid,");
		strSql2.append(Wareid + ",");
		// }
		// if (Colorid != null) {
		strSql1.append("colorid,");
		strSql2.append(Colorid + ",");
		// }
		// if (Sizeid != null) {
		strSql1.append("sizeid,");
		strSql2.append(Sizeid + ",");
		// }
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Prtok != null) {
			strSql1.append("Prtok,");
			strSql2.append(Prtok + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Ywly != null) {
			strSql1.append("ywly,");
			strSql2.append(Ywly + ",");
		}
		if (Barremark != null) {
			strSql1.append("barremark,");
			strSql2.append("'" + Barremark + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_count number; ");
		strSql.append("\n   v_retcs varchar2(200); ");
		strSql.append("\n begin ");
		strSql.append("\n   select count(*) into v_count from warecode where accid=" + Accid + " and statetag=1 and wareid=" + Wareid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0商品无效！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   select count(*) into v_count from colorcode where accid=" + Accid + " and statetag=1 and Colorid=" + Colorid + ";");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0颜色无效！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   select count(*) into v_count from sizecode a join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno ");
		strSql.append("\n   where a.accid=" + Accid + " and b.wareid=" + Wareid + " and a.sizeid=" + Sizeid + " and a.statetag=1;");
		strSql.append("\n   if v_count=0 then");
		strSql.append("\n      v_retcs:='0尺码无效！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   select count(*) into v_count from warebarcode");
		strSql.append("\n   where barcode='" + Barcode + "' and accid=" + Accid + " and rownum=1;");
		strSql.append("\n   if v_count>0 then");
		strSql.append("\n      v_retcs:='0条码已存在！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");

		strSql.append("\n   v_id:=Warebarcode_id.nextval; ");
		strSql.append("\n   insert into Warebarcode (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n   v_retcs:='1'||v_id ; ");
		strSql.append("\n   <<exit>>");
		strSql.append("\n   select v_retcs into :retcs from dual;");
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
	}

	// 修改记录
	public int Update() {
		if (Id == null || Id == 0) {
			errmess = "id不是一个有效值！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_count number;");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin ");
		if (Barcode != null) {
			if (Barcode.length() <= 0) {
				errmess = "barcode不是一个有效值！";
				return 0;
			}
			strSql.append("\n   select count(*) into v_count from warebarcode");
			strSql.append("\n   where barcode='" + Barcode + "' and accid=" + Accid + " and id<>" + Id + ";");
			strSql.append("\n   if v_count>0 then ");
			strSql.append("\n      v_retcs:='0条码已存在！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Wareid != null) {

			strSql.append("\n   select count(*) into v_count from warecode where accid=" + Accid + " and wareid=" + Wareid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0商品无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Colorid != null) {

			strSql.append("\n   select count(*) into v_count from colorcode where accid=" + Accid + " and Colorid=" + Colorid + " and statetag=1;");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0颜色无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}
		if (Sizeid != null) {
			if (Wareid == null) {
				errmess = "wareid不是一个有效值！";
				return 0;
			}
			strSql.append("\n   select count(*) into v_count from sizecode a join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno");
			strSql.append("\n   where a.accid=" + Accid + " and b.wareid=" + Wareid + " and a.sizeid=" + Sizeid + ";");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      v_retcs:='0尺码无效！';");
			strSql.append("\n      goto exit;");
			strSql.append("\n   end if;");
		}

		strSql.append("\n    update Warebarcode set ");
		if (Barcode != null) {
			strSql.append("barcode='" + Barcode + "',");
		}
		if (Wareid != null) {
			strSql.append("wareid=" + Wareid + ",");
		}
		if (Colorid != null) {
			strSql.append("colorid=" + Colorid + ",");
		}
		if (Sizeid != null) {
			strSql.append("sizeid=" + Sizeid + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Prtok != null) {
			strSql.append("Prtok=" + Prtok + ",");
		}
		if (Ywly != null) {
			strSql.append("ywly=" + Ywly + ",");
		}
		if (Barremark != null) {
			strSql.append("barremark='" + Barremark + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and accid=" + Accid + ";");
		strSql.append("\n  commit;");
		strSql.append("\n  v_retcs:='1操作成功！';");
		strSql.append("\n  <<exit>>");
		strSql.append("\n  select v_retcs into :retcs from dual;");
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
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append("\n FROM Warebarcode a");
		strSql.append("\n left outer join warecode b on a.wareid=b.wareid");
		strSql.append("\n left outer join colorcode c on a.colorid=c.colorid");
		strSql.append("\n left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append("\n left outer join brand e on b.brandid=e.brandid");

		if (!strWhere.equals("")) {
			strSql.append("\n where " + strWhere);
		}
		System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表(订货会专用)
	public DataSet GetList(String strWhere, String fieldlist, long omid) {
		StringBuilder strSql = new StringBuilder();
		// System.out.println("aaaaaa");

		strSql.append("select " + fieldlist);
		strSql.append(" FROM Warebarcode a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" join omwarecode e on a.wareid=e.wareid and e.omid=" + omid);

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		// System.out.println(strSql.toString());
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warebarcode a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" left outer join brand e on b.brandid=e.brandid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public String GetTable2Excel(QueryParam qp, String strWhere, String fieldlist, JSONObject jsonObject) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM warebarcode a");
		strSql.append(" left outer join warecode b on a.wareid=b.wareid");
		strSql.append(" left outer join colorcode c on a.colorid=c.colorid");
		strSql.append(" left outer join sizecode d on a.sizeid=d.sizeid");
		strSql.append(" left outer join brand e on b.brandid=e.brandid");

		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		qp.setPageIndex(1);
		qp.setPageSize(100);
		return DbHelperSQL.Table2Excel(qp, jsonObject);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Warebarcode");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1
		// and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}

	// 自动产生条码
	public int doAutobarcode(int barfs) {
		if (barfs == 0) {
			errmess = "系统未设置自动产生条码方式，请在系统参数中设置！";
			return 0;
		}
		//
		// 1=货号+色码+尺码代号
		// 2=货号+色码+尺码名称
		// 3=厂家编码+色码+尺码代号
		// 4=厂家编码+色码+尺码名称
		// 5=货号+尺码代号+色码
		// 6=货号+尺码名称+色码
		// 7=厂家编码+尺码代号+色码
		// 8=厂家编码+尺码名称+色码
		// 9=8位自动序号
		String qry = "declare ";
		qry += "\n    v_barcode varchar2(30);";
		qry += "\n    v_count number(10);";
		qry += "\n    v_count1 number(10);";
		qry += "\n    v_accid number(10); ";
		qry += "\n begin ";
		// qry += " v_accid:=" + accid + "; ";
		// qry += " v_fs:=" + fs + "; ";
		qry += "\n    v_accid:=" + Accid + "; ";

		if (barfs == 9) // 8位自动序号条码
		{
			qry += "\n    declare cursor cur_tempdata is  ";
			qry += "\n      select a.wareno,a.prodno,d.colorno,b.sizeno,b.sizename,a.wareid,c.colorid,b.sizeid   ";
			qry += "\n      from warecode a  ";
			qry += "\n      join sizecode b on a.sizegroupno=b.groupno and a.accid=b.accid  ";
			qry += "\n      join warecolor c on a.wareid=c.wareid  ";
			qry += "\n      join colorcode d on c.colorid=d.colorid  ";
			qry += "\n      where a.statetag=1 and b.statetag=1 and d.statetag=1 and a.accid=v_accid ";
			qry += "\n      and not exists (select 1 from warenosize a1 where a.wareid=a1.wareid and b.sizeid=a1.sizeid) ";
			// if (Newbj == 1) // 无条码的商品产生条码
			// {
			qry += "\n      and not exists (select 1 from warebarcode a2 where a2.wareid=a.wareid and a2.colorid=c.colorid and a2.sizeid=b.sizeid and a2.accid=v_accid) ";
			// }

			if (Wareid != null && Wareid > 0) {
				qry += "\n and a.wareid=" + Wareid;
			}
			if (Typeid != null && Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=a.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and a.typeid=" + Typeid;
				}

			if (Brandid != null && Brandid >= 0) {
				qry += " and a.brandid=" + Brandid;
			}
			if (!Func.isNull(Wareno)) {
				qry += " and a.wareno like '" + Wareno + "%'";
			}

			if (!Func.isNull(Prodyear)) {
				qry += " and a.prodyear='" + Prodyear + "'";
			}
			if (!Func.isNull(Seasonname)) {
				qry += " and a.seasonname='" + Seasonname + "'";
			}
			if (!Func.isNull(Sizegroupno)) {
				qry += " and a.sizegroupno='" + Sizegroupno + "'";
			}
			qry += "\n      order by a.wareid,c.colorid,b.sizeid;";

			qry += "\n       v_row cur_tempdata%rowtype; ";
			qry += "\n   begin ";
			qry += "\n      v_count1:=0;";
			qry += "\n      for v_row in cur_tempdata loop ";
			qry += "\n          insert into warebarcode (id,accid,barcode,wareid,colorid,sizeid,statetag,lastop,lastdate) ";
			qry += "\n          values (warebarcode_id.nextval,v_accid,f_getbarcode(v_accid),v_row.wareid,v_row.colorid,v_row.sizeid,1,'" + Lastop + "',sysdate); ";
			qry += "\n          v_count1:=v_count1+1;";
			qry += "\n          if v_count1>=1000 then ";
			qry += "\n             v_count1:=0;";
			qry += "\n             commit;";
			qry += "\n          end if;";
			qry += "\n      end loop; ";
			qry += "\n   end;  ";
		} else {
			qry += "\n    declare cursor cur_tempdata is  ";
			qry += "\n      select a.wareno,a.prodno,d.colorno,b.sizeno,b.sizename,a.wareid,c.colorid,b.sizeid   ";
			qry += "\n      from warecode a  ";
			qry += "\n      join sizecode b on a.sizegroupno=b.groupno and a.accid=b.accid  ";
			qry += "\n      join warecolor c on a.wareid=c.wareid  ";
			qry += "\n      join colorcode d on c.colorid=d.colorid  ";
			qry += "\n      where a.statetag=1 and b.statetag=1 and d.statetag=1 and a.accid=v_accid ";
			qry += "\n      and not exists (select 1 from warenosize a1 where a.wareid=a1.wareid and b.sizeid=a1.sizeid) ";
			if (Newbj == 1) // 无条码的商品产生条码
			{
				qry += "\n      and not exists (select 1 from warebarcode a2 where a2.wareid=a.wareid and a2.colorid=c.colorid and a2.accid=v_accid) ";
			}
			if (barfs == 1 || barfs == 2 || barfs == 5 || barfs == 6)
				qry += "\n      and length(a.wareno)>0 ";// and
														// length(d.colorno)>0
															// and
															// length(b.sizeno)>0
			else
				qry += "\n      and length(a.prodno)>0 ";

			if (Wareid != null && Wareid > 0) {
				qry += "\n and a.wareid=" + Wareid;
			}
			if (Typeid != null && Typeid >= 0)
				if (Typeid > 0 && Typeid < 1000) {
					qry += "\n    and exists (select 1 from waretype t where t.typeid=a.typeid and t.p_typeid= " + Typeid + ")";
				} else {
					qry += "\n    and a.typeid=" + Typeid;
				}

			if (Brandid != null && Brandid >= 0) {
				qry += " and a.brandid=" + Brandid;
			}
			if (!Func.isNull(Wareno)) {
				qry += " and a.wareno like '" + Wareno + "%'";
			}

			if (!Func.isNull(Prodyear)) {
				qry += " and a.prodyear='" + Prodyear + "'";
			}
			if (!Func.isNull(Seasonname)) {
				qry += " and a.seasonname='" + Seasonname + "'";
			}
			if (!Func.isNull(Sizegroupno)) {
				qry += " and a.sizegroupno='" + Sizegroupno + "'";
			}
			qry += "\n      order by a.wareid,c.colorid,b.sizeid;";

			qry += "\n       v_row cur_tempdata%rowtype; ";
			qry += "\n   begin ";
			qry += "\n      v_count1:=0;";
			qry += "\n      for v_row in cur_tempdata loop ";
			/*
			 * fs 1=货号+色码+尺码代号 2=货号+色码+尺码名称 3=厂家编码+色码+尺码代号 4=厂家编码+色码+尺码名称
			 * 5=货号+尺码代号+色码 6=货号+尺码名称+色码 7=厂家编码+尺码代号+色码 8=厂家编码+尺码名称+色码
			 */
			if (barfs == 1)
				qry += "\n         v_barcode:=v_row.wareno||v_row.colorno||v_row.sizeno; ";
			else if (barfs == 2)
				qry += "\n         v_barcode:=v_row.wareno||v_row.colorno||v_row.sizename; ";
			else if (barfs == 3)
				qry += "\n         v_barcode:=v_row.prodno||v_row.colorno||v_row.sizeno; ";
			else if (barfs == 4)
				qry += "\n         v_barcode:=v_row.prodno||v_row.colorno||v_row.sizename; ";
			else if (barfs == 5)
				qry += "\n         v_barcode:=v_row.wareno||v_row.sizeno||v_row.colorno; ";
			else if (barfs == 6)
				qry += "\n         v_barcode:=v_row.wareno||v_row.sizename||v_row.colorno; ";
			else if (barfs == 7)
				qry += "\n         v_barcode:=v_row.prodno||v_row.sizeno||v_row.colorno; ";
			else if (barfs == 8)
				qry += "\n         v_barcode:=v_row.prodno||v_row.sizename||v_row.colorno; ";

			qry += "\n         select count(*) into v_count from warebarcode where barcode=v_barcode and accid=v_accid; ";
			qry += "\n         if v_count=0 then  ";
			qry += "\n            insert into warebarcode (id,accid,barcode,wareid,colorid,sizeid,statetag,prtok,lastop,lastdate) ";
			qry += "\n            values (warebarcode_id.nextval,v_accid,v_barcode,v_row.wareid,v_row.colorid,v_row.sizeid,1,1,'" + Lastop + "',sysdate); ";
			qry += "\n            v_count1:=v_count1+1;";
			qry += "\n         else ";
			qry += "\n            update warebarcode set wareid=v_row.wareid,colorid=v_row.colorid,sizeid=v_row.sizeid where barcode=v_barcode and accid=v_accid;";
			qry += "\n         end if; ";
			qry += "\n         if v_count1>=1000 then ";
			qry += "\n            v_count1:=0;";
			qry += "\n            commit;";
			qry += "\n         end if;";
			qry += "\n     end loop; ";
			qry += "\n   end;  ";
		}
		qry += "\n end;  ";
		int ret = DbHelperSQL.ExecuteSql(qry);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 从excel中导入条码
	public int LoadFromXLS() {

		if (Barcode == null || Barcode.equals("")) {
			errmess = "条码参数无效！";
			return 0;
		}
		if (Wareno == null || Wareno.equals("")) {
			errmess = "货号参数无效！";
			return 0;
		}
		if ((Colorno == null || Colorno.equals("")) && (Colorname == null || Colorname.equals(""))) {
			errmess = "颜色参数无效！";
			return 0;
		}
		if (Sizename == null || Sizename.equals("")) {
			errmess = "尺码参数无效！";
			return 0;
		}
		//		if (Sizename == null || Sizename.equals("") || Sizegroupno == null || Sizegroupno.equals("")) {
		//			errmess = "尺码参数无效！";
		//			return 0;
		//		}

		String qry = "declare ";// "select wareid from warecode where accid=" +
								// accid + " and wareno='" + wareno + "' and
								// rownum=1";
		qry += "\n   v_id number;";
		qry += "\n   v_count number;";
		qry += "\n   v_wareid number;";
		qry += "\n   v_colorid number;";
		qry += "\n   v_sizeid number;";
		qry += "\n   v_sizegroupno varchar2(30);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n begin";

		// 商品
		qry += "\n  begin";
		qry += "\n    select wareid,sizegroupno into v_wareid,v_sizegroupno from warecode where accid=" + Accid + " and noused=0 and statetag=1 and wareno='" + Wareno + "' and rownum=1;";
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n    v_retcs:='0货号:" + Wareno + "未找到！';";
		qry += "\n    goto exit;";
		qry += "\n  end;";
		// 颜色
		if (Colorno != null && !Colorno.equals("")) {
			qry += "\n  begin";
			qry += "\n    select a.colorid into v_colorid from colorcode a join warecolor b on a.colorid=b.colorid";
			qry += "\n    where a.accid=" + Accid + " and b.wareid=v_wareid and a.noused=0 and a.statetag=1 and a.colorno='" + Colorno + "' and rownum=1;";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n    v_retcs:='0颜色代号:" + Colorno + "未找到！';";
			qry += "\n    goto exit;";
			qry += "\n  end;";
		} else {

			qry += "\n  begin";
			qry += "\n    select a.colorid into v_colorid from colorcode  a join warecolor b on a.colorid=b.colorid";
			qry += "\n    where a.accid=" + Accid + " and b.wareid=v_wareid and a.noused=0 and a.statetag=1 and a.Colorname='" + Colorname + "' and rownum=1;";
			qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
			qry += "\n    v_retcs:='0颜色名称:" + Colorname + "未找到！';";
			qry += "\n    goto exit;";
			qry += "\n  end;";
		}

		// 尺码
		qry += "\n  begin";
		qry += "\n    select sizeid into v_sizeid from sizecode where accid=" + Accid + " and statetag=1 and groupno=v_sizegroupno and sizename='" + Sizename + "' and rownum=1;";
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n    v_retcs:='0尺码:" + Sizename + "未找到！';";
		qry += "\n    goto exit;";
		qry += "\n  end;";
		qry += "\n  begin";
		qry += "\n    select id into v_id from warebarcode where barcode='" + Barcode + "' and accid=" + Accid + ";";
		qry += "\n    update warebarcode set lastdate=sysdate,lastop='" + Lastop + "'";
		qry += "\n    ,wareid=v_wareid,colorid=v_colorid,sizeid=v_sizeid";
		if (Prtok != null)
			qry += "\n  ,prtok=" + Prtok;
		qry += "\n    where id=v_id ;";
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n    insert into warebarcode (id,barcode,wareid,colorid,sizeid,accid,lastdate,statetag,prtok,lastop) ";
		qry += "\n    values (warebarcode_id.nextval,'" + Barcode + "',v_wareid,v_colorid,v_sizeid," + Accid + ",sysdate,1";
		if (Prtok != null)
			qry += "\n   ," + Prtok;
		else
			qry += "\n   ,1";
		qry += "\n    ,'" + Lastop + "') ;";

		qry += "\n  end;";

		qry += "\n   v_retcs:='1操作成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();

		errmess = retcs.substring(1);
		return Integer.parseInt(retcs.substring(0, 1));

	}

	// 删除选中的条码
	public int DelSelect(JSONObject jsonObject) {
		if (!jsonObject.has("reclist")) {
			errmess = "未传入要删除的条码记录！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		JSONArray jsonArray = jsonObject.getJSONArray("reclist");
		strSql.append("begin");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Id = Double.parseDouble(jsonObject2.getString("recid"));
			strSql.append("\n  delete from warebarcode where accid=" + Accid + " and id=" + Id + ";");
		}

		strSql.append(" end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	public int doBarcode(String noteno) {
		if (Barcode == null || Barcode.length() <= 0) {
			errmess = "barcode不是一个有效值！";
			return 0;
		}
		Barcode = Barcode.toUpperCase();
		String qry = "select A.BARCODE,A.WAREID,B.WARENO,B.WARENAME,B.UNITS,A.COLORID,C.COLORNAME,A.SIZEID,D.SIZENAME,B.ENTERSALE,B.RETAILSALE";
		qry += " FROM warebarcode a";
		qry += " left outer join warecode b on a.wareid=b.wareid";
		qry += " left outer join colorcode c on a.colorid=c.colorid";
		qry += " left outer join sizecode d on a.sizeid=d.sizeid";
		// qry += " join omwarecode e on a.wareid=e.wareid and e.omid=" + omid;
		qry += " where b.accid=" + Accid + " and a.barcode='" + Barcode + "' ";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {

			errmess = "未找到条码:" + Barcode + "！";
			return 0;
		}

		// String retcs = "\"WAREID\":\"" + dt.Rows[0]["wareid"].ToString() +
		// "\""//
		// + ",\"WARENO\":\"" + dt.Rows[0]["wareno"].ToString() + "\""
		// + ",\"WARENAME\":\"" + dt.Rows[0]["warename"].ToString() + "\""
		// + ",\"UNITS\":\"" + dt.Rows[0]["units"].ToString() + "\""
		// + ",\"COLORID\":\"" + dt.Rows[0]["colorid"].ToString() + "\""
		// + ",\"COLORNAME\":\"" + dt.Rows[0]["colorname"].ToString() + "\""
		// + ",\"SIZEID\":\"" + dt.Rows[0]["sizeid"].ToString() + "\""
		// + ",\"SIZENAME\":\"" + dt.Rows[0]["sizename"].ToString() + "\""
		// + ",\"RETAILSALE\":\"" + dt.Rows[0]["retailsale"].ToString() + "\""
		// ;
		String retcs = "\"msg\":\"操作成功！\"";
//		String fh = "";
		for (int j = 0; j < tb.getColumnCount(); j++) {//
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();//
			String strValue = tb.getRow(0).get(j).toString();//
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
//			fh = ",";//
		}

		long saleid = 0;
		String salename = "";
		if (noteno != null && noteno.length() > 0) {
			qry = "select a.saleid,b.salename from warepeim a join salecode b on a.saleid=b.saleid where a.accid=" + Accid + " and a.noteno='" + noteno + "' and rownum=1 order by a.id";
			tb = DbHelperSQL.Query(qry).getTable(1);
			if (tb.getRowCount() > 0) {
				saleid = Long.parseLong(tb.getRow(0).get("SALEID").toString());
				salename = tb.getRow(0).get("SALENAME").toString();
			}

		}
		retcs += ",\"SALEID\":\"" + saleid + "\",\"SALENAME\":\"" + salename + "\"";
		errmess = retcs;
		return 1;
	}

	// 同步商品条码(erp->app)
	public int Sync() {
		if (Func.isNull(Barcode)) {
			errmess = "barcode不是一个有效值！";
			return 0;
		}
		if (Wareid == null || Colorid == null || Sizeid == null || Wareid == 0 || Colorid == 0 || Sizeid == 0) {
			errmess = "wareid或colorid或sizeid不是一个有效值！";
			return 0;
		}
		if (Ywly == null)
			Ywly = 0;
		Barcode = Barcode.toUpperCase();
		String qry = "declare ";
		qry += "\n    v_id number(10); ";
		qry += "\n begin ";
		qry += "\n    select id into v_id from warebarcode where accid=" + Accid + " and barcode='" + Barcode + "'; ";
		qry += "\n    update warebarcode set wareid=" + Wareid + ",colorid=" + Colorid + ",sizeid=" + Sizeid + ",lastdate=sysdate,lastop='" + Lastop + "',ywly=" + Ywly + " where id=v_id; ";
		qry += "\n EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n    insert into warebarcode (id,accid,barcode,wareid,colorid,sizeid,statetag,ywly,lastop)";
		qry += "\n    values (warebarcode_id.nextval," + Accid + ",'" + Barcode + "'," + Wareid + "," + Colorid + "," + Sizeid + ",1," + Ywly + ",'" + Lastop + "'); ";
		qry += "\n end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 下载
	public int doDown(String downdate)// downdate="",同步所有数据，只同步更新的数据
	{
		StringBuilder sql = new StringBuilder();
		String headsql = "";
		boolean isupdate = false;
		String qry = "select barcode,wareid,colorid,sizeid,barremark,statetag,lastop,accid";
		qry += " from warebarcode where accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and statetag=1 ";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		// System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate) {
			sql.append("\n begin;");
			sql.append("\n delete from warebarcode where accid=" + Accid + ";");
			sql.append("\n commit;");
		}
		sql.append("\n begin;");
		for (int i = 0; i < tb.getRowCount(); i++) {
			String sql1 = "";// "accid";
			String sql2 = "";// Accid.toString();
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				// if (j > 0) {
				// sql1 += ",";
				// sql2 += ",";
				// }
				String fieldtype = tb.getMeta().get(j + 1).getType();

				String strKey = tb.getRow(i).getColumn(j + 1).getName();//
				String strValue = "";
				if (fieldtype.equals("NUMBER"))
					strValue = tb.getRow(i).get(j).toString();//
				else
					strValue = "'" + tb.getRow(i).get(j).toString() + "'";//
				sql1 += strKey + ",";
				sql2 += strValue + ",";

			}
			sql.append("\n " + headsql + " into warebarcode (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
			if (i > 0 && i % 100 == 0) {
				sql.append("\n commit;");
				sql.append("\n begin;");
			}
		}
		sql.append("\n commit;");
		String path = Func.getRootPath();
		String filename = "down" + Func.desEncode(Accid + "_" + Func.DateToStr(new Date(), "yyyyMMddHHmmss")).toLowerCase() + ".sql";// "brand.sql";
		String savefile = path + "/temp/" + filename;
		PrintStream printStream;
		try {
			printStream = new PrintStream(new FileOutputStream(savefile));
			printStream.println(sql.toString());
			printStream.close();
			errmess = "/temp/" + filename;
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmess = "操作失败：" + e.getMessage();
			return 0;
		}

	}

	// 首页商品扫码
	public int doFindWarecode(int qxbj) {
		if (Wareno.length() <= 0 && Barcode.length() <= 0) {
			errmess = "wareno或barcode不是一个有效值！";
			return 0;
		}
		String qry = "";
		qry = "select a.wareid,a.wareno,a.warename,a.units,a.model,a.retailsale,a.prodyear,a.seasonname,b.brandname,a.remark,a.imagename0";
		qry += "\n from warecode a ";
		qry += "\n left outer join brand b on a.brandid=b.brandid";
		qry += "\n where a.statetag=1 and a.accid=" + Accid + " and rownum=1 ";
		if (Barcode.length() > 0) {
			// qry += "\n and exists (select 1 from warebarcode a1 where
			// a1.wareid=a.wareid and a1.accid='" + Accid + " and a1.barcode='"
			// + Barcode + "')";
			qry += "\n and exists (select 1 from warebarcode a1 where a1.wareid=a.wareid and a1.accid=" + Accid + " and a1.barcode=substr('" + Barcode + "',1,length(a1.barcode)) and rownum=1 )";
			// barcode=substr('" + barcode + "',1,length(barcode))
		} else
			qry += " and a.wareno='" + Wareno + "'";
		//		System.out.println(qry);
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() <= 0) {
			errmess = "商品未找到！";
			return 0;
		}
		Wareid = Long.parseLong(tb.getRow(0).get("WAREID").toString());
		String retcs = "\"msg\":\"操作成功！\"";
		for (int j = 0; j < tb.getColumnCount(); j++) {
//			if (j > 0)
//				retcs += ",";
			String strKey = tb.getRow(0).getColumn(j + 1).getName().toUpperCase();
			String strValue = tb.getRow(0).get(j).toString();
			retcs += ",\"" + strKey + "\":\"" + strValue + "\"";
		}

		// 取颜色
		qry = "select a.colorid,b.colorname";
		qry += "\n  FROM warecolor a";
		qry += "\n  left outer join colorcode b on a.colorid=b.colorid";
		qry += "\n  where a.wareid=" + Wareid + " and b.statetag=1 and b.noused=0";
		qry += "\n  and a.noused=0 ";

		qry += "\n  order by b.colorno";
		// LogUtils.LogDebugWrite("wareinm", qry);
		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs += ",\"colorlist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"COLORID\":\"" + tb.getRow(i).get("COLORID").toString() + "\""//
					+ ",\"COLORNAME\":\"" + tb.getRow(i).get("COLORNAME").toString() + "\"}";

		}
		retcs += "]";

		// 取尺码
		qry = "select a.sizeid,a.sizename,a.sizeno  FROM sizecode a";
		qry += "\n  left outer join warecode b on a.accid=b.accid and a.groupno=b.sizegroupno";
		qry += "\n  where a.statetag=1 and a.noused=0 and a.accid=" + Accid + " and b.wareid=" + Wareid;
		qry += "\n  and not exists (select 1 from warenosize a1 where b.wareid=a1.wareid and a.sizeid=a1.sizeid)";
		qry += "\n  order by a.sizeno,a.sizename";

		tb = DbHelperSQL.Query(qry).getTable(1);

		retcs += ",\"sizelist\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			retcs += "{\"SIZEID\":\"" + tb.getRow(i).get("SIZEID").toString() + "\"" //
					+ ",\"SIZENAME\":\"" + tb.getRow(i).get("SIZENAME").toString() + "\"}";
		}
		retcs += "]";

		String houseidlist = "";

		// 查找店铺
		if (qxbj == 1) {//如果启用了权限控制，只允许查看授权的店铺
			qry = " select distinct houseid from employehouse where epid=" + Userid;
			tb = DbHelperSQL.Query(qry).getTable(1);
			for (int i = 0; i < tb.getRowCount(); i++) {
				if (i == 0)
					houseidlist = "^";
				houseidlist += tb.getRow(i).get("HOUSEID").toString() + "^";
			}
		}
		vTotalCxkczy kc = new vTotalCxkczy();
		// System.out.println("houseid="+houseidlist+" wareid="+Wareid);
		kc.setUserid(Userid);
		kc.setAccid(Accid);
		kc.setWareid(Wareid);
		kc.setCalcdate(Calcdate);
		kc.setHouseidlist(houseidlist);
		kc.doCxkczy();
		qry = " select a.houseid,b.housename,count(*) as num ";
		qry += "\n from v_cxkczy_data a ";
		qry += "\n left outer join warehouse b on a.houseid=b.houseid";
		qry += "\n where epid=" + Userid;
		qry += "\n group by a.houseid,b.housename";
		tb = DbHelperSQL.Query(qry).getTable(1);
		retcs += ",\"houselist\":[";
		for (int i = 0; i < tb.getRowCount(); i++) {
			long houseid = Long.parseLong(tb.getRow(i).get("HOUSEID").toString());
			if (i > 0)
				retcs += ",";
			retcs += "{\"HOUSEID\":\"" + houseid + "\"" //
					+ ",\"HOUSENAME\":\"" + tb.getRow(i).get("HOUSENAME").toString() + "\"";
			String qry1 = "select colorid,sizeid,amount from v_cxkczy_data where epid=" + Userid //
					+ " and houseid=" + houseid + " and wareid=" + Wareid;
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);

			// 查询库存
			retcs += ",\"amountlist\":[";
			for (int j = 0; j < tb1.getRowCount(); j++) {
				if (j > 0)
					retcs += ",";
				retcs += "{\"COLORID\":\"" + tb1.getRow(j).get("COLORID").toString() + "\"" //
						+ ",\"SIZEID\":\"" + tb1.getRow(j).get("SIZEID").toString() + "\"" //
						+ ",\"AMOUNT\":\"" + tb1.getRow(j).get("AMOUNT").toString() + "\"}";
			}

			retcs += "]}";
		}
		if (tb.getRowCount() > 0) {
			// 合计
			retcs += ",{\"HOUSEID\":\"0\"" //
					+ ",\"HOUSENAME\":\"合计\"";
			String qry1 = "select colorid,sizeid,sum(amount) as amount from v_cxkczy_data where epid=" + Userid + " and wareid=" + Wareid;
			qry1 += " group by colorid,sizeid";
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);

			// 查询库存
			retcs += ",\"amountlist\":[";
			for (int j = 0; j < tb1.getRowCount(); j++) {
				if (j > 0)
					retcs += ",";
				retcs += "{\"COLORID\":\"" + tb1.getRow(j).get("COLORID").toString() + "\"" //
						+ ",\"SIZEID\":\"" + tb1.getRow(j).get("SIZEID").toString() + "\"" //
						+ ",\"AMOUNT\":\"" + tb1.getRow(j).get("AMOUNT").toString() + "\"}";
			}

			retcs += "]}";
		}
		retcs += "]";

		errmess = retcs;
		return 1;
	}

	//清除异常条码
	public int Clearerr() {
		String qry = " begin ";
		qry += "\n   delete from warebarcode a where a.accid=" + Accid;
		qry += "\n   and (not exists (select 1  from warecode b where a.wareid=b.wareid and a.accid=b.accid)";
		qry += "\n   or  not exists (select 1  from colorcode c where a.colorid=c.colorid and a.accid=c.accid) ";
		qry += "\n   or not exists (select 1  from sizecode d where a.sizeid=d.sizeid and a.accid=d.accid));";
		qry += "\n  end;";

		if (DbHelperSQL.ExecuteSql(qry) < 0) {
			errmess = "操作失败！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}
}
