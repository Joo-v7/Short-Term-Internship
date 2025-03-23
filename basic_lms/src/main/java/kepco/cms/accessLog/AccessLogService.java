package kepco.cms.accessLog;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccessLogService {
	
	@Autowired
	private AccessLogMapper logMapper;

	public int insert(EgovMap req) {
		return logMapper.insert(req);
	}

	public List<Object> hourList(EgovMap req) {
		return logMapper.hourList(req);
	}

	public List<Object> dayList(EgovMap req) {
		return logMapper.dayList(req);
	}
	
}
