package ${OPEN_PATH}.bean;
<#if OPEN_MUTILFLAG=='TRUE'>
import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
</#if>
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class ${OPEN_NAME}Out{
	<#list OPEN_SINGLEOUT as s>
	@XmlElement
	private String ${s};// ${OPEN_SINGLENOTE[s_index]}
	
	</#list>
	<#list OPEN_FUSINGLEOUT as s>
	public String get${s}() {
		return ${OPEN_SINGLEOUT[s_index]};
	}
	
	public void set${s}(String ${OPEN_SINGLEOUT[s_index]}) {
		this.${OPEN_SINGLEOUT[s_index]} = ${OPEN_SINGLEOUT[s_index]};
	}
	
	</#list>
	<#if OPEN_MUTILFLAG=='TRUE'>
	@XmlElementWrapper(name = "multilpyList")
	private List<${OPEN_NAME}DetailOut> multilpyList;
	
	public List<${OPEN_NAME}DetailOut> getMultilpyList() {
		return multilpyList;
	}

	public void setMultilpyList(List<${OPEN_NAME}DetailOut> multilpyList) {
		this.multilpyList = multilpyList;
	}
	</#if>
	
}
