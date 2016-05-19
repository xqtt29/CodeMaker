package ${OPEN_PATH}.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class ${OPEN_NAME}DetailIn{
	
	<#list OPEN_MUTILIN as s>
	@XmlElement
	private String ${s};// ${OPEN_MUTILNOTEIN[s_index]}
	
	</#list>
	<#list OPEN_FUMUTILIN as s>
	public String get${s}() {
		return ${OPEN_MUTILIN[s_index]};
	}
	
	public void set${s}(String ${OPEN_MUTILIN[s_index]}) {
		this.${OPEN_MUTILIN[s_index]} = ${OPEN_MUTILIN[s_index]};
	}
	</#list>
}
