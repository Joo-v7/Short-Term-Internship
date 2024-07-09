package kepco.lms.vod.apply;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.board.Criteria;
import kepco.cms.board.Paging;
import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.vod.VodService;
import kepco.lms.vod.VodVo;
import kepco.lms.vod.bundle.BundleService;
import kepco.lms.vod.bundle.BundleVo;
import kepco.lms.vod.content.ContentService;
import kepco.lms.vod.learn.LearnService;
import kepco.lms.vod.learn.LearnVo;
import kepco.util.StrUtil;
/**
 * 영상교육 신청 관리
 */
@Controller
@RequestMapping(value = "lms/vod/apply")
public class ApplyController {
	
	@Autowired
	ApplyService applyService;
	
	@Autowired
	VodService vodService;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	BundleService bundleService;
	
	@Autowired
	LearnService learnService;
	
	@Autowired
	MemberService memberService;
	/**
	 * 영상교육 신청 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "lms/vod/apply/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 영상교육 신청 조회
	 * @param req
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "lms/vod/apply/view";
		
		VodVo vo = null;
		List<BundleVo> bundleList = null;
		List<LearnVo> learnList = null;
		
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			vo = vodService.select(req);
			
			//콘텐츠 리스트
			req.put("vodIdx",vo.getVodIdx());
			req.put("memberIdx",StrUtil.getSessionIdx());
			
			bundleList = bundleService.selectAll(req);		
			learnList = learnService.selectAll(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		model.addAttribute("bundleList",bundleList);
		model.addAttribute("learnList",learnList);
		
		return viewName;
	}
	/**
	 * 영상교육 신청 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "lms/vod/apply/form";
		
		ApplyVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("applyIdx"))) {
			vo = applyService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	/**
	 * 내 영상교육 신청 목록
	 * @param req
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
    @PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "myList")
	public String myList(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "lms/vod/apply/list";
		
		if(StrUtil.isEmpty(req.get("offset"))){ req.put("offset", 0); }
	    if(StrUtil.isEmpty(req.get("pageNum"))){ req.put("pageNum", "1"); }
	    if(StrUtil.isEmpty(req.get("scKey"))){ req.put("scKey", ""); }
	    if(StrUtil.isEmpty(req.get("scWord"))){ req.put("scWord", ""); }
		
		req.put("scMemberIdx",StrUtil.getSessionIdx());
		req.put("scVodIdx",req.get("vodIdx"));
		req.put("scCurrentTime",StrUtil.getCurrentTime());
		
		List<ApplyVo> list = applyService.selectMyList(req);
		int listCnt = StrUtil.toInt(list.size(),0);
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		
		// 페이징 객체
		Paging paging = new Paging();
		paging.setCri(cri);
		paging.setTotalCount(listCnt, pageNum);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("listTotal",listCnt);
    
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
		
		String viewName = "lms/vod/apply/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 영상교육 신청 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		req.put("scVodIdx",req.get("vodIdx"));
		
		List<ApplyVo> list = applyService.selectAll(req);
		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 신청 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		if(!"USER".equals(StrUtil.getUserRole())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "학습자가 아닌 회원은 영상교육을 신청 할 수 없습니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ApplyVo applyVo = mapper.convertValue(req, ApplyVo.class);

		String detailState = null;
		int duplicateCnt = 0;
		long result = 0 ;
		
//		if(StrUtil.isEmpty(req.get("applyIdx"))) {
//			
//			req.put("scMemberIdx",StrUtil.getSessionIdx());
//			req.put("scVodIdx",applyVo.getVodIdx());
//			duplicateCnt = applyService.duplicate(req);
//			if(duplicateCnt > 0) {
//				
//			}else {
//				applyVo.setMemberIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
//				applyVo.setApplyState("1");
//				
//				applyVo.setInsertIdx(StrUtil.getSessionIdx());
//				applyVo.setInsertIp(StrUtil.getClientIP());
//				result = applyService.insert(applyVo);
//			}
//			
//		}else {
//			applyVo.setUpdateIdx(StrUtil.getSessionIdx());
//			applyVo.setUpdateIp(StrUtil.getClientIP());
//			result = applyService.update(applyVo);
//		}

		req.put("scMemberIdx",StrUtil.getSessionIdx());
		req.put("scVodIdx",applyVo.getVodIdx());
		duplicateCnt = applyService.duplicate(req);
		ApplyVo duplicateVo = applyService.select(req);
		
		if(duplicateCnt > 0) {
			
				
		}else {
			if(duplicateVo != null) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "해당 영상교육의 수강신청이 취소 되었습니다. 관리자에게 문의해 주시길 바랍니다.");
			}else {
				applyVo.setMemberIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
				applyVo.setApplyState("1");
				
				applyVo.setInsertIdx(StrUtil.getSessionIdx());
				applyVo.setInsertIp(StrUtil.getClientIP());
				result = applyService.insert(applyVo);
			}
		}
			
		model.addAttribute("detailState", detailState);
		model.addAttribute("duplicateCnt", duplicateCnt);
		model.addAttribute("resultCnt", result);
		model.addAttribute(applyVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 영상교육 신청 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		long result = 0 ;
		
		if (StrUtil.isEmpty(req.get("applyIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = applyService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 신청 취소
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "cancel")
	@ResponseBody
	public JsonResponse cancel(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ApplyVo applyVo = mapper.convertValue(req, ApplyVo.class);
		
		long result = 0 ;
		
		if (StrUtil.isEmpty(applyVo.getApplyIdx())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		applyVo.setApplyState("3");
		applyVo.setUpdateIdx(StrUtil.getSessionIdx());
		applyVo.setUpdateIp(StrUtil.getClientIP());
		result = applyService.update(applyVo);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}