package kepco.lms.edu.sysMgmt;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.category.CategoryService;
import kepco.lms.edu.category.CategoryVo;
/**
 * 시스템 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/sysMgmt")
public class AdminSysMgmtController {
	
	@Autowired
	CategoryService categoryService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 훈련 콘텐츠 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "contentList")
	public String contentList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/sysMgmt/contentList";
		
//		req.put("codeParent", "edu");
		List<CategoryVo> categoryList = categoryService.selectAll(req);
		
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 훈련 과정 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "detailList")
	public String detailList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/sysMgmt/detailList";

		model.addAttribute("req", req);

		return viewName;
	}


}