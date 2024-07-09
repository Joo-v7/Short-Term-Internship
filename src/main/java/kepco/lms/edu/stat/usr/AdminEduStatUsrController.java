package kepco.lms.edu.stat.usr;
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
@RequestMapping(value = "${global.admin-path}/lms/edu/stat/usr")
public class AdminEduStatUsrController {
	
	@Autowired
	EdustatService edustatService;
	
	@Autowired
	DetailService detailService;
	
	/**
	 * 사용자별 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list2")
	public String list2(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/stat/usr/list_old";
		req.put("stat", 'y');
		List<DetailVo> list = detailService.selectAll(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/stat/usr/list";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		List<EduStatUsrVo> list = edustatService.usrList(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}
	

	/**
	 * 학습자별 훈련 수행 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "playListJson")
	@ResponseBody
	public JsonResponse playListJson(@RequestEgovMap EgovMap req, Model model) {
				
		List<EduStatUsrVo> list = edustatService.usrPlayList(req);
		
		model.addAttribute("list", list);
		model.addAttribute("req", req);

		return new JsonResponse.Builder().model(model).build();
	}

	/**
	 * 사용자별 통계 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/stat/usr/view";
		req.put("stat", 'y');
		DetailVo detailVo = detailService.select(req);
		
		
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

		List<EduStatUsrVo> list = edustatService.usrList(req);
        
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("사용자별 통계");
        int rowNum = 0;

        rowNum = createRow(sheet, rowNum, "아이디", "성명", "훈련시작일자", "훈련종료일자", "훈련시간(분)", "훈련수행과정(횟수)", "주작업자(횟수)", "보조작업자(횟수)", "지상작업자(횟수)", "작업책임자(횟수)");
        for (EduStatUsrVo eduStatUsrVo : list) {
        	rowNum = createRow(sheet, rowNum, eduStatUsrVo.getMemberId(), eduStatUsrVo.getMemberNm(), StrUtil.toStr(eduStatUsrVo.getStartTime()), StrUtil.toStr(eduStatUsrVo.getEndTime()), 
        			StrUtil.toStr(eduStatUsrVo.getSumPlayMinute()), StrUtil.toStr(eduStatUsrVo.getPlayCnt()), StrUtil.toStr(eduStatUsrVo.getMainCnt()),
        			StrUtil.toStr(eduStatUsrVo.getSubCnt()), StrUtil.toStr(eduStatUsrVo.getGroundCnt()), StrUtil.toStr(eduStatUsrVo.getSuperCnt()));
        }
        rowNum++;

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=train_user_stat.xlsx");

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