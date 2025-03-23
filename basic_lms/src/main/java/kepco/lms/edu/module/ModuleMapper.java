package kepco.lms.edu.module;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ModuleMapper {
	
	public List<ModuleVo> selectList(EgovMap req);
	
	public List<ModuleVo> selectPack(EgovMap req);
	
	public List<ModuleVo> selectAll(EgovMap req);

	public ModuleVo select(EgovMap req);
	
	public int insert(ModuleVo vo);
	
	public int update(ModuleVo vo);
	
	public int delete(EgovMap req);
	
	public int fileUpdate(ModuleVo vo);
}
