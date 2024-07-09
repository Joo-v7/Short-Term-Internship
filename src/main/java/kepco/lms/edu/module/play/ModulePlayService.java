package kepco.lms.edu.module.play;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class ModulePlayService {
	
	@Autowired
	private ModulePlayMapper modulePlayMapper;
	
	
	public List<ModulePlayVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return modulePlayMapper.selectList(req);
	}
	
	public List<ModulePlayVo> selectAll(EgovMap req) {
		return modulePlayMapper.selectList(req);
	}
	
	public ModulePlayVo select(EgovMap req) {
		return modulePlayMapper.select(req);
	}
	
	public List<ModulePlayVo> modules(EgovMap req) {
		return modulePlayMapper.modules(req);
	}
	
	public List<ModulePlayVo> moduleAttempts(EgovMap req) {
		return modulePlayMapper.moduleAttempts(req);
	}
	
//	public List<ModulePlayVo> teamPlays(EgovMap req) {
//		return modulePlayMapper.teamPlays(req);
//	}
	
	public ModulePlayVo risks(EgovMap req) {
		
		return modulePlayMapper.risks(req);
	}
	
	public int count(EgovMap req) {
		return modulePlayMapper.count(req);
	}
	
	@Transactional
	public int moduleStart(ModulePlayVo vo) {
		
		return modulePlayMapper.moduleStart(vo);
	}
	
	@Transactional
	public int moduleEnd(ModulePlayVo vo) {
		
		return modulePlayMapper.moduleEnd(vo);
	}
	
	public List<ModulePlayVo> modulePlayList(EgovMap req) {
		return modulePlayMapper.modulePlayList(req);
	}
	
	public List<ModulePlayVo> modulePlayHistory(EgovMap req) {
		return modulePlayMapper.modulePlayHistory(req);
	}
}
