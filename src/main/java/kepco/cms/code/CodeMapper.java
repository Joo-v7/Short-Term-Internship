package kepco.cms.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface CodeMapper {
	
	public List<CodeVo> selectList(EgovMap req);
	
	public List<CodeVo> selectCodeList(EgovMap req);

	public CodeVo select(EgovMap req);
	
	public int insert(CodeVo vo);

	public int insertCode(CodeVo vo);
	
	public int update(CodeVo vo);
	
	public int delete(EgovMap req);
	
}
