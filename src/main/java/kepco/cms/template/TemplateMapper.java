package kepco.cms.template;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface TemplateMapper {
	
	public List<TemplateVo> selectList(EgovMap req);
	
	public TemplateVo select(EgovMap req);
	
	public int insert(TemplateVo vo);
	
	public int update(TemplateVo vo);

	public int logSave(TemplateVo vo);
	
	public int delete(EgovMap req);
	
}
