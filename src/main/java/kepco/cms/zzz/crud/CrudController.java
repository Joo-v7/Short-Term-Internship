package kepco.cms.zzz.crud;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;

@Controller
@RequestMapping(value = "cms/zzz/crud")
public class CrudController {
	
	@Autowired
	CrudService crudService;
	
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		return "cms/zzz/crud/list";
	}
	
	@RequestMapping(value = "listData")
	public String listData(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<CrudVo> list = crudService.selectList(req);
		model.addAttribute("list", list);
		
		return "cms/zzz/crud/listData";
	}
	
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<CrudVo> list = crudService.selectList(req);
		model.addAttribute("list", list);
		
		// TODO: 커스텀 @ResponseBody 애노테이션 만들어서 Model 자체를 리턴하는 걸 고려
		return new JsonResponse.Builder().model(model).build();
	}
	
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) {
		model.addAttribute("req", req);
		
		CrudVo row = crudService.select(req);
		
		model.addAttribute("row", row);
		
		return "cms/zzz/crud/view";
	}
	
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) {
		model.addAttribute("req", req);
		
		CrudVo row = crudService.select(req);
		
		model.addAttribute("row", row);
		
		return "cms/zzz/crud/form";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		// "Unrecognized field" 무시 목적
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CrudVo crudVo = mapper.convertValue(req, CrudVo.class);
		
		long rowCnt = 0;
		if (StrUtil.isBlank(crudVo.getCrudIdx())) {
			rowCnt = crudService.insert(crudVo);
		} else {
			rowCnt = crudService.update(crudVo);
		}
		
		if(rowCnt == 0) {
			throw new CmsCustomException(CmsStatusCode.INTERNAL_SERVER_ERROR, "데이터가 저장되지 않았습니다.");
		}
		
		return new JsonResponse.Builder().data(crudVo).build();
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		if(StrUtil.isEmpty(req.get("crudIdx")) && StrUtil.isEmpty(req.get("crudIdxList"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		long rowCnt = crudService.delete(req);
		
		return new JsonResponse.Builder().data(rowCnt).build();
	}
}
