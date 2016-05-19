package ${OPEN_PATH};

<#if OPEN_MUTILFLAG=='TRUE'>
import java.util.ArrayList;
import java.util.List;
</#if>
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chinacreator.ecb.adapter.${OPEN_ECBCODE}Adapter;
import com.chinacreator.ecb.xml.CPUBXmlParam;
<#if OPEN_MUTILFLAG=='TRUE'>
import ${OPEN_PATH}.bean.${OPEN_NAME}DetailOut;
</#if>
import ${OPEN_PATH}.bean.${OPEN_NAME}Out;
import ${OPEN_PATH}.response.${OPEN_NAME}Response;
import com.chinacreator.rop.annotation.NeedInSessionType;
import com.chinacreator.rop.annotation.ServiceMethod;
import com.chinacreator.rop.annotation.ServiceMethodBean;
import com.chinacreator.rop.response.BusinessErrorResponse;
import ${OPEN_PATH}.request.${OPEN_NAME}Request;

@ServiceMethodBean(version = "1.0")
public class ${OPEN_NAME}Service {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@ServiceMethod(method = "${OPEN_METHOD}", version = "1.0",needInSession = NeedInSessionType.NO)
	public Object query(${OPEN_NAME}Request request) {
		//request.getRopRequestContext().setMobileNum(request.getContent().getMobileNum());
		CPUBXmlParam out = null;
		${OPEN_ECBCODE}Adapter adapter = new ${OPEN_ECBCODE}Adapter();
		try {
			out = adapter.execute(request.getRopRequestContext(),
			<#list OPEN_FUSINGLEIN as s>
							request.getContent().get${s}()<#if s_has_next>,</#if>
			</#list>
			<#if OPEN_MUTILFLAGIN=='TRUE'>
							,
							request.getContent().getMultilpyList()
			</#if>
						);
			logger.debug(out.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resultCode = out.getCommonMap().get("BUSI_RETURN_CODE") == null ? ""
				: out.getCommonMap().get("BUSI_RETURN_CODE").toString();
		String resultInfo = out.getCommonMap().get("BUSI_RETURN_INFO") == null ? ""
				: out.getCommonMap().get("BUSI_RETURN_INFO").toString();
		if ("0".equals(resultCode)) {
			${OPEN_NAME}Response response = new ${OPEN_NAME}Response("00000","");
			fillBean(out, response);
			return response;
		} else {
			return new BusinessErrorResponse(request.getRopRequestContext(),resultCode,resultInfo);
		}
	}

	public void fillBean(CPUBXmlParam outParam, ${OPEN_NAME}Response response) {
		Map<String, String> single = outParam.getSingleMap();
		${OPEN_NAME}Out bean = new ${OPEN_NAME}Out();
		<#list OPEN_FUSINGLEOUT as s>
		bean.set${s}(single.get("${OPEN_ECBSINGLEOUT[s_index]}"));
		</#list>
		<#if OPEN_MUTILFLAG=='TRUE'>
		bean.setMultilpyList(get${OPEN_NAME}Detail(outParam.getMultilpyList()));
		</#if>
		response.setResult(bean);
	}
	
	<#if OPEN_MUTILFLAG=='TRUE'>
	public List<${OPEN_NAME}DetailOut> get${OPEN_NAME}Detail(List<Map> detailList) {
		List<${OPEN_NAME}DetailOut> list = new ArrayList<${OPEN_NAME}DetailOut>();
		for (int i = 0; i < detailList.size(); i++) {
			${OPEN_NAME}DetailOut bean = new ${OPEN_NAME}DetailOut();
			Map<String, String> map = detailList.get(i);
			<#list OPEN_MUTILOUT as s>
			String ${s} = map.get("${OPEN_ECBMUTILOUT[s_index]}") == null ? "" : map.get(
					"${OPEN_ECBMUTILOUT[s_index]}").toString();
			bean.set${OPEN_FUMUTILOUT[s_index]}(${s});
			</#list>
			list.add(bean);
		}
		return list;
	}
	</#if>
}
