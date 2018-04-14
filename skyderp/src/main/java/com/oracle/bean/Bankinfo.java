package com.oracle.bean;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

//*************************************
//  @author:sunhong  @date:2017-08-26 23:45:02
//*************************************
public class Bankinfo implements java.io.Serializable {
	private Long Id;
	private String Bank_name;
	private Long Version;
	private Date Create_time;
	private String Type_code;
	private String Bank_channel_no;
	private String Province;
	private String City;
	private String Clear_bank_channel_no;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Long id) {
		this.Id = id;
	}

	public Long getId() {
		return Id;
	}

	public void setBank_name(String bank_name) {
		this.Bank_name = bank_name;
	}

	public String getBank_name() {
		return Bank_name;
	}

	public void setVersion(Long version) {
		this.Version = version;
	}

	public Long getVersion() {
		return Version;
	}

	public void setCreate_time(Date create_time) {
		this.Create_time = create_time;
	}

	public String getCreate_time() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Create_time);
	}

	public void setType_code(String type_code) {
		this.Type_code = type_code;
	}

	public String getType_code() {
		return Type_code;
	}

	public void setBank_channel_no(String bank_channel_no) {
		this.Bank_channel_no = bank_channel_no;
	}

	public String getBank_channel_no() {
		return Bank_channel_no;
	}

	public void setProvince(String province) {
		this.Province = province;
	}

	public String getProvince() {
		return Province;
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getCity() {
		return City;
	}

	public void setClear_bank_channel_no(String clear_bank_channel_no) {
		this.Clear_bank_channel_no = clear_bank_channel_no;
	}

	public String getClear_bank_channel_no() {
		return Clear_bank_channel_no;
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
		// strSql.append("select wareid from warecode where id=" + id + " and
		// rownum=1 and statetag=1");
		// strSql.append(")");
		// if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
		// errmess = "当前记录已使用，不允许删除";
		// return 0;
		// }
		// strSql.setLength(0);
		// strSql.append(" update bankinfo set statetag=2,lastop='" + Lastop +
		// "',lastdate=sysdate");
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
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Id != null) {
			strSql1.append("id,");
			strSql2.append(Id + ",");
		}
		if (Bank_name != null) {
			strSql1.append("bank_name,");
			strSql2.append("'" + Bank_name + "',");
		}
		if (Version != null) {
			strSql1.append("version,");
			strSql2.append(Version + ",");
		}
		if (Create_time != null) {
			strSql1.append("create_time,");
			strSql2.append("'" + Create_time + "',");
		}
		if (Type_code != null) {
			strSql1.append("type_code,");
			strSql2.append("'" + Type_code + "',");
		}
		if (Bank_channel_no != null) {
			strSql1.append("bank_channel_no,");
			strSql2.append("'" + Bank_channel_no + "',");
		}
		if (Province != null) {
			strSql1.append("province,");
			strSql2.append("'" + Province + "',");
		}
		if (City != null) {
			strSql1.append("city,");
			strSql2.append("'" + City + "',");
		}
		if (Clear_bank_channel_no != null) {
			strSql1.append("clear_bank_channel_no,");
			strSql2.append("'" + Clear_bank_channel_no + "',");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=bankinfo_id.nextval; ");
		strSql.append("   insert into bankinfo (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		// id = Long.parseLong(param.get("id").getParamvalue().toString());
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update bankinfo set ");
		if (Id != null) {
			strSql.append("id=" + Id + ",");
		}
		if (Bank_name != null) {
			strSql.append("bank_name='" + Bank_name + "',");
		}
		if (Version != null) {
			strSql.append("version=" + Version + ",");
		}
		if (Create_time != null) {
			strSql.append("create_time='" + Create_time + "',");
		}
		if (Type_code != null) {
			strSql.append("type_code='" + Type_code + "',");
		}
		if (Bank_channel_no != null) {
			strSql.append("bank_channel_no='" + Bank_channel_no + "',");
		}
		if (Province != null) {
			strSql.append("province='" + Province + "',");
		}
		if (City != null) {
			strSql.append("city='" + City + "',");
		}
		if (Clear_bank_channel_no != null) {
			strSql.append("clear_bank_channel_no='" + Clear_bank_channel_no + "',");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=id ;");
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
		strSql.append(" FROM bankinfo ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM bankinfo ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from bankinfo");
		return false;
	}
}