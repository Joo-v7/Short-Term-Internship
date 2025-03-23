package kepco.lms.vod.content;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.member.MemberService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 영상교육 관리
 */
@Controller
@RequestMapping(value = "lms/vod/content")
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	MemberService memberService;
	
	private final static String FILE_PATH = "vod/content/"; 
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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

		String viewName = "lms/vod/content/list";
		
		model.addAttribute("req", req);

		return viewName;
	}

	/**
	 * 영상교육 조회
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "view")
	public String view(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "lms/vod/content/view";
		
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
		
		String viewName = "lms/vod/content/form";
		
		ContentVo vo = null;
		
		if(!StrUtil.isEmpty(req.get("contentIdx"))) {
			vo = contentService.select(req);
		}

		model.addAttribute("req",req);
		model.addAttribute("vo",vo);
		
		return viewName;
	}
	
	/**
	 * 영상교육 재생
	 * @param req
	 * @param model
	 * @param request
	 * @param response
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "stream")
	public void stream(@RequestEgovMap EgovMap req, Model model ,HttpServletRequest request , HttpServletResponse response) throws FileNotFoundException {
				
		model.addAttribute("req", req);
		
		ContentVo vo = null;

		if(!StrUtil.isEmpty(req.get("contentIdx"))) {
			vo = contentService.select(req);
		}
		
		File file = new File(basePath + "/" + FILE_PATH + vo.getContentFileName());
		
		RandomAccessFile randomFile = new RandomAccessFile(file, "r"); 
	    
		long rangeStart = 0; //요청 범위의 시작 위치 
	    long rangeEnd = 0; //요청 범위의 끝 위치 
	    boolean isPart=false; //부분 요청일 경우 true, 전체 요청의 경우 false 
	    try{ //동영상 파일 크기 
	        long movieSize = randomFile.length(); //스트림 요청 범위, request의 헤더에서 range를 읽는다. 
	        String range = request.getHeader("range"); 
	        if(range!=null) { 
	              if (range.endsWith("-")) { 
	                   range = range + (movieSize - 1); 
	              } 
	              int idxm = range.trim().indexOf("-"); 
	              rangeStart = Long.parseLong(range.substring(6, idxm)); 
	              rangeEnd = Long.parseLong(range.substring(idxm + 1)); 
	              if (rangeStart > 0) {
	                  isPart = true; 
	              }
	        }
	        else { 
	            rangeStart =0; 
	            rangeEnd = movieSize -1; 
	        } 
	        long partSize = rangeEnd - rangeStart + 1; 
	        response.reset(); 
	        response.setStatus(isPart ? 206 : 200); 
	        response.setContentType("video/mp4"); 
	        response.setHeader("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+movieSize); 
	        response.setHeader("Accept-Ranges", "bytes"); 
	        response.setHeader("Content-Length", ""+partSize); 
	        OutputStream out = response.getOutputStream(); 
	        randomFile.seek(rangeStart); 
	        int bufferSize = 8*1024; 
	        byte[] buf = new byte[bufferSize]; 
	        do{ 
	            int block = partSize > bufferSize ? bufferSize : (int)partSize; 
	            int len = randomFile.read(buf, 0, block); 
	            out.write(buf, 0, len); 
	            partSize -= block; 
	        }while(partSize > 0); 
	    }catch(IOException e){ 
	    }finally{ 
	        try {
				randomFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	}
	
	//파일 읽고 쓰기
	private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
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
		
		req.put("site","site");
		
		List<ContentVo> list = contentService.selectAll(req);
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	/**
	 * 영상교육 저장
	 * @param req
	 * @param files
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile files, Model model) {

		// "Unrecognized field" 무시
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ContentVo contentVo = mapper.convertValue(req, ContentVo.class);

		int result = 0 ;
		
		if(!StrUtil.isEmpty(files)) {
			String uuid = UUID.randomUUID().toString();
			String fileBaseName = FilenameUtils.getBaseName(files.getOriginalFilename()).replaceAll("_", " ");;
			String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());
//			String fileSaveName = fileBaseName + "_" + uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
			String fileSaveName = uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : "");
			contentVo.setContentFileName(fileSaveName);
			contentVo.setContentFileOrigin(files.getOriginalFilename());
			
			Path fullPath = this.basePath.resolve(FILE_PATH);
			
			try {
				Files.createDirectories(fullPath);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			
			try {
				files.transferTo(new File(fullPath+"/"+fileSaveName));
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		
		if(StrUtil.isEmpty(req.get("contentIdx"))) {
			contentVo.setInsertIdx(StrUtil.getSessionIdx());
			contentVo.setInsertIp(StrUtil.getClientIP());
			result = contentService.insert(contentVo);
		}else {
			result = contentService.update(contentVo);
		}
		
		model.addAttribute("resultCnt", result);
		model.addAttribute(contentVo);

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
		
		if (StrUtil.isEmpty(req.get("contentIdx"))) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
		}
		
		result = contentService.delete(req);
		model.addAttribute("resultCnt", result);
		
		return new JsonResponse.Builder().model(model).build();
	}
}