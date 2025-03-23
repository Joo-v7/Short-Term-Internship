package kepco.cms.visitCount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class VisitCountService {
	
	@Autowired
	private VisitCountMapper visitCountMapper;
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public long insert(VisitCountVo vo) {
	
		return visitCountMapper.insert(vo);
	}
	

}
