package kepco.cms.board.post.file;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

public class FileVo {
	
	private int fileIdx;
	private int postIdx;
	private int boardIdx;
	private String boardId;
	private String fileOriginName;
	private String fileSaveName;
	private int fileDownload;
	private long fileFilesize;
	private String fileWidth;
	private String fileHeight;
	private String fileType;
	private int fileIsImage;
	private String fileMemo;
	@JsonView(jsonView.PRIVATE.class)
	private int fileSort;
	private String fileThumbSrc;
	private String fileHidden;
	private String fileTrust;
	private int fileView;
	
	private String boardName;
	private String postTitle;
	private String postName;
	
	private String mn;
	
	@JsonView(jsonView.PRIVATE.class)
	private int rn;
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	@JsonView(jsonView.PRIVATE.class)
	private Date insertDate;
	@JsonView(jsonView.PRIVATE.class)
	private int updateIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String updateIp;
	@JsonView(jsonView.PRIVATE.class)
	private String updateDate;
	@JsonView(jsonView.PRIVATE.class)
	private int deleteIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteIp;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteDate;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteYn;
	//
	public int getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	public int getPostIdx() {
		return postIdx;
	}
	public void setPostIdx(int postIdx) {
		this.postIdx = postIdx;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getFileOriginName() {
		return fileOriginName;
	}
	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	public int getFileDownload() {
		return fileDownload;
	}
	public void setFileDownload(int fileDownload) {
		this.fileDownload = fileDownload;
	}
	public long getFileFilesize() {
		return fileFilesize;
	}
	public void setFileFilesize(long fileFilesize) {
		this.fileFilesize = fileFilesize;
	}
	public String getFileWidth() {
		return fileWidth;
	}
	public void setFileWidth(String fileWidth) {
		this.fileWidth = fileWidth;
	}
	public String getFileHeight() {
		return fileHeight;
	}
	public void setFileHeight(String fileHeight) {
		this.fileHeight = fileHeight;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getFileIsImage() {
		return fileIsImage;
	}
	public void setFileIsImage(int fileIsImage) {
		this.fileIsImage = fileIsImage;
	}
	public String getFileMemo() {
		return fileMemo;
	}
	public void setFileMemo(String fileMemo) {
		this.fileMemo = fileMemo;
	}
	public int getFileSort() {
		return fileSort;
	}
	public void setFileSort(int fileSort) {
		this.fileSort = fileSort;
	}
	public String getFileThumbSrc() {
		return fileThumbSrc;
	}
	public void setFileThumbSrc(String fileThumbSrc) {
		this.fileThumbSrc = fileThumbSrc;
	}
	public String getFileHidden() {
		return fileHidden;
	}
	public void setFileHidden(String fileHidden) {
		this.fileHidden = fileHidden;
	}
	public String getFileTrust() {
		return fileTrust;
	}
	public void setFileTrust(String fileTrust) {
		this.fileTrust = fileTrust;
	}
	public int getFileView() {
		return fileView;
	}
	public void setFileView(int fileView) {
		this.fileView = fileView;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	public int getInsertIdx() {
		return insertIdx;
	}
	public void setInsertIdx(int insertIdx) {
		this.insertIdx = insertIdx;
	}
	public String getInsertIp() {
		return insertIp;
	}
	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public int getUpdateIdx() {
		return updateIdx;
	}
	public void setUpdateIdx(int updateIdx) {
		this.updateIdx = updateIdx;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getDeleteIdx() {
		return deleteIdx;
	}
	public void setDeleteIdx(int deleteIdx) {
		this.deleteIdx = deleteIdx;
	}
	public String getDeleteIp() {
		return deleteIp;
	}
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
}