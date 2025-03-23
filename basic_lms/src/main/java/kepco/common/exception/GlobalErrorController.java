package kepco.common.exception;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kepco.common.json.JsonResponse;

/**
 * 
 * @see org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController extends AbstractErrorController {

	public GlobalErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Value("${global.error.view}")
	String globalErrorView;
	
	@Value("${global.error.user-view}")
	String globalErrorUserView;
	
	@Value("${global.admin-path}")
	String globalAdminPath;

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView customHtmlError(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		
		Map<String, Object> model = getMyErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML));
		response.setStatus(status.value());
		String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		if(requestUri.startsWith("/"+globalAdminPath)) {
			return new ModelAndView(globalErrorView, model);
		}
		else {
			return new ModelAndView(globalErrorUserView, model);
		}
	}

	@RequestMapping
	public ResponseEntity<JsonResponse> customJsonError(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		
		if (status == HttpStatus.NO_CONTENT) {
			JsonResponse jsonResponse = new JsonResponse.Builder().data("").status(status.value()).build();
			new ResponseEntity<>(jsonResponse, status);
		}
		Map<String, Object> model = getMyErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
		JsonResponse jsonResponse = new JsonResponse.Builder().data(model).status(status.value()).build();
		
		Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		if(exception instanceof CmsCustomException) {
			status = HttpStatus.valueOf(((CmsCustomException) exception).getCmsStatusCode().value());
			jsonResponse.setStatus(((CmsCustomException) exception).getCmsStatusCode().value());
			jsonResponse.setCode(((CmsCustomException) exception).code());
			jsonResponse.setMessage(((CmsCustomException) exception).getMessage());
		}
		
		if(status != HttpStatus.OK && model.get("message") != null && model.get("message") instanceof String) {
			jsonResponse.setMessage(model.get("message").toString());
		}
		return new ResponseEntity<>(jsonResponse, status);
	}

	protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
		
		ErrorAttributeOptions options = ErrorAttributeOptions.of(
				ErrorAttributeOptions.Include.EXCEPTION,
				ErrorAttributeOptions.Include.STACK_TRACE,
				ErrorAttributeOptions.Include.MESSAGE,
				ErrorAttributeOptions.Include.BINDING_ERRORS);
		return options;
	}
	
	protected Map<String, Object> getMyErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {
		
		// TODO: 커스텀으로 아예 따로 만들어서 에러 가져오기
		Map<String, Object> model = getErrorAttributes(request, options);
		
		Enumeration<String> e = request.getAttributeNames();
		while( e.hasMoreElements()) {
			String name = (String) e.nextElement();
			if(name.startsWith("cms.")) {
				model.put(name, request.getAttribute(name));
			}
		}
		
		return model;
	}
	
	@RequestMapping(value = "403")
	public String error403(HttpServletRequest request, HttpServletResponse response, Model model) {

		int statusCode = 403;
		
		model.addAttribute("status", statusCode);
		return "/global/errorUser";
	
	}
}
