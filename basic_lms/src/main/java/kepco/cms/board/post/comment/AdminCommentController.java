package kepco.cms.board.post.comment;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.board.BoardService;
import kepco.cms.board.BoardVo;
import kepco.cms.board.post.PostService;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 댓글 
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/board/post/comment")
public class AdminCommentController {
	
	@Autowired
	SiteService siteService;

	@Autowired
	BoardService boardService;

	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;
	
	/**
	 * 관리자 댓글 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/board/post/comment/list";
		
		List<SiteVo> siteList = siteService.selectAll(req);
		model.addAttribute("siteList", siteList);
		
		List<BoardVo> boardList = boardService.selectAll(req);
		model.addAttribute("boardList", boardList);
		
		model.addAttribute("req",req);
		
		return viewName;
	}
	
	/**
	 * 관리자 댓글 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/board/post/comment/form";
		
		CommentVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("commentIdx")))	{
			vo = commentService.select(req);
		}
		
		List<SiteVo> siteList =  siteService.selectAll(req);
		List<BoardVo> boardList =  boardService.selectAll(req);
		
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		model.addAttribute("siteList",siteList);
		model.addAttribute("boardList",boardList);
		
		return viewName;
	}
	
	/**
	 * 관리자 댓글 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<CommentVo> list = commentService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 관리자 댓글 저장
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CommentVo commentVo = mapper.convertValue(req, CommentVo.class);
		
		int result = 0 ;
		
		
		
		if(StrUtil.isEmpty(req.get("commentIdx")))	{
			commentVo.setInsertIdx(StrUtil.getSessionIdx());
			commentVo.setInsertIp(StrUtil.getClientIP());
			result = commentService.insert(commentVo);
		}else {
			commentVo.setUpdateIdx(StrUtil.getSessionIdx());
			commentVo.setUpdateIp(StrUtil.getClientIP());
			result = commentService.update(commentVo);
		}
		model.addAttribute("resultCnt", result);
		model.addAttribute(commentVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 관리자 댓글 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("commentIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "인덱스가 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = commentService.delete(req);
		model.addAttribute("resultCnt", result);

		return new JsonResponse.Builder().data(model).build();
	}
	

}
