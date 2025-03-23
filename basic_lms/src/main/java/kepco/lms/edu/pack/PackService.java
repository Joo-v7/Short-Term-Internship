package kepco.lms.edu.pack;

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
public class PackService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private PackMapper packMapper;
	
	
	public List<PackVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return packMapper.selectList(req);
	}

	public List<PackVo> selectAll(EgovMap req) {
		return packMapper.selectList(req);
	}
	
	public PackVo select(EgovMap req) {
		return packMapper.select(req);
	}
	
	@Transactional
	public int insert(PackVo vo) {
		
		Set<ConstraintViolation<PackVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<PackVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return packMapper.insert(vo);
	}
	
	@Transactional
	public int update(PackVo vo) {
		
		Set<ConstraintViolation<PackVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<PackVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return packMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return packMapper.delete(req);
	}
	
	
}
