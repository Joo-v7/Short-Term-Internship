package kepco.cms.member;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.member.auth.MemberDetailVo;
import kepco.cms.setting.SettingService;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.site.SiteLayout;
import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;

/**
 * 회원 관리
 */
@Controller
@RequestMapping(value = "/cms/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    EmailService emailService;

    @Autowired
    TemplateService templateService;

    @Autowired
    SettingService settingService;

    @Value("${global.file.base-path}")
    private Path baseFilePath;

    /**
     * 회원 목록
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "list")
    public String list(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "/cms/member/list";

        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 회원 정보 찾기
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "findIdPw")
    public String idpw_find(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "/cms/member/idPwCheck";

        if ("".equals(req.get("mod"))) {
            viewName = "/cms/member/check";
        } else if ("findIdPw".equals(req.get("mod"))) {
            viewName = "/cms/member/findIdPw";
        }

        model.addAttribute("type", "find");
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 회원가입
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "join")
    public String join(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "/cms/member/join";
        if ("".equals(req.get("mod"))) {
            viewName = "/cms/member/join";
        } else if ("in".equals(req.get("mod"))) {
            viewName = "/cms/member/in";
        } else if ("out".equals(req.get("mod"))) {
            viewName = "/cms/member/out";
        } else if ("check".equals(req.get("mod"))) {
            viewName = "/cms/member/check";
        } else if ("form".equals(req.get("mod"))) {
            viewName = "/cms/member/form";
        }

        model.addAttribute("type", "join");
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 비밀번호 확인
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "pwForm")
    public String pwForm(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "/cms/member/pwForm";

        if ("myPage".equals(req.get("mod"))) {
            model.addAttribute("mod", "myPage");
        } else if ("delete".equals(req.get("mod"))) {
            model.addAttribute("mod", "delete");
        }
        model.addAttribute("req", req);

        return viewName;
    }

    /**
     * 내 정보
     *
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @SiteLayout
    @RequestMapping(value = "myPage")
    public String myPage(@RequestEgovMap EgovMap req, Model model) throws Exception {

        String viewName = "/cms/member/myPage";

        if ("".equals(req.get("mod"))) {
            viewName = "/cms/member/myPage";
        } else if ("delForm".equals(req.get("mod"))) {
            viewName = "/cms/member/delForm";
        }

        MemberVo vo = null;

        if (!StrUtil.isEmpty(StrUtil.getSessionIdx())) {
            req.put("memberIdx", StrUtil.getSessionIdx());
            vo = memberService.select(req);
        }

        model.addAttribute("req", req);
        model.addAttribute("vo", vo);

        return viewName;
    }

    /**
     * 회원 목록 데이터
     *
     * @param req
     * @param model
     * @return
     * @throws
     */
    @RequestMapping(value = "listJson")
    @ResponseBody
    public JsonResponse listJson(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        List<MemberVo> list = memberService.selectAll(req);
        model.addAttribute("list", list);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 회원 저장
     *
     * @param req
     * @param model
     * @return
     * @throws
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public JsonResponse save(@RequestEgovMap EgovMap req, Model model) {

        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MemberVo memberVo = mapper.convertValue(req, MemberVo.class);
        if (StrUtil.isBlank(memberVo.getMemberNm())) {
            throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "성명은 필수입력 입니다.");
        }

        
        int result = 0;
        if(StrUtil.isEmpty(req.get("memberBirth"))) {
            String memberBirth = StrUtil.toStr(req.get("memberBirthY")) + "-" + StrUtil.getMonth(StrUtil.toInt(req.get("memberBirthM"))) + "-" + StrUtil.getMonth(StrUtil.toInt(req.get("memberBirthD")));
            memberVo.setMemberBirth(memberBirth);
        }

        if ("1".equals(memberVo.getMemberType2())) {
            memberVo.setMemberRole("ROLE_USER");
        } else {
            memberVo.setMemberRole("ROLE_ADMIN_LMS");
        }

        if (StrUtil.isEmpty(req.get("memberIdx"))) {

            //회원가입 아이디 보안금지어 검증
            int denyCnt = 0;
            denyCnt = settingService.secureVaildate(memberVo.getMemberId());
            if (denyCnt > 0) {
                throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, memberVo.getMemberNm() + " 해당 아이디로 계정을 생성 할 수 없습니다.(금지어)");
            }
            
            memberVo.setInsertIp(StrUtil.getClientIP());
            result = memberService.insert(memberVo);

            //신규 회원가입시 email 보내기 추가
            EmailVo emailVo = mapper.convertValue(req, EmailVo.class);
            EgovMap egovMap = new EgovMap();
            TemplateVo templateVo = new TemplateVo();
            egovMap.put("tempIdx", "4");
            templateVo = templateService.select(egovMap);
            emailVo.setTo(memberVo.getMemberEmail());
//			emailVo.setFrom("초실감훈련센터");//email 바꾸기 필요
            emailVo.setSubject(templateVo.getTempTitle());
            emailVo.setText(templateVo.getTempContent());
            emailVo.setUseHtml(true);
            emailVo.setUseTemplate(true);
            String memberType = "회원";
            if (memberVo.getMemberType2().equals("1")) {
                memberType = "학습자";
            } else if (memberVo.getMemberType2().equals("2")) {
                memberType = "교수자";
            }
            Map<String, Object> map = Map.of("memberName", memberVo.getMemberNm(), "now", new Date(), "memberType", memberType); //템플릿 내용 할당
            emailVo.setTemplateMap(map);
            emailService.send(emailVo);
        } else {
            memberVo.setUpdateIdx(StrUtil.getSessionIdx());
            memberVo.setUpdateIp(StrUtil.getClientIP());
            result = memberService.update(memberVo);
        }
        model.addAttribute("resultCnt", result);
        model.addAttribute(memberVo);

        return new JsonResponse.Builder().data(model).build();
    }

    /**
     * 회원 탈퇴
     *
     * @param req
     * @param model
     * @param auth
     * @return
     * @throws
     */
    @RequestMapping(value = "withdrawal")
    @ResponseBody
    public JsonResponse delete(Authentication auth, @RequestEgovMap EgovMap req, Model model) {

        int result = 0;
        int copyResult = 0;

        if (StrUtil.isEmpty(req.get("memberIdx"))) {
            throw new CmsCustomException(CmsStatusCode.BAD_REQUEST, "필수입력이 누락되었습니다.");
        }

        //회원 비밀번호, 입력된 비밀번호를 직접 비교
        MemberDetailVo user = (MemberDetailVo) auth.getPrincipal();
        req.put("deleteIdx", StrUtil.getSessionIdx());
        req.put("deleteIp", StrUtil.getClientIP());
        result = memberService.delete(req);
        if (result > 0) {
            copyResult = memberService.deleteCopy(req);
        }

        model.addAttribute("result", result);

        return new JsonResponse.Builder().data(model).build();
    }

    /**
     * 아이디 중복 확인
     *
     * @param req
     * @param model
     * @return
     * @throws
     */
    @RequestMapping(value = "checkId")
    @ResponseBody
    public JsonResponse checkId(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        MemberVo check = memberService.login(StrUtil.toStr(req.get("memberId")));
        int code = 1;
        String message = "사용가능한 아이디입니다.";
        if (check != null) {
            code = 2;
            message = "사용할 수 없는 아이디입니다.";
        }
        model.addAttribute("check", check);
        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 아이디 찾기
     *
     * @param req
     * @param model
     * @return
     * @throws
     */
    @RequestMapping(value = "findId")
    @ResponseBody
    public JsonResponse findId(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        String findId = memberService.findId(req);
        String message = "입력한 정보와 일치하는 회원정보가 없습니다. 관리자에게 문의하십시오.";
        if (!StrUtil.isEmpty(findId)) {
            message = "아이디는 " + findId + "입니다.";
        }
        model.addAttribute("message", message);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 비밀번호 찾기
     *
     * @param req
     * @param model
     * @return
     * @throws
     */
    @RequestMapping(value = "findPw")
    @ResponseBody
    public JsonResponse findPw(@RequestEgovMap EgovMap req, Model model) {

        model.addAttribute("req", req);

        req.put("memberPhone", req.get("memberPhone2"));
        //방법변경 필요
        String findIdx = memberService.findPw(req);
        String message = "입력한 정보와 일치하는 회원정보가 없습니다. 관리자에게 문의하십시오.";
        if (!StrUtil.isEmpty(findIdx)) {
            MemberVo vo = new MemberVo();
            vo.setMemberIdx(StrUtil.toInt(findIdx));
            vo.setMemberPw("1q2w3e4r!");
            memberService.changePw(vo);
            message = "임시 비밀번호는 " + "1q2w3e4r!입니다.\n보안을 위해 비밀번호를 변경해주세요.";
        }
        model.addAttribute("findIdx", findIdx);
        model.addAttribute("message", message);

        return new JsonResponse.Builder().model(model).build();
    }

    /**
     * 비밀번호 확인
     *
     * @param req
     * @param model
     * @return
     * @throws
     */
    @RequestMapping(value = "pwCheck")
    @ResponseBody
    public JsonResponse pwCheck(Authentication auth, @RequestEgovMap EgovMap req, Model model) throws Exception {

        model.addAttribute("req", req);

        int result = 0;

        //회원 비밀번호, 입력된 비밀번호를 직접 비교
        MemberDetailVo user = (MemberDetailVo) auth.getPrincipal();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches((CharSequence) req.get("memberPw"), user.getMemberPw())) {
            result = 1;
        } else {
            result = 0;
        }
        model.addAttribute("result", result);

        return new JsonResponse.Builder().model(model).build();
    }
}
