package kepco.lms.edu.play.team;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface PlayTeamMapper {
	
	public List<PlayTeamVo> selectList(EgovMap req);
	
	public List<PlayTeamVo> selectAll(EgovMap req);

	public List<PlayTeamVo> modulePlayListByPlayTeam(EgovMap req);
	
	public PlayTeamVo select(EgovMap req);
	
	public int insert(PlayTeamVo vo);
	
	public int update(PlayTeamVo vo);
	
	public int deletePlayTeam(EgovMap req);
	public int deletePlay(EgovMap req);
	public int deleteModulePlay(EgovMap req);
	public int deleteProcessPlay(EgovMap req);
	public int deleteEvent(EgovMap req);
	
}
