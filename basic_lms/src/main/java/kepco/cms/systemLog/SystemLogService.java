package kepco.cms.systemLog;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class SystemLogService {
	
	@Autowired
	private SystemLogMapper systemLogMapper;
	
	
	public List<SystemLogVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return systemLogMapper.selectList(req);
	}

	public List<SystemLogVo> selectAll(EgovMap req) {
		return systemLogMapper.selectList(req);
	}
	
	public List<SystemLogVo> useStat(EgovMap req) {
		return systemLogMapper.useStat(req);
	}
	
	public SystemLogVo select(EgovMap req) {
		return systemLogMapper.select(req);
	}
	
	@Transactional
	public int insert(EgovMap req) {
		
		return systemLogMapper.insert(req);
	}
}
