package kepco.lms.edu.regist;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface RegistMapper {
	
	public List<RegistVo> selectList(EgovMap req);

	public List<RegistVo> selectMyList(EgovMap req);
	
	public List<RegistVo> selectAll(EgovMap req);
	
	public List<RegistVo> studentList(EgovMap req);
	
	public List<RegistVo> myselectall(EgovMap req);
	
	public RegistVo select(EgovMap req);
	
	public RegistVo selectByDetailAndMember(EgovMap req);

	public int duplicate(EgovMap req);

	@Select(value = { "SELECT IFNULL(MAX(member_idx),0) memberIdx FROM cms_member WHERE member_id = #{memberId}" })
	public int checkIdx(String req);
	
	public int insert(RegistVo vo);
	
	public int update(RegistVo vo);
	
	public int graduate(RegistVo vo);
	
	public int delete(EgovMap req);
	
	public int eduStateSave(EgovMap req);
}
