package kepco.lms.edu.stat;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kepco.cms.member.MemberService;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.lms.edu.EduService;
import kepco.lms.edu.EduVo;
import kepco.lms.edu.detail.DetailService;
import kepco.lms.edu.detail.DetailVo;
import kepco.lms.edu.process.EduProcessService;
import kepco.util.StrUtil;

/**
 * 통계 관리
 */
@Controller
@RequestMapping(value = "lms/edu/stat")
public class EdustatController {

    @Autowired
    EdustatService statService;

    @Autowired
    MemberService memberService;

    @Autowired
    DetailService detailService;

    @Autowired
    EduService eduService;

    @Autowired
    EduProcessService eduProcessService;

    //TODO 실감훈련 통계 개발 필요

    /**
     * 통계 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "list")
    public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/list";

//        PlayVo playVo = playService.select(req);
        DetailVo detailVo = detailService.select(req);
        req.put("eduIdx", detailVo.getEduIdx());
        EduVo eduVo = eduService.select(req);

        model.addAttribute("detailVo", detailVo);
        model.addAttribute("eduVo", eduVo);

        return viewName;
    }


    /**
     * 학습자 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "memberList")
    public String memberList(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/memberList";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 통계 폼
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "form")
    public String form(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "lms/edu/stat/form";

        EgovMap vo = null;

        if (!StrUtil.isEmpty(req.get("statIdx"))) {
            vo = statService.select(req);
        }

        model.addAttribute("req", req);
        model.addAttribute("vo", vo);

        return viewName;
    }

    /**
     * 목록 데이터
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "listJson")
    @ResponseBody
    public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        List<EdustatVo> list = statService.selectAll(req);
        model.addAttribute("list", list);

        return new JsonResponse.Builder().model(model).build();
    }

}