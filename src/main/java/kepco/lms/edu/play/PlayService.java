package kepco.lms.edu.play;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class PlayService {
	
	@Autowired
	private PlayMapper playMapper;
	
	
	public List<PlayVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return playMapper.selectList(req);
	}
	
	public List<PlayVo> selectAll(EgovMap req) {
		return playMapper.selectList(req);
	}
	
	public List<PlayVo> playTeamList(EgovMap req) {
		return playMapper.playTeamList(req);
	}
	
	public List<PlayVo> studentList(EgovMap req) {
		 List<PlayVo> playList = playMapper.studentList(req);
		 for (PlayVo playVo : playList) {
			    EgovMap egovMap = new EgovMap();
			    egovMap.put("memberId", playVo.getMemberId());
			    egovMap.put("detailIdx", playVo.getDetailIdx());
			    PlayVo mostVo = playMapper.mostInfo(egovMap);
			    playVo.setMostRole(mostVo.getMostRole());
			    playVo.setMostRisk(mostVo.getMostRisk());
			}
		 
		 return playList;
	}
	
	public PlayVo select(EgovMap req) {
		return playMapper.select(req);
	}
	
	public PlayVo mostInfo(EgovMap req) {
		return playMapper.mostInfo(req);
	}
	
	@Transactional
	public int insert(PlayVo vo) {
		
		return playMapper.insert(vo);
	}
	
	@Transactional
	public int start(PlayVo vo) {
		
		return playMapper.start(vo);
	}
	
	@Transactional
	public int end(PlayVo vo) {
		
		return playMapper.end(vo);
	}
	
	@Transactional
	public int update(PlayVo vo) {
		
		return playMapper.update(vo);
	}
	
	public List<PlayVo> playLogList(EgovMap req) {
		return playMapper.playLogList(req);
	}
	
	public PlayVo statSelect(EgovMap req) {
		return playMapper.statSelect(req);
	}
	
}
