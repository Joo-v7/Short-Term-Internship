package kepco.lms.vod.bundle;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface BundleMapper {
	
	public List<BundleVo> selectList(EgovMap req);
	
	public List<BundleVo> selectAll(EgovMap req);

	public BundleVo select(EgovMap req);
	
	public int insert(BundleVo vo);
	
	public int update(BundleVo vo);
	
	public int delete(EgovMap req);
	
}
