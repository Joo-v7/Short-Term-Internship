package kepco.cms.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.cms.member.auth.MemberAuthVo;

@Mapper
public interface MemberMapper {
	
	public List<MemberVo> selectList(EgovMap req);

	public MemberVo select(EgovMap req);

	public MemberVo login(String req);
	
	public int insert(MemberVo vo);
	
	public int update(MemberVo vo);
	
	public int delete(EgovMap req);
	
	@Update(value = { "update cms_member set login_fail = 0, login_date = CURRENT_TIMESTAMP where delete_yn = 'n' and member_idx = ${memberIdx}" })
	public int loginSuccess(int vo);

	@Update(value = { "update cms_member set login_fail = ifnull(login_fail,0)+1 where delete_yn = 'n' and member_id = #{memberId}" })
	public int loginFail(String vo);

	@Update(value = { "update cms_member set login_fail = 0 where delete_yn = 'n' and member_idx = ${memberIdx}" })
	public int failReset(int vo);

	public int deleteCopy(EgovMap vo);
	
	@Select(value = { "select member_id from cms_member where delete_yn = 'n' and member_nm = #{memberNm} and member_phone = #{memberPhone}" })
	public String findId(EgovMap req);
	
	@Select(value = { "select member_idx from cms_member where delete_yn = 'n' and member_id = #{memberId} and member_nm = #{memberNm} and member_phone = #{memberPhone}" })
	public String findPw(EgovMap req);
	
	@Update(value = { "update cms_member set member_pw = #{memberPw} where delete_yn = 'n' and member_idx = ${memberIdx}" })
	public int changePw(MemberVo vo);
	
	@Select(value = { "select count(1) from cms_member where delete_yn = 'n' and member_idx = #{memberIdx} and member_pw = #{memberPw}" })
	public int pwCheck(MemberVo vo);
	
	@Select(value = { "select count(1) from cms_member where delete_yn = 'n' and member_id = #{memberId}" })
	public int idCheck(String req);
	
	public int createAuthKey(MemberAuthVo req);
	
	public int authCountByEmail(EgovMap req);
	
	public List<MemberVo> playMemberList(EgovMap req);
}
