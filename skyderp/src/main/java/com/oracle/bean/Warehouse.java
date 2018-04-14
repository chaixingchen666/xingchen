package com.oracle.bean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.common.tool.LogUtils;
import com.common.tool.PinYin;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//*************************************
//  @author:sunhong  @date:2017-02-19 20:01:32
//*************************************
public class Warehouse implements java.io.Serializable {
	private Long Houseid;
	private String Housename;
	private Long Accid;
	private String Address;
	private String Tel;
	private Integer Noused;
	private Integer Statetag;
	private String Lastop;
	private String Ontime;
	private String Offtime;
	private Long Rent;
	private Long Aream2;
	private Float Lx;
	private Float Ly;
	private Date Lastdate;
	private String Remark;
	private String Ontime1;
	private String Offtime1;
	private String Ontime2;
	private String Offtime2;
	private String Imagename;
	private String Twobarimage;
	private Long Pageview;
	private Long Agreeview;
	private Long Numview;
	private String Shortname;
	private Integer Usedbj;
	private String Locano;
	private Long Onhouseid;
	private String Imagename1;
	private Integer Pricetype;
	private Integer Pricetype1;
	private String errmess;

	private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	private Integer Qxbj; //

	public void setQxbj(Integer qxbj) {
		this.Qxbj = qxbj;
	}

	private Long Userid;

	public void setUserid(Long userid) {
		this.Userid = userid;
	}

	public void setAccbegindate(String accbegindate) {
		this.Accbegindate = accbegindate;
	}

	public void setHouseid(Long houseid) {
		this.Houseid = houseid;
	}

	public Long getHouseid() {
		return Houseid;
	}

	public void setHousename(String housename) {
		this.Housename = housename;
	}

	public String getHousename() {
		return Housename;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getAddress() {
		return Address;
	}

	public void setTel(String tel) {
		this.Tel = tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setNoused(Integer noused) {
		this.Noused = noused;
	}

	public Integer getNoused() {
		return Noused;
	}

	public void setStatetag(Integer statetag) {
		this.Statetag = statetag;
	}

	public Integer getStatetag() {
		return Statetag;
	}

	public void setLastop(String lastop) {
		this.Lastop = lastop;
	}

	public String getLastop() {
		return Lastop;
	}

	public void setOntime(String ontime) {
		this.Ontime = ontime;
	}

	public String getOntime() {
		return Ontime;
	}

	public void setOfftime(String offtime) {
		this.Offtime = offtime;
	}

	public String getOfftime() {
		return Offtime;
	}

	public void setRent(Long rent) {
		this.Rent = rent;
	}

	public Long getRent() {
		return Rent;
	}

	public void setAream2(Long aream2) {
		this.Aream2 = aream2;
	}

	public Long getAream2() {
		return Aream2;
	}

	public void setLx(Float lx) {
		this.Lx = lx;
	}

	public Float getLx() {
		return Lx;
	}

	public void setLy(Float ly) {
		this.Ly = ly;
	}

	public Float getLy() {
		return Ly;
	}

	public void setLastdate(Date lastdate) {
		this.Lastdate = lastdate;
	}

	public String getLastdate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Lastdate);
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return Remark;
	}

	public void setOntime1(String ontime1) {
		this.Ontime1 = ontime1;
	}

	public String getOntime1() {
		return Ontime1;
	}

	public void setOfftime1(String offtime1) {
		this.Offtime1 = offtime1;
	}

	public String getOfftime1() {
		return Offtime1;
	}

	public void setOntime2(String ontime2) {
		this.Ontime2 = ontime2;
	}

	public String getOntime2() {
		return Ontime2;
	}

	public void setOfftime2(String offtime2) {
		this.Offtime2 = offtime2;
	}

	public String getOfftime2() {
		return Offtime2;
	}

	public void setImagename(String imagename) {
		this.Imagename = imagename;
	}

	public String getImagename() {
		return Imagename;
	}

	public void setPageview(Long pageview) {
		this.Pageview = pageview;
	}

	public Long getPageview() {
		return Pageview;
	}

	public void setAgreeview(Long agreeview) {
		this.Agreeview = agreeview;
	}

	public Long getAgreeview() {
		return Agreeview;
	}

	public void setNumview(Long numview) {
		this.Numview = numview;
	}

	public Long getNumview() {
		return Numview;
	}

	public void setShortname(String shortname) {
		this.Shortname = shortname;
	}

	public String getShortname() {
		return Shortname;
	}

	public void setUsedbj(Integer usedbj) {
		this.Usedbj = usedbj;
	}

	public Integer getUsedbj() {
		return Usedbj;
	}

	public void setLocano(String locano) {
		this.Locano = locano;
	}

	public String getLocano() {
		return Locano;
	}

	public void setOnhouseid(Long onhouseid) {
		this.Onhouseid = onhouseid;
	}

	public Long getOnhouseid() {
		return Onhouseid;
	}

	public void setImagename1(String imagename1) {
		this.Imagename1 = imagename1;
	}

	public String getImagename1() {
		return Imagename1;
	}

	public void setPricetype(Integer pricetype) {
		this.Pricetype = pricetype;
	}

	public Integer getPricetype() {
		return Pricetype;
	}

	public void setPricetype1(Integer pricetype1) {
		this.Pricetype1 = pricetype1;
	}

	public Integer getPricetype1() {
		return Pricetype1;
	}

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// 删除记录
	public int Delete() {
		if (Houseid == 0) {
			errmess = "店铺id无效！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("select count(*) from (");
		strSql.append("\n  select id from wareinh  where accid=" + Accid + " and houseid=" + Houseid + " and statetag<>2 and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from wareouth where  accid=" + Accid + " and houseid=" + Houseid + " and statetag<>2 and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select a.id from shopsaleh a");
		strSql.append("\n  where  a.accid=" + Accid + " and statetag<>2 and a.houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and a.notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from allotinh where  accid=" + Accid + " and statetag<>2 and (houseid=" + Houseid + " or fromhouseid=" + Houseid + ") and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from allotouth where  accid=" + Accid + " and statetag<>2 and (houseid=" + Houseid + "  or tohouseid=" + Houseid + ") and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from warecheckh where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from tempcheckh where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from incomecurr where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from paycurr where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from incomecost where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n  union all");
		strSql.append("\n  select id from paycost where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");

		strSql.append("\n  union all");
		strSql.append("\n  select id from housecost where  accid=" + Accid + " and statetag<>2 and houseid=" + Houseid + " and rownum=1");
		strSql.append("\n  and notedate>=to_date('" + Accbegindate + "','yyyy-mm-dd')");
		strSql.append("\n )");
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "当前店铺已使用，不允许删除";
			return 0;
		}
		strSql.setLength(0);
		strSql.append(" update warehouse set statetag=2,lastop='" + Lastop + "',lastdate=sysdate");
		strSql.append(" where houseid=" + Houseid + " and accid=" + Accid);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "删除失败！";
			return 0;
		} else {
			errmess = "删除成功！";
			return 1;
		}
	}

	// 成批增加店铺
	public int AppendMulti(JSONObject jsonObject) {
		if (!jsonObject.has("housereclist")) {
			errmess = "housereclist未传入！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		// string shortname = "";
		// string housename = "";
		strSql.append("begin ");
		JSONArray jsonArray = jsonObject.getJSONArray("housereclist");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			Housename = jsonObject2.getString("housename");
			Shortname = PinYin.getPinYinHeadChar(Housename);
			strSql.append("\n   insert into warehouse (houseid,housename,shortname,accid,address,tel,statetag,noused,lastop,lastdate)");
			strSql.append("\n   values (warehouse_houseid.nextval,'" + Housename + "','" + Shortname + "'," + Accid + ",'','',1,0,'" + Lastop + "',sysdate); ");
		}
		strSql.append("\n    commit; ");
		strSql.append("\n end;");
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作异常！";
			return 0;
		} else {
			errmess = "操作成功！";
			return 1;
		}

	}

	// 增加记录
	public int Append(String pictjson) {
		if (Housename == null || Housename.length() <= 0) {
			errmess = "店铺名称不允许为空！";
			return 0;
		}
		if (Exists()) // 判断店铺名称是否已存在
		{
			// WriteResult(response, 0, dal.getErrmess());
			return 0;

		}
		if (pictjson != null && pictjson.length() > 0) {
			JSONObject jsonObject = JSONObject.fromObject(pictjson);
			if (jsonObject.has("twobarimage")) {
				String base64string = jsonObject.getString("twobarimage");
				String retcs = Func.Base64UpFastDFS(base64string);
				if (retcs.substring(0, 1).equals("1")) {
					Twobarimage = retcs.substring(1);
				}
			}
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("houseid,");
		strSql2.append("v_id,");
		// if (Houseid != null) {
		// strSql1.append("houseid,");
		// strSql2.append(Houseid + ",");
		// }
		// if (Housename != null) {
		strSql1.append("housename,");
		strSql2.append("'" + Housename + "',");
		Shortname = PinYin.getPinYinHeadChar(Housename);
		// }
		// if (Accid != null) {
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		// }
		if (Address != null) {
			strSql1.append("address,");
			strSql2.append("'" + Address + "',");
		}
		if (Tel != null) {
			strSql1.append("tel,");
			strSql2.append("'" + Tel + "',");
		}
		if (Noused != null) {
			strSql1.append("noused,");
			strSql2.append(Noused + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		if (Ontime != null) {
			strSql1.append("ontime,");
			strSql2.append("'" + Ontime + "',");
		}
		if (Offtime != null) {
			strSql1.append("offtime,");
			strSql2.append("'" + Offtime + "',");
		}
		if (Rent != null) {
			strSql1.append("rent,");
			strSql2.append(Rent + ",");
		}
		if (Aream2 != null) {
			strSql1.append("aream2,");
			strSql2.append(Aream2 + ",");
		}
		if (Lx != null) {
			strSql1.append("lx,");
			strSql2.append(Lx + ",");
		}
		if (Ly != null) {
			strSql1.append("ly,");
			strSql2.append(Ly + ",");
		}
		// if (Lastdate != null) {
		// strSql1.append("lastdate,");
		// strSql2.append("" + Lastdate + ",");
		// }
		if (Remark != null) {
			strSql1.append("remark,");
			strSql2.append("'" + Remark + "',");
		}
		if (Ontime1 != null) {
			strSql1.append("ontime1,");
			strSql2.append("'" + Ontime1 + "',");
		}
		if (Offtime1 != null) {
			strSql1.append("offtime1,");
			strSql2.append("'" + Offtime1 + "',");
		}
		if (Ontime2 != null) {
			strSql1.append("ontime2,");
			strSql2.append("'" + Ontime2 + "',");
		}
		if (Offtime2 != null) {
			strSql1.append("offtime2,");
			strSql2.append("'" + Offtime2 + "',");
		}
		if (Imagename != null) {
			strSql1.append("imagename,");
			strSql2.append("'" + Imagename + "',");
		}
		if (Pageview != null) {
			strSql1.append("pageview,");
			strSql2.append(Pageview + ",");
		}
		if (Agreeview != null) {
			strSql1.append("agreeview,");
			strSql2.append(Agreeview + ",");
		}
		if (Numview != null) {
			strSql1.append("numview,");
			strSql2.append(Numview + ",");
		}
		if (Shortname != null) {
			strSql1.append("shortname,");
			strSql2.append("'" + Shortname + "',");
		}
		if (Usedbj != null) {
			strSql1.append("usedbj,");
			strSql2.append(Usedbj + ",");
		}
		if (Locano != null) {
			strSql1.append("locano,");
			strSql2.append("'" + Locano + "',");
		}
		if (Onhouseid != null) {
			strSql1.append("onhouseid,");
			strSql2.append(Onhouseid + ",");
		}
		if (Imagename1 != null) {
			strSql1.append("imagename1,");
			strSql2.append("'" + Imagename1 + "',");
		}
		if (Twobarimage != null) {
			strSql1.append("Twobarimage,");
			strSql2.append("'" + Twobarimage + "',");
		}
		if (Pricetype != null) {
			strSql1.append("pricetype,");
			strSql2.append(Pricetype + ",");
		}
		if (Pricetype1 != null) {
			strSql1.append("pricetype1,");
			strSql2.append(Pricetype1 + ",");
		}
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append(" begin ");
		strSql.append("  v_id:=warehouse_houseid.nextval; ");
		strSql.append("  insert into warehouse (");
		strSql.append("  " + strSql1.toString());
		strSql.append(")");
		strSql.append(" values (");
		strSql.append("  " + strSql2.toString());
		strSql.append(");");
		if (Qxbj == 1)// 如果启用了权限控制，增加的店铺自动加入权限表
		{
			strSql.append("\n   insert into employehouse (id,epid,houseid)");
			strSql.append("\n   values (employehouse_id.nextval," + Userid + ",v_id); ");
		}
		strSql.append("  select v_id into :id from dual; ");
		strSql.append(" end;");
		//System.out.println(strSql.toString());
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("id", new ProdParam(Types.BIGINT));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret >= 0) {
			Houseid = Long.parseLong(param.get("id").getParamvalue().toString());
			errmess = Houseid.toString();
			return 1;
		} else {
			errmess = "增加失败！";
			return 0;
		}
	}

	// 修改记录
	public int Update(String pictjson) {

		if (Houseid == null || Houseid == 0) {
			errmess = "店铺id参数无效！";
			return 0;
		}
		if (Housename != null) {
			if (Housename.length() <= 0) {
				errmess = "店铺名称不允许为空！";
				return 0;
			}
			if (Exists()) // 判断店铺名称是否已存在
			{

				return 0;
			}
		}
		if (pictjson != null) {
			if (pictjson.length() > 0) {
				// Twobarimage = "";
				// else {
				JSONObject jsonObject = JSONObject.fromObject(pictjson);
				if (jsonObject.has("twobarimage")) {
					String base64string = jsonObject.getString("twobarimage");
					if (base64string.length() <= 0)
						Twobarimage = "";
					else {
						String retcs = Func.Base64UpFastDFS(base64string);
						if (retcs.substring(0, 1).equals("1")) {
							Twobarimage = retcs.substring(1);
						}
					}
				}
			}
		}

		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update warehouse set ");
		// if (Houseid != null) {
		// strSql.append("houseid=" + Houseid + ",");
		// }
		if (Housename != null) {
			strSql.append("housename='" + Housename + "',");
			Shortname = PinYin.getPinYinHeadChar(Housename);
		}
		if (Shortname != null) {
			strSql.append("shortname='" + Shortname + "',");
		}
		// if (Accid != null) {
		// strSql.append("accid=" + Accid + ",");
		// }
		if (Address != null) {
			strSql.append("address='" + Address + "',");
		}
		if (Tel != null) {
			strSql.append("tel='" + Tel + "',");
		}
		if (Noused != null) {
			strSql.append("noused=" + Noused + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
		}
		if (Ontime != null) {
			strSql.append("ontime='" + Ontime + "',");
		}
		if (Offtime != null) {
			strSql.append("offtime='" + Offtime + "',");
		}
		if (Rent != null) {
			strSql.append("rent=" + Rent + ",");
		}
		if (Aream2 != null) {
			strSql.append("aream2=" + Aream2 + ",");
		}
		if (Lx != null) {
			strSql.append("lx=" + Lx + ",");
		}
		if (Ly != null) {
			strSql.append("ly=" + Ly + ",");
		}
		if (Lastdate != null) {
			strSql.append("lastdate='" + Lastdate + "',");
		}
		if (Remark != null) {
			strSql.append("remark='" + Remark + "',");
		}
		if (Ontime1 != null) {
			strSql.append("ontime1='" + Ontime1 + "',");
		}
		if (Offtime1 != null) {
			strSql.append("offtime1='" + Offtime1 + "',");
		}
		if (Ontime2 != null) {
			strSql.append("ontime2='" + Ontime2 + "',");
		}
		if (Offtime2 != null) {
			strSql.append("offtime2='" + Offtime2 + "',");
		}
		if (Imagename != null) {
			strSql.append("imagename='" + Imagename + "',");
		}
		if (Twobarimage != null) {
			strSql.append("Twobarimage='" + Twobarimage + "',");
		}
		if (Pageview != null) {
			strSql.append("pageview=" + Pageview + ",");
		}
		if (Agreeview != null) {
			strSql.append("agreeview=" + Agreeview + ",");
		}
		if (Numview != null) {
			strSql.append("numview=" + Numview + ",");
		}
		if (Usedbj != null) {
			strSql.append("usedbj=" + Usedbj + ",");
		}
		if (Locano != null) {
			strSql.append("locano='" + Locano + "',");
		}
		if (Onhouseid != null) {
			strSql.append("onhouseid=" + Onhouseid + ",");
		}
		if (Imagename1 != null) {
			strSql.append("imagename1='" + Imagename1 + "',");
		}
		if (Pricetype != null) {
			strSql.append("pricetype=" + Pricetype + ",");
		}
		if (Pricetype1 != null) {
			strSql.append("pricetype1=" + Pricetype1 + ",");
		}
		strSql.append("lastdate=sysdate");
		strSql.append("  where houseid=" + Houseid + " and accid=" + Accid + ";");
		strSql.append("  commit;");
		strSql.append(" end;");
		//		LogUtils.LogDebugWrite("updatewarehouse", strSql.toString());
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
		strSql.append("select " + fieldlist + ",to_char(lx) as lx,to_char(ly) as ly");
		strSql.append(" FROM warehouse ");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist + ",to_char(lx) as lx,to_char(ly) as ly");
		strSql.append(" FROM warehouse a");
		if (!strWhere.equals("")) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 ok
	public Table GetTable(QueryParam qp) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM warehouse ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	// 获取分页数据 ok
	public Table GetTable(String qry) {
		// StringBuilder strSql = new StringBuilder();
		// strSql.append("select " + fieldlist);
		// strSql.append(" FROM warehouse ");
		// if (!strWhere.equals("")) {
		// strSql.append(" where " + strWhere);
		// }
		// qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qry);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from warehouse");
		strSql.append(" where housename='" + Housename + "' and statetag=1 and rownum=1 and accid=" + Accid);
		if (Houseid != null)
			strSql.append(" and houseid<>" + Houseid);
		if (DbHelperSQL.GetSingleCount(strSql.toString()) > 0) {
			errmess = "店铺:" + Housename + " 已存在，请重新输入！";
			return true;
		}
		return false;
	}

	// 保存线上店铺对应的实体店铺
	public int Append(JSONObject jsonObject) {
		if (Onhouseid == null && Onhouseid == 0) {
			errmess = "线上店铺参数无效";
			return 0;
		}
		if (!jsonObject.has("houselist")) {
			errmess = "未指定线上店铺对应的实体店参数！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();

		strSql.append("declare ");
		strSql.append("   v_id number; ");
		strSql.append("\n begin ");
		strSql.append("\n  v_id:=0;");
		// "typelist":[{"typeid":102,"value":1},{"typeid":101,"value":1},{"typeid":105,"value":0}]
		JSONArray jsonArray = jsonObject.getJSONArray("houselist");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			long houseid = Long.parseLong(jsonObject2.getString("houseid"));
			int value = Integer.parseInt(jsonObject2.getString("value"));
			if (value == 0) {
				strSql.append("\n  update warehouse set onhouseid=0 where accid=" + Accid + " and houseid=" + houseid + ";");
			} else {
				strSql.append("\n  update warehouse set onhouseid=" + Onhouseid + " where accid=" + Accid + " and houseid=" + houseid + ";");
			}
		}

		// strSql.append(" select count(*) into :count from ONLINE2TYPE where onhouseid=" + Onhouseid + ";");
		strSql.append("\n end;");
		int ret = DbHelperSQL.ExecuteSql(strSql.toString());
		if (ret < 0) {
			errmess = "操作异常！";
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
		String qry = "select houseid,housename,shortname,address,tel,noused,statetag,imagename,pricetype,pricetype1,remark,accid";
		qry += "\n from warehouse where accid=" + Accid;
		if (downdate.length() <= 0) {
			qry += " and statetag=1 and noused=0";
			headsql = " insert ";
		} else {
			qry += " and lastdate>=to_date('" + downdate + "','yyyy-mm-dd hh24:mi:ss')";
			isupdate = true; // 更改
			headsql = " replace ";
		}
		Table tb = new Table();
		//System.out.println(qry);
		tb = DbHelperSQL.GetTable(qry);

		if (!isupdate)

			sql.append("\n delete from warehouse where accid=" + Accid + ";");

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
			sql.append("\n " + headsql + " into warehouse (" + sql1 + " lastdate) values (" + sql2 + " datetime('now','localtime'));");
		}

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

}