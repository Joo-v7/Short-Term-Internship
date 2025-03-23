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
import kepco.lms.vod.apply.ApplyService;
import kepco.lms.vod.content.ContentService;
import kepco.lms.vod.content.ContentVo;
import kepco.util.StrUtil;

@Controller
@RequestMapping(value = "lms/vod/learn")
public class LearnController {
	
	@Autowired
	LearnService learnService;
	
	@Autowired
	ApplyService applyService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ContentService contentService;
	
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "lms/vod/learn/list";
		
		model.addAttribute("req", req);

		return viewName;
	}

	@RequestMapping(value = "memberList")
	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "lms/vod/learn/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}

	@RequestMapping(value = "select")
	@ResponseBody
	public JsonResponse select(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		if (StrUtil.isEmpty(req.get("vodIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		if (StrUtil.isEmpty(req.get("contentIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		if(StrUtil.getSessionIdx() < 1 ) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "로그인 정보가 없습니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		LearnVo learnVo = mapper.convertValue(req, LearnVo.class);
		
		LearnVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			req.put("memberIdx", StrUtil.getSessionIdx());
			vo = learnService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", vo);
		return new JsonResponse.Builder().data(model).build();
	}
	
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		LearnVo learnVo = mapper.convertValue(req, LearnVo.class);
		
		String viewName = "lms/vod/learn/form";
		
		LearnVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("learnIdx"))) {
			vo = learnService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	

	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<LearnVo> list = learnService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile file, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		LearnVo learnVo = mapper.convertValue(req, LearnVo.class);

		if(StrUtil.getSessionIdx() < 1 ) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "로그인 정보가 없습니다.");
		}
		
		if(StrUtil.isEmpty(learnVo.getVodIdx())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "영상 정보가 없습니다.");
		}
		
		if(StrUtil.isEmpty(learnVo.getContentIdx())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "영상 콘텐츠 정보가 없습니다.");
		}
		
		int result = 0 ;
//		req.put("memberIdx",StrUtil.getSessionIdx());
		learnVo.setMemberIdx(StrUtil.getSessionIdx());
		LearnVo check = learnService.select(req);
		
		//수강 내역 있을 경우 조건
		if(check == null) {
			learnVo.setInsertIdx(StrUtil.getSessionIdx());
			learnVo.setInsertIp(StrUtil.getClientIP());
			result = learnService.insert(learnVo);
		}else {
			result = learnService.update(learnVo);
		}
		
		//영상교육 과정 수료 검증
		EgovMap egovMap = new EgovMap();
		egovMap.put("vodIdx", learnVo.getVodIdx());
		egovMap.put("memberIdx", learnVo.getMemberIdx());
		List<LearnVo> list = learnService.selectAll(egovMap);
		int needCnt = list.size(); 
		int graduateCnt = learnService.graduateCal(egovMap);
		
		if(graduateCnt >= needCnt ) {
			egovMap.put("learnState", "2");
			applyService.learnUpdate(egovMap);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(learnVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
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