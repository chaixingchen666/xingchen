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
//  @author:sunhong  @date:2017-03-25 11:32:29
//*************************************
public class Userback implements java.io.Serializable {
	private Float Id;
	private Long Epid;
	private Integer Tag;
	private String Content;
	private String Remark;
	private String Lastop;
	private Date Notedate;
	private Date Lastdate;
	private String Tel;
	private String Lx;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
	}

	public void setTag(Integer tag) {
		this.Tag = tag;
	}

	public Integer getTag() {
		return Tag;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getContent() {
		return Content;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setLx(String lx) {
		this.Lx = lx;
	}

	public String getLx() {
		return Lx;
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
		// strSql.append("select count(*) as num from (");
		// strSql.append("select wareid from warecode where id=" + id + " and rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update Userback set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		return 1;
		// }
	}

	// 增加记录
	public int Append() {

		if (Content == null || Content.equals("")) {
			errmess = "用户反馈内容不允许为空！";
			return 0;
		}
		if (Tel == null || Tel.equals("")) {
			errmess = "联系电话不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		// if (Id != null) {
		// strSql1.append("id,");
		// strSql2.append("'" + Id + "',");
		// }
		// if (Epid != null) {
		strSql1.append("epid,");
		strSql2.append(Epid + ",");
		// }
		if (Tag != null) {
			strSql1.append("tag,");
			strSql2.append(Tag + ",");
		}
		if (Content != null) {
			strSql1.append("content,");
			strSql2.append("'" + Content + "',");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Lx != null) {
			strSql1.append("lx,");
			strSql2.append("'" + Lx + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=Userback_id.nextval; ");
		strSql.append("   insert into Userback (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.FLOAT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			Id = Float.parseFloat(param.get("id").getParamvalue().toString());
			errmess = Id.toString();
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Id == null || Id == 0) {
			errmess = "id无效！";
			return 0;
		}
		if (Remark == null || Remark.equals("")) {
			errmess = "处理意见不允许为空！";
			return 0;
		}

		if (Lx == null || Lx.equals("")) {
			errmess = "类型不允许为空！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Userback set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Tag != null) {
			strSql.append("tag=" + Tag + ",");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		// if (Notedate != null) {
		// strSql.append("notedate='" + Notedate + "',");
		// }
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Lx != null) {
			strSql.append("lx='" + Lx + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update1() {
		if (Id == null || Id == 0) {
			errmess = "id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Userback set ");
		// if (Id != null) {
		// strSql.append("id='" + Id + "',");
		// }
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Tag != null) {
			strSql.append("tag=" + Tag + ",");
		}
		if (Content != null) {
			strSql.append("content='" + Content + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		// if (Lastop != null) {
		strSql.append("lastop='" + Lastop + "',");
		// }
		// if (Notedate != null) {
		// strSql.append("notedate='" + Notedate + "',");
		// }
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Lx != null) {
			strSql.append("lx='" + Lx + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
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
		strSql.append(" FROM Userback ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Userback a");
		strSql.append(" left outer join employe b on a.epid=b.epid");
		strSql.append(" left outer join accreg c on b.accid=c.accid");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Userback");
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