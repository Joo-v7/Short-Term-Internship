package kepco.cms.sysinfoLog;
import java.util.HashMap;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "${global.admin-path}/cms/sysinfoLog")
public class AdminSysinfoLogController {

	@Autowired
	SysinfoLogService sysinfoLogService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 메트릭스 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
//		String a = this.getClass().getAnnotation(RequestMapping.class).value()[0];
//		String b = this.getClass().getEnclosingMethod().getAnnotation(RequestMapping.class).value()[0];
		
		String viewName = "admin/cms/sysinfoLog/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	
	/**
	 * 메트릭스 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
		List<SysinfoLogVo> list = sysinfoLogService.selectList(req);
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 현재시점의 주요 메트릭스 데이터 조회
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "currMetricsJson")
	@ResponseBody
	public JsonResponse currMetricsJson(@RequestEgovMap EgovMap req, Model model) {
		HashMap<String, Double> metrics = sysinfoLogService.currMetrics(req);
		model.addAttribute("metrics", metrics);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 현재시점의 주요 메트릭스 조회
	 * @param req
	 * @param model
	 * @return
	 */
	@SiteLayout
	@RequestMapping(value = "currMetrics")
	public String currMetricsView(@RequestEgovMap EgovMap req, Model model) {
		HashMap<String, Double> metrics = sysinfoLogService.currMetrics(req);
		model.addAttribute("metrics", metrics);
		model.addAttribute("req", req);
		String viewName = "admin/cms/sysinfoLog/currView";

		return viewName;
	}
	
	/**
	 * 메트릭스 차트 데이터 조회
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chartJson")
	@ResponseBody
	public JsonResponse chartJson(@RequestEgovMap EgovMap req, Model model) {
		 
		List<SysinfoLogVo> list = sysinfoLogService.chartList(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 평균 주요 메트릭스 조회
	 * @param req
	 * @param model
	 * @return
	 */
	/*
	@SiteLayout
	@RequestMapping(value = "avgMetrics")
	public String avgMetricsView(@RequestEgovMap EgovMap req, Model model) {
		HashMap<String, Double> metricsAvg = sysinfoLogService.avgByPeriod(req);
		model.addAttribute("metricsAvg", metricsAvg);
		model.addAttribute("req", req);
		String viewName = "admin/cms/sysinfoLog/avgView";

		return viewName;
	}
	*/
}