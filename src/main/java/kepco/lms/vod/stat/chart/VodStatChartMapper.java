package kepco.lms.vod.stat.chart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface VodStatChartMapper {
	
	public List<EgovMap> shareByCategory(EgovMap req);
	
	public List<EgovMap> vodKeyword(EgovMap req);
	
	public EgovMap graduateRate(EgovMap req);
}
