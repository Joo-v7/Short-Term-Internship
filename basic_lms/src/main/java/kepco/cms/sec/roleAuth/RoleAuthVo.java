package kepco.cms.sec.roleAuth;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 역할별 권한 */
@Component
public class RoleAuthVo {

	/** 역할코드 */
	@NotBlank
	@Size(max = 20)
	private String roleCd;
	
	/** 권한코드 */
	@NotBlank
	@Size(max = 20)
	private String authCd;
	
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
	
	/** 삭제여부 */
	@Size(max = 1)
	private String deleteYn;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getRoleCd() {
		return this.roleCd;
	}
	
	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}
	public String getAuthCd() {
		return this.authCd;
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


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
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


	public Date getDeleteDate() {
		return deleteDate;
	}


	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}


	public String getDeleteYn() {
		return deleteYn;
	}


	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	
}