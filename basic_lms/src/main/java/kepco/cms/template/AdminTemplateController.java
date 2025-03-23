package kepco.cms.template;

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

import kepco.cms.template.log.LogService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 템플릿 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/template")
public class AdminTemplateController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	LogService logService;
	
	/**
	 * 템플릿 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/template/list";
		
		return viewName;
	}
	/**
	 * 템플릿 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/template/form";
		
		TemplateVo templateVo = null;
		
		System.out.println(req);
		
		if(!StrUtil.isEmpty(req.get("tempIdx"))) {
			templateVo = templateService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", templateVo);
		
		return viewName;
	}
	/**
	 * 템플릿 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TemplateVo templateVo = mapper.convertValue(req, TemplateVo.class);
		
		System.out.println(req.toString());
		
		if (StrUtil.isBlank(templateVo.getTempTitle())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "제목은 필수입력 입니다.");
		}
		if (StrUtil.isBlank(templateVo.getTempContent())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "내용은 필수입력 입니다.");
		}
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("tempIdx"))) {
			templateVo.setInsertIdx(StrUtil.getSessionIdx());
			templateVo.setInsertIp(StrUtil.getClientIP());
			result = templateService.insert(templateVo);
		}else {
			templateVo.setUpdateIdx(StrUtil.getSessionIdx());
			templateVo.setUpdateIp(StrUtil.getClientIP());
			result = templateService.update(templateVo);
			logService.insert(req);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(templateVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 템플릿 이력 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logSave")
	@ResponseBody
	public JsonResponse logSave(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TemplateVo templateVo = mapper.convertValue(req, TemplateVo.class);
		
		int result = 0 ;
		
		templateVo.setUpdateIdx(StrUtil.getSessionIdx());
		templateVo.setUpdateIp(StrUtil.getClientIP());
		result = templateService.logSave(templateVo);
		
		model.addAttribute("result", result);
		model.addAttribute(templateVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 템플릿 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<TemplateVo> list = templateService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 템플릿 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		if (StrUtil.isEmpty(req.get("tempIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int result = templateService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
}