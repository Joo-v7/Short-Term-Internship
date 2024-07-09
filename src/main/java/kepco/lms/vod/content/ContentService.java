package kepco.lms.vod.content;

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

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class ContentService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ContentMapper contentMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<ContentVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return contentMapper.selectList(req);
	}

	public List<ContentVo> selectAll(EgovMap req) {
		return contentMapper.selectList(req);
	}
	
	public ContentVo select(EgovMap req) {
		return contentMapper.select(req);
	}
	
	@Transactional
	public int insert(ContentVo vo) {
		
		Set<ConstraintViolation<ContentVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<ContentVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return contentMapper.insert(vo);
	}
	
	@Transactional
	public int update(ContentVo vo) {
		
		Set<ConstraintViolation<ContentVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<ContentVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return contentMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return contentMapper.delete(req);
	}

	@Transactional
	public int fileUpdate(ContentVo vo) {
		return contentMapper.fileUpdate(vo);
	}
	
}
