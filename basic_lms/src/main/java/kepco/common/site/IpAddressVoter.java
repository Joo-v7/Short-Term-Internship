package kepco.common.site;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import kepco.cms.setting.PropApp;
import kepco.cms.setting.SettingService;
import kepco.util.StrUtil;

/**
 * @deprecated 아이피 허용/제한은 Voter보다는 Interceptor가 더 명료한듯
 */
public class IpAddressVoter implements AccessDecisionVoter<Object> {
	
	@Autowired
	private PropApp propApp;
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		
		WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
		String remoteIp = details.getRemoteAddress();
		
		// localhost 는 무조건 허용
		if(remoteIp.startsWith("127.")) {
			return ACCESS_GRANTED;
		}
		if(remoteIp.equals("0:0:0:0:0:0:0:1") || remoteIp.equals("::1")) {
			return ACCESS_GRANTED;
		}
		
		
		
		String adminWhiteIp = StrUtil.noNull(propApp.getAdminWhiteIp()).replaceAll("\\s", "");
		
		if(!adminWhiteIp.equals("")) {
			List<String> whiteIpList = Arrays.asList(adminWhiteIp.split(","));
			
			for (String ip : whiteIpList) {
				// TODO: CIDR 지원
				if(ip.indexOf("/") != -1) {
					
				}
				else if(remoteIp.equals(ip)) {
					return ACCESS_GRANTED;
				}
			}
			
			// 화이트가 등록된 경우, 블랙은 무시
			return ACCESS_DENIED;
		}
		
		String adminBlackIp = StrUtil.noNull(propApp.getAdminBlackIp()).replaceAll("\\s", "");
		if(!adminBlackIp.equals("")) {
			List<String> blackIpList = Arrays.asList(adminBlackIp.split(","));
			for (String ip : blackIpList) {
				if(remoteIp.equals(ip)) {
					return ACCESS_DENIED;
				}
			}
			return ACCESS_GRANTED;
		}
		
		return ACCESS_GRANTED;
	}
	
}