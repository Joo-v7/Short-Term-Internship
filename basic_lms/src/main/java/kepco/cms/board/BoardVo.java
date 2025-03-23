package kepco.cms.board;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardVo {
	
	private int boardIdx;
	private int siteIdx;
	private String boardId;
	private String boardName;
	private int boardOrder;
	private String boardSearch;
	private String boardSkin;
	private int boardUploadSize;
	private int boardThumbWidth;
	private int boardThumbHeight;
	private String boardUseComment;
	private String boardUseReply;
	private String boardUseSecret;
	private String boardUseCate;
	private String boardUseRss;
	private String boardUseEditor;
	private String boardHistory;
	
	/** 게시판 관리자 아이디 */
	private String boardAdminMemberId;
	
	//학습자 게시판 권한
	private String boardAuthListUser;
	private String boardAuthReadUser;
	private String boardAuthWriteUser;
	private String boardAuthUploadUser;
	private String boardAuthDownloadUser;
	private String boardAuthCommentUser;
	private String boardAuthReplyUser;
	
	//관리자 공통 권한
	private String boardAuthListAdmin;
	private String boardAuthReadAdmin;
	private String boardAuthWriteAdmin;
	private String boardAuthUploadAdmin;
	private String boardAuthDownloadAdmin;
	private String boardAuthCommentAdmin;
	private String boardAuthReplyAdmin;
	private int boardPageLists;
	private int boardPageLines;
	private String boardSortList;
	private String boardContentDefault;
	private String boardUseUpload;
	private String boardUseFileInfo;
	private int boardUploadCount;
	private String boardUseThumb;
	private String boardUseName;
	private String boardUseViewCount;
	private String boardUseInsertDate;
	private String boardUseEmail;
	private String boardUseLike;
	private String boardUseLink;
	private String boardUseNickname;
	private String boardUseTel;
	private String boardUseAddress;
	private String boardUseCaptcha;
	private String boardUseDownload;
	private int boardUseCaptchaRadio;
	private String boardCategory;
	private String boardUsePriority;
	
	private int rn;
	
	private int postCnt;
	private int fileCnt;
	
	
	private int insertIdx;
	private String insertIp;
	private String insertDate;
	private int updateIdx;
	private String updateIp;
	private String updateDate;
	private int deleteIdx;
	private String deleteIp;
	private String deleteDate;
	private String deleteYn;
	
	//비회원 권한
	private String boardAuthListNonMember;
	private String boardAuthReadNonMember;
	private String boardAuthWriteNonMember;
	private String boardAuthUploadNonMember;
	private String boardAuthDownloadNonMember;
	private String boardAuthCommentNonMember;
	private String boardAuthReplyNonMember;
	
	//cms 관리자 권한
	private String boardAuthListCmsAdmin;
	private String boardAuthReadCmsAdmin;
	private String boardAuthWriteCmsAdmin;
	private String boardAuthUploadCmsAdmin;
	private String boardAuthDownloadCmsAdmin;
	private String boardAuthCommentCmsAdmin;
	private String boardAuthReplyCmsAdmin;
	
	//lms 관리자 권한
	private String boardAuthListLmsAdmin;
	private String boardAuthReadLmsAdmin;
	private String boardAuthWriteLmsAdmin;
	private String boardAuthUploadLmsAdmin;
	private String boardAuthDownloadLmsAdmin;
	private String boardAuthCommentLmsAdmin;
	private String boardAuthReplyLmsAdmin;
	
	//ras 관리자 권한
	private String boardAuthListRasAdmin;
	private String boardAuthReadRasAdmin;
	private String boardAuthWriteRasAdmin;
	private String boardAuthUploadRasAdmin;
	private String boardAuthDownloadRasAdmin;
	private String boardAuthCommentRasAdmin;
	private String boardAuthReplyRasAdmin;
	
	public String getBoardAuthListCmsAdmin() {
		return boardAuthListCmsAdmin;
	}
	public void setBoardAuthListCmsAdmin(String boardAuthListCmsAdmin) {
		this.boardAuthListCmsAdmin = boardAuthListCmsAdmin;
	}
	public String getBoardAuthReadCmsAdmin() {
		return boardAuthReadCmsAdmin;
	}
	public void setBoardAuthReadCmsAdmin(String boardAuthReadCmsAdmin) {
		this.boardAuthReadCmsAdmin = boardAuthReadCmsAdmin;
	}
	public String getBoardAuthWriteCmsAdmin() {
		return boardAuthWriteCmsAdmin;
	}
	public void setBoardAuthWriteCmsAdmin(String boardAuthWriteCmsAdmin) {
		this.boardAuthWriteCmsAdmin = boardAuthWriteCmsAdmin;
	}
	public String getBoardAuthUploadCmsAdmin() {
		return boardAuthUploadCmsAdmin;
	}
	public void setBoardAuthUploadCmsAdmin(String boardAuthUploadCmsAdmin) {
		this.boardAuthUploadCmsAdmin = boardAuthUploadCmsAdmin;
	}
	public String getBoardAuthDownloadCmsAdmin() {
		return boardAuthDownloadCmsAdmin;
	}
	public void setBoardAuthDownloadCmsAdmin(String boardAuthDownloadCmsAdmin) {
		this.boardAuthDownloadCmsAdmin = boardAuthDownloadCmsAdmin;
	}
	public String getBoardAuthCommentCmsAdmin() {
		return boardAuthCommentCmsAdmin;
	}
	public void setBoardAuthCommentCmsAdmin(String boardAuthCommentCmsAdmin) {
		this.boardAuthCommentCmsAdmin = boardAuthCommentCmsAdmin;
	}
	public String getBoardAuthReplyCmsAdmin() {
		return boardAuthReplyCmsAdmin;
	}
	public void setBoardAuthReplyCmsAdmin(String boardAuthReplyCmsAdmin) {
		this.boardAuthReplyCmsAdmin = boardAuthReplyCmsAdmin;
	}
	public String getBoardAuthListRasAdmin() {
		return boardAuthListRasAdmin;
	}
	public void setBoardAuthListRasAdmin(String boardAuthListRasAdmin) {
		this.boardAuthListRasAdmin = boardAuthListRasAdmin;
	}
	public String getBoardAuthReadRasAdmin() {
		return boardAuthReadRasAdmin;
	}
	public void setBoardAuthReadRasAdmin(String boardAuthReadRasAdmin) {
		this.boardAuthReadRasAdmin = boardAuthReadRasAdmin;
	}
	public String getBoardAuthWriteRasAdmin() {
		return boardAuthWriteRasAdmin;
	}
	public void setBoardAuthWriteRasAdmin(String boardAuthWriteRasAdmin) {
		this.boardAuthWriteRasAdmin = boardAuthWriteRasAdmin;
	}
	public String getBoardAuthUploadRasAdmin() {
		return boardAuthUploadRasAdmin;
	}
	public void setBoardAuthUploadRasAdmin(String boardAuthUploadRasAdmin) {
		this.boardAuthUploadRasAdmin = boardAuthUploadRasAdmin;
	}
	public String getBoardAuthDownloadRasAdmin() {
		return boardAuthDownloadRasAdmin;
	}
	public void setBoardAuthDownloadRasAdmin(String boardAuthDownloadRasAdmin) {
		this.boardAuthDownloadRasAdmin = boardAuthDownloadRasAdmin;
	}
	public String getBoardAuthCommentRasAdmin() {
		return boardAuthCommentRasAdmin;
	}
	public void setBoardAuthCommentRasAdmin(String boardAuthCommentRasAdmin) {
		this.boardAuthCommentRasAdmin = boardAuthCommentRasAdmin;
	}
	public String getBoardAuthReplyRasAdmin() {
		return boardAuthReplyRasAdmin;
	}
	public void setBoardAuthReplyRasAdmin(String boardAuthReplyRasAdmin) {
		this.boardAuthReplyRasAdmin = boardAuthReplyRasAdmin;
	}
	public String getBoardAuthListLmsAdmin() {
		return boardAuthListLmsAdmin;
	}
	public void setBoardAuthListLmsAdmin(String boardAuthListLmsAdmin) {
		this.boardAuthListLmsAdmin = boardAuthListLmsAdmin;
	}
	public String getBoardAuthReadLmsAdmin() {
		return boardAuthReadLmsAdmin;
	}
	public void setBoardAuthReadLmsAdmin(String boardAuthReadLmsAdmin) {
		this.boardAuthReadLmsAdmin = boardAuthReadLmsAdmin;
	}
	public String getBoardAuthWriteLmsAdmin() {
		return boardAuthWriteLmsAdmin;
	}
	public void setBoardAuthWriteLmsAdmin(String boardAuthWriteLmsAdmin) {
		this.boardAuthWriteLmsAdmin = boardAuthWriteLmsAdmin;
	}
	public String getBoardAuthUploadLmsAdmin() {
		return boardAuthUploadLmsAdmin;
	}
	public void setBoardAuthUploadLmsAdmin(String boardAuthUploadLmsAdmin) {
		this.boardAuthUploadLmsAdmin = boardAuthUploadLmsAdmin;
	}
	public String getBoardAuthDownloadLmsAdmin() {
		return boardAuthDownloadLmsAdmin;
	}
	public void setBoardAuthDownloadLmsAdmin(String boardAuthDownloadLmsAdmin) {
		this.boardAuthDownloadLmsAdmin = boardAuthDownloadLmsAdmin;
	}
	public String getBoardAuthCommentLmsAdmin() {
		return boardAuthCommentLmsAdmin;
	}
	public void setBoardAuthCommentLmsAdmin(String boardAuthCommentLmsAdmin) {
		this.boardAuthCommentLmsAdmin = boardAuthCommentLmsAdmin;
	}
	public String getBoardAuthReplyLmsAdmin() {
		return boardAuthReplyLmsAdmin;
	}
	public void setBoardAuthReplyLmsAdmin(String boardAuthReplyLmsAdmin) {
		this.boardAuthReplyLmsAdmin = boardAuthReplyLmsAdmin;
	}
	public String getBoardAuthListNonMember() {
		return boardAuthListNonMember;
	}
	public void setBoardAuthListNonMember(String boardAuthListNonMember) {
		this.boardAuthListNonMember = boardAuthListNonMember;
	}
	public String getBoardAuthReadNonMember() {
		return boardAuthReadNonMember;
	}
	public void setBoardAuthReadNonMember(String boardAuthReadNonMember) {
		this.boardAuthReadNonMember = boardAuthReadNonMember;
	}
	public String getBoardAuthWriteNonMember() {
		return boardAuthWriteNonMember;
	}
	public void setBoardAuthWriteNonMember(String boardAuthWriteNonMember) {
		this.boardAuthWriteNonMember = boardAuthWriteNonMember;
	}
	public String getBoardAuthUploadNonMember() {
		return boardAuthUploadNonMember;
	}
	public void setBoardAuthUploadNonMember(String boardAuthUploadNonMember) {
		this.boardAuthUploadNonMember = boardAuthUploadNonMember;
	}
	public String getBoardAuthDownloadNonMember() {
		return boardAuthDownloadNonMember;
	}
	public void setBoardAuthDownloadNonMember(String boardAuthDownloadNonMember) {
		this.boardAuthDownloadNonMember = boardAuthDownloadNonMember;
	}
	public String getBoardAuthCommentNonMember() {
		return boardAuthCommentNonMember;
	}
	public void setBoardAuthCommentNonMember(String boardAuthCommentNonMember) {
		this.boardAuthCommentNonMember = boardAuthCommentNonMember;
	}
	public String getBoardAuthReplyNonMember() {
		return boardAuthReplyNonMember;
	}
	public void setBoardAuthReplyNonMember(String boardAuthReplyNonMember) {
		this.boardAuthReplyNonMember = boardAuthReplyNonMember;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public String getBoardUsePriority() {
		return boardUsePriority;
	}
	public void setBoardUsePriority(String boardUsePriority) {
		this.boardUsePriority = boardUsePriority;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public int getSiteIdx() {
		return siteIdx;
	}
	public void setSiteIdx(int siteIdx) {
		this.siteIdx = siteIdx;
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
	public int getBoardOrder() {
		return boardOrder;
	}
	public void setBoardOrder(int boardOrder) {
		this.boardOrder = boardOrder;
	}
	public String getBoardSearch() {
		return boardSearch;
	}
	public void setBoardSearch(String boardSearch) {
		this.boardSearch = boardSearch;
	}
	public String getBoardSkin() {
		return boardSkin;
	}
	public void setBoardSkin(String boardSkin) {
		this.boardSkin = boardSkin;
	}
	public int getBoardUploadSize() {
		return boardUploadSize;
	}
	public void setBoardUploadSize(int boardUploadSize) {
		this.boardUploadSize = boardUploadSize;
	}
	public int getBoardThumbWidth() {
		return boardThumbWidth;
	}
	public void setBoardThumbWidth(int boardThumbWidth) {
		this.boardThumbWidth = boardThumbWidth;
	}
	public int getBoardThumbHeight() {
		return boardThumbHeight;
	}
	public void setBoardThumbHeight(int boardThumbHeight) {
		this.boardThumbHeight = boardThumbHeight;
	}
	public String getBoardUseComment() {
		return boardUseComment;
	}
	public void setBoardUseComment(String boardUseComment) {
		this.boardUseComment = boardUseComment;
	}
	public String getBoardUseReply() {
		return boardUseReply;
	}
	public void setBoardUseReply(String boardUseReply) {
		this.boardUseReply = boardUseReply;
	}
	public String getBoardUseSecret() {
		return boardUseSecret;
	}
	public void setBoardUseSecret(String boardUseSecret) {
		this.boardUseSecret = boardUseSecret;
	}
	public String getBoardUseCate() {
		return boardUseCate;
	}
	public void setBoardUseCate(String boardUseCate) {
		this.boardUseCate = boardUseCate;
	}
	public String getBoardUseRss() {
		return boardUseRss;
	}
	public void setBoardUseRss(String boardUseRss) {
		this.boardUseRss = boardUseRss;
	}
	public String getBoardUseEditor() {
		return boardUseEditor;
	}
	public void setBoardUseEditor(String boardUseEditor) {
		this.boardUseEditor = boardUseEditor;
	}
	public String getBoardHistory() {
		return boardHistory;
	}
	public void setBoardHistory(String boardHistory) {
		this.boardHistory = boardHistory;
	}
	public String getBoardAuthListUser() {
		return boardAuthListUser;
	}
	public void setBoardAuthListUser(String boardAuthListUser) {
		this.boardAuthListUser = boardAuthListUser;
	}
	public String getBoardAuthReadUser() {
		return boardAuthReadUser;
	}
	public void setBoardAuthReadUser(String boardAuthReadUser) {
		this.boardAuthReadUser = boardAuthReadUser;
	}
	public String getBoardAuthWriteUser() {
		return boardAuthWriteUser;
	}
	public void setBoardAuthWriteUser(String boardAuthWriteUser) {
		this.boardAuthWriteUser = boardAuthWriteUser;
	}
	public String getBoardAuthUploadUser() {
		return boardAuthUploadUser;
	}
	public void setBoardAuthUploadUser(String boardAuthUploadUser) {
		this.boardAuthUploadUser = boardAuthUploadUser;
	}
	public String getBoardAuthDownloadUser() {
		return boardAuthDownloadUser;
	}
	public void setBoardAuthDownloadUser(String boardAuthDownloadUser) {
		this.boardAuthDownloadUser = boardAuthDownloadUser;
	}
	public String getBoardAuthCommentUser() {
		return boardAuthCommentUser;
	}
	public void setBoardAuthCommentUser(String boardAuthCommentUser) {
		this.boardAuthCommentUser = boardAuthCommentUser;
	}
	public String getBoardAuthReplyUser() {
		return boardAuthReplyUser;
	}
	public void setBoardAuthReplyUser(String boardAuthReplyUser) {
		this.boardAuthReplyUser = boardAuthReplyUser;
	}
	public String getBoardAuthListAdmin() {
		return boardAuthListAdmin;
	}
	public void setBoardAuthListAdmin(String boardAuthListAdmin) {
		this.boardAuthListAdmin = boardAuthListAdmin;
	}
	public String getBoardAuthReadAdmin() {
		return boardAuthReadAdmin;
	}
	public void setBoardAuthReadAdmin(String boardAuthReadAdmin) {
		this.boardAuthReadAdmin = boardAuthReadAdmin;
	}
	public String getBoardAuthWriteAdmin() {
		return boardAuthWriteAdmin;
	}
	public void setBoardAuthWriteAdmin(String boardAuthWriteAdmin) {
		this.boardAuthWriteAdmin = boardAuthWriteAdmin;
	}
	public String getBoardAuthUploadAdmin() {
		return boardAuthUploadAdmin;
	}
	public void setBoardAuthUploadAdmin(String boardAuthUploadAdmin) {
		this.boardAuthUploadAdmin = boardAuthUploadAdmin;
	}
	public String getBoardAuthDownloadAdmin() {
		return boardAuthDownloadAdmin;
	}
	public void setBoardAuthDownloadAdmin(String boardAuthDownloadAdmin) {
		this.boardAuthDownloadAdmin = boardAuthDownloadAdmin;
	}
	public String getBoardAuthCommentAdmin() {
		return boardAuthCommentAdmin;
	}
	public void setBoardAuthCommentAdmin(String boardAuthCommentAdmin) {
		this.boardAuthCommentAdmin = boardAuthCommentAdmin;
	}
	public String getBoardAuthReplyAdmin() {
		return boardAuthReplyAdmin;
	}
	public void setBoardAuthReplyAdmin(String boardAuthReplyAdmin) {
		this.boardAuthReplyAdmin = boardAuthReplyAdmin;
	}
	public int getBoardUseCaptchaRadio() {
		return boardUseCaptchaRadio;
	}
	public void setBoardUseCaptchaRadio(int boardUseCaptchaRadio) {
		this.boardUseCaptchaRadio = boardUseCaptchaRadio;
	}
	public int getBoardPageLists() {
		return boardPageLists;
	}
	public void setBoardPageLists(int boardPageLists) {
		this.boardPageLists = boardPageLists;
	}
	public int getBoardPageLines() {
		return boardPageLines;
	}
	public void setBoardPageLines(int boardPageLines) {
		this.boardPageLines = boardPageLines;
	}
	public String getBoardSortList() {
		return boardSortList;
	}
	public void setBoardSortList(String boardSortList) {
		this.boardSortList = boardSortList;
	}
	public String getBoardContentDefault() {
		return boardContentDefault;
	}
	public void setBoardContentDefault(String boardContentDefault) {
		this.boardContentDefault = boardContentDefault;
	}
	public String getBoardUseUpload() {
		return boardUseUpload;
	}
	public void setBoardUseUpload(String boardUseUpload) {
		this.boardUseUpload = boardUseUpload;
	}
	public String getBoardUseFileInfo() {
		return boardUseFileInfo;
	}
	public void setBoardUseFileInfo(String boardUseFileInfo) {
		this.boardUseFileInfo = boardUseFileInfo;
	}
	public int getBoardUploadCount() {
		return boardUploadCount;
	}
	public void setBoardUploadCount(int boardUploadCount) {
		this.boardUploadCount = boardUploadCount;
	}
	public String getBoardUseThumb() {
		return boardUseThumb;
	}
	public void setBoardUseThumb(String boardUseThumb) {
		this.boardUseThumb = boardUseThumb;
	}
	public String getBoardUseName() {
		return boardUseName;
	}
	public void setBoardUseName(String boardUseName) {
		this.boardUseName = boardUseName;
	}
	public String getBoardUseViewCount() {
		return boardUseViewCount;
	}
	public void setBoardUseViewCount(String boardUseViewCount) {
		this.boardUseViewCount = boardUseViewCount;
	}
	public String getBoardUseInsertDate() {
		return boardUseInsertDate;
	}
	public void setBoardUseInsertDate(String boardUseInsertDate) {
		this.boardUseInsertDate = boardUseInsertDate;
	}
	public String getBoardUseEmail() {
		return boardUseEmail;
	}
	public void setBoardUseEmail(String boardUseEmail) {
		this.boardUseEmail = boardUseEmail;
	}
	public String getBoardUseLike() {
		return boardUseLike;
	}
	public void setBoardUseLike(String boardUseLike) {
		this.boardUseLike = boardUseLike;
	}
	public String getBoardUseLink() {
		return boardUseLink;
	}
	public void setBoardUseLink(String boardUseLink) {
		this.boardUseLink = boardUseLink;
	}
	public String getBoardUseNickname() {
		return boardUseNickname;
	}
	public void setBoardUseNickname(String boardUseNickname) {
		this.boardUseNickname = boardUseNickname;
	}
	public String getBoardUseTel() {
		return boardUseTel;
	}
	public void setBoardUseTel(String boardUseTel) {
		this.boardUseTel = boardUseTel;
	}
	public String getBoardUseAddress() {
		return boardUseAddress;
	}
	public void setBoardUseAddress(String boardUseAddress) {
		this.boardUseAddress = boardUseAddress;
	}
	public String getBoardUseCaptcha() {
		return boardUseCaptcha;
	}
	public void setBoardUseCaptcha(String boardUseCaptcha) {
		this.boardUseCaptcha = boardUseCaptcha;
	}
	public String getBoardUseDownload() {
		return boardUseDownload;
	}
	public void setBoardUseDownload(String boardUseDownload) {
		this.boardUseDownload = boardUseDownload;
	}
	public int getBoardCaptchaRadio() {
		return boardUseCaptchaRadio;
	}
	public void setBoardCaptchaRadio(int boardUseCaptchaRadio) {
		this.boardUseCaptchaRadio = boardUseCaptchaRadio;
	}
	public String getBoardCategory() {
		return boardCategory;
	}
	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	
	public int getPostCnt() {
		return postCnt;
	}
	public void setPostCnt(int postCnt) {
		this.postCnt = postCnt;
	}
	
	public int getFileCnt() {
		return fileCnt;
	}
	public void setFileCnt(int fileCnt) {
		this.fileCnt = fileCnt;
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
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
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
	public String getBoardAdminMemberId() {
		return boardAdminMemberId;
	}
	public void setBoardAdminMemberId(String boardAdminMemberId) {
		this.boardAdminMemberId = boardAdminMemberId;
	}
}