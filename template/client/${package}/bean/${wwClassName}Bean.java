package ${wwPackagePath}.client.${wwPackageName}.bean;

import java.io.Serializable;

/**
 * ${wwClassInfo}java BEAN
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
public class ${wwClassName}Bean implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	//table ${wwTable.tableNameUpper}
	<#list wwTable.columns as c>
	private String ${c.columnNameVec};		//${c.columnNote}
	</#list>
	
	<#list wwTable.columns as c>
	public void set${c.columnNameVecFistUpper}(String ${c.columnNameVec}){
		this.${c.columnNameVec}=${c.columnNameVec};
	}
	public String get${c.columnNameVecFistUpper}(){
		return this.${c.columnNameVec};
	}
	</#list>
	
}
