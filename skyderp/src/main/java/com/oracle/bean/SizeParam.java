/**
 * 
 */
package com.oracle.bean;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.netdata.db.DbHelperSQL;
import com.netdata.db.ProdParam;

/**
 * @author Administrator
 *
 */
public class SizeParam {
	// private long wareid;
	// private int sizenum = 10;
	private String sqlSum = "";
	private String sqlSize = "";

	public String getSqlSum() {
		return sqlSum;
	}

	public String getSqlSize() {
		return sqlSize;
	}

	/// <summary>
	/// 返回指定商品的尺码横向汇总sql语句
	/// </summary>
	/// <param name="wareid"></param>
	/// <returns></returns>
	// fs 0=普通，1=盘点，2=临时盘点,3=库存上下限报警
	public SizeParam(long wareid, int sizenum, int fs) {
		//System.out.println("qrySizesum begin");

		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("v_wareid", new ProdParam(Types.BIGINT, wareid, 1));// 输入参数
		param.put("v_sizenum", new ProdParam(Types.INTEGER, sizenum, 2));// 输入参数
		param.put("v_sqlsum", new ProdParam(Types.VARCHAR, 3)); // 输出参数
		param.put("v_sqlsize", new ProdParam(Types.VARCHAR, 4));// 输出参数
		String qry = "";

		if (fs == 1)
			qry = "{call p_qrysizesum1(?,?,?,?)}";
		else if (fs == 2)
			qry = "{call p_qrysizesum2(?,?,?,?)}";
		else if (fs == 3)
			qry = "{call p_qrysizesum3(?,?,?,?)}";
		else
			qry = "{call p_qrysizesum(?,?,?,?)}";

		int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
		if (ret >= 0) {
			// return 0;
			sqlSum = param.get("v_sqlsum").getParamvalue().toString();
			sqlSize = param.get("v_sqlsize").getParamvalue().toString();
			// return 1;
		}
	}

	// fs 0=普通，1=盘点，2=临时盘点
	public SizeParam(long wareid, int sizenum) {
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("v_wareid", new ProdParam(Types.BIGINT, wareid, 1));// 输入参数
		param.put("v_sizenum", new ProdParam(Types.INTEGER, sizenum, 2));// 输入参数
		param.put("v_sqlsum", new ProdParam(Types.VARCHAR, 3)); // 输出参数
		param.put("v_sqlsize", new ProdParam(Types.VARCHAR, 4));// 输出参数
		String qry = "{call p_qrysizesum(?,?,?,?)}";

		int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
		if (ret >= 0) {
			// return 0;
			sqlSum = param.get("v_sqlsum").getParamvalue().toString();
			sqlSize = param.get("v_sqlsize").getParamvalue().toString();
			// return 1;
		}
	}

	// 采购汇总，销售汇总产生尺码合计专用，根据tj处理退货
	public SizeParam(long wareid, int sizenum, String tj, int va) {
		Map<String, ProdParam> param = new HashMap<String, ProdParam>();
		param.put("v_wareid", new ProdParam(Types.BIGINT, wareid, 1));// 输入参数
		param.put("v_sizenum", new ProdParam(Types.INTEGER, sizenum, 2));// 输入参数
		param.put("v_tj", new ProdParam(Types.VARCHAR, tj, 3));// 输入参数
		param.put("v_va", new ProdParam(Types.INTEGER, va, 4));// 输入参数
		param.put("v_sqlsum", new ProdParam(Types.VARCHAR, 5)); // 输出参数
		param.put("v_sqlsize", new ProdParam(Types.VARCHAR, 6));// 输出参数
		String qry = "{call p_qrysizesumx(?,?,?,?,?,?)}";

		int ret = DbHelperSQL.ExecuteProcIn(qry, param); // 带输入参数
		if (ret >= 0) {
			// return 0;
			sqlSum = param.get("v_sqlsum").getParamvalue().toString();
			sqlSize = param.get("v_sqlsize").getParamvalue().toString();
			// return 1;
		}
	}

}
