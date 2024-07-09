package kepco.lms.edu.result;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.event.EventService;
import kepco.lms.edu.event.EventVo;
import kepco.lms.edu.module.play.ModulePlayService;
import kepco.lms.edu.module.play.ModulePlayVo;
import kepco.lms.edu.play.PlayService;
import kepco.lms.edu.play.PlayVo;
import kepco.lms.edu.play.team.PlayTeamService;
import kepco.lms.edu.play.team.PlayTeamVo;
import kepco.lms.edu.process.play.ProcessPlayService;
import kepco.lms.edu.process.play.ProcessPlayVo;
import kepco.lms.edu.regist.RegistService;
import kepco.lms.edu.stat.EdustatController.TimeCalculator;
import kepco.util.StrUtil;
/**
 * 훈련 결과
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/result")
public class AdminResultController {
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	DetailService detailService;
	
	@Autowired
	PlayService playService;

	@Autowired
	PlayTeamService playTeamService;

	@Autowired
	ModulePlayService modulePlayService;
	
	@Autowired
	EventService eventService;

	@Autowired
	RegistService registService;

	@Autowired
	ProcessPlayService processPlayService;
	
	/**
	 * 훈련 결과 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/result/list";
		
		return viewName;
	}
	
	/**
	 * 훈련 팀 결과 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "team/list")
	public String teamList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/result/team/list";
		
		return viewName;
	}
	
	/**
	 * 훈련 팀 결과 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "team/listJson")
	@ResponseBody
	public JsonResponse teamListJson(@RequestEgovMap EgovMap req, Model model) {
		
		List<PlayTeamVo> list = playTeamService.selectAll(req);
//		if(list.size() > 0) {
			
//			for (PlayTeamVo playTeamVo : list) {
//				 playTeamvo
//	        	}
//	        }
			
//		}
	
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
     * 엑셀 다운로드
     *
     * @param req
     * @param model
     */
    @RequestMapping(value = "team/download")
    @ResponseBody
    public void download(@RequestEgovMap EgovMap req, Model model, HttpServletResponse response) {

    	List<PlayTeamVo> list = playTeamService.selectAll(req);

        
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("팀별 훈련결과");
        int rowNum = 0;

        rowNum = createRow(sheet, rowNum, "훈련팀IDX", "훈련명", "교수자", "훈련일시", "훈련시간(분)", "수행시도(횟수)", "수행모듈(갯수)", "팀", "주작업자", "보조작업자", "지상작업자", "작업책임자");
        for (PlayTeamVo playTeamVo : list) {
        	rowNum = createRow(sheet, rowNum, StrUtil.toStr(playTeamVo.getPlayTeamIdx()), playTeamVo.getTrainTitle(), playTeamVo.getTeacherNm(), 
        			StrUtil.toStr(playTeamVo.getStartTime()), StrUtil.toStr(playTeamVo.getPlayMinute()), StrUtil.toStr(playTeamVo.getAttemptMax()),
        			StrUtil.toStr(playTeamVo.getModuleCdCnt()), StrUtil.toStr(playTeamVo.getTeam()), StrUtil.toStr(playTeamVo.getMainWorker()),
        			StrUtil.toStr(playTeamVo.getSubWorker()),
        			StrUtil.toStr(playTeamVo.getGroundWorker()),
        			StrUtil.toStr(playTeamVo.getSuperWorker()));
        }
        rowNum++;
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=train_result.xlsx");

        try {
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int createRow(Sheet sheet, int rowNum, String... values) {
        Row row = sheet.createRow(rowNum++);
        int cellNum = 0;
        for (String value : values) {
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(value);
        }
        return rowNum;
    }
	
	/**
	 * 훈련 결과 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/result/view";
		
		DetailVo detailVo = detailService.select(req);
		
//		List<PlayVo> playList = playService.selectList(req);
		
		model.addAttribute("detailVo", detailVo);
		model.addAttribute("req", req);
//		model.addAttribute("playList", playList);
		
		return viewName;
	}
	/**
	 * 훈련 결과 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		int listCnt = 0;
		int firstDetailIdx = 0;
		List<DetailVo> list = detailService.selectAll(req);
		listCnt = list.size(); 
		if (!list.isEmpty()) {
		    firstDetailIdx = list.get(0).getDetailIdx();
		    // 이제 firstDetailIdx 변수에 첫 번째 요소의 detail_idx 값이 저장됨
		}
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 학습자 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "studentList")
	@ResponseBody
	public JsonResponse studentList(@RequestEgovMap EgovMap req, Model model) {

		
		List<PlayVo> list = playService.selectAll(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 수행 모듈 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "moduleList")
	@ResponseBody
	public JsonResponse moduleList(@RequestEgovMap EgovMap req, Model model) {

		PlayVo playVo = playService.select(req);
		String firstModuleCd = null;
				
		EgovMap playTeamMap = new EgovMap();
    	playTeamMap.put("playTeamIdx", playVo.getPlayTeamIdx());
    	
    	List<ModulePlayVo> list = modulePlayService.modules(playTeamMap);
    	
//    	if (!list.isEmpty()) {
//    	    ModulePlayVo firstModule = list.get(0); // 첫 번째 요소 가져오기
//    	    firstModuleCd = firstModule.getModuleCd(); // ModuleCd 가져오기
//    	}
    	
		model.addAttribute("list", list);
//		model.addAttribute("firstModuleCd", firstModuleCd);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 콘텐츠 모듈 수행 회차 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modulePlayList")
	@ResponseBody
	public JsonResponse modulePlayList(@RequestEgovMap EgovMap req, Model model) {
		
		PlayVo playVo = playService.select(req);
		String moduleCd = StrUtil.toStr(req.get("moduleCd"));
		
		
//    	Map<Integer, List<ModulePlayVo>> list = new LinkedHashMap<>(); // 회차, 모듈 
    		
		//회차 수만큼
//		List<ModulePlayVo> moduleAttempts = modulePlayService.moduleAttempts(playTeamMap); //해당모듈 수행이 몇회차까지 있는지 확인

		EgovMap moduleMap = new EgovMap();
		moduleMap.put("playTeamIdx", playVo.getPlayTeamIdx());
		moduleMap.put("moduleCd", moduleCd);
		moduleMap.put("playIdx", playVo.getPlayIdx());
		
		List<ModulePlayVo> list = modulePlayService.modulePlayHistory(moduleMap);
        
//        for (ModulePlayVo modulePlayVo : list) {
//        	String successYn = StrUtil.toStr(modulePlayVo.getSuccessYn());
//        	if(successYn.equals("N")) {
//        		
//            	EgovMap eventMap = new EgovMap();
//            	eventMap.put("playTeamIdx", playVo.getPlayTeamIdx());
//            	eventMap.put("attempt", modulePlayVo.getAttempt());
//            	eventMap.put("moduleCd", moduleCd);
//            	eventMap.put("accidentYn", "Y");
//            	
//            	EventVo accidentEventVo = eventService.select(eventMap);
//	    		modulePlayVo.setAccidentEventVo(accidentEventVo);
//        	}
//        }
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 발생 이벤트 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "viewEventList")
	@ResponseBody
	public JsonResponse viewEventList(@RequestEgovMap EgovMap req, Model model) {

		PlayVo playVo = playService.select(req); 
		int attempt = StrUtil.toInt(req.get("attempt"));
		String moduleCd = StrUtil.toStr(req.get("moduleCd"));
		
		ModulePlayVo modulePlayVo = modulePlayService.select(req);
		
		EgovMap moduleMap = new EgovMap();
		moduleMap.put("attempt", attempt);
		moduleMap.put("moduleCd", moduleCd);
		moduleMap.put("playTeamIdx", playVo.getPlayTeamIdx());
		moduleMap.put("playIdx", playVo.getPlayIdx());
		
		List<ProcessPlayVo> processPlayAndEventList = processPlayService.processEventList(moduleMap);
            	
    	model.addAttribute("playVo", playVo);
		model.addAttribute("req", req);
		model.addAttribute("attempt", attempt);
		model.addAttribute("moduleCd", moduleCd);
		model.addAttribute("list", processPlayAndEventList);
		model.addAttribute("modulePlayVo", modulePlayVo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 선택
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	public JsonResponse select(@RequestEgovMap EgovMap req, Model model) {
		
		model.addAttribute("req", req);
		
		List<ResultVo> list = resultService.select(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 다운로드
	 * @param req
	 * @param model
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	public void download(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		//다운로드 형식(excel?), 데이터 결정 시 api 작성할 예정  
		
	}
	
	/**
	 * 훈련 팀 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "team/delete")
	@ResponseBody
	public JsonResponse teamDelete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("playTeamIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		result = playTeamService.delete(req);
		model.addAttribute("result", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}