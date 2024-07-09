package kepco.lms.vod.learn;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface LearnMapper {
	
	public List<LearnVo> selectList(EgovMap req);
	
	public List<LearnVo> selectAll(EgovMap req);

	public LearnVo select(EgovMap req);
	
	public int graduateCal(EgovMap req);
	
	public int insert(LearnVo vo);
	
	public int update(LearnVo vo);
	
	public int delete(EgovMap req);
	
}
