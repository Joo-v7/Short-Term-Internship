package kepco.lms.edu.team;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface TeamMapper {
	
	public List<TeamVo> selectList(EgovMap req);
	
	public List<TeamVo> selectAll(EgovMap req);

	public TeamVo select(EgovMap req);
	
	public int insert(TeamVo vo);
	
	public int update(TeamVo vo);
	
	public int delete(EgovMap req);
	
}
