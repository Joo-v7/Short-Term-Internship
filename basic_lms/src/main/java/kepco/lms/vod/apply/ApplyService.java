package kepco.lms.vod.apply;

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
public class ApplyService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ApplyMapper applyMapper;
	
	
	public List<ApplyVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return applyMapper.selectList(req);
	}

	public List<ApplyVo> selectAll(EgovMap req) {
		return applyMapper.selectList(req);
	}

	public List<ApplyVo> selectMyList(EgovMap req) {
		return applyMapper.selectMyList(req);
	}
	
	public List<ApplyVo> selectApplyMyList(EgovMap req) {
		return applyMapper.selectApplyMyList(req);
	}
	
	public ApplyVo select(EgovMap req) {
		return applyMapper.select(req);
	}

	public int duplicate(EgovMap req) {
		return applyMapper.duplicate(req);
	}
	
	public int checkIdx(String req) {
		return applyMapper.checkIdx(req);
	}
	
	@Transactional
	public int insert(ApplyVo vo) {
		
		Set<ConstraintViolation<ApplyVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<ApplyVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return applyMapper.insert(vo);
	}
	
	@Transactional
	public int update(ApplyVo vo) {
		
		Set<ConstraintViolation<ApplyVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<ApplyVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return applyMapper.update(vo);
	}

	@Transactional
	public int learnUpdate(EgovMap req) {
		return applyMapper.learnUpdate(req);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return applyMapper.delete(req);
	}
	
	
}
