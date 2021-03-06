package com.entity;

public class Column {
	private String columnName;                  //列名			:id
	private String columnNameUpper;				//列名大写			:ID
	private String columnNameFistUpper;			//列名首字母大写		:Id
	private String columnType;					//列数据类型		:numeric() identity||varchar||datetime||int||numeric
	private String columnNote;					//列的备注			:主键id
	private String columnNameLower;				//列名小写			:id
	private String columnSize;					//列值大小			:8
	private String columnNameVec;			    //列名变量			: busi_code  ->busiCode
	private String columnNameVecFistUpper;		//列名变量			: busi_code  ->BusiCode
	
	public String getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}
	public String getColumnNameLower() {
		return columnNameLower;
	}
	public void setColumnNameLower(String columnNameLower) {
		this.columnNameLower = columnNameLower;
	}
	public String getColumnNote() {
		return columnNote;
	}
	public void setColumnNote(String columnNote) {
		this.columnNote = columnNote;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnNameUpper() {
		return columnNameUpper;
	}
	public void setColumnNameUpper(String columnNameUpper) {
		this.columnNameUpper = columnNameUpper;
	}
	public String getColumnNameFistUpper() {
		return columnNameFistUpper;
	}
	public void setColumnNameFistUpper(String columnNameFistUpper) {
		this.columnNameFistUpper = columnNameFistUpper;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnNameVec() {
		return columnNameVec;
	}
	public void setColumnNameVec(String columnNameVec) {
		this.columnNameVec = columnNameVec;
	}
	public String getColumnNameVecFistUpper() {
		return columnNameVecFistUpper;
	}
	public void setColumnNameVecFistUpper(String columnNameVecFistUpper) {
		this.columnNameVecFistUpper = columnNameVecFistUpper;
	}
	
	
}
