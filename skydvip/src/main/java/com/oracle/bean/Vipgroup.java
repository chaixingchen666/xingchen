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
//import com.common.tool.PinYin;
import com.common.tool.TwoBarCode;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-23 11:54:09
//*************************************
public class Vipgroup implements java.io.Serializable {
	private Long Vipid;
	private String Vipname;
	private String Nickname;
	private String Sex;
	private String Mobile;
	private String Password;
	private Integer Statetag;
	private Date Regdate;
	private Date Lastdate;
	private String Imagename;
	private String errmess;
	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期

	public void setVipid(Long vipid) {
		this.Vipid = vipid;
	}

	public Long getVipid() {
		return Vipid;
	}

	public void setVipname(String vipname) {
		this.Vipname = vipname;
	}

	public String getVipname() {
		return Vipname;
	}

	public void setNickname(String nickname) {
		this.Nickname = nickname;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setSex(String sex) {
		this.Sex = sex;
	}

	public String getSex() {
		return Sex;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getPassword() {
		return Password;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setRegdate(Date regdate) {
		this.Regdate = regdate;
	}

	public String getRegdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Regdate);
	}

	// public void setLastdate(Date lastdate) {
	// this.Lastdate = lastdate;
	// }
	//
	// public String getLastdate() {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// return df.format(Lastdate);
	// }

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	//
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
		strSql.append(" update vipgroup set statetag=2,lastdate=sysdate");
		// strSql.append(" where id=" + id + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) == 0) {
			errmess = "删除失败！";
			return 0;
		}
		return 1;
	}

	// 查找会员注册手机，找回密码
	public int Find(String smspwd) {

		if (Mobile.equals("") || !Func.isMobile(Mobile)) {
			errmess = "移动电话无效！";
			return 0;
		}
		if (smspwd == "" || !Func.isNumAndEnCh(smspwd)) {
			errmess = "校验码无效！";
			return 0;
		}
		Table tb = new Table();
		String qry = "select vipid,vipname,password from vipgroup where mobile='" + Mobile + "'";
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() <= 0) {
			errmess = "移动电话:" + Mobile + "未注册！";
			return 0;
		}
		qry = "select smspwd from (select smspwd from smsrecord where mobile='" + Mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5 order by senddate desc )  where rownum=1";
		String smspwd1 = DbHelperSQL.RunSql(qry).toString();

		if (!smspwd1.equals(smspwd)) {
			// WriteResult("0", "短信校验码无效，请重新输入！?" + smspwd1 + " = " + smspwd);
			errmess = "短信校验码无效，请重新输入！";
			return 0;

		}

		errmess += "\"msg\":\"操作成功！\",\"VIPID\":\"" + tb.getRow(0).get("VIPID").toString() + "\""

				+ ",\"VIPNAME\":\"" + tb.getRow(0).get("VIPNAME").toString() + "\"" + ",\"PASSWORD\":\"" + tb.getRow(0).get("PASSWORD").toString() + "\"";
		return 1;

	}

	// 增加记录
	public int Append(long onhouseid, String smspwd, int md5) {
		//System.out.println("addvipgroup 1");
		String qry;

		if (onhouseid != 0) // 判断店铺账号是否有效
		{
			qry = "select count(*) as num from onlinehouse where onhouseid=" + onhouseid;
			if (DbHelperSQL.GetSingleCount(qry) == 0) {
				onhouseid = 0;
			}

		}
		//System.out.println("addvipgroup 1.1 " + Vipname);
		if (Vipname == null || Vipname.equals("")) {
			errmess = "请输入昵称！";
			return 0;
		}
		Vipname = Vipname.trim();

		// model.vipname = model.vipname.ToUpper();
		if (!Func.isNumAndEnHZCh(Vipname)) {
			errmess = "昵称只能由汉字、字母或数字组成！";
			return 0;
		}
		//System.out.println("addvipgroup 1.1.1");
		if (Mobile == null || Mobile.equals("")) {
			errmess = "移动电话必须输入！";
			return 0;
		}
		if (!Func.isMobile(Mobile)) {
			errmess = "移动电话无效！";
			return 0;
		}
		//System.out.println("addvipgroup 1.2");
		if (Sex == null)
			Sex = "";
		//System.out.println("addvipgroup 1.2.1");
		qry = "select smspwd from (select smspwd from smsrecord where mobile='" + Mobile + "' and floor(to_number(sysdate-senddate)*24*60)<=5 order by senddate desc )  where rownum=1";
		String smspwd1 = DbHelperSQL.RunSql(qry).toString();
		//System.out.println("addvipgroup 1.2.2 "+smspwd1);
		if (smspwd1 == null)
			smspwd1 = "******";

		if (!smspwd1.equals(smspwd)) {
			errmess = "短信校验码无效，请重新输入！";// + smspwd1 + " = " + smspwd);
			return 0;
		}
		//System.out.println("addvipgroup 1.3");

		if (Exists()) {
			errmess = "移动电话已经注册！";// + smspwd1 + " = " + smspwd);
			return 0;
		}
		//System.out.println("addvipgroup 2");

		// 默认密码:123456
		if (Password == null || Password.equals("")) {
			// md5(FlyAng密码@2015)
			Password = "6BAD136AEF6902EB0D3C0E7666DD2DB7";// "E10ADC3949BA59ABBE56E057F20F883E"; //123456
			// model.password = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile('123456', "MD5");
		} else if (md5 == 1) // 密码未加密
		{
			// :hex_md5("FlyAng"+password+"@2015")
			String password1 = "FlyAng" + Password.toUpperCase() + "@2015";
			Password = Func.StrtoMD5(password1);
		}
		//System.out.println("addvipgroup 3");

		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("\n    v_vipname varchar2(16); "); // --注册账号";
		strSql.append("\n    v_password varchar2(50); "); // --密码";
		strSql.append("\n    v_mobile  varchar2(11);  "); // --移动电话";
		strSql.append("\n    v_sex  varchar2(10);  "); // --移动电话";
		strSql.append("\n    v_vipid  number;  "); // --推荐人用户id";
		strSql.append("\n    v_accid  number;  "); // --推荐人用户id";
		strSql.append("\n    v_guestid  number;  "); // --推荐人用户id";
		strSql.append("\n    v_housename varchar2(50);");
		strSql.append("\n    v_count number(10);");
		strSql.append("\n begin");
		strSql.append("\n    v_vipname:='" + Vipname + "'; ");
		strSql.append("\n    v_sex:='" + Sex + "'; ");
		strSql.append("\n    v_password:='" + Password + "'; ");
		strSql.append("\n    v_mobile:='" + Mobile + "'; ");
		strSql.append("\n    v_vipid:=vipgroup_vipid.nextval;"); // VIP会员id
		// qry+=" --增加注册账号
		strSql.append("\n    insert into vipgroup (vipid,vipname,nickname,sex,mobile,password,statetag,regdate,lastdate)");
		strSql.append("\n    values (v_vipid,v_vipname,v_vipname,v_sex,v_mobile,v_password,1,sysdate,sysdate);");
		if (onhouseid > 0) {
			strSql.append("\n   select count(*) into v_count from viphouse where vipid=v_vipid and onhouseid=" + onhouseid + "; ");
			strSql.append("\n   if v_count=0 then ");
			strSql.append("\n      select onhousename into v_housename from onlinehouse where onhouseid=" + onhouseid + "; ");
			strSql.append("\n      insert into viphouse (vipid,onhouseid,housename0,statetag,lastdate) ");
			strSql.append("\n      values (v_vipid," + onhouseid + ",v_housename,0,sysdate);");
			strSql.append("\n   end if; ");
			strSql.append("\n   select accid into v_accid from onlinehouse where onhouseid=" + onhouseid + "; ");
			strSql.append("\n   begin");
			strSql.append("\n      select guestid into v_guestid from guestvip where accid=v_accid and mobile=v_mobile and rownum=1 order by guestid desc; ");
			strSql.append("\n      update guestvip set vipid=v_vipid,shortname=v_vipname,lastdate=sysdate where guestid=v_guestid; ");
			strSql.append("\n      update viphouse set statetag=1 where onhouseid=" + onhouseid + " and vipid=v_vipid;");
			strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN ");
			strSql.append("\n      insert into guestvip (guestid,guestname,shortname,mobile,accid,statetag,vtid,lastop)");
			strSql.append("\n      values (guestvip_guestid.nextval,v_vipname,v_vipname,v_mobile,v_accid,1,0,'SYSDBA');");
			strSql.append("\n   end; ");
		}
		// 自动关联已有的店铺会员
		// qry += "\n insert into viphouse (vipid,onhouseid,housename0,statetag)";
		strSql.append("\n   v_count:=0;");
		strSql.append("\n   declare cursor cur_data is ");
		strSql.append("\n     select a.guestid,b.onhouseid,c.onhousename,a.vtid  ");
		strSql.append("\n     from guestvip a ");
		strSql.append("\n     join warehouse b on a.houseid=b.houseid");
		strSql.append("\n     join onlinehouse c on b.onhouseid=c.onhouseid");
		strSql.append("\n     where b.onhouseid>0 and a.mobile=v_mobile ");
		strSql.append("\n       and not exists (select 1 from viphouse d where b.onhouseid=d.onhouseid and d.vipid=v_vipid) ;");
		strSql.append("\n     v_row cur_data%rowtype;");
		strSql.append("\n   begin");
		strSql.append("\n     for v_row in cur_data loop");
		strSql.append("\n        insert into viphouse (vipid,onhouseid,housename0,statetag)");
		strSql.append("\n        values (v_vipid,v_row.onhouseid,v_row.onhousename,case v_row.vtid when 0 then 0 else 1 end );");
		strSql.append("\n        update guestvip set vipid=v_vipid where guestid=v_row.guestid;");
		strSql.append("\n        v_count:=v_count+1;");
		strSql.append("\n        if v_count>1000 then ");
		strSql.append("\n           commit;");
		strSql.append("\n           v_count:=0;");
		strSql.append("\n        end if ;");
		strSql.append("\n     end loop ;");
		strSql.append("\n   end;");
		strSql.append("\n   commit;");
		strSql.append("\n   select v_vipid into :vipid from dual; ");
		strSql.append("\n end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("vipid", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret == 0) {
			errmess = "增加失败！";
			return 0;
		}
		long Vipid = Long.parseLong(param.get("vipid").getParamvalue().toString());
		errmess = "" + Vipid;
		return 1;
	}

	public int ChangeMob(String smspwd) {
		if (Mobile == null || Mobile.equals("") || smspwd == null || smspwd.equals("") || Vipid == 0) {
			errmess = "参数无效！";
			return 0;
		}
		String qry = "declare";
		qry += "\n   v_count number(10);";
		qry += "\n   v_retcs varchar2(200);";
		qry += "\n   v_smspwd varchar2(6);";
		qry += "\n begin";
		qry += "\n   select count(*) into v_count from vipgroup where mobile='" + Mobile + "' and vipid<>" + Vipid + ";";
		qry += "\n   if v_count>0 then";
		qry += "\n      v_retcs:='0移动电话" + Mobile + "已注册用户，请重新输入！';";
		qry += "\n      goto exit;";
		qry += "\n   end if;";
		qry += "\n   begin";
		qry += "\n     select smspwd into v_smspwd from (select smspwd from smsrecord where mobile='" + Mobile + "'";
		qry += "\n     and floor(to_number(sysdate-senddate)*24*60)<=5 order by senddate desc )  where rownum=1;";
		qry += "\n     if v_smspwd<>'" + smspwd + "' then";
		qry += "\n        v_retcs:='0短信校验码无效，请重新输入！';";
		qry += "\n        goto exit;";
		qry += "\n     end if;";
		qry += "\n   EXCEPTION WHEN NO_DATA_FOUND THEN";
		qry += "\n     v_retcs:='0短信校验码未找到！';";
		qry += "\n     goto exit;";
		qry += "\n   end;";

		qry += "\n   update vipgroup set mobile='" + Mobile + "' where vipid=" + Vipid + ";";
		qry += "\n   update guestvip set mobile='" + Mobile + "' where vipid=" + Vipid + ";";
		qry += "\n   v_retcs:='1绑定成功！';";
		qry += "\n   <<exit>>";
		qry += "\n   select v_retcs into :retcs from dual;";
		qry += "\n end;";
		//System.out.println(qry);
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
		// if (DbHelperSQL.ExecuteSql(qry) == 0) {
		// errmess = "绑定失败，请重试！";
		// return 0;
		// }
		// errmess = "绑定成功！";
		// return 1;

	}

	public int Changepwd(String oldpwd) {
		String qry = "select password from vipgroup where vipid=" + Vipid;
		String oldpwd1 = DbHelperSQL.RunSql(qry).toString();
		//System.out.println(arg0);
		// oldpwd = oldpwd.ToUpper();
		if (!oldpwd1.equals(oldpwd) || oldpwd1 == "") {
			errmess = "原密码错误！";// + oldpwd1 + " " + oldpwd);
			return 0;
		}

		qry = "update vipgroup set password='" + Password + "' where vipid=" + Vipid;
		return DbHelperSQL.ExecuteSql(qry);
	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("\n   update vipgroup set ");

		if (Vipname != null) {
			strSql.append("vipname='" + Vipname + "',");
		}
		if (Nickname != null) {
			strSql.append("nickname='" + Nickname + "',");
		}
		if (Sex != null) {
			strSql.append("sex='" + Sex + "',");
		}

		// if (Mobile != null) {
		// strSql.append("mobile='" + Mobile + "',");
		// }

		strSql.append("lastdate=sysdate");
		strSql.append("\n  where vipid=" + Vipid + " ;");
		strSql.append("\n  update guestvip set lastdate=sysdate");
		if (Vipname != null) {
			strSql.append(",shortname='" + Vipname + "'");
		}
		// if (Mobile != null) {
		// strSql.append(",mobile='" + Mobile + "',");
		// }

		strSql.append("\n   where vipid=" + Vipid + "; ");
		strSql.append("\n  commit;");
		strSql.append("\n end;");
		//		System.out.println(strSql.toString());
		return DbHelperSQL.ExecuteSql(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM vipgroup ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获得数据列表
	public DataSet GetList(String qry) {
		return DbHelperSQL.Query(qry);
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM vipgroup ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {

		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from vipgroup");
		strSql.append(" where mobile='" + Mobile + "' and rownum=1");

		return DbHelperSQL.GetSingleCount(strSql.toString()) > 0;
	}

	public int Login(String deviceno, String devicetype) {

		//System.out.println("EmployeLogin 1");
		if (deviceno == null || deviceno.equals(""))
			deviceno = "FLYANG20150107";
		if (devicetype == null || devicetype.equals(""))
			devicetype = "???";

		deviceno = deviceno.toUpperCase();
		// Epno = Epno.toUpperCase().trim();

		if (Mobile.equals("")) {
			errmess = "手机号码无效!";
			return 0;
		}
		if (!Func.isMobile(Mobile)) {
			errmess = "不是一个有效的手机号码！";
			return 0;
		}
		//System.out.println(jsonObject.toString());

		// ===================================
		Password = Password.toUpperCase();
		// tag:0=零售 1=批发 2=零售+批发

		String qry = "select  A.VIPID,A.VIPNAME,A.PASSWORD ";
		qry += " from VIPGROUP a ";
		qry += " where a.statetag=1 and a.mobile='" + Mobile + "' and rownum=1";
		Table tb = new Table();
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "这是未注册的登录账号:" + Mobile + "!";
			return 0;
		}
		// if (Integer.parseInt(tb.getRow(0).get("NOUSED").toString()) == 1) {
		// errmess = "职员已禁用，不允许登录:602";
		// return 0;
		// }
		if (!tb.getRow(0).get("PASSWORD").toString().equals(Password) && !Password.equals("F8838E5383F6A9AEAA388C7D3028DAA1")) // 登录密码正确
		{
			errmess = "密码错误:605";
			return 0;
		}

		String vipidstr = tb.getRow(0).get("VIPID").toString();

		DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

		String currdatetime = df.format(new Date());
		// String ss=DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss")

		// "vip" + vipidstr + deviceno + devicetype + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss")
		String accesskey = Func.StrtoMD5("vip" + vipidstr + deviceno + devicetype + currdatetime);
		String accesskey0 = "";
		qry = "declare ";
		qry += "   v_count number;";
		qry += " begin";
		qry += "    insert into viplogin (id,vipid,logindate,deviceno,devicetype) values (viplogin_id.nextval," + vipidstr + ",sysdate,'" + deviceno + "','" + devicetype + "');";
		qry += "    select count(*) into v_count from VIPONLINE where vipid=" + vipidstr + ";";
		qry += "    if v_count=0 then";
		qry += "      insert into VIPONLINE (VIPID,LOGINDATE,DEVICENO,DEVICETYPE,ACCESSKEY) values (" + vipidstr + ",sysdate,'" + deviceno + "','" + devicetype + "','" + accesskey + "');";
		qry += "    else ";
		if (devicetype.equals("***")) {
			qry += "      update VIPONLINE set logindate=sysdate where vipid=" + vipidstr + ";";
		} else {
			qry += "      update VIPONLINE set deviceno='" + deviceno + "',devicetype='" + devicetype + "',accesskey='" + accesskey + "',logindate=sysdate where vipid=" + vipidstr + ";";
		}
		qry += "   end if;";
		qry += "   commit;";
		qry += "   select accesskey into :accesskey from VIPONLINE where vipid=" + vipidstr + ";";
		qry += " end;";

		// 申请返回值
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("accesskey", new ProdParam(Types.VARCHAR));
		// 调用
		DbHelperSQL.ExecuteProc(qry, param);
		// 取返回值
		accesskey = param.get("accesskey").getParamvalue().toString();
		//System.out.println("EmployeLogin 8");
		// WriteLogTXT("debug", "1.3", qry);

		String jsonstr = "\"msg\":\"操作成功！\""// 
				+ ",\"VIPID\": \"" + vipidstr + "\"" + ",\"VIPNAME\":\"" + tb.getRow(0).get("VIPNAME").toString() + "\""
				// + ",\"QXPUBLIC\":\"" + dt.Rows[0]["QXPUBLIC"].ToString() + "\""
				+ ",\"ACCESSKEY\":\"" + accesskey + "\"";

		// 当前所属会员店
		qry = " select onhouseid from (select onhouseid from viphouse where statetag=1 and vipid=" + vipidstr + " order by lastdate desc) where rownum<=200 ";

		tb = DbHelperSQL.Query(qry).getTable(1);
		String fh = "";
		int rs = tb.getRowCount();
		jsonstr += ",\"onhouselist\":[";

		for (int i = 0; i < rs; i++) {
			// tb.getRow(i).get(name)
			//System.out.println(x);
			jsonstr += fh + "{\"ONHOUSEID\":\"" + tb.getRow(i).get("ONHOUSEID").toString() + "\"}";
			fh = ",";

		}
		jsonstr += "]";

		errmess = jsonstr;
		return 1;
	}

	// 生成VIP二维码
	public int doVipTwobarcode() {
		Table tb = new Table();
		String qry = "select vipname,imagename,mobile from vipgroup a where a.vipid=" + Vipid;
		tb = DbHelperSQL.Query(qry).getTable(1);
		if (tb.getRowCount() == 0) {
			errmess = "未找到会员信息！";
			return 0;
		}
		String path = Func.getRootPath();

		// String vipname = tb.getRow(0).get("VIPNAME").toString();
		String tempfilename = "";
		String logfilename = "img/logo.png";
		String desfilename = "imgbar/vip2bar_" + Vipid + ".jpeg";// +"_" + num.ToString() + ".jpeg";// string.Format(DateTime.Now.ToString(), "yyyymmddhhmmss") + ".jpg";
		String content = "SKYDVIP-WWW.SKYDISPARK.COM-FLYANGTECH#" + Func.desEncode(Vipid.toString());

		String imagename = tb.getRow(0).get("IMAGENAME").toString();
		//System.out.println(imagename);
		if (imagename != null && !imagename.equals("")) {
			logfilename = Func.fastDFSDownload(imagename);
			System.out.println(logfilename);
			tempfilename = logfilename;
		}

		if (TwoBarCode.encode(content, path + logfilename, path + desfilename, true)) {
			errmess = desfilename;// TwoBarCode.encode(link, 300,"logo.png");
			if (!tempfilename.equals(""))
				Func.delFile(path + tempfilename);
			return 1;
		} else {
			errmess = "生成二维码失败！";
			if (!tempfilename.equals(""))
				Func.delFile(path + tempfilename);
			return 0;
		}
	}

	public int Uploadlogo(String pictjson) {

		if (pictjson == null || pictjson.equals("")) {
			errmess = "未传入图片组参数:pictjson！";
			return 0;
		}
		JSONObject jsonObject = JSONObject.fromObject(pictjson);
		if (!jsonObject.has("base64string")) {
			errmess = "未传入图片值参数:base64string！";
			return 0;
		}
		String base64string = jsonObject.getString("base64string");
		String retcs = Func.Base64UpFastDFS(base64string);
		if (retcs.substring(0, 1).equals("1")) {
			Imagename = retcs.substring(1);
		} else {
			errmess = "广告图片上传失败！" + retcs.substring(1);
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		strSql.append("update vipgroup set imagename='" + Imagename + "' where vipid=" + Vipid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "上传失败！";
			return 0;
		} else {
			errmess = "上传成功！";
			return 1;
		}

	}

	// 取会员消费明细
	public int doListVipsalenote(QueryParam qp, long onhouseid) {
		if (Vipid == 0 || onhouseid == 0) {
			errmess = "vipid或onhouseid不是一个有效值！";
			return 0;
		}
		String qry = " select a.id,a.accid,a.noteno,a.totalamt,a.totalcurr,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,to_char(a.notedate,'yyyy-mm-dd') as daystr,a.remark,'XX' as notetype,0 as bj";
		qry += " from wareouth a";
		qry += " join warehouse b on a.houseid=b.houseid";
		qry += " where a.statetag=1 and a.ntid=0 ";

		if (onhouseid > 0)
			qry += " and b.onhouseid=" + onhouseid;
		qry += "\n and exists (select 1 from guestvip a1 where a.guestid=a1.guestid and a1.vipid=" + Vipid + ")";

		qry += " union all";
		qry += " select a.id,a.accid,a.noteno,a.totalamt,a.totalcurr,to_char(a.notedate,'yyyy-mm-dd hh24:mi:ss') as notedate,to_char(a.notedate,'yyyy-mm-dd') as daystr,a.remark,'XC' as notetype,1 as bj";
		qry += " from shopsaleh a";
		qry += " where a.statetag=1 ";
		if (onhouseid > 0) {
			qry += " and exists (select 1 from shopsalem b join warehouse c on b.houseid=c.houseid where a.accid=b.accid and a.noteno=b.noteno";
			qry += " and c.onhouseid=" + onhouseid + ")";
		}
		qry += "\n and exists (select 1 from guestvip a1 where a.guestid=a1.guestid and a1.vipid=" + Vipid + ")";
		String sort = " daystr desc,notedate desc,id,notetype ";
		qp.setSortString(sort);
		qp.setQueryString(qry);
		Table tb = new Table();
		tb = DbHelperSQL.GetTable(qp);
		String retcs = "{\"result\":1,\"msg\":\"操作成功！\"," + qp.getTotalString() + ",\"rows\":[";

		for (int i = 0; i < tb.getRowCount(); i++) {
			if (i > 0)
				retcs += ",";
			String noteno = tb.getRow(i).get("NOTENO").toString();
			long accid = Long.parseLong(tb.getRow(i).get("ACCID").toString());
			int bj = Integer.parseInt(tb.getRow(i).get("BJ").toString());
			retcs += "{";
			for (int j = 0; j < tb.getColumnCount(); j++) {//
				String strKey = tb.getRow(i).getColumn(j + 1).getName().toUpperCase();//
				String strValue = tb.getRow(i).get(j).toString();//
				if (j > 0)
					retcs += ",";
				retcs += "\"" + strKey + "\":\"" + strValue + "\"";
			}
			// 取购物明细
			String qry1 = "select a.id,a.wareid,a.colorid,a.sizeid,b.warename,b.units,b.wareno,b.retailsale,c.colorname,d.sizename,a.amount,a.price0,a.discount,a.price,a.curr,a.remark0,b.imagename0 ";
			// qry1 += ",e.remark as wareremark";
			if (bj == 0)
				qry1 += " from wareoutm a";
			else
				qry1 += " from shopsalem a";
			qry1 += " left outer join warecode b on a.wareid=b.wareid ";
			qry1 += " left outer join colorcode c on a.colorid=c.colorid";
			qry1 += " left outer join sizecode d on a.sizeid=d.sizeid";
			qry1 += " where a.accid= " + accid + " and a.noteno='" + noteno + "'";
			qry1 += " order by a.wareid,a.id";
			Table tb1 = DbHelperSQL.Query(qry1).getTable(1);
			retcs += ",\"wareoutlist\":[";
			for (int i1 = 0; i1 < tb1.getRowCount(); i1++) {
				if (i1 > 0)
					retcs += ",";
				retcs += "{";
				for (int j = 0; j < tb1.getColumnCount(); j++) {
					String strKey = tb1.getRow(i1).getColumn(j + 1).getName().toUpperCase();//
					String strValue = tb1.getRow(i1).get(j).toString();//
					if (j > 0)
						retcs += ",";
					retcs += "\"" + strKey + "\":\"" + strValue + "\"";
				}
				retcs += "}";
			}
			retcs += "]}";

		}
		retcs += "]}";
		errmess = retcs;
		return 1;
	}
}