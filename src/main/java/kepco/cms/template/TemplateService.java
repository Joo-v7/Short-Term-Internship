package kepco.cms.template;

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
public class TemplateService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private TemplateMapper templateMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<TemplateVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return templateMapper.selectList(req);
	}

	public List<TemplateVo> selectAll(EgovMap req) {
		return templateMapper.selectList(req);
	}
	
	public TemplateVo select(EgovMap req) {
		return templateMapper.select(req);
	}
	
	
	@Transactional
	public int insert(TemplateVo vo) {
		
		Set<ConstraintViolation<TemplateVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<TemplateVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		
		return templateMapper.insert(vo);
	}
	
	@Transactional
	public int update(TemplateVo vo) {
		
		Set<ConstraintViolation<TemplateVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<TemplateVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return templateMapper.update(vo);
	}

	@Transactional
	public int logSave(TemplateVo vo) {
		
		Set<ConstraintViolation<TemplateVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<TemplateVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		return templateMapper.logSave(vo);
	}
	
	public int delete(EgovMap req) {
		return templateMapper.delete(req);
	}
}
