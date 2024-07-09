package kepco.cms.sec.role.log;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.fasterxml.jackson.annotation.JsonFormat;


@Mapper
public interface RoleLogMapper {
	
	public List<RoleLogVo> selectList(EgovMap req);
	public RoleLogVo select(EgovMap req);
	public int insert(RoleLogVo vo);

}