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
//  @author:sunhong  @date:2017-03-02 16:29:09
//*************************************
public class Vipreservation implements java.io.Serializable {
	private Float Id;
	private Long Vipid;
	private Long Wareid;
	private Long Colorid;
	private Long Sizeid;
	private Long Houseid;
	private Long Onhouseid;
	private String Linkman;
	private String Sex ;
	private String Tel ;
	private String Arrdate;
	private String Remark0 ;
	private Long Statetag;
	private Date Lastdate;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
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

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setArrdate(String arrdate) {
		this.Arrdate = arrdate;
	}

	public String getArrdate() {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// return df.format(Arrdate);
		return Arrdate;
	}

	public void setRemark0(String remark0) {
		this.Remark0 = remark0;
	}

	public String getRemark0() {
		return Remark0;
	}

	public void setStatetag(Long statetag) {
		this.Statetag = statetag;
	}

	public Long getStatetag() {
		return Statetag;
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

		strSql.append(" delete from Vipreservation");
		strSql.append(" where id=" + Id + " and vipid=" + Vipid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		errmess = "删除成功！";
		return 1;
	}

	// 增加记录
	public int Append() {
		//System.out.println("aaaaaa");
		if (Wareid == null || Colorid == null || Sizeid == null || Onhouseid == null || Wareid == 0 || Colorid == 0 || Sizeid == 0 || Onhouseid == 0) {
			errmess = "参数无效！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n    v_id number; ");
		strSql.append("\n    v_linkman varchar2(40);");
		strSql.append("\n    v_sex varchar2(10);");
		strSql.append("\n    v_tel varchar2(40);");
		strSql.append("\n    v_count number(10);");
		strSql.append("\n begin ");

		strSql.append("\n   select vipname,sex,mobile into v_linkman,v_sex,v_tel from vipgroup where vipid=" + Vipid + ";");
		strSql.append("\n   select count(*) into v_count from vipreservation where vipid=" + Vipid + " and onhouseid=" + Onhouseid + " and wareid=" + Wareid + " and colorid=" + Colorid + " and sizeid=" + Sizeid + ";");
		strSql.append("\n   if v_count=0 then ");
		strSql.append("\n      insert into vipreservation (id,vipid,onhouseid,wareid,colorid,sizeid,remark0,linkman,sex,tel)");
		strSql.append("\n      values (vipreservation_id.nextval," + Vipid + "," + Onhouseid + "," + Wareid + "," + Colorid + "," + Sizeid + ",'',v_linkman,v_sex,v_tel);");
		strSql.append("\n   end if;");
		strSql.append("\n  select count(*) into :num from vipreservation where vipid=" + Vipid + ";");
		strSql.append("\n end;");
		System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("num", new ProdParam(Types.INTEGER));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		int num = Integer.parseInt(param.get("num").getParamvalue().toString());
		errmess = "" + num;

		return 1;
	}

	// 取消预约
	public int Cancel() {
		if (Id==null || Id == 0) {
			errmess = "记录id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append(" update Vipreservation set statetag=0 where vipid=" + Vipid + " and id=" + Id);
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "取消预约成功！";
			return 0;
		} else {
			errmess = "取消预约成功！";
			return 1;
		}
	}

	// 修改记录
	public int Update() {
		if (Id == null || Id == 0) {
			errmess = "记录id无效！";
			return 0;
		}
		if (Houseid != null && Houseid == 0) {
			errmess = "请选择预约门店！";
			return 0;
		}
		if (Linkman != null && Linkman.equals("")) {
			errmess = "请输入预约人！";
			return 0;
		}
		if (Tel != null && Tel.equals("")) {
			errmess = "请输入联系电话！";
			return 0;
		}
		if (Arrdate != null && Arrdate.equals("")) {
			errmess = "请输入到店时间！";
			return 0;
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update Vipreservation set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
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
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Linkman != null) {
			strSql.append("linkman='" + Linkman + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Arrdate != null) {
			strSql.append("arrdate=to_date('" + Arrdate + "','yyyy-mm-dd hh24:mi:ss'),");
		}
		if (Remark0 != null) {
			strSql.append("remark0='" + Remark0 + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where id=" + Id + " and vipid=" + Vipid + " ;");
		strSql.append("  commit;");
		strSql.append(" end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "修改失败！";
			return 0;
		} else {
			errmess = "修改成功！";

			return 1;
		}
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipreservation ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM Vipreservation ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM Vipreservation ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public int Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from Vipreservation");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return 0;
		}
		return 1;
	}
}