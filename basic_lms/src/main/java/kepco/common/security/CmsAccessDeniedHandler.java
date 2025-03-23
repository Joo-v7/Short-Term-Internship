package kepco.common.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CmsAccessDeniedHandler implements AccessDeniedHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
		
		log.error("onAccessDenied");
		
		request.setAttribute("exception", exception);
		response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getLocalizedMessage());
		
	}

}
