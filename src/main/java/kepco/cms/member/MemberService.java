package kepco.cms.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.common.config.EncryptionService;
import kepco.util.StrUtil;


@Service
public class MemberService extends EgovAbstractServiceImpl {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private MemberMapper memberMapper;

	public List<MemberVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return memberMapper.selectList(req);
	}
	
	public List<MemberVo> selectAll(EgovMap req) {
		return memberMapper.selectList(req);
	}
	
	public MemberVo select(EgovMap req) {
		MemberVo vo = memberMapper.select(req);
		
		if(vo != null) {

			//이메일 및 전화번호 암호화 해제
			if(vo.getMemberEmail().length() > 40) {
				String decryptedEmail = encryptionService.decryptData(vo.getMemberEmail());
		        vo.setMemberEmail(decryptedEmail);
			}
	        
			if(vo.getMemberPhone().length() > 20) {
				String decryptedPhone = encryptionService.decryptData(vo.getMemberPhone());
			    vo.setMemberPhone(decryptedPhone);
			}
			
			String birth = vo.getMemberBirth();
			if(!"".equals(birth)) {
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				
				try {
					Date formatDate = simpleDateFormat.parse(birth);
					String stDate = simpleDateFormat.format(formatDate);
					vo.setMemberBirth(stDate);	
				} catch (ParseException e) {
				}
			}
		}
		
		
		return vo;
	}

	public MemberVo login(String req) {
		return memberMapper.login(req);
	}
	
	@Transactional
	public int insert(MemberVo vo) {
		
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<MemberVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		vo.setMemberPw(passwordEncoder.encode(vo.getMemberPw()));
		
		//이메일 및 전화번호 암호화
        String encryptedEmail = encryptionService.encryptData(vo.getMemberEmail());
        vo.setMemberEmail(encryptedEmail);
        System.out.println("암호화된 이메일 데이터: " + encryptedEmail);
		
        String encryptedPhone = encryptionService.encryptData(vo.getMemberPhone());
        vo.setMemberPhone(encryptedPhone);
        System.out.println("암호화된 폰 번호 데이터: " + encryptedPhone);
		
		return memberMapper.insert(vo);
	}
	
	@Transactional
	public int update(MemberVo vo) {
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<MemberVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(!StrUtil.isEmpty(vo.getMemberPw())) {
			vo.setMemberPw(passwordEncoder.encode(vo.getMemberPw()));
		} else {
			//입력값이 없을경우 수정되지 않도록 null입력
			vo.setMemberPw(null);
		}
		
		//이메일 및 전화번호 암호화
        String encryptedEmail = encryptionService.encryptData(vo.getMemberEmail());
        vo.setMemberEmail(encryptedEmail);
        System.out.println("암호화된 이메일 데이터: " + encryptedEmail);
		
        String encryptedPhone = encryptionService.encryptData(vo.getMemberPhone());
        vo.setMemberPhone(encryptedPhone);
        System.out.println("암호화된 폰 번호 데이터: " + encryptedPhone);
		
		return memberMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return memberMapper.delete(req);
	}

	@Transactional
	public int loginSuccess(int vo) {
		return memberMapper.loginSuccess(vo);
	}

	@Transactional
	public int loginFail(String vo) {
		return memberMapper.loginFail(vo);
	}

	@Transactional
	public int failReset(int vo) {
		return memberMapper.failReset(vo);
	}

	@Transactional
	public int deleteCopy(EgovMap vo) {
		return memberMapper.deleteCopy(vo);
	}
	
	public String findId(EgovMap req) {
		return memberMapper.findId(req);
	}

	public String findPw(EgovMap req) {
		return memberMapper.findPw(req);
	}
	
	@Transactional
	public int changePw(MemberVo vo) {
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(!StrUtil.isEmpty(vo.getMemberPw())) {
			vo.setMemberPw(passwordEncoder.encode(vo.getMemberPw()));
		} else {
			//입력값이 없을경우 수정되지 않도록 null입력
			vo.setMemberPw(null);
		}
		return memberMapper.changePw(vo);
	}
	
	public int pwCheck(MemberVo vo) {
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		vo.setMemberPw(passwordEncoder.encode(vo.getMemberPw()));
		
		return memberMapper.pwCheck(vo);
	}
	
	public int idCheck(String req) {
		
		return memberMapper.idCheck(req);
	}
	
	public List<MemberVo> playMemberList(EgovMap req) {
		return memberMapper.playMemberList(req);
	}
}
