package kepco.cms.board.post.file;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import kepco.cms.board.BoardVo;

@Mapper
public interface FileMapper {

	public List<FileVo> selectList(EgovMap req);

	public List<FileVo> selectAll(EgovMap req);
	
	public FileVo select(EgovMap req);

	public int insert(FileVo vo);
	
	public int update(FileVo vo);
	
	public int delete(EgovMap req);

	@Update(value = { "update cms_board_post_file set file_download = file_download + 1 where delete_yn = 'n' and file_idx = ${fileIdx}" })
	public int download(FileVo vo);
	
	/**
	 * 게시물ID로 파일 목록 가져오기
	 * @param req
	 * @return
	 * @deprecated @see {@link FileMapper#selectByFileIdx(int)}
	 */
	@Select(value = { "select * from cms_board_post_file where post_idx = ${postIdx} order by file_idx asc" })
	@Deprecated
	public List<FileVo> selectFile(EgovMap req);
	
	/**
	 * 게시물ID로 파일 목록 가져오기
	 * @param postIdx
	 * @return
	 */
	@Select(value = { "select * from cms_board_post_file where post_idx = ${postIdx} order by file_idx asc"})
	public List<FileVo> selectListByPostIdx(int postIdx);
	
	/**
	 * 파일ID로 1건 가져오기
	 * @param fileIdx
	 * @return
	 */
	@Select(value = { "select * from cms_board_post_file where file_idx = ${fileIdx} order by file_idx asc"})
	public FileVo selectByFileIdx(int fileIdx);
	
	@Select(value = { "select file_idx, file_save_name, file_origin_name from cms_board_post_file where file_idx = ${fileIdx}" })
	public FileVo selectFile2(EgovMap req);
	
	@Delete(value = { "delete from cms_board_post_file where file_idx = ${fileIdx}" })
	public int deleteFile(String req);
	
	@Delete(value = { "delete from cms_board_post_file where file_idx = ${fileIdx}" })
	public int deleteFile2(EgovMap req);
	
	public int fileadd(EgovMap req);
	
	public int filedelete(EgovMap req);
	
	/**
	 * 게시물의 전체파일 삭제
	 * @param req
	 * @return
	 */
	public int deleteListByPostIdx(EgovMap req);
}
