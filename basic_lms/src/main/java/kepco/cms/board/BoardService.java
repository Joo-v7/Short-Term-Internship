package kepco.cms.board;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.board.post.PostVo;
import kepco.cms.member.auth.MemberDetailVo;
import kepco.cms.systemLog.SystemLogService;
import kepco.util.CamelMap;
import kepco.util.StrUtil;

@Service
public class BoardService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<BoardVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 15);
		PageHelper.startPage(pageNum, pageSize);
		
		return boardMapper.selectList(req);
	}

	public List<BoardVo> selectAll(EgovMap req) {
		return boardMapper.selectList(req);
	}
	
	public BoardVo select(EgovMap req) {
		return boardMapper.select(req);
	}
	
	
	public BoardVo selectUser(EgovMap req) {
		return boardMapper.selectUser(req);
	}
	
	public List<BoardCateVo> selectCateUser(EgovMap req) {
		return boardMapper.selectCateUser(req);
	}
	
	
	
	

	@Transactional
	public int insert(BoardVo vo) {

		Set<ConstraintViolation<BoardVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<BoardVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return boardMapper.insert(vo);
	}
	
	@Transactional
	public int update(BoardVo vo) {

		Set<ConstraintViolation<BoardVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<BoardVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return boardMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return boardMapper.delete(req);
	}
	
	
	/**
	 * 목록에서 권한 가져오기
	 * @param memberDetailVo
	 * @param boardVo
	 * @return
	 */
	public CamelMap getBoardPermissionList(MemberDetailVo memberDetailVo, BoardVo boardVo) {
		
		CamelMap boardPermission = new CamelMap();
		
		boardPermission.put("isBoardAdmin", false);
		
		boardPermission.put("list", false);
		boardPermission.put("read", false);
		boardPermission.put("write", false);
//		boardPermission.put("reply", false);
//		boardPermission.put("upload", false);
//		boardPermission.put("download", false);
//		boardPermission.put("comment", false);
		
		// 관리자
		if(memberDetailVo.getIsAdmin()) {
			boardPermission.put("list", "y".equals(boardVo.getBoardAuthListAdmin()));
			boardPermission.put("read", "y".equals(boardVo.getBoardAuthReadAdmin()));
			boardPermission.put("write", "y".equals(boardVo.getBoardAuthWriteAdmin()));
//			boardPermission.put("reply", "y".equals(boardVo.getBoardAuthReplyAdmin()));
//			boardPermission.put("upload", "y".equals(boardVo.getBoardAuthUploadAdmin()));
//			boardPermission.put("download", "y".equals(boardVo.getBoardAuthDownloadAdmin()));
//			boardPermission.put("comment", "y".equals(boardVo.getBoardAuthCommentAdmin()));
			
			return boardPermission;
		}
		
		// 사용자
		if(!memberDetailVo.getIsAdmin()) {
			boardPermission.put("list", "y".equals(boardVo.getBoardAuthListUser()));
			boardPermission.put("read", "y".equals(boardVo.getBoardAuthReadUser()));
			boardPermission.put("write", "y".equals(boardVo.getBoardAuthWriteUser()));
//			boardPermission.put("reply", "y".equals(boardVo.getBoardAuthReplyUser()));
//			boardPermission.put("upload", "y".equals(boardVo.getBoardAuthUploadUser()));
//			boardPermission.put("download", "y".equals(boardVo.getBoardAuthDownloadUser()));
//			boardPermission.put("comment", "y".equals(boardVo.getBoardAuthCommentUser()));
			
			return boardPermission;
		}
		
		
		return boardPermission;
	}
	
	/**
	 * 조회에서 권한 가져오기
	 * @param memberDetailVo
	 * @param boardVo
	 * @param postVo
	 * @return
	 */
	public CamelMap getBoardPermissionRead(MemberDetailVo memberDetailVo, BoardVo boardVo, PostVo postVo) {
		
		CamelMap boardPermission = new CamelMap();
		
		boardPermission.put("isBoardAdmin", false);
		
		boardPermission.put("list", false);
		boardPermission.put("read", false);
		boardPermission.put("write", false);
//		boardPermission.put("reply", false);
//		boardPermission.put("upload", false);
//		boardPermission.put("download", false);
//		boardPermission.put("comment", false);
		
		// 관리자
		if(memberDetailVo.getIsAdmin()) {
			boardPermission.put("list", "y".equals(boardVo.getBoardAuthListAdmin()));
			boardPermission.put("read", "y".equals(boardVo.getBoardAuthReadAdmin()));
			boardPermission.put("write", "y".equals(boardVo.getBoardAuthWriteAdmin()));
//			boardPermission.put("reply", "y".equals(boardVo.getBoardAuthReplyAdmin()));
//			boardPermission.put("upload", "y".equals(boardVo.getBoardAuthUploadAdmin()));
//			boardPermission.put("download", "y".equals(boardVo.getBoardAuthDownloadAdmin()));
//			boardPermission.put("comment", "y".equals(boardVo.getBoardAuthCommentAdmin()));
			
			return boardPermission;
		}
		
		// 사용자
		if(!memberDetailVo.getIsAdmin()) {
			boardPermission.put("list", "y".equals(boardVo.getBoardAuthListUser()));
			boardPermission.put("read", "y".equals(boardVo.getBoardAuthReadUser()));
			boardPermission.put("write", "y".equals(boardVo.getBoardAuthWriteUser()));
//			boardPermission.put("reply", "y".equals(boardVo.getBoardAuthReplyUser()));
//			boardPermission.put("upload", "y".equals(boardVo.getBoardAuthUploadUser()));
//			boardPermission.put("download", "y".equals(boardVo.getBoardAuthDownloadUser()));
//			boardPermission.put("comment", "y".equals(boardVo.getBoardAuthCommentUser()));
			
			return boardPermission;
		}
		
		
		return boardPermission;
	}
}
