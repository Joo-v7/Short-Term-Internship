package kepco.cms.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kepco.cms.common.EditorVo;

@Mapper
public interface EditorMapper {
	public int insert(EditorVo vo);

}
