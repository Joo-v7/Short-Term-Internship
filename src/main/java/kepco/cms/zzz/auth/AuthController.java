package kepco.cms.zzz.auth;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import kepco.common.exception.CmsCustomException;
import kepco.common.exception.CmsStatusCode;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestCamelMap;
import kepco.util.CamelMap;

@Controller
@RequestMapping(value = "cms/zzz/auth")
public class AuthController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = {"", "index"})
	public String index(@RequestCamelMap CamelMap req, Model model) {
		
		
		return "cms/zzz/auth/index";

	}
	
	@RequestMapping(value = "isAuthenticated")
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public JsonResponse isAuthenticated(@RequestCamelMap CamelMap req, Model model) {
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	@RequestMapping(value = "hasRoleAdmin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	public JsonResponse hasRoleAdmin(@RequestCamelMap CamelMap req, Model model) {
		
		return new JsonResponse.Builder().data(model).build();
	}
	
	@RequestMapping(value = "hasPermission")
	// FullyQualifiedAnnotationBeanNameGenerator 를 사용하는 상황에서 .(dot)을 어떻게 사용할 수 있는지 모르겠다.
	@PreAuthorize("@myPermissionChecker.myCmd(#req)")
	@ResponseBody
	public JsonResponse hasPermission(@RequestCamelMap CamelMap req, Model model) {
		// 기본적으로 그냥 사용하는 건 맘에 안드는데?
		// 굳이 Evaluator 를 만들어서 해야하나? 만들어야 할 게 한두개도 아니고...
		
		// 무시하고 쓰기엔 굳이 이렇게 사용해야 할 필요가 있나? 그냥 처리하면 되는거 아닌가?
		
		return new JsonResponse.Builder().data(model).build();
	}
}
