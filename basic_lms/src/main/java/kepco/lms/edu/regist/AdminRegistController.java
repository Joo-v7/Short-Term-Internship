package kepco.lms.edu.regist;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.excel.ExcelSheetHandler;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.team.TeamService;
import kepco.util.StrUtil;
/**
 * 실감훈련 신청자 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/regist")
public class AdminRegistController {
	
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
	
	private final static String FILE_PATH = "edu/regist/"; 
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	@Autowired
	LocalFileService localFileService;
	
	/**
	 * 신청자 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/regist/list";
		
		DetailVo detailVo = detailService.select(req);
		
		model.addAttribute("req", req);
		model.addAttribute("detailVo", detailVo);

		return viewName;
	}
	/**
	 * 팀 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "teamList")
	public String teamList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/regist/teamList";
		DetailVo detailVo = detailService.select(req);
		
		model.addAttribute("req", req);
		model.addAttribute("vo", detailVo);
		
		return viewName;
	}
	/**
	 * 신청자 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/regist/form";
		
		RegistVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("registIdx"))) {
			vo = registService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	/**
	 * 신청자 명단
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "memberList")
	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/regist/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 신청자 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		if("memberList".equals(req.get("type"))) {
			req.put("scDetailIdx",req.get("detailIdx"));
		}
		
		List<RegistVo> list = registService.selectAll(req);
		
		model.addAttribute("list", list);
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 신청자 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RegistVo registVo = mapper.convertValue(req, RegistVo.class);
		EgovMap regMap = new EgovMap();
//		regMap.put("scMemberIdx",registVo.getMemberIdx());
//		regMap.put("scDetailIdx",registVo.getDetailIdx());
		regMap.put("registIdx",registVo.getRegistIdx());
		RegistVo originRegistVo = registService.select(regMap);
		if(originRegistVo != null) { //기존 승인정보가 있으면
			registVo.setRegistIdx(originRegistVo.getRegistIdx());
			registVo.setMemberIdx(originRegistVo.getMemberIdx());
		}	
		
		DetailVo detailVo = detailService.select(req);
		String detailState = detailVo.getDetailState();
		String check = null;
		req.put("scMemberIdx",registVo.getMemberIdx());
		req.put("scDetailIdx",registVo.getDetailIdx());
		int duplicateCnt = registService.duplicate(req);
		int result = 0 ;
		
		if(!(registVo.getRegistIdx() != 0)) {
			//TeamVo teamVo  = teamService.select(req);
			//봇 추가 조건
			if(!StrUtil.isEmpty(req.get("bot"))) {
				//팀 정원 조건
				//if(teamVo.getTeamCnt() < teamVo.getTeamMax()) {
					registVo.setTeam(StrUtil.toStr(req.get("team")));
					registVo.setRegistState("2");
					
					registVo.setInsertIdx(StrUtil.getSessionIdx());
					registVo.setInsertIp(StrUtil.getClientIP());
					result = registService.insert(registVo);
				//} else {
				//	check = "o"; //인원 over 메시지
				//	model.addAttribute("check", check);
				//}
			}else {
				
				
				//실감 훈련 디테일 상태 승인일경우 조건
				if("2".equals(detailState)) {
					
					String eduBgnDateTime = detailVo.getEduAcceptBgnDate() + " " + detailVo.getEduAcceptBgnTime() + ":00"; 
					String eduEndDateTime = detailVo.getEduAcceptEndDate() + " " + detailVo.getEduAcceptEndTime() + ":00"; 
					
					int compare1 = eduBgnDateTime.compareTo(StrUtil.getCurrentTime());
					int compare2 = eduEndDateTime.compareTo(StrUtil.getCurrentTime());
					
					//신청 시간 조건
					if(compare1 <= 0 && compare2 > 0 ) {
						
						//중복신청 조건
						if(duplicateCnt > 0) {
							//관리자 페이지라서 제거
						}else {
							//팀 정원 조건
							//if(teamVo.getTeamCnt() < teamVo.getTeamMax()) {
								registVo.setMemberIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
								if("y".equals(detailVo.getEduAcceptAuto())) {
									registVo.setRegistState("2");
								}else {
									registVo.setRegistState("1");
								}
								registVo.setInsertIdx(StrUtil.getSessionIdx());
								registVo.setInsertIp(StrUtil.getClientIP());
								result = registService.insert(registVo);
							//} else {
							//	check = "o"; //인원 over 메시지
							//	model.addAttribute("check", check);
							//}
						}
					} else {
						check = "n"; //신청기간 아님 메세지
						model.addAttribute("check", check);
					}
					
				}else {
					//실감 훈련 디테일 상태 승인이 아닐경우 추가?
				}
				
			}
			
		}else {
			registVo.setUpdateIdx(StrUtil.getSessionIdx());
			registVo.setUpdateIp(StrUtil.getClientIP());
			result = registService.update(registVo);
			
			if(registVo.getRegistState().equals("2")) { //수강신청 승인으로 변경 시 email 알림 전송
				EmailVo emailVo = new EmailVo();
    			EgovMap egovMap = new EgovMap();
    			TemplateVo templateVo = new TemplateVo();
	        	egovMap.put("tempIdx", "6");
				templateVo = templateService.select(egovMap);
				
				egovMap.put("memberIdx", registVo.getMemberIdx()); //사용자 정보 가져오기
	        	MemberVo memberVo = memberService.select(egovMap);
	        	
				emailVo.setTo(memberVo.getMemberEmail());
//				emailVo.setFrom("초실감훈련센터");//email 바꾸기 필요
				emailVo.setSubject(templateVo.getTempTitle());
				emailVo.setText(templateVo.getTempContent());
				emailVo.setUseHtml(true);
				emailVo.setUseTemplate(true);
				
				Map<String, Object> map = Map.of("memberName",memberVo.getMemberNm(), "now", new Date(), "trainTitle", detailVo.getTrainTitle()); 
				emailVo.setTemplateMap(map);
				emailService.send(emailVo);
			}
		}
		
		model.addAttribute("detailState", detailState);
		model.addAttribute("duplicateCnt", duplicateCnt);
		model.addAttribute("resultCnt", result);
		model.addAttribute(registVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 신청자 팀 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "teamSave")
	@ResponseBody
	public JsonResponse teamSave(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		RegistVo registVo = mapper.convertValue(req, RegistVo.class);
		if(!StrUtil.isEmpty(req.get("registIdx"))) {
			RegistVo originRegistVo = registService.select(req);
			req.remove("detailIdx");
			req.remove("memberIdx");
			req.put("detailIdx", originRegistVo.getDetailIdx());
			req.put("memberIdx", originRegistVo.getMemberIdx());
		}
		DetailVo detailVo = detailService.select(req);
		String detailState = detailVo.getDetailState();
		String check = null;
		req.put("scMemberIdx",registVo.getMemberIdx());
		req.put("scDetailIdx",registVo.getDetailIdx());
		int duplicateCnt = registService.duplicate(req);
		int registerCnt = detailVo.getEduRegCnt() + detailVo.getEduBotCnt();
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("registIdx"))) {
//			TeamVo teamVo  = teamService.select(req);
			//봇 추가 조건
			if(!StrUtil.isEmpty(req.get("bot"))) {
				//팀 정원 조건
				if(registerCnt < detailVo.getEduLimitCnt()) {
					registVo.setTeam(StrUtil.toStr(req.get("team")));
					registVo.setRegistState("2");
					
					registVo.setInsertIdx(StrUtil.getSessionIdx());
					registVo.setInsertIp(StrUtil.getClientIP());
					result = registService.insert(registVo);
				} else {
					check = "o"; //인원 over 메시지
					model.addAttribute("check", check);
				}
			}else {
				
				
				//실감 훈련 디테일 상태 승인일경우 조건
				if("2".equals(detailState)) {
					
					String eduBgnDateTime = detailVo.getEduAcceptBgnDate() + " " + detailVo.getEduAcceptBgnTime() + ":00"; 
					String eduEndDateTime = detailVo.getEduAcceptEndDate() + " " + detailVo.getEduAcceptEndTime() + ":00"; 
					
					int compare1 = eduBgnDateTime.compareTo(StrUtil.getCurrentTime());
					int compare2 = eduEndDateTime.compareTo(StrUtil.getCurrentTime());
					
					//신청 시간 조건
					if(compare1 <= 0 && compare2 > 0 ) {
						
						//중복신청 조건
						if(duplicateCnt > 0) {
							//관리자 페이지라서 제거
						}else {
							//팀 정원 조건
							//if(teamVo.getTeamCnt() < teamVo.getTeamMax()) {
								registVo.setMemberIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
								if("y".equals(detailVo.getEduAcceptAuto())) {
									registVo.setRegistState("2");
								}else {
									registVo.setRegistState("1");
								}
								registVo.setInsertIdx(StrUtil.getSessionIdx());
								registVo.setInsertIp(StrUtil.getClientIP());
								result = registService.insert(registVo);
							//} else {
							//	check = "o"; //인원 over 메시지
							//	model.addAttribute("check", check);
							//}
						}
					} else {
						check = "n"; //신청기간 아님 메세지
						model.addAttribute("check", check);
					}
					
				}else {
					//실감 훈련 디테일 상태 승인이 아닐경우 추가?
				}
				
			}
			
		}else {
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
	 * 학습 상태 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "eduState/save")
	@ResponseBody
	public JsonResponse eduStateSave(@RequestEgovMap EgovMap req, Model model) {
		
		if (StrUtil.isEmpty(req.get("registIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		int result = registService.eduStateSave(req);
		
		model.addAttribute("result", result);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 신청자 팀 자동 배정
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "autoTemplate")
	@ResponseBody
	public JsonResponse autoTemplate(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		DetailVo detailVo = detailService.select(req);
		int eduLimitCnt = detailVo.getEduLimitCnt(); // 수강 정원
		
		int eduTeamCnt = detailVo.getEduTeamCnt();
		int roleCnt = 4; //역할 수
		int team = 1; //배정되는 팀
		
		// 역할별 최대 역할수 설정
		int[][] eduLimit = new int[eduTeamCnt][roleCnt];

		for (int i = 0; i < eduTeamCnt; i++) {
		    eduLimit[i][0] = detailVo.getEduLimitCnt1(); //주작업자
		    eduLimit[i][1] = detailVo.getEduLimitCnt2(); //보조작업자
		    eduLimit[i][2] = detailVo.getEduLimitCnt3(); //지상작업자
		    eduLimit[i][3] = detailVo.getEduLimitCnt4(); //작업책임자
		}
		
		// 현재 배정된 역할 수를 세기 위한 변수
		int[][] tempLimitCnt = new int[eduTeamCnt][roleCnt];

		for (int i = 0; i < eduTeamCnt; i++) {
			tempLimitCnt[i][0] = 0;
			tempLimitCnt[i][1] = 0;
			tempLimitCnt[i][2] = 0;
			tempLimitCnt[i][3] = 0;
		}
		
		req.put("scDetailIdx",req.get("detailIdx"));
		List<RegistVo> listAddBefore = registService.selectAll(req); 
		
		int listSize = listAddBefore.size(); //수강자 리스트 수
		int addBotCnt = eduLimitCnt - listSize; //생성해야하는 아바타 수 계산
		
		for (int i = 0; i < addBotCnt; i++) { // 필요한 아바타 수 만큼 생성
			RegistVo rgVo = new RegistVo();
			rgVo.setRegistState("2");
			rgVo.setMemberIdx(0);
			rgVo.setDetailIdx(StrUtil.toInt(req.get("detailIdx")));
			rgVo.setInsertIdx(StrUtil.getSessionIdx());
			rgVo.setInsertIp(StrUtil.getClientIP());
			registService.insert(rgVo);
			
		}
		
		List<RegistVo> listAddAfter = registService.selectAll(req); // 아바타 생성 후 수강자 리스트 수
		for (int i = 0; i < listAddAfter.size(); i++) { // 수강 수 만큼 반복문
		    RegistVo registVo = listAddAfter.get(i);
		    if(team > eduTeamCnt) { // 현재 팀이 팀수치를 넘어갔을 경우 1부터 다시 오르도록 함
		    	team = team % eduTeamCnt;
		    }
		    
	    	if(team <= eduTeamCnt) { // 순차적 팀배정
	    		if(tempLimitCnt[team-1][0] < eduLimit[team-1][0]) { // team 이 1부터 시작하므로 인덱스값으로는 team-1 
		    		registVo.setRegistRole("1"); // 역할배정 
		    		tempLimitCnt[team-1][0]++;
		    	}else if(tempLimitCnt[team-1][1] < eduLimit[team-1][1]) {
		    		registVo.setRegistRole("2");
		    		tempLimitCnt[team-1][1]++;
		    	}else if(tempLimitCnt[team-1][2] < eduLimit[team-1][2]) {
		    		registVo.setRegistRole("3");
		    		tempLimitCnt[team-1][2]++;
		    	}else if(tempLimitCnt[team-1][3] < eduLimit[team-1][3]) {
		    		registVo.setRegistRole("4");
		    		tempLimitCnt[team-1][3]++;
		    	}
	    		registVo.setTeam(StrUtil.toStr(team));
	    		team ++; //다음 사람 배정되는 팀 변경
	    	}
	    	registVo.setUpdateIdx(StrUtil.getSessionIdx());
			registVo.setUpdateIp(StrUtil.getClientIP());
			registService.update(registVo);
		}
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 신청자 파일 등록
	 * @param req
	 * @param model
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "fileSave")
	@ResponseBody
	public JsonResponse fileSave(@RequestEgovMap EgovMap req, Model model, @RequestParam(required=false) MultipartFile file) throws Exception {

		RegistVo registVo = null;
		int duplicateCnt = 0;
		int result = 0 ;
		
		int memberIdx = 0;
		int t = 0; //전체 카운트
		int s = 0; //성공 카운트
		int f = 0; //실패 카운트
		int d = 0; //중복 카운트
		
		try {
			
			List<List<String>> sheet = ExcelSheetHandler.readExcel(file.getInputStream()).getRows();
			
			for(int i=0; i<sheet.size(); i++) {
				registVo = new RegistVo();
				List<String> row = sheet.get(i);

				if(null == row) {
					continue;
				}
				
				String cell = null;
				
				//ID
				cell = row.get(1);
				
				if(!StrUtil.isBlank(cell)) {
					memberIdx = StrUtil.toInt(registService.checkIdx(cell));				
					registVo.setMemberIdx(memberIdx);	
				}

				//팀명
				cell = row.get(2);
				if(!StrUtil.isBlank(cell)) {
					registVo.setTeam(cell);	
				}
				
				//역할
				cell = row.get(3);
				if(!StrUtil.isBlank(cell)) {
					registVo.setRegistRole(cell);	
				}
				
				//훈련 IDX
				registVo.setDetailIdx(StrUtil.toInt(req.get("detailIdx")));
				
				//신청상태
				registVo.setRegistState("2");
				
				//등록
				if(memberIdx != 0) {
					req.put("scMemberIdx",memberIdx);
					req.put("scDetailIdx",StrUtil.toInt(req.get("detailIdx")));
					duplicateCnt = registService.duplicate(req);
					if(duplicateCnt > 0) {
						d = d + 1;
					} else {
						registVo.setInsertIdx(StrUtil.getSessionIdx());
						registVo.setInsertIp(StrUtil.getClientIP());
						result = registService.insert(registVo);
						s = s + 1;
					}
				} else {
					f = f + 1;
				}
				t = t + 1;
			}
		} 
	catch (Exception e) {
			e.printStackTrace();
			f = f + 1;
			t = t + 1;
		}

		model.addAttribute("s",s);
		model.addAttribute("f",f);
		model.addAttribute("d",d);
		model.addAttribute("t",t);
		model.addAttribute("resultCnt", result);
		model.addAttribute(registVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 신청자 삭제
	 * @param req
	 * @param model
	 * @return
	 */
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
	/**
	 * 아바타 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "botDelete")
	@ResponseBody
	public JsonResponse botDelete(@RequestEgovMap EgovMap req, Model model) {

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
	
	/**
     * 엑셀 다운로드
     *
     * @param req
     * @param model
     */
    @RequestMapping(value = "download")
    @ResponseBody
    public void download(@RequestEgovMap EgovMap req, Model model, HttpServletResponse response) {

		req.put("scDetailIdx",req.get("detailIdx"));
		DetailVo detailVo = detailService.select(req);
		List<RegistVo> list = registService.selectAll(req);

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("신청자 목록");
        int rowNum = 0;
        
        rowNum = createRow(sheet, rowNum, "훈련정보");
        rowNum = createRow(sheet, rowNum, "훈련명", "기수", "교수자", "등록일시", "신청시작날짜", "신청종료날짜", "교육시작날짜", "교육종료날짜");
        rowNum = createRow(sheet, rowNum, detailVo.getTrainTitle(), detailVo.getEduNumeral(), detailVo.getTeacherNm(), detailVo.getInsertDate(), 
        		detailVo.getEduAcceptBgnDate(), detailVo.getEduAcceptEndDate(), detailVo.getEduTrainBgnDate(), detailVo.getEduTrainEndDate());
        rowNum++;
        
        rowNum = createRow(sheet, rowNum, "신청자목록");
        rowNum = createRow(sheet, rowNum, "아이디", "성명", "등록일시", "수정일시", "승인상태", "학습상태");
        for (RegistVo registVo : list) {
            rowNum = createRow(sheet, rowNum, registVo.getMemberId(), registVo.getMemberNm(), registVo.getInsertDate(), registVo.getUpdateDate(),
            			("1".equals(registVo.getRegistState()) ? "대기" :
                        "2".equals(registVo.getRegistState()) ? "승인" :
                        "3".equals(registVo.getRegistState()) ? "취소" :
                        "알 수 없음"), 
            			("2".equals(registVo.getEduState()) ? "수료" : "미수료")
            			);
        }
        rowNum++;

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=regist_member_list.xlsx");

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
	 * 샘플 파일 다운로드
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "sampleDownload")
	@ResponseBody
	protected ResponseEntity<Resource> sampleDownload(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {
			
			String fileName = "";
			String fileOrigin = "";
			
			fileName = StrUtil.toStr(req.get("fileName"),"");
			fileOrigin = StrUtil.toStr(req.get("fileOrigin"),"");
			
			if("".equals(fileOrigin)){
			}else{
				return localFileService.download(FILE_PATH + fileName, fileOrigin);
				}
			return null;
	}
}