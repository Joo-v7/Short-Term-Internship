package kepco.lms.edu.module;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleVo {
	/** 모듈IDX */
	private int moduleIdx;
	/** 모듈코드 */
	private String moduleCd;
	/** 모듈명 */
	private String moduleTitle;
	/** 모듈설명 */
	private String moduleDesc;
	/** 모듈시간(분) */
	private int moduleTime;
	/** 모듈 저장파일명 */
	private String moduleFileName;
	/** 모듈 원본파일명 */
	private String moduleFileOrigin;
	@JsonView(jsonView.PRIVATE.class)
	private int eduCount;
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
	
	public int getEduCount() {
		return eduCount;
	}
	public void setEduCount(int eduCount) {
		this.eduCount = eduCount;
	}
	public int getModuleIdx() {
		return moduleIdx;
	}
	public void setModuleIdx(int moduleIdx) {
		this.moduleIdx = moduleIdx;
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
	public String getModuleDesc() {
		return moduleDesc;
	}
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	public int getModuleTime() {
		return moduleTime;
	}
	public void setModuleTime(int moduleTime) {
		this.moduleTime = moduleTime;
	}
	public String getModuleFileName() {
		return moduleFileName;
	}
	public void setModuleFileName(String moduleFileName) {
		this.moduleFileName = moduleFileName;
	}
	public String getModuleFileOrigin() {
		return moduleFileOrigin;
	}
	public void setModuleFileOrigin(String moduleFileOrigin) {
		this.moduleFileOrigin = moduleFileOrigin;
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