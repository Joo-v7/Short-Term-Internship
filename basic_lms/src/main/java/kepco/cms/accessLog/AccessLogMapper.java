package kepco.cms.accessLog;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface AccessLogMapper {

	public int insert(EgovMap req);
	
	public List<Object> hourList(EgovMap req);
	
	public List<Object> dayList(EgovMap req);
	
}
