package kepco.common.config;

import java.util.List;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kepco.cms.visitCount.VisitCountService;
import kepco.common.site.LogInterceptor;
import kepco.common.site.SiteLayoutInterceptor;
import kepco.common.site.VisitCounter;
import kepco.common.web.CamelMapArgumentResolver;
import kepco.common.web.EgovMapArgumentResolver;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// 컨트롤러에서 EgovMap을 파라미터로 받을 목적
		argumentResolvers.add(new EgovMapArgumentResolver());
		// 컨트롤러에서 CamelMap을 파라미터로 받을 목적
		argumentResolvers.add(new CamelMapArgumentResolver());
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// 헤더,푸터에 대한 추가 데이터가 필요한 경우에 대응할 목적
		registry.addInterceptor(logInterceptor());
		registry.addInterceptor(siteLayoutInterceptor());
		
		// PageHelper 자동 적용
//		registry.addInterceptor(pageHelperInterceptor());
	}
	
	@Bean
	public SiteLayoutInterceptor siteLayoutInterceptor() {
		return new SiteLayoutInterceptor();
	}
	
	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}
	
	@Bean
	public ServletListenerRegistrationBean<VisitCounter> visitCounter() {
		ServletListenerRegistrationBean<VisitCounter> listenerRegBean = new ServletListenerRegistrationBean<>();
		listenerRegBean.setListener(new VisitCounter());
		return listenerRegBean;
	}
	
//	@Bean
//	public VisitCountService visitCountService() {
//		return new VisitCountService();
//	}
	
//	@Bean
//	public MessageSource messageSource() {
//		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.setBasename("classpath:/messages/message");
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
//
//	@Bean
//	public Validator getValidator() {
//		LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
//		localValidatorFactory.setValidationMessageSource(messageSource());
//		return localValidatorFactory;
//	}
	
	
}
