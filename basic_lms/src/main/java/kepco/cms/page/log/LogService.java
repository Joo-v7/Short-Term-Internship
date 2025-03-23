package kepco.cms.page.log;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service("kepco.cms.page.log.LogService")
public class LogService {

	@Autowired
	private LogMapper logMapper;
	
	public List<LogVo> selectList(EgovMap req){
		return logMapper.selectList(req);
	}
	public LogVo select(EgovMap req) {
		return logMapper.select(req);
	}
	@Transactional
	public int insert(EgovMap req) {
		return logMapper.insert(req);
	}
}
