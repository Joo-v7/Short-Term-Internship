package kepco.cms.systemLog;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "${global.admin-path}/cms/systemLog")
public class AdminSystemLogController {

	@Autowired
	SystemLogService systemLogService;
	

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@SiteLayout
	@RequestMapping(value = "cms/list")
	public String cmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/systemLog/cms/list";
		
		model.addAttribute("req", req);

		return viewName;
	}

	@SiteLayout
	@RequestMapping(value = "lms/list")
	public String lmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/systemLog/lms/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "ras/list")
	public String rasList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/systemLog/ras/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/*
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/systemLog/view";
		
		SystemLogVo systemLogVo = systemLogService.select(req);
		
//		List<PlayVo> playList = playService.selectList(req);
		
		model.addAttribute("vo", systemLogVo);
		model.addAttribute("req", req);
//		model.addAttribute("playList", playList);
		
		return viewName;
	}
	*/
	
	@SiteLayout
	@RequestMapping(value = "logList")
	public String logList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/systemLog/logList";
		
		req.put("pageSize" , "5");
		List<SystemLogVo> list = systemLogService.selectList(req);
		PageInfo<SystemLogVo> pageInfo = new PageInfo<SystemLogVo>(list);
		
		model.addAttribute("list",list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
		List<SystemLogVo> list = systemLogService.selectAll(req);
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "useStat")
	@ResponseBody
	public JsonResponse useStat(@RequestEgovMap EgovMap req, Model model) {
		
		List<SystemLogVo> list = systemLogService.useStat(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}