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
//  @author:sunhong  @date:2017-03-15 22:19:56
//*************************************
public class Viphousenotice implements java.io.Serializable {
	private Long Hnid;
	private Date Notedate;
	private Integer Statetag;
	private Long Onhouseid;
	private String Topical;
	private String Content;
	private String Checkman;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setHnid(Long hnid) {
		this.Hnid = hnid;
	}

	public Long getHnid() {
		return Hnid;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
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

	public void setCheckman(String checkman) {
		this.Checkman = checkman;
	}

	public String getCheckman() {
		return Checkman;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
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
		strSql.append(" update viphousenotice set statetag=2 where hnid=" + Hnid);

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
		if (Onhouseid == null || Onhouseid == 0) {
			errmess = "缺少线上店铺id参数！";
			return 0;
		}
		if (Topical == null || Topical.equals("")) {
			errmess = "公告主题不允许为空！";
			return 0;
		}
		if (Content == null || Content.equals("")) {
			errmess = "公告内容不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("hnid,");
		strSql2.append("v_hnid,");
		// if (Hnid != null) {
		// strSql1.append("hnid,");
		// strSql2.append(Hnid + ",");
		// }
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		// if (Statetag != null) {
		strSql1.append("statetag,");
		strSql2.append("1,");
		// }
		// if (Onhouseid != null) {
		strSql1.append("onhouseid,");
		strSql2.append(Onhouseid + ",");
		// }
		if (Topical != null) {
			strSql1.append("topical,");
			strSql2.append("'" + Topical + "',");
		}
		if (Content != null) {
			strSql1.append("content,");
			strSql2.append("'" + Content + "',");
		}
		if (Checkman != null) {
			strSql1.append("checkman,");
			strSql2.append("'" + Checkman + "',");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_hnid number; ");
		strSql.append(" begin ");
		strSql.append("   v_hnid:=Viphousenotice_hnid.nextval; ");
		strSql.append("   insert into Viphousenotice (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		// strSql.append(" select v_id into :id from dual; ");
		strSql.append("   select v_hnid,onhousename into :hnid,:onhousename from onlinehouse where onhouseid=" + Onhouseid + "; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("hnid", new ProdParam(Types.BIGINT));
		param.put("onhousename", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = param.get("hnid").getParamvalue().toString();
			String onhousename = param.get("onhousename").getParamvalue().toString();
			// PushToVip("", "viptag" + Onhouseid,Topical+":"+ Content + "【" + Onhousename+"】",Onhouseid,0);

			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Viphousenotice set ");
		if (Hnid != null) {
			strSql.append("hnid=" + Hnid + ",");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Topical != null) {
			strSql.append("topical='" + Topical + "',");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Checkman != null) {
			strSql.append("checkman='" + Checkman + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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
		strSql.append(" FROM Viphousenotice ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Viphousenotice a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Viphousenotice");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "???:" + Brandname + " 已存在，请重新输入！";
		// return true;
		// }
		return false;
	}
}