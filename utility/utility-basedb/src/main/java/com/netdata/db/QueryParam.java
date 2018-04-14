package com.netdata.db;

public class QueryParam {

	private int PageIndex = 1;
	private int PageSize = 10;
	private String QueryString = "";
	private String SortString = "";
//	private String GroupString = "";

	private String SumString = "";
	private String TotalString = "";

	private String Calcfield = "";

//	public String getGroupString() {
//		return GroupString;
//	}
//
//	public void setGroupString(String groupString) {
//		GroupString = groupString;
//	}

	public String getCalcfield() {
		return Calcfield;
	}

	public void setCalcfield(String calcfield) {
		Calcfield = calcfield;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public String getQueryString() {
		return QueryString;
	}

	public void setQueryString(String queryString) {
		QueryString = queryString;
	}

	public String getSortString() {
		return SortString;
	}

	public void setSortString(String sortString) {
		SortString = sortString;
	}

	public String getSumString() {
		return SumString;
	}

	public void setSumString(String sumString) {
		SumString = sumString;
	}

	public String getTotalString() {
		return TotalString;
	}

	public void setTotalString(String totalString) {
		TotalString = totalString;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public QueryParam() { // 传输入参数

	}
	
	public QueryParam(int pageIndex, int pageSize) { // 传输入参数
		PageIndex = pageIndex;
		PageSize = pageSize;
	}

	public QueryParam(int pageIndex, int pageSize, String sortString) { // 传输入参数
		PageIndex = pageIndex;
		PageSize = pageSize;
		SortString = sortString;
	}

	public QueryParam(int pageIndex, int pageSize, String sortString, String queryString) { // 传输入参数
		PageIndex = pageIndex;
		PageSize = pageSize;
		SortString = sortString;
		QueryString = queryString;
	}

	// public int Count;

}
