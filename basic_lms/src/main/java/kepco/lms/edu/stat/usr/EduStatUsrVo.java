package kepco.lms.edu.stat.usr;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EduStatUsrVo {

	private int detailIdx;
	private int memberIdx;
	
	private String memberNm;
	private String memberId;
	private String teacherNm;
	
	private int rn;
	
	private int mainCnt;
	private int subCnt;
	private int groundCnt;
	private int superCnt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	private int sumPlayMinute;
	private int playCnt;
	
	private String trainTitle;
	private int playMinute;
	private int moduleCdCnt;
	private int modulePlayCnt;
	
	private String team;
	private String workRole;
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getWorkRole() {
		return workRole;
	}
	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}
	public String getTeacherNm() {
		return teacherNm;
	}
	public void setTeacherNm(String teacherNm) {
		this.teacherNm = teacherNm;
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
	public String getTrainTitle() {
		return trainTitle;
	}
	public void setTrainTitle(String trainTitle) {
		this.trainTitle = trainTitle;
	}
	public int getPlayMinute() {
		return playMinute;
	}
	public void setPlayMinute(int playMinute) {
		this.playMinute = playMinute;
	}
	public int getModuleCdCnt() {
		return moduleCdCnt;
	}
	public void setModuleCdCnt(int moduleCdCnt) {
		this.moduleCdCnt = moduleCdCnt;
	}
	public int getModulePlayCnt() {
		return modulePlayCnt;
	}
	public void setModulePlayCnt(int modulePlayCnt) {
		this.modulePlayCnt = modulePlayCnt;
	}
	public int getDetailIdx() {
		return detailIdx;
	}
	public void setDetailIdx(int detailIdx) {
		this.detailIdx = detailIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
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
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	public int getMainCnt() {
		return mainCnt;
	}
	public void setMainCnt(int mainCnt) {
		this.mainCnt = mainCnt;
	}
	public int getSubCnt() {
		return subCnt;
	}
	public void setSubCnt(int subCnt) {
		this.subCnt = subCnt;
	}
	public int getGroundCnt() {
		return groundCnt;
	}
	public void setGroundCnt(int groundCnt) {
		this.groundCnt = groundCnt;
	}
	public int getSuperCnt() {
		return superCnt;
	}
	public void setSuperCnt(int superCnt) {
		this.superCnt = superCnt;
	}
	public int getSumPlayMinute() {
		return sumPlayMinute;
	}
	public void setSumPlayMinute(int sumPlayMinute) {
		this.sumPlayMinute = sumPlayMinute;
	}
	public int getPlayCnt() {
		return playCnt;
	}
	public void setPlayCnt(int playCnt) {
		this.playCnt = playCnt;
	}
	
}