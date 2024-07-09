package kepco.cms.page.log;

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

import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 페이지 이력
 */
@Controller("kepco.cms.page.log.AdminLogController")
@RequestMapping(value = "${global.admin-path}/cms/page/log")
public class AdminLogController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LogService logService;
	
	@Autowired
	SiteService siteService;
	/**
	 * 페이지 이력 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		List<SiteVo> vos = siteService.selectAll(req);
		model.addAttribute("siteList", vos);
		
		String pageIdx = StrUtil.toStr(req.get("pageIdx"));
		model.addAttribute("pageIdx", pageIdx);
		 
		return "admin/cms/page/log/list";
	}
	/**
	 * 페이지 이력 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		
		String siteIdx = (String) req.get("siteIdx");
		
		List<LogVo> list = logService.selectList(req);
		
		model.addAttribute("siteIdx", siteIdx);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}