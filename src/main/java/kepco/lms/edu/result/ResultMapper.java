package kepco.lms.edu.result;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ResultMapper {
	
	public List<ResultVo> selectList(EgovMap req);
	
	public List<ResultVo> selectAll(EgovMap req);

	public List<ResultVo> select(EgovMap req);
	
}
