package kepco.lms.edu.process.play;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import kepco.lms.edu.event.EventVo;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessPlayVo {
	/** 절차IDX */
	private int processPlayIdx;
	private int modulePlayIdx;
	private int playIdx;
	private Integer eventIdx;
	
	private String taskId;
	private String successYn;
	private Date processStartTime;
	private Date processEndTime;
	private String date;
	
	private String workRole;
	
	//작업ID 구분별 분류	
	private int step;

	private int processIdx;
	private String processContents;
	private String taskTool;

	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String weather;
	
	private int processElapsedStartTime;
	private int processElapsedEndTime;
	
	//이벤트 관련
	private String accidentCode;
	private String eventLocation;
	private String objectFactor;
	private String accidentBehaviorFactor;
	private String accidentEvent;
	
	private int accidentContentIdx;
	private String accidentType;
	private String accidentCause;
	private String accidentBehavior;
	
	private String riskFactor;
	private String riskLevel;
	private String riskValue1;
	private String riskValue2;
	
	private String eventId;
	private String accidentYn;
	
	private int eventElapsedStartTime;
	private int eventElapsedEndTime;

	private Date eventStartTime;
	private Date eventEndTime;
	
	private String eventElapsedMinute; //경과시간 분
	private String eventElapsedSecond; //경과시간 초
	private String processElapsedMinute;
	private String processElapsedSecond;

	private int processCount;
	
	private int processTiming;
	private int eventTiming;
	
	public int getProcessTiming() {
		return processTiming;
	}
	public void setProcessTiming(int processTiming) {
		this.processTiming = processTiming;
	}
	public int getEventTiming() {
		return eventTiming;
	}
	public void setEventTiming(int eventTiming) {
		this.eventTiming = eventTiming;
	}
	public int getProcessCount() {
		return processCount;
	}
	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}
	public int getProcessIdx() {
		return processIdx;
	}
	public void setProcessIdx(int processIdx) {
		this.processIdx = processIdx;
	}
	public String getProcessContents() {
		return processContents;
	}
	public void setProcessContents(String processContents) {
		this.processContents = processContents;
	}
	public String getEventElapsedMinute() {
		return eventElapsedMinute;
	}
	public void setEventElapsedMinute(String eventElapsedMinute) {
		this.eventElapsedMinute = eventElapsedMinute;
	}
	public String getEventElapsedSecond() {
		return eventElapsedSecond;
	}
	public void setEventElapsedSecond(String eventElapsedSecond) {
		this.eventElapsedSecond = eventElapsedSecond;
	}
	public String getProcessElapsedMinute() {
		return processElapsedMinute;
	}
	public void setProcessElapsedMinute(String processElapsedMinute) {
		this.processElapsedMinute = processElapsedMinute;
	}
	public String getProcessElapsedSecond() {
		return processElapsedSecond;
	}
	public void setProcessElapsedSecond(String processElapsedSecond) {
		this.processElapsedSecond = processElapsedSecond;
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
	public Integer getEventIdx() {
		return eventIdx;
	}
	public void setEventIdx(Integer eventIdx) {
		this.eventIdx = eventIdx;
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
	public int getProcessElapsedEndTime() {
		return processElapsedEndTime;
	}
	public void setProcessElapsedEndTime(int processElapsedEndTime) {
		this.processElapsedEndTime = processElapsedEndTime;
	}
	public int getProcessElapsedStartTime() {
		return processElapsedStartTime;
	}
	public void setProcessElapsedStartTime(int processElapsedStartTime) {
		this.processElapsedStartTime = processElapsedStartTime;
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
	public String getTaskTool() {
		return taskTool;
	}
	public void setTaskTool(String taskTool) {
		this.taskTool = taskTool;
	}
	private List<EventVo> eventList;
	
	public String getWorkRole() {
		return workRole;
	}
	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}
	public List<EventVo> getEventList() {
		return eventList;
	}
	public void setEventList(List<EventVo> eventList) {
		this.eventList = eventList;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getProcessPlayIdx() {
		return processPlayIdx;
	}
	public void setProcessPlayIdx(int processPlayIdx) {
		this.processPlayIdx = processPlayIdx;
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSuccessYn() {
		return successYn;
	}
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}
	public Date getProcessStartTime() {
		return processStartTime;
	}
	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}
	public Date getProcessEndTime() {
		return processEndTime;
	}
	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}