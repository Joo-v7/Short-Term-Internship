package kepco.lms.vod;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.code.CodeService;
import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.vod.category.VodCategoryService;
import kepco.lms.vod.category.VodCategoryVo;
import kepco.util.StrUtil;
import kepco.util.Thumbnail;
/**
 * 영상교육 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/vod")
public class AdminVodController {
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	VodService vodService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	VodCategoryService vodCategoryService;
	
	@Autowired
	LocalFileService localFileService;
	
	private final String[] IMAGE_ALLOW_EXTENSION = { "gif","jpg","jpeg","png","bmp" };
	private final String[] FILE_ALLOW_EXTENSION = { "doc","hwp","hwpx","pdf","ppt","xls","pptx","docx","xlsx","txt","psd" };
	private final int imageFileSize = 10 * 1024 * 1024; // 10MB
	private final int fileFileSize = 500 * 1024 * 1024; // 500MB
	
	private int imageFail = 0;
	private int fileFail = 0;
	
	private final static String FILE_PATH = "vod/"; 
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 대시보드
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "dashboard")
	public String dashboard(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/vod/dashboard";
		
		model.addAttribute("req", req);

		return viewName;
	}
	
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

		String viewName = "admin/lms/vod/list";
		
		List<VodCategoryVo> categoryList = vodCategoryService.selectAll(req);
		model.addAttribute("categoryList", categoryList);
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
		
		String viewName = "admin/lms/vod/memberList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 영상교육 콘텐츠 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contentList")
	public String contentList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/contentList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 만족도 조사 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "polList")
	public String polList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/polList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 사후평가 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "evList")
	public String evlList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/vod/evList";
		
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
		
		String viewName = "admin/lms/vod/form";
		
		VodVo vo = new VodVo();
		LocalDate today = LocalDate.now();
        LocalDate oneMonthLater = today.plusMonths(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = today.format(formatter);
        String endDate = oneMonthLater.format(formatter);
        
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			vo = vodService.select(req);
			if(StrUtil.isEmpty(vo.getVodAcceptBgnDate()) && StrUtil.isEmpty(vo.getVodAcceptEndDate())) {
		        vo.setVodAcceptBgnDate(startDate);
		        vo.setVodAcceptEndDate(endDate);
			}
		}else {
			vo.setVodAcceptBgnDate(startDate);
	        vo.setVodAcceptEndDate(endDate);
		}
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 영상교육 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<VodVo> list = vodService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 영상교육 저장
	 * @param req
	 * @param model
	 * @param file
	 * @param multiRequest
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model, @RequestParam(required=false) MultipartFile file, MultipartHttpServletRequest multiRequest) {

		Thumbnail thumbnail = new Thumbnail();

		String vodKeyword = StrUtil.toStr(req.get("vodKeyword"));

		// 쉼표 앞뒤에 있는 띄어쓰기를 모두 제거
		String cleanedKeyword = vodKeyword.replaceAll("\\s*,\\s*", ",");

		// 두 개의 쉼표가 연속으로 나오는 경우 방지
		if (cleanedKeyword.matches(".*,\\s*,.*")) {
		    throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "키워드를 구분하는 쉼표는 붙여서 사용할 수 없습니다.");
		}

		// 단어의 시작과 끝에 ,가 있는 경우 방지
		if (cleanedKeyword.matches(",.*|.*,")) {
		    throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "키워드는 쉼표로 시작하거나 끝나지 않게 작성해야 합니다.");
		}
		req.put("vodKeyword", cleanedKeyword);

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		VodVo vodVo = mapper.convertValue(req, VodVo.class);
		
		int result = 0 ;
		imageFail = 0;
		fileFail = 0;
		
		if(!StrUtil.isEmpty(multiRequest.getFileMap())) {
			
			multiRequest.getFileMap().forEach((key,value)->{
				
				if(!StrUtil.isBlank(value.getOriginalFilename())) {
					//임의 파일명 생성
					String uuid = UUID.randomUUID().toString();
					String fileBaseName = FilenameUtils.getBaseName(value.getOriginalFilename()).replaceAll("_", " ");;
					String fileExtension = FilenameUtils.getExtension(value.getOriginalFilename());
//					String fileSaveName = fileBaseName + "_" + uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
					String fileSaveName = uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
					
					switch (key) {
					case "vodImage": vodVo.setVodImage(fileSaveName);
						//파일 확장자 체크
						for(int i=0; i<IMAGE_ALLOW_EXTENSION.length; i++) {
							imageFail = 1;
							if(fileExtension.equalsIgnoreCase(IMAGE_ALLOW_EXTENSION[i])) {
								imageFail = 0;
								break;
							}
						}
						//파일 크기 체크
						if(value.getSize() > imageFileSize) {
							imageFail = 2;
						}
						break;
					case "vodFile" : vodVo.setVodFile(fileSaveName); //파일 확장자 체크
						for(int i=0; i<FILE_ALLOW_EXTENSION.length; i++) {
							fileFail = 1;
							if(fileExtension.equalsIgnoreCase(FILE_ALLOW_EXTENSION[i])) {
								fileFail = 0;
								break;
							}
						}
						//파일 크기 체크
						if(value.getSize() > fileFileSize) {
							fileFail = 2;
						}
						break;
					default:
						break;
					}
				}
			});
			
			model.addAttribute("imageFail", imageFail);
			model.addAttribute("fileFail", fileFail);
			if(imageFail > 0 || fileFail > 0) {
				return new JsonResponse.Builder().data(model).build();
			}
		}
		
		if(StrUtil.isEmpty(req.get("vodIdx"))) {
			vodVo.setInsertIdx(StrUtil.getSessionIdx());
			vodVo.setInsertIp(StrUtil.getClientIP());
			result = vodService.insert(vodVo);
		}else {
			vodVo.setUpdateIdx(StrUtil.getSessionIdx());
			vodVo.setUpdateIp(StrUtil.getClientIP());
			result = vodService.update(vodVo);
		}
		
		if(!StrUtil.isEmpty(multiRequest.getFileMap())) {
			
			Path fullPath = this.basePath.resolve(FILE_PATH);
	
			multiRequest.getFileMap().forEach((key,value)->{
				
				if(!StrUtil.isBlank(value.getOriginalFilename())) {
					try {
						Files.createDirectories(fullPath);
					} catch (IOException e) {
						log.error(e.getMessage());
					}
					
					String fileSaveName = "";
					
					switch (key) {
					case "vodImage": fileSaveName = vodVo.getVodImage(); break;
					case "vodFile" : fileSaveName = vodVo.getVodFile(); break;
					default:
						break;
					}
	
					try {
						value.transferTo(new File(fullPath+"/"+fileSaveName));
						//썸네일 생성
						if(value.getContentType().contains("image") == true){
							thumbnail.setThumbnail(this.basePath.resolve(FILE_PATH) + "/" + fileSaveName, this.basePath.resolve(FILE_PATH) + "/" + "thumb_" + fileSaveName, 600, 0); // 원본, 뉴파일명, 새로운 너비, 새로운 높이(0:비율값)
						}
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				}
			});
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(vodVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 영상교육 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		long result = 0 ;
		
		if (StrUtil.isEmpty(req.get("vodIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = vodService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 썸네일 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "thumbnailDelete")
	@ResponseBody
	public JsonResponse thumbnailDelete(@RequestEgovMap EgovMap req, Model model) {
		
		VodVo vo = vodService.select(req);
		localFileService.delete(FILE_PATH, vo.getVodImage());
		
		vo.setVodImage(null);
		int result = vodService.thumbnailUpdate(vo);
		
		model.addAttribute("result", result);
		
		
		return new JsonResponse.Builder().data(model).build();
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
		
		VodVo vo = vodService.select(req);
		localFileService.delete(FILE_PATH, vo.getVodFile());
		
		vo.setVodFile(null);
		int result = vodService.fileUpdate(vo);
		
		model.addAttribute("result", result);
		
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 영상교육 파일 다운로드
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	protected ResponseEntity<Resource> fileDownload(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String fileName = "";
		String fileOrigin = "";
		
		fileName = StrUtil.toStr(req.get("fileName"),"");
		//fileOrigin = StrUtil.toStr(req.get("fileOrigin"),"");
		
		if("".equals(fileName)){
		}else{
			return localFileService.download(FILE_PATH + fileName, fileName);
			}
		return null;
		}
}