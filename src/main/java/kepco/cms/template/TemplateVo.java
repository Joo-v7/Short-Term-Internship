package kepco.cms.template;

public class TemplateVo {
	
	private int tempIdx;
	private String tempTitle;
	private String tempCode1;
	private String tempCode2;
	private String tempContent;

	private int logIdx;
	private int insertIdx;
	private String insertIp;
	private String insertDate;
	private int updateIdx;
	private String updateIp;
	private String updateDate;
	private int deleteIdx;
	private String deleteIp;
	private String deleteDate;
	private String deleteYn;
	
	public int getLogIdx() {
		return logIdx;
	}
	public void setLogIdx(int logIdx) {
		this.logIdx = logIdx;
	}
	public String getTempCode1() {
		return tempCode1;
	}
	public void setTempCode1(String tempCode1) {
		this.tempCode1 = tempCode1;
	}
	public String getTempCode2() {
		return tempCode2;
	}
	public void setTempCode2(String tempCode2) {
		this.tempCode2 = tempCode2;
	}
	public int getTempIdx() {
		return tempIdx;
	}
	public void setTempIdx(int tempIdx) {
		this.tempIdx = tempIdx;
	}
	public String getTempTitle() {
		return tempTitle;
	}
	public void setTempTitle(String tempTitle) {
		this.tempTitle = tempTitle;
	}
	public String getTempContent() {
		return tempContent;
	}
	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
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
