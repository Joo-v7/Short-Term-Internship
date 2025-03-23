package kepco.cms.dashboard;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
/**
 * CMS 대시보드
 */
@Controller
@RequestMapping("${global.admin-path}/cms/dashboard")
public class AdminDashboardController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@SiteLayout
	@RequestMapping(value = {"", "main"})
	public String main(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		
		return "admin/cms/dashboard/main";
	}
	
}
