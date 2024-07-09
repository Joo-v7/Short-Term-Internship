package kepco.cms.systemLog;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class SystemLogVo {
	private int logIdx;
	private String systemType;
	private String logMemberIdx;
	private String logIp;
	private String logSessionId;
	private String logDate;
	private String logAccess;
	private String memberNm;
	private String memberId;
	private String useLocation;
	
	private int saveCnt;
	private int listCnt;
	private int deleteCnt;
	
	public int getSaveCnt() {
		return saveCnt;
	}
	public void setSaveCnt(int saveCnt) {
		this.saveCnt = saveCnt;
	}
	public int getListCnt() {
		return listCnt;
	}
	public void setListCnt(int listCnt) {
		this.listCnt = listCnt;
	}
	public int getDeleteCnt() {
		return deleteCnt;
	}
	public void setDeleteCnt(int deleteCnt) {
		this.deleteCnt = deleteCnt;
	}
	public String getUseLocation() {
		return useLocation;
	}
	public void setUseLocation(String useLocation) {
		this.useLocation = useLocation;
	}
	public int getLogIdx() {
		return logIdx;
	}
	public void setLogIdx(int logIdx) {
		this.logIdx = logIdx;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getLogMemberIdx() {
		return logMemberIdx;
	}
	public void setLogMemberIdx(String logMemberIdx) {
		this.logMemberIdx = logMemberIdx;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
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
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}