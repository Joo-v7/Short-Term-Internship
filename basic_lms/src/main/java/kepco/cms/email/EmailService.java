package kepco.cms.email;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import kepco.util.StrUtil;

@Service
public class EmailService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	@Qualifier("htmlStringTemplateEngine")
	private TemplateEngine htmlStringTemplateEngine;
	
	@Value("${spring.mail.from-noreply:noreply@localhost}")
	private String fromNoreply;

	public boolean send(EmailVo emailVo) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
			
			messageHelper.setTo(emailVo.getTo());
			if(StrUtil.isEmpty(emailVo.getFrom())) {
				messageHelper.setFrom(fromNoreply);
			}
			else {
				messageHelper.setFrom(emailVo.getFrom());
			}
			messageHelper.setSubject(emailVo.getSubject());
			if(emailVo.isUseTemplate()) {
				final Context ctx = new Context();
				if(emailVo.getTemplateMap() != null && emailVo.getTemplateMap().size() > 0) {
					emailVo.getTemplateMap().forEach((k, v) -> {
						ctx.setVariable(k, v);
					});
				}
				emailVo.setText(htmlStringTemplateEngine.process(emailVo.getText(), ctx));
			}
			
			messageHelper.setText(emailVo.getText(), emailVo.isUseHtml());
			
			if(emailVo.getAttachMap() != null && emailVo.getAttachMap().size() > 0) {
				for (Map.Entry<String,Object> entry : emailVo.getAttachMap().entrySet()) {
					// TODO: 파일 검증
					messageHelper.addAttachment(entry.getKey(), (File)entry.getValue());
				}
			}
			
			if(emailVo.getInlineMap() != null && emailVo.getInlineMap().size() > 0) {
				if(!emailVo.isUseHtml()) {
					log.error("메일생성 실패 : 인라인은 HTML에서만 가능합니다.");
					return;
				}
				
				Pattern pattern = Pattern.compile("<img .*src=[\"']cid\\:([^\\s]+)[\"']");
				Matcher matcher = pattern.matcher(emailVo.getText());
				while(matcher.find()) {
					if(!emailVo.getInlineMap().containsKey(matcher.group(1))) {
						log.error("메일생성 실패 : 인라인 항목 `" + matcher.group(1) + "`이(가) 첨부되지 않았습니다.");
						return;
					}
				}
				
				for (Map.Entry<String,Object> entry : emailVo.getInlineMap().entrySet()) {
					// TODO: 파일 검증
					messageHelper.addInline(entry.getKey(), (File)entry.getValue());
				}
			}
		};
		
		try {
			javaMailSender.send(messagePreparator);
			return true;
		} catch (MailException e) {
			log.error("메일전송 실패 : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
