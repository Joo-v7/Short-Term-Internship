package kepco.common.site;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kepco.cms.adminMenu.AdminMenuService;
import kepco.cms.adminMenu.AdminMenuTree;
import kepco.cms.layout.LayoutService;
import kepco.cms.layout.LayoutVo;
import kepco.cms.menu.MenuService;
import kepco.cms.menu.MenuTree;
import kepco.cms.menu.MenuVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.CamelMap;
import kepco.util.StrUtil;

public class SiteLayoutInterceptor implements HandlerInterceptor {
	
	@Autowired
	AdminMenuService adminMenuService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	LayoutService layoutService;
	
	@Value("${global.admin-path}")
	String globalAdminPath;
	
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(!(handler instanceof HandlerMethod)) {
			return;
		}
		
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		SiteLayout siteLayout = method.getAnnotation(SiteLayout.class);
		if (siteLayout == null) {
			return;
		}
		
		String requestURI = request.getRequestURI();
		
		
		// 관리자
		if(requestURI.startsWith(request.getContextPath() + "/" + globalAdminPath)) {
			CamelMap req = new CamelMap();
			req.put("site", "1");
			req.put("scUseYn", "y");
			AdminMenuTree menuTree = adminMenuService.selectTree(req);
			String menuUrl = requestURI.replaceAll("^"+request.getContextPath() + "/" + globalAdminPath, "");
			List<AdminMenuTree.Node> menu3List = menuTree.getSiblingList(menuTree.findNodeByMenuUrl(menuTree.getRoot(), menuUrl));
			modelAndView.addObject("menu3List", menu3List);
			modelAndView.addObject("menuTree", menuTree);
			
			return;
		}
		
		// 사용자
		// 메뉴에 따른 레이아웃 가져오기
		String site = request.getParameter("site");
		String mn = request.getParameter("mn");
		EgovMap param;
		param = new EgovMap();
		if(!StrUtil.isBlank(site)) {
			param.put("site", site);
		}
		else {
			param.put("scDefaultSiteYn", "y");
		}
		SiteVo siteVo = siteService.select(param);
		if(siteVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "사이트를 찾을 수 없습니다.");
		}
		modelAndView.addObject("siteVo", siteVo);
		
		int layoutIdx = 0;
		if(!StrUtil.isBlank(mn)) {
			// 서브
			param = new EgovMap();
			param.put("site", siteVo.getSite());
			param.put("scMn", mn);
			MenuVo menuVo = menuService.select(param);
			modelAndView.addObject("menuVo", menuVo);
			if(menuVo.getLayoutIdx() == 0) {
				throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "레이아웃이 정의되지 않았습니다.");
			}
			layoutIdx = menuVo.getLayoutIdx();
		} else {
			// 메인
			if(siteVo.getLayoutIdx() == 0) {
				throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "레이아웃이 정의되지 않았습니다.");
			}
			layoutIdx = siteVo.getLayoutIdx();
		}
		
		// 레이아웃
		param = new EgovMap();
		param.put("layoutIdx", layoutIdx);
		LayoutVo layoutVo = layoutService.select(param);
		if(layoutVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "레이아웃을 찾을 수 없습니다.");
		}
		
		modelAndView.addObject("siteLayoutPath", layoutVo.getLayoutPath() + layoutVo.getLayoutCd());
		
		// 전체 메뉴
		param = new EgovMap();
		param.put("site", siteVo.getSite());
		MenuTree menuTree = menuService.selectTree(param);
		
		// 현재 메뉴
		MenuTree.Node currMenuNode = null;
		if(!StrUtil.isBlank(mn)) {
			currMenuNode = menuTree.findNodeByMn(menuTree.getRoot(), mn);
		}
		modelAndView.addObject("currMenuNode", currMenuNode);
		
		
		modelAndView.addObject("menuTree", menuTree);
		
		
	}
}