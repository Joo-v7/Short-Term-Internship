package kepco.cms.setting;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** CMS 설정 */
@Component
public class SettingVo {

	/** setting 항목 */

	private int settingIdx;
	
	private String settingKey;
	
	/** 내용 */
	private String settingValue;
	
	/** 주석 */
	private String settingComment;
	
	/** 등록 IDX */
	private int insertIdx;
	
	/** 등록 IP */
	private String insertIp;
	
	/** 등록일시 */
	private String insertDate;
	
	/** 수정 IDX */
	private int updateIdx;
	
	/** 수정 IP */
	private String updateIp;
	
	/** 수정일시 */
	private String updateDate;
	
	/** 삭제 IDX */
	private int deleteIdx;
	
	/** 삭제 IP */
	private String deleteIp;
	
	/** 삭제일시 */
	private String deleteDate;
	
	/** 삭제여부 */
	private String deleteYn;
	
	/** 유형 */
	private String settingType;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public int getSettingIdx() {
		return settingIdx;
	}


	public void setSettingIdx(int settingIdx) {
		this.settingIdx = settingIdx;
	}
	
	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}
	public String getSettingKey() {
		return this.settingKey;
	}
	
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	public String getSettingValue() {
		return this.settingValue;
	}
	
	public void setSettingComment(String settingComment) {
		this.settingComment = settingComment;
	}
	public String getSettingComment() {
		return this.settingComment;
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
	
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getInsertDate() {
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
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateDate() {
		return this.updateDate;
	}
	
	public void setDeleteIdx(int deleteIdx) {
		this.deleteIdx = deleteIdx;
	}
	public int getDeleteIdx() {
		return this.deleteIdx;
	}
	
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeleteIp() {
		return this.deleteIp;
	}
	
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getDeleteDate() {
		return this.deleteDate;
	}
	
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public String getDeleteYn() {
		return this.deleteYn;
	}
	
	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}
	public String getSettingType() {
		return this.settingType;
	}
	
}