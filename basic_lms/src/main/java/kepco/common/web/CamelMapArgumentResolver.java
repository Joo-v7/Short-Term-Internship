package kepco.common.web;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kepco.util.CamelMap;

@Component
public class CamelMapArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestCamelMap.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest req = (HttpServletRequest)webRequest.getNativeRequest();
		
		RequestCamelMap attr = parameter.getParameterAnnotation(RequestCamelMap.class);
		
		CamelMap camelMap = new CamelMap();
		
		Set<Entry<String, String[]>> entries = req.getParameterMap().entrySet();
		for (Map.Entry<String, String[]> mapEntry : entries) {
			String key = mapEntry.getKey();
			if(!StringUtils.hasText(key)) {
				continue;
			}
			
			if (!attr.includeSc()) {
				if(!key.startsWith("sc") && !key.equals("page")) {
					continue;
				}
			}
			List<String> val = Arrays.asList(mapEntry.getValue());
			if (val == null)
				continue;
			
			if (val.size() == 1) {
				camelMap.put(key, val.get(0));
			} else {
				camelMap.put(key, val);
			}
		}
		
		return camelMap;
	}
}