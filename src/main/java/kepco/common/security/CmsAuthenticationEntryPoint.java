package kepco.common.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import kepco.util.StrUtil;

public class CmsAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${global.admin-path}")
	private String adminPath;
	
	@Value("${global.user.login-url}")
	private String userLoginUrl;
	
	@Value("${server.error.path}")
	private String serverErrorPath;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.error("onAuthenticationEntryPoint : " + request.getRequestURI());
		
		// 사용자는 로그인 페이지로 리디렉션
		if (!request.getRequestURI().startsWith("/"+adminPath)) {
			response.sendRedirect(userLoginUrl);
			return;
		}
			
		
		// 관리자는 에러 페이지로 리디렉션 
		request.setAttribute("exception", exception);
		if(StrUtil.isBlank(serverErrorPath)) {
			// 에러 페이지는 정의되어 있어 이곳으로 오지 않는다. 
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getLocalizedMessage());
		}
		else {
			request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
			request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
			request.getRequestDispatcher(serverErrorPath).forward(request, response);
		}
	}

}
