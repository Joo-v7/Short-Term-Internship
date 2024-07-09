package kepco.cms.board.post;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.board.BoardService;
import kepco.cms.board.BoardVo;
import kepco.cms.board.post.comment.CommentService;
import kepco.cms.board.post.comment.CommentVo;
import kepco.cms.board.post.file.FileService;
import kepco.cms.board.post.file.FileVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 게시물
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/board/post")
public class AdminPostController {

	@Autowired
	SiteService siteService;

	@Autowired
	BoardService boardService;

	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;

	@Autowired
	FileService fileService;

	@Autowired
	LocalFileService localFileService;

	@Value("${global.file.base-path}")
	private Path basePath;

	private final static String FILE_PATH = "board/post/file";
	
	/**
	 * 게시물 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/board/post/list";

		List<BoardVo> boardList = boardService.selectAll(req);
		model.addAttribute("boardList", boardList);

		model.addAttribute("req", req);

		return viewName;
	}
	
	/**
	 * 게시물 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/board/post/form";

		PostVo vo = null;
		List<FileVo> fileVo = null;
		List<BoardVo> boardList = boardService.selectAll(req);
		BoardVo boardVo = null;
		if (!StrUtil.isEmpty(req.get("postIdx"))) {
			vo = postService.select(req);
			req.put("boardIdx", vo.getBoardIdx());
			boardVo = boardService.select(req);
			fileVo = fileService.selectFile(req);

		}

		model.addAttribute("req", req);
		model.addAttribute("vo", vo);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("fileVo", fileVo);
		model.addAttribute("boardList", boardList);
		return viewName;
	}
	
	/**
	 * 게시물 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);

		List<PostVo> list = postService.selectAll(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 게시물 저장
	 * @param req
	 * @param model
	 * @param files
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required = false) MultipartFile[] files,
			Model model) {

		// "Unrecognized field" 무시
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		PostVo postVo = mapper.convertValue(req, PostVo.class);
		BoardVo boardVo = null;
		int result = 0;

		if ((StrUtil.isEmpty(req.get("boardIdx"))) && (StrUtil.isEmpty(req.get("boardId")))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "게시판이 누락되었습니다.");
		}
		if (!StrUtil.isEmpty(req.get("boardId"))) {
			boardVo = boardService.select(req);
			postVo.setBoardIdx(boardVo.getBoardIdx());
		}

		if (StrUtil.isEmpty(req.get("postSecret"))) {
			postVo.setPostSecret("n");
		}
		if (StrUtil.isEmpty(req.get("postNotice"))) {
			postVo.setPostNotice(0);
		}
		if (StrUtil.isEmpty(req.get("postHtml"))) {
			postVo.setPostHtml(0);
		}

		if (StrUtil.isEmpty(req.get("postIdx"))) {
			postVo.setInsertIdx(StrUtil.getSessionIdx());
			postVo.setInsertIp(StrUtil.getClientIP());
			result = postService.insert(postVo);
		} else {
			postVo.setUpdateIdx(StrUtil.getSessionIdx());
			postVo.setUpdateIp(StrUtil.getClientIP());
			result = postService.update(postVo);
		}

		// 첨부파일 저장
		if (files != null) {
			for (MultipartFile file : files) {
				FileVo multiFile = localFileService.upload2(req, file, FILE_PATH);
				result = fileService.insert(multiFile);
			}
		}

		// 첨부파일 삭제
		String[] deleleIdx = (String[]) req.get("deleleIdx");

		if (!StrUtil.isEmpty(deleleIdx)) {
			for (int i = 0; i < deleleIdx.length; i++) {
				int fileResult = fileService.deleteFile(deleleIdx[i]);
			}
		}

		model.addAttribute("resultCnt", result);
		model.addAttribute(postVo);
		model.addAttribute(boardVo);

		return new JsonResponse.Builder().data(model).build();
	}

	/**
	 * 게시물 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0;

		if (StrUtil.isEmpty(req.get("postIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "인덱스가 누락되었습니다.");
		}

		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		postService.deleteFile(req);
		result = postService.delete(req);
		model.addAttribute("resultCnt", result);

		return new JsonResponse.Builder().data(model).build();
	}

	/**
	 * 댓글 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "commentList")
	@ResponseBody
	public JsonResponse commentList(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);

		List<CommentVo> list = commentService.selectPostComment(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}

	/**
	 * 댓글 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "commentForm")
	public String commentForm(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/cms/board/post/commentForm";

//			CommentVo vo = commentService.select(req);

		CommentVo vo = null;

		if (!StrUtil.isEmpty(req.get("commentIdx"))) {
			vo = commentService.select(req);
		}

		List<SiteVo> siteList = siteService.selectAll(req);
		List<BoardVo> boardList = boardService.selectAll(req);

		model.addAttribute("req", req);
		model.addAttribute("vo", vo);
		model.addAttribute("siteList", siteList);
		model.addAttribute("boardList", boardList);

		return viewName;
	}
	
	/**
	 * 댓글 저장
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "commentSave")
	@ResponseBody
	public JsonResponse commentSave(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		CommentVo commentVo = mapper.convertValue(req, CommentVo.class);

		int result = 0;

		commentVo.setInsertIdx(StrUtil.getSessionIdx());
		commentVo.setInsertIp(StrUtil.getClientIP());
		result = commentService.insert(commentVo);
		model.addAttribute("resultCnt", result);
		model.addAttribute(commentVo);

		return new JsonResponse.Builder().data(model).build();
	}

	@RequestMapping(value = "download")
	@ResponseBody
	protected ResponseEntity<Resource> fileDownload(@RequestEgovMap EgovMap req, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		FileVo vo = null;
		String savePath = basePath + "/board/" + req.get("boardId");

		if (!StrUtil.isEmpty(req.get("fileIdx"))) {
			vo = fileService.selectFile2(req);
		}
		if (vo == null) {
		} else {
			return localFileService.download(savePath + "/" + vo.getFileSaveName(), vo.getFileOriginName());
		}
		return null;
	}
	
	/**
	 * 관리자 게시물 첨부파일 저장
	 * @param req
	 * @param model
	 * @param uploadFile
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "file_save")
	@ResponseBody
	public JsonResponse file_save(@RequestEgovMap EgovMap req, Model model,
			@RequestParam(value = "fileSaveName", required = false) List<MultipartFile> uploadFile) throws Exception {

		String savePath = basePath + "/board/" + req.get("boardId");

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		PostVo postVo = mapper.convertValue(req, PostVo.class);

		int result = 0;

		File Folder = new File(savePath);
		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}

		if (StrUtil.isEmpty(uploadFile)) {

		} else {
			if (uploadFile.size() > 0) {
				for (int i = 0; i < uploadFile.size(); i++) {
					FileVo fileVo = new FileVo();

					fileVo.setPostIdx(StrUtil.toInt(postVo.getPostIdx()));
					fileVo.setBoardIdx(StrUtil.toInt(req.get("boardIdx")));

					String boardId = StrUtil.toStr(req.get("boardId"));
					fileVo.setBoardId(boardId);

					String origFilename = uploadFile.get(i).getOriginalFilename();

					if (origFilename != "") {
						String fileBaseName = FilenameUtils.getBaseName(origFilename);
						String fileExtension = FilenameUtils.getExtension(origFilename);
						String filename = uploadFile(origFilename)
								+ (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
						String filePath = savePath + "/" + filename;

						fileVo.setFileFilesize(uploadFile.get(i).getSize());
						fileVo.setFileOriginName(origFilename);
						fileVo.setFileSaveName(filename);
						fileVo.setFileSort(StrUtil.toInt(i));
						fileVo.setFileOriginName(origFilename);

						if (uploadFile.get(i).getContentType().startsWith("image") == false) {
							fileVo.setFileIsImage(0);
						} else {
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
}
