package kepco.cms.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface MenuMapper {
	
	public List<MenuVo> selectList(EgovMap req);
	
	public MenuVo select(EgovMap req);
	
	public int selectCnt(EgovMap req);
	
	public int insert(MenuVo vo);
	
	public int update(MenuVo vo);
	
	public int delete(EgovMap req);

}