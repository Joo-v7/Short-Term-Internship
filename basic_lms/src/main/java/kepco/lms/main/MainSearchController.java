package kepco.lms.main;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

import kepco.cms.board.post.PostVo;
import kepco.cms.board.post.file.FileService;
import kepco.cms.board.post.file.FileVo;
import kepco.cms.menu.MenuVo;
import kepco.cms.page.PageService;
import kepco.cms.page.PageVo;
import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.vod.VodVo;
import kepco.util.StrUtil;
/**
 * 메인화면
 */
@Controller
public class MainSearchController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MainSearchService mainSearchService;
	
	@Autowired
	SiteService siteService;
	
	@Autowired
	PageService pageService;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	LocalFileService localFileService;
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	/**
	 * 메인화면 통합검색
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "/search")
	public String search(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		if(StrUtil.isEmpty(req.get("scWord"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "검색어는 필수입력 입니다.");
		}
		
		String site = StrUtil.toStr(req.get("site"));
		
		// 사이트 조회
		EgovMap param = new EgovMap();
		
		
		if(!StrUtil.isBlank(site)) {
			param.put("site", site);
		}
		else {
			param.put("scDefaultSiteYn", "y");
		}
		SiteVo siteVo = siteService.select(param);
		
		if(siteVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "사이트를 찾을 수 없습니다.");
		}
		
		if(siteVo.getPageIdx() == 0) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "페이지가 설정되지 않았습니다.");
		}
		
		param = new EgovMap();
		param.put("scPageCd", "search");
		PageVo pageVo = pageService.select(param);
		
		if(pageVo == null) {
			throw new CmsCustomException(CmsStatusCode.NOT_FOUND, "페이지를 찾을 수 없습니다.");
		}
		
		//통합검색 검색유형별 결과
		if("all".equals(req.get("scType"))) {
			req.put("pageSize", 4);
		}else {
			req.put("pageSize", 10);
		}
		if(StrUtil.isEmpty(req.get("scOrder"))) {
			req.put("scOrder", "1");
		}
		if(StrUtil.isEmpty(req.get("scType"))) {
			req.put("scType", "all");
		}
		List<MenuVo> menuList = null;
		List<PostVo> postList = null;
		List<DetailVo> detailList = null;
		List<VodVo> vodList = null;
		List<FileVo> fileList = null;
		List<DetailVo> dataList = null;
		PageInfo<MenuVo> menuPageInfo = null;
		PageInfo<PostVo> postPageInfo = null;
		PageInfo<DetailVo> detailPageInfo = null;
		PageInfo<VodVo> vodPageInfo = null;
		PageInfo<FileVo> filePageInfo = null;
		PageInfo<DetailVo> dataPageInfo = null;
		if(StrUtil.isEmpty(req.get("scWord"))) {
			
		}else {
			if("menu".equals(req.get("scType"))) {//메뉴
				menuList = mainSearchService.menuList(req);
				menuPageInfo = new PageInfo<MenuVo>(menuList);
			}else if("post".equals(req.get("scType"))) {//게시물
				postList = mainSearchService.postList(req);
				postPageInfo = new PageInfo<PostVo>(postList);
			}else if("edu".equals(req.get("scType"))) {//실감훈련
				detailList = mainSearchService.detailList(req);
				detailPageInfo = new PageInfo<DetailVo>(detailList);
			}else if("vod".equals(req.get("scType"))) {//영상교육
				vodList = mainSearchService.vodList(req);
				vodPageInfo = new PageInfo<VodVo>(vodList);
			}else if("file".equals(req.get("scType"))) {//첨부파일
				fileList = mainSearchService.fileList(req);
				filePageInfo = new PageInfo<FileVo>(fileList); 
			}else if("all".equals(req.get("scType"))){//전체
				menuList = mainSearchService.menuList(req);
				menuPageInfo = new PageInfo<MenuVo>(menuList);
				postList = mainSearchService.postList(req);
				postPageInfo = new PageInfo<PostVo>(postList);
				detailList = mainSearchService.detailList(req);
				detailPageInfo = new PageInfo<DetailVo>(detailList);
				vodList = mainSearchService.vodList(req);
				vodPageInfo = new PageInfo<VodVo>(vodList);
				fileList = mainSearchService.fileList(req);
				filePageInfo = new PageInfo<FileVo>(fileList);
			}
			req.put("insertIdx", StrUtil.getSessionIdx());
			req.put("insertIp", StrUtil.getClientIP());
			mainSearchService.insert(req);
		}
		//게시글 내용 내부의 태그들 제거
		if (postList != null) {
		    for (PostVo postVo : postList) {
		        if (postVo != null && postVo.getPostContent() != null) {
		            // HTML 태그 제거
		            String cleanedContent = removeHtmlTags(postVo.getPostContent());
		            postVo.setPostContent(cleanedContent);
		        }
		    }
		}

		//인기검색어 목록
		req.put("pageSize", 6);
		List<MainSearchVo> dayHistory = mainSearchService.dayHistory(req);
		List<MainSearchVo> weekHistory = mainSearchService.weekHistory(req);
		
		model.addAttribute("req", req);
		model.addAttribute("dayHistory", dayHistory);
		model.addAttribute("weekHistory", weekHistory);
		model.addAttribute("menuList", menuList);
		model.addAttribute("postList", postList);
		model.addAttribute("detailList", detailList);
		model.addAttribute("vodList", vodList);
		model.addAttribute("fileList", fileList);
		model.addAttribute("dataList", dataList);
		model.addAttribute("menuPageInfo", menuPageInfo);
		model.addAttribute("postPageInfo", postPageInfo);
		model.addAttribute("detailPageInfo", detailPageInfo);
		model.addAttribute("vodPageInfo", vodPageInfo);
		model.addAttribute("filePageInfo", filePageInfo);
		model.addAttribute("dataPageInfo", dataPageInfo);
		
		
		String viewName = "pages/" + siteVo.getSite() + "/main/" + pageVo.getPageCd();
		
		return viewName;
	}
	/**
	 * 정규식을 사용하여 HTML 태그 제거하는 메서드
	 * @param input
	 * @return
	 */
	private static String removeHtmlTags(String input) {
	    if (input == null) {
	        return null;
	    }

	    // 정규식 패턴
	    String htmlPattern = "<[^>]*>";

	    // 정규식을 이용한 HTML 태그 제거
	    Pattern pattern = Pattern.compile(htmlPattern);
	    Matcher matcher = pattern.matcher(input);
	    return matcher.replaceAll("");
	}
	
	/**
	 * 통합검색 첨부파일 다운로드
	 * @param req
	 * @param model
	 * @return 
	 * @throws 
	 */
	@RequestMapping(value = "/search/download")
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