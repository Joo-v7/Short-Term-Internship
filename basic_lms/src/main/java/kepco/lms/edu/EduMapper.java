package kepco.lms.edu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface EduMapper {
	
	public List<EduVo> selectList(EgovMap req);
	
	public List<EduVo> selectAll(EgovMap req);
	
	public List<EduVo> myListSelect(EgovMap req);
	
	public List<EduVo> myPlayListSelect(EgovMap req);
	
	public List<EduVo> myApplyListSelect(EgovMap req);
	
	public List<EduVo> myApplyListSelect2(EgovMap req);

	public EduVo select(EgovMap req);
	
	public int insert(EduVo vo);
	
	public int update(EduVo vo);
	
	public int fileUpdate(EduVo vo);
	
	public int delete(EgovMap req);
	
}
