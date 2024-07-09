package kepco.lms.edu.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import kepco.util.CamelMap;
import kepco.util.StrUtil;

/**
 * 통계 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/stat")
public class AdminEdustatController {

    @Autowired
    EdustatService edustatService;

    @Autowired
    DetailService detailService;

    @Autowired
    PlayService playService;

    @Autowired
    EventService eventService;

    @Autowired
    ModulePlayService modulePlayService;

    @Autowired
    PlayTeamService playTeamService;

    /**
     * 통계 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "list2")
    public String list2(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/list_old";

        List<DetailVo> list = detailService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return viewName;
    }

    @SiteLayout
    @RequestMapping(value = "list")
    public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/list";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 통계 목록 데이터
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "listJson")
    @ResponseBody
    public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
        int listCnt = 0;
        req.put("stat", 'y');
        List<DetailVo> list = detailService.selectAll(req);
        listCnt = list.size();

        model.addAttribute("listCnt", listCnt);
        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    @RequestMapping(value = "listJson2")
    @ResponseBody
    public JsonResponse listJson2(@RequestEgovMap EgovMap req, Model model) {

        int listCnt = 0;
        req.put("stat", 'y');
        List<DetailVo> list = detailService.selectAll(req);
        listCnt = list.size();

        model.addAttribute("listCnt", listCnt);
        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    @RequestMapping(value = "modulePlayListJson")
    @ResponseBody
    public JsonResponse modulePlayListJson(@RequestEgovMap EgovMap req, Model model) {

        int listCnt = 0;
        List<ModulePlayVo> list = modulePlayService.modules(req);
        listCnt = list.size();

        model.addAttribute("listCnt", listCnt);
        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    @RequestMapping(value = "modulePlayListByPlayTeam")
    @ResponseBody
    public JsonResponse modulePlayListByPlayTeam(@RequestEgovMap EgovMap req, Model model) {

        List<PlayTeamVo> list = playTeamService.modulePlayListByPlayTeam(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @brief 만족도조사 통계 과정 목록
     */
    @RequestMapping(value = "poll/listJson")
    @ResponseBody
    public JsonResponse pollListJson(@RequestEgovMap EgovMap req, Model model) {
        // 쿼리 변경
        List<CamelMap> pollList = edustatService.selectPollList(req);

        model.addAttribute("req", req);
        model.addAttribute("pollList", pollList);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @brief 만족도조사 통계현황 만족도조사 목록
     */
    @RequestMapping(value = "overview/listJson")
    @ResponseBody
    public JsonResponse overviewListJson(@RequestEgovMap EgovMap req, Model model) {
        // 목록 가져오기
        List<CamelMap> overviewList = edustatService.selectOverviewList(req);

        model.addAttribute("req", req);
        model.addAttribute("overviewList", overviewList);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 만족도조사 view
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "poll/list")
    public String pollList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        if(!req.containsKey("detailIdx")) {
            req.put("detailIdx", "");
        }
        String viewName = "admin/lms/edu/stat/poll/list";
        req.put("stat", 'y');
        List<DetailVo> list = detailService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 위험성평가 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "risk/list")
    public String riskList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/risk/list";
        req.put("stat", 'y');
        List<DetailVo> list = detailService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 사후평가 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "evaluation/list")
    public String evaluationList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        if(!req.containsKey("detailIdx")) {
            req.put("detailIdx", "");
        }

        String viewName = "admin/lms/edu/stat/evaluation/list";
        req.put("stat", 'y');
        List<DetailVo> list = detailService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * @param req
     * @param model
     * @return
     * @brief 사후평가 데이터 목록
     */
    @RequestMapping(value = "evaluation/listJson")
    @ResponseBody
    public JsonResponse evaluationListJson(@RequestEgovMap EgovMap req, Model model) {
        List<CamelMap> listData = edustatService.selectEvalList(req);

        model.addAttribute("req", req);
        model.addAttribute("rsData", listData);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 통계 선택
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "select")
    @ResponseBody
    public JsonResponse select(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        EgovMap list = edustatService.select(req);
        model.addAttribute("list", list);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 통계 조회
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "view")
    public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/view";
        req.put("stat", 'y');
        DetailVo detailVo = edustatService.detailStat(req);

        model.addAttribute("detailVo", detailVo);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 통계 현황 조회
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "overview")
    public String overview(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/overview";

        EdustatVo edustatVo = edustatService.statOverview(req);

        model.addAttribute("edustatVo", edustatVo);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 사후평가 통계 조회
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "evaluation/view")
    public String evaluationView(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/evaluation/view";

        CamelMap detailData = edustatService.selectEvalViewDetailData(req);

        req.put("evIdx", detailData.get("evIdx"));

        List<CamelMap> evalQsonList = edustatService.evalQsonList(req);

        model.addAttribute("req", req);
        model.addAttribute("detailEduInfo", detailData);
        model.addAttribute("evalQsonList", evalQsonList);

        return viewName;
    }

    /**
     * 사후평가 통계 현황
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "evaluation/overview/list")
    public String evaluationOverviewList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/evaluation/listOverview";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * @param req
     * @param model
     * @return
     * @throws Exception
     * @brief 사후평가 통계현황 - 목록
     */
    @RequestMapping("evaluation/overview/listJson")
    @ResponseBody
    public JsonResponse overviewEvaluationListJson(@RequestEgovMap EgovMap req, Model model) throws Exception {

        // 사후평가 목록
        List<CamelMap> rsData = edustatService.selectEvalOverviewList(req);

        model.addAttribute("req", req);
        model.addAttribute("rsData", rsData);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @throws Exception
     * @brief 사후평가 view
     */
    @RequestMapping("evaluation/overview")
    public String evaluationOverview(@RequestEgovMap EgovMap req, Model model) throws Exception {
        String viewName = "admin/lms/edu/stat/evaluation/overview";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * @param req
     * @param model
     * @return
     * @throws Exception
     * @brief 사후평가 통계현황 > 전체 과정별 참여,미참여 인원
     */
    @RequestMapping("/evaluation/overview/getEvalPlayCountData")
    @ResponseBody
    public JsonResponse getEvalPlayCountData(@RequestEgovMap EgovMap req, Model model) throws Exception {

        CamelMap evalPlayCountData = edustatService.evalPlayCountData(req);
        evalPlayCountData.put("allPlayNotCnt", (evalPlayCountData.getInt("allCnt") - evalPlayCountData.getInt("allPlayCnt")));
        evalPlayCountData.remove("allCnt");

        List<CamelMap> rsData = new ArrayList<>();

        for (Map.Entry<String, Object> val : evalPlayCountData.entrySet()) {
            String key = val.getKey();
            Integer value = evalPlayCountData.getInt(key);
            if(key.equals("allPlayCnt")) {
                key = "참여";
            }
            if(key.equals("allPlayNotCnt")) {
                key = "미참여";
            }
            CamelMap data = new CamelMap();
            data.put("title", key);
            data.put("cnt", value);

            rsData.add(data);
        }

        model.addAttribute("req", req);
        model.addAttribute("rsData", rsData);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @throws Exception
     * @brief 사후평가 통계현황 > 전체 과정별 참여,미참여 인원
     */
    @RequestMapping("evaluation/overview/getEvalPlayCountXYData")
    @ResponseBody
    public JsonResponse getEvalPlayCountXYData(@RequestEgovMap EgovMap req, Model model) throws Exception {

        List<CamelMap> evalPlayCountXYData = edustatService.evalPlayCountXYData(req);
        List<CamelMap> rsData = new ArrayList<>();

        for (int i = 0; i < evalPlayCountXYData.size(); i++) {
            CamelMap data = new CamelMap();
            data.put("title", (evalPlayCountXYData.get(i).getStr("trainTitle") + " " + evalPlayCountXYData.get(i).getStr("eduNumeral")));
            data.put("playCnt", evalPlayCountXYData.get(i).getInt("evalPlayCnt"));
            data.put("playNotCnt", (evalPlayCountXYData.get(i).getInt("playCnt") - evalPlayCountXYData.get(i).getInt("evalPlayCnt")));

            rsData.add(i, data);
        }

        // list size별로 height조정
        // 하나의 항목 => 60px정도 주면됨
        int listSize = rsData.size();

        model.addAttribute("req", req);
        model.addAttribute("rsData", rsData);
        model.addAttribute("listSize", listSize);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @throws Exception
     * @brief 사후평가 통계현황 > 전체 과정별 참여,미참여 인원
     */
    @RequestMapping("evaluation/overview/getEvalPlayDataTable")
    @ResponseBody
    public JsonResponse getEvalPlayDataTable(@RequestEgovMap EgovMap req, Model model) throws Exception {
        List<CamelMap> evalPlayDataTable = edustatService.evalPlayDataTable(req);

        model.addAttribute("req", req);
        model.addAttribute("rsData", evalPlayDataTable);

        return new JsonResponse.Builder().model(model).build();
    }


    /**
     * 만족도조사 통계 조회
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "poll/view")
    public String pollView(@RequestEgovMap EgovMap req, Model model) throws Exception {
        String viewName = "admin/lms/edu/stat/poll/view";

        CamelMap detailData = edustatService.selectPollViewDetailData(req);
        req.put("poIdx", detailData.get("poIdx"));
        List<CamelMap> pollQsonList = edustatService.pollQsonList(req);

        model.addAttribute("req", req);
        model.addAttribute("detailEduInfo", detailData);
        model.addAttribute("pollQsonList", pollQsonList);

        return viewName;
    }

    /**
     * 만족도조사 통계 list
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "poll/overview/list")
    public String pollOverviewList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/poll/listOverview";
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 만족도조사 통계 view
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "poll/overview")
    public String pollOverview(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/poll/overview";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * @param req
     * @param model
     * @return
     * @brief 만족도조사 통계현황 > 전체과정별 참여, 미참여 인원 비율
     */
    @RequestMapping("/overview/getPollPlayCountData")
    @ResponseBody
    public JsonResponse getChartDetailOverview(@RequestEgovMap EgovMap req, Model model) {

        CamelMap pollPlayCountData = edustatService.pollPlayCountData(req);
        pollPlayCountData.put("allPlayNotCnt", (pollPlayCountData.getInt("allCnt") - pollPlayCountData.getInt("allPlayCnt")));
        pollPlayCountData.remove("allCnt");

        List<CamelMap> rsData = new ArrayList<>();

        for (Map.Entry<String, Object> val : pollPlayCountData.entrySet()) {
            String key = val.getKey();
            Integer value = pollPlayCountData.getInt(key);
            if (key.equals("allPlayCnt")) {
                key = "참여";
            }
            if (key.equals("allPlayNotCnt")) {
                key = "미참여";
            }
            CamelMap data = new CamelMap();
            data.put("title", key);
            data.put("cnt", value);

            rsData.add(data);
        }

        model.addAttribute("req", req);
        model.addAttribute("rsData", rsData);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @brief 만족도조사 과정별 참여인원 미참여인원 - XYChart
     */
    @RequestMapping("/overview/getPollPlayCountXYData")
    @ResponseBody
    public JsonResponse getPollPlayCountXYData(@RequestEgovMap EgovMap req, Model model) {

        List<CamelMap> pollPlayCountXYData = edustatService.pollPlayCountXYData(req);
        List<CamelMap> rsData = new ArrayList<>();

        for (int i = 0; i < pollPlayCountXYData.size(); i++) {
            CamelMap data = new CamelMap();
            data.put("title", (pollPlayCountXYData.get(i).getStr("trainTitle") + " " + pollPlayCountXYData.get(i).getStr("eduNumeral")));
            data.put("playCnt", pollPlayCountXYData.get(i).getInt("pollPlayCnt"));
            data.put("playNotCnt", (pollPlayCountXYData.get(i).getInt("playCnt") - pollPlayCountXYData.get(i).getInt("pollPlayCnt")));

            rsData.add(i, data);
        }

        // list size별로 height조정
        // 하나의 항목 => 60px정도 주면됨
        int listSize = rsData.size();

        model.addAttribute("req", req);
        model.addAttribute("rsData", rsData);
        model.addAttribute("listSize", listSize);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * @param req
     * @param model
     * @return
     * @brief 족도조사 사용중 훈련과정 - DataTable
     */
    @RequestMapping("/overview/getPollPlayDataTable")
    @ResponseBody
    public JsonResponse getPollPlayDataTable(@RequestEgovMap EgovMap req, Model model) {

        List<CamelMap> pollPlayDataTable = edustatService.pollPlayDataTable(req);

        model.addAttribute("req", req);
        model.addAttribute("rsData", pollPlayDataTable);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 위험성 평가 조회
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "risk/view")
    public String riskView(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "admin/lms/edu/stat/risk/view";
        req.put("stat", 'y');
        DetailVo detailVo = edustatService.detailStat(req);
        EdustatVo edustatVo = edustatService.accidentCntByRole(req);

        model.addAttribute("detailVo", detailVo);
        model.addAttribute("edustatVo", edustatVo);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 위험성 평가 조회
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "risk/overview")
    public String riskOverview(@RequestEgovMap EgovMap req, Model model) throws Exception {

        EdustatVo edustatVo = edustatService.statOverview(req);
        String viewName = "admin/lms/edu/stat/risk/overview";
        EdustatVo statVo = edustatService.accidentCntByRole(req);

        model.addAttribute("edustatVo", edustatVo);
        model.addAttribute("statVo", statVo);
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 학습자 목록
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "studentList")
    @ResponseBody
    public JsonResponse studentList(@RequestEgovMap EgovMap req, Model model) {


        List<PlayVo> list = playService.studentList(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 대시보드
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "dashBoard")
    @ResponseBody
    public JsonResponse dashBoard(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        req.put("memberIdx", StrUtil.getSessionIdx());
        Object vo = edustatService.dashBoardStat(req);
        model.addAttribute("vo", vo);

        return new JsonResponse.Builder().model(model).build();
    }


    /**
     * 대시보드 달력
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "dashBoard/calendar")
    @ResponseBody
    public JsonResponse dashboardCalendar(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        List<DetailVo> list = detailService.dashboardCalendar(req);

        model.addAttribute("list", list);

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

        List<DetailVo> list = detailService.selectAll(req);


        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("훈련 통계");
        int rowNum = 0;

        rowNum = createRow(sheet, rowNum, "훈련IDX", "훈련명", "기수", "교수자", "훈련구분", "훈련일시", "훈련시간(분)", "훈련수행(횟수)", "훈련인원(명)", "수행모듈(갯수)", "수행성공(횟수)", "수행실패(횟수)");
        for (DetailVo detailVo : list) {
            rowNum = createRow(sheet, rowNum, StrUtil.toStr(detailVo.getDetailIdx()), detailVo.getTrainTitle(), detailVo.getEduNumeral(), detailVo.getTeacherNm(),
                    ("1".equals(detailVo.getEduType()) ? "개인" : "협동"), StrUtil.toStr(detailVo.getEduTrainBgnDate()), StrUtil.toStr(detailVo.getSumPlayMinute()),
                    StrUtil.toStr(detailVo.getPlayCnt()), StrUtil.toStr(detailVo.getPlayerCnt()), StrUtil.toStr(detailVo.getModuleCdCnt()),
                    StrUtil.toStr(detailVo.getPlaySuccessCnt()), StrUtil.toStr(detailVo.getPlayFailCnt()));
        }
        rowNum++;

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=train_stat.xlsx");

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
     * 이벤트 목록
     * @param req
     * @param model
     * @return
     */
//	@RequestMapping(value = "viewEventList")
//	@ResponseBody
//	public JsonResponse viewEventList(@RequestEgovMap EgovMap req, Model model) {
//
//		
//		List<EventVo> list = eventService.selectAll(req);
//		
//		model.addAttribute("list", list);
//		model.addAttribute("req", req);
//
//		return new JsonResponse.Builder().model(model).build();
//	}

    /**
     * 사고 목록
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "viewAccidentList")
    @ResponseBody
    public JsonResponse viewAccidentList(@RequestEgovMap EgovMap req, Model model) {

        req.put("accidentYn", "Y");
        List<EventVo> list = eventService.selectAll(req);

        model.addAttribute("list", list);
        model.addAttribute("req", req);

        return new JsonResponse.Builder().model(model).build();
    }
}