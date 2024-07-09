package kepco.cms.accessLog;
/**
 * 접속 로그
 */
public class AccessLogVo{
	/** 로그 IDX */
    private int logIdx;
    private String logMemberIdx;
    private String logIp;
    private String logSessionId;
    private String logDate;
    private String logAccess;
    
	public String getLogMemberIdx() {
		return logMemberIdx;
	}
	public void setLogMemberIdx(String logMemberIdx) {
		this.logMemberIdx = logMemberIdx;
	}
	public int getLogIdx() {
		return logIdx;
	}
	public void setLogIdx(int logIdx) {
		this.logIdx = logIdx;
	}
	public String getLogSessionId() {
		return logSessionId;
	}
	public void setLogSessionId(String logSessionId) {
		this.logSessionId = logSessionId;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getLogAccess() {
		return logAccess;
	}
	public void setLogAccess(String logAccess) {
		this.logAccess = logAccess;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

}