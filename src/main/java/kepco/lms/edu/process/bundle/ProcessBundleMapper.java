package kepco.lms.edu.process.bundle;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface ProcessBundleMapper {
	
	public List<ProcessBundleVo> selectList(EgovMap req);
	
	public List<ProcessBundleVo> selectAll(EgovMap req);

	public ProcessBundleVo select(EgovMap req);
	
	public int insert(ProcessBundleVo vo);
	
	public int update(ProcessBundleVo vo);
	
	public int delete(EgovMap req);
	
}
