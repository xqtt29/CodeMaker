package com.base;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.entity.Column;
import com.entity.Table;

public class DateBase {
	private String datebase;    //数据库，标识是sql server还是oracle
	private String url;
	private String driver;
	private String pass;
	private String user;
	private ResultSet rs;
	private Statement dms;
	private Logger log;
	/**
	 * @desciption 初始化数据库配置信息
	 */
	public void init(String url,String driver,String user,String pass,String path){
		this.datebase=url.indexOf("thin")>0?"oracle":"sqlserver";
		this.url=url;
		this.driver=driver;
		this.user=user;
		this.pass=pass;
		log=new Logger(path);
	}
	/**
	 * @desciption 获取数据库连接
	 */
	private Connection getConn(){
		Connection conn=null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}		
		return conn;
	}
	/**
	 * @desciption 查询SQL
	 * @param String sql
	 * @return map<String,List<String>>
	 */
	public List<Map<String,String>> executeQuery(String sql){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> tmap;
		try {
			dms = getConn().createStatement();
			rs = dms.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				tmap = new HashMap<String,String>();
				for (int i = 1; i <= metaData.getColumnCount(); ++i) {
					tmap.put(metaData.getColumnLabel(i),rs.getString(metaData.getColumnLabel(i)));
				}
				list.add(tmap);
			}
			dms.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}	
		return list;
	}
	/**
	 * @desciption 获取指定表的字段详细信息
	 * @param String name
	 * @return Table
	 */
	public Table getTable(String name){
		Table t=new Table();
		ResultSet rss;
		try {
			dms=getConn().createStatement();
			if("sqlserver".equals(datebase)){
				name=name.toLowerCase();
				rs=dms.executeQuery("select objname as column_name, cast(value as varchar(100)) as comments FROM ::fn_listextendedproperty (NULL, 'user', 'dbo', 'table', '"+name+"', 'column', default)");
			}
			else{
				name=name.toUpperCase();
				rs=dms.executeQuery("SELECT comments,column_name FROM user_col_comments WHERE table_name='"+name+"'");
			}
			DatabaseMetaData dbf=getConn().getMetaData();
			rss=dbf.getColumns(null, null, name, null);
			t.setTableName(name.toLowerCase());
			t.setTableNameUpper(name.toUpperCase());
			List<Column> list = new ArrayList<Column>();
			while (rs.next()) {
				Column c = new Column();
				if(rss.next()){
					c.setColumnSize(rss.getString("COLUMN_SIZE"));
					c.setColumnType(rss.getString("TYPE_NAME"));
				}else{
					c.setColumnSize("");
					c.setColumnType("");
				}
				c.setColumnNote(rs.getString("COMMENTS")==null?"":rs.getString("COMMENTS"));
				c.setColumnName(rs.getString("COLUMN_NAME").toLowerCase());
				c.setColumnNameLower(c.getColumnName().toLowerCase());
				c.setColumnNameFistUpper(firstUpper(c.getColumnName()));
				c.setColumnNameUpper(c.getColumnName().toUpperCase());
				StringBuffer sb=new StringBuffer();
				if(c.getColumnName().contains("_")){
					String[] str=c.getColumnName().split("_");
					for(String temp : str){
						sb.append(firstUpper(temp));
					}
					c.setColumnNameVec(firstLower(sb.toString()));
					c.setColumnNameVecFistUpper(sb.toString());
				}
				else{
					c.setColumnNameVec(c.getColumnName());
					c.setColumnNameVecFistUpper(firstUpper(c.getColumnName()));
				}
				list.add(c);
			}
			t.setColumns(list);
			rss.close();
			dms.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return t;
	}
	private String firstUpper(String str){
		return str.substring(0, 1)
		.toUpperCase()
		+ str.substring(1,
				str.length());
	}
	private String firstLower(String str){
		return str.substring(0, 1)
		.toLowerCase()
		+ str.substring(1,
				str.length());
	}
}
