package kepco.lms.edu.stat.chart;
import java.util.ArrayList;
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
import kepco.lms.edu.stat.EdustatVo;
import kepco.util.StrUtil;

@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/stat/chart")
public class AdminEdustatChartController {

	@Autowired
	EdustatService edustatService;
	
	@Autowired
	DetailService detailService;
	
	@RequestMapping(value = "playTime")
	@ResponseBody
	public JsonResponse playTime(@RequestEgovMap EgovMap req, Model model) {

		EdustatVo vo = edustatService.playTime(req);
		
		model.addAttribute("vo", vo);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
	
//	@RequestMapping(value = "riskCount")
//	@ResponseBody
//	public JsonResponse riskCount(@RequestEgovMap EgovMap req, Model model) {

		
//		List<EventVo> list = eventService.selectAll(req);
		
//		model.addAttribute("list", list);
//		model.addAttribute("req", req);
//		return new JsonResponse.Builder().model(model).build();
//	}
	
	@RequestMapping(value = "riskFactorByPeriod")
	@ResponseBody
	public JsonResponse riskFactorByPeriod(@RequestEgovMap EgovMap req, Model model) {

		String period = StrUtil.toStr(req.get("period"));
		int numberPart = Integer.parseInt(period.substring(0, 1));
		String stringPart = period.substring(1); // 나머지 부분 추출
		String unit = StrUtil.toStr(req.get("unit"));
		int periodLength = 0;
		int repeat = 0;
		
		if(stringPart.equals("MONTH")) { //기간 단위가 월 인경우 4주로 환산
			if(numberPart >= 6) { //기간단위가 월이지만 6개월 이상일경우 개월로 환산
				periodLength = 1;
			}else {
				periodLength = 4; //개월당 4주차
			}
		}else if(stringPart.equals("YEAR")) { //기간 단위가 년 인경우 12개월로 환산
			periodLength = 12;
		}
		repeat = numberPart * periodLength;
		
		List<EdustatVo> list = new ArrayList<EdustatVo>();
		
		for (int i = repeat, j = 1; i >= 1; i--, j++) {
			EgovMap egovMap = new EgovMap();
			if(!StrUtil.isEmpty(req.get("detailIdx"))) {
				egovMap.put("detailIdx", req.get("detailIdx"));
			}
			egovMap.put("startPeriod", i);
			egovMap.put("endPeriod", i-1);
			egovMap.put("unit", unit);
			EdustatVo edustatVo = edustatService.riskFactorByPeriod(egovMap);
			edustatVo.setTeom(j);
			list.add(edustatVo);
			
		}
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "muscleActive")
	@ResponseBody
	public JsonResponse muscleActive(@RequestEgovMap EgovMap req, Model model) {

		
//		List<EventVo> list = eventService.selectAll(req);
		
//		model.addAttribute("list", list);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "fallRisk")
	@ResponseBody
	public JsonResponse failRisk(@RequestEgovMap EgovMap req, Model model) {

		
//		List<EventVo> list = eventService.selectAll(req);
		
//		model.addAttribute("list", list);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
	
	
	@RequestMapping(value = "accidentReason")
	@ResponseBody
	public JsonResponse accidentReason(@RequestEgovMap EgovMap req, Model model) {

		List<EdustatVo> list = new ArrayList<EdustatVo>();
		
		EdustatVo statVo = edustatService.accidentReasonRank(req); //연간 사고원인 5개 구하기
		
		for (int i = 12, j = 1; i >= 1; i--, j++) { //월간 사고원인의 발생횟수 구하기
			EgovMap egovMap = new EgovMap();
			if(!StrUtil.isEmpty(req.get("detailIdx"))) {
				egovMap.put("detailIdx", req.get("detailIdx"));
			}
			egovMap.put("startPeriod", i);
			egovMap.put("endPeriod", i-1);
			egovMap.put("accidentReasonRank1", statVo.getAccidentReasonRank1());
			egovMap.put("accidentReasonRank2", statVo.getAccidentReasonRank2());
			egovMap.put("accidentReasonRank3", statVo.getAccidentReasonRank3());
			egovMap.put("accidentReasonRank4", statVo.getAccidentReasonRank4());
			egovMap.put("accidentReasonRank5", statVo.getAccidentReasonRank5());
			
			EdustatVo edustatVo = edustatService.accidentReasonRankCnt(egovMap);
			edustatVo.setTeom(j);
			list.add(edustatVo); 
			
		}
		
		model.addAttribute("list", list);
		model.addAttribute("vo", statVo);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "shareByRole")
	@ResponseBody
	public JsonResponse shareByRole(@RequestEgovMap EgovMap req, Model model) {

		EdustatVo edustatVo = edustatService.shareByRole(req);
		
		model.addAttribute("vo", edustatVo);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "shareByAccident")
	@ResponseBody
	public JsonResponse shareByAccident(@RequestEgovMap EgovMap req, Model model) {

		List<EdustatVo> list = new ArrayList<EdustatVo>();
		list = edustatService.shareByAccident(req);
		
		if(list.size() < 1) {
			EdustatVo edustatVo = new EdustatVo();
			edustatVo.setAccidentType("no data");
			edustatVo.setAccidentCnt(0);
			list.add(edustatVo);
		}

		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "accidentLocation")
	@ResponseBody
	public JsonResponse accidentLocation(@RequestEgovMap EgovMap req, Model model) {
		
		List<EdustatVo> list = new ArrayList<EdustatVo>();
		list = edustatService.accidentLocation(req);
		
		if(list.size() < 1) {
			EdustatVo edustatVo = new EdustatVo();
			edustatVo.setAccidentLocation("no data");
			edustatVo.setAccidentLocationCnt(0);
			list.add(edustatVo);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "objectFactor")
	@ResponseBody
	public JsonResponse objectFactor(@RequestEgovMap EgovMap req, Model model) {

		int accidentObjectFactorMax = 0;
		List<EdustatVo> list = new ArrayList<EdustatVo>();
		list = edustatService.accidentObjectFactor(req); //사고 오브젝트 요인 3개구하기
		
		if(list.size() < 1) {
			EdustatVo edustatVo = new EdustatVo();
			edustatVo.setAccidentObjectFactor("no data");
			edustatVo.setAccidentObjectFactorCnt(0);
			list.add(edustatVo);
			
		}else {
			for (EdustatVo vo : list) {
				if(accidentObjectFactorMax < vo.getAccidentObjectFactorCnt()) {
					accidentObjectFactorMax = vo.getAccidentObjectFactorCnt();
				}
			}
		}
		
		model.addAttribute("list", list);
		model.addAttribute("accidentObjectFactorMax", accidentObjectFactorMax);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "accidentType")
	@ResponseBody
	public JsonResponse accidentType(@RequestEgovMap EgovMap req, Model model) {
		
		Map<String, List<EduStatChartVo>> accidentTypeMap = new LinkedHashMap<>();
		List<String> accidentTypeList = edustatService.accidentTypeNm(req);
		int listSize = accidentTypeList.size();
		for (String accidentType : accidentTypeList) {
			
			req.remove("accidentType");
			req.put("accidentType", accidentType);
			List<EduStatChartVo> edustatList = edustatService.accidentType(req);
			
			accidentTypeMap.put(accidentType, edustatList);
		}
		
		model.addAttribute("list", accidentTypeMap);
		model.addAttribute("listSize", listSize);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "accidentCause")
	@ResponseBody
	public JsonResponse accidentCause(@RequestEgovMap EgovMap req, Model model) {
		
		List<EduStatChartVo> edustatList = edustatService.accidentCause(req);
		
		
		model.addAttribute("list", edustatList);
		
		return new JsonResponse.Builder().model(model).build();
	}

	@RequestMapping(value = "riskStatusEs")
	@ResponseBody
	public JsonResponse riskStatusEs(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "1");
		req.put("riskLevel", "2");
		EduStatChartVo vo = edustatService.riskStatusByTeam(req);
		
		
		model.addAttribute("vo", vo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskStatusFall")
	@ResponseBody
	public JsonResponse riskStatusFall(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "2");
		req.put("riskLevel", "1");
		EduStatChartVo vo1 = edustatService.riskStatusByTeam(req);
		req.remove("riskLevel");
		req.put("riskLevel", "2");
		EduStatChartVo vo2 = edustatService.riskStatusByTeam(req);
		
		model.addAttribute("vo1", vo1);
		model.addAttribute("vo2", vo2);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskStatusCare")
	@ResponseBody
	public JsonResponse riskStatusCare(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "3");
		EduStatChartVo vo = edustatService.riskStatusByTeam(req);
		
		model.addAttribute("vo", vo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskStatusLoad")
	@ResponseBody
	public JsonResponse riskStatusLoad(@RequestEgovMap EgovMap req, Model model) {
		
		req.put("riskFactor", "4");
		req.put("riskLevel", "2");
		EduStatChartVo vo1 = edustatService.riskStatusByTeam(req);
		req.remove("riskLevel");
		req.put("riskLevel", "3");
		EduStatChartVo vo2 = edustatService.riskStatusByTeam(req);
		req.remove("riskLevel");
		req.put("riskLevel", "4");
		EduStatChartVo vo3 = edustatService.riskStatusByTeam(req);
		req.remove("riskLevel");
		req.put("riskLevel", "5");
		EduStatChartVo vo4 = edustatService.riskStatusByTeam(req);
		
		model.addAttribute("vo1", vo1);
		model.addAttribute("vo2", vo2);
		model.addAttribute("vo3", vo3);
		model.addAttribute("vo4", vo4);
		
		return new JsonResponse.Builder().model(model).build();
	}
}