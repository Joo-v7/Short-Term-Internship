package kepco.lms.edu.process;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EduProcessVo {

	private int processIdx;
	private String processContents;
	private String memberNm;
	
	private String scenario;
	private String moduleCd;
	private int step;
	
	private String workRole;
	private String taskTool;
	private String taskId;
	private String aiId;
	private String taskTools;

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
	
	public String getAiId() {
		return aiId;
	}
	public void setAiId(String aiId) {
		this.aiId = aiId;
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
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getScenario() {
		return scenario;
	}
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	public String getModuleCd() {
		return moduleCd;
	}
	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getWorkRole() {
		return workRole;
	}
	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}
	public String getTaskTool() {
		return taskTool;
	}
	public void setTaskTool(String taskTool) {
		this.taskTool = taskTool;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskTools() {
		return taskTools;
	}
	public void setTaskTools(String taskTools) {
		this.taskTools = taskTools;
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