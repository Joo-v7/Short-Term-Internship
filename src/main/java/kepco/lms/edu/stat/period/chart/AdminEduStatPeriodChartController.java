package kepco.lms.edu.stat.period.chart;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.stat.EdustatService;
import kepco.lms.edu.stat.chart.EduStatChartVo;
import kepco.util.StrUtil;

@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/stat/period/chart")
public class AdminEduStatPeriodChartController {

	@Autowired
	EdustatService edustatService;
	
	@Autowired
	DetailService detailService;
	
	@RequestMapping(value = "trainStatByPeriod")
	@ResponseBody
	public JsonResponse trainStatByPeriod(@RequestEgovMap EgovMap req, Model model) {
		
		List<EduStatChartVo> list = edustatService.trainStatByPeriod(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "riskFactorStatByPeriod")
	@ResponseBody
	public JsonResponse riskFactorStatByPeriod(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		EduStatChartVo vo = mapper.convertValue(req, EduStatChartVo.class);
		
		Date startDate = vo.getStartDate();
        Date endDate = vo.getEndDate();
        
        // endDate가 null인 경우 현재 날짜로 설정
        if (endDate == null) {
            endDate = new Date();
        }
        
        // startDate가 null인 경우 현재 날짜에서 180일을 뺀 날짜로 설정
        if (startDate == null) {
            LocalDate thirtyDaysAgo = LocalDate.now().minusDays(180);
            startDate = Date.from(thirtyDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        
        // Date를 LocalDate로 변환
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 두 날짜 사이의 차이를 일 단위로 계산 => 날짜차이 계산해서 주단위로 올림 계산 예: 10일 -> 2주
        long differenceInDays = ChronoUnit.DAYS.between(localStartDate, localEndDate);
        double differenceInDaysDouble = (double) differenceInDays; // long을 double로 형변환
        
        double differenceInWeeks = differenceInDays / 7.0; // 주 단위
        double roundedRemainder = Math.ceil(differenceInWeeks); // 나머지 올림
        
        //최소 4주와 최대 24주 제한
        int periodWeek;
        if (roundedRemainder < 4) {
            periodWeek = 4;
        } else if (roundedRemainder > 24) {
            periodWeek = 24;
        } else {
            periodWeek = (int) roundedRemainder;
        }
		
//        EgovMap egovMap = new EgovMap();
//		egovMap.put("detailIdx", req.get("detailIdx"));
        req.remove("endDate");
		req.put("endDate", StrUtil.toStr(localEndDate));
		req.put("period", periodWeek);
		List<EduStatChartVo> list = edustatService.riskFactorStatByPeriod(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}