package kepco.cms.page;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface PageMapper {

	public List<PageVo> selectList(EgovMap req);
	
	public PageVo select(EgovMap req);
	
	public int insert(PageVo vo);
	
	public int update(PageVo vo);
	
	public int delete(EgovMap req);
	
}
