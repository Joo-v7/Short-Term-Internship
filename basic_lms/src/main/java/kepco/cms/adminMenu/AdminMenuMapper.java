package kepco.cms.adminMenu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMenuMapper {
	
	public List<AdminMenuVo> selectList(Map req);

	public AdminMenuVo select(Map req);
	
	public int insert(AdminMenuVo vo);
	
	public int update(AdminMenuVo vo);
	
	public int delete(Map req);
	
}
