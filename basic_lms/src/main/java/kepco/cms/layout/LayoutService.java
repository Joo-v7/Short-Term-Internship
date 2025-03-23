package kepco.cms.layout;

import java.util.List;

import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class LayoutService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private LayoutMapper layoutMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<LayoutVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return layoutMapper.selectList(req);
	}
	
	public LayoutVo select(EgovMap req) {
		
		return layoutMapper.select(req);
	}
	
	public List<LayoutVo> selectAll(EgovMap req) {
		return layoutMapper.selectList(req);
	}
	
	@Transactional
	public int insert(LayoutVo vo) {
		
		return layoutMapper.insert(vo);
	}
	@Transactional
	public int update(LayoutVo vo) {
		
		return layoutMapper.update(vo);
	}
	
	public int delete(EgovMap req) {
		
		return layoutMapper.delete(req);
	}
	
}
