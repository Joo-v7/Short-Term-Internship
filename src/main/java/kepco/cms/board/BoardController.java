package kepco.cms.board;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import kepco.cms.board.post.PostService;
import kepco.cms.board.post.PostVo;
import kepco.cms.board.post.file.FileService;
import kepco.cms.board.post.file.FileVo;
import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 게시판
 */
@Controller
@RequestMapping(value = "/cms/board")
public class BoardController {
	
	@Autowired
	SiteService siteService;

	@Autowired
	BoardService boardService;
	
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
	
	private final static String FILE_PATH = "board/post/file"; 

	private Criteria cri;
	
	/**
	 * 게시판 목록
	 * @param req
	 * @param model
	 * @param cri
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "cms/board/skin/basic/list";
		
		BoardVo boardVo = null;
		
		HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();;
		HttpSession session = http.getSession(true);
		String memberRole = StrUtil.toStr(session.getAttribute("memberRoleDetail"));
	    boolean writeYn = false;
	    
		boardVo = boardService.selectUser(req);
		
		switch (memberRole) {
	        case "USER":
	        	if("y".equals(boardVo.getBoardAuthWriteUser())) {
	        		writeYn = true;
	        	}
	            break;
	        case "ADMIN_CMS":
	        	if("y".equals(boardVo.getBoardAuthWriteCmsAdmin())) {
	        		writeYn = true;
	        	}
	            break;
	        case "ADMIN_LMS":
	        	if("y".equals(boardVo.getBoardAuthWriteLmsAdmin())) {
	        		writeYn = true;
	        	}
	            break;
	        case "ADMIN_RAS":
	        	if("y".equals(boardVo.getBoardAuthWriteRasAdmin())) {
	        		writeYn = true;
	        	}
	            break;
	        case "ADMIN_SUPER":
	        	writeYn = true;
	            break;
	        default:
	        	if("y".equals(boardVo.getBoardAuthWriteNonMember())) {
	        		writeYn = true;
	        	}
	            break;
	    }
		
		req.put("scBoardIdx", boardVo.getBoardIdx());
		req.put("boardIdx", boardVo.getBoardIdx());
		
		List<BoardCateVo> boardCateVo = null;
		
		if(boardVo.getBoardUseCate().equals("y")) {
			boardCateVo = boardService.selectCateUser(req);
		}
		
		
		if(StrUtil.isEmpty(boardVo.getBoardSkin())){
			viewName = "cms/board/skin/basic/list";
		}else {
	        //스킨경로에 파일이 있는지 체크
	        final File driverFile = new File("src/main/webapp/cms/board/skin/"+boardVo.getBoardSkin()+"/list.html");
	        final String driverFilePath = driverFile.getAbsolutePath();
	 
	        if(!driverFile.exists()) {
	        	viewName = "cms/board/skin/basic/list";
	        }else {
	        	viewName = "cms/board/skin/"+boardVo.getBoardSkin()+"/list";
	        }
		}
		
		
		PostVo PostTotal = null;
		
		//
		PostTotal = postService.selectTotal(req);
		
		List<PostVo> PostVo = null;
		PostVo = postService.selectList(req);
		

	    if(StrUtil.isEmpty(req.get("offset"))){
	    	req.put("offset", 0);
	    }
	    
	    if(StrUtil.isEmpty(req.get("pageNum"))){
	    	req.put("pageNum", "1");
	    }
	 
	    
	    if(StrUtil.isEmpty(req.get("scKey"))){
	    	req.put("scKey", "subject");
	    }
	    
	    if(StrUtil.isEmpty(req.get("scWord"))){
	    	req.put("scWord", "");
	    }
	    
	    if(StrUtil.isEmpty(req.get("cate"))){
	    	req.put("cate", "");
	    }
	    
		List<SiteVo> siteList =  siteService.selectAll(req);
		
		
		  // 전체 글 개수
        int boardListCnt = PostTotal.getPostTotal();
        int pageNum = Integer.parseInt((String) req.get("pageNum"));
        
        // 페이징 객체
        Paging paging = new Paging();
        paging.setCri(cri);
        paging.setTotalCount(boardListCnt, pageNum);
          
        
        model.addAttribute("paging", paging);   
        
		
		model.addAttribute("req",req);
		model.addAttribute("vo",boardVo);
		
	
		model.addAttribute("total",PostTotal);
		model.addAttribute("list",PostVo);
		
		String memberIdx = StrUtil.toStr(session.getAttribute("memberIdx"));
		String memberName = StrUtil.toStr(session.getAttribute("memberName"));
		
		model.addAttribute("siteList",siteList);
		model.addAttribute("memberIdx",memberIdx);
		model.addAttribute("memberName",memberName);
		model.addAttribute("catevo",boardCateVo);
		model.addAttribute("writeYn", writeYn);
		
		
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
	 * 사용자 게시물 조회
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "cms/board/skin/basic/view";
		PostVo vo = null;
		
		int cnt = 0 ;
		List<FileVo> fileVo = null;
		
		// TODO: 왜 이런식으로 처리하는거지?
		if(!StrUtil.isEmpty(req.get("postIdx")))	{
			//조회수 증가
			cnt = postService.cntadd(req);	
			vo = postService.select(req);
			fileVo = fileService.selectFile(req);
		}
		
		// TODO: 용도가 뭐지?
		List<SiteVo> siteList =  siteService.selectAll(req);
		
		// TODO: 이걸 왜 받지?
		if(StrUtil.isEmpty(req.get("offset"))){
	    	req.put("offset", 0);
	    }
	    
	    if(StrUtil.isEmpty(req.get("pageNum"))){
	    	req.put("pageNum", "1");
	    }
	 
	    
	    if(StrUtil.isEmpty(req.get("scKey"))){
	    	req.put("scKey", "subject");
	    }
	    
	    if(StrUtil.isEmpty(req.get("scWord"))){
	    	req.put("scWord", "");
	    }
	    
	    HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();;
		HttpSession session = http.getSession(true);
		
		String memberIdx = StrUtil.toStr(session.getAttribute("memberIdx"));
		String memberName = StrUtil.toStr(session.getAttribute("memberName"));
		String memberRole = StrUtil.toStr(session.getAttribute("memberRoleDetail"));
		
		BoardVo boardVo = null;
		// TODO: PostVo에 boardId가 없다?
		req.put("boardId", vo.getBoardId());
		// TODO: 파라미터 값을 신뢰하고 있다.
		// TODO: 메서드명이 생소한데?
		boardVo = boardService.selectUser(req);
		
	    boolean readYn = false;
	    boolean writeYn = false;
	    boolean uploadYn = false;
	    boolean downloadYn = false;
	    boolean commentYn = false;
	    boolean replyYn = false;
	    
	    String[] permissions = new String[6];

	    switch (memberRole) {
	        case "USER":
	            permissions[0] = boardVo.getBoardAuthReadUser();
	            permissions[1] = boardVo.getBoardAuthWriteUser();
	            permissions[2] = boardVo.getBoardAuthUploadUser();
	            permissions[3] = boardVo.getBoardAuthDownloadUser();
	            permissions[4] = boardVo.getBoardAuthCommentUser();
	            permissions[5] = boardVo.getBoardAuthReplyUser();
	            break;
	        case "ADMIN_CMS":
	            permissions[0] = boardVo.getBoardAuthReadCmsAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteCmsAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadCmsAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadCmsAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentCmsAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyCmsAdmin();
	            break;
	        case "ADMIN_LMS":
	            permissions[0] = boardVo.getBoardAuthReadLmsAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteLmsAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadLmsAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadLmsAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentLmsAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyLmsAdmin();
	            break;
	        case "ADMIN_RAS":
	            permissions[0] = boardVo.getBoardAuthReadRasAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteRasAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadRasAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadRasAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentRasAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyRasAdmin();
	            break;
	        case "ADMIN_SUPER":
	            readYn = writeYn = uploadYn = downloadYn = commentYn = replyYn = true;
	            break;
	        default:
	            permissions[0] = boardVo.getBoardAuthReadNonMember();
	            permissions[1] = boardVo.getBoardAuthWriteNonMember();
	            permissions[2] = boardVo.getBoardAuthUploadNonMember();
	            permissions[3] = boardVo.getBoardAuthDownloadNonMember();
	            permissions[4] = boardVo.getBoardAuthCommentNonMember();
	            permissions[5] = boardVo.getBoardAuthReplyNonMember();
	            break;
	    }

	    if (memberRole != "ADMIN_SUPER") {
	        readYn = "y".equals(permissions[0]);
	        writeYn = "y".equals(permissions[1]);
	        uploadYn = "y".equals(permissions[2]);
	        downloadYn = "y".equals(permissions[3]);
	        commentYn = "y".equals(permissions[4]);
	        replyYn = "y".equals(permissions[5]);
	    }
	    
		if(!readYn) {
			throw new CmsCustomException(CmsStatusCode.FORBIDDEN, "읽을 권한이 없습니다.");
		}
		
		if(StrUtil.isEmpty(boardVo.getBoardSkin())){
			viewName = "cms/board/skin/basic/view";
		}else {
	        //스킨경로에 파일이 있는지 체크
	        final File driverFile = new File("src/main/webapp/cms/board/skin/"+boardVo.getBoardSkin()+"/view.html");
	        final String driverFilePath = driverFile.getAbsolutePath();
	 
	        if(!driverFile.exists()) {
	        	viewName = "cms/board/skin/basic/view";
	        }else {
	        	viewName = "cms/board/skin/"+boardVo.getBoardSkin()+"/view";
	        }
		}
	 
		
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		model.addAttribute("fileVo",fileVo);
		model.addAttribute("boardVo",boardVo);
		model.addAttribute("siteList",siteList);
		model.addAttribute("memberIdx",memberIdx);
		model.addAttribute("memberName",memberName);
		model.addAttribute("writeYn", writeYn);
		model.addAttribute("uploadYn", uploadYn);
		model.addAttribute("downloadYn", downloadYn);
		model.addAttribute("commentYn", commentYn);
		model.addAttribute("replyYn", replyYn);
		
		return viewName;
	}
	
	/**
	 * 사용자 게시물 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "cms/board/skin/basic/form";
		
		HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();;
		HttpSession session = http.getSession(true);
		
		String memberIdx = StrUtil.toStr(session.getAttribute("memberIdx"));
		String memberName = StrUtil.toStr(session.getAttribute("memberName"));
		String memberRole = StrUtil.toStr(session.getAttribute("memberRole"));
		String memberRoleDetail = StrUtil.toStr(session.getAttribute("memberRoleDetail"));
		BoardVo boardVo = null;
		//게시판 id로 게시판idx값 찾기
		boardVo = boardService.selectUser(req);
	    
		boolean readYn = false;
	    boolean writeYn = false;
	    boolean uploadYn = false;
	    boolean downloadYn = false;
	    boolean commentYn = false;
	    boolean replyYn = false;
	    
	    String[] permissions = new String[6];

	    switch (memberRoleDetail) {
	        case "USER":
	            permissions[0] = boardVo.getBoardAuthReadUser();
	            permissions[1] = boardVo.getBoardAuthWriteUser();
	            permissions[2] = boardVo.getBoardAuthUploadUser();
	            permissions[3] = boardVo.getBoardAuthDownloadUser();
	            permissions[4] = boardVo.getBoardAuthCommentUser();
	            permissions[5] = boardVo.getBoardAuthReplyUser();
	            break;
	        case "ADMIN_CMS":
	            permissions[0] = boardVo.getBoardAuthReadCmsAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteCmsAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadCmsAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadCmsAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentCmsAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyCmsAdmin();
	            break;
	        case "ADMIN_LMS":
	            permissions[0] = boardVo.getBoardAuthReadLmsAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteLmsAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadLmsAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadLmsAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentLmsAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyLmsAdmin();
	            break;
	        case "ADMIN_RAS":
	            permissions[0] = boardVo.getBoardAuthReadRasAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteRasAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadRasAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadRasAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentRasAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyRasAdmin();
	            break;
	        case "ADMIN_SUPER":
	            readYn = writeYn = uploadYn = downloadYn = commentYn = replyYn = true;
	            break;
	        default:
	            permissions[0] = boardVo.getBoardAuthReadNonMember();
	            permissions[1] = boardVo.getBoardAuthWriteNonMember();
	            permissions[2] = boardVo.getBoardAuthUploadNonMember();
	            permissions[3] = boardVo.getBoardAuthDownloadNonMember();
	            permissions[4] = boardVo.getBoardAuthCommentNonMember();
	            permissions[5] = boardVo.getBoardAuthReplyNonMember();
	            break;
	    }

	    if (memberRoleDetail != "ADMIN_SUPER") {
	        readYn = "y".equals(permissions[0]);
	        writeYn = "y".equals(permissions[1]);
	        uploadYn = "y".equals(permissions[2]);
	        downloadYn = "y".equals(permissions[3]);
	        commentYn = "y".equals(permissions[4]);
	        replyYn = "y".equals(permissions[5]);
	    }
	    
		if(!writeYn) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성 권한이 없습니다.");
		}
		
		req.put("boardIdx", boardVo.getBoardIdx());
		
		req.put("type", "form");
		
		//게시판 설정 가져오기
		if(!StrUtil.isEmpty(req.get("boardIdx")))	{
			boardVo = boardService.select(req);
		}
		
		req.put("boardIdx", boardVo.getBoardIdx());
		
		List<BoardCateVo> boardCateVo = null;
		
		if(boardVo.getBoardUseCate().equals("y")) {
			boardCateVo = boardService.selectCateUser(req);
		}
		
		
		if(StrUtil.isEmpty(boardVo.getBoardSkin())){
			viewName = "cms/board/skin/basic/form";
		}else {
	        //스킨경로에 파일이 있는지 체크
	        final File driverFile = new File("src/main/webapp/cms/board/skin/"+boardVo.getBoardSkin()+"/form.html");
	        final String driverFilePath = driverFile.getAbsolutePath();
	 
	        if(!driverFile.exists()) {
	        	viewName = "cms/board/skin/basic/form";
	        }else {
	        	viewName = "cms/board/skin/"+boardVo.getBoardSkin()+"/form";
	        }
		}
		
		List<SiteVo> siteList =  siteService.selectAll(req);
		
		PostVo vo = null;
		List<FileVo> fileVo = null;
		
		if(!StrUtil.isEmpty(req.get("postIdx")))	{
			vo = postService.select(req);
			fileVo = fileService.selectFile(req);
			
			int intValue1 = vo.getInsertIdx();
			String insertIdx = Integer.toString(intValue1);
			
			if(!insertIdx.equals(memberIdx) && !memberRole.equals("SUPER") && !memberRole.equals("ADMIN")) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성자가 다릅니다.");
			}
		}
		
		
		
		model.addAttribute("postvo",vo);
		model.addAttribute("filevo",fileVo);
		
		model.addAttribute("req",req);
		model.addAttribute("vo",boardVo);
		model.addAttribute("siteList",siteList);
		model.addAttribute("catevo",boardCateVo);
		
		
		model.addAttribute("memberIdx",memberIdx);
		model.addAttribute("memberName",memberName);
		model.addAttribute("writeYn", writeYn);
		model.addAttribute("uploadYn", uploadYn);
		model.addAttribute("downloadYn", downloadYn);
		model.addAttribute("commentYn", commentYn);
		model.addAttribute("replyYn", replyYn);
		
		return viewName;
	}
	
	/**
	 * 게시물 답글
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "reply")
	public String reply(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "cms/board/skin/basic/form";
		
		HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = http.getSession(true);
		
		String memberIdx = StrUtil.toStr(session.getAttribute("memberIdx"));
		String memberName = StrUtil.toStr(session.getAttribute("memberName"));
		String memberRoleDetail = StrUtil.toStr(session.getAttribute("memberRoleDetail"));
		
		BoardVo boardVo = null;
		
		//게시판 id로 게시판idx값 찾기
		boardVo = boardService.selectUser(req);
		req.put("boardIdx", boardVo.getBoardIdx());
		
		req.put("type", "reply");
	    
		boolean readYn = false;
	    boolean writeYn = false;
	    boolean uploadYn = false;
	    boolean downloadYn = false;
	    boolean commentYn = false;
	    boolean replyYn = false;
	    
	    String[] permissions = new String[6];

	    switch (memberRoleDetail) {
	        case "USER":
	            permissions[0] = boardVo.getBoardAuthReadUser();
	            permissions[1] = boardVo.getBoardAuthWriteUser();
	            permissions[2] = boardVo.getBoardAuthUploadUser();
	            permissions[3] = boardVo.getBoardAuthDownloadUser();
	            permissions[4] = boardVo.getBoardAuthCommentUser();
	            permissions[5] = boardVo.getBoardAuthReplyUser();
	            break;
	        case "ADMIN_CMS":
	            permissions[0] = boardVo.getBoardAuthReadCmsAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteCmsAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadCmsAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadCmsAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentCmsAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyCmsAdmin();
	            break;
	        case "ADMIN_LMS":
	            permissions[0] = boardVo.getBoardAuthReadLmsAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteLmsAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadLmsAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadLmsAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentLmsAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyLmsAdmin();
	            break;
	        case "ADMIN_RAS":
	            permissions[0] = boardVo.getBoardAuthReadRasAdmin();
	            permissions[1] = boardVo.getBoardAuthWriteRasAdmin();
	            permissions[2] = boardVo.getBoardAuthUploadRasAdmin();
	            permissions[3] = boardVo.getBoardAuthDownloadRasAdmin();
	            permissions[4] = boardVo.getBoardAuthCommentRasAdmin();
	            permissions[5] = boardVo.getBoardAuthReplyRasAdmin();
	            break;
	        case "ADMIN_SUPER":
	            readYn = writeYn = uploadYn = downloadYn = commentYn = replyYn = true;
	            break;
	        default:
	            permissions[0] = boardVo.getBoardAuthReadNonMember();
	            permissions[1] = boardVo.getBoardAuthWriteNonMember();
	            permissions[2] = boardVo.getBoardAuthUploadNonMember();
	            permissions[3] = boardVo.getBoardAuthDownloadNonMember();
	            permissions[4] = boardVo.getBoardAuthCommentNonMember();
	            permissions[5] = boardVo.getBoardAuthReplyNonMember();
	            break;
	    }

	    if (memberRoleDetail != "ADMIN_SUPER") {
	        readYn = "y".equals(permissions[0]);
	        writeYn = "y".equals(permissions[1]);
	        uploadYn = "y".equals(permissions[2]);
	        downloadYn = "y".equals(permissions[3]);
	        commentYn = "y".equals(permissions[4]);
	        replyYn = "y".equals(permissions[5]);
	    }
	    
		if(!writeYn) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성 권한이 없습니다.");
		}
		
		
		//게시판 설정 가져오기
		if(!StrUtil.isEmpty(req.get("boardIdx")))	{
			boardVo = boardService.select(req);
		}
		
		req.put("boardIdx", boardVo.getBoardIdx());
		
		List<BoardCateVo> boardCateVo = null;
		 
		if(boardVo.getBoardUseCate().equals("y")) {
			boardCateVo = boardService.selectCateUser(req);
		}
		
		
		if(StrUtil.isEmpty(boardVo.getBoardSkin())){
			viewName = "cms/board/skin/basic/form";
		}else {
	        //스킨경로에 파일이 있는지 체크
	        final File driverFile = new File("src/main/webapp/cms/board/skin/"+boardVo.getBoardSkin()+"/form.html");
	        final String driverFilePath = driverFile.getAbsolutePath();
	 
	        if(!driverFile.exists()) {
	        	viewName = "cms/board/skin/basic/form";
	        }else {
	        	viewName = "cms/board/skin/"+boardVo.getBoardSkin()+"/form";
	        }
		}
		
		List<SiteVo> siteList =  siteService.selectAll(req);
		
		PostVo vo = null;
		List<FileVo> fileVo = null;
		
		if(!StrUtil.isEmpty(req.get("postIdx")))	{
			vo = postService.select(req);
			fileVo = fileService.selectFile(req);
			
			int intValue1 = vo.getInsertIdx();
			String insertIdx = Integer.toString(intValue1);
		
		}
		
		
		
		model.addAttribute("postvo",vo);
		model.addAttribute("filevo",fileVo);
		
		model.addAttribute("req",req);
		model.addAttribute("vo",boardVo);
		model.addAttribute("siteList",siteList);
		model.addAttribute("catevo",boardCateVo);
		
		model.addAttribute("memberIdx",memberIdx);
		model.addAttribute("memberName",memberName);
		model.addAttribute("writeYn", writeYn);
		model.addAttribute("uploadYn", uploadYn);
		model.addAttribute("downloadYn", downloadYn);
		model.addAttribute("commentYn", commentYn);
		model.addAttribute("replyYn", replyYn);
		
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
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		PostVo postVo = mapper.convertValue(req, PostVo.class);
		
		int result = 0 ;
		
		HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();;
		HttpSession session = http.getSession(true);
		PostVo vo = null;
		
		if (StrUtil.isEmpty(req.get("boardIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시판이 누락되었습니다.");
		}
		
		BoardVo boardVo = null;
		
		//게시판 id로 게시판idx값 찾기
		boardVo = boardService.selectUser(req);
		
//		if(boardVo.getBoardUseCate().equals("y")) {
//			if (StrUtil.isEmpty(req.get("postCategory"))) {
//				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "카테고리는 필수입력 입니다.");
//			}
//		}
		
		if (StrUtil.isEmpty(req.get("postTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "제목은 필수입력 입니다.");
		}
		
		
		if (StrUtil.isEmpty(req.get("postContent"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "내용은 필수입력 입니다.");
		}
		
		boardVo = boardService.selectUser(req);

		String memberIdx = StrUtil.toStr(session.getAttribute("memberIdx"));
		String memberRole = StrUtil.toStr(session.getAttribute("memberRole"));
		String memberName = StrUtil.toStr(session.getAttribute("memberName"));
		String memberRoleDetail = StrUtil.toStr(session.getAttribute("memberRoleDetail"));
		
	    boolean writeYn = false;
	    
	    String[] permissions = new String[1];

	    switch (memberRoleDetail) {
	        case "USER":
	            permissions[0] = boardVo.getBoardAuthWriteUser();
	            break;
	        case "ADMIN_CMS":
	            permissions[0] = boardVo.getBoardAuthWriteCmsAdmin();
	            break;
	        case "ADMIN_LMS":
	            permissions[0] = boardVo.getBoardAuthWriteLmsAdmin();
	            break;
	        case "ADMIN_RAS":
	            permissions[0] = boardVo.getBoardAuthWriteRasAdmin();
	            break;
	        case "ADMIN_SUPER":
	            writeYn = true;
	            break;
	        default:
	            permissions[0] = boardVo.getBoardAuthWriteNonMember();
	            break;
	    }

	    if (memberRoleDetail != "ADMIN_SUPER") {
	        writeYn = "y".equals(permissions[0]);
	    }
	    
		if(!writeYn) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성 권한이 없습니다.");
		}
		
		if(StrUtil.isEmpty(req.get("postName"))){
			postVo.setPostName("비회원"); 
		}else {
			postVo.setPostName(memberName);
		}
		if(StrUtil.isEmpty(req.get("postSecret")))	{ postVo.setPostSecret("n"); }
		if(StrUtil.isEmpty(req.get("postNotice")))	{ postVo.setPostNotice(0); }
		if(StrUtil.isEmpty(req.get("postHtml")))	{ postVo.setPostHtml(0); }
		
		if(StrUtil.isEmpty(req.get("postNoticeSdate")))	{ postVo.setPostNoticeSdate("0000-00-00"); }
		if(StrUtil.isEmpty(req.get("postNoticeEdate")))	{ postVo.setPostNoticeEdate("0000-00-00"); }
		
		 LocalDate now = LocalDate.now();
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 
        // 포맷 적용
        String formatedNow = now.format(formatter);
        
        if(StrUtil.isEmpty(req.get("postDatetime")))	{ postVo.setPostDatetime(formatedNow); }
        
        if(!StrUtil.isEmpty(req.get("type"))){
	        if(req.get("type").equals("reply")) {
	        	postVo.setInsertIdx(StrUtil.getSessionIdx());
    			postVo.setInsertIp(StrUtil.getClientIP());
    			result = postService.insert(postVo);
    			
	        	//답글등록시 부모글 작성자에게 email 알림설정
    			EmailVo emailVo = mapper.convertValue(req, EmailVo.class);
    			EgovMap egovMap = new EgovMap();
    			TemplateVo templateVo = new TemplateVo();
	        	egovMap.put("tempIdx", "5");
				templateVo = templateService.select(egovMap);
				egovMap.put("postIdx", postVo.getPostParentIdx()); //부모글 선택
				PostVo parentPostVo = postService.select(egovMap);
	        	egovMap.put("memberIdx", parentPostVo.getInsertIdx()); // 부모글의 작성자 선택
	        	MemberVo memberVo = memberService.select(egovMap);
				emailVo.setTo(memberVo.getMemberEmail());
//				emailVo.setFrom("초실감훈련센터");//email 바꾸기 필요
				emailVo.setSubject(templateVo.getTempTitle());
				emailVo.setText(templateVo.getTempContent());
				emailVo.setUseHtml(true);
				emailVo.setUseTemplate(true);
				
				Map<String, Object> map = Map.of("memberName",memberVo.getMemberNm(), "now", new Date(), "postTitle", parentPostVo.getPostTitle()); 
				emailVo.setTemplateMap(map);
				emailService.send(emailVo);
	        }
        }else {
        	if(StrUtil.isEmpty(req.get("postIdx")))	{
    			postVo.setInsertIdx(StrUtil.getSessionIdx());
    			postVo.setInsertIp(StrUtil.getClientIP());
    			result = postService.insert(postVo);
    		}else {
    			
    			vo = postService.select(req);
    			int intValue1 = vo.getInsertIdx();
    			String insertIdx = Integer.toString(intValue1);
    			
    			if(!insertIdx.equals(memberIdx) && !memberRole.equals("SUPER") && !memberRole.equals("ADMIN")) {
    				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성자가 다릅니다.");
    			}
    			
    			postVo.setUpdateIdx(StrUtil.getSessionIdx());
    			postVo.setUpdateIp(StrUtil.getClientIP());
    			
    			result = postService.update(postVo);
    		}
        }
			
		
		

		model.addAttribute("resultCnt", result);
		model.addAttribute(postVo);
		
		if(!StrUtil.isEmpty(req.get("fileDel"))){
			Object value = req.get("fileDel");
			if (value.getClass().isArray()) {
				for(int i=0;i<((String[]) req.get("fileDel")).length;i++){
					req.put("fileIdx", ((String[])req.get("fileDel"))[i]);
					
					FileVo fileSelect = null;
					
					fileSelect = fileService.selectFile2(req);
					Path filePath2 = Paths.get(basePath+fileSelect.getFileSaveName());
					Files.delete(filePath2);
					
					fileService.deleteFile2(req);
					
					fileService.filedelete(req);
					
				}
			}else {
				req.put("fileIdx", req.get("fileDel"));
				
				FileVo fileSelect = null;
				
				fileSelect = fileService.selectFile2(req);
				Path filePath2 = Paths.get(basePath+fileSelect.getFileSaveName());
				Files.delete(filePath2);
				
				fileService.deleteFile2(req);
				
				fileService.filedelete(req);
			}
		 }
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
	@Value("${global.file.base-path}")
	private Path basePath;
	@RequestMapping(value = "file_save")
	@ResponseBody
	public JsonResponse file_save(@RequestEgovMap EgovMap req, Model model, @RequestParam(value = "fileSaveName", required = false) List<MultipartFile> uploadFile) throws Exception {

		  
			String savePath = basePath + "/board/"+req.get("boardId");
			
			final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			PostVo postVo = mapper.convertValue(req, PostVo.class);
			
			int result = 0 ;
				
			File Folder = new File(savePath);  
			if (!Folder.exists()) {
				try{
				    Folder.mkdir(); //폴더 생성합니다.
			    System.out.println("폴더가 생성되었습니다.");
			    } 
			    catch(Exception e){
			    e.getStackTrace();
			}        
			 }else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
			}
			
			if(StrUtil.isEmpty(uploadFile))	{
				 
			 }else {
				 if (uploadFile.size() > 0) {
			            for(int i=0;i<uploadFile.size();i++){
			            	FileVo fileVo = new FileVo();
			    			 
			     			fileVo.setPostIdx(StrUtil.toInt(postVo.getPostIdx()));
			     			fileVo.setBoardIdx(StrUtil.toInt(req.get("boardIdx")));
			     			
			     			String boardId = StrUtil.toStr(req.get("boardId"));
			     			fileVo.setBoardId(boardId);
			            	
							 String origFilename = uploadFile.get(i).getOriginalFilename();
							 
							 if(origFilename != ""){
								 String fileBaseName = FilenameUtils.getBaseName(origFilename);
								 String fileExtension = FilenameUtils.getExtension(origFilename);
								 String filename = uploadFile(origFilename) + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
								 String filePath = savePath + "/" + filename;
								 
								 fileVo.setFileFilesize(uploadFile.get(i).getSize());
								 fileVo.setFileOriginName(origFilename);
								 fileVo.setFileSaveName(filename);
								 fileVo.setFileSort(StrUtil.toInt(i));
								 fileVo.setFileOriginName(origFilename);
								 
								 if(uploadFile.get(i).getContentType().startsWith("image") == false) {	 
									 fileVo.setFileIsImage(0);
								 }else {
									 fileVo.setFileIsImage(1);
								 }
								 
								 fileVo.setFileType(!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
								
								 Files.createDirectories(Paths.get(filePath));
								 uploadFile.get(i).transferTo(new File(filePath));
								 
								 
								 fileVo.setPostIdx(StrUtil.toInt(req.get("newPostIdx")));
								 
								 fileVo.setInsertIdx(StrUtil.getSessionIdx());
								 fileVo.setInsertIp(StrUtil.getClientIP());
								 
								 result = fileService.insert(fileVo);
								 
								 fileService.fileadd(req);	
							 }
			            }
				 }
			 }
		
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	//첨부파일 명 랜덤으로 돌리기
	private String uploadFile(String originalName) throws Exception{
	    UUID uuid = UUID.randomUUID();
	    String savedName = uuid.toString();
	    
	    return savedName;
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

		int result = 0 ;
		
		HttpServletRequest http = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();;
		HttpSession session = http.getSession(true);
		PostVo vo = null;
		
		if (StrUtil.isEmpty(req.get("postIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "인덱스가 누락되었습니다.");
			
		}
		
		vo = postService.select(req);
		int intValue1 = vo.getInsertIdx();
		String insertIdx = Integer.toString(intValue1);
		String memberIdx = StrUtil.toStr(session.getAttribute("memberIdx"));
		String memberRole = StrUtil.toStr(session.getAttribute("memberRole"));
		
		if(!insertIdx.equals(memberIdx) && !memberRole.equals("SUPER") && !memberRole.equals("ADMIN")) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "작성자가 다릅니다.");
		}
		
		
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		postService.deleteFile(req);
		result = postService.delete(req);
		model.addAttribute("resultCnt", result);

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
		
		FileVo vo = null;
		String savePath = basePath + "/board/"+req.get("boardId");
		
		if(!StrUtil.isEmpty(req.get("fileIdx"))) {
			vo = fileService.selectFile2(req);
		}
		if(vo == null){
		}else{
			return localFileService.download(savePath + "/" + vo.getFileSaveName(), vo.getFileOriginName());
			}
		return null;
		}
	

}