package kepco.lms.vod.learn;

import org.springframework.stereotype.Component;

@Component
public class LearnVo {

	private int learnIdx;
	private int vodIdx;
	private int contentIdx;
	private int memberIdx;
	private int learnTime;
	private int learnScore;
	private String learnData;
	private String presentTime;
	
	private String vodTitle;
	private int learnCnt;
	
	private int insertIdx;
	private String insertIp;
	private String insertDate;
	
	public String getPresentTime() {
		return presentTime;
	}
	public void setPresentTime(String presentTime) {
		this.presentTime = presentTime;
	}
	public int getVodIdx() {
		return vodIdx;
	}
	public void setVodIdx(int vodIdx) {
		this.vodIdx = vodIdx;
	}
	public int getLearnIdx() {
		return learnIdx;
	}
	public void setLearnIdx(int learnIdx) {
		this.learnIdx = learnIdx;
	}
	public int getContentIdx() {
		return contentIdx;
	}
	public void setContentIdx(int contentIdx) {
		this.contentIdx = contentIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public int getLearnTime() {
		return learnTime;
	}
	public void setLearnTime(int learnTime) {
		this.learnTime = learnTime;
	}
	public int getLearnScore() {
		return learnScore;
	}
	public void setLearnScore(int learnScore) {
		this.learnScore = learnScore;
	}
	public String getLearnData() {
		return learnData;
	}
	public void setLearnData(String learnData) {
		this.learnData = learnData;
	}
	public String getVodTitle() {
		return vodTitle;
	}
	public void setVodTitle(String vodTitle) {
		this.vodTitle = vodTitle;
	}
	public int getLearnCnt() {
		return learnCnt;
	}
	public void setLearnCnt(int learnCnt) {
		this.learnCnt = learnCnt;
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
	
}