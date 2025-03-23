package kepco.lms.edu.process;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class EduProcessService {
	
	@Autowired
	private EduProcessMapper eduProcessMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<EduProcessVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return eduProcessMapper.selectList(req);
	}

	public List<EduProcessVo> selectAll(EgovMap req) {
		return eduProcessMapper.selectList(req);
	}
	
	public List<EduProcessVo> taskAll(EgovMap req) {
		return eduProcessMapper.taskAll(req);
	}
	
	public EduProcessVo select(EgovMap req) {
		return eduProcessMapper.select(req);
	}
	
	public EduProcessVo taskSelect(EgovMap req) {
		return eduProcessMapper.taskSelect(req);
	}
	
	public int count(EgovMap req) {
		return eduProcessMapper.count(req);
	}
	
	@Transactional
	public int insert(EduProcessVo vo) {
		
		return eduProcessMapper.insert(vo);
	}
	
	@Transactional
	public int update(EduProcessVo vo) {
		
		return eduProcessMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return eduProcessMapper.delete(req);
	}
	
	public int idCheck(EduProcessVo eduProcessVo) {
		return eduProcessMapper.idCheck(eduProcessVo);
	}
	
	public int aiIdCheck(EduProcessVo eduProcessVo) {
		return eduProcessMapper.aiIdCheck(eduProcessVo);
	}
}
