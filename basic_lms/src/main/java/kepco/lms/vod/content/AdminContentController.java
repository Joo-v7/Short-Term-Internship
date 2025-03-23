package kepco.lms.vod.content;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
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

import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.vod.category.VodCategoryService;
import kepco.lms.vod.category.VodCategoryVo;
import kepco.util.StrUtil;
/**
 * 영상교육 콘텐츠 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod/content")
public class AdminContentController {
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	VodCategoryService vodCategoryService;
	
	@Autowired
	LocalFileService localFileService;
	
	private final static String FILE_PATH = "vod/content/"; 
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String[] ALLOW_EXTENSION = { "asx","asf","wmv","wma","mpg","mpeg","mov","avi","swf","mp4" }; //불가능한 확장자로 수정 가능
	private final int maxFileSize = 1 * 1024 * 1024 * 1024; // 1GB
	
	/**
	 * 영상교육 콘텐츠 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/content/list";
		
		List<VodCategoryVo> categoryList = vodCategoryService.selectAll(req);
		model.addAttribute("categoryList", categoryList);
		
		model.addAttribute("req", req);

		return viewName;
	}

	/**
	 * 영상교육 콘텐츠 폼 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "formList")
	public String formList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/content/formList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 영상교육 콘텐츠 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/content/form";
		req.put("useYn", "y"); //사용중인 카테고리 목록
		List<VodCategoryVo> categoryList = vodCategoryService.selectAll(req);
		req.remove("useYn");
		ContentVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("contentIdx"))) {
			vo = contentService.select(req);
		}

		model.addAttribute("categoryList",categoryList);
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
	public String detail(@RequestEgovMap EgovMap req, Model model) {
		
		String viewName = "admin/lms/vod/content/vodContentList";
		model.addAttribute("req", req);
		
		List<ContentVo> list = null;
		if(!StrUtil.isEmpty(req.get("vodIdx"))){
			list = contentService.selectAll(req);
		}
		
		model.addAttribute("list", list);
		
		return viewName;
	}
	
	/**
	 * 영상교육 콘텐츠 목록 조회
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<ContentVo> list = contentService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 영상교육 콘텐츠 저장
	 * @param req
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile file, Model model) {

		if(StrUtil.isBlank(req.get("contentTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "콘텐츠명은(는) 필수입력 입니다.");
		}
		if(!StrUtil.isInt(req.get("contentTime"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "영상시간은(는) 정수만 입력가능 합니다.");
		}
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ContentVo contentVo = mapper.convertValue(req, ContentVo.class);

		int result = 0 ;
		
		if(!"y".equals(contentVo.getUseYn())) {
			contentVo.setUseYn("n");
		}
		
		if(StrUtil.isEmpty(req.get("contentIdx"))) {
			contentVo.setInsertIdx(StrUtil.getSessionIdx());
			contentVo.setInsertIp(StrUtil.getClientIP());
			result = contentService.insert(contentVo);
		}else {
			contentVo.setUpdateIdx(StrUtil.getSessionIdx());
			contentVo.setUpdateIp(StrUtil.getClientIP());
			result = contentService.update(contentVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(contentVo);

		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 영상교육 콘텐츠 파일 저장
	 * @param req
	 * @param model
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "file_save")
	@ResponseBody
	public JsonResponse file_save(@RequestEgovMap EgovMap req, Model model, @RequestParam(value = "fileSaveName", required = false) MultipartFile uploadFile) throws Exception {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ContentVo contentVo = mapper.convertValue(req, ContentVo.class);
			
			int result = 0 ;
				
			if(StrUtil.isEmpty(uploadFile))	{
				 
			 }else {
				 if(!StrUtil.isEmpty(uploadFile)) {
						if(!"".equals(uploadFile.getOriginalFilename())) {
							
							//임의 파일명 생성
							String uuid = UUID.randomUUID().toString();
							String fileBaseName = FilenameUtils.getBaseName(uploadFile.getOriginalFilename()).replaceAll("_", " ");
							String fileExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
//							String fileSaveName = fileBaseName + "_" + uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
							String fileSaveName = uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
							contentVo.setContentFileName(fileSaveName);
							contentVo.setContentFileOrigin(uploadFile.getOriginalFilename());
							
							//파일 확장자 체크
							int fileCkeck = 0;
							for(int i=0; i<ALLOW_EXTENSION.length; i++) {
								if(fileExtension.equalsIgnoreCase(ALLOW_EXTENSION[i])) {
									fileCkeck = 1;
								}
							}
							if(fileCkeck != 1) {
								model.addAttribute("fail", "1");
								return new JsonResponse.Builder().data(model).build();
							}
							
							//파일 크기 체크
							if(uploadFile.getSize() > maxFileSize) {
								model.addAttribute("fail", "2");
								return new JsonResponse.Builder().data(model).build();
							}
							
							Path fullPath = this.basePath.resolve(FILE_PATH);
							
							try {
								Files.createDirectories(fullPath);
							} catch (IOException e) {
								log.error(e.getMessage());
							}
							
							try {
								uploadFile.transferTo(new File(fullPath+"/"+fileSaveName));
							} catch (IOException e) {
								log.error(e.getMessage());
							}
							
						}
					} else {
						contentVo.setContentFileName(StrUtil.toStr(req.get("contentFileName")));
						contentVo.setContentFileOrigin(StrUtil.toStr(req.get("contentFileOrigin")));
					}
				 
				contentVo.setUpdateIdx(StrUtil.getSessionIdx());
				contentVo.setUpdateIp(StrUtil.getClientIP());
				result = contentService.fileUpdate(contentVo);
			 }
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 영상교육 콘텐츠 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("contentIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
                                                          		result = contentService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 콘텐츠 파일 다운로드
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	protected ResponseEntity<Resource> download(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String fileName = "";
		String fileOrigin = "";
		
		fileName = StrUtil.toStr(req.get("fileName"),"");
		fileOrigin = StrUtil.toStr(req.get("fileOrigin"),"");
		
		if("".equals(fileOrigin)){
		}else{
			return localFileService.download(FILE_PATH + fileName, fileOrigin);
		}
		return null;
	}
	
	/**
	 * 첨부파일 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "fileDelete")
	@ResponseBody
	public JsonResponse fileDelete(@RequestEgovMap EgovMap req, Model model) {
		
		ContentVo vo = contentService.select(req);
		localFileService.delete(FILE_PATH, vo.getContentFileName());
		
		vo.setContentFileName(null);
		vo.setContentFileOrigin(null);
		int result = contentService.fileUpdate(vo);
		
		model.addAttribute("result", result);
		
		
		return new JsonResponse.Builder().data(model).build();
	}
	
}