package kepco.lms.edu.process.play;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ProcessPlayMapper {
	
	public List<ProcessPlayVo> selectList(EgovMap req);
	
	public List<ProcessPlayVo> selectAll(EgovMap req);

	public List<ProcessPlayVo> teamProcessPlays(EgovMap req);
	
	public List<ProcessPlayVo> processEventList(EgovMap req);
	
	public ProcessPlayVo select(EgovMap req);
	
	public int count(EgovMap req);
	
	public int stepCount(EgovMap req);
	
//	public int playStepCount(EgovMap req);
	
	public int processStart(ProcessPlayVo vo);
	
	public int processEnd(ProcessPlayVo vo);

	public List<ProcessPlayVo> processPlayList(EgovMap req);
}
