package kepco.cms.common;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.board.post.PostVo;
import kepco.cms.board.post.file.FileService;
import kepco.cms.common.EditorVo;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * 에디터
 */
@Controller
@RequestMapping(value = "/cms/common/")
public class EditorController {
	
	@Autowired
	EditorService editorService;
	
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	private static final String ADD_PATH = "/editor"; 
	
	@RequestMapping(value = "editorSave")
	@ResponseBody
	public JsonResponse editorSave(@RequestEgovMap EgovMap req, Model model, @RequestParam(value = "image", required = false) List<MultipartFile> uploadFileList) throws Exception {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate now = LocalDate.now();
		String formatedNow = now.format(formatter);
		
		String filePath = ADD_PATH + "/" +formatedNow;
		String saveFolder = basePath + filePath;
		
		File Folder = new File(saveFolder);
		if (!Folder.exists()) {
			try{
				Folder.mkdir();
			} 
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if (uploadFileList.size() == 0) {
			return new JsonResponse.Builder().model(model).build();
		}
		
		List<Map<String, String>> list = new ArrayList<>();
		
		for(MultipartFile uploadFile : uploadFileList){
			EditorVo editorVo = new EditorVo();
			
			String origFilename = uploadFile.getOriginalFilename();
			
			String fileBaseName = FilenameUtils.getBaseName(origFilename);
			String fileExtension = FilenameUtils.getExtension(origFilename);
			
			if("blob".equals(origFilename)) {
				fileExtension = getFileExt(uploadFile.getContentType());
			}
			
			if(StrUtil.isBlank(fileExtension) || "tmp".equals(fileExtension)) {
				continue;
			}
			
			String fileName = UUID.randomUUID().toString() + "." + fileExtension;
			
			editorVo.setFilePath(filePath);
			editorVo.setFullPath(filePath+"/"+fileName);
			editorVo.setFileSize(uploadFile.getSize());
			editorVo.setFileName(fileName);
			editorVo.setOrigName(origFilename);
			editorVo.setFileExt(fileExtension);
			
			if(uploadFile.getContentType().startsWith("image") == false) {	
				editorVo.setIsImage(0);
			}else {
				editorVo.setIsImage(1);
			}
			
			uploadFile.transferTo(new File(saveFolder+"/"+fileName));
			
			int result = editorService.insert(editorVo);
			
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("fileSrc", "/datas" + editorVo.getFullPath());
			list.add(resultMap);
				
		}
		
		model.addAttribute("list", list);
		
		return new JsonResponse.Builder().model(model).build();
		
	}
	
	
	private String getFileExt(String contentType) {
		if(contentType == null || !contentType.startsWith("image/")) {
			return "tmp";
		}
		String fileExt = contentType.split("/")[1];
		
		if("jpeg".equals(fileExt)) {
			return "jpg";
		}
		
		if(fileExt.startsWith("svg")) {
			return "svg";
		}
		
		if(fileExt.endsWith("icon")) {
			return "ico";
		}
		
		return fileExt;
	}
}