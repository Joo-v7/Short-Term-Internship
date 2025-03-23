package kepco.cms.layout;
import java.io.File;
import java.io.IOException;
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

import kepco.cms.layout.log.LogService;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 레이아웃 관리
 */
@Controller
@RequestMapping(value="${global.admin-path}/cms/layout")
public class AdminLayoutController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LayoutService layoutService;
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	LogService logService;
	
	@Autowired
	private ServletContext servletContext;
	
	/**
	 * 레이아웃 목록
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value="list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		List<SiteVo> vos = siteService.selectAll(req);
		model.addAttribute("siteList", vos);
		
		return "admin/cms/layout/list";
		
	}
	
	/**
	 * 레이아웃 폼
	 * @param req
	 * @param model
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception{
		
		LayoutVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("layoutIdx"))) {
			vo = layoutService.select(req);
			
			// 실제 물리적 파일 내용이 더 중요
			String rootPath = servletContext.getRealPath("");
			String filePath = vo.getLayoutPath() + vo.getLayoutCd() + ".html";
			File file = Paths.get(rootPath, filePath).toFile();
			if(file.exists()) {
				Date fileUpdateDate = new Date(file.lastModified());
				model.addAttribute("fileUpdateDate", fileUpdateDate);
				
				// 최신버전의 파일내용 가져오기
				if( vo.getUpdateDate() != null ) {
					if(vo.getUpdateDate().compareTo(fileUpdateDate) < 0 ) {
						String pageContent = FileUtils.readFileToString(file, "UTF-8");
						vo.setLayoutContent(pageContent);
						
						model.addAttribute("isFileContent", true);
					}
				}
				else {
					if(vo.getInsertDate().compareTo(fileUpdateDate) < 0 ) {
						String pageContent = FileUtils.readFileToString(file, "UTF-8");
						vo.setLayoutContent(pageContent);
						
						model.addAttribute("isFileContent", true);
					}
				}
			}
		} else {
			
			if (StrUtil.isBlank(req.get("site"))) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트코드는 필수입력 입니다.");
			}
			
			vo = new LayoutVo();
			vo.setSite(StrUtil.toStr(req.get("site")));
		}
		
		model.addAttribute("vo",vo);
		
		return "admin/cms/layout/form";
	}
	
	/**
	 * 레이아웃 저장
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		LayoutVo layoutVo = mapper.convertValue(req, LayoutVo.class);
		
		if (StrUtil.isBlank(layoutVo.getSite())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사이트코드는 필수입력 입니다.");
		}
		if (StrUtil.isBlank(layoutVo.getLayoutNm())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "레이아웃명은 필수입력 입니다.");
		}
		int result = 0;
		
		String rootPath = servletContext.getRealPath("");
		String filePath = layoutVo.getLayoutPath() + layoutVo.getLayoutCd() + ".html";
		File file = Paths.get(rootPath, filePath).toFile();
		
		if(layoutVo.getLayoutIdx() == 0) {
			
			if( file.exists()) {
				throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "파일이 이미 존재 합니다.");
			}
			
			layoutVo.setInsertIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
			layoutVo.setInsertIp(StrUtil.getClientIP());
			result = layoutService.insert(layoutVo);
		}else {
			logService.insert(req);
			layoutVo.setUpdateIdx(StrUtil.toInt(StrUtil.getSessionIdx()));
			layoutVo.setUpdateIp(StrUtil.getClientIP());
			result = layoutService.update(layoutVo);
		}
		
		try {
			FileUtils.forceMkdirParent(file);
			FileUtils.writeStringToFile(file, layoutVo.getLayoutContent(), "UTF-8");
		} catch (IOException e) {
//			e.printStackTrace();
			throw new CmsCustomException(CmsStatusCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		
		model.addAttribute("result", result);
		model.addAttribute(layoutVo);
		
		return new JsonResponse.Builder().data(model).build();		
	}
	
	/**
	 * 레이아웃 목록 데이터
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		String site = (String) req.get("siteIdx");
		
		List<SiteVo> vos = siteService.selectAll(req);
		List<LayoutVo> list = layoutService.selectAll(req);
		
		model.addAttribute("site", site);
		model.addAttribute("siteList", vos);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 레이아웃 삭제
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public JsonResponse delete (@RequestEgovMap EgovMap req, Model model) {
		
		req.put("deleteIdx", StrUtil.getSessionIdx());
		req.put("deleteIp", StrUtil.getClientIP());
		int result = layoutService.delete(req);
		
		model.addAttribute("result", result);
		return new JsonResponse.Builder().data(model).build();
	}
	
	/**
	 * 레이아웃 모달
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
		
		return "admin/cms/layout/listModal";
	}
	
	
}
