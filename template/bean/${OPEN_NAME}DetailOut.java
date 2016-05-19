package ${OPEN_PATH}.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class ${OPEN_NAME}DetailOut{
	
	<#list OPEN_MUTILOUT as s>
	@XmlElement
	private String ${s};// ${OPEN_MUTILNOTE[s_index]}
	
	</#list>
	<#list OPEN_FUMUTILOUT as s>
	public String get${s}() {
		return ${OPEN_MUTILOUT[s_index]};
	}
	
	public void set${s}(String ${OPEN_MUTILOUT[s_index]}) {
		this.${OPEN_MUTILOUT[s_index]} = ${OPEN_MUTILOUT[s_index]};
	}
	</#list>
}
