package kepco.cms.sec.role;

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
 * 역할 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/sec/role")
public class AdminRoleController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	RoleService secRoleService;
	
	/**
	 * 역할 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/role/list";
		
		return viewName;
	}
	/**
	 * 역할 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/role/form";
		
		RoleVo secRoleVo = null;
		
		if(!StrUtil.isEmpty(req.get("roleIdx"))) {
			secRoleVo = secRoleService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", secRoleVo);
		
		return viewName;
	}
	/**
	 * 역할 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		if(StrUtil.isBlank(req.get("roleCd"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "역할코드은(는) 필수입력 입니다.");
		}
		if(!StrUtil.isRegex(req.get("roleCd"),"^[A-Z0-9-_]{0,}")) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "역할코드은(는) 영대문자, 숫자, _만 입력 가능합니다.");
		}
		if(!StrUtil.isRegex(req.get("parentRoleCd"),"^[A-Z0-9-_]{0,}")) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "상위역할코드은(는) 영대문자, 숫자, _만 입력 가능합니다.");
		}
		if(req.get("roleCd").equals(req.get("parentRoleCd"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "역할코드와 상위역할코드는 같을 수 없습니다.");
		}
		if(StrUtil.isBlank(req.get("roleNm"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "역할명은(는) 필수입력 입니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RoleVo secRoleVo = mapper.convertValue(req, RoleVo.class);
		
		secRoleVo.setUseYn("y");
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("roleIdx"))) {
			secRoleVo.setInsertIdx(StrUtil.getSessionIdx());
			secRoleVo.setInsertIp(StrUtil.getClientIP());
			result = secRoleService.insert(secRoleVo);
		}else {
			result = secRoleService.update(secRoleVo);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(secRoleVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 역할 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<RoleVo> list = secRoleService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 역할 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int result = secRoleService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
}