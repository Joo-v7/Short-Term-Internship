package kepco.lms.vod.bundle;
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
import kepco.lms.vod.content.ContentService;
import kepco.util.StrUtil;
/**
 * 영상교육 번들 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/bundle")
public class AdminBundleController {
	
	@Autowired
	BundleService bundleService;
	
	@Autowired
	ContentService contentService;
	/**
	 * 영상교육 번들 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/bundle/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 영상교육 번들 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/bundle/form";
		
		BundleVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("bundleIdx"))) {
			vo = bundleService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 영상교육 콘텐츠 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "vodContentList")
	public String vodContentList(@RequestEgovMap EgovMap req, Model model) {
		
		String viewName = "admin/lms/vod/bundle/vodContentList";
		model.addAttribute("req", req);
		
		List<BundleVo> list = null;
		if(!StrUtil.isEmpty(req.get("vodIdx"))){
			list = bundleService.selectAll(req);
		}
		
		model.addAttribute("list", list);
		
		return viewName;
	}
	/**
	 * 영상교육 번들 목록 조회
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<BundleVo> list = null;
		if(!StrUtil.isEmpty(req.get("vodIdx"))){
			list = bundleService.selectAll(req);
		}
		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 번들 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BundleVo bundleVo = mapper.convertValue(req, BundleVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("bundleIdx"))) {
			bundleVo.setInsertIdx(StrUtil.getSessionIdx());
			bundleVo.setInsertIp(StrUtil.getClientIP());
			result = bundleService.insert(bundleVo);
		} else {
			bundleVo.setUpdateIdx(StrUtil.getSessionIdx());
			bundleVo.setUpdateIp(StrUtil.getClientIP());
			result = bundleService.update(bundleVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(bundleVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 영상교육 번들 삭제
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
		result = bundleService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 번들 순서 교체
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bundleChange")
	@ResponseBody
	public JsonResponse bundleChange(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BundleVo bundleVo = mapper.convertValue(req, BundleVo.class);

		long result = 0 ;
		
		if(!StrUtil.isEmpty(req.get("order[]"))){
			if (req.get("order[]").getClass().isArray()) {
				for(int i=0;i<((String[]) req.get("order[]")).length;i++){
					
					String str = ((String[])req.get("order[]"))[i];
					Integer number = Integer.valueOf(str);
					bundleVo.setBundleIdx(number);
					bundleVo.setContentOrder(i+1);
					 
					bundleVo.setUpdateIdx(StrUtil.getSessionIdx());
					bundleVo.setUpdateIp(StrUtil.getClientIP());
					result = bundleService.update(bundleVo);
				
				}
			}else {
				String str = (String)req.get("order[]");
				Integer number = Integer.valueOf(str);
				bundleVo.setBundleIdx(number);
				bundleVo.setContentOrder(1);
				 
				bundleVo.setUpdateIdx(StrUtil.getSessionIdx());
				bundleVo.setUpdateIp(StrUtil.getClientIP());
				result = bundleService.update(bundleVo);
			}
		}
		return new JsonResponse.Builder().data(model).build();
	}
}