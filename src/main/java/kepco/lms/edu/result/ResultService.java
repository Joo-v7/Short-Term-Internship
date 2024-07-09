package kepco.lms.edu.result;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class ResultService {
	
	@Autowired
	private ResultMapper resultMapper;
	
	
	public List<ResultVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return resultMapper.selectList(req);
	}

	public List<ResultVo> selectAll(EgovMap req) {
		return resultMapper.selectList(req);
	}
	
	public List<ResultVo> select(EgovMap req) {
		return resultMapper.select(req);
	}

}
