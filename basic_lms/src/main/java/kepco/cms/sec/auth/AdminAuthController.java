package kepco.cms.sec.auth;

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
 * 회원 권한
 */
@Controller("cms.sec.auth.adminAuthController")
@RequestMapping(value = "${global.admin-path}/cms/sec/auth")
public class AdminAuthController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AuthService secAuthService;
	
	/**
	 * 회원 권한 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/auth/list";
		
		return viewName;
	}
	/**
	 * 회원 권한 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/auth/form";
		
		AuthVo secAuthVo = null;
		
		if(!StrUtil.isEmpty(req.get("authIdx"))) {
			secAuthVo = secAuthService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", secAuthVo);
		
		return viewName;
	}
	/**
	 * 회원 권한 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		if(StrUtil.isBlank(req.get("authCd"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "권한코드은(는) 필수입력 입니다.");
		}
		if(!StrUtil.isRegex(req.get("authCd"),"^[A-Z0-9-_]{0,}")) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "권한코드은(는) 영대문자, 숫자, _만 입력 가능합니다.");
		}
		if(StrUtil.isBlank(req.get("authNm"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "권한명은(는) 필수입력 입니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		AuthVo secAuthVo = mapper.convertValue(req, AuthVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("authIdx"))) {
			result = secAuthService.insert(secAuthVo);
		}else {
			result = secAuthService.update(secAuthVo);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(secAuthVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 회원 권한 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<AuthVo> list = secAuthService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 회원 권한 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		int result = secAuthService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
	
	//lms, ras 용 권한관리 추가
	@SiteLayout
	@RequestMapping(value = "lmsList")
	public String lmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/auth/lmsList";
		
		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "rasList")
	public String rasList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/sec/auth/rasList";
		
		return viewName;
	}
	
}