package kepco.lms.edu.module.play;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import kepco.lms.edu.event.EventVo;
import kepco.lms.edu.process.play.ProcessPlayVo;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModulePlayVo {
	/** 모듈IDX */
	private int modulePlayIdx;
	private int playIdx;
	private int playTeamIdx;
	
	private String memberId;
	private String moduleTitle;
	private String successYn;
	private Date moduleStartTime;
	private Date moduleEndTime;
	private String moduleCd;
	private int attempt;
	private String workRole;
	
	private String date;
	
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String b1;
	private String b2;
	private String b3;
	
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	
	private int moduleElapsedTime;
	
	private String mostRisk;
	
	private List<ProcessPlayVo> processPlayList;
	
//	private EventVo accidentEventVo;
	
	private int modulePlayCnt;
	private int modulePlayerCnt;
	private int maxModulePlayMinute;
	private int avgModulePlayMinute;
	private int minModulePlayMinute;
	private int modulePlaySuccessCnt;
	private int modulePlayFailCnt;
	private int accidentCnt;
	
	private int moduleFailCnt;
	private String accidentBehavior;
	private int eventIdx;
	
	public int getEventIdx() {
		return eventIdx;
	}

	public void setEventIdx(int eventIdx) {
		this.eventIdx = eventIdx;
	}

	public int getModuleFailCnt() {
		return moduleFailCnt;
	}

	public void setModuleFailCnt(int moduleFailCnt) {
		this.moduleFailCnt = moduleFailCnt;
	}

	public String getAccidentBehavior() {
		return accidentBehavior;
	}

	public void setAccidentBehavior(String accidentBehavior) {
		this.accidentBehavior = accidentBehavior;
	}
	
	public int getAccidentCnt() {
		return accidentCnt;
	}

	public void setAccidentCnt(int accidentCnt) {
		this.accidentCnt = accidentCnt;
	}

	public int getAvgModulePlayMinute() {
		return avgModulePlayMinute;
	}

	public void setAvgModulePlayMinute(int avgModulePlayMinute) {
		this.avgModulePlayMinute = avgModulePlayMinute;
	}

	public int getModulePlayCnt() {
		return modulePlayCnt;
	}

	public void setModulePlayCnt(int modulePlayCnt) {
		this.modulePlayCnt = modulePlayCnt;
	}

	public int getModulePlayerCnt() {
		return modulePlayerCnt;
	}

	public void setModulePlayerCnt(int modulePlayerCnt) {
		this.modulePlayerCnt = modulePlayerCnt;
	}

	public int getMaxModulePlayMinute() {
		return maxModulePlayMinute;
	}

	public void setMaxModulePlayMinute(int maxModulePlayMinute) {
		this.maxModulePlayMinute = maxModulePlayMinute;
	}

	public int getMinModulePlayMinute() {
		return minModulePlayMinute;
	}

	public void setMinModulePlayMinute(int minModulePlayMinute) {
		this.minModulePlayMinute = minModulePlayMinute;
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

	public String getMostRisk() {
		return mostRisk;
	}

	public void setMostRisk(String mostRisk) {
		this.mostRisk = mostRisk;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getPlayTeamIdx() {
		return playTeamIdx;
	}

	public void setPlayTeamIdx(int playTeamIdx) {
		this.playTeamIdx = playTeamIdx;
	}

//	public EventVo getAccidentEventVo() {
//		return accidentEventVo;
//	}
//
//	public void setAccidentEventVo(EventVo accidentEventVo) {
//		this.accidentEventVo = accidentEventVo;
//	}

	public int getModuleElapsedTime() {
		return moduleElapsedTime;
	}

	public void setModuleElapsedTime(int moduleElapsedTime) {
		this.moduleElapsedTime = moduleElapsedTime;
	}

	public List<ProcessPlayVo> getProcessPlayList() {
		return processPlayList;
	}

	public void setProcessPlayList(List<ProcessPlayVo> processPlayList) {
		this.processPlayList = processPlayList;
	}

	public String getWorkRole() {
		return workRole;
	}

	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getB1() {
		return b1;
	}

	public void setB1(String b1) {
		this.b1 = b1;
	}

	public String getB2() {
		return b2;
	}

	public void setB2(String b2) {
		this.b2 = b2;
	}

	public String getB3() {
		return b3;
	}

	public void setB3(String b3) {
		this.b3 = b3;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	private String deleteYn;

	public String getModuleCd() {
		return moduleCd;
	}

	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}

	public int getModulePlayIdx() {
		return modulePlayIdx;
	}

	public void setModulePlayIdx(int modulePlayIdx) {
		this.modulePlayIdx = modulePlayIdx;
	}

	public int getPlayIdx() {
		return playIdx;
	}

	public void setPlayIdx(int playIdx) {
		this.playIdx = playIdx;
	}

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public String getSuccessYn() {
		return successYn;
	}

	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}
	
	public Date getModuleStartTime() {
		return moduleStartTime;
	}

	public void setModuleStartTime(Date moduleStartTime) {
		this.moduleStartTime = moduleStartTime;
	}

	public Date getModuleEndTime() {
		return moduleEndTime;
	}

	public void setModuleEndTime(Date moduleEndTime) {
		this.moduleEndTime = moduleEndTime;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

}