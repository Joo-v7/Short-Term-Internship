package kepco.lms.edu;
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
import kepco.cms.member.MemberVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.category.CategoryService;
import kepco.lms.edu.category.CategoryVo;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.module.ModuleService;
import kepco.lms.edu.pack.PackService;
import kepco.lms.edu.pack.PackVo;
import kepco.lms.edu.stat.EdustatService;
import kepco.util.StrUtil;
/**
 * 훈련 콘텐츠
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu")
public class AdminEduController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	EduService eduService;
	
	@Autowired
	PackService packService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	DetailService detailService;
	
	@Autowired
	EdustatService eduStatService;
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	LocalFileService localFileService;
	
	private final static String FILE_PATH = "edu/";

	@Value("${global.file.base-path}")
	private Path basePath;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String[] ALLOW_EXTENSION = { "exe" }; // 불가능한 확장자로 수정 가능
	private final int maxFileSize = 1 * 1024 * 1024 * 1024; // 1GB
	
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

		String viewName = "admin/lms/edu/dashboard";
		
//		req.put("codeParent", "edu");
		req.put("memberIdx", StrUtil.getSessionIdx());
		
		List<CategoryVo> categoryList = categoryService.selectAll(req);
		Object processList = eduStatService.dashBoardProcessList(req);
		Object famousList = eduStatService.dashBoardFamousList(req);
		
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("processList", processList);
		model.addAttribute("famousList", famousList);
		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 훈련 콘텐츠 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/list";
		
//		req.put("codeParent", "edu");
		List<CategoryVo> categoryList = categoryService.selectAll(req);
		
		model.addAttribute("categoryList",categoryList);
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
		
		String viewName = "admin/lms/edu/memberList";
		int scDepth = 0;
		
		if(!StrUtil.isEmpty(req.get("scDepth"))) {
			scDepth = StrUtil.toInt(req.get("scDepth"));
		}
		
		model.addAttribute("req", req);
		model.addAttribute("scDepth", scDepth);
		
		return viewName;
	}
	/**
	 * 모듈 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "moduleList")
	public String moduleList(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/moduleList";
		
		model.addAttribute("req", req);
		
		return viewName;
	}

	/**
	 * 훈련 콘텐츠 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/lms/edu/form";
		int depth = 0;
//		req.put("codeParent", "edu");
		List<CategoryVo> categoryList = categoryService.selectAll(req);
		
		EduVo vo = null;
		List<PackVo> packList = null;
		
		//TODO member 구분 정의 필요
		List<MemberVo> memberList = memberService.selectAll(req);
		
		if(!StrUtil.isEmpty(req.get("eduIdx"))) {
			vo = eduService.select(req);
			
			packList = packService.selectAll(req);
		}
		if(!StrUtil.isEmpty(req.get("depth"))) {
			depth = StrUtil.toInt(req.get("depth"));
		}
		
		model.addAttribute("depth", depth);
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("memberList",memberList);
		model.addAttribute("packList",packList);
		
		return viewName;
	}
	
	/**
	 * 훈련 콘텐츠 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<EduVo> list = eduService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 훈련 콘텐츠 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		if(StrUtil.isBlank(req.get("eduTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "콘텐츠명은(는) 필수 입력입니다.");
		}
		if(StrUtil.isBlank(req.get("memberIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "교수자은(는) 필수 입력입니다.");
		}
		String eduKeyword = StrUtil.toStr(req.get("eduKeyword"));
		
		// 쉼표 앞뒤에 있는 띄어쓰기를 제거
		String cleanedKeyword = eduKeyword.replaceAll("\\s*,\\s*", ",");

		// 두 개의 쉼표가 연속으로 나오는 경우 방지
		if (cleanedKeyword.matches(".*,\\s*,.*")) {
		    throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "키워드를 구분하는 쉼표는 붙여서 사용할 수 없습니다.");
		}

		// 단어의 시작과 끝에 ,가 있는 경우 방지
		if (cleanedKeyword.matches(",.*|.*,")) {
		    throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "키워드는 쉼표로 시작하거나 끝나지 않게 작성해야 합니다.");
		}
		req.put("eduKeyword", cleanedKeyword);
		
		// "Unrecognized field" 무시
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		EduVo eduVo = mapper.convertValue(req, EduVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("eduIdx"))) {
			//승인정보 없을 시 승인대기로 설정
			if(StrUtil.isEmpty(req.get("eduState")))	{ eduVo.setEduState("1"); }
			eduVo.setInsertIdx(StrUtil.getSessionIdx());
			eduVo.setInsertIp(StrUtil.getClientIP());
			result = eduService.insert(eduVo);
		}else {
			eduVo.setUpdateIdx(StrUtil.getSessionIdx());
			eduVo.setUpdateIp(StrUtil.getClientIP());
			result = eduService.update(eduVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(eduVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 훈련 콘텐츠 파일 저장
	 * @param req
	 * @param model
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "file_save")
	@ResponseBody
	public JsonResponse file_save(@RequestEgovMap EgovMap req, Model model,
			@RequestParam(value = "fileSaveName", required = false) MultipartFile uploadFile) throws Exception {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		EduVo eduVo = mapper.convertValue(req, EduVo.class);

		int result = 0;

		if (StrUtil.isEmpty(uploadFile)) {

		} else {
			if (!StrUtil.isEmpty(uploadFile)) {
				if (!"".equals(uploadFile.getOriginalFilename())) {

					// 임의 파일명 생성
					String uuid = UUID.randomUUID().toString();
					String fileBaseName = FilenameUtils.getBaseName(uploadFile.getOriginalFilename()).replaceAll("_",
							" ");
					;
					String fileExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
//					String fileSaveName = fileBaseName + "_" + uuid
					String fileSaveName = uuid
							+ (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
					eduVo.setAttachmentFileName(fileSaveName);
					eduVo.setAttachmentFileOrigin(uploadFile.getOriginalFilename());

					// 파일 확장자 체크
					for (int i = 0; i < ALLOW_EXTENSION.length; i++) {
						if (!fileExtension.equalsIgnoreCase(ALLOW_EXTENSION[i])) {
							model.addAttribute("fail", "1");
							return new JsonResponse.Builder().data(model).build();
						}
					}

					// 파일 크기 체크
					if (uploadFile.getSize() > maxFileSize) {
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
						uploadFile.transferTo(new File(fullPath + "/" + fileSaveName));
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				}
			} else {
				eduVo.setAttachmentFileName(StrUtil.toStr(req.get("attachmentFileName")));
				eduVo.setAttachmentFileOrigin(StrUtil.toStr(req.get("attachmentFileOrigin")));
			}

			eduVo.setUpdateIdx(StrUtil.getSessionIdx());
			eduVo.setUpdateIp(StrUtil.getClientIP());
			result = eduService.fileUpdate(eduVo);
		}
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 훈련 콘텐츠 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0 ;
		
		if (StrUtil.isEmpty(req.get("eduIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = eduService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
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
		
		EduVo vo = eduService.select(req);
		localFileService.delete(FILE_PATH, vo.getAttachmentFileName());
		
		vo.setAttachmentFileName(null);
		vo.setAttachmentFileOrigin(null);
		int result = eduService.fileUpdate(vo);
		
		model.addAttribute("result", result);
		
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 훈련 콘텐츠 파일 다운로드
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	protected ResponseEntity<Resource> download(@RequestEgovMap EgovMap req, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		EduVo vo = eduService.select(req);
		String fileName = vo.getAttachmentFileName();
		String fileOrigin = vo.getAttachmentFileOrigin();

		if ("".equals(fileOrigin)) {
		} else {
			return localFileService.download(FILE_PATH + fileName, fileOrigin);
		}
		return null;
	}
	
	//화면설계서용 임시
	@SiteLayout
	@RequestMapping(value = "cmsList")
	public String cmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/cmsList";
		
//		req.put("codeParent", "edu");
		List<CategoryVo> categoryList = categoryService.selectAll(req);
		
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("req", req);

		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "testPage")
	public String testPage(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "cms/zzz/test/test";
		List<DetailVo> detailList = detailService.selectAll(req);
		
		model.addAttribute("detailList",detailList);
		model.addAttribute("req", req);

		return viewName;
	}
}