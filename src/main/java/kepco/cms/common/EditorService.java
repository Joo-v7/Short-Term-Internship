package kepco.cms.common;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kepco.util.StrUtil;

@Service
public class EditorService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private EditorMapper editorMapper;
	
	@Transactional
	public int insert(EditorVo vo) {
		return editorMapper.insert(vo);
	}

}
