package kepco.lms.edu.stat;

import java.util.List;
import java.util.Map;

import kepco.util.CamelMap;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kepco.lms.edu.EduVo;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.event.EventVo;
import kepco.lms.edu.stat.chart.EduStatChartVo;
import kepco.lms.edu.stat.usr.EduStatUsrVo;
import kepco.util.StrUtil;

@Service
public class EdustatService {

    @Autowired
    private EdustatMapper eduStatMapper;


    public List<EdustatVo> selectList(EgovMap req) {

        int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
        int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);

        return eduStatMapper.selectList(req);
    }

    public List<EdustatVo> selectAll(EgovMap req) {
        return eduStatMapper.selectList(req);
    }

    public EgovMap select(EgovMap req) {
        return eduStatMapper.select(req);
    }

    public DetailVo detailStat(EgovMap req) {
        return eduStatMapper.detailStat(req);
    }

    public EduVo eduStat(EgovMap req) {
        return eduStatMapper.eduStat(req);
    }

    public EdustatVo statOverview(EgovMap req) {
        return eduStatMapper.statOverview(req);
    }

    public EdustatVo riskFactorByPeriod(EgovMap req) {
        return eduStatMapper.riskFactorByPeriod(req);
    }

    public EdustatVo accidentReasonRank(EgovMap req) {
        return eduStatMapper.accidentReasonRank(req);
    }

    public EdustatVo accidentReasonRankCnt(EgovMap req) {
        return eduStatMapper.accidentReasonRankCnt(req);
    }

    public EdustatVo shareByRole(EgovMap req) {
        return eduStatMapper.shareByRole(req);
    }

    public List<EdustatVo> shareByAccident(EgovMap req) {
        return eduStatMapper.shareByAccident(req);
    }

    public EdustatVo accidentCntByRole(EgovMap req) {
        EdustatVo edustatVo = eduStatMapper.accidentCntByRole(req);

        if (edustatVo.getMainEsCnt() == 0 && edustatVo.getMainFallCnt() == 0 && edustatVo.getMainCareCnt() == 0 && edustatVo.getMainLoadCnt() == 0) {
            edustatVo.setMostRiskByMain("-");
        } else if (edustatVo.getMainEsCnt() >= edustatVo.getMainFallCnt() && edustatVo.getMainEsCnt() >= edustatVo.getMainCareCnt() && edustatVo.getMainEsCnt() >= edustatVo.getMainLoadCnt()) {
            edustatVo.setMostRiskByMain("감전");
        } else if (edustatVo.getMainFallCnt() > edustatVo.getMainEsCnt() && edustatVo.getMainFallCnt() >= edustatVo.getMainCareCnt() && edustatVo.getMainFallCnt() >= edustatVo.getMainLoadCnt()) {
            edustatVo.setMostRiskByMain("추락");
        } else if (edustatVo.getMainCareCnt() > edustatVo.getMainEsCnt() && edustatVo.getMainCareCnt() > edustatVo.getMainFallCnt() && edustatVo.getMainCareCnt() >= edustatVo.getMainLoadCnt()) {
            edustatVo.setMostRiskByMain("부주의");
        } else if (edustatVo.getMainLoadCnt() > edustatVo.getMainEsCnt() && edustatVo.getMainLoadCnt() > edustatVo.getMainFallCnt() && edustatVo.getMainLoadCnt() > edustatVo.getMainCareCnt()) {
            edustatVo.setMostRiskByMain("작업부하");
        }

        if (edustatVo.getSubEsCnt() == 0 && edustatVo.getSubFallCnt() == 0 && edustatVo.getSubCareCnt() == 0 && edustatVo.getSubLoadCnt() == 0) {
            edustatVo.setMostRiskBySub("-");
        } else if (edustatVo.getSubEsCnt() >= edustatVo.getSubFallCnt() && edustatVo.getSubEsCnt() >= edustatVo.getSubCareCnt() && edustatVo.getSubEsCnt() >= edustatVo.getSubLoadCnt()) {
            edustatVo.setMostRiskBySub("감전");
        } else if (edustatVo.getSubFallCnt() > edustatVo.getSubEsCnt() && edustatVo.getSubFallCnt() >= edustatVo.getSubCareCnt() && edustatVo.getSubFallCnt() >= edustatVo.getSubLoadCnt()) {
            edustatVo.setMostRiskBySub("추락");
        } else if (edustatVo.getSubCareCnt() > edustatVo.getSubEsCnt() && edustatVo.getSubCareCnt() > edustatVo.getSubFallCnt() && edustatVo.getSubCareCnt() >= edustatVo.getSubLoadCnt()) {
            edustatVo.setMostRiskBySub("부주의");
        } else if (edustatVo.getSubLoadCnt() > edustatVo.getSubEsCnt() && edustatVo.getSubLoadCnt() > edustatVo.getSubFallCnt() && edustatVo.getSubLoadCnt() > edustatVo.getSubCareCnt()) {
            edustatVo.setMostRiskBySub("작업부하");
        }

        if (edustatVo.getGroundEsCnt() == 0 && edustatVo.getGroundFallCnt() == 0 && edustatVo.getGroundCareCnt() == 0 && edustatVo.getGroundLoadCnt() == 0) {
            edustatVo.setMostRiskByGround("-");
        } else if (edustatVo.getGroundEsCnt() >= edustatVo.getGroundFallCnt() && edustatVo.getGroundEsCnt() >= edustatVo.getGroundCareCnt() && edustatVo.getGroundEsCnt() >= edustatVo.getGroundLoadCnt()) {
            edustatVo.setMostRiskByGround("감전");
        } else if (edustatVo.getGroundFallCnt() > edustatVo.getGroundEsCnt() && edustatVo.getGroundFallCnt() >= edustatVo.getGroundCareCnt() && edustatVo.getGroundFallCnt() >= edustatVo.getGroundLoadCnt()) {
            edustatVo.setMostRiskByGround("추락");
        } else if (edustatVo.getGroundCareCnt() > edustatVo.getGroundEsCnt() && edustatVo.getGroundCareCnt() > edustatVo.getGroundFallCnt() && edustatVo.getGroundCareCnt() >= edustatVo.getGroundLoadCnt()) {
            edustatVo.setMostRiskByGround("부주의");
        } else if (edustatVo.getGroundLoadCnt() > edustatVo.getGroundEsCnt() && edustatVo.getGroundLoadCnt() > edustatVo.getGroundFallCnt() && edustatVo.getGroundLoadCnt() > edustatVo.getGroundCareCnt()) {
            edustatVo.setMostRiskByGround("작업부하");
        }

        if (edustatVo.getSuperEsCnt() == 0 && edustatVo.getSuperFallCnt() == 0 && edustatVo.getSuperCareCnt() == 0 && edustatVo.getSuperLoadCnt() == 0) {
            edustatVo.setMostRiskBySuper("-");
        } else if (edustatVo.getSuperEsCnt() >= edustatVo.getSuperFallCnt() && edustatVo.getSuperEsCnt() >= edustatVo.getSuperCareCnt() && edustatVo.getSuperEsCnt() >= edustatVo.getSuperLoadCnt()) {
            edustatVo.setMostRiskBySuper("감전");
        } else if (edustatVo.getSuperFallCnt() > edustatVo.getSuperEsCnt() && edustatVo.getSuperFallCnt() >= edustatVo.getSuperCareCnt() && edustatVo.getSuperFallCnt() >= edustatVo.getSuperLoadCnt()) {
            edustatVo.setMostRiskBySuper("추락");
        } else if (edustatVo.getSuperCareCnt() > edustatVo.getSuperEsCnt() && edustatVo.getSuperCareCnt() > edustatVo.getSuperFallCnt() && edustatVo.getSuperCareCnt() >= edustatVo.getSuperLoadCnt()) {
            edustatVo.setMostRiskBySuper("부주의");
        } else if (edustatVo.getSuperLoadCnt() > edustatVo.getSuperEsCnt() && edustatVo.getSuperLoadCnt() > edustatVo.getSuperFallCnt() && edustatVo.getSuperLoadCnt() > edustatVo.getSuperCareCnt()) {
            edustatVo.setMostRiskBySuper("작업부하");
        }
        return edustatVo;
    }

    public List<EdustatVo> accidentLocation(EgovMap req) {
        return eduStatMapper.accidentLocation(req);
    }

    public List<EdustatVo> accidentObjectFactor(EgovMap req) {
        return eduStatMapper.accidentObjectFactor(req);
    }

    public EdustatVo playTime(EgovMap req) {
        return eduStatMapper.playTime(req);
    }

    public List<String> accidentTypeNm(EgovMap req) {
        return eduStatMapper.accidentTypeNm(req);
    }

    public List<EduStatChartVo> accidentType(EgovMap req) {
        return eduStatMapper.accidentType(req);
    }

    public List<EduStatChartVo> accidentCause(EgovMap req) {
        return eduStatMapper.accidentCause(req);
    }

    public EduStatChartVo riskStatusByTeam(EgovMap req) {
        return eduStatMapper.riskStatusByTeam(req);
    }

    public EduStatChartVo riskStatusByUsr(EgovMap req) {
        return eduStatMapper.riskStatusByUsr(req);
    }

    public Object dashBoardStat(EgovMap req) {
        return eduStatMapper.dashBoardStat(req);
    }

    public Object dashBoardProcessList(EgovMap req) {
        return eduStatMapper.dashBoardProcessList(req);
    }

    public List<EdustatVo> dashBoardFamousList(EgovMap req) {
        return eduStatMapper.dashBoardFamousList(req);
    }

    public List<EduStatUsrVo> usrList(EgovMap req) {
        return eduStatMapper.usrList(req);
    }

    public List<EduStatUsrVo> usrPlayList(EgovMap req) {
        return eduStatMapper.usrPlayList(req);
    }

    public List<EduStatChartVo> accidentRateByTrain(EgovMap req) {
        return eduStatMapper.accidentRateByTrain(req);
    }

    public List<String> accidentReasonAnalysisRoleList(EgovMap req) {
        return eduStatMapper.accidentReasonAnalysisRoleList(req);
    }

    public List<EduStatChartVo> accidentReasonAnalysisLocation(EgovMap req) {
        return eduStatMapper.accidentReasonAnalysisLocation(req);
    }

    public List<EduStatChartVo> accidentReasonAnalysisFactor(EgovMap req) {
        return eduStatMapper.accidentReasonAnalysisFactor(req);
    }

    public List<EduStatChartVo> accidentReasonAnalysisType(EgovMap req) {
        return eduStatMapper.accidentReasonAnalysisType(req);
    }

    public List<EduStatChartVo> trainStatByPeriod(EgovMap req) {
        return eduStatMapper.trainStatByPeriod(req);
    }

    public List<EduStatChartVo> riskFactorStatByPeriod(EgovMap req) {
        return eduStatMapper.riskFactorStatByPeriod(req);
    }
    
    public List<EduStatChartVo> myTrainStatByPeriod(EgovMap req) {
        return eduStatMapper.myTrainStatByPeriod(req);
    }

    // 만족도조사 list data
    public List<CamelMap> selectPollList(EgovMap req) {
        return eduStatMapper.selectPollList(req);
    }

    // 만족도조사 통계현황 list data
    public List<CamelMap> selectOverviewList(EgovMap req) {
        return eduStatMapper.selectOverviewList(req);
    }

    // 만족도조사 view 훈련상세 데이터
    public CamelMap selectPollViewDetailData(EgovMap req) {
        return eduStatMapper.selectPollViewDetailData(req);
    }

    // 만족도조사 질문 List
    public List<CamelMap> pollQsonList(EgovMap req) {
        return eduStatMapper.pollQsonList(req);
    }

    // 만족도조사 상세 질문 list
    public List<CamelMap> pollQsonDetailList(EgovMap req) {
        return eduStatMapper.pollQsonDetailList(req);
    }

    // 만족도조사 상세 질문 리스트 : 객관식
    public List<CamelMap> pollQsonDetailNumList(EgovMap req) {
        return eduStatMapper.pollQsonDetailNumList(req);
    }

    // 만족도조사 상세 질문 리스트 : 주관식
    public List<CamelMap> pollQsonDetailDescList(EgovMap req) {
        return eduStatMapper.pollQsonDetailDescList(req);
    }

    // 만족도조사 통계현황 > 전체과정별 참여, 미참여 인원 비율
    public CamelMap pollPlayCountData(EgovMap req) {
        return eduStatMapper.pollPlayCountData(req);
    }

    // 만족도조사 과정별 참여인원 미참여인원 - XYChart
    public List<CamelMap> pollPlayCountXYData(EgovMap req) {
        return eduStatMapper.pollPlayCountXYData(req);
    }

    // 만족도조사 사용중인 훈련과정 - DataTable
    public List<CamelMap> pollPlayDataTable(EgovMap req) {
        return eduStatMapper.pollPlayDataTable(req);
    }

    // 사후평가 리스트 목록
    public List<CamelMap> selectEvalList(EgovMap req) {
        return eduStatMapper.selectEvalList(req);
    }

    // 사후평가 통계 상세 데이터
    public CamelMap selectEvalViewDetailData(EgovMap req) {
        return eduStatMapper.selectEvalViewDetailData(req);
    }

    // 사후평가 문제목록
    public List<CamelMap> evalQsonList(EgovMap req) {
        return eduStatMapper.evalQsonList(req);
    }

    // 사후평가 상세 질문 목록
    public List<CamelMap> evalQsonDetailList(EgovMap req) {
        return eduStatMapper.evalQsonDetailList(req);
    }

    // 사후평과 객관식 답안
    public List<CamelMap> evalQsonDetailNumList(EgovMap req) {
        return eduStatMapper.evalQsonDetailNumList(req);
    }

    // 사후평가 주관식 답안
    public List<CamelMap> evalQsonDescList(EgovMap req) {
        return eduStatMapper.evalQsonDescList(req);
    }

    // 사후평가 통계현황 - 사후평가 목록
    List<CamelMap> selectEvalOverviewList(EgovMap req) {
        return eduStatMapper.selectEvalOverviewList(req);
    }

    // 사후평가 통계현황 - 전체 과정별 참여, 미참여 인원 비율
    CamelMap evalPlayCountData(EgovMap req) {
        return eduStatMapper.evalPlayCountData(req);
    }

    // 사후평가 통계현황 > 과정별 참여인원 미참여인원 - XYChart
    public List<CamelMap> evalPlayCountXYData(EgovMap req) {
        return eduStatMapper.evalPlayCountXYData(req);
    }

    // 사후평가 통계현황 > 사용중인 훈련과정 - DataTable
    public List<CamelMap> evalPlayDataTable(EgovMap req) {
        return eduStatMapper.evalPlayDataTable(req);
    }
}
