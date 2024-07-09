package kepco.lms.edu.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.EduService;
import kepco.lms.edu.EduVo;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.event.EventService;
import kepco.lms.edu.event.EventVo;
import kepco.lms.edu.module.play.ModulePlayService;
import kepco.lms.edu.module.play.ModulePlayVo;
import kepco.lms.edu.play.PlayService;
import kepco.lms.edu.play.PlayVo;
import kepco.lms.edu.play.team.PlayTeamService;
import kepco.lms.edu.process.EduProcessService;
import kepco.lms.edu.process.play.ProcessPlayService;
import kepco.lms.edu.process.play.ProcessPlayVo;
import kepco.util.StrUtil;

/**
 * 통계 관리
 */
@Controller
@RequestMapping(value = "lms/edu/stat")
public class EdustatController {

    @Autowired
    EdustatService statService;

    @Autowired
    MemberService memberService;

    @Autowired
    DetailService detailService;

    @Autowired
    EduService eduService;

    @Autowired
    PlayService playService;

    @Autowired
    ModulePlayService modulePlayService;

    @Autowired
    ProcessPlayService processPlayService;

    @Autowired
    EduProcessService eduProcessService;

    @Autowired
    EventService eventService;

    @Autowired
    PlayTeamService playTeamService;

    //TODO 실감훈련 통계 개발 필요

    /**
     * 통계 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "list")
    public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/list";

//        PlayVo playVo = playService.select(req);
        PlayVo playVo = playService.statSelect(req);
        req.put("detailIdx", playVo.getDetailIdx());
        DetailVo detailVo = detailService.select(req);
        req.put("eduIdx", detailVo.getEduIdx());
        EduVo eduVo = eduService.select(req);

        EgovMap playTeamMap = new EgovMap();
        playTeamMap.put("playTeamIdx", playVo.getPlayTeamIdx());

        List<ModulePlayVo> modules = modulePlayService.modules(playTeamMap);
        if(modules.size() < 1)	{
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "해당 훈련에서 수행한 모듈이 없습니다.");
		}
    	
        Map<ModulePlayVo, List<ModulePlayVo>> modulePlayMap = new LinkedHashMap<>(); //모듈코드, 회차


        for (ModulePlayVo module : modules) { // 수행 모듈 수 만큼
//            Map<Integer, List<ModulePlayVo>> attemptsPlayMap = new LinkedHashMap<>(); // 회차, 모듈
            //회차 수만큼

//            List<ModulePlayVo> moduleAttempts = modulePlayService.moduleAttempts(playTeamMap); //해당모듈 수행이 몇회차까지 있는지 확인
//
//            for (ModulePlayVo moduleAttempt : moduleAttempts) { //회차별 반복

                EgovMap moduleMap = new EgovMap();
//                moduleMap.put("attempt", moduleAttempt.getAttempt());
                moduleMap.put("moduleCd", module.getModuleCd());
                moduleMap.put("playTeamIdx", playVo.getPlayTeamIdx());

                List<ModulePlayVo> modulePlayList = modulePlayService.modulePlayList(moduleMap);

//                for (ModulePlayVo modulePlayVo : modulePlayList) {
//                    String successYn = StrUtil.toStr(modulePlayVo.getSuccessYn());
//                    if (successYn.equals("N")) {

//                        EgovMap eventMap = new EgovMap();
//		            	eventMap.put("team", playVo.getTeam());
//		            	eventMap.put("detailIdx", playVo.getDetailIdx());

//                        eventMap.put("playTeamIdx", playVo.getPlayTeamIdx());
//                        eventMap.put("attempt", moduleAttempt.getAttempt());
//                        eventMap.put("moduleCd", module.getModuleCd());
//                        eventMap.put("accidentYn", "Y");
//		            	eventMap.put("modulePlayIdx", modulePlayVo.getModulePlayIdx());

//                        EventVo accidentEventVo = eventService.select(eventMap);
//			    		EventVo accidentEventVo = eventService.accidentEventInModule(eventMap);
//                        modulePlayVo.setAccidentEventVo(accidentEventVo);
//		            	List<ProcessPlayVo> processPlayList = processPlayService.teamProcessPlays(processMap);

//		            	for (ProcessPlayVo processPlayVo : processPlayList) {
//		            		EgovMap eventMap = new EgovMap();
//		                	
//		                	List<EventVo> eventList = eventService.selectAll(eventMap);
//		                	
//		                	processPlayVo.setEventList(eventList);
//		                }
//	            	modulePlayVo.setProcessPlayList(processPlayList);
//                    }

//                }
//                attemptsPlayMap.put(moduleAttempt.getAttempt(), modulePlayList);

//            }
            modulePlayMap.put(module, modulePlayList);
        }

        String firstModuleCd = modulePlayMap.isEmpty() ? null : modulePlayMap.keySet().iterator().next().getModuleCd();
//    	Map<Integer, List<ModulePlayVo>> firstModuleMap = modulePlayMap.get(firstModuleCd);
//    	Integer firstAttempt = firstModuleMap.keySet().iterator().next();
//    	List<ModulePlayVo> firstModulePlayVoList = firstModuleMap.get(firstAttempt);
//    	Integer firstModulePlayIdx = firstModulePlayVoList.get(0).getModulePlayIdx();

        model.addAttribute("req", req);
        model.addAttribute("detailVo", detailVo);
        model.addAttribute("playVo", playVo);
        model.addAttribute("eduVo", eduVo);
        model.addAttribute("modulePlayMap", modulePlayMap);
        model.addAttribute("firstModuleCd", firstModuleCd);
//		model.addAttribute("firstModulePlayIdx", firstModulePlayIdx);
        model.addAttribute("moduleSize", modules.size());

        return viewName;
    }

    @SiteLayout
    @RequestMapping(value = "playInfo")
    public String playInfo(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/playInfo";

        PlayVo playVo = playService.select(req);
        int attempt = StrUtil.toInt(req.get("attempt"));
        String moduleCd = StrUtil.toStr(req.get("moduleCd"));

        ModulePlayVo modulePlayVo = modulePlayService.select(req);
        int modulePlayIdx = modulePlayVo.getModulePlayIdx();

        EgovMap moduleMap = new EgovMap();
        moduleMap.put("attempt", attempt);
        moduleMap.put("moduleCd", moduleCd);
        moduleMap.put("playTeamIdx", playVo.getPlayTeamIdx());

//		moduleMap.put("team", playVo.getTeam());
//		moduleMap.put("detailIdx", playVo.getDetailIdx());
//		int stepCount = processPlayService.playStepCount(moduleMap); // 수행한 절차의 수

//		Map<Integer, Integer> stepCountMap = new LinkedHashMap<>(); //모듈코드, 회차
//		for (int i = 0; i < stepCount; i++) {
//			EgovMap taskMap = new EgovMap();
//		    taskMap.put("moduleCd", moduleCd);
//			taskMap.put("step", i);
//		    
//		    int processCount = eduProcessService.count(taskMap);
//            
//            stepCountMap.put(i, processCount);
//        }

//    	List<ProcessPlayVo> processPlayList = processPlayService.teamProcessPlays(moduleMap);

//    	List<ProcessPlayVo> myProcessPlayList = processPlayService.selectAll(req);

        List<ProcessPlayVo> processPlayAndEventList = processPlayService.processEventList(moduleMap);

//        for (ProcessPlayVo processPlayAndEventVo : processPlayAndEventList) {

//            EgovMap processEventMap = new EgovMap();
//            processEventMap.put("attempt", attempt);
//            processEventMap.put("moduleCd", moduleCd);
//            processEventMap.put("playTeamIdx", playVo.getPlayTeamIdx());
//            processEventMap.put("step", processPlayAndEventVo.getStep());
//            processPlayAndEventVo.setProcessCount(processPlayService.playStepCount(processEventMap));

//            Date processStartTime = processPlayAndEventVo.getProcessStartTime();
//            if (processStartTime != null) {
//                int processElapsedStartTime = TimeCalculator.calculateEventElapsedTime(processStartTime, modulePlayVo.getModuleStartTime());
//                processPlayAndEventVo.setProcessElapsedStartTime(processElapsedStartTime);
//                int minutes = processElapsedStartTime / 60;
//                processElapsedStartTime %= 60;
//                String formattedMinutes = String.format("%02d", minutes);
//                String formattedSeconds = String.format("%02d", processElapsedStartTime);
//                processPlayAndEventVo.setProcessElapsedMinute(formattedMinutes);
//                processPlayAndEventVo.setProcessElapsedSecond(formattedSeconds);
//            }
//            Date eventStartTime = processPlayAndEventVo.getEventStartTime();
//            if (eventStartTime != null) {
//                int eventElapsedStartTime = TimeCalculator.calculateEventElapsedTime(eventStartTime, modulePlayVo.getModuleStartTime());
//                processPlayAndEventVo.setEventElapsedStartTime(eventElapsedStartTime);
//                int minutes = eventElapsedStartTime / 60;
//                eventElapsedStartTime %= 60;
//                String formattedMinutes = String.format("%02d", minutes);
//                String formattedSeconds = String.format("%02d", eventElapsedStartTime);
//                processPlayAndEventVo.setEventElapsedMinute(formattedMinutes);
//                processPlayAndEventVo.setEventElapsedSecond(formattedSeconds);
//
//            }

//    		EgovMap eventMap = new EgovMap();
//        	eventMap.put("processPlayIdx", processPlayVo.getProcessPlayIdx());
//        	List<EventVo> eventList = eventService.selectAll(eventMap);

//        	for (EventVo eventVo : eventList) {
//        		Date eventStartTime = eventVo.getEventStartTime();
//				int eventElapsedStartTime = TimeCalculator.calculateEventElapsedTime(eventStartTime, modulePlayVo.getModuleStartTime());
//	    		eventVo.setEventElapsedStartTime(eventElapsedStartTime);
//        	}
//        	processPlayVo.setEventList(eventList);

//        }

//    	for (ProcessPlayVo myProcessPlayVo : myProcessPlayList) {
//    		Date processStartTime = myProcessPlayVo.getProcessStartTime();
//    		Date moduleStartTime = modulePlayVo.getModuleStartTime();
//    		// 두 날짜 간의 차이 계산 (단위: 밀리초)
//    		long timeDifferenceMillis = processStartTime.getTime() - moduleStartTime.getTime();
//
//    		// 차이를 초 단위로 변환
//    		int elapsedTime = (int) (timeDifferenceMillis / 1000);
//    		myProcessPlayVo.setProcessElapsedStartTime(elapsedTime);
//    		
//    		EgovMap eventMap2 = new EgovMap();
//        	eventMap2.put("processPlayIdx", myProcessPlayVo.getProcessPlayIdx());
//        	List<EventVo> myEventList = eventService.selectAll(eventMap2);
//        	for (EventVo eventVo : myEventList) {
//        		Date eventTime = eventVo.getEventTime();
//        		// 두 날짜 간의 차이 계산 (단위: 밀리초)
//        		long eventTimeDifferenceMillis = eventTime.getTime() - moduleStartTime.getTime(); 
//
//        		// 차이를 초 단위로 변환
//        		int eventElapsedTime = (int) (eventTimeDifferenceMillis / 1000);
//        		eventVo.setEventElapsedTime(eventElapsedTime);
//        	}
//        	myProcessPlayVo.setEventList(myEventList);
//        }

        model.addAttribute("playVo", playVo);
        model.addAttribute("req", req);
        model.addAttribute("attempt", attempt);
        model.addAttribute("moduleCd", moduleCd);
        model.addAttribute("modulePlayIdx", modulePlayIdx);
        model.addAttribute("processPlayAndEventList", processPlayAndEventList);
        model.addAttribute("modulePlayVo", modulePlayVo);
//		model.addAttribute("stepCount", stepCount);
//		model.addAttribute("stepCountMap", stepCountMap);
//		model.addAttribute("myProcessPlayList", myProcessPlayList);

        return viewName;
    }

    /**
     * 수행 그래프
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "playGraph")
    @ResponseBody
    public JsonResponse playGraph(@RequestEgovMap EgovMap req, Model model) throws Exception {

        PlayVo playVo = playService.select(req);
        int attempt = StrUtil.toInt(req.get("attempt"));
        String moduleCd = StrUtil.toStr(req.get("moduleCd"));

        ModulePlayVo modulePlayVo = modulePlayService.select(req);

//        Date moduleStartTime = modulePlayVo.getModuleStartTime();
//        Date moduleEndTime = modulePlayVo.getModuleEndTime();

//        long differnceMillis = moduleEndTime.getTime() - moduleStartTime.getTime();
//        int moduleElapsedTime = (int) (differnceMillis / 1000);
//        modulePlayVo.setModuleElapsedTime(moduleElapsedTime);


        EgovMap processMap = new EgovMap();
//		processMap.put("attempt", attempt);
//		processMap.put("moduleCd", moduleCd);
//		processMap.put("team", playVo.getTeam());
//		processMap.put("detailIdx", playVo.getDetailIdx());
//		processMap.put("playIdx", playVo.getPlayIdx());
        processMap.put("modulePlayIdx", modulePlayVo.getModulePlayIdx());
        List<ProcessPlayVo> processPlayList = processPlayService.processPlayList(processMap);

        EgovMap eventMap = new EgovMap();
        eventMap.put("modulePlayIdx", modulePlayVo.getModulePlayIdx());
        eventMap.put("riskFactor", "1"); //감전
        List<EventVo> eletricShockEventList = eventService.eventList(eventMap);
        
        eventMap.remove("riskFactor");
        eventMap.put("riskFactor", "2"); //추락
        List<EventVo> downFallEventList = eventService.eventList(eventMap);
        
        eventMap.remove("riskFactor");
        eventMap.put("riskFactor", "3"); //부주의
        List<EventVo> carelessEventList = eventService.eventList(eventMap);

        eventMap.remove("riskFactor");
        eventMap.put("riskFactor", "4"); //작업부하
        List<EventVo> workloadEventList = eventService.eventList(eventMap);

//        for (ProcessPlayVo processPlayVo : processPlayList) {
//            Date processStartTime = processPlayVo.getProcessStartTime();
//            Date processEndTime = processPlayVo.getProcessEndTime();
//            // 두 날짜 간의 차이 계산 (단위: 밀리초)
//            long startDifferenceMillis = processStartTime.getTime() - moduleStartTime.getTime();
//            long endDifferenceMillis = processEndTime.getTime() - moduleStartTime.getTime();
//
//            // 차이를 초 단위로 변환
//            int processElapsedStartTime = (int) (startDifferenceMillis / 1000);
//            int processElapsedEndTime = (int) (endDifferenceMillis / 1000);
//            processPlayVo.setProcessElapsedStartTime(processElapsedStartTime);
//            processPlayVo.setProcessElapsedEndTime(processElapsedEndTime);
//
////    		EgovMap accidentMap = new EgovMap();
////    		accidentMap.put("accidentYn", "y"); //사고발생
////    		accidentMap.put("processPlayIdx", processPlayVo.getProcessPlayIdx());
////			List<EventVo> accidentEvents = eventService.selectAll(accidentMap);
////			
////			for(EventVo eventVo : accidentEvents) {
////				Date eventStartTime = eventVo.getEventStartTime();
////				int eventElapsedStartTime = TimeCalculator.calculateEventElapsedTime(eventStartTime, modulePlayVo.getModuleStartTime());
////				Date eventEndTime = eventVo.getEventEndTime();
////				int eventElapsedEndTime = TimeCalculator.calculateEventElapsedTime(eventEndTime, modulePlayVo.getModuleStartTime());
////	    		eventVo.setEventElapsedStartTime(eventElapsedStartTime);
////	    		eventVo.setEventElapsedEndTime(eventElapsedEndTime);
////	    		accidentEventList.add(eventVo);
////			}
//        }

        model.addAttribute("eletricShockEventList", eletricShockEventList);
        model.addAttribute("downFallEventList", downFallEventList);
        model.addAttribute("carelessEventList", carelessEventList);
        model.addAttribute("workloadEventList", workloadEventList);
//		model.addAttribute("accidentEventList", accidentEventList);
        model.addAttribute("processPlayList", processPlayList);
        model.addAttribute("modulePlayVo", modulePlayVo);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    //시간계산
    public class TimeCalculator {
        public static int calculateEventElapsedTime(Date dateTime, Date moduleStartTime) {
            // 두 날짜 간의 차이 계산 (단위: 밀리초)
            long eventTimeDifferenceMillis = dateTime.getTime() - moduleStartTime.getTime();

            // 차이를 초 단위로 변환
            return (int) (eventTimeDifferenceMillis / 1000);
        }
    }


    /**
     * 모듈 목록
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
	/*
	@RequestMapping(value = "moduleList")
	public String moduleList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "lms/edu/stat/moduleList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	*/

    /**
     * 수행 모듈 목록
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "moduleList")
    @ResponseBody
    public JsonResponse moduleList(@RequestEgovMap EgovMap req, Model model) {

        int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);

        PlayVo playVo = playService.select(req); //detailIdx 와 memberIdx 를 이용하여 수행정보를 가져온다. //TODO 같은 사람이 같은 훈련에대해 여러번 실행했을경우 수행이 단일이 아닌 여러개 조회될 수 있는데 이것에 대한 처리 고민 필요
        req.put("playIdx", playVo.getPlayIdx());
        List<ModulePlayVo> list = modulePlayService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 발생 이벤트 목록
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "viewEventList")
    @ResponseBody
    public JsonResponse viewEventList(@RequestEgovMap EgovMap req, Model model) {

        // modulePlayIdx 를 받아와서 해당 모듈 수행동안 발생한 이벤트 목록을 받아오기
        List<EventVo> list = eventService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }


    /**
     * 학습자 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "memberList")
    public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/memberList";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 통계 폼
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "form")
    public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/form";

        EgovMap vo = null;

        if (!StrUtil.isEmpty(req.get("statIdx"))) {
            vo = statService.select(req);
        }

        model.addAttribute("req", req);
        model.addAttribute("vo", vo);

        return viewName;
    }

    /**
     * 목록 데이터
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "listJson")
    @ResponseBody
    public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        List<EdustatVo> list = statService.selectAll(req);
        model.addAttribute("list", list);

        return new JsonResponse.Builder().model(model).build();
    }

}