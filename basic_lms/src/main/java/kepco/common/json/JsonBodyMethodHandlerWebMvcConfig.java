package kepco.common.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

//@Configuration
public class JsonBodyMethodHandlerWebMvcConfig {
	
	private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	private final JsonBodyMethodProcessor jsonBodyMethodProcessor;

	public JsonBodyMethodHandlerWebMvcConfig(RequestMappingHandlerAdapter requestMappingHandlerAdapter, 
			JsonBodyMethodProcessor jsonBodyMethodProcessor) {
	 
	 this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
	 this.jsonBodyMethodProcessor = jsonBodyMethodProcessor;
	 }

	@PostConstruct
	public void init() {
		setReturnValueHttpHandler();
	}

	private void setReturnValueHttpHandler() {
		List<HandlerMethodReturnValueHandler> updatedValues = new ArrayList<>();
		updatedValues.add(0, jsonBodyMethodProcessor);
		updatedValues.addAll(requestMappingHandlerAdapter.getReturnValueHandlers());
		requestMappingHandlerAdapter.setReturnValueHandlers(updatedValues);
	}
}
