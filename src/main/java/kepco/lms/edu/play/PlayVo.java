package kepco.lms.edu.play;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayVo {
	/** 모듈IDX */
	private int playIdx;
	private int detailIdx;
	private int memberIdx;
	private int eduIdx;
	private int playTeamIdx;
	
	private String team;
	private String workRole;
	private String mostRole;
	private String successYn;
	
	private String date;
	private Date startTime;
	private Date endTime;
	private int playedTime;
	
	private String memberId;
	private String memberNm;
	
	private int evScore;
	
	private String trainTitle;
	private String teacherNm;
	
	private int mouduleCnt;
	private String eduCateNm;
	
	private String mostRisk;
	
	@JsonView(jsonView.PRIVATE.class)
	private String eduTrainBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduTrainEndDate;
	
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
	
	private String deleteYn;
	
	/** 수행시간(분) */
	private int playMinute;
	/** 수행모듈(갯수) */
	private int moduleCdCnt;
	/** 모듈수행(횟수) */
	private int modulePlayCnt;
	private int accidentCnt;
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	
	public int getAccidentCnt() {
		return accidentCnt;
	}

	public void setAccidentCnt(int accidentCnt) {
		this.accidentCnt = accidentCnt;
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

	public int getEvScore() {
		return evScore;
	}

	public void setEvScore(int evScore) {
		this.evScore = evScore;
	}

	public String getMostRole() {
		return mostRole;
	}

	public void setMostRole(String mostRole) {
		this.mostRole = mostRole;
	}

	public String getMostRisk() {
		return mostRisk;
	}

	public void setMostRisk(String mostRisk) {
		this.mostRisk = mostRisk;
	}

	public int getPlayTeamIdx() {
		return playTeamIdx;
	}

	public void setPlayTeamIdx(int playTeamIdx) {
		this.playTeamIdx = playTeamIdx;
	}

	public int getMouduleCnt() {
		return mouduleCnt;
	}

	public void setMouduleCnt(int mouduleCnt) {
		this.mouduleCnt = mouduleCnt;
	}

	public String getEduCateNm() {
		return eduCateNm;
	}

	public void setEduCateNm(String eduCateNm) {
		this.eduCateNm = eduCateNm;
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

	public int getPlayedTime() {
		return playedTime;
	}

	public void setPlayedTime(int playedTime) {
		this.playedTime = playedTime;
	}

	public String getTrainTitle() {
		return trainTitle;
	}

	public void setTrainTitle(String trainTitle) {
		this.trainTitle = trainTitle;
	}

	public String getTeacherNm() {
		return teacherNm;
	}

	public void setTeacherNm(String teacherNm) {
		this.teacherNm = teacherNm;
	}

	public int getPlayIdx() {
		return playIdx;
	}

	public void setPlayIdx(int playIdx) {
		this.playIdx = playIdx;
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

	public int getEduIdx() {
		return eduIdx;
	}

	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getWorkRole() {
		return workRole;
	}

	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}

	public String getSuccessYn() {
		return successYn;
	}

	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberNm() {
		return memberNm;
	}

	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
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

	public int getPlayMinute() {
		return playMinute;
	}

	public void setPlayMinute(int playMinute) {
		this.playMinute = playMinute;
	}

	public int getModuleCdCnt() {
		return moduleCdCnt;
	}

	public void setModuleCdCnt(int moduleCdCnt) {
		this.moduleCdCnt = moduleCdCnt;
	}

	public int getModulePlayCnt() {
		return modulePlayCnt;
	}

	public void setModulePlayCnt(int modulePlayCnt) {
		this.modulePlayCnt = modulePlayCnt;
	}
	
}