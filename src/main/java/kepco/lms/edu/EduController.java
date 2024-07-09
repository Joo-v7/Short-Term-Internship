package kepco.lms.edu;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import kepco.cms.board.Criteria;
import kepco.cms.member.MemberService;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.category.CategoryService;
import kepco.lms.edu.category.CategoryVo;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.event.EventService;
import kepco.lms.edu.event.EventVo;
import kepco.lms.edu.pack.PackService;
import kepco.lms.edu.pack.PackVo;
import kepco.lms.edu.play.PlayService;
import kepco.lms.edu.play.PlayVo;
import kepco.lms.edu.stat.EdustatService;
import kepco.lms.edu.stat.EdustatVo;
import kepco.lms.edu.stat.chart.EduStatChartVo;
import kepco.util.StrUtil;

/**
 * 훈련 관리
 */
@Controller
@RequestMapping(value = "/lms/edu")
public class EduController {

    @Autowired
    DetailService detailService;

    @Autowired
    EduService eduService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PackService packService;

    @Autowired
    MemberService memberService;

    @Autowired
    LocalFileService localFileService;

    @Autowired
    PlayService playService;

    @Autowired
    EventService eventService;

    @Autowired
    EdustatService edustatService;
    
    private final static String FILE_PATH = "edu/detail/";

    private final String THUMB_SUFFIX = ".thumb";

    @Value("${global.file.base-path}")
    private Path basePath;
    private String clientAnswerStr;

    /**
     * 훈련 목록
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "list")
    public String list(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/list";

//		req.put("codeParent", "edu");
        List<CategoryVo> categoryList = categoryService.selectAll(req);

        req.put("lms", "lms");
        req.put("pageSize", "6");
        List<DetailVo> list = detailService.selectList(req);

        PageInfo<DetailVo> pageInfo = new PageInfo<DetailVo>(list);

        model.addAttribute("req", req);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", pageInfo);

        return viewName;
    }

    /**
     * 파일 목록
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "fileList")
    @ResponseBody
    protected JsonResponse fileList(@RequestEgovMap EgovMap req, Model model) {

        int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);

        List<EduVo> list = eduService.myListSelect(req);

        model.addAttribute("req", req);
        model.addAttribute("list", list);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 메인페이지 훈련 목록
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "mainList")
    public String mainList(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/main/mainList";

        model.addAttribute("req", req);

        //실감훈련 데이터 조회, 끝나지않은 훈련만 조회
        req.put("lms", "lms");
        req.put("main", "main");
        List<DetailVo> detailList = detailService.selectAll(req);
        
        //조회된 훈련이 4개 미만일경우 최신 4개 조회
        if(detailList.size() < 4) {
        	req.remove("main");
        	req.put("pageSize", "4");
        	detailList = detailService.selectAll(req);
        }

        model.addAttribute("detailList", detailList);
        return viewName;
    }

    /**
     * 메인페이지 훈련 일정
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "mainCalList")
    public String mainCalList(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/main/mainCalList";

        model.addAttribute("req", req);

        int year = 0;
        int mon = 0;
        int startDayOfWeek = 0;
        int endDay = 0;

        Calendar tDay = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        Calendar sDay = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        Calendar eDay = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

        if (StrUtil.isEmpty(req.get("selDate"))) {
            year = sDay.get(Calendar.YEAR);
            mon = sDay.get(Calendar.MONTH);
        } else {
            year = StrUtil.toInt(StrUtil.toStr(req.get("selDate")).substring(0, 4));
            mon = StrUtil.toInt(StrUtil.toStr(req.get("selDate")).substring(4, 6)) - 1;
        }

        sDay.set(year, mon, 1); //해당월 1일
        eDay.set(year, mon - 1, 1);
        eDay.add(Calendar.DATE, -1);

        startDayOfWeek = sDay.get(Calendar.DAY_OF_WEEK) - 1;
        endDay = eDay.get(Calendar.DATE);

        //2차 배열 사용해, 달력에 맞게 일자를 맞추는 방법에서 Map에 모든 정보를 담아 처리하는 방법으로 바꿈
        HashMap<String, Object> cal = new HashMap<String, Object>();

        List<Integer> calIdx = new ArrayList<Integer>();
        List<Integer> calYear = new ArrayList<Integer>();
        List<Integer> calMonth = new ArrayList<Integer>();
        List<Integer> calDay = new ArrayList<Integer>();

        sDay.set(Calendar.DATE, -startDayOfWeek + 1);

        int count = 1;

        //이전월 일자
        for (int i = 1, e = startDayOfWeek; i <= startDayOfWeek; i++) {
            calIdx.add(count);
            calYear.add(sDay.get(Calendar.YEAR));
            calMonth.add(sDay.get(Calendar.MONTH) + 1);
            calDay.add(sDay.get(Calendar.DATE));
            count++;

            sDay.add(Calendar.DATE, +1);
        }

        int thisMonthDay = 1; //해당월 일자
        for (int i = 0; i < 6; i++) {
            for (int j = 0, e = startDayOfWeek; j < 7; j++) {
                if ((i == 0) && (j < startDayOfWeek)) {
                } else if (thisMonthDay <= endDay) {
                    calIdx.add(count);
                    calYear.add(sDay.get(Calendar.YEAR));
                    calMonth.add(sDay.get(Calendar.MONTH) + 1);
                    calDay.add(sDay.get(Calendar.DATE));
                    count++;

                    sDay.add(Calendar.DATE, +1);
                } else {
                    calIdx.add(count);
                    calYear.add(sDay.get(Calendar.YEAR));
                    calMonth.add(sDay.get(Calendar.MONTH) + 1);
                    calDay.add(sDay.get(Calendar.DATE));
                    count++;

                    sDay.add(Calendar.DATE, +1);
                }
            }
        }

        //이전달, 해당달, 다음달, 오늘 문자처리
        sDay.set(year, mon + 1, 1);
        eDay.set(year, mon - 1, 1);

        int prevYear = eDay.get(Calendar.YEAR);
        int nowYear = year;
        int nextYear = sDay.get(Calendar.YEAR);
        int prevMon = eDay.get(Calendar.MONTH) + 1;
        int nextMon = sDay.get(Calendar.MONTH) + 1;

        int toYear = tDay.get(Calendar.YEAR);
        int toMon = tDay.get(Calendar.MONTH) + 1;
        int toDay = tDay.get(Calendar.DATE);
        int nowMon = mon + 1;

        String strToMon = StrUtil.toStr(toMon);
        if (StrUtil.toStr(toMon).length() == 1) {
            strToMon = "0" + StrUtil.toStr(toMon);
        }
        String strToYM = StrUtil.toStr(toYear) + StrUtil.toStr(strToMon);

        cal.put("idx", calIdx);
        cal.put("year", calYear);
        cal.put("month", calMonth);
        cal.put("day", calDay);

        //실감훈련 데이터 조회
        req.put("mainCal", "mainCal");
        req.put("scMon1", strToYM + "01");
        List<DetailVo> detailList = detailService.selectAll(req);
        
        String[] eduTrainBgnList = null;
        if (detailList != null) {
            eduTrainBgnList = new String[detailList.size()];

            for (int i = 0; i < detailList.size(); i++) {
                eduTrainBgnList[i] = detailList.get(i).getEduTrainBgnDate().replace("-", "");
            }
        }

        model.addAttribute("cal", cal);
        model.addAttribute("prevYear", prevYear);
        model.addAttribute("prevMon", prevMon);
        model.addAttribute("nextYear", nextYear);
        model.addAttribute("nextMon", nextMon);
        model.addAttribute("nowYear", nowYear);
        model.addAttribute("nowMon", nowMon);
        model.addAttribute("toYear", toYear);
        model.addAttribute("toMon", toMon);
        model.addAttribute("toDay", toDay);

        model.addAttribute("detailList", detailList);
        model.addAttribute("eduTrainBgnList", eduTrainBgnList);
        return viewName;
    }

    /**
     * 훈련 조회
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "view")
    public String view(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/view";

        DetailVo vo = null;
        List<PackVo> packList = null;
        List<EventVo> accidentList = null;

        if (!StrUtil.isEmpty(req.get("detailIdx"))) {
            vo = detailService.select(req);

            //모듈 팩 리스트
            req.put("eduIdx", vo.getEduIdx());
            packList = packService.selectAll(req);

            accidentList = eventService.accidentList(req);
        }

        model.addAttribute("req", req);
        model.addAttribute("vo", vo);
        model.addAttribute("packList", packList);
        model.addAttribute("accidentList", accidentList);

        return viewName;
    }

    /**
     * 훈련 썸네일
     *
     * @param req
     * @param request
     * @param response
     * @throws Throwable
     */
    @RequestMapping(value = "file")
    protected void file(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        DetailVo vo = null;
        String img_file = "";

        if (!StrUtil.isEmpty(req.get("detailIdx"))) {
            vo = detailService.fileSelect(req);
        }

        if ("eduImage1".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduImage1());
        } else if ("eduImage2".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduImage2());
        } else if ("eduFile1".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile1());
        } else if ("eduFile2".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile2());
        } else if ("eduFile3".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile3());
        } else if ("eduFile4".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile4());
        } else if ("eduFile5".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile5());
        }

        if (vo == null) {
        } else {
            if (!img_file.equals("")) {
                // 불러와야할 이미지에 대한 식별값을 파라미터로 받아옴.
                File file = new File((basePath + "/" + FILE_PATH + img_file));

                if ("eduImage1".equals(req.get("file"))) {
                    file = new File((basePath + "/" + FILE_PATH + "thumb_" + img_file));
                }

                FileInputStream fis = null;

                response.setHeader("Content-type", "image/jpeg");
                response.setHeader("Content-Transfer-Encoding", "binary");

                BufferedInputStream in = null;
                ByteArrayOutputStream bStream = null;
                try {
                    fis = new FileInputStream(file);
                    in = new BufferedInputStream(fis);
                    bStream = new ByteArrayOutputStream();
                    int imgByte;
                    while ((imgByte = in.read()) != -1) {
                        bStream.write(imgByte);
                    }
                    response.setContentLength(bStream.size());
                    bStream.writeTo(response.getOutputStream());
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                } catch (Throwable e) {
                    img_file = "";
                } finally {
                    if (bStream != null) {
                        try {
                            bStream.close();
                        } catch (Throwable e) {
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Throwable e) {
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Throwable e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * 훈련 파일 다운로드
     *
     * @param req
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "download")
    @ResponseBody
    protected ResponseEntity<Resource> download(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        DetailVo vo = null;
        String img_file = "";

        if (!StrUtil.isEmpty(req.get("detailIdx"))) {
            vo = detailService.fileSelect(req);
        }

        if ("eduImage1".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduImage1());
        } else if ("eduImage2".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduImage2());
        } else if ("eduFile1".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile1());
        } else if ("eduFile2".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile2());
        } else if ("eduFile3".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile3());
        } else if ("eduFile4".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile4());
        } else if ("eduFile5".equals(req.get("file"))) {
            img_file = StrUtil.noNull(vo.getEduFile5());
        }

        if (vo == null) {
        } else {
            return localFileService.download(FILE_PATH + img_file, img_file);
        }
        return null;
    }

    /**
     * 훈련 인쇄
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "myPrint")
    public String myprint(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/my/print";

        PlayVo playVo = playService.select(req);
        req.put("detailIdx", playVo.getDetailIdx());

        int memberIdx = StrUtil.getSessionIdx();

        req.put("memberIdx", memberIdx);

        List<EduVo> list = eduService.myListSelect(req);

        model.addAttribute("req", req);
        model.addAttribute("list", list);

        return viewName;
    }

    /**
     * 내 훈련 목록
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "myList")
    public String mylist(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/my/list";

        int memberIdx = StrUtil.getSessionIdx();

        req.put("memberIdx", memberIdx);
        req.put("pageSize", "5");

//		TODO 사용자페이지 교육훈련현황 목록의 기준을 현재 수강신청(regist) 목록에서 수행(play)기준 으로 바꿀 것
//		List<EduVo> list = eduService.myListSelect(req);

        List<EduVo> list = eduService.myPlayListSelect(req);
        PageInfo<EduVo> pageInfo = new PageInfo<EduVo>(list);


        model.addAttribute("req", req);
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", pageInfo);

        return viewName;
    }
    
    /**
     * 내 훈련 통계
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "my/stat")
    public String myStat(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {

        String viewName = "lms/edu/my/stat";

        int memberIdx = StrUtil.getSessionIdx();

        req.put("memberIdx", memberIdx);

        EgovMap vo = edustatService.select(req);

        model.addAttribute("req", req);
        model.addAttribute("vo", vo);

        return viewName;
    }
    
    /**
     * 내 기간별 훈련 통계
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "my/stat/trainStatByPeriod")
	@ResponseBody
	public JsonResponse myStatTrainStatByPeriod(@RequestEgovMap EgovMap req, Model model) {
		
    	int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		List<EduStatChartVo> list = edustatService.myTrainStatByPeriod(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
    
    /**
     * 내 역할별 훈련 비율 통계
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "my/stat/shareByRole")
	@ResponseBody
	public JsonResponse myStatShareByRole(@RequestEgovMap EgovMap req, Model model) {
    	
    	int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		EdustatVo edustatVo = edustatService.shareByRole(req);
		
		model.addAttribute("vo", edustatVo);
		model.addAttribute("req", req);
		return new JsonResponse.Builder().model(model).build();
	}
    /**
     * 내 사고 위치 통계
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "my/stat/accidentLocation")
	@ResponseBody
	public JsonResponse myStatAccidentLocation(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		List<EdustatVo> list = edustatService.accidentLocation(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
     * 내 사고 구분 통계
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "my/stat/accidentType")
	@ResponseBody
	public JsonResponse myStatAccidentType(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
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
	
	/**
     * 내 사고 원인 분석
     *
     * @param req
     * @param cri
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "my/stat/accidentReasonAnalysis")
	@ResponseBody
	public JsonResponse myStatAccidentReasonAnalysis(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		
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
	
	@RequestMapping(value = "my/stat/riskStatusEs")
	@ResponseBody
	public JsonResponse riskStatusEs(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		req.put("riskFactor", "1");
		req.put("riskLevel", "2");
		EduStatChartVo vo = edustatService.riskStatusByUsr(req);
		
		
		model.addAttribute("vo", vo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "my/stat/riskStatusFall")
	@ResponseBody
	public JsonResponse riskStatusFall(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		req.put("riskFactor", "2");
		req.put("riskLevel", "1");
		EduStatChartVo vo1 = edustatService.riskStatusByUsr(req);
		req.remove("riskLevel");
		req.put("riskLevel", "2");
		EduStatChartVo vo2 = edustatService.riskStatusByUsr(req);
		
		model.addAttribute("vo1", vo1);
		model.addAttribute("vo2", vo2);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "my/stat/riskStatusCare")
	@ResponseBody
	public JsonResponse riskStatusCare(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
		req.put("riskFactor", "3");
		EduStatChartVo vo = edustatService.riskStatusByUsr(req);
		
		model.addAttribute("vo", vo);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "my/stat/riskStatusLoad")
	@ResponseBody
	public JsonResponse riskStatusLoad(@RequestEgovMap EgovMap req, Model model) {
		
		int memberIdx = StrUtil.getSessionIdx();
        req.put("memberIdx", memberIdx);
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
	
}
