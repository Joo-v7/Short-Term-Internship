package kepco.lms.vod.learn;

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
public class LearnService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private LearnMapper learnMapper;
	
	
	public List<LearnVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return learnMapper.selectList(req);
	}

	public List<LearnVo> selectAll(EgovMap req) {
		return learnMapper.selectList(req);
	}
	
	public LearnVo select(EgovMap req) {
		return learnMapper.select(req);
	}
	
	public int graduateCal(EgovMap req) {
		return learnMapper.graduateCal(req);
	}
	
	@Transactional
	public int insert(LearnVo vo) {
		
		Set<ConstraintViolation<LearnVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<LearnVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return learnMapper.insert(vo);
	}
	
	@Transactional
	public int update(LearnVo vo) {
		
		Set<ConstraintViolation<LearnVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<LearnVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return learnMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return learnMapper.delete(req);
	}
	
	
}
