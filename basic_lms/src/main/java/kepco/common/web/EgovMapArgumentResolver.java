package kepco.common.web;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class EgovMapArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestEgovMap.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest req = (HttpServletRequest)webRequest.getNativeRequest();
		
		RequestEgovMap attr = parameter.getParameterAnnotation(RequestEgovMap.class);
		
		EgovMap egovMap = new EgovMap();
		
		Set<Entry<String, String[]>> entries = req.getParameterMap().entrySet();
		for (Map.Entry<String, String[]> mapEntry : entries) {
			String key = mapEntry.getKey();
			if(!StringUtils.hasText(key)) {
				continue;
			}
						
			if(attr.isSiteCriteria() ) {
				if(!key.startsWith("sc") && !key.equals("page")) {
					continue;
				}
			}
			String[] val = mapEntry.getValue();
			if(val != null && val.length == 1) {
				egovMap.put(key, val[0]);
			}
			else {
				egovMap.put(key, val);
			}
		}
		
		return egovMap;
    }
}