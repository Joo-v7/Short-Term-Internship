package kepco.cms.zzz.thymeleaf;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "cms/zzz/thymeleaf")
public class ThymeleafController {
	
	
	@RequestMapping(value = "case1")
	public String case1(@RequestEgovMap EgovMap req, Model model) {
		
		model.addAttribute("req", req);
		
		model.addAttribute("myLayoutName", "case1_layout");
		
		return "cms/zzz/thymeleaf/case1";

	}
	
	@RequestMapping(value = "case2")
	public String case2(@RequestEgovMap EgovMap req, Model model) {
		
		model.addAttribute("req", req);
		
		model.addAttribute("myLayoutName", "case2_layout");
		
		return "cms/zzz/thymeleaf/case2";

	}
	
	@SiteLayout
	@RequestMapping(value = "case3")
	public String case3(@RequestEgovMap EgovMap req, Model model) {
		
		model.addAttribute("req", req);
		
		model.addAttribute("myLayoutName", "case2_layout");
		
		return "cms/zzz/thymeleaf/case2";

	}

	@RequestMapping(value = "dashboard")
	public String dashboard(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);
				
		return "cms/zzz/thymeleaf/dashboard";
		
	}
	
	@RequestMapping(value = "login")
	public String login(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);
				
		return "cms/zzz/thymeleaf/login";
		
	}
	
	
}
