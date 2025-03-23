package kepco.cms.page;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class PageService {

	@Autowired
	private PageMapper pageMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<PageVo> selectList(EgovMap req){
		
		int pageNum=StrUtil.toInt(req.get("pageNm"),1);
		int pageSize=StrUtil.toInt(req.get("pageSize"),10);
		
		PageHelper.startPage(pageNum, pageSize);
		return pageMapper.selectList(req);
	}
	public List<PageVo> selectAll(EgovMap req) {
		return pageMapper.selectList(req);
	}
	public PageVo select(EgovMap req) {
		return pageMapper.select(req);
	}
	
	@Transactional
	public int insert(PageVo vo) {
		
		return pageMapper.insert(vo);
	}
	@Transactional
	public int update(PageVo vo) {
		
		return pageMapper.update(vo);
	}
	
	public int delete(EgovMap req) {
		
		return pageMapper.delete(req);
	}
}
