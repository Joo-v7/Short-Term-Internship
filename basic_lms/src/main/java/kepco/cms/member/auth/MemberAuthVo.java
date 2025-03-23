package kepco.cms.member.auth;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class MemberAuthVo {
	
	private int authIdx;
	private int memberIdx;
	private String memberId;
	
	private String memberPw;

	private String memberNm;
	private String authKey;
	private String authType;
	private String findPw;
	private String findType;
	@Valid
	@Email
	private String memberEmail;
	
	@JsonView(jsonView.PRIVATE.class)
	private String insertDate;

	
	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
	}

	public String getFindPw() {
		return findPw;
	}

	public void setFindPw(String findPw) {
		this.findPw = findPw;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public int getAuthIdx() {
		return authIdx;
	}

	public void setAuthIdx(int authIdx) {
		this.authIdx = authIdx;
	}

	public int getMemberIdx() {
		return memberIdx;
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

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	
}