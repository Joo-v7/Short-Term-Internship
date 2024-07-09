package kepco.cms.sec.role.log;

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
public class RoleLogService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private RoleLogMapper roleLogMapper;
	
	public List<RoleLogVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return roleLogMapper.selectList(req);
	}
	
	public List<RoleLogVo> selectAll(EgovMap req) {
		return roleLogMapper.selectList(req);
	}
	
	public RoleLogVo select(EgovMap req) {
		return roleLogMapper.select(req);
	}
	
	@Transactional
	public int insert(RoleLogVo vo) {
		
		Set<ConstraintViolation<RoleLogVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<RoleLogVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		return roleLogMapper.insert(vo);
	}
	
	
}