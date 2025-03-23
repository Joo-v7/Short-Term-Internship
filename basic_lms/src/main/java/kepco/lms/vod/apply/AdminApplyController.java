package kepco.lms.vod.apply;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 영상교육 신청 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/apply")
public class AdminApplyController {
	
	@Autowired
	ApplyService applyService;
	
	@Autowired
	MemberService memberService;
	/**
	 * 영상교육 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/apply/list";
		
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 영상교육 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/apply/form";
		
		ApplyVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("applyIdx"))) {
			vo = applyService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	/**
	 * 내 영상교육 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "myList")
	public String myList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/apply/myList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 회원 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "memberList")
	public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/apply/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 신청자 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		req.put("scVodIdx",req.get("vodIdx"));
		
		List<ApplyVo> list = applyService.selectAll(req);
		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 신청
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ApplyVo applyVo = mapper.convertValue(req, ApplyVo.class);

		String detailState = null;
		int duplicateCnt = 0;
		long result = 0 ;
		
		//영상교육은 관리자 신청이 없음 아마?
		if(StrUtil.isEmpty(req.get("applyIdx"))) {
			
		}else {
			applyVo.setUpdateIdx(StrUtil.getSessionIdx());
			applyVo.setUpdateIp(StrUtil.getClientIP());
			result = applyService.update(applyVo);
		}
		
		model.addAttribute("detailState", detailState);
		model.addAttribute("duplicateCnt", duplicateCnt);
		model.addAttribute("resultCnt", result);
		model.addAttribute(applyVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 영상교육 신청 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		long result = 0 ;
		
		if (StrUtil.isEmpty(req.get("applyIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = applyService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}