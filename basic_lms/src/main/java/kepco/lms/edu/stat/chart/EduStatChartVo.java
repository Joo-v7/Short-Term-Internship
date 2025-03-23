package kepco.lms.edu.stat.chart;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EduStatChartVo {

	private int minRiskCnt;
	private int maxRiskCnt;
	private int avgRiskCnt;
	
	private int memberIdx;
	private String memberId;
	private String memberNm;
	private int accidentCnt;
	private int riskCnt;
	private int sumPlayMinute;
	private int playCnt;
	private int playerCnt;
	
	private String objectFactor;
	private String accidentType;
	private int objectFactorCnt;
	private String accidentCause;
	private int accidentCauseCnt;
	
	private int accidentTypeCnt;
	
	private String eventLocation;
	private int eventLocationCnt;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	
	public int getRiskCnt() {
		return riskCnt;
	}
	public void setRiskCnt(int riskCnt) {
		this.riskCnt = riskCnt;
	}
	public int getEsCnt() {
		return esCnt;
	}
	public void setEsCnt(int esCnt) {
		this.esCnt = esCnt;
	}
	public int getFallCnt() {
		return fallCnt;
	}
	public void setFallCnt(int fallCnt) {
		this.fallCnt = fallCnt;
	}
	public int getCareCnt() {
		return careCnt;
	}
	public void setCareCnt(int careCnt) {
		this.careCnt = careCnt;
	}
	public int getLoadCnt() {
		return loadCnt;
	}
	public void setLoadCnt(int loadCnt) {
		this.loadCnt = loadCnt;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPlayerCnt() {
		return playerCnt;
	}
	public void setPlayerCnt(int playerCnt) {
		this.playerCnt = playerCnt;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public int getEventLocationCnt() {
		return eventLocationCnt;
	}
	public void setEventLocationCnt(int eventLocationCnt) {
		this.eventLocationCnt = eventLocationCnt;
	}
	public int getAccidentTypeCnt() {
		return accidentTypeCnt;
	}
	public void setAccidentTypeCnt(int accidentTypeCnt) {
		this.accidentTypeCnt = accidentTypeCnt;
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
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public int getAccidentCnt() {
		return accidentCnt;
	}
	public void setAccidentCnt(int accidentCnt) {
		this.accidentCnt = accidentCnt;
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
	public String getObjectFactor() {
		return objectFactor;
	}
	public void setObjectFactor(String objectFactor) {
		this.objectFactor = objectFactor;
	}
	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public int getObjectFactorCnt() {
		return objectFactorCnt;
	}
	public void setObjectFactorCnt(int objectFactorCnt) {
		this.objectFactorCnt = objectFactorCnt;
	}
	public String getAccidentCause() {
		return accidentCause;
	}
	public void setAccidentCause(String accidentCause) {
		this.accidentCause = accidentCause;
	}
	public int getAccidentCauseCnt() {
		return accidentCauseCnt;
	}
	public void setAccidentCauseCnt(int accidentCauseCnt) {
		this.accidentCauseCnt = accidentCauseCnt;
	}
	public int getMinRiskCnt() {
		return minRiskCnt;
	}
	public void setMinRiskCnt(int minRiskCnt) {
		this.minRiskCnt = minRiskCnt;
	}
	public int getMaxRiskCnt() {
		return maxRiskCnt;
	}
	public void setMaxRiskCnt(int maxRiskCnt) {
		this.maxRiskCnt = maxRiskCnt;
	}
	public int getAvgRiskCnt() {
		return avgRiskCnt;
	}
	public void setAvgRiskCnt(int avgRiskCnt) {
		this.avgRiskCnt = avgRiskCnt;
	}
	
}