package kepco.cms.sec.role;

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
public class RoleVo {

	/** 역할 IDX */
	private int roleIdx;
	
	/** 역할코드 */
	@NotBlank
	@Size(max = 20)
	private String roleCd;
	
	/** 역할명 */
	@NotBlank
	@Size(max = 100)
	private String roleNm;
	
	/** 상위 역할코드 */
	@NotBlank
	@Size(max = 100)
	private String parentRoleCd;
	
	/** 등록 IDX */
	private int insertIdx;
	
	/** 등록 IP */
	@Size(max = 20)
	private String insertIp;
	
	/** 등록일자 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	
	/** 수정 IDX */
	private int updateIdx;
	
	/** 수정 IP */
	@Size(max = 20)
	private String updateIp;
	
	/** 수정일자 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	/** 삭제 IDX */
	private int deleteIdx;
	
	/** 삭제 IP */
	@Size(max = 20)
	private String deleteIp;
	
	/** 삭제일자 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deleteDate;
	
	/** 사용여부 */
	@Size(max = 1)
	private String useYn;
	
	/** 삭제여부 */
	@Size(max = 1)
	private String deleteYn;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	
	public void setRoleIdx(int roleIdx) {
		this.roleIdx = roleIdx;
	}
	public int getRoleIdx() {
		return this.roleIdx;
	}
	
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getRoleCd() {
		return this.roleCd;
	}
	
	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}
	public String getRoleNm() {
		return this.roleNm;
	}
	
	public void setParentRoleCd(String parentRoleCd) {
		this.parentRoleCd = parentRoleCd;
	}
	public String getParentRoleCd() {
		return this.parentRoleCd;
	}
	
	public void setInsertIdx(int insertIdx) {
		this.insertIdx = insertIdx;
	}
	public int getInsertIdx() {
		return this.insertIdx;
	}
	
	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}
	public String getInsertIp() {
		return this.insertIp;
	}
	
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Date getInsertDate() {
		return this.insertDate;
	}
	
	public void setUpdateIdx(int updateIdx) {
		this.updateIdx = updateIdx;
	}
	public int getUpdateIdx() {
		return this.updateIdx;
	}
	
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getUpdateIp() {
		return this.updateIp;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getUpdateDate() {
		return this.updateDate;
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


	public Date getDeleteDate() {
		return deleteDate;
	}


	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUseYn() {
		return this.useYn;
	}


	public String getDeleteYn() {
		return deleteYn;
	}


	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	
}