package kepco.lms.edu.category;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.lms.edu.regist.RegistVo;


@Mapper
public interface CategoryMapper {
	
	public List<CategoryVo> selectList(EgovMap req);
	
	public List<CategoryVo> selectAll(EgovMap req);

	public CategoryVo select(EgovMap req);
	
	public int insert(CategoryVo vo);
	
	public int update(CategoryVo vo);
	
	public int delete(EgovMap req);
}
