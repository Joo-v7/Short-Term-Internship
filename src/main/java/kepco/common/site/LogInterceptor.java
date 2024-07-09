package kepco.common.site;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kepco.cms.accessLog.AccessLogService;
import kepco.util.StrUtil;

/**
 * 웹 액세스 로그
 */
public class LogInterceptor implements HandlerInterceptor {
	
	@Autowired
	AccessLogService accessLogService;
	
	@Value("${global.admin-path}")
	String globalAdminPath;
	
	@Value("${server.error.path:${error.path:/error}}")
	String serverErrorPath;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		if(!(handler instanceof HandlerMethod)) {
			return;
		}
		
		HttpSession session = request.getSession();
		
		int idx = StrUtil.getSessionIdx();
		String ip = StrUtil.getClientIP();
		String sessionId = session.getId();
		String logAccess = request.getRequestURI();
		
		// 에러페이지는 무시
		if(serverErrorPath.equals(logAccess)) {
			return;
		}
		
		EgovMap req =  new EgovMap();
		req.put("logMemberIdx", idx);
		req.put("logIp", ip);
		req.put("logSessionId", sessionId);
		req.put("logAccess", logAccess);
		
		accessLogService.insert(req);
		
		return;
	}
		
}