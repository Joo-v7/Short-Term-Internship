package kepco.lms.vod.learn;
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
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 영상교육 학습정보
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/learn")
public class AdminLearnController {
	
	@Autowired
	LearnService learnService;
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 영상교육 학습정보 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/learn/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 회원 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "memberList")
	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/learn/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	/**
	 * 영상교육 학습정보 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		LearnVo learnVo = mapper.convertValue(req, LearnVo.class);
		
		String viewName = "admin/lms/vod/learn/form";
		
		LearnVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("learnIdx"))) {
			vo = learnService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 영상교육 학습정보 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<LearnVo> list = learnService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 영상교육 학습정보 저장
	 * @param req
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile file, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		LearnVo learnVo = mapper.convertValue(req, LearnVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("learnIdx"))) {
			//관리자 수강 기능 없음 아마?
		}else {
			result = learnService.update(learnVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(learnVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 영상교육 학습정보 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("learnIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		result = learnService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}