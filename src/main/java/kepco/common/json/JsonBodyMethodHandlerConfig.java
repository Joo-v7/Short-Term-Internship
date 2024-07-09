package kepco.common.json;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

//@Configuration
public class JsonBodyMethodHandlerConfig implements WebMvcRegistrations {
	
	private final JsonBodyMethodProcessor jsonBodyMethodProcessor;
	
	public JsonBodyMethodHandlerConfig(JsonBodyMethodProcessor jsonBodyMethodProcessor) {
        this.jsonBodyMethodProcessor = jsonBodyMethodProcessor;
    }
	
	@Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new JsonBodyMappingHandlerAdapter(jsonBodyMethodProcessor);
    }
}
