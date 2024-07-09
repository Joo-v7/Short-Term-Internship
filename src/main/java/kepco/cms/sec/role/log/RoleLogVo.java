package kepco.cms.sec.role.log;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 역할 */
@Component
public class RoleLogVo {

	/** 역할 로그 IDX */
	private int roleLogIdx;
	
	/** 변경 전 역할 */
	private String beforeRole;
	
	/** 변경 후 역할 */
	private String afterRole;
	
	private int memberIdx;
	
	private String memberId;
	
	private String memberNm;
	

	private String insertId;
	
	private String insertNm;
	
	/** 등록 IDX */
	private int insertIdx;
	
	/** 등록 IP */
	@Size(max = 20)
	private String insertIp;
	
	/** 권한 변경일자 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	
	/** 수정 IDX */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getMemberNm() {
		return memberNm;
	}


	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}


	public int getRoleLogIdx() {
		return roleLogIdx;
	}

	public void setRoleLogIdx(int roleLogIdx) {
		this.roleLogIdx = roleLogIdx;
	}
	
	public int getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getBeforeRole() {
		return beforeRole;
	}

	public void setBeforeRole(String beforeRole) {
		this.beforeRole = beforeRole;
	}

	public String getAfterRole() {
		return afterRole;
	}

	public void setAfterRole(String afterRole) {
		this.afterRole = afterRole;
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

	public String getInsertId() {
		return insertId;
	}

	public void setInsertId(String insertId) {
		this.insertId = insertId;
	}

	public String getInsertNm() {
		return insertNm;
	}

	public void setInsertNm(String insertNm) {
		this.insertNm = insertNm;
	}
	
	
}