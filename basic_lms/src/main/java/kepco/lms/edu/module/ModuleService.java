package kepco.lms.edu.module;

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

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.StrUtil;

@Service
public class ModuleService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	
	public List<ModuleVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return moduleMapper.selectList(req);
	}
	
	public List<ModuleVo> selectPack(EgovMap req) {
		
		return moduleMapper.selectPack(req);
	}

	public List<ModuleVo> selectAll(EgovMap req) {
		return moduleMapper.selectList(req);
	}
	
	public ModuleVo select(EgovMap req) {
		return moduleMapper.select(req);
	}
	
	@Transactional
	public int insert(ModuleVo vo) {
		
		if (StrUtil.isEmpty(vo.getModuleCd())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "모듈코드는 필수입력 입니다.");
		}
		
		if (StrUtil.isEmpty(vo.getModuleTitle())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "모듈명은 필수입력 입니다.");
		}
		
		EgovMap myParam = new EgovMap();
		myParam.put("moduleCd", vo.getModuleCd());
		ModuleVo myVo = moduleMapper.select(myParam);
		if(myVo != null) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "이미 등록된 모듈코드 입니다.");
		}
		
		return moduleMapper.insert(vo);
	}
	
	@Transactional
	public int update(ModuleVo vo) {
		
		if (StrUtil.isEmpty(vo.getModuleCd())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "모듈코드는 필수입력 입니다.");
		}
		
		if (StrUtil.isEmpty(vo.getModuleTitle())) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "모듈명은 필수입력 입니다.");
		}
		
		EgovMap myParam = new EgovMap();
		myParam.put("moduleCd", vo.getModuleCd());
		ModuleVo myVo = moduleMapper.select(myParam);
		if(myVo != null && vo.getModuleIdx() != myVo.getModuleIdx()) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "이미 등록된 모듈코드 입니다.");
		}
		
		return moduleMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return moduleMapper.delete(req);
	}
	
	@Transactional
	public int fileUpdate(ModuleVo vo) {
		return moduleMapper.fileUpdate(vo);
	}
	
}
