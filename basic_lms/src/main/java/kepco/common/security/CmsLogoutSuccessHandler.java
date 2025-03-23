package kepco.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class CmsLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		log.info("onLogoutSuccess");
		
		response.sendRedirect("/");
	}

}
