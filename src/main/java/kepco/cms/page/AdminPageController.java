package kepco.cms.page;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.page.log.LogService;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 페이지 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/page")
public class AdminPageController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PageService pageService;
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	LogService logService;
	
	@Autowired
	private ServletContext servletContext;
	
	/**
	 * 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		
		List<SiteVo> vos = siteService.selectAll(req);
		model.addAttribute("siteList", vos);
		
		
		 
		return "admin/cms/page/list";
	}
	/**
	 * 페이지 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/page/form";
		
		PageVo vo = null;
		

		 
		
		if(!StrUtil.isEmpty(req.get("pageIdx"))) {
			vo = pageService.select(req);
			
			// 실제 물리적 파일 내용이 더 중요
			String rootPath = servletContext.getRealPath("");
			String filePath = vo.getPagePath() + vo.getPageCd() + vo.getPageExt();
			File file = Paths.get(rootPath, filePath).toFile();
			if(file.exists()) {
				Date fileUpdateDate = new Date(file.lastModified());
				model.addAttribute("fileUpdateDate", fileUpdateDate);
				
				// 최신버전의 파일내용 가져오기
				if( vo.getUpdateDate() != null ) {
					if(vo.getUpdateDate().compareTo(fileUpdateDate) < 0 ) {
						String pageContent = FileUtils.readFileToString(file, "UTF-8");
						vo.setPageContent(pageContent);
						
						model.addAttribute("isFileContent", true);
					}
				}
				else {
					if(vo.getInsertDate().compareTo(fileUpdateDate) < 0 ) {
						String pageContent = FileUtils.readFileToString(file, "UTF-8");
						vo.setPageContent(pageContent);
						
						model.addAttribute("isFileContent", true);
					}
				}
			}
			
		} else {
			
			if (StrUtil.isBlank(req.get("site"))) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트코드는 필수입력 입니다.");
			}
			
			vo = new PageVo();
			vo.setSite(StrUtil.toStr(req.get("site")));
			vo.setPageType("sub");
			vo.setUseYn("y");
		}
		
		model.addAttribute("vo", vo);
		
		return viewName;
	}
	/**
	 * 페이지 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		PageVo pageVo = mapper.convertValue(req, PageVo.class);
		
		if (StrUtil.isBlank(pageVo.getSite())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트코드는 필수입력 입니다.");
		}
		if (StrUtil.isBlank(pageVo.getPageNm())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "페이지명은 필수입력 입니다.");
		}
		if (StrUtil.isBlank(pageVo.getPageCd())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "페이지코드는 필수입력 입니다.");
		}
		if (StrUtil.isBlank(pageVo.getPagePath())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "페이지 경로는 필수입력 입니다.");
		}
		int result = 0 ;
		
		if (!"n".equals(pageVo.getUseYn())) {
			pageVo.setUseYn("y");
		}
		
		// 파일 쓰기
		String rootPath = servletContext.getRealPath("");
		String filePath = pageVo.getPagePath() + pageVo.getPageCd() + pageVo.getPageExt();
		File file = Paths.get(rootPath, filePath).toFile();
				
		if(StrUtil.toInt(req.get("pageIdx")) == 0) {
			if( file.exists()) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "파일이 이미 존재 합니다.");
			}
			
			result = pageService.insert(pageVo);
		} else {
			// 왜 로그를 먼저 넣는거지?
			logService.insert(req);
			result = pageService.update(pageVo);
		}
		
		try {
			FileUtils.forceMkdirParent(file);
			FileUtils.writeStringToFile(file, pageVo.getPageContent(), "UTF-8");
		} catch (IOException e) {
//			e.printStackTrace();
			throw new CmsCustomException(CmsStatusCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		
		model.addAttribute("result", result);
		model.addAttribute(pageVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 페이지 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		
		String siteIdx = (String) req.get("siteIdx");
		
		List<SiteVo> vos = siteService.selectAll(req);
		List<PageVo> list = pageService.selectAll(req);
		
		model.addAttribute("siteIdx", siteIdx);
		model.addAttribute("siteList", vos);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 페이지 삭제
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete(@RequestEgovMap EgovMap req, Model model) {
		
		int result = pageService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 페이지 모달
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listModal")
	public String listModal(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
//		List<SiteVo> vos = siteService.selectAll(req);
//		model.addAttribute("siteList", vos);
		
		model.addAttribute("req", req);
		
		return "admin/cms/page/listModal";
	}
}