package ${classPath};

/**
 * @desciption ${classNote}  
 */
import java.io.Serializable;

public class ${ClassName} implements Serializable{
	private static final long serialVersionUID = -1;
	
	//table ${table.tableNameUpper}
	<#list table.columns as c>
	private String ${c.columnNameLower};		//${c.columnNote}
	</#list>
	
	<#list table.columns as c>
	public void set${c.columnNameFistUpper}(String ${c.columnNameLower}){
		this.${c.columnNameLower}=${c.columnNameLower};
	}
	public String get${c.columnNameFistUpper}(){
		return this.${c.columnNameLower};
	}
	</#list>
}
