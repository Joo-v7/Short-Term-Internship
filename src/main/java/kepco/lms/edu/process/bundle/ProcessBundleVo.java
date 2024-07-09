package kepco.lms.edu.process.bundle;

import java.util.Date;

import org.springframework.stereotype.Component;

import kepco.lms.edu.process.EduProcessVo;

@Component
public class ProcessBundleVo {

	private int processIdx;
	private int bundleIdx;
	private int moduleIdx;
	private int processOrder;
	
	private int rn;
	private int insertIdx;
	private String insertIp;
	private Date insertDate;
	
	private String processContents;
	private String processRole;
	
	public int getProcessIdx() {
		return processIdx;
	}
	public void setProcessIdx(int processIdx) {
		this.processIdx = processIdx;
	}
	public String getProcessRole() {
		return processRole;
	}
	public void setProcessRole(String processRole) {
		this.processRole = processRole;
	}
	public String getProcessContents() {
		return processContents;
	}
	public void setProcessContents(String processContents) {
		this.processContents = processContents;
	}
	public int getBundleIdx() {
		return bundleIdx;
	}
	public void setBundleIdx(int bundleIdx) {
		this.bundleIdx = bundleIdx;
	}
	public int getModuleIdx() {
		return moduleIdx;
	}
	public void setModuleIdx(int moduleIdx) {
		this.moduleIdx = moduleIdx;
	}
	public int getProcessOrder() {
		return processOrder;
	}
	public void setProcessOrder(int processOrder) {
		this.processOrder = processOrder;
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
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
}