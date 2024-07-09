package kepco.cms.stat;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.StrUtil;

/**
 * 방문자
 */
@Service
public class StatService {

    @Autowired
    private Validator validator;

    @Autowired
    private StatMapper statMapper;

    public List<StatVo> selectList(EgovMap req) {

        return statMapper.selectList(req);
    }

    public List<StatVo> selectDateStat(EgovMap req) {
        return statMapper.selectDate(req);
    }

    public List<StatVo> selectHourStat(EgovMap req) {
        return statMapper.selectHour(req);
    }

    public List<StatVo> selectDeviceStat(EgovMap req) {
        return statMapper.selectDeviceStat(req);
    }

    public StatVo selectDevice(EgovMap req) {
        return statMapper.selectDevice(req);
    }

    public List<StatVo> selectBrowserStat(EgovMap req) {
        return statMapper.selectBrowser(req);
    }

    public List<StatVo> selectOsStat(EgovMap req) {
        return statMapper.selectOs(req);
    }

    public List<StatVo> selectAll(EgovMap req) {
        return statMapper.selectList(req);
    }

    public StatVo select(EgovMap req) {
        return statMapper.select(req);
    }

    public StatVo visitCount(EgovMap req) {
        return statMapper.visitCount(req);
    }

    @Transactional
    public int insert(StatVo vo) {

        Set<ConstraintViolation<StatVo>> violations = validator.validate(vo);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<StatVo> constraintViolation : violations) {
                String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
                throw new ConstraintViolationException(message, violations);
            }
        }
        return statMapper.insert(vo);
    }
}