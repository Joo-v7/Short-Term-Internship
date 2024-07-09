package kepco.cms.board.post;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostVo {
	
	private int postIdx;
	private int boardIdx;
	private String boardId;
	
	/** 그룹ID. 게시판별 1부터 MAX()+1 값 */
	private int postGroupId;
	/** 그룹순서. 원본글:0, 답변글:1 */
	private int postGroupOrder;
	/** 그룹깊이. 원본글:0, 답변글:1, 답변글의답변글:2 */
	private int postGroupDepth;
	
	private String postTitle;
	private String postContent;
	private String postCategory;
	
	private String postPassword;
	
	@JsonView(jsonView.PRIVATE.class)
	private int postCommentCount = 0;
	@JsonView(jsonView.PRIVATE.class)
	private int postReplyCount = 0;
	
	/** 비밀글 여부 */
	private String postSecret = "n";
	
	/** 게시물내용 HTML 여부 */
	private int postHtml = 0;
	
	/** 공지사항여부. 0:일반, 1이상:공지 */
	private int postNotice = 0;
	private String postNoticeSdate;
	private String postNoticeEdate;
	
	private int postReceiveEmail;
	private int postHit = 0;
	
	private int postFile;
	private int postImage;
	
	/** 원본 게시물ID. 원본글은 0. 답변글은 원본 postIdx */
	@JsonView(jsonView.PRIVATE.class)
	private int postOriginalIdx = 0;
	
	/** 부모 게시물ID. 원본글은 0. 답변글은 부모 postIdx */
	@JsonView(jsonView.PRIVATE.class)
	private int postParentIdx = 0;
	
	private String postTel;
	private String postPost;
	private String postAddr;
	private String postAddr2;
	
	private String postThumb;
	
	
	/** 작성자명 */
	private String postName;
	/** 작성일자 */
	private String postDate;
	private String mn;
	
	@JsonView(jsonView.PRIVATE.class)
	private int rn;
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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
	
	private String memberNm;
	private String boardNm;
	private String CommentCnt;
	
	private int postTotal;
	@JsonView(jsonView.PRIVATE.class)
	private String commentContent;

	private String postDatetime;
	
	private int limit;

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

	public int getPostGroupId() {
		return postGroupId;
	}

	public void setPostGroupId(int postGroupId) {
		this.postGroupId = postGroupId;
	}

	public int getPostGroupOrder() {
		return postGroupOrder;
	}

	public void setPostGroupOrder(int postGroupOrder) {
		this.postGroupOrder = postGroupOrder;
	}

	public int getPostGroupDepth() {
		return postGroupDepth;
	}

	public void setPostGroupDepth(int postGroupDepth) {
		this.postGroupDepth = postGroupDepth;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public String getPostPassword() {
		return postPassword;
	}

	public void setPostPassword(String postPassword) {
		this.postPassword = postPassword;
	}

	public int getPostCommentCount() {
		return postCommentCount;
	}

	public void setPostCommentCount(int postCommentCount) {
		this.postCommentCount = postCommentCount;
	}

	public int getPostReplyCount() {
		return postReplyCount;
	}

	public void setPostReplyCount(int postReplyCount) {
		this.postReplyCount = postReplyCount;
	}

	public String getPostSecret() {
		return postSecret;
	}

	public void setPostSecret(String postSecret) {
		this.postSecret = postSecret;
	}

	public int getPostHtml() {
		return postHtml;
	}

	public void setPostHtml(int postHtml) {
		this.postHtml = postHtml;
	}

	public int getPostNotice() {
		return postNotice;
	}

	public void setPostNotice(int postNotice) {
		this.postNotice = postNotice;
	}

	public String getPostNoticeSdate() {
		return postNoticeSdate;
	}

	public void setPostNoticeSdate(String postNoticeSdate) {
		this.postNoticeSdate = postNoticeSdate;
	}

	public String getPostNoticeEdate() {
		return postNoticeEdate;
	}

	public void setPostNoticeEdate(String postNoticeEdate) {
		this.postNoticeEdate = postNoticeEdate;
	}

	public int getPostReceiveEmail() {
		return postReceiveEmail;
	}

	public void setPostReceiveEmail(int postReceiveEmail) {
		this.postReceiveEmail = postReceiveEmail;
	}

	public int getPostHit() {
		return postHit;
	}

	public void setPostHit(int postHit) {
		this.postHit = postHit;
	}

	public int getPostFile() {
		return postFile;
	}

	public void setPostFile(int postFile) {
		this.postFile = postFile;
	}

	public int getPostImage() {
		return postImage;
	}

	public void setPostImage(int postImage) {
		this.postImage = postImage;
	}

	public int getPostOriginalIdx() {
		return postOriginalIdx;
	}

	public void setPostOriginalIdx(int postOriginalIdx) {
		this.postOriginalIdx = postOriginalIdx;
	}

	public int getPostParentIdx() {
		return postParentIdx;
	}

	public void setPostParentIdx(int postParentIdx) {
		this.postParentIdx = postParentIdx;
	}

	public String getPostTel() {
		return postTel;
	}

	public void setPostTel(String postTel) {
		this.postTel = postTel;
	}

	public String getPostPost() {
		return postPost;
	}

	public void setPostPost(String postPost) {
		this.postPost = postPost;
	}

	public String getPostAddr() {
		return postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getPostAddr2() {
		return postAddr2;
	}

	public void setPostAddr2(String postAddr2) {
		this.postAddr2 = postAddr2;
	}

	public String getPostThumb() {
		return postThumb;
	}

	public void setPostThumb(String postThumb) {
		this.postThumb = postThumb;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getMn() {
		return mn;
	}

	public void setMn(String mn) {
		this.mn = mn;
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

	public String getMemberNm() {
		return memberNm;
	}

	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}

	public String getBoardNm() {
		return boardNm;
	}

	public void setBoardNm(String boardNm) {
		this.boardNm = boardNm;
	}

	public String getCommentCnt() {
		return CommentCnt;
	}

	public void setCommentCnt(String commentCnt) {
		CommentCnt = commentCnt;
	}

	public int getPostTotal() {
		return postTotal;
	}

	public void setPostTotal(int postTotal) {
		this.postTotal = postTotal;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getPostDatetime() {
		return postDatetime;
	}

	public void setPostDatetime(String postDatetime) {
		this.postDatetime = postDatetime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}