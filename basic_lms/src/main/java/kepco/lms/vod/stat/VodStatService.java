package kepco.lms.vod.stat;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class VodStatService {
	
	@Autowired
	private VodStatMapper vodStatMapper;
	
	
	public List<VodStatVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return vodStatMapper.selectList(req);
	}

	public List<VodStatVo> selectAll(EgovMap req) {
		return vodStatMapper.selectList(req);
	}
	
	public VodStatVo select(EgovMap req) {
		return vodStatMapper.select(req);
	}
	
	public List<EgovMap> vodStatList(EgovMap req) {
		return vodStatMapper.vodStatList(req);
	}
	
	public List<EgovMap> vodContentsStatList(EgovMap req) {
		return vodStatMapper.vodContentsStatList(req);
	}
	
	public List<EgovMap> shareByCategory(EgovMap req) {
		return vodStatMapper.shareByCategory(req);
	}
	public List<EgovMap> vodKeyword(EgovMap req) {
		return vodStatMapper.vodKeyword(req);
	}
	public List<EgovMap> studentList(EgovMap req) {
		return vodStatMapper.studentList(req);
	}
	
	public List<VodStatVo> vodStatExcel(EgovMap req) {
		return vodStatMapper.vodStatExcel(req);
	}
	public List<VodStatVo> vodContentsStatExcel(EgovMap req) {
		return vodStatMapper.vodContentsStatExcel(req);
	}
}
