package kepco.common.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Component
public class PageHelperInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		int pageNum = StrUtil.toInt(request.getParameter("pageNum"), 1);
		int pageSize = StrUtil.toInt(request.getParameter("pageSize"), 15);
		PageHelper.startPage(pageNum, pageSize);
		
		return HandlerInterceptor.super.preHandle(request,  response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		
	}
}