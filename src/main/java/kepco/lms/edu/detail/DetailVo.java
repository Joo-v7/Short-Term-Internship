package kepco.lms.edu.detail;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;
import kepco.lms.edu.pack.PackVo;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailVo{
	
	private int detailIdx;
	private int eduIdx;
	private String eduNumeral;
	private String eduImage1;
	private String eduImage2;
	private String eduDesc;
	private String eduGoal;
	private String eduTarget;
	private String eduCriteria;
	private int eduOrder;
	private int eduTeamCnt;
	private int eduLimitCnt;
	private int eduLimitCnt1;
	private int eduLimitCnt2;
	private int eduLimitCnt3;
	private int eduLimitCnt4;
	private String trainTitle;
	private int teacherIdx;
	private String teacherNm;
	private String eduCateNm;
	private String eduType;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptAuto;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptBgnTime;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptEndDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduAcceptEndTime;
	@JsonView(jsonView.PRIVATE.class)
	private String eduTrainBgnDate;
	@JsonView(jsonView.PRIVATE.class)
	private String eduTrainEndDate;
	private String eduDateDesc;
	private String eduFile1;
	private String eduFile2;
	private String eduFile3;
	private String eduFile4;
	private String eduFile5;
	private String detailState;
	private int poIdx;
	private int evIdx;
	
	private String memberNm;
	private String eduTitle;
	@JsonView(jsonView.PRIVATE.class)
	private String eduKeyword;
	private int eduRegCnt;
	private int eduBotCnt;
	private int eduAcceptCnt;

	private int regWaitCnt;
	private int regAcceptCnt;
	private int regRejectCnt;
	
	private String poSubject;
	private String evSubject;
	private int eduAcceptState;
	private int eduTrainState;
	
	private String playTimeAvg;
	private String playTimeMin;
	private String playTimeMax;
	
	private int totalCnt;
	private int esCnt;
	private int fallCnt;
	private int careCnt;
	private int loadCnt;
	private int accidentCnt;
	
	private int sumPlayMinute;
	private int playCnt;
	private int playerCnt;
	private int attemptMax;
	private int moduleCdCnt;
	private int playSuccessCnt;
	private int playFailCnt;
	
	public int getPlaySuccessCnt() {
		return playSuccessCnt;
	}
	public void setPlaySuccessCnt(int playSuccessCnt) {
		this.playSuccessCnt = playSuccessCnt;
	}
	public int getPlayFailCnt() {
		return playFailCnt;
	}
	public void setPlayFailCnt(int playFailCnt) {
		this.playFailCnt = playFailCnt;
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
	public int getPlayerCnt() {
		return playerCnt;
	}
	public void setPlayerCnt(int playerCnt) {
		this.playerCnt = playerCnt;
	}
	public int getAttemptMax() {
		return attemptMax;
	}
	public void setAttemptMax(int attemptMax) {
		this.attemptMax = attemptMax;
	}
	public int getModuleCdCnt() {
		return moduleCdCnt;
	}
	public void setModuleCdCnt(int moduleCdCnt) {
		this.moduleCdCnt = moduleCdCnt;
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

	@JsonView(jsonView.PRIVATE.class)
	private PackVo packVo;
	
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
	
	public String getPlayTimeAvg() {
		return playTimeAvg;
	}
	public void setPlayTimeAvg(String playTimeAvg) {
		this.playTimeAvg = playTimeAvg;
	}
	public String getPlayTimeMin() {
		return playTimeMin;
	}
	public void setPlayTimeMin(String playTimeMin) {
		this.playTimeMin = playTimeMin;
	}
	public String getPlayTimeMax() {
		return playTimeMax;
	}
	public void setPlayTimeMax(String playTimeMax) {
		this.playTimeMax = playTimeMax;
	}
	public int getRegWaitCnt() {
		return regWaitCnt;
	}
	public void setRegWaitCnt(int regWaitCnt) {
		this.regWaitCnt = regWaitCnt;
	}
	public int getRegAcceptCnt() {
		return regAcceptCnt;
	}
	public void setRegAcceptCnt(int regAcceptCnt) {
		this.regAcceptCnt = regAcceptCnt;
	}
	public int getRegRejectCnt() {
		return regRejectCnt;
	}
	public void setRegRejectCnt(int regRejectCnt) {
		this.regRejectCnt = regRejectCnt;
	}
	public int getEduOrder() {
		return eduOrder;
	}
	public void setEduOrder(int eduOrder) {
		this.eduOrder = eduOrder;
	}
	public String getEduType() {
		return eduType;
	}
	public void setEduType(String eduType) {
		this.eduType = eduType;
	}
	public String getEduCateNm() {
		return eduCateNm;
	}
	public void setEduCateNm(String eduCateNm) {
		this.eduCateNm = eduCateNm;
	}
	public int getEduAcceptCnt() {
		return eduAcceptCnt;
	}
	public void setEduAcceptCnt(int eduAcceptCnt) {
		this.eduAcceptCnt = eduAcceptCnt;
	}
	public int getEduTeamCnt() {
		return eduTeamCnt;
	}
	public void setEduTeamCnt(int eduTeamCnt) {
		this.eduTeamCnt = eduTeamCnt;
	}
	public int getDetailIdx() {
		return detailIdx;
	}
	public void setDetailIdx(int detailIdx) {
		this.detailIdx = detailIdx;
	}
	public int getEduIdx() {
		return eduIdx;
	}
	public void setEduIdx(int eduIdx) {
		this.eduIdx = eduIdx;
	}
	public String getEduNumeral() {
		return eduNumeral;
	}
	public void setEduNumeral(String eduNumeral) {
		this.eduNumeral = eduNumeral;
	}
	public String getEduImage1() {
		return eduImage1;
	}
	public void setEduImage1(String eduImage1) {
		this.eduImage1 = eduImage1;
	}
	public String getEduImage2() {
		return eduImage2;
	}
	public void setEduImage2(String eduImage2) {
		this.eduImage2 = eduImage2;
	}
	public String getEduDesc() {
		return eduDesc;
	}
	public void setEduDesc(String eduDesc) {
		this.eduDesc = eduDesc;
	}
	public String getEduGoal() {
		return eduGoal;
	}
	public void setEduGoal(String eduGoal) {
		this.eduGoal = eduGoal;
	}
	public String getEduTarget() {
		return eduTarget;
	}
	public void setEduTarget(String eduTarget) {
		this.eduTarget = eduTarget;
	}
	public String getEduCriteria() {
		return eduCriteria;
	}
	public void setEduCriteria(String eduCriteria) {
		this.eduCriteria = eduCriteria;
	}
	public int getEduLimitCnt() {
		return eduLimitCnt;
	}
	public void setEduLimitCnt(int eduLimitCnt) {
		this.eduLimitCnt = eduLimitCnt;
	}
	public int getEduLimitCnt1() {
		return eduLimitCnt1;
	}
	public void setEduLimitCnt1(int eduLimitCnt1) {
		this.eduLimitCnt1 = eduLimitCnt1;
	}
	public int getEduLimitCnt2() {
		return eduLimitCnt2;
	}
	public void setEduLimitCnt2(int eduLimitCnt2) {
		this.eduLimitCnt2 = eduLimitCnt2;
	}
	public int getEduLimitCnt3() {
		return eduLimitCnt3;
	}
	public void setEduLimitCnt3(int eduLimitCnt3) {
		this.eduLimitCnt3 = eduLimitCnt3;
	}
	public int getEduLimitCnt4() {
		return eduLimitCnt4;
	}
	public void setEduLimitCnt4(int eduLimitCnt4) {
		this.eduLimitCnt4 = eduLimitCnt4;
	}
	public String getEduAcceptAuto() {
		return eduAcceptAuto;
	}
	public void setEduAcceptAuto(String eduAcceptAuto) {
		this.eduAcceptAuto = eduAcceptAuto;
	}
	public String getEduAcceptBgnDate() {
		return eduAcceptBgnDate;
	}
	public void setEduAcceptBgnDate(String eduAcceptBgnDate) {
		this.eduAcceptBgnDate = eduAcceptBgnDate;
	}
	public String getEduAcceptBgnTime() {
		return eduAcceptBgnTime;
	}
	public void setEduAcceptBgnTime(String eduAcceptBgnTime) {
		this.eduAcceptBgnTime = eduAcceptBgnTime;
	}
	public String getEduAcceptEndDate() {
		return eduAcceptEndDate;
	}
	public void setEduAcceptEndDate(String eduAcceptEndDate) {
		this.eduAcceptEndDate = eduAcceptEndDate;
	}
	public String getEduAcceptEndTime() {
		return eduAcceptEndTime;
	}
	public void setEduAcceptEndTime(String eduAcceptEndTime) {
		this.eduAcceptEndTime = eduAcceptEndTime;
	}
	public String getEduTrainBgnDate() {
		return eduTrainBgnDate;
	}
	public void setEduTrainBgnDate(String eduTrainBgnDate) {
		this.eduTrainBgnDate = eduTrainBgnDate;
	}
	public String getEduTrainEndDate() {
		return eduTrainEndDate;
	}
	public void setEduTrainEndDate(String eduTrainEndDate) {
		this.eduTrainEndDate = eduTrainEndDate;
	}
	public String getEduDateDesc() {
		return eduDateDesc;
	}
	public void setEduDateDesc(String eduDateDesc) {
		this.eduDateDesc = eduDateDesc;
	}
	public String getEduFile1() {
		return eduFile1;
	}
	public void setEduFile1(String eduFile1) {
		this.eduFile1 = eduFile1;
	}
	public String getEduFile2() {
		return eduFile2;
	}
	public void setEduFile2(String eduFile2) {
		this.eduFile2 = eduFile2;
	}
	public String getEduFile3() {
		return eduFile3;
	}
	public void setEduFile3(String eduFile3) {
		this.eduFile3 = eduFile3;
	}
	public String getEduFile4() {
		return eduFile4;
	}
	public void setEduFile4(String eduFile4) {
		this.eduFile4 = eduFile4;
	}
	public String getEduFile5() {
		return eduFile5;
	}
	public void setEduFile5(String eduFile5) {
		this.eduFile5 = eduFile5;
	}
	public String getDetailState() {
		return detailState;
	}
	public void setDetailState(String detailState) {
		this.detailState = detailState;
	}
	public int getPoIdx() {
		return poIdx;
	}
	public void setPoIdx(int poIdx) {
		this.poIdx = poIdx;
	}
	public int getEvIdx() {
		return evIdx;
	}
	public void setEvIdx(int evIdx) {
		this.evIdx = evIdx;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getEduTitle() {
		return eduTitle;
	}
	public void setEduTitle(String eduTitle) {
		this.eduTitle = eduTitle;
	}
	public String getEduKeyword() {
		return eduKeyword;
	}
	public void setEduKeyword(String eduKeyword) {
		this.eduKeyword = eduKeyword;
	}
	public int getEduRegCnt() {
		return eduRegCnt;
	}
	public void setEduRegCnt(int eduRegCnt) {
		this.eduRegCnt = eduRegCnt;
	}
	public int getEduBotCnt() {
		return eduBotCnt;
	}
	public void setEduBotCnt(int eduBotCnt) {
		this.eduBotCnt = eduBotCnt;
	}
	public String getPoSubject() {
		return poSubject;
	}
	public void setPoSubject(String poSubject) {
		this.poSubject = poSubject;
	}
	public String getEvSubject() {
		return evSubject;
	}
	public void setEvSubject(String evSubject) {
		this.evSubject = evSubject;
	}
	public int getEduAcceptState() {
		return eduAcceptState;
	}
	public void setEduAcceptState(int eduAcceptState) {
		this.eduAcceptState = eduAcceptState;
	}
	public int getEduTrainState() {
		return eduTrainState;
	}
	public void setEduTrainState(int eduTrainState) {
		this.eduTrainState = eduTrainState;
	}
	public PackVo getPackVo() {
		return packVo;
	}
	public void setPackVo(PackVo packVo) {
		this.packVo = packVo;
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
	
	public String getTrainTitle() {
		return trainTitle;
	}
	public void setTrainTitle(String trainTitle) {
		this.trainTitle = trainTitle;
	}
	public int getTeacherIdx() {
		return teacherIdx;
	}
	public void setTeacherIdx(int teacherIdx) {
		this.teacherIdx = teacherIdx;
	}
	public String getTeacherNm() {
		return teacherNm;
	}
	public void setTeacherNm(String teacherNm) {
		this.teacherNm = teacherNm;
	}
}