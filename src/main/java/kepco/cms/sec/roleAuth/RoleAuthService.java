package kepco.cms.sec.roleAuth;

import java.util.Collection;
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

/** 역할별 권한 */
@Service
public class RoleAuthService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private RoleAuthMapper roleAuthMapper;
	
	public List<RoleAuthVo> selectList(EgovMap req) {
		
		return roleAuthMapper.selectList(req);
	}
	
	public List<RoleAuthVo> selectAll(EgovMap req) {
		return roleAuthMapper.selectList(req);
	}
	
	public RoleAuthVo select(EgovMap req) {
		return roleAuthMapper.select(req);
	}
	
	@Transactional
	public int insert(RoleAuthVo vo) {
		
		Set<ConstraintViolation<RoleAuthVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<RoleAuthVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		return roleAuthMapper.insert(vo);
	}
	
	public int delete(EgovMap req) {
		return roleAuthMapper.delete(req);
	}

	public List<String> selectAuthCdList(Collection<String> roleList) {
		return roleAuthMapper.selectAuthCdList(roleList);
	}
}