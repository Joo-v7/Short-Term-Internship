package kepco.cms.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface BoardMapper {

	public List<BoardVo> selectList(EgovMap req);

	public BoardVo select(EgovMap req);

	public int insert(BoardVo vo);
	
	public int update(BoardVo vo);
	
	public int delete(EgovMap req);

	public BoardVo selectUser(EgovMap req);
	
	
	public List<BoardCateVo> selectCateUser(EgovMap req);
	
}
