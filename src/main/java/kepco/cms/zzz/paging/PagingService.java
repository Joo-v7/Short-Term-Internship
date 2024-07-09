package kepco.cms.zzz.paging;

import java.util.List;

import javax.validation.Validator;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kepco.cms.zzz.crud.CrudMapper;
import kepco.cms.zzz.crud.CrudVo;
import kepco.util.StrUtil;


@Service
public class PagingService extends EgovAbstractServiceImpl {
	
	@Autowired
	private CrudMapper crudMapper;
	
	public PageInfo<CrudVo> selectListPageInfo(EgovMap req) {
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		// https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/en/HowToUse.md
		PageHelper.startPage(pageNum, pageSize);
		return PageInfo.of(crudMapper.selectList(req));
	}
	
	public List<CrudVo> selectList(EgovMap req) {
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		
		PageHelper.startPage(pageNum, pageSize);
		return crudMapper.selectList(req);
	}
}
