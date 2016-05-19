package ${wwPackagePath}.client.${wwPackageName}.rpc;

import java.util.List;
import com.chinacreator.ecb.client.base.rpc.EcbRpcServiceAsync;
import ${wwPackagePath}.client.${wwPackageName}.bean.${wwClassName}Bean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * ${wwClassInfo}Rpc异步请求接口
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
public interface ${wwClassName}RpcServiceAsync  extends EcbRpcServiceAsync<${wwClassName}Bean> {
	void get${wwClassName}List(AsyncCallback<List<${wwClassName}Bean>> asyncCallback);
}
