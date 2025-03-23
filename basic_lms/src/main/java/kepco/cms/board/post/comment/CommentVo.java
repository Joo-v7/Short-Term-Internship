package kepco.cms.board.post.comment;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentVo {
	
	private int commentIdx;
	private int postIdx;
	private int boardIdx;
	
	private String commentName;
	private String commentTitle;
	private String commentContent;
	
	private int insertIdx;
	private String insertIp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date insertDate;
	
	private int updateIdx;
	private String updateIp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private String updateDate;
	
	private int deleteIdx;
	private String deleteIp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private String deleteDate;
	
	private String deleteYn;
	
	/** 게시물 제목 */
	private String postTitle;
	/** 게시판명 */
	private String boardName;
	
	/** ROWNUMBER */
	private int rn;

	public int getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(int commentIdx) {
		this.commentIdx = commentIdx;
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

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

	public String getCommentTitle() {
		return commentTitle;
	}

	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
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

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}
	
	
}