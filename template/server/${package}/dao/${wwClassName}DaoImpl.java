package ${wwPackagePath}.server.${wwPackageName}.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ${wwPackagePath}.client.${wwPackageName}.bean.${wwClassName}Bean;
import com.chinacreator.ecb.client.core.EcbConstants;
import com.chinacreator.ecb.server.base.dao.EcbDaoImpl;
import com.chinacreator.security.AccessControl;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.handle.RowHandler;
import com.frameworkset.common.tag.pager.ListInfo;

/**
 * ${wwClassInfo}底层处理实现类
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
public class ${wwClassName}DaoImpl extends EcbDaoImpl<${wwClassName}Bean>
			implements ${wwClassName}Dao{



	//log object
	private final static Logger log = Logger.getLogger(${wwClassName}DaoImpl.class);
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static String Sql_QueryForList=
		new StringBuffer().append(" SELECT <#list wwTable.columns as c>t1.${c.columnNameLower}<#if c_has_next>,</#if></#list>")
		.append(" FROM ${wwTable.tableNameUpper} t1")
		.append(" Where 1=1").toString();
	
	public ListInfo queryForPagedList(AccessControl accessControl, long offset, int pageSize,${wwClassName}Bean conidtion,String sortBy) throws Exception {
//		PreparedDBUtil preDBUtil = new PreparedDBUtil();
//		log.debug("初始化业务参数配置==查询SQL:"+Sql_QueryForList);
//		return preDBUtil.executeSelectForList( Sql_QueryForList, ErrorRule.class);


		ListInfo listInfo = new ListInfo(); 

		StringBuffer sql_full = new StringBuffer(Sql_QueryForList);
		StringBuffer sql_where = new StringBuffer();
		
		

		//Object express = null ;//filterCriteria.get("express");
		//Object errCode = null ;//filterCriteria.get("errCode");
		System.out.println(Sql_QueryForList);
		
		//where part
		//List<String> paramsList = new ArrayList<String>();
		//boolean hasParam = false;
//		if (express!=null && express.toString().trim().length()>0) {
//			sql_where.append(" And t1.EXPRESS like ?");
//			paramsList.add( "%"+express+"%");
//			hasParam = true;
//		}
//		if (errCode!=null && errCode.toString().trim().length()>0) {
//			sql_where.append(" And t1.ERR_CODE like ?");
//			paramsList.add( "%"+errCode+"%" );
//			hasParam = true;
//		}
		
		<#list wwTable.columns as c>
		String ${c.columnNameVec} = conidtion.get${c.columnNameVecFistUpper}();
		</#list>
		//where part
		List<String> paramsList = new ArrayList<String>();
		boolean hasParam = false;
		<#list wwTable.columns as c>
		if (${c.columnNameVec}!=null && ${c.columnNameVec}.trim().length()>0) {
			sql_where.append(" And t1.${c.columnNameLower} like ?");
			paramsList.add( "%"+${c.columnNameVec}+"%"  );
			hasParam = true;
		}
		</#list>
		
		if (hasParam) {
			sql_full.append( sql_where );
		}
		sql_full.append( super.getOrderbySql(sortBy) );
		
		//where part end
		
		
		PreparedDBUtil preDBUtil = new PreparedDBUtil();
		try
		{
			preDBUtil.preparedSelect(  EcbConstants.DEFAULT_DB_NAME, sql_full.toString(), offset, pageSize);
			int idx=1;
			for (String val : paramsList ) {
		        preDBUtil.setString(idx++, val );
			}
			preDBUtil.executePrepared();
			listInfo.setTotalSize(preDBUtil.getTotalSize());
			listInfo.setDatas(dbutilToLogList(preDBUtil));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return listInfo;
	
	}
	

	private List<${wwClassName}Bean> dbutilToLogList(PreparedDBUtil preDBUtil)
	{
		List<${wwClassName}Bean> list = new ArrayList<${wwClassName}Bean>();
		if (preDBUtil != null)
			try
			{
				for (int i = 0; i < preDBUtil.size(); i++)
				{
					${wwClassName}Bean bean = new ${wwClassName}Bean();  
					<#list wwTable.columns as c>
					bean.set${c.columnNameVecFistUpper}( preDBUtil.getString(i,"${c.columnNameLower}") );  //渠道编码 
					</#list>
					list.add(bean);
				}

			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		return list;
	}



	private static String Sql_CheckUniqueIndex="Select Rownum From ${wwTable.tableNameUpper} Where ${wwTable.columns[0].columnNameLower}=?";
	private static String Sql_InsertRecord=
		new StringBuffer().append(" INSERT INTO ${wwTable.tableNameUpper}( ")
		.append(" <#list wwTable.columns as c>${c.columnNameLower}<#if c_has_next>,</#if></#list>")
		.append(" )")
		.append(" values(")
		.append(" <#list wwTable.columns as c>?<#if c_has_next>,</#if></#list>")
		.append(" )").toString();
	@Override
	public ${wwClassName}Bean insert(${wwClassName}Bean data, Map<String, Object> params)
			throws Exception {

		PreparedDBUtil preDBUtil = new PreparedDBUtil();
		//检查是否唯一性
		preDBUtil.preparedSelect( EcbConstants.DEFAULT_DB_NAME, Sql_CheckUniqueIndex);
		preDBUtil.setString(1, data.get${wwTable.columns[0].columnNameVecFistUpper}());
		preDBUtil.executePrepared();
		if(preDBUtil.size()>0){
			log.warn("${wwClassInfo}编号已经存在了！");
			throw new RuntimeException("${wwClassInfo}编号已经存在了！");
		}
		
		//更新表达式
		preDBUtil.preparedUpdate(EcbConstants.DEFAULT_DB_NAME,  Sql_InsertRecord );
		int idx=1; 
		<#list wwTable.columns as c>
		preDBUtil.setString(idx++, data.get${c.columnNameVecFistUpper}()); 
		</#list>
		preDBUtil.executePrepared();
		return data;
	}

	private static String Sql_UpdateRecord=
		new StringBuffer().append(" UPDATE ${wwTable.tableNameUpper}")
		.append(" Set ")  
		.append(" <#list wwTable.columns as c><#if (c_index!=0)>${c.columnNameLower}=?<#if c_has_next>,</#if></#if></#list>")
		.append(" Where ${wwTable.columns[0].columnNameLower}=? ").toString();

	@Override
	public ${wwClassName}Bean update(${wwClassName}Bean data, Map<String, Object> params)
			throws Exception {

		PreparedDBUtil preDBUtil = new PreparedDBUtil();
		//更新表达式
		preDBUtil.preparedUpdate(  EcbConstants.DEFAULT_DB_NAME, Sql_UpdateRecord );
		int idx=1;  
		<#list wwTable.columns as c>
		<#if (c_index!=0)>
		preDBUtil.setString(idx++, data.get${c.columnNameVecFistUpper}()); 
		</#if>
		</#list>
		preDBUtil.setString(idx++, data.get${wwTable.columns[0].columnNameVecFistUpper}()); 
		preDBUtil.executePrepared();
		
		return data;
	}


	private static String Sql_DeleteRecord="Delete From ${wwTable.tableNameUpper} Where ${wwTable.columns[0].columnNameLower}=?"; // And CHANNEL_ACC=?
	@Override
	public void delete(${wwClassName}Bean data, Map<String, Object> params)
			throws Exception {

		PreparedDBUtil preDBUtil = new PreparedDBUtil();
		preDBUtil.preparedUpdate(  EcbConstants.DEFAULT_DB_NAME, Sql_DeleteRecord );
		int idx=1;
		preDBUtil.setString(idx++, data.get${wwTable.columns[0].columnNameVecFistUpper}());
		preDBUtil.executePrepared();
	} 

	@Override
	public List<${wwClassName}Bean> queryForList(AccessControl accessControl,${wwClassName}Bean conidtion,String sortBy)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	private static String Sql_QueryForMapList=
		new StringBuffer().append(" SELECT <#list wwTable.columns as c>t1.${c.columnNameLower}<#if c_has_next>,</#if></#list>")
		.append(" FROM ${wwTable.tableNameUpper} t1").toString();
	public List<${wwClassName}Bean> get${wwClassName}List() throws Exception {
		PreparedDBUtil preDBUtil = new PreparedDBUtil(); 
		preDBUtil.preparedSelect(  EcbConstants.DEFAULT_DB_NAME, Sql_QueryForMapList );  //
        //preDBUtil.setString(1, type );
        return preDBUtil.executePreparedForList(${wwClassName}Bean.class, new RowHandler<${wwClassName}Bean>(){
			@Override
			public void handleRow(${wwClassName}Bean rowValue, Record record)
								throws Exception {
				<#list wwTable.columns as c>
				rowValue.set${c.columnNameVecFistUpper}(record.getString("${c.columnNameLower}"));
				</#list>
			}
		});
    }

}
