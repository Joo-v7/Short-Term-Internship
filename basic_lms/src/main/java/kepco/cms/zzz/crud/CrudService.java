package kepco.cms.zzz.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.StrUtil;


@Service
public class CrudService extends EgovAbstractServiceImpl {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CrudMapper crudDtoMapper;
	
	public List<CrudVo> selectList(EgovMap req) {
		return crudDtoMapper.selectList(req);
	}

	public CrudVo select(EgovMap req) {
		return crudDtoMapper.select(req);
	}
	
	@Transactional
	public long insert(CrudVo vo) {
		
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<CrudVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<CrudVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(),
						constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		long rowCnt = crudDtoMapper.insert(vo);
		
		return rowCnt;
	}
	
	public long update(CrudVo vo) {
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<CrudVo>> violations = validator.validate(vo);

		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<CrudVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(),
						constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		long rowCnt = crudDtoMapper.update(vo);
		
		return rowCnt;
	}
	
	public long delete(EgovMap req) {
		
		long rowCnt = crudDtoMapper.delete(req);
		
		return rowCnt;
	}
}
