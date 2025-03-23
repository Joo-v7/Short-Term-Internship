package kepco.cms.zzz.crud;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class CrudVo {
	
	private Long crudIdx;
	
	private Long userIdx;
	
	@Size(min = 2, max = 20, message = "성명은 {min}글자 이상 {max}자 이하여야 합니다. 현재 ${validatedValue.length()}글자 입니다.")
	private String userNm;
	
	@Valid
	@Email
	private String userEmail;
	
	@Min(0)
	@Max(200)
	private Integer userAge;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}")
	private String userBirth;

	@NotBlank
	@Length(max = 1)
	private String userGender;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public Long getCrudIdx() {
		return crudIdx;
	}

	public void setCrudId(Long crudIdx) {
		this.crudIdx = crudIdx;
	}
	
	public Long getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(Long userIdx) {
		this.userIdx = userIdx;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
