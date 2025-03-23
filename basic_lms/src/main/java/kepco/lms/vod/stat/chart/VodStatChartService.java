package kepco.lms.vod.stat.chart;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class VodStatChartService {
	
	@Autowired
	private VodStatChartMapper vodStatChartMapper;
	
	public List<EgovMap> shareByCategory(EgovMap req) {
		return vodStatChartMapper.shareByCategory(req);
	}
	public List<EgovMap> vodKeyword(EgovMap req) {
		return vodStatChartMapper.vodKeyword(req);
	}
	public EgovMap graduateRate(EgovMap req) {
		return vodStatChartMapper.graduateRate(req);
	}
}
