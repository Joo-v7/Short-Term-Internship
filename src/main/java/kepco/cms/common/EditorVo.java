package kepco.cms.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EditorVo {
	
	private int fileIdx;
	
	/** 파일명 */
	private String fileName;
	
	/** 파일경로 */
	private String filePath;
	
	/** 파일경로 + 파일명 */
	private String fullPath;
	
	/** 저장전 원본파일명 */
	private String origName;
	
	/** 파일 확장자 */
	private String fileExt;
	
	/** 파일 크기 */
	private long fileSize;
	
	/** 이미지 여부(1:이미지, 0:일반) */
	private int isImage;
	
	private String imageWidth;
	private String imageHeight;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fileInsertDate;

	public int getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public int getIsImage() {
		return isImage;
	}

	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public Date getFileInsertDate() {
		return fileInsertDate;
	}

	public void setFileInsertDate(Date fileInsertDate) {
		this.fileInsertDate = fileInsertDate;
	}

}