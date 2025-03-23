package kepco.cms.site;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kepco.cms.page.PageService;
import kepco.cms.page.PageVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 메인화면
 */
@Controller
public class MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	PageService pageService;
	/**
	 * 메인화면 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = {"", "/cms/main"})
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
		
		if(siteVo.getPageIdx() == 0) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "페이지가 설정되지 않았습니다.");
		}
		
		// 메인페이지 조회
		param = new EgovMap();
//		param.put("scUseYn", "y");
//		param.put("scPageType", "main");
//		param.put("scSite", siteVo.getSite());
		param.put("pageIdx", siteVo.getPageIdx());
		PageVo pageVo = pageService.select(param);
		
		if(pageVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "페이지를 찾을 수 없습니다.");
		}
		
		String viewName = "pages/" + siteVo.getSite() + "/main/" + pageVo.getPageCd();
		
		return viewName;
	}
}