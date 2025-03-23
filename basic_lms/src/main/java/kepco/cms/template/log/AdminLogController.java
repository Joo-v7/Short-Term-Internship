package kepco.cms.template.log;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 템플릿 이력 관리
 */
@Controller("kepco.cms.template.log.AdminLogController")
@RequestMapping(value = "${global.admin-path}/cms/template/log")
public class AdminLogController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LogService logService;
	
	@Autowired
	TemplateService templateService;
	
	/**
	 * 템플릿 이력 목록	
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		List<TemplateVo> vos = templateService.selectAll(req);
		model.addAttribute("templateList", vos);
		
		String tempIdx = StrUtil.toStr(req.get("tempIdx"));
		model.addAttribute("tempIdx", tempIdx);
		 
		return "admin/cms/template/log/list";
	}
	/**
	 * 템플릿 이력 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		
		String tempIdx = (String) req.get("tempIdx");
		
		List<LogVo> list = logService.selectList(req);
		
		model.addAttribute("tempIdx", tempIdx);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}