package kepco.lms.vod.stat;
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

import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.vod.VodService;
import kepco.lms.vod.VodVo;
import kepco.util.StrUtil;
/**
 * 영상교육 통계
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/stat")
public class AdminVodStatController {
	
	@Autowired
	VodStatService vodStatService;
	
	@Autowired
	VodService vodService;
	
	/**
	 * 영상교육 통계 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/stat/list";
		
//		List<VodVo> list = vodService.selectAll(req);
		
//		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return viewName;
	}
	
	/**
	 * 통계 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
		
		List<EgovMap> list = vodStatService.vodStatList(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	

	/**
	 * 통계 콘텐츠 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "contents/listJson")
	@ResponseBody
	public JsonResponse contentsListJson(@RequestEgovMap EgovMap req, Model model) {
		
		List<EgovMap> list = vodStatService.vodContentsStatList(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 통계 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/stat/view";
		
		VodStatVo vodStatVo = vodStatService.select(req);
		
		model.addAttribute("vo", vodStatVo);
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	/**
	 * 통계 현황 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "overview")
	public String overview(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/stat/overview";
		
//		VodVo vodVo = vodService.select(req);
		
//		model.addAttribute("vodVo", vodVo);
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	@RequestMapping(value = "overview/studentList")
	@ResponseBody
	public JsonResponse overviewStudentList(@RequestEgovMap EgovMap req, Model model) throws Exception  {
				
		List<EgovMap> list = vodStatService.studentList(req);
		
		model.addAttribute("req", req);
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

        List<VodStatVo> list = vodStatService.vodStatExcel(req);
        List<VodStatVo> contentsList = vodStatService.vodContentsStatExcel(req);
        
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("영상교육 통계");
        int rowNum = 0;

        rowNum = createRow(sheet, rowNum, "교육IDX", "교육명", "교육시작일자", "교육종료일자", "콘텐츠 수", "영상시간(분)", "학습시간(최소)", "학습시간(최대)", "학습시간(평균)", "학습자(명)", "수료자(명)");
        
        for (VodStatVo vodStatVo : list) {
            rowNum = createRow(sheet, rowNum, StrUtil.toStr(vodStatVo.getVodIdx()), vodStatVo.getVodTitle(), 
            		vodStatVo.getVodEduBgnDate(), vodStatVo.getVodEduEndDate(), StrUtil.toStr(vodStatVo.getContentsCnt()),
            		StrUtil.toStr(vodStatVo.getStudySumTime()) + "분", StrUtil.toStr(vodStatVo.getLearnMinTime()) + "분",
            		StrUtil.toStr(vodStatVo.getLearnMaxTime()) + "분", StrUtil.toStr(vodStatVo.getLearnAvgTime()) + "분", 
            		StrUtil.toStr(vodStatVo.getVodStudentsCnt()), StrUtil.toStr(vodStatVo.getVodCompleteCnt()));
            		
        }
        rowNum++;

        rowNum = createRow(sheet, rowNum, "콘텐츠IDX", "콘텐츠명", "교육분야", "영상시간(분)", "학습시간(최소)", "학습시간(최대)", "학습시간(평균)", "학습자(명)", "수료자(명)");
        
        for (VodStatVo vodStatVo : contentsList) {
            rowNum = createRow(sheet, rowNum, StrUtil.toStr(vodStatVo.getContentIdx()), vodStatVo.getContentTitle(), vodStatVo.getCategoryNm(), 
            		StrUtil.toStr(vodStatVo.getStudySumTime()) + "분", StrUtil.toStr(vodStatVo.getLearnMinTime()) + "분",
            		StrUtil.toStr(vodStatVo.getLearnMaxTime()) + "분", StrUtil.toStr(vodStatVo.getLearnAvgTime()) + "분", 
            		StrUtil.toStr(vodStatVo.getVodStudentsCnt()), StrUtil.toStr(vodStatVo.getVodCompleteCnt()));
            		
        }
        rowNum++;
        
        
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=vod_stat.xlsx");

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
}