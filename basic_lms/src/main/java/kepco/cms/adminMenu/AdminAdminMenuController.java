package kepco.cms.adminMenu;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.menu.MenuTree;
import kepco.cms.menu.MenuVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestCamelMap;
import kepco.common.web.RequestEgovMap;
import kepco.util.CamelMap;
import kepco.util.StrUtil;

/**
 * 관리자 메뉴
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/adminMenu")
public class AdminAdminMenuController {
	
	/** 관리자 메뉴 서비스 */
	@Autowired
	AdminMenuService adminMenuService;
	
	/** 사이트 서비스 */
	@Autowired
	SiteService siteService;
	
	/**
	 * 관리자 메뉴
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = { "", "tree" })
	public String tree(@RequestCamelMap CamelMap req, Model model) throws Exception {
		
		if(req.getStr("site").isBlank()) {
			req.put("site", 1);
		}
		
		model.addAttribute("req", req);
		
		// 관리자 사이트는 1개만 존재? LMS, CMS, RAS로 3개?
		List<Map<String, String>> siteList = new ArrayList<Map<String, String>>() {{
			add(Map.of("site", "1", "siteNm", "CMS"));
			add(Map.of("site", "2", "siteNm", "LMS"));
			add(Map.of("site", "3", "siteNm", "RAS"));
		}};
		model.addAttribute("siteList", siteList);
		
		return "admin/cms/adminMenu/tree";
	}
	
	/**
	 * 관리자 메뉴트리
	 * @param req
	 * @param model
	 * @return 
	 * @throws  
	 */
	@SiteLayout
	@RequestMapping(value = "menuModal")
	public String menuModal(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		if(!StrUtil.isEmpty(req.get("site"))) {
			req.put("site", req.get("site"));
		}
		
		model.addAttribute("req", req);
		
		List<SiteVo> vo = siteService.selectAll(req);
		
		model.addAttribute("siteList", vo);
		
		return "admin/cms/adminMenu/parentMenuTree";
	}
	
	/**
	 * 관리자 메뉴 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "treeJson")
	@ResponseBody
	public JsonResponse treeJson(@RequestCamelMap CamelMap req, Model model) {
		
		if(req.getStr("site").isBlank()) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트코드는 필수입력 입니다.");
		}
		
		// jstree 에서 처리해주므로 트리형태는 불필요
		List<AdminMenuVo> menuList = adminMenuService.selectAll(req);
		
		model.addAttribute("menuList", menuList);
		
		return new JsonResponse.Builder().model(model).build();
		
	}
	
	/**
	 * 관리자 메뉴 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestCamelMap CamelMap req, Model model) throws Exception {
		
		AdminMenuVo vo;
		
		// 메뉴 수정
		if(!req.getStr("menuIdx").isBlank()) {
			// ROOT
			if (req.getStr("menuIdx").equals("0")) {
				vo = new AdminMenuVo();
				vo.setSite(req.getStr("site"));
				vo.setParentMenuIdx(-1);
				vo.setParentMenuNm("없음");
				vo.setMenuNm("ROOT");
				vo.setMenuIdx(0);
				vo.setMenuDepth(0);
				vo.setMenuOrder(100);
				vo.setUseYn("n");
				vo.setMenuType("none");
			}
			// 
			else {
				vo = adminMenuService.select(req);
				if(vo.getParentMenuIdx() == 0) {
					vo.setParentMenuNm("ROOT");
				}
			}
		}
		// 상위메뉴로 메뉴 추가
		else if (!req.getStr("parentMenuIdx").isBlank()) {
			// ROOT 하위 메뉴 추가
			if (req.getStr("parentMenuIdx").equals("0")) {
				// 최상위메뉴로 추가
				vo = new AdminMenuVo();
				vo.setSite(req.getStr("site"));
				vo.setParentMenuIdx(0);
				vo.setParentMenuNm("ROOT");
				vo.setMenuDepth(1);
				vo.setMenuOrder(100);
				vo.setUseYn("y");
				vo.setMenuType("none");
			}
			// 상위메뉴ID를 받아 하위 메뉴 추가
			else {
				CamelMap req2 = new CamelMap();
				req2.put("menuIdx", req.getStr("parentMenuIdx"));
				AdminMenuVo parent =  adminMenuService.select(req2);
				
				if(parent == null || parent.getMenuIdx() == 0 || "y".equals(parent.getDeleteYn())) {
					throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "상위 메뉴가 존재하지 않습니다.");
				}
				vo = new AdminMenuVo();
				vo.setSite(parent.getSite());
				vo.setParentMenuIdx(parent.getMenuIdx());
				vo.setParentMenuNm(parent.getMenuNm());
				vo.setMenuDepth(parent.getMenuDepth()+1);
				vo.setMenuOrder(100);
				vo.setUseYn("y");
				vo.setMenuType("none");
			}
		}
		// menuIdx, parentMenuIdx 가 없다?
		else {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력 값이 누락되었습니다.");
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", vo);
		
		// 관리자 사이트는 1개만 존재? LMS, CMS, RAS로 3개?
		List<Map<String, String>> siteList = new ArrayList<Map<String, String>>() {{
			add(Map.of("site", "1", "siteNm", "CMS"));
			add(Map.of("site", "2", "siteNm", "LMS"));
			add(Map.of("site", "3", "siteNm", "RAS"));
		}};
		model.addAttribute("siteList", siteList);
		
		return "admin/cms/adminMenu/form";
	}
	
	/**
	 * 관리자 메뉴 저장
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestCamelMap CamelMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		AdminMenuVo menuVo = mapper.convertValue(req, AdminMenuVo.class);
		
		if(!"y".equals(menuVo.getUseYn()))	{
			menuVo.setUseYn("n");
		}
		
		int resultCnt = 0 ;
		if(StrUtil.isEmpty(req.get("menuIdx"))) {
			resultCnt = adminMenuService.insert(menuVo);
		}else {
			resultCnt = adminMenuService.update(menuVo);
		}
		
		model.addAttribute("resultCnt", resultCnt);
		model.addAttribute(menuVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 관리자 메뉴 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestCamelMap CamelMap req, Model model) {
		
		if (StrUtil.isEmpty(req.get("menuIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		int resultCnt = adminMenuService.delete(req);
		model.addAttribute("resultCnt", resultCnt);
		
		return new JsonResponse.Builder().model(model).build();
	}
}