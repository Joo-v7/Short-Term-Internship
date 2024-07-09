package kepco.cms.sec.roleAuth;

import java.util.List;

import javax.validation.ConstraintViolationException;

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
 * 역할별 권한 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/sec/roleAuth")
public class AdminRoleAuthController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private RoleAuthService secRoleAuthService;
	
	/**
	 * 역할변 권한 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/roleAuth/list";
		
		return viewName;
	}
	/**
	 * 역할별 권한 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/roleAuth/form";
		
		RoleAuthVo roleAuthVo = null;
		
		if(!StrUtil.isEmpty(req.get("roleCd"))) {
			roleAuthVo = secRoleAuthService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", roleAuthVo);
		
		return viewName;
	}
	/**
	 * 역할별 권한 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RoleAuthVo roleAuthVo = mapper.convertValue(req, RoleAuthVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("roleCd"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST);
		}
		
		if(StrUtil.isEmpty(req.get("authCd"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST);
		}
		
		result = secRoleAuthService.insert(roleAuthVo);
		
		model.addAttribute("result", result);
		model.addAttribute(roleAuthVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 역할별 권한 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<RoleAuthVo> list = secRoleAuthService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 역할별 권한 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		int result = secRoleAuthService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
	
	//화면설계서용 임시
	@SiteLayout
	@RequestMapping(value = "lmsList")
	public String lmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/roleAuth/lmsList";
		
		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "rasList")
	public String rasList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/roleAuth/rasList";
		
		return viewName;
	}
	
}