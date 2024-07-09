package kepco.lms.edu.process;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface EduProcessMapper {
	
	public List<EduProcessVo> selectList(EgovMap req);
	
	public List<EduProcessVo> selectAll(EgovMap req);
	
	public List<EduProcessVo> taskAll(EgovMap req);
	
	public EduProcessVo select(EgovMap req);
	
	public EduProcessVo taskSelect(EgovMap req);
	
	public int count(EgovMap req);
	
	public int insert(EduProcessVo vo);
	
	public int update(EduProcessVo vo);
	
	public int delete(EgovMap req);
	
	public int idCheck(EduProcessVo eduProcessVo);
	
	public int aiIdCheck(EduProcessVo eduProcessVo);
}
