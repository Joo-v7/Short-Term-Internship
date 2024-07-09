package kepco.lms.vod;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class VodVo {

	private int vodIdx;
	private String vodTitle;
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

	@JsonView(jsonView.PRIVATE.class)
	private String memberNm;
	private int contentCnt;
	private int vodApplyCnt;
	@JsonView(jsonView.PRIVATE.class)
	private String poSubject;
	@JsonView(jsonView.PRIVATE.class)
	private String evSubject;
	
	@JsonView(jsonView.PRIVATE.class)
	private String vodAcceptBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String vodAcceptEndDate;
	@JsonView(jsonView.PRIVATE.class)
	private String vodEduBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String vodEduEndDate;
	
	private int vodLimitCnt;
	private String vodAcceptAuto;
	private String insertNm;
	private int vodStudentsCnt;
	private int vodCompleteCnt;
	
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
	
	public String getInsertNm() {
		return insertNm;
	}
	public void setInsertNm(String insertNm) {
		this.insertNm = insertNm;
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
	public String getVodAcceptBgnDate() {
		return vodAcceptBgnDate;
	}
	public void setVodAcceptBgnDate(String vodAcceptBgnDate) {
		this.vodAcceptBgnDate = vodAcceptBgnDate;
	}
	public String getVodAcceptEndDate() {
		return vodAcceptEndDate;
	}
	public void setVodAcceptEndDate(String vodAcceptEndDate) {
		this.vodAcceptEndDate = vodAcceptEndDate;
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
	public int getVodLimitCnt() {
		return vodLimitCnt;
	}
	public void setVodLimitCnt(int vodLimitCnt) {
		this.vodLimitCnt = vodLimitCnt;
	}
	public String getVodAcceptAuto() {
		return vodAcceptAuto;
	}
	public void setVodAcceptAuto(String vodAcceptAuto) {
		this.vodAcceptAuto = vodAcceptAuto;
	}
	public int getVodApplyCnt() {
		return vodApplyCnt;
	}
	public void setVodApplyCnt(int vodApplyCnt) {
		this.vodApplyCnt = vodApplyCnt;
	}
	public String getEvSubject() {
		return evSubject;
	}
	public void setEvSubject(String evSubject) {
		this.evSubject = evSubject;
	}
	public int getEvIdx() {
		return evIdx;
	}
	public void setEvIdx(int evIdx) {
		this.evIdx = evIdx;
	}
	public String getVodSummary() {
		return vodSummary;
	}
	public void setVodSummary(String vodSummary) {
		this.vodSummary = vodSummary;
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
	public int getContentCnt() {
		return contentCnt;
	}
	public void setContentCnt(int contentCnt) {
		this.contentCnt = contentCnt;
	}
	public int getPoIdx() {
		return poIdx;
	}
	public void setPoIdx(int poIdx) {
		this.poIdx = poIdx;
	}
	public String getPoSubject() {
		return poSubject;
	}
	public void setPoSubject(String poSubject) {
		this.poSubject = poSubject;
	}
	public int getVodIdx() {
		return vodIdx;
	}
	public void setVodIdx(int vodIdx) {
		this.vodIdx = vodIdx;
	}
	public String getVodTitle() {
		return vodTitle;
	}
	public void setVodTitle(String vodTitle) {
		this.vodTitle = vodTitle;
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
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
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
	public String getVodDesc() {
		return vodDesc;
	}
	public void setVodDesc(String vodDesc) {
		this.vodDesc = vodDesc;
	}
	public String getVodImage() {
		return vodImage;
	}
	public void setVodImage(String vodImage) {
		this.vodImage = vodImage;
	}
}