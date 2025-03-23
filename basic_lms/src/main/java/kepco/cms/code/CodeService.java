package kepco.cms.code;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.StrUtil;

@Service
public class CodeService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CodeMapper codeMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<CodeVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		return codeMapper.selectList(req);
	}

	public List<CodeVo> selectCodeList(EgovMap req) {
		return codeMapper.selectCodeList(req);
	}
	
	public List<CodeVo> selectCodeList(@NotNull String codeParent) {
		EgovMap req = new EgovMap();
		req.put("codeParent", codeParent);
		return codeMapper.selectCodeList(req);
	}
	
	public List<CodeVo> selectAll(EgovMap req) {
		return codeMapper.selectList(req);
	}
	
	public CodeVo select(EgovMap req) {
		return codeMapper.select(req);
	}
	
	@Transactional
	public int insert(CodeVo vo) {
		
		Set<ConstraintViolation<CodeVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<CodeVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		
		return codeMapper.insert(vo);
	}

	@Transactional
	public int insertCode(CodeVo vo) {
		
		Set<ConstraintViolation<CodeVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<CodeVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		return codeMapper.insertCode(vo);
	}
	
	@Transactional
	public int update(CodeVo vo) {
		
		Set<ConstraintViolation<CodeVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<CodeVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return codeMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		
		return codeMapper.delete(req);
	}
	
	public CodeTree selectTree(EgovMap req) {
		
		PageHelper.orderBy("code_depth, code_order");
		List<CodeVo> codeList =  codeMapper.selectList(req);
		CodeTree menuTree = new CodeTree(new CodeVo());
		for(CodeVo vo : codeList) {
			menuTree.addChildByData(vo);
		}
		
		return menuTree;
	}
}
