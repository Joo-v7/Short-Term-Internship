package kepco.lms.edu.module;

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
import kepco.util.StrUtil;
/**
 * 모듈 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/module")
public class AdminModuleController {

	@Autowired
	ModuleService moduleService;

	@Autowired
	MemberService memberService;

	private final static String FILE_PATH = "edu/module/";

	@Value("${global.file.base-path}")
	private Path basePath;

	@Autowired
	LocalFileService localFileService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String[] ALLOW_EXTENSION = { "exe" }; // 불가능한 확장자로 수정 가능
	private final int maxFileSize = 1 * 1024 * 1024 * 1024; // 1GB
	
	/**
	 * 모듈 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/module/list";

		model.addAttribute("req", req);

		return viewName;
	}
	/**
	 * 모듈 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/module/form";
		int depth = 0;
		ModuleVo vo = null;

		if (!StrUtil.isEmpty(req.get("moduleIdx"))) {
			vo = moduleService.select(req);
		}
		if(!StrUtil.isEmpty(req.get("depth"))) {
			depth = 1 + StrUtil.toInt(req.get("depth"));
		}
		model.addAttribute("depth", depth);
		model.addAttribute("req", req);
		model.addAttribute("vo", vo);

		return viewName;
	}
	/**
	 * 모듈 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);

		List<ModuleVo> list = moduleService.selectAll(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 모듈 저장
	 * @param req
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required = false) MultipartFile file,
			Model model) {

		if (StrUtil.isBlank(req.get("moduleCd"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "모듈코드은(는) 필수입력 입니다.");
		}
		if (StrUtil.isBlank(req.get("moduleTitle"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "모듈명은(는) 필수입력 입니다.");
		}
		if (!StrUtil.isInt(req.get("moduleTime"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "훈련시간은(는) 정수만 입력가능 합니다.");
		}

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		ModuleVo moduleVo = mapper.convertValue(req, ModuleVo.class);

		int result = 0;

		if (StrUtil.isEmpty(req.get("moduleIdx"))) {
			moduleVo.setInsertIdx(StrUtil.getSessionIdx());
			moduleVo.setInsertIp(StrUtil.getClientIP());
			result = moduleService.insert(moduleVo);
		} else {
			moduleVo.setUpdateIdx(StrUtil.getSessionIdx());
			moduleVo.setUpdateIp(StrUtil.getClientIP());
			result = moduleService.update(moduleVo);
		}

		model.addAttribute("resultCnt", result);
		model.addAttribute(moduleVo);

		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 모듈 파일 저장
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
		ModuleVo moduleVo = mapper.convertValue(req, ModuleVo.class);

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
					moduleVo.setModuleFileName(fileSaveName);
					moduleVo.setModuleFileOrigin(uploadFile.getOriginalFilename());

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
				moduleVo.setModuleFileName(StrUtil.toStr(req.get("moduleFileName")));
				moduleVo.setModuleFileOrigin(StrUtil.toStr(req.get("moduleFileOrigin")));
			}

			moduleVo.setUpdateIdx(StrUtil.getSessionIdx());
			moduleVo.setUpdateIp(StrUtil.getClientIP());
			result = moduleService.fileUpdate(moduleVo);
		}
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 모듈 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {

		if (StrUtil.isEmpty(req.get("moduleIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}

		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int result = moduleService.delete(req);
		model.addAttribute("resultCnt", result);

		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 모듈 파일 다운로드
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "download")
	@ResponseBody
	protected ResponseEntity<Resource> fileDownload(@RequestEgovMap EgovMap req, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		ModuleVo vo = moduleService.select(req);
		String fileName = vo.getModuleFileName();
		String fileOrigin = vo.getModuleFileOrigin();

		if ("".equals(fileOrigin)) {
		} else {
			return localFileService.download(FILE_PATH + fileName, fileOrigin);
		}
		return null;
	}

}