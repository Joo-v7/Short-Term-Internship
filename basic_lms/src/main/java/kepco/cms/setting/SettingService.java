package kepco.cms.setting;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.CamelUtil;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kepco.cms.systemLog.SystemLogService;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.StrUtil;

/** CMS 설정 */
@Service
public class SettingService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private SettingMapper settingMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<SettingVo> selectList(EgovMap req) {
		
		return settingMapper.selectList(req);
	}
	
	public List<SettingVo> selectAll(EgovMap req) {
		return settingMapper.selectList(req);
	}
	
	public SettingVo select(EgovMap req) {
		return settingMapper.select(req);
	}
	
	@Transactional
	public int insert(SettingVo vo) {
		
		Set<ConstraintViolation<SettingVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<SettingVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		
		return settingMapper.insert(vo);
	}
	
	@Transactional
	public int update(SettingVo vo) {
		Set<ConstraintViolation<SettingVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<SettingVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return settingMapper.update(vo);
	}
	
	public int delete(EgovMap req) {
		int settingIdx = StrUtil.toInt(req.get("settingIdx"));
		if(settingIdx < 1) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "세팅IDX는 필수입력 입니다.");
		}
		
		// 삭제불가 세팅키 체크
		SettingVo settingVo = settingMapper.select(req);
		String settingKey = settingVo.getSettingKey();
		try {
			Field field = PropApp.class.getDeclaredField(CamelUtil.convert2CamelCase(settingKey));
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "사전정의된 세팅키는 삭제할 수 없습니다.");
		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
		} catch (SecurityException e) {
//			e.printStackTrace();
		}
		
		return settingMapper.delete(req);
	}
	
	/**
	 * 
	 * @return
	 * @deprecated 반복된 쿼리 호출은 지양
	 */
	public List<String> selectIpList() {
		return settingMapper.selectIpList();
	}
	
	public int secureVaildate(String str) {
		return settingMapper.secureVaildate(str);
	}
	
	
}