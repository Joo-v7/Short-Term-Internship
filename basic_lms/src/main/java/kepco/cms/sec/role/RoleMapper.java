package kepco.cms.sec.role;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.fasterxml.jackson.annotation.JsonFormat;


@Mapper
public interface RoleMapper {
	
	public List<RoleVo> selectList(EgovMap req);
	public RoleVo select(EgovMap req);
	public int insert(RoleVo vo);
	public int update(RoleVo vo);
	public int delete(EgovMap req);
	
	/**
	 * 
	 * @return
	 */
	public List<RoleVo> selectRoleCdList();
	
	/**
	 * 
	 * @param parentRoleCd
	 * @return
	 */
	public List<String> selectRoleCdChildrenList(String parentRoleCd);

}