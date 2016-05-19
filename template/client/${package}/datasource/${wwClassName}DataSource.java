package ${wwPackagePath}.client.${wwPackageName}.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.chinacreator.ecb.client.base.datasource.EcbDataSource;
import ${wwPackagePath}.client.${wwPackageName}.bean.${wwClassName}Bean;
import ${wwPackagePath}.client.${wwPackageName}.rpc.${wwClassName}RpcService;
import ${wwPackagePath}.client.${wwPackageName}.rpc.${wwClassName}RpcServiceAsync;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * ${wwClassInfo}数据源
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
public class ${wwClassName}DataSource extends 
					EcbDataSource<${wwClassName}Bean, ListGridRecord, ${wwClassName}RpcServiceAsync>{
	private static ${wwClassName}DataSource instance;

	private static Map<String, Object> params = new HashMap<String, Object>();

	private ${wwClassName}DataSource() {

	}
	
	public static ${wwClassName}DataSource getInstance() {
		if (instance == null) {
			instance = new ${wwClassName}DataSource();
		}
		return instance;
	}

	@Override
	public List<DataSourceField> getDataSourceFields() {

		List<DataSourceField> dataSourceFields = new ArrayList<DataSourceField>();
		
		//field.setPrimaryKey(true);
		<#list wwTable.columns as c>
		DataSourceTextField ${c.columnNameVec}Field = new DataSourceTextField("${c.columnNameLower}", "${c.columnNote}");	
		${c.columnNameVec}Field.setLength(20);
		dataSourceFields.add(${c.columnNameVec}Field);
		
		</#list>
		return dataSourceFields;
	}

	@Override
	public void copyValues(ListGridRecord from, ${wwClassName}Bean to) {
		<#list wwTable.columns as c>
		to.set${c.columnNameVecFistUpper}(from.getAttribute("${c.columnNameLower}"));		//${c.columnNote}
		</#list>
	}

	@Override
	public void copyValues(${wwClassName}Bean from, ListGridRecord to) {
		<#list wwTable.columns as c>
		to.setAttribute("${c.columnNameLower}",from.get${c.columnNameVecFistUpper}());		//${c.columnNote}
		</#list>
	}

	@Override
	public ${wwClassName}RpcServiceAsync getServiceAsync() {
		return GWT.create(${wwClassName}RpcService.class);
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public ${wwClassName}Bean getNewDataObjectInstance() {
		return new ${wwClassName}Bean();
	}

	@Override
	public Map<String, Object> getCondtionMap() {
		return params;
	}
	
}
