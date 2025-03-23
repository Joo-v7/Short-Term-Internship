package kepco.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.CamelUtil;

/**
 * ListOrderedMap을 쓰면 뭐가 좋은건지 모르겠는데?<br/>
 * K는 String으로 고정하고 V는 그냥 두고 쓰자. 일반적으로 Object를 사용할 이유가 거의 없다.<br/>
 * 걍 Object로 쓰자.<br/>
 * 우선 카멜케이스 처리만 사용하고 향후 고민하는 걸로...<br/>
 * @see org.egovframe.rte.psl.dataaccess.util.EgovMap
 *
 */
public class CamelMap extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = -8205709516694219659L;
	
	@Override
	public Object put(String key, Object value) {
		return super.put(CamelUtil.convert2CamelCase(key), value);
	}
	
	
	public String getStr(String key) {
		return StrUtil.toStr(super.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return 기본값음 0
	 */
	public int getInt(String key) {
		return StrUtil.toInt(super.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return 기본값 : 0L
	 */
	public long getLng(String key) {
		return StrUtil.toLng(super.get(key));
	}
	
	/**
	 * 
	 * @param key
	 * @return 기본값 : false
	 */
	public boolean getBool(String key) {
		return StrUtil.toBool(super.get(key));
	}
	
	/**
	 * 
	 * array는 불편하니 List로...
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> getList(String key) {
		Object value = super.get(key);
		if (value.getClass().isArray()) {
			return new ArrayList<Object>(Arrays.asList((Object[]) value));
		}
		if (value instanceof List<?>) {
			return (List<Object>) value;
		}
		return Collections.EMPTY_LIST;
	}
}
