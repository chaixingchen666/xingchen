package com.oracle.bean;

// import java.io.UnsupportedEncodingException;
// import java.security.NoSuchAlgorithmException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.comdot.data.DataSet;
import com.comdot.data.Table;
import com.common.tool.Func;
import com.flyang.yht.SilverPassHttpsUtils;
import com.flyang.yht.ToolUtils;
import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;
import com.netdata.db.QueryParam;

import net.sf.json.JSONObject;

// import net.sf.json.JSONException;
// import net.sf.json.JSONObject;

// *************************************
// @author:sunhong @date:2017-09-06 23:21:51
// *************************************
public class Creditrecord implements java.io.Serializable {
	private Float Id;
	private Long Accid;
	private String Surname;
	private String Idno;
	private String Mobile;
	private Float Sqcurr;
	private Date Notedate;
	private Float Dkcurr;
	private Long Hkqs;
	private Integer Statetag;
	private String Lastop;
	private Date Lastdate;
	private String errmess;

	// private String Accbegindate = "2000-01-01"; // 账套开始使用日期
	public void setId(Float id) {
		this.Id = id;
	}

	public Float getId() {
		return Id;
	}

	public void setAccid(Long accid) {
		this.Accid = accid;
	}

	public Long getAccid() {
		return Accid;
	}

	public void setSurname(String surname) {
		this.Surname = surname;
	}

	public String getSurname() {
		return Surname;
	}

	public void setIdno(String idno) {
		this.Idno = idno;
	}

	public String getIdno() {
		return Idno;
	}

	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setSqcurr(Float sqcurr) {
		this.Sqcurr = sqcurr;
	}

	public Float getSqcurr() {
		return Sqcurr;
	}

	public void setNotedate(Date notedate) {
		this.Notedate = notedate;
	}

	public String getNotedate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(Notedate);
	}

	public void setDkcurr(Float dkcurr) {
		this.Dkcurr = dkcurr;
	}

	public Float getDkcurr() {
		return Dkcurr;
	}

	public void setHkqs(Long hkqs) {
		this.Hkqs = hkqs;
	}

	public Long getHkqs() {
		return Hkqs;
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

	public String getErrmess() { // 取错误提示
		return errmess;
	}

	// public void setAccbegindate(String accbegindate) {
	// this.Accbegindate = accbegindate;
	// }
	// 删除记录
	public int Delete() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("declare");
		strSql.append("\n   v_statetag number(1);");
		strSql.append("\n   v_retcs varchar2(200);");
		strSql.append("\n begin");
		strSql.append("\n   begin");
		strSql.append("\n     select statetag into v_statetag from CREDITRECORD where  id=" + Id + " and accid=" + Accid + ";");
		strSql.append("\n   EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n     v_retcs:='0未找到记录！';");
		strSql.append("\n     goto exit;");
		strSql.append("\n   end;");
		// statetag:0=申请，1=提交，2=审核通过 3=贷款中，4=已还款，7=异常 ，8=申请驳回,9=删除
		// strSql.append("\n if v_statetag>2 and v_statetag<6 then");
		strSql.append("\n   if v_statetag>0 then");
		strSql.append("\n      v_retcs:='0当前记录已在贷款中，不允许删除！';");
		strSql.append("\n      goto exit;");
		strSql.append("\n   end if;");
		strSql.append("\n   update CREDITRECORD set statetag=9  where id=" + Id + " and accid=" + Accid + ";");
		strSql.append("\n   v_retcs:='1操作成功！';");
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
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess = "删除失败！";
		// return 0;
		// } else {
		// errmess = "删除成功！";
		// return 1;
		// }
	}

	// 增加记录
	public int Append() {
		if (Func.isNull(Surname)) {
			errmess = "申请人姓名不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Idno)) {
			errmess = "身份证号不是一个有效值！";
			return 0;
		}
		if (Func.isNull(Mobile)) {
			errmess = "移动电话不能为空！";
			return 0;
		}
		if (!Func.isMobile(Mobile)) {
			errmess = "移动电话不是一个有效值！";
			return 0;
		}
		StringBuilder strSql = new StringBuilder();
		StringBuilder strSql1 = new StringBuilder();
		StringBuilder strSql2 = new StringBuilder();
		strSql1.append("id,");
		strSql2.append("v_id,");
		strSql1.append("accid,");
		strSql2.append(Accid + ",");
		strSql1.append("surname,");
		strSql2.append("'" + Surname + "',");
		strSql1.append("idno,");
		strSql2.append("'" + Idno + "',");
		strSql1.append("mobile,");
		strSql2.append("'" + Mobile + "',");
		if (Sqcurr != null) {
			strSql1.append("sqcurr,");
			strSql2.append("'" + Sqcurr + "',");
		}
		// if (Notedate != null) {
		strSql1.append("notedate,");
		strSql2.append("sysdate,");
		// }
		if (Dkcurr != null) {
			strSql1.append("dkcurr,");
			strSql2.append("'" + Dkcurr + "',");
		}
		if (Hkqs != null) {
			strSql1.append("hkqs,");
			strSql2.append(Hkqs + ",");
		}
		if (Statetag != null) {
			strSql1.append("statetag,");
			strSql2.append(Statetag + ",");
		}
		// if (Lastop != null) {
		strSql1.append("lastop,");
		strSql2.append("'" + Lastop + "',");
		// }
		strSql1.append("lastdate");
		strSql2.append("sysdate");
		strSql.append("declare ");
		strSql.append("\n   v_id number; ");
		strSql.append("\n   v_statetag number(1); ");
		strSql.append("\n   v_retcs varchar(200); ");
		strSql.append("\n begin ");
		strSql.append("\n    begin");
		strSql.append("\n      select id,statetag into v_id,v_statetag from CREDITRECORD where accid=" + Accid + " and surname='" + Surname + "' and idno='" + Idno + "' and statetag<=3;");
		// statetag:0=申请，1=提交，2=审核通过 3=贷款中，4=已还款，7=异常 ，8=申请驳回,9=删除
		strSql.append("\n    EXCEPTION WHEN NO_DATA_FOUND THEN");
		strSql.append("\n      v_statetag:=-1;");  // 未找到申请记录
		strSql.append("\n    end;");
		strSql.append("\n    if v_statetag>0 then ");
		strSql.append("\n       v_retcs:='0已有未结案贷款记录，请完成后再申请！';");
		strSql.append("\n       goto exit;");
		strSql.append("\n    end if;");
		// statetag:0=申请，1=提交，2=审核通过 3=贷款中，4=已还款，7=异常 ，8=申请驳回,9=删除
		strSql.append("\n    if v_statetag<0 then");// 未找到申请记录 ，则生成申请记录
		strSql.append("\n       v_id:=CREDITRECORD_id.nextval; ");
		strSql.append("\n       insert into CREDITRECORD (" + strSql1.toString() + ")");
		strSql.append("\n       values (" + strSql2.toString() + ");");
		strSql.append("\n    end if;");
		strSql.append("\n    v_retcs:='1'||v_id;");
		strSql.append("\n    <<exit>>");
		strSql.append("\n    select v_retcs into :retcs from dual; ");
		strSql.append("\n end;");
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("retcs", new ProdParam(Types.VARCHAR));
		int ret = DbHelperSQL.ExecuteProc(strSql.toString(), param);
		if (ret < 0) {
			errmess = "操作异常1！";
			return 0;
		}
		String retcs = param.get("retcs").getParamvalue().toString();
		ret = Integer.parseInt(retcs.substring(0, 1));
		if (ret == 0) {
			errmess = retcs.substring(1);
			// System.out.println(retcs);
			return 0;
		}
		Id = Float.parseFloat(retcs.substring(1));
		errmess = "\"id\":\"" + Id + "\"";
		// System.out.println("begin....1");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("certNo", Idno);
		map.put("certType", "101");
		map.put("custName", Surname);
		map.put("mobile", Mobile);
		// 客户准入申请
		// System.out.println("begin....2");
		JSONObject resobj = SilverPassHttpsUtils.httpPost("/cust/access.do", map);
		// System.out.println("1111:" + resobj.toString());
		JSONObject body = JSONObject.fromObject(resobj.getString("body"));
		String code = body.getString("code");
		if (code.equals("9999")) { // 不成功
			// errmess += ",\"code\":\"" + code + "\"" //
			// + ",\"message:" + body.getString("message") + "\"";
			errmess = "客户准入失败:" + body.getString("message");
			// 删除申请记录
			strSql.setLength(0);
			strSql.append("delete from CREDITRECORD  where id=" + Id);
			if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
				errmess += ";操作异常2！";
				// return 0;
			}
			return 0;
		}
		errmess += ",\"code\":\"" + code + "\"";

		// // 如果成功，进行贷款申请
		// resobj = SilverPassHttpsUtils.httpPost("/loan/application.do",
		// map);
		// // System.out.println("1111:" + resobj.toString());
		// body = JSONObject.fromObject(resobj.getString("body"));
		// code = body.getString("code");
		// if (!code.equals("0000")) { // 不成功
		// // errmess += ",\"code\":\"" + code + "\"" //
		// // + ",\"message:" + body.getString("message") + "\"";
		// errmess = "贷款申请失败:" + body.getString("message");
		// // 删除申请记录
		// strSql.setLength(0);
		// strSql.append("delete from CREDITRECORD where id=" + Id);
		// if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
		// errmess += ";操作异常3！";
		// // return 0;
		// }
		// return 0;
		// }
		// 更改申请提交标志为1，
		strSql.setLength(0);
		// statetag:0=申请，1=提交（准入），2=审核通过，3=贷款，4=已还款，9=申请驳回
		strSql.append("update CREDITRECORD set statetag=1 where id=" + Id);
		if (DbHelperSQL.ExecuteSql(strSql.toString()) < 0) {
			errmess = "操作异常3！";
			return 0;
		}
		// errmess += "\"code\":\"" + code + "\"";

		return ret;
		// -------------------------------------------------------

	}

	// 修改记录
	public int Update() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("begin ");
		strSql.append("   update CREDITRECORD set ");
		if (Id != null) {
			strSql.append("id='" + Id + "',");
		}
		if (Accid != null) {
			strSql.append("accid=" + Accid + ",");
		}
		if (Surname != null) {
			strSql.append("surname='" + Surname + "',");
		}
		if (Idno != null) {
			strSql.append("idno='" + Idno + "',");
		}
		if (Mobile != null) {
			strSql.append("mobile='" + Mobile + "',");
		}
		if (Sqcurr != null) {
			strSql.append("sqcurr='" + Sqcurr + "',");
		}
		if (Notedate != null) {
			strSql.append("notedate='" + Notedate + "',");
		}
		if (Dkcurr != null) {
			strSql.append("dkcurr='" + Dkcurr + "',");
		}
		if (Hkqs != null) {
			strSql.append("hkqs=" + Hkqs + ",");
		}
		if (Statetag != null) {
			strSql.append("statetag=" + Statetag + ",");
		}
		if (Lastop != null) {
			strSql.append("lastop='" + Lastop + "',");
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
		strSql.append(" FROM CREDITRECORD ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		return DbHelperSQL.Query(strSql.toString());
	}

	// 获取分页数据
	public Table GetTable(QueryParam qp, String strWhere, String fieldlist) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select " + fieldlist);
		strSql.append(" FROM CREDITRECORD ");
		if (strWhere.length() > 0) {
			strSql.append(" where " + strWhere);
		}
		qp.setQueryString(strSql.toString());
		return DbHelperSQL.GetTable(qp);
	}

	public boolean Exists() {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select count(*) as num  from CREDITRECORD");
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

	// 查询贷款信息
	public int GetInfo() {
		if (Func.isNull(Surname) || Func.isNull(Idno) || Func.isNull(Mobile)) {
			errmess = "参数无效！";
			return 0;
		}

		return 1;
	}

	// 客户贷款信息列表
	public int doLoaninformation() {
		if (Func.isNull(Surname)) {
			errmess = "\"code\":\"9001\",\"message\":\"申请人姓名不是一个有效值！\"";
			return 0;
		}
		if (Func.isNull(Idno)) {
			errmess = "\"code\":\"9001\",\"message\":\"身份证号不是一个有效值！\"";
			return 0;
		}
		if (Func.isNull(Mobile) || !Func.isMobile(Mobile)) {
			errmess = "\"code\":9001\",\"message\":\"移动电话不是一个有效值！\"";
			return 0;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("certNo", Idno);// 330127199302122736
		map.put("certType", "101");
		map.put("custName", Surname);// 朱道真
		map.put("mobile", Mobile);// 17826871520
		// 客户准入申请
		//System.out.println("begin");
		JSONObject resobj = SilverPassHttpsUtils.httpPost("/loan/information.do", map);
		//		{
		//		    "body": {
		//		        "code": "0000",
		//		        "message": "请求成功",
		//		        "result": {
		//		            "certNo": "330127199302122736",
		//		            "certType": "101",
		//		            "custName": "朱道真",
		//		            "data": [
		//		                {
		//		                    "declareCode": "RZXZ201707190252",
		//		                    "status": "APPLY"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201707180170",
		//		                    "status": "SIGN_IN"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201707180169",
		//		                    "status": "LOAN_IN"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201707310399",
		//		                    "status": "APPLY"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201707200294",
		//		                    "status": "APPLY"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201708290609",
		//		                    "status": "OVER"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201707190256",
		//		                    "status": "AUDIT"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201708080336",
		//		                    "status": "APPLY"
		//		                },
		//		                {
		//		                    "declareCode": "RZXZ201708030210",
		//		                    "status": "APPLY"
		//		                }
		//		            ],
		//		            "mobile": "17826871520"
		//		        }
		//		    },
		//		    "head": {
		//		        "appKey": "123e51b3eecf458a9ba4591b9542ee53",
		//		        "contentType": "json",
		//		        "deviceId": "118.178.229.131",
		//		        "retCode": "0000",
		//		        "retMsg": "请求成功",
		//		        "timestamp": 1507532131811
		//		    }
		//		}		
		System.out.println("resobj=" + resobj.toString());

		JSONObject body = JSONObject.fromObject(resobj.getString("body"));
		System.out.println("body=" + body.toString());

		//		JSONObject result = JSONObject.fromObject(body.getString("result"));
		//		System.out.println("result=" + result.toString());
		//		JSONObject data = JSONObject.fromObject(result.getString("data"));
		//		System.out.println("data=" + data.toString());
		String code = body.getString("code");
		if (!code.equals("0000")) { // 不成功
			errmess = body.toString();// body.getString("message");
			return 0;
		}
		errmess = body.toString();

		return 1;
	}

}