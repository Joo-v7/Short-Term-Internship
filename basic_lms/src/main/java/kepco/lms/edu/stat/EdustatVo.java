package kepco.lms.edu.stat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdustatVo {

	private int statIdx;
	private int eduIdx;
	private int detailIdx;
	private int moduleIdx;
	private int memberIdx;
	
	private String memberNm;
	private String memberId;
	private String mainRole; // 주할당역할
	private String mainRiskFactor; //취약위험요인
	private String team;
	
	private int teamIdx;
	private String registRole;
	private String registState;
	private float playTime;
	private float playScore;
	private String statData;
	
	private String eduTitle;
	private String moduleTitle;
	private int statCnt;
	
	private int rn;
	private int insertIdx;
	private String insertIp;
	private String insertDate;
	
	private String trainTitle;
	private String teacherNm;
	
	private String playTimeAvg;
	private String playTimeMax;
	private String playTimeMin;
	
	private int totalCnt;
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	private int accidentCnt;
	private int teom;
	
	private String accidentReasonRank1;
	private String accidentReasonRank2;
	private String accidentReasonRank3;
	private String accidentReasonRank4;
	private String accidentReasonRank5;
	private int accidentReasonRankCnt1;
	private int accidentReasonRankCnt2;
	private int accidentReasonRankCnt3;
	private int accidentReasonRankCnt4;
	private int accidentReasonRankCnt5;
	
	private int mainCnt;
	private int subCnt;
	private int groundCnt;
	private int superCnt;
	
	private String accidentLocation;
	private int accidentLocationCnt;
	
	private String accidentObjectFactor;
	private int accidentObjectFactorCnt;
	
	private int mainEsCnt;
	private int subEsCnt;
	private int groundEsCnt;
	private int superEsCnt;
	private int mainFallCnt;
	private int subFallCnt;
	private int groundFallCnt;
	private int superFallCnt;
	private int mainCareCnt;
	private int subCareCnt;
	private int groundCareCnt;
	private int superCareCnt;
	private int mainLoadCnt;
	private int subLoadCnt;
	private int groundLoadCnt;
	private int superLoadCnt;
	
	private int mainAccidentCnt;
	private int subAccidentCnt;
	private int groundAccidentCnt;
	private int superAccidentCnt;
	
	private String accidentType;
	
	private String mostRiskByMain;
	private String mostRiskBySub;
	private String mostRiskByGround;
	private String mostRiskBySuper;
	
	public String getMostRiskByMain() {
		return mostRiskByMain;
	}
	public void setMostRiskByMain(String mostRiskByMain) {
		this.mostRiskByMain = mostRiskByMain;
	}
	public String getMostRiskBySub() {
		return mostRiskBySub;
	}
	public void setMostRiskBySub(String mostRiskBySub) {
		this.mostRiskBySub = mostRiskBySub;
	}
	public String getMostRiskByGround() {
		return mostRiskByGround;
	}
	public void setMostRiskByGround(String mostRiskByGround) {
		this.mostRiskByGround = mostRiskByGround;
	}
	public String getMostRiskBySuper() {
		return mostRiskBySuper;
	}
	public void setMostRiskBySuper(String mostRiskBySuper) {
		this.mostRiskBySuper = mostRiskBySuper;
	}
	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public int getMainAccidentCnt() {
		return mainAccidentCnt;
	}
	public void setMainAccidentCnt(int mainAccidentCnt) {
		this.mainAccidentCnt = mainAccidentCnt;
	}
	public int getSubAccidentCnt() {
		return subAccidentCnt;
	}
	public void setSubAccidentCnt(int subAccidentCnt) {
		this.subAccidentCnt = subAccidentCnt;
	}
	public int getGroundAccidentCnt() {
		return groundAccidentCnt;
	}
	public void setGroundAccidentCnt(int groundAccidentCnt) {
		this.groundAccidentCnt = groundAccidentCnt;
	}
	public int getSuperAccidentCnt() {
		return superAccidentCnt;
	}
	public void setSuperAccidentCnt(int superAccidentCnt) {
		this.superAccidentCnt = superAccidentCnt;
	}
	public int getMainEsCnt() {
		return mainEsCnt;
	}
	public void setMainEsCnt(int mainEsCnt) {
		this.mainEsCnt = mainEsCnt;
	}
	public int getSubEsCnt() {
		return subEsCnt;
	}
	public void setSubEsCnt(int subEsCnt) {
		this.subEsCnt = subEsCnt;
	}
	public int getGroundEsCnt() {
		return groundEsCnt;
	}
	public void setGroundEsCnt(int groundEsCnt) {
		this.groundEsCnt = groundEsCnt;
	}
	public int getSuperEsCnt() {
		return superEsCnt;
	}
	public void setSuperEsCnt(int superEsCnt) {
		this.superEsCnt = superEsCnt;
	}
	public int getMainFallCnt() {
		return mainFallCnt;
	}
	public void setMainFallCnt(int mainFallCnt) {
		this.mainFallCnt = mainFallCnt;
	}
	public int getSubFallCnt() {
		return subFallCnt;
	}
	public void setSubFallCnt(int subFallCnt) {
		this.subFallCnt = subFallCnt;
	}
	public int getGroundFallCnt() {
		return groundFallCnt;
	}
	public void setGroundFallCnt(int groundFallCnt) {
		this.groundFallCnt = groundFallCnt;
	}
	public int getSuperFallCnt() {
		return superFallCnt;
	}
	public void setSuperFallCnt(int superFallCnt) {
		this.superFallCnt = superFallCnt;
	}
	public int getMainCareCnt() {
		return mainCareCnt;
	}
	public void setMainCareCnt(int mainCareCnt) {
		this.mainCareCnt = mainCareCnt;
	}
	public int getSubCareCnt() {
		return subCareCnt;
	}
	public void setSubCareCnt(int subCareCnt) {
		this.subCareCnt = subCareCnt;
	}
	public int getGroundCareCnt() {
		return groundCareCnt;
	}
	public void setGroundCareCnt(int groundCareCnt) {
		this.groundCareCnt = groundCareCnt;
	}
	public int getSuperCareCnt() {
		return superCareCnt;
	}
	public void setSuperCareCnt(int superCareCnt) {
		this.superCareCnt = superCareCnt;
	}
	public int getMainLoadCnt() {
		return mainLoadCnt;
	}
	public void setMainLoadCnt(int mainLoadCnt) {
		this.mainLoadCnt = mainLoadCnt;
	}
	public int getSubLoadCnt() {
		return subLoadCnt;
	}
	public void setSubLoadCnt(int subLoadCnt) {
		this.subLoadCnt = subLoadCnt;
	}
	public int getGroundLoadCnt() {
		return groundLoadCnt;
	}
	public void setGroundLoadCnt(int groundLoadCnt) {
		this.groundLoadCnt = groundLoadCnt;
	}
	public int getSuperLoadCnt() {
		return superLoadCnt;
	}
	public void setSuperLoadCnt(int superLoadCnt) {
		this.superLoadCnt = superLoadCnt;
	}
	public String getAccidentObjectFactor() {
		return accidentObjectFactor;
	}
	public void setAccidentObjectFactor(String accidentObjectFactor) {
		this.accidentObjectFactor = accidentObjectFactor;
	}
	public int getAccidentObjectFactorCnt() {
		return accidentObjectFactorCnt;
	}
	public void setAccidentObjectFactorCnt(int accidentObjectFactorCnt) {
		this.accidentObjectFactorCnt = accidentObjectFactorCnt;
	}
	public String getAccidentLocation() {
		return accidentLocation;
	}
	public void setAccidentLocation(String accidentLocation) {
		this.accidentLocation = accidentLocation;
	}
	public int getAccidentLocationCnt() {
		return accidentLocationCnt;
	}
	public void setAccidentLocationCnt(int accidentLocationCnt) {
		this.accidentLocationCnt = accidentLocationCnt;
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
	public void setGroundCnt(int grountCnt) {
		this.groundCnt = grountCnt;
	}
	public int getSuperCnt() {
		return superCnt;
	}
	public void setSuperCnt(int superCnt) {
		this.superCnt = superCnt;
	}
	public int getAccidentReasonRankCnt1() {
		return accidentReasonRankCnt1;
	}
	public void setAccidentReasonRankCnt1(int accidentReasonRankCnt1) {
		this.accidentReasonRankCnt1 = accidentReasonRankCnt1;
	}
	public int getAccidentReasonRankCnt2() {
		return accidentReasonRankCnt2;
	}
	public void setAccidentReasonRankCnt2(int accidentReasonRankCnt2) {
		this.accidentReasonRankCnt2 = accidentReasonRankCnt2;
	}
	public int getAccidentReasonRankCnt3() {
		return accidentReasonRankCnt3;
	}
	public void setAccidentReasonRankCnt3(int accidentReasonRankCnt3) {
		this.accidentReasonRankCnt3 = accidentReasonRankCnt3;
	}
	public int getAccidentReasonRankCnt4() {
		return accidentReasonRankCnt4;
	}
	public void setAccidentReasonRankCnt4(int accidentReasonRankCnt4) {
		this.accidentReasonRankCnt4 = accidentReasonRankCnt4;
	}
	public int getAccidentReasonRankCnt5() {
		return accidentReasonRankCnt5;
	}
	public void setAccidentReasonRankCnt5(int accidentReasonRankCnt5) {
		this.accidentReasonRankCnt5 = accidentReasonRankCnt5;
	}
	public String getAccidentReasonRank1() {
		return accidentReasonRank1;
	}
	public void setAccidentReasonRank1(String accidentReasonRank1) {
		this.accidentReasonRank1 = accidentReasonRank1;
	}
	public String getAccidentReasonRank2() {
		return accidentReasonRank2;
	}
	public void setAccidentReasonRank2(String accidentReasonRank2) {
		this.accidentReasonRank2 = accidentReasonRank2;
	}
	public String getAccidentReasonRank3() {
		return accidentReasonRank3;
	}
	public void setAccidentReasonRank3(String accidentReasonRank3) {
		this.accidentReasonRank3 = accidentReasonRank3;
	}
	public String getAccidentReasonRank4() {
		return accidentReasonRank4;
	}
	public void setAccidentReasonRank4(String accidentReasonRank4) {
		this.accidentReasonRank4 = accidentReasonRank4;
	}
	public String getAccidentReasonRank5() {
		return accidentReasonRank5;
	}
	public void setAccidentReasonRank5(String accidentReasonRank5) {
		this.accidentReasonRank5 = accidentReasonRank5;
	}
	public int getTeom() {
		return teom;
	}
	public void setTeom(int teom) {
		this.teom = teom;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public int getAccidentCnt() {
		return accidentCnt;
	}
	public void setAccidentCnt(int accidentCnt) {
		this.accidentCnt = accidentCnt;
	}
	public String getPlayTimeAvg() {
		return playTimeAvg;
	}
	public void setPlayTimeAvg(String playTimeAvg) {
		this.playTimeAvg = playTimeAvg;
	}
	public String getPlayTimeMax() {
		return playTimeMax;
	}
	public void setPlayTimeMax(String playTimeMax) {
		this.playTimeMax = playTimeMax;
	}
	public String getPlayTimeMin() {
		return playTimeMin;
	}
	public void setPlayTimeMin(String playTimeMin) {
		this.playTimeMin = playTimeMin;
	}
	public int getEduIdx() {
		return eduIdx;
	}
	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}
	public int getStatIdx() {
		return statIdx;
	}
	public void setStatIdx(int statIdx) {
		this.statIdx = statIdx;
	}
	public int getDetailIdx() {
		return detailIdx;
	}
	public void setDetailIdx(int detailIdx) {
		this.detailIdx = detailIdx;
	}
	public int getModuleIdx() {
		return moduleIdx;
	}
	public void setModuleIdx(int moduleIdx) {
		this.moduleIdx = moduleIdx;
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	public String getRegistRole() {
		return registRole;
	}
	public void setRegistRole(String registRole) {
		this.registRole = registRole;
	}
	public String getRegistState() {
		return registState;
	}
	public void setRegistState(String registState) {
		this.registState = registState;
	}
	public float getPlayTime() {
		return playTime;
	}
	public void setPlayTime(float playTime) {
		this.playTime = playTime;
	}
	public float getPlayScore() {
		return playScore;
	}
	public void setPlayScore(float playScore) {
		this.playScore = playScore;
	}
	public String getStatData() {
		return statData;
	}
	public void setStatData(String statData) {
		this.statData = statData;
	}
	public String getEduTitle() {
		return eduTitle;
	}
	public void setEduTitle(String eduTitle) {
		this.eduTitle = eduTitle;
	}
	public int getStatCnt() {
		return statCnt;
	}
	public void setStatCnt(int statCnt) {
		this.statCnt = statCnt;
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
	public String getModuleTitle() {
		return moduleTitle;
	}
	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}
	public String getTrainTitle() {
		return trainTitle;
	}
	public void setTrainTitle(String trainTitle) {
		this.trainTitle = trainTitle;
	}
	public String getTeacherNm() {
		return teacherNm;
	}
	public void setTeacherNm(String teacherNm) {
		this.teacherNm = teacherNm;
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
	public String getMainRole() {
		return mainRole;
	}
	public void setMainRole(String mainRole) {
		this.mainRole = mainRole;
	}
	public String getMainRiskFactor() {
		return mainRiskFactor;
	}
	public void setMainRiskFactor(String mainRiskFactor) {
		this.mainRiskFactor = mainRiskFactor;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	
}