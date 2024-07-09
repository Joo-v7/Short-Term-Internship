package kepco.cms.zzz;

import java.util.Date;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.cms.email.EmailService;
import kepco.cms.email.EmailVo;
import kepco.cms.template.TemplateService;
import kepco.cms.template.TemplateVo;
import kepco.common.file.LocalFileService;
import kepco.common.json.JsonResponse;
import kepco.common.web.RequestCamelMap;
import kepco.util.CamelMap;

@Controller
@RequestMapping(value = "/cms/email")
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TemplateService templateService;
	
//	@Autowired
//	LocalFileService localFileService;
/*
	@RequestMapping(value = "send")
	@ResponseBody
	public JsonResponse send(@RequestCamelMap CamelMap req, Model model) throws Exception {
		
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		EmailVo emailVo = mapper.convertValue(req, EmailVo.class);
		
		emailVo.setTo("kmtsky@uokdc.com");
		emailVo.setSubject("제목");
		emailVo.setText("내용");
		emailVo.setFrom("kmtsky@uokdc.com");
		emailVo.setUseHtml(false);
		
		boolean result;
		result = emailService.send(emailVo);
		
		EgovMap egovMap = new EgovMap();
		egovMap.put("tempIdx", "4");
		TemplateVo templateVo = templateService.select(egovMap);
		emailVo.setText(templateVo.getTempContent());
		emailVo.setUseHtml(true);
		emailVo.setUseTemplate(true);
		Map<String, Object> map = Map.of("memberName","홍길동", "now", new Date()); 
		emailVo.setTemplateMap(map);
		result = emailService.send(emailVo);
		
		model.addAttribute("result",result);
		
		return new JsonResponse.Builder().model(model).build();
		
	}
	*/
}