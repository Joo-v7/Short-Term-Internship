package kepco.cms.site;

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

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 사이트 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/site")
public class AdminSiteController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SiteService siteService;
	
	/**
	 * 사이트 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/site/list";
		
		return viewName;
	}
	/**
	 * 사이트 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/site/form";
		
		SiteVo siteVo = null;
		
		if(!StrUtil.isEmpty(req.get("siteIdx"))) {
			siteVo = siteService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", siteVo);
		
		return viewName;
	}
	/**
	 * 사이트 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SiteVo siteVo = mapper.convertValue(req, SiteVo.class);
		
		if (StrUtil.isBlank(siteVo.getSite())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트는 필수입력 입니다.");
		}
		if (StrUtil.isBlank(siteVo.getSiteNm())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트명은 필수입력 입니다.");
		}
		
		int result = 0 ;
		
		if (!"y".equals(siteVo.getDefaultSiteYn())) {
			siteVo.setDefaultSiteYn("n");
		}
		
		if(StrUtil.isEmpty(req.get("siteIdx"))) {
			siteVo.setInsertIdx(StrUtil.getSessionIdx());
			siteVo.setInsertIp(StrUtil.getClientIP());
			result = siteService.insert(siteVo);
		}else {
			siteVo.setUpdateIdx(StrUtil.getSessionIdx());
			siteVo.setUpdateIp(StrUtil.getClientIP());
			result = siteService.update(siteVo);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(siteVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 사이트 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<SiteVo> list = siteService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 사이트 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		if (StrUtil.isEmpty(req.get("siteIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int result = siteService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
}