package kepco.cms.sec.auth;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import com.fasterxml.jackson.annotation.JsonFormat;


@Mapper
public interface AuthMapper {
	
	public List<AuthVo> selectList(EgovMap req);
	public AuthVo select(EgovMap req);
	public int insert(AuthVo vo);
	public int update(AuthVo vo);
	public int delete(EgovMap req);
	
	/** 
	 * 
	 * @param roleList
	 * @return
	 */
	public List<String> selectAuthCdList(List<String> roleList);

}