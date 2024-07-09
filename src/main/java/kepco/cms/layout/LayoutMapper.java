package kepco.cms.layout;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface LayoutMapper {
	
	public List<LayoutVo> selectList(EgovMap req);
	
	public LayoutVo select(EgovMap req);
	
	public int insert(LayoutVo vo);
	
	public int update(LayoutVo vo);
	
	public int delete(EgovMap req);
	
}
