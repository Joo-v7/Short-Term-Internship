package kepco.lms.edu.event;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class EventService {
	
	@Autowired
	private EventMapper eventMapper;
	
	
	public List<EventVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return eventMapper.selectList(req);
	}
	
	public List<EventVo> selectAll(EgovMap req) {
		return eventMapper.selectList(req);
	}
	
	public List<EventVo> eventIdList(EgovMap req) {
		return eventMapper.eventIdList(req);
	}
	
	public List<EventVo> teamEventList(EgovMap req) {
		return eventMapper.teamEventList(req);
	}
	
	public List<EventVo> accidentList(EgovMap req) {
		return eventMapper.accidentList(req);
	}
	
	public EventVo select(EgovMap req) {
		return eventMapper.select(req);
	}
	
	@Transactional
	public int insert(EventVo vo) {
		
		return eventMapper.insert(vo);
	}
	
	@Transactional
	public int delete(EventVo vo) {
		return eventMapper.delete(vo);
	}
	
	@Transactional
	public int eventStart(EventVo vo) {
		
		return eventMapper.eventStart(vo);
	}
	
	@Transactional
	public int eventEnd(EventVo vo) {
		
		return eventMapper.eventEnd(vo);
	}
	
	public List<EventVo> eventList(EgovMap req) {
		return eventMapper.eventList(req);
	}
}
