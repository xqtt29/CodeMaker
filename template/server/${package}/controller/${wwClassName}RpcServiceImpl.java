package ${wwPackagePath}.server.${wwPackageName}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.chinacreator.common.client.Constants;
import com.chinacreator.common.client.datasource.GenericGwtRpcList;
import ${wwPackagePath}.client.${wwPackageName}.bean.${wwClassName}Bean;
import ${wwPackagePath}.client.${wwPackageName}.rpc.${wwClassName}RpcService;
import com.chinacreator.ecb.server.base.controller.EcbRpcServiceImpl;
import ${wwPackagePath}.server.${wwPackageName}.dao.${wwClassName}Dao;
import ${wwPackagePath}.server.${wwPackageName}.dao.${wwClassName}DaoImpl;
import com.chinacreator.logmanager.bean.Log;
import com.frameworkset.util.ListInfo;

/**
 * ${wwClassInfo}Rpc服务
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
public class ${wwClassName}RpcServiceImpl extends EcbRpcServiceImpl<${wwClassName}Bean>
		 implements ${wwClassName}RpcService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//log object
	private final static Logger log = Logger.getLogger(${wwClassName}RpcServiceImpl.class);
	
	/* 添加 服务适配器
	 * 
	 */
	@Override
	public  ${wwClassName}Bean add(${wwClassName}Bean data,Map<String,Object> params) throws Exception {
		params = addloginedUserInfo(params);
		try {
			${wwClassName}Dao dao = new ${wwClassName}DaoImpl();
			dao.insert(data, params);
			//记录日志
			addLog("${wwClassInfo}配置","新增${wwClassInfo},${wwClassInfo}编码："+data.get${wwTable.columns[0].columnNameVecFistUpper}(),Log.INSERT_OPER_TYPE);
			return data;			
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/* 修改服务适配器
	 * 
	 */
	@Override
	public ${wwClassName}Bean update(${wwClassName}Bean data,Map<String,Object> params) throws Exception {
		params = addloginedUserInfo(params);
		try {
			${wwClassName}Dao dao = new ${wwClassName}DaoImpl();
			dao.update(data, params);
			//记录日志
			addLog("${wwClassInfo}配置","修改${wwClassInfo},${wwClassInfo}编码："+data.get${wwTable.columns[0].columnNameVecFistUpper}(),Log.UPDATE_OPER_TYPE);
			
					
			return data;			
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	/* 删除服务适配器
	 * 
	 */
	@Override
	public void remove(${wwClassName}Bean data) throws Exception {
		
		try {
			${wwClassName}Dao dao = new ${wwClassName}DaoImpl();
			dao.delete(data, null);
			//记录日志
			addLog("${wwClassInfo}配置","删除${wwClassInfo},${wwClassInfo}编码："+data.get${wwTable.columns[0].columnNameVecFistUpper}(),Log.DELETE_OPER_TYPE);
			
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		
	}
	
	/* 条件分页查询的记录
	 * @param startRow 分页开始行
	 * @param pageSize 每月显示记录数
	 * @param sortBy	查询排序方式
	 * @param filterCriteria	查询条件
	 * @param map	额外查询条件，用的不多
	 * @return	 如果startRow不为null，则返回符合条件的该页数据，否则返回符合条件的所有数据
	 */
	@Override
	public List<${wwClassName}Bean> fetch(Integer startRow, Integer pageSize, String sortBy,
			Map<String, Object> filterCriteria, Map<String, Object> map)
			throws Exception {

		try {
			GenericGwtRpcList<${wwClassName}Bean> outList = new GenericGwtRpcList<${wwClassName}Bean>();
			
				if(startRow == null){
					startRow = 0;
				}		
				if(pageSize == null){
					pageSize = Constants.PAGE_MAX;
				}
				
				//将过滤条件map转换为Bean
				${wwClassName}Bean condition = parseCondition(filterCriteria, ${wwClassName}Bean.class);
				
				${wwClassName}Dao dao = new ${wwClassName}DaoImpl();
				ListInfo listInfo = dao.queryForPagedList(null, startRow,pageSize, condition, sortBy);
				outList.setTotalRows((int) listInfo.getTotalSize());
				outList.addAll(listInfo.getDatas());
		
			return outList;		
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
	

	@Override
	public List<${wwClassName}Bean> get${wwClassName}List() {
		${wwClassName}Dao ued = new ${wwClassName}DaoImpl();
		try {
			return ued.get${wwClassName}List();
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	
}
