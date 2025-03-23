package kepco.cms.member;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
/**
 * 관리자 목록
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/member/adm")
public class AdminAdmMemberController {
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 교수자 회원 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/list";
			
		model.addAttribute("scMemberType","2");
		model.addAttribute("req",req);
		
		return viewName;
	}
	
	/**
	 * 관리자 회원 목록 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "sp/list")
	public String spList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/member/list";
			
		model.addAttribute("scMemberType","3");
		model.addAttribute("req",req);
		
		return viewName;
	}
}
