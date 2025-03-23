package kepco.cms.zzz.paging;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import kepco.cms.zzz.crud.CrudVo;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "cms/zzz/paging")
public class PagingController {
	
	@Autowired
	PagingService pagingService;
	
	
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) {
		
		// 실제로는 com.github.pagehelper.Page<T> 를 리턴
		List<CrudVo> list = pagingService.selectList(req);
		
		PageInfo<CrudVo> pageInfo = new PageInfo<CrudVo>(list);
		
		model.addAttribute("req", req);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		
		return "cms/zzz/paging/list";

	}
	
	@RequestMapping(value = "listJsp")
	public String listJsp(@RequestEgovMap EgovMap req, Model model) {
		
		// 이런방식도 가능
		PageInfo<CrudVo> pageInfo = pagingService.selectListPageInfo(req);
		List<CrudVo> list = pageInfo.getList();
		
		model.addAttribute("req", req);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("list", list);
		
		return "cms/zzz/paging/list";

	}
	
}
