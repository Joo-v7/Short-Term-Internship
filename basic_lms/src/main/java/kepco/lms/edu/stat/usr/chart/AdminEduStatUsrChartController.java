package kepco.lms.edu.stat.usr.chart;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.stat.EdustatService;
import kepco.lms.edu.stat.chart.EduStatChartVo;
import kepco.lms.edu.stat.usr.EduStatUsrVo;

@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/stat/usr/chart")
public class AdminEduStatUsrChartController {

	@Autowired
	EdustatService edustatService;
	
	@Autowired
	DetailService detailService;
	
	@RequestMapping(value = "riskStatusEs")
	@ResponseBody
	public JsonResponse riskStatusEs(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "1");
		req.put("riskLevel", "2");
		EduStatChartVo vo = edustatService.riskStatusByUsr(req);
		
		
		model.addAttribute("vo", vo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskStatusFall")
	@ResponseBody
	public JsonResponse riskStatusFall(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "1");
		req.put("riskLevel", "2");
		EduStatChartVo vo1 = edustatService.riskStatusByUsr(req);
		req.remove("riskLevel");
		req.put("riskLevel", "2");
		EduStatChartVo vo2 = edustatService.riskStatusByUsr(req);
		
		model.addAttribute("vo1", vo1);
		model.addAttribute("vo2", vo2);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskStatusCare")
	@ResponseBody
	public JsonResponse riskStatusCare(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "3");
		EduStatChartVo vo = edustatService.riskStatusByUsr(req);
		
		model.addAttribute("vo", vo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskStatusLoad")
	@ResponseBody
	public JsonResponse riskStatusLoad(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "4");
		req.put("riskLevel", "2");
		EduStatChartVo vo1 = edustatService.riskStatusByUsr(req);
		req.remove("riskLevel");
		req.put("riskLevel", "3");
		EduStatChartVo vo2 = edustatService.riskStatusByUsr(req);
		req.remove("riskLevel");
		req.put("riskLevel", "4");
		EduStatChartVo vo3 = edustatService.riskStatusByUsr(req);
		req.remove("riskLevel");
		req.put("riskLevel", "5");
		EduStatChartVo vo4 = edustatService.riskStatusByUsr(req);
		
		model.addAttribute("vo1", vo1);
		model.addAttribute("vo2", vo2);
		model.addAttribute("vo3", vo3);
		model.addAttribute("vo4", vo4);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "accidentRateByTrain")
	@ResponseBody
	public JsonResponse accidentRateByTrain(@RequestEgovMap EgovMap req, Model model) {
		
		List<EduStatChartVo> list = edustatService.accidentRateByTrain(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "accidentReasonAnalysis")
	@ResponseBody
	public JsonResponse accidentReasonAnalysis(@RequestEgovMap EgovMap req, Model model) {
		
		Map<String, List<EduStatChartVo>> roleLocationMap = new LinkedHashMap<>();
		Map<String, List<EduStatChartVo>> locationFactorMap = new LinkedHashMap<>();
		Map<String, List<EduStatChartVo>> factorTypeMap = new LinkedHashMap<>();
		List<String> accidentReasonAnalysisRoleList = edustatService.accidentReasonAnalysisRoleList(req); //사고가 발생한 역할 목록
		
		int listSize = accidentReasonAnalysisRoleList.size();
		for (String workRole : accidentReasonAnalysisRoleList) { // 학습자 역할별 오브젝트 요인 목록 구하기
			
			req.remove("workRole");
			req.remove("eventLocation");
			req.remove("objectFactor");
			req.put("workRole", workRole);
			List<EduStatChartVo> accidentReasonAnalysisLocation = edustatService.accidentReasonAnalysisLocation(req);
			
			for (EduStatChartVo eventLocationVo : accidentReasonAnalysisLocation) { // 학습자 오브젝트 요인별 사고유형 구하기
				
				req.remove("eventLocation");
				req.remove("objectFactor");
				req.put("eventLocation", eventLocationVo.getEventLocation());

				List<EduStatChartVo> accidentReasonAnalysisFactor = edustatService.accidentReasonAnalysisFactor(req);
				for (EduStatChartVo objectFactorVo : accidentReasonAnalysisFactor) { // 학습자 오브젝트 요인별 사고유형 구하기
					
					req.remove("objectFactor");
					req.put("objectFactor", objectFactorVo.getObjectFactor());
					
					List<EduStatChartVo> accidentReasonAnalysisType = edustatService.accidentReasonAnalysisType(req);
					factorTypeMap.put(objectFactorVo.getObjectFactor(), accidentReasonAnalysisType);
				}	
				locationFactorMap.put(eventLocationVo.getEventLocation(), accidentReasonAnalysisFactor);
			}	
			
			
			switch(workRole) {
			    case "1":
			        workRole = "주작업자";
			        break;
			    case "2":
			    	workRole = "보조작업자";
			        break;
			    case "3":
			    	workRole = "지상작업자";
			        break;
			    case "4":
			    	workRole = "작업책임자";
			        break;
			    default:
			    	workRole = "UNKNOWN";
			}
			
			roleLocationMap.put(workRole, accidentReasonAnalysisLocation);
		}
		
		model.addAttribute("roleLocationMap", roleLocationMap);
		model.addAttribute("locationFactorMap", locationFactorMap);
		model.addAttribute("factorTypeMap", factorTypeMap);
		model.addAttribute("listSize", listSize);
		
		return new JsonResponse.Builder().model(model).build();
	}
}