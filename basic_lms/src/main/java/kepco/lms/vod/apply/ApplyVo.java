package kepco.lms.vod.apply;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class ApplyVo {

	private int applyIdx;
	private int vodIdx;
	private int memberIdx;
	private String applyState;
	private String applyDate;
	private String learnState;
	private String learnDate;
	
	private String vodTitle;
	private String vodSummary;
	private String vodDesc;
	private String vodState;
	private String vodImage;
	private String vodFile;
	private String vodKeyword;
	private int poIdx;
	private int evIdx;
	
	private String memberNm;
	private int vodContentCnt;
	private int vodLearnCnt;
	private String memberId;
	
	private int contentIdx;
	private int vodCategoryIdx;
	private String contentTitle;
	private String contentDesc;
	private int contentTime;
	private String contentFileName;
	private String contentFileOrigin;
	
	private String categoryNm;
	@JsonView(jsonView.PRIVATE.class)
	private int rn;
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	@JsonView(jsonView.PRIVATE.class)
	private String insertDate;
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
	
	
	public String getVodSummary() {
		return vodSummary;
	}
	public void setVodSummary(String vodSummary) {
		this.vodSummary = vodSummary;
	}
	public String getVodDesc() {
		return vodDesc;
	}
	public void setVodDesc(String vodDesc) {
		this.vodDesc = vodDesc;
	}
	public String getVodState() {
		return vodState;
	}
	public void setVodState(String vodState) {
		this.vodState = vodState;
	}
	public String getVodImage() {
		return vodImage;
	}
	public void setVodImage(String vodImage) {
		this.vodImage = vodImage;
	}
	public String getVodFile() {
		return vodFile;
	}
	public void setVodFile(String vodFile) {
		this.vodFile = vodFile;
	}
	public String getVodKeyword() {
		return vodKeyword;
	}
	public void setVodKeyword(String vodKeyword) {
		this.vodKeyword = vodKeyword;
	}
	public int getPoIdx() {
		return poIdx;
	}
	public void setPoIdx(int poIdx) {
		this.poIdx = poIdx;
	}
	public int getEvIdx() {
		return evIdx;
	}
	public void setEvIdx(int evIdx) {
		this.evIdx = evIdx;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public int getContentIdx() {
		return contentIdx;
	}
	public void setContentIdx(int contentIdx) {
		this.contentIdx = contentIdx;
	}
	public int getVodCategoryIdx() {
		return vodCategoryIdx;
	}
	public void setVodCategoryIdx(int vodCategoryIdx) {
		this.vodCategoryIdx = vodCategoryIdx;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getContentDesc() {
		return contentDesc;
	}
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	public int getContentTime() {
		return contentTime;
	}
	public void setContentTime(int contentTime) {
		this.contentTime = contentTime;
	}
	public String getContentFileName() {
		return contentFileName;
	}
	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}
	public String getContentFileOrigin() {
		return contentFileOrigin;
	}
	public void setContentFileOrigin(String contentFileOrigin) {
		this.contentFileOrigin = contentFileOrigin;
	}
	public String getLearnState() {
		return learnState;
	}
	public void setLearnState(String learnState) {
		this.learnState = learnState;
	}
	public String getLearnDate() {
		return learnDate;
	}
	public void setLearnDate(String learnDate) {
		this.learnDate = learnDate;
	}
	public int getVodLearnCnt() {
		return vodLearnCnt;
	}
	public void setVodLearnCnt(int vodLearnCnt) {
		this.vodLearnCnt = vodLearnCnt;
	}
	public int getApplyIdx() {
		return applyIdx;
	}
	public void setApplyIdx(int applyIdx) {
		this.applyIdx = applyIdx;
	}
	public int getVodIdx() {
		return vodIdx;
	}
	public void setVodIdx(int vodIdx) {
		this.vodIdx = vodIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
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
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getVodTitle() {
		return vodTitle;
	}
	public void setVodTitle(String vodTitle) {
		this.vodTitle = vodTitle;
	}
	public int getVodContentCnt() {
		return vodContentCnt;
	}
	public void setVodContentCnt(int vodContentCnt) {
		this.vodContentCnt = vodContentCnt;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}