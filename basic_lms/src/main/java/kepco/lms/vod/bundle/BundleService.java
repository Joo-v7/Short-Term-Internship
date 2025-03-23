package kepco.lms.vod.bundle;

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
public class BundleService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private BundleMapper bundleMapper;
	
	
	public List<BundleVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return bundleMapper.selectList(req);
	}

	public List<BundleVo> selectAll(EgovMap req) {
		return bundleMapper.selectList(req);
	}
	
	public BundleVo select(EgovMap req) {
		return bundleMapper.select(req);
	}
	
	@Transactional
	public int insert(BundleVo vo) {
		
		Set<ConstraintViolation<BundleVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<BundleVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return bundleMapper.insert(vo);
	}
	
	@Transactional
	public int update(BundleVo vo) {
		
		Set<ConstraintViolation<BundleVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<BundleVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return bundleMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return bundleMapper.delete(req);
	}
	
	
}
