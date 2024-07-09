package kepco.cms.member;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.member.privacy.log.PrivacyLogService;
import kepco.cms.sec.role.RoleService;
import kepco.cms.sec.role.RoleVo;
import kepco.cms.sec.role.log.RoleLogService;
import kepco.cms.sec.role.log.RoleLogVo;
import kepco.cms.setting.SettingService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.ExcelUtil;
import kepco.util.StrUtil;
/**
 * 회원 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService memberService;
	
	@Autowired
	RoleLogService roleLogService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PrivacyLogService privacyLogService;

	@Autowired
	SettingService settingService;

	@Value("${global.file.base-path}")
	private Path baseFilePath;
	
	/**
	 * 회원 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/list";
		
		model.addAttribute("req",req);
		
		return viewName;
	}
	
	/**
	 * 회원 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/form";
		
		MemberVo vo = null;
		int logVo = 0;
		
		if(!StrUtil.isEmpty(req.get("memberIdx")))	{
				vo = memberService.select(req);
				
				if(StrUtil.toInt(req.get("memberIdx")) > 0) {
					req.put("requestType", "VIEW");
					logVo = privacyLogService.insert(req);
				}
		}
		
		List<RoleVo> roleList = roleService.selectAll(req);
		
		model.addAttribute("req",req);
		model.addAttribute("roleList",roleList);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 회원 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<MemberVo> list = memberService.selectAll(req);
		model.addAttribute("list",list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 회원 저장
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		//IP, 로그인 idx 불러오기
		HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();;
		HttpSession session = http.getSession(true);

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MemberVo memberVo = mapper.convertValue(req, MemberVo.class);
		RoleLogVo roleLogVo = new RoleLogVo();
		if (StrUtil.isBlank(memberVo.getMemberNm())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "성명은 필수입력 입니다.");
		}
        
		int result = 0 ;
		int logVo = 0;
		
		//세션으로 memberIdx 값 불러오기
		String idx = StrUtil.toStr(session.getAttribute("memberIdx"));
		
		if(StrUtil.isEmpty(req.get("memberIdx")))	{
	        //회원가입 아이디 보안금지어 검증
	        int denyCnt = 0;
	        denyCnt = settingService.secureVaildate(memberVo.getMemberId());
	        if (denyCnt > 0) {
	            throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, memberVo.getMemberNm() + " 해당 아이디로 계정을 생성 할 수 없습니다.(금지어)");
	        }
	        
			memberVo.setInsertIdx(StrUtil.getSessionIdx());
			memberVo.setInsertIp(StrUtil.getClientIP());
			result = memberService.insert(memberVo);
		}else { 
			if(!memberVo.getMemberRole().equals(memberVo.getMemberRoleOG())) { //역할이 변경된 경우
				roleLogVo.setBeforeRole(memberVo.getMemberRoleOG()); // 변경 전 역할 세팅
				roleLogVo.setAfterRole(memberVo.getMemberRole()); // 변경 후 역할 세팅
				roleLogVo.setMemberIdx(StrUtil.toInt(req.get("memberIdx")));
				roleLogVo.setInsertIdx(StrUtil.getSessionIdx()); // 행위자 IDX
				roleLogVo.setInsertIp(StrUtil.getClientIP());
				roleLogService.insert(roleLogVo);
			}
			if(memberVo.getMemberRole().contains("SUPER")) { //지정된 권한이 최고관리자일 경우
				memberVo.setMemberType2("3");
			}
			
			memberVo.setUpdateIdx(StrUtil.getSessionIdx());
			memberVo.setUpdateIp(StrUtil.getClientIP());
			result = memberService.update(memberVo);
			// 개인정보 수정 로그
			req.put("requestType", "UPDATE");
			
			if(!req.get("memberRoleOG").equals(req.get("memberRole"))) {
				String requestDesc = req.get("memberRoleOG") + " -> " + req.get("memberRole");
				req.put("requestDesc", requestDesc);
				}
			if(!StrUtil.isEmpty(req.get("memberIdx")))
				logVo = privacyLogService.insert(req);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(memberVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 회원 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		int copyResult = 0;
		int logVo = 0;
		
		if (StrUtil.isEmpty(req.get("memberIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		req.put("requestType", "DELETE");
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = memberService.delete(req);
		copyResult = memberService.deleteCopy(req);
		model.addAttribute("resultCnt", result);
		logVo = privacyLogService.insert(req);
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 로그인 횟수 초기화
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "failReset")
	@ResponseBody
	public JsonResponse failReset(@RequestEgovMap EgovMap req, Model model) {
		
		int logVo = 0;
		model.addAttribute("req", req);
		int result = memberService.failReset(StrUtil.toInt(req.get("memberIdx")));
		
		req.put("requestType", "UPDATE");
		req.put("requestDesc", "로그인실패횟수 초기화");
		
		logVo = privacyLogService.insert(req);
		model.addAttribute("resultCnt", result);
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 권한 셀렉트박스
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "power")
	public String power(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/power";
		
		MemberVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("memberIdx")))	{
				vo = memberService.select(req);
		}
		
		List<RoleVo> roleList = roleService.selectAll(req);
		
		model.addAttribute("req",req);
		model.addAttribute("roleList",roleList);
		model.addAttribute("vo",vo);
		
		return viewName;
	}

	@RequestMapping("download")
	@ResponseBody
	public void download(@RequestEgovMap EgovMap req, Model model, HttpServletResponse response) throws IOException {
		model.addAttribute("req", req);

		String fileName = "회원목록_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String filePath = baseFilePath + "/excel/memberExcel.xlsx";

		List<MemberVo> list = memberService.selectAll(req);

		List<Map<String, Object>> data = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> tmpData = new LinkedHashMap<>();
			tmpData.put("번호", (i + 1));
			tmpData.put("아이디", list.get(i).getMemberId());
			tmpData.put("성명", list.get(i).getMemberNm());
			tmpData.put("수강수", list.get(i).getRegistCnt());
			tmpData.put("등록일시", list.get(i).getInsertDate() == null ? "-" : list.get(i).getInsertDate());
			tmpData.put("수정일시", list.get(i).getUpdateDate() == null ? "-" : list.get(i).getUpdateDate());
			tmpData.put("최종로그인", list.get(i).getLoginDate() == null ? "-" : list.get(i).getLoginDate());
			tmpData.put("승인여부", list.get(i).getMemberApproval() == null ? "미승인" : list.get(i).getMemberApproval().equals("y") ? "승인" : "미승인");
			data.add(i, tmpData);
		}

		ExcelUtil.makeExcelByTemplate(filePath, fileName, data, response);
	}
}
