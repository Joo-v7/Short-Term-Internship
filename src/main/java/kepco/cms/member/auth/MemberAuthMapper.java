package kepco.cms.member.auth;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.cms.member.MemberVo;

@Mapper
public interface MemberAuthMapper {
	
	public List<MemberAuthVo> selectList(EgovMap req);

	public MemberAuthVo select(EgovMap req);
	
	public List<MemberVo> memberList(EgovMap req);
	
	public MemberVo memberSelect(EgovMap req);
	
	public int insert(MemberAuthVo vo);
	
	public int update(MemberAuthVo vo);
	
	public int delete(EgovMap req);
	
	public int createAuthKey(MemberAuthVo req);
	
	public int authCountByEmail(EgovMap req);
	
	public long deleteOldData(EgovMap req);
}
