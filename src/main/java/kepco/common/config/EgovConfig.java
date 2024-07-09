package kepco.common.config;

import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.egovframe.rte.psl.dataaccess.mapper.MapperConfigurer;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import kepco.common.exception.GlobalErrorController;

@Configuration
@MapperScan(
		basePackages = {"kepco.lms", "kepco.ras", "kepco.api", "kepco.cms", "kepco.common", "kepco.util"}, 
		annotationClass = org.apache.ibatis.annotations.Mapper.class
)
public class EgovConfig {

	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
		rrbms.setBasenames(new String[]{"classpath:egovframework/message/message-common"});
		rrbms.setCacheSeconds(60);
		rrbms.setUseCodeAsDefaultMessage(false);
		return rrbms;
	}

	@Bean
	public LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandlerService){
		LeaveaTrace leaveaTrace = new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[]{traceHandlerService});
		return leaveaTrace;
	}

	@Bean
	public DefaultTraceHandleManager traceHandlerService(AntPathMatcher antPathMater, DefaultTraceHandler defaultTraceHandler){
		DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMater);
		defaultTraceHandleManager.setPatterns(new String[]{"*"});
		defaultTraceHandleManager.setHandlers(new TraceHandler[]{defaultTraceHandler});
		return defaultTraceHandleManager;
	}
	
	@Bean
	public AntPathMatcher antPathMater(){
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher;
	}
	
	@Bean
	public DefaultTraceHandler defaultTraceHandler(){
		DefaultTraceHandler defaultTraceHandler = new DefaultTraceHandler();
		return defaultTraceHandler;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource validationMessageSource() {
		ReloadableResourceBundleMessageSource rrbm = new ReloadableResourceBundleMessageSource();
		rrbm.setBasename("classpath:validation");
		return rrbm;
	}
	
	/**
	 * 
	 * <p>
	 * 보류. 굳이 전자정부의 Mapper 애노테이션을 써야할 이유를 잘 모르겠다.<br/> 
	 * </p>
	 * @param dataSource
	 * @return
	 * @see org.egovframe.rte.psl.dataaccess.mapper.MapperConfigurer
	 * @see org.egovframe.rte.psl.dataaccess.mapper.Mapper
	 */
//	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(DataSource dataSource) {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setAnnotationClass(Mapper.class);
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("*");
		return mapperScannerConfigurer;
	}
	
	
}
