package kepco.lms.edu.team;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;
import kepco.lms.edu.detail.DetailVo;

@Component
public class TeamVo {

	private int teamIdx;
	private int eduIdx;
	private int detailIdx;
	private int teamMax;
	private String teamNm;
	
	private String eduTitle;
	private String eduNumeral;
	private int teamCnt;
	
	@JsonView(jsonView.PRIVATE.class)
	private int rn;
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	@JsonView(jsonView.PRIVATE.class)
	private String insertDate;

	public String getEduNumeral() {
		return eduNumeral;
	}
	public void setEduNumeral(String eduNumeral) {
		this.eduNumeral = eduNumeral;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	public int getEduIdx() {
		return eduIdx;
	}
	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}
	public int getDetailIdx() {
		return detailIdx;
	}
	public void setDetailIdx(int detailIdx) {
		this.detailIdx = detailIdx;
	}
	public int getTeamMax() {
		return teamMax;
	}
	public void setTeamMax(int teamMax) {
		this.teamMax = teamMax;
	}
	public String getTeamNm() {
		return teamNm;
	}
	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
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
	public int getTeamCnt() {
		return teamCnt;
	}
	public void setTeamCnt(int teamCnt) {
		this.teamCnt = teamCnt;
	}
	public String getEduTitle() {
		return eduTitle;
	}
	public void setEduTitle(String eduTitle) {
		this.eduTitle = eduTitle;
	}
}