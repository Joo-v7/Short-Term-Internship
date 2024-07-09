package kepco.lms.edu.process.bundle;

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

import kepco.util.StrUtil;

@Service
public class ProcessBundleService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ProcessBundleMapper processBundleMapper;
	
	
	public List<ProcessBundleVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return processBundleMapper.selectList(req);
	}

	public List<ProcessBundleVo> selectAll(EgovMap req) {
		return processBundleMapper.selectList(req);
	}
	
	public ProcessBundleVo select(EgovMap req) {
		return processBundleMapper.select(req);
	}
	
	@Transactional
	public int insert(ProcessBundleVo vo) {
		
		Set<ConstraintViolation<ProcessBundleVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<ProcessBundleVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return processBundleMapper.insert(vo);
	}
	
	@Transactional
	public int update(ProcessBundleVo vo) {
		
		Set<ConstraintViolation<ProcessBundleVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<ProcessBundleVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return processBundleMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return processBundleMapper.delete(req);
	}
	
	
}
