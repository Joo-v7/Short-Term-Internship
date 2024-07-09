package kepco.lms.main;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.board.post.PostVo;
import kepco.cms.board.post.file.FileVo;
import kepco.cms.menu.MenuVo;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.vod.VodVo;
import kepco.util.StrUtil;

@Service
public class MainSearchService {
	
	@Autowired
	private MainSearchMapper mainSearchMapper;
	
	public List<MenuVo> menuList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.menuList(req);
	}
	
	public List<PostVo> postList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.postList(req);
	}
	
	public List<DetailVo> detailList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.detailList(req);
	}

	public List<VodVo> vodList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.vodList(req);
	}

	public List<FileVo> fileList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.fileList(req);
	}
	
	public List<MainSearchVo> dayHistory(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.dayHistory(req);
	}
	
	public List<MainSearchVo> weekHistory(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		return mainSearchMapper.weekHistory(req);
	}
	
	@Transactional
	public int insert(EgovMap req) {
		return mainSearchMapper.insert(req);
	}
	
	/**
	 * 오래된 데이터(1달 경과) 삭제
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteOldData() {
		
		EgovMap req = new EgovMap();
		long ret = mainSearchMapper.deleteOldData(req);
	}
	
}