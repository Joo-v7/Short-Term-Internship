package kepco.lms.vod.stat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface VodStatMapper {
	
	public List<VodStatVo> selectList(EgovMap req);
	
	public List<VodStatVo> selectAll(EgovMap req);

	public VodStatVo select(EgovMap req);
	
	public List<EgovMap> vodStatList(EgovMap req);
	
	public List<EgovMap> vodContentsStatList(EgovMap req);
	
	public List<EgovMap> shareByCategory(EgovMap req);
	
	public List<EgovMap> vodKeyword(EgovMap req);
	
	public List<EgovMap> studentList(EgovMap req);
	
	public List<VodStatVo> vodStatExcel(EgovMap req);
	
	public List<VodStatVo> vodContentsStatExcel(EgovMap req);
}
