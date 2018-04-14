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
import com.common.tool.JGPush;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-03-08 18:27:11
//*************************************
public class Guestvip implements java.io.Serializable {
	private Long Guestid;
	private String Guestname;
	private String Shortname;
	private String Sex;
	private String Vipno;
	private Long Accid;
	private Long Vtid;
	private String Mobile;
	private String Birthday;
	private Integer Statetag;
	private Integer Usetag;
	private String Stature;
	private String Fitsize;
	private String Likesome;
	private Float Balcent;
	private Float Balcurr;
	private Long Houseid;
	private Date Createdate;
	private String Lastop;
	private Date Lastdate;
	private Long Vipid;
	private String Remark;
	private Date Validdate;
	private String Address;
	private String Email;
	private Long Epid;
	private Integer Lytag;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setGuestid(Long guestid) {
		this.Guestid = guestid;
	}

	public Long getGuestid() {
		return Guestid;
	}

	public void setGuestname(String guestname) {
		this.Guestname = guestname;
	}

	public String getGuestname() {
		return Guestname;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setVipno(String vipno) {
		this.Vipno = vipno;
	}

	public String getVipno() {
		return Vipno;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setVtid(Long vtid) {
		this.Vtid = vtid;
	}

	public Long getVtid() {
		return Vtid;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setBirthday(String birthday) {
		this.Birthday = birthday;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setUsetag(Integer usetag) {
		this.Usetag = usetag;
	}

	public Integer getUsetag() {
		return Usetag;
	}

	public void setStature(String stature) {
		this.Stature = stature;
	}

	public String getStature() {
		return Stature;
	}

	public void setFitsize(String fitsize) {
		this.Fitsize = fitsize;
	}

	public String getFitsize() {
		return Fitsize;
	}

	public void setLikesome(String likesome) {
		this.Likesome = likesome;
	}

	public String getLikesome() {
		return Likesome;
	}

	public void setBalcent(Float balcent) {
		this.Balcent = balcent;
	}

	public Float getBalcent() {
		return Balcent;
	}

	public void setBalcurr(Float balcurr) {
		this.Balcurr = balcurr;
	}

	public Float getBalcurr() {
		return Balcurr;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setCreatedate(Date createdate) {
		this.Createdate = createdate;
	}

	public String getCreatedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Createdate);
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setValiddate(Date validdate) {
		this.Validdate = validdate;
	}

	public String getValiddate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Validdate);
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getEmail() {
		return Email;
	}

	public void setEpid(Long epid) {
		this.Epid = epid;
	}

	public Long getEpid() {
		return Epid;
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
		strSql.append(" update guestvip set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 增加记录
	public int Append() {
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		if (Guestid != null) {
			strSql1.append("guestid,");
			strSql2.append(Guestid + ",");
		}
		if (Guestname != null) {
			strSql1.append("guestname,");
			strSql2.append("'" + Guestname + "',");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Sex != null) {
			strSql1.append("sex,");
			strSql2.append("'" + Sex + "',");
		}
		if (Vipno != null) {
			strSql1.append("vipno,");
			strSql2.append("'" + Vipno + "',");
		}
		if (Accid != null) {
			strSql1.append("accid,");
			strSql2.append(Accid + ",");
		}
		if (Vtid != null) {
			strSql1.append("vtid,");
			strSql2.append(Vtid + ",");
		}
		if (Mobile != null) {
			strSql1.append("mobile,");
			strSql2.append("'" + Mobile + "',");
		}
		if (Birthday != null) {
			strSql1.append("birthday,");
			strSql2.append("'" + Birthday + "',");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		if (Usetag != null) {
			strSql1.append("usetag,");
			strSql2.append(Usetag + ",");
		}
		if (Stature != null) {
			strSql1.append("stature,");
			strSql2.append("'" + Stature + "',");
		}
		if (Fitsize != null) {
			strSql1.append("fitsize,");
			strSql2.append("'" + Fitsize + "',");
		}
		if (Likesome != null) {
			strSql1.append("likesome,");
			strSql2.append("'" + Likesome + "',");
		}
		if (Balcent != null) {
			strSql1.append("balcent,");
			strSql2.append("'" + Balcent + "',");
		}
		if (Balcurr != null) {
			strSql1.append("balcurr,");
			strSql2.append("'" + Balcurr + "',");
		}
		if (Houseid != null) {
			strSql1.append("houseid,");
			strSql2.append(Houseid + ",");
		}
		if (Createdate != null) {
			strSql1.append("createdate,");
			strSql2.append("'" + Createdate + "',");
		}
		if (Lastop != null) {
			strSql1.append("lastop,");
			strSql2.append("'" + Lastop + "',");
		}
		if (Vipid != null) {
			strSql1.append("vipid,");
			strSql2.append(Vipid + ",");
		}
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Validdate != null) {
			strSql1.append("validdate,");
			strSql2.append("'" + Validdate + "',");
		}
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Email != null) {
			strSql1.append("email,");
			strSql2.append("'" + Email + "',");
		}
		if (Epid != null) {
			strSql1.append("epid,");
			strSql2.append(Epid + ",");
		}
		if (Lytag != null) {
			strSql1.append("lytag,");
			strSql2.append(Lytag + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("   v_id:=guestvip_id.nextval; ");
		strSql.append("   insert into guestvip (");
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
		if (ret < 0)
			return 0;
		else
			return 1;
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update guestvip set ");
		if (Guestid != null) {
			strSql.append("guestid=" + Guestid + ",");
		}
		if (Guestname != null) {
			strSql.append("guestname='" + Guestname + "',");
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}
		if (Vipno != null) {
			strSql.append("vipno='" + Vipno + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Vtid != null) {
			strSql.append("vtid=" + Vtid + ",");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Birthday != null) {
			strSql.append("birthday='" + Birthday + "',");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Usetag != null) {
			strSql.append("usetag=" + Usetag + ",");
		}
		if (Stature != null) {
			strSql.append("stature='" + Stature + "',");
		}
		if (Fitsize != null) {
			strSql.append("fitsize='" + Fitsize + "',");
		}
		if (Likesome != null) {
			strSql.append("likesome='" + Likesome + "',");
		}
		if (Balcent != null) {
			strSql.append("balcent='" + Balcent + "',");
		}
		if (Balcurr != null) {
			strSql.append("balcurr='" + Balcurr + "',");
		}
		if (Houseid != null) {
			strSql.append("houseid=" + Houseid + ",");
		}
		if (Createdate != null) {
			strSql.append("createdate='" + Createdate + "',");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Vipid != null) {
			strSql.append("vipid=" + Vipid + ",");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Validdate != null) {
			strSql.append("validdate='" + Validdate + "',");
		}
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Email != null) {
			strSql.append("email='" + Email + "',");
		}
		if (Epid != null) {
			strSql.append("epid=" + Epid + ",");
		}
		if (Lytag != null) {
			strSql.append("lytag=" + Lytag + ",");
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
		strSql.append(" FROM guestvip ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM guestvip ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from guestvip");
		// strSql.append(" where brandname='" + Brandname + "' and statetag=1 and rownum=1 and accid=" + Accid);
		// if (Brandid != null)
		// strSql.append(" and brandid<>" + Brandid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			// errmess = "???:" + Brandname + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	public int doAddGuestvip(long onhouseid) {

		if (Guestname == null || Guestname.equals("")) {
			errmess = "请输入会员姓名！";
			return 0;
		}

		if (Mobile == null || Mobile.equals("")) {
			errmess = "请输入移动电话号码！";
			return 0;
		}
		if (!Func.isMobile(Mobile)) {
			errmess = "移动电话号码无效！";
			return 0;
		}
		if (Sex == null || Sex.equals("")) {
			errmess = "请选择性别！";
			return 0;

		}
		if (Email == null)
			Email = "";
		if (!Email.equals("")) {
			if (!Func.isEmail(Email)) {
				errmess = "邮箱格式无效！";
				return 0;
			}
		}
		if (Address == null)
			Address = "";

		if (Birthday == null)
			Birthday = "";

		String qry = "declare ";
		qry += "\n    v_accid number(10); ";
		qry += "\n    v_guestid number; ";
		qry += "\n    v_houseid number; ";
		qry += "\n    v_vipid number(10); ";
		qry += "\n    v_vtid number(10); ";
		qry += "\n    v_vtid0 number(10); ";
		qry += "\n    v_count number; ";
		qry += "\n    v_housename varchar2(40); ";
		qry += "\n    v_vtname varchar2(40); ";
		qry += "\n    v_retcs varchar2(200); ";
		qry += "\n    v_userlist varchar2(4000); ";

		qry += "\n begin ";
		qry += "\n    v_userlist:='';v_accid:=0;";
		qry += "\n    begin ";
		qry += "\n      select accid,onhousename into v_accid,v_housename from onlinehouse where onhouseid=" + onhouseid + "; ";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0线上店铺id无效';  ";
		// qry += "\n select '0线上店铺id无效',0 into :retcs,:accid from dual; ";
		qry += "\n      goto exit ; ";
		qry += "\n    end ; ";

		qry += "\n    begin ";
		qry += "\n      select houseid into v_houseid from warehouse where accid=v_accid and statetag=1 and noused=0 and onhouseid=" + onhouseid + " and rownum=1 ; ";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_retcs:= '0未找到线上店铺对应的实体店！'; ";
		// qry += "\n select '0未找到线上店铺对应的实体店！',0 into :retcs,:accid from dual; ";
		qry += "\n      goto exit ; ";
		qry += "\n    end ; ";
		// 查找默认会员类型
		qry += "\n    begin ";
		qry += "\n      select vtid into v_vtid0 from guesttype where accid=v_accid and defbj=1; ";
		qry += "\n    EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n      v_vtid0:=0 ; ";
		qry += "\n    end ; ";

		qry += "\n    begin ";
		// 查询手机号对应的会员是否存在
		qry += "\n     select a.guestid,a.vtid,b.vtname into v_guestid,v_vtid,v_vtname from guestvip a ";
		qry += "\n     left outer join guesttype b on a.vtid=b.vtid  where a.accid=v_accid and a.mobile='" + Mobile + "' and rownum=1;";
		qry += "\n     if v_vtid=0 then";
		qry += "\n        delete from viphouse where onhouseid=" + onhouseid + " and vipid=" + Vipid + ";";
		qry += "\n        insert into viphouse (vipid,onhouseid,housename0,statetag) ";
		qry += "\n        values (" + Vipid + "," + onhouseid + ",v_housename,0); ";
		// qry += "\n select '2申请已经提交成功，请稍候！',v_accid into :retcs,:accid from dual; ";
		qry += "\n        v_retcs:= '2申请已经提交成功，请稍候！'; v_userlist:=f_getaccmanagerlist(v_accid) ; ";
		qry += "\n        goto exit ; ";
		qry += "\n     end if;";
		// 如果存在，关联会员id
		// qry.qry += " if v_vipid=" + vipid + " then "; //如果当前会员已是企业的会员，自动关联线上店铺
		qry += "\n     select count(*) into v_count from viphouse where onhouseid=" + onhouseid + " and vipid=" + Vipid + ";";
		qry += "\n     if v_count=0 then";
		qry += "\n        insert into viphouse(vipid,onhouseid,housename0,statetag) ";
		qry += "\n        values (" + Vipid + "," + onhouseid + ",v_housename,1); ";
		qry += "\n     else ";
		qry += "\n        update viphouse set statetag=1 where vipid=" + Vipid + " and onhouseid=" + onhouseid + ";";
		qry += "\n     end if; ";
		// qry += " end if;";
		qry += "\n     update guestvip set vipid=" + Vipid + " where guestid=v_guestid; ";
		qry += "\n     v_retcs:= '1申请加入店铺VIP成功！'; v_accid:=0; v_userlist:=v_vtname; ";
		// qry += "\n select '1申请加入店铺VIP成功！',0 into :retcs,:accid from dual; ";
		qry += "\n  EXCEPTION WHEN NO_DATA_FOUND THEN";
		// 如果不存在，新建一条会员记录，待审核
		qry += "\n       insert into guestvip (guestid,accid,guestname,sex,mobile,address,email,birthday,vipid,vipno,houseid,vtid,balcurr,balcent,statetag,lytag,createdate,lastdate,lastop)";
		qry += "\n       values (guestvip_guestid.nextval,v_accid,'" + Guestname + "','" + Sex + "','" + Mobile + "','" + Address + "','" + Email + "','" + Birthday + "'," + Vipid + ",'" + Mobile
				+ "',v_houseid,v_vtid0,0,0,1,1,sysdate,sysdate,'sysdba'); ";
		qry += "\n       select count(*) into v_count from viphouse where onhouseid=" + onhouseid + " and vipid=" + Vipid + ";";

		qry += "\n       delete from viphouse where onhouseid=" + onhouseid + " and vipid=" + Vipid + ";";
		qry += "\n       insert into viphouse(vipid,onhouseid,housename0,statetag) ";
		qry += "\n       values (" + Vipid + "," + onhouseid + ",v_housename,0); ";
		// qry += "\n select '2申请提交成功，请等候审核！' into :retcs from dual; ";
		qry += "\n       v_retcs:= '2申请提交成功，请等候审核！'; v_userlist:=f_getaccmanagerlist(v_accid); ";
		// qry += "\n select '2申请提交成功，请等候审核！',v_accid into :retcs,:accid from dual; ";
		qry += "\n    end ; ";
		qry += "\n    <<exit>>  ";
		qry += "\n    select v_retcs,v_accid,v_userlist into :retcs,:accid,:userlist from dual; ";

		qry += "\n end; ";
//		LogUtils.LogDebugWrite("addguestvip", qry);
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		param.put("accid", new ProdParam(Types.BIGINT));
		param.put("userlist", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(qry, param);
		if (ret < 0) {
			errmess = "操作异常！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		if (retcs.substring(0, 1).equals("1")) {
			String userlist = param.get("userlist").getParamvalue().toString();
			String responsestr = "\"msg\":\"" + retcs.substring(1) + "\"" + ",\"VTNAME\":\"" + userlist + "\"";
			errmess = responsestr;
			return 1;
		} else {
			// WriteLogTXT("debug", "addguestvip", "6");
			errmess = retcs.substring(1);
			// WriteResult(retcs.Substring(0, 1), retcs.Substring(1, retcs.Length - 1));

			if (retcs.substring(0, 1).equals("2")) // 向商家发送申请加入会员消息
			{
				// WriteLogTXT("debug", "addguestvip", "7");
				String userlist = param.get("userlist").getParamvalue().toString();
				Accid = Long.parseLong(param.get("accid").getParamvalue().toString());
				String usertag = "erptag" + Accid;
				String strmess = "您有新会员申请加入！";
				// 发送消息推送
				JGPush.PushToErp(userlist, usertag, strmess);
			}
			return Integer.parseInt(retcs.substring(0, 1));
		}
	}
}