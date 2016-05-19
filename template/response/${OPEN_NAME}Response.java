package ${OPEN_PATH}.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ${OPEN_PATH}.bean.${OPEN_NAME}Out;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class ${OPEN_NAME}Response{

	@XmlElement
	private String res_code;

	@XmlElement
	private String rest_desc;
	
	@XmlElement
	private ${OPEN_NAME}Out result;
	
	public ${OPEN_NAME}Response() {//关键就是这个，有了它，问题纠正  
        super();  
    } 
	
	public ${OPEN_NAME}Response(String code,String desc){
		this.res_code=code;
		this.rest_desc=desc;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String resCode) {
		res_code = resCode;
	}

	public String getRest_desc() {
		return rest_desc;
	}

	public void setRest_desc(String restDesc) {
		rest_desc = restDesc;
	}

	public ${OPEN_NAME}Out getResult() {
		return result;
	}

	public void setResult(${OPEN_NAME}Out result) {
		this.result = result;
	}
	
}
