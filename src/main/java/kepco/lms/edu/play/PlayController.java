package kepco.lms.edu.play;

import java.nio.file.Path;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.member.MemberService;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
/**
 * 실감훈련 수행 관리
 */
@Controller
@RequestMapping(value = "${global.admin-path}/lms/edu/play")
public class PlayController {

	@Autowired
	PlayService playService;
	
	@Autowired
	MemberService memberService;

	private final static String FILE_PATH = "edu/play/";

	@Value("${global.file.base-path}")
	private Path basePath;

	@Autowired
	LocalFileService localFileService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 수행 목록
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SiteLayout
	@RequestMapping(value = "list")
	public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

		String viewName = "admin/lms/edu/play/list";

		model.addAttribute("req", req);

		return viewName;
	}

	/**
	 * 수행 목록 데이터
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listJson")
	@ResponseBody
	public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

		model.addAttribute("req", req);
		
		List<PlayVo> list = playService.selectList(req);
		model.addAttribute("list", list);

		return new JsonResponse.Builder().model(model).build();
	}


//	@RequestMapping(value = "download")
//	@ResponseBody
//	protected ResponseEntity<Resource> fileDownload(@RequestEgovMap EgovMap req, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		playVo vo = playService.select(req);
//		String fileName = vo.getModuleFileName();
//		String fileOrigin = vo.getModuleFileOrigin();
//
//		if ("".equals(fileOrigin)) {
//		} else {
//			return localFileService.download(FILE_PATH + fileName, fileOrigin);
//		}
//		return null;
//	}

}