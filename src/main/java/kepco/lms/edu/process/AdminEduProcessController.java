package kepco.lms.edu.process;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.code.CodeService;
import kepco.cms.code.CodeVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 훈련 콘텐츠
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/process")
public class AdminEduProcessController {
	
	@Autowired
	EduProcessService eduProcessService;
	
	@Autowired
	CodeService codeService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 훈련 절차 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		// 역할코드
		List<CodeVo> workRoleCodeList =  codeService.selectCodeList("workRole");
		model.addAttribute("workRoleCodeList", workRoleCodeList);
		
		String viewName = "admin/lms/edu/process/list";
		
		model.addAttribute("req", req);

		return viewName;
	}

	/**
	 * 훈련 절차 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/process/form";
		
		EduProcessVo vo = null;
		int depth = 0;
		
		if(!StrUtil.isEmpty(req.get("processIdx"))) {
			vo = eduProcessService.select(req);
		}
		if(!StrUtil.isEmpty(req.get("depth"))) {
			depth = StrUtil.toInt(req.get("depth"));
		}
		
		model.addAttribute("depth", depth);
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 훈련 절차 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<EduProcessVo> list = eduProcessService.selectAll(req);
		
		for (EduProcessVo eduProcessVo : list) {
		    String taskId = eduProcessVo.getTaskId();
		    
		    // taskId를 '_' 기준으로 분리하여 배열로 저장
		    String[] parts = taskId.split("_");
		    
		    // scenario 추출 (첫 번째 부분)
		    String scenario = parts[0];
		    
		    // moduleCd 추출 (첫 번째 부분과 두 번째 부분을 '_'로 연결)
		    String moduleCd = parts[0] + "_" + parts[1];
		    
		    // 추출한 값을 해당 객체에 설정
		    eduProcessVo.setScenario(scenario);
		    eduProcessVo.setModuleCd(moduleCd);
		    
		}

		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 훈련 절차 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		if(StrUtil.isBlank(req.get("processContents"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "절차내용은(는) 필수 입력입니다.");
		}
		
		// "Unrecognized field" 무시
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		EduProcessVo eduProcessVo = mapper.convertValue(req, EduProcessVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(eduProcessVo.getAiId())) { //aiId 값이 없으면 null 처리
			eduProcessVo.setAiId(null);
		}
		if(StrUtil.isEmpty(eduProcessVo.getTaskTool())) { //taskTool 값이 없으면 null 처리
			eduProcessVo.setTaskTool(null);
		}
		
		if(!validateTaskId(eduProcessVo.getTaskId())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "절차 ID가 유효한 형식이 아닙니다.");
		}
		if(eduProcessService.idCheck(eduProcessVo) > 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "중복된 절차 ID 입니다.");
		}
		
		if(eduProcessVo.getAiId() != null){
			if(!validateAiId(eduProcessVo.getAiId())) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "AI ID가 유효한 형식이 아닙니다.");
			}
			if(!validateIds(eduProcessVo.getAiId(), eduProcessVo.getTaskId())) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "AI ID는 작업 ID와 형식이 일치해야 합니다.");
			}
			if(eduProcessService.aiIdCheck(eduProcessVo) > 0) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "중복된 AI ID 입니다.");
			}
			String[] aiIdParts = eduProcessVo.getAiId().split("_");
			if (!aiIdParts[3].startsWith("AI")) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "AI ID가 유효한 형식이 아닙니다(AI).");
		    }
		}
		
		String[] taskIdParts = eduProcessVo.getTaskId().split("_");
		if (!taskIdParts[3].startsWith("MW")) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "절차 ID가 유효한 형식이 아닙니다(MW).");
	    }
		int step = Integer.parseInt(taskIdParts[2]);
		eduProcessVo.setStep(step); 
		
		char lastChar = eduProcessVo.getTaskId().charAt(eduProcessVo.getTaskId().length() - 1);
		eduProcessVo.setWorkRole(StrUtil.toStr(lastChar));
		
		
		if(StrUtil.isEmpty(req.get("processIdx"))) {
			eduProcessVo.setInsertIdx(StrUtil.getSessionIdx());
			eduProcessVo.setInsertIp(StrUtil.getClientIP());
			result = eduProcessService.insert(eduProcessVo);
		}else {
			eduProcessVo.setUpdateIdx(StrUtil.getSessionIdx());
			eduProcessVo.setUpdateIp(StrUtil.getClientIP());
			result = eduProcessService.update(eduProcessVo);
		}
		
		model.addAttribute(eduProcessVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 훈련 절차 ID 유효성 검사
	 * @param req
	 * @param model
	 * @return
	 */
	 public static boolean validateTaskId(String taskId) {
//		 String regex = "^T\\d{1,2}_T(0\\d{1}|[1-9]\\d{1})_\\d{2}_MW[1-4]$";
		 String regex = "^T\\d{1,2}_T\\d{2}_\\d{2}_MW[1-4]$";
		 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(taskId);
        return matcher.matches();
    }
	 
	 /**
	 * 훈련 절차 aiID 유효성 검사
	 * @param req
	 * @param model
	 * @return
	 */
	 public static boolean validateAiId(String aiId) {
//		 String regex = "^T\\d{1,2}_T(0\\d{1}|[1-9]\\d{1})_\\d{2}_MW[1-4]$";
		 String regex = "^T\\d{1,2}_T\\d{2}_\\d{2}_MW[1-4]$";
		 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aiId);

        return matcher.matches();
    }
	
	public static boolean validateIds(String aiId, String taskId) {
	    String modifiedTaskId = taskId.replace("MW", "AI");

	    return aiId.equals(modifiedTaskId);
	}

	 
	/**
	 * 훈련 절차 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("processIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = eduProcessService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}