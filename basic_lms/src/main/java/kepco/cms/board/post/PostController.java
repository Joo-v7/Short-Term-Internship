package kepco.cms.board.post;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kepco.cms.board.BoardCateVo;
import kepco.cms.board.BoardPermissionService;
import kepco.cms.board.BoardService;
import kepco.cms.board.BoardVo;
import kepco.cms.board.Criteria;
import kepco.cms.board.post.file.FileService;
import kepco.cms.board.post.file.FileVo;
import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.member.auth.MemberDetailVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.security.AuthenticationFacade;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.CamelMap;
import kepco.util.StrUtil;
/**
 * 게시판
 */
@Controller
@RequestMapping(value = "/cms/board/post")
public class PostController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardPermissionService boardPermissionService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	LocalFileService localFileService;
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	
	/**
	 * 게시물 목록
	 * @param req
	 * @param model
	 * @param cri
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		// 기본값 처리
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		req.put("pageNum", pageNum);
		req.put("scKey", StrUtil.toStr(req.get("scKey"), "title"));
		req.put("scWord", StrUtil.toStr(req.get("scWord"), ""));
		
		// 게시판 정보
		if(StrUtil.isBlank(req.get("boardId"))) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		BoardVo boardVo = boardService.select(req);
		if(boardVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionList(memberDetailVo, boardVo);
		
		if(!boardPermission.getBool("list")) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		// 게시판 설정
		int pageSize = StrUtil.toInt(req.get("pageSize"), boardVo.getBoardPageLines());
		req.put("pageSize", pageSize);
		String orderBy = boardVo.getBoardSortList();
		if(!StrUtil.isBlank(orderBy)) {
			req.put("orderBy", orderBy);
		}
		
		// 게시판 스킨
		String boardSkin = boardVo.getBoardSkin();
		String viewName = "cms/board/skin/"+boardSkin+"/list";
		
		// 조회 대상 게시판
		req.put("scBoardIdx", boardVo.getBoardIdx());
		// 원본글만 조회(답변글 제외)
//		req.put("scIsPostOriginal", true);
		// 게시물 목록
		Page<PostVo> list = (Page<PostVo>) postService.selectList(req);
		
		model.addAttribute("req", req);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("memberDetailVo", memberDetailVo);
		model.addAttribute("boardPermission", boardPermission);
		
		model.addAttribute("list", list);
		
		return viewName;
	}
	
	
	/**
	 * 메인화면 공지사항 목록
	 * @param req
	 * @param model
	 * @param cri
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "mainList")
	public String mainList(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "cms/board/main/list";
		
		req.put("main", "main");
		req.put("scBoardIdx", "3");
		
		List<PostVo> postList = null;
		postList = postService.selectAll(req);
		
		model.addAttribute("req",req);
		model.addAttribute("postList",postList);
		
		return viewName;
	}
	
	/**
	 * 게시물 조회
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		// 기본값 처리
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		req.put("pageNum", pageNum);
		req.put("scKey", StrUtil.toStr(req.get("scKey"), "title"));
		req.put("scWord", StrUtil.toStr(req.get("scWord"), ""));
		
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
		
		// 조회수 증가
		postService.updatePostHit(StrUtil.toInt(req.get("postIdx")));
		
		// 게시판 스킨
		String boardSkin = boardVo.getBoardSkin();
		String viewName = "cms/board/skin/"+boardSkin+"/view";
		
		// 첨부파일
		List<FileVo> fileVoList = fileService.selectListByPostIdx(StrUtil.toInt(req.get("postIdx")));
		
		// 댓글. 댓글은 AJAX로 조회
		
		model.addAttribute("req", req);
		model.addAttribute("postVo", postVo);
		model.addAttribute("fileVoList", fileVoList);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("boardPermission", boardPermission);
		model.addAttribute("memberDetailVo", memberDetailVo);
		
		return viewName;
	}
	
	/**
	 * 사용자 게시물 추가,수정,답변 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = {"form", "reply"})
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		// 추가, 수정, 답변 폼 체크
		String requestUri = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
		String cmd = "write";
		if(requestUri.matches(".*/reply/?$")) {
			cmd = "reply";
		}
		else if(!StrUtil.isBlank(req.get("postIdx"))) {
			cmd = "modify";
		}
		
		// 기본값 처리
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		req.put("pageNum", pageNum);
		req.put("scKey", StrUtil.toStr(req.get("scKey"), "title"));
		req.put("scWord", StrUtil.toStr(req.get("scWord"), ""));
		
		PostVo postVo = null;
		if(!StrUtil.isBlank(req.get("postIdx")))	{
			postVo = postService.select(req);
			
			if(postVo == null) {
				throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
			}
		}
		
		// 게시판 정보
		EgovMap myMap = new EgovMap();
		if(postVo == null) {
			myMap.put("boardId", req.get("boardId"));
		}
		else {
			myMap.put("boardIdx", postVo.getBoardIdx());
		}
		BoardVo boardVo = boardService.select(myMap);
		if(boardVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionForm(memberDetailVo, boardVo, postVo);
		
		if(!boardPermission.getBool(cmd)) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		// 게시판 스킨
		String boardSkin = boardVo.getBoardSkin();
		String viewName = "cms/board/skin/"+boardSkin+"/form";
		
		List<FileVo> fileVoList = new ArrayList<FileVo>();
		if("write".equals(cmd)) {
			postVo = new PostVo();
			postVo.setBoardId(boardVo.getBoardId());
			postVo.setPostName(memberDetailVo.getMemberName());
		}
		else if("modify".equals(cmd)) {
			// 첨부파일
			fileVoList = fileService.selectListByPostIdx(StrUtil.toInt(req.get("postIdx")));
		}
		else if("reply".equals(cmd)) {
			
			model.addAttribute("postOriginalVo", postVo);
			
			postVo = new PostVo();
			postVo.setBoardId(boardVo.getBoardId());
			postVo.setPostName(memberDetailVo.getMemberName());
			
		}
		else {
			// 사실 진입 불가
			throw new CmsCustomException(CmsStatusCode.INTERNAL_SERVER_ERROR, "처리할 수 없는 기능 입니다.");
		}
		
		req.put("cmd", cmd);
		
		model.addAttribute("req", req);
		model.addAttribute("postVo", postVo);
		model.addAttribute("fileVoList", fileVoList);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("boardPermission", boardPermission);
		model.addAttribute("memberDetailVo", memberDetailVo);
		
		return viewName;
	}
	

	/**
	 * 게시물 저장
	 * @param req
	 * @param model
	 * @param files
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile[] files, Model model) throws Exception {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		String cmd = StrUtil.toStr(req.get("cmd"), "write");
		int postIdx = StrUtil.toInt(req.get("postIdx"), 0);
		
		PostVo postVo = null;
		if(postIdx > 0) {
			postVo = postService.select(req);
			if(postVo == null) {
				throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
			}
		}
		
		// 게시판 정보
		EgovMap myMap = new EgovMap();
		if(postVo == null) {
			myMap.put("boardId", req.get("boardId"));
		}
		else {
			myMap.put("boardIdx", postVo.getBoardIdx());
		}
		BoardVo boardVo = boardService.select(myMap);
		
		if("reply".equals(cmd) && postIdx < 1) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "답변 등록을 위한 필수입력이 누락되었습니다.");
		}
		
		if(!"reply".equals(cmd)) {
			if(postIdx > 0)
				cmd = "modify";
			else
				cmd = "write";
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionForm(memberDetailVo, boardVo, postVo);
		
		if(!boardPermission.getBool(cmd)) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		if (StrUtil.isEmpty(req.get("postTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "제목은 필수입력 입니다.");
		}
		
		if (StrUtil.isEmpty(req.get("postContent"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "내용은 필수입력 입니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		PostVo dataPostVo = mapper.convertValue(req, PostVo.class);
		
		// 게시판ID를 변조하여 보낼 경우
		if(!dataPostVo.getBoardId().equals(boardVo.getBoardId())) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "인식할 수 없는 게시판ID 입니다.");
		}
		
		dataPostVo.setBoardIdx(boardVo.getBoardIdx());
		
		dataPostVo.setPostTitle(StrUtil.toStr(req.get("postTitle")));
		dataPostVo.setPostContent(StrUtil.toStr(req.get("postContent")));
		dataPostVo.setPostName(StrUtil.toStr(req.get("postName")));
		
		// 웹에디터 사용여부
		if("y".equals(boardVo.getBoardUseEditor())) {
			// 0:TEXT, 1:HTML
			dataPostVo.setPostHtml(1);
		}
		else {
			dataPostVo.setPostHtml(0);
		}
		
		// 공지 사용여부
		if("n".equals(boardVo.getBoardUsePriority())) {
			dataPostVo.setPostNotice(0);
			dataPostVo.setPostNoticeSdate(null);
			dataPostVo.setPostNoticeEdate(null);
		}
		
		// 비밀글 사용여부
		if(!"y".equals(boardVo.getBoardUseSecret())) {
			dataPostVo.setPostSecret("n");
		}
		
		
		// 쓰기
		if("write".equals(cmd)) {
			dataPostVo.setInsertIdx(StrUtil.getSessionIdx());
			dataPostVo.setInsertIp(StrUtil.getClientIP());
			
			if(StrUtil.isEmpty(dataPostVo.getPostDate())) {
				// TODO: 데이터 임의 변조, 날짜 포맷 체크
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String postDate = sdf.format(new Date());
				dataPostVo.setPostDate(postDate);
			}
			
			postService.insertWrite(dataPostVo);
		}
		else if("modify".equals(cmd)) {
			postVo.setUpdateIdx(StrUtil.getSessionIdx());
			postVo.setUpdateIp(StrUtil.getClientIP());
			postService.updateModify(dataPostVo);
		}
		// 답변
		else {
			dataPostVo.setPostGroupId(postVo.getPostGroupId());
			dataPostVo.setPostGroupOrder(postVo.getPostGroupOrder()+1);
			dataPostVo.setPostGroupDepth(postVo.getPostGroupDepth()+1);
			
			// 그룹내 최상위 게시물ID
			dataPostVo.setPostOriginalIdx(postVo.getPostOriginalIdx() > 0 ? postVo.getPostOriginalIdx() : postVo.getPostIdx());
			// 그룹내 부모 게시물ID
			dataPostVo.setPostParentIdx(postVo.getPostIdx());
			
			dataPostVo.setInsertIdx(StrUtil.getSessionIdx());
			dataPostVo.setInsertIp(StrUtil.getClientIP());
			
			postService.insertReply(dataPostVo);
		}
		
		// 파일 삭제
		if(!StrUtil.isEmpty(req.get("fileDel"))) {
			String[] fileDel;
			if(StrUtil.isArray(req.get("fileDel"))) {
				fileDel = (String[]) req.get("fileDel");
			}
			else {
				fileDel = new String[1];
				fileDel[0] = (String) req.get("fileDel");
			}
			for(int i = 0; i < fileDel.length; i++) {
				fileService.deleteFile(fileDel[i]);
			}
		}
		
		// 첨부파일(일반) 존재여부
		postService.updatePostFile(dataPostVo.getPostIdx());
		
		// 첨부파일(사진) 존재여부
		postService.updatePostImage(dataPostVo.getPostIdx());
		
		
		// 답변인 경우 원글 등록자에게 메일 발송
		if("reply".equals(cmd) && postVo.getPostReceiveEmail() == 1) {
			EgovMap egovMap;
			egovMap = new EgovMap();
			egovMap.put("tempCode1", "qna_reply");
			TemplateVo templateVo = templateService.select(egovMap);
			egovMap = new EgovMap();
			egovMap.put("memberIdx", postVo.getInsertIdx());
			MemberVo memberVo = memberService.select(egovMap);
			
			EmailVo emailVo = new EmailVo();
			emailVo.setTo(memberVo.getMemberEmail());
			emailVo.setSubject(templateVo.getTempTitle());
			emailVo.setText(templateVo.getTempContent());
			emailVo.setUseHtml(true);
			emailVo.setUseTemplate(true);
			
			Map<String, Object> map = Map.of(
					"memberName",memberVo.getMemberNm(), 
					"now", new Date(), 
					"postTitle", postVo.getPostTitle()
			); 
			emailVo.setTemplateMap(map);
			emailService.send(emailVo);
		}
		
		model.addAttribute("postIdx", dataPostVo.getPostIdx());
		model.addAttribute("boardIdx", dataPostVo.getBoardIdx());
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 게시물 파일 저장
	 * @param req
	 * @param model
	 * @param cri
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "fileSave")
	@ResponseBody
	public JsonResponse file_save(
			@RequestParam(value="postIdx", required=true) int postIdx, 
			@RequestParam(value = "uploadFile", required=true) List<MultipartFile> uploadFile, 
			Model model) throws Exception {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		EgovMap myMap;
		myMap = new EgovMap();
		myMap.put("postIdx", postIdx);
		PostVo postVo = postService.select(myMap);
		
		if( postVo == null ) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
		}
		
		// 게시판 정보
		myMap = new EgovMap();
		myMap.put("boardIdx", postVo.getBoardIdx());
		BoardVo boardVo = boardService.select(myMap);
		
		if( boardVo == null ) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionForm(memberDetailVo, boardVo, postVo);
		
		
				
		String savePath = basePath + "/board/"+boardVo.getBoardId();
			
		File folder = new File(savePath);
		
		if (!folder.exists()) {
			FileUtils.forceMkdir(folder);
		}
		
		for(int i = 0; i < uploadFile.size(); i++) {
			FileVo fileVo = new FileVo();
			
			fileVo.setPostIdx(postVo.getPostIdx());
			fileVo.setBoardIdx(postVo.getBoardIdx());
			fileVo.setBoardId(boardVo.getBoardId());
			
			String origFilename = uploadFile.get(i).getOriginalFilename();
			String fileBaseName = FilenameUtils.getBaseName(origFilename);
			String fileExtension = FilenameUtils.getExtension(origFilename);
			String fileSaveName = UUID.randomUUID().toString() + "." + fileExtension;
			String filePath = savePath + "/" + fileSaveName;
			
			fileVo.setFileFilesize(uploadFile.get(i).getSize());
			fileVo.setFileOriginName(origFilename);
			fileVo.setFileSaveName(fileSaveName);
			// 기본값으로 동일하게 사용하고 PK로 정렬
			fileVo.setFileSort(10);
			fileVo.setFileOriginName(origFilename);
			
			if(uploadFile.get(i).getContentType().startsWith("image") == false) {	 
				fileVo.setFileIsImage(0);
			}
			else {
				fileVo.setFileIsImage(1);
			}
			
			fileVo.setFileType(fileExtension);
			
			fileVo.setInsertIp(StrUtil.getClientIP());
			
			uploadFile.get(i).transferTo(new File(filePath));
			
			int result = fileService.insert(fileVo);
			
			// 게시물 테이블 파일갯수 증가
			//fileService.fileadd(req);	
		} // for i
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 게시물 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		
		int postIdx = StrUtil.toInt(req.get("postIdx"), 0);
		
		if(postIdx == 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시물ID는 필수입력 입니다.");
		}
		EgovMap myMap;
		myMap = new EgovMap();
		myMap.put("postIdx", postIdx);
		PostVo postVo = postService.select(myMap);
		if(postVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
		}
		
		// 게시판 정보
		myMap = new EgovMap();
		myMap.put("boardIdx", postVo.getBoardIdx());
		BoardVo boardVo = boardService.select(myMap);
		if(boardVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시판정보를 조회할 수 없습니다.");
		}
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionForm(memberDetailVo, boardVo, postVo);
		
		// 작성자 본인 여부
		boolean isOwner = memberDetailVo.getMemberIdx() > 0 && postVo.getInsertIdx() == memberDetailVo.getMemberIdx();
				
		if(!boardPermission.getBool("isBoardAdmin") && !isOwner ) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		// 답변글이 있다면 삭제 불가
		int childCount = 0;
		childCount = postService.selectChildCount(postVo.getPostIdx());
		if(childCount > 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "답변글이 있어 삭제할 수 없습니다.");
		}
		
		// 댓글이 있다면 삭제 불가
		int commentCount = 0;
		commentCount = postService.selectCommentCount(postVo.getPostIdx());
		if(commentCount > 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "댓글이 있어 삭제할 수 없습니다.");
		}
		
		int resultCnt = 0;
		myMap = new EgovMap();
		req.put("postIdx", postVo.getPostIdx());
		req.put("deleteIdx", memberDetailVo.getMemberIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		
		// 첨부파일 삭제
		resultCnt = fileService.deleteListByPostIdx(req);
		
		// 게시물 삭제
		resultCnt = postService.delete(req);
		
		model.addAttribute("resultCnt", resultCnt);
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 게시물 첨부파일 다운로드
	 * @param req
	 * @param request
	 * @param response
	 * @return 
	 * @throws Throwable
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	protected ResponseEntity<Resource> download(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		MemberDetailVo memberDetailVo = authenticationFacade.getMemberDetailVo();
		int postIdx = StrUtil.toInt(req.get("postIdx"), 0);
		int fileIdx = StrUtil.toInt(req.get("fileIdx"), 0);
		
		if(postIdx == 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시물ID는 필수입력 입니다.");
		}
		if(fileIdx == 0) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "첨부파일ID는 필수입력 입니다.");
		}
		
		PostVo postVo = postService.select(req);
		if(postVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "게시물정보를 조회할 수 없습니다.");
		}
		
		// 게시판 정보
		EgovMap myMap = new EgovMap();
		myMap.put("boardIdx", postVo.getBoardIdx());
		BoardVo boardVo = boardService.select(myMap);
		
		// 게시판에 대한 권한 조회
		CamelMap boardPermission = boardPermissionService.getBoardPermissionView(memberDetailVo, boardVo, postVo);
		
		if(!boardPermission.getBool("download")) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "권한이 없습니다.");
		}
		
		String savePath = basePath + "/board/"+boardVo.getBoardId();
		
		FileVo fileVo = fileService.selectByFileIdx(fileIdx);
		if(fileVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "파일정보를 조회할 수 없습니다.");
		}
		
		if(!"n".equals(fileVo.getDeleteYn())) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "첨부파일을 찾을 수 없습니다.");
		}
		
		return localFileService.download(savePath + "/" + fileVo.getFileSaveName(), fileVo.getFileOriginName());
	}

}