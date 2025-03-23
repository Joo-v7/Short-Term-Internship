package kepco.cms.board;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 관리자 게시판
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/board")
public class AdminBoardController {
	
	@Autowired
	SiteService siteService;

	@Autowired
	BoardService boardService;
	
	/**
	 * 게시판 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/board/list";
		
		model.addAttribute("req",req);
		
		return viewName;
	}
	
	/**
	 * 게시판 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/board/form";
		
		BoardVo boardVo = null;
		
		if(!StrUtil.isEmpty(req.get("boardIdx")))	{
			boardVo = boardService.select(req);
		}
		
		List<SiteVo> siteList =  siteService.selectAll(req);
		
		model.addAttribute("req",req);
		model.addAttribute("vo",boardVo);
		model.addAttribute("siteList",siteList);
		
		return viewName;
	}
	
	/**
	 * 게시판 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<BoardVo> list = boardService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 게시판 저장
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BoardVo boardVo = mapper.convertValue(req, BoardVo.class);

		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("boardSearch")))			{ boardVo.setBoardSearch("n"); }
		if(StrUtil.isEmpty(req.get("boardUseComment")))		{ boardVo.setBoardUseComment("n"); }
		if(StrUtil.isEmpty(req.get("boardUseReply")))		{ boardVo.setBoardUseReply("n"); }
		if(StrUtil.isEmpty(req.get("boardUseSecret")))		{ boardVo.setBoardUseSecret("n"); }
		if(StrUtil.isEmpty(req.get("boardUseCate")))		{ boardVo.setBoardUseCate("n"); }
		if(StrUtil.isEmpty(req.get("boardUseRss")))			{ boardVo.setBoardUseRss("n"); }
		if(StrUtil.isEmpty(req.get("boardUseEditor")))		{ boardVo.setBoardUseEditor("n"); }
		if(StrUtil.isEmpty(req.get("boardUseUpload")))		{ boardVo.setBoardUseUpload("n"); }
		if(StrUtil.isEmpty(req.get("boardUseFileInfo")))	{ boardVo.setBoardUseFileInfo("n"); }
		if(StrUtil.isEmpty(req.get("boardUseThumb")))		{ boardVo.setBoardUseThumb("n"); }
		if(StrUtil.isEmpty(req.get("boardUseName")))		{ boardVo.setBoardUseName("n"); }
		if(StrUtil.isEmpty(req.get("boardUseViewCount")))	{ boardVo.setBoardUseViewCount("n"); }
		if(StrUtil.isEmpty(req.get("boardUseInsertDate")))	{ boardVo.setBoardUseInsertDate("n"); }
		if(StrUtil.isEmpty(req.get("boardUseEmail")))		{ boardVo.setBoardUseEmail("n"); }
		if(StrUtil.isEmpty(req.get("boardUseLike")))		{ boardVo.setBoardUseLike("n"); }
		if(StrUtil.isEmpty(req.get("boardUseLink")))		{ boardVo.setBoardUseLink("n"); }
		if(StrUtil.isEmpty(req.get("boardUseNickname")))	{ boardVo.setBoardUseNickname("n"); }
		if(StrUtil.isEmpty(req.get("boardUseTel")))			{ boardVo.setBoardUseTel("n"); }
		if(StrUtil.isEmpty(req.get("boardUseAddress")))		{ boardVo.setBoardUseAddress("n"); }
		if(StrUtil.isEmpty(req.get("boardUseCaptcha")))		{ boardVo.setBoardUseCaptcha("n"); }
		if(StrUtil.isEmpty(req.get("boardUseDownload")))	{ boardVo.setBoardUseDownload("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthListUser")))	{ boardVo.setBoardAuthListUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthReadUser")))	{ boardVo.setBoardAuthReadUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthWriteUser")))	{ boardVo.setBoardAuthWriteUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthReplyUser")))	{ boardVo.setBoardAuthReplyUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthCommentUser")))	{ boardVo.setBoardAuthCommentUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthUploadUser")))	{ boardVo.setBoardAuthUploadUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthDownloadUser")))	{ boardVo.setBoardAuthDownloadUser("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthListAdmin")))	{ boardVo.setBoardAuthListAdmin("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthReadAdmin")))	{ boardVo.setBoardAuthReadAdmin("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthWriteAdmin")))	{ boardVo.setBoardAuthWriteAdmin("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthReplyAdmin")))	{ boardVo.setBoardAuthReplyAdmin("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthCommentAdmin")))	{ boardVo.setBoardAuthCommentAdmin("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthUploadAdmin")))	{ boardVo.setBoardAuthUploadAdmin("n"); }
		if(StrUtil.isEmpty(req.get("boardAuthDownloadAdmin")))	{ boardVo.setBoardAuthDownloadAdmin("n"); }
		
		if(StrUtil.isEmpty(req.get("boardIdx")))	{
			boardVo.setInsertIdx(StrUtil.getSessionIdx());
			boardVo.setInsertIp(StrUtil.getClientIP());
			result = boardService.insert(boardVo);
		}else {
			boardVo.setUpdateIdx(StrUtil.getSessionIdx());
			boardVo.setUpdateIp(StrUtil.getClientIP());
			result = boardService.update(boardVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(boardVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 게시판 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("boardIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = boardService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().data(model).build();
	}

}
