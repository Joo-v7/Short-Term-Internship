package kepco.lms.edu.process.play;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class ProcessPlayService {
	
	@Autowired
	private ProcessPlayMapper processPlayMapper;
	
	public List<ProcessPlayVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return processPlayMapper.selectList(req);
	}
	
	public List<ProcessPlayVo> selectAll(EgovMap req) {
		return processPlayMapper.selectList(req);
	}
	
	public List<ProcessPlayVo> teamProcessPlays(EgovMap req) {
		return processPlayMapper.teamProcessPlays(req);
	}
	
	public List<ProcessPlayVo> processEventList(EgovMap req) {
		return processPlayMapper.processEventList(req);
	}
	
	public ProcessPlayVo select(EgovMap req) {
		return processPlayMapper.select(req);
	}
	
	public int count(EgovMap req) {
		return processPlayMapper.count(req);
	}
	
	public int stepCount(EgovMap req) {
		return processPlayMapper.stepCount(req);
	}
	
//	public int playStepCount(EgovMap req) {
//		return processPlayMapper.playStepCount(req);
//	}
	
	@Transactional
	public int processStart(ProcessPlayVo vo) {
		
		return processPlayMapper.processStart(vo);
	}
	
	@Transactional
	public int processEnd(ProcessPlayVo vo) {
		
		return processPlayMapper.processEnd(vo);
	}
	
	public List<ProcessPlayVo> processPlayList(EgovMap req) {
		return processPlayMapper.processPlayList(req);
	}
}
