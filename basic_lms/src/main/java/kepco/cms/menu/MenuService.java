package kepco.cms.menu;

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

/** 메뉴트리구조 */
@Service
public class MenuService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<MenuVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return menuMapper.selectList(req);
	}
	
	public List<MenuVo> selectAll(EgovMap req) {
		return menuMapper.selectList(req);
	}
	
	public MenuVo select(EgovMap req) {
		return menuMapper.select(req);
	}
	
	@Transactional
	public int insert(MenuVo vo) {
		
		Set<ConstraintViolation<MenuVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<MenuVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		
		return menuMapper.insert(vo);
	}
	
	@Transactional
	public int update(MenuVo vo) {

		Set<ConstraintViolation<MenuVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<MenuVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return menuMapper.update(vo);
	}
	
	public int selectCnt(EgovMap req) {
		return menuMapper.selectCnt(req);
	}
	
	public int delete(EgovMap req) {
		
		return menuMapper.delete(req);
	}
	
	public MenuTree selectTree(EgovMap req) {
		
		PageHelper.orderBy("menu_depth, menu_order");
		List<MenuVo> menuList =  menuMapper.selectList(req);
		MenuTree menuTree = new MenuTree(new MenuVo());
		for(MenuVo vo : menuList) {
			menuTree.addChildByData(vo);
		}
		
		return menuTree;
	}
}