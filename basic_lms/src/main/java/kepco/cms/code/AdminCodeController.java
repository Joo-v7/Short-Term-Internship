package kepco.cms.code;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.menu.MenuVo;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 공통코드
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/code")
public class AdminCodeController {
	
	@Autowired
	CodeService codeService;
	
	/**
	 * 공통코드 메뉴트리
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = { "", "tree" })
	public String tree(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		model.addAttribute("req", req);
		
		List<CodeVo> vo = codeService.selectAll(req);

		model.addAttribute("codeList", vo);
		
		return "admin/cms/code/tree";
	}
	
	/**
	 * 공통코드 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "treeJson")
	@ResponseBody
	public JsonResponse treeJson(@RequestEgovMap EgovMap req, Model model) {
		
		List<CodeVo> codeList = codeService.selectAll(req);
		model.addAttribute("codeList", codeList);
		
		return new JsonResponse.Builder().model(model).build();
		
	}
	
	/**
	 * 공통코드 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/code/list";
		
		return viewName;
	}
	
	/**
	 * 공통코드 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/code/form";
		
		CodeVo codeVo = null;
		
		if(!StrUtil.isEmpty(req.get("code"))) {
			codeVo = codeService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", codeVo);
		
		return viewName;
	}
	
	/**
	 * 공통코드 등록/수정
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CodeVo codeVo = mapper.convertValue(req, CodeVo.class);
		
		if (StrUtil.isBlank(codeVo.getCode())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "코드는 필수입력 입니다.");
		}
		if (StrUtil.isBlank(codeVo.getCodeNm())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "코드명은 필수입력 입니다.");
		}
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("insert"))) {
			if(StrUtil.isEmpty(req.get("codeParent"))) {
				result = codeService.insert(codeVo);
			}else {
				result = codeService.insertCode(codeVo);
			}
		}else {
			result = codeService.update(codeVo);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(codeVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 공통코드 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<CodeVo> list = codeService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 공통코드 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		if (StrUtil.isEmpty(req.get("code"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		int result = codeService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
}