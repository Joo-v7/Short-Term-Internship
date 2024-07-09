package kepco.lms.vod;

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
public class VodService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private VodMapper vodMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<VodVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return vodMapper.selectList(req);
	}

	public List<VodVo> selectAll(EgovMap req) {
		return vodMapper.selectList(req);
	}
	
	public VodVo select(EgovMap req) {
		return vodMapper.select(req);
	}
	
	@Transactional
	public int insert(VodVo vo) {
		
		Set<ConstraintViolation<VodVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<VodVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return vodMapper.insert(vo);
	}
	
	@Transactional
	public int update(VodVo vo) {
		
		Set<ConstraintViolation<VodVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<VodVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return vodMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return vodMapper.delete(req);
	}
	
	public VodVo fileSelect(EgovMap req) {
		return vodMapper.fileSelect(req);
	}
	
	public Object myPrintSelect(EgovMap req) {
		return vodMapper.myPrintSelect(req);
	}

	public List<VodVo> myListSelect(EgovMap req) {
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return vodMapper.myListSelect(req);
	}
	
	public List<VodVo> myApplyListSelect(EgovMap req) {
		return vodMapper.myApplyListSelect(req);
	}
	
	@Transactional
	public int thumbnailUpdate(VodVo vo) {
		
		return vodMapper.thumbnailUpdate(vo);
	}
	
	@Transactional
	public int fileUpdate(VodVo vo) {
		
		return vodMapper.fileUpdate(vo);
	}
}
