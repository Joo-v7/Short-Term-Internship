package kepco.lms.vod.category;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.lms.edu.regist.RegistVo;


@Mapper
public interface VodCategoryMapper {
	
	public List<VodCategoryVo> selectList(EgovMap req);
	
	public List<VodCategoryVo> selectAll(EgovMap req);

	public VodCategoryVo select(EgovMap req);
	
	public int insert(VodCategoryVo vo);
	
	public int update(VodCategoryVo vo);
	
	public int delete(EgovMap req);
}
