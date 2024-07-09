package kepco.lms.edu.stat;

import java.util.List;
import java.util.Map;

import kepco.util.CamelMap;
import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.EgovAbstractDAO;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.lms.edu.EduVo;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.event.EventVo;
import kepco.lms.edu.stat.chart.EduStatChartVo;
import kepco.lms.edu.stat.usr.EduStatUsrVo;


@Mapper
public interface EdustatMapper {

    public List<EdustatVo> selectList(EgovMap req);

    public List<EdustatVo> selectAll(EgovMap req);

    public EgovMap select(EgovMap req);

    public DetailVo detailStat(EgovMap req);

    public EduVo eduStat(EgovMap req);

    public EdustatVo statOverview(EgovMap req);

    public EdustatVo playTime(EgovMap req);

    public EdustatVo riskFactorByPeriod(EgovMap req);

    public EdustatVo accidentReasonRank(EgovMap req);

    public EdustatVo accidentReasonRankCnt(EgovMap req);

    public EdustatVo shareByRole(EgovMap req);

    public List<EdustatVo> shareByAccident(EgovMap req);

    public EdustatVo accidentCntByRole(EgovMap req);

    public List<EdustatVo> accidentLocation(EgovMap req);

    public List<EdustatVo> accidentObjectFactor(EgovMap req);

    public List<String> accidentTypeNm(EgovMap req);

    public List<EduStatChartVo> accidentType(EgovMap req);

    public List<EduStatChartVo> accidentCause(EgovMap req);

    public EduStatChartVo riskStatusByTeam(EgovMap req);

    public EduStatChartVo riskStatusByUsr(EgovMap req);

    public Object dashBoardStat(EgovMap req);

    public List<EdustatVo> dashBoardProcessList(EgovMap req);

    public List<EdustatVo> dashBoardFamousList(EgovMap req);

    public List<EduStatUsrVo> usrList(EgovMap req);

    public List<EduStatUsrVo> usrPlayList(EgovMap req);

    public List<EduStatChartVo> accidentRateByTrain(EgovMap req);

    public List<String> accidentReasonAnalysisRoleList(EgovMap req);

    public List<EduStatChartVo> accidentReasonAnalysisLocation(EgovMap req);

    public List<EduStatChartVo> accidentReasonAnalysisFactor(EgovMap req);

    public List<EduStatChartVo> accidentReasonAnalysisType(EgovMap req);

    public List<EduStatChartVo> trainStatByPeriod(EgovMap req);

    public List<EduStatChartVo> riskFactorStatByPeriod(EgovMap req);

    public List<EduStatChartVo> myTrainStatByPeriod(EgovMap req);

    // 만족도조사 list 데이터
    public List<CamelMap> selectPollList(EgovMap req);

    // 만족도조사 통계현황 list 데이터
    public List<CamelMap> selectOverviewList(EgovMap req);

    // 만족도조사 view 훈련상세 데이터
    public CamelMap selectPollViewDetailData(EgovMap req);

    // 만족도조사 질문 리스트
    public List<CamelMap> pollQsonList(EgovMap req);

    // 만족도조사 상세 질문 목록
    public List<CamelMap> pollQsonDetailList(EgovMap req);

    // 만족도조사 상세 질문 리스트 : 객관식
    public List<CamelMap> pollQsonDetailNumList(EgovMap req);

    // 만족도조사 상세 질문 리스트 : 객관식
    public List<CamelMap> pollQsonDetailDescList(EgovMap req);

    // 만족도조사 통계현황 > 전체과정별 참여, 미참여 인원 비율
    public CamelMap pollPlayCountData(EgovMap req);

    // 만족도조사 과정별 참여인원 미참여인원 - XYChart
    public List<CamelMap> pollPlayCountXYData(EgovMap req);

    // 만족도조사 사용중인 훈련과정 - DataTable
    public List<CamelMap> pollPlayDataTable(EgovMap req);

    // 사후평가 리스트 목록
    public List<CamelMap> selectEvalList(EgovMap req);

    // 사후평가 통계 상세 데이터
    public CamelMap selectEvalViewDetailData(EgovMap req);

    // 사후평가 문제 목록
    public List<CamelMap> evalQsonList(EgovMap req);

    // 사후평가 상세 질문 목록
    public List<CamelMap> evalQsonDetailList(EgovMap req);

    // 사후평과 객관식 답안
    public List<CamelMap> evalQsonDetailNumList(EgovMap req);

    // 사후평가 주관식 목록
    List<CamelMap> evalQsonDescList(EgovMap req);

    // 사후평가 통계현황 - 사후평가 목록
    List<CamelMap> selectEvalOverviewList(EgovMap req);

    // 사후평가 통계현황 > 전체 과정별 참여, 미참여 인원
    CamelMap evalPlayCountData(EgovMap req);

    // 사후평가 통계현황 > 과정별 참여인원 미참여인원 - XYChart
    public List<CamelMap> evalPlayCountXYData(EgovMap req);

    // 사후평가 통계현황 > 사용중인 훈련과정 - DataTable
    public List<CamelMap> evalPlayDataTable(EgovMap req);
}
