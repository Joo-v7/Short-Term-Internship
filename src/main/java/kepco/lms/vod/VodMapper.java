package kepco.lms.vod;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface VodMapper {
	
	public List<VodVo> selectList(EgovMap req);
	
	public List<VodVo> selectAll(EgovMap req);

	public Object myPrintSelect(EgovMap req);

	public List<VodVo> myListSelect(EgovMap req);
	
	public List<VodVo> myApplyListSelect(EgovMap req);
	
	public VodVo select(EgovMap req);
	
	public int insert(VodVo vo);
	
	public int update(VodVo vo);
	
	public int delete(EgovMap req);
	
	@Select(value = { "select vod_image,vod_file from lms_vod where vod_idx = ${vodIdx}" })
	public VodVo fileSelect(EgovMap req);
	
	public int thumbnailUpdate(VodVo vo);
	
	public int fileUpdate(VodVo vo);
}
