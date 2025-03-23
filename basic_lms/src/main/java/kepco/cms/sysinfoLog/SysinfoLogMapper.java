package kepco.cms.sysinfoLog;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface SysinfoLogMapper {
	
	public List<SysinfoLogVo> selectList(EgovMap req);
	
	public List<SysinfoLogVo> selectAll(EgovMap req);

	public SysinfoLogVo select(EgovMap req);
	
	public long insert(EgovMap req);

	public long deleteOldData(EgovMap req);
	
//	HashMap<String, Double> avgByPeriod(EgovMap req);
	
	public List<SysinfoLogVo> chartList(EgovMap req);
}
