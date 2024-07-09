package kepco.lms.edu.play.team;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PlayTeamVo {

	private int playTeamIdx;
	private int playIdx;
	private int detailIdx;
	private int teacherIdx;
	
	private String team;
	private String teamMemberId1;
	private String teamMemberId2;
	private String teamMemberId3;
	private String teamMemberId4; 
	private String teamMemberRole1;
	private String teamMemberRole2;
	private String teamMemberRole3;
	private String teamMemberRole4;
	
	private String date;
	
	private String successYn;
	
	private Date startTime;
	private Date endTime;

	private String deleteYn;
	
	private String trainTitle;
	private String teacherNm;
	private int playMinute;
	private String mainWorker;
	private String subWorker;
	private String groundWorker;
	private String superWorker;
	
	/** 수행시도 횟수 */
	private int attemptMax;
	
	/** 수행시도 모듈 갯수 */
	private int moduleCdCnt;
	
	private String moduleCd;
	private String moduleTitle;
	private int modulePlaySuccessCnt;
	private int modulePlayFailCnt;
	
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	private int accidentCnt;
	
	public int getTeacherIdx() {
		return teacherIdx;
	}
	public void setTeacherIdx(int teacherIdx) {
		this.teacherIdx = teacherIdx;
	}
	public int getModulePlaySuccessCnt() {
		return modulePlaySuccessCnt;
	}
	public void setModulePlaySuccessCnt(int modulePlaySuccessCnt) {
		this.modulePlaySuccessCnt = modulePlaySuccessCnt;
	}
	public int getModulePlayFailCnt() {
		return modulePlayFailCnt;
	}
	public void setModulePlayFailCnt(int modulePlayFailCnt) {
		this.modulePlayFailCnt = modulePlayFailCnt;
	}
	public String getModuleCd() {
		return moduleCd;
	}
	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}
	public String getModuleTitle() {
		return moduleTitle;
	}
	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
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
	public void setModuleCdCnt(int moduleCdCnt) {
		this.moduleCdCnt = moduleCdCnt;
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
	public int getPlayMinute() {
		return playMinute;
	}
	public void setPlayMinute(int playMinute) {
		this.playMinute = playMinute;
	}
	public String getMainWorker() {
		return mainWorker;
	}
	public void setMainWorker(String mainWorker) {
		this.mainWorker = mainWorker;
	}
	public String getSubWorker() {
		return subWorker;
	}
	public void setSubWorker(String subWorker) {
		this.subWorker = subWorker;
	}
	public String getGroundWorker() {
		return groundWorker;
	}
	public void setGroundWorker(String groundWorker) {
		this.groundWorker = groundWorker;
	}
	public String getSuperWorker() {
		return superWorker;
	}
	public void setSuperWorker(String superWorker) {
		this.superWorker = superWorker;
	}
	public int getPlayTeamIdx() {
		return playTeamIdx;
	}
	public void setPlayTeamIdx(int playTeamIdx) {
		this.playTeamIdx = playTeamIdx;
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
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getTeamMemberId1() {
		return teamMemberId1;
	}
	public void setTeamMemberId1(String teamMemberId1) {
		this.teamMemberId1 = teamMemberId1;
	}
	public String getTeamMemberId2() {
		return teamMemberId2;
	}
	public void setTeamMemberId2(String teamMemberId2) {
		this.teamMemberId2 = teamMemberId2;
	}
	public String getTeamMemberId3() {
		return teamMemberId3;
	}
	public void setTeamMemberId3(String teamMemberId3) {
		this.teamMemberId3 = teamMemberId3;
	}
	public String getTeamMemberId4() {
		return teamMemberId4;
	}
	public void setTeamMemberId4(String teamMemberId4) {
		this.teamMemberId4 = teamMemberId4;
	}
	public String getTeamMemberRole1() {
		return teamMemberRole1;
	}
	public void setTeamMemberRole1(String teamMemberRole1) {
		this.teamMemberRole1 = teamMemberRole1;
	}
	public String getTeamMemberRole2() {
		return teamMemberRole2;
	}
	public void setTeamMemberRole2(String teamMemberRole2) {
		this.teamMemberRole2 = teamMemberRole2;
	}
	public String getTeamMemberRole3() {
		return teamMemberRole3;
	}
	public void setTeamMemberRole3(String teamMemberRole3) {
		this.teamMemberRole3 = teamMemberRole3;
	}
	public String getTeamMemberRole4() {
		return teamMemberRole4;
	}
	public void setTeamMemberRole4(String teamMemberRole4) {
		this.teamMemberRole4 = teamMemberRole4;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSuccessYn() {
		return successYn;
	}
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
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
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public int getAttemptMax() {
		return attemptMax;
	}
	public void setAttemptMax(int attemptMax) {
		this.attemptMax = attemptMax;
	}
	public int getModuleCdCnt() {
		return moduleCdCnt;
	}
	public void setModuleCntCnt(int moduleCdCnt) {
		this.moduleCdCnt = moduleCdCnt;
	}
	
}