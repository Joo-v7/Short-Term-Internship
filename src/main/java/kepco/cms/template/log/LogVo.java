package kepco.cms.template.log;

public class LogVo {
	
	private int logIdx;
	private int tempIdx;
	private String tempTitle;
	private String tempCode1;
	private String tempCode2;
	private String tempContent;

	private String insertIdx;
	private String insertIp;
	private String insertDate;
	
	public int getLogIdx() {
		return logIdx;
	}
	public void setLogIdx(int logIdx) {
		this.logIdx = logIdx;
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
	public String getTempContent() {
		return tempContent;
	}
	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}
	public String getInsertIdx() {
		return insertIdx;
	}
	public void setInsertIdx(String insertIdx) {
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