package kepco.common.json;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

//@Configuration
public class JsonBodyMappingHandlerAdapter extends RequestMappingHandlerAdapter {
    private final JsonBodyMethodProcessor jsonBodyMethodProcessor;

    public JsonBodyMappingHandlerAdapter(JsonBodyMethodProcessor jsonBodyMethodProcessor) {
        this.jsonBodyMethodProcessor = jsonBodyMethodProcessor;
    }

    @Override
    protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
        return new ServletInvocableHandlerMethod (handlerMethod);
    }
}