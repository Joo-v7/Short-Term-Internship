package kepco.cms.board.post.comment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.board.BoardPermissionService;
import kepco.cms.board.BoardService;
import kepco.cms.board.BoardVo;
import kepco.cms.board.post.PostService;
import kepco.cms.board.post.PostVo;
import kepco.cms.member.auth.MemberDetailVo;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.security.AuthenticationFacade;
import kepco.common.web.RequestEgovMap;
import kepco.util.CamelMap;
import kepco.util.StrUtil;
/**
 * 댓글
 */
@Controller
@RequestMapping(value = "/cms/board/post/comment")
public class CommentController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardPermissionService boardPermissionService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	/**
	 * 댓글 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		if(StrUtil.isBlank(req.get("commentName"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성자는 필수입력 입니다.");
		}
		if(StrUtil.isBlank(req.get("commentContent"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "내용은 필수입력 입니다.");
		}
		
		if(StrUtil.isBlank(req.get("postIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시물ID는 필수입력 입니다.");
		}
		
		PostVo postVo = postService.select(req);
		if(postVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
		}
		
		// 게시판 정보
		EgovMap myMap = new EgovMap();
		myMap.put("boardIdx", postVo.getBoardIdx());
		BoardVo boardVo = boardService.select(myMap);
		if(boardVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionView(memberDetailVo, boardVo, postVo);
		
		if(!boardPermission.getBool("comment")) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		CommentVo commentVo;
		
		// 본인, 관리자 외 수정불가
		if(!StrUtil.isBlank(req.get("commentIdx"))) {
			commentVo = commentService.select(req);
			
			if(commentVo.getInsertIdx() != memberDetailVo.getMemberIdx() && !boardPermission.getBool("isBoardAdmin")) {
				throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
			}
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		commentVo = mapper.convertValue(req, CommentVo.class);
		
		int resultCnt = 0;
		if(StrUtil.isBlank(req.get("commentIdx"))) {
			commentVo.setBoardIdx(postVo.getBoardIdx());
			commentVo.setInsertIdx(memberDetailVo.getMemberIdx());
			commentVo.setInsertIp(StrUtil.getClientIP());
			resultCnt = commentService.insert(commentVo);
		}else {
			commentVo.setBoardIdx(postVo.getBoardIdx());
			commentVo.setUpdateIdx(memberDetailVo.getMemberIdx());
			commentVo.setUpdateIp(StrUtil.getClientIP());
			resultCnt = commentService.update(commentVo);
		}
		postService.updatePostCommentCount(postVo.getPostIdx());
		
		model.addAttribute("resultCnt", resultCnt);
		model.addAttribute("commentIdx", commentVo.getCommentIdx());
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 댓글 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		if(StrUtil.isBlank(req.get("postIdx")))	{
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시물ID는 필수입력 입니다.");
		}
		
		PostVo postVo = postService.select(req);
		if(postVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
		}
		
		// 게시판 정보
		EgovMap myMap = new EgovMap();
		myMap.put("boardIdx", postVo.getBoardIdx());
		BoardVo boardVo = boardService.select(myMap);
		if(boardVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionView(memberDetailVo, boardVo, postVo);
		
		if(!boardPermission.getBool("read")) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		
		// 게시판 스킨
		String boardSkin = boardVo.getBoardSkin();
		String viewName = "cms/board/skin/"+boardSkin+"/commentList";
		
		model.addAttribute("req", req);
		
		model.addAttribute("memberDetailVo", memberDetailVo);
		model.addAttribute("boardPermission", boardPermission);
		
		List<CommentVo> list = commentService.selectAll(req);
		model.addAttribute("list", list);
		
		return viewName;
	}
	
	/**
	 * 댓글 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		if(StrUtil.isBlank(req.get("postIdx")))	{
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시물ID는 필수입력 입니다.");
		}
		
		PostVo postVo = postService.select(req);
		if(postVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
		}
		
		// 게시판 정보
		EgovMap myMap = new EgovMap();
		myMap.put("boardIdx", postVo.getBoardIdx());
		BoardVo boardVo = boardService.select(myMap);
		if(boardVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionView(memberDetailVo, boardVo, postVo);
		
		if(!boardPermission.getBool("comment")) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		// 게시판 스킨
		String boardSkin = boardVo.getBoardSkin();
		String viewName = "cms/board/skin/"+boardSkin+"/commentForm";
		
		CommentVo commentVo;
		
		// 추가
		if(StrUtil.isBlank(req.get("commentIdx"))) {
			commentVo = new CommentVo();
			commentVo.setPostIdx(postVo.getPostIdx());
			commentVo.setBoardIdx(postVo.getBoardIdx());
			commentVo.setCommentName(memberDetailVo.getMemberName());
		}
		// 수정
		else {
			commentVo = commentService.select(req);
			
			if(commentVo.getInsertIdx() != memberDetailVo.getMemberIdx()) {
				throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
			}
		}
		
		model.addAttribute("memberDetailVo", memberDetailVo);
		model.addAttribute("boardPermission", boardPermission);
		
		model.addAttribute("vo", commentVo);
		
		return viewName;
	}
	
	/**
	 * 댓글 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("commentIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = commentService.delete(req);
		
		commentService.commentdelete(req);	
		
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
}