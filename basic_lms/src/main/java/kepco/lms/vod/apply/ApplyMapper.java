package kepco.lms.vod.apply;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ApplyMapper {
	
	public List<ApplyVo> selectList(EgovMap req);

	public List<ApplyVo> selectMyList(EgovMap req);
	
	public List<ApplyVo> selectApplyMyList(EgovMap req);
	
	public List<ApplyVo> selectAll(EgovMap req);

	public ApplyVo select(EgovMap req);

	public int duplicate(EgovMap req);

	@Select(value = { "SELECT IFNULL(MAX(member_idx),0) memberIdx FROM cms_member WHERE member_id = #{memberId}" })
	public int checkIdx(String req);
	
	public int insert(ApplyVo vo);
	
	public int update(ApplyVo vo);

	public int learnUpdate(EgovMap req);
	
	public int delete(EgovMap req);
	
}
