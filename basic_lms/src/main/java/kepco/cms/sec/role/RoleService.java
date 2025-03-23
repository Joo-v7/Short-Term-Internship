package kepco.cms.sec.role;

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

/** 역할 */
@Service
public class RoleService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private RoleMapper roleMapper;
	
	public List<RoleVo> selectList(EgovMap req) {
		
		return roleMapper.selectList(req);
	}
	
	public List<RoleVo> selectAll(EgovMap req) {
		return roleMapper.selectList(req);
	}
	
	public RoleVo select(EgovMap req) {
		return roleMapper.select(req);
	}
	
	@Transactional
	public int insert(RoleVo vo) {
		
		Set<ConstraintViolation<RoleVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<RoleVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		return roleMapper.insert(vo);
	}
	
	@Transactional
	public int update(RoleVo vo) {
		Set<ConstraintViolation<RoleVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<RoleVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return roleMapper.update(vo);
	}
	
	public int delete(EgovMap req) {
		return roleMapper.delete(req);
	}
	
	/** 
	 * RoleHierarchy 위한 목록
	 * @return
	 */
	public List<RoleVo> selectRoleCdList() {
		return roleMapper.selectRoleCdList();
		
	}
	
	/**
	 * 
	 * @param parentRoleCd
	 * @return
	 */
	public List<String> selectRoleCdChildrenList(String parentRoleCd) {
		return roleMapper.selectRoleCdChildrenList(parentRoleCd);
		
	}
}