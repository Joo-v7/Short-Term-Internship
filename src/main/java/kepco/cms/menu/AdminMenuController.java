package kepco.cms.menu;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.layout.LayoutService;
import kepco.cms.layout.LayoutVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 관리자 메뉴
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/menu")
public class AdminMenuController {
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	LayoutService layoutService;
	
	/**
	 * 관리자 메뉴트리
	 * @param req
	 * @param model
	 * @return 
	 * @throws  
	 */
	@SiteLayout
	@RequestMapping(value = { "", "tree" })
	public String tree(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		if(!StrUtil.isEmpty(req.get("site"))) {
			req.put("site", req.get("site"));
		}
		
		model.addAttribute("req", req);
		
		List<SiteVo> vo = siteService.selectAll(req);
		
		model.addAttribute("siteList", vo);
		
		return "admin/cms/menu/tree";
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
		
		return "admin/cms/menu/parentMenuTree";
	}
	
	/**
	 * 관리자 메뉴트리 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws  
	 */
	@RequestMapping(value = "treeJson")
	@ResponseBody
	public JsonResponse treeJson(@RequestEgovMap EgovMap req, Model model) {
		
		String site = (String) req.get("site");
		if(site.isBlank()) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트코드는 필수입력 입니다.");
		}
		
		// jstree 에서 처리해주므로 트리형태는 불필요
		List<MenuVo> menuList = menuService.selectAll(req);
		
		List<SiteVo> vo = siteService.selectAll(req);
		
		model.addAttribute("site", site);
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
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		MenuVo vo;
		
		List<LayoutVo> layoutList = layoutService.selectAll(req);
		
		// 메뉴 수정
		if(!StrUtil.isBlank(req.get("menuIdx"))) {
			// ROOT
			if (StrUtil.toStr(req.get("menuIdx")).equals("0")) {
				vo = new MenuVo();
				vo.setSite((String) req.get("site"));
				vo.setParentMenuIdx(-1);
				vo.setParentMenuNm(null);
				vo.setMenuNm("ROOT");
				vo.setMenuIdx(0);
				vo.setMenuDepth(0);
				vo.setMenuOrder(100);
				vo.setUseYn("n");
				vo.setMenuType("none");
				vo.setMenuUrl((String) req.get("menuUrl"));
				vo.setMenuTitle((String) req.get("menuTitle"));
				vo.setMenuType((String) req.get("menuType"));
			}
			// 
			else {
				vo = menuService.select(req);
				if(vo.getParentMenuIdx() == 0) {
					vo.setParentMenuNm("ROOT");
				}
			}
		}
		// 상위메뉴로 메뉴 추가
		else if (!StrUtil.isBlank(req.get("parentMenuIdx"))) {
			// ROOT 하위 메뉴 추가
			if (StrUtil.toStr(req.get("parentMenuIdx")).equals("0")) {
				// 최상위메뉴로 추가
				vo = new MenuVo();
				vo.setSite((String) req.get("site"));
				vo.setParentMenuIdx(0);
				vo.setParentMenuNm("ROOT");
				vo.setMenuDepth(1);
				vo.setMenuOrder(100);
				vo.setUseYn("y");
				vo.setMenuType("none");
				// 이게 왜 필요하지?
//				vo.setLayoutIdx((int) req.get("layoutIdx"));
			}
			// 상위메뉴ID를 받아 하위 메뉴 추가
			else {
				EgovMap req2 = new EgovMap();
				req2.put("menuIdx", StrUtil.toStr(req.get("parentMenuIdx")));
				MenuVo parent = menuService.select(req2);
				
				if(parent == null || parent.getMenuIdx() == 0 || "y".equals(parent.getDeleteYn())) {
					throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "상위 메뉴가 존재하지 않습니다.");
				}
				vo = new MenuVo();
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
		model.addAttribute("layoutList", layoutList);
	
		return "admin/cms/menu/form";
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
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MenuVo menuVo = mapper.convertValue(req, MenuVo.class);
		
		if(!"y".equals(menuVo.getUseYn()))	{
			menuVo.setUseYn("n");
		}
		
		EgovMap myParam = new EgovMap();
		myParam.put("scMn", menuVo.getMn());
		myParam.put("site", menuVo.getSite());
		myParam.put("scExceptMenuIdx", menuVo.getMenuIdx());
		int mnCnt = menuService.selectCnt(myParam);
		if(mnCnt > 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "이미 등록된 메뉴코드 입니다.");
		}
		
		int resultCnt = 0 ;
		if(StrUtil.isEmpty(req.get("menuIdx"))) {
			menuVo.setInsertIdx(StrUtil.getSessionIdx());
			menuVo.setInsertIp(StrUtil.getClientIP());
			resultCnt = menuService.insert(menuVo);
		}else {
			menuVo.setUpdateIdx(StrUtil.getSessionIdx());
			menuVo.setUpdateIp(StrUtil.getClientIP());
			resultCnt = menuService.update(menuVo);
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
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		if (StrUtil.isEmpty(req.get("menuIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}

		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int resultCnt = menuService.delete(req);
		model.addAttribute("resultCnt", resultCnt);
		
		return new JsonResponse.Builder().model(model).build();
	}
}