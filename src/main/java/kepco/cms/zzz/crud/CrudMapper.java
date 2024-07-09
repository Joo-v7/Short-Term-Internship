package kepco.cms.zzz.crud;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface CrudMapper {
	
	public List<CrudVo> selectList(EgovMap req);

	public CrudVo select(EgovMap req);
	
	public long insert(CrudVo vo);
	
	public long update(CrudVo req);
	
	public long delete(EgovMap req);
}
