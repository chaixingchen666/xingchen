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
//  @author:sunhong  @date:2017-03-08 19:37:59
//*************************************
public class Sysnotice implements java.io.Serializable {
	private Float Ggid;
	private Date Notedate;
	private String Topical;
	private String Content;
	private String Operant;
	private String Checkman;
	private Integer Statetag;
	private Date Lastdate;
	private Integer Lytag;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setGgid(Float ggid) {
		this.Ggid = ggid;
	}

	public Float getGgid() {
		return Ggid;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setTopical(String topical) {
		this.Topical = topical;
	}

	public String getTopical() {
		return Topical;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getContent() {
		return Content;
	}

	public void setOperant(String operant) {
		this.Operant = operant;
	}

	public String getOperant() {
		return Operant;
	}

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setLytag(Integer lytag) {
		this.Lytag = lytag;
	}

	public Integer getLytag() {
		return Lytag;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }

	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		strSql.append(")");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前记录已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		// strSql.append(" update Sysnotice set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		if (Topical == null || Topical.equals("")) {
			errmess = "公告主题未设定！";
			return 0;
		}
		if (Content == null || Content.equals("")) {
			errmess = "公告内容不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("ggid,");
		strSql2.append("v_id,");
		// if (Ggid != null) {
		// strSql1.append("ggid,");
		// strSql2.append("'" + Ggid + "',");
		// }
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		// if (Topical != null) {
		strSql1.append("topical,");
		strSql2.append("'" + Topical + "',");
		// }
		// if (Content != null) {
		strSql1.append("content,");
		strSql2.append("'" + Content + "',");
		// }
		// if (Operant != null) {
		strSql1.append("operant,");
		strSql2.append("'" + Operant + "',");
		// }
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append(Statetag + ",");
		// }
		if (Lytag != null) {
			strSql1.append("lytag,");
			strSql2.append(Lytag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");

		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n begin ");
		strSql.append("\n   v_id:=Sysnotice_ggid.nextval; ");
		strSql.append("\n   insert into Sysnotice (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append("\n values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("\n  select v_id into :id from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Ggid = Float.parseFloat(param.get("id").getParamvalue().toString());
			errmess = "" + Ggid;
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Ggid == null || Ggid == 0) {
			errmess = "公告id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Sysnotice set ");
		// if (Notedate != null) {
		// strSql.append("notedate='" + Notedate + "',");
		// }
		if (Topical != null) {
			strSql.append("topical='" + Topical + "',");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lytag != null) {
			strSql.append("lytag=" + Lytag + ",");
		}
		// if (Operant != null) {
		strSql.append("operant='" + Operant + "',");
		// }
		strSql.append("lastdate=sysdate");
		strSql.append("  where ggid=" + Ggid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Sysnotice ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Sysnotice ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist, long epid) {
		StringBuilder strSql = new StringBuilder();
		if (epid == 0) {
			strSql.append("select  " + fieldlist + ",'0' as rdbj");
			strSql.append(" FROM sysnotice a");
			if (!strWhere.equals("")) {
				strSql.append(" where " + strWhere);
			}
		} else {
			strSql.append("select " + fieldlist + ",'1' as rdbj from sysnotice a");
			strSql.append(" where exists (select 1 from sysreadnotice b where a.ggid=b.ggid and b.epid=" + epid + ")");
			if (!strWhere.equals("")) {
				strSql.append(" and " + strWhere);
			}

			strSql.append(" union all");
			strSql.append(" select " + fieldlist + ",'0' as rdbj from sysnotice a");
			strSql.append(" where not exists (select 1 from sysreadnotice b where a.ggid=b.ggid and b.epid=" + epid + ")");
			if (!strWhere.equals("")) {
				strSql.append(" and " + strWhere);
			}
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Sysnotice");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}
}