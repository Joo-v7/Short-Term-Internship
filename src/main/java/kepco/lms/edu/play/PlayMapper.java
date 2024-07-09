package kepco.lms.edu.play;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface PlayMapper {
	
	public List<PlayVo> selectList(EgovMap req);
	
	public List<PlayVo> selectAll(EgovMap req);
	
	public List<PlayVo> playTeamList(EgovMap req);

	public List<PlayVo> studentList(EgovMap req);
	
	public PlayVo select(EgovMap req);
	
	public PlayVo mostInfo(EgovMap req);
	
	public int insert(PlayVo vo);
	
	public int start(PlayVo vo);
	
	public int end(PlayVo vo);
	
	public int update(PlayVo vo);
	
	public List<PlayVo> playLogList(EgovMap req);
	
	public PlayVo statSelect(EgovMap req);
}
