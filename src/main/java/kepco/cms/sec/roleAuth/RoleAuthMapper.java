package kepco.cms.sec.roleAuth;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.fasterxml.jackson.annotation.JsonFormat;


@Mapper
public interface RoleAuthMapper {
	
	public List<RoleAuthVo> selectList(EgovMap req);
	public RoleAuthVo select(EgovMap req);
	public int insert(RoleAuthVo vo);
	public int delete(EgovMap req);
	/**
	 * 
	 * @param roleList
	 * @return
	 */
	public List<String> selectAuthCdList(Collection<String> roleList);

}