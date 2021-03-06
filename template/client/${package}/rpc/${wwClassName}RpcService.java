package ${wwPackagePath}.client.${wwPackageName}.rpc;

import java.util.List;
import com.chinacreator.ecb.client.base.rpc.EcbRpcService;
import ${wwPackagePath}.client.${wwPackageName}.bean.${wwClassName}Bean;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * ${wwClassInfo}Rpc请求接口
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
@RemoteServiceRelativePath("ecb/servlet/${wwClassName}RpcService")
public interface ${wwClassName}RpcService extends EcbRpcService<${wwClassName}Bean> {
	public List<${wwClassName}Bean> get${wwClassName}List();
}
