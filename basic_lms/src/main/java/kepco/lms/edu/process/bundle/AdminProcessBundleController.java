package kepco.lms.edu.process.bundle;
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
import kepco.lms.edu.module.ModuleVo;
import kepco.lms.edu.process.EduProcessService;
import kepco.lms.edu.process.EduProcessVo;
import kepco.util.StrUtil;
/**
 * 절차 번들 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/process/bundle")
public class AdminProcessBundleController {
	
	@Autowired
	ProcessBundleService processBundleService;
	
	@Autowired
	EduProcessService eduProcessService;
	
	@Autowired
	ModuleService moduleService;
	/**
	 * 번들 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/process/bundle/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 절차 번들 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/process/bundle/form";
		
		ProcessBundleVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("bundleIdx"))) {
			vo = processBundleService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 번들 절차 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bundleList")
	public String bundleList(@RequestEgovMap EgovMap req, Model model) {
		
		String viewName = "admin/lms/edu/process/bundle/bundleList";
		
		List<EduProcessVo> bundleList = null;
		if(!StrUtil.isEmpty(req.get("moduleIdx"))){
			ModuleVo moduleVo = moduleService.select(req); 
			
			if(moduleVo != null) {
				EgovMap processMap = new EgovMap();
			    processMap.put("moduleCd", moduleVo.getModuleCd());
				bundleList = eduProcessService.selectAll(processMap);
			}
			
		}
		
		
		
		
//		List<ProcessBundleVo> bundleList = null;
//		if(!StrUtil.isEmpty(req.get("moduleIdx"))){
//			bundleList = processBundleService.selectAll(req);
//		}
		

		model.addAttribute("req", req);
		model.addAttribute("bundleList", bundleList);
		
		return viewName;
	}
	/**
	 * 번들 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<ProcessBundleVo> list = null;
		if(!StrUtil.isEmpty(req.get("moduleIdx"))){
			list = processBundleService.selectAll(req);
		}
		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 절차 번들 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ProcessBundleVo processBundleVo = mapper.convertValue(req, ProcessBundleVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("bundleIdx"))) {
			processBundleVo.setInsertIdx(StrUtil.getSessionIdx());
			processBundleVo.setInsertIp(StrUtil.getClientIP());
			result = processBundleService.insert(processBundleVo);
		} else {
			result = processBundleService.update(processBundleVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(processBundleVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 절차 번들 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("bundleIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = processBundleService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	
	/**
	 * 절차 번들 리스트
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "processList")
	public String processList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/process/bundle/processList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	/**
	 * 절차 번들 순서변경
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "orderChange")
	@ResponseBody
	public JsonResponse orderChange(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ProcessBundleVo processBundleVo = mapper.convertValue(req, ProcessBundleVo.class);

		long result = 0 ;
		
		if(!StrUtil.isEmpty(req.get("order[]"))){
			if (req.get("order[]").getClass().isArray()) {
				for(int i=0;i<((String[]) req.get("order[]")).length;i++){
					
					String str = ((String[])req.get("order[]"))[i];
					Integer number = Integer.valueOf(str);
					processBundleVo.setBundleIdx(number);
					processBundleVo.setProcessOrder(i+1);
					 
					result = processBundleService.update(processBundleVo);
				
				}
			}else {
				String str = (String)req.get("order[]");
				Integer number = Integer.valueOf(str);
				processBundleVo.setBundleIdx(number);
				processBundleVo.setProcessOrder(1);
				 
				result = processBundleService.update(processBundleVo);
			}
		}
		return new JsonResponse.Builder().data(model).build();
	}
}