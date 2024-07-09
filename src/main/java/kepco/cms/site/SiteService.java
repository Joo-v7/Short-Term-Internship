package kepco.cms.site;

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
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.util.StrUtil;

@Service
public class SiteService {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private SiteMapper siteMapper;
	
	@Autowired
	SystemLogService systemLogService;
	
	public List<SiteVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		
		
		return siteMapper.selectList(req);
	}

	public List<SiteVo> selectAll(EgovMap req) {
		return siteMapper.selectList(req);
	}
	
	public SiteVo select(EgovMap req) {
		return siteMapper.select(req);
	}
	
	
	@Transactional
	public int insert(SiteVo vo) {
		
		Set<ConstraintViolation<SiteVo>> violations = validator.validate(vo);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<SiteVo> constraintViolation : violations) {
				String message = String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
				throw new ConstraintViolationException(message, violations);
			}
		}
		
		
		EgovMap req = new EgovMap();
		req.put("site", vo.getSite());
		SiteVo vo2 = siteMapper.select(req);
		if(vo2 != null) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "이미 등록된 사이트코드 입니다.");
		}
		
		if ("y".equalsIgnoreCase(vo.getDefaultSiteYn()) && siteMapper.defaultSiteCnt(null) > 0 ) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "대표사이트가 이미 존재합니다.");
		}
		
		return siteMapper.insert(vo);
	}
	
	@Transactional
	public int update(SiteVo vo) {
		
		Set<ConstraintViolation<SiteVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<SiteVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		// 기존 사이트코드를 수정치 않는 것을 전제
		
		if ("y".equalsIgnoreCase(vo.getDefaultSiteYn()) && siteMapper.defaultSiteCnt(vo.getSite()) > 0 ) {
			throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "대표사이트가 이미 존재합니다.");
		}
		
		return siteMapper.update(vo);
	}
	
	public int delete(EgovMap req) {
		
		return siteMapper.delete(req);
	}
}
