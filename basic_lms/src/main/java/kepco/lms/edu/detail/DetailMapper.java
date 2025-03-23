package kepco.lms.edu.detail;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface DetailMapper {
	
	public List<DetailVo> selectList(EgovMap req);
	
	public List<DetailVo> selectAll(EgovMap req);

	public DetailVo select(EgovMap req);

	public List<DetailVo> detail(EgovMap req);

	@Select(value = { "select edu_image1,edu_image2,edu_file1,edu_file2,edu_file3,edu_file4,edu_file5 from lms_edu_detail where detail_idx = ${detailIdx}" })
	public DetailVo fileSelect(EgovMap req);
	
	public int insert(DetailVo vo);
	
	public int update(DetailVo vo);
	
	public int delete(EgovMap req);

	public List<DetailVo> dashboardCalendar(EgovMap req);

	public int thumbnailUpdate(DetailVo vo);
	
	public int fileUpdate(DetailVo vo);
	
}
