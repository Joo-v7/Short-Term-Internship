package kepco.lms.vod.category;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class VodCategoryService {
	
	@Autowired
	private VodCategoryMapper vodCategoryMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<VodCategoryVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return vodCategoryMapper.selectList(req);
	}
	
	public List<VodCategoryVo> selectAll(EgovMap req) {
		return vodCategoryMapper.selectList(req);
	}
	
	public VodCategoryVo select(EgovMap req) {
		return vodCategoryMapper.select(req);
	}
	
	@Transactional
	public int insert(VodCategoryVo vo) {
		
		return vodCategoryMapper.insert(vo);
	}
	
	@Transactional
	public int update(VodCategoryVo vo) {
		
		return vodCategoryMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return vodCategoryMapper.delete(req);
	}
}
