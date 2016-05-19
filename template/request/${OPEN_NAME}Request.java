package ${OPEN_PATH}.request;

import javax.validation.Valid;
import com.chinacreator.rop.AbstractRopRequest;

public class ${OPEN_NAME}Request extends AbstractRopRequest{
	
	
	@Valid
  private ${OPEN_NAME}In content;

	public ${OPEN_NAME}In getContent() {
		return content;
	}

	public void setContent(${OPEN_NAME}In content) {
		this.content = content;
	}


}
