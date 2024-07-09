package kepco.cms.board.post;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import kepco.cms.board.BoardVo;
import kepco.common.config.EncryptionService;
import kepco.util.StrUtil;

@Service
public class PostService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private PostMapper postMapper;
	
	public List<PostVo> selectList(EgovMap req) {
		
		if(!StrUtil.isBlank(req.get("orderBy"))) {
			String orderBy = StrUtil.noNull(req.get("orderBy"));
			PageHelper.orderBy(orderBy);
		}
		PageHelper.startPage(StrUtil.toInt(req.get("pageNum"), 1), StrUtil.toInt(req.get("pageSize"), 15));
		return postMapper.selectList(req);
	}

	public List<PostVo> selectAll(EgovMap req) {
		return postMapper.selectList(req);
	}
	
	public PostVo select(EgovMap req) {
		PostVo vo = postMapper.select(req);
		if( vo == null ) {
			return vo;
		}
		
		String myVal;
		if(vo.getPostTel() != null) {
			myVal = encryptionService.decryptData(vo.getPostTel());
			vo.setPostTel(myVal);
		}
		if(vo.getPostAddr() != null) {
			myVal = encryptionService.decryptData(vo.getPostAddr());
			vo.setPostAddr(myVal);
		}
		if(vo.getPostAddr2() != null) {
			myVal = encryptionService.decryptData(vo.getPostAddr2());
			vo.setPostAddr2(myVal);
		}
		return vo;
	}
	
	public List<PostVo> selectComment(EgovMap req) {
		return postMapper.selectComment(req);
	}
	
	@Deprecated
	public PostVo selectTotal(EgovMap req) {
		return postMapper.selectTotal(req);
	}

	@Transactional
	public int insert(PostVo vo) {
		Set<ConstraintViolation<PostVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<PostVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return postMapper.insert(vo);
	}
	
	@Transactional
	public int update(PostVo vo) {
		Set<ConstraintViolation<PostVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<PostVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return postMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return postMapper.delete(req);
	}
	
	@Transactional
	public int deleteFile(EgovMap req) {
		return postMapper.deleteFile(req);
	}
	
	@Transactional
	@Deprecated
	public int cntadd(EgovMap req) {
		return postMapper.cntadd(req);
	}
	
	/** 조회수 증가 */
	public int updatePostHit(int postIdx) {
		return postMapper.updatePostHit(postIdx);
	}
	
	/**
	 * 쓰기
	 * @param vo
	 * @return
	 */
	@Transactional
	public int insertWrite(PostVo vo) {
		int postGroupId = postMapper.selectPostGroupId(vo.getBoardIdx());
		vo.setPostGroupId(postGroupId + 1);
		
		String myVal;
		if(vo.getPostTel() != null) {
			myVal = encryptionService.encryptData(vo.getPostTel());
			vo.setPostTel(myVal);
		}
		if(vo.getPostAddr() != null) {
			myVal = encryptionService.encryptData(vo.getPostAddr());
			vo.setPostAddr(myVal);
		}
		if(vo.getPostAddr2() != null) {
			myVal = encryptionService.encryptData(vo.getPostAddr2());
			vo.setPostAddr2(myVal);
		}
		int result = postMapper.insert(vo);
		
		return result;
	}
	
	/**
	 * 답변
	 * @param vo
	 * @return
	 */
	@Transactional
	public int insertReply(PostVo vo) {
		postMapper.updatePosGroupOrder(vo.getBoardIdx(), vo.getPostGroupId(), vo.getPostGroupOrder());
		
		String myVal;
		if(vo.getPostTel() != null) {
			myVal = encryptionService.encryptData(vo.getPostTel());
			vo.setPostTel(myVal);
		}
		if(vo.getPostAddr() != null) {
			myVal = encryptionService.encryptData(vo.getPostAddr());
			vo.setPostAddr(myVal);
		}
		if(vo.getPostAddr2() != null) {
			myVal = encryptionService.encryptData(vo.getPostAddr2());
			vo.setPostAddr2(myVal);
		}
		
		int result = postMapper.insert(vo);
		
		return result;
	}
	
	/**
	 * 수정
	 * @param vo
	 * @return
	 */
	@Transactional
	public int updateModify(PostVo vo) {
		String myVal;
		if(vo.getPostTel() != null) {
			myVal = encryptionService.encryptData(vo.getPostTel());
			vo.setPostTel(myVal);
		}
		if(vo.getPostAddr() != null) {
			myVal = encryptionService.encryptData(vo.getPostAddr());
			vo.setPostAddr(myVal);
		}
		if(vo.getPostAddr2() != null) {
			myVal = encryptionService.encryptData(vo.getPostAddr2());
			vo.setPostAddr2(myVal);
		}
		
		int result = postMapper.updateModify(vo);
		
		return result;
	}
	
	/** 
	 * 답변 갯수 
	 * @param postIdx
	 * @return
	 */
	public int selectChildCount(int postIdx) {
		return postMapper.selectChildCount(postIdx);
	}
	
	/**
	 * 댓글 갯수
	 * @param postIdx
	 * @return
	 */
	public int selectCommentCount(int postIdx) {
		return postMapper.selectCommentCount(postIdx);
	}
	
	/**
	 * 게시물의 첨부파일(일반) 갯수 갱신
	 * @param postIdx
	 */
	public int updatePostFile(int postIdx) {
		return postMapper.updatePostFile(postIdx);
		
	}
	
	/**
	 * 게시물의 첨부파일(사진) 갯수 갱신
	 * @param postIdx
	 */
	public int updatePostImage(int postIdx) {
		return postMapper.updatePostImage(postIdx);
		
	}
	
	/**
	 * 게시물의 댓글 수 갱신
	 * @param postIdx
	 * @return
	 */
	public int updatePostCommentCount(int postIdx) {
		return postMapper.updatePostCommentCount(postIdx);
		
	}
}
