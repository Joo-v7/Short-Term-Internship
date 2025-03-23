package kepco.lms.edu.pack;

import org.springframework.stereotype.Component;

import kepco.lms.edu.module.ModuleVo;

@Component
public class PackVo extends ModuleVo {

	private int packIdx;
	private int eduIdx;
	private int moduleIdx;
	private int moduleOrder;
	
	private ModuleVo moduleVo;
	private String trainState;
	
	private int rn;
	private int insertIdx;
	private String insertIp;
	private String insertDate;

	public String getTrainState() {
		return trainState;
	}
	public void setTrainState(String trainState) {
		this.trainState = trainState;
	}
	public ModuleVo getModuleVo() {
		return moduleVo;
	}
	public void setModuleVo(ModuleVo moduleVo) {
		this.moduleVo = moduleVo;
	}
	public int getPackIdx() {
		return packIdx;
	}
	public void setPackIdx(int packIdx) {
		this.packIdx = packIdx;
	}
	public int getEduIdx() {
		return eduIdx;
	}
	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}
	public int getModuleIdx() {
		return moduleIdx;
	}
	public void setModuleIdx(int moduleIdx) {
		this.moduleIdx = moduleIdx;
	}
	public int getModuleOrder() {
		return moduleOrder;
	}
	public void setModuleOrder(int moduleOrder) {
		this.moduleOrder = moduleOrder;
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
}