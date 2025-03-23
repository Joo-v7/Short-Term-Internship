package kepco.common.file;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kepco.cms.board.post.file.FileVo;
import kepco.util.StrUtil;
import kepco.util.Thumbnail;

/**
 * 
 *
 */
@Service
public class LocalFileService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${global.file.base-path}")
	private Path basePath;
	
	private final String THUMB_SUFFIX = ".thumb";
	
	Thumbnail thumbnail = new Thumbnail();
	
	public FileInfo upload(@NonNull MultipartFile file, @NonNull String addPath) {
		// TODO: 파일 크기, 종류에 따른 제한, 허용 조건
		// TODO: 이미지의 썸네일 생성, 썸네일 생성 조건
		// TODO: UploadOption 만들어서 추가하기
		
		String fileName = file.getOriginalFilename();
		String fileBaseName = FilenameUtils.getBaseName(fileName);
		String fileExtension = FilenameUtils.getExtension(fileName);
		String uuid = UUID.randomUUID().toString();
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setOrigFileName(fileName);
		fileInfo.setSaveFileName(uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : ""));
		fileInfo.setContentType(file.getContentType());
		fileInfo.setSize(file.getSize());
		
		Path fullPath = this.basePath.resolve(addPath);
		try {
			Files.createDirectories(fullPath);
		} catch (IOException e) {
			log.error(e.getMessage());
			// TODO: 뭐 때문에 파일업로드가 실패한거지?
			return new FileInfo();
		}
		
		// UNDONE: try/catch로 묶는게 나으려나?
		try {
			Files.copy(file.getInputStream(), this.basePath.resolve(addPath).resolve(fileInfo.getSaveFileName()));
		} catch (IOException e) {
			log.error(e.getMessage());
			// TODO: 뭐 때문에 파일업로드가 실패한거지?
			return new FileInfo();
		}
		
		try {
			Files.copy(file.getInputStream(), this.basePath.resolve(addPath).resolve(fileInfo.getSaveFileName()));
			//썸네일 생성
			if(file.getContentType().contains("image") == true){
				thumbnail.setThumbnail(this.basePath.resolve(addPath) + "/" + fileInfo.getSaveFileName(), this.basePath.resolve(addPath) + "/" + "thumb_" + fileInfo.getSaveFileName(), 600, 0); // 원본, 뉴파일명, 새로운 너비, 새로운 높이(0:비율값)
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return fileInfo;
	}

	public FileVo upload2(EgovMap req, @NonNull MultipartFile file, @NonNull String addPath) {
		// TODO: 파일 크기, 종류에 따른 제한, 허용 조건
		// TODO: 이미지의 썸네일 생성, 썸네일 생성 조건
		// TODO: UploadOption 만들어서 추가하기
		
		String fileName = file.getOriginalFilename();
		String fileBaseName = FilenameUtils.getBaseName(fileName);
		String fileExtension = FilenameUtils.getExtension(fileName);
		String uuid = UUID.randomUUID().toString();
		
		FileVo fileVo = new FileVo();
		fileVo.setPostIdx(StrUtil.toInt(req.get("postIdx")));
		fileVo.setBoardIdx(StrUtil.toInt(req.get("boardIdx")));
		fileVo.setFileOriginName(fileName);
		fileVo.setFileSaveName(uuid + (!StrUtil.isEmpty(fileExtension) ? "." + fileExtension : ""));
		fileVo.setFileTrust(file.getContentType());
		fileVo.setFileFilesize(file.getSize());
		
		Path fullPath = this.basePath.resolve(addPath);
		try {
			Files.createDirectories(fullPath);
		} catch (IOException e) {
			log.error(e.getMessage());
			// TODO: 뭐 때문에 파일업로드가 실패한거지?
			return new FileVo();
		}
		
		// UNDONE: try/catch로 묶는게 나으려나?
		try {
			Files.copy(file.getInputStream(), this.basePath.resolve(addPath).resolve(fileVo.getFileSaveName()));
		} catch (IOException e) {
			log.error(e.getMessage());
			// TODO: 뭐 때문에 파일업로드가 실패한거지?
			return new FileVo();
		}
		
		try {
			Files.copy(file.getInputStream(), this.basePath.resolve(addPath).resolve(fileVo.getFileSaveName()));
			//썸네일 생성
			if(file.getContentType().contains("image") == true){
				thumbnail.setThumbnail(this.basePath.resolve(addPath) + "/" + fileVo.getFileSaveName(), this.basePath.resolve(addPath) + "/" + "thumb_" + fileVo.getFileSaveName(), 600, 0); // 원본, 뉴파일명, 새로운 너비, 새로운 높이(0:비율값)
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		return fileVo;
	}
	
	public ResponseEntity<Resource> download(@NonNull String saveFileName, @NonNull String origFileName) {
		
		Resource file;
		try {
			file = getFileResource(saveFileName);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		
		Boolean force  = true;
		if(force) {
		
		}
		
		String downloadFileName = FilenameUtils.getName(origFileName);
		downloadFileName = URLEncoder.encode(downloadFileName, StandardCharsets.UTF_8).replace("+", "%20");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFileName + "\"").body(file);
	}
	
	public Resource getFileResource(String saveFileName) throws FileNotFoundException {
		String fullFilePath = this.basePath.resolve(saveFileName).toString();
		
		File file = new File(fullFilePath);
		if(!file.exists()) {
			throw new FileNotFoundException(saveFileName);
		}
		
		if(!file.isFile()) {
			throw new FileNotFoundException(saveFileName);
		}
		return new InputStreamResource(new FileInputStream(file));
	}
	
	public Resource getThumbResource(String saveFileName) throws FileNotFoundException {
		String fileBaseName = FilenameUtils.getBaseName(saveFileName);
		String fileExtension = FilenameUtils.getExtension(saveFileName);
		String filePath = FilenameUtils.getPath(saveFileName);
		
		String fullFilePath = this.basePath.resolve(filePath+fileBaseName+THUMB_SUFFIX+"."+fileExtension).toString();
		File file = new File(fullFilePath);
		
		// 썸네일이 없으면 만들어주는게 필요한가? 파라미터로 크기 받기?
		
		if(!file.exists()) {
			throw new FileNotFoundException(saveFileName);
		}
		
		if(!file.isFile()) {
			throw new FileNotFoundException(saveFileName);
		}
		
		
		return new InputStreamResource(new FileInputStream(file));
	}
	
	public Boolean delete(String savePath, String fileSaveNm) {
		
		String filePath = savePath + fileSaveNm;
	    String fullFilePath = this.basePath.resolve(filePath).toString();
		File file = new File(fullFilePath);
		if(!file.exists()) {
			return false;
		}
		
		if(!file.isFile()) {
			return false;
		}
		
		// 썸네일 파일 제거
	    // 경로와 파일명을 분리
	    String parentPath = file.getParent();
	    String fileName = file.getName();
	    
	    // 파일명 앞에 'thumb_' 추가
	    String thumbnailFileName = "thumb_" + fileName;
	    String thumbnailPath = new File(parentPath, thumbnailFileName).toString();
	    
	    // 썸네일 파일 삭제 시도
	    File thumbnailFile = new File(thumbnailPath);
	    if (thumbnailFile.exists() && thumbnailFile.isFile()) {
	        thumbnailFile.delete();
	    }
	    
		return file.delete();
		
	}
}