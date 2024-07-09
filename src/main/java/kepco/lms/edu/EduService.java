package kepco.lms.edu;

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
public class EduService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private EduMapper eduMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<EduVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return eduMapper.selectList(req);
	}

	public List<EduVo> selectAll(EgovMap req) {
		return eduMapper.selectList(req);
	}
	
	public EduVo select(EgovMap req) {
		return eduMapper.select(req);
	}
	
	@Transactional
	public int insert(EduVo vo) {
		
		Set<ConstraintViolation<EduVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<EduVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		return eduMapper.insert(vo);
	}
	
	@Transactional
	public int update(EduVo vo) {
		
		Set<ConstraintViolation<EduVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<EduVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return eduMapper.update(vo);
	}
	
	@Transactional
	public int fileUpdate(EduVo vo) {
		return eduMapper.fileUpdate(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return eduMapper.delete(req);
	}

	public List<EduVo> myListSelect(EgovMap req) {
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return eduMapper.myListSelect(req);
	}
	
	public List<EduVo> myPlayListSelect(EgovMap req) {
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return eduMapper.myPlayListSelect(req);
	}
	
	public List<EduVo> myApplyListSelect(EgovMap req) {
		return eduMapper.myApplyListSelect(req);
	}
	
	public List<EduVo> myApplyListSelect2(EgovMap req) { // 교수자
		return eduMapper.myApplyListSelect2(req);
	}
}
