package kepco.lms.edu.play.team;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class PlayTeamService {
	
	@Autowired
	private PlayTeamMapper playTeamMapper;
	
	
	public List<PlayTeamVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return playTeamMapper.selectList(req);
	}

	public List<PlayTeamVo> selectAll(EgovMap req) {
		return playTeamMapper.selectList(req);
	}
	
	public List<PlayTeamVo> modulePlayListByPlayTeam(EgovMap req) {
		return playTeamMapper.modulePlayListByPlayTeam(req);
	}
	
	public PlayTeamVo select(EgovMap req) {
		return playTeamMapper.select(req);
	}
	
	@Transactional
	public int insert(PlayTeamVo vo) {
		return playTeamMapper.insert(vo);
	}
	
	@Transactional
	public int update(PlayTeamVo vo) {
		return playTeamMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		playTeamMapper.deleteEvent(req);
		playTeamMapper.deleteProcessPlay(req);
		playTeamMapper.deleteModulePlay(req);
		playTeamMapper.deletePlayTeam(req);
		playTeamMapper.deletePlay(req);
		
		return 1;
	}
	
	
}
