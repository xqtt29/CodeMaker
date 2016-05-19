package ${OPEN_PATH}.request;
<#if OPEN_MUTILFLAGIN=='TRUE'>
import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
</#if>
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "content")
public class ${OPEN_NAME}In{
	<#list OPEN_SINGLEIN as s>
	private String ${s};// ${OPEN_SINGLENOTEIN[s_index]}
	
	</#list>
	<#list OPEN_FUSINGLEIN as s>
	public String get${s}() {
		return ${OPEN_SINGLEIN[s_index]};
	}
	
	public void set${s}(String ${OPEN_SINGLEIN[s_index]}) {
		this.${OPEN_SINGLEIN[s_index]} = ${OPEN_SINGLEIN[s_index]};
	}
	
	</#list>
	<#if OPEN_MUTILFLAGIN=='TRUE'>
	@XmlElementWrapper(name = "multilpyList")
	private List<${OPEN_NAME}DetailIn> multilpyList;
	
	public List<${OPEN_NAME}DetailIn> getMultilpyList() {
		return multilpyList;
	}

	public void setMultilpyList(List<${OPEN_NAME}DetailIn> multilpyList) {
		this.multilpyList = multilpyList;
	}
	</#if>
}
