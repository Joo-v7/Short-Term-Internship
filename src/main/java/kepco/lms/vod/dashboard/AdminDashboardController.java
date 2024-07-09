package kepco.lms.vod.dashboard;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
/**
 * 영상교육 대시보드
 */
@Controller
@RequestMapping("${global.admin-path}/lms/vod/dashboard")
public class AdminDashboardController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 대시보드
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "dashboard")
	public String dashboard(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/dashboard";
		
		model.addAttribute("req", req);

		return viewName;
	}
	
}
