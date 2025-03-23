package kepco.cms.stat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;


@Mapper
public interface StatMapper {

    public List<StatVo> selectList(EgovMap req);

    public List<StatVo> selectDate(EgovMap req);

    public List<StatVo> selectHour(EgovMap req);

    public List<StatVo> selectDeviceStat(EgovMap req);

    public StatVo selectDevice(EgovMap req);

    public List<StatVo> selectBrowser(EgovMap req);

    public List<StatVo> selectOs(EgovMap req);

    public StatVo select(EgovMap req);

    public StatVo visitCount(EgovMap req);

    public int insert(StatVo vo);

    public int update(StatVo vo);

    public int delete(EgovMap req);

}