package kepco.cms.board.post.comment;

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

import kepco.util.StrUtil;

@Service
public class CommentService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private CommentMapper commentMapper;
	
	public List<CommentVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return commentMapper.selectList(req);
	}
	public List<CommentVo> selectPostComment(EgovMap req) {
		return commentMapper.selectPostComment(req);
	}

	public List<CommentVo> selectAll(EgovMap req) {
		return commentMapper.selectList(req);
	}
	
	public CommentVo select(EgovMap req) {
		return commentMapper.select(req);
	}

	@Transactional
	public int insert(CommentVo vo) {
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<CommentVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<CommentVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return commentMapper.insert(vo);
	}
	
	@Transactional
	public int update(CommentVo vo) {

		Set<ConstraintViolation<CommentVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<CommentVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return commentMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return commentMapper.delete(req);
	}
	
	@Transactional
	public int commentadd(EgovMap req) {
		return commentMapper.commentadd(req);
	}
	
	@Transactional
	public int commentdelete(EgovMap req) {
		return commentMapper.commentdelete(req);
	}
	
	

}
