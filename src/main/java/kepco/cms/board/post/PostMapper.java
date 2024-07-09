package kepco.cms.board.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface PostMapper {

	public List<PostVo> selectList(EgovMap req);

	public List<PostVo> selectAll(EgovMap req);
	
	public PostVo select(EgovMap req);
	
	@Deprecated
	public int cntadd(EgovMap req);
	
	@Deprecated
	public PostVo selectTotal(EgovMap req);
	
	@Deprecated
	public List<PostVo> selectComment(EgovMap req);

	public int insert(PostVo vo);
	
	public int update(PostVo vo);
	
	public int delete(EgovMap req);
	
	@Deprecated
	public int deleteFile(EgovMap req);
	
	/** 조회수 증가 */
	@Update("UPDATE cms_board_post SET POST_HIT = POST_HIT + 1 WHERE POST_IDX = #{postIdx}")
	public int updatePostHit(int postIdx);
	
	/** 쓰기시 게시판의 그룹ID 가져오기 */
	@Select("SELECT IFNULL(MAX(POST_GROUP_ID), 0) FROM cms_board_post WHERE BOARD_IDX = #{boardIdx}")
	public int selectPostGroupId(int boardIdx);
	
	/** 답변시 그룹내 게시물 순서 조정 */
	@Update("UPDATE cms_board_post SET POST_GROUP_ORDER = POST_GROUP_ORDER + 1 WHERE BOARD_IDX = #{boardIdx} AND POST_GROUP_ID = #{postGroupId} AND POST_GROUP_ORDER >= #{postGroupOrder}")
	public int updatePosGroupOrder(int boardIdx, int postGroupId, int postGroupOrder);
	
	/** 수정시 적용되는 컬럼만 제한적 UPDATE */
	public int updateModify(PostVo vo);
	
	/** 답변 갯수 */ 
	@Select("SELECT COUNT(1) FROM cms_board_post WHERE POST_PARENT_IDX = #{postIdx} AND delete_yn = 'n'")
	public int selectChildCount(int postIdx);
	
	/** 댓글 갯수 */ 
	@Select("SELECT COUNT(1) FROM cms_board_post_comment WHERE POST_IDX = #{postIdx} AND delete_yn = 'n'")
	public int selectCommentCount(int postIdx);
	
	/** 게시물의 첨부파일(일반) 갯수 갱신 */
	@Update("update cms_board_post set post_file = (select if(count(1) > 0, 1, 0) from cms_board_post_file where post_idx = #{postIdx} and delete_yn = 'n') where post_idx = #{postIdx}")
	public int updatePostFile(int postIdx);
	
	/** 게시물의 첨부파일(사진) 갯수 갱신 */
	@Update("update cms_board_post set post_image = (select if(count(1) > 0, 1, 0) from cms_board_post_file where post_idx = #{postIdx} and delete_yn = 'n' and file_is_image = 1) where post_idx = #{postIdx}")
	public int updatePostImage(int postIdx);
	
	/** 게시물의 댓글 수 갱신 */
	@Update("update cms_board_post set post_comment_count = (select count(1) from cms_board_post_comment where post_idx = #{postIdx} and delete_yn = 'n') where post_idx = #{postIdx}")
	public int updatePostCommentCount(int postIdx);
	
}
