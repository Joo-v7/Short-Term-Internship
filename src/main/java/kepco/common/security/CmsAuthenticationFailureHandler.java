package kepco.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import kepco.cms.member.MemberService;

public class CmsAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Autowired
	MemberService memberService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.error("onAuthenticationFailure : " + request.getParameter("memberId"));
		
		if(exception instanceof BadCredentialsException) {
			//비밀번호 틀림, 로그인 실패 횟수 증가
			memberService.loginFail(request.getParameter("memberId"));
		}
		
		request.setAttribute("exception", exception);
		response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getLocalizedMessage());
	}

}
