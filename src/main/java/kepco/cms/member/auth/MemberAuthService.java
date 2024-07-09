package kepco.cms.member.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Validator;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kepco.cms.member.MemberService;
import kepco.cms.member.MemberVo;
import kepco.cms.sec.role.RoleService;
import kepco.cms.sec.roleAuth.RoleAuthService;


@Service
public class MemberAuthService extends EgovAbstractServiceImpl implements UserDetailsService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	RoleAuthService roleAuthService;
	
	@Autowired
	private MemberAuthMapper memberAuthMapper;
	
	@Autowired
	private ApplicationContext context;
	
	public MemberAuthVo select(EgovMap req) {
		return memberAuthMapper.select(req);
	}
	
	public List<MemberVo> memberList(EgovMap req) {
		return memberAuthMapper.memberList(req);
	}
	
	public MemberVo memberSelect(EgovMap req) {
		return memberAuthMapper.memberSelect(req);
	}
	
	public int authCountByEmail(EgovMap req) {
		return memberAuthMapper.authCountByEmail(req);
	}
	
	public int createAuthKey(MemberAuthVo req) {
		return memberAuthMapper.createAuthKey(req);
	}
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws AuthenticationException {

		MemberVo vo = memberService.login(memberId);
		
		if (vo == null) {
			throw new UsernameNotFoundException("사용자 정보를 조회할 수 없습니다.");
		} else if(vo.getLoginFail() >= 5) {
			throw new DisabledException("계정이 비활성화 상태입니다. 관리자에게 문의하세요.");
		} else if(!"y".equals(vo.getMemberApproval())) {
			throw new DisabledException("계정이 승인되지 않았습니다.");
		}
		
		MemberDetailVo memberDetailVo = new MemberDetailVo();
		memberDetailVo.setMemberIdx(vo.getMemberIdx());
		memberDetailVo.setMemberId(vo.getMemberId());
		memberDetailVo.setMemberPw(vo.getMemberPw());
		memberDetailVo.setMemberName(vo.getMemberNm());
		memberDetailVo.setMemberRole(vo.getMemberRole());
		
		// 하위 역할 가져오기
		List<String> roleList = roleService.selectRoleCdChildrenList(vo.getMemberRole());
		roleList.add(vo.getMemberRole());
		
		memberDetailVo.setRoleList(roleList);
		
		// 역할별 권한 가져오기
		List<String> authList = roleAuthService.selectAuthCdList(roleList);
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		for(String role : roleList) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		for(String auth : authList) {
			authorities.add(new SimpleGrantedAuthority(auth));
		}
		memberDetailVo.setAuthorities(authorities);
		
		if(vo.getMemberRole().startsWith("ROLE_ADMIN")) {
			memberDetailVo.setIsAdmin(true);
		}
		return memberDetailVo;
	}
	
	/**
	 * 오래된 데이터(하루 경과) 삭제
	 */
	@Scheduled(cron = "0 0 * * * ?")
	public void deleteOldData() {
		
		EgovMap req = new EgovMap();
		long ret = memberAuthMapper.deleteOldData(req);
	}
	
}
