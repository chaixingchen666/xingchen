package com.comdot.data;

import java.util.*;

//表头，每个行都有其的引用
public class Meta {

	private Map<String, Column> columns;
	private Map<Integer, String> index$name;

	public Map<String, Column> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}

	public Map<Integer, String> getIndex$name() {
		return index$name;
	}

	public void setIndex$name(Map<Integer, String> index$name) {
		this.index$name = index$name;
	}

	public Meta() {
		columns = new HashMap<String, Column>();
		index$name = new HashMap<Integer, String>();
	}

	public Column get(String name) {
		//System.out.print("\n metaget  name="+name);

		//		System.out.print("\n metaget  name="+name+"\n columns="+columns.toString()+"\n  columns.get(name)="+columns.get(name));

		return columns.get(name);
	}

	public Column get(int index) {
		return columns.get(index$name.get(index));
	}

	//	public String get1(int index){
	//		return index$name.get(index);
	//	}
	//	public Column get(int index){
	//		//return columns.get(index$name.get(index));
	//		return index$name.get(index);
	//	}

	public void addColumn(int index, Column col) {

		this.index$name.put(index, col.getName());
		this.columns.put(col.getName(), col);
		//System.out.println("addColumn="+this.get(index));
	}

	public int getCount() {
		//System.out.println("getCount 1");
		return columns.size();
	}
}
