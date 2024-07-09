package kepco.cms.sec.roleHierarchy;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.fasterxml.jackson.annotation.JsonFormat;


@Mapper
public interface RoleHierarchyMapper {
	
	public List<RoleHierarchyVo> selectList(EgovMap req);

}