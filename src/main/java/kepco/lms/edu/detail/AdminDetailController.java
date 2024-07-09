package kepco.lms.edu.detail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.EduService;
import kepco.lms.edu.pack.PackService;
import kepco.util.StrUtil;
import kepco.util.Thumbnail;
/**
 * 훈련 과정 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/detail")
public class AdminDetailController {

	@Autowired
	DetailService detailService;

	@Autowired
	MemberService memberService;

	@Autowired
	LocalFileService localFileService;

	@Autowired
	CodeService codeService;

	@Autowired
	EduService eduService;

	@Autowired
	PackService packService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TemplateService templateService;
	
	private final static String FILE_PATH = "edu/detail/";

	private final String THUMB_SUFFIX = ".thumb";

	private final String[] IMAGE_ALLOW_EXTENSION = { "gif", "jpg", "jpeg", "png", "bmp" };
	private final String[] FILE_ALLOW_EXTENSION = { "doc", "hwp", "hwpx", "pdf", "ppt", "xls", "pptx", "docx", "xlsx",
			"txt", "psd" };
	private final int imageFileSize = 10 * 1024 * 1024; // 10MB
	private final int fileFileSize = 500 * 1024 * 1024; // 500MB

	private int imageFail = 0;
	private int fileFail = 0;

	@Value("${global.file.base-path}")
	private Path basePath;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 훈련 과정 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/list";

		model.addAttribute("req", req);

		return viewName;
	}

	/**
	 * 훈련 신청자 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "registList")
	public String registList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/registList";

		model.addAttribute("req", req);
		model.addAttribute("gubun", "regist");

		return viewName;
	}
	
	/**
	 * 훈련 팀 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "teamList")
	public String teamList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/teamList";

		model.addAttribute("req", req);
		model.addAttribute("gubun", "team");

		return viewName;
	}

	/**
	 * 훈련 팀 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "teamDetail")
	public String teamDetail(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/teamDetail";

		List<DetailVo> list = detailService.detail(req);

		model.addAttribute("req", req);
		model.addAttribute("list", list);

		return viewName;
	}
	
	/**
	 * 훈련 콘텐츠 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "eduList")
	public String eduList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/eduList";

		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 만족도조사 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "polList")
	public String polList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/polList";

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

		String viewName = "admin/lms/edu/detail/evList";

		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 훈련 과정 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/form";

		DetailVo vo = null;

		if (!StrUtil.isEmpty(req.get("detailIdx"))) {
			vo = detailService.select(req);
		}

		model.addAttribute("req", req);
		model.addAttribute("vo", vo);

		return viewName;
	}
	/**
	 * 훈련 과정 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);

		List<DetailVo> list = detailService.selectAll(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 훈련 과정 저장
	 * @param req
	 * @param files
	 * @param model
	 * @param multiRequest
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required = false) MultipartFile[] files,
			Model model, MultipartHttpServletRequest multiRequest) {
		
		if (StrUtil.isBlank(req.get("trainTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "훈련명은(는) 필수입력 입니다.");
		}
		
		if (StrUtil.isBlank(req.get("eduTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "콘텐츠(는) 필수입력 입니다.");
		}

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		DetailVo detailVo = mapper.convertValue(req, DetailVo.class);

		Thumbnail thumbnail = new Thumbnail();

		int result = 0;
		imageFail = 0;
		fileFail = 0;

		if (!StrUtil.isEmpty(multiRequest.getFileMap())) {

			multiRequest.getFileMap().forEach((key, value) -> {

				if (!StrUtil.isBlank(value.getOriginalFilename())) {
					// 임의 파일명 생성
					String uuid = UUID.randomUUID().toString();
					String fileBaseName = FilenameUtils.getBaseName(value.getOriginalFilename()).replaceAll("_", " ");
					String FileOriginNm = value.getOriginalFilename();
					String fileExtension = FilenameUtils.getExtension(value.getOriginalFilename());
//					String fileSaveName = fileBaseName + "_" + uuid
					String fileSaveName = uuid
							+ (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");

					switch (key) {
					case "eduImage1":
						detailVo.setEduImage1(fileSaveName);
						// 파일 확장자 체크
						for (int i = 0; i < IMAGE_ALLOW_EXTENSION.length; i++) {
							imageFail = 1;
							if (fileExtension.equalsIgnoreCase(IMAGE_ALLOW_EXTENSION[i])) {
								imageFail = 0;
								break;
							}
						}

						// 파일 크기 체크
						if (value.getSize() > imageFileSize) {
							imageFail = 2;
						}

						break;
//					case "eduImage2": detailVo.setEduImage2(fileSaveName); break;
					case "eduFile1":
						detailVo.setEduFile1(fileSaveName); // 파일 확장자 체크
						for (int i = 0; i < FILE_ALLOW_EXTENSION.length; i++) {
							fileFail = 1;
							if (fileExtension.equalsIgnoreCase(FILE_ALLOW_EXTENSION[i])) {
								fileFail = 0;
								break;
							}
						}

						// 파일 크기 체크
						if (value.getSize() > fileFileSize) {
							fileFail = 2;
						}

						break;
//					case "eduFile2" : detailVo.setEduFile2(fileSaveName); break;
//					case "eduFile3" : detailVo.setEduFile3(fileSaveName); break;
//					case "eduFile4" : detailVo.setEduFile4(fileSaveName); break;
//					case "eduFile5" : detailVo.setEduFile5(fileSaveName); break;
					default:
						break;
					}
				}
			});

			model.addAttribute("imageFail", imageFail);
			model.addAttribute("fileFail", fileFail);
			if (imageFail > 0 || fileFail > 0) {
				return new JsonResponse.Builder().data(model).build();
			}
		}

		if (StrUtil.isEmpty(req.get("eduAcceptBgnTime"))) {
			detailVo.setEduAcceptBgnTime("00");
		}
		if (StrUtil.isEmpty(req.get("eduAcceptEndTime"))) {
			detailVo.setEduAcceptEndTime("00");
		}
		if (StrUtil.isEmpty(req.get("eduLimitCnt"))) {
			detailVo.setEduLimitCnt(20);
		}

		if (StrUtil.isEmpty(req.get("detailIdx"))) {
			// 승인정보 없을 시 승인대기로 설정
			if (StrUtil.isEmpty(req.get("detailState"))) {
				detailVo.setDetailState("1");
			}
			detailVo.setInsertIdx(StrUtil.getSessionIdx());
			detailVo.setInsertIp(StrUtil.getClientIP());
			result = detailService.insert(detailVo);
		} else {
			detailVo.setUpdateIdx(StrUtil.getSessionIdx());
			detailVo.setUpdateIp(StrUtil.getClientIP());
			result = detailService.update(detailVo);
			
			if(req.get("detailState").equals("2")) { //훈련 승인시 이메일 전송
				EmailVo emailVo = mapper.convertValue(req, EmailVo.class);
				EgovMap egovMap = new EgovMap();
				TemplateVo templateVo = new TemplateVo();
	        	egovMap.put("tempIdx", "7");
				templateVo = templateService.select(egovMap);
				
				egovMap.put("memberIdx", req.get("teacherIdx")); //교수자 정보 가져오기
	        	MemberVo memberVo = memberService.select(egovMap);
	        	
				emailVo.setTo(memberVo.getMemberEmail());
//				emailVo.setFrom("초실감훈련센터");//email 바꾸기 필요
				emailVo.setSubject(templateVo.getTempTitle());
				emailVo.setText(templateVo.getTempContent());
				emailVo.setUseHtml(true);
				emailVo.setUseTemplate(true);
				
				Map<String, Object> map = Map.of("memberName",memberVo.getMemberNm(), "now", new Date(), "trainTitle", detailVo.getTrainTitle()); 
				emailVo.setTemplateMap(map);
				emailService.send(emailVo);
			}
			
		}

		if (!StrUtil.isEmpty(multiRequest.getFileMap())) {

			Path fullPath = this.basePath.resolve(FILE_PATH);

			multiRequest.getFileMap().forEach((key, value) -> {

				if (!StrUtil.isBlank(value.getOriginalFilename())) {
					try {
						Files.createDirectories(fullPath);
					} catch (IOException e) {
						log.error(e.getMessage());
					}

					String fileSaveName = "";

					switch (key) {
					case "eduImage1":
						fileSaveName = detailVo.getEduImage1();
						break;
//					case "eduImage2": fileSaveName = detailVo.getEduImage2(); break;
					case "eduFile1":
						fileSaveName = detailVo.getEduFile1();
						break;
//					case "eduFile2" : fileSaveName = detailVo.getEduFile2(); break;
//					case "eduFile3" : fileSaveName = detailVo.getEduFile3(); break;
//					case "eduFile4" : fileSaveName = detailVo.getEduFile4(); break;
//					case "eduFile5" : fileSaveName = detailVo.getEduFile5(); break;
					default:
						break;
					}

					try {
						value.transferTo(new File(fullPath + "/" + fileSaveName));
						// 썸네일 생성
						if (value.getContentType().contains("image") == true) {
							thumbnail.setThumbnail(this.basePath.resolve(FILE_PATH) + "/" + fileSaveName,
									this.basePath.resolve(FILE_PATH) + "/" + "thumb_" + fileSaveName, 600, 0); // 원본,
																												// 뉴파일명,
																												// 새로운
																												// 너비,
																												// 새로운
																												// 높이(0:비율값)
						}
						//훈련 과정 등록시 메타데이터 데이터 테이블에도 해당 정보 등록
						String fileExtension = FilenameUtils.getExtension(value.getOriginalFilename());
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				}
			});
		}

		model.addAttribute("resultCnt", result);
		model.addAttribute(detailVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 훈련과정 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		int result = 0;

		if (StrUtil.isEmpty(req.get("detailIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}

		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		result = detailService.delete(req);
		model.addAttribute("resultCnt", result);

		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 첨부파일 다운로드
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

		String fileName = "";
		String fileOrigin = "";

		fileName = StrUtil.toStr(req.get("fileName"), "");
		// fileOrigin = StrUtil.toStr(req.get("fileOrigin"),"");

		if ("".equals(fileName)) {
		} else {
			return localFileService.download(FILE_PATH + fileName, fileName);
		}
		return null;
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
		
		DetailVo vo = detailService.select(req);
		localFileService.delete(FILE_PATH, vo.getEduImage1());
		
		vo.setEduImage1(null);
		int result = detailService.thumbnailUpdate(vo);
		
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
		
		DetailVo vo = detailService.select(req);
		localFileService.delete(FILE_PATH, vo.getEduFile1());
		
		vo.setEduFile1(null);
		int result = detailService.fileUpdate(vo);
		
		model.addAttribute("result", result);
		
		
		return new JsonResponse.Builder().data(model).build();
	}
	//화면설계서용 임시
	@SiteLayout
	@RequestMapping(value = "cmsList")
	public String cmsList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/detail/cmsList";

		model.addAttribute("req", req);

		return viewName;
	}

}