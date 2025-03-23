package kepco.cms.member.privacy.log;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface PrivacyLogMapper {
	
	public List<PrivacyLogVo> selectList(EgovMap req);
	public PrivacyLogVo select(EgovMap req);
	public int insert(EgovMap req);
}