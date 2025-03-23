package kepco.lms.vod.stat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class VodStatVo {
	
	private int applyIdx;
	private int vodIdx;
	private int contentIdx;
	private String vodTitle;
	private String contentTitle;
	private String vodDesc;
	private String vodSummary;
	private String vodCategory;
	private String vodState;
	private String vodImage;
	private String vodFile;
	private String vodKeyword;
	@JsonView(jsonView.PRIVATE.class)
	private int poIdx;
	@JsonView(jsonView.PRIVATE.class)
	private int evIdx;

	private String applyState;
	private String applyDate;
	private String learnState;
	private String learnDate;
	private int sumLearnTime;
	
	private int memberIdx;
	private String memberNm;
	private String memberId;
	private int rn;
	
	private int minLearnMinute;
	private int maxLearnMinute;
	private int avgLearnMinute;
	
	private int graduateCnt;
	private int applyCnt;
	
	private String vodEduBgnDate;
	private String vodEduEndDate;
	private int contentsCnt;
	private int studySumTime;
	private int learnMinTime;
	private int learnMaxTime;
	private int learnAvgTime;
	private int vodStudentsCnt;
	private int vodCompleteCnt;
	
	private String categoryNm;
	private int contentsStudentsCnt;
	private int comtentsCompleteCnt;
	
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
	
	public int getContentIdx() {
		return contentIdx;
	}
	public void setContentIdx(int contentIdx) {
		this.contentIdx = contentIdx;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public int getContentsStudentsCnt() {
		return contentsStudentsCnt;
	}
	public void setContentsStudentsCnt(int contentsStudentsCnt) {
		this.contentsStudentsCnt = contentsStudentsCnt;
	}
	public int getComtentsCompleteCnt() {
		return comtentsCompleteCnt;
	}
	public void setComtentsCompleteCnt(int comtentsCompleteCnt) {
		this.comtentsCompleteCnt = comtentsCompleteCnt;
	}
	public String getVodEduBgnDate() {
		return vodEduBgnDate;
	}
	public void setVodEduBgnDate(String vodEduBgnDate) {
		this.vodEduBgnDate = vodEduBgnDate;
	}
	public String getVodEduEndDate() {
		return vodEduEndDate;
	}
	public void setVodEduEndDate(String vodEduEndDate) {
		this.vodEduEndDate = vodEduEndDate;
	}
	public int getContentsCnt() {
		return contentsCnt;
	}
	public void setContentsCnt(int contentsCnt) {
		this.contentsCnt = contentsCnt;
	}
	public int getStudySumTime() {
		return studySumTime;
	}
	public void setStudySumTime(int studySumTime) {
		this.studySumTime = studySumTime;
	}
	public int getLearnMinTime() {
		return learnMinTime;
	}
	public void setLearnMinTime(int learnMinTime) {
		this.learnMinTime = learnMinTime;
	}
	public int getLearnMaxTime() {
		return learnMaxTime;
	}
	public void setLearnMaxTime(int learnMaxTime) {
		this.learnMaxTime = learnMaxTime;
	}
	public int getLearnAvgTime() {
		return learnAvgTime;
	}
	public void setLearnAvgTime(int learnAvgTime) {
		this.learnAvgTime = learnAvgTime;
	}
	public int getVodStudentsCnt() {
		return vodStudentsCnt;
	}
	public void setVodStudentsCnt(int vodStudentsCnt) {
		this.vodStudentsCnt = vodStudentsCnt;
	}
	public int getVodCompleteCnt() {
		return vodCompleteCnt;
	}
	public void setVodCompleteCnt(int vodCompleteCnt) {
		this.vodCompleteCnt = vodCompleteCnt;
	}
	public int getGraduateCnt() {
		return graduateCnt;
	}
	public void setGraduateCnt(int graduateCnt) {
		this.graduateCnt = graduateCnt;
	}
	public int getApplyCnt() {
		return applyCnt;
	}
	public void setApplyCnt(int applyCnt) {
		this.applyCnt = applyCnt;
	}
	public String getVodTitle() {
		return vodTitle;
	}
	public void setVodTitle(String vodTitle) {
		this.vodTitle = vodTitle;
	}
	public String getVodDesc() {
		return vodDesc;
	}
	public void setVodDesc(String vodDesc) {
		this.vodDesc = vodDesc;
	}
	public String getVodSummary() {
		return vodSummary;
	}
	public void setVodSummary(String vodSummary) {
		this.vodSummary = vodSummary;
	}
	public String getVodCategory() {
		return vodCategory;
	}
	public void setVodCategory(String vodCategory) {
		this.vodCategory = vodCategory;
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
	public int getMinLearnMinute() {
		return minLearnMinute;
	}
	public void setMinLearnMinute(int minLearnMinute) {
		this.minLearnMinute = minLearnMinute;
	}
	public int getMaxLearnMinute() {
		return maxLearnMinute;
	}
	public void setMaxLearnMinute(int maxLearnMinute) {
		this.maxLearnMinute = maxLearnMinute;
	}
	public int getAvgLearnMinute() {
		return avgLearnMinute;
	}
	public void setAvgLearnMinute(int avgLearnMinute) {
		this.avgLearnMinute = avgLearnMinute;
	}
	public int getSumLearnTime() {
		return sumLearnTime;
	}
	public void setSumLearnTime(int sumLearnTime) {
		this.sumLearnTime = sumLearnTime;
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
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
}