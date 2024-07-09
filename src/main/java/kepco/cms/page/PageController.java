package kepco.cms.page;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.menu.MenuService;
import kepco.cms.menu.MenuVo;
import kepco.cms.page.PageService;
import kepco.cms.page.PageVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 페이지
 */
@Controller
public class PageController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	PageService pageService;
	
	/**
	 * 페이지
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = {"/cms/page"})
	public String main(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		
		String site = StrUtil.toStr(req.get("site"));
		
		EgovMap param;
		
		// 사이트 조회
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
		
		// 메뉴 조회
		String mn = StrUtil.toStr(req.get("mn"));
		if(StrUtil.isBlank(mn)) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "메뉴코드는 필수입력 입니다.");
		}
		
		// 메뉴 조회
		param = new EgovMap();
		param.put("scSite", siteVo.getSite());
		param.put("scMn", mn);
		MenuVo menuVo = menuService.select(param);
		
		if(menuVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "메뉴를 찾을 수 없습니다.");
		}
		
		// 리디렉션
		if("redirect".equals(menuVo.getMenuType())) {
			return "redirect:" + menuVo.getMenuUrl();
		}
		
		if(menuVo.getPageIdx() == 0) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "페이지가 정의되지 않았습니다.");
		}
		
		// 서브페이지 조회
		param = new EgovMap();
//		param.put("scUseYn", "y");
//		param.put("scPageType", "sub");
//		param.put("scSite", siteVo.getSite());
		param.put("pageIdx", menuVo.getPageIdx());
		PageVo pageVo = pageService.select(param);
		
		if(pageVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "페이지를 찾을 수 없습니다.");
		}
		
		String viewName = "pages/" + siteVo.getSite() + "/sub/" + pageVo.getPageCd();
		
		return viewName;
	}

}