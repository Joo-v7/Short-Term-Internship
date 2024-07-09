package kepco.cms.setting;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

/** 프로퍼티 관리 */
@Service
public class PropAppService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private PropApp propApp;
	
	/**
	 * 
	 * @return
	 */
	public boolean loadPropApp() {
		List<SettingVo> settingVoList = settingService.selectAll(new EgovMap());
		
		// 사전정의된 프로퍼티에에 대해 비교
		Field[] fieldArray = PropApp.class.getDeclaredFields();
		for (Field field : fieldArray) {
			String key = StrUtil.camelCase2UnderScoreCase(field.getName());
			
			// 디비에서 가져온 값과 비교
			for (SettingVo settingVo : settingVoList) {
				if (key.equals(settingVo.getSettingKey())) {
					
					try {
						Method setter = PropApp.class.getDeclaredMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1),field.getType());
						setter.invoke(propApp, settingVo.getSettingValue());
						log.debug("PropApp - " + field.getName() + " : " + settingVo.getSettingValue());
					} catch (NoSuchMethodException e) {
						log.error("");
						e.printStackTrace();
					} catch (SecurityException e) {
						log.error("");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// invoke()에서 발생 가능
						log.error("");
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// invoke()에서 발생 가능
						log.error("");
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// invoke()에서 발생 가능
						log.error("");
						e.printStackTrace();
					}
				}
			}
		}
		
		return true;
	}
}