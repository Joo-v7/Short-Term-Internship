package kepco.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kepco.cms.member.MemberService;
import kepco.cms.member.auth.MemberDetailVo;
import kepco.util.StrUtil;

public class CmsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	MemberService memberService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession();

		log.info("onAuthenticationSuccess : " + request.getParameter("memberId"));
		
		if (authentication != null) {
			MemberDetailVo principal = (MemberDetailVo) authentication.getPrincipal();
			
			if (principal != null && principal instanceof UserDetails) {
				log.debug(principal.toString());
				
				//로그인 날짜 갱신
				memberService.loginSuccess(principal.getMemberIdx());
				
				//세션에 멤버 idx, name 정보 저장
				session.setAttribute("memberIdx", principal.getMemberIdx());
				session.setAttribute("memberId", principal.getMemberId());
				session.setAttribute("memberName", principal.getMemberName());
				session.setAttribute("memberRole", StrUtil.getUserRole());
				session.setAttribute("memberRoleDetail", StrUtil.getUserRole2());
				
			}
		}
		
		// TODO: 적절한 자격증명에 대해서 처리 - 된것같음
		
		String returnUrl = request.getParameter("returnUrl");
		if(StrUtil.isBlank(returnUrl)) {
			returnUrl = request.getHeader("Referer");
			if(StrUtil.isBlank(returnUrl)) {
				returnUrl = "/";
			}
		}
		
		if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.sendRedirect(returnUrl);
		}
		else {
			
			request.setAttribute("cms.authentication", authentication);
			request.setAttribute("cms.Role", StrUtil.getUserRole2());
			response.sendError(HttpServletResponse.SC_OK);
		}
	}

}
