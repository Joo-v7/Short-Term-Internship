package kepco.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class EmailConfig {

//	@Bean
//	public JavaMailSenderImpl javaMailSender() {
//		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//		javaMailSender.setProtocol("smtp");
//		javaMailSender.setHost("127.0.0.1");
//		javaMailSender.setPort(25);
//
//		return javaMailSender;
//	}
	
	@Bean("htmlStringTemplateEngine")
	public TemplateEngine HtmlStringTemplateEngine() {
		final StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(false);
		
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(templateResolver);
		return templateEngine;
	}
}
