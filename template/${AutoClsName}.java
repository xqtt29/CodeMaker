package ${autoClsPath};

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.chinacreator.ecb.bus.exception.InsideBusException;
import com.chinacreator.ecb.dao.TbBusBusiparamDM;
import com.chinacreator.ecb.pub.common.CPUBXmlParam;
import com.chinacreator.ecb.pub.common.Global;
/**
 * @Description
 * 	${AutoClsNote}
 <#list clsMethodName as cc>
INSERT INTO TB_BUS_INSIDECONFIG (INF_BUSI_CODE,INF_BUSI_NAME,INF_BUSI_CLASS,INF_BUSI_METHOD,STATE,CREATE_TIME,CREATE_MAN,REMARK) 
SELECT '${cc}','验证${clsMethodNote[cc_index]}','${autoClsPath}.${AutoClsName}',
'${cc}','0',''||TO_CHAR(SYSDATE,'yyyyMMdd')||'','${author}','${clsMethodNote[cc_index]}'
FROM DUAL;
 </#list>
 * @Author ${author}
 * @Datetime ${sysdate}
 * @Version
 * @Copyright (c) 2014 湖南科创信息技术股份有限公司
 */
public class ${AutoClsName} {
	private static Logger log = Logger.getLogger(${AutoClsName}.class);
	<#list clsMethodName as cc>
	
	/**
	 * ${clsMethodNote[cc_index]}
	 * @param inParam
	 * @param outParam
	 * @return
	 * @throws InsideBusException
	 */
	public CPUBXmlParam ${cc}(CPUBXmlParam inParam,CPUBXmlParam outParam) throws InsideBusException {
		Map commMap=inParam.getCommonMap();
		String busi_code=commMap.get(Global.BUSINESS_CODE).toString();
		try {
			String parmValue = TbBusBusiparamDM.getInstance().getBusiParamValue(
					busi_code, Global.QUERY_CODE);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("异常 ${cc},InsideBusException："+e.getMessage());
			throw new InsideBusException("异常 ${cc},InsideBusException："+e.getMessage());
		}
		return inParam;
	}
	</#list>
}
