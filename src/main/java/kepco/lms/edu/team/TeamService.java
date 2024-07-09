package kepco.lms.edu.team;

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
public class TeamService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private TeamMapper teamMapper;
	
	
	public List<TeamVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return teamMapper.selectList(req);
	}

	public List<TeamVo> selectAll(EgovMap req) {
		return teamMapper.selectList(req);
	}
	
	public TeamVo select(EgovMap req) {
		return teamMapper.select(req);
	}
	
	@Transactional
	public int insert(TeamVo vo) {
		
		Set<ConstraintViolation<TeamVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<TeamVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return teamMapper.insert(vo);
	}
	
	@Transactional
	public int update(TeamVo vo) {
		
		Set<ConstraintViolation<TeamVo>> violations = validator.validate(vo);
		
		if(!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(ConstraintViolation<TeamVo> constraintViolation : violations) {
				sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		
		return teamMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return teamMapper.delete(req);
	}
	
	
}
