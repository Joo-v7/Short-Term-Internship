package kepco.lms.edu.module.play;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.member.MemberService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
/**
 * 모듈 수행 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/module/play")
public class ModulePlayController {

	@Autowired
	ModulePlayService modulePlayService;
	
	@Autowired
	MemberService memberService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 모듈 수행 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/module/play/list";

		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 모듈 수행 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);
		
		List<ModulePlayVo> list = modulePlayService.selectList(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}

}