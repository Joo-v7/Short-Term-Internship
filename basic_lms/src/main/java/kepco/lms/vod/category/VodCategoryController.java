package kepco.lms.vod.category;
/**
 * 훈련 분야 관리
 */
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;

@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/category")
public class VodCategoryController {

	@Autowired
	VodCategoryService vodCategoryService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 영상교육 구분 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/category/list";

		model.addAttribute("req", req);

		return viewName;
	}
	
	/**
	 * 영상교육 구분 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/category/form";
		VodCategoryVo vo = null;

		if (!StrUtil.isEmpty(req.get("vodCategoryIdx"))) {
			vo = vodCategoryService.select(req);
		}
		model.addAttribute("req", req);
		model.addAttribute("vo", vo);

		return viewName;
	}

	/**
	 * 영상교육 구분 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);

		List<VodCategoryVo> list = vodCategoryService.selectAll(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 영상교육 구분 저장
	 * @param req
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required = false) MultipartFile file,
			Model model) {
		
		if (StrUtil.isBlank(req.get("categoryNm"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "구분명은 필수입력 입니다.");
		}
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,	false);
		VodCategoryVo categoryVo = mapper.convertValue(req, VodCategoryVo.class);
		
		if(StrUtil.isEmpty(req.get("vodCategoryIdx"))) {
			List<VodCategoryVo> categoryList = vodCategoryService.selectAll(req);
			int categoryExist = categoryList.size();
			
			if(categoryExist > 0) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "중복된 카테고리명이 있습니다.");
			}
		}
		if(!"y".equals(categoryVo.getUseYn())) {
			categoryVo.setUseYn("n");
		}
		
		
		if (StrUtil.isEmpty(req.get("vodCategoryIdx"))) {
			categoryVo.setInsertIdx(StrUtil.getSessionIdx());
			categoryVo.setInsertIp(StrUtil.getClientIP());
			vodCategoryService.insert(categoryVo);
		} else {
			categoryVo.setUpdateIdx(StrUtil.getSessionIdx());
			categoryVo.setUpdateIp(StrUtil.getClientIP());
			vodCategoryService.update(categoryVo);
		}
		model.addAttribute(categoryVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 영상교육 구분 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		if (StrUtil.isEmpty(req.get("vodCategoryIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}

		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		vodCategoryService.delete(req);

		return new JsonResponse.Builder().model(model).build();
	}


}