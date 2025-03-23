package kepco.lms.vod.stat;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.member.MemberService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "lms/vod/stat")
public class VodStatController {
	
	@Autowired
	VodStatService vodStatService;
	
	@Autowired
	MemberService memberService;
	
	//TODO 실감훈련 통계 개발 필요
	
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "lms/vod/stat/list";
//		List<EdustatVo> list = statService.selectAll(req);
//		model.addAttribute("list", list);
		
		model.addAttribute("req", req);

		return viewName;
	}

//	@RequestMapping(value = "moduleList")
//	public String moduleList(@RequestEgovMap EgovMap req, Model model) throws Exception {
//		
//		String viewName = "lms/vod/stat/moduleList";
//		
//		model.addAttribute("req", req);
//		
//		return viewName;
//	}
//	
//	@RequestMapping(value = "memberList")
//	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
//		
//		String viewName = "lms/vod/stat/memberList";
//		
//		model.addAttribute("req", req);
//		
//		return viewName;
//	}
	
//	@RequestMapping(value = "form")
//	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
//		
//		String viewName = "lms/vod/stat/form";
//
//		VodStatVo vo = null;
//		
//		if(!StrUtil.isEmpty(req.get("statIdx"))) {
//			vo = vodStatService.select(req);
//		}
//
//		model.addAttribute("req",req);
//		model.addAttribute("vo",vo);
//		
//		return viewName;
//	}

	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
//		List<VodStatVo> list = vodStatService.selectAll(req);
//		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}