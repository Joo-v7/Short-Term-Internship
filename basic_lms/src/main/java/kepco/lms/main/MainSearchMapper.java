package kepco.lms.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.cms.board.post.PostVo;
import kepco.cms.board.post.file.FileVo;
import kepco.cms.menu.MenuVo;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.vod.VodVo;

@Mapper
public interface MainSearchMapper {
		
	public List<MenuVo> menuList(EgovMap req);
	public List<PostVo> postList(EgovMap req);
	public List<DetailVo> detailList(EgovMap req);
	public List<VodVo> vodList(EgovMap req);
	public List<FileVo> fileList(EgovMap req);
	public List<MainSearchVo> dayHistory(EgovMap req);
	public List<MainSearchVo> weekHistory(EgovMap req);
	public long deleteOldData(EgovMap req);
	public int insert(EgovMap req);
}
