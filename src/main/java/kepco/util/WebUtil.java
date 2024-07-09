package kepco.util;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import kepco.cms.member.auth.MemberDetailVo;

public class WebUtil {

	public static String toQueryString(EgovMap egovMap) {
		if(egovMap == null) return "";
		
		
		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Object key: egovMap.keySet()) {
			Object val = egovMap.get(key);
			if (val instanceof List) {
				map.addAll((String) key, ((List<Object>) val).stream().map(obj -> StrUtil.toStr(obj)).toList());
			} else if (val.getClass().isArray()) {
				map.addAll((String) key, Arrays.asList(val).stream().map(obj -> StrUtil.toStr(obj)).toList());
			} else {
				map.add((String) key, StrUtil.toStr(val));
			}
		}
		
		String queryString = UriComponentsBuilder.newInstance()
				.queryParams(map)
				.build()
				.encode()
				.getQuery();
		
		return queryString;
	}
	
	public static MemberDetailVo getMemberDetailVo() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext == null)
			return null;
		return (MemberDetailVo) securityContext.getAuthentication().getPrincipal();
	}
}
