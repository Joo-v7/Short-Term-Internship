package kepco.lms.edu.regist;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistVo {

	private int registIdx;
	@JsonView(jsonView.PRIVATE.class)
	private int detailIdx;
	private int memberIdx;
	private int teamIdx;
	private String team;
	private String registRole;
	private String registState;
	private String registDate;
	private String eduKeyword;
	@JsonView(jsonView.PRIVATE.class)
	private String eduState;
	@JsonView(jsonView.PRIVATE.class)
	private String eduDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptBgnTime;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptEndDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptEndTime;
	@JsonView(jsonView.PRIVATE.class)
	private String eduTrainBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduTrainEndDate;
	
	
	private String trainTitle;
	private String eduNumeral;
	
	private String memberNm;
	private String memberId;
	private String teamNm;
	@JsonView(jsonView.PRIVATE.class)
	private int moduleCnt;
	
	private int eduRegCnt;
	private int eduBotCnt;
	private int eduAcceptCnt;
	
	private String eduCategory;
	private int categoryIdx;
	private String eduType;
	private String attachmentFileName;
	private String attachmentFileOrigin;
	
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
	
	
	public String getEduCategory() {
		return eduCategory;
	}
	public void setEduCategory(String eduCategory) {
		this.eduCategory = eduCategory;
	}
	public int getCategoryIdx() {
		return categoryIdx;
	}
	public void setCategoryIdx(int categoryIdx) {
		this.categoryIdx = categoryIdx;
	}
	public String getEduType() {
		return eduType;
	}
	public void setEduType(String eduType) {
		this.eduType = eduType;
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
	public String getEduKeyword() {
		return eduKeyword;
	}
	public void setEduKeyword(String eduKeyword) {
		this.eduKeyword = eduKeyword;
	}
	public int getEduRegCnt() {
		return eduRegCnt;
	}
	public void setEduRegCnt(int eduRegCnt) {
		this.eduRegCnt = eduRegCnt;
	}
	public int getEduBotCnt() {
		return eduBotCnt;
	}
	public void setEduBotCnt(int eduBotCnt) {
		this.eduBotCnt = eduBotCnt;
	}
	public int getEduAcceptCnt() {
		return eduAcceptCnt;
	}
	public void setEduAcceptCnt(int eduAcceptCnt) {
		this.eduAcceptCnt = eduAcceptCnt;
	}
	public String getEduTrainBgnDate() {
		return eduTrainBgnDate;
	}
	public void setEduTrainBgnDate(String eduTrainBgnDate) {
		this.eduTrainBgnDate = eduTrainBgnDate;
	}
	public String getEduTrainEndDate() {
		return eduTrainEndDate;
	}
	public void setEduTrainEndDate(String eduTrainEndDate) {
		this.eduTrainEndDate = eduTrainEndDate;
	}
	public String getTrainTitle() {
		return trainTitle;
	}
	public void setTrainTitle(String trainTitle) {
		this.trainTitle = trainTitle;
	}
	public String getEduNumeral() {
		return eduNumeral;
	}
	public void setEduNumeral(String eduNumeral) {
		this.eduNumeral = eduNumeral;
	}
	public String getEduAcceptBgnDate() {
		return eduAcceptBgnDate;
	}
	public void setEduAcceptBgnDate(String eduAcceptBgnDate) {
		this.eduAcceptBgnDate = eduAcceptBgnDate;
	}
	public String getEduAcceptBgnTime() {
		return eduAcceptBgnTime;
	}
	public void setEduAcceptBgnTime(String eduAcceptBgnTime) {
		this.eduAcceptBgnTime = eduAcceptBgnTime;
	}
	public String getEduAcceptEndDate() {
		return eduAcceptEndDate;
	}
	public void setEduAcceptEndDate(String eduAcceptEndDate) {
		this.eduAcceptEndDate = eduAcceptEndDate;
	}
	public String getEduAcceptEndTime() {
		return eduAcceptEndTime;
	}
	public void setEduAcceptEndTime(String eduAcceptEndTime) {
		this.eduAcceptEndTime = eduAcceptEndTime;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getEduState() {
		return eduState;
	}
	public void setEduState(String eduState) {
		this.eduState = eduState;
	}
	public String getEduDate() {
		return eduDate;
	}
	public void setEduDate(String eduDate) {
		this.eduDate = eduDate;
	}
	public String getTeamNm() {
		return teamNm;
	}
	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
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
	public int getRegistIdx() {
		return registIdx;
	}
	public void setRegistIdx(int registIdx) {
		this.registIdx = registIdx;
	}
	public int getDetailIdx() {
		return detailIdx;
	}
	public void setDetailIdx(int detailIdx) {
		this.detailIdx = detailIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getRegistRole() {
		return registRole;
	}
	public void setRegistRole(String registRole) {
		this.registRole = registRole;
	}
	public String getRegistState() {
		return registState;
	}
	public void setRegistState(String registState) {
		this.registState = registState;
	}
	public int getModuleCnt() {
		return moduleCnt;
	}
	public void setModuleCnt(int moduleCnt) {
		this.moduleCnt = moduleCnt;
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
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
}