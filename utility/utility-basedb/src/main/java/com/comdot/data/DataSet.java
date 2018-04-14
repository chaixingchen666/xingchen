package com.comdot.data;

import java.util.*;

//Table的集合
public class DataSet {
	private String name;
	private Map<String, Table> tables;
	
	private Map<Integer, String> index$name;

	public Map<String, Table> getTables() {
		return tables;
	}

	public void setTables(Map<String, Table> tables) {
		this.tables = tables;
		
	}


	public Map<Integer, String> getIndex$name() {
		return index$name;
	}

	public void setIndex$name(Map<Integer, String> index$name) {
		this.index$name = index$name;
	}

	public DataSet() {
		if (tables == null) {
			tables = new HashMap<String, Table>();
		}
		if (index$name == null) {
			index$name = new HashMap<Integer, String>();
		}
	}

	public DataSet(String name) {
		if (tables == null) {
			tables = new HashMap<String, Table>();
		}
		if (index$name == null) {
			index$name = new HashMap<Integer, String>();
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Table getTable(String name) {
		return this.tables.get(name);
	}

	public Table getTable(int index) {
//		System.out.println("getTable ....");
		return this.getTable(this.index$name.get(index));
//		return this.getTable(index);
	}


}
