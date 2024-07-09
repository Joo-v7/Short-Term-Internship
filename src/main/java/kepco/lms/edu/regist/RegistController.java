package kepco.lms.edu.regist;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import kepco.cms.board.post.PostVo;
import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.team.TeamService;
import kepco.lms.edu.team.TeamVo;
import kepco.util.StrUtil;
/**
 * 신청자 관리 */
@Controller
@RequestMapping(value = "lms/edu/regist")
public class RegistController {
	
	@Autowired
	RegistService registService;
	
	@Autowired
	DetailService detailService;
	
	@Autowired
	TeamService teamService;

	@Autowired
	MemberService memberService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TemplateService templateService;
	
	/**
	 * 신청자 목록
	 * @param req
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

		String viewName = "lms/edu/regist/list";
	    
	    if(StrUtil.isEmpty(req.get("offset"))){
	    	req.put("offset", 0);
	    }
	    
	    if(StrUtil.isEmpty(req.get("pageNum"))){
	    	req.put("pageNum", "1");
	    }
	    
	    if(StrUtil.isEmpty(req.get("scKey1"))){
	    	req.put("scKey1", "");
	    }
	    
	    if(StrUtil.isEmpty(req.get("scKey2"))){
	    	req.put("scKey2", "");
	    }

	    if(StrUtil.isEmpty(req.get("scWord"))){
	    	req.put("scWord", "");
	    }
	    
	    
		
		List<RegistVo> list = registService.mySelectAll(req);
		
		int listCnt = StrUtil.toInt(list.size(),0);
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		
		// 페이징 객체
		Paging paging = new Paging();
		paging.setCri(cri);
		paging.setTotalCount(listCnt, pageNum);
		
		model.addAttribute("paging", paging);
		
		model.addAttribute("listTotal",listCnt);
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	/**
	 * 신청자 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "lms/edu/regist/form";
		
		RegistVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("registIdx"))) {
			vo = registService.select(req);
		}
		
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 훈련 신청 목록
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
		
 		String viewName = "lms/edu/regist/list";

		if(StrUtil.isEmpty(req.get("offset"))){ req.put("offset", 0); }
		if(StrUtil.isEmpty(req.get("pageNum"))){ req.put("pageNum", "1"); }
		if(StrUtil.isEmpty(req.get("scKey"))){ req.put("scKey", ""); }
		if(StrUtil.isEmpty(req.get("scWord"))){ req.put("scWord", ""); }
		
		req.put("scMemberIdx",StrUtil.getSessionIdx());
		req.put("scDetailIdx",req.get("detailIdx"));
		req.put("scCurrentTime",StrUtil.getCurrentTime());
		
		List<RegistVo> list = registService.selectMyList(req);
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
	 * 신청자 명단
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "memberList")
	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "lms/edu/regist/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	/**
	 * 신청 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		if("myList".equals(req.get("type"))) {
			req.put("scMemberIdx",StrUtil.getSessionIdx());
		}else if("memberList".equals(req.get("type"))) {
			req.put("scDetailIdx",req.get("detailIdx"));
		}
		
		List<RegistVo> list = registService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 훈련 신청 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		if(!"USER".equals(StrUtil.getUserRole())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "학습자가 아닌 회원은 훈련신청을 할 수 없습니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RegistVo registVo = mapper.convertValue(req, RegistVo.class);
		EgovMap regMap = new EgovMap();
		regMap.put("scMemberIdx",registVo.getMemberIdx());
		regMap.put("scDetailIdx",registVo.getDetailIdx());
		RegistVo originRegistVo = registService.select(regMap);
		if(originRegistVo != null) { //기존 승인정보가 있으면
			registVo.setRegistIdx(originRegistVo.getRegistIdx());
			if(originRegistVo.getRegistState().equals("1")) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "해당 훈련은 이미 수강 신청 되었습니다.");
			}else if(originRegistVo.getRegistState().equals("2")) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "해당 훈련은 이미 수강 승인 되었습니다.");
			}else {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "해당 훈련에 대한 수강이 취소 되었습니다. 자세한 사항은 관리자에게 문의 바랍니다.");
			}
			
		}	
		DetailVo detailVo = detailService.select(req);
		
		String detailState = detailVo.getDetailState();
		String check = null;
		int duplicateCnt = 0;
		int result = 0 ;
		
		if((registVo.getRegistIdx() == 0)) {
			
			if("2".equals(detailState)) {
				
				String eduBgnDateTime = detailVo.getEduAcceptBgnDate() + " " + detailVo.getEduAcceptBgnTime() + ":00"; 
				String eduEndDateTime = detailVo.getEduAcceptEndDate() + " " + detailVo.getEduAcceptEndTime() + ":00"; 
				
				int compare1 = eduBgnDateTime.compareTo(StrUtil.getCurrentTime());
				int compare2 = eduEndDateTime.compareTo(StrUtil.getCurrentTime());
				
				if(compare1 <= 0 && compare2 > 0 ) {
					
					req.put("scDetailIdx",registVo.getDetailIdx());
					req.put("scMemberIdx",StrUtil.getSessionIdx());
					duplicateCnt = registService.duplicate(req);
					if(duplicateCnt > 0) {
						
					}else {
						registVo.setMemberIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
						if("y".equals(detailVo.getEduAcceptAuto())) {
							registVo.setRegistState("2"); 
							// 자동승인 시 email 발송
							if(registVo.getRegistState().equals("2")) { //수강신청 승인일 시 email 알림 전송
								EmailVo emailVo = new EmailVo();
				    			EgovMap egovMap = new EgovMap();
				    			TemplateVo templateVo = new TemplateVo();
					        	egovMap.put("tempIdx", "6");
								templateVo = templateService.select(egovMap);
								
								egovMap.put("memberIdx", registVo.getMemberIdx()); //사용자 정보 가져오기
					        	MemberVo memberVo = memberService.select(egovMap);
					        	
								emailVo.setTo(memberVo.getMemberEmail());
//								emailVo.setFrom("초실감훈련센터");//email 바꾸기 필요
								emailVo.setSubject(templateVo.getTempTitle());
								emailVo.setText(templateVo.getTempContent());
								emailVo.setUseHtml(true);
								emailVo.setUseTemplate(true);
								
								Map<String, Object> map = Map.of("memberName",memberVo.getMemberNm(), "now", new Date(), "trainTitle", detailVo.getTrainTitle()); 
								emailVo.setTemplateMap(map);
								emailService.send(emailVo);
							}
						}else {
							registVo.setRegistState("1");
						}
						registVo.setInsertIdx(StrUtil.getSessionIdx());
						registVo.setInsertIp(StrUtil.getClientIP());
						result = registService.insert(registVo);
					}
				} else {
					check = "n";
					model.addAttribute("check", check);
				}
				
			} else {
				//승인이 안된 훈련은 미노출이므로 삭제 
			}
		} else {
			registVo.setUpdateIdx(StrUtil.getSessionIdx());
			registVo.setUpdateIp(StrUtil.getClientIP());
			result = registService.update(registVo);
		}
		model.addAttribute("detailState", detailState);
		model.addAttribute("duplicateCnt", duplicateCnt);
		model.addAttribute("resultCnt", result);
		model.addAttribute(registVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 훈련 신청 취소
	 * @param req
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "cancel")
	@ResponseBody
	public JsonResponse cancel(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RegistVo registVo = mapper.convertValue(req, RegistVo.class);
		
		int result = 0 ;
		
		if (StrUtil.isEmpty(registVo.getRegistIdx())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		registVo.setRegistState("3");
		registVo.setUpdateIdx(StrUtil.getSessionIdx());
		registVo.setUpdateIp(StrUtil.getClientIP());
		result = registService.update(registVo);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 훈련 신청 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("registIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = registService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}