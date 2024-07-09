package kepco.cms.zzz.multipart;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import kepco.common.file.FileInfo;
import kepco.common.file.LocalFileService;
import kepco.common.web.RequestEgovMap;

@Controller
@RequestMapping(value = "cms/zzz/multipart")
public class MultipartController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final static String FILE_PATH = "multipart"; 
	
	@Autowired
	LocalFileService localFileService;
	
	
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) {
		
		model.addAttribute("req", req);
		
		return "cms/zzz/multipart/form";

	}
	
	@RequestMapping(value = "save")
	public String save(@RequestEgovMap EgovMap req, @RequestParam(required=false) MultipartFile[] files, Model model) {
		
		ServletContext servletContext = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext();
		model.addAttribute("req", req);
		
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		// 첨부파일이 없는 경우도 있으니...
		if (files != null) {
			for (MultipartFile file : files) {
				FileInfo fileInfo = localFileService.upload(file, FILE_PATH);
				fileInfoList.add(fileInfo);
				log.debug(fileInfo.toString());
			}
		}
		
		model.addAttribute("fileInfoList", fileInfoList);
		
		return "cms/zzz/multipart/save";
	}
	
	@RequestMapping(value = "download")
	@ResponseBody
	public ResponseEntity<Resource> download(@RequestParam("saveFileName") String saveFileName, @RequestParam("origFileName") String origFileName) {
		// 어찌저찌해서 파일 다운로드
		return localFileService.download(FILE_PATH + "/" + saveFileName, origFileName);
	}
}
