package kepco.lms.edu.pack;
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
import kepco.lms.edu.module.ModuleService;
import kepco.util.StrUtil;
/**
 * 모듈 팩 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/pack")
public class AdminPackController {
	
	@Autowired
	PackService packService;
	
	@Autowired
	ModuleService moduleService;
	/**
	 * 팩 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/pack/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 모듈 팩 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/pack/form";
		
		PackVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("packIdx"))) {
			vo = packService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 팩 모듈 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "eduModuleList")
	public String detail(@RequestEgovMap EgovMap req, Model model) {
		
		String viewName = "admin/lms/edu/pack/eduModuleList";
		model.addAttribute("req", req);
		
		List<PackVo> list = null;
		if(!StrUtil.isEmpty(req.get("eduIdx"))){
			list = packService.selectAll(req);
		}
		
		model.addAttribute("list", list);
		
		return viewName;
	}
	/**
	 * 팩 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<PackVo> list = null;
		if(!StrUtil.isEmpty(req.get("eduIdx"))){
			list = packService.selectAll(req);
		}
		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 모듈 팩 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		PackVo packVo = mapper.convertValue(req, PackVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("packIdx"))) {
			packVo.setInsertIdx(StrUtil.getSessionIdx());
			packVo.setInsertIp(StrUtil.getClientIP());
			result = packService.insert(packVo);
		} else {
			packVo.setUpdateIdx(StrUtil.getSessionIdx());
			packVo.setUpdateIp(StrUtil.getClientIP());
			result = packService.update(packVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(packVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 모듈 팩 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("packIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = packService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 모듈 팩 순서변경
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "packChange")
	@ResponseBody
	public JsonResponse packChange(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		PackVo packVo = mapper.convertValue(req, PackVo.class);

		long result = 0 ;
		
		if(!StrUtil.isEmpty(req.get("order[]"))){
			if (req.get("order[]").getClass().isArray()) {
				for(int i=0;i<((String[]) req.get("order[]")).length;i++){
					
					String str = ((String[])req.get("order[]"))[i];
					Integer number = Integer.valueOf(str);
					packVo.setPackIdx(number);
					packVo.setModuleOrder(i+1);
					 
					packVo.setUpdateIdx(StrUtil.getSessionIdx());
					packVo.setUpdateIp(StrUtil.getClientIP());
					result = packService.update(packVo);
				
				}
			}else {
				String str = (String)req.get("order[]");
				Integer number = Integer.valueOf(str);
				packVo.setPackIdx(number);
				packVo.setModuleOrder(1);
				 
				packVo.setUpdateIdx(StrUtil.getSessionIdx());
				packVo.setUpdateIp(StrUtil.getClientIP());
				result = packService.update(packVo);
			}
		}
		return new JsonResponse.Builder().data(model).build();
	}
}