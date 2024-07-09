package kepco.cms.sec.auth;

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

/** 권한 */
@Service("cms.sec.authService")
public class AuthService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private AuthMapper secAuthMapper;
	
	public List<AuthVo> selectList(EgovMap req) {
		
		return secAuthMapper.selectList(req);
	}
	
	public List<AuthVo> selectAll(EgovMap req) {
		return secAuthMapper.selectList(req);
	}
	
	public AuthVo select(EgovMap req) {
		return secAuthMapper.select(req);
	}
	
	@Transactional
	public int insert(AuthVo vo) {
		
		Set<ConstraintViolation<AuthVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<AuthVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		return secAuthMapper.insert(vo);
	}
	
	@Transactional
	public int update(AuthVo vo) {
		Set<ConstraintViolation<AuthVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<AuthVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return secAuthMapper.update(vo);
	}
	
	public int delete(EgovMap req) {
		return secAuthMapper.delete(req);
	}

	public List<String> selectAuthCdList(List<String> roleList) {
		return secAuthMapper.selectAuthCdList(roleList);
	}
}