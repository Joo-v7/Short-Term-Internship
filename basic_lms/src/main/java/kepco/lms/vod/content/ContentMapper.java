package kepco.lms.vod.content;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ContentMapper {
	
	public List<ContentVo> selectList(EgovMap req);
	
	public List<ContentVo> selectAll(EgovMap req);

	public ContentVo select(EgovMap req);
	
	public int insert(ContentVo vo);
	
	public int update(ContentVo vo);
	
	public int delete(EgovMap req);
	
	public int fileUpdate(ContentVo vo);
}
