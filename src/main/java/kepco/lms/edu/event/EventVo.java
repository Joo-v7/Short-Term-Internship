package kepco.lms.edu.event;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventVo {
	/** 모듈IDX */
	private int eventIdx;
	private int playIdx;
	private int eduIdx;
	private int modulePlayIdx;
	private String step;
	private String taskId;
	private String role;
	private String processContents;
	
	private String tools;
	private String taskTool;
	private String workRole;
	private String successYn;
	private String accidentYn;
	private String eventId;
	private String riskId;
	
	private String accidentCode;
	private String eventLocation;
	private String objectFactor;
	private String accidentBehaviorFactor;
	private String accidentEvent;
	private String evaluationId;
	private String evaluationType;
	//교육 사고 내용
	private int accidentContentIdx;
	private String accidentType;
	private String accidentCause;
	private String accidentBehavior;
	
	private String date;
	private String startDate;
	private String endDate;
	
	//절차 IDX 위험요인 추가
	private int processPlayIdx;
	private String riskFactor;
	private String riskLevel;
	private String riskValue1;
	private String riskValue2;
	
	private String deleteYn;

	private int eventElapsedStartTime;
	private int eventElapsedEndTime;
	
	private Date eventStartTime;
	private Date eventEndTime;

	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String weather;
	
	private int objectFactorCnt;

	public int getObjectFactorCnt() {
		return objectFactorCnt;
	}

	public void setObjectFactorCnt(int objectFactorCnt) {
		this.objectFactorCnt = objectFactorCnt;
	}

	private int accidentCauseCnt;

	public int getAccidentCauseCnt() {
		return accidentCauseCnt;
	}

	public void setAccidentCauseCnt(int accidentCauseCnt) {
		this.accidentCauseCnt = accidentCauseCnt;
	}

	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public String getC3() {
		return c3;
	}

	public void setC3(String c3) {
		this.c3 = c3;
	}

	public String getC4() {
		return c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}

	public String getC5() {
		return c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}

	public String getProcessContents() {
		return processContents;
	}

	public void setProcessContents(String processContents) {
		this.processContents = processContents;
	}

	public int getEventElapsedStartTime() {
		return eventElapsedStartTime;
	}

	public void setEventElapsedStartTime(int eventElapsedStartTime) {
		this.eventElapsedStartTime = eventElapsedStartTime;
	}

	public int getEventElapsedEndTime() {
		return eventElapsedEndTime;
	}

	public void setEventElapsedEndTime(int eventElapsedEndTime) {
		this.eventElapsedEndTime = eventElapsedEndTime;
	}

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getWorkRole() {
		return workRole;
	}

	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}

	public int getEventIdx() {
		return eventIdx;
	}

	public void setEventIdx(int eventIdx) {
		this.eventIdx = eventIdx;
	}

	public int getPlayIdx() {
		return playIdx;
	}

	public void setPlayIdx(int playIdx) {
		this.playIdx = playIdx;
	}

	public int getEduIdx() {
		return eduIdx;
	}

	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}

	public int getModulePlayIdx() {
		return modulePlayIdx;
	}

	public void setModulePlayIdx(int modulePlayIdx) {
		this.modulePlayIdx = modulePlayIdx;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTools() {
		return tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getTaskTool() {
		return taskTool;
	}

	public void setTaskTool(String taskTool) {
		this.taskTool = taskTool;
	}

	public String getSuccessYn() {
		return successYn;
	}

	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}

	public String getAccidentYn() {
		return accidentYn;
	}

	public void setAccidentYn(String accidentYn) {
		this.accidentYn = accidentYn;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getAccidentCode() {
		return accidentCode;
	}

	public void setAccidentCode(String accidentCode) {
		this.accidentCode = accidentCode;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getObjectFactor() {
		return objectFactor;
	}

	public void setObjectFactor(String objectFactor) {
		this.objectFactor = objectFactor;
	}

	public String getAccidentBehaviorFactor() {
		return accidentBehaviorFactor;
	}

	public void setAccidentBehaviorFactor(String accidentBehaviorFactor) {
		this.accidentBehaviorFactor = accidentBehaviorFactor;
	}

	public String getAccidentEvent() {
		return accidentEvent;
	}

	public void setAccidentEvent(String accidentEvent) {
		this.accidentEvent = accidentEvent;
	}

	public String getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(String evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public int getAccidentContentIdx() {
		return accidentContentIdx;
	}

	public void setAccidentContentIdx(int accidentContentIdx) {
		this.accidentContentIdx = accidentContentIdx;
	}

	public String getAccidentType() {
		return accidentType;
	}

	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}

	public String getAccidentCause() {
		return accidentCause;
	}

	public void setAccidentCause(String accidentCause) {
		this.accidentCause = accidentCause;
	}

	public String getAccidentBehavior() {
		return accidentBehavior;
	}

	public void setAccidentBehavior(String accidentBehavior) {
		this.accidentBehavior = accidentBehavior;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getProcessPlayIdx() {
		return processPlayIdx;
	}

	public void setProcessPlayIdx(int processPlayIdx) {
		this.processPlayIdx = processPlayIdx;
	}

	public String getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(String riskFactor) {
		this.riskFactor = riskFactor;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getRiskValue1() {
		return riskValue1;
	}

	public void setRiskValue1(String riskValue1) {
		this.riskValue1 = riskValue1;
	}

	public String getRiskValue2() {
		return riskValue2;
	}

	public void setRiskValue2(String riskValue2) {
		this.riskValue2 = riskValue2;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	
}