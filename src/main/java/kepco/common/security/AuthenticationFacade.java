package kepco.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import kepco.cms.member.auth.MemberDetailVo;

@Component
public class AuthenticationFacade {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	
	public MemberDetailVo getMemberDetailVo() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 로그인하지 않았다면 기본적으로 String 값으로 "anonymousUser"
		if(principal instanceof String) {
			return new MemberDetailVo();
		}
		return (MemberDetailVo) principal;
	}
}