package com.chinacreator.ecb.adapter;

import java.util.HashMap;
import com.chinacreator.ecb.CallEcb;
import com.chinacreator.ecb.xml.CPUBXmlParam;
import com.chinacreator.rop.RopRequestContext;
<#if OPEN_MUTILFLAGIN=='TRUE'>
import ${OPEN_PATH}.request.${OPEN_NAME}DetailIn;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
</#if>

/**
 * ${OPEN_METHODNAME}
 * @author ${author}
 *
 */
public class ${OPEN_ECBCODE}Adapter extends CommonAdapter{
	
	public CPUBXmlParam execute(RopRequestContext ropRequestContext,
			<#list OPEN_SINGLEIN as s>
							String ${s}<#if s_has_next>,</#if>
			</#list>
			<#if OPEN_MUTILFLAGIN=='TRUE'>
							,
							List<${OPEN_NAME}DetailIN> multilpyList
			</#if>
							) throws Exception{	
		CallEcb ws = new CallEcb();
		CPUBXmlParam in = new CPUBXmlParam();
		//组建公共输入参数，需传入业务接口编码和活动编码（普遍业务传“0”）
		HashMap<String,String> commPara = createCommParam("${OPEN_ECBCODE}","0",ropRequestContext);
		in.setCommonMap(commPara);
		//单行输入参数
		HashMap<String,String> singleParam = new HashMap<String,String>();
		<#list OPEN_SINGLEIN as s>
		singleParam.put("${OPEN_ECBSINGLEIN[s_index]}",${s});
		</#list>
		in.setSingleMap(singleParam);
		<#if OPEN_MUTILFLAGIN=='TRUE'>
		List<Map> list=new ArrayList<Map>();
		for(${OPEN_NAME}DetailIn detail : multilpyList){
			Map<String,String> map = new HashMap<String,String>();
			<#list OPEN_FUMUTILIN as s>
			map.put("${OPEN_ECBMUTILIN[s_index]}",detail.get${s}());
			</#list>
			list.add(map);	
		}
		in.setMultilpyList(list);
		</#if>
		return ws.call(in,ropRequestContext);
	}

}
