package kepco.cms.systemLog;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface SystemLogMapper {
	
	public List<SystemLogVo> selectList(EgovMap req);
	
	public List<SystemLogVo> selectAll(EgovMap req);
	
	public List<SystemLogVo> useStat(EgovMap req);

	public SystemLogVo select(EgovMap req);
	
	public int insert(EgovMap req);
}
