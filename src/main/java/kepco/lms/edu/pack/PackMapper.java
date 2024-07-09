package kepco.lms.edu.pack;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface PackMapper {
	
	public List<PackVo> selectList(EgovMap req);
	
	public List<PackVo> selectAll(EgovMap req);

	public PackVo select(EgovMap req);
	
	public int insert(PackVo vo);
	
	public int update(PackVo vo);
	
	public int delete(EgovMap req);
	
}
