package kepco.cms.sec.roleHierarchy;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.StrUtil;

/** 역할 */
@Service
public class RoleHierarchyService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private RoleHierarchyMapper roleMapper;
	
	@Transactional
    public String findAllHierarchy(EgovMap req) {

        List<RoleHierarchyVo> roleHierarchies = roleMapper.selectList(req);

        Iterator<RoleHierarchyVo> iterator = roleHierarchies.iterator();
        StringBuilder concatRoles = new StringBuilder();
        while (iterator.hasNext()) {
            RoleHierarchyVo roleHierarchy = iterator.next();
            if (!StrUtil.isEmpty(roleHierarchy.getParentRoleCd())) {
                concatRoles.append(roleHierarchy.getParentRoleCd());
                concatRoles.append(" > ");
                concatRoles.append(roleHierarchy.getRoleCd());
                concatRoles.append("\n");
            }
        }
        return concatRoles.toString();
    }
}