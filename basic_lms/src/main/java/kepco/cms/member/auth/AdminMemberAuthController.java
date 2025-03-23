package kepco.cms.member.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 관리자 인증
 */
@Controller
@RequestMapping("${global.admin-path}/cms/member/auth")
public class AdminMemberAuthController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MemberAuthService memberAuthService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	EmailService emailService;
	
	@Value("${global.admin-path}")
	private String adminPath;
	
	/**
	 * 권한에 따른 리디렉션 처리
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = {"", "form"})
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		// 이미 로그인된 상태라면 리디렉션
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		if (!(auth instanceof AnonymousAuthenticationToken)) {
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN_CMS"))) {
			log.debug("ROLE_ADMIN_CMS 권한이 존재하므로 대시보드로 리디렉션.");
			return "redirect:/" + this.adminPath + "/cms/stat/list"; 
		}
		else if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN_LMS"))) {
			log.debug("ROLE_ADMIN_LMS 권한이 존재하므로 대시보드로 리디렉션.");
			return "redirect:/" + this.adminPath + "/lms/edu/dashboard"; 
		}
		else if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN_RAS"))) {
			log.debug("ROLE_ADMIN_RAS 권한이 존재하므로 대시보드로 리디렉션.");
			return "redirect:/" + this.adminPath + "/ras/dashboard"; 
		}
		
		return "admin/cms/member/auth/form";
	}
	
	/**
	 * RAS 로그인 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "rasForm")
	public String rasForm(@RequestEgovMap EgovMap req, Model model) throws Exception {

		return "admin/ras/member/auth/form";
	}
	
	/**
	 * 관리자 회원가입
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "join")
	public String join(@RequestEgovMap EgovMap req, Model model) throws Exception {

		return "admin/cms/member/auth/join";
	}
	
	/**
	 * 관리자 회원 정보 찾기
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "find")
	public String find(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		return "admin/cms/member/auth/find";
	}
	
	/**
	 * 관리자 회원 찾기 email 인증
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "findByEmail")
	@ResponseBody
	public JsonResponse findByEmail(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MemberAuthVo authVo = mapper.convertValue(req, MemberAuthVo.class);
		String findType = "";
		String message = "";
		String memberName = "";
		model.addAttribute("req", req);
		
		int cnt = memberAuthService.authCountByEmail(req);
		if(cnt > 0) {
			
	        String generatedCode = generateRandomCode();
	        System.out.println("Generated Code: " + generatedCode);
	        authVo.setAuthKey(generatedCode);
	        
			if("y".equals(authVo.getFindPw())) {
				findType = "패스워드";
				authVo.setFindType("pw");
				memberName = authVo.getMemberId();
			}else {
				findType = "아이디";
				authVo.setFindType("id");
				memberName = authVo.getMemberNm();
			}
			memberAuthService.createAuthKey(authVo);
			
			//email 인증 보내기 추가
			EmailVo emailVo = new EmailVo();
			EgovMap egovMap = new EgovMap();
			TemplateVo templateVo = new TemplateVo();
			egovMap.put("tempIdx", "8");
			templateVo = templateService.select(egovMap);
			emailVo.setTo(authVo.getMemberEmail());
//			emailVo.setFrom("초실감훈련센터");//email 바꾸기 필요
			emailVo.setSubject(templateVo.getTempTitle());
			emailVo.setText(templateVo.getTempContent());
			emailVo.setUseHtml(true);
			emailVo.setUseTemplate(true);
			
			Map<String, Object> map = Map.of("memberName", memberName, "now", new Date(), "findType", findType, "authKey", authVo.getAuthKey()); //템플릿 내용 할당
			emailVo.setTemplateMap(map);
			emailService.send(emailVo);
			
			
			message = "이메일로 인증번호를 전송했습니다.";
		}else {
			message = "입력하신 정보에 맞는 아이디가 존재하지 않습니다.";
		}
		model.addAttribute("message",message);
		model.addAttribute("cnt",cnt);
		model.addAttribute("authIdx",authVo.getAuthIdx());
		model.addAttribute("findType",findType);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	//랜덤 인증번호 생성
    public static String generateRandomCode() {
    	final String CHARACTERS = "0123456789";
    	final int CODE_LENGTH = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }
    
    /**
	 * 관리자 회원 찾기 인증번호 비교
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "verification")
	@ResponseBody
	public JsonResponse verification(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		boolean verification = false;
		model.addAttribute("req", req);
		MemberAuthVo authVo = memberAuthService.select(req);
		if((authVo.getAuthKey()).equals(StrUtil.toStr(req.get("certNumber")))) {
			verification = true;
		}
		
		model.addAttribute("verification", verification);
		model.addAttribute("auth", authVo.getAuthIdx());
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 관리자 회원 찾기 결과
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "find_result")
	public String findResult(@RequestEgovMap EgovMap req, Model model) throws Exception {
	    
	    model.addAttribute("req", req);
	    req.put("authIdx", req.get("auth"));
	    MemberAuthVo authVo = memberAuthService.select(req);
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime insertDate = LocalDateTime.parse(authVo.getInsertDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    List<MemberVo> list = null;
	    
        // 3분 이내 여부 확인
        if (now.minusMinutes(3).isBefore(insertDate)) {
    	    req.put("memberEmail", authVo.getMemberEmail());
    	    req.put("memberNm", authVo.getMemberNm());
    	    req.put("memberId", authVo.getMemberId());
    	    req.put("findType", authVo.getFindType());
            list = memberAuthService.memberList(req);

    	    for (MemberVo member : list) {
    	        member.setMemberPw(null);
    	    }
        } else {
            throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "인증 유효시간이 지났습니다.");
        }
	    
	    model.addAttribute("list", list);
	    
		return "admin/cms/member/auth/find_result";
	}
	
	/**
	 * 관리자 비밀번호 변경
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "changePw")
	@ResponseBody
	public JsonResponse changePw(@RequestEgovMap EgovMap req, Model model) throws Exception {
	    
	    MemberAuthVo authVo = memberAuthService.select(req);
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime insertDate = LocalDateTime.parse(authVo.getInsertDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    String message = "";
	    MemberVo vo = null;
	    boolean successYn = false;
	    
	    if(req.get("memberId").equals(authVo.getMemberId())) { // 입력폼에 입력된 ID 값과 인증한 값 ID 가 같은지 비교
	        // 3분 이내 여부 확인
	        if (now.minusMinutes(3).isBefore(insertDate)) {
	    	    req.put("memberEmail", authVo.getMemberEmail());
	    	    req.put("memberNm", authVo.getMemberNm());
	    	    req.put("memberId", authVo.getMemberId());
	    	    req.put("findType", authVo.getFindType());
	    	    vo = memberAuthService.memberSelect(req);
	    	    
	    	    if("2".equals(vo.getMemberType2())) { // 비밀번호를 변경하려는 사람이 학습자인지, 교수자인지 검증
	    	    	vo.setMemberPw(StrUtil.toStr(req.get("certNewPw")));
		    	    vo.setUpdateIdx(StrUtil.getSessionIdx());
					vo.setUpdateIp(StrUtil.getClientIP());
		    	    memberService.update(vo);
		    	    
		    	    message = "비밀번호 변경에 성공하였습니다.";
		    	    successYn = true;
	    	    }else {
	    	    	message = "해당 계정은 관리자페이지 접근권한이 없습니다.";
	    	    }

	        } else {
	            message = "인증 유효기간이 지났습니다.";
	        }
		    
	    }else {
	    	message = "입력된 ID와 인증을 진행한 ID가 일치하지 않습니다.";
	    }
	    model.addAttribute("message", message);
        model.addAttribute("successYn", successYn);
	    return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 관리자 비밀번호 확인
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "pwForm")
	public String pwForm(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		return "admin/cms/member/auth/pwForm";
	}
	
	/**
	 * 내 정보
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "myPage")
	public String myPage(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		MemberVo vo = null;
		
		if(!StrUtil.isEmpty(StrUtil.getSessionIdx()))	{
			req.put("memberIdx", StrUtil.getSessionIdx());
			vo = memberService.select(req);
		}
		
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return "admin/cms/member/auth/myPage";
	}
}
