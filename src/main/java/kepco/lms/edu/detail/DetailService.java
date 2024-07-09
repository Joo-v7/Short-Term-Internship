package kepco.lms.edu.detail;

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

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class DetailService {

    @Autowired
    private Validator validator;

    @Autowired
    private DetailMapper detailMapper;

    @Autowired
    SystemLogService systemLogService;


    public List<DetailVo> selectList(EgovMap req) {

        int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
        int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);

        return detailMapper.selectList(req);
    }

    public List<DetailVo> selectAll(EgovMap req) {
        return detailMapper.selectList(req);
    }

    public DetailVo select(EgovMap req) {
        return detailMapper.select(req);
    }

    public List<DetailVo> detail(EgovMap req) {
        return detailMapper.detail(req);
    }

    public DetailVo fileSelect(EgovMap req) {
        return detailMapper.fileSelect(req);
    }

    @Transactional
    public int insert(DetailVo vo) {

        Set<ConstraintViolation<DetailVo>> violations = validator.validate(vo);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<DetailVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        return detailMapper.insert(vo);
    }

    @Transactional
    public int update(DetailVo vo) {

        Set<ConstraintViolation<DetailVo>> violations = validator.validate(vo);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<DetailVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        return detailMapper.update(vo);
    }

    @Transactional
    public int delete(EgovMap req) {

        return detailMapper.delete(req);
    }

    public List<DetailVo> dashboardCalendar(EgovMap req) {
        return detailMapper.dashboardCalendar(req);
    }
    
    @Transactional
    public int thumbnailUpdate(DetailVo vo) {
        return detailMapper.thumbnailUpdate(vo);
    }
    
    @Transactional
    public int fileUpdate(DetailVo vo) {
        return detailMapper.fileUpdate(vo);
    }
}
