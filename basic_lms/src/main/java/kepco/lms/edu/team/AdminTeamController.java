package kepco.lms.edu.team;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 팀 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/team")
public class AdminTeamController {
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 팀 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/team/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	
	/**
	 * 모듈 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "moduleList")
	public String moduleList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/team/moduleList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 학습자 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "memberList")
	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/team/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 팀 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/team/form";
		
		TeamVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("teamIdx"))) {
			vo = teamService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 팀 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<TeamVo> list = teamService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 팀 저장
	 * @param req
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile file, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TeamVo teamVo = mapper.convertValue(req, TeamVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("teamIdx"))) {
			if(StrUtil.isEmpty(teamVo.getTeamNm())) { teamVo.setTeamNm("팀"); }
			if(teamVo.getTeamMax() == 0) { teamVo.setTeamMax(4); }
			
			teamVo.setInsertIdx(StrUtil.getSessionIdx());
			teamVo.setInsertIp(StrUtil.getClientIP());
			result = teamService.insert(teamVo);
		}else {
			result = teamService.update(teamVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(teamVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 팀 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("teamIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		result = teamService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}