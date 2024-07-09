package kepco.lms.edu.stat.period;
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
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.stat.EdustatService;
import kepco.util.StrUtil;
/**
 * 통계 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/stat/period")
public class AdminEduStatPeriodController {
	
	@Autowired
	EdustatService edustatService;
	
	@Autowired
	DetailService detailService;
	

	/**
	 * 기간별 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list2")
	public String list2(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/stat/period/list_old";
		req.put("stat", 'y');
		List<DetailVo> list = detailService.selectAll(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return viewName;
	}
	

	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/stat/period/list";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	
	/**
	 * 기간별 통계 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/stat/period/view";
		req.put("stat", 'y');
		DetailVo detailVo = edustatService.detailStat(req);
		
		model.addAttribute("detailVo", detailVo);
		model.addAttribute("req", req);
		
		return viewName;
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
        response.setHeader("Content-Disposition", "attachment;filename=train_period_stat.xlsx");

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