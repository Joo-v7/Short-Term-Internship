package kepco.cms.setting;

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
 * 환경설정
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/setting")
public class AdminSettingController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private SettingService settingService;
	
	/**
	 * 환경설정 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/setting/list";
		List<SettingVo> settingList = settingService.selectAll(req);
		model.addAttribute("seetingList", settingList);
		model.addAttribute("req", req);
		return viewName;
	}
	/**
	 * 환경설정 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/setting/form";
		
		SettingVo settingVo = null;
		
		if(!StrUtil.isEmpty(req.get("settingIdx"))) {
			settingVo = settingService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", settingVo);
		
		return viewName;
	}
	/**
	 * 환경설정 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		if(StrUtil.isBlank(req.get("settingKey"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "항목코드은(는) 필수입력 입니다.");
		}
		if(!StrUtil.isRegex(req.get("settingKey"),"^[a-z0-9-_]{0,}")) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "항목코드은(는) 영소문자, 숫자, _만 입력 가능합니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SettingVo settingVo = mapper.convertValue(req, SettingVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("settingIdx"))) {
			settingVo.setInsertIdx(StrUtil.getSessionIdx());
			settingVo.setInsertIp(StrUtil.getClientIP());
			result = settingService.insert(settingVo);
		}else {
			settingVo.setUpdateIdx(StrUtil.getSessionIdx());
			settingVo.setUpdateIp(StrUtil.getClientIP());
			result = settingService.update(settingVo);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(settingVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 환경설정 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<SettingVo> list = settingService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 환경설정 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int result = settingService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
}