package kepco.lms.edu.event;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.lms.edu.process.play.ProcessPlayVo;


@Mapper
public interface EventMapper {
	
	public List<EventVo> selectList(EgovMap req);
	
	public List<EventVo> selectAll(EgovMap req);

	public List<EventVo> eventIdList(EgovMap req);
	
	public List<EventVo> teamEventList(EgovMap req);
	
	public List<EventVo> accidentList(EgovMap req);
	
	public int eventStart(EventVo vo);
	
	public int eventEnd(EventVo vo);
	
	public int delete(EventVo vo);
	
	public EventVo select(EgovMap req);
	
	public int insert(EventVo vo);
	
	public List<EventVo> eventList(EgovMap req);
}
