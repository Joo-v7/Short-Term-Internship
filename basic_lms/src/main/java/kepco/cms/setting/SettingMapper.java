package kepco.cms.setting;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface SettingMapper {
	
	public List<SettingVo> selectList(EgovMap req);

	@Select("select setting_value from cms_setting where setting_key = 'ban_ip'")
	public List<String> selectIpList();
	
	public SettingVo select(EgovMap req);
	
	public int insert(SettingVo vo);
	
	public int update(SettingVo vo);
	
	public int delete(EgovMap req);
	
	public int secureVaildate(String str);

}