package kepco.cms.stat;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.site.SiteService;
import kepco.cms.site.SiteVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 통계 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/cms/stat")
public class AdminStatController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private StatService statService;
	
	@Autowired
	SiteService siteService;
	
	/**
	 * 통계 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/stat/list";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	/**
	 * 통계 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {
		
		model.addAttribute("req", req);
		
		String[] systemTypes = {"cms", "lms", "ras"};
		StatVo[] statVoArray = new StatVo[3];

		for (int i = 0; i < statVoArray.length; i++) {
		    String systemType = "admin/"+systemTypes[i];
		    req.put("scType", systemType);
		    statVoArray[i] = statService.visitCount(req);
		    statVoArray[i].setSystemType(systemTypes[i]);
		}
		model.addAttribute("list", statVoArray);
		
		return new JsonResponse.Builder().model(model).build();
	}
	/**
	 * 통계 차트
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "chart")
	public String chart(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		if(!StrUtil.isEmpty(req.get("site"))) {
			req.put("site", req.get("site"));
		}
		
		model.addAttribute("req", req);
		
		List<SiteVo> vo = siteService.selectAll(req);
		
		model.addAttribute("siteList", vo);
		
		return "admin/cms/stat/chart";
	}
	/**
	 * 통계 폼
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form")
	public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/stat/form";
		
		StatVo statVo = null;
		
		if(!StrUtil.isEmpty(req.get("statIdx"))) {
			statVo = statService.select(req);
		}
		
		model.addAttribute("req", req);
		model.addAttribute("vo", statVo);
		
		return viewName;
	}
	/**
	 * 통계 저장
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		StatVo statVo = mapper.convertValue(req, StatVo.class);
		
		int result = 0 ;
		
		if(StrUtil.isEmpty(req.get("statIdx"))) {
			result = statService.insert(statVo);
		}
		
		model.addAttribute("result", result);
		model.addAttribute(statVo);
		
		return new JsonResponse.Builder().data(model).build();
	}
	/**
	 * 통계 차트 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chartJson")
	@ResponseBody
	public JsonResponse chartJson(@RequestEgovMap EgovMap req, Model model) {
				
		model.addAttribute("req", req);
		
		List<StatVo> list = statService.selectAll(req);
		List<StatVo> date = statService.selectDateStat(req);
		List<StatVo> hour = statService.selectHourStat(req);
		List<StatVo> device = statService.selectDeviceStat(req);
		List<StatVo> browser = statService.selectBrowserStat(req);
		List<StatVo> os = statService.selectOsStat(req);
		
		model.addAttribute("list", list);
		model.addAttribute("date", date);
		model.addAttribute("hour", hour);
		model.addAttribute("device", device);
		model.addAttribute("browser", browser);
		model.addAttribute("os", os);
		
		return new JsonResponse.Builder().model(model).build();
	}
	
	@SiteLayout
	@RequestMapping(value = "cms/log")
	public String cmsLog(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/stat/cms/log";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "lms/log")
	public String lmsLog(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/stat/lms/log";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
	
	@SiteLayout
	@RequestMapping(value = "ras/log")
	public String rasLog(@RequestEgovMap EgovMap req, Model model) throws Exception {
		
		String viewName = "admin/cms/stat/ras/log";
		
		model.addAttribute("req", req);
		
		return viewName;
	}
}