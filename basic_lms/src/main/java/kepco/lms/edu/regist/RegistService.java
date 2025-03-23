package kepco.lms.edu.regist;

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
public class RegistService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private RegistMapper registMapper;
	
	
	public List<RegistVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return registMapper.selectList(req);
	}

	public List<RegistVo> selectAll(EgovMap req) {
		return registMapper.selectList(req);
	}
	
	public List<RegistVo> studentList(EgovMap req) {
		return registMapper.studentList(req);
	}
	
	public List<RegistVo> mySelectAll(EgovMap req) {
		return registMapper.myselectall(req);
	}

	public List<RegistVo> selectMyList(EgovMap req) {
		return registMapper.selectMyList(req);
	}
	
	public RegistVo select(EgovMap req) {
		return registMapper.select(req);
	}
	
	public RegistVo selectByDetailAndMember(EgovMap req) {
		return registMapper.selectByDetailAndMember(req);
	}

	public int duplicate(EgovMap req) {
		return registMapper.duplicate(req);
	}
	
	public int checkIdx(String req) {
		return registMapper.checkIdx(req);
	}
	
	@Transactional
	public int insert(RegistVo vo) {
		
		Set<ConstraintViolation<RegistVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<RegistVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return registMapper.insert(vo);
	}
	
	@Transactional
	public int update(RegistVo vo) {
		
		Set<ConstraintViolation<RegistVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<RegistVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return registMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return registMapper.delete(req);
	}
	
	@Transactional
	public int graduate(RegistVo vo) {
		
		return registMapper.graduate(vo);
	}
	
	@Transactional
	public int eduStateSave(EgovMap req) {
		
		return registMapper.eduStateSave(req);
	}
	
}
