package kepco.cms.site;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

@Mapper
public interface SiteMapper {
	
	public List<SiteVo> selectList(EgovMap req);
	
	public SiteVo select(EgovMap req);
	
	public int insert(SiteVo vo);
	
	public int update(SiteVo vo);
	
	public int delete(EgovMap req);
	
	public int defaultSiteCnt(String exceptSite);

	
}
