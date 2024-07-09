package kepco.cms.visitCount;


import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface VisitCountMapper {

	public long insert(VisitCountVo vo);
	
	

}
