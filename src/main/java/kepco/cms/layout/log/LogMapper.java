package kepco.cms.layout.log;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.context.annotation.Configuration;

@Configuration("kepco.cms.layout.log.LogMapper")
@Mapper
public interface LogMapper {
		
	public List<LogVo> selectList(EgovMap req);
	public LogVo select(EgovMap req);
	
	public int insert(EgovMap req);
}
