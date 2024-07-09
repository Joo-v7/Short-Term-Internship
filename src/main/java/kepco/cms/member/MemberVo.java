package kepco.cms.member;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class MemberVo {
	
	private int memberIdx;
	private String memberId;
	
	private String memberPw;

	private String memberNm;
	
	@Valid
	@Email
	private String memberEmail;
	
	@Min(0)
	@Max(200)
	private int memberAge;
	
	private String memberBirthType;
	private String memberBirth;
	
	private String memberGender;
	
	private String memberPhone;
	
	private String memberRole;
	private String memberRoleOG;
	private String memberType1;
	
	private String memberType2;
	
	private String loginDate;

	private String memberDi;
	private String memberCi;

	private String memberApproval;
	
	private int loginFail;
	private int memberTempIdx;
	private int registCnt;
	private int eduCnt;
	
	/** 훈련수행 횟수 */
	private int playCount;
	
	/** 훈련시작일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/** 훈련종료일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/** 훈련수행 시간(분) */
	private int sumPlayMinute;
	
	@JsonView(jsonView.PRIVATE.class)
	private int rn;
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	@JsonView(jsonView.PRIVATE.class)
	private String insertDate;
	@JsonView(jsonView.PRIVATE.class)
	private int updateIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String updateIp;
	@JsonView(jsonView.PRIVATE.class)
	private String updateDate;
	@JsonView(jsonView.PRIVATE.class)
	private int deleteIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteIp;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteDate;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteYn;
	//
	public int getMemberIdx() {
		return memberIdx;
	}
	public String getMemberApproval() {
		return memberApproval;
	}
	public void setMemberApproval(String memberApproval) {
		this.memberApproval = memberApproval;
	}
	public int getRegistCnt() {
		return registCnt;
	}
	public void setRegistCnt(int registCnt) {
		this.registCnt = registCnt;
	}
	public int getEduCnt() {
		return eduCnt;
	}
	public void setEduCnt(int eduCnt) {
		this.eduCnt = eduCnt;
	}
	public String getMemberDi() {
		return memberDi;
	}
	public void setMemberDi(String memberDi) {
		this.memberDi = memberDi;
	}
	public String getMemberCi() {
		return memberCi;
	}
	public void setMemberCi(String memberCi) {
		this.memberCi = memberCi;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public int getMemberAge() {
		return memberAge;
	}
	public void setMemberAge(int memberAge) {
		this.memberAge = memberAge;
	}
	public String getMemberBirthType() {
		return memberBirthType;
	}
	public void setMemberBirthType(String memberBirthType) {
		this.memberBirthType = memberBirthType;
	}
	public String getMemberBirth() {
		return memberBirth;
	}
	public void setMemberBirth(String memberBirth) {
		this.memberBirth = memberBirth;
	}
	public String getMemberGender() {
		return memberGender;
	}
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberRole() {
		return memberRole;
	}
	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}
	public String getMemberType1() {
		return memberType1;
	}
	public void setMemberType1(String memberType1) {
		this.memberType1 = memberType1;
	}
	public String getMemberType2() {
		return memberType2;
	}
	public void setMemberType2(String memberType2) {
		this.memberType2 = memberType2;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public int getLoginFail() {
		return loginFail;
	}
	public void setLoginFail(int loginFail) {
		this.loginFail = loginFail;
	}
	public int getMemberTempIdx() {
		return memberTempIdx;
	}
	public void setMemberTempIdx(int memberTempIdx) {
		this.memberTempIdx = memberTempIdx;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
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
	public String getMemberRoleOG() {
		return memberRoleOG;
	}
	public void setMemberRoleOG(String memberRoleOG) {
		this.memberRoleOG = memberRoleOG;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getSumPlayMinute() {
		return sumPlayMinute;
	}
	public void setSumPlayMinute(int sumPlayMinute) {
		this.sumPlayMinute = sumPlayMinute;
	}
	
	
}