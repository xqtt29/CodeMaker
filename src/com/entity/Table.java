package com.entity;

import java.util.List;

public class Table {
	private String tableName;			//表名
	private String tableNameUpper;		//表名大写
	private List<?> columns;				//表包含的列
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableNameUpper() {
		return tableNameUpper;
	}
	public void setTableNameUpper(String tableNameUpper) {
		this.tableNameUpper = tableNameUpper;
	}
	public List<?> getColumns() {
		return columns;
	}
	public void setColumns(List<?> columns) {
		this.columns = columns;
	}
	
}
