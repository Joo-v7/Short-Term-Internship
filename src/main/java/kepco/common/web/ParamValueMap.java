package kepco.common.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;


/**
 * 
 * @Deprecated 사용치 않는 걸로..<br/>목적은 대부분 단일값(single value)인데 MultiValueMap으로 get(), put() 쓰기가 불편해서 였는데, 익숙해지면 오히려 혼란을 가중시킨다.
 *
 */
@Deprecated
@SuppressWarnings("serial")
public final class ParamValueMap extends LinkedMultiValueMap<String, String> {

	@Nullable
	public String get(String key) {
		return super.getFirst(key);
	}
	
	@Nullable
	public List<String> getList(Object key) {
		return super.get(key);
	}
	
	
	public void fromArrayMap(Map<String, String[]> map) {
		Set<Entry<String, String[]>> entries = map.entrySet();
		for (Map.Entry<String, String[]> mapEntry : entries) {
		    super.put(mapEntry.getKey(), Arrays.asList(mapEntry.getValue()));
		}
	}
}
