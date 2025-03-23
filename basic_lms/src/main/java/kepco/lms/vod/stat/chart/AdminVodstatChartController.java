package kepco.lms.vod.stat.chart;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.lms.vod.stat.VodStatService;

@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/stat/chart")
public class AdminVodstatChartController {
	
	@Autowired
	VodStatChartService vodStatChartService;
	
	@RequestMapping(value = "shareByCategory")
	@ResponseBody
	public JsonResponse shareByCategory(@RequestEgovMap EgovMap req, Model model) {
		
		List<EgovMap> list = vodStatChartService.shareByCategory(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "vodKeyword")
	@ResponseBody
	public JsonResponse vodKeyword(@RequestEgovMap EgovMap req, Model model) {
		
		List<EgovMap> list = vodStatChartService.vodKeyword(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "graduateRate")
	@ResponseBody
	public JsonResponse graduateRate(@RequestEgovMap EgovMap req, Model model) {
		
		EgovMap vo = vodStatChartService.graduateRate(req);
		
		model.addAttribute("vo", vo);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
}