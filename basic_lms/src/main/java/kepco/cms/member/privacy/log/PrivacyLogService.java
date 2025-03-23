package kepco.cms.member.privacy.log;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.member.MemberVo;
import kepco.util.StrUtil;

@Service
public class PrivacyLogService {

	@Autowired
	private PrivacyLogMapper logMapper;
	
	public List<PrivacyLogVo> selectList(EgovMap req){
		return logMapper.selectList(req);
	}
	public PrivacyLogVo select(EgovMap req) {
		return logMapper.select(req);
	}
	public PrivacyLogVo selectAll(EgovMap req) {
		return logMapper.select(req);
	}
	@Transactional
	public int insert(EgovMap req) {
		return logMapper.insert(req);
	}
}
