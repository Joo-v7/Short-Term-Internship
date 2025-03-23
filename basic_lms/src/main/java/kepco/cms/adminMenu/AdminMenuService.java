package kepco.cms.adminMenu;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.cms.systemLog.SystemLogService;
import kepco.util.CamelMap;
import kepco.util.StrUtil;

@Service
public class AdminMenuService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private AdminMenuMapper adminMenuMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<AdminMenuVo> selectList(CamelMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return adminMenuMapper.selectList(req);
	}

	public List<AdminMenuVo> selectAll(CamelMap req) {
		return adminMenuMapper.selectList(req);
	}
	
	public AdminMenuVo select(CamelMap req) {
		return adminMenuMapper.select(req);
	}
	
	@Transactional
	public int insert(AdminMenuVo vo) {
		
		Set<ConstraintViolation<AdminMenuVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<AdminMenuVo> constraintViolation : violations) {
				// TODO: 오류가 N개일 경우에 대한 메시지 처리 변경
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return adminMenuMapper.insert(vo);
	}
	
	@Transactional
	public int update(AdminMenuVo vo) {
		Set<ConstraintViolation<AdminMenuVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<AdminMenuVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return adminMenuMapper.update(vo);
	}
	
	@Transactional
	public int delete(CamelMap req) {
		
		return adminMenuMapper.delete(req);
	}
	
	
	public AdminMenuTree selectTree(CamelMap req) {
		
		PageHelper.orderBy("menu_depth, menu_order");
		List<AdminMenuVo> menuList =  adminMenuMapper.selectList(req);
		AdminMenuTree menuTree = new AdminMenuTree(new AdminMenuVo());
		for(AdminMenuVo vo : menuList) {
			menuTree.addChildByData(vo);
		}
		
		return menuTree;
	}
}
