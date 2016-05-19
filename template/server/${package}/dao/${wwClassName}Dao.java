package ${wwPackagePath}.server.${wwPackageName}.dao;

import java.util.List;
import ${wwPackagePath}.client.${wwPackageName}.bean.${wwClassName}Bean;
import com.chinacreator.ecb.server.base.dao.EcbDao;

/**
 * ${wwClassInfo}底层处理接口
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
public interface ${wwClassName}Dao extends EcbDao<${wwClassName}Bean> {

	 List<${wwClassName}Bean> get${wwClassName}List() throws Exception;
}
