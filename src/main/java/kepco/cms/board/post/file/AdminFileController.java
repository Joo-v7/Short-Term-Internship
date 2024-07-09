package kepco.cms.board.post.file;

import java.nio.file.Path;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import kepco.cms.board.post.PostService;
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
 * 게시물 첨부파일
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/board/post/file")
public class AdminFileController {
	
	@Autowired
	SiteService siteService;

	@Autowired
	BoardService boardService;

	@Autowired
	PostService postService;

	@Autowired
	FileService fileService;
	
	@Autowired
	LocalFileService localFileService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final static String FILE_PATH = "board/post/file"; 
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	/**
	 * 관리자 첨부파일 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/board/post/file/list";
		
		List<SiteVo> siteList = siteService.selectAll(req);
		model.addAttribute("siteList", siteList);
		
		model.addAttribute("req",req);
		
		List<BoardVo> boardList = boardService.selectAll(req);
		model.addAttribute("boardList", boardList);
		
		return viewName;
	}
	
	/**
	 * 관리자 첨부파일 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/board/post/file/form";
		
		FileVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("fileIdx")))	{
			vo = fileService.select(req);
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
	 * 관리자 첨부파일 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<FileVo> list = fileService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 관리자 첨부파일 저장
	 * @param req
	 * @param files
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile[] files, Model model) {
		
		// "Unrecognized field" 무시
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		FileVo fileVo = mapper.convertValue(req, FileVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("fileIdx")))	{
			
			if (files != null) {
				for (MultipartFile file : files) {
					FileVo multiFile = localFileService.upload2(req, file, FILE_PATH);
					fileVo.setInsertIdx(StrUtil.getSessionIdx());
					fileVo.setInsertIp(StrUtil.getClientIP());
					result = fileService.insert(multiFile);
					log.debug(multiFile.toString());
				}
			}
		}else {
			fileVo.setUpdateIdx(StrUtil.getSessionIdx());
			fileVo.setUpdateIp(StrUtil.getClientIP());
			result = fileService.update(fileVo);
		}
		model.addAttribute("resultCnt", result);
		model.addAttribute(fileVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 관리자 첨부파일 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("fileIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "인덱스가 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = fileService.delete(req);
		model.addAttribute("resultCnt", result);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 관리자 첨부파일 다운로드
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	public ResponseEntity<Resource> download(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		FileVo fileVo = mapper.convertValue(req, FileVo.class);
		
		FileVo vo = fileService.select(req);
		String savePath = basePath + "/board/"+vo.getBoardId();
		//다운로드 횟수 증가
		int resultCnt = fileService.download(fileVo);
		
		return localFileService.download(savePath + "/" + vo.getFileSaveName(), vo.getFileOriginName());
	}

}
