package kepco.cms.stat.chart;
import java.util.ArrayList;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.stat.StatService;
import kepco.cms.stat.StatVo;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "${global.admin-path}/cms/stat/chart")
public class AdminStatChartController {

	@Autowired
	StatService statService;
	
	@RequestMapping(value = "visitWeek")
	@ResponseBody
	public JsonResponse visitWeek(@RequestEgovMap EgovMap req, Model model) {
		
		List<StatVo> list = statService.selectDateStat(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	@RequestMapping(value = "visitHour")
	@ResponseBody
	public JsonResponse visitHour(@RequestEgovMap EgovMap req, Model model) {
		
		List<StatVo> list = statService.selectHourStat(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "visitDevice")
	@ResponseBody
	public JsonResponse visitDevice(@RequestEgovMap EgovMap req, Model model) {
		
		StatVo vo = statService.selectDevice(req);
		
		model.addAttribute("vo", vo);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "visitCount")
	@ResponseBody
	public JsonResponse visitCount(@RequestEgovMap EgovMap req, Model model) {
		
		List<StatVo> list = new ArrayList<StatVo>();
		String[] scType = {"CMS", "LMS", "RAS"};
		
		for (String type : scType) {
            // 각각의 scType에 대한 처리
			EgovMap egovMap = new EgovMap();
			egovMap.put("scType", type);
			StatVo vo = statService.visitCount(egovMap);
			vo.setSystemType(type);
            
            // 다른 필요한 처리 추가 가능
            list.add(vo);
        }
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
}