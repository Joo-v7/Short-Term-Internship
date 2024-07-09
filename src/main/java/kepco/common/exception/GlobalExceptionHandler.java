package kepco.common.exception;

import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//	@Value("${server.error.path}")
//	String serverErrorPath;
	
	@Value("${global.user.login-url}")
	private String loginUrl;
	
	@ExceptionHandler(CmsCustomException.class)
	public ModelAndView cmsCustomException(HttpServletRequest request, HttpServletResponse response, Object handlerMethod, Exception exception) throws Exception {
		
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, ((CmsCustomException)exception).getCmsStatusCode().value());
		
		return new ModelAndView("forward:/error");
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handlerMethod, Exception exception) throws Exception {
		
		// 기본값은 무조건 404
		int statusCode = 404;
		
		if (exception instanceof org.springframework.security.access.AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
				// 401인 경우 로그인
				String retUrl = request.getRequestURI();
				retUrl += request.getQueryString() != null ? "?"+request.getQueryString() : "";
				return new ModelAndView("redirect:"+loginUrl).addObject("retUrl", retUrl);
			} else {
				// 403인 경우 차단안내
				statusCode = 403;
			}
		}
		
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, statusCode);
		request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
		return new ModelAndView("forward:/error");
	}
}