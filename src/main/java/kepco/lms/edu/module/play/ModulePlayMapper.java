package kepco.lms.edu.module.play;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ModulePlayMapper {
	
	public List<ModulePlayVo> selectList(EgovMap req);
	
	public List<ModulePlayVo> selectAll(EgovMap req);

	public ModulePlayVo select(EgovMap req);
	
	public List<ModulePlayVo> modules(EgovMap req);
	
	public List<ModulePlayVo> moduleAttempts(EgovMap req);
	
//	public List<ModulePlayVo> teamPlays(EgovMap req);
	
	public int count(EgovMap req);

	public int moduleStart(ModulePlayVo vo);
	
	public int moduleEnd(ModulePlayVo vo);
	
	public ModulePlayVo risks(EgovMap req);
	
	public List<ModulePlayVo> modulePlayList(EgovMap req);

	public List<ModulePlayVo> modulePlayHistory(EgovMap req);
}
