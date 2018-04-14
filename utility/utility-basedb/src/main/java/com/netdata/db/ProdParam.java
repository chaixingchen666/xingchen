package com.netdata.db;

//import java.sql.Types;

public class ProdParam {

	private int Paramtype;
	private Object Paramvalue;
	private int Iotype;
	private int Ordid = 0;

	public int getOrdid() {
		return Ordid;
	}

	public void setOrdid(int ordid) {
		Ordid = ordid;
	}

	public ProdParam(int paramtype) {// 传输出参数

		Paramtype = paramtype;
		Iotype = 1; // 0=输入，1=输出
		Paramvalue = "";
	}

	public ProdParam(int paramtype, Object paramvalue) { // 传输入参数

		Paramtype = paramtype;
		Paramvalue = paramvalue;
		Iotype = 0; // 0=输入，1=输出
	}

	public ProdParam(int paramtype, int ordid) {// 传输出参数
		Ordid = ordid;
		Paramtype = paramtype;
		Iotype = 1; // 0=输入，1=输出
		Paramvalue = "";
	}

	public ProdParam(int paramtype, Object paramvalue, int ordid) { // 传输入参数

		Ordid = ordid;
		Paramtype = paramtype;
		Paramvalue = paramvalue;
		Iotype = 0; // 0=输入，1=输出
	}

	public int getIotype() {
		return Iotype;
	}

	public void setIotype(int iotype) {
		Iotype = iotype;
	}

	public void setParamtype(int paramtype) {
		Paramtype = paramtype;
	}

	public int getParamtype() {
		return Paramtype;
	}

	public Object getParamvalue() {
		return Paramvalue;
	}

	public void setParamvalue(Object paramvalue) {
		Paramvalue = paramvalue;
	}
	// public String getParamkey() {
	// return Paramkey;
	// }
	//
	// public void setParamkey(String paramkey) {
	// Paramkey = paramkey;
	// }

}
