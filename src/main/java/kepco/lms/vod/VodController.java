package kepco.lms.vod;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

import kepco.cms.board.Criteria;
import kepco.cms.code.CodeService;
import kepco.cms.code.CodeVo;
import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.vod.bundle.BundleService;
import kepco.lms.vod.bundle.BundleVo;
import kepco.lms.vod.content.ContentService;
import kepco.util.StrUtil;
/**
 * 영상교육
 */
@Controller
@RequestMapping(value = "/lms/vod")
public class VodController {
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	VodService vodService;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	BundleService bundleService;

	@Autowired
	MemberService memberService;
	
	@Autowired
	LocalFileService localFileService;
	
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

		String viewName = "lms/vod/dashboard";
		
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

		String viewName = "lms/vod/list";
		
		req.put("codeParent", "vod");
		List<CodeVo> codeList = codeService.selectCodeList(req);
		
		req.put("lms", "lms");
		req.put("pageSize" , "6");
		List<VodVo> list = vodService.selectList(req);
		
		PageInfo<VodVo> pageInfo = new PageInfo<VodVo>(list);
		
		model.addAttribute("codeList", codeList);
		model.addAttribute("req", req);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);

		return viewName;
	}
	/**
	 * 영상교육 파일 목록
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "fileList")
	@ResponseBody
	protected JsonResponse fileList(@RequestEgovMap EgovMap req, Model model){
		
		int memberIdx = StrUtil.getSessionIdx();
		req.put("memberIdx", memberIdx);

		List<VodVo> list = vodService.myListSelect(req);
		
		model.addAttribute("req",req);
		model.addAttribute("list",list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 메인페이지 영상 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mainList")
	public String mainList(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "lms/vod/main/mainList";
		
		req.put("codeParent", "vod");
		List<CodeVo> codeList = codeService.selectCodeList(req);
		req.put("lms", "lms");
//		req.put("pageSize" , "4");
		List<VodVo> vodList = vodService.selectList(req);

		model.addAttribute("req", req);
		model.addAttribute("vodList", vodList);

		return viewName;
	}
	/**
	 * 영상교육 조회
	 * @param req
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "lms/vod/view";
		
		VodVo vo = null;
		List<BundleVo> bundleList = null;
		
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			vo = vodService.select(req);
			
			//콘텐츠 리스트
			req.put("vodIdx",vo.getVodIdx());
			bundleList = bundleService.selectAll(req);		
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		model.addAttribute("bundleList",bundleList);
		
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
		
		String viewName = "lms/vod/memberList";
		
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
		
		String viewName = "lms/vod/form";
		
		VodVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			vo = vodService.select(req);
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
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		VodVo vodVo = mapper.convertValue(req, VodVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("vodIdx"))) {
			vodVo.setInsertIdx(StrUtil.getSessionIdx());
			vodVo.setInsertIp(StrUtil.getClientIP());
			result = vodService.insert(vodVo);
		}else {
			vodVo.setUpdateIdx(StrUtil.getSessionIdx());
			vodVo.setUpdateIp(StrUtil.getClientIP());
			result = vodService.update(vodVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(vodVo);

		return new JsonResponse.Builder().data(model).build();
		//return "redirect:list";
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

		int result = 0 ;
		
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
	 * 영상교육 썸네일
	 * @param req
	 * @param request
	 * @param response
	 * @throws Throwable
	 */
	@RequestMapping(value = "file")
	protected void file(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		VodVo vo = null;
		String img_file = "";
		
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			vo = vodService.select(req);
		}
		
		img_file = StrUtil.noNull(vo.getVodImage());
		
		if(vo == null){
		}else{
			if(!img_file.equals("")){
				// 불러와야할 이미지에 대한 식별값을 파라미터로 받아옴.
				File file = new File((basePath + "/" + FILE_PATH  + "thumb_" + img_file));
				if(file == null) {
					 file = new File((basePath + "/" + FILE_PATH  + img_file));
				}
				FileInputStream fis = null;

				response.setHeader("Content-type", "image/jpeg");
				response.setHeader("Content-Transfer-Encoding", "binary");
			  
				BufferedInputStream in = null;
				ByteArrayOutputStream bStream = null;
				try{
					fis = new FileInputStream(file);
					in = new BufferedInputStream(fis);
					bStream = new ByteArrayOutputStream();
					int imgByte;
					while ((imgByte = in.read()) != -1) {
						bStream.write(imgByte);
					}
					response.setContentLength(bStream.size());
					bStream.writeTo(response.getOutputStream());
					response.getOutputStream().flush();
					response.getOutputStream().close();
				} catch (Throwable e) {  
					img_file = "";
				} finally {
					if (bStream != null) {
						try { bStream.close(); } catch (Throwable e) {  }
					}
					if (in != null) {
						try {in.close();}catch (Throwable e) {   }
					}
					if (fis != null) {
						try {fis.close();}catch (Throwable e) {  }
					}
				}
			}
		}
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
	protected ResponseEntity<Resource> download(@RequestEgovMap EgovMap req, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		VodVo vo = null;
		String img_file = "";
		
		if(!StrUtil.isEmpty(req.get("vodIdx"))) {
			vo = vodService.fileSelect(req);
		}
		
		if("vodImage".equals(req.get("file"))) {
			img_file = StrUtil.noNull(vo.getVodImage());
		}else if("vodFile".equals(req.get("file"))) {
			img_file = StrUtil.noNull(vo.getVodFile());
		}
		
		if(vo == null){
		}else{
			return localFileService.download(FILE_PATH + img_file, img_file);
			}
		return null;
	}
	
	/**
	 * 영상교육 프린트
	 * @param req
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "myPrint")
	public String myprint(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "lms/vod/my/print";
		
		int memberIdx = StrUtil.getSessionIdx();
		
		
		req.put("memberIdx", memberIdx);
		
		
		Object vo = vodService.myPrintSelect(req);
		
		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 내 영상교육 목록
	 * @param req
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "myList")
	public String mylist(@RequestEgovMap EgovMap req, Criteria cri, Model model) throws Exception {
		
		String viewName = "lms/vod/my/list";
		
		int memberIdx = StrUtil.getSessionIdx();
		
		req.put("memberIdx", memberIdx);
		req.put("pageSize" , "5");
		
		List<VodVo> list = vodService.myListSelect(req);
		PageInfo<VodVo> pageInfo = new PageInfo<VodVo>(list);
		
		model.addAttribute("req", req);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		
		return viewName;
	}
	
}