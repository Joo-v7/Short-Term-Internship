package kepco.lms.edu;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EduVo {

	private int eduIdx;
	private int memberIdx;
	private int categoryIdx;
	private int playIdx;
	private String eduTitle;
	private String eduCategory;
	private String eduType;
	private String eduKeyword;
	private String eduModule;
	private String eduState;
	
	private String registDate;

	private String eduFile1;
	private String memberNm;
	private int moduleCnt;
	private int detailCnt;

	private String eduCateNm;
	/** 첨부파일 저장파일명 */
	private String attachmentFileName;
	/** 첨부파일 원본파일명 */
	private String attachmentFileOrigin;
	
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
	
	private String teacherNm;

	private String playTimeAvg;
	private String playTimeMin;
	private String playTimeMax;
	
	private int totalCnt;
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	private int accidentCnt;

	private String evPlayYn;
	private String pollPlayYn;
	
	private int evPlayIdx;
	private int poPlayIdx;
	
	public int getEvPlayIdx() {
		return evPlayIdx;
	}
	public void setEvPlayIdx(int evPlayIdx) {
		this.evPlayIdx = evPlayIdx;
	}
	public int getPoPlayIdx() {
		return poPlayIdx;
	}
	public void setPoPlayIdx(int poPlayIdx) {
		this.poPlayIdx = poPlayIdx;
	}
	public String getEvPlayYn() {
		return evPlayYn;
	}
	public void setEvPlayYn(String evPlayYn) {
		this.evPlayYn = evPlayYn;
	}
	public String getPollPlayYn() {
		return pollPlayYn;
	}
	public void setPollPlayYn(String pollPlayYn) {
		this.pollPlayYn = pollPlayYn;
	}
	public String getTeacherNm() {
		return teacherNm;
	}
	public void setTeacherNm(String teacherNm) {
		this.teacherNm = teacherNm;
	}
	public String getPlayTimeAvg() {
		return playTimeAvg;
	}
	public void setPlayTimeAvg(String playTimeAvg) {
		this.playTimeAvg = playTimeAvg;
	}
	public String getPlayTimeMin() {
		return playTimeMin;
	}
	public void setPlayTimeMin(String playTimeMin) {
		this.playTimeMin = playTimeMin;
	}
	public String getPlayTimeMax() {
		return playTimeMax;
	}
	public void setPlayTimeMax(String playTimeMax) {
		this.playTimeMax = playTimeMax;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getEsCnt() {
		return esCnt;
	}
	public void setEsCnt(int esCnt) {
		this.esCnt = esCnt;
	}
	public int getFallCnt() {
		return fallCnt;
	}
	public void setFallCnt(int fallCnt) {
		this.fallCnt = fallCnt;
	}
	public int getCareCnt() {
		return careCnt;
	}
	public void setCareCnt(int careCnt) {
		this.careCnt = careCnt;
	}
	public int getLoadCnt() {
		return loadCnt;
	}
	public void setLoadCnt(int loadCnt) {
		this.loadCnt = loadCnt;
	}
	public int getAccidentCnt() {
		return accidentCnt;
	}
	public void setAccidentCnt(int accidentCnt) {
		this.accidentCnt = accidentCnt;
	}
	public int getPlayIdx() {
		return playIdx;
	}
	public void setPlayIdx(int playIdx) {
		this.playIdx = playIdx;
	}
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	public String getAttachmentFileOrigin() {
		return attachmentFileOrigin;
	}
	public void setAttachmentFileOrigin(String attachmentFileOrigin) {
		this.attachmentFileOrigin = attachmentFileOrigin;
	}
	public String getEduFile1() {
		return eduFile1;
	}
	public void setEduFile1(String eduFile1) {
		this.eduFile1 = eduFile1;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getEduKeyword() {
		return eduKeyword;
	}
	public void setEduKeyword(String eduKeyword) {
		this.eduKeyword = eduKeyword;
	}
	public int getModuleCnt() {
		return moduleCnt;
	}
	public void setModuleCnt(int moduleCnt) {
		this.moduleCnt = moduleCnt;
	}
	public int getDetailCnt() {
		return detailCnt;
	}
	public void setDetailCnt(int detailCnt) {
		this.detailCnt = detailCnt;
	}
	public int getEduIdx() {
		return eduIdx;
	}
	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getEduTitle() {
		return eduTitle;
	}
	public void setEduTitle(String eduTitle) {
		this.eduTitle = eduTitle;
	}
	public String getEduCategory() {
		return eduCategory;
	}
	public void setEduCategory(String eduCategory) {
		this.eduCategory = eduCategory;
	}
	public String getEduType() {
		return eduType;
	}
	public void setEduType(String eduType) {
		this.eduType = eduType;
	}
	public String getEduModule() {
		return eduModule;
	}
	public void setEduModule(String eduModule) {
		this.eduModule = eduModule;
	}
	public String getEduState() {
		return eduState;
	}
	public void setEduState(String eduState) {
		this.eduState = eduState;
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
	public String getEduCateNm() {
		return eduCateNm;
	}
	public void setEduCateNm(String eduCateNm) {
		this.eduCateNm = eduCateNm;
	}
	public int getCategoryIdx() {
		return categoryIdx;
	}
	public void setCategoryIdx(int categoryIdx) {
		this.categoryIdx = categoryIdx;
	}
	
}