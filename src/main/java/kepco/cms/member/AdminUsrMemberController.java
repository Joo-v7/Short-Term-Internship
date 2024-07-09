package kepco.cms.member;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
/**
 * 학습자 목록
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/member/usr")
public class AdminUsrMemberController {
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 학습자 회원 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/list";
		
		model.addAttribute("scMemberType","1");
		model.addAttribute("req",req);
		
		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "lmsList")
	public String lmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/lmsList";
		
		model.addAttribute("scMemberType","1");
		model.addAttribute("req",req);
		
		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "rasList")
	public String rasList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/rasList";
		
		model.addAttribute("scMemberType","1");
		model.addAttribute("req",req);
		
		return viewName;
	}
	
}
