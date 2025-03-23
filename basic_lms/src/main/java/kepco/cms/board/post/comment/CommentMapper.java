package kepco.cms.board.post.comment;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface CommentMapper {

	public List<CommentVo> selectList(EgovMap req);

	public List<CommentVo> selectAll(EgovMap req);
	
	public CommentVo select(EgovMap req);

	public int insert(CommentVo vo);
	
	public int update(CommentVo vo);
	
	public int delete(EgovMap req);

	public List<CommentVo> selectPostComment(EgovMap req);
	
	public int commentadd(EgovMap req);
	
	public int commentdelete(EgovMap req);
	
	

}
