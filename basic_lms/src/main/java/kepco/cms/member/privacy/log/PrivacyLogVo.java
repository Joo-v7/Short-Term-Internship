package kepco.cms.member.privacy.log;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 개인정보 수정 로그  */
@Component
public class PrivacyLogVo {

	/**  */
	private int logIdx;
	
	/** 요청 구분 */
	private String requestType;
	
	/** 개인정보 수정된 사용자 IDX */
	private int memberIdx;
	
	/** 개인정보 수정된 사용자 아이디 */
	private String memberId;
	
	/** 사용자 이름 */
	private String memberNm;
	
	/** 요청자 IDX */
	private String requestIdx;
	
	/** 요청자 ID */
	private String requestId;
	
	/** 요청자 이름 */
	private String requestNm;
	
	/** 요청자 IP */
	private String requestIp;
	
	/** 요청일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date requestDate;
	
	/** 요청 내용 */
	private String requestDesc;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	
	public void setLogIdx(int logIdx) {
		this.logIdx = logIdx;
	}
	public int getLogIdx() {
		return this.logIdx;
	}
	
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestType() {
		return this.requestType;
	}
	
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public int getMemberIdx() {
		return this.memberIdx;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberId() {
		return this.memberId;
	}
	
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getMemberNm() {
		return this.memberNm;
	}
	
	public void setRequestIdx(String requestIdx) {
		this.requestIdx = requestIdx;
	}
	public String getRequestIdx() {
		return this.requestIdx;
	}
	
	public String getRequestId() {
		return requestId;
	}


	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


	public String getRequestNm() {
		return requestNm;
	}


	public void setRequestNm(String requestNm) {
		this.requestNm = requestNm;
	}


	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getRequestIp() {
		return this.requestIp;
	}
	
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getRequestDate() {
		return this.requestDate;
	}
	
	public void setRequestDesc(String requestDesc) {
		this.requestDesc = requestDesc;
	}

	public String getRequestDesc() {
		return requestDesc;
	}

}